/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.utils.render.font;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import java.awt.Color;
import mpp.venusfr.utils.client.IMinecraft;
import mpp.venusfr.utils.render.ColorUtils;
import mpp.venusfr.utils.render.font.FontData;
import mpp.venusfr.utils.render.font.MsdfFont;
import mpp.venusfr.utils.shader.ShaderUtil;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;

public class Font
implements IMinecraft {
    private final String atlas;
    private final String data;
    private final MsdfFont font;

    public Font(String string, String string2) {
        this.atlas = string;
        this.data = string2;
        this.font = MsdfFont.builder().withAtlas(string).withData(string2).build();
    }

    public void drawText(MatrixStack matrixStack, String string, float f, float f2, int n, float f3) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        ShaderUtil shaderUtil = ShaderUtil.textShader;
        FontData.AtlasData atlasData = this.font.getAtlas();
        shaderUtil.attach();
        shaderUtil.setUniform("Sampler", 0);
        shaderUtil.setUniform("EdgeStrength", 0.5f);
        shaderUtil.setUniform("TextureSize", atlasData.width(), atlasData.height());
        shaderUtil.setUniform("Range", atlasData.range());
        shaderUtil.setUniform("Thickness", 0.0f);
        shaderUtil.setUniform("Outline", 0);
        shaderUtil.setUniform("OutlineThickness", 0.0f);
        shaderUtil.setUniform("OutlineColor", 1.0f, 1.0f, 1.0f, 1.0f);
        shaderUtil.setUniform("color", ColorUtils.rgba(n));
        this.font.bind();
        GlStateManager.enableBlend();
        Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        this.font.applyGlyphs(matrixStack.getLast().getMatrix(), Tessellator.getInstance().getBuffer(), f3, string, 0.0f, f, f2 + this.font.getMetrics().baselineHeight() * f3, 0.0f, 255, 255, 255, 255);
        Tessellator.getInstance().draw();
        this.font.unbind();
        shaderUtil.detach();
    }

    public void drawText(MatrixStack matrixStack, ITextComponent iTextComponent, float f, float f2, float f3, int n) {
        float f4 = 0.0f;
        for (ITextComponent iTextComponent2 : iTextComponent.getSiblings()) {
            for (ITextComponent iTextComponent3 : iTextComponent2.getSiblings()) {
                String string = iTextComponent3.getString();
                if (iTextComponent3.getStyle().getColor() != null) {
                    this.drawText(matrixStack, string, f + f4, f2, ColorUtils.setAlpha(ColorUtils.toColor(iTextComponent3.getStyle().getColor().getHex()), n), f3);
                } else {
                    this.drawText(matrixStack, string, f + f4, f2, ColorUtils.setAlpha(Color.GRAY.getRGB(), n), f3);
                }
                f4 += this.getWidth(string, f3);
            }
            if (iTextComponent2.getSiblings().size() > 1) continue;
            String string = TextFormatting.getTextWithoutFormattingCodes(iTextComponent2.getString());
            this.drawText(matrixStack, string, f + f4, f2, ColorUtils.setAlpha(iTextComponent2.getStyle().getColor() == null ? Color.GRAY.getRGB() : iTextComponent2.getStyle().getColor().getColor(), n), f3);
            f4 += this.getWidth(string, f3);
        }
        if (iTextComponent.getSiblings().isEmpty()) {
            String string = TextFormatting.getTextWithoutFormattingCodes(iTextComponent.getString());
            this.drawText(matrixStack, string, f + f4, f2, ColorUtils.setAlpha(iTextComponent.getStyle().getColor() == null ? Color.GRAY.getRGB() : iTextComponent.getStyle().getColor().getColor(), n), f3);
            f4 += this.getWidth(string, f3);
        }
    }

    public float getWidth(ITextComponent iTextComponent, float f) {
        float f2 = 0.0f;
        for (ITextComponent iTextComponent2 : iTextComponent.getSiblings()) {
            for (ITextComponent iTextComponent3 : iTextComponent2.getSiblings()) {
                String string = iTextComponent3.getString();
                f2 += this.getWidth(string, f);
            }
            if (iTextComponent2.getSiblings().size() > 1) continue;
            String string = TextFormatting.getTextWithoutFormattingCodes(iTextComponent2.getString());
            f2 += this.getWidth(string, f);
        }
        if (iTextComponent.getSiblings().isEmpty()) {
            String string = TextFormatting.getTextWithoutFormattingCodes(iTextComponent.getString());
            f2 += this.getWidth(string, f);
        }
        return f2;
    }

    public void drawTextBuilding(MatrixStack matrixStack, String string, float f, float f2, int n, float f3) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        ShaderUtil shaderUtil = ShaderUtil.textShader;
        FontData.AtlasData atlasData = this.font.getAtlas();
        shaderUtil.attach();
        shaderUtil.setUniform("Sampler", 0);
        shaderUtil.setUniform("EdgeStrength", 0.5f);
        shaderUtil.setUniform("TextureSize", atlasData.width(), atlasData.height());
        shaderUtil.setUniform("Range", atlasData.range());
        shaderUtil.setUniform("Thickness", 0.0f);
        shaderUtil.setUniform("Outline", 0);
        shaderUtil.setUniform("OutlineThickness", 0.0f);
        shaderUtil.setUniform("OutlineColor", 1.0f, 1.0f, 1.0f, 1.0f);
        shaderUtil.setUniform("color", ColorUtils.rgba(n));
        this.font.bind();
        GlStateManager.enableBlend();
        this.font.applyGlyphs(matrixStack.getLast().getMatrix(), Tessellator.getInstance().getBuffer(), f3, string, 0.0f, f, f2 + this.font.getMetrics().baselineHeight() * f3, 0.0f, 255, 255, 255, 255);
        this.font.unbind();
        shaderUtil.detach();
    }

    public void drawCenteredText(MatrixStack matrixStack, String string, float f, float f2, int n, float f3) {
        this.drawText(matrixStack, string, f - this.getWidth(string, f3) / 2.0f, f2, n, f3);
    }

    public void drawCenteredText(MatrixStack matrixStack, ITextComponent iTextComponent, float f, float f2, float f3) {
        this.drawText(matrixStack, iTextComponent, f - this.getWidth(iTextComponent, f3) / 2.0f, f2, f3, 255);
    }

    public void drawCenteredTextWithOutline(MatrixStack matrixStack, String string, float f, float f2, int n, float f3) {
        this.drawTextWithOutline(matrixStack, string, f - this.getWidth(string, f3) / 2.0f, f2, n, f3, 0.05f);
    }

    public void drawCenteredTextEmpty(MatrixStack matrixStack, String string, float f, float f2, int n, float f3) {
        this.drawEmpty(matrixStack, string, f - this.getWidth(string, f3) / 2.0f, f2, f3, n, 0.0f);
    }

    public void drawCenteredTextEmptyOutline(MatrixStack matrixStack, String string, float f, float f2, int n, float f3) {
        this.drawEmptyWithOutline(matrixStack, string, f - this.getWidth(string, f3) / 2.0f, f2, f3, n, 0.0f);
    }

    public void drawCenteredText(MatrixStack matrixStack, String string, float f, float f2, int n, float f3, float f4) {
        this.drawText(matrixStack, string, f - this.getWidth(string, f3, f4) / 2.0f, f2, n, f3, f4);
    }

    public void drawText(MatrixStack matrixStack, String string, float f, float f2, int n, float f3, float f4) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        ShaderUtil shaderUtil = ShaderUtil.textShader;
        FontData.AtlasData atlasData = this.font.getAtlas();
        shaderUtil.attach();
        shaderUtil.setUniform("Sampler", 0);
        shaderUtil.setUniform("EdgeStrength", 0.5f);
        shaderUtil.setUniform("TextureSize", atlasData.width(), atlasData.height());
        shaderUtil.setUniform("Range", atlasData.range());
        shaderUtil.setUniform("Thickness", f4);
        shaderUtil.setUniform("color", ColorUtils.rgba(n));
        shaderUtil.setUniform("Outline", 0);
        shaderUtil.setUniform("OutlineThickness", 0.0f);
        shaderUtil.setUniform("OutlineColor", 1.0f, 1.0f, 1.0f, 1.0f);
        this.font.bind();
        GlStateManager.enableBlend();
        Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        this.font.applyGlyphs(matrixStack.getLast().getMatrix(), Tessellator.getInstance().getBuffer(), f3, string, f4, f, f2 + this.font.getMetrics().baselineHeight() * f3, 0.0f, 255, 255, 255, 255);
        Tessellator.getInstance().draw();
        this.font.unbind();
        shaderUtil.detach();
    }

    public void drawTextWithOutline(MatrixStack matrixStack, String string, float f, float f2, int n, float f3, float f4) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        ShaderUtil shaderUtil = ShaderUtil.textShader;
        FontData.AtlasData atlasData = this.font.getAtlas();
        shaderUtil.attach();
        shaderUtil.setUniform("Sampler", 0);
        shaderUtil.setUniform("EdgeStrength", 0.5f);
        shaderUtil.setUniform("TextureSize", atlasData.width(), atlasData.height());
        shaderUtil.setUniform("Range", atlasData.range());
        shaderUtil.setUniform("Thickness", f4);
        shaderUtil.setUniform("color", ColorUtils.rgba(n));
        shaderUtil.setUniform("Outline", 1);
        shaderUtil.setUniform("OutlineThickness", 0.2f);
        shaderUtil.setUniform("OutlineColor", 0.0f, 0.0f, 0.0f, 1.0f);
        this.font.bind();
        GlStateManager.enableBlend();
        Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
        this.font.applyGlyphs(matrixStack.getLast().getMatrix(), Tessellator.getInstance().getBuffer(), f3, string, f4, f, f2 + this.font.getMetrics().baselineHeight() * f3, 0.0f, 255, 255, 255, 255);
        Tessellator.getInstance().draw();
        this.font.unbind();
        shaderUtil.detach();
    }

    public void init(float f, float f2) {
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        ShaderUtil shaderUtil = ShaderUtil.textShader;
        FontData.AtlasData atlasData = this.font.getAtlas();
        shaderUtil.attach();
        shaderUtil.setUniform("Sampler", 0);
        shaderUtil.setUniform("EdgeStrength", f2);
        shaderUtil.setUniform("TextureSize", atlasData.width(), atlasData.height());
        shaderUtil.setUniform("Range", atlasData.range());
        shaderUtil.setUniform("Thickness", f);
        shaderUtil.setUniform("Outline", 0);
        shaderUtil.setUniform("OutlineThickness", 0.0f);
        shaderUtil.setUniform("OutlineColor", 1.0f, 1.0f, 1.0f, 1.0f);
        this.font.bind();
        GlStateManager.enableBlend();
        Tessellator.getInstance().getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR_TEX);
    }

    public void drawEmpty(MatrixStack matrixStack, String string, float f, float f2, float f3, int n, float f4) {
        ShaderUtil shaderUtil = ShaderUtil.textShader;
        shaderUtil.setUniform("color", ColorUtils.rgba(n));
        this.font.applyGlyphs(matrixStack.getLast().getMatrix(), Tessellator.getInstance().getBuffer(), f3, string, f4, f, f2 + this.font.getMetrics().baselineHeight() * f3, 0.0f, 255, 255, 255, 255);
    }

    public void drawEmptyWithOutline(MatrixStack matrixStack, String string, float f, float f2, float f3, int n, float f4) {
        ShaderUtil shaderUtil = ShaderUtil.textShader;
        shaderUtil.setUniform("Outline", 1);
        shaderUtil.setUniform("OutlineThickness", 0.2f);
        shaderUtil.setUniform("OutlineColor", 0.0f, 0.0f, 0.0f, 1.0f);
        shaderUtil.setUniform("color", ColorUtils.rgba(n));
        this.font.applyGlyphs(matrixStack.getLast().getMatrix(), Tessellator.getInstance().getBuffer(), f3, string, f4, f, f2 + this.font.getMetrics().baselineHeight() * f3, 0.0f, 255, 255, 255, 255);
    }

    public void end() {
        Tessellator.getInstance().draw();
        this.font.unbind();
        ShaderUtil shaderUtil = ShaderUtil.textShader;
        shaderUtil.detach();
    }

    public float getWidth(String string, float f) {
        return this.font.getWidth(string, f);
    }

    public float getWidth(String string, float f, float f2) {
        return this.font.getWidth(string, f, f2);
    }

    public float getHeight(float f) {
        return f;
    }
}

