// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.event.events;

import net.minecraftforge.fml.common.eventhandler.Cancelable;
import net.minecraft.network.Packet;
import me.perry.mcdonalds.event.EventStage;

public class PacketEvent extends EventStage
{
    private final Packet<?> packet;
    
    public PacketEvent(final int stage, final Packet<?> packet) {
        super(stage);
        this.packet = packet;
    }
    
    public <T extends Packet<?>> T getPacket() {
        return (T)this.packet;
    }
    
    @Cancelable
    public static class Send extends PacketEvent
    {
        public Send(final int stage, final Packet<?> packet) {
            super(stage, packet);
        }
    }
    
    @Cancelable
    public static class Receive extends PacketEvent
    {
        public Receive(final int stage, final Packet<?> packet) {
            super(stage, packet);
        }
    }
}
