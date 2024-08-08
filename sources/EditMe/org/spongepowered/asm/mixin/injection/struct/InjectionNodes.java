package org.spongepowered.asm.mixin.injection.struct;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.util.Bytecode;

public class InjectionNodes extends ArrayList {
   private static final long serialVersionUID = 1L;

   public InjectionNodes.InjectionNode add(AbstractInsnNode var1) {
      InjectionNodes.InjectionNode var2 = this.get(var1);
      if (var2 == null) {
         var2 = new InjectionNodes.InjectionNode(var1);
         this.add(var2);
      }

      return var2;
   }

   public InjectionNodes.InjectionNode get(AbstractInsnNode var1) {
      Iterator var2 = this.iterator();

      InjectionNodes.InjectionNode var3;
      do {
         if (!var2.hasNext()) {
            return null;
         }

         var3 = (InjectionNodes.InjectionNode)var2.next();
      } while(!var3.matches(var1));

      return var3;
   }

   public boolean contains(AbstractInsnNode var1) {
      return this.get(var1) != null;
   }

   public void replace(AbstractInsnNode var1, AbstractInsnNode var2) {
      InjectionNodes.InjectionNode var3 = this.get(var1);
      if (var3 != null) {
         var3.replace(var2);
      }

   }

   public void remove(AbstractInsnNode var1) {
      InjectionNodes.InjectionNode var2 = this.get(var1);
      if (var2 != null) {
         var2.remove();
      }

   }

   public static class InjectionNode implements Comparable {
      private static int nextId = 0;
      private final int id;
      private final AbstractInsnNode originalTarget;
      private AbstractInsnNode currentTarget;
      private Map decorations;

      public InjectionNode(AbstractInsnNode var1) {
         this.currentTarget = this.originalTarget = var1;
         this.id = nextId++;
      }

      public int getId() {
         return this.id;
      }

      public AbstractInsnNode getOriginalTarget() {
         return this.originalTarget;
      }

      public AbstractInsnNode getCurrentTarget() {
         return this.currentTarget;
      }

      public InjectionNodes.InjectionNode replace(AbstractInsnNode var1) {
         this.currentTarget = var1;
         return this;
      }

      public InjectionNodes.InjectionNode remove() {
         this.currentTarget = null;
         return this;
      }

      public boolean matches(AbstractInsnNode var1) {
         return this.originalTarget == var1 || this.currentTarget == var1;
      }

      public boolean isReplaced() {
         return this.originalTarget != this.currentTarget;
      }

      public boolean isRemoved() {
         return this.currentTarget == null;
      }

      public InjectionNodes.InjectionNode decorate(String var1, Object var2) {
         if (this.decorations == null) {
            this.decorations = new HashMap();
         }

         this.decorations.put(var1, var2);
         return this;
      }

      public boolean hasDecoration(String var1) {
         return this.decorations != null && this.decorations.get(var1) != null;
      }

      public Object getDecoration(String var1) {
         return this.decorations == null ? null : this.decorations.get(var1);
      }

      public int compareTo(InjectionNodes.InjectionNode var1) {
         return var1 == null ? Integer.MAX_VALUE : this.hashCode() - var1.hashCode();
      }

      public String toString() {
         return String.format("InjectionNode[%s]", Bytecode.describeNode(this.currentTarget).replaceAll("\\s+", " "));
      }

      public int compareTo(Object var1) {
         return this.compareTo((InjectionNodes.InjectionNode)var1);
      }
   }
}
