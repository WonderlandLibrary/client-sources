package cn.Hanabi.modules.Combat;

import cn.Hanabi.value.*;
import com.darkmagician6.eventapi.*;
import ClassSub.*;
import cn.Hanabi.events.*;
import cn.Hanabi.modules.*;
import cn.Hanabi.injection.interfaces.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class Criticals extends Mod
{
    int onGroundState;
    Class205 timer;
    Class205 stepTimer;
    public Value<String> modes;
    
    
    public Criticals() {
        super("Criticals", Category.COMBAT);
        this.timer = new Class205();
        this.stepTimer = new Class205();
        (this.modes = new Value<String>("Criticals", "Mode", 0)).addValue("HypixelPacket");
        this.modes.addValue("NoGround");
    }
    
    @EventTarget
    public void onStep(final EventStep eventStep) {
        if (!eventStep.isPre()) {
            this.stepTimer.reset();
            if (Class334.username.length() < 1) {
                System.exit(0);
            }
        }
    }
    
    @EventTarget
    public void onUpdate(final EventUpdate eventUpdate) {
        this.setDisplayName(this.modes.getModeAt(this.modes.getCurrentMode()));
        if (Class180.isOnGround(0.001)) {
            ++this.onGroundState;
        }
        else if (!Criticals.mc.thePlayer.onGround) {
            this.onGroundState = 0;
        }
    }
    
    @EventTarget
    public void onAttack(final EventAttack eventAttack) {
        if (eventAttack.getEntity() != null) {}
    }
    
    @EventTarget
    public void onPacket(final EventPacket eventPacket) {
        if (this.modes.isCurrentMode("NoGround")) {
            if (ModManager.getModule("Killaura").isEnabled() && KillAura.target != null && Criticals.mc.thePlayer.onGround && Class180.isOnGround(0.001) && !ModManager.getModule("Fly").isEnabled() && !ModManager.getModule("Longjump").isEnabled() && !ModManager.getModule("Speed").isEnabled() && this.onGroundState > 1 && eventPacket.getPacket() instanceof C03PacketPlayer) {
                ((IC03PacketPlayer)eventPacket.getPacket()).setOnGround(false);
                this.timer.reset();
            }
        }
        else if (this.modes.isCurrentMode("HypixelPacket") && eventPacket.getPacket() instanceof C02PacketUseEntity && ((C02PacketUseEntity)eventPacket.getPacket()).getAction() == C02PacketUseEntity.Action.ATTACK && Criticals.mc.thePlayer.onGround && Class180.isOnGround(0.001) && !ModManager.getModule("LongJump").isEnabled() && !ModManager.getModule("Speed").isEnabled() && this.stepTimer.isDelayComplete(20L) && this.timer.isDelayComplete(500L) && Criticals.mc.thePlayer.isCollidedVertically && this.onGroundState > 1) {
            this.doCrit();
            this.timer.reset();
        }
    }
    
    public void doCrit() {
        Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.0624218713251234 + Math.random() * 2.0 / 1000.0, Criticals.mc.thePlayer.posZ, false));
        Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
        Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY + 0.012511000037193298 + Math.random() * 2.0 / 10000.0, Criticals.mc.thePlayer.posZ, false));
        Criticals.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C03PacketPlayer.C04PacketPlayerPosition(Criticals.mc.thePlayer.posX, Criticals.mc.thePlayer.posY, Criticals.mc.thePlayer.posZ, false));
    }
}
