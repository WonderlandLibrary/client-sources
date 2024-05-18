package net.minecraft.src;

final class StructureStrongholdPieceWeight2 extends StructureStrongholdPieceWeight
{
    StructureStrongholdPieceWeight2(final Class par1Class, final int par2, final int par3) {
        super(par1Class, par2, par3);
    }
    
    @Override
    public boolean canSpawnMoreStructuresOfType(final int par1) {
        return super.canSpawnMoreStructuresOfType(par1) && par1 > 4;
    }
}
