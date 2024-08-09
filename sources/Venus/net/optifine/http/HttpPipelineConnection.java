/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.http;

import java.io.BufferedOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Proxy;
import java.net.Socket;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Pattern;
import net.optifine.Config;
import net.optifine.http.HttpListener;
import net.optifine.http.HttpPipeline;
import net.optifine.http.HttpPipelineReceiver;
import net.optifine.http.HttpPipelineRequest;
import net.optifine.http.HttpPipelineSender;
import net.optifine.http.HttpRequest;
import net.optifine.http.HttpResponse;

public class HttpPipelineConnection {
    private String host = null;
    private int port = 0;
    private Proxy proxy = Proxy.NO_PROXY;
    private List<HttpPipelineRequest> listRequests = new LinkedList<HttpPipelineRequest>();
    private List<HttpPipelineRequest> listRequestsSend = new LinkedList<HttpPipelineRequest>();
    private Socket socket = null;
    private InputStream inputStream = null;
    private OutputStream outputStream = null;
    private HttpPipelineSender httpPipelineSender = null;
    private HttpPipelineReceiver httpPipelineReceiver = null;
    private int countRequests = 0;
    private boolean responseReceived = false;
    private long keepaliveTimeoutMs = 5000L;
    private int keepaliveMaxCount = 1000;
    private long timeLastActivityMs = System.currentTimeMillis();
    private boolean terminated = false;
    private static final String LF = "\n";
    public static final int TIMEOUT_CONNECT_MS = 5000;
    public static final int TIMEOUT_READ_MS = 5000;
    private static final Pattern patternFullUrl = Pattern.compile("^[a-zA-Z]+://.*");

    public HttpPipelineConnection(String string, int n) {
        this(string, n, Proxy.NO_PROXY);
    }

    public HttpPipelineConnection(String string, int n, Proxy proxy) {
        this.host = string;
        this.port = n;
        this.proxy = proxy;
        this.httpPipelineSender = new HttpPipelineSender(this);
        this.httpPipelineSender.start();
        this.httpPipelineReceiver = new HttpPipelineReceiver(this);
        this.httpPipelineReceiver.start();
    }

    public synchronized boolean addRequest(HttpPipelineRequest httpPipelineRequest) {
        if (this.isClosed()) {
            return true;
        }
        this.addRequest(httpPipelineRequest, this.listRequests);
        this.addRequest(httpPipelineRequest, this.listRequestsSend);
        ++this.countRequests;
        return false;
    }

    private void addRequest(HttpPipelineRequest httpPipelineRequest, List<HttpPipelineRequest> list) {
        list.add(httpPipelineRequest);
        this.notifyAll();
    }

    public synchronized void setSocket(Socket socket) throws IOException {
        if (!this.terminated) {
            if (this.socket != null) {
                throw new IllegalArgumentException("Already connected");
            }
            this.socket = socket;
            this.socket.setTcpNoDelay(false);
            this.inputStream = this.socket.getInputStream();
            this.outputStream = new BufferedOutputStream(this.socket.getOutputStream());
            this.onActivity();
            this.notifyAll();
        }
    }

    public synchronized OutputStream getOutputStream() throws IOException, InterruptedException {
        while (this.outputStream == null) {
            this.checkTimeout();
            this.wait(1000L);
        }
        return this.outputStream;
    }

    public synchronized InputStream getInputStream() throws IOException, InterruptedException {
        while (this.inputStream == null) {
            this.checkTimeout();
            this.wait(1000L);
        }
        return this.inputStream;
    }

    public synchronized HttpPipelineRequest getNextRequestSend() throws InterruptedException, IOException {
        if (this.listRequestsSend.size() <= 0 && this.outputStream != null) {
            this.outputStream.flush();
        }
        return this.getNextRequest(this.listRequestsSend, false);
    }

    public synchronized HttpPipelineRequest getNextRequestReceive() throws InterruptedException {
        return this.getNextRequest(this.listRequests, true);
    }

    private HttpPipelineRequest getNextRequest(List<HttpPipelineRequest> list, boolean bl) throws InterruptedException {
        while (list.size() <= 0) {
            this.checkTimeout();
            this.wait(1000L);
        }
        this.onActivity();
        return bl ? list.remove(0) : list.get(0);
    }

    private void checkTimeout() {
        if (this.socket != null) {
            long l;
            long l2 = this.keepaliveTimeoutMs;
            if (this.listRequests.size() > 0) {
                l2 = 5000L;
            }
            if ((l = System.currentTimeMillis()) > this.timeLastActivityMs + l2) {
                this.terminate(new InterruptedException("Timeout " + l2));
            }
        }
    }

    private void onActivity() {
        this.timeLastActivityMs = System.currentTimeMillis();
    }

    public synchronized void onRequestSent(HttpPipelineRequest httpPipelineRequest) {
        if (!this.terminated) {
            this.onActivity();
        }
    }

    public synchronized void onResponseReceived(HttpPipelineRequest httpPipelineRequest, HttpResponse httpResponse) {
        if (!this.terminated) {
            this.responseReceived = true;
            this.onActivity();
            if (this.listRequests.size() > 0 && this.listRequests.get(0) == httpPipelineRequest) {
                this.listRequests.remove(0);
                httpPipelineRequest.setClosed(false);
                String string = httpResponse.getHeader("Location");
                if (httpResponse.getStatus() / 100 == 3 && string != null && httpPipelineRequest.getHttpRequest().getRedirects() < 5) {
                    try {
                        string = this.normalizeUrl(string, httpPipelineRequest.getHttpRequest());
                        HttpRequest httpRequest = HttpPipeline.makeRequest(string, httpPipelineRequest.getHttpRequest().getProxy());
                        httpRequest.setRedirects(httpPipelineRequest.getHttpRequest().getRedirects() + 1);
                        HttpPipelineRequest httpPipelineRequest2 = new HttpPipelineRequest(httpRequest, httpPipelineRequest.getHttpListener());
                        HttpPipeline.addRequest(httpPipelineRequest2);
                    } catch (IOException iOException) {
                        httpPipelineRequest.getHttpListener().failed(httpPipelineRequest.getHttpRequest(), iOException);
                    }
                } else {
                    HttpListener httpListener = httpPipelineRequest.getHttpListener();
                    httpListener.finished(httpPipelineRequest.getHttpRequest(), httpResponse);
                }
                this.checkResponseHeader(httpResponse);
            } else {
                throw new IllegalArgumentException("Response out of order: " + httpPipelineRequest);
            }
        }
    }

    private String normalizeUrl(String string, HttpRequest httpRequest) {
        if (patternFullUrl.matcher(string).matches()) {
            return string;
        }
        if (string.startsWith("//")) {
            return "http:" + string;
        }
        Object object = httpRequest.getHost();
        if (httpRequest.getPort() != 80) {
            object = (String)object + ":" + httpRequest.getPort();
        }
        if (string.startsWith("/")) {
            return "http://" + (String)object + string;
        }
        String string2 = httpRequest.getFile();
        int n = string2.lastIndexOf("/");
        return n >= 0 ? "http://" + (String)object + string2.substring(0, n + 1) + string : "http://" + (String)object + "/" + string;
    }

    private void checkResponseHeader(HttpResponse httpResponse) {
        String string;
        String string2 = httpResponse.getHeader("Connection");
        if (string2 != null && !string2.toLowerCase().equals("keep-alive")) {
            this.terminate(new EOFException("Connection not keep-alive"));
        }
        if ((string = httpResponse.getHeader("Keep-Alive")) != null) {
            String[] stringArray = Config.tokenize(string, ",;");
            for (int i = 0; i < stringArray.length; ++i) {
                int n;
                String string3 = stringArray[i];
                String[] stringArray2 = this.split(string3, '=');
                if (stringArray2.length < 2) continue;
                if (stringArray2[0].equals("timeout") && (n = Config.parseInt(stringArray2[5], -1)) > 0) {
                    this.keepaliveTimeoutMs = n * 1000;
                }
                if (!stringArray2[0].equals("max") || (n = Config.parseInt(stringArray2[5], -1)) <= 0) continue;
                this.keepaliveMaxCount = n;
            }
        }
    }

    private String[] split(String string, char c) {
        int n = string.indexOf(c);
        if (n < 0) {
            return new String[]{string};
        }
        String string2 = string.substring(0, n);
        String string3 = string.substring(n + 1);
        return new String[]{string2, string3};
    }

    public synchronized void onExceptionSend(HttpPipelineRequest httpPipelineRequest, Exception exception) {
        this.terminate(exception);
    }

    public synchronized void onExceptionReceive(HttpPipelineRequest httpPipelineRequest, Exception exception) {
        this.terminate(exception);
    }

    private synchronized void terminate(Exception exception) {
        if (!this.terminated) {
            this.terminated = true;
            this.terminateRequests(exception);
            if (this.httpPipelineSender != null) {
                this.httpPipelineSender.interrupt();
            }
            if (this.httpPipelineReceiver != null) {
                this.httpPipelineReceiver.interrupt();
            }
            try {
                if (this.socket != null) {
                    this.socket.close();
                }
            } catch (IOException iOException) {
                // empty catch block
            }
            this.socket = null;
            this.inputStream = null;
            this.outputStream = null;
        }
    }

    private void terminateRequests(Exception exception) {
        if (this.listRequests.size() > 0) {
            HttpPipelineRequest httpPipelineRequest;
            if (!this.responseReceived) {
                httpPipelineRequest = this.listRequests.remove(0);
                httpPipelineRequest.getHttpListener().failed(httpPipelineRequest.getHttpRequest(), exception);
                httpPipelineRequest.setClosed(false);
            }
            while (this.listRequests.size() > 0) {
                httpPipelineRequest = this.listRequests.remove(0);
                HttpPipeline.addRequest(httpPipelineRequest);
            }
        }
    }

    public synchronized boolean isClosed() {
        if (this.terminated) {
            return false;
        }
        return this.countRequests >= this.keepaliveMaxCount;
    }

    public int getCountRequests() {
        return this.countRequests;
    }

    public synchronized boolean hasActiveRequests() {
        return this.listRequests.size() > 0;
    }

    public String getHost() {
        return this.host;
    }

    public int getPort() {
        return this.port;
    }

    public Proxy getProxy() {
        return this.proxy;
    }
}

