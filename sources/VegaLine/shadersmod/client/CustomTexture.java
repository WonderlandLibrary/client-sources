/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package shadersmod.client;

import net.minecraft.client.renderer.texture.ITextureObject;
import net.minecraft.client.renderer.texture.TextureUtil;
import shadersmod.client.ICustomTexture;

public class CustomTexture
implements ICustomTexture {
    private int textureUnit = -1;
    private String path = null;
    private ITextureObject texture = null;

    public CustomTexture(int textureUnit, String path, ITextureObject texture) {
        this.textureUnit = textureUnit;
        this.path = path;
        this.texture = texture;
    }

    @Override
    public int getTextureUnit() {
        return this.textureUnit;
    }

    public String getPath() {
        return this.path;
    }

    public ITextureObject getTexture() {
        return this.texture;
    }

    @Override
    public int getTextureId() {
        return this.texture.getGlTextureId();
    }

    @Override
    public void deleteTexture() {
        TextureUtil.deleteTexture(this.texture.getGlTextureId());
    }

    public String toString() {
        return "textureUnit: " + this.textureUnit + ", path: " + this.path + ", glTextureId: " + this.texture.getGlTextureId();
    }
}

