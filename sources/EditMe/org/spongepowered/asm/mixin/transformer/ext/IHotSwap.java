package org.spongepowered.asm.mixin.transformer.ext;

public interface IHotSwap {
   void registerMixinClass(String var1);

   void registerTargetClass(String var1, byte[] var2);
}
