package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.GL14;
import org.lwjgl.opengl.EXTBlendFuncSeparate;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.ARBMultitexture;
import org.lwjgl.opengl.EXTFramebufferObject;
import org.lwjgl.opengl.ARBFramebufferObject;
import org.lwjgl.opengl.GL30;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.ARBVertexBufferObject;
import org.lwjgl.opengl.ARBVertexShader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ByteBuffer;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.ContextCapabilities;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;

public class OpenGlHelper
{
    public static boolean HorizonCode_Horizon_È;
    public static int Â;
    public static int Ý;
    public static int Ø­áŒŠá;
    public static int Âµá€;
    public static int Ó;
    public static int à;
    public static int Ø;
    public static int áŒŠÆ;
    public static int áˆºÑ¢Õ;
    private static int áŒŠ;
    public static boolean ÂµÈ;
    private static boolean £ÂµÄ;
    private static boolean Ø­Âµ;
    public static int á;
    public static int ˆÏ­;
    public static int £á;
    public static int Å;
    private static boolean Ä;
    public static int £à;
    public static int µà;
    public static int ˆà;
    private static boolean Ñ¢Â;
    public static int ¥Æ;
    public static int Ø­à;
    public static int µÕ;
    public static int Æ;
    public static int Šáƒ;
    public static int Ï­Ðƒà;
    public static int áŒŠà;
    public static int ŠÄ;
    public static int Ñ¢á;
    public static int ŒÏ;
    public static int Çªà¢;
    public static int Ê;
    public static int ÇŽÉ;
    public static int ˆá;
    public static int ÇŽÕ;
    public static int É;
    public static int áƒ;
    public static int á€;
    public static int Õ;
    private static boolean Ï­à;
    public static boolean à¢;
    public static boolean ŠÂµà;
    public static boolean ¥à;
    private static String áˆºáˆºÈ;
    public static boolean Âµà;
    private static boolean ÇŽá€;
    public static int Ç;
    public static int È;
    private static final String Ï = "CL_00001179";
    public static float áŠ;
    public static float ˆáŠ;
    
    static {
        OpenGlHelper.áˆºáˆºÈ = "";
        OpenGlHelper.áŠ = 0.0f;
        OpenGlHelper.ˆáŠ = 0.0f;
    }
    
    public static void HorizonCode_Horizon_È() {
        Config.Â();
        final ContextCapabilities var0 = GLContext.getCapabilities();
        OpenGlHelper.Ä = (var0.GL_ARB_multitexture && !var0.OpenGL13);
        OpenGlHelper.Ñ¢Â = (var0.GL_ARB_texture_env_combine && !var0.OpenGL13);
        if (OpenGlHelper.Ä) {
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "Using ARB_multitexture.\n";
            OpenGlHelper.£à = 33984;
            OpenGlHelper.µà = 33985;
            OpenGlHelper.ˆà = 33986;
        }
        else {
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "Using GL 1.3 multitexturing.\n";
            OpenGlHelper.£à = 33984;
            OpenGlHelper.µà = 33985;
            OpenGlHelper.ˆà = 33986;
        }
        if (OpenGlHelper.Ñ¢Â) {
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "Using ARB_texture_env_combine.\n";
            OpenGlHelper.¥Æ = 34160;
            OpenGlHelper.Ø­à = 34165;
            OpenGlHelper.µÕ = 34167;
            OpenGlHelper.Æ = 34166;
            OpenGlHelper.Šáƒ = 34168;
            OpenGlHelper.Ï­Ðƒà = 34161;
            OpenGlHelper.áŒŠà = 34176;
            OpenGlHelper.ŠÄ = 34177;
            OpenGlHelper.Ñ¢á = 34178;
            OpenGlHelper.ŒÏ = 34192;
            OpenGlHelper.Çªà¢ = 34193;
            OpenGlHelper.Ê = 34194;
            OpenGlHelper.ÇŽÉ = 34162;
            OpenGlHelper.ˆá = 34184;
            OpenGlHelper.ÇŽÕ = 34185;
            OpenGlHelper.É = 34186;
            OpenGlHelper.áƒ = 34200;
            OpenGlHelper.á€ = 34201;
            OpenGlHelper.Õ = 34202;
        }
        else {
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "Using GL 1.3 texture combiners.\n";
            OpenGlHelper.¥Æ = 34160;
            OpenGlHelper.Ø­à = 34165;
            OpenGlHelper.µÕ = 34167;
            OpenGlHelper.Æ = 34166;
            OpenGlHelper.Šáƒ = 34168;
            OpenGlHelper.Ï­Ðƒà = 34161;
            OpenGlHelper.áŒŠà = 34176;
            OpenGlHelper.ŠÄ = 34177;
            OpenGlHelper.Ñ¢á = 34178;
            OpenGlHelper.ŒÏ = 34192;
            OpenGlHelper.Çªà¢ = 34193;
            OpenGlHelper.Ê = 34194;
            OpenGlHelper.ÇŽÉ = 34162;
            OpenGlHelper.ˆá = 34184;
            OpenGlHelper.ÇŽÕ = 34185;
            OpenGlHelper.É = 34186;
            OpenGlHelper.áƒ = 34200;
            OpenGlHelper.á€ = 34201;
            OpenGlHelper.Õ = 34202;
        }
        OpenGlHelper.à¢ = (var0.GL_EXT_blend_func_separate && !var0.OpenGL14);
        OpenGlHelper.Ï­à = (var0.OpenGL14 || var0.GL_EXT_blend_func_separate);
        OpenGlHelper.ÂµÈ = (OpenGlHelper.Ï­à && (var0.GL_ARB_framebuffer_object || var0.GL_EXT_framebuffer_object || var0.OpenGL30));
        if (OpenGlHelper.ÂµÈ) {
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "Using framebuffer objects because ";
            if (var0.OpenGL30) {
                OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "OpenGL 3.0 is supported and separate blending is supported.\n";
                OpenGlHelper.áŒŠ = 0;
                OpenGlHelper.Â = 36160;
                OpenGlHelper.Ý = 36161;
                OpenGlHelper.Ø­áŒŠá = 36064;
                OpenGlHelper.Âµá€ = 36096;
                OpenGlHelper.Ó = 36053;
                OpenGlHelper.à = 36054;
                OpenGlHelper.Ø = 36055;
                OpenGlHelper.áŒŠÆ = 36059;
                OpenGlHelper.áˆºÑ¢Õ = 36060;
            }
            else if (var0.GL_ARB_framebuffer_object) {
                OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "ARB_framebuffer_object is supported and separate blending is supported.\n";
                OpenGlHelper.áŒŠ = 1;
                OpenGlHelper.Â = 36160;
                OpenGlHelper.Ý = 36161;
                OpenGlHelper.Ø­áŒŠá = 36064;
                OpenGlHelper.Âµá€ = 36096;
                OpenGlHelper.Ó = 36053;
                OpenGlHelper.Ø = 36055;
                OpenGlHelper.à = 36054;
                OpenGlHelper.áŒŠÆ = 36059;
                OpenGlHelper.áˆºÑ¢Õ = 36060;
            }
            else if (var0.GL_EXT_framebuffer_object) {
                OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "EXT_framebuffer_object is supported.\n";
                OpenGlHelper.áŒŠ = 2;
                OpenGlHelper.Â = 36160;
                OpenGlHelper.Ý = 36161;
                OpenGlHelper.Ø­áŒŠá = 36064;
                OpenGlHelper.Âµá€ = 36096;
                OpenGlHelper.Ó = 36053;
                OpenGlHelper.Ø = 36055;
                OpenGlHelper.à = 36054;
                OpenGlHelper.áŒŠÆ = 36059;
                OpenGlHelper.áˆºÑ¢Õ = 36060;
            }
        }
        else {
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "Not using framebuffer objects because ";
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "OpenGL 1.4 is " + (var0.OpenGL14 ? "" : "not ") + "supported, ";
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "EXT_blend_func_separate is " + (var0.GL_EXT_blend_func_separate ? "" : "not ") + "supported, ";
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "OpenGL 3.0 is " + (var0.OpenGL30 ? "" : "not ") + "supported, ";
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "ARB_framebuffer_object is " + (var0.GL_ARB_framebuffer_object ? "" : "not ") + "supported, and ";
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "EXT_framebuffer_object is " + (var0.GL_EXT_framebuffer_object ? "" : "not ") + "supported.\n";
        }
        OpenGlHelper.ŠÂµà = var0.OpenGL21;
        OpenGlHelper.£ÂµÄ = (OpenGlHelper.ŠÂµà || (var0.GL_ARB_vertex_shader && var0.GL_ARB_fragment_shader && var0.GL_ARB_shader_objects));
        OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "Shaders are " + (OpenGlHelper.£ÂµÄ ? "" : "not ") + "available because ";
        if (OpenGlHelper.£ÂµÄ) {
            if (var0.OpenGL21) {
                OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "OpenGL 2.1 is supported.\n";
                OpenGlHelper.Ø­Âµ = false;
                OpenGlHelper.á = 35714;
                OpenGlHelper.ˆÏ­ = 35713;
                OpenGlHelper.£á = 35633;
                OpenGlHelper.Å = 35632;
            }
            else {
                OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "ARB_shader_objects, ARB_vertex_shader, and ARB_fragment_shader are supported.\n";
                OpenGlHelper.Ø­Âµ = true;
                OpenGlHelper.á = 35714;
                OpenGlHelper.ˆÏ­ = 35713;
                OpenGlHelper.£á = 35633;
                OpenGlHelper.Å = 35632;
            }
        }
        else {
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "OpenGL 2.1 is " + (var0.OpenGL21 ? "" : "not ") + "supported, ";
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "ARB_shader_objects is " + (var0.GL_ARB_shader_objects ? "" : "not ") + "supported, ";
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "ARB_vertex_shader is " + (var0.GL_ARB_vertex_shader ? "" : "not ") + "supported, and ";
            OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "ARB_fragment_shader is " + (var0.GL_ARB_fragment_shader ? "" : "not ") + "supported.\n";
        }
        OpenGlHelper.¥à = (OpenGlHelper.ÂµÈ && OpenGlHelper.£ÂµÄ);
        OpenGlHelper.HorizonCode_Horizon_È = GL11.glGetString(7936).toLowerCase().contains("nvidia");
        OpenGlHelper.ÇŽá€ = (!var0.OpenGL15 && var0.GL_ARB_vertex_buffer_object);
        OpenGlHelper.Âµà = (var0.OpenGL15 || OpenGlHelper.ÇŽá€);
        OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "VBOs are " + (OpenGlHelper.Âµà ? "" : "not ") + "available because ";
        if (OpenGlHelper.Âµà) {
            if (OpenGlHelper.ÇŽá€) {
                OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "ARB_vertex_buffer_object is supported.\n";
                OpenGlHelper.È = 35044;
                OpenGlHelper.Ç = 34962;
            }
            else {
                OpenGlHelper.áˆºáˆºÈ = String.valueOf(OpenGlHelper.áˆºáˆºÈ) + "OpenGL 1.5 is supported.\n";
                OpenGlHelper.È = 35044;
                OpenGlHelper.Ç = 34962;
            }
        }
    }
    
    public static boolean Â() {
        return OpenGlHelper.¥à;
    }
    
    public static String Ý() {
        return OpenGlHelper.áˆºáˆºÈ;
    }
    
    public static int HorizonCode_Horizon_È(final int p_153175_0_, final int p_153175_1_) {
        return OpenGlHelper.Ø­Âµ ? ARBShaderObjects.glGetObjectParameteriARB(p_153175_0_, p_153175_1_) : GL20.glGetProgrami(p_153175_0_, p_153175_1_);
    }
    
    public static void Â(final int p_153178_0_, final int p_153178_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glAttachObjectARB(p_153178_0_, p_153178_1_);
        }
        else {
            GL20.glAttachShader(p_153178_0_, p_153178_1_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final int p_153180_0_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glDeleteObjectARB(p_153180_0_);
        }
        else {
            GL20.glDeleteShader(p_153180_0_);
        }
    }
    
    public static int Â(final int p_153195_0_) {
        return OpenGlHelper.Ø­Âµ ? ARBShaderObjects.glCreateShaderObjectARB(p_153195_0_) : GL20.glCreateShader(p_153195_0_);
    }
    
    public static void HorizonCode_Horizon_È(final int p_153169_0_, final ByteBuffer p_153169_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glShaderSourceARB(p_153169_0_, p_153169_1_);
        }
        else {
            GL20.glShaderSource(p_153169_0_, p_153169_1_);
        }
    }
    
    public static void Ý(final int p_153170_0_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glCompileShaderARB(p_153170_0_);
        }
        else {
            GL20.glCompileShader(p_153170_0_);
        }
    }
    
    public static int Ý(final int p_153157_0_, final int p_153157_1_) {
        return OpenGlHelper.Ø­Âµ ? ARBShaderObjects.glGetObjectParameteriARB(p_153157_0_, p_153157_1_) : GL20.glGetShaderi(p_153157_0_, p_153157_1_);
    }
    
    public static String Ø­áŒŠá(final int p_153158_0_, final int p_153158_1_) {
        return OpenGlHelper.Ø­Âµ ? ARBShaderObjects.glGetInfoLogARB(p_153158_0_, p_153158_1_) : GL20.glGetShaderInfoLog(p_153158_0_, p_153158_1_);
    }
    
    public static String Âµá€(final int p_153166_0_, final int p_153166_1_) {
        return OpenGlHelper.Ø­Âµ ? ARBShaderObjects.glGetInfoLogARB(p_153166_0_, p_153166_1_) : GL20.glGetProgramInfoLog(p_153166_0_, p_153166_1_);
    }
    
    public static void Ø­áŒŠá(final int p_153161_0_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUseProgramObjectARB(p_153161_0_);
        }
        else {
            GL20.glUseProgram(p_153161_0_);
        }
    }
    
    public static int Ø­áŒŠá() {
        return OpenGlHelper.Ø­Âµ ? ARBShaderObjects.glCreateProgramObjectARB() : GL20.glCreateProgram();
    }
    
    public static void Âµá€(final int p_153187_0_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glDeleteObjectARB(p_153187_0_);
        }
        else {
            GL20.glDeleteProgram(p_153187_0_);
        }
    }
    
    public static void Ó(final int p_153179_0_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glLinkProgramARB(p_153179_0_);
        }
        else {
            GL20.glLinkProgram(p_153179_0_);
        }
    }
    
    public static int HorizonCode_Horizon_È(final int p_153194_0_, final CharSequence p_153194_1_) {
        return OpenGlHelper.Ø­Âµ ? ARBShaderObjects.glGetUniformLocationARB(p_153194_0_, p_153194_1_) : GL20.glGetUniformLocation(p_153194_0_, p_153194_1_);
    }
    
    public static void HorizonCode_Horizon_È(final int p_153181_0_, final IntBuffer p_153181_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniform1ARB(p_153181_0_, p_153181_1_);
        }
        else {
            GL20.glUniform1(p_153181_0_, p_153181_1_);
        }
    }
    
    public static void Ó(final int p_153163_0_, final int p_153163_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniform1iARB(p_153163_0_, p_153163_1_);
        }
        else {
            GL20.glUniform1i(p_153163_0_, p_153163_1_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final int p_153168_0_, final FloatBuffer p_153168_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniform1ARB(p_153168_0_, p_153168_1_);
        }
        else {
            GL20.glUniform1(p_153168_0_, p_153168_1_);
        }
    }
    
    public static void Â(final int p_153182_0_, final IntBuffer p_153182_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniform2ARB(p_153182_0_, p_153182_1_);
        }
        else {
            GL20.glUniform2(p_153182_0_, p_153182_1_);
        }
    }
    
    public static void Â(final int p_153177_0_, final FloatBuffer p_153177_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniform2ARB(p_153177_0_, p_153177_1_);
        }
        else {
            GL20.glUniform2(p_153177_0_, p_153177_1_);
        }
    }
    
    public static void Ý(final int p_153192_0_, final IntBuffer p_153192_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniform3ARB(p_153192_0_, p_153192_1_);
        }
        else {
            GL20.glUniform3(p_153192_0_, p_153192_1_);
        }
    }
    
    public static void Ý(final int p_153191_0_, final FloatBuffer p_153191_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniform3ARB(p_153191_0_, p_153191_1_);
        }
        else {
            GL20.glUniform3(p_153191_0_, p_153191_1_);
        }
    }
    
    public static void Ø­áŒŠá(final int p_153162_0_, final IntBuffer p_153162_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniform4ARB(p_153162_0_, p_153162_1_);
        }
        else {
            GL20.glUniform4(p_153162_0_, p_153162_1_);
        }
    }
    
    public static void Ø­áŒŠá(final int p_153159_0_, final FloatBuffer p_153159_1_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniform4ARB(p_153159_0_, p_153159_1_);
        }
        else {
            GL20.glUniform4(p_153159_0_, p_153159_1_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final int p_153173_0_, final boolean p_153173_1_, final FloatBuffer p_153173_2_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniformMatrix2ARB(p_153173_0_, p_153173_1_, p_153173_2_);
        }
        else {
            GL20.glUniformMatrix2(p_153173_0_, p_153173_1_, p_153173_2_);
        }
    }
    
    public static void Â(final int p_153189_0_, final boolean p_153189_1_, final FloatBuffer p_153189_2_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniformMatrix3ARB(p_153189_0_, p_153189_1_, p_153189_2_);
        }
        else {
            GL20.glUniformMatrix3(p_153189_0_, p_153189_1_, p_153189_2_);
        }
    }
    
    public static void Ý(final int p_153160_0_, final boolean p_153160_1_, final FloatBuffer p_153160_2_) {
        if (OpenGlHelper.Ø­Âµ) {
            ARBShaderObjects.glUniformMatrix4ARB(p_153160_0_, p_153160_1_, p_153160_2_);
        }
        else {
            GL20.glUniformMatrix4(p_153160_0_, p_153160_1_, p_153160_2_);
        }
    }
    
    public static int Â(final int p_153164_0_, final CharSequence p_153164_1_) {
        return OpenGlHelper.Ø­Âµ ? ARBVertexShader.glGetAttribLocationARB(p_153164_0_, p_153164_1_) : GL20.glGetAttribLocation(p_153164_0_, p_153164_1_);
    }
    
    public static int Âµá€() {
        return OpenGlHelper.ÇŽá€ ? ARBVertexBufferObject.glGenBuffersARB() : GL15.glGenBuffers();
    }
    
    public static void à(final int p_176072_0_, final int p_176072_1_) {
        if (OpenGlHelper.ÇŽá€) {
            ARBVertexBufferObject.glBindBufferARB(p_176072_0_, p_176072_1_);
        }
        else {
            GL15.glBindBuffer(p_176072_0_, p_176072_1_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final int p_176071_0_, final ByteBuffer p_176071_1_, final int p_176071_2_) {
        if (OpenGlHelper.ÇŽá€) {
            ARBVertexBufferObject.glBufferDataARB(p_176071_0_, p_176071_1_, p_176071_2_);
        }
        else {
            GL15.glBufferData(p_176071_0_, p_176071_1_, p_176071_2_);
        }
    }
    
    public static void à(final int p_176074_0_) {
        if (OpenGlHelper.ÇŽá€) {
            ARBVertexBufferObject.glDeleteBuffersARB(p_176074_0_);
        }
        else {
            GL15.glDeleteBuffers(p_176074_0_);
        }
    }
    
    public static boolean Ó() {
        return OpenGlHelper.Âµà && Minecraft.áŒŠà().ŠÄ.Ñ¢Ç;
    }
    
    public static void Ø(final int p_153171_0_, final int p_153171_1_) {
        if (OpenGlHelper.ÂµÈ) {
            switch (OpenGlHelper.áŒŠ) {
                case 0: {
                    GL30.glBindFramebuffer(p_153171_0_, p_153171_1_);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindFramebuffer(p_153171_0_, p_153171_1_);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindFramebufferEXT(p_153171_0_, p_153171_1_);
                    break;
                }
            }
        }
    }
    
    public static void áŒŠÆ(final int p_153176_0_, final int p_153176_1_) {
        if (OpenGlHelper.ÂµÈ) {
            switch (OpenGlHelper.áŒŠ) {
                case 0: {
                    GL30.glBindRenderbuffer(p_153176_0_, p_153176_1_);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glBindRenderbuffer(p_153176_0_, p_153176_1_);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glBindRenderbufferEXT(p_153176_0_, p_153176_1_);
                    break;
                }
            }
        }
    }
    
    public static void Ø(final int p_153184_0_) {
        if (OpenGlHelper.ÂµÈ) {
            switch (OpenGlHelper.áŒŠ) {
                case 0: {
                    GL30.glDeleteRenderbuffers(p_153184_0_);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteRenderbuffers(p_153184_0_);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteRenderbuffersEXT(p_153184_0_);
                    break;
                }
            }
        }
    }
    
    public static void áŒŠÆ(final int p_153174_0_) {
        if (OpenGlHelper.ÂµÈ) {
            switch (OpenGlHelper.áŒŠ) {
                case 0: {
                    GL30.glDeleteFramebuffers(p_153174_0_);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glDeleteFramebuffers(p_153174_0_);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glDeleteFramebuffersEXT(p_153174_0_);
                    break;
                }
            }
        }
    }
    
    public static int à() {
        if (!OpenGlHelper.ÂµÈ) {
            return -1;
        }
        switch (OpenGlHelper.áŒŠ) {
            case 0: {
                return GL30.glGenFramebuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenFramebuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenFramebuffersEXT();
            }
            default: {
                return -1;
            }
        }
    }
    
    public static int Ø() {
        if (!OpenGlHelper.ÂµÈ) {
            return -1;
        }
        switch (OpenGlHelper.áŒŠ) {
            case 0: {
                return GL30.glGenRenderbuffers();
            }
            case 1: {
                return ARBFramebufferObject.glGenRenderbuffers();
            }
            case 2: {
                return EXTFramebufferObject.glGenRenderbuffersEXT();
            }
            default: {
                return -1;
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final int p_153186_0_, final int p_153186_1_, final int p_153186_2_, final int p_153186_3_) {
        if (OpenGlHelper.ÂµÈ) {
            switch (OpenGlHelper.áŒŠ) {
                case 0: {
                    GL30.glRenderbufferStorage(p_153186_0_, p_153186_1_, p_153186_2_, p_153186_3_);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glRenderbufferStorage(p_153186_0_, p_153186_1_, p_153186_2_, p_153186_3_);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glRenderbufferStorageEXT(p_153186_0_, p_153186_1_, p_153186_2_, p_153186_3_);
                    break;
                }
            }
        }
    }
    
    public static void Â(final int p_153190_0_, final int p_153190_1_, final int p_153190_2_, final int p_153190_3_) {
        if (OpenGlHelper.ÂµÈ) {
            switch (OpenGlHelper.áŒŠ) {
                case 0: {
                    GL30.glFramebufferRenderbuffer(p_153190_0_, p_153190_1_, p_153190_2_, p_153190_3_);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferRenderbuffer(p_153190_0_, p_153190_1_, p_153190_2_, p_153190_3_);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferRenderbufferEXT(p_153190_0_, p_153190_1_, p_153190_2_, p_153190_3_);
                    break;
                }
            }
        }
    }
    
    public static int áˆºÑ¢Õ(final int p_153167_0_) {
        if (!OpenGlHelper.ÂµÈ) {
            return -1;
        }
        switch (OpenGlHelper.áŒŠ) {
            case 0: {
                return GL30.glCheckFramebufferStatus(p_153167_0_);
            }
            case 1: {
                return ARBFramebufferObject.glCheckFramebufferStatus(p_153167_0_);
            }
            case 2: {
                return EXTFramebufferObject.glCheckFramebufferStatusEXT(p_153167_0_);
            }
            default: {
                return -1;
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final int p_153188_0_, final int p_153188_1_, final int p_153188_2_, final int p_153188_3_, final int p_153188_4_) {
        if (OpenGlHelper.ÂµÈ) {
            switch (OpenGlHelper.áŒŠ) {
                case 0: {
                    GL30.glFramebufferTexture2D(p_153188_0_, p_153188_1_, p_153188_2_, p_153188_3_, p_153188_4_);
                    break;
                }
                case 1: {
                    ARBFramebufferObject.glFramebufferTexture2D(p_153188_0_, p_153188_1_, p_153188_2_, p_153188_3_, p_153188_4_);
                    break;
                }
                case 2: {
                    EXTFramebufferObject.glFramebufferTexture2DEXT(p_153188_0_, p_153188_1_, p_153188_2_, p_153188_3_, p_153188_4_);
                    break;
                }
            }
        }
    }
    
    public static void ÂµÈ(final int p_77473_0_) {
        if (OpenGlHelper.Ä) {
            ARBMultitexture.glActiveTextureARB(p_77473_0_);
        }
        else {
            GL13.glActiveTexture(p_77473_0_);
        }
    }
    
    public static void á(final int p_77472_0_) {
        if (OpenGlHelper.Ä) {
            ARBMultitexture.glClientActiveTextureARB(p_77472_0_);
        }
        else {
            GL13.glClientActiveTexture(p_77472_0_);
        }
    }
    
    public static void HorizonCode_Horizon_È(final int p_77475_0_, final float p_77475_1_, final float p_77475_2_) {
        if (OpenGlHelper.Ä) {
            ARBMultitexture.glMultiTexCoord2fARB(p_77475_0_, p_77475_1_, p_77475_2_);
        }
        else {
            GL13.glMultiTexCoord2f(p_77475_0_, p_77475_1_, p_77475_2_);
        }
        if (p_77475_0_ == OpenGlHelper.µà) {
            OpenGlHelper.áŠ = p_77475_1_;
            OpenGlHelper.ˆáŠ = p_77475_2_;
        }
    }
    
    public static void Ý(final int p_148821_0_, final int p_148821_1_, final int p_148821_2_, final int p_148821_3_) {
        if (OpenGlHelper.Ï­à) {
            if (OpenGlHelper.à¢) {
                EXTBlendFuncSeparate.glBlendFuncSeparateEXT(p_148821_0_, p_148821_1_, p_148821_2_, p_148821_3_);
            }
            else {
                GL14.glBlendFuncSeparate(p_148821_0_, p_148821_1_, p_148821_2_, p_148821_3_);
            }
        }
        else {
            GL11.glBlendFunc(p_148821_0_, p_148821_1_);
        }
    }
    
    public static boolean áŒŠÆ() {
        return OpenGlHelper.ÂµÈ && Minecraft.áŒŠà().ŠÄ.Ó;
    }
}
