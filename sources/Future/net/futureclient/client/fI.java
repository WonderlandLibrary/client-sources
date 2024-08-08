package net.futureclient.client;

import java.util.Iterator;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class fI implements F
{
    private a k;
    
    public fI(final a k) {
        super();
        this.k = k;
    }
    
    @Override
    public CH M(final List<String> list, final String s) {
        return this.M(list, s, (Comparator<CH>)new Bh());
    }
    
    @Override
    public CH M(final List<String> list, final String s, final Comparator<CH> comparator) {
        if (list.size() == 0) {
            return null;
        }
        final List<CH> m = this.M(list, s);
        final int n = 0;
        final List<CH> list2 = m;
        Collections.<Object>sort((List<Object>)list2, (Comparator<? super Object>)comparator);
        return list2.get(n);
    }
    
    @Override
    public List<CH> M(final List<String> list, final String s) {
        final ArrayList<CH> list2 = new ArrayList<CH>();
        final Iterator<String> iterator2;
        Iterator<String> iterator = iterator2 = list.iterator();
        while (iterator.hasNext()) {
            final String s2 = iterator2.next();
            iterator = iterator2;
            list2.add(new CH(s2, this.k.M(s2, s)));
        }
        return list2;
    }
    
    @Override
    public double M(final String s, final String s2) {
        return this.k.M(s, s2);
    }
}
