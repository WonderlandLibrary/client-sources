// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
//

package me.kaktuswasser.altmanager;

import java.io.IOException;

import me.kaktuswasser.client.Client;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.GuiScreen;

public class GuiRenameAlt extends GuiScreen
{
    private final GuiAltManager manager;
    private GuiTextField nameField;
    private String status;
    
    public GuiRenameAlt(final GuiAltManager manager) {
        this.status = "Waiting...";
        this.manager = manager;
    }
    
    public void actionPerformed(final GuiButton button) {
        switch (button.id) {
            case 1: {
                this.mc.displayGuiScreen(this.manager);
                break;
            }
            case 0: {
                this.manager.selectedAlt.setMask(this.nameField.getText());
                this.status = "§aRenamed!";
                Client.getFileManager().getFileByName("alts").saveFile();
                break;
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(this.fontRendererObj, "Rename Alt", this.width / 2, 10, -1);
        this.drawCenteredString(this.fontRendererObj, this.status, this.width / 2, 20, -1);
        this.nameField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
    
    @Override
    public void initGui() {
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, this.height / 4 + 92 + 12, "Rename"));
        this.buttonList.add(new GuiButton(1, this.width / 2 - 100, this.height / 4 + 116 + 12, "Cancel"));
        this.nameField = new GuiTextField(1, this.fontRendererObj, this.width / 2 - 100, 76, 200, 20);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) {
        this.nameField.textboxKeyTyped(par1, par2);
        if (par1 == '\t' && this.nameField.isFocused()) {
            this.nameField.setFocused(!this.nameField.isFocused());
        }
        if (par1 == '\r') {
            this.actionPerformed((GuiButton) this.buttonList.get(0));
        }
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) {
        try {
            super.mouseClicked(par1, par2, par3);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        this.nameField.mouseClicked(par1, par2, par3);
    }
}
