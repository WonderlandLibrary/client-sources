package me.mees.remix.modules.combat;

import me.satisfactory.base.utils.timer.*;
import me.satisfactory.base.module.*;
import java.util.*;
import net.minecraft.network.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.client.*;
import pw.stamina.causam.scan.method.model.*;

public final class Criticals extends Module
{
    TimerUtil timer;
    
    public Criticals() {
        super("Criticals", 0, Category.COMBAT);
        this.timer = new TimerUtil();
    }
    
    public boolean MovementInput() {
        return Criticals.mc.gameSettings.keyBindForward.getIsKeyPressed() || Criticals.mc.gameSettings.keyBindBack.getIsKeyPressed() || Criticals.mc.gameSettings.keyBindLeft.getIsKeyPressed() || Criticals.mc.gameSettings.keyBindRight.getIsKeyPressed() || (Criticals.mc.gameSettings.keyBindSneak.getIsKeyPressed() && !Criticals.mc.thePlayer.isCollidedVertically) || Criticals.mc.gameSettings.keyBindJump.getIsKeyPressed();
    }
    
    private void doCrits() {
        if (this.timer.hasTimeElapsed(50.0) && Criticals.mc.thePlayer.isSwingInProgress && Criticals.mc.thePlayer.onGround) {
            final double[] offsets = { 0.0625, 0.0, 1.0E-4, 0.0 };
            Arrays.stream(offsets).forEach(offset -> Criticals.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + offset, Criticals.mc.thePlayer.posZ, false)));
            this.timer.reset();
        }
    }
    
    @Subscriber
    public void onPacket(final EventSendPacket event) {
        if (event.getPacket() instanceof C02PacketUseEntity) {
            final C02PacketUseEntity packet = (C02PacketUseEntity)event.getPacket();
            if (packet.getAction() == C02PacketUseEntity.Action.ATTACK && Criticals.mc.thePlayer.onGround) {
                this.doCrits();
            }
        }
    }
}
