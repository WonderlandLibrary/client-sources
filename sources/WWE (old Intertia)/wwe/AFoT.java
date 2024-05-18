package wwe;

import java.util.*;
import java.awt.image.*;
import java.awt.*;

public final class wwe/AFoT
{
    public String hpMD;
    private int pAGu;
    private int ZRfb;
    private int DOLo;
    public boolean SOHH;
    public int qQEO;
    public boolean CvKh;
    public int utuP;
    public int EndD;
    public int spyv;
    public int[] FxwC;
    private final List<wwe/TAhz> odLZ;
    public String RvEk;
    public String oYPr;
    public int Jfdm;
    private BufferedImage yXhb;
    private int[] iIgr;
    private final wwe/QkaT VkFL;
    private final wwe/eCpO oWXh;
    private Graphics2D qLVc;
    final kdJj bcPu;
    
    public wwe/AFoT(final kdJj bcPu) {
        this.bcPu = bcPu;
        super();
        this.odLZ = new ArrayList<wwe/TAhz>(64);
        this.RvEk = "";
        this.oYPr = "";
        this.Jfdm = 0;
        this.yXhb = null;
        this.iIgr = null;
        this.VkFL = new wwe/QkaT();
        this.oWXh = new wwe/eCpO();
    }
    
    private final int[] TWyD(final wwe/TAhz wwe/TAhz, final int[] array) {
        wwe/eCpO.qcsS(this.oWXh, wwe/TAhz, array, this.VkFL);
        wwe/QkaT.sEgv(this.VkFL, kdJj.wwe/TAhz.feDj(wwe/TAhz));
        final int fHnS = kdJj.wwe/TAhz.FHnS(wwe/TAhz);
        final int qJjm = kdJj.wwe/TAhz.QJjm(wwe/TAhz);
        final int[] array2 = new int[this.DOLo];
        final int[][] array3 = wwe/eCpO.NHjA(this.oWXh);
        final int n = 0;
        wwe/eCpO.stUN(this.oWXh);
        wwe/QkaT.urTX(this.VkFL);
        int n2 = wwe/QkaT.urTX(this.VkFL);
        final int[] array4 = array3[n2];
        System.arraycopy(array4, 0, array2, n, array4.length);
        int n3 = n + array4.length;
        try {
            while (true) {
                final int n4 = n2;
                n2 = wwe/QkaT.urTX(this.VkFL);
                if (n2 == fHnS) {
                    wwe/eCpO.stUN(this.oWXh);
                    n2 = wwe/QkaT.urTX(this.VkFL);
                    final int[] array5 = array3[n2];
                    System.arraycopy(array5, 0, array2, n3, array5.length);
                    n3 += array5.length;
                    if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                        throw null;
                    }
                    continue;
                }
                else if (n2 == qJjm) {
                    if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                        throw null;
                    }
                    if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                        throw null;
                    }
                    break;
                }
                else {
                    final int[] array6 = array3[n4];
                    final int[] array7 = new int[array6.length + 1];
                    System.arraycopy(array6, 0, array7, 0, array6.length);
                    if (n2 < wwe/eCpO.RLzb(this.oWXh)) {
                        final int[] array8 = array3[n2];
                        System.arraycopy(array8, 0, array2, n3, array8.length);
                        n3 += array8.length;
                        array7[array6.length] = array3[n2][0];
                        if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                            throw null;
                        }
                    }
                    else {
                        array7[array6.length] = array6[0];
                        System.arraycopy(array7, 0, array2, n3, array7.length);
                        n3 += array7.length;
                    }
                    wwe/eCpO.eCwX(this.oWXh, array7);
                    if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                        throw null;
                    }
                    continue;
                }
            }
        }
        catch (ArrayIndexOutOfBoundsException ex) {}
        return array2;
    }
    
    private final int[] TVJk(final int[] array, final wwe/TAhz wwe/TAhz) {
        final int tDzh = kdJj.wwe/TAhz.tDzh(wwe/TAhz);
        final int yWtZ = kdJj.wwe/TAhz.YWtZ(wwe/TAhz);
        final int ioBz = kdJj.wwe/TAhz.IOBz(wwe/TAhz);
        final int[] array2 = new int[array.length];
        final int n = yWtZ + 7 >>> 3;
        final int n2 = n + (yWtZ + 3 >>> 3);
        final int n3 = n2 + (yWtZ + 1 >>> 2);
        final int n4 = tDzh * n;
        final int n5 = tDzh * n2;
        final int n6 = tDzh * n3;
        final int n7 = tDzh << 1;
        final int n8 = n7 << 1;
        final int n9 = n8 << 1;
        int i = 0;
        int n10 = 0;
        while (i < n4) {
            System.arraycopy(array, i, array2, n10, tDzh);
            i += tDzh;
            n10 += n9;
            if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                throw null;
            }
        }
        int n11 = n8;
        while (i < n5) {
            System.arraycopy(array, i, array2, n11, tDzh);
            i += tDzh;
            n11 += n9;
            if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                throw null;
            }
        }
        int n12 = n7;
        while (i < n6) {
            System.arraycopy(array, i, array2, n12, tDzh);
            i += tDzh;
            n12 += n8;
            if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                throw null;
            }
        }
        int n13 = tDzh;
        while (i < ioBz) {
            System.arraycopy(array, i, array2, n13, tDzh);
            i += tDzh;
            n13 += n7;
            if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                throw null;
            }
        }
        return array2;
    }
    
    private final void eUwH(final wwe/TAhz wwe/TAhz) {
        int[] fxwC;
        if (kdJj.wwe/TAhz.Xkta(wwe/TAhz)) {
            fxwC = kdJj.wwe/TAhz.cozM(wwe/TAhz);
            if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                throw null;
            }
        }
        else {
            fxwC = this.FxwC;
        }
        int[] array = this.TWyD(wwe/TAhz, fxwC);
        if (kdJj.wwe/TAhz.nxjS(wwe/TAhz)) {
            array = this.TVJk(array, wwe/TAhz);
        }
        final BufferedImage bufferedImage = new BufferedImage(kdJj.wwe/TAhz.tDzh(wwe/TAhz), kdJj.wwe/TAhz.YWtZ(wwe/TAhz), 2);
        System.arraycopy(array, 0, ((DataBufferInt)bufferedImage.getRaster().getDataBuffer()).getData(), 0, kdJj.wwe/TAhz.IOBz(wwe/TAhz));
        this.qLVc.drawImage(bufferedImage, kdJj.wwe/TAhz.agUi(wwe/TAhz), kdJj.wwe/TAhz.YLVf(wwe/TAhz), null);
        this.iIgr = new int[this.DOLo];
        System.arraycopy(((DataBufferInt)this.yXhb.getRaster().getDataBuffer()).getData(), 0, this.iIgr, 0, this.DOLo);
        kdJj.wwe/TAhz.OsuN(wwe/TAhz, new BufferedImage(this.pAGu, this.ZRfb, 2));
        System.arraycopy(this.iIgr, 0, ((DataBufferInt)kdJj.wwe/TAhz.BUYD(wwe/TAhz).getRaster().getDataBuffer()).getData(), 0, this.DOLo);
        if (kdJj.wwe/TAhz.DqRz(wwe/TAhz) == 2) {
            this.qLVc.clearRect(kdJj.wwe/TAhz.agUi(wwe/TAhz), kdJj.wwe/TAhz.YLVf(wwe/TAhz), kdJj.wwe/TAhz.tDzh(wwe/TAhz), kdJj.wwe/TAhz.YWtZ(wwe/TAhz));
            if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                throw null;
            }
        }
        else if (kdJj.wwe/TAhz.DqRz(wwe/TAhz) == 3 && this.iIgr != null) {
            System.arraycopy(this.iIgr, 0, ((DataBufferInt)this.yXhb.getRaster().getDataBuffer()).getData(), 0, this.DOLo);
        }
    }
    
    public final int cCtA() {
        final wwe/TAhz wwe/TAhz = this.odLZ.get(0);
        if (kdJj.wwe/TAhz.Xkta(wwe/TAhz)) {
            return kdJj.wwe/TAhz.cozM(wwe/TAhz)[this.EndD];
        }
        if (this.SOHH) {
            return this.FxwC[this.EndD];
        }
        return 0;
    }
    
    public final int eoEa(final int n) {
        return wwe/TAhz.nvXN(this.odLZ.get(n));
    }
    
    public final BufferedImage zeqb(final int n) {
        if (this.yXhb == null) {
            this.yXhb = new BufferedImage(this.pAGu, this.ZRfb, 2);
            (this.qLVc = this.yXhb.createGraphics()).setBackground(new Color(0, true));
        }
        wwe/TAhz wwe/TAhz = this.odLZ.get(n);
        if (kdJj.wwe/TAhz.BUYD(wwe/TAhz) == null) {
            int i = 0;
            while (i <= n) {
                wwe/TAhz = this.odLZ.get(i);
                if (kdJj.wwe/TAhz.BUYD(wwe/TAhz) == null) {
                    this.eUwH(wwe/TAhz);
                }
                ++i;
                if (wwe/AFoT.\u200d\u2004\u2005\u200d\u2003\u200b\u2003\u200f\u2004) {
                    throw null;
                }
            }
        }
        return kdJj.wwe/TAhz.BUYD(wwe/TAhz);
    }
    
    public final int KrUj() {
        return this.odLZ.size();
    }
    
    public final int CFiM() {
        return this.ZRfb;
    }
    
    public final int zJLb() {
        return this.pAGu;
    }
    
    static List Zyin(final wwe/AFoT wwe/AFoT) {
        return wwe/AFoT.odLZ;
    }
    
    static int Swah(final wwe/AFoT wwe/AFoT, final int paGu) {
        return wwe/AFoT.pAGu = paGu;
    }
    
    static int VqFs(final wwe/AFoT wwe/AFoT, final int zRfb) {
        return wwe/AFoT.ZRfb = zRfb;
    }
    
    static int TXae(final wwe/AFoT wwe/AFoT, final int doLo) {
        return wwe/AFoT.DOLo = doLo;
    }
    
    static int avTz(final wwe/AFoT wwe/AFoT) {
        return wwe/AFoT.pAGu;
    }
    
    static int OEWQ(final wwe/AFoT wwe/AFoT) {
        return wwe/AFoT.ZRfb;
    }
}
