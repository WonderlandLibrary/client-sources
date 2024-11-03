package dev.lvstrng.argon.managers;

import dev.lvstrng.argon.Argon;
import dev.lvstrng.argon.event.EventBus;
import dev.lvstrng.argon.event.events.*;
import dev.lvstrng.argon.event.listeners.*;
import dev.lvstrng.argon.utils.Rotation;
import dev.lvstrng.argon.utils.RotationUtil;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;

public final class RotationManager implements PacketSendListener, BreakBlockListener, ItemUseListener, AttackListener, MoveC2SPacketListener, PacketReceiveListener {
    private final EventBus eventBus;
    private boolean shouldUpdateRotation;
    private boolean isApplyingRotation;
    private boolean hasCachedRotation;
    private Rotation targetRotation;
    private float cachedYaw;
    private float cachedPitch;
    private float currentYaw;
    private float currentPitch;
    private boolean isAttacking;

    public RotationManager() {
        this.eventBus = Argon.INSTANCE.EVENT_BUS;
        this.eventBus.registerPriorityListener(PacketSendListener.class, this);
        this.eventBus.registerPriorityListener(AttackListener.class, this);
        this.eventBus.registerPriorityListener(ItemUseListener.class, this);
        this.eventBus.registerPriorityListener(MoveC2SPacketListener.class, this);
        this.eventBus.registerPriorityListener(PacketReceiveListener.class, this);
        this.eventBus.registerPriorityListener(BreakBlockListener.class, this);
        this.shouldUpdateRotation = true;
        this.isApplyingRotation = false;
        this.hasCachedRotation = false;
        this.currentYaw = 0.0f;
        this.currentPitch = 0.0f;
        this.cachedYaw = 0.0f;
        this.cachedPitch = 0.0f;
    }

    public void registerListeners() {
        this.eventBus.registerPriorityListener(PacketSendListener.class, this);
        this.eventBus.registerPriorityListener(AttackListener.class, this);
        this.eventBus.registerPriorityListener(ItemUseListener.class, this);
        this.eventBus.registerPriorityListener(MoveC2SPacketListener.class, this);
        this.eventBus.registerPriorityListener(PacketReceiveListener.class, this);
        this.eventBus.registerPriorityListener(BreakBlockListener.class, this);
    }

    public Rotation getCurrentRotation() {
        return new Rotation(this.currentYaw, this.currentPitch);
    }

    public void resetRotation() {
        this.shouldUpdateRotation = true;
        this.isApplyingRotation = false;
    }

    public void applyRotation() {
        if (shouldApplyRotation()) {
            this.shouldUpdateRotation = false;
            if (!this.isApplyingRotation) {
                this.isApplyingRotation = true;
            }
        }
    }

    public void setTargetRotation(final Rotation rotation) {
        this.targetRotation = rotation;
    }

    public void setTargetRotation(final double yaw, final double pitch) {
        this.setTargetRotation(new Rotation(yaw, pitch));
    }

    private void restoreCachedRotation() {
        Argon.mc.player.setYaw(this.cachedYaw);
        Argon.mc.player.setPitch(this.cachedPitch);
        this.hasCachedRotation = false;
    }

    public void applyRotationToPlayer(final Rotation rotation) {
        this.cachedYaw = Argon.mc.player.getYaw();
        this.cachedPitch = Argon.mc.player.getPitch();
        Argon.mc.player.setYaw((float) rotation.getYaw());
        Argon.mc.player.setPitch((float) rotation.getPitch());
        this.hasCachedRotation = true;
    }

    public void updateCurrentRotation(final Rotation rotation) {
        this.currentYaw = (float) rotation.getYaw();
        this.currentPitch = (float) rotation.getPitch();
    }

    @Override
    public void onAttack(final AttackEvent event) {
        if (!shouldApplyRotation() && this.isAttacking) {
            this.shouldUpdateRotation = true;
            this.isAttacking = false;
        }
    }

    @Override
    public void onItemUse(final ItemUseEvent event) {
        if (!event.isCancelled() && shouldApplyRotation()) {
            this.shouldUpdateRotation = false;
            this.isAttacking = true;
        }
    }

    @Override
    public void onPacketSend(final PacketSendEvent event) {
        Packet<?> packet = event.packet;
        if (packet instanceof PlayerMoveC2SPacket movePacket) {
            this.currentYaw = movePacket.getYaw(this.currentYaw);
            this.currentPitch = movePacket.getPitch(this.currentPitch);
        }
    }

    @Override
    public void onBlockBreak(final BreakBlockEvent event) {
        if (!event.isCancelled() && shouldApplyRotation()) {
            this.shouldUpdateRotation = false;
            this.isAttacking = true;
        }
    }

    @Override
    public void onMoveC2SPacket() {
        if (shouldApplyRotation() && this.targetRotation != null) {
            this.applyRotationToPlayer(this.targetRotation);
            this.updateCurrentRotation(this.targetRotation);
        } else if (this.isApplyingRotation) {
            Rotation currentRotation = new Rotation(this.currentYaw, this.currentPitch);
            Rotation playerRotation = new Rotation(Argon.mc.player.getYaw(), Argon.mc.player.getPitch());
            if (RotationUtil.abs(currentRotation, playerRotation) > 1.0) {
                Rotation smoothRotation = RotationUtil.method486(currentRotation, playerRotation, 0.2);
                this.applyRotationToPlayer(smoothRotation);
                this.updateCurrentRotation(smoothRotation);
            } else {
                this.isApplyingRotation = false;
            }
        }
    }

    @Override
    public void onPacketReceive(final PacketReceiveEvent event) {
        Packet<?> packet = event.packet;
        if (packet instanceof PlayerPositionLookS2CPacket positionPacket) {
            this.currentYaw = positionPacket.getYaw();
            this.currentPitch = positionPacket.getPitch();
        }
    }

    public boolean shouldApplyRotation() {
        return this.shouldUpdateRotation;
    }
}
