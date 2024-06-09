/*      */ package net.minecraft.network;
/*      */ import com.google.common.collect.Lists;
/*      */ import com.google.common.primitives.Doubles;
/*      */ import com.google.common.primitives.Floats;
/*      */ import com.google.common.util.concurrent.Futures;
/*      */ import io.netty.buffer.Unpooled;
/*      */ import io.netty.util.concurrent.Future;
/*      */ import io.netty.util.concurrent.GenericFutureListener;
/*      */ import java.io.IOException;
/*      */ import java.util.Collections;
/*      */ import java.util.List;
/*      */ import java.util.Set;
/*      */ import java.util.concurrent.Callable;
/*      */ import java.util.concurrent.Future;
/*      */ import net.minecraft.block.material.Material;
/*      */ import net.minecraft.command.ICommandSender;
/*      */ import net.minecraft.command.server.CommandBlockLogic;
/*      */ import net.minecraft.crash.CrashReport;
/*      */ import net.minecraft.crash.CrashReportCategory;
/*      */ import net.minecraft.entity.Entity;
/*      */ import net.minecraft.entity.EntityMinecartCommandBlock;
/*      */ import net.minecraft.entity.item.EntityItem;
/*      */ import net.minecraft.entity.passive.EntityHorse;
/*      */ import net.minecraft.entity.player.EntityPlayer;
/*      */ import net.minecraft.entity.player.EntityPlayerMP;
/*      */ import net.minecraft.entity.player.InventoryPlayer;
/*      */ import net.minecraft.init.Items;
/*      */ import net.minecraft.inventory.Container;
/*      */ import net.minecraft.inventory.ContainerBeacon;
/*      */ import net.minecraft.inventory.ContainerMerchant;
/*      */ import net.minecraft.inventory.ContainerRepair;
/*      */ import net.minecraft.inventory.IInventory;
/*      */ import net.minecraft.inventory.Slot;
/*      */ import net.minecraft.item.ItemEditableBook;
/*      */ import net.minecraft.item.ItemStack;
/*      */ import net.minecraft.item.ItemWritableBook;
/*      */ import net.minecraft.nbt.NBTBase;
/*      */ import net.minecraft.nbt.NBTTagCompound;
/*      */ import net.minecraft.nbt.NBTTagString;
/*      */ import net.minecraft.network.play.INetHandlerPlayServer;
/*      */ import net.minecraft.network.play.client.C00PacketKeepAlive;
/*      */ import net.minecraft.network.play.client.C01PacketChatMessage;
/*      */ import net.minecraft.network.play.client.C02PacketUseEntity;
/*      */ import net.minecraft.network.play.client.C03PacketPlayer;
/*      */ import net.minecraft.network.play.client.C07PacketPlayerDigging;
/*      */ import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
/*      */ import net.minecraft.network.play.client.C09PacketHeldItemChange;
/*      */ import net.minecraft.network.play.client.C0APacketAnimation;
/*      */ import net.minecraft.network.play.client.C0BPacketEntityAction;
/*      */ import net.minecraft.network.play.client.C0CPacketInput;
/*      */ import net.minecraft.network.play.client.C0DPacketCloseWindow;
/*      */ import net.minecraft.network.play.client.C0EPacketClickWindow;
/*      */ import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
/*      */ import net.minecraft.network.play.client.C10PacketCreativeInventoryAction;
/*      */ import net.minecraft.network.play.client.C11PacketEnchantItem;
/*      */ import net.minecraft.network.play.client.C12PacketUpdateSign;
/*      */ import net.minecraft.network.play.client.C13PacketPlayerAbilities;
/*      */ import net.minecraft.network.play.client.C14PacketTabComplete;
/*      */ import net.minecraft.network.play.client.C15PacketClientSettings;
/*      */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*      */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*      */ import net.minecraft.network.play.client.C18PacketSpectate;
/*      */ import net.minecraft.network.play.client.C19PacketResourcePackStatus;
/*      */ import net.minecraft.network.play.server.S00PacketKeepAlive;
/*      */ import net.minecraft.network.play.server.S02PacketChat;
/*      */ import net.minecraft.network.play.server.S07PacketRespawn;
/*      */ import net.minecraft.network.play.server.S08PacketPlayerPosLook;
/*      */ import net.minecraft.network.play.server.S18PacketEntityTeleport;
/*      */ import net.minecraft.network.play.server.S23PacketBlockChange;
/*      */ import net.minecraft.network.play.server.S2FPacketSetSlot;
/*      */ import net.minecraft.network.play.server.S32PacketConfirmTransaction;
/*      */ import net.minecraft.network.play.server.S3APacketTabComplete;
/*      */ import net.minecraft.network.play.server.S40PacketDisconnect;
/*      */ import net.minecraft.server.MinecraftServer;
/*      */ import net.minecraft.server.management.UserListBansEntry;
/*      */ import net.minecraft.server.management.UserListEntry;
/*      */ import net.minecraft.stats.StatBase;
/*      */ import net.minecraft.tileentity.TileEntity;
/*      */ import net.minecraft.tileentity.TileEntityCommandBlock;
/*      */ import net.minecraft.tileentity.TileEntitySign;
/*      */ import net.minecraft.util.AxisAlignedBB;
/*      */ import net.minecraft.util.BlockPos;
/*      */ import net.minecraft.util.ChatAllowedCharacters;
/*      */ import net.minecraft.util.ChatComponentText;
/*      */ import net.minecraft.util.ChatComponentTranslation;
/*      */ import net.minecraft.util.EnumChatFormatting;
/*      */ import net.minecraft.util.EnumFacing;
/*      */ import net.minecraft.util.IChatComponent;
/*      */ import net.minecraft.util.IThreadListener;
/*      */ import net.minecraft.util.ITickable;
/*      */ import net.minecraft.util.IntHashMap;
/*      */ import net.minecraft.util.ReportedException;
/*      */ import net.minecraft.world.World;
/*      */ import net.minecraft.world.WorldServer;
/*      */ import org.apache.commons.lang3.StringUtils;
/*      */ import org.apache.logging.log4j.LogManager;
/*      */ import org.apache.logging.log4j.Logger;
/*      */ 
/*      */ public class NetHandlerPlayServer implements INetHandlerPlayServer, ITickable {
/*  100 */   private static final Logger logger = LogManager.getLogger();
/*      */   
/*      */   public final NetworkManager netManager;
/*      */   
/*      */   private final MinecraftServer serverController;
/*      */   
/*      */   public EntityPlayerMP playerEntity;
/*      */   
/*      */   private int networkTickCount;
/*      */   
/*      */   private int field_175090_f;
/*      */   
/*      */   private int floatingTickCount;
/*      */   
/*      */   private boolean field_147366_g;
/*      */   
/*      */   private int field_147378_h;
/*      */   
/*      */   private long lastPingTime;
/*      */   
/*      */   private long lastSentPingPacket;
/*      */   private int chatSpamThresholdCount;
/*      */   private int itemDropThreshold;
/*  123 */   private IntHashMap<Short> field_147372_n = new IntHashMap();
/*      */   
/*      */   private double lastPosX;
/*      */   private double lastPosY;
/*      */   private double lastPosZ;
/*      */   private boolean hasMoved = true;
/*      */   
/*      */   public NetHandlerPlayServer(MinecraftServer server, NetworkManager networkManagerIn, EntityPlayerMP playerIn) {
/*  131 */     this.serverController = server;
/*  132 */     this.netManager = networkManagerIn;
/*  133 */     networkManagerIn.setNetHandler((INetHandler)this);
/*  134 */     this.playerEntity = playerIn;
/*  135 */     playerIn.playerNetServerHandler = this;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void update() {
/*  143 */     this.field_147366_g = false;
/*  144 */     this.networkTickCount++;
/*  145 */     this.serverController.theProfiler.startSection("keepAlive");
/*      */     
/*  147 */     if (this.networkTickCount - this.lastSentPingPacket > 40L) {
/*      */       
/*  149 */       this.lastSentPingPacket = this.networkTickCount;
/*  150 */       this.lastPingTime = currentTimeMillis();
/*  151 */       this.field_147378_h = (int)this.lastPingTime;
/*  152 */       sendPacket((Packet)new S00PacketKeepAlive(this.field_147378_h));
/*      */     } 
/*      */     
/*  155 */     this.serverController.theProfiler.endSection();
/*      */     
/*  157 */     if (this.chatSpamThresholdCount > 0)
/*      */     {
/*  159 */       this.chatSpamThresholdCount--;
/*      */     }
/*      */     
/*  162 */     if (this.itemDropThreshold > 0)
/*      */     {
/*  164 */       this.itemDropThreshold--;
/*      */     }
/*      */     
/*  167 */     if (this.playerEntity.getLastActiveTime() > 0L && this.serverController.getMaxPlayerIdleMinutes() > 0 && MinecraftServer.getCurrentTimeMillis() - this.playerEntity.getLastActiveTime() > (this.serverController.getMaxPlayerIdleMinutes() * 1000 * 60))
/*      */     {
/*  169 */       kickPlayerFromServer("You have been idle for too long!");
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public NetworkManager getNetworkManager() {
/*  175 */     return this.netManager;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void kickPlayerFromServer(String reason) {
/*  183 */     final ChatComponentText chatcomponenttext = new ChatComponentText(reason);
/*  184 */     this.netManager.sendPacket((Packet)new S40PacketDisconnect((IChatComponent)chatcomponenttext), new GenericFutureListener<Future<? super Void>>()
/*      */         {
/*      */           public void operationComplete(Future<? super Void> p_operationComplete_1_) throws Exception
/*      */           {
/*  188 */             NetHandlerPlayServer.this.netManager.closeChannel((IChatComponent)chatcomponenttext);
/*      */           }
/*  190 */         },  (GenericFutureListener<? extends Future<? super Void>>[])new GenericFutureListener[0]);
/*  191 */     this.netManager.disableAutoRead();
/*  192 */     Futures.getUnchecked((Future)this.serverController.addScheduledTask(new Runnable()
/*      */           {
/*      */             public void run()
/*      */             {
/*  196 */               NetHandlerPlayServer.this.netManager.checkDisconnected();
/*      */             }
/*      */           }));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processInput(C0CPacketInput packetIn) {
/*  207 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  208 */     this.playerEntity.setEntityActionState(packetIn.getStrafeSpeed(), packetIn.getForwardSpeed(), packetIn.isJumping(), packetIn.isSneaking());
/*      */   }
/*      */ 
/*      */   
/*      */   private boolean func_183006_b(C03PacketPlayer p_183006_1_) {
/*  213 */     return !(Doubles.isFinite(p_183006_1_.getPositionX()) && Doubles.isFinite(p_183006_1_.getPositionY()) && Doubles.isFinite(p_183006_1_.getPositionZ()) && Floats.isFinite(p_183006_1_.getPitch()) && Floats.isFinite(p_183006_1_.getYaw()));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayer(C03PacketPlayer packetIn) {
/*  221 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  223 */     if (func_183006_b(packetIn)) {
/*      */       
/*  225 */       kickPlayerFromServer("Invalid move packet received");
/*      */     }
/*      */     else {
/*      */       
/*  229 */       WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  230 */       this.field_147366_g = true;
/*      */       
/*  232 */       if (!this.playerEntity.playerConqueredTheEnd) {
/*      */         
/*  234 */         double d0 = this.playerEntity.posX;
/*  235 */         double d1 = this.playerEntity.posY;
/*  236 */         double d2 = this.playerEntity.posZ;
/*  237 */         double d3 = 0.0D;
/*  238 */         double d4 = packetIn.getPositionX() - this.lastPosX;
/*  239 */         double d5 = packetIn.getPositionY() - this.lastPosY;
/*  240 */         double d6 = packetIn.getPositionZ() - this.lastPosZ;
/*      */         
/*  242 */         if (packetIn.isMoving()) {
/*      */           
/*  244 */           d3 = d4 * d4 + d5 * d5 + d6 * d6;
/*      */           
/*  246 */           if (!this.hasMoved && d3 < 0.25D)
/*      */           {
/*  248 */             this.hasMoved = true;
/*      */           }
/*      */         } 
/*      */         
/*  252 */         if (this.hasMoved) {
/*      */           
/*  254 */           this.field_175090_f = this.networkTickCount;
/*      */           
/*  256 */           if (this.playerEntity.ridingEntity != null) {
/*      */             
/*  258 */             float f4 = this.playerEntity.rotationYaw;
/*  259 */             float f = this.playerEntity.rotationPitch;
/*  260 */             this.playerEntity.ridingEntity.updateRiderPosition();
/*  261 */             double d16 = this.playerEntity.posX;
/*  262 */             double d17 = this.playerEntity.posY;
/*  263 */             double d18 = this.playerEntity.posZ;
/*      */             
/*  265 */             if (packetIn.getRotating()) {
/*      */               
/*  267 */               f4 = packetIn.getYaw();
/*  268 */               f = packetIn.getPitch();
/*      */             } 
/*      */             
/*  271 */             this.playerEntity.onGround = packetIn.isOnGround();
/*  272 */             this.playerEntity.onUpdateEntity();
/*  273 */             this.playerEntity.setPositionAndRotation(d16, d17, d18, f4, f);
/*      */             
/*  275 */             if (this.playerEntity.ridingEntity != null)
/*      */             {
/*  277 */               this.playerEntity.ridingEntity.updateRiderPosition();
/*      */             }
/*      */             
/*  280 */             this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*      */             
/*  282 */             if (this.playerEntity.ridingEntity != null) {
/*      */               
/*  284 */               if (d3 > 4.0D) {
/*      */                 
/*  286 */                 Entity entity = this.playerEntity.ridingEntity;
/*  287 */                 this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S18PacketEntityTeleport(entity));
/*  288 */                 setPlayerLocation(this.playerEntity.posX, this.playerEntity.posY, this.playerEntity.posZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */               } 
/*      */               
/*  291 */               this.playerEntity.ridingEntity.isAirBorne = true;
/*      */             } 
/*      */             
/*  294 */             if (this.hasMoved) {
/*      */               
/*  296 */               this.lastPosX = this.playerEntity.posX;
/*  297 */               this.lastPosY = this.playerEntity.posY;
/*  298 */               this.lastPosZ = this.playerEntity.posZ;
/*      */             } 
/*      */             
/*  301 */             worldserver.updateEntity((Entity)this.playerEntity);
/*      */             
/*      */             return;
/*      */           } 
/*  305 */           if (this.playerEntity.isPlayerSleeping()) {
/*      */             
/*  307 */             this.playerEntity.onUpdateEntity();
/*  308 */             this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*  309 */             worldserver.updateEntity((Entity)this.playerEntity);
/*      */             
/*      */             return;
/*      */           } 
/*  313 */           double d7 = this.playerEntity.posY;
/*  314 */           this.lastPosX = this.playerEntity.posX;
/*  315 */           this.lastPosY = this.playerEntity.posY;
/*  316 */           this.lastPosZ = this.playerEntity.posZ;
/*  317 */           double d8 = this.playerEntity.posX;
/*  318 */           double d9 = this.playerEntity.posY;
/*  319 */           double d10 = this.playerEntity.posZ;
/*  320 */           float f1 = this.playerEntity.rotationYaw;
/*  321 */           float f2 = this.playerEntity.rotationPitch;
/*      */           
/*  323 */           if (packetIn.isMoving() && packetIn.getPositionY() == -999.0D)
/*      */           {
/*  325 */             packetIn.setMoving(false);
/*      */           }
/*      */           
/*  328 */           if (packetIn.isMoving()) {
/*      */             
/*  330 */             d8 = packetIn.getPositionX();
/*  331 */             d9 = packetIn.getPositionY();
/*  332 */             d10 = packetIn.getPositionZ();
/*      */             
/*  334 */             if (Math.abs(packetIn.getPositionX()) > 3.0E7D || Math.abs(packetIn.getPositionZ()) > 3.0E7D) {
/*      */               
/*  336 */               kickPlayerFromServer("Illegal position");
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*  341 */           if (packetIn.getRotating()) {
/*      */             
/*  343 */             f1 = packetIn.getYaw();
/*  344 */             f2 = packetIn.getPitch();
/*      */           } 
/*      */           
/*  347 */           this.playerEntity.onUpdateEntity();
/*  348 */           this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, f1, f2);
/*      */           
/*  350 */           if (!this.hasMoved) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/*  355 */           double d11 = d8 - this.playerEntity.posX;
/*  356 */           double d12 = d9 - this.playerEntity.posY;
/*  357 */           double d13 = d10 - this.playerEntity.posZ;
/*  358 */           double d14 = this.playerEntity.motionX * this.playerEntity.motionX + this.playerEntity.motionY * this.playerEntity.motionY + this.playerEntity.motionZ * this.playerEntity.motionZ;
/*  359 */           double d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*      */           
/*  361 */           if (d15 - d14 > 100.0D && (!this.serverController.isSinglePlayer() || !this.serverController.getServerOwner().equals(this.playerEntity.getName()))) {
/*      */             
/*  363 */             logger.warn(String.valueOf(this.playerEntity.getName()) + " moved too quickly! " + d11 + "," + d12 + "," + d13 + " (" + d11 + ", " + d12 + ", " + d13 + ")");
/*  364 */             setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */             
/*      */             return;
/*      */           } 
/*  368 */           float f3 = 0.0625F;
/*  369 */           boolean flag = worldserver.getCollidingBoundingBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(f3, f3, f3)).isEmpty();
/*      */           
/*  371 */           if (this.playerEntity.onGround && !packetIn.isOnGround() && d12 > 0.0D)
/*      */           {
/*  373 */             this.playerEntity.jump();
/*      */           }
/*      */           
/*  376 */           this.playerEntity.moveEntity(d11, d12, d13);
/*  377 */           this.playerEntity.onGround = packetIn.isOnGround();
/*  378 */           d11 = d8 - this.playerEntity.posX;
/*  379 */           d12 = d9 - this.playerEntity.posY;
/*      */           
/*  381 */           if (d12 > -0.5D || d12 < 0.5D)
/*      */           {
/*  383 */             d12 = 0.0D;
/*      */           }
/*      */           
/*  386 */           d13 = d10 - this.playerEntity.posZ;
/*  387 */           d15 = d11 * d11 + d12 * d12 + d13 * d13;
/*  388 */           boolean flag1 = false;
/*      */           
/*  390 */           if (d15 > 0.0625D && !this.playerEntity.isPlayerSleeping() && !this.playerEntity.theItemInWorldManager.isCreative()) {
/*      */             
/*  392 */             flag1 = true;
/*  393 */             logger.warn(String.valueOf(this.playerEntity.getName()) + " moved wrongly!");
/*      */           } 
/*      */           
/*  396 */           this.playerEntity.setPositionAndRotation(d8, d9, d10, f1, f2);
/*  397 */           this.playerEntity.addMovementStat(this.playerEntity.posX - d0, this.playerEntity.posY - d1, this.playerEntity.posZ - d2);
/*      */           
/*  399 */           if (!this.playerEntity.noClip) {
/*      */             
/*  401 */             boolean flag2 = worldserver.getCollidingBoundingBoxes((Entity)this.playerEntity, this.playerEntity.getEntityBoundingBox().contract(f3, f3, f3)).isEmpty();
/*      */             
/*  403 */             if (flag && (flag1 || !flag2) && !this.playerEntity.isPlayerSleeping()) {
/*      */               
/*  405 */               setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, f1, f2);
/*      */               
/*      */               return;
/*      */             } 
/*      */           } 
/*  410 */           AxisAlignedBB axisalignedbb = this.playerEntity.getEntityBoundingBox().expand(f3, f3, f3).addCoord(0.0D, -0.55D, 0.0D);
/*      */           
/*  412 */           if (!this.serverController.isFlightAllowed() && !this.playerEntity.capabilities.allowFlying && !worldserver.checkBlockCollision(axisalignedbb)) {
/*      */             
/*  414 */             if (d12 >= -0.03125D) {
/*      */               
/*  416 */               this.floatingTickCount++;
/*      */               
/*  418 */               if (this.floatingTickCount > 80) {
/*      */                 
/*  420 */                 logger.warn(String.valueOf(this.playerEntity.getName()) + " was kicked for floating too long!");
/*  421 */                 kickPlayerFromServer("Flying is not enabled on this server");
/*      */ 
/*      */                 
/*      */                 return;
/*      */               } 
/*      */             } 
/*      */           } else {
/*  428 */             this.floatingTickCount = 0;
/*      */           } 
/*      */           
/*  431 */           this.playerEntity.onGround = packetIn.isOnGround();
/*  432 */           this.serverController.getConfigurationManager().serverUpdateMountedMovingPlayer(this.playerEntity);
/*  433 */           this.playerEntity.handleFalling(this.playerEntity.posY - d7, packetIn.isOnGround());
/*      */         }
/*  435 */         else if (this.networkTickCount - this.field_175090_f > 20) {
/*      */           
/*  437 */           setPlayerLocation(this.lastPosX, this.lastPosY, this.lastPosZ, this.playerEntity.rotationYaw, this.playerEntity.rotationPitch);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch) {
/*  445 */     setPlayerLocation(x, y, z, yaw, pitch, Collections.emptySet());
/*      */   }
/*      */ 
/*      */   
/*      */   public void setPlayerLocation(double x, double y, double z, float yaw, float pitch, Set<S08PacketPlayerPosLook.EnumFlags> relativeSet) {
/*  450 */     this.hasMoved = false;
/*  451 */     this.lastPosX = x;
/*  452 */     this.lastPosY = y;
/*  453 */     this.lastPosZ = z;
/*      */     
/*  455 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.X))
/*      */     {
/*  457 */       this.lastPosX += this.playerEntity.posX;
/*      */     }
/*      */     
/*  460 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Y))
/*      */     {
/*  462 */       this.lastPosY += this.playerEntity.posY;
/*      */     }
/*      */     
/*  465 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Z))
/*      */     {
/*  467 */       this.lastPosZ += this.playerEntity.posZ;
/*      */     }
/*      */     
/*  470 */     float f = yaw;
/*  471 */     float f1 = pitch;
/*      */     
/*  473 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.Y_ROT))
/*      */     {
/*  475 */       f = yaw + this.playerEntity.rotationYaw;
/*      */     }
/*      */     
/*  478 */     if (relativeSet.contains(S08PacketPlayerPosLook.EnumFlags.X_ROT))
/*      */     {
/*  480 */       f1 = pitch + this.playerEntity.rotationPitch;
/*      */     }
/*      */     
/*  483 */     this.playerEntity.setPositionAndRotation(this.lastPosX, this.lastPosY, this.lastPosZ, f, f1);
/*  484 */     this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S08PacketPlayerPosLook(x, y, z, yaw, pitch, relativeSet));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerDigging(C07PacketPlayerDigging packetIn) {
/*      */     double d0, d1, d2, d3;
/*  494 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  495 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  496 */     BlockPos blockpos = packetIn.getPosition();
/*  497 */     this.playerEntity.markPlayerActive();
/*      */     
/*  499 */     switch (packetIn.getStatus()) {
/*      */       
/*      */       case DROP_ITEM:
/*  502 */         if (!this.playerEntity.isSpectator())
/*      */         {
/*  504 */           this.playerEntity.dropOneItem(false);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case DROP_ALL_ITEMS:
/*  510 */         if (!this.playerEntity.isSpectator())
/*      */         {
/*  512 */           this.playerEntity.dropOneItem(true);
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case RELEASE_USE_ITEM:
/*  518 */         this.playerEntity.stopUsingItem();
/*      */         return;
/*      */       
/*      */       case START_DESTROY_BLOCK:
/*      */       case null:
/*      */       case STOP_DESTROY_BLOCK:
/*  524 */         d0 = this.playerEntity.posX - blockpos.getX() + 0.5D;
/*  525 */         d1 = this.playerEntity.posY - blockpos.getY() + 0.5D + 1.5D;
/*  526 */         d2 = this.playerEntity.posZ - blockpos.getZ() + 0.5D;
/*  527 */         d3 = d0 * d0 + d1 * d1 + d2 * d2;
/*      */         
/*  529 */         if (d3 > 36.0D) {
/*      */           return;
/*      */         }
/*      */         
/*  533 */         if (blockpos.getY() >= this.serverController.getBuildLimit()) {
/*      */           return;
/*      */         }
/*      */ 
/*      */ 
/*      */         
/*  539 */         if (packetIn.getStatus() == C07PacketPlayerDigging.Action.START_DESTROY_BLOCK) {
/*      */           
/*  541 */           if (!this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos))
/*      */           {
/*  543 */             this.playerEntity.theItemInWorldManager.onBlockClicked(blockpos, packetIn.getFacing());
/*      */           }
/*      */           else
/*      */           {
/*  547 */             this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  552 */           if (packetIn.getStatus() == C07PacketPlayerDigging.Action.STOP_DESTROY_BLOCK) {
/*      */             
/*  554 */             this.playerEntity.theItemInWorldManager.blockRemoving(blockpos);
/*      */           }
/*  556 */           else if (packetIn.getStatus() == C07PacketPlayerDigging.Action.ABORT_DESTROY_BLOCK) {
/*      */             
/*  558 */             this.playerEntity.theItemInWorldManager.cancelDestroyingBlock();
/*      */           } 
/*      */           
/*  561 */           if (worldserver.getBlockState(blockpos).getBlock().getMaterial() != Material.air)
/*      */           {
/*  563 */             this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*      */           }
/*      */         } 
/*      */         return;
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*  571 */     throw new IllegalArgumentException("Invalid player action");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerBlockPlacement(C08PacketPlayerBlockPlacement packetIn) {
/*  580 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  581 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  582 */     ItemStack itemstack = this.playerEntity.inventory.getCurrentItem();
/*  583 */     boolean flag = false;
/*  584 */     BlockPos blockpos = packetIn.getPosition();
/*  585 */     EnumFacing enumfacing = EnumFacing.getFront(packetIn.getPlacedBlockDirection());
/*  586 */     this.playerEntity.markPlayerActive();
/*      */     
/*  588 */     if (packetIn.getPlacedBlockDirection() == 255) {
/*      */       
/*  590 */       if (itemstack == null) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  595 */       this.playerEntity.theItemInWorldManager.tryUseItem((EntityPlayer)this.playerEntity, (World)worldserver, itemstack);
/*      */     }
/*  597 */     else if (blockpos.getY() < this.serverController.getBuildLimit() - 1 || (enumfacing != EnumFacing.UP && blockpos.getY() < this.serverController.getBuildLimit())) {
/*      */       
/*  599 */       if (this.hasMoved && this.playerEntity.getDistanceSq(blockpos.getX() + 0.5D, blockpos.getY() + 0.5D, blockpos.getZ() + 0.5D) < 64.0D && !this.serverController.isBlockProtected((World)worldserver, blockpos, (EntityPlayer)this.playerEntity) && worldserver.getWorldBorder().contains(blockpos))
/*      */       {
/*  601 */         this.playerEntity.theItemInWorldManager.activateBlockOrUseItem((EntityPlayer)this.playerEntity, (World)worldserver, itemstack, blockpos, enumfacing, packetIn.getPlacedBlockOffsetX(), packetIn.getPlacedBlockOffsetY(), packetIn.getPlacedBlockOffsetZ());
/*      */       }
/*      */       
/*  604 */       flag = true;
/*      */     }
/*      */     else {
/*      */       
/*  608 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("build.tooHigh", new Object[] { Integer.valueOf(this.serverController.getBuildLimit()) });
/*  609 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  610 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S02PacketChat((IChatComponent)chatcomponenttranslation));
/*  611 */       flag = true;
/*      */     } 
/*      */     
/*  614 */     if (flag) {
/*      */       
/*  616 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos));
/*  617 */       this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S23PacketBlockChange((World)worldserver, blockpos.offset(enumfacing)));
/*      */     } 
/*      */     
/*  620 */     itemstack = this.playerEntity.inventory.getCurrentItem();
/*      */     
/*  622 */     if (itemstack != null && itemstack.stackSize == 0) {
/*      */       
/*  624 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = null;
/*  625 */       itemstack = null;
/*      */     } 
/*      */     
/*  628 */     if (itemstack == null || itemstack.getMaxItemUseDuration() == 0) {
/*      */       
/*  630 */       this.playerEntity.isChangingQuantityOnly = true;
/*  631 */       this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem] = ItemStack.copyItemStack(this.playerEntity.inventory.mainInventory[this.playerEntity.inventory.currentItem]);
/*  632 */       Slot slot = this.playerEntity.openContainer.getSlotFromInventory((IInventory)this.playerEntity.inventory, this.playerEntity.inventory.currentItem);
/*  633 */       this.playerEntity.openContainer.detectAndSendChanges();
/*  634 */       this.playerEntity.isChangingQuantityOnly = false;
/*      */       
/*  636 */       if (!ItemStack.areItemStacksEqual(this.playerEntity.inventory.getCurrentItem(), packetIn.getStack()))
/*      */       {
/*  638 */         sendPacket((Packet)new S2FPacketSetSlot(this.playerEntity.openContainer.windowId, slot.slotNumber, this.playerEntity.inventory.getCurrentItem()));
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleSpectate(C18PacketSpectate packetIn) {
/*  645 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  647 */     if (this.playerEntity.isSpectator()) {
/*      */       
/*  649 */       Entity entity = null; byte b; int i;
/*      */       WorldServer[] arrayOfWorldServer;
/*  651 */       for (i = (arrayOfWorldServer = this.serverController.worldServers).length, b = 0; b < i; ) { WorldServer worldserver = arrayOfWorldServer[b];
/*      */         
/*  653 */         if (worldserver != null) {
/*      */           
/*  655 */           entity = packetIn.getEntity(worldserver);
/*      */           
/*  657 */           if (entity != null) {
/*      */             break;
/*      */           }
/*      */         } 
/*      */         
/*      */         b++; }
/*      */       
/*  664 */       if (entity != null) {
/*      */         
/*  666 */         this.playerEntity.setSpectatingEntity((Entity)this.playerEntity);
/*  667 */         this.playerEntity.mountEntity(null);
/*      */         
/*  669 */         if (entity.worldObj != this.playerEntity.worldObj) {
/*      */           
/*  671 */           WorldServer worldserver1 = this.playerEntity.getServerForPlayer();
/*  672 */           WorldServer worldserver2 = (WorldServer)entity.worldObj;
/*  673 */           this.playerEntity.dimension = entity.dimension;
/*  674 */           sendPacket((Packet)new S07PacketRespawn(this.playerEntity.dimension, worldserver1.getDifficulty(), worldserver1.getWorldInfo().getTerrainType(), this.playerEntity.theItemInWorldManager.getGameType()));
/*  675 */           worldserver1.removePlayerEntityDangerously((Entity)this.playerEntity);
/*  676 */           this.playerEntity.isDead = false;
/*  677 */           this.playerEntity.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
/*      */           
/*  679 */           if (this.playerEntity.isEntityAlive()) {
/*      */             
/*  681 */             worldserver1.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*  682 */             worldserver2.spawnEntityInWorld((Entity)this.playerEntity);
/*  683 */             worldserver2.updateEntityWithOptionalForce((Entity)this.playerEntity, false);
/*      */           } 
/*      */           
/*  686 */           this.playerEntity.setWorld((World)worldserver2);
/*  687 */           this.serverController.getConfigurationManager().preparePlayer(this.playerEntity, worldserver1);
/*  688 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*  689 */           this.playerEntity.theItemInWorldManager.setWorld(worldserver2);
/*  690 */           this.serverController.getConfigurationManager().updateTimeAndWeatherForPlayer(this.playerEntity, worldserver2);
/*  691 */           this.serverController.getConfigurationManager().syncPlayerInventory(this.playerEntity);
/*      */         }
/*      */         else {
/*      */           
/*  695 */           this.playerEntity.setPositionAndUpdate(entity.posX, entity.posY, entity.posZ);
/*      */         } 
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void handleResourcePackStatus(C19PacketResourcePackStatus packetIn) {}
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void onDisconnect(IChatComponent reason) {
/*  710 */     logger.info(String.valueOf(this.playerEntity.getName()) + " lost connection: " + reason);
/*  711 */     this.serverController.refreshStatusNextTick();
/*  712 */     ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("multiplayer.player.left", new Object[] { this.playerEntity.getDisplayName() });
/*  713 */     chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.YELLOW);
/*  714 */     this.serverController.getConfigurationManager().sendChatMsg((IChatComponent)chatcomponenttranslation);
/*  715 */     this.playerEntity.mountEntityAndWakeUp();
/*  716 */     this.serverController.getConfigurationManager().playerLoggedOut(this.playerEntity);
/*      */     
/*  718 */     if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
/*      */       
/*  720 */       logger.info("Stopping singleplayer server as player logged out");
/*  721 */       this.serverController.initiateShutdown();
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public void sendPacket(final Packet packetIn) {
/*  727 */     if (packetIn instanceof S02PacketChat) {
/*      */       
/*  729 */       S02PacketChat s02packetchat = (S02PacketChat)packetIn;
/*  730 */       EntityPlayer.EnumChatVisibility entityplayer$enumchatvisibility = this.playerEntity.getChatVisibility();
/*      */       
/*  732 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/*  737 */       if (entityplayer$enumchatvisibility == EntityPlayer.EnumChatVisibility.SYSTEM && !s02packetchat.isChat()) {
/*      */         return;
/*      */       }
/*      */     } 
/*      */ 
/*      */ 
/*      */     
/*      */     try {
/*  745 */       this.netManager.sendPacket(packetIn);
/*      */     }
/*  747 */     catch (Throwable throwable) {
/*      */       
/*  749 */       CrashReport crashreport = CrashReport.makeCrashReport(throwable, "Sending packet");
/*  750 */       CrashReportCategory crashreportcategory = crashreport.makeCategory("Packet being sent");
/*  751 */       crashreportcategory.addCrashSectionCallable("Packet class", new Callable<String>()
/*      */           {
/*      */             public String call() throws Exception
/*      */             {
/*  755 */               return packetIn.getClass().getCanonicalName();
/*      */             }
/*      */           });
/*  758 */       throw new ReportedException(crashreport);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processHeldItemChange(C09PacketHeldItemChange packetIn) {
/*  767 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  769 */     if (packetIn.getSlotId() >= 0 && packetIn.getSlotId() < InventoryPlayer.getHotbarSize()) {
/*      */       
/*  771 */       this.playerEntity.inventory.currentItem = packetIn.getSlotId();
/*  772 */       this.playerEntity.markPlayerActive();
/*      */     }
/*      */     else {
/*      */       
/*  776 */       logger.warn(String.valueOf(this.playerEntity.getName()) + " tried to set an invalid carried item");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processChatMessage(C01PacketChatMessage packetIn) {
/*  785 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/*  787 */     if (this.playerEntity.getChatVisibility() == EntityPlayer.EnumChatVisibility.HIDDEN) {
/*      */       
/*  789 */       ChatComponentTranslation chatcomponenttranslation = new ChatComponentTranslation("chat.cannotSend", new Object[0]);
/*  790 */       chatcomponenttranslation.getChatStyle().setColor(EnumChatFormatting.RED);
/*  791 */       sendPacket((Packet)new S02PacketChat((IChatComponent)chatcomponenttranslation));
/*      */     }
/*      */     else {
/*      */       
/*  795 */       this.playerEntity.markPlayerActive();
/*  796 */       String s = packetIn.getMessage();
/*  797 */       s = StringUtils.normalizeSpace(s);
/*      */       
/*  799 */       for (int i = 0; i < s.length(); i++) {
/*      */         
/*  801 */         if (!ChatAllowedCharacters.isAllowedCharacter(s.charAt(i))) {
/*      */           
/*  803 */           kickPlayerFromServer("Illegal characters in chat");
/*      */           
/*      */           return;
/*      */         } 
/*      */       } 
/*  808 */       if (s.startsWith("/")) {
/*      */         
/*  810 */         handleSlashCommand(s);
/*      */       }
/*      */       else {
/*      */         
/*  814 */         ChatComponentTranslation chatComponentTranslation = new ChatComponentTranslation("chat.type.text", new Object[] { this.playerEntity.getDisplayName(), s });
/*  815 */         this.serverController.getConfigurationManager().sendChatMsgImpl((IChatComponent)chatComponentTranslation, false);
/*      */       } 
/*      */       
/*  818 */       this.chatSpamThresholdCount += 20;
/*      */       
/*  820 */       if (this.chatSpamThresholdCount > 200 && !this.serverController.getConfigurationManager().canSendCommands(this.playerEntity.getGameProfile()))
/*      */       {
/*  822 */         kickPlayerFromServer("disconnect.spam");
/*      */       }
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private void handleSlashCommand(String command) {
/*  832 */     this.serverController.getCommandManager().executeCommand((ICommandSender)this.playerEntity, command);
/*      */   }
/*      */ 
/*      */   
/*      */   public void handleAnimation(C0APacketAnimation packetIn) {
/*  837 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  838 */     this.playerEntity.markPlayerActive();
/*  839 */     this.playerEntity.swingItem();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processEntityAction(C0BPacketEntityAction packetIn) {
/*  848 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  849 */     this.playerEntity.markPlayerActive();
/*      */     
/*  851 */     switch (packetIn.getAction()) {
/*      */       
/*      */       case START_SNEAKING:
/*  854 */         this.playerEntity.setSneaking(true);
/*      */         return;
/*      */       
/*      */       case STOP_SNEAKING:
/*  858 */         this.playerEntity.setSneaking(false);
/*      */         return;
/*      */       
/*      */       case START_SPRINTING:
/*  862 */         this.playerEntity.setSprinting(true);
/*      */         return;
/*      */       
/*      */       case STOP_SPRINTING:
/*  866 */         this.playerEntity.setSprinting(false);
/*      */         return;
/*      */       
/*      */       case STOP_SLEEPING:
/*  870 */         this.playerEntity.wakeUpPlayer(false, true, true);
/*  871 */         this.hasMoved = false;
/*      */         return;
/*      */       
/*      */       case RIDING_JUMP:
/*  875 */         if (this.playerEntity.ridingEntity instanceof EntityHorse)
/*      */         {
/*  877 */           ((EntityHorse)this.playerEntity.ridingEntity).setJumpPower(packetIn.getAuxData());
/*      */         }
/*      */         return;
/*      */ 
/*      */       
/*      */       case null:
/*  883 */         if (this.playerEntity.ridingEntity instanceof EntityHorse)
/*      */         {
/*  885 */           ((EntityHorse)this.playerEntity.ridingEntity).openGUI((EntityPlayer)this.playerEntity);
/*      */         }
/*      */         return;
/*      */     } 
/*      */ 
/*      */     
/*  891 */     throw new IllegalArgumentException("Invalid client command!");
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processUseEntity(C02PacketUseEntity packetIn) {
/*  901 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  902 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/*  903 */     Entity entity = packetIn.getEntityFromWorld((World)worldserver);
/*  904 */     this.playerEntity.markPlayerActive();
/*      */     
/*  906 */     if (entity != null) {
/*      */       
/*  908 */       boolean flag = this.playerEntity.canEntityBeSeen(entity);
/*  909 */       double d0 = 36.0D;
/*      */       
/*  911 */       if (!flag)
/*      */       {
/*  913 */         d0 = 9.0D;
/*      */       }
/*      */       
/*  916 */       if (this.playerEntity.getDistanceSqToEntity(entity) < d0)
/*      */       {
/*  918 */         if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT) {
/*      */           
/*  920 */           this.playerEntity.interactWith(entity);
/*      */         }
/*  922 */         else if (packetIn.getAction() == C02PacketUseEntity.Action.INTERACT_AT) {
/*      */           
/*  924 */           entity.interactAt((EntityPlayer)this.playerEntity, packetIn.getHitVec());
/*      */         }
/*  926 */         else if (packetIn.getAction() == C02PacketUseEntity.Action.ATTACK) {
/*      */           
/*  928 */           if (entity instanceof EntityItem || entity instanceof net.minecraft.entity.item.EntityXPOrb || entity instanceof net.minecraft.entity.projectile.EntityArrow || entity == this.playerEntity) {
/*      */             
/*  930 */             kickPlayerFromServer("Attempting to attack an invalid entity");
/*  931 */             this.serverController.logWarning("Player " + this.playerEntity.getName() + " tried to attack an invalid entity");
/*      */             
/*      */             return;
/*      */           } 
/*  935 */           this.playerEntity.attackTargetEntityWithCurrentItem(entity);
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
/*      */   public void processClientStatus(C16PacketClientStatus packetIn) {
/*  947 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  948 */     this.playerEntity.markPlayerActive();
/*  949 */     C16PacketClientStatus.EnumState c16packetclientstatus$enumstate = packetIn.getStatus();
/*      */     
/*  951 */     switch (c16packetclientstatus$enumstate) {
/*      */       
/*      */       case PERFORM_RESPAWN:
/*  954 */         if (this.playerEntity.playerConqueredTheEnd) {
/*      */           
/*  956 */           this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, true); break;
/*      */         } 
/*  958 */         if (this.playerEntity.getServerForPlayer().getWorldInfo().isHardcoreModeEnabled()) {
/*      */           
/*  960 */           if (this.serverController.isSinglePlayer() && this.playerEntity.getName().equals(this.serverController.getServerOwner())) {
/*      */             
/*  962 */             this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*  963 */             this.serverController.deleteWorldAndStopServer();
/*      */             
/*      */             break;
/*      */           } 
/*  967 */           UserListBansEntry userlistbansentry = new UserListBansEntry(this.playerEntity.getGameProfile(), null, "(You just lost the game)", null, "Death in Hardcore");
/*  968 */           this.serverController.getConfigurationManager().getBannedPlayers().addEntry((UserListEntry)userlistbansentry);
/*  969 */           this.playerEntity.playerNetServerHandler.kickPlayerFromServer("You have died. Game over, man, it's game over!");
/*      */           
/*      */           break;
/*      */         } 
/*      */         
/*  974 */         if (this.playerEntity.getHealth() > 0.0F) {
/*      */           return;
/*      */         }
/*      */ 
/*      */         
/*  979 */         this.playerEntity = this.serverController.getConfigurationManager().recreatePlayerEntity(this.playerEntity, 0, false);
/*      */         break;
/*      */ 
/*      */ 
/*      */       
/*      */       case REQUEST_STATS:
/*  985 */         this.playerEntity.getStatFile().func_150876_a(this.playerEntity);
/*      */         break;
/*      */       
/*      */       case null:
/*  989 */         this.playerEntity.triggerAchievement((StatBase)AchievementList.openInventory);
/*      */         break;
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCloseWindow(C0DPacketCloseWindow packetIn) {
/*  998 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*  999 */     this.playerEntity.closeContainer();
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClickWindow(C0EPacketClickWindow packetIn) {
/* 1009 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1010 */     this.playerEntity.markPlayerActive();
/*      */     
/* 1012 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity))
/*      */     {
/* 1014 */       if (this.playerEntity.isSpectator()) {
/*      */         
/* 1016 */         List<ItemStack> list = Lists.newArrayList();
/*      */         
/* 1018 */         for (int i = 0; i < this.playerEntity.openContainer.inventorySlots.size(); i++)
/*      */         {
/* 1020 */           list.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(i)).getStack());
/*      */         }
/*      */         
/* 1023 */         this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, list);
/*      */       }
/*      */       else {
/*      */         
/* 1027 */         ItemStack itemstack = this.playerEntity.openContainer.slotClick(packetIn.getSlotId(), packetIn.getUsedButton(), packetIn.getMode(), (EntityPlayer)this.playerEntity);
/*      */         
/* 1029 */         if (ItemStack.areItemStacksEqual(packetIn.getClickedItem(), itemstack)) {
/*      */           
/* 1031 */           this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), true));
/* 1032 */           this.playerEntity.isChangingQuantityOnly = true;
/* 1033 */           this.playerEntity.openContainer.detectAndSendChanges();
/* 1034 */           this.playerEntity.updateHeldItem();
/* 1035 */           this.playerEntity.isChangingQuantityOnly = false;
/*      */         }
/*      */         else {
/*      */           
/* 1039 */           this.field_147372_n.addKey(this.playerEntity.openContainer.windowId, Short.valueOf(packetIn.getActionNumber()));
/* 1040 */           this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S32PacketConfirmTransaction(packetIn.getWindowId(), packetIn.getActionNumber(), false));
/* 1041 */           this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, false);
/* 1042 */           List<ItemStack> list1 = Lists.newArrayList();
/*      */           
/* 1044 */           for (int j = 0; j < this.playerEntity.openContainer.inventorySlots.size(); j++)
/*      */           {
/* 1046 */             list1.add(((Slot)this.playerEntity.openContainer.inventorySlots.get(j)).getStack());
/*      */           }
/*      */           
/* 1049 */           this.playerEntity.updateCraftingInventory(this.playerEntity.openContainer, list1);
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
/*      */   public void processEnchantItem(C11PacketEnchantItem packetIn) {
/* 1061 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1062 */     this.playerEntity.markPlayerActive();
/*      */     
/* 1064 */     if (this.playerEntity.openContainer.windowId == packetIn.getWindowId() && this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator()) {
/*      */       
/* 1066 */       this.playerEntity.openContainer.enchantItem((EntityPlayer)this.playerEntity, packetIn.getButton());
/* 1067 */       this.playerEntity.openContainer.detectAndSendChanges();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processCreativeInventoryAction(C10PacketCreativeInventoryAction packetIn) {
/* 1076 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/* 1078 */     if (this.playerEntity.theItemInWorldManager.isCreative()) {
/*      */       
/* 1080 */       boolean flag = (packetIn.getSlotId() < 0);
/* 1081 */       ItemStack itemstack = packetIn.getStack();
/*      */       
/* 1083 */       if (itemstack != null && itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("BlockEntityTag", 10)) {
/*      */         
/* 1085 */         NBTTagCompound nbttagcompound = itemstack.getTagCompound().getCompoundTag("BlockEntityTag");
/*      */         
/* 1087 */         if (nbttagcompound.hasKey("x") && nbttagcompound.hasKey("y") && nbttagcompound.hasKey("z")) {
/*      */           
/* 1089 */           BlockPos blockpos = new BlockPos(nbttagcompound.getInteger("x"), nbttagcompound.getInteger("y"), nbttagcompound.getInteger("z"));
/* 1090 */           TileEntity tileentity = this.playerEntity.worldObj.getTileEntity(blockpos);
/*      */           
/* 1092 */           if (tileentity != null) {
/*      */             
/* 1094 */             NBTTagCompound nbttagcompound1 = new NBTTagCompound();
/* 1095 */             tileentity.writeToNBT(nbttagcompound1);
/* 1096 */             nbttagcompound1.removeTag("x");
/* 1097 */             nbttagcompound1.removeTag("y");
/* 1098 */             nbttagcompound1.removeTag("z");
/* 1099 */             itemstack.setTagInfo("BlockEntityTag", (NBTBase)nbttagcompound1);
/*      */           } 
/*      */         } 
/*      */       } 
/*      */       
/* 1104 */       boolean flag1 = (packetIn.getSlotId() >= 1 && packetIn.getSlotId() < 36 + InventoryPlayer.getHotbarSize());
/* 1105 */       boolean flag2 = !(itemstack != null && itemstack.getItem() == null);
/* 1106 */       boolean flag3 = !(itemstack != null && (itemstack.getMetadata() < 0 || itemstack.stackSize > 64 || itemstack.stackSize <= 0));
/*      */       
/* 1108 */       if (flag1 && flag2 && flag3) {
/*      */         
/* 1110 */         if (itemstack == null) {
/*      */           
/* 1112 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), null);
/*      */         }
/*      */         else {
/*      */           
/* 1116 */           this.playerEntity.inventoryContainer.putStackInSlot(packetIn.getSlotId(), itemstack);
/*      */         } 
/*      */         
/* 1119 */         this.playerEntity.inventoryContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*      */       }
/* 1121 */       else if (flag && flag2 && flag3 && this.itemDropThreshold < 200) {
/*      */         
/* 1123 */         this.itemDropThreshold += 20;
/* 1124 */         EntityItem entityitem = this.playerEntity.dropPlayerItemWithRandomChoice(itemstack, true);
/*      */         
/* 1126 */         if (entityitem != null)
/*      */         {
/* 1128 */           entityitem.setAgeToCreativeDespawnTime();
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
/*      */   public void processConfirmTransaction(C0FPacketConfirmTransaction packetIn) {
/* 1141 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1142 */     Short oshort = (Short)this.field_147372_n.lookup(this.playerEntity.openContainer.windowId);
/*      */     
/* 1144 */     if (oshort != null && packetIn.getUid() == oshort.shortValue() && this.playerEntity.openContainer.windowId == packetIn.getWindowId() && !this.playerEntity.openContainer.getCanCraft((EntityPlayer)this.playerEntity) && !this.playerEntity.isSpectator())
/*      */     {
/* 1146 */       this.playerEntity.openContainer.setCanCraft((EntityPlayer)this.playerEntity, true);
/*      */     }
/*      */   }
/*      */ 
/*      */   
/*      */   public void processUpdateSign(C12PacketUpdateSign packetIn) {
/* 1152 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1153 */     this.playerEntity.markPlayerActive();
/* 1154 */     WorldServer worldserver = this.serverController.worldServerForDimension(this.playerEntity.dimension);
/* 1155 */     BlockPos blockpos = packetIn.getPosition();
/*      */     
/* 1157 */     if (worldserver.isBlockLoaded(blockpos)) {
/*      */       
/* 1159 */       TileEntity tileentity = worldserver.getTileEntity(blockpos);
/*      */       
/* 1161 */       if (!(tileentity instanceof TileEntitySign)) {
/*      */         return;
/*      */       }
/*      */ 
/*      */       
/* 1166 */       TileEntitySign tileentitysign = (TileEntitySign)tileentity;
/*      */       
/* 1168 */       if (!tileentitysign.getIsEditable() || tileentitysign.getPlayer() != this.playerEntity) {
/*      */         
/* 1170 */         this.serverController.logWarning("Player " + this.playerEntity.getName() + " just tried to change non-editable sign");
/*      */         
/*      */         return;
/*      */       } 
/* 1174 */       IChatComponent[] aichatcomponent = packetIn.getLines();
/*      */       
/* 1176 */       for (int i = 0; i < aichatcomponent.length; i++)
/*      */       {
/* 1178 */         tileentitysign.signText[i] = (IChatComponent)new ChatComponentText(EnumChatFormatting.getTextWithoutFormattingCodes(aichatcomponent[i].getUnformattedText()));
/*      */       }
/*      */       
/* 1181 */       tileentitysign.markDirty();
/* 1182 */       worldserver.markBlockForUpdate(blockpos);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processKeepAlive(C00PacketKeepAlive packetIn) {
/* 1191 */     if (packetIn.getKey() == this.field_147378_h) {
/*      */       
/* 1193 */       int i = (int)(currentTimeMillis() - this.lastPingTime);
/* 1194 */       this.playerEntity.ping = (this.playerEntity.ping * 3 + i) / 4;
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   private long currentTimeMillis() {
/* 1200 */     return System.nanoTime() / 1000000L;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processPlayerAbilities(C13PacketPlayerAbilities packetIn) {
/* 1208 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1209 */     this.playerEntity.capabilities.isFlying = (packetIn.isFlying() && this.playerEntity.capabilities.allowFlying);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processTabComplete(C14PacketTabComplete packetIn) {
/* 1217 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1218 */     List<String> list = Lists.newArrayList();
/*      */     
/* 1220 */     for (String s : this.serverController.getTabCompletions((ICommandSender)this.playerEntity, packetIn.getMessage(), packetIn.getTargetBlock()))
/*      */     {
/* 1222 */       list.add(s);
/*      */     }
/*      */     
/* 1225 */     this.playerEntity.playerNetServerHandler.sendPacket((Packet)new S3APacketTabComplete(list.<String>toArray(new String[list.size()])));
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processClientSettings(C15PacketClientSettings packetIn) {
/* 1234 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/* 1235 */     this.playerEntity.handleClientSettings(packetIn);
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public void processVanilla250Packet(C17PacketCustomPayload packetIn) {
/* 1243 */     PacketThreadUtil.checkThreadAndEnqueue((Packet<NetHandlerPlayServer>)packetIn, this, (IThreadListener)this.playerEntity.getServerForPlayer());
/*      */     
/* 1245 */     if ("MC|BEdit".equals(packetIn.getChannelName())) {
/*      */       
/* 1247 */       PacketBuffer packetbuffer3 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));
/*      */ 
/*      */       
/*      */       try {
/* 1251 */         ItemStack itemstack1 = packetbuffer3.readItemStackFromBuffer();
/*      */         
/* 1253 */         if (itemstack1 != null) {
/*      */           
/* 1255 */           if (!ItemWritableBook.isNBTValid(itemstack1.getTagCompound()))
/*      */           {
/* 1257 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1260 */           ItemStack itemstack3 = this.playerEntity.inventory.getCurrentItem();
/*      */           
/* 1262 */           if (itemstack3 == null) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/* 1267 */           if (itemstack1.getItem() == Items.writable_book && itemstack1.getItem() == itemstack3.getItem())
/*      */           {
/* 1269 */             itemstack3.setTagInfo("pages", (NBTBase)itemstack1.getTagCompound().getTagList("pages", 8));
/*      */           }
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/* 1275 */       } catch (Exception exception3) {
/*      */         
/* 1277 */         logger.error("Couldn't handle book info", exception3);
/*      */ 
/*      */         
/*      */         return;
/*      */       } finally {
/* 1282 */         packetbuffer3.release();
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1287 */     if ("MC|BSign".equals(packetIn.getChannelName())) {
/*      */       
/* 1289 */       PacketBuffer packetbuffer2 = new PacketBuffer(Unpooled.wrappedBuffer(packetIn.getBufferData()));
/*      */ 
/*      */       
/*      */       try {
/* 1293 */         ItemStack itemstack = packetbuffer2.readItemStackFromBuffer();
/*      */         
/* 1295 */         if (itemstack != null) {
/*      */           
/* 1297 */           if (!ItemEditableBook.validBookTagContents(itemstack.getTagCompound()))
/*      */           {
/* 1299 */             throw new IOException("Invalid book tag!");
/*      */           }
/*      */           
/* 1302 */           ItemStack itemstack2 = this.playerEntity.inventory.getCurrentItem();
/*      */           
/* 1304 */           if (itemstack2 == null) {
/*      */             return;
/*      */           }
/*      */ 
/*      */           
/* 1309 */           if (itemstack.getItem() == Items.written_book && itemstack2.getItem() == Items.writable_book) {
/*      */             
/* 1311 */             itemstack2.setTagInfo("author", (NBTBase)new NBTTagString(this.playerEntity.getName()));
/* 1312 */             itemstack2.setTagInfo("title", (NBTBase)new NBTTagString(itemstack.getTagCompound().getString("title")));
/* 1313 */             itemstack2.setTagInfo("pages", (NBTBase)itemstack.getTagCompound().getTagList("pages", 8));
/* 1314 */             itemstack2.setItem(Items.written_book);
/*      */           } 
/*      */ 
/*      */           
/*      */           return;
/*      */         } 
/* 1320 */       } catch (Exception exception4) {
/*      */         
/* 1322 */         logger.error("Couldn't sign book", exception4);
/*      */ 
/*      */         
/*      */         return;
/*      */       } finally {
/* 1327 */         packetbuffer2.release();
/*      */       } 
/*      */       
/*      */       return;
/*      */     } 
/* 1332 */     if ("MC|TrSel".equals(packetIn.getChannelName())) {
/*      */       
/*      */       try
/*      */       {
/* 1336 */         int i = packetIn.getBufferData().readInt();
/* 1337 */         Container container = this.playerEntity.openContainer;
/*      */         
/* 1339 */         if (container instanceof ContainerMerchant)
/*      */         {
/* 1341 */           ((ContainerMerchant)container).setCurrentRecipeIndex(i);
/*      */         }
/*      */       }
/* 1344 */       catch (Exception exception2)
/*      */       {
/* 1346 */         logger.error("Couldn't select trade", exception2);
/*      */       }
/*      */     
/* 1349 */     } else if ("MC|AdvCdm".equals(packetIn.getChannelName())) {
/*      */       
/* 1351 */       if (!this.serverController.isCommandBlockEnabled()) {
/*      */         
/* 1353 */         this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.notEnabled", new Object[0]));
/*      */       }
/* 1355 */       else if (this.playerEntity.canCommandSenderUseCommand(2, "") && this.playerEntity.capabilities.isCreativeMode) {
/*      */         
/* 1357 */         PacketBuffer packetbuffer = packetIn.getBufferData();
/*      */ 
/*      */         
/*      */         try {
/* 1361 */           int j = packetbuffer.readByte();
/* 1362 */           CommandBlockLogic commandblocklogic = null;
/*      */           
/* 1364 */           if (j == 0) {
/*      */             
/* 1366 */             TileEntity tileentity = this.playerEntity.worldObj.getTileEntity(new BlockPos(packetbuffer.readInt(), packetbuffer.readInt(), packetbuffer.readInt()));
/*      */             
/* 1368 */             if (tileentity instanceof TileEntityCommandBlock)
/*      */             {
/* 1370 */               commandblocklogic = ((TileEntityCommandBlock)tileentity).getCommandBlockLogic();
/*      */             }
/*      */           }
/* 1373 */           else if (j == 1) {
/*      */             
/* 1375 */             Entity entity = this.playerEntity.worldObj.getEntityByID(packetbuffer.readInt());
/*      */             
/* 1377 */             if (entity instanceof EntityMinecartCommandBlock)
/*      */             {
/* 1379 */               commandblocklogic = ((EntityMinecartCommandBlock)entity).getCommandBlockLogic();
/*      */             }
/*      */           } 
/*      */           
/* 1383 */           String s1 = packetbuffer.readStringFromBuffer(packetbuffer.readableBytes());
/* 1384 */           boolean flag = packetbuffer.readBoolean();
/*      */           
/* 1386 */           if (commandblocklogic != null)
/*      */           {
/* 1388 */             commandblocklogic.setCommand(s1);
/* 1389 */             commandblocklogic.setTrackOutput(flag);
/*      */             
/* 1391 */             if (!flag)
/*      */             {
/* 1393 */               commandblocklogic.setLastOutput(null);
/*      */             }
/*      */             
/* 1396 */             commandblocklogic.updateCommand();
/* 1397 */             this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.setCommand.success", new Object[] { s1 }));
/*      */           }
/*      */         
/* 1400 */         } catch (Exception exception1) {
/*      */           
/* 1402 */           logger.error("Couldn't set command block", exception1);
/*      */         }
/*      */         finally {
/*      */           
/* 1406 */           packetbuffer.release();
/*      */         }
/*      */       
/*      */       } else {
/*      */         
/* 1411 */         this.playerEntity.addChatMessage((IChatComponent)new ChatComponentTranslation("advMode.notAllowed", new Object[0]));
/*      */       }
/*      */     
/* 1414 */     } else if ("MC|Beacon".equals(packetIn.getChannelName())) {
/*      */       
/* 1416 */       if (this.playerEntity.openContainer instanceof ContainerBeacon) {
/*      */         
/*      */         try {
/*      */           
/* 1420 */           PacketBuffer packetbuffer1 = packetIn.getBufferData();
/* 1421 */           int k = packetbuffer1.readInt();
/* 1422 */           int l = packetbuffer1.readInt();
/* 1423 */           ContainerBeacon containerbeacon = (ContainerBeacon)this.playerEntity.openContainer;
/* 1424 */           Slot slot = containerbeacon.getSlot(0);
/*      */           
/* 1426 */           if (slot.getHasStack())
/*      */           {
/* 1428 */             slot.decrStackSize(1);
/* 1429 */             IInventory iinventory = containerbeacon.func_180611_e();
/* 1430 */             iinventory.setField(1, k);
/* 1431 */             iinventory.setField(2, l);
/* 1432 */             iinventory.markDirty();
/*      */           }
/*      */         
/* 1435 */         } catch (Exception exception) {
/*      */           
/* 1437 */           logger.error("Couldn't set beacon", exception);
/*      */         }
/*      */       
/*      */       }
/* 1441 */     } else if ("MC|ItemName".equals(packetIn.getChannelName()) && this.playerEntity.openContainer instanceof ContainerRepair) {
/*      */       
/* 1443 */       ContainerRepair containerrepair = (ContainerRepair)this.playerEntity.openContainer;
/*      */       
/* 1445 */       if (packetIn.getBufferData() != null && packetIn.getBufferData().readableBytes() >= 1) {
/*      */         
/* 1447 */         String s = ChatAllowedCharacters.filterAllowedCharacters(packetIn.getBufferData().readStringFromBuffer(32767));
/*      */         
/* 1449 */         if (s.length() <= 30)
/*      */         {
/* 1451 */           containerrepair.updateItemName(s);
/*      */         }
/*      */       }
/*      */       else {
/*      */         
/* 1456 */         containerrepair.updateItemName("");
/*      */       } 
/*      */     } 
/*      */   }
/*      */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\network\NetHandlerPlayServer.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */