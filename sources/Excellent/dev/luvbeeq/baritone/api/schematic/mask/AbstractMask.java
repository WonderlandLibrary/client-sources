package dev.luvbeeq.baritone.api.schematic.mask;

/**
 * @author Brady
 */
public abstract class AbstractMask implements Mask {

    private final int widthX;
    private final int heightY;
    private final int lengthZ;

    public AbstractMask(int widthX, int heightY, int lengthZ) {
        this.widthX = widthX;
        this.heightY = heightY;
        this.lengthZ = lengthZ;
    }

    @Override
    public int widthX() {
        return this.widthX;
    }

    @Override
    public int heightY() {
        return this.heightY;
    }

    @Override
    public int lengthZ() {
        return this.lengthZ;
    }
}
