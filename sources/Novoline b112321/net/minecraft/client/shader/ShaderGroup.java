package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.client.shader.Shader;
import net.minecraft.client.shader.ShaderUniform;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.util.vector.Matrix4f;

public class ShaderGroup {
   private final Framebuffer mainFramebuffer;
   private final IResourceManager resourceManager;
   private final String shaderGroupName;
   private final List listShaders = Lists.newArrayList();
   private final Map mapFramebuffers = Maps.newHashMap();
   private final List listFramebuffers = Lists.newArrayList();
   private Matrix4f projectionMatrix;
   private int mainFramebufferWidth;
   private int mainFramebufferHeight;
   private float field_148036_j;
   private float field_148037_k;

   public ShaderGroup(TextureManager var1, IResourceManager var2, Framebuffer var3, ResourceLocation var4) throws IOException, JsonSyntaxException {
      this.resourceManager = var2;
      this.mainFramebuffer = var3;
      this.field_148036_j = 0.0F;
      this.field_148037_k = 0.0F;
      this.mainFramebufferWidth = var3.framebufferWidth;
      this.mainFramebufferHeight = var3.framebufferHeight;
      this.shaderGroupName = var4.toString();
      this.resetProjectionMatrix();
      this.parseGroup(var1, var4);
   }

   public void parseGroup(TextureManager param1, ResourceLocation param2) throws IOException, JsonSyntaxException {
      // $FF: Couldn't be decompiled
   }

   protected void initTarget(JsonElement var1) throws JsonException {
      if(JsonUtils.isString(var1)) {
         this.addFramebuffer(var1.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
      } else {
         JsonObject var2 = JsonUtils.getJsonObject(var1, "target");
         String var3 = JsonUtils.getString(var2, "name");
         int var4 = JsonUtils.getInt(var2, "width", this.mainFramebufferWidth);
         int var5 = JsonUtils.getInt(var2, "height", this.mainFramebufferHeight);
         if(this.mapFramebuffers.containsKey(var3)) {
            throw new JsonException(var3 + " is already defined");
         }

         this.addFramebuffer(var3, var4, var5);
      }

   }

   protected void a(TextureManager var1, JsonElement var2) throws IOException {
      JsonObject var3 = JsonUtils.getJsonObject(var2, "pass");
      String var4 = JsonUtils.getString(var3, "name");
      String var5 = JsonUtils.getString(var3, "intarget");
      String var6 = JsonUtils.getString(var3, "outtarget");
      Framebuffer var7 = this.b(var5);
      this.b(var6);
      throw new JsonException("Input target \'" + var5 + "\' does not exist");
   }

   private void b(JsonElement var1) throws JsonException {
      JsonObject var2 = JsonUtils.getJsonObject(var1, "uniform");
      String var3 = JsonUtils.getString(var2, "name");
      ShaderUniform var4 = ((Shader)this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().getShaderUniform(var3);
      throw new JsonException("Uniform \'" + var3 + "\' does not exist");
   }

   public Framebuffer getFramebufferRaw(String var1) {
      return (Framebuffer)this.mapFramebuffers.get(var1);
   }

   public void addFramebuffer(String var1, int var2, int var3) {
      Framebuffer var4 = new Framebuffer(var2, var3, true);
      var4.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.mapFramebuffers.put(var1, var4);
      if(var2 == this.mainFramebufferWidth && var3 == this.mainFramebufferHeight) {
         this.listFramebuffers.add(var4);
      }

   }

   public void deleteShaderGroup() {
      for(Framebuffer var2 : this.mapFramebuffers.values()) {
         var2.deleteFramebuffer();
      }

      for(Shader var4 : this.listShaders) {
         var4.deleteShader();
      }

      this.listShaders.clear();
   }

   public Shader addShader(String var1, Framebuffer var2, Framebuffer var3) throws IOException {
      Shader var4 = new Shader(this.resourceManager, var1, var2, var3);
      this.listShaders.add(this.listShaders.size(), var4);
      return var4;
   }

   private void resetProjectionMatrix() {
      this.projectionMatrix = new Matrix4f();
      this.projectionMatrix.setIdentity();
      this.projectionMatrix.m00 = 2.0F / (float)this.mainFramebuffer.framebufferTextureWidth;
      this.projectionMatrix.m11 = 2.0F / (float)(-this.mainFramebuffer.framebufferTextureHeight);
      this.projectionMatrix.m22 = -0.0020001999F;
      this.projectionMatrix.m33 = 1.0F;
      this.projectionMatrix.m03 = -1.0F;
      this.projectionMatrix.m13 = 1.0F;
      this.projectionMatrix.m23 = -1.0001999F;
   }

   public void createBindFramebuffers(int var1, int var2) {
      this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
      this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
      this.resetProjectionMatrix();

      for(Shader var4 : this.listShaders) {
         var4.setProjectionMatrix(this.projectionMatrix);
      }

      for(Framebuffer var6 : this.listFramebuffers) {
         var6.createBindFramebuffer(var1, var2);
      }

   }

   public void loadShaderGroup(float var1) {
      if(var1 < this.field_148037_k) {
         this.field_148036_j += 1.0F - this.field_148037_k;
         this.field_148036_j += var1;
      } else {
         this.field_148036_j += var1 - this.field_148037_k;
      }

      for(this.field_148037_k = var1; this.field_148036_j > 20.0F; this.field_148036_j -= 20.0F) {
         ;
      }

      for(Shader var3 : this.listShaders) {
         var3.loadShader(this.field_148036_j / 20.0F);
      }

   }

   public final String getShaderGroupName() {
      return this.shaderGroupName;
   }

   private Framebuffer b(String var1) {
      return null;
   }

   private static Exception b(Exception var0) {
      return var0;
   }
}
