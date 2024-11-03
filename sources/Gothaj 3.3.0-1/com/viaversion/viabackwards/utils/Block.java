package com.viaversion.viabackwards.utils;

public class Block {
   private final int id;
   private final short data;

   public Block(int id, int data) {
      this.id = id;
      this.data = (short)data;
   }

   public Block(int id) {
      this.id = id;
      this.data = 0;
   }

   public int getId() {
      return this.id;
   }

   public int getData() {
      return this.data;
   }

   public Block withData(int data) {
      return new Block(this.id, data);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         Block block = (Block)o;
         return this.id != block.id ? false : this.data == block.data;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      int result = this.id;
      return 31 * result + this.data;
   }
}
