/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.shader;

import com.google.common.collect.Maps;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.IShaderManager;
import org.apache.commons.lang3.StringUtils;

public class ShaderLoader {
    private final ShaderType shaderType;
    private final String shaderFilename;
    private final int shader;
    private int shaderAttachCount;

    private ShaderLoader(ShaderType shaderType, int n, String string) {
        this.shaderType = shaderType;
        this.shader = n;
        this.shaderFilename = string;
    }

    public void attachShader(IShaderManager iShaderManager) {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        ++this.shaderAttachCount;
        GlStateManager.attachShader(iShaderManager.getProgram(), this.shader);
    }

    public void detachShader() {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        --this.shaderAttachCount;
        if (this.shaderAttachCount <= 0) {
            GlStateManager.deleteShader(this.shader);
            this.shaderType.getLoadedShaders().remove(this.shaderFilename);
        }
    }

    public String getShaderFilename() {
        return this.shaderFilename;
    }

    public static ShaderLoader func_216534_a(ShaderType shaderType, String string, InputStream inputStream, String string2) throws IOException {
        RenderSystem.assertThread(RenderSystem::isOnRenderThread);
        String string3 = TextureUtil.readResourceAsString(inputStream);
        if (string3 == null) {
            throw new IOException("Could not load program " + shaderType.getShaderName());
        }
        int n = GlStateManager.createShader(shaderType.getShaderMode());
        GlStateManager.shaderSource(n, string3);
        GlStateManager.compileShader(n);
        if (GlStateManager.getShader(n, 35713) == 0) {
            String string4 = StringUtils.trim(GlStateManager.getShaderInfoLog(n, 32768));
            throw new IOException("Couldn't compile " + shaderType.getShaderName() + " program (" + string2 + ", " + string + ") : " + string4);
        }
        ShaderLoader shaderLoader = new ShaderLoader(shaderType, n, string);
        shaderType.getLoadedShaders().put(string, shaderLoader);
        return shaderLoader;
    }

    public static enum ShaderType {
        VERTEX("vertex", ".vsh", 35633),
        FRAGMENT("fragment", ".fsh", 35632);

        private final String shaderName;
        private final String shaderExtension;
        private final int shaderMode;
        private final Map<String, ShaderLoader> loadedShaders = Maps.newHashMap();

        private ShaderType(String string2, String string3, int n2) {
            this.shaderName = string2;
            this.shaderExtension = string3;
            this.shaderMode = n2;
        }

        public String getShaderName() {
            return this.shaderName;
        }

        public String getShaderExtension() {
            return this.shaderExtension;
        }

        private int getShaderMode() {
            return this.shaderMode;
        }

        public Map<String, ShaderLoader> getLoadedShaders() {
            return this.loadedShaders;
        }
    }
}

