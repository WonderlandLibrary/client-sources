// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.movement;

import me.chrest.event.EventTarget;
import net.minecraft.network.play.client.C03PacketPlayer;
import me.chrest.event.events.PacketSendEvent;
import me.chrest.event.EventManager;
import net.minecraft.init.Blocks;
import net.minecraft.util.MathHelper;
import me.chrest.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import me.chrest.client.module.Module;

@Mod(displayName = "NewNoFall")
public class NoFall extends Module
{
    Minecraft mc;
    
    public NoFall() {
        this.mc = Minecraft.getMinecraft();
    }
    
    public int getBlocks() {
        int py = MathHelper.floor_double(ClientUtils.mc().thePlayer.posY);
        final int i = 0;
        for (int y = (int)ClientUtils.mc().thePlayer.posY; y > 12; --y) {
            if (ClientUtils.mc().theWorld.getBlock(MathHelper.floor_double(ClientUtils.mc().thePlayer.posX), y, MathHelper.floor_double(ClientUtils.mc().thePlayer.posZ)) == Blocks.air) {
                --py;
            }
        }
        return py;
    }
    
    public void onToggle() {
        if (this.isToggled()) {
            EventManager.register(this);
        }
        else {
            EventManager.unregister(this);
        }
    }
    
    private boolean isToggled() {
        return false;
    }
    
    @EventTarget
    public void onPacket(final PacketSendEvent e) {
        if (e.getPacket() instanceof C03PacketPlayer && ClientUtils.mc().thePlayer.fallDistance >= 2.0f) {
            final C03PacketPlayer c03PacketPlayer = (C03PacketPlayer)e.getPacket();
        }
    }
}
