package net.minecraft.util;

public class Tuple<A, B> {
   private final A a;
   private final B b;

   public Tuple(A aIn, B bIn) {
      this.a = aIn;
      this.b = bIn;
   }

   public A getFirst() {
      return this.a;
   }

   public B getSecond() {
      return this.b;
   }
}
