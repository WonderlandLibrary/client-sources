package net.minecraft.entity;

import net.minecraft.network.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.block.*;
import net.minecraft.entity.item.*;
import net.minecraft.util.*;
import net.minecraft.potion.*;
import net.minecraft.nbt.*;
import net.minecraft.entity.ai.attributes.*;
import java.util.*;
import com.google.common.collect.*;
import net.minecraft.item.*;
import net.minecraft.init.*;
import net.minecraft.network.play.server.*;
import net.minecraft.world.storage.*;
import org.apache.logging.log4j.*;

public class EntityTrackerEntry
{
    public int encodedPosY;
    public boolean playerEntitiesUpdated;
    public Entity trackedEntity;
    private int ticksSinceLastForcedTeleport;
    private double lastTrackedEntityPosX;
    private static final String[] I;
    public double lastTrackedEntityMotionY;
    public int encodedRotationPitch;
    private boolean ridingEntity;
    public int lastHeadMotion;
    public Set<EntityPlayerMP> trackingPlayers;
    public double lastTrackedEntityMotionX;
    private boolean sendVelocityUpdates;
    private boolean firstUpdateDone;
    public int encodedRotationYaw;
    public int updateFrequency;
    private double lastTrackedEntityPosZ;
    public double motionZ;
    public int encodedPosX;
    private boolean onGround;
    public int updateCounter;
    private static final Logger logger;
    public int trackingDistanceThreshold;
    private double lastTrackedEntityPosY;
    private Entity field_85178_v;
    public int encodedPosZ;
    
    private Packet func_151260_c() {
        if (this.trackedEntity.isDead) {
            EntityTrackerEntry.logger.warn(EntityTrackerEntry.I["".length()]);
        }
        if (this.trackedEntity instanceof EntityItem) {
            return new S0EPacketSpawnObject(this.trackedEntity, "  ".length(), " ".length());
        }
        if (this.trackedEntity instanceof EntityPlayerMP) {
            return new S0CPacketSpawnPlayer((EntityPlayer)this.trackedEntity);
        }
        if (this.trackedEntity instanceof EntityMinecart) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0xB4 ^ 0xBE, ((EntityMinecart)this.trackedEntity).getMinecartType().getNetworkID());
        }
        if (this.trackedEntity instanceof EntityBoat) {
            return new S0EPacketSpawnObject(this.trackedEntity, " ".length());
        }
        if (this.trackedEntity instanceof IAnimals) {
            this.lastHeadMotion = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
            return new S0FPacketSpawnMob((EntityLivingBase)this.trackedEntity);
        }
        if (this.trackedEntity instanceof EntityFishHook) {
            final EntityPlayer angler = ((EntityFishHook)this.trackedEntity).angler;
            final Entity trackedEntity = this.trackedEntity;
            final int n = 0x3A ^ 0x60;
            int n2;
            if (angler != null) {
                n2 = angler.getEntityId();
                "".length();
                if (2 < -1) {
                    throw null;
                }
            }
            else {
                n2 = this.trackedEntity.getEntityId();
            }
            return new S0EPacketSpawnObject(trackedEntity, n, n2);
        }
        if (this.trackedEntity instanceof EntityArrow) {
            final Entity shootingEntity = ((EntityArrow)this.trackedEntity).shootingEntity;
            final Entity trackedEntity2 = this.trackedEntity;
            final int n3 = 0x22 ^ 0x1E;
            int n4;
            if (shootingEntity != null) {
                n4 = shootingEntity.getEntityId();
                "".length();
                if (0 == 3) {
                    throw null;
                }
            }
            else {
                n4 = this.trackedEntity.getEntityId();
            }
            return new S0EPacketSpawnObject(trackedEntity2, n3, n4);
        }
        if (this.trackedEntity instanceof EntitySnowball) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x9A ^ 0xA7);
        }
        if (this.trackedEntity instanceof EntityPotion) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x2E ^ 0x67, ((EntityPotion)this.trackedEntity).getPotionDamage());
        }
        if (this.trackedEntity instanceof EntityExpBottle) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x67 ^ 0x2C);
        }
        if (this.trackedEntity instanceof EntityEnderPearl) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0xD5 ^ 0x94);
        }
        if (this.trackedEntity instanceof EntityEnderEye) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x62 ^ 0x2A);
        }
        if (this.trackedEntity instanceof EntityFireworkRocket) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x33 ^ 0x7F);
        }
        if (this.trackedEntity instanceof EntityFireball) {
            final EntityFireball entityFireball = (EntityFireball)this.trackedEntity;
            int n5 = 0x88 ^ 0xB7;
            if (this.trackedEntity instanceof EntitySmallFireball) {
                n5 = (0xCB ^ 0x8B);
                "".length();
                if (3 < 0) {
                    throw null;
                }
            }
            else if (this.trackedEntity instanceof EntityWitherSkull) {
                n5 = (0x1B ^ 0x59);
            }
            S0EPacketSpawnObject s0EPacketSpawnObject;
            if (entityFireball.shootingEntity != null) {
                s0EPacketSpawnObject = new S0EPacketSpawnObject(this.trackedEntity, n5, ((EntityFireball)this.trackedEntity).shootingEntity.getEntityId());
                "".length();
                if (false) {
                    throw null;
                }
            }
            else {
                s0EPacketSpawnObject = new S0EPacketSpawnObject(this.trackedEntity, n5, "".length());
            }
            s0EPacketSpawnObject.setSpeedX((int)(entityFireball.accelerationX * 8000.0));
            s0EPacketSpawnObject.setSpeedY((int)(entityFireball.accelerationY * 8000.0));
            s0EPacketSpawnObject.setSpeedZ((int)(entityFireball.accelerationZ * 8000.0));
            return s0EPacketSpawnObject;
        }
        if (this.trackedEntity instanceof EntityEgg) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x51 ^ 0x6F);
        }
        if (this.trackedEntity instanceof EntityTNTPrimed) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x65 ^ 0x57);
        }
        if (this.trackedEntity instanceof EntityEnderCrystal) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x83 ^ 0xB0);
        }
        if (this.trackedEntity instanceof EntityFallingBlock) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x86 ^ 0xC0, Block.getStateId(((EntityFallingBlock)this.trackedEntity).getBlock()));
        }
        if (this.trackedEntity instanceof EntityArmorStand) {
            return new S0EPacketSpawnObject(this.trackedEntity, 0x62 ^ 0x2C);
        }
        if (this.trackedEntity instanceof EntityPainting) {
            return new S10PacketSpawnPainting((EntityPainting)this.trackedEntity);
        }
        if (this.trackedEntity instanceof EntityItemFrame) {
            final EntityItemFrame entityItemFrame = (EntityItemFrame)this.trackedEntity;
            final S0EPacketSpawnObject s0EPacketSpawnObject2 = new S0EPacketSpawnObject(this.trackedEntity, 0xC7 ^ 0x80, entityItemFrame.facingDirection.getHorizontalIndex());
            final BlockPos hangingPosition = entityItemFrame.getHangingPosition();
            s0EPacketSpawnObject2.setX(MathHelper.floor_float(hangingPosition.getX() * (0x6C ^ 0x4C)));
            s0EPacketSpawnObject2.setY(MathHelper.floor_float(hangingPosition.getY() * (0x8F ^ 0xAF)));
            s0EPacketSpawnObject2.setZ(MathHelper.floor_float(hangingPosition.getZ() * (0x4A ^ 0x6A)));
            return s0EPacketSpawnObject2;
        }
        if (this.trackedEntity instanceof EntityLeashKnot) {
            final EntityLeashKnot entityLeashKnot = (EntityLeashKnot)this.trackedEntity;
            final S0EPacketSpawnObject s0EPacketSpawnObject3 = new S0EPacketSpawnObject(this.trackedEntity, 0x6D ^ 0x20);
            final BlockPos hangingPosition2 = entityLeashKnot.getHangingPosition();
            s0EPacketSpawnObject3.setX(MathHelper.floor_float(hangingPosition2.getX() * (0x63 ^ 0x43)));
            s0EPacketSpawnObject3.setY(MathHelper.floor_float(hangingPosition2.getY() * (0xAD ^ 0x8D)));
            s0EPacketSpawnObject3.setZ(MathHelper.floor_float(hangingPosition2.getZ() * (0x6E ^ 0x4E)));
            return s0EPacketSpawnObject3;
        }
        if (this.trackedEntity instanceof EntityXPOrb) {
            return new S11PacketSpawnExperienceOrb((EntityXPOrb)this.trackedEntity);
        }
        throw new IllegalArgumentException(EntityTrackerEntry.I[" ".length()] + this.trackedEntity.getClass() + EntityTrackerEntry.I["  ".length()]);
    }
    
    public void removeTrackedPlayerSymmetric(final EntityPlayerMP entityPlayerMP) {
        if (this.trackingPlayers.contains(entityPlayerMP)) {
            this.trackingPlayers.remove(entityPlayerMP);
            entityPlayerMP.removeEntity(this.trackedEntity);
        }
    }
    
    public void updatePlayerEntity(final EntityPlayerMP entityPlayerMP) {
        if (entityPlayerMP != this.trackedEntity) {
            if (this.func_180233_c(entityPlayerMP)) {
                if (!this.trackingPlayers.contains(entityPlayerMP) && (this.isPlayerWatchingThisChunk(entityPlayerMP) || this.trackedEntity.forceSpawn)) {
                    this.trackingPlayers.add(entityPlayerMP);
                    final Packet func_151260_c = this.func_151260_c();
                    entityPlayerMP.playerNetServerHandler.sendPacket(func_151260_c);
                    if (!this.trackedEntity.getDataWatcher().getIsBlank()) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), this.trackedEntity.getDataWatcher(), (boolean)(" ".length() != 0)));
                    }
                    final NBTTagCompound nbtTagCompound = this.trackedEntity.getNBTTagCompound();
                    if (nbtTagCompound != null) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S49PacketUpdateEntityNBT(this.trackedEntity.getEntityId(), nbtTagCompound));
                    }
                    if (this.trackedEntity instanceof EntityLivingBase) {
                        final Collection<IAttributeInstance> watchedAttributes = ((ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap()).getWatchedAttributes();
                        if (!watchedAttributes.isEmpty()) {
                            entityPlayerMP.playerNetServerHandler.sendPacket(new S20PacketEntityProperties(this.trackedEntity.getEntityId(), watchedAttributes));
                        }
                    }
                    this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
                    this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
                    this.motionZ = this.trackedEntity.motionZ;
                    if (this.sendVelocityUpdates && !(func_151260_c instanceof S0FPacketSpawnMob)) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.trackedEntity.motionX, this.trackedEntity.motionY, this.trackedEntity.motionZ));
                    }
                    if (this.trackedEntity.ridingEntity != null) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach("".length(), this.trackedEntity, this.trackedEntity.ridingEntity));
                    }
                    if (this.trackedEntity instanceof EntityLiving && ((EntityLiving)this.trackedEntity).getLeashedToEntity() != null) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(new S1BPacketEntityAttach(" ".length(), this.trackedEntity, ((EntityLiving)this.trackedEntity).getLeashedToEntity()));
                    }
                    if (this.trackedEntity instanceof EntityLivingBase) {
                        int i = "".length();
                        "".length();
                        if (4 <= 3) {
                            throw null;
                        }
                        while (i < (0x7C ^ 0x79)) {
                            final ItemStack equipmentInSlot = ((EntityLivingBase)this.trackedEntity).getEquipmentInSlot(i);
                            if (equipmentInSlot != null) {
                                entityPlayerMP.playerNetServerHandler.sendPacket(new S04PacketEntityEquipment(this.trackedEntity.getEntityId(), i, equipmentInSlot));
                            }
                            ++i;
                        }
                    }
                    if (this.trackedEntity instanceof EntityPlayer) {
                        final EntityPlayer entityPlayer = (EntityPlayer)this.trackedEntity;
                        if (entityPlayer.isPlayerSleeping()) {
                            entityPlayerMP.playerNetServerHandler.sendPacket(new S0APacketUseBed(entityPlayer, new BlockPos(this.trackedEntity)));
                        }
                    }
                    if (this.trackedEntity instanceof EntityLivingBase) {
                        final Iterator<PotionEffect> iterator = ((EntityLivingBase)this.trackedEntity).getActivePotionEffects().iterator();
                        "".length();
                        if (-1 >= 2) {
                            throw null;
                        }
                        while (iterator.hasNext()) {
                            entityPlayerMP.playerNetServerHandler.sendPacket(new S1DPacketEntityEffect(this.trackedEntity.getEntityId(), iterator.next()));
                        }
                        "".length();
                        if (1 < -1) {
                            throw null;
                        }
                    }
                }
            }
            else if (this.trackingPlayers.contains(entityPlayerMP)) {
                this.trackingPlayers.remove(entityPlayerMP);
                entityPlayerMP.removeEntity(this.trackedEntity);
            }
        }
    }
    
    private static void I() {
        (I = new String["   ".length()])["".length()] = I("\u0005\n\u0010/?*\u0001\u0003l6'\u000b4-4(\n\u0010l1,\u001dD>2.\u0000\u0012)3c\n\n8>7\u0016", "CodLW");
        EntityTrackerEntry.I[" ".length()] = I("3*\fj\u0010W.\f\"\u0013W-\r:D\u0003*B,\u0000\u0013e", "wEbMd");
        EntityTrackerEntry.I["  ".length()] = I("`", "AutvL");
    }
    
    @Override
    public boolean equals(final Object o) {
        int n;
        if (o instanceof EntityTrackerEntry) {
            if (((EntityTrackerEntry)o).trackedEntity.getEntityId() == this.trackedEntity.getEntityId()) {
                n = " ".length();
                "".length();
                if (4 <= 0) {
                    throw null;
                }
            }
            else {
                n = "".length();
                "".length();
                if (1 == 3) {
                    throw null;
                }
            }
        }
        else {
            n = "".length();
        }
        return n != 0;
    }
    
    public boolean func_180233_c(final EntityPlayerMP entityPlayerMP) {
        final double n = entityPlayerMP.posX - this.encodedPosX / (0x99 ^ 0xB9);
        final double n2 = entityPlayerMP.posZ - this.encodedPosZ / (0x8F ^ 0xAF);
        if (n >= -this.trackingDistanceThreshold && n <= this.trackingDistanceThreshold && n2 >= -this.trackingDistanceThreshold && n2 <= this.trackingDistanceThreshold && this.trackedEntity.isSpectatedByPlayer(entityPlayerMP)) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public void sendDestroyEntityPacketToTrackedPlayers() {
        final Iterator<EntityPlayerMP> iterator = this.trackingPlayers.iterator();
        "".length();
        if (-1 >= 3) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().removeEntity(this.trackedEntity);
        }
    }
    
    @Override
    public int hashCode() {
        return this.trackedEntity.getEntityId();
    }
    
    public void sendPacketToTrackedPlayers(final Packet packet) {
        final Iterator<EntityPlayerMP> iterator = this.trackingPlayers.iterator();
        "".length();
        if (1 == -1) {
            throw null;
        }
        while (iterator.hasNext()) {
            iterator.next().playerNetServerHandler.sendPacket(packet);
        }
    }
    
    public void updatePlayerEntities(final List<EntityPlayer> list) {
        int i = "".length();
        "".length();
        if (1 == 3) {
            throw null;
        }
        while (i < list.size()) {
            this.updatePlayerEntity(list.get(i));
            ++i;
        }
    }
    
    private boolean isPlayerWatchingThisChunk(final EntityPlayerMP entityPlayerMP) {
        return entityPlayerMP.getServerForPlayer().getPlayerManager().isPlayerWatchingChunk(entityPlayerMP, this.trackedEntity.chunkCoordX, this.trackedEntity.chunkCoordZ);
    }
    
    private void sendMetadataToAllAssociatedPlayers() {
        final DataWatcher dataWatcher = this.trackedEntity.getDataWatcher();
        if (dataWatcher.hasObjectChanged()) {
            this.func_151261_b(new S1CPacketEntityMetadata(this.trackedEntity.getEntityId(), dataWatcher, (boolean)("".length() != 0)));
        }
        if (this.trackedEntity instanceof EntityLivingBase) {
            final Set<IAttributeInstance> attributeInstanceSet = ((ServersideAttributeMap)((EntityLivingBase)this.trackedEntity).getAttributeMap()).getAttributeInstanceSet();
            if (!attributeInstanceSet.isEmpty()) {
                this.func_151261_b(new S20PacketEntityProperties(this.trackedEntity.getEntityId(), attributeInstanceSet));
            }
            attributeInstanceSet.clear();
        }
    }
    
    public EntityTrackerEntry(final Entity trackedEntity, final int trackingDistanceThreshold, final int updateFrequency, final boolean sendVelocityUpdates) {
        this.trackingPlayers = (Set<EntityPlayerMP>)Sets.newHashSet();
        this.trackedEntity = trackedEntity;
        this.trackingDistanceThreshold = trackingDistanceThreshold;
        this.updateFrequency = updateFrequency;
        this.sendVelocityUpdates = sendVelocityUpdates;
        this.encodedPosX = MathHelper.floor_double(trackedEntity.posX * 32.0);
        this.encodedPosY = MathHelper.floor_double(trackedEntity.posY * 32.0);
        this.encodedPosZ = MathHelper.floor_double(trackedEntity.posZ * 32.0);
        this.encodedRotationYaw = MathHelper.floor_float(trackedEntity.rotationYaw * 256.0f / 360.0f);
        this.encodedRotationPitch = MathHelper.floor_float(trackedEntity.rotationPitch * 256.0f / 360.0f);
        this.lastHeadMotion = MathHelper.floor_float(trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
        this.onGround = trackedEntity.onGround;
    }
    
    public void updatePlayerList(final List<EntityPlayer> list) {
        this.playerEntitiesUpdated = ("".length() != 0);
        if (!this.firstUpdateDone || this.trackedEntity.getDistanceSq(this.lastTrackedEntityPosX, this.lastTrackedEntityPosY, this.lastTrackedEntityPosZ) > 16.0) {
            this.lastTrackedEntityPosX = this.trackedEntity.posX;
            this.lastTrackedEntityPosY = this.trackedEntity.posY;
            this.lastTrackedEntityPosZ = this.trackedEntity.posZ;
            this.firstUpdateDone = (" ".length() != 0);
            this.playerEntitiesUpdated = (" ".length() != 0);
            this.updatePlayerEntities(list);
        }
        if (this.field_85178_v != this.trackedEntity.ridingEntity || (this.trackedEntity.ridingEntity != null && this.updateCounter % (0x9E ^ 0xA2) == 0)) {
            this.field_85178_v = this.trackedEntity.ridingEntity;
            this.sendPacketToTrackedPlayers(new S1BPacketEntityAttach("".length(), this.trackedEntity, this.trackedEntity.ridingEntity));
        }
        if (this.trackedEntity instanceof EntityItemFrame && this.updateCounter % (0x5A ^ 0x50) == 0) {
            final ItemStack displayedItem = ((EntityItemFrame)this.trackedEntity).getDisplayedItem();
            if (displayedItem != null && displayedItem.getItem() instanceof ItemMap) {
                final MapData mapData = Items.filled_map.getMapData(displayedItem, this.trackedEntity.worldObj);
                final Iterator<EntityPlayer> iterator = list.iterator();
                "".length();
                if (1 >= 2) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final EntityPlayerMP entityPlayerMP = (EntityPlayerMP)iterator.next();
                    mapData.updateVisiblePlayers(entityPlayerMP, displayedItem);
                    final Packet mapDataPacket = Items.filled_map.createMapDataPacket(displayedItem, this.trackedEntity.worldObj, entityPlayerMP);
                    if (mapDataPacket != null) {
                        entityPlayerMP.playerNetServerHandler.sendPacket(mapDataPacket);
                    }
                }
            }
            this.sendMetadataToAllAssociatedPlayers();
        }
        if (this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.getDataWatcher().hasObjectChanged()) {
            if (this.trackedEntity.ridingEntity == null) {
                this.ticksSinceLastForcedTeleport += " ".length();
                final int floor_double = MathHelper.floor_double(this.trackedEntity.posX * 32.0);
                final int floor_double2 = MathHelper.floor_double(this.trackedEntity.posY * 32.0);
                final int floor_double3 = MathHelper.floor_double(this.trackedEntity.posZ * 32.0);
                final int floor_float = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0f / 360.0f);
                final int floor_float2 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0f / 360.0f);
                final int n = floor_double - this.encodedPosX;
                final int n2 = floor_double2 - this.encodedPosY;
                final int n3 = floor_double3 - this.encodedPosZ;
                Packet packet = null;
                int n4;
                if (Math.abs(n) < (0x68 ^ 0x6C) && Math.abs(n2) < (0x51 ^ 0x55) && Math.abs(n3) < (0x26 ^ 0x22) && this.updateCounter % (0x43 ^ 0x7F) != 0) {
                    n4 = "".length();
                    "".length();
                    if (3 <= -1) {
                        throw null;
                    }
                }
                else {
                    n4 = " ".length();
                }
                final int n5 = n4;
                int n6;
                if (Math.abs(floor_float - this.encodedRotationYaw) < (0x7B ^ 0x7F) && Math.abs(floor_float2 - this.encodedRotationPitch) < (0xE ^ 0xA)) {
                    n6 = "".length();
                    "".length();
                    if (3 <= 2) {
                        throw null;
                    }
                }
                else {
                    n6 = " ".length();
                }
                final int n7 = n6;
                if (this.updateCounter > 0 || this.trackedEntity instanceof EntityArrow) {
                    if (n >= -(58 + 92 - 112 + 90) && n < 119 + 38 - 102 + 73 && n2 >= -(4 + 42 + 64 + 18) && n2 < 54 + 46 - 5 + 33 && n3 >= -(95 + 47 - 37 + 23) && n3 < 46 + 103 - 90 + 69 && this.ticksSinceLastForcedTeleport <= 101 + 98 - 56 + 257 && !this.ridingEntity && this.onGround == this.trackedEntity.onGround) {
                        if ((n5 == 0 || n7 == 0) && !(this.trackedEntity instanceof EntityArrow)) {
                            if (n5 != 0) {
                                packet = new S14PacketEntity.S15PacketEntityRelMove(this.trackedEntity.getEntityId(), (byte)n, (byte)n2, (byte)n3, this.trackedEntity.onGround);
                                "".length();
                                if (1 >= 4) {
                                    throw null;
                                }
                            }
                            else if (n7 != 0) {
                                packet = new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)floor_float, (byte)floor_float2, this.trackedEntity.onGround);
                                "".length();
                                if (4 < 1) {
                                    throw null;
                                }
                            }
                        }
                        else {
                            packet = new S14PacketEntity.S17PacketEntityLookMove(this.trackedEntity.getEntityId(), (byte)n, (byte)n2, (byte)n3, (byte)floor_float, (byte)floor_float2, this.trackedEntity.onGround);
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                    }
                    else {
                        this.onGround = this.trackedEntity.onGround;
                        this.ticksSinceLastForcedTeleport = "".length();
                        packet = new S18PacketEntityTeleport(this.trackedEntity.getEntityId(), floor_double, floor_double2, floor_double3, (byte)floor_float, (byte)floor_float2, this.trackedEntity.onGround);
                    }
                }
                if (this.sendVelocityUpdates) {
                    final double n8 = this.trackedEntity.motionX - this.lastTrackedEntityMotionX;
                    final double n9 = this.trackedEntity.motionY - this.lastTrackedEntityMotionY;
                    final double n10 = this.trackedEntity.motionZ - this.motionZ;
                    final double n11 = 0.02;
                    final double n12 = n8 * n8 + n9 * n9 + n10 * n10;
                    if (n12 > n11 * n11 || (n12 > 0.0 && this.trackedEntity.motionX == 0.0 && this.trackedEntity.motionY == 0.0 && this.trackedEntity.motionZ == 0.0)) {
                        this.lastTrackedEntityMotionX = this.trackedEntity.motionX;
                        this.lastTrackedEntityMotionY = this.trackedEntity.motionY;
                        this.motionZ = this.trackedEntity.motionZ;
                        this.sendPacketToTrackedPlayers(new S12PacketEntityVelocity(this.trackedEntity.getEntityId(), this.lastTrackedEntityMotionX, this.lastTrackedEntityMotionY, this.motionZ));
                    }
                }
                if (packet != null) {
                    this.sendPacketToTrackedPlayers(packet);
                }
                this.sendMetadataToAllAssociatedPlayers();
                if (n5 != 0) {
                    this.encodedPosX = floor_double;
                    this.encodedPosY = floor_double2;
                    this.encodedPosZ = floor_double3;
                }
                if (n7 != 0) {
                    this.encodedRotationYaw = floor_float;
                    this.encodedRotationPitch = floor_float2;
                }
                this.ridingEntity = ("".length() != 0);
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            else {
                final int floor_float3 = MathHelper.floor_float(this.trackedEntity.rotationYaw * 256.0f / 360.0f);
                final int floor_float4 = MathHelper.floor_float(this.trackedEntity.rotationPitch * 256.0f / 360.0f);
                int n13;
                if (Math.abs(floor_float3 - this.encodedRotationYaw) < (0x3A ^ 0x3E) && Math.abs(floor_float4 - this.encodedRotationPitch) < (0x9 ^ 0xD)) {
                    n13 = "".length();
                    "".length();
                    if (4 == 1) {
                        throw null;
                    }
                }
                else {
                    n13 = " ".length();
                }
                if (n13 != 0) {
                    this.sendPacketToTrackedPlayers(new S14PacketEntity.S16PacketEntityLook(this.trackedEntity.getEntityId(), (byte)floor_float3, (byte)floor_float4, this.trackedEntity.onGround));
                    this.encodedRotationYaw = floor_float3;
                    this.encodedRotationPitch = floor_float4;
                }
                this.encodedPosX = MathHelper.floor_double(this.trackedEntity.posX * 32.0);
                this.encodedPosY = MathHelper.floor_double(this.trackedEntity.posY * 32.0);
                this.encodedPosZ = MathHelper.floor_double(this.trackedEntity.posZ * 32.0);
                this.sendMetadataToAllAssociatedPlayers();
                this.ridingEntity = (" ".length() != 0);
            }
            final int floor_float5 = MathHelper.floor_float(this.trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
            if (Math.abs(floor_float5 - this.lastHeadMotion) >= (0x24 ^ 0x20)) {
                this.sendPacketToTrackedPlayers(new S19PacketEntityHeadLook(this.trackedEntity, (byte)floor_float5));
                this.lastHeadMotion = floor_float5;
            }
            this.trackedEntity.isAirBorne = ("".length() != 0);
        }
        this.updateCounter += " ".length();
        if (this.trackedEntity.velocityChanged) {
            this.func_151261_b(new S12PacketEntityVelocity(this.trackedEntity));
            this.trackedEntity.velocityChanged = ("".length() != 0);
        }
    }
    
    public void removeFromTrackedPlayers(final EntityPlayerMP entityPlayerMP) {
        if (this.trackingPlayers.contains(entityPlayerMP)) {
            entityPlayerMP.removeEntity(this.trackedEntity);
            this.trackingPlayers.remove(entityPlayerMP);
        }
    }
    
    public void func_151261_b(final Packet packet) {
        this.sendPacketToTrackedPlayers(packet);
        if (this.trackedEntity instanceof EntityPlayerMP) {
            ((EntityPlayerMP)this.trackedEntity).playerNetServerHandler.sendPacket(packet);
        }
    }
    
    static {
        I();
        logger = LogManager.getLogger();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (2 <= 1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
