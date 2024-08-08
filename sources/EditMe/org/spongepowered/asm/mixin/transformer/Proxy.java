package org.spongepowered.asm.mixin.transformer;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.launchwrapper.IClassTransformer;
import org.apache.logging.log4j.LogManager;
import org.spongepowered.asm.service.ILegacyClassTransformer;

public final class Proxy implements IClassTransformer, ILegacyClassTransformer {
   private static List proxies = new ArrayList();
   private static MixinTransformer transformer = new MixinTransformer();
   private boolean isActive = true;

   public Proxy() {
      Proxy var2;
      for(Iterator var1 = proxies.iterator(); var1.hasNext(); var2.isActive = false) {
         var2 = (Proxy)var1.next();
      }

      proxies.add(this);
      LogManager.getLogger("mixin").debug("Adding new mixin transformer proxy #{}", new Object[]{proxies.size()});
   }

   public byte[] transform(String var1, String var2, byte[] var3) {
      return this.isActive ? transformer.transformClassBytes(var1, var2, var3) : var3;
   }

   public String getName() {
      return this.getClass().getName();
   }

   public boolean isDelegationExcluded() {
      return true;
   }

   public byte[] transformClassBytes(String var1, String var2, byte[] var3) {
      return this.isActive ? transformer.transformClassBytes(var1, var2, var3) : var3;
   }
}
