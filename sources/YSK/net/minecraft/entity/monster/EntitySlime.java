package net.minecraft.entity.monster;

import net.minecraft.nbt.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.entity.*;
import net.minecraft.world.*;
import net.minecraft.util.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.entity.player.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;

public class EntitySlime extends EntityLiving implements IMob
{
    public float prevSquishFactor;
    public float squishAmount;
    private static final String[] I;
    private boolean wasOnGround;
    public float squishFactor;
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        int n = nbtTagCompound.getInteger(EntitySlime.I["  ".length()]);
        if (n < 0) {
            n = "".length();
        }
        this.setSlimeSize(n + " ".length());
        this.wasOnGround = nbtTagCompound.getBoolean(EntitySlime.I["   ".length()]);
    }
    
    public int getSlimeSize() {
        return this.dataWatcher.getWatchableObjectByte(0x3 ^ 0x13);
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setInteger(EntitySlime.I["".length()], this.getSlimeSize() - " ".length());
        nbtTagCompound.setBoolean(EntitySlime.I[" ".length()], this.wasOnGround);
    }
    
    private static void I() {
        (I = new String[0x4D ^ 0x43])["".length()] = I("\",?6", "qEESB");
        EntitySlime.I[" ".length()] = I("\u0015)\u0000\u00076%:\u001c=6\u0006", "bHsHX");
        EntitySlime.I["  ".length()] = I(":\u0019+\u0014", "ipQqj");
        EntitySlime.I["   ".length()] = I("2#:=!\u00020&\u0007!!", "EBIrO");
        EntitySlime.I[0x3E ^ 0x3A] = I("\u001d&4T\u0004\u001c ;\u001fY", "pIVzw");
        EntitySlime.I[0x9F ^ 0x9A] = I("\u0011?\n", "sVmxg");
        EntitySlime.I[0x31 ^ 0x37] = I("8\u001d\u0012)\u0016", "KpsEz");
        EntitySlime.I[0xAE ^ 0xA9] = I(" !+h%9:(%/", "MNIFD");
        EntitySlime.I[0x92 ^ 0x9A] = I("\n \u000bD+\u000b&\u0004\u000fv", "gOijX");
        EntitySlime.I[0xAF ^ 0xA6] = I("'9\u0002", "EPeSP");
        EntitySlime.I[0xB5 ^ 0xBF] = I("\u0015\u000185\u000e", "flYYb");
        EntitySlime.I[0x82 ^ 0x89] = I("\u0006=\u0012i?\u0007;\u001d\"b", "kRpGL");
        EntitySlime.I[0x70 ^ 0x7C] = I("'9\u0012", "EPuMT");
        EntitySlime.I[0x6C ^ 0x61] = I("\u0015\u001c)\u0000\u001f", "fqHls");
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        int nextInt = this.rand.nextInt("   ".length());
        if (nextInt < "  ".length() && this.rand.nextFloat() < 0.5f * difficultyInstance.getClampedAdditionalDifficulty()) {
            ++nextInt;
        }
        this.setSlimeSize(" ".length() << nextInt);
        return super.onInitialSpawn(difficultyInstance, entityLivingData);
    }
    
    @Override
    protected String getDeathSound() {
        final StringBuilder sb = new StringBuilder(EntitySlime.I[0x7C ^ 0x77]);
        String s;
        if (this.getSlimeSize() > " ".length()) {
            s = EntitySlime.I[0xAF ^ 0xA3];
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        else {
            s = EntitySlime.I[0x82 ^ 0x8F];
        }
        return sb.append(s).toString();
    }
    
    protected void setSlimeSize(final int experienceValue) {
        this.dataWatcher.updateObject(0x82 ^ 0x92, (byte)experienceValue);
        this.setSize(0.51000005f * experienceValue, 0.51000005f * experienceValue);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(experienceValue * experienceValue);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.2f + 0.1f * experienceValue);
        this.setHealth(this.getMaxHealth());
        this.experienceValue = experienceValue;
    }
    
    protected boolean makesSoundOnLand() {
        if (this.getSlimeSize() > "  ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onDataWatcherUpdate(final int n) {
        if (n == (0x80 ^ 0x90)) {
            final int slimeSize = this.getSlimeSize();
            this.setSize(0.51000005f * slimeSize, 0.51000005f * slimeSize);
            this.rotationYaw = this.rotationYawHead;
            this.renderYawOffset = this.rotationYawHead;
            if (this.isInWater() && this.rand.nextInt(0x6A ^ 0x7E) == 0) {
                this.resetHeight();
            }
        }
        super.onDataWatcherUpdate(n);
    }
    
    protected boolean canDamagePlayer() {
        if (this.getSlimeSize() > " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void alterSquishAmount() {
        this.squishAmount *= 0.6f;
    }
    
    @Override
    public boolean getCanSpawnHere() {
        final BlockPos blockPos = new BlockPos(MathHelper.floor_double(this.posX), "".length(), MathHelper.floor_double(this.posZ));
        final Chunk chunkFromBlockCoords = this.worldObj.getChunkFromBlockCoords(blockPos);
        if (this.worldObj.getWorldInfo().getTerrainType() == WorldType.FLAT && this.rand.nextInt(0x89 ^ 0x8D) != " ".length()) {
            return "".length() != 0;
        }
        if (this.worldObj.getDifficulty() != EnumDifficulty.PEACEFUL) {
            if (this.worldObj.getBiomeGenForCoords(blockPos) == BiomeGenBase.swampland && this.posY > 50.0 && this.posY < 70.0 && this.rand.nextFloat() < 0.5f && this.rand.nextFloat() < this.worldObj.getCurrentMoonPhaseFactor() && this.worldObj.getLightFromNeighbors(new BlockPos(this)) <= this.rand.nextInt(0x7C ^ 0x74)) {
                return super.getCanSpawnHere();
            }
            if (this.rand.nextInt(0x23 ^ 0x29) == 0 && chunkFromBlockCoords.getRandomWithSeed(987234911L).nextInt(0x80 ^ 0x8A) == 0 && this.posY < 40.0) {
                return super.getCanSpawnHere();
            }
        }
        return "".length() != 0;
    }
    
    protected boolean makesSoundOnJump() {
        if (this.getSlimeSize() > 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void func_175451_e(final EntityLivingBase entityLivingBase) {
        final int slimeSize = this.getSlimeSize();
        if (this.canEntityBeSeen(entityLivingBase) && this.getDistanceSqToEntity(entityLivingBase) < 0.6 * slimeSize * 0.6 * slimeSize && entityLivingBase.attackEntityFrom(DamageSource.causeMobDamage(this), this.getAttackStrength())) {
            this.playSound(EntitySlime.I[0x9B ^ 0x9C], 1.0f, (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            this.applyEnchantments(this, entityLivingBase);
        }
    }
    
    @Override
    public float getEyeHeight() {
        return 0.625f * this.height;
    }
    
    protected int getJumpDelay() {
        return this.rand.nextInt(0x31 ^ 0x25) + (0x44 ^ 0x4E);
    }
    
    protected int getAttackStrength() {
        return this.getSlimeSize();
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        return "".length();
    }
    
    public EntitySlime(final World world) {
        super(world);
        this.moveHelper = new SlimeMoveHelper(this);
        this.tasks.addTask(" ".length(), new AISlimeFloat(this));
        this.tasks.addTask("  ".length(), new AISlimeAttack(this));
        this.tasks.addTask("   ".length(), new AISlimeFaceRandom(this));
        this.tasks.addTask(0x32 ^ 0x37, new AISlimeHop(this));
        this.targetTasks.addTask(" ".length(), new EntityAIFindEntityNearestPlayer(this));
        this.targetTasks.addTask("   ".length(), new EntityAIFindEntityNearest(this, EntityIronGolem.class));
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f * this.getSlimeSize();
    }
    
    protected String getJumpSound() {
        final StringBuilder sb = new StringBuilder(EntitySlime.I[0xAD ^ 0xA9]);
        String s;
        if (this.getSlimeSize() > " ".length()) {
            s = EntitySlime.I[0x2E ^ 0x2B];
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            s = EntitySlime.I[0x27 ^ 0x21];
        }
        return sb.append(s).toString();
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
            if (-1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected String getHurtSound() {
        final StringBuilder sb = new StringBuilder(EntitySlime.I[0xAC ^ 0xA4]);
        String s;
        if (this.getSlimeSize() > " ".length()) {
            s = EntitySlime.I[0x34 ^ 0x3D];
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            s = EntitySlime.I[0xA1 ^ 0xAB];
        }
        return sb.append(s).toString();
    }
    
    @Override
    protected void jump() {
        this.motionY = 0.41999998688697815;
        this.isAirBorne = (" ".length() != 0);
    }
    
    static {
        I();
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x7F ^ 0x6F, (byte)" ".length());
    }
    
    protected EntitySlime createInstance() {
        return new EntitySlime(this.worldObj);
    }
    
    @Override
    public void setDead() {
        final int slimeSize = this.getSlimeSize();
        if (!this.worldObj.isRemote && slimeSize > " ".length() && this.getHealth() <= 0.0f) {
            final int n = "  ".length() + this.rand.nextInt("   ".length());
            int i = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
            while (i < n) {
                final float n2 = (i % "  ".length() - 0.5f) * slimeSize / 4.0f;
                final float n3 = (i / "  ".length() - 0.5f) * slimeSize / 4.0f;
                final EntitySlime instance = this.createInstance();
                if (this.hasCustomName()) {
                    instance.setCustomNameTag(this.getCustomNameTag());
                }
                if (this.isNoDespawnRequired()) {
                    instance.enablePersistence();
                }
                instance.setSlimeSize(slimeSize / "  ".length());
                instance.setLocationAndAngles(this.posX + n2, this.posY + 0.5, this.posZ + n3, this.rand.nextFloat() * 360.0f, 0.0f);
                this.worldObj.spawnEntityInWorld(instance);
                ++i;
            }
        }
        super.setDead();
    }
    
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SLIME;
    }
    
    @Override
    public void onUpdate() {
        if (!this.worldObj.isRemote && this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.getSlimeSize() > 0) {
            this.isDead = (" ".length() != 0);
        }
        this.squishFactor += (this.squishAmount - this.squishFactor) * 0.5f;
        this.prevSquishFactor = this.squishFactor;
        super.onUpdate();
        if (this.onGround && !this.wasOnGround) {
            final int slimeSize = this.getSlimeSize();
            int i = "".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
            while (i < slimeSize * (0x51 ^ 0x59)) {
                final float n = this.rand.nextFloat() * 3.1415927f * 2.0f;
                final float n2 = this.rand.nextFloat() * 0.5f + 0.5f;
                this.worldObj.spawnParticle(this.getParticleType(), this.posX + MathHelper.sin(n) * slimeSize * 0.5f * n2, this.getEntityBoundingBox().minY, this.posZ + MathHelper.cos(n) * slimeSize * 0.5f * n2, 0.0, 0.0, 0.0, new int["".length()]);
                ++i;
            }
            if (this.makesSoundOnLand()) {
                this.playSound(this.getJumpSound(), this.getSoundVolume(), ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f) / 0.8f);
            }
            this.squishAmount = -0.5f;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else if (!this.onGround && this.wasOnGround) {
            this.squishAmount = 1.0f;
        }
        this.wasOnGround = this.onGround;
        this.alterSquishAmount();
    }
    
    @Override
    public void applyEntityCollision(final Entity entity) {
        super.applyEntityCollision(entity);
        if (entity instanceof EntityIronGolem && this.canDamagePlayer()) {
            this.func_175451_e((EntityLivingBase)entity);
        }
    }
    
    @Override
    protected Item getDropItem() {
        Item slime_ball;
        if (this.getSlimeSize() == " ".length()) {
            slime_ball = Items.slime_ball;
            "".length();
            if (2 == 0) {
                throw null;
            }
        }
        else {
            slime_ball = null;
        }
        return slime_ball;
    }
    
    @Override
    public void onCollideWithPlayer(final EntityPlayer entityPlayer) {
        if (this.canDamagePlayer()) {
            this.func_175451_e(entityPlayer);
        }
    }
    
    static class AISlimeFloat extends EntityAIBase
    {
        private EntitySlime slime;
        
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
                if (4 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean shouldExecute() {
            if (!this.slime.isInWater() && !this.slime.isInLava()) {
                return "".length() != 0;
            }
            return " ".length() != 0;
        }
        
        @Override
        public void updateTask() {
            if (this.slime.getRNG().nextFloat() < 0.8f) {
                this.slime.getJumpHelper().setJumping();
            }
            ((SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.2);
        }
        
        public AISlimeFloat(final EntitySlime slime) {
            this.slime = slime;
            this.setMutexBits(0xA6 ^ 0xA3);
            ((PathNavigateGround)slime.getNavigator()).setCanSwim(" ".length() != 0);
        }
    }
    
    static class SlimeMoveHelper extends EntityMoveHelper
    {
        private boolean field_179923_j;
        private float field_179922_g;
        private EntitySlime slime;
        private int field_179924_h;
        
        public SlimeMoveHelper(final EntitySlime slime) {
            super(slime);
            this.slime = slime;
        }
        
        @Override
        public void onUpdateMoveHelper() {
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, this.field_179922_g, 30.0f);
            this.entity.rotationYawHead = this.entity.rotationYaw;
            this.entity.renderYawOffset = this.entity.rotationYaw;
            if (!this.update) {
                this.entity.setMoveForward(0.0f);
                "".length();
                if (2 == 4) {
                    throw null;
                }
            }
            else {
                this.update = ("".length() != 0);
                if (this.entity.onGround) {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                    final int field_179924_h = this.field_179924_h;
                    this.field_179924_h = field_179924_h - " ".length();
                    if (field_179924_h <= 0) {
                        this.field_179924_h = this.slime.getJumpDelay();
                        if (this.field_179923_j) {
                            this.field_179924_h /= "   ".length();
                        }
                        this.slime.getJumpHelper().setJumping();
                        if (this.slime.makesSoundOnJump()) {
                            this.slime.playSound(this.slime.getJumpSound(), this.slime.getSoundVolume(), ((this.slime.getRNG().nextFloat() - this.slime.getRNG().nextFloat()) * 0.2f + 1.0f) * 0.8f);
                            "".length();
                            if (0 >= 3) {
                                throw null;
                            }
                        }
                    }
                    else {
                        final EntitySlime slime = this.slime;
                        final EntitySlime slime2 = this.slime;
                        final float n = 0.0f;
                        slime2.moveForward = n;
                        slime.moveStrafing = n;
                        this.entity.setAIMoveSpeed(0.0f);
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                }
                else {
                    this.entity.setAIMoveSpeed((float)(this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue()));
                }
            }
        }
        
        public void setSpeed(final double speed) {
            this.speed = speed;
            this.update = (" ".length() != 0);
        }
        
        public void func_179920_a(final float field_179922_g, final boolean field_179923_j) {
            this.field_179922_g = field_179922_g;
            this.field_179923_j = field_179923_j;
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
                if (0 < 0) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
    
    static class AISlimeHop extends EntityAIBase
    {
        private EntitySlime slime;
        
        @Override
        public void updateTask() {
            ((SlimeMoveHelper)this.slime.getMoveHelper()).setSpeed(1.0);
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
                if (4 <= 2) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean shouldExecute() {
            return " ".length() != 0;
        }
        
        public AISlimeHop(final EntitySlime slime) {
            this.slime = slime;
            this.setMutexBits(0x6D ^ 0x68);
        }
    }
    
    static class AISlimeAttack extends EntityAIBase
    {
        private EntitySlime slime;
        private int field_179465_b;
        
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
                if (3 < 1) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        @Override
        public boolean shouldExecute() {
            final EntityLivingBase attackTarget = this.slime.getAttackTarget();
            int n;
            if (attackTarget == null) {
                n = "".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else if (!attackTarget.isEntityAlive()) {
                n = "".length();
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
            else if (attackTarget instanceof EntityPlayer && ((EntityPlayer)attackTarget).capabilities.disableDamage) {
                n = "".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            return n != 0;
        }
        
        @Override
        public boolean continueExecuting() {
            final EntityLivingBase attackTarget = this.slime.getAttackTarget();
            int n;
            if (attackTarget == null) {
                n = "".length();
                "".length();
                if (-1 >= 3) {
                    throw null;
                }
            }
            else if (!attackTarget.isEntityAlive()) {
                n = "".length();
                "".length();
                if (2 == -1) {
                    throw null;
                }
            }
            else if (attackTarget instanceof EntityPlayer && ((EntityPlayer)attackTarget).capabilities.disableDamage) {
                n = "".length();
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else if ((this.field_179465_b -= " ".length()) > 0) {
                n = " ".length();
                "".length();
                if (-1 >= 1) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            return n != 0;
        }
        
        @Override
        public void startExecuting() {
            this.field_179465_b = 49 + 78 + 120 + 53;
            super.startExecuting();
        }
        
        @Override
        public void updateTask() {
            this.slime.faceEntity(this.slime.getAttackTarget(), 10.0f, 10.0f);
            ((SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.slime.rotationYaw, this.slime.canDamagePlayer());
        }
        
        public AISlimeAttack(final EntitySlime slime) {
            this.slime = slime;
            this.setMutexBits("  ".length());
        }
    }
    
    static class AISlimeFaceRandom extends EntityAIBase
    {
        private float field_179459_b;
        private EntitySlime slime;
        private int field_179460_c;
        
        @Override
        public boolean shouldExecute() {
            if (this.slime.getAttackTarget() == null && (this.slime.onGround || this.slime.isInWater() || this.slime.isInLava())) {
                return " ".length() != 0;
            }
            return "".length() != 0;
        }
        
        @Override
        public void updateTask() {
            final int field_179460_c = this.field_179460_c - " ".length();
            this.field_179460_c = field_179460_c;
            if (field_179460_c <= 0) {
                this.field_179460_c = (0x92 ^ 0xBA) + this.slime.getRNG().nextInt(0x21 ^ 0x1D);
                this.field_179459_b = this.slime.getRNG().nextInt(185 + 251 - 317 + 241);
            }
            ((SlimeMoveHelper)this.slime.getMoveHelper()).func_179920_a(this.field_179459_b, "".length() != 0);
        }
        
        public AISlimeFaceRandom(final EntitySlime slime) {
            this.slime = slime;
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
                if (1 == 3) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
