package net.minecraft.client.renderer.entity;

import cc.novoline.Novoline;
import cc.novoline.events.EventManager;
import cc.novoline.events.events.RenderNameTagEvent;
import cc.novoline.modules.visual.Chams;
import cc.novoline.modules.visual.ESP;
import cc.novoline.utils.OutlineUtils;
import cc.novoline.utils.RenderUtils;
import com.google.common.collect.Lists;
import java.awt.Color;
import java.nio.FloatBuffer;
import java.util.List;
import net.A7;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EnumPlayerModelParts;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.Team$EnumVisible;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.optifine.Config;
import net.optifine.Reflector;
import net.shadersmod.client.Shaders;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.GL11;

public abstract class RendererLivingEntity extends Render {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final DynamicTexture field_177096_e = new DynamicTexture(16, 16);
   public static float NAME_TAG_RANGE = 64.0F;
   public static float NAME_TAG_RANGE_SNEAK = 32.0F;
   protected ModelBase mainModel;
   protected FloatBuffer l = GLAllocation.createDirectFloatBuffer(4);
   protected List layerRenderers = Lists.newArrayList();
   protected boolean renderOutlines = false;

   public RendererLivingEntity(RenderManager var1, ModelBase var2, float var3) {
      super(var1);
      this.mainModel = var2;
      this.shadowSize = var3;
   }

   public boolean addLayer(LayerRenderer var1) {
      return this.layerRenderers.add(var1);
   }

   protected boolean removeLayer(LayerRenderer var1) {
      return this.layerRenderers.remove(var1);
   }

   protected float interpolateRotation(float var1, float var2, float var3) {
      float var4;
      for(var4 = var2 - var1; var4 < -180.0F; var4 += 360.0F) {
         ;
      }

      while(var4 >= 180.0F) {
         var4 -= 360.0F;
      }

      return var1 + var3 * var4;
   }

   public void transformHeldFull3DItemLayer() {
   }

   public void doRender(EntityLivingBase param1, double param2, double param4, double param6, float param8, float param9) {
      // $FF: Couldn't be decompiled
   }

   protected boolean setScoreTeamColor(EntityLivingBase var1) {
      int var2 = 16777215;
      if(var1 instanceof EntityPlayer) {
         ScorePlayerTeam var3 = (ScorePlayerTeam)var1.getTeam();
         String var4 = FontRenderer.getFormatFromString(var3.getColorPrefix());
         if(var4.length() >= 2) {
            var2 = this.getFontRendererFromRenderManager().getColorCode(var4.charAt(1));
         }
      }

      float var6 = (float)(var2 >> 16 & 255) / 255.0F;
      float var7 = (float)(var2 >> 8 & 255) / 255.0F;
      float var5 = (float)(var2 & 255) / 255.0F;
      GlStateManager.disableLighting();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.color(var6, var7, var5, 1.0F);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.disableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      return true;
   }

   protected void unsetScoreTeamColor() {
      GlStateManager.enableLighting();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
      GlStateManager.enableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
      GlStateManager.enableTexture2D();
      GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
   }

   protected void renderModel(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7) {
      boolean var8 = !var1.isInvisible();
      boolean var9 = !var1.isInvisibleToPlayer(Minecraft.getInstance().player);
      if(this.bindEntityTexture(var1)) {
         GlStateManager.pushMatrix();
         GlStateManager.color(1.0F, 1.0F, 1.0F, 0.15F);
         GlStateManager.depthMask(false);
         GlStateManager.enableBlend();
         GlStateManager.blendFunc(770, 771);
         GlStateManager.alphaFunc(516, 0.003921569F);
         ESP var10 = (ESP)Novoline.getInstance().getModuleManager().getModule(ESP.class);
         Chams var11 = (Chams)Novoline.getInstance().getModuleManager().getModule(Chams.class);
         if(var10.isEnabled() && var10.i().equals("Outline") && !var10.d().isEmpty() && var10.b(var1) && Minecraft.getInstance().world != null) {
            GL11.glPushMatrix();
            GlStateManager.depthMask(true);
            Color var12 = var10.e().getAwtColor();
            String var13 = (String)var10.k().get();
            String var14 = var1.getDisplayName().getFormattedText();
            boolean var15 = !var13.equals("Team");
            boolean var16 = var13.equals("Rainbow");
            float var17 = 0.003921569F * (float)var12.getRed();
            float var18 = 0.003921569F * (float)var12.getBlue();
            float var19 = 0.003921569F * (float)var12.getGreen();
            float var20 = 0.003921569F * (float)((Integer)var10.a().get()).intValue();
            OutlineUtils.renderOne();
            this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
            OutlineUtils.renderTwo();
            this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
            OutlineUtils.renderThree();
            OutlineUtils.a(var10, var1);
            this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
            OutlineUtils.renderFive();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            GL11.glPopMatrix();
         }

         if(var11.isEnabled() && var11.isValid(var1) && ((Boolean)var11.isColored().get()).booleanValue()) {
            int var21 = ((Boolean)var11.getRainbow().get()).booleanValue()?RenderUtils.a():var11.getHidden().getAwtColor().getRGB();
            GL11.glPushAttrib(-1);
            GlStateManager.enableBlend();
            GL11.glBlendFunc(770, 771);
            GlStateManager.disableTexture2D();
            GlStateManager.b(!((Boolean)var11.isMaterial().get()).booleanValue());
            GL11.glColor4f((float)(var21 >> 16 & 255) / 255.0F, (float)(var21 >> 8 & 255) / 255.0F, (float)(var21 & 255) / 255.0F, Math.max(0.11764706F, var11.getVisibleAlpha() / 255.0F));
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
            GlStateManager.disableDepth();
            this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
            GlStateManager.enableDepth();
            var21 = ((Boolean)var11.getRainbow().get()).booleanValue()?RenderUtils.a():var11.getVisible().getAwtColor().getRGB();
            GL11.glColor4f((float)(var21 >> 16 & 255) / 255.0F, (float)(var21 >> 8 & 255) / 255.0F, (float)(var21 & 255) / 255.0F, Math.max(0.11764706F, var11.getVisibleAlpha() / 255.0F));
            this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
            GlStateManager.a(!((Boolean)var11.isMaterial().get()).booleanValue());
            GlStateManager.enableTexture2D();
            GlStateManager.disableBlend();
            GL11.glPopAttrib();
         } else {
            this.mainModel.render(var1, var2, var3, var4, var5, var6, var7);
         }

         GlStateManager.disableBlend();
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
      }
   }

   private boolean setDoRenderBrightness(EntityLivingBase var1, float var2) {
      return this.a(var1, var2, true);
   }

   private boolean a(EntityLivingBase var1, float var2, boolean var3) {
      float var4 = var1.getBrightness(var2);
      int var5 = this.getColorMultiplier(var1, var4, var2);
      boolean var6 = (var5 >> 24 & 255) > 0;
      boolean var7 = var1.hurtTime > 0 || var1.deathTime > 0;
      return false;
   }

   private void unsetBrightness() {
      this.startBrightness(8448);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
      ARBMultitexture.glActiveTextureARB(OpenGlHelper.lightmapTexUnit);
      this.unsetBrightness0();
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      ARBMultitexture.glActiveTextureARB(OpenGlHelper.GL_TEXTURE2);
      GlStateManager.disableTexture2D();
      GlStateManager.bindTexture(0);
      this.unsetBrightness0();
      ARBMultitexture.glActiveTextureARB(OpenGlHelper.defaultTexUnit);
      if(Config.isShaders()) {
         Shaders.setEntityColor(0.0F, 0.0F, 0.0F, 0.0F);
      }

   }

   private void startBrightness(int var1) {
      ARBMultitexture.glActiveTextureARB(OpenGlHelper.defaultTexUnit);
      GlStateManager.enableTexture2D();
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, var1);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
   }

   private void unsetBrightness0() {
      GL11.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
      GL11.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
   }

   protected void renderLivingAt(EntityLivingBase var1, double var2, double var4, double var6) {
      GlStateManager.translate((float)var2, (float)var4, (float)var6);
   }

   protected void rotateCorpse(EntityLivingBase var1, float var2, float var3, float var4) {
      GlStateManager.rotate(180.0F - var3, 0.0F, 1.0F, 0.0F);
      if(var1.deathTime > 0) {
         float var5 = ((float)var1.deathTime + var4 - 1.0F) / 20.0F * 1.6F;
         var5 = MathHelper.sqrt_float(var5);
         if(var5 > 1.0F) {
            var5 = 1.0F;
         }

         GlStateManager.rotate(var5 * this.getDeathMaxRotation(var1), 0.0F, 0.0F, 1.0F);
      } else {
         String var7 = EnumChatFormatting.a(var1.getName());
         if((var7.equals("Dinnerbone") || var7.equals("Grumm")) && (!(var1 instanceof EntityPlayer) || ((EntityPlayer)var1).isWearing(EnumPlayerModelParts.CAPE))) {
            GlStateManager.translate(0.0F, var1.height + 0.1F, 0.0F);
            GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
         }
      }

   }

   protected float getSwingProgress(EntityLivingBase var1, float var2) {
      return var1.getSwingProgress(var2);
   }

   protected float handleRotationFloat(EntityLivingBase var1, float var2) {
      return (float)var1.ticksExisted + var2;
   }

   protected void renderLayers(EntityLivingBase var1, float var2, float var3, float var4, float var5, float var6, float var7, float var8) {
      for(LayerRenderer var10 : this.layerRenderers) {
         boolean var11 = this.a(var1, var4, var10.shouldCombineTextures());
         A7.a(var10, var1, var2, var3, var4, var5, var6, var7, var8);
         this.unsetBrightness();
      }

   }

   protected float getDeathMaxRotation(EntityLivingBase var1) {
      return 90.0F;
   }

   protected int getColorMultiplier(EntityLivingBase var1, float var2, float var3) {
      return 0;
   }

   protected void preRenderCallback(EntityLivingBase var1, float var2) {
   }

   public void renderName(EntityLivingBase var1, double var2, double var4, double var6) {
      RenderNameTagEvent var8 = new RenderNameTagEvent(var1);
      EventManager.call(var8);
      if(!var8.isCancelled()) {
         if(!Reflector.aW.b() || !Reflector.a(Reflector.aW, new Object[]{var1, this, Double.valueOf(var2), Double.valueOf(var4), Double.valueOf(var6)})) {
            if(this.canRenderName(var1)) {
               double var9 = var1.getDistanceSqToEntity(this.renderManager.livingPlayer);
               float var11 = var1.isSneaking()?NAME_TAG_RANGE_SNEAK:NAME_TAG_RANGE;
               if(var9 < (double)(var11 * var11)) {
                  String var12 = var1.getDisplayName().getFormattedText();
                  GlStateManager.alphaFunc(516, 0.1F);
                  if(var1.isSneaking()) {
                     FontRenderer var13 = this.getFontRendererFromRenderManager();
                     GlStateManager.pushMatrix();
                     GlStateManager.translate((float)var2, (float)var4 + var1.height + 0.5F - (var1.isChild()?var1.height / 2.0F:0.0F), (float)var6);
                     GL11.glNormal3f(0.0F, 1.0F, 0.0F);
                     GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                     GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
                     GlStateManager.scale(-0.02666667F, -0.02666667F, 0.02666667F);
                     GlStateManager.translate(0.0F, 9.374999F, 0.0F);
                     GlStateManager.disableLighting();
                     GlStateManager.depthMask(false);
                     GlStateManager.enableBlend();
                     GlStateManager.disableTexture2D();
                     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
                     int var14 = var13.d(var12) / 2;
                     Tessellator var15 = Tessellator.getInstance();
                     WorldRenderer var16 = var15.getWorldRenderer();
                     var16.begin(7, DefaultVertexFormats.POSITION_COLOR);
                     var16.pos((double)(-var14 - 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                     var16.pos((double)(-var14 - 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                     var16.pos((double)(var14 + 1), 8.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                     var16.pos((double)(var14 + 1), -1.0D, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                     var15.draw();
                     GlStateManager.enableTexture2D();
                     GlStateManager.depthMask(true);
                     var13.drawString(var12, (float)(-var13.d(var12) / 2), 0.0F, 553648127);
                     GlStateManager.enableLighting();
                     GlStateManager.disableBlend();
                     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
                     GlStateManager.popMatrix();
                  } else {
                     this.renderOffsetLivingLabel(var1, var2, var4 - (var1.isChild()?(double)(var1.height / 2.0F):0.0D), var6, var12, 0.02666667F, var9);
                  }
               }
            }

            if(Reflector.bT.b()) {
               Reflector.a(Reflector.bT, new Object[]{var1, this, Double.valueOf(var2), Double.valueOf(var4), Double.valueOf(var6)});
            }
         }

      }
   }

   protected boolean canRenderName(EntityLivingBase var1) {
      EntityPlayerSP var2 = Minecraft.getInstance().player;
      if(var1 instanceof EntityPlayer && var1 != var2) {
         Team var3 = var1.getTeam();
         Team$EnumVisible var4 = var3.getNameTagVisibility();
         Team var5 = var2.getTeam();
         switch(var4.ordinal()) {
         case 0:
            return true;
         case 1:
            return false;
         case 2:
            return var3.isSameTeam(var5);
         case 3:
            return !var3.isSameTeam(var5);
         default:
            return true;
         }
      } else {
         return Minecraft.isGuiEnabled() && var1 != this.renderManager.livingPlayer && !var1.isInvisibleToPlayer(var2) && var1.riddenByEntity == null;
      }
   }

   public void setRenderOutlines(boolean var1) {
      this.renderOutlines = var1;
   }

   public ModelBase getMainModel() {
      return this.mainModel;
   }

   static {
      int[] var7 = field_177096_e.getTextureData();

      for(int var8 = 0; var8 < 256; ++var8) {
         var7[var8] = -1;
      }

      field_177096_e.updateDynamicTexture();
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
