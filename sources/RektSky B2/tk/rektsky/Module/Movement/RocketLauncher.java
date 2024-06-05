package tk.rektsky.Module.Movement;

import tk.rektsky.Event.*;
import org.lwjgl.input.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.*;
import tk.rektsky.Module.*;
import net.minecraft.client.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import tk.rektsky.Event.Events.*;

public class RocketLauncher extends Module
{
    public double forward;
    public double up;
    public boolean jumped;
    public boolean finishedJumping;
    
    public RocketLauncher() {
        super("RocketLauncher", "Rocket Launcher but for Players", Category.MOVEMENT);
        this.forward = 0.0;
        this.up = 0.0;
        this.jumped = false;
        this.finishedJumping = false;
    }
    
    @Override
    public void onEnable() {
        if (this.mc.thePlayer.onGround) {
            this.mc.thePlayer.jump();
            this.finishedJumping = false;
        }
        else {
            this.finishedJumping = true;
        }
        this.jumped = false;
        this.up = 5.0;
        this.forward = 10.0;
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.speedInAir = 0.02f;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof WorldTickPostEvent) {
            if (this.enabledTicks == 5 && !this.finishedJumping) {
                this.finishedJumping = true;
                Client.notify(new Notification.PopupMessage("Rocket Launcher but for Player", "Press " + Keyboard.getKeyName(this.mc.gameSettings.keyBindForward.getKeyCode()) + "," + Keyboard.getKeyName(this.mc.gameSettings.keyBindBack.getKeyCode()) + " and " + Keyboard.getKeyName(this.mc.gameSettings.keyBindJump.getKeyCode()) + ", " + Keyboard.getKeyName(this.mc.gameSettings.keyBindSneak.getKeyCode()) + " to change speed and height, press " + Keyboard.getKeyName(this.mc.gameSettings.keyBindSprint.getKeyCode()) + " to jump!", ColorUtil.NotificationColors.GREEN, 80));
            }
            if (!this.jumped && this.finishedJumping) {
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionY = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
                this.mc.thePlayer.speedOnGround = 0.0f;
                this.mc.thePlayer.speedInAir = 0.0f;
                if (this.mc.gameSettings.keyBindForward.isKeyDown()) {
                    if (this.forward < 10.0) {
                        this.forward += 0.5;
                    }
                    else {
                        this.forward = 10.0;
                    }
                }
                if (this.mc.gameSettings.keyBindBack.isKeyDown()) {
                    if (this.forward > 0.0) {
                        this.forward -= 0.5;
                    }
                    else {
                        this.forward = 0.0;
                    }
                }
                if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                    if (this.up < 10.0) {
                        this.up += 0.5;
                    }
                    else {
                        this.up = 10.0;
                    }
                }
                if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    if (this.up > 0.0) {
                        this.up -= 0.5;
                    }
                    else {
                        this.up = 0.0;
                    }
                }
            }
            if (this.mc.gameSettings.keyBindSprint.isKeyDown() && (this.finishedJumping & !this.jumped)) {
                this.jumped = true;
                ((Fly)ModulesManager.getModuleByClass(Fly.class)).disable = Minecraft.getSystemTime() - 3000L;
                final double playerYaw = Math.toRadians(this.mc.thePlayer.rotationYaw);
                this.mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX - 8.956969696969 * (this.forward / 10.0) * Math.sin(playerYaw), this.mc.thePlayer.posY + this.up / 3.0, this.mc.thePlayer.posZ + 8.956969696969 * (this.forward / 10.0) * Math.cos(playerYaw), false));
                this.mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 50.0, this.mc.thePlayer.posZ, false));
                this.rawSetToggled(false);
                this.mc.timer.timerSpeed = 1.0f;
                this.mc.thePlayer.speedInAir = 0.02f;
                Client.notify(new Notification.PopupMessage("Rocket Launcher but for Player", "Successfully boosted you into sky!", ColorUtil.NotificationColors.GREEN, 40));
            }
        }
        if (event instanceof PacketSentEvent && !this.jumped && this.finishedJumping && ((PacketSentEvent)event).getPacket() instanceof C03PacketPlayer) {
            ((PacketSentEvent)event).setCanceled(true);
        }
    }
}
