package net.shadersmod.client;

import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ActiveRenderInfo;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.culling.ClippingHelper;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumWorldBlockLayer;
import net.optifine.Reflector;
import net.shadersmod.client.ClippingHelperShadow;
import net.shadersmod.client.ShaderOption;
import net.shadersmod.client.Shaders;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

public class ShadersRender {
   public static void setFrustrumPosition(Frustum var0, double var1, double var3, double var5) {
      var0.setPosition(var1, var3, var5);
   }

   public static void setupTerrain(RenderGlobal var0, Entity var1, double var2, ICamera var4, int var5, boolean var6) {
      var0.setupTerrain(var1, var2, var4, var5, var6);
   }

   public static void beginTerrainSolid() {
      String[] var0 = ShaderOption.p();
      if(Shaders.isRenderingWorld) {
         Shaders.fogEnabled = true;
         Shaders.useProgram(7);
      }

   }

   public static void j() {
      String[] var0 = ShaderOption.p();
      if(Shaders.isRenderingWorld) {
         Shaders.useProgram(7);
      }

   }

   public static void m() {
      String[] var0 = ShaderOption.p();
      if(Shaders.isRenderingWorld) {
         Shaders.useProgram(7);
      }

   }

   public static void d() {
      String[] var0 = ShaderOption.p();
      if(Shaders.isRenderingWorld) {
         Shaders.useProgram(3);
      }

   }

   public static void beginTranslucent() {
      String[] var0 = ShaderOption.p();
      if(Shaders.isRenderingWorld) {
         if(Shaders.usedDepthBuffers >= 2) {
            GlStateManager.setActiveTexture('蓋');
            Shaders.checkGLError("pre copy depth");
            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.renderWidth, Shaders.renderHeight);
            Shaders.checkGLError("copy depth");
            GlStateManager.setActiveTexture('蓀');
         }

         Shaders.useProgram(12);
      }

   }

   public static void endTranslucent() {
      String[] var0 = ShaderOption.p();
      if(Shaders.isRenderingWorld) {
         Shaders.useProgram(3);
      }

   }

   public static void renderHand0(EntityRenderer var0, float var1, int var2) {
      String[] var3 = ShaderOption.p();
      if(!Shaders.isShadowPass) {
         boolean var4 = Shaders.isItemToRenderMainTranslucent();
         if(!var4) {
            Shaders.readCenterDepth();
            Shaders.beginHand();
            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
            var0.renderHand(var1, var2, true, false, false);
            Shaders.endHand();
            Shaders.setHandRenderedMain(true);
         }
      }

   }

   public static void renderHand1(EntityRenderer var0, float var1, int var2) {
      String[] var3 = ShaderOption.p();
      if(!Shaders.isShadowPass && !Shaders.isHandRenderedMain()) {
         Shaders.readCenterDepth();
         GlStateManager.enableBlend();
         Shaders.beginHand();
         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
         var0.renderHand(var1, var2, true, false, true);
         Shaders.endHand();
         Shaders.setHandRenderedMain(true);
      }

   }

   public static void renderItemFP(ItemRenderer var0, float var1, boolean var2) {
      ShaderOption.p();
      GlStateManager.depthMask(true);
      if(var2) {
         GlStateManager.depthFunc(519);
         GL11.glPushMatrix();
         IntBuffer var4 = Shaders.activeDrawBuffers;
         Shaders.setDrawBuffers(Shaders.drawBuffersNone);
         Shaders.renderItemKeepDepthMask = true;
         var0.renderItemInFirstPerson(var1);
         Shaders.renderItemKeepDepthMask = false;
         Shaders.setDrawBuffers(var4);
         GL11.glPopMatrix();
      }

      GlStateManager.depthFunc(515);
      GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
      var0.renderItemInFirstPerson(var1);
   }

   public static void renderFPOverlay(EntityRenderer var0, float var1, int var2) {
      String[] var3 = ShaderOption.p();
      if(!Shaders.isShadowPass) {
         Shaders.beginFPOverlay();
         var0.renderHand(var1, var2, false, true, false);
         Shaders.endFPOverlay();
      }

   }

   public static void beginBlockDamage() {
      String[] var0 = ShaderOption.p();
      if(Shaders.isRenderingWorld) {
         Shaders.useProgram(11);
         if(Shaders.programsID[11] == Shaders.programsID[7]) {
            Shaders.setDrawBuffers(Shaders.drawBuffersColorAtt0);
            GlStateManager.depthMask(false);
         }
      }

   }

   public static void endBlockDamage() {
      String[] var0 = ShaderOption.p();
      if(Shaders.isRenderingWorld) {
         GlStateManager.depthMask(true);
         Shaders.useProgram(3);
      }

   }

   public static void renderShadowMap(EntityRenderer var0, int var1, float var2, long var3) {
      String[] var5 = ShaderOption.p();
      if(Shaders.usedShadowDepthBuffers > 0 && --Shaders.shadowPassCounter <= 0) {
         Minecraft var6 = Minecraft.getInstance();
         var6.mcProfiler.endStartSection("shadow pass");
         RenderGlobal var7 = var6.renderGlobal;
         Shaders.isShadowPass = true;
         Shaders.shadowPassCounter = Shaders.shadowPassInterval;
         Shaders.preShadowPassThirdPersonView = var6.gameSettings.thirdPersonView;
         var6.gameSettings.thirdPersonView = 1;
         Shaders.checkGLError("pre shadow");
         GL11.glMatrixMode(5889);
         GL11.glPushMatrix();
         GL11.glMatrixMode(5888);
         GL11.glPushMatrix();
         var6.mcProfiler.endStartSection("shadow clear");
         EXTFramebufferObject.glBindFramebufferEXT('赀', Shaders.sfb);
         Shaders.checkGLError("shadow bind sfb");
         Shaders.useProgram(30);
         var6.mcProfiler.endStartSection("shadow camera");
         var0.setupCameraTransform(var2, 2);
         Shaders.setCameraShadow(var2);
         ActiveRenderInfo.updateRenderInfo(var6.player, var6.gameSettings.thirdPersonView == 2);
         Shaders.checkGLError("shadow camera");
         GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
         Shaders.checkGLError("shadow drawbuffers");
         GL11.glReadBuffer(0);
         Shaders.checkGLError("shadow readbuffer");
         EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '贀', 3553, Shaders.sfbDepthTextures.get(0), 0);
         if(Shaders.usedShadowColorBuffers != 0) {
            EXTFramebufferObject.glFramebufferTexture2DEXT('赀', '賠', 3553, Shaders.sfbColorTextures.get(0), 0);
         }

         Shaders.checkFramebufferStatus("shadow fb");
         GL11.glClearColor(1.0F, 1.0F, 1.0F, 1.0F);
         GL11.glClear(Shaders.usedShadowColorBuffers != 0?16640:256);
         Shaders.checkGLError("shadow clear");
         var6.mcProfiler.endStartSection("shadow frustum");
         ClippingHelper var8 = ClippingHelperShadow.getInstance();
         var6.mcProfiler.endStartSection("shadow culling");
         Frustum var9 = new Frustum(var8);
         Entity var10 = var6.getRenderViewEntity();
         double var11 = var10.lastTickPosX + (var10.posX - var10.lastTickPosX) * (double)var2;
         double var13 = var10.lastTickPosY + (var10.posY - var10.lastTickPosY) * (double)var2;
         double var15 = var10.lastTickPosZ + (var10.posZ - var10.lastTickPosZ) * (double)var2;
         var9.setPosition(var11, var13, var15);
         GlStateManager.shadeModel(7425);
         GlStateManager.enableDepth();
         GlStateManager.depthFunc(515);
         GlStateManager.depthMask(true);
         GlStateManager.colorMask(true, true, true, true);
         GlStateManager.disableCull();
         var6.mcProfiler.endStartSection("shadow prepareterrain");
         var6.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         var6.mcProfiler.endStartSection("shadow setupterrain");
         int var17 = 0;
         var17 = var0.frameCount;
         var0.frameCount = var17 + 1;
         var7.setupTerrain(var10, (double)var2, var9, var17, var6.player.isSpectator());
         var6.mcProfiler.endStartSection("shadow updatechunks");
         var6.mcProfiler.endStartSection("shadow terrain");
         GlStateManager.matrixMode(5888);
         GlStateManager.pushMatrix();
         GlStateManager.disableAlpha();
         var7.renderBlockLayer(EnumWorldBlockLayer.SOLID, (double)var2, 2, var10);
         Shaders.checkGLError("shadow terrain solid");
         GlStateManager.enableAlpha();
         var7.renderBlockLayer(EnumWorldBlockLayer.CUTOUT_MIPPED, (double)var2, 2, var10);
         Shaders.checkGLError("shadow terrain cutoutmipped");
         var6.getTextureManager().getTexture(TextureMap.locationBlocksTexture).setBlurMipmap(false, false);
         var7.renderBlockLayer(EnumWorldBlockLayer.CUTOUT, (double)var2, 2, var10);
         Shaders.checkGLError("shadow terrain cutout");
         var6.getTextureManager().getTexture(TextureMap.locationBlocksTexture).restoreLastBlurMipmap();
         GlStateManager.shadeModel(7424);
         GlStateManager.alphaFunc(516, 0.1F);
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.pushMatrix();
         var6.mcProfiler.endStartSection("shadow entities");
         if(Reflector.aF.d()) {
            Reflector.a(Reflector.aF, new Object[]{Integer.valueOf(0)});
         }

         var7.renderEntities(var10, var9, var2);
         Shaders.checkGLError("shadow entities");
         GlStateManager.matrixMode(5888);
         GlStateManager.popMatrix();
         GlStateManager.depthMask(true);
         GlStateManager.disableBlend();
         GlStateManager.enableCull();
         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
         GlStateManager.alphaFunc(516, 0.1F);
         if(Shaders.usedShadowDepthBuffers >= 2) {
            GlStateManager.setActiveTexture('蓅');
            Shaders.checkGLError("pre copy shadow depth");
            GL11.glCopyTexSubImage2D(3553, 0, 0, 0, 0, 0, Shaders.shadowMapWidth, Shaders.shadowMapHeight);
            Shaders.checkGLError("copy shadow depth");
            GlStateManager.setActiveTexture('蓀');
         }

         GlStateManager.disableBlend();
         GlStateManager.depthMask(true);
         var6.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         GlStateManager.shadeModel(7425);
         Shaders.checkGLError("shadow pre-translucent");
         GL20.glDrawBuffers(Shaders.sfbDrawBuffers);
         Shaders.checkGLError("shadow drawbuffers pre-translucent");
         Shaders.checkFramebufferStatus("shadow pre-translucent");
         if(Shaders.isRenderShadowTranslucent()) {
            var6.mcProfiler.endStartSection("shadow translucent");
            var7.renderBlockLayer(EnumWorldBlockLayer.TRANSLUCENT, (double)var2, 2, var10);
            Shaders.checkGLError("shadow translucent");
         }

         if(Reflector.aF.d()) {
            RenderHelper.enableStandardItemLighting();
            Reflector.f(Reflector.aF, new Object[]{Integer.valueOf(1)});
            var7.renderEntities(var10, var9, var2);
            Reflector.f(Reflector.aF, new Object[]{Integer.valueOf(-1)});
            RenderHelper.disableStandardItemLighting();
            Shaders.checkGLError("shadow entities 1");
         }

         GlStateManager.shadeModel(7424);
         GlStateManager.depthMask(true);
         GlStateManager.enableCull();
         GlStateManager.disableBlend();
         GL11.glFlush();
         Shaders.checkGLError("shadow flush");
         Shaders.isShadowPass = false;
         var6.gameSettings.thirdPersonView = Shaders.preShadowPassThirdPersonView;
         var6.mcProfiler.endStartSection("shadow postprocess");
         if(Shaders.hasGlGenMipmap) {
            if(Shaders.usedShadowDepthBuffers >= 1) {
               if(Shaders.shadowMipmapEnabled[0]) {
                  GlStateManager.setActiveTexture('蓄');
                  GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(0));
                  GL30.glGenerateMipmap(3553);
                  GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[0]?9984:9987);
               }

               if(Shaders.usedShadowDepthBuffers >= 2 && Shaders.shadowMipmapEnabled[1]) {
                  GlStateManager.setActiveTexture('蓅');
                  GlStateManager.bindTexture(Shaders.sfbDepthTextures.get(1));
                  GL30.glGenerateMipmap(3553);
                  GL11.glTexParameteri(3553, 10241, Shaders.shadowFilterNearest[1]?9984:9987);
               }

               GlStateManager.setActiveTexture('蓀');
            }

            if(Shaders.usedShadowColorBuffers >= 1) {
               if(Shaders.shadowColorMipmapEnabled[0]) {
                  GlStateManager.setActiveTexture('蓍');
                  GlStateManager.bindTexture(Shaders.sfbColorTextures.get(0));
                  GL30.glGenerateMipmap(3553);
                  GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[0]?9984:9987);
               }

               if(Shaders.usedShadowColorBuffers >= 2 && Shaders.shadowColorMipmapEnabled[1]) {
                  GlStateManager.setActiveTexture('蓎');
                  GlStateManager.bindTexture(Shaders.sfbColorTextures.get(1));
                  GL30.glGenerateMipmap(3553);
                  GL11.glTexParameteri(3553, 10241, Shaders.shadowColorFilterNearest[1]?9984:9987);
               }

               GlStateManager.setActiveTexture('蓀');
            }
         }

         Shaders.checkGLError("shadow postprocess");
         EXTFramebufferObject.glBindFramebufferEXT('赀', Shaders.dfb);
         GL11.glViewport(0, 0, Shaders.renderWidth, Shaders.renderHeight);
         Shaders.activeDrawBuffers = null;
         var6.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
         Shaders.useProgram(7);
         GL11.glMatrixMode(5888);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5889);
         GL11.glPopMatrix();
         GL11.glMatrixMode(5888);
         Shaders.checkGLError("shadow end");
      }

   }

   public static void preRenderChunkLayer(EnumWorldBlockLayer var0) {
      String[] var1 = ShaderOption.p();
      if(Shaders.isRenderBackFace(var0)) {
         GlStateManager.disableCull();
      }

      if(OpenGlHelper.useVbo()) {
         GL11.glEnableClientState('聵');
         GL20.glEnableVertexAttribArray(Shaders.midTexCoordAttrib);
         GL20.glEnableVertexAttribArray(Shaders.tangentAttrib);
         GL20.glEnableVertexAttribArray(Shaders.entityAttrib);
      }

   }

   public static void postRenderChunkLayer(EnumWorldBlockLayer var0) {
      String[] var1 = ShaderOption.p();
      if(OpenGlHelper.useVbo()) {
         GL11.glDisableClientState('聵');
         GL20.glDisableVertexAttribArray(Shaders.midTexCoordAttrib);
         GL20.glDisableVertexAttribArray(Shaders.tangentAttrib);
         GL20.glDisableVertexAttribArray(Shaders.entityAttrib);
      }

      if(Shaders.isRenderBackFace(var0)) {
         GlStateManager.enableCull();
      }

   }

   public static void setupArrayPointersVbo() {
      boolean var0 = true;
      GL11.glVertexPointer(3, 5126, 56, 0L);
      GL11.glColorPointer(4, 5121, 56, 12L);
      GL11.glTexCoordPointer(2, 5126, 56, 16L);
      OpenGlHelper.setClientActiveTexture(OpenGlHelper.lightmapTexUnit);
      GL11.glTexCoordPointer(2, 5122, 56, 24L);
      OpenGlHelper.setClientActiveTexture(OpenGlHelper.defaultTexUnit);
      GL11.glNormalPointer(5120, 56, 28L);
      GL20.glVertexAttribPointer(Shaders.midTexCoordAttrib, 2, 5126, false, 56, 32L);
      GL20.glVertexAttribPointer(Shaders.tangentAttrib, 4, 5122, false, 56, 40L);
      GL20.glVertexAttribPointer(Shaders.entityAttrib, 3, 5122, false, 56, 48L);
   }

   public static void beaconBeamBegin() {
      Shaders.useProgram(14);
   }

   public static void h() {
   }

   public static void l() {
   }

   public static void k() {
   }

   public static void beaconBeamDraw2() {
      GlStateManager.disableBlend();
   }

   public static void renderEnchantedGlintBegin() {
      Shaders.useProgram(17);
   }

   public static void renderEnchantedGlintEnd() {
      String[] var0 = ShaderOption.p();
      if(Shaders.isRenderingWorld) {
         if(Shaders.isRenderBothHands()) {
            Shaders.useProgram(19);
         }

         Shaders.useProgram(16);
      }

      Shaders.useProgram(0);
   }
}
