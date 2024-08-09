/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.client.audio;

import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.math.vector.Vector3f;
import org.lwjgl.openal.AL10;

public class Listener {
    private float gain = 1.0f;
    private Vector3d clientLocation = Vector3d.ZERO;

    public void setPosition(Vector3d vector3d) {
        this.clientLocation = vector3d;
        AL10.alListener3f(4100, (float)vector3d.x, (float)vector3d.y, (float)vector3d.z);
    }

    public Vector3d getClientLocation() {
        return this.clientLocation;
    }

    public void setOrientation(Vector3f vector3f, Vector3f vector3f2) {
        AL10.alListenerfv(4111, new float[]{vector3f.getX(), vector3f.getY(), vector3f.getZ(), vector3f2.getX(), vector3f2.getY(), vector3f2.getZ()});
    }

    public void setGain(float f) {
        AL10.alListenerf(4106, f);
        this.gain = f;
    }

    public float getGain() {
        return this.gain;
    }

    public void init() {
        this.setPosition(Vector3d.ZERO);
        this.setOrientation(Vector3f.ZN, Vector3f.YP);
    }
}

