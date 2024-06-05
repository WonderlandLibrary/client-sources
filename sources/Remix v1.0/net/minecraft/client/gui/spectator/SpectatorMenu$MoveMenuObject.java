package net.minecraft.client.gui.spectator;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

static class MoveMenuObject implements ISpectatorMenuObject
{
    private final int field_178666_a;
    private final boolean field_178665_b;
    
    public MoveMenuObject(final int p_i45495_1_, final boolean p_i45495_2_) {
        this.field_178666_a = p_i45495_1_;
        this.field_178665_b = p_i45495_2_;
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu p_178661_1_) {
        SpectatorMenu.access$002(p_178661_1_, this.field_178666_a);
    }
    
    @Override
    public IChatComponent func_178664_z_() {
        return (this.field_178666_a < 0) ? new ChatComponentText("Previous Page") : new ChatComponentText("Next Page");
    }
    
    @Override
    public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        if (this.field_178666_a < 0) {
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 144.0f, 0.0f, 16, 16, 256.0f, 256.0f);
        }
        else {
            Gui.drawModalRectWithCustomSizedTexture(0, 0, 160.0f, 0.0f, 16, 16, 256.0f, 256.0f);
        }
    }
    
    @Override
    public boolean func_178662_A_() {
        return this.field_178665_b;
    }
}
