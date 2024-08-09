/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viaversion.api.data;

import com.viaversion.viaversion.api.data.FullMappingsBase;
import com.viaversion.viaversion.api.data.Mappings;
import com.viaversion.viaversion.libs.fastutil.ints.IntArrayList;
import com.viaversion.viaversion.libs.fastutil.ints.IntList;
import java.util.List;

public class ParticleMappings
extends FullMappingsBase {
    private final IntList itemParticleIds = new IntArrayList(4);
    private final IntList blockParticleIds = new IntArrayList(4);

    public ParticleMappings(List<String> list, List<String> list2, Mappings mappings) {
        super(list, list2, mappings);
        this.addBlockParticle("block");
        this.addBlockParticle("falling_dust");
        this.addBlockParticle("block_marker");
        this.addItemParticle("item");
    }

    public boolean addItemParticle(String string) {
        int n = this.id(string);
        return n != -1 && this.itemParticleIds.add(n);
    }

    public boolean addBlockParticle(String string) {
        int n = this.id(string);
        return n != -1 && this.blockParticleIds.add(n);
    }

    public boolean isBlockParticle(int n) {
        return this.blockParticleIds.contains(n);
    }

    public boolean isItemParticle(int n) {
        return this.itemParticleIds.contains(n);
    }

    @Deprecated
    public int getBlockId() {
        return this.id("block");
    }

    @Deprecated
    public int getFallingDustId() {
        return this.id("falling_dust");
    }

    @Deprecated
    public int getItemId() {
        return this.id("item");
    }
}

