/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.model.BakedQuad;

public class ListQuadsOverlay {
    private List<BakedQuad> listQuads = new ArrayList<BakedQuad>();
    private List<BlockState> listBlockStates = new ArrayList<BlockState>();
    private List<BakedQuad> listQuadsSingle = Arrays.asList(new BakedQuad[0]);

    public void addQuad(BakedQuad bakedQuad, BlockState blockState) {
        if (bakedQuad != null) {
            this.listQuads.add(bakedQuad);
            this.listBlockStates.add(blockState);
        }
    }

    public int size() {
        return this.listQuads.size();
    }

    public BakedQuad getQuad(int n) {
        return this.listQuads.get(n);
    }

    public BlockState getBlockState(int n) {
        return n >= 0 && n < this.listBlockStates.size() ? this.listBlockStates.get(n) : Blocks.AIR.getDefaultState();
    }

    public List<BakedQuad> getListQuadsSingle(BakedQuad bakedQuad) {
        this.listQuadsSingle.set(0, bakedQuad);
        return this.listQuadsSingle;
    }

    public void clear() {
        this.listQuads.clear();
        this.listBlockStates.clear();
    }
}

