package net.minecraft.entity.ai;

import net.minecraft.entity.*;
import net.minecraft.util.*;

public class EntityAIArrowAttack extends EntityAIBase
{
    private double entityMoveSpeed;
    private int field_75318_f;
    private final EntityLiving entityHost;
    private float maxAttackDistance;
    private int rangedAttackTime;
    private static final String[] I;
    private int field_96561_g;
    private int maxRangedAttackTime;
    private final IRangedAttackMob rangedAttackEntityHost;
    private EntityLivingBase attackTarget;
    private float field_96562_i;
    
    static {
        I();
    }
    
    public EntityAIArrowAttack(final IRangedAttackMob rangedAttackEntityHost, final double entityMoveSpeed, final int field_96561_g, final int maxRangedAttackTime, final float field_96562_i) {
        this.rangedAttackTime = -" ".length();
        if (!(rangedAttackEntityHost instanceof EntityLivingBase)) {
            throw new IllegalArgumentException(EntityAIArrowAttack.I["".length()]);
        }
        this.rangedAttackEntityHost = rangedAttackEntityHost;
        this.entityHost = (EntityLiving)rangedAttackEntityHost;
        this.entityMoveSpeed = entityMoveSpeed;
        this.field_96561_g = field_96561_g;
        this.maxRangedAttackTime = maxRangedAttackTime;
        this.field_96562_i = field_96562_i;
        this.maxAttackDistance = field_96562_i * field_96562_i;
        this.setMutexBits("   ".length());
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
            if (-1 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean shouldExecute() {
        final EntityLivingBase attackTarget = this.entityHost.getAttackTarget();
        if (attackTarget == null) {
            return "".length() != 0;
        }
        this.attackTarget = attackTarget;
        return " ".length() != 0;
    }
    
    @Override
    public void resetTask() {
        this.attackTarget = null;
        this.field_75318_f = "".length();
        this.rangedAttackTime = -" ".length();
    }
    
    public EntityAIArrowAttack(final IRangedAttackMob rangedAttackMob, final double n, final int n2, final float n3) {
        this(rangedAttackMob, n, n2, n2, n3);
    }
    
    private static void I() {
        (I = new String[" ".length()])["".length()] = I("\u000b\u0013\u0014,\u0014\u000b\u0015\u0012\"\u0000!&\t\"\u000fj\u0013\u00032\u0016#\u0013\u00030C\u0007\u000e\u0004c\n'\u0011\n&\u000e/\u000f\u00120C\u0018\u0000\b$\u0006. \u00127\u0002)\n+,\u0001", "JafCc");
    }
    
    @Override
    public boolean continueExecuting() {
        if (!this.shouldExecute() && this.entityHost.getNavigator().noPath()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    @Override
    public void updateTask() {
        final double distanceSq = this.entityHost.getDistanceSq(this.attackTarget.posX, this.attackTarget.getEntityBoundingBox().minY, this.attackTarget.posZ);
        final boolean canSee = this.entityHost.getEntitySenses().canSee(this.attackTarget);
        if (canSee) {
            this.field_75318_f += " ".length();
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            this.field_75318_f = "".length();
        }
        if (distanceSq <= this.maxAttackDistance && this.field_75318_f >= (0xB5 ^ 0xA1)) {
            this.entityHost.getNavigator().clearPathEntity();
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            this.entityHost.getNavigator().tryMoveToEntityLiving(this.attackTarget, this.entityMoveSpeed);
        }
        this.entityHost.getLookHelper().setLookPositionWithEntity(this.attackTarget, 30.0f, 30.0f);
        if ((this.rangedAttackTime -= " ".length()) == 0) {
            if (distanceSq > this.maxAttackDistance || !canSee) {
                return;
            }
            final float n = MathHelper.sqrt_double(distanceSq) / this.field_96562_i;
            this.rangedAttackEntityHost.attackEntityWithRangedAttack(this.attackTarget, MathHelper.clamp_float(n, 0.1f, 1.0f));
            this.rangedAttackTime = MathHelper.floor_float(n * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (this.rangedAttackTime < 0) {
            this.rangedAttackTime = MathHelper.floor_float(MathHelper.sqrt_double(distanceSq) / this.field_96562_i * (this.maxRangedAttackTime - this.field_96561_g) + this.field_96561_g);
        }
    }
}
