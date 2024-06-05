package net;

public enum bgb {
   POSITIVE("POSITIVE", 0, 1, "Towards positive"),
   NEGATIVE("NEGATIVE", 1, -1, "Towards negative");

   private final int a;
   private final String b;
   private static final bgb[] c = new bgb[]{POSITIVE, NEGATIVE};

   private bgb(String var3, int var4, int var5, String var6) {
      this.a = var5;
      this.b = var6;
   }

   public int a() {
      return this.a;
   }

   public String toString() {
      return this.b;
   }
}
