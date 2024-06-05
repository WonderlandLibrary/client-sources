package net.minecraft.entity.ai;

import com.google.common.base.*;
import java.util.*;
import net.minecraft.entity.player.*;
import net.minecraft.scoreboard.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.*;
import org.apache.logging.log4j.*;

public class EntityAIFindEntityNearestPlayer extends EntityAIBase
{
    private static final Logger field_179436_a;
    private final Predicate field_179435_c;
    private final EntityAINearestAttackableTarget.Sorter field_179432_d;
    private EntityLiving field_179434_b;
    private EntityLivingBase field_179433_e;
    
    public EntityAIFindEntityNearestPlayer(final EntityLiving p_i45882_1_) {
        this.field_179434_b = p_i45882_1_;
        if (p_i45882_1_ instanceof EntityCreature) {
            EntityAIFindEntityNearestPlayer.field_179436_a.warn("Use NearestAttackableTargetGoal.class for PathfinerMob mobs!");
        }
        this.field_179435_c = (Predicate)new Predicate() {
            public boolean func_179880_a(final Entity p_179880_1_) {
                if (!(p_179880_1_ instanceof EntityPlayer)) {
                    return false;
                }
                double var2 = EntityAIFindEntityNearestPlayer.this.func_179431_f();
                if (p_179880_1_.isSneaking()) {
                    var2 *= 0.800000011920929;
                }
                if (p_179880_1_.isInvisible()) {
                    float var3 = ((EntityPlayer)p_179880_1_).getArmorVisibility();
                    if (var3 < 0.1f) {
                        var3 = 0.1f;
                    }
                    var2 *= 0.7f * var3;
                }
                return p_179880_1_.getDistanceToEntity(EntityAIFindEntityNearestPlayer.this.field_179434_b) <= var2 && EntityAITarget.func_179445_a(EntityAIFindEntityNearestPlayer.this.field_179434_b, (EntityLivingBase)p_179880_1_, false, true);
            }
            
            public boolean apply(final Object p_apply_1_) {
                return this.func_179880_a((Entity)p_apply_1_);
            }
        };
        this.field_179432_d = new EntityAINearestAttackableTarget.Sorter(p_i45882_1_);
    }
    
    @Override
    public boolean shouldExecute() {
        final double var1 = this.func_179431_f();
        final List var2 = this.field_179434_b.worldObj.func_175647_a(EntityPlayer.class, this.field_179434_b.getEntityBoundingBox().expand(var1, 4.0, var1), this.field_179435_c);
        Collections.sort((List<Object>)var2, this.field_179432_d);
        if (var2.isEmpty()) {
            return false;
        }
        this.field_179433_e = var2.get(0);
        return true;
    }
    
    @Override
    public boolean continueExecuting() {
        final EntityLivingBase var1 = this.field_179434_b.getAttackTarget();
        if (var1 == null) {
            return false;
        }
        if (!var1.isEntityAlive()) {
            return false;
        }
        final Team var2 = this.field_179434_b.getTeam();
        final Team var3 = var1.getTeam();
        if (var2 != null && var3 == var2) {
            return false;
        }
        final double var4 = this.func_179431_f();
        return this.field_179434_b.getDistanceSqToEntity(var1) <= var4 * var4 && (!(var1 instanceof EntityPlayerMP) || !((EntityPlayerMP)var1).theItemInWorldManager.isCreative());
    }
    
    @Override
    public void startExecuting() {
        this.field_179434_b.setAttackTarget(this.field_179433_e);
        super.startExecuting();
    }
    
    @Override
    public void resetTask() {
        this.field_179434_b.setAttackTarget(null);
        super.startExecuting();
    }
    
    protected double func_179431_f() {
        final IAttributeInstance var1 = this.field_179434_b.getEntityAttribute(SharedMonsterAttributes.followRange);
        return (var1 == null) ? 16.0 : var1.getAttributeValue();
    }
    
    static {
        field_179436_a = LogManager.getLogger();
    }
}
