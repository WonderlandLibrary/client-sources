package cn.Hanabi.events;

import com.darkmagician6.eventapi.events.callables.*;
import com.darkmagician6.eventapi.types.*;
import net.minecraft.network.*;
import net.minecraft.network.play.server.*;
import com.darkmagician6.eventapi.*;
import com.darkmagician6.eventapi.events.*;

public class EventPacket extends EventCancellable
{
    private final EventType eventType;
    public Packet packet;
    
    public EventPacket(final EventType eventType, final Packet packet) {
        this.eventType = eventType;
        this.packet = packet;
        if (this.packet instanceof S08PacketPlayerPosLook) {
            EventManager.call(new EventPullback());
        }
    }
    
    public EventType getEventType() {
        return this.eventType;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
