package cc.slack.features.modules.impl.movement.glides.impl;

import cc.slack.start.Slack;
import cc.slack.events.State;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.movement.Glide;
import cc.slack.features.modules.impl.movement.glides.IGlide;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class VulcanClipGlide implements IGlide {

    private boolean isWaiting = false;
    private boolean isGlideEnabled = false;
    private int tickCounter = 0;

    @Override
    public void onEnable() {
        EntityPlayer player = mc.thePlayer;
        Glide glideModule = Slack.getInstance().getModuleManager().getInstance(Glide.class);

        if (player.onGround && glideModule.vulcanClipValue.getValue()) {
            clip(0f, -0.1f);
            isWaiting = true;
            isGlideEnabled = false;
            tickCounter = 0;
            mc.timer.timerSpeed = 0.1f;
        } else {
            isWaiting = false;
            isGlideEnabled = true;
        }
    }

    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() == State.PRE && isGlideEnabled) {
            EntityPlayer player = mc.thePlayer;
            mc.timer.timerSpeed = 1f;
            player.motionY = (tickCounter % 2 == 0) ? -0.17 : -0.10;

            if (tickCounter == 0) {
                player.motionY = -0.07;
            }
            tickCounter++;
        }

        if (tickCounter > 4 && mc.thePlayer.onGround) {
            Slack.getInstance().getModuleManager().getInstance(Glide.class).toggle();
        }
    }

    @Override
    public void onPacket(PacketEvent event) {
        if (event.getPacket() instanceof S08PacketPlayerPosLook && isWaiting) {
            EntityPlayer player = mc.thePlayer;
            S08PacketPlayerPosLook packet = (S08PacketPlayerPosLook) event.getPacket();

            isWaiting = false;
            player.setPosition(packet.getX(), packet.getY(), packet.getZ());
            mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(player.posX, player.posY, player.posZ, player.rotationYaw, player.rotationPitch, false));
            event.cancel();
            player.jump();
            performClips();
            isGlideEnabled = true;
        }
    }

    @Override
    public void onDisable() {
        mc.timer.timerSpeed = 1f;
    }

    private void performClips() {
        clip(0.127318f, 0f);
        clip(3.425559f, 3.7f);
        clip(3.14285f, 3.54f);
        clip(2.88522f, 3.4f);
    }

    private void clip(float dist, float y) {
        EntityPlayer player = mc.thePlayer;
        double yaw = Math.toRadians(player.rotationYaw);
        double x = -Math.sin(yaw) * dist;
        double z = Math.cos(yaw) * dist;
        player.setPosition(player.posX + x, player.posY + y, player.posZ + z);
        mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(player.posX, player.posY, player.posZ, false));
    }

    @Override
    public String toString() {
        return "Vulcan Clip";
    }

}
