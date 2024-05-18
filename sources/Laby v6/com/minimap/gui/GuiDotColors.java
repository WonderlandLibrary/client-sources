package com.minimap.gui;

import net.minecraft.client.resources.*;
import com.minimap.*;
import java.io.*;
import com.minimap.settings.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiDotColors extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    private ModSettings guiModSettings;
    public ArrayList<GuiDropDown> dropDowns;
    private String[] colorOptions;
    private boolean dropped;
    
    public GuiDotColors(final GuiScreen par1GuiScreen, final ModSettings par2ModSettings) {
        this.dropDowns = new ArrayList<GuiDropDown>();
        this.dropped = false;
        this.parentGuiScreen = par1GuiScreen;
        this.guiModSettings = par2ModSettings;
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18n.format("gui.xaero_entity_colours", new Object[0]);
        super.buttonList.clear();
        super.buttonList.add(new MySmallButton(200, super.width / 2 - 155, super.height / 6 + 168, I18n.format("gui.xaero_confirm", new Object[0])));
        super.buttonList.add(new MySmallButton(201, super.width / 2 + 5, super.height / 6 + 168, I18n.format("gui.xaero_cancel", new Object[0])));
        this.dropDowns.clear();
        this.colorOptions = this.createColorOptions();
        final String[] playerOptions = new String[this.colorOptions.length + 1];
        for (int i = 0; i < this.colorOptions.length; ++i) {
            playerOptions[i] = this.colorOptions[i];
        }
        playerOptions[this.colorOptions.length] = "gui.xaero_team_colours";
        this.dropDowns.add(new GuiDropDown(this.colorOptions, super.width / 2 - 60, super.height / 7 + 50, 120, XaeroMinimap.getSettings().mobsColor));
        this.dropDowns.add(new GuiDropDown(this.colorOptions, super.width / 2 - 60, super.height / 7 + 80, 120, XaeroMinimap.getSettings().hostileColor));
        this.dropDowns.add(new GuiDropDown(this.colorOptions, super.width / 2 - 60, super.height / 7 + 110, 120, XaeroMinimap.getSettings().itemsColor));
        this.dropDowns.add(new GuiDropDown(this.colorOptions, super.width / 2 - 60, super.height / 7 + 140, 120, XaeroMinimap.getSettings().otherColor));
        this.dropDowns.add(new GuiDropDown(playerOptions, super.width / 2 - 60, super.height / 7 + 20, 120, (XaeroMinimap.getSettings().playersColor != -1) ? XaeroMinimap.getSettings().playersColor : this.colorOptions.length));
    }
    
    public String[] createColorOptions() {
        final String[] options = new String[ModSettings.ENCHANT_COLOR_NAMES.length];
        for (int i = 0; i < options.length; ++i) {
            if (i == 0) {
                options[i] = I18n.format(ModSettings.ENCHANT_COLOR_NAMES[i], new Object[0]);
            }
            else {
                options[i] = "§" + ModSettings.ENCHANT_COLORS[i] + I18n.format(ModSettings.ENCHANT_COLOR_NAMES[i], new Object[0]);
            }
        }
        return options;
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
                XaeroMinimap.getSettings().mobsColor = this.dropDowns.get(0).selected;
                XaeroMinimap.getSettings().hostileColor = this.dropDowns.get(1).selected;
                XaeroMinimap.getSettings().itemsColor = this.dropDowns.get(2).selected;
                XaeroMinimap.getSettings().otherColor = this.dropDowns.get(3).selected;
                final int playerOption = this.dropDowns.get(4).selected;
                XaeroMinimap.getSettings().playersColor = ((playerOption < this.colorOptions.length) ? playerOption : -1);
                try {
                    this.guiModSettings.saveSettings();
                }
                catch (IOException e2) {
                    e2.printStackTrace();
                }
                super.mc.displayGuiScreen(this.parentGuiScreen);
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
    protected void mouseClicked(final int par1, final int par2, final int par3) throws IOException {
        for (final GuiDropDown d : this.dropDowns) {
            if (!d.closed && d.onDropDown(par1, par2)) {
                d.mouseClicked(par1, par2, par3);
                return;
            }
            d.closed = true;
        }
        for (final GuiDropDown d : this.dropDowns) {
            if (d.onDropDown(par1, par2)) {
                d.mouseClicked(par1, par2, par3);
                return;
            }
            d.closed = true;
        }
        super.mouseClicked(par1, par2, par3);
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(super.fontRendererObj, this.screenTitle, super.width / 2, 20, 16777215);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_players", new Object[0]) + ":", super.width / 2, super.height / 7 + 10, 16777215);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_mobs", new Object[0]) + ":", super.width / 2, super.height / 7 + 40, 16777215);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_hostile", new Object[0]) + ":", super.width / 2, super.height / 7 + 70, 16777215);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_items", new Object[0]) + ":", super.width / 2, super.height / 7 + 100, 16777215);
        this.drawCenteredString(super.fontRendererObj, I18n.format("gui.xaero_other", new Object[0]) + ":", super.width / 2, super.height / 7 + 130, 16777215);
        if (this.dropped) {
            super.drawScreen(0, 0, par3);
        }
        else {
            super.drawScreen(par1, par2, par3);
        }
        this.dropped = false;
        for (int k = 0; k < this.dropDowns.size(); ++k) {
            if (this.dropDowns.get(k).closed) {
                this.dropDowns.get(k).drawButton(par1, par2);
            }
            else {
                this.dropped = true;
            }
        }
        for (int k = 0; k < this.dropDowns.size(); ++k) {
            if (!this.dropDowns.get(k).closed) {
                this.dropDowns.get(k).drawButton(par1, par2);
            }
        }
    }
}
