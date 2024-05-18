package com.minimap.gui;

import com.minimap.settings.*;
import net.minecraft.client.resources.*;
import com.minimap.interfaces.*;
import net.minecraft.client.gui.*;
import java.util.*;

public class GuiChoosePreset extends GuiScreen
{
    private GuiScreen parentGuiScreen;
    protected String screenTitle;
    
    public GuiChoosePreset(final GuiScreen par1GuiScreen, final ModSettings par2ModSettings) {
        this.screenTitle = "Customization";
        this.parentGuiScreen = par1GuiScreen;
    }
    
    @Override
    public void initGui() {
        this.screenTitle = I18n.format("gui.xaero_choose_a_preset", new Object[0]);
        InterfaceHandler.selectedId = -1;
        InterfaceHandler.draggingId = -1;
        super.buttonList.clear();
        super.buttonList.add(new GuiButton(200, super.width / 2 - 100, super.height / 6 + 168, I18n.format("gui.xaero_cancel", new Object[0])));
        int var8 = 0;
        final ArrayList<Preset> var9 = InterfaceHandler.presets;
        for (int var10 = var9.size(), var11 = 0; var11 < var10; ++var11) {
            final Preset var12 = var9.get(var11);
            super.buttonList.add(new MySmallButton(var11, super.width / 2 - 155 + var8 % 2 * 160, super.height / 7 + 24 * (var8 >> 1), var12.getName()));
            ++var8;
        }
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        if (par1GuiButton.enabled) {
            final int var2 = super.mc.gameSettings.guiScale;
            if (par1GuiButton.id < 100 && par1GuiButton instanceof MySmallButton) {
                InterfaceHandler.applyPreset(par1GuiButton.id);
                super.mc.displayGuiScreen(this.parentGuiScreen);
            }
            if (par1GuiButton.id == 200) {
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
    
    public List getButtons() {
        return super.buttonList;
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        this.drawDefaultBackground();
        this.drawCenteredString(super.fontRendererObj, this.screenTitle, super.width / 2, 20, 16777215);
        super.drawScreen(par1, par2, par3);
    }
}
