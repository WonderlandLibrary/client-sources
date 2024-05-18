package net.minecraft.util;

import java.util.concurrent.atomic.*;
import net.minecraft.server.*;
import java.util.*;
import java.util.concurrent.*;
import org.apache.logging.log4j.*;
import com.google.common.util.concurrent.*;
import org.apache.commons.io.*;
import java.net.*;
import java.io.*;

public class HttpUtil
{
    private static final String[] I;
    private static final Logger logger;
    private static final AtomicInteger downloadThreadsStarted;
    public static final ListeningExecutorService field_180193_a;
    
    public static String postMap(final URL url, final Map<String, Object> map, final boolean b) {
        return post(url, buildPostString(map), b);
    }
    
    private static String post(final URL url, final String s, final boolean b) {
        try {
            Proxy serverProxy;
            if (MinecraftServer.getServer() == null) {
                serverProxy = null;
                "".length();
                if (true != true) {
                    throw null;
                }
            }
            else {
                serverProxy = MinecraftServer.getServer().getServerProxy();
            }
            Proxy no_PROXY = serverProxy;
            if (no_PROXY == null) {
                no_PROXY = Proxy.NO_PROXY;
            }
            final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection(no_PROXY);
            httpURLConnection.setRequestMethod(HttpUtil.I["   ".length()]);
            httpURLConnection.setRequestProperty(HttpUtil.I[0x1A ^ 0x1E], HttpUtil.I[0x7A ^ 0x7F]);
            httpURLConnection.setRequestProperty(HttpUtil.I[0x6F ^ 0x69], new StringBuilder().append(s.getBytes().length).toString());
            httpURLConnection.setRequestProperty(HttpUtil.I[0x48 ^ 0x4F], HttpUtil.I[0x44 ^ 0x4C]);
            httpURLConnection.setUseCaches("".length() != 0);
            httpURLConnection.setDoInput(" ".length() != 0);
            httpURLConnection.setDoOutput(" ".length() != 0);
            final DataOutputStream dataOutputStream = new DataOutputStream(httpURLConnection.getOutputStream());
            dataOutputStream.writeBytes(s);
            dataOutputStream.flush();
            dataOutputStream.close();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
            final StringBuffer sb = new StringBuffer();
            "".length();
            if (false) {
                throw null;
            }
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append((char)(0x62 ^ 0x6F));
            }
            bufferedReader.close();
            return sb.toString();
        }
        catch (Exception ex) {
            if (!b) {
                HttpUtil.logger.error(HttpUtil.I[0x22 ^ 0x2B] + url, (Throwable)ex);
            }
            return HttpUtil.I[0xCE ^ 0xC4];
        }
    }
    
    public static String buildPostString(final Map<String, Object> map) {
        final StringBuilder sb = new StringBuilder();
        final Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        "".length();
        if (4 < 4) {
            throw null;
        }
        while (iterator.hasNext()) {
            final Map.Entry<String, Object> entry = iterator.next();
            if (sb.length() > 0) {
                sb.append((char)(0xA4 ^ 0x82));
            }
            try {
                sb.append(URLEncoder.encode(entry.getKey(), HttpUtil.I[" ".length()]));
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            catch (UnsupportedEncodingException ex) {
                ex.printStackTrace();
            }
            if (entry.getValue() != null) {
                sb.append((char)(0x5C ^ 0x61));
                try {
                    sb.append(URLEncoder.encode(entry.getValue().toString(), HttpUtil.I["  ".length()]));
                    "".length();
                    if (0 < -1) {
                        throw null;
                    }
                    continue;
                }
                catch (UnsupportedEncodingException ex2) {
                    ex2.printStackTrace();
                }
            }
        }
        return sb.toString();
    }
    
    private static void I() {
        (I = new String[0x57 ^ 0x5B])["".length()] = I("\u0013\u0004\u001c\u0003'8\n\u000f\b9wN\u000f", "WkkmK");
        HttpUtil.I[" ".length()] = I(" &'b\\", "uraOd");
        HttpUtil.I["  ".length()] = I("\u0017\u0013'As", "BGalK");
        HttpUtil.I["   ".length()] = I("\u0018;\u0000\u0013", "HtSGU");
        HttpUtil.I[0x21 ^ 0x25] = I("5\u001e\u0003$\u0002\u0018\u0005@\u0004\u001e\u0006\u0014", "vqmPg");
        HttpUtil.I[0x13 ^ 0x16] = I("\b89\u001c-\n)=\u0019+\u0007g1]3\u001e?d\u0016+\u001b%d\u00056\u0005-'\u0013+\r--", "iHIpD");
        HttpUtil.I[0x6E ^ 0x68] = I("0>#\u00155\u001d%`-5\u001d69\t", "sQMaP");
        HttpUtil.I[0x78 ^ 0x7F] = I("\u0002\u001e%\u0000,/\u0005f8(/\u0016>\u0015.$", "AqKtI");
        HttpUtil.I[0x7A ^ 0x72] = I("\u00009i/\u0010", "eWDzC");
        HttpUtil.I[0x1E ^ 0x17] = I("-\u0006'?\u0016N\u0007='R\u001e\u0006!'R\u001a\u0006r", "niRSr");
        HttpUtil.I[0x55 ^ 0x5F] = I("", "XxREE");
        HttpUtil.I[0x64 ^ 0x6F] = I(",<7", "kycrK");
    }
    
    static {
        I();
        field_180193_a = MoreExecutors.listeningDecorator(Executors.newCachedThreadPool(new ThreadFactoryBuilder().setDaemon((boolean)(" ".length() != 0)).setNameFormat(HttpUtil.I["".length()]).build()));
        downloadThreadsStarted = new AtomicInteger("".length());
        logger = LogManager.getLogger();
    }
    
    public static ListenableFuture<Object> downloadResourcePack(final File file, final String s, final Map<String, String> map, final int n, final IProgressUpdate progressUpdate, final Proxy proxy) {
        return (ListenableFuture<Object>)HttpUtil.field_180193_a.submit((Runnable)new Runnable(progressUpdate, s, proxy, map, file, n) {
            private final Proxy val$p_180192_5_;
            private final int val$maxSize;
            private static final String[] I;
            private final File val$saveFile;
            private final IProgressUpdate val$p_180192_4_;
            private final Map val$p_180192_2_;
            private final String val$packUrl;
            
            static {
                I();
            }
            
            private static String I(final String s, final String s2) {
                final StringBuilder sb = new StringBuilder();
                final char[] charArray = s2.toCharArray();
                int length = "".length();
                final char[] charArray2 = s.toCharArray();
                final int length2 = charArray2.length;
                int i = "".length();
                while (i < length2) {
                    sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                    ++length;
                    ++i;
                    "".length();
                    if (-1 != -1) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            private static void I() {
                (I = new String[0xBD ^ 0xB3])["".length()] = I("\r(>8$&&-?&.g\u001b3;&2;5-i\u0017(5#", "IGIVH");
                HttpUtil$1.I[" ".length()] = I("!\u000b\u0007, \u000bJ> ?\u0019\u000f\u001f1`BD", "ljlEN");
                HttpUtil$1.I["  ".length()] = I("\u0012\u000e\u0001 \u00159\u0000\u0012'\u00171A\u0010'\u00153A^kWd\u0007V\u0003;\u007fOX`", "VavNy");
                HttpUtil$1.I["   ".length()] = I("\b&\u0004\u0016\u0012%-\u000fS", "LChsf");
                HttpUtil$1.I[0x88 ^ 0x8C] = I("e'2V\u001e1f%\u0019\u00126f/\u0019\u0003e+ \u0002\u0014-f6\u001e\u00161f6\u0013W&33\u0004\u0012+2-\u000fW-'7\u0013Wm", "EFAvw");
                HttpUtil$1.I[0x89 ^ 0x8C] = I("n\u0010\u0018h);\u0014K", "NfkHF");
                HttpUtil$1.I[0x43 ^ 0x45] = I("Zj", "sDNcn");
                HttpUtil$1.I[0xAB ^ 0xAC] = I("$\u000b\u001b.%\u000b\u0018\u0012k?\u0011B\u0015\"1\u0005\u0007\u0005k\"\n\u0003\u0019k;\u0003\u001a\u001e&#\u000fB\u0016':\r\u0015\u0012/vJ\u0004\u001e'3B\u000b\u0004k", "bbwKV");
                HttpUtil$1.I[0xAF ^ 0xA7] = I("Hb\u0000\u000e\b\r6L\u000e\u0016D", "dBlge");
                HttpUtil$1.I[0xE ^ 0x7] = I("B", "kJAhC");
                HttpUtil$1.I[0xA0 ^ 0xAA] = I("\r#\u001b\u0010=\"0\u0012U9*9W\u0017',-\u0012\u0007n?\"\u0016\u001bn&+\u000f\u001c#>'W\u0014\"'%\u0000\u0010*kb\u0010\u001a:ktJU", "KJwuN");
                HttpUtil$1.I[0x5C ^ 0x57] = I("Uq\n#\u000f\u0010%F=\u0003\nq", "yQfJb");
                HttpUtil$1.I[0x27 ^ 0x2B] = I("S", "znWBi");
                HttpUtil$1.I[0x6 ^ 0xB] = I(" \u000f0'4;\u001446#-", "iAdbf");
            }
            
            @Override
            public void run() {
                HttpURLConnection httpURLConnection = null;
                InputStream inputStream = null;
                OutputStream outputStream = null;
                if (this.val$p_180192_4_ != null) {
                    this.val$p_180192_4_.resetProgressAndMessage(HttpUtil$1.I["".length()]);
                    this.val$p_180192_4_.displayLoadingString(HttpUtil$1.I[" ".length()]);
                }
                try {
                    final byte[] array = new byte[3065 + 3097 - 2253 + 187];
                    httpURLConnection = (HttpURLConnection)new URL(this.val$packUrl).openConnection(this.val$p_180192_5_);
                    float n = 0.0f;
                    final float n2 = this.val$p_180192_2_.entrySet().size();
                    final Iterator<Map.Entry<String, V>> iterator = this.val$p_180192_2_.entrySet().iterator();
                    "".length();
                    if (0 >= 2) {
                        throw null;
                    }
                    while (iterator.hasNext()) {
                        final Map.Entry<String, V> entry = iterator.next();
                        httpURLConnection.setRequestProperty(entry.getKey(), (String)entry.getValue());
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setLoadingProgress((int)(++n / n2 * 100.0f));
                        }
                    }
                    inputStream = httpURLConnection.getInputStream();
                    final float n3 = httpURLConnection.getContentLength();
                    final int contentLength = httpURLConnection.getContentLength();
                    if (this.val$p_180192_4_ != null) {
                        final IProgressUpdate val$p_180192_4_ = this.val$p_180192_4_;
                        final String s = HttpUtil$1.I["  ".length()];
                        final Object[] array2 = new Object[" ".length()];
                        array2["".length()] = n3 / 1000.0f / 1000.0f;
                        val$p_180192_4_.displayLoadingString(String.format(s, array2));
                    }
                    if (this.val$saveFile.exists()) {
                        final long length = this.val$saveFile.length();
                        if (length == contentLength) {
                            if (this.val$p_180192_4_ != null) {
                                this.val$p_180192_4_.setDoneWorking();
                            }
                            return;
                        }
                        HttpUtil.access$0().warn(HttpUtil$1.I["   ".length()] + this.val$saveFile + HttpUtil$1.I[0xBA ^ 0xBE] + contentLength + HttpUtil$1.I[0x3A ^ 0x3F] + length + HttpUtil$1.I[0x12 ^ 0x14]);
                        FileUtils.deleteQuietly(this.val$saveFile);
                        "".length();
                        if (0 >= 3) {
                            throw null;
                        }
                    }
                    else if (this.val$saveFile.getParentFile() != null) {
                        this.val$saveFile.getParentFile().mkdirs();
                    }
                    outputStream = new DataOutputStream(new FileOutputStream(this.val$saveFile));
                    if (this.val$maxSize > 0 && n3 > this.val$maxSize) {
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setDoneWorking();
                        }
                        throw new IOException(HttpUtil$1.I[0xBD ^ 0xBA] + n + HttpUtil$1.I[0x59 ^ 0x51] + this.val$maxSize + HttpUtil$1.I[0xB3 ^ 0xBA]);
                    }
                    "".length();
                    "".length();
                    if (1 >= 2) {
                        throw null;
                    }
                    int read;
                    while ((read = inputStream.read(array)) >= 0) {
                        n += read;
                        if (this.val$p_180192_4_ != null) {
                            this.val$p_180192_4_.setLoadingProgress((int)(n / n3 * 100.0f));
                        }
                        if (this.val$maxSize > 0 && n > this.val$maxSize) {
                            if (this.val$p_180192_4_ != null) {
                                this.val$p_180192_4_.setDoneWorking();
                            }
                            throw new IOException(HttpUtil$1.I[0x2 ^ 0x8] + n + HttpUtil$1.I[0x95 ^ 0x9E] + this.val$maxSize + HttpUtil$1.I[0x8E ^ 0x82]);
                        }
                        if (Thread.interrupted()) {
                            HttpUtil.access$0().error(HttpUtil$1.I[0x8F ^ 0x82]);
                            if (this.val$p_180192_4_ != null) {
                                this.val$p_180192_4_.setDoneWorking();
                            }
                            return;
                        }
                        outputStream.write(array, "".length(), read);
                    }
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.setDoneWorking();
                        return;
                    }
                }
                catch (Throwable t) {
                    t.printStackTrace();
                    if (httpURLConnection != null) {
                        final InputStream errorStream = httpURLConnection.getErrorStream();
                        try {
                            HttpUtil.access$0().error(IOUtils.toString(errorStream));
                            "".length();
                            if (4 != 4) {
                                throw null;
                            }
                        }
                        catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }
                    if (this.val$p_180192_4_ != null) {
                        this.val$p_180192_4_.setDoneWorking();
                        return;
                    }
                }
                finally {
                    IOUtils.closeQuietly(inputStream);
                    IOUtils.closeQuietly(outputStream);
                }
                IOUtils.closeQuietly(inputStream);
                IOUtils.closeQuietly(outputStream);
            }
        });
    }
    
    public static int getSuitableLanPort() throws IOException {
        ServerSocket serverSocket = null;
        int localPort = -" ".length();
        try {
            serverSocket = new ServerSocket("".length());
            localPort = serverSocket.getLocalPort();
            "".length();
            if (4 <= 2) {
                throw null;
            }
        }
        finally {
            try {
                if (serverSocket != null) {
                    serverSocket.close();
                    "".length();
                    if (0 >= 4) {
                        throw null;
                    }
                }
            }
            catch (IOException ex) {}
        }
        try {
            if (serverSocket != null) {
                serverSocket.close();
                "".length();
                if (3 == 0) {
                    throw null;
                }
            }
        }
        catch (IOException ex2) {}
        return localPort;
    }
    
    static Logger access$0() {
        return HttpUtil.logger;
    }
    
    public static String get(final URL url) throws IOException {
        final HttpURLConnection httpURLConnection = (HttpURLConnection)url.openConnection();
        httpURLConnection.setRequestMethod(HttpUtil.I[0x83 ^ 0x88]);
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream()));
        final StringBuilder sb = new StringBuilder();
        "".length();
        if (4 == 3) {
            throw null;
        }
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            sb.append(line);
            sb.append((char)(0xD ^ 0x0));
        }
        bufferedReader.close();
        return sb.toString();
    }
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (0 == -1) {
                throw null;
            }
        }
        return sb.toString();
    }
}
