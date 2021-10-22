package com.socen.ws.common.domain;

import java.util.HashMap;

public class WsResponse extends HashMap<String, Object> {

    private static final long serialVersionUID = -8713837118340960775L;

    public WsResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public WsResponse data(Object data) {
        this.put("data", data);
        return this;
    }

    @Override
    public WsResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
