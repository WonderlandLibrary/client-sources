package net.minecraft.src;

import java.util.*;

public class EntityVillager extends EntityAgeable implements INpc, IMerchant
{
    private int randomTickDivider;
    private boolean isMating;
    private boolean isPlaying;
    Village villageObj;
    private EntityPlayer buyingPlayer;
    private MerchantRecipeList buyingList;
    private int timeUntilReset;
    private boolean needsInitilization;
    private int wealth;
    private String lastBuyingPlayer;
    private boolean field_82190_bM;
    private float field_82191_bN;
    private static final Map villagerStockList;
    private static final Map blacksmithSellingList;
    
    static {
        villagerStockList = new HashMap();
        blacksmithSellingList = new HashMap();
        EntityVillager.villagerStockList.put(Item.coal.itemID, new Tuple(16, 24));
        EntityVillager.villagerStockList.put(Item.ingotIron.itemID, new Tuple(8, 10));
        EntityVillager.villagerStockList.put(Item.ingotGold.itemID, new Tuple(8, 10));
        EntityVillager.villagerStockList.put(Item.diamond.itemID, new Tuple(4, 6));
        EntityVillager.villagerStockList.put(Item.paper.itemID, new Tuple(24, 36));
        EntityVillager.villagerStockList.put(Item.book.itemID, new Tuple(11, 13));
        EntityVillager.villagerStockList.put(Item.writtenBook.itemID, new Tuple(1, 1));
        EntityVillager.villagerStockList.put(Item.enderPearl.itemID, new Tuple(3, 4));
        EntityVillager.villagerStockList.put(Item.eyeOfEnder.itemID, new Tuple(2, 3));
        EntityVillager.villagerStockList.put(Item.porkRaw.itemID, new Tuple(14, 18));
        EntityVillager.villagerStockList.put(Item.beefRaw.itemID, new Tuple(14, 18));
        EntityVillager.villagerStockList.put(Item.chickenRaw.itemID, new Tuple(14, 18));
        EntityVillager.villagerStockList.put(Item.fishCooked.itemID, new Tuple(9, 13));
        EntityVillager.villagerStockList.put(Item.seeds.itemID, new Tuple(34, 48));
        EntityVillager.villagerStockList.put(Item.melonSeeds.itemID, new Tuple(30, 38));
        EntityVillager.villagerStockList.put(Item.pumpkinSeeds.itemID, new Tuple(30, 38));
        EntityVillager.villagerStockList.put(Item.wheat.itemID, new Tuple(18, 22));
        EntityVillager.villagerStockList.put(Block.cloth.blockID, new Tuple(14, 22));
        EntityVillager.villagerStockList.put(Item.rottenFlesh.itemID, new Tuple(36, 64));
        EntityVillager.blacksmithSellingList.put(Item.flintAndSteel.itemID, new Tuple(3, 4));
        EntityVillager.blacksmithSellingList.put(Item.shears.itemID, new Tuple(3, 4));
        EntityVillager.blacksmithSellingList.put(Item.swordIron.itemID, new Tuple(7, 11));
        EntityVillager.blacksmithSellingList.put(Item.swordDiamond.itemID, new Tuple(12, 14));
        EntityVillager.blacksmithSellingList.put(Item.axeIron.itemID, new Tuple(6, 8));
        EntityVillager.blacksmithSellingList.put(Item.axeDiamond.itemID, new Tuple(9, 12));
        EntityVillager.blacksmithSellingList.put(Item.pickaxeIron.itemID, new Tuple(7, 9));
        EntityVillager.blacksmithSellingList.put(Item.pickaxeDiamond.itemID, new Tuple(10, 12));
        EntityVillager.blacksmithSellingList.put(Item.shovelIron.itemID, new Tuple(4, 6));
        EntityVillager.blacksmithSellingList.put(Item.shovelDiamond.itemID, new Tuple(7, 8));
        EntityVillager.blacksmithSellingList.put(Item.hoeIron.itemID, new Tuple(4, 6));
        EntityVillager.blacksmithSellingList.put(Item.hoeDiamond.itemID, new Tuple(7, 8));
        EntityVillager.blacksmithSellingList.put(Item.bootsIron.itemID, new Tuple(4, 6));
        EntityVillager.blacksmithSellingList.put(Item.bootsDiamond.itemID, new Tuple(7, 8));
        EntityVillager.blacksmithSellingList.put(Item.helmetIron.itemID, new Tuple(4, 6));
        EntityVillager.blacksmithSellingList.put(Item.helmetDiamond.itemID, new Tuple(7, 8));
        EntityVillager.blacksmithSellingList.put(Item.plateIron.itemID, new Tuple(10, 14));
        EntityVillager.blacksmithSellingList.put(Item.plateDiamond.itemID, new Tuple(16, 19));
        EntityVillager.blacksmithSellingList.put(Item.legsIron.itemID, new Tuple(8, 10));
        EntityVillager.blacksmithSellingList.put(Item.legsDiamond.itemID, new Tuple(11, 14));
        EntityVillager.blacksmithSellingList.put(Item.bootsChain.itemID, new Tuple(5, 7));
        EntityVillager.blacksmithSellingList.put(Item.helmetChain.itemID, new Tuple(5, 7));
        EntityVillager.blacksmithSellingList.put(Item.plateChain.itemID, new Tuple(11, 15));
        EntityVillager.blacksmithSellingList.put(Item.legsChain.itemID, new Tuple(9, 11));
        EntityVillager.blacksmithSellingList.put(Item.bread.itemID, new Tuple(-4, -2));
        EntityVillager.blacksmithSellingList.put(Item.melon.itemID, new Tuple(-8, -4));
        EntityVillager.blacksmithSellingList.put(Item.appleRed.itemID, new Tuple(-8, -4));
        EntityVillager.blacksmithSellingList.put(Item.cookie.itemID, new Tuple(-10, -7));
        EntityVillager.blacksmithSellingList.put(Block.glass.blockID, new Tuple(-5, -3));
        EntityVillager.blacksmithSellingList.put(Block.bookShelf.blockID, new Tuple(3, 4));
        EntityVillager.blacksmithSellingList.put(Item.plateLeather.itemID, new Tuple(4, 5));
        EntityVillager.blacksmithSellingList.put(Item.bootsLeather.itemID, new Tuple(2, 4));
        EntityVillager.blacksmithSellingList.put(Item.helmetLeather.itemID, new Tuple(2, 4));
        EntityVillager.blacksmithSellingList.put(Item.legsLeather.itemID, new Tuple(2, 4));
        EntityVillager.blacksmithSellingList.put(Item.saddle.itemID, new Tuple(6, 8));
        EntityVillager.blacksmithSellingList.put(Item.expBottle.itemID, new Tuple(-4, -1));
        EntityVillager.blacksmithSellingList.put(Item.redstone.itemID, new Tuple(-4, -1));
        EntityVillager.blacksmithSellingList.put(Item.compass.itemID, new Tuple(10, 12));
        EntityVillager.blacksmithSellingList.put(Item.pocketSundial.itemID, new Tuple(10, 12));
        EntityVillager.blacksmithSellingList.put(Block.glowStone.blockID, new Tuple(-3, -1));
        EntityVillager.blacksmithSellingList.put(Item.porkCooked.itemID, new Tuple(-7, -5));
        EntityVillager.blacksmithSellingList.put(Item.beefCooked.itemID, new Tuple(-7, -5));
        EntityVillager.blacksmithSellingList.put(Item.chickenCooked.itemID, new Tuple(-8, -6));
        EntityVillager.blacksmithSellingList.put(Item.eyeOfEnder.itemID, new Tuple(7, 11));
        EntityVillager.blacksmithSellingList.put(Item.arrow.itemID, new Tuple(-12, -8));
    }
    
    public EntityVillager(final World par1World) {
        this(par1World, 0);
    }
    
    public EntityVillager(final World par1World, final int par2) {
        super(par1World);
        this.randomTickDivider = 0;
        this.isMating = false;
        this.isPlaying = false;
        this.villageObj = null;
        this.setProfession(par2);
        this.texture = "/mob/villager/villager.png";
        this.moveSpeed = 0.5f;
        this.setSize(0.6f, 1.8f);
        this.getNavigator().setBreakDoors(true);
        this.getNavigator().setAvoidsWater(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0f, 0.3f, 0.35f));
        this.tasks.addTask(1, new EntityAITradePlayer(this));
        this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
        this.tasks.addTask(2, new EntityAIMoveIndoors(this));
        this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
        this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
        this.tasks.addTask(5, new EntityAIMoveTwardsRestriction(this, 0.3f));
        this.tasks.addTask(6, new EntityAIVillagerMate(this));
        this.tasks.addTask(7, new EntityAIFollowGolem(this));
        this.tasks.addTask(8, new EntityAIPlay(this, 0.32f));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0f, 1.0f));
        this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityVillager.class, 5.0f, 0.02f));
        this.tasks.addTask(9, new EntityAIWander(this, 0.3f));
        this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0f));
    }
    
    public boolean isAIEnabled() {
        return true;
    }
    
    @Override
    protected void updateAITick() {
        final int randomTickDivider = this.randomTickDivider - 1;
        this.randomTickDivider = randomTickDivider;
        if (randomTickDivider <= 0) {
            this.worldObj.villageCollectionObj.addVillagerPosition(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ));
            this.randomTickDivider = 70 + this.rand.nextInt(50);
            this.villageObj = this.worldObj.villageCollectionObj.findNearestVillage(MathHelper.floor_double(this.posX), MathHelper.floor_double(this.posY), MathHelper.floor_double(this.posZ), 32);
            if (this.villageObj == null) {
                this.detachHome();
            }
            else {
                final ChunkCoordinates var1 = this.villageObj.getCenter();
                this.setHomeArea(var1.posX, var1.posY, var1.posZ, (int)(this.villageObj.getVillageRadius() * 0.6f));
                if (this.field_82190_bM) {
                    this.field_82190_bM = false;
                    this.villageObj.func_82683_b(5);
                }
            }
        }
        if (!this.isTrading() && this.timeUntilReset > 0) {
            --this.timeUntilReset;
            if (this.timeUntilReset <= 0) {
                if (this.needsInitilization) {
                    if (this.buyingList.size() > 1) {
                        for (final MerchantRecipe var3 : this.buyingList) {
                            if (var3.func_82784_g()) {
                                var3.func_82783_a(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                            }
                        }
                    }
                    this.addDefaultEquipmentAndRecipies(1);
                    this.needsInitilization = false;
                    if (this.villageObj != null && this.lastBuyingPlayer != null) {
                        this.worldObj.setEntityState(this, (byte)14);
                        this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
                    }
                }
                this.addPotionEffect(new PotionEffect(Potion.regeneration.id, 200, 0));
            }
        }
        super.updateAITick();
    }
    
    @Override
    public boolean interact(final EntityPlayer par1EntityPlayer) {
        final ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        final boolean var3 = var2 != null && var2.itemID == Item.monsterPlacer.itemID;
        if (!var3 && this.isEntityAlive() && !this.isTrading() && !this.isChild()) {
            if (!this.worldObj.isRemote) {
                this.setCustomer(par1EntityPlayer);
                par1EntityPlayer.displayGUIMerchant(this, this.func_94057_bL());
            }
            return true;
        }
        return super.interact(par1EntityPlayer);
    }
    
    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataWatcher.addObject(16, 0);
    }
    
    @Override
    public int getMaxHealth() {
        return 20;
    }
    
    @Override
    public void writeEntityToNBT(final NBTTagCompound par1NBTTagCompound) {
        super.writeEntityToNBT(par1NBTTagCompound);
        par1NBTTagCompound.setInteger("Profession", this.getProfession());
        par1NBTTagCompound.setInteger("Riches", this.wealth);
        if (this.buyingList != null) {
            par1NBTTagCompound.setCompoundTag("Offers", this.buyingList.getRecipiesAsTags());
        }
    }
    
    @Override
    public void readEntityFromNBT(final NBTTagCompound par1NBTTagCompound) {
        super.readEntityFromNBT(par1NBTTagCompound);
        this.setProfession(par1NBTTagCompound.getInteger("Profession"));
        this.wealth = par1NBTTagCompound.getInteger("Riches");
        if (par1NBTTagCompound.hasKey("Offers")) {
            final NBTTagCompound var2 = par1NBTTagCompound.getCompoundTag("Offers");
            this.buyingList = new MerchantRecipeList(var2);
        }
    }
    
    @Override
    public String getTexture() {
        switch (this.getProfession()) {
            case 0: {
                return "/mob/villager/farmer.png";
            }
            case 1: {
                return "/mob/villager/librarian.png";
            }
            case 2: {
                return "/mob/villager/priest.png";
            }
            case 3: {
                return "/mob/villager/smith.png";
            }
            case 4: {
                return "/mob/villager/butcher.png";
            }
            default: {
                return super.getTexture();
            }
        }
    }
    
    @Override
    protected boolean canDespawn() {
        return false;
    }
    
    @Override
    protected String getLivingSound() {
        return "mob.villager.default";
    }
    
    @Override
    protected String getHurtSound() {
        return "mob.villager.defaulthurt";
    }
    
    @Override
    protected String getDeathSound() {
        return "mob.villager.defaultdeath";
    }
    
    public void setProfession(final int par1) {
        this.dataWatcher.updateObject(16, par1);
    }
    
    public int getProfession() {
        return this.dataWatcher.getWatchableObjectInt(16);
    }
    
    public boolean isMating() {
        return this.isMating;
    }
    
    public void setMating(final boolean par1) {
        this.isMating = par1;
    }
    
    public void setPlaying(final boolean par1) {
        this.isPlaying = par1;
    }
    
    public boolean isPlaying() {
        return this.isPlaying;
    }
    
    @Override
    public void setRevengeTarget(final EntityLiving par1EntityLiving) {
        super.setRevengeTarget(par1EntityLiving);
        if (this.villageObj != null && par1EntityLiving != null) {
            this.villageObj.addOrRenewAgressor(par1EntityLiving);
            if (par1EntityLiving instanceof EntityPlayer) {
                byte var2 = -1;
                if (this.isChild()) {
                    var2 = -3;
                }
                this.villageObj.setReputationForPlayer(((EntityPlayer)par1EntityLiving).getCommandSenderName(), var2);
                if (this.isEntityAlive()) {
                    this.worldObj.setEntityState(this, (byte)13);
                }
            }
        }
    }
    
    @Override
    public void onDeath(final DamageSource par1DamageSource) {
        if (this.villageObj != null) {
            final Entity var2 = par1DamageSource.getEntity();
            if (var2 != null) {
                if (var2 instanceof EntityPlayer) {
                    this.villageObj.setReputationForPlayer(((EntityPlayer)var2).getCommandSenderName(), -2);
                }
                else if (var2 instanceof IMob) {
                    this.villageObj.endMatingSeason();
                }
            }
            else if (var2 == null) {
                final EntityPlayer var3 = this.worldObj.getClosestPlayerToEntity(this, 16.0);
                if (var3 != null) {
                    this.villageObj.endMatingSeason();
                }
            }
        }
        super.onDeath(par1DamageSource);
    }
    
    @Override
    public void setCustomer(final EntityPlayer par1EntityPlayer) {
        this.buyingPlayer = par1EntityPlayer;
    }
    
    @Override
    public EntityPlayer getCustomer() {
        return this.buyingPlayer;
    }
    
    public boolean isTrading() {
        return this.buyingPlayer != null;
    }
    
    @Override
    public void useRecipe(final MerchantRecipe par1MerchantRecipe) {
        par1MerchantRecipe.incrementToolUses();
        if (par1MerchantRecipe.hasSameIDsAs(this.buyingList.get(this.buyingList.size() - 1))) {
            this.timeUntilReset = 40;
            this.needsInitilization = true;
            if (this.buyingPlayer != null) {
                this.lastBuyingPlayer = this.buyingPlayer.getCommandSenderName();
            }
            else {
                this.lastBuyingPlayer = null;
            }
        }
        if (par1MerchantRecipe.getItemToBuy().itemID == Item.emerald.itemID) {
            this.wealth += par1MerchantRecipe.getItemToBuy().stackSize;
        }
    }
    
    @Override
    public MerchantRecipeList getRecipes(final EntityPlayer par1EntityPlayer) {
        if (this.buyingList == null) {
            this.addDefaultEquipmentAndRecipies(1);
        }
        return this.buyingList;
    }
    
    private float func_82188_j(final float par1) {
        final float var2 = par1 + this.field_82191_bN;
        return (var2 > 0.9f) ? (0.9f - (var2 - 0.9f)) : var2;
    }
    
    private void addDefaultEquipmentAndRecipies(final int par1) {
        if (this.buyingList != null) {
            this.field_82191_bN = MathHelper.sqrt_float(this.buyingList.size()) * 0.2f;
        }
        else {
            this.field_82191_bN = 0.0f;
        }
        final MerchantRecipeList var2 = new MerchantRecipeList();
        switch (this.getProfession()) {
            case 0: {
                addMerchantItem(var2, Item.wheat.itemID, this.rand, this.func_82188_j(0.9f));
                addMerchantItem(var2, Block.cloth.blockID, this.rand, this.func_82188_j(0.5f));
                addMerchantItem(var2, Item.chickenRaw.itemID, this.rand, this.func_82188_j(0.5f));
                addMerchantItem(var2, Item.fishCooked.itemID, this.rand, this.func_82188_j(0.4f));
                addBlacksmithItem(var2, Item.bread.itemID, this.rand, this.func_82188_j(0.9f));
                addBlacksmithItem(var2, Item.melon.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.appleRed.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.cookie.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.shears.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.flintAndSteel.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.chickenCooked.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.arrow.itemID, this.rand, this.func_82188_j(0.5f));
                if (this.rand.nextFloat() < this.func_82188_j(0.5f)) {
                    var2.add(new MerchantRecipe(new ItemStack(Block.gravel, 10), new ItemStack(Item.emerald), new ItemStack(Item.flint.itemID, 4 + this.rand.nextInt(2), 0)));
                    break;
                }
                break;
            }
            case 1: {
                addMerchantItem(var2, Item.paper.itemID, this.rand, this.func_82188_j(0.8f));
                addMerchantItem(var2, Item.book.itemID, this.rand, this.func_82188_j(0.8f));
                addMerchantItem(var2, Item.writtenBook.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Block.bookShelf.blockID, this.rand, this.func_82188_j(0.8f));
                addBlacksmithItem(var2, Block.glass.blockID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.compass.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.pocketSundial.itemID, this.rand, this.func_82188_j(0.2f));
                if (this.rand.nextFloat() < this.func_82188_j(0.07f)) {
                    final Enchantment var3 = Enchantment.field_92090_c[this.rand.nextInt(Enchantment.field_92090_c.length)];
                    final int var4 = MathHelper.getRandomIntegerInRange(this.rand, var3.getMinLevel(), var3.getMaxLevel());
                    final ItemStack var5 = Item.enchantedBook.func_92111_a(new EnchantmentData(var3, var4));
                    final int var6 = 2 + this.rand.nextInt(5 + var4 * 10) + 3 * var4;
                    var2.add(new MerchantRecipe(new ItemStack(Item.book), new ItemStack(Item.emerald, var6), var5));
                    break;
                }
                break;
            }
            case 2: {
                addBlacksmithItem(var2, Item.eyeOfEnder.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.expBottle.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.redstone.itemID, this.rand, this.func_82188_j(0.4f));
                addBlacksmithItem(var2, Block.glowStone.blockID, this.rand, this.func_82188_j(0.3f));
                final int[] var8;
                final int[] var7 = var8 = new int[] { Item.swordIron.itemID, Item.swordDiamond.itemID, Item.plateIron.itemID, Item.plateDiamond.itemID, Item.axeIron.itemID, Item.axeDiamond.itemID, Item.pickaxeIron.itemID, Item.pickaxeDiamond.itemID };
                for (int var9 = var7.length, var6 = 0; var6 < var9; ++var6) {
                    final int var10 = var8[var6];
                    if (this.rand.nextFloat() < this.func_82188_j(0.05f)) {
                        var2.add(new MerchantRecipe(new ItemStack(var10, 1, 0), new ItemStack(Item.emerald, 2 + this.rand.nextInt(3), 0), EnchantmentHelper.addRandomEnchantment(this.rand, new ItemStack(var10, 1, 0), 5 + this.rand.nextInt(15))));
                    }
                }
                break;
            }
            case 3: {
                addMerchantItem(var2, Item.coal.itemID, this.rand, this.func_82188_j(0.7f));
                addMerchantItem(var2, Item.ingotIron.itemID, this.rand, this.func_82188_j(0.5f));
                addMerchantItem(var2, Item.ingotGold.itemID, this.rand, this.func_82188_j(0.5f));
                addMerchantItem(var2, Item.diamond.itemID, this.rand, this.func_82188_j(0.5f));
                addBlacksmithItem(var2, Item.swordIron.itemID, this.rand, this.func_82188_j(0.5f));
                addBlacksmithItem(var2, Item.swordDiamond.itemID, this.rand, this.func_82188_j(0.5f));
                addBlacksmithItem(var2, Item.axeIron.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.axeDiamond.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.pickaxeIron.itemID, this.rand, this.func_82188_j(0.5f));
                addBlacksmithItem(var2, Item.pickaxeDiamond.itemID, this.rand, this.func_82188_j(0.5f));
                addBlacksmithItem(var2, Item.shovelIron.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.shovelDiamond.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.hoeIron.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.hoeDiamond.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.bootsIron.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.bootsDiamond.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.helmetIron.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.helmetDiamond.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.plateIron.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.plateDiamond.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.legsIron.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.legsDiamond.itemID, this.rand, this.func_82188_j(0.2f));
                addBlacksmithItem(var2, Item.bootsChain.itemID, this.rand, this.func_82188_j(0.1f));
                addBlacksmithItem(var2, Item.helmetChain.itemID, this.rand, this.func_82188_j(0.1f));
                addBlacksmithItem(var2, Item.plateChain.itemID, this.rand, this.func_82188_j(0.1f));
                addBlacksmithItem(var2, Item.legsChain.itemID, this.rand, this.func_82188_j(0.1f));
                break;
            }
            case 4: {
                addMerchantItem(var2, Item.coal.itemID, this.rand, this.func_82188_j(0.7f));
                addMerchantItem(var2, Item.porkRaw.itemID, this.rand, this.func_82188_j(0.5f));
                addMerchantItem(var2, Item.beefRaw.itemID, this.rand, this.func_82188_j(0.5f));
                addBlacksmithItem(var2, Item.saddle.itemID, this.rand, this.func_82188_j(0.1f));
                addBlacksmithItem(var2, Item.plateLeather.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.bootsLeather.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.helmetLeather.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.legsLeather.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.porkCooked.itemID, this.rand, this.func_82188_j(0.3f));
                addBlacksmithItem(var2, Item.beefCooked.itemID, this.rand, this.func_82188_j(0.3f));
                break;
            }
        }
        if (var2.isEmpty()) {
            addMerchantItem(var2, Item.ingotGold.itemID, this.rand, 1.0f);
        }
        Collections.shuffle(var2);
        if (this.buyingList == null) {
            this.buyingList = new MerchantRecipeList();
        }
        for (int var11 = 0; var11 < par1 && var11 < var2.size(); ++var11) {
            this.buyingList.addToListWithCheck(var2.get(var11));
        }
    }
    
    @Override
    public void setRecipes(final MerchantRecipeList par1MerchantRecipeList) {
    }
    
    private static void addMerchantItem(final MerchantRecipeList par0MerchantRecipeList, final int par1, final Random par2Random, final float par3) {
        if (par2Random.nextFloat() < par3) {
            par0MerchantRecipeList.add(new MerchantRecipe(getRandomSizedStack(par1, par2Random), Item.emerald));
        }
    }
    
    private static ItemStack getRandomSizedStack(final int par0, final Random par1Random) {
        return new ItemStack(par0, getRandomCountForItem(par0, par1Random), 0);
    }
    
    private static int getRandomCountForItem(final int par0, final Random par1Random) {
        final Tuple var2 = EntityVillager.villagerStockList.get(par0);
        return (int)((var2 == null) ? 1 : (((int)var2.getFirst() >= (int)var2.getSecond()) ? var2.getFirst() : ((int)var2.getFirst() + par1Random.nextInt((int)var2.getSecond() - (int)var2.getFirst()))));
    }
    
    private static void addBlacksmithItem(final MerchantRecipeList par0MerchantRecipeList, final int par1, final Random par2Random, final float par3) {
        if (par2Random.nextFloat() < par3) {
            final int var4 = getRandomCountForBlacksmithItem(par1, par2Random);
            ItemStack var5;
            ItemStack var6;
            if (var4 < 0) {
                var5 = new ItemStack(Item.emerald.itemID, 1, 0);
                var6 = new ItemStack(par1, -var4, 0);
            }
            else {
                var5 = new ItemStack(Item.emerald.itemID, var4, 0);
                var6 = new ItemStack(par1, 1, 0);
            }
            par0MerchantRecipeList.add(new MerchantRecipe(var5, var6));
        }
    }
    
    private static int getRandomCountForBlacksmithItem(final int par0, final Random par1Random) {
        final Tuple var2 = EntityVillager.blacksmithSellingList.get(par0);
        return (int)((var2 == null) ? 1 : (((int)var2.getFirst() >= (int)var2.getSecond()) ? var2.getFirst() : ((int)var2.getFirst() + par1Random.nextInt((int)var2.getSecond() - (int)var2.getFirst()))));
    }
    
    @Override
    public void handleHealthUpdate(final byte par1) {
        if (par1 == 12) {
            this.generateRandomParticles("heart");
        }
        else if (par1 == 13) {
            this.generateRandomParticles("angryVillager");
        }
        else if (par1 == 14) {
            this.generateRandomParticles("happyVillager");
        }
        else {
            super.handleHealthUpdate(par1);
        }
    }
    
    private void generateRandomParticles(final String par1Str) {
        for (int var2 = 0; var2 < 5; ++var2) {
            final double var3 = this.rand.nextGaussian() * 0.02;
            final double var4 = this.rand.nextGaussian() * 0.02;
            final double var5 = this.rand.nextGaussian() * 0.02;
            this.worldObj.spawnParticle(par1Str, this.posX + this.rand.nextFloat() * this.width * 2.0f - this.width, this.posY + 1.0 + this.rand.nextFloat() * this.height, this.posZ + this.rand.nextFloat() * this.width * 2.0f - this.width, var3, var4, var5);
        }
    }
    
    @Override
    public void initCreature() {
        this.setProfession(this.worldObj.rand.nextInt(5));
    }
    
    public void func_82187_q() {
        this.field_82190_bM = true;
    }
    
    public EntityVillager func_90012_b(final EntityAgeable par1EntityAgeable) {
        final EntityVillager var2 = new EntityVillager(this.worldObj);
        var2.initCreature();
        return var2;
    }
    
    @Override
    public EntityAgeable createChild(final EntityAgeable par1EntityAgeable) {
        return this.func_90012_b(par1EntityAgeable);
    }
}
