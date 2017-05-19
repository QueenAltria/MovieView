package com.jp.movieview.api;

import com.jp.movieview.constant.Api;

import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * Created by jp on 2017/4/13
 */
public interface YandeService {

    String BASE_URL = Api.URL_YANDE;

    @Headers({
            "Authoriztion: JP",
            "User-Agent: UA",
    })
    @GET("post/popular_by_day")
    Observable<String> getPopularDayData(@Query("day") String day,
                                         @Query("month") String month,
                                         @Query("year") String year);


    @GET("post/popular_by_week")
    Observable<String> getPopularWeekData(@Query("day") String day,
                                         @Query("month") String month,
                                         @Query("year") String year);

    @GET("post/popular_by_month")
    Observable<String> getPopularMonthData(@Query("day") String day,
                                         @Query("month") String month,
                                         @Query("year") String year);

    @GET("post/popular_recent")
    Observable<String> getPopularYearData(@Query("period") String period);


    @GET("post")
    Observable<String> getTagData(@Query("page") String page,
                                  @Query("tags") String tags);

    @GET("post/popular_by_day?day={day}&month={month}&year={year}")
    Observable<String> getYandePopularData(@Path("day") String day,
                                           @Path("month") String month,
                                           @Path("year") String year);

}
