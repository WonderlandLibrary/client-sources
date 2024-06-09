// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.player;

import java.util.Iterator;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import xyz.niggfaclient.property.impl.Representation;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.utils.other.TimerUtil;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.Property;
import net.minecraft.network.Packet;
import java.util.ArrayList;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Blink", description = "Lags you serverside", cat = Category.PLAYER)
public class Blink extends Module
{
    public static ArrayList<Packet<?>> p;
    private final Property<Boolean> pulse;
    private final DoubleProperty delay;
    private final TimerUtil timer;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    
    public Blink() {
        this.pulse = new Property<Boolean>("Pulse", false);
        this.delay = new DoubleProperty("Pulse Delay", 1000.0, 20.0, 5000.0, 20.0, Representation.MILLISECONDS);
        this.timer = new TimerUtil();
        this.packetEventListener = (e -> {
            if (e.getState() == PacketEvent.State.SEND) {
                if (this.mc.thePlayer != null && this.mc.theWorld != null) {
                    if (!(e.getPacket() instanceof C00PacketKeepAlive) && !(e.getPacket() instanceof C00Handshake) && !(e.getPacket() instanceof C00PacketLoginStart)) {
                        Blink.p.add(e.getPacket());
                        e.setCancelled();
                    }
                }
            }
            return;
        });
        final Iterator<Packet<?>> iterator;
        Packet<?> packet;
        this.motionEventListener = (e -> {
            if (this.pulse.getValue() && this.timer.hasTimeElapsed(this.delay.getValue().longValue(), true)) {
                Blink.p.iterator();
                while (iterator.hasNext()) {
                    packet = iterator.next();
                    if (!this.mc.isSingleplayer()) {
                        PacketUtil.sendPacketNoEvent(packet);
                    }
                }
                Blink.p.clear();
            }
        });
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
        Blink.p.clear();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
        for (final Packet<?> packet : Blink.p) {
            if (!this.mc.isSingleplayer()) {
                PacketUtil.sendPacketNoEvent(packet);
            }
        }
        Blink.p.clear();
    }
    
    static {
        Blink.p = new ArrayList<Packet<?>>();
    }
}
