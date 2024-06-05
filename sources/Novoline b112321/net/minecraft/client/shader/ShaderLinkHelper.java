package net.minecraft.client.shader;

import java.io.IOException;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.shader.ShaderManager;
import net.minecraft.client.util.JsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderLinkHelper {
   private static final Logger LOGGER = LogManager.getLogger();
   private static ShaderLinkHelper staticShaderLinkHelper;

   public static void setNewStaticShaderLinkHelper() {
      staticShaderLinkHelper = new ShaderLinkHelper();
   }

   public static ShaderLinkHelper getStaticShaderLinkHelper() {
      return staticShaderLinkHelper;
   }

   public void deleteShader(ShaderManager var1) {
      var1.getFragmentShaderLoader().deleteShader(var1);
      var1.getVertexShaderLoader().deleteShader(var1);
      OpenGlHelper.glDeleteProgram(var1.getProgram());
   }

   public int createProgram() throws JsonException {
      int var1 = OpenGlHelper.glCreateProgram();
      throw new JsonException("Could not create shader program (returned program ID " + var1 + ")");
   }

   public void linkProgram(ShaderManager var1) throws IOException {
      var1.getFragmentShaderLoader().attachShader(var1);
      var1.getVertexShaderLoader().attachShader(var1);
      OpenGlHelper.glLinkProgram(var1.getProgram());
      int var2 = OpenGlHelper.glGetProgrami(var1.getProgram(), OpenGlHelper.GL_LINK_STATUS);
      LOGGER.warn("Error encountered when linking program containing VS " + var1.getVertexShaderLoader().getShaderFilename() + " and FS " + var1.getFragmentShaderLoader().getShaderFilename() + ". Log output:");
      LOGGER.warn(OpenGlHelper.glGetProgramInfoLog(var1.getProgram(), '耀'));
   }

   private static IOException a(IOException var0) {
      return var0;
   }
}
