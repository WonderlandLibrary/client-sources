package net.minecraft.src;

import java.util.*;

public abstract class EntityPlayer extends EntityLiving implements ICommandSender
{
    public InventoryPlayer inventory;
    private InventoryEnderChest theInventoryEnderChest;
    public Container inventoryContainer;
    public Container openContainer;
    protected FoodStats foodStats;
    protected int flyToggleTimer;
    public byte field_71098_bD;
    public float prevCameraYaw;
    public float cameraYaw;
    public String username;
    public int xpCooldown;
    public double field_71091_bM;
    public double field_71096_bN;
    public double field_71097_bO;
    public double field_71094_bP;
    public double field_71095_bQ;
    public double field_71085_bR;
    protected boolean sleeping;
    public ChunkCoordinates playerLocation;
    private int sleepTimer;
    public float field_71079_bU;
    public float field_71082_cx;
    public float field_71089_bV;
    private ChunkCoordinates spawnChunk;
    private boolean spawnForced;
    private ChunkCoordinates startMinecartRidingCoordinate;
    public PlayerCapabilities capabilities;
    public int experienceLevel;
    public int experienceTotal;
    public float experience;
    private ItemStack itemInUse;
    private int itemInUseCount;
    protected float speedOnGround;
    protected float speedInAir;
    private int field_82249_h;
    public EntityFishHook fishEntity;
    
    public EntityPlayer(final World par1World) {
        super(par1World);
        this.inventory = new InventoryPlayer(this);
        this.theInventoryEnderChest = new InventoryEnderChest();
        this.foodStats = new FoodStats();
        this.flyToggleTimer = 0;
        this.field_71098_bD = 0;
        this.xpCooldown = 0;
        this.capabilities = new PlayerCapabilities();
        this.speedOnGround = 0.1f;
        this.speedInAir = 0.02f;
        this.field_82249_h = 0;
        this.fishEntity = null;
        this.inventoryContainer = new ContainerPlayer(this.inventory, !par1World.isRemote, this);
        this.openContainer = this.inventoryContainer;
        this.yOffset = 1.62f;
        final ChunkCoordinates var2 = par1World.getSpawnPoint();
        this.setLocationAndAngles(var2.posX + 0.5, var2.posY + 1, var2.posZ + 0.5, 0.0f, 0.0f);
        this.entityType = "humanoid";
        this.field_70741_aB = 180.0f;
        this.fireResistance = 20;
        this.texture = "/mob/char.png";
    }
    
    @Override
    public int getMaxHealth() {
        return 20;
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, (byte)0);
        this.dataWatcher.addObject(17, (byte)0);
        this.dataWatcher.addObject(18, 0);
    }
    
    public ItemStack getItemInUse() {
        return this.itemInUse;
    }
    
    public int getItemInUseCount() {
        return this.itemInUseCount;
    }
    
    public boolean isUsingItem() {
        return this.itemInUse != null;
    }
    
    public int getItemInUseDuration() {
        return this.isUsingItem() ? (this.itemInUse.getMaxItemUseDuration() - this.itemInUseCount) : 0;
    }
    
    public void stopUsingItem() {
        if (this.itemInUse != null) {
            this.itemInUse.onPlayerStoppedUsing(this.worldObj, this, this.itemInUseCount);
        }
        this.clearItemInUse();
    }
    
    public void clearItemInUse() {
        this.itemInUse = null;
        this.itemInUseCount = 0;
        if (!this.worldObj.isRemote) {
            this.setEating(false);
        }
    }
    
    @Override
    public boolean isBlocking() {
        return this.isUsingItem() && Item.itemsList[this.itemInUse.itemID].getItemUseAction(this.itemInUse) == EnumAction.block;
    }
    
    @Override
    public void onUpdate() {
        if (this.itemInUse != null) {
            final ItemStack var1 = this.inventory.getCurrentItem();
            if (var1 == this.itemInUse) {
                if (this.itemInUseCount <= 25 && this.itemInUseCount % 4 == 0) {
                    this.updateItemUse(var1, 5);
                }
                if (--this.itemInUseCount == 0 && !this.worldObj.isRemote) {
                    this.onItemUseFinish();
                }
            }
            else {
                this.clearItemInUse();
            }
        }
        if (this.xpCooldown > 0) {
            --this.xpCooldown;
        }
        if (this.isPlayerSleeping()) {
            ++this.sleepTimer;
            if (this.sleepTimer > 100) {
                this.sleepTimer = 100;
            }
            if (!this.worldObj.isRemote) {
                if (!this.isInBed()) {
                    this.wakeUpPlayer(true, true, false);
                }
                else if (this.worldObj.isDaytime()) {
                    this.wakeUpPlayer(false, true, true);
                }
            }
        }
        else if (this.sleepTimer > 0) {
            ++this.sleepTimer;
            if (this.sleepTimer >= 110) {
                this.sleepTimer = 0;
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
        this.field_71091_bM = this.field_71094_bP;
        this.field_71096_bN = this.field_71095_bQ;
        this.field_71097_bO = this.field_71085_bR;
        final double var2 = this.posX - this.field_71094_bP;
        final double var3 = this.posY - this.field_71095_bQ;
        final double var4 = this.posZ - this.field_71085_bR;
        final double var5 = 10.0;
        if (var2 > var5) {
            final double posX = this.posX;
            this.field_71094_bP = posX;
            this.field_71091_bM = posX;
        }
        if (var4 > var5) {
            final double posZ = this.posZ;
            this.field_71085_bR = posZ;
            this.field_71097_bO = posZ;
        }
        if (var3 > var5) {
            final double posY = this.posY;
            this.field_71095_bQ = posY;
            this.field_71096_bN = posY;
        }
        if (var2 < -var5) {
            final double posX2 = this.posX;
            this.field_71094_bP = posX2;
            this.field_71091_bM = posX2;
        }
        if (var4 < -var5) {
            final double posZ2 = this.posZ;
            this.field_71085_bR = posZ2;
            this.field_71097_bO = posZ2;
        }
        if (var3 < -var5) {
            final double posY2 = this.posY;
            this.field_71095_bQ = posY2;
            this.field_71096_bN = posY2;
        }
        this.field_71094_bP += var2 * 0.25;
        this.field_71085_bR += var4 * 0.25;
        this.field_71095_bQ += var3 * 0.25;
        this.addStat(StatList.minutesPlayedStat, 1);
        if (this.ridingEntity == null) {
            this.startMinecartRidingCoordinate = null;
        }
        if (!this.worldObj.isRemote) {
            this.foodStats.onUpdate(this);
        }
    }
    
    @Override
    public int getMaxInPortalTime() {
        return this.capabilities.disableDamage ? 0 : 80;
    }
    
    @Override
    public int getPortalCooldown() {
        return 10;
    }
    
    @Override
    public void playSound(final String par1Str, final float par2, final float par3) {
        this.worldObj.playSoundToNearExcept(this, par1Str, par2, par3);
    }
    
    protected void updateItemUse(final ItemStack par1ItemStack, final int par2) {
        if (par1ItemStack.getItemUseAction() == EnumAction.drink) {
            this.playSound("random.drink", 0.5f, this.worldObj.rand.nextFloat() * 0.1f + 0.9f);
        }
        if (par1ItemStack.getItemUseAction() == EnumAction.eat) {
            for (int var3 = 0; var3 < par2; ++var3) {
                final Vec3 var4 = this.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5) * 0.1, Math.random() * 0.1 + 0.1, 0.0);
                var4.rotateAroundX(-this.rotationPitch * 3.1415927f / 180.0f);
                var4.rotateAroundY(-this.rotationYaw * 3.1415927f / 180.0f);
                Vec3 var5 = this.worldObj.getWorldVec3Pool().getVecFromPool((this.rand.nextFloat() - 0.5) * 0.3, -this.rand.nextFloat() * 0.6 - 0.3, 0.6);
                var5.rotateAroundX(-this.rotationPitch * 3.1415927f / 180.0f);
                var5.rotateAroundY(-this.rotationYaw * 3.1415927f / 180.0f);
                var5 = var5.addVector(this.posX, this.posY + this.getEyeHeight(), this.posZ);
                this.worldObj.spawnParticle("iconcrack_" + par1ItemStack.getItem().itemID, var5.xCoord, var5.yCoord, var5.zCoord, var4.xCoord, var4.yCoord + 0.05, var4.zCoord);
            }
            this.playSound("random.eat", 0.5f + 0.5f * this.rand.nextInt(2), (this.rand.nextFloat() - this.rand.nextFloat()) * 0.2f + 1.0f);
        }
    }
    
    protected void onItemUseFinish() {
        if (this.itemInUse != null) {
            this.updateItemUse(this.itemInUse, 16);
            final int var1 = this.itemInUse.stackSize;
            final ItemStack var2 = this.itemInUse.onFoodEaten(this.worldObj, this);
            if (var2 != this.itemInUse || (var2 != null && var2.stackSize != var1)) {
                this.inventory.mainInventory[this.inventory.currentItem] = var2;
                if (var2.stackSize == 0) {
                    this.inventory.mainInventory[this.inventory.currentItem] = null;
                }
            }
            this.clearItemInUse();
        }
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 9) {
            this.onItemUseFinish();
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    @Override
    protected boolean isMovementBlocked() {
        return this.getHealth() <= 0 || this.isPlayerSleeping();
    }
    
    protected void closeScreen() {
        this.openContainer = this.inventoryContainer;
    }
    
    @Override
    public void mountEntity(final Entity par1Entity) {
        if (this.ridingEntity == par1Entity) {
            this.unmountEntity(par1Entity);
            if (this.ridingEntity != null) {
                this.ridingEntity.riddenByEntity = null;
            }
            this.ridingEntity = null;
        }
        else {
            super.mountEntity(par1Entity);
        }
    }
    
    @Override
    public void updateRidden() {
        final double var1 = this.posX;
        final double var2 = this.posY;
        final double var3 = this.posZ;
        final float var4 = this.rotationYaw;
        final float var5 = this.rotationPitch;
        super.updateRidden();
        this.prevCameraYaw = this.cameraYaw;
        this.cameraYaw = 0.0f;
        this.addMountedMovementStat(this.posX - var1, this.posY - var2, this.posZ - var3);
        if (this.ridingEntity instanceof EntityPig) {
            this.rotationPitch = var5;
            this.rotationYaw = var4;
            this.renderYawOffset = ((EntityPig)this.ridingEntity).renderYawOffset;
        }
    }
    
    public void preparePlayerToSpawn() {
        this.yOffset = 1.62f;
        this.setSize(0.6f, 1.8f);
        super.preparePlayerToSpawn();
        this.setEntityHealth(this.getMaxHealth());
        this.deathTime = 0;
    }
    
    @Override
    protected void updateEntityActionState() {
        this.updateArmSwingProgress();
    }
    
    @Override
    public void onLivingUpdate() {
        if (this.flyToggleTimer > 0) {
            --this.flyToggleTimer;
        }
        if (this.worldObj.difficultySetting == 0 && this.getHealth() < this.getMaxHealth() && this.ticksExisted % 20 * 12 == 0) {
            this.heal(1);
        }
        this.inventory.decrementAnimations();
        this.prevCameraYaw = this.cameraYaw;
        super.onLivingUpdate();
        this.landMovementFactor = this.capabilities.getWalkSpeed();
        this.jumpMovementFactor = this.speedInAir;
        if (this.isSprinting()) {
            this.landMovementFactor += (float)(this.capabilities.getWalkSpeed() * 0.3);
            this.jumpMovementFactor += (float)(this.speedInAir * 0.3);
        }
        float var1 = MathHelper.sqrt_double(this.motionX * this.motionX + this.motionZ * this.motionZ);
        float var2 = (float)Math.atan(-this.motionY * 0.20000000298023224) * 15.0f;
        if (var1 > 0.1f) {
            var1 = 0.1f;
        }
        if (!this.onGround || this.getHealth() <= 0) {
            var1 = 0.0f;
        }
        if (this.onGround || this.getHealth() <= 0) {
            var2 = 0.0f;
        }
        this.cameraYaw += (var1 - this.cameraYaw) * 0.4f;
        this.cameraPitch += (var2 - this.cameraPitch) * 0.8f;
        if (this.getHealth() > 0) {
            final List var3 = this.worldObj.getEntitiesWithinAABBExcludingEntity(this, this.boundingBox.expand(1.0, 0.5, 1.0));
            if (var3 != null) {
                for (int var4 = 0; var4 < var3.size(); ++var4) {
                    final Entity var5 = var3.get(var4);
                    if (!var5.isDead) {
                        this.collideWithPlayer(var5);
                    }
                }
            }
        }
    }
    
    private void collideWithPlayer(final Entity par1Entity) {
        par1Entity.onCollideWithPlayer(this);
    }
    
    public int getScore() {
        return this.dataWatcher.getWatchableObjectInt(18);
    }
    
    public void setScore(final int par1) {
        this.dataWatcher.updateObject(18, par1);
    }
    
    public void addScore(final int par1) {
        final int var2 = this.getScore();
        this.dataWatcher.updateObject(18, var2 + par1);
    }
    
    @Override
    public void onDeath(final DamageSource par1DamageSource) {
        super.onDeath(par1DamageSource);
        this.setSize(0.2f, 0.2f);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionY = 0.10000000149011612;
        if (this.username.equals("Notch")) {
            this.dropPlayerItemWithRandomChoice(new ItemStack(Item.appleRed, 1), true);
        }
        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }
        if (par1DamageSource != null) {
            this.motionX = -MathHelper.cos((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
            this.motionZ = -MathHelper.sin((this.attackedAtYaw + this.rotationYaw) * 3.1415927f / 180.0f) * 0.1f;
        }
        else {
            final double n = 0.0;
            this.motionZ = n;
            this.motionX = n;
        }
        this.yOffset = 0.1f;
        this.addStat(StatList.deathsStat, 1);
    }
    
    @Override
    public void addToPlayerScore(final Entity par1Entity, final int par2) {
        this.addScore(par2);
        final Collection var3 = this.getWorldScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96640_e);
        if (par1Entity instanceof EntityPlayer) {
            this.addStat(StatList.playerKillsStat, 1);
            var3.addAll(this.getWorldScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96639_d));
        }
        else {
            this.addStat(StatList.mobKillsStat, 1);
        }
        for (final ScoreObjective var5 : var3) {
            final Score var6 = this.getWorldScoreboard().func_96529_a(this.getEntityName(), var5);
            var6.func_96648_a();
        }
    }
    
    public EntityItem dropOneItem(final boolean par1) {
        return this.dropPlayerItemWithRandomChoice(this.inventory.decrStackSize(this.inventory.currentItem, (par1 && this.inventory.getCurrentItem() != null) ? this.inventory.getCurrentItem().stackSize : 1), false);
    }
    
    public EntityItem dropPlayerItem(final ItemStack par1ItemStack) {
        return this.dropPlayerItemWithRandomChoice(par1ItemStack, false);
    }
    
    public EntityItem dropPlayerItemWithRandomChoice(final ItemStack par1ItemStack, final boolean par2) {
        if (par1ItemStack == null) {
            return null;
        }
        final EntityItem var3 = new EntityItem(this.worldObj, this.posX, this.posY - 0.30000001192092896 + this.getEyeHeight(), this.posZ, par1ItemStack);
        var3.delayBeforeCanPickup = 40;
        float var4 = 0.1f;
        if (par2) {
            final float var5 = this.rand.nextFloat() * 0.5f;
            final float var6 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            var3.motionX = -MathHelper.sin(var6) * var5;
            var3.motionZ = MathHelper.cos(var6) * var5;
            var3.motionY = 0.20000000298023224;
        }
        else {
            var4 = 0.3f;
            var3.motionX = -MathHelper.sin(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var4;
            var3.motionZ = MathHelper.cos(this.rotationYaw / 180.0f * 3.1415927f) * MathHelper.cos(this.rotationPitch / 180.0f * 3.1415927f) * var4;
            var3.motionY = -MathHelper.sin(this.rotationPitch / 180.0f * 3.1415927f) * var4 + 0.1f;
            var4 = 0.02f;
            final float var5 = this.rand.nextFloat() * 3.1415927f * 2.0f;
            var4 *= this.rand.nextFloat();
            final EntityItem entityItem = var3;
            entityItem.motionX += Math.cos(var5) * var4;
            final EntityItem entityItem2 = var3;
            entityItem2.motionY += (this.rand.nextFloat() - this.rand.nextFloat()) * 0.1f;
            final EntityItem entityItem3 = var3;
            entityItem3.motionZ += Math.sin(var5) * var4;
        }
        this.joinEntityItemWithWorld(var3);
        this.addStat(StatList.dropStat, 1);
        return var3;
    }
    
    protected void joinEntityItemWithWorld(final EntityItem par1EntityItem) {
        this.worldObj.spawnEntityInWorld(par1EntityItem);
    }
    
    public float getCurrentPlayerStrVsBlock(final Block par1Block, final boolean par2) {
        float var3 = this.inventory.getStrVsBlock(par1Block);
        if (var3 > 1.0f) {
            final int var4 = EnchantmentHelper.getEfficiencyModifier(this);
            final ItemStack var5 = this.inventory.getCurrentItem();
            if (var4 > 0 && var5 != null) {
                final float var6 = var4 * var4 + 1;
                if (!var5.canHarvestBlock(par1Block) && var3 <= 1.0f) {
                    var3 += var6 * 0.08f;
                }
                else {
                    var3 += var6;
                }
            }
        }
        if (this.isPotionActive(Potion.digSpeed)) {
            var3 *= 1.0f + (this.getActivePotionEffect(Potion.digSpeed).getAmplifier() + 1) * 0.2f;
        }
        if (this.isPotionActive(Potion.digSlowdown)) {
            var3 *= 1.0f - (this.getActivePotionEffect(Potion.digSlowdown).getAmplifier() + 1) * 0.2f;
        }
        if (this.isInsideOfMaterial(Material.water) && !EnchantmentHelper.getAquaAffinityModifier(this)) {
            var3 /= 5.0f;
        }
        if (!this.onGround) {
            var3 /= 5.0f;
        }
        return var3;
    }
    
    public boolean canHarvestBlock(final Block par1Block) {
        return this.inventory.canHarvestBlock(par1Block);
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        final NBTTagList var2 = par1NBTTagCompound.getTagList("Inventory");
        this.inventory.readFromNBT(var2);
        this.inventory.currentItem = par1NBTTagCompound.getInteger("SelectedItemSlot");
        this.sleeping = par1NBTTagCompound.getBoolean("Sleeping");
        this.sleepTimer = par1NBTTagCompound.getShort("SleepTimer");
        this.experience = par1NBTTagCompound.getFloat("XpP");
        this.experienceLevel = par1NBTTagCompound.getInteger("XpLevel");
        this.experienceTotal = par1NBTTagCompound.getInteger("XpTotal");
        this.setScore(par1NBTTagCompound.getInteger("Score"));
        if (this.sleeping) {
            this.playerLocation = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.wakeUpPlayer(true, true, false);
        }
        if (par1NBTTagCompound.hasKey("SpawnX") && par1NBTTagCompound.hasKey("SpawnY") && par1NBTTagCompound.hasKey("SpawnZ")) {
            this.spawnChunk = new ChunkCoordinates(par1NBTTagCompound.getInteger("SpawnX"), par1NBTTagCompound.getInteger("SpawnY"), par1NBTTagCompound.getInteger("SpawnZ"));
            this.spawnForced = par1NBTTagCompound.getBoolean("SpawnForced");
        }
        this.foodStats.readNBT(par1NBTTagCompound);
        this.capabilities.readCapabilitiesFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("EnderItems")) {
            final NBTTagList var3 = par1NBTTagCompound.getTagList("EnderItems");
            this.theInventoryEnderChest.loadInventoryFromNBT(var3);
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("Inventory", this.inventory.writeToNBT(new NBTTagList()));
        par1NBTTagCompound.setInteger("SelectedItemSlot", this.inventory.currentItem);
        par1NBTTagCompound.setBoolean("Sleeping", this.sleeping);
        par1NBTTagCompound.setShort("SleepTimer", (short)this.sleepTimer);
        par1NBTTagCompound.setFloat("XpP", this.experience);
        par1NBTTagCompound.setInteger("XpLevel", this.experienceLevel);
        par1NBTTagCompound.setInteger("XpTotal", this.experienceTotal);
        par1NBTTagCompound.setInteger("Score", this.getScore());
        if (this.spawnChunk != null) {
            par1NBTTagCompound.setInteger("SpawnX", this.spawnChunk.posX);
            par1NBTTagCompound.setInteger("SpawnY", this.spawnChunk.posY);
            par1NBTTagCompound.setInteger("SpawnZ", this.spawnChunk.posZ);
            par1NBTTagCompound.setBoolean("SpawnForced", this.spawnForced);
        }
        this.foodStats.writeNBT(par1NBTTagCompound);
        this.capabilities.writeCapabilitiesToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setTag("EnderItems", this.theInventoryEnderChest.saveInventoryToNBT());
    }
    
    public void displayGUIChest(final IInventory par1IInventory) {
    }
    
    public void displayGUIHopper(final TileEntityHopper par1TileEntityHopper) {
    }
    
    public void displayGUIHopperMinecart(final EntityMinecartHopper par1EntityMinecartHopper) {
    }
    
    public void displayGUIEnchantment(final int par1, final int par2, final int par3, final String par4Str) {
    }
    
    public void displayGUIAnvil(final int par1, final int par2, final int par3) {
    }
    
    public void displayGUIWorkbench(final int par1, final int par2, final int par3) {
    }
    
    @Override
    public float getEyeHeight() {
        return 0.12f;
    }
    
    protected void resetHeight() {
        this.yOffset = 1.62f;
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        if (this.capabilities.disableDamage && !par1DamageSource.canHarmInCreative()) {
            return false;
        }
        this.entityAge = 0;
        if (this.getHealth() <= 0) {
            return false;
        }
        if (this.isPlayerSleeping() && !this.worldObj.isRemote) {
            this.wakeUpPlayer(true, true, false);
        }
        if (par1DamageSource.isDifficultyScaled()) {
            if (this.worldObj.difficultySetting == 0) {
                par2 = 0;
            }
            if (this.worldObj.difficultySetting == 1) {
                par2 = par2 / 2 + 1;
            }
            if (this.worldObj.difficultySetting == 3) {
                par2 = par2 * 3 / 2;
            }
        }
        if (par2 == 0) {
            return false;
        }
        Entity var3 = par1DamageSource.getEntity();
        if (var3 instanceof EntityArrow && ((EntityArrow)var3).shootingEntity != null) {
            var3 = ((EntityArrow)var3).shootingEntity;
        }
        if (var3 instanceof EntityLiving) {
            this.alertWolves((EntityLiving)var3, false);
        }
        this.addStat(StatList.damageTakenStat, par2);
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    public boolean func_96122_a(final EntityPlayer par1EntityPlayer) {
        final ScorePlayerTeam var2 = this.getTeam();
        final ScorePlayerTeam var3 = par1EntityPlayer.getTeam();
        return var2 != var3 || var2 == null || var2.func_96665_g();
    }
    
    protected void alertWolves(final EntityLiving par1EntityLiving, final boolean par2) {
        if (!(par1EntityLiving instanceof EntityCreeper) && !(par1EntityLiving instanceof EntityGhast)) {
            if (par1EntityLiving instanceof EntityWolf) {
                final EntityWolf var3 = (EntityWolf)par1EntityLiving;
                if (var3.isTamed() && this.username.equals(var3.getOwnerName())) {
                    return;
                }
            }
            if (!(par1EntityLiving instanceof EntityPlayer) || this.func_96122_a((EntityPlayer)par1EntityLiving)) {
                final List var4 = this.worldObj.getEntitiesWithinAABB(EntityWolf.class, AxisAlignedBB.getAABBPool().getAABB(this.posX, this.posY, this.posZ, this.posX + 1.0, this.posY + 1.0, this.posZ + 1.0).expand(16.0, 4.0, 16.0));
                for (final EntityWolf var6 : var4) {
                    if (var6.isTamed() && var6.getEntityToAttack() == null && this.username.equals(var6.getOwnerName()) && (!par2 || !var6.isSitting())) {
                        var6.setSitting(false);
                        var6.setTarget(par1EntityLiving);
                    }
                }
            }
        }
    }
    
    @Override
    protected void damageArmor(final int par1) {
        this.inventory.damageArmor(par1);
    }
    
    @Override
    public int getTotalArmorValue() {
        return this.inventory.getTotalArmorValue();
    }
    
    public float func_82243_bO() {
        int var1 = 0;
        for (final ItemStack var5 : this.inventory.armorInventory) {
            if (var5 != null) {
                ++var1;
            }
        }
        return var1 / this.inventory.armorInventory.length;
    }
    
    @Override
    protected void damageEntity(final DamageSource par1DamageSource, int par2) {
        if (!this.isEntityInvulnerable()) {
            if (!par1DamageSource.isUnblockable() && this.isBlocking()) {
                par2 = 1 + par2 >> 1;
            }
            par2 = this.applyArmorCalculations(par1DamageSource, par2);
            par2 = this.applyPotionDamageCalculations(par1DamageSource, par2);
            this.addExhaustion(par1DamageSource.getHungerDamage());
            final int var3 = this.getHealth();
            this.setEntityHealth(this.getHealth() - par2);
            this.field_94063_bt.func_94547_a(par1DamageSource, var3, par2);
        }
    }
    
    public void displayGUIFurnace(final TileEntityFurnace par1TileEntityFurnace) {
    }
    
    public void displayGUIDispenser(final TileEntityDispenser par1TileEntityDispenser) {
    }
    
    public void displayGUIEditSign(final TileEntity par1TileEntity) {
    }
    
    public void displayGUIBrewingStand(final TileEntityBrewingStand par1TileEntityBrewingStand) {
    }
    
    public void displayGUIBeacon(final TileEntityBeacon par1TileEntityBeacon) {
    }
    
    public void displayGUIMerchant(final IMerchant par1IMerchant, final String par2Str) {
    }
    
    public void displayGUIBook(final ItemStack par1ItemStack) {
    }
    
    public boolean interactWith(final Entity par1Entity) {
        if (par1Entity.interact(this)) {
            return true;
        }
        ItemStack var2 = this.getCurrentEquippedItem();
        if (var2 != null && par1Entity instanceof EntityLiving) {
            if (this.capabilities.isCreativeMode) {
                var2 = var2.copy();
            }
            if (var2.interactWith((EntityLiving)par1Entity)) {
                if (var2.stackSize <= 0 && !this.capabilities.isCreativeMode) {
                    this.destroyCurrentEquippedItem();
                }
                return true;
            }
        }
        return false;
    }
    
    public ItemStack getCurrentEquippedItem() {
        return this.inventory.getCurrentItem();
    }
    
    public void destroyCurrentEquippedItem() {
        this.inventory.setInventorySlotContents(this.inventory.currentItem, null);
    }
    
    @Override
    public double getYOffset() {
        return this.yOffset - 0.5f;
    }
    
    public void attackTargetEntityWithCurrentItem(final Entity par1Entity) {
        if (par1Entity.canAttackWithItem() && !par1Entity.func_85031_j(this)) {
            int var2 = this.inventory.getDamageVsEntity(par1Entity);
            if (this.isPotionActive(Potion.damageBoost)) {
                var2 += 3 << this.getActivePotionEffect(Potion.damageBoost).getAmplifier();
            }
            if (this.isPotionActive(Potion.weakness)) {
                var2 -= 2 << this.getActivePotionEffect(Potion.weakness).getAmplifier();
            }
            int var3 = 0;
            int var4 = 0;
            if (par1Entity instanceof EntityLiving) {
                var4 = EnchantmentHelper.getEnchantmentModifierLiving(this, (EntityLiving)par1Entity);
                var3 += EnchantmentHelper.getKnockbackModifier(this, (EntityLiving)par1Entity);
            }
            if (this.isSprinting()) {
                ++var3;
            }
            if (var2 > 0 || var4 > 0) {
                final boolean var5 = this.fallDistance > 0.0f && !this.onGround && !this.isOnLadder() && !this.isInWater() && !this.isPotionActive(Potion.blindness) && this.ridingEntity == null && par1Entity instanceof EntityLiving;
                if (var5 && var2 > 0) {
                    var2 += this.rand.nextInt(var2 / 2 + 2);
                }
                var2 += var4;
                boolean var6 = false;
                final int var7 = EnchantmentHelper.getFireAspectModifier(this);
                if (par1Entity instanceof EntityLiving && var7 > 0 && !par1Entity.isBurning()) {
                    var6 = true;
                    par1Entity.setFire(1);
                }
                final boolean var8 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(this), var2);
                if (var8) {
                    if (var3 > 0) {
                        par1Entity.addVelocity(-MathHelper.sin(this.rotationYaw * 3.1415927f / 180.0f) * var3 * 0.5f, 0.1, MathHelper.cos(this.rotationYaw * 3.1415927f / 180.0f) * var3 * 0.5f);
                        this.motionX *= 0.6;
                        this.motionZ *= 0.6;
                        this.setSprinting(false);
                    }
                    if (var5) {
                        this.onCriticalHit(par1Entity);
                    }
                    if (var4 > 0) {
                        this.onEnchantmentCritical(par1Entity);
                    }
                    if (var2 >= 18) {
                        this.triggerAchievement(AchievementList.overkill);
                    }
                    this.setLastAttackingEntity(par1Entity);
                    if (par1Entity instanceof EntityLiving) {
                        EnchantmentThorns.func_92096_a(this, (EntityLiving)par1Entity, this.rand);
                    }
                }
                final ItemStack var9 = this.getCurrentEquippedItem();
                Object var10 = par1Entity;
                if (par1Entity instanceof EntityDragonPart) {
                    final IEntityMultiPart var11 = ((EntityDragonPart)par1Entity).entityDragonObj;
                    if (var11 != null && var11 instanceof EntityLiving) {
                        var10 = var11;
                    }
                }
                if (var9 != null && var10 instanceof EntityLiving) {
                    var9.hitEntity((EntityLiving)var10, this);
                    if (var9.stackSize <= 0) {
                        this.destroyCurrentEquippedItem();
                    }
                }
                if (par1Entity instanceof EntityLiving) {
                    if (par1Entity.isEntityAlive()) {
                        this.alertWolves((EntityLiving)par1Entity, true);
                    }
                    this.addStat(StatList.damageDealtStat, var2);
                    if (var7 > 0 && var8) {
                        par1Entity.setFire(var7 * 4);
                    }
                    else if (var6) {
                        par1Entity.extinguish();
                    }
                }
                this.addExhaustion(0.3f);
            }
        }
    }
    
    public void onCriticalHit(final Entity par1Entity) {
    }
    
    public void onEnchantmentCritical(final Entity par1Entity) {
    }
    
    public void respawnPlayer() {
    }
    
    @Override
    public void setDead() {
        super.setDead();
        this.inventoryContainer.onCraftGuiClosed(this);
        if (this.openContainer != null) {
            this.openContainer.onCraftGuiClosed(this);
        }
    }
    
    @Override
    public boolean isEntityInsideOpaqueBlock() {
        return !this.sleeping && super.isEntityInsideOpaqueBlock();
    }
    
    public boolean func_71066_bF() {
        return false;
    }
    
    public EnumStatus sleepInBedAt(final int par1, final int par2, final int par3) {
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
            if (Math.abs(this.posX - par1) > 3.0 || Math.abs(this.posY - par2) > 2.0 || Math.abs(this.posZ - par3) > 3.0) {
                return EnumStatus.TOO_FAR_AWAY;
            }
            final double var4 = 8.0;
            final double var5 = 5.0;
            final List var6 = this.worldObj.getEntitiesWithinAABB(EntityMob.class, AxisAlignedBB.getAABBPool().getAABB(par1 - var4, par2 - var5, par3 - var4, par1 + var4, par2 + var5, par3 + var4));
            if (!var6.isEmpty()) {
                return EnumStatus.NOT_SAFE;
            }
        }
        this.setSize(0.2f, 0.2f);
        this.yOffset = 0.2f;
        if (this.worldObj.blockExists(par1, par2, par3)) {
            final int var7 = this.worldObj.getBlockMetadata(par1, par2, par3);
            final int var8 = BlockDirectional.getDirection(var7);
            float var9 = 0.5f;
            float var10 = 0.5f;
            switch (var8) {
                case 0: {
                    var10 = 0.9f;
                    break;
                }
                case 1: {
                    var9 = 0.1f;
                    break;
                }
                case 2: {
                    var10 = 0.1f;
                    break;
                }
                case 3: {
                    var9 = 0.9f;
                    break;
                }
            }
            this.func_71013_b(var8);
            this.setPosition(par1 + var9, par2 + 0.9375f, par3 + var10);
        }
        else {
            this.setPosition(par1 + 0.5f, par2 + 0.9375f, par3 + 0.5f);
        }
        this.sleeping = true;
        this.sleepTimer = 0;
        this.playerLocation = new ChunkCoordinates(par1, par2, par3);
        final double motionX = 0.0;
        this.motionY = motionX;
        this.motionZ = motionX;
        this.motionX = motionX;
        if (!this.worldObj.isRemote) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        return EnumStatus.OK;
    }
    
    private void func_71013_b(final int par1) {
        this.field_71079_bU = 0.0f;
        this.field_71089_bV = 0.0f;
        switch (par1) {
            case 0: {
                this.field_71089_bV = -1.8f;
                break;
            }
            case 1: {
                this.field_71079_bU = 1.8f;
                break;
            }
            case 2: {
                this.field_71089_bV = 1.8f;
                break;
            }
            case 3: {
                this.field_71079_bU = -1.8f;
                break;
            }
        }
    }
    
    public void wakeUpPlayer(final boolean par1, final boolean par2, final boolean par3) {
        this.setSize(0.6f, 1.8f);
        this.resetHeight();
        final ChunkCoordinates var4 = this.playerLocation;
        ChunkCoordinates var5 = this.playerLocation;
        if (var4 != null && this.worldObj.getBlockId(var4.posX, var4.posY, var4.posZ) == Block.bed.blockID) {
            BlockBed.setBedOccupied(this.worldObj, var4.posX, var4.posY, var4.posZ, false);
            var5 = BlockBed.getNearestEmptyChunkCoordinates(this.worldObj, var4.posX, var4.posY, var4.posZ, 0);
            if (var5 == null) {
                var5 = new ChunkCoordinates(var4.posX, var4.posY + 1, var4.posZ);
            }
            this.setPosition(var5.posX + 0.5f, var5.posY + this.yOffset + 0.1f, var5.posZ + 0.5f);
        }
        this.sleeping = false;
        if (!this.worldObj.isRemote && par2) {
            this.worldObj.updateAllPlayersSleepingFlag();
        }
        if (par1) {
            this.sleepTimer = 0;
        }
        else {
            this.sleepTimer = 100;
        }
        if (par3) {
            this.setSpawnChunk(this.playerLocation, false);
        }
    }
    
    private boolean isInBed() {
        return this.worldObj.getBlockId(this.playerLocation.posX, this.playerLocation.posY, this.playerLocation.posZ) == Block.bed.blockID;
    }
    
    public static ChunkCoordinates verifyRespawnCoordinates(final World par0World, final ChunkCoordinates par1ChunkCoordinates, final boolean par2) {
        final IChunkProvider var3 = par0World.getChunkProvider();
        var3.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
        var3.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ - 3 >> 4);
        var3.loadChunk(par1ChunkCoordinates.posX - 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);
        var3.loadChunk(par1ChunkCoordinates.posX + 3 >> 4, par1ChunkCoordinates.posZ + 3 >> 4);
        if (par0World.getBlockId(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ) == Block.bed.blockID) {
            final ChunkCoordinates var4 = BlockBed.getNearestEmptyChunkCoordinates(par0World, par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ, 0);
            return var4;
        }
        final Material var5 = par0World.getBlockMaterial(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY, par1ChunkCoordinates.posZ);
        final Material var6 = par0World.getBlockMaterial(par1ChunkCoordinates.posX, par1ChunkCoordinates.posY + 1, par1ChunkCoordinates.posZ);
        final boolean var7 = !var5.isSolid() && !var5.isLiquid();
        final boolean var8 = !var6.isSolid() && !var6.isLiquid();
        return (par2 && var7 && var8) ? par1ChunkCoordinates : null;
    }
    
    public float getBedOrientationInDegrees() {
        if (this.playerLocation != null) {
            final int var1 = this.worldObj.getBlockMetadata(this.playerLocation.posX, this.playerLocation.posY, this.playerLocation.posZ);
            final int var2 = BlockDirectional.getDirection(var1);
            switch (var2) {
                case 0: {
                    return 90.0f;
                }
                case 1: {
                    return 0.0f;
                }
                case 2: {
                    return 270.0f;
                }
                case 3: {
                    return 180.0f;
                }
            }
        }
        return 0.0f;
    }
    
    @Override
    public boolean isPlayerSleeping() {
        return this.sleeping;
    }
    
    public boolean isPlayerFullyAsleep() {
        return this.sleeping && this.sleepTimer >= 100;
    }
    
    public int getSleepTimer() {
        return this.sleepTimer;
    }
    
    protected boolean getHideCape(final int par1) {
        return (this.dataWatcher.getWatchableObjectByte(16) & 1 << par1) != 0x0;
    }
    
    protected void setHideCape(final int par1, final boolean par2) {
        final byte var3 = this.dataWatcher.getWatchableObjectByte(16);
        if (par2) {
            this.dataWatcher.updateObject(16, (byte)(var3 | 1 << par1));
        }
        else {
            this.dataWatcher.updateObject(16, (byte)(var3 & ~(1 << par1)));
        }
    }
    
    public void addChatMessage(final String par1Str) {
    }
    
    public ChunkCoordinates getBedLocation() {
        return this.spawnChunk;
    }
    
    public boolean isSpawnForced() {
        return this.spawnForced;
    }
    
    public void setSpawnChunk(final ChunkCoordinates par1ChunkCoordinates, final boolean par2) {
        if (par1ChunkCoordinates != null) {
            this.spawnChunk = new ChunkCoordinates(par1ChunkCoordinates);
            this.spawnForced = par2;
        }
        else {
            this.spawnChunk = null;
            this.spawnForced = false;
        }
    }
    
    public void triggerAchievement(final StatBase par1StatBase) {
        this.addStat(par1StatBase, 1);
    }
    
    public void addStat(final StatBase par1StatBase, final int par2) {
    }
    
    @Override
    protected void jump() {
        super.jump();
        this.addStat(StatList.jumpStat, 1);
        if (this.isSprinting()) {
            this.addExhaustion(0.8f);
        }
        else {
            this.addExhaustion(0.2f);
        }
    }
    
    @Override
    public void moveEntityWithHeading(final float par1, final float par2) {
        final double var3 = this.posX;
        final double var4 = this.posY;
        final double var5 = this.posZ;
        if (this.capabilities.isFlying && this.ridingEntity == null) {
            final double var6 = this.motionY;
            final float var7 = this.jumpMovementFactor;
            this.jumpMovementFactor = this.capabilities.getFlySpeed();
            super.moveEntityWithHeading(par1, par2);
            this.motionY = var6 * 0.6;
            this.jumpMovementFactor = var7;
        }
        else {
            super.moveEntityWithHeading(par1, par2);
        }
        this.addMovementStat(this.posX - var3, this.posY - var4, this.posZ - var5);
    }
    
    public void addMovementStat(final double par1, final double par3, final double par5) {
        if (this.ridingEntity == null) {
            if (this.isInsideOfMaterial(Material.water)) {
                final int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100.0f);
                if (var7 > 0) {
                    this.addStat(StatList.distanceDoveStat, var7);
                    this.addExhaustion(0.015f * var7 * 0.01f);
                }
            }
            else if (this.isInWater()) {
                final int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0f);
                if (var7 > 0) {
                    this.addStat(StatList.distanceSwumStat, var7);
                    this.addExhaustion(0.015f * var7 * 0.01f);
                }
            }
            else if (this.isOnLadder()) {
                if (par3 > 0.0) {
                    this.addStat(StatList.distanceClimbedStat, (int)Math.round(par3 * 100.0));
                }
            }
            else if (this.onGround) {
                final int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0f);
                if (var7 > 0) {
                    this.addStat(StatList.distanceWalkedStat, var7);
                    if (this.isSprinting()) {
                        this.addExhaustion(0.099999994f * var7 * 0.01f);
                    }
                    else {
                        this.addExhaustion(0.01f * var7 * 0.01f);
                    }
                }
            }
            else {
                final int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par5 * par5) * 100.0f);
                if (var7 > 25) {
                    this.addStat(StatList.distanceFlownStat, var7);
                }
            }
        }
    }
    
    private void addMountedMovementStat(final double par1, final double par3, final double par5) {
        if (this.ridingEntity != null) {
            final int var7 = Math.round(MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5) * 100.0f);
            if (var7 > 0) {
                if (this.ridingEntity instanceof EntityMinecart) {
                    this.addStat(StatList.distanceByMinecartStat, var7);
                    if (this.startMinecartRidingCoordinate == null) {
                        this.startMinecartRidingCoordinate = new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
                    }
                    else if (this.startMinecartRidingCoordinate.getDistanceSquared(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ)) >= 1000000.0) {
                        this.addStat(AchievementList.onARail, 1);
                    }
                }
                else if (this.ridingEntity instanceof EntityBoat) {
                    this.addStat(StatList.distanceByBoatStat, var7);
                }
                else if (this.ridingEntity instanceof EntityPig) {
                    this.addStat(StatList.distanceByPigStat, var7);
                }
            }
        }
    }
    
    @Override
    protected void fall(final float par1) {
        if (!this.capabilities.allowFlying) {
            if (par1 >= 2.0f) {
                this.addStat(StatList.distanceFallenStat, (int)Math.round(par1 * 100.0));
            }
            super.fall(par1);
        }
    }
    
    @Override
    public void onKillEntity(final EntityLiving par1EntityLiving) {
        if (par1EntityLiving instanceof IMob) {
            this.triggerAchievement(AchievementList.killEnemy);
        }
    }
    
    @Override
    public void setInWeb() {
        if (!this.capabilities.isFlying) {
            super.setInWeb();
        }
    }
    
    @Override
    public Icon getItemIcon(final ItemStack par1ItemStack, final int par2) {
        Icon var3 = super.getItemIcon(par1ItemStack, par2);
        if (par1ItemStack.itemID == Item.fishingRod.itemID && this.fishEntity != null) {
            var3 = Item.fishingRod.func_94597_g();
        }
        else {
            if (par1ItemStack.getItem().requiresMultipleRenderPasses()) {
                return par1ItemStack.getItem().getIconFromDamageForRenderPass(par1ItemStack.getItemDamage(), par2);
            }
            if (this.itemInUse != null && par1ItemStack.itemID == Item.bow.itemID) {
                final int var4 = par1ItemStack.getMaxItemUseDuration() - this.itemInUseCount;
                if (var4 >= 18) {
                    return Item.bow.getItemIconForUseDuration(2);
                }
                if (var4 > 13) {
                    return Item.bow.getItemIconForUseDuration(1);
                }
                if (var4 > 0) {
                    return Item.bow.getItemIconForUseDuration(0);
                }
            }
        }
        return var3;
    }
    
    @Override
    public ItemStack getCurrentArmor(final int par1) {
        return this.inventory.armorItemInSlot(par1);
    }
    
    @Override
    protected void addRandomArmor() {
    }
    
    @Override
    protected void func_82162_bC() {
    }
    
    public void addExperience(int par1) {
        this.addScore(par1);
        final int var2 = Integer.MAX_VALUE - this.experienceTotal;
        if (par1 > var2) {
            par1 = var2;
        }
        this.experience += par1 / this.xpBarCap();
        this.experienceTotal += par1;
        while (this.experience >= 1.0f) {
            this.experience = (this.experience - 1.0f) * this.xpBarCap();
            this.addExperienceLevel(1);
            this.experience /= this.xpBarCap();
        }
    }
    
    public void addExperienceLevel(final int par1) {
        this.experienceLevel += par1;
        if (this.experienceLevel < 0) {
            this.experienceLevel = 0;
            this.experience = 0.0f;
            this.experienceTotal = 0;
        }
        if (par1 > 0 && this.experienceLevel % 5 == 0 && this.field_82249_h < this.ticksExisted - 100.0f) {
            final float var2 = (this.experienceLevel > 30) ? 1.0f : (this.experienceLevel / 30.0f);
            this.worldObj.playSoundAtEntity(this, "random.levelup", var2 * 0.75f, 1.0f);
            this.field_82249_h = this.ticksExisted;
        }
    }
    
    public int xpBarCap() {
        return (this.experienceLevel >= 30) ? (62 + (this.experienceLevel - 30) * 7) : ((this.experienceLevel >= 15) ? (17 + (this.experienceLevel - 15) * 3) : 17);
    }
    
    public void addExhaustion(final float par1) {
        if (!this.capabilities.disableDamage && !this.worldObj.isRemote) {
            this.foodStats.addExhaustion(par1);
        }
    }
    
    public FoodStats getFoodStats() {
        return this.foodStats;
    }
    
    public boolean canEat(final boolean par1) {
        return (par1 || this.foodStats.needFood()) && !this.capabilities.disableDamage;
    }
    
    public boolean shouldHeal() {
        return this.getHealth() > 0 && this.getHealth() < this.getMaxHealth();
    }
    
    public void setItemInUse(final ItemStack par1ItemStack, final int par2) {
        if (par1ItemStack != this.itemInUse) {
            this.itemInUse = par1ItemStack;
            this.itemInUseCount = par2;
            if (!this.worldObj.isRemote) {
                this.setEating(true);
            }
        }
    }
    
    public boolean canCurrentToolHarvestBlock(final int par1, final int par2, final int par3) {
        if (this.capabilities.allowEdit) {
            return true;
        }
        final int var4 = this.worldObj.getBlockId(par1, par2, par3);
        if (var4 > 0) {
            final Block var5 = Block.blocksList[var4];
            if (var5.blockMaterial.isAlwaysHarvested()) {
                return true;
            }
            if (this.getCurrentEquippedItem() != null) {
                final ItemStack var6 = this.getCurrentEquippedItem();
                if (var6.canHarvestBlock(var5) || var6.getStrVsBlock(var5) > 1.0f) {
                    return true;
                }
            }
        }
        return false;
    }
    
    public boolean canPlayerEdit(final int par1, final int par2, final int par3, final int par4, final ItemStack par5ItemStack) {
        return this.capabilities.allowEdit || (par5ItemStack != null && par5ItemStack.func_82835_x());
    }
    
    @Override
    protected int getExperiencePoints(final EntityPlayer par1EntityPlayer) {
        if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            return 0;
        }
        final int var2 = this.experienceLevel * 7;
        return (var2 > 100) ? 100 : var2;
    }
    
    @Override
    protected boolean isPlayer() {
        return true;
    }
    
    @Override
    public String getEntityName() {
        return this.username;
    }
    
    @Override
    public boolean func_94062_bN() {
        return super.func_94062_bN();
    }
    
    @Override
    public boolean func_94059_bO() {
        return true;
    }
    
    @Override
    public boolean canPickUpLoot() {
        return false;
    }
    
    public void clonePlayer(final EntityPlayer par1EntityPlayer, final boolean par2) {
        if (par2) {
            this.inventory.copyInventory(par1EntityPlayer.inventory);
            this.health = par1EntityPlayer.health;
            this.foodStats = par1EntityPlayer.foodStats;
            this.experienceLevel = par1EntityPlayer.experienceLevel;
            this.experienceTotal = par1EntityPlayer.experienceTotal;
            this.experience = par1EntityPlayer.experience;
            this.setScore(par1EntityPlayer.getScore());
            this.teleportDirection = par1EntityPlayer.teleportDirection;
        }
        else if (this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.copyInventory(par1EntityPlayer.inventory);
            this.experienceLevel = par1EntityPlayer.experienceLevel;
            this.experienceTotal = par1EntityPlayer.experienceTotal;
            this.experience = par1EntityPlayer.experience;
            this.setScore(par1EntityPlayer.getScore());
        }
        this.theInventoryEnderChest = par1EntityPlayer.theInventoryEnderChest;
    }
    
    @Override
    protected boolean canTriggerWalking() {
        return !this.capabilities.isFlying;
    }
    
    public void sendPlayerAbilities() {
    }
    
    public void setGameType(final EnumGameType par1EnumGameType) {
    }
    
    @Override
    public String getCommandSenderName() {
        return this.username;
    }
    
    public StringTranslate getTranslator() {
        return StringTranslate.getInstance();
    }
    
    @Override
    public String translateString(final String par1Str, final Object... par2ArrayOfObj) {
        return this.getTranslator().translateKeyFormat(par1Str, par2ArrayOfObj);
    }
    
    public InventoryEnderChest getInventoryEnderChest() {
        return this.theInventoryEnderChest;
    }
    
    @Override
    public ItemStack getCurrentItemOrArmor(final int par1) {
        return (par1 == 0) ? this.inventory.getCurrentItem() : this.inventory.armorInventory[par1 - 1];
    }
    
    @Override
    public ItemStack getHeldItem() {
        return this.inventory.getCurrentItem();
    }
    
    @Override
    public void setCurrentItemOrArmor(final int par1, final ItemStack par2ItemStack) {
        this.inventory.armorInventory[par1] = par2ItemStack;
    }
    
    @Override
    public boolean func_98034_c(final EntityPlayer par1EntityPlayer) {
        if (!this.isInvisible()) {
            return false;
        }
        final ScorePlayerTeam var2 = this.getTeam();
        return var2 == null || par1EntityPlayer == null || par1EntityPlayer.getTeam() != var2 || !var2.func_98297_h();
    }
    
    @Override
    public ItemStack[] getLastActiveItems() {
        return this.inventory.armorInventory;
    }
    
    public boolean getHideCape() {
        return this.getHideCape(1);
    }
    
    @Override
    public boolean func_96092_aw() {
        return !this.capabilities.isFlying;
    }
    
    public Scoreboard getWorldScoreboard() {
        return this.worldObj.getScoreboard();
    }
    
    public ScorePlayerTeam getTeam() {
        return this.getWorldScoreboard().getPlayersTeam(this.username);
    }
    
    @Override
    public String getTranslatedEntityName() {
        return ScorePlayerTeam.func_96667_a(this.getTeam(), this.username);
    }
}
