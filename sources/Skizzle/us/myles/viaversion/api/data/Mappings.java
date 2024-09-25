/*
 * Decompiled with CFR 0.150.
 * 
 * Could not load the following classes:
 *  org.jetbrains.annotations.Nullable
 */
package us.myles.ViaVersion.api.data;

import java.util.Arrays;
import org.jetbrains.annotations.Nullable;
import us.myles.ViaVersion.api.data.MappingDataLoader;
import us.myles.viaversion.libs.gson.JsonArray;
import us.myles.viaversion.libs.gson.JsonObject;

public class Mappings {
    protected final short[] oldToNew;

    public Mappings(short[] oldToNew) {
        this.oldToNew = oldToNew;
    }

    public Mappings(int size, JsonObject oldMapping, JsonObject newMapping, @Nullable JsonObject diffMapping) {
        this.oldToNew = new short[size];
        Arrays.fill(this.oldToNew, (short)-1);
        MappingDataLoader.mapIdentifiers(this.oldToNew, oldMapping, newMapping, diffMapping);
    }

    public Mappings(JsonObject oldMapping, JsonObject newMapping, @Nullable JsonObject diffMapping) {
        this(oldMapping.entrySet().size(), oldMapping, newMapping, diffMapping);
    }

    public Mappings(int size, JsonObject oldMapping, JsonObject newMapping) {
        this.oldToNew = new short[size];
        Arrays.fill(this.oldToNew, (short)-1);
        MappingDataLoader.mapIdentifiers(this.oldToNew, oldMapping, newMapping);
    }

    public Mappings(JsonObject oldMapping, JsonObject newMapping) {
        this(oldMapping.entrySet().size(), oldMapping, newMapping);
    }

    public Mappings(int size, JsonArray oldMapping, JsonArray newMapping, JsonObject diffMapping, boolean warnOnMissing) {
        this.oldToNew = new short[size];
        Arrays.fill(this.oldToNew, (short)-1);
        MappingDataLoader.mapIdentifiers(this.oldToNew, oldMapping, newMapping, diffMapping, warnOnMissing);
    }

    public Mappings(int size, JsonArray oldMapping, JsonArray newMapping, boolean warnOnMissing) {
        this(size, oldMapping, newMapping, null, warnOnMissing);
    }

    public Mappings(JsonArray oldMapping, JsonArray newMapping, boolean warnOnMissing) {
        this(oldMapping.size(), oldMapping, newMapping, warnOnMissing);
    }

    public Mappings(int size, JsonArray oldMapping, JsonArray newMapping) {
        this(size, oldMapping, newMapping, true);
    }

    public Mappings(JsonArray oldMapping, JsonArray newMapping, JsonObject diffMapping) {
        this(oldMapping.size(), oldMapping, newMapping, diffMapping, true);
    }

    public Mappings(JsonArray oldMapping, JsonArray newMapping) {
        this(oldMapping.size(), oldMapping, newMapping, true);
    }

    public int getNewId(int old) {
        return old >= 0 && old < this.oldToNew.length ? this.oldToNew[old] : -1;
    }

    public short[] getOldToNew() {
        return this.oldToNew;
    }
}

