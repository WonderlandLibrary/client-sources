package optifine;

import java.nio.charset.*;
import java.net.*;
import java.io.*;
import java.util.*;

public class HttpPipelineSender extends Thread
{
    private static final String CRLF = "\r\n";
    private static Charset ASCII;
    private HttpPipelineConnection httpPipelineConnection;
    
    public HttpPipelineSender(final HttpPipelineConnection httpPipelineConnection) {
        super("HttpPipelineSender");
        this.httpPipelineConnection = null;
        this.httpPipelineConnection = httpPipelineConnection;
    }
    
    @Override
    public void run() {
        HttpPipelineRequest hpr = null;
        try {
            this.connect();
            while (!Thread.interrupted()) {
                hpr = this.httpPipelineConnection.getNextRequestSend();
                final HttpRequest e = hpr.getHttpRequest();
                final OutputStream out = this.httpPipelineConnection.getOutputStream();
                this.writeRequest(e, out);
                this.httpPipelineConnection.onRequestSent(hpr);
            }
        }
        catch (InterruptedException var6) {}
        catch (Exception var5) {
            this.httpPipelineConnection.onExceptionSend(hpr, var5);
        }
    }
    
    private void connect() throws IOException {
        final String host = this.httpPipelineConnection.getHost();
        final int port = this.httpPipelineConnection.getPort();
        final Proxy proxy = this.httpPipelineConnection.getProxy();
        final Socket socket = new Socket(proxy);
        socket.connect(new InetSocketAddress(host, port), 5000);
        this.httpPipelineConnection.setSocket(socket);
    }
    
    private void writeRequest(final HttpRequest req, final OutputStream out) throws IOException {
        this.write(out, req.getMethod() + " " + req.getFile() + " " + req.getHttp() + "\r\n");
        final Map headers = req.getHeaders();
        final Set keySet = headers.keySet();
        for (final String key : keySet) {
            final String val = req.getHeaders().get(key);
            this.write(out, key + ": " + val + "\r\n");
        }
        this.write(out, "\r\n");
    }
    
    private void write(final OutputStream out, final String str) throws IOException {
        final byte[] bytes = str.getBytes(HttpPipelineSender.ASCII);
        out.write(bytes);
    }
    
    static {
        HttpPipelineSender.ASCII = Charset.forName("ASCII");
    }
}
