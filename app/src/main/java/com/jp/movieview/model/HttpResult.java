package com.jp.movieview.model;

/**
 * Author: JP
 * Time:  2017/4/13 09:34
 * 与后台约定的统一格式  泛型为数据类型
 */
public class HttpResult<T> {
    private boolean error;

    //results 即json 的key
    private T results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
