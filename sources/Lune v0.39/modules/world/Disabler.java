package me.superskidder.lune.modules.world;

import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.modules.movement.Fly;

import java.util.Random;

import me.superskidder.lune.Lune;
import me.superskidder.lune.events.EventMove;
import me.superskidder.lune.events.EventPacketReceive;
import me.superskidder.lune.events.EventPacketSend;
import me.superskidder.lune.events.EventUpdate;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.utils.client.ClientUtils;
import me.superskidder.lune.utils.timer.TimerUtil;
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
            ClientUtils.sendClientMessage("Disabler ready!", me.superskidder.lune.guis.notification.Notification.Type.INFO);
            if(!Lune.moduleManager.getModByClass(Fly.class).getState())
            	Lune.moduleManager.getModByClass(Fly.class).setStage(true);
            ready = true;
            this.setStage(false);
        }
    }

}
