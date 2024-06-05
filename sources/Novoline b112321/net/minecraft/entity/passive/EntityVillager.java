package net.minecraft.entity.passive;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.INpc;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAIFollowGolem;
import net.minecraft.entity.ai.EntityAIHarvestFarmland;
import net.minecraft.entity.ai.EntityAILookAtTradePlayer;
import net.minecraft.entity.ai.EntityAIMoveIndoors;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIOpenDoor;
import net.minecraft.entity.ai.EntityAIPlay;
import net.minecraft.entity.ai.EntityAIRestrictOpenDoor;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.passive.EntityVillager$EmeraldForItems;
import net.minecraft.entity.passive.EntityVillager$ITradeList;
import net.minecraft.entity.passive.EntityVillager$ItemAndEmeraldToItem;
import net.minecraft.entity.passive.EntityVillager$ListEnchantedBookForEmeralds;
import net.minecraft.entity.passive.EntityVillager$ListEnchantedItemForEmeralds;
import net.minecraft.entity.passive.EntityVillager$ListItemForEmeralds;
import net.minecraft.entity.passive.EntityVillager$PriceInfo;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityVillager extends EntityAgeable implements IMerchant, INpc {
   private int randomTickDivider;
   private boolean isMating;
   private boolean isPlaying;
   Village villageObj;
   private EntityPlayer buyingPlayer;
   private MerchantRecipeList buyingList;
   private int timeUntilReset;
   private boolean needsInitilization;
   private boolean isWillingToMate;
   private int wealth;
   private String lastBuyingPlayer;
   private int careerId;
   private int careerLevel;
   private boolean isLookingForHome;
   private boolean areAdditionalTasksSet;
   private InventoryBasic villagerInventory;
   private static final EntityVillager$ITradeList[][][][] DEFAULT_TRADE_LIST_MAP = new EntityVillager$ITradeList[][][][]{{{{new EntityVillager$EmeraldForItems(Items.wheat, new EntityVillager$PriceInfo(18, 22)), new EntityVillager$EmeraldForItems(Items.potato, new EntityVillager$PriceInfo(15, 19)), new EntityVillager$EmeraldForItems(Items.carrot, new EntityVillager$PriceInfo(15, 19)), new EntityVillager$ListItemForEmeralds(Items.bread, new EntityVillager$PriceInfo(-4, -2))}, {new EntityVillager$EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new EntityVillager$PriceInfo(8, 13)), new EntityVillager$ListItemForEmeralds(Items.pumpkin_pie, new EntityVillager$PriceInfo(-3, -2))}, {new EntityVillager$EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new EntityVillager$PriceInfo(7, 12)), new EntityVillager$ListItemForEmeralds(Items.apple, new EntityVillager$PriceInfo(-5, -7))}, {new EntityVillager$ListItemForEmeralds(Items.cookie, new EntityVillager$PriceInfo(-6, -10)), new EntityVillager$ListItemForEmeralds(Items.cake, new EntityVillager$PriceInfo(1, 1))}}, {{new EntityVillager$EmeraldForItems(Items.string, new EntityVillager$PriceInfo(15, 20)), new EntityVillager$EmeraldForItems(Items.coal, new EntityVillager$PriceInfo(16, 24)), new EntityVillager$ItemAndEmeraldToItem(Items.fish, new EntityVillager$PriceInfo(6, 6), Items.cooked_fish, new EntityVillager$PriceInfo(6, 6))}, {new EntityVillager$ListEnchantedItemForEmeralds(Items.fishing_rod, new EntityVillager$PriceInfo(7, 8))}}, {{new EntityVillager$EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new EntityVillager$PriceInfo(16, 22)), new EntityVillager$ListItemForEmeralds(Items.shears, new EntityVillager$PriceInfo(3, 4))}, {new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14), new EntityVillager$PriceInfo(1, 2)), new EntityVillager$ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), new EntityVillager$PriceInfo(1, 2))}}, {{new EntityVillager$EmeraldForItems(Items.string, new EntityVillager$PriceInfo(15, 20)), new EntityVillager$ListItemForEmeralds(Items.arrow, new EntityVillager$PriceInfo(-12, -8))}, {new EntityVillager$ListItemForEmeralds(Items.bow, new EntityVillager$PriceInfo(2, 3)), new EntityVillager$ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new EntityVillager$PriceInfo(10, 10), Items.flint, new EntityVillager$PriceInfo(6, 10))}}}, {{{new EntityVillager$EmeraldForItems(Items.paper, new EntityVillager$PriceInfo(24, 36)), new EntityVillager$ListEnchantedBookForEmeralds()}, {new EntityVillager$EmeraldForItems(Items.book, new EntityVillager$PriceInfo(8, 10)), new EntityVillager$ListItemForEmeralds(Items.compass, new EntityVillager$PriceInfo(10, 12)), new EntityVillager$ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new EntityVillager$PriceInfo(3, 4))}, {new EntityVillager$EmeraldForItems(Items.written_book, new EntityVillager$PriceInfo(2, 2)), new EntityVillager$ListItemForEmeralds(Items.clock, new EntityVillager$PriceInfo(10, 12)), new EntityVillager$ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new EntityVillager$PriceInfo(-5, -3))}, {new EntityVillager$ListEnchantedBookForEmeralds()}, {new EntityVillager$ListEnchantedBookForEmeralds()}, {new EntityVillager$ListItemForEmeralds(Items.name_tag, new EntityVillager$PriceInfo(20, 22))}}}, {{{new EntityVillager$EmeraldForItems(Items.rotten_flesh, new EntityVillager$PriceInfo(36, 40)), new EntityVillager$EmeraldForItems(Items.gold_ingot, new EntityVillager$PriceInfo(8, 10))}, {new EntityVillager$ListItemForEmeralds(Items.redstone, new EntityVillager$PriceInfo(-4, -1)), new EntityVillager$ListItemForEmeralds(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeDamage()), new EntityVillager$PriceInfo(-2, -1))}, {new EntityVillager$ListItemForEmeralds(Items.ender_eye, new EntityVillager$PriceInfo(7, 11)), new EntityVillager$ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new EntityVillager$PriceInfo(-3, -1))}, {new EntityVillager$ListItemForEmeralds(Items.experience_bottle, new EntityVillager$PriceInfo(3, 11))}}}, {{{new EntityVillager$EmeraldForItems(Items.coal, new EntityVillager$PriceInfo(16, 24)), new EntityVillager$ListItemForEmeralds(Items.iron_helmet, new EntityVillager$PriceInfo(4, 6))}, {new EntityVillager$EmeraldForItems(Items.iron_ingot, new EntityVillager$PriceInfo(7, 9)), new EntityVillager$ListItemForEmeralds(Items.iron_chestplate, new EntityVillager$PriceInfo(10, 14))}, {new EntityVillager$EmeraldForItems(Items.diamond, new EntityVillager$PriceInfo(3, 4)), new EntityVillager$ListEnchantedItemForEmeralds(Items.diamond_chestplate, new EntityVillager$PriceInfo(16, 19))}, {new EntityVillager$ListItemForEmeralds(Items.chainmail_boots, new EntityVillager$PriceInfo(5, 7)), new EntityVillager$ListItemForEmeralds(Items.chainmail_leggings, new EntityVillager$PriceInfo(9, 11)), new EntityVillager$ListItemForEmeralds(Items.chainmail_helmet, new EntityVillager$PriceInfo(5, 7)), new EntityVillager$ListItemForEmeralds(Items.chainmail_chestplate, new EntityVillager$PriceInfo(11, 15))}}, {{new EntityVillager$EmeraldForItems(Items.coal, new EntityVillager$PriceInfo(16, 24)), new EntityVillager$ListItemForEmeralds(Items.iron_axe, new EntityVillager$PriceInfo(6, 8))}, {new EntityVillager$EmeraldForItems(Items.iron_ingot, new EntityVillager$PriceInfo(7, 9)), new EntityVillager$ListEnchantedItemForEmeralds(Items.iron_sword, new EntityVillager$PriceInfo(9, 10))}, {new EntityVillager$EmeraldForItems(Items.diamond, new EntityVillager$PriceInfo(3, 4)), new EntityVillager$ListEnchantedItemForEmeralds(Items.diamond_sword, new EntityVillager$PriceInfo(12, 15)), new EntityVillager$ListEnchantedItemForEmeralds(Items.diamond_axe, new EntityVillager$PriceInfo(9, 12))}}, {{new EntityVillager$EmeraldForItems(Items.coal, new EntityVillager$PriceInfo(16, 24)), new EntityVillager$ListEnchantedItemForEmeralds(Items.iron_shovel, new EntityVillager$PriceInfo(5, 7))}, {new EntityVillager$EmeraldForItems(Items.iron_ingot, new EntityVillager$PriceInfo(7, 9)), new EntityVillager$ListEnchantedItemForEmeralds(Items.iron_pickaxe, new EntityVillager$PriceInfo(9, 11))}, {new EntityVillager$EmeraldForItems(Items.diamond, new EntityVillager$PriceInfo(3, 4)), new EntityVillager$ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new EntityVillager$PriceInfo(12, 15))}}}, {{{new EntityVillager$EmeraldForItems(Items.porkchop, new EntityVillager$PriceInfo(14, 18)), new EntityVillager$EmeraldForItems(Items.chicken, new EntityVillager$PriceInfo(14, 18))}, {new EntityVillager$EmeraldForItems(Items.coal, new EntityVillager$PriceInfo(16, 24)), new EntityVillager$ListItemForEmeralds(Items.cooked_porkchop, new EntityVillager$PriceInfo(-7, -5)), new EntityVillager$ListItemForEmeralds(Items.cooked_chicken, new EntityVillager$PriceInfo(-8, -6))}}, {{new EntityVillager$EmeraldForItems(Items.leather, new EntityVillager$PriceInfo(9, 12)), new EntityVillager$ListItemForEmeralds(Items.leather_leggings, new EntityVillager$PriceInfo(2, 4))}, {new EntityVillager$ListEnchantedItemForEmeralds(Items.leather_chestplate, new EntityVillager$PriceInfo(7, 12))}, {new EntityVillager$ListItemForEmeralds(Items.saddle, new EntityVillager$PriceInfo(8, 10))}}}};

   public EntityVillager(World var1) {
      this(var1, 0);
   }

   public EntityVillager(World var1, int var2) {
      super(var1);
      this.villagerInventory = new InventoryBasic("Items", false, 8);
      this.setProfession(var2);
      this.setSize(0.6F, 1.8F);
      ((PathNavigateGround)this.getNavigator()).setBreakDoors(true);
      ((PathNavigateGround)this.getNavigator()).setAvoidsWater(true);
      this.tasks.addTask(0, new EntityAISwimming(this));
      this.tasks.addTask(1, new EntityAIAvoidEntity(this, EntityZombie.class, 8.0F, 0.6D, 0.6D));
      this.tasks.addTask(1, new EntityAITradePlayer(this));
      this.tasks.addTask(1, new EntityAILookAtTradePlayer(this));
      this.tasks.addTask(2, new EntityAIMoveIndoors(this));
      this.tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
      this.tasks.addTask(4, new EntityAIOpenDoor(this, true));
      this.tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
      this.tasks.addTask(6, new EntityAIVillagerMate(this));
      this.tasks.addTask(7, new EntityAIFollowGolem(this));
      this.tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
      this.tasks.addTask(9, new EntityAIVillagerInteract(this));
      this.tasks.addTask(9, new EntityAIWander(this, 0.6D));
      this.tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
      this.setCanPickUpLoot(true);
   }

   private void setAdditionalAItasks() {
      if(!this.areAdditionalTasksSet) {
         this.areAdditionalTasksSet = true;
         if(this.isChild()) {
            this.tasks.addTask(8, new EntityAIPlay(this, 0.32D));
         } else if(this.getProfession() == 0) {
            this.tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
         }
      }

   }

   protected void onGrowingAdult() {
      if(this.getProfession() == 0) {
         this.tasks.addTask(8, new EntityAIHarvestFarmland(this, 0.6D));
      }

      super.onGrowingAdult();
   }

   protected void applyEntityAttributes() {
      super.applyEntityAttributes();
      this.getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
   }

   protected void updateAITasks() {
      if(--this.randomTickDivider <= 0) {
         BlockPos var1 = new BlockPos(this);
         this.worldObj.getVillageCollection().addToVillagerPositionList(var1);
         this.randomTickDivider = 70 + this.rand.nextInt(50);
         this.villageObj = this.worldObj.getVillageCollection().getNearestVillage(var1, 32);
         if(this.villageObj == null) {
            this.detachHome();
         } else {
            BlockPos var2 = this.villageObj.getCenter();
            this.setHomePosAndDistance(var2, (int)((float)this.villageObj.getVillageRadius() * 1.0F));
            if(this.isLookingForHome) {
               this.isLookingForHome = false;
               this.villageObj.setDefaultPlayerReputation(5);
            }
         }
      }

      if(!this.isTrading() && this.timeUntilReset > 0) {
         --this.timeUntilReset;
         if(this.timeUntilReset <= 0) {
            if(this.needsInitilization) {
               for(MerchantRecipe var4 : this.buyingList) {
                  if(var4.isRecipeDisabled()) {
                     var4.increaseMaxTradeUses(this.rand.nextInt(6) + this.rand.nextInt(6) + 2);
                  }
               }

               this.populateBuyingList();
               this.needsInitilization = false;
               if(this.villageObj != null && this.lastBuyingPlayer != null) {
                  this.worldObj.setEntityState(this, (byte)14);
                  this.villageObj.setReputationForPlayer(this.lastBuyingPlayer, 1);
               }
            }

            this.addPotionEffect(new PotionEffect(Potion.regeneration.getId(), 200, 0));
         }
      }

      super.updateAITasks();
   }

   public boolean interact(EntityPlayer var1) {
      ItemStack var2 = var1.inventory.getCurrentItem();
      boolean var3 = var2.getItem() == Items.spawn_egg;
      if(this.isEntityAlive() && !this.isTrading() && !this.isChild()) {
         if(!this.worldObj.isRemote && (this.buyingList == null || !this.buyingList.isEmpty())) {
            this.setCustomer(var1);
            var1.displayVillagerTradeGui(this);
         }

         var1.triggerAchievement(StatList.timesTalkedToVillagerStat);
         return true;
      } else {
         return super.interact(var1);
      }
   }

   protected void entityInit() {
      super.entityInit();
      this.I.b(16, Integer.valueOf(0));
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("Profession", this.getProfession());
      var1.setInteger("Riches", this.wealth);
      var1.setInteger("Career", this.careerId);
      var1.setInteger("CareerLevel", this.careerLevel);
      var1.setBoolean("Willing", this.isWillingToMate);
      if(this.buyingList != null) {
         var1.setTag("Offers", this.buyingList.getRecipiesAsTags());
      }

      NBTTagList var2 = new NBTTagList();

      for(int var3 = 0; var3 < this.villagerInventory.getSizeInventory(); ++var3) {
         ItemStack var4 = this.villagerInventory.getStackInSlot(var3);
         var2.appendTag(var4.writeToNBT(new NBTTagCompound()));
      }

      var1.setTag("Inventory", var2);
   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      this.setProfession(var1.getInteger("Profession"));
      this.wealth = var1.getInteger("Riches");
      this.careerId = var1.getInteger("Career");
      this.careerLevel = var1.getInteger("CareerLevel");
      this.isWillingToMate = var1.getBoolean("Willing");
      if(var1.hasKey("Offers", 10)) {
         NBTTagCompound var2 = var1.getCompoundTag("Offers");
         this.buyingList = new MerchantRecipeList(var2);
      }

      NBTTagList var5 = var1.getTagList("Inventory", 10);

      for(int var3 = 0; var3 < var5.tagCount(); ++var3) {
         ItemStack var4 = ItemStack.loadItemStackFromNBT(var5.getCompoundTagAt(var3));
         this.villagerInventory.a(var4);
      }

      this.setCanPickUpLoot(true);
      this.setAdditionalAItasks();
   }

   protected boolean canDespawn() {
      return false;
   }

   protected String getLivingSound() {
      return this.isTrading()?"mob.villager.haggle":"mob.villager.idle";
   }

   protected String getHurtSound() {
      return "mob.villager.hit";
   }

   protected String getDeathSound() {
      return "mob.villager.death";
   }

   public void setProfession(int var1) {
      this.I.a(16, Integer.valueOf(var1));
   }

   public int getProfession() {
      return Math.max(this.I.c(16) % 5, 0);
   }

   public boolean isMating() {
      return this.isMating;
   }

   public void setMating(boolean var1) {
      this.isMating = var1;
   }

   public void setPlaying(boolean var1) {
      this.isPlaying = var1;
   }

   public boolean isPlaying() {
      return this.isPlaying;
   }

   public void setRevengeTarget(EntityLivingBase var1) {
      super.setRevengeTarget(var1);
      if(this.villageObj != null) {
         this.villageObj.addOrRenewAgressor(var1);
         if(var1 instanceof EntityPlayer) {
            byte var2 = -1;
            if(this.isChild()) {
               var2 = -3;
            }

            this.villageObj.setReputationForPlayer(var1.getName(), var2);
            if(this.isEntityAlive()) {
               this.worldObj.setEntityState(this, (byte)13);
            }
         }
      }

   }

   public void onDeath(DamageSource var1) {
      if(this.villageObj != null) {
         Entity var2 = var1.getEntity();
         if(var2 instanceof EntityPlayer) {
            this.villageObj.setReputationForPlayer(var2.getName(), -2);
         } else if(var2 instanceof IMob) {
            this.villageObj.endMatingSeason();
         }
      }

      super.onDeath(var1);
   }

   public void setCustomer(EntityPlayer var1) {
      this.buyingPlayer = var1;
   }

   public EntityPlayer getCustomer() {
      return this.buyingPlayer;
   }

   public boolean isTrading() {
      return this.buyingPlayer != null;
   }

   public boolean getIsWillingToMate(boolean var1) {
      if(!this.isWillingToMate && this.func_175553_cp()) {
         boolean var2 = false;
         byte var3 = 0;
         if(var3 < this.villagerInventory.getSizeInventory()) {
            ItemStack var4 = this.villagerInventory.getStackInSlot(var3);
            if(var4.getItem() == Items.bread && var4.stackSize >= 3) {
               var2 = true;
               this.villagerInventory.decrStackSize(var3, 3);
            } else if((var4.getItem() == Items.potato || var4.getItem() == Items.carrot) && var4.stackSize >= 12) {
               var2 = true;
               this.villagerInventory.decrStackSize(var3, 12);
            }

            this.worldObj.setEntityState(this, (byte)18);
            this.isWillingToMate = true;
         }
      }

      return this.isWillingToMate;
   }

   public void setIsWillingToMate(boolean var1) {
      this.isWillingToMate = var1;
   }

   public void useRecipe(MerchantRecipe var1) {
      var1.incrementToolUses();
      this.livingSoundTime = -this.getTalkInterval();
      this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
      int var2 = 3 + this.rand.nextInt(4);
      if(var1.getToolUses() == 1 || this.rand.nextInt(5) == 0) {
         this.timeUntilReset = 40;
         this.needsInitilization = true;
         this.isWillingToMate = true;
         if(this.buyingPlayer != null) {
            this.lastBuyingPlayer = this.buyingPlayer.getName();
         } else {
            this.lastBuyingPlayer = null;
         }

         var2 += 5;
      }

      if(var1.getItemToBuy().getItem() == Items.emerald) {
         this.wealth += var1.getItemToBuy().stackSize;
      }

      if(var1.getRewardsExp()) {
         this.worldObj.spawnEntityInWorld(new EntityXPOrb(this.worldObj, this.posX, this.posY + 0.5D, this.posZ, var2));
      }

   }

   public void verifySellingItem(ItemStack var1) {
      if(!this.worldObj.isRemote && this.livingSoundTime > -this.getTalkInterval() + 20) {
         this.livingSoundTime = -this.getTalkInterval();
         this.playSound("mob.villager.yes", this.getSoundVolume(), this.getSoundPitch());
      }

   }

   public MerchantRecipeList getRecipes(EntityPlayer var1) {
      if(this.buyingList == null) {
         this.populateBuyingList();
      }

      return this.buyingList;
   }

   private void populateBuyingList() {
      EntityVillager$ITradeList[][][] var1 = DEFAULT_TRADE_LIST_MAP[this.getProfession()];
      if(this.careerId != 0 && this.careerLevel != 0) {
         ++this.careerLevel;
      } else {
         this.careerId = this.rand.nextInt(var1.length) + 1;
         this.careerLevel = 1;
      }

      if(this.buyingList == null) {
         this.buyingList = new MerchantRecipeList();
      }

      int var2 = this.careerId - 1;
      int var3 = this.careerLevel - 1;
      EntityVillager$ITradeList[][] var4 = var1[var2];
      if(var3 < var4.length) {
         EntityVillager$ITradeList[] var5 = var4[var3];

         for(EntityVillager$ITradeList var9 : var5) {
            var9.modifyMerchantRecipeList(this.buyingList, this.rand);
         }
      }

   }

   public void setRecipes(MerchantRecipeList var1) {
   }

   public IChatComponent getDisplayName() {
      String var1 = this.getCustomNameTag();
      if(!var1.isEmpty()) {
         ChatComponentText var4 = new ChatComponentText(var1);
         var4.getChatStyle().setChatHoverEvent(this.getHoverEvent());
         var4.getChatStyle().setInsertion(this.getUniqueID().toString());
         return var4;
      } else {
         if(this.buyingList == null) {
            this.populateBuyingList();
         }

         String var2 = null;
         switch(this.getProfession()) {
         case 0:
            if(this.careerId == 1) {
               var2 = "farmer";
            } else if(this.careerId == 2) {
               var2 = "fisherman";
            } else if(this.careerId == 3) {
               var2 = "shepherd";
            } else if(this.careerId == 4) {
               var2 = "fletcher";
            }
            break;
         case 1:
            var2 = "librarian";
            break;
         case 2:
            var2 = "cleric";
            break;
         case 3:
            if(this.careerId == 1) {
               var2 = "armor";
            } else if(this.careerId == 2) {
               var2 = "weapon";
            } else if(this.careerId == 3) {
               var2 = "tool";
            }
            break;
         case 4:
            if(this.careerId == 1) {
               var2 = "butcher";
            } else if(this.careerId == 2) {
               var2 = "leather";
            }
         }

         ChatComponentTranslation var3 = new ChatComponentTranslation("entity.Villager." + var2, new Object[0]);
         var3.getChatStyle().setChatHoverEvent(this.getHoverEvent());
         var3.getChatStyle().setInsertion(this.getUniqueID().toString());
         return var3;
      }
   }

   public float getEyeHeight() {
      float var1 = 1.62F;
      if(this.isChild()) {
         var1 = (float)((double)var1 - 0.81D);
      }

      return var1;
   }

   public void handleStatusUpdate(byte var1) {
      if(var1 == 12) {
         this.spawnParticles(EnumParticleTypes.HEART);
      } else if(var1 == 13) {
         this.spawnParticles(EnumParticleTypes.VILLAGER_ANGRY);
      } else if(var1 == 14) {
         this.spawnParticles(EnumParticleTypes.VILLAGER_HAPPY);
      } else {
         super.handleStatusUpdate(var1);
      }

   }

   private void spawnParticles(EnumParticleTypes var1) {
      for(int var2 = 0; var2 < 5; ++var2) {
         double var3 = this.rand.nextGaussian() * 0.02D;
         double var5 = this.rand.nextGaussian() * 0.02D;
         double var7 = this.rand.nextGaussian() * 0.02D;
         this.worldObj.spawnParticle(var1, this.posX + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, this.posY + 1.0D + (double)(this.rand.nextFloat() * this.height), this.posZ + (double)(this.rand.nextFloat() * this.width * 2.0F) - (double)this.width, var3, var5, var7, new int[0]);
      }

   }

   public IEntityLivingData onInitialSpawn(DifficultyInstance var1, IEntityLivingData var2) {
      var2 = super.onInitialSpawn(var1, var2);
      this.setProfession(this.worldObj.rand.nextInt(5));
      this.setAdditionalAItasks();
      return var2;
   }

   public void setLookingForHome() {
      this.isLookingForHome = true;
   }

   public EntityVillager createChild(EntityAgeable var1) {
      EntityVillager var2 = new EntityVillager(this.worldObj);
      var2.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(var2)), (IEntityLivingData)null);
      return var2;
   }

   public boolean allowLeashing() {
      return false;
   }

   public void onStruckByLightning(EntityLightningBolt var1) {
      if(!this.worldObj.isRemote && !this.isDead) {
         EntityWitch var2 = new EntityWitch(this.worldObj);
         var2.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
         var2.onInitialSpawn(this.worldObj.getDifficultyForLocation(new BlockPos(var2)), (IEntityLivingData)null);
         var2.setNoAI(this.isAIDisabled());
         if(this.hasCustomName()) {
            var2.setCustomNameTag(this.getCustomNameTag());
            var2.setAlwaysRenderNameTag(this.getAlwaysRenderNameTag());
         }

         this.worldObj.spawnEntityInWorld(var2);
         this.setDead();
      }

   }

   public InventoryBasic getVillagerInventory() {
      return this.villagerInventory;
   }

   protected void updateEquipmentIfNeeded(EntityItem var1) {
      ItemStack var2 = var1.getEntityItem();
      Item var3 = var2.getItem();
      if(this.canVillagerPickupItem(var3)) {
         ItemStack var4 = this.villagerInventory.a(var2);
         var1.setDead();
      }

   }

   private boolean canVillagerPickupItem(Item var1) {
      return var1 == Items.bread || var1 == Items.potato || var1 == Items.carrot || var1 == Items.wheat || var1 == Items.wheat_seeds;
   }

   public boolean func_175553_cp() {
      return this.hasEnoughItems(1);
   }

   public boolean canAbondonItems() {
      return this.hasEnoughItems(2);
   }

   public boolean func_175557_cr() {
      boolean var1 = this.getProfession() == 0;
      return !this.hasEnoughItems(5);
   }

   private boolean hasEnoughItems(int var1) {
      boolean var2 = this.getProfession() == 0;

      for(int var3 = 0; var3 < this.villagerInventory.getSizeInventory(); ++var3) {
         ItemStack var4 = this.villagerInventory.getStackInSlot(var3);
         if(var4.getItem() == Items.bread && var4.stackSize >= 3 * var1 || var4.getItem() == Items.potato && var4.stackSize >= 12 * var1 || var4.getItem() == Items.carrot && var4.stackSize >= 12 * var1) {
            return true;
         }

         if(var4.getItem() == Items.wheat && var4.stackSize >= 9 * var1) {
            return true;
         }
      }

      return false;
   }

   public boolean isFarmItemInInventory() {
      for(int var1 = 0; var1 < this.villagerInventory.getSizeInventory(); ++var1) {
         ItemStack var2 = this.villagerInventory.getStackInSlot(var1);
         if(var2.getItem() == Items.wheat_seeds || var2.getItem() == Items.potato || var2.getItem() == Items.carrot) {
            return true;
         }
      }

      return false;
   }

   public boolean replaceItemInInventory(int var1, ItemStack var2) {
      if(super.replaceItemInInventory(var1, var2)) {
         return true;
      } else {
         int var3 = var1 - 300;
         if(var3 < this.villagerInventory.getSizeInventory()) {
            this.villagerInventory.setInventorySlotContents(var3, var2);
            return true;
         } else {
            return false;
         }
      }
   }
}
