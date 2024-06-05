package net.minecraft.client.gui.spectator;

import net.minecraft.util.*;
import net.minecraft.client.*;
import net.minecraft.client.gui.*;

static class EndSpectatorObject implements ISpectatorMenuObject
{
    private EndSpectatorObject() {
    }
    
    EndSpectatorObject(final Object p_i45496_1_) {
        this();
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu p_178661_1_) {
        p_178661_1_.func_178641_d();
    }
    
    @Override
    public IChatComponent func_178664_z_() {
        return new ChatComponentText("Close menu");
    }
    
    @Override
    public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(GuiSpectator.field_175269_a);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 128.0f, 0.0f, 16, 16, 256.0f, 256.0f);
    }
    
    @Override
    public boolean func_178662_A_() {
        return true;
    }
}
