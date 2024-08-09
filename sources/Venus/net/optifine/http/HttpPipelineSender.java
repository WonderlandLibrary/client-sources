/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.http;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.Charset;
import java.util.Map;
import net.optifine.http.HttpPipelineConnection;
import net.optifine.http.HttpPipelineRequest;
import net.optifine.http.HttpRequest;

public class HttpPipelineSender
extends Thread {
    private HttpPipelineConnection httpPipelineConnection = null;
    private static final String CRLF = "\r\n";
    private static Charset ASCII = Charset.forName("ASCII");

    public HttpPipelineSender(HttpPipelineConnection httpPipelineConnection) {
        super("HttpPipelineSender");
        this.httpPipelineConnection = httpPipelineConnection;
    }

    @Override
    public void run() {
        HttpPipelineRequest httpPipelineRequest = null;
        try {
            this.connect();
            while (!Thread.interrupted()) {
                httpPipelineRequest = this.httpPipelineConnection.getNextRequestSend();
                HttpRequest httpRequest = httpPipelineRequest.getHttpRequest();
                OutputStream outputStream = this.httpPipelineConnection.getOutputStream();
                this.writeRequest(httpRequest, outputStream);
                this.httpPipelineConnection.onRequestSent(httpPipelineRequest);
            }
        } catch (InterruptedException interruptedException) {
            return;
        } catch (Exception exception) {
            this.httpPipelineConnection.onExceptionSend(httpPipelineRequest, exception);
        }
    }

    private void connect() throws IOException {
        String string = this.httpPipelineConnection.getHost();
        int n = this.httpPipelineConnection.getPort();
        Proxy proxy = this.httpPipelineConnection.getProxy();
        Socket socket = new Socket(proxy);
        socket.connect(new InetSocketAddress(string, n), 5000);
        this.httpPipelineConnection.setSocket(socket);
    }

    private void writeRequest(HttpRequest httpRequest, OutputStream outputStream) throws IOException {
        this.write(outputStream, httpRequest.getMethod() + " " + httpRequest.getFile() + " " + httpRequest.getHttp() + CRLF);
        Map<String, String> map = httpRequest.getHeaders();
        for (String string : map.keySet()) {
            String string2 = httpRequest.getHeaders().get(string);
            this.write(outputStream, string + ": " + string2 + CRLF);
        }
        this.write(outputStream, CRLF);
    }

    private void write(OutputStream outputStream, String string) throws IOException {
        byte[] byArray = string.getBytes(ASCII);
        outputStream.write(byArray);
    }
}

