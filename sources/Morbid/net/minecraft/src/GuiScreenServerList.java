package net.minecraft.src;

import org.lwjgl.input.*;

public class GuiScreenServerList extends GuiScreen
{
    private final GuiScreen guiScreen;
    private final ServerData theServerData;
    private GuiTextField serverTextField;
    
    public GuiScreenServerList(final GuiScreen par1GuiScreen, final ServerData par2ServerData) {
        this.guiScreen = par1GuiScreen;
        this.theServerData = par2ServerData;
    }
    
    @Override
    public void updateScreen() {
        this.serverTextField.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("selectServer.select")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        (this.serverTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 116, 200, 20)).setMaxStringLength(128);
        this.serverTextField.setFocused(true);
        this.serverTextField.setText(this.mc.gameSettings.lastServer);
        this.buttonList.get(0).enabled = (this.serverTextField.getText().length() > 0 && this.serverTextField.getText().split(":").length > 0);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        this.mc.gameSettings.lastServer = this.serverTextField.getText();
        this.mc.gameSettings.saveOptions();
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                this.guiScreen.confirmClicked(false, 0);
            }
            else if (par1GuiButton.id == 0) {
                this.theServerData.serverIP = this.serverTextField.getText();
                this.guiScreen.confirmClicked(true, 0);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        if (this.serverTextField.textboxKeyTyped(par1, par2)) {
            this.buttonList.get(0).enabled = (this.serverTextField.getText().length() > 0 && this.serverTextField.getText().split(":").length > 0);
        }
        else if (par2 == 28) {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        this.serverTextField.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("selectServer.direct"), this.width / 2, this.height / 4 - 60 + 20, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("addServer.enterIp"), this.width / 2 - 100, 100, 10526880);
        this.serverTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
