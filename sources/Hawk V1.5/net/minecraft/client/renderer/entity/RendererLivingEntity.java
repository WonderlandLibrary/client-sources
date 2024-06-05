package net.minecraft.client.renderer.entity;

import com.google.common.collect.Lists;
import hawk.Client;
import hawk.events.listeners.EventRenderPlayer;
import java.nio.FloatBuffer;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import optifine.Config;
import optifine.Reflector;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.GL11;
import shadersmod.client.Shaders;

public abstract class RendererLivingEntity extends Render {
   public static float NAME_TAG_RANGE_SNEAK = 32.0F;
   protected boolean field_177098_i = false;
   protected List field_177097_h = Lists.newArrayList();
   private static final String __OBFID = "CL_00001012";
   private static final Logger logger = LogManager.getLogger();
   private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);
   protected FloatBuffer field_177095_g = GLAllocation.createDirectFloatBuffer(4);
   protected ModelBase mainModel;
   public static float NAME_TAG_RANGE = 64.0F;

   protected void func_180565_e() {
      GlStateManager.enableLighting();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.func_179098_w();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.func_179098_w();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   public void passSpecialRender(EntityLivingBase var1, double var2, double var4, double var6) {
      if (!Reflector.RenderLivingEvent_Specials_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Pre_Constructor, var1, this, var2, var4, var6)) {
         if (this.canRenderName(var1)) {
            double var8 = var1.getDistanceSqToEntity(this.renderManager.livingPlayer);
            float var10 = var1.isSneaking() ? NAME_TAG_RANGE_SNEAK : NAME_TAG_RANGE;
            if (var8 < (double)(var10 * var10)) {
               String var11 = var1.getDisplayName().getFormattedText();
               float var12 = 0.02666667F;
               GlStateManager.alphaFunc(516, 0.1F);
               if (var1.isSneaking()) {
                  FontRenderer var13 = this.getFontRendererFromRenderManager();
                  GlStateManager.pushMatrix();
                  GlStateManager.translate((float)var2, (float)var4 + var1.height + 0.5F - (var1.isChild() ? var1.height / 2.0F : 0.0F), (float)var6);
                  GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                  GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                  GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                  GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
                  GlStateManager.translate(0.0F, 9.374999F, 0.0F);
                  GlStateManager.disableLighting();
                  GlStateManager.depthMask(false);
                  GlStateManager.enableBlend();
                  GlStateManager.func_179090_x();
                  GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                  Tessellator var14 = Tessellator.getInstance();
                  WorldRenderer var15 = var14.getWorldRenderer();
                  var15.startDrawingQuads();
                  int var16 = var13.getStringWidth(var11) / 2;
                  var15.func_178960_a(0.0F, 0.0F, 0.0F, 0.25F);
                  var15.addVertex((double)(-var16 - 1), -1.0D, 0.0D);
                  var15.addVertex((double)(-var16 - 1), 8.0D, 0.0D);
                  var15.addVertex((double)(var16 + 1), 8.0D, 0.0D);
                  var15.addVertex((double)(var16 + 1), -1.0D, 0.0D);
                  var14.draw();
                  GlStateManager.func_179098_w();
                  GlStateManager.depthMask(true);
                  var13.drawString(var11, (double)(-var13.getStringWidth(var11) / 2), 0.0D, 553648127);
                  GlStateManager.enableLighting();
                  GlStateManager.disableBlend();
                  GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                  GlStateManager.popMatrix();
               } else {
                  this.func_177069_a(var1, var2, var4 - (var1.isChild() ? (double)(var1.height / 2.0F) : 0.0D), var6, var11, 0.02666667F, var8);
               }
            }
         }

         if (Reflector.RenderLivingEvent_Specials_Post_Constructor.exists() && !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Specials_Post_Constructor, var1, this, var2, var4, var6)) {
         }
      }

   }

   protected void renderLivingAt(EntityLivingBase var1, double var2, double var4, double var6) {
      GlStateManager.translate((float)var2, (float)var4, (float)var6);
   }

   public void func_177086_a(boolean var1) {
      this.field_177098_i = var1;
   }

   protected boolean func_177088_c(EntityLivingBase var1) {
      int var2 = 16777215;
      if (var1 instanceof EntityPlayer) {
         ScorePlayerTeam var3 = (ScorePlayerTeam)var1.getTeam();
         if (var3 != null) {
            String var4 = FontRenderer.getFormatFromString(var3.getColorPrefix());
            if (var4.length() >= 2) {
               var2 = this.getFontRendererFromRenderManager().func_175064_b(var4.charAt(1));
            }
         }
      }

      float var6 = (float)(var2 >> 16 & 255) / 255.0F;
      float var7 = (float)(var2 >> 8 & 255) / 255.0F;
      float var5 = (float)(var2 & 255) / 255.0F;
      GlStateManager.disableLighting();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.color(var6, var7, var5, 1.0F);
      GlStateManager.func_179090_x();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.func_179090_x();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      return true;
   }

   protected boolean func_177090_c(EntityLivingBase var1, float var2) {
      return this.func_177092_a(var1, var2, true);
   }

   protected void func_177093_a(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      Iterator var9 = this.field_177097_h.iterator();

      while(var9.hasNext()) {
         LayerRenderer var10 = (LayerRenderer)var9.next();
         boolean var11 = this.func_177092_a(var1, var4, var10.shouldCombineTextures());
         var10.doRenderLayer(var1, var2, var3, var4, var5, var6, var7, var8);
         if (var11) {
            this.func_177091_f();
         }
      }

   }

   public RendererLivingEntity(RenderManager var1, ModelBase var2, float var3) {
      super(var1);
      this.mainModel = var2;
      this.shadowSize = var3;
   }

   public ModelBase getMainModel() {
      return this.mainModel;
   }

   public void doRender(Entity var1, double var2, double var4, double var6, float var8, float var9) {
      this.doRender((EntityLivingBase)var1, var2, var4, var6, var8, var9);
   }

   protected float getDeathMaxRotation(EntityLivingBase var1) {
      return 90.0F;
   }

   protected boolean func_177070_b(Entity var1) {
      return this.canRenderName((EntityLivingBase)var1);
   }

   public void func_177067_a(Entity var1, double var2, double var4, double var6) {
      this.passSpecialRender((EntityLivingBase)var1, var2, var4, var6);
   }

   public boolean addLayer(LayerRenderer var1) {
      return this.field_177097_h.add(var1);
   }

   protected float getSwingProgress(EntityLivingBase var1, float var2) {
      return var1.getSwingProgress(var2);
   }

   protected boolean canRenderName(EntityLivingBase var1) {
      EntityPlayerSP var2 = Minecraft.getMinecraft().thePlayer;
      if (var1 instanceof EntityPlayer && var1 != var2) {
         Team var3 = var1.getTeam();
         Team var4 = var2.getTeam();
         if (var3 != null) {
            Team.EnumVisible var5 = var3.func_178770_i();
            switch(var5) {
            case ALWAYS:
               return true;
            case NEVER:
               return false;
            case HIDE_FOR_OTHER_TEAMS:
               if (var4 != null && !var3.isSameTeam(var4)) {
                  return false;
               }

               return true;
            case HIDE_FOR_OWN_TEAM:
               if (var4 != null && var3.isSameTeam(var4)) {
                  return false;
               }

               return true;
            default:
               return true;
            }
         }
      }

      return Minecraft.isGuiEnabled() && var1 != this.renderManager.livingPlayer && !var1.isInvisibleToPlayer(var2) && var1.riddenByEntity == null;
   }

   protected void rotateCorpse(EntityLivingBase var1, float var2, float var3, float var4) {
      GlStateManager.rotate(180.0F - var3, 0.0F, 1.0F, 0.0F);
      if (var1.deathTime > 0) {
         float var5 = ((float)var1.deathTime + var4 - 1.0F) / 20.0F * 1.6F;
         var5 = MathHelper.sqrt_float(var5);
         if (var5 > 1.0F) {
            var5 = 1.0F;
         }

         GlStateManager.rotate(var5 * this.getDeathMaxRotation(var1), 0.0F, 0.0F, 1.0F);
      } else {
         String var6 = EnumChatFormatting.getTextWithoutFormattingCodes(var1.getName());
         if (var6 != null && (var6.equals("Dinnerbone") || var6.equals("Grumm")) && (!(var1 instanceof EntityPlayer) || ((EntityPlayer)var1).func_175148_a(EnumPlayerModelParts.CAPE))) {
            GlStateManager.translate(0.0F, var1.height + 0.1F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         }
      }

   }

   protected boolean func_177092_a(EntityLivingBase var1, float var2, boolean var3) {
      float var4 = var1.getBrightness(var2);
      int var5 = this.getColorMultiplier(var1, var4, var2);
      boolean var6 = (var5 >> 24 & 255) > 0;
      boolean var7 = var1.hurtTime > 0 || var1.deathTime > 0;
      if (!var6 && !var7) {
         return false;
      } else if (!var6 && !var3) {
         return false;
      } else {
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
         GlStateManager.func_179098_w();
         GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.defaultTexUnit);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.defaultTexUnit);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
         GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GlStateManager.func_179098_w();
         GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, OpenGlHelper.field_176094_t);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176092_v);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176080_A, OpenGlHelper.field_176092_v);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176076_D, 770);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
         this.field_177095_g.position(0);
         if (var7) {
            this.field_177095_g.put(1.0F);
            this.field_177095_g.put(0.0F);
            this.field_177095_g.put(0.0F);
            this.field_177095_g.put(0.3F);
            if (Config.isShaders()) {
               Shaders.setEntityColor(1.0F, 0.0F, 0.0F, 0.3F);
            }
         } else {
            float var8 = (float)(var5 >> 24 & 255) / 255.0F;
            float var9 = (float)(var5 >> 16 & 255) / 255.0F;
            float var10 = (float)(var5 >> 8 & 255) / 255.0F;
            float var11 = (float)(var5 & 255) / 255.0F;
            this.field_177095_g.put(var9);
            this.field_177095_g.put(var10);
            this.field_177095_g.put(var11);
            this.field_177095_g.put(1.0F - var8);
            if (Config.isShaders()) {
               Shaders.setEntityColor(var9, var10, var11, 1.0F - var8);
            }
         }

         this.field_177095_g.flip();
         GL11.glTexEnv(8960, 8705, this.field_177095_g);
         GlStateManager.setActiveTexture(OpenGlHelper.field_176096_r);
         GlStateManager.func_179098_w();
         GlStateManager.func_179144_i(field_177096_e.getGlTextureId());
         GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.field_176091_w);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.lightmapTexUnit);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 7681);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.field_176091_w);
         GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
         return true;
      }
   }

   protected float interpolateRotation(float var1, float var2, float var3) {
      float var4;
      for(var4 = var2 - var1; var4 < -180.0F; var4 += 360.0F) {
      }

      while(var4 >= 180.0F) {
         var4 -= 360.0F;
      }

      return var1 + var3 * var4;
   }

   public void doRender(EntityLivingBase var1, double var2, double var4, double var6, float var8, float var9) {
      if (!Reflector.RenderLivingEvent_Pre_Constructor.exists() || !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Pre_Constructor, var1, this, var2, var4, var6)) {
         GlStateManager.pushMatrix();
         GlStateManager.disableCull();
         this.mainModel.swingProgress = this.getSwingProgress(var1, var9);
         this.mainModel.isRiding = var1.isRiding();
         if (Reflector.ForgeEntity_shouldRiderSit.exists()) {
            this.mainModel.isRiding = var1.isRiding() && var1.ridingEntity != null && Reflector.callBoolean(var1.ridingEntity, Reflector.ForgeEntity_shouldRiderSit);
         }

         this.mainModel.isChild = var1.isChild();

         try {
            float var10 = this.interpolateRotation(var1.prevRenderYawOffset, var1.renderYawOffset, var9);
            float var11 = this.interpolateRotation(var1.prevRotationYawHead, var1.rotationYawHead, var9);
            float var12 = var11 - var10;
            float var13;
            if (this.mainModel.isRiding && var1.ridingEntity instanceof EntityLivingBase) {
               EntityLivingBase var14 = (EntityLivingBase)var1.ridingEntity;
               var10 = this.interpolateRotation(var14.prevRenderYawOffset, var14.renderYawOffset, var9);
               var12 = var11 - var10;
               var13 = MathHelper.wrapAngleTo180_float(var12);
               if (var13 < -85.0F) {
                  var13 = -85.0F;
               }

               if (var13 >= 85.0F) {
                  var13 = 85.0F;
               }

               var10 = var11 - var13;
               if (var13 * var13 > 2500.0F) {
                  var10 += var13 * 0.2F;
               }
            }

            float var20 = var1.prevRotationPitch + (var1.rotationPitch - var1.prevRotationPitch) * var9;
            this.renderLivingAt(var1, var2, var4, var6);
            var13 = this.handleRotationFloat(var1, var9);
            if (var1.getEntityId() == Minecraft.getMinecraft().thePlayer.getEntityId()) {
               EventRenderPlayer var15 = new EventRenderPlayer(var10, var20, var12, var9);
               Client.onEvent(var15);
               var20 = var15.getPitch();
               var10 = var15.getYaw();
               var12 = var15.yawChange;
            }

            this.rotateCorpse(var1, var13, var10, var9);
            GlStateManager.enableRescaleNormal();
            GlStateManager.scale(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(var1, var9);
            float var21 = 0.0625F;
            GlStateManager.translate(0.0F, -1.5078125F, 0.0F);
            float var16 = var1.prevLimbSwingAmount + (var1.limbSwingAmount - var1.prevLimbSwingAmount) * var9;
            float var17 = var1.limbSwing - var1.limbSwingAmount * (1.0F - var9);
            if (var1.isChild()) {
               var17 *= 3.0F;
            }

            if (var16 > 1.0F) {
               var16 = 1.0F;
            }

            GlStateManager.enableAlpha();
            this.mainModel.setLivingAnimations(var1, var17, var16, var9);
            this.mainModel.setRotationAngles(var17, var16, var13, var12, var20, 0.0625F, var1);
            boolean var18;
            if (this.field_177098_i) {
               var18 = this.func_177088_c(var1);
               this.renderModel(var1, var17, var16, var13, var12, var20, 0.0625F);
               if (var18) {
                  this.func_180565_e();
               }
            } else {
               var18 = this.func_177090_c(var1, var9);
               this.renderModel(var1, var17, var16, var13, var12, var20, 0.0625F);
               if (var18) {
                  this.func_177091_f();
               }

               GlStateManager.depthMask(true);
               if (!(var1 instanceof EntityPlayer) || !((EntityPlayer)var1).func_175149_v()) {
                  this.func_177093_a(var1, var17, var16, var9, var13, var12, var20, 0.0625F);
               }
            }

            GlStateManager.disableRescaleNormal();
         } catch (Exception var19) {
            logger.error("Couldn't render entity", var19);
         }

         GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
         GlStateManager.func_179098_w();
         GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
         GlStateManager.enableCull();
         GlStateManager.popMatrix();
         if (!this.field_177098_i) {
            super.doRender(var1, var2, var4, var6, var8, var9);
         }

         if (Reflector.RenderLivingEvent_Post_Constructor.exists() && !Reflector.postForgeBusEvent(Reflector.RenderLivingEvent_Post_Constructor, var1, this, var2, var4, var6)) {
         }
      }

   }

   protected void preRenderCallback(EntityLivingBase var1, float var2) {
   }

   protected void func_177091_f() {
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.func_179098_w();
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, OpenGlHelper.defaultTexUnit);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176093_u);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, OpenGlHelper.defaultTexUnit);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176079_G, OpenGlHelper.field_176093_u);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176086_J, 770);
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, 5890);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, 5890);
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.setActiveTexture(OpenGlHelper.field_176096_r);
      GlStateManager.func_179090_x();
      GlStateManager.func_179144_i(0);
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.field_176095_s);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176099_x, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176081_B, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176082_C, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176098_y, 5890);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176097_z, OpenGlHelper.field_176091_w);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176077_E, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176085_I, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.field_176078_F, 5890);
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      if (Config.isShaders()) {
         Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
      }

   }

   protected int getColorMultiplier(EntityLivingBase var1, float var2, float var3) {
      return 0;
   }

   protected float handleRotationFloat(EntityLivingBase var1, float var2) {
      return (float)var1.ticksExisted + var2;
   }

   static {
      int[] var0 = field_177096_e.getTextureData();

      for(int var1 = 0; var1 < 256; ++var1) {
         var0[var1] = -1;
      }

      field_177096_e.updateDynamicTexture();
   }

   public void func_82422_c() {
   }

   protected void renderModel(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      boolean var8 = !var1.isInvisible();
      boolean var9 = !var8 && !var1.isInvisibleToPlayer(Minecraft.getMinecraft().thePlayer);
      if (var8 || var9) {
         if (!this.bindEntityTexture(var1)) {
            return;
         }

         if (var9) {
            GlStateManager.pushMatrix();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
            GlStateManager.depthMask(false);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(770, 771);
            GlStateManager.alphaFunc(516, 0.003921569F);
         }

         this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
         if (var9) {
            GlStateManager.disableBlend();
            GlStateManager.alphaFunc(516, 0.1F);
            GlStateManager.popMatrix();
            GlStateManager.depthMask(true);
         }
      }

   }

   protected boolean func_177089_b(LayerRenderer var1) {
      return this.field_177097_h.remove(var1);
   }

   static final class SwitchEnumVisible {
      static final int[] field_178679_a = new int[Team.EnumVisible.values().length];

      static {
         try {
            field_178679_a[Team.EnumVisible.ALWAYS.ordinal()] = 1;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_178679_a[Team.EnumVisible.NEVER.ordinal()] = 2;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178679_a[Team.EnumVisible.HIDE_FOR_OTHER_TEAMS.ordinal()] = 3;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178679_a[Team.EnumVisible.HIDE_FOR_OWN_TEAM.ordinal()] = 4;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
