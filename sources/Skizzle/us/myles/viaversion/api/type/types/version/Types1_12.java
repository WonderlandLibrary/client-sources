/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.api.type.types.version;

import java.util.List;
import us.myles.ViaVersion.api.minecraft.metadata.Metadata;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.api.type.types.version.Metadata1_12Type;
import us.myles.ViaVersion.api.type.types.version.MetadataList1_12Type;

public class Types1_12 {
    public static final Type<List<Metadata>> METADATA_LIST = new MetadataList1_12Type();
    public static final Type<Metadata> METADATA = new Metadata1_12Type();
}

