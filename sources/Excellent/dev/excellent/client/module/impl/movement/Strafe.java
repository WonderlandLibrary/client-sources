package dev.excellent.client.module.impl.movement;

import dev.excellent.api.event.impl.player.ActionEvent;
import dev.excellent.api.event.impl.player.DamageEvent;
import dev.excellent.api.event.impl.player.MoveEvent;
import dev.excellent.api.event.impl.player.PostMoveEvent;
import dev.excellent.api.event.impl.server.PacketEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.DamagePlayerUtil;
import dev.excellent.impl.util.player.StrafeMovement;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;


@ModuleInfo(name = "Strafe", description = "Позволяет вам маневрировать в воздухе.", category = Category.MOVEMENT)
public class Strafe extends Module {
    public static Singleton<Strafe> singleton = Singleton.create(() -> Module.link(Strafe.class));
    private final BooleanValue damageBoost = new BooleanValue("Ускорение", this, false);

    private final NumberValue boostSpeed = new NumberValue("Сила ускорения", this, 0.7f, 0.1F, 5.0f, 0.1F, () -> !damageBoost.getValue());

    private final DamagePlayerUtil damageUtil = new DamagePlayerUtil();
    private final StrafeMovement strafeMovement = new StrafeMovement();
    private final Listener<DamageEvent> onDamage = this::handleDamageEvent;
    private final Listener<PacketEvent> onPacket = this::handleEventPacket;
    private final Listener<MoveEvent> onMove = this::handleEventMove;
    private final Listener<PostMoveEvent> onPostMove = this::handleEventPostMove;
    private final Listener<ActionEvent> onAction = this::handleEventAction;

    private void handleDamageEvent(DamageEvent damage) {
        if (damageBoost.getValue()) {
            damageUtil.processDamage(damage);
        }
    }

    private void handleEventAction(ActionEvent action) {
        if (strafes()) {
            handleStrafesEventAction(action);
        }
        if (strafeMovement.isNeedSwap()) {
            handleNeedSwapEventAction(action);
        }
    }

    private void handleEventMove(MoveEvent eventMove) {
        if (strafes()) {
            handleStrafesEventMove(eventMove);
        } else {
            strafeMovement.setOldSpeed(0);
        }
    }

    private void handleEventPostMove(PostMoveEvent eventPostMove) {
        strafeMovement.postMove(eventPostMove.getHorizontalMove());
    }

    private void handleEventPacket(PacketEvent packet) {

        if (packet.isReceive()) {
            if (damageBoost.getValue()) {
                damageUtil.onPacketEvent(packet);
            }
            handleReceivePacketEventPacket(packet);
        }
    }

    private void handleStrafesEventAction(ActionEvent action) {
        if (CEntityActionPacket.lastUpdatedSprint != strafeMovement.isNeedSprintState()) {
            action.setSprintState(!CEntityActionPacket.lastUpdatedSprint);
        }
    }

    private void handleStrafesEventMove(MoveEvent eventMove) {

        if (damageBoost.getValue())
            this.damageUtil.time(700L);

        final float damageSpeed = boostSpeed.getValue().floatValue() / 10.0F;
        final double speed = strafeMovement.calculateSpeed(eventMove, damageBoost.getValue(), damageUtil.isNormalDamage(), false, damageSpeed);

        setMoveMotion(eventMove, speed);
    }

    private void setMoveMotion(final MoveEvent move, final double motion) {
        double forward = mc.player.movementInput.moveForward;
        double strafe = mc.player.movementInput.moveStrafe;
        float yaw = mc.player.rotationYaw;
        if (forward == 0 && strafe == 0) {
            move.getMotion().x = 0;
            move.getMotion().z = 0;
        } else {
            if (forward != 0) {
                if (strafe > 0) {
                    yaw += (float) (forward > 0 ? -45 : 45);
                } else if (strafe < 0) {
                    yaw += (float) (forward > 0 ? 45 : -45);
                }
                strafe = 0;
                if (forward > 0) {
                    forward = 1;
                } else if (forward < 0) {
                    forward = -1;
                }
            }
            move.getMotion().x = forward * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f))
                    + strafe * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f));
            move.getMotion().z = forward * motion * MathHelper.sin((float) Math.toRadians(yaw + 90.0f))
                    - strafe * motion * MathHelper.cos((float) Math.toRadians(yaw + 90.0f));
        }
    }

    private void handleNeedSwapEventAction(ActionEvent action) {
        action.setSprintState(!mc.player.serverSprintState);
        strafeMovement.setNeedSwap(false);
    }

    private void handleReceivePacketEventPacket(PacketEvent packet) {
        if (packet.getPacket() instanceof SPlayerPositionLookPacket) {
            strafeMovement.setOldSpeed(0);
        }

    }

    public boolean strafes() {
        if (isInvalidPlayerState()) {
            return false;
        }

        BlockPos playerPosition = new BlockPos(mc.player.getPositionVec());
        BlockPos abovePosition = playerPosition.up();
        BlockPos belowPosition = playerPosition.down();

        if (isSurfaceLiquid(abovePosition, belowPosition)) {
            return false;
        }

        if (isPlayerInWebOrSoulSand(playerPosition)) {
            return false;
        }

        return isPlayerAbleToStrafe();
    }

    private boolean isInvalidPlayerState() {
        return mc.player == null || mc.world == null
                || mc.player.isSneaking()
                || mc.player.isElytraFlying()
                || mc.player.isInWater()
                || mc.player.isInLava();
    }

    private boolean isSurfaceLiquid(BlockPos abovePosition, BlockPos belowPosition) {
        Block aboveBlock = mc.world.getBlockState(abovePosition).getBlock();
        Block belowBlock = mc.world.getBlockState(belowPosition).getBlock();

        return aboveBlock instanceof AirBlock && belowBlock == Blocks.WATER;
    }

    private boolean isPlayerInWebOrSoulSand(BlockPos playerPosition) {
        Material playerMaterial = mc.world.getBlockState(playerPosition).getMaterial();
        Block oneBelowBlock = mc.world.getBlockState(playerPosition.down()).getBlock();

        return playerMaterial == Material.WEB || oneBelowBlock instanceof SoulSandBlock;
    }

    private boolean isPlayerAbleToStrafe() {
        return !mc.player.abilities.isFlying && !mc.player.isPotionActive(Effects.LEVITATION);
    }

    @Override
    public void onEnable() {
        strafeMovement.setOldSpeed(0);
        super.onEnable();
    }
}