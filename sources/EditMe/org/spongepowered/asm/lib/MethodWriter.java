package org.spongepowered.asm.lib;

class MethodWriter extends MethodVisitor {
   static final int ACC_CONSTRUCTOR = 524288;
   static final int SAME_FRAME = 0;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME = 64;
   static final int RESERVED = 128;
   static final int SAME_LOCALS_1_STACK_ITEM_FRAME_EXTENDED = 247;
   static final int CHOP_FRAME = 248;
   static final int SAME_FRAME_EXTENDED = 251;
   static final int APPEND_FRAME = 252;
   static final int FULL_FRAME = 255;
   static final int FRAMES = 0;
   static final int INSERTED_FRAMES = 1;
   static final int MAXS = 2;
   static final int NOTHING = 3;
   final ClassWriter cw;
   private int access;
   private final int name;
   private final int desc;
   private final String descriptor;
   String signature;
   int classReaderOffset;
   int classReaderLength;
   int exceptionCount;
   int[] exceptions;
   private ByteVector annd;
   private AnnotationWriter anns;
   private AnnotationWriter ianns;
   private AnnotationWriter tanns;
   private AnnotationWriter itanns;
   private AnnotationWriter[] panns;
   private AnnotationWriter[] ipanns;
   private int synthetics;
   private Attribute attrs;
   private ByteVector code = new ByteVector();
   private int maxStack;
   private int maxLocals;
   private int currentLocals;
   private int frameCount;
   private ByteVector stackMap;
   private int previousFrameOffset;
   private int[] previousFrame;
   private int[] frame;
   private int handlerCount;
   private Handler firstHandler;
   private Handler lastHandler;
   private int methodParametersCount;
   private ByteVector methodParameters;
   private int localVarCount;
   private ByteVector localVar;
   private int localVarTypeCount;
   private ByteVector localVarType;
   private int lineNumberCount;
   private ByteVector lineNumber;
   private int lastCodeOffset;
   private AnnotationWriter ctanns;
   private AnnotationWriter ictanns;
   private Attribute cattrs;
   private int subroutines;
   private final int compute;
   private Label labels;
   private Label previousBlock;
   private Label currentBlock;
   private int stackSize;
   private int maxStackSize;

   MethodWriter(ClassWriter var1, int var2, String var3, String var4, String var5, String[] var6, int var7) {
      super(327680);
      if (var1.firstMethod == null) {
         var1.firstMethod = this;
      } else {
         var1.lastMethod.mv = this;
      }

      var1.lastMethod = this;
      this.cw = var1;
      this.access = var2;
      if ("<init>".equals(var3)) {
         this.access |= 524288;
      }

      this.name = var1.newUTF8(var3);
      this.desc = var1.newUTF8(var4);
      this.descriptor = var4;
      this.signature = var5;
      int var8;
      if (var6 != null && var6.length > 0) {
         this.exceptionCount = var6.length;
         this.exceptions = new int[this.exceptionCount];

         for(var8 = 0; var8 < this.exceptionCount; ++var8) {
            this.exceptions[var8] = var1.newClass(var6[var8]);
         }
      }

      this.compute = var7;
      if (var7 != 3) {
         var8 = Type.getArgumentsAndReturnSizes(this.descriptor) >> 2;
         if ((var2 & 8) != 0) {
            --var8;
         }

         this.maxLocals = var8;
         this.currentLocals = var8;
         this.labels = new Label();
         Label var10000 = this.labels;
         var10000.status |= 8;
         this.visitLabel(this.labels);
      }

   }

   public void visitParameter(String var1, int var2) {
      if (this.methodParameters == null) {
         this.methodParameters = new ByteVector();
      }

      ++this.methodParametersCount;
      this.methodParameters.putShort(var1 == null ? 0 : this.cw.newUTF8(var1)).putShort(var2);
   }

   public AnnotationVisitor visitAnnotationDefault() {
      this.annd = new ByteVector();
      return new AnnotationWriter(this.cw, false, this.annd, (ByteVector)null, 0);
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      ByteVector var3 = new ByteVector();
      var3.putShort(this.cw.newUTF8(var1)).putShort(0);
      AnnotationWriter var4 = new AnnotationWriter(this.cw, true, var3, var3, 2);
      if (var2) {
         var4.next = this.anns;
         this.anns = var4;
      } else {
         var4.next = this.ianns;
         this.ianns = var4;
      }

      return var4;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.cw.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.cw, true, var5, var5, var5.length - 2);
      if (var4) {
         var6.next = this.tanns;
         this.tanns = var6;
      } else {
         var6.next = this.itanns;
         this.itanns = var6;
      }

      return var6;
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      ByteVector var4 = new ByteVector();
      if ("Ljava/lang/Synthetic;".equals(var2)) {
         this.synthetics = Math.max(this.synthetics, var1 + 1);
         return new AnnotationWriter(this.cw, false, var4, (ByteVector)null, 0);
      } else {
         var4.putShort(this.cw.newUTF8(var2)).putShort(0);
         AnnotationWriter var5 = new AnnotationWriter(this.cw, true, var4, var4, 2);
         if (var3) {
            if (this.panns == null) {
               this.panns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }

            var5.next = this.panns[var1];
            this.panns[var1] = var5;
         } else {
            if (this.ipanns == null) {
               this.ipanns = new AnnotationWriter[Type.getArgumentTypes(this.descriptor).length];
            }

            var5.next = this.ipanns[var1];
            this.ipanns[var1] = var5;
         }

         return var5;
      }
   }

   public void visitAttribute(Attribute var1) {
      if (var1.isCodeAttribute()) {
         var1.next = this.cattrs;
         this.cattrs = var1;
      } else {
         var1.next = this.attrs;
         this.attrs = var1;
      }

   }

   public void visitCode() {
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      if (this.compute != 0) {
         if (this.compute == 1) {
            if (this.currentBlock.frame == null) {
               this.currentBlock.frame = new CurrentFrame();
               this.currentBlock.frame.owner = this.currentBlock;
               this.currentBlock.frame.initInputFrame(this.cw, this.access, Type.getArgumentTypes(this.descriptor), var2);
               this.visitImplicitFirstFrame();
            } else {
               if (var1 == -1) {
                  this.currentBlock.frame.set(this.cw, var2, var3, var4, var5);
               }

               this.visitFrame(this.currentBlock.frame);
            }
         } else {
            int var6;
            int var7;
            if (var1 == -1) {
               if (this.previousFrame == null) {
                  this.visitImplicitFirstFrame();
               }

               this.currentLocals = var2;
               var6 = this.startFrame(this.code.length, var2, var4);

               for(var7 = 0; var7 < var2; ++var7) {
                  if (var3[var7] instanceof String) {
                     this.frame[var6++] = 24117248 | this.cw.addType((String)var3[var7]);
                  } else if (var3[var7] instanceof Integer) {
                     this.frame[var6++] = (Integer)var3[var7];
                  } else {
                     this.frame[var6++] = 25165824 | this.cw.addUninitializedType("", ((Label)var3[var7]).position);
                  }
               }

               for(var7 = 0; var7 < var4; ++var7) {
                  if (var5[var7] instanceof String) {
                     this.frame[var6++] = 24117248 | this.cw.addType((String)var5[var7]);
                  } else if (var5[var7] instanceof Integer) {
                     this.frame[var6++] = (Integer)var5[var7];
                  } else {
                     this.frame[var6++] = 25165824 | this.cw.addUninitializedType("", ((Label)var5[var7]).position);
                  }
               }

               this.endFrame();
            } else {
               if (this.stackMap == null) {
                  this.stackMap = new ByteVector();
                  var6 = this.code.length;
               } else {
                  var6 = this.code.length - this.previousFrameOffset - 1;
                  if (var6 < 0) {
                     if (var1 == 3) {
                        return;
                     }

                     throw new IllegalStateException();
                  }
               }

               label137:
               switch(var1) {
               case 0:
                  this.currentLocals = var2;
                  this.stackMap.putByte(255).putShort(var6).putShort(var2);

                  for(var7 = 0; var7 < var2; ++var7) {
                     this.writeFrameType(var3[var7]);
                  }

                  this.stackMap.putShort(var4);

                  for(var7 = 0; var7 < var4; ++var7) {
                     this.writeFrameType(var5[var7]);
                  }
                  break;
               case 1:
                  this.currentLocals += var2;
                  this.stackMap.putByte(251 + var2).putShort(var6);
                  var7 = 0;

                  while(true) {
                     if (var7 >= var2) {
                        break label137;
                     }

                     this.writeFrameType(var3[var7]);
                     ++var7;
                  }
               case 2:
                  this.currentLocals -= var2;
                  this.stackMap.putByte(251 - var2).putShort(var6);
                  break;
               case 3:
                  if (var6 < 64) {
                     this.stackMap.putByte(var6);
                  } else {
                     this.stackMap.putByte(251).putShort(var6);
                  }
                  break;
               case 4:
                  if (var6 < 64) {
                     this.stackMap.putByte(64 + var6);
                  } else {
                     this.stackMap.putByte(247).putShort(var6);
                  }

                  this.writeFrameType(var5[0]);
               }

               this.previousFrameOffset = this.code.length;
               ++this.frameCount;
            }
         }

         this.maxStack = Math.max(this.maxStack, var4);
         this.maxLocals = Math.max(this.maxLocals, this.currentLocals);
      }
   }

   public void visitInsn(int var1) {
      this.lastCodeOffset = this.code.length;
      this.code.putByte(var1);
      if (this.currentBlock != null) {
         if (this.compute != 0 && this.compute != 1) {
            int var2 = this.stackSize + Frame.SIZE[var1];
            if (var2 > this.maxStackSize) {
               this.maxStackSize = var2;
            }

            this.stackSize = var2;
         } else {
            this.currentBlock.frame.execute(var1, 0, (ClassWriter)null, (Item)null);
         }

         if (var1 >= 172 && var1 <= 177 || var1 == 191) {
            this.noSuccessor();
         }
      }

   }

   public void visitIntInsn(int var1, int var2) {
      this.lastCodeOffset = this.code.length;
      if (this.currentBlock != null) {
         if (this.compute != 0 && this.compute != 1) {
            if (var1 != 188) {
               int var3 = this.stackSize + 1;
               if (var3 > this.maxStackSize) {
                  this.maxStackSize = var3;
               }

               this.stackSize = var3;
            }
         } else {
            this.currentBlock.frame.execute(var1, var2, (ClassWriter)null, (Item)null);
         }
      }

      if (var1 == 17) {
         this.code.put12(var1, var2);
      } else {
         this.code.put11(var1, var2);
      }

   }

   public void visitVarInsn(int var1, int var2) {
      this.lastCodeOffset = this.code.length;
      int var3;
      if (this.currentBlock != null) {
         if (this.compute != 0 && this.compute != 1) {
            if (var1 == 169) {
               Label var10000 = this.currentBlock;
               var10000.status |= 256;
               this.currentBlock.inputStackTop = this.stackSize;
               this.noSuccessor();
            } else {
               var3 = this.stackSize + Frame.SIZE[var1];
               if (var3 > this.maxStackSize) {
                  this.maxStackSize = var3;
               }

               this.stackSize = var3;
            }
         } else {
            this.currentBlock.frame.execute(var1, var2, (ClassWriter)null, (Item)null);
         }
      }

      if (this.compute != 3) {
         if (var1 != 22 && var1 != 24 && var1 != 55 && var1 != 57) {
            var3 = var2 + 1;
         } else {
            var3 = var2 + 2;
         }

         if (var3 > this.maxLocals) {
            this.maxLocals = var3;
         }
      }

      if (var2 < 4 && var1 != 169) {
         if (var1 < 54) {
            var3 = 26 + (var1 - 21 << 2) + var2;
         } else {
            var3 = 59 + (var1 - 54 << 2) + var2;
         }

         this.code.putByte(var3);
      } else if (var2 >= 256) {
         this.code.putByte(196).put12(var1, var2);
      } else {
         this.code.put11(var1, var2);
      }

      if (var1 >= 54 && this.compute == 0 && this.handlerCount > 0) {
         this.visitLabel(new Label());
      }

   }

   public void visitTypeInsn(int var1, String var2) {
      this.lastCodeOffset = this.code.length;
      Item var3 = this.cw.newClassItem(var2);
      if (this.currentBlock != null) {
         if (this.compute != 0 && this.compute != 1) {
            if (var1 == 187) {
               int var4 = this.stackSize + 1;
               if (var4 > this.maxStackSize) {
                  this.maxStackSize = var4;
               }

               this.stackSize = var4;
            }
         } else {
            this.currentBlock.frame.execute(var1, this.code.length, this.cw, var3);
         }
      }

      this.code.put12(var1, var3.index);
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.lastCodeOffset = this.code.length;
      Item var5 = this.cw.newFieldItem(var2, var3, var4);
      if (this.currentBlock != null) {
         if (this.compute != 0 && this.compute != 1) {
            int var7;
            label91: {
               char var6 = var4.charAt(0);
               switch(var1) {
               case 178:
                  var7 = this.stackSize + (var6 != 'D' && var6 != 'J' ? 1 : 2);
                  break label91;
               case 179:
                  var7 = this.stackSize + (var6 != 'D' && var6 != 'J' ? -1 : -2);
                  break label91;
               case 180:
                  var7 = this.stackSize + (var6 != 'D' && var6 != 'J' ? 0 : 1);
                  break label91;
               }

               var7 = this.stackSize + (var6 != 'D' && var6 != 'J' ? -2 : -3);
            }

            if (var7 > this.maxStackSize) {
               this.maxStackSize = var7;
            }

            this.stackSize = var7;
         } else {
            this.currentBlock.frame.execute(var1, 0, this.cw, var5);
         }
      }

      this.code.put12(var1, var5.index);
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      this.lastCodeOffset = this.code.length;
      Item var6 = this.cw.newMethodItem(var2, var3, var4, var5);
      int var7 = var6.intVal;
      if (this.currentBlock != null) {
         if (this.compute != 0 && this.compute != 1) {
            if (var7 == 0) {
               var7 = Type.getArgumentsAndReturnSizes(var4);
               var6.intVal = var7;
            }

            int var8;
            if (var1 == 184) {
               var8 = this.stackSize - (var7 >> 2) + (var7 & 3) + 1;
            } else {
               var8 = this.stackSize - (var7 >> 2) + (var7 & 3);
            }

            if (var8 > this.maxStackSize) {
               this.maxStackSize = var8;
            }

            this.stackSize = var8;
         } else {
            this.currentBlock.frame.execute(var1, 0, this.cw, var6);
         }
      }

      if (var1 == 185) {
         if (var7 == 0) {
            var7 = Type.getArgumentsAndReturnSizes(var4);
            var6.intVal = var7;
         }

         this.code.put12(185, var6.index).put11(var7 >> 2, 0);
      } else {
         this.code.put12(var1, var6.index);
      }

   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.lastCodeOffset = this.code.length;
      Item var5 = this.cw.newInvokeDynamicItem(var1, var2, var3, var4);
      int var6 = var5.intVal;
      if (this.currentBlock != null) {
         if (this.compute != 0 && this.compute != 1) {
            if (var6 == 0) {
               var6 = Type.getArgumentsAndReturnSizes(var2);
               var5.intVal = var6;
            }

            int var7 = this.stackSize - (var6 >> 2) + (var6 & 3) + 1;
            if (var7 > this.maxStackSize) {
               this.maxStackSize = var7;
            }

            this.stackSize = var7;
         } else {
            this.currentBlock.frame.execute(186, 0, this.cw, var5);
         }
      }

      this.code.put12(186, var5.index);
      this.code.putShort(0);
   }

   public void visitJumpInsn(int var1, Label var2) {
      boolean var3 = var1 >= 200;
      var1 = var3 ? var1 - 33 : var1;
      this.lastCodeOffset = this.code.length;
      Label var4 = null;
      if (this.currentBlock != null) {
         Label var10000;
         if (this.compute == 0) {
            this.currentBlock.frame.execute(var1, 0, (ClassWriter)null, (Item)null);
            var10000 = var2.getFirst();
            var10000.status |= 16;
            this.addSuccessor(0, var2);
            if (var1 != 167) {
               var4 = new Label();
            }
         } else if (this.compute == 1) {
            this.currentBlock.frame.execute(var1, 0, (ClassWriter)null, (Item)null);
         } else if (var1 == 168) {
            if ((var2.status & 512) == 0) {
               var2.status |= 512;
               ++this.subroutines;
            }

            var10000 = this.currentBlock;
            var10000.status |= 128;
            this.addSuccessor(this.stackSize + 1, var2);
            var4 = new Label();
         } else {
            this.stackSize += Frame.SIZE[var1];
            this.addSuccessor(this.stackSize, var2);
         }
      }

      if ((var2.status & 2) != 0 && var2.position - this.code.length < -32768) {
         if (var1 == 167) {
            this.code.putByte(200);
         } else if (var1 == 168) {
            this.code.putByte(201);
         } else {
            if (var4 != null) {
               var4.status |= 16;
            }

            this.code.putByte(var1 <= 166 ? (var1 + 1 ^ 1) - 1 : var1 ^ 1);
            this.code.putShort(8);
            this.code.putByte(200);
         }

         var2.put(this, this.code, this.code.length - 1, true);
      } else if (var3) {
         this.code.putByte(var1 + 33);
         var2.put(this, this.code, this.code.length - 1, true);
      } else {
         this.code.putByte(var1);
         var2.put(this, this.code, this.code.length - 1, false);
      }

      if (this.currentBlock != null) {
         if (var4 != null) {
            this.visitLabel(var4);
         }

         if (var1 == 167) {
            this.noSuccessor();
         }
      }

   }

   public void visitLabel(Label var1) {
      ClassWriter var10000 = this.cw;
      var10000.hasAsmInsns |= var1.resolve(this, this.code.length, this.code.data);
      if ((var1.status & 1) == 0) {
         if (this.compute == 0) {
            Label var2;
            if (this.currentBlock != null) {
               if (var1.position == this.currentBlock.position) {
                  var2 = this.currentBlock;
                  var2.status |= var1.status & 16;
                  var1.frame = this.currentBlock.frame;
                  return;
               }

               this.addSuccessor(0, var1);
            }

            this.currentBlock = var1;
            if (var1.frame == null) {
               var1.frame = new Frame();
               var1.frame.owner = var1;
            }

            if (this.previousBlock != null) {
               if (var1.position == this.previousBlock.position) {
                  var2 = this.previousBlock;
                  var2.status |= var1.status & 16;
                  var1.frame = this.previousBlock.frame;
                  this.currentBlock = this.previousBlock;
                  return;
               }

               this.previousBlock.successor = var1;
            }

            this.previousBlock = var1;
         } else if (this.compute == 1) {
            if (this.currentBlock == null) {
               this.currentBlock = var1;
            } else {
               this.currentBlock.frame.owner = var1;
            }
         } else if (this.compute == 2) {
            if (this.currentBlock != null) {
               this.currentBlock.outputStackMax = this.maxStackSize;
               this.addSuccessor(this.stackSize, var1);
            }

            this.currentBlock = var1;
            this.stackSize = 0;
            this.maxStackSize = 0;
            if (this.previousBlock != null) {
               this.previousBlock.successor = var1;
            }

            this.previousBlock = var1;
         }

      }
   }

   public void visitLdcInsn(Object var1) {
      this.lastCodeOffset = this.code.length;
      Item var2 = this.cw.newConstItem(var1);
      int var3;
      if (this.currentBlock != null) {
         if (this.compute != 0 && this.compute != 1) {
            if (var2.type != 5 && var2.type != 6) {
               var3 = this.stackSize + 1;
            } else {
               var3 = this.stackSize + 2;
            }

            if (var3 > this.maxStackSize) {
               this.maxStackSize = var3;
            }

            this.stackSize = var3;
         } else {
            this.currentBlock.frame.execute(18, 0, this.cw, var2);
         }
      }

      var3 = var2.index;
      if (var2.type != 5 && var2.type != 6) {
         if (var3 >= 256) {
            this.code.put12(19, var3);
         } else {
            this.code.put11(18, var3);
         }
      } else {
         this.code.put12(20, var3);
      }

   }

   public void visitIincInsn(int var1, int var2) {
      this.lastCodeOffset = this.code.length;
      if (this.currentBlock != null && (this.compute == 0 || this.compute == 1)) {
         this.currentBlock.frame.execute(132, var1, (ClassWriter)null, (Item)null);
      }

      if (this.compute != 3) {
         int var3 = var1 + 1;
         if (var3 > this.maxLocals) {
            this.maxLocals = var3;
         }
      }

      if (var1 <= 255 && var2 <= 127 && var2 >= -128) {
         this.code.putByte(132).put11(var1, var2);
      } else {
         this.code.putByte(196).put12(132, var1).putShort(var2);
      }

   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.lastCodeOffset = this.code.length;
      int var5 = this.code.length;
      this.code.putByte(170);
      this.code.putByteArray((byte[])null, 0, (4 - this.code.length % 4) % 4);
      var3.put(this, this.code, var5, true);
      this.code.putInt(var1).putInt(var2);

      for(int var6 = 0; var6 < var4.length; ++var6) {
         var4[var6].put(this, this.code, var5, true);
      }

      this.visitSwitchInsn(var3, var4);
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.lastCodeOffset = this.code.length;
      int var4 = this.code.length;
      this.code.putByte(171);
      this.code.putByteArray((byte[])null, 0, (4 - this.code.length % 4) % 4);
      var1.put(this, this.code, var4, true);
      this.code.putInt(var3.length);

      for(int var5 = 0; var5 < var3.length; ++var5) {
         this.code.putInt(var2[var5]);
         var3[var5].put(this, this.code, var4, true);
      }

      this.visitSwitchInsn(var1, var3);
   }

   private void visitSwitchInsn(Label var1, Label[] var2) {
      if (this.currentBlock != null) {
         int var3;
         if (this.compute == 0) {
            this.currentBlock.frame.execute(171, 0, (ClassWriter)null, (Item)null);
            this.addSuccessor(0, var1);
            Label var10000 = var1.getFirst();
            var10000.status |= 16;

            for(var3 = 0; var3 < var2.length; ++var3) {
               this.addSuccessor(0, var2[var3]);
               var10000 = var2[var3].getFirst();
               var10000.status |= 16;
            }
         } else {
            --this.stackSize;
            this.addSuccessor(this.stackSize, var1);

            for(var3 = 0; var3 < var2.length; ++var3) {
               this.addSuccessor(this.stackSize, var2[var3]);
            }
         }

         this.noSuccessor();
      }

   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.lastCodeOffset = this.code.length;
      Item var3 = this.cw.newClassItem(var1);
      if (this.currentBlock != null) {
         if (this.compute != 0 && this.compute != 1) {
            this.stackSize += 1 - var2;
         } else {
            this.currentBlock.frame.execute(197, var2, this.cw, var3);
         }
      }

      this.code.put12(197, var3.index).putByte(var2);
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      var1 = var1 & -16776961 | this.lastCodeOffset << 8;
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.cw.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.cw, true, var5, var5, var5.length - 2);
      if (var4) {
         var6.next = this.ctanns;
         this.ctanns = var6;
      } else {
         var6.next = this.ictanns;
         this.ictanns = var6;
      }

      return var6;
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      ++this.handlerCount;
      Handler var5 = new Handler();
      var5.start = var1;
      var5.end = var2;
      var5.handler = var3;
      var5.desc = var4;
      var5.type = var4 != null ? this.cw.newClass(var4) : 0;
      if (this.lastHandler == null) {
         this.firstHandler = var5;
      } else {
         this.lastHandler.next = var5;
      }

      this.lastHandler = var5;
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      ByteVector var5 = new ByteVector();
      AnnotationWriter.putTarget(var1, var2, var5);
      var5.putShort(this.cw.newUTF8(var3)).putShort(0);
      AnnotationWriter var6 = new AnnotationWriter(this.cw, true, var5, var5, var5.length - 2);
      if (var4) {
         var6.next = this.ctanns;
         this.ctanns = var6;
      } else {
         var6.next = this.ictanns;
         this.ictanns = var6;
      }

      return var6;
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      if (var3 != null) {
         if (this.localVarType == null) {
            this.localVarType = new ByteVector();
         }

         ++this.localVarTypeCount;
         this.localVarType.putShort(var4.position).putShort(var5.position - var4.position).putShort(this.cw.newUTF8(var1)).putShort(this.cw.newUTF8(var3)).putShort(var6);
      }

      if (this.localVar == null) {
         this.localVar = new ByteVector();
      }

      ++this.localVarCount;
      this.localVar.putShort(var4.position).putShort(var5.position - var4.position).putShort(this.cw.newUTF8(var1)).putShort(this.cw.newUTF8(var2)).putShort(var6);
      if (this.compute != 3) {
         char var7 = var2.charAt(0);
         int var8 = var6 + (var7 != 'J' && var7 != 'D' ? 1 : 2);
         if (var8 > this.maxLocals) {
            this.maxLocals = var8;
         }
      }

   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      ByteVector var8 = new ByteVector();
      var8.putByte(var1 >>> 24).putShort(var3.length);

      int var9;
      for(var9 = 0; var9 < var3.length; ++var9) {
         var8.putShort(var3[var9].position).putShort(var4[var9].position - var3[var9].position).putShort(var5[var9]);
      }

      if (var2 == null) {
         var8.putByte(0);
      } else {
         var9 = var2.b[var2.offset] * 2 + 1;
         var8.putByteArray(var2.b, var2.offset, var9);
      }

      var8.putShort(this.cw.newUTF8(var6)).putShort(0);
      AnnotationWriter var10 = new AnnotationWriter(this.cw, true, var8, var8, var8.length - 2);
      if (var7) {
         var10.next = this.ctanns;
         this.ctanns = var10;
      } else {
         var10.next = this.ictanns;
         this.ictanns = var10;
      }

      return var10;
   }

   public void visitLineNumber(int var1, Label var2) {
      if (this.lineNumber == null) {
         this.lineNumber = new ByteVector();
      }

      ++this.lineNumberCount;
      this.lineNumber.putShort(var2.position);
      this.lineNumber.putShort(var1);
   }

   public void visitMaxs(int var1, int var2) {
      Handler var3;
      Label var4;
      Label var5;
      Label var6;
      int var8;
      Edge var9;
      Label var15;
      if (this.compute == 0) {
         for(var3 = this.firstHandler; var3 != null; var3 = var3.next) {
            var4 = var3.start.getFirst();
            var5 = var3.handler.getFirst();
            var6 = var3.end.getFirst();
            String var7 = var3.desc == null ? "java/lang/Throwable" : var3.desc;
            var8 = 24117248 | this.cw.addType(var7);

            for(var5.status |= 16; var4 != var6; var4 = var4.successor) {
               var9 = new Edge();
               var9.info = var8;
               var9.successor = var5;
               var9.next = var4.successors;
               var4.successors = var9;
            }
         }

         Frame var12 = this.labels.frame;
         var12.initInputFrame(this.cw, this.access, Type.getArgumentTypes(this.descriptor), this.maxLocals);
         this.visitFrame(var12);
         int var13 = 0;
         var6 = this.labels;

         while(var6 != null) {
            var15 = var6;
            var6 = var6.next;
            var15.next = null;
            var12 = var15.frame;
            if ((var15.status & 16) != 0) {
               var15.status |= 32;
            }

            var15.status |= 64;
            var8 = var12.inputStack.length + var15.outputStackMax;
            if (var8 > var13) {
               var13 = var8;
            }

            for(var9 = var15.successors; var9 != null; var9 = var9.next) {
               Label var10 = var9.successor.getFirst();
               boolean var11 = var12.merge(this.cw, var10.frame, var9.info);
               if (var11 && var10.next == null) {
                  var10.next = var6;
                  var6 = var10;
               }
            }
         }

         for(var15 = this.labels; var15 != null; var15 = var15.successor) {
            var12 = var15.frame;
            if ((var15.status & 32) != 0) {
               this.visitFrame(var12);
            }

            if ((var15.status & 64) == 0) {
               Label var17 = var15.successor;
               int var19 = var15.position;
               int var20 = (var17 == null ? this.code.length : var17.position) - 1;
               if (var20 >= var19) {
                  var13 = Math.max(var13, 1);

                  int var21;
                  for(var21 = var19; var21 < var20; ++var21) {
                     this.code.data[var21] = 0;
                  }

                  this.code.data[var20] = -65;
                  var21 = this.startFrame(var19, 0, 1);
                  this.frame[var21] = 24117248 | this.cw.addType("java/lang/Throwable");
                  this.endFrame();
                  this.firstHandler = Handler.remove(this.firstHandler, var15, var17);
               }
            }
         }

         var3 = this.firstHandler;

         for(this.handlerCount = 0; var3 != null; var3 = var3.next) {
            ++this.handlerCount;
         }

         this.maxStack = var13;
      } else if (this.compute == 2) {
         for(var3 = this.firstHandler; var3 != null; var3 = var3.next) {
            var4 = var3.start;
            var5 = var3.handler;

            for(var6 = var3.end; var4 != var6; var4 = var4.successor) {
               Edge var16 = new Edge();
               var16.info = Integer.MAX_VALUE;
               var16.successor = var5;
               if ((var4.status & 128) == 0) {
                  var16.next = var4.successors;
                  var4.successors = var16;
               } else {
                  var16.next = var4.successors.next.next;
                  var4.successors.next.next = var16;
               }
            }
         }

         int var14;
         if (this.subroutines > 0) {
            var14 = 0;
            this.labels.visitSubroutine((Label)null, 1L, this.subroutines);

            for(var5 = this.labels; var5 != null; var5 = var5.successor) {
               if ((var5.status & 128) != 0) {
                  var6 = var5.successors.next.successor;
                  if ((var6.status & 1024) == 0) {
                     ++var14;
                     var6.visitSubroutine((Label)null, (long)var14 / 32L << 32 | 1L << var14 % 32, this.subroutines);
                  }
               }
            }

            for(var5 = this.labels; var5 != null; var5 = var5.successor) {
               if ((var5.status & 128) != 0) {
                  for(var6 = this.labels; var6 != null; var6 = var6.successor) {
                     var6.status &= -2049;
                  }

                  var15 = var5.successors.next.successor;
                  var15.visitSubroutine(var5, 0L, this.subroutines);
               }
            }
         }

         var14 = 0;
         var5 = this.labels;

         while(var5 != null) {
            var6 = var5;
            var5 = var5.next;
            int var18 = var6.inputStackTop;
            var8 = var18 + var6.outputStackMax;
            if (var8 > var14) {
               var14 = var8;
            }

            var9 = var6.successors;
            if ((var6.status & 128) != 0) {
               var9 = var9.next;
            }

            for(; var9 != null; var9 = var9.next) {
               var6 = var9.successor;
               if ((var6.status & 8) == 0) {
                  var6.inputStackTop = var9.info == Integer.MAX_VALUE ? 1 : var18 + var9.info;
                  var6.status |= 8;
                  var6.next = var5;
                  var5 = var6;
               }
            }
         }

         this.maxStack = Math.max(var1, var14);
      } else {
         this.maxStack = var1;
         this.maxLocals = var2;
      }

   }

   public void visitEnd() {
   }

   private void addSuccessor(int var1, Label var2) {
      Edge var3 = new Edge();
      var3.info = var1;
      var3.successor = var2;
      var3.next = this.currentBlock.successors;
      this.currentBlock.successors = var3;
   }

   private void noSuccessor() {
      if (this.compute == 0) {
         Label var1 = new Label();
         var1.frame = new Frame();
         var1.frame.owner = var1;
         var1.resolve(this, this.code.length, this.code.data);
         this.previousBlock.successor = var1;
         this.previousBlock = var1;
      } else {
         this.currentBlock.outputStackMax = this.maxStackSize;
      }

      if (this.compute != 1) {
         this.currentBlock = null;
      }

   }

   private void visitFrame(Frame var1) {
      int var2 = 0;
      int var3 = 0;
      int var4 = 0;
      int[] var5 = var1.inputLocals;
      int[] var6 = var1.inputStack;

      int var7;
      int var8;
      for(var7 = 0; var7 < var5.length; ++var7) {
         var8 = var5[var7];
         if (var8 == 16777216) {
            ++var2;
         } else {
            var3 += var2 + 1;
            var2 = 0;
         }

         if (var8 == 16777220 || var8 == 16777219) {
            ++var7;
         }
      }

      for(var7 = 0; var7 < var6.length; ++var7) {
         var8 = var6[var7];
         ++var4;
         if (var8 == 16777220 || var8 == 16777219) {
            ++var7;
         }
      }

      int var9 = this.startFrame(var1.owner.position, var3, var4);

      for(var7 = 0; var3 > 0; --var3) {
         var8 = var5[var7];
         this.frame[var9++] = var8;
         if (var8 == 16777220 || var8 == 16777219) {
            ++var7;
         }

         ++var7;
      }

      for(var7 = 0; var7 < var6.length; ++var7) {
         var8 = var6[var7];
         this.frame[var9++] = var8;
         if (var8 == 16777220 || var8 == 16777219) {
            ++var7;
         }
      }

      this.endFrame();
   }

   private void visitImplicitFirstFrame() {
      int var1 = this.startFrame(0, this.descriptor.length() + 1, 0);
      if ((this.access & 8) == 0) {
         if ((this.access & 524288) == 0) {
            this.frame[var1++] = 24117248 | this.cw.addType(this.cw.thisName);
         } else {
            this.frame[var1++] = 6;
         }
      }

      int var2 = 1;

      while(true) {
         int var3 = var2;
         switch(this.descriptor.charAt(var2++)) {
         case 'B':
         case 'C':
         case 'I':
         case 'S':
         case 'Z':
            this.frame[var1++] = 1;
            break;
         case 'D':
            this.frame[var1++] = 3;
            break;
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
         case 'V':
         case 'W':
         case 'X':
         case 'Y':
         default:
            this.frame[1] = var1 - 3;
            this.endFrame();
            return;
         case 'F':
            this.frame[var1++] = 2;
            break;
         case 'J':
            this.frame[var1++] = 4;
            break;
         case 'L':
            while(this.descriptor.charAt(var2) != ';') {
               ++var2;
            }

            this.frame[var1++] = 24117248 | this.cw.addType(this.descriptor.substring(var3 + 1, var2++));
            break;
         case '[':
            while(this.descriptor.charAt(var2) == '[') {
               ++var2;
            }

            if (this.descriptor.charAt(var2) == 'L') {
               ++var2;

               while(this.descriptor.charAt(var2) != ';') {
                  ++var2;
               }
            }

            int var10001 = var1++;
            ++var2;
            this.frame[var10001] = 24117248 | this.cw.addType(this.descriptor.substring(var3, var2));
         }
      }
   }

   private int startFrame(int var1, int var2, int var3) {
      int var4 = 3 + var2 + var3;
      if (this.frame == null || this.frame.length < var4) {
         this.frame = new int[var4];
      }

      this.frame[0] = var1;
      this.frame[1] = var2;
      this.frame[2] = var3;
      return 3;
   }

   private void endFrame() {
      if (this.previousFrame != null) {
         if (this.stackMap == null) {
            this.stackMap = new ByteVector();
         }

         this.writeFrame();
         ++this.frameCount;
      }

      this.previousFrame = this.frame;
      this.frame = null;
   }

   private void writeFrame() {
      int var1 = this.frame[1];
      int var2 = this.frame[2];
      if ((this.cw.version & '\uffff') < 50) {
         this.stackMap.putShort(this.frame[0]).putShort(var1);
         this.writeFrameTypes(3, 3 + var1);
         this.stackMap.putShort(var2);
         this.writeFrameTypes(3 + var1, 3 + var1 + var2);
      } else {
         int var3 = this.previousFrame[1];
         int var4 = 255;
         int var5 = 0;
         int var6;
         if (this.frameCount == 0) {
            var6 = this.frame[0];
         } else {
            var6 = this.frame[0] - this.previousFrame[0] - 1;
         }

         if (var2 == 0) {
            var5 = var1 - var3;
            switch(var5) {
            case -3:
            case -2:
            case -1:
               var4 = 248;
               var3 = var1;
               break;
            case 0:
               var4 = var6 < 64 ? 0 : 251;
               break;
            case 1:
            case 2:
            case 3:
               var4 = 252;
            }
         } else if (var1 == var3 && var2 == 1) {
            var4 = var6 < 63 ? 64 : 247;
         }

         if (var4 != 255) {
            int var7 = 3;

            for(int var8 = 0; var8 < var3; ++var8) {
               if (this.frame[var7] != this.previousFrame[var7]) {
                  var4 = 255;
                  break;
               }

               ++var7;
            }
         }

         switch(var4) {
         case 0:
            this.stackMap.putByte(var6);
            break;
         case 64:
            this.stackMap.putByte(64 + var6);
            this.writeFrameTypes(3 + var1, 4 + var1);
            break;
         case 247:
            this.stackMap.putByte(247).putShort(var6);
            this.writeFrameTypes(3 + var1, 4 + var1);
            break;
         case 248:
            this.stackMap.putByte(251 + var5).putShort(var6);
            break;
         case 251:
            this.stackMap.putByte(251).putShort(var6);
            break;
         case 252:
            this.stackMap.putByte(251 + var5).putShort(var6);
            this.writeFrameTypes(3 + var3, 3 + var1);
            break;
         default:
            this.stackMap.putByte(255).putShort(var6).putShort(var1);
            this.writeFrameTypes(3, 3 + var1);
            this.stackMap.putShort(var2);
            this.writeFrameTypes(3 + var1, 3 + var1 + var2);
         }

      }
   }

   private void writeFrameTypes(int var1, int var2) {
      for(int var3 = var1; var3 < var2; ++var3) {
         int var4 = this.frame[var3];
         int var5 = var4 & -268435456;
         if (var5 == 0) {
            int var7 = var4 & 1048575;
            switch(var4 & 267386880) {
            case 24117248:
               this.stackMap.putByte(7).putShort(this.cw.newClass(this.cw.typeTable[var7].strVal1));
               break;
            case 25165824:
               this.stackMap.putByte(8).putShort(this.cw.typeTable[var7].intVal);
               break;
            default:
               this.stackMap.putByte(var7);
            }
         } else {
            StringBuilder var6 = new StringBuilder();
            var5 >>= 28;

            while(var5-- > 0) {
               var6.append('[');
            }

            if ((var4 & 267386880) == 24117248) {
               var6.append('L');
               var6.append(this.cw.typeTable[var4 & 1048575].strVal1);
               var6.append(';');
            } else {
               switch(var4 & 15) {
               case 1:
                  var6.append('I');
                  break;
               case 2:
                  var6.append('F');
                  break;
               case 3:
                  var6.append('D');
                  break;
               case 4:
               case 5:
               case 6:
               case 7:
               case 8:
               default:
                  var6.append('J');
                  break;
               case 9:
                  var6.append('Z');
                  break;
               case 10:
                  var6.append('B');
                  break;
               case 11:
                  var6.append('C');
                  break;
               case 12:
                  var6.append('S');
               }
            }

            this.stackMap.putByte(7).putShort(this.cw.newClass(var6.toString()));
         }
      }

   }

   private void writeFrameType(Object var1) {
      if (var1 instanceof String) {
         this.stackMap.putByte(7).putShort(this.cw.newClass((String)var1));
      } else if (var1 instanceof Integer) {
         this.stackMap.putByte((Integer)var1);
      } else {
         this.stackMap.putByte(8).putShort(((Label)var1).position);
      }

   }

   final int getSize() {
      if (this.classReaderOffset != 0) {
         return 6 + this.classReaderLength;
      } else {
         int var1 = 8;
         if (this.code.length > 0) {
            if (this.code.length > 65535) {
               throw new RuntimeException("Method code too large!");
            }

            this.cw.newUTF8("Code");
            var1 += 18 + this.code.length + 8 * this.handlerCount;
            if (this.localVar != null) {
               this.cw.newUTF8("LocalVariableTable");
               var1 += 8 + this.localVar.length;
            }

            if (this.localVarType != null) {
               this.cw.newUTF8("LocalVariableTypeTable");
               var1 += 8 + this.localVarType.length;
            }

            if (this.lineNumber != null) {
               this.cw.newUTF8("LineNumberTable");
               var1 += 8 + this.lineNumber.length;
            }

            if (this.stackMap != null) {
               boolean var2 = (this.cw.version & '\uffff') >= 50;
               this.cw.newUTF8(var2 ? "StackMapTable" : "StackMap");
               var1 += 8 + this.stackMap.length;
            }

            if (this.ctanns != null) {
               this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
               var1 += 8 + this.ctanns.getSize();
            }

            if (this.ictanns != null) {
               this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
               var1 += 8 + this.ictanns.getSize();
            }

            if (this.cattrs != null) {
               var1 += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }
         }

         if (this.exceptionCount > 0) {
            this.cw.newUTF8("Exceptions");
            var1 += 8 + 2 * this.exceptionCount;
         }

         if ((this.access & 4096) != 0 && ((this.cw.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
            this.cw.newUTF8("Synthetic");
            var1 += 6;
         }

         if ((this.access & 131072) != 0) {
            this.cw.newUTF8("Deprecated");
            var1 += 6;
         }

         if (this.signature != null) {
            this.cw.newUTF8("Signature");
            this.cw.newUTF8(this.signature);
            var1 += 8;
         }

         if (this.methodParameters != null) {
            this.cw.newUTF8("MethodParameters");
            var1 += 7 + this.methodParameters.length;
         }

         if (this.annd != null) {
            this.cw.newUTF8("AnnotationDefault");
            var1 += 6 + this.annd.length;
         }

         if (this.anns != null) {
            this.cw.newUTF8("RuntimeVisibleAnnotations");
            var1 += 8 + this.anns.getSize();
         }

         if (this.ianns != null) {
            this.cw.newUTF8("RuntimeInvisibleAnnotations");
            var1 += 8 + this.ianns.getSize();
         }

         if (this.tanns != null) {
            this.cw.newUTF8("RuntimeVisibleTypeAnnotations");
            var1 += 8 + this.tanns.getSize();
         }

         if (this.itanns != null) {
            this.cw.newUTF8("RuntimeInvisibleTypeAnnotations");
            var1 += 8 + this.itanns.getSize();
         }

         int var3;
         if (this.panns != null) {
            this.cw.newUTF8("RuntimeVisibleParameterAnnotations");
            var1 += 7 + 2 * (this.panns.length - this.synthetics);

            for(var3 = this.panns.length - 1; var3 >= this.synthetics; --var3) {
               var1 += this.panns[var3] == null ? 0 : this.panns[var3].getSize();
            }
         }

         if (this.ipanns != null) {
            this.cw.newUTF8("RuntimeInvisibleParameterAnnotations");
            var1 += 7 + 2 * (this.ipanns.length - this.synthetics);

            for(var3 = this.ipanns.length - 1; var3 >= this.synthetics; --var3) {
               var1 += this.ipanns[var3] == null ? 0 : this.ipanns[var3].getSize();
            }
         }

         if (this.attrs != null) {
            var1 += this.attrs.getSize(this.cw, (byte[])null, 0, -1, -1);
         }

         return var1;
      }
   }

   final void put(ByteVector var1) {
      boolean var2 = true;
      int var3 = 917504 | (this.access & 262144) / 64;
      var1.putShort(this.access & ~var3).putShort(this.name).putShort(this.desc);
      if (this.classReaderOffset != 0) {
         var1.putByteArray(this.cw.cr.b, this.classReaderOffset, this.classReaderLength);
      } else {
         int var4 = 0;
         if (this.code.length > 0) {
            ++var4;
         }

         if (this.exceptionCount > 0) {
            ++var4;
         }

         if ((this.access & 4096) != 0 && ((this.cw.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
            ++var4;
         }

         if ((this.access & 131072) != 0) {
            ++var4;
         }

         if (this.signature != null) {
            ++var4;
         }

         if (this.methodParameters != null) {
            ++var4;
         }

         if (this.annd != null) {
            ++var4;
         }

         if (this.anns != null) {
            ++var4;
         }

         if (this.ianns != null) {
            ++var4;
         }

         if (this.tanns != null) {
            ++var4;
         }

         if (this.itanns != null) {
            ++var4;
         }

         if (this.panns != null) {
            ++var4;
         }

         if (this.ipanns != null) {
            ++var4;
         }

         if (this.attrs != null) {
            var4 += this.attrs.getCount();
         }

         var1.putShort(var4);
         int var5;
         if (this.code.length > 0) {
            var5 = 12 + this.code.length + 8 * this.handlerCount;
            if (this.localVar != null) {
               var5 += 8 + this.localVar.length;
            }

            if (this.localVarType != null) {
               var5 += 8 + this.localVarType.length;
            }

            if (this.lineNumber != null) {
               var5 += 8 + this.lineNumber.length;
            }

            if (this.stackMap != null) {
               var5 += 8 + this.stackMap.length;
            }

            if (this.ctanns != null) {
               var5 += 8 + this.ctanns.getSize();
            }

            if (this.ictanns != null) {
               var5 += 8 + this.ictanns.getSize();
            }

            if (this.cattrs != null) {
               var5 += this.cattrs.getSize(this.cw, this.code.data, this.code.length, this.maxStack, this.maxLocals);
            }

            var1.putShort(this.cw.newUTF8("Code")).putInt(var5);
            var1.putShort(this.maxStack).putShort(this.maxLocals);
            var1.putInt(this.code.length).putByteArray(this.code.data, 0, this.code.length);
            var1.putShort(this.handlerCount);
            if (this.handlerCount > 0) {
               for(Handler var6 = this.firstHandler; var6 != null; var6 = var6.next) {
                  var1.putShort(var6.start.position).putShort(var6.end.position).putShort(var6.handler.position).putShort(var6.type);
               }
            }

            var4 = 0;
            if (this.localVar != null) {
               ++var4;
            }

            if (this.localVarType != null) {
               ++var4;
            }

            if (this.lineNumber != null) {
               ++var4;
            }

            if (this.stackMap != null) {
               ++var4;
            }

            if (this.ctanns != null) {
               ++var4;
            }

            if (this.ictanns != null) {
               ++var4;
            }

            if (this.cattrs != null) {
               var4 += this.cattrs.getCount();
            }

            var1.putShort(var4);
            if (this.localVar != null) {
               var1.putShort(this.cw.newUTF8("LocalVariableTable"));
               var1.putInt(this.localVar.length + 2).putShort(this.localVarCount);
               var1.putByteArray(this.localVar.data, 0, this.localVar.length);
            }

            if (this.localVarType != null) {
               var1.putShort(this.cw.newUTF8("LocalVariableTypeTable"));
               var1.putInt(this.localVarType.length + 2).putShort(this.localVarTypeCount);
               var1.putByteArray(this.localVarType.data, 0, this.localVarType.length);
            }

            if (this.lineNumber != null) {
               var1.putShort(this.cw.newUTF8("LineNumberTable"));
               var1.putInt(this.lineNumber.length + 2).putShort(this.lineNumberCount);
               var1.putByteArray(this.lineNumber.data, 0, this.lineNumber.length);
            }

            if (this.stackMap != null) {
               boolean var7 = (this.cw.version & '\uffff') >= 50;
               var1.putShort(this.cw.newUTF8(var7 ? "StackMapTable" : "StackMap"));
               var1.putInt(this.stackMap.length + 2).putShort(this.frameCount);
               var1.putByteArray(this.stackMap.data, 0, this.stackMap.length);
            }

            if (this.ctanns != null) {
               var1.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
               this.ctanns.put(var1);
            }

            if (this.ictanns != null) {
               var1.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
               this.ictanns.put(var1);
            }

            if (this.cattrs != null) {
               this.cattrs.put(this.cw, this.code.data, this.code.length, this.maxLocals, this.maxStack, var1);
            }
         }

         if (this.exceptionCount > 0) {
            var1.putShort(this.cw.newUTF8("Exceptions")).putInt(2 * this.exceptionCount + 2);
            var1.putShort(this.exceptionCount);

            for(var5 = 0; var5 < this.exceptionCount; ++var5) {
               var1.putShort(this.exceptions[var5]);
            }
         }

         if ((this.access & 4096) != 0 && ((this.cw.version & '\uffff') < 49 || (this.access & 262144) != 0)) {
            var1.putShort(this.cw.newUTF8("Synthetic")).putInt(0);
         }

         if ((this.access & 131072) != 0) {
            var1.putShort(this.cw.newUTF8("Deprecated")).putInt(0);
         }

         if (this.signature != null) {
            var1.putShort(this.cw.newUTF8("Signature")).putInt(2).putShort(this.cw.newUTF8(this.signature));
         }

         if (this.methodParameters != null) {
            var1.putShort(this.cw.newUTF8("MethodParameters"));
            var1.putInt(this.methodParameters.length + 1).putByte(this.methodParametersCount);
            var1.putByteArray(this.methodParameters.data, 0, this.methodParameters.length);
         }

         if (this.annd != null) {
            var1.putShort(this.cw.newUTF8("AnnotationDefault"));
            var1.putInt(this.annd.length);
            var1.putByteArray(this.annd.data, 0, this.annd.length);
         }

         if (this.anns != null) {
            var1.putShort(this.cw.newUTF8("RuntimeVisibleAnnotations"));
            this.anns.put(var1);
         }

         if (this.ianns != null) {
            var1.putShort(this.cw.newUTF8("RuntimeInvisibleAnnotations"));
            this.ianns.put(var1);
         }

         if (this.tanns != null) {
            var1.putShort(this.cw.newUTF8("RuntimeVisibleTypeAnnotations"));
            this.tanns.put(var1);
         }

         if (this.itanns != null) {
            var1.putShort(this.cw.newUTF8("RuntimeInvisibleTypeAnnotations"));
            this.itanns.put(var1);
         }

         if (this.panns != null) {
            var1.putShort(this.cw.newUTF8("RuntimeVisibleParameterAnnotations"));
            AnnotationWriter.put(this.panns, this.synthetics, var1);
         }

         if (this.ipanns != null) {
            var1.putShort(this.cw.newUTF8("RuntimeInvisibleParameterAnnotations"));
            AnnotationWriter.put(this.ipanns, this.synthetics, var1);
         }

         if (this.attrs != null) {
            this.attrs.put(this.cw, (byte[])null, 0, -1, -1, var1);
         }

      }
   }
}
