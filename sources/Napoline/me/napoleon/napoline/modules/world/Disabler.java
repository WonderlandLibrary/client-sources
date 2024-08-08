package me.napoleon.napoline.modules.world;

import java.util.Random;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.events.EventMove;
import me.napoleon.napoline.events.EventPacketReceive;
import me.napoleon.napoline.events.EventPacketSend;
import me.napoleon.napoline.events.EventUpdate;
import me.napoleon.napoline.manager.event.EventTarget;
import me.napoleon.napoline.modules.Mod;
import me.napoleon.napoline.modules.ModCategory;
import me.napoleon.napoline.modules.movement.Fly;
import me.napoleon.napoline.utils.client.ClientUtils;
import me.napoleon.napoline.utils.timer.TimerUtil;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class Disabler extends Mod {
    public Disabler() {
        super("Disabler", ModCategory.World, "5s to close watchdog");
    }

    public static boolean ready = false;

    public TimerUtil tu = new TimerUtil();

    @Override
    public void onEnabled() {
        this.ready = false;
        if (mc.theWorld == null)
            return;
        tu.reset();
        Random random = new Random();
        mc.thePlayer.jump();
        //mc.thePlayer.motionY = 0.4108888688697815D;
    }

    @EventTarget
    public void onPacket(EventPacketSend e) {
        if (mc.thePlayer.motionY!=0)
            return;

    }

    @EventTarget
    public void onUpdate(EventUpdate e) {
        if(mc.thePlayer.motionY<(0.18 + (mc.thePlayer.ticksExisted % 2)*0.0002) || ready) {
            ready = true;
            mc.thePlayer.motionY = 0.0;
        }

    }

    @EventTarget
    public void onMove(EventMove e) {
//        if(ready){
            e.setMoveSpeed(0);
//        }
    }

    @EventTarget
    public void onPacket(EventPacketReceive e) {

        if (e.getPacket() instanceof S08PacketPlayerPosLook) {
            ClientUtils.sendClientMessage("Disabler ready!", me.napoleon.napoline.guis.notification.Notification.Type.INFO);
            if(!Napoline.moduleManager.getModByClass(Fly.class).getState())
            	Napoline.moduleManager.getModByClass(Fly.class).setStage(true);
            ready = true;
            this.setStage(false);
        }
    }

}
