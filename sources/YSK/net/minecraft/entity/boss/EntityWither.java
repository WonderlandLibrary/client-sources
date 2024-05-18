package net.minecraft.entity.boss;

import net.minecraft.entity.monster.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.ai.*;
import net.minecraft.nbt.*;
import net.minecraft.potion.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import com.google.common.base.*;
import net.minecraft.block.material.*;
import net.minecraft.block.*;
import net.minecraft.util.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.stats.*;
import net.minecraft.entity.item.*;
import java.util.*;
import net.minecraft.init.*;

public class EntityWither extends EntityMob implements IBossDisplayData, IRangedAttackMob
{
    private float[] field_82217_f;
    private int[] field_82224_i;
    private static final Predicate<Entity> attackEntitySelector;
    private float[] field_82220_d;
    private static final String[] I;
    private float[] field_82218_g;
    private int blockBreakCounter;
    private int[] field_82223_h;
    private float[] field_82221_e;
    
    @Override
    protected String getLivingSound() {
        return EntityWither.I["  ".length()];
    }
    
    public EntityWither(final World world) {
        super(world);
        this.field_82220_d = new float["  ".length()];
        this.field_82221_e = new float["  ".length()];
        this.field_82217_f = new float["  ".length()];
        this.field_82218_g = new float["  ".length()];
        this.field_82223_h = new int["  ".length()];
        this.field_82224_i = new int["  ".length()];
        this.setHealth(this.getMaxHealth());
        this.setSize(0.9f, 3.5f);
        this.isImmuneToFire = (" ".length() != 0);
        ((PathNavigateGround)this.getNavigator()).setCanSwim(" ".length() != 0);
        this.tasks.addTask("".length(), new EntityAISwimming(this));
        this.tasks.addTask("  ".length(), new EntityAIArrowAttack(this, 1.0, 0x85 ^ 0xAD, 20.0f));
        this.tasks.addTask(0xC4 ^ 0xC1, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0x9B ^ 0x9D, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0x26 ^ 0x21, new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAIHurtByTarget(this, (boolean)("".length() != 0), new Class["".length()]));
        this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityLiving.class, "".length(), (boolean)("".length() != 0), (boolean)("".length() != 0), EntityWither.attackEntitySelector));
        this.experienceValue = (0x94 ^ 0xA6);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(300.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.6000000238418579);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(40.0);
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    public void mountEntity(final Entity entity) {
        this.ridingEntity = null;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntityWither.I["".length()], this.getInvulTime());
    }
    
    @Override
    public void setInWeb() {
    }
    
    public float func_82210_r(final int n) {
        return this.field_82220_d[n];
    }
    
    public void setInvulTime(final int n) {
        this.dataWatcher.updateObject(0xA0 ^ 0xB4, n);
    }
    
    private double func_82208_v(final int n) {
        double n2;
        if (n <= 0) {
            n2 = this.posY + 3.0;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            n2 = this.posY + 2.2;
        }
        return n2;
    }
    
    @Override
    public void addPotionEffect(final PotionEffect potionEffect) {
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x7 ^ 0x16, new Integer("".length()));
        this.dataWatcher.addObject(0x54 ^ 0x46, new Integer("".length()));
        this.dataWatcher.addObject(0x4B ^ 0x58, new Integer("".length()));
        this.dataWatcher.addObject(0x67 ^ 0x73, new Integer("".length()));
    }
    
    @Override
    public int getTotalArmorValue() {
        return 0x5D ^ 0x59;
    }
    
    private double func_82214_u(final int n) {
        if (n <= 0) {
            return this.posX;
        }
        return this.posX + MathHelper.cos((this.renderYawOffset + (85 + 80 - 63 + 78) * (n - " ".length())) / 180.0f * 3.1415927f) * 1.3;
    }
    
    @Override
    public void fall(final float n, final float n2) {
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
    protected void updateAITasks() {
        if (this.getInvulTime() > 0) {
            final int invulTime = this.getInvulTime() - " ".length();
            if (invulTime <= 0) {
                this.worldObj.newExplosion(this, this.posX, this.posY + this.getEyeHeight(), this.posZ, 7.0f, "".length() != 0, this.worldObj.getGameRules().getBoolean(EntityWither.I[0x8C ^ 0x89]));
                this.worldObj.playBroadcastSound(823 + 435 - 596 + 351, new BlockPos(this), "".length());
            }
            this.setInvulTime(invulTime);
            if (this.ticksExisted % (0x25 ^ 0x2F) == 0) {
                this.heal(10.0f);
                "".length();
                if (4 == -1) {
                    throw null;
                }
            }
        }
        else {
            super.updateAITasks();
            int i = " ".length();
            "".length();
            if (0 <= -1) {
                throw null;
            }
            while (i < "   ".length()) {
                if (this.ticksExisted >= this.field_82223_h[i - " ".length()]) {
                    this.field_82223_h[i - " ".length()] = this.ticksExisted + (0x3C ^ 0x36) + this.rand.nextInt(0x13 ^ 0x19);
                    if (this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                        final int n = i - " ".length();
                        final int n2 = this.field_82224_i[i - " ".length()];
                        this.field_82224_i[n] = this.field_82224_i[i - " ".length()] + " ".length();
                        if (n2 > (0x95 ^ 0x9A)) {
                            final float n3 = 10.0f;
                            final float n4 = 5.0f;
                            this.launchWitherSkullToCoords(i + " ".length(), MathHelper.getRandomDoubleInRange(this.rand, this.posX - n3, this.posX + n3), MathHelper.getRandomDoubleInRange(this.rand, this.posY - n4, this.posY + n4), MathHelper.getRandomDoubleInRange(this.rand, this.posZ - n3, this.posZ + n3), " ".length() != 0);
                            this.field_82224_i[i - " ".length()] = "".length();
                        }
                    }
                    final int watchedTargetId = this.getWatchedTargetId(i);
                    if (watchedTargetId > 0) {
                        final Entity entityByID = this.worldObj.getEntityByID(watchedTargetId);
                        if (entityByID != null && entityByID.isEntityAlive() && this.getDistanceSqToEntity(entityByID) <= 900.0 && this.canEntityBeSeen(entityByID)) {
                            if (entityByID instanceof EntityPlayer && ((EntityPlayer)entityByID).capabilities.disableDamage) {
                                this.updateWatchedTargetId(i, "".length());
                                "".length();
                                if (2 != 2) {
                                    throw null;
                                }
                            }
                            else {
                                this.launchWitherSkullToEntity(i + " ".length(), (EntityLivingBase)entityByID);
                                this.field_82223_h[i - " ".length()] = this.ticksExisted + (0x69 ^ 0x41) + this.rand.nextInt(0x4A ^ 0x5E);
                                this.field_82224_i[i - " ".length()] = "".length();
                                "".length();
                                if (false) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            this.updateWatchedTargetId(i, "".length());
                            "".length();
                            if (2 <= 0) {
                                throw null;
                            }
                        }
                    }
                    else {
                        final List<Entity> entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityLivingBase.class, this.getEntityBoundingBox().expand(20.0, 8.0, 20.0), (com.google.common.base.Predicate<? super Entity>)Predicates.and((Predicate)EntityWither.attackEntitySelector, (Predicate)EntitySelectors.NOT_SPECTATING));
                        int length = "".length();
                        "".length();
                        if (3 == -1) {
                            throw null;
                        }
                        while (length < (0xB7 ^ 0xBD) && !entitiesWithinAABB.isEmpty()) {
                            final EntityLivingBase entityLivingBase = entitiesWithinAABB.get(this.rand.nextInt(entitiesWithinAABB.size()));
                            if (entityLivingBase != this && entityLivingBase.isEntityAlive() && this.canEntityBeSeen(entityLivingBase)) {
                                if (entityLivingBase instanceof EntityPlayer) {
                                    if (((EntityPlayer)entityLivingBase).capabilities.disableDamage) {
                                        break;
                                    }
                                    this.updateWatchedTargetId(i, entityLivingBase.getEntityId());
                                    "".length();
                                    if (1 == 4) {
                                        throw null;
                                    }
                                    break;
                                }
                                else {
                                    this.updateWatchedTargetId(i, entityLivingBase.getEntityId());
                                    "".length();
                                    if (4 == -1) {
                                        throw null;
                                    }
                                    break;
                                }
                            }
                            else {
                                entitiesWithinAABB.remove(entityLivingBase);
                                ++length;
                            }
                        }
                    }
                }
                ++i;
            }
            if (this.getAttackTarget() != null) {
                this.updateWatchedTargetId("".length(), this.getAttackTarget().getEntityId());
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                this.updateWatchedTargetId("".length(), "".length());
            }
            if (this.blockBreakCounter > 0) {
                this.blockBreakCounter -= " ".length();
                if (this.blockBreakCounter == 0 && this.worldObj.getGameRules().getBoolean(EntityWither.I[0x93 ^ 0x95])) {
                    final int floor_double = MathHelper.floor_double(this.posY);
                    final int floor_double2 = MathHelper.floor_double(this.posX);
                    final int floor_double3 = MathHelper.floor_double(this.posZ);
                    int length2 = "".length();
                    int j = -" ".length();
                    "".length();
                    if (-1 >= 0) {
                        throw null;
                    }
                    while (j <= " ".length()) {
                        int k = -" ".length();
                        "".length();
                        if (3 <= 2) {
                            throw null;
                        }
                        while (k <= " ".length()) {
                            int l = "".length();
                            "".length();
                            if (-1 >= 3) {
                                throw null;
                            }
                            while (l <= "   ".length()) {
                                final BlockPos blockPos = new BlockPos(floor_double2 + j, floor_double + l, floor_double3 + k);
                                final Block block = this.worldObj.getBlockState(blockPos).getBlock();
                                if (block.getMaterial() != Material.air && func_181033_a(block)) {
                                    int n5;
                                    if (!this.worldObj.destroyBlock(blockPos, " ".length() != 0) && length2 == 0) {
                                        n5 = "".length();
                                        "".length();
                                        if (3 != 3) {
                                            throw null;
                                        }
                                    }
                                    else {
                                        n5 = " ".length();
                                    }
                                    length2 = n5;
                                }
                                ++l;
                            }
                            ++k;
                        }
                        ++j;
                    }
                    if (length2 != 0) {
                        this.worldObj.playAuxSFXAtEntity(null, 482 + 276 + 243 + 11, new BlockPos(this), "".length());
                    }
                }
            }
            if (this.ticksExisted % (0x2D ^ 0x39) == 0) {
                this.heal(1.0f);
            }
        }
    }
    
    @Override
    public int getBrightnessForRender(final float n) {
        return 13164973 + 8708470 - 18412174 + 12267611;
    }
    
    public float func_82207_a(final int n) {
        return this.field_82221_e[n];
    }
    
    private double func_82213_w(final int n) {
        if (n <= 0) {
            return this.posZ;
        }
        return this.posZ + MathHelper.sin((this.renderYawOffset + (117 + 10 - 26 + 79) * (n - " ".length())) / 180.0f * 3.1415927f) * 1.3;
    }
    
    public void updateWatchedTargetId(final int n, final int n2) {
        this.dataWatcher.updateObject((0x26 ^ 0x37) + n, n2);
    }
    
    public int getInvulTime() {
        return this.dataWatcher.getWatchableObjectInt(0x1B ^ 0xF);
    }
    
    private float func_82204_b(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    public void func_82206_m() {
        this.setInvulTime(170 + 172 - 123 + 1);
        this.setHealth(this.getMaxHealth() / 3.0f);
    }
    
    private void launchWitherSkullToCoords(final int n, final double n2, final double n3, final double n4, final boolean b) {
        this.worldObj.playAuxSFXAtEntity(null, 497 + 78 - 339 + 778, new BlockPos(this), "".length());
        final double func_82214_u = this.func_82214_u(n);
        final double func_82208_v = this.func_82208_v(n);
        final double func_82213_w = this.func_82213_w(n);
        final EntityWitherSkull entityWitherSkull = new EntityWitherSkull(this.worldObj, this, n2 - func_82214_u, n3 - func_82208_v, n4 - func_82213_w);
        if (b) {
            entityWitherSkull.setInvulnerable(" ".length() != 0);
        }
        entityWitherSkull.posY = func_82208_v;
        entityWitherSkull.posX = func_82214_u;
        entityWitherSkull.posZ = func_82213_w;
        this.worldObj.spawnEntityInWorld(entityWitherSkull);
    }
    
    @Override
    protected void despawnEntity() {
        this.entityAge = "".length();
    }
    
    static {
        I();
        attackEntitySelector = (Predicate)new Predicate<Entity>() {
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
                    if (0 == -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            public boolean apply(final Entity entity) {
                if (entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() != EnumCreatureAttribute.UNDEAD) {
                    return " ".length() != 0;
                }
                return "".length() != 0;
            }
        };
    }
    
    @Override
    protected String getHurtSound() {
        return EntityWither.I["   ".length()];
    }
    
    @Override
    public void onLivingUpdate() {
        this.motionY *= 0.6000000238418579;
        if (!this.worldObj.isRemote && this.getWatchedTargetId("".length()) > 0) {
            final Entity entityByID = this.worldObj.getEntityByID(this.getWatchedTargetId("".length()));
            if (entityByID != null) {
                if (this.posY < entityByID.posY || (!this.isArmored() && this.posY < entityByID.posY + 5.0)) {
                    if (this.motionY < 0.0) {
                        this.motionY = 0.0;
                    }
                    this.motionY += (0.5 - this.motionY) * 0.6000000238418579;
                }
                final double n = entityByID.posX - this.posX;
                final double n2 = entityByID.posZ - this.posZ;
                final double n3 = n * n + n2 * n2;
                if (n3 > 9.0) {
                    final double n4 = MathHelper.sqrt_double(n3);
                    this.motionX += (n / n4 * 0.5 - this.motionX) * 0.6000000238418579;
                    this.motionZ += (n2 / n4 * 0.5 - this.motionZ) * 0.6000000238418579;
                }
            }
        }
        if (this.motionX * this.motionX + this.motionZ * this.motionZ > 0.05000000074505806) {
            this.rotationYaw = (float)MathHelper.func_181159_b(this.motionZ, this.motionX) * 57.295776f - 90.0f;
        }
        super.onLivingUpdate();
        int i = "".length();
        "".length();
        if (-1 == 4) {
            throw null;
        }
        while (i < "  ".length()) {
            this.field_82218_g[i] = this.field_82221_e[i];
            this.field_82217_f[i] = this.field_82220_d[i];
            ++i;
        }
        int j = "".length();
        "".length();
        if (1 >= 2) {
            throw null;
        }
        while (j < "  ".length()) {
            final int watchedTargetId = this.getWatchedTargetId(j + " ".length());
            Entity entityByID2 = null;
            if (watchedTargetId > 0) {
                entityByID2 = this.worldObj.getEntityByID(watchedTargetId);
            }
            if (entityByID2 != null) {
                final double func_82214_u = this.func_82214_u(j + " ".length());
                final double func_82208_v = this.func_82208_v(j + " ".length());
                final double func_82213_w = this.func_82213_w(j + " ".length());
                final double n5 = entityByID2.posX - func_82214_u;
                final double n6 = entityByID2.posY + entityByID2.getEyeHeight() - func_82208_v;
                final double n7 = entityByID2.posZ - func_82213_w;
                final double n8 = MathHelper.sqrt_double(n5 * n5 + n7 * n7);
                final float n9 = (float)(MathHelper.func_181159_b(n7, n5) * 180.0 / 3.141592653589793) - 90.0f;
                this.field_82220_d[j] = this.func_82204_b(this.field_82220_d[j], (float)(-(MathHelper.func_181159_b(n6, n8) * 180.0 / 3.141592653589793)), 40.0f);
                this.field_82221_e[j] = this.func_82204_b(this.field_82221_e[j], n9, 10.0f);
                "".length();
                if (0 >= 2) {
                    throw null;
                }
            }
            else {
                this.field_82221_e[j] = this.func_82204_b(this.field_82221_e[j], this.renderYawOffset, 10.0f);
            }
            ++j;
        }
        final boolean armored = this.isArmored();
        int k = "".length();
        "".length();
        if (1 >= 3) {
            throw null;
        }
        while (k < "   ".length()) {
            final double func_82214_u2 = this.func_82214_u(k);
            final double func_82208_v2 = this.func_82208_v(k);
            final double func_82213_w2 = this.func_82213_w(k);
            this.worldObj.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, func_82214_u2 + this.rand.nextGaussian() * 0.30000001192092896, func_82208_v2 + this.rand.nextGaussian() * 0.30000001192092896, func_82213_w2 + this.rand.nextGaussian() * 0.30000001192092896, 0.0, 0.0, 0.0, new int["".length()]);
            if (armored && this.worldObj.rand.nextInt(0xBA ^ 0xBE) == 0) {
                this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, func_82214_u2 + this.rand.nextGaussian() * 0.30000001192092896, func_82208_v2 + this.rand.nextGaussian() * 0.30000001192092896, func_82213_w2 + this.rand.nextGaussian() * 0.30000001192092896, 0.699999988079071, 0.699999988079071, 0.5, new int["".length()]);
            }
            ++k;
        }
        if (this.getInvulTime() > 0) {
            int l = "".length();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
            while (l < "   ".length()) {
                this.worldObj.spawnParticle(EnumParticleTypes.SPELL_MOB, this.posX + this.rand.nextGaussian() * 1.0, this.posY + this.rand.nextFloat() * 3.3f, this.posZ + this.rand.nextGaussian() * 1.0, 0.699999988079071, 0.699999988079071, 0.8999999761581421, new int["".length()]);
                ++l;
            }
        }
    }
    
    public int getWatchedTargetId(final int n) {
        return this.dataWatcher.getWatchableObjectInt((0x66 ^ 0x77) + n);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (damageSource == DamageSource.drown || damageSource.getEntity() instanceof EntityWither) {
            return "".length() != 0;
        }
        if (this.getInvulTime() > 0 && damageSource != DamageSource.outOfWorld) {
            return "".length() != 0;
        }
        if (this.isArmored() && damageSource.getSourceOfDamage() instanceof EntityArrow) {
            return "".length() != 0;
        }
        final Entity entity = damageSource.getEntity();
        if (entity != null && !(entity instanceof EntityPlayer) && entity instanceof EntityLivingBase && ((EntityLivingBase)entity).getCreatureAttribute() == this.getCreatureAttribute()) {
            return "".length() != 0;
        }
        if (this.blockBreakCounter <= 0) {
            this.blockBreakCounter = (0x78 ^ 0x6C);
        }
        int i = "".length();
        "".length();
        if (4 <= -1) {
            throw null;
        }
        while (i < this.field_82224_i.length) {
            final int[] field_82224_i = this.field_82224_i;
            final int n2 = i;
            field_82224_i[n2] += "   ".length();
            ++i;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase entityLivingBase, final float n) {
        this.launchWitherSkullToEntity("".length(), entityLivingBase);
    }
    
    private static void I() {
        (I = new String[0x72 ^ 0x75])["".length()] = I("\u0007(\u00078\u0001", "NFqMm");
        EntityWither.I[" ".length()] = I("\u0004\u001c\u001d=/", "MrkHC");
        EntityWither.I["  ".length()] = I("7\u001f\u0017a\u001e3\u0004\u001d*\u001bt\u0019\u0011#\f", "ZpuOi");
        EntityWither.I["   ".length()] = I("#*\fX-'1\u0006\u0013(`-\u001b\u0004.", "NEnvZ");
        EntityWither.I[0x83 ^ 0x87] = I("\u001a\u001f\u0006_>\u001e\u0004\f\u0014;Y\u0014\u0001\u0010=\u001f", "wpdqI");
        EntityWither.I[0x96 ^ 0x93] = I(" #\u0000\u0014\u0006$)\u0004:\u001a*", "MLbSt");
        EntityWither.I[0xB0 ^ 0xB6] = I("\u0002\r+\b1\u0006\u0007/&-\b", "obIOC");
    }
    
    public boolean isArmored() {
        if (this.getHealth() <= this.getMaxHealth() / 2.0f) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final EntityItem dropItem = this.dropItem(Items.nether_star, " ".length());
        if (dropItem != null) {
            dropItem.setNoDespawn();
        }
        if (!this.worldObj.isRemote) {
            final Iterator<EntityPlayer> iterator = this.worldObj.getEntitiesWithinAABB((Class<? extends EntityPlayer>)EntityPlayer.class, this.getEntityBoundingBox().expand(50.0, 100.0, 50.0)).iterator();
            "".length();
            if (-1 >= 0) {
                throw null;
            }
            while (iterator.hasNext()) {
                iterator.next().triggerAchievement(AchievementList.killWither);
            }
        }
    }
    
    @Override
    protected String getDeathSound() {
        return EntityWither.I[0x18 ^ 0x1C];
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setInvulTime(nbtTagCompound.getInteger(EntityWither.I[" ".length()]));
    }
    
    public static boolean func_181033_a(final Block block) {
        if (block != Blocks.bedrock && block != Blocks.end_portal && block != Blocks.end_portal_frame && block != Blocks.command_block && block != Blocks.barrier) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private void launchWitherSkullToEntity(final int n, final EntityLivingBase entityLivingBase) {
        final double posX = entityLivingBase.posX;
        final double n2 = entityLivingBase.posY + entityLivingBase.getEyeHeight() * 0.5;
        final double posZ = entityLivingBase.posZ;
        int n3;
        if (n == 0 && this.rand.nextFloat() < 0.001f) {
            n3 = " ".length();
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else {
            n3 = "".length();
        }
        this.launchWitherSkullToCoords(n, posX, n2, posZ, n3 != 0);
    }
}
