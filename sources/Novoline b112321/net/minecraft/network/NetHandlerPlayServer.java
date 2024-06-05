package net.minecraft.network;

import com.google.common.collect.Lists;
import com.google.common.primitives.Doubles;
import com.google.common.primitives.Floats;
import com.google.common.util.concurrent.Futures;
import io.netty.util.concurrent.GenericFutureListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.Callable;
import net.aJs;
import net.minecraft.block.material.Material;
import net.minecraft.crash.CrashReport;
import net.minecraft.crash.CrashReportCategory;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.player.EntityPlayer$EnumChatVisibility;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetHandlerPlayServer$1;
import net.minecraft.network.NetHandlerPlayServer$2;
import net.minecraft.network.NetHandlerPlayServer$3;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketThreadUtil;
import net.minecraft.network.play.INetHandlerPlayServer;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.network.play.client.C02PacketUseEntity$Action;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C07PacketPlayerDigging$Action;
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
import net.minecraft.network.play.client.C16PacketClientStatus$EnumState;
import net.minecraft.network.play.client.C17PacketCustomPayload;
import net.minecraft.network.play.client.C18PacketSpectate;
import net.minecraft.network.play.client.C19PacketResourcePackStatus;
import net.minecraft.network.play.client.CPacketVehicleMove;
import net.minecraft.network.play.server.S00PacketKeepAlive;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S07PacketRespawn;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.network.play.server.S08PacketPlayerPosLook$EnumFlags;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S23PacketBlockChange;
import net.minecraft.network.play.server.S2FPacketSetSlot;
import net.minecraft.network.play.server.S32PacketConfirmTransaction;
import net.minecraft.network.play.server.S3APacketTabComplete;
import net.minecraft.network.play.server.S40PacketDisconnect;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.management.UserListBansEntry;
import net.minecraft.stats.AchievementList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.util.ITickable;
import net.minecraft.util.IntHashMap;
import net.minecraft.util.ReportedException;
import net.minecraft.world.WorldServer;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
   private static final Logger LOGGER = LogManager.getLogger();
   public final NetworkManager netManager;
   private final MinecraftServer serverController;
   public EntityPlayerMP playerEntity;
   private int networkTickCount;
   private int field_175090_f;
   private int floatingTickCount;
   private boolean field_147366_g;
   private int field_147378_h;
   private long lastPingTime;
   private long lastSentPingPacket;
   private int chatSpamThresholdCount;
   private int itemDropThreshold;
   private final IntHashMap field_147372_n = new IntHashMap();
   private double lastPosX;
   private double lastPosY;
   private double lastPosZ;
   private boolean hasMoved = true;

   public NetHandlerPlayServer(MinecraftServer var1, NetworkManager var2, EntityPlayerMP var3) {
      this.serverController = var1;
      this.netManager = var2;
      var2.setNetHandler(this);
      this.playerEntity = var3;
      var3.playerNetServerHandler = this;
   }

   public void update() {
      this.field_147366_g = false;
      ++this.networkTickCount;
      this.serverController.theProfiler.startSection("keepAlive");
      if((long)this.networkTickCount - this.lastSentPingPacket > 40L) {
         this.lastSentPingPacket = (long)this.networkTickCount;
         this.lastPingTime = this.currentTimeMillis();
         this.field_147378_h = (int)this.lastPingTime;
         this.sendPacket(new S00PacketKeepAlive(this.field_147378_h));
      }

      this.serverController.theProfiler.endSection();
      if(this.chatSpamThresholdCount > 0) {
         --this.chatSpamThresholdCount;
      }

      if(this.itemDropThreshold > 0) {
         --this.itemDropThreshold;
      }

      if(this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > (long)(this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60)) {
         this.kickPlayerFromServer("You have been idle for too long!");
      }

   }

   public NetworkManager getNetworkManager() {
      return this.netManager;
   }

   public void kickPlayerFromServer(String var1) {
      ChatComponentText var2 = new ChatComponentText(var1);
      this.netManager.sendPacket(new S40PacketDisconnect(var2), new NetHandlerPlayServer$1(this, var2), new GenericFutureListener[0]);
      this.netManager.disableAutoRead();
      Futures.getUnchecked(this.serverController.addScheduledTask(new NetHandlerPlayServer$2(this)));
   }

   public void processInput(C0CPacketInput var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.setEntityActionState(var1.getStrafeSpeed(), var1.getForwardSpeed(), var1.isJumping(), var1.isSneaking());
   }

   private boolean func_183006_b(C03PacketPlayer var1) {
      return !Doubles.isFinite(var1.getX()) || !Doubles.isFinite(var1.getY()) || !Doubles.isFinite(var1.getZ()) || !Floats.isFinite(var1.getPitch()) || !Floats.isFinite(var1.getYaw());
   }

   public void processPlayer(C03PacketPlayer var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      if(this.func_183006_b(var1)) {
         this.kickPlayerFromServer("Invalid move packet received");
      } else {
         WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
         this.field_147366_g = true;
         if(!this.playerEntity.playerConqueredTheEnd) {
            double var3 = this.playerEntity.posX;
            double var5 = this.playerEntity.posY;
            double var7 = this.playerEntity.posZ;
            double var9 = 0.0D;
            double var11 = var1.getX() - this.lastPosX;
            double var13 = var1.getY() - this.lastPosY;
            double var15 = var1.getZ() - this.lastPosZ;
            if(var1.isMoving()) {
               var9 = var11 * var11 + var13 * var13 + var15 * var15;
               if(!this.hasMoved && var9 < 0.25D) {
                  this.hasMoved = true;
               }
            }

            if(this.hasMoved) {
               this.field_175090_f = this.networkTickCount;
               if(this.playerEntity.ridingEntity != null) {
                  float var41 = this.playerEntity.rotationYaw;
                  float var18 = this.playerEntity.rotationPitch;
                  this.playerEntity.ridingEntity.updateRiderPosition();
                  double var42 = this.playerEntity.posX;
                  double var43 = this.playerEntity.posY;
                  double var44 = this.playerEntity.posZ;
                  if(var1.isRotating()) {
                     var41 = var1.getYaw();
                     var18 = var1.getPitch();
                  }

                  this.playerEntity.onGround = var1.isOnGround();
                  this.playerEntity.onUpdateEntity();
                  this.playerEntity.setPositionAndRotation(var42, var43, var44, var41, var18);
                  if(this.playerEntity.ridingEntity != null) {
                     this.playerEntity.ridingEntity.updateRiderPosition();
                  }

                  this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
                  if(this.playerEntity.ridingEntity != null) {
                     if(var9 > 4.0D) {
                        Entity var45 = this.playerEntity.ridingEntity;
                        this.playerEntity.playerNetServerHandler.sendPacket(new S18PacketEntityTeleport(var45));
                        this.setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                     }

                     this.playerEntity.ridingEntity.isAirBorne = true;
                  }

                  if(this.hasMoved) {
                     this.lastPosX = this.playerEntity.posX;
                     this.lastPosY = this.playerEntity.posY;
                     this.lastPosZ = this.playerEntity.posZ;
                  }

                  var2.updateEntity(this.playerEntity);
                  return;
               }

               if(this.playerEntity.isPlayerSleeping()) {
                  this.playerEntity.onUpdateEntity();
                  this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                  var2.updateEntity(this.playerEntity);
                  return;
               }

               double var17 = this.playerEntity.posY;
               this.lastPosX = this.playerEntity.posX;
               this.lastPosY = this.playerEntity.posY;
               this.lastPosZ = this.playerEntity.posZ;
               double var19 = this.playerEntity.posX;
               double var21 = this.playerEntity.posY;
               double var23 = this.playerEntity.posZ;
               float var25 = this.playerEntity.rotationYaw;
               float var26 = this.playerEntity.rotationPitch;
               if(var1.isMoving() && var1.getY() == -999.0D) {
                  var1.setMoving(false);
               }

               if(var1.isMoving()) {
                  var19 = var1.getX();
                  var21 = var1.getY();
                  var23 = var1.getZ();
                  if(Math.abs(var1.getX()) > 3.0E7D || Math.abs(var1.getZ()) > 3.0E7D) {
                     this.kickPlayerFromServer("Illegal position");
                     return;
                  }
               }

               if(var1.isRotating()) {
                  var25 = var1.getYaw();
                  var26 = var1.getPitch();
               }

               this.playerEntity.onUpdateEntity();
               this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var25, var26);
               if(!this.hasMoved) {
                  return;
               }

               double var27 = var19 - this.playerEntity.posX;
               double var29 = var21 - this.playerEntity.posY;
               double var31 = var23 - this.playerEntity.posZ;
               double var33 = this.playerEntity.motionX * this.playerEntity.motionX + this.playerEntity.motionY * this.playerEntity.motionY + this.playerEntity.motionZ * this.playerEntity.motionZ;
               double var35 = var27 * var27 + var29 * var29 + var31 * var31;
               if(var35 - var33 > 100.0D && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
                  LOGGER.warn(this.playerEntity.getName() + " moved too quickly! " + var27 + "," + var29 + "," + var31 + " (" + var27 + ", " + var29 + ", " + var31 + ")");
                  this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
                  return;
               }

               float var37 = 0.0625F;
               boolean var38 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(0.0625D, 0.0625D, 0.0625D)).isEmpty();
               if(this.playerEntity.onGround && !var1.isOnGround() && var29 > 0.0D) {
                  this.playerEntity.jump();
               }

               this.playerEntity.moveEntity(var27, var29, var31);
               this.playerEntity.onGround = var1.isOnGround();
               var27 = var19 - this.playerEntity.posX;
               var29 = var21 - this.playerEntity.posY;
               if(var29 > -0.5D || var29 < 0.5D) {
                  var29 = 0.0D;
               }

               var31 = var23 - this.playerEntity.posZ;
               var35 = var27 * var27 + var29 * var29 + var31 * var31;
               boolean var39 = false;
               if(var35 > 0.0625D && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
                  var39 = true;
                  LOGGER.warn(this.playerEntity.getName() + " moved wrongly!");
               }

               this.playerEntity.setPositionAndRotation(var19, var21, var23, var25, var26);
               this.playerEntity.addMovementStat(this.playerEntity.posX - var3, this.playerEntity.posY - var5, this.playerEntity.posZ - var7);
               if(!this.playerEntity.noClip) {
                  boolean var40 = var2.getCollidingBoundingBoxes(this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(0.0625D, 0.0625D, 0.0625D)).isEmpty();
                  if(!this.playerEntity.isPlayerSleeping()) {
                     this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, var25, var26);
                     return;
                  }
               }

               AxisAlignedBB var51 = this.playerEntity.getEntityBoundingBox().expand(0.0625D, 0.0625D, 0.0625D).addCoord(0.0D, -0.55D, 0.0D);
               if(!this.serverController.isFlightAllowed() && !this.playerEntity.abilities.isAllowFlying() && !var2.checkBlockCollision(var51)) {
                  if(var29 >= -0.03125D) {
                     ++this.floatingTickCount;
                     if(this.floatingTickCount > 80) {
                        LOGGER.warn(this.playerEntity.getName() + " was kicked for floating too long!");
                        this.kickPlayerFromServer("Flying is not enabled on this server");
                        return;
                     }
                  }
               } else {
                  this.floatingTickCount = 0;
               }

               this.playerEntity.onGround = var1.isOnGround();
               this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
               this.playerEntity.handleFalling(this.playerEntity.posY - var17, var1.isOnGround());
            } else if(this.networkTickCount - this.field_175090_f > 20) {
               this.setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
            }
         }
      }

   }

   public void setPlayerLocation(double var1, double var3, double var5, float var7, float var8) {
      this.setPlayerLocation(var1, var3, var5, var7, var8, Collections.emptySet());
   }

   public void setPlayerLocation(double var1, double var3, double var5, float var7, float var8, Set var9) {
      this.hasMoved = false;
      this.lastPosX = var1;
      this.lastPosY = var3;
      this.lastPosZ = var5;
      if(var9.contains(S08PacketPlayerPosLook$EnumFlags.X)) {
         this.lastPosX += this.playerEntity.posX;
      }

      if(var9.contains(S08PacketPlayerPosLook$EnumFlags.Y)) {
         this.lastPosY += this.playerEntity.posY;
      }

      if(var9.contains(S08PacketPlayerPosLook$EnumFlags.Z)) {
         this.lastPosZ += this.playerEntity.posZ;
      }

      float var10 = var7;
      float var11 = var8;
      if(var9.contains(S08PacketPlayerPosLook$EnumFlags.Y_ROT)) {
         var10 = var7 + this.playerEntity.rotationYaw;
      }

      if(var9.contains(S08PacketPlayerPosLook$EnumFlags.X_ROT)) {
         var11 = var8 + this.playerEntity.rotationPitch;
      }

      this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, var10, var11);
      this.playerEntity.playerNetServerHandler.sendPacket(new S08PacketPlayerPosLook(var1, var3, var5, var7, var8, var9));
   }

   public void processPlayerDigging(C07PacketPlayerDigging var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      BlockPos var3 = var1.getPosition();
      this.playerEntity.markPlayerActive();
      switch(NetHandlerPlayServer$3.$SwitchMap$net$minecraft$network$play$client$C07PacketPlayerDigging$Action[var1.getStatus().ordinal()]) {
      case 1:
         if(!this.playerEntity.isSpectator()) {
            this.playerEntity.dropOneItem(false);
         }

         return;
      case 2:
         if(!this.playerEntity.isSpectator()) {
            this.playerEntity.dropOneItem(true);
         }

         return;
      case 3:
         this.playerEntity.stopUsingItem();
         return;
      case 4:
      case 5:
      case 6:
         double var4 = this.playerEntity.posX - ((double)var3.getX() + 0.5D);
         double var6 = this.playerEntity.posY - ((double)var3.getY() + 0.5D) + 1.5D;
         double var8 = this.playerEntity.posZ - ((double)var3.getZ() + 0.5D);
         double var10 = var4 * var4 + var6 * var6 + var8 * var8;
         if(var10 > 36.0D) {
            return;
         } else if(var3.getY() >= this.serverController.getBuildLimit()) {
            return;
         } else {
            if(var1.getStatus() == C07PacketPlayerDigging$Action.START_DESTROY_BLOCK) {
               if(!this.serverController.isBlockProtected(var2, var3, this.playerEntity) && var2.getWorldBorder().contains(var3)) {
                  this.playerEntity.theItemInWorldManager.onBlockClicked(var3, var1.getFacing());
               } else {
                  this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var3));
               }
            } else {
               if(var1.getStatus() == C07PacketPlayerDigging$Action.STOP_DESTROY_BLOCK) {
                  this.playerEntity.theItemInWorldManager.blockRemoving(var3);
               } else if(var1.getStatus() == C07PacketPlayerDigging$Action.ABORT_DESTROY_BLOCK) {
                  this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
               }

               if(var2.getBlockState(var3).getBlock().getMaterial() != Material.air) {
                  this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var3));
               }
            }

            return;
         }
      default:
         throw new IllegalArgumentException("Invalid player action");
      }
   }

   public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      ItemStack var3 = this.playerEntity.inventory.getCurrentItem();
      boolean var4 = false;
      BlockPos var5 = var1.getPosition();
      EnumFacing var6 = EnumFacing.getFront(var1.getPlacedBlockDirection());
      this.playerEntity.markPlayerActive();
      if(var1.getPlacedBlockDirection() != 255) {
         if(var5.getY() < this.serverController.getBuildLimit() - 1 || var6 != EnumFacing.UP && var5.getY() < this.serverController.getBuildLimit()) {
            if(this.hasMoved && this.playerEntity.getDistanceSq((double)var5.getX() + 0.5D, (double)var5.getY() + 0.5D, (double)var5.getZ() + 0.5D) < 64.0D && !this.serverController.isBlockProtected(var2, var5, this.playerEntity) && var2.getWorldBorder().contains(var5)) {
               aJs.a(this.playerEntity.theItemInWorldManager, this.playerEntity, var2, var3, var5, var6, var1.getPlacedBlockOffsetX(), var1.getPlacedBlockOffsetY(), var1.getPlacedBlockOffsetZ());
            }

            var4 = true;
         } else {
            ChatComponentTranslation var7 = new ChatComponentTranslation("build.tooHigh", new Object[]{Integer.valueOf(this.serverController.getBuildLimit())});
            var7.getChatStyle().setColor(EnumChatFormatting.RED);
            this.playerEntity.playerNetServerHandler.sendPacket(new S02PacketChat(var7));
            var4 = true;
         }

         this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var5));
         this.playerEntity.playerNetServerHandler.sendPacket(new S23PacketBlockChange(var2, var5.offset(var6)));
         var3 = this.playerEntity.inventory.getCurrentItem();
         if(var3.stackSize == 0) {
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
            var3 = null;
         }

         if(var3.getMaxItemUseDuration() == 0) {
            this.playerEntity.isChangingQuantityOnly = true;
            this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.d(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
            Slot var11 = this.playerEntity.openContainer.getSlotFromInventory(this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
            this.playerEntity.openContainer.detectAndSendChanges();
            this.playerEntity.isChangingQuantityOnly = false;
            if(!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), var1.getStack())) {
               this.sendPacket(new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, var11.slotNumber, this.playerEntity.inventory.getCurrentItem()));
            }
         }

      }
   }

   public void handleSpectate(C18PacketSpectate var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      if(this.playerEntity.isSpectator()) {
         Entity var2 = null;
         WorldServer[] var3 = this.serverController.worldServers;
         int var4 = var3.length;
         byte var5 = 0;
         if(var5 < var4) {
            WorldServer var6 = var3[var5];
            var2 = var1.getEntity(var6);
         }

         this.playerEntity.setSpectatingEntity(this.playerEntity);
         this.playerEntity.mountEntity((Entity)null);
         if(var2.worldObj != this.playerEntity.worldObj) {
            WorldServer var7 = this.playerEntity.getServerForPlayer();
            WorldServer var8 = (WorldServer)var2.worldObj;
            this.playerEntity.dimension = var2.dimension;
            this.sendPacket(new S07PacketRespawn(this.playerEntity.dimension, var7.getDifficulty(), var7.getWorldInfo().getTerrainType(), this.playerEntity.theItemInWorldManager.getGameType()));
            var7.removePlayerEntityDangerously(this.playerEntity);
            this.playerEntity.isDead = false;
            this.playerEntity.setLocationAndAngles(var2.posX, var2.posY, var2.posZ, var2.rotationYaw, var2.rotationPitch);
            if(this.playerEntity.isEntityAlive()) {
               var7.updateEntityWithOptionalForce(this.playerEntity, false);
               var8.spawnEntityInWorld(this.playerEntity);
               var8.updateEntityWithOptionalForce(this.playerEntity, false);
            }

            this.playerEntity.setWorld(var8);
            this.serverController.getConfigurationManager().preparePlayer(this.playerEntity, var7);
            this.playerEntity.setPositionAndUpdate(var2.posX, var2.posY, var2.posZ);
            this.playerEntity.theItemInWorldManager.setWorld(var8);
            this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, var8);
            this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
         } else {
            this.playerEntity.setPositionAndUpdate(var2.posX, var2.posY, var2.posZ);
         }
      }

   }

   public void handleResourcePackStatus(C19PacketResourcePackStatus var1) {
   }

   public void onDisconnect(IChatComponent var1) {
      LOGGER.info(this.playerEntity.getName() + " lost connection: " + var1);
      this.serverController.refreshStatusNextTick();
      ChatComponentTranslation var2 = new ChatComponentTranslation("multiplayer.player.left", new Object[]{this.playerEntity.getDisplayName()});
      var2.getChatStyle().setColor(EnumChatFormatting.YELLOW);
      this.serverController.getConfigurationManager().sendChatMsg(var2);
      this.playerEntity.mountEntityAndWakeUp();
      this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
      if(this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
         LOGGER.info("Stopping singleplayer server as player logged out");
         this.serverController.initiateShutdown();
      }

   }

   public void sendPacket(Packet var1) {
      if(var1 instanceof S02PacketChat) {
         S02PacketChat var2 = (S02PacketChat)var1;
         EntityPlayer$EnumChatVisibility var3 = this.playerEntity.getChatVisibility();
         if(var3 == EntityPlayer$EnumChatVisibility.HIDDEN) {
            return;
         }

         if(var3 == EntityPlayer$EnumChatVisibility.SYSTEM && !var2.isChat()) {
            return;
         }
      }

      NetHandlerPlayServer var10000 = this;

      try {
         var10000.netManager.sendPacket(var1);
      } catch (Throwable var5) {
         CrashReport var6 = CrashReport.makeCrashReport(var5, "Sending packet");
         CrashReportCategory var4 = var6.makeCategory("Packet being sent");
         var4.addCrashSectionCallable("Packet class", NetHandlerPlayServer::lambda$sendPacket$0);
         throw new ReportedException(var6);
      }
   }

   public void processHeldItemChange(C09PacketHeldItemChange var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      if(var1.getSlotId() >= 0 && var1.getSlotId() < InventoryPlayer.getHotbarSize()) {
         this.playerEntity.inventory.currentItem = var1.getSlotId();
         this.playerEntity.markPlayerActive();
      } else {
         LOGGER.warn(this.playerEntity.getName() + " tried to set an invalid carried item");
      }

   }

   public void processChatMessage(C01PacketChatMessage var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      if(this.playerEntity.getChatVisibility() == EntityPlayer$EnumChatVisibility.HIDDEN) {
         ChatComponentTranslation var2 = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
         var2.getChatStyle().setColor(EnumChatFormatting.RED);
         this.sendPacket(new S02PacketChat(var2));
      } else {
         this.playerEntity.markPlayerActive();
         String var4 = var1.getMessage();
         var4 = StringUtils.normalizeSpace(var4);

         for(int var3 = 0; var3 < var4.length(); ++var3) {
            if(!ChatAllowedCharacters.isAllowedCharacter(var4.charAt(var3))) {
               this.kickPlayerFromServer("Illegal characters in chat");
               return;
            }
         }

         if(var4.startsWith("/")) {
            this.handleSlashCommand(var4);
         } else {
            ChatComponentTranslation var6 = new ChatComponentTranslation("chat.type.text", new Object[]{this.playerEntity.getDisplayName(), var4});
            this.serverController.getConfigurationManager().sendChatMsgImpl(var6, false);
         }

         this.chatSpamThresholdCount += 20;
         if(this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile())) {
            this.kickPlayerFromServer("disconnect.spam");
         }
      }

   }

   private void handleSlashCommand(String var1) {
      this.serverController.getCommandManager().executeCommand(this.playerEntity, var1);
   }

   public void handleAnimation(C0APacketAnimation var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      this.playerEntity.swingItem();
   }

   public void processEntityAction(C0BPacketEntityAction var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      switch(NetHandlerPlayServer$3.$SwitchMap$net$minecraft$network$play$client$C0BPacketEntityAction$Action[var1.getAction().ordinal()]) {
      case 1:
         this.playerEntity.setSneaking(true);
         break;
      case 2:
         this.playerEntity.setSneaking(false);
         break;
      case 3:
         this.playerEntity.setSprinting(true);
         break;
      case 4:
         this.playerEntity.setSprinting(false);
         break;
      case 5:
         this.playerEntity.wakeUpPlayer(false, true, true);
         this.hasMoved = false;
         break;
      case 6:
         if(this.playerEntity.ridingEntity instanceof EntityHorse) {
            ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(var1.getAuxData());
         }
         break;
      case 7:
         if(this.playerEntity.ridingEntity instanceof EntityHorse) {
            ((EntityHorse)this.playerEntity.ridingEntity).openGUI(this.playerEntity);
         }
         break;
      default:
         throw new IllegalArgumentException("Invalid client command!");
      }

   }

   public void processUseEntity(C02PacketUseEntity var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      Entity var3 = var1.getEntityFromWorld(var2);
      this.playerEntity.markPlayerActive();
      boolean var4 = this.playerEntity.canEntityBeSeen(var3);
      double var5 = 36.0D;
      var5 = 9.0D;
      if(this.playerEntity.getDistanceSqToEntity(var3) < var5) {
         if(var1.getAction() == C02PacketUseEntity$Action.INTERACT) {
            this.playerEntity.interactWith(var3);
         } else if(var1.getAction() == C02PacketUseEntity$Action.INTERACT_AT) {
            var3.interactAt(this.playerEntity, var1.getHitVec());
         } else if(var1.getAction() == C02PacketUseEntity$Action.ATTACK) {
            if(var3 instanceof EntityItem || var3 instanceof EntityXPOrb || var3 instanceof EntityArrow || var3 == this.playerEntity) {
               this.kickPlayerFromServer("Attempting to attack an invalid entity");
               this.serverController.logWarning("Player " + this.playerEntity.getName() + " tried to attack an invalid entity");
               return;
            }

            this.playerEntity.attackTargetEntityWithCurrentItem(var3);
         }
      }

   }

   public void processClientStatus(C16PacketClientStatus var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      C16PacketClientStatus$EnumState var2 = var1.getStatus();
      switch(NetHandlerPlayServer$3.$SwitchMap$net$minecraft$network$play$client$C16PacketClientStatus$EnumState[var2.ordinal()]) {
      case 1:
         if(this.playerEntity.playerConqueredTheEnd) {
            this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, true);
         } else if(this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
            if(this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
               this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
               this.serverController.deleteWorldAndStopServer();
            } else {
               UserListBansEntry var3 = new UserListBansEntry(this.playerEntity.getGameProfile(), (Date)null, "(You just lost the game)", (Date)null, "Death in Hardcore");
               this.serverController.getConfigurationManager().getBannedPlayers().addEntry(var3);
               this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it\'s game over!");
            }
         } else {
            if(this.playerEntity.getHealth() > 0.0F) {
               return;
            }

            this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, false);
         }
         break;
      case 2:
         this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
         break;
      case 3:
         this.playerEntity.triggerAchievement(AchievementList.openInventory);
      }

   }

   public void processCloseWindow(C0DPacketCloseWindow var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.closeContainer();
   }

   public void processClickWindow(C0EPacketClickWindow var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      if(this.playerEntity.openContainer.windowId == var1.getWindowId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity)) {
         if(this.playerEntity.isSpectator()) {
            ArrayList var2 = Lists.newArrayList();

            for(int var3 = 0; var3 < this.playerEntity.openContainer.inventorySlots.size(); ++var3) {
               var2.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(var3)).getStack());
            }

            this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, var2);
         } else {
            ItemStack var5 = this.playerEntity.openContainer.slotClick(var1.getSlotId(), var1.getUsedButton(), var1.getMode(), this.playerEntity);
            if(ItemStack.areItemStacksEqual(var1.getClickedItem(), var5)) {
               this.playerEntity.playerNetServerHandler.sendPacket(new S32PacketConfirmTransaction(var1.getWindowId(), var1.getActionNumber(), true));
               this.playerEntity.isChangingQuantityOnly = true;
               this.playerEntity.openContainer.detectAndSendChanges();
               this.playerEntity.updateHeldItem();
               this.playerEntity.isChangingQuantityOnly = false;
            } else {
               this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, Short.valueOf(var1.getActionNumber()));
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

   public void processEnchantItem(C11PacketEnchantItem var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      if(this.playerEntity.openContainer.windowId == var1.getWindowId() && this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.isSpectator()) {
         this.playerEntity.openContainer.enchantItem(this.playerEntity, var1.getButton());
         this.playerEntity.openContainer.detectAndSendChanges();
      }

   }

   public void processCreativeInventoryAction(C10PacketCreativeInventoryAction var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      if(this.playerEntity.theItemInWorldManager.isCreative()) {
         boolean var2 = var1.getSlotId() < 0;
         ItemStack var3 = var1.getStack();
         if(var3.hasTagCompound() && var3.getTagCompound().hasKey("BlockEntityTag", 10)) {
            NBTTagCompound var4 = var3.getTagCompound().getCompoundTag("BlockEntityTag");
            if(var4.hasKey("x") && var4.hasKey("y") && var4.hasKey("z")) {
               BlockPos var5 = new BlockPos(var4.getInteger("x"), var4.getInteger("y"), var4.getInteger("z"));
               TileEntity var6 = this.playerEntity.worldObj.getTileEntity(var5);
               NBTTagCompound var7 = new NBTTagCompound();
               var6.writeToNBT(var7);
               var7.removeTag("x");
               var7.removeTag("y");
               var7.removeTag("z");
               var3.setTagInfo("BlockEntityTag", var7);
            }
         }

         if(var1.getSlotId() >= 1 && var1.getSlotId() < 36 + InventoryPlayer.getHotbarSize()) {
            boolean var10 = true;
         } else {
            boolean var10000 = false;
         }

         boolean var8 = var3.getItem() != null;
         boolean var9 = var3.getMetadata() >= 0 && var3.stackSize <= 64 && var3.stackSize > 0;
         this.playerEntity.inventoryContainer.putStackInSlot(var1.getSlotId(), var3);
         this.playerEntity.inventoryContainer.setCanCraft(this.playerEntity, true);
      }

   }

   public void processConfirmTransaction(C0FPacketConfirmTransaction var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      Short var2 = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
      if(var1.getID() == var2.shortValue() && this.playerEntity.openContainer.windowId == var1.getWindowId() && !this.playerEntity.openContainer.getCanCraft(this.playerEntity) && !this.playerEntity.isSpectator()) {
         this.playerEntity.openContainer.setCanCraft(this.playerEntity, true);
      }

   }

   public void processUpdateSign(C12PacketUpdateSign var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.markPlayerActive();
      WorldServer var2 = this.serverController.worldServerForDimension(this.playerEntity.dimension);
      BlockPos var3 = var1.getPosition();
      if(var2.isBlockLoaded(var3)) {
         TileEntity var4 = var2.getTileEntity(var3);
         if(!(var4 instanceof TileEntitySign)) {
            return;
         }

         TileEntitySign var5 = (TileEntitySign)var4;
         if(!var5.getIsEditable() || var5.getPlayer() != this.playerEntity) {
            this.serverController.logWarning("Player " + this.playerEntity.getName() + " just tried to change non-editable sign");
            return;
         }

         IChatComponent[] var6 = var1.getLines();

         for(int var7 = 0; var7 < var6.length; ++var7) {
            var5.signText[var7] = new ChatComponentText(EnumChatFormatting.a(var6[var7].getUnformattedText()));
         }

         var5.markDirty();
         var2.markBlockForUpdate(var3);
      }

   }

   public void processKeepAlive(C00PacketKeepAlive var1) {
      if(var1.getKey() == this.field_147378_h) {
         int var2 = (int)(this.currentTimeMillis() - this.lastPingTime);
         this.playerEntity.ping = (this.playerEntity.ping * 3 + var2) / 4;
      }

   }

   private long currentTimeMillis() {
      return System.nanoTime() / 1000000L;
   }

   public void processPlayerAbilities(C13PacketPlayerAbilities var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.abilities.setFlying(var1.isFlying() && this.playerEntity.abilities.isAllowFlying());
   }

   public void processTabComplete(C14PacketTabComplete var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      ArrayList var2 = Lists.newArrayList();
      var2.addAll(this.serverController.getTabCompletions(this.playerEntity, var1.getMessage(), var1.getTargetBlock()));
      this.playerEntity.playerNetServerHandler.sendPacket(new S3APacketTabComplete((String[])var2.toArray(new String[0])));
   }

   public void processClientSettings(C15PacketClientSettings var1) {
      PacketThreadUtil.checkThreadAndEnqueue(var1, this, this.playerEntity.getServerForPlayer());
      this.playerEntity.handleClientSettings(var1);
   }

   public void processVanilla250Packet(C17PacketCustomPayload param1) {
      // $FF: Couldn't be decompiled
   }

   public void processVehicleMove(CPacketVehicleMove var1) {
   }

   private static String lambda$sendPacket$0(Packet var0) throws Exception {
      return var0.getClass().getCanonicalName();
   }

   private static Throwable a(Throwable var0) {
      return var0;
   }
}
