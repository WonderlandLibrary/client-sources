package net.minecraft.entity.monster;

import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import com.google.common.base.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.potion.*;
import java.util.*;
import net.minecraft.nbt.*;
import net.minecraft.block.material.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.*;
import net.minecraft.pathfinding.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.*;

public class EntityGuardian extends EntityMob
{
    private EntityAIWander wander;
    private static final String[] I;
    private float field_175485_bl;
    private float field_175483_bk;
    private int field_175479_bo;
    private float field_175484_c;
    private boolean field_175480_bp;
    private float field_175486_bm;
    private float field_175482_b;
    private EntityLivingBase targetedEntity;
    
    private boolean isSyncedFlagSet(final int n) {
        if ((this.dataWatcher.getWatchableObjectInt(0xA2 ^ 0xB2) & n) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void addRandomDrop() {
        this.entityDropItem(WeightedRandom.getRandomItem(this.rand, EntityFishHook.func_174855_j()).getItemStack(this.rand), 1.0f);
    }
    
    @Override
    public int getTalkInterval() {
        return 54 + 65 - 35 + 76;
    }
    
    public EntityLivingBase getTargetedEntity() {
        if (!this.hasTargetedEntity()) {
            return null;
        }
        if (!this.worldObj.isRemote) {
            return this.getAttackTarget();
        }
        if (this.targetedEntity != null) {
            return this.targetedEntity;
        }
        final Entity entityByID = this.worldObj.getEntityByID(this.dataWatcher.getWatchableObjectInt(0xD ^ 0x1C));
        if (entityByID instanceof EntityLivingBase) {
            return this.targetedEntity = (EntityLivingBase)entityByID;
        }
        return null;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final int n2 = this.rand.nextInt("   ".length()) + this.rand.nextInt(n + " ".length());
        if (n2 > 0) {
            this.entityDropItem(new ItemStack(Items.prismarine_shard, n2, "".length()), 1.0f);
        }
        if (this.rand.nextInt("   ".length() + n) > " ".length()) {
            this.entityDropItem(new ItemStack(Items.fish, " ".length(), ItemFishFood.FishType.COD.getMetadata()), 1.0f);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        else if (this.rand.nextInt("   ".length() + n) > " ".length()) {
            this.entityDropItem(new ItemStack(Items.prismarine_crystals, " ".length(), "".length()), 1.0f);
        }
        if (b && this.isElder()) {
            this.entityDropItem(new ItemStack(Blocks.sponge, " ".length(), " ".length()), 1.0f);
        }
    }
    
    private static void I() {
        (I = new String[0xCE ^ 0xC3])["".length()] = I("\u0003\t\u0002\n:", "FefoH");
        EntityGuardian.I[" ".length()] = I("<40' ", "yXTBR");
        EntityGuardian.I["  ".length()] = I(".\u0005\rw\u001f6\u000b\u001d=\u0011\"\u0004A5\u0019-\u000eA0\u001c/\u000f", "CjoYx");
        EntityGuardian.I["   ".length()] = I("\u00147+v-\f9;<#\u00186g=&\u001d=;v#\u001d4,", "yXIXJ");
        EntityGuardian.I[0x4B ^ 0x4F] = I(".\u000b\u001bA56\u0005\u000b\u000b;\"\nW\u00066/\u0001", "CdyoR");
        EntityGuardian.I[0x31 ^ 0x34] = I("\u0017\u001f\u0007O\u0015\u000f\u0011\u0017\u0005\u001b\u001b\u001eK\r\u0013\u0014\u0014K\t\u001b\u000e", "zpear");
        EntityGuardian.I[0xAF ^ 0xA9] = I("8\u0001/\\\u0006 \u000f?\u0016\b4\u0000c\u0017\r1\u000b?\\\t<\u001a", "UnMra");
        EntityGuardian.I[0x90 ^ 0x97] = I(";9\u0012]\u001e#7\u0002\u0017\u001078^\u001b\u0010\"", "VVpsy");
        EntityGuardian.I[0xAA ^ 0xA2] = I("%,;I(=\"+\u0003&)-w\u000b.&'w\u0003*)71", "HCYgO");
        EntityGuardian.I[0x47 ^ 0x4E] = I("*\u0007#_\u00002\t3\u0015\u000e&\u0006o\u0014\u000b#\r3_\u0003\"\t5\u0019", "GhAqg");
        EntityGuardian.I[0x90 ^ 0x9A] = I("'\u000e8z5?\u0000(0;+\u000ft07+\u00152", "JaZTR");
        EntityGuardian.I[0x40 ^ 0x4B] = I("+\u0003$Y\u00153\r4\u0013\u001b'\u0002h\u0011\u001e)\u001c", "FlFwr");
        EntityGuardian.I[0x5F ^ 0x53] = I(">3\u0002\u000b6?|\u001b\u0002>(<\u001c", "ZRojQ");
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (!this.func_175472_n() && !damageSource.isMagicDamage() && damageSource.getSourceOfDamage() instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)damageSource.getSourceOfDamage();
            if (!damageSource.isExplosion()) {
                entityLivingBase.attackEntityFrom(DamageSource.causeThornsDamage(this), 2.0f);
                entityLivingBase.playSound(EntityGuardian.I[0x3E ^ 0x32], 0.5f, 1.0f);
            }
        }
        this.wander.makeUpdate();
        return super.attackEntityFrom(damageSource, n);
    }
    
    public int func_175464_ck() {
        int n;
        if (this.isElder()) {
            n = (0x98 ^ 0xA4);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = (0x25 ^ 0x75);
        }
        return n;
    }
    
    @Override
    protected void updateAITasks() {
        super.updateAITasks();
        if (this.isElder()) {
            "  ".length();
            if ((this.ticksExisted + this.getEntityId()) % (338 + 54 - 84 + 892) == 0) {
                final Potion digSlowdown = Potion.digSlowdown;
                final Iterator<EntityPlayerMP> iterator = this.worldObj.getPlayers((Class<? extends EntityPlayerMP>)EntityPlayerMP.class, (com.google.common.base.Predicate<? super EntityPlayerMP>)new Predicate<EntityPlayerMP>(this) {
                    final EntityGuardian this$0;
                    
                    public boolean apply(final EntityPlayerMP entityPlayerMP) {
                        if (this.this$0.getDistanceSqToEntity(entityPlayerMP) < 2500.0 && entityPlayerMP.theItemInWorldManager.survivalOrAdventure()) {
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
                            if (0 == -1) {
                                throw null;
                            }
                        }
                        return sb.toString();
                    }
                    
                    public boolean apply(final Object o) {
                        return this.apply((EntityPlayerMP)o);
                    }
                }).iterator();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final EntityPlayerMP entityPlayerMP = iterator.next();
                    if (!entityPlayerMP.isPotionActive(digSlowdown) || entityPlayerMP.getActivePotionEffect(digSlowdown).getAmplifier() < "  ".length() || entityPlayerMP.getActivePotionEffect(digSlowdown).getDuration() < 771 + 738 - 1077 + 768) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(0x8A ^ 0x80, 0.0f));
                        entityPlayerMP.addPotionEffect(new PotionEffect(digSlowdown.id, 4976 + 4745 - 7575 + 3854, "  ".length()));
                    }
                }
            }
            if (!this.hasHome()) {
                this.setHomePosAndDistance(new BlockPos(this), 0x71 ^ 0x61);
            }
        }
    }
    
    public float func_175471_a(final float n) {
        return this.field_175484_c + (this.field_175482_b - this.field_175484_c) * n;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setElder(nbtTagCompound.getBoolean(EntityGuardian.I["".length()]));
    }
    
    private void setSyncedFlag(final int n, final boolean b) {
        final int watchableObjectInt = this.dataWatcher.getWatchableObjectInt(0x3E ^ 0x2E);
        if (b) {
            this.dataWatcher.updateObject(0x1A ^ 0xA, watchableObjectInt | n);
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x74 ^ 0x64, watchableObjectInt & (n ^ -" ".length()));
        }
    }
    
    public float func_175469_o(final float n) {
        return this.field_175486_bm + (this.field_175485_bl - this.field_175486_bm) * n;
    }
    
    @Override
    protected String getLivingSound() {
        String s;
        if (!this.isInWater()) {
            s = EntityGuardian.I["  ".length()];
            "".length();
            if (4 < 0) {
                throw null;
            }
        }
        else if (this.isElder()) {
            s = EntityGuardian.I["   ".length()];
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            s = EntityGuardian.I[0x37 ^ 0x33];
        }
        return s;
    }
    
    public boolean hasTargetedEntity() {
        if (this.dataWatcher.getWatchableObjectInt(0x8D ^ 0x9C) != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected String getHurtSound() {
        String s;
        if (!this.isInWater()) {
            s = EntityGuardian.I[0x33 ^ 0x36];
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else if (this.isElder()) {
            s = EntityGuardian.I[0x88 ^ 0x8E];
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            s = EntityGuardian.I[0x50 ^ 0x57];
        }
        return s;
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return 103 + 171 - 249 + 155;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        if ((this.rand.nextInt(0xA7 ^ 0xB3) == 0 || !this.worldObj.canBlockSeeSky(new BlockPos(this))) && super.getCanSpawnHere()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.5f;
    }
    
    static void access$2(final EntityGuardian entityGuardian, final boolean b) {
        entityGuardian.func_175476_l(b);
    }
    
    @Override
    protected boolean isValidLightLevel() {
        return " ".length() != 0;
    }
    
    public float func_175477_p(final float n) {
        return (this.field_175479_bo + n) / this.func_175464_ck();
    }
    
    static {
        I();
    }
    
    public void setElder(final boolean b) {
        this.setSyncedFlag(0x47 ^ 0x43, b);
        if (b) {
            this.setSize(1.9975f, 1.9975f);
            this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(8.0);
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(80.0);
            this.enablePersistence();
            this.wander.setExecutionChance(201 + 347 - 267 + 119);
        }
    }
    
    @Override
    public float getBlockPathWeight(final BlockPos blockPos) {
        float blockPathWeight;
        if (this.worldObj.getBlockState(blockPos).getBlock().getMaterial() == Material.water) {
            blockPathWeight = 10.0f + this.worldObj.getLightBrightness(blockPos) - 0.5f;
            "".length();
            if (!true) {
                throw null;
            }
        }
        else {
            blockPathWeight = super.getBlockPathWeight(blockPos);
        }
        return blockPathWeight;
    }
    
    static void access$0(final EntityGuardian entityGuardian, final int targetedEntity) {
        entityGuardian.setTargetedEntity(targetedEntity);
    }
    
    public void setElder() {
        this.setElder(" ".length() != 0);
        final float n = 1.0f;
        this.field_175485_bl = n;
        this.field_175486_bm = n;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(EntityGuardian.I[" ".length()], this.isElder());
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
            if (2 >= 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityGuardian(final World world) {
        super(world);
        this.experienceValue = (0x20 ^ 0x2A);
        this.setSize(0.85f, 0.85f);
        this.tasks.addTask(0x8B ^ 0x8F, new AIGuardianAttack(this));
        final EntityAIMoveTowardsRestriction entityAIMoveTowardsRestriction;
        this.tasks.addTask(0x4D ^ 0x48, entityAIMoveTowardsRestriction = new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(0xC3 ^ 0xC4, this.wander = new EntityAIWander(this, 1.0, 0x75 ^ 0x25));
        this.tasks.addTask(0x22 ^ 0x2A, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0x60 ^ 0x68, new EntityAIWatchClosest(this, EntityGuardian.class, 12.0f, 0.01f));
        this.tasks.addTask(0x7B ^ 0x72, new EntityAILookIdle(this));
        this.wander.setMutexBits("   ".length());
        entityAIMoveTowardsRestriction.setMutexBits("   ".length());
        this.targetTasks.addTask(" ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityLivingBase.class, 0x55 ^ 0x5F, (boolean)(" ".length() != 0), (boolean)("".length() != 0), (com.google.common.base.Predicate<?>)new GuardianTargetSelector(this)));
        this.moveHelper = new GuardianMoveHelper(this);
        final float nextFloat = this.rand.nextFloat();
        this.field_175482_b = nextFloat;
        this.field_175484_c = nextFloat;
    }
    
    @Override
    protected PathNavigate getNewNavigator(final World world) {
        return new PathNavigateSwimmer(this, world);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x22 ^ 0x32, "".length());
        this.dataWatcher.addObject(0x4A ^ 0x5B, "".length());
    }
    
    private void setTargetedEntity(final int n) {
        this.dataWatcher.updateObject(0x48 ^ 0x59, n);
    }
    
    private void func_175476_l(final boolean b) {
        this.setSyncedFlag("  ".length(), b);
    }
    
    @Override
    public void onDataWatcherUpdate(final int n) {
        super.onDataWatcherUpdate(n);
        if (n == (0x20 ^ 0x30)) {
            if (this.isElder() && this.width < 1.0f) {
                this.setSize(1.9975f, 1.9975f);
                "".length();
                if (false) {
                    throw null;
                }
            }
        }
        else if (n == (0x56 ^ 0x47)) {
            this.field_175479_bo = "".length();
            this.targetedEntity = null;
        }
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isRemote) {
            this.field_175484_c = this.field_175482_b;
            if (!this.isInWater()) {
                this.field_175483_bk = 2.0f;
                if (this.motionY > 0.0 && this.field_175480_bp && !this.isSilent()) {
                    this.worldObj.playSound(this.posX, this.posY, this.posZ, EntityGuardian.I[0x0 ^ 0xB], 1.0f, 1.0f, "".length() != 0);
                }
                int field_175480_bp;
                if (this.motionY < 0.0 && this.worldObj.isBlockNormalCube(new BlockPos(this).down(), "".length() != 0)) {
                    field_175480_bp = " ".length();
                    "".length();
                    if (4 <= 1) {
                        throw null;
                    }
                }
                else {
                    field_175480_bp = "".length();
                }
                this.field_175480_bp = (field_175480_bp != 0);
                "".length();
                if (0 < -1) {
                    throw null;
                }
            }
            else if (this.func_175472_n()) {
                if (this.field_175483_bk < 0.5f) {
                    this.field_175483_bk = 4.0f;
                    "".length();
                    if (0 == 4) {
                        throw null;
                    }
                }
                else {
                    this.field_175483_bk += (0.5f - this.field_175483_bk) * 0.1f;
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
            }
            else {
                this.field_175483_bk += (0.125f - this.field_175483_bk) * 0.2f;
            }
            this.field_175482_b += this.field_175483_bk;
            this.field_175486_bm = this.field_175485_bl;
            if (!this.isInWater()) {
                this.field_175485_bl = this.rand.nextFloat();
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else if (this.func_175472_n()) {
                this.field_175485_bl += (0.0f - this.field_175485_bl) * 0.25f;
                "".length();
                if (1 >= 4) {
                    throw null;
                }
            }
            else {
                this.field_175485_bl += (1.0f - this.field_175485_bl) * 0.06f;
            }
            if (this.func_175472_n() && this.isInWater()) {
                final Vec3 look = this.getLook(0.0f);
                int i = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
                while (i < "  ".length()) {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + (this.rand.nextDouble() - 0.5) * this.width - look.xCoord * 1.5, this.posY + this.rand.nextDouble() * this.height - look.yCoord * 1.5, this.posZ + (this.rand.nextDouble() - 0.5) * this.width - look.zCoord * 1.5, 0.0, 0.0, 0.0, new int["".length()]);
                    ++i;
                }
            }
            if (this.hasTargetedEntity()) {
                if (this.field_175479_bo < this.func_175464_ck()) {
                    this.field_175479_bo += " ".length();
                }
                final EntityLivingBase targetedEntity = this.getTargetedEntity();
                if (targetedEntity != null) {
                    this.getLookHelper().setLookPositionWithEntity(targetedEntity, 90.0f, 90.0f);
                    this.getLookHelper().onUpdateLook();
                    final double n = this.func_175477_p(0.0f);
                    final double n2 = targetedEntity.posX - this.posX;
                    final double n3 = targetedEntity.posY + targetedEntity.height * 0.5f - (this.posY + this.getEyeHeight());
                    final double n4 = targetedEntity.posZ - this.posZ;
                    final double sqrt = Math.sqrt(n2 * n2 + n3 * n3 + n4 * n4);
                    final double n5 = n2 / sqrt;
                    final double n6 = n3 / sqrt;
                    final double n7 = n4 / sqrt;
                    double nextDouble = this.rand.nextDouble();
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                    while (nextDouble < sqrt) {
                        nextDouble += 1.8 - n + this.rand.nextDouble() * (1.7 - n);
                        this.worldObj.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX + n5 * nextDouble, this.posY + n6 * nextDouble + this.getEyeHeight(), this.posZ + n7 * nextDouble, 0.0, 0.0, 0.0, new int["".length()]);
                    }
                }
            }
        }
        if (this.inWater) {
            this.setAir(264 + 146 - 172 + 62);
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else if (this.onGround) {
            this.motionY += 0.5;
            this.motionX += (this.rand.nextFloat() * 2.0f - 1.0f) * 0.4f;
            this.motionZ += (this.rand.nextFloat() * 2.0f - 1.0f) * 0.4f;
            this.rotationYaw = this.rand.nextFloat() * 360.0f;
            this.onGround = ("".length() != 0);
            this.isAirBorne = (" ".length() != 0);
        }
        if (this.hasTargetedEntity()) {
            this.rotationYaw = this.rotationYawHead;
        }
        super.onLivingUpdate();
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(6.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5);
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(30.0);
    }
    
    public boolean func_175472_n() {
        return this.isSyncedFlagSet("  ".length());
    }
    
    static EntityAIWander access$1(final EntityGuardian entityGuardian) {
        return entityGuardian.wander;
    }
    
    public boolean isElder() {
        return this.isSyncedFlagSet(0x4E ^ 0x4A);
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return "".length() != 0;
    }
    
    @Override
    public boolean isNotColliding() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        if (this.isServerWorld()) {
            if (this.isInWater()) {
                this.moveFlying(n, n2, 0.1f);
                this.moveEntity(this.motionX, this.motionY, this.motionZ);
                this.motionX *= 0.8999999761581421;
                this.motionY *= 0.8999999761581421;
                this.motionZ *= 0.8999999761581421;
                if (!this.func_175472_n() && this.getAttackTarget() == null) {
                    this.motionY -= 0.005;
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                }
            }
            else {
                super.moveEntityWithHeading(n, n2);
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
        }
        else {
            super.moveEntityWithHeading(n, n2);
        }
    }
    
    @Override
    protected String getDeathSound() {
        String s;
        if (!this.isInWater()) {
            s = EntityGuardian.I[0x15 ^ 0x1D];
            "".length();
            if (3 < -1) {
                throw null;
            }
        }
        else if (this.isElder()) {
            s = EntityGuardian.I[0x34 ^ 0x3D];
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            s = EntityGuardian.I[0x2A ^ 0x20];
        }
        return s;
    }
    
    static class AIGuardianAttack extends EntityAIBase
    {
        private int tickCounter;
        private EntityGuardian theEntity;
        
        @Override
        public boolean continueExecuting() {
            if (super.continueExecuting() && (this.theEntity.isElder() || this.theEntity.getDistanceSqToEntity(this.theEntity.getAttackTarget()) > 9.0)) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        public AIGuardianAttack(final EntityGuardian theEntity) {
            this.theEntity = theEntity;
            this.setMutexBits("   ".length());
        }
        
        @Override
        public void resetTask() {
            EntityGuardian.access$0(this.theEntity, "".length());
            this.theEntity.setAttackTarget(null);
            EntityGuardian.access$1(this.theEntity).makeUpdate();
        }
        
        @Override
        public void updateTask() {
            final EntityLivingBase attackTarget = this.theEntity.getAttackTarget();
            this.theEntity.getNavigator().clearPathEntity();
            this.theEntity.getLookHelper().setLookPositionWithEntity(attackTarget, 90.0f, 90.0f);
            if (!this.theEntity.canEntityBeSeen(attackTarget)) {
                this.theEntity.setAttackTarget(null);
                "".length();
                if (2 != 2) {
                    throw null;
                }
            }
            else {
                this.tickCounter += " ".length();
                if (this.tickCounter == 0) {
                    EntityGuardian.access$0(this.theEntity, this.theEntity.getAttackTarget().getEntityId());
                    this.theEntity.worldObj.setEntityState(this.theEntity, (byte)(0x8D ^ 0x98));
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
                else if (this.tickCounter >= this.theEntity.func_175464_ck()) {
                    float n = 1.0f;
                    if (this.theEntity.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                        n += 2.0f;
                    }
                    if (this.theEntity.isElder()) {
                        n += 2.0f;
                    }
                    attackTarget.attackEntityFrom(DamageSource.causeIndirectMagicDamage(this.theEntity, this.theEntity), n);
                    attackTarget.attackEntityFrom(DamageSource.causeMobDamage(this.theEntity), (float)this.theEntity.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
                    this.theEntity.setAttackTarget(null);
                    "".length();
                    if (0 >= 1) {
                        throw null;
                    }
                }
                else if (this.tickCounter < (0xBA ^ 0x86) || this.tickCounter % (0x2E ^ 0x3A) == 0) {}
                super.updateTask();
            }
        }
        
        @Override
        public void startExecuting() {
            this.tickCounter = -(0xA9 ^ 0xA3);
            this.theEntity.getNavigator().clearPathEntity();
            this.theEntity.getLookHelper().setLookPositionWithEntity(this.theEntity.getAttackTarget(), 90.0f, 90.0f);
            this.theEntity.isAirBorne = (" ".length() != 0);
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityLivingBase attackTarget = this.theEntity.getAttackTarget();
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
                if (3 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class GuardianMoveHelper extends EntityMoveHelper
    {
        private EntityGuardian entityGuardian;
        
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
                if (2 <= -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public GuardianMoveHelper(final EntityGuardian entityGuardian) {
            super(entityGuardian);
            this.entityGuardian = entityGuardian;
        }
        
        @Override
        public void onUpdateMoveHelper() {
            if (this.update && !this.entityGuardian.getNavigator().noPath()) {
                final double n = this.posX - this.entityGuardian.posX;
                final double n2 = this.posY - this.entityGuardian.posY;
                final double n3 = this.posZ - this.entityGuardian.posZ;
                final double n4 = MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3);
                final double n5 = n2 / n4;
                this.entityGuardian.rotationYaw = this.limitAngle(this.entityGuardian.rotationYaw, (float)(MathHelper.func_181159_b(n3, n) * 180.0 / 3.141592653589793) - 90.0f, 30.0f);
                this.entityGuardian.renderYawOffset = this.entityGuardian.rotationYaw;
                this.entityGuardian.setAIMoveSpeed(this.entityGuardian.getAIMoveSpeed() + ((float)(this.speed * this.entityGuardian.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()) - this.entityGuardian.getAIMoveSpeed()) * 0.125f);
                final double n6 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.5) * 0.05;
                final double cos = Math.cos(this.entityGuardian.rotationYaw * 3.1415927f / 180.0f);
                final double sin = Math.sin(this.entityGuardian.rotationYaw * 3.1415927f / 180.0f);
                final EntityGuardian entityGuardian = this.entityGuardian;
                entityGuardian.motionX += n6 * cos;
                final EntityGuardian entityGuardian2 = this.entityGuardian;
                entityGuardian2.motionZ += n6 * sin;
                final double n7 = Math.sin((this.entityGuardian.ticksExisted + this.entityGuardian.getEntityId()) * 0.75) * 0.05;
                final EntityGuardian entityGuardian3 = this.entityGuardian;
                entityGuardian3.motionY += n7 * (sin + cos) * 0.25;
                final EntityGuardian entityGuardian4 = this.entityGuardian;
                entityGuardian4.motionY += this.entityGuardian.getAIMoveSpeed() * n5 * 0.1;
                final EntityLookHelper lookHelper = this.entityGuardian.getLookHelper();
                final double n8 = this.entityGuardian.posX + n / n4 * 2.0;
                final double n9 = this.entityGuardian.getEyeHeight() + this.entityGuardian.posY + n5 / n4 * 1.0;
                final double n10 = this.entityGuardian.posZ + n3 / n4 * 2.0;
                double lookPosX = lookHelper.getLookPosX();
                double lookPosY = lookHelper.getLookPosY();
                double lookPosZ = lookHelper.getLookPosZ();
                if (!lookHelper.getIsLooking()) {
                    lookPosX = n8;
                    lookPosY = n9;
                    lookPosZ = n10;
                }
                this.entityGuardian.getLookHelper().setLookPosition(lookPosX + (n8 - lookPosX) * 0.125, lookPosY + (n9 - lookPosY) * 0.125, lookPosZ + (n10 - lookPosZ) * 0.125, 10.0f, 40.0f);
                EntityGuardian.access$2(this.entityGuardian, " ".length() != 0);
                "".length();
                if (3 == 2) {
                    throw null;
                }
            }
            else {
                this.entityGuardian.setAIMoveSpeed(0.0f);
                EntityGuardian.access$2(this.entityGuardian, "".length() != 0);
            }
        }
    }
    
    static class GuardianTargetSelector implements Predicate<EntityLivingBase>
    {
        private EntityGuardian parentEntity;
        
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
                if (0 >= 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        public boolean apply(final Object o) {
            return this.apply((EntityLivingBase)o);
        }
        
        public GuardianTargetSelector(final EntityGuardian parentEntity) {
            this.parentEntity = parentEntity;
        }
        
        public boolean apply(final EntityLivingBase entityLivingBase) {
            if ((entityLivingBase instanceof EntityPlayer || entityLivingBase instanceof EntitySquid) && entityLivingBase.getDistanceSqToEntity(this.parentEntity) > 9.0) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
    }
}
