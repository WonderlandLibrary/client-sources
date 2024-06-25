package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.utils.render.Render3DUtil;
import io.github.nevalackin.radbus.Listen;
import java.util.ArrayList;
import java.util.Iterator;
import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

@ModuleInfo(
   name = "ChestESP",
   category = Category.RENDER
)
public class ChestESP extends Module {
   public ArrayList<BlockPos> chestBoundingBoxes = new ArrayList();
   private final Integer[] chestIDS = new Integer[]{54, 130, 146};

   @Listen
   public void onRender(RenderEvent event) {
      if (event.getState() == RenderEvent.State.RENDER_3D) {
         Iterator var2 = this.chestBoundingBoxes.iterator();

         while(var2.hasNext()) {
            BlockPos bp = (BlockPos)var2.next();
            Render3DUtil.drawAABB(AxisAlignedBB.fromBounds((double)bp.getX(), (double)bp.getY(), (double)bp.getZ(), (double)(bp.getX() + 1), (double)(bp.getY() + 1), (double)(bp.getZ() + 1)));
         }

         this.chestBoundingBoxes.clear();
      }
   }

   public boolean isChest(Block block) {
      Integer[] var2 = this.chestIDS;
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
