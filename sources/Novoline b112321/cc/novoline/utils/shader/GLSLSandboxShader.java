package cc.novoline.utils.shader;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import net.acE;
import org.lwjgl.opengl.GL20;

public class GLSLSandboxShader {
   private final int programId;
   private final int timeUniform;
   private final int mouseUniform;
   private final int resolutionUniform;
   private static int[] b;

   public GLSLSandboxShader(String var1) throws IOException {
      b();
      super();
      int var3 = GL20.glCreateProgram();
      GL20.glAttachShader(var3, this.createShader(GLSLSandboxShader.class.getResourceAsStream("/assets/minecraft/shaders/program/novoline_vector.vsh"), '謱'));
      GL20.glAttachShader(var3, this.createShader(GLSLSandboxShader.class.getResourceAsStream(var1), '謰'));
      GL20.glLinkProgram(var3);
      int var4 = GL20.glGetProgrami(var3, '讂');
      if(var4 == 0) {
         System.err.println(GL20.glGetProgramInfoLog(var3, GL20.glGetProgrami(var3, '讄')));
         throw new IllegalStateException("link");
      } else {
         this.programId = var3;
         GL20.glUseProgram(var3);
         this.timeUniform = GL20.glGetUniformLocation(var3, "time");
         this.mouseUniform = GL20.glGetUniformLocation(var3, "mouse");
         this.resolutionUniform = GL20.glGetUniformLocation(var3, "resolution");
         GL20.glUseProgram(0);
      }
   }

   public void useShader(int var1, int var2, float var3, float var4, float var5) {
      GL20.glUseProgram(this.programId);
      GL20.glUniform2f(this.resolutionUniform, (float)var1, (float)var2);
      GL20.glUniform2f(this.mouseUniform, var3 / (float)var1, 1.0F - var4 / (float)var2);
      b();
      GL20.glUniform1f(this.timeUniform, var5);
      if(acE.b() == null) {
         b(new int[4]);
      }

   }

   private int createShader(InputStream var1, int var2) throws IOException {
      b();
      int var4 = GL20.glCreateShader(var2);
      GL20.glShaderSource(var4, this.readStreamToString(var1));
      GL20.glCompileShader(var4);
      int var5 = GL20.glGetShaderi(var4, '讁');
      if(var5 == 0) {
         System.err.println(GL20.glGetShaderInfoLog(var4, GL20.glGetShaderi(var4, '讄')));
         throw new IllegalStateException("compile");
      } else {
         return var4;
      }
   }

   private String readStreamToString(InputStream var1) throws IOException {
      b();
      ByteArrayOutputStream var3 = new ByteArrayOutputStream();
      byte[] var4 = new byte[512];
      int var5;
      if((var5 = var1.read(var4, 0, var4.length)) != -1) {
         var3.write(var4, 0, var5);
      }

      return new String(var3.toByteArray(), StandardCharsets.UTF_8);
   }

   public static void b(int[] var0) {
      b = var0;
   }

   public static int[] b() {
      return b;
   }

   private static Exception a(Exception var0) {
      return var0;
   }

   static {
      b(new int[3]);
   }
}
