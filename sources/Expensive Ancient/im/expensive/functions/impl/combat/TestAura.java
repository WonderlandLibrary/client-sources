package im.expensive.functions.impl.combat;

import com.google.common.eventbus.Subscribe;
import im.expensive.Expensive;
import im.expensive.command.friends.FriendStorage;
import im.expensive.events.EventUpdate;
import im.expensive.functions.api.Category;
import im.expensive.functions.api.Function;
import im.expensive.functions.api.FunctionRegister;
import im.expensive.functions.impl.combat.killAura.ClickScheduler;
import im.expensive.functions.impl.combat.killAura.SprintController;
import im.expensive.functions.impl.combat.killAura.TargetTracker;
import im.expensive.functions.impl.combat.killAura.rotation.VecRotation;
import im.expensive.functions.impl.combat.killAura.rotation.impl.FuntimeAimPlan;
import im.expensive.functions.impl.combat.killAura.rotation.impl.GrimAimPlan;
import im.expensive.functions.settings.impl.BooleanSetting;
import im.expensive.functions.settings.impl.ModeListSetting;
import im.expensive.functions.settings.impl.ModeSetting;
import im.expensive.functions.settings.impl.SliderSetting;
import im.expensive.utils.player.InventoryUtil;
import im.expensive.utils.player.MouseUtil;
import im.expensive.utils.rotation.PointTracker;
import im.expensive.utils.rotation.RotationUtils;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.container.ClickType;
import net.minecraft.network.play.client.CEntityActionPacket;
import net.minecraft.network.play.client.CHeldItemChangePacket;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Hand;
import net.minecraft.util.math.vector.Vector3d;

@FunctionRegister(name = "TestAura", type = Category.Combat)
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TestAura extends Function {
    ModeSetting type = new ModeSetting("Обход", "Фантайм", "Фантайм", "Сатурн", "Грим");
    SliderSetting attackRange = new SliderSetting("Дистанция аттаки", 3f, 3f, 6f, 0.1f);

    ModeListSetting targets = new ModeListSetting("Таргеты",
            new BooleanSetting("Игроки", true),
            new BooleanSetting("Голые", true),
            new BooleanSetting("Мобы", false),
            new BooleanSetting("Животные", false),
            new BooleanSetting("Друзья", false),
            new BooleanSetting("Голые невидимки", true),
            new BooleanSetting("Невидимки", true));
    ModeListSetting options = new ModeListSetting("Опции",
            new BooleanSetting("Только криты", true),
            new BooleanSetting("Ломать щит", true),
            new BooleanSetting("Отжимать щит", true),
            new BooleanSetting("Фокусировать одну цель", true),
            new BooleanSetting("Коррекция движения", true));

    @NonFinal
    @Getter
    LivingEntity target;
    TargetTracker targetTracker = new TargetTracker();
    ClickScheduler clickScheduler = new ClickScheduler();
    PointTracker pointTracker = new PointTracker();
    SprintController sprintController = new SprintController();


    public TestAura() {
        addSettings(type, attackRange, targets, options);
    }

    @Override
    public void onEnable() {
        if (mc.player != null) {
            Expensive.getInstance().getRotationHandler().reset();
        }
        super.onEnable();
    }

    @Override
    public void onDisable() {
        if (mc.player != null) {
            targetTracker.cleanup();
            clickScheduler.cleanup();
            sprintController.reset();
            target = null;
            Expensive.getInstance().getRotationHandler().resetRotation();
        }
        super.onDisable();
    }

    @Subscribe
    public void onTick(EventUpdate eventUpdate) {
//        updateEnemySelection();
//        target = targetTracker.getLockedOnTarget();

        clickScheduler.update();
        if (target == null) {
            Expensive.getInstance().getRotationHandler().resetRotation();
            sprintController.reset();
            return;
        }
        if (clickScheduler.goingToClick() && readyToCauseCriticalHit()) {
            Expensive.getInstance().getRotationHandler().setRotateTicks(4);
        }
        updateRotation(target);

        VecRotation rotation = Expensive.getInstance().getRotationHandler().getRotation();
        boolean lookingAtTarget = MouseUtil.getMouseOver(target, rotation.getYaw(), rotation.getPitch(), attackRange.get()) != null;
        if (type.is("Фантайм") && !lookingAtTarget) {
            return;
        }
        updateAttack(target);
    }


    private void updateAttack(LivingEntity target) {
        if (clickScheduler.goingToClick() && readyToCauseCriticalHit()) {

            if (mc.player.isBlocking() && options.getValueByName("Отжимать щит").get()) {
                mc.playerController.onStoppedUsingItem(mc.player);
            }

            if (type.is("Сатурн")) {
                if (sprintController.isReady()) {
                    mc.playerController.attackEntity(mc.player, target);
                    mc.player.swingArm(Hand.MAIN_HAND);
                    if (target instanceof PlayerEntity player) {
                        breakShieldPlayer(player);
                    }
                    clickScheduler.resetCounter();
                    sprintController.reset();
                } else {
                    sprintController.sendStopSprintPacket();
                }

            } else {
                if (mc.player.serverSprintState) {
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.STOP_SPRINTING));
                }
                mc.playerController.attackEntity(mc.player, target);
                mc.player.swingArm(Hand.MAIN_HAND);
                if (target instanceof PlayerEntity player) {
                    breakShieldPlayer(player);
                }
                clickScheduler.resetCounter();
                if (mc.player.serverSprintState) {
                    mc.player.connection.sendPacket(new CEntityActionPacket(mc.player, CEntityActionPacket.Action.START_SPRINTING));
                }
            }
        } else {
            sprintController.reset();
        }
    }


    private void updateRotation(LivingEntity target) {
        Vector3d vector3d = pointTracker.getSpot(target, attackRange.get()).subtract(mc.player.getEyePosition(1));
        Expensive.getInstance().getRotationHandler().setRotation(RotationUtils.createRotation(vector3d), type.is("Фантайм") ? new FuntimeAimPlan() : new GrimAimPlan());
    }


    private boolean readyToCauseCriticalHit() {
        boolean cancelReason = mc.player.isInWater() && mc.player.areEyesInFluid(FluidTags.WATER)
                || mc.player.isInLava() && mc.player.areEyesInFluid(FluidTags.LAVA)
                || mc.player.isOnLadder()
                || mc.world.getBlockState(mc.player.getPosition()).getMaterial() == Material.WEB
                || mc.player.abilities.isFlying;

        if (mc.player.getCooledAttackStrength(0.5F) < 0.92F) {
            return false;
        }

        if (!cancelReason && options.getValueByName("Только криты").get()) {
            return !mc.player.isOnGround() && mc.player.fallDistance > 0;
        }
        return true;
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

//    private void updateEnemySelection() {
//        //targetTracker.validateLock(enemy -> enemy.canAttack() && !target.isInvulnerable() && mc.player.getDistance(enemy) <= attackRange.get());
//        Iterable<LivingEntity> enemies = targetTracker.enemies();
//        updateTargetWithRange(enemies, attackRange.get());
//    }


//    private void updateTargetWithRange(Iterable<LivingEntity> enemies, float range) {
//        for (LivingEntity target : enemies) {
//            if (mc.player.getDistanceSq(target) > Math.pow(range, 2)) {
//                continue;
//            }
//            //if (!target.canAttack()) {
//                continue;
//            }
//            //if (!validateTarget(target)) {
//                continue;
//            }
//            //targetTracker.lock(target);
//        }
//    }

    private boolean validateTarget(LivingEntity entity) {
        if (entity instanceof PlayerEntity player) {
            if (AntiBot.isBot(entity)) {
                return false;
            }
            if (!targets.getValueByName("Друзья").get() && FriendStorage.isFriend(player.getName().getString())) {
                return false;
            }

            if (!targets.getValueByName("Игроки").get()) {
                return false;
            }

            if (player.getTotalArmorValue() == 0) {
                if (!targets.getValueByName("Голые").get()) {
                    return false;
                }

                if (player.isInvisible() && !targets.getValueByName("Голые невидимки").get()) {
                    return false;
                }
            }

            if (player.isInvisible() && !targets.getValueByName("Невидимки").get()) {
                return false;
            }
        }
        if (entity instanceof MonsterEntity && !targets.getValueByName("Мобы").get()) {
            return false;
        }

        if (entity instanceof AnimalEntity && !targets.getValueByName("Животные").get()) {
            return false;
        }

        return !(entity instanceof ArmorStandEntity);
    }
}
