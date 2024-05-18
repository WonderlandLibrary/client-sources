package wwe;

import java.util.*;

public class DhFe
{
    public static ArrayList<HAxG> ACXD;
    public static String itOx;
    
    public DhFe() {
        super();
        DhFe.ACXD.clear();
        DhFe.ACXD.add(new YRhC());
        DhFe.ACXD.add(new KryP());
        DhFe.ACXD.add(new xdbD());
        DhFe.ACXD.add(new THBI());
        DhFe.ACXD.add(new LVuv());
        DhFe.ACXD.add(new qCQk());
        DhFe.ACXD.add(new lRpE());
        DhFe.ACXD.add(new heAY());
        DhFe.ACXD.add(new MLmK());
        DhFe.ACXD.add(new KyAq());
        DhFe.ACXD.add(new FHaQ());
        DhFe.ACXD.add(new ytfU());
        DhFe.ACXD.add(new PbYr());
        DhFe.ACXD.add(new lRvb());
        DhFe.ACXD.add(new MkWg());
        if (!Boolean.parseBoolean(System.getProperty("disablebaritone"))) {
            DhFe.ACXD.add(new vmmZ());
        }
        DhFe.ACXD.add(new BcuG());
        DhFe.ACXD.add(new Tmzv());
        DhFe.ACXD.add(new ddkD());
        DhFe.ACXD.add(new LmJc());
        DhFe.ACXD.add(new WhjY());
        DhFe.ACXD.add(new Bjuw());
        DhFe.ACXD.add(new leIC());
        DhFe.ACXD.add(new VCSW());
        DhFe.ACXD.add(new pOnu());
    }
    
    public void RRWX(final String s) {
        if (!s.contains(DhFe.itOx) || !s.startsWith(DhFe.itOx)) {
            return;
        }
        final String trim = s.trim().substring(DhFe.itOx.length()).trim();
        final boolean contains = trim.trim().contains(" ");
        String trim2;
        if (contains) {
            trim2 = trim.split(" ")[0];
            if (DhFe.\u2004\u2008\u200d\u2007\u200d) {
                throw null;
            }
        }
        else {
            trim2 = trim.trim();
        }
        final String s2 = trim2;
        String[] array;
        if (contains) {
            array = trim.substring(s2.length()).trim().split(" ");
            if (DhFe.\u2004\u2008\u200d\u2007\u200d) {
                throw null;
            }
        }
        else {
            array = new String[0];
        }
        final String[] array2 = array;
        for (final HAxG hAxG : DhFe.ACXD) {
            if (hAxG.thqu().trim().equalsIgnoreCase(s2.trim())) {
                hAxG.lpzH(trim, array2);
                if (DhFe.\u2004\u2008\u200d\u2007\u200d) {
                    throw null;
                }
                break;
            }
            else {
                if (DhFe.\u2004\u2008\u200d\u2007\u200d) {
                    throw null;
                }
                continue;
            }
        }
        HAxG.TuzD("Invalid command. Try .help for a list of commands.");
    }
    
    static {
        DhFe.ACXD = new ArrayList<HAxG>();
        DhFe.itOx = ".";
    }
}
