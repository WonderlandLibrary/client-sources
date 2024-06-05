package net.minecraft.client.gui.inventory;

import net.minecraft.potion.*;
import net.minecraft.client.resources.*;

class PowerButton extends Button
{
    private final int field_146149_p;
    private final int field_146148_q;
    
    public PowerButton(final int p_i1076_2_, final int p_i1076_3_, final int p_i1076_4_, final int p_i1076_5_, final int p_i1076_6_) {
        super(p_i1076_2_, p_i1076_3_, p_i1076_4_, GuiContainer.inventoryBackground, 0 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() % 8 * 18, 198 + Potion.potionTypes[p_i1076_5_].getStatusIconIndex() / 8 * 18);
        this.field_146149_p = p_i1076_5_;
        this.field_146148_q = p_i1076_6_;
    }
    
    @Override
    public void drawButtonForegroundLayer(final int mouseX, final int mouseY) {
        String var3 = I18n.format(Potion.potionTypes[this.field_146149_p].getName(), new Object[0]);
        if (this.field_146148_q >= 3 && this.field_146149_p != Potion.regeneration.id) {
            var3 += " II";
        }
        GuiBeacon.access$300(GuiBeacon.this, var3, mouseX, mouseY);
    }
}
