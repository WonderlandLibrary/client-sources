package org.json.simple.parser;

public class Yytoken {
   public static final int TYPE_VALUE = 0;
   public static final int TYPE_LEFT_BRACE = 1;
   public static final int TYPE_RIGHT_BRACE = 2;
   public static final int TYPE_LEFT_SQUARE = 3;
   public static final int TYPE_RIGHT_SQUARE = 4;
   public static final int TYPE_COMMA = 5;
   public static final int TYPE_COLON = 6;
   public static final int TYPE_EOF = -1;
   public int type = 0;
   public Object value = null;

   public Yytoken(int var1, Object var2) {
      this.type = var1;
      this.value = var2;
   }

   public String toString() {
      StringBuffer var1 = new StringBuffer();
      switch(this.type) {
      case -1:
         var1.append("END OF FILE");
         break;
      case 0:
         var1.append("VALUE(").append(this.value).append(")");
         break;
      case 1:
         var1.append("LEFT BRACE({)");
         break;
      case 2:
         var1.append("RIGHT BRACE(})");
         break;
      case 3:
         var1.append("LEFT SQUARE([)");
         break;
      case 4:
         var1.append("RIGHT SQUARE(])");
         break;
      case 5:
         var1.append("COMMA(,)");
         break;
      case 6:
         var1.append("COLON(:)");
      }

      return var1.toString();
   }
}
