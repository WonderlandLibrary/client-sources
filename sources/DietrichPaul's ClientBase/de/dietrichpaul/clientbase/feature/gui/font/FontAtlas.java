/*
 * This file is part of Clientbase - https://github.com/DietrichPaul/Clientbase
 * by DietrichPaul, FlorianMichael and contributors
 *
 * To the extent possible under law, the person who associated CC0 with
 * Clientbase has waived all copyright and related or neighboring rights
 * to Clientbase.
 *
 * You should have received a copy of the CC0 legalcode along with this
 * work.  If not, see <http://creativecommons.org/publicdomain/zero/1.0/>.
 */
package de.dietrichpaul.clientbase.feature.gui.font;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.blaze3d.systems.RenderSystem;
import de.dietrichpaul.clientbase.util.render.api.Renderer2D;
import net.minecraft.client.render.*;
import net.minecraft.client.texture.NativeImage;
import net.minecraft.client.texture.NativeImageBackedTexture;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.resource.ResourceManager;
import net.minecraft.text.OrderedText;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.ColorHelper;
import org.joml.Matrix4f;

import java.io.*;

// https://github.com/Chlumsky/msdf-atlas-gen
//
// for linux use wine-package + win32 binary
// wine msdf-atlas-gen.exe -type msdf -font verdana.ttf -charset charset.txt -imageout verdana.png -size 128 -pxrange 8 -json verdana.json
//
// charset.txt: [30, 255]
public class FontAtlas {

    private final static String FORMATTING_PALETTE = "0123456789abcdefklmnor";
    private final static int[][] FORMATTING_COLOR_PALETTE = new int[32][3];

    private final int[] textColor = new int[3];
    private volatile float textX;

    private final int distanceRange;
    private final int width;
    private final int height;

    private float size = 9;

    private final Glyph[] glyphs = new Glyph[256];
    private final FontMetrics fontMetrics;

    private final NativeImageBackedTexture tex;

    static {
        // I don't know what this is but 1.8 code
        for (int i = 0; i < 32; ++i) {
            int j = (i >> 3 & 1) * 85;
            int k = (i >> 2 & 1) * 170 + j;
            int l = (i >> 1 & 1) * 170 + j;
            int i1 = (i & 1) * 170 + j;

            if (i == 6) {
                k += 85;
            }


            if (i >= 16) {
                k /= 4;
                l /= 4;
                i1 /= 4;
            }

            FORMATTING_COLOR_PALETTE[i][0] = k;
            FORMATTING_COLOR_PALETTE[i][1] = l;
            FORMATTING_COLOR_PALETTE[i][2] = i1;
        }
    }

    public void setSize(float size) {
        this.size = size;
    }

    public float getSize() {
        return size;
    }

    public FontAtlas(ResourceManager manager, String name) throws IOException {
        this(
                new InputStreamReader(manager.open(new Identifier("clientbase", "fonts/" + name + ".json"))),
                manager.open(new Identifier("clientbase", "fonts/" + name + ".png"))
        );
    }

    public FontAtlas(Reader meta, InputStream texture) throws IOException {
        tex = new NativeImageBackedTexture(NativeImage.read(texture));
        JsonObject atlasJson = JsonParser.parseReader(meta).getAsJsonObject();
        if ("msdf".equals(atlasJson.getAsJsonObject("atlas").get("width").getAsString())) {
            throw new RuntimeException("Unsupported atlas-type");
        }
        width = atlasJson.getAsJsonObject("atlas").get("width").getAsInt();
        height = atlasJson.getAsJsonObject("atlas").get("height").getAsInt();
        distanceRange = atlasJson.getAsJsonObject("atlas").get("distanceRange").getAsInt();
        fontMetrics = FontMetrics.parse(atlasJson.getAsJsonObject("metrics"));
        for (JsonElement glyphElement : atlasJson.getAsJsonArray("glyphs")) {
            JsonObject glyphObject = glyphElement.getAsJsonObject();
            Glyph glyph = Glyph.parse(glyphObject);
            glyphs[glyph.getUnicode()] = glyph;
        }
    }

    public void render(MatrixStack matrices, Text text, float x, float y, int color) {
        render(matrices, text.asOrderedText(), x, y, size, color);
    }

    public void render(MatrixStack matrices, Text text, float x, float y, float size, int color) {
        render(matrices, text.asOrderedText(), x, y, size, color);
    }

    public void render(MatrixStack matrices, OrderedText text, float x, float y, int color) {
        render(matrices, text, x, y, size, color);
    }

    public void render(MatrixStack matrices, OrderedText text, float x, float y, float size, int color) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, tex.getGlId());
        RenderSystem.setShader(Renderer2D.msdfShader::getProgram);
        Renderer2D.msdfShaderPxRange.set(distanceRange);

        textX = x;

        Matrix4f model = matrices.peek().getPositionMatrix();
        int alpha = ColorHelper.Argb.getAlpha(color);
        int red = ColorHelper.Argb.getRed(color);
        int green = ColorHelper.Argb.getGreen(color);
        int blue = ColorHelper.Argb.getBlue(color);

        textColor[0] = red;
        textColor[1] = green;
        textColor[2] = blue;

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        text.accept((index, style, codePoint) -> {
            Glyph glyph = glyphs[codePoint];
            if (glyph == null)
                return true;
            if (style.getColor() == null) {
                textColor[0] = red;
                textColor[1] = green;
                textColor[2] = blue;
            } else {
                int rgb = style.getColor().getRgb();
                textColor[0] = ColorHelper.Argb.getRed(rgb);
                textColor[1] = ColorHelper.Argb.getGreen(rgb);
                textColor[2] = ColorHelper.Argb.getBlue(rgb);
            }
            textX += visit(model, bufferBuilder, glyph, textX, y, size, alpha);
            return true;
        });
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    private float visit(Matrix4f model, BufferBuilder bufferBuilder, Glyph glyph, float x, float y, float size, int alpha) {
        if (glyph.getpRight() - glyph.getpLeft() != 0) {
            float x0 = x + glyph.getpLeft() * size;
            float x1 = x + glyph.getpRight() * size;
            float y0 = y + fontMetrics.getAscender() * size - glyph.getpTop() * size;
            float y1 = y + fontMetrics.getAscender() * size - glyph.getpBottom() * size;
            float u0 = glyph.getaLeft() / width;
            float u1 = glyph.getaRight() / width;
            float v0 = glyph.getaTop() / height;
            float v1 = glyph.getaBottom() / height;
            bufferBuilder.vertex(model, x0, y0, 0).texture(u0, 1 - v0).color(textColor[0], textColor[1], textColor[2], alpha).next();
            bufferBuilder.vertex(model, x0, y1, 0).texture(u0, 1 - v1).color(textColor[0], textColor[1], textColor[2], alpha).next();
            bufferBuilder.vertex(model, x1, y1, 0).texture(u1, 1 - v1).color(textColor[0], textColor[1], textColor[2], alpha).next();
            bufferBuilder.vertex(model, x1, y0, 0).texture(u1, 1 - v0).color(textColor[0], textColor[1], textColor[2], alpha).next();
        }
        return size * glyph.getAdvance();
    }

    public void render(MatrixStack matrixStack, String text, float x, float y, int color) {
        render(matrixStack, text, x, y, size, color);
    }

    public void render(MatrixStack matrices, String text, float x, float y, float size, int color) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderTexture(0, tex.getGlId());
        RenderSystem.setShader(Renderer2D.msdfShader::getProgram);
        Renderer2D.msdfShaderPxRange.set(distanceRange);

        Matrix4f model = matrices.peek().getPositionMatrix();
        int alpha = ColorHelper.Argb.getAlpha(color);
        int red = ColorHelper.Argb.getRed(color);
        int green = ColorHelper.Argb.getGreen(color);
        int blue = ColorHelper.Argb.getBlue(color);

        textColor[0] = red;
        textColor[1] = green;
        textColor[2] = blue;

        BufferBuilder bufferBuilder = Tessellator.getInstance().getBuffer();
        bufferBuilder.begin(VertexFormat.DrawMode.QUADS, VertexFormats.POSITION_TEXTURE_COLOR);
        for (int i = 0; i < text.length(); i++) {
            int unicode = text.codePointAt(i);

            if (unicode == '§' && i + 1 < text.length()) {
                int colorIndex = FORMATTING_PALETTE.indexOf(Character.toLowerCase(text.charAt(i + 1)));
                if (colorIndex < 16) { // COLOR code != FORMATTING code
                    System.arraycopy(FORMATTING_COLOR_PALETTE[colorIndex], 0, textColor, 0, 3);
                } else if (colorIndex == 21) { // reset
                    textColor[0] = red;
                    textColor[1] = green;
                    textColor[2] = blue;
                }
                i++;
            } else {

                Glyph glyph = glyphs[unicode];
                if (glyph == null)
                    continue;
                if (glyph.getpRight() - glyph.getpLeft() != 0) {
                    float x0 = x + glyph.getpLeft() * size;
                    float x1 = x + glyph.getpRight() * size;
                    float y0 = y + fontMetrics.getAscender() * size - glyph.getpTop() * size;
                    float y1 = y + fontMetrics.getAscender() * size - glyph.getpBottom() * size;
                    float u0 = glyph.getaLeft() / width;
                    float u1 = glyph.getaRight() / width;
                    float v0 = glyph.getaTop() / height;
                    float v1 = glyph.getaBottom() / height;
                    bufferBuilder.vertex(model, x0, y0, 0).texture(u0, 1 - v0).color(textColor[0], textColor[1], textColor[2], alpha).next();
                    bufferBuilder.vertex(model, x0, y1, 0).texture(u0, 1 - v1).color(textColor[0], textColor[1], textColor[2], alpha).next();
                    bufferBuilder.vertex(model, x1, y1, 0).texture(u1, 1 - v1).color(textColor[0], textColor[1], textColor[2], alpha).next();
                    bufferBuilder.vertex(model, x1, y0, 0).texture(u1, 1 - v0).color(textColor[0], textColor[1], textColor[2], alpha).next();
                }
                x += size * glyph.getAdvance();
            }
        }
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    public float getWidth(Text text) {
        return getWidth(text, size);
    }

    public float getWidth(Text text, float size) {
        return getWidth(text.asOrderedText(), size);
    }

    public float getWidth(OrderedText text) {
        return getWidth(text, size);
    }

    public float getWidth(OrderedText text, float size) {
        float[] sum = new float[1];
        text.accept((index, style, codePoint) -> {
            Glyph glyph = glyphs[codePoint];
            if (glyph == null)
                return true;
            if (glyph.getpRight() - glyph.getpLeft() != 0) {
                sum[0] += size * glyph.getAdvance();
            }
            return true;
        });
        return sum[0];
    }

    public float getWidth(String text) {
        return getWidth(text, size);
    }

    public float getWidth(String text, float size) {
        float sum = 0;
        for (int i = 0; i < text.length(); i++) {

            int unicode = text.codePointAt(i);
            if (unicode == '§' && i + 1 < text.length()) {
                i++;
            } else {
                Glyph glyph = glyphs[unicode];
                if (glyph == null)
                    continue;
                if (glyph.getpRight() - glyph.getpLeft() != 0) {
                    sum += size * glyph.getAdvance();
                }
            }
        }
        return sum;
    }

    public float getLineHeight() {
        return getLineHeight(size);
    }

    public float getLineHeight(float size) {
        return fontMetrics.getLineHeight() * size;
    }

    public void renderWithShadow(MatrixStack matrices, Text text, float x, float y, int color) {
        renderWithShadow(matrices, text, x, y, size, color);
    }

    public void renderWithShadow(MatrixStack matrices, OrderedText text, float x, float y, int color) {
        renderWithShadow(matrices, text, x, y, size, color);
    }

    public void renderWithShadow(MatrixStack matrices, String text, float x, float y, int color) {
        renderWithShadow(matrices, text, x, y, size, color);
    }

    /*
    Badly done: here comes an own method for batching with boolean dropShadow for color
    and setShaderColor must be omitted
     */
    public void renderWithShadow(MatrixStack matrices, Text text, float x, float y, float size, int color) {
        RenderSystem.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        render(matrices, text, x + 0.75F, y + 0.75F,size, color);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        render(matrices, text, x, y,size, color);
    }

    public void renderWithShadow(MatrixStack matrices, OrderedText text, float x, float y, float size, int color) {
        RenderSystem.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        render(matrices, text, x + 0.75F, y + 0.75F,size, color);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        render(matrices, text, x, y,size, color);
    }

    public void renderWithShadow(MatrixStack matrices, String text, float x, float y, float size, int color) {
        RenderSystem.setShaderColor(0.25F, 0.25F, 0.25F, 1.0F);
        render(matrices, text, x + 0.75F, y + 0.75F,size, color);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        render(matrices, text, x, y,size, color);
    }
}
