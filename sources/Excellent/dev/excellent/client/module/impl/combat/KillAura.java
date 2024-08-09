package dev.excellent.client.module.impl.combat;

import dev.excellent.api.event.impl.player.MotionEvent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.client.module.api.Category;
import dev.excellent.client.module.api.Module;
import dev.excellent.client.module.api.ModuleInfo;
import dev.excellent.client.rotation.Rotation;
import dev.excellent.client.rotation.RotationHandler;
import dev.excellent.client.target.TargetHandler;
import dev.excellent.impl.util.pattern.Singleton;
import dev.excellent.impl.util.player.RayTraceUtil;
import dev.excellent.impl.util.rotation.AuraUtil;
import dev.excellent.impl.util.time.TimerUtil;
import dev.excellent.impl.value.impl.BooleanValue;
import dev.excellent.impl.value.impl.ModeValue;
import dev.excellent.impl.value.impl.MultiBooleanValue;
import dev.excellent.impl.value.impl.NumberValue;
import dev.excellent.impl.value.mode.SubMode;
import lombok.Getter;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.AxeItem;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.potion.Effects;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.vector.Vector3d;

@Getter
@ModuleInfo(name = "Kill Aura", description = "Осуществляет автоматические удары по заданным существам.", category = Category.COMBAT)
public class KillAura extends Module {
    public static Singleton<KillAura> singleton = Singleton.create(() -> Module.link(KillAura.class));
    private final NumberValue attackRange = new NumberValue("Дистанция", this, 3, 3, 6, 0.1f);
    private final ModeValue modeValue = new ModeValue("Режим", this).add(SubMode.of("Обычная", "ХВХ"));
    public final ModeValue sortMode = new ModeValue("Сортировка по", this)
            .add(SubMode.of("Всему", "Здоровью", "Дистанции", "Время жизни"));

    private final MultiBooleanValue targets = new MultiBooleanValue("Таргеты", this)
            .add(
                    new BooleanValue("Игроки", true),
                    new BooleanValue("Голые", true),
                    new BooleanValue("Невидимые", true),
                    new BooleanValue("Мобы", true)
            );
    private final MultiBooleanValue checks = new MultiBooleanValue("Настройки", this)
            .add(
                    new BooleanValue("Только криты", true),
                    new BooleanValue("Умные криты", true),
                    new BooleanValue("Ломать щит", true),
                    new BooleanValue("Таргет есп", true),
                    new BooleanValue("Сбрасывать спринт", true)
            );
    public final ModeValue targetEspMode = new ModeValue("Режим есп", this, () -> !checks.get("Таргет есп").getValue())
            .add(SubMode.of("Новый", "Старый", "Кругляшок"));
    public LivingEntity target;

    private final TimerUtil timer = TimerUtil.create();
    private double prevPosY;
    private boolean canCritical;
    private float lastYaw;
    private int oldSlot = -1;

    @Override
    public void toggle() {
        super.toggle();
        target = null;
    }

    private final Listener<UpdateEvent> onUpdate = event -> {
        target = TargetHandler.getTarget(attackRange.getValue().floatValue());
        if (target == null) {
            return;
        }
        if (modeValue.is("Обычная"))
            updateRotation();
        updateAttack();
    };

    private final Listener<MotionEvent> onMotion = event -> {
        double posY = event.getY();

        canCritical = !event.isOnGround() && posY < prevPosY;
        prevPosY = posY;
        if (target == null) {
            return;
        }
        if (modeValue.is("ХВХ"))
            HvHRotation(event);
    };

    private void updateRotation() {
        Vector3d vec = target.getPositionVec().add(0, MathHelper.clamp(mc.player.getEyePosition(mc.getRenderPartialTicks()).y - target.getPosY(),
                        0, target.getHeight() * (AuraUtil.getStrictDistance((target)) / Math.max(mc.playerController.extendedReach() ? 6 : 3, attackRange.getValue().floatValue()))), 0)
                .subtract(mc.player.getEyePosition(mc.getRenderPartialTicks())).normalize();

        float rawYaw = (float) Math.toDegrees(Math.atan2(-vec.x, vec.z));
        float rawPitch = (float) MathHelper.clamp(Math.toDegrees(Math.asin(-vec.y)), -90, 90);

        float yawDelta = (int) MathHelper.wrapDegrees(rawYaw - mc.player.rotationYaw);
        float pitchDelta = rawPitch - mc.player.rotationPitch;
        float yawSpeed = 40;
        float pitchSpeed = 2;

        float clampedYaw = MathHelper.clamp(yawDelta, -yawSpeed, yawSpeed);
        float clampedPitch = MathHelper.clamp(pitchDelta, -pitchSpeed, pitchSpeed);

        RotationHandler.update(new Rotation(
                        mc.player.rotationYaw + clampedYaw,
                        mc.player.rotationPitch + (mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY ? 0 : clampedPitch)),
                360, 1, 5);
    }

    @Deprecated
    private void HvHRotation(MotionEvent e) {
        Vector3d vec = target.getPositionVec().add(0, MathHelper.clamp(mc.player.getEyePosition(mc.getRenderPartialTicks()).y - target.getPosY(),
                        0, target.getHeight() * (AuraUtil.getStrictDistance((target)) / Math.max(mc.playerController.extendedReach() ? 6 : 3, attackRange.getValue().floatValue()))), 0)
                .subtract(mc.player.getEyePosition(mc.getRenderPartialTicks())).normalize();

        float rawYaw = (float) Math.toDegrees(Math.atan2(-vec.x, vec.z));
        float rawPitch = (float) MathHelper.clamp(Math.toDegrees(Math.asin(-vec.y)), -89, 89);

        float yawDelta = (int) MathHelper.wrapDegrees(rawYaw - mc.player.rotationYaw);
        float pitchDelta = rawPitch - mc.player.rotationPitch;
        float yawSpeed = 125;
        float pitchSpeed = 50;

        float clampedYaw = MathHelper.clamp(yawDelta, -yawSpeed, yawSpeed);
        float clampedPitch = MathHelper.clamp(pitchDelta, -pitchSpeed, pitchSpeed);

        RotationHandler.update(new Rotation(
                        mc.player.rotationYaw + clampedYaw,
                        mc.player.rotationPitch + (mc.objectMouseOver.getType() == RayTraceResult.Type.ENTITY ? 0 : clampedPitch)),
                360, 1, 5);


    }

    @Override
    public String getSuffix() {
        return modeValue.getValue().getName();
    }

    private void updateAttack() {
        int currSlot = mc.player.inventory.currentItem;
        int axeSlot = getAxeSlot();
        boolean shouldBreak = axeSlot != -1;

        if (shouldBreak) {
            if (currSlot != axeSlot) {
                mc.player.inventory.currentItem = axeSlot;
                mc.playerController.syncCurrentPlayItem();
                oldSlot = currSlot;
            }
        } else if (oldSlot != -1) {
            mc.player.inventory.currentItem = oldSlot;
            mc.playerController.syncCurrentPlayItem();
            oldSlot = -1;
        }

        if (shouldAttack() || shouldBreak) {
            if ((!modeValue.is("Обычная") || RayTraceUtil.rayTraceSingleEntity(mc.player.rotationYaw, mc.player.rotationPitch, Math.max(mc.playerController.extendedReach() ? 6 : 3, attackRange.getValue().floatValue()), target))) {
                if (mc.player.isActiveItemStackBlocking()) {
                    mc.playerController.onStoppedUsingItem(mc.player);
                }

                boolean sprinting = checks.isEnabled("Сбрасывать спринт") && mc.player.serverSprintState;


                if (sprinting) {
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
                }

                attackEntity(target);

                if (sprinting) {
                    mc.player.setSprinting(true);
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_SPRINTING));
                }
            }
        }
    }

    private void attackEntity(Entity entity) {
        mc.playerController.attackEntity(mc.player, entity);
        mc.player.swingArm(Hand.MAIN_HAND);
        timer.reset();
    }

    public boolean shouldAttack() {
        boolean isReady = timer.hasReached(250) && mc.player.getCooledAttackStrength(1.5F) >= 1F;
        boolean isInAirStrict = mc.player.movementInput.jump || !mc.player.isOnGround();

        return isReady && !((checks.isEnabled("Только криты") && shouldCritical()) && !(checks.isEnabled("Умные криты") && !isInAirStrict) && !canCritical);
    }

    private int getAxeSlot() {
        if (!target.isActiveItemStackBlocking() || !checks.isEnabled("Ломать щит")) {
            return -1;
        }

        if (Math.abs(MathHelper.wrapDegrees(mc.player.rotationYaw - target.rotationYaw - 180)) > 90) {
            return -1;
        }

        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.mainInventory.get(i).getItem() instanceof AxeItem) {
                return i;
            }
        }

        return -1;
    }

    private boolean shouldCritical() {
        boolean isDeBuffed = mc.player.isPotionActive(Effects.LEVITATION) || mc.player.isPotionActive(Effects.BLINDNESS) || mc.player.isPotionActive(Effects.SLOW_FALLING);
        boolean isInLiquid = mc.player.areEyesInFluid(FluidTags.WATER) || mc.player.areEyesInFluid(FluidTags.LAVA);
        boolean isFlying = mc.player.abilities.isFlying || mc.player.isElytraFlying();
        boolean isClimbing = mc.player.isOnLadder();
        boolean isCantJump = mc.player.isPassenger();

        return !(isDeBuffed || isInLiquid || isFlying || isClimbing || isCantJump);
    }

}