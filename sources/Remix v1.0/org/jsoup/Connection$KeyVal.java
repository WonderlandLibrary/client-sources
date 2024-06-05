package org.jsoup;

import java.io.*;

public interface KeyVal
{
    KeyVal key(final String p0);
    
    String key();
    
    KeyVal value(final String p0);
    
    String value();
    
    KeyVal inputStream(final InputStream p0);
    
    InputStream inputStream();
    
    boolean hasInputStream();
}
