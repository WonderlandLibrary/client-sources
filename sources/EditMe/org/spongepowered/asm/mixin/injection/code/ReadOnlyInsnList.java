package org.spongepowered.asm.mixin.injection.code;

import java.util.ListIterator;
import org.spongepowered.asm.lib.tree.AbstractInsnNode;
import org.spongepowered.asm.lib.tree.InsnList;

class ReadOnlyInsnList extends InsnList {
   private InsnList insnList;

   public ReadOnlyInsnList(InsnList var1) {
      this.insnList = var1;
   }

   void dispose() {
      this.insnList = null;
   }

   public final void set(AbstractInsnNode var1, AbstractInsnNode var2) {
      throw new UnsupportedOperationException();
   }

   public final void add(AbstractInsnNode var1) {
      throw new UnsupportedOperationException();
   }

   public final void add(InsnList var1) {
      throw new UnsupportedOperationException();
   }

   public final void insert(AbstractInsnNode var1) {
      throw new UnsupportedOperationException();
   }

   public final void insert(InsnList var1) {
      throw new UnsupportedOperationException();
   }

   public final void insert(AbstractInsnNode var1, AbstractInsnNode var2) {
      throw new UnsupportedOperationException();
   }

   public final void insert(AbstractInsnNode var1, InsnList var2) {
      throw new UnsupportedOperationException();
   }

   public final void insertBefore(AbstractInsnNode var1, AbstractInsnNode var2) {
      throw new UnsupportedOperationException();
   }

   public final void insertBefore(AbstractInsnNode var1, InsnList var2) {
      throw new UnsupportedOperationException();
   }

   public final void remove(AbstractInsnNode var1) {
      throw new UnsupportedOperationException();
   }

   public AbstractInsnNode[] toArray() {
      return this.insnList.toArray();
   }

   public int size() {
      return this.insnList.size();
   }

   public AbstractInsnNode getFirst() {
      return this.insnList.getFirst();
   }

   public AbstractInsnNode getLast() {
      return this.insnList.getLast();
   }

   public AbstractInsnNode get(int var1) {
      return this.insnList.get(var1);
   }

   public boolean contains(AbstractInsnNode var1) {
      return this.insnList.contains(var1);
   }

   public int indexOf(AbstractInsnNode var1) {
      return this.insnList.indexOf(var1);
   }

   public ListIterator iterator() {
      return this.insnList.iterator();
   }

   public ListIterator iterator(int var1) {
      return this.insnList.iterator(var1);
   }

   public final void resetLabels() {
      this.insnList.resetLabels();
   }
}
