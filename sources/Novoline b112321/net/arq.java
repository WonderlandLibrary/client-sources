package net;

import com.viaversion.viaversion.api.minecraft.metadata.MetaType;
import com.viaversion.viaversion.api.type.Type;

public enum arq implements MetaType {
   Byte(0, Type.k),
   VarInt(1, Type.VAR_INT),
   Float(2, Type.FLOAT),
   String(3, Type.STRING),
   Chat(4, Type.p),
   Slot(5, Type.ITEM),
   Boolean(6, Type.c),
   Vector3F(7, Type.ROTATION),
   Position(8, Type.POSITION),
   OptPosition(9, Type.v),
   Direction(10, Type.VAR_INT),
   OptUUID(11, Type.OPTIONAL_UUID),
   BlockID(12, Type.VAR_INT),
   NBTTag(13, Type.ac),
   Discontinued(99, (Type)null);

   private final int b;
   private final Type a;

   private arq(int var3, Type var4) {
      this.b = var3;
      this.a = var4;
   }

   public static arq a(int var0) {
      return values()[var0];
   }

   public int typeId() {
      return this.b;
   }

   public Type type() {
      return this.a;
   }
}
