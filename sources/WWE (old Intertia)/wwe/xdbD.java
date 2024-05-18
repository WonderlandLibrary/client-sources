package wwe;

import org.lwjgl.input.*;
import wwe.utils.*;
import java.util.*;

public class xdbD extends HAxG
{
    public xdbD() {
        super("bind");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            if (array.length < 2) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            qMIe qmIe = null;
            for (final qMIe qmIe2 : vlwv.hNxW.ijZl) {
                if (qmIe2.czUH().replace(" ", "_").equalsIgnoreCase(array[0])) {
                    qmIe = qmIe2;
                    if (xdbD.\u2002\u200c\u2007\u200b\u2002\u2005\u2000\u2007\u200f) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (xdbD.\u2002\u200c\u2007\u200b\u2002\u2005\u2000\u2007\u200f) {
                        throw null;
                    }
                    continue;
                }
            }
            if (qmIe != null) {
                qmIe.hzzk(Keyboard.getKeyIndex(array[1].toUpperCase()));
                HAxG.TuzD(qmIe.czUH() + " bound to: " + Keyboard.getKeyName(qmIe.APjp()));
                Wrapper.ypJK().hUMI();
                if (xdbD.\u2002\u200c\u2007\u200b\u2002\u2005\u2000\u2007\u200f) {
                    throw null;
                }
            }
            else {
                HAxG.TuzD("Thats not a module?");
            }
            if (xdbD.\u2002\u200c\u2007\u200b\u2002\u2005\u2000\u2007\u200f) {
                throw null;
            }
        }
        catch (Exception ex) {
            HAxG.TuzD("Maybe try binding in the gui");
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "Binds a Module to a  set key";
    }
    
    @Override
    public String ZlRY() {
        return "bind Module key";
    }
}
