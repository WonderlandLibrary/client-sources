package im.expensive.functions.impl.combat.killAura;

import im.expensive.utils.client.IMinecraft;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
@Getter
public class TargetTracker implements IMinecraft {
    private LivingEntity lockedOnTarget;
    private double maximumDistance;

    public TargetTracker() {
        lockedOnTarget = null;
        maximumDistance = 0.0F;
    }

    public List<LivingEntity> enemies() {
        List<LivingEntity> entities = StreamSupport.stream(mc.world.getAllEntities().spliterator(), false)
                .filter(LivingEntity.class::isInstance)
                .map(LivingEntity.class::cast)
                .filter(this::validate)
                .sorted(Comparator.comparingDouble(e -> e.getDistance(mc.player)))
                .collect(Collectors.toList());

        entities.stream()
                .min(Comparator.comparingDouble(entity -> entity.getDistanceSq(mc.player)))
                .ifPresent(entity -> maximumDistance = entity.getDistanceSq(mc.player));

        return entities;
    }
    public void cleanup() {
        unlock();
    }

    public void lock(LivingEntity entity) {
        if (lockedOnTarget == null) {
            lockedOnTarget = entity;
        }
    }

    private void unlock() {
        lockedOnTarget = null;
    }

    public void validateLock(EntityValidator validator) {
        if (lockedOnTarget == null)
            return;

        if (!validate(lockedOnTarget) || !validator.isValid(lockedOnTarget)) {
            this.lockedOnTarget = null;
        }
    }
    private boolean validate(LivingEntity entity) {
        return entity != mc.player && entity.isAlive() && entity.getHealth() > 0.0F;
    }
    public interface EntityValidator {
        boolean isValid(LivingEntity entity);
    }
}
