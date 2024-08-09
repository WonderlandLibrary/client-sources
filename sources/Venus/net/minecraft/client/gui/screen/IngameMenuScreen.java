/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.gui.screen;

import com.mojang.blaze3d.matrix.MatrixStack;
import mpp.venusfr.functions.api.FunctionRegistry;
import mpp.venusfr.ui.mainmenu.MainScreen;
import mpp.venusfr.venusfr;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.screen.ConfirmOpenLinkScreen;
import net.minecraft.client.gui.screen.DirtMessageScreen;
import net.minecraft.client.gui.screen.MultiplayerScreen;
import net.minecraft.client.gui.screen.OptionsScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ShareToLanScreen;
import net.minecraft.client.gui.screen.StatsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.util.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.text.TranslationTextComponent;

public class IngameMenuScreen
extends Screen {
    private final boolean isFullMenu;

    public IngameMenuScreen(boolean bl) {
        super(bl ? new TranslationTextComponent("menu.game") : new TranslationTextComponent("menu.paused"));
        this.isFullMenu = bl;
    }

    @Override
    protected void init() {
        if (this.isFullMenu) {
            this.addButtons();
        }
    }

    private void addButtons() {
        int n = -16;
        int n2 = 98;
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, new TranslationTextComponent("menu.returnToGame"), this::lambda$addButtons$0));
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.advancements"), this::lambda$addButtons$1));
        this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.stats"), this::lambda$addButtons$2));
        String string = SharedConstants.getVersion().isStable() ? "https://aka.ms/javafeedback?ref=game" : "https://aka.ms/snapshotfeedback?ref=game";
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 72 + -16, 98, 20, new TranslationTextComponent("menu.sendFeedback"), arg_0 -> this.lambda$addButtons$4(string, arg_0)));
        this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20, new TranslationTextComponent("menu.reportBugs"), this::lambda$addButtons$6));
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 96 + -16, 98, 20, new TranslationTextComponent("menu.options"), this::lambda$addButtons$7));
        Button button = this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 96 + -16, 98, 20, new TranslationTextComponent("menu.shareToLan"), this::lambda$addButtons$8));
        button.active = this.minecraft.isSingleplayer() && !this.minecraft.getIntegratedServer().getPublic();
        Button button2 = this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, new TranslationTextComponent("menu.returnToMenu"), this::lambda$addButtons$9));
        if (!this.minecraft.isIntegratedServerRunning()) {
            button2.setMessage(new TranslationTextComponent("menu.disconnect"));
        }
    }

    @Override
    public void tick() {
        super.tick();
    }

    @Override
    public void render(MatrixStack matrixStack, int n, int n2, float f) {
        if (this.isFullMenu) {
            this.renderBackground(matrixStack);
            IngameMenuScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 40, 0xFFFFFF);
        } else {
            IngameMenuScreen.drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 10, 0xFFFFFF);
        }
        super.render(matrixStack, n, n2, f);
    }

    private void lambda$addButtons$9(Button button) {
        boolean bl = this.minecraft.isIntegratedServerRunning();
        boolean bl2 = this.minecraft.isConnectedToRealms();
        button.active = false;
        this.minecraft.world.sendQuittingDisconnectingPacket();
        if (bl) {
            this.minecraft.unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
        } else {
            this.minecraft.unloadWorld();
        }
        FunctionRegistry functionRegistry = venusfr.getInstance().getFunctionRegistry();
        if (bl) {
            this.minecraft.displayGuiScreen(new MainScreen());
        } else if (bl2) {
            RealmsBridgeScreen realmsBridgeScreen = new RealmsBridgeScreen();
            realmsBridgeScreen.func_231394_a_(new MainScreen());
        } else {
            this.minecraft.displayGuiScreen(new MultiplayerScreen(new MainScreen()));
        }
    }

    private void lambda$addButtons$8(Button button) {
        this.minecraft.displayGuiScreen(new ShareToLanScreen(this));
    }

    private void lambda$addButtons$7(Button button) {
        this.minecraft.displayGuiScreen(new OptionsScreen(this, this.minecraft.gameSettings));
    }

    private void lambda$addButtons$6(Button button) {
        this.minecraft.displayGuiScreen(new ConfirmOpenLinkScreen(this::lambda$addButtons$5, "https://aka.ms/snapshotbugs?ref=game", true));
    }

    private void lambda$addButtons$5(boolean bl) {
        if (bl) {
            Util.getOSType().openURI("https://aka.ms/snapshotbugs?ref=game");
        }
        this.minecraft.displayGuiScreen(this);
    }

    private void lambda$addButtons$4(String string, Button button) {
        this.minecraft.displayGuiScreen(new ConfirmOpenLinkScreen(arg_0 -> this.lambda$addButtons$3(string, arg_0), string, true));
    }

    private void lambda$addButtons$3(String string, boolean bl) {
        if (bl) {
            Util.getOSType().openURI(string);
        }
        this.minecraft.displayGuiScreen(this);
    }

    private void lambda$addButtons$2(Button button) {
        this.minecraft.displayGuiScreen(new StatsScreen(this, this.minecraft.player.getStats()));
    }

    private void lambda$addButtons$1(Button button) {
        this.minecraft.displayGuiScreen(new AdvancementsScreen(this.minecraft.player.connection.getAdvancementManager()));
    }

    private void lambda$addButtons$0(Button button) {
        this.minecraft.displayGuiScreen(null);
        this.minecraft.mouseHelper.grabMouse();
    }
}

