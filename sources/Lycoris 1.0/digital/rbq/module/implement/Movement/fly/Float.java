package digital.rbq.module.implement.Movement.fly;

import com.darkmagician6.eventapi.EventTarget;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import digital.rbq.event.PreUpdateEvent;
import digital.rbq.module.implement.Combat.Criticals;
import digital.rbq.module.implement.Movement.Speed;
import digital.rbq.event.MoveEvent;
import digital.rbq.module.SubModule;
import digital.rbq.utility.EntityUtils;
import digital.rbq.utility.PlayerUtils;

import java.util.Random;

/**
 * Created by John on 2017/06/27.
 */
public class Float extends SubModule {
    public Float() {
        super("Float", "Fly");
    }

    @EventTarget
    public void onMove2(MoveEvent event) {
        event.setOnGround(true);
        mc.thePlayer.motionY = 0;
        event.setY(0);
        Speed.setMoveSpeed(event, Speed.getBaseMoveSpeed());
    }

    @EventTarget
    public void onPre(PreUpdateEvent e) {
        e.setOnGround(true);
    }

    @Override
    public void onEnable() {
        super.onEnable();
    }

    @Override
    public void onDisable() {
        super.onDisable();

    }
}