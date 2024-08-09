package im.expensive.scripts.lua.classes;

import net.minecraft.util.math.vector.Vector3d;

public class VectorClass {

    public Vector3d vector3d;

    public VectorClass(Vector3d vector3d) {
        this.vector3d = vector3d;
    }

    public double getX() {
        return vector3d.x;
    }

    public double getY() {
        return vector3d.y;
    }

    public double getZ() {
        return vector3d.z;
    }

}