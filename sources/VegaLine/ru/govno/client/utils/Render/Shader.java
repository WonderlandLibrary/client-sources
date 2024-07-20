/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package ru.govno.client.utils.Render;

import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import org.apache.commons.io.IOUtils;
import org.lwjgl.opengl.ARBShaderObjects;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public abstract class Shader {
    private int program;
    public static Minecraft mc = Minecraft.getMinecraft();
    private Map<String, Integer> uniformsMap;

    public Shader(String fragmentShader) {
        int fragmentShaderID;
        int vertexShaderID;
        try {
            InputStream vertexStream = this.getClass().getResourceAsStream("/assets/minecraft/liquar/shader/vertex.vert");
            vertexShaderID = this.createShader(IOUtils.toString(vertexStream), 35633);
            IOUtils.closeQuietly(vertexStream);
            InputStream fragmentStream = this.getClass().getResourceAsStream("/assets/minecraft/liquar/shader/" + fragmentShader);
            fragmentShaderID = this.createShader(IOUtils.toString(fragmentStream), 35632);
            IOUtils.closeQuietly(fragmentStream);
        } catch (Exception e) {
            return;
        }
        if (vertexShaderID == 0 || fragmentShaderID == 0) {
            return;
        }
        this.program = ARBShaderObjects.glCreateProgramObjectARB();
        if (this.program == 0) {
            return;
        }
        ARBShaderObjects.glAttachObjectARB(this.program, vertexShaderID);
        ARBShaderObjects.glAttachObjectARB(this.program, fragmentShaderID);
        ARBShaderObjects.glLinkProgramARB(this.program);
        ARBShaderObjects.glValidateProgramARB(this.program);
    }

    public void startShader() {
        GL11.glPushMatrix();
        GL20.glUseProgram(this.program);
        if (this.uniformsMap == null) {
            this.uniformsMap = new HashMap<String, Integer>();
            this.setupUniforms();
        }
        this.updateUniforms();
    }

    public void stopShader() {
        GL20.glUseProgram(0);
        GL11.glPopMatrix();
    }

    public abstract void setupUniforms();

    public abstract void updateUniforms();

    private int createShader(String shaderSource, int shaderType) {
        int shader = 0;
        try {
            shader = ARBShaderObjects.glCreateShaderObjectARB(shaderType);
            if (shader == 0) {
                return 0;
            }
            ARBShaderObjects.glShaderSourceARB(shader, shaderSource);
            ARBShaderObjects.glCompileShaderARB(shader);
            return shader;
        } catch (Exception e) {
            ARBShaderObjects.glDeleteObjectARB(shader);
            throw e;
        }
    }

    public void setUniform(String uniformName, int location) {
        this.uniformsMap.put(uniformName, location);
    }

    public void setupUniform(String uniformName) {
        this.setUniform(uniformName, GL20.glGetUniformLocation(this.program, uniformName));
    }

    public int getUniform(String uniformName) {
        return this.uniformsMap.get(uniformName);
    }
}

