// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.misc;

import io.netty.buffer.Unpooled;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.IChatComponent;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.util.ChatComponentText;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "ClientSpoofer", description = "Makes servers think you're on a specified client", cat = Category.MISC)
public class ClientSpoofer extends Module
{
    private final EnumProperty<Mode> mode;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    
    public ClientSpoofer() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Lunar);
        C17PacketCustomPayload packet;
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.SEND && e.getPacket() instanceof C17PacketCustomPayload) {
                packet = (C17PacketCustomPayload)e.getPacket();
                if (!this.mc.isSingleplayer()) {
                    switch (this.mode.getValue()) {
                        case Lunar: {
                            packet.setChannel("REGISTER");
                            packet.setData(this.createPacketBuffer("Lunar-Client", false));
                            break;
                        }
                        case Forge: {
                            packet.setData(this.createPacketBuffer("FML", true));
                            break;
                        }
                    }
                }
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        if (!this.mc.isSingleplayer()) {
            this.mc.thePlayer.sendQueue.handleDisconnect(new S40PacketDisconnect(new ChatComponentText("Rejoin for ClientSpoofer work.")));
        }
    }
    
    private PacketBuffer createPacketBuffer(final String data, final boolean string) {
        if (string) {
            return new PacketBuffer(Unpooled.buffer()).writeString(data);
        }
        return new PacketBuffer(Unpooled.wrappedBuffer(data.getBytes()));
    }
    
    public enum Mode
    {
        Lunar, 
        Forge;
    }
}
