/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.world.storage;

import com.mojang.serialization.Dynamic;
import com.mojang.serialization.OptionalDynamic;
import net.minecraft.util.SharedConstants;

public class VersionData {
    private final int storageVersion;
    private final long lastPlayed;
    private final String name;
    private final int id;
    private final boolean snapshot;

    public VersionData(int n, long l, String string, int n2, boolean bl) {
        this.storageVersion = n;
        this.lastPlayed = l;
        this.name = string;
        this.id = n2;
        this.snapshot = bl;
    }

    public static VersionData getVersionData(Dynamic<?> dynamic) {
        int n = dynamic.get("version").asInt(0);
        long l = dynamic.get("LastPlayed").asLong(0L);
        OptionalDynamic<?> optionalDynamic = dynamic.get("Version");
        return optionalDynamic.result().isPresent() ? new VersionData(n, l, optionalDynamic.get("Name").asString(SharedConstants.getVersion().getName()), optionalDynamic.get("Id").asInt(SharedConstants.getVersion().getWorldVersion()), optionalDynamic.get("Snapshot").asBoolean(!SharedConstants.getVersion().isStable())) : new VersionData(n, l, "", 0, false);
    }

    public int getStorageVersionID() {
        return this.storageVersion;
    }

    public long getLastPlayed() {
        return this.lastPlayed;
    }

    public String getName() {
        return this.name;
    }

    public int getID() {
        return this.id;
    }

    public boolean isSnapshot() {
        return this.snapshot;
    }
}

