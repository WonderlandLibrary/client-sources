package com.client.glowclient;

import com.google.common.collect.*;

public class oC extends AbstractIterator<wc>
{
    private int d;
    public final yb L;
    private wc A;
    private int B;
    private int b;
    
    public oC(final yb l) {
        final wc a = null;
        this.L = l;
        super();
        this.A = a;
    }
    
    public Object computeNext() {
        return this.computeNext();
    }
    
    public wc computeNext() {
        if (this.A == null) {
            this.d = this.L.B.getX();
            this.b = this.L.B.getY();
            this.B = this.L.B.getZ();
            return this.A = new wc(this.d, this.b, this.B);
        }
        if (this.A.equals((Object)this.L.b)) {
            return this.endOfData();
        }
        oC oc;
        if (this.d < this.L.b.getX()) {
            oc = this;
            ++this.d;
        }
        else if (this.B < this.L.b.getZ()) {
            oc = this;
            this.d = this.L.B.getX();
            ++this.B;
        }
        else {
            if (this.b < this.L.b.getY()) {
                this.d = this.L.B.getX();
                this.B = this.L.B.getZ();
                ++this.b;
            }
            oc = this;
        }
        oc.A.b = this.d;
        this.A.A = this.b;
        this.A.B = this.B;
        return this.A;
    }
}
