/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package nl.matsv.viabackwards.api.entities.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import nl.matsv.viabackwards.api.BackwardsProtocol;
import nl.matsv.viabackwards.api.entities.storage.EntityStorage;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.EntityType;

public class EntityTracker
extends StoredObject {
    private final Map<BackwardsProtocol, ProtocolEntityTracker> trackers = new ConcurrentHashMap<BackwardsProtocol, ProtocolEntityTracker>();

    public EntityTracker(UserConnection user) {
        super(user);
    }

    public void initProtocol(BackwardsProtocol protocol) {
        this.trackers.put(protocol, new ProtocolEntityTracker());
    }

    @Nullable
    public ProtocolEntityTracker get(BackwardsProtocol protocol) {
        return this.trackers.get(protocol);
    }

    public static final class StoredEntity {
        private final int entityId;
        private final EntityType type;
        private final Map<Class<? extends EntityStorage>, EntityStorage> storedObjects = new ConcurrentHashMap<Class<? extends EntityStorage>, EntityStorage>();

        private StoredEntity(int entityId, EntityType type) {
            this.entityId = entityId;
            this.type = type;
        }

        @Nullable
        public <T extends EntityStorage> T get(Class<T> objectClass) {
            return (T)this.storedObjects.get(objectClass);
        }

        public boolean has(Class<? extends EntityStorage> objectClass) {
            return this.storedObjects.containsKey(objectClass);
        }

        public void put(EntityStorage object) {
            this.storedObjects.put(object.getClass(), object);
        }

        public int getEntityId() {
            return this.entityId;
        }

        public EntityType getType() {
            return this.type;
        }

        public String toString() {
            return "StoredEntity{entityId=" + this.entityId + ", type=" + this.type + ", storedObjects=" + this.storedObjects + '}';
        }
    }

    public static class ProtocolEntityTracker {
        private final Map<Integer, StoredEntity> entityMap = new ConcurrentHashMap<Integer, StoredEntity>();

        public void trackEntityType(int id, EntityType type) {
            this.entityMap.putIfAbsent(id, new StoredEntity(id, type));
        }

        public void removeEntity(int id) {
            this.entityMap.remove(id);
        }

        @Nullable
        public EntityType getEntityType(int id) {
            StoredEntity storedEntity = this.entityMap.get(id);
            return storedEntity != null ? storedEntity.getType() : null;
        }

        @Nullable
        public StoredEntity getEntity(int id) {
            return this.entityMap.get(id);
        }
    }
}

