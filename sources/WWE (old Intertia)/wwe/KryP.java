package wwe;

import java.util.*;

public class KryP extends HAxG
{
    public KryP() {
        super("toggle");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            if (array.length < 1) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            qMIe qmIe = null;
            for (final qMIe qmIe2 : vlwv.hNxW.ijZl) {
                if (qmIe2.czUH().replace(" ", "_").equalsIgnoreCase(array[0])) {
                    qmIe = qmIe2;
                    if (KryP.\u200e\u200d\u2003\u200e\u2001\u200d\u2003\u2008) {
                        throw null;
                    }
                    break;
                }
                else {
                    if (KryP.\u200e\u200d\u2003\u200e\u2001\u200d\u2003\u2008) {
                        throw null;
                    }
                    continue;
                }
            }
            if (qmIe != null) {
                qmIe.GDVp();
                final StringBuilder append = new StringBuilder().append("Toggled ").append(qmIe.czUH()).append(" (");
                String s2;
                if (qmIe.IuiN()) {
                    s2 = "on";
                    if (KryP.\u200e\u200d\u2003\u200e\u2001\u200d\u2003\u2008) {
                        throw null;
                    }
                }
                else {
                    s2 = "off";
                }
                HAxG.TuzD(append.append(s2).append(")").toString());
                if (KryP.\u200e\u200d\u2003\u200e\u2001\u200d\u2003\u2008) {
                    throw null;
                }
            }
            else {
                HAxG.TuzD("Thats not a module?");
            }
            if (KryP.\u200e\u200d\u2003\u200e\u2001\u200d\u2003\u2008) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "Toggles a module";
    }
    
    @Override
    public String ZlRY() {
        return "Toggle name of module";
    }
}
