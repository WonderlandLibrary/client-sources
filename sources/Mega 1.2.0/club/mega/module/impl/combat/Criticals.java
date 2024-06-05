package club.mega.module.impl.combat;

import club.mega.event.impl.EventPacket;
import club.mega.event.impl.EventPreTick;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.setting.impl.ListSetting;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import rip.hippo.lwjeb.annotation.Handler;

@Module.ModuleInfo(name = "Criticals", description = "Always land Criticals", category = Category.COMBAT)
public class Criticals extends Module {

    private final ListSetting mode = new ListSetting("Mode", this, new String[]{"Packet", "No Ground"});

    @Handler
    public final void packet(final EventPacket event) {
        if (mode.is("packet"))
        if (event.getPacket() instanceof C02PacketUseEntity) {
            final C02PacketUseEntity packet = (C02PacketUseEntity) event.getPacket();

            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK) {
                MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, MC.thePlayer.posY + 2.001, MC.thePlayer.posZ, false));
                MC.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(MC.thePlayer.posX, MC.thePlayer.posY, MC.thePlayer.posZ, true));
            }
        }
    }

    @Handler
    public final void preTick(final EventPreTick event) {
        if (mode.is("No Ground"))
        event.setOnGround(false);
    }

}
