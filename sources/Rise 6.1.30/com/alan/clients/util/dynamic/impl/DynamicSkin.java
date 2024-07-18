package com.alan.clients.util.dynamic.impl;

import com.google.gson.annotations.SerializedName;

public class DynamicSkin {
    @SerializedName("id")
    private final String id;

    @SerializedName("state")
    private final String state;

    @SerializedName("url")
    private final String url;

    @SerializedName("variant")
    private final String variant;

    public DynamicSkin(String id, String state, String url, String variant) {
        this.id = id;
        this.state = state;
        this.url = url;
        this.variant = variant;
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

    public String getVariant() {
        return variant;
    }

    @Override
    public String toString() {
        return "DynamicSkin{" +
                "id='" + id + '\'' +
                ", state='" + state + '\'' +
                ", url='" + url + '\'' +
                ", variant='" + variant + '\'' +
                '}';
    }
}
