package wwe.modules.world;

import wwe.modules.*;
import java.util.*;
import wwe.*;
import wwe.utils.*;

public class NoteBotStealer extends qMIe
{
    public int mbRf;
    public static ajLR DbuO;
    public static String zTtz;
    
    public NoteBotStealer() {
        super("Note Bot Stealer", "Steals note bot songs so you can play them with note bot", 0, Category.WORLD);
    }
    
    @Override
    public void HrDr() {
        this.azsP(false);
    }
    
    @oqrr
    public void leGN(final omqH omqH) {
        ++this.mbRf;
    }
    
    @oqrr
    public void qsxv(final jWya jWya) {
        NoteBotStealer.DbuO.FgWb.add(new ajLR.wwe/WkII(this.mbRf, jWya.GFhL, jWya.njMC));
    }
    
    @Override
    public void LPxx() {
        NoteBotStealer.DbuO = new ajLR(new ArrayList<ajLR.wwe/ABpb>(), new ArrayList<String>(), new ArrayList<ajLR.wwe/WkII>());
        this.mbRf = 0;
        hSNE.lyvR(this);
    }
    
    @Override
    public void qBmy() {
        this.mbRf = 0;
        hSNE.kduK(this);
        Wrapper.ypJK().HLif(NoteBotStealer.zTtz, NoteBotStealer.DbuO, false);
    }
    
    static {
        NoteBotStealer.DbuO = new ajLR(null, null, null);
        NoteBotStealer.zTtz = "default";
    }
}
