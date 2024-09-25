/*
 * Decompiled with CFR 0.150.
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8.providers;

import java.util.Optional;
import us.myles.ViaVersion.api.PacketWrapper;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.api.minecraft.Position;
import us.myles.ViaVersion.api.platform.providers.Provider;
import us.myles.ViaVersion.api.type.Type;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.Protocol1_9To1_8;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.CommandBlockStorage;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.storage.EntityTracker1_9;
import us.myles.viaversion.libs.opennbt.tag.builtin.CompoundTag;

public class CommandBlockProvider
implements Provider {
    public void addOrUpdateBlock(UserConnection user, Position position, CompoundTag tag) throws Exception {
        this.checkPermission(user);
        if (this.isEnabled()) {
            this.getStorage(user).addOrUpdateBlock(position, tag);
        }
    }

    public Optional<CompoundTag> get(UserConnection user, Position position) throws Exception {
        this.checkPermission(user);
        if (this.isEnabled()) {
            return this.getStorage(user).getCommandBlock(position);
        }
        return Optional.empty();
    }

    public void unloadChunk(UserConnection user, int x, int z) throws Exception {
        this.checkPermission(user);
        if (this.isEnabled()) {
            this.getStorage(user).unloadChunk(x, z);
        }
    }

    private CommandBlockStorage getStorage(UserConnection connection) {
        return connection.get(CommandBlockStorage.class);
    }

    public void sendPermission(UserConnection user) throws Exception {
        if (!this.isEnabled()) {
            return;
        }
        PacketWrapper wrapper = new PacketWrapper(27, null, user);
        wrapper.write(Type.INT, user.get(EntityTracker1_9.class).getProvidedEntityId());
        wrapper.write(Type.BYTE, (byte)26);
        wrapper.send(Protocol1_9To1_8.class);
        user.get(CommandBlockStorage.class).setPermissions(true);
    }

    private void checkPermission(UserConnection user) throws Exception {
        if (!this.isEnabled()) {
            return;
        }
        CommandBlockStorage storage = this.getStorage(user);
        if (!storage.isPermissions()) {
            this.sendPermission(user);
        }
    }

    public boolean isEnabled() {
        return true;
    }

    public void unloadChunks(UserConnection userConnection) {
        if (this.isEnabled()) {
            this.getStorage(userConnection).unloadChunks();
        }
    }
}

