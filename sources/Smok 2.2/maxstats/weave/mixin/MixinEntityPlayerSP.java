package maxstats.weave.mixin;

import com.mojang.authlib.GameProfile;
import me.sleepyfish.smok.Smok;
import me.sleepyfish.smok.rats.impl.blatant.Scaffold;
import me.sleepyfish.smok.utils.entities.Utils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C03PacketPlayer.C04PacketPlayerPosition;
import net.minecraft.network.play.client.C03PacketPlayer.C05PacketPlayerLook;
import net.minecraft.network.play.client.C03PacketPlayer.C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C0BPacketEntityAction.Action;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

// Class from SMok Client by SleepyFish
@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer {

    @Shadow
    @Final
    public NetHandlerPlayClient sendQueue;
    @Shadow
    private boolean serverSneakState;
    @Shadow
    private boolean serverSprintState;
    @Shadow
    private double lastReportedPosX;
    @Shadow
    private double lastReportedPosY;
    @Shadow
    private double lastReportedPosZ;
    @Shadow
    private float lastReportedYaw;
    @Shadow
    private float lastReportedPitch;
    @Shadow
    private int positionUpdateTicks;

    @Shadow
    public abstract boolean isSneaking();

    @Shadow
    protected abstract boolean isCurrentViewEntity();

    @Shadow
    public abstract void setSprinting(boolean var1);

    public MixinEntityPlayerSP(World world, GameProfile profile) {
        super(world, profile);
    }

    /**
     * @author sleepy fish
     * @reason server sided rotations
     */
    @Overwrite
    public void onUpdateWalkingPlayer() {
        if (Minecraft.getMinecraft().theWorld != null) {

            float yaw = this.rotationYaw;
            float pitch = this.rotationPitch;

            if (Smok.inst.rotManager.isRotating()) {
                if (Smok.inst.rotManager.yaw != this.rotationYaw) {
                    yaw = Smok.inst.rotManager.yaw;
                }

                if (Smok.inst.rotManager.pitch != this.rotationPitch) {
                    pitch = Smok.inst.rotManager.pitch;
                }

                // SMok: Client sided rotation ...
                this.rotationYawHead = yaw;
                this.renderYawOffset = yaw;

                if (Smok.inst.debugMode) {
                    this.rotationYaw = yaw;
                    this.rotationPitch = pitch;
                }
            }

            boolean var1 = this.isSprinting();

            if (Smok.inst.ratManager.getRatByClass(Scaffold.class).isEnabled()) {
                if (Utils.holdingBlock() && Smok.inst.mc.thePlayer.onGround && Smok.inst.rotManager.isRotating()) {
                    KeyBinding.setKeyBindState(Smok.inst.mc.gameSettings.keyBindSprint.getKeyCode(), true);
                    KeyBinding.onTick(Smok.inst.mc.gameSettings.keyBindSprint.getKeyCode());
                    KeyBinding.setKeyBindState(Smok.inst.mc.gameSettings.keyBindSprint.getKeyCode(), false);
                    KeyBinding.onTick(Smok.inst.mc.gameSettings.keyBindSprint.getKeyCode());
                    this.setSprinting(false);
                    this.serverSprintState = false;
                    var1 = false;
                }
            } else {
                if (var1 != this.serverSprintState) {
                    if (var1) {
                        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, Action.START_SPRINTING));
                    } else {
                        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, Action.STOP_SPRINTING));
                    }

                    this.serverSprintState = var1;
                }

                boolean var2 = this.isSneaking();
                if (var2 != this.serverSneakState) {
                    if (var2) {
                        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, Action.START_SNEAKING));
                    } else {
                        this.sendQueue.addToSendQueue(new C0BPacketEntityAction(this, Action.STOP_SNEAKING));
                    }

                    this.serverSneakState = var2;
                }
            }

            if (this.isCurrentViewEntity()) {
                double var3 = this.posX - this.lastReportedPosX;
                double var5 = this.getEntityBoundingBox().minY - this.lastReportedPosY;
                double var7 = this.posZ - this.lastReportedPosZ;
                double var9 = (yaw - this.lastReportedYaw);
                double var11 = (pitch - this.lastReportedPitch);
                boolean var13 = var3 * var3 + var5 * var5 + var7 * var7 > 9.0E-4 || this.positionUpdateTicks >= 20;
                boolean var14 = var9 != 0.0 || var11 != 0.0;

                if (this.ridingEntity == null) {
                    if (var13 && var14) {
                        this.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(this.posX, this.getEntityBoundingBox().minY, this.posZ, yaw, pitch, this.onGround));
                    } else if (var13) {
                        this.sendQueue.addToSendQueue(new C04PacketPlayerPosition(this.posX, this.getEntityBoundingBox().minY, this.posZ, this.onGround));
                    } else if (var14) {
                        this.sendQueue.addToSendQueue(new C05PacketPlayerLook(yaw, pitch, this.onGround));
                    } else {
                        this.sendQueue.addToSendQueue(new C03PacketPlayer(this.onGround));
                    }
                } else {
                    this.sendQueue.addToSendQueue(new C06PacketPlayerPosLook(this.motionX, -999.0, this.motionZ, yaw, pitch, this.onGround));
                    var13 = false;
                }

                this.positionUpdateTicks++;

                if (var13) {
                    this.lastReportedPosX = this.posX;
                    this.lastReportedPosY = this.getEntityBoundingBox().minY;
                    this.lastReportedPosZ = this.posZ;
                    this.positionUpdateTicks = 0;
                }

                if (var14) {
                    this.lastReportedYaw = yaw;
                    this.lastReportedPitch = pitch;
                }
            }
        }
    }



}