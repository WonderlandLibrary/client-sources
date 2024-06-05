package net.minecraft.client.resources;

import com.google.common.base.Function;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.client.resources.SimpleReloadableResourceManager;

class SimpleReloadableResourceManager$1 implements Function {
   final SimpleReloadableResourceManager this$0;

   SimpleReloadableResourceManager$1(SimpleReloadableResourceManager var1) {
      this.this$0 = var1;
   }

   public String apply(IResourcePack var1) {
      return var1.getPackName();
   }
}
