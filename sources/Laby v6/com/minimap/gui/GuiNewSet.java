package com.minimap.gui;

import net.minecraft.client.resources.*;
import org.lwjgl.input.*;
import java.io.*;
import com.minimap.minimap.*;
import com.minimap.*;
import net.minecraft.client.gui.*;

public class GuiNewSet extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    public GuiTextField nameTextField;
    public String nameText;
    
    public GuiNewSet(final GuiScreen par1GuiScreen) {
        this.nameText = "";
        this.parentGuiScreen = par1GuiScreen;
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18n.format("gui.xaero_create_set", new Object[0]);
        super.buttonList.clear();
        super.buttonList.add(new MySmallButton(200, super.width / 2 - 155, super.height / 6 + 168, I18n.format("gui.xaero_confirm", new Object[0])));
        super.buttonList.add(new MySmallButton(201, super.width / 2 + 5, super.height / 6 + 168, I18n.format("gui.xaero_cancel", new Object[0])));
        (this.nameTextField = new GuiTextField(0, super.fontRendererObj, super.width / 2 - 100, 60, 200, 20)).setText(this.nameText);
        this.nameTextField.setFocused(true);
        Keyboard.enableRepeatEvents(true);
        this.updateConfirmButton();
    }
    
    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
    }
    
    private void updateConfirmButton() {
        super.buttonList.get(0).enabled = (this.nameTextField.getText().length() > 0 && Minimap.getCurrentWorld().sets.get(this.nameTextField.getText()) == null);
    }
    
    @Override
    protected void keyTyped(final char par1, final int par2) throws IOException {
        if (this.nameTextField.isFocused()) {
            this.nameTextField.textboxKeyTyped(par1, par2);
            this.nameTextField.setText(this.nameText = this.nameTextField.getText().replaceAll(":", ""));
            this.updateConfirmButton();
        }
        if (par2 == 28 || par2 == 156) {
            this.actionPerformed(super.buttonList.get(0));
        }
        super.keyTyped(par1, par2);
    }
    
    @Override
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        super.mouseClicked(par1, par2, par3);
        this.nameTextField.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void updateScreen() {
        this.nameTextField.updateCursorCounter();
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            final int var2 = super.mc.gameSettings.guiScale;
            if (par1GuiButton.id == 200) {
                Minimap.getCurrentWorld().sets.put(this.nameTextField.getText(), new WaypointSet(Minimap.getCurrentWorld()));
                Minimap.getCurrentWorld().current = this.nameTextField.getText();
                Minimap.updateWaypoints();
                try {
                    XaeroMinimap.settings.saveWaypoints();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                super.mc.displayGuiScreen(new GuiWaypoints(((GuiWaypoints)this.parentGuiScreen).parentScreen));
            }
            if (par1GuiButton.id == 201) {
                super.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (super.mc.gameSettings.guiScale != var2) {
                final ScaledResolution res = new ScaledResolution(super.mc);
                final int var3 = res.getScaledWidth();
                final int var4 = res.getScaledHeight();
                this.setWorldAndResolution(super.mc, var3, var4);
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(super.fontRendererObj, this.screenTitle, super.width / 2, 20, 16777215);
        this.nameTextField.drawTextBox();
        super.drawScreen(par1, par2, par3);
    }
}
