package net.minecraft.entity.ai;

import com.google.common.base.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import org.apache.logging.log4j.*;
import java.util.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase
{
    private EntityLiving field_179434_b;
    private final EntityAINearestAttackableTarget.Sorter field_179432_d;
    private static final String[] I;
    private final Predicate<Entity> field_179435_c;
    private EntityLivingBase field_179433_e;
    private static final Logger field_179436_a;
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase attackTarget = this.field_179434_b.getAttackTarget();
        if (attackTarget == null) {
            return "".length() != 0;
        }
        if (!attackTarget.isEntityAlive()) {
            return "".length() != 0;
        }
        if (attackTarget instanceof EntityPlayer && ((EntityPlayer)attackTarget).capabilities.disableDamage) {
            return "".length() != 0;
        }
        final Team team = this.field_179434_b.getTeam();
        final Team team2 = attackTarget.getTeam();
        if (team != null && team2 == team) {
            return "".length() != 0;
        }
        final double func_179431_f = this.func_179431_f();
        int n;
        if (this.field_179434_b.getDistanceSqToEntity(attackTarget) > func_179431_f * func_179431_f) {
            n = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (attackTarget instanceof EntityPlayerMP && ((EntityPlayerMP)attackTarget).theItemInWorldManager.isCreative()) {
            n = "".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public EntityAIFindEntityNearestPlayer(final EntityLiving field_179434_b) {
        this.field_179434_b = field_179434_b;
        if (field_179434_b instanceof EntityCreature) {
            EntityAIFindEntityNearestPlayer.field_179436_a.warn(EntityAIFindEntityNearestPlayer.I["".length()]);
        }
        this.field_179435_c = (Predicate<Entity>)new Predicate<Entity>(this) {
            final EntityAIFindEntityNearestPlayer this$0;
            
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
                    if (4 < 1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Object o) {
                return this.apply((Entity)o);
            }
            
            public boolean apply(final Entity entity) {
                if (!(entity instanceof EntityPlayer)) {
                    return "".length() != 0;
                }
                if (((EntityPlayer)entity).capabilities.disableDamage) {
                    return "".length() != 0;
                }
                double func_179431_f = this.this$0.func_179431_f();
                if (entity.isSneaking()) {
                    func_179431_f *= 0.800000011920929;
                }
                if (entity.isInvisible()) {
                    float armorVisibility = ((EntityPlayer)entity).getArmorVisibility();
                    if (armorVisibility < 0.1f) {
                        armorVisibility = 0.1f;
                    }
                    func_179431_f *= 0.7f * armorVisibility;
                }
                int n;
                if (entity.getDistanceToEntity(EntityAIFindEntityNearestPlayer.access$0(this.this$0)) > func_179431_f) {
                    n = "".length();
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else {
                    n = (EntityAITarget.isSuitableTarget(EntityAIFindEntityNearestPlayer.access$0(this.this$0), (EntityLivingBase)entity, "".length() != 0, " ".length() != 0) ? 1 : 0);
                }
                return n != 0;
            }
        };
        this.field_179432_d = new EntityAINearestAttackableTarget.Sorter(field_179434_b);
    }
    
    static EntityLiving access$0(final EntityAIFindEntityNearestPlayer entityAIFindEntityNearestPlayer) {
        return entityAIFindEntityNearestPlayer.field_179434_b;
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("8\u00006M+\b\u0012!\b\u0016\u00192'\u0019\u0004\u000e\u00182\u000f\t\b'2\u001f\u0002\b\u0007\u0014\u0002\u0004\u0001]0\u0001\u0004\u001e\u0000s\u000b\n\u001fS\u0003\f\u0011\u0005\u0015:\u0003\u0000\u001f><\u000fE\u0000\u001c1\u001eD", "msSme");
    }
    
    static {
        I();
        field_179436_a = LogManager.getLogger();
    }
    
    @Override
    public void startExecuting() {
        this.field_179434_b.setAttackTarget(this.field_179433_e);
        super.startExecuting();
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
            if (-1 == 1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldExecute() {
        final double func_179431_f = this.func_179431_f();
        final List<Entity> entitiesWithinAABB = this.field_179434_b.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityPlayer.class, this.field_179434_b.getEntityBoundingBox().expand(func_179431_f, 4.0, func_179431_f), (com.google.common.base.Predicate<? super Entity>)this.field_179435_c);
        Collections.sort((List<Object>)entitiesWithinAABB, (Comparator<? super Object>)this.field_179432_d);
        if (entitiesWithinAABB.isEmpty()) {
            return "".length() != 0;
        }
        this.field_179433_e = (EntityLivingBase)entitiesWithinAABB.get("".length());
        return " ".length() != 0;
    }
    
    @Override
    public void resetTask() {
        this.field_179434_b.setAttackTarget(null);
        super.startExecuting();
    }
    
    protected double func_179431_f() {
        final IAttributeInstance entityAttribute = this.field_179434_b.getEntityAttribute(SharedMonsterAttributes.followRange);
        double attributeValue;
        if (entityAttribute == null) {
            attributeValue = 16.0;
            "".length();
            if (-1 >= 0) {
                throw null;
            }
        }
        else {
            attributeValue = entityAttribute.getAttributeValue();
        }
        return attributeValue;
    }
}
