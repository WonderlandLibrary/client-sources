/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.ArrayList;
import java.util.List;
import net.optifine.DynamicLight;

public class DynamicLightsMap {
    private Int2ObjectMap<DynamicLight> map = new Int2ObjectOpenHashMap<DynamicLight>();
    private List<DynamicLight> list = new ArrayList<DynamicLight>();
    private boolean dirty = false;

    public DynamicLight put(int n, DynamicLight dynamicLight) {
        DynamicLight dynamicLight2 = this.map.put(n, dynamicLight);
        this.setDirty();
        return dynamicLight2;
    }

    public DynamicLight get(int n) {
        return (DynamicLight)this.map.get(n);
    }

    public int size() {
        return this.map.size();
    }

    public DynamicLight remove(int n) {
        DynamicLight dynamicLight = (DynamicLight)this.map.remove(n);
        if (dynamicLight != null) {
            this.setDirty();
        }
        return dynamicLight;
    }

    public void clear() {
        this.map.clear();
        this.list.clear();
        this.setDirty();
    }

    private void setDirty() {
        this.dirty = true;
    }

    public List<DynamicLight> valueList() {
        if (this.dirty) {
            this.list.clear();
            this.list.addAll(this.map.values());
            this.dirty = false;
        }
        return this.list;
    }
}

