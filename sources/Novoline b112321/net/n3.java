package net;

import net.aSG;
import viaversion.viaversion.api.minecraft.metadata.MetaType;
import viaversion.viaversion.api.type.Type;

public enum n3 implements MetaType {
   Byte(0, Type.BYTE),
   VarInt(1, Type.VAR_INT),
   Float(2, Type.FLOAT),
   String(3, Type.STRING),
   Chat(4, Type.COMPONENT),
   OptChat(5, Type.OPTIONAL_COMPONENT),
   Slot(6, Type.FLAT_VAR_INT_ITEM),
   Boolean(7, Type.BOOLEAN),
   Vector3F(8, Type.ROTATION),
   Position(9, Type.POSITION1_14),
   OptPosition(10, Type.OPTIONAL_POSITION_1_14),
   Direction(11, Type.VAR_INT),
   OptUUID(12, Type.OPTIONAL_UUID),
   BlockID(13, Type.VAR_INT),
   NBTTag(14, Type.NBT),
   PARTICLE(15, aSG.e),
   VillagerData(16, Type.VILLAGER_DATA),
   OptVarInt(17, Type.OPTIONAL_VAR_INT),
   Pose(18, Type.VAR_INT),
   Discontinued(99, (Type)null);

   private final int c;
   private final Type b;

   private n3(int var3, Type var4) {
      this.c = var3;
      this.b = var4;
   }

   public static n3 a(int var0) {
      return values()[var0];
   }

   public int getTypeID() {
      return this.c;
   }

   public Type getType() {
      return this.b;
   }
}
