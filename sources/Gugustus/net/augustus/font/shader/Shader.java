package net.augustus.font.shader;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import org.joml.Matrix3f;
import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL20;

public class Shader {
   private int shaderProgramID;
   private boolean beingUsed = false;
   private String vertexSource;
   private String fragmentSource;
   private String filepath;

   public Shader(String filepath) {
      this.filepath = filepath;

      try {
         String source = new String(Files.readAllBytes(Paths.get(filepath)));
         String[] splitString = source.split("(#type)( )+([a-zA-Z]+)");
         int index = source.indexOf("#type") + 6;
         int eol = source.indexOf("\r\n", index);
         String firstPattern = source.substring(index, eol).trim();
         index = source.indexOf("#type", eol) + 6;
         eol = source.indexOf("\r\n", index);
         String secondPattern = source.substring(index, eol).trim();
         if (firstPattern.equals("vertex")) {
            this.vertexSource = splitString[1];
         } else {
            if (!firstPattern.equals("fragment")) {
               throw new IOException("Unexpected token '" + firstPattern + "'");
            }

            this.fragmentSource = splitString[1];
         }

         if (secondPattern.equals("vertex")) {
            this.vertexSource = splitString[2];
         } else {
            if (!secondPattern.equals("fragment")) {
               throw new IOException("Unexpected token '" + secondPattern + "'");
            }

            this.fragmentSource = splitString[2];
         }
      } catch (IOException var8) {
         var8.printStackTrace();

         assert false : "Error: Could not open file for shader: '" + filepath + "'";
      }

      this.compile();
   }

   public void compile() {
      int vertexID = GL20.glCreateShader(35633);
      GL20.glShaderSource(vertexID, this.vertexSource);
      GL20.glCompileShader(vertexID);
      int success = GL20.glGetShaderi(vertexID, 35713);
      if (success == 0) {
         int len = GL20.glGetShaderi(vertexID, 35716);
         System.out.println("ERROR: '" + this.filepath + "'\n\tVertex shader compilation failed.");
         System.out.println(GL20.glGetShaderInfoLog(vertexID, len));

         assert false : "";
      }

      int fragmentID = GL20.glCreateShader(35632);
      GL20.glShaderSource(fragmentID, this.fragmentSource);
      GL20.glCompileShader(fragmentID);
      success = GL20.glGetShaderi(fragmentID, 35713);
      if (success == 0) {
         int len = GL20.glGetShaderi(fragmentID, 35716);
         System.out.println("ERROR: '" + this.filepath + "'\n\tFragment shader compilation failed.");
         System.out.println(GL20.glGetShaderInfoLog(fragmentID, len));

         assert false : "";
      }

      this.shaderProgramID = GL20.glCreateProgram();
      GL20.glAttachShader(this.shaderProgramID, vertexID);
      GL20.glAttachShader(this.shaderProgramID, fragmentID);
      GL20.glLinkProgram(this.shaderProgramID);
      success = GL20.glGetProgrami(this.shaderProgramID, 35714);
      if (success == 0) {
         int len = GL20.glGetProgrami(this.shaderProgramID, 35716);
         System.out.println("ERROR: '" + this.filepath + "'\n\tLinking of shaders failed.");
         System.out.println(GL20.glGetProgramInfoLog(this.shaderProgramID, len));

         assert false : "";
      }
   }

   public void use() {
      if (!this.beingUsed) {
         GL20.glUseProgram(this.shaderProgramID);
         this.beingUsed = true;
      }
   }

   public void detach() {
      GL20.glUseProgram(0);
      this.beingUsed = false;
   }

   public void uploadMat4f(String varName, Matrix4f mat4) {
      int varLocation = GL20.glGetUniformLocation(this.shaderProgramID, varName);
      this.use();
      FloatBuffer matBuffer = BufferUtils.createFloatBuffer(16);
      mat4.get(matBuffer);
      GL20.glUniformMatrix4(varLocation, false, matBuffer);
   }

   public void uploadMat3f(String varName, Matrix3f mat3) {
      int varLocation = GL20.glGetUniformLocation(this.shaderProgramID, varName);
      this.use();
      FloatBuffer matBuffer = BufferUtils.createFloatBuffer(9);
      mat3.get(matBuffer);
      GL20.glUniformMatrix3(varLocation, false, matBuffer);
   }

   public void uploadVec4f(String varName, Vector4f vec) {
      int varLocation = GL20.glGetUniformLocation(this.shaderProgramID, varName);
      this.use();
      GL20.glUniform4f(varLocation, vec.x, vec.y, vec.z, vec.w);
   }

   public void uploadVec3f(String varName, Vector3f vec) {
      int varLocation = GL20.glGetUniformLocation(this.shaderProgramID, varName);
      this.use();
      GL20.glUniform3f(varLocation, vec.x, vec.y, vec.z);
   }

   public void uploadVec2f(String varName, Vector2f vec) {
      int varLocation = GL20.glGetUniformLocation(this.shaderProgramID, varName);
      this.use();
      GL20.glUniform2f(varLocation, vec.x, vec.y);
   }

   public void uploadFloat(String varName, float val) {
      int varLocation = GL20.glGetUniformLocation(this.shaderProgramID, varName);
      this.use();
      GL20.glUniform1f(varLocation, val);
   }

   public void uploadInt(String varName, int val) {
      int varLocation = GL20.glGetUniformLocation(this.shaderProgramID, varName);
      this.use();
      GL20.glUniform1i(varLocation, val);
   }

   public void uploadTexture(String varName, int slot) {
      int varLocation = GL20.glGetUniformLocation(this.shaderProgramID, varName);
      this.use();
      GL20.glUniform1i(varLocation, slot);
   }

   public void uploadIntArray(String varName, int[] array) {
      int varLocation = GL20.glGetUniformLocation(this.shaderProgramID, varName);
      this.use();
   }
}
