/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.resources.data;

import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.data.IMetadataSectionSerializer;
import net.minecraft.resources.data.PackMetadataSection;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class PackMetadataSectionSerializer
implements IMetadataSectionSerializer<PackMetadataSection> {
    @Override
    public PackMetadataSection deserialize(JsonObject jsonObject) {
        IFormattableTextComponent iFormattableTextComponent = ITextComponent.Serializer.getComponentFromJson(jsonObject.get("description"));
        if (iFormattableTextComponent == null) {
            throw new JsonParseException("Invalid/missing description!");
        }
        int n = JSONUtils.getInt(jsonObject, "pack_format");
        return new PackMetadataSection(iFormattableTextComponent, n);
    }

    @Override
    public String getSectionName() {
        return "pack";
    }

    @Override
    public Object deserialize(JsonObject jsonObject) {
        return this.deserialize(jsonObject);
    }
}

