package vestige.module.impl.visual;

import net.minecraft.block.Block;
import vestige.module.Category;
import vestige.module.Module;

public class Xray extends Module {
   private float oldGamma;
   private final int[] blockIds = new int[]{14, 15, 56, 129};

   public Xray() {
      super("Xray", Category.VISUAL);
   }

   public void onEnable() {
      this.oldGamma = mc.gameSettings.gammaSetting;
      mc.gameSettings.gammaSetting = 10.0F;
      mc.gameSettings.ambientOcclusion = 0;
      mc.renderGlobal.loadRenderers();
   }

   public boolean onDisable() {
      mc.gameSettings.gammaSetting = this.oldGamma;
      mc.gameSettings.ambientOcclusion = 1;
      mc.renderGlobal.loadRenderers();
      return false;
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
