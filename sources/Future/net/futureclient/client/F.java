package net.futureclient.client;

import java.util.Comparator;
import java.util.List;

public interface F
{
    List<CH> M(final List<String> p0, final String p1);
    
    CH M(final List<String> p0, final String p1, final Comparator<CH> p2);
    
    double M(final String p0, final String p1);
    
    CH M(final List<String> p0, final String p1);
}
