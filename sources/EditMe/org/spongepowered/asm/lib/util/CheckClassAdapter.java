package org.spongepowered.asm.lib.util;

import java.io.FileInputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.FieldVisitor;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.lib.tree.analysis.SimpleVerifier;

public class CheckClassAdapter extends ClassVisitor {
   private int version;
   private boolean start;
   private boolean source;
   private boolean outer;
   private boolean end;
   private Map labels;
   private boolean checkDataFlow;

   public static void main(String[] var0) throws Exception {
      if (var0.length != 1) {
         System.err.println("Verifies the given class.");
         System.err.println("Usage: CheckClassAdapter <fully qualified class name or class file name>");
      } else {
         ClassReader var1;
         if (var0[0].endsWith(".class")) {
            var1 = new ClassReader(new FileInputStream(var0[0]));
         } else {
            var1 = new ClassReader(var0[0]);
         }

         verify(var1, false, new PrintWriter(System.err));
      }
   }

   public static void verify(ClassReader var0, ClassLoader var1, boolean var2, PrintWriter var3) {
      ClassNode var4 = new ClassNode();
      var0.accept(new CheckClassAdapter(var4, false), 2);
      Type var5 = var4.superName == null ? null : Type.getObjectType(var4.superName);
      List var6 = var4.methods;
      ArrayList var7 = new ArrayList();
      Iterator var8 = var4.interfaces.iterator();

      while(var8.hasNext()) {
         var7.add(Type.getObjectType((String)var8.next()));
      }

      for(int var14 = 0; var14 < var6.size(); ++var14) {
         MethodNode var9 = (MethodNode)var6.get(var14);
         SimpleVerifier var10 = new SimpleVerifier(Type.getObjectType(var4.name), var5, var7, (var4.access & 512) != 0);
         Analyzer var11 = new Analyzer(var10);
         if (var1 != null) {
            var10.setClassLoader(var1);
         }

         try {
            var11.analyze(var4.name, var9);
            if (!var2) {
               continue;
            }
         } catch (Exception var13) {
            var13.printStackTrace(var3);
         }

         printAnalyzerResult(var9, var11, var3);
      }

      var3.flush();
   }

   public static void verify(ClassReader var0, boolean var1, PrintWriter var2) {
      verify(var0, (ClassLoader)null, var1, var2);
   }

   static void printAnalyzerResult(MethodNode var0, Analyzer var1, PrintWriter var2) {
      Frame[] var3 = var1.getFrames();
      Textifier var4 = new Textifier();
      TraceMethodVisitor var5 = new TraceMethodVisitor(var4);
      var2.println(var0.name + var0.desc);

      int var6;
      for(var6 = 0; var6 < var0.instructions.size(); ++var6) {
         var0.instructions.get(var6).accept(var5);
         StringBuilder var7 = new StringBuilder();
         Frame var8 = var3[var6];
         if (var8 == null) {
            var7.append('?');
         } else {
            int var9;
            for(var9 = 0; var9 < var8.getLocals(); ++var9) {
               var7.append(getShortName(((BasicValue)var8.getLocal(var9)).toString())).append(' ');
            }

            var7.append(" : ");

            for(var9 = 0; var9 < var8.getStackSize(); ++var9) {
               var7.append(getShortName(((BasicValue)var8.getStack(var9)).toString())).append(' ');
            }
         }

         while(var7.length() < var0.maxStack + var0.maxLocals + 1) {
            var7.append(' ');
         }

         var2.print(Integer.toString(var6 + 100000).substring(1));
         var2.print(" " + var7 + " : " + var4.text.get(var4.text.size() - 1));
      }

      for(var6 = 0; var6 < var0.tryCatchBlocks.size(); ++var6) {
         ((TryCatchBlockNode)var0.tryCatchBlocks.get(var6)).accept(var5);
         var2.print(" " + var4.text.get(var4.text.size() - 1));
      }

      var2.println();
   }

   private static String getShortName(String var0) {
      int var1 = var0.lastIndexOf(47);
      int var2 = var0.length();
      if (var0.charAt(var2 - 1) == ';') {
         --var2;
      }

      return var1 == -1 ? var0 : var0.substring(var1 + 1, var2);
   }

   public CheckClassAdapter(ClassVisitor var1) {
      this(var1, true);
   }

   public CheckClassAdapter(ClassVisitor var1, boolean var2) {
      this(327680, var1, var2);
      if (this.getClass() != CheckClassAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckClassAdapter(int var1, ClassVisitor var2, boolean var3) {
      super(var1, var2);
      this.labels = new HashMap();
      this.checkDataFlow = var3;
   }

   public void visit(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      if (this.start) {
         throw new IllegalStateException("visit must be called only once");
      } else {
         this.start = true;
         this.checkState();
         checkAccess(var2, 423473);
         if (var3 == null || !var3.endsWith("package-info")) {
            CheckMethodAdapter.checkInternalName(var3, "class name");
         }

         if ("java/lang/Object".equals(var3)) {
            if (var5 != null) {
               throw new IllegalArgumentException("The super class name of the Object class must be 'null'");
            }
         } else {
            CheckMethodAdapter.checkInternalName(var5, "super class name");
         }

         if (var4 != null) {
            checkClassSignature(var4);
         }

         if ((var2 & 512) != 0 && !"java/lang/Object".equals(var5)) {
            throw new IllegalArgumentException("The super class name of interfaces must be 'java/lang/Object'");
         } else {
            if (var6 != null) {
               for(int var7 = 0; var7 < var6.length; ++var7) {
                  CheckMethodAdapter.checkInternalName(var6[var7], "interface name at index " + var7);
               }
            }

            this.version = var1;
            super.visit(var1, var2, var3, var4, var5, var6);
         }
      }
   }

   public void visitSource(String var1, String var2) {
      this.checkState();
      if (this.source) {
         throw new IllegalStateException("visitSource can be called only once.");
      } else {
         this.source = true;
         super.visitSource(var1, var2);
      }
   }

   public void visitOuterClass(String var1, String var2, String var3) {
      this.checkState();
      if (this.outer) {
         throw new IllegalStateException("visitOuterClass can be called only once.");
      } else {
         this.outer = true;
         if (var1 == null) {
            throw new IllegalArgumentException("Illegal outer class owner");
         } else {
            if (var3 != null) {
               CheckMethodAdapter.checkMethodDesc(var3);
            }

            super.visitOuterClass(var1, var2, var3);
         }
      }
   }

   public void visitInnerClass(String var1, String var2, String var3, int var4) {
      this.checkState();
      CheckMethodAdapter.checkInternalName(var1, "class name");
      if (var2 != null) {
         CheckMethodAdapter.checkInternalName(var2, "outer class name");
      }

      if (var3 != null) {
         int var5;
         for(var5 = 0; var5 < var3.length() && Character.isDigit(var3.charAt(var5)); ++var5) {
         }

         if (var5 == 0 || var5 < var3.length()) {
            CheckMethodAdapter.checkIdentifier(var3, var5, -1, "inner class name");
         }
      }

      checkAccess(var4, 30239);
      super.visitInnerClass(var1, var2, var3, var4);
   }

   public FieldVisitor visitField(int var1, String var2, String var3, String var4, Object var5) {
      this.checkState();
      checkAccess(var1, 413919);
      CheckMethodAdapter.checkUnqualifiedName(this.version, var2, "field name");
      CheckMethodAdapter.checkDesc(var3, false);
      if (var4 != null) {
         checkFieldSignature(var4);
      }

      if (var5 != null) {
         CheckMethodAdapter.checkConstant(var5);
      }

      FieldVisitor var6 = super.visitField(var1, var2, var3, var4, var5);
      return new CheckFieldAdapter(var6);
   }

   public MethodVisitor visitMethod(int var1, String var2, String var3, String var4, String[] var5) {
      this.checkState();
      checkAccess(var1, 400895);
      if (!"<init>".equals(var2) && !"<clinit>".equals(var2)) {
         CheckMethodAdapter.checkMethodIdentifier(this.version, var2, "method name");
      }

      CheckMethodAdapter.checkMethodDesc(var3);
      if (var4 != null) {
         checkMethodSignature(var4);
      }

      if (var5 != null) {
         for(int var6 = 0; var6 < var5.length; ++var6) {
            CheckMethodAdapter.checkInternalName(var5[var6], "exception name at index " + var6);
         }
      }

      CheckMethodAdapter var7;
      if (this.checkDataFlow) {
         var7 = new CheckMethodAdapter(var1, var2, var3, super.visitMethod(var1, var2, var3, var4, var5), this.labels);
      } else {
         var7 = new CheckMethodAdapter(super.visitMethod(var1, var2, var3, var4, var5), this.labels);
      }

      var7.version = this.version;
      return var7;
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      this.checkState();
      CheckMethodAdapter.checkDesc(var1, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(var1, var2));
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.checkState();
      int var5 = var1 >>> 24;
      if (var5 != 0 && var5 != 17 && var5 != 16) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(var5));
      } else {
         checkTypeRefAndPath(var1, var2);
         CheckMethodAdapter.checkDesc(var3, false);
         return new CheckAnnotationAdapter(super.visitTypeAnnotation(var1, var2, var3, var4));
      }
   }

   public void visitAttribute(Attribute var1) {
      this.checkState();
      if (var1 == null) {
         throw new IllegalArgumentException("Invalid attribute (must not be null)");
      } else {
         super.visitAttribute(var1);
      }
   }

   public void visitEnd() {
      this.checkState();
      this.end = true;
      super.visitEnd();
   }

   private void checkState() {
      if (!this.start) {
         throw new IllegalStateException("Cannot visit member before visit has been called.");
      } else if (this.end) {
         throw new IllegalStateException("Cannot visit member after visitEnd has been called.");
      }
   }

   static void checkAccess(int var0, int var1) {
      if ((var0 & ~var1) != 0) {
         throw new IllegalArgumentException("Invalid access flags: " + var0);
      } else {
         int var2 = (var0 & 1) == 0 ? 0 : 1;
         int var3 = (var0 & 2) == 0 ? 0 : 1;
         int var4 = (var0 & 4) == 0 ? 0 : 1;
         if (var2 + var3 + var4 > 1) {
            throw new IllegalArgumentException("public private and protected are mutually exclusive: " + var0);
         } else {
            int var5 = (var0 & 16) == 0 ? 0 : 1;
            int var6 = (var0 & 1024) == 0 ? 0 : 1;
            if (var5 + var6 > 1) {
               throw new IllegalArgumentException("final and abstract are mutually exclusive: " + var0);
            }
         }
      }
   }

   public static void checkClassSignature(String var0) {
      int var1 = 0;
      if (getChar(var0, 0) == '<') {
         var1 = checkFormalTypeParameters(var0, var1);
      }

      for(var1 = checkClassTypeSignature(var0, var1); getChar(var0, var1) == 'L'; var1 = checkClassTypeSignature(var0, var1)) {
      }

      if (var1 != var0.length()) {
         throw new IllegalArgumentException(var0 + ": error at index " + var1);
      }
   }

   public static void checkMethodSignature(String var0) {
      int var1 = 0;
      if (getChar(var0, 0) == '<') {
         var1 = checkFormalTypeParameters(var0, var1);
      }

      for(var1 = checkChar('(', var0, var1); "ZCBSIFJDL[T".indexOf(getChar(var0, var1)) != -1; var1 = checkTypeSignature(var0, var1)) {
      }

      var1 = checkChar(')', var0, var1);
      if (getChar(var0, var1) == 'V') {
         ++var1;
      } else {
         var1 = checkTypeSignature(var0, var1);
      }

      while(getChar(var0, var1) == '^') {
         ++var1;
         if (getChar(var0, var1) == 'L') {
            var1 = checkClassTypeSignature(var0, var1);
         } else {
            var1 = checkTypeVariableSignature(var0, var1);
         }
      }

      if (var1 != var0.length()) {
         throw new IllegalArgumentException(var0 + ": error at index " + var1);
      }
   }

   public static void checkFieldSignature(String var0) {
      int var1 = checkFieldTypeSignature(var0, 0);
      if (var1 != var0.length()) {
         throw new IllegalArgumentException(var0 + ": error at index " + var1);
      }
   }

   static void checkTypeRefAndPath(int var0, TypePath var1) {
      boolean var2 = false;
      int var5;
      switch(var0 >>> 24) {
      case 0:
      case 1:
      case 22:
         var5 = -65536;
         break;
      case 2:
      case 3:
      case 4:
      case 5:
      case 6:
      case 7:
      case 8:
      case 9:
      case 10:
      case 11:
      case 12:
      case 13:
      case 14:
      case 15:
      case 24:
      case 25:
      case 26:
      case 27:
      case 28:
      case 29:
      case 30:
      case 31:
      case 32:
      case 33:
      case 34:
      case 35:
      case 36:
      case 37:
      case 38:
      case 39:
      case 40:
      case 41:
      case 42:
      case 43:
      case 44:
      case 45:
      case 46:
      case 47:
      case 48:
      case 49:
      case 50:
      case 51:
      case 52:
      case 53:
      case 54:
      case 55:
      case 56:
      case 57:
      case 58:
      case 59:
      case 60:
      case 61:
      case 62:
      case 63:
      default:
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(var0 >>> 24));
      case 16:
      case 17:
      case 18:
      case 23:
      case 66:
         var5 = -256;
         break;
      case 19:
      case 20:
      case 21:
      case 64:
      case 65:
      case 67:
      case 68:
      case 69:
      case 70:
         var5 = -16777216;
         break;
      case 71:
      case 72:
      case 73:
      case 74:
      case 75:
         var5 = -16776961;
      }

      if ((var0 & ~var5) != 0) {
         throw new IllegalArgumentException("Invalid type reference 0x" + Integer.toHexString(var0));
      } else {
         if (var1 != null) {
            for(int var3 = 0; var3 < var1.getLength(); ++var3) {
               int var4 = var1.getStep(var3);
               if (var4 != 0 && var4 != 1 && var4 != 3 && var4 != 2) {
                  throw new IllegalArgumentException("Invalid type path step " + var3 + " in " + var1);
               }

               if (var4 != 3 && var1.getStepArgument(var3) != 0) {
                  throw new IllegalArgumentException("Invalid type path step argument for step " + var3 + " in " + var1);
               }
            }
         }

      }
   }

   private static int checkFormalTypeParameters(String var0, int var1) {
      var1 = checkChar('<', var0, var1);

      for(var1 = checkFormalTypeParameter(var0, var1); getChar(var0, var1) != '>'; var1 = checkFormalTypeParameter(var0, var1)) {
      }

      return var1 + 1;
   }

   private static int checkFormalTypeParameter(String var0, int var1) {
      var1 = checkIdentifier(var0, var1);
      var1 = checkChar(':', var0, var1);
      if ("L[T".indexOf(getChar(var0, var1)) != -1) {
         var1 = checkFieldTypeSignature(var0, var1);
      }

      while(getChar(var0, var1) == ':') {
         var1 = checkFieldTypeSignature(var0, var1 + 1);
      }

      return var1;
   }

   private static int checkFieldTypeSignature(String var0, int var1) {
      switch(getChar(var0, var1)) {
      case 'L':
         return checkClassTypeSignature(var0, var1);
      case '[':
         return checkTypeSignature(var0, var1 + 1);
      default:
         return checkTypeVariableSignature(var0, var1);
      }
   }

   private static int checkClassTypeSignature(String var0, int var1) {
      var1 = checkChar('L', var0, var1);

      for(var1 = checkIdentifier(var0, var1); getChar(var0, var1) == '/'; var1 = checkIdentifier(var0, var1 + 1)) {
      }

      if (getChar(var0, var1) == '<') {
         var1 = checkTypeArguments(var0, var1);
      }

      while(getChar(var0, var1) == '.') {
         var1 = checkIdentifier(var0, var1 + 1);
         if (getChar(var0, var1) == '<') {
            var1 = checkTypeArguments(var0, var1);
         }
      }

      return checkChar(';', var0, var1);
   }

   private static int checkTypeArguments(String var0, int var1) {
      var1 = checkChar('<', var0, var1);

      for(var1 = checkTypeArgument(var0, var1); getChar(var0, var1) != '>'; var1 = checkTypeArgument(var0, var1)) {
      }

      return var1 + 1;
   }

   private static int checkTypeArgument(String var0, int var1) {
      char var2 = getChar(var0, var1);
      if (var2 == '*') {
         return var1 + 1;
      } else {
         if (var2 == '+' || var2 == '-') {
            ++var1;
         }

         return checkFieldTypeSignature(var0, var1);
      }
   }

   private static int checkTypeVariableSignature(String var0, int var1) {
      var1 = checkChar('T', var0, var1);
      var1 = checkIdentifier(var0, var1);
      return checkChar(';', var0, var1);
   }

   private static int checkTypeSignature(String var0, int var1) {
      switch(getChar(var0, var1)) {
      case 'B':
      case 'C':
      case 'D':
      case 'F':
      case 'I':
      case 'J':
      case 'S':
      case 'Z':
         return var1 + 1;
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
      case 'T':
      case 'U':
      case 'V':
      case 'W':
      case 'X':
      case 'Y':
      default:
         return checkFieldTypeSignature(var0, var1);
      }
   }

   private static int checkIdentifier(String var0, int var1) {
      if (!Character.isJavaIdentifierStart(getChar(var0, var1))) {
         throw new IllegalArgumentException(var0 + ": identifier expected at index " + var1);
      } else {
         ++var1;

         while(Character.isJavaIdentifierPart(getChar(var0, var1))) {
            ++var1;
         }

         return var1;
      }
   }

   private static int checkChar(char var0, String var1, int var2) {
      if (getChar(var1, var2) == var0) {
         return var2 + 1;
      } else {
         throw new IllegalArgumentException(var1 + ": '" + var0 + "' expected at index " + var2);
      }
   }

   private static char getChar(String var0, int var1) {
      return var1 < var0.length() ? var0.charAt(var1) : '\u0000';
   }
}
