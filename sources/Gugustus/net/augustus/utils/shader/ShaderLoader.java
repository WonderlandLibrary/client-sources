package net.augustus.utils.shader;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.GL20;

public class ShaderLoader {
   private final int shaderProgram = GL20.glCreateProgram();
   private final int timeUniform;
   private final int mouseUniform;
   private final int resolutionUniform;

   public ShaderLoader(String vertexSource, String fragmentSource) {
      int vertexShader = GL20.glCreateShader(35633);
      int fragmentShader = GL20.glCreateShader(35632);
      String vertexShaderSource = "";
      StringBuilder fragmentShaderSource = new StringBuilder();
      ClassLoader classLoader = this.getClass().getClassLoader();

      try (InputStream inputStream = classLoader.getResourceAsStream(vertexSource)) {
         vertexShaderSource = IOUtils.toString(inputStream, StandardCharsets.UTF_8);
      } catch (IOException var23) {
         System.err.println("Vertex shader was not load properly");
      }

      try {
         BufferedReader reader = new BufferedReader(new FileReader(fragmentSource));

         String line;
         while((line = reader.readLine()) != null) {
            fragmentShaderSource.append(line).append('\n');
         }

         reader.close();
      } catch (IOException var21) {
         System.err.println("Fragment shader was not load properly");
      }

      GL20.glShaderSource(vertexShader, vertexShaderSource);
      GL20.glCompileShader(vertexShader);
      if (GL20.glGetShaderi(vertexShader, 35713) == 0) {
         System.err.println("Vertex shader wasn't able to be compiled correctly :(");
      }

      GL20.glShaderSource(fragmentShader, fragmentShaderSource);
      GL20.glCompileShader(fragmentShader);
      if (GL20.glGetShaderi(fragmentShader, 35713) == 0) {
         System.err.println("Fragment shader wasn't able to be compiled correctly :(");
      }

      GL20.glAttachShader(this.shaderProgram, vertexShader);
      GL20.glAttachShader(this.shaderProgram, fragmentShader);
      GL20.glLinkProgram(this.shaderProgram);
      GL20.glValidateProgram(this.shaderProgram);
      GL20.glUseProgram(this.shaderProgram);
      this.timeUniform = GL20.glGetUniformLocation(this.shaderProgram, "time");
      this.mouseUniform = GL20.glGetUniformLocation(this.shaderProgram, "mouse");
      this.resolutionUniform = GL20.glGetUniformLocation(this.shaderProgram, "resolution");
      GL20.glUseProgram(0);
   }

   public void startShader(int width, int height, float mouseX, float mouseY, float time) {
      GL20.glUseProgram(this.shaderProgram);
      GL20.glUniform2f(this.resolutionUniform, (float)width, (float)height);
      GL20.glUniform2f(this.mouseUniform, mouseX / (float)width, 1.0F - mouseY / (float)height);
      GL20.glUniform1f(this.timeUniform, time);
   }

   public void endShader() {
      GL20.glUseProgram(0);
   }
}
