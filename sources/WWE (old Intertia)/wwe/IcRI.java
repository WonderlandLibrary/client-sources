package wwe;

import wwe.utils.*;

public class IcRI<T>
{
    public String VQBN;
    public qMIe lJZv;
    public String qYCS;
    public T JsAg;
    public boolean geCg;
    public wwe/NUxq vaTl;
    
    public IcRI(final String vqbn, final qMIe ljZv, final String qycs, final T jsAg) {
        super();
        this.VQBN = vqbn;
        this.lJZv = ljZv;
        this.qYCS = qycs;
        this.JsAg = jsAg;
        vlwv.tiEw.add(this);
    }
    
    public boolean MATs() {
        boolean b;
        if (this.vaTl == wwe/NUxq.eFCi) {
            b = true;
            if (IcRI.\u200e\u200b) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean wnpo() {
        boolean b;
        if (this.vaTl == wwe/NUxq.QJpj) {
            b = true;
            if (IcRI.\u200e\u200b) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean dTln() {
        boolean b;
        if (this.vaTl == wwe/NUxq.JdUb) {
            b = true;
            if (IcRI.\u200e\u200b) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean bsGi() {
        final wwe/NUxq vaTl = this.vaTl;
        final wwe/NUxq vaTl2 = this.vaTl;
        boolean b;
        if (vaTl == wwe/NUxq.bdnx) {
            b = true;
            if (IcRI.\u200e\u200b) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public T OnGi() {
        return this.JsAg;
    }
    
    public void NdCc(final T jsAg) {
        this.JsAg = jsAg;
        Wrapper.ypJK().rXPb();
    }
    
    public void thqV(final T jsAg) {
        this.JsAg = jsAg;
    }
    
    public enum wwe/NUxq
    {
        eFCi, 
        QJpj, 
        JdUb, 
        bdnx;
        
        private static final wwe/NUxq[] Awuh;
        
        public static wwe/NUxq[] CNgA() {
            return wwe/NUxq.Awuh.clone();
        }
        
        public static wwe/NUxq KetI(final String s) {
            return Enum.valueOf(wwe/NUxq.class, s);
        }
        
        static {
            Awuh = new wwe/NUxq[] { wwe/NUxq.eFCi, wwe/NUxq.QJpj, wwe/NUxq.JdUb, wwe/NUxq.bdnx };
        }
    }
}
