package net.minecraft.client.shader;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.shader.ShaderLoader$ShaderType;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.client.util.JsonException;
import net.minecraft.util.ResourceLocation;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.lwjgl.BufferUtils;

public class ShaderLoader {
   private final ShaderLoader$ShaderType shaderType;
   private final String shaderFilename;
   private int shader;
   private int shaderAttachCount = 0;

   private ShaderLoader(ShaderLoader$ShaderType var1, int var2, String var3) {
      this.shaderType = var1;
      this.shader = var2;
      this.shaderFilename = var3;
   }

   public void attachShader(ShaderManager var1) {
      ++this.shaderAttachCount;
      OpenGlHelper.glAttachShader(var1.getProgram(), this.shader);
   }

   public void deleteShader(ShaderManager var1) {
      --this.shaderAttachCount;
      if(this.shaderAttachCount <= 0) {
         OpenGlHelper.glDeleteShader(this.shader);
         this.shaderType.getLoadedShaders().remove(this.shaderFilename);
      }

   }

   public String getShaderFilename() {
      return this.shaderFilename;
   }

   public static ShaderLoader loadShader(IResourceManager var0, ShaderLoader$ShaderType var1, String var2) throws IOException {
      ShaderLoader var3 = (ShaderLoader)var1.getLoadedShaders().get(var2);
      ResourceLocation var4 = new ResourceLocation("shaders/program/" + var2 + var1.getShaderExtension());
      BufferedInputStream var5 = new BufferedInputStream(var0.getResource(var4).getInputStream());
      byte[] var6 = toByteArray(var5);
      ByteBuffer var7 = BufferUtils.createByteBuffer(var6.length);
      var7.put(var6);
      var7.position(0);
      int var8 = OpenGlHelper.glCreateShader(var1.getShaderMode());
      OpenGlHelper.glShaderSource(var8, var7);
      OpenGlHelper.glCompileShader(var8);
      if(OpenGlHelper.glGetShaderi(var8, OpenGlHelper.GL_COMPILE_STATUS) == 0) {
         String var9 = StringUtils.trim(OpenGlHelper.glGetShaderInfoLog(var8, '耀'));
         JsonException var10 = new JsonException("Couldn\'t compile " + var1.getShaderName() + " program: " + var9);
         var10.func_151381_b(var4.getResourcePath());
         throw var10;
      } else {
         var3 = new ShaderLoader(var1, var8, var2);
         var1.getLoadedShaders().put(var2, var3);
         return var3;
      }
   }

   protected static byte[] toByteArray(BufferedInputStream var0) throws IOException {
      BufferedInputStream var10000 = var0;

      byte[] var1;
      try {
         var1 = IOUtils.toByteArray(var10000);
      } finally {
         var0.close();
      }

      return var1;
   }
}
