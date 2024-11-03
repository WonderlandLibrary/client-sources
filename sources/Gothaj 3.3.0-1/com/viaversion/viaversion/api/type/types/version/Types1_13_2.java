package com.viaversion.viaversion.api.type.types.version;

import com.viaversion.viaversion.api.minecraft.metadata.Metadata;
import com.viaversion.viaversion.api.minecraft.metadata.types.MetaTypes1_13_2;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.metadata.MetaListType;
import com.viaversion.viaversion.api.type.types.metadata.MetadataType;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;
import java.util.List;

public final class Types1_13_2 {
   public static final ParticleType PARTICLE = new ParticleType();
   public static final MetaTypes1_13_2 META_TYPES = new MetaTypes1_13_2(PARTICLE);
   public static final Type<Metadata> METADATA = new MetadataType(META_TYPES);
   public static final Type<List<Metadata>> METADATA_LIST = new MetaListType(METADATA);
}
