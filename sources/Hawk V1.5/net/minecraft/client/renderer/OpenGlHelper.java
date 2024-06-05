package net.minecraft.client.renderer;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import net.minecraft.client.Minecraft;
import optifine.Config;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.EXTBlendFuncSeparate;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GLContext;

public class OpenGlHelper {
   public static int field_176089_P;
   private static String field_153196_B = "";
   public static int field_176078_F;
   public static boolean field_153211_u;
   public static int field_176093_u;
   private static boolean openGL14;
   public static int GL_LINK_STATUS;
   private static boolean field_153213_x;
   public static int field_176086_J;
   public static int lightmapTexUnit;
   public static boolean shadersSupported;
   private static final String __OBFID = "CL_00001179";
   public static int field_153205_l;
   public static float lastBrightnessX = 0.0F;
   public static int field_176077_E;
   public static int field_176085_I;
   public static int field_176092_v;
   public static int field_153201_h;
   public static int field_153206_m;
   public static float lastBrightnessY = 0.0F;
   public static int field_176081_B;
   public static int field_153199_f;
   public static int anisotropicFilteringMax;
   public static int field_176095_s;
   public static int field_176082_C;
   private static boolean field_153214_y;
   public static int field_153200_g;
   public static int field_153198_e;
   public static boolean field_153197_d;
   public static int field_153202_i;
   public static int GL_COMPILE_STATUS;
   public static boolean field_176083_O;
   private static boolean field_153215_z;
   public static int defaultTexUnit;
   public static int field_153203_j;
   public static int field_153204_k;
   public static int field_176091_w;
   public static int GL_VERTEX_SHADER;
   public static int field_176087_K;
   public static int field_176096_r;
   private static boolean field_176088_V;
   public static int field_176094_t;
   public static boolean openGL21;
   public static int field_176084_H;
   public static boolean framebufferSupported;
   public static int field_176076_D;
   public static int field_176080_A;
   public static int field_176098_y;
   public static int field_176079_G;
   private static int field_153212_w;
   public static int field_176099_x;
   public static int field_176097_z;
   private static boolean field_176090_Y;
   public static int GL_FRAGMENT_SHADER;

   public static void func_153174_h(int var0) {
      if (framebufferSupported) {
         switch(field_153212_w) {
         case 0:
            GL30.glDeleteFramebuffers(var0);
            break;
         case 1:
            ARBFramebufferObject.glDeleteFramebuffers(var0);
            break;
         case 2:
            EXTFramebufferObject.glDeleteFramebuffersEXT(var0);
         }
      }

   }

   public static void setActiveTexture(int var0) {
      if (field_153215_z) {
         ARBMultitexture.glActiveTextureARB(var0);
      } else {
         GL13.glActiveTexture(var0);
      }

   }

   public static void glDeleteShader(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glDeleteObjectARB(var0);
      } else {
         GL20.glDeleteShader(var0);
      }

   }

   public static void func_153186_a(int var0, int var1, int var2, int var3) {
      if (framebufferSupported) {
         switch(field_153212_w) {
         case 0:
            GL30.glRenderbufferStorage(var0, var1, var2, var3);
            break;
         case 1:
            ARBFramebufferObject.glRenderbufferStorage(var0, var1, var2, var3);
            break;
         case 2:
            EXTFramebufferObject.glRenderbufferStorageEXT(var0, var1, var2, var3);
         }
      }

   }

   public static boolean areShadersSupported() {
      return shadersSupported;
   }

   public static void glUniform4(int var0, FloatBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform4ARB(var0, var1);
      } else {
         GL20.glUniform4(var0, var1);
      }

   }

   public static void func_153171_g(int var0, int var1) {
      if (framebufferSupported) {
         switch(field_153212_w) {
         case 0:
            GL30.glBindFramebuffer(var0, var1);
            break;
         case 1:
            ARBFramebufferObject.glBindFramebuffer(var0, var1);
            break;
         case 2:
            EXTFramebufferObject.glBindFramebufferEXT(var0, var1);
         }
      }

   }

   public static void glUniform1(int var0, IntBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform1ARB(var0, var1);
      } else {
         GL20.glUniform1(var0, var1);
      }

   }

   public static void setClientActiveTexture(int var0) {
      if (field_153215_z) {
         ARBMultitexture.glClientActiveTextureARB(var0);
      } else {
         GL13.glClientActiveTexture(var0);
      }

   }

   public static int glGetAttribLocation(int var0, CharSequence var1) {
      return field_153214_y ? ARBVertexShader.glGetAttribLocationARB(var0, var1) : GL20.glGetAttribLocation(var0, var1);
   }

   public static void func_176074_g(int var0) {
      if (field_176090_Y) {
         ARBVertexBufferObject.glDeleteBuffersARB(var0);
      } else {
         GL15.glDeleteBuffers(var0);
      }

   }

   public static void glUniform1i(int var0, int var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform1iARB(var0, var1);
      } else {
         GL20.glUniform1i(var0, var1);
      }

   }

   public static int glGetShaderi(int var0, int var1) {
      return field_153214_y ? ARBShaderObjects.glGetObjectParameteriARB(var0, var1) : GL20.glGetShaderi(var0, var1);
   }

   public static void glAttachShader(int var0, int var1) {
      if (field_153214_y) {
         ARBShaderObjects.glAttachObjectARB(var0, var1);
      } else {
         GL20.glAttachShader(var0, var1);
      }

   }

   public static void func_153176_h(int var0, int var1) {
      if (framebufferSupported) {
         switch(field_153212_w) {
         case 0:
            GL30.glBindRenderbuffer(var0, var1);
            break;
         case 1:
            ARBFramebufferObject.glBindRenderbuffer(var0, var1);
            break;
         case 2:
            EXTFramebufferObject.glBindRenderbufferEXT(var0, var1);
         }
      }

   }

   public static int func_153185_f() {
      if (!framebufferSupported) {
         return -1;
      } else {
         switch(field_153212_w) {
         case 0:
            return GL30.glGenRenderbuffers();
         case 1:
            return ARBFramebufferObject.glGenRenderbuffers();
         case 2:
            return EXTFramebufferObject.glGenRenderbuffersEXT();
         default:
            return -1;
         }
      }
   }

   public static void glLinkProgram(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glLinkProgramARB(var0);
      } else {
         GL20.glLinkProgram(var0);
      }

   }

   public static void func_153184_g(int var0) {
      if (framebufferSupported) {
         switch(field_153212_w) {
         case 0:
            GL30.glDeleteRenderbuffers(var0);
            break;
         case 1:
            ARBFramebufferObject.glDeleteRenderbuffers(var0);
            break;
         case 2:
            EXTFramebufferObject.glDeleteRenderbuffersEXT(var0);
         }
      }

   }

   public static void glUniform2(int var0, FloatBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform2ARB(var0, var1);
      } else {
         GL20.glUniform2(var0, var1);
      }

   }

   public static int func_176073_e() {
      return field_176090_Y ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
   }

   public static void glUniform1(int var0, FloatBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform1ARB(var0, var1);
      } else {
         GL20.glUniform1(var0, var1);
      }

   }

   public static void glUniformMatrix4(int var0, boolean var1, FloatBuffer var2) {
      if (field_153214_y) {
         ARBShaderObjects.glUniformMatrix4ARB(var0, var1, var2);
      } else {
         GL20.glUniformMatrix4(var0, var1, var2);
      }

   }

   public static void glDeleteProgram(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glDeleteObjectARB(var0);
      } else {
         GL20.glDeleteProgram(var0);
      }

   }

   public static void func_153190_b(int var0, int var1, int var2, int var3) {
      if (framebufferSupported) {
         switch(field_153212_w) {
         case 0:
            GL30.glFramebufferRenderbuffer(var0, var1, var2, var3);
            break;
         case 1:
            ARBFramebufferObject.glFramebufferRenderbuffer(var0, var1, var2, var3);
            break;
         case 2:
            EXTFramebufferObject.glFramebufferRenderbufferEXT(var0, var1, var2, var3);
         }
      }

   }

   public static void glBlendFunc(int var0, int var1, int var2, int var3) {
      if (openGL14) {
         if (field_153211_u) {
            EXTBlendFuncSeparate.glBlendFuncSeparateEXT(var0, var1, var2, var3);
         } else {
            GL14.glBlendFuncSeparate(var0, var1, var2, var3);
         }
      } else {
         GL11.glBlendFunc(var0, var1);
      }

   }

   public static void glShaderSource(int var0, ByteBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glShaderSourceARB(var0, var1);
      } else {
         GL20.glShaderSource(var0, var1);
      }

   }

   public static int glCreateProgram() {
      return field_153214_y ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
   }

   public static void glUniformMatrix3(int var0, boolean var1, FloatBuffer var2) {
      if (field_153214_y) {
         ARBShaderObjects.glUniformMatrix3ARB(var0, var1, var2);
      } else {
         GL20.glUniformMatrix3(var0, var1, var2);
      }

   }

   public static void func_176072_g(int var0, int var1) {
      if (field_176090_Y) {
         ARBVertexBufferObject.glBindBufferARB(var0, var1);
      } else {
         GL15.glBindBuffer(var0, var1);
      }

   }

   public static void glUniform3(int var0, FloatBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform3ARB(var0, var1);
      } else {
         GL20.glUniform3(var0, var1);
      }

   }

   public static void glUniform2(int var0, IntBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform2ARB(var0, var1);
      } else {
         GL20.glUniform2(var0, var1);
      }

   }

   public static void initializeTextures() {
      Config.initDisplay();
      ContextCapabilities var0 = GLContext.getCapabilities();
      field_153215_z = var0.GL_ARB_multitexture && !var0.OpenGL13;
      field_176088_V = var0.GL_ARB_texture_env_combine && !var0.OpenGL13;
      if (field_153215_z) {
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("Using ARB_multitexture.\n"));
         defaultTexUnit = 33984;
         lightmapTexUnit = 33985;
         field_176096_r = 33986;
      } else {
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("Using GL 1.3 multitexturing.\n"));
         defaultTexUnit = 33984;
         lightmapTexUnit = 33985;
         field_176096_r = 33986;
      }

      if (field_176088_V) {
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("Using ARB_texture_env_combine.\n"));
         field_176095_s = 34160;
         field_176094_t = 34165;
         field_176093_u = 34167;
         field_176092_v = 34166;
         field_176091_w = 34168;
         field_176099_x = 34161;
         field_176098_y = 34176;
         field_176097_z = 34177;
         field_176080_A = 34178;
         field_176081_B = 34192;
         field_176082_C = 34193;
         field_176076_D = 34194;
         field_176077_E = 34162;
         field_176078_F = 34184;
         field_176079_G = 34185;
         field_176084_H = 34186;
         field_176085_I = 34200;
         field_176086_J = 34201;
         field_176087_K = 34202;
      } else {
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("Using GL 1.3 texture combiners.\n"));
         field_176095_s = 34160;
         field_176094_t = 34165;
         field_176093_u = 34167;
         field_176092_v = 34166;
         field_176091_w = 34168;
         field_176099_x = 34161;
         field_176098_y = 34176;
         field_176097_z = 34177;
         field_176080_A = 34178;
         field_176081_B = 34192;
         field_176082_C = 34193;
         field_176076_D = 34194;
         field_176077_E = 34162;
         field_176078_F = 34184;
         field_176079_G = 34185;
         field_176084_H = 34186;
         field_176085_I = 34200;
         field_176086_J = 34201;
         field_176087_K = 34202;
      }

      field_153211_u = var0.GL_EXT_blend_func_separate && !var0.OpenGL14;
      openGL14 = var0.OpenGL14 || var0.GL_EXT_blend_func_separate;
      framebufferSupported = openGL14 && (var0.GL_ARB_framebuffer_object || var0.GL_EXT_framebuffer_object || var0.OpenGL30);
      if (framebufferSupported) {
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("Using framebuffer objects because "));
         if (var0.OpenGL30) {
            field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("OpenGL 3.0 is supported and separate blending is supported.\n"));
            field_153212_w = 0;
            field_153198_e = 36160;
            field_153199_f = 36161;
            field_153200_g = 36064;
            field_153201_h = 36096;
            field_153202_i = 36053;
            field_153203_j = 36054;
            field_153204_k = 36055;
            field_153205_l = 36059;
            field_153206_m = 36060;
         } else if (var0.GL_ARB_framebuffer_object) {
            field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("ARB_framebuffer_object is supported and separate blending is supported.\n"));
            field_153212_w = 1;
            field_153198_e = 36160;
            field_153199_f = 36161;
            field_153200_g = 36064;
            field_153201_h = 36096;
            field_153202_i = 36053;
            field_153204_k = 36055;
            field_153203_j = 36054;
            field_153205_l = 36059;
            field_153206_m = 36060;
         } else if (var0.GL_EXT_framebuffer_object) {
            field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("EXT_framebuffer_object is supported.\n"));
            field_153212_w = 2;
            field_153198_e = 36160;
            field_153199_f = 36161;
            field_153200_g = 36064;
            field_153201_h = 36096;
            field_153202_i = 36053;
            field_153204_k = 36055;
            field_153203_j = 36054;
            field_153205_l = 36059;
            field_153206_m = 36060;
         }
      } else {
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("Not using framebuffer objects because "));
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("OpenGL 1.4 is ").append(var0.OpenGL14 ? "" : "not ").append("supported, "));
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("EXT_blend_func_separate is ").append(var0.GL_EXT_blend_func_separate ? "" : "not ").append("supported, "));
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("OpenGL 3.0 is ").append(var0.OpenGL30 ? "" : "not ").append("supported, "));
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("ARB_framebuffer_object is ").append(var0.GL_ARB_framebuffer_object ? "" : "not ").append("supported, and "));
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("EXT_framebuffer_object is ").append(var0.GL_EXT_framebuffer_object ? "" : "not ").append("supported.\n"));
      }

      openGL21 = var0.OpenGL21;
      field_153213_x = openGL21 || var0.GL_ARB_vertex_shader && var0.GL_ARB_fragment_shader && var0.GL_ARB_shader_objects;
      field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("Shaders are ").append(field_153213_x ? "" : "not ").append("available because "));
      if (field_153213_x) {
         if (var0.OpenGL21) {
            field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("OpenGL 2.1 is supported.\n"));
            field_153214_y = false;
            GL_LINK_STATUS = 35714;
            GL_COMPILE_STATUS = 35713;
            GL_VERTEX_SHADER = 35633;
            GL_FRAGMENT_SHADER = 35632;
         } else {
            field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n"));
            field_153214_y = true;
            GL_LINK_STATUS = 35714;
            GL_COMPILE_STATUS = 35713;
            GL_VERTEX_SHADER = 35633;
            GL_FRAGMENT_SHADER = 35632;
         }
      } else {
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("OpenGL 2.1 is ").append(var0.OpenGL21 ? "" : "not ").append("supported, "));
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("ARB_shader_objects is ").append(var0.GL_ARB_shader_objects ? "" : "not ").append("supported, "));
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("ARB_vertex_shader is ").append(var0.GL_ARB_vertex_shader ? "" : "not ").append("supported, and "));
         field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("ARB_fragment_shader is ").append(var0.GL_ARB_fragment_shader ? "" : "not ").append("supported.\n"));
      }

      shadersSupported = framebufferSupported && field_153213_x;
      field_153197_d = GL11.glGetString(7936).toLowerCase().contains("nvidia");
      field_176090_Y = !var0.OpenGL15 && var0.GL_ARB_vertex_buffer_object;
      field_176083_O = var0.OpenGL15 || field_176090_Y;
      field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("VBOs are ").append(field_176083_O ? "" : "not ").append("available because "));
      if (field_176083_O) {
         if (field_176090_Y) {
            field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("ARB_vertex_buffer_object is supported.\n"));
            anisotropicFilteringMax = 35044;
            field_176089_P = 34962;
         } else {
            field_153196_B = String.valueOf((new StringBuilder(String.valueOf(field_153196_B))).append("OpenGL 1.5 is supported.\n"));
            anisotropicFilteringMax = 35044;
            field_176089_P = 34962;
         }
      }

   }

   public static boolean isFramebufferEnabled() {
      return Config.isFastRender() ? false : (Config.isAntialiasing() ? false : framebufferSupported && Minecraft.getMinecraft().gameSettings.fboEnable);
   }

   public static int func_153165_e() {
      if (!framebufferSupported) {
         return -1;
      } else {
         switch(field_153212_w) {
         case 0:
            return GL30.glGenFramebuffers();
         case 1:
            return ARBFramebufferObject.glGenFramebuffers();
         case 2:
            return EXTFramebufferObject.glGenFramebuffersEXT();
         default:
            return -1;
         }
      }
   }

   public static void glUniform3(int var0, IntBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform3ARB(var0, var1);
      } else {
         GL20.glUniform3(var0, var1);
      }

   }

   public static void func_153188_a(int var0, int var1, int var2, int var3, int var4) {
      if (framebufferSupported) {
         switch(field_153212_w) {
         case 0:
            GL30.glFramebufferTexture2D(var0, var1, var2, var3, var4);
            break;
         case 1:
            ARBFramebufferObject.glFramebufferTexture2D(var0, var1, var2, var3, var4);
            break;
         case 2:
            EXTFramebufferObject.glFramebufferTexture2DEXT(var0, var1, var2, var3, var4);
         }
      }

   }

   public static String glGetShaderInfoLog(int var0, int var1) {
      return field_153214_y ? ARBShaderObjects.glGetInfoLogARB(var0, var1) : GL20.glGetShaderInfoLog(var0, var1);
   }

   public static void func_176071_a(int var0, ByteBuffer var1, int var2) {
      if (field_176090_Y) {
         ARBVertexBufferObject.glBufferDataARB(var0, var1, var2);
      } else {
         GL15.glBufferData(var0, var1, var2);
      }

   }

   public static void glUniformMatrix2(int var0, boolean var1, FloatBuffer var2) {
      if (field_153214_y) {
         ARBShaderObjects.glUniformMatrix2ARB(var0, var1, var2);
      } else {
         GL20.glUniformMatrix2(var0, var1, var2);
      }

   }

   public static void glUniform4(int var0, IntBuffer var1) {
      if (field_153214_y) {
         ARBShaderObjects.glUniform4ARB(var0, var1);
      } else {
         GL20.glUniform4(var0, var1);
      }

   }

   public static int func_153167_i(int var0) {
      if (!framebufferSupported) {
         return -1;
      } else {
         switch(field_153212_w) {
         case 0:
            return GL30.glCheckFramebufferStatus(var0);
         case 1:
            return ARBFramebufferObject.glCheckFramebufferStatus(var0);
         case 2:
            return EXTFramebufferObject.glCheckFramebufferStatusEXT(var0);
         default:
            return -1;
         }
      }
   }

   public static void glUseProgram(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glUseProgramObjectARB(var0);
      } else {
         GL20.glUseProgram(var0);
      }

   }

   public static int glGetUniformLocation(int var0, CharSequence var1) {
      return field_153214_y ? ARBShaderObjects.glGetUniformLocationARB(var0, var1) : GL20.glGetUniformLocation(var0, var1);
   }

   public static String glGetProgramInfoLog(int var0, int var1) {
      return field_153214_y ? ARBShaderObjects.glGetInfoLogARB(var0, var1) : GL20.glGetProgramInfoLog(var0, var1);
   }

   public static void setLightmapTextureCoords(int var0, float var1, float var2) {
      if (field_153215_z) {
         ARBMultitexture.glMultiTexCoord2fARB(var0, var1, var2);
      } else {
         GL13.glMultiTexCoord2f(var0, var1, var2);
      }

      if (var0 == lightmapTexUnit) {
         lastBrightnessX = var1;
         lastBrightnessY = var2;
      }

   }

   public static String func_153172_c() {
      return field_153196_B;
   }

   public static boolean func_176075_f() {
      return Config.isMultiTexture() ? false : field_176083_O && Minecraft.getMinecraft().gameSettings.field_178881_t;
   }

   public static int glGetProgrami(int var0, int var1) {
      return field_153214_y ? ARBShaderObjects.glGetObjectParameteriARB(var0, var1) : GL20.glGetProgrami(var0, var1);
   }

   public static int glCreateShader(int var0) {
      return field_153214_y ? ARBShaderObjects.glCreateShaderObjectARB(var0) : GL20.glCreateShader(var0);
   }

   public static void glCompileShader(int var0) {
      if (field_153214_y) {
         ARBShaderObjects.glCompileShaderARB(var0);
      } else {
         GL20.glCompileShader(var0);
      }

   }
}
