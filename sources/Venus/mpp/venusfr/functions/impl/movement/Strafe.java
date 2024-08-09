/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.ActionEvent;
import mpp.venusfr.events.EventDamageReceive;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.MovingEvent;
import mpp.venusfr.events.PostMoveEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.combat.KillAura;
import mpp.venusfr.functions.impl.movement.TargetStrafe;
import mpp.venusfr.functions.settings.impl.BooleanSetting;
import mpp.venusfr.functions.settings.impl.SliderSetting;
import mpp.venusfr.utils.player.DamagePlayerUtil;
import mpp.venusfr.utils.player.MoveUtils;
import mpp.venusfr.utils.player.StrafeMovement;
import net.minecraft.block.AirBlock;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoulSandBlock;
import net.minecraft.block.material.Material;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;

@FunctionRegister(name="Strafe", type=Category.Movement)
public class Strafe
extends Function {
    private final BooleanSetting damageBoost = new BooleanSetting("\u0411\u0443\u0441\u0442 \u0441 \u0434\u0430\u043c\u0430\u0433\u043e\u043c", false);
    private final SliderSetting boostSpeed = new SliderSetting("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u0431\u0443\u0441\u0442\u0430", 0.7f, 0.1f, 5.0f, 0.1f);
    private final DamagePlayerUtil damageUtil = new DamagePlayerUtil();
    private final StrafeMovement strafeMovement = new StrafeMovement();
    private final TargetStrafe targetStrafe;
    private final KillAura killAura;

    public Strafe(TargetStrafe targetStrafe, KillAura killAura) {
        this.targetStrafe = targetStrafe;
        this.killAura = killAura;
        this.addSettings(this.damageBoost, this.boostSpeed);
    }

    @Subscribe
    private void onAction(ActionEvent actionEvent) {
        this.handleEventAction(actionEvent);
    }

    @Subscribe
    private void onMoving(MovingEvent movingEvent) {
        this.handleEventMove(movingEvent);
    }

    @Subscribe
    private void onPostMove(PostMoveEvent postMoveEvent) {
        this.handleEventPostMove(postMoveEvent);
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        this.handleEventPacket(eventPacket);
    }

    @Subscribe
    private void onDamage(EventDamageReceive eventDamageReceive) {
        this.handleDamageEvent(eventDamageReceive);
    }

    private void handleDamageEvent(EventDamageReceive eventDamageReceive) {
        if (((Boolean)this.damageBoost.get()).booleanValue()) {
            this.damageUtil.processDamage(eventDamageReceive);
        }
    }

    private void handleEventAction(ActionEvent actionEvent) {
        if (this.strafes()) {
            this.handleStrafesEventAction(actionEvent);
        }
        if (this.strafeMovement.isNeedSwap()) {
            this.handleNeedSwapEventAction(actionEvent);
        }
    }

    private void handleEventMove(MovingEvent movingEvent) {
        if (this.strafes()) {
            this.handleStrafesEventMove(movingEvent);
        } else {
            this.strafeMovement.setOldSpeed(0.0);
        }
    }

    private void handleEventPostMove(PostMoveEvent postMoveEvent) {
        this.strafeMovement.postMove(postMoveEvent.getHorizontalMove());
    }

    private void handleEventPacket(EventPacket eventPacket) {
        if (eventPacket.getType() == EventPacket.Type.RECEIVE) {
            if (((Boolean)this.damageBoost.get()).booleanValue()) {
                this.damageUtil.onPacketEvent(eventPacket);
            }
            this.handleReceivePacketEventPacket(eventPacket);
        }
    }

    private void handleStrafesEventAction(ActionEvent actionEvent) {
        if (CEntityActionPacket.lastUpdatedSprint != this.strafeMovement.isNeedSprintState()) {
            actionEvent.setSprintState(!CEntityActionPacket.lastUpdatedSprint);
        }
    }

    private void handleStrafesEventMove(MovingEvent movingEvent) {
        if (this.targetStrafe.isState() && this.killAura.isState() && this.killAura.getTarget() != null) {
            return;
        }
        if (((Boolean)this.damageBoost.get()).booleanValue()) {
            this.damageUtil.time(700L);
        }
        float f = ((Float)this.boostSpeed.get()).floatValue() / 10.0f;
        double d = this.strafeMovement.calculateSpeed(movingEvent, (Boolean)this.damageBoost.get(), this.damageUtil.isNormalDamage(), false, f);
        MoveUtils.MoveEvent.setMoveMotion(movingEvent, d);
    }

    private void handleNeedSwapEventAction(ActionEvent actionEvent) {
        actionEvent.setSprintState(!Strafe.mc.player.serverSprintState);
        this.strafeMovement.setNeedSwap(true);
    }

    private void handleReceivePacketEventPacket(EventPacket eventPacket) {
        if (eventPacket.getPacket() instanceof SPlayerPositionLookPacket) {
            this.strafeMovement.setOldSpeed(0.0);
        }
    }

    public boolean strafes() {
        BlockPos blockPos;
        if (this.isInvalidPlayerState()) {
            return true;
        }
        BlockPos blockPos2 = new BlockPos(Strafe.mc.player.getPositionVec());
        BlockPos blockPos3 = blockPos2.up();
        if (this.isSurfaceLiquid(blockPos3, blockPos = blockPos2.down())) {
            return true;
        }
        if (this.isPlayerInWebOrSoulSand(blockPos2)) {
            return true;
        }
        return this.isPlayerAbleToStrafe();
    }

    private boolean isInvalidPlayerState() {
        return Strafe.mc.player == null || Strafe.mc.world == null || Strafe.mc.player.isSneaking() || Strafe.mc.player.isElytraFlying() || Strafe.mc.player.isInWater() || Strafe.mc.player.isInLava();
    }

    private boolean isSurfaceLiquid(BlockPos blockPos, BlockPos blockPos2) {
        Block block = Strafe.mc.world.getBlockState(blockPos).getBlock();
        Block block2 = Strafe.mc.world.getBlockState(blockPos2).getBlock();
        return block instanceof AirBlock && block2 == Blocks.WATER;
    }

    private boolean isPlayerInWebOrSoulSand(BlockPos blockPos) {
        Material material = Strafe.mc.world.getBlockState(blockPos).getMaterial();
        Block block = Strafe.mc.world.getBlockState(blockPos.down()).getBlock();
        return material == Material.WEB || block instanceof SoulSandBlock;
    }

    private boolean isPlayerAbleToStrafe() {
        return !Strafe.mc.player.abilities.isFlying && !Strafe.mc.player.isPotionActive(Effects.LEVITATION);
    }

    @Override
    public void onEnable() {
        this.strafeMovement.setOldSpeed(0.0);
        super.onEnable();
    }
}

