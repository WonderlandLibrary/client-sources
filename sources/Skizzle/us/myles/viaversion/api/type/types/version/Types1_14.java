/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.type.types.version;

import java.util.List;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.ViaVersion.api.type.types.minecraft.Particle1_14Type;
import us.myles.ViaVersion.api.type.types.version.Metadata1_14Type;
import us.myles.ViaVersion.api.type.types.version.MetadataList1_14Type;

public class Types1_14 {
    public static final Type<List<Metadata>> METADATA_LIST = new MetadataList1_14Type();
    public static final Type<Metadata> METADATA = new Metadata1_14Type();
    public static final Type<Particle> PARTICLE = new Particle1_14Type();
}

