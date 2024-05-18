package wwe;

import wwe.modules.player.*;
import java.util.*;
import wwe.utils.*;

public static class wwe/DLei
{
    public sbfc uffc;
    public boolean nLvH;
    public Announcer HKtP;
    public String zVOT;
    public String[] ACbc;
    public String[] qFVg;
    public ArrayList<xbUc.wwe/DLei> NhFM;
    
    public wwe/DLei(final String zvot, final String[] array, final Announcer hKtP) {
        super();
        this.uffc = new sbfc();
        this.zVOT = zvot;
        this.ACbc = array;
        this.HKtP = hKtP;
        this.nLvH = true;
        this.qFVg = array;
    }
    
    public boolean XSxB() {
        boolean b;
        if (this.nLvH && this.uffc.QOXG(this.HKtP.delay.OnGi() * 1000.0)) {
            b = true;
            if (wwe/DLei.\u2001\u200c\u2002\u200f\u200a\u2002\u2005\u2009\u2001) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public void ZHsM() {
        this.uffc.BBwj();
    }
    
    public void leSX(final boolean b) {
        if (this.XSxB() && b) {
            String replaceAll = this.ACbc[new Random().nextInt(this.ACbc.length)];
            if (this.NhFM != null) {
                int i = 0;
                while (i < this.NhFM.size()) {
                    final xbUc.wwe/DLei wwe/DLei = this.NhFM.get(i);
                    replaceAll = replaceAll.replaceAll(wwe/DLei.Plsy, wwe/DLei.JWSl);
                    ++i;
                    if (xbUc.wwe/DLei.\u2001\u200c\u2002\u200f\u200a\u2002\u2005\u2009\u2001) {
                        throw null;
                    }
                }
            }
            Wrapper.mc.player.sendChatMessage(replaceAll);
            this.ZHsM();
        }
    }
}
