package com.client.glowclient;

import java.util.*;

public class Z implements Comparator<X>
{
    @Override
    public int compare(final Object o, final Object o2) {
        return this.M((X)o, (X)o2);
    }
    
    public int M(final X x, final X x2) {
        final int n = x.M().getMaxLevel() - x.M().getMinLevel();
        final int n2 = x2.M().getMaxLevel() - x2.M().getMinLevel();
        if (n == n2) {
            return 0;
        }
        if (n < n2) {
            return 1;
        }
        return -1;
    }
    
    public Z() {
        super();
    }
}
