/*      */ package org.spongepowered.asm.lib;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ class MethodWriter
/*      */   extends MethodVisitor
/*      */ {
/*      */   static final int ACC_CONSTRUCTOR = 524288;
/*      */   static final int SAME_FRAME = 0;
/*      */   static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
/*      */   static final int RESERVED = 128;
/*      */   static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
/*      */   static final int CHOP_FRAME = 248;
/*      */   static final int SAME_FRAME_EXTENDED = 251;
/*      */   static final int APPEND_FRAME = 252;
/*      */   static final int FULL_FRAME = 255;
/*      */   static final int FRAMES = 0;
/*      */   static final int INSERTED_FRAMES = 1;
/*      */   static final int MAXS = 2;
/*      */   static final int NOTHING = 3;
/*      */   final ClassWriter cw;
/*      */   private int access;
/*      */   private final int name;
/*      */   private final int desc;
/*      */   private final String descriptor;
/*      */   String signature;
/*      */   int classReaderOffset;
/*      */   int classReaderLength;
/*      */   int exceptionCount;
/*      */   int[] exceptions;
/*      */   private ByteVector annd;
/*      */   private AnnotationWriter anns;
/*      */   private AnnotationWriter ianns;
/*      */   private AnnotationWriter tanns;
/*      */   private AnnotationWriter itanns;
/*      */   private AnnotationWriter[] panns;
/*      */   private AnnotationWriter[] ipanns;
/*      */   private int synthetics;
/*      */   private Attribute attrs;
/*  243 */   private ByteVector code = new ByteVector();
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxStack;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxLocals;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int currentLocals;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int frameCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector stackMap;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int previousFrameOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] previousFrame;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int[] frame;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int handlerCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler firstHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Handler lastHandler;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int methodParametersCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector methodParameters;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int localVarCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector localVar;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int localVarTypeCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector localVarType;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lineNumberCount;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private ByteVector lineNumber;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int lastCodeOffset;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AnnotationWriter ctanns;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private AnnotationWriter ictanns;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Attribute cattrs;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int subroutines;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final int compute;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Label labels;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Label previousBlock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Label currentBlock;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int stackSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private int maxStackSize;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   MethodWriter(ClassWriter cw, int access, String name, String desc, String signature, String[] exceptions, int compute) {
/*  459 */     super(327680);
/*  460 */     if (cw.firstMethod == null) {
/*  461 */       cw.firstMethod = this;
/*      */     } else {
/*  463 */       cw.lastMethod.mv = this;
/*      */     } 
/*  465 */     cw.lastMethod = this;
/*  466 */     this.cw = cw;
/*  467 */     this.access = access;
/*  468 */     if ("<init>".equals(name)) {
/*  469 */       this.access |= 0x80000;
/*      */     }
/*  471 */     this.name = cw.newUTF8(name);
/*  472 */     this.desc = cw.newUTF8(desc);
/*  473 */     this.descriptor = desc;
/*      */     
/*  475 */     this.signature = signature;
/*      */     
/*  477 */     if (exceptions != null && exceptions.length > 0) {
/*  478 */       this.exceptionCount = exceptions.length;
/*  479 */       this.exceptions = new int[this.exceptionCount];
/*  480 */       for (int i = 0; i < this.exceptionCount; i++) {
/*  481 */         this.exceptions[i] = cw.newClass(exceptions[i]);
/*      */       }
/*      */     } 
/*  484 */     this.compute = compute;
/*  485 */     if (compute != 3) {
/*      */       
/*  487 */       int size = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
/*  488 */       if ((access & 0x8) != 0) {
/*  489 */         size--;
/*      */       }
/*  491 */       this.maxLocals = size;
/*  492 */       this.currentLocals = size;
/*      */       
/*  494 */       this.labels = new Label();
/*  495 */       this.labels.status |= 0x8;
/*  496 */       visitLabel(this.labels);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitParameter(String name, int access) {
/*  506 */     if (this.methodParameters == null) {
/*  507 */       this.methodParameters = new ByteVector();
/*      */     }
/*  509 */     this.methodParametersCount++;
/*  510 */     this.methodParameters.putShort((name == null) ? 0 : this.cw.newUTF8(name))
/*  511 */       .putShort(access);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotationDefault() {
/*  519 */     this.annd = new ByteVector();
/*  520 */     return new AnnotationWriter(this.cw, false, this.annd, null, 0);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitAnnotation(String desc, boolean visible) {
/*  529 */     ByteVector bv = new ByteVector();
/*      */     
/*  531 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/*  532 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, 2);
/*  533 */     if (visible) {
/*  534 */       aw.next = this.anns;
/*  535 */       this.anns = aw;
/*      */     } else {
/*  537 */       aw.next = this.ianns;
/*  538 */       this.ianns = aw;
/*      */     } 
/*  540 */     return aw;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitTypeAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/*  549 */     ByteVector bv = new ByteVector();
/*      */     
/*  551 */     AnnotationWriter.putTarget(typeRef, typePath, bv);
/*      */     
/*  553 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/*  554 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, bv.length - 2);
/*      */     
/*  556 */     if (visible) {
/*  557 */       aw.next = this.tanns;
/*  558 */       this.tanns = aw;
/*      */     } else {
/*  560 */       aw.next = this.itanns;
/*  561 */       this.itanns = aw;
/*      */     } 
/*  563 */     return aw;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitParameterAnnotation(int parameter, String desc, boolean visible) {
/*  572 */     ByteVector bv = new ByteVector();
/*  573 */     if ("Ljava/lang/Synthetic;".equals(desc)) {
/*      */ 
/*      */       
/*  576 */       this.synthetics = Math.max(this.synthetics, parameter + 1);
/*  577 */       return new AnnotationWriter(this.cw, false, bv, null, 0);
/*      */     } 
/*      */     
/*  580 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/*  581 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, 2);
/*  582 */     if (visible) {
/*  583 */       if (this.panns == null) {
/*  584 */         this.panns = new AnnotationWriter[(Type.getArgumentTypes(this.descriptor)).length];
/*      */       }
/*  586 */       aw.next = this.panns[parameter];
/*  587 */       this.panns[parameter] = aw;
/*      */     } else {
/*  589 */       if (this.ipanns == null) {
/*  590 */         this.ipanns = new AnnotationWriter[(Type.getArgumentTypes(this.descriptor)).length];
/*      */       }
/*  592 */       aw.next = this.ipanns[parameter];
/*  593 */       this.ipanns[parameter] = aw;
/*      */     } 
/*  595 */     return aw;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitAttribute(Attribute attr) {
/*  600 */     if (attr.isCodeAttribute()) {
/*  601 */       attr.next = this.cattrs;
/*  602 */       this.cattrs = attr;
/*      */     } else {
/*  604 */       attr.next = this.attrs;
/*  605 */       this.attrs = attr;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitCode() {}
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFrame(int type, int nLocal, Object[] local, int nStack, Object[] stack) {
/*  616 */     if (this.compute == 0) {
/*      */       return;
/*      */     }
/*      */     
/*  620 */     if (this.compute == 1) {
/*  621 */       if (this.currentBlock.frame == null) {
/*      */ 
/*      */ 
/*      */         
/*  625 */         this.currentBlock.frame = new CurrentFrame();
/*  626 */         this.currentBlock.frame.owner = this.currentBlock;
/*  627 */         this.currentBlock.frame.initInputFrame(this.cw, this.access, 
/*  628 */             Type.getArgumentTypes(this.descriptor), nLocal);
/*  629 */         visitImplicitFirstFrame();
/*      */       } else {
/*  631 */         if (type == -1) {
/*  632 */           this.currentBlock.frame.set(this.cw, nLocal, local, nStack, stack);
/*      */         }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  640 */         visitFrame(this.currentBlock.frame);
/*      */       } 
/*  642 */     } else if (type == -1) {
/*  643 */       if (this.previousFrame == null) {
/*  644 */         visitImplicitFirstFrame();
/*      */       }
/*  646 */       this.currentLocals = nLocal;
/*  647 */       int frameIndex = startFrame(this.code.length, nLocal, nStack); int i;
/*  648 */       for (i = 0; i < nLocal; i++) {
/*  649 */         if (local[i] instanceof String) {
/*  650 */           this.frame[frameIndex++] = 0x1700000 | this.cw
/*  651 */             .addType((String)local[i]);
/*  652 */         } else if (local[i] instanceof Integer) {
/*  653 */           this.frame[frameIndex++] = ((Integer)local[i]).intValue();
/*      */         } else {
/*  655 */           this.frame[frameIndex++] = 0x1800000 | this.cw
/*  656 */             .addUninitializedType("", ((Label)local[i]).position);
/*      */         } 
/*      */       } 
/*      */       
/*  660 */       for (i = 0; i < nStack; i++) {
/*  661 */         if (stack[i] instanceof String) {
/*  662 */           this.frame[frameIndex++] = 0x1700000 | this.cw
/*  663 */             .addType((String)stack[i]);
/*  664 */         } else if (stack[i] instanceof Integer) {
/*  665 */           this.frame[frameIndex++] = ((Integer)stack[i]).intValue();
/*      */         } else {
/*  667 */           this.frame[frameIndex++] = 0x1800000 | this.cw
/*  668 */             .addUninitializedType("", ((Label)stack[i]).position);
/*      */         } 
/*      */       } 
/*      */       
/*  672 */       endFrame();
/*      */     } else {
/*      */       int delta, i;
/*  675 */       if (this.stackMap == null) {
/*  676 */         this.stackMap = new ByteVector();
/*  677 */         delta = this.code.length;
/*      */       } else {
/*  679 */         delta = this.code.length - this.previousFrameOffset - 1;
/*  680 */         if (delta < 0) {
/*  681 */           if (type == 3) {
/*      */             return;
/*      */           }
/*  684 */           throw new IllegalStateException();
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/*  689 */       switch (type) {
/*      */         case 0:
/*  691 */           this.currentLocals = nLocal;
/*  692 */           this.stackMap.putByte(255).putShort(delta).putShort(nLocal);
/*  693 */           for (i = 0; i < nLocal; i++) {
/*  694 */             writeFrameType(local[i]);
/*      */           }
/*  696 */           this.stackMap.putShort(nStack);
/*  697 */           for (i = 0; i < nStack; i++) {
/*  698 */             writeFrameType(stack[i]);
/*      */           }
/*      */           break;
/*      */         case 1:
/*  702 */           this.currentLocals += nLocal;
/*  703 */           this.stackMap.putByte(251 + nLocal).putShort(delta);
/*  704 */           for (i = 0; i < nLocal; i++) {
/*  705 */             writeFrameType(local[i]);
/*      */           }
/*      */           break;
/*      */         case 2:
/*  709 */           this.currentLocals -= nLocal;
/*  710 */           this.stackMap.putByte(251 - nLocal).putShort(delta);
/*      */           break;
/*      */         case 3:
/*  713 */           if (delta < 64) {
/*  714 */             this.stackMap.putByte(delta); break;
/*      */           } 
/*  716 */           this.stackMap.putByte(251).putShort(delta);
/*      */           break;
/*      */         
/*      */         case 4:
/*  720 */           if (delta < 64) {
/*  721 */             this.stackMap.putByte(64 + delta);
/*      */           } else {
/*  723 */             this.stackMap.putByte(247)
/*  724 */               .putShort(delta);
/*      */           } 
/*  726 */           writeFrameType(stack[0]);
/*      */           break;
/*      */       } 
/*      */       
/*  730 */       this.previousFrameOffset = this.code.length;
/*  731 */       this.frameCount++;
/*      */     } 
/*      */     
/*  734 */     this.maxStack = Math.max(this.maxStack, nStack);
/*  735 */     this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitInsn(int opcode) {
/*  740 */     this.lastCodeOffset = this.code.length;
/*      */     
/*  742 */     this.code.putByte(opcode);
/*      */ 
/*      */     
/*  745 */     if (this.currentBlock != null) {
/*  746 */       if (this.compute == 0 || this.compute == 1) {
/*  747 */         this.currentBlock.frame.execute(opcode, 0, null, null);
/*      */       } else {
/*      */         
/*  750 */         int size = this.stackSize + Frame.SIZE[opcode];
/*  751 */         if (size > this.maxStackSize) {
/*  752 */           this.maxStackSize = size;
/*      */         }
/*  754 */         this.stackSize = size;
/*      */       } 
/*      */       
/*  757 */       if ((opcode >= 172 && opcode <= 177) || opcode == 191)
/*      */       {
/*  759 */         noSuccessor();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIntInsn(int opcode, int operand) {
/*  766 */     this.lastCodeOffset = this.code.length;
/*      */     
/*  768 */     if (this.currentBlock != null) {
/*  769 */       if (this.compute == 0 || this.compute == 1) {
/*  770 */         this.currentBlock.frame.execute(opcode, operand, null, null);
/*  771 */       } else if (opcode != 188) {
/*      */ 
/*      */         
/*  774 */         int size = this.stackSize + 1;
/*  775 */         if (size > this.maxStackSize) {
/*  776 */           this.maxStackSize = size;
/*      */         }
/*  778 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  782 */     if (opcode == 17) {
/*  783 */       this.code.put12(opcode, operand);
/*      */     } else {
/*  785 */       this.code.put11(opcode, operand);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitVarInsn(int opcode, int var) {
/*  791 */     this.lastCodeOffset = this.code.length;
/*      */     
/*  793 */     if (this.currentBlock != null) {
/*  794 */       if (this.compute == 0 || this.compute == 1) {
/*  795 */         this.currentBlock.frame.execute(opcode, var, null, null);
/*      */       
/*      */       }
/*  798 */       else if (opcode == 169) {
/*      */         
/*  800 */         this.currentBlock.status |= 0x100;
/*      */ 
/*      */         
/*  803 */         this.currentBlock.inputStackTop = this.stackSize;
/*  804 */         noSuccessor();
/*      */       } else {
/*  806 */         int size = this.stackSize + Frame.SIZE[opcode];
/*  807 */         if (size > this.maxStackSize) {
/*  808 */           this.maxStackSize = size;
/*      */         }
/*  810 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  814 */     if (this.compute != 3) {
/*      */       int n;
/*      */       
/*  817 */       if (opcode == 22 || opcode == 24 || opcode == 55 || opcode == 57) {
/*      */         
/*  819 */         n = var + 2;
/*      */       } else {
/*  821 */         n = var + 1;
/*      */       } 
/*  823 */       if (n > this.maxLocals) {
/*  824 */         this.maxLocals = n;
/*      */       }
/*      */     } 
/*      */     
/*  828 */     if (var < 4 && opcode != 169) {
/*      */       int opt;
/*  830 */       if (opcode < 54) {
/*      */         
/*  832 */         opt = 26 + (opcode - 21 << 2) + var;
/*      */       } else {
/*      */         
/*  835 */         opt = 59 + (opcode - 54 << 2) + var;
/*      */       } 
/*  837 */       this.code.putByte(opt);
/*  838 */     } else if (var >= 256) {
/*  839 */       this.code.putByte(196).put12(opcode, var);
/*      */     } else {
/*  841 */       this.code.put11(opcode, var);
/*      */     } 
/*  843 */     if (opcode >= 54 && this.compute == 0 && this.handlerCount > 0) {
/*  844 */       visitLabel(new Label());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitTypeInsn(int opcode, String type) {
/*  850 */     this.lastCodeOffset = this.code.length;
/*  851 */     Item i = this.cw.newClassItem(type);
/*      */     
/*  853 */     if (this.currentBlock != null) {
/*  854 */       if (this.compute == 0 || this.compute == 1) {
/*  855 */         this.currentBlock.frame.execute(opcode, this.code.length, this.cw, i);
/*  856 */       } else if (opcode == 187) {
/*      */ 
/*      */         
/*  859 */         int size = this.stackSize + 1;
/*  860 */         if (size > this.maxStackSize) {
/*  861 */           this.maxStackSize = size;
/*      */         }
/*  863 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  867 */     this.code.put12(opcode, i.index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitFieldInsn(int opcode, String owner, String name, String desc) {
/*  873 */     this.lastCodeOffset = this.code.length;
/*  874 */     Item i = this.cw.newFieldItem(owner, name, desc);
/*      */     
/*  876 */     if (this.currentBlock != null) {
/*  877 */       if (this.compute == 0 || this.compute == 1) {
/*  878 */         this.currentBlock.frame.execute(opcode, 0, this.cw, i);
/*      */       } else {
/*      */         int size;
/*      */         
/*  882 */         char c = desc.charAt(0);
/*  883 */         switch (opcode) {
/*      */           case 178:
/*  885 */             size = this.stackSize + ((c == 'D' || c == 'J') ? 2 : 1);
/*      */             break;
/*      */           case 179:
/*  888 */             size = this.stackSize + ((c == 'D' || c == 'J') ? -2 : -1);
/*      */             break;
/*      */           case 180:
/*  891 */             size = this.stackSize + ((c == 'D' || c == 'J') ? 1 : 0);
/*      */             break;
/*      */           
/*      */           default:
/*  895 */             size = this.stackSize + ((c == 'D' || c == 'J') ? -3 : -2);
/*      */             break;
/*      */         } 
/*      */         
/*  899 */         if (size > this.maxStackSize) {
/*  900 */           this.maxStackSize = size;
/*      */         }
/*  902 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  906 */     this.code.put12(opcode, i.index);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitMethodInsn(int opcode, String owner, String name, String desc, boolean itf) {
/*  912 */     this.lastCodeOffset = this.code.length;
/*  913 */     Item i = this.cw.newMethodItem(owner, name, desc, itf);
/*  914 */     int argSize = i.intVal;
/*      */     
/*  916 */     if (this.currentBlock != null) {
/*  917 */       if (this.compute == 0 || this.compute == 1) {
/*  918 */         this.currentBlock.frame.execute(opcode, 0, this.cw, i);
/*      */       } else {
/*      */         int size;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  928 */         if (argSize == 0) {
/*      */ 
/*      */           
/*  931 */           argSize = Type.getArgumentsAndReturnSizes(desc);
/*      */ 
/*      */           
/*  934 */           i.intVal = argSize;
/*      */         } 
/*      */         
/*  937 */         if (opcode == 184) {
/*  938 */           size = this.stackSize - (argSize >> 2) + (argSize & 0x3) + 1;
/*      */         } else {
/*  940 */           size = this.stackSize - (argSize >> 2) + (argSize & 0x3);
/*      */         } 
/*      */         
/*  943 */         if (size > this.maxStackSize) {
/*  944 */           this.maxStackSize = size;
/*      */         }
/*  946 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  950 */     if (opcode == 185) {
/*  951 */       if (argSize == 0) {
/*  952 */         argSize = Type.getArgumentsAndReturnSizes(desc);
/*  953 */         i.intVal = argSize;
/*      */       } 
/*  955 */       this.code.put12(185, i.index).put11(argSize >> 2, 0);
/*      */     } else {
/*  957 */       this.code.put12(opcode, i.index);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitInvokeDynamicInsn(String name, String desc, Handle bsm, Object... bsmArgs) {
/*  964 */     this.lastCodeOffset = this.code.length;
/*  965 */     Item i = this.cw.newInvokeDynamicItem(name, desc, bsm, bsmArgs);
/*  966 */     int argSize = i.intVal;
/*      */     
/*  968 */     if (this.currentBlock != null) {
/*  969 */       if (this.compute == 0 || this.compute == 1) {
/*  970 */         this.currentBlock.frame.execute(186, 0, this.cw, i);
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/*  980 */         if (argSize == 0) {
/*      */ 
/*      */           
/*  983 */           argSize = Type.getArgumentsAndReturnSizes(desc);
/*      */ 
/*      */           
/*  986 */           i.intVal = argSize;
/*      */         } 
/*  988 */         int size = this.stackSize - (argSize >> 2) + (argSize & 0x3) + 1;
/*      */ 
/*      */         
/*  991 */         if (size > this.maxStackSize) {
/*  992 */           this.maxStackSize = size;
/*      */         }
/*  994 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/*  998 */     this.code.put12(186, i.index);
/*  999 */     this.code.putShort(0);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitJumpInsn(int opcode, Label label) {
/* 1004 */     boolean isWide = (opcode >= 200);
/* 1005 */     opcode = isWide ? (opcode - 33) : opcode;
/* 1006 */     this.lastCodeOffset = this.code.length;
/* 1007 */     Label nextInsn = null;
/*      */     
/* 1009 */     if (this.currentBlock != null) {
/* 1010 */       if (this.compute == 0) {
/* 1011 */         this.currentBlock.frame.execute(opcode, 0, null, null);
/*      */         
/* 1013 */         (label.getFirst()).status |= 0x10;
/*      */         
/* 1015 */         addSuccessor(0, label);
/* 1016 */         if (opcode != 167)
/*      */         {
/* 1018 */           nextInsn = new Label();
/*      */         }
/* 1020 */       } else if (this.compute == 1) {
/* 1021 */         this.currentBlock.frame.execute(opcode, 0, null, null);
/*      */       }
/* 1023 */       else if (opcode == 168) {
/* 1024 */         if ((label.status & 0x200) == 0) {
/* 1025 */           label.status |= 0x200;
/* 1026 */           this.subroutines++;
/*      */         } 
/* 1028 */         this.currentBlock.status |= 0x80;
/* 1029 */         addSuccessor(this.stackSize + 1, label);
/*      */         
/* 1031 */         nextInsn = new Label();
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*      */       }
/*      */       else {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1042 */         this.stackSize += Frame.SIZE[opcode];
/* 1043 */         addSuccessor(this.stackSize, label);
/*      */       } 
/*      */     }
/*      */ 
/*      */     
/* 1048 */     if ((label.status & 0x2) != 0 && label.position - this.code.length < -32768) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1057 */       if (opcode == 167) {
/* 1058 */         this.code.putByte(200);
/* 1059 */       } else if (opcode == 168) {
/* 1060 */         this.code.putByte(201);
/*      */       }
/*      */       else {
/*      */         
/* 1064 */         if (nextInsn != null) {
/* 1065 */           nextInsn.status |= 0x10;
/*      */         }
/* 1067 */         this.code.putByte((opcode <= 166) ? ((opcode + 1 ^ 0x1) - 1) : (opcode ^ 0x1));
/*      */         
/* 1069 */         this.code.putShort(8);
/* 1070 */         this.code.putByte(200);
/*      */       } 
/* 1072 */       label.put(this, this.code, this.code.length - 1, true);
/* 1073 */     } else if (isWide) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1079 */       this.code.putByte(opcode + 33);
/* 1080 */       label.put(this, this.code, this.code.length - 1, true);
/*      */ 
/*      */     
/*      */     }
/*      */     else {
/*      */ 
/*      */ 
/*      */       
/* 1088 */       this.code.putByte(opcode);
/* 1089 */       label.put(this, this.code, this.code.length - 1, false);
/*      */     } 
/* 1091 */     if (this.currentBlock != null) {
/* 1092 */       if (nextInsn != null)
/*      */       {
/*      */ 
/*      */ 
/*      */         
/* 1097 */         visitLabel(nextInsn);
/*      */       }
/* 1099 */       if (opcode == 167) {
/* 1100 */         noSuccessor();
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLabel(Label label) {
/* 1108 */     this.cw.hasAsmInsns |= label.resolve(this, this.code.length, this.code.data);
/*      */     
/* 1110 */     if ((label.status & 0x1) != 0) {
/*      */       return;
/*      */     }
/* 1113 */     if (this.compute == 0) {
/* 1114 */       if (this.currentBlock != null) {
/* 1115 */         if (label.position == this.currentBlock.position) {
/*      */           
/* 1117 */           this.currentBlock.status |= label.status & 0x10;
/* 1118 */           label.frame = this.currentBlock.frame;
/*      */           
/*      */           return;
/*      */         } 
/* 1122 */         addSuccessor(0, label);
/*      */       } 
/*      */       
/* 1125 */       this.currentBlock = label;
/* 1126 */       if (label.frame == null) {
/* 1127 */         label.frame = new Frame();
/* 1128 */         label.frame.owner = label;
/*      */       } 
/*      */       
/* 1131 */       if (this.previousBlock != null) {
/* 1132 */         if (label.position == this.previousBlock.position) {
/* 1133 */           this.previousBlock.status |= label.status & 0x10;
/* 1134 */           label.frame = this.previousBlock.frame;
/* 1135 */           this.currentBlock = this.previousBlock;
/*      */           return;
/*      */         } 
/* 1138 */         this.previousBlock.successor = label;
/*      */       } 
/* 1140 */       this.previousBlock = label;
/* 1141 */     } else if (this.compute == 1) {
/* 1142 */       if (this.currentBlock == null) {
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1147 */         this.currentBlock = label;
/*      */       }
/*      */       else {
/*      */         
/* 1151 */         this.currentBlock.frame.owner = label;
/*      */       } 
/* 1153 */     } else if (this.compute == 2) {
/* 1154 */       if (this.currentBlock != null) {
/*      */         
/* 1156 */         this.currentBlock.outputStackMax = this.maxStackSize;
/* 1157 */         addSuccessor(this.stackSize, label);
/*      */       } 
/*      */       
/* 1160 */       this.currentBlock = label;
/*      */       
/* 1162 */       this.stackSize = 0;
/* 1163 */       this.maxStackSize = 0;
/*      */       
/* 1165 */       if (this.previousBlock != null) {
/* 1166 */         this.previousBlock.successor = label;
/*      */       }
/* 1168 */       this.previousBlock = label;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLdcInsn(Object cst) {
/* 1174 */     this.lastCodeOffset = this.code.length;
/* 1175 */     Item i = this.cw.newConstItem(cst);
/*      */     
/* 1177 */     if (this.currentBlock != null) {
/* 1178 */       if (this.compute == 0 || this.compute == 1) {
/* 1179 */         this.currentBlock.frame.execute(18, 0, this.cw, i);
/*      */       } else {
/*      */         int size;
/*      */         
/* 1183 */         if (i.type == 5 || i.type == 6) {
/* 1184 */           size = this.stackSize + 2;
/*      */         } else {
/* 1186 */           size = this.stackSize + 1;
/*      */         } 
/*      */         
/* 1189 */         if (size > this.maxStackSize) {
/* 1190 */           this.maxStackSize = size;
/*      */         }
/* 1192 */         this.stackSize = size;
/*      */       } 
/*      */     }
/*      */     
/* 1196 */     int index = i.index;
/* 1197 */     if (i.type == 5 || i.type == 6) {
/* 1198 */       this.code.put12(20, index);
/* 1199 */     } else if (index >= 256) {
/* 1200 */       this.code.put12(19, index);
/*      */     } else {
/* 1202 */       this.code.put11(18, index);
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitIincInsn(int var, int increment) {
/* 1208 */     this.lastCodeOffset = this.code.length;
/* 1209 */     if (this.currentBlock != null && (
/* 1210 */       this.compute == 0 || this.compute == 1)) {
/* 1211 */       this.currentBlock.frame.execute(132, var, null, null);
/*      */     }
/*      */     
/* 1214 */     if (this.compute != 3) {
/*      */       
/* 1216 */       int n = var + 1;
/* 1217 */       if (n > this.maxLocals) {
/* 1218 */         this.maxLocals = n;
/*      */       }
/*      */     } 
/*      */     
/* 1222 */     if (var > 255 || increment > 127 || increment < -128) {
/* 1223 */       this.code.putByte(196).put12(132, var)
/* 1224 */         .putShort(increment);
/*      */     } else {
/* 1226 */       this.code.putByte(132).put11(var, increment);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTableSwitchInsn(int min, int max, Label dflt, Label... labels) {
/* 1233 */     this.lastCodeOffset = this.code.length;
/*      */     
/* 1235 */     int source = this.code.length;
/* 1236 */     this.code.putByte(170);
/* 1237 */     this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
/* 1238 */     dflt.put(this, this.code, source, true);
/* 1239 */     this.code.putInt(min).putInt(max);
/* 1240 */     for (int i = 0; i < labels.length; i++) {
/* 1241 */       labels[i].put(this, this.code, source, true);
/*      */     }
/*      */     
/* 1244 */     visitSwitchInsn(dflt, labels);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLookupSwitchInsn(Label dflt, int[] keys, Label[] labels) {
/* 1250 */     this.lastCodeOffset = this.code.length;
/*      */     
/* 1252 */     int source = this.code.length;
/* 1253 */     this.code.putByte(171);
/* 1254 */     this.code.putByteArray(null, 0, (4 - this.code.length % 4) % 4);
/* 1255 */     dflt.put(this, this.code, source, true);
/* 1256 */     this.code.putInt(labels.length);
/* 1257 */     for (int i = 0; i < labels.length; i++) {
/* 1258 */       this.code.putInt(keys[i]);
/* 1259 */       labels[i].put(this, this.code, source, true);
/*      */     } 
/*      */     
/* 1262 */     visitSwitchInsn(dflt, labels);
/*      */   }
/*      */ 
/*      */   
/*      */   private void visitSwitchInsn(Label dflt, Label[] labels) {
/* 1267 */     if (this.currentBlock != null) {
/* 1268 */       if (this.compute == 0) {
/* 1269 */         this.currentBlock.frame.execute(171, 0, null, null);
/*      */         
/* 1271 */         addSuccessor(0, dflt);
/* 1272 */         (dflt.getFirst()).status |= 0x10;
/* 1273 */         for (int i = 0; i < labels.length; i++) {
/* 1274 */           addSuccessor(0, labels[i]);
/* 1275 */           (labels[i].getFirst()).status |= 0x10;
/*      */         } 
/*      */       } else {
/*      */         
/* 1279 */         this.stackSize--;
/*      */         
/* 1281 */         addSuccessor(this.stackSize, dflt);
/* 1282 */         for (int i = 0; i < labels.length; i++) {
/* 1283 */           addSuccessor(this.stackSize, labels[i]);
/*      */         }
/*      */       } 
/*      */       
/* 1287 */       noSuccessor();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMultiANewArrayInsn(String desc, int dims) {
/* 1293 */     this.lastCodeOffset = this.code.length;
/* 1294 */     Item i = this.cw.newClassItem(desc);
/*      */     
/* 1296 */     if (this.currentBlock != null) {
/* 1297 */       if (this.compute == 0 || this.compute == 1) {
/* 1298 */         this.currentBlock.frame.execute(197, dims, this.cw, i);
/*      */       }
/*      */       else {
/*      */         
/* 1302 */         this.stackSize += 1 - dims;
/*      */       } 
/*      */     }
/*      */     
/* 1306 */     this.code.put12(197, i.index).putByte(dims);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitInsnAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 1315 */     ByteVector bv = new ByteVector();
/*      */     
/* 1317 */     typeRef = typeRef & 0xFF0000FF | this.lastCodeOffset << 8;
/* 1318 */     AnnotationWriter.putTarget(typeRef, typePath, bv);
/*      */     
/* 1320 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/* 1321 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, bv.length - 2);
/*      */     
/* 1323 */     if (visible) {
/* 1324 */       aw.next = this.ctanns;
/* 1325 */       this.ctanns = aw;
/*      */     } else {
/* 1327 */       aw.next = this.ictanns;
/* 1328 */       this.ictanns = aw;
/*      */     } 
/* 1330 */     return aw;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitTryCatchBlock(Label start, Label end, Label handler, String type) {
/* 1336 */     this.handlerCount++;
/* 1337 */     Handler h = new Handler();
/* 1338 */     h.start = start;
/* 1339 */     h.end = end;
/* 1340 */     h.handler = handler;
/* 1341 */     h.desc = type;
/* 1342 */     h.type = (type != null) ? this.cw.newClass(type) : 0;
/* 1343 */     if (this.lastHandler == null) {
/* 1344 */       this.firstHandler = h;
/*      */     } else {
/* 1346 */       this.lastHandler.next = h;
/*      */     } 
/* 1348 */     this.lastHandler = h;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public AnnotationVisitor visitTryCatchAnnotation(int typeRef, TypePath typePath, String desc, boolean visible) {
/* 1357 */     ByteVector bv = new ByteVector();
/*      */     
/* 1359 */     AnnotationWriter.putTarget(typeRef, typePath, bv);
/*      */     
/* 1361 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/* 1362 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, bv.length - 2);
/*      */     
/* 1364 */     if (visible) {
/* 1365 */       aw.next = this.ctanns;
/* 1366 */       this.ctanns = aw;
/*      */     } else {
/* 1368 */       aw.next = this.ictanns;
/* 1369 */       this.ictanns = aw;
/*      */     } 
/* 1371 */     return aw;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void visitLocalVariable(String name, String desc, String signature, Label start, Label end, int index) {
/* 1378 */     if (signature != null) {
/* 1379 */       if (this.localVarType == null) {
/* 1380 */         this.localVarType = new ByteVector();
/*      */       }
/* 1382 */       this.localVarTypeCount++;
/* 1383 */       this.localVarType.putShort(start.position)
/* 1384 */         .putShort(end.position - start.position)
/* 1385 */         .putShort(this.cw.newUTF8(name)).putShort(this.cw.newUTF8(signature))
/* 1386 */         .putShort(index);
/*      */     } 
/* 1388 */     if (this.localVar == null) {
/* 1389 */       this.localVar = new ByteVector();
/*      */     }
/* 1391 */     this.localVarCount++;
/* 1392 */     this.localVar.putShort(start.position)
/* 1393 */       .putShort(end.position - start.position)
/* 1394 */       .putShort(this.cw.newUTF8(name)).putShort(this.cw.newUTF8(desc))
/* 1395 */       .putShort(index);
/* 1396 */     if (this.compute != 3) {
/*      */       
/* 1398 */       char c = desc.charAt(0);
/* 1399 */       int n = index + ((c == 'J' || c == 'D') ? 2 : 1);
/* 1400 */       if (n > this.maxLocals) {
/* 1401 */         this.maxLocals = n;
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
/*      */   public AnnotationVisitor visitLocalVariableAnnotation(int typeRef, TypePath typePath, Label[] start, Label[] end, int[] index, String desc, boolean visible) {
/* 1413 */     ByteVector bv = new ByteVector();
/*      */     
/* 1415 */     bv.putByte(typeRef >>> 24).putShort(start.length);
/* 1416 */     for (int i = 0; i < start.length; i++) {
/* 1417 */       bv.putShort((start[i]).position)
/* 1418 */         .putShort((end[i]).position - (start[i]).position)
/* 1419 */         .putShort(index[i]);
/*      */     }
/* 1421 */     if (typePath == null) {
/* 1422 */       bv.putByte(0);
/*      */     } else {
/* 1424 */       int length = typePath.b[typePath.offset] * 2 + 1;
/* 1425 */       bv.putByteArray(typePath.b, typePath.offset, length);
/*      */     } 
/*      */     
/* 1428 */     bv.putShort(this.cw.newUTF8(desc)).putShort(0);
/* 1429 */     AnnotationWriter aw = new AnnotationWriter(this.cw, true, bv, bv, bv.length - 2);
/*      */     
/* 1431 */     if (visible) {
/* 1432 */       aw.next = this.ctanns;
/* 1433 */       this.ctanns = aw;
/*      */     } else {
/* 1435 */       aw.next = this.ictanns;
/* 1436 */       this.ictanns = aw;
/*      */     } 
/* 1438 */     return aw;
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitLineNumber(int line, Label start) {
/* 1443 */     if (this.lineNumber == null) {
/* 1444 */       this.lineNumber = new ByteVector();
/*      */     }
/* 1446 */     this.lineNumberCount++;
/* 1447 */     this.lineNumber.putShort(start.position);
/* 1448 */     this.lineNumber.putShort(line);
/*      */   }
/*      */ 
/*      */   
/*      */   public void visitMaxs(int maxStack, int maxLocals) {
/* 1453 */     if (this.compute == 0) {
/*      */       
/* 1455 */       Handler handler = this.firstHandler;
/* 1456 */       while (handler != null) {
/* 1457 */         Label label1 = handler.start.getFirst();
/* 1458 */         Label h = handler.handler.getFirst();
/* 1459 */         Label e = handler.end.getFirst();
/*      */         
/* 1461 */         String t = (handler.desc == null) ? "java/lang/Throwable" : handler.desc;
/*      */         
/* 1463 */         int kind = 0x1700000 | this.cw.addType(t);
/*      */         
/* 1465 */         h.status |= 0x10;
/*      */         
/* 1467 */         while (label1 != e) {
/*      */           
/* 1469 */           Edge b = new Edge();
/* 1470 */           b.info = kind;
/* 1471 */           b.successor = h;
/*      */           
/* 1473 */           b.next = label1.successors;
/* 1474 */           label1.successors = b;
/*      */           
/* 1476 */           label1 = label1.successor;
/*      */         } 
/* 1478 */         handler = handler.next;
/*      */       } 
/*      */ 
/*      */       
/* 1482 */       Frame f = this.labels.frame;
/* 1483 */       f.initInputFrame(this.cw, this.access, Type.getArgumentTypes(this.descriptor), this.maxLocals);
/*      */       
/* 1485 */       visitFrame(f);
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1493 */       int max = 0;
/* 1494 */       Label changed = this.labels;
/* 1495 */       while (changed != null) {
/*      */         
/* 1497 */         Label label = changed;
/* 1498 */         changed = changed.next;
/* 1499 */         label.next = null;
/* 1500 */         f = label.frame;
/*      */         
/* 1502 */         if ((label.status & 0x10) != 0) {
/* 1503 */           label.status |= 0x20;
/*      */         }
/*      */         
/* 1506 */         label.status |= 0x40;
/*      */         
/* 1508 */         int blockMax = f.inputStack.length + label.outputStackMax;
/* 1509 */         if (blockMax > max) {
/* 1510 */           max = blockMax;
/*      */         }
/*      */         
/* 1513 */         Edge e = label.successors;
/* 1514 */         while (e != null) {
/* 1515 */           Label n = e.successor.getFirst();
/* 1516 */           boolean change = f.merge(this.cw, n.frame, e.info);
/* 1517 */           if (change && n.next == null) {
/*      */ 
/*      */             
/* 1520 */             n.next = changed;
/* 1521 */             changed = n;
/*      */           } 
/* 1523 */           e = e.next;
/*      */         } 
/*      */       } 
/*      */ 
/*      */       
/* 1528 */       Label l = this.labels;
/* 1529 */       while (l != null) {
/* 1530 */         f = l.frame;
/* 1531 */         if ((l.status & 0x20) != 0) {
/* 1532 */           visitFrame(f);
/*      */         }
/* 1534 */         if ((l.status & 0x40) == 0) {
/*      */           
/* 1536 */           Label k = l.successor;
/* 1537 */           int start = l.position;
/* 1538 */           int end = ((k == null) ? this.code.length : k.position) - 1;
/*      */           
/* 1540 */           if (end >= start) {
/* 1541 */             max = Math.max(max, 1);
/*      */             
/* 1543 */             for (int i = start; i < end; i++) {
/* 1544 */               this.code.data[i] = 0;
/*      */             }
/* 1546 */             this.code.data[end] = -65;
/*      */             
/* 1548 */             int frameIndex = startFrame(start, 0, 1);
/* 1549 */             this.frame[frameIndex] = 0x1700000 | this.cw
/* 1550 */               .addType("java/lang/Throwable");
/* 1551 */             endFrame();
/*      */ 
/*      */             
/* 1554 */             this.firstHandler = Handler.remove(this.firstHandler, l, k);
/*      */           } 
/*      */         } 
/* 1557 */         l = l.successor;
/*      */       } 
/*      */       
/* 1560 */       handler = this.firstHandler;
/* 1561 */       this.handlerCount = 0;
/* 1562 */       while (handler != null) {
/* 1563 */         this.handlerCount++;
/* 1564 */         handler = handler.next;
/*      */       } 
/*      */       
/* 1567 */       this.maxStack = max;
/* 1568 */     } else if (this.compute == 2) {
/*      */       
/* 1570 */       Handler handler = this.firstHandler;
/* 1571 */       while (handler != null) {
/* 1572 */         Label l = handler.start;
/* 1573 */         Label h = handler.handler;
/* 1574 */         Label e = handler.end;
/*      */         
/* 1576 */         while (l != e) {
/*      */           
/* 1578 */           Edge b = new Edge();
/* 1579 */           b.info = Integer.MAX_VALUE;
/* 1580 */           b.successor = h;
/*      */           
/* 1582 */           if ((l.status & 0x80) == 0) {
/* 1583 */             b.next = l.successors;
/* 1584 */             l.successors = b;
/*      */           
/*      */           }
/*      */           else {
/*      */             
/* 1589 */             b.next = l.successors.next.next;
/* 1590 */             l.successors.next.next = b;
/*      */           } 
/*      */           
/* 1593 */           l = l.successor;
/*      */         } 
/* 1595 */         handler = handler.next;
/*      */       } 
/*      */       
/* 1598 */       if (this.subroutines > 0) {
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1605 */         int id = 0;
/* 1606 */         this.labels.visitSubroutine(null, 1L, this.subroutines);
/*      */         
/* 1608 */         Label l = this.labels;
/* 1609 */         while (l != null) {
/* 1610 */           if ((l.status & 0x80) != 0) {
/*      */             
/* 1612 */             Label subroutine = l.successors.next.successor;
/*      */             
/* 1614 */             if ((subroutine.status & 0x400) == 0) {
/*      */               
/* 1616 */               id++;
/* 1617 */               subroutine.visitSubroutine(null, id / 32L << 32L | 1L << id % 32, this.subroutines);
/*      */             } 
/*      */           } 
/*      */           
/* 1621 */           l = l.successor;
/*      */         } 
/*      */         
/* 1624 */         l = this.labels;
/* 1625 */         while (l != null) {
/* 1626 */           if ((l.status & 0x80) != 0) {
/* 1627 */             Label L = this.labels;
/* 1628 */             while (L != null) {
/* 1629 */               L.status &= 0xFFFFF7FF;
/* 1630 */               L = L.successor;
/*      */             } 
/*      */             
/* 1633 */             Label subroutine = l.successors.next.successor;
/* 1634 */             subroutine.visitSubroutine(l, 0L, this.subroutines);
/*      */           } 
/* 1636 */           l = l.successor;
/*      */         } 
/*      */       } 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1650 */       int max = 0;
/* 1651 */       Label stack = this.labels;
/* 1652 */       while (stack != null) {
/*      */         
/* 1654 */         Label l = stack;
/* 1655 */         stack = stack.next;
/*      */         
/* 1657 */         int start = l.inputStackTop;
/* 1658 */         int blockMax = start + l.outputStackMax;
/*      */         
/* 1660 */         if (blockMax > max) {
/* 1661 */           max = blockMax;
/*      */         }
/*      */         
/* 1664 */         Edge b = l.successors;
/* 1665 */         if ((l.status & 0x80) != 0)
/*      */         {
/* 1667 */           b = b.next;
/*      */         }
/* 1669 */         while (b != null) {
/* 1670 */           l = b.successor;
/*      */           
/* 1672 */           if ((l.status & 0x8) == 0) {
/*      */             
/* 1674 */             l.inputStackTop = (b.info == Integer.MAX_VALUE) ? 1 : (start + b.info);
/*      */ 
/*      */             
/* 1677 */             l.status |= 0x8;
/* 1678 */             l.next = stack;
/* 1679 */             stack = l;
/*      */           } 
/* 1681 */           b = b.next;
/*      */         } 
/*      */       } 
/* 1684 */       this.maxStack = Math.max(maxStack, max);
/*      */     } else {
/* 1686 */       this.maxStack = maxStack;
/* 1687 */       this.maxLocals = maxLocals;
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
/*      */   public void visitEnd() {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void addSuccessor(int info, Label successor) {
/* 1709 */     Edge b = new Edge();
/* 1710 */     b.info = info;
/* 1711 */     b.successor = successor;
/*      */     
/* 1713 */     b.next = this.currentBlock.successors;
/* 1714 */     this.currentBlock.successors = b;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void noSuccessor() {
/* 1722 */     if (this.compute == 0) {
/* 1723 */       Label l = new Label();
/* 1724 */       l.frame = new Frame();
/* 1725 */       l.frame.owner = l;
/* 1726 */       l.resolve(this, this.code.length, this.code.data);
/* 1727 */       this.previousBlock.successor = l;
/* 1728 */       this.previousBlock = l;
/*      */     } else {
/* 1730 */       this.currentBlock.outputStackMax = this.maxStackSize;
/*      */     } 
/* 1732 */     if (this.compute != 1) {
/* 1733 */       this.currentBlock = null;
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
/*      */   private void visitFrame(Frame f) {
/* 1749 */     int nTop = 0;
/* 1750 */     int nLocal = 0;
/* 1751 */     int nStack = 0;
/* 1752 */     int[] locals = f.inputLocals;
/* 1753 */     int[] stacks = f.inputStack;
/*      */     
/*      */     int i;
/* 1756 */     for (i = 0; i < locals.length; i++) {
/* 1757 */       int t = locals[i];
/* 1758 */       if (t == 16777216) {
/* 1759 */         nTop++;
/*      */       } else {
/* 1761 */         nLocal += nTop + 1;
/* 1762 */         nTop = 0;
/*      */       } 
/* 1764 */       if (t == 16777220 || t == 16777219) {
/* 1765 */         i++;
/*      */       }
/*      */     } 
/*      */ 
/*      */     
/* 1770 */     for (i = 0; i < stacks.length; i++) {
/* 1771 */       int t = stacks[i];
/* 1772 */       nStack++;
/* 1773 */       if (t == 16777220 || t == 16777219) {
/* 1774 */         i++;
/*      */       }
/*      */     } 
/*      */     
/* 1778 */     int frameIndex = startFrame(f.owner.position, nLocal, nStack);
/* 1779 */     for (i = 0; nLocal > 0; i++, nLocal--) {
/* 1780 */       int t = locals[i];
/* 1781 */       this.frame[frameIndex++] = t;
/* 1782 */       if (t == 16777220 || t == 16777219) {
/* 1783 */         i++;
/*      */       }
/*      */     } 
/* 1786 */     for (i = 0; i < stacks.length; i++) {
/* 1787 */       int t = stacks[i];
/* 1788 */       this.frame[frameIndex++] = t;
/* 1789 */       if (t == 16777220 || t == 16777219) {
/* 1790 */         i++;
/*      */       }
/*      */     } 
/* 1793 */     endFrame();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void visitImplicitFirstFrame() {
/* 1801 */     int frameIndex = startFrame(0, this.descriptor.length() + 1, 0);
/* 1802 */     if ((this.access & 0x8) == 0) {
/* 1803 */       if ((this.access & 0x80000) == 0) {
/* 1804 */         this.frame[frameIndex++] = 0x1700000 | this.cw.addType(this.cw.thisName);
/*      */       } else {
/* 1806 */         this.frame[frameIndex++] = 6;
/*      */       } 
/*      */     }
/* 1809 */     int i = 1;
/*      */     while (true) {
/* 1811 */       int j = i;
/* 1812 */       switch (this.descriptor.charAt(i++)) {
/*      */         case 'B':
/*      */         case 'C':
/*      */         case 'I':
/*      */         case 'S':
/*      */         case 'Z':
/* 1818 */           this.frame[frameIndex++] = 1;
/*      */           continue;
/*      */         case 'F':
/* 1821 */           this.frame[frameIndex++] = 2;
/*      */           continue;
/*      */         case 'J':
/* 1824 */           this.frame[frameIndex++] = 4;
/*      */           continue;
/*      */         case 'D':
/* 1827 */           this.frame[frameIndex++] = 3;
/*      */           continue;
/*      */         case '[':
/* 1830 */           while (this.descriptor.charAt(i) == '[') {
/* 1831 */             i++;
/*      */           }
/* 1833 */           if (this.descriptor.charAt(i) == 'L') {
/* 1834 */             i++;
/* 1835 */             while (this.descriptor.charAt(i) != ';') {
/* 1836 */               i++;
/*      */             }
/*      */           } 
/* 1839 */           this.frame[frameIndex++] = 0x1700000 | this.cw
/* 1840 */             .addType(this.descriptor.substring(j, ++i));
/*      */           continue;
/*      */         case 'L':
/* 1843 */           while (this.descriptor.charAt(i) != ';') {
/* 1844 */             i++;
/*      */           }
/* 1846 */           this.frame[frameIndex++] = 0x1700000 | this.cw
/* 1847 */             .addType(this.descriptor.substring(j + 1, i++));
/*      */           continue;
/*      */       } 
/*      */       
/*      */       break;
/*      */     } 
/* 1853 */     this.frame[1] = frameIndex - 3;
/* 1854 */     endFrame();
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
/*      */   private int startFrame(int offset, int nLocal, int nStack) {
/* 1869 */     int n = 3 + nLocal + nStack;
/* 1870 */     if (this.frame == null || this.frame.length < n) {
/* 1871 */       this.frame = new int[n];
/*      */     }
/* 1873 */     this.frame[0] = offset;
/* 1874 */     this.frame[1] = nLocal;
/* 1875 */     this.frame[2] = nStack;
/* 1876 */     return 3;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void endFrame() {
/* 1884 */     if (this.previousFrame != null) {
/* 1885 */       if (this.stackMap == null) {
/* 1886 */         this.stackMap = new ByteVector();
/*      */       }
/* 1888 */       writeFrame();
/* 1889 */       this.frameCount++;
/*      */     } 
/* 1891 */     this.previousFrame = this.frame;
/* 1892 */     this.frame = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void writeFrame() {
/* 1900 */     int delta, clocalsSize = this.frame[1];
/* 1901 */     int cstackSize = this.frame[2];
/* 1902 */     if ((this.cw.version & 0xFFFF) < 50) {
/* 1903 */       this.stackMap.putShort(this.frame[0]).putShort(clocalsSize);
/* 1904 */       writeFrameTypes(3, 3 + clocalsSize);
/* 1905 */       this.stackMap.putShort(cstackSize);
/* 1906 */       writeFrameTypes(3 + clocalsSize, 3 + clocalsSize + cstackSize);
/*      */       return;
/*      */     } 
/* 1909 */     int localsSize = this.previousFrame[1];
/* 1910 */     int type = 255;
/* 1911 */     int k = 0;
/*      */     
/* 1913 */     if (this.frameCount == 0) {
/* 1914 */       delta = this.frame[0];
/*      */     } else {
/* 1916 */       delta = this.frame[0] - this.previousFrame[0] - 1;
/*      */     } 
/* 1918 */     if (cstackSize == 0) {
/* 1919 */       k = clocalsSize - localsSize;
/* 1920 */       switch (k) {
/*      */         case -3:
/*      */         case -2:
/*      */         case -1:
/* 1924 */           type = 248;
/* 1925 */           localsSize = clocalsSize;
/*      */           break;
/*      */         case 0:
/* 1928 */           type = (delta < 64) ? 0 : 251;
/*      */           break;
/*      */         case 1:
/*      */         case 2:
/*      */         case 3:
/* 1933 */           type = 252;
/*      */           break;
/*      */       } 
/* 1936 */     } else if (clocalsSize == localsSize && cstackSize == 1) {
/* 1937 */       type = (delta < 63) ? 64 : 247;
/*      */     } 
/*      */     
/* 1940 */     if (type != 255) {
/*      */       
/* 1942 */       int l = 3;
/* 1943 */       for (int j = 0; j < localsSize; j++) {
/* 1944 */         if (this.frame[l] != this.previousFrame[l]) {
/* 1945 */           type = 255;
/*      */           break;
/*      */         } 
/* 1948 */         l++;
/*      */       } 
/*      */     } 
/* 1951 */     switch (type) {
/*      */       case 0:
/* 1953 */         this.stackMap.putByte(delta);
/*      */         return;
/*      */       case 64:
/* 1956 */         this.stackMap.putByte(64 + delta);
/* 1957 */         writeFrameTypes(3 + clocalsSize, 4 + clocalsSize);
/*      */         return;
/*      */       case 247:
/* 1960 */         this.stackMap.putByte(247).putShort(delta);
/*      */         
/* 1962 */         writeFrameTypes(3 + clocalsSize, 4 + clocalsSize);
/*      */         return;
/*      */       case 251:
/* 1965 */         this.stackMap.putByte(251).putShort(delta);
/*      */         return;
/*      */       case 248:
/* 1968 */         this.stackMap.putByte(251 + k).putShort(delta);
/*      */         return;
/*      */       case 252:
/* 1971 */         this.stackMap.putByte(251 + k).putShort(delta);
/* 1972 */         writeFrameTypes(3 + localsSize, 3 + clocalsSize);
/*      */         return;
/*      */     } 
/*      */     
/* 1976 */     this.stackMap.putByte(255).putShort(delta).putShort(clocalsSize);
/* 1977 */     writeFrameTypes(3, 3 + clocalsSize);
/* 1978 */     this.stackMap.putShort(cstackSize);
/* 1979 */     writeFrameTypes(3 + clocalsSize, 3 + clocalsSize + cstackSize);
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
/*      */   private void writeFrameTypes(int start, int end) {
/* 1995 */     for (int i = start; i < end; i++) {
/* 1996 */       int t = this.frame[i];
/* 1997 */       int d = t & 0xF0000000;
/* 1998 */       if (d == 0) {
/* 1999 */         int v = t & 0xFFFFF;
/* 2000 */         switch (t & 0xFF00000) {
/*      */           case 24117248:
/* 2002 */             this.stackMap.putByte(7).putShort(this.cw
/* 2003 */                 .newClass((this.cw.typeTable[v]).strVal1));
/*      */             break;
/*      */           case 25165824:
/* 2006 */             this.stackMap.putByte(8).putShort((this.cw.typeTable[v]).intVal);
/*      */             break;
/*      */           default:
/* 2009 */             this.stackMap.putByte(v); break;
/*      */         } 
/*      */       } else {
/* 2012 */         StringBuilder sb = new StringBuilder();
/* 2013 */         d >>= 28;
/* 2014 */         while (d-- > 0) {
/* 2015 */           sb.append('[');
/*      */         }
/* 2017 */         if ((t & 0xFF00000) == 24117248) {
/* 2018 */           sb.append('L');
/* 2019 */           sb.append((this.cw.typeTable[t & 0xFFFFF]).strVal1);
/* 2020 */           sb.append(';');
/*      */         } else {
/* 2022 */           switch (t & 0xF) {
/*      */             case 1:
/* 2024 */               sb.append('I');
/*      */               break;
/*      */             case 2:
/* 2027 */               sb.append('F');
/*      */               break;
/*      */             case 3:
/* 2030 */               sb.append('D');
/*      */               break;
/*      */             case 9:
/* 2033 */               sb.append('Z');
/*      */               break;
/*      */             case 10:
/* 2036 */               sb.append('B');
/*      */               break;
/*      */             case 11:
/* 2039 */               sb.append('C');
/*      */               break;
/*      */             case 12:
/* 2042 */               sb.append('S');
/*      */               break;
/*      */             default:
/* 2045 */               sb.append('J'); break;
/*      */           } 
/*      */         } 
/* 2048 */         this.stackMap.putByte(7).putShort(this.cw.newClass(sb.toString()));
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   private void writeFrameType(Object type) {
/* 2054 */     if (type instanceof String) {
/* 2055 */       this.stackMap.putByte(7).putShort(this.cw.newClass((String)type));
/* 2056 */     } else if (type instanceof Integer) {
/* 2057 */       this.stackMap.putByte(((Integer)type).intValue());
/*      */     } else {
/* 2059 */       this.stackMap.putByte(8).putShort(((Label)type).position);
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
/*      */   final int getSize() {
/* 2073 */     if (this.classReaderOffset != 0) {
/* 2074 */       return 6 + this.classReaderLength;
/*      */     }
/* 2076 */     int size = 8;
/* 2077 */     if (this.code.length > 0) {
/* 2078 */       if (this.code.length > 65535) {
/* 2079 */         throw new RuntimeException("Method code too large!");
/*      */       }
/* 2081 */       this.cw.newUTF8("Code");
/* 2082 */       size += 18 + this.code.length + 8 * this.handlerCount;
/* 2083 */       if (this.localVar != null) {
/* 2084 */         this.cw.newUTF8("LocalVariableTable");
/* 2085 */         size += 8 + this.localVar.length;
/*      */       } 
/* 2087 */       if (this.localVarType != null) {
/* 2088 */         this.cw.newUTF8("LocalVariableTypeTable");
/* 2089 */         size += 8 + this.localVarType.length;
/*      */       } 
/* 2091 */       if (this.lineNumber != null) {
/* 2092 */         this.cw.newUTF8("LineNumberTable");
/* 2093 */         size += 8 + this.lineNumber.length;
/*      */       } 
/* 2095 */       if (this.stackMap != null) {
/* 2096 */         boolean zip = ((this.cw.version & 0xFFFF) >= 50);
/* 2097 */         this.cw.newUTF8(zip ? "StackMapTable" : "StackMap");
/* 2098 */         size += 8 + this.stackMap.length;
/*      */       } 
/* 2100 */       if (this.ctanns != null) {
/* 2101 */         this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
/* 2102 */         size += 8 + this.ctanns.getSize();
/*      */       } 
/* 2104 */       if (this.ictanns != null) {
/* 2105 */         this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
/* 2106 */         size += 8 + this.ictanns.getSize();
/*      */       } 
/* 2108 */       if (this.cattrs != null) {
/* 2109 */         size += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
/*      */       }
/*      */     } 
/*      */     
/* 2113 */     if (this.exceptionCount > 0) {
/* 2114 */       this.cw.newUTF8("Exceptions");
/* 2115 */       size += 8 + 2 * this.exceptionCount;
/*      */     } 
/* 2117 */     if ((this.access & 0x1000) != 0 && ((
/* 2118 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0)) {
/*      */       
/* 2120 */       this.cw.newUTF8("Synthetic");
/* 2121 */       size += 6;
/*      */     } 
/*      */     
/* 2124 */     if ((this.access & 0x20000) != 0) {
/* 2125 */       this.cw.newUTF8("Deprecated");
/* 2126 */       size += 6;
/*      */     } 
/* 2128 */     if (this.signature != null) {
/* 2129 */       this.cw.newUTF8("Signature");
/* 2130 */       this.cw.newUTF8(this.signature);
/* 2131 */       size += 8;
/*      */     } 
/* 2133 */     if (this.methodParameters != null) {
/* 2134 */       this.cw.newUTF8("MethodParameters");
/* 2135 */       size += 7 + this.methodParameters.length;
/*      */     } 
/* 2137 */     if (this.annd != null) {
/* 2138 */       this.cw.newUTF8("AnnotationDefault");
/* 2139 */       size += 6 + this.annd.length;
/*      */     } 
/* 2141 */     if (this.anns != null) {
/* 2142 */       this.cw.newUTF8("RuntimeVisibleAnnotations");
/* 2143 */       size += 8 + this.anns.getSize();
/*      */     } 
/* 2145 */     if (this.ianns != null) {
/* 2146 */       this.cw.newUTF8("RuntimeInvisibleAnnotations");
/* 2147 */       size += 8 + this.ianns.getSize();
/*      */     } 
/* 2149 */     if (this.tanns != null) {
/* 2150 */       this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
/* 2151 */       size += 8 + this.tanns.getSize();
/*      */     } 
/* 2153 */     if (this.itanns != null) {
/* 2154 */       this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
/* 2155 */       size += 8 + this.itanns.getSize();
/*      */     } 
/* 2157 */     if (this.panns != null) {
/* 2158 */       this.cw.newUTF8("RuntimeVisibleParameterAnnotations");
/* 2159 */       size += 7 + 2 * (this.panns.length - this.synthetics);
/* 2160 */       for (int i = this.panns.length - 1; i >= this.synthetics; i--) {
/* 2161 */         size += (this.panns[i] == null) ? 0 : this.panns[i].getSize();
/*      */       }
/*      */     } 
/* 2164 */     if (this.ipanns != null) {
/* 2165 */       this.cw.newUTF8("RuntimeInvisibleParameterAnnotations");
/* 2166 */       size += 7 + 2 * (this.ipanns.length - this.synthetics);
/* 2167 */       for (int i = this.ipanns.length - 1; i >= this.synthetics; i--) {
/* 2168 */         size += (this.ipanns[i] == null) ? 0 : this.ipanns[i].getSize();
/*      */       }
/*      */     } 
/* 2171 */     if (this.attrs != null) {
/* 2172 */       size += this.attrs.getSize(this.cw, null, 0, -1, -1);
/*      */     }
/* 2174 */     return size;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   final void put(ByteVector out) {
/* 2185 */     int FACTOR = 64;
/* 2186 */     int mask = 0xE0000 | (this.access & 0x40000) / 64;
/*      */ 
/*      */     
/* 2189 */     out.putShort(this.access & (mask ^ 0xFFFFFFFF)).putShort(this.name).putShort(this.desc);
/* 2190 */     if (this.classReaderOffset != 0) {
/* 2191 */       out.putByteArray(this.cw.cr.b, this.classReaderOffset, this.classReaderLength);
/*      */       return;
/*      */     } 
/* 2194 */     int attributeCount = 0;
/* 2195 */     if (this.code.length > 0) {
/* 2196 */       attributeCount++;
/*      */     }
/* 2198 */     if (this.exceptionCount > 0) {
/* 2199 */       attributeCount++;
/*      */     }
/* 2201 */     if ((this.access & 0x1000) != 0 && ((
/* 2202 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0))
/*      */     {
/* 2204 */       attributeCount++;
/*      */     }
/*      */     
/* 2207 */     if ((this.access & 0x20000) != 0) {
/* 2208 */       attributeCount++;
/*      */     }
/* 2210 */     if (this.signature != null) {
/* 2211 */       attributeCount++;
/*      */     }
/* 2213 */     if (this.methodParameters != null) {
/* 2214 */       attributeCount++;
/*      */     }
/* 2216 */     if (this.annd != null) {
/* 2217 */       attributeCount++;
/*      */     }
/* 2219 */     if (this.anns != null) {
/* 2220 */       attributeCount++;
/*      */     }
/* 2222 */     if (this.ianns != null) {
/* 2223 */       attributeCount++;
/*      */     }
/* 2225 */     if (this.tanns != null) {
/* 2226 */       attributeCount++;
/*      */     }
/* 2228 */     if (this.itanns != null) {
/* 2229 */       attributeCount++;
/*      */     }
/* 2231 */     if (this.panns != null) {
/* 2232 */       attributeCount++;
/*      */     }
/* 2234 */     if (this.ipanns != null) {
/* 2235 */       attributeCount++;
/*      */     }
/* 2237 */     if (this.attrs != null) {
/* 2238 */       attributeCount += this.attrs.getCount();
/*      */     }
/* 2240 */     out.putShort(attributeCount);
/* 2241 */     if (this.code.length > 0) {
/* 2242 */       int size = 12 + this.code.length + 8 * this.handlerCount;
/* 2243 */       if (this.localVar != null) {
/* 2244 */         size += 8 + this.localVar.length;
/*      */       }
/* 2246 */       if (this.localVarType != null) {
/* 2247 */         size += 8 + this.localVarType.length;
/*      */       }
/* 2249 */       if (this.lineNumber != null) {
/* 2250 */         size += 8 + this.lineNumber.length;
/*      */       }
/* 2252 */       if (this.stackMap != null) {
/* 2253 */         size += 8 + this.stackMap.length;
/*      */       }
/* 2255 */       if (this.ctanns != null) {
/* 2256 */         size += 8 + this.ctanns.getSize();
/*      */       }
/* 2258 */       if (this.ictanns != null) {
/* 2259 */         size += 8 + this.ictanns.getSize();
/*      */       }
/* 2261 */       if (this.cattrs != null) {
/* 2262 */         size += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
/*      */       }
/*      */       
/* 2265 */       out.putShort(this.cw.newUTF8("Code")).putInt(size);
/* 2266 */       out.putShort(this.maxStack).putShort(this.maxLocals);
/* 2267 */       out.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
/* 2268 */       out.putShort(this.handlerCount);
/* 2269 */       if (this.handlerCount > 0) {
/* 2270 */         Handler h = this.firstHandler;
/* 2271 */         while (h != null) {
/* 2272 */           out.putShort(h.start.position).putShort(h.end.position)
/* 2273 */             .putShort(h.handler.position).putShort(h.type);
/* 2274 */           h = h.next;
/*      */         } 
/*      */       } 
/* 2277 */       attributeCount = 0;
/* 2278 */       if (this.localVar != null) {
/* 2279 */         attributeCount++;
/*      */       }
/* 2281 */       if (this.localVarType != null) {
/* 2282 */         attributeCount++;
/*      */       }
/* 2284 */       if (this.lineNumber != null) {
/* 2285 */         attributeCount++;
/*      */       }
/* 2287 */       if (this.stackMap != null) {
/* 2288 */         attributeCount++;
/*      */       }
/* 2290 */       if (this.ctanns != null) {
/* 2291 */         attributeCount++;
/*      */       }
/* 2293 */       if (this.ictanns != null) {
/* 2294 */         attributeCount++;
/*      */       }
/* 2296 */       if (this.cattrs != null) {
/* 2297 */         attributeCount += this.cattrs.getCount();
/*      */       }
/* 2299 */       out.putShort(attributeCount);
/* 2300 */       if (this.localVar != null) {
/* 2301 */         out.putShort(this.cw.newUTF8("LocalVariableTable"));
/* 2302 */         out.putInt(this.localVar.length + 2).putShort(this.localVarCount);
/* 2303 */         out.putByteArray(this.localVar.data, 0, this.localVar.length);
/*      */       } 
/* 2305 */       if (this.localVarType != null) {
/* 2306 */         out.putShort(this.cw.newUTF8("LocalVariableTypeTable"));
/* 2307 */         out.putInt(this.localVarType.length + 2).putShort(this.localVarTypeCount);
/* 2308 */         out.putByteArray(this.localVarType.data, 0, this.localVarType.length);
/*      */       } 
/* 2310 */       if (this.lineNumber != null) {
/* 2311 */         out.putShort(this.cw.newUTF8("LineNumberTable"));
/* 2312 */         out.putInt(this.lineNumber.length + 2).putShort(this.lineNumberCount);
/* 2313 */         out.putByteArray(this.lineNumber.data, 0, this.lineNumber.length);
/*      */       } 
/* 2315 */       if (this.stackMap != null) {
/* 2316 */         boolean zip = ((this.cw.version & 0xFFFF) >= 50);
/* 2317 */         out.putShort(this.cw.newUTF8(zip ? "StackMapTable" : "StackMap"));
/* 2318 */         out.putInt(this.stackMap.length + 2).putShort(this.frameCount);
/* 2319 */         out.putByteArray(this.stackMap.data, 0, this.stackMap.length);
/*      */       } 
/* 2321 */       if (this.ctanns != null) {
/* 2322 */         out.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
/* 2323 */         this.ctanns.put(out);
/*      */       } 
/* 2325 */       if (this.ictanns != null) {
/* 2326 */         out.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
/* 2327 */         this.ictanns.put(out);
/*      */       } 
/* 2329 */       if (this.cattrs != null) {
/* 2330 */         this.cattrs.put(this.cw, this.code.data, this.code.length, this.maxLocals, this.maxStack, out);
/*      */       }
/*      */     } 
/* 2333 */     if (this.exceptionCount > 0) {
/* 2334 */       out.putShort(this.cw.newUTF8("Exceptions")).putInt(2 * this.exceptionCount + 2);
/*      */       
/* 2336 */       out.putShort(this.exceptionCount);
/* 2337 */       for (int i = 0; i < this.exceptionCount; i++) {
/* 2338 */         out.putShort(this.exceptions[i]);
/*      */       }
/*      */     } 
/* 2341 */     if ((this.access & 0x1000) != 0 && ((
/* 2342 */       this.cw.version & 0xFFFF) < 49 || (this.access & 0x40000) != 0))
/*      */     {
/* 2344 */       out.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
/*      */     }
/*      */     
/* 2347 */     if ((this.access & 0x20000) != 0) {
/* 2348 */       out.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
/*      */     }
/* 2350 */     if (this.signature != null) {
/* 2351 */       out.putShort(this.cw.newUTF8("Signature")).putInt(2)
/* 2352 */         .putShort(this.cw.newUTF8(this.signature));
/*      */     }
/* 2354 */     if (this.methodParameters != null) {
/* 2355 */       out.putShort(this.cw.newUTF8("MethodParameters"));
/* 2356 */       out.putInt(this.methodParameters.length + 1).putByte(this.methodParametersCount);
/*      */       
/* 2358 */       out.putByteArray(this.methodParameters.data, 0, this.methodParameters.length);
/*      */     } 
/* 2360 */     if (this.annd != null) {
/* 2361 */       out.putShort(this.cw.newUTF8("AnnotationDefault"));
/* 2362 */       out.putInt(this.annd.length);
/* 2363 */       out.putByteArray(this.annd.data, 0, this.annd.length);
/*      */     } 
/* 2365 */     if (this.anns != null) {
/* 2366 */       out.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
/* 2367 */       this.anns.put(out);
/*      */     } 
/* 2369 */     if (this.ianns != null) {
/* 2370 */       out.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
/* 2371 */       this.ianns.put(out);
/*      */     } 
/* 2373 */     if (this.tanns != null) {
/* 2374 */       out.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
/* 2375 */       this.tanns.put(out);
/*      */     } 
/* 2377 */     if (this.itanns != null) {
/* 2378 */       out.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
/* 2379 */       this.itanns.put(out);
/*      */     } 
/* 2381 */     if (this.panns != null) {
/* 2382 */       out.putShort(this.cw.newUTF8("RuntimeVisibleParameterAnnotations"));
/* 2383 */       AnnotationWriter.put(this.panns, this.synthetics, out);
/*      */     } 
/* 2385 */     if (this.ipanns != null) {
/* 2386 */       out.putShort(this.cw.newUTF8("RuntimeInvisibleParameterAnnotations"));
/* 2387 */       AnnotationWriter.put(this.ipanns, this.synthetics, out);
/*      */     } 
/* 2389 */     if (this.attrs != null)
/* 2390 */       this.attrs.put(this.cw, null, 0, -1, -1, out); 
/*      */   }
/*      */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\org\spongepowered\asm\lib\MethodWriter.class
 * Java compiler version: 5 (49.0)
 * JD-Core Version:       1.1.3
 */