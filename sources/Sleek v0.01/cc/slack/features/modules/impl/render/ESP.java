package cc.slack.features.modules.impl.render;

import cc.slack.events.impl.render.RenderEvent;
import cc.slack.features.modules.api.Category;
import cc.slack.features.modules.api.Module;
import cc.slack.features.modules.api.ModuleInfo;
import cc.slack.features.modules.api.settings.Value;
import cc.slack.features.modules.api.settings.impl.NumberValue;
import cc.slack.utils.client.mc;
import cc.slack.utils.render.Render3DUtil;
import io.github.nevalackin.radbus.Listen;
import java.util.Iterator;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Timer;
import org.lwjgl.opengl.GL11;

@ModuleInfo(
   name = "ESP",
   category = Category.RENDER
)
public class ESP extends Module {
   private final NumberValue<Float> lineWidth = new NumberValue("Line Width", 1.0F, 1.0F, 3.0F, 0.1F);

   public ESP() {
      this.addSettings(new Value[]{this.lineWidth});
   }

   @Listen
   public void onRender(RenderEvent event) {
      if (event.getState() == RenderEvent.State.RENDER_3D) {
         Iterator var2 = mc.getWorld().loadedEntityList.iterator();

         while(var2.hasNext()) {
            Entity entity = (Entity)var2.next();
            if (entity.getEntityId() != mc.getPlayer().getEntityId()) {
               RenderManager renderManager = mc.getRenderManager();
               Timer timer = mc.getTimer();
               GL11.glBlendFunc(770, 771);
               Render3DUtil.enableGlCap(3042);
               Render3DUtil.disableGlCap(3553, 2929);
               GL11.glDepthMask(false);
               double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * (double)timer.renderPartialTicks - renderManager.renderPosX;
               double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * (double)timer.renderPartialTicks - renderManager.renderPosY;
               double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * (double)timer.renderPartialTicks - renderManager.renderPosZ;
               AxisAlignedBB entityBox = entity.getEntityBoundingBox();
               AxisAlignedBB axisAlignedBB = new AxisAlignedBB(entityBox.minX - entity.posX + x - 0.05D, entityBox.minY - entity.posY + y, entityBox.minZ - entity.posZ + z - 0.05D, entityBox.maxX - entity.posX + x + 0.05D, entityBox.maxY - entity.posY + y + 0.15D, entityBox.maxZ - entity.posZ + z + 0.05D);
               GL11.glLineWidth((Float)this.lineWidth.getValue());
               Render3DUtil.enableGlCap(2848);
               if (entity.hurtResistantTime > 1) {
                  glColor(255, 10, 10, 95);
               } else {
                  glColor(255, 255, 255, 95);
               }

               Render3DUtil.drawSelectionBoundingBox(axisAlignedBB);
               GlStateManager.resetColor();
               GL11.glDepthMask(true);
               Render3DUtil.resetCaps();
            }
         }

      }
   }

   public static void glColor(int red, int green, int blue, int alpha) {
      GlStateManager.color((float)red / 255.0F, (float)green / 255.0F, (float)blue / 255.0F, (float)alpha / 255.0F);
   }
}
