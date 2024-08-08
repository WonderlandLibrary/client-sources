package org.spongepowered.asm.mixin.injection.code;

import com.google.common.base.Strings;
import java.util.LinkedList;
import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.AnnotationNode;
import org.spongepowered.asm.lib.tree.InsnList;
import org.spongepowered.asm.lib.tree.MethodNode;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.InjectionPoint;
import org.spongepowered.asm.mixin.injection.Slice;
import org.spongepowered.asm.mixin.injection.throwables.InjectionError;
import org.spongepowered.asm.mixin.injection.throwables.InvalidSliceException;
import org.spongepowered.asm.util.Annotations;
import org.spongepowered.asm.util.Bytecode;

public final class MethodSlice {
   private final ISliceContext owner;
   private final String id;
   private final InjectionPoint from;
   private final InjectionPoint to;
   private final String name;

   private MethodSlice(ISliceContext var1, String var2, InjectionPoint var3, InjectionPoint var4) {
      if (var3 == null && var4 == null) {
         throw new InvalidSliceException(var1, String.format("%s is redundant. No 'from' or 'to' value specified", this));
      } else {
         this.owner = var1;
         this.id = Strings.nullToEmpty(var2);
         this.from = var3;
         this.to = var4;
         this.name = getSliceName(var2);
      }
   }

   public String getId() {
      return this.id;
   }

   public ReadOnlyInsnList getSlice(MethodNode var1) {
      int var2 = var1.instructions.size() - 1;
      int var3 = this.find(var1, this.from, 0, this.name + "(from)");
      int var4 = this.find(var1, this.to, var2, this.name + "(to)");
      if (var3 > var4) {
         throw new InvalidSliceException(this.owner, String.format("%s is negative size. Range(%d -> %d)", this.describe(), var3, var4));
      } else if (var3 >= 0 && var4 >= 0 && var3 <= var2 && var4 <= var2) {
         return (ReadOnlyInsnList)(var3 == 0 && var4 == var2 ? new ReadOnlyInsnList(var1.instructions) : new MethodSlice.InsnListSlice(var1.instructions, var3, var4));
      } else {
         throw new InjectionError("Unexpected critical error in " + this + ": out of bounds start=" + var3 + " end=" + var4 + " lim=" + var2);
      }
   }

   private int find(MethodNode var1, InjectionPoint var2, int var3, String var4) {
      if (var2 == null) {
         return var3;
      } else {
         LinkedList var5 = new LinkedList();
         ReadOnlyInsnList var6 = new ReadOnlyInsnList(var1.instructions);
         boolean var7 = var2.find(var1.desc, var6, var5);
         InjectionPoint.Selector var8 = var2.getSelector();
         if (var5.size() != 1 && var8 == InjectionPoint.Selector.ONE) {
            throw new InvalidSliceException(this.owner, String.format("%s requires 1 result but found %d", this.describe(var4), var5.size()));
         } else {
            return !var7 ? var3 : var1.instructions.indexOf(var8 == InjectionPoint.Selector.FIRST ? (AbstractInsnNode)var5.getFirst() : (AbstractInsnNode)var5.getLast());
         }
      }
   }

   public String toString() {
      return this.describe();
   }

   private String describe() {
      return this.describe(this.name);
   }

   private String describe(String var1) {
      return describeSlice(var1, this.owner);
   }

   private static String describeSlice(String var0, ISliceContext var1) {
      String var2 = Bytecode.getSimpleName(var1.getAnnotation());
      MethodNode var3 = var1.getMethod();
      return String.format("%s->%s(%s)::%s%s", var1.getContext(), var2, var0, var3.name, var3.desc);
   }

   private static String getSliceName(String var0) {
      return String.format("@Slice[%s]", Strings.nullToEmpty(var0));
   }

   public static MethodSlice parse(ISliceContext var0, Slice var1) {
      String var2 = var1.id();
      At var3 = var1.from();
      At var4 = var1.to();
      InjectionPoint var5 = var3 != null ? InjectionPoint.parse(var0, (At)var3) : null;
      InjectionPoint var6 = var4 != null ? InjectionPoint.parse(var0, (At)var4) : null;
      return new MethodSlice(var0, var2, var5, var6);
   }

   public static MethodSlice parse(ISliceContext var0, AnnotationNode var1) {
      String var2 = (String)Annotations.getValue(var1, "id");
      AnnotationNode var3 = (AnnotationNode)Annotations.getValue(var1, "from");
      AnnotationNode var4 = (AnnotationNode)Annotations.getValue(var1, "to");
      InjectionPoint var5 = var3 != null ? InjectionPoint.parse(var0, (AnnotationNode)var3) : null;
      InjectionPoint var6 = var4 != null ? InjectionPoint.parse(var0, (AnnotationNode)var4) : null;
      return new MethodSlice(var0, var2, var5, var6);
   }

   static final class InsnListSlice extends ReadOnlyInsnList {
      private final int start;
      private final int end;

      protected InsnListSlice(InsnList var1, int var2, int var3) {
         super(var1);
         this.start = var2;
         this.end = var3;
      }

      public ListIterator iterator() {
         return this.iterator(0);
      }

      public ListIterator iterator(int var1) {
         return new MethodSlice.InsnListSlice.SliceIterator(super.iterator(this.start + var1), this.start, this.end, this.start + var1);
      }

      public AbstractInsnNode[] toArray() {
         AbstractInsnNode[] var1 = super.toArray();
         AbstractInsnNode[] var2 = new AbstractInsnNode[this.size()];
         System.arraycopy(var1, this.start, var2, 0, var2.length);
         return var2;
      }

      public int size() {
         return this.end - this.start + 1;
      }

      public AbstractInsnNode getFirst() {
         return super.get(this.start);
      }

      public AbstractInsnNode getLast() {
         return super.get(this.end);
      }

      public AbstractInsnNode get(int var1) {
         return super.get(this.start + var1);
      }

      public boolean contains(AbstractInsnNode var1) {
         AbstractInsnNode[] var2 = this.toArray();
         int var3 = var2.length;

         for(int var4 = 0; var4 < var3; ++var4) {
            AbstractInsnNode var5 = var2[var4];
            if (var5 == var1) {
               return true;
            }
         }

         return false;
      }

      public int indexOf(AbstractInsnNode var1) {
         int var2 = super.indexOf(var1);
         return var2 >= this.start && var2 <= this.end ? var2 - this.start : -1;
      }

      public int realIndexOf(AbstractInsnNode var1) {
         return super.indexOf(var1);
      }

      static class SliceIterator implements ListIterator {
         private final ListIterator iter;
         private int start;
         private int end;
         private int index;

         public SliceIterator(ListIterator var1, int var2, int var3, int var4) {
            this.iter = var1;
            this.start = var2;
            this.end = var3;
            this.index = var4;
         }

         public boolean hasNext() {
            return this.index <= this.end && this.iter.hasNext();
         }

         public AbstractInsnNode next() {
            if (this.index > this.end) {
               throw new NoSuchElementException();
            } else {
               ++this.index;
               return (AbstractInsnNode)this.iter.next();
            }
         }

         public boolean hasPrevious() {
            return this.index > this.start;
         }

         public AbstractInsnNode previous() {
            if (this.index <= this.start) {
               throw new NoSuchElementException();
            } else {
               --this.index;
               return (AbstractInsnNode)this.iter.previous();
            }
         }

         public int nextIndex() {
            return this.index - this.start;
         }

         public int previousIndex() {
            return this.index - this.start - 1;
         }

         public void remove() {
            throw new UnsupportedOperationException("Cannot remove insn from slice");
         }

         public void set(AbstractInsnNode var1) {
            throw new UnsupportedOperationException("Cannot set insn using slice");
         }

         public void add(AbstractInsnNode var1) {
            throw new UnsupportedOperationException("Cannot add insn using slice");
         }

         public void add(Object var1) {
            this.add((AbstractInsnNode)var1);
         }

         public void set(Object var1) {
            this.set((AbstractInsnNode)var1);
         }

         public Object previous() {
            return this.previous();
         }

         public Object next() {
            return this.next();
         }
      }
   }
}
