package net.minecraft.src;

import java.util.*;

class StructureVillageStart extends StructureStart
{
    private boolean hasMoreThanTwoComponents;
    
    public StructureVillageStart(final World par1World, final Random par2Random, final int par3, final int par4, final int par5) {
        this.hasMoreThanTwoComponents = false;
        final ArrayList var6 = StructureVillagePieces.getStructureVillageWeightedPieceList(par2Random, par5);
        final ComponentVillageStartPiece var7 = new ComponentVillageStartPiece(par1World.getWorldChunkManager(), 0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2, var6, par5);
        this.components.add(var7);
        var7.buildComponent(var7, this.components, par2Random);
        final ArrayList var8 = var7.field_74930_j;
        final ArrayList var9 = var7.field_74932_i;
        while (!var8.isEmpty() || !var9.isEmpty()) {
            if (var8.isEmpty()) {
                final int var10 = par2Random.nextInt(var9.size());
                final StructureComponent var11 = var9.remove(var10);
                var11.buildComponent(var7, this.components, par2Random);
            }
            else {
                final int var10 = par2Random.nextInt(var8.size());
                final StructureComponent var11 = var8.remove(var10);
                var11.buildComponent(var7, this.components, par2Random);
            }
        }
        this.updateBoundingBox();
        int var10 = 0;
        for (final StructureComponent var13 : this.components) {
            if (!(var13 instanceof ComponentVillageRoadPiece)) {
                ++var10;
            }
        }
        this.hasMoreThanTwoComponents = (var10 > 2);
    }
    
    @Override
    public boolean isSizeableStructure() {
        return this.hasMoreThanTwoComponents;
    }
}
