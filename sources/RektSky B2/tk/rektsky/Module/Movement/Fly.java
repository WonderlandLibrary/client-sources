package tk.rektsky.Module.Movement;

import tk.rektsky.Module.*;
import tk.rektsky.Module.Settings.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import tk.rektsky.Event.*;
import tk.rektsky.Utils.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.entity.*;

public class Fly extends Module
{
    public ListSetting mode;
    public DoubleSetting lagBackRange;
    public double oldX;
    public double oldY;
    public double oldZ;
    public long disable;
    public float oldYaw;
    int lastFlag;
    public float oldPitch;
    public EntityOtherPlayerMP player;
    
    public Fly() {
        super("Fly", "Makes you fly like a paid players", 0, Category.MOVEMENT);
        this.mode = new ListSetting("Mode", new String[] { "Vanilla", "Stable", "Motion", "Repixel" }, "Vanilla");
        this.lagBackRange = new DoubleSetting("AutoLagBackRange", 6.0, 15.0, 12.0);
        this.oldX = 0.0;
        this.oldY = 0.0;
        this.oldZ = 0.0;
        this.disable = 0L;
        this.oldYaw = 0.0f;
        this.lastFlag = 0;
        this.oldPitch = 0.0f;
    }
    
    @Override
    public void onEnable() {
        Client.notify(new Notification.PopupMessage("Final Dad", "Enabling Final dad... Please wait...", ColorUtil.NotificationColors.YELLOW, 40));
        this.lastFlag = 0;
        this.oldX = this.mc.thePlayer.posX;
        this.oldY = this.mc.thePlayer.posY;
        this.oldZ = this.mc.thePlayer.posZ;
        this.oldYaw = this.mc.thePlayer.rotationYaw;
        this.oldPitch = this.mc.thePlayer.rotationPitch;
        if (this.mc.thePlayer.onGround) {}
        (this.player = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.getGameProfile())).copyLocationAndAnglesFrom(this.mc.thePlayer);
        this.player.rotationYawHead = this.mc.thePlayer.rotationYawHead;
        this.player.renderYawOffset = this.mc.thePlayer.renderYawOffset;
        this.mc.thePlayer.noClip = true;
    }
    
    @Override
    public String getSuffix() {
        return this.mode.getValue();
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.setPosition(this.oldX, this.oldY, this.oldZ);
        this.mc.thePlayer.rotationYaw = this.oldYaw;
        this.mc.thePlayer.rotationPitch = this.oldPitch;
        this.mc.thePlayer.motionX = 0.0;
        this.mc.thePlayer.motionY = 0.0;
        this.mc.thePlayer.motionZ = 0.0;
        this.player = null;
        this.mc.timer.timerSpeed = 1.0f;
        this.mc.thePlayer.capabilities.allowFlying = false;
        this.mc.thePlayer.capabilities.setFlySpeed(0.05f);
        this.mc.thePlayer.capabilities.isFlying = false;
        this.mc.thePlayer.speedInAir = 0.02f;
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof RenderEvent) {}
        if (event instanceof WorldTickPostEvent) {
            if (this.enabledTicks == 3) {
                Client.notify(new Notification.PopupMessage("Final Dad", "Enabled Final Dad! You can fly freely until you disable fly", ColorUtil.NotificationColors.GREEN, 40));
            }
            if (this.enabledTicks <= 2) {
                this.mc.timer.timerSpeed = 0.3f;
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionY = 0.05;
                this.mc.thePlayer.motionZ = 0.0;
                this.mc.thePlayer.speedOnGround = 0.0f;
                this.mc.thePlayer.speedInAir = 0.0f;
                return;
            }
            this.mc.timer.timerSpeed = 1.0f;
            if (this.mode.getValue().equals("Motion")) {
                this.mc.thePlayer.fallDistance = 0.0f;
                this.mc.thePlayer.motionY = 0.0;
                MovementUtil.strafe(0.85);
                if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                    final EntityPlayerSP thePlayer = this.mc.thePlayer;
                    thePlayer.motionY += 0.5;
                }
                if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    final EntityPlayerSP thePlayer2 = this.mc.thePlayer;
                    thePlayer2.motionY -= 0.5;
                }
            }
            if (this.mode.getValue().equals("Stable")) {
                this.mc.thePlayer.fallDistance = 0.0f;
                this.mc.thePlayer.motionX = 0.0;
                this.mc.thePlayer.motionY = 0.0;
                this.mc.thePlayer.motionZ = 0.0;
                final EntityPlayerSP thePlayer3 = this.mc.thePlayer;
                thePlayer3.posY += 0.1;
                final EntityPlayerSP thePlayer4 = this.mc.thePlayer;
                thePlayer4.posY -= 0.1;
                MovementUtil.strafe(0.85);
                if (this.mc.gameSettings.keyBindJump.isKeyDown()) {
                    final EntityPlayerSP thePlayer5 = this.mc.thePlayer;
                    thePlayer5.motionY += 0.5;
                }
                if (this.mc.gameSettings.keyBindSneak.isKeyDown()) {
                    final EntityPlayerSP thePlayer6 = this.mc.thePlayer;
                    thePlayer6.motionY -= 0.5;
                }
            }
            if (this.mode.getValue().equals("Vanilla")) {
                this.mc.thePlayer.setSprinting(true);
                this.mc.thePlayer.noClip = true;
                this.mc.thePlayer.capabilities.allowFlying = true;
                this.mc.thePlayer.capabilities.setFlySpeed(0.033f);
                this.mc.thePlayer.capabilities.isFlying = true;
                this.mc.thePlayer.onGround = false;
            }
            if (this.enabledTicks % 2 != 0) {
                return;
            }
            if (this.mc.thePlayer.posY - this.player.posY > 10.0) {
                if (this.enabledTicks % 20 == 0) {
                    this.mc.thePlayer.setLocationAndAngles(this.player.posX, this.player.posY, this.player.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch);
                    Client.notify(new Notification.PopupMessage("Final Dad", "You got lag backed! PPlease land on the ground if it keeps happening.", ColorUtil.NotificationColors.RED, 40));
                }
                return;
            }
            if (this.enabledTicks % 10 == 0 && (this.enabledTicks - this.lastFlag >= 6 || this.mc.thePlayer.posY - this.player.posY > 4.0) && this.mc.thePlayer.getDistanceToEntity(this.player) >= 12.0f) {
                this.mc.thePlayer.setLocationAndAngles(this.player.posX, this.player.posY, this.player.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch);
                Client.notify(new Notification.PopupMessage("Final Dad", "You got lag backed! Please land on the ground if it keeps happening.", ColorUtil.NotificationColors.RED, 40));
            }
            this.mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
            this.mc.thePlayer.sendQueue.addToSendQueueSilent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 50.0, this.mc.thePlayer.posZ, true));
        }
        if (event instanceof PacketSentEvent && ((PacketSentEvent)event).getPacket() instanceof C03PacketPlayer) {
            ((PacketSentEvent)event).setCanceled(true);
        }
        if (event instanceof ClientTickEvent && (this.mc.theWorld == null || !this.mc.isInGame())) {
            this.rawSetToggled(false);
        }
        if (event instanceof PacketReceiveEvent) {
            if (((PacketReceiveEvent)event).getPacket() instanceof S12PacketEntityVelocity) {
                ((PacketReceiveEvent)event).setCanceled(true);
                this.lastFlag = this.enabledTicks;
            }
            if (((PacketReceiveEvent)event).getPacket() instanceof S08PacketPlayerPosLook) {
                this.oldX = ((S08PacketPlayerPosLook)((PacketReceiveEvent)event).getPacket()).getX();
                this.oldY = ((S08PacketPlayerPosLook)((PacketReceiveEvent)event).getPacket()).getY();
                this.oldZ = ((S08PacketPlayerPosLook)((PacketReceiveEvent)event).getPacket()).getZ();
                this.oldYaw = ((S08PacketPlayerPosLook)((PacketReceiveEvent)event).getPacket()).yaw;
                this.oldPitch = ((S08PacketPlayerPosLook)((PacketReceiveEvent)event).getPacket()).pitch;
                this.player.posX = this.oldX;
                this.player.posY = this.oldY;
                this.player.posZ = this.oldZ;
                this.player.rotationYaw = this.oldYaw;
                this.player.rotationPitch = this.oldPitch;
                ((PacketReceiveEvent)event).setCanceled(true);
                this.mc.getNetHandler().addToSendQueueSilent(new C03PacketPlayer.C06PacketPlayerPosLook(this.oldX, this.oldY, this.oldZ, this.oldYaw, this.oldPitch, true));
            }
            if (((PacketReceiveEvent)event).getPacket() instanceof S07PacketRespawn) {
                this.rawSetToggled(false);
            }
        }
    }
}
