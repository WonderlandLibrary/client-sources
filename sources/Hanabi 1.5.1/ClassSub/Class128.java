package ClassSub;

import java.util.*;
import javax.vecmath.*;

class Class128
{
    private boolean aac;
    private float smooth;
    private Random random;
    
    
    public Class128(final boolean aac, final float smooth) {
        this.aac = aac;
        this.smooth = smooth;
        this.random = new Random();
    }
    
    public Class94 calculateAngle(final Vector3d vector3d, final Vector3d vector3d2) {
        final Class94 class94 = new Class94();
        vector3d.x += (this.aac ? this.randomFloat(-0.75f, 0.75f) : 0.0f) - vector3d2.x;
        vector3d.y += (this.aac ? this.randomFloat(-0.25f, 0.5f) : 0.0f) - vector3d2.y;
        vector3d.z += (this.aac ? this.randomFloat(-0.75f, 0.75f) : 0.0f) - vector3d2.z;
        final double hypot = Math.hypot(vector3d.x, vector3d.z);
        class94.setYaw((float)(Math.atan2(vector3d.z, vector3d.x) * 57.29577951308232) - 90.0f);
        class94.setPitch(-(float)(Math.atan2(vector3d.y, hypot) * 57.29577951308232));
        return class94.constrantAngle();
    }
    
    public Class94 smoothAngle(final Class94 class94, final Class94 class95) {
        final Class94 constrantAngle = new Class94(class95.getYaw() - class94.getYaw(), class95.getPitch() - class94.getPitch()).constrantAngle();
        constrantAngle.setYaw(class95.getYaw() - constrantAngle.getYaw() / 100.0f * this.smooth);
        constrantAngle.setPitch(class95.getPitch() - constrantAngle.getPitch() / 100.0f * this.smooth);
        return constrantAngle.constrantAngle();
    }
    
    public float randomFloat(final float n, final float n2) {
        return n + this.random.nextFloat() * (n2 - n);
    }
}
