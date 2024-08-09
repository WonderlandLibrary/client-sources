package dev.excellent.client.module.impl.player.pet;

import dev.excellent.client.script.manager.ScriptBlueprints;
import dev.excellent.impl.util.rotation.RotationUtil;
import dev.excellent.impl.util.time.TimerUtil;
import lombok.Getter;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ArmorStandEntity;
import net.minecraft.entity.item.ItemEntity;
import org.joml.Vector3d;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class PetController {
    private final Minecraft mc;
    private final Pet pet;
    private final double DISTANCE = 15.0D;
    private final TimerUtil waitTimer = TimerUtil.create();
    @Getter
    private final ScriptBlueprints scripts = new ScriptBlueprints() {{
        createScript("idle");
    }};

    public PetController(Pet pet) {
        this.mc = Minecraft.getInstance();
        this.pet = pet;
    }

    private void evaluatePriorities(List<Entity> entities) {
        entities.stream()
                .filter(entity -> !(entity instanceof ArmorStandEntity) && ((entity instanceof LivingEntity) || (entity instanceof ItemEntity)))
                .min(Comparator.comparingDouble(entity -> mc.player.getDistance(entity)))
                .ifPresent(entity -> {
                    if (pet.getCurrentState().equals(Pet.PetState.IDLE)) {
                        pet.setTarget(entity);
                        pet.setCurrentState(Pet.PetState.MOVE_TO_ENTITY);
                    }
                });
        Vector3d petPos = pet.getPosition();
        if (waitTimer.hasReached(1000) || mc.player.getDistanceSq(petPos.x, petPos.y, petPos.z) > (DISTANCE * DISTANCE)) {
            pet.setTarget(null);
            pet.setCurrentState(Pet.PetState.IDLE);
            waitTimer.reset();
        }
    }

    public void update() {
        List<Entity> entities = getNearbyEntities(LivingEntity.class);
        entities.addAll(getNearbyEntities(ItemEntity.class));

        evaluatePriorities(entities);

        switch (pet.getCurrentState()) {
            case IDLE:
                scripts.getScript("idle")
                        .ifPresent(script -> script
                                .getScriptConstructor()
                                .update());
                break;
            case MOVE_TO_ENTITY:
                moveToEntityState();
                break;
            default:
                break;
        }
        pet.updateMovement();
    }

    private List<Entity> getNearbyEntities(Class<? extends Entity> entityClass) {
        Predicate<Entity> entityFilter = entity -> entityClass.isInstance(entity) && entity != mc.player && mc.player.getDistance(entity) <= DISTANCE;
        return StreamSupport.stream(mc.world.getAllEntities().spliterator(), false)
                .filter(entityFilter)
                .collect(Collectors.toList());
    }

    public void idleState() {
        double distance = 5;
        double yawRad = Math.toRadians(pet.getYaw() + (Math.random() - 0.5) * 180);
        double xOffset = -Math.sin(yawRad) * distance;
        double zOffset = Math.cos(yawRad) * distance;

        double x = pet.getPosition().x + xOffset;
        double y = pet.getPosition().y + (Math.random() - 0.5) * pet.getHitbox().maxY * 10;
        double z = pet.getPosition().z + zOffset;

        double playerX = mc.player.getPosX();
        double playerY = mc.player.getPosY();
        double playerZ = mc.player.getPosZ();

        double squareDistance = mc.player.getDistanceSq(x, y, z);
        boolean yCheck = y < (playerY - pet.getHitbox().maxY);
        double range = yCheck ? 3 : 15;
        if (squareDistance > (range * range) || yCheck) {
            distance = range;
            yawRad = Math.toRadians(RotationUtil.calculate(mc.player).x + 180);
            xOffset = -Math.sin(yawRad) * distance;
            zOffset = Math.cos(yawRad) * distance;

            x = pet.getPosition().x + xOffset;
            y = playerY + (Math.random() * (mc.player.getHeight() / 2));
            z = pet.getPosition().z + zOffset;
        }

        x += (Math.random() - 0.5) * 0.5;
        z += (Math.random() - 0.5) * 0.5;

        double newSquareDistance = mc.player.getDistanceSq(x, y, z);
        if (newSquareDistance <= (range * range)) {
            pet.setDestination(x, y, z);
        } else {
            x = playerX + xOffset;
            y = playerY + (Math.random() * (mc.player.getHeight() / 2));
            z = playerZ + zOffset;

            x += (Math.random() - 0.5) * 0.5;
            z += (Math.random() - 0.5) * 0.5;

            pet.setDestination(x, y, z);
        }
    }

    private void moveToEntityState() {
        Entity entity = pet.getTarget();
        if (entity != null && entity.isAlive()) {
            double x = entity.getPosX();
            double y = entity.getPosY();
            double z = entity.getPosZ();

            double distance = entity.getWidth();
            double yawRad = Math.toRadians(entity.rotationYaw);
            double xOffset = -Math.sin(yawRad) * distance;
            double zOffset = Math.cos(yawRad) * distance;
            pet.setDestination(x + xOffset, y + (entity.getHeight() / 2), z + zOffset);
            waitTimer.reset();
        }
    }

}
