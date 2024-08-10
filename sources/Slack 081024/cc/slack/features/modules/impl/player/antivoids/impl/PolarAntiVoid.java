package cc.slack.features.modules.impl.player.antivoids.impl;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.features.modules.impl.player.antivoids.IAntiVoid;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.Vec3;

public class PolarAntiVoid implements IAntiVoid {

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof C03PacketPlayer) {
            if (mc.thePlayer.fallDistance > 7 && isOverVoid()) {
                event.cancel();
            }
        }
    }

    private boolean isOverVoid() {
        return mc.theWorld.rayTraceBlocks(
                new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                new Vec3(mc.thePlayer.posX, mc.thePlayer.posY - 40, mc.thePlayer.posZ),
                true, true, false) == null;
    }

    @Override
    public String toString() {
        return "Polar";
    }
}
