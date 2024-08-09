package dev.excellent.client.target;

import dev.excellent.Excellent;
import dev.excellent.api.event.impl.player.UpdateEvent;
import dev.excellent.api.interfaces.event.Listener;
import dev.excellent.api.interfaces.game.IMinecraft;
import dev.excellent.client.module.impl.combat.AntiBot;
import dev.excellent.client.module.impl.combat.KillAura;
import dev.excellent.impl.util.player.PlayerUtil;
import dev.excellent.impl.util.rotation.AuraUtil;
import dev.excellent.impl.value.impl.ModeValue;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class TargetHandler implements IMinecraft {

    private static final List<LivingEntity> entities = new CopyOnWriteArrayList<>();
    private int countLoadedEntities;

    public TargetHandler() {
        Excellent.getInst().getEventBus().register(this);
    }

    public final Listener<UpdateEvent> onUpdate = event -> {
        if (mc.player.ticksExisted % 100 == 0 || countLoadedEntities != mc.world.getCountLoadedEntities()) {
            updateTargetList();
            countLoadedEntities = mc.world.getCountLoadedEntities();
        }
    };

    private void updateTargetList() {
        entities.clear();
        entities.addAll(mc.world.loadedEntityList().stream()
                .filter(entity -> (entity instanceof LivingEntity) && entity != mc.player)
                .map(entity -> (LivingEntity) entity)
                .toList());
    }

    public static List<LivingEntity> getTargets(final double range) {
        return entities.stream()
                .filter(ENTITY_FILTER)
                .filter(entity -> mc.player.getDistance(entity) < range && mc.world.loadedEntityList().contains(entity))
                .sorted(Comparator.comparingDouble(entity -> mc.player.getDistanceSq(entity)))
                .collect(Collectors.toList());
    }

    public static LivingEntity getTarget(final double range) {
        return findTarget(range);
    }

    private static final Predicate<LivingEntity> ENTITY_FILTER = TargetHandler::isValidTarget;

    private static boolean isValidTarget(final LivingEntity entity) {
        if (entity == null) return false;
        final KillAura killAura = KillAura.singleton.get();
        if (!mc.player.canEntityBeSeen(entity)) return false;
        if (entity.getHealth() <= 0 || !entity.isAlive() || entity.equals(mc.player)) return false;
        if (entity instanceof ArmorStandEntity) return false;
        if (!killAura.getTargets().isEnabled("Невидимые") && entity.isInvisible()) {
            return false;
        }
        if (entity instanceof PlayerEntity player) {
            if (AntiBot.singleton.get().isEnabled() && AntiBot.contains(player)) return false;
            if (Excellent.getInst().getFriendManager().isFriend(player.getGameProfile().getName())) return false;
            if (!killAura.getTargets().isEnabled("Игроки")) {
                return false;
            } else if (!killAura.getTargets().isEnabled("Голые") && player.getTotalArmorValue() <= 0) {
                return false;
            }
        }
        if (entity instanceof MobEntity) {
            return killAura.getTargets().isEnabled("Мобы");
        }
        return true;
    }

    private static LivingEntity findTarget(final double range) {
        List<LivingEntity> validTargets = getTargets(range);
        ModeValue sortMode = KillAura.singleton.get().sortMode;
        if (validTargets.size() > 1) {
            if (sortMode.is("Всему")) {
                validTargets.sort((o1, o2) -> {
                    int armorDiff = Double.compare(compareArmor(o1, o2), 0);
                    if (armorDiff != 0) {
                        return armorDiff;
                    }
                    int healthDiff = Double.compare(compareHealth(o1, o2), 0);
                    if (healthDiff != 0) {
                        return healthDiff;
                    }
                    return Double.compare(AuraUtil.getVector(o1).length(), AuraUtil.getVector(o2).length());
                });
            } else if (sortMode.is("Дистанции")) {
                validTargets.sort(Comparator.comparingDouble(mc.player::getDistance).thenComparingDouble(PlayerUtil::getEntityHealth));
            } else if (sortMode.is("Время жизни")) {
                validTargets.sort(Comparator.comparingDouble(o -> o.ticksExisted));
            } else if (sortMode.is("Здоровью")) {
                validTargets.sort(Comparator.comparingDouble(PlayerUtil::getEntityHealth).thenComparingDouble(mc.player::getDistance));
            }
            return validTargets.get(0);
        } else if (validTargets.size() == 1) {
            return validTargets.get(0);
        } else {
            return null;
        }
    }

    private static int compareArmor(LivingEntity o1, LivingEntity o2) {
        if (o1 instanceof PlayerEntity p1 && o2 instanceof PlayerEntity p2) {
            return Double.compare(-PlayerUtil.getEntityArmor(p1), -PlayerUtil.getEntityArmor(p2));
        }
        return Double.compare(-o1.getTotalArmorValue(), -o2.getTotalArmorValue());
    }

    private static int compareHealth(LivingEntity o1, LivingEntity o2) {
        double health1 = PlayerUtil.getEntityHealth(o1);
        double health2 = PlayerUtil.getEntityHealth(o2);
        return Double.compare(health1, health2);
    }
}