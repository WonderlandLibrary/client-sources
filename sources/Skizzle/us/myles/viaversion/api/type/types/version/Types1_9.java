/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.type.types.version;

import java.util.List;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.ChunkSectionType1_9;
import us.myles.ViaVersion.api.type.types.version.Metadata1_9Type;
import us.myles.ViaVersion.api.type.types.version.MetadataList1_9Type;

public class Types1_9 {
    public static final Type<List<Metadata>> METADATA_LIST = new MetadataList1_9Type();
    public static final Type<Metadata> METADATA = new Metadata1_9Type();
    public static final Type<ChunkSection> CHUNK_SECTION = new ChunkSectionType1_9();
}

