/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.storage;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.data.ExternalJoinGameListener;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.entities.EntityType;

public abstract class EntityTracker
extends StoredObject
implements ExternalJoinGameListener {
    private final Map<Integer, EntityType> clientEntityTypes = new ConcurrentHashMap<Integer, EntityType>();
    private final EntityType playerType;
    private int clientEntityId;

    protected EntityTracker(UserConnection user, EntityType playerType) {
        super(user);
        this.playerType = playerType;
    }

    public void removeEntity(int entityId) {
        this.clientEntityTypes.remove(entityId);
    }

    public void addEntity(int entityId, EntityType type) {
        this.clientEntityTypes.put(entityId, type);
    }

    public boolean hasEntity(int entityId) {
        return this.clientEntityTypes.containsKey(entityId);
    }

    @Nullable
    public EntityType getEntity(int entityId) {
        return this.clientEntityTypes.get(entityId);
    }

    @Override
    public void onExternalJoinGame(int playerEntityId) {
        this.clientEntityId = playerEntityId;
        this.clientEntityTypes.put(playerEntityId, this.playerType);
    }

    public int getClientEntityId() {
        return this.clientEntityId;
    }

    public void setClientEntityId(int clientEntityId) {
        this.clientEntityId = clientEntityId;
    }
}

