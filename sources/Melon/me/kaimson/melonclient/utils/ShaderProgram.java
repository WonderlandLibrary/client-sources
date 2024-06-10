package me.kaimson.melonclient.utils;

import org.lwjgl.opengl.*;
import me.kaimson.melonclient.*;
import java.io.*;

public class ShaderProgram
{
    private static final ave MINECRAFT;
    private int program;
    
    public ShaderProgram(final String domain, final String vertShaderFilename, final String fragShaderFilename) {
        try {
            this.init(domain, vertShaderFilename, fragShaderFilename);
            if (this.program > 0) {
                GL20.glUseProgram(this.program);
                GL20.glUniform1i(GL20.glGetUniformLocation(this.program, "texture"), 0);
                GL20.glUseProgram(0);
            }
        }
        catch (Exception e) {
            Client.error("Could not initialize shader program!", e);
            this.program = 0;
        }
    }
    
    private void init(final String domain, final String vertShaderFilename, final String fragShaderFilename) {
        if (!bqs.O) {
            this.program = 0;
            return;
        }
        this.program = GL20.glCreateProgram();
        final int vertShader = this.loadAndCompileShader(domain, vertShaderFilename, 35633);
        final int fragShader = this.loadAndCompileShader(domain, fragShaderFilename, 35632);
        if (vertShader != 0) {
            GL20.glAttachShader(this.program, vertShader);
        }
        if (fragShader != 0) {
            GL20.glAttachShader(this.program, fragShader);
        }
        GL20.glLinkProgram(this.program);
        if (GL20.glGetProgrami(this.program, 35714) == 0) {
            Client.error("Could not link shader: {}", GL20.glGetProgramInfoLog(this.program, 1024));
            GL20.glDeleteProgram(this.program);
            this.program = 0;
            return;
        }
        GL20.glValidateProgram(this.program);
        if (GL20.glGetProgrami(this.program, 35715) == 0) {
            Client.error("Could not validate shader: {}", GL20.glGetProgramInfoLog(this.program, 1024));
            GL20.glDeleteProgram(this.program);
            this.program = 0;
        }
    }
    
    private int loadAndCompileShader(final String domain, final String filename, final int shaderType) {
        if (filename == null) {
            return 0;
        }
        final int handle = GL20.glCreateShader(shaderType);
        if (handle == 0) {
            Client.error("Could not create shader of type {} for {}: {}", shaderType, filename, GL20.glGetProgramInfoLog(this.program, 1024));
            return 0;
        }
        final String code = this.loadFile(new jy(domain, filename));
        if (code == null) {
            GL20.glDeleteShader(handle);
            return 0;
        }
        GL20.glShaderSource(handle, code);
        GL20.glCompileShader(handle);
        if (GL20.glGetShaderi(handle, 35713) == 0) {
            Client.error("Could not compile shader {}: {}", filename, GL20.glGetShaderInfoLog(this.program, 1024));
            GL20.glDeleteShader(handle);
            return 0;
        }
        return handle;
    }
    
    private String loadFile(final jy resourceLocation) {
        try {
            final StringBuilder code = new StringBuilder();
            final InputStream inputStream = ShaderProgram.MINECRAFT.Q().a(resourceLocation).b();
            final BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = reader.readLine()) != null) {
                code.append(line);
                code.append('\n');
            }
            reader.close();
            return code.toString();
        }
        catch (Exception e) {
            Client.error("Could not load shader file!", e);
            return null;
        }
    }
    
    public int getProgram() {
        return this.program;
    }
    
    static {
        MINECRAFT = ave.A();
    }
}
