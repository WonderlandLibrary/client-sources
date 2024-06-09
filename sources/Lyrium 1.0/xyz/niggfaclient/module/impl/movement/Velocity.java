// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.module.impl.movement;

import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S27PacketExplosion;
import xyz.niggfaclient.utils.other.Printer;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import xyz.niggfaclient.property.impl.Representation;
import xyz.niggfaclient.events.impl.MotionEvent;
import xyz.niggfaclient.eventbus.annotations.EventLink;
import xyz.niggfaclient.events.impl.PacketEvent;
import xyz.niggfaclient.eventbus.Listener;
import xyz.niggfaclient.property.Property;
import xyz.niggfaclient.property.impl.DoubleProperty;
import xyz.niggfaclient.property.impl.EnumProperty;
import xyz.niggfaclient.module.Category;
import xyz.niggfaclient.module.ModuleInfo;
import xyz.niggfaclient.module.Module;

@ModuleInfo(name = "Velocity", description = "Changes Knockback", cat = Category.MOVEMENT)
public class Velocity extends Module
{
    private final EnumProperty<Mode> mode;
    private final DoubleProperty horizontal;
    private final DoubleProperty vertical;
    private final Property<Boolean> debug;
    @EventLink
    private final Listener<PacketEvent> packetEventListener;
    @EventLink
    private final Listener<MotionEvent> motionEventListener;
    
    public Velocity() {
        this.mode = new EnumProperty<Mode>("Mode", Mode.Cancel);
        this.horizontal = new DoubleProperty("Horizontal", 0.0, 0.0, 100.0, 1.0, Representation.PERCENTAGE);
        this.vertical = new DoubleProperty("Vertical", 0.0, 0.0, 100.0, 1.0, Representation.PERCENTAGE);
        this.debug = new Property<Boolean>("Debug", false);
        S12PacketEntityVelocity s12;
        double x;
        double y;
        double z;
        S12PacketEntityVelocity s13;
        int sHorizontal;
        int sVertical;
        S12PacketEntityVelocity s14;
        S12PacketEntityVelocity s15;
        S12PacketEntityVelocity s16;
        final S12PacketEntityVelocity s12PacketEntityVelocity;
        final S12PacketEntityVelocity s12PacketEntityVelocity2;
        this.packetEventListener = (e -> {
            if ((this.mc.thePlayer != null || this.mc.theWorld != null) && e.getState() == PacketEvent.State.RECEIVE) {
                if (this.debug.getValue() && e.getPacket() instanceof S12PacketEntityVelocity) {
                    s12 = (S12PacketEntityVelocity)e.getPacket();
                    x = s12.getMotionX() / 8000.0;
                    y = s12.getMotionX() / 8000.0;
                    z = s12.getMotionX() / 8000.0;
                    if (x != 0.0 && y != 0.0 && z != 0.0 && s12.getEntityID() == this.mc.thePlayer.getEntityId()) {
                        Printer.addMessage("Knockback taken (x: " + x + " y: " + y + " z: " + z + ")");
                    }
                }
                switch (this.mode.getValue()) {
                    case Vulcan:
                    case Cancel: {
                        if (e.getPacket() instanceof S12PacketEntityVelocity) {
                            s13 = (S12PacketEntityVelocity)e.getPacket();
                            if (s13.getEntityID() == this.mc.thePlayer.getEntityId()) {
                                e.setCancelled();
                            }
                        }
                        if (e.getPacket() instanceof S27PacketExplosion) {
                            e.setCancelled();
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Custom: {
                        sHorizontal = this.horizontal.getValue().intValue();
                        sVertical = this.vertical.getValue().intValue();
                        if (e.getPacket() instanceof S12PacketEntityVelocity) {
                            s14 = (S12PacketEntityVelocity)e.getPacket();
                            if (s14.getEntityID() == this.mc.thePlayer.getEntityId()) {
                                if (sVertical != 0 || sHorizontal != 0) {
                                    s14.setX(sHorizontal * s14.getMotionX() / 100);
                                    s14.setY(sVertical * s14.getMotionY() / 100);
                                    s14.setZ(sHorizontal * s14.getMotionZ() / 100);
                                }
                                else {
                                    e.setCancelled();
                                }
                            }
                        }
                        if (e.getPacket() instanceof S27PacketExplosion) {
                            e.setCancelled();
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Hypixel: {
                        if (e.getPacket() instanceof S12PacketEntityVelocity) {
                            s15 = (S12PacketEntityVelocity)e.getPacket();
                            if (s15.getEntityID() == this.mc.thePlayer.getEntityId()) {
                                s15.setX(0);
                                s15.setY(100 * s15.getMotionY() / 100);
                                s15.setZ(0);
                            }
                        }
                        if (e.getPacket() instanceof S27PacketExplosion) {
                            e.setCancelled();
                            break;
                        }
                        else {
                            break;
                        }
                        break;
                    }
                    case Reverse: {
                        if (e.getPacket() instanceof S12PacketEntityVelocity) {
                            s16 = (S12PacketEntityVelocity)e.getPacket();
                            if (s16.getEntityID() == this.mc.thePlayer.getEntityId()) {
                                s12PacketEntityVelocity.motionX *= -1;
                                s12PacketEntityVelocity2.motionZ *= -1;
                                e.setPacket(s16);
                                break;
                            }
                            else {
                                break;
                            }
                        }
                        else {
                            break;
                        }
                        break;
                    }
                }
            }
            return;
        });
        this.motionEventListener = (e -> {
            if (this.mode.getValue() == Mode.Custom) {
                this.setDisplayName(this.getName() + " §7H: " + ((Property<Object>)this.horizontal).getValue() + "% V: " + ((Property<Object>)this.vertical).getValue() + "%");
            }
            else {
                this.setDisplayName(this.getName() + " §7" + this.mode.getValue());
            }
        });
    }
    
    public enum Mode
    {
        Cancel, 
        Custom, 
        Vulcan, 
        Reverse, 
        Hypixel;
    }
}
