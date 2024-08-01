package wtf.diablo.client.scripting.impl.data.functions.packet;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;

public final class PacketScriptingFunction {
    private final Minecraft minecraft = Minecraft.getMinecraft();

    public void sendPacket(final Packet<?> packet) {
        this.minecraft.getNetHandler().addToSendQueue(packet);
    }

    public void sendPacketWithoutEvent(final Packet<?> packet) {
        this.minecraft.getNetHandler().addToSendQueueNoEvent(packet);
    }
}