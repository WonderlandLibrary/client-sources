package net.minecraft.entity.monster;

import net.minecraft.world.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;

public class EntityBlaze extends EntityMob
{
    private static final String[] I;
    private int heightOffsetUpdateTime;
    private float heightOffset;
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(48.0);
    }
    
    static {
        I();
    }
    
    public EntityBlaze(final World world) {
        super(world);
        this.heightOffset = 0.5f;
        this.isImmuneToFire = (" ".length() != 0);
        this.experienceValue = (0x7F ^ 0x75);
        this.tasks.addTask(0x5D ^ 0x59, new AIFireballAttack(this));
        this.tasks.addTask(0x41 ^ 0x44, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(0x2 ^ 0x5, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0xBE ^ 0xB6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0xA2 ^ 0xAA, new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAIHurtByTarget(this, (boolean)(" ".length() != 0), new Class["".length()]));
        this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityPlayer.class, (boolean)(" ".length() != 0)));
    }
    
    @Override
    public void fall(final float n, final float n2) {
    }
    
    public void setOnFire(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x27 ^ 0x37);
        byte b2;
        if (b) {
            b2 = (byte)(watchableObjectByte | " ".length());
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            b2 = (byte)(watchableObjectByte & -"  ".length());
        }
        this.dataWatcher.updateObject(0xB8 ^ 0xA8, b2);
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return " ".length() != 0;
    }
    
    public boolean func_70845_n() {
        if ((this.dataWatcher.getWatchableObjectByte(0x36 ^ 0x26) & " ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public float getBrightness(final float n) {
        return 1.0f;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityBlaze.I[" ".length()];
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x53 ^ 0x43, new Byte((byte)"".length()));
    }
    
    private static void I() {
        (I = new String[0x2E ^ 0x2A])["".length()] = I("\u00037\u0017e\u000f\u00029\u000f.C\f*\u0010*\u0019\u0006=", "nXuKm");
        EntityBlaze.I[" ".length()] = I("$? j2%18!~!96", "IPBDP");
        EntityBlaze.I["  ".length()] = I(".\f\rL6/\u0002\u0015\u0007z'\u0006\u000e\u0016<", "CcobT");
        EntityBlaze.I["   ".length()] = I("\u0004\u001c?\u001dM\u0004\u001c?\u001d", "buMxc");
    }
    
    @Override
    protected void updateAITasks() {
        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.drown, 1.0f);
        }
        this.heightOffsetUpdateTime -= " ".length();
        if (this.heightOffsetUpdateTime <= 0) {
            this.heightOffsetUpdateTime = (0x7 ^ 0x63);
            this.heightOffset = 0.5f + (float)this.rand.nextGaussian() * 3.0f;
        }
        final EntityLivingBase attackTarget = this.getAttackTarget();
        if (attackTarget != null && attackTarget.posY + attackTarget.getEyeHeight() > this.posY + this.getEyeHeight() + this.heightOffset) {
            this.motionY += (0.30000001192092896 - this.motionY) * 0.30000001192092896;
            this.isAirBorne = (" ".length() != 0);
        }
        super.updateAITasks();
    }
    
    @Override
    public void onLivingUpdate() {
        if (!this.onGround && this.motionY < 0.0) {
            this.motionY *= 0.6;
        }
        if (this.worldObj.isRemote) {
            if (this.rand.nextInt(0x35 ^ 0x2D) == 0 && !this.isSilent()) {
                this.worldObj.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, EntityBlaze.I["   ".length()], 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, "".length() != 0);
            }
            int i = "".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (i < "  ".length()) {
                this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_LARGE, this.posX + (this.rand.nextDouble() - 0.5) * this.width, this.posY + this.rand.nextDouble() * this.height, this.posZ + (this.rand.nextDouble() - 0.5) * this.width, 0.0, 0.0, 0.0, new int["".length()]);
                ++i;
            }
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected String getDeathSound() {
        return EntityBlaze.I["  ".length()];
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 14988558 + 10504038 - 17882139 + 8118423;
    }
    
    @Override
    protected String getLivingSound() {
        return EntityBlaze.I["".length()];
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        if (b) {
            final int nextInt = this.rand.nextInt("  ".length() + n);
            int i = "".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
            while (i < nextInt) {
                this.dropItem(Items.blaze_rod, " ".length());
                ++i;
            }
        }
    }
    
    @Override
    public boolean isBurning() {
        return this.func_70845_n();
    }
    
    @Override
    protected Item getDropItem() {
        return Items.blaze_rod;
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
            if (0 >= 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static class AIFireballAttack extends EntityAIBase
    {
        private int field_179468_c;
        private int field_179467_b;
        private EntityBlaze blaze;
        
        public AIFireballAttack(final EntityBlaze blaze) {
            this.blaze = blaze;
            this.setMutexBits("   ".length());
        }
        
        @Override
        public void resetTask() {
            this.blaze.setOnFire("".length() != 0);
        }
        
        @Override
        public void startExecuting() {
            this.field_179467_b = "".length();
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityLivingBase attackTarget = this.blaze.getAttackTarget();
            if (attackTarget != null && attackTarget.isEntityAlive()) {
                return " ".length() != 0;
            }
            return "".length() != 0;
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
                if (4 == -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public void updateTask() {
            this.field_179468_c -= " ".length();
            final EntityLivingBase attackTarget = this.blaze.getAttackTarget();
            final double distanceSqToEntity = this.blaze.getDistanceSqToEntity(attackTarget);
            if (distanceSqToEntity < 4.0) {
                if (this.field_179468_c <= 0) {
                    this.field_179468_c = (0x1 ^ 0x15);
                    this.blaze.attackEntityAsMob(attackTarget);
                }
                this.blaze.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 1.0);
                "".length();
                if (4 < 3) {
                    throw null;
                }
            }
            else if (distanceSqToEntity < 256.0) {
                final double n = attackTarget.posX - this.blaze.posX;
                final double n2 = attackTarget.getEntityBoundingBox().minY + attackTarget.height / 2.0f - (this.blaze.posY + this.blaze.height / 2.0f);
                final double n3 = attackTarget.posZ - this.blaze.posZ;
                if (this.field_179468_c <= 0) {
                    this.field_179467_b += " ".length();
                    if (this.field_179467_b == " ".length()) {
                        this.field_179468_c = (0x8D ^ 0xB1);
                        this.blaze.setOnFire(" ".length() != 0);
                        "".length();
                        if (-1 >= 3) {
                            throw null;
                        }
                    }
                    else if (this.field_179467_b <= (0xAF ^ 0xAB)) {
                        this.field_179468_c = (0xBA ^ 0xBC);
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                    }
                    else {
                        this.field_179468_c = (0xE9 ^ 0x8D);
                        this.field_179467_b = "".length();
                        this.blaze.setOnFire("".length() != 0);
                    }
                    if (this.field_179467_b > " ".length()) {
                        final float n4 = MathHelper.sqrt_float(MathHelper.sqrt_double(distanceSqToEntity)) * 0.5f;
                        this.blaze.worldObj.playAuxSFXAtEntity(null, 456 + 983 - 722 + 292, new BlockPos((int)this.blaze.posX, (int)this.blaze.posY, (int)this.blaze.posZ), "".length());
                        int i = "".length();
                        "".length();
                        if (3 == 1) {
                            throw null;
                        }
                        while (i < " ".length()) {
                            final EntitySmallFireball entitySmallFireball = new EntitySmallFireball(this.blaze.worldObj, this.blaze, n + this.blaze.getRNG().nextGaussian() * n4, n2, n3 + this.blaze.getRNG().nextGaussian() * n4);
                            entitySmallFireball.posY = this.blaze.posY + this.blaze.height / 2.0f + 0.5;
                            this.blaze.worldObj.spawnEntityInWorld(entitySmallFireball);
                            ++i;
                        }
                    }
                }
                this.blaze.getLookHelper().setLookPositionWithEntity(attackTarget, 10.0f, 10.0f);
                "".length();
                if (-1 != -1) {
                    throw null;
                }
            }
            else {
                this.blaze.getNavigator().clearPathEntity();
                this.blaze.getMoveHelper().setMoveTo(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 1.0);
            }
            super.updateTask();
        }
    }
}
