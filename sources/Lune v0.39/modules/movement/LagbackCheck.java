package me.superskidder.lune.modules.movement;

import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventPacketReceive;
import me.superskidder.lune.guis.notification.Notification;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.client.ClientUtils;
import me.superskidder.lune.values.type.Bool;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class LagbackCheck extends Mod {
    private Bool<Boolean> fly = new Bool<>("Fly", true);
    private Bool<Boolean> speed = new Bool<>("Speed", true);
    //private Num<Float> lagtime = new Num<Float>("LagTime", 30f, 1f, 100.0f);

    public LagbackCheck() {
        super("LagBackCheck", ModCategory.Movement, "LagBack");
        this.addValues(speed, fly);
    }

    @EventTarget
    private void onReceived(EventPacketReceive event) {
        if (!(event.getPacket() instanceof S08PacketPlayerPosLook)) {
            return;
        }

        if (Lune.moduleManager.getModByClass(Fly.class).getState() && fly.getValue()) {
            Lune.moduleManager.getModByClass(Fly.class).setStage(false);
            ClientUtils.sendClientMessage("Lagback (Fly)", Notification.Type.ERROR);
        }

        if (this.speed.getValue() && speed.getValue()) {
            if (ModuleManager.getModByClass(Speed.class).getState()) {
                ModuleManager.getModByClass(Speed.class).setStage(false);
                //Speed.lagTime = lagtime.value.intValue();
            }
        }
    }
}
