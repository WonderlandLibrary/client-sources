package wwe;

static final class wwe/eCpO
{
    private final int[][] eZqR;
    private int BLoD;
    private int vlpV;
    private int Stxb;
    private int dsEB;
    private int WzEK;
    private int LqwG;
    private wwe/QkaT suIR;
    
    public wwe/eCpO() {
        super();
        this.eZqR = new int[4096][1];
    }
    
    private final int RIGp(final int[] array) {
        if (this.WzEK < 4096) {
            if (this.WzEK == this.LqwG && this.dsEB < 12) {
                ++this.dsEB;
                wwe/QkaT.kJoN(this.suIR, this.dsEB);
                this.LqwG = (1 << this.dsEB) - 1;
            }
            this.eZqR[this.WzEK++] = array;
        }
        return this.dsEB;
    }
    
    private final int uLHQ() {
        this.dsEB = this.vlpV;
        wwe/QkaT.kJoN(this.suIR, this.dsEB);
        this.LqwG = this.Stxb;
        this.WzEK = this.BLoD;
        return this.dsEB;
    }
    
    private final void EwDX(final wwe/TAhz wwe/TAhz, final int[] array, final wwe/QkaT suIR) {
        this.suIR = suIR;
        final int length = array.length;
        this.vlpV = kdJj.wwe/TAhz.QFSe(wwe/TAhz);
        this.Stxb = (1 << this.vlpV) - 1;
        this.BLoD = kdJj.wwe/TAhz.QJjm(wwe/TAhz) + 1;
        this.WzEK = this.BLoD;
        int i = length - 1;
        while (i >= 0) {
            this.eZqR[i][0] = array[i];
            --i;
            if (wwe/eCpO.\u2007\u200d) {
                throw null;
            }
        }
        this.eZqR[kdJj.wwe/TAhz.FHnS(wwe/TAhz)] = new int[] { kdJj.wwe/TAhz.FHnS(wwe/TAhz) };
        this.eZqR[kdJj.wwe/TAhz.QJjm(wwe/TAhz)] = new int[] { kdJj.wwe/TAhz.QJjm(wwe/TAhz) };
        if (kdJj.wwe/TAhz.sTPw(wwe/TAhz) && kdJj.wwe/TAhz.VgXR(wwe/TAhz) < length) {
            this.eZqR[kdJj.wwe/TAhz.VgXR(wwe/TAhz)][0] = 0;
        }
    }
    
    static void qcsS(final wwe/eCpO wwe/eCpO, final wwe/TAhz wwe/TAhz, final int[] array, final wwe/QkaT wwe/QkaT) {
        wwe/eCpO.EwDX(wwe/TAhz, array, wwe/QkaT);
    }
    
    static int[][] NHjA(final wwe/eCpO wwe/eCpO) {
        return wwe/eCpO.eZqR;
    }
    
    static int stUN(final wwe/eCpO wwe/eCpO) {
        return wwe/eCpO.uLHQ();
    }
    
    static int RLzb(final wwe/eCpO wwe/eCpO) {
        return wwe/eCpO.WzEK;
    }
    
    static int eCwX(final wwe/eCpO wwe/eCpO, final int[] array) {
        return wwe/eCpO.RIGp(array);
    }
}
