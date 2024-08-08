package me.napoleon.napoline.modules.movement;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.events.EventPacketReceive;
import me.napoleon.napoline.guis.notification.Notification;
import me.napoleon.napoline.junk.values.type.Bool;
import me.napoleon.napoline.manager.ModuleManager;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.utils.client.ClientUtils;
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

        if (Napoline.moduleManager.getModByClass(Fly.class).getState() && fly.getValue()) {
            Napoline.moduleManager.getModByClass(Fly.class).setStage(false);
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
