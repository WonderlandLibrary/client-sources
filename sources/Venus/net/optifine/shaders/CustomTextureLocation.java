/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.shaders;

import java.io.Serializable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.optifine.shaders.ICustomTexture;
import net.optifine.shaders.MultiTexID;

public class CustomTextureLocation
implements ICustomTexture {
    private int textureUnit = -1;
    private ResourceLocation location;
    private int variant = 0;
    private Texture texture;
    public static final int VARIANT_BASE = 0;
    public static final int VARIANT_NORMAL = 1;
    public static final int VARIANT_SPECULAR = 2;

    public CustomTextureLocation(int n, ResourceLocation resourceLocation, int n2) {
        this.textureUnit = n;
        this.location = resourceLocation;
        this.variant = n2;
    }

    public Texture getTexture() {
        if (this.texture == null) {
            TextureManager textureManager = Minecraft.getInstance().getTextureManager();
            this.texture = textureManager.getTexture(this.location);
            if (this.texture == null) {
                this.texture = new SimpleTexture(this.location);
                textureManager.loadTexture(this.location, this.texture);
                this.texture = textureManager.getTexture(this.location);
            }
        }
        return this.texture;
    }

    public void reloadTexture() {
        this.texture = null;
    }

    @Override
    public int getTextureId() {
        MultiTexID multiTexID;
        Texture texture = this.getTexture();
        if (this.variant != 0 && texture instanceof Texture && (multiTexID = texture.multiTex) != null) {
            if (this.variant == 1) {
                return multiTexID.norm;
            }
            if (this.variant == 2) {
                return multiTexID.spec;
            }
        }
        return texture.getGlTextureId();
    }

    @Override
    public int getTextureUnit() {
        return this.textureUnit;
    }

    @Override
    public void deleteTexture() {
    }

    public String toString() {
        return "textureUnit: " + this.textureUnit + ", location: " + this.location + ", glTextureId: " + (Serializable)(this.texture != null ? Integer.valueOf(this.texture.getGlTextureId()) : "");
    }
}

