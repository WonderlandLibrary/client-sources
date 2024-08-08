package org.spongepowered.asm.lib.signature;

public abstract class SignatureVisitor {
   public static final char EXTENDS = '+';
   public static final char SUPER = '-';
   public static final char INSTANCEOF = '=';
   protected final int api;

   public SignatureVisitor(int var1) {
      if (var1 != 262144 && var1 != 327680) {
         throw new IllegalArgumentException();
      } else {
         this.api = var1;
      }
   }

   public void visitFormalTypeParameter(String var1) {
   }

   public SignatureVisitor visitClassBound() {
      return this;
   }

   public SignatureVisitor visitInterfaceBound() {
      return this;
   }

   public SignatureVisitor visitSuperclass() {
      return this;
   }

   public SignatureVisitor visitInterface() {
      return this;
   }

   public SignatureVisitor visitParameterType() {
      return this;
   }

   public SignatureVisitor visitReturnType() {
      return this;
   }

   public SignatureVisitor visitExceptionType() {
      return this;
   }

   public void visitBaseType(char var1) {
   }

   public void visitTypeVariable(String var1) {
   }

   public SignatureVisitor visitArrayType() {
      return this;
   }

   public void visitClassType(String var1) {
   }

   public void visitInnerClassType(String var1) {
   }

   public void visitTypeArgument() {
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      return this;
   }

   public void visitEnd() {
   }
}
