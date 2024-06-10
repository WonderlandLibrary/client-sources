/*     */ package org.spongepowered.asm.lib.tree;
/*     */ 
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.List;
/*     */ import org.spongepowered.asm.lib.AnnotationVisitor;
/*     */ import org.spongepowered.asm.lib.Attribute;
/*     */ import org.spongepowered.asm.lib.ClassVisitor;
/*     */ import org.spongepowered.asm.lib.Handle;
/*     */ import org.spongepowered.asm.lib.Label;
/*     */ import org.spongepowered.asm.lib.MethodVisitor;
/*     */ import org.spongepowered.asm.lib.Type;
/*     */ import org.spongepowered.asm.lib.TypePath;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MethodNode
/*     */   extends MethodVisitor
/*     */ {
/*     */   public int access;
/*     */   public String name;
/*     */   public String desc;
/*     */   public String signature;
/*     */   public List<String> exceptions;
/*     */   public List<ParameterNode> parameters;
/*     */   public List<AnnotationNode> visibleAnnotations;
/*     */   public List<AnnotationNode> invisibleAnnotations;
/*     */   public List<TypeAnnotationNode> visibleTypeAnnotations;
/*     */   public List<TypeAnnotationNode> invisibleTypeAnnotations;
/*     */   public List<Attribute> attrs;
/*     */   public Object annotationDefault;
/*     */   public List<AnnotationNode>[] visibleParameterAnnotations;
/*     */   public List<AnnotationNode>[] invisibleParameterAnnotations;
/*     */   public InsnList instructions;
/*     */   public List<TryCatchBlockNode> tryCatchBlocks;
/*     */   public int maxStack;
/*     */   public int maxLocals;
/*     */   public List<LocalVariableNode> localVariables;
/*     */   public List<LocalVariableAnnotationNode> visibleLocalVariableAnnotations;
/*     */   public List<LocalVariableAnnotationNode> invisibleLocalVariableAnnotations;
/*     */   private boolean visited;
/*     */   
/*     */   public MethodNode() {
/* 223 */     this(327680);
/* 224 */     if (getClass() != MethodNode.class) {
/* 225 */       throw new IllegalStateException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode(int api) {
/* 237 */     super(api);
/* 238 */     this.instructions = new InsnList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode(int access, String name, String desc, String signature, String[] exceptions) {
/* 265 */     this(327680, access, name, desc, signature, exceptions);
/* 266 */     if (getClass() != MethodNode.class) {
/* 267 */       throw new IllegalStateException();
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MethodNode(int api, int access, String name, String desc, String signature, String[] exceptions) {
/* 294 */     super(api);
/* 295 */     this.access = access;
/* 296 */     this.name = name;
/* 297 */     this.desc = desc;
/* 298 */     this.signature = signature;
/* 299 */     this.exceptions = new ArrayList<String>((exceptions == null) ? 0 : exceptions.length);
/*     */     
/* 301 */     boolean isAbstract = ((access & 0x400) != 0);
/* 302 */     if (!isAbstract) {
/* 303 */       this.localVariables = new ArrayList<LocalVariableNode>(5);
/*     */     }
/* 305 */     this.tryCatchBlocks = new ArrayList<TryCatchBlockNode>();
/* 306 */     if (exceptions != null) {
/* 307 */       this.exceptions.addAll(Arrays.asList(exceptions));
/*     */     }
/* 309 */     this.instructions = new InsnList();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitParameter(String name, int access) {
/* 318 */     if (this.parameters == null) {
/* 319 */       this.parameters = new ArrayList<ParameterNode>(5);
/*     */     }
/* 321 */     this.parameters.add(new ParameterNode(name, access));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotationDefault() {
/* 327 */     return new AnnotationNode(new ArrayList(0)
/*     */         {
/*     */           public boolean add(Object o) {
/* 330 */             MethodNode.this.annotationDefault = o;
/* 331 */             return super.add(o);
/*     */           }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/* 339 */     AnnotationNode an = new AnnotationNode(desc);
/* 340 */     if (visible) {
/* 341 */       if (this.visibleAnnotations == null) {
/* 342 */         this.visibleAnnotations = new ArrayList<AnnotationNode>(1);
/*     */       }
/* 344 */       this.visibleAnnotations.add(an);
/*     */     } else {
/* 346 */       if (this.invisibleAnnotations == null) {
/* 347 */         this.invisibleAnnotations = new ArrayList<AnnotationNode>(1);
/*     */       }
/* 349 */       this.invisibleAnnotations.add(an);
/*     */     } 
/* 351 */     return an;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 357 */     TypeAnnotationNode an = new TypeAnnotationNode(typeRef, typePath, desc);
/* 358 */     if (visible) {
/* 359 */       if (this.visibleTypeAnnotations == null) {
/* 360 */         this.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/* 362 */       this.visibleTypeAnnotations.add(an);
/*     */     } else {
/* 364 */       if (this.invisibleTypeAnnotations == null) {
/* 365 */         this.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/* 367 */       this.invisibleTypeAnnotations.add(an);
/*     */     } 
/* 369 */     return an;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
/* 376 */     AnnotationNode an = new AnnotationNode(desc);
/* 377 */     if (visible) {
/* 378 */       if (this.visibleParameterAnnotations == null) {
/* 379 */         int params = (Type.getArgumentTypes(this.desc)).length;
/* 380 */         this.visibleParameterAnnotations = (List<AnnotationNode>[])new List[params];
/*     */       } 
/* 382 */       if (this.visibleParameterAnnotations[parameter] == null) {
/* 383 */         this.visibleParameterAnnotations[parameter] = new ArrayList<AnnotationNode>(1);
/*     */       }
/*     */       
/* 386 */       this.visibleParameterAnnotations[parameter].add(an);
/*     */     } else {
/* 388 */       if (this.invisibleParameterAnnotations == null) {
/* 389 */         int params = (Type.getArgumentTypes(this.desc)).length;
/* 390 */         this.invisibleParameterAnnotations = (List<AnnotationNode>[])new List[params];
/*     */       } 
/* 392 */       if (this.invisibleParameterAnnotations[parameter] == null) {
/* 393 */         this.invisibleParameterAnnotations[parameter] = new ArrayList<AnnotationNode>(1);
/*     */       }
/*     */       
/* 396 */       this.invisibleParameterAnnotations[parameter].add(an);
/*     */     } 
/* 398 */     return an;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitAttribute(Attribute attr) {
/* 403 */     if (this.attrs == null) {
/* 404 */       this.attrs = new ArrayList<Attribute>(1);
/*     */     }
/* 406 */     this.attrs.add(attr);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitCode() {}
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/* 416 */     this.instructions.add(new FrameNode(type, nLocal, (local == null) ? null : 
/* 417 */           getLabelNodes(local), nStack, (stack == null) ? null : 
/* 418 */           getLabelNodes(stack)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitInsn(int opcode) {
/* 423 */     this.instructions.add(new InsnNode(opcode));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIntInsn(int opcode, int operand) {
/* 428 */     this.instructions.add(new IntInsnNode(opcode, operand));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitVarInsn(int opcode, int var) {
/* 433 */     this.instructions.add(new VarInsnNode(opcode, var));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitTypeInsn(int opcode, String type) {
/* 438 */     this.instructions.add(new TypeInsnNode(opcode, type));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/* 444 */     this.instructions.add(new FieldInsnNode(opcode, owner, name, desc));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   @Deprecated
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc) {
/* 451 */     if (this.api >= 327680) {
/* 452 */       super.visitMethodInsn(opcode, owner, name, desc);
/*     */       return;
/*     */     } 
/* 455 */     this.instructions.add(new MethodInsnNode(opcode, owner, name, desc));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/* 461 */     if (this.api < 327680) {
/* 462 */       super.visitMethodInsn(opcode, owner, name, desc, itf);
/*     */       return;
/*     */     } 
/* 465 */     this.instructions.add(new MethodInsnNode(opcode, owner, name, desc, itf));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
/* 471 */     this.instructions.add(new InvokeDynamicInsnNode(name, desc, bsm, bsmArgs));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitJumpInsn(int opcode, Label label) {
/* 476 */     this.instructions.add(new JumpInsnNode(opcode, getLabelNode(label)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLabel(Label label) {
/* 481 */     this.instructions.add(getLabelNode(label));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLdcInsn(Object cst) {
/* 486 */     this.instructions.add(new LdcInsnNode(cst));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitIincInsn(int var, int increment) {
/* 491 */     this.instructions.add(new IincInsnNode(var, increment));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
/* 497 */     this.instructions.add(new TableSwitchInsnNode(min, max, getLabelNode(dflt), 
/* 498 */           getLabelNodes(labels)));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/* 504 */     this.instructions.add(new LookupSwitchInsnNode(getLabelNode(dflt), keys, 
/* 505 */           getLabelNodes(labels)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 510 */     this.instructions.add(new MultiANewArrayInsnNode(desc, dims));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 518 */     AbstractInsnNode insn = this.instructions.getLast();
/* 519 */     while (insn.getOpcode() == -1) {
/* 520 */       insn = insn.getPrevious();
/*     */     }
/*     */     
/* 523 */     TypeAnnotationNode an = new TypeAnnotationNode(typeRef, typePath, desc);
/* 524 */     if (visible) {
/* 525 */       if (insn.visibleTypeAnnotations == null) {
/* 526 */         insn.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/*     */       
/* 529 */       insn.visibleTypeAnnotations.add(an);
/*     */     } else {
/* 531 */       if (insn.invisibleTypeAnnotations == null) {
/* 532 */         insn.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/*     */       
/* 535 */       insn.invisibleTypeAnnotations.add(an);
/*     */     } 
/* 537 */     return an;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/* 543 */     this.tryCatchBlocks.add(new TryCatchBlockNode(getLabelNode(start), 
/* 544 */           getLabelNode(end), getLabelNode(handler), type));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 550 */     TryCatchBlockNode tcb = this.tryCatchBlocks.get((typeRef & 0xFFFF00) >> 8);
/* 551 */     TypeAnnotationNode an = new TypeAnnotationNode(typeRef, typePath, desc);
/* 552 */     if (visible) {
/* 553 */       if (tcb.visibleTypeAnnotations == null) {
/* 554 */         tcb.visibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/*     */       
/* 557 */       tcb.visibleTypeAnnotations.add(an);
/*     */     } else {
/* 559 */       if (tcb.invisibleTypeAnnotations == null) {
/* 560 */         tcb.invisibleTypeAnnotations = new ArrayList<TypeAnnotationNode>(1);
/*     */       }
/*     */       
/* 563 */       tcb.invisibleTypeAnnotations.add(an);
/*     */     } 
/* 565 */     return an;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 572 */     this.localVariables.add(new LocalVariableNode(name, desc, signature, 
/* 573 */           getLabelNode(start), getLabelNode(end), index));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
/* 581 */     LocalVariableAnnotationNode an = new LocalVariableAnnotationNode(typeRef, typePath, getLabelNodes(start), getLabelNodes(end), index, desc);
/*     */     
/* 583 */     if (visible) {
/* 584 */       if (this.visibleLocalVariableAnnotations == null) {
/* 585 */         this.visibleLocalVariableAnnotations = new ArrayList<LocalVariableAnnotationNode>(1);
/*     */       }
/*     */       
/* 588 */       this.visibleLocalVariableAnnotations.add(an);
/*     */     } else {
/* 590 */       if (this.invisibleLocalVariableAnnotations == null) {
/* 591 */         this.invisibleLocalVariableAnnotations = new ArrayList<LocalVariableAnnotationNode>(1);
/*     */       }
/*     */       
/* 594 */       this.invisibleLocalVariableAnnotations.add(an);
/*     */     } 
/* 596 */     return an;
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitLineNumber(int line, Label start) {
/* 601 */     this.instructions.add(new LineNumberNode(line, getLabelNode(start)));
/*     */   }
/*     */ 
/*     */   
/*     */   public void visitMaxs(int maxStack, int maxLocals) {
/* 606 */     this.maxStack = maxStack;
/* 607 */     this.maxLocals = maxLocals;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void visitEnd() {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected LabelNode getLabelNode(Label l) {
/* 625 */     if (!(l.info instanceof LabelNode)) {
/* 626 */       l.info = new LabelNode();
/*     */     }
/* 628 */     return (LabelNode)l.info;
/*     */   }
/*     */   
/*     */   private LabelNode[] getLabelNodes(Label[] l) {
/* 632 */     LabelNode[] nodes = new LabelNode[l.length];
/* 633 */     for (int i = 0; i < l.length; i++) {
/* 634 */       nodes[i] = getLabelNode(l[i]);
/*     */     }
/* 636 */     return nodes;
/*     */   }
/*     */   
/*     */   private Object[] getLabelNodes(Object[] objs) {
/* 640 */     Object[] nodes = new Object[objs.length];
/* 641 */     for (int i = 0; i < objs.length; i++) {
/* 642 */       Object o = objs[i];
/* 643 */       if (o instanceof Label) {
/* 644 */         o = getLabelNode((Label)o);
/*     */       }
/* 646 */       nodes[i] = o;
/*     */     } 
/* 648 */     return nodes;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void check(int api) {
/* 666 */     if (api == 262144) {
/* 667 */       if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations
/* 668 */         .size() > 0) {
/* 669 */         throw new RuntimeException();
/*     */       }
/* 671 */       if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations
/* 672 */         .size() > 0) {
/* 673 */         throw new RuntimeException();
/*     */       }
/* 675 */       int n = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size(); int i;
/* 676 */       for (i = 0; i < n; i++) {
/* 677 */         TryCatchBlockNode tcb = this.tryCatchBlocks.get(i);
/* 678 */         if (tcb.visibleTypeAnnotations != null && tcb.visibleTypeAnnotations
/* 679 */           .size() > 0) {
/* 680 */           throw new RuntimeException();
/*     */         }
/* 682 */         if (tcb.invisibleTypeAnnotations != null && tcb.invisibleTypeAnnotations
/* 683 */           .size() > 0) {
/* 684 */           throw new RuntimeException();
/*     */         }
/*     */       } 
/* 687 */       for (i = 0; i < this.instructions.size(); i++) {
/* 688 */         AbstractInsnNode insn = this.instructions.get(i);
/* 689 */         if (insn.visibleTypeAnnotations != null && insn.visibleTypeAnnotations
/* 690 */           .size() > 0) {
/* 691 */           throw new RuntimeException();
/*     */         }
/* 693 */         if (insn.invisibleTypeAnnotations != null && insn.invisibleTypeAnnotations
/* 694 */           .size() > 0) {
/* 695 */           throw new RuntimeException();
/*     */         }
/* 697 */         if (insn instanceof MethodInsnNode) {
/* 698 */           boolean itf = ((MethodInsnNode)insn).itf;
/* 699 */           if (itf != ((insn.opcode == 185))) {
/* 700 */             throw new RuntimeException();
/*     */           }
/*     */         } 
/*     */       } 
/* 704 */       if (this.visibleLocalVariableAnnotations != null && this.visibleLocalVariableAnnotations
/* 705 */         .size() > 0) {
/* 706 */         throw new RuntimeException();
/*     */       }
/* 708 */       if (this.invisibleLocalVariableAnnotations != null && this.invisibleLocalVariableAnnotations
/* 709 */         .size() > 0) {
/* 710 */         throw new RuntimeException();
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(ClassVisitor cv) {
/* 722 */     String[] exceptions = new String[this.exceptions.size()];
/* 723 */     this.exceptions.toArray(exceptions);
/* 724 */     MethodVisitor mv = cv.visitMethod(this.access, this.name, this.desc, this.signature, exceptions);
/*     */     
/* 726 */     if (mv != null) {
/* 727 */       accept(mv);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void accept(MethodVisitor mv) {
/* 740 */     int n = (this.parameters == null) ? 0 : this.parameters.size(); int i;
/* 741 */     for (i = 0; i < n; i++) {
/* 742 */       ParameterNode parameter = this.parameters.get(i);
/* 743 */       mv.visitParameter(parameter.name, parameter.access);
/*     */     } 
/*     */     
/* 746 */     if (this.annotationDefault != null) {
/* 747 */       AnnotationVisitor av = mv.visitAnnotationDefault();
/* 748 */       AnnotationNode.accept(av, null, this.annotationDefault);
/* 749 */       if (av != null) {
/* 750 */         av.visitEnd();
/*     */       }
/*     */     } 
/* 753 */     n = (this.visibleAnnotations == null) ? 0 : this.visibleAnnotations.size();
/* 754 */     for (i = 0; i < n; i++) {
/* 755 */       AnnotationNode an = this.visibleAnnotations.get(i);
/* 756 */       an.accept(mv.visitAnnotation(an.desc, true));
/*     */     } 
/* 758 */     n = (this.invisibleAnnotations == null) ? 0 : this.invisibleAnnotations.size();
/* 759 */     for (i = 0; i < n; i++) {
/* 760 */       AnnotationNode an = this.invisibleAnnotations.get(i);
/* 761 */       an.accept(mv.visitAnnotation(an.desc, false));
/*     */     } 
/* 763 */     n = (this.visibleTypeAnnotations == null) ? 0 : this.visibleTypeAnnotations.size();
/* 764 */     for (i = 0; i < n; i++) {
/* 765 */       TypeAnnotationNode an = this.visibleTypeAnnotations.get(i);
/* 766 */       an.accept(mv.visitTypeAnnotation(an.typeRef, an.typePath, an.desc, true));
/*     */     } 
/*     */ 
/*     */     
/* 770 */     n = (this.invisibleTypeAnnotations == null) ? 0 : this.invisibleTypeAnnotations.size();
/* 771 */     for (i = 0; i < n; i++) {
/* 772 */       TypeAnnotationNode an = this.invisibleTypeAnnotations.get(i);
/* 773 */       an.accept(mv.visitTypeAnnotation(an.typeRef, an.typePath, an.desc, false));
/*     */     } 
/*     */     
/* 776 */     n = (this.visibleParameterAnnotations == null) ? 0 : this.visibleParameterAnnotations.length;
/*     */     
/* 778 */     for (i = 0; i < n; i++) {
/* 779 */       List<?> l = this.visibleParameterAnnotations[i];
/* 780 */       if (l != null)
/*     */       {
/*     */         
/* 783 */         for (int j = 0; j < l.size(); j++) {
/* 784 */           AnnotationNode an = (AnnotationNode)l.get(j);
/* 785 */           an.accept(mv.visitParameterAnnotation(i, an.desc, true));
/*     */         }  } 
/*     */     } 
/* 788 */     n = (this.invisibleParameterAnnotations == null) ? 0 : this.invisibleParameterAnnotations.length;
/*     */     
/* 790 */     for (i = 0; i < n; i++) {
/* 791 */       List<?> l = this.invisibleParameterAnnotations[i];
/* 792 */       if (l != null)
/*     */       {
/*     */         
/* 795 */         for (int j = 0; j < l.size(); j++) {
/* 796 */           AnnotationNode an = (AnnotationNode)l.get(j);
/* 797 */           an.accept(mv.visitParameterAnnotation(i, an.desc, false));
/*     */         }  } 
/*     */     } 
/* 800 */     if (this.visited) {
/* 801 */       this.instructions.resetLabels();
/*     */     }
/* 803 */     n = (this.attrs == null) ? 0 : this.attrs.size();
/* 804 */     for (i = 0; i < n; i++) {
/* 805 */       mv.visitAttribute(this.attrs.get(i));
/*     */     }
/*     */     
/* 808 */     if (this.instructions.size() > 0) {
/* 809 */       mv.visitCode();
/*     */       
/* 811 */       n = (this.tryCatchBlocks == null) ? 0 : this.tryCatchBlocks.size();
/* 812 */       for (i = 0; i < n; i++) {
/* 813 */         ((TryCatchBlockNode)this.tryCatchBlocks.get(i)).updateIndex(i);
/* 814 */         ((TryCatchBlockNode)this.tryCatchBlocks.get(i)).accept(mv);
/*     */       } 
/*     */       
/* 817 */       this.instructions.accept(mv);
/*     */       
/* 819 */       n = (this.localVariables == null) ? 0 : this.localVariables.size();
/* 820 */       for (i = 0; i < n; i++) {
/* 821 */         ((LocalVariableNode)this.localVariables.get(i)).accept(mv);
/*     */       }
/*     */ 
/*     */       
/* 825 */       n = (this.visibleLocalVariableAnnotations == null) ? 0 : this.visibleLocalVariableAnnotations.size();
/* 826 */       for (i = 0; i < n; i++) {
/* 827 */         ((LocalVariableAnnotationNode)this.visibleLocalVariableAnnotations.get(i)).accept(mv, true);
/*     */       }
/*     */       
/* 830 */       n = (this.invisibleLocalVariableAnnotations == null) ? 0 : this.invisibleLocalVariableAnnotations.size();
/* 831 */       for (i = 0; i < n; i++) {
/* 832 */         ((LocalVariableAnnotationNode)this.invisibleLocalVariableAnnotations.get(i)).accept(mv, false);
/*     */       }
/*     */       
/* 835 */       mv.visitMaxs(this.maxStack, this.maxLocals);
/* 836 */       this.visited = true;
/*     */     } 
/* 838 */     mv.visitEnd();
/*     */   }
/*     */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\tree\MethodNode.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */