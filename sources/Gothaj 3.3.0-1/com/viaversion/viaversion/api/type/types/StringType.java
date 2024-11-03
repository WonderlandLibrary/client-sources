package com.viaversion.viaversion.api.type.types;

import com.google.common.base.Preconditions;
import com.viaversion.viaversion.api.type.OptionalType;
import com.viaversion.viaversion.api.type.Type;
import io.netty.buffer.ByteBuf;
import java.nio.charset.StandardCharsets;

public class StringType extends Type<String> {
   private static final int MAX_CHAR_UTF_8_LENGTH = Character.toString('\uffff').getBytes(StandardCharsets.UTF_8).length;
   private final int maxLength;

   public StringType() {
      this(32767);
   }

   public StringType(int maxLength) {
      super(String.class);
      this.maxLength = maxLength;
   }

   public String read(ByteBuf buffer) throws Exception {
      int len = Type.VAR_INT.readPrimitive(buffer);
      Preconditions.checkArgument(
         len <= this.maxLength * MAX_CHAR_UTF_8_LENGTH,
         "Cannot receive string longer than Short.MAX_VALUE * " + MAX_CHAR_UTF_8_LENGTH + " bytes (got %s bytes)",
         new Object[]{len}
      );
      String string = buffer.toString(buffer.readerIndex(), len, StandardCharsets.UTF_8);
      buffer.skipBytes(len);
      Preconditions.checkArgument(
         string.length() <= this.maxLength, "Cannot receive string longer than Short.MAX_VALUE characters (got %s bytes)", new Object[]{string.length()}
      );
      return string;
   }

   public void write(ByteBuf buffer, String object) throws Exception {
      if (object.length() > this.maxLength) {
         throw new IllegalArgumentException("Cannot send string longer than Short.MAX_VALUE characters (got " + object.length() + " characters)");
      } else {
         byte[] b = object.getBytes(StandardCharsets.UTF_8);
         Type.VAR_INT.writePrimitive(buffer, b.length);
         buffer.writeBytes(b);
      }
   }

   public static final class OptionalStringType extends OptionalType<String> {
      public OptionalStringType() {
         super(Type.STRING);
      }
   }
}
