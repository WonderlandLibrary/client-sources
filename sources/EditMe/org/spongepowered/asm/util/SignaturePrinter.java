package org.spongepowered.asm.util;

import com.google.common.base.Strings;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.struct.MemberInfo;

public class SignaturePrinter {
   private final String name;
   private final Type returnType;
   private final Type[] argTypes;
   private final String[] argNames;
   private String modifiers;
   private boolean fullyQualified;

   public SignaturePrinter(MethodNode var1) {
      this(var1.name, Type.VOID_TYPE, Type.getArgumentTypes(var1.desc));
      this.setModifiers(var1);
   }

   public SignaturePrinter(MethodNode var1, String[] var2) {
      this(var1.name, Type.VOID_TYPE, Type.getArgumentTypes(var1.desc), var2);
      this.setModifiers(var1);
   }

   public SignaturePrinter(MemberInfo var1) {
      this(var1.name, var1.desc);
   }

   public SignaturePrinter(String var1, String var2) {
      this(var1, Type.getReturnType(var2), Type.getArgumentTypes(var2));
   }

   public SignaturePrinter(String var1, Type var2, Type[] var3) {
      this.modifiers = "private void";
      this.name = var1;
      this.returnType = var2;
      this.argTypes = new Type[var3.length];
      this.argNames = new String[var3.length];
      int var4 = 0;

      for(int var5 = 0; var4 < var3.length; ++var4) {
         if (var3[var4] != null) {
            this.argTypes[var4] = var3[var4];
            this.argNames[var4] = "var" + var5++;
         }
      }

   }

   public SignaturePrinter(String var1, Type var2, LocalVariableNode[] var3) {
      this.modifiers = "private void";
      this.name = var1;
      this.returnType = var2;
      this.argTypes = new Type[var3.length];
      this.argNames = new String[var3.length];

      for(int var4 = 0; var4 < var3.length; ++var4) {
         if (var3[var4] != null) {
            this.argTypes[var4] = Type.getType(var3[var4].desc);
            this.argNames[var4] = var3[var4].name;
         }
      }

   }

   public SignaturePrinter(String var1, Type var2, Type[] var3, String[] var4) {
      this.modifiers = "private void";
      this.name = var1;
      this.returnType = var2;
      this.argTypes = var3;
      this.argNames = var4;
      if (this.argTypes.length > this.argNames.length) {
         throw new IllegalArgumentException(String.format("Types array length must not exceed names array length! (names=%d, types=%d)", this.argNames.length, this.argTypes.length));
      }
   }

   public String getFormattedArgs() {
      return this.appendArgs(new StringBuilder(), true, true).toString();
   }

   public String getReturnType() {
      return getTypeName(this.returnType, false, this.fullyQualified);
   }

   public void setModifiers(MethodNode var1) {
      String var2 = getTypeName(Type.getReturnType(var1.desc), false, this.fullyQualified);
      if ((var1.access & 1) != 0) {
         this.setModifiers("public " + var2);
      } else if ((var1.access & 4) != 0) {
         this.setModifiers("protected " + var2);
      } else if ((var1.access & 2) != 0) {
         this.setModifiers("private " + var2);
      } else {
         this.setModifiers(var2);
      }

   }

   public SignaturePrinter setModifiers(String var1) {
      this.modifiers = var1.replace("${returnType}", this.getReturnType());
      return this;
   }

   public SignaturePrinter setFullyQualified(boolean var1) {
      this.fullyQualified = var1;
      return this;
   }

   public boolean isFullyQualified() {
      return this.fullyQualified;
   }

   public String toString() {
      return this.appendArgs((new StringBuilder()).append(this.modifiers).append(" ").append(this.name), false, true).toString();
   }

   public String toDescriptor() {
      StringBuilder var1 = this.appendArgs(new StringBuilder(), true, false);
      return var1.append(getTypeName(this.returnType, false, this.fullyQualified)).toString();
   }

   private StringBuilder appendArgs(StringBuilder var1, boolean var2, boolean var3) {
      var1.append('(');

      for(int var4 = 0; var4 < this.argTypes.length; ++var4) {
         if (this.argTypes[var4] != null) {
            if (var4 > 0) {
               var1.append(',');
               if (var3) {
                  var1.append(' ');
               }
            }

            try {
               String var5 = var2 ? null : (Strings.isNullOrEmpty(this.argNames[var4]) ? "unnamed" + var4 : this.argNames[var4]);
               this.appendType(var1, this.argTypes[var4], var5);
            } catch (Exception var6) {
               throw new RuntimeException(var6);
            }
         }
      }

      return var1.append(")");
   }

   private StringBuilder appendType(StringBuilder var1, Type var2, String var3) {
      switch(var2.getSort()) {
      case 9:
         return appendArraySuffix(this.appendType(var1, var2.getElementType(), var3), var2);
      case 10:
         return this.appendType(var1, var2.getClassName(), var3);
      default:
         var1.append(getTypeName(var2, false, this.fullyQualified));
         if (var3 != null) {
            var1.append(' ').append(var3);
         }

         return var1;
      }
   }

   private StringBuilder appendType(StringBuilder var1, String var2, String var3) {
      if (!this.fullyQualified) {
         var2 = var2.substring(var2.lastIndexOf(46) + 1);
      }

      var1.append(var2);
      if (var2.endsWith("CallbackInfoReturnable")) {
         var1.append('<').append(getTypeName(this.returnType, true, this.fullyQualified)).append('>');
      }

      if (var3 != null) {
         var1.append(' ').append(var3);
      }

      return var1;
   }

   public static String getTypeName(Type var0, boolean var1) {
      return getTypeName(var0, var1, false);
   }

   public static String getTypeName(Type var0, boolean var1, boolean var2) {
      switch(var0.getSort()) {
      case 0:
         return var1 ? "Void" : "void";
      case 1:
         return var1 ? "Boolean" : "boolean";
      case 2:
         return var1 ? "Character" : "char";
      case 3:
         return var1 ? "Byte" : "byte";
      case 4:
         return var1 ? "Short" : "short";
      case 5:
         return var1 ? "Integer" : "int";
      case 6:
         return var1 ? "Float" : "float";
      case 7:
         return var1 ? "Long" : "long";
      case 8:
         return var1 ? "Double" : "double";
      case 9:
         return getTypeName(var0.getElementType(), var1, var2) + arraySuffix(var0);
      case 10:
         String var3 = var0.getClassName();
         if (!var2) {
            var3 = var3.substring(var3.lastIndexOf(46) + 1);
         }

         return var3;
      default:
         return "Object";
      }
   }

   private static String arraySuffix(Type var0) {
      return Strings.repeat("[]", var0.getDimensions());
   }

   private static StringBuilder appendArraySuffix(StringBuilder var0, Type var1) {
      for(int var2 = 0; var2 < var1.getDimensions(); ++var2) {
         var0.append("[]");
      }

      return var0;
   }
}
