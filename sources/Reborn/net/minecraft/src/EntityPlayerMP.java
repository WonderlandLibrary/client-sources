package net.minecraft.src;

import net.minecraft.server.*;
import java.util.*;
import java.io.*;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
    private StringTranslate translator;
    public NetServerHandler playerNetServerHandler;
    public MinecraftServer mcServer;
    public ItemInWorldManager theItemInWorldManager;
    public double managedPosX;
    public double managedPosZ;
    public final List loadedChunks;
    public final List destroyedItemsNetCache;
    private int lastHealth;
    private int lastFoodLevel;
    private boolean wasHungry;
    private int lastExperience;
    private int initialInvulnerability;
    private int renderDistance;
    private int chatVisibility;
    private boolean chatColours;
    private int currentWindowId;
    public boolean playerInventoryBeingManipulated;
    public int ping;
    public boolean playerConqueredTheEnd;
    
    public EntityPlayerMP(final MinecraftServer par1MinecraftServer, final World par2World, final String par3Str, final ItemInWorldManager par4ItemInWorldManager) {
        super(par2World);
        this.translator = new StringTranslate("en_US");
        this.loadedChunks = new LinkedList();
        this.destroyedItemsNetCache = new LinkedList();
        this.lastHealth = -99999999;
        this.lastFoodLevel = -99999999;
        this.wasHungry = true;
        this.lastExperience = -99999999;
        this.initialInvulnerability = 60;
        this.renderDistance = 0;
        this.chatVisibility = 0;
        this.chatColours = true;
        this.currentWindowId = 0;
        this.playerConqueredTheEnd = false;
        par4ItemInWorldManager.thisPlayerMP = this;
        this.theItemInWorldManager = par4ItemInWorldManager;
        this.renderDistance = par1MinecraftServer.getConfigurationManager().getViewDistance();
        final ChunkCoordinates var5 = par2World.getSpawnPoint();
        int var6 = var5.posX;
        int var7 = var5.posZ;
        int var8 = var5.posY;
        if (!par2World.provider.hasNoSky && par2World.getWorldInfo().getGameType() != EnumGameType.ADVENTURE) {
            final int var9 = Math.max(5, par1MinecraftServer.getSpawnProtectionSize() - 6);
            var6 += this.rand.nextInt(var9 * 2) - var9;
            var7 += this.rand.nextInt(var9 * 2) - var9;
            var8 = par2World.getTopSolidOrLiquidBlock(var6, var7);
        }
        this.mcServer = par1MinecraftServer;
        this.stepHeight = 0.0f;
        this.username = par3Str;
        this.yOffset = 0.0f;
        this.setLocationAndAngles(var6 + 0.5, var8, var7 + 0.5, 0.0f, 0.0f);
        while (!par2World.getCollidingBoundingBoxes(this, this.boundingBox).isEmpty()) {
            this.setPosition(this.posX, this.posY + 1.0, this.posZ);
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        if (par1NBTTagCompound.hasKey("playerGameType")) {
            if (MinecraftServer.getServer().func_104056_am()) {
                this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
            }
            else {
                this.theItemInWorldManager.setGameType(EnumGameType.getByID(par1NBTTagCompound.getInteger("playerGameType")));
            }
        }
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
    }
    
    @Override
    public void addExperienceLevel(final int par1) {
        super.addExperienceLevel(par1);
        this.lastExperience = -1;
    }
    
    public void addSelfToInternalCraftingInventory() {
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    protected void resetHeight() {
        this.yOffset = 0.0f;
    }
    
    @Override
    public float getEyeHeight() {
        return 1.62f;
    }
    
    @Override
    public void onUpdate() {
        this.theItemInWorldManager.updateBlockRemoving();
        --this.initialInvulnerability;
        this.openContainer.detectAndSendChanges();
        while (!this.destroyedItemsNetCache.isEmpty()) {
            final int var1 = Math.min(this.destroyedItemsNetCache.size(), 127);
            final int[] var2 = new int[var1];
            final Iterator var3 = this.destroyedItemsNetCache.iterator();
            int var4 = 0;
            while (var3.hasNext() && var4 < var1) {
                var2[var4++] = var3.next();
                var3.remove();
            }
            this.playerNetServerHandler.sendPacketToPlayer(new Packet29DestroyEntity(var2));
        }
        if (!this.loadedChunks.isEmpty()) {
            final ArrayList var5 = new ArrayList();
            final Iterator var6 = this.loadedChunks.iterator();
            final ArrayList var7 = new ArrayList();
            while (var6.hasNext() && var5.size() < 5) {
                final ChunkCoordIntPair var8 = var6.next();
                var6.remove();
                if (var8 != null && this.worldObj.blockExists(var8.chunkXPos << 4, 0, var8.chunkZPos << 4)) {
                    var5.add(this.worldObj.getChunkFromChunkCoords(var8.chunkXPos, var8.chunkZPos));
                    var7.addAll(((WorldServer)this.worldObj).getAllTileEntityInBox(var8.chunkXPos * 16, 0, var8.chunkZPos * 16, var8.chunkXPos * 16 + 16, 256, var8.chunkZPos * 16 + 16));
                }
            }
            if (!var5.isEmpty()) {
                this.playerNetServerHandler.sendPacketToPlayer(new Packet56MapChunks(var5));
                for (final TileEntity var10 : var7) {
                    this.sendTileEntityToPlayer(var10);
                }
                for (final Chunk var11 : var5) {
                    this.getServerForPlayer().getEntityTracker().func_85172_a(this, var11);
                }
            }
        }
    }
    
    @Override
    public void setEntityHealth(final int par1) {
        super.setEntityHealth(par1);
        final Collection var2 = this.getWorldScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96638_f);
        for (final ScoreObjective var4 : var2) {
            this.getWorldScoreboard().func_96529_a(this.getEntityName(), var4).func_96651_a(Arrays.asList(this));
        }
    }
    
    public void onUpdateEntity() {
        try {
            super.onUpdate();
            for (int var1 = 0; var1 < this.inventory.getSizeInventory(); ++var1) {
                final ItemStack var2 = this.inventory.getStackInSlot(var1);
                if (var2 != null && Item.itemsList[var2.itemID].isMap() && this.playerNetServerHandler.packetSize() <= 5) {
                    final Packet var3 = ((ItemMapBase)Item.itemsList[var2.itemID]).createMapDataPacket(var2, this.worldObj, this);
                    if (var3 != null) {
                        this.playerNetServerHandler.sendPacketToPlayer(var3);
                    }
                }
            }
            if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0f != this.wasHungry) {
                this.playerNetServerHandler.sendPacketToPlayer(new Packet8UpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
                this.lastHealth = this.getHealth();
                this.lastFoodLevel = this.foodStats.getFoodLevel();
                this.wasHungry = (this.foodStats.getSaturationLevel() == 0.0f);
            }
            if (this.experienceTotal != this.lastExperience) {
                this.lastExperience = this.experienceTotal;
                this.playerNetServerHandler.sendPacketToPlayer(new Packet43Experience(this.experience, this.experienceTotal, this.experienceLevel));
            }
        }
        catch (Throwable var5) {
            final CrashReport var4 = CrashReport.makeCrashReport(var5, "Ticking player");
            final CrashReportCategory var6 = var4.makeCategory("Player being ticked");
            this.func_85029_a(var6);
            throw new ReportedException(var4);
        }
    }
    
    @Override
    public void onDeath(final DamageSource par1DamageSource) {
        this.mcServer.getConfigurationManager().sendChatMsg(this.field_94063_bt.func_94546_b());
        if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
            this.inventory.dropAllItems();
        }
        final Collection var2 = this.worldObj.getScoreboard().func_96520_a(ScoreObjectiveCriteria.field_96642_c);
        for (final ScoreObjective var4 : var2) {
            final Score var5 = this.getWorldScoreboard().func_96529_a(this.getEntityName(), var4);
            var5.func_96648_a();
        }
        final EntityLiving var6 = this.func_94060_bK();
        if (var6 != null) {
            var6.addToPlayerScore(this, this.scoreValue);
        }
    }
    
    @Override
    public boolean attackEntityFrom(final DamageSource par1DamageSource, final int par2) {
        if (this.isEntityInvulnerable()) {
            return false;
        }
        final boolean var3 = this.mcServer.isDedicatedServer() && this.mcServer.isPVPEnabled() && "fall".equals(par1DamageSource.damageType);
        if (!var3 && this.initialInvulnerability > 0 && par1DamageSource != DamageSource.outOfWorld) {
            return false;
        }
        if (par1DamageSource instanceof EntityDamageSource) {
            final Entity var4 = par1DamageSource.getEntity();
            if (var4 instanceof EntityPlayer && !this.func_96122_a((EntityPlayer)var4)) {
                return false;
            }
            if (var4 instanceof EntityArrow) {
                final EntityArrow var5 = (EntityArrow)var4;
                if (var5.shootingEntity instanceof EntityPlayer && !this.func_96122_a((EntityPlayer)var5.shootingEntity)) {
                    return false;
                }
            }
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }
    
    @Override
    public boolean func_96122_a(final EntityPlayer par1EntityPlayer) {
        return this.mcServer.isPVPEnabled() && super.func_96122_a(par1EntityPlayer);
    }
    
    @Override
    public void travelToDimension(int par1) {
        if (this.dimension == 1 && par1 == 1) {
            this.triggerAchievement(AchievementList.theEnd2);
            this.worldObj.removeEntity(this);
            this.playerConqueredTheEnd = true;
            this.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(4, 0));
        }
        else {
            if (this.dimension == 1 && par1 == 0) {
                this.triggerAchievement(AchievementList.theEnd);
                final ChunkCoordinates var2 = this.mcServer.worldServerForDimension(par1).getEntrancePortalLocation();
                if (var2 != null) {
                    this.playerNetServerHandler.setPlayerLocation(var2.posX, var2.posY, var2.posZ, 0.0f, 0.0f);
                }
                par1 = 1;
            }
            else {
                this.triggerAchievement(AchievementList.portal);
            }
            this.mcServer.getConfigurationManager().transferPlayerToDimension(this, par1);
            this.lastExperience = -1;
            this.lastHealth = -1;
            this.lastFoodLevel = -1;
        }
    }
    
    private void sendTileEntityToPlayer(final TileEntity par1TileEntity) {
        if (par1TileEntity != null) {
            final Packet var2 = par1TileEntity.getDescriptionPacket();
            if (var2 != null) {
                this.playerNetServerHandler.sendPacketToPlayer(var2);
            }
        }
    }
    
    @Override
    public void onItemPickup(final Entity par1Entity, final int par2) {
        super.onItemPickup(par1Entity, par2);
        this.openContainer.detectAndSendChanges();
    }
    
    @Override
    public EnumStatus sleepInBedAt(final int par1, final int par2, final int par3) {
        final EnumStatus var4 = super.sleepInBedAt(par1, par2, par3);
        if (var4 == EnumStatus.OK) {
            final Packet17Sleep var5 = new Packet17Sleep(this, 0, par1, par2, par3);
            this.getServerForPlayer().getEntityTracker().sendPacketToAllPlayersTrackingEntity(this, var5);
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
            this.playerNetServerHandler.sendPacketToPlayer(var5);
        }
        return var4;
    }
    
    @Override
    public void wakeUpPlayer(final boolean par1, final boolean par2, final boolean par3) {
        if (this.isPlayerSleeping()) {
            this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 3));
        }
        super.wakeUpPlayer(par1, par2, par3);
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
        }
    }
    
    @Override
    public void mountEntity(final Entity par1Entity) {
        super.mountEntity(par1Entity);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet39AttachEntity(this, this.ridingEntity));
        this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
    }
    
    @Override
    protected void updateFallState(final double par1, final boolean par3) {
    }
    
    public void updateFlyingState(final double par1, final boolean par3) {
        super.updateFallState(par1, par3);
    }
    
    private void incrementWindowID() {
        this.currentWindowId = this.currentWindowId % 100 + 1;
    }
    
    @Override
    public void displayGUIWorkbench(final int par1, final int par2, final int par3) {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 1, "Crafting", 9, true));
        this.openContainer = new ContainerWorkbench(this.inventory, this.worldObj, par1, par2, par3);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIEnchantment(final int par1, final int par2, final int par3, final String par4Str) {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 4, (par4Str == null) ? "" : par4Str, 9, par4Str != null));
        this.openContainer = new ContainerEnchantment(this.inventory, this.worldObj, par1, par2, par3);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIAnvil(final int par1, final int par2, final int par3) {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 8, "Repairing", 9, true));
        this.openContainer = new ContainerRepair(this.inventory, this.worldObj, par1, par2, par3, this);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIChest(final IInventory par1IInventory) {
        if (this.openContainer != this.inventoryContainer) {
            this.closeScreen();
        }
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 0, par1IInventory.getInvName(), par1IInventory.getSizeInventory(), par1IInventory.isInvNameLocalized()));
        this.openContainer = new ContainerChest(this.inventory, par1IInventory);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIHopper(final TileEntityHopper par1TileEntityHopper) {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 9, par1TileEntityHopper.getInvName(), par1TileEntityHopper.getSizeInventory(), par1TileEntityHopper.isInvNameLocalized()));
        this.openContainer = new ContainerHopper(this.inventory, par1TileEntityHopper);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIHopperMinecart(final EntityMinecartHopper par1EntityMinecartHopper) {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 9, par1EntityMinecartHopper.getInvName(), par1EntityMinecartHopper.getSizeInventory(), par1EntityMinecartHopper.isInvNameLocalized()));
        this.openContainer = new ContainerHopper(this.inventory, par1EntityMinecartHopper);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIFurnace(final TileEntityFurnace par1TileEntityFurnace) {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 2, par1TileEntityFurnace.getInvName(), par1TileEntityFurnace.getSizeInventory(), par1TileEntityFurnace.isInvNameLocalized()));
        this.openContainer = new ContainerFurnace(this.inventory, par1TileEntityFurnace);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIDispenser(final TileEntityDispenser par1TileEntityDispenser) {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, (par1TileEntityDispenser instanceof TileEntityDropper) ? 10 : 3, par1TileEntityDispenser.getInvName(), par1TileEntityDispenser.getSizeInventory(), par1TileEntityDispenser.isInvNameLocalized()));
        this.openContainer = new ContainerDispenser(this.inventory, par1TileEntityDispenser);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIBrewingStand(final TileEntityBrewingStand par1TileEntityBrewingStand) {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 5, par1TileEntityBrewingStand.getInvName(), par1TileEntityBrewingStand.getSizeInventory(), par1TileEntityBrewingStand.isInvNameLocalized()));
        this.openContainer = new ContainerBrewingStand(this.inventory, par1TileEntityBrewingStand);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIBeacon(final TileEntityBeacon par1TileEntityBeacon) {
        this.incrementWindowID();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 7, par1TileEntityBeacon.getInvName(), par1TileEntityBeacon.getSizeInventory(), par1TileEntityBeacon.isInvNameLocalized()));
        this.openContainer = new ContainerBeacon(this.inventory, par1TileEntityBeacon);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
    }
    
    @Override
    public void displayGUIMerchant(final IMerchant par1IMerchant, final String par2Str) {
        this.incrementWindowID();
        this.openContainer = new ContainerMerchant(this.inventory, par1IMerchant, this.worldObj);
        this.openContainer.windowId = this.currentWindowId;
        this.openContainer.addCraftingToCrafters(this);
        final InventoryMerchant var3 = ((ContainerMerchant)this.openContainer).getMerchantInventory();
        this.playerNetServerHandler.sendPacketToPlayer(new Packet100OpenWindow(this.currentWindowId, 6, (par2Str == null) ? "" : par2Str, var3.getSizeInventory(), par2Str != null));
        final MerchantRecipeList var4 = par1IMerchant.getRecipes(this);
        if (var4 != null) {
            try {
                final ByteArrayOutputStream var5 = new ByteArrayOutputStream();
                final DataOutputStream var6 = new DataOutputStream(var5);
                var6.writeInt(this.currentWindowId);
                var4.writeRecipiesToStream(var6);
                this.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("MC|TrList", var5.toByteArray()));
            }
            catch (IOException var7) {
                var7.printStackTrace();
            }
        }
    }
    
    @Override
    public void sendSlotContents(final Container par1Container, final int par2, final ItemStack par3ItemStack) {
        if (!(par1Container.getSlot(par2) instanceof SlotCrafting) && !this.playerInventoryBeingManipulated) {
            this.playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(par1Container.windowId, par2, par3ItemStack));
        }
    }
    
    public void sendContainerToPlayer(final Container par1Container) {
        this.sendContainerAndContentsToPlayer(par1Container, par1Container.getInventory());
    }
    
    @Override
    public void sendContainerAndContentsToPlayer(final Container par1Container, final List par2List) {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet104WindowItems(par1Container.windowId, par2List));
        this.playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, this.inventory.getItemStack()));
    }
    
    @Override
    public void sendProgressBarUpdate(final Container par1Container, final int par2, final int par3) {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet105UpdateProgressbar(par1Container.windowId, par2, par3));
    }
    
    public void closeScreen() {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet101CloseWindow(this.openContainer.windowId));
        this.closeInventory();
    }
    
    public void updateHeldItem() {
        if (!this.playerInventoryBeingManipulated) {
            this.playerNetServerHandler.sendPacketToPlayer(new Packet103SetSlot(-1, -1, this.inventory.getItemStack()));
        }
    }
    
    public void closeInventory() {
        this.openContainer.onCraftGuiClosed(this);
        this.openContainer = this.inventoryContainer;
    }
    
    @Override
    public void addStat(final StatBase par1StatBase, int par2) {
        if (par1StatBase != null && !par1StatBase.isIndependent) {
            while (par2 > 100) {
                this.playerNetServerHandler.sendPacketToPlayer(new Packet200Statistic(par1StatBase.statId, 100));
                par2 -= 100;
            }
            this.playerNetServerHandler.sendPacketToPlayer(new Packet200Statistic(par1StatBase.statId, par2));
        }
    }
    
    public void mountEntityAndWakeUp() {
        if (this.riddenByEntity != null) {
            this.riddenByEntity.mountEntity(this);
        }
        if (this.sleeping) {
            this.wakeUpPlayer(true, false, false);
        }
    }
    
    public void setPlayerHealthUpdated() {
        this.lastHealth = -99999999;
    }
    
    @Override
    public void addChatMessage(final String par1Str) {
        final StringTranslate var2 = StringTranslate.getInstance();
        final String var3 = var2.translateKey(par1Str);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(var3));
    }
    
    @Override
    protected void onItemUseFinish() {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet38EntityStatus(this.entityId, (byte)9));
        super.onItemUseFinish();
    }
    
    @Override
    public void setItemInUse(final ItemStack par1ItemStack, final int par2) {
        super.setItemInUse(par1ItemStack, par2);
        if (par1ItemStack != null && par1ItemStack.getItem() != null && par1ItemStack.getItem().getItemUseAction(par1ItemStack) == EnumAction.eat) {
            this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(this, 5));
        }
    }
    
    @Override
    public void clonePlayer(final EntityPlayer par1EntityPlayer, final boolean par2) {
        super.clonePlayer(par1EntityPlayer, par2);
        this.lastExperience = -1;
        this.lastHealth = -1;
        this.lastFoodLevel = -1;
        this.destroyedItemsNetCache.addAll(((EntityPlayerMP)par1EntityPlayer).destroyedItemsNetCache);
    }
    
    @Override
    protected void onNewPotionEffect(final PotionEffect par1PotionEffect) {
        super.onNewPotionEffect(par1PotionEffect);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(this.entityId, par1PotionEffect));
    }
    
    @Override
    protected void onChangedPotionEffect(final PotionEffect par1PotionEffect) {
        super.onChangedPotionEffect(par1PotionEffect);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(this.entityId, par1PotionEffect));
    }
    
    @Override
    protected void onFinishedPotionEffect(final PotionEffect par1PotionEffect) {
        super.onFinishedPotionEffect(par1PotionEffect);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet42RemoveEntityEffect(this.entityId, par1PotionEffect));
    }
    
    @Override
    public void setPositionAndUpdate(final double par1, final double par3, final double par5) {
        this.playerNetServerHandler.setPlayerLocation(par1, par3, par5, this.rotationYaw, this.rotationPitch);
    }
    
    @Override
    public void onCriticalHit(final Entity par1Entity) {
        this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(par1Entity, 6));
    }
    
    @Override
    public void onEnchantmentCritical(final Entity par1Entity) {
        this.getServerForPlayer().getEntityTracker().sendPacketToAllAssociatedPlayers(this, new Packet18Animation(par1Entity, 7));
    }
    
    @Override
    public void sendPlayerAbilities() {
        if (this.playerNetServerHandler != null) {
            this.playerNetServerHandler.sendPacketToPlayer(new Packet202PlayerAbilities(this.capabilities));
        }
    }
    
    public WorldServer getServerForPlayer() {
        return (WorldServer)this.worldObj;
    }
    
    @Override
    public void setGameType(final EnumGameType par1EnumGameType) {
        this.theItemInWorldManager.setGameType(par1EnumGameType);
        this.playerNetServerHandler.sendPacketToPlayer(new Packet70GameEvent(3, par1EnumGameType.getID()));
    }
    
    @Override
    public void sendChatToPlayer(final String par1Str) {
        this.playerNetServerHandler.sendPacketToPlayer(new Packet3Chat(par1Str));
    }
    
    @Override
    public boolean canCommandSenderUseCommand(final int par1, final String par2Str) {
        return ("seed".equals(par2Str) && !this.mcServer.isDedicatedServer()) || "tell".equals(par2Str) || "help".equals(par2Str) || "me".equals(par2Str) || this.mcServer.getConfigurationManager().areCommandsAllowed(this.username);
    }
    
    public String getPlayerIP() {
        String var1 = this.playerNetServerHandler.netManager.getSocketAddress().toString();
        var1 = var1.substring(var1.indexOf("/") + 1);
        var1 = var1.substring(0, var1.indexOf(":"));
        return var1;
    }
    
    public void updateClientInfo(final Packet204ClientInfo par1Packet204ClientInfo) {
        if (this.translator.getLanguageList().containsKey(par1Packet204ClientInfo.getLanguage())) {
            this.translator.setLanguage(par1Packet204ClientInfo.getLanguage(), false);
        }
        final int var2 = 256 >> par1Packet204ClientInfo.getRenderDistance();
        if (var2 > 3 && var2 < 15) {
            this.renderDistance = var2;
        }
        this.chatVisibility = par1Packet204ClientInfo.getChatVisibility();
        this.chatColours = par1Packet204ClientInfo.getChatColours();
        if (this.mcServer.isSinglePlayer() && this.mcServer.getServerOwner().equals(this.username)) {
            this.mcServer.setDifficultyForAllWorlds(par1Packet204ClientInfo.getDifficulty());
        }
        this.setHideCape(1, !par1Packet204ClientInfo.getShowCape());
    }
    
    @Override
    public StringTranslate getTranslator() {
        return this.translator;
    }
    
    public int getChatVisibility() {
        return this.chatVisibility;
    }
    
    public void requestTexturePackLoad(final String par1Str, final int par2) {
        final String var3 = String.valueOf(par1Str) + "\u0000" + par2;
        this.playerNetServerHandler.sendPacketToPlayer(new Packet250CustomPayload("MC|TPack", var3.getBytes()));
    }
    
    @Override
    public ChunkCoordinates getPlayerCoordinates() {
        return new ChunkCoordinates(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY + 0.5), MathHelper.floor_double(this.posZ));
    }
}
