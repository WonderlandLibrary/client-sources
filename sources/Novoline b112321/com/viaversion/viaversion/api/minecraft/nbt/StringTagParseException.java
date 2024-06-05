package com.viaversion.viaversion.api.minecraft.nbt;

import java.io.IOException;

class StringTagParseException extends IOException {
   private static final long serialVersionUID = -3001637554903912905L;
   private final CharSequence buffer;
   private final int position;

   public StringTagParseException(String var1, CharSequence var2, int var3) {
      super(var1);
      this.buffer = var2;
      this.position = var3;
   }

   public String getMessage() {
      return super.getMessage() + "(at position " + this.position + ")";
   }
}
