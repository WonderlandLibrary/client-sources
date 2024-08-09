package dev.luvbeeq.baritone.api.schematic;

import dev.luvbeeq.baritone.api.utils.BlockOptionalMetaLookup;
import net.minecraft.block.BlockState;

public class ReplaceSchematic extends MaskSchematic {

    private final BlockOptionalMetaLookup filter;
    private final Boolean[][][] cache;

    public ReplaceSchematic(ISchematic schematic, BlockOptionalMetaLookup filter) {
        super(schematic);
        this.filter = filter;
        this.cache = new Boolean[widthX()][heightY()][lengthZ()];
    }

    @Override
    public void reset() {
        // it's final, can't use this.cache = new Boolean[widthX()][heightY()][lengthZ()]
        for (int x = 0; x < cache.length; x++) {
            for (int y = 0; y < cache[0].length; y++) {
                for (int z = 0; z < cache[0][0].length; z++) {
                    cache[x][y][z] = null;
                }
            }
        }
    }

    @Override
    protected boolean partOfMask(int x, int y, int z, BlockState currentState) {
        if (cache[x][y][z] == null) {
            cache[x][y][z] = filter.has(currentState);
        }
        return cache[x][y][z];
    }
}
