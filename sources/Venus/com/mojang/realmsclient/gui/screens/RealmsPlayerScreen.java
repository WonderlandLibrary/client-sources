/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.Ops;
import com.mojang.realmsclient.dto.PlayerInfo;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsConfirmScreen;
import com.mojang.realmsclient.gui.screens.RealmsInviteScreen;
import com.mojang.realmsclient.util.RealmsTextureManager;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsPlayerScreen
extends RealmsScreen {
    private static final Logger field_224300_a = LogManager.getLogger();
    private static final ResourceLocation field_237895_b_ = new ResourceLocation("realms", "textures/gui/realms/op_icon.png");
    private static final ResourceLocation field_237896_c_ = new ResourceLocation("realms", "textures/gui/realms/user_icon.png");
    private static final ResourceLocation field_237897_p_ = new ResourceLocation("realms", "textures/gui/realms/cross_player_icon.png");
    private static final ResourceLocation field_237898_q_ = new ResourceLocation("minecraft", "textures/gui/options_background.png");
    private static final ITextComponent field_243138_r = new TranslationTextComponent("mco.configure.world.invites.normal.tooltip");
    private static final ITextComponent field_243139_s = new TranslationTextComponent("mco.configure.world.invites.ops.tooltip");
    private static final ITextComponent field_243140_t = new TranslationTextComponent("mco.configure.world.invites.remove.tooltip");
    private static final ITextComponent field_243141_u = new TranslationTextComponent("mco.configure.world.invited");
    private ITextComponent field_224301_b;
    private final RealmsConfigureWorldScreen field_224302_c;
    private final RealmsServer field_224303_d;
    private InvitedList field_224304_e;
    private int field_224305_f;
    private int field_224306_g;
    private int field_224307_h;
    private Button field_224308_i;
    private Button field_224309_j;
    private int field_224310_k = -1;
    private String field_224311_l;
    private int field_224312_m = -1;
    private boolean field_224313_n;
    private RealmsLabel field_224314_o;
    private GuestAction field_243137_J = GuestAction.NONE;

    public RealmsPlayerScreen(RealmsConfigureWorldScreen realmsConfigureWorldScreen, RealmsServer realmsServer) {
        this.field_224302_c = realmsConfigureWorldScreen;
        this.field_224303_d = realmsServer;
    }

    @Override
    public void init() {
        this.field_224305_f = this.width / 2 - 160;
        this.field_224306_g = 150;
        this.field_224307_h = this.width / 2 + 12;
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224304_e = new InvitedList(this);
        this.field_224304_e.setLeftPos(this.field_224305_f);
        this.addListener(this.field_224304_e);
        for (PlayerInfo playerInfo : this.field_224303_d.field_230589_h_) {
            this.field_224304_e.func_223870_a(playerInfo);
        }
        this.addButton(new Button(this.field_224307_h, RealmsPlayerScreen.func_239562_k_(1), this.field_224306_g + 10, 20, new TranslationTextComponent("mco.configure.world.buttons.invite"), this::lambda$init$0));
        this.field_224308_i = this.addButton(new Button(this.field_224307_h, RealmsPlayerScreen.func_239562_k_(7), this.field_224306_g + 10, 20, new TranslationTextComponent("mco.configure.world.invites.remove.tooltip"), this::lambda$init$1));
        this.field_224309_j = this.addButton(new Button(this.field_224307_h, RealmsPlayerScreen.func_239562_k_(9), this.field_224306_g + 10, 20, new TranslationTextComponent("mco.configure.world.invites.ops.tooltip"), this::lambda$init$2));
        this.addButton(new Button(this.field_224307_h + this.field_224306_g / 2 + 2, RealmsPlayerScreen.func_239562_k_(12), this.field_224306_g / 2 + 10 - 2, 20, DialogTexts.GUI_BACK, this::lambda$init$3));
        this.field_224314_o = this.addListener(new RealmsLabel(new TranslationTextComponent("mco.configure.world.players.title"), this.width / 2, 17, 0xFFFFFF));
        this.func_231411_u_();
        this.func_224280_a();
    }

    private void func_224280_a() {
        this.field_224308_i.visible = this.func_224296_a(this.field_224312_m);
        this.field_224309_j.visible = this.func_224296_a(this.field_224312_m);
    }

    private boolean func_224296_a(int n) {
        return n != -1;
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.func_224298_b();
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void func_224298_b() {
        if (this.field_224313_n) {
            this.minecraft.displayGuiScreen(this.field_224302_c.func_224407_b());
        } else {
            this.minecraft.displayGuiScreen(this.field_224302_c);
        }
    }

    private void func_224289_b(int n) {
        this.func_224280_a();
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        String string = this.field_224303_d.field_230589_h_.get(n).func_230760_b_();
        try {
            this.func_224283_a(realmsClient.func_224906_e(this.field_224303_d.field_230582_a_, string));
        } catch (RealmsServiceException realmsServiceException) {
            field_224300_a.error("Couldn't op the user");
        }
    }

    private void func_224279_c(int n) {
        this.func_224280_a();
        RealmsClient realmsClient = RealmsClient.func_224911_a();
        String string = this.field_224303_d.field_230589_h_.get(n).func_230760_b_();
        try {
            this.func_224283_a(realmsClient.func_224929_f(this.field_224303_d.field_230582_a_, string));
        } catch (RealmsServiceException realmsServiceException) {
            field_224300_a.error("Couldn't deop the user");
        }
    }

    private void func_224283_a(Ops ops) {
        for (PlayerInfo playerInfo : this.field_224303_d.field_230589_h_) {
            playerInfo.func_230759_a_(ops.field_230562_a_.contains(playerInfo.func_230757_a_()));
        }
    }

    private void func_224274_d(int n) {
        this.func_224280_a();
        if (n >= 0 && n < this.field_224303_d.field_230589_h_.size()) {
            PlayerInfo playerInfo = this.field_224303_d.field_230589_h_.get(n);
            this.field_224311_l = playerInfo.func_230760_b_();
            this.field_224310_k = n;
            RealmsConfirmScreen realmsConfirmScreen = new RealmsConfirmScreen(this::lambda$func_224274_d$4, new StringTextComponent("Question"), new TranslationTextComponent("mco.configure.world.uninvite.question").appendString(" '").appendString(playerInfo.func_230757_a_()).appendString("' ?"));
            this.minecraft.displayGuiScreen(realmsConfirmScreen);
        }
    }

    private void func_224292_e(int n) {
        this.field_224303_d.field_230589_h_.remove(n);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.field_224301_b = null;
        this.field_243137_J = GuestAction.NONE;
        this.renderBackground(matrixStack);
        if (this.field_224304_e != null) {
            this.field_224304_e.render(matrixStack, n, n2, f);
        }
        int n3 = RealmsPlayerScreen.func_239562_k_(12) + 20;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferBuilder = tessellator.getBuffer();
        this.minecraft.getTextureManager().bindTexture(field_237898_q_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f2 = 32.0f;
        bufferBuilder.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
        bufferBuilder.pos(0.0, this.height, 0.0).tex(0.0f, (float)(this.height - n3) / 32.0f + 0.0f).color(64, 64, 64, 255).endVertex();
        bufferBuilder.pos(this.width, this.height, 0.0).tex((float)this.width / 32.0f, (float)(this.height - n3) / 32.0f + 0.0f).color(64, 64, 64, 255).endVertex();
        bufferBuilder.pos(this.width, n3, 0.0).tex((float)this.width / 32.0f, 0.0f).color(64, 64, 64, 255).endVertex();
        bufferBuilder.pos(0.0, n3, 0.0).tex(0.0f, 0.0f).color(64, 64, 64, 255).endVertex();
        tessellator.draw();
        this.field_224314_o.func_239560_a_(this, matrixStack);
        if (this.field_224303_d != null && this.field_224303_d.field_230589_h_ != null) {
            this.font.func_243248_b(matrixStack, new StringTextComponent("").append(field_243141_u).appendString(" (").appendString(Integer.toString(this.field_224303_d.field_230589_h_.size())).appendString(")"), this.field_224305_f, RealmsPlayerScreen.func_239562_k_(0), 0xA0A0A0);
        } else {
            this.font.func_243248_b(matrixStack, field_243141_u, this.field_224305_f, RealmsPlayerScreen.func_239562_k_(0), 0xA0A0A0);
        }
        super.render(matrixStack, n, n2, f);
        if (this.field_224303_d != null) {
            this.func_237903_a_(matrixStack, this.field_224301_b, n, n2);
        }
    }

    protected void func_237903_a_(MatrixStack matrixStack, @Nullable ITextComponent iTextComponent, int n, int n2) {
        if (iTextComponent != null) {
            int n3 = n + 12;
            int n4 = n2 - 12;
            int n5 = this.font.getStringPropertyWidth(iTextComponent);
            this.fillGradient(matrixStack, n3 - 3, n4 - 3, n3 + n5 + 3, n4 + 8 + 3, -1073741824, -1073741824);
            this.font.func_243246_a(matrixStack, iTextComponent, n3, n4, 0xFFFFFF);
        }
    }

    private void func_237914_c_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        boolean bl = n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < RealmsPlayerScreen.func_239562_k_(12) + 20 && n4 > RealmsPlayerScreen.func_239562_k_(1);
        this.minecraft.getTextureManager().bindTexture(field_237897_p_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = bl ? 7.0f : 0.0f;
        RealmsPlayerScreen.blit(matrixStack, n, n2, 0.0f, f, 8, 7, 8, 14);
        if (bl) {
            this.field_224301_b = field_243140_t;
            this.field_243137_J = GuestAction.REMOVE;
        }
    }

    private void func_237921_d_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        boolean bl = n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < RealmsPlayerScreen.func_239562_k_(12) + 20 && n4 > RealmsPlayerScreen.func_239562_k_(1);
        this.minecraft.getTextureManager().bindTexture(field_237895_b_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = bl ? 8.0f : 0.0f;
        RealmsPlayerScreen.blit(matrixStack, n, n2, 0.0f, f, 8, 8, 8, 16);
        if (bl) {
            this.field_224301_b = field_243139_s;
            this.field_243137_J = GuestAction.TOGGLE_OP;
        }
    }

    private void func_237925_e_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
        boolean bl = n3 >= n && n3 <= n + 9 && n4 >= n2 && n4 <= n2 + 9 && n4 < RealmsPlayerScreen.func_239562_k_(12) + 20 && n4 > RealmsPlayerScreen.func_239562_k_(1);
        this.minecraft.getTextureManager().bindTexture(field_237896_c_);
        RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
        float f = bl ? 8.0f : 0.0f;
        RealmsPlayerScreen.blit(matrixStack, n, n2, 0.0f, f, 8, 8, 8, 16);
        if (bl) {
            this.field_224301_b = field_243138_r;
            this.field_243137_J = GuestAction.TOGGLE_OP;
        }
    }

    private void lambda$func_224274_d$4(boolean bl) {
        if (bl) {
            RealmsClient realmsClient = RealmsClient.func_224911_a();
            try {
                realmsClient.func_224908_a(this.field_224303_d.field_230582_a_, this.field_224311_l);
            } catch (RealmsServiceException realmsServiceException) {
                field_224300_a.error("Couldn't uninvite user");
            }
            this.func_224292_e(this.field_224310_k);
            this.field_224312_m = -1;
            this.func_224280_a();
        }
        this.field_224313_n = true;
        this.minecraft.displayGuiScreen(this);
    }

    private void lambda$init$3(Button button) {
        this.func_224298_b();
    }

    private void lambda$init$2(Button button) {
        if (this.field_224303_d.field_230589_h_.get(this.field_224312_m).func_230763_c_()) {
            this.func_224279_c(this.field_224312_m);
        } else {
            this.func_224289_b(this.field_224312_m);
        }
    }

    private void lambda$init$1(Button button) {
        this.func_224274_d(this.field_224312_m);
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(new RealmsInviteScreen(this.field_224302_c, this, this.field_224303_d));
    }

    static FontRenderer access$000(RealmsPlayerScreen realmsPlayerScreen) {
        return realmsPlayerScreen.font;
    }

    static int access$100(int n) {
        return RealmsPlayerScreen.func_239562_k_(n);
    }

    static int access$200(int n) {
        return RealmsPlayerScreen.func_239562_k_(n);
    }

    static int access$300(int n) {
        return RealmsPlayerScreen.func_239562_k_(n);
    }

    static enum GuestAction {
        TOGGLE_OP,
        REMOVE,
        NONE;

    }

    class InvitedList
    extends RealmsObjectSelectionList<InvitedEntry> {
        final RealmsPlayerScreen this$0;

        public InvitedList(RealmsPlayerScreen realmsPlayerScreen) {
            this.this$0 = realmsPlayerScreen;
            super(realmsPlayerScreen.field_224306_g + 10, RealmsPlayerScreen.access$100(12) + 20, RealmsPlayerScreen.access$200(1), RealmsPlayerScreen.access$300(12) + 20, 13);
        }

        public void func_223870_a(PlayerInfo playerInfo) {
            RealmsPlayerScreen realmsPlayerScreen = this.this$0;
            Objects.requireNonNull(realmsPlayerScreen);
            this.addEntry(new InvitedEntry(realmsPlayerScreen, playerInfo));
        }

        @Override
        public int getRowWidth() {
            return (int)((double)this.width * 1.0);
        }

        @Override
        public boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            if (n == 0 && d < (double)this.getScrollbarPosition() && d2 >= (double)this.y0 && d2 <= (double)this.y1) {
                int n2 = this.this$0.field_224305_f;
                int n3 = this.this$0.field_224305_f + this.this$0.field_224306_g;
                int n4 = (int)Math.floor(d2 - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount() - 4;
                int n5 = n4 / this.itemHeight;
                if (d >= (double)n2 && d <= (double)n3 && n5 >= 0 && n4 >= 0 && n5 < this.getItemCount()) {
                    this.func_231400_a_(n5);
                    this.func_231401_a_(n4, n5, d, d2, this.width);
                }
                return false;
            }
            return super.mouseClicked(d, d2, n);
        }

        @Override
        public void func_231401_a_(int n, int n2, double d, double d2, int n3) {
            if (n2 >= 0 && n2 <= this.this$0.field_224303_d.field_230589_h_.size() && this.this$0.field_243137_J != GuestAction.NONE) {
                if (this.this$0.field_243137_J == GuestAction.TOGGLE_OP) {
                    if (this.this$0.field_224303_d.field_230589_h_.get(n2).func_230763_c_()) {
                        this.this$0.func_224279_c(n2);
                    } else {
                        this.this$0.func_224289_b(n2);
                    }
                } else if (this.this$0.field_243137_J == GuestAction.REMOVE) {
                    this.this$0.func_224274_d(n2);
                }
            }
        }

        @Override
        public void func_231400_a_(int n) {
            this.func_239561_k_(n);
            if (n != -1) {
                RealmsNarratorHelper.func_239550_a_(I18n.format("narrator.select", this.this$0.field_224303_d.field_230589_h_.get(n).func_230757_a_()));
            }
            this.func_223869_a(n);
        }

        public void func_223869_a(int n) {
            this.this$0.field_224312_m = n;
            this.this$0.func_224280_a();
        }

        @Override
        public void setSelected(@Nullable InvitedEntry invitedEntry) {
            super.setSelected(invitedEntry);
            this.this$0.field_224312_m = this.getEventListeners().indexOf(invitedEntry);
            this.this$0.func_224280_a();
        }

        @Override
        public void renderBackground(MatrixStack matrixStack) {
            this.this$0.renderBackground(matrixStack);
        }

        @Override
        public int getScrollbarPosition() {
            return this.this$0.field_224305_f + this.width - 5;
        }

        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 13;
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((InvitedEntry)abstractListEntry);
        }
    }

    class InvitedEntry
    extends ExtendedList.AbstractListEntry<InvitedEntry> {
        private final PlayerInfo field_237930_b_;
        final RealmsPlayerScreen this$0;

        public InvitedEntry(RealmsPlayerScreen realmsPlayerScreen, PlayerInfo playerInfo) {
            this.this$0 = realmsPlayerScreen;
            this.field_237930_b_ = playerInfo;
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.func_237932_a_(matrixStack, this.field_237930_b_, n3, n2, n6, n7);
        }

        private void func_237932_a_(MatrixStack matrixStack, PlayerInfo playerInfo, int n, int n2, int n3, int n4) {
            int n5 = !playerInfo.func_230765_d_() ? 0xA0A0A0 : (playerInfo.func_230766_e_() ? 0x7FFF7F : 0xFFFFFF);
            RealmsPlayerScreen.access$000(this.this$0).drawString(matrixStack, playerInfo.func_230757_a_(), this.this$0.field_224305_f + 3 + 12, n2 + 1, n5);
            if (playerInfo.func_230763_c_()) {
                this.this$0.func_237921_d_(matrixStack, this.this$0.field_224305_f + this.this$0.field_224306_g - 10, n2 + 1, n3, n4);
            } else {
                this.this$0.func_237925_e_(matrixStack, this.this$0.field_224305_f + this.this$0.field_224306_g - 10, n2 + 1, n3, n4);
            }
            this.this$0.func_237914_c_(matrixStack, this.this$0.field_224305_f + this.this$0.field_224306_g - 22, n2 + 2, n3, n4);
            RealmsTextureManager.func_225205_a(playerInfo.func_230760_b_(), () -> this.lambda$func_237932_a_$0(matrixStack, n2));
        }

        private void lambda$func_237932_a_$0(MatrixStack matrixStack, int n) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            AbstractGui.blit(matrixStack, this.this$0.field_224305_f + 2 + 2, n + 1, 8, 8, 8.0f, 8.0f, 8, 8, 64, 64);
            AbstractGui.blit(matrixStack, this.this$0.field_224305_f + 2 + 2, n + 1, 8, 8, 40.0f, 8.0f, 8, 8, 64, 64);
        }
    }
}

