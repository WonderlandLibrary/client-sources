/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.social;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.IGuiEventListener;
import net.minecraft.client.gui.chat.NarratorChatListener;
import net.minecraft.client.gui.social.FilterManager;
import net.minecraft.client.gui.social.SocialInteractionsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.button.ImageButton;
import net.minecraft.client.gui.widget.list.AbstractOptionList;
import net.minecraft.util.ColorHelper;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;

public class FilterListEntry
extends AbstractOptionList.Entry<FilterListEntry> {
    private final Minecraft field_244620_e;
    private final List<IGuiEventListener> field_244621_f;
    private final UUID field_244622_g;
    private final String field_244623_h;
    private final Supplier<ResourceLocation> field_244742_j;
    private boolean field_244625_j;
    @Nullable
    private Button field_244626_k;
    @Nullable
    private Button field_244627_l;
    private final List<IReorderingProcessor> field_244628_m;
    private final List<IReorderingProcessor> field_244629_n;
    private float field_244630_o;
    private static final ITextComponent field_244743_q = new TranslationTextComponent("gui.socialInteractions.status_hidden").mergeStyle(TextFormatting.ITALIC);
    private static final ITextComponent field_244744_r = new TranslationTextComponent("gui.socialInteractions.status_blocked").mergeStyle(TextFormatting.ITALIC);
    private static final ITextComponent field_244745_s = new TranslationTextComponent("gui.socialInteractions.status_offline").mergeStyle(TextFormatting.ITALIC);
    private static final ITextComponent field_244746_t = new TranslationTextComponent("gui.socialInteractions.status_hidden_offline").mergeStyle(TextFormatting.ITALIC);
    private static final ITextComponent field_244747_u = new TranslationTextComponent("gui.socialInteractions.status_blocked_offline").mergeStyle(TextFormatting.ITALIC);
    public static final int field_244616_a = ColorHelper.PackedColor.packColor(190, 0, 0, 0);
    public static final int field_244617_b = ColorHelper.PackedColor.packColor(255, 74, 74, 74);
    public static final int field_244618_c = ColorHelper.PackedColor.packColor(255, 48, 48, 48);
    public static final int field_244619_d = ColorHelper.PackedColor.packColor(255, 255, 255, 255);
    public static final int field_244741_e = ColorHelper.PackedColor.packColor(140, 255, 255, 255);

    public FilterListEntry(Minecraft minecraft, SocialInteractionsScreen socialInteractionsScreen, UUID uUID, String string, Supplier<ResourceLocation> supplier) {
        this.field_244620_e = minecraft;
        this.field_244622_g = uUID;
        this.field_244623_h = string;
        this.field_244742_j = supplier;
        this.field_244628_m = minecraft.fontRenderer.trimStringToWidth(new TranslationTextComponent("gui.socialInteractions.tooltip.hide", string), 150);
        this.field_244629_n = minecraft.fontRenderer.trimStringToWidth(new TranslationTextComponent("gui.socialInteractions.tooltip.show", string), 150);
        FilterManager filterManager = minecraft.func_244599_aA();
        if (!minecraft.player.getGameProfile().getId().equals(uUID) && !filterManager.func_244757_e(uUID)) {
            this.field_244626_k = new ImageButton(this, 0, 0, 20, 20, 0, 38, 20, SocialInteractionsScreen.field_244666_a, 256, 256, arg_0 -> this.lambda$new$0(filterManager, uUID, string, arg_0), (arg_0, arg_1, arg_2, arg_3) -> this.lambda$new$2(minecraft, socialInteractionsScreen, arg_0, arg_1, arg_2, arg_3), new TranslationTextComponent("gui.socialInteractions.hide")){
                final FilterListEntry this$0;
                {
                    this.this$0 = filterListEntry;
                    super(n, n2, n3, n4, n5, n6, n7, resourceLocation, n8, n9, iPressable, iTooltip, iTextComponent);
                }

                @Override
                protected IFormattableTextComponent getNarrationMessage() {
                    return this.this$0.func_244750_a(super.getNarrationMessage());
                }
            };
            this.field_244627_l = new ImageButton(this, 0, 0, 20, 20, 20, 38, 20, SocialInteractionsScreen.field_244666_a, 256, 256, arg_0 -> this.lambda$new$3(filterManager, uUID, string, arg_0), (arg_0, arg_1, arg_2, arg_3) -> this.lambda$new$5(minecraft, socialInteractionsScreen, arg_0, arg_1, arg_2, arg_3), new TranslationTextComponent("gui.socialInteractions.show")){
                final FilterListEntry this$0;
                {
                    this.this$0 = filterListEntry;
                    super(n, n2, n3, n4, n5, n6, n7, resourceLocation, n8, n9, iPressable, iTooltip, iTextComponent);
                }

                @Override
                protected IFormattableTextComponent getNarrationMessage() {
                    return this.this$0.func_244750_a(super.getNarrationMessage());
                }
            };
            this.field_244627_l.visible = filterManager.func_244648_c(uUID);
            this.field_244626_k.visible = !this.field_244627_l.visible;
            this.field_244621_f = ImmutableList.of(this.field_244626_k, this.field_244627_l);
        } else {
            this.field_244621_f = ImmutableList.of();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
        int n8;
        int n9 = n3 + 4;
        int n10 = n2 + (n5 - 24) / 2;
        int n11 = n9 + 24 + 4;
        ITextComponent iTextComponent = this.func_244752_d();
        if (iTextComponent == StringTextComponent.EMPTY) {
            AbstractGui.fill(matrixStack, n3, n2, n3 + n4, n2 + n5, field_244617_b);
            n8 = n2 + (n5 - 9) / 2;
        } else {
            AbstractGui.fill(matrixStack, n3, n2, n3 + n4, n2 + n5, field_244618_c);
            n8 = n2 + (n5 - 18) / 2;
            this.field_244620_e.fontRenderer.func_243248_b(matrixStack, iTextComponent, n11, n8 + 12, field_244741_e);
        }
        this.field_244620_e.getTextureManager().bindTexture(this.field_244742_j.get());
        AbstractGui.blit(matrixStack, n9, n10, 24, 24, 8.0f, 8.0f, 8, 8, 64, 64);
        RenderSystem.enableBlend();
        AbstractGui.blit(matrixStack, n9, n10, 24, 24, 40.0f, 8.0f, 8, 8, 64, 64);
        RenderSystem.disableBlend();
        this.field_244620_e.fontRenderer.drawString(matrixStack, this.field_244623_h, n11, n8, field_244619_d);
        if (this.field_244625_j) {
            AbstractGui.fill(matrixStack, n9, n10, n9 + 24, n10 + 24, field_244616_a);
        }
        if (this.field_244626_k != null && this.field_244627_l != null) {
            float f2 = this.field_244630_o;
            this.field_244626_k.x = n3 + (n4 - this.field_244626_k.getWidth() - 4);
            this.field_244626_k.y = n2 + (n5 - this.field_244626_k.getHeightRealms()) / 2;
            this.field_244626_k.render(matrixStack, n6, n7, f);
            this.field_244627_l.x = n3 + (n4 - this.field_244627_l.getWidth() - 4);
            this.field_244627_l.y = n2 + (n5 - this.field_244627_l.getHeightRealms()) / 2;
            this.field_244627_l.render(matrixStack, n6, n7, f);
            if (f2 == this.field_244630_o) {
                this.field_244630_o = 0.0f;
            }
        }
    }

    @Override
    public List<? extends IGuiEventListener> getEventListeners() {
        return this.field_244621_f;
    }

    public String func_244636_b() {
        return this.field_244623_h;
    }

    public UUID func_244640_c() {
        return this.field_244622_g;
    }

    public void func_244641_c(boolean bl) {
        this.field_244625_j = bl;
    }

    private void func_244635_a(boolean bl, ITextComponent iTextComponent) {
        this.field_244627_l.visible = bl;
        this.field_244626_k.visible = !bl;
        this.field_244620_e.ingameGUI.getChatGUI().printChatMessage(iTextComponent);
        NarratorChatListener.INSTANCE.say(iTextComponent.getString());
    }

    private IFormattableTextComponent func_244750_a(IFormattableTextComponent iFormattableTextComponent) {
        ITextComponent iTextComponent = this.func_244752_d();
        return iTextComponent == StringTextComponent.EMPTY ? new StringTextComponent(this.field_244623_h).appendString(", ").append(iFormattableTextComponent) : new StringTextComponent(this.field_244623_h).appendString(", ").append(iTextComponent).appendString(", ").append(iFormattableTextComponent);
    }

    private ITextComponent func_244752_d() {
        boolean bl = this.field_244620_e.func_244599_aA().func_244648_c(this.field_244622_g);
        boolean bl2 = this.field_244620_e.func_244599_aA().func_244757_e(this.field_244622_g);
        if (bl2 && this.field_244625_j) {
            return field_244747_u;
        }
        if (bl && this.field_244625_j) {
            return field_244746_t;
        }
        if (bl2) {
            return field_244744_r;
        }
        if (bl) {
            return field_244743_q;
        }
        return this.field_244625_j ? field_244745_s : StringTextComponent.EMPTY;
    }

    private static void func_244634_a(SocialInteractionsScreen socialInteractionsScreen, MatrixStack matrixStack, List<IReorderingProcessor> list, int n, int n2) {
        socialInteractionsScreen.renderTooltip(matrixStack, list, n, n2);
        socialInteractionsScreen.func_244684_a(null);
    }

    private void lambda$new$5(Minecraft minecraft, SocialInteractionsScreen socialInteractionsScreen, Button button, MatrixStack matrixStack, int n, int n2) {
        this.field_244630_o += minecraft.getTickLength();
        if (this.field_244630_o >= 10.0f) {
            socialInteractionsScreen.func_244684_a(() -> this.lambda$new$4(socialInteractionsScreen, matrixStack, n, n2));
        }
    }

    private void lambda$new$4(SocialInteractionsScreen socialInteractionsScreen, MatrixStack matrixStack, int n, int n2) {
        FilterListEntry.func_244634_a(socialInteractionsScreen, matrixStack, this.field_244629_n, n, n2);
    }

    private void lambda$new$3(FilterManager filterManager, UUID uUID, String string, Button button) {
        filterManager.func_244647_b(uUID);
        this.func_244635_a(false, new TranslationTextComponent("gui.socialInteractions.shown_in_chat", string));
    }

    private void lambda$new$2(Minecraft minecraft, SocialInteractionsScreen socialInteractionsScreen, Button button, MatrixStack matrixStack, int n, int n2) {
        this.field_244630_o += minecraft.getTickLength();
        if (this.field_244630_o >= 10.0f) {
            socialInteractionsScreen.func_244684_a(() -> this.lambda$new$1(socialInteractionsScreen, matrixStack, n, n2));
        }
    }

    private void lambda$new$1(SocialInteractionsScreen socialInteractionsScreen, MatrixStack matrixStack, int n, int n2) {
        FilterListEntry.func_244634_a(socialInteractionsScreen, matrixStack, this.field_244628_m, n, n2);
    }

    private void lambda$new$0(FilterManager filterManager, UUID uUID, String string, Button button) {
        filterManager.func_244646_a(uUID);
        this.func_244635_a(true, new TranslationTextComponent("gui.socialInteractions.hidden_in_chat", string));
    }
}

