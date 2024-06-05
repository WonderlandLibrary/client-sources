package net.minecraft.world.gen.structure;

import com.google.common.collect.*;
import java.util.*;
import net.minecraft.util.*;
import net.minecraft.world.biome.*;

public static class Start extends Well
{
    public WorldChunkManager worldChunkMngr;
    public boolean inDesert;
    public int terrainType;
    public PieceWeight structVillagePieceWeight;
    public List structureVillageWeightedPieceList;
    public List field_74932_i;
    public List field_74930_j;
    
    public Start() {
        this.field_74932_i = Lists.newArrayList();
        this.field_74930_j = Lists.newArrayList();
    }
    
    public Start(final WorldChunkManager p_i2104_1_, final int p_i2104_2_, final Random p_i2104_3_, final int p_i2104_4_, final int p_i2104_5_, final List p_i2104_6_, final int p_i2104_7_) {
        super(null, 0, p_i2104_3_, p_i2104_4_, p_i2104_5_);
        this.field_74932_i = Lists.newArrayList();
        this.field_74930_j = Lists.newArrayList();
        this.worldChunkMngr = p_i2104_1_;
        this.structureVillageWeightedPieceList = p_i2104_6_;
        this.terrainType = p_i2104_7_;
        final BiomeGenBase var8 = p_i2104_1_.func_180300_a(new BlockPos(p_i2104_4_, 0, p_i2104_5_), BiomeGenBase.field_180279_ad);
        this.func_175846_a(this.inDesert = (var8 == BiomeGenBase.desert || var8 == BiomeGenBase.desertHills));
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.worldChunkMngr;
    }
}
