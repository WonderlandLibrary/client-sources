package net.shoreline.client.api.render.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import org.jetbrains.annotations.NotNull;

public abstract class Program {
    protected final int id;
    private boolean isInitialisedUniforms = false;

    public Program(Shader @NotNull ... shaders) {
        id = GlStateManager.glCreateProgram();

        for (Shader shader : shaders) {
            GlStateManager.glAttachShader(id, shader.getId());
            GlStateManager.glLinkProgram(id);
            GlStateManager.glDeleteShader(shader.getId());
        }
    }

    public abstract void initUniforms();

    public abstract void updateUniforms();

    public void use() {
        GlStateManager._glUseProgram(id);

        if (!isInitialisedUniforms) {
            initUniforms();
            isInitialisedUniforms = true;
        }
        //
        updateUniforms();
    }

    public void stopUse() {
        GlStateManager._glUseProgram(0);
    }

    public int getId() {
        return id;
    }
}
