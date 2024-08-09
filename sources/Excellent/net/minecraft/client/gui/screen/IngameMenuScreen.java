package net.minecraft.client.gui.screen;

import dev.excellent.client.component.impl.LastConnectionComponent;
import dev.excellent.client.screen.mainmenu.MainMenu;
import net.minecraft.client.gui.advancements.AdvancementsScreen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.realms.RealmsBridgeScreen;
import net.minecraft.util.Util;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.mojang.blaze3d.matrix.MatrixStack;

public class IngameMenuScreen extends Screen {
    private final boolean isFullMenu;

    public IngameMenuScreen(boolean isFullMenu) {
        super(isFullMenu ? new TranslationTextComponent("menu.game") : new TranslationTextComponent("menu.paused"));
        this.isFullMenu = isFullMenu;
    }

    protected void init() {
        if (this.isFullMenu) {
            this.addButtons();
        }
    }

    private void addButtons() {
        int i = -16;
        int j = 98;
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 24 + -16, 204, 20, new TranslationTextComponent("menu.returnToGame"), (button2) ->
        {
            this.minecraft.displayScreen(null);
            this.minecraft.mouseHelper.grabMouse();
        }));
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.advancements"), (button2) ->
        {
            this.minecraft.displayScreen(new AdvancementsScreen(this.minecraft.player.connection.getAdvancementManager()));
        }));
        this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 48 + -16, 98, 20, new TranslationTextComponent("gui.stats"), (button2) ->
        {
            this.minecraft.displayScreen(new StatsScreen(this, this.minecraft.player.getStats()));
        }));
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 72 + -16, 98, 20, new StringTextComponent("Discord"), (button2) ->
        {
            Util.getOSType().openURI("https://discord.gg/excellentdlc");
        }));
        this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 72 + -16, 98, 20, new StringTextComponent("Telegram"), (button2) ->
        {
            Util.getOSType().openURI("https://t.me/excellent_client");
        }));
        this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 96 + -16, 98, 20, new TranslationTextComponent("menu.options"), (button2) ->
        {
            this.minecraft.displayScreen(new OptionsScreen(this, this.minecraft.gameSettings));
        }));
        boolean isLocalHost = this.minecraft.isSingleplayer();
        Button button = this.addButton(new Button(this.width / 2 + 4, this.height / 4 + 96 + -16, 98, 20, isLocalHost ? new TranslationTextComponent("menu.shareToLan") : new StringTextComponent("Reconnect"), (button2) ->
        {
            if (isLocalHost) {
                this.minecraft.displayScreen(new ShareToLanScreen(this));
            } else {
                this.minecraft.world.sendQuittingDisconnectingPacket();
                this.minecraft.displayScreen(new ConnectingScreen(new MainMenu(), this.minecraft, LastConnectionComponent.ip, LastConnectionComponent.port));

            }
        }));
        button.active = !isLocalHost || !this.minecraft.getIntegratedServer().getPublic();
        Button button1 = this.addButton(new Button(this.width / 2 - 102, this.height / 4 + 120 + -16, 204, 20, new TranslationTextComponent("menu.returnToMenu"), (button2) ->
        {
            boolean flag = this.minecraft.isIntegratedServerRunning();
            boolean flag1 = this.minecraft.isConnectedToRealms();
            button2.active = false;
            this.minecraft.world.sendQuittingDisconnectingPacket();

            if (flag) {
                this.minecraft.unloadWorld(new DirtMessageScreen(new TranslationTextComponent("menu.savingLevel")));
            } else {
                this.minecraft.unloadWorld();
            }

            if (flag) {
                this.minecraft.displayScreen(new MainMenu());
            } else if (flag1) {
                RealmsBridgeScreen realmsbridgescreen = new RealmsBridgeScreen();
                realmsbridgescreen.func_231394_a_(new MainMenu());
            } else {
                this.minecraft.displayScreen(new MultiplayerScreen(new MainMenu()));
            }
        }));

        if (!this.minecraft.isIntegratedServerRunning()) {
            button1.setMessage(new TranslationTextComponent("menu.disconnect"));
        }
    }

    public void tick() {
        super.tick();
    }

    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        if (this.isFullMenu) {
            this.renderBackground(matrixStack);
            drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 40, 16777215);
        } else {
            drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 10, 16777215);
        }

        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
