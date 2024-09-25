/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.api.entities.storage;

import java.util.function.Supplier;
import nl.matsv.viabackwards.ViaBackwards;
import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.entities.storage.EntityPositionStorage;
import nl.matsv.viabackwards.api.entities.storage.EntityTracker;
import nl.matsv.viabackwards.api.rewriters.EntityRewriterBase;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.type.Type;

public class EntityPositionHandler {
    public static final double RELATIVE_MOVE_FACTOR = 4096.0;
    private final EntityRewriterBase<?> entityRewriter;
    private final Class<? extends EntityPositionStorage> storageClass;
    private final Supplier<? extends EntityPositionStorage> storageSupplier;
    private boolean warnedForMissingEntity;

    public EntityPositionHandler(EntityRewriterBase<?> entityRewriter, Class<? extends EntityPositionStorage> storageClass, Supplier<? extends EntityPositionStorage> storageSupplier) {
        this.entityRewriter = entityRewriter;
        this.storageClass = storageClass;
        this.storageSupplier = storageSupplier;
    }

    public void cacheEntityPosition(PacketWrapper wrapper, boolean create, boolean relative) throws Exception {
        this.cacheEntityPosition(wrapper, wrapper.get(Type.DOUBLE, 0), wrapper.get(Type.DOUBLE, 1), wrapper.get(Type.DOUBLE, 2), create, relative);
    }

    public void cacheEntityPosition(PacketWrapper wrapper, double x, double y, double z, boolean create, boolean relative) throws Exception {
        EntityPositionStorage positionStorage;
        int entityId = wrapper.get(Type.VAR_INT, 0);
        EntityTracker.StoredEntity storedEntity = this.entityRewriter.getEntityTracker(wrapper.user()).getEntity(entityId);
        if (storedEntity == null) {
            if (Via.getManager().isDebug()) {
                ViaBackwards.getPlatform().getLogger().warning("Stored entity with id " + entityId + " missing at position: " + x + " - " + y + " - " + z + " in " + this.storageClass.getSimpleName());
                if (entityId == -1 && x == 0.0 && y == 0.0 && z == 0.0) {
                    ViaBackwards.getPlatform().getLogger().warning("DO NOT REPORT THIS TO VIA, THIS IS A PLUGIN ISSUE");
                } else if (!this.warnedForMissingEntity) {
                    this.warnedForMissingEntity = true;
                    ViaBackwards.getPlatform().getLogger().warning("This is very likely caused by a plugin sending a teleport packet for an entity outside of the player's range.");
                }
            }
            return;
        }
        EntityPositionStorage entityPositionStorage = positionStorage = create ? this.storageSupplier.get() : storedEntity.get(this.storageClass);
        if (positionStorage == null) {
            ViaBackwards.getPlatform().getLogger().warning("Stored entity with id " + entityId + " missing " + this.storageClass.getSimpleName());
            return;
        }
        positionStorage.setCoordinates(x, y, z, relative);
        storedEntity.put(positionStorage);
    }

    public EntityPositionStorage getStorage(UserConnection user, int entityId) {
        EntityPositionStorage entityStorage;
        EntityTracker.StoredEntity storedEntity = user.get(EntityTracker.class).get((BackwardsProtocol)this.entityRewriter.getProtocol()).getEntity(entityId);
        if (storedEntity == null || (entityStorage = storedEntity.get(EntityPositionStorage.class)) == null) {
            ViaBackwards.getPlatform().getLogger().warning("Untracked entity with id " + entityId + " in " + this.storageClass.getSimpleName());
            return null;
        }
        return entityStorage;
    }

    public static void writeFacingAngles(PacketWrapper wrapper, double x, double y, double z, double targetX, double targetY, double targetZ) {
        double dX = targetX - x;
        double dY = targetY - y;
        double dZ = targetZ - z;
        double r = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
        double yaw = -Math.atan2(dX, dZ) / Math.PI * 180.0;
        if (yaw < 0.0) {
            yaw = 360.0 + yaw;
        }
        double pitch = -Math.asin(dY / r) / Math.PI * 180.0;
        wrapper.write(Type.BYTE, (byte)(yaw * 256.0 / 360.0));
        wrapper.write(Type.BYTE, (byte)(pitch * 256.0 / 360.0));
    }

    public static void writeFacingDegrees(PacketWrapper wrapper, double x, double y, double z, double targetX, double targetY, double targetZ) {
        double dX = targetX - x;
        double dY = targetY - y;
        double dZ = targetZ - z;
        double r = Math.sqrt(dX * dX + dY * dY + dZ * dZ);
        double yaw = -Math.atan2(dX, dZ) / Math.PI * 180.0;
        if (yaw < 0.0) {
            yaw = 360.0 + yaw;
        }
        double pitch = -Math.asin(dY / r) / Math.PI * 180.0;
        wrapper.write(Type.FLOAT, Float.valueOf((float)yaw));
        wrapper.write(Type.FLOAT, Float.valueOf((float)pitch));
    }
}

