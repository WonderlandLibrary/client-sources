package org.spongepowered.asm.util;

import com.google.common.base.Joiner;
import com.google.common.primitives.Ints;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.regex.Pattern;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.lib.ClassReader;
import org.spongepowered.asm.lib.ClassWriter;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.FieldInsnNode;
import org.spongepowered.asm.lib.tree.FieldNode;
import org.spongepowered.asm.lib.tree.FrameNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.IntInsnNode;
import org.spongepowered.asm.lib.tree.JumpInsnNode;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LdcInsnNode;
import org.spongepowered.asm.lib.tree.LineNumberNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.lib.tree.VarInsnNode;
import org.spongepowered.asm.lib.util.CheckClassAdapter;
import org.spongepowered.asm.lib.util.TraceClassVisitor;
import org.spongepowered.asm.mixin.Debug;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Intrinsic;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.util.throwables.SyntheticBridgeException;

public final class Bytecode {
   public static final int[] CONSTANTS_INT = new int[]{2, 3, 4, 5, 6, 7, 8};
   public static final int[] CONSTANTS_FLOAT = new int[]{11, 12, 13};
   public static final int[] CONSTANTS_DOUBLE = new int[]{14, 15};
   public static final int[] CONSTANTS_LONG = new int[]{9, 10};
   public static final int[] CONSTANTS_ALL = new int[]{1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18};
   private static final Object[] CONSTANTS_VALUES = new Object[]{null, -1, 0, 1, 2, 3, 4, 5, 0L, 1L, 0.0F, 1.0F, 2.0F, 0.0D, 1.0D};
   private static final String[] CONSTANTS_TYPES = new String[]{null, "I", "I", "I", "I", "I", "I", "I", "J", "J", "F", "F", "F", "D", "D", "I", "I"};
   private static final String[] BOXING_TYPES = new String[]{null, "java/lang/Boolean", "java/lang/Character", "java/lang/Byte", "java/lang/Short", "java/lang/Integer", "java/lang/Float", "java/lang/Long", "java/lang/Double", null, null, null};
   private static final String[] UNBOXING_METHODS = new String[]{null, "booleanValue", "charValue", "byteValue", "shortValue", "intValue", "floatValue", "longValue", "doubleValue", null, null, null};
   private static final Class[] MERGEABLE_MIXIN_ANNOTATIONS = new Class[]{Overwrite.class, Intrinsic.class, Final.class, Debug.class};
   private static Pattern mergeableAnnotationPattern = getMergeableAnnotationPattern();
   private static final Logger logger = LogManager.getLogger("mixin");

   private Bytecode() {
   }

   public static MethodNode findMethod(ClassNode var0, String var1, String var2) {
      Iterator var3 = var0.methods.iterator();

      MethodNode var4;
      do {
         if (!var3.hasNext()) {
            return null;
         }

         var4 = (MethodNode)var3.next();
      } while(!var4.name.equals(var1) || !var4.desc.equals(var2));

      return var4;
   }

   public static AbstractInsnNode findInsn(MethodNode var0, int var1) {
      ListIterator var2 = var0.instructions.iterator();

      AbstractInsnNode var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (AbstractInsnNode)var2.next();
      } while(var3.getOpcode() != var1);

      return var3;
   }

   public static MethodInsnNode findSuperInit(MethodNode var0, String var1) {
      if (!"<init>".equals(var0.name)) {
         return null;
      } else {
         int var2 = 0;
         ListIterator var3 = var0.instructions.iterator();

         while(true) {
            while(var3.hasNext()) {
               AbstractInsnNode var4 = (AbstractInsnNode)var3.next();
               if (var4 instanceof TypeInsnNode && var4.getOpcode() == 187) {
                  ++var2;
               } else if (var4 instanceof MethodInsnNode && var4.getOpcode() == 183) {
                  MethodInsnNode var5 = (MethodInsnNode)var4;
                  if ("<init>".equals(var5.name)) {
                     if (var2 > 0) {
                        --var2;
                     } else if (var5.owner.equals(var1)) {
                        return var5;
                     }
                  }
               }
            }

            return null;
         }
      }
   }

   public static void textify(ClassNode var0, OutputStream var1) {
      var0.accept(new TraceClassVisitor(new PrintWriter(var1)));
   }

   public static void textify(MethodNode var0, OutputStream var1) {
      TraceClassVisitor var2 = new TraceClassVisitor(new PrintWriter(var1));
      MethodVisitor var3 = var2.visitMethod(var0.access, var0.name, var0.desc, var0.signature, (String[])var0.exceptions.toArray(new String[0]));
      var0.accept(var3);
      var2.visitEnd();
   }

   public static void dumpClass(ClassNode var0) {
      ClassWriter var1 = new ClassWriter(3);
      var0.accept(var1);
      dumpClass(var1.toByteArray());
   }

   public static void dumpClass(byte[] var0) {
      ClassReader var1 = new ClassReader(var0);
      CheckClassAdapter.verify(var1, true, new PrintWriter(System.out));
   }

   public static void printMethodWithOpcodeIndices(MethodNode var0) {
      System.err.printf("%s%s\n", var0.name, var0.desc);
      int var1 = 0;
      ListIterator var2 = var0.instructions.iterator();

      while(var2.hasNext()) {
         System.err.printf("[%4d] %s\n", var1++, describeNode((AbstractInsnNode)var2.next()));
      }

   }

   public static void printMethod(MethodNode var0) {
      System.err.printf("%s%s\n", var0.name, var0.desc);
      ListIterator var1 = var0.instructions.iterator();

      while(var1.hasNext()) {
         System.err.print("  ");
         printNode((AbstractInsnNode)var1.next());
      }

   }

   public static void printNode(AbstractInsnNode var0) {
      System.err.printf("%s\n", describeNode(var0));
   }

   public static String describeNode(AbstractInsnNode var0) {
      if (var0 == null) {
         return String.format("   %-14s ", "null");
      } else if (var0 instanceof LabelNode) {
         return String.format("[%s]", ((LabelNode)var0).getLabel());
      } else {
         String var1 = String.format("   %-14s ", var0.getClass().getSimpleName().replace("Node", ""));
         if (var0 instanceof JumpInsnNode) {
            var1 = var1 + String.format("[%s] [%s]", getOpcodeName(var0), ((JumpInsnNode)var0).label.getLabel());
         } else if (var0 instanceof VarInsnNode) {
            var1 = var1 + String.format("[%s] %d", getOpcodeName(var0), ((VarInsnNode)var0).var);
         } else if (var0 instanceof MethodInsnNode) {
            MethodInsnNode var2 = (MethodInsnNode)var0;
            var1 = var1 + String.format("[%s] %s %s %s", getOpcodeName(var0), var2.owner, var2.name, var2.desc);
         } else if (var0 instanceof FieldInsnNode) {
            FieldInsnNode var3 = (FieldInsnNode)var0;
            var1 = var1 + String.format("[%s] %s %s %s", getOpcodeName(var0), var3.owner, var3.name, var3.desc);
         } else if (var0 instanceof LineNumberNode) {
            LineNumberNode var4 = (LineNumberNode)var0;
            var1 = var1 + String.format("LINE=[%d] LABEL=[%s]", var4.line, var4.start.getLabel());
         } else if (var0 instanceof LdcInsnNode) {
            var1 = var1 + ((LdcInsnNode)var0).cst;
         } else if (var0 instanceof IntInsnNode) {
            var1 = var1 + ((IntInsnNode)var0).operand;
         } else if (var0 instanceof FrameNode) {
            var1 = var1 + String.format("[%s] ", getOpcodeName(((FrameNode)var0).type, "H_INVOKEINTERFACE", -1));
         } else {
            var1 = var1 + String.format("[%s] ", getOpcodeName(var0));
         }

         return var1;
      }
   }

   public static String getOpcodeName(AbstractInsnNode var0) {
      return var0 != null ? getOpcodeName(var0.getOpcode()) : "";
   }

   public static String getOpcodeName(int var0) {
      return getOpcodeName(var0, "UNINITIALIZED_THIS", 1);
   }

   private static String getOpcodeName(int var0, String var1, int var2) {
      if (var0 >= var2) {
         boolean var3 = false;

         try {
            Field[] var4 = Opcodes.class.getDeclaredFields();
            int var5 = var4.length;

            for(int var6 = 0; var6 < var5; ++var6) {
               Field var7 = var4[var6];
               if (var3 || var7.getName().equals(var1)) {
                  var3 = true;
                  if (var7.getType() == Integer.TYPE && var7.getInt((Object)null) == var0) {
                     return var7.getName();
                  }
               }
            }
         } catch (Exception var8) {
         }
      }

      return var0 >= 0 ? String.valueOf(var0) : "UNKNOWN";
   }

   public static boolean methodHasLineNumbers(MethodNode var0) {
      ListIterator var1 = var0.instructions.iterator();

      do {
         if (!var1.hasNext()) {
            return false;
         }
      } while(!(var1.next() instanceof LineNumberNode));

      return true;
   }

   public static boolean methodIsStatic(MethodNode var0) {
      return (var0.access & 8) == 8;
   }

   public static boolean fieldIsStatic(FieldNode var0) {
      return (var0.access & 8) == 8;
   }

   public static int getFirstNonArgLocalIndex(MethodNode var0) {
      return getFirstNonArgLocalIndex(Type.getArgumentTypes(var0.desc), (var0.access & 8) == 0);
   }

   public static int getFirstNonArgLocalIndex(Type[] var0, boolean var1) {
      return getArgsSize(var0) + (var1 ? 1 : 0);
   }

   public static int getArgsSize(Type[] var0) {
      int var1 = 0;
      Type[] var2 = var0;
      int var3 = var0.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         Type var5 = var2[var4];
         var1 += var5.getSize();
      }

      return var1;
   }

   public static void loadArgs(Type[] var0, InsnList var1, int var2) {
      loadArgs(var0, var1, var2, -1);
   }

   public static void loadArgs(Type[] var0, InsnList var1, int var2, int var3) {
      loadArgs(var0, var1, var2, var3, (Type[])null);
   }

   public static void loadArgs(Type[] var0, InsnList var1, int var2, int var3, Type[] var4) {
      int var5 = var2;
      int var6 = 0;
      Type[] var7 = var0;
      int var8 = var0.length;

      for(int var9 = 0; var9 < var8; ++var9) {
         Type var10 = var7[var9];
         var1.add((AbstractInsnNode)(new VarInsnNode(var10.getOpcode(21), var5)));
         if (var4 != null && var6 < var4.length && var4[var6] != null) {
            var1.add((AbstractInsnNode)(new TypeInsnNode(192, var4[var6].getInternalName())));
         }

         var5 += var10.getSize();
         if (var3 >= var2 && var5 >= var3) {
            return;
         }

         ++var6;
      }

   }

   public static Map cloneLabels(InsnList var0) {
      HashMap var1 = new HashMap();
      ListIterator var2 = var0.iterator();

      while(var2.hasNext()) {
         AbstractInsnNode var3 = (AbstractInsnNode)var2.next();
         if (var3 instanceof LabelNode) {
            var1.put((LabelNode)var3, new LabelNode(((LabelNode)var3).getLabel()));
         }
      }

      return var1;
   }

   public static String generateDescriptor(Object var0, Object... var1) {
      StringBuilder var2 = (new StringBuilder()).append('(');
      Object[] var3 = var1;
      int var4 = var1.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         Object var6 = var3[var5];
         var2.append(toDescriptor(var6));
      }

      return var2.append(')').append(var0 != null ? toDescriptor(var0) : "V").toString();
   }

   private static String toDescriptor(Object var0) {
      if (var0 instanceof String) {
         return (String)var0;
      } else if (var0 instanceof Type) {
         return var0.toString();
      } else if (var0 instanceof Class) {
         return Type.getDescriptor((Class)var0);
      } else {
         return var0 == null ? "" : var0.toString();
      }
   }

   public static String getDescriptor(Type[] var0) {
      return "(" + Joiner.on("").join(var0) + ")";
   }

   public static String getDescriptor(Type[] var0, Type var1) {
      return getDescriptor(var0) + var1.toString();
   }

   public static String changeDescriptorReturnType(String var0, String var1) {
      if (var0 == null) {
         return null;
      } else {
         return var1 == null ? var0 : var0.substring(0, var0.lastIndexOf(41) + 1) + var1;
      }
   }

   public static String getSimpleName(Class var0) {
      return var0.getSimpleName();
   }

   public static String getSimpleName(AnnotationNode var0) {
      return getSimpleName(var0.desc);
   }

   public static String getSimpleName(String var0) {
      int var1 = Math.max(var0.lastIndexOf(47), 0);
      return var0.substring(var1 + 1).replace(";", "");
   }

   public static boolean isConstant(AbstractInsnNode var0) {
      return var0 == null ? false : Ints.contains(CONSTANTS_ALL, var0.getOpcode());
   }

   public static Object getConstant(AbstractInsnNode var0) {
      if (var0 == null) {
         return null;
      } else if (var0 instanceof LdcInsnNode) {
         return ((LdcInsnNode)var0).cst;
      } else {
         int var1;
         if (var0 instanceof IntInsnNode) {
            var1 = ((IntInsnNode)var0).operand;
            if (var0.getOpcode() != 16 && var0.getOpcode() != 17) {
               throw new IllegalArgumentException("IntInsnNode with invalid opcode " + var0.getOpcode() + " in getConstant");
            } else {
               return var1;
            }
         } else {
            var1 = Ints.indexOf(CONSTANTS_ALL, var0.getOpcode());
            return var1 < 0 ? null : CONSTANTS_VALUES[var1];
         }
      }
   }

   public static Type getConstantType(AbstractInsnNode var0) {
      if (var0 == null) {
         return null;
      } else if (var0 instanceof LdcInsnNode) {
         Object var2 = ((LdcInsnNode)var0).cst;
         if (var2 instanceof Integer) {
            return Type.getType("I");
         } else if (var2 instanceof Float) {
            return Type.getType("F");
         } else if (var2 instanceof Long) {
            return Type.getType("J");
         } else if (var2 instanceof Double) {
            return Type.getType("D");
         } else if (var2 instanceof String) {
            return Type.getType("Ljava/lang/String;");
         } else if (var2 instanceof Type) {
            return Type.getType("Ljava/lang/Class;");
         } else {
            throw new IllegalArgumentException("LdcInsnNode with invalid payload type " + var2.getClass() + " in getConstant");
         }
      } else {
         int var1 = Ints.indexOf(CONSTANTS_ALL, var0.getOpcode());
         return var1 < 0 ? null : Type.getType(CONSTANTS_TYPES[var1]);
      }
   }

   public static boolean hasFlag(ClassNode var0, int var1) {
      return (var0.access & var1) == var1;
   }

   public static boolean hasFlag(MethodNode var0, int var1) {
      return (var0.access & var1) == var1;
   }

   public static boolean hasFlag(FieldNode var0, int var1) {
      return (var0.access & var1) == var1;
   }

   public static boolean compareFlags(MethodNode var0, MethodNode var1, int var2) {
      return hasFlag(var0, var2) == hasFlag(var1, var2);
   }

   public static boolean compareFlags(FieldNode var0, FieldNode var1, int var2) {
      return hasFlag(var0, var2) == hasFlag(var1, var2);
   }

   public static Bytecode.Visibility getVisibility(MethodNode var0) {
      return getVisibility(var0.access & 7);
   }

   public static Bytecode.Visibility getVisibility(FieldNode var0) {
      return getVisibility(var0.access & 7);
   }

   private static Bytecode.Visibility getVisibility(int var0) {
      if ((var0 & 4) != 0) {
         return Bytecode.Visibility.PROTECTED;
      } else if ((var0 & 2) != 0) {
         return Bytecode.Visibility.PRIVATE;
      } else {
         return (var0 & 1) != 0 ? Bytecode.Visibility.PUBLIC : Bytecode.Visibility.PACKAGE;
      }
   }

   public static void setVisibility(MethodNode var0, Bytecode.Visibility var1) {
      var0.access = setVisibility(var0.access, var1.access);
   }

   public static void setVisibility(FieldNode var0, Bytecode.Visibility var1) {
      var0.access = setVisibility(var0.access, var1.access);
   }

   public static void setVisibility(MethodNode var0, int var1) {
      var0.access = setVisibility(var0.access, var1);
   }

   public static void setVisibility(FieldNode var0, int var1) {
      var0.access = setVisibility(var0.access, var1);
   }

   private static int setVisibility(int var0, int var1) {
      return var0 & -8 | var1 & 7;
   }

   public static int getMaxLineNumber(ClassNode var0, int var1, int var2) {
      int var3 = 0;
      Iterator var4 = var0.methods.iterator();

      while(var4.hasNext()) {
         MethodNode var5 = (MethodNode)var4.next();
         ListIterator var6 = var5.instructions.iterator();

         while(var6.hasNext()) {
            AbstractInsnNode var7 = (AbstractInsnNode)var6.next();
            if (var7 instanceof LineNumberNode) {
               var3 = Math.max(var3, ((LineNumberNode)var7).line);
            }
         }
      }

      return Math.max(var1, var3 + var2);
   }

   public static String getBoxingType(Type var0) {
      return var0 == null ? null : BOXING_TYPES[var0.getSort()];
   }

   public static String getUnboxingMethod(Type var0) {
      return var0 == null ? null : UNBOXING_METHODS[var0.getSort()];
   }

   public static void mergeAnnotations(ClassNode var0, ClassNode var1) {
      var1.visibleAnnotations = mergeAnnotations(var0.visibleAnnotations, var1.visibleAnnotations, "class", var0.name);
      var1.invisibleAnnotations = mergeAnnotations(var0.invisibleAnnotations, var1.invisibleAnnotations, "class", var0.name);
   }

   public static void mergeAnnotations(MethodNode var0, MethodNode var1) {
      var1.visibleAnnotations = mergeAnnotations(var0.visibleAnnotations, var1.visibleAnnotations, "method", var0.name);
      var1.invisibleAnnotations = mergeAnnotations(var0.invisibleAnnotations, var1.invisibleAnnotations, "method", var0.name);
   }

   public static void mergeAnnotations(FieldNode var0, FieldNode var1) {
      var1.visibleAnnotations = mergeAnnotations(var0.visibleAnnotations, var1.visibleAnnotations, "field", var0.name);
      var1.invisibleAnnotations = mergeAnnotations(var0.invisibleAnnotations, var1.invisibleAnnotations, "field", var0.name);
   }

   private static List mergeAnnotations(List var0, List var1, String var2, String var3) {
      try {
         if (var0 == null) {
            return (List)var1;
         } else {
            if (var1 == null) {
               var1 = new ArrayList();
            }

            Iterator var4 = var0.iterator();

            while(true) {
               AnnotationNode var5;
               do {
                  if (!var4.hasNext()) {
                     return (List)var1;
                  }

                  var5 = (AnnotationNode)var4.next();
               } while(!isMergeableAnnotation(var5));

               Iterator var6 = ((List)var1).iterator();

               while(var6.hasNext()) {
                  if (((AnnotationNode)var6.next()).desc.equals(var5.desc)) {
                     var6.remove();
                     break;
                  }
               }

               ((List)var1).add(var5);
            }
         }
      } catch (Exception var7) {
         logger.warn("Exception encountered whilst merging annotations for {} {}", new Object[]{var2, var3});
         return (List)var1;
      }
   }

   private static boolean isMergeableAnnotation(AnnotationNode var0) {
      return var0.desc.startsWith("L" + Constants.MIXIN_PACKAGE_REF) ? mergeableAnnotationPattern.matcher(var0.desc).matches() : true;
   }

   private static Pattern getMergeableAnnotationPattern() {
      StringBuilder var0 = new StringBuilder("^L(");

      for(int var1 = 0; var1 < MERGEABLE_MIXIN_ANNOTATIONS.length; ++var1) {
         if (var1 > 0) {
            var0.append('|');
         }

         var0.append(MERGEABLE_MIXIN_ANNOTATIONS[var1].getName().replace('.', '/'));
      }

      return Pattern.compile(var0.append(");$").toString());
   }

   public static void compareBridgeMethods(MethodNode var0, MethodNode var1) {
      ListIterator var2 = var0.instructions.iterator();
      ListIterator var3 = var1.instructions.iterator();

      int var4;
      for(var4 = 0; var2.hasNext() && var3.hasNext(); ++var4) {
         AbstractInsnNode var5 = (AbstractInsnNode)var2.next();
         AbstractInsnNode var6 = (AbstractInsnNode)var3.next();
         if (!(var5 instanceof LabelNode)) {
            if (var5 instanceof MethodInsnNode) {
               MethodInsnNode var7 = (MethodInsnNode)var5;
               MethodInsnNode var8 = (MethodInsnNode)var6;
               if (!var7.name.equals(var8.name)) {
                  throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_NAME, var0.name, var0.desc, var4, var5, var6);
               }

               if (!var7.desc.equals(var8.desc)) {
                  throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INVOKE_DESC, var0.name, var0.desc, var4, var5, var6);
               }
            } else {
               if (var5.getOpcode() != var6.getOpcode()) {
                  throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_INSN, var0.name, var0.desc, var4, var5, var6);
               }

               if (var5 instanceof VarInsnNode) {
                  VarInsnNode var9 = (VarInsnNode)var5;
                  VarInsnNode var11 = (VarInsnNode)var6;
                  if (var9.var != var11.var) {
                     throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LOAD, var0.name, var0.desc, var4, var5, var6);
                  }
               } else if (var5 instanceof TypeInsnNode) {
                  TypeInsnNode var10 = (TypeInsnNode)var5;
                  TypeInsnNode var12 = (TypeInsnNode)var6;
                  if (var10.getOpcode() == 192 && !var10.desc.equals(var12.desc)) {
                     throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_CAST, var0.name, var0.desc, var4, var5, var6);
                  }
               }
            }
         }
      }

      if (var2.hasNext() || var3.hasNext()) {
         throw new SyntheticBridgeException(SyntheticBridgeException.Problem.BAD_LENGTH, var0.name, var0.desc, var4, (AbstractInsnNode)null, (AbstractInsnNode)null);
      }
   }

   public static enum Visibility {
      PRIVATE(2),
      PROTECTED(4),
      PACKAGE(0),
      PUBLIC(1);

      static final int MASK = 7;
      final int access;
      private static final Bytecode.Visibility[] $VALUES = new Bytecode.Visibility[]{PRIVATE, PROTECTED, PACKAGE, PUBLIC};

      private Visibility(int var3) {
         this.access = var3;
      }
   }
}
