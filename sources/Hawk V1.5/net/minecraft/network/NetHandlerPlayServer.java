package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.util.concurrent.Futures;
import io.netty.buffer.Unpooled;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.Callable;
import net.minecraft.block.material.Material;
import net.minecraft.command.server.CommandBlockLogic;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityMinecartCommandBlock;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerBeacon;
import net.minecraft.inventory.ContainerMerchant;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemEditableBook;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemWritableBook;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.network.play.client.C0APacketAnimation;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.network.play.client.C0CPacketInput;
import net.minecraft.network.play.client.C0DPacketCloseWindow;
import net.minecraft.network.play.client.C0EPacketClickWindow;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
import net.minecraft.network.play.client.C11PacketEnchantItem;
import net.minecraft.network.play.client.C12PacketUpdateSign;
import net.minecraft.network.play.client.C13PacketPlayerAbilities;
import net.minecraft.network.play.client.C14PacketTabComplete;
import net.minecraft.network.play.client.C15PacketClientSettings;
import net.minecraft.network.play.client.C16PacketClientStatus;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.gui.IUpdatePlayerListBox;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityCommandBlock;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayServer implements IUpdatePlayerListBox, INetHandlerPlayServer {
   private long lastSentPingPacket;
   private int field_175090_f;
   private int chatSpamThresholdCount;
   private int itemDropThreshold;
   private boolean field_147366_g;
   private int floatingTickCount;
   private final MinecraftServer serverController;
   private long lastPingTime;
   private double lastPosX;
   private int field_147378_h;
   private int networkTickCount;
   private double lastPosY;
   private IntHashMap field_147372_n = new IntHashMap();
   private static final Logger logger = LogManager.getLogger();
   private double lastPosZ;
   public final NetworkManager netManager;
   private static final String __OBFID = "CL_00001452";
   private boolean hasMoved = true;
   public EntityPlayerMP playerEntity;

   public void func_175086_a(C19PacketResourcePackStatus var1) {
   }

   public void update() {
      this.field_147366_g = false;
      ++this.networkTickCount;
      this.serverController.theProfiler.startSection("keepAlive");
      if ((long)this.networkTickCount - this.lastSentPingPacket > 40L) {
         this.lastSentPingPacket = (long)this.networkTickCount;
         this.lastPingTime = this.currentTimeMillis();
         this.field_147378_h = (int)this.lastPingTime;
         this.sendPacket(new S00PacketKeepAlive(this.field_147378_h));
      }

      this.serverController.theProfiler.endSection();
      if (this.chatSpamThresholdCount > 0) {
         --this.chatSpamThresholdCount;
      }

      if (this.itemDropThreshold > 0) {
         --this.itemDropThreshold;
      }

      if (this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > (long)(this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60)) {
         this.kickPlayerFromServer("You have been idle for too long!");
      }

   }

   public void processPlayer(C03PacketPlayer var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      this.field_147366_g = true;
      if (!this.playerEntity.playerConqueredTheEnd) {
         double var3 = this.playerEntity.posX;
         double var5 = this.playerEntity.posY;
         double var7 = this.playerEntity.posZ;
         double var9 = 0.0D;
         double var11 = var1.getPositionX() - this.lastPosX;
         double var13 = var1.getPositionY() - this.lastPosY;
         double var15 = var1.getPositionZ() - this.lastPosZ;
         if (var1.func_149466_j()) {
            var9 = var11 * var11 + var13 * var13 + var15 * var15;
            if (!this.hasMoved && var9 < 0.25D) {
               this.hasMoved = true;
            }
         }

         if (this.hasMoved) {
            this.field_175090_f = this.networkTickCount;
            double var17;
            double var19;
            double var21;
            if (this.playerEntity.ridingEntity != null) {
               float var48 = this.playerEntity.rotationYaw;
               float var24 = this.playerEntity.rotationPitch;
               this.playerEntity.ridingEntity.updateRiderPosition();
               var17 = this.playerEntity.posX;
               var19 = this.playerEntity.posY;
               var21 = this.playerEntity.posZ;
               if (var1.getRotating()) {
                  var48 = var1.getYaw();
                  var24 = var1.getPitch();
               }

               this.playerEntity.onGround = var1.func_149465_i();
               this.playerEntity.onUpdateEntity();
               this.playerEntity.setPositionAndRotation(var17, var19, var21, var48, var24);
               if (this.playerEntity.ridingEntity != null) {
                  this.playerEntity.ridingEntity.updateRiderPosition();
               }

               this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
               if (this.playerEntity.ridingEntity != null) {
                  if (var9 > 4.0D) {
                     Entity var49 = this.playerEntity.ridingEntity;
                     this.playerEntity.playerNetServerHandler.sendPacket(new S18PacketEntityTeleport(var49));
                     this.setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                  }

                  this.playerEntity.ridingEntity.isAirBorne = true;
               }

               if (this.hasMoved) {
                  this.lastPosX = this.playerEntity.posX;
                  this.lastPosY = this.playerEntity.posY;
                  this.lastPosZ = this.playerEntity.posZ;
               }

               var2.updateEntity(this.playerEntity);
               return;
            }

            if (this.playerEntity.isPlayerSleeping()) {
               this.playerEntity.onUpdateEntity();
               this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
               var2.updateEntity(this.playerEntity);
               return;
            }

            double var23 = this.playerEntity.posY;
            this.lastPosX = this.playerEntity.posX;
            this.lastPosY = this.playerEntity.posY;
            this.lastPosZ = this.playerEntity.posZ;
            var17 = this.playerEntity.posX;
            var19 = this.playerEntity.posY;
            var21 = this.playerEntity.posZ;
            float var25 = this.playerEntity.rotationYaw;
            float var26 = this.playerEntity.rotationPitch;
            if (var1.func_149466_j() && var1.getPositionY() == -999.0D) {
               var1.func_149469_a(false);
            }

            if (var1.func_149466_j()) {
               var17 = var1.getPositionX();
               var19 = var1.getPositionY();
               var21 = var1.getPositionZ();
               if (Math.abs(var1.getPositionX()) > 3.0E7D || Math.abs(var1.getPositionZ()) > 3.0E7D) {
                  this.kickPlayerFromServer("Illegal position");
                  return;
               }
            }

            if (var1.getRotating()) {
               var25 = var1.getYaw();
               var26 = var1.getPitch();
            }

            this.playerEntity.onUpdateEntity();
            this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var25, var26);
            if (!this.hasMoved) {
               return;
            }

            double var27 = var17 - this.playerEntity.posX;
            double var29 = var19 - this.playerEntity.posY;
            double var31 = var21 - this.playerEntity.posZ;
            double var33 = Math.min(Math.abs(var27), Math.abs(this.playerEntity.motionX));
            double var35 = Math.min(Math.abs(var29), Math.abs(this.playerEntity.motionY));
            double var37 = Math.min(Math.abs(var31), Math.abs(this.playerEntity.motionZ));
            double var39 = var33 * var33 + var35 * var35 + var37 * var37;
            if (var39 > 100.0D && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
               logger.warn(String.valueOf((new StringBuilder(String.valueOf(this.playerEntity.getName()))).append(" moved too quickly! ").append(var27).append(",").append(var29).append(",").append(var31).append(" (").append(var33).append(", ").append(var35).append(", ").append(var37).append(")")));
               this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
               return;
            }

            float var41 = 0.0625F;
            boolean var42 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract((double)var41, (double)var41, (double)var41)).isEmpty();
            if (this.playerEntity.onGround && !var1.func_149465_i() && var29 > 0.0D) {
               this.playerEntity.jump();
            }

            this.playerEntity.moveEntity(var27, var29, var31);
            this.playerEntity.onGround = var1.func_149465_i();
            double var43 = var29;
            var27 = var17 - this.playerEntity.posX;
            var29 = var19 - this.playerEntity.posY;
            if (var29 > -0.5D || var29 < 0.5D) {
               var29 = 0.0D;
            }

            var31 = var21 - this.playerEntity.posZ;
            var39 = var27 * var27 + var29 * var29 + var31 * var31;
            boolean var45 = false;
            if (var39 > 0.0625D && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
               var45 = true;
               logger.warn(String.valueOf((new StringBuilder(String.valueOf(this.playerEntity.getName()))).append(" moved wrongly!")));
            }

            this.playerEntity.setPositionAndRotation(var17, var19, var21, var25, var26);
            this.playerEntity.addMovementStat(this.playerEntity.posX - var3, this.playerEntity.posY - var5, this.playerEntity.posZ - var7);
            if (!this.playerEntity.noClip) {
               boolean var46 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract((double)var41, (double)var41, (double)var41)).isEmpty();
               if (var42 && (var45 || !var46) && !this.playerEntity.isPlayerSleeping()) {
                  this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, var25, var26);
                  return;
               }
            }

            AxisAlignedBB var47 = this.playerEntity.getEntityBoundingBox().expand((double)var41, (double)var41, (double)var41).addCoord(0.0D, -0.55D, 0.0D);
            if (!this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying && !var2.checkBlockCollision(var47)) {
               if (var43 >= -0.03125D) {
                  ++this.floatingTickCount;
                  if (this.floatingTickCount > 80) {
                     logger.warn(String.valueOf((new StringBuilder(String.valueOf(this.playerEntity.getName()))).append(" was kicked for floating too long!")));
                     this.kickPlayerFromServer("Flying is not enabled on this server");
                     return;
                  }
               }
            } else {
               this.floatingTickCount = 0;
            }

            this.playerEntity.onGround = var1.func_149465_i();
            this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
            this.playerEntity.handleFalling(this.playerEntity.posY - var23, var1.func_149465_i());
         } else if (this.networkTickCount - this.field_175090_f > 20) {
            this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
         }
      }

   }

   public void processClientStatus(C16PacketClientStatus var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      C16PacketClientStatus.EnumState var2 = var1.getStatus();
      switch(var2) {
      case PERFORM_RESPAWN:
         if (this.playerEntity.playerConqueredTheEnd) {
            this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, true);
         } else if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
            if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
               this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
               this.serverController.deleteWorldAndStopServer();
            } else {
               UserListBansEntry var3 = new UserListBansEntry(this.playerEntity.getGameProfile(), (Date)null, "(You just lost the game)", (Date)null, "Death in Hardcore");
               this.serverController.getConfigurationManager().getBannedPlayers().addEntry(var3);
               this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
            }
         } else {
            if (this.playerEntity.getHealth() > 0.0F) {
               return;
            }

            this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, false);
         }
         break;
      case REQUEST_STATS:
         this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
         break;
      case OPEN_INVENTORY_ACHIEVEMENT:
         this.playerEntity.triggerAchievement(AchievementList.openInventory);
      }

   }

   public void processClientSettings(C15PacketClientSettings var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.handleClientSettings(var1);
   }

   public void processUseEntity(C02PacketUseEntity var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      Entity var3 = var1.getEntityFromWorld(var2);
      this.playerEntity.markPlayerActive();
      if (var3 != null) {
         boolean var4 = this.playerEntity.canEntityBeSeen(var3);
         double var5 = 36.0D;
         if (!var4) {
            var5 = 9.0D;
         }

         if (this.playerEntity.getDistanceSqToEntity(var3) < var5) {
            if (var1.getAction() == C02PacketUseEntity.Action.INTERACT) {
               this.playerEntity.interactWith(var3);
            } else if (var1.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
               var3.func_174825_a(this.playerEntity, var1.func_179712_b());
            } else if (var1.getAction() == C02PacketUseEntity.Action.ATTACK) {
               if (var3 instanceof EntityItem || var3 instanceof EntityXPOrb || var3 instanceof EntityArrow || var3 == this.playerEntity) {
                  this.kickPlayerFromServer("Attempting to attack an invalid entity");
                  this.serverController.logWarning(String.valueOf((new StringBuilder("Player ")).append(this.playerEntity.getName()).append(" tried to attack an invalid entity")));
                  return;
               }

               this.playerEntity.attackTargetEntityWithCurrentItem(var3);
            }
         }
      }

   }

   public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
      boolean var4 = false;
      BlockPos var5 = var1.func_179724_a();
      EnumFacing var6 = EnumFacing.getFront(var1.getPlacedBlockDirection());
      this.playerEntity.markPlayerActive();
      if (var1.getPlacedBlockDirection() == 255) {
         if (var3 == null) {
            return;
         }

         this.playerEntity.theItemInWorldManager.tryUseItem(this.playerEntity, var2, var3);
      } else if (var5.getY() >= this.serverController.getBuildLimit() - 1 && (var6 == EnumFacing.UP || var5.getY() >= this.serverController.getBuildLimit())) {
         ChatComponentTranslation var7 = new ChatComponentTranslation("build.tooHigh", new Object[]{this.serverController.getBuildLimit()});
         var7.getChatStyle().setColor(EnumChatFormatting.RED);
         this.playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(var7));
         var4 = true;
      } else {
         if (this.hasMoved && this.playerEntity.getDistanceSq((double)var5.getX() + 0.5D, (double)var5.getY() + 0.5D, (double)var5.getZ() + 0.5D) < 64.0D && !this.serverController.isBlockProtected(var2, var5, this.playerEntity) && var2.getWorldBorder().contains(var5)) {
            this.playerEntity.theItemInWorldManager.func_180236_a(this.playerEntity, var2, var3, var5, var6, var1.getPlacedBlockOffsetX(), var1.getPlacedBlockOffsetY(), var1.getPlacedBlockOffsetZ());
         }

         var4 = true;
      }

      if (var4) {
         this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var5));
         this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var5.offset(var6)));
      }

      var3 = this.playerEntity.inventory.getCurrentItem();
      if (var3 != null && var3.stackSize == 0) {
         this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
         var3 = null;
      }

      if (var3 == null || var3.getMaxItemUseDuration() == 0) {
         this.playerEntity.isChangingQuantityOnly = true;
         this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
         Slot var8 = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
         this.playerEntity.openContainer.detectAndSendChanges();
         this.playerEntity.isChangingQuantityOnly = false;
         if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), var1.getStack())) {
            this.sendPacket(new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, var8.slotNumber, this.playerEntity.inventory.getCurrentItem()));
         }
      }

   }

   public void processConfirmTransaction(C0FPacketConfirmTransaction var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      Short var2 = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
      if (var2 != null && var1.getUid() == var2 && this.playerEntity.openContainer.windowId == var1.getId() && !this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.func_175149_v()) {
         this.playerEntity.openContainer.setCanCraft(this.playerEntity, true);
      }

   }

   public void func_175088_a(C18PacketSpectate var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      if (this.playerEntity.func_175149_v()) {
         Entity var2 = null;
         WorldServer[] var3 = this.serverController.worldServers;
         int var4 = var3.length;

         WorldServer var6;
         for(int var5 = 0; var5 < var4; ++var5) {
            var6 = var3[var5];
            if (var6 != null) {
               var2 = var1.func_179727_a(var6);
               if (var2 != null) {
                  break;
               }
            }
         }

         if (var2 != null) {
            this.playerEntity.func_175399_e(this.playerEntity);
            this.playerEntity.mountEntity((Entity)null);
            if (var2.worldObj != this.playerEntity.worldObj) {
               WorldServer var7 = this.playerEntity.getServerForPlayer();
               var6 = (WorldServer)var2.worldObj;
               this.playerEntity.dimension = var2.dimension;
               this.sendPacket(new S07PacketRespawn(this.playerEntity.dimension, var7.getDifficulty(), var7.getWorldInfo().getTerrainType(), this.playerEntity.theItemInWorldManager.getGameType()));
               var7.removePlayerEntityDangerously(this.playerEntity);
               this.playerEntity.isDead = false;
               this.playerEntity.setLocationAndAngles(var2.posX, var2.posY, var2.posZ, var2.rotationYaw, var2.rotationPitch);
               if (this.playerEntity.isEntityAlive()) {
                  var7.updateEntityWithOptionalForce(this.playerEntity, false);
                  var6.spawnEntityInWorld(this.playerEntity);
                  var6.updateEntityWithOptionalForce(this.playerEntity, false);
               }

               this.playerEntity.setWorld(var6);
               this.serverController.getConfigurationManager().func_72375_a(this.playerEntity, var7);
               this.playerEntity.setPositionAndUpdate(var2.posX, var2.posY, var2.posZ);
               this.playerEntity.theItemInWorldManager.setWorld(var6);
               this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, var6);
               this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
            } else {
               this.playerEntity.setPositionAndUpdate(var2.posX, var2.posY, var2.posZ);
            }
         }
      }

   }

   public void processCloseWindow(C0DPacketCloseWindow var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.closeContainer();
   }

   private void handleSlashCommand(String var1) {
      this.serverController.getCommandManager().executeCommand(this.playerEntity, var1);
   }

   public void sendPacket(Packet var1) {
      if (var1 instanceof S02PacketChat) {
         S02PacketChat var2 = (S02PacketChat)var1;
         EntityPlayer.EnumChatVisibility var3 = this.playerEntity.getChatVisibility();
         if (var3 == EntityPlayer.EnumChatVisibility.HIDDEN) {
            return;
         }

         if (var3 == EntityPlayer.EnumChatVisibility.SYSTEM && !var2.isChat()) {
            return;
         }
      }

      try {
         this.netManager.sendPacket(var1);
      } catch (Throwable var5) {
         CrashReport var6 = CrashReport.makeCrashReport(var5, "Sending packet");
         CrashReportCategory var4 = var6.makeCategory("Packet being sent");
         var4.addCrashSectionCallable("Packet class", new Callable(this, var1) {
            final NetHandlerPlayServer this$0;
            private static final String __OBFID = "CL_00002270";
            private final Packet val$packetIn;

            {
               this.this$0 = var1;
               this.val$packetIn = var2;
            }

            public String func_180225_a() {
               return this.val$packetIn.getClass().getCanonicalName();
            }

            public Object call() {
               return this.func_180225_a();
            }
         });
         throw new ReportedException(var6);
      }
   }

   public void func_175087_a(C0APacketAnimation var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      this.playerEntity.swingItem();
   }

   public void processHeldItemChange(C09PacketHeldItemChange var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      if (var1.getSlotId() >= 0 && var1.getSlotId() < InventoryPlayer.getHotbarSize()) {
         this.playerEntity.inventory.currentItem = var1.getSlotId();
         this.playerEntity.markPlayerActive();
      } else {
         logger.warn(String.valueOf((new StringBuilder(String.valueOf(this.playerEntity.getName()))).append(" tried to set an invalid carried item")));
      }

   }

   public NetworkManager getNetworkManager() {
      return this.netManager;
   }

   public void processInput(C0CPacketInput var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.setEntityActionState(var1.getStrafeSpeed(), var1.getForwardSpeed(), var1.isJumping(), var1.isSneaking());
   }

   public void processKeepAlive(C00PacketKeepAlive var1) {
      if (var1.getKey() == this.field_147378_h) {
         int var2 = (int)(this.currentTimeMillis() - this.lastPingTime);
         this.playerEntity.ping = (this.playerEntity.ping * 3 + var2) / 4;
      }

   }

   public void func_175089_a(double var1, double var3, double var5, float var7, float var8, Set var9) {
      this.hasMoved = false;
      this.lastPosX = var1;
      this.lastPosY = var3;
      this.lastPosZ = var5;
      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.X)) {
         this.lastPosX += this.playerEntity.posX;
      }

      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.Y)) {
         this.lastPosY += this.playerEntity.posY;
      }

      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.Z)) {
         this.lastPosZ += this.playerEntity.posZ;
      }

      float var10 = var7;
      float var11 = var8;
      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT)) {
         var10 = var7 + this.playerEntity.rotationYaw;
      }

      if (var9.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT)) {
         var11 = var8 + this.playerEntity.rotationPitch;
      }

      this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var10, var11);
      this.playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(var1, var3, var5, var7, var8, var9));
   }

   public void processPlayerAbilities(C13PacketPlayerAbilities var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.capabilities.isFlying = var1.isFlying() && this.playerEntity.capabilities.allowFlying;
   }

   public void processVanilla250Packet(C17PacketCustomPayload var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      PacketBuffer var2;
      ItemStack var3;
      ItemStack var4;
      if ("MC|BEdit".equals(var1.getChannelName())) {
         var2 = new PacketBuffer(Unpooled.wrappedBuffer(var1.getBufferData()));

         try {
            var3 = var2.readItemStackFromBuffer();
            if (var3 == null) {
               return;
            }

            if (!ItemWritableBook.validBookPageTagContents(var3.getTagCompound())) {
               throw new IOException("Invalid book tag!");
            }

            var4 = this.playerEntity.inventory.getCurrentItem();
            if (var4 != null) {
               if (var3.getItem() == Items.writable_book && var3.getItem() == var4.getItem()) {
                  var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages", 8));
               }

               return;
            }
         } catch (Exception var38) {
            logger.error("Couldn't handle book info", var38);
            return;
         } finally {
            var2.release();
         }

      } else if ("MC|BSign".equals(var1.getChannelName())) {
         var2 = new PacketBuffer(Unpooled.wrappedBuffer(var1.getBufferData()));

         try {
            var3 = var2.readItemStackFromBuffer();
            if (var3 != null) {
               if (!ItemEditableBook.validBookTagContents(var3.getTagCompound())) {
                  throw new IOException("Invalid book tag!");
               }

               var4 = this.playerEntity.inventory.getCurrentItem();
               if (var4 == null) {
                  return;
               }

               if (var3.getItem() == Items.written_book && var4.getItem() == Items.writable_book) {
                  var4.setTagInfo("author", new NBTTagString(this.playerEntity.getName()));
                  var4.setTagInfo("title", new NBTTagString(var3.getTagCompound().getString("title")));
                  var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages", 8));
                  var4.setItem(Items.written_book);
               }

               return;
            }

            return;
         } catch (Exception var40) {
            logger.error("Couldn't sign book", var40);
         } finally {
            var2.release();
         }

      } else {
         int var5;
         if ("MC|TrSel".equals(var1.getChannelName())) {
            try {
               var5 = var1.getBufferData().readInt();
               Container var6 = this.playerEntity.openContainer;
               if (var6 instanceof ContainerMerchant) {
                  ((ContainerMerchant)var6).setCurrentRecipeIndex(var5);
               }
            } catch (Exception var37) {
               logger.error("Couldn't select trade", var37);
            }
         } else if ("MC|AdvCdm".equals(var1.getChannelName())) {
            if (!this.serverController.isCommandBlockEnabled()) {
               this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
            } else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
               var2 = var1.getBufferData();

               try {
                  byte var42 = var2.readByte();
                  CommandBlockLogic var44 = null;
                  if (var42 == 0) {
                     TileEntity var7 = this.playerEntity.worldObj.getTileEntity(new BlockPos(var2.readInt(), var2.readInt(), var2.readInt()));
                     if (var7 instanceof TileEntityCommandBlock) {
                        var44 = ((TileEntityCommandBlock)var7).getCommandBlockLogic();
                     }
                  } else if (var42 == 1) {
                     Entity var45 = this.playerEntity.worldObj.getEntityByID(var2.readInt());
                     if (var45 instanceof EntityMinecartCommandBlock) {
                        var44 = ((EntityMinecartCommandBlock)var45).func_145822_e();
                     }
                  }

                  String var47 = var2.readStringFromBuffer(var2.readableBytes());
                  boolean var8 = var2.readBoolean();
                  if (var44 != null) {
                     var44.setCommand(var47);
                     var44.func_175573_a(var8);
                     if (!var8) {
                        var44.func_145750_b((IChatComponent)null);
                     }

                     var44.func_145756_e();
                     this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.setCommand.success", new Object[]{var47}));
                  }
               } catch (Exception var35) {
                  logger.error("Couldn't set command block", var35);
               } finally {
                  var2.release();
               }
            } else {
               this.playerEntity.addChatMessage(new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
            }
         } else if ("MC|Beacon".equals(var1.getChannelName())) {
            if (this.playerEntity.openContainer instanceof ContainerBeacon) {
               try {
                  var2 = var1.getBufferData();
                  var5 = var2.readInt();
                  int var46 = var2.readInt();
                  ContainerBeacon var48 = (ContainerBeacon)this.playerEntity.openContainer;
                  Slot var50 = var48.getSlot(0);
                  if (var50.getHasStack()) {
                     var50.decrStackSize(1);
                     IInventory var9 = var48.func_180611_e();
                     var9.setField(1, var5);
                     var9.setField(2, var46);
                     var9.markDirty();
                  }
               } catch (Exception var34) {
                  logger.error("Couldn't set beacon", var34);
               }
            }
         } else if ("MC|ItemName".equals(var1.getChannelName()) && this.playerEntity.openContainer instanceof ContainerRepair) {
            ContainerRepair var43 = (ContainerRepair)this.playerEntity.openContainer;
            if (var1.getBufferData() != null && var1.getBufferData().readableBytes() >= 1) {
               String var49 = ChatAllowedCharacters.filterAllowedCharacters(var1.getBufferData().readStringFromBuffer(32767));
               if (var49.length() <= 30) {
                  var43.updateItemName(var49);
               }
            } else {
               var43.updateItemName("");
            }
         }

      }
   }

   public void setPlayerLocation(double var1, double var3, double var5, float var7, float var8) {
      this.func_175089_a(var1, var3, var5, var7, var8, Collections.emptySet());
   }

   public void processPlayerDigging(C07PacketPlayerDigging var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      BlockPos var3 = var1.func_179715_a();
      this.playerEntity.markPlayerActive();
      switch(var1.func_180762_c()) {
      case DROP_ITEM:
         if (!this.playerEntity.func_175149_v()) {
            this.playerEntity.dropOneItem(false);
         }

         return;
      case DROP_ALL_ITEMS:
         if (!this.playerEntity.func_175149_v()) {
            this.playerEntity.dropOneItem(true);
         }

         return;
      case RELEASE_USE_ITEM:
         this.playerEntity.stopUsingItem();
         return;
      case START_DESTROY_BLOCK:
      case ABORT_DESTROY_BLOCK:
      case STOP_DESTROY_BLOCK:
         double var4 = this.playerEntity.posX - ((double)var3.getX() + 0.5D);
         double var6 = this.playerEntity.posY - ((double)var3.getY() + 0.5D) + 1.5D;
         double var8 = this.playerEntity.posZ - ((double)var3.getZ() + 0.5D);
         double var10 = var4 * var4 + var6 * var6 + var8 * var8;
         if (var10 > 36.0D) {
            return;
         } else if (var3.getY() >= this.serverController.getBuildLimit()) {
            return;
         } else {
            if (var1.func_180762_c() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
               if (!this.serverController.isBlockProtected(var2, var3, this.playerEntity) && var2.getWorldBorder().contains(var3)) {
                  this.playerEntity.theItemInWorldManager.func_180784_a(var3, var1.func_179714_b());
               } else {
                  this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var3));
               }
            } else {
               if (var1.func_180762_c() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
                  this.playerEntity.theItemInWorldManager.func_180785_a(var3);
               } else if (var1.func_180762_c() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
                  this.playerEntity.theItemInWorldManager.func_180238_e();
               }

               if (var2.getBlockState(var3).getBlock().getMaterial() != Material.air) {
                  this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var3));
               }
            }

            return;
         }
      default:
         throw new IllegalArgumentException("Invalid player action");
      }
   }

   public void processTabComplete(C14PacketTabComplete var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      ArrayList var2 = Lists.newArrayList();
      Iterator var3 = this.serverController.func_180506_a(this.playerEntity, var1.getMessage(), var1.func_179709_b()).iterator();

      while(var3.hasNext()) {
         String var4 = (String)var3.next();
         var2.add(var4);
      }

      this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete((String[])var2.toArray(new String[var2.size()])));
   }

   public void onDisconnect(IChatComponent var1) {
      logger.info(String.valueOf((new StringBuilder(String.valueOf(this.playerEntity.getName()))).append(" lost connection: ").append(var1)));
      this.serverController.refreshStatusNextTick();
      ChatComponentTranslation var2 = new ChatComponentTranslation("multiplayer.player.left", new Object[]{this.playerEntity.getDisplayName()});
      var2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
      this.serverController.getConfigurationManager().sendChatMsg(var2);
      this.playerEntity.mountEntityAndWakeUp();
      this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
      if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
         logger.info("Stopping singleplayer server as player logged out");
         this.serverController.initiateShutdown();
      }

   }

   public void kickPlayerFromServer(String var1) {
      ChatComponentText var2 = new ChatComponentText(var1);
      this.netManager.sendPacket(new S40PacketDisconnect(var2), new GenericFutureListener(this, var2) {
         private static final String __OBFID = "CL_00001453";
         final NetHandlerPlayServer this$0;
         private final ChatComponentText val$var2;

         public void operationComplete(Future var1) {
            this.this$0.netManager.closeChannel(this.val$var2);
         }

         {
            this.this$0 = var1;
            this.val$var2 = var2;
         }
      });
      this.netManager.disableAutoRead();
      Futures.getUnchecked(this.serverController.addScheduledTask(new Runnable(this) {
         final NetHandlerPlayServer this$0;
         private static final String __OBFID = "CL_00001454";

         {
            this.this$0 = var1;
         }

         public void run() {
            this.this$0.netManager.checkDisconnected();
         }
      }));
   }

   public void processCreativeInventoryAction(C10PacketCreativeInventoryAction var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      if (this.playerEntity.theItemInWorldManager.isCreative()) {
         boolean var2 = var1.getSlotId() < 0;
         ItemStack var3 = var1.getStack();
         if (var3 != null && var3.hasTagCompound() && var3.getTagCompound().hasKey("BlockEntityTag", 10)) {
            NBTTagCompound var4 = var3.getTagCompound().getCompoundTag("BlockEntityTag");
            if (var4.hasKey("x") && var4.hasKey("y") && var4.hasKey("z")) {
               BlockPos var5 = new BlockPos(var4.getInteger("x"), var4.getInteger("y"), var4.getInteger("z"));
               TileEntity var6 = this.playerEntity.worldObj.getTileEntity(var5);
               if (var6 != null) {
                  NBTTagCompound var7 = new NBTTagCompound();
                  var6.writeToNBT(var7);
                  var7.removeTag("x");
                  var7.removeTag("y");
                  var7.removeTag("z");
                  var3.setTagInfo("BlockEntityTag", var7);
               }
            }
         }

         boolean var8 = var1.getSlotId() >= 1 && var1.getSlotId() < 36 + InventoryPlayer.getHotbarSize();
         boolean var9 = var3 == null || var3.getItem() != null;
         boolean var10 = var3 == null || var3.getMetadata() >= 0 && var3.stackSize <= 64 && var3.stackSize > 0;
         if (var8 && var9 && var10) {
            if (var3 == null) {
               this.playerEntity.inventoryContainer.putStackInSlot(var1.getSlotId(), (ItemStack)null);
            } else {
               this.playerEntity.inventoryContainer.putStackInSlot(var1.getSlotId(), var3);
            }

            this.playerEntity.inventoryContainer.setCanCraft(this.playerEntity, true);
         } else if (var2 && var9 && var10 && this.itemDropThreshold < 200) {
            this.itemDropThreshold += 20;
            EntityItem var11 = this.playerEntity.dropPlayerItemWithRandomChoice(var3, true);
            if (var11 != null) {
               var11.setAgeToCreativeDespawnTime();
            }
         }
      }

   }

   public NetHandlerPlayServer(MinecraftServer var1, NetworkManager var2, EntityPlayerMP var3) {
      this.serverController = var1;
      this.netManager = var2;
      var2.setNetHandler(this);
      this.playerEntity = var3;
      var3.playerNetServerHandler = this;
   }

   private long currentTimeMillis() {
      return System.nanoTime() / 1000000L;
   }

   public void processClickWindow(C0EPacketClickWindow var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      if (this.playerEntity.openContainer.windowId == var1.getWindowId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity)) {
         if (this.playerEntity.func_175149_v()) {
            ArrayList var2 = Lists.newArrayList();

            for(int var3 = 0; var3 < this.playerEntity.openContainer.inventorySlots.size(); ++var3) {
               var2.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(var3)).getStack());
            }

            this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, var2);
         } else {
            ItemStack var5 = this.playerEntity.openContainer.slotClick(var1.getSlotId(), var1.getUsedButton(), var1.getMode(), this.playerEntity);
            if (ItemStack.areItemStacksEqual(var1.getClickedItem(), var5)) {
               this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(var1.getWindowId(), var1.getActionNumber(), true));
               this.playerEntity.isChangingQuantityOnly = true;
               this.playerEntity.openContainer.detectAndSendChanges();
               this.playerEntity.updateHeldItem();
               this.playerEntity.isChangingQuantityOnly = false;
            } else {
               this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, var1.getActionNumber());
               this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(var1.getWindowId(), var1.getActionNumber(), false));
               this.playerEntity.openContainer.setCanCraft(this.playerEntity, false);
               ArrayList var6 = Lists.newArrayList();

               for(int var4 = 0; var4 < this.playerEntity.openContainer.inventorySlots.size(); ++var4) {
                  var6.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(var4)).getStack());
               }

               this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, var6);
            }
         }
      }

   }

   public void processEntityAction(C0BPacketEntityAction var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      switch(var1.func_180764_b()) {
      case START_SNEAKING:
         this.playerEntity.setSneaking(true);
         break;
      case STOP_SNEAKING:
         this.playerEntity.setSneaking(false);
         break;
      case START_SPRINTING:
         this.playerEntity.setSprinting(true);
         break;
      case STOP_SPRINTING:
         this.playerEntity.setSprinting(false);
         break;
      case STOP_SLEEPING:
         this.playerEntity.wakeUpPlayer(false, true, true);
         this.hasMoved = false;
         break;
      case RIDING_JUMP:
         if (this.playerEntity.ridingEntity instanceof EntityHorse) {
            ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(var1.func_149512_e());
         }
         break;
      case OPEN_INVENTORY:
         if (this.playerEntity.ridingEntity instanceof EntityHorse) {
            ((EntityHorse)this.playerEntity.ridingEntity).openGUI(this.playerEntity);
         }
         break;
      default:
         throw new IllegalArgumentException("Invalid client command!");
      }

   }

   public void processChatMessage(C01PacketChatMessage var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
         ChatComponentTranslation var2 = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
         var2.getChatStyle().setColor(EnumChatFormatting.RED);
         this.sendPacket(new S02PacketChat(var2));
      } else {
         this.playerEntity.markPlayerActive();
         String var4 = var1.getMessage();
         var4 = StringUtils.normalizeSpace(var4);

         for(int var3 = 0; var3 < var4.length(); ++var3) {
            if (!ChatAllowedCharacters.isAllowedCharacter(var4.charAt(var3))) {
               this.kickPlayerFromServer("Illegal characters in chat");
               return;
            }
         }

         if (var4.startsWith("/")) {
            this.handleSlashCommand(var4);
         } else {
            ChatComponentTranslation var5 = new ChatComponentTranslation("chat.type.text", new Object[]{this.playerEntity.getDisplayName(), var4});
            this.serverController.getConfigurationManager().sendChatMsgImpl(var5, false);
         }

         this.chatSpamThresholdCount += 20;
         if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile())) {
            this.kickPlayerFromServer("disconnect.spam");
         }
      }

   }

   public void processEnchantItem(C11PacketEnchantItem var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      if (this.playerEntity.openContainer.windowId == var1.getId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.func_175149_v()) {
         this.playerEntity.openContainer.enchantItem(this.playerEntity, var1.getButton());
         this.playerEntity.openContainer.detectAndSendChanges();
      }

   }

   public void processUpdateSign(C12PacketUpdateSign var1) {
      PacketThreadUtil.func_180031_a(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      BlockPos var3 = var1.func_179722_a();
      if (var2.isBlockLoaded(var3)) {
         TileEntity var4 = var2.getTileEntity(var3);
         if (!(var4 instanceof TileEntitySign)) {
            return;
         }

         TileEntitySign var5 = (TileEntitySign)var4;
         if (!var5.getIsEditable() || var5.func_145911_b() != this.playerEntity) {
            this.serverController.logWarning(String.valueOf((new StringBuilder("Player ")).append(this.playerEntity.getName()).append(" just tried to change non-editable sign")));
            return;
         }

         System.arraycopy(var1.func_180768_b(), 0, var5.signText, 0, 4);
         var5.markDirty();
         var2.markBlockForUpdate(var3);
      }

   }

   static final class SwitchAction {
      static final int[] field_180224_a;
      static final int[] field_180222_b;
      static final int[] field_180223_c = new int[C16PacketClientStatus.EnumState.values().length];
      private static final String __OBFID = "CL_00002269";

      static {
         try {
            field_180223_c[C16PacketClientStatus.EnumState.PERFORM_RESPAWN.ordinal()] = 1;
         } catch (NoSuchFieldError var16) {
         }

         try {
            field_180223_c[C16PacketClientStatus.EnumState.REQUEST_STATS.ordinal()] = 2;
         } catch (NoSuchFieldError var15) {
         }

         try {
            field_180223_c[C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT.ordinal()] = 3;
         } catch (NoSuchFieldError var14) {
         }

         field_180222_b = new int[C0BPacketEntityAction.Action.values().length];

         try {
            field_180222_b[C0BPacketEntityAction.Action.START_SNEAKING.ordinal()] = 1;
         } catch (NoSuchFieldError var13) {
         }

         try {
            field_180222_b[C0BPacketEntityAction.Action.STOP_SNEAKING.ordinal()] = 2;
         } catch (NoSuchFieldError var12) {
         }

         try {
            field_180222_b[C0BPacketEntityAction.Action.START_SPRINTING.ordinal()] = 3;
         } catch (NoSuchFieldError var11) {
         }

         try {
            field_180222_b[C0BPacketEntityAction.Action.STOP_SPRINTING.ordinal()] = 4;
         } catch (NoSuchFieldError var10) {
         }

         try {
            field_180222_b[C0BPacketEntityAction.Action.STOP_SLEEPING.ordinal()] = 5;
         } catch (NoSuchFieldError var9) {
         }

         try {
            field_180222_b[C0BPacketEntityAction.Action.RIDING_JUMP.ordinal()] = 6;
         } catch (NoSuchFieldError var8) {
         }

         try {
            field_180222_b[C0BPacketEntityAction.Action.OPEN_INVENTORY.ordinal()] = 7;
         } catch (NoSuchFieldError var7) {
         }

         field_180224_a = new int[C07PacketPlayerDigging.Action.values().length];

         try {
            field_180224_a[C07PacketPlayerDigging.Action.DROP_ITEM.ordinal()] = 1;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_180224_a[C07PacketPlayerDigging.Action.DROP_ALL_ITEMS.ordinal()] = 2;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_180224_a[C07PacketPlayerDigging.Action.RELEASE_USE_ITEM.ordinal()] = 3;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_180224_a[C07PacketPlayerDigging.Action.START_DESTROY_BLOCK.ordinal()] = 4;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180224_a[C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK.ordinal()] = 5;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180224_a[C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK.ordinal()] = 6;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
