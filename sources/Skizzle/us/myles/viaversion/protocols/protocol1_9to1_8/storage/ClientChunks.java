/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Sets
 */
package us.myles.ViaVersion.protocols.protocol1_9to1_8.storage;

import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import us.myles.ViaVersion.api.Via;
import us.myles.ViaVersion.api.data.StoredObject;
import us.myles.ViaVersion.api.data.UserConnection;
import us.myles.ViaVersion.protocols.protocol1_9to1_8.providers.BulkChunkTranslatorProvider;

public class ClientChunks
extends StoredObject {
    private final Set<Long> loadedChunks = Sets.newConcurrentHashSet();
    private final Set<Long> bulkChunks = Sets.newConcurrentHashSet();

    public ClientChunks(UserConnection user) {
        super(user);
    }

    public static long toLong(int msw, int lsw) {
        return ((long)msw << 32) + (long)lsw - Integer.MIN_VALUE;
    }

    public List<Object> transformMapChunkBulk(Object packet) throws Exception {
        return Via.getManager().getProviders().get(BulkChunkTranslatorProvider.class).transformMapChunkBulk(packet, this);
    }

    public Set<Long> getLoadedChunks() {
        return this.loadedChunks;
    }

    public Set<Long> getBulkChunks() {
        return this.bulkChunks;
    }
}

