package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.metadata.MetaListType;
import com.viaversion.viaversion.api.type.types.metadata.MetadataType1_12;
import java.util.List;

public final class Types1_12 {
   public static final Type<Metadata> METADATA = new MetadataType1_12();
   public static final Type<List<Metadata>> METADATA_LIST = new MetaListType(METADATA);
}
