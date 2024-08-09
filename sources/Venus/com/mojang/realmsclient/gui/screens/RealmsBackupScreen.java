/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.realmsclient.client.RealmsClient;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.dto.RealmsServer;
import com.mojang.realmsclient.exception.RealmsServiceException;
import com.mojang.realmsclient.gui.screens.RealmsBackupInfoScreen;
import com.mojang.realmsclient.gui.screens.RealmsConfigureWorldScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongConfirmationScreen;
import com.mojang.realmsclient.gui.screens.RealmsLongRunningMcoTaskScreen;
import com.mojang.realmsclient.util.RealmsUtil;
import java.text.DateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import javax.annotation.Nullable;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.AbstractList;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.realms.RealmsNarratorHelper;
import net.minecraft.realms.RealmsObjectSelectionList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.realms.action.PrepareDownloadRealmsAction;
import net.minecraft.realms.action.RestoringBackupRealmsAction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RealmsBackupScreen
extends RealmsScreen {
    private static final Logger field_224114_a = LogManager.getLogger();
    private static final ResourceLocation field_237740_b_ = new ResourceLocation("realms", "textures/gui/realms/plus_icon.png");
    private static final ResourceLocation field_237741_c_ = new ResourceLocation("realms", "textures/gui/realms/restore_icon.png");
    private static final ITextComponent field_243094_p = new TranslationTextComponent("mco.backup.button.restore");
    private static final ITextComponent field_243095_q = new TranslationTextComponent("mco.backup.changes.tooltip");
    private static final ITextComponent field_243096_r = new TranslationTextComponent("mco.configure.world.backup");
    private static final ITextComponent field_243097_s = new TranslationTextComponent("mco.backup.nobackups");
    private static int field_224115_b = -1;
    private final RealmsConfigureWorldScreen field_224116_c;
    private List<Backup> field_224117_d = Collections.emptyList();
    @Nullable
    private ITextComponent field_224118_e;
    private BackupObjectSelectionList field_224119_f;
    private int field_224120_g = -1;
    private final int field_224121_h;
    private Button field_224122_i;
    private Button field_224123_j;
    private Button field_224124_k;
    private Boolean field_224125_l = false;
    private final RealmsServer field_224126_m;
    private RealmsLabel field_224127_n;

    public RealmsBackupScreen(RealmsConfigureWorldScreen realmsConfigureWorldScreen, RealmsServer realmsServer, int n) {
        this.field_224116_c = realmsConfigureWorldScreen;
        this.field_224126_m = realmsServer;
        this.field_224121_h = n;
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.field_224119_f = new BackupObjectSelectionList(this);
        if (field_224115_b != -1) {
            this.field_224119_f.setScrollAmount(field_224115_b);
        }
        new Thread(this, "Realms-fetch-backups"){
            final RealmsBackupScreen this$0;
            {
                this.this$0 = realmsBackupScreen;
                super(string);
            }

            @Override
            public void run() {
                RealmsClient realmsClient = RealmsClient.func_224911_a();
                try {
                    List<Backup> list = realmsClient.func_224923_d((long)this.this$0.field_224126_m.field_230582_a_).field_230560_a_;
                    RealmsBackupScreen.access$000(this.this$0).execute(() -> this.lambda$run$0(list));
                } catch (RealmsServiceException realmsServiceException) {
                    field_224114_a.error("Couldn't request backups", (Throwable)realmsServiceException);
                }
            }

            private void lambda$run$0(List list) {
                this.this$0.field_224117_d = list;
                this.this$0.field_224125_l = this.this$0.field_224117_d.isEmpty();
                this.this$0.field_224119_f.func_231409_q_();
                for (Backup backup : this.this$0.field_224117_d) {
                    this.this$0.field_224119_f.func_223867_a(backup);
                }
                this.this$0.func_224112_b();
            }
        }.start();
        this.field_224122_i = this.addButton(new Button(this.width - 135, RealmsBackupScreen.func_239562_k_(1), 120, 20, new TranslationTextComponent("mco.backup.button.download"), this::lambda$init$0));
        this.field_224123_j = this.addButton(new Button(this.width - 135, RealmsBackupScreen.func_239562_k_(3), 120, 20, new TranslationTextComponent("mco.backup.button.restore"), this::lambda$init$1));
        this.field_224124_k = this.addButton(new Button(this.width - 135, RealmsBackupScreen.func_239562_k_(5), 120, 20, new TranslationTextComponent("mco.backup.changes.tooltip"), this::lambda$init$2));
        this.addButton(new Button(this.width - 100, this.height - 35, 85, 20, DialogTexts.GUI_BACK, this::lambda$init$3));
        this.addListener(this.field_224119_f);
        this.field_224127_n = this.addListener(new RealmsLabel(new TranslationTextComponent("mco.configure.world.backup"), this.width / 2, 12, 0xFFFFFF));
        this.setListenerDefault(this.field_224119_f);
        this.func_224113_d();
        this.func_231411_u_();
    }

    private void func_224112_b() {
        if (this.field_224117_d.size() > 1) {
            for (int i = 0; i < this.field_224117_d.size() - 1; ++i) {
                Backup backup = this.field_224117_d.get(i);
                Backup backup2 = this.field_224117_d.get(i + 1);
                if (backup.field_230556_d_.isEmpty() || backup2.field_230556_d_.isEmpty()) continue;
                for (String string : backup.field_230556_d_.keySet()) {
                    if (!string.contains("Uploaded") && backup2.field_230556_d_.containsKey(string)) {
                        if (backup.field_230556_d_.get(string).equals(backup2.field_230556_d_.get(string))) continue;
                        this.func_224103_a(backup, string);
                        continue;
                    }
                    this.func_224103_a(backup, string);
                }
            }
        }
    }

    private void func_224103_a(Backup backup, String string) {
        if (string.contains("Uploaded")) {
            String string2 = DateFormat.getDateTimeInstance(3, 3).format(backup.field_230554_b_);
            backup.field_230557_e_.put(string, string2);
            backup.func_230752_a_(false);
        } else {
            backup.field_230557_e_.put(string, backup.field_230556_d_.get(string));
        }
    }

    private void func_224113_d() {
        this.field_224123_j.visible = this.func_224111_f();
        this.field_224124_k.visible = this.func_224096_e();
    }

    private boolean func_224096_e() {
        if (this.field_224120_g == -1) {
            return true;
        }
        return !this.field_224117_d.get((int)this.field_224120_g).field_230557_e_.isEmpty();
    }

    private boolean func_224111_f() {
        if (this.field_224120_g == -1) {
            return true;
        }
        return !this.field_224126_m.field_230591_j_;
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224116_c);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    private void func_224104_b(int n) {
        if (n >= 0 && n < this.field_224117_d.size() && !this.field_224126_m.field_230591_j_) {
            this.field_224120_g = n;
            Date date = this.field_224117_d.get((int)n).field_230554_b_;
            String string = DateFormat.getDateTimeInstance(3, 3).format(date);
            String string2 = RealmsUtil.func_238105_a_(date);
            TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.configure.world.restore.question.line1", string, string2);
            TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.configure.world.restore.question.line2");
            this.minecraft.displayGuiScreen(new RealmsLongConfirmationScreen(this::lambda$func_224104_b$4, RealmsLongConfirmationScreen.Type.Warning, translationTextComponent, translationTextComponent2, true));
        }
    }

    private void func_224088_g() {
        TranslationTextComponent translationTextComponent = new TranslationTextComponent("mco.configure.world.restore.download.question.line1");
        TranslationTextComponent translationTextComponent2 = new TranslationTextComponent("mco.configure.world.restore.download.question.line2");
        this.minecraft.displayGuiScreen(new RealmsLongConfirmationScreen(this::lambda$func_224088_g$5, RealmsLongConfirmationScreen.Type.Info, translationTextComponent, translationTextComponent2, true));
    }

    private void func_224100_h() {
        this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224116_c.func_224407_b(), new PrepareDownloadRealmsAction(this.field_224126_m.field_230582_a_, this.field_224121_h, this.field_224126_m.field_230584_c_ + " (" + this.field_224126_m.field_230590_i_.get(this.field_224126_m.field_230595_n_).func_230787_a_(this.field_224126_m.field_230595_n_) + ")", this)));
    }

    private void func_224097_i() {
        Backup backup = this.field_224117_d.get(this.field_224120_g);
        this.field_224120_g = -1;
        this.minecraft.displayGuiScreen(new RealmsLongRunningMcoTaskScreen(this.field_224116_c.func_224407_b(), new RestoringBackupRealmsAction(backup, this.field_224126_m.field_230582_a_, this.field_224116_c)));
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.field_224118_e = null;
        this.renderBackground(matrixStack);
        this.field_224119_f.render(matrixStack, n, n2, f);
        this.field_224127_n.func_239560_a_(this, matrixStack);
        this.font.func_243248_b(matrixStack, field_243096_r, (this.width - 150) / 2 - 90, 20.0f, 0xA0A0A0);
        if (this.field_224125_l.booleanValue()) {
            this.font.func_243248_b(matrixStack, field_243097_s, 20.0f, this.height / 2 - 10, 0xFFFFFF);
        }
        this.field_224122_i.active = this.field_224125_l == false;
        super.render(matrixStack, n, n2, f);
        if (this.field_224118_e != null) {
            this.func_237744_a_(matrixStack, this.field_224118_e, n, n2);
        }
    }

    protected void func_237744_a_(MatrixStack matrixStack, @Nullable ITextComponent iTextComponent, int n, int n2) {
        if (iTextComponent != null) {
            int n3 = n + 12;
            int n4 = n2 - 12;
            int n5 = this.font.getStringPropertyWidth(iTextComponent);
            this.fillGradient(matrixStack, n3 - 3, n4 - 3, n3 + n5 + 3, n4 + 8 + 3, -1073741824, -1073741824);
            this.font.func_243246_a(matrixStack, iTextComponent, n3, n4, 0xFFFFFF);
        }
    }

    private void lambda$func_224088_g$5(boolean bl) {
        if (bl) {
            this.func_224100_h();
        } else {
            this.minecraft.displayGuiScreen(this);
        }
    }

    private void lambda$func_224104_b$4(boolean bl) {
        if (bl) {
            this.func_224097_i();
        } else {
            this.field_224120_g = -1;
            this.minecraft.displayGuiScreen(this);
        }
    }

    private void lambda$init$3(Button button) {
        this.minecraft.displayGuiScreen(this.field_224116_c);
    }

    private void lambda$init$2(Button button) {
        this.minecraft.displayGuiScreen(new RealmsBackupInfoScreen(this, this.field_224117_d.get(this.field_224120_g)));
        this.field_224120_g = -1;
    }

    private void lambda$init$1(Button button) {
        this.func_224104_b(this.field_224120_g);
    }

    private void lambda$init$0(Button button) {
        this.func_224088_g();
    }

    static Minecraft access$000(RealmsBackupScreen realmsBackupScreen) {
        return realmsBackupScreen.minecraft;
    }

    static FontRenderer access$100(RealmsBackupScreen realmsBackupScreen) {
        return realmsBackupScreen.font;
    }

    static FontRenderer access$200(RealmsBackupScreen realmsBackupScreen) {
        return realmsBackupScreen.font;
    }

    static Minecraft access$300(RealmsBackupScreen realmsBackupScreen) {
        return realmsBackupScreen.minecraft;
    }

    static Minecraft access$400(RealmsBackupScreen realmsBackupScreen) {
        return realmsBackupScreen.minecraft;
    }

    class BackupObjectSelectionList
    extends RealmsObjectSelectionList<BackupObjectSelectionListEntry> {
        final RealmsBackupScreen this$0;

        public BackupObjectSelectionList(RealmsBackupScreen realmsBackupScreen) {
            this.this$0 = realmsBackupScreen;
            super(realmsBackupScreen.width - 150, realmsBackupScreen.height, 32, realmsBackupScreen.height - 15, 36);
        }

        public void func_223867_a(Backup backup) {
            RealmsBackupScreen realmsBackupScreen = this.this$0;
            Objects.requireNonNull(realmsBackupScreen);
            this.addEntry(new BackupObjectSelectionListEntry(realmsBackupScreen, backup));
        }

        @Override
        public int getRowWidth() {
            return (int)((double)this.width * 0.93);
        }

        @Override
        public boolean isFocused() {
            return this.this$0.getListener() == this;
        }

        @Override
        public int getMaxPosition() {
            return this.getItemCount() * 36;
        }

        @Override
        public void renderBackground(MatrixStack matrixStack) {
            this.this$0.renderBackground(matrixStack);
        }

        @Override
        public boolean mouseClicked(double d, double d2, int n) {
            if (n != 0) {
                return true;
            }
            if (d < (double)this.getScrollbarPosition() && d2 >= (double)this.y0 && d2 <= (double)this.y1) {
                int n2 = this.width / 2 - 92;
                int n3 = this.width;
                int n4 = (int)Math.floor(d2 - (double)this.y0) - this.headerHeight + (int)this.getScrollAmount();
                int n5 = n4 / this.itemHeight;
                if (d >= (double)n2 && d <= (double)n3 && n5 >= 0 && n4 >= 0 && n5 < this.getItemCount()) {
                    this.func_231400_a_(n5);
                    this.func_231401_a_(n4, n5, d, d2, this.width);
                }
                return false;
            }
            return true;
        }

        @Override
        public int getScrollbarPosition() {
            return this.width - 5;
        }

        @Override
        public void func_231401_a_(int n, int n2, double d, double d2, int n3) {
            int n4 = this.width - 35;
            int n5 = n2 * this.itemHeight + 36 - (int)this.getScrollAmount();
            int n6 = n4 + 10;
            int n7 = n5 - 3;
            if (d >= (double)n4 && d <= (double)(n4 + 9) && d2 >= (double)n5 && d2 <= (double)(n5 + 9)) {
                if (!this.this$0.field_224117_d.get((int)n2).field_230557_e_.isEmpty()) {
                    this.this$0.field_224120_g = -1;
                    field_224115_b = (int)this.getScrollAmount();
                    this.minecraft.displayGuiScreen(new RealmsBackupInfoScreen(this.this$0, this.this$0.field_224117_d.get(n2)));
                }
            } else if (d >= (double)n6 && d < (double)(n6 + 13) && d2 >= (double)n7 && d2 < (double)(n7 + 15)) {
                field_224115_b = (int)this.getScrollAmount();
                this.this$0.func_224104_b(n2);
            }
        }

        @Override
        public void func_231400_a_(int n) {
            this.func_239561_k_(n);
            if (n != -1) {
                RealmsNarratorHelper.func_239550_a_(I18n.format("narrator.select", this.this$0.field_224117_d.get((int)n).field_230554_b_.toString()));
            }
            this.func_223866_a(n);
        }

        public void func_223866_a(int n) {
            this.this$0.field_224120_g = n;
            this.this$0.func_224113_d();
        }

        @Override
        public void setSelected(@Nullable BackupObjectSelectionListEntry backupObjectSelectionListEntry) {
            super.setSelected(backupObjectSelectionListEntry);
            this.this$0.field_224120_g = this.getEventListeners().indexOf(backupObjectSelectionListEntry);
            this.this$0.func_224113_d();
        }

        @Override
        public void setSelected(@Nullable AbstractList.AbstractListEntry abstractListEntry) {
            this.setSelected((BackupObjectSelectionListEntry)abstractListEntry);
        }
    }

    class BackupObjectSelectionListEntry
    extends ExtendedList.AbstractListEntry<BackupObjectSelectionListEntry> {
        private final Backup field_237765_b_;
        final RealmsBackupScreen this$0;

        public BackupObjectSelectionListEntry(RealmsBackupScreen realmsBackupScreen, Backup backup) {
            this.this$0 = realmsBackupScreen;
            this.field_237765_b_ = backup;
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            this.func_237767_a_(matrixStack, this.field_237765_b_, n3 - 40, n2, n6, n7);
        }

        private void func_237767_a_(MatrixStack matrixStack, Backup backup, int n, int n2, int n3, int n4) {
            int n5 = backup.func_230749_a_() ? -8388737 : 0xFFFFFF;
            RealmsBackupScreen.access$100(this.this$0).drawString(matrixStack, "Backup (" + RealmsUtil.func_238105_a_(backup.field_230554_b_) + ")", n + 40, n2 + 1, n5);
            RealmsBackupScreen.access$200(this.this$0).drawString(matrixStack, this.func_223738_a(backup.field_230554_b_), n + 40, n2 + 12, 0x4C4C4C);
            int n6 = this.this$0.width - 175;
            int n7 = -3;
            int n8 = n6 - 10;
            boolean bl = false;
            if (!this.this$0.field_224126_m.field_230591_j_) {
                this.func_237766_a_(matrixStack, n6, n2 + -3, n3, n4);
            }
            if (!backup.field_230557_e_.isEmpty()) {
                this.func_237768_b_(matrixStack, n8, n2 + 0, n3, n4);
            }
        }

        private String func_223738_a(Date date) {
            return DateFormat.getDateTimeInstance(3, 3).format(date);
        }

        private void func_237766_a_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            boolean bl = n3 >= n && n3 <= n + 12 && n4 >= n2 && n4 <= n2 + 14 && n4 < this.this$0.height - 15 && n4 > 32;
            RealmsBackupScreen.access$300(this.this$0).getTextureManager().bindTexture(field_237741_c_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(0.5f, 0.5f, 0.5f);
            float f = bl ? 28.0f : 0.0f;
            AbstractGui.blit(matrixStack, n * 2, n2 * 2, 0.0f, f, 23, 28, 23, 56);
            RenderSystem.popMatrix();
            if (bl) {
                this.this$0.field_224118_e = field_243094_p;
            }
        }

        private void func_237768_b_(MatrixStack matrixStack, int n, int n2, int n3, int n4) {
            boolean bl = n3 >= n && n3 <= n + 8 && n4 >= n2 && n4 <= n2 + 8 && n4 < this.this$0.height - 15 && n4 > 32;
            RealmsBackupScreen.access$400(this.this$0).getTextureManager().bindTexture(field_237740_b_);
            RenderSystem.color4f(1.0f, 1.0f, 1.0f, 1.0f);
            RenderSystem.pushMatrix();
            RenderSystem.scalef(0.5f, 0.5f, 0.5f);
            float f = bl ? 15.0f : 0.0f;
            AbstractGui.blit(matrixStack, n * 2, n2 * 2, 0.0f, f, 15, 15, 15, 30);
            RenderSystem.popMatrix();
            if (bl) {
                this.this$0.field_224118_e = field_243095_q;
            }
        }
    }
}

