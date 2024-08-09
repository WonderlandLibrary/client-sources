/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.util.LazyValue;
import net.minecraft.util.ResourceLocation;

public final class MissingTextureSprite
extends TextureAtlasSprite {
    private static final ResourceLocation LOCATION = new ResourceLocation("missingno");
    @Nullable
    private static DynamicTexture dynamicTexture;
    private static final LazyValue<NativeImage> IMAGE;
    private static final TextureAtlasSprite.Info spriteInfo;

    public MissingTextureSprite(AtlasTexture atlasTexture, TextureAtlasSprite.Info info, int n, int n2, int n3, int n4, int n5) {
        super(atlasTexture, info, n, n2, n3, n4, n5, MissingTextureSprite.makeMissingImage(info.getSpriteWidth(), info.getSpriteHeight()));
    }

    private MissingTextureSprite(AtlasTexture atlasTexture, int n, int n2, int n3, int n4, int n5) {
        super(atlasTexture, spriteInfo, n, n2, n3, n4, n5, IMAGE.getValue());
    }

    public static MissingTextureSprite create(AtlasTexture atlasTexture, int n, int n2, int n3, int n4, int n5) {
        return new MissingTextureSprite(atlasTexture, n, n2, n3, n4, n5);
    }

    public static ResourceLocation getLocation() {
        return LOCATION;
    }

    public static TextureAtlasSprite.Info getSpriteInfo() {
        return spriteInfo;
    }

    @Override
    public void close() {
        super.close();
    }

    public static DynamicTexture getDynamicTexture() {
        if (dynamicTexture == null) {
            dynamicTexture = new DynamicTexture(IMAGE.getValue());
            Minecraft.getInstance().getTextureManager().loadTexture(LOCATION, dynamicTexture);
        }
        return dynamicTexture;
    }

    private static NativeImage makeMissingImage(int n, int n2) {
        int n3 = n / 2;
        int n4 = n2 / 2;
        NativeImage nativeImage = new NativeImage(n, n2, false);
        int n5 = -16777216;
        int n6 = -524040;
        for (int i = 0; i < n2; ++i) {
            for (int j = 0; j < n; ++j) {
                if (i < n4 ^ j < n3) {
                    nativeImage.setPixelRGBA(j, i, n6);
                    continue;
                }
                nativeImage.setPixelRGBA(j, i, n5);
            }
        }
        return nativeImage;
    }

    private static NativeImage lambda$static$0() {
        NativeImage nativeImage = new NativeImage(16, 16, false);
        int n = -16777216;
        int n2 = -524040;
        for (int i = 0; i < 16; ++i) {
            for (int j = 0; j < 16; ++j) {
                if (i < 8 ^ j < 8) {
                    nativeImage.setPixelRGBA(j, i, -524040);
                    continue;
                }
                nativeImage.setPixelRGBA(j, i, -16777216);
            }
        }
        nativeImage.untrack();
        return nativeImage;
    }

    static {
        IMAGE = new LazyValue<NativeImage>(MissingTextureSprite::lambda$static$0);
        spriteInfo = new TextureAtlasSprite.Info(LOCATION, 16, 16, new AnimationMetadataSection(Lists.newArrayList(new AnimationFrame(0, -1)), 16, 16, 1, false));
    }
}

