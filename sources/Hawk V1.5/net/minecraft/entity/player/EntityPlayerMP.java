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
import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.ContainerHorseInventory;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryMerchant;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMapBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.potion.PotionEffect;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.ItemInWorldManager;
import net.minecraft.server.management.UserListOpsEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.stats.StatList;
import net.minecraft.stats.StatisticsFile;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EntityDamageSource;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.JsonSerializableSet;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ReportedException;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.ILockableContainer;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.chunk.Chunk;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityPlayerMP extends EntityPlayer implements ICrafting {
   public boolean isChangingQuantityOnly;
   public double managedPosX;
   private int lastExperience = -99999999;
   public final ItemInWorldManager theItemInWorldManager;
   public int ping;
   private Entity field_175401_bS = null;
   public boolean playerConqueredTheEnd;
   public final List loadedChunks = Lists.newLinkedList();
   private String translator = "en_US";
   private int lastFoodLevel = -99999999;
   private static final String __OBFID = "CL_00001440";
   public NetHandlerPlayServer playerNetServerHandler;
   private int currentWindowId;
   public double managedPosZ;
   private int respawnInvulnerabilityTicks = 60;
   private float field_130068_bO = Float.MIN_VALUE;
   private boolean chatColours = true;
   private static final Logger logger = LogManager.getLogger();
   private long playerLastActiveTime = System.currentTimeMillis();
   private EntityPlayer.EnumChatVisibility chatVisibility;
   private float lastHealth = -1.0E8F;
   private final List destroyedItemsNetCache = Lists.newLinkedList();
   public final MinecraftServer mcServer;
   private final StatisticsFile statsFile;
   private boolean wasHungry = true;

   public EntityPlayer.EnumChatVisibility getChatVisibility() {
      return this.chatVisibility;
   }

   public void mountEntity(Entity var1) {
      Entity var2 = this.ridingEntity;
      super.mountEntity(var1);
      if (var1 != var2) {
         this.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this, this.ridingEntity));
         this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
      }

   }

   public void func_175141_a(TileEntitySign var1) {
      var1.func_145912_a(this);
      this.playerNetServerHandler.sendPacket(new S36PacketSignEditorOpen(var1.getPos()));
   }

   protected void onNewPotionEffect(PotionEffect var1) {
      super.onNewPotionEffect(var1);
      this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), var1));
   }

   public void updateHeldItem() {
      if (!this.isChangingQuantityOnly) {
         this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
      }

   }

   public void sendProgressBarUpdate(Container var1, int var2, int var3) {
      this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(var1.windowId, var2, var3));
   }

   public void clonePlayer(EntityPlayer var1, boolean var2) {
      super.clonePlayer(var1, var2);
      this.lastExperience = -1;
      this.lastHealth = -1.0F;
      this.lastFoodLevel = -1;
      this.destroyedItemsNetCache.addAll(((EntityPlayerMP)var1).destroyedItemsNetCache);
   }

   public void displayGUIHorse(EntityHorse var1, IInventory var2) {
      if (this.openContainer != this.inventoryContainer) {
         this.closeScreen();
      }

      this.getNextWindowId();
      this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "EntityHorse", var2.getDisplayName(), var2.getSizeInventory(), var1.getEntityId()));
      this.openContainer = new ContainerHorseInventory(this.inventory, var2, var1, this);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.onCraftGuiOpened(this);
   }

   public void onUpdateEntity() {
      try {
         super.onUpdate();

         for(int var1 = 0; var1 < this.inventory.getSizeInventory(); ++var1) {
            ItemStack var6 = this.inventory.getStackInSlot(var1);
            if (var6 != null && var6.getItem().isMap()) {
               Packet var8 = ((ItemMapBase)var6.getItem()).createMapDataPacket(var6, this.worldObj, this);
               if (var8 != null) {
                  this.playerNetServerHandler.sendPacket(var8);
               }
            }
         }

         if (this.getHealth() != this.lastHealth || this.lastFoodLevel != this.foodStats.getFoodLevel() || this.foodStats.getSaturationLevel() == 0.0F != this.wasHungry) {
            this.playerNetServerHandler.sendPacket(new S06PacketUpdateHealth(this.getHealth(), this.foodStats.getFoodLevel(), this.foodStats.getSaturationLevel()));
            this.lastHealth = this.getHealth();
            this.lastFoodLevel = this.foodStats.getFoodLevel();
            this.wasHungry = this.foodStats.getSaturationLevel() == 0.0F;
         }

         if (this.getHealth() + this.getAbsorptionAmount() != this.field_130068_bO) {
            this.field_130068_bO = this.getHealth() + this.getAbsorptionAmount();
            Collection var5 = this.getWorldScoreboard().func_96520_a(IScoreObjectiveCriteria.health);
            Iterator var7 = var5.iterator();

            while(var7.hasNext()) {
               ScoreObjective var9 = (ScoreObjective)var7.next();
               this.getWorldScoreboard().getValueFromObjective(this.getName(), var9).func_96651_a(Arrays.asList(this));
            }
         }

         if (this.experienceTotal != this.lastExperience) {
            this.lastExperience = this.experienceTotal;
            this.playerNetServerHandler.sendPacket(new S1FPacketSetExperience(this.experience, this.experienceTotal, this.experienceLevel));
         }

         if (this.ticksExisted % 20 * 5 == 0 && !this.getStatFile().hasAchievementUnlocked(AchievementList.exploreAllBiomes)) {
            this.func_147098_j();
         }

      } catch (Throwable var4) {
         CrashReport var2 = CrashReport.makeCrashReport(var4, "Ticking player");
         CrashReportCategory var3 = var2.makeCategory("Player being ticked");
         this.addEntityCrashInfo(var3);
         throw new ReportedException(var2);
      }
   }

   public void onItemPickup(Entity var1, int var2) {
      super.onItemPickup(var1, var2);
      this.openContainer.detectAndSendChanges();
   }

   public void setPositionAndUpdate(double var1, double var3, double var5) {
      this.playerNetServerHandler.setPlayerLocation(var1, var3, var5, this.rotationYaw, this.rotationPitch);
   }

   public void closeScreen() {
      this.playerNetServerHandler.sendPacket(new S2EPacketCloseWindow(this.openContainer.windowId));
      this.closeContainer();
   }

   protected void onFinishedPotionEffect(PotionEffect var1) {
      super.onFinishedPotionEffect(var1);
      this.playerNetServerHandler.sendPacket(new S1EPacketRemoveEntityEffect(this.getEntityId(), var1));
   }

   public void func_152339_d(Entity var1) {
      if (var1 instanceof EntityPlayer) {
         this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(new int[]{var1.getEntityId()}));
      } else {
         this.destroyedItemsNetCache.add(var1.getEntityId());
      }

   }

   public void displayVillagerTradeGui(IMerchant var1) {
      this.getNextWindowId();
      this.openContainer = new ContainerMerchant(this.inventory, var1, this.worldObj);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.onCraftGuiOpened(this);
      InventoryMerchant var2 = ((ContainerMerchant)this.openContainer).getMerchantInventory();
      IChatComponent var3 = var1.getDisplayName();
      this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "minecraft:villager", var3, var2.getSizeInventory()));
      MerchantRecipeList var4 = var1.getRecipes(this);
      if (var4 != null) {
         PacketBuffer var5 = new PacketBuffer(Unpooled.buffer());
         var5.writeInt(this.currentWindowId);
         var4.func_151391_a(var5);
         this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|TrList", var5));
      }

   }

   public void travelToDimension(int var1) {
      if (this.dimension == 1 && var1 == 1) {
         this.triggerAchievement(AchievementList.theEnd2);
         this.worldObj.removeEntity(this);
         this.playerConqueredTheEnd = true;
         this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(4, 0.0F));
      } else {
         if (this.dimension == 0 && var1 == 1) {
            this.triggerAchievement(AchievementList.theEnd);
            BlockPos var2 = this.mcServer.worldServerForDimension(var1).func_180504_m();
            if (var2 != null) {
               this.playerNetServerHandler.setPlayerLocation((double)var2.getX(), (double)var2.getY(), (double)var2.getZ(), 0.0F, 0.0F);
            }

            var1 = 1;
         } else {
            this.triggerAchievement(AchievementList.portal);
         }

         this.mcServer.getConfigurationManager().transferPlayerToDimension(this, var1);
         this.lastExperience = -1;
         this.lastHealth = -1.0F;
         this.lastFoodLevel = -1;
      }

   }

   protected void func_147098_j() {
      BiomeGenBase var1 = this.worldObj.getBiomeGenForCoords(new BlockPos(MathHelper.floor_double(this.posX), 0, MathHelper.floor_double(this.posZ)));
      String var2 = var1.biomeName;
      JsonSerializableSet var3 = (JsonSerializableSet)this.getStatFile().func_150870_b(AchievementList.exploreAllBiomes);
      if (var3 == null) {
         var3 = (JsonSerializableSet)this.getStatFile().func_150872_a(AchievementList.exploreAllBiomes, new JsonSerializableSet());
      }

      var3.add(var2);
      if (this.getStatFile().canUnlockAchievement(AchievementList.exploreAllBiomes) && var3.size() >= BiomeGenBase.explorationBiomesList.size()) {
         HashSet var4 = Sets.newHashSet(BiomeGenBase.explorationBiomesList);
         Iterator var5 = var3.iterator();

         while(var5.hasNext()) {
            String var6 = (String)var5.next();
            Iterator var7 = var4.iterator();

            while(var7.hasNext()) {
               BiomeGenBase var8 = (BiomeGenBase)var7.next();
               if (var8.biomeName.equals(var6)) {
                  var7.remove();
               }
            }

            if (var4.isEmpty()) {
               break;
            }
         }

         if (var4.isEmpty()) {
            this.triggerAchievement(AchievementList.exploreAllBiomes);
         }
      }

   }

   public void onDeath(DamageSource var1) {
      if (this.worldObj.getGameRules().getGameRuleBooleanValue("showDeathMessages")) {
         Team var2 = this.getTeam();
         if (var2 != null && var2.func_178771_j() != Team.EnumVisible.ALWAYS) {
            if (var2.func_178771_j() == Team.EnumVisible.HIDE_FOR_OTHER_TEAMS) {
               this.mcServer.getConfigurationManager().func_177453_a(this, this.getCombatTracker().func_151521_b());
            } else if (var2.func_178771_j() == Team.EnumVisible.HIDE_FOR_OWN_TEAM) {
               this.mcServer.getConfigurationManager().func_177452_b(this, this.getCombatTracker().func_151521_b());
            }
         } else {
            this.mcServer.getConfigurationManager().sendChatMsg(this.getCombatTracker().func_151521_b());
         }
      }

      if (!this.worldObj.getGameRules().getGameRuleBooleanValue("keepInventory")) {
         this.inventory.dropAllItems();
      }

      Collection var7 = this.worldObj.getScoreboard().func_96520_a(IScoreObjectiveCriteria.deathCount);
      Iterator var3 = var7.iterator();

      while(var3.hasNext()) {
         ScoreObjective var4 = (ScoreObjective)var3.next();
         Score var5 = this.getWorldScoreboard().getValueFromObjective(this.getName(), var4);
         var5.func_96648_a();
      }

      EntityLivingBase var8 = this.func_94060_bK();
      if (var8 != null) {
         EntityList.EntityEggInfo var6 = (EntityList.EntityEggInfo)EntityList.entityEggs.get(EntityList.getEntityID(var8));
         if (var6 != null) {
            this.triggerAchievement(var6.field_151513_e);
         }

         var8.addToPlayerScore(this, this.scoreValue);
      }

      this.triggerAchievement(StatList.deathsStat);
      this.func_175145_a(StatList.timeSinceDeathStat);
      this.getCombatTracker().func_94549_h();
   }

   public void sendContainerToPlayer(Container var1) {
      this.updateCraftingInventory(var1, var1.getInventory());
   }

   public void addChatComponentMessage(IChatComponent var1) {
      this.playerNetServerHandler.sendPacket(new S02PacketChat(var1));
   }

   public EntityPlayer.EnumStatus func_180469_a(BlockPos var1) {
      EntityPlayer.EnumStatus var2 = super.func_180469_a(var1);
      if (var2 == EntityPlayer.EnumStatus.OK) {
         S0APacketUseBed var3 = new S0APacketUseBed(this, var1);
         this.getServerForPlayer().getEntityTracker().sendToAllTrackingEntity(this, var3);
         this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
         this.playerNetServerHandler.sendPacket(var3);
      }

      return var2;
   }

   public boolean func_175149_v() {
      return this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR;
   }

   public void sendSlotContents(Container var1, int var2, ItemStack var3) {
      if (!(var1.getSlot(var2) instanceof SlotCrafting) && !this.isChangingQuantityOnly) {
         this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(var1.windowId, var2, var3));
      }

   }

   public void readEntityFromNBT(NBTTagCompound var1) {
      super.readEntityFromNBT(var1);
      if (var1.hasKey("playerGameType", 99)) {
         if (MinecraftServer.getServer().getForceGamemode()) {
            this.theItemInWorldManager.setGameType(MinecraftServer.getServer().getGameType());
         } else {
            this.theItemInWorldManager.setGameType(WorldSettings.GameType.getByID(var1.getInteger("playerGameType")));
         }
      }

   }

   public void addExperienceLevel(int var1) {
      super.addExperienceLevel(var1);
      this.lastExperience = -1;
   }

   public void func_175145_a(StatBase var1) {
      if (var1 != null) {
         this.statsFile.func_150873_a(this, var1, 0);
         Iterator var2 = this.getWorldScoreboard().func_96520_a(var1.func_150952_k()).iterator();

         while(var2.hasNext()) {
            ScoreObjective var3 = (ScoreObjective)var2.next();
            this.getWorldScoreboard().getValueFromObjective(this.getName(), var3).setScorePoints(0);
         }

         if (this.statsFile.func_150879_e()) {
            this.statsFile.func_150876_a(this);
         }
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

   public void handleFalling(double var1, boolean var3) {
      int var4 = MathHelper.floor_double(this.posX);
      int var5 = MathHelper.floor_double(this.posY - 0.20000000298023224D);
      int var6 = MathHelper.floor_double(this.posZ);
      BlockPos var7 = new BlockPos(var4, var5, var6);
      Block var8 = this.worldObj.getBlockState(var7).getBlock();
      if (var8.getMaterial() == Material.air) {
         Block var9 = this.worldObj.getBlockState(var7.offsetDown()).getBlock();
         if (var9 instanceof BlockFence || var9 instanceof BlockWall || var9 instanceof BlockFenceGate) {
            var7 = var7.offsetDown();
            var8 = this.worldObj.getBlockState(var7).getBlock();
         }
      }

      super.func_180433_a(var1, var3, var8, var7);
   }

   private void getNextWindowId() {
      this.currentWindowId = this.currentWindowId % 100 + 1;
   }

   public void setPlayerHealthUpdated() {
      this.lastHealth = -1.0E8F;
   }

   public boolean canAttackPlayer(EntityPlayer var1) {
      return !this.func_175400_cq() ? false : super.canAttackPlayer(var1);
   }

   public void displayGUIChest(IInventory var1) {
      if (this.openContainer != this.inventoryContainer) {
         this.closeScreen();
      }

      if (var1 instanceof ILockableContainer) {
         ILockableContainer var2 = (ILockableContainer)var1;
         if (var2.isLocked() && !this.func_175146_a(var2.getLockCode()) && !this.func_175149_v()) {
            this.playerNetServerHandler.sendPacket(new S02PacketChat(new ChatComponentTranslation("container.isLocked", new Object[]{var1.getDisplayName()}), (byte)2));
            this.playerNetServerHandler.sendPacket(new S29PacketSoundEffect("random.door_close", this.posX, this.posY, this.posZ, 1.0F, 1.0F));
            return;
         }
      }

      this.getNextWindowId();
      if (var1 instanceof IInteractionObject) {
         this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, ((IInteractionObject)var1).getGuiID(), var1.getDisplayName(), var1.getSizeInventory()));
         this.openContainer = ((IInteractionObject)var1).createContainer(this.inventory, this);
      } else {
         this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, "minecraft:container", var1.getDisplayName(), var1.getSizeInventory()));
         this.openContainer = new ContainerChest(this.inventory, var1, this);
      }

      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.onCraftGuiOpened(this);
   }

   protected void func_180433_a(double var1, boolean var3, Block var4, BlockPos var5) {
   }

   public WorldServer getServerForPlayer() {
      return (WorldServer)this.worldObj;
   }

   public void wakeUpPlayer(boolean var1, boolean var2, boolean var3) {
      if (this.isPlayerSleeping()) {
         this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 2));
      }

      super.wakeUpPlayer(var1, var2, var3);
      if (this.playerNetServerHandler != null) {
         this.playerNetServerHandler.setPlayerLocation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
      }

   }

   public void func_152112_bu() {
      super.func_152112_bu();
      this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.END_COMBAT));
   }

   public BlockPos getPosition() {
      return new BlockPos(this.posX, this.posY + 0.5D, this.posZ);
   }

   public void setEntityActionState(float var1, float var2, boolean var3, boolean var4) {
      if (this.ridingEntity != null) {
         if (var1 >= -1.0F && var1 <= 1.0F) {
            this.moveStrafing = var1;
         }

         if (var2 >= -1.0F && var2 <= 1.0F) {
            this.moveForward = var2;
         }

         this.isJumping = var3;
         this.setSneaking(var4);
      }

   }

   public void closeContainer() {
      this.openContainer.onContainerClosed(this);
      this.openContainer = this.inventoryContainer;
   }

   public void displayGUIBook(ItemStack var1) {
      Item var2 = var1.getItem();
      if (var2 == Items.written_book) {
         this.playerNetServerHandler.sendPacket(new S3FPacketCustomPayload("MC|BOpen", new PacketBuffer(Unpooled.buffer())));
      }

   }

   public StatisticsFile getStatFile() {
      return this.statsFile;
   }

   public void setGameType(WorldSettings.GameType var1) {
      this.theItemInWorldManager.setGameType(var1);
      this.playerNetServerHandler.sendPacket(new S2BPacketChangeGameState(3, (float)var1.getID()));
      if (var1 == WorldSettings.GameType.SPECTATOR) {
         this.mountEntity((Entity)null);
      } else {
         this.func_175399_e(this);
      }

      this.sendPlayerAbilities();
      this.func_175136_bO();
   }

   public boolean attackEntityFrom(DamageSource var1, float var2) {
      if (this.func_180431_b(var1)) {
         return false;
      } else {
         boolean var3 = this.mcServer.isDedicatedServer() && this.func_175400_cq() && "fall".equals(var1.damageType);
         if (!var3 && this.respawnInvulnerabilityTicks > 0 && var1 != DamageSource.outOfWorld) {
            return false;
         } else {
            if (var1 instanceof EntityDamageSource) {
               Entity var4 = var1.getEntity();
               if (var4 instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var4)) {
                  return false;
               }

               if (var4 instanceof EntityArrow) {
                  EntityArrow var5 = (EntityArrow)var4;
                  if (var5.shootingEntity instanceof EntityPlayer && !this.canAttackPlayer((EntityPlayer)var5.shootingEntity)) {
                     return false;
                  }
               }
            }

            return super.attackEntityFrom(var1, var2);
         }
      }
   }

   public void updateCraftingInventory(Container var1, List var2) {
      this.playerNetServerHandler.sendPacket(new S30PacketWindowItems(var1.windowId, var2));
      this.playerNetServerHandler.sendPacket(new S2FPacketSetSlot(-1, -1, this.inventory.getItemStack()));
   }

   public void func_175397_a(String var1, String var2) {
      this.playerNetServerHandler.sendPacket(new S48PacketResourcePackSend(var1, var2));
   }

   public void setItemInUse(ItemStack var1, int var2) {
      super.setItemInUse(var1, var2);
      if (var1 != null && var1.getItem() != null && var1.getItem().getItemUseAction(var1) == EnumAction.EAT) {
         this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(this, 3));
      }

   }

   protected void func_175135_B() {
      if (this.func_175149_v()) {
         this.func_175133_bi();
         this.setInvisible(true);
      } else {
         super.func_175135_B();
      }

      this.getServerForPlayer().getEntityTracker().func_180245_a(this);
   }

   public void addSelfToInternalCraftingInventory() {
      this.openContainer.onCraftGuiOpened(this);
   }

   public IChatComponent func_175396_E() {
      return null;
   }

   protected void onItemUseFinish() {
      this.playerNetServerHandler.sendPacket(new S19PacketEntityStatus(this, (byte)9));
      super.onItemUseFinish();
   }

   public void func_175173_a(Container var1, IInventory var2) {
      for(int var3 = 0; var3 < var2.getFieldCount(); ++var3) {
         this.playerNetServerHandler.sendPacket(new S31PacketWindowProperty(var1.windowId, var3, var2.getField(var3)));
      }

   }

   public void func_175399_e(Entity var1) {
      Entity var2 = this.func_175398_C();
      this.field_175401_bS = (Entity)(var1 == null ? this : var1);
      if (var2 != this.field_175401_bS) {
         this.playerNetServerHandler.sendPacket(new S43PacketCamera(this.field_175401_bS));
         this.setPositionAndUpdate(this.field_175401_bS.posX, this.field_175401_bS.posY, this.field_175401_bS.posZ);
      }

   }

   public void handleClientSettings(C15PacketClientSettings var1) {
      this.translator = var1.getLang();
      this.chatVisibility = var1.getChatVisibility();
      this.chatColours = var1.isColorsEnabled();
      this.getDataWatcher().updateObject(10, (byte)var1.getView());
   }

   public void writeEntityToNBT(NBTTagCompound var1) {
      super.writeEntityToNBT(var1);
      var1.setInteger("playerGameType", this.theItemInWorldManager.getGameType().getID());
   }

   public EntityPlayerMP(MinecraftServer var1, WorldServer var2, GameProfile var3, ItemInWorldManager var4) {
      super(var2, var3);
      var4.thisPlayerMP = this;
      this.theItemInWorldManager = var4;
      BlockPos var5 = var2.getSpawnPoint();
      if (!var2.provider.getHasNoSky() && var2.getWorldInfo().getGameType() != WorldSettings.GameType.ADVENTURE) {
         int var6 = Math.max(5, var1.getSpawnProtectionSize() - 6);
         int var7 = MathHelper.floor_double(var2.getWorldBorder().getClosestDistance((double)var5.getX(), (double)var5.getZ()));
         if (var7 < var6) {
            var6 = var7;
         }

         if (var7 <= 1) {
            var6 = 1;
         }

         var5 = var2.func_175672_r(var5.add(this.rand.nextInt(var6 * 2) - var6, 0, this.rand.nextInt(var6 * 2) - var6));
      }

      this.mcServer = var1;
      this.statsFile = var1.getConfigurationManager().getPlayerStatsFile(this);
      this.stepHeight = 0.0F;
      this.func_174828_a(var5, 0.0F, 0.0F);

      while(!var2.getCollidingBoundingBoxes(this, this.getEntityBoundingBox()).isEmpty() && this.posY < 255.0D) {
         this.setPosition(this.posX, this.posY + 1.0D, this.posZ);
      }

   }

   public void attackTargetEntityWithCurrentItem(Entity var1) {
      if (this.theItemInWorldManager.getGameType() == WorldSettings.GameType.SPECTATOR) {
         this.func_175399_e(var1);
      } else {
         super.attackTargetEntityWithCurrentItem(var1);
      }

   }

   public void onUpdate() {
      this.theItemInWorldManager.updateBlockRemoving();
      --this.respawnInvulnerabilityTicks;
      if (this.hurtResistantTime > 0) {
         --this.hurtResistantTime;
      }

      this.openContainer.detectAndSendChanges();
      if (!this.worldObj.isRemote && !this.openContainer.canInteractWith(this)) {
         this.closeScreen();
         this.openContainer = this.inventoryContainer;
      }

      while(!this.destroyedItemsNetCache.isEmpty()) {
         int var1 = Math.min(this.destroyedItemsNetCache.size(), Integer.MAX_VALUE);
         int[] var2 = new int[var1];
         Iterator var3 = this.destroyedItemsNetCache.iterator();
         int var4 = 0;

         while(var3.hasNext() && var4 < var1) {
            var2[var4++] = (Integer)var3.next();
            var3.remove();
         }

         this.playerNetServerHandler.sendPacket(new S13PacketDestroyEntities(var2));
      }

      if (!this.loadedChunks.isEmpty()) {
         ArrayList var7 = Lists.newArrayList();
         Iterator var9 = this.loadedChunks.iterator();
         ArrayList var10 = Lists.newArrayList();

         Chunk var11;
         while(var9.hasNext() && var7.size() < 10) {
            ChunkCoordIntPair var5 = (ChunkCoordIntPair)var9.next();
            if (var5 != null) {
               if (this.worldObj.isBlockLoaded(new BlockPos(var5.chunkXPos << 4, 0, var5.chunkZPos << 4))) {
                  var11 = this.worldObj.getChunkFromChunkCoords(var5.chunkXPos, var5.chunkZPos);
                  if (var11.isPopulated()) {
                     var7.add(var11);
                     var10.addAll(((WorldServer)this.worldObj).func_147486_a(var5.chunkXPos * 16, 0, var5.chunkZPos * 16, var5.chunkXPos * 16 + 16, 256, var5.chunkZPos * 16 + 16));
                     var9.remove();
                  }
               }
            } else {
               var9.remove();
            }
         }

         if (!var7.isEmpty()) {
            if (var7.size() == 1) {
               this.playerNetServerHandler.sendPacket(new S21PacketChunkData((Chunk)var7.get(0), true, 65535));
            } else {
               this.playerNetServerHandler.sendPacket(new S26PacketMapChunkBulk(var7));
            }

            Iterator var12 = var10.iterator();

            while(var12.hasNext()) {
               TileEntity var6 = (TileEntity)var12.next();
               this.sendTileEntityUpdate(var6);
            }

            var12 = var7.iterator();

            while(var12.hasNext()) {
               var11 = (Chunk)var12.next();
               this.getServerForPlayer().getEntityTracker().func_85172_a(this, var11);
            }
         }
      }

      Entity var8 = this.func_175398_C();
      if (var8 != this) {
         if (!var8.isEntityAlive()) {
            this.func_175399_e(this);
         } else {
            this.setPositionAndRotation(var8.posX, var8.posY, var8.posZ, var8.rotationYaw, var8.rotationPitch);
            this.mcServer.getConfigurationManager().serverUpdateMountedMovingPlayer(this);
            if (this.isSneaking()) {
               this.func_175399_e(this);
            }
         }
      }

   }

   public void addStat(StatBase var1, int var2) {
      if (var1 != null) {
         this.statsFile.func_150871_b(this, var1, var2);
         Iterator var3 = this.getWorldScoreboard().func_96520_a(var1.func_150952_k()).iterator();

         while(var3.hasNext()) {
            ScoreObjective var4 = (ScoreObjective)var3.next();
            this.getWorldScoreboard().getValueFromObjective(this.getName(), var4).increseScore(var2);
         }

         if (this.statsFile.func_150879_e()) {
            this.statsFile.func_150876_a(this);
         }
      }

   }

   public void sendPlayerAbilities() {
      if (this.playerNetServerHandler != null) {
         this.playerNetServerHandler.sendPacket(new S39PacketPlayerAbilities(this.capabilities));
         this.func_175135_B();
      }

   }

   public void addChatMessage(IChatComponent var1) {
      this.playerNetServerHandler.sendPacket(new S02PacketChat(var1));
   }

   public boolean func_174827_a(EntityPlayerMP var1) {
      return var1.func_175149_v() ? this.func_175398_C() == this : (this.func_175149_v() ? false : super.func_174827_a(var1));
   }

   public String getPlayerIP() {
      String var1 = this.playerNetServerHandler.netManager.getRemoteAddress().toString();
      var1 = var1.substring(var1.indexOf("/") + 1);
      var1 = var1.substring(0, var1.indexOf(":"));
      return var1;
   }

   private boolean func_175400_cq() {
      return this.mcServer.isPVPEnabled();
   }

   private void sendTileEntityUpdate(TileEntity var1) {
      if (var1 != null) {
         Packet var2 = var1.getDescriptionPacket();
         if (var2 != null) {
            this.playerNetServerHandler.sendPacket(var2);
         }
      }

   }

   public void func_152111_bt() {
      super.func_152111_bt();
      this.playerNetServerHandler.sendPacket(new S42PacketCombatEvent(this.getCombatTracker(), S42PacketCombatEvent.Event.ENTER_COMBAT));
   }

   public void onEnchantmentCritical(Entity var1) {
      this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(var1, 5));
   }

   public void markPlayerActive() {
      this.playerLastActiveTime = MinecraftServer.getCurrentTimeMillis();
   }

   public void onCriticalHit(Entity var1) {
      this.getServerForPlayer().getEntityTracker().func_151248_b(this, new S0BPacketAnimation(var1, 4));
   }

   public Entity func_175398_C() {
      return (Entity)(this.field_175401_bS == null ? this : this.field_175401_bS);
   }

   public long getLastActiveTime() {
      return this.playerLastActiveTime;
   }

   public void func_71013_b(int var1) {
      super.func_71013_b(var1);
      this.lastExperience = -1;
   }

   public void displayGui(IInteractionObject var1) {
      this.getNextWindowId();
      this.playerNetServerHandler.sendPacket(new S2DPacketOpenWindow(this.currentWindowId, var1.getGuiID(), var1.getDisplayName()));
      this.openContainer = var1.createContainer(this.inventory, this);
      this.openContainer.windowId = this.currentWindowId;
      this.openContainer.onCraftGuiOpened(this);
   }

   protected void onChangedPotionEffect(PotionEffect var1, boolean var2) {
      super.onChangedPotionEffect(var1, var2);
      this.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.getEntityId(), var1));
   }

   public boolean canCommandSenderUseCommand(int var1, String var2) {
      if ("seed".equals(var2) && !this.mcServer.isDedicatedServer()) {
         return true;
      } else if (!"tell".equals(var2) && !"help".equals(var2) && !"me".equals(var2) && !"trigger".equals(var2)) {
         if (this.mcServer.getConfigurationManager().canSendCommands(this.getGameProfile())) {
            UserListOpsEntry var3 = (UserListOpsEntry)this.mcServer.getConfigurationManager().getOppedPlayers().getEntry(this.getGameProfile());
            return var3 != null ? var3.func_152644_a() >= var1 : this.mcServer.getOpPermissionLevel() >= var1;
         } else {
            return false;
         }
      } else {
         return true;
      }
   }
}
