package net.minecraft.src;

import java.util.*;

class StructureStrongholdStart extends StructureStart
{
    public StructureStrongholdStart(final World par1World, final Random par2Random, final int par3, final int par4) {
        StructureStrongholdPieces.prepareStructurePieces();
        final ComponentStrongholdStairs2 var5 = new ComponentStrongholdStairs2(0, par2Random, (par3 << 4) + 2, (par4 << 4) + 2);
        this.components.add(var5);
        var5.buildComponent(var5, this.components, par2Random);
        final ArrayList var6 = var5.field_75026_c;
        while (!var6.isEmpty()) {
            final int var7 = par2Random.nextInt(var6.size());
            final StructureComponent var8 = var6.remove(var7);
            var8.buildComponent(var5, this.components, par2Random);
        }
        this.updateBoundingBox();
        this.markAvailableHeight(par1World, par2Random, 10);
    }
}
