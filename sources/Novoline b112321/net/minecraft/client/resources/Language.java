package net.minecraft.client.resources;

public class Language implements Comparable {
   private final String languageCode;
   private final String region;
   private final String name;
   private final boolean bidirectional;

   public Language(String var1, String var2, String var3, boolean var4) {
      this.languageCode = var1;
      this.region = var2;
      this.name = var3;
      this.bidirectional = var4;
   }

   public String getLanguageCode() {
      return this.languageCode;
   }

   public boolean isBidirectional() {
      return this.bidirectional;
   }

   public String toString() {
      return String.format("%s (%s)", new Object[]{this.name, this.region});
   }

   public boolean equals(Object var1) {
      return this == var1 || var1 instanceof Language && this.languageCode.equals(((Language)var1).languageCode);
   }

   public int hashCode() {
      return this.languageCode.hashCode();
   }

   public int compareTo(Language var1) {
      return this.languageCode.compareTo(var1.languageCode);
   }
}
