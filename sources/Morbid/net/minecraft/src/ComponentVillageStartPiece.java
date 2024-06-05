package net.minecraft.src;

import java.util.*;

public class ComponentVillageStartPiece extends ComponentVillageWell
{
    public final WorldChunkManager worldChunkMngr;
    public final boolean inDesert;
    public final int terrainType;
    public StructureVillagePieceWeight structVillagePieceWeight;
    public ArrayList structureVillageWeightedPieceList;
    public ArrayList field_74932_i;
    public ArrayList field_74930_j;
    
    public ComponentVillageStartPiece(final WorldChunkManager par1WorldChunkManager, final int par2, final Random par3Random, final int par4, final int par5, final ArrayList par6ArrayList, final int par7) {
        super(null, 0, par3Random, par4, par5);
        this.field_74932_i = new ArrayList();
        this.field_74930_j = new ArrayList();
        this.worldChunkMngr = par1WorldChunkManager;
        this.structureVillageWeightedPieceList = par6ArrayList;
        this.terrainType = par7;
        final BiomeGenBase var8 = par1WorldChunkManager.getBiomeGenAt(par4, par5);
        this.inDesert = (var8 == BiomeGenBase.desert || var8 == BiomeGenBase.desertHills);
        this.startPiece = this;
    }
    
    public WorldChunkManager getWorldChunkManager() {
        return this.worldChunkMngr;
    }
}
