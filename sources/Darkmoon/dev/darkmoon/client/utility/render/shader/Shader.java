package dev.darkmoon.client.utility.render.shader;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import static org.lwjgl.opengl.GL20.*;

public class Shader {
    int shaderProgram;
    public void setUniformf(String name, float... args) {
        int loc = glGetUniformLocation(shaderProgram, name);
        switch (args.length) {
            case 1:
                glUniform1f(loc, args[0]);
                break;
            case 2:
                glUniform2f(loc, args[0], args[1]);
                break;
            case 3:
                glUniform3f(loc, args[0], args[1], args[2]);
                break;
            case 4:
                glUniform4f(loc, args[0], args[1], args[2], args[3]);
                break;
        }
    }
    public String readInputStream(InputStream inputStream) {
        StringBuilder stringBuilder = new StringBuilder();

        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            String line;
            while ((line = bufferedReader.readLine()) != null)
                stringBuilder.append(line).append('\n');

        } catch (Exception e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }
    public void setUniformi(String name, int... args) {
        int loc = glGetUniformLocation(shaderProgram, name);
        if (args.length > 1) glUniform2i(loc, args[0], args[1]);
        else glUniform1i(loc, args[0]);
    }
    public Shader(String fragmentSource, boolean isFile) {
        int shaderProgram = glCreateProgram();
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        try {
            glShaderSource(fragmentShader, (isFile ? readInputStream(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(fragmentSource)).getInputStream()) : fragmentSource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        glCompileShader(fragmentShader);
        glAttachShader(shaderProgram, fragmentShader);

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertexShader, "#version 120\nvoid main() {\n gl_TexCoord[0] = gl_MultiTexCoord0;\n gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;\n}");
        glCompileShader(vertexShader);
        glAttachShader(shaderProgram, vertexShader);

        glLinkProgram(shaderProgram);
        this.shaderProgram = shaderProgram;
    }

    public Shader(String fragmentSource, String vertexSource, boolean isFile) {
        int shaderProgram = glCreateProgram();
        int fragmentShader = glCreateShader(GL_FRAGMENT_SHADER);
        try {
            glShaderSource(fragmentShader, (isFile ? readInputStream(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(fragmentSource)).getInputStream()) : fragmentSource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        glAttachShader(shaderProgram, fragmentShader);
        glCompileShader(fragmentShader);

        int vertexShader = glCreateShader(GL_VERTEX_SHADER);
        try {
            glShaderSource(vertexShader, (isFile ? readInputStream(Minecraft.getMinecraft().getResourceManager().getResource(new ResourceLocation(vertexSource)).getInputStream()) : vertexSource));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        glCompileShader(vertexShader);
        glAttachShader(shaderProgram, vertexShader);
        glDeleteShader(fragmentShader);
        glDeleteShader(vertexShader);

        glLinkProgram(shaderProgram);
        this.shaderProgram = shaderProgram;
    }

    public void useProgram() {
        glUseProgram(shaderProgram);
    }

    public void unloadProgram() {
        glUseProgram(0);
    }
    public void setupUniform1f(String uniform, float x) {
        int vertexColorLocation = glGetUniformLocation(shaderProgram, uniform);
        glUniform1f(vertexColorLocation, x);
    }

    public void setupUniform2f(String uniform, float x, float y) {
        int vertexColorLocation = glGetUniformLocation(shaderProgram, uniform);
        glUniform2f(vertexColorLocation, x, y);
    }

    public void setupUniform3f(String uniform, float x, float y, float z) {
        int vertexColorLocation = glGetUniformLocation(shaderProgram, uniform);
        glUniform3f(vertexColorLocation, x, y, z);
    }

    public void setupUniform4f(String uniform, float x, float y, float z, float w) {
        int vertexColorLocation = glGetUniformLocation(shaderProgram, uniform);
        glUniform4f(vertexColorLocation, x, y, z, w);
    }

    public void setupUniform1i(String uniform, int x) {
        int vertexColorLocation = glGetUniformLocation(shaderProgram, uniform);
        glUniform1i(vertexColorLocation, x);
    }

    public void setupUniform2i(String uniform, int x, int y) {
        int vertexColorLocation = glGetUniformLocation(shaderProgram, uniform);
        glUniform2i(vertexColorLocation, x, y);
    }

    public void setupUniform3i(String uniform, int x, int y, int z) {
        int vertexColorLocation = glGetUniformLocation(shaderProgram, uniform);
        glUniform3i(vertexColorLocation, x, y, z);
    }

    public void setupUniform4i(String uniform, int x, int y, int z, int w) {
        int vertexColorLocation = glGetUniformLocation(shaderProgram, uniform);
        glUniform4i(vertexColorLocation, x, y, z, w);
    }

    public int getUniform(String name) {
        return glGetUniformLocation(shaderProgram, name);
    }
}