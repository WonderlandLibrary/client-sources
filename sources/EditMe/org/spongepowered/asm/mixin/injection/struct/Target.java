package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import org.spongepowered.asm.lib.Label;
import org.spongepowered.asm.lib.Type;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.ClassNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.LabelNode;
import org.spongepowered.asm.lib.tree.LocalVariableNode;
import org.spongepowered.asm.lib.tree.MethodInsnNode;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.lib.tree.TypeInsnNode;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.transformer.ClassInfo;
import org.spongepowered.asm.util.Bytecode;

public class Target implements Comparable, Iterable {
   public final ClassNode classNode;
   public final MethodNode method;
   public final InsnList insns;
   public final boolean isStatic;
   public final boolean isCtor;
   public final Type[] arguments;
   public final Type returnType;
   private final int maxStack;
   private final int maxLocals;
   private final InjectionNodes injectionNodes = new InjectionNodes();
   private String callbackInfoClass;
   private String callbackDescriptor;
   private int[] argIndices;
   private List argMapVars;
   private LabelNode start;
   private LabelNode end;

   public Target(ClassNode var1, MethodNode var2) {
      this.classNode = var1;
      this.method = var2;
      this.insns = var2.instructions;
      this.isStatic = Bytecode.methodIsStatic(var2);
      this.isCtor = var2.name.equals("<init>");
      this.arguments = Type.getArgumentTypes(var2.desc);
      this.returnType = Type.getReturnType(var2.desc);
      this.maxStack = var2.maxStack;
      this.maxLocals = var2.maxLocals;
   }

   public InjectionNodes.InjectionNode addInjectionNode(AbstractInsnNode var1) {
      return this.injectionNodes.add(var1);
   }

   public InjectionNodes.InjectionNode getInjectionNode(AbstractInsnNode var1) {
      return this.injectionNodes.get(var1);
   }

   public int getMaxLocals() {
      return this.maxLocals;
   }

   public int getMaxStack() {
      return this.maxStack;
   }

   public int getCurrentMaxLocals() {
      return this.method.maxLocals;
   }

   public int getCurrentMaxStack() {
      return this.method.maxStack;
   }

   public int allocateLocal() {
      return this.allocateLocals(1);
   }

   public int allocateLocals(int var1) {
      int var2 = this.method.maxLocals;
      MethodNode var10000 = this.method;
      var10000.maxLocals += var1;
      return var2;
   }

   public void addToLocals(int var1) {
      this.setMaxLocals(this.maxLocals + var1);
   }

   public void setMaxLocals(int var1) {
      if (var1 > this.method.maxLocals) {
         this.method.maxLocals = var1;
      }

   }

   public void addToStack(int var1) {
      this.setMaxStack(this.maxStack + var1);
   }

   public void setMaxStack(int var1) {
      if (var1 > this.method.maxStack) {
         this.method.maxStack = var1;
      }

   }

   public int[] generateArgMap(Type[] var1, int var2) {
      if (this.argMapVars == null) {
         this.argMapVars = new ArrayList();
      }

      int[] var3 = new int[var1.length];
      int var4 = var2;

      for(int var5 = 0; var4 < var1.length; ++var4) {
         int var6 = var1[var4].getSize();
         var3[var4] = this.allocateArgMapLocal(var5, var6);
         var5 += var6;
      }

      return var3;
   }

   private int allocateArgMapLocal(int var1, int var2) {
      int var3;
      int var4;
      if (var1 < this.argMapVars.size()) {
         var3 = (Integer)this.argMapVars.get(var1);
         if (var2 > 1 && var1 + var2 > this.argMapVars.size()) {
            var4 = this.allocateLocals(1);
            if (var4 == var3 + 1) {
               this.argMapVars.add(var4);
               return var3;
            } else {
               this.argMapVars.set(var1, var4);
               this.argMapVars.add(this.allocateLocals(1));
               return var4;
            }
         } else {
            return var3;
         }
      } else {
         var3 = this.allocateLocals(var2);

         for(var4 = 0; var4 < var2; ++var4) {
            this.argMapVars.add(var3 + var4);
         }

         return var3;
      }
   }

   public int[] getArgIndices() {
      if (this.argIndices == null) {
         this.argIndices = this.calcArgIndices(this.isStatic ? 0 : 1);
      }

      return this.argIndices;
   }

   private int[] calcArgIndices(int var1) {
      int[] var2 = new int[this.arguments.length];

      for(int var3 = 0; var3 < this.arguments.length; ++var3) {
         var2[var3] = var1;
         var1 += this.arguments[var3].getSize();
      }

      return var2;
   }

   public String getCallbackInfoClass() {
      if (this.callbackInfoClass == null) {
         this.callbackInfoClass = CallbackInfo.getCallInfoClassName(this.returnType);
      }

      return this.callbackInfoClass;
   }

   public String getSimpleCallbackDescriptor() {
      return String.format("(L%s;)V", this.getCallbackInfoClass());
   }

   public String getCallbackDescriptor(Type[] var1, Type[] var2) {
      return this.getCallbackDescriptor(false, var1, var2, 0, 32767);
   }

   public String getCallbackDescriptor(boolean var1, Type[] var2, Type[] var3, int var4, int var5) {
      if (this.callbackDescriptor == null) {
         this.callbackDescriptor = String.format("(%sL%s;)V", this.method.desc.substring(1, this.method.desc.indexOf(41)), this.getCallbackInfoClass());
      }

      if (var1 && var2 != null) {
         StringBuilder var6 = new StringBuilder(this.callbackDescriptor.substring(0, this.callbackDescriptor.indexOf(41)));

         for(int var7 = var4; var7 < var2.length && var5 > 0; ++var7) {
            if (var2[var7] != null) {
               var6.append(var2[var7].getDescriptor());
               --var5;
            }
         }

         return var6.append(")V").toString();
      } else {
         return this.callbackDescriptor;
      }
   }

   public String toString() {
      return String.format("%s::%s%s", this.classNode.name, this.method.name, this.method.desc);
   }

   public int compareTo(Target var1) {
      return var1 == null ? Integer.MAX_VALUE : this.toString().compareTo(var1.toString());
   }

   public int indexOf(InjectionNodes.InjectionNode var1) {
      return this.insns.indexOf(var1.getCurrentTarget());
   }

   public int indexOf(AbstractInsnNode var1) {
      return this.insns.indexOf(var1);
   }

   public AbstractInsnNode get(int var1) {
      return this.insns.get(var1);
   }

   public Iterator iterator() {
      return this.insns.iterator();
   }

   public MethodInsnNode findInitNodeFor(TypeInsnNode var1) {
      int var2 = this.indexOf((AbstractInsnNode)var1);
      ListIterator var3 = this.insns.iterator(var2);

      while(var3.hasNext()) {
         AbstractInsnNode var4 = (AbstractInsnNode)var3.next();
         if (var4 instanceof MethodInsnNode && var4.getOpcode() == 183) {
            MethodInsnNode var5 = (MethodInsnNode)var4;
            if ("<init>".equals(var5.name) && var5.owner.equals(var1.desc)) {
               return var5;
            }
         }
      }

      return null;
   }

   public MethodInsnNode findSuperInitNode() {
      return !this.isCtor ? null : Bytecode.findSuperInit(this.method, ClassInfo.forName(this.classNode.name).getSuperName());
   }

   public void insertBefore(InjectionNodes.InjectionNode var1, InsnList var2) {
      this.insns.insertBefore(var1.getCurrentTarget(), var2);
   }

   public void insertBefore(AbstractInsnNode var1, InsnList var2) {
      this.insns.insertBefore(var1, var2);
   }

   public void replaceNode(AbstractInsnNode var1, AbstractInsnNode var2) {
      this.insns.insertBefore(var1, var2);
      this.insns.remove(var1);
      this.injectionNodes.replace(var1, var2);
   }

   public void replaceNode(AbstractInsnNode var1, AbstractInsnNode var2, InsnList var3) {
      this.insns.insertBefore(var1, var3);
      this.insns.remove(var1);
      this.injectionNodes.replace(var1, var2);
   }

   public void wrapNode(AbstractInsnNode var1, AbstractInsnNode var2, InsnList var3, InsnList var4) {
      this.insns.insertBefore(var1, var3);
      this.insns.insert(var1, var4);
      this.injectionNodes.replace(var1, var2);
   }

   public void replaceNode(AbstractInsnNode var1, InsnList var2) {
      this.insns.insertBefore(var1, var2);
      this.removeNode(var1);
   }

   public void removeNode(AbstractInsnNode var1) {
      this.insns.remove(var1);
      this.injectionNodes.remove(var1);
   }

   public void addLocalVariable(int var1, String var2, String var3) {
      if (this.start == null) {
         this.start = new LabelNode(new Label());
         this.end = new LabelNode(new Label());
         this.insns.insert((AbstractInsnNode)this.start);
         this.insns.add((AbstractInsnNode)this.end);
      }

      this.addLocalVariable(var1, var2, var3, this.start, this.end);
   }

   private void addLocalVariable(int var1, String var2, String var3, LabelNode var4, LabelNode var5) {
      if (this.method.localVariables == null) {
         this.method.localVariables = new ArrayList();
      }

      this.method.localVariables.add(new LocalVariableNode(var2, var3, (String)null, var4, var5, var1));
   }

   public int compareTo(Object var1) {
      return this.compareTo((Target)var1);
   }
}
