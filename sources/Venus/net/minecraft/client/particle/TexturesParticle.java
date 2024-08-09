/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.particle;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Streams;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;

public class TexturesParticle {
    @Nullable
    private final List<ResourceLocation> textures;

    private TexturesParticle(@Nullable List<ResourceLocation> list) {
        this.textures = list;
    }

    @Nullable
    public List<ResourceLocation> getTextures() {
        return this.textures;
    }

    public static TexturesParticle deserialize(JsonObject jsonObject) {
        JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "textures", null);
        List list = jsonArray != null ? (List)Streams.stream(jsonArray).map(TexturesParticle::lambda$deserialize$0).map(ResourceLocation::new).collect(ImmutableList.toImmutableList()) : null;
        return new TexturesParticle(list);
    }

    private static String lambda$deserialize$0(JsonElement jsonElement) {
        return JSONUtils.getString(jsonElement, "texture");
    }
}

