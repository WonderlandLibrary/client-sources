/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemFrameEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.entity.projectile.AbstractArrowEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemStack;
import net.minecraft.network.IPacket;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.network.play.server.SEntityEquipmentPacket;
import net.minecraft.network.play.server.SEntityHeadLookPacket;
import net.minecraft.network.play.server.SEntityMetadataPacket;
import net.minecraft.network.play.server.SEntityPacket;
import net.minecraft.network.play.server.SEntityPropertiesPacket;
import net.minecraft.network.play.server.SEntityTeleportPacket;
import net.minecraft.network.play.server.SEntityVelocityPacket;
import net.minecraft.network.play.server.SMountEntityPacket;
import net.minecraft.network.play.server.SPlayEntityEffectPacket;
import net.minecraft.network.play.server.SSetPassengersPacket;
import net.minecraft.network.play.server.SSpawnMobPacket;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.server.ServerWorld;
import net.minecraft.world.storage.MapData;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TrackedEntity {
    private static final Logger LOGGER = LogManager.getLogger();
    private final ServerWorld world;
    private final Entity trackedEntity;
    private final int updateFrequency;
    private final boolean sendVelocityUpdates;
    private final Consumer<IPacket<?>> packetConsumer;
    private long encodedPosX;
    private long encodedPosY;
    private long encodedPosZ;
    private int encodedRotationYaw;
    private int encodedRotationPitch;
    private int encodedRotationYawHead;
    private Vector3d velocity = Vector3d.ZERO;
    private int updateCounter;
    private int ticksSinceAbsoluteTeleport;
    private List<Entity> passengers = Collections.emptyList();
    private boolean riding;
    private boolean onGround;

    public TrackedEntity(ServerWorld serverWorld, Entity entity2, int n, boolean bl, Consumer<IPacket<?>> consumer) {
        this.world = serverWorld;
        this.packetConsumer = consumer;
        this.trackedEntity = entity2;
        this.updateFrequency = n;
        this.sendVelocityUpdates = bl;
        this.updateEncodedPosition();
        this.encodedRotationYaw = MathHelper.floor(entity2.rotationYaw * 256.0f / 360.0f);
        this.encodedRotationPitch = MathHelper.floor(entity2.rotationPitch * 256.0f / 360.0f);
        this.encodedRotationYawHead = MathHelper.floor(entity2.getRotationYawHead() * 256.0f / 360.0f);
        this.onGround = entity2.isOnGround();
    }

    /*
     * WARNING - void declaration
     */
    public void tick() {
        Object object;
        List<Entity> list = this.trackedEntity.getPassengers();
        if (!list.equals(this.passengers)) {
            this.passengers = list;
            this.packetConsumer.accept(new SSetPassengersPacket(this.trackedEntity));
        }
        if (this.trackedEntity instanceof ItemFrameEntity && this.updateCounter % 10 == 0) {
            ItemFrameEntity itemFrameEntity = (ItemFrameEntity)this.trackedEntity;
            ItemStack itemStack = itemFrameEntity.getDisplayedItem();
            if (itemStack.getItem() instanceof FilledMapItem) {
                object = FilledMapItem.getMapData(itemStack, this.world);
                for (ServerPlayerEntity object2 : this.world.getPlayers()) {
                    ((MapData)object).updateVisiblePlayers(object2, itemStack);
                    IPacket<?> iPacket = ((FilledMapItem)itemStack.getItem()).getUpdatePacket(itemStack, this.world, object2);
                    if (iPacket == null) continue;
                    object2.connection.sendPacket(iPacket);
                }
            }
            this.sendMetadata();
        }
        if (this.updateCounter % this.updateFrequency == 0 || this.trackedEntity.isAirBorne || this.trackedEntity.getDataManager().isDirty()) {
            int n;
            if (this.trackedEntity.isPassenger()) {
                boolean bl;
                n = MathHelper.floor(this.trackedEntity.rotationYaw * 256.0f / 360.0f);
                int n2 = MathHelper.floor(this.trackedEntity.rotationPitch * 256.0f / 360.0f);
                boolean bl2 = bl = Math.abs(n - this.encodedRotationYaw) >= 1 || Math.abs(n2 - this.encodedRotationPitch) >= 1;
                if (bl) {
                    this.packetConsumer.accept(new SEntityPacket.LookPacket(this.trackedEntity.getEntityId(), (byte)n, (byte)n2, this.trackedEntity.isOnGround()));
                    this.encodedRotationYaw = n;
                    this.encodedRotationPitch = n2;
                }
                this.updateEncodedPosition();
                this.sendMetadata();
                this.riding = true;
            } else {
                void var6_17;
                Vector3d vector3d;
                double d;
                boolean bl;
                ++this.ticksSinceAbsoluteTeleport;
                n = MathHelper.floor(this.trackedEntity.rotationYaw * 256.0f / 360.0f);
                int n3 = MathHelper.floor(this.trackedEntity.rotationPitch * 256.0f / 360.0f);
                object = this.trackedEntity.getPositionVec().subtract(SEntityPacket.func_218744_a(this.encodedPosX, this.encodedPosY, this.encodedPosZ));
                boolean bl3 = ((Vector3d)object).lengthSquared() >= 7.62939453125E-6;
                Object var6_12 = null;
                boolean bl4 = bl3 || this.updateCounter % 60 == 0;
                boolean bl5 = bl = Math.abs(n - this.encodedRotationYaw) >= 1 || Math.abs(n3 - this.encodedRotationPitch) >= 1;
                if (this.updateCounter > 0 || this.trackedEntity instanceof AbstractArrowEntity) {
                    boolean bl6;
                    long l = SEntityPacket.func_218743_a(((Vector3d)object).x);
                    long l2 = SEntityPacket.func_218743_a(((Vector3d)object).y);
                    long l3 = SEntityPacket.func_218743_a(((Vector3d)object).z);
                    boolean bl7 = bl6 = l < -32768L || l > 32767L || l2 < -32768L || l2 > 32767L || l3 < -32768L || l3 > 32767L;
                    if (!bl6 && this.ticksSinceAbsoluteTeleport <= 400 && !this.riding && this.onGround == this.trackedEntity.isOnGround()) {
                        if (!(bl4 && bl || this.trackedEntity instanceof AbstractArrowEntity)) {
                            if (bl4) {
                                SEntityPacket.RelativeMovePacket relativeMovePacket = new SEntityPacket.RelativeMovePacket(this.trackedEntity.getEntityId(), (short)l, (short)l2, (short)l3, this.trackedEntity.isOnGround());
                            } else if (bl) {
                                SEntityPacket.LookPacket lookPacket = new SEntityPacket.LookPacket(this.trackedEntity.getEntityId(), (byte)n, (byte)n3, this.trackedEntity.isOnGround());
                            }
                        } else {
                            SEntityPacket.MovePacket movePacket = new SEntityPacket.MovePacket(this.trackedEntity.getEntityId(), (short)l, (short)l2, (short)l3, (byte)n, (byte)n3, this.trackedEntity.isOnGround());
                        }
                    } else {
                        this.onGround = this.trackedEntity.isOnGround();
                        this.ticksSinceAbsoluteTeleport = 0;
                        SEntityTeleportPacket sEntityTeleportPacket = new SEntityTeleportPacket(this.trackedEntity);
                    }
                }
                if ((this.sendVelocityUpdates || this.trackedEntity.isAirBorne || this.trackedEntity instanceof LivingEntity && ((LivingEntity)this.trackedEntity).isElytraFlying()) && this.updateCounter > 0 && ((d = (vector3d = this.trackedEntity.getMotion()).squareDistanceTo(this.velocity)) > 1.0E-7 || d > 0.0 && vector3d.lengthSquared() == 0.0)) {
                    this.velocity = vector3d;
                    this.packetConsumer.accept(new SEntityVelocityPacket(this.trackedEntity.getEntityId(), this.velocity));
                }
                if (var6_17 != null) {
                    this.packetConsumer.accept((IPacket<?>)var6_17);
                }
                this.sendMetadata();
                if (bl4) {
                    this.updateEncodedPosition();
                }
                if (bl) {
                    this.encodedRotationYaw = n;
                    this.encodedRotationPitch = n3;
                }
                this.riding = false;
            }
            n = MathHelper.floor(this.trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
            if (Math.abs(n - this.encodedRotationYawHead) >= 1) {
                this.packetConsumer.accept(new SEntityHeadLookPacket(this.trackedEntity, (byte)n));
                this.encodedRotationYawHead = n;
            }
            this.trackedEntity.isAirBorne = false;
        }
        ++this.updateCounter;
        if (this.trackedEntity.velocityChanged) {
            this.sendPacket(new SEntityVelocityPacket(this.trackedEntity));
            this.trackedEntity.velocityChanged = false;
        }
    }

    public void untrack(ServerPlayerEntity serverPlayerEntity) {
        this.trackedEntity.removeTrackingPlayer(serverPlayerEntity);
        serverPlayerEntity.removeEntity(this.trackedEntity);
    }

    public void track(ServerPlayerEntity serverPlayerEntity) {
        this.sendSpawnPackets(serverPlayerEntity.connection::sendPacket);
        this.trackedEntity.addTrackingPlayer(serverPlayerEntity);
        serverPlayerEntity.addEntity(this.trackedEntity);
    }

    public void sendSpawnPackets(Consumer<IPacket<?>> consumer) {
        Collection<ModifiableAttributeInstance> collection;
        if (this.trackedEntity.removed) {
            LOGGER.warn("Fetching packet for removed entity " + this.trackedEntity);
        }
        IPacket<?> iPacket = this.trackedEntity.createSpawnPacket();
        this.encodedRotationYawHead = MathHelper.floor(this.trackedEntity.getRotationYawHead() * 256.0f / 360.0f);
        consumer.accept(iPacket);
        if (!this.trackedEntity.getDataManager().isEmpty()) {
            consumer.accept(new SEntityMetadataPacket(this.trackedEntity.getEntityId(), this.trackedEntity.getDataManager(), true));
        }
        boolean bl = this.sendVelocityUpdates;
        if (this.trackedEntity instanceof LivingEntity) {
            collection = ((LivingEntity)this.trackedEntity).getAttributeManager().getWatchedInstances();
            if (!collection.isEmpty()) {
                consumer.accept(new SEntityPropertiesPacket(this.trackedEntity.getEntityId(), collection));
            }
            if (((LivingEntity)this.trackedEntity).isElytraFlying()) {
                bl = true;
            }
        }
        this.velocity = this.trackedEntity.getMotion();
        if (bl && !(iPacket instanceof SSpawnMobPacket)) {
            consumer.accept(new SEntityVelocityPacket(this.trackedEntity.getEntityId(), this.velocity));
        }
        if (this.trackedEntity instanceof LivingEntity) {
            collection = Lists.newArrayList();
            for (EquipmentSlotType equipmentSlotType : EquipmentSlotType.values()) {
                ItemStack itemStack = ((LivingEntity)this.trackedEntity).getItemStackFromSlot(equipmentSlotType);
                if (itemStack.isEmpty()) continue;
                collection.add((ModifiableAttributeInstance)((Object)Pair.of(equipmentSlotType, itemStack.copy())));
            }
            if (!collection.isEmpty()) {
                consumer.accept(new SEntityEquipmentPacket(this.trackedEntity.getEntityId(), (List<Pair<EquipmentSlotType, ItemStack>>)collection));
            }
        }
        if (this.trackedEntity instanceof LivingEntity) {
            collection = (LivingEntity)this.trackedEntity;
            for (EffectInstance effectInstance : ((LivingEntity)((Object)collection)).getActivePotionEffects()) {
                consumer.accept(new SPlayEntityEffectPacket(this.trackedEntity.getEntityId(), effectInstance));
            }
        }
        if (!this.trackedEntity.getPassengers().isEmpty()) {
            consumer.accept(new SSetPassengersPacket(this.trackedEntity));
        }
        if (this.trackedEntity.isPassenger()) {
            consumer.accept(new SSetPassengersPacket(this.trackedEntity.getRidingEntity()));
        }
        if (this.trackedEntity instanceof MobEntity && ((MobEntity)((Object)(collection = (MobEntity)this.trackedEntity))).getLeashed()) {
            consumer.accept(new SMountEntityPacket((Entity)((Object)collection), ((MobEntity)((Object)collection)).getLeashHolder()));
        }
    }

    private void sendMetadata() {
        EntityDataManager entityDataManager = this.trackedEntity.getDataManager();
        if (entityDataManager.isDirty()) {
            this.sendPacket(new SEntityMetadataPacket(this.trackedEntity.getEntityId(), entityDataManager, false));
        }
        if (this.trackedEntity instanceof LivingEntity) {
            Set<ModifiableAttributeInstance> set = ((LivingEntity)this.trackedEntity).getAttributeManager().getInstances();
            if (!set.isEmpty()) {
                this.sendPacket(new SEntityPropertiesPacket(this.trackedEntity.getEntityId(), set));
            }
            set.clear();
        }
    }

    private void updateEncodedPosition() {
        this.encodedPosX = SEntityPacket.func_218743_a(this.trackedEntity.getPosX());
        this.encodedPosY = SEntityPacket.func_218743_a(this.trackedEntity.getPosY());
        this.encodedPosZ = SEntityPacket.func_218743_a(this.trackedEntity.getPosZ());
    }

    public Vector3d getDecodedPosition() {
        return SEntityPacket.func_218744_a(this.encodedPosX, this.encodedPosY, this.encodedPosZ);
    }

    private void sendPacket(IPacket<?> iPacket) {
        this.packetConsumer.accept(iPacket);
        if (this.trackedEntity instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity)this.trackedEntity).connection.sendPacket(iPacket);
        }
    }
}

