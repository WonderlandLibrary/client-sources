package net.minecraft.client.network;

import com.google.common.collect.Maps;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.mojang.authlib.GameProfile;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;
import java.util.Map.Entry;
import net.minecraft.block.Block;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.GuardianSound;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.GuiDisconnected;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMerchant;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiScreenBook;
import net.minecraft.client.gui.GuiScreenDemo;
import net.minecraft.client.gui.GuiScreenRealmsProxy;
import net.minecraft.client.gui.GuiWinGame;
import net.minecraft.client.gui.GuiYesNo;
import net.minecraft.client.gui.GuiYesNoCallback;
import net.minecraft.client.gui.IProgressMeter;
import net.minecraft.client.gui.inventory.GuiContainerCreative;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.ServerData;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.multiplayer.WorldClient;
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
import net.minecraft.entity.IMerchant;
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
import net.minecraft.init.Items;
import net.minecraft.inventory.AnimalChest;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayClient;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S01PacketJoinGame;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S03PacketTimeUpdate;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S05PacketSpawnPosition;
import net.minecraft.network.play.server.S06PacketUpdateHealth;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
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
import net.minecraft.network.play.server.S21PacketChunkData;
import net.minecraft.network.play.server.S22PacketMultiBlockChange;
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
import net.minecraft.network.play.server.S39PacketPlayerAbilities;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
import net.minecraft.network.play.server.S3CPacketUpdateScore;
import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
import net.minecraft.network.play.server.S3EPacketTeams;
import net.minecraft.network.play.server.S3FPacketCustomPayload;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.network.play.server.S41PacketServerDifficulty;
import net.minecraft.network.play.server.S42PacketCombatEvent;
import net.minecraft.network.play.server.S43PacketCamera;
import net.minecraft.network.play.server.S44PacketWorldBorder;
import net.minecraft.network.play.server.S45PacketTitle;
import net.minecraft.network.play.server.S46PacketSetCompressionLevel;
import net.minecraft.network.play.server.S47PacketPlayerListHeaderFooter;
import net.minecraft.network.play.server.S48PacketResourcePackSend;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.potion.PotionEffect;
import net.minecraft.realms.DisconnectedRealmsScreen;
import net.minecraft.scoreboard.IScoreObjectiveCriteria;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.ScorePlayerTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
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
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.Explosion;
import net.minecraft.world.WorldProviderSurface;
import net.minecraft.world.WorldSettings;
import net.minecraft.world.chunk.Chunk;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayClient implements INetHandlerPlayClient {
   private final Map playerInfoMap = Maps.newHashMap();
   private static final String __OBFID = "CL_00000878";
   private final NetworkManager netManager;
   private static final Logger logger = LogManager.getLogger();
   private final GameProfile field_175107_d;
   private Minecraft gameController;
   public int currentServerMaxPlayers = 20;
   private final Random avRandomizer = new Random();
   private boolean field_147308_k = false;
   private final GuiScreen guiScreenServer;
   private boolean doneLoadingTerrain;
   private WorldClient clientWorldController;

   static NetworkManager access$0(NetHandlerPlayClient var0) {
      return var0.netManager;
   }

   public void handleEntityTeleport(S18PacketEntityTeleport var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.func_149451_c());
      if (var2 != null) {
         var2.serverPosX = var1.func_149449_d();
         var2.serverPosY = var1.func_149448_e();
         var2.serverPosZ = var1.func_149446_f();
         double var3 = (double)var2.serverPosX / 32.0D;
         double var5 = (double)var2.serverPosY / 32.0D + 0.015625D;
         double var7 = (double)var2.serverPosZ / 32.0D;
         float var9 = (float)(var1.func_149450_g() * 360) / 256.0F;
         float var10 = (float)(var1.func_149447_h() * 360) / 256.0F;
         if (Math.abs(var2.posX - var3) < 0.03125D && Math.abs(var2.posY - var5) < 0.015625D && Math.abs(var2.posZ - var7) < 0.03125D) {
            var2.func_180426_a(var2.posX, var2.posY, var2.posZ, var9, var10, 3, true);
         } else {
            var2.func_180426_a(var3, var5, var7, var9, var10, 3, true);
         }

         var2.onGround = var1.func_179697_g();
      }

   }

   public void handleEntityMovement(S14PacketEntity var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = var1.func_149065_a(this.clientWorldController);
      if (var2 != null) {
         var2.serverPosX += var1.func_149062_c();
         var2.serverPosY += var1.func_149061_d();
         var2.serverPosZ += var1.func_149064_e();
         double var3 = (double)var2.serverPosX / 32.0D;
         double var5 = (double)var2.serverPosY / 32.0D;
         double var7 = (double)var2.serverPosZ / 32.0D;
         float var9 = var1.func_149060_h() ? (float)(var1.func_149066_f() * 360) / 256.0F : var2.rotationYaw;
         float var10 = var1.func_149060_h() ? (float)(var1.func_149063_g() * 360) / 256.0F : var2.rotationPitch;
         var2.func_180426_a(var3, var5, var7, var9, var10, 3, false);
         var2.onGround = var1.func_179742_g();
      }

   }

   public void func_175101_a(S41PacketServerDifficulty var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.theWorld.getWorldInfo().setDifficulty(var1.func_179831_b());
      this.gameController.theWorld.getWorldInfo().setDifficultyLocked(var1.func_179830_a());
   }

   public void handleWindowItems(S30PacketWindowItems var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.thePlayer;
      if (var1.func_148911_c() == 0) {
         var2.inventoryContainer.putStacksInSlots(var1.func_148910_d());
      } else if (var1.func_148911_c() == var2.openContainer.windowId) {
         var2.openContainer.putStacksInSlots(var1.func_148910_d());
      }

   }

   public void handleTeams(S3EPacketTeams var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Scoreboard var2 = this.clientWorldController.getScoreboard();
      ScorePlayerTeam var3;
      if (var1.func_149307_h() == 0) {
         var3 = var2.createTeam(var1.func_149312_c());
      } else {
         var3 = var2.getTeam(var1.func_149312_c());
      }

      if (var1.func_149307_h() == 0 || var1.func_149307_h() == 2) {
         var3.setTeamName(var1.func_149306_d());
         var3.setNamePrefix(var1.func_149311_e());
         var3.setNameSuffix(var1.func_149309_f());
         var3.func_178774_a(EnumChatFormatting.func_175744_a(var1.func_179813_h()));
         var3.func_98298_a(var1.func_149308_i());
         Team.EnumVisible var4 = Team.EnumVisible.func_178824_a(var1.func_179814_i());
         if (var4 != null) {
            var3.func_178772_a(var4);
         }
      }

      Iterator var5;
      String var6;
      if (var1.func_149307_h() == 0 || var1.func_149307_h() == 3) {
         var5 = var1.func_149310_g().iterator();

         while(var5.hasNext()) {
            var6 = (String)var5.next();
            var2.func_151392_a(var6, var1.func_149312_c());
         }
      }

      if (var1.func_149307_h() == 4) {
         var5 = var1.func_149310_g().iterator();

         while(var5.hasNext()) {
            var6 = (String)var5.next();
            var2.removePlayerFromTeam(var6, var3);
         }
      }

      if (var1.func_149307_h() == 1) {
         var2.removeTeam(var3);
      }

   }

   public void handleSpawnMob(S0FPacketSpawnMob var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      double var2 = (double)var1.func_149023_f() / 32.0D;
      double var4 = (double)var1.func_149034_g() / 32.0D;
      double var6 = (double)var1.func_149029_h() / 32.0D;
      float var8 = (float)(var1.func_149028_l() * 360) / 256.0F;
      float var9 = (float)(var1.func_149030_m() * 360) / 256.0F;
      EntityLivingBase var10 = (EntityLivingBase)EntityList.createEntityByID(var1.func_149025_e(), this.gameController.theWorld);
      var10.serverPosX = var1.func_149023_f();
      var10.serverPosY = var1.func_149034_g();
      var10.serverPosZ = var1.func_149029_h();
      var10.rotationYawHead = (float)(var1.func_149032_n() * 360) / 256.0F;
      Entity[] var11 = var10.getParts();
      if (var11 != null) {
         int var12 = var1.func_149024_d() - var10.getEntityId();

         for(int var13 = 0; var13 < var11.length; ++var13) {
            var11[var13].setEntityId(var11[var13].getEntityId() + var12);
         }
      }

      var10.setEntityId(var1.func_149024_d());
      var10.setPositionAndRotation(var2, var4, var6, var8, var9);
      var10.motionX = (double)((float)var1.func_149026_i() / 8000.0F);
      var10.motionY = (double)((float)var1.func_149033_j() / 8000.0F);
      var10.motionZ = (double)((float)var1.func_149031_k() / 8000.0F);
      this.clientWorldController.addEntityToWorld(var1.func_149024_d(), var10);
      List var14 = var1.func_149027_c();
      if (var14 != null) {
         var10.getDataWatcher().updateWatchedObjectsFromList(var14);
      }

   }

   public void handleHeldItemChange(S09PacketHeldItemChange var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      if (var1.func_149385_c() >= 0 && var1.func_149385_c() < InventoryPlayer.getHotbarSize()) {
         this.gameController.thePlayer.inventory.currentItem = var1.func_149385_c();
      }

   }

   public void handleConfirmTransaction(S32PacketConfirmTransaction var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Container var2 = null;
      EntityPlayerSP var3 = this.gameController.thePlayer;
      if (var1.func_148889_c() == 0) {
         var2 = var3.inventoryContainer;
      } else if (var1.func_148889_c() == var3.openContainer.windowId) {
         var2 = var3.openContainer;
      }

      if (var2 != null && !var1.func_148888_e()) {
         this.addToSendQueue(new C0FPacketConfirmTransaction(var1.func_148889_c(), var1.func_148890_d(), true));
      }

   }

   public void handleBlockAction(S24PacketBlockAction var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.theWorld.addBlockEvent(var1.func_179825_a(), var1.getBlockType(), var1.getData1(), var1.getData2());
   }

   public void func_175098_a(S42PacketCombatEvent var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.field_179775_c);
      EntityLivingBase var3 = var2 instanceof EntityLivingBase ? (EntityLivingBase)var2 : null;
      if (var1.field_179776_a == S42PacketCombatEvent.Event.END_COMBAT) {
         long var4 = (long)(1000 * var1.field_179772_d / 20);
         MetadataCombat var6 = new MetadataCombat(this.gameController.thePlayer, var3);
         this.gameController.getTwitchStream().func_176026_a(var6, 0L - var4, 0L);
      } else if (var1.field_179776_a == S42PacketCombatEvent.Event.ENTITY_DIED) {
         Entity var7 = this.clientWorldController.getEntityByID(var1.field_179774_b);
         if (var7 instanceof EntityPlayer) {
            MetadataPlayerDeath var5 = new MetadataPlayerDeath((EntityPlayer)var7, var3);
            var5.func_152807_a(var1.field_179773_e);
            this.gameController.getTwitchStream().func_152911_a(var5, 0L);
         }
      }

   }

   public void handleEntityEffect(S1DPacketEntityEffect var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.func_149426_d());
      if (var2 instanceof EntityLivingBase) {
         PotionEffect var3 = new PotionEffect(var1.func_149427_e(), var1.func_180755_e(), var1.func_149428_f(), false, var1.func_179707_f());
         var3.setPotionDurationMax(var1.func_149429_c());
         ((EntityLivingBase)var2).addPotionEffect(var3);
      }

   }

   public void func_175094_a(S43PacketCamera var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = var1.func_179780_a(this.clientWorldController);
      if (var2 != null) {
         this.gameController.func_175607_a(var2);
      }

   }

   public void func_175095_a(S48PacketResourcePackSend var1) {
      String var2 = var1.func_179783_a();
      String var3 = var1.func_179784_b();
      if (var2.startsWith("level://")) {
         String var4 = var2.substring("level://".length());
         File var5 = new File(this.gameController.mcDataDir, "saves");
         File var6 = new File(var5, var4);
         if (var6.isFile()) {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.ACCEPTED));
            Futures.addCallback(this.gameController.getResourcePackRepository().func_177319_a(var6), new FutureCallback(this, var3) {
               final NetHandlerPlayClient this$0;
               private static final String __OBFID = "CL_00000879";
               private final String val$var3;

               public void onSuccess(Object var1) {
                  NetHandlerPlayClient.access$0(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
               }

               public void onFailure(Throwable var1) {
                  NetHandlerPlayClient.access$0(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
               }

               {
                  this.this$0 = var1;
                  this.val$var3 = var2;
               }
            });
         } else {
            this.netManager.sendPacket(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
         }
      } else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() == ServerData.ServerResourceMode.ENABLED) {
         this.netManager.sendPacket(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.ACCEPTED));
         Futures.addCallback(this.gameController.getResourcePackRepository().func_180601_a(var2, var3), new FutureCallback(this, var3) {
            private static final String __OBFID = "CL_00002624";
            private final String val$var3;
            final NetHandlerPlayClient this$0;

            {
               this.this$0 = var1;
               this.val$var3 = var2;
            }

            public void onFailure(Throwable var1) {
               NetHandlerPlayClient.access$0(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
            }

            public void onSuccess(Object var1) {
               NetHandlerPlayClient.access$0(this.this$0).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
            }
         });
      } else if (this.gameController.getCurrentServerData() != null && this.gameController.getCurrentServerData().getResourceMode() != ServerData.ServerResourceMode.PROMPT) {
         this.netManager.sendPacket(new C19PacketResourcePackStatus(var3, C19PacketResourcePackStatus.Action.DECLINED));
      } else {
         this.gameController.addScheduledTask(new Runnable(this, var3, var2) {
            final NetHandlerPlayClient this$0;
            private final String val$var2;
            private final String val$var3;
            private static final String __OBFID = "CL_00002623";

            public void run() {
               NetHandlerPlayClient.access$1(this.this$0).displayGuiScreen(new GuiYesNo(new GuiYesNoCallback(this, this.val$var3, this.val$var2) {
                  final <undefinedtype> this$1;
                  private static final String __OBFID = "CL_00002622";
                  private final String val$var2;
                  private final String val$var3;

                  {
                     this.this$1 = var1;
                     this.val$var3 = var2;
                     this.val$var2 = var3;
                  }

                  static <undefinedtype> access$0(Object var0) {
                     return var0.this$1;
                  }

                  public void confirmClicked(boolean var1, int var2) {
                     NetHandlerPlayClient.access$2(null.access$0(this.this$1), Minecraft.getMinecraft());
                     if (var1) {
                        if (NetHandlerPlayClient.access$1(null.access$0(this.this$1)).getCurrentServerData() != null) {
                           NetHandlerPlayClient.access$1(null.access$0(this.this$1)).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.ENABLED);
                        }

                        NetHandlerPlayClient.access$0(null.access$0(this.this$1)).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.ACCEPTED));
                        Futures.addCallback(NetHandlerPlayClient.access$1(null.access$0(this.this$1)).getResourcePackRepository().func_180601_a(this.val$var2, this.val$var3), new FutureCallback(this, this.val$var3) {
                           private static final String __OBFID = "CL_00002621";
                           private final String val$var3;
                           final <undefinedtype> this$2;

                           public void onFailure(Throwable var1) {
                              NetHandlerPlayClient.access$0(null.access$0(null.access$0(this.this$2))).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.FAILED_DOWNLOAD));
                           }

                           {
                              this.this$2 = var1;
                              this.val$var3 = var2;
                           }

                           public void onSuccess(Object var1) {
                              NetHandlerPlayClient.access$0(null.access$0(null.access$0(this.this$2))).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.SUCCESSFULLY_LOADED));
                           }
                        });
                     } else {
                        if (NetHandlerPlayClient.access$1(null.access$0(this.this$1)).getCurrentServerData() != null) {
                           NetHandlerPlayClient.access$1(null.access$0(this.this$1)).getCurrentServerData().setResourceMode(ServerData.ServerResourceMode.DISABLED);
                        }

                        NetHandlerPlayClient.access$0(null.access$0(this.this$1)).sendPacket(new C19PacketResourcePackStatus(this.val$var3, C19PacketResourcePackStatus.Action.DECLINED));
                     }

                     ServerList.func_147414_b(NetHandlerPlayClient.access$1(null.access$0(this.this$1)).getCurrentServerData());
                     NetHandlerPlayClient.access$1(null.access$0(this.this$1)).displayGuiScreen((GuiScreen)null);
                  }
               }, I18n.format("multiplayer.texturePrompt.line1"), I18n.format("multiplayer.texturePrompt.line2"), 0));
            }

            {
               this.this$0 = var1;
               this.val$var3 = var2;
               this.val$var2 = var3;
            }

            static NetHandlerPlayClient access$0(Object var0) {
               return var0.this$0;
            }
         });
      }

   }

   public void handleSpawnPainting(S10PacketSpawnPainting var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      EntityPainting var2 = new EntityPainting(this.clientWorldController, var1.func_179837_b(), var1.func_179836_c(), var1.func_148961_h());
      this.clientWorldController.addEntityToWorld(var1.func_148965_c(), var2);
   }

   public void handleUpdateHealth(S06PacketUpdateHealth var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.thePlayer.setPlayerSPHealth(var1.getHealth());
      this.gameController.thePlayer.getFoodStats().setFoodLevel(var1.getFoodLevel());
      this.gameController.thePlayer.getFoodStats().setFoodSaturationLevel(var1.getSaturationLevel());
   }

   public void cleanup() {
      this.clientWorldController = null;
   }

   public void addToSendQueue(Packet var1) {
      this.netManager.sendPacket(var1);
   }

   public NetHandlerPlayClient(Minecraft var1, GuiScreen var2, NetworkManager var3, GameProfile var4) {
      this.gameController = var1;
      this.guiScreenServer = var2;
      this.netManager = var3;
      this.field_175107_d = var4;
   }

   public void handleCloseWindow(S2EPacketCloseWindow var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.thePlayer.func_175159_q();
   }

   public void handleBlockBreakAnim(S25PacketBlockBreakAnim var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.theWorld.sendBlockBreakProgress(var1.func_148845_c(), var1.func_179821_b(), var1.func_148846_g());
   }

   public void handleScoreboardObjective(S3BPacketScoreboardObjective var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Scoreboard var2 = this.clientWorldController.getScoreboard();
      ScoreObjective var3;
      if (var1.func_149338_e() == 0) {
         var3 = var2.addScoreObjective(var1.func_149339_c(), IScoreObjectiveCriteria.DUMMY);
         var3.setDisplayName(var1.func_149337_d());
         var3.func_178767_a(var1.func_179817_d());
      } else {
         var3 = var2.getObjective(var1.func_149339_c());
         if (var1.func_149338_e() == 1) {
            var2.func_96519_k(var3);
         } else if (var1.func_149338_e() == 2) {
            var3.setDisplayName(var1.func_149337_d());
            var3.func_178767_a(var1.func_179817_d());
         }
      }

   }

   public void handlePlayerListItem(S38PacketPlayerListItem var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Iterator var2 = var1.func_179767_a().iterator();

      while(var2.hasNext()) {
         S38PacketPlayerListItem.AddPlayerData var3 = (S38PacketPlayerListItem.AddPlayerData)var2.next();
         if (var1.func_179768_b() == S38PacketPlayerListItem.Action.REMOVE_PLAYER) {
            this.playerInfoMap.remove(var3.func_179962_a().getId());
         } else {
            NetworkPlayerInfo var4 = (NetworkPlayerInfo)this.playerInfoMap.get(var3.func_179962_a().getId());
            if (var1.func_179768_b() == S38PacketPlayerListItem.Action.ADD_PLAYER) {
               var4 = new NetworkPlayerInfo(var3);
               this.playerInfoMap.put(var4.func_178845_a().getId(), var4);
            }

            if (var4 != null) {
               switch(var1.func_179768_b()) {
               case ADD_PLAYER:
                  var4.func_178839_a(var3.func_179960_c());
                  var4.func_178838_a(var3.func_179963_b());
                  break;
               case UPDATE_GAME_MODE:
                  var4.func_178839_a(var3.func_179960_c());
                  break;
               case UPDATE_LATENCY:
                  var4.func_178838_a(var3.func_179963_b());
                  break;
               case UPDATE_DISPLAY_NAME:
                  var4.func_178859_a(var3.func_179961_d());
               }
            }
         }
      }

   }

   public void handleCollectItem(S0DPacketCollectItem var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.func_149354_c());
      Object var3 = (EntityLivingBase)this.clientWorldController.getEntityByID(var1.func_149353_d());
      if (var3 == null) {
         var3 = this.gameController.thePlayer;
      }

      if (var2 != null) {
         if (var2 instanceof EntityXPOrb) {
            this.clientWorldController.playSoundAtEntity(var2, "random.orb", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         } else {
            this.clientWorldController.playSoundAtEntity(var2, "random.pop", 0.2F, ((this.avRandomizer.nextFloat() - this.avRandomizer.nextFloat()) * 0.7F + 1.0F) * 2.0F);
         }

         this.gameController.effectRenderer.addEffect(new EntityPickupFX(this.clientWorldController, var2, (Entity)var3, 0.5F));
         this.clientWorldController.removeEntityFromWorld(var1.func_149354_c());
      }

   }

   public void handleSpawnPlayer(S0CPacketSpawnPlayer var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      double var2 = (double)var1.func_148942_f() / 32.0D;
      double var4 = (double)var1.func_148949_g() / 32.0D;
      double var6 = (double)var1.func_148946_h() / 32.0D;
      float var8 = (float)(var1.func_148941_i() * 360) / 256.0F;
      float var9 = (float)(var1.func_148945_j() * 360) / 256.0F;
      EntityOtherPlayerMP var10 = new EntityOtherPlayerMP(this.gameController.theWorld, this.func_175102_a(var1.func_179819_c()).func_178845_a());
      var10.prevPosX = var10.lastTickPosX = (double)(var10.serverPosX = var1.func_148942_f());
      var10.prevPosY = var10.lastTickPosY = (double)(var10.serverPosY = var1.func_148949_g());
      var10.prevPosZ = var10.lastTickPosZ = (double)(var10.serverPosZ = var1.func_148946_h());
      int var11 = var1.func_148947_k();
      if (var11 == 0) {
         var10.inventory.mainInventory[var10.inventory.currentItem] = null;
      } else {
         var10.inventory.mainInventory[var10.inventory.currentItem] = new ItemStack(Item.getItemById(var11), 1, 0);
      }

      var10.setPositionAndRotation(var2, var4, var6, var8, var9);
      this.clientWorldController.addEntityToWorld(var1.func_148943_d(), var10);
      List var12 = var1.func_148944_c();
      if (var12 != null) {
         var10.getDataWatcher().updateWatchedObjectsFromList(var12);
      }

   }

   public NetworkPlayerInfo func_175102_a(UUID var1) {
      return (NetworkPlayerInfo)this.playerInfoMap.get(var1);
   }

   public void handleKeepAlive(S00PacketKeepAlive var1) {
      this.addToSendQueue(new C00PacketKeepAlive(var1.func_149134_c()));
   }

   public void handleDisplayScoreboard(S3DPacketDisplayScoreboard var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Scoreboard var2 = this.clientWorldController.getScoreboard();
      if (var1.func_149370_d().length() == 0) {
         var2.setObjectiveInDisplaySlot(var1.func_149371_c(), (ScoreObjective)null);
      } else {
         ScoreObjective var3 = var2.getObjective(var1.func_149370_d());
         var2.setObjectiveInDisplaySlot(var1.func_149371_c(), var3);
      }

   }

   public void handleEntityStatus(S19PacketEntityStatus var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = var1.func_149161_a(this.clientWorldController);
      if (var2 != null) {
         if (var1.func_149160_c() == 21) {
            this.gameController.getSoundHandler().playSound(new GuardianSound((EntityGuardian)var2));
         } else {
            var2.handleHealthUpdate(var1.func_149160_c());
         }
      }

   }

   public void handleRemoveEntityEffect(S1EPacketRemoveEntityEffect var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.func_149076_c());
      if (var2 instanceof EntityLivingBase) {
         ((EntityLivingBase)var2).removePotionEffectClient(var1.func_149075_d());
      }

   }

   public void handleEntityMetadata(S1CPacketEntityMetadata var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.func_149375_d());
      if (var2 != null && var1.func_149376_c() != null) {
         var2.getDataWatcher().updateWatchedObjectsFromList(var1.func_149376_c());
      }

   }

   public NetworkManager getNetworkManager() {
      return this.netManager;
   }

   public void handlePlayerPosLook(S08PacketPlayerPosLook var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.thePlayer;
      double var3 = var1.func_148932_c();
      double var5 = var1.func_148928_d();
      double var7 = var1.func_148933_e();
      float var9 = var1.func_148931_f();
      float var10 = var1.func_148930_g();
      if (var1.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X)) {
         var3 += var2.posX;
      } else {
         var2.motionX = 0.0D;
      }

      if (var1.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
         var5 += var2.posY;
      } else {
         var2.motionY = 0.0D;
      }

      if (var1.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
         var7 += var2.posZ;
      } else {
         var2.motionZ = 0.0D;
      }

      if (var1.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
         var10 += var2.rotationPitch;
      }

      if (var1.func_179834_f().contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
         var9 += var2.rotationYaw;
      }

      var2.setPositionAndRotation(var3, var5, var7, var9, var10);
      this.netManager.sendPacket(new C03PacketPlayer.C06PacketPlayerPosLook(var2.posX, var2.getEntityBoundingBox().minY, var2.posZ, var2.rotationYaw, var2.rotationPitch, false));
      if (!this.doneLoadingTerrain) {
         this.gameController.thePlayer.prevPosX = this.gameController.thePlayer.posX;
         this.gameController.thePlayer.prevPosY = this.gameController.thePlayer.posY;
         this.gameController.thePlayer.prevPosZ = this.gameController.thePlayer.posZ;
         this.doneLoadingTerrain = true;
         this.gameController.displayGuiScreen((GuiScreen)null);
      }

   }

   public void func_175099_a(S45PacketTitle var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      S45PacketTitle.Type var2 = var1.func_179807_a();
      String var3 = null;
      String var4 = null;
      String var5 = var1.func_179805_b() != null ? var1.func_179805_b().getFormattedText() : "";
      switch(var2) {
      case TITLE:
         var3 = var5;
         break;
      case SUBTITLE:
         var4 = var5;
         break;
      case RESET:
         this.gameController.ingameGUI.func_175178_a("", "", -1, -1, -1);
         this.gameController.ingameGUI.func_175177_a();
         return;
      }

      this.gameController.ingameGUI.func_175178_a(var3, var4, var1.func_179806_c(), var1.func_179804_d(), var1.func_179803_e());
   }

   public void handleUseBed(S0APacketUseBed var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      var1.getPlayer(this.clientWorldController).func_180469_a(var1.func_179798_a());
   }

   public GameProfile func_175105_e() {
      return this.field_175107_d;
   }

   public void handleJoinGame(S01PacketJoinGame var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.playerController = new PlayerControllerMP(this.gameController, this);
      this.clientWorldController = new WorldClient(this, new WorldSettings(0L, var1.func_149198_e(), false, var1.func_149195_d(), var1.func_149196_i()), var1.func_149194_f(), var1.func_149192_g(), this.gameController.mcProfiler);
      this.gameController.gameSettings.difficulty = var1.func_149192_g();
      this.gameController.loadWorld(this.clientWorldController);
      this.gameController.thePlayer.dimension = var1.func_149194_f();
      this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
      this.gameController.thePlayer.setEntityId(var1.func_149197_c());
      this.currentServerMaxPlayers = var1.func_149193_h();
      this.gameController.thePlayer.func_175150_k(var1.func_179744_h());
      this.gameController.playerController.setGameType(var1.func_149198_e());
      this.gameController.gameSettings.sendSettingsToServer();
      this.netManager.sendPacket(new C17PacketCustomPayload("MC|Brand", (new PacketBuffer(Unpooled.buffer())).writeString(ClientBrandRetriever.getClientModName())));
   }

   public void handleSpawnPosition(S05PacketSpawnPosition var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.thePlayer.func_180473_a(var1.func_179800_a(), true);
      this.gameController.theWorld.getWorldInfo().setSpawn(var1.func_179800_a());
   }

   public void handleDestroyEntities(S13PacketDestroyEntities var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);

      for(int var2 = 0; var2 < var1.func_149098_c().length; ++var2) {
         this.clientWorldController.removeEntityFromWorld(var1.func_149098_c()[var2]);
      }

   }

   public NetworkPlayerInfo func_175104_a(String var1) {
      Iterator var2 = this.playerInfoMap.values().iterator();

      while(var2.hasNext()) {
         NetworkPlayerInfo var3 = (NetworkPlayerInfo)var2.next();
         if (var3.func_178845_a().getName().equals(var1)) {
            return var3;
         }
      }

      return null;
   }

   public void handleBlockChange(S23PacketBlockChange var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.clientWorldController.func_180503_b(var1.func_179827_b(), var1.func_180728_a());
   }

   public void handleExplosion(S27PacketExplosion var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Explosion var2 = new Explosion(this.gameController.theWorld, (Entity)null, var1.func_149148_f(), var1.func_149143_g(), var1.func_149145_h(), var1.func_149146_i(), var1.func_149150_j());
      var2.doExplosionB(true);
      EntityPlayerSP var10000 = this.gameController.thePlayer;
      var10000.motionX += (double)var1.func_149149_c();
      var10000 = this.gameController.thePlayer;
      var10000.motionY += (double)var1.func_149144_d();
      var10000 = this.gameController.thePlayer;
      var10000.motionZ += (double)var1.func_149147_e();
   }

   public void handleTabComplete(S3APacketTabComplete var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      String[] var2 = var1.func_149630_c();
      if (this.gameController.currentScreen instanceof GuiChat) {
         GuiChat var3 = (GuiChat)this.gameController.currentScreen;
         var3.onAutocompleteResponse(var2);
      }

   }

   public void handleSpawnObject(S0EPacketSpawnObject var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      double var2 = (double)var1.func_148997_d() / 32.0D;
      double var4 = (double)var1.func_148998_e() / 32.0D;
      double var6 = (double)var1.func_148994_f() / 32.0D;
      Object var8 = null;
      if (var1.func_148993_l() == 10) {
         var8 = EntityMinecart.func_180458_a(this.clientWorldController, var2, var4, var6, EntityMinecart.EnumMinecartType.func_180038_a(var1.func_149009_m()));
      } else if (var1.func_148993_l() == 90) {
         Entity var9 = this.clientWorldController.getEntityByID(var1.func_149009_m());
         if (var9 instanceof EntityPlayer) {
            var8 = new EntityFishHook(this.clientWorldController, var2, var4, var6, (EntityPlayer)var9);
         }

         var1.func_149002_g(0);
      } else if (var1.func_148993_l() == 60) {
         var8 = new EntityArrow(this.clientWorldController, var2, var4, var6);
      } else if (var1.func_148993_l() == 61) {
         var8 = new EntitySnowball(this.clientWorldController, var2, var4, var6);
      } else if (var1.func_148993_l() == 71) {
         var8 = new EntityItemFrame(this.clientWorldController, new BlockPos(MathHelper.floor_double(var2), MathHelper.floor_double(var4), MathHelper.floor_double(var6)), EnumFacing.getHorizontal(var1.func_149009_m()));
         var1.func_149002_g(0);
      } else if (var1.func_148993_l() == 77) {
         var8 = new EntityLeashKnot(this.clientWorldController, new BlockPos(MathHelper.floor_double(var2), MathHelper.floor_double(var4), MathHelper.floor_double(var6)));
         var1.func_149002_g(0);
      } else if (var1.func_148993_l() == 65) {
         var8 = new EntityEnderPearl(this.clientWorldController, var2, var4, var6);
      } else if (var1.func_148993_l() == 72) {
         var8 = new EntityEnderEye(this.clientWorldController, var2, var4, var6);
      } else if (var1.func_148993_l() == 76) {
         var8 = new EntityFireworkRocket(this.clientWorldController, var2, var4, var6, (ItemStack)null);
      } else if (var1.func_148993_l() == 63) {
         var8 = new EntityLargeFireball(this.clientWorldController, var2, var4, var6, (double)var1.func_149010_g() / 8000.0D, (double)var1.func_149004_h() / 8000.0D, (double)var1.func_148999_i() / 8000.0D);
         var1.func_149002_g(0);
      } else if (var1.func_148993_l() == 64) {
         var8 = new EntitySmallFireball(this.clientWorldController, var2, var4, var6, (double)var1.func_149010_g() / 8000.0D, (double)var1.func_149004_h() / 8000.0D, (double)var1.func_148999_i() / 8000.0D);
         var1.func_149002_g(0);
      } else if (var1.func_148993_l() == 66) {
         var8 = new EntityWitherSkull(this.clientWorldController, var2, var4, var6, (double)var1.func_149010_g() / 8000.0D, (double)var1.func_149004_h() / 8000.0D, (double)var1.func_148999_i() / 8000.0D);
         var1.func_149002_g(0);
      } else if (var1.func_148993_l() == 62) {
         var8 = new EntityEgg(this.clientWorldController, var2, var4, var6);
      } else if (var1.func_148993_l() == 73) {
         var8 = new EntityPotion(this.clientWorldController, var2, var4, var6, var1.func_149009_m());
         var1.func_149002_g(0);
      } else if (var1.func_148993_l() == 75) {
         var8 = new EntityExpBottle(this.clientWorldController, var2, var4, var6);
         var1.func_149002_g(0);
      } else if (var1.func_148993_l() == 1) {
         var8 = new EntityBoat(this.clientWorldController, var2, var4, var6);
      } else if (var1.func_148993_l() == 50) {
         var8 = new EntityTNTPrimed(this.clientWorldController, var2, var4, var6, (EntityLivingBase)null);
      } else if (var1.func_148993_l() == 78) {
         var8 = new EntityArmorStand(this.clientWorldController, var2, var4, var6);
      } else if (var1.func_148993_l() == 51) {
         var8 = new EntityEnderCrystal(this.clientWorldController, var2, var4, var6);
      } else if (var1.func_148993_l() == 2) {
         var8 = new EntityItem(this.clientWorldController, var2, var4, var6);
      } else if (var1.func_148993_l() == 70) {
         var8 = new EntityFallingBlock(this.clientWorldController, var2, var4, var6, Block.getStateById(var1.func_149009_m() & '\uffff'));
         var1.func_149002_g(0);
      }

      if (var8 != null) {
         ((Entity)var8).serverPosX = var1.func_148997_d();
         ((Entity)var8).serverPosY = var1.func_148998_e();
         ((Entity)var8).serverPosZ = var1.func_148994_f();
         ((Entity)var8).rotationPitch = (float)(var1.func_149008_j() * 360) / 256.0F;
         ((Entity)var8).rotationYaw = (float)(var1.func_149006_k() * 360) / 256.0F;
         Entity[] var12 = ((Entity)var8).getParts();
         if (var12 != null) {
            int var10 = var1.func_149001_c() - ((Entity)var8).getEntityId();

            for(int var11 = 0; var11 < var12.length; ++var11) {
               var12[var11].setEntityId(var12[var11].getEntityId() + var10);
            }
         }

         ((Entity)var8).setEntityId(var1.func_149001_c());
         this.clientWorldController.addEntityToWorld(var1.func_149001_c(), (Entity)var8);
         if (var1.func_149009_m() > 0) {
            if (var1.func_148993_l() == 60) {
               Entity var13 = this.clientWorldController.getEntityByID(var1.func_149009_m());
               if (var13 instanceof EntityLivingBase && var8 instanceof EntityArrow) {
                  ((EntityArrow)var8).shootingEntity = var13;
               }
            }

            ((Entity)var8).setVelocity((double)var1.func_149010_g() / 8000.0D, (double)var1.func_149004_h() / 8000.0D, (double)var1.func_148999_i() / 8000.0D);
         }
      }

   }

   public void handleUpdateTileEntity(S35PacketUpdateTileEntity var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      if (this.gameController.theWorld.isBlockLoaded(var1.func_179823_a())) {
         TileEntity var2 = this.gameController.theWorld.getTileEntity(var1.func_179823_a());
         int var3 = var1.getTileEntityType();
         if (var3 == 1 && var2 instanceof TileEntityMobSpawner || var3 == 2 && var2 instanceof TileEntityCommandBlock || var3 == 3 && var2 instanceof TileEntityBeacon || var3 == 4 && var2 instanceof TileEntitySkull || var3 == 5 && var2 instanceof TileEntityFlowerPot || var3 == 6 && var2 instanceof TileEntityBanner) {
            var2.readFromNBT(var1.getNbtCompound());
         }
      }

   }

   public void onDisconnect(IChatComponent var1) {
      this.gameController.loadWorld((WorldClient)null);
      if (this.guiScreenServer != null) {
         if (this.guiScreenServer instanceof GuiScreenRealmsProxy) {
            this.gameController.displayGuiScreen((new DisconnectedRealmsScreen(((GuiScreenRealmsProxy)this.guiScreenServer).func_154321_a(), "disconnect.lost", var1)).getProxy());
         } else {
            this.gameController.displayGuiScreen(new GuiDisconnected(this.guiScreenServer, "disconnect.lost", var1));
         }
      } else {
         this.gameController.displayGuiScreen(new GuiDisconnected(new GuiMultiplayer(new GuiMainMenu()), "disconnect.lost", var1));
      }

   }

   public void func_175093_a(S44PacketWorldBorder var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      var1.func_179788_a(this.clientWorldController.getWorldBorder());
   }

   public void handlePlayerAbilities(S39PacketPlayerAbilities var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.thePlayer;
      var2.capabilities.isFlying = var1.isFlying();
      var2.capabilities.isCreativeMode = var1.isCreativeMode();
      var2.capabilities.disableDamage = var1.isInvulnerable();
      var2.capabilities.allowFlying = var1.isAllowFlying();
      var2.capabilities.setFlySpeed(var1.getFlySpeed());
      var2.capabilities.setPlayerWalkSpeed(var1.getWalkSpeed());
   }

   public void handleChat(S02PacketChat var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      if (var1.func_179841_c() == 2) {
         this.gameController.ingameGUI.func_175188_a(var1.func_148915_c(), false);
      } else {
         this.gameController.ingameGUI.getChatGUI().printChatMessage(var1.func_148915_c());
      }

   }

   public void handleChangeGameState(S2BPacketChangeGameState var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.thePlayer;
      int var3 = var1.func_149138_c();
      float var4 = var1.func_149137_d();
      int var5 = MathHelper.floor_float(var4 + 0.5F);
      if (var3 >= 0 && var3 < S2BPacketChangeGameState.MESSAGE_NAMES.length && S2BPacketChangeGameState.MESSAGE_NAMES[var3] != null) {
         var2.addChatComponentMessage(new ChatComponentTranslation(S2BPacketChangeGameState.MESSAGE_NAMES[var3], new Object[0]));
      }

      if (var3 == 1) {
         this.clientWorldController.getWorldInfo().setRaining(true);
         this.clientWorldController.setRainStrength(0.0F);
      } else if (var3 == 2) {
         this.clientWorldController.getWorldInfo().setRaining(false);
         this.clientWorldController.setRainStrength(1.0F);
      } else if (var3 == 3) {
         this.gameController.playerController.setGameType(WorldSettings.GameType.getByID(var5));
      } else if (var3 == 4) {
         this.gameController.displayGuiScreen(new GuiWinGame());
      } else if (var3 == 5) {
         GameSettings var6 = this.gameController.gameSettings;
         if (var4 == 0.0F) {
            this.gameController.displayGuiScreen(new GuiScreenDemo());
         } else if (var4 == 101.0F) {
            this.gameController.ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation("demo.help.movement", new Object[]{GameSettings.getKeyDisplayString(var6.keyBindForward.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindLeft.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindBack.getKeyCode()), GameSettings.getKeyDisplayString(var6.keyBindRight.getKeyCode())}));
         } else if (var4 == 102.0F) {
            this.gameController.ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation("demo.help.jump", new Object[]{GameSettings.getKeyDisplayString(var6.keyBindJump.getKeyCode())}));
         } else if (var4 == 103.0F) {
            this.gameController.ingameGUI.getChatGUI().printChatMessage(new ChatComponentTranslation("demo.help.inventory", new Object[]{GameSettings.getKeyDisplayString(var6.keyBindInventory.getKeyCode())}));
         }
      } else if (var3 == 6) {
         this.clientWorldController.playSound(var2.posX, var2.posY + (double)var2.getEyeHeight(), var2.posZ, "random.successful_hit", 0.18F, 0.45F, false);
      } else if (var3 == 7) {
         this.clientWorldController.setRainStrength(var4);
      } else if (var3 == 8) {
         this.clientWorldController.setThunderStrength(var4);
      } else if (var3 == 10) {
         this.clientWorldController.spawnParticle(EnumParticleTypes.MOB_APPEARANCE, var2.posX, var2.posY, var2.posZ, 0.0D, 0.0D, 0.0D, new int[0]);
         this.clientWorldController.playSound(var2.posX, var2.posY, var2.posZ, "mob.guardian.curse", 1.0F, 1.0F, false);
      }

   }

   public void handleUpdateSign(S33PacketUpdateSign var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      boolean var2 = false;
      if (this.gameController.theWorld.isBlockLoaded(var1.func_179704_a())) {
         TileEntity var3 = this.gameController.theWorld.getTileEntity(var1.func_179704_a());
         if (var3 instanceof TileEntitySign) {
            TileEntitySign var4 = (TileEntitySign)var3;
            if (var4.getIsEditable()) {
               System.arraycopy(var1.func_180753_b(), 0, var4.signText, 0, 4);
               var4.markDirty();
            }

            var2 = true;
         }
      }

      if (!var2 && this.gameController.thePlayer != null) {
         this.gameController.thePlayer.addChatMessage(new ChatComponentText(String.valueOf((new StringBuilder("Unable to locate sign at ")).append(var1.func_179704_a().getX()).append(", ").append(var1.func_179704_a().getY()).append(", ").append(var1.func_179704_a().getZ()))));
      }

   }

   public void handleRespawn(S07PacketRespawn var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      if (var1.func_149082_c() != this.gameController.thePlayer.dimension) {
         this.doneLoadingTerrain = false;
         Scoreboard var2 = this.clientWorldController.getScoreboard();
         this.clientWorldController = new WorldClient(this, new WorldSettings(0L, var1.func_149083_e(), false, this.gameController.theWorld.getWorldInfo().isHardcoreModeEnabled(), var1.func_149080_f()), var1.func_149082_c(), var1.func_149081_d(), this.gameController.mcProfiler);
         this.clientWorldController.setWorldScoreboard(var2);
         this.gameController.loadWorld(this.clientWorldController);
         this.gameController.thePlayer.dimension = var1.func_149082_c();
         this.gameController.displayGuiScreen(new GuiDownloadTerrain(this));
      }

      this.gameController.setDimensionAndSpawnPlayer(var1.func_149082_c());
      this.gameController.playerController.setGameType(var1.func_149083_e());
   }

   public void handleStatistics(S37PacketStatistics var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      boolean var2 = false;

      StatBase var3;
      int var4;
      for(Iterator var5 = var1.func_148974_c().entrySet().iterator(); var5.hasNext(); this.gameController.thePlayer.getStatFileWriter().func_150873_a(this.gameController.thePlayer, var3, var4)) {
         Entry var6 = (Entry)var5.next();
         var3 = (StatBase)var6.getKey();
         var4 = (Integer)var6.getValue();
         if (var3.isAchievement() && var4 > 0) {
            if (this.field_147308_k && this.gameController.thePlayer.getStatFileWriter().writeStat(var3) == 0) {
               Achievement var7 = (Achievement)var3;
               this.gameController.guiAchievement.displayAchievement(var7);
               this.gameController.getTwitchStream().func_152911_a(new MetadataAchievement(var7), 0L);
               if (var3 == AchievementList.openInventory) {
                  this.gameController.gameSettings.showInventoryAchievementHint = false;
                  this.gameController.gameSettings.saveOptions();
               }
            }

            var2 = true;
         }
      }

      if (!this.field_147308_k && !var2 && this.gameController.gameSettings.showInventoryAchievementHint) {
         this.gameController.guiAchievement.displayUnformattedAchievement(AchievementList.openInventory);
      }

      this.field_147308_k = true;
      if (this.gameController.currentScreen instanceof IProgressMeter) {
         ((IProgressMeter)this.gameController.currentScreen).doneLoading();
      }

   }

   public void func_175100_a(S46PacketSetCompressionLevel var1) {
      if (!this.netManager.isLocalChannel()) {
         this.netManager.setCompressionTreshold(var1.func_179760_a());
      }

   }

   public void handleChunkData(S21PacketChunkData var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      if (var1.func_149274_i()) {
         if (var1.func_149276_g() == 0) {
            this.clientWorldController.doPreChunk(var1.func_149273_e(), var1.func_149271_f(), false);
            return;
         }

         this.clientWorldController.doPreChunk(var1.func_149273_e(), var1.func_149271_f(), true);
      }

      this.clientWorldController.invalidateBlockReceiveRegion(var1.func_149273_e() << 4, 0, var1.func_149271_f() << 4, (var1.func_149273_e() << 4) + 15, 256, (var1.func_149271_f() << 4) + 15);
      Chunk var2 = this.clientWorldController.getChunkFromChunkCoords(var1.func_149273_e(), var1.func_149271_f());
      var2.func_177439_a(var1.func_149272_d(), var1.func_149276_g(), var1.func_149274_i());
      this.clientWorldController.markBlockRangeForRenderUpdate(var1.func_149273_e() << 4, 0, var1.func_149271_f() << 4, (var1.func_149273_e() << 4) + 15, 256, (var1.func_149271_f() << 4) + 15);
      if (!var1.func_149274_i() || !(this.clientWorldController.provider instanceof WorldProviderSurface)) {
         var2.resetRelightChecks();
      }

   }

   public void handleMultiBlockChange(S22PacketMultiBlockChange var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      S22PacketMultiBlockChange.BlockUpdateData[] var2 = var1.func_179844_a();
      int var3 = var2.length;

      for(int var4 = 0; var4 < var3; ++var4) {
         S22PacketMultiBlockChange.BlockUpdateData var5 = var2[var4];
         this.clientWorldController.func_180503_b(var5.func_180090_a(), var5.func_180088_c());
      }

   }

   public void func_175097_a(S49PacketUpdateEntityNBT var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = var1.func_179764_a(this.clientWorldController);
      if (var2 != null) {
         var2.func_174834_g(var1.func_179763_a());
      }

   }

   public void handleEntityEquipment(S04PacketEntityEquipment var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.func_149389_d());
      if (var2 != null) {
         var2.setCurrentItemOrArmor(var1.func_149388_e(), var1.func_149390_c());
      }

   }

   public void handleOpenWindow(S2DPacketOpenWindow var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.thePlayer;
      if ("minecraft:container".equals(var1.func_148902_e())) {
         var2.displayGUIChest(new InventoryBasic(var1.func_179840_c(), var1.func_148898_f()));
         var2.openContainer.windowId = var1.func_148901_c();
      } else if ("minecraft:villager".equals(var1.func_148902_e())) {
         var2.displayVillagerTradeGui(new NpcMerchant(var2, var1.func_179840_c()));
         var2.openContainer.windowId = var1.func_148901_c();
      } else if ("EntityHorse".equals(var1.func_148902_e())) {
         Entity var3 = this.clientWorldController.getEntityByID(var1.func_148897_h());
         if (var3 instanceof EntityHorse) {
            var2.displayGUIHorse((EntityHorse)var3, new AnimalChest(var1.func_179840_c(), var1.func_148898_f()));
            var2.openContainer.windowId = var1.func_148901_c();
         }
      } else if (!var1.func_148900_g()) {
         var2.displayGui(new LocalBlockIntercommunication(var1.func_148902_e(), var1.func_179840_c()));
         var2.openContainer.windowId = var1.func_148901_c();
      } else {
         ContainerLocalMenu var4 = new ContainerLocalMenu(var1.func_148902_e(), var1.func_179840_c(), var1.func_148898_f());
         var2.displayGUIChest(var4);
         var2.openContainer.windowId = var1.func_148901_c();
      }

   }

   public void handleSetExperience(S1FPacketSetExperience var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.thePlayer.setXPStats(var1.func_149397_c(), var1.func_149396_d(), var1.func_149395_e());
   }

   public void handleSpawnGlobalEntity(S2CPacketSpawnGlobalEntity var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      double var2 = (double)var1.func_149051_d() / 32.0D;
      double var4 = (double)var1.func_149050_e() / 32.0D;
      double var6 = (double)var1.func_149049_f() / 32.0D;
      EntityLightningBolt var8 = null;
      if (var1.func_149053_g() == 1) {
         var8 = new EntityLightningBolt(this.clientWorldController, var2, var4, var6);
      }

      if (var8 != null) {
         var8.serverPosX = var1.func_149051_d();
         var8.serverPosY = var1.func_149050_e();
         var8.serverPosZ = var1.func_149049_f();
         var8.rotationYaw = 0.0F;
         var8.rotationPitch = 0.0F;
         var8.setEntityId(var1.func_149052_c());
         this.clientWorldController.addWeatherEffect(var8);
      }

   }

   public void handleSoundEffect(S29PacketSoundEffect var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.theWorld.playSound(var1.func_149207_d(), var1.func_149211_e(), var1.func_149210_f(), var1.func_149212_c(), var1.func_149208_g(), var1.func_149209_h(), false);
   }

   public void func_175096_a(S47PacketPlayerListHeaderFooter var1) {
      this.gameController.ingameGUI.getTabList().setHeader(var1.func_179700_a().getFormattedText().length() == 0 ? null : var1.func_179700_a());
      this.gameController.ingameGUI.getTabList().setFooter(var1.func_179701_b().getFormattedText().length() == 0 ? null : var1.func_179701_b());
   }

   public void handleSpawnExperienceOrb(S11PacketSpawnExperienceOrb var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      EntityXPOrb var2 = new EntityXPOrb(this.clientWorldController, (double)var1.func_148984_d(), (double)var1.func_148983_e(), (double)var1.func_148982_f(), var1.func_148986_g());
      var2.serverPosX = var1.func_148984_d();
      var2.serverPosY = var1.func_148983_e();
      var2.serverPosZ = var1.func_148982_f();
      var2.rotationYaw = 0.0F;
      var2.rotationPitch = 0.0F;
      var2.setEntityId(var1.func_148985_c());
      this.clientWorldController.addEntityToWorld(var1.func_148985_c(), var2);
   }

   public void handleEntityAttach(S1BPacketEntityAttach var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Object var2 = this.clientWorldController.getEntityByID(var1.func_149403_d());
      Entity var3 = this.clientWorldController.getEntityByID(var1.func_149402_e());
      if (var1.func_149404_c() == 0) {
         boolean var4 = false;
         if (var1.func_149403_d() == this.gameController.thePlayer.getEntityId()) {
            var2 = this.gameController.thePlayer;
            if (var3 instanceof EntityBoat) {
               ((EntityBoat)var3).setIsBoatEmpty(false);
            }

            var4 = ((Entity)var2).ridingEntity == null && var3 != null;
         } else if (var3 instanceof EntityBoat) {
            ((EntityBoat)var3).setIsBoatEmpty(true);
         }

         if (var2 == null) {
            return;
         }

         ((Entity)var2).mountEntity(var3);
         if (var4) {
            GameSettings var5 = this.gameController.gameSettings;
            this.gameController.ingameGUI.setRecordPlaying(I18n.format("mount.onboard", GameSettings.getKeyDisplayString(var5.keyBindSneak.getKeyCode())), false);
         }
      } else if (var1.func_149404_c() == 1 && var2 instanceof EntityLiving) {
         if (var3 != null) {
            ((EntityLiving)var2).setLeashedToEntity(var3, false);
         } else {
            ((EntityLiving)var2).clearLeashed(false, false);
         }
      }

   }

   public void handleParticles(S2APacketParticles var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      if (var1.func_149222_k() == 0) {
         double var2 = (double)(var1.func_149227_j() * var1.func_149221_g());
         double var4 = (double)(var1.func_149227_j() * var1.func_149224_h());
         double var6 = (double)(var1.func_149227_j() * var1.func_149223_i());

         try {
            this.clientWorldController.spawnParticle(var1.func_179749_a(), var1.func_179750_b(), var1.func_149220_d(), var1.func_149226_e(), var1.func_149225_f(), var2, var4, var6, var1.func_179748_k());
         } catch (Throwable var17) {
            logger.warn(String.valueOf((new StringBuilder("Could not spawn particle effect ")).append(var1.func_179749_a())));
         }
      } else {
         for(int var18 = 0; var18 < var1.func_149222_k(); ++var18) {
            double var3 = this.avRandomizer.nextGaussian() * (double)var1.func_149221_g();
            double var5 = this.avRandomizer.nextGaussian() * (double)var1.func_149224_h();
            double var7 = this.avRandomizer.nextGaussian() * (double)var1.func_149223_i();
            double var9 = this.avRandomizer.nextGaussian() * (double)var1.func_149227_j();
            double var11 = this.avRandomizer.nextGaussian() * (double)var1.func_149227_j();
            double var13 = this.avRandomizer.nextGaussian() * (double)var1.func_149227_j();

            try {
               this.clientWorldController.spawnParticle(var1.func_179749_a(), var1.func_179750_b(), var1.func_149220_d() + var3, var1.func_149226_e() + var5, var1.func_149225_f() + var7, var9, var11, var13, var1.func_179748_k());
            } catch (Throwable var16) {
               logger.warn(String.valueOf((new StringBuilder("Could not spawn particle effect ")).append(var1.func_179749_a())));
               return;
            }
         }
      }

   }

   public void handleDisconnect(S40PacketDisconnect var1) {
      this.netManager.closeChannel(var1.func_149165_c());
   }

   public void handleEntityHeadLook(S19PacketEntityHeadLook var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = var1.func_149381_a(this.clientWorldController);
      if (var2 != null) {
         float var3 = (float)(var1.func_149380_c() * 360) / 256.0F;
         var2.setRotationYawHead(var3);
      }

   }

   static Minecraft access$1(NetHandlerPlayClient var0) {
      return var0.gameController;
   }

   public void handleMapChunkBulk(S26PacketMapChunkBulk var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);

      for(int var2 = 0; var2 < var1.func_149254_d(); ++var2) {
         int var3 = var1.func_149255_a(var2);
         int var4 = var1.func_149253_b(var2);
         this.clientWorldController.doPreChunk(var3, var4, true);
         this.clientWorldController.invalidateBlockReceiveRegion(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
         Chunk var5 = this.clientWorldController.getChunkFromChunkCoords(var3, var4);
         var5.func_177439_a(var1.func_149256_c(var2), var1.func_179754_d(var2), true);
         this.clientWorldController.markBlockRangeForRenderUpdate(var3 << 4, 0, var4 << 4, (var3 << 4) + 15, 256, (var4 << 4) + 15);
         if (!(this.clientWorldController.provider instanceof WorldProviderSurface)) {
            var5.resetRelightChecks();
         }
      }

   }

   public void handleCustomPayload(S3FPacketCustomPayload var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      if ("MC|TrList".equals(var1.getChannelName())) {
         PacketBuffer var2 = var1.getBufferData();

         try {
            int var3 = var2.readInt();
            GuiScreen var4 = this.gameController.currentScreen;
            if (var4 != null && var4 instanceof GuiMerchant && var3 == this.gameController.thePlayer.openContainer.windowId) {
               IMerchant var5 = ((GuiMerchant)var4).getMerchant();
               MerchantRecipeList var6 = MerchantRecipeList.func_151390_b(var2);
               var5.setRecipes(var6);
            }
         } catch (IOException var10) {
            logger.error("Couldn't load trade info", var10);
         } finally {
            var2.release();
         }
      } else if ("MC|Brand".equals(var1.getChannelName())) {
         this.gameController.thePlayer.func_175158_f(var1.getBufferData().readStringFromBuffer(32767));
      } else if ("MC|BOpen".equals(var1.getChannelName())) {
         ItemStack var12 = this.gameController.thePlayer.getCurrentEquippedItem();
         if (var12 != null && var12.getItem() == Items.written_book) {
            this.gameController.displayGuiScreen(new GuiScreenBook(this.gameController.thePlayer, var12, false));
         }
      }

   }

   public Collection func_175106_d() {
      return this.playerInfoMap.values();
   }

   public void handleSetSlot(S2FPacketSetSlot var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.thePlayer;
      if (var1.func_149175_c() == -1) {
         var2.inventory.setItemStack(var1.func_149174_e());
      } else {
         boolean var3 = false;
         if (this.gameController.currentScreen instanceof GuiContainerCreative) {
            GuiContainerCreative var4 = (GuiContainerCreative)this.gameController.currentScreen;
            var3 = var4.func_147056_g() != CreativeTabs.tabInventory.getTabIndex();
         }

         if (var1.func_149175_c() == 0 && var1.func_149173_d() >= 36 && var1.func_149173_d() < 45) {
            ItemStack var5 = var2.inventoryContainer.getSlot(var1.func_149173_d()).getStack();
            if (var1.func_149174_e() != null && (var5 == null || var5.stackSize < var1.func_149174_e().stackSize)) {
               var1.func_149174_e().animationsToGo = 5;
            }

            var2.inventoryContainer.putStackInSlot(var1.func_149173_d(), var1.func_149174_e());
         } else if (var1.func_149175_c() == var2.openContainer.windowId && (var1.func_149175_c() != 0 || !var3)) {
            var2.openContainer.putStackInSlot(var1.func_149173_d(), var1.func_149174_e());
         }
      }

   }

   public void handleAnimation(S0BPacketAnimation var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.func_148978_c());
      if (var2 != null) {
         if (var1.func_148977_d() == 0) {
            EntityLivingBase var3 = (EntityLivingBase)var2;
            var3.swingItem();
         } else if (var1.func_148977_d() == 1) {
            var2.performHurtAnimation();
         } else if (var1.func_148977_d() == 2) {
            EntityPlayer var4 = (EntityPlayer)var2;
            var4.wakeUpPlayer(false, false, false);
         } else if (var1.func_148977_d() == 4) {
            this.gameController.effectRenderer.func_178926_a(var2, EnumParticleTypes.CRIT);
         } else if (var1.func_148977_d() == 5) {
            this.gameController.effectRenderer.func_178926_a(var2, EnumParticleTypes.CRIT_MAGIC);
         }
      }

   }

   public void handleMaps(S34PacketMaps var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      MapData var2 = ItemMap.loadMapData(var1.getMapId(), this.gameController.theWorld);
      var1.func_179734_a(var2);
      this.gameController.entityRenderer.getMapItemRenderer().func_148246_a(var2);
   }

   public void handleEffect(S28PacketEffect var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      if (var1.isSoundServerwide()) {
         this.gameController.theWorld.func_175669_a(var1.getSoundType(), var1.func_179746_d(), var1.getSoundData());
      } else {
         this.gameController.theWorld.playAuxSFX(var1.getSoundType(), var1.func_179746_d(), var1.getSoundData());
      }

   }

   public void handleEntityVelocity(S12PacketEntityVelocity var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.func_149412_c());
      if (var2 != null) {
         var2.setVelocity((double)var1.func_149411_d() / 8000.0D, (double)var1.func_149410_e() / 8000.0D, (double)var1.func_149409_f() / 8000.0D);
      }

   }

   public void handleSignEditorOpen(S36PacketSignEditorOpen var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Object var2 = this.clientWorldController.getTileEntity(var1.func_179777_a());
      if (!(var2 instanceof TileEntitySign)) {
         var2 = new TileEntitySign();
         ((TileEntity)var2).setWorldObj(this.clientWorldController);
         ((TileEntity)var2).setPos(var1.func_179777_a());
      }

      this.gameController.thePlayer.func_175141_a((TileEntitySign)var2);
   }

   public void handleEntityProperties(S20PacketEntityProperties var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Entity var2 = this.clientWorldController.getEntityByID(var1.func_149442_c());
      if (var2 != null) {
         if (!(var2 instanceof EntityLivingBase)) {
            throw new IllegalStateException(String.valueOf((new StringBuilder("Server tried to update attributes of a non-living entity (actually: ")).append(var2).append(")")));
         }

         BaseAttributeMap var3 = ((EntityLivingBase)var2).getAttributeMap();
         Iterator var4 = var1.func_149441_d().iterator();

         while(var4.hasNext()) {
            S20PacketEntityProperties.Snapshot var5 = (S20PacketEntityProperties.Snapshot)var4.next();
            IAttributeInstance var6 = var3.getAttributeInstanceByName(var5.func_151409_a());
            if (var6 == null) {
               var6 = var3.registerAttribute(new RangedAttribute((IAttribute)null, var5.func_151409_a(), 0.0D, 2.2250738585072014E-308D, Double.MAX_VALUE));
            }

            var6.setBaseValue(var5.func_151410_b());
            var6.removeAllModifiers();
            Iterator var7 = var5.func_151408_c().iterator();

            while(var7.hasNext()) {
               AttributeModifier var8 = (AttributeModifier)var7.next();
               var6.applyModifier(var8);
            }
         }
      }

   }

   public void handleTimeUpdate(S03PacketTimeUpdate var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      this.gameController.theWorld.func_82738_a(var1.func_149366_c());
      this.gameController.theWorld.setWorldTime(var1.func_149365_d());
   }

   public void handleWindowProperty(S31PacketWindowProperty var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      EntityPlayerSP var2 = this.gameController.thePlayer;
      if (var2.openContainer != null && var2.openContainer.windowId == var1.func_149182_c()) {
         var2.openContainer.updateProgressBar(var1.func_149181_d(), var1.func_149180_e());
      }

   }

   static void access$2(NetHandlerPlayClient var0, Minecraft var1) {
      var0.gameController = var1;
   }

   public void handleUpdateScore(S3CPacketUpdateScore var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.gameController);
      Scoreboard var2 = this.clientWorldController.getScoreboard();
      ScoreObjective var3 = var2.getObjective(var1.func_149321_d());
      if (var1.func_180751_d() == S3CPacketUpdateScore.Action.CHANGE) {
         Score var4 = var2.getValueFromObjective(var1.func_149324_c(), var3);
         var4.setScorePoints(var1.func_149323_e());
      } else if (var1.func_180751_d() == S3CPacketUpdateScore.Action.REMOVE) {
         if (StringUtils.isNullOrEmpty(var1.func_149321_d())) {
            var2.func_178822_d(var1.func_149324_c(), (ScoreObjective)null);
         } else if (var3 != null) {
            var2.func_178822_d(var1.func_149324_c(), var3);
         }
      }

   }

   static final class SwitchAction {
      static final int[] field_178885_a;
      static final int[] field_178884_b = new int[S38PacketPlayerListItem.Action.values().length];
      private static final String __OBFID = "CL_00002620";

      static {
         try {
            field_178884_b[S38PacketPlayerListItem.Action.ADD_PLAYER.ordinal()] = 1;
         } catch (NoSuchFieldError var7) {
         }

         try {
            field_178884_b[S38PacketPlayerListItem.Action.UPDATE_GAME_MODE.ordinal()] = 2;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_178884_b[S38PacketPlayerListItem.Action.UPDATE_LATENCY.ordinal()] = 3;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_178884_b[S38PacketPlayerListItem.Action.UPDATE_DISPLAY_NAME.ordinal()] = 4;
         } catch (NoSuchFieldError var4) {
         }

         field_178885_a = new int[S45PacketTitle.Type.values().length];

         try {
            field_178885_a[S45PacketTitle.Type.TITLE.ordinal()] = 1;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_178885_a[S45PacketTitle.Type.SUBTITLE.ordinal()] = 2;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_178885_a[S45PacketTitle.Type.RESET.ordinal()] = 3;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
