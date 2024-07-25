package club.bluezenith.module.modules.combat;

import club.bluezenith.events.Listener;
import club.bluezenith.events.impl.PacketEvent;
import club.bluezenith.module.Module;
import club.bluezenith.module.ModuleCategory;
import club.bluezenith.module.value.types.BooleanValue;
import club.bluezenith.module.value.types.FloatValue;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S27PacketExplosion;

public class Velocity extends Module {
    public Velocity() {
        super("Velocity", ModuleCategory.COMBAT);
    }
    private final FloatValue horizontal = new FloatValue("Horizontal", 100F, 0F, 100F, 1F).setIndex(1);
    private final FloatValue vertical = new FloatValue("Vertical", 100F, 0F, 100F, 1F).setIndex(2);
    private final BooleanValue explosions = new BooleanValue("Explosions", true).setIndex(3);

    @Listener
    public void onPacket(PacketEvent event) {
        if(mc.thePlayer == null) return;

        final Packet<?> packet = event.packet;

        if(packet instanceof S12PacketEntityVelocity) {

            S12PacketEntityVelocity s12 = (S12PacketEntityVelocity) packet;
            if(s12.getEntityID() == mc.thePlayer.getEntityId()) {
                s12.motionX *= (horizontal.get() / 100.0);
                s12.motionZ *= (horizontal.get() / 100.0);
                s12.motionY *= (vertical.get() / 100.0);
                event.cancel();
            }

        }
        if(packet instanceof S27PacketExplosion && explosions.get()) {
            S27PacketExplosion s27 = (S27PacketExplosion) packet;
            if(horizontal.get() > 0.0 && vertical.get() > 0.0) {
                mc.thePlayer.motionX += s27.getMotionX() * horizontal.get() / 100.0;
                mc.thePlayer.motionY += s27.getMotionY() * vertical.get() / 100.0;
                mc.thePlayer.motionZ += s27.getMotionZ() * horizontal.get() / 100.0;
            }
            event.cancel();
        }
    }

    @Override
    public String getTag() {
        return (horizontal.get() == 0 && vertical.get() == 0) ? "Cancel" : horizontal.get() + "% " + vertical.get() + "%";
    }
}
