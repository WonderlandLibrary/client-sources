package vestige.module.impl.visual;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.BlockBed;
import net.minecraft.util.BlockPos;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.Render3DEvent;
import vestige.event.impl.TickEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.util.render.RenderUtil;

public class BedESP extends Module {
   private final IntegerSetting range = new IntegerSetting("Range", 20, 1, 100, 1);
   private List<BlockPos> bedPos = new ArrayList();
   private ClientTheme theme;

   public BedESP() {
      super("BedESP", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.range});
   }

   @Listener
   public void onTick(TickEvent event) {
      this.bedPos.clear();

      for(double x = mc.thePlayer.posX - (double)this.range.getValue(); x <= mc.thePlayer.posX + (double)this.range.getValue(); ++x) {
         for(double y = mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() - (double)this.range.getValue(); y <= mc.thePlayer.posY + (double)mc.thePlayer.getEyeHeight() + (double)this.range.getValue(); ++y) {
            for(double z = mc.thePlayer.posZ - (double)this.range.getValue(); z <= mc.thePlayer.posZ + (double)this.range.getValue(); ++z) {
               BlockPos pos = new BlockPos(x, y, z);
               if (mc.theWorld.getBlockState(pos).getBlock() instanceof BlockBed) {
                  this.bedPos.add(pos);
               }
            }
         }
      }

   }

   @Listener
   public void onRender3d(Render3DEvent event) {
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      if (!this.bedPos.isEmpty() && this.theme != null) {
         Iterator var2 = this.bedPos.iterator();

         while(var2.hasNext()) {
            BlockPos pos = (BlockPos)var2.next();
            RenderUtil.renderBlock(pos, this.theme.getColor(1), true, true);
         }
      }

   }
}
