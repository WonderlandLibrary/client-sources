/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.protocols.protocol1_9to1_8.providers;

import com.viaversion.viaversion.api.connection.UserConnection;
import com.viaversion.viaversion.api.minecraft.Position;
import com.viaversion.viaversion.api.platform.providers.Provider;
import com.viaversion.viaversion.api.protocol.packet.PacketWrapper;
import com.viaversion.viaversion.api.type.Type;
import com.viaversion.viaversion.libs.opennbt.tag.builtin.CompoundTag;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.ClientboundPackets1_9;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;
import com.viaversion.viaversion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import java.util.Optional;

public class CommandBlockProvider
implements Provider {
    public void addOrUpdateBlock(UserConnection userConnection, Position position, CompoundTag compoundTag) throws Exception {
        this.checkPermission(userConnection);
        if (this.isEnabled()) {
            this.getStorage(userConnection).addOrUpdateBlock(position, compoundTag);
        }
    }

    public Optional<CompoundTag> get(UserConnection userConnection, Position position) throws Exception {
        this.checkPermission(userConnection);
        if (this.isEnabled()) {
            return this.getStorage(userConnection).getCommandBlock(position);
        }
        return Optional.empty();
    }

    public void unloadChunk(UserConnection userConnection, int n, int n2) throws Exception {
        this.checkPermission(userConnection);
        if (this.isEnabled()) {
            this.getStorage(userConnection).unloadChunk(n, n2);
        }
    }

    private CommandBlockStorage getStorage(UserConnection userConnection) {
        return userConnection.get(CommandBlockStorage.class);
    }

    public void sendPermission(UserConnection userConnection) throws Exception {
        if (!this.isEnabled()) {
            return;
        }
        PacketWrapper packetWrapper = PacketWrapper.create(ClientboundPackets1_9.ENTITY_STATUS, null, userConnection);
        EntityTracker1_9 entityTracker1_9 = (EntityTracker1_9)userConnection.getEntityTracker(Protocol1_9To1_8.class);
        packetWrapper.write(Type.INT, entityTracker1_9.getProvidedEntityId());
        packetWrapper.write(Type.BYTE, (byte)26);
        packetWrapper.scheduleSend(Protocol1_9To1_8.class);
        userConnection.get(CommandBlockStorage.class).setPermissions(false);
    }

    private void checkPermission(UserConnection userConnection) throws Exception {
        if (!this.isEnabled()) {
            return;
        }
        CommandBlockStorage commandBlockStorage = this.getStorage(userConnection);
        if (!commandBlockStorage.isPermissions()) {
            this.sendPermission(userConnection);
        }
    }

    public boolean isEnabled() {
        return false;
    }

    public void unloadChunks(UserConnection userConnection) {
        if (this.isEnabled()) {
            this.getStorage(userConnection).unloadChunks();
        }
    }
}

