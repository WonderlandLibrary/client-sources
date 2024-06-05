package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketReceive;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.item.ItemBow;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.Timer;

import java.awt.*;

public class FastBow extends Module {
    int counter;
    private final TimerUtil timer = new TimerUtil();

    public FastBow() {
        super("FastBow", new String[]{"zoombow", "quickbow"}, ModuleType.Blatant);
        this.setColor(new Color(255, 99, 99).getRGB());
        this.counter = 0;
    }

    private boolean canConsume() {
        return mc.thePlayer.inventory.getCurrentItem() != null
                && mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow;
    }

    private void killGuardian() {
        if (this.timer.hasReached(1000.0)) {
            mc.thePlayer.sendQueue
                    .addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX,
                            mc.thePlayer.posY - Double.POSITIVE_INFINITY, mc.thePlayer.posZ, false));
            this.timer.reset();
        }
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        if (mc.thePlayer.onGround && this.canConsume() && mc.gameSettings.keyBindUseItem.pressed) {
            mc.thePlayer.sendQueue
                    .addToSendQueue(new C08PacketPlayerBlockPlacement(mc.thePlayer.inventory.getCurrentItem()));
            int i = 0;
            while (i < 20) {
                mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(
                        mc.thePlayer.posX, mc.thePlayer.posY + 1.0E-9, mc.thePlayer.posZ, true));
                ++i;
            }
            mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(
                    C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
        } else {
            Timer.timerSpeed = 1.0f;
        }
    }

    @EventHandler
    public void onRecieve(EventPacketReceive event) {
        if (event.getPacket() instanceof S18PacketEntityTeleport) {
            S18PacketEntityTeleport packet = (S18PacketEntityTeleport) event.getPacket();
            if (mc.thePlayer != null) {
                packet.yaw = (byte) mc.thePlayer.rotationYaw;
            }
            packet.pitch = (byte) mc.thePlayer.rotationPitch;
        }
    }
}
