/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

import com.google.gson.JsonObject;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JSONUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TextureMetadataSectionSerializer
implements IMetadataSectionSerializer<TextureMetadataSection> {
    @Override
    public TextureMetadataSection deserialize(JsonObject jsonObject) {
        boolean bl = JSONUtils.getBoolean(jsonObject, "blur", false);
        boolean bl2 = JSONUtils.getBoolean(jsonObject, "clamp", false);
        return new TextureMetadataSection(bl, bl2);
    }

    @Override
    public String getSectionName() {
        return "texture";
    }

    @Override
    public Object deserialize(JsonObject jsonObject) {
        return this.deserialize(jsonObject);
    }
}

