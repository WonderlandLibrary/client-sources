package net.minecraft.client.shader;

import com.google.common.collect.Maps;
import java.util.Map;
import net.minecraft.client.renderer.OpenGlHelper;

public enum ShaderLoader$ShaderType {
   VERTEX("vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER),
   FRAGMENT("fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);

   private final String shaderName;
   private final String shaderExtension;
   private final int shaderMode;
   private final Map loadedShaders = Maps.newHashMap();
   private static final ShaderLoader$ShaderType[] $VALUES = new ShaderLoader$ShaderType[]{VERTEX, FRAGMENT};

   private ShaderLoader$ShaderType(String var3, String var4, int var5) {
      this.shaderName = var3;
      this.shaderExtension = var4;
      this.shaderMode = var5;
   }

   public String getShaderName() {
      return this.shaderName;
   }

   protected String getShaderExtension() {
      return this.shaderExtension;
   }

   protected int getShaderMode() {
      return this.shaderMode;
   }

   protected Map getLoadedShaders() {
      return this.loadedShaders;
   }
}
