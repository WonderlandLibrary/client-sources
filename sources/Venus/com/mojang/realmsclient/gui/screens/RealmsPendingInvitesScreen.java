/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.RealmsMainScreen;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.PendingInvite;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.ListButton;
import com.mojang.realmsclient.util.RealmsTextureManager;
import com.mojang.realmsclient.util.RealmsUtil;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsPendingInvitesScreen
extends RealmsScreen {
    private static final Logger field_224333_a = LogManager.getLogger();
    private static final ResourceLocation field_237863_b_ = new ResourceLocation("realms", "textures/gui/realms/accept_icon.png");
    private static final ResourceLocation field_237864_c_ = new ResourceLocation("realms", "textures/gui/realms/reject_icon.png");
    private static final ITextComponent field_243124_p = new TranslationTextComponent("mco.invites.nopending");
    private static final ITextComponent field_243125_q = new TranslationTextComponent("mco.invites.button.accept");
    private static final ITextComponent field_243126_r = new TranslationTextComponent("mco.invites.button.reject");
    private final Screen field_224334_b;
    @Nullable
    private ITextComponent field_224335_c;
    private boolean field_224336_d;
    private InvitationList field_224337_e;
    private RealmsLabel field_224338_f;
    private int field_224339_g = -1;
    private Button field_224340_h;
    private Button field_224341_i;

    public RealmsPendingInvitesScreen(Screen screen) {
        this.field_224334_b = screen;
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224337_e = new InvitationList(this);
        new Thread(this, "Realms-pending-invitations-fetcher"){
            final RealmsPendingInvitesScreen this$0;
            {
                this.this$0 = realmsPendingInvitesScreen;
                super(string);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void run() {
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                try {
                    List<PendingInvite> list = realmsClient.func_224919_k().field_230569_a_;
                    List list2 = list.stream().map(this::lambda$run$0).collect(Collectors.toList());
                    RealmsPendingInvitesScreen.access$000(this.this$0).execute(() -> this.lambda$run$1(list2));
                } catch (RealmsServiceException realmsServiceException) {
                    field_224333_a.error("Couldn't list invites");
                } finally {
                    this.this$0.field_224336_d = true;
                }
            }

            private void lambda$run$1(List list) {
                this.this$0.field_224337_e.replaceEntries(list);
            }

            private InvitationEntry lambda$run$0(PendingInvite pendingInvite) {
                RealmsPendingInvitesScreen realmsPendingInvitesScreen = this.this$0;
                Objects.requireNonNull(realmsPendingInvitesScreen);
                return new InvitationEntry(realmsPendingInvitesScreen, pendingInvite);
            }
        }.start();
        this.addListener(this.field_224337_e);
        this.field_224340_h = this.addButton(new Button(this.width / 2 - 174, this.height - 32, 100, 20, new TranslationTextComponent("mco.invites.button.accept"), this::lambda$init$0));
        this.addButton(new Button(this.width / 2 - 50, this.height - 32, 100, 20, DialogTexts.GUI_DONE, this::lambda$init$1));
        this.field_224341_i = this.addButton(new Button(this.width / 2 + 74, this.height - 32, 100, 20, new TranslationTextComponent("mco.invites.button.reject"), this::lambda$init$2));
        this.field_224338_f = new RealmsLabel(new TranslationTextComponent("mco.invites.title"), this.width / 2, 12, 0xFFFFFF);
        this.addListener(this.field_224338_f);
        this.func_231411_u_();
        this.func_224331_b();
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(new RealmsMainScreen(this.field_224334_b));
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void func_224318_a(int n) {
        this.field_224337_e.func_223872_a(n);
    }

    private void func_224321_b(int n) {
        if (n < this.field_224337_e.getItemCount()) {
            new Thread(this, "Realms-reject-invitation", n){
                final int val$p_224321_1_;
                final RealmsPendingInvitesScreen this$0;
                {
                    this.this$0 = realmsPendingInvitesScreen;
                    this.val$p_224321_1_ = n;
                    super(string);
                }

                @Override
                public void run() {
                    try {
                        RealmsClient realmsClient = RealmsClient.func_224911_a();
                        realmsClient.func_224913_b(((InvitationEntry)this.this$0.field_224337_e.getEventListeners().get((int)this.val$p_224321_1_)).field_223750_a.field_230563_a_);
                        RealmsPendingInvitesScreen.access$100(this.this$0).execute(() -> this.lambda$run$0(this.val$p_224321_1_));
                    } catch (RealmsServiceException realmsServiceException) {
                        field_224333_a.error("Couldn't reject invite");
                    }
                }

                private void lambda$run$0(int n) {
                    this.this$0.func_224318_a(n);
                }
            }.start();
        }
    }

    private void func_224329_c(int n) {
        if (n < this.field_224337_e.getItemCount()) {
            new Thread(this, "Realms-accept-invitation", n){
                final int val$p_224329_1_;
                final RealmsPendingInvitesScreen this$0;
                {
                    this.this$0 = realmsPendingInvitesScreen;
                    this.val$p_224329_1_ = n;
                    super(string);
                }

                @Override
                public void run() {
                    try {
                        RealmsClient realmsClient = RealmsClient.func_224911_a();
                        realmsClient.func_224901_a(((InvitationEntry)this.this$0.field_224337_e.getEventListeners().get((int)this.val$p_224329_1_)).field_223750_a.field_230563_a_);
                        RealmsPendingInvitesScreen.access$200(this.this$0).execute(() -> this.lambda$run$0(this.val$p_224329_1_));
                    } catch (RealmsServiceException realmsServiceException) {
                        field_224333_a.error("Couldn't accept invite");
                    }
                }

                private void lambda$run$0(int n) {
                    this.this$0.func_224318_a(n);
                }
            }.start();
        }
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.field_224335_c = null;
        this.renderBackground(matrixStack);
        this.field_224337_e.render(matrixStack, n, n2, f);
        this.field_224338_f.func_239560_a_(this, matrixStack);
        if (this.field_224335_c != null) {
            this.func_237866_a_(matrixStack, this.field_224335_c, n, n2);
        }
        if (this.field_224337_e.getItemCount() == 0 && this.field_224336_d) {
            RealmsPendingInvitesScreen.drawCenteredString(matrixStack, this.font, field_243124_p, this.width / 2, this.height / 2 - 20, 0xFFFFFF);
        }
        super.render(matrixStack, n, n2, f);
    }

    protected void func_237866_a_(MatrixStack matrixStack, @Nullable ITextComponent iTextComponent, int n, int n2) {
        if (iTextComponent != null) {
            int n3 = n + 12;
            int n4 = n2 - 12;
            int n5 = this.font.getStringPropertyWidth(iTextComponent);
            this.fillGradient(matrixStack, n3 - 3, n4 - 3, n3 + n5 + 3, n4 + 8 + 3, -1073741824, -1073741824);
            this.font.func_243246_a(matrixStack, iTextComponent, n3, n4, 0xFFFFFF);
        }
    }

    private void func_224331_b() {
        this.field_224340_h.visible = this.func_224316_d(this.field_224339_g);
        this.field_224341_i.visible = this.func_224316_d(this.field_224339_g);
    }

    private boolean func_224316_d(int n) {
        return n != -1;
    }

    private void lambda$init$2(Button button) {
        this.func_224321_b(this.field_224339_g);
        this.field_224339_g = -1;
        this.func_224331_b();
    }

    private void lambda$init$1(Button button) {
        this.minecraft.displayGuiScreen(new RealmsMainScreen(this.field_224334_b));
    }

    private void lambda$init$0(Button button) {
        this.func_224329_c(this.field_224339_g);
        this.field_224339_g = -1;
        this.func_224331_b();
    }

    static Minecraft access$000(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.minecraft;
    }

    static Minecraft access$100(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.minecraft;
    }

    static Minecraft access$200(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.minecraft;
    }

    static FontRenderer access$300(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.font;
    }

    static FontRenderer access$400(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.font;
    }

    static FontRenderer access$500(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.font;
    }

    static Minecraft access$600(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.minecraft;
    }

    static Minecraft access$700(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
        return realmsPendingInvitesScreen.minecraft;
    }

    class InvitationList
    extends RealmsObjectSelectionList<InvitationEntry> {
        final RealmsPendingInvitesScreen this$0;

        public InvitationList(RealmsPendingInvitesScreen realmsPendingInvitesScreen) {
            this.this$0 = realmsPendingInvitesScreen;
            super(realmsPendingInvitesScreen.width, realmsPendingInvitesScreen.height, 32, realmsPendingInvitesScreen.height - 40, 36);
        }

        public void func_223872_a(int n) {
            this.remove(n);
        }

        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 36;
        }

        @Override
        public int getRowWidth() {
            return 1;
        }

        @Override
        public boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        @Override
        public void renderBackground(MatrixStack matrixStack) {
            this.this$0.renderBackground(matrixStack);
        }

        @Override
        public void func_231400_a_(int n) {
            this.func_239561_k_(n);
            if (n != -1) {
                List list = this.this$0.field_224337_e.getEventListeners();
                PendingInvite pendingInvite = ((InvitationEntry)list.get((int)n)).field_223750_a;
                String string = I18n.format("narrator.select.list.position", n + 1, list.size());
                String string2 = RealmsNarratorHelper.func_239552_b_(Arrays.asList(pendingInvite.field_230564_b_, pendingInvite.field_230565_c_, RealmsUtil.func_238105_a_(pendingInvite.field_230567_e_), string));
                RealmsNarratorHelper.func_239550_a_(I18n.format("narrator.select", string2));
            }
            this.func_223873_b(n);
        }

        public void func_223873_b(int n) {
            this.this$0.field_224339_g = n;
            this.this$0.func_224331_b();
        }

        @Override
        public void setSelected(@Nullable InvitationEntry invitationEntry) {
            super.setSelected(invitationEntry);
            this.this$0.field_224339_g = this.getEventListeners().indexOf(invitationEntry);
            this.this$0.func_224331_b();
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((InvitationEntry)abstractListEntry);
        }
    }

    class InvitationEntry
    extends ExtendedList.AbstractListEntry<InvitationEntry> {
        private final PendingInvite field_223750_a;
        private final List<ListButton> field_223752_c;
        final RealmsPendingInvitesScreen this$0;

        InvitationEntry(RealmsPendingInvitesScreen realmsPendingInvitesScreen, PendingInvite pendingInvite) {
            this.this$0 = realmsPendingInvitesScreen;
            this.field_223750_a = pendingInvite;
            this.field_223752_c = Arrays.asList(new AcceptButton(this), new RejectButton(this));
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.func_237893_a_(matrixStack, this.field_223750_a, n3, n2, n6, n7);
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            ListButton.func_237728_a_(this.this$0.field_224337_e, this, this.field_223752_c, n, d, d2);
            return false;
        }

        private void func_237893_a_(MatrixStack matrixStack, PendingInvite pendingInvite, int n, int n2, int n3, int n4) {
            RealmsPendingInvitesScreen.access$300(this.this$0).drawString(matrixStack, pendingInvite.field_230564_b_, n + 38, n2 + 1, 0xFFFFFF);
            RealmsPendingInvitesScreen.access$400(this.this$0).drawString(matrixStack, pendingInvite.field_230565_c_, n + 38, n2 + 12, 0x6C6C6C);
            RealmsPendingInvitesScreen.access$500(this.this$0).drawString(matrixStack, RealmsUtil.func_238105_a_(pendingInvite.field_230567_e_), n + 38, n2 + 24, 0x6C6C6C);
            ListButton.func_237727_a_(matrixStack, this.field_223752_c, this.this$0.field_224337_e, n, n2, n3, n4);
            RealmsTextureManager.func_225205_a(pendingInvite.field_230566_d_, () -> InvitationEntry.lambda$func_237893_a_$0(matrixStack, n, n2));
        }

        private static void lambda$func_237893_a_$0(MatrixStack matrixStack, int n, int n2) {
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            AbstractGui.blit(matrixStack, n, n2, 32, 32, 8.0f, 8.0f, 8, 8, 64, 64);
            AbstractGui.blit(matrixStack, n, n2, 32, 32, 40.0f, 8.0f, 8, 8, 64, 64);
        }

        class AcceptButton
        extends ListButton {
            final InvitationEntry this$1;

            AcceptButton(InvitationEntry invitationEntry) {
                this.this$1 = invitationEntry;
                super(15, 15, 215, 5);
            }

            @Override
            protected void func_230435_a_(MatrixStack matrixStack, int n, int n2, boolean bl) {
                RealmsPendingInvitesScreen.access$600(this.this$1.this$0).getTextureManager().bindTexture(field_237863_b_);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                float f = bl ? 19.0f : 0.0f;
                AbstractGui.blit(matrixStack, n, n2, f, 0.0f, 18, 18, 37, 18);
                if (bl) {
                    this.this$1.this$0.field_224335_c = field_243125_q;
                }
            }

            @Override
            public void func_225121_a(int n) {
                this.this$1.this$0.func_224329_c(n);
            }
        }

        class RejectButton
        extends ListButton {
            final InvitationEntry this$1;

            RejectButton(InvitationEntry invitationEntry) {
                this.this$1 = invitationEntry;
                super(15, 15, 235, 5);
            }

            @Override
            protected void func_230435_a_(MatrixStack matrixStack, int n, int n2, boolean bl) {
                RealmsPendingInvitesScreen.access$700(this.this$1.this$0).getTextureManager().bindTexture(field_237864_c_);
                RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
                float f = bl ? 19.0f : 0.0f;
                AbstractGui.blit(matrixStack, n, n2, f, 0.0f, 18, 18, 37, 18);
                if (bl) {
                    this.this$1.this$0.field_224335_c = field_243126_r;
                }
            }

            @Override
            public void func_225121_a(int n) {
                this.this$1.this$0.func_224321_b(n);
            }
        }
    }
}

