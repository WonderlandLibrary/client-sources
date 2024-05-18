package net.minecraft.entity.monster;

import net.minecraft.nbt.*;
import net.minecraft.world.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.item.*;
import net.minecraft.entity.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import java.util.*;

public class EntityGhast extends EntityFlying implements IMob
{
    private static final String[] I;
    private int explosionStrength;
    
    public EntityGhast(final World world) {
        super(world);
        this.explosionStrength = " ".length();
        this.setSize(4.0f, 4.0f);
        this.isImmuneToFire = (" ".length() != 0);
        this.experienceValue = (0x14 ^ 0x11);
        this.moveHelper = new GhastMoveHelper(this);
        this.tasks.addTask(0xA ^ 0xF, new AIRandomFly(this));
        this.tasks.addTask(0x29 ^ 0x2E, new AILookAround(this));
        this.tasks.addTask(0x2C ^ 0x2B, new AIFireballAttack(this));
        this.targetTasks.addTask(" ".length(), new EntityAIFindEntityNearestPlayer(this));
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(EntityGhast.I[0x1 ^ 0x4], 0x3E ^ 0x5D)) {
            this.explosionStrength = nbtTagCompound.getInteger(EntityGhast.I[0x40 ^ 0x46]);
        }
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x36 ^ 0x26, (byte)"".length());
    }
    
    private static void I() {
        (I = new String[0xBE ^ 0xB9])["".length()] = I("\u001e*\u001a\u0001\u0012\u0019/\u0004", "xChdp");
        EntityGhast.I[" ".length()] = I("\u0001\u00065B\u001f\u0004\b$\u0018V\u0001\u00066\u0002", "liWlx");
        EntityGhast.I["  ".length()] = I("\u0014\u0015\u0016w\u0013\u0011\u001b\u0007-Z\n\u0019\u0006<\u0015\u0014", "yztYt");
        EntityGhast.I["   ".length()] = I("\u001d(\u001b~\u000b\u0018&\n$B\u0014\"\u0018$\u0004", "pGyPl");
        EntityGhast.I[0x7B ^ 0x7F] = I("\n;*\u0006\u001d<*5\u0004\" 4?\u0018", "OCZjr");
        EntityGhast.I[0x73 ^ 0x76] = I("=\u0019\u001b\u0018\r\u000b\b\u0004\u001a2\u0017\u0016\u000e\u0006", "xaktb");
        EntityGhast.I[0x48 ^ 0x4E] = I("?\u0000\u0018\u0005.\t\u0011\u0007\u0007\u0011\u0015\u000f\r\u001b", "zxhiA");
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
            if (0 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected float getSoundVolume() {
        return 10.0f;
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(10.0);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(100.0);
    }
    
    @Override
    protected String getDeathSound() {
        return EntityGhast.I["   ".length()];
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if (this.rand.nextInt(0x19 ^ 0xD) == 0 && super.getCanSpawnHere() && this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void setAttacking(final boolean b) {
        final DataWatcher dataWatcher = this.dataWatcher;
        final int n = 0x20 ^ 0x30;
        int n2;
        if (b) {
            n2 = " ".length();
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int n2 = this.rand.nextInt("  ".length()) + this.rand.nextInt(" ".length() + n);
        int i = "".length();
        "".length();
        if (-1 == 3) {
            throw null;
        }
        while (i < n2) {
            this.dropItem(Items.ghast_tear, " ".length());
            ++i;
        }
        final int n3 = this.rand.nextInt("   ".length()) + this.rand.nextInt(" ".length() + n);
        int j = "".length();
        "".length();
        if (3 < 2) {
            throw null;
        }
        while (j < n3) {
            this.dropItem(Items.gunpowder, " ".length());
            ++j;
        }
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return " ".length();
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (EntityGhast.I["".length()].equals(damageSource.getDamageType()) && damageSource.getEntity() instanceof EntityPlayer) {
            super.attackEntityFrom(damageSource, 1000.0f);
            ((EntityPlayer)damageSource.getEntity()).triggerAchievement(AchievementList.ghast);
            return " ".length() != 0;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected String getHurtSound() {
        return EntityGhast.I["  ".length()];
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
            this.setDead();
        }
    }
    
    public int getFireballStrength() {
        return this.explosionStrength;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityGhast.I[0x99 ^ 0x9D], this.explosionStrength);
    }
    
    @Override
    protected String getLivingSound() {
        return EntityGhast.I[" ".length()];
    }
    
    @Override
    protected Item getDropItem() {
        return Items.gunpowder;
    }
    
    @Override
    public float getEyeHeight() {
        return 2.6f;
    }
    
    static {
        I();
    }
    
    public boolean isAttacking() {
        if (this.dataWatcher.getWatchableObjectByte(0x99 ^ 0x89) != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static class AILookAround extends EntityAIBase
    {
        private EntityGhast parentEntity;
        
        @Override
        public boolean shouldExecute() {
            return " ".length() != 0;
        }
        
        @Override
        public void updateTask() {
            if (this.parentEntity.getAttackTarget() == null) {
                final EntityGhast parentEntity = this.parentEntity;
                final EntityGhast parentEntity2 = this.parentEntity;
                final float n = -(float)MathHelper.func_181159_b(this.parentEntity.motionX, this.parentEntity.motionZ) * 180.0f / 3.1415927f;
                parentEntity2.rotationYaw = n;
                parentEntity.renderYawOffset = n;
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else {
                final EntityLivingBase attackTarget = this.parentEntity.getAttackTarget();
                final double n2 = 64.0;
                if (attackTarget.getDistanceSqToEntity(this.parentEntity) < n2 * n2) {
                    final double n3 = attackTarget.posX - this.parentEntity.posX;
                    final double n4 = attackTarget.posZ - this.parentEntity.posZ;
                    final EntityGhast parentEntity3 = this.parentEntity;
                    final EntityGhast parentEntity4 = this.parentEntity;
                    final float n5 = -(float)MathHelper.func_181159_b(n3, n4) * 180.0f / 3.1415927f;
                    parentEntity4.rotationYaw = n5;
                    parentEntity3.renderYawOffset = n5;
                }
            }
        }
        
        public AILookAround(final EntityGhast parentEntity) {
            this.parentEntity = parentEntity;
            this.setMutexBits("  ".length());
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
    }
    
    static class AIFireballAttack extends EntityAIBase
    {
        public int attackTimer;
        private EntityGhast parentEntity;
        
        @Override
        public void startExecuting() {
            this.attackTimer = "".length();
        }
        
        @Override
        public boolean shouldExecute() {
            if (this.parentEntity.getAttackTarget() != null) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        public void updateTask() {
            final EntityLivingBase attackTarget = this.parentEntity.getAttackTarget();
            final double n = 64.0;
            if (attackTarget.getDistanceSqToEntity(this.parentEntity) < n * n && this.parentEntity.canEntityBeSeen(attackTarget)) {
                final World worldObj = this.parentEntity.worldObj;
                this.attackTimer += " ".length();
                if (this.attackTimer == (0xA7 ^ 0xAD)) {
                    worldObj.playAuxSFXAtEntity(null, 227 + 482 + 39 + 259, new BlockPos(this.parentEntity), "".length());
                }
                if (this.attackTimer == (0xD6 ^ 0xC2)) {
                    final double n2 = 4.0;
                    final Vec3 look = this.parentEntity.getLook(1.0f);
                    final double n3 = attackTarget.posX - (this.parentEntity.posX + look.xCoord * n2);
                    final double n4 = attackTarget.getEntityBoundingBox().minY + attackTarget.height / 2.0f - (0.5 + this.parentEntity.posY + this.parentEntity.height / 2.0f);
                    final double n5 = attackTarget.posZ - (this.parentEntity.posZ + look.zCoord * n2);
                    worldObj.playAuxSFXAtEntity(null, 324 + 514 - 788 + 958, new BlockPos(this.parentEntity), "".length());
                    final EntityLargeFireball entityLargeFireball = new EntityLargeFireball(worldObj, this.parentEntity, n3, n4, n5);
                    entityLargeFireball.explosionPower = this.parentEntity.getFireballStrength();
                    entityLargeFireball.posX = this.parentEntity.posX + look.xCoord * n2;
                    entityLargeFireball.posY = this.parentEntity.posY + this.parentEntity.height / 2.0f + 0.5;
                    entityLargeFireball.posZ = this.parentEntity.posZ + look.zCoord * n2;
                    worldObj.spawnEntityInWorld(entityLargeFireball);
                    this.attackTimer = -(0x20 ^ 0x8);
                    "".length();
                    if (3 < 0) {
                        throw null;
                    }
                }
            }
            else if (this.attackTimer > 0) {
                this.attackTimer -= " ".length();
            }
            final EntityGhast parentEntity = this.parentEntity;
            int attacking;
            if (this.attackTimer > (0x3A ^ 0x30)) {
                attacking = " ".length();
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                attacking = "".length();
            }
            parentEntity.setAttacking(attacking != 0);
        }
        
        public AIFireballAttack(final EntityGhast parentEntity) {
            this.parentEntity = parentEntity;
        }
        
        @Override
        public void resetTask() {
            this.parentEntity.setAttacking("".length() != 0);
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
                if (2 <= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class GhastMoveHelper extends EntityMoveHelper
    {
        private EntityGhast parentEntity;
        private int courseChangeCooldown;
        
        public GhastMoveHelper(final EntityGhast parentEntity) {
            super(parentEntity);
            this.parentEntity = parentEntity;
        }
        
        @Override
        public void onUpdateMoveHelper() {
            if (this.update) {
                final double n = this.posX - this.parentEntity.posX;
                final double n2 = this.posY - this.parentEntity.posY;
                final double n3 = this.posZ - this.parentEntity.posZ;
                final double n4 = n * n + n2 * n2 + n3 * n3;
                final int courseChangeCooldown = this.courseChangeCooldown;
                this.courseChangeCooldown = courseChangeCooldown - " ".length();
                if (courseChangeCooldown <= 0) {
                    this.courseChangeCooldown += this.parentEntity.getRNG().nextInt(0x92 ^ 0x97) + "  ".length();
                    final double n5 = MathHelper.sqrt_double(n4);
                    if (this.isNotColliding(this.posX, this.posY, this.posZ, n5)) {
                        final EntityGhast parentEntity = this.parentEntity;
                        parentEntity.motionX += n / n5 * 0.1;
                        final EntityGhast parentEntity2 = this.parentEntity;
                        parentEntity2.motionY += n2 / n5 * 0.1;
                        final EntityGhast parentEntity3 = this.parentEntity;
                        parentEntity3.motionZ += n3 / n5 * 0.1;
                        "".length();
                        if (2 != 2) {
                            throw null;
                        }
                    }
                    else {
                        this.update = ("".length() != 0);
                    }
                }
            }
        }
        
        private boolean isNotColliding(final double n, final double n2, final double n3, final double n4) {
            final double n5 = (n - this.parentEntity.posX) / n4;
            final double n6 = (n2 - this.parentEntity.posY) / n4;
            final double n7 = (n3 - this.parentEntity.posZ) / n4;
            AxisAlignedBB axisAlignedBB = this.parentEntity.getEntityBoundingBox();
            int length = " ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
            while (length < n4) {
                axisAlignedBB = axisAlignedBB.offset(n5, n6, n7);
                if (!this.parentEntity.worldObj.getCollidingBoundingBoxes(this.parentEntity, axisAlignedBB).isEmpty()) {
                    return "".length() != 0;
                }
                ++length;
            }
            return " ".length() != 0;
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
                if (4 <= 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class AIRandomFly extends EntityAIBase
    {
        private EntityGhast parentEntity;
        
        @Override
        public boolean shouldExecute() {
            final EntityMoveHelper moveHelper = this.parentEntity.getMoveHelper();
            if (!moveHelper.isUpdating()) {
                return " ".length() != 0;
            }
            final double n = moveHelper.getX() - this.parentEntity.posX;
            final double n2 = moveHelper.getY() - this.parentEntity.posY;
            final double n3 = moveHelper.getZ() - this.parentEntity.posZ;
            final double n4 = n * n + n2 * n2 + n3 * n3;
            if (n4 >= 1.0 && n4 <= 3600.0) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        @Override
        public void startExecuting() {
            final Random rng = this.parentEntity.getRNG();
            this.parentEntity.getMoveHelper().setMoveTo(this.parentEntity.posX + (rng.nextFloat() * 2.0f - 1.0f) * 16.0f, this.parentEntity.posY + (rng.nextFloat() * 2.0f - 1.0f) * 16.0f, this.parentEntity.posZ + (rng.nextFloat() * 2.0f - 1.0f) * 16.0f, 1.0);
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
                if (-1 >= 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public AIRandomFly(final EntityGhast parentEntity) {
            this.parentEntity = parentEntity;
            this.setMutexBits(" ".length());
        }
        
        @Override
        public boolean continueExecuting() {
            return "".length() != 0;
        }
    }
}
