/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.type.types.version;

import java.util.List;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.Particle;
import us.myles.ViaVersion.api.type.types.version.Metadata1_13_2Type;
import us.myles.ViaVersion.api.type.types.version.MetadataList1_13_2Type;
import us.myles.ViaVersion.protocols.protocol1_13_2to1_13_1.types.Particle1_13_2Type;

public class Types1_13_2 {
    public static final Type<List<Metadata>> METADATA_LIST = new MetadataList1_13_2Type();
    public static final Type<Metadata> METADATA = new Metadata1_13_2Type();
    public static Type<Particle> PARTICLE = new Particle1_13_2Type();
}

