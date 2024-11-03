package net.augustus.modules.render;

import java.awt.Color;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventTick;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.modules.combat.AntiBot;
import net.augustus.settings.BooleanValue;
import net.augustus.settings.BooleansSetting;
import net.augustus.settings.ColorSetting;
import net.augustus.settings.DoubleValue;
import net.augustus.settings.Setting;
import net.augustus.settings.StringValue;
import net.augustus.utils.GuiUtils;
import net.augustus.utils.RainbowUtil;
import net.augustus.utils.RenderUtil;
import net.augustus.utils.ResourceUtil;
import net.augustus.utils.skid.idek.Esp2DUtil;
import net.augustus.utils.skid.lorious.BlurUtil;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ESP extends Module {
   public final java.util.ArrayList<EntityLivingBase> livingBases = new java.util.ArrayList<>();
   private final RainbowUtil rainbowUtil = new RainbowUtil();
   public StringValue mode = new StringValue(7, "Mode", this, "Box", new String[]{"Box", "FakeCorner", "Fake2D", "Vanilla", "Other2D", "Hase"});
   public ColorSetting color = new ColorSetting(2, "Color", this, new Color(21, 121, 230, 65));
   public ColorSetting outlineColor = new ColorSetting(9, "Color", this, new Color(21, 121, 230, 65));
   public BooleanValue rainbow = new BooleanValue(11, "Rainbow", this, false);
   public DoubleValue rainbowSpeed = new DoubleValue(12, "RainbowSpeed", this, 55.0, 0.0, 1000.0, 0);
   public DoubleValue rainbowAlpha = new DoubleValue(13, "RainbowAlpha", this, 80.0, 0.0, 255.0, 0);
   public BooleanValue teamColor = new BooleanValue(10, "TeamColor", this, true);
   public BooleanValue otherColorOnHit = new BooleanValue(15, "HitColor", this, true);
   public ColorSetting hitColor = new ColorSetting(14, "HitColor", this, new Color(255, 0, 0));
   public BooleanValue player = new BooleanValue(3, "Player", this, true);
   public BooleanValue mob = new BooleanValue(4, "Mob", this, true);
   public BooleanValue animal = new BooleanValue(5, "Animal", this, false);
   public BooleanValue invisible = new BooleanValue(6, "Invisible", this, false);
   public DoubleValue lineWidth = new DoubleValue(1, "LineWidth", this, 6.0, 0.0, 15.0, 0);
   public BooleanValue blur2D = new BooleanValue(4, "Blur2D", this, false);
   public BooleansSetting entities = new BooleansSetting(8, "Entities", this, new Setting[]{this.player, this.mob, this.animal, this.invisible});

   public ESP() {
      super("ESP", new Color(47, 134, 124, 255), Categorys.RENDER);
   }

   @EventTarget
   public void onEventTick(EventTick eventTick) {
      if (mc.theWorld != null) {
    	  if(mm.arrayList.mode.getSelected().equalsIgnoreCase("Default")) {
	      this.setDisplayName(this.getName() + "  " + this.mode.getSelected());
	 }else {
		 this.setDisplayName(this.getName() + this.mode.getSelected());
	 }
         this.livingBases.clear();
         if (!this.mode.getSelected().equals("Vanilla")) {
            for(Object object : mc.theWorld.loadedEntityList) {
               if (object instanceof EntityLivingBase) {
                  EntityLivingBase entity = (EntityLivingBase)object;
                  if (entity.isInvisible()) {
                     if (this.invisible.getBoolean()) {
                        if (entity instanceof EntityPlayer && this.player.getBoolean() && mc.thePlayer != entity) {
                           if (mm.antiBot.isToggled()) {
                              if (!AntiBot.bots.contains((EntityPlayer)entity)) {
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
                     if (entity instanceof EntityPlayer && this.player.getBoolean() && mc.thePlayer != entity) {
                        if (mm.antiBot.isToggled()) {
                           if (!AntiBot.bots.contains((EntityPlayer)entity)) {
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
               this.rainbowSpeed.getValue() == 1000.0 ? (float)(this.rainbowSpeed.getValue() * 1.0E-5F) : (float)(this.rainbowSpeed.getValue() * 1.0E-6F),
               (int)this.rainbowAlpha.getValue()
            );
      }

      if (!this.livingBases.isEmpty()) {
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
   }

   private void render(EntityLivingBase entity, float red, float green, float blue, float alpha) {
      float lineWidth = (float)this.lineWidth.getValue() / 2.0F;
      int i = 16777215;
      if (this.teamColor.getBoolean() && entity instanceof EntityPlayer) {
         ScorePlayerTeam scoreplayerteam = (ScorePlayerTeam)entity.getTeam();
         if (scoreplayerteam != null) {
            String s = FontRenderer.getFormatFromString(scoreplayerteam.getColorPrefix());
            if (s.length() >= 2) {
               i = mc.fontRendererObj.getColorCode(s.charAt(1));
            }
         }

         red = (float)(i >> 16 & 0xFF) / 255.0F;
         green = (float)(i >> 8 & 0xFF) / 255.0F;
         blue = (float)(i & 0xFF) / 255.0F;
      }

      if (entity.hurtTime > 0 && this.otherColorOnHit.getBoolean()) {
         red = (float)this.hitColor.getColor().getRed() / 255.0F;
         green = (float)this.hitColor.getColor().getGreen() / 255.0F;
         blue = (float)this.hitColor.getColor().getBlue() / 255.0F;
      }

      if (mc.thePlayer.getDistanceToEntity(entity) > 1.0F) {
         double d0 = (double)(1.0F - mc.thePlayer.getDistanceToEntity(entity) / 20.0F);
         if (d0 < 0.3) {
            d0 = 0.3;
         }

         lineWidth = (float)((double)lineWidth * d0);
      }
      Color other2dcolor = new Color(red, green, blue, alpha);
      String var11 = this.mode.getSelected();
      switch(var11) {
         case "Hase": {
            if (!entity.isDead && entity != mc.thePlayer && Esp2DUtil.isInViewFrustrum(entity)) {
               final double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosX;
               final double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosY);
               final double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - (mc.getRenderManager()).renderPosZ;

               GL11.glPushMatrix();
               GL11.glTranslated(x, y - 0.2, z);
               GlStateManager.disableDepth();

               GL11.glRotated(-(mc.getRenderManager()).playerViewY, 0.0D, 1.0D, 0.0D);

               final float width = 1.1f;
               final float height = 2.2f;

               //draw2DBox(width, height, lineWidth, 0.04f, new Color(0, 0, 0, 165));

               //draw2DBox(width, height, lineWidth, 0, tm.getTheme().getSecondary());
               Esp2DUtil.drawImage(new ResourceLocation("client   /IchHaseDich.png"), (int) width, (int) height, (int) (-width * 2), (int) -height);

               GlStateManager.enableDepth();
               GL11.glPopMatrix();
            }
            break;
         }
         case "Box":
            RenderUtil.drawEntityESP(entity, red, green, blue, alpha, 1.0F, lineWidth);
            break;
         case "FakeCorner":
            RenderUtil.drawCornerESP(entity, red, green, blue);
            break;
         case "Fake2D":
            RenderUtil.drawFake2DESP(entity, red, green, blue);
            break;
         case "Other2D": {
            double x = entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosX;
            double y = entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosY;
            double z = entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * mc.timer.renderPartialTicks - mc.getRenderManager().renderPosZ;
            GL11.glPushMatrix();
            GL11.glTranslated(x, y - 0.2D, z);
            GL11.glScalef(0.03F, 0.03F, 0.03F);
            GL11.glRotated(-(mc.getRenderManager()).playerViewY, 0.0D, 1.0D, 0.0D);
            GlStateManager.disableDepth();
            Gui.drawRect(-20, -1, -26, 75, Color.BLACK.hashCode());
            Gui.drawRect(-21, 0, -25, 74, other2dcolor.getRGB());
            Gui.drawRect(20, -1, 26, 75, Color.BLACK.hashCode());
            Gui.drawRect(21, 0, 25, 74, other2dcolor.getRGB());
            Gui.drawRect(-20, -1, 21, 5, Color.BLACK.hashCode());
            Gui.drawRect(-21, 0, 24, 4, other2dcolor.getRGB());
            Gui.drawRect(-20, 70, 21, 75, Color.BLACK.hashCode());
            Gui.drawRect(-21, 71, 25, 74, other2dcolor.getRGB());
            GlStateManager.enableDepth();
            GL11.glPopMatrix();
            break;
         }
      }
   }

   public boolean canRender(EntityLivingBase entity) {
      if (entity.isInvisible()) {
         if (!this.invisible.getBoolean()) {
            return false;
         } else if (entity instanceof EntityPlayer && this.player.getBoolean() && mc.thePlayer != entity) {
            return true;
         } else if (entity instanceof EntityMob && this.mob.getBoolean()) {
            return true;
         } else {
            return entity instanceof EntityAnimal && this.animal.getBoolean();
         }
      } else if (entity instanceof EntityPlayer && this.player.getBoolean() && mc.thePlayer != entity) {
         return true;
      } else if (entity instanceof EntityMob && this.mob.getBoolean()) {
         return true;
      } else {
         return entity instanceof EntityAnimal && this.animal.getBoolean();
      }
   }

   public RainbowUtil getRainbowUtil() {
      return this.rainbowUtil;
   }
}
