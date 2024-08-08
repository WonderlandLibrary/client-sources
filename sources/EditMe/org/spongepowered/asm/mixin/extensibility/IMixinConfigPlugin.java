package org.spongepowered.asm.mixin.extensibility;

import java.util.List;
import java.util.Set;
import org.spongepowered.asm.lib.tree.ClassNode;

public interface IMixinConfigPlugin {
   void onLoad(String var1);

   String getRefMapperConfig();

   boolean shouldApplyMixin(String var1, String var2);

   void acceptTargets(Set var1, Set var2);

   List getMixins();

   void preApply(String var1, ClassNode var2, String var3, IMixinInfo var4);

   void postApply(String var1, ClassNode var2, String var3, IMixinInfo var4);
}
