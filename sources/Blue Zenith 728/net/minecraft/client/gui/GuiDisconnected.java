package net.minecraft.client.gui;

import club.bluezenith.BlueZenith;
import club.bluezenith.ui.alt.AltLogin;
import club.bluezenith.util.player.PacketUtil;
import club.bluezenith.util.math.MathUtil;
import net.minecraft.client.resources.I18n;
import net.minecraft.util.IChatComponent;
import org.apache.commons.lang3.RandomStringUtils;

import java.io.IOException;
import java.util.List;

public class GuiDisconnected extends GuiScreen {
    private final String reason;
    private final IChatComponent message;
    private List<String> multilineMessage;
    private final GuiScreen parentScreen;
    private int textHeight;

    public GuiDisconnected(GuiScreen screen, String reasonLocalizationKey, IChatComponent chatComp) {
        this.parentScreen = screen;
        this.reason = I18n.format(reasonLocalizationKey);
        this.message = chatComp;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        //RPCManager.updateRPC("Disconnected", "");
        this.buttonList.clear();
        this.multilineMessage = this.fontRendererObj.listFormattedStringToWidth(this.message.getFormattedText(), this.width - 50);
        this.textHeight = this.multilineMessage.size() * this.fontRendererObj.FONT_HEIGHT;
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT, I18n.format("gui.toMenu")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 20 + 2, 99, 20, "Reconnect"));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100 + 99 + 2, this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 20 + 2, 99, 20, "Gen & reconnect"));
        this.buttonList.add(new GuiButton(3, this.width / 2 - 100, this.height / 2 + this.textHeight / 2 + this.fontRendererObj.FONT_HEIGHT + 40 + 4, "Mark IP as banned")
                .onClick(() -> {
                    BlueZenith.getBlueZenith().getNewAltManagerGUI().ipTracker.markIpAsBanned();
                    BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("IP Tracker", "Marked current IP as banned", 2000);
                }));
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        switch(button.id) {
            case 0:
                this.mc.displayGuiScreen(this.parentScreen);
                break;
            case 1:
                PacketUtil.reconnect();
                break;
            case 2:
                String l = RandomStringUtils.randomAlphanumeric(MathUtil.getRandomInt(6, 16));//AltUtil.getRandomNick();
                AltLogin.login(l, null,
                        (session -> BlueZenith.getBlueZenith().getNotificationPublisher().postSuccess("Account Manager", "Logged in as " + session.getUsername(), 2500)),
                        null
                );
                PacketUtil.reconnect();
                break;
        }
        super.actionPerformed(button);
    }

    /**f
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, this.reason, this.width / 2, this.height / 2 - this.textHeight / 2 - this.fontRendererObj.FONT_HEIGHT * 2, 11184810);
        int i = this.height / 2 - this.textHeight / 2;

        if (this.multilineMessage != null) {
            for (String s : this.multilineMessage) {
                this.drawCenteredString(this.fontRendererObj, s, this.width / 2, i, 16777215);
                i += this.fontRendererObj.FONT_HEIGHT;
            }
        }

        super.drawScreen(mouseX, mouseY, partialTicks);
    }
}
