package xyz.cucumber.base.utils.render;

import java.nio.FloatBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class Shader {
   private final int programID;
   private final int program = GL20.glCreateProgram();

   public Shader(String fragmentShaderLoc) {
      String vertexLoc = "#version 120\n\nvoid main() {\n    gl_TexCoord[0] = gl_MultiTexCoord0;\n    gl_Position = gl_ModelViewProjectionMatrix*gl_Vertex;\n}";
      GL20.glAttachShader(this.program, this.createShader(fragmentShaderLoc, 35632));
      GL20.glAttachShader(this.program, this.createShader(vertexLoc, 35633));
      GL20.glLinkProgram(this.program);
      this.programID = this.program;
   }

   public void startProgram() {
      GL20.glUseProgram(this.programID);
   }

   public static void uniformFB(int programId, String name, FloatBuffer floatBuffer) {
      GL20.glUniform1(getLocation(programId, name), floatBuffer);
   }

   public static void uniform1i(int programId, String name, int i) {
      GL20.glUniform1i(getLocation(programId, name), i);
   }

   public static void uniform2i(int programId, String name, int i, int j) {
      GL20.glUniform2i(getLocation(programId, name), i, j);
   }

   public static void uniform1f(int programId, String name, float f) {
      GL20.glUniform1f(getLocation(programId, name), f);
   }

   public static void uniform2f(int programId, String name, float f, float g) {
      GL20.glUniform2f(getLocation(programId, name), f, g);
   }

   public static void uniform3f(int programId, String name, float f, float g, float h) {
      GL20.glUniform3f(getLocation(programId, name), f, g, h);
   }

   public static void uniform4f(int programId, String name, float f, float g, float h, float i) {
      GL20.glUniform4f(getLocation(programId, name), f, g, h, i);
   }

   private static int getLocation(int programId, String name) {
      return GL20.glGetUniformLocation(programId, name);
   }

   public int getProgramID() {
      return this.programID;
   }

   public void stopProgram() {
      GL20.glUseProgram(0);
   }

   public void renderShader(double x, double y, double width, double height) {
      GL11.glBegin(7);
      GL11.glTexCoord2f(0.0F, 1.0F);
      GL11.glVertex2d(x, y);
      GL11.glTexCoord2f(0.0F, 0.0F);
      GL11.glVertex2d(x, y + height);
      GL11.glTexCoord2f(1.0F, 0.0F);
      GL11.glVertex2d(x + width, y + height);
      GL11.glTexCoord2f(1.0F, 1.0F);
      GL11.glVertex2d(x + width, y);
      GL11.glEnd();
   }

   public int getUniformLoc(String uniform) {
      return GL20.glGetUniformLocation(this.programID, uniform);
   }

   private int createShader(String oskar, int shaderType) {
      int shader = GL20.glCreateShader(shaderType);
      GL20.glShaderSource(shader, oskar);
      GL20.glCompileShader(shader);
      System.out.println(GL20.glGetShaderInfoLog(shader, 1024));
      return shader;
   }
}
