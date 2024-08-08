package net.futureclient.client;

import java.io.File;

public abstract class q implements H, c, D
{
    private File a;
    private String D;
    private File k;
    
    public q(final String d) {
        super();
        this.D = d;
        this.k = pg.M().M();
        this.a = new File(this.k, d);
        pg.M().M().e(this);
    }
    
    public q(final String d, final File k) {
        super();
        this.D = d;
        this.k = k;
        this.a = new File(k, d);
        pg.M().M().e(this);
    }
    
    public File e() {
        return this.a;
    }
    
    @Override
    public abstract void e(final Object... p0);
    
    @Override
    public String M() {
        return this.D;
    }
    
    @Override
    public abstract void M(final Object... p0);
    
    public File M() {
        return this.k;
    }
}
