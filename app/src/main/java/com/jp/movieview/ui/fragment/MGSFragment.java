package com.jp.movieview.ui.fragment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.jp.movieview.R;
import com.jp.movieview.callback.JsonCallBack;
import com.jp.movieview.ui.activity.MainActivity;
import com.jp.movieview.utils.LogUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.cookie.store.CookieStore;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import butterknife.BindView;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Cookie;
import okhttp3.HttpUrl;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class MGSFragment extends Fragment {
    public String TAG=getClass().getName();

    @BindView(R.id.text1)
    TextView mTextView;

    String end="";

    public MGSFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view=inflater.inflate(R.layout.fragment_mg, container, false);
        ButterKnife.bind(this,view);


        HttpUrl httpUrl = HttpUrl.parse("http://mgstage.com");
        Cookie.Builder builder = new Cookie.Builder();
        Cookie cookie = builder.name("ageConf").value("1").domain(httpUrl.host()).build();
        Cookie cookie2 = builder.name("adc").value("1").domain(httpUrl.host()).build();
        CookieStore cookieStore = OkGo.getInstance().getCookieJar().getCookieStore();
        cookieStore.saveCookie(httpUrl, cookie);
        cookieStore.saveCookie(httpUrl, cookie2);
        OkGo.getInstance().setCookieStore(cookieStore);


        OkGo.get("http://sp.mgstage.com")
                .tag(this)
                .execute(new JsonCallBack<String>(getActivity()) {
                    @Override
                    public void onSuccess(String result, Call call, Response response) {
                        LogUtils.e(TAG,"mgs"+result);
                        mTextView.setText(result);

                        Document doc = Jsoup.parse(result);
                        //Elements company = doc.select("a[href]");
                        Elements select = doc.select("ul.top_photo_list");
                        Elements sizes = doc.select("span.directlink-res");
                        Elements real_size_url=doc.select("a.directlink");

                        Elements select1 = doc.select("[src$=.jpg");

                        Elements li = select.select("li");
                        for (int i=0;i<li.size();i++){
                            //String attr = li.get(i).select("a").attr("href");
                            String attr = li.get(i).select("img").attr("src");

                            end=end+attr+"\n";

                        }



                        mTextView.setText(end);


                    }
                });


        return view;
    }

}
