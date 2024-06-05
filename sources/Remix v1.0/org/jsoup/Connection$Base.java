package org.jsoup;

import java.net.*;
import java.util.*;

public interface Base<T extends Base>
{
    URL url();
    
    T url(final URL p0);
    
    Method method();
    
    T method(final Method p0);
    
    String header(final String p0);
    
    T header(final String p0, final String p1);
    
    boolean hasHeader(final String p0);
    
    boolean hasHeaderWithValue(final String p0, final String p1);
    
    T removeHeader(final String p0);
    
    Map<String, String> headers();
    
    String cookie(final String p0);
    
    T cookie(final String p0, final String p1);
    
    boolean hasCookie(final String p0);
    
    T removeCookie(final String p0);
    
    Map<String, String> cookies();
}
