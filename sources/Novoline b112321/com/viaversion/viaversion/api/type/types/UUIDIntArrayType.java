package com.viaversion.viaversion.api.type.types;

import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.util.UUID;

public class UUIDIntArrayType extends Type {
   public UUIDIntArrayType() {
      super("UUID", UUID.class);
   }

   public UUID read(ByteBuf var1) {
      int[] var2 = new int[]{var1.readInt(), var1.readInt(), var1.readInt(), var1.readInt()};
      return uuidFromIntArray(var2);
   }

   public void write(ByteBuf var1, UUID var2) {
      int[] var3 = uuidToIntArray(var2);
      var1.writeInt(var3[0]);
      var1.writeInt(var3[1]);
      var1.writeInt(var3[2]);
      var1.writeInt(var3[3]);
   }

   public static UUID uuidFromIntArray(int[] var0) {
      return new UUID((long)var0[0] << 32 | (long)var0[1] & 4294967295L, (long)var0[2] << 32 | (long)var0[3] & 4294967295L);
   }

   public static int[] uuidToIntArray(UUID var0) {
      return bitsToIntArray(var0.getMostSignificantBits(), var0.getLeastSignificantBits());
   }

   public static int[] bitsToIntArray(long var0, long var2) {
      return new int[]{(int)(var0 >> 32), (int)var0, (int)(var2 >> 32), (int)var2};
   }
}
