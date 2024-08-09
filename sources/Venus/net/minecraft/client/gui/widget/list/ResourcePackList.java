/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.widget.list;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IBidiRenderer;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.PackLoadingManager;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.resources.PackCompatibility;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.LanguageMap;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class ResourcePackList
extends ExtendedList<ResourcePackEntry> {
    private static final ResourceLocation field_214367_b = new ResourceLocation("textures/gui/resource_packs.png");
    private static final ITextComponent field_214368_c = new TranslationTextComponent("pack.incompatible");
    private static final ITextComponent field_214369_d = new TranslationTextComponent("pack.incompatible.confirm.title");
    private final ITextComponent field_214370_e;

    public ResourcePackList(Minecraft minecraft, int n, int n2, ITextComponent iTextComponent) {
        super(minecraft, n, n2, 32, n2 - 55 + 4, 36);
        this.field_214370_e = iTextComponent;
        this.centerListVertically = false;
        this.setRenderHeader(true, 0);
    }

    @Override
    protected void renderHeader(MatrixStack matrixStack, int n, int n2, Tessellator tessellator) {
        IFormattableTextComponent iFormattableTextComponent = new StringTextComponent("").append(this.field_214370_e).mergeStyle(TextFormatting.UNDERLINE, TextFormatting.BOLD);
        this.minecraft.fontRenderer.func_243248_b(matrixStack, iFormattableTextComponent, n + this.width / 2 - this.minecraft.fontRenderer.getStringPropertyWidth(iFormattableTextComponent) / 2, Math.min(this.y0 + 3, n2), 0xFFFFFF);
    }

    @Override
    public int getRowWidth() {
        return this.width;
    }

    @Override
    protected int getScrollbarPosition() {
        return this.x1 - 6;
    }

    public static class ResourcePackEntry
    extends AbstractList.AbstractListEntry<ResourcePackEntry> {
        private ResourcePackList field_214430_c;
        protected final Minecraft field_214428_a;
        protected final Screen field_214429_b;
        private final PackLoadingManager.IPack field_214431_d;
        private final IReorderingProcessor field_243407_e;
        private final IBidiRenderer field_243408_f;
        private final IReorderingProcessor field_244422_g;
        private final IBidiRenderer field_244423_h;

        public ResourcePackEntry(Minecraft minecraft, ResourcePackList resourcePackList, Screen screen, PackLoadingManager.IPack iPack) {
            this.field_214428_a = minecraft;
            this.field_214429_b = screen;
            this.field_214431_d = iPack;
            this.field_214430_c = resourcePackList;
            this.field_243407_e = ResourcePackEntry.func_244424_a(minecraft, iPack.func_230462_b_());
            this.field_243408_f = ResourcePackEntry.func_244425_b(minecraft, iPack.func_243390_f());
            this.field_244422_g = ResourcePackEntry.func_244424_a(minecraft, field_214368_c);
            this.field_244423_h = ResourcePackEntry.func_244425_b(minecraft, iPack.func_230460_a_().getDescription());
        }

        private static IReorderingProcessor func_244424_a(Minecraft minecraft, ITextComponent iTextComponent) {
            int n = minecraft.fontRenderer.getStringPropertyWidth(iTextComponent);
            if (n > 157) {
                ITextProperties iTextProperties = ITextProperties.func_240655_a_(minecraft.fontRenderer.func_238417_a_(iTextComponent, 157 - minecraft.fontRenderer.getStringWidth("...")), ITextProperties.func_240652_a_("..."));
                return LanguageMap.getInstance().func_241870_a(iTextProperties);
            }
            return iTextComponent.func_241878_f();
        }

        private static IBidiRenderer func_244425_b(Minecraft minecraft, ITextComponent iTextComponent) {
            return IBidiRenderer.func_243259_a(minecraft.fontRenderer, iTextComponent, 157, 2);
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            PackCompatibility packCompatibility = this.field_214431_d.func_230460_a_();
            if (!packCompatibility.isCompatible()) {
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                AbstractGui.fill(matrixStack, n3 - 1, n2 - 1, n3 + n4 - 9, n2 + n5 + 1, -8978432);
            }
            this.field_214428_a.getTextureManager().bindTexture(this.field_214431_d.func_241868_a());
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            AbstractGui.blit(matrixStack, n3, n2, 0.0f, 0.0f, 32, 32, 32, 32);
            IReorderingProcessor iReorderingProcessor = this.field_243407_e;
            IBidiRenderer iBidiRenderer = this.field_243408_f;
            if (this.func_238920_a_() && (this.field_214428_a.gameSettings.touchscreen || bl)) {
                this.field_214428_a.getTextureManager().bindTexture(field_214367_b);
                AbstractGui.fill(matrixStack, n3, n2, n3 + 32, n2 + 32, -1601138544);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                int n8 = n6 - n3;
                int n9 = n7 - n2;
                if (!this.field_214431_d.func_230460_a_().isCompatible()) {
                    iReorderingProcessor = this.field_244422_g;
                    iBidiRenderer = this.field_244423_h;
                }
                if (this.field_214431_d.func_238875_m_()) {
                    if (n8 < 32) {
                        AbstractGui.blit(matrixStack, n3, n2, 0.0f, 32.0f, 32, 32, 256, 256);
                    } else {
                        AbstractGui.blit(matrixStack, n3, n2, 0.0f, 0.0f, 32, 32, 256, 256);
                    }
                } else {
                    if (this.field_214431_d.func_238876_n_()) {
                        if (n8 < 16) {
                            AbstractGui.blit(matrixStack, n3, n2, 32.0f, 32.0f, 32, 32, 256, 256);
                        } else {
                            AbstractGui.blit(matrixStack, n3, n2, 32.0f, 0.0f, 32, 32, 256, 256);
                        }
                    }
                    if (this.field_214431_d.func_230469_o_()) {
                        if (n8 < 32 && n8 > 16 && n9 < 16) {
                            AbstractGui.blit(matrixStack, n3, n2, 96.0f, 32.0f, 32, 32, 256, 256);
                        } else {
                            AbstractGui.blit(matrixStack, n3, n2, 96.0f, 0.0f, 32, 32, 256, 256);
                        }
                    }
                    if (this.field_214431_d.func_230470_p_()) {
                        if (n8 < 32 && n8 > 16 && n9 > 16) {
                            AbstractGui.blit(matrixStack, n3, n2, 64.0f, 32.0f, 32, 32, 256, 256);
                        } else {
                            AbstractGui.blit(matrixStack, n3, n2, 64.0f, 0.0f, 32, 32, 256, 256);
                        }
                    }
                }
            }
            this.field_214428_a.fontRenderer.func_238407_a_(matrixStack, iReorderingProcessor, n3 + 32 + 2, n2 + 1, 0xFFFFFF);
            iBidiRenderer.func_241865_b(matrixStack, n3 + 32 + 2, n2 + 12, 10, 0x808080);
        }

        private boolean func_238920_a_() {
            return !this.field_214431_d.func_230465_f_() || !this.field_214431_d.func_230466_g_();
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            double d3 = d - (double)this.field_214430_c.getRowLeft();
            double d4 = d2 - (double)this.field_214430_c.getRowTop(this.field_214430_c.getEventListeners().indexOf(this));
            if (this.func_238920_a_() && d3 <= 32.0) {
                if (this.field_214431_d.func_238875_m_()) {
                    PackCompatibility packCompatibility = this.field_214431_d.func_230460_a_();
                    if (packCompatibility.isCompatible()) {
                        this.field_214431_d.func_230471_h_();
                    } else {
                        ITextComponent iTextComponent = packCompatibility.getConfirmMessage();
                        this.field_214428_a.displayGuiScreen(new ConfirmScreen(this::lambda$mouseClicked$0, field_214369_d, iTextComponent));
                    }
                    return false;
                }
                if (d3 < 16.0 && this.field_214431_d.func_238876_n_()) {
                    this.field_214431_d.func_230472_i_();
                    return false;
                }
                if (d3 > 16.0 && d4 < 16.0 && this.field_214431_d.func_230469_o_()) {
                    this.field_214431_d.func_230467_j_();
                    return false;
                }
                if (d3 > 16.0 && d4 > 16.0 && this.field_214431_d.func_230470_p_()) {
                    this.field_214431_d.func_230468_k_();
                    return false;
                }
            }
            return true;
        }

        private void lambda$mouseClicked$0(boolean bl) {
            this.field_214428_a.displayGuiScreen(this.field_214429_b);
            if (bl) {
                this.field_214431_d.func_230471_h_();
            }
        }
    }
}

