package net.shadersmod.client;

import java.io.InputStream;
import net.shadersmod.client.IShaderPack;
import net.shadersmod.client.Shaders;

public class ShaderPackNone implements IShaderPack {
   public void close() {
   }

   public InputStream getResourceAsStream(String var1) {
      return null;
   }

   public boolean hasDirectory(String var1) {
      return false;
   }

   public String getName() {
      return Shaders.packNameNone;
   }
}
