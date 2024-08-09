package dev.luvbeeq.baritone.api.schematic.mask;

/**
 * @author Brady
 */
final class PreComputedMask extends AbstractMask implements StaticMask {

    private final boolean[][][] mask;

    public PreComputedMask(StaticMask mask) {
        super(mask.widthX(), mask.heightY(), mask.lengthZ());

        this.mask = new boolean[this.heightY()][this.lengthZ()][this.widthX()];
        for (int y = 0; y < this.heightY(); y++) {
            for (int z = 0; z < this.lengthZ(); z++) {
                for (int x = 0; x < this.widthX(); x++) {
                    this.mask[y][z][x] = mask.partOfMask(x, y, z);
                }
            }
        }
    }

    @Override
    public boolean partOfMask(int x, int y, int z) {
        return this.mask[y][z][x];
    }
}
