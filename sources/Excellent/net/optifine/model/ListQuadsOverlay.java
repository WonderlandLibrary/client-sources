package net.optifine.model;

import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.renderer.model.BakedQuad;

import java.util.ArrayList;
import java.util.List;

public class ListQuadsOverlay {
    private final List<BakedQuad> listQuads = new ArrayList<>();
    private final List<BlockState> listBlockStates = new ArrayList<>();

    public void addQuad(BakedQuad quad, BlockState blockState) {
        if (quad != null) {
            this.listQuads.add(quad);
            this.listBlockStates.add(blockState);
        }
    }

    public int size() {
        return this.listQuads.size();
    }

    public BakedQuad getQuad(int index) {
        return this.listQuads.get(index);
    }

    public BlockState getBlockState(int index) {
        return index >= 0 && index < this.listBlockStates.size() ? this.listBlockStates.get(index) : Blocks.AIR.getDefaultState();
    }

    public List<BakedQuad> getListQuadsSingle(BakedQuad quad) {
        List<BakedQuad> listQuadsSingle = new ArrayList<>();
        listQuadsSingle.add(quad);
        return listQuadsSingle;
    }


    public void clear() {
        this.listQuads.clear();
        this.listBlockStates.clear();
    }
}
