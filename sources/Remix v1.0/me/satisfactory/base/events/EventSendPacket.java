package me.satisfactory.base.events;

import me.satisfactory.base.events.event.callables.*;
import me.satisfactory.base.events.event.*;
import net.minecraft.network.*;

public class EventSendPacket extends EventCancellable implements Event
{
    private Packet packet;
    private SendProgress progress;
    
    public EventSendPacket(final Packet packet, final SendProgress progress) {
        this.packet = packet;
        this.progress = progress;
    }
    
    public SendProgress getProgress() {
        return this.progress;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
    
    public enum SendProgress
    {
        PRE, 
        POST;
    }
}
