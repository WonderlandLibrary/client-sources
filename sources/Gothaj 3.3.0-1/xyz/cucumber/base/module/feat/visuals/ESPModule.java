package xyz.cucumber.base.module.feat.visuals;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventBlur;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.events.ext.EventRenderGui;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.math.Convertors;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Allows you see players behind obsticles",
   name = "ESP"
)
public class ESPModule extends Mod {
   public BooleanSettings filled = new BooleanSettings("Filled", true);
   public BooleanSettings blur = new BooleanSettings("Blur", true);
   public ColorSettings filledColor = new ColorSettings("Filled Color", "Static", -16777216, -1, 40);
   public ModeSettings outline = new ModeSettings("Outline", new String[]{"None", "Full", "Brackets", "Arrows"});
   public ColorSettings outlineColor = new ColorSettings("Outline Color", "Static", -1, -1, 100);
   public BooleanSettings healthBar = new BooleanSettings("Health Bar", true);
   public HashMap<EntityPlayer, PositionUtils> entities = new HashMap<>();
   private double start;

   public ESPModule() {
      this.addSettings(new ModuleSettings[]{this.filled, this.blur, this.filledColor, this.outline, this.outlineColor, this.healthBar});
   }

   @Override
   public void onEnable() {
      this.start = (double)(System.nanoTime() / 1000000L);
   }

   @EventListener
   public void onRender3d(EventRender3D e) {
      this.entities.clear();

      for (Entity entity : this.mc.theWorld.loadedEntityList) {
         if ((!(this.mc.thePlayer.getDistanceToEntity(entity) < 1.0F) || this.mc.gameSettings.thirdPersonView != 0)
            && (entity != this.mc.thePlayer || this.mc.gameSettings.thirdPersonView != 0)
            && entity instanceof EntityPlayer
            && RenderUtils.isInViewFrustrum(entity)) {
            double x = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosX;
            double y = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosY;
            double z = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosZ;
            double width = (double)entity.width / 2.5;
            AxisAlignedBB bb = new AxisAlignedBB(x - width, y, z - width, x + width, y + (double)entity.height, z + width).expand(0.2, 0.1, 0.2);
            List<double[]> vectors = Arrays.asList(
               new double[]{bb.minX, bb.minY, bb.minZ},
               new double[]{bb.minX, bb.maxY, bb.minZ},
               new double[]{bb.minX, bb.maxY, bb.maxZ},
               new double[]{bb.minX, bb.minY, bb.maxZ},
               new double[]{bb.maxX, bb.minY, bb.minZ},
               new double[]{bb.maxX, bb.maxY, bb.minZ},
               new double[]{bb.maxX, bb.maxY, bb.maxZ},
               new double[]{bb.maxX, bb.minY, bb.maxZ}
            );
            double[] position = new double[]{Float.MAX_VALUE, Float.MAX_VALUE, -1.0, -1.0};

            for (double[] vec : vectors) {
               float[] points = Convertors.convert2D((float)vec[0], (float)vec[1], (float)vec[2], new ScaledResolution(this.mc).getScaleFactor());
               if (points != null && points[2] >= 0.0F && points[2] < 1.0F) {
                  float pX = points[0];
                  float pY = points[1];
                  position[0] = Math.min(position[0], (double)pX);
                  position[1] = Math.min(position[1], (double)pY);
                  position[2] = Math.max(position[2], (double)pX);
                  position[3] = Math.max(position[3], (double)pY);
               }
            }

            this.entities.put((EntityPlayer)entity, new PositionUtils(position[0], position[1], position[2] - position[0], position[3] - position[1], 1.0F));
         }
      }
   }

   @EventListener
   public void onBlur(EventBlur e) {
      if (this.blur.isEnabled()) {
         e.setCancelled(true);
         if (e.getType() == EventType.POST) {
            GlStateManager.pushMatrix();

            for (Entry<EntityPlayer, PositionUtils> entry : this.entities.entrySet()) {
               EntityPlayer player = entry.getKey();
               PositionUtils pos = entry.getValue();
               RenderUtils.drawRect(pos.getX(), pos.getY(), pos.getX2(), pos.getY2(), -1);
            }

            GlStateManager.popMatrix();
         }
      }
   }

   @EventListener
   public void onRenderGui(EventRenderGui e) {
      GlStateManager.pushMatrix();

      for (Entry<EntityPlayer, PositionUtils> entry : this.entities.entrySet()) {
         EntityPlayer player = entry.getKey();
         PositionUtils pos = entry.getValue();
         GlStateManager.pushMatrix();
         if (this.filled.isEnabled()) {
            RenderUtils.drawRect(
               pos.getX(), pos.getY(), pos.getX2(), pos.getY2(), ColorUtils.getColor(this.filledColor, (double)(System.nanoTime() / 1000000L), 1.0, 5.0)
            );
         }

         this.renderOutline(pos);
         if (this.healthBar.isEnabled()) {
            this.renderHealth(pos.getX() - 2.0, pos.getY(), pos.getHeight(), -1879048192, 3.0F);
            this.renderHealth(
               pos.getX() - 2.0,
               pos.getY(),
               pos.getHeight() / (double)this.mc.thePlayer.getMaxHealth() * (double)this.mc.thePlayer.getHealth(),
               ColorUtils.mix(
                  -65536,
                  -16711936,
                  (double)this.mc.thePlayer.getMaxHealth(),
                  (double)(this.mc.thePlayer.getHealth() < 0.0F ? this.mc.thePlayer.getMaxHealth() : this.mc.thePlayer.getHealth())
               ),
               2.5F
            );
         }

         GlStateManager.popMatrix();
      }

      GlStateManager.popMatrix();
   }

   public void renderOutline(PositionUtils pos) {
      String var2;
      switch ((var2 = this.outline.getMode().toLowerCase()).hashCode()) {
         case -1409151606:
            if (var2.equals("arrows")) {
               double aw = pos.getWidth() / 4.0;
               this.renderBracket(pos.getX() + aw - 0.3, pos.getY(), pos.getX(), pos.getY(), pos.getX(), pos.getY() + aw + 0.3, -1879048192, 1.6);
               this.renderBracket(pos.getX2() - aw - 0.3, pos.getY(), pos.getX2(), pos.getY(), pos.getX2(), pos.getY() + aw + 0.3, -1879048192, 1.6);
               this.renderBracket(pos.getX(), pos.getY2() - aw - 0.3, pos.getX(), pos.getY2(), pos.getX() + aw + 0.3, pos.getY2(), -1879048192, 1.6);
               this.renderBracket(pos.getX2(), pos.getY2() - aw - 0.3, pos.getX2(), pos.getY2(), pos.getX2() - aw - 0.3, pos.getY2(), -1879048192, 1.6);
            }
            break;
         case -35329845:
            if (var2.equals("brackets")) {
               double bw = pos.getWidth() / 4.0;
               this.renderBracket(new double[]{pos.getX(), pos.getY()}, new double[]{pos.getX(), pos.getY2()}, bw + 0.3, -1879048192, 1.6);
               this.renderBracket(new double[]{pos.getX2(), pos.getY()}, new double[]{pos.getX2(), pos.getY2()}, -bw - 0.3, -1879048192, 1.6);
            }
            break;
         case 3154575:
            if (var2.equals("full")) {
               RenderUtils.drawOutlinedRect(pos.getX(), pos.getY(), pos.getX2(), pos.getY2(), -1879048192, 1.6F);
            }
      }

      if (!this.outline.getMode().toLowerCase().equals("none")) {
         String var8;
         switch ((var8 = this.outline.getMode().toLowerCase()).hashCode()) {
            case -1409151606:
               if (var8.equals("arrows")) {
                  double aw = pos.getWidth() / 4.0;
                  this.renderBracket(
                     pos.getX() + aw,
                     pos.getY(),
                     pos.getX(),
                     pos.getY(),
                     pos.getX(),
                     pos.getY() + aw,
                     ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 1000000L), 1.0, 5.0),
                     1.0
                  );
                  this.renderBracket(
                     pos.getX2() - aw,
                     pos.getY(),
                     pos.getX2(),
                     pos.getY(),
                     pos.getX2(),
                     pos.getY() + aw,
                     ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 1000000L), 1.0, 5.0),
                     1.0
                  );
                  this.renderBracket(
                     pos.getX(),
                     pos.getY2() - aw,
                     pos.getX(),
                     pos.getY2(),
                     pos.getX() + aw,
                     pos.getY2(),
                     ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 1000000L), 1.0, 5.0),
                     1.0
                  );
                  this.renderBracket(
                     pos.getX2(),
                     pos.getY2() - aw,
                     pos.getX2(),
                     pos.getY2(),
                     pos.getX2() - aw,
                     pos.getY2(),
                     ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 1000000L), 1.0, 5.0),
                     1.0
                  );
               }
               break;
            case -35329845:
               if (var8.equals("brackets")) {
                  double bw = pos.getWidth() / 4.0;
                  this.renderBracket(
                     new double[]{pos.getX(), pos.getY()},
                     new double[]{pos.getX(), pos.getY2()},
                     bw,
                     ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 1000000L), 1.0, 5.0),
                     1.0
                  );
                  this.renderBracket(
                     new double[]{pos.getX2(), pos.getY()},
                     new double[]{pos.getX2(), pos.getY2()},
                     -bw,
                     ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 1000000L), 1.0, 5.0),
                     1.0
                  );
               }
               break;
            case 3154575:
               if (var8.equals("full")) {
                  RenderUtils.drawOutlinedRect(
                     pos.getX(),
                     pos.getY(),
                     pos.getX2(),
                     pos.getY2(),
                     ColorUtils.getColor(this.outlineColor, (double)(System.nanoTime() / 1000000L), 1.0, 5.0),
                     1.0F
                  );
               }
         }
      }
   }

   public void renderHealth(double x, double y, double width, int color, float player) {
      GlStateManager.pushMatrix();
      RenderUtils.start2D();
      RenderUtils.color(color);
      GL11.glLineWidth(player);
      GL11.glBegin(3);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x, y + width);
      GL11.glEnd();
      GL11.glLineWidth(1.0F);
      RenderUtils.color(-1);
      RenderUtils.stop2D();
      GlStateManager.popMatrix();
   }

   public void renderBracket(double[] pos1, double[] pos2, double width, int color, double size) {
      GlStateManager.pushMatrix();
      RenderUtils.start2D();
      RenderUtils.color(color);
      GL11.glLineWidth((float)size);
      GL11.glBegin(3);
      GL11.glVertex2d(pos1[0] + width, pos1[1]);
      GL11.glVertex2d(pos1[0], pos1[1]);
      GL11.glVertex2d(pos2[0], pos2[1]);
      GL11.glVertex2d(pos2[0] + width, pos2[1]);
      GL11.glEnd();
      RenderUtils.color(-1);
      RenderUtils.stop2D();
      GlStateManager.popMatrix();
   }

   public void renderBracket(double x, double y, double x1, double y1, double x2, double y2, int color, double size) {
      GlStateManager.pushMatrix();
      RenderUtils.start2D();
      RenderUtils.color(color);
      GL11.glLineWidth((float)size);
      GL11.glBegin(3);
      GL11.glVertex2d(x, y);
      GL11.glVertex2d(x1, y1);
      GL11.glVertex2d(x2, y2);
      GL11.glEnd();
      RenderUtils.color(-1);
      RenderUtils.stop2D();
      GlStateManager.popMatrix();
   }
}
