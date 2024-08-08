package org.spongepowered.asm.lib.util;

import org.spongepowered.asm.lib.signature.SignatureVisitor;

public class CheckSignatureAdapter extends SignatureVisitor {
   public static final int CLASS_SIGNATURE = 0;
   public static final int METHOD_SIGNATURE = 1;
   public static final int TYPE_SIGNATURE = 2;
   private static final int EMPTY = 1;
   private static final int FORMAL = 2;
   private static final int BOUND = 4;
   private static final int SUPER = 8;
   private static final int PARAM = 16;
   private static final int RETURN = 32;
   private static final int SIMPLE_TYPE = 64;
   private static final int CLASS_TYPE = 128;
   private static final int END = 256;
   private final int type;
   private int state;
   private boolean canBeVoid;
   private final SignatureVisitor sv;

   public CheckSignatureAdapter(int var1, SignatureVisitor var2) {
      this(327680, var1, var2);
   }

   protected CheckSignatureAdapter(int var1, int var2, SignatureVisitor var3) {
      super(var1);
      this.type = var2;
      this.state = 1;
      this.sv = var3;
   }

   public void visitFormalTypeParameter(String var1) {
      if (this.type == 2 || this.state != 1 && this.state != 2 && this.state != 4) {
         throw new IllegalStateException();
      } else {
         CheckMethodAdapter.checkIdentifier(var1, "formal type parameter");
         this.state = 2;
         if (this.sv != null) {
            this.sv.visitFormalTypeParameter(var1);
         }

      }
   }

   public SignatureVisitor visitClassBound() {
      if (this.state != 2) {
         throw new IllegalStateException();
      } else {
         this.state = 4;
         SignatureVisitor var1 = this.sv == null ? null : this.sv.visitClassBound();
         return new CheckSignatureAdapter(2, var1);
      }
   }

   public SignatureVisitor visitInterfaceBound() {
      if (this.state != 2 && this.state != 4) {
         throw new IllegalArgumentException();
      } else {
         SignatureVisitor var1 = this.sv == null ? null : this.sv.visitInterfaceBound();
         return new CheckSignatureAdapter(2, var1);
      }
   }

   public SignatureVisitor visitSuperclass() {
      if (this.type == 0 && (this.state & 7) != 0) {
         this.state = 8;
         SignatureVisitor var1 = this.sv == null ? null : this.sv.visitSuperclass();
         return new CheckSignatureAdapter(2, var1);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public SignatureVisitor visitInterface() {
      if (this.state != 8) {
         throw new IllegalStateException();
      } else {
         SignatureVisitor var1 = this.sv == null ? null : this.sv.visitInterface();
         return new CheckSignatureAdapter(2, var1);
      }
   }

   public SignatureVisitor visitParameterType() {
      if (this.type == 1 && (this.state & 23) != 0) {
         this.state = 16;
         SignatureVisitor var1 = this.sv == null ? null : this.sv.visitParameterType();
         return new CheckSignatureAdapter(2, var1);
      } else {
         throw new IllegalArgumentException();
      }
   }

   public SignatureVisitor visitReturnType() {
      if (this.type == 1 && (this.state & 23) != 0) {
         this.state = 32;
         SignatureVisitor var1 = this.sv == null ? null : this.sv.visitReturnType();
         CheckSignatureAdapter var2 = new CheckSignatureAdapter(2, var1);
         var2.canBeVoid = true;
         return var2;
      } else {
         throw new IllegalArgumentException();
      }
   }

   public SignatureVisitor visitExceptionType() {
      if (this.state != 32) {
         throw new IllegalStateException();
      } else {
         SignatureVisitor var1 = this.sv == null ? null : this.sv.visitExceptionType();
         return new CheckSignatureAdapter(2, var1);
      }
   }

   public void visitBaseType(char var1) {
      if (this.type == 2 && this.state == 1) {
         if (var1 == 'V') {
            if (!this.canBeVoid) {
               throw new IllegalArgumentException();
            }
         } else if ("ZCBSIFJD".indexOf(var1) == -1) {
            throw new IllegalArgumentException();
         }

         this.state = 64;
         if (this.sv != null) {
            this.sv.visitBaseType(var1);
         }

      } else {
         throw new IllegalStateException();
      }
   }

   public void visitTypeVariable(String var1) {
      if (this.type == 2 && this.state == 1) {
         CheckMethodAdapter.checkIdentifier(var1, "type variable");
         this.state = 64;
         if (this.sv != null) {
            this.sv.visitTypeVariable(var1);
         }

      } else {
         throw new IllegalStateException();
      }
   }

   public SignatureVisitor visitArrayType() {
      if (this.type == 2 && this.state == 1) {
         this.state = 64;
         SignatureVisitor var1 = this.sv == null ? null : this.sv.visitArrayType();
         return new CheckSignatureAdapter(2, var1);
      } else {
         throw new IllegalStateException();
      }
   }

   public void visitClassType(String var1) {
      if (this.type == 2 && this.state == 1) {
         CheckMethodAdapter.checkInternalName(var1, "class name");
         this.state = 128;
         if (this.sv != null) {
            this.sv.visitClassType(var1);
         }

      } else {
         throw new IllegalStateException();
      }
   }

   public void visitInnerClassType(String var1) {
      if (this.state != 128) {
         throw new IllegalStateException();
      } else {
         CheckMethodAdapter.checkIdentifier(var1, "inner class name");
         if (this.sv != null) {
            this.sv.visitInnerClassType(var1);
         }

      }
   }

   public void visitTypeArgument() {
      if (this.state != 128) {
         throw new IllegalStateException();
      } else {
         if (this.sv != null) {
            this.sv.visitTypeArgument();
         }

      }
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      if (this.state != 128) {
         throw new IllegalStateException();
      } else if ("+-=".indexOf(var1) == -1) {
         throw new IllegalArgumentException();
      } else {
         SignatureVisitor var2 = this.sv == null ? null : this.sv.visitTypeArgument(var1);
         return new CheckSignatureAdapter(2, var2);
      }
   }

   public void visitEnd() {
      if (this.state != 128) {
         throw new IllegalStateException();
      } else {
         this.state = 256;
         if (this.sv != null) {
            this.sv.visitEnd();
         }

      }
   }
}
