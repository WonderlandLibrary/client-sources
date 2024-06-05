package org.jsoup;

import java.net.*;
import java.util.*;
import org.jsoup.parser.*;

public interface Request extends Base<Request>
{
    Proxy proxy();
    
    Request proxy(final Proxy p0);
    
    Request proxy(final String p0, final int p1);
    
    int timeout();
    
    Request timeout(final int p0);
    
    int maxBodySize();
    
    Request maxBodySize(final int p0);
    
    boolean followRedirects();
    
    Request followRedirects(final boolean p0);
    
    boolean ignoreHttpErrors();
    
    Request ignoreHttpErrors(final boolean p0);
    
    boolean ignoreContentType();
    
    Request ignoreContentType(final boolean p0);
    
    boolean validateTLSCertificates();
    
    void validateTLSCertificates(final boolean p0);
    
    Request data(final KeyVal p0);
    
    Collection<KeyVal> data();
    
    Request requestBody(final String p0);
    
    String requestBody();
    
    Request parser(final Parser p0);
    
    Parser parser();
    
    Request postDataCharset(final String p0);
    
    String postDataCharset();
}
