package wwe;

static final class wwe/QkaT
{
    private int qqMK;
    private int pSYy;
    private int WrWV;
    private byte[] QGBu;
    
    wwe/QkaT() {
        super();
    }
    
    private final void dsuC(final byte[] qgBu) {
        this.QGBu = qgBu;
        this.qqMK = 0;
    }
    
    private final int oakA() {
        int n = this.qqMK >>> 3;
        final int n2 = (((this.QGBu[n] & 0xFF) << 8 | (this.QGBu[n++] & 0xFF)) << 8 | (this.QGBu[n++] & 0xFF)) >>> (this.qqMK & 0x7);
        this.qqMK += this.pSYy;
        return n2 & this.WrWV;
    }
    
    private final void CxVh(final int psYy) {
        this.pSYy = psYy;
        this.WrWV = (1 << psYy) - 1;
    }
    
    static void kJoN(final wwe/QkaT wwe/QkaT, final int n) {
        wwe/QkaT.CxVh(n);
    }
    
    static void sEgv(final wwe/QkaT wwe/QkaT, final byte[] array) {
        wwe/QkaT.dsuC(array);
    }
    
    static int urTX(final wwe/QkaT wwe/QkaT) {
        return wwe/QkaT.oakA();
    }
}
