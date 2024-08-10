package cc.slack.features.modules.impl.player.antivoids.impl;

import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.player.antivoids.IAntiVoid;
import cc.slack.utils.network.PacketUtil;
import cc.slack.utils.player.BlinkUtil;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;

public class UniversalAntiVoid implements IAntiVoid {

    private double groundX = 0.0;
    private double groundY = 0.0;
    private double groundZ = 0.0;


    private boolean universalStarted = false;
    private boolean universalFlag = false;


    @Override
    public void onEnable() {
        universalStarted = false;
    }

    @Override
    public void onDisable() {
        BlinkUtil.disable();
    }

    @Override
    public void onUpdate(UpdateEvent event) {
        if (universalStarted) {
            if (mc.thePlayer.onGround || mc.thePlayer.fallDistance > 8f) {
                BlinkUtil.disable();
                universalStarted = false;
                universalFlag = false;
            } else if (mc.thePlayer.fallDistance > 6f && !universalFlag) {
                universalFlag = true;
                PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(groundX, groundY + 1, groundZ, false));
            }
        } else if (mc.thePlayer.fallDistance > 0f && !mc.thePlayer.onGround && mc.thePlayer.motionY < 0) {
            if (isOverVoid()) {
                universalStarted = true;
                universalFlag = false;
                BlinkUtil.enable(false, true);
                groundX = mc.thePlayer.posX;
                groundY = mc.thePlayer.posY;
                groundZ = mc.thePlayer.posZ;
            }
        }
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook) {
            if (((S08PacketPlayerPosLook) event.getPacket()).getX() == groundX && ((S08PacketPlayerPosLook) event.getPacket()).getY() == groundY && ((S08PacketPlayerPosLook) event.getPacket()).getZ() == groundZ) {
                BlinkUtil.disable(false);
                mc.thePlayer.setPosition(groundX, groundY, groundZ);
                universalFlag = false;
                universalStarted = false;
            }
        }
    }

    private boolean isOverVoid() {
        return mc.theWorld.rayTraceBlocks(
                new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ),
                new Vec3(mc.thePlayer.posX, mc.thePlayer.posY - 40, mc.thePlayer.posZ),
                true, true, false) == null;
    }

    @Override
    public String toString() {
        return "Hypixel";
    }
}
