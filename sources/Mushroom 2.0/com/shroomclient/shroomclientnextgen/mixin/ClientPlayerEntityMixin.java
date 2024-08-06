package com.shroomclient.shroomclientnextgen.mixin;

import com.mojang.authlib.GameProfile;
import com.shroomclient.shroomclientnextgen.events.Bus;
import com.shroomclient.shroomclientnextgen.events.impl.MotionEvent;
import com.shroomclient.shroomclientnextgen.modules.ModuleManager;
import com.shroomclient.shroomclientnextgen.modules.impl.combat.KillAura3;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.NoSlow;
import com.shroomclient.shroomclientnextgen.modules.impl.movement.Sneak;
import com.shroomclient.shroomclientnextgen.util.C;
import com.shroomclient.shroomclientnextgen.util.MovementUtil;
import com.shroomclient.shroomclientnextgen.util.RotationUtil;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.packet.c2s.play.ClientCommandC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(value = ClientPlayerEntity.class)
public abstract class ClientPlayerEntityMixin extends PlayerEntity {

    @Shadow
    @Final
    public ClientPlayNetworkHandler networkHandler;

    @Shadow
    @Final
    protected MinecraftClient client;

    @Shadow
    private boolean lastSprinting;

    @Shadow
    private boolean lastSneaking;

    @Shadow
    private double lastX;

    @Shadow
    private double lastBaseY;

    @Shadow
    private double lastZ;

    @Shadow
    private float lastYaw;

    @Shadow
    private float lastPitch;

    @Shadow
    private int ticksSinceLastPositionPacketSent;

    @Shadow
    private boolean lastOnGround;

    @Shadow
    private boolean autoJumpEnabled;

    public ClientPlayerEntityMixin(
        World world,
        BlockPos pos,
        float yaw,
        GameProfile gameProfile
    ) {
        super(world, pos, yaw, gameProfile);
    }

    @Shadow
    protected abstract boolean isCamera();

    @Shadow
    public abstract boolean isUsingItem();

    @Shadow
    public abstract float getYaw(float tickDelta);

    @Redirect(
        method = "tickMovement",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/network/ClientPlayerEntity;isUsingItem()Z"
        )
    )
    public boolean noSlow_isUsingItem(ClientPlayerEntity instance) {
        if (
            ModuleManager.isEnabled(NoSlow.class) && NoSlow.doNoSlow
        ) return false;
        return isUsingItem() || KillAura3.isServerBlocking;
    }

    /**
     * @author 112batman
     * @reason Motion event (real)
     */
    @Overwrite
    public void sendMovementPackets() {
        MotionEvent.Pre pre = new MotionEvent.Pre(
            this.isSprinting(),
            this.isSneaking(),
            this.getX(),
            this.getY(),
            this.getZ(),
            this.getYaw(),
            this.getPitch(),
            this.isOnGround(),
            new RotationUtil.Rotation(this.getPitch(), this.getYaw())
        );

        if (Bus.post(pre)) return;

        MovementUtil.PrevServersideYaw = MovementUtil.ServersideYaw;
        MovementUtil.ServersideYaw = pre.getYaw();
        MovementUtil.ServersideRots = new RotationUtil.Rotation(
            pre.getPitch(),
            pre.getYaw()
        );

        // ----------------------
        // sendSprintingPacket
        // ----------------------
        boolean blSprint = pre.isSprinting();
        if (blSprint != this.lastSprinting) {
            ClientCommandC2SPacket.Mode mode = blSprint
                ? ClientCommandC2SPacket.Mode.START_SPRINTING
                : ClientCommandC2SPacket.Mode.STOP_SPRINTING;
            this.networkHandler.sendPacket(
                    new ClientCommandC2SPacket(this, mode)
                );
            this.lastSprinting = blSprint;
        }

        // ----------------------
        // sendMovementPackets
        // ----------------------
        boolean bl = pre.isSneaking();
        if (bl != this.lastSneaking) {
            ClientCommandC2SPacket.Mode mode = bl
                ? ClientCommandC2SPacket.Mode.PRESS_SHIFT_KEY
                : ClientCommandC2SPacket.Mode.RELEASE_SHIFT_KEY;
            this.networkHandler.sendPacket(
                    new ClientCommandC2SPacket(this, mode)
                );
            this.lastSneaking = bl;
        }
        if (this.isCamera()) {
            boolean bl3;
            double d = pre.getX() - this.lastX;
            double e = pre.getY() - this.lastBaseY;
            double f = pre.getZ() - this.lastZ;
            double g = pre.getYaw() - this.lastYaw;
            double h = pre.getPitch() - this.lastPitch;
            ++this.ticksSinceLastPositionPacketSent;
            boolean bl2 =
                MathHelper.squaredMagnitude(d, e, f) >
                    MathHelper.square(2.0E-4) ||
                this.ticksSinceLastPositionPacketSent >= 20;
            boolean bl4 = bl3 = g != 0.0 || h != 0.0;
            if (this.hasVehicle()) {
                Vec3d vec3d = this.getVelocity();
                this.networkHandler.sendPacket(
                        new PlayerMoveC2SPacket.Full(
                            vec3d.x,
                            -999.0,
                            vec3d.z,
                            pre.getYaw(),
                            pre.getPitch(),
                            pre.isOnGround()
                        )
                    );
                bl2 = false;
            } else if (bl2 && bl3) {
                this.networkHandler.sendPacket(
                        new PlayerMoveC2SPacket.Full(
                            pre.getX(),
                            pre.getY(),
                            pre.getZ(),
                            pre.getYaw(),
                            pre.getPitch(),
                            pre.isOnGround()
                        )
                    );
            } else if (bl2) {
                this.networkHandler.sendPacket(
                        new PlayerMoveC2SPacket.PositionAndOnGround(
                            pre.getX(),
                            pre.getY(),
                            pre.getZ(),
                            pre.isOnGround()
                        )
                    );
            } else if (bl3) {
                this.networkHandler.sendPacket(
                        new PlayerMoveC2SPacket.LookAndOnGround(
                            pre.getYaw(),
                            pre.getPitch(),
                            pre.isOnGround()
                        )
                    );
            } else if (this.lastOnGround != pre.isOnGround()) {
                this.networkHandler.sendPacket(
                        new PlayerMoveC2SPacket.OnGroundOnly(pre.isOnGround())
                    );
            }
            if (bl2) {
                this.lastX = pre.getX();
                this.lastBaseY = pre.getY();
                this.lastZ = pre.getZ();
                this.ticksSinceLastPositionPacketSent = 0;
            }
            if (bl3) {
                this.lastYaw = pre.getYaw();
                this.lastPitch = pre.getPitch();
            }
            this.lastOnGround = pre.isOnGround();
            this.autoJumpEnabled = this.client.options.getAutoJump().getValue();
        }

        if (C.p() == null) return;

        Bus.post(
            new MotionEvent.Post(
                pre.isSprinting(),
                pre.isSneaking(),
                pre.getX(),
                pre.getY(),
                pre.getZ(),
                pre.getYaw(),
                pre.getPitch(),
                pre.isOnGround()
            )
        );
    }

    @Inject(method = "shouldSlowDown", at = @At("RETURN"), cancellable = true)
    public void shouldSlowDown(CallbackInfoReturnable<Boolean> cir) {
        if (Sneak.shouldSneakNormalSpeed()) {
            cir.setReturnValue(false);
        }
    }
}
