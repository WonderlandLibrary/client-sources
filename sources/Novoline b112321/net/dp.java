package net;

import java.io.Reader;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition;
import net.minecraft.client.renderer.block.model.ModelBlockDefinition$Variants;

public class dp {
   public static ModelBlockDefinition$Variants a(ModelBlockDefinition var0, String var1) {
      return var0.getVariants(var1);
   }

   public static ModelBlockDefinition a(Reader var0) {
      return ModelBlockDefinition.parseFromReader(var0);
   }
}
