package org.spongepowered.asm.lib.tree.analysis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.IincInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LookupSwitchInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TableSwitchInsnNode;
import org.spongepowered.asm.lib.tree.TryCatchBlockNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;

public class Analyzer implements Opcodes {
   private final Interpreter interpreter;
   private int n;
   private InsnList insns;
   private List[] handlers;
   private Frame[] frames;
   private Subroutine[] subroutines;
   private boolean[] queued;
   private int[] queue;
   private int top;

   public Analyzer(Interpreter var1) {
      this.interpreter = var1;
   }

   public Frame[] analyze(String var1, MethodNode var2) throws AnalyzerException {
      if ((var2.access & 1280) != 0) {
         this.frames = (Frame[])(new Frame[0]);
         return this.frames;
      } else {
         this.n = var2.instructions.size();
         this.insns = var2.instructions;
         this.handlers = (List[])(new List[this.n]);
         this.frames = (Frame[])(new Frame[this.n]);
         this.subroutines = new Subroutine[this.n];
         this.queued = new boolean[this.n];
         this.queue = new int[this.n];
         this.top = 0;

         int var6;
         for(int var3 = 0; var3 < var2.tryCatchBlocks.size(); ++var3) {
            TryCatchBlockNode var4 = (TryCatchBlockNode)var2.tryCatchBlocks.get(var3);
            int var5 = this.insns.indexOf(var4.start);
            var6 = this.insns.indexOf(var4.end);

            for(int var7 = var5; var7 < var6; ++var7) {
               Object var8 = this.handlers[var7];
               if (var8 == null) {
                  var8 = new ArrayList();
                  this.handlers[var7] = (List)var8;
               }

               ((List)var8).add(var4);
            }
         }

         Subroutine var23 = new Subroutine((LabelNode)null, var2.maxLocals, (JumpInsnNode)null);
         ArrayList var24 = new ArrayList();
         HashMap var25 = new HashMap();
         this.findSubroutine(0, var23, var24);

         while(!var24.isEmpty()) {
            JumpInsnNode var26 = (JumpInsnNode)var24.remove(0);
            Subroutine var28 = (Subroutine)var25.get(var26.label);
            if (var28 == null) {
               var28 = new Subroutine(var26.label, var2.maxLocals, var26);
               var25.put(var26.label, var28);
               this.findSubroutine(this.insns.indexOf(var26.label), var28, var24);
            } else {
               var28.callers.add(var26);
            }
         }

         for(var6 = 0; var6 < this.n; ++var6) {
            if (this.subroutines[var6] != null && this.subroutines[var6].start == null) {
               this.subroutines[var6] = null;
            }
         }

         Frame var27 = this.newFrame(var2.maxLocals, var2.maxStack);
         Frame var29 = this.newFrame(var2.maxLocals, var2.maxStack);
         var27.setReturn(this.interpreter.newValue(Type.getReturnType(var2.desc)));
         Type[] var30 = Type.getArgumentTypes(var2.desc);
         int var9 = 0;
         if ((var2.access & 8) == 0) {
            Type var10 = Type.getObjectType(var1);
            var27.setLocal(var9++, this.interpreter.newValue(var10));
         }

         int var31;
         for(var31 = 0; var31 < var30.length; ++var31) {
            var27.setLocal(var9++, this.interpreter.newValue(var30[var31]));
            if (var30[var31].getSize() == 2) {
               var27.setLocal(var9++, this.interpreter.newValue((Type)null));
            }
         }

         while(var9 < var2.maxLocals) {
            var27.setLocal(var9++, this.interpreter.newValue((Type)null));
         }

         this.merge(0, var27, (Subroutine)null);
         this.init(var1, var2);

         while(this.top > 0) {
            var31 = this.queue[--this.top];
            Frame var11 = this.frames[var31];
            Subroutine var12 = this.subroutines[var31];
            this.queued[var31] = false;
            AbstractInsnNode var13 = null;

            try {
               var13 = var2.instructions.get(var31);
               int var14 = var13.getOpcode();
               int var15 = var13.getType();
               int var17;
               if (var15 != 8 && var15 != 15 && var15 != 14) {
                  var27.init(var11).execute(var13, this.interpreter);
                  var12 = var12 == null ? null : var12.copy();
                  if (var13 instanceof JumpInsnNode) {
                     JumpInsnNode var16 = (JumpInsnNode)var13;
                     if (var14 != 167 && var14 != 168) {
                        this.merge(var31 + 1, var27, var12);
                        this.newControlFlowEdge(var31, var31 + 1);
                     }

                     var17 = this.insns.indexOf(var16.label);
                     if (var14 == 168) {
                        this.merge(var17, var27, new Subroutine(var16.label, var2.maxLocals, var16));
                     } else {
                        this.merge(var17, var27, var12);
                     }

                     this.newControlFlowEdge(var31, var17);
                  } else {
                     int var18;
                     LabelNode var19;
                     if (var13 instanceof LookupSwitchInsnNode) {
                        LookupSwitchInsnNode var32 = (LookupSwitchInsnNode)var13;
                        var17 = this.insns.indexOf(var32.dflt);
                        this.merge(var17, var27, var12);
                        this.newControlFlowEdge(var31, var17);

                        for(var18 = 0; var18 < var32.labels.size(); ++var18) {
                           var19 = (LabelNode)var32.labels.get(var18);
                           var17 = this.insns.indexOf(var19);
                           this.merge(var17, var27, var12);
                           this.newControlFlowEdge(var31, var17);
                        }
                     } else if (var13 instanceof TableSwitchInsnNode) {
                        TableSwitchInsnNode var33 = (TableSwitchInsnNode)var13;
                        var17 = this.insns.indexOf(var33.dflt);
                        this.merge(var17, var27, var12);
                        this.newControlFlowEdge(var31, var17);

                        for(var18 = 0; var18 < var33.labels.size(); ++var18) {
                           var19 = (LabelNode)var33.labels.get(var18);
                           var17 = this.insns.indexOf(var19);
                           this.merge(var17, var27, var12);
                           this.newControlFlowEdge(var31, var17);
                        }
                     } else {
                        int var34;
                        if (var14 == 169) {
                           if (var12 == null) {
                              throw new AnalyzerException(var13, "RET instruction outside of a sub routine");
                           }

                           for(var34 = 0; var34 < var12.callers.size(); ++var34) {
                              JumpInsnNode var36 = (JumpInsnNode)var12.callers.get(var34);
                              var18 = this.insns.indexOf(var36);
                              if (this.frames[var18] != null) {
                                 this.merge(var18 + 1, this.frames[var18], var27, this.subroutines[var18], var12.access);
                                 this.newControlFlowEdge(var31, var18 + 1);
                              }
                           }
                        } else if (var14 != 191 && (var14 < 172 || var14 > 177)) {
                           if (var12 != null) {
                              if (var13 instanceof VarInsnNode) {
                                 var34 = ((VarInsnNode)var13).var;
                                 var12.access[var34] = true;
                                 if (var14 == 22 || var14 == 24 || var14 == 55 || var14 == 57) {
                                    var12.access[var34 + 1] = true;
                                 }
                              } else if (var13 instanceof IincInsnNode) {
                                 var34 = ((IincInsnNode)var13).var;
                                 var12.access[var34] = true;
                              }
                           }

                           this.merge(var31 + 1, var27, var12);
                           this.newControlFlowEdge(var31, var31 + 1);
                        }
                     }
                  }
               } else {
                  this.merge(var31 + 1, var11, var12);
                  this.newControlFlowEdge(var31, var31 + 1);
               }

               List var35 = this.handlers[var31];
               if (var35 != null) {
                  for(var17 = 0; var17 < var35.size(); ++var17) {
                     TryCatchBlockNode var37 = (TryCatchBlockNode)var35.get(var17);
                     Type var38;
                     if (var37.type == null) {
                        var38 = Type.getObjectType("java/lang/Throwable");
                     } else {
                        var38 = Type.getObjectType(var37.type);
                     }

                     int var20 = this.insns.indexOf(var37.handler);
                     if (this.newControlFlowExceptionEdge(var31, var37)) {
                        var29.init(var11);
                        var29.clearStack();
                        var29.push(this.interpreter.newValue(var38));
                        this.merge(var20, var29, var12);
                     }
                  }
               }
            } catch (AnalyzerException var21) {
               throw new AnalyzerException(var21.node, "Error at instruction " + var31 + ": " + var21.getMessage(), var21);
            } catch (Exception var22) {
               throw new AnalyzerException(var13, "Error at instruction " + var31 + ": " + var22.getMessage(), var22);
            }
         }

         return this.frames;
      }
   }

   private void findSubroutine(int var1, Subroutine var2, List var3) throws AnalyzerException {
      while(var1 >= 0 && var1 < this.n) {
         if (this.subroutines[var1] != null) {
            return;
         }

         this.subroutines[var1] = var2.copy();
         AbstractInsnNode var4 = this.insns.get(var1);
         int var6;
         if (var4 instanceof JumpInsnNode) {
            if (var4.getOpcode() == 168) {
               var3.add(var4);
            } else {
               JumpInsnNode var5 = (JumpInsnNode)var4;
               this.findSubroutine(this.insns.indexOf(var5.label), var2, var3);
            }
         } else {
            LabelNode var7;
            if (var4 instanceof TableSwitchInsnNode) {
               TableSwitchInsnNode var8 = (TableSwitchInsnNode)var4;
               this.findSubroutine(this.insns.indexOf(var8.dflt), var2, var3);

               for(var6 = var8.labels.size() - 1; var6 >= 0; --var6) {
                  var7 = (LabelNode)var8.labels.get(var6);
                  this.findSubroutine(this.insns.indexOf(var7), var2, var3);
               }
            } else if (var4 instanceof LookupSwitchInsnNode) {
               LookupSwitchInsnNode var9 = (LookupSwitchInsnNode)var4;
               this.findSubroutine(this.insns.indexOf(var9.dflt), var2, var3);

               for(var6 = var9.labels.size() - 1; var6 >= 0; --var6) {
                  var7 = (LabelNode)var9.labels.get(var6);
                  this.findSubroutine(this.insns.indexOf(var7), var2, var3);
               }
            }
         }

         List var10 = this.handlers[var1];
         if (var10 != null) {
            for(var6 = 0; var6 < var10.size(); ++var6) {
               TryCatchBlockNode var11 = (TryCatchBlockNode)var10.get(var6);
               this.findSubroutine(this.insns.indexOf(var11.handler), var2, var3);
            }
         }

         switch(var4.getOpcode()) {
         case 167:
         case 169:
         case 170:
         case 171:
         case 172:
         case 173:
         case 174:
         case 175:
         case 176:
         case 177:
         case 191:
            return;
         case 168:
         case 178:
         case 179:
         case 180:
         case 181:
         case 182:
         case 183:
         case 184:
         case 185:
         case 186:
         case 187:
         case 188:
         case 189:
         case 190:
         default:
            ++var1;
         }
      }

      throw new AnalyzerException((AbstractInsnNode)null, "Execution can fall off end of the code");
   }

   public Frame[] getFrames() {
      return this.frames;
   }

   public List getHandlers(int var1) {
      return this.handlers[var1];
   }

   protected void init(String var1, MethodNode var2) throws AnalyzerException {
   }

   protected Frame newFrame(int var1, int var2) {
      return new Frame(var1, var2);
   }

   protected Frame newFrame(Frame var1) {
      return new Frame(var1);
   }

   protected void newControlFlowEdge(int var1, int var2) {
   }

   protected boolean newControlFlowExceptionEdge(int var1, int var2) {
      return true;
   }

   protected boolean newControlFlowExceptionEdge(int var1, TryCatchBlockNode var2) {
      return this.newControlFlowExceptionEdge(var1, this.insns.indexOf(var2.handler));
   }

   private void merge(int var1, Frame var2, Subroutine var3) throws AnalyzerException {
      Frame var4 = this.frames[var1];
      Subroutine var5 = this.subroutines[var1];
      boolean var6;
      if (var4 == null) {
         this.frames[var1] = this.newFrame(var2);
         var6 = true;
      } else {
         var6 = var4.merge(var2, this.interpreter);
      }

      if (var5 == null) {
         if (var3 != null) {
            this.subroutines[var1] = var3.copy();
            var6 = true;
         }
      } else if (var3 != null) {
         var6 |= var5.merge(var3);
      }

      if (var6 && !this.queued[var1]) {
         this.queued[var1] = true;
         this.queue[this.top++] = var1;
      }

   }

   private void merge(int var1, Frame var2, Frame var3, Subroutine var4, boolean[] var5) throws AnalyzerException {
      Frame var6 = this.frames[var1];
      Subroutine var7 = this.subroutines[var1];
      var3.merge(var2, var5);
      boolean var8;
      if (var6 == null) {
         this.frames[var1] = this.newFrame(var3);
         var8 = true;
      } else {
         var8 = var6.merge(var3, this.interpreter);
      }

      if (var7 != null && var4 != null) {
         var8 |= var7.merge(var4);
      }

      if (var8 && !this.queued[var1]) {
         this.queued[var1] = true;
         this.queue[this.top++] = var1;
      }

   }
}
