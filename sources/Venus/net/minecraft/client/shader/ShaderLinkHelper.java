/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.shader;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import net.minecraft.client.shader.IShaderManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ShaderLinkHelper {
    private static final Logger LOGGER = LogManager.getLogger();

    public static void func_227804_a_(int n) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        GlStateManager.useProgram(n);
    }

    public static void deleteShader(IShaderManager iShaderManager) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        iShaderManager.getFragmentShaderLoader().detachShader();
        iShaderManager.getVertexShaderLoader().detachShader();
        GlStateManager.deleteProgram(iShaderManager.getProgram());
    }

    public static int createProgram() throws IOException {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        int n = GlStateManager.createProgram();
        if (n <= 0) {
            throw new IOException("Could not create shader program (returned program ID " + n + ")");
        }
        return n;
    }

    public static void linkProgram(IShaderManager iShaderManager) throws IOException {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        iShaderManager.getFragmentShaderLoader().attachShader(iShaderManager);
        iShaderManager.getVertexShaderLoader().attachShader(iShaderManager);
        GlStateManager.linkProgram(iShaderManager.getProgram());
        int n = GlStateManager.getProgram(iShaderManager.getProgram(), 35714);
        if (n == 0) {
            LOGGER.warn("Error encountered when linking program containing VS {} and FS {}. Log output:", (Object)iShaderManager.getVertexShaderLoader().getShaderFilename(), (Object)iShaderManager.getFragmentShaderLoader().getShaderFilename());
            LOGGER.warn(GlStateManager.getProgramInfoLog(iShaderManager.getProgram(), 32768));
        }
    }
}

