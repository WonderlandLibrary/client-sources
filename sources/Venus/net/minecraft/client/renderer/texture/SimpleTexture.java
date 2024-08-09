/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.renderer.texture;

import com.mojang.blaze3d.systems.RenderSystem;
import java.io.Closeable;
import java.io.IOException;
import javax.annotation.Nullable;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.data.TextureMetadataSection;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.EmissiveTextures;
import net.optifine.shaders.ShadersTex;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleTexture
extends Texture {
    private static final Logger LOGGER = LogManager.getLogger();
    protected final ResourceLocation textureLocation;
    private IResourceManager resourceManager;
    public ResourceLocation locationEmissive;
    public boolean isEmissive;

    public SimpleTexture(ResourceLocation resourceLocation) {
        this.textureLocation = resourceLocation;
    }

    @Override
    public void loadTexture(IResourceManager iResourceManager) throws IOException {
        boolean bl;
        boolean bl2;
        this.resourceManager = iResourceManager;
        TextureData textureData = this.getTextureData(iResourceManager);
        textureData.checkException();
        TextureMetadataSection textureMetadataSection = textureData.getMetadata();
        if (textureMetadataSection != null) {
            bl2 = textureMetadataSection.getTextureBlur();
            bl = textureMetadataSection.getTextureClamp();
        } else {
            bl2 = false;
            bl = false;
        }
        NativeImage nativeImage = textureData.getNativeImage();
        if (!RenderSystem.isOnRenderThreadOrInit()) {
            RenderSystem.recordRenderCall(() -> this.lambda$loadTexture$0(nativeImage, bl2, bl));
        } else {
            this.loadImage(nativeImage, bl2, bl);
        }
    }

    private void loadImage(NativeImage nativeImage, boolean bl, boolean bl2) {
        TextureUtil.prepareImage(this.getGlTextureId(), 0, nativeImage.getWidth(), nativeImage.getHeight());
        nativeImage.uploadTextureSub(0, 0, 0, 0, 0, nativeImage.getWidth(), nativeImage.getHeight(), bl, bl2, false, false);
        if (Config.isShaders()) {
            ShadersTex.loadSimpleTextureNS(this.getGlTextureId(), nativeImage, bl, bl2, this.resourceManager, this.textureLocation, this.getMultiTexID());
        }
        if (EmissiveTextures.isActive()) {
            EmissiveTextures.loadTexture(this.textureLocation, this);
        }
    }

    protected TextureData getTextureData(IResourceManager iResourceManager) {
        return TextureData.getTextureData(iResourceManager, this.textureLocation);
    }

    private void lambda$loadTexture$0(NativeImage nativeImage, boolean bl, boolean bl2) {
        this.loadImage(nativeImage, bl, bl2);
    }

    public static class TextureData
    implements Closeable {
        @Nullable
        private final TextureMetadataSection metadata;
        @Nullable
        private final NativeImage nativeImage;
        @Nullable
        private final IOException exception;

        public TextureData(IOException iOException) {
            this.exception = iOException;
            this.metadata = null;
            this.nativeImage = null;
        }

        public TextureData(@Nullable TextureMetadataSection textureMetadataSection, NativeImage nativeImage) {
            this.exception = null;
            this.metadata = textureMetadataSection;
            this.nativeImage = nativeImage;
        }

        public static TextureData getTextureData(IResourceManager iResourceManager, ResourceLocation resourceLocation) {
            TextureData textureData;
            block10: {
                IResource iResource = iResourceManager.getResource(resourceLocation);
                try {
                    NativeImage nativeImage = NativeImage.read(iResource.getInputStream());
                    TextureMetadataSection textureMetadataSection = null;
                    try {
                        textureMetadataSection = iResource.getMetadata(TextureMetadataSection.SERIALIZER);
                    } catch (RuntimeException runtimeException) {
                        LOGGER.warn("Failed reading metadata of: {}", (Object)resourceLocation, (Object)runtimeException);
                    }
                    textureData = new TextureData(textureMetadataSection, nativeImage);
                    if (iResource == null) break block10;
                } catch (Throwable throwable) {
                    try {
                        if (iResource != null) {
                            try {
                                iResource.close();
                            } catch (Throwable throwable2) {
                                throwable.addSuppressed(throwable2);
                            }
                        }
                        throw throwable;
                    } catch (IOException iOException) {
                        return new TextureData(iOException);
                    }
                }
                iResource.close();
            }
            return textureData;
        }

        @Nullable
        public TextureMetadataSection getMetadata() {
            return this.metadata;
        }

        public NativeImage getNativeImage() throws IOException {
            if (this.exception != null) {
                throw this.exception;
            }
            return this.nativeImage;
        }

        @Override
        public void close() {
            if (this.nativeImage != null) {
                this.nativeImage.close();
            }
        }

        public void checkException() throws IOException {
            if (this.exception != null) {
                throw this.exception;
            }
        }
    }
}

