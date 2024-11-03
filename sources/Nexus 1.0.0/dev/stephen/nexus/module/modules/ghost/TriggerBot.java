package dev.stephen.nexus.module.modules.ghost;

import dev.stephen.nexus.event.bus.Listener;
import dev.stephen.nexus.event.bus.annotations.EventLink;
import dev.stephen.nexus.event.impl.player.EventTickPre;
import dev.stephen.nexus.module.ModuleCategory;
import dev.stephen.nexus.module.Module;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Tameable;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.util.Hand;

public class TriggerBot extends Module {

    private int hitDelayTimer = 0;

    public TriggerBot() {
        super("TriggerBot", "Attacks players when your crosshair is over an enemy", 0, ModuleCategory.GHOST);
    }

    @Override
    public void onDisable() {
        hitDelayTimer = 0;
        super.onDisable();
    }

    @EventLink
    public final Listener<EventTickPre> eventTickPreListener = event -> {
        if (mc.player == null || !mc.player.isAlive() || mc.player.getHealth() <= 0 || mc.player.isSpectator()) return;

        Entity targetedEntity = mc.targetedEntity;
        if (targetedEntity == null) return;

        if (!delayCheck()) return;

        if (entityCheck(targetedEntity)) {
            hitEntity(targetedEntity);
        }
    };

    private boolean delayCheck() {
        return mc.player.getAttackCooldownProgress(0.5f) >= 1;
    }

    private boolean entityCheck(Entity entity) {
        if (entity.equals(mc.player) || entity.equals(mc.cameraEntity)) return false;
        if (!(entity instanceof LivingEntity livingEntity) || livingEntity.isDead() || !entity.isAlive()) return false;
        if (entity instanceof Tameable tameable && tameable.getOwnerUuid() != null && tameable.getOwnerUuid().equals(mc.player.getUuid()))
            return false;
        return !(entity instanceof AnimalEntity) || !((AnimalEntity) entity).isBaby();
    }

    private void hitEntity(Entity target) {
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
    }
}
