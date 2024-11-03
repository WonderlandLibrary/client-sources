package org.jsoup.parser;

public class ParseError {
   private int pos;
   private String cursorPos;
   private String errorMsg;

   ParseError(CharacterReader reader, String errorMsg) {
      this.pos = reader.pos();
      this.cursorPos = reader.cursorPos();
      this.errorMsg = errorMsg;
   }

   ParseError(CharacterReader reader, String errorFormat, Object... args) {
      this.pos = reader.pos();
      this.cursorPos = reader.cursorPos();
      this.errorMsg = String.format(errorFormat, args);
   }

   ParseError(int pos, String errorMsg) {
      this.pos = pos;
      this.cursorPos = String.valueOf(pos);
      this.errorMsg = errorMsg;
   }

   ParseError(int pos, String errorFormat, Object... args) {
      this.pos = pos;
      this.cursorPos = String.valueOf(pos);
      this.errorMsg = String.format(errorFormat, args);
   }

   public String getErrorMessage() {
      return this.errorMsg;
   }

   public int getPosition() {
      return this.pos;
   }

   public String getCursorPos() {
      return this.cursorPos;
   }

   @Override
   public String toString() {
      return "<" + this.cursorPos + ">: " + this.errorMsg;
   }
}
