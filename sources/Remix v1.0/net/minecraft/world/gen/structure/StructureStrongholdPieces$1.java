package net.minecraft.world.gen.structure;

static final class StructureStrongholdPieces$1 extends PieceWeight {
    @Override
    public boolean canSpawnMoreStructuresOfType(final int p_75189_1_) {
        return super.canSpawnMoreStructuresOfType(p_75189_1_) && p_75189_1_ > 4;
    }
}