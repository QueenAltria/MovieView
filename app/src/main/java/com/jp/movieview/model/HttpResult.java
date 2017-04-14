package com.jp.movieview.model;

/**
 * Author: JP
 * Time:  2014/4/13 09:34
 */
public class HttpResult<T> {
    private boolean error;
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
