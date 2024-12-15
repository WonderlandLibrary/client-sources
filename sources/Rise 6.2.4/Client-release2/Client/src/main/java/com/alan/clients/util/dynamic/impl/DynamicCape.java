package com.alan.clients.util.dynamic.impl;

import com.google.gson.annotations.SerializedName;

public class DynamicCape {
    @SerializedName("id")
    private final String id;

    @SerializedName("state")
    private final String state;

    @SerializedName("url")
    private final String url;

    @SerializedName("alias")
    private final String alias;

    public DynamicCape(String id, String state, String url, String alias) {
        this.id = id;
        this.state = state;
        this.url = url;
        this.alias = alias;
    }

    public String getId() {
        return id;
    }

    public String getState() {
        return state;
    }

    public String getUrl() {
        return url;
    }

    public String getAlias() {
        return alias;
    }
}
