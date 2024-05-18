package net.minecraft.entity.ai;

import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.player.*;

public class EntityAINearestAttackableTarget<T extends EntityLivingBase> extends EntityAITarget
{
    protected final Sorter theNearestAttackableTargetSorter;
    private final int targetChance;
    protected EntityLivingBase targetEntity;
    protected Predicate<? super T> targetEntitySelector;
    protected final Class<T> targetClass;
    
    @Override
    public boolean shouldExecute() {
        if (this.targetChance > 0 && this.taskOwner.getRNG().nextInt(this.targetChance) != 0) {
            return "".length() != 0;
        }
        final double targetDistance = this.getTargetDistance();
        final List<Entity> entitiesWithinAABB = this.taskOwner.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)this.targetClass, this.taskOwner.getEntityBoundingBox().expand(targetDistance, 4.0, targetDistance), (com.google.common.base.Predicate<? super Entity>)Predicates.and((Predicate)this.targetEntitySelector, (Predicate)EntitySelectors.NOT_SPECTATING));
        Collections.sort((List<Object>)entitiesWithinAABB, (Comparator<? super Object>)this.theNearestAttackableTargetSorter);
        if (entitiesWithinAABB.isEmpty()) {
            return "".length() != 0;
        }
        this.targetEntity = (EntityLivingBase)entitiesWithinAABB.get("".length());
        return " ".length() != 0;
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature entityCreature, final Class<T> clazz, final boolean b, final boolean b2) {
        this(entityCreature, clazz, 0xCB ^ 0xC1, b, b2, null);
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
            if (-1 < -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature entityCreature, final Class<T> targetClass, final int targetChance, final boolean b, final boolean b2, final Predicate<? super T> predicate) {
        super(entityCreature, b, b2);
        this.targetClass = targetClass;
        this.targetChance = targetChance;
        this.theNearestAttackableTargetSorter = new Sorter(entityCreature);
        this.setMutexBits(" ".length());
        this.targetEntitySelector = (Predicate<? super T>)new Predicate<T>(this, predicate) {
            final EntityAINearestAttackableTarget this$0;
            private final Predicate val$targetSelector;
            
            public boolean apply(final T t) {
                if (this.val$targetSelector != null && !this.val$targetSelector.apply((Object)t)) {
                    return "".length() != 0;
                }
                if (t instanceof EntityPlayer) {
                    double targetDistance = this.this$0.getTargetDistance();
                    if (t.isSneaking()) {
                        targetDistance *= 0.800000011920929;
                    }
                    if (t.isInvisible()) {
                        float armorVisibility = ((EntityPlayer)t).getArmorVisibility();
                        if (armorVisibility < 0.1f) {
                            armorVisibility = 0.1f;
                        }
                        targetDistance *= 0.7f * armorVisibility;
                    }
                    if (t.getDistanceToEntity(this.this$0.taskOwner) > targetDistance) {
                        return "".length() != 0;
                    }
                }
                return this.this$0.isSuitableTarget(t, "".length() != 0);
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
                    if (4 != 4) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Object o) {
                return this.apply((EntityLivingBase)o);
            }
        };
    }
    
    @Override
    public void startExecuting() {
        this.taskOwner.setAttackTarget(this.targetEntity);
        super.startExecuting();
    }
    
    public EntityAINearestAttackableTarget(final EntityCreature entityCreature, final Class<T> clazz, final boolean b) {
        this(entityCreature, clazz, b, "".length() != 0);
    }
    
    public static class Sorter implements Comparator<Entity>
    {
        private final Entity theEntity;
        
        @Override
        public int compare(final Entity entity, final Entity entity2) {
            final double distanceSqToEntity = this.theEntity.getDistanceSqToEntity(entity);
            final double distanceSqToEntity2 = this.theEntity.getDistanceSqToEntity(entity2);
            int n;
            if (distanceSqToEntity < distanceSqToEntity2) {
                n = -" ".length();
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else if (distanceSqToEntity > distanceSqToEntity2) {
                n = " ".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            return n;
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
                if (4 != 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public int compare(final Object o, final Object o2) {
            return this.compare((Entity)o, (Entity)o2);
        }
        
        public Sorter(final Entity theEntity) {
            this.theEntity = theEntity;
        }
    }
}
