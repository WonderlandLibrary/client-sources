package net.minecraft.entity.player;

import com.mojang.authlib.*;
import net.minecraft.inventory.*;
import net.minecraft.command.server.*;
import net.minecraft.nbt.*;
import com.google.common.base.*;
import net.minecraft.stats.*;
import net.minecraft.block.properties.*;
import net.minecraft.block.*;
import net.minecraft.enchantment.*;
import net.minecraft.potion.*;
import net.minecraft.block.material.*;
import net.minecraft.init.*;
import net.minecraft.item.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.state.*;
import net.minecraft.event.*;
import net.minecraft.entity.ai.attributes.*;
import net.minecraft.util.*;
import net.minecraft.scoreboard.*;
import java.util.*;
import net.minecraft.world.*;
import net.minecraft.server.*;
import com.google.common.collect.*;
import net.minecraft.tileentity.*;
import net.minecraft.network.play.server.*;
import net.minecraft.network.*;
import net.minecraft.entity.boss.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.item.*;

public abstract class EntityPlayer extends EntityLivingBase
{
    private InventoryEnderChest theInventoryEnderChest;
    private BlockPos startMinecartRidingCoordinate;
    public Container inventoryContainer;
    public float prevCameraYaw;
    public double prevChasingPosX;
    protected float speedInAir;
    protected boolean sleeping;
    public float renderOffsetX;
    public double prevChasingPosZ;
    public double chasingPosX;
    private boolean spawnForced;
    public InventoryPlayer inventory;
    private static final String[] I;
    public float cameraYaw;
    public float renderOffsetY;
    protected int flyToggleTimer;
    private ItemStack itemInUse;
    public double chasingPosZ;
    public PlayerCapabilities capabilities;
    private int xpSeed;
    public double chasingPosY;
    public double prevChasingPosY;
    private int sleepTimer;
    public float renderOffsetZ;
    private final GameProfile gameProfile;
    public int xpCooldown;
    public int experienceTotal;
    protected float speedOnGround;
    public Container openContainer;
    private BlockPos spawnChunk;
    private boolean hasReducedDebug;
    public EntityFishHook fishEntity;
    private int itemInUseCount;
    public BlockPos playerLocation;
    private static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing;
    private int lastXPSound;
    public int experienceLevel;
    public float experience;
    protected FoodStats foodStats;
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(0x15 ^ 0x5, (byte)"".length());
        this.dataWatcher.addObject(0xE ^ 0x1F, 0.0f);
        this.dataWatcher.addObject(0x87 ^ 0x95, "".length());
        this.dataWatcher.addObject(0x5D ^ 0x57, (byte)"".length());
    }
    
    public void displayGui(final IInteractionObject interactionObject) {
    }
    
    public void destroyCurrentEquippedItem() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }
    
    public void addExhaustion(final float n) {
        if (!this.capabilities.disableDamage && !this.worldObj.isRemote) {
            this.foodStats.addExhaustion(n);
        }
    }
    
    public EntityPlayer(final World world, final GameProfile gameProfile) {
        super(world);
        this.inventory = new InventoryPlayer(this);
        this.theInventoryEnderChest = new InventoryEnderChest();
        this.foodStats = new FoodStats();
        this.capabilities = new PlayerCapabilities();
        this.speedOnGround = 0.1f;
        this.speedInAir = 0.02f;
        this.hasReducedDebug = ("".length() != 0);
        this.entityUniqueID = getUUID(gameProfile);
        this.gameProfile = gameProfile;
        final InventoryPlayer inventory = this.inventory;
        int n;
        if (world.isRemote) {
            n = "".length();
            "".length();
            if (false) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        this.inventoryContainer = new ContainerPlayer(inventory, n != 0, this);
        this.openContainer = this.inventoryContainer;
        final BlockPos spawnPoint = world.getSpawnPoint();
        this.setLocationAndAngles(spawnPoint.getX() + 0.5, spawnPoint.getY() + " ".length(), spawnPoint.getZ() + 0.5, 0.0f, 0.0f);
        this.field_70741_aB = 180.0f;
        this.fireResistance = (0x4A ^ 0x5E);
    }
    
    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.attackDamage).setBaseValue(1.0);
        this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.10000000149011612);
    }
    
    @Override
    public Team getTeam() {
        return this.getWorldScoreboard().getPlayersTeam(this.getName());
    }
    
    @Override
    public double getYOffset() {
        return -0.35;
    }
    
    public void displayGUIBook(final ItemStack itemStack) {
    }
    
    public ItemStack getItemInUse() {
        return this.itemInUse;
    }
    
    @Override
    public String getName() {
        return this.gameProfile.getName();
    }
    
    public void setSpawnPoint(final BlockPos spawnChunk, final boolean spawnForced) {
        if (spawnChunk != null) {
            this.spawnChunk = spawnChunk;
            this.spawnForced = spawnForced;
            "".length();
            if (3 < 3) {
                throw null;
            }
        }
        else {
            this.spawnChunk = null;
            this.spawnForced = ("".length() != 0);
        }
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        if (!this.sleeping && super.isEntityInsideOpaqueBlock()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isWearing(final EnumPlayerModelParts enumPlayerModelParts) {
        if ((this.getDataWatcher().getWatchableObjectByte(0x5A ^ 0x50) & enumPlayerModelParts.getPartMask()) == enumPlayerModelParts.getPartMask()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    private boolean isInBed() {
        if (this.worldObj.getBlockState(this.playerLocation).getBlock() == Blocks.bed) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public float getArmorVisibility() {
        int length = "".length();
        final ItemStack[] armorInventory;
        final int length2 = (armorInventory = this.inventory.armorInventory).length;
        int i = "".length();
        "".length();
        if (-1 >= 2) {
            throw null;
        }
        while (i < length2) {
            if (armorInventory[i] != null) {
                ++length;
            }
            ++i;
        }
        return length / this.inventory.armorInventory.length;
    }
    
    static {
        I();
    }
    
    @Override
    protected void updateEntityActionState() {
        super.updateEntityActionState();
        this.updateArmSwingProgress();
        this.rotationYawHead = this.rotationYaw;
    }
    
    public EntityItem dropItem(final ItemStack itemStack, final boolean b, final boolean b2) {
        if (itemStack == null) {
            return null;
        }
        if (itemStack.stackSize == 0) {
            return null;
        }
        final EntityItem entityItem = new EntityItem(this.worldObj, this.posX, this.posY - 0.30000001192092896 + this.getEyeHeight(), this.posZ, itemStack);
        entityItem.setPickupDelay(0x88 ^ 0xA0);
        if (b2) {
            entityItem.setThrower(this.getName());
        }
        if (b) {
            final float n = this.rand.nextFloat() * 0.5f;
            final float n2 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            entityItem.motionX = -MathHelper.sin(n2) * n;
            entityItem.motionZ = MathHelper.cos(n2) * n;
            entityItem.motionY = 0.20000000298023224;
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        else {
            final float n3 = 0.3f;
            entityItem.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n3;
            entityItem.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * n3;
            entityItem.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * n3 + 0.1f;
            final float n4 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            final float n5 = 0.02f * this.rand.nextFloat();
            final EntityItem entityItem2 = entityItem;
            entityItem2.motionX += Math.cos(n4) * n5;
            final EntityItem entityItem3 = entityItem;
            entityItem3.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            final EntityItem entityItem4 = entityItem;
            entityItem4.motionZ += Math.sin(n4) * n5;
        }
        this.joinEntityItemWithWorld(entityItem);
        if (b2) {
            this.triggerAchievement(StatList.dropStat);
        }
        return entityItem;
    }
    
    public void jump() {
        super.jump();
        this.triggerAchievement(StatList.jumpStat);
        if (this.isSprinting()) {
            this.addExhaustion(0.8f);
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            this.addExhaustion(0.2f);
        }
    }
    
    public BlockPos getBedLocation() {
        return this.spawnChunk;
    }
    
    public void displayGUIHorse(final EntityHorse entityHorse, final IInventory inventory) {
    }
    
    public void openEditCommandBlock(final CommandBlockLogic commandBlockLogic) {
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound nbtTagCompound) {
        super.writeEntityToNBT(nbtTagCompound);
        nbtTagCompound.setTag(EntityPlayer.I[0x6C ^ 0x77], this.inventory.writeToNBT(new NBTTagList()));
        nbtTagCompound.setInteger(EntityPlayer.I[0x99 ^ 0x85], this.inventory.currentItem);
        nbtTagCompound.setBoolean(EntityPlayer.I[0x3B ^ 0x26], this.sleeping);
        nbtTagCompound.setShort(EntityPlayer.I[0x9C ^ 0x82], (short)this.sleepTimer);
        nbtTagCompound.setFloat(EntityPlayer.I[0xB4 ^ 0xAB], this.experience);
        nbtTagCompound.setInteger(EntityPlayer.I[0x8 ^ 0x28], this.experienceLevel);
        nbtTagCompound.setInteger(EntityPlayer.I[0x14 ^ 0x35], this.experienceTotal);
        nbtTagCompound.setInteger(EntityPlayer.I[0x3E ^ 0x1C], this.xpSeed);
        nbtTagCompound.setInteger(EntityPlayer.I[0xBB ^ 0x98], this.getScore());
        if (this.spawnChunk != null) {
            nbtTagCompound.setInteger(EntityPlayer.I[0x69 ^ 0x4D], this.spawnChunk.getX());
            nbtTagCompound.setInteger(EntityPlayer.I[0x4F ^ 0x6A], this.spawnChunk.getY());
            nbtTagCompound.setInteger(EntityPlayer.I[0x8D ^ 0xAB], this.spawnChunk.getZ());
            nbtTagCompound.setBoolean(EntityPlayer.I[0x2 ^ 0x25], this.spawnForced);
        }
        this.foodStats.writeNBT(nbtTagCompound);
        this.capabilities.writeCapabilitiesToNBT(nbtTagCompound);
        nbtTagCompound.setTag(EntityPlayer.I[0x5B ^ 0x73], this.theInventoryEnderChest.saveInventoryToNBT());
        final ItemStack currentItem = this.inventory.getCurrentItem();
        if (currentItem != null && currentItem.getItem() != null) {
            nbtTagCompound.setTag(EntityPlayer.I[0x9C ^ 0xB5], currentItem.writeToNBT(new NBTTagCompound()));
        }
    }
    
    public EntityItem dropOneItem(final boolean b) {
        final InventoryPlayer inventory = this.inventory;
        final int currentItem = this.inventory.currentItem;
        int n;
        if (b && this.inventory.getCurrentItem() != null) {
            n = this.inventory.getCurrentItem().stackSize;
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return this.dropItem(inventory.decrStackSize(currentItem, n), "".length() != 0, " ".length() != 0);
    }
    
    public static UUID getOfflineUUID(final String s) {
        return UUID.nameUUIDFromBytes((EntityPlayer.I[0x67 ^ 0x56] + s).getBytes(Charsets.UTF_8));
    }
    
    public void addExperienceLevel(final int n) {
        this.experienceLevel += n;
        if (this.experienceLevel < 0) {
            this.experienceLevel = "".length();
            this.experience = 0.0f;
            this.experienceTotal = "".length();
        }
        if (n > 0 && this.experienceLevel % (0x24 ^ 0x21) == 0 && this.lastXPSound < this.ticksExisted - 100.0f) {
            float n2;
            if (this.experienceLevel > (0xB6 ^ 0xA8)) {
                n2 = 1.0f;
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
            else {
                n2 = this.experienceLevel / 30.0f;
            }
            this.worldObj.playSoundAtEntity(this, EntityPlayer.I[0x74 ^ 0x58], n2 * 0.75f, 1.0f);
            this.lastXPSound = this.ticksExisted;
        }
    }
    
    @Override
    public void onKillEntity(final EntityLivingBase entityLivingBase) {
        if (entityLivingBase instanceof IMob) {
            this.triggerAchievement(AchievementList.killEnemy);
        }
        final EntityList.EntityEggInfo entityEggInfo = EntityList.entityEggs.get(EntityList.getEntityID(entityLivingBase));
        if (entityEggInfo != null) {
            this.triggerAchievement(entityEggInfo.field_151512_d);
        }
    }
    
    @Override
    protected void resetHeight() {
        if (!this.isSpectator()) {
            super.resetHeight();
        }
    }
    
    @Override
    public ItemStack[] getInventory() {
        return this.inventory.armorInventory;
    }
    
    public float getBedOrientationInDegrees() {
        if (this.playerLocation != null) {
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[this.worldObj.getBlockState(this.playerLocation).getValue((IProperty<EnumFacing>)BlockDirectional.FACING).ordinal()]) {
                case 4: {
                    return 90.0f;
                }
                case 3: {
                    return 270.0f;
                }
                case 5: {
                    return 0.0f;
                }
                case 6: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }
    
    @Override
    public void setDead() {
        super.setDead();
        this.inventoryContainer.onContainerClosed(this);
        if (this.openContainer != null) {
            this.openContainer.onContainerClosed(this);
        }
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
            if (2 != 2) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    @Override
    public boolean isPushedByWater() {
        int n;
        if (this.capabilities.isFlying) {
            n = "".length();
            "".length();
            if (2 >= 4) {
                throw null;
            }
        }
        else {
            n = " ".length();
        }
        return n != 0;
    }
    
    public void onEnchantmentCritical(final Entity entity) {
    }
    
    @Override
    public void playSound(final String s, final float n, final float n2) {
        this.worldObj.playSoundToNearExcept(this, s, n, n2);
    }
    
    public void respawnPlayer() {
    }
    
    public static BlockPos getBedSpawnLocation(final World world, final BlockPos blockPos, final boolean b) {
        final Block block = world.getBlockState(blockPos).getBlock();
        if (block == Blocks.bed) {
            return BlockBed.getSafeExitLocation(world, blockPos, "".length());
        }
        if (!b) {
            return null;
        }
        final boolean func_181623_g = block.func_181623_g();
        final boolean func_181623_g2 = world.getBlockState(blockPos.up()).getBlock().func_181623_g();
        BlockPos blockPos2;
        if (func_181623_g && func_181623_g2) {
            blockPos2 = blockPos;
            "".length();
            if (-1 == 1) {
                throw null;
            }
        }
        else {
            blockPos2 = null;
        }
        return blockPos2;
    }
    
    @Override
    public boolean getAlwaysRenderNameTagForRender() {
        return " ".length() != 0;
    }
    
    public boolean canPlayerEdit(final BlockPos blockPos, final EnumFacing enumFacing, final ItemStack itemStack) {
        if (this.capabilities.allowEdit) {
            return " ".length() != 0;
        }
        if (itemStack == null) {
            return "".length() != 0;
        }
        if (!itemStack.canPlaceOn(this.worldObj.getBlockState(blockPos.offset(enumFacing.getOpposite())).getBlock()) && !itemStack.canEditBlocks()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public float getToolDigEfficiency(final Block block) {
        float strVsBlock = this.inventory.getStrVsBlock(block);
        if (strVsBlock > 1.0f) {
            final int efficiencyModifier = EnchantmentHelper.getEfficiencyModifier(this);
            final ItemStack currentItem = this.inventory.getCurrentItem();
            if (efficiencyModifier > 0 && currentItem != null) {
                strVsBlock += efficiencyModifier * efficiencyModifier + " ".length();
            }
        }
        if (this.isPotionActive(Potion.digSpeed)) {
            strVsBlock *= 1.0f + (this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + " ".length()) * 0.2f;
        }
        if (this.isPotionActive(Potion.digSlowdown)) {
            float n = 0.0f;
            switch (this.getActivePotionEffect(Potion.digSlowdown).getAmplifier()) {
                case 0: {
                    n = 0.3f;
                    "".length();
                    if (false) {
                        throw null;
                    }
                    break;
                }
                case 1: {
                    n = 0.09f;
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                    break;
                }
                case 2: {
                    n = 0.0027f;
                    "".length();
                    if (1 <= 0) {
                        throw null;
                    }
                    break;
                }
                default: {
                    n = 8.1E-4f;
                    break;
                }
            }
            strVsBlock *= n;
        }
        if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
            strVsBlock /= 5.0f;
        }
        if (!this.onGround) {
            strVsBlock /= 5.0f;
        }
        return strVsBlock;
    }
    
    public EntityItem dropPlayerItemWithRandomChoice(final ItemStack itemStack, final boolean b) {
        return this.dropItem(itemStack, "".length() != 0, "".length() != 0);
    }
    
    public void addMovementStat(final double n, final double n2, final double n3) {
        if (this.ridingEntity == null) {
            if (this.isInsideOfMaterial(Material.water)) {
                final int round = Math.round(MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3) * 100.0f);
                if (round > 0) {
                    this.addStat(StatList.distanceDoveStat, round);
                    this.addExhaustion(0.015f * round * 0.01f);
                    "".length();
                    if (3 != 3) {
                        throw null;
                    }
                }
            }
            else if (this.isInWater()) {
                final int round2 = Math.round(MathHelper.sqrt_double(n * n + n3 * n3) * 100.0f);
                if (round2 > 0) {
                    this.addStat(StatList.distanceSwumStat, round2);
                    this.addExhaustion(0.015f * round2 * 0.01f);
                    "".length();
                    if (2 <= 1) {
                        throw null;
                    }
                }
            }
            else if (this.isOnLadder()) {
                if (n2 > 0.0) {
                    this.addStat(StatList.distanceClimbedStat, (int)Math.round(n2 * 100.0));
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
            }
            else if (this.onGround) {
                final int round3 = Math.round(MathHelper.sqrt_double(n * n + n3 * n3) * 100.0f);
                if (round3 > 0) {
                    this.addStat(StatList.distanceWalkedStat, round3);
                    if (this.isSprinting()) {
                        this.addStat(StatList.distanceSprintedStat, round3);
                        this.addExhaustion(0.099999994f * round3 * 0.01f);
                        "".length();
                        if (-1 == 3) {
                            throw null;
                        }
                    }
                    else {
                        if (this.isSneaking()) {
                            this.addStat(StatList.distanceCrouchedStat, round3);
                        }
                        this.addExhaustion(0.01f * round3 * 0.01f);
                        "".length();
                        if (4 != 4) {
                            throw null;
                        }
                    }
                }
            }
            else {
                final int round4 = Math.round(MathHelper.sqrt_double(n * n + n3 * n3) * 100.0f);
                if (round4 > (0x5E ^ 0x47)) {
                    this.addStat(StatList.distanceFlownStat, round4);
                }
            }
        }
    }
    
    @Override
    public boolean replaceItemInInventory(final int n, final ItemStack itemStack) {
        if (n >= 0 && n < this.inventory.mainInventory.length) {
            this.inventory.setInventorySlotContents(n, itemStack);
            return " ".length() != 0;
        }
        final int n2 = n - (0x7C ^ 0x18);
        if (n2 >= 0 && n2 < this.inventory.armorInventory.length) {
            final int n3 = n2 + " ".length();
            if (itemStack != null && itemStack.getItem() != null) {
                if (itemStack.getItem() instanceof ItemArmor) {
                    if (EntityLiving.getArmorPosition(itemStack) != n3) {
                        return "".length() != 0;
                    }
                }
                else if (n3 != (0x96 ^ 0x92) || (itemStack.getItem() != Items.skull && !(itemStack.getItem() instanceof ItemBlock))) {
                    return "".length() != 0;
                }
            }
            this.inventory.setInventorySlotContents(n2 + this.inventory.mainInventory.length, itemStack);
            return " ".length() != 0;
        }
        final int n4 = n - (41 + 170 - 13 + 2);
        if (n4 >= 0 && n4 < this.theInventoryEnderChest.getSizeInventory()) {
            this.theInventoryEnderChest.setInventorySlotContents(n4, itemStack);
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public Scoreboard getWorldScoreboard() {
        return this.worldObj.getScoreboard();
    }
    
    protected void updateItemUse(final ItemStack itemStack, final int n) {
        if (itemStack.getItemUseAction() == EnumAction.DRINK) {
            this.playSound(EntityPlayer.I["  ".length()], 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if (itemStack.getItemUseAction() == EnumAction.EAT) {
            int i = "".length();
            "".length();
            if (3 < -1) {
                throw null;
            }
            while (i < n) {
                final Vec3 rotateYaw = new Vec3((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f);
                final Vec3 addVector = new Vec3((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6).rotatePitch(-this.rotationPitch * 3.1415927f / 180.0f).rotateYaw(-this.rotationYaw * 3.1415927f / 180.0f).addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
                if (itemStack.getHasSubtypes()) {
                    final World worldObj = this.worldObj;
                    final EnumParticleTypes item_CRACK = EnumParticleTypes.ITEM_CRACK;
                    final double xCoord = addVector.xCoord;
                    final double yCoord = addVector.yCoord;
                    final double zCoord = addVector.zCoord;
                    final double xCoord2 = rotateYaw.xCoord;
                    final double n2 = rotateYaw.yCoord + 0.05;
                    final double zCoord2 = rotateYaw.zCoord;
                    final int[] array = new int["  ".length()];
                    array["".length()] = Item.getIdFromItem(itemStack.getItem());
                    array[" ".length()] = itemStack.getMetadata();
                    worldObj.spawnParticle(item_CRACK, xCoord, yCoord, zCoord, xCoord2, n2, zCoord2, array);
                    "".length();
                    if (1 == 3) {
                        throw null;
                    }
                }
                else {
                    final World worldObj2 = this.worldObj;
                    final EnumParticleTypes item_CRACK2 = EnumParticleTypes.ITEM_CRACK;
                    final double xCoord3 = addVector.xCoord;
                    final double yCoord2 = addVector.yCoord;
                    final double zCoord3 = addVector.zCoord;
                    final double xCoord4 = rotateYaw.xCoord;
                    final double n3 = rotateYaw.yCoord + 0.05;
                    final double zCoord4 = rotateYaw.zCoord;
                    final int[] array2 = new int[" ".length()];
                    array2["".length()] = Item.getIdFromItem(itemStack.getItem());
                    worldObj2.spawnParticle(item_CRACK2, xCoord3, yCoord2, zCoord3, xCoord4, n3, zCoord4, array2);
                }
                ++i;
            }
            this.playSound(EntityPlayer.I["   ".length()], 0.5f + 0.5f * this.rand.nextInt("  ".length()), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    public int getXPSeed() {
        return this.xpSeed;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource damageSource, float n) {
        if (this.isEntityInvulnerable(damageSource)) {
            return "".length() != 0;
        }
        if (this.capabilities.disableDamage && !damageSource.canHarmInCreative()) {
            return "".length() != 0;
        }
        this.entityAge = "".length();
        if (this.getHealth() <= 0.0f) {
            return "".length() != 0;
        }
        if (this.isPlayerSleeping() && !this.worldObj.isRemote) {
            this.wakeUpPlayer(" ".length() != 0, " ".length() != 0, "".length() != 0);
        }
        if (damageSource.isDifficultyScaled()) {
            if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL) {
                n = 0.0f;
            }
            if (this.worldObj.getDifficulty() == EnumDifficulty.EASY) {
                n = n / 2.0f + 1.0f;
            }
            if (this.worldObj.getDifficulty() == EnumDifficulty.HARD) {
                n = n * 3.0f / 2.0f;
            }
        }
        if (n == 0.0f) {
            return "".length() != 0;
        }
        final Entity entity = damageSource.getEntity();
        if (entity instanceof EntityArrow && ((EntityArrow)entity).shootingEntity != null) {
            final Entity shootingEntity = ((EntityArrow)entity).shootingEntity;
        }
        return super.attackEntityFrom(damageSource, n);
    }
    
    @Override
    protected String getHurtSound() {
        return EntityPlayer.I[0x46 ^ 0x41];
    }
    
    public boolean canOpen(final LockCode lockCode) {
        if (lockCode.isEmpty()) {
            return " ".length() != 0;
        }
        final ItemStack currentEquippedItem = this.getCurrentEquippedItem();
        int n;
        if (currentEquippedItem != null && currentEquippedItem.hasDisplayName()) {
            n = (currentEquippedItem.getDisplayName().equals(lockCode.getLock()) ? 1 : 0);
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound nbtTagCompound) {
        super.readEntityFromNBT(nbtTagCompound);
        this.entityUniqueID = getUUID(this.gameProfile);
        this.inventory.readFromNBT(nbtTagCompound.getTagList(EntityPlayer.I[0x8E ^ 0x87], 0x6F ^ 0x65));
        this.inventory.currentItem = nbtTagCompound.getInteger(EntityPlayer.I[0x6C ^ 0x66]);
        this.sleeping = nbtTagCompound.getBoolean(EntityPlayer.I[0x67 ^ 0x6C]);
        this.sleepTimer = nbtTagCompound.getShort(EntityPlayer.I[0x46 ^ 0x4A]);
        this.experience = nbtTagCompound.getFloat(EntityPlayer.I[0x9 ^ 0x4]);
        this.experienceLevel = nbtTagCompound.getInteger(EntityPlayer.I[0xBA ^ 0xB4]);
        this.experienceTotal = nbtTagCompound.getInteger(EntityPlayer.I[0x4 ^ 0xB]);
        this.xpSeed = nbtTagCompound.getInteger(EntityPlayer.I[0x49 ^ 0x59]);
        if (this.xpSeed == 0) {
            this.xpSeed = this.rand.nextInt();
        }
        this.setScore(nbtTagCompound.getInteger(EntityPlayer.I[0x19 ^ 0x8]));
        if (this.sleeping) {
            this.playerLocation = new BlockPos(this);
            this.wakeUpPlayer(" ".length() != 0, " ".length() != 0, "".length() != 0);
        }
        if (nbtTagCompound.hasKey(EntityPlayer.I[0x65 ^ 0x77], 0x57 ^ 0x34) && nbtTagCompound.hasKey(EntityPlayer.I[0xBE ^ 0xAD], 0xFF ^ 0x9C) && nbtTagCompound.hasKey(EntityPlayer.I[0xB9 ^ 0xAD], 0xE7 ^ 0x84)) {
            this.spawnChunk = new BlockPos(nbtTagCompound.getInteger(EntityPlayer.I[0x91 ^ 0x84]), nbtTagCompound.getInteger(EntityPlayer.I[0x9 ^ 0x1F]), nbtTagCompound.getInteger(EntityPlayer.I[0x8B ^ 0x9C]));
            this.spawnForced = nbtTagCompound.getBoolean(EntityPlayer.I[0x85 ^ 0x9D]);
        }
        this.foodStats.readNBT(nbtTagCompound);
        this.capabilities.readCapabilitiesFromNBT(nbtTagCompound);
        if (nbtTagCompound.hasKey(EntityPlayer.I[0x19 ^ 0x0], 0x73 ^ 0x7A)) {
            this.theInventoryEnderChest.loadInventoryFromNBT(nbtTagCompound.getTagList(EntityPlayer.I[0x49 ^ 0x53], 0x77 ^ 0x7D));
        }
    }
    
    @Override
    public void onUpdate() {
        this.noClip = this.isSpectator();
        if (this.isSpectator()) {
            this.onGround = ("".length() != 0);
        }
        if (this.itemInUse != null) {
            final ItemStack currentItem = this.inventory.getCurrentItem();
            if (currentItem == this.itemInUse) {
                if (this.itemInUseCount <= (0x4 ^ 0x1D) && this.itemInUseCount % (0x6A ^ 0x6E) == 0) {
                    this.updateItemUse(currentItem, 0x8C ^ 0x89);
                }
                if ((this.itemInUseCount -= " ".length()) == 0 && !this.worldObj.isRemote) {
                    this.onItemUseFinish();
                    "".length();
                    if (1 <= -1) {
                        throw null;
                    }
                }
            }
            else {
                this.clearItemInUse();
            }
        }
        if (this.xpCooldown > 0) {
            this.xpCooldown -= " ".length();
        }
        if (this.isPlayerSleeping()) {
            this.sleepTimer += " ".length();
            if (this.sleepTimer > (0x70 ^ 0x14)) {
                this.sleepTimer = (0x6E ^ 0xA);
            }
            if (!this.worldObj.isRemote) {
                if (!this.isInBed()) {
                    this.wakeUpPlayer(" ".length() != 0, " ".length() != 0, "".length() != 0);
                    "".length();
                    if (2 != 2) {
                        throw null;
                    }
                }
                else if (this.worldObj.isDaytime()) {
                    this.wakeUpPlayer("".length() != 0, " ".length() != 0, " ".length() != 0);
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
            }
        }
        else if (this.sleepTimer > 0) {
            this.sleepTimer += " ".length();
            if (this.sleepTimer >= (0xD3 ^ 0xBD)) {
                this.sleepTimer = "".length();
            }
        }
        super.onUpdate();
        if (!this.worldObj.isRemote && this.openContainer != null && !this.openContainer.canInteractWith(this)) {
            this.closeScreen();
            this.openContainer = this.inventoryContainer;
        }
        if (this.isBurning() && this.capabilities.disableDamage) {
            this.extinguish();
        }
        this.prevChasingPosX = this.chasingPosX;
        this.prevChasingPosY = this.chasingPosY;
        this.prevChasingPosZ = this.chasingPosZ;
        final double n = this.posX - this.chasingPosX;
        final double n2 = this.posY - this.chasingPosY;
        final double n3 = this.posZ - this.chasingPosZ;
        final double n4 = 10.0;
        if (n > n4) {
            final double posX = this.posX;
            this.chasingPosX = posX;
            this.prevChasingPosX = posX;
        }
        if (n3 > n4) {
            final double posZ = this.posZ;
            this.chasingPosZ = posZ;
            this.prevChasingPosZ = posZ;
        }
        if (n2 > n4) {
            final double posY = this.posY;
            this.chasingPosY = posY;
            this.prevChasingPosY = posY;
        }
        if (n < -n4) {
            final double posX2 = this.posX;
            this.chasingPosX = posX2;
            this.prevChasingPosX = posX2;
        }
        if (n3 < -n4) {
            final double posZ2 = this.posZ;
            this.chasingPosZ = posZ2;
            this.prevChasingPosZ = posZ2;
        }
        if (n2 < -n4) {
            final double posY2 = this.posY;
            this.chasingPosY = posY2;
            this.prevChasingPosY = posY2;
        }
        this.chasingPosX += n * 0.25;
        this.chasingPosZ += n3 * 0.25;
        this.chasingPosY += n2 * 0.25;
        if (this.ridingEntity == null) {
            this.startMinecartRidingCoordinate = null;
        }
        if (!this.worldObj.isRemote) {
            this.foodStats.onUpdate(this);
            this.triggerAchievement(StatList.minutesPlayedStat);
            if (this.isEntityAlive()) {
                this.triggerAchievement(StatList.timeSinceDeathStat);
            }
        }
        final double clamp_double = MathHelper.clamp_double(this.posX, -2.9999999E7, 2.9999999E7);
        final double clamp_double2 = MathHelper.clamp_double(this.posZ, -2.9999999E7, 2.9999999E7);
        if (clamp_double != this.posX || clamp_double2 != this.posZ) {
            this.setPosition(clamp_double, this.posY, clamp_double2);
        }
    }
    
    public void addChatComponentMessage(final IChatComponent chatComponent) {
    }
    
    private static void I() {
        (I = new String[0x6 ^ 0x35])["".length()] = I("\u0016( .t\u0001%,2?\u0003g><3\u001c", "qIMKZ");
        EntityPlayer.I[" ".length()] = I("$\u0005\u0000\b|3\b\f\u001471J\u001e\u001a;.J\u001e\u001d>\"\u0017\u0005", "CdmmR");
        EntityPlayer.I["  ".length()] = I("\u001e\u0018\u0019\u000b*\u0001W\u0013\u001d,\u0002\u0012", "lywoE");
        EntityPlayer.I["   ".length()] = I("\u001b6\u001c\u00127\u0004y\u0017\u0017,", "iWrvX");
        EntityPlayer.I[0xB5 ^ 0xB1] = I("-\u000e\u001c\u000f!\"\u0003:\u001f4&\u0001\r\b27\u0006\u0007\u0014", "CohzS");
        EntityPlayer.I[0x98 ^ 0x9D] = I("\u0019\u00063&&", "WiGEN");
        EntityPlayer.I[0x88 ^ 0x8E] = I("1\f\u0010\u0014:4\u001f\u0010\n\u00075\u001b\f", "Ziuds");
        EntityPlayer.I[0x6B ^ 0x6C] = I("\u001d\u0014\u000f&^\n\u0019\u0003:\u0015\b[\n6\u0002\u000e", "zubCp");
        EntityPlayer.I[0x39 ^ 0x31] = I("7\f/2f \u0001#.-\"C&>-", "PmBWH");
        EntityPlayer.I[0x31 ^ 0x38] = I("\f8<\r\u0019198\u0011", "EVJhw");
        EntityPlayer.I[0x10 ^ 0x1A] = I("\u0018\u0001$\u0012\u0016?\u0001,>\u0001.\t\u001b\u001b\u001a?", "KdHwu");
        EntityPlayer.I[0x49 ^ 0x42] = I("8\b)\u0017\u001e\u0002\n+", "kdLrn");
        EntityPlayer.I[0x80 ^ 0x8C] = I("\u001b9\u0016\u0017\t\u001c<\u001e\u0017\u000b", "HUsry");
        EntityPlayer.I[0x8C ^ 0x81] = I("\"\u0005\u0017", "zuGve");
        EntityPlayer.I[0xA6 ^ 0xA8] = I("\u0000\u0013\u0006+\"=\u000f", "XcJNT");
        EntityPlayer.I[0x5C ^ 0x53] = I("\u001e6\":\u001b'*", "FFvUo");
        EntityPlayer.I[0xD3 ^ 0xC3] = I(".\u001f\n\u001f\u0012\u0012", "voYzw");
        EntityPlayer.I[0x85 ^ 0x94] = I("5\u000f$\u001c/", "flKnJ");
        EntityPlayer.I[0xA6 ^ 0xB4] = I("\u0015;3\u001f\u0004\u001e", "FKRhj");
        EntityPlayer.I[0xD0 ^ 0xC3] = I("\u0018\t\u0003 +\u0012", "KybWE");
        EntityPlayer.I[0xD5 ^ 0xC1] = I("\u001d=\u0011\u0012\r\u0014", "NMpec");
        EntityPlayer.I[0x41 ^ 0x54] = I("\u0017\u0014\r09\u001c", "DdlGW");
        EntityPlayer.I[0x3E ^ 0x28] = I("0\u0003\u0018\u0013&:", "csydH");
        EntityPlayer.I[0x36 ^ 0x21] = I("*\u0018$',#", "yhEPB");
        EntityPlayer.I[0x79 ^ 0x61] = I("\u000b\u000783\u001c\u001e\u0018+'\u0017<", "XwYDr");
        EntityPlayer.I[0x4A ^ 0x53] = I("'\u0003\u0002'$+\u0019\u0003/%", "bmfBV");
        EntityPlayer.I[0x68 ^ 0x72] = I("\u0007\u0017\u0005\n3\u000b\r\u0004\u00022", "ByaoA");
        EntityPlayer.I[0xA3 ^ 0xB8] = I("\u0000'#\u001c,=&'\u0000", "IIUyB");
        EntityPlayer.I[0x81 ^ 0x9D] = I("\u001f\u0012\r<'8\u0012\u0005\u00100)\u001a25+8", "LwaYD");
        EntityPlayer.I[0x5E ^ 0x43] = I("\t'\u000f\u000b93%\r", "ZKjnI");
        EntityPlayer.I[0xDB ^ 0xC5] = I("\u001a'\u001f\u00025\u001d\"\u0017\u00027", "IKzgE");
        EntityPlayer.I[0x65 ^ 0x7A] = I("*()", "rXycj");
        EntityPlayer.I[0xE3 ^ 0xC3] = I("\u001a: *\u0003'&", "BJlOu");
        EntityPlayer.I[0x7D ^ 0x5C] = I("1\u001f\u000e\u0000%\b\u0003", "ioZoQ");
        EntityPlayer.I[0x57 ^ 0x75] = I("\u0014\u00041\u0011\u0004(", "Ltbta");
        EntityPlayer.I[0x3A ^ 0x19] = I("\u0011\u0006\u0007<$", "BehNA");
        EntityPlayer.I[0x96 ^ 0xB2] = I("\u0015:\u0007\u001f%\u001e", "FJfhK");
        EntityPlayer.I[0x27 ^ 0x2] = I("4*\u000e\u0003\u000f>", "gZota");
        EntityPlayer.I[0x52 ^ 0x74] = I("\u0000\u001d\r\u000e\t\t", "Smlyg");
        EntityPlayer.I[0x4B ^ 0x6C] = I("+52\u001c\u0003>*!\b\b\u001c", "xESkm");
        EntityPlayer.I[0x3A ^ 0x12] = I("\n\u0007\u0005/:\u0006\u001d\u0004';", "OiaJH");
        EntityPlayer.I[0x2B ^ 0x2] = I("6<\r\u000b\u0013\u0011<\u0005'\u0004\u00004", "eYanp");
        EntityPlayer.I[0xE8 ^ 0xC2] = I("\u0017%\u0002\bZ\u0000(\u000e\u0014\u0011\u0002j\u0007\u0018\u0006\u0004j\t\f\u0018\u001cj\r\u0004\u0013", "pDomt");
        EntityPlayer.I[0x91 ^ 0xBA] = I(")\u000f\b\u000ez>\u0002\u0004\u00121<@\r\u001e&:@\u0003\n8\"@\u0016\u00065\"\u0002", "NnekT");
        EntityPlayer.I[0x3D ^ 0x11] = I(":$+,\u0004%k)-\u001d-)08", "HEEHk");
        EntityPlayer.I[0x27 ^ 0xA] = I("%3\n\u001f\u001f  \n\u0001\"!$\u0016", "NVooV");
        EntityPlayer.I[0xA7 ^ 0x89] = I("\u001f\u001c\n1\u0010\u001a\u000f\n/-\u001b\u000b\u0016", "tyoAY");
        EntityPlayer.I[0x59 ^ 0x76] = I("@* \ru", "oGSjU");
        EntityPlayer.I[0x22 ^ 0x12] = I("A", "aUMmb");
        EntityPlayer.I[0x83 ^ 0xB2] = I("\u0017\u000e2\u001f 6\r\u0004\u001f(!\r&I", "XhTsI");
        EntityPlayer.I[0x4C ^ 0x7E] = I("?3?\u000e:#;<\u000b\u0017(\u00104\u000f\u001d.72\u0001", "LVQjy");
    }
    
    public boolean hasReducedDebug() {
        return this.hasReducedDebug;
    }
    
    @Override
    public void onDeath(final DamageSource damageSource) {
        super.onDeath(damageSource);
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.10000000149011612;
        if (this.getName().equals(EntityPlayer.I[0x57 ^ 0x52])) {
            this.dropItem(new ItemStack(Items.apple, " ".length()), " ".length() != 0, "".length() != 0);
        }
        if (!this.worldObj.getGameRules().getBoolean(EntityPlayer.I[0xB ^ 0xD])) {
            this.inventory.dropAllItems();
        }
        if (damageSource != null) {
            this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
            this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
            "".length();
            if (0 >= 2) {
                throw null;
            }
        }
        else {
            final double n = 0.0;
            this.motionZ = n;
            this.motionX = n;
        }
        this.triggerAchievement(StatList.deathsStat);
        this.func_175145_a(StatList.timeSinceDeathStat);
    }
    
    public boolean isAllowEdit() {
        return this.capabilities.allowEdit;
    }
    
    public void wakeUpPlayer(final boolean b, final boolean b2, final boolean b3) {
        this.setSize(0.6f, 1.8f);
        final IBlockState blockState = this.worldObj.getBlockState(this.playerLocation);
        if (this.playerLocation != null && blockState.getBlock() == Blocks.bed) {
            this.worldObj.setBlockState(this.playerLocation, blockState.withProperty((IProperty<Comparable>)BlockBed.OCCUPIED, (boolean)("".length() != 0)), 0x8D ^ 0x89);
            BlockPos blockPos = BlockBed.getSafeExitLocation(this.worldObj, this.playerLocation, "".length());
            if (blockPos == null) {
                blockPos = this.playerLocation.up();
            }
            this.setPosition(blockPos.getX() + 0.5f, blockPos.getY() + 0.1f, blockPos.getZ() + 0.5f);
        }
        this.sleeping = ("".length() != 0);
        if (!this.worldObj.isRemote && b2) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        int length;
        if (b) {
            length = "".length();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            length = (0x60 ^ 0x4);
        }
        this.sleepTimer = length;
        if (b3) {
            this.setSpawnPoint(this.playerLocation, "".length() != 0);
        }
    }
    
    @Override
    protected String getSplashSound() {
        return EntityPlayer.I[" ".length()];
    }
    
    public FoodStats getFoodStats() {
        return this.foodStats;
    }
    
    public void addExperience(int n) {
        this.addScore(n);
        final int n2 = 2089964913 + 206907383 - 568643516 + 419254867 - this.experienceTotal;
        if (n > n2) {
            n = n2;
        }
        this.experience += n / this.xpBarCap();
        this.experienceTotal += n;
        "".length();
        if (-1 != -1) {
            throw null;
        }
        while (this.experience >= 1.0f) {
            this.experience = (this.experience - 1.0f) * this.xpBarCap();
            this.addExperienceLevel(" ".length());
            this.experience /= this.xpBarCap();
        }
    }
    
    public void stopUsingItem() {
        if (this.itemInUse != null) {
            this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
        }
        this.clearItemInUse();
    }
    
    public int getItemInUseDuration() {
        int length;
        if (this.isUsingItem()) {
            length = this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount;
            "".length();
            if (3 <= 2) {
                throw null;
            }
        }
        else {
            length = "".length();
        }
        return length;
    }
    
    @Override
    public IChatComponent getDisplayName() {
        final ChatComponentText chatComponentText = new ChatComponentText(ScorePlayerTeam.formatPlayerName(this.getTeam(), this.getName()));
        chatComponentText.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, EntityPlayer.I[0xB2 ^ 0x9D] + this.getName() + EntityPlayer.I[0x13 ^ 0x23]));
        chatComponentText.getChatStyle().setChatHoverEvent(this.getHoverEvent());
        chatComponentText.getChatStyle().setInsertion(this.getName());
        return chatComponentText;
    }
    
    public void preparePlayerToSpawn() {
        this.setSize(0.6f, 1.8f);
        super.preparePlayerToSpawn();
        this.setHealth(this.getMaxHealth());
        this.deathTime = "".length();
    }
    
    @Override
    protected void damageArmor(final float n) {
        this.inventory.damageArmor(n);
    }
    
    @Override
    public ItemStack getEquipmentInSlot(final int n) {
        ItemStack currentItem;
        if (n == 0) {
            currentItem = this.inventory.getCurrentItem();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            currentItem = this.inventory.armorInventory[n - " ".length()];
        }
        return currentItem;
    }
    
    public boolean isUsingItem() {
        if (this.itemInUse != null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean canAttackPlayer(final EntityPlayer entityPlayer) {
        final Team team = this.getTeam();
        final Team team2 = entityPlayer.getTeam();
        int n;
        if (team == null) {
            n = " ".length();
            "".length();
            if (2 <= 0) {
                throw null;
            }
        }
        else if (!team.isSameTeam(team2)) {
            n = " ".length();
            "".length();
            if (-1 < -1) {
                throw null;
            }
        }
        else {
            n = (team.getAllowFriendlyFire() ? 1 : 0);
        }
        return n != 0;
    }
    
    public boolean canHarvestBlock(final Block block) {
        return this.inventory.canHeldItemHarvest(block);
    }
    
    public void addStat(final StatBase statBase, final int n) {
    }
    
    public void func_175145_a(final StatBase statBase) {
    }
    
    public void clonePlayer(final EntityPlayer entityPlayer, final boolean b) {
        if (b) {
            this.inventory.copyInventory(entityPlayer.inventory);
            this.setHealth(entityPlayer.getHealth());
            this.foodStats = entityPlayer.foodStats;
            this.experienceLevel = entityPlayer.experienceLevel;
            this.experienceTotal = entityPlayer.experienceTotal;
            this.experience = entityPlayer.experience;
            this.setScore(entityPlayer.getScore());
            this.field_181016_an = entityPlayer.field_181016_an;
            this.field_181017_ao = entityPlayer.field_181017_ao;
            this.field_181018_ap = entityPlayer.field_181018_ap;
            "".length();
            if (0 >= 4) {
                throw null;
            }
        }
        else if (this.worldObj.getGameRules().getBoolean(EntityPlayer.I[0xBB ^ 0x95])) {
            this.inventory.copyInventory(entityPlayer.inventory);
            this.experienceLevel = entityPlayer.experienceLevel;
            this.experienceTotal = entityPlayer.experienceTotal;
            this.experience = entityPlayer.experience;
            this.setScore(entityPlayer.getScore());
        }
        this.xpSeed = entityPlayer.xpSeed;
        this.theInventoryEnderChest = entityPlayer.theInventoryEnderChest;
        this.getDataWatcher().updateObject(0x70 ^ 0x7A, entityPlayer.getDataWatcher().getWatchableObjectByte(0x4B ^ 0x41));
    }
    
    public void clearItemInUse() {
        this.itemInUse = null;
        this.itemInUseCount = "".length();
        if (!this.worldObj.isRemote) {
            this.setEating("".length() != 0);
        }
    }
    
    protected void closeScreen() {
        this.openContainer = this.inventoryContainer;
    }
    
    public void triggerAchievement(final StatBase statBase) {
        this.addStat(statBase, " ".length());
    }
    
    @Override
    public ItemStack getCurrentArmor(final int n) {
        return this.inventory.armorItemInSlot(n);
    }
    
    @Override
    public boolean isInvisibleToPlayer(final EntityPlayer entityPlayer) {
        if (!this.isInvisible()) {
            return "".length() != 0;
        }
        if (entityPlayer.isSpectator()) {
            return "".length() != 0;
        }
        final Team team = this.getTeam();
        if (team != null && entityPlayer != null && entityPlayer.getTeam() == team && team.getSeeFriendlyInvisiblesEnabled()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void removeExperienceLevel(final int n) {
        this.experienceLevel -= n;
        if (this.experienceLevel < 0) {
            this.experienceLevel = "".length();
            this.experience = 0.0f;
            this.experienceTotal = "".length();
        }
        this.xpSeed = this.rand.nextInt();
    }
    
    @Override
    protected String getFallSoundString(final int n) {
        String s;
        if (n > (0xA0 ^ 0xA4)) {
            s = EntityPlayer.I[0x42 ^ 0x68];
            "".length();
            if (3 >= 4) {
                throw null;
            }
        }
        else {
            s = EntityPlayer.I[0xE ^ 0x25];
        }
        return s;
    }
    
    @Override
    public int getPortalCooldown() {
        return 0xAD ^ 0xA7;
    }
    
    public void setScore(final int n) {
        this.dataWatcher.updateObject(0xB ^ 0x19, n);
    }
    
    public boolean interactWith(final Entity entity) {
        if (this.isSpectator()) {
            if (entity instanceof IInventory) {
                this.displayGUIChest((IInventory)entity);
            }
            return "".length() != 0;
        }
        ItemStack currentEquippedItem = this.getCurrentEquippedItem();
        ItemStack copy;
        if (currentEquippedItem != null) {
            copy = currentEquippedItem.copy();
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            copy = null;
        }
        final ItemStack itemStack = copy;
        if (!entity.interactFirst(this)) {
            if (currentEquippedItem != null && entity instanceof EntityLivingBase) {
                if (this.capabilities.isCreativeMode) {
                    currentEquippedItem = itemStack;
                }
                if (currentEquippedItem.interactWithEntity(this, (EntityLivingBase)entity)) {
                    if (currentEquippedItem.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                        this.destroyCurrentEquippedItem();
                    }
                    return " ".length() != 0;
                }
            }
            return "".length() != 0;
        }
        if (currentEquippedItem != null && currentEquippedItem == this.getCurrentEquippedItem()) {
            if (currentEquippedItem.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                this.destroyCurrentEquippedItem();
                "".length();
                if (2 >= 4) {
                    throw null;
                }
            }
            else if (currentEquippedItem.stackSize < itemStack.stackSize && this.capabilities.isCreativeMode) {
                currentEquippedItem.stackSize = itemStack.stackSize;
            }
        }
        return " ".length() != 0;
    }
    
    @Override
    public void setCurrentItemOrArmor(final int n, final ItemStack itemStack) {
        this.inventory.armorInventory[n] = itemStack;
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.flyToggleTimer > 0) {
            this.flyToggleTimer -= " ".length();
        }
        if (this.worldObj.getDifficulty() == EnumDifficulty.PEACEFUL && this.worldObj.getGameRules().getBoolean(EntityPlayer.I[0x19 ^ 0x1D])) {
            if (this.getHealth() < this.getMaxHealth() && this.ticksExisted % (0x31 ^ 0x25) == 0) {
                this.heal(1.0f);
            }
            if (this.foodStats.needFood() && this.ticksExisted % (0x3 ^ 0x9) == 0) {
                this.foodStats.setFoodLevel(this.foodStats.getFoodLevel() + " ".length());
            }
        }
        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        final IAttributeInstance entityAttribute = this.getEntityAttribute(SharedMonsterAttributes.movementSpeed);
        if (!this.worldObj.isRemote) {
            entityAttribute.setBaseValue(this.capabilities.getWalkSpeed());
        }
        this.jumpMovementFactor = this.speedInAir;
        if (this.isSprinting()) {
            this.jumpMovementFactor += (float)(this.speedInAir * 0.3);
        }
        this.setAIMoveSpeed((float)entityAttribute.getAttributeValue());
        float sqrt_double = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float n = (float)(Math.atan(-this.motionY * 0.20000000298023224) * 15.0);
        if (sqrt_double > 0.1f) {
            sqrt_double = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0.0f) {
            sqrt_double = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0.0f) {
            n = 0.0f;
        }
        this.cameraYaw += (sqrt_double - this.cameraYaw) * 0.4f;
        this.cameraPitch += (n - this.cameraPitch) * 0.8f;
        if (this.getHealth() > 0.0f && !this.isSpectator()) {
            AxisAlignedBB axisAlignedBB;
            if (this.ridingEntity != null && !this.ridingEntity.isDead) {
                axisAlignedBB = this.getEntityBoundingBox().union(this.ridingEntity.getEntityBoundingBox()).expand(1.0, 0.0, 1.0);
                "".length();
                if (3 < 2) {
                    throw null;
                }
            }
            else {
                axisAlignedBB = this.getEntityBoundingBox().expand(1.0, 0.5, 1.0);
            }
            final List<Entity> entitiesWithinAABBExcludingEntity = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, axisAlignedBB);
            int i = "".length();
            "".length();
            if (4 < 4) {
                throw null;
            }
            while (i < entitiesWithinAABBExcludingEntity.size()) {
                final Entity entity = entitiesWithinAABBExcludingEntity.get(i);
                if (!entity.isDead) {
                    this.collideWithPlayer(entity);
                }
                ++i;
            }
        }
    }
    
    public int getSleepTimer() {
        return this.sleepTimer;
    }
    
    @Override
    public float getEyeHeight() {
        float n = 1.62f;
        if (this.isPlayerSleeping()) {
            n = 0.2f;
        }
        if (this.isSneaking()) {
            n -= 0.08f;
        }
        return n;
    }
    
    @Override
    public void addToPlayerScore(final Entity entity, final int n) {
        this.addScore(n);
        final Collection<ScoreObjective> objectivesFromCriteria = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.totalKillCount);
        if (entity instanceof EntityPlayer) {
            this.triggerAchievement(StatList.playerKillsStat);
            objectivesFromCriteria.addAll(this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.playerKillCount));
            objectivesFromCriteria.addAll(this.func_175137_e(entity));
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        else {
            this.triggerAchievement(StatList.mobKillsStat);
        }
        final Iterator<ScoreObjective> iterator = objectivesFromCriteria.iterator();
        "".length();
        if (3 < -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            this.getWorldScoreboard().getValueFromObjective(this.getName(), iterator.next()).func_96648_a();
        }
    }
    
    public void setGameType(final WorldSettings.GameType gameType) {
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer entityPlayer) {
        if (this.worldObj.getGameRules().getBoolean(EntityPlayer.I[0x2D ^ 0x0])) {
            return "".length();
        }
        final int n = this.experienceLevel * (0x74 ^ 0x73);
        int n2;
        if (n > (0xC ^ 0x68)) {
            n2 = (0x4C ^ 0x28);
            "".length();
            if (1 >= 4) {
                throw null;
            }
        }
        else {
            n2 = n;
        }
        return n2;
    }
    
    public void setReducedDebug(final boolean hasReducedDebug) {
        this.hasReducedDebug = hasReducedDebug;
    }
    
    private void func_175139_a(final EnumFacing enumFacing) {
        this.renderOffsetX = 0.0f;
        this.renderOffsetZ = 0.0f;
        switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
            case 4: {
                this.renderOffsetZ = -1.8f;
                "".length();
                if (4 <= 0) {
                    throw null;
                }
                break;
            }
            case 3: {
                this.renderOffsetZ = 1.8f;
                "".length();
                if (4 < 3) {
                    throw null;
                }
                break;
            }
            case 5: {
                this.renderOffsetX = 1.8f;
                "".length();
                if (3 <= 0) {
                    throw null;
                }
                break;
            }
            case 6: {
                this.renderOffsetX = -1.8f;
                break;
            }
        }
    }
    
    @Override
    public int getMaxInPortalTime() {
        int length;
        if (this.capabilities.disableDamage) {
            length = "".length();
            "".length();
            if (4 == 3) {
                throw null;
            }
        }
        else {
            length = (0xC8 ^ 0x98);
        }
        return length;
    }
    
    public void displayGUIChest(final IInventory inventory) {
    }
    
    @Override
    public boolean sendCommandFeedback() {
        return MinecraftServer.getServer().worldServers["".length()].getGameRules().getBoolean(EntityPlayer.I[0xA8 ^ 0x9A]);
    }
    
    private Collection<ScoreObjective> func_175137_e(final Entity entity) {
        final ScorePlayerTeam playersTeam = this.getWorldScoreboard().getPlayersTeam(this.getName());
        if (playersTeam != null) {
            final int colorIndex = playersTeam.getChatFormat().getColorIndex();
            if (colorIndex >= 0 && colorIndex < IScoreObjectiveCriteria.field_178793_i.length) {
                final Iterator<ScoreObjective> iterator = this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178793_i[colorIndex]).iterator();
                "".length();
                if (2 != 2) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    this.getWorldScoreboard().getValueFromObjective(entity.getName(), iterator.next()).func_96648_a();
                }
            }
        }
        final ScorePlayerTeam playersTeam2 = this.getWorldScoreboard().getPlayersTeam(entity.getName());
        if (playersTeam2 != null) {
            final int colorIndex2 = playersTeam2.getChatFormat().getColorIndex();
            if (colorIndex2 >= 0 && colorIndex2 < IScoreObjectiveCriteria.field_178792_h.length) {
                return this.getWorldScoreboard().getObjectivesFromCriteria(IScoreObjectiveCriteria.field_178792_h[colorIndex2]);
            }
        }
        return (Collection<ScoreObjective>)Lists.newArrayList();
    }
    
    @Override
    public boolean isPlayerSleeping() {
        return this.sleeping;
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.inventory.getCurrentItem();
    }
    
    @Override
    public float getAbsorptionAmount() {
        return this.getDataWatcher().getWatchableObjectFloat(0x9C ^ 0x8D);
    }
    
    public int getItemInUseCount() {
        return this.itemInUseCount;
    }
    
    @Override
    public void fall(final float n, final float n2) {
        if (!this.capabilities.allowFlying) {
            if (n >= 2.0f) {
                this.addStat(StatList.distanceFallenStat, (int)Math.round(n * 100.0));
            }
            super.fall(n, n2);
        }
    }
    
    public boolean isPlayerFullyAsleep() {
        if (this.sleeping && this.sleepTimer >= (0x51 ^ 0x35)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public int xpBarCap() {
        int n;
        if (this.experienceLevel >= (0xC ^ 0x12)) {
            n = (0xB4 ^ 0xC4) + (this.experienceLevel - (0x2D ^ 0x33)) * (0x37 ^ 0x3E);
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        else if (this.experienceLevel >= (0x44 ^ 0x4B)) {
            n = (0x5D ^ 0x78) + (this.experienceLevel - (0x76 ^ 0x79)) * (0x3B ^ 0x3E);
            "".length();
            if (3 != 3) {
                throw null;
            }
        }
        else {
            n = (0xBD ^ 0xBA) + this.experienceLevel * "  ".length();
        }
        return n;
    }
    
    @Override
    protected boolean isPlayer() {
        return " ".length() != 0;
    }
    
    @Override
    protected String getSwimSound() {
        return EntityPlayer.I["".length()];
    }
    
    public void onCriticalHit(final Entity entity) {
    }
    
    public boolean isBlocking() {
        if (this.isUsingItem() && this.itemInUse.getItem().getItemUseAction(this.itemInUse) == EnumAction.BLOCK) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void openEditSign(final TileEntitySign tileEntitySign) {
    }
    
    @Override
    protected void damageEntity(final DamageSource damageSource, float n) {
        if (!this.isEntityInvulnerable(damageSource)) {
            if (!damageSource.isUnblockable() && this.isBlocking() && n > 0.0f) {
                n = (1.0f + n) * 0.5f;
            }
            n = this.applyArmorCalculations(damageSource, n);
            final float applyPotionDamageCalculations;
            n = (applyPotionDamageCalculations = this.applyPotionDamageCalculations(damageSource, n));
            n = Math.max(n - this.getAbsorptionAmount(), 0.0f);
            this.setAbsorptionAmount(this.getAbsorptionAmount() - (applyPotionDamageCalculations - n));
            if (n != 0.0f) {
                this.addExhaustion(damageSource.getHungerDamage());
                final float health = this.getHealth();
                this.setHealth(this.getHealth() - n);
                this.getCombatTracker().trackDamage(damageSource, health, n);
                if (n < 3.4028235E37f) {
                    this.addStat(StatList.damageTakenStat, Math.round(n * 10.0f));
                }
            }
        }
    }
    
    @Override
    protected boolean canTriggerWalking() {
        int n;
        if (this.capabilities.isFlying) {
            n = "".length();
            "".length();
            if (false) {
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
        return EntityPlayer.I[0x8A ^ 0x82];
    }
    
    @Override
    protected boolean isMovementBlocked() {
        if (this.getHealth() > 0.0f && !this.isPlayerSleeping()) {
            return "".length() != 0;
        }
        return " ".length() != 0;
    }
    
    public void attackTargetEntityWithCurrentItem(final Entity lastAttacker) {
        if (lastAttacker.canAttackWithItem() && !lastAttacker.hitByEntity(this)) {
            float n = (float)this.getEntityAttribute(SharedMonsterAttributes.attackDamage).getAttributeValue();
            final int length = "".length();
            float n2;
            if (lastAttacker instanceof EntityLivingBase) {
                n2 = EnchantmentHelper.func_152377_a(this.getHeldItem(), ((EntityLivingBase)lastAttacker).getCreatureAttribute());
                "".length();
                if (3 != 3) {
                    throw null;
                }
            }
            else {
                n2 = EnchantmentHelper.func_152377_a(this.getHeldItem(), EnumCreatureAttribute.UNDEFINED);
            }
            int n3 = length + EnchantmentHelper.getKnockbackModifier(this);
            if (this.isSprinting()) {
                ++n3;
            }
            if (n > 0.0f || n2 > 0.0f) {
                int n4;
                if (this.fallDistance > 0.0f && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && lastAttacker instanceof EntityLivingBase) {
                    n4 = " ".length();
                    "".length();
                    if (0 <= -1) {
                        throw null;
                    }
                }
                else {
                    n4 = "".length();
                }
                final int n5 = n4;
                if (n5 != 0 && n > 0.0f) {
                    n *= 1.5f;
                }
                final float n6 = n + n2;
                int n7 = "".length();
                final int fireAspectModifier = EnchantmentHelper.getFireAspectModifier(this);
                if (lastAttacker instanceof EntityLivingBase && fireAspectModifier > 0 && !lastAttacker.isBurning()) {
                    n7 = " ".length();
                    lastAttacker.setFire(" ".length());
                }
                final double motionX = lastAttacker.motionX;
                final double motionY = lastAttacker.motionY;
                final double motionZ = lastAttacker.motionZ;
                if (lastAttacker.attackEntityFrom(DamageSource.causePlayerDamage(this), n6)) {
                    if (n3 > 0) {
                        lastAttacker.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * n3 * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * n3 * 0.5f);
                        this.motionX *= 0.6;
                        this.motionZ *= 0.6;
                        this.setSprinting("".length() != 0);
                    }
                    if (lastAttacker instanceof EntityPlayerMP && lastAttacker.velocityChanged) {
                        ((EntityPlayerMP)lastAttacker).playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(lastAttacker));
                        lastAttacker.velocityChanged = ("".length() != 0);
                        lastAttacker.motionX = motionX;
                        lastAttacker.motionY = motionY;
                        lastAttacker.motionZ = motionZ;
                    }
                    if (n5 != 0) {
                        this.onCriticalHit(lastAttacker);
                    }
                    if (n2 > 0.0f) {
                        this.onEnchantmentCritical(lastAttacker);
                    }
                    if (n6 >= 18.0f) {
                        this.triggerAchievement(AchievementList.overkill);
                    }
                    this.setLastAttacker(lastAttacker);
                    if (lastAttacker instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments((EntityLivingBase)lastAttacker, this);
                    }
                    EnchantmentHelper.applyArthropodEnchantments(this, lastAttacker);
                    final ItemStack currentEquippedItem = this.getCurrentEquippedItem();
                    Entity entity = lastAttacker;
                    if (lastAttacker instanceof EntityDragonPart) {
                        final IEntityMultiPart entityDragonObj = ((EntityDragonPart)lastAttacker).entityDragonObj;
                        if (entityDragonObj instanceof EntityLivingBase) {
                            entity = (EntityLivingBase)entityDragonObj;
                        }
                    }
                    if (currentEquippedItem != null && entity instanceof EntityLivingBase) {
                        currentEquippedItem.hitEntity((EntityLivingBase)entity, this);
                        if (currentEquippedItem.stackSize <= 0) {
                            this.destroyCurrentEquippedItem();
                        }
                    }
                    if (lastAttacker instanceof EntityLivingBase) {
                        this.addStat(StatList.damageDealtStat, Math.round(n6 * 10.0f));
                        if (fireAspectModifier > 0) {
                            lastAttacker.setFire(fireAspectModifier * (0x53 ^ 0x57));
                        }
                    }
                    this.addExhaustion(0.3f);
                    "".length();
                    if (true != true) {
                        throw null;
                    }
                }
                else if (n7 != 0) {
                    lastAttacker.extinguish();
                }
            }
        }
    }
    
    public abstract boolean isSpectator();
    
    protected void onItemUseFinish() {
        if (this.itemInUse != null) {
            this.updateItemUse(this.itemInUse, 0xD4 ^ 0xC4);
            final int stackSize = this.itemInUse.stackSize;
            final ItemStack onItemUseFinish = this.itemInUse.onItemUseFinish(this.worldObj, this);
            if (onItemUseFinish != this.itemInUse || (onItemUseFinish != null && onItemUseFinish.stackSize != stackSize)) {
                this.inventory.mainInventory[this.inventory.currentItem] = onItemUseFinish;
                if (onItemUseFinish.stackSize == 0) {
                    this.inventory.mainInventory[this.inventory.currentItem] = null;
                }
            }
            this.clearItemInUse();
        }
    }
    
    public void sendPlayerAbilities() {
    }
    
    public boolean isUser() {
        return "".length() != 0;
    }
    
    public static UUID getUUID(final GameProfile gameProfile) {
        UUID uuid = gameProfile.getId();
        if (uuid == null) {
            uuid = getOfflineUUID(gameProfile.getName());
        }
        return uuid;
    }
    
    public EnumStatus trySleep(final BlockPos playerLocation) {
        if (!this.worldObj.isRemote) {
            if (this.isPlayerSleeping() || !this.isEntityAlive()) {
                return EnumStatus.OTHER_PROBLEM;
            }
            if (!this.worldObj.provider.isSurfaceWorld()) {
                return EnumStatus.NOT_POSSIBLE_HERE;
            }
            if (this.worldObj.isDaytime()) {
                return EnumStatus.NOT_POSSIBLE_NOW;
            }
            if (Math.abs(this.posX - playerLocation.getX()) > 3.0 || Math.abs(this.posY - playerLocation.getY()) > 2.0 || Math.abs(this.posZ - playerLocation.getZ()) > 3.0) {
                return EnumStatus.TOO_FAR_AWAY;
            }
            final double n = 8.0;
            final double n2 = 5.0;
            if (!this.worldObj.getEntitiesWithinAABB((Class<? extends Entity>)EntityMob.class, new AxisAlignedBB(playerLocation.getX() - n, playerLocation.getY() - n2, playerLocation.getZ() - n, playerLocation.getX() + n, playerLocation.getY() + n2, playerLocation.getZ() + n)).isEmpty()) {
                return EnumStatus.NOT_SAFE;
            }
        }
        if (this.isRiding()) {
            this.mountEntity(null);
        }
        this.setSize(0.2f, 0.2f);
        if (this.worldObj.isBlockLoaded(playerLocation)) {
            final EnumFacing enumFacing = this.worldObj.getBlockState(playerLocation).getValue((IProperty<EnumFacing>)BlockDirectional.FACING);
            float n3 = 0.5f;
            float n4 = 0.5f;
            switch ($SWITCH_TABLE$net$minecraft$util$EnumFacing()[enumFacing.ordinal()]) {
                case 4: {
                    n4 = 0.9f;
                    "".length();
                    if (3 == 0) {
                        throw null;
                    }
                    break;
                }
                case 3: {
                    n4 = 0.1f;
                    "".length();
                    if (4 != 4) {
                        throw null;
                    }
                    break;
                }
                case 5: {
                    n3 = 0.1f;
                    "".length();
                    if (-1 >= 1) {
                        throw null;
                    }
                    break;
                }
                case 6: {
                    n3 = 0.9f;
                    break;
                }
            }
            this.func_175139_a(enumFacing);
            this.setPosition(playerLocation.getX() + n3, playerLocation.getY() + 0.6875f, playerLocation.getZ() + n4);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        else {
            this.setPosition(playerLocation.getX() + 0.5f, playerLocation.getY() + 0.6875f, playerLocation.getZ() + 0.5f);
        }
        this.sleeping = (" ".length() != 0);
        this.sleepTimer = "".length();
        this.playerLocation = playerLocation;
        final double motionX = 0.0;
        this.motionY = motionX;
        this.motionZ = motionX;
        this.motionX = motionX;
        if (!this.worldObj.isRemote) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        return EnumStatus.OK;
    }
    
    public void displayVillagerTradeGui(final IMerchant merchant) {
    }
    
    @Override
    public float getAIMoveSpeed() {
        return (float)this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).getAttributeValue();
    }
    
    @Override
    public void updateRidden() {
        if (!this.worldObj.isRemote && this.isSneaking()) {
            this.mountEntity(null);
            this.setSneaking("".length() != 0);
            "".length();
            if (1 <= -1) {
                throw null;
            }
        }
        else {
            final double posX = this.posX;
            final double posY = this.posY;
            final double posZ = this.posZ;
            final float rotationYaw = this.rotationYaw;
            final float rotationPitch = this.rotationPitch;
            super.updateRidden();
            this.prevCameraYaw = this.cameraYaw;
            this.cameraYaw = 0.0f;
            this.addMountedMovementStat(this.posX - posX, this.posY - posY, this.posZ - posZ);
            if (this.ridingEntity instanceof EntityPig) {
                this.rotationPitch = rotationPitch;
                this.rotationYaw = rotationYaw;
                this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
            }
        }
    }
    
    private void collideWithPlayer(final Entity entity) {
        entity.onCollideWithPlayer(this);
    }
    
    public int getScore() {
        return this.dataWatcher.getWatchableObjectInt(0xB3 ^ 0xA1);
    }
    
    public boolean shouldHeal() {
        if (this.getHealth() > 0.0f && this.getHealth() < this.getMaxHealth()) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean isSpawnForced() {
        return this.spawnForced;
    }
    
    public GameProfile getGameProfile() {
        return this.gameProfile;
    }
    
    public InventoryEnderChest getInventoryEnderChest() {
        return this.theInventoryEnderChest;
    }
    
    protected void joinEntityItemWithWorld(final EntityItem entityItem) {
        this.worldObj.spawnEntityInWorld(entityItem);
    }
    
    @Override
    public void handleStatusUpdate(final byte b) {
        if (b == (0x5D ^ 0x54)) {
            this.onItemUseFinish();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        else if (b == (0x68 ^ 0x7F)) {
            this.hasReducedDebug = ("".length() != 0);
            "".length();
            if (1 < -1) {
                throw null;
            }
        }
        else if (b == (0xA0 ^ 0xB6)) {
            this.hasReducedDebug = (" ".length() != 0);
            "".length();
            if (3 < 1) {
                throw null;
            }
        }
        else {
            super.handleStatusUpdate(b);
        }
    }
    
    @Override
    public void moveEntityWithHeading(final float n, final float n2) {
        final double posX = this.posX;
        final double posY = this.posY;
        final double posZ = this.posZ;
        if (this.capabilities.isFlying && this.ridingEntity == null) {
            final double motionY = this.motionY;
            final float jumpMovementFactor = this.jumpMovementFactor;
            final float flySpeed = this.capabilities.getFlySpeed();
            int n3;
            if (this.isSprinting()) {
                n3 = "  ".length();
                "".length();
                if (3 == -1) {
                    throw null;
                }
            }
            else {
                n3 = " ".length();
            }
            this.jumpMovementFactor = flySpeed * n3;
            super.moveEntityWithHeading(n, n2);
            this.motionY = motionY * 0.6;
            this.jumpMovementFactor = jumpMovementFactor;
            "".length();
            if (4 == 0) {
                throw null;
            }
        }
        else {
            super.moveEntityWithHeading(n, n2);
        }
        this.addMovementStat(this.posX - posX, this.posY - posY, this.posZ - posZ);
    }
    
    static int[] $SWITCH_TABLE$net$minecraft$util$EnumFacing() {
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing = EntityPlayer.$SWITCH_TABLE$net$minecraft$util$EnumFacing;
        if ($switch_TABLE$net$minecraft$util$EnumFacing != null) {
            return $switch_TABLE$net$minecraft$util$EnumFacing;
        }
        final int[] $switch_TABLE$net$minecraft$util$EnumFacing2 = new int[EnumFacing.values().length];
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.DOWN.ordinal()] = " ".length();
            "".length();
            if (4 < -1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.EAST.ordinal()] = (0xA0 ^ 0xA6);
            "".length();
            if (0 < 0) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError2) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.NORTH.ordinal()] = "   ".length();
            "".length();
            if (0 == 4) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError3) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.SOUTH.ordinal()] = (0x4C ^ 0x48);
            "".length();
            if (false) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError4) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.UP.ordinal()] = "  ".length();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError5) {}
        try {
            $switch_TABLE$net$minecraft$util$EnumFacing2[EnumFacing.WEST.ordinal()] = (0x21 ^ 0x24);
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        catch (NoSuchFieldError noSuchFieldError6) {}
        return EntityPlayer.$SWITCH_TABLE$net$minecraft$util$EnumFacing = $switch_TABLE$net$minecraft$util$EnumFacing2;
    }
    
    @Override
    public int getTotalArmorValue() {
        return this.inventory.getTotalArmorValue();
    }
    
    public boolean canEat(final boolean b) {
        if ((b || this.foodStats.needFood()) && !this.capabilities.disableDamage) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }
    
    private void addMountedMovementStat(final double n, final double n2, final double n3) {
        if (this.ridingEntity != null) {
            final int round = Math.round(MathHelper.sqrt_double(n * n + n2 * n2 + n3 * n3) * 100.0f);
            if (round > 0) {
                if (this.ridingEntity instanceof EntityMinecart) {
                    this.addStat(StatList.distanceByMinecartStat, round);
                    if (this.startMinecartRidingCoordinate == null) {
                        this.startMinecartRidingCoordinate = new BlockPos(this);
                        "".length();
                        if (4 == 3) {
                            throw null;
                        }
                    }
                    else if (this.startMinecartRidingCoordinate.distanceSq(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0) {
                        this.triggerAchievement(AchievementList.onARail);
                        "".length();
                        if (false) {
                            throw null;
                        }
                    }
                }
                else if (this.ridingEntity instanceof EntityBoat) {
                    this.addStat(StatList.distanceByBoatStat, round);
                    "".length();
                    if (-1 == 0) {
                        throw null;
                    }
                }
                else if (this.ridingEntity instanceof EntityPig) {
                    this.addStat(StatList.distanceByPigStat, round);
                    "".length();
                    if (4 < 3) {
                        throw null;
                    }
                }
                else if (this.ridingEntity instanceof EntityHorse) {
                    this.addStat(StatList.distanceByHorseStat, round);
                }
            }
        }
    }
    
    @Override
    public void setInWeb() {
        if (!this.capabilities.isFlying) {
            super.setInWeb();
        }
    }
    
    public void setItemInUse(final ItemStack itemInUse, final int itemInUseCount) {
        if (itemInUse != this.itemInUse) {
            this.itemInUse = itemInUse;
            this.itemInUseCount = itemInUseCount;
            if (!this.worldObj.isRemote) {
                this.setEating(" ".length() != 0);
            }
        }
    }
    
    public void addScore(final int n) {
        this.dataWatcher.updateObject(0xD5 ^ 0xC7, this.getScore() + n);
    }
    
    @Override
    public void setAbsorptionAmount(float n) {
        if (n < 0.0f) {
            n = 0.0f;
        }
        this.getDataWatcher().updateObject(0x49 ^ 0x58, n);
    }
    
    public enum EnumChatVisibility
    {
        HIDDEN(EnumChatVisibility.I[0xB2 ^ 0xB6], "  ".length(), "  ".length(), EnumChatVisibility.I[0x96 ^ 0x93]), 
        SYSTEM(EnumChatVisibility.I["  ".length()], " ".length(), " ".length(), EnumChatVisibility.I["   ".length()]);
        
        private final int chatVisibility;
        
        FULL(EnumChatVisibility.I["".length()], "".length(), "".length(), EnumChatVisibility.I[" ".length()]);
        
        private static final EnumChatVisibility[] ID_LOOKUP;
        private static final EnumChatVisibility[] ENUM$VALUES;
        private static final String[] I;
        private final String resourceKey;
        
        private static void I() {
            (I = new String[0x77 ^ 0x71])["".length()] = I("\u000f7 \u0007", "IblKb");
            EnumChatVisibility.I[" ".length()] = I("!\n,+= \tv!:/\u000ev4;=\u0013:+>'\u000e!l4;\u00164", "NzXBR");
            EnumChatVisibility.I["  ".length()] = I("\u001b '\u0005\r\u0005", "HytQH");
            EnumChatVisibility.I["   ".length()] = I(". &\u0006\t/#|\f\u000e $|\u0019\u000f290\u0006\n($+A\u00158#&\n\u000b", "APRof");
            EnumChatVisibility.I[0x6A ^ 0x6E] = I("#-#,.%", "kdghk");
            EnumChatVisibility.I[0xBD ^ 0xB8] = I("\u00062\u0000*8\u00071Z ?\b6Z5>\u001a+\u0016*;\u00006\rm?\u0000&\u0010&9", "iBtCW");
        }
        
        public int getChatVisibility() {
            return this.chatVisibility;
        }
        
        public static EnumChatVisibility getEnumChatVisibility(final int n) {
            return EnumChatVisibility.ID_LOOKUP[n % EnumChatVisibility.ID_LOOKUP.length];
        }
        
        static {
            I();
            final EnumChatVisibility[] enum$VALUES = new EnumChatVisibility["   ".length()];
            enum$VALUES["".length()] = EnumChatVisibility.FULL;
            enum$VALUES[" ".length()] = EnumChatVisibility.SYSTEM;
            enum$VALUES["  ".length()] = EnumChatVisibility.HIDDEN;
            ENUM$VALUES = enum$VALUES;
            ID_LOOKUP = new EnumChatVisibility[values().length];
            final EnumChatVisibility[] values;
            final int length = (values = values()).length;
            int i = "".length();
            "".length();
            if (-1 != -1) {
                throw null;
            }
            while (i < length) {
                final EnumChatVisibility enumChatVisibility = values[i];
                EnumChatVisibility.ID_LOOKUP[enumChatVisibility.chatVisibility] = enumChatVisibility;
                ++i;
            }
        }
        
        private EnumChatVisibility(final String s, final int n, final int chatVisibility, final String resourceKey) {
            this.chatVisibility = chatVisibility;
            this.resourceKey = resourceKey;
        }
        
        public String getResourceKey() {
            return this.resourceKey;
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
    }
    
    public enum EnumStatus
    {
        NOT_POSSIBLE_HERE(EnumStatus.I[" ".length()], " ".length()), 
        TOO_FAR_AWAY(EnumStatus.I["   ".length()], "   ".length()), 
        OTHER_PROBLEM(EnumStatus.I[0x27 ^ 0x23], 0x6 ^ 0x2);
        
        private static final EnumStatus[] ENUM$VALUES;
        
        NOT_SAFE(EnumStatus.I[0x11 ^ 0x14], 0xB ^ 0xE);
        
        private static final String[] I;
        
        NOT_POSSIBLE_NOW(EnumStatus.I["  ".length()], "  ".length()), 
        OK(EnumStatus.I["".length()], "".length());
        
        private EnumStatus(final String s, final int n) {
        }
        
        private static void I() {
            (I = new String[0x6B ^ 0x6D])["".length()] = I("*\u0001", "eJhbF");
            EnumStatus.I[" ".length()] = I("'&'\u00051&: \u0013#%,,\u0012$;,", "iisZa");
            EnumStatus.I["  ".length()] = I(":)3\n\u0015;54\u001c\u00078#8\u001b\n#", "tfgUE");
            EnumStatus.I["   ".length()] = I("%\u0015)\f\u00140\b9\u0012\u00050\u0003", "qZfSR");
            EnumStatus.I[0x3F ^ 0x3B] = I("\u0003#\u0007\u00174\u0013'\u001d\u001d$\u00002\u0002", "LwORf");
            EnumStatus.I[0x14 ^ 0x11] = I("\u0016#\u0000/\u001a\u0019*\u0011", "XlTpI");
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
        
        static {
            I();
            final EnumStatus[] enum$VALUES = new EnumStatus[0x12 ^ 0x14];
            enum$VALUES["".length()] = EnumStatus.OK;
            enum$VALUES[" ".length()] = EnumStatus.NOT_POSSIBLE_HERE;
            enum$VALUES["  ".length()] = EnumStatus.NOT_POSSIBLE_NOW;
            enum$VALUES["   ".length()] = EnumStatus.TOO_FAR_AWAY;
            enum$VALUES[0x3B ^ 0x3F] = EnumStatus.OTHER_PROBLEM;
            enum$VALUES[0x17 ^ 0x12] = EnumStatus.NOT_SAFE;
            ENUM$VALUES = enum$VALUES;
        }
    }
}
