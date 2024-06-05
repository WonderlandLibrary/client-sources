package Ascii4UwUWareClient.Module.Modules.Combat;

import Ascii4UwUWareClient.API.EventHandler;
import Ascii4UwUWareClient.API.Events.World.EventPacketSend;
import Ascii4UwUWareClient.API.Events.World.EventPreUpdate;
import Ascii4UwUWareClient.API.Value.Mode;
import Ascii4UwUWareClient.API.Value.Numbers;
import Ascii4UwUWareClient.Module.Module;
import Ascii4UwUWareClient.Module.ModuleType;
import Ascii4UwUWareClient.Util.TimerUtil;
import Ascii4UwUWareClient.Util.player.MovementUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;

import java.awt.*;
import java.util.Random;

import static Ascii4UwUWareClient.Util.MoveUtils.isOnGround;

public class Criticals extends Module {
    private int groundTicks;
    TimerUtil timer = new TimerUtil();
    private double watchdogOffsets;
    private final Mode<Enum> mode = new Mode("Mode", "Mode", CritMode.values(), CritMode.Redesky);
    private final Numbers <Double> Timer = new Numbers<Double>("Delay", "Delay", 0.5D , 0.1D, 2D, 0.1D);
    private final Numbers <Double> Motion = new Numbers<Double>("Delay", "Delay", 0.5D , 0.1D, 2D, 0.1D);

    public Criticals() {
        super("Criticals", new String[]{"crits", "crit"}, ModuleType.Combat);
        this.setColor(new Color(235, 194, 138).getRGB());
        addValues(mode);

    }

    @Override
    public void onEnable() {

    }

    @EventHandler
    public void onUpdate(EventPreUpdate event) {
        groundTicks = MovementUtil.isOnGround() ? groundTicks + 1 : 0;
        watchdogOffsets = new Random().nextDouble() / 30;
    }

    @EventHandler
    public void onPacket(EventPacketSend e) {
        switch (mode.getModeAsString()) {
            case "Hypixel":
                if (e.getPacket() instanceof C0APacketAnimation) {
                    if (timer.hasElapsed(490)) {
                        mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.0031311231111, mc.thePlayer.posZ, false));

                        }
                        timer.reset();
                        break;
                    }
            case"AAC4":
                if (e.getPacket() instanceof C0APacketAnimation) {
                    if (timer.hasElapsed ( 490 )) {
                        mc.thePlayer.sendQueue.addToSendQueue ( new C03PacketPlayer.C04PacketPlayerPosition ( mc.thePlayer.posX, mc.thePlayer.posY + 0.0031311231111, mc.thePlayer.posZ, false ) );

                    }
                    timer.reset ();
                }
                break;
            case"Jump":
                if(mc.thePlayer.onGround || isOnGround(0.5)){
                    mc.thePlayer.jump();
                    mc.timer.timerSpeed = (float) Timer.getValue().doubleValue ();
                    mc.thePlayer.motionY = Motion.getValue();
                }
                break;
            case "Redesky":
                if (e.getPacket() instanceof C0APacketAnimation && Killaura.target != null) {
                    mc.thePlayer.onCriticalHit(Killaura.target);
                    break;
                }
        }
    }

    public enum CritMode {
        Hypixel, Redesky, AAC4, Jump
    }
}