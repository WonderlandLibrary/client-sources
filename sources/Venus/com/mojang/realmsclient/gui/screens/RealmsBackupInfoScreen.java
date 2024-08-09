/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.mojang.realmsclient.gui.screens;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.realmsclient.dto.Backup;
import com.mojang.realmsclient.gui.screens.RealmsSlotOptionsScreen;
import java.util.Locale;
import java.util.Objects;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.client.gui.DialogTexts;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.realms.RealmsScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class RealmsBackupInfoScreen
extends RealmsScreen {
    private final Screen field_224047_c;
    private final Backup field_224049_e;
    private BackupInfoList field_224051_g;

    public RealmsBackupInfoScreen(Screen screen, Backup backup) {
        this.field_224047_c = screen;
        this.field_224049_e = backup;
    }

    @Override
    public void tick() {
    }

    @Override
    public void init() {
        this.minecraft.keyboardListener.enableRepeatEvents(false);
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120 + 24, 200, 20, DialogTexts.GUI_BACK, this::lambda$init$0));
        this.field_224051_g = new BackupInfoList(this, this.minecraft);
        this.addListener(this.field_224051_g);
        this.setListenerDefault(this.field_224051_g);
    }

    @Override
    public void onClose() {
        this.minecraft.keyboardListener.enableRepeatEvents(true);
    }

    @Override
    public boolean keyPressed(int n, int n2, int n3) {
        if (n == 256) {
            this.minecraft.displayGuiScreen(this.field_224047_c);
            return false;
        }
        return super.keyPressed(n, n2, n3);
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        this.renderBackground(matrixStack);
        RealmsBackupInfoScreen.drawCenteredString(matrixStack, this.font, "Changes from last backup", this.width / 2, 10, 0xFFFFFF);
        this.field_224051_g.render(matrixStack, n, n2, f);
        super.render(matrixStack, n, n2, f);
    }

    private ITextComponent func_237733_a_(String string, String string2) {
        String string3 = string.toLowerCase(Locale.ROOT);
        if (string3.contains("game") && string3.contains("mode")) {
            return this.func_237735_b_(string2);
        }
        return string3.contains("game") && string3.contains("difficulty") ? this.func_237732_a_(string2) : new StringTextComponent(string2);
    }

    private ITextComponent func_237732_a_(String string) {
        try {
            return RealmsSlotOptionsScreen.field_238035_a_[Integer.parseInt(string)];
        } catch (Exception exception) {
            return new StringTextComponent("UNKNOWN");
        }
    }

    private ITextComponent func_237735_b_(String string) {
        try {
            return RealmsSlotOptionsScreen.field_238036_b_[Integer.parseInt(string)];
        } catch (Exception exception) {
            return new StringTextComponent("UNKNOWN");
        }
    }

    private void lambda$init$0(Button button) {
        this.minecraft.displayGuiScreen(this.field_224047_c);
    }

    static Minecraft access$000(RealmsBackupInfoScreen realmsBackupInfoScreen) {
        return realmsBackupInfoScreen.minecraft;
    }

    class BackupInfoList
    extends ExtendedList<BackupInfoEntry> {
        final RealmsBackupInfoScreen this$0;

        public BackupInfoList(RealmsBackupInfoScreen realmsBackupInfoScreen, Minecraft minecraft) {
            this.this$0 = realmsBackupInfoScreen;
            super(minecraft, realmsBackupInfoScreen.width, realmsBackupInfoScreen.height, 32, realmsBackupInfoScreen.height - 64, 36);
            this.setRenderSelection(true);
            if (realmsBackupInfoScreen.field_224049_e.field_230557_e_ != null) {
                realmsBackupInfoScreen.field_224049_e.field_230557_e_.forEach(this::lambda$new$0);
            }
        }

        private void lambda$new$0(String string, String string2) {
            RealmsBackupInfoScreen realmsBackupInfoScreen = this.this$0;
            Objects.requireNonNull(realmsBackupInfoScreen);
            this.addEntry(new BackupInfoEntry(realmsBackupInfoScreen, string, string2));
        }
    }

    class BackupInfoEntry
    extends ExtendedList.AbstractListEntry<BackupInfoEntry> {
        private final String field_237738_b_;
        private final String field_237739_c_;
        final RealmsBackupInfoScreen this$0;

        public BackupInfoEntry(RealmsBackupInfoScreen realmsBackupInfoScreen, String string, String string2) {
            this.this$0 = realmsBackupInfoScreen;
            this.field_237738_b_ = string;
            this.field_237739_c_ = string2;
        }

        @Override
        public void render(MatrixStack matrixStack, int n, int n2, int n3, int n4, int n5, int n6, int n7, boolean bl, float f) {
            FontRenderer fontRenderer = RealmsBackupInfoScreen.access$000((RealmsBackupInfoScreen)this.this$0).fontRenderer;
            AbstractGui.drawString(matrixStack, fontRenderer, this.field_237738_b_, n3, n2, 0xA0A0A0);
            AbstractGui.drawString(matrixStack, fontRenderer, this.this$0.func_237733_a_(this.field_237738_b_, this.field_237739_c_), n3, n2 + 12, 0xFFFFFF);
        }
    }
}

