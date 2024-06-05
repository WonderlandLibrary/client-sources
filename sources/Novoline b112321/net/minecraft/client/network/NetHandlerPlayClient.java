package net.minecraft.client.network;

import cc.novoline.Novoline;
import cc.novoline.modules.player.NoRotate;
import com.google.common.collect.Maps;
import com.google.common.util.concurrent.Futures;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import net.AU;
import net.aHM;
import net.aHv;
import net.aLc;
import net.aTX;
import net.aXg;
import net.minecraft.block.Block;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.GuardianSound;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenDemo;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.multiplayer.ServerData$ServerResourceMode;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.client.network.NetHandlerPlayClient$1;
import net.minecraft.client.network.NetHandlerPlayClient$2;
import net.minecraft.client.network.NetHandlerPlayClient$3;
import net.minecraft.client.network.NetHandlerPlayClient$4;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.client.player.inventory.ContainerLocalMenu;
import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.client.stream.MetadataAchievement;
import net.minecraft.client.stream.MetadataCombat;
import net.minecraft.client.stream.MetadataPlayerDeath;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.NpcMerchant;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.BaseAttributeMap;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.ai.attributes.RangedAttribute;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.item.EntityEnderEye;
import net.minecraft.entity.item.EntityEnderPearl;
import net.minecraft.entity.item.EntityExpBottle;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.entity.item.EntityFireworkRocket;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.item.EntityMinecart$EnumMinecartType;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer$C06PacketPlayerPosLook;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.C19PacketResourcePackStatus$Action;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook$EnumFlags;
import net.minecraft.network.play.server.S09PacketHeldItemChange;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0BPacketAnimation;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0DPacketCollectItem;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import net.minecraft.network.play.server.S14PacketEntity;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S19PacketEntityStatus;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
import net.minecraft.network.play.server.S1FPacketSetExperience;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S20PacketEntityProperties$Snapshot;
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
import net.minecraft.network.play.server.S22PacketMultiBlockChange$BlockUpdateData;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S24PacketBlockAction;
import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
import net.minecraft.network.play.server.S26PacketMapChunkBulk;
import net.minecraft.network.play.server.S27PacketExplosion;
import net.minecraft.network.play.server.S28PacketEffect;
import net.minecraft.network.play.server.S29PacketSoundEffect;
import net.minecraft.network.play.server.S2APacketParticles;
import net.minecraft.network.play.server.S2BPacketChangeGameState;
import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
import net.minecraft.network.play.server.S2DPacketOpenWindow;
import net.minecraft.network.play.server.S2EPacketCloseWindow;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S30PacketWindowItems;
import net.minecraft.network.play.server.S31PacketWindowProperty;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S33PacketUpdateSign;
import net.minecraft.network.play.server.S34PacketMaps;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.network.play.server.S36PacketSignEditorOpen;
import net.minecraft.network.play.server.S37PacketStatistics;
import net.minecraft.network.play.server.S38PacketPlayerListItem;
import net.minecraft.network.play.server.S38PacketPlayerListItem$Action;
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3CPacketUpdateScore$Action;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S42PacketCombatEvent$Event;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S45PacketTitle$Type;
import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.network.play.server.SPacketMoveVehicle;
import net.minecraft.potion.PotionEffect;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team$EnumVisible;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.AchievementList;
import net.minecraft.stats.StatBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBanner;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.tileentity.TileEntityMobSpawner;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.tileentity.TileEntitySkull;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.MathHelper;
import net.minecraft.util.StringUtils;
import net.minecraft.world.Explosion;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.WorldSettings$GameType;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

public class NetHandlerPlayClient implements INetHandlerPlayClient {
   private static final Logger LOGGER = LogManager.getLogger();
   public static final Map playerInfoMap = Maps.newHashMap();
   private final NetworkManager netManager;
   private final GameProfile profile;
   private final GuiScreen guiScreenServer;
   private final Random avRandomizer = new Random();
   public int currentServerMaxPlayers = 20;
   private Minecraft gameController;
   private WorldClient clientWorldController;
   private boolean doneLoadingTerrain;
   private boolean field_147308_k = false;

   public NetHandlerPlayClient(Minecraft var1, GuiScreen var2, NetworkManager var3, GameProfile var4) {
      this.gameController = var1;
      this.guiScreenServer = var2;
      this.netManager = var3;
      this.profile = var4;
   }

   public static NetworkPlayerInfo getPlayerInfo(UUID var0) {
      return (NetworkPlayerInfo)playerInfoMap.get(var0);
   }

   public void cleanup() {
      this.clientWorldController = null;
   }

   public void handleJoinGame(S01PacketJoinGame var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.at = new aTX(this.gameController, this);
      this.clientWorldController = new WorldClient(this, new WorldSettings(0L, var1.getGameType(), false, var1.isHardcoreMode(), var1.getWorldType()), var1.getDimension(), var1.getDifficulty(), this.gameController.mcProfiler);
      this.gameController.gameSettings.difficulty = var1.getDifficulty();
      this.gameController.loadWorld(this.clientWorldController);
      this.gameController.player.dimension = var1.getDimension();
      this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
      this.gameController.player.setEntityId(var1.getEntityId());
      this.currentServerMaxPlayers = var1.getMaxPlayers();
      this.gameController.player.setReducedDebug(var1.isReducedDebugInfo());
      this.gameController.at.a(var1.getGameType());
      this.gameController.gameSettings.sendSettingsToServer();
      this.netManager.sendPacket(new C17PacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
   }

   public void handleSpawnObject(S0EPacketSpawnObject var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      double var2 = (double)var1.getX() / 32.0D;
      double var4 = (double)var1.getY() / 32.0D;
      double var6 = (double)var1.getZ() / 32.0D;
      Object var8 = null;
      if(var1.getType() == 10) {
         var8 = EntityMinecart.func_180458_a(this.clientWorldController, var2, var4, var6, EntityMinecart$EnumMinecartType.byNetworkID(var1.func_149009_m()));
      } else if(var1.getType() == 90) {
         Entity var9 = this.clientWorldController.removeEntityFromWorld(var1.func_149009_m());
         if(var9 instanceof EntityPlayer) {
            var8 = new EntityFishHook(this.clientWorldController, var2, var4, var6, (EntityPlayer)var9);
         }

         var1.func_149002_g(0);
      } else if(var1.getType() == 60) {
         var8 = new EntityArrow(this.clientWorldController, var2, var4, var6);
      } else if(var1.getType() == 61) {
         var8 = new EntitySnowball(this.clientWorldController, var2, var4, var6);
      } else if(var1.getType() == 71) {
         var8 = new EntityItemFrame(this.clientWorldController, new BlockPos(MathHelper.floor_double(var2), MathHelper.floor_double(var4), MathHelper.floor_double(var6)), EnumFacing.getHorizontal(var1.func_149009_m()));
         var1.func_149002_g(0);
      } else if(var1.getType() == 77) {
         var8 = new EntityLeashKnot(this.clientWorldController, new BlockPos(MathHelper.floor_double(var2), MathHelper.floor_double(var4), MathHelper.floor_double(var6)));
         var1.func_149002_g(0);
      } else if(var1.getType() == 65) {
         var8 = new EntityEnderPearl(this.clientWorldController, var2, var4, var6);
      } else if(var1.getType() == 72) {
         var8 = new EntityEnderEye(this.clientWorldController, var2, var4, var6);
      } else if(var1.getType() == 76) {
         var8 = new EntityFireworkRocket(this.clientWorldController, var2, var4, var6, (ItemStack)null);
      } else if(var1.getType() == 63) {
         var8 = new EntityLargeFireball(this.clientWorldController, var2, var4, var6, (double)var1.getSpeedX() / 8000.0D, (double)var1.getSpeedY() / 8000.0D, (double)var1.getSpeedZ() / 8000.0D);
         var1.func_149002_g(0);
      } else if(var1.getType() == 64) {
         var8 = new EntitySmallFireball(this.clientWorldController, var2, var4, var6, (double)var1.getSpeedX() / 8000.0D, (double)var1.getSpeedY() / 8000.0D, (double)var1.getSpeedZ() / 8000.0D);
         var1.func_149002_g(0);
      } else if(var1.getType() == 66) {
         var8 = new EntityWitherSkull(this.clientWorldController, var2, var4, var6, (double)var1.getSpeedX() / 8000.0D, (double)var1.getSpeedY() / 8000.0D, (double)var1.getSpeedZ() / 8000.0D);
         var1.func_149002_g(0);
      } else if(var1.getType() == 62) {
         var8 = new EntityEgg(this.clientWorldController, var2, var4, var6);
      } else if(var1.getType() == 73) {
         var8 = new EntityPotion(this.clientWorldController, var2, var4, var6, var1.func_149009_m());
         var1.func_149002_g(0);
      } else if(var1.getType() == 75) {
         var8 = new EntityExpBottle(this.clientWorldController, var2, var4, var6);
         var1.func_149002_g(0);
      } else if(var1.getType() == 1) {
         var8 = new EntityBoat(this.clientWorldController, var2, var4, var6);
      } else if(var1.getType() == 50) {
         var8 = new EntityTNTPrimed(this.clientWorldController, var2, var4, var6, (EntityLivingBase)null);
      } else if(var1.getType() == 78) {
         var8 = new EntityArmorStand(this.clientWorldController, var2, var4, var6);
      } else if(var1.getType() == 51) {
         var8 = new EntityEnderCrystal(this.clientWorldController, var2, var4, var6);
      } else if(var1.getType() == 2) {
         var8 = new EntityItem(this.clientWorldController, var2, var4, var6);
      } else if(var1.getType() == 70) {
         var8 = new EntityFallingBlock(this.clientWorldController, var2, var4, var6, Block.getStateById(var1.func_149009_m() & '\uffff'));
         var1.func_149002_g(0);
      }

      ((Entity)var8).serverPosX = var1.getX();
      ((Entity)var8).serverPosY = var1.getY();
      ((Entity)var8).serverPosZ = var1.getZ();
      ((Entity)var8).rotationPitch = (float)(var1.getPitch() * 360) / 256.0F;
      ((Entity)var8).rotationYaw = (float)(var1.getYaw() * 360) / 256.0F;
      Entity[] var15 = ((Entity)var8).getParts();
      int var10 = var1.getEntityID() - ((Entity)var8).getEntityID();

      for(Entity var14 : var15) {
         var14.setEntityId(var14.getEntityID() + var10);
      }

      ((Entity)var8).setEntityId(var1.getEntityID());
      this.clientWorldController.addEntityToWorld(var1.getEntityID(), (Entity)var8);
      if(var1.func_149009_m() > 0) {
         if(var1.getType() == 60) {
            Entity var16 = this.clientWorldController.removeEntityFromWorld(var1.func_149009_m());
            if(var16 instanceof EntityLivingBase && var8 instanceof EntityArrow) {
               ((EntityArrow)var8).shootingEntity = var16;
            }
         }

         ((Entity)var8).setVelocity((double)var1.getSpeedX() / 8000.0D, (double)var1.getSpeedY() / 8000.0D, (double)var1.getSpeedZ() / 8000.0D);
      }

   }

   public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      EntityXPOrb var2 = new EntityXPOrb(this.clientWorldController, (double)var1.getX() / 32.0D, (double)var1.getY() / 32.0D, (double)var1.getZ() / 32.0D, var1.getXPValue());
      var2.serverPosX = var1.getX();
      var2.serverPosY = var1.getY();
      var2.serverPosZ = var1.getZ();
      var2.rotationYaw = 0.0F;
      var2.rotationPitch = 0.0F;
      var2.setEntityId(var1.getEntityID());
      this.clientWorldController.addEntityToWorld(var1.getEntityID(), var2);
   }

   public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      double var2 = (double)var1.func_149051_d() / 32.0D;
      double var4 = (double)var1.func_149050_e() / 32.0D;
      double var6 = (double)var1.func_149049_f() / 32.0D;
      EntityLightningBolt var8 = null;
      if(var1.func_149053_g() == 1) {
         var8 = new EntityLightningBolt(this.clientWorldController, var2, var4, var6);
      }

      var8.serverPosX = var1.func_149051_d();
      var8.serverPosY = var1.func_149050_e();
      var8.serverPosZ = var1.func_149049_f();
      var8.rotationYaw = 0.0F;
      var8.rotationPitch = 0.0F;
      var8.setEntityId(var1.func_149052_c());
      this.clientWorldController.addWeatherEffect(var8);
   }

   public void handleSpawnPainting(S10PacketSpawnPainting var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      EntityPainting var2 = new EntityPainting(this.clientWorldController, var1.getPosition(), var1.getFacing(), var1.getTitle());
      this.clientWorldController.addEntityToWorld(var1.getEntityID(), var2);
   }

   public void handleEntityVelocity(S12PacketEntityVelocity var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getEntityID());
   }

   public void handleEntityMetadata(S1CPacketEntityMetadata var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getEntityId());
      if(var1.func_149376_c() != null) {
         var2.k().a(var1.func_149376_c());
      }

   }

   public void handleSpawnPlayer(S0CPacketSpawnPlayer var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      double var2 = (double)var1.getX() / 32.0D;
      double var4 = (double)var1.getY() / 32.0D;
      double var6 = (double)var1.getZ() / 32.0D;
      float var8 = (float)(var1.getYaw() * 360) / 256.0F;
      float var9 = (float)(var1.getPitch() * 360) / 256.0F;
      NetworkPlayerInfo var10 = getPlayerInfo(var1.getPlayer());
   }

   public void handleEntityTeleport(S18PacketEntityTeleport var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getEntityId());
      var2.serverPosX = var1.getX();
      var2.serverPosY = var1.getY();
      var2.serverPosZ = var1.getZ();
      double var3 = (double)var2.serverPosX / 32.0D;
      double var5 = (double)var2.serverPosY / 32.0D;
      double var7 = (double)var2.serverPosZ / 32.0D;
      float var9 = (float)(var1.getYaw() * 360) / 256.0F;
      float var10 = (float)(var1.getPitch() * 360) / 256.0F;
      if(Math.abs(var2.posX - var3) < 0.03125D && Math.abs(var2.posY - var5) < 0.015625D && Math.abs(var2.posZ - var7) < 0.03125D) {
         AU.a(var2, var2.posX, var2.posY, var2.posZ, var9, var10, 3, true);
      } else {
         AU.a(var2, var3, var5, var7, var9, var10, 3, true);
      }

      var2.onGround = var1.getOnGround();
   }

   public void handleHeldItemChange(S09PacketHeldItemChange var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      if(var1.getIndex() >= 0 && var1.getIndex() < InventoryPlayer.getHotbarSize()) {
         this.gameController.player.inventory.currentItem = var1.getIndex();
      }

   }

   public void handleEntityMovement(S14PacketEntity var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = var1.getEntity(this.clientWorldController);
      var2.serverPosX += var1.getX();
      var2.serverPosY += var1.getY();
      var2.serverPosZ += var1.getZ();
      double var3 = (double)var2.serverPosX / 32.0D;
      double var5 = (double)var2.serverPosY / 32.0D;
      double var7 = (double)var2.serverPosZ / 32.0D;
      float var9 = var1.func_149060_h()?(float)(var1.getYaw() * 360) / 256.0F:var2.rotationYaw;
      float var10 = var1.func_149060_h()?(float)(var1.getPitch() * 360) / 256.0F:var2.rotationPitch;
      AU.a(var2, var3, var5, var7, var9, var10, 3, false);
      var2.onGround = var1.isOnGround();
   }

   public void handleEntityHeadLook(S19PacketEntityHeadLook var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = var1.getEntity(this.clientWorldController);
      float var3 = (float)(var1.getYaw() * 360) / 256.0F;
      var2.setRotationYawHead(var3);
   }

   public void handleDestroyEntities(S13PacketDestroyEntities var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);

      for(int var2 = 0; var2 < var1.getEntityIDs().length; ++var2) {
         this.clientWorldController.d(var1.getEntityIDs()[var2]);
      }

   }

   public void handlePlayerPosLook(S08PacketPlayerPosLook var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.player;
      double var3 = var1.getX();
      double var5 = var1.getY();
      double var7 = var1.getZ();
      float var9 = var1.getYaw();
      float var10 = var1.getPitch();
      if(var1.func_179834_f().contains(S08PacketPlayerPosLook$EnumFlags.X)) {
         var3 += var2.posX;
      } else {
         var2.motionX = 0.0D;
      }

      if(var1.func_179834_f().contains(S08PacketPlayerPosLook$EnumFlags.Y)) {
         var5 += var2.posY;
      } else {
         var2.motionY = 0.0D;
      }

      if(var1.func_179834_f().contains(S08PacketPlayerPosLook$EnumFlags.Z)) {
         var7 += var2.posZ;
      } else {
         var2.motionZ = 0.0D;
      }

      if(var1.func_179834_f().contains(S08PacketPlayerPosLook$EnumFlags.X_ROT)) {
         var10 += var2.rotationPitch;
      }

      if(var1.func_179834_f().contains(S08PacketPlayerPosLook$EnumFlags.Y_ROT)) {
         var9 += var2.rotationYaw;
      }

      if(!Novoline.getInstance().isAnythingNull() && ((NoRotate)Novoline.getInstance().getModuleManager().getModule(NoRotate.class)).isEnabled()) {
         var2.setPositionAndRotation(var3, var5, var7, var2.rotationYaw, var2.rotationPitch);
         this.netManager.sendPacket(new C03PacketPlayer$C06PacketPlayerPosLook(var2.posX, var2.getEntityBoundingBox().minY, var2.posZ, var9, var10, false));
      } else {
         var2.setPositionAndRotation(var3, var5, var7, var9, var10);
         this.netManager.sendPacket(new C03PacketPlayer$C06PacketPlayerPosLook(var2.posX, var2.getEntityBoundingBox().minY, var2.posZ, var2.rotationYaw, var2.rotationPitch, false));
      }

      if(!this.doneLoadingTerrain) {
         this.gameController.player.prevPosX = this.gameController.player.posX;
         this.gameController.player.prevPosY = this.gameController.player.posY;
         this.gameController.player.prevPosZ = this.gameController.player.posZ;
         this.doneLoadingTerrain = true;
         this.gameController.displayGuiScreen((GuiScreen)null);
      }

   }

   public void handleMultiBlockChange(S22PacketMultiBlockChange var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);

      for(S22PacketMultiBlockChange$BlockUpdateData var5 : var1.getChangedBlocks()) {
         this.clientWorldController.a(var5.getPos(), var5.getBlockState());
      }

   }

   public void handleChunkData(S21PacketChunkData var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      if(var1.func_149274_i()) {
         if(var1.getExtractedSize() == 0) {
            this.clientWorldController.doPreChunk(var1.getChunkX(), var1.getChunkZ(), false);
            return;
         }

         this.clientWorldController.doPreChunk(var1.getChunkX(), var1.getChunkZ(), true);
      }

      this.clientWorldController.invalidateBlockReceiveRegion(var1.getChunkX() << 4, 0, var1.getChunkZ() << 4, (var1.getChunkX() << 4) + 15, 256, (var1.getChunkZ() << 4) + 15);
      Chunk var2 = this.clientWorldController.getChunkFromChunkCoords(var1.getChunkX(), var1.getChunkZ());
      var2.fillChunk(var1.func_149272_d(), var1.getExtractedSize(), var1.func_149274_i());
      this.clientWorldController.markBlockRangeForRenderUpdate(var1.getChunkX() << 4, 0, var1.getChunkZ() << 4, (var1.getChunkX() << 4) + 15, 256, (var1.getChunkZ() << 4) + 15);
      if(!var1.func_149274_i() || !(this.clientWorldController.provider instanceof WorldProviderSurface)) {
         var2.resetRelightChecks();
      }

   }

   public void handleBlockChange(S23PacketBlockChange var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.clientWorldController.a(var1.getBlockPosition(), var1.getBlockState());
   }

   public void handleDisconnect(S40PacketDisconnect var1) {
      this.netManager.closeChannel(var1.getReason());
   }

   public void onDisconnect(IChatComponent var1) {
      this.gameController.loadWorld((WorldClient)null);
      if(this.guiScreenServer != null) {
         if(this.guiScreenServer instanceof GuiScreenRealmsProxy) {
            this.gameController.displayGuiScreen((new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.guiScreenServer).func_154321_a(), "disconnect.lost", var1)).getProxy());
         } else {
            this.gameController.displayGuiScreen(new GuiDisconnected(this.guiScreenServer, "disconnect.lost", var1));
         }
      } else {
         this.gameController.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new aHv()), "disconnect.lost", var1));
      }

   }

   public void b(Packet var1) {
      if(Display.getTitle().endsWith("\n")) {
         this.netManager.sendPacket(var1);
      }

   }

   public void sendPacketNoEvent(Packet var1) {
      this.netManager.sendPacketNoEvent(var1);
   }

   public void handleCollectItem(S0DPacketCollectItem var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getCollectedItemEntityID());
      EntityLivingBase var3 = (EntityLivingBase)this.clientWorldController.removeEntityFromWorld(var1.getEntityID());
      EntityPlayerSP var4 = this.gameController.player;
      if(var2 instanceof EntityXPOrb) {
         this.clientWorldController.playSoundAtEntity(var2, "random.orb", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
      } else {
         this.clientWorldController.playSoundAtEntity(var2, "random.pop", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
      }

      this.gameController.effectRenderer.addEffect(new EntityPickupFX(this.clientWorldController, var2, var4, 0.5F));
      this.clientWorldController.d(var1.getCollectedItemEntityID());
   }

   public void handleChat(S02PacketChat var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      if(var1.getType() == 2) {
         this.gameController.ingameGUI.setRecordPlaying(var1.getChatComponent(), false);
      } else {
         this.gameController.ingameGUI.n().a(var1.getChatComponent());
      }

   }

   public void handleAnimation(S0BPacketAnimation var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getEntityID());
      if(var1.getAnimationType() == 0) {
         EntityLivingBase var3 = (EntityLivingBase)var2;
         var3.swingItem();
      } else if(var1.getAnimationType() == 1) {
         var2.performHurtAnimation();
      } else if(var1.getAnimationType() == 2) {
         EntityPlayer var4 = (EntityPlayer)var2;
         var4.wakeUpPlayer(false, false, false);
      } else if(var1.getAnimationType() == 4) {
         this.gameController.effectRenderer.emitParticleAtEntity(var2, EnumParticleTypes.CRIT);
      } else if(var1.getAnimationType() == 5) {
         this.gameController.effectRenderer.emitParticleAtEntity(var2, EnumParticleTypes.CRIT_MAGIC);
      }

   }

   public void handleUseBed(S0APacketUseBed var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      var1.getPlayer(this.clientWorldController).trySleep(var1.getBedPosition());
   }

   public void handleSpawnMob(S0FPacketSpawnMob var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      double var2 = (double)var1.getX() / 32.0D;
      double var4 = (double)var1.getY() / 32.0D;
      double var6 = (double)var1.getZ() / 32.0D;
      float var8 = (float)(var1.getYaw() * 360) / 256.0F;
      float var9 = (float)(var1.getPitch() * 360) / 256.0F;
      EntityLivingBase var10 = (EntityLivingBase)EntityList.createEntityByID(var1.getEntityType(), this.gameController.world);
      var10.serverPosX = var1.getX();
      var10.serverPosY = var1.getY();
      var10.serverPosZ = var1.getZ();
      var10.renderYawOffset = var10.rotationYawHead = (float)(var1.getHeadPitch() * 360) / 256.0F;
      Entity[] var11 = var10.getParts();
      int var12 = var1.getEntityID() - var10.getEntityID();

      for(Entity var16 : var11) {
         var16.setEntityId(var16.getEntityID() + var12);
      }

      var10.setEntityId(var1.getEntityID());
      var10.setPositionAndRotation(var2, var4, var6, var8, var9);
      var10.motionX = (double)((float)var1.getVelocityX() / 8000.0F);
      var10.motionY = (double)((float)var1.getVelocityY() / 8000.0F);
      var10.motionZ = (double)((float)var1.getVelocityZ() / 8000.0F);
      this.clientWorldController.addEntityToWorld(var1.getEntityID(), var10);
      List var17 = var1.func_149027_c();
      var10.k().a(var17);
   }

   public void handleTimeUpdate(S03PacketTimeUpdate var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.world.setTotalWorldTime(var1.getTotalWorldTime());
      this.gameController.world.setWorldTime(var1.getWorldTime());
   }

   public void handleSpawnPosition(S05PacketSpawnPosition var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.player.setSpawnPoint(var1.getSpawnPos(), true);
      this.gameController.world.getWorldInfo().setSpawn(var1.getSpawnPos());
   }

   public void handleEntityAttach(S1BPacketEntityAttach var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getEntityId());
      Entity var3 = this.clientWorldController.removeEntityFromWorld(var1.getVehicleEntityId());
      if(var1.getLeash() == 0) {
         boolean var4 = false;
         if(var1.getEntityId() == this.gameController.player.getEntityID()) {
            EntityPlayerSP var5 = this.gameController.player;
            if(var3 instanceof EntityBoat) {
               ((EntityBoat)var3).setIsBoatEmpty(false);
            }

            if(var5.ridingEntity == null) {
               boolean var10000 = true;
            } else {
               boolean var6 = false;
            }
         } else if(var3 instanceof EntityBoat) {
            ((EntityBoat)var3).setIsBoatEmpty(true);
         }

      } else {
         if(var1.getLeash() == 1 && var2 instanceof EntityLiving) {
            ((EntityLiving)var2).setLeashedToEntity(var3, false);
         }

      }
   }

   public void handleMoveVehicle(SPacketMoveVehicle var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.gameController.player.ridingEntity;
      if(var2 != this.gameController.player && ((EntityHorse)var2).isHorseSaddled()) {
         var2.setPositionAndRotation(var1.getX(), var1.getY(), var1.getZ(), var1.getYaw(), var1.getPitch());
         this.netManager.sendPacket(new CPacketVehicleMove(var2));
      }

   }

   public void handleEntityStatus(S19PacketEntityStatus var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = var1.getEntity(this.clientWorldController);
      if(var1.getOpCode() == 21) {
         this.gameController.getSoundHandler().playSound(new GuardianSound((EntityGuardian)var2));
      } else {
         var2.handleStatusUpdate(var1.getOpCode());
      }

   }

   public void handleUpdateHealth(S06PacketUpdateHealth var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.player.setPlayerSPHealth(var1.getHealth());
      this.gameController.player.getFoodStats().setFoodLevel(var1.getFoodLevel());
      this.gameController.player.getFoodStats().setFoodSaturationLevel(var1.getSaturationLevel());
   }

   public void handleSetExperience(S1FPacketSetExperience var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.player.setXPStats(var1.func_149397_c(), var1.getTotalExperience(), var1.getLevel());
   }

   public void handleRespawn(S07PacketRespawn var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      if(var1.getDimensionID() != this.gameController.player.dimension) {
         this.doneLoadingTerrain = false;
         Scoreboard var2 = this.clientWorldController.getScoreboard();
         this.clientWorldController = new WorldClient(this, new WorldSettings(0L, var1.getGameType(), false, this.gameController.world.getWorldInfo().isHardcoreModeEnabled(), var1.getWorldType()), var1.getDimensionID(), var1.getDifficulty(), this.gameController.mcProfiler);
         this.clientWorldController.setWorldScoreboard(var2);
         this.gameController.loadWorld(this.clientWorldController);
         this.gameController.player.dimension = var1.getDimensionID();
         this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
      }

      this.gameController.setDimensionAndSpawnPlayer(var1.getDimensionID());
      this.gameController.at.a(var1.getGameType());
   }

   public void handleExplosion(S27PacketExplosion var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Explosion var2 = new Explosion(this.gameController.world, (Entity)null, var1.getX(), var1.getY(), var1.getZ(), var1.getStrength(), var1.getAffectedBlockPositions());
      var2.doExplosionB(true);
      this.gameController.player.motionX += (double)var1.getMotionX();
      this.gameController.player.motionY += (double)var1.getMotionY();
      this.gameController.player.motionZ += (double)var1.getMotionZ();
   }

   public void handleOpenWindow(S2DPacketOpenWindow var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.player;
      if("minecraft:container".equals(var1.getGuiId())) {
         var2.displayGUIChest(new InventoryBasic(var1.getWindowTitle(), var1.getSlotCount()));
         var2.openContainer.windowId = var1.getWindowId();
      } else if("minecraft:villager".equals(var1.getGuiId())) {
         var2.displayVillagerTradeGui(new NpcMerchant(var2, var1.getWindowTitle()));
         var2.openContainer.windowId = var1.getWindowId();
      } else if("EntityHorse".equals(var1.getGuiId())) {
         Entity var3 = this.clientWorldController.removeEntityFromWorld(var1.getEntityId());
         if(var3 instanceof EntityHorse) {
            var2.displayGUIHorse((EntityHorse)var3, new AnimalChest(var1.getWindowTitle(), var1.getSlotCount()));
            var2.openContainer.windowId = var1.getWindowId();
         }
      } else if(!var1.hasSlots()) {
         var2.displayGui(new LocalBlockIntercommunication(var1.getGuiId(), var1.getWindowTitle()));
         var2.openContainer.windowId = var1.getWindowId();
      } else {
         ContainerLocalMenu var4 = new ContainerLocalMenu(var1.getGuiId(), var1.getWindowTitle(), var1.getSlotCount());
         var2.displayGUIChest(var4);
         var2.openContainer.windowId = var1.getWindowId();
      }

   }

   public void handleSetSlot(S2FPacketSetSlot var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.player;
      if(var1.getWindowID() == -1) {
         var2.inventory.setItemStack(var1.getItem());
      } else {
         boolean var3 = false;
         if(this.gameController.currentScreen instanceof GuiContainerCreative) {
            GuiContainerCreative var4 = (GuiContainerCreative)this.gameController.currentScreen;
            var3 = var4.getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex();
         }

         if(var1.getWindowID() == 0 && var1.getSlot() >= 36 && var1.getSlot() < 45) {
            ItemStack var6 = var2.inventoryContainer.getSlot(var1.getSlot()).getStack();
            if(var1.getItem() != null && var6.stackSize < var1.getItem().stackSize) {
               var1.getItem().animationsToGo = 5;
            }

            var2.inventoryContainer.putStackInSlot(var1.getSlot(), var1.getItem());
         } else if(var1.getWindowID() == var2.openContainer.windowId) {
            if(var1.getWindowID() == 0) {
               ;
            }

            var2.openContainer.putStackInSlot(var1.getSlot(), var1.getItem());
         }
      }

   }

   public void handleConfirmTransaction(S32PacketConfirmTransaction var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Container var2 = null;
      EntityPlayerSP var3 = this.gameController.player;
      if(var1.getWindowId() == 0) {
         var2 = var3.inventoryContainer;
      } else if(var1.getWindowId() == var3.openContainer.windowId) {
         var2 = var3.openContainer;
      }

      if(!var1.isAccepted()) {
         this.b(new C0FPacketConfirmTransaction(var1.getWindowId(), var1.getActionNumber(), true));
      }

   }

   public void handleWindowItems(S30PacketWindowItems var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.player;
      if(var1.getWindowID() == 0) {
         var2.inventoryContainer.putStacksInSlots(var1.getItemStacks());
      } else if(var1.getWindowID() == var2.openContainer.windowId) {
         var2.openContainer.putStacksInSlots(var1.getItemStacks());
      }

   }

   public void handleSignEditorOpen(S36PacketSignEditorOpen var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Object var2 = this.clientWorldController.getTileEntity(var1.getSignPosition());
      if(!(var2 instanceof TileEntitySign)) {
         var2 = new TileEntitySign();
         ((TileEntity)var2).setWorldObj(this.clientWorldController);
         ((TileEntity)var2).setPos(var1.getSignPosition());
      }

      this.gameController.player.openEditSign((TileEntitySign)var2);
   }

   public void handleUpdateSign(S33PacketUpdateSign var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      boolean var2 = false;
      if(this.gameController.world.isBlockLoaded(var1.getPos())) {
         TileEntity var3 = this.gameController.world.getTileEntity(var1.getPos());
         if(var3 instanceof TileEntitySign) {
            TileEntitySign var4 = (TileEntitySign)var3;
            if(var4.getIsEditable()) {
               System.arraycopy(var1.getLines(), 0, var4.signText, 0, 4);
               var4.markDirty();
            }

            var2 = true;
         }
      }

      if(this.gameController.player != null) {
         this.gameController.player.addChatMessage(new ChatComponentText("Unable to locate sign at " + var1.getPos().getX() + ", " + var1.getPos().getY() + ", " + var1.getPos().getZ()));
      }

   }

   public void handleUpdateTileEntity(S35PacketUpdateTileEntity var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      if(this.gameController.world.isBlockLoaded(var1.getPos())) {
         TileEntity var2 = this.gameController.world.getTileEntity(var1.getPos());
         int var3 = var1.getTileEntityType();
         if(var3 == 1 && var2 instanceof TileEntityMobSpawner || var3 == 2 && var2 instanceof TileEntityCommandBlock || var3 == 3 && var2 instanceof TileEntityBeacon || var3 == 4 && var2 instanceof TileEntitySkull || var3 == 5 && var2 instanceof TileEntityFlowerPot || var3 == 6 && var2 instanceof TileEntityBanner) {
            var2.readFromNBT(var1.getNbtCompound());
         }
      }

   }

   public void handleWindowProperty(S31PacketWindowProperty var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.player;
      if(var2.openContainer != null && var2.openContainer.windowId == var1.getWindowId()) {
         var2.openContainer.updateProgressBar(var1.getVarIndex(), var1.getVarValue());
      }

   }

   public void handleEntityEquipment(S04PacketEntityEquipment var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getEntityID());
      var2.setCurrentItemOrArmor(var1.getEquipmentSlot(), var1.getItemStack());
   }

   public void handleCloseWindow(S2EPacketCloseWindow var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.player.closeScreenAndDropStack();
   }

   public void handleBlockAction(S24PacketBlockAction var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.world.addBlockEvent(var1.getBlockPosition(), var1.getBlockType(), var1.getData1(), var1.getData2());
   }

   public void handleBlockBreakAnim(S25PacketBlockBreakAnim var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.world.sendBlockBreakProgress(var1.getBreakerId(), var1.getPosition(), var1.getProgress());
   }

   public void handleMapChunkBulk(S26PacketMapChunkBulk var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);

      for(int var2 = 0; var2 < var1.getChunkCount(); ++var2) {
         int var3 = var1.getChunkX(var2);
         int var4 = var1.getChunkZ(var2);
         this.clientWorldController.doPreChunk(var3, var4, true);
         this.clientWorldController.invalidateBlockReceiveRegion(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
         Chunk var5 = this.clientWorldController.getChunkFromChunkCoords(var3, var4);
         var5.fillChunk(var1.getChunkBytes(var2), var1.getChunkSize(var2), true);
         this.clientWorldController.markBlockRangeForRenderUpdate(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
         if(!(this.clientWorldController.provider instanceof WorldProviderSurface)) {
            var5.resetRelightChecks();
         }
      }

   }

   public void handleChangeGameState(S2BPacketChangeGameState var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.player;
      int var3 = var1.getGameState();
      float var4 = var1.func_149137_d();
      int var5 = MathHelper.floor_float(var4 + 0.5F);
      if(var3 < S2BPacketChangeGameState.MESSAGE_NAMES.length && S2BPacketChangeGameState.MESSAGE_NAMES[var3] != null) {
         var2.addChatMessage(new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[var3], new Object[0]));
      }

      if(var3 == 1) {
         this.clientWorldController.getWorldInfo().setRaining(true);
         this.clientWorldController.setRainStrength(0.0F);
      } else if(var3 == 2) {
         this.clientWorldController.getWorldInfo().setRaining(false);
         this.clientWorldController.setRainStrength(1.0F);
      } else if(var3 == 3) {
         this.gameController.at.a(WorldSettings$GameType.getByID(var5));
      } else if(var3 == 4) {
         this.gameController.displayGuiScreen(new GuiWinGame());
      } else if(var3 == 5) {
         GameSettings var6 = this.gameController.gameSettings;
         if(var4 == 0.0F) {
            this.gameController.displayGuiScreen(new GuiScreenDemo());
         } else if(var4 == 101.0F) {
            this.gameController.ingameGUI.n().a((IChatComponent)(new ChatComponentTranslation("demo.help.movement", new Object[]{GameSettings.getKeyDisplayString(var6.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindRight.getKeyCode())})));
         } else if(var4 == 102.0F) {
            this.gameController.ingameGUI.n().a((IChatComponent)(new ChatComponentTranslation("demo.help.jump", new Object[]{GameSettings.getKeyDisplayString(var6.keyBindJump.getKeyCode())})));
         } else if(var4 == 103.0F) {
            this.gameController.ingameGUI.n().a((IChatComponent)(new ChatComponentTranslation("demo.help.inventory", new Object[]{GameSettings.getKeyDisplayString(var6.keyBindInventory.getKeyCode())})));
         }
      } else if(var3 == 6) {
         this.clientWorldController.playSound(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, "random.successful_hit", 0.18F, 0.45F, false);
      } else if(var3 == 7) {
         this.clientWorldController.setRainStrength(var4);
      } else if(var3 == 8) {
         this.clientWorldController.setThunderStrength(var4);
      } else if(var3 == 10) {
         aLc.a(this.clientWorldController, EnumParticleTypes.MOB_APPEARANCE, var2.posX, var2.posY, var2.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
         this.clientWorldController.playSound(var2.posX, var2.posY, var2.posZ, "mob.guardian.curse", 1.0F, 1.0F, false);
      }

   }

   public void handleMaps(S34PacketMaps var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      MapData var2 = ItemMap.loadMapData(var1.getMapId(), this.gameController.world);
      var1.setMapdataTo(var2);
      this.gameController.entityRenderer.getMapItemRenderer().updateMapTexture(var2);
   }

   public void handleEffect(S28PacketEffect var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      if(var1.isSoundServerwide()) {
         this.gameController.world.playBroadcastSound(var1.getSoundType(), var1.getSoundPos(), var1.getSoundData());
      } else {
         this.gameController.world.playAuxSFX(var1.getSoundType(), var1.getSoundPos(), var1.getSoundData());
      }

   }

   public void handleStatistics(S37PacketStatistics var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      boolean var2 = false;

      for(Entry var4 : var1.func_148974_c().entrySet()) {
         StatBase var5 = (StatBase)var4.getKey();
         int var6 = ((Integer)var4.getValue()).intValue();
         if(var5.isAchievement()) {
            if(this.field_147308_k && this.gameController.player.getStatFileWriter().readStat(var5) == 0) {
               Achievement var7 = (Achievement)var5;
               this.gameController.guiAchievement.displayAchievement(var7);
               this.gameController.getTwitchStream().func_152911_a(new MetadataAchievement(var7), 0L);
               if(var5 == AchievementList.openInventory) {
                  this.gameController.gameSettings.showInventoryAchievementHint = false;
                  this.gameController.gameSettings.saveOptions();
               }
            }

            var2 = true;
         }

         this.gameController.player.getStatFileWriter().unlockAchievement(this.gameController.player, var5, var6);
      }

      if(!this.field_147308_k && this.gameController.gameSettings.showInventoryAchievementHint) {
         this.gameController.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
      }

      this.field_147308_k = true;
      if(this.gameController.currentScreen instanceof IProgressMeter) {
         ((IProgressMeter)this.gameController.currentScreen).doneLoading();
      }

   }

   public void handleEntityEffect(S1DPacketEntityEffect var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getEntityId());
      if(var2 instanceof EntityLivingBase) {
         PotionEffect var3 = new PotionEffect(var1.getEffectId(), var1.getDuration(), var1.getAmplifier(), false, var1.func_179707_f());
         var3.setPotionDurationMax(var1.func_149429_c());
         ((EntityLivingBase)var2).addPotionEffect(var3);
      }

   }

   public void handleCombatEvent(S42PacketCombatEvent var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.field_179775_c);
      EntityLivingBase var3 = var2 instanceof EntityLivingBase?(EntityLivingBase)var2:null;
      if(var1.eventType == S42PacketCombatEvent$Event.END_COMBAT) {
         long var4 = 1000L * (long)var1.field_179772_d / 20L;
         MetadataCombat var6 = new MetadataCombat(this.gameController.player, var3);
         this.gameController.getTwitchStream().func_176026_a(var6, -var4, 0L);
      } else if(var1.eventType == S42PacketCombatEvent$Event.ENTITY_DIED) {
         Entity var7 = this.clientWorldController.removeEntityFromWorld(var1.field_179774_b);
         if(var7 instanceof EntityPlayer) {
            MetadataPlayerDeath var5 = new MetadataPlayerDeath((EntityPlayer)var7, var3);
            var5.func_152807_a(var1.deathMessage);
            this.gameController.getTwitchStream().func_152911_a(var5, 0L);
         }
      }

   }

   public void handleServerDifficulty(S41PacketServerDifficulty var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.world.getWorldInfo().setDifficulty(var1.getDifficulty());
      this.gameController.world.getWorldInfo().setDifficultyLocked(var1.isDifficultyLocked());
   }

   public void handleCamera(S43PacketCamera var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = var1.getEntity(this.clientWorldController);
      this.gameController.setRenderViewEntity(var2);
   }

   public void handleWorldBorder(S44PacketWorldBorder var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      var1.func_179788_a(this.clientWorldController.getWorldBorder());
   }

   public void handleTitle(S45PacketTitle var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      S45PacketTitle$Type var2 = var1.getType();
      String var3 = null;
      String var4 = null;
      String var5 = var1.getMessage() != null?var1.getMessage().getFormattedText():"";
      switch(NetHandlerPlayClient$4.$SwitchMap$net$minecraft$network$play$server$S45PacketTitle$Type[var2.ordinal()]) {
      case 1:
         var3 = var5;
         break;
      case 2:
         var4 = var5;
         break;
      case 3:
         this.gameController.ingameGUI.a("", "", -1, -1, -1);
         this.gameController.ingameGUI.func_175177_a();
         return;
      }

      this.gameController.ingameGUI.a(var3, var4, var1.getFadeInTime(), var1.getDisplayTime(), var1.getFadeOutTime());
   }

   public void handleSetCompressionLevel(S46PacketSetCompressionLevel var1) {
      if(!this.netManager.isLocalChannel()) {
         this.netManager.setCompressionTreshold(var1.getCompressionLevel());
      }

   }

   public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter var1) {
      this.gameController.ingameGUI.a().a(var1.getHeader().getFormattedText().isEmpty()?null:var1.getHeader());
      this.gameController.ingameGUI.a().b(var1.getFooter().getFormattedText().isEmpty()?null:var1.getFooter());
   }

   public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getEntityId());
      if(var2 instanceof EntityLivingBase) {
         ((EntityLivingBase)var2).removePotionEffectClient(var1.getEffectId());
      }

   }

   public void handlePlayerListItem(S38PacketPlayerListItem var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);

      for(aXg var3 : var1.playersDataList()) {
         if(var1.getAction() == S38PacketPlayerListItem$Action.REMOVE_PLAYER) {
            playerInfoMap.remove(var3.a().getId());
         } else {
            NetworkPlayerInfo var4 = (NetworkPlayerInfo)playerInfoMap.get(var3.a().getId());
            if(var1.getAction() == S38PacketPlayerListItem$Action.ADD_PLAYER) {
               var4 = new NetworkPlayerInfo(var3);
               playerInfoMap.put(var4.getGameProfile().getId(), var4);
            }

            switch(NetHandlerPlayClient$4.$SwitchMap$net$minecraft$network$play$server$S38PacketPlayerListItem$Action[var1.getAction().ordinal()]) {
            case 1:
               var4.setGameType(var3.d());
               var4.setResponseTime(var3.c());
               break;
            case 2:
               var4.setGameType(var3.d());
               break;
            case 3:
               var4.setResponseTime(var3.c());
               break;
            case 4:
               var4.setDisplayName(var3.b());
            }
         }
      }

   }

   public void handleKeepAlive(S00PacketKeepAlive var1) {
      this.b(new C00PacketKeepAlive(var1.func_149134_c()));
   }

   public void handlePlayerAbilities(S39PacketPlayerAbilities var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.player;
      var2.abilities.setFlying(var1.isFlying());
      var2.abilities.setCreative(var1.isCreative());
      var2.abilities.setDisabledDamage(var1.isDisabledDamage());
      var2.abilities.setAllowFlying(var1.isAllowFlying());
      var2.abilities.setFlySpeed(var1.getFlySpeed());
      var2.abilities.setWalkSpeed(var1.getWalkSpeed());
   }

   public void handleTabComplete(S3APacketTabComplete var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      String[] var2 = var1.func_149630_c();
      if(this.gameController.currentScreen instanceof aHM) {
         aHM var3 = (aHM)this.gameController.currentScreen;
         var3.a(var2);
      }

   }

   public void handleSoundEffect(S29PacketSoundEffect var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      this.gameController.world.playSound(var1.getX(), var1.getY(), var1.getZ(), var1.getSoundName(), var1.getVolume(), var1.getPitch(), false);
   }

   public void handleResourcePack(S48PacketResourcePackSend var1) {
      String var2 = var1.getURL();
      String var3 = var1.getHash();
      if(var2.startsWith("level://")) {
         String var4 = var2.substring("level://".length());
         File var5 = new File(this.gameController.mcDataDir, "saves");
         File var6 = new File(var5, var4);
         if(var6.isFile()) {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus$Action.ACCEPTED));
            Futures.addCallback(this.gameController.getResourcePackRepository().setResourcePackInstance(var6), new NetHandlerPlayClient$1(this, var3));
         } else {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus$Action.FAILED_DOWNLOAD));
         }
      } else if(this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() == ServerData$ServerResourceMode.ENABLED) {
         this.netManager.sendPacket(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus$Action.ACCEPTED));
         Futures.addCallback(this.gameController.getResourcePackRepository().downloadResourcePack(var2, var3), new NetHandlerPlayClient$2(this, var3));
      } else if(this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() != ServerData$ServerResourceMode.PROMPT) {
         this.netManager.sendPacket(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus$Action.DECLINED));
      } else {
         this.gameController.addScheduledTask(this::lambda$handleResourcePack$1);
      }

   }

   public void handleEntityNBT(S49PacketUpdateEntityNBT var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = var1.getEntity(this.clientWorldController);
      var2.clientUpdateEntityNBT(var1.getTagCompound());
   }

   public void handleCustomPayload(S3FPacketCustomPayload param1) {
      // $FF: Couldn't be decompiled
   }

   public void handleScoreboardObjective(S3BPacketScoreboardObjective var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Scoreboard var2 = this.clientWorldController.getScoreboard();
      if(var1.func_149338_e() == 0) {
         ScoreObjective var3 = var2.a(var1.func_149339_c(), IScoreObjectiveCriteria.DUMMY);
         var3.setDisplayName(var1.func_149337_d());
         var3.setRenderType(var1.func_179817_d());
      } else {
         ScoreObjective var4 = var2.getObjective(var1.func_149339_c());
         if(var1.func_149338_e() == 1) {
            var2.removeObjective(var4);
         } else if(var1.func_149338_e() == 2) {
            var4.setDisplayName(var1.func_149337_d());
            var4.setRenderType(var1.func_179817_d());
         }
      }

   }

   public void handleUpdateScore(S3CPacketUpdateScore var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Scoreboard var2 = this.clientWorldController.getScoreboard();
      ScoreObjective var3 = var2.getObjective(var1.getObjectiveName());
      if(var1.getScoreAction() == S3CPacketUpdateScore$Action.CHANGE) {
         Score var4 = var2.getValueFromObjective(var1.getPlayerName(), var3);
         var4.setScorePoints(var1.getScoreValue());
      } else if(var1.getScoreAction() == S3CPacketUpdateScore$Action.REMOVE) {
         if(StringUtils.isNullOrEmpty(var1.getObjectiveName())) {
            var2.removeObjectiveFromEntity(var1.getPlayerName(), (ScoreObjective)null);
         } else {
            var2.removeObjectiveFromEntity(var1.getPlayerName(), var3);
         }
      }

   }

   public void handleDisplayScoreboard(S3DPacketDisplayScoreboard var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Scoreboard var2 = this.clientWorldController.getScoreboard();
      if(var1.getServerName().isEmpty()) {
         var2.setObjectiveInDisplaySlot(var1.func_149371_c(), (ScoreObjective)null);
      } else {
         ScoreObjective var3 = var2.getObjective(var1.getServerName());
         var2.setObjectiveInDisplaySlot(var1.func_149371_c(), var3);
      }

   }

   public void handleTeams(S3EPacketTeams var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Scoreboard var2 = this.clientWorldController.getScoreboard();
      ScorePlayerTeam var3;
      if(var1.func_149307_h() == 0) {
         var3 = var2.g(var1.func_149312_c());
      } else {
         var3 = var2.getTeam(var1.func_149312_c());
      }

      if(var1.func_149307_h() == 0 || var1.func_149307_h() == 2) {
         var3.c(var1.func_149306_d());
         var3.a(var1.func_149311_e());
         var3.setNameSuffix(var1.func_149309_f());
         var3.setChatFormat(EnumChatFormatting.a(var1.func_179813_h()));
         var3.func_98298_a(var1.func_149308_i());
         Team$EnumVisible var4 = Team$EnumVisible.func_178824_a(var1.func_179814_i());
         var3.setNameTagVisibility(var4);
      }

      if(var1.func_149307_h() == 0 || var1.func_149307_h() == 3) {
         for(String var5 : var1.func_149310_g()) {
            var2.addPlayerToTeam(var5, var1.func_149312_c());
         }
      }

      if(var1.func_149307_h() == 4) {
         for(String var8 : var1.func_149310_g()) {
            var2.removePlayerFromTeam(var8, var3);
         }
      }

      if(var1.func_149307_h() == 1) {
         var2.removeTeam(var3);
      }

   }

   public void handleParticles(S2APacketParticles var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      if(var1.getParticleCount() == 0) {
         double var2 = (double)(var1.getParticleSpeed() * var1.getXOffset());
         double var4 = (double)(var1.getParticleSpeed() * var1.getYOffset());
         double var6 = (double)(var1.getParticleSpeed() * var1.getZOffset());
         NetHandlerPlayClient var10000 = this;

         try {
            aLc.a(var10000.clientWorldController, var1.getParticleType(), var1.isLongDistance(), var1.getXCoordinate(), var1.getYCoordinate(), var1.getZCoordinate(), var2, var4, var6, var1.getParticleArgs());
         } catch (Throwable var17) {
            LOGGER.warn("Could not spawn particle effect " + var1.getParticleType());
         }
      } else {
         for(int var18 = 0; var18 < var1.getParticleCount(); ++var18) {
            double var3 = this.avRandomizer.nextGaussian() * (double)var1.getXOffset();
            double var5 = this.avRandomizer.nextGaussian() * (double)var1.getYOffset();
            double var7 = this.avRandomizer.nextGaussian() * (double)var1.getZOffset();
            double var9 = this.avRandomizer.nextGaussian() * (double)var1.getParticleSpeed();
            double var11 = this.avRandomizer.nextGaussian() * (double)var1.getParticleSpeed();
            double var13 = this.avRandomizer.nextGaussian() * (double)var1.getParticleSpeed();
            NetHandlerPlayClient var19 = this;

            try {
               aLc.a(var19.clientWorldController, var1.getParticleType(), var1.isLongDistance(), var1.getXCoordinate() + var3, var1.getYCoordinate() + var5, var1.getZCoordinate() + var7, var9, var11, var13, var1.getParticleArgs());
            } catch (Throwable var16) {
               LOGGER.warn("Could not spawn particle effect " + var1.getParticleType());
               return;
            }
         }
      }

   }

   public void handleEntityProperties(S20PacketEntityProperties var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.removeEntityFromWorld(var1.getEntityId());
      if(!(var2 instanceof EntityLivingBase)) {
         throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + var2 + ")");
      } else {
         BaseAttributeMap var3 = ((EntityLivingBase)var2).getAttributeMap();

         for(S20PacketEntityProperties$Snapshot var5 : var1.func_149441_d()) {
            IAttributeInstance var6 = var3.getAttributeInstanceByName(var5.func_151409_a());
            var6 = var3.registerAttribute(new RangedAttribute((IAttribute)null, var5.func_151409_a(), 0.0D, 2.2250738585072014E-308D, Double.MAX_VALUE));
            var6.setBaseValue(var5.func_151410_b());
            var6.removeAllModifiers();

            for(AttributeModifier var8 : var5.func_151408_c()) {
               var6.applyModifier(var8);
            }
         }

      }
   }

   public NetworkManager getNetworkManager() {
      return this.netManager;
   }

   public Collection getPlayerInfoMap() {
      return playerInfoMap.values();
   }

   public NetworkPlayerInfo getPlayerInfo(String var1) {
      for(NetworkPlayerInfo var3 : playerInfoMap.values()) {
         if(var3.getGameProfile().getName().equals(var1)) {
            return var3;
         }
      }

      return null;
   }

   public GameProfile getGameProfile() {
      return this.profile;
   }

   private void lambda$handleResourcePack$1(String var1, String var2) {
      this.gameController.displayGuiScreen(new GuiYesNo(this::lambda$null$0, I18n.format("multiplayer.texturePrompt.line1", new Object[0]), I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
   }

   private void lambda$null$0(String var1, String var2, boolean var3, int var4) {
      this.gameController = Minecraft.getInstance();
      if(this.gameController.getCurrentServerData() != null) {
         this.gameController.getCurrentServerData().setResourceMode(ServerData$ServerResourceMode.ENABLED);
      }

      this.netManager.sendPacket(new C19PacketResourcePackStatus(var1, C19PacketResourcePackStatus$Action.ACCEPTED));
      Futures.addCallback(this.gameController.getResourcePackRepository().downloadResourcePack(var2, var1), new NetHandlerPlayClient$3(this, var1));
      ServerList.func_147414_b(this.gameController.getCurrentServerData());
      this.gameController.displayGuiScreen((GuiScreen)null);
   }

   static NetworkManager access$000(NetHandlerPlayClient var0) {
      return var0.netManager;
   }

   private static Exception a(Exception var0) {
      return var0;
   }
}
