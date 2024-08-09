package fun.ellant.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import fun.ellant.events.*;
import fun.ellant.functions.api.Category;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.api.FunctionRegister;
import fun.ellant.functions.impl.combat.KillAura;
import fun.ellant.functions.settings.impl.BooleanSetting;
import fun.ellant.functions.settings.impl.ModeSetting;
import fun.ellant.functions.settings.impl.SliderSetting;
import fun.ellant.utils.player.DamagePlayerUtil;
import fun.ellant.utils.player.MoveUtils;
import fun.ellant.utils.player.StrafeMovement;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;


@FunctionRegister(name = "Strafe", type = Category.MOVEMENT, desc = "Позволяет вам стрейфится")
public class Strafe extends Function {
    private final ModeSetting mode = new ModeSetting("Обход", "Обычный", "Обычный", "Grim Hard", "Grim");
    private final BooleanSetting damageBoost = new BooleanSetting("Буст с дамагом", false).setVisible(() -> mode.is("Обычный"));;
    private final SliderSetting boostSpeed = new SliderSetting("Значение буста", 0.7f, 0.1F, 5.0f, 0.1F).setVisible(() -> mode.is("Обычный"));
    private final DamagePlayerUtil damageUtil = new DamagePlayerUtil();
    private final StrafeMovement strafeMovement = new StrafeMovement();

    private final TargetStrafe targetStrafe;
    private final KillAura killAura;

    public Strafe(TargetStrafe targetStrafe, KillAura killAura) {
        this.targetStrafe = targetStrafe;
        this.killAura = killAura;
        addSettings(mode, damageBoost, boostSpeed);
    }

    @Subscribe
    private void onAction(ActionEvent e) {
        handleEventAction(e);
    }

    @Subscribe
    private void onMoving(MovingEvent e) {
        handleEventMove(e);
    }

    @Subscribe
    private void onPostMove(PostMoveEvent e) {
        handleEventPostMove(e);
    }

    @Subscribe
    private void onPacket(EventPacket e) {
        handleEventPacket(e);
    }

    @Subscribe
    private void onDamage(EventDamageReceive e) {
        handleDamageEvent(e);
    }

    private void handleDamageEvent(EventDamageReceive damage) {
        if (damageBoost.get()) {
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

    private void handleEventMove(MovingEvent eventMove) {
        if (strafes()) {
            handleStrafesEventMove(eventMove);
        } else {
            strafeMovement.setOldSpeed(0);
        }
    }

    private void handleEventPostMove(PostMoveEvent eventPostMove) {
        strafeMovement.postMove(eventPostMove.getHorizontalMove());
    }

    private void handleEventPacket(EventPacket packet) {

        if (packet.getType() == EventPacket.Type.RECEIVE) {
            if (damageBoost.get()) {
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

    private void handleStrafesEventMove(MovingEvent eventMove) {
        if (targetStrafe.isState() && (killAura.isState() && killAura.getTarget() != null)) {
            return;
        }
        if (mode.is("Grim Hard")) {
            if (Strafe.mc.player.getDistance(killAura.getTarget()) <= 2.0f && (Strafe.mc.gameSettings.keyBindForward.isKeyDown() || Strafe.mc.gameSettings.keyBindRight.isKeyDown() || Strafe.mc.gameSettings.keyBindLeft.isKeyDown() || Strafe.mc.gameSettings.keyBindBack.isKeyDown())) {
                float speed = 1.185f;
                Strafe.mc.player.getMotion().x *= speed;
                Strafe.mc.player.getMotion().z *= speed;
            }
        } else if (mode.is("Grim")) {
            if (Strafe.mc.player.getDistance(killAura.getTarget()) <= 2.0f && (Strafe.mc.gameSettings.keyBindForward.isKeyDown() || Strafe.mc.gameSettings.keyBindRight.isKeyDown() || Strafe.mc.gameSettings.keyBindLeft.isKeyDown() || Strafe.mc.gameSettings.keyBindBack.isKeyDown())) {
                float speed = 1.155f;
                Strafe.mc.player.getMotion().x *= speed;
                Strafe.mc.player.getMotion().z *= speed;
            }
        } else if (mode.is("Обычный")) {

            if (damageBoost.get())
                this.damageUtil.time(700L);

            final float damageSpeed = boostSpeed.get().floatValue() / 10.0F;
            final double speed = strafeMovement.calculateSpeed(eventMove, damageBoost.get(), damageUtil.isNormalDamage(), false, damageSpeed);

            MoveUtils.MoveEvent.setMoveMotion(eventMove, speed);
        }
    }

    private void handleNeedSwapEventAction(ActionEvent action) {
        action.setSprintState(!mc.player.serverSprintState);
        strafeMovement.setNeedSwap(false);
    }

    private void handleReceivePacketEventPacket(EventPacket packet) {
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
    public boolean onEnable() {
        strafeMovement.setOldSpeed(0);
        super.onEnable();
        return false;
    }
}
