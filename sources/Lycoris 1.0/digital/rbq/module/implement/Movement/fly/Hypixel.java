package digital.rbq.module.implement.Movement.fly;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import org.apache.logging.log4j.core.appender.ConsoleAppender;
import digital.rbq.addon.api.event.events.EventMove;
import digital.rbq.addon.api.event.events.EventPreUpdate;
import digital.rbq.event.PacketReceiveEvent;
import digital.rbq.event.PreUpdateEvent;
import digital.rbq.module.Module;
import digital.rbq.module.SubModule;
import digital.rbq.utility.MoveUtils;
import digital.rbq.utility.PacketUtils;
import digital.rbq.utility.PlayerUtils;

public class Hypixel extends SubModule {
    public Hypixel() {
        super("Hypixel", "Fly");
    }

    private int ticks = 0;
    private boolean shouldFly = false;


    @EventTarget
    public void onUpdate(PreUpdateEvent e) {
        ticks++;
        if (ticks == 1) {
            mc.thePlayer.motionY = 0.1;
        } else if (ticks == 3) {
            PacketUtils.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(null));
            PacketUtils.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                    mc.thePlayer.posY,
                    mc.thePlayer.posZ,
                    true));
            e.setY(e.getY() - 0.2);
        }
        if (shouldFly) {
            mc.thePlayer.motionY = 0 + (Math.random() / 500);
            PlayerUtils.setSpeed(MoveUtils.getBaseMoveSpeed());
        } else {
            PlayerUtils.setSpeed(0.1);
        }
    }

    @EventTarget
    public void onLagback(PacketReceiveEvent e) {
        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            shouldFly = true;
        }
    }

    @Override
    public void onEnable() {
        ticks = 0;
        shouldFly = false;
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }
}