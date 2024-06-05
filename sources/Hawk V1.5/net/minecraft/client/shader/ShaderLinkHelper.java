package net.minecraft.client.shader;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.util.JsonException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderLinkHelper {
   private static ShaderLinkHelper staticShaderLinkHelper;
   private static final Logger logger = LogManager.getLogger();
   private static final String __OBFID = "CL_00001045";

   public void deleteShader(ShaderManager var1) {
      var1.getFragmentShaderLoader().deleteShader(var1);
      var1.getVertexShaderLoader().deleteShader(var1);
      OpenGlHelper.glDeleteProgram(var1.getProgram());
   }

   public void linkProgram(ShaderManager var1) {
      var1.getFragmentShaderLoader().attachShader(var1);
      var1.getVertexShaderLoader().attachShader(var1);
      OpenGlHelper.glLinkProgram(var1.getProgram());
      int var2 = OpenGlHelper.glGetProgrami(var1.getProgram(), OpenGlHelper.GL_LINK_STATUS);
      if (var2 == 0) {
         logger.warn(String.valueOf((new StringBuilder("Error encountered when linking program containing VS ")).append(var1.getVertexShaderLoader().getShaderFilename()).append(" and FS ").append(var1.getFragmentShaderLoader().getShaderFilename()).append(". Log output:")));
         logger.warn(OpenGlHelper.glGetProgramInfoLog(var1.getProgram(), 32768));
      }

   }

   public static ShaderLinkHelper getStaticShaderLinkHelper() {
      return staticShaderLinkHelper;
   }

   public int createProgram() throws JsonException {
      int var1 = OpenGlHelper.glCreateProgram();
      if (var1 <= 0) {
         throw new JsonException(String.valueOf((new StringBuilder("Could not create shader program (returned program ID ")).append(var1).append(")")));
      } else {
         return var1;
      }
   }

   public static void setNewStaticShaderLinkHelper() {
      staticShaderLinkHelper = new ShaderLinkHelper();
   }
}
