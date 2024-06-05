package club.mega.module.impl.player;

import club.mega.event.impl.EventPacket;
import club.mega.event.impl.EventTick;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.util.RandomUtil;
import club.mega.util.TimeUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import rip.hippo.lwjeb.annotation.Handler;

import java.util.ArrayList;

@Module.ModuleInfo(name = "Disabler", description = "Disables every single anicheat in the fucking world lmao", category = Category.PLAYER)
public class Disabler extends Module {

    private ArrayList<Packet<?>> packets = new ArrayList<>();
    private final TimeUtil timer = new TimeUtil();

    @Handler
    public void tick(final EventTick event) {
        if (MC.thePlayer.ticksExisted <= 1)
            packets.clear();

        if (timer.hasTimePassed((long) (500 + RandomUtil.getRandomNumber(0, 300))) && !packets.isEmpty()) {
            MC.thePlayer.sendQueue.addToSendQueue(packets.get(0));
            packets.remove(0);
            timer.reset();
        }
    }

    @Handler
    public void packet(final EventPacket event) {
        if (event.getPacket() instanceof C0FPacketConfirmTransaction || event.getPacket() instanceof C00PacketKeepAlive) {
            packets.add(event.getPacket());
            event.setCancelled(true);
        }
    }

}
