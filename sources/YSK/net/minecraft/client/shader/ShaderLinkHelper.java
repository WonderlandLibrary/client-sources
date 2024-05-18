package net.minecraft.client.shader;

import net.minecraft.client.renderer.*;
import net.minecraft.client.util.*;
import java.io.*;
import org.apache.logging.log4j.*;

public class ShaderLinkHelper
{
    private static ShaderLinkHelper staticShaderLinkHelper;
    private static final String[] I;
    private static final Logger logger;
    
    public static ShaderLinkHelper getStaticShaderLinkHelper() {
        return ShaderLinkHelper.staticShaderLinkHelper;
    }
    
    public int createProgram() throws JsonException {
        final int glCreateProgram = OpenGlHelper.glCreateProgram();
        if (glCreateProgram <= 0) {
            throw new JsonException(ShaderLinkHelper.I["".length()] + glCreateProgram + ShaderLinkHelper.I[" ".length()]);
        }
        return glCreateProgram;
    }
    
    public void linkProgram(final ShaderManager shaderManager) throws IOException {
        shaderManager.getFragmentShaderLoader().attachShader(shaderManager);
        shaderManager.getVertexShaderLoader().attachShader(shaderManager);
        OpenGlHelper.glLinkProgram(shaderManager.getProgram());
        if (OpenGlHelper.glGetProgrami(shaderManager.getProgram(), OpenGlHelper.GL_LINK_STATUS) == 0) {
            ShaderLinkHelper.logger.warn(ShaderLinkHelper.I["  ".length()] + shaderManager.getVertexShaderLoader().getShaderFilename() + ShaderLinkHelper.I["   ".length()] + shaderManager.getFragmentShaderLoader().getShaderFilename() + ShaderLinkHelper.I[0x75 ^ 0x71]);
            ShaderLinkHelper.logger.warn(OpenGlHelper.glGetProgramInfoLog(shaderManager.getProgram(), 21039 + 8263 - 3547 + 7013));
        }
    }
    
    private static void I() {
        (I = new String[0xA7 ^ 0xA2])["".length()] = I("/,\u0007.\u0000L-\u001d6D\u000f1\u0017#\u0010\tc\u0001*\u0005\b&\u0000b\u0014\u001e,\u00150\u0005\u0001cZ0\u0001\u00186\u0000,\u0001\bc\u00020\u000b\u000b1\u0013/D%\u0007R", "lCrBd");
        ShaderLinkHelper.I[" ".length()] = I("C", "jKcwP");
        ShaderLinkHelper.I["  ".length()] = I(" !\n\u00196E6\u0016\u0015+\u0010=\f\u00136\u00007X\u0001,\u0000=X\u001a-\u000b8\u0011\u0018#E#\n\u0019#\u00172\u0015V'\n=\f\u0017-\u000b:\u0016\u0011d3\u0000X", "eSxvD");
        ShaderLinkHelper.I["   ".length()] = I("K\u00057\u001eY-7y", "kdYzy");
        ShaderLinkHelper.I[0x99 ^ 0x9D] = I("WT'\u001d2Y\u001b\u001e\u0006%\f\u0000Q", "ytkrU");
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    public void deleteShader(final ShaderManager shaderManager) {
        shaderManager.getFragmentShaderLoader().deleteShader(shaderManager);
        shaderManager.getVertexShaderLoader().deleteShader(shaderManager);
        OpenGlHelper.glDeleteProgram(shaderManager.getProgram());
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (4 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static void setNewStaticShaderLinkHelper() {
        ShaderLinkHelper.staticShaderLinkHelper = new ShaderLinkHelper();
    }
}
