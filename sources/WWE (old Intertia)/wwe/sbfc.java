package wwe;

public class sbfc
{
    private long Pjvt;
    private long alqC;
    
    public sbfc() {
        super();
        this.Pjvt = (0x2CAAD36FCD640CB2L ^ 0x2CAAD36FCD640CB2L);
        this.alqC = (0x9F340F6E316A055CL ^ 0x60CBF091CE95FAA3L);
    }
    
    public boolean QOXG(final double n) {
        boolean b;
        if (this.Pjvt - this.alqC >= n) {
            b = true;
            if (sbfc.\u200c\u200b\u2001\u2009) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public long KBDH() {
        return this.Pjvt - this.alqC;
    }
    
    public void gCgh() {
        this.Pjvt = System.nanoTime() / (0x334E0B9E9DF88AF7L ^ 0x334E0B9E9DF7C8B7L);
    }
    
    public void BBwj() {
        this.alqC = System.nanoTime() / (0xD8D6DF7A7BE086A1L ^ 0xD8D6DF7A7BEFC4E1L);
    }
}
