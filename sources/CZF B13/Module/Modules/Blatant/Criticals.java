/*
 * Decompiled with CFR 0_132.
 */
package gq.vapu.czfclient.Module.Modules.Blatant;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.Events.World.EventPacketSend;
import gq.vapu.czfclient.API.Events.World.EventPreUpdate;
import gq.vapu.czfclient.API.Value.Mode;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.block.BlockLiquid;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;

import java.awt.*;
import java.util.Random;

public class Criticals
        extends Module {
    public static Mode mode = new Mode("Mode", "mode", CritMode.values(), CritMode.Jump);
    private final TimerUtil timer = new TimerUtil();

    public Criticals() {
        super("Criticals", new String[]{"crits", "crit"}, ModuleType.Blatant);
        this.setColor(new Color(235, 194, 138).getRGB());
        this.addValues(mode);
    }

    public static boolean isOnWater() {
        double y = mc.thePlayer.posY - 0.03;
        for (int x = MathHelper.floor_double(mc.thePlayer.posX); x < MathHelper
                .ceiling_double_int(mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.posZ); z < MathHelper
                    .ceiling_double_int(mc.thePlayer.posZ); ++z) {
                BlockPos pos = new BlockPos(x, MathHelper.floor_double(y), z);
                if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid))
                    continue;
                return true;
            }
        }
        return false;
    }

    public static boolean isInLiquid() {
        double y = mc.thePlayer.posY + 0.01;
        for (int x = MathHelper.floor_double(mc.thePlayer.posX); x < MathHelper
                .ceiling_double_int(mc.thePlayer.posX); ++x) {
            for (int z = MathHelper.floor_double(mc.thePlayer.posZ); z < MathHelper
                    .ceiling_double_int(mc.thePlayer.posZ); ++z) {
                BlockPos pos = new BlockPos(x, (int) y, z);
                if (!(mc.theWorld.getBlockState(pos).getBlock() instanceof BlockLiquid))
                    continue;
                return true;
            }
        }
        return false;
    }

    @EventHandler
    private void onUpdate(EventPreUpdate e) {
        this.setSuffix(mode.getValue());
    }

    private boolean canCrit() {
        return mc.thePlayer.onGround && !mc.thePlayer.isInWater();
    }

    @EventHandler
    private void onPacket(EventPacketSend e) {
        double x = mc.thePlayer.posX;
        double y = mc.thePlayer.posY;
        double z = mc.thePlayer.posZ;
        float yaw = mc.thePlayer.rotationYaw;
        Random iq = new Random();
        float pitch = mc.thePlayer.rotationPitch;
        Packet packet = EventPacketSend.getPacket();
        if (EventPacketSend.getPacket() instanceof C02PacketUseEntity && this.canCrit() && mode.getValue() == CritMode.Jump) {
            mc.thePlayer.jump();
        }
        if (EventPacketSend.getPacket() instanceof C02PacketUseEntity && this.canCrit() && mode.getValue() == CritMode.Hypixel) {
            //Helper.sendMessage("Crit:" + iq.nextInt(101));
//			C06PacketPlayerPosLook c06 = (C06PacketPlayerPosLook) packet;
//			c06.y = c06.y+0.01145141919810D;
            //mc.thePlayer.sendQueue.addToSendQueue1(new C06PacketPlayerPosLook(x, y+0.5250000001304, z, yaw, pitch, false));
            //mc.thePlayer.motionY = 0.2;
            //mc.thePlayer.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(x, y+0.5, z, yaw, pitch, false));
            mc.thePlayer.motionY = 0.12;
            //Helper.sendDebugMessage("Crit1?");
        }
        if (mode.getValue() == CritMode.NCPPacket && this.canCrit()) {
            double[] offsets = new double[]{0.0625, 0.0, 1.0E-4, 0.0};
            int i = 0;
            while (i < offsets.length) {
                mc.thePlayer.sendQueue.addToSendQueue1(new C06PacketPlayerPosLook(x, y + 0.10020000114514, z, yaw, pitch, false));
                mc.thePlayer.sendQueue.addToSendQueue1(new C06PacketPlayerPosLook(x, y + 0.00000114514, z, yaw, pitch, false));
                mc.thePlayer.sendQueue.addToSendQueue1(new C06PacketPlayerPosLook(x, y + 0.00000114514, z, yaw, pitch, false));
                ++i;
            }
            this.timer.reset();
        }
    }

    enum CritMode {
        NCPPacket,
        Hypixel,
        Jump
    }

}