package org.spongepowered.asm.service.mojang;

import javax.annotation.Resource;
import net.minecraft.launchwrapper.IClassTransformer;
import org.spongepowered.asm.service.ILegacyClassTransformer;

class LegacyTransformerHandle implements ILegacyClassTransformer {
   private final IClassTransformer transformer;

   LegacyTransformerHandle(IClassTransformer var1) {
      this.transformer = var1;
   }

   public String getName() {
      return this.transformer.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      return this.transformer.getClass().getAnnotation(Resource.class) != null;
   }

   public byte[] transformClassBytes(String var1, String var2, byte[] var3) {
      return this.transformer.transform(var1, var2, var3);
   }
}
