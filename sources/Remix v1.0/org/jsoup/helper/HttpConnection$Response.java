package org.jsoup.helper;

import java.nio.*;
import java.util.regex.*;
import org.jsoup.*;
import java.util.zip.*;
import org.jsoup.nodes.*;
import java.nio.charset.*;
import java.security.cert.*;
import javax.net.ssl.*;
import java.security.*;
import org.jsoup.parser.*;
import java.io.*;
import java.net.*;
import java.util.*;

public static class Response extends HttpConnection.Base<Connection.Response> implements Connection.Response
{
    private static final int MAX_REDIRECTS = 20;
    private static SSLSocketFactory sslSocketFactory;
    private static final String LOCATION = "Location";
    private int statusCode;
    private String statusMessage;
    private ByteBuffer byteData;
    private String charset;
    private String contentType;
    private boolean executed;
    private int numRedirects;
    private Connection.Request req;
    private static final Pattern xmlContentTypeRxp;
    
    Response() {
        this.executed = false;
        this.numRedirects = 0;
    }
    
    private Response(final Response previousResponse) throws IOException {
        this.executed = false;
        this.numRedirects = 0;
        if (previousResponse != null) {
            this.numRedirects = previousResponse.numRedirects + 1;
            if (this.numRedirects >= 20) {
                throw new IOException(String.format("Too many redirects occurred trying to load URL %s", previousResponse.url()));
            }
        }
    }
    
    static Response execute(final Connection.Request req) throws IOException {
        return execute(req, null);
    }
    
    static Response execute(final Connection.Request req, final Response previousResponse) throws IOException {
        Validate.notNull(req, "Request must not be null");
        final String protocol = req.url().getProtocol();
        if (!protocol.equals("http") && !protocol.equals("https")) {
            throw new MalformedURLException("Only http & https protocols supported");
        }
        final boolean methodHasBody = req.method().hasBody();
        final boolean hasRequestBody = req.requestBody() != null;
        if (!methodHasBody) {
            Validate.isFalse(hasRequestBody, "Cannot set a request body for HTTP method " + req.method());
        }
        String mimeBoundary = null;
        if (req.data().size() > 0 && (!methodHasBody || hasRequestBody)) {
            serialiseRequestUrl(req);
        }
        else if (methodHasBody) {
            mimeBoundary = setOutputContentType(req);
        }
        final HttpURLConnection conn = createConnection(req);
        Response res;
        try {
            conn.connect();
            if (conn.getDoOutput()) {
                writePost(req, conn.getOutputStream(), mimeBoundary);
            }
            final int status = conn.getResponseCode();
            res = new Response(previousResponse);
            res.setupFromConnection(conn, previousResponse);
            res.req = req;
            if (res.hasHeader("Location") && req.followRedirects()) {
                if (status != 307) {
                    req.method(Method.GET);
                    req.data().clear();
                    req.requestBody(null);
                    req.removeHeader("Content-Type");
                }
                String location = res.header("Location");
                if (location != null && location.startsWith("http:/") && location.charAt(6) != '/') {
                    location = location.substring(6);
                }
                final URL redir = StringUtil.resolve(req.url(), location);
                req.url(HttpConnection.access$200(redir));
                for (final Map.Entry<String, String> cookie : res.cookies.entrySet()) {
                    req.cookie(cookie.getKey(), cookie.getValue());
                }
                return execute(req, res);
            }
            if ((status < 200 || status >= 400) && !req.ignoreHttpErrors()) {
                throw new HttpStatusException("HTTP error fetching URL", status, req.url().toString());
            }
            final String contentType = res.contentType();
            if (contentType != null && !req.ignoreContentType() && !contentType.startsWith("text/") && !Response.xmlContentTypeRxp.matcher(contentType).matches()) {
                throw new UnsupportedMimeTypeException("Unhandled content type. Must be text/*, application/xml, or application/xhtml+xml", contentType, req.url().toString());
            }
            if (contentType != null && Response.xmlContentTypeRxp.matcher(contentType).matches() && req instanceof HttpConnection.Request && !((HttpConnection.Request)req).parserDefined) {
                req.parser(Parser.xmlParser());
            }
            res.charset = DataUtil.getCharsetFromContentType(res.contentType);
            if (conn.getContentLength() != 0 && req.method() != Method.HEAD) {
                InputStream bodyStream = null;
                try {
                    bodyStream = ((conn.getErrorStream() != null) ? conn.getErrorStream() : conn.getInputStream());
                    if (res.hasHeaderWithValue("Content-Encoding", "gzip")) {
                        bodyStream = new GZIPInputStream(bodyStream);
                    }
                    res.byteData = DataUtil.readToByteBuffer(bodyStream, req.maxBodySize());
                }
                finally {
                    if (bodyStream != null) {
                        bodyStream.close();
                    }
                }
            }
            else {
                res.byteData = DataUtil.emptyByteBuffer();
            }
        }
        finally {
            conn.disconnect();
        }
        res.executed = true;
        return res;
    }
    
    public int statusCode() {
        return this.statusCode;
    }
    
    public String statusMessage() {
        return this.statusMessage;
    }
    
    public String charset() {
        return this.charset;
    }
    
    public Response charset(final String charset) {
        this.charset = charset;
        return this;
    }
    
    public String contentType() {
        return this.contentType;
    }
    
    public Document parse() throws IOException {
        Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before parsing response");
        final Document doc = DataUtil.parseByteData(this.byteData, this.charset, this.url.toExternalForm(), this.req.parser());
        this.byteData.rewind();
        this.charset = doc.outputSettings().charset().name();
        return doc;
    }
    
    public String body() {
        Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
        String body;
        if (this.charset == null) {
            body = Charset.forName("UTF-8").decode(this.byteData).toString();
        }
        else {
            body = Charset.forName(this.charset).decode(this.byteData).toString();
        }
        this.byteData.rewind();
        return body;
    }
    
    public byte[] bodyAsBytes() {
        Validate.isTrue(this.executed, "Request must be executed (with .execute(), .get(), or .post() before getting response body");
        return this.byteData.array();
    }
    
    private static HttpURLConnection createConnection(final Connection.Request req) throws IOException {
        final HttpURLConnection conn = (HttpURLConnection)((req.proxy() == null) ? req.url().openConnection() : req.url().openConnection(req.proxy()));
        conn.setRequestMethod(req.method().name());
        conn.setInstanceFollowRedirects(false);
        conn.setConnectTimeout(req.timeout());
        conn.setReadTimeout(req.timeout());
        if (conn instanceof HttpsURLConnection && !req.validateTLSCertificates()) {
            initUnSecureTSL();
            ((HttpsURLConnection)conn).setSSLSocketFactory(Response.sslSocketFactory);
            ((HttpsURLConnection)conn).setHostnameVerifier(getInsecureVerifier());
        }
        if (req.method().hasBody()) {
            conn.setDoOutput(true);
        }
        if (req.cookies().size() > 0) {
            conn.addRequestProperty("Cookie", getRequestCookieString(req));
        }
        for (final Map.Entry<String, String> header : req.headers().entrySet()) {
            conn.addRequestProperty(header.getKey(), header.getValue());
        }
        return conn;
    }
    
    private static HostnameVerifier getInsecureVerifier() {
        return new HostnameVerifier() {
            public boolean verify(final String urlHostName, final SSLSession session) {
                return true;
            }
        };
    }
    
    private static synchronized void initUnSecureTSL() throws IOException {
        if (Response.sslSocketFactory == null) {
            final TrustManager[] trustAllCerts = { new X509TrustManager() {
                    public void checkClientTrusted(final X509Certificate[] chain, final String authType) {
                    }
                    
                    public void checkServerTrusted(final X509Certificate[] chain, final String authType) {
                    }
                    
                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                } };
            try {
                final SSLContext sslContext = SSLContext.getInstance("SSL");
                sslContext.init(null, trustAllCerts, new SecureRandom());
                Response.sslSocketFactory = sslContext.getSocketFactory();
            }
            catch (NoSuchAlgorithmException e) {
                throw new IOException("Can't create unsecure trust manager");
            }
            catch (KeyManagementException e2) {
                throw new IOException("Can't create unsecure trust manager");
            }
        }
    }
    
    private void setupFromConnection(final HttpURLConnection conn, final Connection.Response previousResponse) throws IOException {
        this.method = Method.valueOf(conn.getRequestMethod());
        this.url = conn.getURL();
        this.statusCode = conn.getResponseCode();
        this.statusMessage = conn.getResponseMessage();
        this.contentType = conn.getContentType();
        final Map<String, List<String>> resHeaders = createHeaderMap(conn);
        this.processResponseHeaders(resHeaders);
        if (previousResponse != null) {
            for (final Map.Entry<String, String> prevCookie : previousResponse.cookies().entrySet()) {
                if (!this.hasCookie(prevCookie.getKey())) {
                    this.cookie(prevCookie.getKey(), prevCookie.getValue());
                }
            }
        }
    }
    
    private static LinkedHashMap<String, List<String>> createHeaderMap(final HttpURLConnection conn) {
        final LinkedHashMap<String, List<String>> headers = new LinkedHashMap<String, List<String>>();
        int i = 0;
        while (true) {
            final String key = conn.getHeaderFieldKey(i);
            final String val = conn.getHeaderField(i);
            if (key == null && val == null) {
                break;
            }
            ++i;
            if (key == null) {
                continue;
            }
            if (val == null) {
                continue;
            }
            if (headers.containsKey(key)) {
                headers.get(key).add(val);
            }
            else {
                final ArrayList<String> vals = new ArrayList<String>();
                vals.add(val);
                headers.put(key, vals);
            }
        }
        return headers;
    }
    
    void processResponseHeaders(final Map<String, List<String>> resHeaders) {
        for (final Map.Entry<String, List<String>> entry : resHeaders.entrySet()) {
            final String name = entry.getKey();
            if (name == null) {
                continue;
            }
            final List<String> values = entry.getValue();
            if (name.equalsIgnoreCase("Set-Cookie")) {
                for (final String value : values) {
                    if (value == null) {
                        continue;
                    }
                    final TokenQueue cd = new TokenQueue(value);
                    final String cookieName = cd.chompTo("=").trim();
                    final String cookieVal = cd.consumeTo(";").trim();
                    if (cookieName.length() <= 0) {
                        continue;
                    }
                    this.cookie(cookieName, cookieVal);
                }
            }
            else if (values.size() == 1) {
                this.header(name, values.get(0));
            }
            else {
                if (values.size() <= 1) {
                    continue;
                }
                final StringBuilder accum = new StringBuilder();
                for (int i = 0; i < values.size(); ++i) {
                    final String val = values.get(i);
                    if (i != 0) {
                        accum.append(", ");
                    }
                    accum.append(val);
                }
                this.header(name, accum.toString());
            }
        }
    }
    
    private static String setOutputContentType(final Connection.Request req) {
        String bound = null;
        if (!req.hasHeader("Content-Type")) {
            if (HttpConnection.access$400(req)) {
                bound = DataUtil.mimeBoundary();
                req.header("Content-Type", "multipart/form-data; boundary=" + bound);
            }
            else {
                req.header("Content-Type", "application/x-www-form-urlencoded; charset=" + req.postDataCharset());
            }
        }
        return bound;
    }
    
    private static void writePost(final Connection.Request req, final OutputStream outputStream, final String bound) throws IOException {
        final Collection<Connection.KeyVal> data = req.data();
        final BufferedWriter w = new BufferedWriter(new OutputStreamWriter(outputStream, req.postDataCharset()));
        if (bound != null) {
            for (final Connection.KeyVal keyVal : data) {
                w.write("--");
                w.write(bound);
                w.write("\r\n");
                w.write("Content-Disposition: form-data; name=\"");
                w.write(HttpConnection.access$500(keyVal.key()));
                w.write("\"");
                if (keyVal.hasInputStream()) {
                    w.write("; filename=\"");
                    w.write(HttpConnection.access$500(keyVal.value()));
                    w.write("\"\r\nContent-Type: application/octet-stream\r\n\r\n");
                    w.flush();
                    DataUtil.crossStreams(keyVal.inputStream(), outputStream);
                    outputStream.flush();
                }
                else {
                    w.write("\r\n\r\n");
                    w.write(keyVal.value());
                }
                w.write("\r\n");
            }
            w.write("--");
            w.write(bound);
            w.write("--");
        }
        else if (req.requestBody() != null) {
            w.write(req.requestBody());
        }
        else {
            boolean first = true;
            for (final Connection.KeyVal keyVal2 : data) {
                if (!first) {
                    w.append('&');
                }
                else {
                    first = false;
                }
                w.write(URLEncoder.encode(keyVal2.key(), req.postDataCharset()));
                w.write(61);
                w.write(URLEncoder.encode(keyVal2.value(), req.postDataCharset()));
            }
        }
        w.close();
    }
    
    private static String getRequestCookieString(final Connection.Request req) {
        final StringBuilder sb = new StringBuilder();
        boolean first = true;
        for (final Map.Entry<String, String> cookie : req.cookies().entrySet()) {
            if (!first) {
                sb.append("; ");
            }
            else {
                first = false;
            }
            sb.append(cookie.getKey()).append('=').append(cookie.getValue());
        }
        return sb.toString();
    }
    
    private static void serialiseRequestUrl(final Connection.Request req) throws IOException {
        final URL in = req.url();
        final StringBuilder url = new StringBuilder();
        boolean first = true;
        url.append(in.getProtocol()).append("://").append(in.getAuthority()).append(in.getPath()).append("?");
        if (in.getQuery() != null) {
            url.append(in.getQuery());
            first = false;
        }
        for (final Connection.KeyVal keyVal : req.data()) {
            Validate.isFalse(keyVal.hasInputStream(), "InputStream data not supported in URL query string.");
            if (!first) {
                url.append('&');
            }
            else {
                first = false;
            }
            url.append(URLEncoder.encode(keyVal.key(), "UTF-8")).append('=').append(URLEncoder.encode(keyVal.value(), "UTF-8"));
        }
        req.url(new URL(url.toString()));
        req.data().clear();
    }
    
    static {
        xmlContentTypeRxp = Pattern.compile("(application|text)/\\w*\\+?xml.*");
    }
}
