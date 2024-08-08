package org.yaml.snakeyaml.tokens;

public final class TagTuple {
   private final String handle;
   private final String suffix;

   public TagTuple(String var1, String var2) {
      if (var2 == null) {
         throw new NullPointerException("Suffix must be provided.");
      } else {
         this.handle = var1;
         this.suffix = var2;
      }
   }

   public String getHandle() {
      return this.handle;
   }

   public String getSuffix() {
      return this.suffix;
   }
}
