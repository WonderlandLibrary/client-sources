package net.minecraft.src;

import java.util.*;

public class EntityTrackerEntry
{
    public Entity myEntity;
    public int blocksDistanceThreshold;
    public int updateFrequency;
    public int lastScaledXPosition;
    public int lastScaledYPosition;
    public int lastScaledZPosition;
    public int lastYaw;
    public int lastPitch;
    public int lastHeadMotion;
    public double motionX;
    public double motionY;
    public double motionZ;
    public int ticks;
    private double posX;
    private double posY;
    private double posZ;
    private boolean isDataInitialized;
    private boolean sendVelocityUpdates;
    private int ticksSinceLastForcedTeleport;
    private Entity field_85178_v;
    private boolean ridingEntity;
    public boolean playerEntitiesUpdated;
    public Set trackingPlayers;
    
    public EntityTrackerEntry(final Entity par1Entity, final int par2, final int par3, final boolean par4) {
        this.ticks = 0;
        this.isDataInitialized = false;
        this.ticksSinceLastForcedTeleport = 0;
        this.ridingEntity = false;
        this.playerEntitiesUpdated = false;
        this.trackingPlayers = new HashSet();
        this.myEntity = par1Entity;
        this.blocksDistanceThreshold = par2;
        this.updateFrequency = par3;
        this.sendVelocityUpdates = par4;
        this.lastScaledXPosition = MathHelper.floor_double(par1Entity.posX * 32.0);
        this.lastScaledYPosition = MathHelper.floor_double(par1Entity.posY * 32.0);
        this.lastScaledZPosition = MathHelper.floor_double(par1Entity.posZ * 32.0);
        this.lastYaw = MathHelper.floor_float(par1Entity.rotationYaw * 256.0f / 360.0f);
        this.lastPitch = MathHelper.floor_float(par1Entity.rotationPitch * 256.0f / 360.0f);
        this.lastHeadMotion = MathHelper.floor_float(par1Entity.getRotationYawHead() * 256.0f / 360.0f);
    }
    
    @Override
    public boolean equals(final Object par1Obj) {
        return par1Obj instanceof EntityTrackerEntry && ((EntityTrackerEntry)par1Obj).myEntity.entityId == this.myEntity.entityId;
    }
    
    @Override
    public int hashCode() {
        return this.myEntity.entityId;
    }
    
    public void sendLocationToAllClients(final List par1List) {
        this.playerEntitiesUpdated = false;
        if (!this.isDataInitialized || this.myEntity.getDistanceSq(this.posX, this.posY, this.posZ) > 16.0) {
            this.posX = this.myEntity.posX;
            this.posY = this.myEntity.posY;
            this.posZ = this.myEntity.posZ;
            this.isDataInitialized = true;
            this.playerEntitiesUpdated = true;
            this.sendEventsToPlayers(par1List);
        }
        if (this.field_85178_v != this.myEntity.ridingEntity || (this.myEntity.ridingEntity != null && this.ticks % 60 == 0)) {
            this.field_85178_v = this.myEntity.ridingEntity;
            this.sendPacketToAllTrackingPlayers(new Packet39AttachEntity(this.myEntity, this.myEntity.ridingEntity));
        }
        if (this.myEntity instanceof EntityItemFrame && this.ticks % 10 == 0) {
            final EntityItemFrame var23 = (EntityItemFrame)this.myEntity;
            final ItemStack var24 = var23.getDisplayedItem();
            if (var24 != null && var24.getItem() instanceof ItemMap) {
                final MapData var25 = Item.map.getMapData(var24, this.myEntity.worldObj);
                for (final EntityPlayer var27 : par1List) {
                    final EntityPlayerMP var28 = (EntityPlayerMP)var27;
                    var25.updateVisiblePlayers(var28, var24);
                    if (var28.playerNetServerHandler.packetSize() <= 5) {
                        final Packet var29 = Item.map.createMapDataPacket(var24, this.myEntity.worldObj, var28);
                        if (var29 == null) {
                            continue;
                        }
                        var28.playerNetServerHandler.sendPacketToPlayer(var29);
                    }
                }
            }
            final DataWatcher var30 = this.myEntity.getDataWatcher();
            if (var30.hasChanges()) {
                this.sendPacketToAllAssociatedPlayers(new Packet40EntityMetadata(this.myEntity.entityId, var30, false));
            }
        }
        else if (this.ticks % this.updateFrequency == 0 || this.myEntity.isAirBorne || this.myEntity.getDataWatcher().hasChanges()) {
            if (this.myEntity.ridingEntity == null) {
                ++this.ticksSinceLastForcedTeleport;
                final int var31 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posX);
                final int var32 = MathHelper.floor_double(this.myEntity.posY * 32.0);
                final int var33 = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posZ);
                final int var34 = MathHelper.floor_float(this.myEntity.rotationYaw * 256.0f / 360.0f);
                final int var35 = MathHelper.floor_float(this.myEntity.rotationPitch * 256.0f / 360.0f);
                final int var36 = var31 - this.lastScaledXPosition;
                final int var37 = var32 - this.lastScaledYPosition;
                final int var38 = var33 - this.lastScaledZPosition;
                Object var39 = null;
                final boolean var40 = Math.abs(var36) >= 4 || Math.abs(var37) >= 4 || Math.abs(var38) >= 4 || this.ticks % 60 == 0;
                final boolean var41 = Math.abs(var34 - this.lastYaw) >= 4 || Math.abs(var35 - this.lastPitch) >= 4;
                if (this.ticks > 0 || this.myEntity instanceof EntityArrow) {
                    if (var36 >= -128 && var36 < 128 && var37 >= -128 && var37 < 128 && var38 >= -128 && var38 < 128 && this.ticksSinceLastForcedTeleport <= 400 && !this.ridingEntity) {
                        if (var40 && var41) {
                            var39 = new Packet33RelEntityMoveLook(this.myEntity.entityId, (byte)var36, (byte)var37, (byte)var38, (byte)var34, (byte)var35);
                        }
                        else if (var40) {
                            var39 = new Packet31RelEntityMove(this.myEntity.entityId, (byte)var36, (byte)var37, (byte)var38);
                        }
                        else if (var41) {
                            var39 = new Packet32EntityLook(this.myEntity.entityId, (byte)var34, (byte)var35);
                        }
                    }
                    else {
                        this.ticksSinceLastForcedTeleport = 0;
                        var39 = new Packet34EntityTeleport(this.myEntity.entityId, var31, var32, var33, (byte)var34, (byte)var35);
                    }
                }
                if (this.sendVelocityUpdates) {
                    final double var42 = this.myEntity.motionX - this.motionX;
                    final double var43 = this.myEntity.motionY - this.motionY;
                    final double var44 = this.myEntity.motionZ - this.motionZ;
                    final double var45 = 0.02;
                    final double var46 = var42 * var42 + var43 * var43 + var44 * var44;
                    if (var46 > var45 * var45 || (var46 > 0.0 && this.myEntity.motionX == 0.0 && this.myEntity.motionY == 0.0 && this.myEntity.motionZ == 0.0)) {
                        this.motionX = this.myEntity.motionX;
                        this.motionY = this.myEntity.motionY;
                        this.motionZ = this.myEntity.motionZ;
                        this.sendPacketToAllTrackingPlayers(new Packet28EntityVelocity(this.myEntity.entityId, this.motionX, this.motionY, this.motionZ));
                    }
                }
                if (var39 != null) {
                    this.sendPacketToAllTrackingPlayers((Packet)var39);
                }
                final DataWatcher var47 = this.myEntity.getDataWatcher();
                if (var47.hasChanges()) {
                    this.sendPacketToAllAssociatedPlayers(new Packet40EntityMetadata(this.myEntity.entityId, var47, false));
                }
                if (var40) {
                    this.lastScaledXPosition = var31;
                    this.lastScaledYPosition = var32;
                    this.lastScaledZPosition = var33;
                }
                if (var41) {
                    this.lastYaw = var34;
                    this.lastPitch = var35;
                }
                this.ridingEntity = false;
            }
            else {
                final int var31 = MathHelper.floor_float(this.myEntity.rotationYaw * 256.0f / 360.0f);
                final int var32 = MathHelper.floor_float(this.myEntity.rotationPitch * 256.0f / 360.0f);
                final boolean var48 = Math.abs(var31 - this.lastYaw) >= 4 || Math.abs(var32 - this.lastPitch) >= 4;
                if (var48) {
                    this.sendPacketToAllTrackingPlayers(new Packet32EntityLook(this.myEntity.entityId, (byte)var31, (byte)var32));
                    this.lastYaw = var31;
                    this.lastPitch = var32;
                }
                this.lastScaledXPosition = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posX);
                this.lastScaledYPosition = MathHelper.floor_double(this.myEntity.posY * 32.0);
                this.lastScaledZPosition = this.myEntity.myEntitySize.multiplyBy32AndRound(this.myEntity.posZ);
                final DataWatcher var49 = this.myEntity.getDataWatcher();
                if (var49.hasChanges()) {
                    this.sendPacketToAllAssociatedPlayers(new Packet40EntityMetadata(this.myEntity.entityId, var49, false));
                }
                this.ridingEntity = true;
            }
            final int var31 = MathHelper.floor_float(this.myEntity.getRotationYawHead() * 256.0f / 360.0f);
            if (Math.abs(var31 - this.lastHeadMotion) >= 4) {
                this.sendPacketToAllTrackingPlayers(new Packet35EntityHeadRotation(this.myEntity.entityId, (byte)var31));
                this.lastHeadMotion = var31;
            }
            this.myEntity.isAirBorne = false;
        }
        ++this.ticks;
        if (this.myEntity.velocityChanged) {
            this.sendPacketToAllAssociatedPlayers(new Packet28EntityVelocity(this.myEntity));
            this.myEntity.velocityChanged = false;
        }
    }
    
    public void sendPacketToAllTrackingPlayers(final Packet par1Packet) {
        for (final EntityPlayerMP var3 : this.trackingPlayers) {
            var3.playerNetServerHandler.sendPacketToPlayer(par1Packet);
        }
    }
    
    public void sendPacketToAllAssociatedPlayers(final Packet par1Packet) {
        this.sendPacketToAllTrackingPlayers(par1Packet);
        if (this.myEntity instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.myEntity).playerNetServerHandler.sendPacketToPlayer(par1Packet);
        }
    }
    
    public void informAllAssociatedPlayersOfItemDestruction() {
        for (final EntityPlayerMP var2 : this.trackingPlayers) {
            var2.destroyedItemsNetCache.add(this.myEntity.entityId);
        }
    }
    
    public void removeFromWatchingList(final EntityPlayerMP par1EntityPlayerMP) {
        if (this.trackingPlayers.contains(par1EntityPlayerMP)) {
            par1EntityPlayerMP.destroyedItemsNetCache.add(this.myEntity.entityId);
            this.trackingPlayers.remove(par1EntityPlayerMP);
        }
    }
    
    public void tryStartWachingThis(final EntityPlayerMP par1EntityPlayerMP) {
        if (par1EntityPlayerMP != this.myEntity) {
            final double var2 = par1EntityPlayerMP.posX - this.lastScaledXPosition / 32;
            final double var3 = par1EntityPlayerMP.posZ - this.lastScaledZPosition / 32;
            if (var2 >= -this.blocksDistanceThreshold && var2 <= this.blocksDistanceThreshold && var3 >= -this.blocksDistanceThreshold && var3 <= this.blocksDistanceThreshold) {
                if (!this.trackingPlayers.contains(par1EntityPlayerMP) && (this.isPlayerWatchingThisChunk(par1EntityPlayerMP) || this.myEntity.field_98038_p)) {
                    this.trackingPlayers.add(par1EntityPlayerMP);
                    final Packet var4 = this.getPacketForThisEntity();
                    par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(var4);
                    if (!this.myEntity.getDataWatcher().getIsBlank()) {
                        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet40EntityMetadata(this.myEntity.entityId, this.myEntity.getDataWatcher(), true));
                    }
                    this.motionX = this.myEntity.motionX;
                    this.motionY = this.myEntity.motionY;
                    this.motionZ = this.myEntity.motionZ;
                    if (this.sendVelocityUpdates && !(var4 instanceof Packet24MobSpawn)) {
                        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet28EntityVelocity(this.myEntity.entityId, this.myEntity.motionX, this.myEntity.motionY, this.myEntity.motionZ));
                    }
                    if (this.myEntity.ridingEntity != null) {
                        par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet39AttachEntity(this.myEntity, this.myEntity.ridingEntity));
                    }
                    if (this.myEntity instanceof EntityLiving) {
                        for (int var5 = 0; var5 < 5; ++var5) {
                            final ItemStack var6 = ((EntityLiving)this.myEntity).getCurrentItemOrArmor(var5);
                            if (var6 != null) {
                                par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet5PlayerInventory(this.myEntity.entityId, var5, var6));
                            }
                        }
                    }
                    if (this.myEntity instanceof EntityPlayer) {
                        final EntityPlayer var7 = (EntityPlayer)this.myEntity;
                        if (var7.isPlayerSleeping()) {
                            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet17Sleep(this.myEntity, 0, MathHelper.floor_double(this.myEntity.posX), MathHelper.floor_double(this.myEntity.posY), MathHelper.floor_double(this.myEntity.posZ)));
                        }
                    }
                    if (this.myEntity instanceof EntityLiving) {
                        final EntityLiving var8 = (EntityLiving)this.myEntity;
                        for (final PotionEffect var10 : var8.getActivePotionEffects()) {
                            par1EntityPlayerMP.playerNetServerHandler.sendPacketToPlayer(new Packet41EntityEffect(this.myEntity.entityId, var10));
                        }
                    }
                }
            }
            else if (this.trackingPlayers.contains(par1EntityPlayerMP)) {
                this.trackingPlayers.remove(par1EntityPlayerMP);
                par1EntityPlayerMP.destroyedItemsNetCache.add(this.myEntity.entityId);
            }
        }
    }
    
    private boolean isPlayerWatchingThisChunk(final EntityPlayerMP par1EntityPlayerMP) {
        return par1EntityPlayerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(par1EntityPlayerMP, this.myEntity.chunkCoordX, this.myEntity.chunkCoordZ);
    }
    
    public void sendEventsToPlayers(final List par1List) {
        for (int var2 = 0; var2 < par1List.size(); ++var2) {
            this.tryStartWachingThis(par1List.get(var2));
        }
    }
    
    private Packet getPacketForThisEntity() {
        if (this.myEntity.isDead) {
            this.myEntity.worldObj.getWorldLogAgent().logWarning("Fetching addPacket for removed entity");
        }
        if (this.myEntity instanceof EntityItem) {
            return new Packet23VehicleSpawn(this.myEntity, 2, 1);
        }
        if (this.myEntity instanceof EntityPlayerMP) {
            return new Packet20NamedEntitySpawn((EntityPlayer)this.myEntity);
        }
        if (this.myEntity instanceof EntityMinecart) {
            final EntityMinecart var8 = (EntityMinecart)this.myEntity;
            return new Packet23VehicleSpawn(this.myEntity, 10, var8.getMinecartType());
        }
        if (this.myEntity instanceof EntityBoat) {
            return new Packet23VehicleSpawn(this.myEntity, 1);
        }
        if (this.myEntity instanceof IAnimals || this.myEntity instanceof EntityDragon) {
            this.lastHeadMotion = MathHelper.floor_float(this.myEntity.getRotationYawHead() * 256.0f / 360.0f);
            return new Packet24MobSpawn((EntityLiving)this.myEntity);
        }
        if (this.myEntity instanceof EntityFishHook) {
            final EntityPlayer var9 = ((EntityFishHook)this.myEntity).angler;
            return new Packet23VehicleSpawn(this.myEntity, 90, (var9 != null) ? var9.entityId : this.myEntity.entityId);
        }
        if (this.myEntity instanceof EntityArrow) {
            final Entity var10 = ((EntityArrow)this.myEntity).shootingEntity;
            return new Packet23VehicleSpawn(this.myEntity, 60, (var10 != null) ? var10.entityId : this.myEntity.entityId);
        }
        if (this.myEntity instanceof EntitySnowball) {
            return new Packet23VehicleSpawn(this.myEntity, 61);
        }
        if (this.myEntity instanceof EntityPotion) {
            return new Packet23VehicleSpawn(this.myEntity, 73, ((EntityPotion)this.myEntity).getPotionDamage());
        }
        if (this.myEntity instanceof EntityExpBottle) {
            return new Packet23VehicleSpawn(this.myEntity, 75);
        }
        if (this.myEntity instanceof EntityEnderPearl) {
            return new Packet23VehicleSpawn(this.myEntity, 65);
        }
        if (this.myEntity instanceof EntityEnderEye) {
            return new Packet23VehicleSpawn(this.myEntity, 72);
        }
        if (this.myEntity instanceof EntityFireworkRocket) {
            return new Packet23VehicleSpawn(this.myEntity, 76);
        }
        if (this.myEntity instanceof EntityFireball) {
            final EntityFireball var11 = (EntityFireball)this.myEntity;
            Packet23VehicleSpawn var12 = null;
            byte var13 = 63;
            if (this.myEntity instanceof EntitySmallFireball) {
                var13 = 64;
            }
            else if (this.myEntity instanceof EntityWitherSkull) {
                var13 = 66;
            }
            if (var11.shootingEntity != null) {
                var12 = new Packet23VehicleSpawn(this.myEntity, var13, ((EntityFireball)this.myEntity).shootingEntity.entityId);
            }
            else {
                var12 = new Packet23VehicleSpawn(this.myEntity, var13, 0);
            }
            var12.speedX = (int)(var11.accelerationX * 8000.0);
            var12.speedY = (int)(var11.accelerationY * 8000.0);
            var12.speedZ = (int)(var11.accelerationZ * 8000.0);
            return var12;
        }
        if (this.myEntity instanceof EntityEgg) {
            return new Packet23VehicleSpawn(this.myEntity, 62);
        }
        if (this.myEntity instanceof EntityTNTPrimed) {
            return new Packet23VehicleSpawn(this.myEntity, 50);
        }
        if (this.myEntity instanceof EntityEnderCrystal) {
            return new Packet23VehicleSpawn(this.myEntity, 51);
        }
        if (this.myEntity instanceof EntityFallingSand) {
            final EntityFallingSand var14 = (EntityFallingSand)this.myEntity;
            return new Packet23VehicleSpawn(this.myEntity, 70, var14.blockID | var14.metadata << 16);
        }
        if (this.myEntity instanceof EntityPainting) {
            return new Packet25EntityPainting((EntityPainting)this.myEntity);
        }
        if (this.myEntity instanceof EntityItemFrame) {
            final EntityItemFrame var15 = (EntityItemFrame)this.myEntity;
            final Packet23VehicleSpawn var12 = new Packet23VehicleSpawn(this.myEntity, 71, var15.hangingDirection);
            var12.xPosition = MathHelper.floor_float(var15.xPosition * 32);
            var12.yPosition = MathHelper.floor_float(var15.yPosition * 32);
            var12.zPosition = MathHelper.floor_float(var15.zPosition * 32);
            return var12;
        }
        if (this.myEntity instanceof EntityXPOrb) {
            return new Packet26EntityExpOrb((EntityXPOrb)this.myEntity);
        }
        throw new IllegalArgumentException("Don't know how to add " + this.myEntity.getClass() + "!");
    }
    
    public void removePlayerFromTracker(final EntityPlayerMP par1EntityPlayerMP) {
        if (this.trackingPlayers.contains(par1EntityPlayerMP)) {
            this.trackingPlayers.remove(par1EntityPlayerMP);
            par1EntityPlayerMP.destroyedItemsNetCache.add(this.myEntity.entityId);
        }
    }
}
