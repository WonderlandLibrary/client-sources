package org.spongepowered.asm.lib.util;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.BasicVerifier;

public class CheckMethodAdapter extends MethodVisitor {
   public int version;
   private int access;
   private boolean startCode;
   private boolean endCode;
   private boolean endMethod;
   private int insnCount;
   private final Map labels;
   private Set usedLabels;
   private int expandedFrames;
   private int compressedFrames;
   private int lastFrame;
   private List handlers;
   private static final int[] TYPE;
   private static Field labelStatusField;

   public CheckMethodAdapter(MethodVisitor var1) {
      this(var1, new HashMap());
   }

   public CheckMethodAdapter(MethodVisitor var1, Map var2) {
      this(327680, var1, var2);
      if (this.getClass() != CheckMethodAdapter.class) {
         throw new IllegalStateException();
      }
   }

   protected CheckMethodAdapter(int var1, MethodVisitor var2, Map var3) {
      super(var1, var2);
      this.lastFrame = -1;
      this.labels = var3;
      this.usedLabels = new HashSet();
      this.handlers = new ArrayList();
   }

   public CheckMethodAdapter(int var1, String var2, String var3, MethodVisitor var4, Map var5) {
      this(new MethodNode(327680, var1, var2, var3, (String)null, (String[])null, var4) {
         final MethodVisitor val$cmv;

         {
            this.val$cmv = var7;
         }

         public void visitEnd() {
            Analyzer var1 = new Analyzer(new BasicVerifier());

            try {
               var1.analyze("dummy", this);
            } catch (Exception var5) {
               if (var5 instanceof IndexOutOfBoundsException && this.maxLocals == 0 && this.maxStack == 0) {
                  throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
               }

               var5.printStackTrace();
               StringWriter var3 = new StringWriter();
               PrintWriter var4 = new PrintWriter(var3, true);
               CheckClassAdapter.printAnalyzerResult(this, var1, var4);
               var4.close();
               throw new RuntimeException(var5.getMessage() + ' ' + var3.toString());
            }

            this.accept(this.val$cmv);
         }
      }, var5);
      this.access = var1;
   }

   public void visitParameter(String var1, int var2) {
      if (var1 != null) {
         checkUnqualifiedName(this.version, var1, "name");
      }

      CheckClassAdapter.checkAccess(var2, 36880);
      super.visitParameter(var1, var2);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      this.checkEndMethod();
      checkDesc(var1, false);
      return new CheckAnnotationAdapter(super.visitAnnotation(var1, var2));
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.checkEndMethod();
      int var5 = var1 >>> 24;
      if (var5 != 1 && var5 != 18 && var5 != 20 && var5 != 21 && var5 != 22 && var5 != 23) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(var5));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(var1, var2);
         checkDesc(var3, false);
         return new CheckAnnotationAdapter(super.visitTypeAnnotation(var1, var2, var3, var4));
      }
   }

   public AnnotationVisitor visitAnnotationDefault() {
      this.checkEndMethod();
      return new CheckAnnotationAdapter(super.visitAnnotationDefault(), false);
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      this.checkEndMethod();
      checkDesc(var2, false);
      return new CheckAnnotationAdapter(super.visitParameterAnnotation(var1, var2, var3));
   }

   public void visitAttribute(Attribute var1) {
      this.checkEndMethod();
      if (var1 == null) {
         throw new IllegalArgumentException("Invalid attribute (must not be null)");
      } else {
         super.visitAttribute(var1);
      }
   }

   public void visitCode() {
      if ((this.access & 1024) != 0) {
         throw new RuntimeException("Abstract methods cannot have code");
      } else {
         this.startCode = true;
         super.visitCode();
      }
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      if (this.insnCount == this.lastFrame) {
         throw new IllegalStateException("At most one frame can be visited at a given code location.");
      } else {
         this.lastFrame = this.insnCount;
         int var6;
         int var7;
         switch(var1) {
         case -1:
         case 0:
            var6 = Integer.MAX_VALUE;
            var7 = Integer.MAX_VALUE;
            break;
         case 1:
         case 2:
            var6 = 3;
            var7 = 0;
            break;
         case 3:
            var6 = 0;
            var7 = 0;
            break;
         case 4:
            var6 = 0;
            var7 = 1;
            break;
         default:
            throw new IllegalArgumentException("Invalid frame type " + var1);
         }

         if (var2 > var6) {
            throw new IllegalArgumentException("Invalid nLocal=" + var2 + " for frame type " + var1);
         } else if (var4 > var7) {
            throw new IllegalArgumentException("Invalid nStack=" + var4 + " for frame type " + var1);
         } else {
            int var8;
            if (var1 != 2) {
               if (var2 > 0 && (var3 == null || var3.length < var2)) {
                  throw new IllegalArgumentException("Array local[] is shorter than nLocal");
               }

               for(var8 = 0; var8 < var2; ++var8) {
                  this.checkFrameValue(var3[var8]);
               }
            }

            if (var4 <= 0 || var5 != null && var5.length >= var4) {
               for(var8 = 0; var8 < var4; ++var8) {
                  this.checkFrameValue(var5[var8]);
               }

               if (var1 == -1) {
                  ++this.expandedFrames;
               } else {
                  ++this.compressedFrames;
               }

               if (this.expandedFrames > 0 && this.compressedFrames > 0) {
                  throw new RuntimeException("Expanded and compressed frames must not be mixed.");
               } else {
                  super.visitFrame(var1, var2, var3, var4, var5);
               }
            } else {
               throw new IllegalArgumentException("Array stack[] is shorter than nStack");
            }
         }
      }
   }

   public void visitInsn(int var1) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 0);
      super.visitInsn(var1);
      ++this.insnCount;
   }

   public void visitIntInsn(int var1, int var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 1);
      switch(var1) {
      case 16:
         checkSignedByte(var2, "Invalid operand");
         break;
      case 17:
         checkSignedShort(var2, "Invalid operand");
         break;
      default:
         if (var2 < 4 || var2 > 11) {
            throw new IllegalArgumentException("Invalid operand (must be an array type code T_...): " + var2);
         }
      }

      super.visitIntInsn(var1, var2);
      ++this.insnCount;
   }

   public void visitVarInsn(int var1, int var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 2);
      checkUnsignedShort(var2, "Invalid variable index");
      super.visitVarInsn(var1, var2);
      ++this.insnCount;
   }

   public void visitTypeInsn(int var1, String var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 3);
      checkInternalName(var2, "type");
      if (var1 == 187 && var2.charAt(0) == '[') {
         throw new IllegalArgumentException("NEW cannot be used to create arrays: " + var2);
      } else {
         super.visitTypeInsn(var1, var2);
         ++this.insnCount;
      }
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 4);
      checkInternalName(var2, "owner");
      checkUnqualifiedName(this.version, var3, "name");
      checkDesc(var4, false);
      super.visitFieldInsn(var1, var2, var3, var4);
      ++this.insnCount;
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      if (this.api >= 327680) {
         super.visitMethodInsn(var1, var2, var3, var4);
      } else {
         this.doVisitMethodInsn(var1, var2, var3, var4, var1 == 185);
      }
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         this.doVisitMethodInsn(var1, var2, var3, var4, var5);
      }
   }

   private void doVisitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 5);
      if (var1 != 183 || !"<init>".equals(var3)) {
         checkMethodIdentifier(this.version, var3, "name");
      }

      checkInternalName(var2, "owner");
      checkMethodDesc(var4);
      if (var1 == 182 && var5) {
         throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
      } else if (var1 == 185 && !var5) {
         throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
      } else if (var1 == 183 && var5 && (this.version & '\uffff') < 52) {
         throw new IllegalArgumentException("INVOKESPECIAL can't be used with interfaces prior to Java 8");
      } else {
         if (this.mv != null) {
            this.mv.visitMethodInsn(var1, var2, var3, var4, var5);
         }

         ++this.insnCount;
      }
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.checkStartCode();
      this.checkEndCode();
      checkMethodIdentifier(this.version, var1, "name");
      checkMethodDesc(var2);
      if (var3.getTag() != 6 && var3.getTag() != 8) {
         throw new IllegalArgumentException("invalid handle tag " + var3.getTag());
      } else {
         for(int var5 = 0; var5 < var4.length; ++var5) {
            this.checkLDCConstant(var4[var5]);
         }

         super.visitInvokeDynamicInsn(var1, var2, var3, var4);
         ++this.insnCount;
      }
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkOpcode(var1, 6);
      this.checkLabel(var2, false, "label");
      checkNonDebugLabel(var2);
      super.visitJumpInsn(var1, var2);
      this.usedLabels.add(var2);
      ++this.insnCount;
   }

   public void visitLabel(Label var1) {
      this.checkStartCode();
      this.checkEndCode();
      this.checkLabel(var1, false, "label");
      if (this.labels.get(var1) != null) {
         throw new IllegalArgumentException("Already visited label");
      } else {
         this.labels.put(var1, this.insnCount);
         super.visitLabel(var1);
      }
   }

   public void visitLdcInsn(Object var1) {
      this.checkStartCode();
      this.checkEndCode();
      this.checkLDCConstant(var1);
      super.visitLdcInsn(var1);
      ++this.insnCount;
   }

   public void visitIincInsn(int var1, int var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkUnsignedShort(var1, "Invalid variable index");
      checkSignedShort(var2, "Invalid increment");
      super.visitIincInsn(var1, var2);
      ++this.insnCount;
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.checkStartCode();
      this.checkEndCode();
      if (var2 < var1) {
         throw new IllegalArgumentException("Max = " + var2 + " must be greater than or equal to min = " + var1);
      } else {
         this.checkLabel(var3, false, "default label");
         checkNonDebugLabel(var3);
         if (var4 != null && var4.length == var2 - var1 + 1) {
            int var5;
            for(var5 = 0; var5 < var4.length; ++var5) {
               this.checkLabel(var4[var5], false, "label at index " + var5);
               checkNonDebugLabel(var4[var5]);
            }

            super.visitTableSwitchInsn(var1, var2, var3, var4);

            for(var5 = 0; var5 < var4.length; ++var5) {
               this.usedLabels.add(var4[var5]);
            }

            ++this.insnCount;
         } else {
            throw new IllegalArgumentException("There must be max - min + 1 labels");
         }
      }
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.checkEndCode();
      this.checkStartCode();
      this.checkLabel(var1, false, "default label");
      checkNonDebugLabel(var1);
      if (var2 != null && var3 != null && var2.length == var3.length) {
         int var4;
         for(var4 = 0; var4 < var3.length; ++var4) {
            this.checkLabel(var3[var4], false, "label at index " + var4);
            checkNonDebugLabel(var3[var4]);
         }

         super.visitLookupSwitchInsn(var1, var2, var3);
         this.usedLabels.add(var1);

         for(var4 = 0; var4 < var3.length; ++var4) {
            this.usedLabels.add(var3[var4]);
         }

         ++this.insnCount;
      } else {
         throw new IllegalArgumentException("There must be the same number of keys and labels");
      }
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkDesc(var1, false);
      if (var1.charAt(0) != '[') {
         throw new IllegalArgumentException("Invalid descriptor (must be an array type descriptor): " + var1);
      } else if (var2 < 1) {
         throw new IllegalArgumentException("Invalid dimensions (must be greater than 0): " + var2);
      } else if (var2 > var1.lastIndexOf(91) + 1) {
         throw new IllegalArgumentException("Invalid dimensions (must not be greater than dims(desc)): " + var2);
      } else {
         super.visitMultiANewArrayInsn(var1, var2);
         ++this.insnCount;
      }
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.checkStartCode();
      this.checkEndCode();
      int var5 = var1 >>> 24;
      if (var5 != 67 && var5 != 68 && var5 != 69 && var5 != 70 && var5 != 71 && var5 != 72 && var5 != 73 && var5 != 74 && var5 != 75) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(var5));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(var1, var2);
         checkDesc(var3, false);
         return new CheckAnnotationAdapter(super.visitInsnAnnotation(var1, var2, var3, var4));
      }
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      this.checkStartCode();
      this.checkEndCode();
      this.checkLabel(var1, false, "start label");
      this.checkLabel(var2, false, "end label");
      this.checkLabel(var3, false, "handler label");
      checkNonDebugLabel(var1);
      checkNonDebugLabel(var2);
      checkNonDebugLabel(var3);
      if (this.labels.get(var1) == null && this.labels.get(var2) == null && this.labels.get(var3) == null) {
         if (var4 != null) {
            checkInternalName(var4, "type");
         }

         super.visitTryCatchBlock(var1, var2, var3, var4);
         this.handlers.add(var1);
         this.handlers.add(var2);
      } else {
         throw new IllegalStateException("Try catch blocks must be visited before their labels");
      }
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      this.checkStartCode();
      this.checkEndCode();
      int var5 = var1 >>> 24;
      if (var5 != 66) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(var5));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(var1, var2);
         checkDesc(var3, false);
         return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(var1, var2, var3, var4));
      }
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      this.checkStartCode();
      this.checkEndCode();
      checkUnqualifiedName(this.version, var1, "name");
      checkDesc(var2, false);
      this.checkLabel(var4, true, "start label");
      this.checkLabel(var5, true, "end label");
      checkUnsignedShort(var6, "Invalid variable index");
      int var7 = (Integer)this.labels.get(var4);
      int var8 = (Integer)this.labels.get(var5);
      if (var8 < var7) {
         throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
      } else {
         super.visitLocalVariable(var1, var2, var3, var4, var5, var6);
      }
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      this.checkStartCode();
      this.checkEndCode();
      int var8 = var1 >>> 24;
      if (var8 != 64 && var8 != 65) {
         throw new IllegalArgumentException("Invalid type reference sort 0x" + Integer.toHexString(var8));
      } else {
         CheckClassAdapter.checkTypeRefAndPath(var1, var2);
         checkDesc(var6, false);
         if (var3 != null && var4 != null && var5 != null && var4.length == var3.length && var5.length == var3.length) {
            for(int var9 = 0; var9 < var3.length; ++var9) {
               this.checkLabel(var3[var9], true, "start label");
               this.checkLabel(var4[var9], true, "end label");
               checkUnsignedShort(var5[var9], "Invalid variable index");
               int var10 = (Integer)this.labels.get(var3[var9]);
               int var11 = (Integer)this.labels.get(var4[var9]);
               if (var11 < var10) {
                  throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
               }
            }

            return super.visitLocalVariableAnnotation(var1, var2, var3, var4, var5, var6, var7);
         } else {
            throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
         }
      }
   }

   public void visitLineNumber(int var1, Label var2) {
      this.checkStartCode();
      this.checkEndCode();
      checkUnsignedShort(var1, "Invalid line number");
      this.checkLabel(var2, true, "start label");
      super.visitLineNumber(var1, var2);
   }

   public void visitMaxs(int var1, int var2) {
      this.checkStartCode();
      this.checkEndCode();
      this.endCode = true;
      Iterator var3 = this.usedLabels.iterator();

      while(var3.hasNext()) {
         Label var4 = (Label)var3.next();
         if (this.labels.get(var4) == null) {
            throw new IllegalStateException("Undefined label used");
         }
      }

      int var6 = 0;

      Integer var5;
      Integer var7;
      do {
         if (var6 >= this.handlers.size()) {
            checkUnsignedShort(var1, "Invalid max stack");
            checkUnsignedShort(var2, "Invalid max locals");
            super.visitMaxs(var1, var2);
            return;
         }

         var7 = (Integer)this.labels.get(this.handlers.get(var6++));
         var5 = (Integer)this.labels.get(this.handlers.get(var6++));
         if (var7 == null || var5 == null) {
            throw new IllegalStateException("Undefined try catch block labels");
         }
      } while(var5 > var7);

      throw new IllegalStateException("Emty try catch block handler range");
   }

   public void visitEnd() {
      this.checkEndMethod();
      this.endMethod = true;
      super.visitEnd();
   }

   void checkStartCode() {
      if (!this.startCode) {
         throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
      }
   }

   void checkEndCode() {
      if (this.endCode) {
         throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
      }
   }

   void checkEndMethod() {
      if (this.endMethod) {
         throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
      }
   }

   void checkFrameValue(Object var1) {
      if (var1 != Opcodes.TOP && var1 != Opcodes.INTEGER && var1 != Opcodes.FLOAT && var1 != Opcodes.LONG && var1 != Opcodes.DOUBLE && var1 != Opcodes.NULL && var1 != Opcodes.UNINITIALIZED_THIS) {
         if (var1 instanceof String) {
            checkInternalName((String)var1, "Invalid stack frame value");
         } else if (!(var1 instanceof Label)) {
            throw new IllegalArgumentException("Invalid stack frame value: " + var1);
         } else {
            this.usedLabels.add((Label)var1);
         }
      }
   }

   static void checkOpcode(int var0, int var1) {
      if (var0 < 0 || var0 > 199 || TYPE[var0] != var1) {
         throw new IllegalArgumentException("Invalid opcode: " + var0);
      }
   }

   static void checkSignedByte(int var0, String var1) {
      if (var0 < -128 || var0 > 127) {
         throw new IllegalArgumentException(var1 + " (must be a signed byte): " + var0);
      }
   }

   static void checkSignedShort(int var0, String var1) {
      if (var0 < -32768 || var0 > 32767) {
         throw new IllegalArgumentException(var1 + " (must be a signed short): " + var0);
      }
   }

   static void checkUnsignedShort(int var0, String var1) {
      if (var0 < 0 || var0 > 65535) {
         throw new IllegalArgumentException(var1 + " (must be an unsigned short): " + var0);
      }
   }

   static void checkConstant(Object var0) {
      if (!(var0 instanceof Integer) && !(var0 instanceof Float) && !(var0 instanceof Long) && !(var0 instanceof Double) && !(var0 instanceof String)) {
         throw new IllegalArgumentException("Invalid constant: " + var0);
      }
   }

   void checkLDCConstant(Object var1) {
      int var2;
      if (var1 instanceof Type) {
         var2 = ((Type)var1).getSort();
         if (var2 != 10 && var2 != 9 && var2 != 11) {
            throw new IllegalArgumentException("Illegal LDC constant value");
         }

         if (var2 != 11 && (this.version & '\uffff') < 49) {
            throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
         }

         if (var2 == 11 && (this.version & '\uffff') < 51) {
            throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
         }
      } else if (var1 instanceof Handle) {
         if ((this.version & '\uffff') < 51) {
            throw new IllegalArgumentException("ldc of a handle requires at least version 1.7");
         }

         var2 = ((Handle)var1).getTag();
         if (var2 < 1 || var2 > 9) {
            throw new IllegalArgumentException("invalid handle tag " + var2);
         }
      } else {
         checkConstant(var1);
      }

   }

   static void checkUnqualifiedName(int var0, String var1, String var2) {
      if ((var0 & '\uffff') < 49) {
         checkIdentifier(var1, var2);
      } else {
         for(int var3 = 0; var3 < var1.length(); ++var3) {
            if (".;[/".indexOf(var1.charAt(var3)) != -1) {
               throw new IllegalArgumentException("Invalid " + var2 + " (must be a valid unqualified name): " + var1);
            }
         }
      }

   }

   static void checkIdentifier(String var0, String var1) {
      checkIdentifier(var0, 0, -1, var1);
   }

   static void checkIdentifier(String var0, int var1, int var2, String var3) {
      label42: {
         if (var0 != null) {
            if (var2 == -1) {
               if (var0.length() > var1) {
                  break label42;
               }
            } else if (var2 > var1) {
               break label42;
            }
         }

         throw new IllegalArgumentException("Invalid " + var3 + " (must not be null or empty)");
      }

      if (!Character.isJavaIdentifierStart(var0.charAt(var1))) {
         throw new IllegalArgumentException("Invalid " + var3 + " (must be a valid Java identifier): " + var0);
      } else {
         int var4 = var2 == -1 ? var0.length() : var2;

         for(int var5 = var1 + 1; var5 < var4; ++var5) {
            if (!Character.isJavaIdentifierPart(var0.charAt(var5))) {
               throw new IllegalArgumentException("Invalid " + var3 + " (must be a valid Java identifier): " + var0);
            }
         }

      }
   }

   static void checkMethodIdentifier(int var0, String var1, String var2) {
      if (var1 != null && var1.length() != 0) {
         int var3;
         if ((var0 & '\uffff') >= 49) {
            for(var3 = 0; var3 < var1.length(); ++var3) {
               if (".;[/<>".indexOf(var1.charAt(var3)) != -1) {
                  throw new IllegalArgumentException("Invalid " + var2 + " (must be a valid unqualified name): " + var1);
               }
            }

         } else if (!Character.isJavaIdentifierStart(var1.charAt(0))) {
            throw new IllegalArgumentException("Invalid " + var2 + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + var1);
         } else {
            for(var3 = 1; var3 < var1.length(); ++var3) {
               if (!Character.isJavaIdentifierPart(var1.charAt(var3))) {
                  throw new IllegalArgumentException("Invalid " + var2 + " (must be '<init>' or '<clinit>' or a valid Java identifier): " + var1);
               }
            }

         }
      } else {
         throw new IllegalArgumentException("Invalid " + var2 + " (must not be null or empty)");
      }
   }

   static void checkInternalName(String var0, String var1) {
      if (var0 != null && var0.length() != 0) {
         if (var0.charAt(0) == '[') {
            checkDesc(var0, false);
         } else {
            checkInternalName(var0, 0, -1, var1);
         }

      } else {
         throw new IllegalArgumentException("Invalid " + var1 + " (must not be null or empty)");
      }
   }

   static void checkInternalName(String var0, int var1, int var2, String var3) {
      int var4 = var2 == -1 ? var0.length() : var2;

      try {
         int var5 = var1;

         int var6;
         do {
            var6 = var0.indexOf(47, var5 + 1);
            if (var6 == -1 || var6 > var4) {
               var6 = var4;
            }

            checkIdentifier(var0, var5, var6, (String)null);
            var5 = var6 + 1;
         } while(var6 != var4);

      } catch (IllegalArgumentException var7) {
         throw new IllegalArgumentException("Invalid " + var3 + " (must be a fully qualified class name in internal form): " + var0);
      }
   }

   static void checkDesc(String var0, boolean var1) {
      int var2 = checkDesc(var0, 0, var1);
      if (var2 != var0.length()) {
         throw new IllegalArgumentException("Invalid descriptor: " + var0);
      }
   }

   static int checkDesc(String var0, int var1, boolean var2) {
      if (var0 != null && var1 < var0.length()) {
         int var3;
         switch(var0.charAt(var1)) {
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
         case 'M':
         case 'N':
         case 'O':
         case 'P':
         case 'Q':
         case 'R':
         case 'T':
         case 'U':
         case 'W':
         case 'X':
         case 'Y':
         default:
            throw new IllegalArgumentException("Invalid descriptor: " + var0);
         case 'L':
            var3 = var0.indexOf(59, var1);
            if (var3 != -1 && var3 - var1 >= 2) {
               try {
                  checkInternalName(var0, var1 + 1, var3, (String)null);
               } catch (IllegalArgumentException var5) {
                  throw new IllegalArgumentException("Invalid descriptor: " + var0);
               }

               return var3 + 1;
            }

            throw new IllegalArgumentException("Invalid descriptor: " + var0);
         case 'V':
            if (var2) {
               return var1 + 1;
            }

            throw new IllegalArgumentException("Invalid descriptor: " + var0);
         case '[':
            for(var3 = var1 + 1; var3 < var0.length() && var0.charAt(var3) == '['; ++var3) {
            }

            if (var3 < var0.length()) {
               return checkDesc(var0, var3, false);
            } else {
               throw new IllegalArgumentException("Invalid descriptor: " + var0);
            }
         }
      } else {
         throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
      }
   }

   static void checkMethodDesc(String var0) {
      if (var0 != null && var0.length() != 0) {
         if (var0.charAt(0) == '(' && var0.length() >= 3) {
            int var1 = 1;
            if (var0.charAt(var1) != ')') {
               do {
                  if (var0.charAt(var1) == 'V') {
                     throw new IllegalArgumentException("Invalid descriptor: " + var0);
                  }

                  var1 = checkDesc(var0, var1, false);
               } while(var1 < var0.length() && var0.charAt(var1) != ')');
            }

            var1 = checkDesc(var0, var1 + 1, true);
            if (var1 != var0.length()) {
               throw new IllegalArgumentException("Invalid descriptor: " + var0);
            }
         } else {
            throw new IllegalArgumentException("Invalid descriptor: " + var0);
         }
      } else {
         throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
      }
   }

   void checkLabel(Label var1, boolean var2, String var3) {
      if (var1 == null) {
         throw new IllegalArgumentException("Invalid " + var3 + " (must not be null)");
      } else if (var2 && this.labels.get(var1) == null) {
         throw new IllegalArgumentException("Invalid " + var3 + " (must be visited first)");
      }
   }

   private static void checkNonDebugLabel(Label var0) {
      Field var1 = getLabelStatusField();
      boolean var2 = false;

      int var5;
      try {
         var5 = var1 == null ? 0 : (Integer)var1.get(var0);
      } catch (IllegalAccessException var4) {
         throw new Error("Internal error");
      }

      if ((var5 & 1) != 0) {
         throw new IllegalArgumentException("Labels used for debug info cannot be reused for control flow");
      }
   }

   private static Field getLabelStatusField() {
      if (labelStatusField == null) {
         labelStatusField = getLabelField("a");
         if (labelStatusField == null) {
            labelStatusField = getLabelField("status");
         }
      }

      return labelStatusField;
   }

   private static Field getLabelField(String var0) {
      try {
         Field var1 = Label.class.getDeclaredField(var0);
         var1.setAccessible(true);
         return var1;
      } catch (NoSuchFieldException var2) {
         return null;
      }
   }

   static {
      String var0 = "BBBBBBBBBBBBBBBBCCIAADDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBDDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJBBBBBBBBBBBBBBBBBBBBHHHHHHHHHHHHHHHHDKLBBBBBBFFFFGGGGAECEBBEEBBAMHHAA";
      TYPE = new int[var0.length()];

      for(int var1 = 0; var1 < TYPE.length; ++var1) {
         TYPE[var1] = var0.charAt(var1) - 65 - 1;
      }

   }
}
