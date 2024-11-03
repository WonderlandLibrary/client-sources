package xyz.cucumber.base.module.feat.visuals;

import java.util.ArrayList;
import net.minecraft.util.Vec3;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.events.ext.EventTick;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.MovementUtils;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Displays trail when you walk",
   name = "Trail"
)
public class TrailModule extends Mod {
   private ArrayList<Vec3> crumbs = new ArrayList<>();
   private ArrayList<Integer> time = new ArrayList<>();
   public ColorSettings color = new ColorSettings("Color", "Rainbow", -1, -1, 100);
   private ModeSettings mode = new ModeSettings("Mode", new String[]{"Line", "Gradient", "Dotted"});
   private NumberSettings fadeOut = new NumberSettings("Fade time", 2000.0, 100.0, 5000.0, 1.0);

   public TrailModule() {
      this.addSettings(new ModuleSettings[]{this.mode, this.fadeOut, this.color});
   }

   @EventListener
   public void onRender3D(EventRender3D e) {
      GL11.glPushMatrix();
      RenderUtils.start3D();
      GL11.glShadeModel(7425);
      GL11.glEnable(2848);
      GL11.glEnable(2929);
      GL11.glLineWidth(4.0F);
      GL11.glPointSize(10.0F);
      if (this.mode.getMode().toLowerCase().equals("gradient")) {
         GL11.glBegin(8);
      } else if (this.mode.getMode().toLowerCase().equals("dotted")) {
         GL11.glBegin(0);
      } else {
         GL11.glBegin(3);
      }

      for (int i = 0; i < this.crumbs.size(); i++) {
         Vec3 pos = this.crumbs.get(i);
         int t = this.time.get(i);
         double a = 1.0 / this.fadeOut.getValue() * (double)((float)t - (float)System.nanoTime() / 1000000.0F);
         if ((float)t < (float)System.nanoTime() / 1000000.0F) {
            this.crumbs.remove(i);
            this.time.remove(i);
         } else {
            RenderUtils.color(ColorUtils.getColor(this.color, 0.0, (double)((float)i / (float)this.crumbs.size() * 360.0F), 1.0), (float)a);
            GL11.glVertex3d(
               pos.xCoord - this.mc.getRenderManager().viewerPosX,
               pos.yCoord - this.mc.getRenderManager().viewerPosY,
               pos.zCoord - this.mc.getRenderManager().viewerPosZ
            );
            if (this.mode.getMode().toLowerCase().equals("gradient")) {
               RenderUtils.color(ColorUtils.getColor(this.color, 0.0, (double)((float)i / (float)this.crumbs.size() * 360.0F), 1.0), 0.0F);
               GL11.glVertex3d(
                  pos.xCoord - this.mc.getRenderManager().viewerPosX,
                  pos.yCoord + 1.0 - this.mc.getRenderManager().viewerPosY,
                  pos.zCoord - this.mc.getRenderManager().viewerPosZ
               );
            }
         }
      }

      GL11.glEnd();
      GL11.glDisable(2929);
      GL11.glShadeModel(7424);
      RenderUtils.stop3D();
      GL11.glPopMatrix();
   }

   @EventListener
   public void onTick(EventTick e) {
      if (MovementUtils.isMoving() || !this.mc.thePlayer.onGround) {
         this.crumbs.add(new Vec3(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ));
         this.time.add((int)((double)(System.nanoTime() / 1000000L) + this.fadeOut.getValue()));
      }
   }
}
