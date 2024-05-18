/*      */ package net.minecraft.client.network;
/*      */ 
/*      */ import com.google.common.collect.Maps;
/*      */ import com.google.common.util.concurrent.FutureCallback;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import com.mojang.authlib.GameProfile;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import java.io.File;
/*      */ import java.io.IOException;
/*      */ import java.util.Collection;
/*      */ import java.util.List;
/*      */ import java.util.Map;
/*      */ import java.util.Random;
/*      */ import java.util.UUID;
/*      */ import me.eagler.Client;
/*      */ import me.eagler.event.Event;
/*      */ import me.eagler.event.EventManager;
/*      */ import me.eagler.event.events.PacketEvent;
/*      */ import me.eagler.utils.PositionHelper;
/*      */ import net.minecraft.block.Block;
/*      */ import net.minecraft.client.ClientBrandRetriever;
/*      */ import net.minecraft.client.Minecraft;
/*      */ import net.minecraft.client.audio.GuardianSound;
/*      */ import net.minecraft.client.audio.ISound;
/*      */ import net.minecraft.client.entity.EntityOtherPlayerMP;
/*      */ import net.minecraft.client.entity.EntityPlayerSP;
/*      */ import net.minecraft.client.gui.GuiChat;
/*      */ import net.minecraft.client.gui.GuiDisconnected;
/*      */ import net.minecraft.client.gui.GuiDownloadTerrain;
/*      */ import net.minecraft.client.gui.GuiMainMenu;
/*      */ import net.minecraft.client.gui.GuiMerchant;
/*      */ import net.minecraft.client.gui.GuiMultiplayer;
/*      */ import net.minecraft.client.gui.GuiScreen;
/*      */ import net.minecraft.client.gui.GuiScreenBook;
/*      */ import net.minecraft.client.gui.GuiScreenDemo;
/*      */ import net.minecraft.client.gui.GuiScreenRealmsProxy;
/*      */ import net.minecraft.client.gui.GuiWinGame;
/*      */ import net.minecraft.client.gui.GuiYesNo;
/*      */ import net.minecraft.client.gui.GuiYesNoCallback;
/*      */ import net.minecraft.client.gui.IProgressMeter;
/*      */ import net.minecraft.client.gui.inventory.GuiContainerCreative;
/*      */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*      */ import net.minecraft.client.multiplayer.ServerData;
/*      */ import net.minecraft.client.multiplayer.ServerList;
/*      */ import net.minecraft.client.multiplayer.WorldClient;
/*      */ import net.minecraft.client.particle.EntityFX;
/*      */ import net.minecraft.client.particle.EntityPickupFX;
/*      */ import net.minecraft.client.player.inventory.ContainerLocalMenu;
/*      */ import net.minecraft.client.player.inventory.LocalBlockIntercommunication;
/*      */ import net.minecraft.client.resources.I18n;
/*      */ import net.minecraft.client.settings.GameSettings;
/*      */ import net.minecraft.client.stream.Metadata;
/*      */ import net.minecraft.client.stream.MetadataAchievement;
/*      */ import net.minecraft.client.stream.MetadataCombat;
/*      */ import net.minecraft.client.stream.MetadataPlayerDeath;
/*      */ import net.minecraft.creativetab.CreativeTabs;
/*      */ import net.minecraft.entity.DataWatcher;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityLeashKnot;
/*      */ import net.minecraft.entity.EntityList;
/*      */ import net.minecraft.entity.EntityLiving;
/*      */ import net.minecraft.entity.EntityLivingBase;
/*      */ import net.minecraft.entity.IMerchant;
/*      */ import net.minecraft.entity.NpcMerchant;
/*      */ import net.minecraft.entity.ai.attributes.AttributeModifier;
/*      */ import net.minecraft.entity.ai.attributes.BaseAttributeMap;
/*      */ import net.minecraft.entity.ai.attributes.IAttribute;
/*      */ import net.minecraft.entity.ai.attributes.IAttributeInstance;
/*      */ import net.minecraft.entity.ai.attributes.RangedAttribute;
/*      */ import net.minecraft.entity.effect.EntityLightningBolt;
/*      */ import net.minecraft.entity.item.EntityArmorStand;
/*      */ import net.minecraft.entity.item.EntityBoat;
/*      */ import net.minecraft.entity.item.EntityEnderCrystal;
/*      */ import net.minecraft.entity.item.EntityEnderEye;
/*      */ import net.minecraft.entity.item.EntityEnderPearl;
/*      */ import net.minecraft.entity.item.EntityExpBottle;
/*      */ import net.minecraft.entity.item.EntityFallingBlock;
/*      */ import net.minecraft.entity.item.EntityFireworkRocket;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.item.EntityItemFrame;
/*      */ import net.minecraft.entity.item.EntityMinecart;
/*      */ import net.minecraft.entity.item.EntityPainting;
/*      */ import net.minecraft.entity.item.EntityTNTPrimed;
/*      */ import net.minecraft.entity.item.EntityXPOrb;
/*      */ import net.minecraft.entity.monster.EntityGuardian;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.entity.projectile.EntityArrow;
/*      */ import net.minecraft.entity.projectile.EntityEgg;
/*      */ import net.minecraft.entity.projectile.EntityFishHook;
/*      */ import net.minecraft.entity.projectile.EntityLargeFireball;
/*      */ import net.minecraft.entity.projectile.EntityPotion;
/*      */ import net.minecraft.entity.projectile.EntitySmallFireball;
/*      */ import net.minecraft.entity.projectile.EntitySnowball;
/*      */ import net.minecraft.entity.projectile.EntityWitherSkull;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.AnimalChest;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.InventoryBasic;
/*      */ import net.minecraft.item.Item;
/*      */ import net.minecraft.item.ItemMap;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.network.INetHandler;
/*      */ import net.minecraft.network.NetworkManager;
/*      */ import net.minecraft.network.Packet;
/*      */ import net.minecraft.network.PacketBuffer;
/*      */ import net.minecraft.network.PacketThreadUtil;
/*      */ import net.minecraft.network.play.INetHandlerPlayClient;
/*      */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*      */ import net.minecraft.network.play.client.C03PacketPlayer;
/*      */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*      */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*      */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*      */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S03PacketTimeUpdate;
/*      */ import net.minecraft.network.play.server.S04PacketEntityEquipment;
/*      */ import net.minecraft.network.play.server.S05PacketSpawnPosition;
/*      */ import net.minecraft.network.play.server.S06PacketUpdateHealth;
/*      */ import net.minecraft.network.play.server.S07PacketRespawn;
/*      */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.S09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.server.S0APacketUseBed;
/*      */ import net.minecraft.network.play.server.S0BPacketAnimation;
/*      */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*      */ import net.minecraft.network.play.server.S0DPacketCollectItem;
/*      */ import net.minecraft.network.play.server.S0EPacketSpawnObject;
/*      */ import net.minecraft.network.play.server.S0FPacketSpawnMob;
/*      */ import net.minecraft.network.play.server.S10PacketSpawnPainting;
/*      */ import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
/*      */ import net.minecraft.network.play.server.S12PacketEntityVelocity;
/*      */ import net.minecraft.network.play.server.S13PacketDestroyEntities;
/*      */ import net.minecraft.network.play.server.S14PacketEntity;
/*      */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*      */ import net.minecraft.network.play.server.S19PacketEntityHeadLook;
/*      */ import net.minecraft.network.play.server.S19PacketEntityStatus;
/*      */ import net.minecraft.network.play.server.S1BPacketEntityAttach;
/*      */ import net.minecraft.network.play.server.S1CPacketEntityMetadata;
/*      */ import net.minecraft.network.play.server.S1DPacketEntityEffect;
/*      */ import net.minecraft.network.play.server.S1EPacketRemoveEntityEffect;
/*      */ import net.minecraft.network.play.server.S1FPacketSetExperience;
/*      */ import net.minecraft.network.play.server.S20PacketEntityProperties;
/*      */ import net.minecraft.network.play.server.S21PacketChunkData;
/*      */ import net.minecraft.network.play.server.S22PacketMultiBlockChange;
/*      */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*      */ import net.minecraft.network.play.server.S24PacketBlockAction;
/*      */ import net.minecraft.network.play.server.S25PacketBlockBreakAnim;
/*      */ import net.minecraft.network.play.server.S26PacketMapChunkBulk;
/*      */ import net.minecraft.network.play.server.S27PacketExplosion;
/*      */ import net.minecraft.network.play.server.S28PacketEffect;
/*      */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*      */ import net.minecraft.network.play.server.S2APacketParticles;
/*      */ import net.minecraft.network.play.server.S2BPacketChangeGameState;
/*      */ import net.minecraft.network.play.server.S2CPacketSpawnGlobalEntity;
/*      */ import net.minecraft.network.play.server.S2DPacketOpenWindow;
/*      */ import net.minecraft.network.play.server.S2EPacketCloseWindow;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S30PacketWindowItems;
/*      */ import net.minecraft.network.play.server.S31PacketWindowProperty;
/*      */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*      */ import net.minecraft.network.play.server.S33PacketUpdateSign;
/*      */ import net.minecraft.network.play.server.S34PacketMaps;
/*      */ import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
/*      */ import net.minecraft.network.play.server.S36PacketSignEditorOpen;
/*      */ import net.minecraft.network.play.server.S37PacketStatistics;
/*      */ import net.minecraft.network.play.server.S38PacketPlayerListItem;
/*      */ import net.minecraft.network.play.server.S39PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.server.S3APacketTabComplete;
/*      */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*      */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*      */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/*      */ import net.minecraft.network.play.server.S3EPacketTeams;
/*      */ import net.minecraft.network.play.server.S3FPacketCustomPayload;
/*      */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*      */ import net.minecraft.network.play.server.S41PacketServerDifficulty;
/*      */ import net.minecraft.network.play.server.S42PacketCombatEvent;
/*      */ import net.minecraft.network.play.server.S43PacketCamera;
/*      */ import net.minecraft.network.play.server.S44PacketWorldBorder;
/*      */ import net.minecraft.network.play.server.S45PacketTitle;
/*      */ import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
/*      */ import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
/*      */ import net.minecraft.network.play.server.S48PacketResourcePackSend;
/*      */ import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
/*      */ import net.minecraft.potion.PotionEffect;
/*      */ import net.minecraft.realms.DisconnectedRealmsScreen;
/*      */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*      */ import net.minecraft.scoreboard.Score;
/*      */ import net.minecraft.scoreboard.ScoreObjective;
/*      */ import net.minecraft.scoreboard.ScorePlayerTeam;
/*      */ import net.minecraft.scoreboard.Scoreboard;
/*      */ import net.minecraft.scoreboard.Team;
/*      */ import net.minecraft.stats.Achievement;
/*      */ import net.minecraft.stats.AchievementList;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.EnumParticleTypes;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.MathHelper;
/*      */ import net.minecraft.util.StringUtils;
/*      */ import net.minecraft.village.MerchantRecipeList;
/*      */ import net.minecraft.world.Explosion;
/*      */ import net.minecraft.world.IInteractionObject;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldSettings;
/*      */ import net.minecraft.world.chunk.Chunk;
/*      */ import net.minecraft.world.storage.MapData;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class NetHandlerPlayClient
/*      */   implements INetHandlerPlayClient {
/*  222 */   private static final Logger logger = LogManager.getLogger();
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final NetworkManager netManager;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final GameProfile profile;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private final GuiScreen guiScreenServer;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private Minecraft gameController;
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private WorldClient clientWorldController;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean doneLoadingTerrain;
/*      */ 
/*      */ 
/*      */   
/*  255 */   private final Map<UUID, NetworkPlayerInfo> playerInfoMap = Maps.newHashMap();
/*  256 */   public int currentServerMaxPlayers = 20;
/*      */ 
/*      */ 
/*      */   
/*      */   private boolean field_147308_k = false;
/*      */ 
/*      */   
/*  263 */   private final Random avRandomizer = new Random();
/*      */ 
/*      */   
/*      */   public NetHandlerPlayClient(Minecraft mcIn, GuiScreen p_i46300_2_, NetworkManager p_i46300_3_, GameProfile p_i46300_4_) {
/*  267 */     this.gameController = mcIn;
/*  268 */     this.guiScreenServer = p_i46300_2_;
/*  269 */     this.netManager = p_i46300_3_;
/*  270 */     this.profile = p_i46300_4_;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void cleanup() {
/*  277 */     this.clientWorldController = null;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleJoinGame(S01PacketJoinGame packetIn) {
/*  286 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  287 */     this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
/*  288 */     this.clientWorldController = new WorldClient(this, 
/*  289 */         new WorldSettings(0L, packetIn.getGameType(), false, packetIn.isHardcoreMode(), 
/*  290 */           packetIn.getWorldType()), 
/*  291 */         packetIn.getDimension(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/*  292 */     this.gameController.gameSettings.difficulty = packetIn.getDifficulty();
/*  293 */     this.gameController.loadWorld(this.clientWorldController);
/*  294 */     this.gameController.thePlayer.dimension = packetIn.getDimension();
/*  295 */     this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain(this));
/*  296 */     this.gameController.thePlayer.setEntityId(packetIn.getEntityId());
/*  297 */     this.currentServerMaxPlayers = packetIn.getMaxPlayers();
/*  298 */     this.gameController.thePlayer.setReducedDebug(packetIn.isReducedDebugInfo());
/*  299 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*  300 */     this.gameController.gameSettings.sendSettingsToServer();
/*  301 */     this.netManager.sendPacket((Packet)new C17PacketCustomPayload("MC|Brand", (
/*  302 */           new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnObject(S0EPacketSpawnObject packetIn) {
/*      */     EntityFallingBlock entityFallingBlock;
/*  310 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  311 */     double d0 = packetIn.getX() / 32.0D;
/*  312 */     double d1 = packetIn.getY() / 32.0D;
/*  313 */     double d2 = packetIn.getZ() / 32.0D;
/*  314 */     Entity entity = null;
/*      */     
/*  316 */     if (packetIn.getType() == 10) {
/*  317 */       EntityMinecart entityMinecart = EntityMinecart.func_180458_a((World)this.clientWorldController, d0, d1, d2, 
/*  318 */           EntityMinecart.EnumMinecartType.byNetworkID(packetIn.func_149009_m()));
/*  319 */     } else if (packetIn.getType() == 90) {
/*  320 */       Entity entity1 = this.clientWorldController.getEntityByID(packetIn.func_149009_m());
/*      */       
/*  322 */       if (entity1 instanceof EntityPlayer) {
/*  323 */         EntityFishHook entityFishHook = new EntityFishHook((World)this.clientWorldController, d0, d1, d2, (EntityPlayer)entity1);
/*      */       }
/*      */       
/*  326 */       packetIn.func_149002_g(0);
/*  327 */     } else if (packetIn.getType() == 60) {
/*  328 */       EntityArrow entityArrow = new EntityArrow((World)this.clientWorldController, d0, d1, d2);
/*  329 */     } else if (packetIn.getType() == 61) {
/*  330 */       EntitySnowball entitySnowball = new EntitySnowball((World)this.clientWorldController, d0, d1, d2);
/*  331 */     } else if (packetIn.getType() == 71) {
/*  332 */       EntityItemFrame entityItemFrame = new EntityItemFrame((World)this.clientWorldController, 
/*  333 */           new BlockPos(MathHelper.floor_double(d0), MathHelper.floor_double(d1), MathHelper.floor_double(d2)), 
/*  334 */           EnumFacing.getHorizontal(packetIn.func_149009_m()));
/*  335 */       packetIn.func_149002_g(0);
/*  336 */     } else if (packetIn.getType() == 77) {
/*  337 */       EntityLeashKnot entityLeashKnot = new EntityLeashKnot((World)this.clientWorldController, new BlockPos(MathHelper.floor_double(d0), 
/*  338 */             MathHelper.floor_double(d1), MathHelper.floor_double(d2)));
/*  339 */       packetIn.func_149002_g(0);
/*  340 */     } else if (packetIn.getType() == 65) {
/*  341 */       EntityEnderPearl entityEnderPearl = new EntityEnderPearl((World)this.clientWorldController, d0, d1, d2);
/*  342 */     } else if (packetIn.getType() == 72) {
/*  343 */       EntityEnderEye entityEnderEye = new EntityEnderEye((World)this.clientWorldController, d0, d1, d2);
/*  344 */     } else if (packetIn.getType() == 76) {
/*  345 */       EntityFireworkRocket entityFireworkRocket = new EntityFireworkRocket((World)this.clientWorldController, d0, d1, d2, null);
/*  346 */     } else if (packetIn.getType() == 63) {
/*  347 */       EntityLargeFireball entityLargeFireball = new EntityLargeFireball((World)this.clientWorldController, d0, d1, d2, 
/*  348 */           packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, 
/*  349 */           packetIn.getSpeedZ() / 8000.0D);
/*  350 */       packetIn.func_149002_g(0);
/*  351 */     } else if (packetIn.getType() == 64) {
/*  352 */       EntitySmallFireball entitySmallFireball = new EntitySmallFireball((World)this.clientWorldController, d0, d1, d2, 
/*  353 */           packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, 
/*  354 */           packetIn.getSpeedZ() / 8000.0D);
/*  355 */       packetIn.func_149002_g(0);
/*  356 */     } else if (packetIn.getType() == 66) {
/*  357 */       EntityWitherSkull entityWitherSkull = new EntityWitherSkull((World)this.clientWorldController, d0, d1, d2, 
/*  358 */           packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, 
/*  359 */           packetIn.getSpeedZ() / 8000.0D);
/*  360 */       packetIn.func_149002_g(0);
/*  361 */     } else if (packetIn.getType() == 62) {
/*  362 */       EntityEgg entityEgg = new EntityEgg((World)this.clientWorldController, d0, d1, d2);
/*  363 */     } else if (packetIn.getType() == 73) {
/*  364 */       EntityPotion entityPotion = new EntityPotion((World)this.clientWorldController, d0, d1, d2, packetIn.func_149009_m());
/*  365 */       packetIn.func_149002_g(0);
/*  366 */     } else if (packetIn.getType() == 75) {
/*  367 */       EntityExpBottle entityExpBottle = new EntityExpBottle((World)this.clientWorldController, d0, d1, d2);
/*  368 */       packetIn.func_149002_g(0);
/*  369 */     } else if (packetIn.getType() == 1) {
/*  370 */       EntityBoat entityBoat = new EntityBoat((World)this.clientWorldController, d0, d1, d2);
/*  371 */     } else if (packetIn.getType() == 50) {
/*  372 */       EntityTNTPrimed entityTNTPrimed = new EntityTNTPrimed((World)this.clientWorldController, d0, d1, d2, null);
/*  373 */     } else if (packetIn.getType() == 78) {
/*  374 */       EntityArmorStand entityArmorStand = new EntityArmorStand((World)this.clientWorldController, d0, d1, d2);
/*  375 */     } else if (packetIn.getType() == 51) {
/*  376 */       EntityEnderCrystal entityEnderCrystal = new EntityEnderCrystal((World)this.clientWorldController, d0, d1, d2);
/*  377 */     } else if (packetIn.getType() == 2) {
/*  378 */       EntityItem entityItem = new EntityItem((World)this.clientWorldController, d0, d1, d2);
/*  379 */     } else if (packetIn.getType() == 70) {
/*  380 */       entityFallingBlock = new EntityFallingBlock((World)this.clientWorldController, d0, d1, d2, 
/*  381 */           Block.getStateById(packetIn.func_149009_m() & 0xFFFF));
/*  382 */       packetIn.func_149002_g(0);
/*      */     } 
/*      */     
/*  385 */     if (entityFallingBlock != null) {
/*  386 */       ((Entity)entityFallingBlock).serverPosX = packetIn.getX();
/*  387 */       ((Entity)entityFallingBlock).serverPosY = packetIn.getY();
/*  388 */       ((Entity)entityFallingBlock).serverPosZ = packetIn.getZ();
/*  389 */       ((Entity)entityFallingBlock).rotationPitch = (packetIn.getPitch() * 360) / 256.0F;
/*  390 */       ((Entity)entityFallingBlock).rotationYaw = (packetIn.getYaw() * 360) / 256.0F;
/*  391 */       Entity[] aentity = entityFallingBlock.getParts();
/*      */       
/*  393 */       if (aentity != null) {
/*  394 */         int i = packetIn.getEntityID() - entityFallingBlock.getEntityId();
/*      */         
/*  396 */         for (int j = 0; j < aentity.length; j++) {
/*  397 */           aentity[j].setEntityId(aentity[j].getEntityId() + i);
/*      */         }
/*      */       } 
/*      */       
/*  401 */       entityFallingBlock.setEntityId(packetIn.getEntityID());
/*  402 */       this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityFallingBlock);
/*      */       
/*  404 */       if (packetIn.func_149009_m() > 0) {
/*  405 */         if (packetIn.getType() == 60) {
/*  406 */           Entity entity2 = this.clientWorldController.getEntityByID(packetIn.func_149009_m());
/*      */           
/*  408 */           if (entity2 instanceof EntityLivingBase && entityFallingBlock instanceof EntityArrow) {
/*  409 */             ((EntityArrow)entityFallingBlock).shootingEntity = entity2;
/*      */           }
/*      */         } 
/*      */         
/*  413 */         entityFallingBlock.setVelocity(packetIn.getSpeedX() / 8000.0D, packetIn.getSpeedY() / 8000.0D, 
/*  414 */             packetIn.getSpeedZ() / 8000.0D);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb packetIn) {
/*  423 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  424 */     EntityXPOrb entityXPOrb = new EntityXPOrb((World)this.clientWorldController, packetIn.getX() / 32.0D, 
/*  425 */         packetIn.getY() / 32.0D, packetIn.getZ() / 32.0D, packetIn.getXPValue());
/*  426 */     ((Entity)entityXPOrb).serverPosX = packetIn.getX();
/*  427 */     ((Entity)entityXPOrb).serverPosY = packetIn.getY();
/*  428 */     ((Entity)entityXPOrb).serverPosZ = packetIn.getZ();
/*  429 */     ((Entity)entityXPOrb).rotationYaw = 0.0F;
/*  430 */     ((Entity)entityXPOrb).rotationPitch = 0.0F;
/*  431 */     entityXPOrb.setEntityId(packetIn.getEntityID());
/*  432 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityXPOrb);
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity packetIn) {
/*      */     EntityLightningBolt entityLightningBolt;
/*  439 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  440 */     double d0 = packetIn.func_149051_d() / 32.0D;
/*  441 */     double d1 = packetIn.func_149050_e() / 32.0D;
/*  442 */     double d2 = packetIn.func_149049_f() / 32.0D;
/*  443 */     Entity entity = null;
/*      */     
/*  445 */     if (packetIn.func_149053_g() == 1) {
/*  446 */       entityLightningBolt = new EntityLightningBolt((World)this.clientWorldController, d0, d1, d2);
/*      */     }
/*      */     
/*  449 */     if (entityLightningBolt != null) {
/*  450 */       ((Entity)entityLightningBolt).serverPosX = packetIn.func_149051_d();
/*  451 */       ((Entity)entityLightningBolt).serverPosY = packetIn.func_149050_e();
/*  452 */       ((Entity)entityLightningBolt).serverPosZ = packetIn.func_149049_f();
/*  453 */       ((Entity)entityLightningBolt).rotationYaw = 0.0F;
/*  454 */       ((Entity)entityLightningBolt).rotationPitch = 0.0F;
/*  455 */       entityLightningBolt.setEntityId(packetIn.func_149052_c());
/*  456 */       this.clientWorldController.addWeatherEffect((Entity)entityLightningBolt);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPainting(S10PacketSpawnPainting packetIn) {
/*  464 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  465 */     EntityPainting entitypainting = new EntityPainting((World)this.clientWorldController, packetIn.getPosition(), 
/*  466 */         packetIn.getFacing(), packetIn.getTitle());
/*  467 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitypainting);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityVelocity(S12PacketEntityVelocity packetIn) {
/*  475 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  476 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  478 */     if (entity != null)
/*      */     {
/*  480 */       if (!Client.instance.getModuleManager().getModuleByName("Knockback").isEnabled()) {
/*      */         
/*  482 */         entity.setVelocity(packetIn.getMotionX() / 8000.0D, packetIn.getMotionY() / 8000.0D, 
/*  483 */             packetIn.getMotionZ() / 8000.0D);
/*      */ 
/*      */       
/*      */       }
/*  487 */       else if (entity == (Client.instance.getMc()).thePlayer) {
/*      */ 
/*      */         
/*  490 */         if (!Client.instance.getSettingManager().getSettingByName("KBMode").getMode().equalsIgnoreCase("Null"))
/*      */         {
/*      */ 
/*      */           
/*  494 */           entity.setVelocity(packetIn.getMotionX() / 8000.0D, 
/*  495 */               packetIn.getMotionY() / 8000.0D, packetIn.getMotionZ() / 8000.0D);
/*      */         
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/*  501 */         entity.setVelocity(packetIn.getMotionX() / 8000.0D, 
/*  502 */             packetIn.getMotionY() / 8000.0D, packetIn.getMotionZ() / 8000.0D);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMetadata(S1CPacketEntityMetadata packetIn) {
/*  516 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  517 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  519 */     if (entity != null && packetIn.func_149376_c() != null) {
/*  520 */       entity.getDataWatcher().updateWatchedObjectsFromList(packetIn.func_149376_c());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnPlayer(S0CPacketSpawnPlayer packetIn) {
/*  529 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  530 */     double d0 = packetIn.getX() / 32.0D;
/*  531 */     double d1 = packetIn.getY() / 32.0D;
/*  532 */     double d2 = packetIn.getZ() / 32.0D;
/*  533 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/*  534 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*  535 */     EntityOtherPlayerMP entityotherplayermp = new EntityOtherPlayerMP((World)this.gameController.theWorld, 
/*  536 */         getPlayerInfo(packetIn.getPlayer()).getGameProfile());
/*  537 */     entityotherplayermp.prevPosX = entityotherplayermp.lastTickPosX = (entityotherplayermp.serverPosX = packetIn
/*  538 */       .getX());
/*  539 */     entityotherplayermp.prevPosY = entityotherplayermp.lastTickPosY = (entityotherplayermp.serverPosY = packetIn
/*  540 */       .getY());
/*  541 */     entityotherplayermp.prevPosZ = entityotherplayermp.lastTickPosZ = (entityotherplayermp.serverPosZ = packetIn
/*  542 */       .getZ());
/*  543 */     int i = packetIn.getCurrentItemID();
/*      */     
/*  545 */     if (i == 0) {
/*  546 */       entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = null;
/*      */     } else {
/*  548 */       entityotherplayermp.inventory.mainInventory[entityotherplayermp.inventory.currentItem] = new ItemStack(
/*  549 */           Item.getItemById(i), 1, 0);
/*      */     } 
/*      */     
/*  552 */     entityotherplayermp.setPositionAndRotation(d0, d1, d2, f, f1);
/*  553 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entityotherplayermp);
/*  554 */     List<DataWatcher.WatchableObject> list = packetIn.func_148944_c();
/*      */     
/*  556 */     if (list != null) {
/*  557 */       entityotherplayermp.getDataWatcher().updateWatchedObjectsFromList(list);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityTeleport(S18PacketEntityTeleport packetIn) {
/*  565 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  566 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/*  568 */     if (entity != null) {
/*  569 */       entity.serverPosX = packetIn.getX();
/*  570 */       entity.serverPosY = packetIn.getY();
/*  571 */       entity.serverPosZ = packetIn.getZ();
/*  572 */       double d0 = entity.serverPosX / 32.0D;
/*  573 */       double d1 = entity.serverPosY / 32.0D;
/*  574 */       double d2 = entity.serverPosZ / 32.0D;
/*  575 */       float f = (packetIn.getYaw() * 360) / 256.0F;
/*  576 */       float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*      */       
/*  578 */       if (Math.abs(entity.posX - d0) < 0.03125D && Math.abs(entity.posY - d1) < 0.015625D && 
/*  579 */         Math.abs(entity.posZ - d2) < 0.03125D) {
/*  580 */         entity.setPositionAndRotation2(entity.posX, entity.posY, entity.posZ, f, f1, 3, true);
/*      */       } else {
/*  582 */         entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, true);
/*      */       } 
/*      */       
/*  585 */       entity.onGround = packetIn.getOnGround();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleHeldItemChange(S09PacketHeldItemChange packetIn) {
/*  593 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  595 */     if (packetIn.getHeldItemHotbarIndex() >= 0 && 
/*  596 */       packetIn.getHeldItemHotbarIndex() < InventoryPlayer.getHotbarSize()) {
/*  597 */       this.gameController.thePlayer.inventory.currentItem = packetIn.getHeldItemHotbarIndex();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityMovement(S14PacketEntity packetIn) {
/*  608 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  609 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  611 */     if (entity != null) {
/*  612 */       entity.serverPosX += packetIn.func_149062_c();
/*  613 */       entity.serverPosY += packetIn.func_149061_d();
/*  614 */       entity.serverPosZ += packetIn.func_149064_e();
/*  615 */       double d0 = entity.serverPosX / 32.0D;
/*  616 */       double d1 = entity.serverPosY / 32.0D;
/*  617 */       double d2 = entity.serverPosZ / 32.0D;
/*  618 */       float f = packetIn.func_149060_h() ? ((packetIn.func_149066_f() * 360) / 256.0F) : entity.rotationYaw;
/*  619 */       float f1 = packetIn.func_149060_h() ? ((packetIn.func_149063_g() * 360) / 256.0F) : 
/*  620 */         entity.rotationPitch;
/*  621 */       entity.setPositionAndRotation2(d0, d1, d2, f, f1, 3, false);
/*  622 */       entity.onGround = packetIn.getOnGround();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityHeadLook(S19PacketEntityHeadLook packetIn) {
/*  631 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  632 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/*  634 */     if (entity != null) {
/*  635 */       float f = (packetIn.getYaw() * 360) / 256.0F;
/*  636 */       entity.setRotationYawHead(f);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDestroyEntities(S13PacketDestroyEntities packetIn) {
/*  647 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  649 */     for (int i = 0; i < (packetIn.getEntityIDs()).length; i++) {
/*  650 */       this.clientWorldController.removeEntityFromWorld(packetIn.getEntityIDs()[i]);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handlePlayerPosLook(S08PacketPlayerPosLook packetIn) {
/*  661 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  662 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*  663 */     double d0 = packetIn.getX();
/*  664 */     double d1 = packetIn.getY();
/*  665 */     double d2 = packetIn.getZ();
/*  666 */     float f = packetIn.getYaw();
/*  667 */     float f1 = packetIn.getPitch();
/*      */     
/*  669 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
/*  670 */       d0 += ((EntityPlayer)entityPlayerSP).posX;
/*      */     } else {
/*  672 */       ((EntityPlayer)entityPlayerSP).motionX = 0.0D;
/*      */     } 
/*      */     
/*  675 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
/*  676 */       d1 += ((EntityPlayer)entityPlayerSP).posY;
/*      */     } else {
/*  678 */       ((EntityPlayer)entityPlayerSP).motionY = 0.0D;
/*      */     } 
/*      */     
/*  681 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
/*  682 */       d2 += ((EntityPlayer)entityPlayerSP).posZ;
/*      */     } else {
/*  684 */       ((EntityPlayer)entityPlayerSP).motionZ = 0.0D;
/*      */     } 
/*      */     
/*  687 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
/*  688 */       f1 += ((EntityPlayer)entityPlayerSP).rotationPitch;
/*      */     }
/*      */     
/*  691 */     if (packetIn.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
/*  692 */       f += ((EntityPlayer)entityPlayerSP).rotationYaw;
/*      */     }
/*      */     
/*  695 */     entityPlayerSP.setPositionAndRotation(d0, d1, d2, f, f1);
/*  696 */     this.netManager.sendPacket(
/*  697 */         (Packet)new C03PacketPlayer.C06PacketPlayerPosLook(((EntityPlayer)entityPlayerSP).posX, (entityPlayerSP.getEntityBoundingBox()).minY, 
/*  698 */           ((EntityPlayer)entityPlayerSP).posZ, ((EntityPlayer)entityPlayerSP).rotationYaw, ((EntityPlayer)entityPlayerSP).rotationPitch, false));
/*      */     
/*  700 */     if (!this.doneLoadingTerrain) {
/*  701 */       this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
/*  702 */       this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
/*  703 */       this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
/*  704 */       this.doneLoadingTerrain = true;
/*  705 */       this.gameController.displayGuiScreen(null);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMultiBlockChange(S22PacketMultiBlockChange packetIn) {
/*  716 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*      */     S22PacketMultiBlockChange.BlockUpdateData[] arrayOfBlockUpdateData;
/*  719 */     int i = (arrayOfBlockUpdateData = packetIn.getChangedBlocks()).length; byte b = 0; for (; b < i; b++) { S22PacketMultiBlockChange.BlockUpdateData s22packetmultiblockchange$blockupdatedata = arrayOfBlockUpdateData[b];
/*  720 */       this.clientWorldController.invalidateRegionAndSetBlock(s22packetmultiblockchange$blockupdatedata.getPos(), 
/*  721 */           s22packetmultiblockchange$blockupdatedata.getBlockState()); }
/*      */   
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChunkData(S21PacketChunkData packetIn) {
/*  730 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  732 */     if (packetIn.func_149274_i()) {
/*  733 */       if (packetIn.getExtractedSize() == 0) {
/*  734 */         this.clientWorldController.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), false);
/*      */         
/*      */         return;
/*      */       } 
/*  738 */       this.clientWorldController.doPreChunk(packetIn.getChunkX(), packetIn.getChunkZ(), true);
/*      */     } 
/*      */     
/*  741 */     this.clientWorldController.invalidateBlockReceiveRegion(packetIn.getChunkX() << 4, 0, packetIn.getChunkZ() << 4, (
/*  742 */         packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*  743 */     Chunk chunk = this.clientWorldController.getChunkFromChunkCoords(packetIn.getChunkX(), packetIn.getChunkZ());
/*  744 */     chunk.fillChunk(packetIn.func_149272_d(), packetIn.getExtractedSize(), packetIn.func_149274_i());
/*  745 */     this.clientWorldController.markBlockRangeForRenderUpdate(packetIn.getChunkX() << 4, 0, 
/*  746 */         packetIn.getChunkZ() << 4, (packetIn.getChunkX() << 4) + 15, 256, (packetIn.getChunkZ() << 4) + 15);
/*      */     
/*  748 */     if (!packetIn.func_149274_i() || !(this.clientWorldController.provider instanceof net.minecraft.world.WorldProviderSurface)) {
/*  749 */       chunk.resetRelightChecks();
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockChange(S23PacketBlockChange packetIn) {
/*  758 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  759 */     this.clientWorldController.invalidateRegionAndSetBlock(packetIn.getBlockPosition(), packetIn.getBlockState());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisconnect(S40PacketDisconnect packetIn) {
/*  766 */     this.netManager.closeChannel(packetIn.getReason());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(IChatComponent reason) {
/*  774 */     this.gameController.loadWorld(null);
/*      */     
/*  776 */     if (this.guiScreenServer != null) {
/*  777 */       if (this.guiScreenServer instanceof GuiScreenRealmsProxy) {
/*  778 */         this.gameController.displayGuiScreen(
/*  779 */             (GuiScreen)(new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.guiScreenServer).func_154321_a(), 
/*  780 */               "disconnect.lost", reason)).getProxy());
/*      */       } else {
/*  782 */         this.gameController
/*  783 */           .displayGuiScreen((GuiScreen)new GuiDisconnected(this.guiScreenServer, "disconnect.lost", reason));
/*      */       } 
/*      */     } else {
/*  786 */       this.gameController.displayGuiScreen(
/*  787 */           (GuiScreen)new GuiDisconnected((GuiScreen)new GuiMultiplayer((GuiScreen)new GuiMainMenu()), "disconnect.lost", reason));
/*      */     } 
/*      */   } public void addToSendQueue(Packet packet) {
/*      */     C03PacketPlayer c03PacketPlayer;
/*      */     C03PacketPlayer.C04PacketPlayerPosition c04PacketPlayerPosition;
/*  792 */     if (packet instanceof C03PacketPlayer) {
/*  793 */       C03PacketPlayer p = (C03PacketPlayer)packet;
/*  794 */       C03PacketPlayer.yaw = (Minecraft.getMinecraft()).thePlayer.rotationYaw;
/*  795 */       C03PacketPlayer.pitch = (Minecraft.getMinecraft()).thePlayer.rotationPitch;
/*  796 */       c03PacketPlayer = p;
/*      */     } 
/*      */     
/*  799 */     PacketEvent event = new PacketEvent((Packet)c03PacketPlayer);
/*  800 */     EventManager.call((Event)event);
/*  801 */     Packet packet1 = event.getPacket();
/*  802 */     if (packet1 instanceof C03PacketPlayer) {
/*  803 */       C03PacketPlayer.C05PacketPlayerLook c05PacketPlayerLook; C03PacketPlayer.C04PacketPlayerPosition c04PacketPlayerPosition1; C03PacketPlayer p = (C03PacketPlayer)packet1;
/*  804 */       if (!(p instanceof C03PacketPlayer.C05PacketPlayerLook) && 
/*  805 */         !(p instanceof C03PacketPlayer.C06PacketPlayerPosLook) && (
/*  806 */         C03PacketPlayer.yaw != 0.0F || C03PacketPlayer.pitch != 0.0F) && (
/*  807 */         C03PacketPlayer.yaw != PositionHelper.getYaw() || 
/*  808 */         C03PacketPlayer.pitch != PositionHelper.getPitch())) {
/*  809 */         if (p instanceof C03PacketPlayer.C04PacketPlayerPosition) {
/*  810 */           C03PacketPlayer.C06PacketPlayerPosLook c06PacketPlayerPosLook = new C03PacketPlayer.C06PacketPlayerPosLook(C03PacketPlayer.x, C03PacketPlayer.y, 
/*  811 */               C03PacketPlayer.z, C03PacketPlayer.yaw, C03PacketPlayer.pitch, 
/*  812 */               C03PacketPlayer.onGround);
/*      */         } else {
/*  814 */           c05PacketPlayerLook = new C03PacketPlayer.C05PacketPlayerLook(C03PacketPlayer.yaw, C03PacketPlayer.pitch, 
/*  815 */               C03PacketPlayer.onGround);
/*      */         } 
/*      */       }
/*      */       
/*  819 */       if (c05PacketPlayerLook instanceof C03PacketPlayer.C05PacketPlayerLook || 
/*  820 */         c05PacketPlayerLook instanceof C03PacketPlayer.C06PacketPlayerPosLook) {
/*  821 */         if (PositionHelper.getYaw() == C03PacketPlayer.yaw && 
/*  822 */           PositionHelper.getPitch() == C03PacketPlayer.pitch) {
/*  823 */           if (c05PacketPlayerLook instanceof C03PacketPlayer.C05PacketPlayerLook) {
/*  824 */             C03PacketPlayer c03PacketPlayer1 = new C03PacketPlayer(C03PacketPlayer.onGround);
/*      */           } else {
/*  826 */             c04PacketPlayerPosition1 = new C03PacketPlayer.C04PacketPlayerPosition(C03PacketPlayer.x, 
/*  827 */                 C03PacketPlayer.y, C03PacketPlayer.z, C03PacketPlayer.onGround);
/*      */           } 
/*      */         } else {
/*  830 */           PositionHelper.setYaw(C03PacketPlayer.yaw);
/*  831 */           PositionHelper.setPitch(C03PacketPlayer.pitch);
/*      */         } 
/*      */       }
/*      */       
/*  835 */       c04PacketPlayerPosition = c04PacketPlayerPosition1;
/*      */     } 
/*      */     
/*  838 */     this.netManager.sendPacket((Packet)c04PacketPlayerPosition);
/*      */   }
/*      */   public void handleCollectItem(S0DPacketCollectItem packetIn) {
/*      */     EntityPlayerSP entityPlayerSP;
/*  842 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  843 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getCollectedItemEntityID());
/*  844 */     EntityLivingBase entitylivingbase = (EntityLivingBase)this.clientWorldController
/*  845 */       .getEntityByID(packetIn.getEntityID());
/*      */     
/*  847 */     if (entitylivingbase == null) {
/*  848 */       entityPlayerSP = this.gameController.thePlayer;
/*      */     }
/*      */     
/*  851 */     if (entity != null) {
/*  852 */       if (entity instanceof EntityXPOrb) {
/*  853 */         this.clientWorldController.playSoundAtEntity(entity, "random.orb", 0.2F, ((
/*  854 */             this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*      */       } else {
/*  856 */         this.clientWorldController.playSoundAtEntity(entity, "random.pop", 0.2F, ((
/*  857 */             this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
/*      */       } 
/*      */       
/*  860 */       this.gameController.effectRenderer
/*  861 */         .addEffect((EntityFX)new EntityPickupFX((World)this.clientWorldController, entity, (Entity)entityPlayerSP, 0.5F));
/*  862 */       this.clientWorldController.removeEntityFromWorld(packetIn.getCollectedItemEntityID());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleChat(S02PacketChat packetIn) {
/*  870 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/*  872 */     if (packetIn.getType() == 2) {
/*  873 */       this.gameController.ingameGUI.setRecordPlaying(packetIn.getChatComponent(), false);
/*      */     } else {
/*  875 */       this.gameController.ingameGUI.getChatGUI().printChatMessage(packetIn.getChatComponent());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleAnimation(S0BPacketAnimation packetIn) {
/*  885 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  886 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/*  888 */     if (entity != null) {
/*  889 */       if (packetIn.getAnimationType() == 0) {
/*  890 */         EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
/*  891 */         entitylivingbase.swingItem();
/*  892 */       } else if (packetIn.getAnimationType() == 1) {
/*  893 */         entity.performHurtAnimation();
/*  894 */       } else if (packetIn.getAnimationType() == 2) {
/*  895 */         EntityPlayer entityplayer = (EntityPlayer)entity;
/*  896 */         entityplayer.wakeUpPlayer(false, false, false);
/*  897 */       } else if (packetIn.getAnimationType() == 4) {
/*  898 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT);
/*  899 */       } else if (packetIn.getAnimationType() == 5) {
/*  900 */         this.gameController.effectRenderer.emitParticleAtEntity(entity, EnumParticleTypes.CRIT_MAGIC);
/*      */       } 
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUseBed(S0APacketUseBed packetIn) {
/*  910 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  911 */     packetIn.getPlayer((World)this.clientWorldController).trySleep(packetIn.getBedPosition());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSpawnMob(S0FPacketSpawnMob packetIn) {
/*  920 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  921 */     double d0 = packetIn.getX() / 32.0D;
/*  922 */     double d1 = packetIn.getY() / 32.0D;
/*  923 */     double d2 = packetIn.getZ() / 32.0D;
/*  924 */     float f = (packetIn.getYaw() * 360) / 256.0F;
/*  925 */     float f1 = (packetIn.getPitch() * 360) / 256.0F;
/*  926 */     EntityLivingBase entitylivingbase = (EntityLivingBase)EntityList.createEntityByID(packetIn.getEntityType(), 
/*  927 */         (World)this.gameController.theWorld);
/*  928 */     entitylivingbase.serverPosX = packetIn.getX();
/*  929 */     entitylivingbase.serverPosY = packetIn.getY();
/*  930 */     entitylivingbase.serverPosZ = packetIn.getZ();
/*  931 */     entitylivingbase.renderYawOffset = entitylivingbase.rotationYawHead = (packetIn.getHeadPitch() * 360) / 
/*  932 */       256.0F;
/*  933 */     Entity[] aentity = entitylivingbase.getParts();
/*      */     
/*  935 */     if (aentity != null) {
/*  936 */       int i = packetIn.getEntityID() - entitylivingbase.getEntityId();
/*      */       
/*  938 */       for (int j = 0; j < aentity.length; j++) {
/*  939 */         aentity[j].setEntityId(aentity[j].getEntityId() + i);
/*      */       }
/*      */     } 
/*      */     
/*  943 */     entitylivingbase.setEntityId(packetIn.getEntityID());
/*  944 */     entitylivingbase.setPositionAndRotation(d0, d1, d2, f, f1);
/*  945 */     entitylivingbase.motionX = (packetIn.getVelocityX() / 8000.0F);
/*  946 */     entitylivingbase.motionY = (packetIn.getVelocityY() / 8000.0F);
/*  947 */     entitylivingbase.motionZ = (packetIn.getVelocityZ() / 8000.0F);
/*  948 */     this.clientWorldController.addEntityToWorld(packetIn.getEntityID(), (Entity)entitylivingbase);
/*  949 */     List<DataWatcher.WatchableObject> list = packetIn.func_149027_c();
/*      */     
/*  951 */     if (list != null) {
/*  952 */       entitylivingbase.getDataWatcher().updateWatchedObjectsFromList(list);
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleTimeUpdate(S03PacketTimeUpdate packetIn) {
/*  957 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  958 */     this.gameController.theWorld.setTotalWorldTime(packetIn.getTotalWorldTime());
/*  959 */     this.gameController.theWorld.setWorldTime(packetIn.getWorldTime());
/*      */   }
/*      */   
/*      */   public void handleSpawnPosition(S05PacketSpawnPosition packetIn) {
/*  963 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  964 */     this.gameController.thePlayer.setSpawnPoint(packetIn.getSpawnPos(), true);
/*  965 */     this.gameController.theWorld.getWorldInfo().setSpawn(packetIn.getSpawnPos());
/*      */   }
/*      */   public void handleEntityAttach(S1BPacketEntityAttach packetIn) {
/*      */     EntityPlayerSP entityPlayerSP;
/*  969 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*  970 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*  971 */     Entity entity1 = this.clientWorldController.getEntityByID(packetIn.getVehicleEntityId());
/*      */     
/*  973 */     if (packetIn.getLeash() == 0) {
/*  974 */       boolean flag = false;
/*      */       
/*  976 */       if (packetIn.getEntityId() == this.gameController.thePlayer.getEntityId()) {
/*  977 */         entityPlayerSP = this.gameController.thePlayer;
/*      */         
/*  979 */         if (entity1 instanceof EntityBoat) {
/*  980 */           ((EntityBoat)entity1).setIsBoatEmpty(false);
/*      */         }
/*      */         
/*  983 */         flag = (((Entity)entityPlayerSP).ridingEntity == null && entity1 != null);
/*  984 */       } else if (entity1 instanceof EntityBoat) {
/*  985 */         ((EntityBoat)entity1).setIsBoatEmpty(true);
/*      */       } 
/*      */       
/*  988 */       if (entityPlayerSP == null) {
/*      */         return;
/*      */       }
/*      */       
/*  992 */       entityPlayerSP.mountEntity(entity1);
/*      */       
/*  994 */       if (flag) {
/*  995 */         GameSettings gamesettings = this.gameController.gameSettings;
/*  996 */         this.gameController.ingameGUI.setRecordPlaying(
/*  997 */             I18n.format("mount.onboard", 
/*  998 */               new Object[] {
/*  999 */                 GameSettings.getKeyDisplayString(gamesettings.keyBindSneak.getKeyCode())
/* 1000 */               }), false);
/*      */       } 
/* 1002 */     } else if (packetIn.getLeash() == 1 && entityPlayerSP instanceof EntityLiving) {
/* 1003 */       if (entity1 != null) {
/* 1004 */         ((EntityLiving)entityPlayerSP).setLeashedToEntity(entity1, false);
/*      */       } else {
/* 1006 */         ((EntityLiving)entityPlayerSP).clearLeashed(false, false);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityStatus(S19PacketEntityStatus packetIn) {
/* 1020 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1021 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1023 */     if (entity != null) {
/* 1024 */       if (packetIn.getOpCode() == 21) {
/* 1025 */         this.gameController.getSoundHandler().playSound((ISound)new GuardianSound((EntityGuardian)entity));
/*      */       } else {
/* 1027 */         entity.handleStatusUpdate(packetIn.getOpCode());
/*      */       } 
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleUpdateHealth(S06PacketUpdateHealth packetIn) {
/* 1033 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1034 */     this.gameController.thePlayer.setPlayerSPHealth(packetIn.getHealth());
/* 1035 */     this.gameController.thePlayer.getFoodStats().setFoodLevel(packetIn.getFoodLevel());
/* 1036 */     this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(packetIn.getSaturationLevel());
/*      */   }
/*      */   
/*      */   public void handleSetExperience(S1FPacketSetExperience packetIn) {
/* 1040 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1041 */     this.gameController.thePlayer.setXPStats(packetIn.func_149397_c(), packetIn.getTotalExperience(), 
/* 1042 */         packetIn.getLevel());
/*      */   }
/*      */   
/*      */   public void handleRespawn(S07PacketRespawn packetIn) {
/* 1046 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1048 */     if (packetIn.getDimensionID() != this.gameController.thePlayer.dimension) {
/* 1049 */       this.doneLoadingTerrain = false;
/* 1050 */       Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/* 1051 */       this.clientWorldController = new WorldClient(this, new WorldSettings(0L, packetIn.getGameType(), false, 
/* 1052 */             this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), packetIn.getWorldType()), 
/* 1053 */           packetIn.getDimensionID(), packetIn.getDifficulty(), this.gameController.mcProfiler);
/* 1054 */       this.clientWorldController.setWorldScoreboard(scoreboard);
/* 1055 */       this.gameController.loadWorld(this.clientWorldController);
/* 1056 */       this.gameController.thePlayer.dimension = packetIn.getDimensionID();
/* 1057 */       this.gameController.displayGuiScreen((GuiScreen)new GuiDownloadTerrain(this));
/*      */     } 
/*      */     
/* 1060 */     this.gameController.setDimensionAndSpawnPlayer(packetIn.getDimensionID());
/* 1061 */     this.gameController.playerController.setGameType(packetIn.getGameType());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleExplosion(S27PacketExplosion packetIn) {
/* 1069 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1070 */     Explosion explosion = new Explosion((World)this.gameController.theWorld, null, packetIn.getX(), 
/* 1071 */         packetIn.getY(), packetIn.getZ(), packetIn.getStrength(), packetIn.getAffectedBlockPositions());
/* 1072 */     explosion.doExplosionB(true);
/* 1073 */     this.gameController.thePlayer.motionX += packetIn.func_149149_c();
/* 1074 */     this.gameController.thePlayer.motionY += packetIn.func_149144_d();
/* 1075 */     this.gameController.thePlayer.motionZ += packetIn.func_149147_e();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleOpenWindow(S2DPacketOpenWindow packetIn) {
/* 1084 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1085 */     EntityPlayerSP entityplayersp = this.gameController.thePlayer;
/*      */     
/* 1087 */     if ("minecraft:container".equals(packetIn.getGuiId())) {
/* 1088 */       entityplayersp.displayGUIChest((IInventory)new InventoryBasic(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/* 1089 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/* 1090 */     } else if ("minecraft:villager".equals(packetIn.getGuiId())) {
/* 1091 */       entityplayersp.displayVillagerTradeGui((IMerchant)new NpcMerchant((EntityPlayer)entityplayersp, packetIn.getWindowTitle()));
/* 1092 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/* 1093 */     } else if ("EntityHorse".equals(packetIn.getGuiId())) {
/* 1094 */       Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */       
/* 1096 */       if (entity instanceof EntityHorse) {
/* 1097 */         entityplayersp.displayGUIHorse((EntityHorse)entity, 
/* 1098 */             (IInventory)new AnimalChest(packetIn.getWindowTitle(), packetIn.getSlotCount()));
/* 1099 */         entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */       } 
/* 1101 */     } else if (!packetIn.hasSlots()) {
/* 1102 */       entityplayersp.displayGui((IInteractionObject)new LocalBlockIntercommunication(packetIn.getGuiId(), packetIn.getWindowTitle()));
/* 1103 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     } else {
/* 1105 */       ContainerLocalMenu containerlocalmenu = new ContainerLocalMenu(packetIn.getGuiId(), 
/* 1106 */           packetIn.getWindowTitle(), packetIn.getSlotCount());
/* 1107 */       entityplayersp.displayGUIChest((IInventory)containerlocalmenu);
/* 1108 */       entityplayersp.openContainer.windowId = packetIn.getWindowId();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSetSlot(S2FPacketSetSlot packetIn) {
/* 1117 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1118 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1120 */     if (packetIn.func_149175_c() == -1) {
/* 1121 */       ((EntityPlayer)entityPlayerSP).inventory.setItemStack(packetIn.func_149174_e());
/*      */     } else {
/* 1123 */       boolean flag = false;
/*      */       
/* 1125 */       if (this.gameController.currentScreen instanceof GuiContainerCreative) {
/* 1126 */         GuiContainerCreative guicontainercreative = (GuiContainerCreative)this.gameController.currentScreen;
/* 1127 */         flag = (guicontainercreative.getSelectedTabIndex() != CreativeTabs.tabInventory.getTabIndex());
/*      */       } 
/*      */       
/* 1130 */       if (packetIn.func_149175_c() == 0 && packetIn.func_149173_d() >= 36 && packetIn.func_149173_d() < 45) {
/* 1131 */         ItemStack itemstack = ((EntityPlayer)entityPlayerSP).inventoryContainer.getSlot(packetIn.func_149173_d()).getStack();
/*      */         
/* 1133 */         if (packetIn.func_149174_e() != null && (
/* 1134 */           itemstack == null || itemstack.stackSize < (packetIn.func_149174_e()).stackSize)) {
/* 1135 */           (packetIn.func_149174_e()).animationsToGo = 5;
/*      */         }
/*      */         
/* 1138 */         ((EntityPlayer)entityPlayerSP).inventoryContainer.putStackInSlot(packetIn.func_149173_d(), packetIn.func_149174_e());
/* 1139 */       } else if (packetIn.func_149175_c() == ((EntityPlayer)entityPlayerSP).openContainer.windowId && (
/* 1140 */         packetIn.func_149175_c() != 0 || !flag)) {
/* 1141 */         ((EntityPlayer)entityPlayerSP).openContainer.putStackInSlot(packetIn.func_149173_d(), packetIn.func_149174_e());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleConfirmTransaction(S32PacketConfirmTransaction packetIn) {
/* 1151 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1152 */     Container container = null;
/* 1153 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1155 */     if (packetIn.getWindowId() == 0) {
/* 1156 */       container = ((EntityPlayer)entityPlayerSP).inventoryContainer;
/* 1157 */     } else if (packetIn.getWindowId() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/* 1158 */       container = ((EntityPlayer)entityPlayerSP).openContainer;
/*      */     } 
/*      */     
/* 1161 */     if (container != null && !packetIn.func_148888_e()) {
/* 1162 */       addToSendQueue(
/* 1163 */           (Packet)new C0FPacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowItems(S30PacketWindowItems packetIn) {
/* 1172 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1173 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1175 */     if (packetIn.func_148911_c() == 0) {
/* 1176 */       ((EntityPlayer)entityPlayerSP).inventoryContainer.putStacksInSlots(packetIn.getItemStacks());
/* 1177 */     } else if (packetIn.func_148911_c() == ((EntityPlayer)entityPlayerSP).openContainer.windowId) {
/* 1178 */       ((EntityPlayer)entityPlayerSP).openContainer.putStacksInSlots(packetIn.getItemStacks());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleSignEditorOpen(S36PacketSignEditorOpen packetIn) {
/*      */     TileEntitySign tileEntitySign;
/* 1187 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1188 */     TileEntity tileentity = this.clientWorldController.getTileEntity(packetIn.getSignPosition());
/*      */     
/* 1190 */     if (!(tileentity instanceof TileEntitySign)) {
/* 1191 */       tileEntitySign = new TileEntitySign();
/* 1192 */       tileEntitySign.setWorldObj((World)this.clientWorldController);
/* 1193 */       tileEntitySign.setPos(packetIn.getSignPosition());
/*      */     } 
/*      */     
/* 1196 */     this.gameController.thePlayer.openEditSign(tileEntitySign);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateSign(S33PacketUpdateSign packetIn) {
/* 1203 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1204 */     boolean flag = false;
/*      */     
/* 1206 */     if (this.gameController.theWorld.isBlockLoaded(packetIn.getPos())) {
/* 1207 */       TileEntity tileentity = this.gameController.theWorld.getTileEntity(packetIn.getPos());
/*      */       
/* 1209 */       if (tileentity instanceof TileEntitySign) {
/* 1210 */         TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */         
/* 1212 */         if (tileentitysign.getIsEditable()) {
/* 1213 */           System.arraycopy(packetIn.getLines(), 0, tileentitysign.signText, 0, 4);
/* 1214 */           tileentitysign.markDirty();
/*      */         } 
/*      */         
/* 1217 */         flag = true;
/*      */       } 
/*      */     } 
/*      */     
/* 1221 */     if (!flag && this.gameController.thePlayer != null) {
/* 1222 */       this.gameController.thePlayer.addChatMessage((IChatComponent)new ChatComponentText("Unable to locate sign at " + 
/* 1223 */             packetIn.getPos().getX() + ", " + packetIn.getPos().getY() + ", " + packetIn.getPos().getZ()));
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateTileEntity(S35PacketUpdateTileEntity packetIn) {
/* 1232 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1234 */     if (this.gameController.theWorld.isBlockLoaded(packetIn.getPos())) {
/* 1235 */       TileEntity tileentity = this.gameController.theWorld.getTileEntity(packetIn.getPos());
/* 1236 */       int i = packetIn.getTileEntityType();
/*      */       
/* 1238 */       if ((i == 1 && tileentity instanceof net.minecraft.tileentity.TileEntityMobSpawner) || (
/* 1239 */         i == 2 && tileentity instanceof net.minecraft.tileentity.TileEntityCommandBlock) || (
/* 1240 */         i == 3 && tileentity instanceof net.minecraft.tileentity.TileEntityBeacon) || (
/* 1241 */         i == 4 && tileentity instanceof net.minecraft.tileentity.TileEntitySkull) || (
/* 1242 */         i == 5 && tileentity instanceof net.minecraft.tileentity.TileEntityFlowerPot) || (
/* 1243 */         i == 6 && tileentity instanceof net.minecraft.tileentity.TileEntityBanner)) {
/* 1244 */         tileentity.readFromNBT(packetIn.getNbtCompound());
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleWindowProperty(S31PacketWindowProperty packetIn) {
/* 1253 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1254 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/*      */     
/* 1256 */     if (((EntityPlayer)entityPlayerSP).openContainer != null && ((EntityPlayer)entityPlayerSP).openContainer.windowId == packetIn.getWindowId()) {
/* 1257 */       ((EntityPlayer)entityPlayerSP).openContainer.updateProgressBar(packetIn.getVarIndex(), packetIn.getVarValue());
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleEntityEquipment(S04PacketEntityEquipment packetIn) {
/* 1262 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1263 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityID());
/*      */     
/* 1265 */     if (entity != null) {
/* 1266 */       entity.setCurrentItemOrArmor(packetIn.getEquipmentSlot(), packetIn.getItemStack());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCloseWindow(S2EPacketCloseWindow packetIn) {
/* 1274 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1275 */     this.gameController.thePlayer.closeScreenAndDropStack();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockAction(S24PacketBlockAction packetIn) {
/* 1285 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1286 */     this.gameController.theWorld.addBlockEvent(packetIn.getBlockPosition(), packetIn.getBlockType(), 
/* 1287 */         packetIn.getData1(), packetIn.getData2());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleBlockBreakAnim(S25PacketBlockBreakAnim packetIn) {
/* 1295 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1296 */     this.gameController.theWorld.sendBlockBreakProgress(packetIn.getBreakerId(), packetIn.getPosition(), 
/* 1297 */         packetIn.getProgress());
/*      */   }
/*      */   
/*      */   public void handleMapChunkBulk(S26PacketMapChunkBulk packetIn) {
/* 1301 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1303 */     for (int i = 0; i < packetIn.getChunkCount(); i++) {
/* 1304 */       int j = packetIn.getChunkX(i);
/* 1305 */       int k = packetIn.getChunkZ(i);
/* 1306 */       this.clientWorldController.doPreChunk(j, k, true);
/* 1307 */       this.clientWorldController.invalidateBlockReceiveRegion(j << 4, 0, k << 4, (j << 4) + 15, 256, (
/* 1308 */           k << 4) + 15);
/* 1309 */       Chunk chunk = this.clientWorldController.getChunkFromChunkCoords(j, k);
/* 1310 */       chunk.fillChunk(packetIn.getChunkBytes(i), packetIn.getChunkSize(i), true);
/* 1311 */       this.clientWorldController.markBlockRangeForRenderUpdate(j << 4, 0, k << 4, (j << 4) + 15, 256, (
/* 1312 */           k << 4) + 15);
/*      */       
/* 1314 */       if (!(this.clientWorldController.provider instanceof net.minecraft.world.WorldProviderSurface)) {
/* 1315 */         chunk.resetRelightChecks();
/*      */       }
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleChangeGameState(S2BPacketChangeGameState packetIn) {
/* 1321 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1322 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/* 1323 */     int i = packetIn.getGameState();
/* 1324 */     float f = packetIn.func_149137_d();
/* 1325 */     int j = MathHelper.floor_float(f + 0.5F);
/*      */     
/* 1327 */     if (i >= 0 && i < S2BPacketChangeGameState.MESSAGE_NAMES.length && 
/* 1328 */       S2BPacketChangeGameState.MESSAGE_NAMES[i] != null) {
/* 1329 */       entityPlayerSP.addChatComponentMessage(
/* 1330 */           (IChatComponent)new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[i], new Object[0]));
/*      */     }
/*      */     
/* 1333 */     if (i == 1) {
/* 1334 */       this.clientWorldController.getWorldInfo().setRaining(true);
/* 1335 */       this.clientWorldController.setRainStrength(0.0F);
/* 1336 */     } else if (i == 2) {
/* 1337 */       this.clientWorldController.getWorldInfo().setRaining(false);
/* 1338 */       this.clientWorldController.setRainStrength(1.0F);
/* 1339 */     } else if (i == 3) {
/* 1340 */       this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(j));
/* 1341 */     } else if (i == 4) {
/* 1342 */       this.gameController.displayGuiScreen((GuiScreen)new GuiWinGame());
/* 1343 */     } else if (i == 5) {
/* 1344 */       GameSettings gamesettings = this.gameController.gameSettings;
/*      */       
/* 1346 */       if (f == 0.0F) {
/* 1347 */         this.gameController.displayGuiScreen((GuiScreen)new GuiScreenDemo());
/* 1348 */       } else if (f == 101.0F) {
/* 1349 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentTranslation(
/* 1350 */               "demo.help.movement", 
/* 1351 */               new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindForward.getKeyCode()), 
/* 1352 */                 GameSettings.getKeyDisplayString(gamesettings.keyBindLeft.getKeyCode()), 
/* 1353 */                 GameSettings.getKeyDisplayString(gamesettings.keyBindBack.getKeyCode()), 
/* 1354 */                 GameSettings.getKeyDisplayString(gamesettings.keyBindRight.getKeyCode()) }));
/* 1355 */       } else if (f == 102.0F) {
/* 1356 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentTranslation(
/* 1357 */               "demo.help.jump", 
/* 1358 */               new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindJump.getKeyCode()) }));
/* 1359 */       } else if (f == 103.0F) {
/* 1360 */         this.gameController.ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentTranslation(
/* 1361 */               "demo.help.inventory", 
/* 1362 */               new Object[] { GameSettings.getKeyDisplayString(gamesettings.keyBindInventory.getKeyCode()) }));
/*      */       } 
/* 1364 */     } else if (i == 6) {
/* 1365 */       this.clientWorldController.playSound(((EntityPlayer)entityPlayerSP).posX, 
/* 1366 */           ((EntityPlayer)entityPlayerSP).posY + entityPlayerSP.getEyeHeight(), ((EntityPlayer)entityPlayerSP).posZ, 
/* 1367 */           "random.successful_hit", 0.18F, 0.45F, false);
/* 1368 */     } else if (i == 7) {
/* 1369 */       this.clientWorldController.setRainStrength(f);
/* 1370 */     } else if (i == 8) {
/* 1371 */       this.clientWorldController.setThunderStrength(f);
/* 1372 */     } else if (i == 10) {
/* 1373 */       this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, ((EntityPlayer)entityPlayerSP).posX, 
/* 1374 */           ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, 0.0D, 0.0D, 0.0D, new int[0]);
/* 1375 */       this.clientWorldController.playSound(((EntityPlayer)entityPlayerSP).posX, ((EntityPlayer)entityPlayerSP).posY, ((EntityPlayer)entityPlayerSP).posZ, 
/* 1376 */           "mob.guardian.curse", 1.0F, 1.0F, false);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleMaps(S34PacketMaps packetIn) {
/* 1385 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1386 */     MapData mapdata = ItemMap.loadMapData(packetIn.getMapId(), (World)this.gameController.theWorld);
/* 1387 */     packetIn.setMapdataTo(mapdata);
/* 1388 */     this.gameController.entityRenderer.getMapItemRenderer().updateMapTexture(mapdata);
/*      */   }
/*      */   
/*      */   public void handleEffect(S28PacketEffect packetIn) {
/* 1392 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1394 */     if (packetIn.isSoundServerwide()) {
/* 1395 */       this.gameController.theWorld.playBroadcastSound(packetIn.getSoundType(), packetIn.getSoundPos(), 
/* 1396 */           packetIn.getSoundData());
/*      */     } else {
/* 1398 */       this.gameController.theWorld.playAuxSFX(packetIn.getSoundType(), packetIn.getSoundPos(), 
/* 1399 */           packetIn.getSoundData());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleStatistics(S37PacketStatistics packetIn) {
/* 1407 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1408 */     boolean flag = false;
/*      */     
/* 1410 */     for (Map.Entry<StatBase, Integer> entry : (Iterable<Map.Entry<StatBase, Integer>>)packetIn.func_148974_c().entrySet()) {
/* 1411 */       StatBase statbase = entry.getKey();
/* 1412 */       int i = ((Integer)entry.getValue()).intValue();
/*      */       
/* 1414 */       if (statbase.isAchievement() && i > 0) {
/* 1415 */         if (this.field_147308_k && this.gameController.thePlayer.getStatFileWriter().readStat(statbase) == 0) {
/* 1416 */           Achievement achievement = (Achievement)statbase;
/* 1417 */           this.gameController.guiAchievement.displayAchievement(achievement);
/* 1418 */           this.gameController.getTwitchStream().func_152911_a((Metadata)new MetadataAchievement(achievement), 0L);
/*      */           
/* 1420 */           if (statbase == AchievementList.openInventory) {
/* 1421 */             this.gameController.gameSettings.showInventoryAchievementHint = false;
/* 1422 */             this.gameController.gameSettings.saveOptions();
/*      */           } 
/*      */         } 
/*      */         
/* 1426 */         flag = true;
/*      */       } 
/*      */       
/* 1429 */       this.gameController.thePlayer.getStatFileWriter().unlockAchievement((EntityPlayer)this.gameController.thePlayer, statbase, 
/* 1430 */           i);
/*      */     } 
/*      */     
/* 1433 */     if (!this.field_147308_k && !flag && this.gameController.gameSettings.showInventoryAchievementHint) {
/* 1434 */       this.gameController.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
/*      */     }
/*      */     
/* 1437 */     this.field_147308_k = true;
/*      */     
/* 1439 */     if (this.gameController.currentScreen instanceof IProgressMeter) {
/* 1440 */       ((IProgressMeter)this.gameController.currentScreen).doneLoading();
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleEntityEffect(S1DPacketEntityEffect packetIn) {
/* 1445 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1446 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1448 */     if (entity instanceof EntityLivingBase) {
/* 1449 */       PotionEffect potioneffect = new PotionEffect(packetIn.getEffectId(), packetIn.getDuration(), 
/* 1450 */           packetIn.getAmplifier(), false, packetIn.func_179707_f());
/* 1451 */       potioneffect.setPotionDurationMax(packetIn.func_149429_c());
/* 1452 */       ((EntityLivingBase)entity).addPotionEffect(potioneffect);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleCombatEvent(S42PacketCombatEvent packetIn) {
/* 1457 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1458 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.field_179775_c);
/* 1459 */     EntityLivingBase entitylivingbase = (entity instanceof EntityLivingBase) ? (EntityLivingBase)entity : null;
/*      */     
/* 1461 */     if (packetIn.eventType == S42PacketCombatEvent.Event.END_COMBAT) {
/* 1462 */       long i = (1000 * packetIn.field_179772_d / 20);
/* 1463 */       MetadataCombat metadatacombat = new MetadataCombat((EntityLivingBase)this.gameController.thePlayer, entitylivingbase);
/* 1464 */       this.gameController.getTwitchStream().func_176026_a((Metadata)metadatacombat, 0L - i, 0L);
/* 1465 */     } else if (packetIn.eventType == S42PacketCombatEvent.Event.ENTITY_DIED) {
/* 1466 */       Entity entity1 = this.clientWorldController.getEntityByID(packetIn.field_179774_b);
/*      */       
/* 1468 */       if (entity1 instanceof EntityPlayer) {
/* 1469 */         MetadataPlayerDeath metadataplayerdeath = new MetadataPlayerDeath((EntityLivingBase)entity1, 
/* 1470 */             entitylivingbase);
/* 1471 */         metadataplayerdeath.func_152807_a(packetIn.deathMessage);
/* 1472 */         this.gameController.getTwitchStream().func_152911_a((Metadata)metadataplayerdeath, 0L);
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleServerDifficulty(S41PacketServerDifficulty packetIn) {
/* 1478 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1479 */     this.gameController.theWorld.getWorldInfo().setDifficulty(packetIn.getDifficulty());
/* 1480 */     this.gameController.theWorld.getWorldInfo().setDifficultyLocked(packetIn.isDifficultyLocked());
/*      */   }
/*      */   
/*      */   public void handleCamera(S43PacketCamera packetIn) {
/* 1484 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1485 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1487 */     if (entity != null) {
/* 1488 */       this.gameController.setRenderViewEntity(entity);
/*      */     }
/*      */   }
/*      */   
/*      */   public void handleWorldBorder(S44PacketWorldBorder packetIn) {
/* 1493 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1494 */     packetIn.func_179788_a(this.clientWorldController.getWorldBorder());
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleTitle(S45PacketTitle packetIn) {
/* 1499 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1500 */     S45PacketTitle.Type s45packettitle$type = packetIn.getType();
/* 1501 */     String s = null;
/* 1502 */     String s1 = null;
/* 1503 */     String s2 = (packetIn.getMessage() != null) ? packetIn.getMessage().getFormattedText() : "";
/*      */     
/* 1505 */     switch (s45packettitle$type) {
/*      */       case TITLE:
/* 1507 */         s = s2;
/*      */         break;
/*      */       
/*      */       case SUBTITLE:
/* 1511 */         s1 = s2;
/*      */         break;
/*      */       
/*      */       case RESET:
/* 1515 */         this.gameController.ingameGUI.displayTitle("", "", -1, -1, -1);
/* 1516 */         this.gameController.ingameGUI.func_175177_a();
/*      */         return;
/*      */     } 
/*      */     
/* 1520 */     this.gameController.ingameGUI.displayTitle(s, s1, packetIn.getFadeInTime(), packetIn.getDisplayTime(), 
/* 1521 */         packetIn.getFadeOutTime());
/*      */   }
/*      */   
/*      */   public void handleSetCompressionLevel(S46PacketSetCompressionLevel packetIn) {
/* 1525 */     if (!this.netManager.isLocalChannel()) {
/* 1526 */       this.netManager.setCompressionTreshold(packetIn.func_179760_a());
/*      */     }
/*      */   }
/*      */   
/*      */   public void handlePlayerListHeaderFooter(S47PacketPlayerListHeaderFooter packetIn) {
/* 1531 */     this.gameController.ingameGUI.getTabList()
/* 1532 */       .setHeader((packetIn.getHeader().getFormattedText().length() == 0) ? null : packetIn.getHeader());
/* 1533 */     this.gameController.ingameGUI.getTabList()
/* 1534 */       .setFooter((packetIn.getFooter().getFormattedText().length() == 0) ? null : packetIn.getFooter());
/*      */   }
/*      */   
/*      */   public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect packetIn) {
/* 1538 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1539 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1541 */     if (entity instanceof EntityLivingBase) {
/* 1542 */       ((EntityLivingBase)entity).removePotionEffectClient(packetIn.getEffectId());
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void handlePlayerListItem(S38PacketPlayerListItem packetIn) {
/* 1548 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1550 */     for (S38PacketPlayerListItem.AddPlayerData s38packetplayerlistitem$addplayerdata : packetIn.func_179767_a()) {
/* 1551 */       if (packetIn.func_179768_b() == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
/* 1552 */         this.playerInfoMap.remove(s38packetplayerlistitem$addplayerdata.getProfile().getId()); continue;
/*      */       } 
/* 1554 */       NetworkPlayerInfo networkplayerinfo = this.playerInfoMap
/* 1555 */         .get(s38packetplayerlistitem$addplayerdata.getProfile().getId());
/*      */       
/* 1557 */       if (packetIn.func_179768_b() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
/* 1558 */         networkplayerinfo = new NetworkPlayerInfo(s38packetplayerlistitem$addplayerdata);
/* 1559 */         this.playerInfoMap.put(networkplayerinfo.getGameProfile().getId(), networkplayerinfo);
/*      */       } 
/*      */       
/* 1562 */       if (networkplayerinfo != null) {
/* 1563 */         switch (packetIn.func_179768_b()) {
/*      */           case null:
/* 1565 */             networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
/* 1566 */             networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_GAME_MODE:
/* 1570 */             networkplayerinfo.setGameType(s38packetplayerlistitem$addplayerdata.getGameMode());
/*      */ 
/*      */           
/*      */           case UPDATE_LATENCY:
/* 1574 */             networkplayerinfo.setResponseTime(s38packetplayerlistitem$addplayerdata.getPing());
/*      */ 
/*      */           
/*      */           case UPDATE_DISPLAY_NAME:
/* 1578 */             networkplayerinfo.setDisplayName(s38packetplayerlistitem$addplayerdata.getDisplayName());
/*      */         } 
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleKeepAlive(S00PacketKeepAlive packetIn) {
/* 1586 */     addToSendQueue((Packet)new C00PacketKeepAlive(packetIn.func_149134_c()));
/*      */   }
/*      */   
/*      */   public void handlePlayerAbilities(S39PacketPlayerAbilities packetIn) {
/* 1590 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1591 */     EntityPlayerSP entityPlayerSP = this.gameController.thePlayer;
/* 1592 */     ((EntityPlayer)entityPlayerSP).capabilities.isFlying = packetIn.isFlying();
/* 1593 */     ((EntityPlayer)entityPlayerSP).capabilities.isCreativeMode = packetIn.isCreativeMode();
/* 1594 */     ((EntityPlayer)entityPlayerSP).capabilities.disableDamage = packetIn.isInvulnerable();
/* 1595 */     ((EntityPlayer)entityPlayerSP).capabilities.allowFlying = packetIn.isAllowFlying();
/* 1596 */     ((EntityPlayer)entityPlayerSP).capabilities.setFlySpeed(packetIn.getFlySpeed());
/* 1597 */     ((EntityPlayer)entityPlayerSP).capabilities.setPlayerWalkSpeed(packetIn.getWalkSpeed());
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTabComplete(S3APacketTabComplete packetIn) {
/* 1604 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1605 */     String[] astring = packetIn.func_149630_c();
/*      */     
/* 1607 */     if (this.gameController.currentScreen instanceof GuiChat) {
/* 1608 */       GuiChat guichat = (GuiChat)this.gameController.currentScreen;
/* 1609 */       guichat.onAutocompleteResponse(astring);
/*      */     } 
/*      */   }
/*      */   
/*      */   public void handleSoundEffect(S29PacketSoundEffect packetIn) {
/* 1614 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1615 */     this.gameController.theWorld.playSound(packetIn.getX(), packetIn.getY(), packetIn.getZ(), 
/* 1616 */         packetIn.getSoundName(), packetIn.getVolume(), packetIn.getPitch(), false);
/*      */   }
/*      */   
/*      */   public void handleResourcePack(S48PacketResourcePackSend packetIn) {
/* 1620 */     final String s = packetIn.getURL();
/* 1621 */     final String s1 = packetIn.getHash();
/*      */     
/* 1623 */     if (s.startsWith("level://")) {
/* 1624 */       String s2 = s.substring("level://".length());
/* 1625 */       File file1 = new File(this.gameController.mcDataDir, "saves");
/* 1626 */       File file2 = new File(file1, s2);
/*      */       
/* 1628 */       if (file2.isFile()) {
/* 1629 */         this.netManager
/* 1630 */           .sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1631 */         Futures.addCallback(this.gameController.getResourcePackRepository().setResourcePackInstance(file2), 
/* 1632 */             new FutureCallback<Object>() {
/*      */               public void onSuccess(Object p_onSuccess_1_) {
/* 1634 */                 NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, 
/* 1635 */                       C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */               }
/*      */               
/*      */               public void onFailure(Throwable p_onFailure_1_) {
/* 1639 */                 NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, 
/* 1640 */                       C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */               }
/*      */             });
/*      */       } else {
/* 1644 */         this.netManager.sendPacket(
/* 1645 */             (Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */       }
/*      */     
/* 1648 */     } else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData()
/* 1649 */       .getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
/* 1650 */       this.netManager
/* 1651 */         .sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1652 */       Futures.addCallback(this.gameController.getResourcePackRepository().downloadResourcePack(s, s1), 
/* 1653 */           new FutureCallback<Object>() {
/*      */             public void onSuccess(Object p_onSuccess_1_) {
/* 1655 */               NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, 
/* 1656 */                     C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */             }
/*      */             
/*      */             public void onFailure(Throwable p_onFailure_1_) {
/* 1660 */               NetHandlerPlayClient.this.netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, 
/* 1661 */                     C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */             }
/*      */           });
/* 1664 */     } else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData()
/* 1665 */       .getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
/* 1666 */       this.netManager
/* 1667 */         .sendPacket((Packet)new C19PacketResourcePackStatus(s1, C19PacketResourcePackStatus.Action.DECLINED));
/*      */     } else {
/* 1669 */       this.gameController.addScheduledTask(new Runnable() {
/*      */             public void run() {
/* 1671 */               NetHandlerPlayClient.this.gameController.displayGuiScreen((GuiScreen)new GuiYesNo(new GuiYesNoCallback() {
/*      */                       public void confirmClicked(boolean result, int id) {
/* 1673 */                         (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController = Minecraft.getMinecraft();
/*      */                         
/* 1675 */                         if (result) {
/* 1676 */                           if ((NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData() != null) {
/* 1677 */                             (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData()
/* 1678 */                               .setResourceMode(ServerData.ServerResourceMode.ENABLED);
/*      */                           }
/*      */                           
/* 1681 */                           (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, 
/* 1682 */                                 C19PacketResourcePackStatus.Action.ACCEPTED));
/* 1683 */                           Futures.addCallback((NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController
/* 1684 */                               .getResourcePackRepository().downloadResourcePack(s, s1), 
/* 1685 */                               new FutureCallback<Object>() {
/*      */                                 public void onSuccess(Object p_onSuccess_1_) {
/* 1687 */                                   (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.null.access$0(NetHandlerPlayClient.null.null.this))).netManager
/* 1688 */                                     .sendPacket((Packet)new C19PacketResourcePackStatus(s1, 
/* 1689 */                                         C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
/*      */                                 }
/*      */                                 
/*      */                                 public void onFailure(Throwable p_onFailure_1_) {
/* 1693 */                                   (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.null.access$0(NetHandlerPlayClient.null.null.this))).netManager
/* 1694 */                                     .sendPacket((Packet)new C19PacketResourcePackStatus(s1, 
/* 1695 */                                         C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
/*      */                                 }
/*      */                               });
/*      */                         } else {
/* 1699 */                           if ((NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData() != null) {
/* 1700 */                             (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData()
/* 1701 */                               .setResourceMode(ServerData.ServerResourceMode.DISABLED);
/*      */                           }
/*      */                           
/* 1704 */                           (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).netManager.sendPacket((Packet)new C19PacketResourcePackStatus(s1, 
/* 1705 */                                 C19PacketResourcePackStatus.Action.DECLINED));
/*      */                         } 
/*      */ 
/*      */                         
/* 1709 */                         ServerList.func_147414_b((NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.getCurrentServerData());
/* 1710 */                         (NetHandlerPlayClient.null.access$0(NetHandlerPlayClient.null.this)).gameController.displayGuiScreen(null);
/*      */                       }
/* 1712 */                     }I18n.format("multiplayer.texturePrompt.line1", new Object[0]), 
/* 1713 */                     I18n.format("multiplayer.texturePrompt.line2", new Object[0]), 0));
/*      */             }
/*      */           });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleEntityNBT(S49PacketUpdateEntityNBT packetIn) {
/* 1721 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1722 */     Entity entity = packetIn.getEntity((World)this.clientWorldController);
/*      */     
/* 1724 */     if (entity != null) {
/* 1725 */       entity.clientUpdateEntityNBT(packetIn.getTagCompound());
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleCustomPayload(S3FPacketCustomPayload packetIn) {
/* 1737 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1739 */     if ("MC|TrList".equals(packetIn.getChannelName())) {
/* 1740 */       PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */       
/*      */       try {
/* 1743 */         int i = packetbuffer.readInt();
/* 1744 */         GuiScreen guiscreen = this.gameController.currentScreen;
/*      */         
/* 1746 */         if (guiscreen != null && guiscreen instanceof GuiMerchant && 
/* 1747 */           i == this.gameController.thePlayer.openContainer.windowId) {
/* 1748 */           IMerchant imerchant = ((GuiMerchant)guiscreen).getMerchant();
/* 1749 */           MerchantRecipeList merchantrecipelist = MerchantRecipeList.readFromBuf(packetbuffer);
/* 1750 */           imerchant.setRecipes(merchantrecipelist);
/*      */         } 
/* 1752 */       } catch (IOException ioexception) {
/* 1753 */         logger.error("Couldn't load trade info", ioexception);
/*      */       } finally {
/* 1755 */         packetbuffer.release();
/*      */       } 
/* 1757 */     } else if ("MC|Brand".equals(packetIn.getChannelName())) {
/* 1758 */       this.gameController.thePlayer.setClientBrand(packetIn.getBufferData().readStringFromBuffer(32767));
/* 1759 */     } else if ("MC|BOpen".equals(packetIn.getChannelName())) {
/* 1760 */       ItemStack itemstack = this.gameController.thePlayer.getCurrentEquippedItem();
/*      */       
/* 1762 */       if (itemstack != null && itemstack.getItem() == Items.written_book) {
/* 1763 */         this.gameController
/* 1764 */           .displayGuiScreen((GuiScreen)new GuiScreenBook((EntityPlayer)this.gameController.thePlayer, itemstack, false));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleScoreboardObjective(S3BPacketScoreboardObjective packetIn) {
/* 1774 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1775 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 1777 */     if (packetIn.func_149338_e() == 0) {
/* 1778 */       ScoreObjective scoreobjective = scoreboard.addScoreObjective(packetIn.func_149339_c(), 
/* 1779 */           IScoreObjectiveCriteria.DUMMY);
/* 1780 */       scoreobjective.setDisplayName(packetIn.func_149337_d());
/* 1781 */       scoreobjective.setRenderType(packetIn.func_179817_d());
/*      */     } else {
/* 1783 */       ScoreObjective scoreobjective1 = scoreboard.getObjective(packetIn.func_149339_c());
/*      */       
/* 1785 */       if (packetIn.func_149338_e() == 1) {
/* 1786 */         scoreboard.removeObjective(scoreobjective1);
/* 1787 */       } else if (packetIn.func_149338_e() == 2) {
/* 1788 */         scoreobjective1.setDisplayName(packetIn.func_149337_d());
/* 1789 */         scoreobjective1.setRenderType(packetIn.func_179817_d());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleUpdateScore(S3CPacketUpdateScore packetIn) {
/* 1799 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1800 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/* 1801 */     ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.getObjectiveName());
/*      */     
/* 1803 */     if (packetIn.getScoreAction() == S3CPacketUpdateScore.Action.CHANGE) {
/* 1804 */       Score score = scoreboard.getValueFromObjective(packetIn.getPlayerName(), scoreobjective);
/* 1805 */       score.setScorePoints(packetIn.getScoreValue());
/* 1806 */     } else if (packetIn.getScoreAction() == S3CPacketUpdateScore.Action.REMOVE) {
/* 1807 */       if (StringUtils.isNullOrEmpty(packetIn.getObjectiveName())) {
/* 1808 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), null);
/* 1809 */       } else if (scoreobjective != null) {
/* 1810 */         scoreboard.removeObjectiveFromEntity(packetIn.getPlayerName(), scoreobjective);
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleDisplayScoreboard(S3DPacketDisplayScoreboard packetIn) {
/* 1820 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1821 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */     
/* 1823 */     if (packetIn.func_149370_d().length() == 0) {
/* 1824 */       scoreboard.setObjectiveInDisplaySlot(packetIn.func_149371_c(), null);
/*      */     } else {
/* 1826 */       ScoreObjective scoreobjective = scoreboard.getObjective(packetIn.func_149370_d());
/* 1827 */       scoreboard.setObjectiveInDisplaySlot(packetIn.func_149371_c(), scoreobjective);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleTeams(S3EPacketTeams packetIn) {
/*      */     ScorePlayerTeam scoreplayerteam;
/* 1837 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1838 */     Scoreboard scoreboard = this.clientWorldController.getScoreboard();
/*      */ 
/*      */     
/* 1841 */     if (packetIn.func_149307_h() == 0) {
/* 1842 */       scoreplayerteam = scoreboard.createTeam(packetIn.func_149312_c());
/*      */     } else {
/* 1844 */       scoreplayerteam = scoreboard.getTeam(packetIn.func_149312_c());
/*      */     } 
/*      */     
/* 1847 */     if (packetIn.func_149307_h() == 0 || packetIn.func_149307_h() == 2) {
/* 1848 */       scoreplayerteam.setTeamName(packetIn.func_149306_d());
/* 1849 */       scoreplayerteam.setNamePrefix(packetIn.func_149311_e());
/* 1850 */       scoreplayerteam.setNameSuffix(packetIn.func_149309_f());
/* 1851 */       scoreplayerteam.setChatFormat(EnumChatFormatting.func_175744_a(packetIn.func_179813_h()));
/* 1852 */       scoreplayerteam.func_98298_a(packetIn.func_149308_i());
/* 1853 */       Team.EnumVisible team$enumvisible = Team.EnumVisible.func_178824_a(packetIn.func_179814_i());
/*      */       
/* 1855 */       if (team$enumvisible != null) {
/* 1856 */         scoreplayerteam.setNameTagVisibility(team$enumvisible);
/*      */       }
/*      */     } 
/*      */     
/* 1860 */     if (packetIn.func_149307_h() == 0 || packetIn.func_149307_h() == 3) {
/* 1861 */       for (String s : packetIn.func_149310_g()) {
/* 1862 */         scoreboard.addPlayerToTeam(s, packetIn.func_149312_c());
/*      */       }
/*      */     }
/*      */     
/* 1866 */     if (packetIn.func_149307_h() == 4) {
/* 1867 */       for (String s1 : packetIn.func_149310_g()) {
/* 1868 */         scoreboard.removePlayerFromTeam(s1, scoreplayerteam);
/*      */       }
/*      */     }
/*      */     
/* 1872 */     if (packetIn.func_149307_h() == 1) {
/* 1873 */       scoreboard.removeTeam(scoreplayerteam);
/*      */     }
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleParticles(S2APacketParticles packetIn) {
/* 1882 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/*      */     
/* 1884 */     if (packetIn.getParticleCount() == 0) {
/* 1885 */       double d0 = (packetIn.getParticleSpeed() * packetIn.getXOffset());
/* 1886 */       double d2 = (packetIn.getParticleSpeed() * packetIn.getYOffset());
/* 1887 */       double d4 = (packetIn.getParticleSpeed() * packetIn.getZOffset());
/*      */       
/*      */       try {
/* 1890 */         this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), 
/* 1891 */             packetIn.getXCoordinate(), packetIn.getYCoordinate(), packetIn.getZCoordinate(), d0, d2, d4, 
/* 1892 */             packetIn.getParticleArgs());
/* 1893 */       } catch (Throwable var17) {
/* 1894 */         logger.warn("Could not spawn particle effect " + packetIn.getParticleType());
/*      */       } 
/*      */     } else {
/* 1897 */       for (int i = 0; i < packetIn.getParticleCount(); i++) {
/* 1898 */         double d1 = this.avRandomizer.nextGaussian() * packetIn.getXOffset();
/* 1899 */         double d3 = this.avRandomizer.nextGaussian() * packetIn.getYOffset();
/* 1900 */         double d5 = this.avRandomizer.nextGaussian() * packetIn.getZOffset();
/* 1901 */         double d6 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 1902 */         double d7 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/* 1903 */         double d8 = this.avRandomizer.nextGaussian() * packetIn.getParticleSpeed();
/*      */         
/*      */         try {
/* 1906 */           this.clientWorldController.spawnParticle(packetIn.getParticleType(), packetIn.isLongDistance(), 
/* 1907 */               packetIn.getXCoordinate() + d1, packetIn.getYCoordinate() + d3, 
/* 1908 */               packetIn.getZCoordinate() + d5, d6, d7, d8, packetIn.getParticleArgs());
/* 1909 */         } catch (Throwable var16) {
/* 1910 */           logger.warn("Could not spawn particle effect " + packetIn.getParticleType());
/*      */           return;
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleEntityProperties(S20PacketEntityProperties packetIn) {
/* 1924 */     PacketThreadUtil.checkThreadAndEnqueue((Packet)packetIn, (INetHandler)this, (IThreadListener)this.gameController);
/* 1925 */     Entity entity = this.clientWorldController.getEntityByID(packetIn.getEntityId());
/*      */     
/* 1927 */     if (entity != null) {
/* 1928 */       if (!(entity instanceof EntityLivingBase)) {
/* 1929 */         throw new IllegalStateException(
/* 1930 */             "Server tried to update attributes of a non-living entity (actually: " + entity + ")");
/*      */       }
/* 1932 */       BaseAttributeMap baseattributemap = ((EntityLivingBase)entity).getAttributeMap();
/*      */       
/* 1934 */       for (S20PacketEntityProperties.Snapshot s20packetentityproperties$snapshot : packetIn.func_149441_d()) {
/* 1935 */         IAttributeInstance iattributeinstance = baseattributemap
/* 1936 */           .getAttributeInstanceByName(s20packetentityproperties$snapshot.func_151409_a());
/*      */         
/* 1938 */         if (iattributeinstance == null) {
/* 1939 */           iattributeinstance = baseattributemap.registerAttribute((IAttribute)new RangedAttribute(null, 
/* 1940 */                 s20packetentityproperties$snapshot.func_151409_a(), 0.0D, 2.2250738585072014E-308D, 
/* 1941 */                 Double.MAX_VALUE));
/*      */         }
/*      */         
/* 1944 */         iattributeinstance.setBaseValue(s20packetentityproperties$snapshot.func_151410_b());
/* 1945 */         iattributeinstance.removeAllModifiers();
/*      */         
/* 1947 */         for (AttributeModifier attributemodifier : s20packetentityproperties$snapshot.func_151408_c()) {
/* 1948 */           iattributeinstance.applyModifier(attributemodifier);
/*      */         }
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/* 1960 */     return this.netManager;
/*      */   }
/*      */   
/*      */   public Collection<NetworkPlayerInfo> getPlayerInfoMap() {
/* 1964 */     return this.playerInfoMap.values();
/*      */   }
/*      */   
/*      */   public NetworkPlayerInfo getPlayerInfo(UUID p_175102_1_) {
/* 1968 */     return this.playerInfoMap.get(p_175102_1_);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public NetworkPlayerInfo getPlayerInfo(String p_175104_1_) {
/* 1975 */     for (NetworkPlayerInfo networkplayerinfo : this.playerInfoMap.values()) {
/* 1976 */       if (networkplayerinfo.getGameProfile().getName().equals(p_175104_1_)) {
/* 1977 */         return networkplayerinfo;
/*      */       }
/*      */     } 
/*      */     
/* 1981 */     return null;
/*      */   }
/*      */   
/*      */   public GameProfile getGameProfile() {
/* 1985 */     return this.profile;
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\network\NetHandlerPlayClient.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */