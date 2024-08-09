/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.entity.ai.goal;

import java.util.EnumSet;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.EntityPredicate;
import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.GroundPathNavigator;

public class TemptGoal
extends Goal {
    private static final EntityPredicate ENTITY_PREDICATE = new EntityPredicate().setDistance(10.0).allowInvulnerable().allowFriendlyFire().setSkipAttackChecks().setLineOfSiteRequired();
    protected final CreatureEntity creature;
    private final double speed;
    private double targetX;
    private double targetY;
    private double targetZ;
    private double pitch;
    private double yaw;
    protected PlayerEntity closestPlayer;
    private int delayTemptCounter;
    private boolean isRunning;
    private final Ingredient temptItem;
    private final boolean scaredByPlayerMovement;

    public TemptGoal(CreatureEntity creatureEntity, double d, Ingredient ingredient, boolean bl) {
        this(creatureEntity, d, bl, ingredient);
    }

    public TemptGoal(CreatureEntity creatureEntity, double d, boolean bl, Ingredient ingredient) {
        this.creature = creatureEntity;
        this.speed = d;
        this.temptItem = ingredient;
        this.scaredByPlayerMovement = bl;
        this.setMutexFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.LOOK));
        if (!(creatureEntity.getNavigator() instanceof GroundPathNavigator) && !(creatureEntity.getNavigator() instanceof FlyingPathNavigator)) {
            throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
        }
    }

    @Override
    public boolean shouldExecute() {
        if (this.delayTemptCounter > 0) {
            --this.delayTemptCounter;
            return true;
        }
        this.closestPlayer = this.creature.world.getClosestPlayer(ENTITY_PREDICATE, this.creature);
        if (this.closestPlayer == null) {
            return true;
        }
        return this.isTempting(this.closestPlayer.getHeldItemMainhand()) || this.isTempting(this.closestPlayer.getHeldItemOffhand());
    }

    protected boolean isTempting(ItemStack itemStack) {
        return this.temptItem.test(itemStack);
    }

    @Override
    public boolean shouldContinueExecuting() {
        if (this.isScaredByPlayerMovement()) {
            if (this.creature.getDistanceSq(this.closestPlayer) < 36.0) {
                if (this.closestPlayer.getDistanceSq(this.targetX, this.targetY, this.targetZ) > 0.010000000000000002) {
                    return true;
                }
                if (Math.abs((double)this.closestPlayer.rotationPitch - this.pitch) > 5.0 || Math.abs((double)this.closestPlayer.rotationYaw - this.yaw) > 5.0) {
                    return true;
                }
            } else {
                this.targetX = this.closestPlayer.getPosX();
                this.targetY = this.closestPlayer.getPosY();
                this.targetZ = this.closestPlayer.getPosZ();
            }
            this.pitch = this.closestPlayer.rotationPitch;
            this.yaw = this.closestPlayer.rotationYaw;
        }
        return this.shouldExecute();
    }

    protected boolean isScaredByPlayerMovement() {
        return this.scaredByPlayerMovement;
    }

    @Override
    public void startExecuting() {
        this.targetX = this.closestPlayer.getPosX();
        this.targetY = this.closestPlayer.getPosY();
        this.targetZ = this.closestPlayer.getPosZ();
        this.isRunning = true;
    }

    @Override
    public void resetTask() {
        this.closestPlayer = null;
        this.creature.getNavigator().clearPath();
        this.delayTemptCounter = 100;
        this.isRunning = false;
    }

    @Override
    public void tick() {
        this.creature.getLookController().setLookPositionWithEntity(this.closestPlayer, this.creature.getHorizontalFaceSpeed() + 20, this.creature.getVerticalFaceSpeed());
        if (this.creature.getDistanceSq(this.closestPlayer) < 6.25) {
            this.creature.getNavigator().clearPath();
        } else {
            this.creature.getNavigator().tryMoveToEntityLiving(this.closestPlayer, this.speed);
        }
    }

    public boolean isRunning() {
        return this.isRunning;
    }
}

