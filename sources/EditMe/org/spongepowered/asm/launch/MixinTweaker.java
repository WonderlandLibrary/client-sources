package org.spongepowered.asm.launch;

import java.io.File;
import java.util.List;
import net.minecraft.launchwrapper.ITweaker;
import net.minecraft.launchwrapper.LaunchClassLoader;

public class MixinTweaker implements ITweaker {
   public MixinTweaker() {
      MixinBootstrap.start();
   }

   public final void acceptOptions(List var1, File var2, File var3, String var4) {
      MixinBootstrap.doInit(var1);
   }

   public final void injectIntoClassLoader(LaunchClassLoader var1) {
      MixinBootstrap.inject();
   }

   public String getLaunchTarget() {
      return MixinBootstrap.getPlatform().getLaunchTarget();
   }

   public String[] getLaunchArguments() {
      return new String[0];
   }
}
