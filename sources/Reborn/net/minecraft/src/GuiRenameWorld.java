package net.minecraft.src;

import org.lwjgl.input.*;

public class GuiRenameWorld extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    private GuiTextField theGuiTextField;
    private final String worldName;
    
    public GuiRenameWorld(final GuiScreen par1GuiScreen, final String par2Str) {
        this.parentGuiScreen = par1GuiScreen;
        this.worldName = par2Str;
    }
    
    @Override
    public void updateScreen() {
        this.theGuiTextField.updateCursorCounter();
    }
    
    @Override
    public void initGui() {
        final StringTranslate var1 = StringTranslate.getInstance();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.clear();
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 96 + 12, var1.translateKey("selectWorld.renameButton")));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 120 + 12, var1.translateKey("gui.cancel")));
        final ISaveFormat var2 = this.mc.getSaveLoader();
        final WorldInfo var3 = var2.getWorldInfo(this.worldName);
        final String var4 = var3.getWorldName();
        (this.theGuiTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 100, 60, 200, 20)).setFocused(true);
        this.theGuiTextField.setText(var4);
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 1) {
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
            else if (par1GuiButton.id == 0) {
                final ISaveFormat var2 = this.mc.getSaveLoader();
                var2.renameWorld(this.worldName, this.theGuiTextField.getText().trim());
                this.mc.displayGuiScreen(this.parentGuiScreen);
            }
        }
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.theGuiTextField.textboxKeyTyped(par1, par2);
        this.buttonList.get(0).enabled = (this.theGuiTextField.getText().trim().length() > 0);
        if (par1 == '\r') {
            this.actionPerformed(this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        super.mouseClicked(par1, par2, par3);
        this.theGuiTextField.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        final StringTranslate var4 = StringTranslate.getInstance();
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRenderer, var4.translateKey("selectWorld.renameTitle"), this.width / 2, this.height / 4 - 60 + 20, 16777215);
        this.drawString(this.fontRenderer, var4.translateKey("selectWorld.enterName"), this.width / 2 - 100, 47, 10526880);
        this.theGuiTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
