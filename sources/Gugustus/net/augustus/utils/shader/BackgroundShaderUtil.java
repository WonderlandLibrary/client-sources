package net.augustus.utils.shader;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.logging.Level;
import net.augustus.Augustus;

public class BackgroundShaderUtil {
   private final ArrayList<ShaderUtil> shaders = new ArrayList<>();
   private final ArrayList<String> shaderNames = new ArrayList<>();
   private ShaderUtil currentShader;

   public BackgroundShaderUtil() {
      this.saveResource("shaders/abraxas.frag", "gugustus", false);
      this.saveResource("shaders/galaxy.frag", "gugustus", false);
      this.saveResource("shaders/galaxy2.frag", "gugustus", false);
      this.saveResource("shaders/purplewater.frag", "gugustus", false);
      this.saveResource("shaders/liquidbounce.frag", "gugustus", false);
      this.saveResource("shaders/redwaterlike.frag", "gugustus", false);
      this.saveResource("shaders/bluemetal.frag", "gugustus", false);
      this.saveResource("shaders/metal.frag", "gugustus", false);
      this.saveResource("shaders/snowstar.frag", "gugustus", false);
      this.saveResource("shaders/spider.frag", "gugustus", false);
      this.saveResource("shaders/trinity.frag", "gugustus", false);
      this.saveResource("shaders/bluefog.frag", "gugustus", false);
      this.saveResource("shaders/cloudtunnel.frag", "gugustus", false);
      this.saveResource("shaders/cute.frag", "gugustus", false);
      this.saveResource("shaders/idk.frag", "gugustus", false);
      this.saveResource("shaders/nature.frag", "gugustus", false);
      this.saveResource("shaders/race.frag", "gugustus", false);
      this.saveResource("shaders/mountains.frag", "gugustus", false);
      this.saveResource("shaders/purplecircle.frag", "gugustus", false);
      this.saveResource("shaders/RGB.frag", "gugustus", false);
      this.saveResource("shaders/sun.frag", "gugustus", false);
      this.saveResource("shaders/water.frag", "gugustus", false);
//      this.saveResource("shaders/RGBLines.frag", "gugustus", false);
      this.saveResource("shaders/wave.frag", "gugustus", false);
      this.saveResource("shaders/stadium.frag", "gugustus", false);
      this.saveResource("shaders/rainbowtunnel.frag", "gugustus", false);
      this.saveResource("shaders/rainbowwaves.frag", "gugustus", false);
      this.saveResource("shaders/reflection.frag", "gugustus", false);
      this.saveResource("shaders/rainbowdot.frag", "gugustus", false);
      this.saveResource("shaders/gamecube.frag", "gugustus", false);
      this.saveResource("shaders/car.frag", "gugustus", false);
      File folder = new File("gugustus/shaders");
      File[] listOfFiles = folder.listFiles();

      for(File file : listOfFiles) {
         if (file.getName().contains(".")) {
            String[] s = file.getName().split("\\.");
            if (s.length > 1 && s[1].equals("frag")) {
               ShaderUtil shaderUtil = new ShaderUtil();
               char[] arr = s[0].toCharArray();
               arr[0] = Character.toUpperCase(arr[0]);
               shaderUtil.createBackgroundShader(file.getAbsolutePath(), new String(arr));
               this.shaders.add(shaderUtil);
               this.shaderNames.add(shaderUtil.getName());
            }
         }
      }

      String shaderName = Augustus.getInstance().getConverter().readBackground();

      for(ShaderUtil shader : this.shaders) {
         if (shader.getName().equalsIgnoreCase(shaderName)) {
            this.currentShader = shader;
            return;
         }
      }

      this.currentShader = this.shaders.get(0);
   }

   public void setCurrentShader(String name) {
      for(ShaderUtil shaderUtil : this.shaders) {
         if (shaderUtil.getName().equalsIgnoreCase(name)) {
            this.currentShader = shaderUtil;
            Augustus.getInstance().getConverter().saveBackground(shaderUtil);
            return;
         }
      }
   }

   private void saveResource(String resourcePath, String dataFolder, boolean replace) {
      if (resourcePath != null && !resourcePath.equals("")) {
         resourcePath = resourcePath.replace('\\', '/');
         InputStream in = this.getClass().getClassLoader().getResourceAsStream(resourcePath);
         if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in client");
         } else {
            File outFile = new File(dataFolder, resourcePath);
            int lastIndex = resourcePath.lastIndexOf(47);
            File outDir = new File(dataFolder, resourcePath.substring(0, lastIndex >= 0 ? lastIndex : 0));
            if (!outDir.exists()) {
               outDir.mkdirs();
            }

            try {
               if (!outFile.exists() || replace) {
                  OutputStream out = new FileOutputStream(outFile);
                  byte[] buf = new byte[1024];

                  int len;
                  while((len = in.read(buf)) > 0) {
                     out.write(buf, 0, len);
                  }

                  out.close();
                  in.close();
               }
            } catch (IOException var11) {
               System.err.println(Level.SEVERE + "Could not save " + outFile.getName() + " to " + outFile);
            }
         }
      } else {
         throw new IllegalArgumentException("ResourcePath cannot be null or empty");
      }
   }

   public ShaderUtil getCurrentShader() {
      return this.currentShader;
   }

   public ArrayList<ShaderUtil> getShaders() {
      return this.shaders;
   }

   public ArrayList<String> getShaderNames() {
      return this.shaderNames;
   }
}
