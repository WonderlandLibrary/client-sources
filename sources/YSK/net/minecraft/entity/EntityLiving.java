package net.minecraft.entity;

import net.minecraft.world.biome.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.passive.*;
import java.util.*;
import net.minecraft.stats.*;
import net.minecraft.enchantment.*;
import net.minecraft.util.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.nbt.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.init.*;
import optfine.*;
import net.minecraft.world.*;
import net.minecraft.pathfinding.*;
import net.minecraft.entity.monster.*;
import net.minecraft.item.*;
import net.minecraft.profiler.*;

public abstract class EntityLiving extends EntityLivingBase
{
    protected int experienceValue;
    private Entity leashedToEntity;
    protected EntityJumpHelper jumpHelper;
    private NBTTagCompound leashNBTTag;
    private static final String __OBFID;
    private boolean canPickUpLoot;
    private EntitySenses senses;
    protected float[] equipmentDropChances;
    public BlockPos spawnPosition;
    protected final EntityAITasks tasks;
    private EntityLookHelper lookHelper;
    public BiomeGenBase spawnBiome;
    protected PathNavigate navigator;
    protected final EntityAITasks targetTasks;
    private static final String[] I;
    private ItemStack[] equipment;
    private boolean isLeashed;
    protected EntityMoveHelper moveHelper;
    public int livingSoundTime;
    private boolean persistenceRequired;
    public int randomMobsId;
    private EntityLivingBase attackTarget;
    private EntityBodyHelper bodyHelper;
    
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
            if (2 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    protected void setEquipmentBasedOnDifficulty(final DifficultyInstance difficultyInstance) {
        if (this.rand.nextFloat() < 0.15f * difficultyInstance.getClampedAdditionalDifficulty()) {
            int nextInt = this.rand.nextInt("  ".length());
            float n;
            if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                n = 0.1f;
                "".length();
                if (3 < 1) {
                    throw null;
                }
            }
            else {
                n = 0.25f;
            }
            final float n2 = n;
            if (this.rand.nextFloat() < 0.095f) {
                ++nextInt;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++nextInt;
            }
            if (this.rand.nextFloat() < 0.095f) {
                ++nextInt;
            }
            int i = "   ".length();
            "".length();
            if (false) {
                throw null;
            }
            while (i >= 0) {
                final ItemStack currentArmor = this.getCurrentArmor(i);
                if (i < "   ".length() && this.rand.nextFloat() < n2) {
                    "".length();
                    if (1 >= 3) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (currentArmor == null) {
                        final Item armorItemForSlot = getArmorItemForSlot(i + " ".length(), nextInt);
                        if (armorItemForSlot != null) {
                            this.setCurrentItemOrArmor(i + " ".length(), new ItemStack(armorItemForSlot));
                        }
                    }
                    --i;
                }
            }
        }
    }
    
    @Override
    public int getMaxFallHeight() {
        if (this.getAttackTarget() == null) {
            return "   ".length();
        }
        int length = (int)(this.getHealth() - this.getMaxHealth() * 0.33f) - ("   ".length() - this.worldObj.getDifficulty().getDifficultyId()) * (0x3A ^ 0x3E);
        if (length < 0) {
            length = "".length();
        }
        return length + "   ".length();
    }
    
    public EntitySenses getEntitySenses() {
        return this.senses;
    }
    
    @Override
    public ItemStack getCurrentArmor(final int n) {
        return this.equipment[n + " ".length()];
    }
    
    @Override
    protected float func_110146_f(final float n, final float n2) {
        this.bodyHelper.updateRenderAngles();
        return n2;
    }
    
    public void faceEntity(final Entity entity, final float n, final float n2) {
        final double n3 = entity.posX - this.posX;
        final double n4 = entity.posZ - this.posZ;
        double n5;
        if (entity instanceof EntityLivingBase) {
            final EntityLivingBase entityLivingBase = (EntityLivingBase)entity;
            n5 = entityLivingBase.posY + entityLivingBase.getEyeHeight() - (this.posY + this.getEyeHeight());
            "".length();
            if (false == true) {
                throw null;
            }
        }
        else {
            n5 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0 - (this.posY + this.getEyeHeight());
        }
        final double n6 = MathHelper.sqrt_double(n3 * n3 + n4 * n4);
        final float n7 = (float)(MathHelper.func_181159_b(n4, n3) * 180.0 / 3.141592653589793) - 90.0f;
        this.rotationPitch = this.updateRotation(this.rotationPitch, (float)(-(MathHelper.func_181159_b(n5, n6) * 180.0 / 3.141592653589793)), n2);
        this.rotationYaw = this.updateRotation(this.rotationYaw, n7, n);
    }
    
    protected boolean canDespawn() {
        return " ".length() != 0;
    }
    
    public int getMaxSpawnedInChunk() {
        return 0x50 ^ 0x54;
    }
    
    @Override
    public void setAIMoveSpeed(final float n) {
        super.setAIMoveSpeed(n);
        this.setMoveForward(n);
    }
    
    protected boolean interact(final EntityPlayer entityPlayer) {
        return "".length() != 0;
    }
    
    private float updateRotation(final float n, final float n2, final float n3) {
        float wrapAngleTo180_float = MathHelper.wrapAngleTo180_float(n2 - n);
        if (wrapAngleTo180_float > n3) {
            wrapAngleTo180_float = n3;
        }
        if (wrapAngleTo180_float < -n3) {
            wrapAngleTo180_float = -n3;
        }
        return n + wrapAngleTo180_float;
    }
    
    @Override
    public void onEntityUpdate() {
        super.onEntityUpdate();
        this.worldObj.theProfiler.startSection(EntityLiving.I["".length()]);
        if (this.isEntityAlive()) {
            final int nextInt = this.rand.nextInt(961 + 345 - 1165 + 859);
            final int livingSoundTime = this.livingSoundTime;
            this.livingSoundTime = livingSoundTime + " ".length();
            if (nextInt < livingSoundTime) {
                this.livingSoundTime = -this.getTalkInterval();
                this.playLivingSound();
            }
        }
        this.worldObj.theProfiler.endSection();
    }
    
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.worldObj.theProfiler.startSection(EntityLiving.I[0x2D ^ 0x35]);
        if (!this.worldObj.isRemote && this.canPickUpLoot() && !this.dead && this.worldObj.getGameRules().getBoolean(EntityLiving.I[0xB4 ^ 0xAD])) {
            final Iterator<EntityItem> iterator = this.worldObj.getEntitiesWithinAABB((Class<? extends EntityItem>)EntityItem.class, this.getEntityBoundingBox().expand(1.0, 0.0, 1.0)).iterator();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (iterator.hasNext()) {
                final EntityItem entityItem = iterator.next();
                if (!entityItem.isDead && entityItem.getEntityItem() != null && !entityItem.cannotPickup()) {
                    this.updateEquipmentIfNeeded(entityItem);
                }
            }
        }
        this.worldObj.theProfiler.endSection();
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        if (this.experienceValue <= 0) {
            return this.experienceValue;
        }
        int experienceValue = this.experienceValue;
        final ItemStack[] inventory = this.getInventory();
        int i = "".length();
        "".length();
        if (0 >= 4) {
            throw null;
        }
        while (i < inventory.length) {
            if (inventory[i] != null && this.equipmentDropChances[i] <= 1.0f) {
                experienceValue += " ".length() + this.rand.nextInt("   ".length());
            }
            ++i;
        }
        return experienceValue;
    }
    
    public EntityJumpHelper getJumpHelper() {
        return this.jumpHelper;
    }
    
    @Override
    public final boolean interactFirst(final EntityPlayer entityPlayer) {
        if (this.getLeashed() && this.getLeashedToEntity() == entityPlayer) {
            final int length = " ".length();
            int n;
            if (entityPlayer.capabilities.isCreativeMode) {
                n = "".length();
                "".length();
                if (2 < 0) {
                    throw null;
                }
            }
            else {
                n = " ".length();
            }
            this.clearLeashed(length != 0, n != 0);
            return " ".length() != 0;
        }
        final ItemStack currentItem = entityPlayer.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() == Items.lead && this.allowLeashing()) {
            if (!(this instanceof EntityTameable) || !((EntityTameable)this).isTamed()) {
                this.setLeashedToEntity(entityPlayer, " ".length() != 0);
                final ItemStack itemStack = currentItem;
                itemStack.stackSize -= " ".length();
                return " ".length() != 0;
            }
            if (((EntityTameable)this).isOwner(entityPlayer)) {
                this.setLeashedToEntity(entityPlayer, " ".length() != 0);
                final ItemStack itemStack2 = currentItem;
                itemStack2.stackSize -= " ".length();
                return " ".length() != 0;
            }
        }
        int n2;
        if (this.interact(entityPlayer)) {
            n2 = " ".length();
            "".length();
            if (4 <= 3) {
                throw null;
            }
        }
        else {
            n2 = (super.interactFirst(entityPlayer) ? 1 : 0);
        }
        return n2 != 0;
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x46 ^ 0x52)) {
            this.spawnExplosionParticle();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    private void recreateLeash() {
        Label_0397: {
            if (this.isLeashed && this.leashNBTTag != null) {
                if (this.leashNBTTag.hasKey(EntityLiving.I[0xA5 ^ 0x80], 0x5F ^ 0x5B) && this.leashNBTTag.hasKey(EntityLiving.I[0x68 ^ 0x4E], 0xA0 ^ 0xA4)) {
                    final UUID uuid = new UUID(this.leashNBTTag.getLong(EntityLiving.I[0xD ^ 0x2A]), this.leashNBTTag.getLong(EntityLiving.I[0x43 ^ 0x6B]));
                    final Iterator<EntityLivingBase> iterator = this.worldObj.getEntitiesWithinAABB((Class<? extends EntityLivingBase>)EntityLivingBase.class, this.getEntityBoundingBox().expand(10.0, 10.0, 10.0)).iterator();
                    "".length();
                    if (4 <= 0) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final EntityLivingBase leashedToEntity = iterator.next();
                        if (leashedToEntity.getUniqueID().equals(uuid)) {
                            this.leashedToEntity = leashedToEntity;
                            "".length();
                            if (2 < 2) {
                                throw null;
                            }
                            break Label_0397;
                        }
                    }
                    "".length();
                    if (4 < -1) {
                        throw null;
                    }
                }
                else if (this.leashNBTTag.hasKey(EntityLiving.I[0xB9 ^ 0x90], 0x7D ^ 0x1E) && this.leashNBTTag.hasKey(EntityLiving.I[0xC ^ 0x26], 0xE ^ 0x6D) && this.leashNBTTag.hasKey(EntityLiving.I[0x4B ^ 0x60], 0x6E ^ 0xD)) {
                    final BlockPos blockPos = new BlockPos(this.leashNBTTag.getInteger(EntityLiving.I[0xA0 ^ 0x8C]), this.leashNBTTag.getInteger(EntityLiving.I[0x73 ^ 0x5E]), this.leashNBTTag.getInteger(EntityLiving.I[0x60 ^ 0x4E]));
                    EntityLeashKnot leashedToEntity2 = EntityLeashKnot.getKnotForPosition(this.worldObj, blockPos);
                    if (leashedToEntity2 == null) {
                        leashedToEntity2 = EntityLeashKnot.createKnot(this.worldObj, blockPos);
                    }
                    this.leashedToEntity = leashedToEntity2;
                    "".length();
                    if (2 == 4) {
                        throw null;
                    }
                }
                else {
                    this.clearLeashed("".length() != 0, " ".length() != 0);
                }
            }
        }
        this.leashNBTTag = null;
    }
    
    static {
        I();
        __OBFID = EntityLiving.I[0x7E ^ 0x51];
    }
    
    @Override
    public boolean isServerWorld() {
        if (super.isServerWorld() && !this.isAIDisabled()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Entity getLeashedToEntity() {
        return this.leashedToEntity;
    }
    
    protected void updateEquipmentIfNeeded(final EntityItem entityItem) {
        final ItemStack entityItem2 = entityItem.getEntityItem();
        final int armorPosition = getArmorPosition(entityItem2);
        if (armorPosition > -" ".length()) {
            int n = " ".length();
            final ItemStack equipmentInSlot = this.getEquipmentInSlot(armorPosition);
            if (equipmentInSlot != null) {
                if (armorPosition == 0) {
                    if (entityItem2.getItem() instanceof ItemSword && !(equipmentInSlot.getItem() instanceof ItemSword)) {
                        n = " ".length();
                        "".length();
                        if (-1 >= 0) {
                            throw null;
                        }
                    }
                    else if (entityItem2.getItem() instanceof ItemSword && equipmentInSlot.getItem() instanceof ItemSword) {
                        final ItemSword itemSword = (ItemSword)entityItem2.getItem();
                        final ItemSword itemSword2 = (ItemSword)equipmentInSlot.getItem();
                        if (itemSword.getDamageVsEntity() != itemSword2.getDamageVsEntity()) {
                            int n2;
                            if (itemSword.getDamageVsEntity() > itemSword2.getDamageVsEntity()) {
                                n2 = " ".length();
                                "".length();
                                if (2 <= -1) {
                                    throw null;
                                }
                            }
                            else {
                                n2 = "".length();
                            }
                            n = n2;
                            "".length();
                            if (-1 != -1) {
                                throw null;
                            }
                        }
                        else {
                            int n3;
                            if (entityItem2.getMetadata() <= equipmentInSlot.getMetadata() && (!entityItem2.hasTagCompound() || equipmentInSlot.hasTagCompound())) {
                                n3 = "".length();
                                "".length();
                                if (0 < -1) {
                                    throw null;
                                }
                            }
                            else {
                                n3 = " ".length();
                            }
                            n = n3;
                            "".length();
                            if (3 >= 4) {
                                throw null;
                            }
                        }
                    }
                    else if (entityItem2.getItem() instanceof ItemBow && equipmentInSlot.getItem() instanceof ItemBow) {
                        int n4;
                        if (entityItem2.hasTagCompound() && !equipmentInSlot.hasTagCompound()) {
                            n4 = " ".length();
                            "".length();
                            if (3 < -1) {
                                throw null;
                            }
                        }
                        else {
                            n4 = "".length();
                        }
                        n = n4;
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        n = "".length();
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                    }
                }
                else if (entityItem2.getItem() instanceof ItemArmor && !(equipmentInSlot.getItem() instanceof ItemArmor)) {
                    n = " ".length();
                    "".length();
                    if (3 < 3) {
                        throw null;
                    }
                }
                else if (entityItem2.getItem() instanceof ItemArmor && equipmentInSlot.getItem() instanceof ItemArmor) {
                    final ItemArmor itemArmor = (ItemArmor)entityItem2.getItem();
                    final ItemArmor itemArmor2 = (ItemArmor)equipmentInSlot.getItem();
                    if (itemArmor.damageReduceAmount != itemArmor2.damageReduceAmount) {
                        int n5;
                        if (itemArmor.damageReduceAmount > itemArmor2.damageReduceAmount) {
                            n5 = " ".length();
                            "".length();
                            if (4 <= -1) {
                                throw null;
                            }
                        }
                        else {
                            n5 = "".length();
                        }
                        n = n5;
                        "".length();
                        if (2 < 1) {
                            throw null;
                        }
                    }
                    else {
                        int n6;
                        if (entityItem2.getMetadata() <= equipmentInSlot.getMetadata() && (!entityItem2.hasTagCompound() || equipmentInSlot.hasTagCompound())) {
                            n6 = "".length();
                            "".length();
                            if (3 == 0) {
                                throw null;
                            }
                        }
                        else {
                            n6 = " ".length();
                        }
                        n = n6;
                        "".length();
                        if (1 < 0) {
                            throw null;
                        }
                    }
                }
                else {
                    n = "".length();
                }
            }
            if (n != 0 && this.func_175448_a(entityItem2)) {
                if (equipmentInSlot != null && this.rand.nextFloat() - 0.1f < this.equipmentDropChances[armorPosition]) {
                    this.entityDropItem(equipmentInSlot, 0.0f);
                }
                if (entityItem2.getItem() == Items.diamond && entityItem.getThrower() != null) {
                    final EntityPlayer playerEntityByName = this.worldObj.getPlayerEntityByName(entityItem.getThrower());
                    if (playerEntityByName != null) {
                        playerEntityByName.triggerAchievement(AchievementList.diamondsToYou);
                    }
                }
                this.setCurrentItemOrArmor(armorPosition, entityItem2);
                this.equipmentDropChances[armorPosition] = 2.0f;
                this.persistenceRequired = (" ".length() != 0);
                this.onItemPickup(entityItem, " ".length());
                entityItem.setDead();
            }
        }
    }
    
    protected void setEnchantmentBasedOnDifficulty(final DifficultyInstance difficultyInstance) {
        final float clampedAdditionalDifficulty = difficultyInstance.getClampedAdditionalDifficulty();
        if (this.getHeldItem() != null && this.rand.nextFloat() < 0.25f * clampedAdditionalDifficulty) {
            EnchantmentHelper.addRandomEnchantment(this.rand, this.getHeldItem(), (int)(5.0f + clampedAdditionalDifficulty * this.rand.nextInt(0x80 ^ 0x92)));
        }
        int i = "".length();
        "".length();
        if (3 <= 2) {
            throw null;
        }
        while (i < (0xA5 ^ 0xA1)) {
            final ItemStack currentArmor = this.getCurrentArmor(i);
            if (currentArmor != null && this.rand.nextFloat() < 0.5f * clampedAdditionalDifficulty) {
                EnchantmentHelper.addRandomEnchantment(this.rand, currentArmor, (int)(5.0f + clampedAdditionalDifficulty * this.rand.nextInt(0x88 ^ 0x9A)));
            }
            ++i;
        }
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        this.equipment[n] = itemStack;
    }
    
    public void spawnExplosionParticle() {
        if (this.worldObj.isRemote) {
            int i = "".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
            while (i < (0xBA ^ 0xAE)) {
                final double n = this.rand.nextGaussian() * 0.02;
                final double n2 = this.rand.nextGaussian() * 0.02;
                final double n3 = this.rand.nextGaussian() * 0.02;
                final double n4 = 10.0;
                this.worldObj.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width - n * n4, this.posY + this.rand.nextFloat() * this.height - n2 * n4, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width - n3 * n4, n, n2, n3, new int["".length()]);
                ++i;
            }
            "".length();
            if (4 == 1) {
                throw null;
            }
        }
        else {
            this.worldObj.setEntityState(this, (byte)(0x3E ^ 0x2A));
        }
    }
    
    public IEntityLivingData onInitialSpawn(final DifficultyInstance difficultyInstance, final IEntityLivingData entityLivingData) {
        this.getEntityAttribute(SharedMonsterAttributes.followRange).applyModifier(new AttributeModifier(EntityLiving.I[0xB4 ^ 0x90], this.rand.nextGaussian() * 0.05, " ".length()));
        return entityLivingData;
    }
    
    protected void updateAITasks() {
    }
    
    public void setCanPickUpLoot(final boolean canPickUpLoot) {
        this.canPickUpLoot = canPickUpLoot;
    }
    
    @Override
    protected final void updateEntityActionState() {
        this.entityAge += " ".length();
        this.worldObj.theProfiler.startSection(EntityLiving.I[0x9C ^ 0x86]);
        this.despawnEntity();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLiving.I[0x7 ^ 0x1C]);
        this.senses.clearSensingCache();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLiving.I[0x20 ^ 0x3C]);
        this.targetTasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLiving.I[0xDA ^ 0xC7]);
        this.tasks.onUpdateTasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLiving.I[0x44 ^ 0x5A]);
        this.navigator.onUpdateNavigation();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLiving.I[0x55 ^ 0x4A]);
        this.updateAITasks();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.startSection(EntityLiving.I[0x8C ^ 0xAC]);
        this.worldObj.theProfiler.startSection(EntityLiving.I[0x35 ^ 0x14]);
        this.moveHelper.onUpdateMoveHelper();
        this.worldObj.theProfiler.endStartSection(EntityLiving.I[0xE7 ^ 0xC5]);
        this.lookHelper.onUpdateLook();
        this.worldObj.theProfiler.endStartSection(EntityLiving.I[0x48 ^ 0x6B]);
        this.jumpHelper.doJump();
        this.worldObj.theProfiler.endSection();
        this.worldObj.theProfiler.endSection();
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setBoolean(EntityLiving.I[" ".length()], this.canPickUpLoot());
        nbtTagCompound.setBoolean(EntityLiving.I["  ".length()], this.persistenceRequired);
        final NBTTagList list = new NBTTagList();
        int i = "".length();
        "".length();
        if (0 >= 1) {
            throw null;
        }
        while (i < this.equipment.length) {
            final NBTTagCompound nbtTagCompound2 = new NBTTagCompound();
            if (this.equipment[i] != null) {
                this.equipment[i].writeToNBT(nbtTagCompound2);
            }
            list.appendTag(nbtTagCompound2);
            ++i;
        }
        nbtTagCompound.setTag(EntityLiving.I["   ".length()], list);
        final NBTTagList list2 = new NBTTagList();
        int j = "".length();
        "".length();
        if (0 >= 2) {
            throw null;
        }
        while (j < this.equipmentDropChances.length) {
            list2.appendTag(new NBTTagFloat(this.equipmentDropChances[j]));
            ++j;
        }
        nbtTagCompound.setTag(EntityLiving.I[0x52 ^ 0x56], list2);
        nbtTagCompound.setBoolean(EntityLiving.I[0x88 ^ 0x8D], this.isLeashed);
        if (this.leashedToEntity != null) {
            final NBTTagCompound nbtTagCompound3 = new NBTTagCompound();
            if (this.leashedToEntity instanceof EntityLivingBase) {
                nbtTagCompound3.setLong(EntityLiving.I[0x26 ^ 0x20], this.leashedToEntity.getUniqueID().getMostSignificantBits());
                nbtTagCompound3.setLong(EntityLiving.I[0x50 ^ 0x57], this.leashedToEntity.getUniqueID().getLeastSignificantBits());
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else if (this.leashedToEntity instanceof EntityHanging) {
                final BlockPos hangingPosition = ((EntityHanging)this.leashedToEntity).getHangingPosition();
                nbtTagCompound3.setInteger(EntityLiving.I[0x2B ^ 0x23], hangingPosition.getX());
                nbtTagCompound3.setInteger(EntityLiving.I[0x90 ^ 0x99], hangingPosition.getY());
                nbtTagCompound3.setInteger(EntityLiving.I[0x4 ^ 0xE], hangingPosition.getZ());
            }
            nbtTagCompound.setTag(EntityLiving.I[0xB8 ^ 0xB3], nbtTagCompound3);
        }
        if (this.isAIDisabled()) {
            nbtTagCompound.setBoolean(EntityLiving.I[0xAB ^ 0xA7], this.isAIDisabled());
        }
    }
    
    public PathNavigate getNavigator() {
        return this.navigator;
    }
    
    public boolean isNotColliding() {
        if (this.worldObj.checkNoEntityCollision(this.getEntityBoundingBox(), this) && this.worldObj.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && !this.worldObj.isAnyLiquid(this.getEntityBoundingBox())) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void clearLeashed(final boolean b, final boolean b2) {
        if (this.isLeashed) {
            this.isLeashed = ("".length() != 0);
            this.leashedToEntity = null;
            if (!this.worldObj.isRemote && b2) {
                this.dropItem(Items.lead, " ".length());
            }
            if (!this.worldObj.isRemote && b && this.worldObj instanceof WorldServer) {
                ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(" ".length(), this, null));
            }
        }
    }
    
    public void setLeashedToEntity(final Entity leashedToEntity, final boolean b) {
        this.isLeashed = (" ".length() != 0);
        this.leashedToEntity = leashedToEntity;
        if (!this.worldObj.isRemote && b && this.worldObj instanceof WorldServer) {
            ((WorldServer)this.worldObj).getEntityTracker().sendToAllTrackingEntity(this, new S1BPacketEntityAttach(" ".length(), this, this.leashedToEntity));
        }
    }
    
    public EntityMoveHelper getMoveHelper() {
        return this.moveHelper;
    }
    
    public boolean canBeSteered() {
        return "".length() != 0;
    }
    
    @Override
    protected void dropFewItems(final boolean b, final int n) {
        final Item dropItem = this.getDropItem();
        if (dropItem != null) {
            int nextInt = this.rand.nextInt("   ".length());
            if (n > 0) {
                nextInt += this.rand.nextInt(n + " ".length());
            }
            int i = "".length();
            "".length();
            if (2 >= 3) {
                throw null;
            }
            while (i < nextInt) {
                this.dropItem(dropItem, " ".length());
                ++i;
            }
        }
    }
    
    protected void despawnEntity() {
        final Object fieldValue = Reflector.getFieldValue(Reflector.Event_Result_DEFAULT);
        final Object fieldValue2 = Reflector.getFieldValue(Reflector.Event_Result_DENY);
        if (this.persistenceRequired) {
            this.entityAge = "".length();
            "".length();
            if (0 >= 1) {
                throw null;
            }
        }
        else {
            if ((this.entityAge & (0x48 ^ 0x57)) == (0x70 ^ 0x6F)) {
                final ReflectorMethod forgeEventFactory_canEntityDespawn = Reflector.ForgeEventFactory_canEntityDespawn;
                final Object[] array = new Object[" ".length()];
                array["".length()] = this;
                final Object call;
                if ((call = Reflector.call(forgeEventFactory_canEntityDespawn, array)) != fieldValue) {
                    if (call == fieldValue2) {
                        this.entityAge = "".length();
                        "".length();
                        if (0 == 2) {
                            throw null;
                        }
                        return;
                    }
                    else {
                        this.setDead();
                        "".length();
                        if (4 <= 0) {
                            throw null;
                        }
                        return;
                    }
                }
            }
            final EntityPlayer closestPlayerToEntity = this.worldObj.getClosestPlayerToEntity(this, -1.0);
            if (closestPlayerToEntity != null) {
                final double n = closestPlayerToEntity.posX - this.posX;
                final double n2 = closestPlayerToEntity.posY - this.posY;
                final double n3 = closestPlayerToEntity.posZ - this.posZ;
                final double n4 = n * n + n2 * n2 + n3 * n3;
                if (this.canDespawn() && n4 > 16384.0) {
                    this.setDead();
                }
                if (this.entityAge > 276 + 41 + 208 + 75 && this.rand.nextInt(428 + 222 - 421 + 571) == 0 && n4 > 1024.0 && this.canDespawn()) {
                    this.setDead();
                    "".length();
                    if (-1 < -1) {
                        throw null;
                    }
                }
                else if (n4 < 1024.0) {
                    this.entityAge = "".length();
                }
            }
        }
    }
    
    public boolean canAttackClass(final Class clazz) {
        if (clazz != EntityGhast.class) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected void updateLeashedState() {
        if (this.leashNBTTag != null) {
            this.recreateLeash();
        }
        if (this.isLeashed) {
            if (!this.isEntityAlive()) {
                this.clearLeashed(" ".length() != 0, " ".length() != 0);
            }
            if (this.leashedToEntity == null || this.leashedToEntity.isDead) {
                this.clearLeashed(" ".length() != 0, " ".length() != 0);
            }
        }
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.equipment["".length()];
    }
    
    public boolean isNoDespawnRequired() {
        return this.persistenceRequired;
    }
    
    public static int getArmorPosition(final ItemStack itemStack) {
        if (itemStack.getItem() != Item.getItemFromBlock(Blocks.pumpkin) && itemStack.getItem() != Items.skull) {
            if (itemStack.getItem() instanceof ItemArmor) {
                switch (((ItemArmor)itemStack.getItem()).armorType) {
                    case 0: {
                        return 0x1A ^ 0x1E;
                    }
                    case 1: {
                        return "   ".length();
                    }
                    case 2: {
                        return "  ".length();
                    }
                    case 3: {
                        return " ".length();
                    }
                }
            }
            return "".length();
        }
        return 0x50 ^ 0x54;
    }
    
    public int getVerticalFaceSpeed() {
        return 0x3D ^ 0x15;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x1D ^ 0x12, (byte)"".length());
    }
    
    public int getTalkInterval() {
        return 0x6 ^ 0x56;
    }
    
    public void setNoAI(final boolean b) {
        final DataWatcher dataWatcher = this.dataWatcher;
        final int n = 0xCF ^ 0xC0;
        int n2;
        if (b) {
            n2 = " ".length();
            "".length();
            if (3 < 0) {
                throw null;
            }
        }
        else {
            n2 = "".length();
        }
        dataWatcher.updateObject(n, (byte)n2);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.followRange).setBaseValue(16.0);
    }
    
    public EntityLookHelper getLookHelper() {
        return this.lookHelper;
    }
    
    private static void I() {
        (I = new String[0xA9 ^ 0x99])["".length()] = I(",\u0000 \u000672\n\u0016-5*", "AoBDV");
        EntityLiving.I[" ".length()] = I("\u001a\u001b#\u0000\n:\u0011\u0018 /6\u00159", "YzMPc");
        EntityLiving.I["  ".length()] = I("2\u00131 %\u0011\u0002&=/\u0007$&\"9\u000b\u0004&7", "bvCSL");
        EntityLiving.I["   ".length()] = I("3\u001c2\u001e(\u001b\b)\u0003", "vmGwX");
        EntityLiving.I[0x2B ^ 0x2F] = I("-8<$\u000f\u0001+=7)\u001a", "iJSTL");
        EntityLiving.I[0xC7 ^ 0xC2] = I("+)3\u0011\u001b\u0002(", "gLRbs");
        EntityLiving.I[0x27 ^ 0x21] = I("1\"\u001b+\n\u000b\u0004&", "dwRoG");
        EntityLiving.I[0x3A ^ 0x3D] = I("\u0011\u0016\u001b>&!\"!\u000e", "DCRzj");
        EntityLiving.I[0x49 ^ 0x41] = I("\u000f", "WVdyN");
        EntityLiving.I[0xBC ^ 0xB5] = I("\u0011", "HFCUY");
        EntityLiving.I[0x84 ^ 0x8E] = I("\u0015", "OZLDk");
        EntityLiving.I[0x6 ^ 0xD] = I("\u001d\u0000\u0012&\u001b", "QesUs");
        EntityLiving.I[0x7C ^ 0x70] = I("6\u000e7+", "xavbR");
        EntityLiving.I[0x5E ^ 0x53] = I(".+\u001e8\u001e\u000e!%\u0018;\u0002%\u0004", "mJphw");
        EntityLiving.I[0x99 ^ 0x97] = I(",\u0019=8\u0018\f\u0013\u0006\u0018=\u0000\u0017'", "oxShq");
        EntityLiving.I[0x22 ^ 0x2D] = I("\u0016\u0003\u0019*\u00055\u0012\u000e7\u000f#4\u000e(\u0019/\u0014\u000e=", "FfkYl");
        EntityLiving.I[0xA ^ 0x1A] = I("&!\u0013\u0019\u0005\u000e5\b\u0004", "cPfpu");
        EntityLiving.I[0x8E ^ 0x9F] = I("!;\u0002\u000e\u001c\t/\u0019\u0013", "dJwgl");
        EntityLiving.I[0x3B ^ 0x29] = I("\u0006<9?\u0005*/8,#1", "BNVOF");
        EntityLiving.I[0xD6 ^ 0xC5] = I("\u0011\u0007(7:=\u0014)$\u001c&", "UuGGy");
        EntityLiving.I[0x66 ^ 0x72] = I("\u0014*\f$+=+", "XOmWC");
        EntityLiving.I[0x21 ^ 0x34] = I("\b'\u00199\u0003", "DBxJk");
        EntityLiving.I[0x26 ^ 0x30] = I("\u000e76\u001f.", "BRWlF");
        EntityLiving.I[0x24 ^ 0x33] = I("\u000067\u0010", "NYvYx");
        EntityLiving.I[0x7D ^ 0x65] = I("\u0007\u001a.=\u0018\u0005\u0012", "kuAIq");
        EntityLiving.I[0x80 ^ 0x99] = I("\u0003\u001e\u00152>\u0007\u0014\u0011\u001c\"\t", "nqwuL");
        EntityLiving.I[0x4E ^ 0x54] = I("\u000b?=(.,2+;$\u001f9", "hWXKE");
        EntityLiving.I[0x6B ^ 0x70] = I("\u0001,(* \u001c.", "rIFYI");
        EntityLiving.I[0x86 ^ 0x9A] = I("\u0011\u001b\b\u0002\u0000\u0011)\u001f\t\u0000\u0006\u000e\u0015\u0017", "ezzee");
        EntityLiving.I[0x31 ^ 0x2C] = I("\u0006;2 \u0014\u000486/3\u000e&", "aTSLG");
        EntityLiving.I[0xDB ^ 0xC5] = I("\u001b1\f,\u0014\u0014$\u0013*\u001d", "uPzEs");
        EntityLiving.I[0x2F ^ 0x30] = I("\u001c\u001c8J\u0012\u0018\u00101", "qsZjf");
        EntityLiving.I[0x43 ^ 0x63] = I(",\u001e\u0018\"= \u001d\u0005", "OqvVO");
        EntityLiving.I[0xA2 ^ 0x83] = I("\u000e+/\u0010", "cDYuB");
        EntityLiving.I[0x96 ^ 0xB4] = I("\u0001\f(-", "mcGFH");
        EntityLiving.I[0x50 ^ 0x73] = I("\u0004\u001f\u000e6", "njcFs");
        EntityLiving.I[0x69 ^ 0x4D] = I("74*\u001e,\bu7\n\"\u0012;d\u0018,\u000b 7", "eUDzC");
        EntityLiving.I[0x48 ^ 0x6D] = I("\u0001\u001f\u0013*:;9.", "TJZnw");
        EntityLiving.I[0xE1 ^ 0xC7] = I(";\f?\u0014\b\u000b8\u0005$", "nYvPD");
        EntityLiving.I[0xE ^ 0x29] = I("33\f\u000f\b\t\u00151", "ffEKE");
        EntityLiving.I[0xB8 ^ 0x90] = I("\u000f&>3\u000f?\u0012\u0004\u0003", "ZswwC");
        EntityLiving.I[0x4E ^ 0x67] = I("2", "jlxDh");
        EntityLiving.I[0x53 ^ 0x79] = I("\u0001", "Xbewj");
        EntityLiving.I[0x19 ^ 0x32] = I("\u001f", "EXPAm");
        EntityLiving.I[0x37 ^ 0x1B] = I("\u0001", "YdPrn");
        EntityLiving.I[0x66 ^ 0x4B] = I("\n", "SQpiX");
        EntityLiving.I[0x4A ^ 0x64] = I("3", "iYdqo");
        EntityLiving.I[0x85 ^ 0xAA] = I(")\u001a\u0012TFZf|QCZ", "jVMdv");
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (this.noClip) {
            return "".length() != 0;
        }
        final BlockPosM blockPosM = new BlockPosM("".length(), "".length(), "".length());
        int i = "".length();
        "".length();
        if (2 >= 4) {
            throw null;
        }
        while (i < (0xBB ^ 0xB3)) {
            blockPosM.setXyz(this.posX + ((i >> "".length()) % "  ".length() - 0.5f) * this.width * 0.8f, this.posY + ((i >> " ".length()) % "  ".length() - 0.5f) * 0.1f + this.getEyeHeight(), this.posZ + ((i >> "  ".length()) % "  ".length() - 0.5f) * this.width * 0.8f);
            if (this.worldObj.getBlockState(blockPosM).getBlock().isVisuallyOpaque()) {
                return " ".length() != 0;
            }
            ++i;
        }
        return "".length() != 0;
    }
    
    public boolean allowLeashing() {
        if (!this.getLeashed() && !(this instanceof IMob)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected boolean func_175448_a(final ItemStack itemStack) {
        return " ".length() != 0;
    }
    
    public void setAttackTarget(final EntityLivingBase attackTarget) {
        this.attackTarget = attackTarget;
        final ReflectorMethod forgeHooks_onLivingSetAttackTarget = Reflector.ForgeHooks_onLivingSetAttackTarget;
        final Object[] array = new Object["  ".length()];
        array["".length()] = this;
        array[" ".length()] = attackTarget;
        Reflector.callVoid(forgeHooks_onLivingSetAttackTarget, array);
    }
    
    protected Item getDropItem() {
        return null;
    }
    
    public void playLivingSound() {
        final String livingSound = this.getLivingSound();
        if (livingSound != null) {
            this.playSound(livingSound, this.getSoundVolume(), this.getSoundPitch());
        }
    }
    
    @Override
    public void onUpdate() {
        if (Config.isSmoothWorld() && this.canSkipUpdate()) {
            this.onUpdateMinimal();
            "".length();
            if (2 == 4) {
                throw null;
            }
        }
        else {
            super.onUpdate();
            if (!this.worldObj.isRemote) {
                this.updateLeashedState();
            }
        }
    }
    
    protected PathNavigate getNewNavigator(final World world) {
        return new PathNavigateGround(this, world);
    }
    
    private boolean canSkipUpdate() {
        if (this.isChild()) {
            return "".length() != 0;
        }
        if (this.hurtTime > 0) {
            return "".length() != 0;
        }
        if (this.ticksExisted < (0x37 ^ 0x23)) {
            return "".length() != 0;
        }
        final World entityWorld = this.getEntityWorld();
        if (entityWorld == null) {
            return "".length() != 0;
        }
        if (entityWorld.playerEntities.size() != " ".length()) {
            return "".length() != 0;
        }
        final EntityPlayer entityPlayer = entityWorld.playerEntities.get("".length());
        final double n = Math.abs(this.posX - entityPlayer.posX) - 16.0;
        final double n2 = Math.abs(this.posZ - entityPlayer.posZ) - 16.0;
        int n3;
        if (this.isInRangeToRenderDist(n * n + n2 * n2)) {
            n3 = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else {
            n3 = " ".length();
        }
        return n3 != 0;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(EntityLiving.I[0x94 ^ 0x99], " ".length())) {
            this.setCanPickUpLoot(nbtTagCompound.getBoolean(EntityLiving.I[0x8F ^ 0x81]));
        }
        this.persistenceRequired = nbtTagCompound.getBoolean(EntityLiving.I[0x7F ^ 0x70]);
        if (nbtTagCompound.hasKey(EntityLiving.I[0xA3 ^ 0xB3], 0x81 ^ 0x88)) {
            final NBTTagList tagList = nbtTagCompound.getTagList(EntityLiving.I[0x75 ^ 0x64], 0x4B ^ 0x41);
            int i = "".length();
            "".length();
            if (2 == 1) {
                throw null;
            }
            while (i < this.equipment.length) {
                this.equipment[i] = ItemStack.loadItemStackFromNBT(tagList.getCompoundTagAt(i));
                ++i;
            }
        }
        if (nbtTagCompound.hasKey(EntityLiving.I[0xBE ^ 0xAC], 0xF ^ 0x6)) {
            final NBTTagList tagList2 = nbtTagCompound.getTagList(EntityLiving.I[0x61 ^ 0x72], 0x4E ^ 0x4B);
            int j = "".length();
            "".length();
            if (false) {
                throw null;
            }
            while (j < tagList2.tagCount()) {
                this.equipmentDropChances[j] = tagList2.getFloatAt(j);
                ++j;
            }
        }
        this.isLeashed = nbtTagCompound.getBoolean(EntityLiving.I[0x3A ^ 0x2E]);
        if (this.isLeashed && nbtTagCompound.hasKey(EntityLiving.I[0x50 ^ 0x45], 0xBA ^ 0xB0)) {
            this.leashNBTTag = nbtTagCompound.getCompoundTag(EntityLiving.I[0x36 ^ 0x20]);
        }
        this.setNoAI(nbtTagCompound.getBoolean(EntityLiving.I[0xBD ^ 0xAA]));
    }
    
    public EntityLivingBase getAttackTarget() {
        return this.attackTarget;
    }
    
    public boolean getCanSpawnHere() {
        return " ".length() != 0;
    }
    
    public boolean canPickUpLoot() {
        return this.canPickUpLoot;
    }
    
    @Override
    protected void dropEquipment(final boolean b, final int n) {
        int i = "".length();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (i < this.getInventory().length) {
            final ItemStack equipmentInSlot = this.getEquipmentInSlot(i);
            int n2;
            if (this.equipmentDropChances[i] > 1.0f) {
                n2 = " ".length();
                "".length();
                if (4 < 4) {
                    throw null;
                }
            }
            else {
                n2 = "".length();
            }
            final int n3 = n2;
            if (equipmentInSlot != null && (b || n3 != 0) && this.rand.nextFloat() - n * 0.01f < this.equipmentDropChances[i]) {
                if (n3 == 0 && equipmentInSlot.isItemStackDamageable()) {
                    final int max = Math.max(equipmentInSlot.getMaxDamage() - (0xAA ^ 0xB3), " ".length());
                    int length = equipmentInSlot.getMaxDamage() - this.rand.nextInt(this.rand.nextInt(max) + " ".length());
                    if (length > max) {
                        length = max;
                    }
                    if (length < " ".length()) {
                        length = " ".length();
                    }
                    equipmentInSlot.setItemDamage(length);
                }
                this.entityDropItem(equipmentInSlot, 0.0f);
            }
            ++i;
        }
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int n) {
        return this.equipment[n];
    }
    
    public boolean isAIDisabled() {
        if (this.dataWatcher.getWatchableObjectByte(0x5E ^ 0x51) != 0) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    protected String getLivingSound() {
        return null;
    }
    
    public float getRenderSizeModifier() {
        return 1.0f;
    }
    
    public void eatGrassBonus() {
    }
    
    public boolean getLeashed() {
        return this.isLeashed;
    }
    
    public void setEquipmentDropChance(final int n, final float n2) {
        this.equipmentDropChances[n] = n2;
    }
    
    private void onUpdateMinimal() {
        this.entityAge += " ".length();
        if (this instanceof EntityMob && this.getBrightness(1.0f) > 0.5f) {
            this.entityAge += "  ".length();
        }
        this.despawnEntity();
    }
    
    public void setMoveForward(final float moveForward) {
        this.moveForward = moveForward;
    }
    
    @Override
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        int length;
        if (n == (0x53 ^ 0x30)) {
            length = "".length();
            "".length();
            if (-1 >= 2) {
                throw null;
            }
        }
        else {
            length = n - (0xA3 ^ 0xC7) + " ".length();
            if (length < 0 || length >= this.equipment.length) {
                return "".length() != 0;
            }
        }
        if (itemStack == null || getArmorPosition(itemStack) == length || (length == (0xB0 ^ 0xB4) && itemStack.getItem() instanceof ItemBlock)) {
            this.setCurrentItemOrArmor(length, itemStack);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public EntityLiving(final World world) {
        super(world);
        this.equipment = new ItemStack[0x1A ^ 0x1F];
        this.equipmentDropChances = new float[0x3 ^ 0x6];
        this.randomMobsId = "".length();
        this.spawnBiome = null;
        this.spawnPosition = null;
        Profiler theProfiler;
        if (world != null && world.theProfiler != null) {
            theProfiler = world.theProfiler;
            "".length();
            if (3 <= 1) {
                throw null;
            }
        }
        else {
            theProfiler = null;
        }
        this.tasks = new EntityAITasks(theProfiler);
        Profiler theProfiler2;
        if (world != null && world.theProfiler != null) {
            theProfiler2 = world.theProfiler;
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            theProfiler2 = null;
        }
        this.targetTasks = new EntityAITasks(theProfiler2);
        this.lookHelper = new EntityLookHelper(this);
        this.moveHelper = new EntityMoveHelper(this);
        this.jumpHelper = new EntityJumpHelper(this);
        this.bodyHelper = new EntityBodyHelper(this);
        this.navigator = this.getNewNavigator(world);
        this.senses = new EntitySenses(this);
        int i = "".length();
        "".length();
        if (2 == -1) {
            throw null;
        }
        while (i < this.equipmentDropChances.length) {
            this.equipmentDropChances[i] = 0.085f;
            ++i;
        }
        this.randomMobsId = (int)(this.getUniqueID().getLeastSignificantBits() & 0x7FFFFFFFL);
    }
    
    public static Item getArmorItemForSlot(final int n, final int n2) {
        switch (n) {
            case 4: {
                if (n2 == 0) {
                    return Items.leather_helmet;
                }
                if (n2 == " ".length()) {
                    return Items.golden_helmet;
                }
                if (n2 == "  ".length()) {
                    return Items.chainmail_helmet;
                }
                if (n2 == "   ".length()) {
                    return Items.iron_helmet;
                }
                if (n2 == (0x6C ^ 0x68)) {
                    return Items.diamond_helmet;
                }
            }
            case 3: {
                if (n2 == 0) {
                    return Items.leather_chestplate;
                }
                if (n2 == " ".length()) {
                    return Items.golden_chestplate;
                }
                if (n2 == "  ".length()) {
                    return Items.chainmail_chestplate;
                }
                if (n2 == "   ".length()) {
                    return Items.iron_chestplate;
                }
                if (n2 == (0x43 ^ 0x47)) {
                    return Items.diamond_chestplate;
                }
            }
            case 2: {
                if (n2 == 0) {
                    return Items.leather_leggings;
                }
                if (n2 == " ".length()) {
                    return Items.golden_leggings;
                }
                if (n2 == "  ".length()) {
                    return Items.chainmail_leggings;
                }
                if (n2 == "   ".length()) {
                    return Items.iron_leggings;
                }
                if (n2 == (0x6 ^ 0x2)) {
                    return Items.diamond_leggings;
                }
            }
            case 1: {
                if (n2 == 0) {
                    return Items.leather_boots;
                }
                if (n2 == " ".length()) {
                    return Items.golden_boots;
                }
                if (n2 == "  ".length()) {
                    return Items.chainmail_boots;
                }
                if (n2 == "   ".length()) {
                    return Items.iron_boots;
                }
                if (n2 == (0x27 ^ 0x23)) {
                    return Items.diamond_boots;
                }
                break;
            }
        }
        return null;
    }
    
    @Override
    public ItemStack[] getInventory() {
        return this.equipment;
    }
    
    public void enablePersistence() {
        this.persistenceRequired = (" ".length() != 0);
    }
    
    public enum SpawnPlacementType
    {
        IN_WATER(SpawnPlacementType.I[0x70 ^ 0x75], "  ".length(), SpawnPlacementType.I[0x1 ^ 0x7], "  ".length());
        
        private static final String __OBFID;
        private static final SpawnPlacementType[] $VALUES;
        
        IN_AIR(SpawnPlacementType.I["   ".length()], " ".length(), SpawnPlacementType.I[0x94 ^ 0x90], " ".length());
        
        private static final SpawnPlacementType[] ENUM$VALUES;
        
        ON_GROUND(SpawnPlacementType.I[" ".length()], "".length(), SpawnPlacementType.I["  ".length()], "".length());
        
        private static final String[] I;
        
        private SpawnPlacementType(final String s, final int n, final String s2, final int n2) {
        }
        
        private static void I() {
            (I = new String[0x12 ^ 0x15])["".length()] = I("!\u0002\u0013[{R~~Y~W", "bNLkK");
            SpawnPlacementType.I[" ".length()] = I(") >\u0001();/\u0002", "fnaFz");
            SpawnPlacementType.I["  ".length()] = I("\t\u001d\u0018\u001d\u0006\t\u0006\t\u001e", "FSGZT");
            SpawnPlacementType.I["   ".length()] = I("3\u0007=2\u0006(", "zIbsO");
            SpawnPlacementType.I[0xB0 ^ 0xB4] = I("#\n\u0010\"\"8", "jDOck");
            SpawnPlacementType.I[0x30 ^ 0x35] = I("\u001d\n(\u0011*\u0000\u0001%", "TDwFk");
            SpawnPlacementType.I[0xA8 ^ 0xAE] = I("=<9-2 74", "trfzs");
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
                if (4 != 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        static {
            I();
            __OBFID = SpawnPlacementType.I["".length()];
            final SpawnPlacementType[] enum$VALUES = new SpawnPlacementType["   ".length()];
            enum$VALUES["".length()] = SpawnPlacementType.ON_GROUND;
            enum$VALUES[" ".length()] = SpawnPlacementType.IN_AIR;
            enum$VALUES["  ".length()] = SpawnPlacementType.IN_WATER;
            ENUM$VALUES = enum$VALUES;
            final SpawnPlacementType[] $values = new SpawnPlacementType["   ".length()];
            $values["".length()] = SpawnPlacementType.ON_GROUND;
            $values[" ".length()] = SpawnPlacementType.IN_AIR;
            $values["  ".length()] = SpawnPlacementType.IN_WATER;
            $VALUES = $values;
        }
    }
}
