package com.sun.jna.z.a.f;

import java.nio.*;
import org.lwjgl.*;
import org.lwjgl.opengl.*;

public class j
{
    private FloatBuffer a;
    private int b;
    private int c;
    private int d;
    private int e;
    private float[] f;
    
    public j() {
        this.c = 2;
        this.d = 7;
        this.e = -1;
    }
    
    public void a(final int a, final int a, final b a) {
        this.b = a;
        final int b = com.sun.jna.z.a.f.f.b ? 1 : 0;
        this.d = a;
        final int a2 = b;
        if (a2 == 0) {
            if (a == com.sun.jna.z.a.f.b.THREE_DIMENSIONAL) {
                this.c = 3;
            }
            this.f = new float[a * this.c];
            this.a = BufferUtils.createFloatBuffer(a * this.c);
        }
    }
    
    public void a(final int a, final int a) {
        this.a(a, a, com.sun.jna.z.a.f.b.TWO_DIMENSIONAL);
    }
    
    public void a(final int a) {
        this.a(a, 7, com.sun.jna.z.a.f.b.TWO_DIMENSIONAL);
    }
    
    public void a() {
        GL11.glEnableClientState(32884);
        GL11.glVertexPointer(this.c, 0, this.a);
        GL11.glDrawArrays(this.d, 0, this.b);
        GL11.glDisableClientState(32884);
        this.e = 0;
    }
    
    public void b() {
        this.e = 0;
        this.f = new float[this.b * this.c];
        this.a.clear();
    }
    
    public void c() {
        this.a.put(this.f);
        this.a.flip();
        this.a();
    }
    
    public void a(final float a, final float a) {
        this.f[this.e++] = a;
        this.f[this.e++] = a;
    }
    
    public void a(final double a, final double a, final double a) {
        this.f[this.e++] = (float)a;
        this.f[this.e++] = (float)a;
        this.f[this.e++] = (float)a;
    }
}
