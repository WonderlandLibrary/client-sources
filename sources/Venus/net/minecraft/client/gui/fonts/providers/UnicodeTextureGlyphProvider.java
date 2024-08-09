/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts.providers;

import com.google.common.collect.Maps;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.gui.fonts.providers.IGlyphProviderFactory;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UnicodeTextureGlyphProvider
implements IGlyphProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final IResourceManager resourceManager;
    private final byte[] sizes;
    private final String template;
    private final Map<ResourceLocation, NativeImage> field_211845_e = Maps.newHashMap();

    public UnicodeTextureGlyphProvider(IResourceManager iResourceManager, byte[] byArray, String string) {
        this.resourceManager = iResourceManager;
        this.sizes = byArray;
        this.template = string;
        for (int i = 0; i < 256; ++i) {
            int n = i * 256;
            ResourceLocation resourceLocation = this.func_238591_b_(n);
            try (IResource iResource = this.resourceManager.getResource(resourceLocation);
                 NativeImage nativeImage = NativeImage.read(NativeImage.PixelFormat.RGBA, iResource.getInputStream());){
                if (nativeImage.getWidth() == 256 && nativeImage.getHeight() == 256) {
                    for (int j = 0; j < 256; ++j) {
                        byte by = byArray[n + j];
                        if (by == 0 || UnicodeTextureGlyphProvider.func_212453_a(by) <= UnicodeTextureGlyphProvider.func_212454_b(by)) continue;
                        byArray[n + j] = 0;
                    }
                    continue;
                }
            } catch (IOException iOException) {
                // empty catch block
            }
            Arrays.fill(byArray, n, n + 256, (byte)0);
        }
    }

    @Override
    public void close() {
        this.field_211845_e.values().forEach(NativeImage::close);
    }

    private ResourceLocation func_238591_b_(int n) {
        ResourceLocation resourceLocation = new ResourceLocation(String.format(this.template, String.format("%02x", n / 256)));
        return new ResourceLocation(resourceLocation.getNamespace(), "textures/" + resourceLocation.getPath());
    }

    @Override
    @Nullable
    public IGlyphInfo getGlyphInfo(int n) {
        if (n >= 0 && n <= 65535) {
            NativeImage nativeImage;
            byte by = this.sizes[n];
            if (by != 0 && (nativeImage = this.field_211845_e.computeIfAbsent(this.func_238591_b_(n), this::loadTexture)) != null) {
                int n2 = UnicodeTextureGlyphProvider.func_212453_a(by);
                return new GlpyhInfo(n % 16 * 16 + n2, (n & 0xFF) / 16 * 16, UnicodeTextureGlyphProvider.func_212454_b(by) - n2, 16, nativeImage);
            }
            return null;
        }
        return null;
    }

    @Override
    public IntSet func_230428_a_() {
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
        for (int i = 0; i < 65535; ++i) {
            if (this.sizes[i] == 0) continue;
            intOpenHashSet.add(i);
        }
        return intOpenHashSet;
    }

    @Nullable
    private NativeImage loadTexture(ResourceLocation resourceLocation) {
        NativeImage nativeImage;
        block8: {
            IResource iResource = this.resourceManager.getResource(resourceLocation);
            try {
                nativeImage = NativeImage.read(NativeImage.PixelFormat.RGBA, iResource.getInputStream());
                if (iResource == null) break block8;
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
                    LOGGER.error("Couldn't load texture {}", (Object)resourceLocation, (Object)iOException);
                    return null;
                }
            }
            iResource.close();
        }
        return nativeImage;
    }

    private static int func_212453_a(byte by) {
        return by >> 4 & 0xF;
    }

    private static int func_212454_b(byte by) {
        return (by & 0xF) + 1;
    }

    static class GlpyhInfo
    implements IGlyphInfo {
        private final int width;
        private final int height;
        private final int unpackSkipPixels;
        private final int unpackSkipRows;
        private final NativeImage texture;

        private GlpyhInfo(int n, int n2, int n3, int n4, NativeImage nativeImage) {
            this.width = n3;
            this.height = n4;
            this.unpackSkipPixels = n;
            this.unpackSkipRows = n2;
            this.texture = nativeImage;
        }

        @Override
        public float getOversample() {
            return 2.0f;
        }

        @Override
        public int getWidth() {
            return this.width;
        }

        @Override
        public int getHeight() {
            return this.height;
        }

        @Override
        public float getAdvance() {
            return this.width / 2 + 1;
        }

        @Override
        public void uploadGlyph(int n, int n2) {
            this.texture.uploadTextureSub(0, n, n2, this.unpackSkipPixels, this.unpackSkipRows, this.width, this.height, false, true);
        }

        @Override
        public boolean isColored() {
            return this.texture.getFormat().getPixelSize() > 1;
        }

        @Override
        public float getShadowOffset() {
            return 0.5f;
        }

        @Override
        public float getBoldOffset() {
            return 0.5f;
        }
    }

    public static class Factory
    implements IGlyphProviderFactory {
        private final ResourceLocation sizes;
        private final String template;

        public Factory(ResourceLocation resourceLocation, String string) {
            this.sizes = resourceLocation;
            this.template = string;
        }

        public static IGlyphProviderFactory deserialize(JsonObject jsonObject) {
            return new Factory(new ResourceLocation(JSONUtils.getString(jsonObject, "sizes")), JSONUtils.getString(jsonObject, "template"));
        }

        @Override
        @Nullable
        public IGlyphProvider create(IResourceManager iResourceManager) {
            UnicodeTextureGlyphProvider unicodeTextureGlyphProvider;
            block8: {
                IResource iResource = Minecraft.getInstance().getResourceManager().getResource(this.sizes);
                try {
                    byte[] byArray = new byte[65536];
                    iResource.getInputStream().read(byArray);
                    unicodeTextureGlyphProvider = new UnicodeTextureGlyphProvider(iResourceManager, byArray, this.template);
                    if (iResource == null) break block8;
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
                        LOGGER.error("Cannot load {}, unicode glyphs will not render correctly", (Object)this.sizes);
                        return null;
                    }
                }
                iResource.close();
            }
            return unicodeTextureGlyphProvider;
        }
    }
}

