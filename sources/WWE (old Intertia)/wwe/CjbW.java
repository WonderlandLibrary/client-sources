package wwe;

public class CjbW
{
    private String RPJv;
    private String FOYe;
    private String hxTE;
    
    CjbW(final String rpJv, final String foYe) {
        super();
        this.RPJv = rpJv;
        this.FOYe = foYe;
        this.hxTE = foYe.toLowerCase();
    }
    
    public String xvkx() {
        return this.RPJv;
    }
    
    public boolean JPHc() {
        boolean b;
        if (this.RPJv.indexOf(64) >= 0) {
            b = true;
            if (CjbW.\u200b\u2008) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public boolean YNVZ() {
        boolean b;
        if (this.RPJv.indexOf(43) >= 0) {
            b = true;
            if (CjbW.\u200b\u2008) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public String QjOG() {
        return this.FOYe;
    }
    
    @Override
    public String toString() {
        return this.xvkx() + this.QjOG();
    }
    
    public boolean PYDJ(final String s) {
        return s.toLowerCase().equals(this.hxTE);
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof CjbW && ((CjbW)o).hxTE.equals(this.hxTE);
    }
    
    @Override
    public int hashCode() {
        return this.hxTE.hashCode();
    }
    
    public int HBlT(final Object o) {
        if (o instanceof CjbW) {
            return ((CjbW)o).hxTE.compareTo(this.hxTE);
        }
        return -1;
    }
}
