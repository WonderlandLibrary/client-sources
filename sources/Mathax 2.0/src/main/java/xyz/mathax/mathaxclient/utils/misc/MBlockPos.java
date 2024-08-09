package xyz.mathax.mathaxclient.utils.misc;

import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;

import static xyz.mathax.mathaxclient.MatHax.mc;

public class MBlockPos {
    private static final BlockPos.Mutable POS = new BlockPos.Mutable();

    public int x, y, z;

    public MBlockPos() {}

    public MBlockPos(Entity entity) {
        set(entity);
    }

    public MBlockPos set(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;

        return this;
    }

    public MBlockPos set(MBlockPos pos) {
        return set(pos.x, pos.y, pos.z);
    }

    public MBlockPos set(Entity entity) {
        return set(entity.getBlockX(), entity.getBlockY(), entity.getBlockZ());
    }

    public MBlockPos offset(HorizontalDirection dir, int amount) {
        x += dir.offsetX * amount;
        z += dir.offsetZ * amount;

        return this;
    }

    public MBlockPos offset(HorizontalDirection dir) {
        return offset(dir, 1);
    }

    public MBlockPos add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;

        return this;
    }

    public BlockPos getMcPos() {
        return POS.set(x, y, z);
    }

    public BlockState getState() {
        return mc.world.getBlockState(getMcPos());
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (object == null || getClass() != object.getClass()) {
            return false;
        }

        MBlockPos mBlockPos = (MBlockPos) object;
        if (x != mBlockPos.x) {
            return false;
        }

        if (y != mBlockPos.y) {
            return false;
        }

        return z == mBlockPos.z;
    }

    @Override
    public int hashCode() {
        int result = x;
        result = 31 * result + y;
        result = 31 * result + z;
        return result;
    }
}
