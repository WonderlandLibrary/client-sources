package cc.slack.features.modules.impl.render;

import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.client.mc;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;

@ModuleInfo(
   name = "XRay",
   category = Category.RENDER
)
public class XRay extends Module {
   private float oldgammavalue;
   private final int[] blockIds = new int[]{14, 15, 56, 129};

   public void onEnable() {
      this.oldgammavalue = mc.getGameSettings().gammaSetting;
      mc.getGameSettings().gammaSetting = 10.0F;
      mc.getGameSettings().ambientOcclusion = 0;
      Minecraft.getMinecraft().renderGlobal.loadRenderers();
   }

   public void onDisable() {
      mc.getGameSettings().gammaSetting = this.oldgammavalue;
      mc.getGameSettings().ambientOcclusion = 1;
      Minecraft.getMinecraft().renderGlobal.loadRenderers();
   }

   public boolean shouldRenderBlock(Block block) {
      int[] var2 = this.blockIds;
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         int id = var2[var4];
         if (block == Block.getBlockById(id)) {
            return true;
         }
      }

      return false;
   }
}
