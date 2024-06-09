package com.client.glowclient;

import java.util.*;

public class Kc implements Comparator<yA>
{
    public final GB b;
    
    @Override
    public int compare(final Object o, final Object o2) {
        return this.M((yA)o, (yA)o2);
    }
    
    public Kc(final GB b) {
        this.b = b;
        super();
    }
    
    public int M(final yA ya, final yA ya2) {
        return ya2.M().compareTo(ya.M());
    }
}
