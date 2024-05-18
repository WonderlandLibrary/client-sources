package net.minecraft.entity.monster;

import net.minecraft.block.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.player.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.potion.*;
import net.minecraft.entity.passive.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.ai.*;
import net.minecraft.util.*;
import com.google.common.base.*;
import java.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.entity.*;

public class EntityZombie extends EntityMob
{
    private static final UUID babySpeedBoostUUID;
    private float zombieWidth;
    private float zombieHeight;
    private boolean isBreakDoorsTaskSet;
    protected static final IAttribute reinforcementChance;
    private final EntityAIBreakDoor breakDoor;
    private static final AttributeModifier babySpeedBoostModifier;
    private static final String[] I;
    private int conversionTime;
    
    @Override
    protected String getHurtSound() {
        return EntityZombie.I[0x52 ^ 0x55];
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntityZombie.I[0x91 ^ 0x98], 0.15f, 1.0f);
    }
    
    public void setChild(final boolean childSize) {
        final DataWatcher dataWatcher = this.getDataWatcher();
        final int n = 0x5A ^ 0x56;
        int n2;
        if (childSize) {
            n2 = " ".length();
            "".length();
            if (1 >= 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
        if (this.worldObj != null && !this.worldObj.isRemote) {
            final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
            entityAttribute.removeModifier(EntityZombie.babySpeedBoostModifier);
            if (childSize) {
                entityAttribute.applyModifier(EntityZombie.babySpeedBoostModifier);
            }
        }
        this.setChildSize(childSize);
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x2A ^ 0x3A)) {
            if (!this.isSilent()) {
                this.worldObj.playSound(this.posX + 0.5, this.posY + 0.5, this.posZ + 0.5, EntityZombie.I[0x1B ^ 0x3], 1.0f + this.rand.nextFloat(), this.rand.nextFloat() * 0.7f + 0.3f, "".length() != 0);
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        if (this.isChild()) {
            nbtTagCompound.setBoolean(EntityZombie.I[0x98 ^ 0x92], " ".length() != 0);
        }
        if (this.isVillager()) {
            nbtTagCompound.setBoolean(EntityZombie.I[0x75 ^ 0x7E], " ".length() != 0);
        }
        final String s = EntityZombie.I[0xAD ^ 0xA1];
        int conversionTime;
        if (this.isConverting()) {
            conversionTime = this.conversionTime;
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else {
            conversionTime = -" ".length();
        }
        nbtTagCompound.setInteger(s, conversionTime);
        nbtTagCompound.setBoolean(EntityZombie.I[0x79 ^ 0x74], this.isBreakDoorsTaskSet());
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, final float n) {
        if (super.attackEntityFrom(damageSource, n)) {
            EntityLivingBase attackTarget = this.getAttackTarget();
            if (attackTarget == null && damageSource.getEntity() instanceof EntityLivingBase) {
                attackTarget = (EntityLivingBase)damageSource.getEntity();
            }
            if (attackTarget != null && this.worldObj.getDifficulty() == EnumDifficulty.HARD && this.rand.nextFloat() < this.getEntityAttribute(EntityZombie.reinforcementChance).getAttributeValue()) {
                final int floor_double = MathHelper.floor_double(this.posX);
                final int floor_double2 = MathHelper.floor_double(this.posY);
                final int floor_double3 = MathHelper.floor_double(this.posZ);
                final EntityZombie entityZombie = new EntityZombie(this.worldObj);
                int i = "".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
                while (i < (0xA6 ^ 0x94)) {
                    final int n2 = floor_double + MathHelper.getRandomIntegerInRange(this.rand, 0x6E ^ 0x69, 0x7A ^ 0x52) * MathHelper.getRandomIntegerInRange(this.rand, -" ".length(), " ".length());
                    final int n3 = floor_double2 + MathHelper.getRandomIntegerInRange(this.rand, 0x1F ^ 0x18, 0x2B ^ 0x3) * MathHelper.getRandomIntegerInRange(this.rand, -" ".length(), " ".length());
                    final int n4 = floor_double3 + MathHelper.getRandomIntegerInRange(this.rand, 0x23 ^ 0x24, 0x8B ^ 0xA3) * MathHelper.getRandomIntegerInRange(this.rand, -" ".length(), " ".length());
                    if (World.doesBlockHaveSolidTopSurface(this.worldObj, new BlockPos(n2, n3 - " ".length(), n4)) && this.worldObj.getLightFromNeighbors(new BlockPos(n2, n3, n4)) < (0x90 ^ 0x9A)) {
                        entityZombie.setPosition(n2, n3, n4);
                        if (!this.worldObj.isAnyPlayerWithinRangeAt(n2, n3, n4, 7.0) && this.worldObj.checkNoEntityCollision(entityZombie.getEntityBoundingBox(), entityZombie) && this.worldObj.getCollidingBoundingBoxes(entityZombie, entityZombie.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(entityZombie.getEntityBoundingBox())) {
                            this.worldObj.spawnEntityInWorld(entityZombie);
                            entityZombie.setAttackTarget(attackTarget);
                            entityZombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)), null);
                            this.getEntityAttribute(EntityZombie.reinforcementChance).applyModifier(new AttributeModifier(EntityZombie.I[0x76 ^ 0x72], -0.05000000074505806, "".length()));
                            entityZombie.getEntityAttribute(EntityZombie.reinforcementChance).applyModifier(new AttributeModifier(EntityZombie.I[0xA0 ^ 0xA5], -0.05000000074505806, "".length()));
                            "".length();
                            if (-1 >= 1) {
                                throw null;
                            }
                            break;
                        }
                    }
                    ++i;
                }
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentEquippedItem = entityPlayer.getCurrentEquippedItem();
        if (currentEquippedItem != null && currentEquippedItem.getItem() == Items.golden_apple && currentEquippedItem.getMetadata() == 0 && this.isVillager() && this.isPotionActive(Potion.weakness)) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack = currentEquippedItem;
                itemStack.stackSize -= " ".length();
            }
            if (currentEquippedItem.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isRemote) {
                this.startConversion(this.rand.nextInt(896 + 1218 - 498 + 785) + (1317 + 1858 + 50 + 375));
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected Item getDropItem() {
        return Items.rotten_flesh;
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        if (this.isChild()) {
            this.experienceValue *= (int)2.5f;
        }
        return super.getExperiencePoints(entityPlayer);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.getBoolean(EntityZombie.I[0x12 ^ 0x1C])) {
            this.setChild(" ".length() != 0);
        }
        if (nbtTagCompound.getBoolean(EntityZombie.I[0xAA ^ 0xA5])) {
            this.setVillager(" ".length() != 0);
        }
        if (nbtTagCompound.hasKey(EntityZombie.I[0xD ^ 0x1D], 0x4E ^ 0x2D) && nbtTagCompound.getInteger(EntityZombie.I[0x41 ^ 0x50]) > -" ".length()) {
            this.startConversion(nbtTagCompound.getInteger(EntityZombie.I[0x72 ^ 0x60]));
        }
        this.setBreakDoorsAItask(nbtTagCompound.getBoolean(EntityZombie.I[0x27 ^ 0x34]));
    }
    
    @Override
    public int getTotalArmorValue() {
        int n = super.getTotalArmorValue() + "  ".length();
        if (n > (0x75 ^ 0x61)) {
            n = (0x8F ^ 0x9B);
        }
        return n;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote && !this.isChild()) {
            final float brightness = this.getBrightness(1.0f);
            final BlockPos blockPos = new BlockPos(this.posX, Math.round(this.posY), this.posZ);
            if (brightness > 0.5f && this.rand.nextFloat() * 30.0f < (brightness - 0.4f) * 2.0f && this.worldObj.canSeeSky(blockPos)) {
                int n = " ".length();
                final ItemStack equipmentInSlot = this.getEquipmentInSlot(0x45 ^ 0x41);
                if (equipmentInSlot != null) {
                    if (equipmentInSlot.isItemStackDamageable()) {
                        equipmentInSlot.setItemDamage(equipmentInSlot.getItemDamage() + this.rand.nextInt("  ".length()));
                        if (equipmentInSlot.getItemDamage() >= equipmentInSlot.getMaxDamage()) {
                            this.renderBrokenItemStack(equipmentInSlot);
                            this.setCurrentItemOrArmor(0xA ^ 0xE, null);
                        }
                    }
                    n = "".length();
                }
                if (n != 0) {
                    this.setFire(0x7 ^ 0xF);
                }
            }
        }
        if (this.isRiding() && this.getAttackTarget() != null && this.ridingEntity instanceof EntityChicken) {
            ((EntityLiving)this.ridingEntity).getNavigator().setPath(this.getNavigator().getPath(), 1.5);
        }
        super.onLivingUpdate();
    }
    
    protected final void multiplySize(final float n) {
        super.setSize(this.zombieWidth * n, this.zombieHeight * n);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.getDataWatcher().addObject(0xA ^ 0x6, (byte)"".length());
        this.getDataWatcher().addObject(0xA5 ^ 0xA8, (byte)"".length());
        this.getDataWatcher().addObject(0x35 ^ 0x3B, (byte)"".length());
    }
    
    public boolean isVillager() {
        if (this.getDataWatcher().getWatchableObjectByte(0x42 ^ 0x4F) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onUpdate() {
        if (!this.worldObj.isRemote && this.isConverting()) {
            this.conversionTime -= this.getConversionTimeBoost();
            if (this.conversionTime <= 0) {
                this.convertToVillager();
            }
        }
        super.onUpdate();
    }
    
    protected int getConversionTimeBoost() {
        int length = " ".length();
        if (this.rand.nextFloat() < 0.01f) {
            int length2 = "".length();
            final BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            int n = (int)this.posX - (0x42 ^ 0x46);
            "".length();
            if (true != true) {
                throw null;
            }
            while (n < (int)this.posX + (0x30 ^ 0x34) && length2 < (0x1D ^ 0x13)) {
                int n2 = (int)this.posY - (0x65 ^ 0x61);
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                while (n2 < (int)this.posY + (0xB3 ^ 0xB7) && length2 < (0xAF ^ 0xA1)) {
                    int n3 = (int)this.posZ - (0x9 ^ 0xD);
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                    while (n3 < (int)this.posZ + (0xAF ^ 0xAB) && length2 < (0xA1 ^ 0xAF)) {
                        final Block block = this.worldObj.getBlockState(mutableBlockPos.func_181079_c(n, n2, n3)).getBlock();
                        if (block == Blocks.iron_bars || block == Blocks.bed) {
                            if (this.rand.nextFloat() < 0.3f) {
                                ++length;
                            }
                            ++length2;
                        }
                        ++n3;
                    }
                    ++n2;
                }
                ++n;
            }
        }
        return length;
    }
    
    protected void startConversion(final int conversionTime) {
        this.conversionTime = conversionTime;
        this.getDataWatcher().updateObject(0x6F ^ 0x61, (byte)" ".length());
        this.removePotionEffect(Potion.weakness.id);
        this.addPotionEffect(new PotionEffect(Potion.damageBoost.id, conversionTime, Math.min(this.worldObj.getDifficulty().getDifficultyId() - " ".length(), "".length())));
        this.worldObj.setEntityState(this, (byte)(0xA ^ 0x1A));
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase entityLivingBase) {
        super.onKillEntity(entityLivingBase);
        if ((this.worldObj.getDifficulty() == EnumDifficulty.NORMAL || this.worldObj.getDifficulty() == EnumDifficulty.HARD) && entityLivingBase instanceof EntityVillager) {
            if (this.worldObj.getDifficulty() != EnumDifficulty.HARD && this.rand.nextBoolean()) {
                return;
            }
            final EntityLiving entityLiving = (EntityLiving)entityLivingBase;
            final EntityZombie entityZombie = new EntityZombie(this.worldObj);
            entityZombie.copyLocationAndAnglesFrom(entityLivingBase);
            this.worldObj.removeEntity(entityLivingBase);
            entityZombie.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityZombie)), null);
            entityZombie.setVillager(" ".length() != 0);
            if (entityLivingBase.isChild()) {
                entityZombie.setChild(" ".length() != 0);
            }
            entityZombie.setNoAI(entityLiving.isAIDisabled());
            if (entityLiving.hasCustomName()) {
                entityZombie.setCustomNameTag(entityLiving.getCustomNameTag());
                entityZombie.setAlwaysRenderNameTag(entityLiving.getAlwaysRenderNameTag());
            }
            this.worldObj.spawnEntityInWorld(entityZombie);
            this.worldObj.playAuxSFXAtEntity(null, 69 + 883 - 813 + 877, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), "".length());
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        final boolean attackEntityAsMob = super.attackEntityAsMob(entity);
        if (attackEntityAsMob) {
            final int difficultyId = this.worldObj.getDifficulty().getDifficultyId();
            if (this.getHeldItem() == null && this.isBurning() && this.rand.nextFloat() < difficultyId * 0.3f) {
                entity.setFire("  ".length() * difficultyId);
            }
        }
        return attackEntityAsMob;
    }
    
    @Override
    public double getYOffset() {
        double n;
        if (this.isChild()) {
            n = 0.0;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            n = -0.35;
        }
        return n;
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (damageSource.getEntity() instanceof EntityCreeper && !(this instanceof EntityPigZombie) && ((EntityCreeper)damageSource.getEntity()).getPowered() && ((EntityCreeper)damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper)damageSource.getEntity()).func_175493_co();
            this.entityDropItem(new ItemStack(Items.skull, " ".length(), "  ".length()), 0.0f);
        }
    }
    
    @Override
    protected final void setSize(final float zombieWidth, final float zombieHeight) {
        int n;
        if (this.zombieWidth > 0.0f && this.zombieHeight > 0.0f) {
            n = " ".length();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        final int n2 = n;
        this.zombieWidth = zombieWidth;
        this.zombieHeight = zombieHeight;
        if (n2 == 0) {
            this.multiplySize(1.0f);
        }
    }
    
    protected void applyEntityAI() {
        this.tasks.addTask(0x7F ^ 0x7B, new EntityAIAttackOnCollide(this, EntityVillager.class, 1.0, (boolean)(" ".length() != 0)));
        this.tasks.addTask(0x4C ^ 0x48, new EntityAIAttackOnCollide(this, EntityIronGolem.class, 1.0, (boolean)(" ".length() != 0)));
        this.tasks.addTask(0x87 ^ 0x81, new EntityAIMoveThroughVillage(this, 1.0, (boolean)("".length() != 0)));
        final EntityAITasks targetTasks = this.targetTasks;
        final int length = " ".length();
        final int length2 = " ".length();
        final Class[] array = new Class[" ".length()];
        array["".length()] = EntityPigZombie.class;
        targetTasks.addTask(length, new EntityAIHurtByTarget(this, (boolean)(length2 != 0), array));
        this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityPlayer.class, (boolean)(" ".length() != 0)));
        this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityVillager.class, (boolean)("".length() != 0)));
        this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityIronGolem.class, (boolean)(" ".length() != 0)));
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.followRange).setBaseValue(35.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.23000000417232513);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(3.0);
        this.getAttributeMap().registerAttribute(EntityZombie.reinforcementChance).setBaseValue(this.rand.nextDouble() * 0.10000000149011612);
    }
    
    protected void convertToVillager() {
        final EntityVillager entityVillager = new EntityVillager(this.worldObj);
        entityVillager.copyLocationAndAnglesFrom(this);
        entityVillager.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(entityVillager)), null);
        entityVillager.setLookingForHome();
        if (this.isChild()) {
            entityVillager.setGrowingAge(-(15658 + 10773 - 4727 + 2296));
        }
        this.worldObj.removeEntity(this);
        entityVillager.setNoAI(this.isAIDisabled());
        if (this.hasCustomName()) {
            entityVillager.setCustomNameTag(this.getCustomNameTag());
            entityVillager.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
        }
        this.worldObj.spawnEntityInWorld(entityVillager);
        entityVillager.addPotionEffect(new PotionEffect(Potion.confusion.id, 63 + 30 - 84 + 191, "".length()));
        this.worldObj.playAuxSFXAtEntity(null, 194 + 726 - 549 + 646, new BlockPos((int)this.posX, (int)this.posY, (int)this.posZ), "".length());
    }
    
    @Override
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance equipmentBasedOnDifficulty) {
        super.setEquipmentBasedOnDifficulty(equipmentBasedOnDifficulty);
        final float nextFloat = this.rand.nextFloat();
        float n;
        if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
            n = 0.05f;
            "".length();
            if (3 == -1) {
                throw null;
            }
        }
        else {
            n = 0.01f;
        }
        if (nextFloat < n) {
            if (this.rand.nextInt("   ".length()) == 0) {
                this.setCurrentItemOrArmor("".length(), new ItemStack(Items.iron_sword));
                "".length();
                if (-1 >= 0) {
                    throw null;
                }
            }
            else {
                this.setCurrentItemOrArmor("".length(), new ItemStack(Items.iron_shovel));
            }
        }
    }
    
    @Override
    public float getEyeHeight() {
        float n = 1.74f;
        if (this.isChild()) {
            n -= 0.81;
        }
        return n;
    }
    
    public boolean isBreakDoorsTaskSet() {
        return this.isBreakDoorsTaskSet;
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
            if (2 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public EntityZombie(final World world) {
        super(world);
        this.breakDoor = new EntityAIBreakDoor(this);
        this.isBreakDoorsTaskSet = ("".length() != 0);
        this.zombieWidth = -1.0f;
        ((PathNavigateGround)this.getNavigator()).setBreakDoors(" ".length() != 0);
        this.tasks.addTask("".length(), new EntityAISwimming(this));
        this.tasks.addTask("  ".length(), new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.0, (boolean)("".length() != 0)));
        this.tasks.addTask(0x41 ^ 0x44, new EntityAIMoveTowardsRestriction(this, 1.0));
        this.tasks.addTask(0x4 ^ 0x3, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0x1D ^ 0x15, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0x87 ^ 0x8F, new EntityAILookIdle(this));
        this.applyEntityAI();
        this.setSize(0.6f, 1.95f);
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, (IEntityLivingData)onInitialSpawn);
        final float clampedAdditionalDifficulty = difficultyInstance.getClampedAdditionalDifficulty();
        int canPickUpLoot;
        if (this.rand.nextFloat() < 0.55f * clampedAdditionalDifficulty) {
            canPickUpLoot = " ".length();
            "".length();
            if (-1 >= 3) {
                throw null;
            }
        }
        else {
            canPickUpLoot = "".length();
        }
        this.setCanPickUpLoot(canPickUpLoot != 0);
        if (onInitialSpawn == null) {
            int n;
            if (this.worldObj.rand.nextFloat() < 0.05f) {
                n = " ".length();
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                n = "".length();
            }
            int n2;
            if (this.worldObj.rand.nextFloat() < 0.05f) {
                n2 = " ".length();
                "".length();
                if (-1 == 4) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            onInitialSpawn = new GroupData((boolean)(n != 0), (boolean)(n2 != 0), null);
        }
        if (onInitialSpawn instanceof GroupData) {
            final GroupData groupData = (GroupData)onInitialSpawn;
            if (groupData.isVillager) {
                this.setVillager(" ".length() != 0);
            }
            if (groupData.isChild) {
                this.setChild(" ".length() != 0);
                if (this.worldObj.rand.nextFloat() < 0.05) {
                    final List<Entity> entitiesWithinAABB = this.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityChicken.class, this.getEntityBoundingBox().expand(5.0, 3.0, 5.0), (com.google.common.base.Predicate<? super Entity>)EntitySelectors.IS_STANDALONE);
                    if (!entitiesWithinAABB.isEmpty()) {
                        final EntityChicken entityChicken = entitiesWithinAABB.get("".length());
                        entityChicken.setChickenJockey(" ".length() != 0);
                        this.mountEntity(entityChicken);
                        "".length();
                        if (4 < 2) {
                            throw null;
                        }
                    }
                }
                else if (this.worldObj.rand.nextFloat() < 0.05) {
                    final EntityChicken entityChicken2 = new EntityChicken(this.worldObj);
                    entityChicken2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, 0.0f);
                    entityChicken2.onInitialSpawn(difficultyInstance, null);
                    entityChicken2.setChickenJockey(" ".length() != 0);
                    this.worldObj.spawnEntityInWorld(entityChicken2);
                    this.mountEntity(entityChicken2);
                }
            }
        }
        int breakDoorsAItask;
        if (this.rand.nextFloat() < clampedAdditionalDifficulty * 0.1f) {
            breakDoorsAItask = " ".length();
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            breakDoorsAItask = "".length();
        }
        this.setBreakDoorsAItask(breakDoorsAItask != 0);
        this.setEquipmentBasedOnDifficulty(difficultyInstance);
        this.setEnchantmentBasedOnDifficulty(difficultyInstance);
        if (this.getEquipmentInSlot(0xF ^ 0xB) == null) {
            final Calendar currentDate = this.worldObj.getCurrentDate();
            if (currentDate.get("  ".length()) + " ".length() == (0x28 ^ 0x22) && currentDate.get(0x4 ^ 0x1) == (0xAF ^ 0xB0) && this.rand.nextFloat() < 0.25f) {
                final int n3 = 0x3F ^ 0x3B;
                Block block;
                if (this.rand.nextFloat() < 0.1f) {
                    block = Blocks.lit_pumpkin;
                    "".length();
                    if (3 <= 1) {
                        throw null;
                    }
                }
                else {
                    block = Blocks.pumpkin;
                }
                this.setCurrentItemOrArmor(n3, new ItemStack(block));
                this.equipmentDropChances[0x2F ^ 0x2B] = 0.0f;
            }
        }
        this.getEntityAttribute(SharedMonsterAttributes.knockbackResistance).applyModifier(new AttributeModifier(EntityZombie.I[0x2E ^ 0x3A], this.rand.nextDouble() * 0.05000000074505806, "".length()));
        final double n4 = this.rand.nextDouble() * 1.5 * clampedAdditionalDifficulty;
        if (n4 > 1.0) {
            this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier(EntityZombie.I[0x29 ^ 0x3C], n4, "  ".length()));
        }
        if (this.rand.nextFloat() < clampedAdditionalDifficulty * 0.05f) {
            this.getEntityAttribute(EntityZombie.reinforcementChance).applyModifier(new AttributeModifier(EntityZombie.I[0x66 ^ 0x70], this.rand.nextDouble() * 0.25 + 0.5, "".length()));
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).applyModifier(new AttributeModifier(EntityZombie.I[0xA7 ^ 0xB0], this.rand.nextDouble() * 3.0 + 1.0, "  ".length()));
            this.setBreakDoorsAItask(" ".length() != 0);
        }
        return (IEntityLivingData)onInitialSpawn;
    }
    
    static {
        I();
        reinforcementChance = new RangedAttribute(null, EntityZombie.I["".length()], 0.0, 0.0, 1.0).setDescription(EntityZombie.I[" ".length()]);
        babySpeedBoostUUID = UUID.fromString(EntityZombie.I["  ".length()]);
        babySpeedBoostModifier = new AttributeModifier(EntityZombie.babySpeedBoostUUID, EntityZombie.I["   ".length()], 0.5, " ".length());
    }
    
    public void setChildSize(final boolean b) {
        float n;
        if (b) {
            n = 0.5f;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            n = 1.0f;
        }
        this.multiplySize(n);
    }
    
    public void setBreakDoorsAItask(final boolean isBreakDoorsTaskSet) {
        if (this.isBreakDoorsTaskSet != isBreakDoorsTaskSet) {
            this.isBreakDoorsTaskSet = isBreakDoorsTaskSet;
            if (isBreakDoorsTaskSet) {
                this.tasks.addTask(" ".length(), this.breakDoor);
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                this.tasks.removeTask(this.breakDoor);
            }
        }
    }
    
    public void setVillager(final boolean b) {
        final DataWatcher dataWatcher = this.getDataWatcher();
        final int n = 0xA0 ^ 0xAD;
        int n2;
        if (b) {
            n2 = " ".length();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
    }
    
    @Override
    protected boolean func_175448_a(final ItemStack itemStack) {
        int n;
        if (itemStack.getItem() == Items.egg && this.isChild() && this.isRiding()) {
            n = "".length();
            "".length();
            if (1 >= 3) {
                throw null;
            }
        }
        else {
            n = (super.func_175448_a(itemStack) ? 1 : 0);
        }
        return n != 0;
    }
    
    public boolean isConverting() {
        if (this.getDataWatcher().getWatchableObjectByte(0x8A ^ 0x84) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public boolean isChild() {
        if (this.getDataWatcher().getWatchableObjectByte(0x23 ^ 0x2F) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected boolean canDespawn() {
        int n;
        if (this.isConverting()) {
            n = "".length();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    @Override
    protected String getDeathSound() {
        return EntityZombie.I[0x71 ^ 0x79];
    }
    
    private static void I() {
        (I = new String[0x76 ^ 0x6F])["".length()] = I("+\u0007(\u000f+4F6\u001d#&\u0006\u0017\b+?\u000e*\u001f!4\u0005 \u00036\"", "QhEmB");
        EntityZombie.I[" ".length()] = I(";<\u0002\u0011\u0003H\u001e\u0006\u000f\u0003\u000e#\u0011\u0005\b\u0005)\r\u0012\u001eH\u000f\u000b\u0007\u0003\u000b)", "hLcfm");
        EntityZombie.I["  ".length()] = I("-MYsn-AWhaZBXhl[D\\h\u001a,E(hj*1\\\u0004jXB*}kY", "otnEX");
        EntityZombie.I["   ".length()] = I("-\u0000$\u0000F\u001c\u0011#\u001c\u0002O\u0003)\u0016\u0015\u001b", "oaFyf");
        EntityZombie.I[0xBC ^ 0xB8] = I("\n\u001e5\u0006<5Q*\u0001<>\u00177\u001665\u001c=\n!p\u00129\b95\u0003x\u0007=1\u0003?\u0001", "PqXdU");
        EntityZombie.I[0x3E ^ 0x3B] = I("6\u001a\u0018\u000b9\tU\u0007\f9\u0002\u0013\u001a\u001b3\t\u0018\u0010\u0007$L\u0016\u0014\u0005<\t\u0010U\n8\r\u0007\u0012\f", "luuiP");
        EntityZombie.I[0x6F ^ 0x69] = I("\t.)G2\u000b,)\u0000-J2*\u0010", "dAKiH");
        EntityZombie.I[0x76 ^ 0x71] = I("\u001b$\u0007J\"\u0019&\u0007\r=X#\u0010\u0016,", "vKedX");
        EntityZombie.I[0x15 ^ 0x1D] = I("\"\n;f\u0002 \b;!\u001da\u0001<)\f'", "OeYHx");
        EntityZombie.I[0x3 ^ 0xA] = I("\n6!Y4\b4!\u001e+I*7\u0012>", "gYCwN");
        EntityZombie.I[0x64 ^ 0x6E] = I("\u00047\u0004\u0004\t4", "MDFek");
        EntityZombie.I[0x92 ^ 0x99] = I(" \"\u0002\u001a\u0007\u000503\u0016\u0019", "iQTsk");
        EntityZombie.I[0x8A ^ 0x86] = I("-\u001a+?+\u001c\u0006,& :\u001c(,", "nuEIN");
        EntityZombie.I[0x8D ^ 0x80] = I("'\f:\u00159\u0001\f?\u0013$\u000b\u001f'", "dmTWK");
        EntityZombie.I[0x81 ^ 0x8F] = I("<2\u0014)$\f", "uAVHF");
        EntityZombie.I[0x4D ^ 0x42] = I("<\u0005\u0005\u000e\u0015\u0019\u00174\u0002\u000b", "uvSgy");
        EntityZombie.I[0x75 ^ 0x65] = I(")\u00008\u001e\u0002\u0018\u001c?\u0007\t>\u0006;\r", "joVhg");
        EntityZombie.I[0x93 ^ 0x82] = I("&(\u000b>?\u00174\f'41.\b-", "eGeHZ");
        EntityZombie.I[0xB5 ^ 0xA7] = I(".\u001a\u001780\u001f\u0006\u0010!;9\u001c\u0014+", "muyNU");
        EntityZombie.I[0x44 ^ 0x57] = I("/)\u001e\u0014\u001d\t)\u001b\u0012\u0000\u0003:\u0003", "lHpVo");
        EntityZombie.I[0x3 ^ 0x17] = I("\u0000\u001b\u00031\u0002?Z\u001e%\f%\u0014M7\u0002<\u000f\u001e", "RzmUm");
        EntityZombie.I[0x32 ^ 0x27] = I("4(:)7\u000bi.\"5\u0004 1`+\u0016(##x\u0004&:8+", "fITMX");
        EntityZombie.I[0x15 ^ 0x3] = I("<\u0016\u00056\u0006\u0002S\u001e=\u000e\u0012\u001a\u0001r\u0001\u001f\u001d\u0011!", "psdRc");
        EntityZombie.I[0x37 ^ 0x20] = I("-);2\n\u0013l 9\u0002\u0003%?v\r\u000e\"/%", "aLZVo");
        EntityZombie.I[0xDA ^ 0xC2] = I("\u0004\u001d!@\b\u0006\u001f!\u0007\u0017G\u0000&\u0003\u0017\r\u000b", "irCnr");
    }
    
    @Override
    protected void addRandomDrop() {
        switch (this.rand.nextInt("   ".length())) {
            case 0: {
                this.dropItem(Items.iron_ingot, " ".length());
                "".length();
                if (0 <= -1) {
                    throw null;
                }
                break;
            }
            case 1: {
                this.dropItem(Items.carrot, " ".length());
                "".length();
                if (-1 != -1) {
                    throw null;
                }
                break;
            }
            case 2: {
                this.dropItem(Items.potato, " ".length());
                break;
            }
        }
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    protected String getLivingSound() {
        return EntityZombie.I[0x51 ^ 0x57];
    }
    
    class GroupData implements IEntityLivingData
    {
        final EntityZombie this$0;
        public boolean isVillager;
        public boolean isChild;
        
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
        
        private GroupData(final EntityZombie this$0, final boolean isChild, final boolean isVillager) {
            this.this$0 = this$0;
            this.isChild = ("".length() != 0);
            this.isVillager = ("".length() != 0);
            this.isChild = isChild;
            this.isVillager = isVillager;
        }
        
        GroupData(final EntityZombie entityZombie, final boolean b, final boolean b2, final GroupData groupData) {
            this(entityZombie, b, b2);
        }
    }
}
