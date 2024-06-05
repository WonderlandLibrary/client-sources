package org.jsoup;

import org.jsoup.nodes.*;
import java.io.*;

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
