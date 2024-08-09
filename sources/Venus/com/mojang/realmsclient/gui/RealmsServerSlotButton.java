/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.datafixers.util.Pair;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.dto.RealmsWorldOptions;
import com.mojang.realmsclient.util.RealmsTextureManager;
import java.util.function.Consumer;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.IScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public class RealmsServerSlotButton
extends Button
implements IScreen {
    public static final ResourceLocation field_237712_a_ = new ResourceLocation("realms", "textures/gui/realms/slot_frame.png");
    public static final ResourceLocation field_237713_b_ = new ResourceLocation("realms", "textures/gui/realms/empty_frame.png");
    public static final ResourceLocation field_237714_c_ = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_0.png");
    public static final ResourceLocation field_237715_d_ = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_2.png");
    public static final ResourceLocation field_237716_e_ = new ResourceLocation("minecraft", "textures/gui/title/background/panorama_3.png");
    private static final ITextComponent field_243091_v = new TranslationTextComponent("mco.configure.world.slot.tooltip.active");
    private static final ITextComponent field_243092_w = new TranslationTextComponent("mco.configure.world.slot.tooltip.minigame");
    private static final ITextComponent field_243093_x = new TranslationTextComponent("mco.configure.world.slot.tooltip");
    private final Supplier<RealmsServer> field_223773_a;
    private final Consumer<ITextComponent> field_223774_b;
    private final int field_223776_d;
    private int field_223777_e;
    @Nullable
    private ServerData field_223778_f;

    public RealmsServerSlotButton(int n, int n2, int n3, int n4, Supplier<RealmsServer> supplier, Consumer<ITextComponent> consumer, int n5, Button.IPressable iPressable) {
        super(n, n2, n3, n4, StringTextComponent.EMPTY, iPressable);
        this.field_223773_a = supplier;
        this.field_223776_d = n5;
        this.field_223774_b = consumer;
    }

    @Nullable
    public ServerData func_237717_a_() {
        return this.field_223778_f;
    }

    @Override
    public void tick() {
        ++this.field_223777_e;
        RealmsServer realmsServer = this.field_223773_a.get();
        if (realmsServer != null) {
            boolean bl;
            String string;
            long l;
            String string2;
            boolean bl2;
            boolean bl3;
            RealmsWorldOptions realmsWorldOptions = realmsServer.field_230590_i_.get(this.field_223776_d);
            boolean bl4 = bl3 = this.field_223776_d == 4;
            if (bl3) {
                bl2 = realmsServer.field_230594_m_ == RealmsServer.ServerType.MINIGAME;
                string2 = "Minigame";
                l = realmsServer.field_230597_p_;
                string = realmsServer.field_230598_q_;
                bl = realmsServer.field_230597_p_ == -1;
            } else {
                bl2 = realmsServer.field_230595_n_ == this.field_223776_d && realmsServer.field_230594_m_ != RealmsServer.ServerType.MINIGAME;
                string2 = realmsWorldOptions.func_230787_a_(this.field_223776_d);
                l = realmsWorldOptions.field_230624_k_;
                string = realmsWorldOptions.field_230625_l_;
                bl = realmsWorldOptions.field_230627_n_;
            }
            Action action = RealmsServerSlotButton.func_237720_a_(realmsServer, bl2, bl3);
            Pair<ITextComponent, ITextComponent> pair = this.func_237719_a_(realmsServer, string2, bl, bl3, action);
            this.field_223778_f = new ServerData(bl2, string2, l, string, bl, bl3, action, pair.getFirst());
            this.setMessage(pair.getSecond());
        }
    }

    private static Action func_237720_a_(RealmsServer realmsServer, boolean bl, boolean bl2) {
        if (bl) {
            if (!realmsServer.field_230591_j_ && realmsServer.field_230586_e_ != RealmsServer.Status.UNINITIALIZED) {
                return Action.JOIN;
            }
        } else {
            if (!bl2) {
                return Action.SWITCH_SLOT;
            }
            if (!realmsServer.field_230591_j_) {
                return Action.SWITCH_SLOT;
            }
        }
        return Action.NOTHING;
    }

    private Pair<ITextComponent, ITextComponent> func_237719_a_(RealmsServer realmsServer, String string, boolean bl, boolean bl2, Action action) {
        if (action == Action.NOTHING) {
            return Pair.of(null, new StringTextComponent(string));
        }
        ITextComponent iTextComponent = bl2 ? (bl ? StringTextComponent.EMPTY : new StringTextComponent(" ").appendString(string).appendString(" ").appendString(realmsServer.field_230596_o_)) : new StringTextComponent(" ").appendString(string);
        ITextComponent iTextComponent2 = action == Action.JOIN ? field_243091_v : (bl2 ? field_243092_w : field_243093_x);
        IFormattableTextComponent iFormattableTextComponent = iTextComponent2.deepCopy().append(iTextComponent);
        return Pair.of(iTextComponent2, iFormattableTextComponent);
    }

    @Override
    public void renderButton(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.field_223778_f != null) {
            this.func_237718_a_(matrixStack, this.x, this.y, n, n2, this.field_223778_f.field_225110_a, this.field_223778_f.field_225111_b, this.field_223776_d, this.field_223778_f.field_225112_c, this.field_223778_f.field_225113_d, this.field_223778_f.field_225114_e, this.field_223778_f.field_225115_f, this.field_223778_f.field_225116_g, this.field_223778_f.field_225117_h);
        }
    }

    private void func_237718_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4, boolean bl, String string, int n5, long l, @Nullable String string2, boolean bl2, boolean bl3, Action action, @Nullable ITextComponent iTextComponent) {
        boolean bl4;
        boolean bl5 = this.isHovered();
        if (this.isMouseOver(n3, n4) && iTextComponent != null) {
            this.field_223774_b.accept(iTextComponent);
        }
        Minecraft minecraft = Minecraft.getInstance();
        TextureManager textureManager = minecraft.getTextureManager();
        if (bl3) {
            RealmsTextureManager.func_225202_a(String.valueOf(l), string2);
        } else if (bl2) {
            textureManager.bindTexture(field_237713_b_);
        } else if (string2 != null && l != -1L) {
            RealmsTextureManager.func_225202_a(String.valueOf(l), string2);
        } else if (n5 == 1) {
            textureManager.bindTexture(field_237714_c_);
        } else if (n5 == 2) {
            textureManager.bindTexture(field_237715_d_);
        } else if (n5 == 3) {
            textureManager.bindTexture(field_237716_e_);
        }
        if (bl) {
            float f = 0.85f + 0.15f * MathHelper.cos((float)this.field_223777_e * 0.2f);
            RenderSystem.color4f(f, f, f, 1.0f);
        } else {
            RenderSystem.color4f(0.56f, 0.56f, 0.56f, 1.0f);
        }
        RealmsServerSlotButton.blit(matrixStack, n + 3, n2 + 3, 0.0f, 0.0f, 74, 74, 74, 74);
        textureManager.bindTexture(field_237712_a_);
        boolean bl6 = bl4 = bl5 && action != Action.NOTHING;
        if (bl4) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        } else if (bl) {
            RenderSystem.color4f(0.8f, 0.8f, 0.8f, 1.0f);
        } else {
            RenderSystem.color4f(0.56f, 0.56f, 0.56f, 1.0f);
        }
        RealmsServerSlotButton.blit(matrixStack, n, n2, 0.0f, 0.0f, 80, 80, 80, 80);
        RealmsServerSlotButton.drawCenteredString(matrixStack, minecraft.fontRenderer, string, n + 40, n2 + 66, 0xFFFFFF);
    }

    public static class ServerData {
        private final boolean field_225110_a;
        private final String field_225111_b;
        private final long field_225112_c;
        private final String field_225113_d;
        public final boolean field_225114_e;
        public final boolean field_225115_f;
        public final Action field_225116_g;
        @Nullable
        private final ITextComponent field_225117_h;

        ServerData(boolean bl, String string, long l, @Nullable String string2, boolean bl2, boolean bl3, Action action, @Nullable ITextComponent iTextComponent) {
            this.field_225110_a = bl;
            this.field_225111_b = string;
            this.field_225112_c = l;
            this.field_225113_d = string2;
            this.field_225114_e = bl2;
            this.field_225115_f = bl3;
            this.field_225116_g = action;
            this.field_225117_h = iTextComponent;
        }
    }

    public static enum Action {
        NOTHING,
        SWITCH_SLOT,
        JOIN;

    }
}

