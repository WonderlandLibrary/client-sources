package net.minecraft.client.renderer.block.model;

import net.minecraft.client.renderer.block.model.ModelBlock;
import net.minecraft.client.renderer.block.model.ModelBlock$1;

final class ModelBlock$Bookkeep {
   public final ModelBlock model;
   public ModelBlock modelExt;

   private ModelBlock$Bookkeep(ModelBlock var1) {
      this.model = var1;
   }

   ModelBlock$Bookkeep(ModelBlock var1, ModelBlock$1 var2) {
      this(var1);
   }
}
