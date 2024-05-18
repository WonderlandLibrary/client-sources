package com.minimap.gui;

import net.minecraft.client.resources.*;
import com.minimap.interfaces.*;
import java.io.*;
import com.minimap.settings.*;
import com.minimap.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiEditMode extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private ModSettings guiModSettings;
    
    public GuiEditMode(final GuiScreen par1GuiScreen, final ModSettings par2ModSettings) {
        this.screenTitle = "Customization";
        this.parentGuiScreen = par1GuiScreen;
        this.guiModSettings = par2ModSettings;
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18n.format("gui.xaero_edit_mode", new Object[0]);
        InterfaceHandler.selectedId = -1;
        InterfaceHandler.draggingId = -1;
        super.buttonList.clear();
        super.buttonList.add(new MySmallButton(200, super.width / 2 - 155, super.height / 6 + 143, I18n.format("gui.xaero_confirm", new Object[0])));
        super.buttonList.add(new MySmallButton(202, super.width / 2 + 5, super.height / 6 + 143, I18n.format("gui.xaero_choose_a_preset", new Object[0])));
        super.buttonList.add(new GuiButton(201, super.width / 2 - 100, super.height / 6 + 168, I18n.format("gui.xaero_cancel", new Object[0])));
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            final int var2 = super.mc.gameSettings.guiScale;
            if (par1GuiButton.id < 100 && par1GuiButton instanceof MySmallButton) {
                try {
                    this.guiModSettings.setOptionValue(((MySmallButton)par1GuiButton).returnModOptions(), 1);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                par1GuiButton.displayString = this.guiModSettings.getKeyBinding(ModOptions.getModOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 200) {
                try {
                    InterfaceHandler.confirm();
                    this.guiModSettings.saveSettings();
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
                super.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 201) {
                InterfaceHandler.cancel();
                super.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 202) {
                super.mc.displayGuiScreen(new GuiChoosePreset(this, XaeroMinimap.getSettings()));
            }
            if (super.mc.gameSettings.guiScale != var2) {
                final ScaledResolution res = new ScaledResolution(super.mc);
                final int var3 = res.getScaledWidth();
                final int var4 = res.getScaledHeight();
                this.setWorldAndResolution(super.mc, var3, var4);
            }
        }
    }
    
    public List<GuiButton> getButtons() {
        return (List<GuiButton>)super.buttonList;
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        if (InterfaceHandler.draggingId == -1) {
            if (super.mc.thePlayer == null) {
                this.drawDefaultBackground();
                this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_not_ingame", new Object[0]), super.width / 2, super.height / 6 + 128, 16777215);
            }
            else {
                this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_minimap_guide", new Object[0]), super.width / 2, super.height / 6 + 128, 16777215);
            }
            super.drawScreen(par1, par2, par3);
        }
        if (super.mc.thePlayer != null) {
            final ScaledResolution scaledresolution = new ScaledResolution(XaeroMinimap.mc);
            final int width = scaledresolution.getScaledWidth();
            final int height = scaledresolution.getScaledHeight();
            final int scale = scaledresolution.getScaleFactor();
            InterfaceHandler.drawBoxes(par1, par2, width, height, scale);
        }
    }
}
