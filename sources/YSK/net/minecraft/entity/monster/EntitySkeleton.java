package net.minecraft.entity.monster;

import net.minecraft.nbt.*;
import net.minecraft.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.entity.player.*;
import net.minecraft.stats.*;
import net.minecraft.init.*;
import java.util.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.world.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.ai.*;
import net.minecraft.potion.*;
import net.minecraft.util.*;

public class EntitySkeleton extends EntityMob implements IRangedAttackMob
{
    private EntityAIAttackOnCollide aiAttackOnCollide;
    private EntityAIArrowAttack aiArrowAttack;
    private static final String[] I;
    
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
            if (1 == 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        super.setCurrentItemOrArmor(n, itemStack);
        if (!this.worldObj.isRemote && n == 0) {
            this.setCombatTask();
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setByte(EntitySkeleton.I[0x2 ^ 0x5], (byte)this.getSkeletonType());
    }
    
    @Override
    protected Item getDropItem() {
        return Items.arrow;
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        if (damageSource.getSourceOfDamage() instanceof EntityArrow && damageSource.getEntity() instanceof EntityPlayer) {
            final EntityPlayer entityPlayer = (EntityPlayer)damageSource.getEntity();
            final double n = entityPlayer.posX - this.posX;
            final double n2 = entityPlayer.posZ - this.posZ;
            if (n * n + n2 * n2 >= 2500.0) {
                entityPlayer.triggerAchievement(AchievementList.snipeSkeleton);
                "".length();
                if (0 >= 1) {
                    throw null;
                }
            }
        }
        else if (damageSource.getEntity() instanceof EntityCreeper && ((EntityCreeper)damageSource.getEntity()).getPowered() && ((EntityCreeper)damageSource.getEntity()).isAIEnabled()) {
            ((EntityCreeper)damageSource.getEntity()).func_175493_co();
            final Item skull = Items.skull;
            final int length = " ".length();
            int n3;
            if (this.getSkeletonType() == " ".length()) {
                n3 = " ".length();
                "".length();
                if (1 < 1) {
                    throw null;
                }
            }
            else {
                n3 = "".length();
            }
            this.entityDropItem(new ItemStack(skull, length, n3), 0.0f);
        }
    }
    
    public void setSkeletonType(final int n) {
        this.dataWatcher.updateObject(0xD ^ 0x0, (byte)n);
        int isImmuneToFire;
        if (n == " ".length()) {
            isImmuneToFire = " ".length();
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            isImmuneToFire = "".length();
        }
        this.isImmuneToFire = (isImmuneToFire != 0);
        if (n == " ".length()) {
            this.setSize(0.72f, 2.535f);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            this.setSize(0.6f, 1.95f);
        }
    }
    
    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0xCE ^ 0xC3, new Byte((byte)"".length()));
    }
    
    @Override
    protected String getDeathSound() {
        return EntitySkeleton.I["  ".length()];
    }
    
    @Override
    protected String getHurtSound() {
        return EntitySkeleton.I[" ".length()];
    }
    
    @Override
    protected String getLivingSound() {
        return EntitySkeleton.I["".length()];
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.25);
    }
    
    @Override
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, IEntityLivingData onInitialSpawn) {
        onInitialSpawn = super.onInitialSpawn(difficultyInstance, onInitialSpawn);
        if (this.worldObj.provider instanceof WorldProviderHell && this.getRNG().nextInt(0xB5 ^ 0xB0) > 0) {
            this.tasks.addTask(0x4F ^ 0x4B, this.aiAttackOnCollide);
            this.setSkeletonType(" ".length());
            this.setCurrentItemOrArmor("".length(), new ItemStack(Items.stone_sword));
            this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        else {
            this.tasks.addTask(0x33 ^ 0x37, this.aiArrowAttack);
            this.setEquipmentBasedOnDifficulty(difficultyInstance);
            this.setEnchantmentBasedOnDifficulty(difficultyInstance);
        }
        int canPickUpLoot;
        if (this.rand.nextFloat() < 0.55f * difficultyInstance.getClampedAdditionalDifficulty()) {
            canPickUpLoot = " ".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            canPickUpLoot = "".length();
        }
        this.setCanPickUpLoot(canPickUpLoot != 0);
        if (this.getEquipmentInSlot(0x7 ^ 0x3) == null) {
            final Calendar currentDate = this.worldObj.getCurrentDate();
            if (currentDate.get("  ".length()) + " ".length() == (0xAD ^ 0xA7) && currentDate.get(0xA2 ^ 0xA7) == (0xD ^ 0x12) && this.rand.nextFloat() < 0.25f) {
                final int n = 0x46 ^ 0x42;
                Block block;
                if (this.rand.nextFloat() < 0.1f) {
                    block = Blocks.lit_pumpkin;
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
                else {
                    block = Blocks.pumpkin;
                }
                this.setCurrentItemOrArmor(n, new ItemStack(block));
                this.equipmentDropChances[0x14 ^ 0x10] = 0.0f;
            }
        }
        return onInitialSpawn;
    }
    
    private static void I() {
        (I = new String[0xC9 ^ 0xC1])["".length()] = I(".\u001a\u0003f8(\u0010\r-?,\u001bO;*:", "CuaHK");
        EntitySkeleton.I[" ".length()] = I("7\u0001/Y;1\u000b!\u0012<5\u0000c\u001f=(\u001a", "ZnMwH");
        EntitySkeleton.I["  ".length()] = I("(\u00000d\u000b.\n>/\f*\u0001|.\u001d$\u001b:", "EoRJx");
        EntitySkeleton.I["   ".length()] = I("\u001c6-d6\u001a<#/1\u001e7a91\u0014)", "qYOJE");
        EntitySkeleton.I[0x2D ^ 0x29] = I("\u0005\t\u0005>\u001d\u001aF\t5\u0005", "whkZr");
        EntitySkeleton.I[0x51 ^ 0x54] = I("%$\u0017\u0018\u0007\u0002 \u001c \u001b\u0006*", "vOrtb");
        EntitySkeleton.I[0x65 ^ 0x63] = I("58,6\u0016\u0012<'\u000e\n\u00166", "fSIZs");
        EntitySkeleton.I[0x29 ^ 0x2E] = I("\u00102\u000e\u0002\u001776\u0005:\u000b3<", "CYknr");
    }
    
    public int getSkeletonType() {
        return this.dataWatcher.getWatchableObjectByte(0x77 ^ 0x7A);
    }
    
    static {
        I();
    }
    
    @Override
    public void updateRidden() {
        super.updateRidden();
        if (this.ridingEntity instanceof EntityCreature) {
            this.renderYawOffset = ((EntityCreature)this.ridingEntity).renderYawOffset;
        }
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        if (this.getSkeletonType() == " ".length()) {
            final int n2 = this.rand.nextInt("   ".length() + n) - " ".length();
            int i = "".length();
            "".length();
            if (!true) {
                throw null;
            }
            while (i < n2) {
                this.dropItem(Items.coal, " ".length());
                ++i;
            }
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            final int nextInt = this.rand.nextInt("   ".length() + n);
            int j = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (j < nextInt) {
                this.dropItem(Items.arrow, " ".length());
                ++j;
            }
        }
        final int nextInt2 = this.rand.nextInt("   ".length() + n);
        int k = "".length();
        "".length();
        if (false) {
            throw null;
        }
        while (k < nextInt2) {
            this.dropItem(Items.bone, " ".length());
            ++k;
        }
    }
    
    @Override
    public void attackEntityWithRangedAttack(final EntityLivingBase entityLivingBase, final float n) {
        final EntityArrow entityArrow = new EntityArrow(this.worldObj, this, entityLivingBase, 1.6f, (0x3D ^ 0x33) - this.worldObj.getDifficulty().getDifficultyId() * (0xC4 ^ 0xC0));
        final int enchantmentLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, this.getHeldItem());
        final int enchantmentLevel2 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, this.getHeldItem());
        entityArrow.setDamage(n * 2.0f + this.rand.nextGaussian() * 0.25 + this.worldObj.getDifficulty().getDifficultyId() * 0.11f);
        if (enchantmentLevel > 0) {
            entityArrow.setDamage(entityArrow.getDamage() + enchantmentLevel * 0.5 + 0.5);
        }
        if (enchantmentLevel2 > 0) {
            entityArrow.setKnockbackStrength(enchantmentLevel2);
        }
        if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, this.getHeldItem()) > 0 || this.getSkeletonType() == " ".length()) {
            entityArrow.setFire(0xED ^ 0x89);
        }
        this.playSound(EntitySkeleton.I[0x29 ^ 0x2D], 1.0f, 1.0f / (this.getRNG().nextFloat() * 0.4f + 0.8f));
        this.worldObj.spawnEntityInWorld(entityArrow);
    }
    
    @Override
    public double getYOffset() {
        double n;
        if (this.isChild()) {
            n = 0.0;
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        else {
            n = -0.35;
        }
        return n;
    }
    
    public EntitySkeleton(final World world) {
        super(world);
        this.aiArrowAttack = new EntityAIArrowAttack(this, 1.0, 0x1D ^ 0x9, 0xB1 ^ 0x8D, 15.0f);
        this.aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 1.2, "".length() != 0);
        this.tasks.addTask(" ".length(), new EntityAISwimming(this));
        this.tasks.addTask("  ".length(), new EntityAIRestrictSun(this));
        this.tasks.addTask("   ".length(), new EntityAIFleeSun(this, 1.0));
        this.tasks.addTask("   ".length(), new EntityAIAvoidEntity<Object>(this, EntityWolf.class, 6.0f, 1.0, 1.2));
        this.tasks.addTask(0x33 ^ 0x37, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0x7E ^ 0x78, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0x93 ^ 0x95, new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAIHurtByTarget(this, (boolean)("".length() != 0), new Class["".length()]));
        this.targetTasks.addTask("  ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityPlayer.class, (boolean)(" ".length() != 0)));
        this.targetTasks.addTask("   ".length(), new EntityAINearestAttackableTarget<Object>(this, EntityIronGolem.class, (boolean)(" ".length() != 0)));
        if (world != null && !world.isRemote) {
            this.setCombatTask();
        }
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        if (super.attackEntityAsMob(entity)) {
            if (this.getSkeletonType() == " ".length() && entity instanceof EntityLivingBase) {
                ((EntityLivingBase)entity).addPotionEffect(new PotionEffect(Potion.wither.id, 179 + 136 - 175 + 60));
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntitySkeleton.I["   ".length()], 0.15f, 1.0f);
    }
    
    public void setCombatTask() {
        this.tasks.removeTask(this.aiAttackOnCollide);
        this.tasks.removeTask(this.aiArrowAttack);
        final ItemStack heldItem = this.getHeldItem();
        if (heldItem != null && heldItem.getItem() == Items.bow) {
            this.tasks.addTask(0x46 ^ 0x42, this.aiArrowAttack);
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            this.tasks.addTask(0x37 ^ 0x33, this.aiAttackOnCollide);
        }
    }
    
    @Override
    public float getEyeHeight() {
        float eyeHeight;
        if (this.getSkeletonType() == " ".length()) {
            eyeHeight = super.getEyeHeight();
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        else {
            eyeHeight = 1.74f;
        }
        return eyeHeight;
    }
    
    @Override
    protected void addRandomDrop() {
        if (this.getSkeletonType() == " ".length()) {
            this.entityDropItem(new ItemStack(Items.skull, " ".length(), " ".length()), 0.0f);
        }
    }
    
    @Override
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance equipmentBasedOnDifficulty) {
        super.setEquipmentBasedOnDifficulty(equipmentBasedOnDifficulty);
        this.setCurrentItemOrArmor("".length(), new ItemStack(Items.bow));
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(EntitySkeleton.I[0xAF ^ 0xAA], 0x46 ^ 0x25)) {
            this.setSkeletonType(nbtTagCompound.getByte(EntitySkeleton.I[0x8C ^ 0x8A]));
        }
        this.setCombatTask();
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.worldObj.isDaytime() && !this.worldObj.isRemote) {
            final float brightness = this.getBrightness(1.0f);
            final BlockPos blockPos = new BlockPos(this.posX, Math.round(this.posY), this.posZ);
            if (brightness > 0.5f && this.rand.nextFloat() * 30.0f < (brightness - 0.4f) * 2.0f && this.worldObj.canSeeSky(blockPos)) {
                int n = " ".length();
                final ItemStack equipmentInSlot = this.getEquipmentInSlot(0xB8 ^ 0xBC);
                if (equipmentInSlot != null) {
                    if (equipmentInSlot.isItemStackDamageable()) {
                        equipmentInSlot.setItemDamage(equipmentInSlot.getItemDamage() + this.rand.nextInt("  ".length()));
                        if (equipmentInSlot.getItemDamage() >= equipmentInSlot.getMaxDamage()) {
                            this.renderBrokenItemStack(equipmentInSlot);
                            this.setCurrentItemOrArmor(0x67 ^ 0x63, null);
                        }
                    }
                    n = "".length();
                }
                if (n != 0) {
                    this.setFire(0x10 ^ 0x18);
                }
            }
        }
        if (this.worldObj.isRemote && this.getSkeletonType() == " ".length()) {
            this.setSize(0.72f, 2.535f);
        }
        super.onLivingUpdate();
    }
}
