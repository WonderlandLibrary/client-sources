package vestige.module.impl.visual;

import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.util.BlockPos;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.Render3DEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.util.render.RenderUtil;

public class BlockOrvelay extends Module {
   private ClientTheme theme;
   private final BooleanSetting outline = new BooleanSetting("Outline", true);
   private final BooleanSetting fill = new BooleanSetting("Fill", true);

   public BlockOrvelay() {
      super("BlockOrvelay", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.outline, this.fill});
   }

   @Listener
   public void onRender3D(Render3DEvent event) {
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      BlockPos blockPos = mc.objectMouseOver.getBlockPos();
      if (blockPos != null) {
         Block blockAtPos = mc.theWorld.getBlockState(blockPos).getBlock();
         BlockPos blockPostoRender = mc.objectMouseOver.getBlockPos();
         if (!(blockAtPos instanceof BlockAir)) {
            RenderUtil.renderBlock(blockPostoRender, this.theme.getColor(100), this.outline.isEnabled(), this.fill.isEnabled());
         }
      }

   }
}
