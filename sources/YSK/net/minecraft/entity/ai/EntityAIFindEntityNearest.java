package net.minecraft.entity.ai;

import com.google.common.base.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.player.*;
import org.apache.logging.log4j.*;
import net.minecraft.entity.*;
import java.util.*;

public class EntityAIFindEntityNearest extends EntityAIBase
{
    private final EntityAINearestAttackableTarget.Sorter field_179440_d;
    private EntityLiving field_179442_b;
    private EntityLivingBase field_179441_e;
    private static final String[] I;
    private final Predicate<EntityLivingBase> field_179443_c;
    private Class<? extends EntityLivingBase> field_179439_f;
    private static final Logger field_179444_a;
    
    static EntityLiving access$0(final EntityAIFindEntityNearest entityAIFindEntityNearest) {
        return entityAIFindEntityNearest.field_179442_b;
    }
    
    protected double func_179438_f() {
        final IAttributeInstance entityAttribute = this.field_179442_b.getEntityAttribute(SharedMonsterAttributes.followRange);
        double attributeValue;
        if (entityAttribute == null) {
            attributeValue = 16.0;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            attributeValue = entityAttribute.getAttributeValue();
        }
        return attributeValue;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.field_179442_b.getAttackTarget();
        if (attackTarget == null) {
            return "".length() != 0;
        }
        if (!attackTarget.isEntityAlive()) {
            return "".length() != 0;
        }
        final double func_179438_f = this.func_179438_f();
        int n;
        if (this.field_179442_b.getDistanceSqToEntity(attackTarget) > func_179438_f * func_179438_f) {
            n = "".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if (attackTarget instanceof EntityPlayerMP && ((EntityPlayerMP)attackTarget).theItemInWorldManager.isCreative()) {
            n = "".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    static {
        I();
        field_179444_a = LogManager.getLogger();
    }
    
    @Override
    public void resetTask() {
        this.field_179442_b.setAttackTarget(null);
        super.startExecuting();
    }
    
    public EntityAIFindEntityNearest(final EntityLiving field_179442_b, final Class<? extends EntityLivingBase> field_179439_f) {
        this.field_179442_b = field_179442_b;
        this.field_179439_f = field_179439_f;
        if (field_179442_b instanceof EntityCreature) {
            EntityAIFindEntityNearest.field_179444_a.warn(EntityAIFindEntityNearest.I["".length()]);
        }
        this.field_179443_c = (Predicate<EntityLivingBase>)new Predicate<EntityLivingBase>(this) {
            final EntityAIFindEntityNearest this$0;
            
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
                    if (2 >= 3) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Object o) {
                return this.apply((EntityLivingBase)o);
            }
            
            public boolean apply(final EntityLivingBase entityLivingBase) {
                double func_179438_f = this.this$0.func_179438_f();
                if (entityLivingBase.isSneaking()) {
                    func_179438_f *= 0.800000011920929;
                }
                int n;
                if (entityLivingBase.isInvisible()) {
                    n = "".length();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                }
                else if (entityLivingBase.getDistanceToEntity(EntityAIFindEntityNearest.access$0(this.this$0)) > func_179438_f) {
                    n = "".length();
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                else {
                    n = (EntityAITarget.isSuitableTarget(EntityAIFindEntityNearest.access$0(this.this$0), entityLivingBase, "".length() != 0, " ".length() != 0) ? 1 : 0);
                }
                return n != 0;
            }
        };
        this.field_179440_d = new EntityAINearestAttackableTarget.Sorter(field_179442_b);
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
            if (3 < 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u0000'\u0016I\u000605\u0001\f;!\u0015\u0007\u001d)6?\u0012\u000b$0\u0000\u0012\u001b/0 4\u0006)9z\u0010\u0005)&'S\u000f''t#\b<=2\u001a\u0007-'\u0019\u001c\u000bh8;\u0011\u001ai", "UTsiH");
    }
    
    @Override
    public void startExecuting() {
        this.field_179442_b.setAttackTarget(this.field_179441_e);
        super.startExecuting();
    }
    
    @Override
    public boolean shouldExecute() {
        final double func_179438_f = this.func_179438_f();
        final List<Entity> entitiesWithinAABB = this.field_179442_b.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)this.field_179439_f, this.field_179442_b.getEntityBoundingBox().expand(func_179438_f, 4.0, func_179438_f), (com.google.common.base.Predicate<? super Entity>)this.field_179443_c);
        Collections.sort((List<Object>)entitiesWithinAABB, (Comparator<? super Object>)this.field_179440_d);
        if (entitiesWithinAABB.isEmpty()) {
            return "".length() != 0;
        }
        this.field_179441_e = (EntityLivingBase)entitiesWithinAABB.get("".length());
        return " ".length() != 0;
    }
}
