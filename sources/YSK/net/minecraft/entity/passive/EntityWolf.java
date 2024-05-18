package net.minecraft.entity.passive;

import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.player.*;
import com.google.common.base.*;
import net.minecraft.nbt.*;
import net.minecraft.init.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.item.*;
import net.minecraft.util.*;
import net.minecraft.block.*;
import net.minecraft.entity.*;
import net.minecraft.entity.monster.*;

public class EntityWolf extends EntityTameable
{
    private static final String[] I;
    private float prevTimeWolfIsShaking;
    private float headRotationCourseOld;
    private boolean isWet;
    private float timeWolfIsShaking;
    private boolean isShaking;
    private float headRotationCourse;
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x68 ^ 0x60)) {
            this.isShaking = (" ".length() != 0);
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    public EntityWolf(final World world) {
        super(world);
        this.setSize(0.6f, 0.8f);
        ((PathNavigateGround)this.getNavigator()).setAvoidsWater(" ".length() != 0);
        this.tasks.addTask(" ".length(), new EntityAISwimming(this));
        this.tasks.addTask("  ".length(), this.aiSit);
        this.tasks.addTask("   ".length(), new EntityAILeapAtTarget(this, 0.4f));
        this.tasks.addTask(0x1E ^ 0x1A, new EntityAIAttackOnCollide(this, 1.0, (boolean)(" ".length() != 0)));
        this.tasks.addTask(0x95 ^ 0x90, new EntityAIFollowOwner(this, 1.0, 10.0f, 2.0f));
        this.tasks.addTask(0x7 ^ 0x1, new EntityAIMate(this, 1.0));
        this.tasks.addTask(0xB9 ^ 0xBE, new EntityAIWander(this, 1.0));
        this.tasks.addTask(0x23 ^ 0x2B, new EntityAIBeg(this, 8.0f));
        this.tasks.addTask(0x54 ^ 0x5D, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0f));
        this.tasks.addTask(0x3D ^ 0x34, new EntityAILookIdle(this));
        this.targetTasks.addTask(" ".length(), new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask("  ".length(), new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask("   ".length(), new EntityAIHurtByTarget(this, (boolean)(" ".length() != 0), new Class["".length()]));
        this.targetTasks.addTask(0xB5 ^ 0xB1, new EntityAITargetNonTamed<Object>(this, EntityAnimal.class, (boolean)("".length() != 0), (com.google.common.base.Predicate<?>)new Predicate<Entity>(this) {
            final EntityWolf this$0;
            
            public boolean apply(final Entity entity) {
                if (!(entity instanceof EntitySheep) && !(entity instanceof EntityRabbit)) {
                    return "".length() != 0;
                }
                return " ".length() != 0;
            }
            
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
                    if (3 <= -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
        }));
        this.targetTasks.addTask(0x2D ^ 0x28, new EntityAINearestAttackableTarget<Object>(this, EntitySkeleton.class, (boolean)("".length() != 0)));
        this.setTamed("".length() != 0);
    }
    
    public boolean isBegging() {
        if (this.dataWatcher.getWatchableObjectByte(0x57 ^ 0x44) == " ".length()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public void onUpdate() {
        super.onUpdate();
        this.headRotationCourseOld = this.headRotationCourse;
        if (this.isBegging()) {
            this.headRotationCourse += (1.0f - this.headRotationCourse) * 0.4f;
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            this.headRotationCourse += (0.0f - this.headRotationCourse) * 0.4f;
        }
        if (this.isWet()) {
            this.isWet = (" ".length() != 0);
            this.isShaking = ("".length() != 0);
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
            "".length();
            if (4 < 3) {
                throw null;
            }
        }
        else if ((this.isWet || this.isShaking) && this.isShaking) {
            if (this.timeWolfIsShaking == 0.0f) {
                this.playSound(EntityWolf.I[0x52 ^ 0x5E], this.getSoundVolume(), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
            }
            this.prevTimeWolfIsShaking = this.timeWolfIsShaking;
            this.timeWolfIsShaking += 0.05f;
            if (this.prevTimeWolfIsShaking >= 2.0f) {
                this.isWet = ("".length() != 0);
                this.isShaking = ("".length() != 0);
                this.prevTimeWolfIsShaking = 0.0f;
                this.timeWolfIsShaking = 0.0f;
            }
            if (this.timeWolfIsShaking > 0.4f) {
                final float n = (float)this.getEntityBoundingBox().minY;
                final int n2 = (int)(MathHelper.sin((this.timeWolfIsShaking - 0.4f) * 3.1415927f) * 7.0f);
                int i = "".length();
                "".length();
                if (3 < 2) {
                    throw null;
                }
                while (i < n2) {
                    this.worldObj.spawnParticle(EnumParticleTypes.WATER_SPLASH, this.posX + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f, n + 0.8f, this.posZ + (this.rand.nextFloat() * 2.0f - 1.0f) * this.width * 0.5f, this.motionX, this.motionY, this.motionZ, new int["".length()]);
                    ++i;
                }
            }
        }
    }
    
    @Override
    public void setTamed(final boolean tamed) {
        super.setTamed(tamed);
        if (tamed) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
            "".length();
            if (1 < 0) {
                throw null;
            }
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(4.0);
    }
    
    @Override
    public boolean canMateWith(final EntityAnimal entityAnimal) {
        if (entityAnimal == this) {
            return "".length() != 0;
        }
        if (!this.isTamed()) {
            return "".length() != 0;
        }
        if (!(entityAnimal instanceof EntityWolf)) {
            return "".length() != 0;
        }
        final EntityWolf entityWolf = (EntityWolf)entityAnimal;
        int n;
        if (!entityWolf.isTamed()) {
            n = "".length();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (entityWolf.isSitting()) {
            n = "".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else if (this.isInLove() && entityWolf.isInLove()) {
            n = " ".length();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public float getInterestedAngle(final float n) {
        return (this.headRotationCourseOld + (this.headRotationCourse - this.headRotationCourseOld) * n) * 0.15f * 3.1415927f;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x77 ^ 0x65, new Float(this.getHealth()));
        this.dataWatcher.addObject(0x70 ^ 0x63, new Byte((byte)"".length()));
        this.dataWatcher.addObject(0x52 ^ 0x46, new Byte((byte)EnumDyeColor.RED.getMetadata()));
    }
    
    static {
        I();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(EntityWolf.I[" ".length()], this.isAngry());
        nbtTagCompound.setByte(EntityWolf.I["  ".length()], (byte)this.getCollarColor().getDyeDamage());
    }
    
    public float getShadingWhileWet(final float n) {
        return 0.75f + (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * n) / 2.0f * 0.25f;
    }
    
    @Override
    protected float getSoundVolume() {
        return 0.4f;
    }
    
    @Override
    protected String getHurtSound() {
        return EntityWolf.I[0x89 ^ 0x83];
    }
    
    @Override
    public float getEyeHeight() {
        return this.height * 0.8f;
    }
    
    @Override
    public boolean interact(final EntityPlayer entityPlayer) {
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (this.isTamed()) {
            if (currentItem != null) {
                if (currentItem.getItem() instanceof ItemFood) {
                    final ItemFood itemFood = (ItemFood)currentItem.getItem();
                    if (itemFood.isWolfsFavoriteMeat() && this.dataWatcher.getWatchableObjectFloat(0x21 ^ 0x33) < 20.0f) {
                        if (!entityPlayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack = currentItem;
                            itemStack.stackSize -= " ".length();
                        }
                        this.heal(itemFood.getHealAmount(currentItem));
                        if (currentItem.stackSize <= 0) {
                            entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                        }
                        return " ".length() != 0;
                    }
                }
                else if (currentItem.getItem() == Items.dye) {
                    final EnumDyeColor byDyeDamage = EnumDyeColor.byDyeDamage(currentItem.getMetadata());
                    if (byDyeDamage != this.getCollarColor()) {
                        this.setCollarColor(byDyeDamage);
                        if (!entityPlayer.capabilities.isCreativeMode) {
                            final ItemStack itemStack2 = currentItem;
                            if ((itemStack2.stackSize -= " ".length()) <= 0) {
                                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
                            }
                        }
                        return " ".length() != 0;
                    }
                }
            }
            if (this.isOwner(entityPlayer) && !this.worldObj.isRemote && !this.isBreedingItem(currentItem)) {
                final EntityAISit aiSit = this.aiSit;
                int sitting;
                if (this.isSitting()) {
                    sitting = "".length();
                    "".length();
                    if (1 < -1) {
                        throw null;
                    }
                }
                else {
                    sitting = " ".length();
                }
                aiSit.setSitting(sitting != 0);
                this.isJumping = ("".length() != 0);
                this.navigator.clearPathEntity();
                this.setAttackTarget(null);
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
        }
        else if (currentItem != null && currentItem.getItem() == Items.bone && !this.isAngry()) {
            if (!entityPlayer.capabilities.isCreativeMode) {
                final ItemStack itemStack3 = currentItem;
                itemStack3.stackSize -= " ".length();
            }
            if (currentItem.stackSize <= 0) {
                entityPlayer.inventory.setInventorySlotContents(entityPlayer.inventory.currentItem, null);
            }
            if (!this.worldObj.isRemote) {
                if (this.rand.nextInt("   ".length()) == 0) {
                    this.setTamed(" ".length() != 0);
                    this.navigator.clearPathEntity();
                    this.setAttackTarget(null);
                    this.aiSit.setSitting(" ".length() != 0);
                    this.setHealth(20.0f);
                    this.setOwnerId(entityPlayer.getUniqueID().toString());
                    this.playTameEffect(" ".length() != 0);
                    this.worldObj.setEntityState(this, (byte)(0x3E ^ 0x39));
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else {
                    this.playTameEffect("".length() != 0);
                    this.worldObj.setEntityState(this, (byte)(0x6 ^ 0x0));
                }
            }
            return " ".length() != 0;
        }
        return super.interact(entityPlayer);
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        final Entity entity = damageSource.getEntity();
        this.aiSit.setSitting("".length() != 0);
        if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
            n = (n + 1.0f) / 2.0f;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected String getLivingSound() {
        String s;
        if (this.isAngry()) {
            s = EntityWolf.I[0x73 ^ 0x75];
            "".length();
            if (2 >= 3) {
                throw null;
            }
        }
        else if (this.rand.nextInt("   ".length()) == 0) {
            if (this.isTamed() && this.dataWatcher.getWatchableObjectFloat(0xB3 ^ 0xA1) < 10.0f) {
                s = EntityWolf.I[0x55 ^ 0x52];
                "".length();
                if (-1 >= 4) {
                    throw null;
                }
            }
            else {
                s = EntityWolf.I[0x70 ^ 0x78];
                "".length();
                if (1 >= 3) {
                    throw null;
                }
            }
        }
        else {
            s = EntityWolf.I[0x7A ^ 0x73];
        }
        return s;
    }
    
    @Override
    public boolean attackEntityAsMob(final Entity entity) {
        final boolean attackEntity = entity.attackEntityFrom(DamageSource.causeMobDamage(this), (int)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue());
        if (attackEntity) {
            this.applyEnchantments(this, entity);
        }
        return attackEntity;
    }
    
    private static void I() {
        (I = new String[0x1 ^ 0xC])["".length()] = I("7\u000b'o\u001d5\b#o\u0019.\u00015", "ZdEAj");
        EntityWolf.I[" ".length()] = I("\r7\u000f\u0015\u0012", "LYhgk");
        EntityWolf.I["  ".length()] = I("6\u001a> \u0004\u00076= \n\u0007", "uuRLe");
        EntityWolf.I["   ".length()] = I("\u0005\b\u00016(", "DffDQ");
        EntityWolf.I[0xBD ^ 0xB9] = I("\u0010\u0000/\"%!,,\"+!", "SoCND");
        EntityWolf.I[0x8B ^ 0x8E] = I("\u0014\u0018;$8%48$6%", "WwWHY");
        EntityWolf.I[0xBF ^ 0xB9] = I("(\b(L\u0013*\u000b,L\u00037\b=\u000e", "EgJbd");
        EntityWolf.I[0x4F ^ 0x48] = I("('\u0007v%*$\u0003v%-!\u000b=", "EHeXR");
        EntityWolf.I[0x37 ^ 0x3F] = I("\u001e\u0005\u000b]=\u001c\u0006\u000f]:\u0012\u0004\u001d\u001a$\u0014", "sjisJ");
        EntityWolf.I[0xA7 ^ 0xAE] = I("\u0002=+}2\u0000>/}'\u000e \"", "oRISE");
        EntityWolf.I[0x8F ^ 0x85] = I("7\u001c\u0003J-5\u001f\u0007J2/\u0001\u0015", "ZsadZ");
        EntityWolf.I[0xE ^ 0x5] = I("\u0017\u00192G?\u0015\u001a6G,\u001f\u0017$\u0001", "zvPiH");
        EntityWolf.I[0x90 ^ 0x9C] = I("<\u000e\ba2>\r\fa69\u0000\u0001*", "QajOE");
    }
    
    public void setBegging(final boolean b) {
        if (b) {
            this.dataWatcher.updateObject(0x98 ^ 0x8B, (byte)" ".length());
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x9D ^ 0x8E, (byte)"".length());
        }
    }
    
    public float getTailRotation() {
        float n;
        if (this.isAngry()) {
            n = 1.5393804f;
            "".length();
            if (false) {
                throw null;
            }
        }
        else if (this.isTamed()) {
            n = (0.55f - (20.0f - this.dataWatcher.getWatchableObjectFloat(0x9B ^ 0x89)) * 0.02f) * 3.1415927f;
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            n = 0.62831855f;
        }
        return n;
    }
    
    public float getShakeAngle(final float n, final float n2) {
        float n3 = (this.prevTimeWolfIsShaking + (this.timeWolfIsShaking - this.prevTimeWolfIsShaking) * n + n2) / 1.8f;
        if (n3 < 0.0f) {
            n3 = 0.0f;
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else if (n3 > 1.0f) {
            n3 = 1.0f;
        }
        return MathHelper.sin(n3 * 3.1415927f) * MathHelper.sin(n3 * 3.1415927f * 11.0f) * 0.15f * 3.1415927f;
    }
    
    @Override
    protected Item getDropItem() {
        return Item.getItemById(-" ".length());
    }
    
    public boolean isWolfWet() {
        return this.isWet;
    }
    
    @Override
    protected boolean canDespawn() {
        if (!this.isTamed() && this.ticksExisted > 296 + 2269 - 645 + 480) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 0x77 ^ 0x7F;
    }
    
    public EnumDyeColor getCollarColor() {
        return EnumDyeColor.byDyeDamage(this.dataWatcher.getWatchableObjectByte(0xA4 ^ 0xB0) & (0x6D ^ 0x62));
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
            if (1 <= 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    protected String getDeathSound() {
        return EntityWolf.I[0xBB ^ 0xB0];
    }
    
    public void setCollarColor(final EnumDyeColor enumDyeColor) {
        this.dataWatcher.updateObject(0xBC ^ 0xA8, (byte)(enumDyeColor.getDyeDamage() & (0x7E ^ 0x71)));
    }
    
    @Override
    public boolean isBreedingItem(final ItemStack itemStack) {
        int n;
        if (itemStack == null) {
            n = "".length();
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else if (!(itemStack.getItem() instanceof ItemFood)) {
            n = "".length();
            "".length();
            if (4 <= 0) {
                throw null;
            }
        }
        else {
            n = (((ItemFood)itemStack.getItem()).isWolfsFavoriteMeat() ? 1 : 0);
        }
        return n != 0;
    }
    
    @Override
    protected void playStepSound(final BlockPos blockPos, final Block block) {
        this.playSound(EntityWolf.I["".length()], 0.15f, 1.0f);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.30000001192092896);
        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(20.0);
            "".length();
            if (4 < 2) {
                throw null;
            }
        }
        else {
            this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(8.0);
        }
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage);
        this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(2.0);
    }
    
    public boolean isAngry() {
        if ((this.dataWatcher.getWatchableObjectByte(0x34 ^ 0x24) & "  ".length()) != 0x0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    public int getVerticalFaceSpeed() {
        int verticalFaceSpeed;
        if (this.isSitting()) {
            verticalFaceSpeed = (0x5B ^ 0x4F);
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            verticalFaceSpeed = super.getVerticalFaceSpeed();
        }
        return verticalFaceSpeed;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable entityAgeable) {
        return this.createChild(entityAgeable);
    }
    
    @Override
    public EntityWolf createChild(final EntityAgeable entityAgeable) {
        final EntityWolf entityWolf = new EntityWolf(this.worldObj);
        final String ownerId = this.getOwnerId();
        if (ownerId != null && ownerId.trim().length() > 0) {
            entityWolf.setOwnerId(ownerId);
            entityWolf.setTamed(" ".length() != 0);
        }
        return entityWolf;
    }
    
    @Override
    public void setAttackTarget(final EntityLivingBase attackTarget) {
        super.setAttackTarget(attackTarget);
        if (attackTarget == null) {
            this.setAngry("".length() != 0);
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else if (!this.isTamed()) {
            this.setAngry(" ".length() != 0);
        }
    }
    
    public void setAngry(final boolean b) {
        final byte watchableObjectByte = this.dataWatcher.getWatchableObjectByte(0x6E ^ 0x7E);
        if (b) {
            this.dataWatcher.updateObject(0x1 ^ 0x11, (byte)(watchableObjectByte | "  ".length()));
            "".length();
            if (1 <= 0) {
                throw null;
            }
        }
        else {
            this.dataWatcher.updateObject(0x84 ^ 0x94, (byte)(watchableObjectByte & -"   ".length()));
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.setAngry(nbtTagCompound.getBoolean(EntityWolf.I["   ".length()]));
        if (nbtTagCompound.hasKey(EntityWolf.I[0x6E ^ 0x6A], 0xC9 ^ 0xAA)) {
            this.setCollarColor(EnumDyeColor.byDyeDamage(nbtTagCompound.getByte(EntityWolf.I[0x4C ^ 0x49])));
        }
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.worldObj.isRemote && this.isWet && !this.isShaking && !this.hasPath() && this.onGround) {
            this.isShaking = (" ".length() != 0);
            this.timeWolfIsShaking = 0.0f;
            this.prevTimeWolfIsShaking = 0.0f;
            this.worldObj.setEntityState(this, (byte)(0x75 ^ 0x7D));
        }
        if (!this.worldObj.isRemote && this.getAttackTarget() == null && this.isAngry()) {
            this.setAngry("".length() != 0);
        }
    }
    
    @Override
    public boolean allowLeashing() {
        if (!this.isAngry() && super.allowLeashing()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    @Override
    protected void updateAITasks() {
        this.dataWatcher.updateObject(0xAA ^ 0xB8, this.getHealth());
    }
    
    @Override
    public boolean shouldAttackEntity(final EntityLivingBase entityLivingBase, final EntityLivingBase entityLivingBase2) {
        if (!(entityLivingBase instanceof EntityCreeper) && !(entityLivingBase instanceof EntityGhast)) {
            if (entityLivingBase instanceof EntityWolf) {
                final EntityWolf entityWolf = (EntityWolf)entityLivingBase;
                if (entityWolf.isTamed() && entityWolf.getOwner() == entityLivingBase2) {
                    return "".length() != 0;
                }
            }
            int n;
            if (entityLivingBase instanceof EntityPlayer && entityLivingBase2 instanceof EntityPlayer && !((EntityPlayer)entityLivingBase2).canAttackPlayer((EntityPlayer)entityLivingBase)) {
                n = "".length();
                "".length();
                if (4 <= 2) {
                    throw null;
                }
            }
            else if (entityLivingBase instanceof EntityHorse && ((EntityHorse)entityLivingBase).isTame()) {
                n = "".length();
                "".length();
                if (-1 < -1) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            return n != 0;
        }
        return "".length() != 0;
    }
}
