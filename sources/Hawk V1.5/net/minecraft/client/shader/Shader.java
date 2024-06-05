package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import javax.vecmath.Matrix4f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;

public class Shader {
   private final List listAuxWidths = Lists.newArrayList();
   private static final String __OBFID = "CL_00001042";
   public final Framebuffer framebufferIn;
   private final List listAuxFramebuffers = Lists.newArrayList();
   public final Framebuffer framebufferOut;
   private final List listAuxHeights = Lists.newArrayList();
   private final List listAuxNames = Lists.newArrayList();
   private final ShaderManager manager;
   private Matrix4f projectionMatrix;

   public void setProjectionMatrix(Matrix4f var1) {
      this.projectionMatrix = var1;
   }

   public void addAuxFramebuffer(String var1, Object var2, int var3, int var4) {
      this.listAuxNames.add(this.listAuxNames.size(), var1);
      this.listAuxFramebuffers.add(this.listAuxFramebuffers.size(), var2);
      this.listAuxWidths.add(this.listAuxWidths.size(), var3);
      this.listAuxHeights.add(this.listAuxHeights.size(), var4);
   }

   public void loadShader(float var1) {
      this.preLoadShader();
      this.framebufferIn.unbindFramebuffer();
      float var2 = (float)this.framebufferOut.framebufferTextureWidth;
      float var3 = (float)this.framebufferOut.framebufferTextureHeight;
      GlStateManager.viewport(0, 0, (int)var2, (int)var3);
      this.manager.addSamplerTexture("DiffuseSampler", this.framebufferIn);

      for(int var4 = 0; var4 < this.listAuxFramebuffers.size(); ++var4) {
         this.manager.addSamplerTexture((String)this.listAuxNames.get(var4), this.listAuxFramebuffers.get(var4));
         this.manager.getShaderUniformOrDefault(String.valueOf((new StringBuilder("AuxSize")).append(var4))).set((float)(Integer)this.listAuxWidths.get(var4), (float)(Integer)this.listAuxHeights.get(var4));
      }

      this.manager.getShaderUniformOrDefault("ProjMat").set(this.projectionMatrix);
      this.manager.getShaderUniformOrDefault("InSize").set((float)this.framebufferIn.framebufferTextureWidth, (float)this.framebufferIn.framebufferTextureHeight);
      this.manager.getShaderUniformOrDefault("OutSize").set(var2, var3);
      this.manager.getShaderUniformOrDefault("Time").set(var1);
      Minecraft var9 = Minecraft.getMinecraft();
      this.manager.getShaderUniformOrDefault("ScreenSize").set((float)var9.displayWidth, (float)var9.displayHeight);
      this.manager.useShader();
      this.framebufferOut.framebufferClear();
      this.framebufferOut.bindFramebuffer(false);
      GlStateManager.depthMask(false);
      GlStateManager.colorMask(true, true, true, true);
      Tessellator var5 = Tessellator.getInstance();
      WorldRenderer var6 = var5.getWorldRenderer();
      var6.startDrawingQuads();
      var6.func_178991_c(-1);
      var6.addVertex(0.0D, (double)var3, 500.0D);
      var6.addVertex((double)var2, (double)var3, 500.0D);
      var6.addVertex((double)var2, 0.0D, 500.0D);
      var6.addVertex(0.0D, 0.0D, 500.0D);
      var5.draw();
      GlStateManager.depthMask(true);
      GlStateManager.colorMask(true, true, true, true);
      this.manager.endShader();
      this.framebufferOut.unbindFramebuffer();
      this.framebufferIn.unbindFramebufferTexture();
      Iterator var7 = this.listAuxFramebuffers.iterator();

      while(var7.hasNext()) {
         Object var8 = var7.next();
         if (var8 instanceof Framebuffer) {
            ((Framebuffer)var8).unbindFramebufferTexture();
         }
      }

   }

   public Shader(IResourceManager var1, String var2, Framebuffer var3, Framebuffer var4) throws JsonException {
      this.manager = new ShaderManager(var1, var2);
      this.framebufferIn = var3;
      this.framebufferOut = var4;
   }

   private void preLoadShader() {
      GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
      GlStateManager.disableBlend();
      GlStateManager.disableDepth();
      GlStateManager.disableAlpha();
      GlStateManager.disableFog();
      GlStateManager.disableLighting();
      GlStateManager.disableColorMaterial();
      GlStateManager.func_179098_w();
      GlStateManager.func_179144_i(0);
   }

   public void deleteShader() {
      this.manager.deleteShader();
   }

   public ShaderManager getShaderManager() {
      return this.manager;
   }
}
