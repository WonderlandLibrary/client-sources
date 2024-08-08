package me.xatzdevelopments.modules.combat;

import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.util.BlockPos;
import org.lwjgl.input.Keyboard;

import me.xatzdevelopments.Xatz;
import me.xatzdevelopments.events.Event;
import me.xatzdevelopments.events.listeners.EventMotion;
import me.xatzdevelopments.events.listeners.EventPacket;
import me.xatzdevelopments.modules.Module;
import me.xatzdevelopments.settings.NumberSetting;
import me.xatzdevelopments.util.FTimer;
import me.xatzdevelopments.util.movement.MovementUtil;

public class Criticals2 extends Module {

    private final double[] offsets = new double[]{0.05101, 0.01601, 0.0301, 0.00101};
    public FTimer timer = new FTimer();
    public int groundTicks;
    public NumberSetting critTime = new NumberSetting("Crit Time", 250, 10, 600, 10);

    public Criticals2() {
        super("Criticals2", Keyboard.KEY_NONE, Category.COMBAT, "Critical hit some scrub");
        addSettings(critTime);
    }


    public void onEvent(Event e) {
        if (e instanceof EventMotion && e.isPre()) {
            if (mc.thePlayer.onGround)
                groundTicks++;
            else
                groundTicks = 0;
        }
        if (e instanceof EventPacket && e.isOutgoing()) {
            if (((EventPacket) e).getPacket() instanceof C0APacketAnimation) {
                boolean falseModules = Xatz.getModuleByName("Speed").isEnabled() || Xatz.getModuleByName("Fly").isEnabled();
                boolean canCrit = !falseModules && mc.thePlayer.onGround &&
                        !mc.gameSettings.keyBindJump.isPressed() && !mc.thePlayer.isInWater() &&
                        !mc.thePlayer.isOnLadder() && MovementUtil.getBlockAtPos(new BlockPos(mc.thePlayer.posX, mc.thePlayer.posY + 1, mc.thePlayer.posZ)) instanceof BlockAir;
                if (canCrit)
                    if (KillAura2.target != null || mc.objectMouseOver.entityHit != null)
                        this.crit();
            }
        }
    }

    private void crit() {
        if (timer.hasTimeElapsed((long) critTime.getValue(), true) && groundTicks > 1) {
            for (double offset : offsets)
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + offset, mc.thePlayer.posZ, false));
        }
    }

}