/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.google.common.collect.Lists;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.SpriteAwareVertexBuilder;
import net.minecraft.client.renderer.texture.AtlasTexture;
import net.minecraft.client.renderer.texture.MipmapGenerator;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.AnimationFrame;
import net.minecraft.client.resources.data.AnimationMetadataSection;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.crash.ReportedException;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.extensions.IForgeTextureAtlasSprite;
import net.optifine.Config;
import net.optifine.SmartAnimations;
import net.optifine.shaders.Shaders;
import net.optifine.shaders.ShadersTextureType;
import net.optifine.texture.IColorBlender;
import net.optifine.util.CounterInt;
import net.optifine.util.TextureUtils;

public class TextureAtlasSprite
implements AutoCloseable,
IForgeTextureAtlasSprite {
    private final AtlasTexture atlasTexture;
    private final Info spriteInfo;
    private final AnimationMetadataSection animationMetadata;
    protected final NativeImage[] frames;
    private final int[] framesX;
    private final int[] framesY;
    @Nullable
    private final InterpolationData interpolationData;
    private final int x;
    private final int y;
    private final float minU;
    private final float maxU;
    private final float minV;
    private final float maxV;
    private int frameCounter;
    private int tickCounter;
    private int indexInMap = -1;
    public float baseU;
    public float baseV;
    public int sheetWidth;
    public int sheetHeight;
    public int glSpriteTextureId = -1;
    public TextureAtlasSprite spriteSingle = null;
    public boolean isSpriteSingle = false;
    public static final String SUFFIX_SPRITE_SINGLE = ".sprite_single";
    public int mipmapLevels = 0;
    public TextureAtlasSprite spriteNormal = null;
    public TextureAtlasSprite spriteSpecular = null;
    public ShadersTextureType spriteShadersType = null;
    public TextureAtlasSprite spriteEmissive = null;
    public boolean isSpriteEmissive = false;
    private int animationIndex = -1;
    private boolean animationActive = false;
    private boolean usesParentAnimationTime = false;
    private boolean terrain;
    private boolean shaders;
    private boolean multiTexture;
    private IResourceManager resourceManager;

    public TextureAtlasSprite(ResourceLocation resourceLocation) {
        this.atlasTexture = null;
        this.spriteInfo = new Info(resourceLocation, 0, 0, null);
        this.animationMetadata = null;
        this.frames = null;
        this.framesX = new int[0];
        this.framesY = new int[0];
        this.interpolationData = null;
        this.x = 0;
        this.y = 0;
        this.minU = 0.0f;
        this.maxU = 0.0f;
        this.minV = 0.0f;
        this.maxV = 0.0f;
    }

    private TextureAtlasSprite(TextureAtlasSprite textureAtlasSprite) {
        this.atlasTexture = textureAtlasSprite.atlasTexture;
        Info info = textureAtlasSprite.spriteInfo;
        ResourceLocation resourceLocation = info.getSpriteLocation();
        ResourceLocation resourceLocation2 = new ResourceLocation(resourceLocation.getNamespace(), resourceLocation.getPath() + SUFFIX_SPRITE_SINGLE);
        int n = info.getSpriteWidth();
        int n2 = info.getSpriteHeight();
        AnimationMetadataSection animationMetadataSection = info.getSpriteAnimationMetadata();
        this.spriteInfo = new Info(resourceLocation2, n, n2, animationMetadataSection);
        this.animationMetadata = textureAtlasSprite.animationMetadata;
        this.usesParentAnimationTime = true;
        this.frames = textureAtlasSprite.frames;
        this.framesX = textureAtlasSprite.framesX;
        this.framesY = textureAtlasSprite.framesY;
        this.interpolationData = textureAtlasSprite.interpolationData != null ? new InterpolationData(this, textureAtlasSprite.interpolationData.images) : null;
        this.x = 0;
        this.y = 0;
        this.minU = 0.0f;
        this.maxU = 1.0f;
        this.minV = 0.0f;
        this.maxV = 1.0f;
        this.frameCounter = textureAtlasSprite.frameCounter;
        this.tickCounter = textureAtlasSprite.tickCounter;
        this.indexInMap = textureAtlasSprite.indexInMap;
        this.baseU = textureAtlasSprite.baseU;
        this.baseV = textureAtlasSprite.baseV;
        this.sheetWidth = textureAtlasSprite.sheetWidth;
        this.sheetHeight = textureAtlasSprite.sheetHeight;
        this.isSpriteSingle = true;
        this.mipmapLevels = textureAtlasSprite.mipmapLevels;
        this.animationIndex = textureAtlasSprite.animationIndex;
        this.animationActive = textureAtlasSprite.animationActive;
    }

    protected TextureAtlasSprite(AtlasTexture atlasTexture, Info info, int n, int n2, int n3, int n4, int n5, NativeImage nativeImage) {
        this(atlasTexture, info, n, n2, n3, n4, n5, nativeImage, null);
    }

    protected TextureAtlasSprite(AtlasTexture atlasTexture, Info info, int n, int n2, int n3, int n4, int n5, NativeImage nativeImage, ShadersTextureType shadersTextureType) {
        Object object;
        int n6;
        NativeImage nativeImage2;
        this.atlasTexture = atlasTexture;
        AnimationMetadataSection animationMetadataSection = info.spriteAnimationMetadata;
        int n7 = info.spriteWidth;
        int n8 = info.spriteHeight;
        this.x = n4;
        this.y = n5;
        this.minU = (float)n4 / (float)n2;
        this.maxU = (float)(n4 + n7) / (float)n2;
        this.minV = (float)n5 / (float)n3;
        this.maxV = (float)(n5 + n8) / (float)n3;
        if (info.scaleFactor > 1.0 && (nativeImage2 = TextureUtils.scaleImage(nativeImage, n6 = (int)Math.round((double)nativeImage.getWidth() * info.scaleFactor))) != nativeImage) {
            nativeImage.close();
            nativeImage = nativeImage2;
        }
        this.spriteShadersType = shadersTextureType;
        IColorBlender iColorBlender = this.atlasTexture.getShadersColorBlender(this.spriteShadersType);
        if (this.spriteShadersType == null && !info.getSpriteLocation().getPath().endsWith("_leaves")) {
            this.fixTransparentColor(nativeImage);
        }
        nativeImage2 = nativeImage;
        int n9 = nativeImage.getWidth() / animationMetadataSection.getFrameWidth(n7);
        int n10 = nativeImage.getHeight() / animationMetadataSection.getFrameHeight(n8);
        if (animationMetadataSection.getFrameCount() > 0) {
            int n11 = (Integer)animationMetadataSection.getFrameIndexSet().stream().max(Integer::compareTo).get() + 1;
            this.framesX = new int[n11];
            this.framesY = new int[n11];
            Arrays.fill(this.framesX, -1);
            Arrays.fill(this.framesY, -1);
            object = animationMetadataSection.getFrameIndexSet().iterator();
            while (object.hasNext()) {
                int n12;
                var19_24 = object.next();
                if (var19_24 >= n9 * n10) {
                    throw new RuntimeException("invalid frameindex " + var19_24);
                }
                int n13 = var19_24 / n9;
                this.framesX[var19_24] = n12 = var19_24 % n9;
                this.framesY[var19_24] = n13;
            }
        } else {
            ArrayList<AnimationFrame> arrayList = Lists.newArrayList();
            int n14 = n9 * n10;
            this.framesX = new int[n14];
            this.framesY = new int[n14];
            for (var19_24 = 0; var19_24 < n10; ++var19_24) {
                int n15 = 0;
                while (n15 < n9) {
                    int n16 = var19_24 * n9 + n15;
                    this.framesX[n16] = n15++;
                    this.framesY[n16] = var19_24;
                    arrayList.add(new AnimationFrame(n16, -1));
                }
            }
            animationMetadataSection = new AnimationMetadataSection(arrayList, n7, n8, animationMetadataSection.getFrameTime(), animationMetadataSection.isInterpolate());
        }
        this.spriteInfo = new Info(info.spriteLocation, n7, n8, animationMetadataSection);
        this.animationMetadata = animationMetadataSection;
        try {
            try {
                this.frames = MipmapGenerator.generateMipmaps(nativeImage, n, iColorBlender);
            } catch (Throwable throwable) {
                object = CrashReport.makeCrashReport(throwable, "Generating mipmaps for frame");
                CrashReportCategory crashReportCategory = ((CrashReport)object).makeCategory("Frame being iterated");
                crashReportCategory.addDetail("First frame", () -> TextureAtlasSprite.lambda$new$0(nativeImage2));
                throw new ReportedException((CrashReport)object);
            }
        } catch (Throwable throwable) {
            object = CrashReport.makeCrashReport(throwable, "Applying mipmap");
            CrashReportCategory crashReportCategory = ((CrashReport)object).makeCategory("Sprite being mipmapped");
            crashReportCategory.addDetail("Sprite name", this::lambda$new$1);
            crashReportCategory.addDetail("Sprite size", this::lambda$new$2);
            crashReportCategory.addDetail("Sprite frames", this::lambda$new$3);
            crashReportCategory.addDetail("Mipmap levels", n);
            throw new ReportedException((CrashReport)object);
        }
        this.interpolationData = animationMetadataSection.isInterpolate() ? new InterpolationData(this, info, n) : null;
        this.mipmapLevels = n;
        this.baseU = Math.min(this.minU, this.maxU);
        this.baseV = Math.min(this.minV, this.maxV);
        this.sheetWidth = n2;
        this.sheetHeight = n3;
    }

    private void uploadFrames(int n) {
        int n2 = this.framesX[n] * this.spriteInfo.spriteWidth;
        int n3 = this.framesY[n] * this.spriteInfo.spriteHeight;
        this.uploadFrames(n2, n3, this.frames);
    }

    private void uploadFrames(int n, int n2, NativeImage[] nativeImageArray) {
        boolean bl = false;
        boolean bl2 = this.isSpriteSingle;
        for (int i = 0; i < nativeImageArray.length && this.getWidth() >> i > 0 && this.getHeight() >> i > 0; ++i) {
            nativeImageArray[i].uploadTextureSub(i, this.x >> i, this.y >> i, n >> i, n2 >> i, this.spriteInfo.spriteWidth >> i, this.spriteInfo.spriteHeight >> i, bl, bl2, nativeImageArray.length > 1, true);
        }
    }

    public int getWidth() {
        return this.spriteInfo.spriteWidth;
    }

    public int getHeight() {
        return this.spriteInfo.spriteHeight;
    }

    public float getMinU() {
        return this.minU;
    }

    public float getMaxU() {
        return this.maxU;
    }

    public float getInterpolatedU(double d) {
        float f = this.maxU - this.minU;
        return this.minU + f * (float)d / 16.0f;
    }

    public float getMinV() {
        return this.minV;
    }

    public float getMaxV() {
        return this.maxV;
    }

    public float getInterpolatedV(double d) {
        float f = this.maxV - this.minV;
        return this.minV + f * (float)d / 16.0f;
    }

    public ResourceLocation getName() {
        return this.spriteInfo.spriteLocation;
    }

    public AtlasTexture getAtlasTexture() {
        return this.atlasTexture;
    }

    public int getFrameCount() {
        return this.framesX.length;
    }

    @Override
    public void close() {
        for (NativeImage nativeImage : this.frames) {
            if (nativeImage == null) continue;
            nativeImage.close();
        }
        if (this.interpolationData != null) {
            this.interpolationData.close();
        }
        if (this.spriteSingle != null) {
            // empty if block
        }
        if (this.spriteNormal != null) {
            this.spriteNormal.close();
        }
        if (this.spriteSpecular != null) {
            this.spriteSpecular.close();
        }
    }

    public String toString() {
        int n = this.framesX.length;
        return "TextureAtlasSprite{name='" + this.spriteInfo.spriteLocation + "', frameCount=" + n + ", x=" + this.x + ", y=" + this.y + ", height=" + this.spriteInfo.spriteHeight + ", width=" + this.spriteInfo.spriteWidth + ", u0=" + this.minU + ", u1=" + this.maxU + ", v0=" + this.minV + ", v1=" + this.maxV + "}";
    }

    public boolean isPixelTransparent(int n, int n2, int n3) {
        return (this.frames[0].getPixelRGBA(n2 + this.framesX[n] * this.spriteInfo.spriteWidth, n3 + this.framesY[n] * this.spriteInfo.spriteHeight) >> 24 & 0xFF) == 0;
    }

    public void uploadMipmaps() {
        this.uploadFrames(0);
    }

    private float getAtlasSize() {
        float f = (float)this.spriteInfo.spriteWidth / (this.maxU - this.minU);
        float f2 = (float)this.spriteInfo.spriteHeight / (this.maxV - this.minV);
        return Math.max(f2, f);
    }

    public float getUvShrinkRatio() {
        return 4.0f / this.getAtlasSize();
    }

    public void updateAnimation() {
        if (this.animationMetadata != null) {
            boolean bl = this.animationActive = SmartAnimations.isActive() ? SmartAnimations.isSpriteRendered(this) : true;
            if (this.animationMetadata.getFrameCount() <= 1) {
                this.animationActive = false;
            }
            if (this.spriteSingle != null && this.spriteSingle.usesParentAnimationTime) {
                this.spriteSingle.tickCounter = this.tickCounter;
                this.spriteSingle.frameCounter = this.frameCounter;
            }
            if (this.spriteNormal != null && this.spriteNormal.usesParentAnimationTime) {
                this.spriteNormal.tickCounter = this.tickCounter;
                this.spriteNormal.frameCounter = this.frameCounter;
            }
            if (this.spriteSpecular != null && this.spriteSpecular.usesParentAnimationTime) {
                this.spriteSpecular.tickCounter = this.tickCounter;
                this.spriteSpecular.frameCounter = this.frameCounter;
            }
            ++this.tickCounter;
            if (this.tickCounter >= this.animationMetadata.getFrameTimeSingle(this.frameCounter)) {
                int n = this.animationMetadata.getFrameIndex(this.frameCounter);
                int n2 = this.animationMetadata.getFrameCount() == 0 ? this.getFrameCount() : this.animationMetadata.getFrameCount();
                this.frameCounter = (this.frameCounter + 1) % n2;
                this.tickCounter = 0;
                int n3 = this.animationMetadata.getFrameIndex(this.frameCounter);
                if (!this.animationActive) {
                    return;
                }
                if (n != n3 && n3 >= 0 && n3 < this.getFrameCount()) {
                    this.uploadFrames(n3);
                }
            } else if (this.interpolationData != null) {
                if (!this.animationActive) {
                    return;
                }
                if (!RenderSystem.isOnRenderThread()) {
                    RenderSystem.recordRenderCall(this::lambda$updateAnimation$4);
                } else {
                    this.interpolationData.uploadInterpolated();
                }
            }
        }
    }

    public boolean hasAnimationMetadata() {
        return this.animationMetadata.getFrameCount() > 1;
    }

    public IVertexBuilder wrapBuffer(IVertexBuilder iVertexBuilder) {
        IRenderTypeBuffer.Impl impl;
        if (this.getName() == TextureUtils.LOCATION_SPRITE_EMPTY && (impl = iVertexBuilder.getRenderTypeBuffer()) != null) {
            return impl.getDummyBuffer();
        }
        return new SpriteAwareVertexBuilder(iVertexBuilder, this);
    }

    public int getIndexInMap() {
        return this.indexInMap;
    }

    public void updateIndexInMap(CounterInt counterInt) {
        if (this.indexInMap < 0) {
            TextureAtlasSprite textureAtlasSprite;
            if (this.atlasTexture != null && (textureAtlasSprite = this.atlasTexture.getRegisteredSprite(this.getName())) != null) {
                this.indexInMap = textureAtlasSprite.getIndexInMap();
            }
            if (this.indexInMap < 0) {
                this.indexInMap = counterInt.nextValue();
            }
        }
    }

    public int getAnimationIndex() {
        return this.animationIndex;
    }

    public void setAnimationIndex(int n) {
        this.animationIndex = n;
        if (this.spriteSingle != null) {
            this.spriteSingle.setAnimationIndex(n);
        }
        if (this.spriteNormal != null) {
            this.spriteNormal.setAnimationIndex(n);
        }
        if (this.spriteSpecular != null) {
            this.spriteSpecular.setAnimationIndex(n);
        }
    }

    public boolean isAnimationActive() {
        return this.animationActive;
    }

    private void fixTransparentColor(NativeImage nativeImage) {
        int[] nArray = new int[nativeImage.getWidth() * nativeImage.getHeight()];
        nativeImage.getBufferRGBA().get(nArray);
        this.fixTransparentColor(nArray);
        nativeImage.getBufferRGBA().put(nArray);
    }

    private void fixTransparentColor(int[] nArray) {
        if (nArray != null) {
            int n;
            int n2;
            int n3;
            int n4;
            int n5;
            int n6;
            long l = 0L;
            long l2 = 0L;
            long l3 = 0L;
            long l4 = 0L;
            for (n6 = 0; n6 < nArray.length; ++n6) {
                n5 = nArray[n6];
                n4 = n5 >> 24 & 0xFF;
                if (n4 < 16) continue;
                n3 = n5 >> 16 & 0xFF;
                n2 = n5 >> 8 & 0xFF;
                n = n5 & 0xFF;
                l += (long)n3;
                l2 += (long)n2;
                l3 += (long)n;
                ++l4;
            }
            if (l4 > 0L) {
                n6 = (int)(l / l4);
                n5 = (int)(l2 / l4);
                n4 = (int)(l3 / l4);
                n3 = n6 << 16 | n5 << 8 | n4;
                for (n2 = 0; n2 < nArray.length; ++n2) {
                    n = nArray[n2];
                    int n7 = n >> 24 & 0xFF;
                    if (n7 > 16) continue;
                    nArray[n2] = n3;
                }
            }
        }
    }

    public double getSpriteU16(float f) {
        float f2 = this.maxU - this.minU;
        return (f - this.minU) / f2 * 16.0f;
    }

    public double getSpriteV16(float f) {
        float f2 = this.maxV - this.minV;
        return (f - this.minV) / f2 * 16.0f;
    }

    public void bindSpriteTexture() {
        if (this.glSpriteTextureId < 0) {
            this.glSpriteTextureId = TextureUtil.generateTextureId();
            TextureUtil.prepareImage(this.glSpriteTextureId, this.mipmapLevels, this.getWidth(), this.getHeight());
            boolean bl = this.atlasTexture.isTextureBlend(this.spriteShadersType);
            if (bl) {
                TextureUtils.applyAnisotropicLevel();
            } else {
                GlStateManager.texParameter(3553, 34046, 1.0f);
                int n = this.mipmapLevels > 0 ? 9984 : 9728;
                GlStateManager.texParameter(3553, 10241, n);
                GlStateManager.texParameter(3553, 10240, 9728);
            }
        }
        TextureUtils.bindTexture(this.glSpriteTextureId);
    }

    public void deleteSpriteTexture() {
        if (this.glSpriteTextureId >= 0) {
            TextureUtil.releaseTextureId(this.glSpriteTextureId);
            this.glSpriteTextureId = -1;
        }
    }

    public float toSingleU(float f) {
        float f2 = (float)this.sheetWidth / (float)this.getWidth();
        return (f -= this.baseU) * f2;
    }

    public float toSingleV(float f) {
        float f2 = (float)this.sheetHeight / (float)this.getHeight();
        return (f -= this.baseV) * f2;
    }

    public NativeImage[] getMipmapImages() {
        return this.frames;
    }

    public AnimationMetadataSection getAnimationMetadata() {
        return this.animationMetadata;
    }

    public int getOriginX() {
        return this.x;
    }

    public int getOriginY() {
        return this.y;
    }

    public float getUnInterpolatedU(float f) {
        float f2 = this.maxU - this.minU;
        return (f - this.minU) / f2 * 16.0f;
    }

    public float getUnInterpolatedV(float f) {
        float f2 = this.maxV - this.minV;
        return (f - this.minV) / f2 * 16.0f;
    }

    public TextureAtlasSprite makeSpriteSingle() {
        TextureAtlasSprite textureAtlasSprite = new TextureAtlasSprite(this);
        textureAtlasSprite.isSpriteSingle = true;
        return textureAtlasSprite;
    }

    public TextureAtlasSprite makeSpriteShaders(ShadersTextureType shadersTextureType, int n, AnimationMetadataSection animationMetadataSection) {
        Object object;
        AutoCloseable autoCloseable;
        String string = shadersTextureType.getSuffix();
        ResourceLocation resourceLocation = new ResourceLocation(this.getName().getNamespace(), this.getName().getPath() + string);
        ResourceLocation resourceLocation2 = this.atlasTexture.getSpritePath(resourceLocation);
        TextureAtlasSprite textureAtlasSprite = null;
        if (this.resourceManager.hasResource(resourceLocation2)) {
            try {
                autoCloseable = this.resourceManager.getResource(resourceLocation2);
                try {
                    NativeImage nativeImage;
                    IResource iResource = this.resourceManager.getResource(resourceLocation2);
                    object = new PngSizeInfo(resourceLocation2.toString(), iResource.getInputStream());
                    AnimationMetadataSection animationMetadataSection2 = autoCloseable.getMetadata(AnimationMetadataSection.SERIALIZER);
                    if (animationMetadataSection2 == null) {
                        animationMetadataSection2 = AnimationMetadataSection.EMPTY;
                    }
                    Pair<Integer, Integer> pair = animationMetadataSection2.getSpriteSize(((PngSizeInfo)object).width, ((PngSizeInfo)object).height);
                    Info info = new Info(resourceLocation, pair.getFirst(), pair.getSecond(), animationMetadataSection2);
                    NativeImage nativeImage2 = NativeImage.read(autoCloseable.getInputStream());
                    if (nativeImage2.getWidth() != this.getWidth() && (nativeImage = TextureUtils.scaleImage(nativeImage2, this.getWidth())) != nativeImage2) {
                        double d = 1.0 * (double)this.getWidth() / (double)nativeImage2.getWidth();
                        nativeImage2.close();
                        nativeImage2 = nativeImage;
                        info = new Info(resourceLocation, (int)((double)pair.getFirst().intValue() * d), (int)((double)pair.getSecond().intValue() * d), animationMetadataSection2);
                    }
                    textureAtlasSprite = new TextureAtlasSprite(this.atlasTexture, info, this.mipmapLevels, this.sheetWidth, this.sheetHeight, this.x, this.y, nativeImage2, shadersTextureType);
                } finally {
                    if (autoCloseable != null) {
                        autoCloseable.close();
                    }
                }
            } catch (IOException iOException) {
                // empty catch block
            }
        }
        if (textureAtlasSprite == null) {
            autoCloseable = new NativeImage(this.getWidth(), this.getHeight(), false);
            int n2 = TextureUtils.toAbgr(n);
            ((NativeImage)autoCloseable).fillAreaRGBA(0, 0, ((NativeImage)autoCloseable).getWidth(), ((NativeImage)autoCloseable).getHeight(), n2);
            object = new Info(resourceLocation, this.getWidth(), this.getHeight(), AnimationMetadataSection.EMPTY);
            textureAtlasSprite = new TextureAtlasSprite(this.atlasTexture, (Info)object, this.mipmapLevels, this.sheetWidth, this.sheetHeight, this.x, this.y, (NativeImage)autoCloseable, shadersTextureType);
        }
        if (this.terrain && this.multiTexture && !this.isSpriteSingle) {
            textureAtlasSprite.spriteSingle = textureAtlasSprite.makeSpriteSingle();
        }
        textureAtlasSprite.usesParentAnimationTime = TextureAtlasSprite.matchesTiming(textureAtlasSprite.animationMetadata, animationMetadataSection);
        return textureAtlasSprite;
    }

    public boolean isTerrain() {
        return this.terrain;
    }

    private void setTerrain(boolean bl) {
        this.terrain = bl;
        this.multiTexture = false;
        this.shaders = false;
        if (this.spriteSingle != null) {
            this.deleteSpriteTexture();
            this.spriteSingle = null;
        }
        if (this.spriteNormal != null) {
            if (this.spriteNormal.spriteSingle != null) {
                this.spriteNormal.deleteSpriteTexture();
            }
            this.spriteNormal.close();
            this.spriteNormal = null;
        }
        if (this.spriteSpecular != null) {
            if (this.spriteSpecular.spriteSingle != null) {
                this.spriteSpecular.deleteSpriteTexture();
            }
            this.spriteSpecular.close();
            this.spriteSpecular = null;
        }
        this.multiTexture = Config.isMultiTexture();
        this.shaders = Config.isShaders();
        if (this.terrain && this.multiTexture && !this.isSpriteSingle) {
            this.spriteSingle = this.makeSpriteSingle();
        }
        if (this.shaders && !this.isSpriteSingle) {
            if (this.spriteNormal == null && Shaders.configNormalMap) {
                this.spriteNormal = this.makeSpriteShaders(ShadersTextureType.NORMAL, -8421377, this.animationMetadata);
            }
            if (this.spriteSpecular == null && Shaders.configSpecularMap) {
                this.spriteSpecular = this.makeSpriteShaders(ShadersTextureType.SPECULAR, 0, this.animationMetadata);
            }
        }
    }

    private static boolean matchesTiming(AnimationMetadataSection animationMetadataSection, AnimationMetadataSection animationMetadataSection2) {
        if (animationMetadataSection == animationMetadataSection2) {
            return false;
        }
        if (animationMetadataSection != null && animationMetadataSection2 != null) {
            if (animationMetadataSection.getFrameTime() != animationMetadataSection2.getFrameTime()) {
                return true;
            }
            if (animationMetadataSection.isInterpolate() != animationMetadataSection2.isInterpolate()) {
                return true;
            }
            if (animationMetadataSection.getFrameCount() != animationMetadataSection2.getFrameCount()) {
                return true;
            }
            for (int i = 0; i < animationMetadataSection.getFrameCount(); ++i) {
                if (animationMetadataSection.getFrameTimeSingle(i) == animationMetadataSection2.getFrameTimeSingle(i)) continue;
                return true;
            }
            return false;
        }
        return true;
    }

    public void update(IResourceManager iResourceManager) {
        this.resourceManager = iResourceManager;
        this.updateIndexInMap(this.atlasTexture.getCounterIndexInMap());
        this.setTerrain(this.atlasTexture.isTerrain());
    }

    public int getPixelRGBA(int n, int n2, int n3) {
        return this.frames[0].getPixelRGBA(n2 + this.framesX[n] * this.getWidth(), n3 + this.framesY[n] * this.getHeight());
    }

    private void lambda$updateAnimation$4() {
        this.interpolationData.uploadInterpolated();
    }

    private String lambda$new$3() throws Exception {
        return this.getFrameCount() + " frames";
    }

    private String lambda$new$2() throws Exception {
        return this.getWidth() + " x " + this.getHeight();
    }

    private String lambda$new$1() throws Exception {
        return this.getName().toString();
    }

    private static String lambda$new$0(NativeImage nativeImage) throws Exception {
        StringBuilder stringBuilder = new StringBuilder();
        if (stringBuilder.length() > 0) {
            stringBuilder.append(", ");
        }
        stringBuilder.append(nativeImage.getWidth()).append("x").append(nativeImage.getHeight());
        return stringBuilder.toString();
    }

    public static final class Info {
        private final ResourceLocation spriteLocation;
        private int spriteWidth;
        private int spriteHeight;
        private final AnimationMetadataSection spriteAnimationMetadata;
        private double scaleFactor = 1.0;

        public Info(ResourceLocation resourceLocation, int n, int n2, AnimationMetadataSection animationMetadataSection) {
            this.spriteLocation = resourceLocation;
            this.spriteWidth = n;
            this.spriteHeight = n2;
            this.spriteAnimationMetadata = animationMetadataSection;
        }

        public ResourceLocation getSpriteLocation() {
            return this.spriteLocation;
        }

        public int getSpriteWidth() {
            return this.spriteWidth;
        }

        public int getSpriteHeight() {
            return this.spriteHeight;
        }

        public void setSpriteWidth(int n) {
            this.spriteWidth = n;
        }

        public void setSpriteHeight(int n) {
            this.spriteHeight = n;
        }

        public AnimationMetadataSection getSpriteAnimationMetadata() {
            return this.spriteAnimationMetadata;
        }

        public double getScaleFactor() {
            return this.scaleFactor;
        }

        public void setScaleFactor(double d) {
            this.scaleFactor = d;
        }

        public String toString() {
            return this.spriteLocation + ", width: " + this.spriteWidth + ", height: " + this.spriteHeight + ", frames: " + this.spriteAnimationMetadata.getFrameCount() + ", scale: " + this.scaleFactor;
        }
    }

    final class InterpolationData
    implements AutoCloseable {
        private final NativeImage[] images;
        final TextureAtlasSprite this$0;

        private InterpolationData(TextureAtlasSprite textureAtlasSprite, NativeImage[] nativeImageArray) {
            this.this$0 = textureAtlasSprite;
            this.images = nativeImageArray;
        }

        private InterpolationData(TextureAtlasSprite textureAtlasSprite, Info info, int n) {
            this.this$0 = textureAtlasSprite;
            this.images = new NativeImage[n + 1];
            for (int i = 0; i < this.images.length; ++i) {
                int n2 = info.spriteWidth >> i;
                int n3 = info.spriteHeight >> i;
                if (this.images[i] != null) continue;
                this.images[i] = new NativeImage(n2, n3, false);
            }
        }

        private void uploadInterpolated() {
            int n;
            int n2;
            double d = 1.0 - (double)this.this$0.tickCounter / (double)this.this$0.animationMetadata.getFrameTimeSingle(this.this$0.frameCounter);
            int n3 = this.this$0.animationMetadata.getFrameIndex(this.this$0.frameCounter);
            if (n3 != (n2 = this.this$0.animationMetadata.getFrameIndex((this.this$0.frameCounter + 1) % (n = this.this$0.animationMetadata.getFrameCount() == 0 ? this.this$0.getFrameCount() : this.this$0.animationMetadata.getFrameCount()))) && n2 >= 0 && n2 < this.this$0.getFrameCount()) {
                if (!this.this$0.isSpriteSingle) {
                    for (int i = 0; i < this.images.length; ++i) {
                        int n4 = this.this$0.spriteInfo.spriteWidth >> i;
                        int n5 = this.this$0.spriteInfo.spriteHeight >> i;
                        for (int j = 0; j < n5; ++j) {
                            for (int k = 0; k < n4; ++k) {
                                int n6 = this.getPixelColor(n3, i, k, j);
                                int n7 = this.getPixelColor(n2, i, k, j);
                                int n8 = this.mix(d, n6 >> 16 & 0xFF, n7 >> 16 & 0xFF);
                                int n9 = this.mix(d, n6 >> 8 & 0xFF, n7 >> 8 & 0xFF);
                                int n10 = this.mix(d, n6 & 0xFF, n7 & 0xFF);
                                this.images[i].setPixelRGBA(k, j, n6 & 0xFF000000 | n8 << 16 | n9 << 8 | n10);
                            }
                        }
                    }
                }
                this.this$0.uploadFrames(0, 0, this.images);
            }
        }

        private int getPixelColor(int n, int n2, int n3, int n4) {
            return this.this$0.frames[n2].getPixelRGBA(n3 + (this.this$0.framesX[n] * this.this$0.spriteInfo.spriteWidth >> n2), n4 + (this.this$0.framesY[n] * this.this$0.spriteInfo.spriteHeight >> n2));
        }

        private int mix(double d, int n, int n2) {
            return (int)(d * (double)n + (1.0 - d) * (double)n2);
        }

        @Override
        public void close() {
            for (NativeImage nativeImage : this.images) {
                if (nativeImage == null) continue;
                nativeImage.close();
            }
        }
    }
}

