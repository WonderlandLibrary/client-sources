package org.json.simple.parser;

public class ParseException extends Exception {
   private static final long serialVersionUID = -7880698968187728548L;
   public static final int ERROR_UNEXPECTED_CHAR = 0;
   public static final int ERROR_UNEXPECTED_TOKEN = 1;
   public static final int ERROR_UNEXPECTED_EXCEPTION = 2;
   private int errorType;
   private Object unexpectedObject;
   private int position;

   public ParseException(int var1) {
      this(-1, var1, (Object)null);
   }

   public ParseException(int var1, Object var2) {
      this(-1, var1, var2);
   }

   public ParseException(int var1, int var2, Object var3) {
      this.position = var1;
      this.errorType = var2;
      this.unexpectedObject = var3;
   }

   public int getErrorType() {
      return this.errorType;
   }

   public void setErrorType(int var1) {
      this.errorType = var1;
   }

   public int getPosition() {
      return this.position;
   }

   public void setPosition(int var1) {
      this.position = var1;
   }

   public Object getUnexpectedObject() {
      return this.unexpectedObject;
   }

   public void setUnexpectedObject(Object var1) {
      this.unexpectedObject = var1;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      switch(this.errorType) {
      case 0:
         var1.append("Unexpected character (").append(this.unexpectedObject).append(") at position ").append(this.position).append(".");
         break;
      case 1:
         var1.append("Unexpected token ").append(this.unexpectedObject).append(" at position ").append(this.position).append(".");
         break;
      case 2:
         var1.append("Unexpected exception at position ").append(this.position).append(": ").append(this.unexpectedObject);
         break;
      default:
         var1.append("Unkown error at position ").append(this.position).append(".");
      }

      return var1.toString();
   }
}
