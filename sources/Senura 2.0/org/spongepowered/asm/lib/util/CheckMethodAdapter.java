/*      */ package org.spongepowered.asm.lib.util;
/*      */ 
/*      */ import java.io.PrintWriter;
/*      */ import java.io.StringWriter;
/*      */ import java.lang.reflect.Field;
/*      */ import java.util.ArrayList;
/*      */ import java.util.HashMap;
/*      */ import java.util.HashSet;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Set;
/*      */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*      */ import org.spongepowered.asm.lib.Attribute;
/*      */ import org.spongepowered.asm.lib.Handle;
/*      */ import org.spongepowered.asm.lib.Label;
/*      */ import org.spongepowered.asm.lib.MethodVisitor;
/*      */ import org.spongepowered.asm.lib.Opcodes;
/*      */ import org.spongepowered.asm.lib.Type;
/*      */ import org.spongepowered.asm.lib.TypePath;
/*      */ import org.spongepowered.asm.lib.tree.MethodNode;
/*      */ import org.spongepowered.asm.lib.tree.analysis.Analyzer;
/*      */ import org.spongepowered.asm.lib.tree.analysis.BasicValue;
/*      */ import org.spongepowered.asm.lib.tree.analysis.BasicVerifier;
/*      */ import org.spongepowered.asm.lib.tree.analysis.Interpreter;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CheckMethodAdapter
/*      */   extends MethodVisitor
/*      */ {
/*      */   public int version;
/*      */   private int access;
/*      */   private boolean startCode;
/*      */   private boolean endCode;
/*      */   private boolean endMethod;
/*      */   private int insnCount;
/*      */   private final Map<Label, Integer> labels;
/*      */   private Set<Label> usedLabels;
/*      */   private int expandedFrames;
/*      */   private int compressedFrames;
/*  129 */   private int lastFrame = -1;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private List<Label> handlers;
/*      */ 
/*      */ 
/*      */   
/*      */   private static final int[] TYPE;
/*      */ 
/*      */ 
/*      */   
/*      */   private static Field labelStatusField;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static {
/*  148 */     String s = "BBBBBBBBBBBBBBBBCCIAADDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBDDDDDAAAAAAAAAAAAAAAAAAAABBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBBJBBBBBBBBBBBBBBBBBBBBHHHHHHHHHHHHHHHHDKLBBBBBBFFFFGGGGAECEBBEEBBAMHHAA";
/*      */ 
/*      */ 
/*      */     
/*  152 */     TYPE = new int[s.length()];
/*  153 */     for (int i = 0; i < TYPE.length; i++) {
/*  154 */       TYPE[i] = s.charAt(i) - 65 - 1;
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CheckMethodAdapter(MethodVisitor mv) {
/*  381 */     this(mv, new HashMap<Label, Integer>());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CheckMethodAdapter(MethodVisitor mv, Map<Label, Integer> labels) {
/*  400 */     this(327680, mv, labels);
/*  401 */     if (getClass() != CheckMethodAdapter.class) {
/*  402 */       throw new IllegalStateException();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   protected CheckMethodAdapter(int api, MethodVisitor mv, Map<Label, Integer> labels) {
/*  421 */     super(api, mv);
/*  422 */     this.labels = labels;
/*  423 */     this.usedLabels = new HashSet<Label>();
/*  424 */     this.handlers = new ArrayList<Label>();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public CheckMethodAdapter(int access, String name, String desc, MethodVisitor cmv, Map<Label, Integer> labels) {
/*  447 */     this((MethodVisitor)new MethodNode(327680, access, name, desc, null, null, cmv)
/*      */         {
/*      */           public void visitEnd() {
/*  450 */             Analyzer<BasicValue> a = new Analyzer((Interpreter)new BasicVerifier());
/*      */             
/*      */             try {
/*  453 */               a.analyze("dummy", this);
/*  454 */             } catch (Exception e) {
/*  455 */               if (e instanceof IndexOutOfBoundsException && this.maxLocals == 0 && this.maxStack == 0)
/*      */               {
/*  457 */                 throw new RuntimeException("Data flow checking option requires valid, non zero maxLocals and maxStack values.");
/*      */               }
/*      */               
/*  460 */               e.printStackTrace();
/*  461 */               StringWriter sw = new StringWriter();
/*  462 */               PrintWriter pw = new PrintWriter(sw, true);
/*  463 */               CheckClassAdapter.printAnalyzerResult(this, a, pw);
/*  464 */               pw.close();
/*  465 */               throw new RuntimeException(e.getMessage() + ' ' + sw
/*  466 */                   .toString());
/*      */             } 
/*  468 */             accept(cmv);
/*      */           }
/*      */         }labels);
/*  471 */     this.access = access;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitParameter(String name, int access) {
/*  476 */     if (name != null) {
/*  477 */       checkUnqualifiedName(this.version, name, "name");
/*      */     }
/*  479 */     CheckClassAdapter.checkAccess(access, 36880);
/*      */     
/*  481 */     super.visitParameter(name, access);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  487 */     checkEndMethod();
/*  488 */     checkDesc(desc, false);
/*  489 */     return new CheckAnnotationAdapter(super.visitAnnotation(desc, visible));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  495 */     checkEndMethod();
/*  496 */     int sort = typeRef >>> 24;
/*  497 */     if (sort != 1 && sort != 18 && sort != 20 && sort != 21 && sort != 22 && sort != 23)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  503 */       throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/*  504 */           Integer.toHexString(sort));
/*      */     }
/*  506 */     CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
/*  507 */     checkDesc(desc, false);
/*  508 */     return new CheckAnnotationAdapter(super.visitTypeAnnotation(typeRef, typePath, desc, visible));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotationDefault() {
/*  514 */     checkEndMethod();
/*  515 */     return new CheckAnnotationAdapter(super.visitAnnotationDefault(), false);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
/*  521 */     checkEndMethod();
/*  522 */     checkDesc(desc, false);
/*  523 */     return new CheckAnnotationAdapter(super.visitParameterAnnotation(parameter, desc, visible));
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitAttribute(Attribute attr) {
/*  529 */     checkEndMethod();
/*  530 */     if (attr == null) {
/*  531 */       throw new IllegalArgumentException("Invalid attribute (must not be null)");
/*      */     }
/*      */     
/*  534 */     super.visitAttribute(attr);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitCode() {
/*  539 */     if ((this.access & 0x400) != 0) {
/*  540 */       throw new RuntimeException("Abstract methods cannot have code");
/*      */     }
/*  542 */     this.startCode = true;
/*  543 */     super.visitCode();
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/*      */     int mLocal, mStack;
/*  549 */     if (this.insnCount == this.lastFrame) {
/*  550 */       throw new IllegalStateException("At most one frame can be visited at a given code location.");
/*      */     }
/*      */     
/*  553 */     this.lastFrame = this.insnCount;
/*      */ 
/*      */     
/*  556 */     switch (type) {
/*      */       case -1:
/*      */       case 0:
/*  559 */         mLocal = Integer.MAX_VALUE;
/*  560 */         mStack = Integer.MAX_VALUE;
/*      */         break;
/*      */       
/*      */       case 3:
/*  564 */         mLocal = 0;
/*  565 */         mStack = 0;
/*      */         break;
/*      */       
/*      */       case 4:
/*  569 */         mLocal = 0;
/*  570 */         mStack = 1;
/*      */         break;
/*      */       
/*      */       case 1:
/*      */       case 2:
/*  575 */         mLocal = 3;
/*  576 */         mStack = 0;
/*      */         break;
/*      */       
/*      */       default:
/*  580 */         throw new IllegalArgumentException("Invalid frame type " + type);
/*      */     } 
/*      */     
/*  583 */     if (nLocal > mLocal) {
/*  584 */       throw new IllegalArgumentException("Invalid nLocal=" + nLocal + " for frame type " + type);
/*      */     }
/*      */     
/*  587 */     if (nStack > mStack) {
/*  588 */       throw new IllegalArgumentException("Invalid nStack=" + nStack + " for frame type " + type);
/*      */     }
/*      */ 
/*      */     
/*  592 */     if (type != 2) {
/*  593 */       if (nLocal > 0 && (local == null || local.length < nLocal)) {
/*  594 */         throw new IllegalArgumentException("Array local[] is shorter than nLocal");
/*      */       }
/*      */       
/*  597 */       for (int j = 0; j < nLocal; j++) {
/*  598 */         checkFrameValue(local[j]);
/*      */       }
/*      */     } 
/*  601 */     if (nStack > 0 && (stack == null || stack.length < nStack)) {
/*  602 */       throw new IllegalArgumentException("Array stack[] is shorter than nStack");
/*      */     }
/*      */     
/*  605 */     for (int i = 0; i < nStack; i++) {
/*  606 */       checkFrameValue(stack[i]);
/*      */     }
/*  608 */     if (type == -1) {
/*  609 */       this.expandedFrames++;
/*      */     } else {
/*  611 */       this.compressedFrames++;
/*      */     } 
/*  613 */     if (this.expandedFrames > 0 && this.compressedFrames > 0) {
/*  614 */       throw new RuntimeException("Expanded and compressed frames must not be mixed.");
/*      */     }
/*      */     
/*  617 */     super.visitFrame(type, nLocal, local, nStack, stack);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitInsn(int opcode) {
/*  622 */     checkStartCode();
/*  623 */     checkEndCode();
/*  624 */     checkOpcode(opcode, 0);
/*  625 */     super.visitInsn(opcode);
/*  626 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIntInsn(int opcode, int operand) {
/*  631 */     checkStartCode();
/*  632 */     checkEndCode();
/*  633 */     checkOpcode(opcode, 1);
/*  634 */     switch (opcode) {
/*      */       case 16:
/*  636 */         checkSignedByte(operand, "Invalid operand");
/*      */         break;
/*      */       case 17:
/*  639 */         checkSignedShort(operand, "Invalid operand");
/*      */         break;
/*      */       
/*      */       default:
/*  643 */         if (operand < 4 || operand > 11) {
/*  644 */           throw new IllegalArgumentException("Invalid operand (must be an array type code T_...): " + operand);
/*      */         }
/*      */         break;
/*      */     } 
/*      */     
/*  649 */     super.visitIntInsn(opcode, operand);
/*  650 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitVarInsn(int opcode, int var) {
/*  655 */     checkStartCode();
/*  656 */     checkEndCode();
/*  657 */     checkOpcode(opcode, 2);
/*  658 */     checkUnsignedShort(var, "Invalid variable index");
/*  659 */     super.visitVarInsn(opcode, var);
/*  660 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitTypeInsn(int opcode, String type) {
/*  665 */     checkStartCode();
/*  666 */     checkEndCode();
/*  667 */     checkOpcode(opcode, 3);
/*  668 */     checkInternalName(type, "type");
/*  669 */     if (opcode == 187 && type.charAt(0) == '[') {
/*  670 */       throw new IllegalArgumentException("NEW cannot be used to create arrays: " + type);
/*      */     }
/*      */     
/*  673 */     super.visitTypeInsn(opcode, type);
/*  674 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/*  680 */     checkStartCode();
/*  681 */     checkEndCode();
/*  682 */     checkOpcode(opcode, 4);
/*  683 */     checkInternalName(owner, "owner");
/*  684 */     checkUnqualifiedName(this.version, name, "name");
/*  685 */     checkDesc(desc, false);
/*  686 */     super.visitFieldInsn(opcode, owner, name, desc);
/*  687 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   @Deprecated
/*      */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/*  694 */     if (this.api >= 327680) {
/*  695 */       super.visitMethodInsn(opcode, owner, name, desc);
/*      */       return;
/*      */     } 
/*  698 */     doVisitMethodInsn(opcode, owner, name, desc, (opcode == 185));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/*  705 */     if (this.api < 327680) {
/*  706 */       super.visitMethodInsn(opcode, owner, name, desc, itf);
/*      */       return;
/*      */     } 
/*  709 */     doVisitMethodInsn(opcode, owner, name, desc, itf);
/*      */   }
/*      */ 
/*      */   
/*      */   private void doVisitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/*  714 */     checkStartCode();
/*  715 */     checkEndCode();
/*  716 */     checkOpcode(opcode, 5);
/*  717 */     if (opcode != 183 || !"<init>".equals(name)) {
/*  718 */       checkMethodIdentifier(this.version, name, "name");
/*      */     }
/*  720 */     checkInternalName(owner, "owner");
/*  721 */     checkMethodDesc(desc);
/*  722 */     if (opcode == 182 && itf) {
/*  723 */       throw new IllegalArgumentException("INVOKEVIRTUAL can't be used with interfaces");
/*      */     }
/*      */     
/*  726 */     if (opcode == 185 && !itf) {
/*  727 */       throw new IllegalArgumentException("INVOKEINTERFACE can't be used with classes");
/*      */     }
/*      */     
/*  730 */     if (opcode == 183 && itf && (this.version & 0xFFFF) < 52)
/*      */     {
/*  732 */       throw new IllegalArgumentException("INVOKESPECIAL can't be used with interfaces prior to Java 8");
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  740 */     if (this.mv != null) {
/*  741 */       this.mv.visitMethodInsn(opcode, owner, name, desc, itf);
/*      */     }
/*  743 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
/*  749 */     checkStartCode();
/*  750 */     checkEndCode();
/*  751 */     checkMethodIdentifier(this.version, name, "name");
/*  752 */     checkMethodDesc(desc);
/*  753 */     if (bsm.getTag() != 6 && bsm
/*  754 */       .getTag() != 8) {
/*  755 */       throw new IllegalArgumentException("invalid handle tag " + bsm
/*  756 */           .getTag());
/*      */     }
/*  758 */     for (int i = 0; i < bsmArgs.length; i++) {
/*  759 */       checkLDCConstant(bsmArgs[i]);
/*      */     }
/*  761 */     super.visitInvokeDynamicInsn(name, desc, bsm, bsmArgs);
/*  762 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitJumpInsn(int opcode, Label label) {
/*  767 */     checkStartCode();
/*  768 */     checkEndCode();
/*  769 */     checkOpcode(opcode, 6);
/*  770 */     checkLabel(label, false, "label");
/*  771 */     checkNonDebugLabel(label);
/*  772 */     super.visitJumpInsn(opcode, label);
/*  773 */     this.usedLabels.add(label);
/*  774 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLabel(Label label) {
/*  779 */     checkStartCode();
/*  780 */     checkEndCode();
/*  781 */     checkLabel(label, false, "label");
/*  782 */     if (this.labels.get(label) != null) {
/*  783 */       throw new IllegalArgumentException("Already visited label");
/*      */     }
/*  785 */     this.labels.put(label, Integer.valueOf(this.insnCount));
/*  786 */     super.visitLabel(label);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLdcInsn(Object cst) {
/*  791 */     checkStartCode();
/*  792 */     checkEndCode();
/*  793 */     checkLDCConstant(cst);
/*  794 */     super.visitLdcInsn(cst);
/*  795 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIincInsn(int var, int increment) {
/*  800 */     checkStartCode();
/*  801 */     checkEndCode();
/*  802 */     checkUnsignedShort(var, "Invalid variable index");
/*  803 */     checkSignedShort(increment, "Invalid increment");
/*  804 */     super.visitIincInsn(var, increment);
/*  805 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
/*  811 */     checkStartCode();
/*  812 */     checkEndCode();
/*  813 */     if (max < min) {
/*  814 */       throw new IllegalArgumentException("Max = " + max + " must be greater than or equal to min = " + min);
/*      */     }
/*      */     
/*  817 */     checkLabel(dflt, false, "default label");
/*  818 */     checkNonDebugLabel(dflt);
/*  819 */     if (labels == null || labels.length != max - min + 1) {
/*  820 */       throw new IllegalArgumentException("There must be max - min + 1 labels");
/*      */     }
/*      */     int i;
/*  823 */     for (i = 0; i < labels.length; i++) {
/*  824 */       checkLabel(labels[i], false, "label at index " + i);
/*  825 */       checkNonDebugLabel(labels[i]);
/*      */     } 
/*  827 */     super.visitTableSwitchInsn(min, max, dflt, labels);
/*  828 */     for (i = 0; i < labels.length; i++) {
/*  829 */       this.usedLabels.add(labels[i]);
/*      */     }
/*  831 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/*  837 */     checkEndCode();
/*  838 */     checkStartCode();
/*  839 */     checkLabel(dflt, false, "default label");
/*  840 */     checkNonDebugLabel(dflt);
/*  841 */     if (keys == null || labels == null || keys.length != labels.length) {
/*  842 */       throw new IllegalArgumentException("There must be the same number of keys and labels");
/*      */     }
/*      */     int i;
/*  845 */     for (i = 0; i < labels.length; i++) {
/*  846 */       checkLabel(labels[i], false, "label at index " + i);
/*  847 */       checkNonDebugLabel(labels[i]);
/*      */     } 
/*  849 */     super.visitLookupSwitchInsn(dflt, keys, labels);
/*  850 */     this.usedLabels.add(dflt);
/*  851 */     for (i = 0; i < labels.length; i++) {
/*  852 */       this.usedLabels.add(labels[i]);
/*      */     }
/*  854 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMultiANewArrayInsn(String desc, int dims) {
/*  859 */     checkStartCode();
/*  860 */     checkEndCode();
/*  861 */     checkDesc(desc, false);
/*  862 */     if (desc.charAt(0) != '[') {
/*  863 */       throw new IllegalArgumentException("Invalid descriptor (must be an array type descriptor): " + desc);
/*      */     }
/*      */ 
/*      */     
/*  867 */     if (dims < 1) {
/*  868 */       throw new IllegalArgumentException("Invalid dimensions (must be greater than 0): " + dims);
/*      */     }
/*      */     
/*  871 */     if (dims > desc.lastIndexOf('[') + 1) {
/*  872 */       throw new IllegalArgumentException("Invalid dimensions (must not be greater than dims(desc)): " + dims);
/*      */     }
/*      */ 
/*      */     
/*  876 */     super.visitMultiANewArrayInsn(desc, dims);
/*  877 */     this.insnCount++;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  883 */     checkStartCode();
/*  884 */     checkEndCode();
/*  885 */     int sort = typeRef >>> 24;
/*  886 */     if (sort != 67 && sort != 68 && sort != 69 && sort != 70 && sort != 71 && sort != 72 && sort != 73 && sort != 74 && sort != 75)
/*      */     {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  894 */       throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/*  895 */           Integer.toHexString(sort));
/*      */     }
/*  897 */     CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
/*  898 */     checkDesc(desc, false);
/*  899 */     return new CheckAnnotationAdapter(super.visitInsnAnnotation(typeRef, typePath, desc, visible));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/*  906 */     checkStartCode();
/*  907 */     checkEndCode();
/*  908 */     checkLabel(start, false, "start label");
/*  909 */     checkLabel(end, false, "end label");
/*  910 */     checkLabel(handler, false, "handler label");
/*  911 */     checkNonDebugLabel(start);
/*  912 */     checkNonDebugLabel(end);
/*  913 */     checkNonDebugLabel(handler);
/*  914 */     if (this.labels.get(start) != null || this.labels.get(end) != null || this.labels
/*  915 */       .get(handler) != null) {
/*  916 */       throw new IllegalStateException("Try catch blocks must be visited before their labels");
/*      */     }
/*      */     
/*  919 */     if (type != null) {
/*  920 */       checkInternalName(type, "type");
/*      */     }
/*  922 */     super.visitTryCatchBlock(start, end, handler, type);
/*  923 */     this.handlers.add(start);
/*  924 */     this.handlers.add(end);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  930 */     checkStartCode();
/*  931 */     checkEndCode();
/*  932 */     int sort = typeRef >>> 24;
/*  933 */     if (sort != 66) {
/*  934 */       throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/*  935 */           Integer.toHexString(sort));
/*      */     }
/*  937 */     CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
/*  938 */     checkDesc(desc, false);
/*  939 */     return new CheckAnnotationAdapter(super.visitTryCatchAnnotation(typeRef, typePath, desc, visible));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/*  947 */     checkStartCode();
/*  948 */     checkEndCode();
/*  949 */     checkUnqualifiedName(this.version, name, "name");
/*  950 */     checkDesc(desc, false);
/*  951 */     checkLabel(start, true, "start label");
/*  952 */     checkLabel(end, true, "end label");
/*  953 */     checkUnsignedShort(index, "Invalid variable index");
/*  954 */     int s = ((Integer)this.labels.get(start)).intValue();
/*  955 */     int e = ((Integer)this.labels.get(end)).intValue();
/*  956 */     if (e < s) {
/*  957 */       throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
/*      */     }
/*      */     
/*  960 */     super.visitLocalVariable(name, desc, signature, start, end, index);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
/*  967 */     checkStartCode();
/*  968 */     checkEndCode();
/*  969 */     int sort = typeRef >>> 24;
/*  970 */     if (sort != 64 && sort != 65)
/*      */     {
/*  972 */       throw new IllegalArgumentException("Invalid type reference sort 0x" + 
/*  973 */           Integer.toHexString(sort));
/*      */     }
/*  975 */     CheckClassAdapter.checkTypeRefAndPath(typeRef, typePath);
/*  976 */     checkDesc(desc, false);
/*  977 */     if (start == null || end == null || index == null || end.length != start.length || index.length != start.length)
/*      */     {
/*  979 */       throw new IllegalArgumentException("Invalid start, end and index arrays (must be non null and of identical length");
/*      */     }
/*      */     
/*  982 */     for (int i = 0; i < start.length; i++) {
/*  983 */       checkLabel(start[i], true, "start label");
/*  984 */       checkLabel(end[i], true, "end label");
/*  985 */       checkUnsignedShort(index[i], "Invalid variable index");
/*  986 */       int s = ((Integer)this.labels.get(start[i])).intValue();
/*  987 */       int e = ((Integer)this.labels.get(end[i])).intValue();
/*  988 */       if (e < s) {
/*  989 */         throw new IllegalArgumentException("Invalid start and end labels (end must be greater than start)");
/*      */       }
/*      */     } 
/*      */     
/*  993 */     return super.visitLocalVariableAnnotation(typeRef, typePath, start, end, index, desc, visible);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLineNumber(int line, Label start) {
/*  999 */     checkStartCode();
/* 1000 */     checkEndCode();
/* 1001 */     checkUnsignedShort(line, "Invalid line number");
/* 1002 */     checkLabel(start, true, "start label");
/* 1003 */     super.visitLineNumber(line, start);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMaxs(int maxStack, int maxLocals) {
/* 1008 */     checkStartCode();
/* 1009 */     checkEndCode();
/* 1010 */     this.endCode = true;
/* 1011 */     for (Label l : this.usedLabels) {
/* 1012 */       if (this.labels.get(l) == null) {
/* 1013 */         throw new IllegalStateException("Undefined label used");
/*      */       }
/*      */     } 
/* 1016 */     for (int i = 0; i < this.handlers.size(); ) {
/* 1017 */       Integer start = this.labels.get(this.handlers.get(i++));
/* 1018 */       Integer end = this.labels.get(this.handlers.get(i++));
/* 1019 */       if (start == null || end == null) {
/* 1020 */         throw new IllegalStateException("Undefined try catch block labels");
/*      */       }
/*      */       
/* 1023 */       if (end.intValue() <= start.intValue()) {
/* 1024 */         throw new IllegalStateException("Emty try catch block handler range");
/*      */       }
/*      */     } 
/*      */     
/* 1028 */     checkUnsignedShort(maxStack, "Invalid max stack");
/* 1029 */     checkUnsignedShort(maxLocals, "Invalid max locals");
/* 1030 */     super.visitMaxs(maxStack, maxLocals);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitEnd() {
/* 1035 */     checkEndMethod();
/* 1036 */     this.endMethod = true;
/* 1037 */     super.visitEnd();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkStartCode() {
/* 1046 */     if (!this.startCode) {
/* 1047 */       throw new IllegalStateException("Cannot visit instructions before visitCode has been called.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkEndCode() {
/* 1056 */     if (this.endCode) {
/* 1057 */       throw new IllegalStateException("Cannot visit instructions after visitMaxs has been called.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkEndMethod() {
/* 1066 */     if (this.endMethod) {
/* 1067 */       throw new IllegalStateException("Cannot visit elements after visitEnd has been called.");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkFrameValue(Object value) {
/* 1079 */     if (value == Opcodes.TOP || value == Opcodes.INTEGER || value == Opcodes.FLOAT || value == Opcodes.LONG || value == Opcodes.DOUBLE || value == Opcodes.NULL || value == Opcodes.UNINITIALIZED_THIS) {
/*      */       return;
/*      */     }
/*      */ 
/*      */ 
/*      */     
/* 1085 */     if (value instanceof String) {
/* 1086 */       checkInternalName((String)value, "Invalid stack frame value");
/*      */       return;
/*      */     } 
/* 1089 */     if (!(value instanceof Label)) {
/* 1090 */       throw new IllegalArgumentException("Invalid stack frame value: " + value);
/*      */     }
/*      */     
/* 1093 */     this.usedLabels.add((Label)value);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkOpcode(int opcode, int type) {
/* 1106 */     if (opcode < 0 || opcode > 199 || TYPE[opcode] != type) {
/* 1107 */       throw new IllegalArgumentException("Invalid opcode: " + opcode);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkSignedByte(int value, String msg) {
/* 1120 */     if (value < -128 || value > 127) {
/* 1121 */       throw new IllegalArgumentException(msg + " (must be a signed byte): " + value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkSignedShort(int value, String msg) {
/* 1135 */     if (value < -32768 || value > 32767) {
/* 1136 */       throw new IllegalArgumentException(msg + " (must be a signed short): " + value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkUnsignedShort(int value, String msg) {
/* 1150 */     if (value < 0 || value > 65535) {
/* 1151 */       throw new IllegalArgumentException(msg + " (must be an unsigned short): " + value);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkConstant(Object cst) {
/* 1164 */     if (!(cst instanceof Integer) && !(cst instanceof Float) && !(cst instanceof Long) && !(cst instanceof Double) && !(cst instanceof String))
/*      */     {
/*      */       
/* 1167 */       throw new IllegalArgumentException("Invalid constant: " + cst);
/*      */     }
/*      */   }
/*      */   
/*      */   void checkLDCConstant(Object cst) {
/* 1172 */     if (cst instanceof Type) {
/* 1173 */       int s = ((Type)cst).getSort();
/* 1174 */       if (s != 10 && s != 9 && s != 11) {
/* 1175 */         throw new IllegalArgumentException("Illegal LDC constant value");
/*      */       }
/* 1177 */       if (s != 11 && (this.version & 0xFFFF) < 49) {
/* 1178 */         throw new IllegalArgumentException("ldc of a constant class requires at least version 1.5");
/*      */       }
/*      */       
/* 1181 */       if (s == 11 && (this.version & 0xFFFF) < 51) {
/* 1182 */         throw new IllegalArgumentException("ldc of a method type requires at least version 1.7");
/*      */       }
/*      */     }
/* 1185 */     else if (cst instanceof Handle) {
/* 1186 */       if ((this.version & 0xFFFF) < 51) {
/* 1187 */         throw new IllegalArgumentException("ldc of a handle requires at least version 1.7");
/*      */       }
/*      */       
/* 1190 */       int tag = ((Handle)cst).getTag();
/* 1191 */       if (tag < 1 || tag > 9) {
/* 1192 */         throw new IllegalArgumentException("invalid handle tag " + tag);
/*      */       }
/*      */     } else {
/* 1195 */       checkConstant(cst);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkUnqualifiedName(int version, String name, String msg) {
/* 1211 */     if ((version & 0xFFFF) < 49) {
/* 1212 */       checkIdentifier(name, msg);
/*      */     } else {
/* 1214 */       for (int i = 0; i < name.length(); i++) {
/* 1215 */         if (".;[/".indexOf(name.charAt(i)) != -1) {
/* 1216 */           throw new IllegalArgumentException("Invalid " + msg + " (must be a valid unqualified name): " + name);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkIdentifier(String name, String msg) {
/* 1232 */     checkIdentifier(name, 0, -1, msg);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkIdentifier(String name, int start, int end, String msg) {
/* 1251 */     if (name == null || ((end == -1) ? (name.length() <= start) : (end <= start))) {
/* 1252 */       throw new IllegalArgumentException("Invalid " + msg + " (must not be null or empty)");
/*      */     }
/*      */     
/* 1255 */     if (!Character.isJavaIdentifierStart(name.charAt(start))) {
/* 1256 */       throw new IllegalArgumentException("Invalid " + msg + " (must be a valid Java identifier): " + name);
/*      */     }
/*      */     
/* 1259 */     int max = (end == -1) ? name.length() : end;
/* 1260 */     for (int i = start + 1; i < max; i++) {
/* 1261 */       if (!Character.isJavaIdentifierPart(name.charAt(i))) {
/* 1262 */         throw new IllegalArgumentException("Invalid " + msg + " (must be a valid Java identifier): " + name);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkMethodIdentifier(int version, String name, String msg) {
/* 1280 */     if (name == null || name.length() == 0) {
/* 1281 */       throw new IllegalArgumentException("Invalid " + msg + " (must not be null or empty)");
/*      */     }
/*      */     
/* 1284 */     if ((version & 0xFFFF) >= 49) {
/* 1285 */       for (int j = 0; j < name.length(); j++) {
/* 1286 */         if (".;[/<>".indexOf(name.charAt(j)) != -1) {
/* 1287 */           throw new IllegalArgumentException("Invalid " + msg + " (must be a valid unqualified name): " + name);
/*      */         }
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1293 */     if (!Character.isJavaIdentifierStart(name.charAt(0))) {
/* 1294 */       throw new IllegalArgumentException("Invalid " + msg + " (must be a '<init>', '<clinit>' or a valid Java identifier): " + name);
/*      */     }
/*      */ 
/*      */ 
/*      */ 
/*      */     
/* 1300 */     for (int i = 1; i < name.length(); i++) {
/* 1301 */       if (!Character.isJavaIdentifierPart(name.charAt(i))) {
/* 1302 */         throw new IllegalArgumentException("Invalid " + msg + " (must be '<init>' or '<clinit>' or a valid Java identifier): " + name);
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkInternalName(String name, String msg) {
/* 1320 */     if (name == null || name.length() == 0) {
/* 1321 */       throw new IllegalArgumentException("Invalid " + msg + " (must not be null or empty)");
/*      */     }
/*      */     
/* 1324 */     if (name.charAt(0) == '[') {
/* 1325 */       checkDesc(name, false);
/*      */     } else {
/* 1327 */       checkInternalName(name, 0, -1, msg);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkInternalName(String name, int start, int end, String msg) {
/* 1347 */     int max = (end == -1) ? name.length() : end;
/*      */     try {
/* 1349 */       int slash, begin = start;
/*      */       
/*      */       do {
/* 1352 */         slash = name.indexOf('/', begin + 1);
/* 1353 */         if (slash == -1 || slash > max) {
/* 1354 */           slash = max;
/*      */         }
/* 1356 */         checkIdentifier(name, begin, slash, (String)null);
/* 1357 */         begin = slash + 1;
/* 1358 */       } while (slash != max);
/* 1359 */     } catch (IllegalArgumentException unused) {
/* 1360 */       throw new IllegalArgumentException("Invalid " + msg + " (must be a fully qualified class name in internal form): " + name);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkDesc(String desc, boolean canBeVoid) {
/* 1377 */     int end = checkDesc(desc, 0, canBeVoid);
/* 1378 */     if (end != desc.length()) {
/* 1379 */       throw new IllegalArgumentException("Invalid descriptor: " + desc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static int checkDesc(String desc, int start, boolean canBeVoid) {
/*      */     int index;
/* 1396 */     if (desc == null || start >= desc.length()) {
/* 1397 */       throw new IllegalArgumentException("Invalid type descriptor (must not be null or empty)");
/*      */     }
/*      */ 
/*      */     
/* 1401 */     switch (desc.charAt(start)) {
/*      */       case 'V':
/* 1403 */         if (canBeVoid) {
/* 1404 */           return start + 1;
/*      */         }
/* 1406 */         throw new IllegalArgumentException("Invalid descriptor: " + desc);
/*      */ 
/*      */       
/*      */       case 'B':
/*      */       case 'C':
/*      */       case 'D':
/*      */       case 'F':
/*      */       case 'I':
/*      */       case 'J':
/*      */       case 'S':
/*      */       case 'Z':
/* 1417 */         return start + 1;
/*      */       case '[':
/* 1419 */         index = start + 1;
/* 1420 */         while (index < desc.length() && desc.charAt(index) == '[') {
/* 1421 */           index++;
/*      */         }
/* 1423 */         if (index < desc.length()) {
/* 1424 */           return checkDesc(desc, index, false);
/*      */         }
/* 1426 */         throw new IllegalArgumentException("Invalid descriptor: " + desc);
/*      */ 
/*      */       
/*      */       case 'L':
/* 1430 */         index = desc.indexOf(';', start);
/* 1431 */         if (index == -1 || index - start < 2) {
/* 1432 */           throw new IllegalArgumentException("Invalid descriptor: " + desc);
/*      */         }
/*      */         
/*      */         try {
/* 1436 */           checkInternalName(desc, start + 1, index, (String)null);
/* 1437 */         } catch (IllegalArgumentException unused) {
/* 1438 */           throw new IllegalArgumentException("Invalid descriptor: " + desc);
/*      */         } 
/*      */         
/* 1441 */         return index + 1;
/*      */     } 
/* 1443 */     throw new IllegalArgumentException("Invalid descriptor: " + desc);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   static void checkMethodDesc(String desc) {
/* 1454 */     if (desc == null || desc.length() == 0) {
/* 1455 */       throw new IllegalArgumentException("Invalid method descriptor (must not be null or empty)");
/*      */     }
/*      */     
/* 1458 */     if (desc.charAt(0) != '(' || desc.length() < 3) {
/* 1459 */       throw new IllegalArgumentException("Invalid descriptor: " + desc);
/*      */     }
/* 1461 */     int start = 1;
/* 1462 */     if (desc.charAt(start) != ')') {
/*      */       do {
/* 1464 */         if (desc.charAt(start) == 'V') {
/* 1465 */           throw new IllegalArgumentException("Invalid descriptor: " + desc);
/*      */         }
/*      */         
/* 1468 */         start = checkDesc(desc, start, false);
/* 1469 */       } while (start < desc.length() && desc.charAt(start) != ')');
/*      */     }
/* 1471 */     start = checkDesc(desc, start + 1, true);
/* 1472 */     if (start != desc.length()) {
/* 1473 */       throw new IllegalArgumentException("Invalid descriptor: " + desc);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   void checkLabel(Label label, boolean checkVisited, String msg) {
/* 1490 */     if (label == null) {
/* 1491 */       throw new IllegalArgumentException("Invalid " + msg + " (must not be null)");
/*      */     }
/*      */     
/* 1494 */     if (checkVisited && this.labels.get(label) == null) {
/* 1495 */       throw new IllegalArgumentException("Invalid " + msg + " (must be visited first)");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void checkNonDebugLabel(Label label) {
/* 1507 */     Field f = getLabelStatusField();
/* 1508 */     int status = 0;
/*      */     try {
/* 1510 */       status = (f == null) ? 0 : ((Integer)f.get(label)).intValue();
/* 1511 */     } catch (IllegalAccessException e) {
/* 1512 */       throw new Error("Internal error");
/*      */     } 
/* 1514 */     if ((status & 0x1) != 0) {
/* 1515 */       throw new IllegalArgumentException("Labels used for debug info cannot be reused for control flow");
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Field getLabelStatusField() {
/* 1526 */     if (labelStatusField == null) {
/* 1527 */       labelStatusField = getLabelField("a");
/* 1528 */       if (labelStatusField == null) {
/* 1529 */         labelStatusField = getLabelField("status");
/*      */       }
/*      */     } 
/* 1532 */     return labelStatusField;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static Field getLabelField(String name) {
/*      */     try {
/* 1544 */       Field f = Label.class.getDeclaredField(name);
/* 1545 */       f.setAccessible(true);
/* 1546 */       return f;
/* 1547 */     } catch (NoSuchFieldException e) {
/* 1548 */       return null;
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\li\\util\CheckMethodAdapter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */