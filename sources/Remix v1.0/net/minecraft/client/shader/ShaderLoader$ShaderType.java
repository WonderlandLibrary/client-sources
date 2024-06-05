package net.minecraft.client.shader;

import java.util.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.*;

public enum ShaderType
{
    VERTEX("VERTEX", 0, "vertex", ".vsh", OpenGlHelper.GL_VERTEX_SHADER), 
    FRAGMENT("FRAGMENT", 1, "fragment", ".fsh", OpenGlHelper.GL_FRAGMENT_SHADER);
    
    private static final ShaderType[] $VALUES;
    private final String shaderName;
    private final String shaderExtension;
    private final int shaderMode;
    private final Map loadedShaders;
    
    private ShaderType(final String p_i45090_1_, final int p_i45090_2_, final String p_i45090_3_, final String p_i45090_4_, final int p_i45090_5_) {
        this.loadedShaders = Maps.newHashMap();
        this.shaderName = p_i45090_3_;
        this.shaderExtension = p_i45090_4_;
        this.shaderMode = p_i45090_5_;
    }
    
    public String getShaderName() {
        return this.shaderName;
    }
    
    protected String getShaderExtension() {
        return this.shaderExtension;
    }
    
    protected int getShaderMode() {
        return this.shaderMode;
    }
    
    protected Map getLoadedShaders() {
        return this.loadedShaders;
    }
    
    static {
        $VALUES = new ShaderType[] { ShaderType.VERTEX, ShaderType.FRAGMENT };
    }
}
