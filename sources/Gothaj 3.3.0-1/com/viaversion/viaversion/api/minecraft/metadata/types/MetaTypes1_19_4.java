package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.api.type.types.misc.ParticleType;

public final class MetaTypes1_19_4 extends AbstractMetaTypes {
   public final MetaType byteType = this.add(0, Type.BYTE);
   public final MetaType varIntType = this.add(1, Type.VAR_INT);
   public final MetaType longType = this.add(2, Type.VAR_LONG);
   public final MetaType floatType = this.add(3, Type.FLOAT);
   public final MetaType stringType = this.add(4, Type.STRING);
   public final MetaType componentType = this.add(5, Type.COMPONENT);
   public final MetaType optionalComponentType = this.add(6, Type.OPTIONAL_COMPONENT);
   public final MetaType itemType = this.add(7, Type.ITEM1_13_2);
   public final MetaType booleanType = this.add(8, Type.BOOLEAN);
   public final MetaType rotationType = this.add(9, Type.ROTATION);
   public final MetaType positionType = this.add(10, Type.POSITION1_14);
   public final MetaType optionalPositionType = this.add(11, Type.OPTIONAL_POSITION_1_14);
   public final MetaType directionType = this.add(12, Type.VAR_INT);
   public final MetaType optionalUUIDType = this.add(13, Type.OPTIONAL_UUID);
   public final MetaType blockStateType = this.add(14, Type.VAR_INT);
   public final MetaType optionalBlockStateType = this.add(15, Type.VAR_INT);
   public final MetaType nbtType = this.add(16, Type.NAMED_COMPOUND_TAG);
   public final MetaType particleType;
   public final MetaType villagerDatatType = this.add(18, Type.VILLAGER_DATA);
   public final MetaType optionalVarIntType = this.add(19, Type.OPTIONAL_VAR_INT);
   public final MetaType poseType = this.add(20, Type.VAR_INT);
   public final MetaType catVariantType = this.add(21, Type.VAR_INT);
   public final MetaType frogVariantType = this.add(22, Type.VAR_INT);
   public final MetaType optionalGlobalPosition = this.add(23, Type.OPTIONAL_GLOBAL_POSITION);
   public final MetaType paintingVariantType = this.add(24, Type.VAR_INT);
   public final MetaType snifferState = this.add(25, Type.VAR_INT);
   public final MetaType vectorType = this.add(26, Type.VECTOR3F);
   public final MetaType quaternionType = this.add(27, Type.QUATERNION);

   public MetaTypes1_19_4(ParticleType particleType) {
      super(28);
      this.particleType = this.add(17, particleType);
   }
}
