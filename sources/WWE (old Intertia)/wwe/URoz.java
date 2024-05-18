package wwe;

import java.util.*;

public class URoz
{
    private Vector fIwl;
    
    public URoz() {
        super();
        this.fIwl = new Vector();
    }
    
    public void xJDd(final Object o) {
        synchronized (this.fIwl) {
            this.fIwl.addElement(o);
            this.fIwl.notify();
            // monitorexit(this.fIwl)
            if (URoz.\u200d\u200a\u2003\u2007\u2005\u200b\u200c\u2005\u2005) {
                throw null;
            }
        }
    }
    
    public void fXmg(final Object o) {
        synchronized (this.fIwl) {
            this.fIwl.insertElementAt(o, 0);
            this.fIwl.notify();
            // monitorexit(this.fIwl)
            if (URoz.\u200d\u200a\u2003\u2007\u2005\u200b\u200c\u2005\u2005) {
                throw null;
            }
        }
    }
    
    public Object uFcI() {
        Object firstElement = null;
        synchronized (this.fIwl) {
            if (this.fIwl.size() == 0) {
                try {
                    this.fIwl.wait();
                    if (URoz.\u200d\u200a\u2003\u2007\u2005\u200b\u200c\u2005\u2005) {
                        throw null;
                    }
                }
                catch (InterruptedException ex) {
                    return null;
                }
            }
            try {
                firstElement = this.fIwl.firstElement();
                this.fIwl.removeElementAt(0);
                if (URoz.\u200d\u200a\u2003\u2007\u2005\u200b\u200c\u2005\u2005) {
                    throw null;
                }
            }
            catch (ArrayIndexOutOfBoundsException ex2) {
                throw new InternalError("Race hazard in Queue object.");
            }
            // monitorexit(this.fIwl)
            if (URoz.\u200d\u200a\u2003\u2007\u2005\u200b\u200c\u2005\u2005) {
                throw null;
            }
        }
        return firstElement;
    }
    
    public boolean HCgy() {
        boolean b;
        if (this.zJft() != 0) {
            b = true;
            if (URoz.\u200d\u200a\u2003\u2007\u2005\u200b\u200c\u2005\u2005) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public void Xbga() {
        synchronized (this.fIwl) {
            this.fIwl.removeAllElements();
            // monitorexit(this.fIwl)
            if (URoz.\u200d\u200a\u2003\u2007\u2005\u200b\u200c\u2005\u2005) {
                throw null;
            }
        }
    }
    
    public int zJft() {
        return this.fIwl.size();
    }
}
