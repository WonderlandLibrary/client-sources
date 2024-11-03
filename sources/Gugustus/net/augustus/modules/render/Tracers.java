package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.modules.combat.AntiBot;
import net.augustus.modules.misc.MidClick;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.BooleansSetting;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.settings.StringValue;
import net.augustus.utils.RainbowUtil;
import net.augustus.utils.RenderUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import org.lwjgl.opengl.GL11;

public class Tracers extends Module {
   private final RainbowUtil rainbowUtil = new RainbowUtil();
   public ColorSetting color = new ColorSetting(2, "Color", this, new Color(21, 121, 230, 65));
   public StringValue mode = new StringValue(7, "Mode", this, "Feet", new String[]{"Feet", "CrossHair"});
   public BooleanValue staticColor = new BooleanValue(9, "StaticColor", this, false);
   public BooleanValue rainbow = new BooleanValue(11, "Rainbow", this, false);
   public DoubleValue rainbowSpeed = new DoubleValue(12, "RainbowSpeed", this, 55.0, 0.0, 1000.0, 0);
   public BooleanValue player = new BooleanValue(3, "Player", this, true);
   public BooleanValue mob = new BooleanValue(4, "Mob", this, true);
   public BooleanValue animal = new BooleanValue(5, "Animal", this, false);
   public BooleanValue invisible = new BooleanValue(6, "Invisible", this, false);
   public DoubleValue lineWidth = new DoubleValue(1, "LineWidth", this, 6.0, 0.1, 15.0, 1);
   public BooleansSetting entities = new BooleansSetting(8, "Entities", this, new Setting[]{this.player, this.mob, this.animal, this.invisible});
   private boolean bobbing;
   private java.util.ArrayList<EntityLivingBase> livingBases = new java.util.ArrayList<>();

   public Tracers() {
      super("Tracers", new Color(12, 58, 133), Categorys.RENDER);
   }

   @Override
   public void onEnable() {
      this.bobbing = mc.gameSettings.viewBobbing;
      mc.gameSettings.viewBobbing = false;
   }

   @Override
   public void onDisable() {
      mc.gameSettings.viewBobbing = this.bobbing;
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      this.livingBases = new java.util.ArrayList<>();

      for(Object object : mc.theWorld.loadedEntityList) {
         if (object instanceof EntityLivingBase) {
            EntityLivingBase entity = (EntityLivingBase)object;
            if (entity.isInvisible()) {
               if (this.invisible.getBoolean()) {
                  if (entity instanceof EntityPlayer
                     && this.player.getBoolean()
                     && mc.thePlayer != entity
                     && !AntiBot.bots.contains(entity)
                     && !mm.teams.getTeammates().contains(entity)) {
                     if (MidClick.friends.contains(entity.getName())) {
                        if (mm.midClick.noFiends) {
                           this.livingBases.add(entity);
                        }
                     } else {
                        this.livingBases.add(entity);
                     }
                  }

                  if (entity instanceof EntityMob && this.mob.getBoolean()) {
                     this.livingBases.add(entity);
                  }

                  if (entity instanceof EntityAnimal && this.animal.getBoolean()) {
                     this.livingBases.add(entity);
                  }
               }
            } else {
               if (entity instanceof EntityPlayer
                  && this.player.getBoolean()
                  && mc.thePlayer != entity
                  && !AntiBot.bots.contains(entity)
                  && !MidClick.friends.contains(entity.getName())
                  && !mm.teams.getTeammates().contains(entity)) {
                  this.livingBases.add(entity);
               }

               if (entity instanceof EntityMob && this.mob.getBoolean()) {
                  this.livingBases.add(entity);
               }

               if (entity instanceof EntityAnimal && this.animal.getBoolean()) {
                  this.livingBases.add(entity);
               }
            }
         }
      }
   }

   @EventTarget
   public void onEventRender3D(EventRender3D eventRender3D) {
      if (this.rainbow.getBoolean()) {
         this.rainbowUtil
            .updateRainbow(
               this.rainbowSpeed.getValue() == 1000.0 ? (float)(this.rainbowSpeed.getValue() * 1.0E-5F) : (float)(this.rainbowSpeed.getValue() * 1.0E-6F), 255
            );
      }

      GL11.glEnable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glEnable(2848);
      GL11.glDisable(2929);
      GL11.glDisable(3553);
      GlStateManager.disableCull();
      GL11.glDepthMask(false);
      float red = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getRed() / 255.0F : (float)this.color.getColor().getRed() / 225.0F;
      float green = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getGreen() / 255.0F : (float)this.color.getColor().getGreen() / 225.0F;
      float blue = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getBlue() / 255.0F : (float)this.color.getColor().getBlue() / 225.0F;
      float alpha = this.rainbow.getBoolean() ? (float)this.rainbowUtil.getColor().getAlpha() / 255.0F : (float)this.color.getColor().getAlpha() / 225.0F;

      for(EntityLivingBase livingBase : this.livingBases) {
         this.render(livingBase, red, green, blue, alpha);
      }

      GL11.glDepthMask(true);
      GlStateManager.enableCull();
      GL11.glEnable(3553);
      GL11.glEnable(2929);
      GL11.glDisable(3042);
      GL11.glBlendFunc(770, 771);
      GL11.glDisable(2848);
   }

   private void render(EntityLivingBase e, float red, float green, float blue, float alpha) {
      float lineWidth = (float)this.lineWidth.getValue() / 2.0F;
      if (!this.staticColor.getBoolean() && !this.rainbow.getBoolean()) {
         double dist = (double)(Math.min(Math.max(mc.thePlayer.getDistanceToEntity(e), 6.0F), 36.0F) - 6.0F);
         Color color = new Color(
            this.color.getColor().getRed(), this.color.getColor().getGreen(), this.color.getColor().getBlue(), this.color.getColor().getAlpha()
         );
         float[] hsbColor = Color.RGBtoHSB(color.getRed(), color.getGreen(), color.getBlue(), null);
         int colorRGB = Color.HSBtoRGB((float)(0.011415525F * dist), hsbColor[1], hsbColor[2]);
         float f = (float)(colorRGB >> 24 & 0xFF) / 255.0F;
         red = (float)(colorRGB >> 16 & 0xFF) / 255.0F;
         green = (float)(colorRGB >> 8 & 0xFF) / 255.0F;
         blue = (float)(colorRGB & 0xFF) / 255.0F;
      }

      RenderUtil.drawTracers(e, red, green, blue, alpha, lineWidth, this.mode.getSelected());
   }
}
