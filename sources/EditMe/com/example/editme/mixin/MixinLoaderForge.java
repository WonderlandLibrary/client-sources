package com.example.editme.mixin;

import java.util.Map;
import javax.annotation.Nullable;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import org.spongepowered.asm.launch.MixinBootstrap;
import org.spongepowered.asm.mixin.MixinEnvironment;
import org.spongepowered.asm.mixin.Mixins;

public class MixinLoaderForge implements IFMLLoadingPlugin {
   public MixinLoaderForge() {
      MixinBootstrap.init();
      Mixins.addConfiguration("mixins.editme.json");
      MixinEnvironment.getDefaultEnvironment().setSide(MixinEnvironment.Side.CLIENT);
      MixinEnvironment.getCurrentEnvironment().setOption(MixinEnvironment.Option.DEBUG_VERBOSE, true);
      MixinEnvironment.getCurrentEnvironment().setOption(MixinEnvironment.Option.DEBUG_TARGETS, true);
      MixinEnvironment.getCurrentEnvironment().setOption(MixinEnvironment.Option.DEBUG_INJECTORS, true);
      MixinEnvironment.getCurrentEnvironment().setOption(MixinEnvironment.Option.DUMP_TARGET_ON_FAILURE, true);
   }

   public String[] getASMTransformerClass() {
      return new String[0];
   }

   public String getModContainerClass() {
      return null;
   }

   @Nullable
   public String getSetupClass() {
      return null;
   }

   public void injectData(Map var1) {
   }

   public String getAccessTransformerClass() {
      return null;
   }
}
