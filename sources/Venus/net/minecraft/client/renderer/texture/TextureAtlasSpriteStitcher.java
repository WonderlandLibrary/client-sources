/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.google.gson.JsonObject;
import net.minecraft.client.resources.data.VillagerMetadataSection;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.util.JSONUtils;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class TextureAtlasSpriteStitcher
implements IMetadataSectionSerializer<VillagerMetadataSection> {
    @Override
    public VillagerMetadataSection deserialize(JsonObject jsonObject) {
        return new VillagerMetadataSection(VillagerMetadataSection.HatType.func_217821_a(JSONUtils.getString(jsonObject, "hat", "none")));
    }

    @Override
    public String getSectionName() {
        return "villager";
    }

    @Override
    public Object deserialize(JsonObject jsonObject) {
        return this.deserialize(jsonObject);
    }
}

