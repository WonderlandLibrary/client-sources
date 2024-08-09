package dev.luvbeeq.baritone.api.utils;

import com.google.gson.annotations.SerializedName;

/**
 * Need a non obfed chunkpos that we can load using GSON
 */
public class MyChunkPos {

    @SerializedName("x")
    public int x;

    @SerializedName("z")
    public int z;

    @Override
    public String toString() {
        return x + ", " + z;
    }
}
