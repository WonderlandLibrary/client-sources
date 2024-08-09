/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.api.entities.storage;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.entities.storage.EntityPositionStorage;
import com.viaversion.viabackwards.api.rewriters.EntityRewriterBase;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.data.entity.StoredEntityData;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import java.util.function.Supplier;

public class EntityPositionHandler {
    public static final double RELATIVE_MOVE_FACTOR = 4096.0;
    private final EntityRewriterBase<?, ?> entityRewriter;
    private final Class<? extends EntityPositionStorage> storageClass;
    private final Supplier<? extends EntityPositionStorage> storageSupplier;
    private boolean warnedForMissingEntity;

    public EntityPositionHandler(EntityRewriterBase<?, ?> entityRewriterBase, Class<? extends EntityPositionStorage> clazz, Supplier<? extends EntityPositionStorage> supplier) {
        this.entityRewriter = entityRewriterBase;
        this.storageClass = clazz;
        this.storageSupplier = supplier;
    }

    public void cacheEntityPosition(PacketWrapper packetWrapper, boolean bl, boolean bl2) throws Exception {
        this.cacheEntityPosition(packetWrapper, packetWrapper.get(Type.DOUBLE, 0), packetWrapper.get(Type.DOUBLE, 1), packetWrapper.get(Type.DOUBLE, 2), bl, bl2);
    }

    public void cacheEntityPosition(PacketWrapper packetWrapper, double d, double d2, double d3, boolean bl, boolean bl2) throws Exception {
        EntityPositionStorage entityPositionStorage;
        int n = packetWrapper.get(Type.VAR_INT, 0);
        StoredEntityData storedEntityData = this.entityRewriter.tracker(packetWrapper.user()).entityData(n);
        if (storedEntityData == null) {
            if (Via.getManager().isDebug()) {
                ViaBackwards.getPlatform().getLogger().warning("Stored entity with id " + n + " missing at position: " + d + " - " + d2 + " - " + d3 + " in " + this.storageClass.getSimpleName());
                if (n == -1 && d == 0.0 && d2 == 0.0 && d3 == 0.0) {
                    ViaBackwards.getPlatform().getLogger().warning("DO NOT REPORT THIS TO VIA, THIS IS A PLUGIN ISSUE");
                } else if (!this.warnedForMissingEntity) {
                    this.warnedForMissingEntity = true;
                    ViaBackwards.getPlatform().getLogger().warning("This is very likely caused by a plugin sending a teleport packet for an entity outside of the player's range.");
                }
            }
            return;
        }
        if (bl) {
            entityPositionStorage = this.storageSupplier.get();
            storedEntityData.put(entityPositionStorage);
        } else {
            entityPositionStorage = storedEntityData.get(this.storageClass);
            if (entityPositionStorage == null) {
                ViaBackwards.getPlatform().getLogger().warning("Stored entity with id " + n + " missing " + this.storageClass.getSimpleName());
                return;
            }
        }
        entityPositionStorage.setCoordinates(d, d2, d3, bl2);
    }

    public EntityPositionStorage getStorage(UserConnection userConnection, int n) {
        EntityPositionStorage entityPositionStorage;
        StoredEntityData storedEntityData = this.entityRewriter.tracker(userConnection).entityData(n);
        if (storedEntityData == null || (entityPositionStorage = storedEntityData.get(EntityPositionStorage.class)) == null) {
            ViaBackwards.getPlatform().getLogger().warning("Untracked entity with id " + n + " in " + this.storageClass.getSimpleName());
            return null;
        }
        return entityPositionStorage;
    }

    public static void writeFacingAngles(PacketWrapper packetWrapper, double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d4 - d;
        double d8 = d5 - d2;
        double d9 = d6 - d3;
        double d10 = Math.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
        double d11 = -Math.atan2(d7, d9) / Math.PI * 180.0;
        if (d11 < 0.0) {
            d11 = 360.0 + d11;
        }
        double d12 = -Math.asin(d8 / d10) / Math.PI * 180.0;
        packetWrapper.write(Type.BYTE, (byte)(d11 * 256.0 / 360.0));
        packetWrapper.write(Type.BYTE, (byte)(d12 * 256.0 / 360.0));
    }

    public static void writeFacingDegrees(PacketWrapper packetWrapper, double d, double d2, double d3, double d4, double d5, double d6) {
        double d7 = d4 - d;
        double d8 = d5 - d2;
        double d9 = d6 - d3;
        double d10 = Math.sqrt(d7 * d7 + d8 * d8 + d9 * d9);
        double d11 = -Math.atan2(d7, d9) / Math.PI * 180.0;
        if (d11 < 0.0) {
            d11 = 360.0 + d11;
        }
        double d12 = -Math.asin(d8 / d10) / Math.PI * 180.0;
        packetWrapper.write(Type.FLOAT, Float.valueOf((float)d11));
        packetWrapper.write(Type.FLOAT, Float.valueOf((float)d12));
    }
}

