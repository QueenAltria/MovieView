package com.jp.movieview.net;

import android.webkit.MimeTypeMap;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Converter;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * Author: Othershe
 * Time:  2016/8/11 14:30
 */
public class NetManager {
    private static final int DEFAULT_TIMEOUT = 10;

    public static NetManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final NetManager INSTANCE = new NetManager();
    }

    private NetManager() {

    }

    public <S> S createGson(Class<S> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(GsonConverterFactory.create())       //Gson
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())  //支持Rxjava
                .baseUrl(getBaseUrl(service))
                .build();
        return retrofit.create(service);
    }

    public <S> S createString(Class<S> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(getBaseUrl(service))
                .build();
        return retrofit.create(service);
    }

    public <S> S createResponse(Class<S> service) {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getOkHttpClient())
                .addConverterFactory(new Converter.Factory() {
                    @Override
                    public Converter<okhttp3.ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
                        return new Converter<okhttp3.ResponseBody, ResponseBody>() {
                            @Override
                            public ResponseBody convert(okhttp3.ResponseBody value) throws IOException {
                                MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();// http协议中的Content-type生成器
                                ResponseBody responseBody = ResponseBody
                                        .create(
                                                MediaType.parse(          // 将字符串解析成MediaType类型
                                                        mimeTypeMap.getMimeTypeFromExtension("jpg") // 获得jpg文件的Content-type值
                                                ),
                                                value.bytes() // value就是这次http请求中的响应体，我们需要的图片数据就在这里面
                                        );
                                return responseBody;
                            }
                        };
                    }
                })
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .baseUrl(getBaseUrl(service))
                .build();
        return retrofit.create(service);
    }

    /**
     * 解析接口中的BASE_URL，解决BASE_URL不一致的问题
     *
     * @param service
     * @param <S>
     * @return
     */
    private <S> String getBaseUrl(Class<S> service) {
        try {
            Field field = service.getField("BASE_URL");
            return (String) field.get(service);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private OkHttpClient getOkHttpClient() {
        //配置超时拦截器
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
        builder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        //配置log打印拦截器
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        builder.addInterceptor(loggingInterceptor);
        return builder.build();
    }
}
