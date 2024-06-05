package gq.vapu.czfclient.Module.Modules.Combat;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Option;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

import java.awt.*;

public class Ragen
        extends Module {
    private final Option<Boolean> guardian = new Option<Boolean>("Guardian", "guardian", true);

    public Ragen() {
        super("Ragen", new String[]{"reach"}, ModuleType.Blatant);
        this.setColor(new Color(208, 30, 142).getRGB());
        this.addValues(this.guardian);
    }

    @EventHandler
    private void onUpdate(EventPreUpdate event) {
        if (mc.thePlayer.onGround && (double) mc.thePlayer.getHealth() < 16.0 && mc.thePlayer.getFoodStats().getFoodLevel() > 17 && mc.thePlayer.isCollidedVertically) {
            int i = 0;
            while (i < 60) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-9, mc.thePlayer.posZ, mc.thePlayer.rotationYaw, mc.thePlayer.rotationPitch, true));
                mc.thePlayer.motionX = 0.0;
                mc.thePlayer.motionZ = 0.0;
                ++i;
            }
            if (this.guardian.getValue().booleanValue() && mc.thePlayer.ticksExisted % 3 == 0) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY - 999.0, mc.thePlayer.posZ, true));
            }
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        }
    }
}