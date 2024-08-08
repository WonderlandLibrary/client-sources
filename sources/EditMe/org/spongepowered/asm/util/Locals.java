package org.spongepowered.asm.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.tree.analysis.Analyzer;
import org.spongepowered.asm.lib.tree.analysis.AnalyzerException;
import org.spongepowered.asm.lib.tree.analysis.BasicValue;
import org.spongepowered.asm.lib.tree.analysis.Frame;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.asm.MixinVerifier;
import org.spongepowered.asm.util.throwables.LVTGeneratorException;

public final class Locals {
   private static final Map calculatedLocalVariables = new HashMap();

   private Locals() {
   }

   public static void loadLocals(Type[] var0, InsnList var1, int var2, int var3) {
      for(; var2 < var0.length && var3 > 0; ++var2) {
         if (var0[var2] != null) {
            var1.add((AbstractInsnNode)(new VarInsnNode(var0[var2].getOpcode(21), var2)));
            --var3;
         }
      }

   }

   public static LocalVariableNode[] getLocalsAt(ClassNode var0, MethodNode var1, AbstractInsnNode var2) {
      for(int var3 = 0; var3 < 3 && (var2 instanceof LabelNode || var2 instanceof LineNumberNode); ++var3) {
         var2 = nextNode(var1.instructions, var2);
      }

      ClassInfo var22 = ClassInfo.forName(var0.name);
      if (var22 == null) {
         throw new LVTGeneratorException("Could not load class metadata for " + var0.name + " generating LVT for " + var1.name);
      } else {
         ClassInfo.Method var4 = var22.findMethod(var1);
         if (var4 == null) {
            throw new LVTGeneratorException("Could not locate method metadata for " + var1.name + " generating LVT in " + var0.name);
         } else {
            List var5 = var4.getFrames();
            LocalVariableNode[] var6 = new LocalVariableNode[var1.maxLocals];
            int var7 = 0;
            int var8 = 0;
            if ((var1.access & 8) == 0) {
               var6[var7++] = new LocalVariableNode("this", var0.name, (String)null, (LabelNode)null, (LabelNode)null, 0);
            }

            Type[] var9 = Type.getArgumentTypes(var1.desc);
            int var10 = var9.length;

            int var11;
            for(var11 = 0; var11 < var10; ++var11) {
               Type var12 = var9[var11];
               var6[var7] = new LocalVariableNode("arg" + var8++, var12.toString(), (String)null, (LabelNode)null, (LabelNode)null, var7);
               var7 += var12.getSize();
            }

            int var23 = var7;
            var10 = -1;
            var11 = 0;
            ListIterator var24 = var1.instructions.iterator();

            while(var24.hasNext()) {
               AbstractInsnNode var13 = (AbstractInsnNode)var24.next();
               if (var13 instanceof FrameNode) {
                  ++var10;
                  FrameNode var14 = (FrameNode)var13;
                  ClassInfo.FrameData var15 = var10 < var5.size() ? (ClassInfo.FrameData)var5.get(var10) : null;
                  var11 = var15 != null && var15.type == 0 ? Math.min(var11, var15.locals) : var14.local.size();
                  int var16 = 0;

                  for(int var17 = 0; var17 < var6.length; ++var16) {
                     Object var18 = var16 < var14.local.size() ? var14.local.get(var16) : null;
                     if (var18 instanceof String) {
                        var6[var17] = getLocalVariableAt(var0, var1, var2, var17);
                     } else if (!(var18 instanceof Integer)) {
                        if (var18 != null) {
                           throw new LVTGeneratorException("Invalid value " + var18 + " in locals array at position " + var16 + " in " + var0.name + "." + var1.name + var1.desc);
                        }

                        if (var17 >= var23 && var17 >= var11 && var11 > 0) {
                           var6[var17] = null;
                        }
                     } else {
                        boolean var19 = var18 == Opcodes.UNINITIALIZED_THIS || var18 == Opcodes.NULL;
                        boolean var20 = var18 == Opcodes.INTEGER || var18 == Opcodes.FLOAT;
                        boolean var21 = var18 == Opcodes.DOUBLE || var18 == Opcodes.LONG;
                        if (var18 != Opcodes.TOP) {
                           if (var19) {
                              var6[var17] = null;
                           } else {
                              if (!var20 && !var21) {
                                 throw new LVTGeneratorException("Unrecognised locals opcode " + var18 + " in locals array at position " + var16 + " in " + var0.name + "." + var1.name + var1.desc);
                              }

                              var6[var17] = getLocalVariableAt(var0, var1, var2, var17);
                              if (var21) {
                                 ++var17;
                                 var6[var17] = null;
                              }
                           }
                        }
                     }

                     ++var17;
                  }
               } else if (var13 instanceof VarInsnNode) {
                  VarInsnNode var26 = (VarInsnNode)var13;
                  var6[var26.var] = getLocalVariableAt(var0, var1, var2, var26.var);
               }

               if (var13 == var2) {
                  break;
               }
            }

            for(int var25 = 0; var25 < var6.length; ++var25) {
               if (var6[var25] != null && var6[var25].desc == null) {
                  var6[var25] = null;
               }
            }

            return var6;
         }
      }
   }

   public static LocalVariableNode getLocalVariableAt(ClassNode var0, MethodNode var1, AbstractInsnNode var2, int var3) {
      return getLocalVariableAt(var0, var1, var1.instructions.indexOf(var2), var3);
   }

   private static LocalVariableNode getLocalVariableAt(ClassNode var0, MethodNode var1, int var2, int var3) {
      LocalVariableNode var4 = null;
      LocalVariableNode var5 = null;
      Iterator var6 = getLocalVariableTable(var0, var1).iterator();

      LocalVariableNode var7;
      while(var6.hasNext()) {
         var7 = (LocalVariableNode)var6.next();
         if (var7.index == var3) {
            if (isOpcodeInRange(var1.instructions, var7, var2)) {
               var4 = var7;
            } else if (var4 == null) {
               var5 = var7;
            }
         }
      }

      if (var4 == null && !var1.localVariables.isEmpty()) {
         var6 = getGeneratedLocalVariableTable(var0, var1).iterator();

         while(var6.hasNext()) {
            var7 = (LocalVariableNode)var6.next();
            if (var7.index == var3 && isOpcodeInRange(var1.instructions, var7, var2)) {
               var4 = var7;
            }
         }
      }

      return var4 != null ? var4 : var5;
   }

   private static boolean isOpcodeInRange(InsnList var0, LocalVariableNode var1, int var2) {
      return var0.indexOf(var1.start) < var2 && var0.indexOf(var1.end) > var2;
   }

   public static List getLocalVariableTable(ClassNode var0, MethodNode var1) {
      return var1.localVariables.isEmpty() ? getGeneratedLocalVariableTable(var0, var1) : var1.localVariables;
   }

   public static List getGeneratedLocalVariableTable(ClassNode var0, MethodNode var1) {
      String var2 = String.format("%s.%s%s", var0.name, var1.name, var1.desc);
      List var3 = (List)calculatedLocalVariables.get(var2);
      if (var3 != null) {
         return var3;
      } else {
         var3 = generateLocalVariableTable(var0, var1);
         calculatedLocalVariables.put(var2, var3);
         return var3;
      }
   }

   public static List generateLocalVariableTable(ClassNode var0, MethodNode var1) {
      ArrayList var2 = null;
      if (var0.interfaces != null) {
         var2 = new ArrayList();
         Iterator var3 = var0.interfaces.iterator();

         while(var3.hasNext()) {
            String var4 = (String)var3.next();
            var2.add(Type.getObjectType(var4));
         }
      }

      Type var19 = null;
      if (var0.superName != null) {
         var19 = Type.getObjectType(var0.superName);
      }

      Analyzer var20 = new Analyzer(new MixinVerifier(Type.getObjectType(var0.name), var19, var2, false));

      try {
         var20.analyze(var0.name, var1);
      } catch (AnalyzerException var18) {
         var18.printStackTrace();
      }

      Frame[] var5 = var20.getFrames();
      int var6 = var1.instructions.size();
      ArrayList var7 = new ArrayList();
      LocalVariableNode[] var8 = new LocalVariableNode[var1.maxLocals];
      BasicValue[] var9 = new BasicValue[var1.maxLocals];
      LabelNode[] var10 = new LabelNode[var6];
      String[] var11 = new String[var1.maxLocals];

      for(int var12 = 0; var12 < var6; ++var12) {
         Frame var13 = var5[var12];
         if (var13 != null) {
            LabelNode var14 = null;

            for(int var15 = 0; var15 < var13.getLocals(); ++var15) {
               BasicValue var16 = (BasicValue)var13.getLocal(var15);
               if ((var16 != null || var9[var15] != null) && (var16 == null || !var16.equals(var9[var15]))) {
                  if (var14 == null) {
                     AbstractInsnNode var17 = var1.instructions.get(var12);
                     if (var17 instanceof LabelNode) {
                        var14 = (LabelNode)var17;
                     } else {
                        var10[var12] = var14 = new LabelNode();
                     }
                  }

                  if (var16 == null && var9[var15] != null) {
                     var7.add(var8[var15]);
                     var8[var15].end = var14;
                     var8[var15] = null;
                  } else if (var16 != null) {
                     if (var9[var15] != null) {
                        var7.add(var8[var15]);
                        var8[var15].end = var14;
                        var8[var15] = null;
                     }

                     String var23 = var16.getType() != null ? var16.getType().getDescriptor() : var11[var15];
                     var8[var15] = new LocalVariableNode("var" + var15, var23, (String)null, var14, (LabelNode)null, var15);
                     if (var23 != null) {
                        var11[var15] = var23;
                     }
                  }

                  var9[var15] = var16;
               }
            }
         }
      }

      LabelNode var21 = null;

      int var22;
      for(var22 = 0; var22 < var8.length; ++var22) {
         if (var8[var22] != null) {
            if (var21 == null) {
               var21 = new LabelNode();
               var1.instructions.add((AbstractInsnNode)var21);
            }

            var8[var22].end = var21;
            var7.add(var8[var22]);
         }
      }

      for(var22 = var6 - 1; var22 >= 0; --var22) {
         if (var10[var22] != null) {
            var1.instructions.insert(var1.instructions.get(var22), (AbstractInsnNode)var10[var22]);
         }
      }

      return var7;
   }

   private static AbstractInsnNode nextNode(InsnList var0, AbstractInsnNode var1) {
      int var2 = var0.indexOf(var1) + 1;
      return var2 > 0 && var2 < var0.size() ? var0.get(var2) : var1;
   }
}
