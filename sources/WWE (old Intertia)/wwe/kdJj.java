package wwe;

import java.io.*;
import org.apache.commons.io.*;
import java.util.*;
import java.awt.image.*;
import java.awt.*;

public final class kdJj
{
    static final boolean SGAQ = false;
    
    public kdJj() {
        super();
    }
    
    public static final wwe/AFoT gWIJ(final byte[] array) throws IOException {
        final kdJj kdJj = new kdJj();
        final wwe/AFoT wwe/AFoT = kdJj.new wwe/AFoT();
        wwe/TAhz wwe/TAhz = null;
        int i = kfZJ(wwe/AFoT, array, tUNV(array, wwe/AFoT));
        if (wwe/AFoT.SOHH) {
            wwe/AFoT.FxwC = new int[wwe/AFoT.utuP];
            i = yaWZ(array, wwe/AFoT.FxwC, i);
        }
        while (i < array.length) {
            Label_0525: {
                switch (array[i] & 0xFF) {
                    case 33: {
                        if (i + 1 >= array.length) {
                            throw new IOException("Unexpected end of file.");
                        }
                        switch (array[i + 1] & 0xFF) {
                            case 254: {
                                i = xEQU(array, i);
                                if (wwe.kdJj.\u2005\u2006\u200f\u200e) {
                                    throw null;
                                }
                                break Label_0525;
                            }
                            case 255: {
                                i = VgNu(wwe/AFoT, array, i);
                                if (wwe.kdJj.\u2005\u2006\u200f\u200e) {
                                    throw null;
                                }
                                break Label_0525;
                            }
                            case 1: {
                                wwe/TAhz = null;
                                i = xEQU(array, i);
                                if (wwe.kdJj.\u2005\u2006\u200f\u200e) {
                                    throw null;
                                }
                                break Label_0525;
                            }
                            case 249: {
                                if (wwe/TAhz == null) {
                                    wwe/TAhz = kdJj.new wwe/TAhz();
                                    wwe.kdJj.wwe/AFoT.Zyin(wwe/AFoT).add(wwe/TAhz);
                                }
                                i = Ipcf(wwe/TAhz, array, i);
                                if (wwe.kdJj.\u2005\u2006\u200f\u200e) {
                                    throw null;
                                }
                                break Label_0525;
                            }
                            default: {
                                throw new IOException("Unknown extension at " + i);
                            }
                        }
                        break;
                    }
                    case 44: {
                        if (wwe/TAhz == null) {
                            wwe/TAhz = kdJj.new wwe/TAhz();
                            wwe.kdJj.wwe/AFoT.Zyin(wwe/AFoT).add(wwe/TAhz);
                        }
                        int n = QDzy(wwe/TAhz, array, i);
                        if (wwe.kdJj.wwe/TAhz.Xkta(wwe/TAhz)) {
                            final int[] array2 = wwe.kdJj.wwe/TAhz.PURY(wwe/TAhz, new int[wwe.kdJj.wwe/TAhz.VGiH(wwe/TAhz)]);
                            n = yaWZ(array, wwe.kdJj.wwe/TAhz.cozM(wwe/TAhz), n);
                        }
                        i = LnZp(wwe/TAhz, array, n);
                        wwe/TAhz = null;
                        if (wwe.kdJj.\u2005\u2006\u200f\u200e) {
                            throw null;
                        }
                        break;
                    }
                    case 59: {
                        return wwe/AFoT;
                    }
                    default: {
                        if (1.0 * i / array.length < 0.9) {
                            throw new IOException("Unknown block at: " + i);
                        }
                        i = array.length;
                        break;
                    }
                }
            }
            if (wwe.kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        return wwe/AFoT;
    }
    
    public static final wwe/AFoT cEoj(final InputStream input) throws IOException {
        final byte[] array = IOUtils.toByteArray(input);
        input.read(array, 0, array.length);
        return gWIJ(array);
    }
    
    static final int VgNu(final wwe/AFoT wwe/AFoT, final byte[] array, int n) {
        wwe/AFoT.RvEk = new String(array, n + 3, 8);
        wwe/AFoT.oYPr = new String(array, n + 11, 3);
        n += 14;
        if ((array[n] & 0xFF) == 0x3) {
            wwe/AFoT.Jfdm = ((array[n + 2] & 0xFF) | (array[n + 3] & 0xFF00));
            return n + 5;
        }
        while ((array[n] & 0xFF) != 0x0) {
            n += (array[n] & 0xFF) + 1;
            if (kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        return n + 1;
    }
    
    static final int yaWZ(final byte[] array, final int[] array2, int n) {
        final int length = array2.length;
        int i = 0;
        while (i < length) {
            array2[i] = (((0xFF00 | (array[n++] & 0xFF)) << 8 | (array[n++] & 0xFF)) << 8 | (array[n++] & 0xFF));
            ++i;
            if (kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        return n;
    }
    
    static final int Ipcf(final wwe/TAhz wwe/TAhz, final byte[] array, final int n) {
        kdJj.wwe/TAhz.bYAt(wwe/TAhz, (array[n + 3] & 0x1C) >>> 2);
        boolean b;
        if ((array[n + 3] & 0x1) == 0x1) {
            b = true;
            if (kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        else {
            b = false;
        }
        kdJj.wwe/TAhz.MNpb(wwe/TAhz, b);
        kdJj.wwe/TAhz.aTED(wwe/TAhz, (array[n + 4] & 0xFF) | (array[n + 5] & 0xFF) << 8);
        kdJj.wwe/TAhz.rtIk(wwe/TAhz, array[n + 6] & 0xFF);
        return n + 8;
    }
    
    static final int tUNV(final byte[] array, final wwe/AFoT wwe/AFoT) throws IOException {
        if (array.length < 6) {
            throw new IOException("Image is truncated.");
        }
        wwe/AFoT.hpMD = new String(array, 0, 6);
        if (!wwe/AFoT.hpMD.equals("GIF87a") && !wwe/AFoT.hpMD.equals("GIF89a")) {
            throw new IOException("Invalid GIF header.");
        }
        return 6;
    }
    
    static final int LnZp(final wwe/TAhz wwe/TAhz, final byte[] array, int n) {
        final int length = array.length;
        final int n2 = array[n++] & 0xFF;
        final int n3 = 1 << n2;
        kdJj.wwe/TAhz.lFWw(wwe/TAhz, n2 + 1);
        kdJj.wwe/TAhz.hPxZ(wwe/TAhz, n3);
        kdJj.wwe/TAhz.fTPm(wwe/TAhz, n3 + 1);
        final byte[] array2 = new byte[rsZZ(array, n) + 2];
        int n4 = 0;
        int i = array[n] & 0xFF;
        while (i > 0) {
            try {
                final int n5 = n + i + 1;
                final int n6 = array[n5] & 0xFF;
                System.arraycopy(array, n + 1, array2, n4, i);
                n4 += i;
                n = n5;
                i = n6;
                if (kdJj.\u2005\u2006\u200f\u200e) {
                    throw null;
                }
                continue;
            }
            catch (Exception ex) {
                final int n7 = length - n - 1;
                System.arraycopy(array, n + 1, array2, n4, n7);
                n += n7 + 1;
                if (kdJj.\u2005\u2006\u200f\u200e) {
                    throw null;
                }
            }
            break;
        }
        final byte[] array3 = kdJj.wwe/TAhz.ytXa(wwe/TAhz, array2);
        return ++n;
    }
    
    static final int rsZZ(final byte[] array, int n) {
        final int length = array.length;
        int n2 = 0;
        int i = array[n] & 0xFF;
        while (i > 0) {
            try {
                final int n3 = n + i + 1;
                final int n4 = array[n3] & 0xFF;
                n2 += i;
                n = n3;
                i = n4;
                if (kdJj.\u2005\u2006\u200f\u200e) {
                    throw null;
                }
                continue;
            }
            catch (Exception ex) {
                n2 += length - n - 1;
                if (kdJj.\u2005\u2006\u200f\u200e) {
                    throw null;
                }
            }
            break;
        }
        return n2;
    }
    
    static final int QDzy(final wwe/TAhz wwe/TAhz, final byte[] array, int n) {
        kdJj.wwe/TAhz.YWzv(wwe/TAhz, (array[++n] & 0xFF) | (array[++n] & 0xFF) << 8);
        kdJj.wwe/TAhz.aaDG(wwe/TAhz, (array[++n] & 0xFF) | (array[++n] & 0xFF) << 8);
        kdJj.wwe/TAhz.xknu(wwe/TAhz, (array[++n] & 0xFF) | (array[++n] & 0xFF) << 8);
        kdJj.wwe/TAhz.KPug(wwe/TAhz, (array[++n] & 0xFF) | (array[++n] & 0xFF) << 8);
        kdJj.wwe/TAhz.yxnl(wwe/TAhz, kdJj.wwe/TAhz.tDzh(wwe/TAhz) * kdJj.wwe/TAhz.YWtZ(wwe/TAhz));
        final byte b = array[++n];
        boolean b2;
        if ((b & 0x80) >>> 7 == 1) {
            b2 = true;
            if (kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        else {
            b2 = false;
        }
        kdJj.wwe/TAhz.KzUM(wwe/TAhz, b2);
        boolean b3;
        if ((b & 0x40) >>> 6 == 1) {
            b3 = true;
            if (kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        else {
            b3 = false;
        }
        kdJj.wwe/TAhz.kqBe(wwe/TAhz, b3);
        boolean b4;
        if ((b & 0x20) >>> 5 == 1) {
            b4 = true;
            if (kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        else {
            b4 = false;
        }
        kdJj.wwe/TAhz.nfrI(wwe/TAhz, b4);
        kdJj.wwe/TAhz.hDEv(wwe/TAhz, 1 << (b & 0x7) + 1);
        return ++n;
    }
    
    static final int kfZJ(final wwe/AFoT wwe/AFoT, final byte[] array, final int n) {
        kdJj.wwe/AFoT.Swah(wwe/AFoT, (array[n] & 0xFF) | (array[n + 1] & 0xFF) << 8);
        kdJj.wwe/AFoT.VqFs(wwe/AFoT, (array[n + 2] & 0xFF) | (array[n + 3] & 0xFF) << 8);
        kdJj.wwe/AFoT.TXae(wwe/AFoT, kdJj.wwe/AFoT.avTz(wwe/AFoT) * kdJj.wwe/AFoT.OEWQ(wwe/AFoT));
        final byte b = array[n + 4];
        boolean sohh;
        if ((b & 0x80) >>> 7 == 1) {
            sohh = true;
            if (kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        else {
            sohh = false;
        }
        wwe/AFoT.SOHH = sohh;
        wwe/AFoT.qQEO = 1 << ((b & 0x70) >>> 4) + 1;
        boolean cvKh;
        if ((b & 0x8) >>> 3 == 1) {
            cvKh = true;
            if (kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        else {
            cvKh = false;
        }
        wwe/AFoT.CvKh = cvKh;
        wwe/AFoT.utuP = 1 << (b & 0x7) + 1;
        wwe/AFoT.EndD = (array[n + 5] & 0xFF);
        wwe/AFoT.spyv = (array[n + 6] & 0xFF);
        return n + 7;
    }
    
    static final int xEQU(final byte[] array, final int n) {
        int n2 = n + 2;
        int n3 = array[n2++] & 0xFF;
        while (n3 != 0 && n2 < array.length) {
            n2 += n3;
            n3 = (array[n2++] & 0xFF);
            if (kdJj.\u2005\u2006\u200f\u200e) {
                throw null;
            }
        }
        return n2;
    }
    
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
    
    final class wwe/TAhz
    {
        private int isPG;
        private boolean WObF;
        private int ItuP;
        private int Rabw;
        private int pnlj;
        private int Rong;
        private int nNsP;
        private int XZSf;
        private int CBqY;
        private boolean HnoQ;
        private boolean cSTs;
        private boolean RSbt;
        private int bgsT;
        private int[] nIhE;
        private int SWCt;
        private int nnlx;
        private int iauq;
        private byte[] fNfv;
        private BufferedImage gYfS;
        final kdJj JGeB;
        
        wwe/TAhz(final kdJj jGeB) {
            this.JGeB = jGeB;
            super();
        }
        
        static int QFSe(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.SWCt;
        }
        
        static int QJjm(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.iauq;
        }
        
        static int FHnS(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.nnlx;
        }
        
        static boolean sTPw(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.WObF;
        }
        
        static int VgXR(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.Rabw;
        }
        
        static byte[] feDj(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.fNfv;
        }
        
        static int tDzh(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.nNsP;
        }
        
        static int YWtZ(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.XZSf;
        }
        
        static int IOBz(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.CBqY;
        }
        
        static boolean Xkta(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.HnoQ;
        }
        
        static int[] cozM(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.nIhE;
        }
        
        static boolean nxjS(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.cSTs;
        }
        
        static int agUi(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.pnlj;
        }
        
        static int YLVf(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.Rong;
        }
        
        static BufferedImage OsuN(final wwe/TAhz wwe/TAhz, final BufferedImage gYfS) {
            return wwe/TAhz.gYfS = gYfS;
        }
        
        static BufferedImage BUYD(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.gYfS;
        }
        
        static int DqRz(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.isPG;
        }
        
        static int nvXN(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.ItuP;
        }
        
        static int[] PURY(final wwe/TAhz wwe/TAhz, final int[] nIhE) {
            return wwe/TAhz.nIhE = nIhE;
        }
        
        static int VGiH(final wwe/TAhz wwe/TAhz) {
            return wwe/TAhz.bgsT;
        }
        
        static int bYAt(final wwe/TAhz wwe/TAhz, final int isPG) {
            return wwe/TAhz.isPG = isPG;
        }
        
        static boolean MNpb(final wwe/TAhz wwe/TAhz, final boolean wObF) {
            return wwe/TAhz.WObF = wObF;
        }
        
        static int aTED(final wwe/TAhz wwe/TAhz, final int ituP) {
            return wwe/TAhz.ItuP = ituP;
        }
        
        static int rtIk(final wwe/TAhz wwe/TAhz, final int rabw) {
            return wwe/TAhz.Rabw = rabw;
        }
        
        static int lFWw(final wwe/TAhz wwe/TAhz, final int swCt) {
            return wwe/TAhz.SWCt = swCt;
        }
        
        static int hPxZ(final wwe/TAhz wwe/TAhz, final int nnlx) {
            return wwe/TAhz.nnlx = nnlx;
        }
        
        static int fTPm(final wwe/TAhz wwe/TAhz, final int iauq) {
            return wwe/TAhz.iauq = iauq;
        }
        
        static byte[] ytXa(final wwe/TAhz wwe/TAhz, final byte[] fNfv) {
            return wwe/TAhz.fNfv = fNfv;
        }
        
        static int YWzv(final wwe/TAhz wwe/TAhz, final int pnlj) {
            return wwe/TAhz.pnlj = pnlj;
        }
        
        static int aaDG(final wwe/TAhz wwe/TAhz, final int rong) {
            return wwe/TAhz.Rong = rong;
        }
        
        static int xknu(final wwe/TAhz wwe/TAhz, final int nNsP) {
            return wwe/TAhz.nNsP = nNsP;
        }
        
        static int KPug(final wwe/TAhz wwe/TAhz, final int xzSf) {
            return wwe/TAhz.XZSf = xzSf;
        }
        
        static int yxnl(final wwe/TAhz wwe/TAhz, final int cBqY) {
            return wwe/TAhz.CBqY = cBqY;
        }
        
        static boolean KzUM(final wwe/TAhz wwe/TAhz, final boolean hnoQ) {
            return wwe/TAhz.HnoQ = hnoQ;
        }
        
        static boolean kqBe(final wwe/TAhz wwe/TAhz, final boolean csTs) {
            return wwe/TAhz.cSTs = csTs;
        }
        
        static boolean nfrI(final wwe/TAhz wwe/TAhz, final boolean rSbt) {
            return wwe/TAhz.RSbt = rSbt;
        }
        
        static int hDEv(final wwe/TAhz wwe/TAhz, final int bgsT) {
            return wwe/TAhz.bgsT = bgsT;
        }
    }
    
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
}
