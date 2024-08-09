/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import java.util.Arrays;
import java.util.List;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;

public interface IBidiRenderer {
    public static final IBidiRenderer field_243257_a = new IBidiRenderer(){

        @Override
        public int func_241863_a(MatrixStack matrixStack, int n, int n2) {
            return n2;
        }

        @Override
        public int func_241864_a(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            return n2;
        }

        @Override
        public int func_241865_b(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            return n2;
        }

        @Override
        public int func_241866_c(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            return n2;
        }

        @Override
        public int func_241862_a() {
            return 1;
        }
    };

    public static IBidiRenderer func_243258_a(FontRenderer fontRenderer, ITextProperties iTextProperties, int n) {
        return IBidiRenderer.func_243262_b(fontRenderer, fontRenderer.trimStringToWidth(iTextProperties, n).stream().map(arg_0 -> IBidiRenderer.lambda$func_243258_a$0(fontRenderer, arg_0)).collect(ImmutableList.toImmutableList()));
    }

    public static IBidiRenderer func_243259_a(FontRenderer fontRenderer, ITextProperties iTextProperties, int n, int n2) {
        return IBidiRenderer.func_243262_b(fontRenderer, fontRenderer.trimStringToWidth(iTextProperties, n).stream().limit(n2).map(arg_0 -> IBidiRenderer.lambda$func_243259_a$1(fontRenderer, arg_0)).collect(ImmutableList.toImmutableList()));
    }

    public static IBidiRenderer func_243260_a(FontRenderer fontRenderer, ITextComponent ... iTextComponentArray) {
        return IBidiRenderer.func_243262_b(fontRenderer, Arrays.stream(iTextComponentArray).map(ITextComponent::func_241878_f).map(arg_0 -> IBidiRenderer.lambda$func_243260_a$2(fontRenderer, arg_0)).collect(ImmutableList.toImmutableList()));
    }

    public static IBidiRenderer func_243262_b(FontRenderer fontRenderer, List<Entry> list) {
        return list.isEmpty() ? field_243257_a : new IBidiRenderer(){
            final List val$p_243262_1_;
            final FontRenderer val$p_243262_0_;
            {
                this.val$p_243262_1_ = list;
                this.val$p_243262_0_ = fontRenderer;
            }

            @Override
            public int func_241863_a(MatrixStack matrixStack, int n, int n2) {
                return this.func_241864_a(matrixStack, n, n2, 9, 0xFFFFFF);
            }

            @Override
            public int func_241864_a(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
                int n5 = n2;
                for (Entry entry : this.val$p_243262_1_) {
                    this.val$p_243262_0_.func_238407_a_(matrixStack, entry.field_243267_a, n - entry.field_243268_b / 2, n5, n4);
                    n5 += n3;
                }
                return n5;
            }

            @Override
            public int func_241865_b(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
                int n5 = n2;
                for (Entry entry : this.val$p_243262_1_) {
                    this.val$p_243262_0_.func_238407_a_(matrixStack, entry.field_243267_a, n, n5, n4);
                    n5 += n3;
                }
                return n5;
            }

            @Override
            public int func_241866_c(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
                int n5 = n2;
                for (Entry entry : this.val$p_243262_1_) {
                    this.val$p_243262_0_.func_238422_b_(matrixStack, entry.field_243267_a, n, n5, n4);
                    n5 += n3;
                }
                return n5;
            }

            @Override
            public int func_241862_a() {
                return this.val$p_243262_1_.size();
            }
        };
    }

    public int func_241863_a(MatrixStack var1, int var2, int var3);

    public int func_241864_a(MatrixStack var1, int var2, int var3, int var4, int var5);

    public int func_241865_b(MatrixStack var1, int var2, int var3, int var4, int var5);

    public int func_241866_c(MatrixStack var1, int var2, int var3, int var4, int var5);

    public int func_241862_a();

    private static Entry lambda$func_243260_a$2(FontRenderer fontRenderer, IReorderingProcessor iReorderingProcessor) {
        return new Entry(iReorderingProcessor, fontRenderer.func_243245_a(iReorderingProcessor));
    }

    private static Entry lambda$func_243259_a$1(FontRenderer fontRenderer, IReorderingProcessor iReorderingProcessor) {
        return new Entry(iReorderingProcessor, fontRenderer.func_243245_a(iReorderingProcessor));
    }

    private static Entry lambda$func_243258_a$0(FontRenderer fontRenderer, IReorderingProcessor iReorderingProcessor) {
        return new Entry(iReorderingProcessor, fontRenderer.func_243245_a(iReorderingProcessor));
    }

    public static class Entry {
        private final IReorderingProcessor field_243267_a;
        private final int field_243268_b;

        private Entry(IReorderingProcessor iReorderingProcessor, int n) {
            this.field_243267_a = iReorderingProcessor;
            this.field_243268_b = n;
        }
    }
}

