package im.expensive.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import com.mojang.blaze3d.platform.GlStateManager;
import im.expensive.Furious;
import im.expensive.command.friends.FriendStorage;
import im.expensive.events.EventDisplay;
import im.expensive.events.EventInput;
import im.expensive.events.EventMotion;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.impl.render.HUD;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.animations.Animation;
import im.expensive.utils.animations.impl.DecelerateAnimation;
import im.expensive.utils.math.SensUtils;
import im.expensive.utils.math.StopWatch;
import im.expensive.utils.math.Vector4i;
import im.expensive.utils.player.InventoryUtil;
import im.expensive.utils.player.MouseUtil;
import im.expensive.utils.player.MoveUtils;
import im.expensive.utils.projections.ProjectionUtil;
import im.expensive.utils.render.ColorUtils;
import im.expensive.utils.render.DisplayUtils;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.CAnimateHandPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import static java.lang.Math.hypot;
import static net.minecraft.util.math.MathHelper.clamp;
import static net.minecraft.util.math.MathHelper.wrapDegrees;

@FunctionRegister(name = "KillAura", type = Category.Combat)
public class KillAura extends Function {
    @Getter
    private final ModeSetting type = new ModeSetting("Тип", "Плавная", "Плавная", "Резкая", "ХВХ");
    private final SliderSetting attackRange = new SliderSetting("Дистанция аттаки", 3f, 3f, 6f, 0.1f);
    private final SliderSetting rotateRange = new SliderSetting("Доп Дистанция", 0.5f, 0.5f, 6f, 0.1f);

    final ModeListSetting targets = new ModeListSetting("Таргеты",
            new BooleanSetting("Игроки", true),
            new BooleanSetting("Голые", true),
            new BooleanSetting("Мобы", false),
            new BooleanSetting("Животные", false),
            new BooleanSetting("Друзья", false),
            new BooleanSetting("Голые невидимки", true),
            new BooleanSetting("Невидимки", true));

    @Getter
    final ModeListSetting options = new ModeListSetting("Опции",
            new BooleanSetting("Только криты", true),
            new BooleanSetting("Ломать щит", true),
            new BooleanSetting("Отжимать щит", true),
            new BooleanSetting("Синхронизировать атаку с ТПС", false),
            new BooleanSetting("Фокусировать одну цель", true),
            new BooleanSetting("Коррекция движения", true));
    final ModeSetting correctionType = new ModeSetting("Тип коррекции", "Незаметный", "Незаметный", "Сфокусированный");

    @Getter
    private final StopWatch stopWatch = new StopWatch();
    private Vector2f rotateVector = new Vector2f(0, 0);
    @Getter
    private LivingEntity target;
    private final Animation alpha = new DecelerateAnimation(700, 255.0);
    private Entity selected;

    int ticks = 0;
    boolean isRotated;

    final AutoPotion autoPotion;

    public KillAura(AutoPotion autoPotion) {
        this.autoPotion = autoPotion;
        addSettings(type, attackRange, rotateRange, targets, options, correctionType);
    }

    @Subscribe
    public void onInput(EventInput eventInput) {
        if (options.getValueByName("Коррекция движения").get() && correctionType.is("Незаметная") && target != null && mc.player != null) {
            MoveUtils.fixMovement(eventInput, rotateVector.x);
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (options.getValueByName("Фокусировать одну цель").get() && (target == null || !isValid(target)) || !options.getValueByName("Фокусировать одну цель").get()) {
            updateTarget();
        }


        if (target != null && !(autoPotion.isState() && autoPotion.isActive())) {
            isRotated = false;
            if (shouldPlayerFalling() && (stopWatch.hasTimeElapsed())) {
                updateAttack();
                ticks = 2;
            }
            if (type.is("Резкая")) {
                if (ticks > 0) {
                    updateRotation(true, 180, 90);
                    ticks--;
                } else {
                    reset();
                }
            } else {
                if (!isRotated) {
                    updateRotation(true, 44, 45);
                }
                if (type.is("ХВХ")) {
                    if (!isRotated) {
                        updateRotation(false, 180,90);
                    }
                }
            }


        } else {
            stopWatch.setLastMS(0);
            reset();
        }
    }

    @Subscribe
    private void onWalking(EventMotion e) {
        if (this.target == null || !this.autoPotion.isState() || !this.autoPotion.isActive()) {
            float yaw = this.rotateVector.x;
            float pitch = this.rotateVector.y;
            float headRot = mc.player.prevRotationYawHead;
            float maxHeadRot = 24.0F;
            float bodRot = mc.player.prevRotationYaw;
            float bodYaw;
            if (Math.abs(headRot - bodRot) > maxHeadRot) {
                if (headRot > bodRot) {
                    bodYaw = headRot + ThreadLocalRandom.current().nextFloat(1.0F, 12F);
                } else {
                    bodYaw = headRot - ThreadLocalRandom.current().nextFloat(1.0F, 12F);
                }
            } else {
                bodYaw = bodRot;
            }

            float newBodYaw = Math.min(Math.max(bodYaw, 0.5F), 12F);
            mc.player.renderYawOffset = bodYaw;
            mc.player.rotationYawHead = yaw;
            mc.player.rotationPitchHead = pitch;
            e.setYaw(yaw);
            e.setPitch(pitch);
        }
    }

    private void updateTarget() {
        List<LivingEntity> targets = new ArrayList<>();

        for (Entity entity : mc.world.getAllEntities()) {
            if (entity instanceof LivingEntity living && isValid(living)) {
                targets.add(living);
            }
        }

        if (targets.isEmpty()) {
            target = null;
            return;
        }

        if (targets.size() == 1) {
            target = targets.get(0);
            return;
        }

        targets.sort(Comparator.comparingDouble(object -> {
            if (object instanceof PlayerEntity player) {
                return -getEntityArmor(player);
            }
            if (object instanceof LivingEntity base) {
                return -base.getTotalArmorValue();
            }
            return 0.0;
        }).thenComparing((object, object2) -> {
            double d2 = getEntityHealth((LivingEntity) object);
            double d3 = getEntityHealth((LivingEntity) object2);
            return Double.compare(d2, d3);
        }).thenComparing((object, object2) -> {
            double d2 = mc.player.getDistance((LivingEntity) object);
            double d3 = mc.player.getDistance((LivingEntity) object2);
            return Double.compare(d2, d3);
        }));

        target = targets.get(0);
    }

    float lastYaw, lastPitch;

    private void updateRotation(boolean attack, float rotationYawSpeed, float rotationPitchSpeed) {
        Vector3d vec = target.getPositionVec().add(0, clamp(mc.player.getPosYEye() - target.getPosY(),
                        0, target.getHeight() * (mc.player.getDistanceEyePos(target) / attackRange.get())), 0)
                .subtract(mc.player.getEyePosition(2.1f));

        isRotated = true;

        float yawToTarget = (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90);
        float pitchToTarget = (float) (-Math.toDegrees(Math.atan2(vec.y, hypot(vec.x, vec.z))));

        float yawDelta = (wrapDegrees(yawToTarget - rotateVector.x));
        float pitchDelta = (wrapDegrees(pitchToTarget - rotateVector.y));
        int roundedYaw = (int) yawDelta;

        switch (type.get()) {
            case "Плавная" -> {
                float clampedYaw = Math.min(Math.max(Math.abs(yawDelta), 1.13f), rotationYawSpeed);
                float clampedPitch = Math.min(Math.max(Math.abs(pitchDelta), 2.32f), rotationPitchSpeed);
                float yaw = rotateVector.x + (yawDelta > 0 ? clampedYaw : -clampedYaw);
                float pitch = clamp(rotateVector.y + (pitchDelta > 0 ? clampedPitch : -clampedPitch), -90F, 90.0F);
                float gcd = SensUtils.getGCDValue() + SensUtils.getSensitivity(7f);
                yaw -= (yaw - rotateVector.x) / gcd;
                pitch -= (pitch - rotateVector.y) % gcd;
                rotateVector = new Vector2f(yaw, pitch);
                if (options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
            }
            case "Резкая" -> {
                float yaw = rotateVector.x + roundedYaw;
                float pitch = clamp(rotateVector.y + pitchDelta, -90, 90);

                float gcd = SensUtils.getGCDValue();
                yaw -= (yaw - rotateVector.x) % gcd;
                pitch -= (pitch - rotateVector.y) % gcd;

                rotateVector = new Vector2f(yaw, pitch);

                if (options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = yaw;
                }
            }
            case "ХВХ" -> {
                float clampedYaw = Math.min(Math.max(Math.abs(yawDelta), 1.14f), rotationYawSpeed * 5);
                float clampedPitch = Math.min(Math.max(Math.abs(pitchDelta), 1.1f), rotationPitchSpeed * 15);

                float yaw = rotateVector.x + (yawDelta > 0 ? clampedYaw : -clampedYaw);
                float pitch = clamp(rotateVector.y + (pitchDelta > 0 ? clampedPitch : -clampedPitch), -90F, 90.0F);

                float gcd = SensUtils.getGCDValue();
                yaw -= (yaw - rotateVector.x) % gcd;
                pitch -= (pitch - rotateVector.y) % gcd;

                rotateVector = new Vector2f(yaw, pitch);
            }
        }
    }


    private void updateAttack() {
        selected = MouseUtil.getMouseOver(target, rotateVector.x, rotateVector.y, attackRange.get());

        if ((selected == null || selected != target) && !mc.player.isElytraFlying()) {
            return;
        }

        if (mc.player.isBlocking() && options.getValueByName("Отжимать щит").get()) {
            mc.playerController.onStoppedUsingItem(mc.player);
        }
        if (!type.is("ХВХ")) {
            mc.player.setSprinting(false);
            stopWatch.setLastMS(455);
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(Hand.MAIN_HAND);
        }
        if (type.is("ХВХ")) {
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(Hand.MAIN_HAND);
        }


        if (target instanceof PlayerEntity player && options.getValueByName("Ломать щит").get()) {
            breakShieldPlayer(player);
        }
    }

    private boolean shouldPlayerFalling() {
        boolean cancelReason = mc.player.isInWater() && mc.player.areEyesInFluid(FluidTags.WATER) || mc.player.isInLava() || mc.player.isOnLadder() || mc.player.isPassenger() || mc.player.abilities.isFlying;

        float attackStrength = mc.player.getCooledAttackStrength(options.getValueByName("Синхронизировать атаку с ТПС").get()
                ? Furious.getInstance().getTpsCalc().getAdjustTicks() : 1.5f);

        if (attackStrength < 0.92f) {
            return false;
        }

        if (!cancelReason && options.getValueByName("Только криты").get()) {
            return !mc.player.isOnGround() && mc.player.fallDistance > 0.1;
        }

        return true;
    }

    private boolean isValid(LivingEntity entity) {
        if (entity instanceof ClientPlayerEntity) return false;

        if (entity.ticksExisted < 3) return false;
        if (mc.player.getDistanceEyePos(entity) > attackRange.get() + rotateRange.get()) return false;

        if (entity instanceof PlayerEntity p) {
            if (AntiBot.isBot(entity)) {
                return false;
            }
            if (!targets.getValueByName("Друзья").get() && FriendStorage.isFriend(p.getName().getString())) {
                return false;
            }
            if (p.getName().getString().equalsIgnoreCase(mc.player.getName().getString())) return false;
        }

        if (entity instanceof PlayerEntity && !targets.getValueByName("Игроки").get()) {
            return false;
        }
        if (entity instanceof PlayerEntity && entity.getTotalArmorValue() == 0 && !targets.getValueByName("Голые").get()) {
            return false;
        }
        if (entity instanceof PlayerEntity && entity.isInvisible() && entity.getTotalArmorValue() == 0 && !targets.getValueByName("Голые невидимки").get()) {
            return false;
        }
        if (entity instanceof PlayerEntity && entity.isInvisible() && !targets.getValueByName("Невидимки").get()) {
            return false;
        }

        if (entity instanceof MonsterEntity && !targets.getValueByName("Мобы").get()) {
            return false;
        }
        if (entity instanceof AnimalEntity && !targets.getValueByName("Животные").get()) {
            return false;
        }

        return !entity.isInvulnerable() && entity.isAlive() && !(entity instanceof ArmorStandEntity);
    }

    private void breakShieldPlayer(PlayerEntity entity) {
        if (entity.isBlocking()) {
            int invSlot = InventoryUtil.getInstance().getAxeInInventory(false);
            int hotBarSlot = InventoryUtil.getInstance().getAxeInInventory(true);

            if (hotBarSlot == -1 && invSlot != -1) {
                int bestSlot = InventoryUtil.getInstance().findBestSlotInHotBar();
                mc.playerController.windowClick(0, invSlot, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, bestSlot + 36, 0, ClickType.PICKUP, mc.player);

                mc.player.connection.sendPacket(new CHeldItemChangePacket(bestSlot));
                mc.playerController.attackEntity(mc.player, entity);
                mc.player.swingArm(Hand.MAIN_HAND);
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));

                mc.playerController.windowClick(0, bestSlot + 36, 0, ClickType.PICKUP, mc.player);
                mc.playerController.windowClick(0, invSlot, 0, ClickType.PICKUP, mc.player);
            }

            if (hotBarSlot != -1) {
                mc.player.connection.sendPacket(new CHeldItemChangePacket(hotBarSlot));
                mc.playerController.attackEntity(mc.player, entity);
                mc.player.swingArm(Hand.MAIN_HAND);
                mc.player.connection.sendPacket(new CHeldItemChangePacket(mc.player.inventory.currentItem));
            }
        }
    }


    private void reset() {
        if (options.getValueByName("Коррекция движения").get()) {
            mc.player.rotationYawOffset = Integer.MIN_VALUE;
        }
        rotateVector = new Vector2f(mc.player.rotationYaw, mc.player.rotationPitch);
    }

    @Override
    public boolean onEnable() {
        super.onEnable();
        reset();
        target = null;
        return false;
    }
    @Subscribe
    private void onDisplay(EventDisplay e) {
            if (e.getType() != im.expensive.events.EventDisplay.Type.PRE) {
                return;
            }

            if (target != null) {
                Minecraft var10001 = mc;
                if (target != Minecraft.player) {
                    double sin = Math.sin((double)System.currentTimeMillis() / 1000.0);
                    float size = 140.0F;
                    Vector3d interpolated = target.getPositon(e.getPartialTicks());
                    Vector2f pos = ProjectionUtil.project(interpolated.x, interpolated.y + (double)(target.getHeight() / 2.0F), interpolated.z);
                    GlStateManager.pushMatrix();
                    GlStateManager.translatef(pos.x, pos.y, 0.0F);
                    GlStateManager.rotatef((float)sin * 360.0F, 0.0F, 0.0F, 1.0F);
                    GlStateManager.translatef(-pos.x, -pos.y, 0.0F);
                    if (pos != null) {
                        DisplayUtils.drawImageAlpha(new ResourceLocation("expensive/images/target.png"), pos.x - size / 2.0F, pos.y - size / 2.0F, size, size, new Vector4i(ColorUtils.setAlpha(HUD.getColor(0, 1.0F), (int)this.alpha.getOutput()), ColorUtils.setAlpha(HUD.getColor(90, 1.0F), (int)this.alpha.getOutput()), ColorUtils.setAlpha(HUD.getColor(180, 1.0F), (int)this.alpha.getOutput()), ColorUtils.setAlpha(HUD.getColor(270, 1.0F), (int)this.alpha.getOutput())));
                        GlStateManager.popMatrix();
                    }
                }
            }
        }
    @Override
    public void onDisable() {
        super.onDisable();
        reset();
        stopWatch.setLastMS(0);
        target = null;
    }

    private double getEntityArmor(PlayerEntity entityPlayer2) {
        double d2 = 0.0;
        for (int i2 = 0; i2 < 4; ++i2) {
            ItemStack is = entityPlayer2.inventory.armorInventory.get(i2);
            if (!(is.getItem() instanceof ArmorItem)) continue;
            d2 += getProtectionLvl(is);
        }
        return d2;
    }

    private double getProtectionLvl(ItemStack stack) {
        if (stack.getItem() instanceof ArmorItem i) {
            double damageReduceAmount = i.getDamageReduceAmount();
            if (stack.isEnchanted()) {
                damageReduceAmount += (double) EnchantmentHelper.getEnchantmentLevel(Enchantments.PROTECTION, stack) * 0.25;
            }
            return damageReduceAmount;
        }
        return 0;
    }

    private double getEntityHealth(LivingEntity ent) {
        if (ent instanceof PlayerEntity player) {
            return (double) (player.getHealth() + player.getAbsorptionAmount()) * (getEntityArmor(player) / 20.0);
        }
        return ent.getHealth() + ent.getAbsorptionAmount();
    }
}
