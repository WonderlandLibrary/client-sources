package net.minecraft.client.renderer.block.model;

import java.util.List;

public class ModelBlockDefinition$Variants {
   private final String name;
   private final List listVariants;

   public ModelBlockDefinition$Variants(String var1, List var2) {
      this.name = var1;
      this.listVariants = var2;
   }

   public List getVariants() {
      return this.listVariants;
   }

   public boolean equals(Object var1) {
      if(this == var1) {
         return true;
      } else if(!(var1 instanceof ModelBlockDefinition$Variants)) {
         return false;
      } else {
         ModelBlockDefinition$Variants var2 = (ModelBlockDefinition$Variants)var1;
         return this.name.equals(var2.name) && this.listVariants.equals(var2.listVariants);
      }
   }

   public int hashCode() {
      int var1 = this.name.hashCode();
      var1 = 31 * var1 + this.listVariants.hashCode();
      return var1;
   }

   static String access$000(ModelBlockDefinition$Variants var0) {
      return var0.name;
   }
}
