package org.spongepowered.asm.lib.tree;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.spongepowered.asm.lib.AnnotationVisitor;
import org.spongepowered.asm.lib.Attribute;
import org.spongepowered.asm.lib.ClassVisitor;
import org.spongepowered.asm.lib.Handle;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.MethodVisitor;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.TypePath;

public class MethodNode extends MethodVisitor {
   public int access;
   public String name;
   public String desc;
   public String signature;
   public List exceptions;
   public List parameters;
   public List visibleAnnotations;
   public List invisibleAnnotations;
   public List visibleTypeAnnotations;
   public List invisibleTypeAnnotations;
   public List attrs;
   public Object annotationDefault;
   public List[] visibleParameterAnnotations;
   public List[] invisibleParameterAnnotations;
   public InsnList instructions;
   public List tryCatchBlocks;
   public int maxStack;
   public int maxLocals;
   public List localVariables;
   public List visibleLocalVariableAnnotations;
   public List invisibleLocalVariableAnnotations;
   private boolean visited;

   public MethodNode() {
      this(327680);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int var1) {
      super(var1);
      this.instructions = new InsnList();
   }

   public MethodNode(int var1, String var2, String var3, String var4, String[] var5) {
      this(327680, var1, var2, var3, var4, var5);
      if (this.getClass() != MethodNode.class) {
         throw new IllegalStateException();
      }
   }

   public MethodNode(int var1, int var2, String var3, String var4, String var5, String[] var6) {
      super(var1);
      this.access = var2;
      this.name = var3;
      this.desc = var4;
      this.signature = var5;
      this.exceptions = new ArrayList(var6 == null ? 0 : var6.length);
      boolean var7 = (var2 & 1024) != 0;
      if (!var7) {
         this.localVariables = new ArrayList(5);
      }

      this.tryCatchBlocks = new ArrayList();
      if (var6 != null) {
         this.exceptions.addAll(Arrays.asList(var6));
      }

      this.instructions = new InsnList();
   }

   public void visitParameter(String var1, int var2) {
      if (this.parameters == null) {
         this.parameters = new ArrayList(5);
      }

      this.parameters.add(new ParameterNode(var1, var2));
   }

   public AnnotationVisitor visitAnnotationDefault() {
      return new AnnotationNode(new ArrayList(this, 0) {
         final MethodNode this$0;

         {
            this.this$0 = var1;
         }

         public boolean add(Object var1) {
            this.this$0.annotationDefault = var1;
            return super.add(var1);
         }
      });
   }

   public AnnotationVisitor visitAnnotation(String var1, boolean var2) {
      AnnotationNode var3 = new AnnotationNode(var1);
      if (var2) {
         if (this.visibleAnnotations == null) {
            this.visibleAnnotations = new ArrayList(1);
         }

         this.visibleAnnotations.add(var3);
      } else {
         if (this.invisibleAnnotations == null) {
            this.invisibleAnnotations = new ArrayList(1);
         }

         this.invisibleAnnotations.add(var3);
      }

      return var3;
   }

   public AnnotationVisitor visitTypeAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      TypeAnnotationNode var5 = new TypeAnnotationNode(var1, var2, var3);
      if (var4) {
         if (this.visibleTypeAnnotations == null) {
            this.visibleTypeAnnotations = new ArrayList(1);
         }

         this.visibleTypeAnnotations.add(var5);
      } else {
         if (this.invisibleTypeAnnotations == null) {
            this.invisibleTypeAnnotations = new ArrayList(1);
         }

         this.invisibleTypeAnnotations.add(var5);
      }

      return var5;
   }

   public AnnotationVisitor visitParameterAnnotation(int var1, String var2, boolean var3) {
      AnnotationNode var4 = new AnnotationNode(var2);
      int var5;
      if (var3) {
         if (this.visibleParameterAnnotations == null) {
            var5 = Type.getArgumentTypes(this.desc).length;
            this.visibleParameterAnnotations = (List[])(new List[var5]);
         }

         if (this.visibleParameterAnnotations[var1] == null) {
            this.visibleParameterAnnotations[var1] = new ArrayList(1);
         }

         this.visibleParameterAnnotations[var1].add(var4);
      } else {
         if (this.invisibleParameterAnnotations == null) {
            var5 = Type.getArgumentTypes(this.desc).length;
            this.invisibleParameterAnnotations = (List[])(new List[var5]);
         }

         if (this.invisibleParameterAnnotations[var1] == null) {
            this.invisibleParameterAnnotations[var1] = new ArrayList(1);
         }

         this.invisibleParameterAnnotations[var1].add(var4);
      }

      return var4;
   }

   public void visitAttribute(Attribute var1) {
      if (this.attrs == null) {
         this.attrs = new ArrayList(1);
      }

      this.attrs.add(var1);
   }

   public void visitCode() {
   }

   public void visitFrame(int var1, int var2, Object[] var3, int var4, Object[] var5) {
      this.instructions.add((AbstractInsnNode)(new FrameNode(var1, var2, var3 == null ? null : this.getLabelNodes(var3), var4, var5 == null ? null : this.getLabelNodes(var5))));
   }

   public void visitInsn(int var1) {
      this.instructions.add((AbstractInsnNode)(new InsnNode(var1)));
   }

   public void visitIntInsn(int var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new IntInsnNode(var1, var2)));
   }

   public void visitVarInsn(int var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new VarInsnNode(var1, var2)));
   }

   public void visitTypeInsn(int var1, String var2) {
      this.instructions.add((AbstractInsnNode)(new TypeInsnNode(var1, var2)));
   }

   public void visitFieldInsn(int var1, String var2, String var3, String var4) {
      this.instructions.add((AbstractInsnNode)(new FieldInsnNode(var1, var2, var3, var4)));
   }

   /** @deprecated */
   @Deprecated
   public void visitMethodInsn(int var1, String var2, String var3, String var4) {
      if (this.api >= 327680) {
         super.visitMethodInsn(var1, var2, var3, var4);
      } else {
         this.instructions.add((AbstractInsnNode)(new MethodInsnNode(var1, var2, var3, var4)));
      }
   }

   public void visitMethodInsn(int var1, String var2, String var3, String var4, boolean var5) {
      if (this.api < 327680) {
         super.visitMethodInsn(var1, var2, var3, var4, var5);
      } else {
         this.instructions.add((AbstractInsnNode)(new MethodInsnNode(var1, var2, var3, var4, var5)));
      }
   }

   public void visitInvokeDynamicInsn(String var1, String var2, Handle var3, Object... var4) {
      this.instructions.add((AbstractInsnNode)(new InvokeDynamicInsnNode(var1, var2, var3, var4)));
   }

   public void visitJumpInsn(int var1, Label var2) {
      this.instructions.add((AbstractInsnNode)(new JumpInsnNode(var1, this.getLabelNode(var2))));
   }

   public void visitLabel(Label var1) {
      this.instructions.add((AbstractInsnNode)this.getLabelNode(var1));
   }

   public void visitLdcInsn(Object var1) {
      this.instructions.add((AbstractInsnNode)(new LdcInsnNode(var1)));
   }

   public void visitIincInsn(int var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new IincInsnNode(var1, var2)));
   }

   public void visitTableSwitchInsn(int var1, int var2, Label var3, Label... var4) {
      this.instructions.add((AbstractInsnNode)(new TableSwitchInsnNode(var1, var2, this.getLabelNode(var3), this.getLabelNodes(var4))));
   }

   public void visitLookupSwitchInsn(Label var1, int[] var2, Label[] var3) {
      this.instructions.add((AbstractInsnNode)(new LookupSwitchInsnNode(this.getLabelNode(var1), var2, this.getLabelNodes(var3))));
   }

   public void visitMultiANewArrayInsn(String var1, int var2) {
      this.instructions.add((AbstractInsnNode)(new MultiANewArrayInsnNode(var1, var2)));
   }

   public AnnotationVisitor visitInsnAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      AbstractInsnNode var5;
      for(var5 = this.instructions.getLast(); var5.getOpcode() == -1; var5 = var5.getPrevious()) {
      }

      TypeAnnotationNode var6 = new TypeAnnotationNode(var1, var2, var3);
      if (var4) {
         if (var5.visibleTypeAnnotations == null) {
            var5.visibleTypeAnnotations = new ArrayList(1);
         }

         var5.visibleTypeAnnotations.add(var6);
      } else {
         if (var5.invisibleTypeAnnotations == null) {
            var5.invisibleTypeAnnotations = new ArrayList(1);
         }

         var5.invisibleTypeAnnotations.add(var6);
      }

      return var6;
   }

   public void visitTryCatchBlock(Label var1, Label var2, Label var3, String var4) {
      this.tryCatchBlocks.add(new TryCatchBlockNode(this.getLabelNode(var1), this.getLabelNode(var2), this.getLabelNode(var3), var4));
   }

   public AnnotationVisitor visitTryCatchAnnotation(int var1, TypePath var2, String var3, boolean var4) {
      TryCatchBlockNode var5 = (TryCatchBlockNode)this.tryCatchBlocks.get((var1 & 16776960) >> 8);
      TypeAnnotationNode var6 = new TypeAnnotationNode(var1, var2, var3);
      if (var4) {
         if (var5.visibleTypeAnnotations == null) {
            var5.visibleTypeAnnotations = new ArrayList(1);
         }

         var5.visibleTypeAnnotations.add(var6);
      } else {
         if (var5.invisibleTypeAnnotations == null) {
            var5.invisibleTypeAnnotations = new ArrayList(1);
         }

         var5.invisibleTypeAnnotations.add(var6);
      }

      return var6;
   }

   public void visitLocalVariable(String var1, String var2, String var3, Label var4, Label var5, int var6) {
      this.localVariables.add(new LocalVariableNode(var1, var2, var3, this.getLabelNode(var4), this.getLabelNode(var5), var6));
   }

   public AnnotationVisitor visitLocalVariableAnnotation(int var1, TypePath var2, Label[] var3, Label[] var4, int[] var5, String var6, boolean var7) {
      LocalVariableAnnotationNode var8 = new LocalVariableAnnotationNode(var1, var2, this.getLabelNodes(var3), this.getLabelNodes(var4), var5, var6);
      if (var7) {
         if (this.visibleLocalVariableAnnotations == null) {
            this.visibleLocalVariableAnnotations = new ArrayList(1);
         }

         this.visibleLocalVariableAnnotations.add(var8);
      } else {
         if (this.invisibleLocalVariableAnnotations == null) {
            this.invisibleLocalVariableAnnotations = new ArrayList(1);
         }

         this.invisibleLocalVariableAnnotations.add(var8);
      }

      return var8;
   }

   public void visitLineNumber(int var1, Label var2) {
      this.instructions.add((AbstractInsnNode)(new LineNumberNode(var1, this.getLabelNode(var2))));
   }

   public void visitMaxs(int var1, int var2) {
      this.maxStack = var1;
      this.maxLocals = var2;
   }

   public void visitEnd() {
   }

   protected LabelNode getLabelNode(Label var1) {
      if (!(var1.info instanceof LabelNode)) {
         var1.info = new LabelNode();
      }

      return (LabelNode)var1.info;
   }

   private LabelNode[] getLabelNodes(Label[] var1) {
      LabelNode[] var2 = new LabelNode[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         var2[var3] = this.getLabelNode(var1[var3]);
      }

      return var2;
   }

   private Object[] getLabelNodes(Object[] var1) {
      Object[] var2 = new Object[var1.length];

      for(int var3 = 0; var3 < var1.length; ++var3) {
         Object var4 = var1[var3];
         if (var4 instanceof Label) {
            var4 = this.getLabelNode((Label)var4);
         }

         var2[var3] = var4;
      }

      return var2;
   }

   public void check(int var1) {
      if (var1 == 262144) {
         if (this.visibleTypeAnnotations != null && this.visibleTypeAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         if (this.invisibleTypeAnnotations != null && this.invisibleTypeAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         int var2 = this.tryCatchBlocks == null ? 0 : this.tryCatchBlocks.size();

         int var3;
         for(var3 = 0; var3 < var2; ++var3) {
            TryCatchBlockNode var4 = (TryCatchBlockNode)this.tryCatchBlocks.get(var3);
            if (var4.visibleTypeAnnotations != null && var4.visibleTypeAnnotations.size() > 0) {
               throw new RuntimeException();
            }

            if (var4.invisibleTypeAnnotations != null && var4.invisibleTypeAnnotations.size() > 0) {
               throw new RuntimeException();
            }
         }

         for(var3 = 0; var3 < this.instructions.size(); ++var3) {
            AbstractInsnNode var6 = this.instructions.get(var3);
            if (var6.visibleTypeAnnotations != null && var6.visibleTypeAnnotations.size() > 0) {
               throw new RuntimeException();
            }

            if (var6.invisibleTypeAnnotations != null && var6.invisibleTypeAnnotations.size() > 0) {
               throw new RuntimeException();
            }

            if (var6 instanceof MethodInsnNode) {
               boolean var5 = ((MethodInsnNode)var6).itf;
               if (var5 != (var6.opcode == 185)) {
                  throw new RuntimeException();
               }
            }
         }

         if (this.visibleLocalVariableAnnotations != null && this.visibleLocalVariableAnnotations.size() > 0) {
            throw new RuntimeException();
         }

         if (this.invisibleLocalVariableAnnotations != null && this.invisibleLocalVariableAnnotations.size() > 0) {
            throw new RuntimeException();
         }
      }

   }

   public void accept(ClassVisitor var1) {
      String[] var2 = new String[this.exceptions.size()];
      this.exceptions.toArray(var2);
      MethodVisitor var3 = var1.visitMethod(this.access, this.name, this.desc, this.signature, var2);
      if (var3 != null) {
         this.accept(var3);
      }

   }

   public void accept(MethodVisitor var1) {
      int var2 = this.parameters == null ? 0 : this.parameters.size();

      int var3;
      for(var3 = 0; var3 < var2; ++var3) {
         ParameterNode var4 = (ParameterNode)this.parameters.get(var3);
         var1.visitParameter(var4.name, var4.access);
      }

      if (this.annotationDefault != null) {
         AnnotationVisitor var7 = var1.visitAnnotationDefault();
         AnnotationNode.accept(var7, (String)null, this.annotationDefault);
         if (var7 != null) {
            var7.visitEnd();
         }
      }

      var2 = this.visibleAnnotations == null ? 0 : this.visibleAnnotations.size();

      AnnotationNode var8;
      for(var3 = 0; var3 < var2; ++var3) {
         var8 = (AnnotationNode)this.visibleAnnotations.get(var3);
         var8.accept(var1.visitAnnotation(var8.desc, true));
      }

      var2 = this.invisibleAnnotations == null ? 0 : this.invisibleAnnotations.size();

      for(var3 = 0; var3 < var2; ++var3) {
         var8 = (AnnotationNode)this.invisibleAnnotations.get(var3);
         var8.accept(var1.visitAnnotation(var8.desc, false));
      }

      var2 = this.visibleTypeAnnotations == null ? 0 : this.visibleTypeAnnotations.size();

      TypeAnnotationNode var9;
      for(var3 = 0; var3 < var2; ++var3) {
         var9 = (TypeAnnotationNode)this.visibleTypeAnnotations.get(var3);
         var9.accept(var1.visitTypeAnnotation(var9.typeRef, var9.typePath, var9.desc, true));
      }

      var2 = this.invisibleTypeAnnotations == null ? 0 : this.invisibleTypeAnnotations.size();

      for(var3 = 0; var3 < var2; ++var3) {
         var9 = (TypeAnnotationNode)this.invisibleTypeAnnotations.get(var3);
         var9.accept(var1.visitTypeAnnotation(var9.typeRef, var9.typePath, var9.desc, false));
      }

      var2 = this.visibleParameterAnnotations == null ? 0 : this.visibleParameterAnnotations.length;

      int var5;
      AnnotationNode var6;
      List var10;
      for(var3 = 0; var3 < var2; ++var3) {
         var10 = this.visibleParameterAnnotations[var3];
         if (var10 != null) {
            for(var5 = 0; var5 < var10.size(); ++var5) {
               var6 = (AnnotationNode)var10.get(var5);
               var6.accept(var1.visitParameterAnnotation(var3, var6.desc, true));
            }
         }
      }

      var2 = this.invisibleParameterAnnotations == null ? 0 : this.invisibleParameterAnnotations.length;

      for(var3 = 0; var3 < var2; ++var3) {
         var10 = this.invisibleParameterAnnotations[var3];
         if (var10 != null) {
            for(var5 = 0; var5 < var10.size(); ++var5) {
               var6 = (AnnotationNode)var10.get(var5);
               var6.accept(var1.visitParameterAnnotation(var3, var6.desc, false));
            }
         }
      }

      if (this.visited) {
         this.instructions.resetLabels();
      }

      var2 = this.attrs == null ? 0 : this.attrs.size();

      for(var3 = 0; var3 < var2; ++var3) {
         var1.visitAttribute((Attribute)this.attrs.get(var3));
      }

      if (this.instructions.size() > 0) {
         var1.visitCode();
         var2 = this.tryCatchBlocks == null ? 0 : this.tryCatchBlocks.size();

         for(var3 = 0; var3 < var2; ++var3) {
            ((TryCatchBlockNode)this.tryCatchBlocks.get(var3)).updateIndex(var3);
            ((TryCatchBlockNode)this.tryCatchBlocks.get(var3)).accept(var1);
         }

         this.instructions.accept(var1);
         var2 = this.localVariables == null ? 0 : this.localVariables.size();

         for(var3 = 0; var3 < var2; ++var3) {
            ((LocalVariableNode)this.localVariables.get(var3)).accept(var1);
         }

         var2 = this.visibleLocalVariableAnnotations == null ? 0 : this.visibleLocalVariableAnnotations.size();

         for(var3 = 0; var3 < var2; ++var3) {
            ((LocalVariableAnnotationNode)this.visibleLocalVariableAnnotations.get(var3)).accept(var1, true);
         }

         var2 = this.invisibleLocalVariableAnnotations == null ? 0 : this.invisibleLocalVariableAnnotations.size();

         for(var3 = 0; var3 < var2; ++var3) {
            ((LocalVariableAnnotationNode)this.invisibleLocalVariableAnnotations.get(var3)).accept(var1, false);
         }

         var1.visitMaxs(this.maxStack, this.maxLocals);
         this.visited = true;
      }

      var1.visitEnd();
   }
}
