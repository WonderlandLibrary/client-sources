package com.viaversion.viaversion.libs.opennbt.stringified;

public final class StringifiedTagParseException extends RuntimeException {
   private static final long serialVersionUID = -3001637514903912905L;
   private final int position;

   public StringifiedTagParseException(String message, int position) {
      super(message);
      this.position = position;
   }

   @Override
   public String getMessage() {
      return super.getMessage() + "(at position " + this.position + ")";
   }

   public int getPosition() {
      return this.position;
   }
}
