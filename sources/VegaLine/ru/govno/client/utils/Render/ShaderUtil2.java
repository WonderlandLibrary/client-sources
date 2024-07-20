/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL20;
import ru.govno.client.utils.Render.RenderUtils;

public class ShaderUtil2 {
    private static final Minecraft mc = Minecraft.getMinecraft();
    private final int programID;

    public void setUniformf(String name, float ... args) {
        int loc = GL20.glGetUniformLocation(this.programID, name);
        switch (args.length) {
            case 1: {
                GL20.glUniform1f(loc, args[0]);
                break;
            }
            case 2: {
                GL20.glUniform2f(loc, args[0], args[1]);
                break;
            }
            case 3: {
                GL20.glUniform3f(loc, args[0], args[1], args[2]);
                break;
            }
            case 4: {
                GL20.glUniform4f(loc, args[0], args[1], args[2], args[3]);
            }
        }
    }

    public void setUniformColor(String name, int color) {
        this.setUniformf(name, (float)RenderUtils.red(color) / 255.0f, (float)RenderUtils.green(color) / 255.0f, (float)RenderUtils.blue(color) / 255.0f);
    }

    public void init() {
        GL20.glUseProgram(this.programID);
    }

    public void unload() {
        GL20.glUseProgram(0);
    }

    public void setUniformi(String name, int ... args) {
        int loc = GL20.glGetUniformLocation(this.programID, name);
        if (args.length > 1) {
            GL20.glUniform2i(loc, args[0], args[1]);
        } else {
            GL20.glUniform1i(loc, args[0]);
        }
    }

    public ShaderUtil2(String fragmentShaderLoc, String vertexShaderLoc) {
        int program = GL20.glCreateProgram();
        try {
            int fragmentShaderID = this.createShader(mc.getResourceManager().getResource(new ResourceLocation(fragmentShaderLoc)).getInputStream(), 35632);
            GL20.glAttachShader(program, fragmentShaderID);
            int vertexShaderID = this.createShader(mc.getResourceManager().getResource(new ResourceLocation(vertexShaderLoc)).getInputStream(), 35633);
            GL20.glAttachShader(program, vertexShaderID);
        } catch (IOException e) {
            e.printStackTrace();
        }
        GL20.glLinkProgram(program);
        if (GL20.glGetProgrami(program, 35714) == 0) {
            throw new IllegalStateException("Shader failed to link!");
        }
        this.programID = program;
    }

    public int getUniform(String name) {
        return GL20.glGetUniformLocation(this.programID, name);
    }

    public ShaderUtil2(String fragmentShaderLoc) {
        this(fragmentShaderLoc, "vegaline/modules/esp/shaders/vertex.vsh");
    }

    public void attach() {
        GL20.glUseProgram(this.programID);
    }

    public void detach() {
        GL20.glUseProgram(0);
    }

    public static String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line;
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    private int createShader(InputStream inputStream, int shaderType) {
        int shader = GL20.glCreateShader(shaderType);
        GL20.glShaderSource(shader, ShaderUtil2.readInputStream(inputStream));
        GL20.glCompileShader(shader);
        if (GL20.glGetShaderi(shader, 35713) == 0) {
            throw new IllegalStateException(String.format("Shader (%s) failed to compile!", shaderType));
        }
        return shader;
    }
}

