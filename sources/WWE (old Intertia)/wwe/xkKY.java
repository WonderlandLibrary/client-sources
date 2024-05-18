package wwe;

import java.util.*;

class xkKY implements Comparator<qMIe>
{
    final FinI BgRz;
    
    xkKY(final FinI bgRz) {
        this.BgRz = bgRz;
        super();
    }
    
    public int arRV(final qMIe qmIe, final qMIe qmIe2) {
        return qmIe.czUH().compareTo(qmIe2.czUH());
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.arRV((qMIe)o, (qMIe)o2);
    }
}
