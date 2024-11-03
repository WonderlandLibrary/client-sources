package vestige.module.impl.visual;

import java.awt.Color;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.Scoreboard;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.opengl.GL11;
import vestige.Flap;
import vestige.font.VestigeFontRenderer;
import vestige.module.AlignType;
import vestige.module.Category;
import vestige.module.HUDModule;
import vestige.module.impl.combat.Killaura;
import vestige.module.impl.combat.Velocity;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.EnumModeSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.setting.impl.ModeSetting;
import vestige.shaders.impl.GaussianBlur;
import vestige.util.animation.Animation;
import vestige.util.animation.AnimationType;
import vestige.util.animation.AnimationUtil;
import vestige.util.misc.TimerUtil;
import vestige.util.render.DrawUtil;
import vestige.util.render.FontUtil;
import vestige.util.render.RenderUtils2;
import vestige.util.util.MathUtils;

public class TargetHUD extends HUDModule {
   private final ModeSetting mode = new ModeSetting("Mode", "Flap", new String[]{"Adjust", "Flap", "Ehxibition", "Astolfo"});
   private final IntegerSetting roundedpx = new IntegerSetting("Rounded Pixels", 0, 0, 50, 1);
   private final EnumModeSetting<AnimationType> animationType;
   private final IntegerSetting animationDuration;
   private final IntegerSetting healthBarDelay;
   private final BooleanSetting roundedHealth;
   private final BooleanSetting shadow;
   private final ModeSetting font;
   private Animation animation;
   private Killaura killauraModule;
   private ClientTheme theme;
   private EntityPlayer target;
   private final TimerUtil barTimer;
   private float renderedHealth;
   private VestigeFontRenderer productSans17;
   private VestigeFontRenderer productSan;
   private VestigeFontRenderer productSans;
   private VestigeFontRenderer productSansBold;
   private VestigeFontRenderer productSans172;
   private VestigeFontRenderer productSansBold2;
   private boolean hadTarget;
   public static int current$minX;
   public static int current$maxX;
   public static int current$minY;
   public static int current$maxY;
   public int totalhealth;
   private Velocity velocity;

   public TargetHUD() {
      super("TargetHUD", Category.VISUAL, 0.0D, 0.0D, 140, 46, AlignType.LEFT);
      this.animationType = AnimationUtil.getAnimationType(AnimationType.POP2);
      this.animationDuration = AnimationUtil.getAnimationDuration(250);
      this.healthBarDelay = new IntegerSetting("Healh bar delay", 750, 0, 2000, 25);
      this.roundedHealth = new BooleanSetting("Rounded health", true);
      this.shadow = new BooleanSetting("Shadow", true);
      this.font = FontUtil.getFontSetting();
      this.barTimer = new TimerUtil();
      this.addSettings(new AbstractSetting[]{this.mode, this.roundedpx, this.font, this.animationType, this.animationDuration, this.healthBarDelay, this.roundedHealth, this.shadow});
      this.setEnabledSilently(true);
      ScaledResolution sr = new ScaledResolution(mc);
      this.posX.setValue((double)(sr.getScaledWidth() / 2 + 300));
      this.posY.setValue(300.0D);
      this.animation = new Animation();
      this.animation.setAnimDuration((long)this.animationDuration.getValue());
      this.animation.setAnimType((AnimationType)this.animationType.getMode());
   }

   public void onClientStarted() {
      this.productSan = Flap.instance.getFontManager().getProductSan();
      this.productSans = Flap.instance.getFontManager().getProductSans();
      this.productSansBold = Flap.instance.getFontManager().getProductSansBold20();
      this.productSans172 = Flap.instance.getFontManager().getProductSans30();
      this.productSansBold2 = Flap.instance.getFontManager().getProductSansBold30();
      this.killauraModule = (Killaura)Flap.instance.getModuleManager().getModule(Killaura.class);
      this.theme = (ClientTheme)Flap.instance.getModuleManager().getModule(ClientTheme.class);
      this.productSans17 = Flap.instance.getFontManager().getProductSans17();
   }

   protected void renderModule(boolean inChat) {
      if (inChat) {
         this.animation.getTimer().setTimeElapsed((long)this.animationDuration.getValue());
         this.renderTargetHUD(mc.thePlayer, true);
         if (this.target == null) {
            this.target = mc.thePlayer;
         }
      } else if (this.isEnabled()) {
         boolean canRender = this.killauraModule.isEnabled() && this.killauraModule.getTarget() != null && this.killauraModule.getTarget() instanceof EntityPlayer;
         if (this.killauraModule.isEnabled() && this.killauraModule.getTarget() != null && this.killauraModule.getTarget() instanceof EntityPlayer) {
            this.target = (EntityPlayer)this.killauraModule.getTarget();
         }

         this.renderTargetHUD(this.target, canRender);
      } else {
         this.animation.getTimer().setTimeElapsed(0L);
      }

   }

   private void renderTargetHUD(EntityPlayer entity, boolean canRender) {
      GlStateManager.enableBlend();
      GlStateManager.blendFunc(770, 771);
      GlStateManager.enableTexture2D();
      GlStateManager.enableAlpha();
      this.animation.setAnimDuration((long)this.animationDuration.getValue());
      this.animation.setAnimType((AnimationType)this.animationType.getMode());
      this.animation.updateState(canRender);
      if (entity != null && canRender) {
         int x = (int)this.posX.getValue();
         int y = (int)this.posY.getValue();
         this.animation.updateState(canRender);
         this.animation.setAnimDuration((long)this.animationDuration.getValue());
         this.animation.setAnimType((AnimationType)this.animationType.getMode());
         float health;
         if (this.roundedHealth.isEnabled()) {
            float totalHealth = this.getHealthh(entity, true, true);
            health = (float)Math.round(totalHealth * 10.0F) / 10.0F;
         } else {
            health = this.getHealthh(entity, true, true);
         }

         if (!this.hadTarget) {
            this.renderedHealth = health;
         }

         this.totalhealth = (int)health;
         this.animation.render(() -> {
            if (health != this.renderedHealth) {
               this.renderedHealth = (float)((double)this.renderedHealth + (double)(health - this.renderedHealth) * Math.min(1.0D, (double)this.barTimer.getTimeElapsed() / (double)this.healthBarDelay.getValue()));
            } else {
               this.barTimer.reset();
            }

            int headOffset = 5;
            int maxHealthBarWidth = this.width - headOffset - 10;
            double healthBarWidth = (double)((float)maxHealthBarWidth * (this.renderedHealth / entity.getMaxHealth()));
            if (this.renderedHealth > 20.0F) {
               healthBarWidth = (double)(maxHealthBarWidth * 1);
            }

            double endAnimX = (double)(x + headOffset) + healthBarWidth;
            double endAnimXe = (double)(x + headOffset) + healthBarWidth / 1.6D;
            int headOffset2 = 5;
            int maxHealthBarWidth2 = 125 - headOffset2 - 10;
            double var10000 = (double)((float)maxHealthBarWidth2 * ((float)maxHealthBarWidth2 / entity.getMaxHealth()));
            int newWidth = (int)((double)this.width * 0.76D);
            int newHeight = (int)((double)this.height * 0.78D);
            int newX = x + (this.width - newWidth) / 2;
            int newY = y + (this.height - newHeight) / 2;
            double endAnimX2 = (double)(x + headOffset) + healthBarWidth - 27.0D;
            int animY = (int)((double)newY + (double)newHeight * 0.5D + 12.5D);
            String var24 = this.mode.getMode();
            byte var25 = -1;
            switch(var24.hashCode()) {
            case 2192277:
               if (var24.equals("Flap")) {
                  var25 = 1;
               }
               break;
            case 617402319:
               if (var24.equals("Ehxibition")) {
                  var25 = 2;
               }
               break;
            case 961091784:
               if (var24.equals("Astolfo")) {
                  var25 = 3;
               }
               break;
            case 1956520879:
               if (var24.equals("Adjust")) {
                  var25 = 0;
               }
            }

            int yex;
            switch(var25) {
            case 0:
               if (this.shadow.isEnabled()) {
                  RenderUtils2.drawBloomShadow((float)(newX + 3), (float)newY, (float)(newWidth + newX + 13), (float)(newHeight + newY), 15, 0, (new Color(0, 0, 0, 50)).getRGB(), false);
               }

               GaussianBlur.startBlur();
               RenderUtils2.drawBloomShadow((float)((double)newX + 1.9D), (float)newY, (float)(newX + newWidth + 13), (float)(newY + newHeight), 0, 0, -1, false);
               GaussianBlur.endBlur(4.0F, 2.0F);
               RenderUtils2.drawRect((double)newX + 1.9D, (double)newY, (double)(newX + newWidth + 13), (double)(newY + newHeight), (new Color(0, 0, 0, 80)).getRGB());

               for(double ixx = (double)(newX + headOffset); ixx < endAnimX; ++ixx) {
                  Gui.drawRect(ixx, (double)(animY - 1), ixx + 4.0D, (double)(animY + 2), this.theme.getColor((int)(200.0D + ixx * 5.0D)));
               }

               if (entity instanceof AbstractClientPlayer) {
                  AbstractClientPlayer playerx = (AbstractClientPlayer)entity;
                  double offset = -((double)playerx.hurtTime * 15.5D);
                  Color dynamicColor = new Color(255, (int)(255.0D + offset), (int)(255.0D + offset));
                  GlStateManager.color((float)dynamicColor.getRed() / 255.0F, (float)dynamicColor.getGreen() / 255.0F, (float)dynamicColor.getBlue() / 255.0F, (float)dynamicColor.getAlpha() / 255.0F);
                  DrawUtil.drawHead(((AbstractClientPlayer)entity).getLocationSkin(), newX + 5, newY + 3, 22, 22);
                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               }

               this.productSans17.drawStringWithShadow(entity.getGameProfile().getName(), (float)(newX + 34), (float)(newY + 6), -1);
               int armorX = newX + 32;
               int armorY = newY + 14;
               ItemStack heldItem = entity.getHeldItem();
               if (heldItem != null && heldItem.getItem() != null) {
                  GlStateManager.pushMatrix();
                  GlStateManager.scale(0.85D, 0.85D, 0.85D);
                  mc.getRenderItem().renderItemAndEffectIntoGUI(heldItem, (int)((double)armorX / 0.85D), (int)((double)armorY / 0.85D));
                  GlStateManager.popMatrix();
                  armorX += 14;
               }

               for(int i = 3; i >= 0; --i) {
                  ItemStack armorStack = entity.getCurrentArmor(i);
                  if (armorStack != null && armorStack.getItem() != null) {
                     GlStateManager.pushMatrix();
                     GlStateManager.disableLighting();
                     mc.getRenderItem().zLevel = -150.0F;
                     GlStateManager.scale(0.85D, 0.85D, 0.85D);
                     mc.getRenderItem().renderItemAndEffectIntoGUI(armorStack, (int)((double)armorX / 0.85D), (int)((double)armorY / 0.85D));
                     mc.getRenderItem().zLevel = 0.0F;
                     GlStateManager.enableLighting();
                     GlStateManager.popMatrix();
                     armorX += 14;
                  }
               }

               return;
            case 1:
               int width2 = 125;
               int height2 = 32;
               if (this.shadow.isEnabled()) {
                  RenderUtils2.drawBloomShadow((float)x, (float)y, (float)(x + width2), (float)(y + height2), 15, 6, (new Color(0, 0, 0, 86)).getRGB(), false);
               }

               RenderUtils2.drawRoundOutline((double)x, (double)y, (double)width2, (double)height2, 6.5D, 0.0D, new Color(0, 0, 0, 100), new Color(0, 0, 0, 0));
               Gui.drawRect((double)(newX + 15), (double)(animY - 9), (double)(newX + 83), (double)animY - 13.5D, (new Color(0, 0, 0, 100)).getRGB());

               for(double ix = (double)(newX + headOffset + 15); ix < endAnimX2; ++ix) {
                  Gui.drawRect(ix - 1.0D, (double)(animY - 9), ix - 5.0D, (double)animY - 13.5D, this.theme.getColor((int)(200.0D + ix * 5.0D)));
               }

               this.productSan.drawString(MathUtils.toPercentagerounded((double)health, 20.0D) + "%", (double)(newX + 86), (double)animY - 15.0D, this.theme.getColor((int)(200.0D + endAnimX2 * 5.0D)));
               this.productSans17.drawStringWithShadow(entity.getGameProfile().getName(), (float)(newX + 15), (float)(newY + 4), -1);
               if (entity instanceof AbstractClientPlayer) {
                  AbstractClientPlayer player = (AbstractClientPlayer)entity;
                  DrawUtil.drawHeadRounded(player, player.getLocationSkin(), x + 4, y + 4, 24, 24);
                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
               }
               break;
            case 2:
               if (this.target != null) {
                  String name = this.target.getDisplayName().getFormattedText();
                  yex = (int)this.posX.getValue();
                  int ye = (int)this.posY.getValue();
                  RenderUtils2.drawRect((double)yex, (double)ye, (double)(yex + 140), (double)(ye + 50), (new Color(0, 0, 0)).getRGB());
                  RenderUtils2.drawRect((double)yex + 0.5D, (double)ye + 0.5D, (double)yex + 0.5D + 139.0D, (double)ye + 0.5D + 49.0D, (new Color(60, 60, 60)).getRGB());
                  RenderUtils2.drawRect((double)yex + 1.5D, (double)ye + 1.5D, (double)yex + 1.5D + 137.0D, (double)ye + 1.5D + 47.0D, (new Color(0, 0, 0)).getRGB());
                  RenderUtils2.drawRect((double)(yex + 2), (double)(ye + 2), (double)(yex + 2 + 136), (double)(ye + 2 + 46), (new Color(25, 25, 24)).getRGB());
                  mc.fontRendererObj.drawString(name, yex + 40, ye + 6, Color.WHITE.getRGB());
                  GlStateManager.pushMatrix();
                  GlStateManager.scale(0.7D, 0.7D, 0.7D);
                  mc.fontRendererObj.drawString("HP: " + Math.round(this.target.getHealth()) + " | Dist: " + Math.round(mc.thePlayer.getDistanceToEntity(this.target)), (int)((double)(yex + 40) * 1.4285714285714286D), (int)((double)(ye + 17) * 1.4285714285714286D), Color.WHITE.getRGB());
                  GlStateManager.popMatrix();
                  double healthe = (double)Math.min((float)Math.round(this.target.getHealth()), this.target.getMaxHealth());
                  Color healthColor = getColor(this.target);
                  double x2 = (double)(yex + 40);
                  RenderUtils2.drawRect(x2, (double)(ye + 25), (double)(yex + 100 - 9), (double)(ye + 25 + 5), (new Color(healthColor.getRed(), healthColor.getGreen(), healthColor.getBlue(), 50)).getRGB());
                  RenderUtils2.drawRect(x2, (double)(ye + 25), (double)yex + 91.0D * (healthe / (double)this.target.getMaxHealth()), (double)(ye + 25 + 6), healthColor.getRGB());
                  RenderUtils2.drawRect(x2, (double)(ye + 25), (double)(yex + 91), (double)(ye + 25 + 1), Color.BLACK.getRGB());
                  RenderUtils2.drawRect(x2, (double)(ye + 30), (double)(yex + 91), (double)(ye + 30 + 1), Color.BLACK.getRGB());

                  for(int ixxxx = 0; ixxxx < 6; ++ixxxx) {
                     RenderUtils2.drawRect(x2 + (double)(10 * ixxxx), (double)(ye + 25), x2 + (double)(10 * ixxxx) + 1.0D, (double)(ye + 25 + 6), Color.BLACK.getRGB());
                  }

                  RenderUtils2.renderItemIcon(x2, (double)(ye + 31), this.target.getHeldItem());
                  RenderUtils2.renderItemIcon(x2 + 15.0D, (double)(ye + 31), this.target.getEquipmentInSlot(4));
                  RenderUtils2.renderItemIcon(x2 + 30.0D, (double)(ye + 31), this.target.getEquipmentInSlot(3));
                  RenderUtils2.renderItemIcon(x2 + 45.0D, (double)(ye + 31), this.target.getEquipmentInSlot(2));
                  RenderUtils2.renderItemIcon(x2 + 60.0D, (double)(ye + 31), this.target.getEquipmentInSlot(1));
                  GlStateManager.pushMatrix();
                  GlStateManager.scale(0.4D, 0.4D, 0.4D);
                  GlStateManager.translate((double)(yex + 20) * 2.5D, (double)(ye + 44) * 2.5D, 100.0D);
                  drawModel(this.target.rotationYaw, this.target.rotationPitch, this.target);
                  GlStateManager.popMatrix();
               }
               break;
            case 3:
               int xe = (int)this.posX.getValue();
               yex = (int)this.posY.getValue();
               float widthee = (float)Math.max(115, mc.fontRendererObj.getStringWidth(this.target.getName()) + 60);
               int heightee = 43;
               if (this.target != null) {
                  RenderUtils2.drawRect((double)xe, (double)yex, (double)((float)xe + widthee), (double)(yex + heightee), (new Color(0, 0, 0, 150)).getRGB());
                  GlStateManager.pushMatrix();
                  GlStateManager.scale(0.4D, 0.4D, 0.4D);
                  GlStateManager.translate((double)(xe + 13) * 2.5D, (double)(yex + 41) * 2.5D, 100.0D);
                  drawModel(this.target.rotationYaw, this.target.rotationPitch, this.target);
                  GlStateManager.popMatrix();
                  mc.fontRendererObj.drawString(this.target.getName(), xe + 27, yex + 4, -1);
                  float scale = 1.75F;
                  GlStateManager.pushMatrix();
                  GlStateManager.scale(scale, scale, scale);
                  mc.fontRendererObj.drawStringWithShadow(health + " ❤", (float)xe / scale + 15.0F, (float)yex / scale + 9.0F, this.theme.getColor(1));
                  GlStateManager.popMatrix();

                  for(double ixxx = (double)(newX + 10); ixxx < endAnimXe + (double)(mc.fontRendererObj.getStringWidth(this.target.getName()) / 2); ++ixxx) {
                     Gui.drawRect(ixxx, (double)(animY - 2), ixxx + 4.0D, (double)(animY + 4), this.theme.getColor((int)(200.0D + ixxx * 5.0D)));
                  }

                  mc.fontRendererObj.drawStringWithShadow("", (float)xe / scale + 15.0F, (float)yex / scale + 9.0F, -1);
               }
            }

         }, (float)x, (float)y, (float)(x + this.width), (float)(y + this.height));
         this.hadTarget = true;
      } else {
         this.hadTarget = false;
      }
   }

   private static Color getColor(@NotNull EntityLivingBase target) {
      if (target == null) {
         $$$reportNull$$$0(0);
      }

      Color healthColor = new Color(0, 165, 0);
      if ((double)target.getHealth() < (double)target.getMaxHealth() / 1.5D) {
         healthColor = new Color(200, 200, 0);
      }

      if ((double)target.getHealth() < (double)target.getMaxHealth() / 2.5D) {
         healthColor = new Color(200, 155, 0);
      }

      if (target.getHealth() < target.getMaxHealth() / 4.0F) {
         healthColor = new Color(120, 0, 0);
      }

      return healthColor;
   }

   public static void drawModel(float yaw, float pitch, @NotNull EntityLivingBase entityLivingBase) {
      if (entityLivingBase == null) {
         $$$reportNull$$$0(1);
      }

      GlStateManager.resetColor();
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.enableColorMaterial();
      GlStateManager.pushMatrix();
      GlStateManager.translate(0.0F, 0.0F, 50.0F);
      GlStateManager.scale(-50.0F, 50.0F, 50.0F);
      GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
      float renderYawOffset = entityLivingBase.renderYawOffset;
      float rotationYaw = entityLivingBase.rotationYaw;
      float rotationPitch = entityLivingBase.rotationPitch;
      float prevRotationYawHead = entityLivingBase.prevRotationYawHead;
      float rotationYawHead = entityLivingBase.rotationYawHead;
      GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
      RenderHelper.enableStandardItemLighting();
      GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
      GlStateManager.rotate((float)(-Math.atan((double)(pitch / 40.0F)) * 20.0D), 1.0F, 0.0F, 0.0F);
      entityLivingBase.renderYawOffset = yaw - 0.4F;
      entityLivingBase.rotationYaw = yaw - 0.2F;
      entityLivingBase.rotationPitch = pitch;
      entityLivingBase.rotationYawHead = entityLivingBase.rotationYaw;
      entityLivingBase.prevRotationYawHead = entityLivingBase.rotationYaw;
      GlStateManager.translate(0.0F, 0.0F, 0.0F);
      RenderManager renderManager = mc.getRenderManager();
      renderManager.setPlayerViewY(180.0F);
      renderManager.setRenderShadow(false);
      renderManager.renderEntityWithPosYaw(entityLivingBase, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
      renderManager.setRenderShadow(true);
      entityLivingBase.renderYawOffset = renderYawOffset;
      entityLivingBase.rotationYaw = rotationYaw;
      entityLivingBase.rotationPitch = rotationPitch;
      entityLivingBase.prevRotationYawHead = prevRotationYawHead;
      entityLivingBase.rotationYawHead = rotationYawHead;
      GlStateManager.popMatrix();
      RenderHelper.disableStandardItemLighting();
      GlStateManager.disableRescaleNormal();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.resetColor();
   }

   private float getHealthh(EntityLivingBase e, boolean fs, boolean a) {
      if (e == null) {
         return 0.0F;
      } else {
         if (fs && e instanceof EntityPlayer) {
            Scoreboard scoreboard = ((EntityPlayer)e).getWorldScoreboard();
            Score objective = scoreboard.getValueFromObjective(((EntityPlayer) e).getName(), scoreboard.getObjectiveInDisplaySlot(2));
            int scoreboardHealth = objective.getScorePoints();
            if (scoreboardHealth > 0) {
               return (float)scoreboardHealth;
            }
         }

         float health = e.getHealth();
         if (a) {
            health += e.getAbsorptionAmount();
         }

         return health > 0.0F ? health : 20.0F;
      }
   }

   // $FF: synthetic method
   private static void $$$reportNull$$$0(int var0) {
      Object[] var10001 = new Object[3];
      switch(var0) {
      case 0:
      default:
         var10001[0] = "target";
         break;
      case 1:
         var10001[0] = "entityLivingBase";
      }

      var10001[1] = "vestige/module/impl/visual/TargetHUD";
      switch(var0) {
      case 0:
      default:
         var10001[2] = "getColor";
         break;
      case 1:
         var10001[2] = "drawModel";
      }

      throw new IllegalArgumentException(String.format("Argument for @NotNull parameter '%s' of %s.%s must not be null", var10001));
   }
}
