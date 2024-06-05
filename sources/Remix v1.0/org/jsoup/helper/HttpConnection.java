package org.jsoup.helper;

import org.jsoup.nodes.*;
import org.jsoup.internal.*;
import java.nio.charset.*;
import java.nio.*;
import java.util.regex.*;
import org.jsoup.*;
import java.util.zip.*;
import java.security.cert.*;
import javax.net.ssl.*;
import java.security.*;
import java.util.*;
import org.jsoup.parser.*;
import java.io.*;
import java.net.*;

public class HttpConnection implements Connection
{
    public static final String CONTENT_ENCODING = "Content-Encoding";
    public static final String DEFAULT_UA = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36";
    private static final String USER_AGENT = "User-Agent";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String MULTIPART_FORM_DATA = "multipart/form-data";
    private static final String FORM_URL_ENCODED = "application/x-www-form-urlencoded";
    private static final int HTTP_TEMP_REDIR = 307;
    private Connection.Request req;
    private Connection.Response res;
    
    public static Connection connect(final String url) {
        final Connection con = new HttpConnection();
        con.url(url);
        return con;
    }
    
    public static Connection connect(final URL url) {
        final Connection con = new HttpConnection();
        con.url(url);
        return con;
    }
    
    private static String encodeUrl(final String url) {
        try {
            final URL u = new URL(url);
            return encodeUrl(u).toExternalForm();
        }
        catch (Exception e) {
            return url;
        }
    }
    
    private static URL encodeUrl(final URL u) {
        try {
            final URI uri = new URI(u.toExternalForm());
            return new URL(uri.toASCIIString());
        }
        catch (Exception e) {
            return u;
        }
    }
    
    private static String encodeMimeName(final String val) {
        if (val == null) {
            return null;
        }
        return val.replaceAll("\"", "%22");
    }
    
    private HttpConnection() {
        this.req = new Request();
        this.res = new Response();
    }
    
    public Connection url(final URL url) {
        this.req.url(url);
        return this;
    }
    
    public Connection url(final String url) {
        Validate.notEmpty(url, "Must supply a valid URL");
        try {
            this.req.url(new URL(encodeUrl(url)));
        }
        catch (MalformedURLException e) {
            throw new IllegalArgumentException("Malformed URL: " + url, e);
        }
        return this;
    }
    
    public Connection proxy(final Proxy proxy) {
        this.req.proxy(proxy);
        return this;
    }
    
    public Connection proxy(final String host, final int port) {
        this.req.proxy(host, port);
        return this;
    }
    
    public Connection userAgent(final String userAgent) {
        Validate.notNull(userAgent, "User agent must not be null");
        this.req.header("User-Agent", userAgent);
        return this;
    }
    
    public Connection timeout(final int millis) {
        this.req.timeout(millis);
        return this;
    }
    
    public Connection maxBodySize(final int bytes) {
        this.req.maxBodySize(bytes);
        return this;
    }
    
    public Connection followRedirects(final boolean followRedirects) {
        this.req.followRedirects(followRedirects);
        return this;
    }
    
    public Connection referrer(final String referrer) {
        Validate.notNull(referrer, "Referrer must not be null");
        this.req.header("Referer", referrer);
        return this;
    }
    
    public Connection method(final Method method) {
        this.req.method(method);
        return this;
    }
    
    public Connection ignoreHttpErrors(final boolean ignoreHttpErrors) {
        this.req.ignoreHttpErrors(ignoreHttpErrors);
        return this;
    }
    
    public Connection ignoreContentType(final boolean ignoreContentType) {
        this.req.ignoreContentType(ignoreContentType);
        return this;
    }
    
    public Connection validateTLSCertificates(final boolean value) {
        this.req.validateTLSCertificates(value);
        return this;
    }
    
    public Connection data(final String key, final String value) {
        this.req.data(KeyVal.create(key, value));
        return this;
    }
    
    public Connection data(final String key, final String filename, final InputStream inputStream) {
        this.req.data(KeyVal.create(key, filename, inputStream));
        return this;
    }
    
    public Connection data(final Map<String, String> data) {
        Validate.notNull(data, "Data map must not be null");
        for (final Map.Entry<String, String> entry : data.entrySet()) {
            this.req.data(KeyVal.create(entry.getKey(), entry.getValue()));
        }
        return this;
    }
    
    public Connection data(final String... keyvals) {
        Validate.notNull(keyvals, "Data key value pairs must not be null");
        Validate.isTrue(keyvals.length % 2 == 0, "Must supply an even number of key value pairs");
        for (int i = 0; i < keyvals.length; i += 2) {
            final String key = keyvals[i];
            final String value = keyvals[i + 1];
            Validate.notEmpty(key, "Data key must not be empty");
            Validate.notNull(value, "Data value must not be null");
            this.req.data(KeyVal.create(key, value));
        }
        return this;
    }
    
    public Connection data(final Collection<Connection.KeyVal> data) {
        Validate.notNull(data, "Data collection must not be null");
        for (final Connection.KeyVal entry : data) {
            this.req.data(entry);
        }
        return this;
    }
    
    public Connection.KeyVal data(final String key) {
        Validate.notEmpty(key, "Data key must not be empty");
        for (final Connection.KeyVal keyVal : this.request().data()) {
            if (keyVal.key().equals(key)) {
                return keyVal;
            }
        }
        return null;
    }
    
    public Connection requestBody(final String body) {
        this.req.requestBody(body);
        return this;
    }
    
    public Connection header(final String name, final String value) {
        this.req.header(name, value);
        return this;
    }
    
    public Connection headers(final Map<String, String> headers) {
        Validate.notNull(headers, "Header map must not be null");
        for (final Map.Entry<String, String> entry : headers.entrySet()) {
            this.req.header(entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    public Connection cookie(final String name, final String value) {
        this.req.cookie(name, value);
        return this;
    }
    
    public Connection cookies(final Map<String, String> cookies) {
        Validate.notNull(cookies, "Cookie map must not be null");
        for (final Map.Entry<String, String> entry : cookies.entrySet()) {
            this.req.cookie(entry.getKey(), entry.getValue());
        }
        return this;
    }
    
    public Connection parser(final Parser parser) {
        this.req.parser(parser);
        return this;
    }
    
    public Document get() throws IOException {
        this.req.method(Method.GET);
        this.execute();
        return this.res.parse();
    }
    
    public Document post() throws IOException {
        this.req.method(Method.POST);
        this.execute();
        return this.res.parse();
    }
    
    public Connection.Response execute() throws IOException {
        return this.res = Response.execute(this.req);
    }
    
    public Connection.Request request() {
        return this.req;
    }
    
    public Connection request(final Connection.Request request) {
        this.req = request;
        return this;
    }
    
    public Connection.Response response() {
        return this.res;
    }
    
    public Connection response(final Connection.Response response) {
        this.res = response;
        return this;
    }
    
    public Connection postDataCharset(final String charset) {
        this.req.postDataCharset(charset);
        return this;
    }
    
    private static boolean needsMultipart(final Connection.Request req) {
        boolean needsMulti = false;
        for (final Connection.KeyVal keyVal : req.data()) {
            if (keyVal.hasInputStream()) {
                needsMulti = true;
                break;
            }
        }
        return needsMulti;
    }
    
    private abstract static class Base<T extends Connection.Base> implements Connection.Base<T>
    {
        URL url;
        Method method;
        Map<String, String> headers;
        Map<String, String> cookies;
        
        private Base() {
            this.headers = new LinkedHashMap<String, String>();
            this.cookies = new LinkedHashMap<String, String>();
        }
        
        public URL url() {
            return this.url;
        }
        
        public T url(final URL url) {
            Validate.notNull(url, "URL must not be null");
            this.url = url;
            return (T)this;
        }
        
        public Method method() {
            return this.method;
        }
        
        public T method(final Method method) {
            Validate.notNull(method, "Method must not be null");
            this.method = method;
            return (T)this;
        }
        
        public String header(final String name) {
            Validate.notNull(name, "Header name must not be null");
            String val = this.getHeaderCaseInsensitive(name);
            if (val != null) {
                val = fixHeaderEncoding(val);
            }
            return val;
        }
        
        private static String fixHeaderEncoding(final String val) {
            try {
                final byte[] bytes = val.getBytes("ISO-8859-1");
                if (!looksLikeUtf8(bytes)) {
                    return val;
                }
                return new String(bytes, "UTF-8");
            }
            catch (UnsupportedEncodingException e) {
                return val;
            }
        }
        
        private static boolean looksLikeUtf8(final byte[] input) {
            int i = 0;
            if (input.length >= 3 && (input[0] & 0xFF) == 0xEF && ((input[1] & 0xFF) == 0xBB & (input[2] & 0xFF) == 0xBF)) {
                i = 3;
            }
            for (int j = input.length; i < j; ++i) {
                int o = input[i];
                if ((o & 0x80) != 0x0) {
                    int end;
                    if ((o & 0xE0) == 0xC0) {
                        end = i + 1;
                    }
                    else if ((o & 0xF0) == 0xE0) {
                        end = i + 2;
                    }
                    else {
                        if ((o & 0xF8) != 0xF0) {
                            return false;
                        }
                        end = i + 3;
                    }
                    while (i < end) {
                        ++i;
                        o = input[i];
                        if ((o & 0xC0) != 0x80) {
                            return false;
                        }
                    }
                }
            }
            return true;
        }
        
        public T header(final String name, final String value) {
            Validate.notEmpty(name, "Header name must not be empty");
            Validate.notNull(value, "Header value must not be null");
            this.removeHeader(name);
            this.headers.put(name, value);
            return (T)this;
        }
        
        public boolean hasHeader(final String name) {
            Validate.notEmpty(name, "Header name must not be empty");
            return this.getHeaderCaseInsensitive(name) != null;
        }
        
        public boolean hasHeaderWithValue(final String name, final String value) {
            return this.hasHeader(name) && this.header(name).equalsIgnoreCase(value);
        }
        
        public T removeHeader(final String name) {
            Validate.notEmpty(name, "Header name must not be empty");
            final Map.Entry<String, String> entry = this.scanHeaders(name);
            if (entry != null) {
                this.headers.remove(entry.getKey());
            }
            return (T)this;
        }
        
        public Map<String, String> headers() {
            return this.headers;
        }
        
        private String getHeaderCaseInsensitive(final String name) {
            Validate.notNull(name, "Header name must not be null");
            String value = this.headers.get(name);
            if (value == null) {
                value = this.headers.get(Normalizer.lowerCase(name));
            }
            if (value == null) {
                final Map.Entry<String, String> entry = this.scanHeaders(name);
                if (entry != null) {
                    value = entry.getValue();
                }
            }
            return value;
        }
        
        private Map.Entry<String, String> scanHeaders(final String name) {
            final String lc = Normalizer.lowerCase(name);
            for (final Map.Entry<String, String> entry : this.headers.entrySet()) {
                if (Normalizer.lowerCase(entry.getKey()).equals(lc)) {
                    return entry;
                }
            }
            return null;
        }
        
        public String cookie(final String name) {
            Validate.notEmpty(name, "Cookie name must not be empty");
            return this.cookies.get(name);
        }
        
        public T cookie(final String name, final String value) {
            Validate.notEmpty(name, "Cookie name must not be empty");
            Validate.notNull(value, "Cookie value must not be null");
            this.cookies.put(name, value);
            return (T)this;
        }
        
        public boolean hasCookie(final String name) {
            Validate.notEmpty(name, "Cookie name must not be empty");
            return this.cookies.containsKey(name);
        }
        
        public T removeCookie(final String name) {
            Validate.notEmpty(name, "Cookie name must not be empty");
            this.cookies.remove(name);
            return (T)this;
        }
        
        public Map<String, String> cookies() {
            return this.cookies;
        }
    }
    
    public static class Request extends HttpConnection.Base<Connection.Request> implements Connection.Request
    {
        private Proxy proxy;
        private int timeoutMilliseconds;
        private int maxBodySizeBytes;
        private boolean followRedirects;
        private Collection<Connection.KeyVal> data;
        private String body;
        private boolean ignoreHttpErrors;
        private boolean ignoreContentType;
        private Parser parser;
        private boolean parserDefined;
        private boolean validateTSLCertificates;
        private String postDataCharset;
        
        private Request() {
            this.body = null;
            this.ignoreHttpErrors = false;
            this.ignoreContentType = false;
            this.parserDefined = false;
            this.validateTSLCertificates = true;
            this.postDataCharset = "UTF-8";
            this.timeoutMilliseconds = 30000;
            this.maxBodySizeBytes = 1048576;
            this.followRedirects = true;
            this.data = new ArrayList<Connection.KeyVal>();
            this.method = Method.GET;
            this.headers.put("Accept-Encoding", "gzip");
            this.headers.put("User-Agent", "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_11_6) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/53.0.2785.143 Safari/537.36");
            this.parser = Parser.htmlParser();
        }
        
        public Proxy proxy() {
            return this.proxy;
        }
        
        public Request proxy(final Proxy proxy) {
            this.proxy = proxy;
            return this;
        }
        
        public Request proxy(final String host, final int port) {
            this.proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved(host, port));
            return this;
        }
        
        public int timeout() {
            return this.timeoutMilliseconds;
        }
        
        public Request timeout(final int millis) {
            Validate.isTrue(millis >= 0, "Timeout milliseconds must be 0 (infinite) or greater");
            this.timeoutMilliseconds = millis;
            return this;
        }
        
        public int maxBodySize() {
            return this.maxBodySizeBytes;
        }
        
        public Connection.Request maxBodySize(final int bytes) {
            Validate.isTrue(bytes >= 0, "maxSize must be 0 (unlimited) or larger");
            this.maxBodySizeBytes = bytes;
            return this;
        }
        
        public boolean followRedirects() {
            return this.followRedirects;
        }
        
        public Connection.Request followRedirects(final boolean followRedirects) {
            this.followRedirects = followRedirects;
            return this;
        }
        
        public boolean ignoreHttpErrors() {
            return this.ignoreHttpErrors;
        }
        
        public boolean validateTLSCertificates() {
            return this.validateTSLCertificates;
        }
        
        public void validateTLSCertificates(final boolean value) {
            this.validateTSLCertificates = value;
        }
        
        public Connection.Request ignoreHttpErrors(final boolean ignoreHttpErrors) {
            this.ignoreHttpErrors = ignoreHttpErrors;
            return this;
        }
        
        public boolean ignoreContentType() {
            return this.ignoreContentType;
        }
        
        public Connection.Request ignoreContentType(final boolean ignoreContentType) {
            this.ignoreContentType = ignoreContentType;
            return this;
        }
        
        public Request data(final Connection.KeyVal keyval) {
            Validate.notNull(keyval, "Key val must not be null");
            this.data.add(keyval);
            return this;
        }
        
        public Collection<Connection.KeyVal> data() {
            return this.data;
        }
        
        public Connection.Request requestBody(final String body) {
            this.body = body;
            return this;
        }
        
        public String requestBody() {
            return this.body;
        }
        
        public Request parser(final Parser parser) {
            this.parser = parser;
            this.parserDefined = true;
            return this;
        }
        
        public Parser parser() {
            return this.parser;
        }
        
        public Connection.Request postDataCharset(final String charset) {
            Validate.notNull(charset, "Charset must not be null");
            if (!Charset.isSupported(charset)) {
                throw new IllegalCharsetNameException(charset);
            }
            this.postDataCharset = charset;
            return this;
        }
        
        public String postDataCharset() {
            return this.postDataCharset;
        }
    }
    
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
                    req.url(encodeUrl(redir));
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
                if (needsMultipart(req)) {
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
                    w.write(encodeMimeName(keyVal.key()));
                    w.write("\"");
                    if (keyVal.hasInputStream()) {
                        w.write("; filename=\"");
                        w.write(encodeMimeName(keyVal.value()));
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
    
    public static class KeyVal implements Connection.KeyVal
    {
        private String key;
        private String value;
        private InputStream stream;
        
        public static KeyVal create(final String key, final String value) {
            return new KeyVal().key(key).value(value);
        }
        
        public static KeyVal create(final String key, final String filename, final InputStream stream) {
            return new KeyVal().key(key).value(filename).inputStream(stream);
        }
        
        private KeyVal() {
        }
        
        public KeyVal key(final String key) {
            Validate.notEmpty(key, "Data key must not be empty");
            this.key = key;
            return this;
        }
        
        public String key() {
            return this.key;
        }
        
        public KeyVal value(final String value) {
            Validate.notNull(value, "Data value must not be null");
            this.value = value;
            return this;
        }
        
        public String value() {
            return this.value;
        }
        
        public KeyVal inputStream(final InputStream inputStream) {
            Validate.notNull(this.value, "Data input stream must not be null");
            this.stream = inputStream;
            return this;
        }
        
        public InputStream inputStream() {
            return this.stream;
        }
        
        public boolean hasInputStream() {
            return this.stream != null;
        }
        
        @Override
        public String toString() {
            return this.key + "=" + this.value;
        }
    }
}
