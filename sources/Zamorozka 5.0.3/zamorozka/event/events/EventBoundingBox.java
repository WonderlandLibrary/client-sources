package zamorozka.event.events;

import net.minecraft.block.Block;
import net.minecraft.util.math.AxisAlignedBB;
import zamorozka.event.Event;

public class EventBoundingBox extends Event {
	
    private final Block block;
    public AxisAlignedBB boundingBox;
    private final double x;
    private final double y;
    private final double z;

    public EventBoundingBox(AxisAlignedBB bb2, Block block, double x2, double y2, double z2) {
        this.block = block;
        this.boundingBox = bb2;
        this.x = x2;
        this.y = y2;
        this.z = z2;
    }

    public double getX() {
        return this.x;
    }

    public double getY() {
        return this.y;
    }

    public double getZ() {
        return this.z;
    }

    public Block getBlock() {
        return this.block;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public void setBoundingBox(AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }
}