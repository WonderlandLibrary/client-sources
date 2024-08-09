/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import javax.annotation.Nullable;
import net.minecraft.client.gui.fonts.EmptyGlyph;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.IGlyph;
import net.minecraft.client.gui.fonts.TexturedGlyph;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.ICharacterConsumer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraft.util.math.vector.TransformationMatrix;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraft.util.text.CharacterManager;
import net.minecraft.util.text.Color;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextProcessing;
import net.optifine.render.GlBlendState;
import net.optifine.util.GlyphAdvanceFixed;
import net.optifine.util.GuiPoint;

public class FontRenderer {
    private static final Vector3f field_238401_c_ = new Vector3f(0.0f, 0.0f, 0.03f);
    public final int FONT_HEIGHT = 9;
    public final Random random = new Random();
    private final Function<ResourceLocation, Font> font;
    private final CharacterManager field_238402_e_;
    private boolean blend = false;
    private GlBlendState oldBlendState = new GlBlendState();
    private IGlyph glyphAdvanceSpace = new GlyphAdvanceFixed(4.0f);

    public FontRenderer(Function<ResourceLocation, Font> function) {
        this.font = function;
        this.field_238402_e_ = new CharacterManager(this::lambda$new$0);
    }

    private Font getFont(ResourceLocation resourceLocation) {
        return this.font.apply(resourceLocation);
    }

    public int drawStringWithShadow(MatrixStack matrixStack, String string, float f, float f2, int n) {
        return this.renderString(string, f, f2, n, matrixStack.getLast().getMatrix(), true, this.getBidiFlag());
    }

    public int func_238406_a_(MatrixStack matrixStack, String string, float f, float f2, int n, boolean bl) {
        RenderSystem.enableAlphaTest();
        return this.renderString(string, f, f2, n, matrixStack.getLast().getMatrix(), true, bl);
    }

    public int drawString(MatrixStack matrixStack, String string, float f, float f2, int n) {
        RenderSystem.enableAlphaTest();
        return this.renderString(string, f, f2, n, matrixStack.getLast().getMatrix(), false, this.getBidiFlag());
    }

    public int func_238407_a_(MatrixStack matrixStack, IReorderingProcessor iReorderingProcessor, float f, float f2, int n) {
        RenderSystem.enableAlphaTest();
        return this.func_238415_a_(iReorderingProcessor, f, f2, n, matrixStack.getLast().getMatrix(), false);
    }

    public int func_243246_a(MatrixStack matrixStack, ITextComponent iTextComponent, float f, float f2, int n) {
        RenderSystem.enableAlphaTest();
        return this.func_238415_a_(iTextComponent.func_241878_f(), f, f2, n, matrixStack.getLast().getMatrix(), false);
    }

    public int func_238422_b_(MatrixStack matrixStack, IReorderingProcessor iReorderingProcessor, float f, float f2, int n) {
        RenderSystem.enableAlphaTest();
        return this.func_238415_a_(iReorderingProcessor, f, f2, n, matrixStack.getLast().getMatrix(), true);
    }

    public int func_243248_b(MatrixStack matrixStack, ITextComponent iTextComponent, float f, float f2, int n) {
        RenderSystem.enableAlphaTest();
        return this.func_238415_a_(iTextComponent.func_241878_f(), f, f2, n, matrixStack.getLast().getMatrix(), true);
    }

    public String bidiReorder(String string) {
        try {
            Bidi bidi = new Bidi(new ArabicShaping(8).shape(string), 127);
            bidi.setReorderingMode(0);
            return bidi.writeReordered(2);
        } catch (ArabicShapingException arabicShapingException) {
            return string;
        }
    }

    private int renderString(String string, float f, float f2, int n, Matrix4f matrix4f, boolean bl, boolean bl2) {
        if (string == null) {
            return 1;
        }
        IRenderTypeBuffer.Impl impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        int n2 = this.func_238411_a_(string, f, f2, n, bl, matrix4f, impl, false, 0, 0xF000F0, bl2);
        impl.finish();
        return n2;
    }

    public void renderStrings(List<String> list, GuiPoint[] guiPointArray, int n, Matrix4f matrix4f, boolean bl, boolean bl2) {
        IRenderTypeBuffer.Impl impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        for (int i = 0; i < list.size(); ++i) {
            GuiPoint guiPoint;
            String string = list.get(i);
            if (string == null || string.isEmpty() || (guiPoint = guiPointArray[i]) == null) continue;
            float f = guiPoint.getX();
            float f2 = guiPoint.getY();
            this.func_238411_a_(string, f, f2, n, bl, matrix4f, impl, false, 0, 0xF000F0, bl2);
        }
        impl.finish();
    }

    private int func_238415_a_(IReorderingProcessor iReorderingProcessor, float f, float f2, int n, Matrix4f matrix4f, boolean bl) {
        IRenderTypeBuffer.Impl impl = IRenderTypeBuffer.getImpl(Tessellator.getInstance().getBuffer());
        int n2 = this.func_238416_a_(iReorderingProcessor, f, f2, n, bl, matrix4f, impl, false, 0, 1);
        impl.finish();
        return n2;
    }

    public int renderString(String string, float f, float f2, int n, boolean bl, Matrix4f matrix4f, IRenderTypeBuffer iRenderTypeBuffer, boolean bl2, int n2, int n3) {
        return this.func_238411_a_(string, f, f2, n, bl, matrix4f, iRenderTypeBuffer, bl2, n2, n3, this.getBidiFlag());
    }

    public int func_238411_a_(String string, float f, float f2, int n, boolean bl, Matrix4f matrix4f, IRenderTypeBuffer iRenderTypeBuffer, boolean bl2, int n2, int n3, boolean bl3) {
        return this.func_238423_b_(string, f, f2, n, bl, matrix4f, iRenderTypeBuffer, bl2, n2, n3, bl3);
    }

    public int func_243247_a(ITextComponent iTextComponent, float f, float f2, int n, boolean bl, Matrix4f matrix4f, IRenderTypeBuffer iRenderTypeBuffer, boolean bl2, int n2, int n3) {
        return this.func_238416_a_(iTextComponent.func_241878_f(), f, f2, n, bl, matrix4f, iRenderTypeBuffer, bl2, n2, n3);
    }

    public int func_238416_a_(IReorderingProcessor iReorderingProcessor, float f, float f2, int n, boolean bl, Matrix4f matrix4f, IRenderTypeBuffer iRenderTypeBuffer, boolean bl2, int n2, int n3) {
        return this.func_238424_b_(iReorderingProcessor, f, f2, n, bl, matrix4f, iRenderTypeBuffer, bl2, n2, n3);
    }

    private static int func_238403_a_(int n) {
        return (n & 0xFC000000) == 0 ? n | 0xFF000000 : n;
    }

    private int func_238423_b_(String string, float f, float f2, int n, boolean bl, Matrix4f matrix4f, IRenderTypeBuffer iRenderTypeBuffer, boolean bl2, int n2, int n3, boolean bl3) {
        if (bl3) {
            string = this.bidiReorder(string);
        }
        n = FontRenderer.func_238403_a_(n);
        Matrix4f matrix4f2 = matrix4f.copy();
        if (bl) {
            this.renderStringAtPos(string, f, f2, n, true, matrix4f, iRenderTypeBuffer, bl2, n2, n3);
            matrix4f2.translate(field_238401_c_);
        }
        f = this.renderStringAtPos(string, f, f2, n, false, matrix4f2, iRenderTypeBuffer, bl2, n2, n3);
        return (int)f + (bl ? 1 : 0);
    }

    private int func_238424_b_(IReorderingProcessor iReorderingProcessor, float f, float f2, int n, boolean bl, Matrix4f matrix4f, IRenderTypeBuffer iRenderTypeBuffer, boolean bl2, int n2, int n3) {
        n = FontRenderer.func_238403_a_(n);
        Matrix4f matrix4f2 = matrix4f.copy();
        if (bl) {
            this.func_238426_c_(iReorderingProcessor, f, f2, n, true, matrix4f, iRenderTypeBuffer, bl2, n2, n3);
            matrix4f2.translate(field_238401_c_);
        }
        f = this.func_238426_c_(iReorderingProcessor, f, f2, n, false, matrix4f2, iRenderTypeBuffer, bl2, n2, n3);
        return (int)f + (bl ? 1 : 0);
    }

    private float renderStringAtPos(String string, float f, float f2, int n, boolean bl, Matrix4f matrix4f, IRenderTypeBuffer iRenderTypeBuffer, boolean bl2, int n2, int n3) {
        CharacterRenderer characterRenderer = new CharacterRenderer(this, iRenderTypeBuffer, f, f2, n, bl, matrix4f, bl2, n3);
        TextProcessing.func_238346_c_(string, Style.EMPTY, characterRenderer);
        return characterRenderer.func_238441_a_(n2, f);
    }

    private float func_238426_c_(IReorderingProcessor iReorderingProcessor, float f, float f2, int n, boolean bl, Matrix4f matrix4f, IRenderTypeBuffer iRenderTypeBuffer, boolean bl2, int n2, int n3) {
        CharacterRenderer characterRenderer = new CharacterRenderer(this, iRenderTypeBuffer, f, f2, n, bl, matrix4f, bl2, n3);
        iReorderingProcessor.accept(characterRenderer);
        return characterRenderer.func_238441_a_(n2, f);
    }

    private void drawGlyph(TexturedGlyph texturedGlyph, boolean bl, boolean bl2, float f, float f2, float f3, Matrix4f matrix4f, IVertexBuilder iVertexBuilder, float f4, float f5, float f6, float f7, int n) {
        texturedGlyph.render(bl2, f2, f3, matrix4f, iVertexBuilder, f4, f5, f6, f7, n);
        if (bl) {
            texturedGlyph.render(bl2, f2 + f, f3, matrix4f, iVertexBuilder, f4, f5, f6, f7, n);
        }
    }

    public int getStringWidth(String string) {
        return MathHelper.ceil(this.field_238402_e_.func_238350_a_(string));
    }

    public int getStringPropertyWidth(ITextProperties iTextProperties) {
        return MathHelper.ceil(this.field_238402_e_.func_238356_a_(iTextProperties));
    }

    public int func_243245_a(IReorderingProcessor iReorderingProcessor) {
        return MathHelper.ceil(this.field_238402_e_.func_243238_a(iReorderingProcessor));
    }

    public String func_238413_a_(String string, int n, boolean bl) {
        return bl ? this.field_238402_e_.func_238364_c_(string, n, Style.EMPTY) : this.field_238402_e_.func_238361_b_(string, n, Style.EMPTY);
    }

    public String func_238412_a_(String string, int n) {
        return this.field_238402_e_.func_238361_b_(string, n, Style.EMPTY);
    }

    public ITextProperties func_238417_a_(ITextProperties iTextProperties, int n) {
        return this.field_238402_e_.func_238358_a_(iTextProperties, n, Style.EMPTY);
    }

    public void func_238418_a_(ITextProperties iTextProperties, int n, int n2, int n3, int n4) {
        Matrix4f matrix4f = TransformationMatrix.identity().getMatrix();
        for (IReorderingProcessor iReorderingProcessor : this.trimStringToWidth(iTextProperties, n3)) {
            this.func_238415_a_(iReorderingProcessor, n, n2, n4, matrix4f, true);
            n2 += 9;
        }
    }

    public int getWordWrappedHeight(String string, int n) {
        return 9 * this.field_238402_e_.func_238365_g_(string, n, Style.EMPTY).size();
    }

    public List<IReorderingProcessor> trimStringToWidth(ITextProperties iTextProperties, int n) {
        return LanguageMap.getInstance().func_244260_a(this.field_238402_e_.func_238362_b_(iTextProperties, n, Style.EMPTY));
    }

    public boolean getBidiFlag() {
        return LanguageMap.getInstance().func_230505_b_();
    }

    public CharacterManager getCharacterManager() {
        return this.field_238402_e_;
    }

    private float lambda$new$0(int n, Style style) {
        return this.getFont(style.getFontId()).func_238557_a_(n).getAdvance(style.getBold());
    }

    class CharacterRenderer
    implements ICharacterConsumer {
        final IRenderTypeBuffer field_238427_a_;
        private final boolean field_238429_c_;
        private final float field_238430_d_;
        private final float field_238431_e_;
        private final float field_238432_f_;
        private final float field_238433_g_;
        private final float field_238434_h_;
        private final Matrix4f field_238435_i_;
        private final boolean field_238436_j_;
        private final int field_238437_k_;
        private float field_238438_l_;
        private float field_238439_m_;
        @Nullable
        private List<TexturedGlyph.Effect> field_238440_n_;
        private Style lastStyle;
        private Font lastStyleFont;
        final FontRenderer this$0;

        private void func_238442_a_(TexturedGlyph.Effect effect) {
            if (this.field_238440_n_ == null) {
                this.field_238440_n_ = Lists.newArrayList();
            }
            this.field_238440_n_.add(effect);
        }

        public CharacterRenderer(FontRenderer fontRenderer, IRenderTypeBuffer iRenderTypeBuffer, float f, float f2, int n, boolean bl, Matrix4f matrix4f, boolean bl2, int n2) {
            this.this$0 = fontRenderer;
            this.field_238427_a_ = iRenderTypeBuffer;
            this.field_238438_l_ = f;
            this.field_238439_m_ = f2;
            this.field_238429_c_ = bl;
            this.field_238430_d_ = bl ? 0.25f : 1.0f;
            this.field_238431_e_ = (float)(n >> 16 & 0xFF) / 255.0f * this.field_238430_d_;
            this.field_238432_f_ = (float)(n >> 8 & 0xFF) / 255.0f * this.field_238430_d_;
            this.field_238433_g_ = (float)(n & 0xFF) / 255.0f * this.field_238430_d_;
            this.field_238434_h_ = (float)(n >> 24 & 0xFF) / 255.0f;
            this.field_238435_i_ = matrix4f.isIdentity() ? TexturedGlyph.MATRIX_IDENTITY : matrix4f;
            this.field_238436_j_ = bl2;
            this.field_238437_k_ = n2;
        }

        @Override
        public boolean accept(int n, Style style, int n2) {
            float f;
            float f2;
            float f3;
            float f4;
            Font font = this.getFont(style);
            IGlyph iGlyph = font.func_238557_a_(n2);
            TexturedGlyph texturedGlyph = style.getObfuscated() && n2 != 32 ? font.obfuscate(iGlyph) : font.func_238559_b_(n2);
            boolean bl = style.getBold();
            float f5 = this.field_238434_h_;
            Color color = style.getColor();
            if (color != null) {
                int n3 = color.getColor();
                f4 = (float)(n3 >> 16 & 0xFF) / 255.0f * this.field_238430_d_;
                f3 = (float)(n3 >> 8 & 0xFF) / 255.0f * this.field_238430_d_;
                f2 = (float)(n3 & 0xFF) / 255.0f * this.field_238430_d_;
            } else {
                f4 = this.field_238431_e_;
                f3 = this.field_238432_f_;
                f2 = this.field_238433_g_;
            }
            if (!(texturedGlyph instanceof EmptyGlyph)) {
                float f6 = bl ? iGlyph.getBoldOffset() : 0.0f;
                f = this.field_238429_c_ ? iGlyph.getShadowOffset() : 0.0f;
                IVertexBuilder iVertexBuilder = this.field_238427_a_.getBuffer(texturedGlyph.getRenderType(this.field_238436_j_));
                this.this$0.drawGlyph(texturedGlyph, bl, style.getItalic(), f6, this.field_238438_l_ + f, this.field_238439_m_ + f, this.field_238435_i_, iVertexBuilder, f4, f3, f2, f5, this.field_238437_k_);
            }
            float f7 = iGlyph.getAdvance(bl);
            float f8 = f = this.field_238429_c_ ? 1.0f : 0.0f;
            if (style.getStrikethrough()) {
                this.func_238442_a_(new TexturedGlyph.Effect(this.field_238438_l_ + f - 1.0f, this.field_238439_m_ + f + 4.5f, this.field_238438_l_ + f + f7, this.field_238439_m_ + f + 4.5f - 1.0f, 0.01f, f4, f3, f2, f5));
            }
            if (style.getUnderlined()) {
                this.func_238442_a_(new TexturedGlyph.Effect(this.field_238438_l_ + f - 1.0f, this.field_238439_m_ + f + 9.0f, this.field_238438_l_ + f + f7, this.field_238439_m_ + f + 9.0f - 1.0f, 0.01f, f4, f3, f2, f5));
            }
            this.field_238438_l_ += f7;
            return false;
        }

        public float func_238441_a_(int n, float f) {
            if (n != 0) {
                float f2 = (float)(n >> 24 & 0xFF) / 255.0f;
                float f3 = (float)(n >> 16 & 0xFF) / 255.0f;
                float f4 = (float)(n >> 8 & 0xFF) / 255.0f;
                float f5 = (float)(n & 0xFF) / 255.0f;
                this.func_238442_a_(new TexturedGlyph.Effect(f - 1.0f, this.field_238439_m_ + 9.0f, this.field_238438_l_ + 1.0f, this.field_238439_m_ - 1.0f, 0.01f, f3, f4, f5, f2));
            }
            if (this.field_238440_n_ != null) {
                TexturedGlyph texturedGlyph = this.this$0.getFont(Style.DEFAULT_FONT).getWhiteGlyph();
                IVertexBuilder iVertexBuilder = this.field_238427_a_.getBuffer(texturedGlyph.getRenderType(this.field_238436_j_));
                for (TexturedGlyph.Effect effect : this.field_238440_n_) {
                    texturedGlyph.renderEffect(effect, this.field_238435_i_, iVertexBuilder, this.field_238437_k_);
                }
            }
            return this.field_238438_l_;
        }

        private Font getFont(Style style) {
            if (style == this.lastStyle) {
                return this.lastStyleFont;
            }
            this.lastStyle = style;
            this.lastStyleFont = this.this$0.getFont(style.getFontId());
            return this.lastStyleFont;
        }
    }
}

