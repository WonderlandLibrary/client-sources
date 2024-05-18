package wwe;

import net.minecraft.client.gui.*;
import wwe.modules.combat.*;
import wwe.utils.*;

public class lRpE extends HAxG
{
    public GuiScreen cCpj;
    
    public lRpE() {
        super("LogOutSpot");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            if (array.length < 1) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            if (this.wJNH(array[0])) {
                if (array.length < 2) {
                    HAxG.TuzD(this.ZlRY());
                    return;
                }
                int i = 0;
                while (i < LogOutSpot.zEsy.size()) {
                    if (LogOutSpot.zEsy.get(i).AAtC.replace(" ", "_").equalsIgnoreCase(array[1])) {
                        LogOutSpot.zEsy.remove(i);
                        HAxG.TuzD("Removed " + array[1] + "'s log out spot");
                        if (lRpE.\u2002\u2001\u200f\u2004\u2006\u2006\u2004\u2002\u200d) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++i;
                        if (lRpE.\u2002\u2001\u200f\u2004\u2006\u2006\u2004\u2002\u200d) {
                            throw null;
                        }
                        continue;
                    }
                }
                HAxG.TuzD("Did not find a log out spot with the name, " + array[1]);
                if (lRpE.\u2002\u2001\u200f\u2004\u2006\u2006\u2004\u2002\u200d) {
                    throw null;
                }
            }
            else if (array[0].equalsIgnoreCase("clear")) {
                LogOutSpot.zEsy.clear();
                HAxG.TuzD("Cleared all log out spots");
                Wrapper.ypJK().stpV();
                if (lRpE.\u2002\u2001\u200f\u2004\u2006\u2006\u2004\u2002\u200d) {
                    throw null;
                }
            }
            else if (array[0].equalsIgnoreCase("open")) {
                if (array.length < 2) {
                    HAxG.TuzD(this.ZlRY());
                    return;
                }
                int j = 0;
                while (j < LogOutSpot.zEsy.size()) {
                    if (LogOutSpot.zEsy.get(j).AAtC.replace(" ", "_").equalsIgnoreCase(array[1])) {
                        this.cCpj = new sSoG(LogOutSpot.zEsy.get(j));
                        hSNE.lyvR(this);
                        if (lRpE.\u2002\u2001\u200f\u2004\u2006\u2006\u2004\u2002\u200d) {
                            throw null;
                        }
                        break;
                    }
                    else {
                        ++j;
                        if (lRpE.\u2002\u2001\u200f\u2004\u2006\u2006\u2004\u2002\u200d) {
                            throw null;
                        }
                        continue;
                    }
                }
                HAxG.TuzD(array[1] + " isent a log out spot");
                if (lRpE.\u2002\u2001\u200f\u2004\u2006\u2006\u2004\u2002\u200d) {
                    throw null;
                }
            }
            else {
                HAxG.TuzD(this.ZlRY());
            }
            if (lRpE.\u2002\u2001\u200f\u2004\u2006\u2006\u2004\u2002\u200d) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "Manage log out spot's";
    }
    
    @Override
    public String ZlRY() {
        return "LogOutSpot remove/clear/open";
    }
    
    @oqrr
    public void KnYY(final omqH omqH) {
        if (this.cCpj != null) {
            Wrapper.mc.displayGuiScreen(this.cCpj);
        }
        hSNE.kduK(this);
    }
}
