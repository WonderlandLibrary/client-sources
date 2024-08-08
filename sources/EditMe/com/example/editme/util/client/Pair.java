package com.example.editme.util.client;

public class Pair {
   Object key;
   Object value;

   public void setKey(Object var1) {
      this.key = var1;
   }

   public void setValue(Object var1) {
      this.value = var1;
   }

   public Object getValue() {
      return this.value;
   }

   public Pair(Object var1, Object var2) {
      this.key = var1;
      this.value = var2;
   }

   public Object getKey() {
      return this.key;
   }
}
