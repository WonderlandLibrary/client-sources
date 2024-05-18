package wwe;

import java.util.*;

class OEBD implements Comparator<qMIe>
{
    final cRUT GCHD;
    
    OEBD(final cRUT gchd) {
        this.GCHD = gchd;
        super();
    }
    
    public int CMxe(final qMIe qmIe, final qMIe qmIe2) {
        return qmIe.czUH().compareTo(qmIe2.czUH());
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.CMxe((qMIe)o, (qMIe)o2);
    }
}
