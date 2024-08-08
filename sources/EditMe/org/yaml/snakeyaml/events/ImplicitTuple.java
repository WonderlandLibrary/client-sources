package org.yaml.snakeyaml.events;

public class ImplicitTuple {
   private final boolean plain;
   private final boolean nonPlain;

   public ImplicitTuple(boolean var1, boolean var2) {
      this.plain = var1;
      this.nonPlain = var2;
   }

   public boolean canOmitTagInPlainScalar() {
      return this.plain;
   }

   public boolean canOmitTagInNonPlainScalar() {
      return this.nonPlain;
   }

   public boolean bothFalse() {
      return !this.plain && !this.nonPlain;
   }

   public String toString() {
      return "implicit=[" + this.plain + ", " + this.nonPlain + "]";
   }
}
