package im.expensive.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import im.expensive.Expensive;
import im.expensive.command.friends.FriendStorage;
import im.expensive.events.EventInput;
import im.expensive.events.EventLook;
import im.expensive.events.EventMotion;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.impl.combat.killAura.rotation.VecRotation;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.client.ClientUtil;
import im.expensive.utils.combat.Rotation;
import im.expensive.utils.math.SensUtils;
import im.expensive.utils.player.InventoryUtil;
import im.expensive.utils.player.MouseUtil;
import im.expensive.utils.player.MoveUtils;
import im.expensive.utils.rotation.PointTracker;
import im.expensive.utils.rotation.RotationUtils;
import lombok.Getter;
import net.minecraft.block.material.Material;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.entity.player.RemoteClientPlayerEntity;
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
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector2f;
import net.minecraft.util.math.vector.Vector3d;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static net.minecraft.util.math.MathHelper.clamp;
import static net.minecraft.util.math.MathHelper.wrapDegrees;

@FunctionRegister(name = "KillAura", type = Category.Combat)
public class KillAura extends Function {
    @Getter
    private final ModeSetting type = new ModeSetting("Тип", "Плавная", "Плавная", "Резкая");
    private final SliderSetting attackRange = new SliderSetting("Дистанция аттаки", 3f, 3f, 6f, 0.1f);

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

    final ModeSetting correctionType = new ModeSetting("Тип коррекции", "Незаметный", "Незаметный", "Сфокусированный").setVisible(() -> options.getValueByName("Коррекция движения").get());

    @Getter
    private Vector2f rotateVector = new Vector2f(0, 0);
    @Getter
    private LivingEntity target;
    private Entity raytrace;
    public int nextAttackDelay = 0;
    int ticks = 0;
    boolean isRotated;
    public boolean canAttackWithSpider = true;

    final AutoPotion autoPotion;
    public double lastPosY;
    boolean isFalling;
    PointTracker pointTracker = new PointTracker();

    public KillAura(AutoPotion autoPotion) {
        this.autoPotion = autoPotion;
        addSettings(type, attackRange, targets, options, correctionType);
    }

    @Subscribe
    public void onInput(EventInput eventInput) {
        if (options.getValueByName("Коррекция движения").get() && correctionType.is("Незаметный") && target != null && mc.player != null) {
            MoveUtils.fixMovement(eventInput, rotateVector.x);
        }
    }

    @Subscribe
    public void onLook(EventLook eventLook) {
        if (target != null) {
            eventLook.setRotation(new VecRotation(rotateVector.x, rotateVector.y));
        }
    }

    @Subscribe
    public void onUpdate(EventUpdate e) {
        if (nextAttackDelay > 0) {
            --nextAttackDelay;
        }

        if (options.getValueByName("Фокусировать одну цель").get() && (target == null || !isValid(target))
                || !options.getValueByName("Фокусировать одну цель").get()) {
            updateTarget();
        }

        if (target == null || autoPotion.isState() && autoPotion.isActive() && autoPotion.canThrowPotion()) {
            reset();
            mc.player.rotationYawOffset = Integer.MIN_VALUE;
            nextAttackDelay = 0;
            return;
        }

        isRotated = false;
        updateAttack();

        if (type.is("Резкая")) {
            if (ticks > 0) {
                updateRotation(true);
                ticks--;
            } else {
                reset();
            }
        } else {
            if (!isRotated) {
                updateRotation(false);
            }
        }

    }


    @Subscribe
    private void onWalking(EventMotion e) {
        if (target == null || autoPotion.isState() && autoPotion.isActive() && autoPotion.canThrowPotion()) return;


        float yaw = rotateVector.x;
        float pitch = rotateVector.y;

        e.setYaw(yaw);
        e.setPitch(pitch);
        mc.player.rotationYawHead = yaw;
        mc.player.renderYawOffset = yaw;
        mc.player.rotationPitchHead = pitch;
    }


    private void updateTarget() {
        List<LivingEntity> targets = new ArrayList<>();
        mc.world.getAllEntities().forEach(entity -> {
            if (entity instanceof LivingEntity living && isValid(living)) {
                targets.add(living);
            }
        });

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

    float lastYaw;

    private void updateRotation(boolean attack) {
        Vector3d vec = pointTracker.getSpot(target, attackRange.get()).subtract(mc.player.getEyePosition(1)).normalize();

        isRotated = true;
        float yawToTarget = (float) MathHelper.wrapDegrees(Math.toDegrees(Math.atan2(vec.z, vec.x)) - 90);
        float pitchToTarget = (float) Math.toDegrees(Math.asin(-vec.y));

        float yawDelta = (wrapDegrees(yawToTarget - rotateVector.x));
        float pitchDelta = (pitchToTarget - rotateVector.y);
        int roundYawDelta = (int) Math.abs(yawDelta);

        switch (type.get()) {
            case "Плавная" -> {
                float clampedYaw = Math.min(Math.max(roundYawDelta, 1.0f), 90.0F);
                float clampedPitch = Math.min(Math.max(Math.abs(pitchDelta) * 0.33f, 1.0f), 90.0F);

                float yaw = rotateVector.x + (yawDelta > 0 ? clampedYaw : -clampedYaw);
                float pitch = clamp(rotateVector.y + (pitchDelta > 0 ? clampedPitch : -clampedPitch), -90.0F, 90.0F);

                float gcd = SensUtils.getGCD();
                yaw -= (yaw - rotateVector.x) % gcd;
                pitch -= (pitch - rotateVector.y) % gcd;
                rotateVector = new Vector2f(yaw, pitch);
                lastYaw = clampedYaw;
                if (options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = rotateVector.x;
                }
            }
            case "Резкая" -> {
                float yaw = rotateVector.x + yawDelta;
                float pitch = clamp(rotateVector.y + pitchDelta, -90.0F, 90.0F);

                float gcd = SensUtils.getGCD();
                yaw -= (yaw - rotateVector.x) % gcd;
                pitch -= (pitch - rotateVector.y) % gcd;
                rotateVector = new Vector2f(yaw, pitch);
                if (options.getValueByName("Коррекция движения").get()) {
                    mc.player.rotationYawOffset = rotateVector.x;
                }
            }
        }
    }

    private void updateAttack() {
        if (!shouldPlayerFalling() || (nextAttackDelay != 0)) {
            return;
        }

        ticks = 3;
        raytrace = MouseUtil.getMouseOver(target, rotateVector.x, rotateVector.y, attackRange.get());
        if (raytrace == null) {
            return;
        }

        if (mc.player.isBlocking() && options.getValueByName("Отжимать щит").get()) {
            mc.playerController.onStoppedUsingItem(mc.player);
        }

        nextAttackDelay = 10;
        mc.playerController.attackEntity(mc.player, target);
        mc.player.swingArm(Hand.MAIN_HAND);

        if (target instanceof PlayerEntity player && options.getValueByName("Ломать щит").get()) {
            breakShieldPlayer(player);
        }
        lastPosY = Integer.MIN_VALUE;
    }

    public boolean shouldPlayerFalling() {
        boolean cancelReason = mc.player.isInWater() && mc.player.areEyesInFluid(FluidTags.WATER)
                || mc.player.isInLava() && mc.player.areEyesInFluid(FluidTags.LAVA)
                || mc.player.isOnLadder()
                || mc.world.getBlockState(mc.player.getPosition()).getMaterial() == Material.WEB
                || mc.player.abilities.isFlying;

        float adjustTicks = options.getValueByName("Синхронизировать атаку с ТПС").get()
                ? Expensive.getInstance().getTpsCalc().getAdjustTicks() : 1.5F;

        if (mc.player.getCooledAttackStrength(adjustTicks) < 0.92F) {
            return false;
        }

        if (!cancelReason && options.getValueByName("Только криты").get()) {
            return !mc.player.isOnGround() && mc.player.fallDistance > 0;
        }

        return true;
    }

    private boolean isValid(LivingEntity entity) {
        if (entity instanceof ClientPlayerEntity) return false;

        if (entity.ticksExisted < 3) return false;
        if (RotationUtils.getDistanceEyePos(entity) > attackRange.get()) return false;

        if (entity instanceof PlayerEntity p) {
            if (AntiBot.isBot(entity)) {
                return false;
            }
            if (!targets.getValueByName("Друзья").get() && FriendStorage.isFriend(p.getName().getString())) {
                return false;
            }
        }
        if (entity instanceof RemoteClientPlayerEntity ent && Expensive.getInstance().getFunctionRegistry().getFreeCam().fakePlayer != null) {
            if (ent.getUniqueID() == Expensive.getInstance().getFunctionRegistry().getFreeCam().fakePlayer.getUniqueID()) {
                return false;
            }
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
        rotateVector = new Vector2f(mc.player.rotationYaw, mc.player.rotationPitch);
    }

    @Override
    public void onEnable() {
        super.onEnable();
        if (mc.player != null) {
            reset();
            isFalling = false;
            nextAttackDelay = 0;
            target = null;
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        if (mc.player != null) {
            mc.player.rotationYawOffset = Integer.MIN_VALUE;
            reset();
            isFalling = false;
            nextAttackDelay = 0;
            target = null;
        }
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
