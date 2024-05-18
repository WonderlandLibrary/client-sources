package com.minimap.gui;

import com.minimap.settings.*;
import net.minecraft.client.resources.*;
import com.minimap.*;
import de.labystudio.modapi.*;
import java.io.*;
import net.minecraft.client.gui.*;

public class GuiSettings extends GuiScreen
{
    protected String screenTitle;
    protected ModSettings guiModSettings;
    protected ModOptions[] options;
    
    public GuiSettings(final ModSettings par2ModSettings) {
        this.guiModSettings = par2ModSettings;
    }
    
    @Override
    public void initGui() {
        super.buttonList.clear();
        super.buttonList.add(new GuiButton(200, super.width / 2 - 100, super.height / 6 + 168, I18n.format("gui.xaero_back", new Object[0])));
        int var8 = 0;
        if (this.options == null) {
            return;
        }
        for (int var9 = this.options.length, var10 = 0; var10 < var9; ++var10) {
            final ModOptions option = this.options[var10];
            if (!option.getEnumFloat()) {
                super.buttonList.add(new MySmallButton(option.returnEnumOrdinal(), super.width / 2 - 155 + var8 % 2 * 160, super.height / 7 + 24 * (var8 >> 1), option, this.guiModSettings.getKeyBinding(option)));
            }
            else {
                super.buttonList.add(new MyOptionSlider(option.returnEnumOrdinal(), super.width / 2 - 155 + var8 % 2 * 160, super.height / 7 + 24 * (var8 >> 1), option));
            }
            ++var8;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            final int var2 = super.mc.gameSettings.guiScale;
            try {
                if (par1GuiButton instanceof MySmallButton) {
                    if (((MySmallButton)par1GuiButton).returnModOptions() == ModOptions.EDIT) {
                        super.mc.displayGuiScreen(new GuiEditMode(this, XaeroMinimap.getSettings()));
                    }
                    else if (((MySmallButton)par1GuiButton).returnModOptions() == ModOptions.RESET) {
                        super.mc.displayGuiScreen(new GuiReset(ModAPI.getLastScreen()));
                    }
                    else if (((MySmallButton)par1GuiButton).returnModOptions() == ModOptions.DOTS) {
                        super.mc.displayGuiScreen(new GuiDotColors(this, XaeroMinimap.getSettings()));
                    }
                }
            }
            catch (Exception e) {
                e.printStackTrace();
            }
            if (par1GuiButton.id < 100 && par1GuiButton instanceof MySmallButton) {
                try {
                    this.guiModSettings.setOptionValue(((MySmallButton)par1GuiButton).returnModOptions(), 1);
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
                par1GuiButton.displayString = this.guiModSettings.getKeyBinding(ModOptions.getModOptions(par1GuiButton.id));
            }
            if (par1GuiButton.id == 200) {
                try {
                    this.guiModSettings.saveSettings();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
                super.mc.displayGuiScreen(ModAPI.getLastScreen());
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
        super.drawScreen(par1, par2, par3);
    }
}
