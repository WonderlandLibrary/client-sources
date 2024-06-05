package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.protocol.remapper.PacketRemapper;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;
import net.Gh;

public class StringType extends Type {
   private static final int maxJavaCharUtf8Length = Character.toString('\uffff').getBytes(StandardCharsets.UTF_8).length;
   private final int maxLength;

   public StringType() {
      this(32767);
   }

   public StringType(int var1) {
      super("String", String.class);
      this.maxLength = var1;
   }

   public String read(ByteBuf var1) throws Exception {
      Gh.b();
      int var3 = Type.VAR_INT.readPrimitive(var1);
      Preconditions.checkArgument(var3 <= this.maxLength * maxJavaCharUtf8Length, "Cannot receive string longer than Short.MAX_VALUE * " + maxJavaCharUtf8Length + " bytes (got %s bytes)", new Object[]{Integer.valueOf(var3)});
      String var4 = var1.toString(var1.readerIndex(), var3, StandardCharsets.UTF_8);
      var1.skipBytes(var3);
      Preconditions.checkArgument(var4.length() <= this.maxLength, "Cannot receive string longer than Short.MAX_VALUE characters (got %s bytes)", new Object[]{Integer.valueOf(var4.length())});
      return var4;
   }

   public void write(ByteBuf var1, String var2) throws Exception {
      String var3 = Gh.b();
      Preconditions.checkArgument(var2.length() <= this.maxLength, "Cannot send string longer than Short.MAX_VALUE (got %s characters)", new Object[]{Integer.valueOf(var2.length())});
      byte[] var4 = var2.getBytes(StandardCharsets.UTF_8);
      Type.VAR_INT.writePrimitive(var1, var4.length);
      var1.writeBytes(var4);
      if(PacketRemapper.b() == null) {
         Gh.b("uzF6x");
      }

   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
