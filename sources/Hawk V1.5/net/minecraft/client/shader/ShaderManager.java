package net.minecraft.client.shader;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonBlendingMode;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.JsonUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderManager {
   private static final String __OBFID = "CL_00001040";
   private final JsonBlendingMode field_148016_p;
   private static final ShaderDefault defaultShaderUniform = new ShaderDefault();
   private final ShaderLoader fragmentShaderLoader;
   private static boolean field_148000_e = true;
   private final List samplerNames;
   private static ShaderManager staticShaderManager = null;
   private static int currentProgram = -1;
   private boolean isDirty;
   private static final Logger logger = LogManager.getLogger();
   private final int program;
   private final List shaderUniforms;
   private final List field_148014_r;
   private final List field_148015_q;
   private final List shaderSamplerLocations;
   private final Map mappedShaderUniforms;
   private final Map shaderSamplers;
   private final List shaderUniformLocations;
   private final ShaderLoader vertexShaderLoader;
   private final boolean useFaceCulling;
   private final String programFilename;

   public void useShader() {
      this.isDirty = false;
      staticShaderManager = this;
      this.field_148016_p.func_148109_a();
      if (this.program != currentProgram) {
         OpenGlHelper.glUseProgram(this.program);
         currentProgram = this.program;
      }

      if (this.useFaceCulling) {
         GlStateManager.enableCull();
      } else {
         GlStateManager.disableCull();
      }

      for(int var1 = 0; var1 < this.shaderSamplerLocations.size(); ++var1) {
         if (this.shaderSamplers.get(this.samplerNames.get(var1)) != null) {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + var1);
            GlStateManager.func_179098_w();
            Object var2 = this.shaderSamplers.get(this.samplerNames.get(var1));
            int var3 = -1;
            if (var2 instanceof Framebuffer) {
               var3 = ((Framebuffer)var2).framebufferTexture;
            } else if (var2 instanceof ITextureObject) {
               var3 = ((ITextureObject)var2).getGlTextureId();
            } else if (var2 instanceof Integer) {
               var3 = (Integer)var2;
            }

            if (var3 != -1) {
               GlStateManager.func_179144_i(var3);
               OpenGlHelper.glUniform1i(OpenGlHelper.glGetUniformLocation(this.program, (CharSequence)this.samplerNames.get(var1)), var1);
            }
         }
      }

      Iterator var4 = this.shaderUniforms.iterator();

      while(var4.hasNext()) {
         ShaderUniform var5 = (ShaderUniform)var4.next();
         var5.upload();
      }

   }

   private void parseUniform(JsonElement var1) throws JsonException {
      JsonObject var2 = JsonUtils.getElementAsJsonObject(var1, "uniform");
      String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
      int var4 = ShaderUniform.parseType(JsonUtils.getJsonObjectStringFieldValue(var2, "type"));
      int var5 = JsonUtils.getJsonObjectIntegerFieldValue(var2, "count");
      float[] var6 = new float[Math.max(var5, 16)];
      JsonArray var7 = JsonUtils.getJsonObjectJsonArrayField(var2, "values");
      if (var7.size() != var5 && var7.size() > 1) {
         throw new JsonException(String.valueOf((new StringBuilder("Invalid amount of values specified (expected ")).append(var5).append(", found ").append(var7.size()).append(")")));
      } else {
         int var8 = 0;

         for(Iterator var9 = var7.iterator(); var9.hasNext(); ++var8) {
            JsonElement var10 = (JsonElement)var9.next();

            try {
               var6[var8] = JsonUtils.getJsonElementFloatValue(var10, "value");
            } catch (Exception var13) {
               JsonException var12 = JsonException.func_151379_a(var13);
               var12.func_151380_a(String.valueOf((new StringBuilder("values[")).append(var8).append("]")));
               throw var12;
            }
         }

         if (var5 > 1 && var7.size() == 1) {
            while(var8 < var5) {
               var6[var8] = var6[0];
               ++var8;
            }
         }

         int var14 = var5 > 1 && var5 <= 4 && var4 < 8 ? var5 - 1 : 0;
         ShaderUniform var15 = new ShaderUniform(var3, var4 + var14, var5, this);
         if (var4 <= 3) {
            var15.set((int)var6[0], (int)var6[1], (int)var6[2], (int)var6[3]);
         } else if (var4 <= 7) {
            var15.func_148092_b(var6[0], var6[1], var6[2], var6[3]);
         } else {
            var15.set(var6);
         }

         this.shaderUniforms.add(var15);
      }
   }

   public void endShader() {
      OpenGlHelper.glUseProgram(0);
      currentProgram = -1;
      staticShaderManager = null;
      field_148000_e = true;

      for(int var1 = 0; var1 < this.shaderSamplerLocations.size(); ++var1) {
         if (this.shaderSamplers.get(this.samplerNames.get(var1)) != null) {
            GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit + var1);
            GlStateManager.func_179144_i(0);
         }
      }

   }

   public ShaderUniform getShaderUniform(String var1) {
      return this.mappedShaderUniforms.containsKey(var1) ? (ShaderUniform)this.mappedShaderUniforms.get(var1) : null;
   }

   public ShaderUniform getShaderUniformOrDefault(String var1) {
      return (ShaderUniform)(this.mappedShaderUniforms.containsKey(var1) ? (ShaderUniform)this.mappedShaderUniforms.get(var1) : defaultShaderUniform);
   }

   public void addSamplerTexture(String var1, Object var2) {
      if (this.shaderSamplers.containsKey(var1)) {
         this.shaderSamplers.remove(var1);
      }

      this.shaderSamplers.put(var1, var2);
      this.markDirty();
   }

   private void setupUniforms() {
      int var1 = 0;

      String var2;
      int var3;
      for(int var4 = 0; var1 < this.samplerNames.size(); ++var4) {
         var2 = (String)this.samplerNames.get(var1);
         var3 = OpenGlHelper.glGetUniformLocation(this.program, var2);
         if (var3 == -1) {
            logger.warn(String.valueOf((new StringBuilder("Shader ")).append(this.programFilename).append("could not find sampler named ").append(var2).append(" in the specified shader program.")));
            this.shaderSamplers.remove(var2);
            this.samplerNames.remove(var4);
            --var4;
         } else {
            this.shaderSamplerLocations.add(var3);
         }

         ++var1;
      }

      Iterator var6 = this.shaderUniforms.iterator();

      while(var6.hasNext()) {
         ShaderUniform var5 = (ShaderUniform)var6.next();
         var2 = var5.getShaderName();
         var3 = OpenGlHelper.glGetUniformLocation(this.program, var2);
         if (var3 == -1) {
            logger.warn(String.valueOf((new StringBuilder("Could not find uniform named ")).append(var2).append(" in the specified").append(" shader program.")));
         } else {
            this.shaderUniformLocations.add(var3);
            var5.setUniformLocation(var3);
            this.mappedShaderUniforms.put(var2, var5);
         }
      }

   }

   public ShaderLoader getVertexShaderLoader() {
      return this.vertexShaderLoader;
   }

   public ShaderManager(IResourceManager param1, String param2) throws JsonException {
      // $FF: Couldn't be decompiled
   }

   public void deleteShader() {
      ShaderLinkHelper.getStaticShaderLinkHelper().deleteShader(this);
   }

   public void markDirty() {
      this.isDirty = true;
   }

   public int getProgram() {
      return this.program;
   }

   public ShaderLoader getFragmentShaderLoader() {
      return this.fragmentShaderLoader;
   }

   private void parseSampler(JsonElement var1) {
      JsonObject var2 = JsonUtils.getElementAsJsonObject(var1, "sampler");
      String var3 = JsonUtils.getJsonObjectStringFieldValue(var2, "name");
      if (!JsonUtils.jsonObjectFieldTypeIsString(var2, "file")) {
         this.shaderSamplers.put(var3, (Object)null);
         this.samplerNames.add(var3);
      } else {
         this.samplerNames.add(var3);
      }

   }
}
