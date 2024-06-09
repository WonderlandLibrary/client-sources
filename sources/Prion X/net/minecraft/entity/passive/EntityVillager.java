package net.minecraft.entity.passive;

import com.google.common.base.Predicate;
import java.util.Iterator;
import java.util.Random;
import java.util.UUID;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.DataWatcher;
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
import net.minecraft.entity.ai.EntityAITasks;
import net.minecraft.entity.ai.EntityAITradePlayer;
import net.minecraft.entity.ai.EntityAIVillagerInteract;
import net.minecraft.entity.ai.EntityAIVillagerMate;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityAIWatchClosest2;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityWitch;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
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
import net.minecraft.util.ChatStyle;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Tuple;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.village.Village;
import net.minecraft.village.VillageCollection;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

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
  private boolean field_175565_bs;
  private int wealth;
  private String lastBuyingPlayer;
  private int field_175563_bv;
  private int field_175562_bw;
  private boolean isLookingForHome;
  private boolean field_175564_by;
  private InventoryBasic field_175560_bz;
  private static final ITradeList[][][][] field_175561_bA = { { { { new EmeraldForItems(Items.wheat, new PriceInfo(18, 22)), new EmeraldForItems(Items.potato, new PriceInfo(15, 19)), new EmeraldForItems(Items.carrot, new PriceInfo(15, 19)), new ListItemForEmeralds(Items.bread, new PriceInfo(-4, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.pumpkin), new PriceInfo(8, 13)), new ListItemForEmeralds(Items.pumpkin_pie, new PriceInfo(-3, -2)) }, { new EmeraldForItems(Item.getItemFromBlock(Blocks.melon_block), new PriceInfo(7, 12)), new ListItemForEmeralds(Items.apple, new PriceInfo(-5, -7)) }, { new ListItemForEmeralds(Items.cookie, new PriceInfo(-6, -10)), new ListItemForEmeralds(Items.cake, new PriceInfo(1, 1)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ItemAndEmeraldToItem(Items.fish, new PriceInfo(6, 6), Items.cooked_fish, new PriceInfo(6, 6)) }, { new ListEnchantedItemForEmeralds(Items.fishing_rod, new PriceInfo(7, 8)) } }, { { new EmeraldForItems(Item.getItemFromBlock(Blocks.wool), new PriceInfo(16, 22)), new ListItemForEmeralds(Items.shears, new PriceInfo(3, 4)) }, { new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 0), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 1), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 2), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 3), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 4), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 5), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 6), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 7), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 8), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 9), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 10), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 11), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 12), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 13), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 14), new PriceInfo(1, 2)), new ListItemForEmeralds(new ItemStack(Item.getItemFromBlock(Blocks.wool), 1, 15), new PriceInfo(1, 2)) } }, { { new EmeraldForItems(Items.string, new PriceInfo(15, 20)), new ListItemForEmeralds(Items.arrow, new PriceInfo(-12, -8)) }, { new ListItemForEmeralds(Items.bow, new PriceInfo(2, 3)), new ItemAndEmeraldToItem(Item.getItemFromBlock(Blocks.gravel), new PriceInfo(10, 10), Items.flint, new PriceInfo(6, 10)) } } }, { { { new EmeraldForItems(Items.paper, new PriceInfo(24, 36)), new ListEnchantedBookForEmeralds() }, { new EmeraldForItems(Items.book, new PriceInfo(8, 10)), new ListItemForEmeralds(Items.compass, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.bookshelf), new PriceInfo(3, 4)) }, { new EmeraldForItems(Items.written_book, new PriceInfo(2, 2)), new ListItemForEmeralds(Items.clock, new PriceInfo(10, 12)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glass), new PriceInfo(-5, -3)) }, { new ListEnchantedBookForEmeralds() }, { new ListEnchantedBookForEmeralds() }, { new ListItemForEmeralds(Items.name_tag, new PriceInfo(20, 22)) } } }, { { { new EmeraldForItems(Items.rotten_flesh, new PriceInfo(36, 40)), new EmeraldForItems(Items.gold_ingot, new PriceInfo(8, 10)) }, { new ListItemForEmeralds(Items.redstone, new PriceInfo(-4, -1)), new ListItemForEmeralds(new ItemStack(Items.dye, 1, EnumDyeColor.BLUE.getDyeColorDamage()), new PriceInfo(-2, -1)) }, { new ListItemForEmeralds(Items.ender_eye, new PriceInfo(7, 11)), new ListItemForEmeralds(Item.getItemFromBlock(Blocks.glowstone), new PriceInfo(-3, -1)) }, { new ListItemForEmeralds(Items.experience_bottle, new PriceInfo(3, 11)) } } }, { { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_helmet, new PriceInfo(4, 6)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListItemForEmeralds(Items.iron_chestplate, new PriceInfo(10, 14)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_chestplate, new PriceInfo(16, 19)) }, { new ListItemForEmeralds(Items.chainmail_boots, new PriceInfo(5, 7)), new ListItemForEmeralds(Items.chainmail_leggings, new PriceInfo(9, 11)), new ListItemForEmeralds(Items.chainmail_helmet, new PriceInfo(5, 7)), new ListItemForEmeralds(Items.chainmail_chestplate, new PriceInfo(11, 15)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.iron_axe, new PriceInfo(6, 8)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_sword, new PriceInfo(9, 10)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_sword, new PriceInfo(12, 15)), new ListEnchantedItemForEmeralds(Items.diamond_axe, new PriceInfo(9, 12)) } }, { { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListEnchantedItemForEmeralds(Items.iron_shovel, new PriceInfo(5, 7)) }, { new EmeraldForItems(Items.iron_ingot, new PriceInfo(7, 9)), new ListEnchantedItemForEmeralds(Items.iron_pickaxe, new PriceInfo(9, 11)) }, { new EmeraldForItems(Items.diamond, new PriceInfo(3, 4)), new ListEnchantedItemForEmeralds(Items.diamond_pickaxe, new PriceInfo(12, 15)) } } }, { { { new EmeraldForItems(Items.porkchop, new PriceInfo(14, 18)), new EmeraldForItems(Items.chicken, new PriceInfo(14, 18)) }, { new EmeraldForItems(Items.coal, new PriceInfo(16, 24)), new ListItemForEmeralds(Items.cooked_porkchop, new PriceInfo(-7, -5)), new ListItemForEmeralds(Items.cooked_chicken, new PriceInfo(-8, -6)) } }, { { new EmeraldForItems(Items.leather, new PriceInfo(9, 12)), new ListItemForEmeralds(Items.leather_leggings, new PriceInfo(2, 4)) }, { new ListEnchantedItemForEmeralds(Items.leather_chestplate, new PriceInfo(7, 12)) }, { new ListItemForEmeralds(Items.saddle, new PriceInfo(8, 10)) } } } };
  private static final String __OBFID = "CL_00001707";
  
  public EntityVillager(World worldIn)
  {
    this(worldIn, 0);
  }
  
  public EntityVillager(World worldIn, int p_i1748_2_)
  {
    super(worldIn);
    field_175560_bz = new InventoryBasic("Items", false, 8);
    setProfession(p_i1748_2_);
    setSize(0.6F, 1.8F);
    ((PathNavigateGround)getNavigator()).func_179688_b(true);
    ((PathNavigateGround)getNavigator()).func_179690_a(true);
    tasks.addTask(0, new EntityAISwimming(this));
    tasks.addTask(1, new EntityAIAvoidEntity(this, new Predicate()
    {
      private static final String __OBFID = "CL_00002195";
      
      public boolean func_179530_a(Entity p_179530_1_) {
        return p_179530_1_ instanceof EntityZombie;
      }
      
      public boolean apply(Object p_apply_1_) {
        return func_179530_a((Entity)p_apply_1_);
      }
    }, 8.0F, 0.6D, 0.6D));
    tasks.addTask(1, new EntityAITradePlayer(this));
    tasks.addTask(1, new EntityAILookAtTradePlayer(this));
    tasks.addTask(2, new EntityAIMoveIndoors(this));
    tasks.addTask(3, new EntityAIRestrictOpenDoor(this));
    tasks.addTask(4, new EntityAIOpenDoor(this, true));
    tasks.addTask(5, new EntityAIMoveTowardsRestriction(this, 0.6D));
    tasks.addTask(6, new EntityAIVillagerMate(this));
    tasks.addTask(7, new EntityAIFollowGolem(this));
    tasks.addTask(9, new EntityAIWatchClosest2(this, EntityPlayer.class, 3.0F, 1.0F));
    tasks.addTask(9, new EntityAIVillagerInteract(this));
    tasks.addTask(9, new EntityAIWander(this, 0.6D));
    tasks.addTask(10, new EntityAIWatchClosest(this, EntityLiving.class, 8.0F));
    setCanPickUpLoot(true);
  }
  
  private void func_175552_ct()
  {
    if (!field_175564_by)
    {
      field_175564_by = true;
      
      if (isChild())
      {
        tasks.addTask(8, new EntityAIPlay(this, 0.32D));
      }
      else if (getProfession() == 0)
      {
        tasks.addTask(6, new EntityAIHarvestFarmland(this, 0.6D));
      }
    }
  }
  
  protected void func_175500_n()
  {
    if (getProfession() == 0)
    {
      tasks.addTask(8, new EntityAIHarvestFarmland(this, 0.6D));
    }
    
    super.func_175500_n();
  }
  
  protected void applyEntityAttributes()
  {
    super.applyEntityAttributes();
    getEntityAttribute(SharedMonsterAttributes.movementSpeed).setBaseValue(0.5D);
  }
  
  protected void updateAITasks()
  {
    if (--randomTickDivider <= 0)
    {
      BlockPos var1 = new BlockPos(this);
      worldObj.getVillageCollection().func_176060_a(var1);
      randomTickDivider = (70 + rand.nextInt(50));
      villageObj = worldObj.getVillageCollection().func_176056_a(var1, 32);
      
      if (villageObj == null)
      {
        detachHome();
      }
      else
      {
        BlockPos var2 = villageObj.func_180608_a();
        func_175449_a(var2, (int)(villageObj.getVillageRadius() * 1.0F));
        
        if (isLookingForHome)
        {
          isLookingForHome = false;
          villageObj.setDefaultPlayerReputation(5);
        }
      }
    }
    
    if ((!isTrading()) && (timeUntilReset > 0))
    {
      timeUntilReset -= 1;
      
      if (timeUntilReset <= 0)
      {
        if (needsInitilization)
        {
          Iterator var3 = buyingList.iterator();
          
          while (var3.hasNext())
          {
            MerchantRecipe var4 = (MerchantRecipe)var3.next();
            
            if (var4.isRecipeDisabled())
            {
              var4.func_82783_a(rand.nextInt(6) + rand.nextInt(6) + 2);
            }
          }
          
          func_175554_cu();
          needsInitilization = false;
          
          if ((villageObj != null) && (lastBuyingPlayer != null))
          {
            worldObj.setEntityState(this, (byte)14);
            villageObj.setReputationForPlayer(lastBuyingPlayer, 1);
          }
        }
        
        addPotionEffect(new PotionEffect(regenerationid, 200, 0));
      }
    }
    
    super.updateAITasks();
  }
  



  public boolean interact(EntityPlayer p_70085_1_)
  {
    ItemStack var2 = inventory.getCurrentItem();
    boolean var3 = (var2 != null) && (var2.getItem() == Items.spawn_egg);
    
    if ((!var3) && (isEntityAlive()) && (!isTrading()) && (!isChild()))
    {
      if ((!worldObj.isRemote) && ((buyingList == null) || (buyingList.size() > 0)))
      {
        setCustomer(p_70085_1_);
        p_70085_1_.displayVillagerTradeGui(this);
      }
      
      p_70085_1_.triggerAchievement(StatList.timesTalkedToVillagerStat);
      return true;
    }
    

    return super.interact(p_70085_1_);
  }
  

  protected void entityInit()
  {
    super.entityInit();
    dataWatcher.addObject(16, Integer.valueOf(0));
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("Profession", getProfession());
    tagCompound.setInteger("Riches", wealth);
    tagCompound.setInteger("Career", field_175563_bv);
    tagCompound.setInteger("CareerLevel", field_175562_bw);
    tagCompound.setBoolean("Willing", field_175565_bs);
    
    if (buyingList != null)
    {
      tagCompound.setTag("Offers", buyingList.getRecipiesAsTags());
    }
    
    NBTTagList var2 = new NBTTagList();
    
    for (int var3 = 0; var3 < field_175560_bz.getSizeInventory(); var3++)
    {
      ItemStack var4 = field_175560_bz.getStackInSlot(var3);
      
      if (var4 != null)
      {
        var2.appendTag(var4.writeToNBT(new NBTTagCompound()));
      }
    }
    
    tagCompound.setTag("Inventory", var2);
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    setProfession(tagCompund.getInteger("Profession"));
    wealth = tagCompund.getInteger("Riches");
    field_175563_bv = tagCompund.getInteger("Career");
    field_175562_bw = tagCompund.getInteger("CareerLevel");
    field_175565_bs = tagCompund.getBoolean("Willing");
    
    if (tagCompund.hasKey("Offers", 10))
    {
      NBTTagCompound var2 = tagCompund.getCompoundTag("Offers");
      buyingList = new MerchantRecipeList(var2);
    }
    
    NBTTagList var5 = tagCompund.getTagList("Inventory", 10);
    
    for (int var3 = 0; var3 < var5.tagCount(); var3++)
    {
      ItemStack var4 = ItemStack.loadItemStackFromNBT(var5.getCompoundTagAt(var3));
      
      if (var4 != null)
      {
        field_175560_bz.func_174894_a(var4);
      }
    }
    
    setCanPickUpLoot(true);
    func_175552_ct();
  }
  



  protected boolean canDespawn()
  {
    return false;
  }
  



  protected String getLivingSound()
  {
    return isTrading() ? "mob.villager.haggle" : "mob.villager.idle";
  }
  



  protected String getHurtSound()
  {
    return "mob.villager.hit";
  }
  



  protected String getDeathSound()
  {
    return "mob.villager.death";
  }
  
  public void setProfession(int p_70938_1_)
  {
    dataWatcher.updateObject(16, Integer.valueOf(p_70938_1_));
  }
  
  public int getProfession()
  {
    return Math.max(dataWatcher.getWatchableObjectInt(16) % 5, 0);
  }
  
  public boolean isMating()
  {
    return isMating;
  }
  
  public void setMating(boolean p_70947_1_)
  {
    isMating = p_70947_1_;
  }
  
  public void setPlaying(boolean p_70939_1_)
  {
    isPlaying = p_70939_1_;
  }
  
  public boolean isPlaying()
  {
    return isPlaying;
  }
  
  public void setRevengeTarget(EntityLivingBase p_70604_1_)
  {
    super.setRevengeTarget(p_70604_1_);
    
    if ((villageObj != null) && (p_70604_1_ != null))
    {
      villageObj.addOrRenewAgressor(p_70604_1_);
      
      if ((p_70604_1_ instanceof EntityPlayer))
      {
        byte var2 = -1;
        
        if (isChild())
        {
          var2 = -3;
        }
        
        villageObj.setReputationForPlayer(p_70604_1_.getName(), var2);
        
        if (isEntityAlive())
        {
          worldObj.setEntityState(this, (byte)13);
        }
      }
    }
  }
  



  public void onDeath(DamageSource cause)
  {
    if (villageObj != null)
    {
      Entity var2 = cause.getEntity();
      
      if (var2 != null)
      {
        if ((var2 instanceof EntityPlayer))
        {
          villageObj.setReputationForPlayer(var2.getName(), -2);
        }
        else if ((var2 instanceof IMob))
        {
          villageObj.endMatingSeason();
        }
      }
      else
      {
        EntityPlayer var3 = worldObj.getClosestPlayerToEntity(this, 16.0D);
        
        if (var3 != null)
        {
          villageObj.endMatingSeason();
        }
      }
    }
    
    super.onDeath(cause);
  }
  
  public void setCustomer(EntityPlayer p_70932_1_)
  {
    buyingPlayer = p_70932_1_;
  }
  
  public EntityPlayer getCustomer()
  {
    return buyingPlayer;
  }
  
  public boolean isTrading()
  {
    return buyingPlayer != null;
  }
  
  public boolean func_175550_n(boolean p_175550_1_)
  {
    if ((!field_175565_bs) && (p_175550_1_) && (func_175553_cp()))
    {
      boolean var2 = false;
      
      for (int var3 = 0; var3 < field_175560_bz.getSizeInventory(); var3++)
      {
        ItemStack var4 = field_175560_bz.getStackInSlot(var3);
        
        if (var4 != null)
        {
          if ((var4.getItem() == Items.bread) && (stackSize >= 3))
          {
            var2 = true;
            field_175560_bz.decrStackSize(var3, 3);
          }
          else if (((var4.getItem() == Items.potato) || (var4.getItem() == Items.carrot)) && (stackSize >= 12))
          {
            var2 = true;
            field_175560_bz.decrStackSize(var3, 12);
          }
        }
        
        if (var2)
        {
          worldObj.setEntityState(this, (byte)18);
          field_175565_bs = true;
          break;
        }
      }
    }
    
    return field_175565_bs;
  }
  
  public void func_175549_o(boolean p_175549_1_)
  {
    field_175565_bs = p_175549_1_;
  }
  
  public void useRecipe(MerchantRecipe p_70933_1_)
  {
    p_70933_1_.incrementToolUses();
    livingSoundTime = (-getTalkInterval());
    playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
    int var2 = 3 + rand.nextInt(4);
    
    if ((p_70933_1_.func_180321_e() == 1) || (rand.nextInt(5) == 0))
    {
      timeUntilReset = 40;
      needsInitilization = true;
      field_175565_bs = true;
      
      if (buyingPlayer != null)
      {
        lastBuyingPlayer = buyingPlayer.getName();
      }
      else
      {
        lastBuyingPlayer = null;
      }
      
      var2 += 5;
    }
    
    if (p_70933_1_.getItemToBuy().getItem() == Items.emerald)
    {
      wealth += getItemToBuystackSize;
    }
    
    if (p_70933_1_.func_180322_j())
    {
      worldObj.spawnEntityInWorld(new EntityXPOrb(worldObj, posX, posY + 0.5D, posZ, var2));
    }
  }
  




  public void verifySellingItem(ItemStack p_110297_1_)
  {
    if ((!worldObj.isRemote) && (livingSoundTime > -getTalkInterval() + 20))
    {
      livingSoundTime = (-getTalkInterval());
      
      if (p_110297_1_ != null)
      {
        playSound("mob.villager.yes", getSoundVolume(), getSoundPitch());
      }
      else
      {
        playSound("mob.villager.no", getSoundVolume(), getSoundPitch());
      }
    }
  }
  
  public MerchantRecipeList getRecipes(EntityPlayer p_70934_1_)
  {
    if (buyingList == null)
    {
      func_175554_cu();
    }
    
    return buyingList;
  }
  
  private void func_175554_cu()
  {
    ITradeList[][][] var1 = field_175561_bA[getProfession()];
    
    if ((field_175563_bv != 0) && (field_175562_bw != 0))
    {
      field_175562_bw += 1;
    }
    else
    {
      field_175563_bv = (rand.nextInt(var1.length) + 1);
      field_175562_bw = 1;
    }
    
    if (buyingList == null)
    {
      buyingList = new MerchantRecipeList();
    }
    
    int var2 = field_175563_bv - 1;
    int var3 = field_175562_bw - 1;
    ITradeList[][] var4 = var1[var2];
    
    if (var3 < var4.length)
    {
      ITradeList[] var5 = var4[var3];
      ITradeList[] var6 = var5;
      int var7 = var5.length;
      
      for (int var8 = 0; var8 < var7; var8++)
      {
        ITradeList var9 = var6[var8];
        var9.func_179401_a(buyingList, rand);
      }
    }
  }
  
  public void setRecipes(MerchantRecipeList p_70930_1_) {}
  
  public IChatComponent getDisplayName()
  {
    String var1 = getCustomNameTag();
    
    if ((var1 != null) && (var1.length() > 0))
    {
      return new ChatComponentText(var1);
    }
    

    if (buyingList == null)
    {
      func_175554_cu();
    }
    
    String var2 = null;
    
    switch (getProfession())
    {
    case 0: 
      if (field_175563_bv == 1)
      {
        var2 = "farmer";
      }
      else if (field_175563_bv == 2)
      {
        var2 = "fisherman";
      }
      else if (field_175563_bv == 3)
      {
        var2 = "shepherd";
      }
      else if (field_175563_bv == 4)
      {
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
      if (field_175563_bv == 1)
      {
        var2 = "armor";
      }
      else if (field_175563_bv == 2)
      {
        var2 = "weapon";
      }
      else if (field_175563_bv == 3)
      {
        var2 = "tool";
      }
      
      break;
    
    case 4: 
      if (field_175563_bv == 1)
      {
        var2 = "butcher";
      }
      else if (field_175563_bv == 2)
      {
        var2 = "leather";
      }
      break;
    }
    if (var2 != null)
    {
      ChatComponentTranslation var3 = new ChatComponentTranslation("entity.Villager." + var2, new Object[0]);
      var3.getChatStyle().setChatHoverEvent(func_174823_aP());
      var3.getChatStyle().setInsertion(getUniqueID().toString());
      return var3;
    }
    

    return super.getDisplayName();
  }
  


  public float getEyeHeight()
  {
    float var1 = 1.62F;
    
    if (isChild())
    {
      var1 = (float)(var1 - 0.81D);
    }
    
    return var1;
  }
  
  public void handleHealthUpdate(byte p_70103_1_)
  {
    if (p_70103_1_ == 12)
    {
      func_180489_a(EnumParticleTypes.HEART);
    }
    else if (p_70103_1_ == 13)
    {
      func_180489_a(EnumParticleTypes.VILLAGER_ANGRY);
    }
    else if (p_70103_1_ == 14)
    {
      func_180489_a(EnumParticleTypes.VILLAGER_HAPPY);
    }
    else
    {
      super.handleHealthUpdate(p_70103_1_);
    }
  }
  
  private void func_180489_a(EnumParticleTypes p_180489_1_)
  {
    for (int var2 = 0; var2 < 5; var2++)
    {
      double var3 = rand.nextGaussian() * 0.02D;
      double var5 = rand.nextGaussian() * 0.02D;
      double var7 = rand.nextGaussian() * 0.02D;
      worldObj.spawnParticle(p_180489_1_, posX + rand.nextFloat() * width * 2.0F - width, posY + 1.0D + rand.nextFloat() * height, posZ + rand.nextFloat() * width * 2.0F - width, var3, var5, var7, new int[0]);
    }
  }
  
  public IEntityLivingData func_180482_a(DifficultyInstance p_180482_1_, IEntityLivingData p_180482_2_)
  {
    p_180482_2_ = super.func_180482_a(p_180482_1_, p_180482_2_);
    setProfession(worldObj.rand.nextInt(5));
    func_175552_ct();
    return p_180482_2_;
  }
  
  public void setLookingForHome()
  {
    isLookingForHome = true;
  }
  
  public EntityVillager func_180488_b(EntityAgeable p_180488_1_)
  {
    EntityVillager var2 = new EntityVillager(worldObj);
    var2.func_180482_a(worldObj.getDifficultyForLocation(new BlockPos(var2)), null);
    return var2;
  }
  
  public boolean allowLeashing()
  {
    return false;
  }
  



  public void onStruckByLightning(EntityLightningBolt lightningBolt)
  {
    if (!worldObj.isRemote)
    {
      EntityWitch var2 = new EntityWitch(worldObj);
      var2.setLocationAndAngles(posX, posY, posZ, rotationYaw, rotationPitch);
      var2.func_180482_a(worldObj.getDifficultyForLocation(new BlockPos(var2)), null);
      worldObj.spawnEntityInWorld(var2);
      setDead();
    }
  }
  
  public InventoryBasic func_175551_co()
  {
    return field_175560_bz;
  }
  
  protected void func_175445_a(EntityItem p_175445_1_)
  {
    ItemStack var2 = p_175445_1_.getEntityItem();
    Item var3 = var2.getItem();
    
    if (func_175558_a(var3))
    {
      ItemStack var4 = field_175560_bz.func_174894_a(var2);
      
      if (var4 == null)
      {
        p_175445_1_.setDead();
      }
      else
      {
        stackSize = stackSize;
      }
    }
  }
  
  private boolean func_175558_a(Item p_175558_1_)
  {
    return (p_175558_1_ == Items.bread) || (p_175558_1_ == Items.potato) || (p_175558_1_ == Items.carrot) || (p_175558_1_ == Items.wheat) || (p_175558_1_ == Items.wheat_seeds);
  }
  
  public boolean func_175553_cp()
  {
    return func_175559_s(1);
  }
  
  public boolean func_175555_cq()
  {
    return func_175559_s(2);
  }
  
  public boolean func_175557_cr()
  {
    boolean var1 = getProfession() == 0;
    return !func_175559_s(5);
  }
  
  private boolean func_175559_s(int p_175559_1_)
  {
    boolean var2 = getProfession() == 0;
    
    for (int var3 = 0; var3 < field_175560_bz.getSizeInventory(); var3++)
    {
      ItemStack var4 = field_175560_bz.getStackInSlot(var3);
      
      if (var4 != null)
      {
        if (((var4.getItem() == Items.bread) && (stackSize >= 3 * p_175559_1_)) || ((var4.getItem() == Items.potato) && (stackSize >= 12 * p_175559_1_)) || ((var4.getItem() == Items.carrot) && (stackSize >= 12 * p_175559_1_)))
        {
          return true;
        }
        
        if ((var2) && (var4.getItem() == Items.wheat) && (stackSize >= 9 * p_175559_1_))
        {
          return true;
        }
      }
    }
    
    return false;
  }
  
  public boolean func_175556_cs()
  {
    for (int var1 = 0; var1 < field_175560_bz.getSizeInventory(); var1++)
    {
      ItemStack var2 = field_175560_bz.getStackInSlot(var1);
      
      if ((var2 != null) && ((var2.getItem() == Items.wheat_seeds) || (var2.getItem() == Items.potato) || (var2.getItem() == Items.carrot)))
      {
        return true;
      }
    }
    
    return false;
  }
  
  public boolean func_174820_d(int p_174820_1_, ItemStack p_174820_2_)
  {
    if (super.func_174820_d(p_174820_1_, p_174820_2_))
    {
      return true;
    }
    

    int var3 = p_174820_1_ - 300;
    
    if ((var3 >= 0) && (var3 < field_175560_bz.getSizeInventory()))
    {
      field_175560_bz.setInventorySlotContents(var3, p_174820_2_);
      return true;
    }
    

    return false;
  }
  


  public EntityAgeable createChild(EntityAgeable p_90011_1_)
  {
    return func_180488_b(p_90011_1_);
  }
  
  static class EmeraldForItems implements EntityVillager.ITradeList
  {
    public Item field_179405_a;
    public EntityVillager.PriceInfo field_179404_b;
    private static final String __OBFID = "CL_00002194";
    
    public EmeraldForItems(Item p_i45815_1_, EntityVillager.PriceInfo p_i45815_2_)
    {
      field_179405_a = p_i45815_1_;
      field_179404_b = p_i45815_2_;
    }
    
    public void func_179401_a(MerchantRecipeList p_179401_1_, Random p_179401_2_)
    {
      int var3 = 1;
      
      if (field_179404_b != null)
      {
        var3 = field_179404_b.func_179412_a(p_179401_2_);
      }
      
      p_179401_1_.add(new MerchantRecipe(new ItemStack(field_179405_a, var3, 0), Items.emerald));
    }
  }
  
  static abstract interface ITradeList
  {
    public abstract void func_179401_a(MerchantRecipeList paramMerchantRecipeList, Random paramRandom);
  }
  
  static class ItemAndEmeraldToItem implements EntityVillager.ITradeList
  {
    public ItemStack field_179411_a;
    public EntityVillager.PriceInfo field_179409_b;
    public ItemStack field_179410_c;
    public EntityVillager.PriceInfo field_179408_d;
    private static final String __OBFID = "CL_00002191";
    
    public ItemAndEmeraldToItem(Item p_i45813_1_, EntityVillager.PriceInfo p_i45813_2_, Item p_i45813_3_, EntityVillager.PriceInfo p_i45813_4_)
    {
      field_179411_a = new ItemStack(p_i45813_1_);
      field_179409_b = p_i45813_2_;
      field_179410_c = new ItemStack(p_i45813_3_);
      field_179408_d = p_i45813_4_;
    }
    
    public void func_179401_a(MerchantRecipeList p_179401_1_, Random p_179401_2_)
    {
      int var3 = 1;
      
      if (field_179409_b != null)
      {
        var3 = field_179409_b.func_179412_a(p_179401_2_);
      }
      
      int var4 = 1;
      
      if (field_179408_d != null)
      {
        var4 = field_179408_d.func_179412_a(p_179401_2_);
      }
      
      p_179401_1_.add(new MerchantRecipe(new ItemStack(field_179411_a.getItem(), var3, field_179411_a.getMetadata()), new ItemStack(Items.emerald), new ItemStack(field_179410_c.getItem(), var4, field_179410_c.getMetadata())));
    }
  }
  
  static class ListEnchantedBookForEmeralds implements EntityVillager.ITradeList {
    private static final String __OBFID = "CL_00002193";
    
    ListEnchantedBookForEmeralds() {}
    
    public void func_179401_a(MerchantRecipeList p_179401_1_, Random p_179401_2_) {
      Enchantment var3 = Enchantment.enchantmentsList[p_179401_2_.nextInt(Enchantment.enchantmentsList.length)];
      int var4 = MathHelper.getRandomIntegerInRange(p_179401_2_, var3.getMinLevel(), var3.getMaxLevel());
      ItemStack var5 = Items.enchanted_book.getEnchantedItemStack(new EnchantmentData(var3, var4));
      int var6 = 2 + p_179401_2_.nextInt(5 + var4 * 10) + 3 * var4;
      
      if (var6 > 64)
      {
        var6 = 64;
      }
      
      p_179401_1_.add(new MerchantRecipe(new ItemStack(Items.book), new ItemStack(Items.emerald, var6), var5));
    }
  }
  
  static class ListEnchantedItemForEmeralds implements EntityVillager.ITradeList
  {
    public ItemStack field_179407_a;
    public EntityVillager.PriceInfo field_179406_b;
    private static final String __OBFID = "CL_00002192";
    
    public ListEnchantedItemForEmeralds(Item p_i45814_1_, EntityVillager.PriceInfo p_i45814_2_)
    {
      field_179407_a = new ItemStack(p_i45814_1_);
      field_179406_b = p_i45814_2_;
    }
    
    public void func_179401_a(MerchantRecipeList p_179401_1_, Random p_179401_2_)
    {
      int var3 = 1;
      
      if (field_179406_b != null)
      {
        var3 = field_179406_b.func_179412_a(p_179401_2_);
      }
      
      ItemStack var4 = new ItemStack(Items.emerald, var3, 0);
      ItemStack var5 = new ItemStack(field_179407_a.getItem(), 1, field_179407_a.getMetadata());
      var5 = EnchantmentHelper.addRandomEnchantment(p_179401_2_, var5, 5 + p_179401_2_.nextInt(15));
      p_179401_1_.add(new MerchantRecipe(var4, var5));
    }
  }
  
  static class ListItemForEmeralds implements EntityVillager.ITradeList
  {
    public ItemStack field_179403_a;
    public EntityVillager.PriceInfo field_179402_b;
    private static final String __OBFID = "CL_00002190";
    
    public ListItemForEmeralds(Item p_i45811_1_, EntityVillager.PriceInfo p_i45811_2_)
    {
      field_179403_a = new ItemStack(p_i45811_1_);
      field_179402_b = p_i45811_2_;
    }
    
    public ListItemForEmeralds(ItemStack p_i45812_1_, EntityVillager.PriceInfo p_i45812_2_)
    {
      field_179403_a = p_i45812_1_;
      field_179402_b = p_i45812_2_;
    }
    
    public void func_179401_a(MerchantRecipeList p_179401_1_, Random p_179401_2_)
    {
      int var3 = 1;
      
      if (field_179402_b != null)
      {
        var3 = field_179402_b.func_179412_a(p_179401_2_);
      }
      
      ItemStack var5;
      ItemStack var4;
      ItemStack var5;
      if (var3 < 0)
      {
        ItemStack var4 = new ItemStack(Items.emerald, 1, 0);
        var5 = new ItemStack(field_179403_a.getItem(), -var3, field_179403_a.getMetadata());
      }
      else
      {
        var4 = new ItemStack(Items.emerald, var3, 0);
        var5 = new ItemStack(field_179403_a.getItem(), 1, field_179403_a.getMetadata());
      }
      
      p_179401_1_.add(new MerchantRecipe(var4, var5));
    }
  }
  
  static class PriceInfo extends Tuple
  {
    private static final String __OBFID = "CL_00002189";
    
    public PriceInfo(int p_i45810_1_, int p_i45810_2_)
    {
      super(Integer.valueOf(p_i45810_2_));
    }
    
    public int func_179412_a(Random p_179412_1_)
    {
      return ((Integer)getFirst()).intValue() >= ((Integer)getSecond()).intValue() ? ((Integer)getFirst()).intValue() : ((Integer)getFirst()).intValue() + p_179412_1_.nextInt(((Integer)getSecond()).intValue() - ((Integer)getFirst()).intValue() + 1);
    }
  }
}
