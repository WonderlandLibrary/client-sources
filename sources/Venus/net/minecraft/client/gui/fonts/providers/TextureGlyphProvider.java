/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts.providers;

import com.google.common.collect.Lists;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntSet;
import it.unimi.dsi.fastutil.ints.IntSets;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.gui.fonts.providers.IGlyphProviderFactory;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.resources.IResource;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.optifine.util.FontUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TextureGlyphProvider
implements IGlyphProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private final NativeImage texture;
    private final Int2ObjectMap<GlyphInfo> glyphInfos;
    private boolean blend = false;
    private float widthSpace = -1.0f;

    private TextureGlyphProvider(NativeImage nativeImage, Int2ObjectMap<GlyphInfo> int2ObjectMap) {
        this.texture = nativeImage;
        this.glyphInfos = int2ObjectMap;
    }

    @Override
    public void close() {
        this.texture.close();
    }

    @Override
    @Nullable
    public IGlyphInfo getGlyphInfo(int n) {
        return (IGlyphInfo)this.glyphInfos.get(n);
    }

    @Override
    public IntSet func_230428_a_() {
        return IntSets.unmodifiable(this.glyphInfos.keySet());
    }

    public boolean isBlend() {
        return this.blend;
    }

    public float getWidthSpace() {
        return this.widthSpace;
    }

    static final class GlyphInfo
    implements IGlyphInfo {
        private final float scale;
        private final NativeImage texture;
        private final int unpackSkipPixels;
        private final int unpackSkipRows;
        private final int width;
        private final int height;
        private final int advanceWidth;
        private final int ascent;
        private float offsetBold = 1.0f;

        private GlyphInfo(float f, NativeImage nativeImage, int n, int n2, int n3, int n4, int n5, int n6) {
            this.scale = f;
            this.texture = nativeImage;
            this.unpackSkipPixels = n;
            this.unpackSkipRows = n2;
            this.width = n3;
            this.height = n4;
            this.advanceWidth = n5;
            this.ascent = n6;
        }

        @Override
        public float getOversample() {
            return 1.0f / this.scale;
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
            return this.advanceWidth;
        }

        @Override
        public float getBearingY() {
            return IGlyphInfo.super.getBearingY() + 7.0f - (float)this.ascent;
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
        public float getBoldOffset() {
            return this.offsetBold;
        }
    }

    public static class Factory
    implements IGlyphProviderFactory {
        private ResourceLocation file;
        private final List<int[]> chars;
        private final int height;
        private final int ascent;

        public Factory(ResourceLocation resourceLocation, int n, int n2, List<int[]> list) {
            this.file = new ResourceLocation(resourceLocation.getNamespace(), "textures/" + resourceLocation.getPath());
            this.file = FontUtils.getHdFontLocation(this.file);
            this.chars = list;
            this.height = n;
            this.ascent = n2;
        }

        public static Factory deserialize(JsonObject jsonObject) {
            int n = JSONUtils.getInt(jsonObject, "height", 8);
            int n2 = JSONUtils.getInt(jsonObject, "ascent");
            if (n2 > n) {
                throw new JsonParseException("Ascent " + n2 + " higher than height " + n);
            }
            ArrayList<int[]> arrayList = Lists.newArrayList();
            JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "chars");
            for (int i = 0; i < jsonArray.size(); ++i) {
                int n3;
                String string = JSONUtils.getString(jsonArray.get(i), "chars[" + i + "]");
                int[] nArray = string.codePoints().toArray();
                if (i > 0 && nArray.length != (n3 = ((int[])arrayList.get(0)).length)) {
                    throw new JsonParseException("Elements of chars have to be the same length (found: " + nArray.length + ", expected: " + n3 + "), pad with space or \\u0000");
                }
                arrayList.add(nArray);
            }
            if (!arrayList.isEmpty() && ((int[])arrayList.get(0)).length != 0) {
                return new Factory(new ResourceLocation(JSONUtils.getString(jsonObject, "file")), n, n2, arrayList);
            }
            throw new JsonParseException("Expected to find data in chars, found none.");
        }

        @Override
        @Nullable
        public IGlyphProvider create(IResourceManager iResourceManager) {
            TextureGlyphProvider textureGlyphProvider;
            block14: {
                IResource iResource = iResourceManager.getResource(this.file);
                try {
                    NativeImage nativeImage = NativeImage.read(NativeImage.PixelFormat.RGBA, iResource.getInputStream());
                    int n = nativeImage.getWidth();
                    int n2 = nativeImage.getHeight();
                    int n3 = n / this.chars.get(0).length;
                    int n4 = n2 / this.chars.size();
                    float f = (float)this.height / (float)n4;
                    Int2ObjectOpenHashMap<GlyphInfo> int2ObjectOpenHashMap = new Int2ObjectOpenHashMap<GlyphInfo>();
                    Properties properties = FontUtils.readFontProperties(this.file);
                    Int2ObjectMap<Float> int2ObjectMap = FontUtils.readCustomCharWidths(properties);
                    Float f2 = (Float)int2ObjectMap.get(32);
                    boolean bl = FontUtils.readBoolean(properties, "blend", false);
                    float f3 = FontUtils.readFloat(properties, "offsetBold", -1.0f);
                    if (f3 < 0.0f) {
                        f3 = n3 > 8 ? 0.5f : 1.0f;
                    }
                    for (int i = 0; i < this.chars.size(); ++i) {
                        int n5 = 0;
                        for (int n6 : this.chars.get(i)) {
                            GlyphInfo glyphInfo;
                            int n7 = n5++;
                            if (n6 == 0 || n6 == 32) continue;
                            float f4 = this.getCharacterWidth(nativeImage, n3, n4, n7, i);
                            Float f5 = (Float)int2ObjectMap.get(n6);
                            if (f5 != null) {
                                f4 = f5.floatValue() * ((float)n3 / 8.0f);
                            }
                            if ((glyphInfo = int2ObjectOpenHashMap.put(n6, new GlyphInfo(f, nativeImage, n7 * n3, i * n4, n3, n4, (int)(0.5 + (double)(f4 * f)) + 1, this.ascent))) != null) {
                                LOGGER.warn("Codepoint '{}' declared multiple times in {}", (Object)Integer.toHexString(n6), (Object)this.file);
                            }
                            GlyphInfo glyphInfo2 = (GlyphInfo)int2ObjectOpenHashMap.get(n6);
                            glyphInfo2.offsetBold = f3;
                        }
                    }
                    TextureGlyphProvider textureGlyphProvider2 = new TextureGlyphProvider(nativeImage, int2ObjectOpenHashMap);
                    textureGlyphProvider2.blend = bl;
                    if (f2 != null) {
                        textureGlyphProvider2.widthSpace = f2.floatValue();
                    }
                    textureGlyphProvider = textureGlyphProvider2;
                    if (iResource == null) break block14;
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
                        throw new RuntimeException(iOException.getMessage());
                    }
                }
                iResource.close();
            }
            return textureGlyphProvider;
        }

        private int getCharacterWidth(NativeImage nativeImage, int n, int n2, int n3, int n4) {
            int n5;
            for (n5 = n - 1; n5 >= 0; --n5) {
                int n6 = n3 * n + n5;
                for (int i = 0; i < n2; ++i) {
                    int n7 = n4 * n2 + i;
                    if ((nativeImage.getPixelLuminanceOrAlpha(n6, n7) & 0xFF) <= 16) continue;
                    return n5 + 1;
                }
            }
            return n5 + 1;
        }
    }
}

