package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;

public enum MetaType1_8 implements MetaType {
   Byte(0, Type.k),
   Short(1, Type.SHORT),
   Int(2, Type.I),
   Float(3, Type.FLOAT),
   String(4, Type.STRING),
   Slot(5, Type.ITEM),
   Position(6, Type.VECTOR),
   Rotation(7, Type.ROTATION),
   NonExistent(-1, Type.af);

   private final int typeID;
   private final Type type;

   private MetaType1_8(int var3, Type var4) {
      this.typeID = var3;
      this.type = var4;
   }

   public static MetaType1_8 byId(int var0) {
      return values()[var0];
   }

   public int typeId() {
      return this.typeID;
   }

   public Type type() {
      return this.type;
   }
}
