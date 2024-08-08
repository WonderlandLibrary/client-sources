package org.spongepowered.asm.lib.tree;

import java.util.ListIterator;
import java.util.NoSuchElementException;
import org.spongepowered.asm.lib.MethodVisitor;

public class InsnList {
   private int size;
   private AbstractInsnNode first;
   private AbstractInsnNode last;
   AbstractInsnNode[] cache;

   public int size() {
      return this.size;
   }

   public AbstractInsnNode getFirst() {
      return this.first;
   }

   public AbstractInsnNode getLast() {
      return this.last;
   }

   public AbstractInsnNode get(int var1) {
      if (var1 >= 0 && var1 < this.size) {
         if (this.cache == null) {
            this.cache = this.toArray();
         }

         return this.cache[var1];
      } else {
         throw new IndexOutOfBoundsException();
      }
   }

   public boolean contains(AbstractInsnNode var1) {
      AbstractInsnNode var2;
      for(var2 = this.first; var2 != null && var2 != var1; var2 = var2.next) {
      }

      return var2 != null;
   }

   public int indexOf(AbstractInsnNode var1) {
      if (this.cache == null) {
         this.cache = this.toArray();
      }

      return var1.index;
   }

   public void accept(MethodVisitor var1) {
      for(AbstractInsnNode var2 = this.first; var2 != null; var2 = var2.next) {
         var2.accept(var1);
      }

   }

   public ListIterator iterator() {
      return this.iterator(0);
   }

   public ListIterator iterator(int var1) {
      return new InsnList.InsnListIterator(this, var1);
   }

   public AbstractInsnNode[] toArray() {
      int var1 = 0;
      AbstractInsnNode var2 = this.first;

      AbstractInsnNode[] var3;
      for(var3 = new AbstractInsnNode[this.size]; var2 != null; var2 = var2.next) {
         var3[var1] = var2;
         var2.index = var1++;
      }

      return var3;
   }

   public void set(AbstractInsnNode var1, AbstractInsnNode var2) {
      AbstractInsnNode var3 = var1.next;
      var2.next = var3;
      if (var3 != null) {
         var3.prev = var2;
      } else {
         this.last = var2;
      }

      AbstractInsnNode var4 = var1.prev;
      var2.prev = var4;
      if (var4 != null) {
         var4.next = var2;
      } else {
         this.first = var2;
      }

      if (this.cache != null) {
         int var5 = var1.index;
         this.cache[var5] = var2;
         var2.index = var5;
      } else {
         var2.index = 0;
      }

      var1.index = -1;
      var1.prev = null;
      var1.next = null;
   }

   public void add(AbstractInsnNode var1) {
      ++this.size;
      if (this.last == null) {
         this.first = var1;
         this.last = var1;
      } else {
         this.last.next = var1;
         var1.prev = this.last;
      }

      this.last = var1;
      this.cache = null;
      var1.index = 0;
   }

   public void add(InsnList var1) {
      if (var1.size != 0) {
         this.size += var1.size;
         if (this.last == null) {
            this.first = var1.first;
            this.last = var1.last;
         } else {
            AbstractInsnNode var2 = var1.first;
            this.last.next = var2;
            var2.prev = this.last;
            this.last = var1.last;
         }

         this.cache = null;
         var1.removeAll(false);
      }
   }

   public void insert(AbstractInsnNode var1) {
      ++this.size;
      if (this.first == null) {
         this.first = var1;
         this.last = var1;
      } else {
         this.first.prev = var1;
         var1.next = this.first;
      }

      this.first = var1;
      this.cache = null;
      var1.index = 0;
   }

   public void insert(InsnList var1) {
      if (var1.size != 0) {
         this.size += var1.size;
         if (this.first == null) {
            this.first = var1.first;
            this.last = var1.last;
         } else {
            AbstractInsnNode var2 = var1.last;
            this.first.prev = var2;
            var2.next = this.first;
            this.first = var1.first;
         }

         this.cache = null;
         var1.removeAll(false);
      }
   }

   public void insert(AbstractInsnNode var1, AbstractInsnNode var2) {
      ++this.size;
      AbstractInsnNode var3 = var1.next;
      if (var3 == null) {
         this.last = var2;
      } else {
         var3.prev = var2;
      }

      var1.next = var2;
      var2.next = var3;
      var2.prev = var1;
      this.cache = null;
      var2.index = 0;
   }

   public void insert(AbstractInsnNode var1, InsnList var2) {
      if (var2.size != 0) {
         this.size += var2.size;
         AbstractInsnNode var3 = var2.first;
         AbstractInsnNode var4 = var2.last;
         AbstractInsnNode var5 = var1.next;
         if (var5 == null) {
            this.last = var4;
         } else {
            var5.prev = var4;
         }

         var1.next = var3;
         var4.next = var5;
         var3.prev = var1;
         this.cache = null;
         var2.removeAll(false);
      }
   }

   public void insertBefore(AbstractInsnNode var1, AbstractInsnNode var2) {
      ++this.size;
      AbstractInsnNode var3 = var1.prev;
      if (var3 == null) {
         this.first = var2;
      } else {
         var3.next = var2;
      }

      var1.prev = var2;
      var2.next = var1;
      var2.prev = var3;
      this.cache = null;
      var2.index = 0;
   }

   public void insertBefore(AbstractInsnNode var1, InsnList var2) {
      if (var2.size != 0) {
         this.size += var2.size;
         AbstractInsnNode var3 = var2.first;
         AbstractInsnNode var4 = var2.last;
         AbstractInsnNode var5 = var1.prev;
         if (var5 == null) {
            this.first = var3;
         } else {
            var5.next = var3;
         }

         var1.prev = var4;
         var4.next = var1;
         var3.prev = var5;
         this.cache = null;
         var2.removeAll(false);
      }
   }

   public void remove(AbstractInsnNode var1) {
      --this.size;
      AbstractInsnNode var2 = var1.next;
      AbstractInsnNode var3 = var1.prev;
      if (var2 == null) {
         if (var3 == null) {
            this.first = null;
            this.last = null;
         } else {
            var3.next = null;
            this.last = var3;
         }
      } else if (var3 == null) {
         this.first = var2;
         var2.prev = null;
      } else {
         var3.next = var2;
         var2.prev = var3;
      }

      this.cache = null;
      var1.index = -1;
      var1.prev = null;
      var1.next = null;
   }

   void removeAll(boolean var1) {
      AbstractInsnNode var3;
      if (var1) {
         for(AbstractInsnNode var2 = this.first; var2 != null; var2 = var3) {
            var3 = var2.next;
            var2.index = -1;
            var2.prev = null;
            var2.next = null;
         }
      }

      this.size = 0;
      this.first = null;
      this.last = null;
      this.cache = null;
   }

   public void clear() {
      this.removeAll(false);
   }

   public void resetLabels() {
      for(AbstractInsnNode var1 = this.first; var1 != null; var1 = var1.next) {
         if (var1 instanceof LabelNode) {
            ((LabelNode)var1).resetLabel();
         }
      }

   }

   private final class InsnListIterator implements ListIterator {
      AbstractInsnNode next;
      AbstractInsnNode prev;
      AbstractInsnNode remove;
      final InsnList this$0;

      InsnListIterator(InsnList var1, int var2) {
         this.this$0 = var1;
         if (var2 == var1.size()) {
            this.next = null;
            this.prev = var1.getLast();
         } else {
            this.next = var1.get(var2);
            this.prev = this.next.prev;
         }

      }

      public boolean hasNext() {
         return this.next != null;
      }

      public Object next() {
         if (this.next == null) {
            throw new NoSuchElementException();
         } else {
            AbstractInsnNode var1 = this.next;
            this.prev = var1;
            this.next = var1.next;
            this.remove = var1;
            return var1;
         }
      }

      public void remove() {
         if (this.remove != null) {
            if (this.remove == this.next) {
               this.next = this.next.next;
            } else {
               this.prev = this.prev.prev;
            }

            this.this$0.remove(this.remove);
            this.remove = null;
         } else {
            throw new IllegalStateException();
         }
      }

      public boolean hasPrevious() {
         return this.prev != null;
      }

      public Object previous() {
         AbstractInsnNode var1 = this.prev;
         this.next = var1;
         this.prev = var1.prev;
         this.remove = var1;
         return var1;
      }

      public int nextIndex() {
         if (this.next == null) {
            return this.this$0.size();
         } else {
            if (this.this$0.cache == null) {
               this.this$0.cache = this.this$0.toArray();
            }

            return this.next.index;
         }
      }

      public int previousIndex() {
         if (this.prev == null) {
            return -1;
         } else {
            if (this.this$0.cache == null) {
               this.this$0.cache = this.this$0.toArray();
            }

            return this.prev.index;
         }
      }

      public void add(Object var1) {
         if (this.next != null) {
            this.this$0.insertBefore(this.next, (AbstractInsnNode)var1);
         } else if (this.prev != null) {
            this.this$0.insert(this.prev, (AbstractInsnNode)var1);
         } else {
            this.this$0.add((AbstractInsnNode)var1);
         }

         this.prev = (AbstractInsnNode)var1;
         this.remove = null;
      }

      public void set(Object var1) {
         if (this.remove != null) {
            this.this$0.set(this.remove, (AbstractInsnNode)var1);
            if (this.remove == this.prev) {
               this.prev = (AbstractInsnNode)var1;
            } else {
               this.next = (AbstractInsnNode)var1;
            }

         } else {
            throw new IllegalStateException();
         }
      }
   }
}
