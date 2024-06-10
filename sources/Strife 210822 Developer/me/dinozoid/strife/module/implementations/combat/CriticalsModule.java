package me.dinozoid.strife.module.implementations.combat;

import me.dinozoid.strife.alpine.event.EventState;
import me.dinozoid.strife.alpine.listener.EventHandler;
import me.dinozoid.strife.alpine.listener.Listener;
import me.dinozoid.strife.event.implementations.network.PacketOutboundEvent;
import me.dinozoid.strife.event.implementations.player.UpdatePlayerEvent;
import me.dinozoid.strife.module.Category;
import me.dinozoid.strife.module.Module;
import me.dinozoid.strife.module.ModuleInfo;
import me.dinozoid.strife.module.implementations.movement.FlightModule;
import me.dinozoid.strife.module.implementations.movement.SpeedModule;
import me.dinozoid.strife.util.player.MovementUtil;
import me.dinozoid.strife.util.system.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.MovingObjectPosition;
import org.apache.commons.lang3.RandomUtils;

import java.util.concurrent.ThreadLocalRandom;

@ModuleInfo(name = "Criticals", renderName = "Criticals", description = "Always do a critical hit.", category = Category.COMBAT)
public class CriticalsModule extends Module {

    private TimerUtil timer = new TimerUtil();
    private final double[] offsets = {0.0625f, 0.016f, 0.003f};
    private int groundTicks;

    @EventHandler
    private final Listener<UpdatePlayerEvent> updatePlayerListener = new Listener<>(event -> {
        if (event.state() == EventState.PRE) groundTicks = MovementUtil.isOnGround() ? groundTicks + 1 : 0;
    });

    @EventHandler
    private final Listener<PacketOutboundEvent> packetListener = new Listener<>(event -> {
        if(event.packet() instanceof C0APacketAnimation && hasTarget() && !SpeedModule.instance().toggled() && !FlightModule.instance().toggled()) {
            if(timer.hasElapsed(490)) {
                if(groundTicks > 1) {
                    for(double offset : offsets) {
                        mc.getNetHandler().getNetworkManager().sendPacket(
                                new C03PacketPlayer.C04PacketPlayerPosition(
                                        mc.thePlayer.posX,
                                        mc.thePlayer.posY + offset * Math.random(),
                                        mc.thePlayer.posZ,
                                        false));
                    }
                    mc.thePlayer.setPosition(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
                }
                timer.reset();
            }
        }
    });

    private boolean hasTarget() {
        final MovingObjectPosition target = Minecraft.getMinecraft().objectMouseOver;
        return target != null && target.entityHit != null;
    }

}
