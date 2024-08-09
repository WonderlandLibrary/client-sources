/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.optifine.shaders.ICustomTexture;

public class CustomTexture
implements ICustomTexture {
    private int textureUnit = -1;
    private String path = null;
    private Texture texture = null;

    public CustomTexture(int n, String string, Texture texture) {
        this.textureUnit = n;
        this.path = string;
        this.texture = texture;
    }

    @Override
    public int getTextureUnit() {
        return this.textureUnit;
    }

    public String getPath() {
        return this.path;
    }

    public Texture getTexture() {
        return this.texture;
    }

    @Override
    public int getTextureId() {
        return this.texture.getGlTextureId();
    }

    @Override
    public void deleteTexture() {
        TextureUtil.releaseTextureId(this.texture.getGlTextureId());
    }

    public String toString() {
        return "textureUnit: " + this.textureUnit + ", path: " + this.path + ", glTextureId: " + this.getTextureId();
    }
}

