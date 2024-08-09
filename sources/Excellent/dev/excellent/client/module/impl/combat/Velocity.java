package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.player.JumpEvent;
import dev.excellent.api.event.impl.player.MoveInputEvent;
import dev.excellent.api.event.impl.player.StrafeEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.player.MoveUtil;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.mode.SubMode;
import net.minecraft.network.IPacket;
import net.minecraft.network.play.client.CPlayerDiggingPacket;
import net.minecraft.network.play.client.CPlayerPacket;
import net.minecraft.network.play.server.SConfirmTransactionPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SExplosionPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

@ModuleInfo(name = "Velocity", description = "Убирает отбрасывание, при получении урона.", category = Category.COMBAT)
public class Velocity extends Module {
    private final ModeValue velocityMode = new ModeValue("Режим", this)
            .add(
                    new SubMode("Ванильный"),
                    new SubMode("Грим"),
                    new SubMode("Грим 2"),
                    new SubMode("Грим обновлённый")
//                    new SubMode("FunTime")
            ).setDefault("Ванильный");
    private int toSkip;
    private int await;

    BlockPos blockPos;

    boolean damaged;
    private boolean flag;
    private int ccCooldown;
    private final Listener<PacketEvent> onPacket = event -> {

        IPacket<?> packet = event.getPacket();
        if (mc.player == null || mc.world == null) return;

        var mode = velocityMode.getValue().getName();
        if (packet instanceof SEntityVelocityPacket wrapper) {
            if (mode.equals("Ванильный")) {
                if (wrapper.getEntityID() == mc.player.getEntityId()) {
                    event.cancel();
                }
            }
        }
        if (mode.equals("Грим")) {
            if (packet instanceof SEntityVelocityPacket wrapper) {
                if (wrapper.getEntityID() != mc.player.getEntityId() || toSkip < 0) return;
                toSkip = 8;
                event.cancel();
            }

            if (packet instanceof SConfirmTransactionPacket) {
                if (toSkip < 0) toSkip++;
                else if (toSkip > 1) {
                    toSkip--;
                    event.cancel();
                }
            }

            if (packet instanceof SPlayerPositionLookPacket) toSkip = -8;

        }
        if (mode.equals("Грим 2")) {
            if (packet instanceof SEntityVelocityPacket wrapper) {
                if (wrapper.getEntityID() != mc.player.getEntityId() || await > -5) {
                    return;
                }

                await = 2;
                damaged = true;
                event.cancel();
            }
        }
        if (mode.equals("Грим обновлённый")) {
            if (packet instanceof SEntityVelocityPacket wrapper) {
                if (wrapper.getEntityID() == mc.player.getEntityId()) {
                    double motion = mc.player.isOnGround() ? 0.0E-127D : 1D;
                    wrapper.motionX = (int) (wrapper.motionX * motion);
                    wrapper.motionZ = (int) (wrapper.motionZ * motion);
                }
            }
        }
        if (mode.equals("FunTime") && event.isReceive()) {
            if (ccCooldown > 0) {
                ccCooldown--;
            } else {
                if (packet instanceof SEntityVelocityPacket wrapper) {
                    if (wrapper.getEntityID() == mc.player.getEntityId()) {
                        event.cancel();
                        flag = true;
                    }
                }
                if (packet instanceof SExplosionPacket) {
                    event.cancel();
                    flag = true;
                }
                if (packet instanceof SPlayerPositionLookPacket) {
                    ccCooldown = 5;
                }
            }
        }
    };

    @Override
    public String getSuffix() {
        return velocityMode.getValue().getName();
    }

    private final Listener<JumpEvent> onJump = event -> {
        var mode = velocityMode.getValue().getName();
        if (mode.equals("Грим обновлённый")) {
            if (mc.player.hurtTime != 0) {
                float rotationYaw = (float) (Math.atan2(mc.player.motion.x, mc.player.motion.z) * (360.0F / Math.PI)) + 180F;

                event.setYaw(rotationYaw);
            }
        }
    };
    private final Listener<StrafeEvent> onStrafe = event -> {
        var mode = velocityMode.getValue().getName();
        if (mode.equals("Грим обновлённый") && mc.player.hurtTime != 0) {
            float rotationYaw = (float) (Math.atan2(mc.player.motion.x, mc.player.motion.z) * (360.0F / Math.PI)) + 180F;

            event.setYaw(rotationYaw);
        }
    };
    private final Listener<MoveInputEvent> onMove = event -> {
        var mode = velocityMode.getValue().getName();
        if (mode.equals("Грим обновлённый")) {
            if (mc.player.hurtTime != 0) {
                event.setJump(false);
                event.setForward(0F);
                event.setStrafe(0F);
                float rotationYaw = (float) (Math.atan2(mc.player.motion.x, mc.player.motion.z) * (360.0F / Math.PI)) + 180F;

                MoveUtil.fixMovement(event, rotationYaw);

                mc.player.setJumpTicks(0);

                float prevJumpFactor = mc.player.jumpMovementFactor;

                mc.player.jumpMovementFactor = 0F;

                if (mc.player.isOnGround()) {
                    mc.player.jump();
                }

                mc.player.jumpMovementFactor = prevJumpFactor;
            }
        }
    };


    private final Listener<UpdateEvent> onUpdate = event -> {
        if (velocityMode.is("Грим 2")) {
            await--;
            if (damaged) {
                blockPos = new BlockPos(mc.player.getPositionVec());
                mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, blockPos, Direction.UP));
                mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.START_DESTROY_BLOCK, blockPos, Direction.UP));
                damaged = false;
            }
        }
        if (velocityMode.is("FunTime")) {
            if (flag) {
                if (ccCooldown <= 0) {
                    mc.player.connection.sendPacket(new CPlayerPacket.PositionRotationPacket(mc.player.getPosX(), mc.player.getPosY(), mc.player.getPosZ(), mc.player.rotationYaw, mc.player.rotationPitch, mc.player.isOnGround()));
                    mc.player.connection.sendPacket(new CPlayerDiggingPacket(CPlayerDiggingPacket.Action.STOP_DESTROY_BLOCK, new BlockPos(
                            Math.floor(mc.player.getPositionVec().x),
                            Math.floor(mc.player.getPositionVec().y),
                            Math.floor(mc.player.getPositionVec().z)
                    ), Direction.UP));
                }
                flag = false;
            }
        }
    };

    @Override
    protected void onEnable() {
        super.onEnable();
        reset();
    }

    @Override
    protected void onDisable() {
        super.onDisable();
        reset();
    }

    private void reset() {
        toSkip = 0;
        await = 0;
    }


}



