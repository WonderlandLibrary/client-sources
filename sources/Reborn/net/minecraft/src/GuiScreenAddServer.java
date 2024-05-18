package net.minecraft.src;

import org.lwjgl.input.*;

public class GuiScreenAddServer extends GuiScreen
{
    private GuiScreen parentGui;
    private GuiTextField serverAddress;
    private GuiTextField serverName;
    private ServerData newServerData;
    
    public GuiScreenAddServer(final GuiScreen par1GuiScreen, final ServerData par2ServerData) {
        this.parentGui = par1GuiScreen;
        this.newServerData = par2ServerData;
    }
    
    @Override
    public void updateScreen() {
        this.serverName.updateCursorCounter();
        this.serverAddress.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("addServer.add")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        this.buttonList.add(new GuiButton(2, this.width / 2 - 100, 142, String.valueOf(var1.translateKey("addServer.hideAddress")) + ": " + (this.newServerData.isHidingAddress() ? var1.translateKey("gui.yes") : var1.translateKey("gui.no"))));
        (this.serverName = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 66, 200, 20)).setFocused(true);
        this.serverName.setText(this.newServerData.serverName);
        (this.serverAddress = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 106, 200, 20)).setMaxStringLength(128);
        this.serverAddress.setText(this.newServerData.serverIP);
        this.buttonList.get(0).enabled = (this.serverAddress.getText().length() > 0 && this.serverAddress.getText().split(":").length > 0 && this.serverName.getText().length() > 0);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                this.parentGui.confirmClicked(false, 0);
            }
            else if (par1GuiButton.id == 0) {
                this.newServerData.serverName = this.serverName.getText();
                this.newServerData.serverIP = this.serverAddress.getText();
                this.parentGui.confirmClicked(true, 0);
            }
            else if (par1GuiButton.id == 2) {
                final StringTranslate var2 = StringTranslate.getInstance();
                this.newServerData.setHideAddress(!this.newServerData.isHidingAddress());
                this.buttonList.get(2).displayString = String.valueOf(var2.translateKey("addServer.hideAddress")) + ": " + (this.newServerData.isHidingAddress() ? var2.translateKey("gui.yes") : var2.translateKey("gui.no"));
            }
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.serverName.textboxKeyTyped(par1, par2);
        this.serverAddress.textboxKeyTyped(par1, par2);
        if (par1 == '\t') {
            if (this.serverName.isFocused()) {
                this.serverName.setFocused(false);
                this.serverAddress.setFocused(true);
            }
            else {
                this.serverName.setFocused(true);
                this.serverAddress.setFocused(false);
            }
        }
        if (par1 == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
        this.buttonList.get(0).enabled = (this.serverAddress.getText().length() > 0 && this.serverAddress.getText().split(":").length > 0 && this.serverName.getText().length() > 0);
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        this.serverAddress.mouseClicked(par1, par2, par3);
        this.serverName.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("addServer.title"), this.width / 2, 17, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("addServer.enterName"), this.width / 2 - 100, 53, 10526880);
        this.drawString(this.fontRenderer, var4.translateKey("addServer.enterIp"), this.width / 2 - 100, 94, 10526880);
        this.serverName.drawTextBox();
        this.serverAddress.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
