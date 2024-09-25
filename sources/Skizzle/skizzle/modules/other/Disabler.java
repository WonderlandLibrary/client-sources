/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other;

import java.util.ArrayList;
import java.util.List;
import net.minecraft.network.ThreadQuickExitException;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import skizzle.events.Event;
import skizzle.events.listeners.EventMotion;
import skizzle.events.listeners.EventPacket;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.util.Timer;

public class Disabler
extends Module {
    public List<S12PacketEntityVelocity> packets;
    public double posY;
    public ModeSetting mode;
    public Timer timer = new Timer();
    public double posZ;
    public boolean sent;
    public double posX;

    public static {
        throw throwable;
    }

    @Override
    public void onEvent(Event Nigga) {
        Disabler Nigga2;
        if (Nigga instanceof EventPacket) {
            EventPacket Nigga3 = (EventPacket)Nigga;
            if (Nigga2.mode.getMode().equals(Qprot0.0("\ub885\u71e8\u83ef")) && Nigga3.getPacket() instanceof S12PacketEntityVelocity && Nigga3.isIncoming()) {
                S12PacketEntityVelocity Nigga4 = (S12PacketEntityVelocity)Nigga3.getPacket();
                if (Nigga4.entityID == Nigga2.mc.thePlayer.getEntityId()) {
                    Nigga2.packets.add(Nigga4);
                    Nigga3.setCancelled(true);
                    for (S12PacketEntityVelocity Nigga5 : Nigga2.packets) {
                        for (int Nigga6 = 0; Nigga6 < 5; ++Nigga6) {
                            try {
                                Nigga5.processPacket(Nigga2.mc.getNetHandler());
                                continue;
                            }
                            catch (ThreadQuickExitException threadQuickExitException) {}
                        }
                    }
                    Nigga2.packets.clear();
                }
            }
        }
        if (Nigga instanceof EventUpdate && Nigga2.mode.getMode().equals(Qprot0.0("\ub885\u71e8\u83ef")) && Nigga2.mc.thePlayer.ticksExisted < 2) {
            Nigga2.packets.clear();
        }
        if (Nigga instanceof EventMotion && Nigga2.mode.getMode().equals(Qprot0.0("\ub88a\u71ea\u83fc")) && Nigga2.mc.thePlayer.fallDistance > Float.intBitsToFloat(1.06134528E9f ^ 0x7F02D7FC)) {
            Nigga2.mc.thePlayer.setSneaking(true);
        }
    }

    public Disabler() {
        super(Qprot0.0("\ub88f\u71c2\u83cc\ua7e5\u927a\u3d1d\u8c2a\ud4f4"), 0, Module.Category.OTHER);
        Disabler Nigga;
        Nigga.packets = new ArrayList<S12PacketEntityVelocity>();
        Nigga.mode = new ModeSetting(Qprot0.0("\ub886\u71c4\u83db\ua7e1"), Qprot0.0("\ub885\u71e8\u83ef"), Qprot0.0("\ub885\u71e8\u83ef"), Qprot0.0("\ub88a\u71ea\u83fc"));
        Nigga.addSettings(Nigga.mode);
    }
}

