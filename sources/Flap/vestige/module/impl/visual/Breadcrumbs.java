package vestige.module.impl.visual;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PreMotionEvent;
import vestige.event.impl.Render3DEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.util.render.RenderUtil;
import vestige.util.render.RenderUtils2;

public class Breadcrumbs extends Module {
   List<Vec3> path = new ArrayList();
   private final IntegerSetting timeout = new IntegerSetting("Time", 15, 1, 150, 1);
   private ClientTheme theme;

   public Breadcrumbs() {
      super("Breadcrumbs", Category.VISUAL);
      this.addSettings(new AbstractSetting[]{this.timeout});
   }

   @Listener
   public void premotion(PreMotionEvent event) {
      if (mc.thePlayer.lastTickPosX != mc.thePlayer.posX || mc.thePlayer.lastTickPosY != mc.thePlayer.posY || mc.thePlayer.lastTickPosZ != mc.thePlayer.posZ) {
         this.path.add(new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ));
      }

      while(this.path.size() > 15) {
         this.path.remove(0);
      }

   }

   @Listener
   public void onrender3d(Render3DEvent event) {
      Iterator var2 = this.path.iterator();

      while(var2.hasNext()) {
         Vec3 var10000 = (Vec3)var2.next();
         this.renderBreadCrumbs(this.path);
      }

   }

   public void renderBreadCrumbs(List<Vec3> vec3s) {
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      GlStateManager.disableDepth();
      GL11.glEnable(3042);
      GL11.glDisable(3553);
      GL11.glEnable(2848);
      GL11.glBlendFunc(770, 771);
      Iterator var2 = vec3s.iterator();

      while(var2.hasNext()) {
         Vec3 v = (Vec3)var2.next();
         boolean var10000 = true;
         double x = v.xCoord - mc.getRenderManager().renderPosX;
         double y = v.yCoord - mc.getRenderManager().renderPosY;
         double z = v.zCoord - mc.getRenderManager().renderPosZ;
         mc.thePlayer.getDistance(v.xCoord, v.yCoord - 1.0D, v.zCoord);
         GL11.glPushMatrix();
         GL11.glTranslated(x, y, z);
         float var14 = 0.06F;
         GL11.glScalef(-0.06F, -0.06F, -0.06F);
         GL11.glRotated((double)(-mc.getRenderManager().playerViewY), 0.0D, 1.0D, 0.0D);
         GL11.glRotated((double)mc.getRenderManager().playerViewX, 1.0D, 0.0D, 0.0D);
         RenderUtil.lineWidth(10.0D);
         RenderUtils2.drawRect(x - 0.2D, y - 0.2D, x + 1.2D, y + 1.2D, Color.black.getRGB());
         RenderUtils2.drawRect(x, y, x + 1.0D, y + 1.0D, this.theme.getColor(1));
         GL11.glPopMatrix();
      }

   }
}
