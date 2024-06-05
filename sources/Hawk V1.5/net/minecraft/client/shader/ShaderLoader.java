package net.minecraft.client.shader;

import com.google.common.collect.Maps;
import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.util.Map;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;

public class ShaderLoader {
   private int shaderAttachCount = 0;
   private int shader;
   private final String shaderFilename;
   private static final String __OBFID = "CL_00001043";
   private final ShaderLoader.ShaderType shaderType;

   public String getShaderFilename() {
      return this.shaderFilename;
   }

   public void attachShader(ShaderManager var1) {
      ++this.shaderAttachCount;
      OpenGlHelper.glAttachShader(var1.getProgram(), this.shader);
   }

   public void deleteShader(ShaderManager var1) {
      --this.shaderAttachCount;
      if (this.shaderAttachCount <= 0) {
         OpenGlHelper.glDeleteShader(this.shader);
         this.shaderType.getLoadedShaders().remove(this.shaderFilename);
      }

   }

   public static ShaderLoader loadShader(IResourceManager var0, ShaderLoader.ShaderType var1, String var2) throws IOException {
      ShaderLoader var3 = (ShaderLoader)var1.getLoadedShaders().get(var2);
      if (var3 == null) {
         ResourceLocation var4 = new ResourceLocation(String.valueOf((new StringBuilder("shaders/program/")).append(var2).append(var1.getShaderExtension())));
         BufferedInputStream var5 = new BufferedInputStream(var0.getResource(var4).getInputStream());
         byte[] var6 = func_177064_a(var5);
         ByteBuffer var7 = BufferUtils.createByteBuffer(var6.length);
         var7.put(var6);
         var7.position(0);
         int var8 = OpenGlHelper.glCreateShader(var1.getShaderMode());
         OpenGlHelper.glShaderSource(var8, var7);
         OpenGlHelper.glCompileShader(var8);
         if (OpenGlHelper.glGetShaderi(var8, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
            String var9 = StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(var8, 32768));
            JsonException var10 = new JsonException(String.valueOf((new StringBuilder("Couldn't compile ")).append(var1.getShaderName()).append(" program: ").append(var9)));
            var10.func_151381_b(var4.getResourcePath());
            throw var10;
         }

         var3 = new ShaderLoader(var1, var8, var2);
         var1.getLoadedShaders().put(var2, var3);
      }

      return var3;
   }

   private ShaderLoader(ShaderLoader.ShaderType var1, int var2, String var3) {
      this.shaderType = var1;
      this.shader = var2;
      this.shaderFilename = var3;
   }

   protected static byte[] func_177064_a(BufferedInputStream var0) throws IOException {
      try {
         byte[] var1 = IOUtils.toByteArray(var0);
         var0.close();
         return var1;
      } finally {
         var0.close();
      }
   }

   public static enum ShaderType {
      private static final ShaderLoader.ShaderType[] ENUM$VALUES = new ShaderLoader.ShaderType[]{VERTEX, FRAGMENT};
      private final int shaderMode;
      private final String shaderName;
      private final Map loadedShaders = Maps.newHashMap();
      VERTEX("VERTEX", 0, "vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER);

      private final String shaderExtension;
      private static final ShaderLoader.ShaderType[] $VALUES = new ShaderLoader.ShaderType[]{VERTEX, FRAGMENT};
      private static final String __OBFID = "CL_00001044";
      FRAGMENT("FRAGMENT", 1, "fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);

      protected int getShaderMode() {
         return this.shaderMode;
      }

      private ShaderType(String var3, int var4, String var5, String var6, int var7) {
         this.shaderName = var5;
         this.shaderExtension = var6;
         this.shaderMode = var7;
      }

      protected String getShaderExtension() {
         return this.shaderExtension;
      }

      protected Map getLoadedShaders() {
         return this.loadedShaders;
      }

      public String getShaderName() {
         return this.shaderName;
      }
   }
}
