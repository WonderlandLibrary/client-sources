package org.spongepowered.asm.lib.signature;

public class SignatureWriter extends SignatureVisitor {
   private final StringBuilder buf = new StringBuilder();
   private boolean hasFormals;
   private boolean hasParameters;
   private int argumentStack;

   public SignatureWriter() {
      super(327680);
   }

   public void visitFormalTypeParameter(String var1) {
      if (!this.hasFormals) {
         this.hasFormals = true;
         this.buf.append('<');
      }

      this.buf.append(var1);
      this.buf.append(':');
   }

   public SignatureVisitor visitClassBound() {
      return this;
   }

   public SignatureVisitor visitInterfaceBound() {
      this.buf.append(':');
      return this;
   }

   public SignatureVisitor visitSuperclass() {
      this.endFormals();
      return this;
   }

   public SignatureVisitor visitInterface() {
      return this;
   }

   public SignatureVisitor visitParameterType() {
      this.endFormals();
      if (!this.hasParameters) {
         this.hasParameters = true;
         this.buf.append('(');
      }

      return this;
   }

   public SignatureVisitor visitReturnType() {
      this.endFormals();
      if (!this.hasParameters) {
         this.buf.append('(');
      }

      this.buf.append(')');
      return this;
   }

   public SignatureVisitor visitExceptionType() {
      this.buf.append('^');
      return this;
   }

   public void visitBaseType(char var1) {
      this.buf.append(var1);
   }

   public void visitTypeVariable(String var1) {
      this.buf.append('T');
      this.buf.append(var1);
      this.buf.append(';');
   }

   public SignatureVisitor visitArrayType() {
      this.buf.append('[');
      return this;
   }

   public void visitClassType(String var1) {
      this.buf.append('L');
      this.buf.append(var1);
      this.argumentStack *= 2;
   }

   public void visitInnerClassType(String var1) {
      this.endArguments();
      this.buf.append('.');
      this.buf.append(var1);
      this.argumentStack *= 2;
   }

   public void visitTypeArgument() {
      if (this.argumentStack % 2 == 0) {
         ++this.argumentStack;
         this.buf.append('<');
      }

      this.buf.append('*');
   }

   public SignatureVisitor visitTypeArgument(char var1) {
      if (this.argumentStack % 2 == 0) {
         ++this.argumentStack;
         this.buf.append('<');
      }

      if (var1 != '=') {
         this.buf.append(var1);
      }

      return this;
   }

   public void visitEnd() {
      this.endArguments();
      this.buf.append(';');
   }

   public String toString() {
      return this.buf.toString();
   }

   private void endFormals() {
      if (this.hasFormals) {
         this.hasFormals = false;
         this.buf.append('>');
      }

   }

   private void endArguments() {
      if (this.argumentStack % 2 != 0) {
         this.buf.append('>');
      }

      this.argumentStack /= 2;
   }
}
