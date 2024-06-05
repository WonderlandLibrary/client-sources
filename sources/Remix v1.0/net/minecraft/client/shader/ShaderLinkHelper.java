package net.minecraft.client.shader;

import net.minecraft.client.renderer.*;
import net.minecraft.client.util.*;
import org.apache.logging.log4j.*;

public class ShaderLinkHelper
{
    private static final Logger logger;
    private static ShaderLinkHelper staticShaderLinkHelper;
    
    public static void setNewStaticShaderLinkHelper() {
        ShaderLinkHelper.staticShaderLinkHelper = new ShaderLinkHelper();
    }
    
    public static ShaderLinkHelper getStaticShaderLinkHelper() {
        return ShaderLinkHelper.staticShaderLinkHelper;
    }
    
    public void deleteShader(final ShaderManager p_148077_1_) {
        p_148077_1_.getFragmentShaderLoader().deleteShader(p_148077_1_);
        p_148077_1_.getVertexShaderLoader().deleteShader(p_148077_1_);
        OpenGlHelper.glDeleteProgram(p_148077_1_.getProgram());
    }
    
    public int createProgram() throws JsonException {
        final int var1 = OpenGlHelper.glCreateProgram();
        if (var1 <= 0) {
            throw new JsonException("Could not create shader program (returned program ID " + var1 + ")");
        }
        return var1;
    }
    
    public void linkProgram(final ShaderManager manager) {
        manager.getFragmentShaderLoader().attachShader(manager);
        manager.getVertexShaderLoader().attachShader(manager);
        OpenGlHelper.glLinkProgram(manager.getProgram());
        final int var2 = OpenGlHelper.glGetProgrami(manager.getProgram(), OpenGlHelper.GL_LINK_STATUS);
        if (var2 == 0) {
            ShaderLinkHelper.logger.warn("Error encountered when linking program containing VS " + manager.getVertexShaderLoader().getShaderFilename() + " and FS " + manager.getFragmentShaderLoader().getShaderFilename() + ". Log output:");
            ShaderLinkHelper.logger.warn(OpenGlHelper.glGetProgramInfoLog(manager.getProgram(), 32768));
        }
    }
    
    static {
        logger = LogManager.getLogger();
    }
}
