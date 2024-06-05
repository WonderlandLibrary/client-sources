package net.minecraft.client.shader;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.io.FileNotFoundException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.vecmath.Matrix4f;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

public class ShaderGroup {
   private final List listFramebuffers = Lists.newArrayList();
   private float field_148036_j;
   private IResourceManager resourceManager;
   private float field_148037_k;
   private Framebuffer mainFramebuffer;
   private final Map mapFramebuffers = Maps.newHashMap();
   private int mainFramebufferHeight;
   private static final String __OBFID = "CL_00001041";
   private int mainFramebufferWidth;
   private final List listShaders = Lists.newArrayList();
   private String shaderGroupName;
   private Matrix4f projectionMatrix;

   public void addFramebuffer(String var1, int var2, int var3) {
      Framebuffer var4 = new Framebuffer(var2, var3, true);
      var4.setFramebufferColor(0.0F, 0.0F, 0.0F, 0.0F);
      this.mapFramebuffers.put(var1, var4);
      if (var2 == this.mainFramebufferWidth && var3 == this.mainFramebufferHeight) {
         this.listFramebuffers.add(var4);
      }

   }

   public ShaderGroup(TextureManager var1, IResourceManager var2, Framebuffer var3, ResourceLocation var4) throws JsonException {
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

   public void loadShaderGroup(float var1) {
      if (var1 < this.field_148037_k) {
         this.field_148036_j += 1.0F - this.field_148037_k;
         this.field_148036_j += var1;
      } else {
         this.field_148036_j += var1 - this.field_148037_k;
      }

      for(this.field_148037_k = var1; this.field_148036_j > 20.0F; this.field_148036_j -= 20.0F) {
      }

      Iterator var2 = this.listShaders.iterator();

      while(var2.hasNext()) {
         Shader var3 = (Shader)var2.next();
         var3.loadShader(this.field_148036_j / 20.0F);
      }

   }

   private Framebuffer getFramebuffer(String var1) {
      return var1 == null ? null : (var1.equals("minecraft:main") ? this.mainFramebuffer : (Framebuffer)this.mapFramebuffers.get(var1));
   }

   public Shader addShader(String var1, Framebuffer var2, Framebuffer var3) throws JsonException {
      Shader var4 = new Shader(this.resourceManager, var1, var2, var3);
      this.listShaders.add(this.listShaders.size(), var4);
      return var4;
   }

   private void parsePass(TextureManager var1, JsonElement var2) throws JsonException {
      JsonObject var3 = JsonUtils.getElementAsJsonObject(var2, "pass");
      String var4 = JsonUtils.getJsonObjectStringFieldValue(var3, "name");
      String var5 = JsonUtils.getJsonObjectStringFieldValue(var3, "intarget");
      String var6 = JsonUtils.getJsonObjectStringFieldValue(var3, "outtarget");
      Framebuffer var7 = this.getFramebuffer(var5);
      Framebuffer var8 = this.getFramebuffer(var6);
      if (var7 == null) {
         throw new JsonException(String.valueOf((new StringBuilder("Input target '")).append(var5).append("' does not exist")));
      } else if (var8 == null) {
         throw new JsonException(String.valueOf((new StringBuilder("Output target '")).append(var6).append("' does not exist")));
      } else {
         Shader var9 = this.addShader(var4, var7, var8);
         JsonArray var10 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var3, "auxtargets", (JsonArray)null);
         if (var10 != null) {
            int var11 = 0;

            for(Iterator var12 = var10.iterator(); var12.hasNext(); ++var11) {
               JsonElement var13 = (JsonElement)var12.next();

               try {
                  JsonObject var14 = JsonUtils.getElementAsJsonObject(var13, "auxtarget");
                  String var30 = JsonUtils.getJsonObjectStringFieldValue(var14, "name");
                  String var16 = JsonUtils.getJsonObjectStringFieldValue(var14, "id");
                  Framebuffer var17 = this.getFramebuffer(var16);
                  if (var17 == null) {
                     ResourceLocation var18 = new ResourceLocation(String.valueOf((new StringBuilder("textures/effect/")).append(var16).append(".png")));

                     try {
                        this.resourceManager.getResource(var18);
                     } catch (FileNotFoundException var24) {
                        throw new JsonException(String.valueOf((new StringBuilder("Render target or texture '")).append(var16).append("' does not exist")));
                     }

                     var1.bindTexture(var18);
                     ITextureObject var19 = var1.getTexture(var18);
                     int var20 = JsonUtils.getJsonObjectIntegerFieldValue(var14, "width");
                     int var21 = JsonUtils.getJsonObjectIntegerFieldValue(var14, "height");
                     boolean var22 = JsonUtils.getJsonObjectBooleanFieldValue(var14, "bilinear");
                     if (var22) {
                        GL11.glTexParameteri(3553, 10241, 9729);
                        GL11.glTexParameteri(3553, 10240, 9729);
                     } else {
                        GL11.glTexParameteri(3553, 10241, 9728);
                        GL11.glTexParameteri(3553, 10240, 9728);
                     }

                     var9.addAuxFramebuffer(var30, var19.getGlTextureId(), var20, var21);
                  } else {
                     var9.addAuxFramebuffer(var30, var17, var17.framebufferTextureWidth, var17.framebufferTextureHeight);
                  }
               } catch (Exception var25) {
                  JsonException var15 = JsonException.func_151379_a(var25);
                  var15.func_151380_a(String.valueOf((new StringBuilder("auxtargets[")).append(var11).append("]")));
                  throw var15;
               }
            }
         }

         JsonArray var26 = JsonUtils.getJsonObjectJsonArrayFieldOrDefault(var3, "uniforms", (JsonArray)null);
         if (var26 != null) {
            int var27 = 0;

            for(Iterator var28 = var26.iterator(); var28.hasNext(); ++var27) {
               JsonElement var29 = (JsonElement)var28.next();

               try {
                  this.initUniform(var29);
               } catch (Exception var23) {
                  JsonException var31 = JsonException.func_151379_a(var23);
                  var31.func_151380_a(String.valueOf((new StringBuilder("uniforms[")).append(var27).append("]")));
                  throw var31;
               }
            }
         }

      }
   }

   public Framebuffer func_177066_a(String var1) {
      return (Framebuffer)this.mapFramebuffers.get(var1);
   }

   private void initUniform(JsonElement var1) throws JsonException {
      JsonObject var2 = JsonUtils.getElementAsJsonObject(var1, "uniform");
      String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
      ShaderUniform var4 = ((Shader)this.listShaders.get(this.listShaders.size() - 1)).getShaderManager().getShaderUniform(var3);
      if (var4 == null) {
         throw new JsonException(String.valueOf((new StringBuilder("Uniform '")).append(var3).append("' does not exist")));
      } else {
         float[] var5 = new float[4];
         int var6 = 0;
         JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var2, "values");

         for(Iterator var8 = var7.iterator(); var8.hasNext(); ++var6) {
            JsonElement var9 = (JsonElement)var8.next();

            try {
               var5[var6] = JsonUtils.getJsonElementFloatValue(var9, "value");
            } catch (Exception var12) {
               JsonException var11 = JsonException.func_151379_a(var12);
               var11.func_151380_a(String.valueOf((new StringBuilder("values[")).append(var6).append("]")));
               throw var11;
            }
         }

         switch(var6) {
         case 0:
         default:
            break;
         case 1:
            var4.set(var5[0]);
            break;
         case 2:
            var4.set(var5[0], var5[1]);
            break;
         case 3:
            var4.set(var5[0], var5[1], var5[2]);
            break;
         case 4:
            var4.set(var5[0], var5[1], var5[2], var5[3]);
         }

      }
   }

   public void deleteShaderGroup() {
      Iterator var1 = this.mapFramebuffers.values().iterator();

      while(var1.hasNext()) {
         Framebuffer var2 = (Framebuffer)var1.next();
         var2.deleteFramebuffer();
      }

      var1 = this.listShaders.iterator();

      while(var1.hasNext()) {
         Shader var3 = (Shader)var1.next();
         var3.deleteShader();
      }

      this.listShaders.clear();
   }

   public void parseGroup(TextureManager param1, ResourceLocation param2) throws JsonException {
      // $FF: Couldn't be decompiled
   }

   private void initTarget(JsonElement var1) throws JsonException {
      if (JsonUtils.jsonElementTypeIsString(var1)) {
         this.addFramebuffer(var1.getAsString(), this.mainFramebufferWidth, this.mainFramebufferHeight);
      } else {
         JsonObject var2 = JsonUtils.getElementAsJsonObject(var1, "target");
         String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
         int var4 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var2, "width", this.mainFramebufferWidth);
         int var5 = JsonUtils.getJsonObjectIntegerFieldValueOrDefault(var2, "height", this.mainFramebufferHeight);
         if (this.mapFramebuffers.containsKey(var3)) {
            throw new JsonException(String.valueOf((new StringBuilder(String.valueOf(var3))).append(" is already defined")));
         }

         this.addFramebuffer(var3, var4, var5);
      }

   }

   public void createBindFramebuffers(int var1, int var2) {
      this.mainFramebufferWidth = this.mainFramebuffer.framebufferTextureWidth;
      this.mainFramebufferHeight = this.mainFramebuffer.framebufferTextureHeight;
      this.resetProjectionMatrix();
      Iterator var3 = this.listShaders.iterator();

      while(var3.hasNext()) {
         Shader var4 = (Shader)var3.next();
         var4.setProjectionMatrix(this.projectionMatrix);
      }

      var3 = this.listFramebuffers.iterator();

      while(var3.hasNext()) {
         Framebuffer var5 = (Framebuffer)var3.next();
         var5.createBindFramebuffer(var1, var2);
      }

   }

   public final String getShaderGroupName() {
      return this.shaderGroupName;
   }
}
