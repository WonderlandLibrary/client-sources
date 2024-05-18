package wwe;

import java.util.*;

class mQzr implements Comparator<qMIe>
{
    final cRUT jXTN;
    
    mQzr(final cRUT jxtn) {
        this.jXTN = jxtn;
        super();
    }
    
    public int jfbG(final qMIe qmIe, final qMIe qmIe2) {
        return qmIe.czUH().compareTo(qmIe2.czUH());
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.jfbG((qMIe)o, (qMIe)o2);
    }
}
