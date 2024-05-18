package wwe;

import java.util.*;

public class FHaQ extends HAxG
{
    public FHaQ() {
        super("value");
    }
    
    @Override
    public void lpzH(final String s, final String[] array) {
        try {
            if (array.length < 3) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            qMIe qmIe = null;
            for (final qMIe qmIe2 : vlwv.hNxW.xRen()) {
                if (qmIe2.czUH().replace(" ", "_").equalsIgnoreCase(array[0])) {
                    qmIe = qmIe2;
                }
                if (FHaQ.\u2003\u2001\u200d) {
                    throw null;
                }
            }
            if (qmIe == null) {
                HAxG.TuzD(this.ZlRY());
                return;
            }
            for (final IcRI<Double> icRI : vlwv.tiEw) {
                if (icRI.MATs()) {
                    if (qmIe.czUH().equalsIgnoreCase(icRI.lJZv.czUH()) && icRI.VQBN.replace(" ", "_").equalsIgnoreCase(array[1])) {
                        icRI.NdCc(Double.parseDouble(array[2]));
                        HAxG.TuzD("Changed " + qmIe.czUH() + "'s value \"" + icRI.VQBN + "\" to " + Double.parseDouble(array[2]));
                        if (FHaQ.\u2003\u2001\u200d) {
                            throw null;
                        }
                        break;
                    }
                }
                else if (icRI.wnpo()) {
                    if (qmIe.czUH().equalsIgnoreCase(icRI.lJZv.czUH()) && icRI.VQBN.replace(" ", "_").equalsIgnoreCase(array[1])) {
                        icRI.NdCc((Double)(Object)Boolean.valueOf(Boolean.parseBoolean(array[2])));
                        HAxG.TuzD("Changed " + qmIe.czUH() + "'s value \"" + icRI.VQBN + "\" to " + Boolean.parseBoolean(array[2]));
                        if (FHaQ.\u2003\u2001\u200d) {
                            throw null;
                        }
                        break;
                    }
                }
                else if (icRI.dTln() && qmIe.czUH().equalsIgnoreCase(icRI.lJZv.czUH()) && icRI.VQBN.replace(" ", "_").equalsIgnoreCase(array[1])) {
                    icRI.NdCc((Double)s.substring(7 + qmIe.czUH().length() + 1 + icRI.VQBN.replace(" ", "_").length()));
                    HAxG.TuzD("Changed " + qmIe.czUH() + "'s value \"" + icRI.VQBN + "\" to " + s.substring(7 + qmIe.czUH().length() + 1 + icRI.VQBN.replace(" ", "_").length()));
                    if (FHaQ.\u2003\u2001\u200d) {
                        throw null;
                    }
                    break;
                }
                if (FHaQ.\u2003\u2001\u200d) {
                    throw null;
                }
            }
            HAxG.TuzD(this.ZlRY());
            HAxG.TuzD("");
            HAxG.TuzD(this.WKzX());
            if (FHaQ.\u2003\u2001\u200d) {
                throw null;
            }
        }
        catch (Exception ex) {
            this.kVjF(ex);
        }
    }
    
    @Override
    public String UiIp() {
        return "Sets a value of one of the settings";
    }
    
    @Override
    public String ZlRY() {
        return "value module_name name_of_setting_with_no_spaces some_value";
    }
    
    public String WKzX() {
        return "Try replacing the setting's or module's spaces in the name with a _";
    }
}
