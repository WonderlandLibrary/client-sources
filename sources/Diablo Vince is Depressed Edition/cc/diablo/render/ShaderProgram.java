/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.opengl.GL11
 *  org.lwjgl.opengl.GL20
 */
package cc.diablo.render;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL20;

public class ShaderProgram {
    private final int programID;
    private final String vertexName;
    private final String fragmentName;
    private final int vertexShaderID;
    private final int fragmentShaderID;

    public ShaderProgram(String vertexName, String fragmentName) {
        this.vertexName = vertexName;
        this.fragmentName = fragmentName;
        String vertexSource = ShaderProgram.readShader(vertexName);
        this.vertexShaderID = GL20.glCreateShader((int)35633);
        GL20.glShaderSource((int)this.vertexShaderID, (CharSequence)vertexSource);
        GL20.glCompileShader((int)this.vertexShaderID);
        String fragmentSource = ShaderProgram.readShader(fragmentName);
        this.fragmentShaderID = GL20.glCreateShader((int)35632);
        GL20.glShaderSource((int)this.fragmentShaderID, (CharSequence)fragmentSource);
        GL20.glCompileShader((int)this.fragmentShaderID);
        boolean compiled = GL20.glGetShaderi((int)this.fragmentShaderID, (int)35713) == 1;
        int n = this.programID = compiled ? GL20.glCreateProgram() : 0;
        if (!compiled) {
            String shaderLog = GL20.glGetShaderInfoLog((int)this.fragmentShaderID, (int)2048);
            System.err.printf("%s: Failed to compile shader source. Message\n%s%n", this, shaderLog);
            return;
        }
        GL20.glAttachShader((int)this.programID, (int)this.vertexShaderID);
        GL20.glAttachShader((int)this.programID, (int)this.fragmentShaderID);
        GL20.glLinkProgram((int)this.programID);
    }

    public ShaderProgram(String fragmentName) {
        this("vertex/vertex.vert", fragmentName);
    }

    public void renderCanvas(float width, float height) {
        GL11.glBegin((int)7);
        GL11.glTexCoord2f((float)0.0f, (float)1.0f);
        GL11.glVertex2d((double)0.0, (double)0.0);
        GL11.glTexCoord2f((float)0.0f, (float)0.0f);
        GL11.glVertex2d((double)0.0, (double)height);
        GL11.glVertex2d((double)width, (double)height);
        GL11.glTexCoord2f((float)1.0f, (float)1.0f);
        GL11.glVertex2d((double)width, (double)0.0);
        GL11.glEnd();
    }

    private static String readShader(String fileName) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            String line;
            InputStreamReader inputStreamReader = new InputStreamReader(ShaderProgram.class.getClassLoader().getResourceAsStream(String.format("assets/minecraft/Client/shaders/%s", fileName)));
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            while ((line = bufferedReader.readLine()) != null) {
                stringBuilder.append(line).append('\n');
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    public void deleteShaderProgram() {
        GL20.glDeleteShader((int)this.vertexShaderID);
        GL20.glDeleteShader((int)this.fragmentShaderID);
        GL20.glDeleteProgram((int)this.programID);
    }

    public void init() {
        GL20.glUseProgram((int)this.programID);
    }

    public void uninit() {
        GL20.glUseProgram((int)0);
    }

    public int getUniform(String name) {
        return GL20.glGetUniformLocation((int)this.programID, (CharSequence)name);
    }

    public float getCurrentUniformValue(String name, FloatBuffer buffer) {
        GL20.glGetUniform((int)this.programID, (int)this.getUniform(name), (FloatBuffer)buffer);
        return buffer.get(0);
    }

    public int getCurrentUniformValue(String name, IntBuffer buffer) {
        this.init();
        GL20.glGetUniform((int)this.programID, (int)this.getUniform(name), (IntBuffer)buffer);
        this.uninit();
        return buffer.get(0);
    }

    public int getProgramID() {
        return this.programID;
    }

    public String toString() {
        return "ShaderProgram{programID=" + this.programID + ", vertexName='" + this.vertexName + '\'' + ", fragmentName='" + this.fragmentName + '\'' + '}';
    }
}

