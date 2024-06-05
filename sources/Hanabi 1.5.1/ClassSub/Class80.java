package ClassSub;

import java.util.*;
import javax.vecmath.*;

class Class80
{
    private boolean aac;
    private float smooth;
    private Random random;
    
    
    public Class80(final boolean aac, final float smooth) {
        this.aac = aac;
        this.smooth = smooth;
        this.random = new Random();
    }
    
    public Class209 calculateAngle(final Vector3d vector3d, final Vector3d vector3d2) {
        final Class209 class209 = new Class209();
        vector3d.x += (this.aac ? this.randomFloat(-0.75f, 0.75f) : 0.0f) - vector3d2.x;
        vector3d.y += (this.aac ? this.randomFloat(-0.25f, 0.5f) : 0.0f) - vector3d2.y;
        vector3d.z += (this.aac ? this.randomFloat(-0.75f, 0.75f) : 0.0f) - vector3d2.z;
        final double hypot = Math.hypot(vector3d.x, vector3d.z);
        class209.setYaw((float)(Math.atan2(vector3d.z, vector3d.x) * 57.29577951308232) - 90.0f);
        class209.setPitch(-(float)(Math.atan2(vector3d.y, hypot) * 57.29577951308232));
        return class209.constrantAngle();
    }
    
    public Class209 smoothAngle(final Class209 class209, final Class209 class210) {
        final Class209 constrantAngle = new Class209(class210.getYaw() - class209.getYaw(), class210.getPitch() - class209.getPitch()).constrantAngle();
        constrantAngle.setYaw(class210.getYaw() - constrantAngle.getYaw() / 100.0f * this.smooth);
        constrantAngle.setPitch(class210.getPitch() - constrantAngle.getPitch() / 100.0f * this.smooth);
        return constrantAngle.constrantAngle();
    }
    
    public float randomFloat(final float n, final float n2) {
        return n + this.random.nextFloat() * (n2 - n);
    }
}
