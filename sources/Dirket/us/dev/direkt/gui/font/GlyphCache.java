/*
 * Minecraft OpenType Font Support Mod
 *
 * Copyright (C) 2012 Wojciech Stryjewski <thvortex@gmail.com>
 *
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation; either version 2.1 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>..
 */

package us.dev.direkt.gui.font;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.IntBuffer;
import java.util.*;
import java.util.List;

class GlyphCache {
    private static final int TEXTURE_WIDTH = 256;
    private static final int TEXTURE_HEIGHT = 256;
    private static final int STRING_WIDTH = 256;
    private static final int STRING_HEIGHT = 64;
    private static final int GLYPH_BORDER = 1;
    private static Color BACK_COLOR = new Color(255, 255, 255, 0);
    private int fontSize = 18;
    private boolean antiAliasEnabled = false;
    private BufferedImage stringImage;
    private Graphics2D stringGraphics;
    private BufferedImage glyphCacheImage = new BufferedImage(256, 256, 2);
    private Graphics2D glyphCacheGraphics;
    private FontRenderContext fontRenderContext;
    private int[] imageData;
    private IntBuffer imageBuffer;
    private List<Font> allFonts;
    private List<Font> usedFonts;
    private int textureName;
    private ResourceLocation texture;
    private LinkedHashMap<Font, Integer> fontCache;
    private LinkedHashMap<Long, Entry> glyphCache;
    private int cachePosX;
    private int cachePosY;
    private int cacheLineHeight;

    static class Entry {
        public ResourceLocation text;
        public int textureName;
        public int width;
        public int height;
        public float u1;
        public float v1;
        public float u2;
        public float v2;
    }

    GlyphCache() {
        this.glyphCacheGraphics = this.glyphCacheImage.createGraphics();
        this.fontRenderContext = this.glyphCacheGraphics.getFontRenderContext();
        this.imageData = new int[65536];
        this.imageBuffer = ByteBuffer.allocateDirect(262144).order(ByteOrder.BIG_ENDIAN).asIntBuffer();
        this.allFonts = Arrays.asList(GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts());
        this.usedFonts = new ArrayList<>();
        this.fontCache = new LinkedHashMap<>();
        this.glyphCache = new LinkedHashMap<>();
        this.cachePosX = 1;
        this.cachePosY = 1;
        this.cacheLineHeight = 0;
        this.glyphCacheGraphics.setBackground(BACK_COLOR);
        this.glyphCacheGraphics.setComposite(AlphaComposite.Src);
        this.allocateGlyphCacheTexture();
        this.allocateStringImage(256, 64);
        GraphicsEnvironment.getLocalGraphicsEnvironment().preferLocaleFonts();
        this.usedFonts.add(new Font("SansSerif", 0, 1));
    }

    void setDefaultFont(String var1, int var2, boolean var3) {
        this.usedFonts.clear();
        this.usedFonts.add(new Font(var1, 0, 1));
        this.fontSize = var2;
        this.antiAliasEnabled = var3;
        this.setRenderingHints();
    }

    GlyphVector layoutGlyphVector(Font var1, char[] var2, int var3, int var4, int var5) {
        if (!this.fontCache.containsKey(var1)) {
            this.fontCache.put(var1, this.fontCache.size());
        }

        return var1.layoutGlyphVector(this.fontRenderContext, var2, var3, var4, var5);
    }

    Font lookupFont(char[] var1, int var2, int var3, int var4) {
        Iterator<Font> var5 = this.usedFonts.iterator();
        Font var6;

        do {
            if (!var5.hasNext()) {
                var5 = this.allFonts.iterator();

                do {
                    if (!var5.hasNext()) {
                        var6 = this.usedFonts.get(0);
                        return var6.deriveFont(var4, (float) this.fontSize);
                    }

                    var6 = var5.next();
                }
                while (var6.canDisplayUpTo(var1, var2, var3) == var2);

                System.out.println("BetterFonts loading font \"" + var6.getFontName() + "\"");
                this.usedFonts.add(var6);
                return var6.deriveFont(var4, (float) this.fontSize);
            }

            var6 = var5.next();
        }
        while (var6.canDisplayUpTo(var1, var2, var3) == var2);

        return var6.deriveFont(var4, (float) this.fontSize);
    }

    Entry lookupGlyph(Font var1, int var2) {
        long var3 = (long) (Integer) this.fontCache.get(var1) << 32;
        return this.glyphCache.get(var3 | (long) var2);
    }

    void cacheGlyphs(Font font, char[] text, int start, int limit, int layoutFlags) {
        GlyphVector vector = this.layoutGlyphVector(font, text, start, limit, layoutFlags);
        Rectangle vectorBounds = null;
        long fontKey = (long) (Integer) this.fontCache.get(font) << 32;
        int numGlyphs = vector.getNumGlyphs();
        Rectangle dirty = null;
        boolean vectorRendered = false;

        for (int index = 0; index < numGlyphs; ++index) {
            int glyphCode = vector.getGlyphCode(index);

            if (!this.glyphCache.containsKey(fontKey | (long) glyphCode)) {
                if (!vectorRendered) {
                    vectorRendered = true;
                    int var15;

                    for (var15 = 0; var15 < numGlyphs; ++var15) {
                        Point2D var16 = vector.getGlyphPosition(var15);
                        var16.setLocation(var16.getX() + (double) (2 * var15), var16.getY());
                        vector.setGlyphPosition(var15, var16);
                    }

                    vectorBounds = vector.getPixelBounds(this.fontRenderContext, 0.0F, 0.0F);

                    if (this.stringImage == null) {
                        this.allocateStringImage(vectorBounds.width, vectorBounds.height);
                    } else if (vectorBounds.width > this.stringImage.getWidth() || vectorBounds.height > this.stringImage.getHeight()) {
                        var15 = Math.max(vectorBounds.width, this.stringImage.getWidth());
                        int var18 = Math.max(vectorBounds.height, this.stringImage.getHeight());
                        this.allocateStringImage(var15, var18);
                    }

                    this.stringGraphics.clearRect(0, 0, vectorBounds.width, vectorBounds.height);
                    this.stringGraphics.drawGlyphVector(vector, (float) (-vectorBounds.x), (float) (-vectorBounds.y));
                }

                Rectangle var19 = vector.getGlyphPixelBounds(index, null, (float) (-vectorBounds.x), (float) (-vectorBounds.y));

                if (this.cachePosX + var19.width + 1 > 256) {
                    this.cachePosX = 1;
                    this.cachePosY += this.cacheLineHeight + 1;
                    this.cacheLineHeight = 0;
                }

                if (this.cachePosY + var19.height + 1 > 256) {
                    this.updateTexture(dirty);
                    dirty = null;

                    this.allocateGlyphCacheTexture();
                    this.cachePosY = this.cachePosX = 1;
                    this.cacheLineHeight = 0;
                }

                if (var19.height > this.cacheLineHeight) {
                    this.cacheLineHeight = var19.height;
                }

                this.glyphCacheGraphics.drawImage(this.stringImage, this.cachePosX, this.cachePosY, this.cachePosX + var19.width, this.cachePosY + var19.height, var19.x, var19.y, var19.x + var19.width, var19.y + var19.height, null);
                var19.setLocation(this.cachePosX, this.cachePosY);
                Entry var17 = new Entry();
                var17.textureName = this.textureName;
                var17.text = this.texture;
                var17.width = var19.width;
                var17.height = var19.height;
                var17.u1 = (float) var19.x / 256.0F;
                var17.v1 = (float) var19.y / 256.0F;
                var17.u2 = (float) (var19.x + var19.width) / 256.0F;
                var17.v2 = (float) (var19.y + var19.height) / 256.0F;
                this.glyphCache.put(fontKey | (long) glyphCode, var17);

                if (dirty == null) {
                    dirty = new Rectangle(this.cachePosX, this.cachePosY, var19.width, var19.height);
                } else {
                    dirty.add(var19);
                }

                this.cachePosX += var19.width + 1;
            }
        }

        this.updateTexture(dirty);
    }

    private void updateTexture(Rectangle dirty) {
        if (dirty != null) {
            this.updateImageBuffer(dirty.x, dirty.y, dirty.width, dirty.height);
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
            GL11.glTexSubImage2D(GL11.GL_TEXTURE_2D, 0, dirty.x, dirty.y, dirty.width, dirty.height, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageBuffer);
        }
    }

    private void allocateStringImage(int width, int height) {
        this.stringImage = new BufferedImage(width, height, 2);
        this.stringGraphics = this.stringImage.createGraphics();
        this.setRenderingHints();

        this.stringGraphics.setBackground(BACK_COLOR);

        this.stringGraphics.setPaint(Color.WHITE);
    }

    private void setRenderingHints() {
        this.stringGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, this.antiAliasEnabled ? RenderingHints.VALUE_ANTIALIAS_ON : RenderingHints.VALUE_ANTIALIAS_OFF);
        this.stringGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, this.antiAliasEnabled ? RenderingHints.VALUE_TEXT_ANTIALIAS_ON : RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
        this.stringGraphics.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
    }

    private void allocateGlyphCacheTexture() {
        this.glyphCacheGraphics.clearRect(0, 0, 256, 256);
        this.textureName = GL11.glGenTextures();
        this.texture = new ResourceLocation("text." + this.textureName);
        final SimpleTexture textureObj = new SimpleTexture(this.texture);
        textureObj.glTextureId = this.textureName;
        Minecraft.getMinecraft().getTextureManager().mapTextureObjects.put(this.texture, textureObj);
        this.updateImageBuffer(0, 0, 256, 256);
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.texture);
        GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGBA8, 256, 256, 0, GL11.GL_RGBA, GL11.GL_UNSIGNED_BYTE, this.imageBuffer);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
        GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
    }

    private void updateImageBuffer(int var1, int var2, int var3, int var4) {
        this.glyphCacheImage.getRGB(var1, var2, var3, var4, this.imageData, 0, var3);

        for (int var5 = 0; var5 < var3 * var4; ++var5) {
            int var6 = this.imageData[var5];
            this.imageData[var5] = var6 << 8 | var6 >>> 24;
        }

        this.imageBuffer.clear();
        this.imageBuffer.put(this.imageData);
        this.imageBuffer.flip();
    }
}