package org.spongepowered.asm.lib.signature;

public class SignatureReader {
   private final String signature;

   public SignatureReader(String var1) {
      this.signature = var1;
   }

   public void accept(SignatureVisitor var1) {
      String var2 = this.signature;
      int var3 = var2.length();
      int var4;
      if (var2.charAt(0) == '<') {
         var4 = 2;

         char var6;
         do {
            int var5 = var2.indexOf(58, var4);
            var1.visitFormalTypeParameter(var2.substring(var4 - 1, var5));
            var4 = var5 + 1;
            var6 = var2.charAt(var4);
            if (var6 == 'L' || var6 == '[' || var6 == 'T') {
               var4 = parseType(var2, var4, var1.visitClassBound());
            }

            while((var6 = var2.charAt(var4++)) == ':') {
               var4 = parseType(var2, var4, var1.visitInterfaceBound());
            }
         } while(var6 != '>');
      } else {
         var4 = 0;
      }

      if (var2.charAt(var4) == '(') {
         ++var4;

         while(var2.charAt(var4) != ')') {
            var4 = parseType(var2, var4, var1.visitParameterType());
         }

         for(var4 = parseType(var2, var4 + 1, var1.visitReturnType()); var4 < var3; var4 = parseType(var2, var4 + 1, var1.visitExceptionType())) {
         }
      } else {
         for(var4 = parseType(var2, var4, var1.visitSuperclass()); var4 < var3; var4 = parseType(var2, var4, var1.visitInterface())) {
         }
      }

   }

   public void acceptType(SignatureVisitor var1) {
      parseType(this.signature, 0, var1);
   }

   private static int parseType(String var0, int var1, SignatureVisitor var2) {
      char var3;
      switch(var3 = var0.charAt(var1++)) {
      case 'B':
      case 'C':
      case 'D':
      case 'F':
      case 'I':
      case 'J':
      case 'S':
      case 'V':
      case 'Z':
         var2.visitBaseType(var3);
         return var1;
      case 'E':
      case 'G':
      case 'H':
      case 'K':
      case 'L':
      case 'M':
      case 'N':
      case 'O':
      case 'P':
      case 'Q':
      case 'R':
      case 'U':
      case 'W':
      case 'X':
      case 'Y':
      default:
         int var5 = var1;
         boolean var6 = false;
         boolean var7 = false;

         while(true) {
            label66:
            while(true) {
               String var8;
               switch(var3 = var0.charAt(var1++)) {
               case '.':
               case ';':
                  if (!var6) {
                     var8 = var0.substring(var5, var1 - 1);
                     if (var7) {
                        var2.visitInnerClassType(var8);
                     } else {
                        var2.visitClassType(var8);
                     }
                  }

                  if (var3 == ';') {
                     var2.visitEnd();
                     return var1;
                  }

                  var5 = var1;
                  var6 = false;
                  var7 = true;
                  break;
               case '<':
                  var8 = var0.substring(var5, var1 - 1);
                  if (var7) {
                     var2.visitInnerClassType(var8);
                  } else {
                     var2.visitClassType(var8);
                  }

                  var6 = true;

                  while(true) {
                     while(true) {
                        switch(var3 = var0.charAt(var1)) {
                        case '*':
                           ++var1;
                           var2.visitTypeArgument();
                           break;
                        case '+':
                        case '-':
                           var1 = parseType(var0, var1 + 1, var2.visitTypeArgument(var3));
                           break;
                        case '>':
                           continue label66;
                        default:
                           var1 = parseType(var0, var1, var2.visitTypeArgument('='));
                        }
                     }
                  }
               }
            }
         }
      case 'T':
         int var4 = var0.indexOf(59, var1);
         var2.visitTypeVariable(var0.substring(var1, var4));
         return var4 + 1;
      case '[':
         return parseType(var0, var1, var2.visitArrayType());
      }
   }
}
