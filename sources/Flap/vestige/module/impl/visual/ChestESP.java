package vestige.module.impl.visual;

import java.util.Iterator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.Render3DEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.util.render.RenderUtil;

public class ChestESP extends Module {
   private ClientTheme theme;

   public ChestESP() {
      super("ChestESP", Category.VISUAL);
   }

   @Listener
   public void onRender3D(Render3DEvent event) {
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      Iterator var2 = mc.theWorld.loadedTileEntityList.iterator();

      while(var2.hasNext()) {
         TileEntity tileEntity = (TileEntity)var2.next();
         if (tileEntity instanceof TileEntityChest && this.theme != null) {
            RenderUtil.renderChest(tileEntity.getPos(), this.theme.getColor(1), true, true);
         }
      }

   }
}
