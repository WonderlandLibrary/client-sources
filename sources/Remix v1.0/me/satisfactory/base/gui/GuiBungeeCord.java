package me.satisfactory.base.gui;

import net.minecraft.client.*;
import net.minecraft.util.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.client.gui.*;

public class GuiBungeeCord extends GuiScreen
{
    public static Minecraft mc;
    protected GuiTextField ipField;
    protected GuiTextField fakeNickField;
    protected GuiTextField realNickField;
    protected GuiScreen prevScreen;
    
    public GuiBungeeCord(final GuiScreen screen) {
        this.prevScreen = screen;
    }
    
    @Override
    public void initGui() {
        final int fieldWidth = 200;
        final int fieldHeight = 20;
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(1, GuiBungeeCord.width / 2 - 100, GuiBungeeCord.height / 4 + 95, fieldWidth, fieldHeight, "Save"));
        this.buttonList.add(new GuiButton(2, GuiBungeeCord.width / 2 - 100, GuiBungeeCord.height / 4 + 95 + fieldHeight + 4, fieldWidth, fieldHeight, "Cancel"));
        this.buttonList.add(new GuiButton(3, GuiBungeeCord.width / 2 - 100, GuiBungeeCord.height / 4 + 95 + fieldHeight + 28, fieldWidth, fieldHeight, this.getEnableButtonText()));
        this.realNickField = new GuiTextField(2, this.fontRendererObj, GuiBungeeCord.width / 2 - 100, GuiBungeeCord.height / 5, fieldWidth, fieldHeight);
        this.fakeNickField = new GuiTextField(1, this.fontRendererObj, GuiBungeeCord.width / 2 - 100, GuiBungeeCord.height / 5 + 40, fieldWidth, fieldHeight);
        (this.ipField = new GuiTextField(0, this.fontRendererObj, GuiBungeeCord.width / 2 - 100, GuiBungeeCord.height / 5 + 80, fieldWidth, fieldHeight)).setText(GuiBungeeCord.mc.getFakeIp());
        this.fakeNickField.setText(GuiBungeeCord.mc.getFakeNick());
        this.realNickField.setText(GuiBungeeCord.mc.getSession().getUsername());
    }
    
    private String getEnableButtonText() {
        return GuiBungeeCord.mc.isUUIDHack ? "Enabled" : "§4Disabled";
    }
    
    @Override
    public void handleMouseInput() {
        super.handleMouseInput();
    }
    
    @Override
    protected void actionPerformed(final GuiButton button) {
        if (button.id == 1) {
            final Session realSession = GuiBungeeCord.mc.getSession();
            GuiBungeeCord.mc.setSession(new Session(this.realNickField.getText(), realSession.getPlayerID(), realSession.getToken(), Session.Type.LEGACY.name()));
            GuiBungeeCord.mc.setFakeNick(this.fakeNickField.getText());
            GuiBungeeCord.mc.setFakeIp(this.ipField.getText());
            GuiBungeeCord.mc.displayGuiScreen(this.prevScreen);
            if (GuiBungeeCord.mc.getCurrentServerData() != null && GuiBungeeCord.mc.theWorld != null) {
                final ServerData data = GuiBungeeCord.mc.getCurrentServerData();
                GuiBungeeCord.mc.theWorld.sendQuittingDisconnectingPacket();
                GuiBungeeCord.mc.loadWorld(null);
                GuiBungeeCord.mc.displayGuiScreen(new GuiConnecting(this.prevScreen, GuiBungeeCord.mc, data));
            }
            else {
                GuiBungeeCord.mc.displayGuiScreen(this.prevScreen);
            }
        }
        else if (button.id == 2) {
            GuiBungeeCord.mc.displayGuiScreen(this.prevScreen);
        }
        else if (button.id == 3) {
            GuiBungeeCord.mc.isUUIDHack = !GuiBungeeCord.mc.isUUIDHack;
            button.displayString = this.getEnableButtonText();
        }
    }
    
    @Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        this.ipField.mouseClicked(mouseX, mouseY, mouseButton);
        this.fakeNickField.mouseClicked(mouseX, mouseY, mouseButton);
        this.realNickField.mouseClicked(mouseX, mouseY, mouseButton);
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }
    
    @Override
    protected void keyTyped(final char typedChar, final int keyCode) {
        if (keyCode == 1) {
            GuiBungeeCord.mc.displayGuiScreen(this.prevScreen);
            return;
        }
        if (keyCode == 15) {
            if (this.realNickField.isFocused()) {
                this.realNickField.setFocused(false);
                this.fakeNickField.setFocused(true);
            }
            else if (this.fakeNickField.isFocused()) {
                this.fakeNickField.setFocused(false);
                this.ipField.setFocused(true);
            }
            else if (this.ipField.isFocused()) {
                this.ipField.setFocused(false);
                this.realNickField.setFocused(true);
            }
        }
        if (this.ipField.isFocused()) {
            this.ipField.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.fakeNickField.isFocused()) {
            this.fakeNickField.textboxKeyTyped(typedChar, keyCode);
        }
        if (this.realNickField.isFocused()) {
            this.realNickField.textboxKeyTyped(typedChar, keyCode);
        }
    }
    
    @Override
    public void drawScreen(final int mouseX, final int mouseY, final float partialTicks) {
        this.drawDefaultBackground();
        Gui.drawCenteredString(this.fontRendererObj, "Real nick", GuiBungeeCord.width / 2, this.realNickField.yPosition - 15, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "Fake nick", GuiBungeeCord.width / 2, this.fakeNickField.yPosition - 15, 16777215);
        Gui.drawCenteredString(this.fontRendererObj, "Fake IP", GuiBungeeCord.width / 2, this.ipField.yPosition - 15, 16777215);
        this.ipField.drawTextBox();
        this.fakeNickField.drawTextBox();
        this.realNickField.drawTextBox();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }
    
    static {
        GuiBungeeCord.mc = Minecraft.getMinecraft();
    }
}
