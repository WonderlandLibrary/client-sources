package jaco.mp3.a.a;

import jaco.mp3.a.*;

public abstract class a
{
    private boolean a;
    private b b;
    
    public a() {
        this.a = false;
        this.b = null;
    }
    
    public final synchronized void a(final b b) {
        if (!this.f()) {
            this.b = b;
            final a a = this;
            final boolean a2 = true;
            this = a;
            a.a = a2;
        }
    }
    
    private synchronized boolean f() {
        return this.a;
    }
    
    public final synchronized void a() {
        if (this.f()) {
            this.b();
            this.a = false;
            this.b = null;
        }
    }
    
    protected void b() {
    }
    
    public final void a(final short[] array, final int n, final int n2) {
        if (this.f()) {
            this.b(array, 0, n2);
        }
    }
    
    protected void b(final short[] array, final int n, final int n2) {
    }
    
    public final void c() {
        if (this.f()) {
            this.d();
        }
    }
    
    protected void d() {
    }
    
    protected final b e() {
        return this.b;
    }
}
