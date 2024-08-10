// Slack Client (discord.gg/slackclient)

package cc.slack.features.modules.impl.movement.speeds.vanilla;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.movement.speeds.ISpeed;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.rotations.RotationUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketDirection;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;

import java.util.ArrayList;

public class FakeStrafeSpeed implements ISpeed {

    private ArrayList<Float> speeds = new ArrayList<>();
    private ArrayList<Packet> packetBuffer = new ArrayList<>();
    private boolean blink = false;

    private double totalDist = 0.0;
    private double currDist = 0.0;

    private Vec3 lastPos;

    @Override
    public void onEnable() {
        speeds.clear();
        packetBuffer.clear();
        blink = false;
    }

    @Override
    public void onDisable() {
        for (Packet p : packetBuffer) {
            PacketUtil.sendNoEvent(p);
        }
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.onGround && MovementUtil.isMoving()) {
            currDist = 0;
            if (blink) {
                totalDist = Math.sqrt(Math.pow(mc.thePlayer.posX - lastPos.xCoord, 2) + Math.pow(mc.thePlayer.posZ - lastPos.zCoord, 2));
                for (Packet p : packetBuffer) {
                    if (p instanceof C03PacketPlayer && !speeds.isEmpty()) {
                        currDist += speeds.remove(0);
                        if (currDist > totalDist) currDist = totalDist;

                        float yaw = RotationUtil.getRotations(lastPos, new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ))[0];

                        PacketUtil.sendNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(
                                lastPos.xCoord + Math.cos(Math.toRadians(yaw + 90.0f)) * currDist,
                                ((C03PacketPlayer) p).getPositionY(),
                                lastPos.zCoord + Math.cos(Math.toRadians(yaw)) * currDist,
                                ((C03PacketPlayer) p).getYaw(),
                                ((C03PacketPlayer) p).getPitch(),
                                ((C03PacketPlayer) p).onGround));
                    } else {
                        PacketUtil.sendNoEvent(p);
                    }
                }
                packetBuffer.clear();
            } else {
                totalDist = -1;
            }
            lastPos = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
            mc.thePlayer.jump();
            blink = true;
            speeds.clear();
            packetBuffer.clear();
        }

        MovementUtil.strafe();
        speeds.add(MovementUtil.getSpeed());
    }

    @Override
    public void onPacket(PacketEvent event) {
        Packet packet = event.getPacket();
        if (event.getDirection() == PacketDirection.OUTGOING) {
            if (!(packet instanceof C00PacketKeepAlive || packet instanceof C00Handshake ||
                    packet instanceof C00PacketLoginStart) && blink) {
                packetBuffer.add(packet);
                event.cancel();
            }
        }
    }

    @Override
    public String toString() {
        return "Fake Strafe";
    }
}
