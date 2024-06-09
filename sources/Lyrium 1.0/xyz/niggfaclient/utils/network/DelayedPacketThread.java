// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.utils.network;

import net.minecraft.network.Packet;

public class DelayedPacketThread extends Thread
{
    private Packet packet;
    private long delay;
    
    public DelayedPacketThread(final Packet packet, final long delay) {
        this.packet = packet;
        this.delay = delay;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(this.delay);
        }
        catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            PacketUtil.sendPacketNoEvent(this.packet);
        }
        catch (Exception e2) {
            e2.printStackTrace();
        }
    }
    
    public long getDelay() {
        return this.delay;
    }
    
    public void setDelay(final long delay) {
        this.delay = delay;
    }
    
    public Packet getPacket() {
        return this.packet;
    }
    
    public void setPacket(final Packet packet) {
        this.packet = packet;
    }
}
