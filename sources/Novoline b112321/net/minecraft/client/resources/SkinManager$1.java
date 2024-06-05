package net.minecraft.client.resources;

import com.google.common.cache.CacheLoader;
import com.mojang.authlib.GameProfile;
import java.util.Map;
import net.nk;
import net.minecraft.client.Minecraft;

class SkinManager$1 extends CacheLoader {
   final nk a;

   SkinManager$1(nk var1) {
      this.a = var1;
   }

   public Map load(GameProfile var1) {
      return Minecraft.getInstance().getSessionService().getTextures(var1, false);
   }
}
