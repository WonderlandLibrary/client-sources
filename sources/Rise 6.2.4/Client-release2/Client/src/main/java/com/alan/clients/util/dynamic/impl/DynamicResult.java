package com.alan.clients.util.dynamic.impl;

import com.google.gson.annotations.SerializedName;

import java.util.Arrays;

public class DynamicResult {
    @SerializedName("id")
    private final int id;

    @SerializedName("name")
    private final String name;

    @SerializedName("uuid")
    private final String uuid;

    @SerializedName("skins")
    private final DynamicSkin[] skins;

    @SerializedName("capes")
    private final DynamicCape[] capes;

    public DynamicResult(int id, String name, String uuid, DynamicSkin[] skins, DynamicCape[] capes) {
        this.id = id;
        this.name = name;
        this.uuid = uuid;
        this.skins = skins;
        this.capes = capes;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUuid() {
        return uuid;
    }

    public DynamicSkin[] getSkins() {
        return skins;
    }

    public DynamicCape[] getCapes() {
        return capes;
    }

    @Override
    public String toString() {
        return "DynamicAccountResult{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", uuid='" + uuid + '\'' +
                ", skins=" + Arrays.toString(skins) +
                ", capes=" + Arrays.toString(capes) +
                '}';
    }
}
