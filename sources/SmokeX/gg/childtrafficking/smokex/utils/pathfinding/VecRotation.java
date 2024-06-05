// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.pathfinding;

public final class VecRotation
{
    Vec3 vec3;
    Rotation rotation;
    
    public VecRotation(final Vec3 Vec3, final Rotation Rotation) {
        this.vec3 = Vec3;
        this.rotation = Rotation;
    }
    
    public Vec3 getVec3() {
        return this.vec3;
    }
    
    public Rotation getRotation() {
        return this.rotation;
    }
}
