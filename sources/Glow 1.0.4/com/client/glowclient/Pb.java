package com.client.glowclient;

import com.google.common.collect.*;

public class pb extends AbstractIterator<wc>
{
    private int d;
    private int L;
    private int A;
    private wc B;
    public final BA b;
    
    public wc computeNext() {
        if (this.B == null) {
            this.L = this.b.b.getX();
            this.A = this.b.b.getY();
            this.d = this.b.b.getZ();
            return this.B = new wc(this.L, this.A, this.d);
        }
        if (this.B.equals((Object)this.b.B)) {
            return this.endOfData();
        }
        pb pb;
        if (this.A < this.b.B.getY()) {
            pb = this;
            ++this.A;
        }
        else if (this.L < this.b.B.getX()) {
            pb = this;
            this.A = this.b.b.getY();
            ++this.L;
        }
        else {
            if (this.d < this.b.B.getZ()) {
                this.A = this.b.b.getY();
                this.L = this.b.b.getX();
                ++this.d;
            }
            pb = this;
        }
        pb.B.b = this.L;
        this.B.A = this.A;
        this.B.B = this.d;
        return this.B;
    }
    
    public pb(final BA b) {
        final wc b2 = null;
        this.b = b;
        super();
        this.B = b2;
    }
    
    public Object computeNext() {
        return this.computeNext();
    }
}
