package net.minecraft.entity.player;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.DataWatcher;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EntityTracker;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S42PacketCombatEvent.Event;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.scoreboard.Team.EnumVisible;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.ServerConfigurationManager;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.CombatTracker;
import net.minecraft.util.DamageSource;
import net.minecraft.util.FoodStats;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonSerializableSet;
import net.minecraft.util.MathHelper;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.GameRules;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings.GameType;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPlayerMP extends EntityPlayer implements ICrafting
{
  private static final Logger logger = ;
  private String translator = "en_US";
  


  public NetHandlerPlayServer playerNetServerHandler;
  


  public final MinecraftServer mcServer;
  

  public final ItemInWorldManager theItemInWorldManager;
  

  public double managedPosX;
  

  public double managedPosZ;
  

  public final List loadedChunks = Lists.newLinkedList();
  

  private final List destroyedItemsNetCache = Lists.newLinkedList();
  private final StatisticsFile statsFile;
  private float field_130068_bO = Float.MIN_VALUE;
  

  private float lastHealth = -1.0E8F;
  

  private int lastFoodLevel = -99999999;
  

  private boolean wasHungry = true;
  

  private int lastExperience = -99999999;
  private int respawnInvulnerabilityTicks = 60;
  private EntityPlayer.EnumChatVisibility chatVisibility;
  private boolean chatColours = true;
  private long playerLastActiveTime = System.currentTimeMillis();
  private Entity field_175401_bS = null;
  


  private int currentWindowId;
  


  public boolean isChangingQuantityOnly;
  

  public int ping;
  

  public boolean playerConqueredTheEnd;
  

  private static final String __OBFID = "CL_00001440";
  


  public EntityPlayerMP(MinecraftServer server, WorldServer worldIn, GameProfile profile, ItemInWorldManager interactionManager)
  {
    super(worldIn, profile);
    thisPlayerMP = this;
    theItemInWorldManager = interactionManager;
    BlockPos var5 = worldIn.getSpawnPoint();
    
    if ((!provider.getHasNoSky()) && (worldIn.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE))
    {
      int var6 = Math.max(5, server.getSpawnProtectionSize() - 6);
      int var7 = MathHelper.floor_double(worldIn.getWorldBorder().getClosestDistance(var5.getX(), var5.getZ()));
      
      if (var7 < var6)
      {
        var6 = var7;
      }
      
      if (var7 <= 1)
      {
        var6 = 1;
      }
      
      var5 = worldIn.func_175672_r(var5.add(rand.nextInt(var6 * 2) - var6, 0, rand.nextInt(var6 * 2) - var6));
    }
    
    mcServer = server;
    statsFile = server.getConfigurationManager().getPlayerStatsFile(this);
    stepHeight = 0.0F;
    func_174828_a(var5, 0.0F, 0.0F);
    
    while ((!worldIn.getCollidingBoundingBoxes(this, getEntityBoundingBox()).isEmpty()) && (posY < 255.0D))
    {
      setPosition(posX, posY + 1.0D, posZ);
    }
  }
  



  public void readEntityFromNBT(NBTTagCompound tagCompund)
  {
    super.readEntityFromNBT(tagCompund);
    
    if (tagCompund.hasKey("playerGameType", 99))
    {
      if (MinecraftServer.getServer().getForceGamemode())
      {
        theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
      }
      else
      {
        theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(tagCompund.getInteger("playerGameType")));
      }
    }
  }
  



  public void writeEntityToNBT(NBTTagCompound tagCompound)
  {
    super.writeEntityToNBT(tagCompound);
    tagCompound.setInteger("playerGameType", theItemInWorldManager.getGameType().getID());
  }
  



  public void addExperienceLevel(int p_82242_1_)
  {
    super.addExperienceLevel(p_82242_1_);
    lastExperience = -1;
  }
  
  public void func_71013_b(int p_71013_1_)
  {
    super.func_71013_b(p_71013_1_);
    lastExperience = -1;
  }
  
  public void addSelfToInternalCraftingInventory()
  {
    openContainer.onCraftGuiOpened(this);
  }
  
  public void func_152111_bt()
  {
    super.func_152111_bt();
    playerNetServerHandler.sendPacket(new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
  }
  
  public void func_152112_bu()
  {
    super.func_152112_bu();
    playerNetServerHandler.sendPacket(new S42PacketCombatEvent(getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
  }
  



  public void onUpdate()
  {
    theItemInWorldManager.updateBlockRemoving();
    respawnInvulnerabilityTicks -= 1;
    
    if (hurtResistantTime > 0)
    {
      hurtResistantTime -= 1;
    }
    
    openContainer.detectAndSendChanges();
    
    if ((!worldObj.isRemote) && (!openContainer.canInteractWith(this)))
    {
      closeScreen();
      openContainer = inventoryContainer;
    }
    
    while (!destroyedItemsNetCache.isEmpty())
    {
      int var1 = Math.min(destroyedItemsNetCache.size(), Integer.MAX_VALUE);
      int[] var2 = new int[var1];
      Iterator var3 = destroyedItemsNetCache.iterator();
      int var4 = 0;
      
      while ((var3.hasNext()) && (var4 < var1))
      {
        var2[(var4++)] = ((Integer)var3.next()).intValue();
        var3.remove();
      }
      
      playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(var2));
    }
    
    if (!loadedChunks.isEmpty())
    {
      ArrayList var6 = Lists.newArrayList();
      Iterator var8 = loadedChunks.iterator();
      ArrayList var9 = Lists.newArrayList();
      

      while ((var8.hasNext()) && (var6.size() < 10))
      {
        ChunkCoordIntPair var10 = (ChunkCoordIntPair)var8.next();
        
        if (var10 != null)
        {
          if (worldObj.isBlockLoaded(new BlockPos(chunkXPos << 4, 0, chunkZPos << 4)))
          {
            Chunk var5 = worldObj.getChunkFromChunkCoords(chunkXPos, chunkZPos);
            
            if (var5.isPopulated())
            {
              var6.add(var5);
              var9.addAll(((WorldServer)worldObj).func_147486_a(chunkXPos * 16, 0, chunkZPos * 16, chunkXPos * 16 + 16, 256, chunkZPos * 16 + 16));
              var8.remove();
            }
            
          }
        }
        else {
          var8.remove();
        }
      }
      
      if (!var6.isEmpty())
      {
        if (var6.size() == 1)
        {
          playerNetServerHandler.sendPacket(new S21PacketChunkData((Chunk)var6.get(0), true, 65535));
        }
        else
        {
          playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(var6));
        }
        
        Iterator var11 = var9.iterator();
        
        while (var11.hasNext())
        {
          TileEntity var12 = (TileEntity)var11.next();
          sendTileEntityUpdate(var12);
        }
        
        var11 = var6.iterator();
        
        while (var11.hasNext())
        {
          Chunk var5 = (Chunk)var11.next();
          getServerForPlayer().getEntityTracker().func_85172_a(this, var5);
        }
      }
    }
    
    Entity var7 = func_175398_C();
    
    if (var7 != this)
    {
      if (!var7.isEntityAlive())
      {
        func_175399_e(this);
      }
      else
      {
        setPositionAndRotation(posX, posY, posZ, rotationYaw, rotationPitch);
        mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
        
        if (isSneaking())
        {
          func_175399_e(this);
        }
      }
    }
  }
  
  public void onUpdateEntity()
  {
    try
    {
      super.onUpdate();
      
      for (int var1 = 0; var1 < inventory.getSizeInventory(); var1++)
      {
        ItemStack var6 = inventory.getStackInSlot(var1);
        
        if ((var6 != null) && (var6.getItem().isMap()))
        {
          Packet var8 = ((ItemMapBase)var6.getItem()).createMapDataPacket(var6, worldObj, this);
          
          if (var8 != null)
          {
            playerNetServerHandler.sendPacket(var8);
          }
        }
      }
      
      if ((getHealth() == lastHealth) && (lastFoodLevel == foodStats.getFoodLevel())) { if ((foodStats.getSaturationLevel() == 0.0F) == wasHungry) {}
      } else {
        playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S06PacketUpdateHealth(getHealth(), foodStats.getFoodLevel(), foodStats.getSaturationLevel()));
        lastHealth = getHealth();
        lastFoodLevel = foodStats.getFoodLevel();
        wasHungry = (foodStats.getSaturationLevel() == 0.0F);
      }
      
      if (getHealth() + getAbsorptionAmount() != field_130068_bO)
      {
        field_130068_bO = (getHealth() + getAbsorptionAmount());
        Collection var5 = getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.health);
        Iterator var7 = var5.iterator();
        
        while (var7.hasNext())
        {
          ScoreObjective var9 = (ScoreObjective)var7.next();
          getWorldScoreboard().getValueFromObjective(getName(), var9).func_96651_a(Arrays.asList(new EntityPlayer[] { this }));
        }
      }
      
      if (experienceTotal != lastExperience)
      {
        lastExperience = experienceTotal;
        playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S1FPacketSetExperience(experience, experienceTotal, experienceLevel));
      }
      
      if ((ticksExisted % 20 * 5 == 0) && (!getStatFile().hasAchievementUnlocked(AchievementList.exploreAllBiomes)))
      {
        func_147098_j();
      }
    }
    catch (Throwable var4)
    {
      CrashReport var2 = CrashReport.makeCrashReport(var4, "Ticking player");
      CrashReportCategory var3 = var2.makeCategory("Player being ticked");
      addEntityCrashInfo(var3);
      throw new net.minecraft.util.ReportedException(var2);
    }
  }
  
  protected void func_147098_j()
  {
    BiomeGenBase var1 = worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(posX), 0, MathHelper.floor_double(posZ)));
    String var2 = biomeName;
    JsonSerializableSet var3 = (JsonSerializableSet)getStatFile().func_150870_b(AchievementList.exploreAllBiomes);
    
    if (var3 == null)
    {
      var3 = (JsonSerializableSet)getStatFile().func_150872_a(AchievementList.exploreAllBiomes, new JsonSerializableSet());
    }
    
    var3.add(var2);
    
    if ((getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes)) && (var3.size() >= BiomeGenBase.explorationBiomesList.size()))
    {
      HashSet var4 = Sets.newHashSet(BiomeGenBase.explorationBiomesList);
      Iterator var5 = var3.iterator();
      
      while (var5.hasNext())
      {
        String var6 = (String)var5.next();
        Iterator var7 = var4.iterator();
        
        while (var7.hasNext())
        {
          BiomeGenBase var8 = (BiomeGenBase)var7.next();
          
          if (biomeName.equals(var6))
          {
            var7.remove();
          }
        }
        
        if (var4.isEmpty()) {
          break;
        }
      }
      

      if (var4.isEmpty())
      {
        triggerAchievement(AchievementList.exploreAllBiomes);
      }
    }
  }
  



  public void onDeath(DamageSource cause)
  {
    if (worldObj.getGameRules().getGameRuleBooleanValue("showDeathMessages"))
    {
      Team var2 = getTeam();
      
      if ((var2 != null) && (var2.func_178771_j() != Team.EnumVisible.ALWAYS))
      {
        if (var2.func_178771_j() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS)
        {
          mcServer.getConfigurationManager().func_177453_a(this, getCombatTracker().func_151521_b());
        }
        else if (var2.func_178771_j() == Team.EnumVisible.HIDE_FOR_OWN_TEAM)
        {
          mcServer.getConfigurationManager().func_177452_b(this, getCombatTracker().func_151521_b());
        }
        
      }
      else {
        mcServer.getConfigurationManager().sendChatMsg(getCombatTracker().func_151521_b());
      }
    }
    
    if (!worldObj.getGameRules().getGameRuleBooleanValue("keepInventory"))
    {
      inventory.dropAllItems();
    }
    
    Collection var6 = worldObj.getScoreboard().func_96520_a(IScoreObjectiveCriteria.deathCount);
    Iterator var3 = var6.iterator();
    
    while (var3.hasNext())
    {
      ScoreObjective var4 = (ScoreObjective)var3.next();
      Score var5 = getWorldScoreboard().getValueFromObjective(getName(), var4);
      var5.func_96648_a();
    }
    
    EntityLivingBase var7 = func_94060_bK();
    
    if (var7 != null)
    {
      EntityList.EntityEggInfo var8 = (EntityList.EntityEggInfo)EntityList.entityEggs.get(Integer.valueOf(EntityList.getEntityID(var7)));
      
      if (var8 != null)
      {
        triggerAchievement(field_151513_e);
      }
      
      var7.addToPlayerScore(this, scoreValue);
    }
    
    triggerAchievement(StatList.deathsStat);
    func_175145_a(StatList.timeSinceDeathStat);
    getCombatTracker().func_94549_h();
  }
  



  public boolean attackEntityFrom(DamageSource source, float amount)
  {
    if (func_180431_b(source))
    {
      return false;
    }
    

    boolean var3 = (mcServer.isDedicatedServer()) && (func_175400_cq()) && ("fall".equals(damageType));
    
    if ((!var3) && (respawnInvulnerabilityTicks > 0) && (source != DamageSource.outOfWorld))
    {
      return false;
    }
    

    if ((source instanceof net.minecraft.util.EntityDamageSource))
    {
      Entity var4 = source.getEntity();
      
      if (((var4 instanceof EntityPlayer)) && (!canAttackPlayer((EntityPlayer)var4)))
      {
        return false;
      }
      
      if ((var4 instanceof EntityArrow))
      {
        EntityArrow var5 = (EntityArrow)var4;
        
        if (((shootingEntity instanceof EntityPlayer)) && (!canAttackPlayer((EntityPlayer)shootingEntity)))
        {
          return false;
        }
      }
    }
    
    return super.attackEntityFrom(source, amount);
  }
  


  public boolean canAttackPlayer(EntityPlayer other)
  {
    return !func_175400_cq() ? false : super.canAttackPlayer(other);
  }
  
  private boolean func_175400_cq()
  {
    return mcServer.isPVPEnabled();
  }
  



  public void travelToDimension(int dimensionId)
  {
    if ((dimension == 1) && (dimensionId == 1))
    {
      triggerAchievement(AchievementList.theEnd2);
      worldObj.removeEntity(this);
      playerConqueredTheEnd = true;
      playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0F));
    }
    else
    {
      if ((dimension == 0) && (dimensionId == 1))
      {
        triggerAchievement(AchievementList.theEnd);
        BlockPos var2 = mcServer.worldServerForDimension(dimensionId).func_180504_m();
        
        if (var2 != null)
        {
          playerNetServerHandler.setPlayerLocation(var2.getX(), var2.getY(), var2.getZ(), 0.0F, 0.0F);
        }
        
        dimensionId = 1;
      }
      else
      {
        triggerAchievement(AchievementList.portal);
      }
      
      mcServer.getConfigurationManager().transferPlayerToDimension(this, dimensionId);
      lastExperience = -1;
      lastHealth = -1.0F;
      lastFoodLevel = -1;
    }
  }
  
  public boolean func_174827_a(EntityPlayerMP p_174827_1_)
  {
    return func_175149_v() ? false : p_174827_1_.func_175149_v() ? false : func_175398_C() == this ? true : super.func_174827_a(p_174827_1_);
  }
  
  private void sendTileEntityUpdate(TileEntity p_147097_1_)
  {
    if (p_147097_1_ != null)
    {
      Packet var2 = p_147097_1_.getDescriptionPacket();
      
      if (var2 != null)
      {
        playerNetServerHandler.sendPacket(var2);
      }
    }
  }
  



  public void onItemPickup(Entity p_71001_1_, int p_71001_2_)
  {
    super.onItemPickup(p_71001_1_, p_71001_2_);
    openContainer.detectAndSendChanges();
  }
  
  public EntityPlayer.EnumStatus func_180469_a(BlockPos p_180469_1_)
  {
    EntityPlayer.EnumStatus var2 = super.func_180469_a(p_180469_1_);
    
    if (var2 == EntityPlayer.EnumStatus.OK)
    {
      S0APacketUseBed var3 = new S0APacketUseBed(this, p_180469_1_);
      getServerForPlayer().getEntityTracker().sendToAllTrackingEntity(this, var3);
      playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
      playerNetServerHandler.sendPacket(var3);
    }
    
    return var2;
  }
  



  public void wakeUpPlayer(boolean p_70999_1_, boolean updateWorldFlag, boolean setSpawn)
  {
    if (isPlayerSleeping())
    {
      getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 2));
    }
    
    super.wakeUpPlayer(p_70999_1_, updateWorldFlag, setSpawn);
    
    if (playerNetServerHandler != null)
    {
      playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
    }
  }
  



  public void mountEntity(Entity entityIn)
  {
    Entity var2 = ridingEntity;
    super.mountEntity(entityIn);
    
    if (entityIn != var2)
    {
      playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this, ridingEntity));
      playerNetServerHandler.setPlayerLocation(posX, posY, posZ, rotationYaw, rotationPitch);
    }
  }
  


  protected void func_180433_a(double p_180433_1_, boolean p_180433_3_, Block p_180433_4_, BlockPos p_180433_5_) {}
  

  public void handleFalling(double p_71122_1_, boolean p_71122_3_)
  {
    int var4 = MathHelper.floor_double(posX);
    int var5 = MathHelper.floor_double(posY - 0.20000000298023224D);
    int var6 = MathHelper.floor_double(posZ);
    BlockPos var7 = new BlockPos(var4, var5, var6);
    Block var8 = worldObj.getBlockState(var7).getBlock();
    
    if (var8.getMaterial() == net.minecraft.block.material.Material.air)
    {
      Block var9 = worldObj.getBlockState(var7.offsetDown()).getBlock();
      
      if (((var9 instanceof net.minecraft.block.BlockFence)) || ((var9 instanceof net.minecraft.block.BlockWall)) || ((var9 instanceof net.minecraft.block.BlockFenceGate)))
      {
        var7 = var7.offsetDown();
        var8 = worldObj.getBlockState(var7).getBlock();
      }
    }
    
    super.func_180433_a(p_71122_1_, p_71122_3_, var8, var7);
  }
  
  public void func_175141_a(TileEntitySign p_175141_1_)
  {
    p_175141_1_.func_145912_a(this);
    playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(p_175141_1_.getPos()));
  }
  



  private void getNextWindowId()
  {
    currentWindowId = (currentWindowId % 100 + 1);
  }
  
  public void displayGui(IInteractionObject guiOwner)
  {
    getNextWindowId();
    playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(currentWindowId, guiOwner.getGuiID(), guiOwner.getDisplayName()));
    openContainer = guiOwner.createContainer(inventory, this);
    openContainer.windowId = currentWindowId;
    openContainer.onCraftGuiOpened(this);
  }
  



  public void displayGUIChest(IInventory chestInventory)
  {
    if (openContainer != inventoryContainer)
    {
      closeScreen();
    }
    
    if ((chestInventory instanceof ILockableContainer))
    {
      ILockableContainer var2 = (ILockableContainer)chestInventory;
      
      if ((var2.isLocked()) && (!func_175146_a(var2.getLockCode())) && (!func_175149_v()))
      {
        playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentTranslation("container.isLocked", new Object[] { chestInventory.getDisplayName() }), (byte)2));
        playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S29PacketSoundEffect("random.door_close", posX, posY, posZ, 1.0F, 1.0F));
        return;
      }
    }
    
    getNextWindowId();
    
    if ((chestInventory instanceof IInteractionObject))
    {
      playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(currentWindowId, ((IInteractionObject)chestInventory).getGuiID(), chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
      openContainer = ((IInteractionObject)chestInventory).createContainer(inventory, this);
    }
    else
    {
      playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(currentWindowId, "minecraft:container", chestInventory.getDisplayName(), chestInventory.getSizeInventory()));
      openContainer = new ContainerChest(inventory, chestInventory, this);
    }
    
    openContainer.windowId = currentWindowId;
    openContainer.onCraftGuiOpened(this);
  }
  
  public void displayVillagerTradeGui(IMerchant villager)
  {
    getNextWindowId();
    openContainer = new ContainerMerchant(inventory, villager, worldObj);
    openContainer.windowId = currentWindowId;
    openContainer.onCraftGuiOpened(this);
    InventoryMerchant var2 = ((ContainerMerchant)openContainer).getMerchantInventory();
    IChatComponent var3 = villager.getDisplayName();
    playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(currentWindowId, "minecraft:villager", var3, var2.getSizeInventory()));
    MerchantRecipeList var4 = villager.getRecipes(this);
    
    if (var4 != null)
    {
      PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
      var5.writeInt(currentWindowId);
      var4.func_151391_a(var5);
      playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|TrList", var5));
    }
  }
  
  public void displayGUIHorse(EntityHorse p_110298_1_, IInventory p_110298_2_)
  {
    if (openContainer != inventoryContainer)
    {
      closeScreen();
    }
    
    getNextWindowId();
    playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(currentWindowId, "EntityHorse", p_110298_2_.getDisplayName(), p_110298_2_.getSizeInventory(), p_110298_1_.getEntityId()));
    openContainer = new net.minecraft.inventory.ContainerHorseInventory(inventory, p_110298_2_, p_110298_1_, this);
    openContainer.windowId = currentWindowId;
    openContainer.onCraftGuiOpened(this);
  }
  



  public void displayGUIBook(ItemStack bookStack)
  {
    Item var2 = bookStack.getItem();
    
    if (var2 == Items.written_book)
    {
      playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
    }
  }
  




  public void sendSlotContents(Container p_71111_1_, int p_71111_2_, ItemStack p_71111_3_)
  {
    if (!(p_71111_1_.getSlot(p_71111_2_) instanceof net.minecraft.inventory.SlotCrafting))
    {
      if (!isChangingQuantityOnly)
      {
        playerNetServerHandler.sendPacket(new S2FPacketSetSlot(windowId, p_71111_2_, p_71111_3_));
      }
    }
  }
  
  public void sendContainerToPlayer(Container p_71120_1_)
  {
    updateCraftingInventory(p_71120_1_, p_71120_1_.getInventory());
  }
  



  public void updateCraftingInventory(Container p_71110_1_, List p_71110_2_)
  {
    playerNetServerHandler.sendPacket(new S30PacketWindowItems(windowId, p_71110_2_));
    playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, inventory.getItemStack()));
  }
  





  public void sendProgressBarUpdate(Container p_71112_1_, int p_71112_2_, int p_71112_3_)
  {
    playerNetServerHandler.sendPacket(new S31PacketWindowProperty(windowId, p_71112_2_, p_71112_3_));
  }
  
  public void func_175173_a(Container p_175173_1_, IInventory p_175173_2_)
  {
    for (int var3 = 0; var3 < p_175173_2_.getFieldCount(); var3++)
    {
      playerNetServerHandler.sendPacket(new S31PacketWindowProperty(windowId, var3, p_175173_2_.getField(var3)));
    }
  }
  



  public void closeScreen()
  {
    playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S2EPacketCloseWindow(openContainer.windowId));
    closeContainer();
  }
  



  public void updateHeldItem()
  {
    if (!isChangingQuantityOnly)
    {
      playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, inventory.getItemStack()));
    }
  }
  



  public void closeContainer()
  {
    openContainer.onContainerClosed(this);
    openContainer = inventoryContainer;
  }
  
  public void setEntityActionState(float p_110430_1_, float p_110430_2_, boolean p_110430_3_, boolean p_110430_4_)
  {
    if (ridingEntity != null)
    {
      if ((p_110430_1_ >= -1.0F) && (p_110430_1_ <= 1.0F))
      {
        moveStrafing = p_110430_1_;
      }
      
      if ((p_110430_2_ >= -1.0F) && (p_110430_2_ <= 1.0F))
      {
        moveForward = p_110430_2_;
      }
      
      isJumping = p_110430_3_;
      setSneaking(p_110430_4_);
    }
  }
  



  public void addStat(StatBase p_71064_1_, int p_71064_2_)
  {
    if (p_71064_1_ != null)
    {
      statsFile.func_150871_b(this, p_71064_1_, p_71064_2_);
      Iterator var3 = getWorldScoreboard().func_96520_a(p_71064_1_.func_150952_k()).iterator();
      
      while (var3.hasNext())
      {
        ScoreObjective var4 = (ScoreObjective)var3.next();
        getWorldScoreboard().getValueFromObjective(getName(), var4).increseScore(p_71064_2_);
      }
      
      if (statsFile.func_150879_e())
      {
        statsFile.func_150876_a(this);
      }
    }
  }
  
  public void func_175145_a(StatBase p_175145_1_)
  {
    if (p_175145_1_ != null)
    {
      statsFile.func_150873_a(this, p_175145_1_, 0);
      Iterator var2 = getWorldScoreboard().func_96520_a(p_175145_1_.func_150952_k()).iterator();
      
      while (var2.hasNext())
      {
        ScoreObjective var3 = (ScoreObjective)var2.next();
        getWorldScoreboard().getValueFromObjective(getName(), var3).setScorePoints(0);
      }
      
      if (statsFile.func_150879_e())
      {
        statsFile.func_150876_a(this);
      }
    }
  }
  
  public void mountEntityAndWakeUp()
  {
    if (riddenByEntity != null)
    {
      riddenByEntity.mountEntity(this);
    }
    
    if (sleeping)
    {
      wakeUpPlayer(true, false, false);
    }
  }
  




  public void setPlayerHealthUpdated()
  {
    lastHealth = -1.0E8F;
  }
  
  public void addChatComponentMessage(IChatComponent p_146105_1_)
  {
    playerNetServerHandler.sendPacket(new S02PacketChat(p_146105_1_));
  }
  



  protected void onItemUseFinish()
  {
    playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S19PacketEntityStatus(this, (byte)9));
    super.onItemUseFinish();
  }
  



  public void setItemInUse(ItemStack p_71008_1_, int p_71008_2_)
  {
    super.setItemInUse(p_71008_1_, p_71008_2_);
    
    if ((p_71008_1_ != null) && (p_71008_1_.getItem() != null) && (p_71008_1_.getItem().getItemUseAction(p_71008_1_) == EnumAction.EAT))
    {
      getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 3));
    }
  }
  




  public void clonePlayer(EntityPlayer p_71049_1_, boolean p_71049_2_)
  {
    super.clonePlayer(p_71049_1_, p_71049_2_);
    lastExperience = -1;
    lastHealth = -1.0F;
    lastFoodLevel = -1;
    destroyedItemsNetCache.addAll(destroyedItemsNetCache);
  }
  
  protected void onNewPotionEffect(PotionEffect p_70670_1_)
  {
    super.onNewPotionEffect(p_70670_1_);
    playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(getEntityId(), p_70670_1_));
  }
  
  protected void onChangedPotionEffect(PotionEffect p_70695_1_, boolean p_70695_2_)
  {
    super.onChangedPotionEffect(p_70695_1_, p_70695_2_);
    playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(getEntityId(), p_70695_1_));
  }
  
  protected void onFinishedPotionEffect(PotionEffect p_70688_1_)
  {
    super.onFinishedPotionEffect(p_70688_1_);
    playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(getEntityId(), p_70688_1_));
  }
  



  public void setPositionAndUpdate(double p_70634_1_, double p_70634_3_, double p_70634_5_)
  {
    playerNetServerHandler.setPlayerLocation(p_70634_1_, p_70634_3_, p_70634_5_, rotationYaw, rotationPitch);
  }
  



  public void onCriticalHit(Entity p_71009_1_)
  {
    getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(p_71009_1_, 4));
  }
  
  public void onEnchantmentCritical(Entity p_71047_1_)
  {
    getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(p_71047_1_, 5));
  }
  



  public void sendPlayerAbilities()
  {
    if (playerNetServerHandler != null)
    {
      playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S39PacketPlayerAbilities(capabilities));
      func_175135_B();
    }
  }
  
  public WorldServer getServerForPlayer()
  {
    return (WorldServer)worldObj;
  }
  



  public void setGameType(WorldSettings.GameType gameType)
  {
    theItemInWorldManager.setGameType(gameType);
    playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(3, gameType.getID()));
    
    if (gameType == WorldSettings.GameType.SPECTATOR)
    {
      mountEntity(null);
    }
    else
    {
      func_175399_e(this);
    }
    
    sendPlayerAbilities();
    func_175136_bO();
  }
  
  public boolean func_175149_v()
  {
    return theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR;
  }
  






  public void addChatMessage(IChatComponent message)
  {
    playerNetServerHandler.sendPacket(new S02PacketChat(message));
  }
  



  public boolean canCommandSenderUseCommand(int permissionLevel, String command)
  {
    if (("seed".equals(command)) && (!mcServer.isDedicatedServer()))
    {
      return true;
    }
    if ((!"tell".equals(command)) && (!"help".equals(command)) && (!"me".equals(command)) && (!"trigger".equals(command)))
    {
      if (mcServer.getConfigurationManager().canSendCommands(getGameProfile()))
      {
        UserListOpsEntry var3 = (UserListOpsEntry)mcServer.getConfigurationManager().getOppedPlayers().getEntry(getGameProfile());
        return var3.func_152644_a() >= permissionLevel;
      }
      

      return false;
    }
    


    return true;
  }
  




  public String getPlayerIP()
  {
    String var1 = playerNetServerHandler.netManager.getRemoteAddress().toString();
    var1 = var1.substring(var1.indexOf("/") + 1);
    var1 = var1.substring(0, var1.indexOf(":"));
    return var1;
  }
  
  public void handleClientSettings(C15PacketClientSettings p_147100_1_)
  {
    translator = p_147100_1_.getLang();
    chatVisibility = p_147100_1_.getChatVisibility();
    chatColours = p_147100_1_.isColorsEnabled();
    getDataWatcher().updateObject(10, Byte.valueOf((byte)p_147100_1_.getView()));
  }
  
  public EntityPlayer.EnumChatVisibility getChatVisibility()
  {
    return chatVisibility;
  }
  
  public void func_175397_a(String p_175397_1_, String p_175397_2_)
  {
    playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S48PacketResourcePackSend(p_175397_1_, p_175397_2_));
  }
  
  public BlockPos getPosition()
  {
    return new BlockPos(posX, posY + 0.5D, posZ);
  }
  
  public void markPlayerActive()
  {
    playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
  }
  



  public StatisticsFile getStatFile()
  {
    return statsFile;
  }
  
  public void func_152339_d(Entity p_152339_1_)
  {
    if ((p_152339_1_ instanceof EntityPlayer))
    {
      playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(new int[] { p_152339_1_.getEntityId() }));
    }
    else
    {
      destroyedItemsNetCache.add(Integer.valueOf(p_152339_1_.getEntityId()));
    }
  }
  
  protected void func_175135_B()
  {
    if (func_175149_v())
    {
      func_175133_bi();
      setInvisible(true);
    }
    else
    {
      super.func_175135_B();
    }
    
    getServerForPlayer().getEntityTracker().func_180245_a(this);
  }
  
  public Entity func_175398_C()
  {
    return field_175401_bS == null ? this : field_175401_bS;
  }
  
  public void func_175399_e(Entity p_175399_1_)
  {
    Entity var2 = func_175398_C();
    field_175401_bS = (p_175399_1_ == null ? this : p_175399_1_);
    
    if (var2 != field_175401_bS)
    {
      playerNetServerHandler.sendPacket(new net.minecraft.network.play.server.S43PacketCamera(field_175401_bS));
      setPositionAndUpdate(field_175401_bS.posX, field_175401_bS.posY, field_175401_bS.posZ);
    }
  }
  




  public void attackTargetEntityWithCurrentItem(Entity targetEntity)
  {
    if (theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR)
    {
      func_175399_e(targetEntity);
    }
    else
    {
      super.attackTargetEntityWithCurrentItem(targetEntity);
    }
  }
  
  public long getLastActiveTime()
  {
    return playerLastActiveTime;
  }
  
  public IChatComponent func_175396_E()
  {
    return null;
  }
}
