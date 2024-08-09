/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.optifine.shaders.Shaders;

public class OverlayTexture
implements AutoCloseable {
    public static final int NO_OVERLAY = OverlayTexture.getPackedUV(0, 10);
    private final DynamicTexture texture = new DynamicTexture(16, 16, false);

    public OverlayTexture() {
        NativeImage nativeImage = this.texture.getTextureData();
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                if (i < 8) {
                    nativeImage.setPixelRGBA(j, i, -1308622593);
                    continue;
                }
                int n = (int)((1.0f - (float)j / 15.0f * 0.75f) * 255.0f);
                nativeImage.setPixelRGBA(j, i, n << 24 | 0xFFFFFF);
            }
        }
        RenderSystem.activeTexture(33985);
        this.texture.bindTexture();
        RenderSystem.matrixMode(5890);
        RenderSystem.loadIdentity();
        float f = 0.06666667f;
        RenderSystem.scalef(0.06666667f, 0.06666667f, 0.06666667f);
        RenderSystem.matrixMode(5888);
        this.texture.bindTexture();
        nativeImage.uploadTextureSub(0, 0, 0, 0, 0, nativeImage.getWidth(), nativeImage.getHeight(), false, true, false, true);
        RenderSystem.activeTexture(33984);
    }

    @Override
    public void close() {
        this.texture.close();
    }

    public void setupOverlayColor() {
        if (!Shaders.isOverlayDisabled()) {
            RenderSystem.setupOverlayColor(this.texture::getGlTextureId, 16);
        }
    }

    public static int getU(float f) {
        return (int)(f * 15.0f);
    }

    public static int getV(boolean bl) {
        return bl ? 3 : 10;
    }

    public static int getPackedUV(int n, int n2) {
        return n | n2 << 16;
    }

    public static int getPackedUV(float f, boolean bl) {
        return OverlayTexture.getPackedUV(OverlayTexture.getU(f), OverlayTexture.getV(bl));
    }

    public void teardownOverlayColor() {
        if (!Shaders.isOverlayDisabled()) {
            RenderSystem.teardownOverlayColor();
        }
    }
}

