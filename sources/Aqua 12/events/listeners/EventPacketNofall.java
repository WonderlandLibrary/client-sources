// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import net.minecraft.network.EnumPacketDirection;
import net.minecraft.network.INetHandler;
import net.minecraft.network.Packet;
import events.Event;

public class EventPacketNofall extends Event
{
    public static Packet packet;
    private boolean cancel;
    private Action eventAction;
    private INetHandler netHandler;
    private EnumPacketDirection packetDirection;
    
    public EventPacketNofall(final Action action, final Packet packet, final INetHandler netHandler, final EnumPacketDirection direction) {
        this.eventAction = action;
        EventPacketNofall.packet = packet;
        this.netHandler = netHandler;
        this.packetDirection = direction;
    }
    
    public INetHandler getNetHandler() {
        return this.netHandler;
    }
    
    public void setNetHandler(final INetHandler netHandler) {
        this.netHandler = netHandler;
    }
    
    @Override
    public EnumPacketDirection getDirection() {
        return this.packetDirection;
    }
    
    public static Packet getPacket() {
        return EventPacketNofall.packet;
    }
    
    public void setPacket(final Packet packet) {
        EventPacketNofall.packet = packet;
    }
    
    @Override
    public boolean isCancelled() {
        return this.cancel;
    }
    
    @Override
    public void setCancelled(final boolean cancel) {
        this.cancel = cancel;
    }
    
    public Action getEventAction() {
        return this.eventAction;
    }
    
    public boolean isSend() {
        return this.getEventAction() == Action.SEND;
    }
    
    public boolean isReceive() {
        return this.getEventAction() == Action.RECEIVE;
    }
    
    public enum Action
    {
        SEND, 
        RECEIVE;
    }
}
