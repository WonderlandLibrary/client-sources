package net.minecraft.client.gui.inventory;

import net.minecraft.client.resources.*;

class CancelButton extends Button
{
    public CancelButton(final int p_i1074_2_, final int p_i1074_3_, final int p_i1074_4_) {
        super(p_i1074_2_, p_i1074_3_, p_i1074_4_, GuiBeacon.access$000(), 112, 220);
    }
    
    @Override
    public void drawButtonForegroundLayer(final int mouseX, final int mouseY) {
        GuiBeacon.access$100(GuiBeacon.this, I18n.format("gui.cancel", new Object[0]), mouseX, mouseY);
    }
}
