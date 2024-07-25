package net.minecraft.client.gui;

import club.bluezenith.ui.notifications.NotificationType;
import net.minecraft.client.gui.achievement.GuiAchievements;
import net.minecraft.client.gui.achievement.GuiStats;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;

import java.io.IOException;

import static club.bluezenith.BlueZenith.getBlueZenith;
import static club.bluezenith.ui.alt.AltLogin.login;
import static club.bluezenith.util.math.MathUtil.getRandomInt;
import static club.bluezenith.util.player.PacketUtil.reconnect;
import static java.lang.String.format;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

public class GuiIngameMenu extends GuiScreen {
    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        //RPCManager.updateRPC("In-game Menu", mc.isSingleplayer() ? "" : "IP: " + BlueZenith.getBlueZenith().getCurrentServerIP());
        this.buttonList.clear();
        int i = -16;

        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 24 + i, I18n.format("menu.returnToGame")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.achievements")));
        this.buttonList.add(new GuiButton(2, this.width / 2 + 2, this.height / 4 + 48 + i, 98, 20, I18n.format("gui.stats")));
        GuiButton guibutton;
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.options")));
        this.buttonList.add(guibutton = new GuiButton(4, this.width / 2 + 2, this.height / 4 + 96 + i, 98, 20, I18n.format("menu.shareToLan")));
        this.buttonList.add(new GuiButton(5, this.width / 2 - 100, this.height / 4 + 120 + i, I18n.format(!this.mc.isIntegratedServerRunning() ? "menu.disconnect" : "menu.returnToMenu")));
        if(!this.mc.isIntegratedServerRunning()){
            this.buttonList.add(new GuiButton(6, this.width / 2 - 100, this.height / 4 + 144 + i, 98, 20, "Reconnect"));
            this.buttonList.add(new GuiButton(7, this.width / 2 + 2, this.height / 4 + 144 + i, 98, 20, "Random Nick"));
        }
        guibutton.enabled = this.mc.isSingleplayer() && !this.mc.getIntegratedServer().getPublic();
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        switch (button.id) {
            case 0:
                this.mc.displayGuiScreen(null);
                this.mc.setIngameFocus();
                break;
            case 1:
                this.mc.displayGuiScreen(new GuiAchievements(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            case 2:
                this.mc.displayGuiScreen(new GuiStats(this, this.mc.thePlayer.getStatFileWriter()));
                break;
            case 3:
                this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
                break;
            case 4:
                this.mc.displayGuiScreen(new GuiShareToLan(this));
                break;
            case 5:
                boolean flag = this.mc.isIntegratedServerRunning();
                boolean flag1 = this.mc.func_181540_al();
                button.enabled = false;
                this.mc.theWorld.sendQuittingDisconnectingPacket();
                this.mc.loadWorld(null);
                getBlueZenith().setCurrentServerIP(null);

                if (flag) {
                    this.mc.displayGuiScreen(getBlueZenith().getMainMenu());
                } else if (flag1) {
                    RealmsBridge realmsbridge = new RealmsBridge();
                    realmsbridge.switchToRealms(getBlueZenith().getMainMenu());
                } else {
                    this.mc.displayGuiScreen(new GuiMultiplayer(getBlueZenith().getMainMenu()));
                }
                break;
            case 6:
                reconnect();
                break;
            case 7:
                login(randomAlphanumeric(getRandomInt(8, 16)), null,
                        (s) -> getBlueZenith().getNotificationPublisher().post(
                                getBlueZenith().getName(),
                                format("Logged in as %s.", s.getUsername()),
                                NotificationType.SUCCESS,
                                5000
                        ), (ex) -> {});
                reconnect();
                break;
        }
    }

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, I18n.format("menu.game"), this.width / 2, 40, 16777215);
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void onGuiClosed() {
        /*if (mc.isSingleplayer()) {
            RPCManager.updateRPC("Playing Singleplayer", "");
        } else RPCManager.updateRPC("Playing Multiplayer", "IP: " + BlueZenith.getBlueZenith().getCurrentServerIP());*/
    }
}
