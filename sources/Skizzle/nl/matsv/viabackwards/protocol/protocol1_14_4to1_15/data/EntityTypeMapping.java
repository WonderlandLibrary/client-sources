/*
 * Decompiled with CFR 0.150.
 */
package nl.matsv.viabackwards.protocol.protocol1_14_4to1_15.data;

import us.myles.ViaVersion.api.entities.Entity1_14Types;

public class EntityTypeMapping {
    public static int getOldEntityId(int entityId) {
        if (entityId == 4) {
            return Entity1_14Types.EntityType.PUFFERFISH.getId();
        }
        return entityId >= 5 ? entityId - 1 : entityId;
    }
}

