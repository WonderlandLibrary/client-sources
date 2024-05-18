package com.minimap.gui;

import com.minimap.settings.*;
import com.minimap.*;
import net.minecraft.client.resources.*;
import net.minecraft.client.gui.*;

public class GuiMinimap3 extends GuiSettings
{
    private MyTinyButton nextButton;
    private MyTinyButton prevButton;
    private String title;
    
    public GuiMinimap3(final ModSettings par2ModSettings) {
        super(par2ModSettings);
        super.options = new ModOptions[] { ModOptions.DISTANCE, ModOptions.AA, ModOptions.WAYPOINTS_SCALE, ModOptions.COLOURS, ModOptions.LIGHT, ModOptions.REDSTONE, ModOptions.FLOWERS, ModOptions.COMPASS, ModOptions.BIOME, ModOptions.ENTITY_HEIGHT, ModOptions.SAFE_MAP, ModOptions.RESET };
    }
    
    @Override
    public void initGui() {
        super.initGui();
        super.screenTitle = XaeroMinimap.message;
        this.title = I18n.format("gui.xaero_minimap_settings", new Object[0]);
        if (ModSettings.serverSettings != Integer.MAX_VALUE) {
            this.title = "§e" + I18n.format("gui.xaero_server_disabled", new Object[0]);
        }
        super.buttonList.add(this.nextButton = new MyTinyButton(202, super.width / 2 + 80, super.height / 7 + 144, I18n.format("gui.xaero_next", new Object[0])));
        super.buttonList.add(this.prevButton = new MyTinyButton(203, super.width / 2 - 155, super.height / 7 + 144, I18n.format("gui.xaero_previous", new Object[0])));
        this.nextButton.enabled = false;
    }
    
    @Override
    protected void actionPerformed(final GuiButton par1GuiButton) {
        super.actionPerformed(par1GuiButton);
        if (par1GuiButton.enabled) {
            if (par1GuiButton.id == 201) {
                super.mc.displayGuiScreen(new GuiWaypoints(this));
            }
            if (par1GuiButton.id == 203) {
                super.mc.displayGuiScreen(new GuiMinimap2(super.guiModSettings));
            }
        }
    }
    
    @Override
    public void drawScreen(final int par1, final int par2, final float par3) {
        super.drawScreen(par1, par2, par3);
        this.drawCenteredString(super.fontRendererObj, this.title, super.width / 2, 5, 16777215);
    }
}
