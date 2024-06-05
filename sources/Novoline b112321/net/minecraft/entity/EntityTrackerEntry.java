package net.minecraft.entity;

import com.google.common.collect.Sets;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import net.rF;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLeashKnot;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.ServersideAttributeMap;
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
import net.minecraft.entity.passive.IAnimals;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.entity.projectile.EntityEgg;
import net.minecraft.entity.projectile.EntityFireball;
import net.minecraft.entity.projectile.EntityFishHook;
import net.minecraft.entity.projectile.EntityPotion;
import net.minecraft.entity.projectile.EntitySmallFireball;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.entity.projectile.EntityWitherSkull;
import net.minecraft.init.Items;
import net.minecraft.item.ItemMap;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S04PacketEntityEquipment;
import net.minecraft.network.play.server.S0APacketUseBed;
import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
import net.minecraft.network.play.server.S0EPacketSpawnObject;
import net.minecraft.network.play.server.S0FPacketSpawnMob;
import net.minecraft.network.play.server.S10PacketSpawnPainting;
import net.minecraft.network.play.server.S11PacketSpawnExperienceOrb;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.network.play.server.S14PacketEntity$S15PacketEntityRelMove;
import net.minecraft.network.play.server.S14PacketEntity$S16PacketEntityLook;
import net.minecraft.network.play.server.S14PacketEntity$S17PacketEntityLookMove;
import net.minecraft.network.play.server.S18PacketEntityTeleport;
import net.minecraft.network.play.server.S19PacketEntityHeadLook;
import net.minecraft.network.play.server.S1BPacketEntityAttach;
import net.minecraft.network.play.server.S1CPacketEntityMetadata;
import net.minecraft.network.play.server.S1DPacketEntityEffect;
import net.minecraft.network.play.server.S20PacketEntityProperties;
import net.minecraft.network.play.server.S49PacketUpdateEntityNBT;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class EntityTrackerEntry {
   private static final Logger LOGGER = LogManager.getLogger();
   public Entity trackedEntity;
   public int trackingDistanceThreshold;
   public int updateFrequency;
   public int encodedPosX;
   public int encodedPosY;
   public int encodedPosZ;
   public int encodedRotationYaw;
   public int encodedRotationPitch;
   public int lastHeadMotion;
   public double lastTrackedEntityMotionX;
   public double lastTrackedEntityMotionY;
   public double motionZ;
   public int updateCounter;
   private double lastTrackedEntityPosX;
   private double lastTrackedEntityPosY;
   private double lastTrackedEntityPosZ;
   private boolean firstUpdateDone;
   private final boolean sendVelocityUpdates;
   private int ticksSinceLastForcedTeleport;
   private Entity field_85178_v;
   private boolean ridingEntity;
   private boolean onGround;
   public boolean playerEntitiesUpdated;
   public Set trackingPlayers = Sets.newHashSet();

   public EntityTrackerEntry(Entity var1, int var2, int var3, boolean var4) {
      this.trackedEntity = var1;
      this.trackingDistanceThreshold = var2;
      this.updateFrequency = var3;
      this.sendVelocityUpdates = var4;
      this.encodedPosX = MathHelper.floor_double(var1.posX * 32.0D);
      this.encodedPosY = MathHelper.floor_double(var1.posY * 32.0D);
      this.encodedPosZ = MathHelper.floor_double(var1.posZ * 32.0D);
      this.encodedRotationYaw = MathHelper.floor_float(var1.rotationYaw * 256.0F / 360.0F);
      this.encodedRotationPitch = MathHelper.floor_float(var1.rotationPitch * 256.0F / 360.0F);
      this.lastHeadMotion = MathHelper.floor_float(var1.getRotationYawHead() * 256.0F / 360.0F);
      this.onGround = var1.onGround;
   }

   public boolean equals(Object var1) {
      return var1 instanceof EntityTrackerEntry && ((EntityTrackerEntry)var1).trackedEntity.getEntityID() == this.trackedEntity.getEntityID();
   }

   public int hashCode() {
      return this.trackedEntity.getEntityID();
   }

   public void updatePlayerList(List var1) {
      this.playerEntitiesUpdated = false;
      if(!this.firstUpdateDone || this.trackedEntity.getDistanceSq(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0D) {
         this.lastTrackedEntityPosX = this.trackedEntity.posX;
         this.lastTrackedEntityPosY = this.trackedEntity.posY;
         this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
         this.firstUpdateDone = true;
         this.playerEntitiesUpdated = true;
         this.updatePlayerEntities(var1);
      }

      if(this.field_85178_v != this.trackedEntity.ridingEntity || this.trackedEntity.ridingEntity != null && this.updateCounter % 60 == 0) {
         this.field_85178_v = this.trackedEntity.ridingEntity;
         this.sendPacketToTrackedPlayers(new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
      }

      if(this.trackedEntity instanceof EntityItemFrame && this.updateCounter % 10 == 0) {
         EntityItemFrame var2 = (EntityItemFrame)this.trackedEntity;
         ItemStack var3 = var2.getDisplayedItem();
         if(var3.getItem() instanceof ItemMap) {
            MapData var4 = Items.filled_map.getMapData(var3, this.trackedEntity.worldObj);

            for(EntityPlayer var6 : var1) {
               EntityPlayerMP var7 = (EntityPlayerMP)var6;
               var4.updateVisiblePlayers(var7, var3);
               Packet var8 = Items.filled_map.createMapDataPacket(var3, this.trackedEntity.worldObj, var7);
               var7.playerNetServerHandler.sendPacket(var8);
            }
         }

         this.sendMetadataToAllAssociatedPlayers();
      }

      if(this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.k().a()) {
         if(this.trackedEntity.ridingEntity == null) {
            ++this.ticksSinceLastForcedTeleport;
            int var24 = MathHelper.floor_double(this.trackedEntity.posX * 32.0D);
            int var27 = MathHelper.floor_double(this.trackedEntity.posY * 32.0D);
            int var28 = MathHelper.floor_double(this.trackedEntity.posZ * 32.0D);
            int var29 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
            int var30 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
            int var31 = var24 - this.encodedPosX;
            int var32 = var27 - this.encodedPosY;
            int var9 = var28 - this.encodedPosZ;
            Object var10 = null;
            if(Math.abs(var31) < 4 && Math.abs(var32) < 4 && Math.abs(var9) < 4 && this.updateCounter % 60 != 0) {
               boolean var35 = false;
            } else {
               boolean var34 = true;
            }

            if(Math.abs(var29 - this.encodedRotationYaw) < 4 && Math.abs(var30 - this.encodedRotationPitch) < 4) {
               boolean var37 = false;
            } else {
               boolean var36 = true;
            }

            if(this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow) {
               if(var31 >= -128 && var31 < 128 && var32 >= -128 && var32 < 128 && var9 >= -128 && var9 < 128 && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity && this.onGround == this.trackedEntity.onGround) {
                  if(!(this.trackedEntity instanceof EntityArrow)) {
                     var10 = new S14PacketEntity$S15PacketEntityRelMove(this.trackedEntity.getEntityID(), (byte)var31, (byte)var32, (byte)var9, this.trackedEntity.onGround);
                  } else {
                     var10 = new S14PacketEntity$S17PacketEntityLookMove(this.trackedEntity.getEntityID(), (byte)var31, (byte)var32, (byte)var9, (byte)var29, (byte)var30, this.trackedEntity.onGround);
                  }
               } else {
                  this.onGround = this.trackedEntity.onGround;
                  this.ticksSinceLastForcedTeleport = 0;
                  var10 = new S18PacketEntityTeleport(this.trackedEntity.getEntityID(), var24, var27, var28, (byte)var29, (byte)var30, this.trackedEntity.onGround);
               }
            }

            if(this.sendVelocityUpdates) {
               double var13 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
               double var15 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
               double var17 = this.trackedEntity.motionZ - this.motionZ;
               double var19 = 0.02D;
               double var21 = var13 * var13 + var15 * var15 + var17 * var17;
               if(var21 > 4.0E-4D || var21 > 0.0D && this.trackedEntity.motionX == 0.0D && this.trackedEntity.motionY == 0.0D && this.trackedEntity.motionZ == 0.0D) {
                  this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
                  this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
                  this.motionZ = this.trackedEntity.motionZ;
                  this.sendPacketToTrackedPlayers(new S12PacketEntityVelocity(this.trackedEntity.getEntityID(), this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ));
               }
            }

            this.sendPacketToTrackedPlayers((Packet)var10);
            this.sendMetadataToAllAssociatedPlayers();
            this.encodedPosX = var24;
            this.encodedPosY = var27;
            this.encodedPosZ = var28;
            this.encodedRotationYaw = var29;
            this.encodedRotationPitch = var30;
            this.ridingEntity = false;
         } else {
            int var23 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0F / 360.0F);
            int var26 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0F / 360.0F);
            if(Math.abs(var23 - this.encodedRotationYaw) < 4 && Math.abs(var26 - this.encodedRotationPitch) < 4) {
               boolean var33 = false;
            } else {
               boolean var10000 = true;
            }

            this.sendPacketToTrackedPlayers(new S14PacketEntity$S16PacketEntityLook(this.trackedEntity.getEntityID(), (byte)var23, (byte)var26, this.trackedEntity.onGround));
            this.encodedRotationYaw = var23;
            this.encodedRotationPitch = var26;
            this.encodedPosX = MathHelper.floor_double(this.trackedEntity.posX * 32.0D);
            this.encodedPosY = MathHelper.floor_double(this.trackedEntity.posY * 32.0D);
            this.encodedPosZ = MathHelper.floor_double(this.trackedEntity.posZ * 32.0D);
            this.sendMetadataToAllAssociatedPlayers();
            this.ridingEntity = true;
         }

         int var25 = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
         if(Math.abs(var25 - this.lastHeadMotion) >= 4) {
            this.sendPacketToTrackedPlayers(new S19PacketEntityHeadLook(this.trackedEntity, (byte)var25));
            this.lastHeadMotion = var25;
         }

         this.trackedEntity.isAirBorne = false;
      }

      ++this.updateCounter;
      if(this.trackedEntity.velocityChanged) {
         this.func_151261_b(new S12PacketEntityVelocity(this.trackedEntity));
         this.trackedEntity.velocityChanged = false;
      }

   }

   private void sendMetadataToAllAssociatedPlayers() {
      rF var1 = this.trackedEntity.k();
      if(var1.a()) {
         this.func_151261_b(new S1CPacketEntityMetadata(this.trackedEntity.getEntityID(), var1, false));
      }

      if(this.trackedEntity instanceof EntityLivingBase) {
         ServersideAttributeMap var2 = (ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
         Set var3 = var2.getAttributeInstanceSet();
         if(!var3.isEmpty()) {
            this.func_151261_b(new S20PacketEntityProperties(this.trackedEntity.getEntityID(), var3));
         }

         var3.clear();
      }

   }

   public void sendPacketToTrackedPlayers(Packet var1) {
      for(EntityPlayerMP var3 : this.trackingPlayers) {
         var3.playerNetServerHandler.sendPacket(var1);
      }

   }

   public void func_151261_b(Packet var1) {
      this.sendPacketToTrackedPlayers(var1);
      if(this.trackedEntity instanceof EntityPlayerMP) {
         ((EntityPlayerMP)this.trackedEntity).playerNetServerHandler.sendPacket(var1);
      }

   }

   public void sendDestroyEntityPacketToTrackedPlayers() {
      for(EntityPlayerMP var2 : this.trackingPlayers) {
         var2.removeEntity(this.trackedEntity);
      }

   }

   public void removeFromTrackedPlayers(EntityPlayerMP var1) {
      if(this.trackingPlayers.contains(var1)) {
         var1.removeEntity(this.trackedEntity);
         this.trackingPlayers.remove(var1);
      }

   }

   public void updatePlayerEntity(EntityPlayerMP var1) {
      if(var1 != this.trackedEntity) {
         if(this.func_180233_c(var1)) {
            if(!this.trackingPlayers.contains(var1) && (this.isPlayerWatchingThisChunk(var1) || this.trackedEntity.forceSpawn)) {
               this.trackingPlayers.add(var1);
               Packet var2 = this.func_151260_c();
               var1.playerNetServerHandler.sendPacket(var2);
               if(!this.trackedEntity.k().e()) {
                  var1.playerNetServerHandler.sendPacket(new S1CPacketEntityMetadata(this.trackedEntity.getEntityID(), this.trackedEntity.k(), true));
               }

               NBTTagCompound var3 = this.trackedEntity.getNBTTagCompound();
               var1.playerNetServerHandler.sendPacket(new S49PacketUpdateEntityNBT(this.trackedEntity.getEntityID(), var3));
               if(this.trackedEntity instanceof EntityLivingBase) {
                  ServersideAttributeMap var4 = (ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap();
                  Collection var5 = var4.getWatchedAttributes();
                  if(!var5.isEmpty()) {
                     var1.playerNetServerHandler.sendPacket(new S20PacketEntityProperties(this.trackedEntity.getEntityID(), var5));
                  }
               }

               this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
               this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
               this.motionZ = this.trackedEntity.motionZ;
               if(this.sendVelocityUpdates && !(var2 instanceof S0FPacketSpawnMob)) {
                  var1.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(this.trackedEntity.getEntityID(), this.trackedEntity.motionX, this.trackedEntity.motionY, this.trackedEntity.motionZ));
               }

               if(this.trackedEntity.ridingEntity != null) {
                  var1.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(0, this.trackedEntity, this.trackedEntity.ridingEntity));
               }

               if(this.trackedEntity instanceof EntityLiving && ((EntityLiving)this.trackedEntity).getLeashedToEntity() != null) {
                  var1.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(1, this.trackedEntity, ((EntityLiving)this.trackedEntity).getLeashedToEntity()));
               }

               if(this.trackedEntity instanceof EntityLivingBase) {
                  for(int var7 = 0; var7 < 5; ++var7) {
                     ItemStack var10 = ((EntityLivingBase)this.trackedEntity).getEquipmentInSlot(var7);
                     var1.playerNetServerHandler.sendPacket(new S04PacketEntityEquipment(this.trackedEntity.getEntityID(), var7, var10));
                  }
               }

               if(this.trackedEntity instanceof EntityPlayer) {
                  EntityPlayer var8 = (EntityPlayer)this.trackedEntity;
                  if(var8.isPlayerSleeping()) {
                     var1.playerNetServerHandler.sendPacket(new S0APacketUseBed(var8, new BlockPos(this.trackedEntity)));
                  }
               }

               if(this.trackedEntity instanceof EntityLivingBase) {
                  EntityLivingBase var9 = (EntityLivingBase)this.trackedEntity;

                  for(PotionEffect var6 : var9.getActivePotionEffects()) {
                     var1.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.trackedEntity.getEntityID(), var6));
                  }
               }
            }
         } else if(this.trackingPlayers.contains(var1)) {
            this.trackingPlayers.remove(var1);
            var1.removeEntity(this.trackedEntity);
         }
      }

   }

   public boolean func_180233_c(EntityPlayerMP var1) {
      double var2 = var1.posX - (double)(this.encodedPosX / 32);
      double var4 = var1.posZ - (double)(this.encodedPosZ / 32);
      return var2 >= (double)(-this.trackingDistanceThreshold) && var2 <= (double)this.trackingDistanceThreshold && var4 >= (double)(-this.trackingDistanceThreshold) && var4 <= (double)this.trackingDistanceThreshold && this.trackedEntity.isSpectatedByPlayer(var1);
   }

   private boolean isPlayerWatchingThisChunk(EntityPlayerMP var1) {
      return var1.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(var1, this.trackedEntity.chunkCoordX, this.trackedEntity.chunkCoordZ);
   }

   public void updatePlayerEntities(List var1) {
      for(EntityPlayer var3 : var1) {
         this.updatePlayerEntity((EntityPlayerMP)var3);
      }

   }

   private Packet func_151260_c() {
      if(this.trackedEntity.isDead) {
         LOGGER.warn("Fetching addPacket for removed entity");
      }

      if(this.trackedEntity instanceof EntityItem) {
         return new S0EPacketSpawnObject(this.trackedEntity, 2, 1);
      } else if(this.trackedEntity instanceof EntityPlayerMP) {
         return new S0CPacketSpawnPlayer((EntityPlayer)this.trackedEntity);
      } else if(this.trackedEntity instanceof EntityMinecart) {
         EntityMinecart var9 = (EntityMinecart)this.trackedEntity;
         return new S0EPacketSpawnObject(this.trackedEntity, 10, var9.getMinecartType().getNetworkID());
      } else if(this.trackedEntity instanceof EntityBoat) {
         return new S0EPacketSpawnObject(this.trackedEntity, 1);
      } else if(this.trackedEntity instanceof IAnimals) {
         this.lastHeadMotion = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0F / 360.0F);
         return new S0FPacketSpawnMob((EntityLivingBase)this.trackedEntity);
      } else if(this.trackedEntity instanceof EntityFishHook) {
         EntityPlayer var8 = ((EntityFishHook)this.trackedEntity).angler;
         return new S0EPacketSpawnObject(this.trackedEntity, 90, var8.getEntityID());
      } else if(this.trackedEntity instanceof EntityArrow) {
         Entity var7 = ((EntityArrow)this.trackedEntity).shootingEntity;
         return new S0EPacketSpawnObject(this.trackedEntity, 60, var7.getEntityID());
      } else if(this.trackedEntity instanceof EntitySnowball) {
         return new S0EPacketSpawnObject(this.trackedEntity, 61);
      } else if(this.trackedEntity instanceof EntityPotion) {
         return new S0EPacketSpawnObject(this.trackedEntity, 73, ((EntityPotion)this.trackedEntity).getPotionDamage());
      } else if(this.trackedEntity instanceof EntityExpBottle) {
         return new S0EPacketSpawnObject(this.trackedEntity, 75);
      } else if(this.trackedEntity instanceof EntityEnderPearl) {
         return new S0EPacketSpawnObject(this.trackedEntity, 65);
      } else if(this.trackedEntity instanceof EntityEnderEye) {
         return new S0EPacketSpawnObject(this.trackedEntity, 72);
      } else if(this.trackedEntity instanceof EntityFireworkRocket) {
         return new S0EPacketSpawnObject(this.trackedEntity, 76);
      } else if(this.trackedEntity instanceof EntityFireball) {
         EntityFireball var6 = (EntityFireball)this.trackedEntity;
         S0EPacketSpawnObject var11 = null;
         byte var14 = 63;
         if(this.trackedEntity instanceof EntitySmallFireball) {
            var14 = 64;
         } else if(this.trackedEntity instanceof EntityWitherSkull) {
            var14 = 66;
         }

         if(var6.shootingEntity != null) {
            var11 = new S0EPacketSpawnObject(this.trackedEntity, var14, ((EntityFireball)this.trackedEntity).shootingEntity.getEntityID());
         } else {
            var11 = new S0EPacketSpawnObject(this.trackedEntity, var14, 0);
         }

         var11.setSpeedX((int)(var6.accelerationX * 8000.0D));
         var11.setSpeedY((int)(var6.accelerationY * 8000.0D));
         var11.setSpeedZ((int)(var6.accelerationZ * 8000.0D));
         return var11;
      } else if(this.trackedEntity instanceof EntityEgg) {
         return new S0EPacketSpawnObject(this.trackedEntity, 62);
      } else if(this.trackedEntity instanceof EntityTNTPrimed) {
         return new S0EPacketSpawnObject(this.trackedEntity, 50);
      } else if(this.trackedEntity instanceof EntityEnderCrystal) {
         return new S0EPacketSpawnObject(this.trackedEntity, 51);
      } else if(this.trackedEntity instanceof EntityFallingBlock) {
         EntityFallingBlock var5 = (EntityFallingBlock)this.trackedEntity;
         return new S0EPacketSpawnObject(this.trackedEntity, 70, Block.getStateId(var5.getBlock()));
      } else if(this.trackedEntity instanceof EntityArmorStand) {
         return new S0EPacketSpawnObject(this.trackedEntity, 78);
      } else if(this.trackedEntity instanceof EntityPainting) {
         return new S10PacketSpawnPainting((EntityPainting)this.trackedEntity);
      } else if(this.trackedEntity instanceof EntityItemFrame) {
         EntityItemFrame var4 = (EntityItemFrame)this.trackedEntity;
         S0EPacketSpawnObject var10 = new S0EPacketSpawnObject(this.trackedEntity, 71, var4.facingDirection.getHorizontalIndex());
         BlockPos var13 = var4.getHangingPosition();
         var10.setX(MathHelper.floor_float((float)(var13.getX() * 32)));
         var10.setY(MathHelper.floor_float((float)(var13.getY() * 32)));
         var10.setZ(MathHelper.floor_float((float)(var13.getZ() * 32)));
         return var10;
      } else if(this.trackedEntity instanceof EntityLeashKnot) {
         EntityLeashKnot var1 = (EntityLeashKnot)this.trackedEntity;
         S0EPacketSpawnObject var2 = new S0EPacketSpawnObject(this.trackedEntity, 77);
         BlockPos var3 = var1.getHangingPosition();
         var2.setX(MathHelper.floor_float((float)(var3.getX() * 32)));
         var2.setY(MathHelper.floor_float((float)(var3.getY() * 32)));
         var2.setZ(MathHelper.floor_float((float)(var3.getZ() * 32)));
         return var2;
      } else if(this.trackedEntity instanceof EntityXPOrb) {
         return new S11PacketSpawnExperienceOrb((EntityXPOrb)this.trackedEntity);
      } else {
         throw new IllegalArgumentException("Don\'t know how to add " + this.trackedEntity.getClass() + "!");
      }
   }

   public void removeTrackedPlayerSymmetric(EntityPlayerMP var1) {
      if(this.trackingPlayers.contains(var1)) {
         this.trackingPlayers.remove(var1);
         var1.removeEntity(this.trackedEntity);
      }

   }

   private static IllegalArgumentException a(IllegalArgumentException var0) {
      return var0;
   }
}
