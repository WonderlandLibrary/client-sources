package xyz.cucumber.base.module.feat.visuals;

import java.util.Arrays;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import org.lwjgl.opengl.GL11;
import xyz.cucumber.base.events.EventListener;
import xyz.cucumber.base.events.EventType;
import xyz.cucumber.base.events.ext.EventAttack;
import xyz.cucumber.base.events.ext.EventBloom;
import xyz.cucumber.base.events.ext.EventBlur;
import xyz.cucumber.base.events.ext.EventRender3D;
import xyz.cucumber.base.events.ext.EventRenderGui;
import xyz.cucumber.base.module.ArrayPriority;
import xyz.cucumber.base.module.Category;
import xyz.cucumber.base.module.Mod;
import xyz.cucumber.base.module.ModuleInfo;
import xyz.cucumber.base.module.addons.Dragable;
import xyz.cucumber.base.module.settings.BooleanSettings;
import xyz.cucumber.base.module.settings.ColorSettings;
import xyz.cucumber.base.module.settings.ModeSettings;
import xyz.cucumber.base.module.settings.ModuleSettings;
import xyz.cucumber.base.module.settings.NumberSettings;
import xyz.cucumber.base.utils.RenderUtils;
import xyz.cucumber.base.utils.Timer;
import xyz.cucumber.base.utils.math.Convertors;
import xyz.cucumber.base.utils.math.PositionHandler;
import xyz.cucumber.base.utils.position.PositionUtils;
import xyz.cucumber.base.utils.render.ColorUtils;
import xyz.cucumber.base.utils.render.Fonts;
import xyz.cucumber.base.utils.render.StencilUtils;

@ModuleInfo(
   category = Category.VISUALS,
   description = "Displays target hud",
   name = "Target Hud",
   priority = ArrayPriority.LOW
)
public class TargetHudModule extends Mod implements Dragable {
   private ModeSettings mode = new ModeSettings("Mode", new String[]{"Style 1", "Style 2"});
   private NumberSettings positionX = new NumberSettings("Position X", 30.0, 0.0, 1000.0, 1.0);
   private NumberSettings positionY = new NumberSettings("Position Y", 50.0, 0.0, 1000.0, 1.0);
   private BooleanSettings follow = new BooleanSettings("Follow", false);
   private BooleanSettings imageRounding = new BooleanSettings("Image rounding", true);
   private BooleanSettings rounded = new BooleanSettings("Rounded", true);
   private ColorSettings textColor = new ColorSettings("Text color", "Static", -16777216, -1, 50);
   private ColorSettings backgroundColor = new ColorSettings("Background color", "Static", -16777216, -1, 50);
   private ColorSettings healthColor = new ColorSettings("Health color", "Static", -65536, -1, 50);
   private BooleanSettings blur = new BooleanSettings("Blur", true);
   private BooleanSettings bloom = new BooleanSettings("Bloom", true);
   private ColorSettings bloomColor = new ColorSettings("Bloom Color", "Static", -16777216, -1, 100);
   private double animation;
   private double openAnimation;
   private Timer timer = new Timer();
   private EntityPlayer target;
   private boolean open;
   private double hurtAnimation;
   double x;
   double y;

   public TargetHudModule() {
      this.addSettings(
         new ModuleSettings[]{
            this.positionX,
            this.positionY,
            this.mode,
            this.follow,
            this.imageRounding,
            this.rounded,
            this.textColor,
            this.backgroundColor,
            this.healthColor,
            this.blur,
            this.bloom,
            this.bloomColor
         }
      );
   }

   @Override
   public PositionUtils getPosition() {
      if (this.target == null) {
         return null;
      } else {
         double width = 40.0 + Fonts.getFont("rb-b").getWidth(this.target.getName()) + 8.0;
         if (width < 110.0) {
            width = 110.0;
         }

         width += this.hurtAnimation;
         double height = 34.0;
         if (this.follow.isEnabled()) {
            return new PositionUtils(0.0, 0.0, 0.0, 0.0, 1.0F);
         } else {
            double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
            return new PositionUtils(pos[0], pos[1], width, height, 1.0F);
         }
      }
   }

   @Override
   public void setXYPosition(double x, double y) {
      this.positionX.setValue(x);
      this.positionY.setValue(y);
   }

   @EventListener
   public void onRender3D(EventRender3D e) {
      if (this.follow.isEnabled()) {
         if (this.target == null) {
            return;
         }

         if (this.mc.thePlayer.getDistanceToEntity(this.target) > 10.0F) {
            this.target = null;
            return;
         }

         double x1 = this.target.prevPosX + (this.target.posX - this.target.prevPosX) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosX;
         double y1 = this.target.prevPosY + (this.target.posY - this.target.prevPosY) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosY;
         double z1 = this.target.prevPosZ + (this.target.posZ - this.target.prevPosZ) * (double)e.getPartialTicks() - this.mc.getRenderManager().viewerPosZ;
         double width = (double)this.target.width / 2.5;
         AxisAlignedBB bb = new AxisAlignedBB(x1 - width, y1, z1 - width, x1 + width, y1 + (double)this.target.height, z1 + width).expand(0.2, 0.1, 0.2);
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

         this.x = position[0];
         this.y = position[1] + (position[3] - position[1]) / 2.0 - 17.0;
      } else {
         double[] pos = PositionHandler.getScaledPosition(this.positionX.getValue(), this.positionY.getValue());
         this.x = pos[0];
         this.y = pos[1];
      }
   }

   @EventListener
   public void onRender2D(EventRenderGui e) {
      if (this.mc.currentScreen instanceof GuiChat && !this.follow.isEnabled()) {
         this.target = this.mc.thePlayer;
         this.timer.reset();
         this.open = true;
      }

      if (this.target == null) {
         this.open = false;
      } else {
         if (this.open) {
            this.openAnimation = (this.openAnimation * 9.0 + 1.0) / 10.0;
            if (this.timer.hasTimeElapsed(2000.0, true)) {
               this.open = false;
            }
         } else {
            this.openAnimation = this.openAnimation * 9.0 / 10.0;
         }

         double width = 40.0 + Fonts.getFont("rb-b").getWidth(this.target.getName()) + 8.0;
         if (width < 110.0) {
            width = 110.0;
         }

         this.hurtAnimation = (this.hurtAnimation * 9.0 + (double)(this.mc.thePlayer.hurtTime / 4)) / 10.0;
         double height = 34.0;
         GL11.glPushMatrix();
         GL11.glTranslated(
            this.x - this.x * this.openAnimation + width / 2.0 - width / 2.0 * this.openAnimation,
            this.y - this.y * this.openAnimation + height / 2.0 - height / 2.0 * this.openAnimation,
            0.0
         );
         GL11.glScaled(this.openAnimation, this.openAnimation, 1.0);
         String var6;
         switch ((var6 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -1875215998:
               if (var6.equals("style 1")) {
                  int bg = ColorUtils.getColor(this.backgroundColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
                  if (this.rounded.isEnabled()) {
                     if (!this.backgroundColor.getMode().toLowerCase().equals("mix")) {
                        RenderUtils.drawRoundedRect(this.x, this.y, this.x + width, this.y + height, bg, 5.0F);
                     } else {
                        RenderUtils.drawMixedRoundedRect(
                           this.x,
                           this.y,
                           this.x + width,
                           this.y + height,
                           ColorUtils.getAlphaColor(this.backgroundColor.getMainColor(), this.backgroundColor.getAlpha()),
                           this.backgroundColor.getSecondaryColor(),
                           5.0
                        );
                     }
                  } else if (!this.backgroundColor.getMode().toLowerCase().equals("mix")) {
                     RenderUtils.drawRect(this.x, this.y, this.x + width, this.y + height, bg);
                  } else {
                     RenderUtils.drawMixedRect(
                        this.x,
                        this.y,
                        this.x + width,
                        this.y + height,
                        ColorUtils.getAlphaColor(this.backgroundColor.getMainColor(), this.backgroundColor.getAlpha()),
                        this.backgroundColor.getSecondaryColor()
                     );
                  }

                  if (this.mc.getNetHandler().getPlayerInfo(this.target.getUniqueID()) != null
                     && this.mc.getNetHandler().getPlayerInfo(this.target.getUniqueID()).getLocationSkin() != null) {
                     if (this.imageRounding.isEnabled()) {
                        StencilUtils.initStencil();
                        GL11.glEnable(2960);
                        StencilUtils.bindWriteStencilBuffer();
                        RenderUtils.drawRoundedRect(this.x + 2.0, this.y + 2.0, this.x + 32.0, this.y + 32.0, -14606047, 2.5F);
                        StencilUtils.bindReadStencilBuffer(1);
                        RenderUtils.color(ColorUtils.mix(-1, -65536, (double)this.target.hurtTime, 10.0));
                        Minecraft.getMinecraft()
                           .getTextureManager()
                           .bindTexture(this.mc.getNetHandler().getPlayerInfo(this.target.getUniqueID()).getLocationSkin());
                        Gui.drawScaledCustomSizeModalRect(
                           this.x + 2.0 + this.hurtAnimation,
                           this.y + 2.0 + this.hurtAnimation,
                           8.0F,
                           8.0F,
                           8.0,
                           8.0,
                           30.0 - this.hurtAnimation * 2.0,
                           30.0 - this.hurtAnimation * 2.0,
                           64.0F,
                           64.0F
                        );
                        StencilUtils.uninitStencilBuffer();
                     } else {
                        RenderUtils.color(ColorUtils.mix(-1, -65536, (double)this.target.hurtTime, 10.0));
                        Minecraft.getMinecraft()
                           .getTextureManager()
                           .bindTexture(this.mc.getNetHandler().getPlayerInfo(this.target.getUniqueID()).getLocationSkin());
                        Gui.drawScaledCustomSizeModalRect(
                           this.x + 2.0 + this.hurtAnimation,
                           this.y + 2.0 + this.hurtAnimation,
                           8.0F,
                           8.0F,
                           8.0,
                           8.0,
                           30.0 - this.hurtAnimation * 2.0,
                           30.0 - this.hurtAnimation * 2.0,
                           64.0F,
                           64.0F
                        );
                     }
                  }

                  Fonts.getFont("rb-b")
                     .drawString(
                        this.target.getName(),
                        this.x + 2.0 + 30.0 + (width - 32.0) / 2.0 - Fonts.getFont("rb-b").getWidth(this.target.getName()) / 2.0,
                        this.y + 7.0,
                        ColorUtils.getAlphaColor(ColorUtils.getColor(this.textColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0), 100)
                     );
                  if (this.mc.thePlayer.getHealth() == this.target.getHealth()) {
                     Fonts.getFont("rb-b")
                        .drawString("Equal", this.x + 2.0 + 30.0 + (width - 32.0) / 2.0 - Fonts.getFont("rb-b").getWidth("Equal") / 2.0, this.y + 15.0, -157);
                  } else if (this.mc.thePlayer.getHealth() < this.target.getHealth()) {
                     Fonts.getFont("rb-b")
                        .drawString(
                           "Losing", this.x + 2.0 + 30.0 + (width - 32.0) / 2.0 - Fonts.getFont("rb-b").getWidth("Losing") / 2.0, this.y + 15.0, -46515
                        );
                  } else {
                     Fonts.getFont("rb-b")
                        .drawString(
                           "Winning", this.x + 2.0 + 30.0 + (width - 32.0) / 2.0 - Fonts.getFont("rb-b").getWidth("Winning") / 2.0, this.y + 15.0, -10223690
                        );
                  }

                  double size = width - 4.0 - 36.0;
                  if (this.rounded.isEnabled()) {
                     RenderUtils.drawRoundedRect(this.x + 36.0, this.y + 24.0, this.x + 36.0 + size, this.y + 30.0, -1879048192, 1.0F);
                  } else {
                     RenderUtils.drawRect(this.x + 36.0, this.y + 24.0, this.x + 36.0 + size, this.y + 30.0, -1879048192);
                  }

                  this.animation = (this.animation * 9.0 + size / (double)this.target.getMaxHealth() * (double)this.target.getHealth()) / 10.0;
                  int health = ColorUtils.getColor(this.healthColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
                  if (this.rounded.isEnabled()) {
                     RenderUtils.drawRoundedRect(this.x + 36.0, this.y + 24.0, this.x + 36.0 + this.animation, this.y + 30.0, health, 1.0F);
                  } else {
                     RenderUtils.drawRect(this.x + 36.0, this.y + 24.0, this.x + 36.0 + this.animation, this.y + 30.0, health);
                  }
               }
            default:
               GlStateManager.resetColor();
               GL11.glPopMatrix();
         }
      }
   }

   @EventListener
   public void onBlur(EventBlur e) {
      if (e.getType() == EventType.PRE) {
         e.setCancelled(true);
      } else if (this.target != null && this.blur.isEnabled()) {
         double width = 40.0 + Fonts.getFont("rb-b").getWidth(this.target.getName()) + 8.0;
         if (width < 110.0) {
            width = 110.0;
         }

         double height = 34.0;
         GL11.glPushMatrix();
         GL11.glTranslated(
            this.x - this.x * this.openAnimation + width / 2.0 - width / 2.0 * this.openAnimation,
            this.y - this.y * this.openAnimation + height / 2.0 - height / 2.0 * this.openAnimation,
            0.0
         );
         GL11.glScaled(this.openAnimation, this.openAnimation, 1.0);
         String var6;
         switch ((var6 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -1875215998:
               if (var6.equals("style 1")) {
                  int bg = -1;
                  if (this.rounded.isEnabled()) {
                     RenderUtils.drawRoundedRect(this.x, this.y, this.x + width, this.y + height, -1, 5.0F);
                  } else {
                     RenderUtils.drawRect(this.x, this.y, this.x + width, this.y + height, -1);
                  }
               }
            default:
               GlStateManager.resetColor();
               GL11.glPopMatrix();
         }
      }
   }

   @EventListener
   public void onBloom(EventBloom e) {
      if (e.getType() == EventType.PRE) {
         e.setCancelled(true);
      } else if (this.target != null && this.bloom.isEnabled()) {
         double width = 40.0 + Fonts.getFont("rb-b").getWidth(this.target.getName()) + 8.0;
         if (width < 110.0) {
            width = 110.0;
         }

         double height = 34.0;
         GL11.glPushMatrix();
         GL11.glTranslated(
            this.x - this.x * this.openAnimation + width / 2.0 - width / 2.0 * this.openAnimation,
            this.y - this.y * this.openAnimation + height / 2.0 - height / 2.0 * this.openAnimation,
            0.0
         );
         GL11.glScaled(this.openAnimation, this.openAnimation, 1.0);
         String var6;
         switch ((var6 = this.mode.getMode().toLowerCase()).hashCode()) {
            case -1875215998:
               if (var6.equals("style 1")) {
                  int bg = ColorUtils.getColor(this.bloomColor, (double)(System.nanoTime() / 1000000L), 0.0, 5.0);
                  if (this.rounded.isEnabled()) {
                     if (!this.backgroundColor.getMode().toLowerCase().equals("mix")) {
                        RenderUtils.drawRoundedRect(this.x, this.y, this.x + width, this.y + height, bg, 5.0F);
                     } else {
                        RenderUtils.drawMixedRoundedRect(
                           this.x, this.y, this.x + width, this.y + height, this.bloomColor.getMainColor(), this.bloomColor.getSecondaryColor(), 5.0
                        );
                     }
                  } else if (!this.backgroundColor.getMode().toLowerCase().equals("mix")) {
                     RenderUtils.drawRect(this.x, this.y, this.x + width, this.y + height, bg);
                  } else {
                     RenderUtils.drawMixedRect(
                        this.x, this.y, this.x + width, this.y + height, this.bloomColor.getMainColor(), this.bloomColor.getSecondaryColor()
                     );
                  }
               }
            default:
               GlStateManager.resetColor();
               GL11.glPopMatrix();
         }
      }
   }

   @EventListener
   public void onAttack(EventAttack e) {
      if (e.getEntity() instanceof EntityPlayer) {
         this.target = (EntityPlayer)e.getEntity();
         this.timer.reset();
         this.open = true;
      }
   }

   public void drawCircle(double x, double y, double radius, int color, double size, double from, double to) {
      RenderUtils.start2D();
      RenderUtils.color(color);
      GL11.glBegin(2);

      for (double i = from; i <= to; i++) {
         GL11.glVertex2d(x + Math.sin(Math.toRadians(i)) * radius, y - Math.cos(Math.toRadians(i)) * radius);
      }

      GL11.glEnd();
      RenderUtils.stop2D();
   }
}
