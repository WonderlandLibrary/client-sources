// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.player.nofalls.specials;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.player.nofalls.INoFall;
import net.minecraft.network.play.client.C03PacketPlayer;

public class VerusNofall implements INoFall {


    boolean spoof;
    int packet1Count;
    boolean packetModify;

    @Override
    public void onEnable() {
        packetModify = false;
        packet1Count = 0;
        spoof = false;
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.fallDistance - mc.thePlayer.motionY > 3) {
            mc.thePlayer.motionY = 0.0D;
            mc.thePlayer.motionX *= 0.5D;
            mc.thePlayer.motionZ *= 0.5D;
            mc.thePlayer.fallDistance = 0F;
            spoof = true;
        }
        if (mc.thePlayer.fallDistance / 3 > packet1Count) {
            packet1Count = (int) (mc.thePlayer.fallDistance / 3);
            packetModify = true;
        }
        if (mc.thePlayer.onGround) {
            packet1Count = 0;
        }

    }

    @Override
    public void onPacket(PacketEvent event) {
        if (spoof && event.getPacket() instanceof C03PacketPlayer) {
            ((C03PacketPlayer) event.getPacket()).onGround = true;
            spoof = false;
        }
    }

    public String toString() {
        return "Verus";
    }
}
