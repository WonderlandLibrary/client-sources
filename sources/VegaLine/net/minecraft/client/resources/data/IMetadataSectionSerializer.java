/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.resources.data;

import com.google.gson.JsonDeserializer;
import net.minecraft.client.resources.data.IMetadataSection;

public interface IMetadataSectionSerializer<T extends IMetadataSection>
extends JsonDeserializer<T> {
    public String getSectionName();
}

