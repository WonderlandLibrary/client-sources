package net.futureclient.client;

import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

public class xE extends dg<cD>
{
    public xE() {
        super();
        this.k = (List<T>)new ArrayList<Object>();
    }
    
    @Override
    public void e(final Object o) {
        this.M((cD)o);
    }
    
    public cD M(final String s) {
        for (final cD cd : this.k) {
            if (s.equalsIgnoreCase(cd.M())) {
                return cd;
            }
        }
        return null;
    }
    
    @Override
    public void M(final cD cd) {
        super.e(cd);
    }
}
