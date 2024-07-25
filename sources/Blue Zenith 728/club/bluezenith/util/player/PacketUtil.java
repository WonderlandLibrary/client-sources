package club.bluezenith.util.player;

import club.bluezenith.BlueZenith;
import club.bluezenith.util.MinecraftInstance;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.network.Packet;

public final class PacketUtil extends MinecraftInstance {

    public static void send(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacket(packet);
    }
    public static void sendSilent(Packet packet) {
        mc.getNetHandler().getNetworkManager().sendPacketNoEvent(packet);
    }
    public static ServerData data = null;
    public static void reconnect(){
        if(mc.theWorld != null) mc.theWorld.sendQuittingDisconnectingPacket();

        if(data != null && !(mc.currentScreen instanceof GuiConnecting)){
            mc.displayGuiScreen(new GuiConnecting(new GuiMultiplayer(BlueZenith.getBlueZenith().getMainMenu()), mc, data));
        }
    }
}
