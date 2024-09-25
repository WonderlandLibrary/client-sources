/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.type.types.version;

import java.util.List;
import us.myles.ViaVersion.api.minecraft.chunks.ChunkSection;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.ViaVersion.api.type.types.version.ChunkSectionType1_13;
import us.myles.ViaVersion.api.type.types.version.Metadata1_13Type;
import us.myles.ViaVersion.api.type.types.version.MetadataList1_13Type;
import us.myles.ViaVersion.protocols.protocol1_13to1_12_2.types.Particle1_13Type;

public class Types1_13 {
    public static final Type<List<Metadata>> METADATA_LIST = new MetadataList1_13Type();
    public static final Type<Metadata> METADATA = new Metadata1_13Type();
    public static final Type<ChunkSection> CHUNK_SECTION = new ChunkSectionType1_13();
    public static final Type<Particle> PARTICLE = new Particle1_13Type();
}

