package mathax.client.utils.misc;

import net.minecraft.nbt.NbtCompound;

import java.util.Objects;

public class Vec2 implements ISerializable<Vec2> {
    public static final Vec2 ZERO = new Vec2(0, 0);

    public double x, y;

    public Vec2(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Vec2() {
        this(0, 0);
    }

    public Vec2(Vec2 other) {
        this(other.x, other.y);
    }

    public void set(double x, double y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public NbtCompound toTag() {
        NbtCompound tag = new NbtCompound();

        tag.putDouble("x", x);
        tag.putDouble("y", y);

        return tag;
    }

    @Override
    public Vec2 fromTag(NbtCompound tag) {
        x = tag.getDouble("x");
        y = tag.getDouble("y");

        return this;
    }

    @Override
    public String toString() {
        return x + ", " + y;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Vec2 vec2 = (Vec2) o;
        return Double.compare(vec2.x, x) == 0 &&
                Double.compare(vec2.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
