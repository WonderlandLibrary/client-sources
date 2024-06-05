package tk.rektsky.Module.Movement;

import tk.rektsky.Module.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Event.*;
import net.minecraft.util.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import tk.rektsky.Event.Events.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.entity.*;
import net.minecraft.client.*;

public class HighJump extends Module
{
    public ListSetting modeSetting;
    public BooleanSetting doCoolDown;
    int ticks;
    long lastEnableTime;
    
    public HighJump() {
        super("HighJump", "Makes you jump higher", 0, Category.MOVEMENT);
        this.modeSetting = new ListSetting("Mode", new String[] { "High", "Normal" }, "Normal");
        this.doCoolDown = new BooleanSetting("CoolDown", true);
        this.registerSetting(this.modeSetting);
    }
    
    @Override
    public String getSuffix() {
        return this.modeSetting.getValue();
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof WorldTickEvent) {
            ++this.ticks;
            if (this.modeSetting.getValue().equalsIgnoreCase("normal")) {
                if (this.ticks >= 20) {
                    this.toggle();
                    return;
                }
                if (!this.mc.thePlayer.onGround) {
                    final EntityPlayerSP thePlayer = this.mc.thePlayer;
                    thePlayer.motionY += 0.075;
                    this.mc.timer.timerSpeed = 1.4f;
                    final float var1 = this.mc.thePlayer.rotationYaw * 0.017453292f;
                    final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                    thePlayer2.motionX -= MathHelper.sin(var1) * 0.069f;
                    final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                    thePlayer3.motionZ += MathHelper.cos(var1) * 0.069f;
                    return;
                }
                this.ticks = 0;
            }
            if (this.modeSetting.getValue().equalsIgnoreCase("high")) {
                this.mc.timer.timerSpeed = 0.2f;
                ++this.ticks;
                this.ticks = 0;
                this.mc.thePlayer.setJumping(true);
                this.mc.thePlayer.fallDistance = 0.0f;
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 10.0, this.mc.thePlayer.posZ, false));
                this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 50.0, this.mc.thePlayer.posZ, false));
            }
        }
        if (e instanceof PacketReceiveEvent && this.modeSetting.getValue().equalsIgnoreCase("high")) {
            if (((PacketReceiveEvent)e).getPacket() instanceof S12PacketEntityVelocity) {
                final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)((PacketReceiveEvent)e).getPacket();
                if (packet.getMotionY() > 18000 && packet.getEntityID() == this.mc.thePlayer.getEntityId()) {
                    this.mc.thePlayer.performHurtAnimation();
                    this.toggle();
                    Client.notify(new Notification.PopupMessage("Highjump", "Successfully boost you up to sky!", ColorUtil.NotificationColors.GREEN, 30));
                }
            }
            if (((PacketReceiveEvent)e).getPacket() instanceof S08PacketPlayerPosLook) {
                final S08PacketPlayerPosLook packet2 = (S08PacketPlayerPosLook)((PacketReceiveEvent)e).getPacket();
                packet2.yaw = this.mc.thePlayer.rotationYaw;
                packet2.pitch = this.mc.thePlayer.rotationPitch;
            }
        }
    }
    
    @Override
    public void onEnable() {
        this.ticks = 0;
        if (this.modeSetting.getValue().equalsIgnoreCase("normal")) {
            if (Minecraft.getSystemTime() - this.lastEnableTime < 3000L && this.doCoolDown.getValue()) {
                Client.notify(new Notification.PopupMessage("Anti-Lagback", "HighJump Normal mode is in cooldown! Using it will result in lag back to the void! You can disable it from Highjump Settings. [ Please wait more " + (3.0f - Math.round((float)(Minecraft.getSystemTime() - this.lastEnableTime)) / 100.0f / 10.0f) + "seconds! ]", ColorUtil.NotificationColors.RED, 20));
                this.rawSetToggled(false);
                return;
            }
            if (!this.mc.thePlayer.onGround) {
                this.rawSetToggled(false);
                return;
            }
            this.mc.thePlayer.jump();
        }
    }
    
    @Override
    public void onDisable() {
        this.mc.timer.timerSpeed = 1.0f;
        this.lastEnableTime = Minecraft.getSystemTime();
    }
}
