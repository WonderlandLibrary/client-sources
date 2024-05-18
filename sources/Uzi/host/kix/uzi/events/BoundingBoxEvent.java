package host.kix.uzi.events;

import com.darkmagician6.eventapi.events.Event;
import host.kix.uzi.utilities.minecraft.Location;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;

/**
 * Created by myche on 3/6/2017.
 */
public class BoundingBoxEvent implements Event{

    private Entity entity;
    private Block block;
    private BlockPos blockPos;
    private Location location;
    private AxisAlignedBB boundingBox;

    public BoundingBoxEvent(Entity entity, Location location, final Block block, final BlockPos pos, final AxisAlignedBB boundingBox) {
        this.entity = entity;
        this.location = location;
        this.block = block;
        this.blockPos = pos;
        this.boundingBox = boundingBox;
    }

    public Block getBlock() {
        return this.block;
    }

    public Location getLocation() {
        return location;
    }

    public BlockPos getBlockPos() {
        return this.blockPos;
    }

    public Entity getEntity() {
        return entity;
    }

    public AxisAlignedBB getBoundingBox() {
        return this.boundingBox;
    }

    public void setBlock(final Block block) {
        this.block = block;
    }

    public void setBlockPos(final BlockPos blockPos) {
        this.blockPos = blockPos;
    }

    public void setBoundingBox(final AxisAlignedBB boundingBox) {
        this.boundingBox = boundingBox;
    }

}
