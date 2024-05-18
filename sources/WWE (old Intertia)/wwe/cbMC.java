package wwe;

import java.util.*;

class cbMC implements Comparator<qMIe>
{
    final hEGx qLlg;
    
    cbMC(final hEGx qLlg) {
        this.qLlg = qLlg;
        super();
    }
    
    public int XTrG(final qMIe qmIe, final qMIe qmIe2) {
        return qmIe.czUH().compareTo(qmIe2.czUH());
    }
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.XTrG((qMIe)o, (qMIe)o2);
    }
}
