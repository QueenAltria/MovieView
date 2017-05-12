package com.jp.movieview.net;

import com.jp.movieview.api.GsonService;
import com.jp.movieview.api.YandeService;

public class ApiService {
    public static ApiService getInstance() {
        return ApiService.SingletonHolder.INSTANCE;
    }

    private static class SingletonHolder {
        private static final ApiService INSTANCE = new ApiService();
    }

    public <S> S initService(Class<S> service) {
        if (service.equals(GsonService.class)) {
            return NetManager.getInstance().createGson(service);
        } else if (service.equals(YandeService.class)) {
            return NetManager.getInstance().createString(service);
        }
        return null;
    }
}
