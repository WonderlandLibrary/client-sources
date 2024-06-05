package net.minecraft.client.gui.spectator;

import com.mojang.authlib.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import net.minecraft.util.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.gui.*;

public class PlayerMenuObject implements ISpectatorMenuObject
{
    private final GameProfile field_178668_a;
    private final ResourceLocation field_178667_b;
    
    public PlayerMenuObject(final GameProfile p_i45498_1_) {
        this.field_178668_a = p_i45498_1_;
        AbstractClientPlayer.getDownloadImageSkin(this.field_178667_b = AbstractClientPlayer.getLocationSkin(p_i45498_1_.getName()), p_i45498_1_.getName());
    }
    
    @Override
    public void func_178661_a(final SpectatorMenu p_178661_1_) {
        Minecraft.getMinecraft().getNetHandler().addToSendQueue(new C18PacketSpectate(this.field_178668_a.getId()));
    }
    
    @Override
    public IChatComponent func_178664_z_() {
        return new ChatComponentText(this.field_178668_a.getName());
    }
    
    @Override
    public void func_178663_a(final float p_178663_1_, final int p_178663_2_) {
        Minecraft.getMinecraft().getTextureManager().bindTexture(this.field_178667_b);
        GlStateManager.color(1.0f, 1.0f, 1.0f, p_178663_2_ / 255.0f);
        Gui.drawScaledCustomSizeModalRect(2.0, 2.0, 8.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0f, 64.0f);
        Gui.drawScaledCustomSizeModalRect(2.0, 2.0, 40.0f, 8.0f, 8.0, 8.0, 12.0, 12.0, 64.0f, 64.0f);
    }
    
    @Override
    public boolean func_178662_A_() {
        return true;
    }
}
