package tk.rektsky.Module.Movement;

import tk.rektsky.Module.*;
import tk.rektsky.Module.Settings.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import tk.rektsky.Event.*;
import tk.rektsky.Utils.*;
import tk.rektsky.Utils.Display.*;
import tk.rektsky.Module.Render.*;
import tk.rektsky.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import tk.rektsky.Event.Events.*;
import net.minecraft.network.play.server.*;
import net.minecraft.client.entity.*;

public class Speed extends Module
{
    public ListSetting modeSetting;
    public double oldX;
    public double oldY;
    public double oldZ;
    public long disable;
    public float oldYaw;
    public float oldPitch;
    int lastFlag;
    public EntityOtherPlayerMP player;
    
    public Speed() {
        super("Speed", "Walk faster", 0, Category.MOVEMENT);
        this.modeSetting = new ListSetting("Mode", new String[] { "OnGround", "BHop", "LowHop" }, "OnGround");
        this.oldX = 0.0;
        this.oldY = 0.0;
        this.oldZ = 0.0;
        this.disable = 0L;
        this.oldYaw = 0.0f;
        this.oldPitch = 0.0f;
        this.lastFlag = 0;
    }
    
    @Override
    public void onEnable() {
        this.lastFlag = 0;
        this.oldX = this.mc.thePlayer.posX;
        this.oldY = this.mc.thePlayer.posY;
        this.oldZ = this.mc.thePlayer.posZ;
        this.oldYaw = this.mc.thePlayer.rotationYaw;
        this.oldPitch = this.mc.thePlayer.rotationPitch;
        (this.player = new EntityOtherPlayerMP(this.mc.theWorld, this.mc.thePlayer.getGameProfile())).copyLocationAndAnglesFrom(this.mc.thePlayer);
        this.player.rotationYawHead = this.mc.thePlayer.rotationYawHead;
        this.player.renderYawOffset = this.mc.thePlayer.renderYawOffset;
        this.mc.thePlayer.noClip = true;
    }
    
    @Override
    public String getSuffix() {
        return this.modeSetting.getValue();
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
        this.mc.thePlayer.speedInAir = 0.02f;
    }
    
    private boolean isMoving() {
        return this.mc.thePlayer != null && (this.mc.thePlayer.movementInput.moveForward != 0.0f || this.mc.thePlayer.movementInput.moveStrafe != 0.0f);
    }
    
    @Override
    public void onEvent(final Event event) {
        if (event instanceof RenderEvent) {}
        if (event instanceof WorldTickPostEvent) {
            this.mc.thePlayer.setSprinting(true);
            if (MovementUtil.isMoving()) {
                if (!this.modeSetting.getValue().equals("OnGround")) {
                    if (this.mc.thePlayer.onGround) {
                        this.mc.thePlayer.jump();
                    }
                    if (!this.mc.gameSettings.keyBindJump.isKeyDown() && this.modeSetting.getValue().equals("LowHop")) {
                        final EntityPlayerSP thePlayer = this.mc.thePlayer;
                        thePlayer.motionY -= 0.1;
                    }
                }
                MovementUtil.strafe(0.6);
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
