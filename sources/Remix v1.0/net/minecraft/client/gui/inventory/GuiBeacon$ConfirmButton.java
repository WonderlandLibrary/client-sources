package net.minecraft.client.gui.inventory;

import net.minecraft.client.resources.*;

class ConfirmButton extends Button
{
    public ConfirmButton(final int p_i1075_2_, final int p_i1075_3_, final int p_i1075_4_) {
        super(p_i1075_2_, p_i1075_3_, p_i1075_4_, GuiBeacon.access$000(), 90, 220);
    }
    
    @Override
    public void drawButtonForegroundLayer(final int mouseX, final int mouseY) {
        GuiBeacon.access$200(GuiBeacon.this, I18n.format("gui.done", new Object[0]), mouseX, mouseY);
    }
}
