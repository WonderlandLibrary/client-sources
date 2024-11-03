package com.viaversion.viaversion.api.minecraft.metadata.types;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;

public enum MetaType1_12 implements MetaType {
   Byte(0, Type.BYTE),
   VarInt(1, Type.VAR_INT),
   Float(2, Type.FLOAT),
   String(3, Type.STRING),
   Chat(4, Type.COMPONENT),
   Slot(5, Type.ITEM1_8),
   Boolean(6, Type.BOOLEAN),
   Vector3F(7, Type.ROTATION),
   Position(8, Type.POSITION1_8),
   OptPosition(9, Type.OPTIONAL_POSITION1_8),
   Direction(10, Type.VAR_INT),
   OptUUID(11, Type.OPTIONAL_UUID),
   BlockID(12, Type.VAR_INT),
   NBTTag(13, Type.NAMED_COMPOUND_TAG);

   private final int typeID;
   private final Type type;

   private MetaType1_12(int typeID, Type type) {
      this.typeID = typeID;
      this.type = type;
   }

   public static MetaType1_12 byId(int id) {
      return values()[id];
   }

   @Override
   public int typeId() {
      return this.typeID;
   }

   @Override
   public Type type() {
      return this.type;
   }
}
