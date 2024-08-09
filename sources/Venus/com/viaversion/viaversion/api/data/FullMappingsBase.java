/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.FullMappings;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntMap;
import com.viaversion.viaversion.libs.fastutil.objects.Object2IntOpenHashMap;
import com.viaversion.viaversion.util.Key;
import java.util.List;
import org.checkerframework.checker.nullness.qual.Nullable;

/*
 * Duplicate member names - consider using --renamedupmembers true
 */
public class FullMappingsBase
implements FullMappings {
    private static final String[] EMPTY_ARRAY = new String[0];
    private final Object2IntMap<String> stringToId;
    private final Object2IntMap<String> mappedStringToId;
    private final String[] idToString;
    private final String[] mappedIdToString;
    private final Mappings mappings;

    public FullMappingsBase(List<String> list, List<String> list2, Mappings mappings) {
        this.mappings = mappings;
        this.stringToId = FullMappingsBase.toInverseMap(list);
        this.mappedStringToId = FullMappingsBase.toInverseMap(list2);
        this.idToString = list.toArray(EMPTY_ARRAY);
        this.mappedIdToString = list2.toArray(EMPTY_ARRAY);
    }

    private FullMappingsBase(Object2IntMap<String> object2IntMap, Object2IntMap<String> object2IntMap2, String[] stringArray, String[] stringArray2, Mappings mappings) {
        this.stringToId = object2IntMap;
        this.mappedStringToId = object2IntMap2;
        this.idToString = stringArray;
        this.mappedIdToString = stringArray2;
        this.mappings = mappings;
    }

    @Override
    public Mappings mappings() {
        return this.mappings;
    }

    @Override
    public int id(String string) {
        return this.stringToId.getInt(Key.stripMinecraftNamespace(string));
    }

    @Override
    public int mappedId(String string) {
        return this.mappedStringToId.getInt(Key.stripMinecraftNamespace(string));
    }

    @Override
    public String identifier(int n) {
        String string = this.idToString[n];
        return Key.namespaced(string);
    }

    @Override
    public String mappedIdentifier(int n) {
        String string = this.mappedIdToString[n];
        return Key.namespaced(string);
    }

    @Override
    public @Nullable String mappedIdentifier(String string) {
        int n = this.id(string);
        if (n == -1) {
            return null;
        }
        int n2 = this.mappings.getNewId(n);
        return n2 != -1 ? this.mappedIdentifier(n2) : null;
    }

    @Override
    public int getNewId(int n) {
        return this.mappings.getNewId(n);
    }

    @Override
    public void setNewId(int n, int n2) {
        this.mappings.setNewId(n, n2);
    }

    @Override
    public int size() {
        return this.mappings.size();
    }

    @Override
    public int mappedSize() {
        return this.mappings.mappedSize();
    }

    @Override
    public FullMappings inverse() {
        return new FullMappingsBase(this.mappedStringToId, this.stringToId, this.mappedIdToString, this.idToString, this.mappings.inverse());
    }

    private static Object2IntMap<String> toInverseMap(List<String> list) {
        Object2IntOpenHashMap<String> object2IntOpenHashMap = new Object2IntOpenHashMap<String>(list.size());
        object2IntOpenHashMap.defaultReturnValue(-1);
        for (int i = 0; i < list.size(); ++i) {
            object2IntOpenHashMap.put(list.get(i), i);
        }
        return object2IntOpenHashMap;
    }

    @Override
    public Mappings inverse() {
        return this.inverse();
    }
}

