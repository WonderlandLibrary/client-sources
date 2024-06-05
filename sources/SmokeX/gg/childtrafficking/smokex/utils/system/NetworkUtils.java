// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.system;

import net.minecraft.client.Minecraft;
import gg.childtrafficking.smokex.module.ModuleManager;
import gg.childtrafficking.smokex.module.modules.exploit.DisablerModule;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.Packet;

public class NetworkUtils
{
    public static void sendPacket(final Packet<?> packet) {
        if (packet instanceof C03PacketPlayer) {
            final C03PacketPlayer paccet = (C03PacketPlayer)packet;
            final DisablerModule disablerModule = ModuleManager.getInstance(DisablerModule.class);
            if (disablerModule.getPosX() == paccet.getPositionX() && disablerModule.getPosY() == paccet.getPositionY() && disablerModule.getPosZ() == paccet.getPositionZ()) {
                return;
            }
        }
        Minecraft.getMinecraft().getNetHandler().getNetworkManager().sendPacket(packet);
    }
}
