package optfine;

import java.net.*;
import net.minecraft.client.*;
import java.util.*;
import java.io.*;

public class HttpUtils
{
    private static final String[] I;
    public static final String SERVER_URL;
    public static final String POST_URL;
    
    public static String post(final String s, final Map map, final byte[] array) throws IOException {
        HttpURLConnection httpURLConnection = null;
        String string;
        try {
            httpURLConnection = (HttpURLConnection)new URL(s).openConnection(Minecraft.getMinecraft().getProxy());
            httpURLConnection.setRequestMethod(HttpUtils.I["  ".length()]);
            if (map != null) {
                final Iterator<String> iterator = map.keySet().iterator();
                "".length();
                if (4 != 4) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final String next = iterator.next();
                    httpURLConnection.setRequestProperty(next, new StringBuilder().append(map.get(next)).toString());
                }
            }
            httpURLConnection.setRequestProperty(HttpUtils.I["   ".length()], HttpUtils.I[0xBE ^ 0xBA]);
            httpURLConnection.setRequestProperty(HttpUtils.I[0x21 ^ 0x24], new StringBuilder().append(array.length).toString());
            httpURLConnection.setRequestProperty(HttpUtils.I[0x79 ^ 0x7F], HttpUtils.I[0x77 ^ 0x70]);
            httpURLConnection.setUseCaches("".length() != 0);
            httpURLConnection.setDoInput(" ".length() != 0);
            httpURLConnection.setDoOutput(" ".length() != 0);
            final OutputStream outputStream = httpURLConnection.getOutputStream();
            outputStream.write(array);
            outputStream.flush();
            outputStream.close();
            final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(httpURLConnection.getInputStream(), HttpUtils.I[0x81 ^ 0x89]));
            final StringBuffer sb = new StringBuffer();
            "".length();
            if (0 >= 4) {
                throw null;
            }
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
                sb.append((char)(0x16 ^ 0x1B));
            }
            bufferedReader.close();
            string = sb.toString();
            "".length();
            if (4 != 4) {
                throw null;
            }
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        return string;
    }
    
    private static void I() {
        (I = new String[0x59 ^ 0x52])["".length()] = I("9\u001e\u00024P\u0003/%\u0014\u001f\u001f93^P", "qJVdp");
        HttpUtils.I[" ".length()] = I(" (5\u0016?I51\u0011.\b+e\u0000'\u00065 \u0007qI", "iFEcK");
        HttpUtils.I["  ".length()] = I("\u0014+1\u0005", "DdbQG");
        HttpUtils.I["   ".length()] = I("\u0011\u0003\t\u001c\"<\u0018J<>\"\t", "RlghG");
        HttpUtils.I[0x61 ^ 0x65] = I(";002A?9)/\u0000", "OUHFn");
        HttpUtils.I[0xC3 ^ 0xC6] = I(")'\u0019!\u0004\u0004<Z\u0019\u0004\u0004/\u0003=", "jHwUa");
        HttpUtils.I[0x19 ^ 0x1F] = I("\u0010\u001a\u0000\u0003.=\u0001C;*=\u0012\u001b\u0016,6", "SunwK");
        HttpUtils.I[0x84 ^ 0x83] = I(" \u0018C\u001d\u0014", "EvnHG");
        HttpUtils.I[0x81 ^ 0x89] = I("\u0005\u0004\u0010/(", "DWSfa");
        HttpUtils.I[0x93 ^ 0x9A] = I("\u0000>\u001a>WGe\u001d`\u0002\u0018>\u0007(\u0004\u0006/@ \b\u001c", "hJnNm");
        HttpUtils.I[0xB8 ^ 0xB2] = I("&<1\u001ajag*\u001a$'.,\u00045`& \u001e", "NHEjP");
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
            if (-1 == 3) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public static byte[] get(final String s) throws IOException {
        HttpURLConnection httpURLConnection = null;
        byte[] array2;
        try {
            httpURLConnection = (HttpURLConnection)new URL(s).openConnection(Minecraft.getMinecraft().getProxy());
            httpURLConnection.setDoInput(" ".length() != 0);
            httpURLConnection.setDoOutput("".length() != 0);
            httpURLConnection.connect();
            if (httpURLConnection.getResponseCode() / (0xD4 ^ 0xB0) != "  ".length()) {
                throw new IOException(HttpUtils.I["".length()] + httpURLConnection.getResponseCode());
            }
            final InputStream inputStream = httpURLConnection.getInputStream();
            final byte[] array = new byte[httpURLConnection.getContentLength()];
            int i = "".length();
            do {
                final int read = inputStream.read(array, i, array.length - i);
                if (read < 0) {
                    throw new IOException(HttpUtils.I[" ".length()] + s);
                }
                i += read;
            } while (i < array.length);
            array2 = array;
            "".length();
            if (3 <= 0) {
                throw null;
            }
        }
        finally {
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }
        if (httpURLConnection != null) {
            httpURLConnection.disconnect();
        }
        return array2;
    }
    
    static {
        I();
        SERVER_URL = HttpUtils.I[0x3D ^ 0x34];
        POST_URL = HttpUtils.I[0x24 ^ 0x2E];
    }
}
