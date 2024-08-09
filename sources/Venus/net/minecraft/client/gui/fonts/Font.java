/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.fonts;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.ints.IntOpenHashSet;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import net.minecraft.client.gui.fonts.DefaultGlyph;
import net.minecraft.client.gui.fonts.EmptyGlyph;
import net.minecraft.client.gui.fonts.FontTexture;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.IGlyphInfo;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.gui.fonts.WhiteGlyph;
import net.minecraft.client.gui.fonts.providers.IGlyphProvider;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;

public class Font
implements AutoCloseable {
    private static final EmptyGlyph EMPTY_GLYPH = new EmptyGlyph();
    private static final IGlyph GLYPH_ADVANCE_SPACE = Font::lambda$static$0;
    private static final Random RANDOM = new Random();
    private final TextureManager textureManager;
    private final ResourceLocation id;
    private TexturedGlyph fallbackGlyph;
    private TexturedGlyph whiteGlyph;
    private final List<IGlyphProvider> glyphProviders = Lists.newArrayList();
    private final Int2ObjectMap<TexturedGlyph> mapTexturedGlyphs = new Int2ObjectOpenHashMap<TexturedGlyph>();
    private final Int2ObjectMap<IGlyph> glyphs = new Int2ObjectOpenHashMap<IGlyph>();
    private final Int2ObjectMap<IntList> glyphsByWidth = new Int2ObjectOpenHashMap<IntList>();
    private final List<FontTexture> textures = Lists.newArrayList();

    public Font(TextureManager textureManager, ResourceLocation resourceLocation) {
        this.textureManager = textureManager;
        this.id = resourceLocation;
    }

    public void setGlyphProviders(List<IGlyphProvider> list) {
        this.func_230154_b_();
        this.deleteTextures();
        this.mapTexturedGlyphs.clear();
        this.glyphs.clear();
        this.glyphsByWidth.clear();
        this.fallbackGlyph = this.createTexturedGlyph(DefaultGlyph.INSTANCE);
        this.whiteGlyph = this.createTexturedGlyph(WhiteGlyph.INSTANCE);
        IntOpenHashSet intOpenHashSet = new IntOpenHashSet();
        for (IGlyphProvider iGlyphProvider : list) {
            intOpenHashSet.addAll(iGlyphProvider.func_230428_a_());
        }
        HashSet hashSet = Sets.newHashSet();
        intOpenHashSet.forEach(arg_0 -> this.lambda$setGlyphProviders$2(list, hashSet, arg_0));
        list.stream().filter(hashSet::contains).forEach(this.glyphProviders::add);
    }

    @Override
    public void close() {
        this.func_230154_b_();
        this.deleteTextures();
    }

    private void func_230154_b_() {
        for (IGlyphProvider iGlyphProvider : this.glyphProviders) {
            iGlyphProvider.close();
        }
        this.glyphProviders.clear();
    }

    private void deleteTextures() {
        for (FontTexture fontTexture : this.textures) {
            fontTexture.close();
        }
        this.textures.clear();
    }

    public IGlyph func_238557_a_(int n) {
        IGlyph iGlyph = (IGlyph)this.glyphs.get(n);
        if (iGlyph == null) {
            iGlyph = n == 32 ? GLYPH_ADVANCE_SPACE : this.getGlyphInfo(n);
            this.glyphs.put(n, iGlyph);
        }
        return iGlyph;
    }

    private IGlyphInfo getGlyphInfo(int n) {
        for (IGlyphProvider iGlyphProvider : this.glyphProviders) {
            IGlyphInfo iGlyphInfo = iGlyphProvider.getGlyphInfo(n);
            if (iGlyphInfo == null) continue;
            return iGlyphInfo;
        }
        return DefaultGlyph.INSTANCE;
    }

    public TexturedGlyph func_238559_b_(int n) {
        TexturedGlyph texturedGlyph = (TexturedGlyph)this.mapTexturedGlyphs.get(n);
        if (texturedGlyph == null) {
            texturedGlyph = n == 32 ? EMPTY_GLYPH : this.createTexturedGlyph(this.getGlyphInfo(n));
            this.mapTexturedGlyphs.put(n, texturedGlyph);
        }
        return texturedGlyph;
    }

    private TexturedGlyph createTexturedGlyph(IGlyphInfo iGlyphInfo) {
        for (FontTexture object2 : this.textures) {
            TexturedGlyph texturedGlyph = object2.createTexturedGlyph(iGlyphInfo);
            if (texturedGlyph == null) continue;
            return texturedGlyph;
        }
        FontTexture fontTexture = new FontTexture(new ResourceLocation(this.id.getNamespace(), this.id.getPath() + "/" + this.textures.size()), iGlyphInfo.isColored());
        this.textures.add(fontTexture);
        this.textureManager.loadTexture(fontTexture.getTextureLocation(), fontTexture);
        TexturedGlyph texturedGlyph = fontTexture.createTexturedGlyph(iGlyphInfo);
        return texturedGlyph == null ? this.fallbackGlyph : texturedGlyph;
    }

    public TexturedGlyph obfuscate(IGlyph iGlyph) {
        IntList intList = (IntList)this.glyphsByWidth.get(MathHelper.ceil(iGlyph.getAdvance(false)));
        return intList != null && !intList.isEmpty() ? this.func_238559_b_(intList.getInt(RANDOM.nextInt(intList.size()))) : this.fallbackGlyph;
    }

    public TexturedGlyph getWhiteGlyph() {
        return this.whiteGlyph;
    }

    private void lambda$setGlyphProviders$2(List list, Set set, int n) {
        for (IGlyphProvider iGlyphProvider : list) {
            IGlyph iGlyph = n == 32 ? GLYPH_ADVANCE_SPACE : iGlyphProvider.getGlyphInfo(n);
            if (iGlyph == null) continue;
            set.add(iGlyphProvider);
            if (iGlyph == DefaultGlyph.INSTANCE) break;
            this.glyphsByWidth.computeIfAbsent(MathHelper.ceil(iGlyph.getAdvance(false)), Font::lambda$setGlyphProviders$1).add(n);
            break;
        }
    }

    private static IntList lambda$setGlyphProviders$1(int n) {
        return new IntArrayList();
    }

    private static float lambda$static$0() {
        return 4.0f;
    }
}

