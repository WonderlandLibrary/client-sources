package org.jsoup;

import java.net.*;
import java.util.*;
import org.jsoup.parser.*;
import org.jsoup.nodes.*;
import java.io.*;

public interface Connection
{
    Connection url(final URL p0);
    
    Connection url(final String p0);
    
    Connection proxy(final Proxy p0);
    
    Connection proxy(final String p0, final int p1);
    
    Connection userAgent(final String p0);
    
    Connection timeout(final int p0);
    
    Connection maxBodySize(final int p0);
    
    Connection referrer(final String p0);
    
    Connection followRedirects(final boolean p0);
    
    Connection method(final Method p0);
    
    Connection ignoreHttpErrors(final boolean p0);
    
    Connection ignoreContentType(final boolean p0);
    
    Connection validateTLSCertificates(final boolean p0);
    
    Connection data(final String p0, final String p1);
    
    Connection data(final String p0, final String p1, final InputStream p2);
    
    Connection data(final Collection<KeyVal> p0);
    
    Connection data(final Map<String, String> p0);
    
    Connection data(final String... p0);
    
    KeyVal data(final String p0);
    
    Connection requestBody(final String p0);
    
    Connection header(final String p0, final String p1);
    
    Connection headers(final Map<String, String> p0);
    
    Connection cookie(final String p0, final String p1);
    
    Connection cookies(final Map<String, String> p0);
    
    Connection parser(final Parser p0);
    
    Connection postDataCharset(final String p0);
    
    Document get() throws IOException;
    
    Document post() throws IOException;
    
    Response execute() throws IOException;
    
    Request request();
    
    Connection request(final Request p0);
    
    Response response();
    
    Connection response(final Response p0);
    
    public enum Method
    {
        GET(false), 
        POST(true), 
        PUT(true), 
        DELETE(false), 
        PATCH(true), 
        HEAD(false), 
        OPTIONS(false), 
        TRACE(false);
        
        private final boolean hasBody;
        
        private Method(final boolean hasBody) {
            this.hasBody = hasBody;
        }
        
        public final boolean hasBody() {
            return this.hasBody;
        }
    }
    
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
    
    public interface Response extends Base<Response>
    {
        int statusCode();
        
        String statusMessage();
        
        String charset();
        
        Response charset(final String p0);
        
        String contentType();
        
        Document parse() throws IOException;
        
        String body();
        
        byte[] bodyAsBytes();
    }
    
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
}
