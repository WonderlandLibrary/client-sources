package wwe;

import java.util.*;
import java.awt.image.*;
import java.awt.*;
import java.net.*;
import java.io.*;

public class OWQp
{
    public static final int cUxh = 0;
    public static final int hezV = 1;
    public static final int YdFB = 2;
    protected BufferedInputStream pZdE;
    protected int QnZa;
    protected int QmBU;
    protected int Rwic;
    protected boolean rbLZ;
    protected int VxwT;
    protected int YcEw;
    protected int[] vCKh;
    protected int[] bfRV;
    protected int[] bZMR;
    protected int vzvV;
    protected int EDID;
    protected int HlEL;
    protected int JGgV;
    protected boolean bVVp;
    protected boolean flOr;
    protected int LzOG;
    protected int Bsml;
    protected int IjLf;
    protected int hLZM;
    protected int lcbI;
    protected Rectangle jprO;
    protected BufferedImage QyWb;
    protected BufferedImage pZHj;
    protected byte[] WAvL;
    protected int AJbw;
    protected int GYfi;
    protected int gJaG;
    protected boolean nFzZ;
    protected int CmLs;
    protected int mTCu;
    protected static final int sRjd = 4096;
    protected short[] joLf;
    protected byte[] chpz;
    protected byte[] qPUo;
    protected byte[] KwHG;
    protected ArrayList AxMn;
    protected int ilNa;
    public static final int cUxh = 0;
    public static final int hezV = 1;
    public static final int YdFB = 2;
    protected BufferedInputStream pZdE;
    protected int QnZa;
    protected int QmBU;
    protected int Rwic;
    protected boolean rbLZ;
    protected int VxwT;
    protected int YcEw;
    protected int[] vCKh;
    protected int[] bfRV;
    protected int[] bZMR;
    protected int vzvV;
    protected int EDID;
    protected int HlEL;
    protected int JGgV;
    protected boolean bVVp;
    protected boolean flOr;
    protected int LzOG;
    protected int Bsml;
    protected int IjLf;
    protected int hLZM;
    protected int lcbI;
    protected Rectangle jprO;
    protected BufferedImage QyWb;
    protected BufferedImage pZHj;
    protected byte[] WAvL;
    protected int AJbw;
    protected int GYfi;
    protected int gJaG;
    protected boolean nFzZ;
    protected int CmLs;
    protected int mTCu;
    protected static final int sRjd = 4096;
    protected short[] joLf;
    protected byte[] chpz;
    protected byte[] qPUo;
    protected byte[] KwHG;
    protected ArrayList AxMn;
    protected int ilNa;
    
    public OWQp() {
        super();
        this.YcEw = 1;
        this.WAvL = new byte[256];
        this.AJbw = 0;
        this.GYfi = 0;
        this.gJaG = 0;
        this.nFzZ = false;
        this.CmLs = 0;
    }
    
    public int woeO(final int n) {
        this.CmLs = -1;
        if (n >= 0 && n < this.ilNa) {
            this.CmLs = this.AxMn.get(n).htve;
        }
        return this.CmLs;
    }
    
    public int QZAG() {
        return this.ilNa;
    }
    
    public BufferedImage iGOD() {
        return this.PJIE(0);
    }
    
    public int skWS() {
        return this.YcEw;
    }
    
    protected void xBDM() {
        final int[] array = ((DataBufferInt)this.QyWb.getRaster().getDataBuffer()).getData();
        if (this.gJaG > 0) {
            if (this.gJaG == 3) {
                final int n = this.ilNa - 2;
                if (n > 0) {
                    this.pZHj = this.PJIE(n - 1);
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                }
                else {
                    this.pZHj = null;
                }
            }
            if (this.pZHj != null) {
                System.arraycopy(((DataBufferInt)this.pZHj.getRaster().getDataBuffer()).getData(), 0, array, 0, this.QmBU * this.Rwic);
                if (this.gJaG == 2) {
                    final Graphics2D graphics = this.QyWb.createGraphics();
                    Color color;
                    if (this.nFzZ) {
                        color = new Color(0, 0, 0, 0);
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                    }
                    else {
                        color = new Color(this.HlEL);
                    }
                    graphics.setColor(color);
                    graphics.setComposite(AlphaComposite.Src);
                    graphics.fill(this.jprO);
                    graphics.dispose();
                }
            }
        }
        int n2 = 1;
        int n3 = 8;
        int n4 = 0;
        int i = 0;
        while (i < this.lcbI) {
            int n5 = i;
            if (this.flOr) {
                if (n4 >= this.lcbI) {
                    switch (++n2) {
                        case 2: {
                            n4 = 4;
                            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                throw null;
                            }
                            break;
                        }
                        case 3: {
                            n4 = 2;
                            n3 = 4;
                            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                throw null;
                            }
                            break;
                        }
                        case 4: {
                            n4 = 1;
                            n3 = 2;
                            break;
                        }
                    }
                }
                n5 = n4;
                n4 += n3;
            }
            final int n6 = n5 + this.IjLf;
            if (n6 < this.Rwic) {
                final int n7 = n6 * this.QmBU;
                int j = n7 + this.Bsml;
                int n8 = j + this.hLZM;
                if (n7 + this.QmBU < n8) {
                    n8 = n7 + this.QmBU;
                }
                int n9 = i * this.hLZM;
                while (j < n8) {
                    final int n10 = this.bZMR[this.KwHG[n9++] & 0xFF];
                    if (n10 != 0) {
                        array[j] = n10;
                    }
                    ++j;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                }
            }
            ++i;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
    }
    
    public BufferedImage PJIE(final int n) {
        BufferedImage zTwe = null;
        if (n >= 0 && n < this.ilNa) {
            zTwe = this.AxMn.get(n).ZTwe;
        }
        return zTwe;
    }
    
    public Dimension koiv() {
        return new Dimension(this.QmBU, this.Rwic);
    }
    
    public int SKXs(final BufferedInputStream pZdE) {
        this.HCzI();
        if (pZdE != null) {
            this.pZdE = pZdE;
            this.IdGS();
            if (!this.uSmb()) {
                this.DYtw();
                if (this.ilNa < 0) {
                    this.QnZa = 1;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                }
            }
        }
        else {
            this.QnZa = 2;
        }
        try {
            pZdE.close();
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {}
        return this.QnZa;
    }
    
    public int HAHr(InputStream inputStream) {
        this.HCzI();
        if (inputStream != null) {
            if (!(inputStream instanceof BufferedInputStream)) {
                inputStream = new BufferedInputStream(inputStream);
            }
            this.pZdE = (BufferedInputStream)inputStream;
            this.IdGS();
            if (!this.uSmb()) {
                this.DYtw();
                if (this.ilNa < 0) {
                    this.QnZa = 1;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                }
            }
        }
        else {
            this.QnZa = 2;
        }
        try {
            inputStream.close();
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {}
        return this.QnZa;
    }
    
    public int xNcA(String lowerCase) {
        this.QnZa = 0;
        try {
            lowerCase = lowerCase.trim().toLowerCase();
            if (lowerCase.indexOf("file:") >= 0 || lowerCase.indexOf(":/") > 0) {
                this.pZdE = new BufferedInputStream(new URL(lowerCase).openStream());
                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                    throw null;
                }
            }
            else {
                this.pZdE = new BufferedInputStream(new FileInputStream(lowerCase));
            }
            this.QnZa = this.SKXs(this.pZdE);
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {
            this.QnZa = 2;
        }
        return this.QnZa;
    }
    
    protected void kXkV() {
        final int n = -1;
        final int n2 = this.hLZM * this.lcbI;
        if (this.KwHG == null || this.KwHG.length < n2) {
            this.KwHG = new byte[n2];
        }
        if (this.joLf == null) {
            this.joLf = new short[4096];
        }
        if (this.chpz == null) {
            this.chpz = new byte[4096];
        }
        if (this.qPUo == null) {
            this.qPUo = new byte[4097];
        }
        final int ylmr = this.YLMR();
        final int n3 = 1 << ylmr;
        final int n4 = n3 + 1;
        int n5 = n3 + 2;
        int n6 = n;
        int n7 = ylmr + 1;
        int n8 = (1 << n7) - 1;
        int i = 0;
        while (i < n3) {
            this.joLf[i] = 0;
            this.chpz[i] = (byte)i;
            ++i;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        final int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        int n14 = n9;
        int j = 0;
        while (j < n2) {
            if (n13 < n7) {
                int avyp = this.Avyp();
                if (avyp <= 0) {
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    break;
                }
                else {
                    int n15 = 0;
                    n14 += (this.WAvL[n15] & 0xFF) << n13;
                    n13 += 8;
                    ++n15;
                    --avyp;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            else {
                int k = n14 & n8;
                n14 >>= n7;
                n13 -= n7;
                if (k > n5) {
                    break;
                }
                if (k == n4) {
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    break;
                }
                else if (k == n3) {
                    n7 = ylmr + 1;
                    n8 = (1 << n7) - 1;
                    n5 = n3 + 2;
                    n6 = n;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    continue;
                }
                else if (n6 == n) {
                    this.qPUo[n11++] = this.chpz[k];
                    n6 = k;
                    n12 = k;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    continue;
                }
                else {
                    final int n16;
                    if ((n16 = k) == n5) {
                        this.qPUo[n11++] = (byte)n12;
                        k = n6;
                    }
                    while (k > n3) {
                        this.qPUo[n11++] = this.chpz[k];
                        k = this.joLf[k];
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                    }
                    n12 = (this.chpz[k] & 0xFF);
                    if (n5 >= 4096) {
                        this.qPUo[n11++] = (byte)n12;
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        this.qPUo[n11++] = (byte)n12;
                        this.joLf[n5] = (short)n6;
                        this.chpz[n5] = (byte)n12;
                        if ((++n5 & n8) == 0x0 && n5 < 4096) {
                            ++n7;
                            n8 += n5;
                        }
                        n6 = n16;
                        --n11;
                        this.KwHG[n10++] = this.qPUo[n11];
                        ++j;
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        continue;
                    }
                }
            }
        }
        int l = n10;
        while (l < n2) {
            this.KwHG[l] = 0;
            ++l;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
    }
    
    protected boolean uSmb() {
        boolean b;
        if (this.QnZa != 0) {
            b = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    protected void HCzI() {
        this.QnZa = 0;
        this.ilNa = 0;
        this.AxMn = new ArrayList();
        this.vCKh = null;
        this.bfRV = null;
    }
    
    protected int YLMR() {
        int read = 0;
        try {
            read = this.pZdE.read();
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {
            this.QnZa = 1;
        }
        return read;
    }
    
    protected int Avyp() {
        this.AJbw = this.YLMR();
        int i = 0;
        if (this.AJbw > 0) {
            try {
                while (i < this.AJbw) {
                    final int read = this.pZdE.read(this.WAvL, i, this.AJbw - i);
                    if (read == -1) {
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        i += read;
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        continue;
                    }
                }
                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                    throw null;
                }
            }
            catch (IOException ex) {}
            if (i < this.AJbw) {
                this.QnZa = 1;
            }
        }
        return i;
    }
    
    protected int[] OWRK(final int n) {
        final int n2 = 3 * n;
        int[] array = null;
        final byte[] array2 = new byte[n2];
        int read = 0;
        try {
            read = this.pZdE.read(array2);
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {}
        if (read < n2) {
            this.QnZa = 1;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            array = new int[256];
            int i = 0;
            int n3 = 0;
            while (i < n) {
                array[i++] = (0xFF000000 | (array2[n3++] & 0xFF) << 16 | (array2[n3++] & 0xFF) << 8 | (array2[n3++] & 0xFF));
                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                    throw null;
                }
            }
        }
        return array;
    }
    
    protected void DYtw() {
        while (!this.uSmb()) {
            Label_0301: {
                switch (this.YLMR()) {
                    case 44: {
                        this.DBPp();
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        break;
                    }
                    case 33: {
                        switch (this.YLMR()) {
                            case 249: {
                                this.YonV();
                                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                    throw null;
                                }
                                break Label_0301;
                            }
                            case 255: {
                                this.Avyp();
                                String string = "";
                                int i = 0;
                                while (i < 11) {
                                    string += (char)this.WAvL[i];
                                    ++i;
                                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                        throw null;
                                    }
                                }
                                if (string.equals("NETSCAPE2.0")) {
                                    this.RcKH();
                                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                        throw null;
                                    }
                                    break Label_0301;
                                }
                                else {
                                    this.kfxo();
                                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                        throw null;
                                    }
                                    break Label_0301;
                                }
                                break;
                            }
                            default: {
                                this.kfxo();
                                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                    throw null;
                                }
                                break Label_0301;
                            }
                        }
                        break;
                    }
                    case 59: {
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        break;
                    }
                    case 0: {
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        break;
                    }
                    default: {
                        this.QnZa = 1;
                        break;
                    }
                }
            }
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
    }
    
    protected void YonV() {
        this.YLMR();
        final int ylmr = this.YLMR();
        this.GYfi = (ylmr & 0x1C) >> 2;
        if (this.GYfi == 0) {
            this.GYfi = 1;
        }
        boolean nFzZ;
        if ((ylmr & 0x1) != 0x0) {
            nFzZ = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            nFzZ = false;
        }
        this.nFzZ = nFzZ;
        this.CmLs = this.WKkg() * 10;
        this.mTCu = this.YLMR();
        this.YLMR();
    }
    
    protected void IdGS() {
        String string = "";
        int i = 0;
        while (i < 6) {
            string += (char)this.YLMR();
            ++i;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        if (!string.startsWith("GIF")) {
            this.QnZa = 1;
            return;
        }
        this.ZDzX();
        if (this.rbLZ && !this.uSmb()) {
            this.vCKh = this.OWRK(this.VxwT);
            this.EDID = this.vCKh[this.vzvV];
        }
    }
    
    protected void DBPp() {
        this.Bsml = this.WKkg();
        this.IjLf = this.WKkg();
        this.hLZM = this.WKkg();
        this.lcbI = this.WKkg();
        final int ylmr = this.YLMR();
        boolean bvVp;
        if ((ylmr & 0x80) != 0x0) {
            bvVp = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            bvVp = false;
        }
        this.bVVp = bvVp;
        boolean flOr;
        if ((ylmr & 0x40) != 0x0) {
            flOr = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            flOr = false;
        }
        this.flOr = flOr;
        this.LzOG = 2 << (ylmr & 0x7);
        if (this.bVVp) {
            this.bfRV = this.OWRK(this.LzOG);
            this.bZMR = this.bfRV;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            this.bZMR = this.vCKh;
            if (this.vzvV == this.mTCu) {
                this.EDID = 0;
            }
        }
        int n = 0;
        if (this.nFzZ) {
            n = this.bZMR[this.mTCu];
            this.bZMR[this.mTCu] = 0;
        }
        if (this.bZMR == null) {
            this.QnZa = 1;
        }
        if (this.uSmb()) {
            return;
        }
        this.kXkV();
        this.kfxo();
        if (this.uSmb()) {
            return;
        }
        ++this.ilNa;
        this.QyWb = new BufferedImage(this.QmBU, this.Rwic, 3);
        this.xBDM();
        this.AxMn.add(new wwe/dRYV(this.QyWb, this.CmLs));
        if (this.nFzZ) {
            this.bZMR[this.mTCu] = n;
        }
        this.uOTB();
    }
    
    protected void ZDzX() {
        this.QmBU = this.WKkg();
        this.Rwic = this.WKkg();
        final int ylmr = this.YLMR();
        boolean rbLZ;
        if ((ylmr & 0x80) != 0x0) {
            rbLZ = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            rbLZ = false;
        }
        this.rbLZ = rbLZ;
        this.VxwT = 2 << (ylmr & 0x7);
        this.vzvV = this.YLMR();
        this.JGgV = this.YLMR();
    }
    
    protected void RcKH() {
        // 
        // This method could not be decompiled.
        // 
        // Could not show original bytecode, likely due to the same error.
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public OWQp() {
        super();
        this.YcEw = 1;
        this.WAvL = new byte[256];
        this.AJbw = 0;
        this.GYfi = 0;
        this.gJaG = 0;
        this.nFzZ = false;
        this.CmLs = 0;
    }
    
    public int woeO(final int n) {
        this.CmLs = -1;
        if (n >= 0 && n < this.ilNa) {
            this.CmLs = this.AxMn.get(n).htve;
        }
        return this.CmLs;
    }
    
    public int QZAG() {
        return this.ilNa;
    }
    
    public BufferedImage iGOD() {
        return this.PJIE(0);
    }
    
    public int skWS() {
        return this.YcEw;
    }
    
    protected void xBDM() {
        final int[] array = ((DataBufferInt)this.QyWb.getRaster().getDataBuffer()).getData();
        if (this.gJaG > 0) {
            if (this.gJaG == 3) {
                final int n = this.ilNa - 2;
                if (n > 0) {
                    this.pZHj = this.PJIE(n - 1);
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                }
                else {
                    this.pZHj = null;
                }
            }
            if (this.pZHj != null) {
                System.arraycopy(((DataBufferInt)this.pZHj.getRaster().getDataBuffer()).getData(), 0, array, 0, this.QmBU * this.Rwic);
                if (this.gJaG == 2) {
                    final Graphics2D graphics = this.QyWb.createGraphics();
                    Color color;
                    if (this.nFzZ) {
                        color = new Color(0, 0, 0, 0);
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                    }
                    else {
                        color = new Color(this.HlEL);
                    }
                    graphics.setColor(color);
                    graphics.setComposite(AlphaComposite.Src);
                    graphics.fill(this.jprO);
                    graphics.dispose();
                }
            }
        }
        int n2 = 1;
        int n3 = 8;
        int n4 = 0;
        int i = 0;
        while (i < this.lcbI) {
            int n5 = i;
            if (this.flOr) {
                if (n4 >= this.lcbI) {
                    switch (++n2) {
                        case 2: {
                            n4 = 4;
                            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                throw null;
                            }
                            break;
                        }
                        case 3: {
                            n4 = 2;
                            n3 = 4;
                            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                throw null;
                            }
                            break;
                        }
                        case 4: {
                            n4 = 1;
                            n3 = 2;
                            break;
                        }
                    }
                }
                n5 = n4;
                n4 += n3;
            }
            final int n6 = n5 + this.IjLf;
            if (n6 < this.Rwic) {
                final int n7 = n6 * this.QmBU;
                int j = n7 + this.Bsml;
                int n8 = j + this.hLZM;
                if (n7 + this.QmBU < n8) {
                    n8 = n7 + this.QmBU;
                }
                int n9 = i * this.hLZM;
                while (j < n8) {
                    final int n10 = this.bZMR[this.KwHG[n9++] & 0xFF];
                    if (n10 != 0) {
                        array[j] = n10;
                    }
                    ++j;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                }
            }
            ++i;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
    }
    
    public BufferedImage PJIE(final int n) {
        BufferedImage zTwe = null;
        if (n >= 0 && n < this.ilNa) {
            zTwe = this.AxMn.get(n).ZTwe;
        }
        return zTwe;
    }
    
    public Dimension koiv() {
        return new Dimension(this.QmBU, this.Rwic);
    }
    
    public int SKXs(final BufferedInputStream pZdE) {
        this.HCzI();
        if (pZdE != null) {
            this.pZdE = pZdE;
            this.IdGS();
            if (!this.uSmb()) {
                this.DYtw();
                if (this.ilNa < 0) {
                    this.QnZa = 1;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                }
            }
        }
        else {
            this.QnZa = 2;
        }
        try {
            pZdE.close();
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {}
        return this.QnZa;
    }
    
    public int HAHr(InputStream inputStream) {
        this.HCzI();
        if (inputStream != null) {
            if (!(inputStream instanceof BufferedInputStream)) {
                inputStream = new BufferedInputStream(inputStream);
            }
            this.pZdE = (BufferedInputStream)inputStream;
            this.IdGS();
            if (!this.uSmb()) {
                this.DYtw();
                if (this.ilNa < 0) {
                    this.QnZa = 1;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                }
            }
        }
        else {
            this.QnZa = 2;
        }
        try {
            inputStream.close();
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {}
        return this.QnZa;
    }
    
    public int xNcA(String lowerCase) {
        this.QnZa = 0;
        try {
            lowerCase = lowerCase.trim().toLowerCase();
            if (lowerCase.indexOf("file:") >= 0 || lowerCase.indexOf(":/") > 0) {
                this.pZdE = new BufferedInputStream(new URL(lowerCase).openStream());
                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                    throw null;
                }
            }
            else {
                this.pZdE = new BufferedInputStream(new FileInputStream(lowerCase));
            }
            this.QnZa = this.SKXs(this.pZdE);
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {
            this.QnZa = 2;
        }
        return this.QnZa;
    }
    
    protected void kXkV() {
        final int n = -1;
        final int n2 = this.hLZM * this.lcbI;
        if (this.KwHG == null || this.KwHG.length < n2) {
            this.KwHG = new byte[n2];
        }
        if (this.joLf == null) {
            this.joLf = new short[4096];
        }
        if (this.chpz == null) {
            this.chpz = new byte[4096];
        }
        if (this.qPUo == null) {
            this.qPUo = new byte[4097];
        }
        final int ylmr = this.YLMR();
        final int n3 = 1 << ylmr;
        final int n4 = n3 + 1;
        int n5 = n3 + 2;
        int n6 = n;
        int n7 = ylmr + 1;
        int n8 = (1 << n7) - 1;
        int i = 0;
        while (i < n3) {
            this.joLf[i] = 0;
            this.chpz[i] = (byte)i;
            ++i;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        final int n9 = 0;
        int n10 = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        int n14 = n9;
        int j = 0;
        while (j < n2) {
            if (n13 < n7) {
                int avyp = this.Avyp();
                if (avyp <= 0) {
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    break;
                }
                else {
                    int n15 = 0;
                    n14 += (this.WAvL[n15] & 0xFF) << n13;
                    n13 += 8;
                    ++n15;
                    --avyp;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    continue;
                }
            }
            else {
                int k = n14 & n8;
                n14 >>= n7;
                n13 -= n7;
                if (k > n5) {
                    break;
                }
                if (k == n4) {
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    break;
                }
                else if (k == n3) {
                    n7 = ylmr + 1;
                    n8 = (1 << n7) - 1;
                    n5 = n3 + 2;
                    n6 = n;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    continue;
                }
                else if (n6 == n) {
                    this.qPUo[n11++] = this.chpz[k];
                    n6 = k;
                    n12 = k;
                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                        throw null;
                    }
                    continue;
                }
                else {
                    final int n16;
                    if ((n16 = k) == n5) {
                        this.qPUo[n11++] = (byte)n12;
                        k = n6;
                    }
                    while (k > n3) {
                        this.qPUo[n11++] = this.chpz[k];
                        k = this.joLf[k];
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                    }
                    n12 = (this.chpz[k] & 0xFF);
                    if (n5 >= 4096) {
                        this.qPUo[n11++] = (byte)n12;
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        continue;
                    }
                    else {
                        this.qPUo[n11++] = (byte)n12;
                        this.joLf[n5] = (short)n6;
                        this.chpz[n5] = (byte)n12;
                        if ((++n5 & n8) == 0x0 && n5 < 4096) {
                            ++n7;
                            n8 += n5;
                        }
                        n6 = n16;
                        --n11;
                        this.KwHG[n10++] = this.qPUo[n11];
                        ++j;
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        continue;
                    }
                }
            }
        }
        int l = n10;
        while (l < n2) {
            this.KwHG[l] = 0;
            ++l;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
    }
    
    protected boolean uSmb() {
        boolean b;
        if (this.QnZa != 0) {
            b = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    protected void HCzI() {
        this.QnZa = 0;
        this.ilNa = 0;
        this.AxMn = new ArrayList();
        this.vCKh = null;
        this.bfRV = null;
    }
    
    protected int YLMR() {
        int read = 0;
        try {
            read = this.pZdE.read();
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {
            this.QnZa = 1;
        }
        return read;
    }
    
    protected int Avyp() {
        this.AJbw = this.YLMR();
        int i = 0;
        if (this.AJbw > 0) {
            try {
                while (i < this.AJbw) {
                    final int read = this.pZdE.read(this.WAvL, i, this.AJbw - i);
                    if (read == -1) {
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        i += read;
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        continue;
                    }
                }
                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                    throw null;
                }
            }
            catch (IOException ex) {}
            if (i < this.AJbw) {
                this.QnZa = 1;
            }
        }
        return i;
    }
    
    protected int[] OWRK(final int n) {
        final int n2 = 3 * n;
        int[] array = null;
        final byte[] array2 = new byte[n2];
        int read = 0;
        try {
            read = this.pZdE.read(array2);
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        catch (IOException ex) {}
        if (read < n2) {
            this.QnZa = 1;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            array = new int[256];
            int i = 0;
            int n3 = 0;
            while (i < n) {
                array[i++] = (0xFF000000 | (array2[n3++] & 0xFF) << 16 | (array2[n3++] & 0xFF) << 8 | (array2[n3++] & 0xFF));
                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                    throw null;
                }
            }
        }
        return array;
    }
    
    protected void DYtw() {
        while (!this.uSmb()) {
            Label_0301: {
                switch (this.YLMR()) {
                    case 44: {
                        this.DBPp();
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        break;
                    }
                    case 33: {
                        switch (this.YLMR()) {
                            case 249: {
                                this.YonV();
                                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                    throw null;
                                }
                                break Label_0301;
                            }
                            case 255: {
                                this.Avyp();
                                String string = "";
                                int i = 0;
                                while (i < 11) {
                                    string += (char)this.WAvL[i];
                                    ++i;
                                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                        throw null;
                                    }
                                }
                                if (string.equals("NETSCAPE2.0")) {
                                    this.RcKH();
                                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                        throw null;
                                    }
                                    break Label_0301;
                                }
                                else {
                                    this.kfxo();
                                    if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                        throw null;
                                    }
                                    break Label_0301;
                                }
                                break;
                            }
                            default: {
                                this.kfxo();
                                if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                                    throw null;
                                }
                                break Label_0301;
                            }
                        }
                        break;
                    }
                    case 59: {
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        break;
                    }
                    case 0: {
                        if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                            throw null;
                        }
                        break;
                    }
                    default: {
                        this.QnZa = 1;
                        break;
                    }
                }
            }
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
    }
    
    protected void YonV() {
        this.YLMR();
        final int ylmr = this.YLMR();
        this.GYfi = (ylmr & 0x1C) >> 2;
        if (this.GYfi == 0) {
            this.GYfi = 1;
        }
        boolean nFzZ;
        if ((ylmr & 0x1) != 0x0) {
            nFzZ = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            nFzZ = false;
        }
        this.nFzZ = nFzZ;
        this.CmLs = this.WKkg() * 10;
        this.mTCu = this.YLMR();
        this.YLMR();
    }
    
    protected void IdGS() {
        String string = "";
        int i = 0;
        while (i < 6) {
            string += (char)this.YLMR();
            ++i;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        if (!string.startsWith("GIF")) {
            this.QnZa = 1;
            return;
        }
        this.ZDzX();
        if (this.rbLZ && !this.uSmb()) {
            this.vCKh = this.OWRK(this.VxwT);
            this.EDID = this.vCKh[this.vzvV];
        }
    }
    
    protected void DBPp() {
        this.Bsml = this.WKkg();
        this.IjLf = this.WKkg();
        this.hLZM = this.WKkg();
        this.lcbI = this.WKkg();
        final int ylmr = this.YLMR();
        boolean bvVp;
        if ((ylmr & 0x80) != 0x0) {
            bvVp = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            bvVp = false;
        }
        this.bVVp = bvVp;
        boolean flOr;
        if ((ylmr & 0x40) != 0x0) {
            flOr = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            flOr = false;
        }
        this.flOr = flOr;
        this.LzOG = 2 << (ylmr & 0x7);
        if (this.bVVp) {
            this.bfRV = this.OWRK(this.LzOG);
            this.bZMR = this.bfRV;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            this.bZMR = this.vCKh;
            if (this.vzvV == this.mTCu) {
                this.EDID = 0;
            }
        }
        int n = 0;
        if (this.nFzZ) {
            n = this.bZMR[this.mTCu];
            this.bZMR[this.mTCu] = 0;
        }
        if (this.bZMR == null) {
            this.QnZa = 1;
        }
        if (this.uSmb()) {
            return;
        }
        this.kXkV();
        this.kfxo();
        if (this.uSmb()) {
            return;
        }
        ++this.ilNa;
        this.QyWb = new BufferedImage(this.QmBU, this.Rwic, 3);
        this.xBDM();
        this.AxMn.add(new wwe/dRYV(this.QyWb, this.CmLs));
        if (this.nFzZ) {
            this.bZMR[this.mTCu] = n;
        }
        this.uOTB();
    }
    
    protected void ZDzX() {
        this.QmBU = this.WKkg();
        this.Rwic = this.WKkg();
        final int ylmr = this.YLMR();
        boolean rbLZ;
        if ((ylmr & 0x80) != 0x0) {
            rbLZ = true;
            if (OWQp.\u2008\u200e\u2001\u200a\u2002\u2005\u2009\u2007) {
                throw null;
            }
        }
        else {
            rbLZ = false;
        }
        this.rbLZ = rbLZ;
        this.VxwT = 2 << (ylmr & 0x7);
        this.vzvV = this.YLMR();
        this.JGgV = this.YLMR();
    }
    
    protected void RcKH() {
        do {
            this.Avyp();
            if (this.WAvL[0] == 1) {
                this.YcEw = ((this.WAvL[2] & 0xFF) << 8 | (this.WAvL[1] & 0xFF));
            }
        } while (this.AJbw > 0 && !this.uSmb());
    }
    
    protected int WKkg() {
        return this.YLMR() | this.YLMR() << 8;
    }
    
    protected void uOTB() {
        this.gJaG = this.GYfi;
        this.jprO = new Rectangle(this.Bsml, this.IjLf, this.hLZM, this.lcbI);
        this.pZHj = this.QyWb;
        this.HlEL = this.EDID;
        this.bfRV = null;
    }
    
    protected void kfxo() {
        do {
            this.Avyp();
        } while (this.AJbw > 0 && !this.uSmb());
    }
    
    static class wwe/dRYV
    {
        public BufferedImage ZTwe;
        public int htve;
        
        public wwe/dRYV(final BufferedImage zTwe, final int htve) {
            super();
            this.ZTwe = zTwe;
            this.htve = htve;
        }
    }
}
