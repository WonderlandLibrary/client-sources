package org.spongepowered.asm.lib.tree.analysis;

import org.spongepowered.asm.lib.Type;

public class BasicValue implements Value {
   public static final BasicValue UNINITIALIZED_VALUE = new BasicValue((Type)null);
   public static final BasicValue INT_VALUE;
   public static final BasicValue FLOAT_VALUE;
   public static final BasicValue LONG_VALUE;
   public static final BasicValue DOUBLE_VALUE;
   public static final BasicValue REFERENCE_VALUE;
   public static final BasicValue RETURNADDRESS_VALUE;
   private final Type type;

   public BasicValue(Type var1) {
      this.type = var1;
   }

   public Type getType() {
      return this.type;
   }

   public int getSize() {
      return this.type != Type.LONG_TYPE && this.type != Type.DOUBLE_TYPE ? 1 : 2;
   }

   public boolean isReference() {
      return this.type != null && (this.type.getSort() == 10 || this.type.getSort() == 9);
   }

   public boolean equals(Object var1) {
      if (var1 == this) {
         return true;
      } else if (var1 instanceof BasicValue) {
         if (this.type == null) {
            return ((BasicValue)var1).type == null;
         } else {
            return this.type.equals(((BasicValue)var1).type);
         }
      } else {
         return false;
      }
   }

   public int hashCode() {
      return this.type == null ? 0 : this.type.hashCode();
   }

   public String toString() {
      if (this == UNINITIALIZED_VALUE) {
         return ".";
      } else if (this == RETURNADDRESS_VALUE) {
         return "A";
      } else {
         return this == REFERENCE_VALUE ? "R" : this.type.getDescriptor();
      }
   }

   static {
      INT_VALUE = new BasicValue(Type.INT_TYPE);
      FLOAT_VALUE = new BasicValue(Type.FLOAT_TYPE);
      LONG_VALUE = new BasicValue(Type.LONG_TYPE);
      DOUBLE_VALUE = new BasicValue(Type.DOUBLE_TYPE);
      REFERENCE_VALUE = new BasicValue(Type.getObjectType("java/lang/Object"));
      RETURNADDRESS_VALUE = new BasicValue(Type.VOID_TYPE);
   }
}
