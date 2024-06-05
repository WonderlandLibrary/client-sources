package zamorozka.ui;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class Location2 implements MCUtil {

    private double x, y, z;
    private boolean ground;

    public Location2(double x, double y, double z, boolean ground) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.ground = ground;
    }

    public Location2(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.ground = true;
    }

    public Location2(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.ground = true;
    }

    public static Location2 fromBlockPos(BlockPos blockPos) {
        return new Location2(blockPos.getX(), blockPos.getY(), blockPos.getZ());
    }

    public Location2 add(int x, int y, int z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Location2 add(double x, double y, double z) {
        this.x += x;
        this.y += y;
        this.z += z;
        return this;
    }

    public Location2 subtract(int x, int y, int z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return this;
    }

    public Location2 subtract(double x, double y, double z) {
        this.x -= x;
        this.y -= y;
        this.z -= z;

        return this;
    }

    public Block getBlock() {
        return mc.world.getBlockState(this.toBlockPos()).getBlock();
    }

    public boolean isOnGround() {
        return this.ground;
    }

    public Location2 setOnGround(boolean ground) {
        this.ground = ground;
        return this;
    }

    public double getX() {
        return x;
    }

    public Location2 setX(double x) {
        this.x = x;
        return this;
    }

    public double getY() {
        return y;
    }

    public Location2 setY(double y) {
        this.y = y;
        return this;
    }

    public double getZ() {
        return z;
    }

    public Location2 setZ(double z) {
        this.z = z;
        return this;
    }

    public BlockPos toBlockPos() {
        return new BlockPos(getX(), getY(), getZ());
    }

}
