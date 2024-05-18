package net.minecraft.entity.ai;

import net.minecraft.pathfinding.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityAIAvoidEntity<T extends Entity> extends EntityAIBase
{
    private double nearSpeed;
    private PathEntity entityPathEntity;
    protected T closestLivingEntity;
    private float avoidDistance;
    private Class<T> field_181064_i;
    private PathNavigate entityPathNavigate;
    private double farSpeed;
    private final Predicate<Entity> canBeSeenSelector;
    private Predicate<? super T> avoidTargetSelector;
    protected EntityCreature theEntity;
    
    public EntityAIAvoidEntity(final EntityCreature theEntity, final Class<T> field_181064_i, final Predicate<? super T> avoidTargetSelector, final float avoidDistance, final double farSpeed, final double nearSpeed) {
        this.canBeSeenSelector = (Predicate<Entity>)new Predicate<Entity>(this) {
            final EntityAIAvoidEntity this$0;
            
            public boolean apply(final Entity entity) {
                if (entity.isEntityAlive() && this.this$0.theEntity.getEntitySenses().canSee(entity)) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (4 == 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        };
        this.theEntity = theEntity;
        this.field_181064_i = field_181064_i;
        this.avoidTargetSelector = avoidTargetSelector;
        this.avoidDistance = avoidDistance;
        this.farSpeed = farSpeed;
        this.nearSpeed = nearSpeed;
        this.entityPathNavigate = theEntity.getNavigator();
        this.setMutexBits(" ".length());
    }
    
    @Override
    public void updateTask() {
        if (this.theEntity.getDistanceSqToEntity(this.closestLivingEntity) < 49.0) {
            this.theEntity.getNavigator().setSpeed(this.nearSpeed);
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            this.theEntity.getNavigator().setSpeed(this.farSpeed);
        }
    }
    
    @Override
    public boolean continueExecuting() {
        int n;
        if (this.entityPathNavigate.noPath()) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    public void startExecuting() {
        this.entityPathNavigate.setPath(this.entityPathEntity, this.farSpeed);
    }
    
    public EntityAIAvoidEntity(final EntityCreature entityCreature, final Class<T> clazz, final float n, final double n2, final double n3) {
        this(entityCreature, (Class)clazz, Predicates.alwaysTrue(), n, n2, n3);
    }
    
    @Override
    public void resetTask() {
        this.closestLivingEntity = null;
    }
    
    @Override
    public boolean shouldExecute() {
        final World worldObj = this.theEntity.worldObj;
        final Class<T> field_181064_i = this.field_181064_i;
        final AxisAlignedBB expand = this.theEntity.getEntityBoundingBox().expand(this.avoidDistance, 3.0, this.avoidDistance);
        final Predicate[] array = new Predicate["   ".length()];
        array["".length()] = EntitySelectors.NOT_SPECTATING;
        array[" ".length()] = this.canBeSeenSelector;
        array["  ".length()] = this.avoidTargetSelector;
        final List<Entity> entitiesWithinAABB = worldObj.getEntitiesWithinAABB((Class<? extends Entity>)field_181064_i, expand, (com.google.common.base.Predicate<? super Entity>)Predicates.and(array));
        if (entitiesWithinAABB.isEmpty()) {
            return "".length() != 0;
        }
        this.closestLivingEntity = (T)entitiesWithinAABB.get("".length());
        final Vec3 randomTargetBlockAway = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntity, 0x9 ^ 0x19, 0x94 ^ 0x93, new Vec3(this.closestLivingEntity.posX, this.closestLivingEntity.posY, this.closestLivingEntity.posZ));
        if (randomTargetBlockAway == null) {
            return "".length() != 0;
        }
        if (this.closestLivingEntity.getDistanceSq(randomTargetBlockAway.xCoord, randomTargetBlockAway.yCoord, randomTargetBlockAway.zCoord) < this.closestLivingEntity.getDistanceSqToEntity(this.theEntity)) {
            return "".length() != 0;
        }
        this.entityPathEntity = this.entityPathNavigate.getPathToXYZ(randomTargetBlockAway.xCoord, randomTargetBlockAway.yCoord, randomTargetBlockAway.zCoord);
        int n;
        if (this.entityPathEntity == null) {
            n = "".length();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n = (this.entityPathEntity.isDestinationSame(randomTargetBlockAway) ? 1 : 0);
        }
        return n != 0;
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
