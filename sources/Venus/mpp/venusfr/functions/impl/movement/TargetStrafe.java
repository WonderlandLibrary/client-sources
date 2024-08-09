/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package mpp.venusfr.functions.impl.movement;

import com.google.common.eventbus.Subscribe;
import mpp.venusfr.events.ActionEvent;
import mpp.venusfr.events.EventDamageReceive;
import mpp.venusfr.events.EventPacket;
import mpp.venusfr.events.EventUpdate;
import mpp.venusfr.events.MovingEvent;
import mpp.venusfr.events.PostMoveEvent;
import mpp.venusfr.functions.api.Category;
import mpp.venusfr.functions.api.Function;
import mpp.venusfr.functions.api.FunctionRegister;
import mpp.venusfr.functions.impl.combat.KillAura;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.util.InputMappings;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.server.SPlayerPositionLookPacket;
import net.minecraft.potion.Effects;
import net.minecraft.util.math.BlockPos;

@FunctionRegister(name="TargetStrafe", type=Category.Movement)
public class TargetStrafe
extends Function {
    private final SliderSetting distanceSetting = new SliderSetting("\u0414\u0438\u0441\u0442\u0430\u043d\u0446\u0438\u044f", 1.0f, 0.1f, 6.0f, 0.05f);
    private final BooleanSetting damageBoostSetting = new BooleanSetting("\u0411\u0443\u0441\u0442 \u0441 \u0434\u0430\u043c\u0430\u0433\u043e\u043c", true);
    private final SliderSetting boostValueSetting = new SliderSetting("\u0417\u043d\u0430\u0447\u0435\u043d\u0438\u0435 \u0431\u0443\u0441\u0442\u0430", 1.5f, 0.1f, 5.0f, 0.05f);
    private final SliderSetting timeSetting = new SliderSetting("\u0412\u0440\u0435\u043c\u044f \u0431\u0443\u0441\u0442\u0430", 10.0f, 1.0f, 20.0f, 1.0f);
    private final BooleanSetting saveTarget = new BooleanSetting("\u0421\u043e\u0445\u0440\u0430\u043d\u044f\u0442\u044c \u0446\u0435\u043b\u044c", true);
    private float side = 1.0f;
    private LivingEntity target = null;
    private final DamagePlayerUtil damageUtil = new DamagePlayerUtil();
    private String targetName = "";
    public StrafeMovement strafeMovement = new StrafeMovement();
    private final KillAura killAura;

    public TargetStrafe(KillAura killAura) {
        this.killAura = killAura;
        this.addSettings(this.distanceSetting, this.damageBoostSetting, this.timeSetting, this.saveTarget);
    }

    @Subscribe
    private void onAction(ActionEvent actionEvent) {
        if (TargetStrafe.mc.player == null || TargetStrafe.mc.world == null) {
            return;
        }
        this.handleEventAction(actionEvent);
    }

    @Subscribe
    public void onMotion(MovingEvent movingEvent) {
        if (TargetStrafe.mc.player == null || TargetStrafe.mc.world == null || TargetStrafe.mc.player.ticksExisted < 10) {
            return;
        }
        boolean bl = InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 65);
        boolean bl2 = InputMappings.isKeyDown(Minecraft.getInstance().getMainWindow().getHandle(), 68);
        LivingEntity livingEntity = this.getTarget();
        if (livingEntity != null) {
            this.targetName = livingEntity.getName().getString();
        }
        this.target = this.shouldSaveTarget(livingEntity) ? this.updateTarget(this.target) : livingEntity;
        if (this.target != null && this.target.isAlive() && this.target.getHealth() > 0.0f) {
            if (TargetStrafe.mc.player.collidedHorizontally) {
                this.side *= -1.0f;
            }
            if (bl) {
                this.side = 1.0f;
            }
            if (bl2) {
                this.side = -1.0f;
            }
            double d = Math.atan2(TargetStrafe.mc.player.getPosZ() - this.target.getPosZ(), TargetStrafe.mc.player.getPosX() - this.target.getPosX());
            double d2 = this.target.getPosX() + (double)((Float)this.distanceSetting.get()).floatValue() * Math.cos(d += MoveUtils.getMotion() / (double)Math.max(TargetStrafe.mc.player.getDistance(this.target), this.distanceSetting.min) * (double)this.side);
            double d3 = this.target.getPosZ() + (double)((Float)this.distanceSetting.get()).floatValue() * Math.sin(d);
            double d4 = this.getYaw(TargetStrafe.mc.player, d2, d3);
            this.damageUtil.time(((Float)this.timeSetting.get()).longValue() * 100L);
            float f = ((Float)this.boostValueSetting.get()).floatValue() / 10.0f;
            double d5 = this.strafeMovement.calculateSpeed(movingEvent, (Boolean)this.damageBoostSetting.get(), this.damageUtil.isNormalDamage(), true, f);
            movingEvent.getMotion().x = d5 * -Math.sin(Math.toRadians(d4));
            movingEvent.getMotion().z = d5 * Math.cos(Math.toRadians(d4));
        }
    }

    @Subscribe
    private void onPostMove(PostMoveEvent postMoveEvent) {
        if (TargetStrafe.mc.player == null || TargetStrafe.mc.world == null) {
            return;
        }
        this.strafeMovement.postMove(postMoveEvent.getHorizontalMove());
    }

    @Subscribe
    private void onPacket(EventPacket eventPacket) {
        if (TargetStrafe.mc.player == null || TargetStrafe.mc.world == null) {
            return;
        }
        if (eventPacket.getType() == EventPacket.Type.RECEIVE) {
            this.damageUtil.onPacketEvent(eventPacket);
            if (eventPacket.getPacket() instanceof SPlayerPositionLookPacket) {
                this.strafeMovement.setOldSpeed(0.0);
            }
        }
    }

    @Subscribe
    private void onDamage(EventDamageReceive eventDamageReceive) {
        if (TargetStrafe.mc.player == null || TargetStrafe.mc.world == null) {
            return;
        }
        this.damageUtil.processDamage(eventDamageReceive);
    }

    @Subscribe
    public void onUpdate(EventUpdate eventUpdate) {
        if (TargetStrafe.mc.player.isOnGround() && !TargetStrafe.mc.gameSettings.keyBindJump.pressed && this.target != null && this.target.isAlive()) {
            TargetStrafe.mc.player.jump();
        }
    }

    @Override
    public void onEnable() {
        this.strafeMovement.setOldSpeed(0.0);
        this.target = null;
        super.onEnable();
    }

    private void handleEventAction(ActionEvent actionEvent) {
        if (this.strafes() && CEntityActionPacket.lastUpdatedSprint != this.strafeMovement.isNeedSprintState()) {
            actionEvent.setSprintState(!CEntityActionPacket.lastUpdatedSprint);
        }
        if (this.strafeMovement.isNeedSwap()) {
            actionEvent.setSprintState(!TargetStrafe.mc.player.serverSprintState);
            this.strafeMovement.setNeedSprintState(true);
        }
    }

    private LivingEntity getTarget() {
        return this.killAura.isState() ? this.killAura.getTarget() : null;
    }

    private LivingEntity updateTarget(LivingEntity livingEntity) {
        for (Entity entity2 : TargetStrafe.mc.world.getAllEntities()) {
            if (!(entity2 instanceof PlayerEntity) || !entity2.getName().getString().equalsIgnoreCase(this.targetName)) continue;
            return (LivingEntity)entity2;
        }
        return livingEntity;
    }

    private boolean shouldSaveTarget(LivingEntity livingEntity) {
        boolean bl = (Boolean)this.saveTarget.get();
        boolean bl2 = livingEntity != null && this.targetName != null;
        return bl && bl2 && this.killAura.isState();
    }

    private double getYaw(LivingEntity livingEntity, double d, double d2) {
        return Math.toDegrees(Math.atan2(d2 - livingEntity.getPosZ(), d - livingEntity.getPosX())) - 90.0;
    }

    public boolean strafes() {
        BlockPos blockPos;
        if (this.isInvalidPlayerState()) {
            return true;
        }
        BlockPos blockPos2 = new BlockPos(TargetStrafe.mc.player.getPositionVec());
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
        return TargetStrafe.mc.player == null || TargetStrafe.mc.world == null || TargetStrafe.mc.player.isSneaking() || TargetStrafe.mc.player.isElytraFlying() || TargetStrafe.mc.player.isInWater() || TargetStrafe.mc.player.isInLava();
    }

    private boolean isSurfaceLiquid(BlockPos blockPos, BlockPos blockPos2) {
        Block block = TargetStrafe.mc.world.getBlockState(blockPos).getBlock();
        Block block2 = TargetStrafe.mc.world.getBlockState(blockPos2).getBlock();
        return block instanceof AirBlock && block2 == Blocks.WATER;
    }

    private boolean isPlayerInWebOrSoulSand(BlockPos blockPos) {
        Material material = TargetStrafe.mc.world.getBlockState(blockPos).getMaterial();
        Block block = TargetStrafe.mc.world.getBlockState(blockPos.down()).getBlock();
        return material == Material.WEB || block instanceof SoulSandBlock;
    }

    private boolean isPlayerAbleToStrafe() {
        return !TargetStrafe.mc.player.abilities.isFlying && !TargetStrafe.mc.player.isPotionActive(Effects.LEVITATION);
    }
}

