// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import net.minecraft.network.Packet;
import xyz.niggfaclient.utils.network.PacketUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import xyz.niggfaclient.utils.player.PlayerUtil;
import xyz.niggfaclient.module.ModuleManager;
import xyz.niggfaclient.property.impl.Representation;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "AntiVoid", description = "Saves you from void", cat = Category.MOVEMENT)
public class AntiVoid extends Module
{
    private final EnumProperty<Mode> mode;
    private final DoubleProperty distance;
    private final Property<Boolean> noDamage;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    
    public AntiVoid() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Packet);
        this.distance = new DoubleProperty("Distance", 5.0, 3.0, 10.0, 0.5, Representation.DISTANCE);
        this.noDamage = new Property<Boolean>("No Damage", true);
        this.motionEventListener = (e -> {
            this.setDisplayName(this.getName() + " §7" + this.mode.getValue());
            if (!ModuleManager.getModule(Flight.class).isEnabled() && this.mc.thePlayer.fallDistance >= this.distance.getValue().floatValue() && !PlayerUtil.isBlockUnder()) {
                switch (this.mode.getValue()) {
                    case Packet: {
                        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(e.getX(), e.getY() + 14.0, e.getZ(), e.getYaw(), e.getPitch(), false));
                        break;
                    }
                    case BlocksMC: {
                        PacketUtil.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(e.getX(), e.getY() + 2.5, e.getZ(), e.getYaw(), e.getPitch(), false));
                        break;
                    }
                }
                if (this.noDamage.getValue()) {
                    this.mc.thePlayer.fallDistance = 0.0f;
                }
            }
        });
    }
    
    public enum Mode
    {
        Packet, 
        BlocksMC;
    }
}
