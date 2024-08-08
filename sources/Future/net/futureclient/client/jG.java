package net.futureclient.client;

import java.io.Reader;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.InputStream;
import net.futureclient.client.modules.miscellaneous.Translate;
import javax.net.ssl.HttpsURLConnection;
import java.util.Iterator;
import java.net.URL;

public abstract class jG
{
    public static final String K = "UTF-8";
    public static final String M = "&lang=";
    private static String d;
    public static final String a = "&text=";
    public static final String D = "key=";
    public static String k;
    
    public jG() {
        super();
    }
    
    public static void e(final String k) {
        jG.k = k;
    }
    
    public static String e(final URL url, final String s) throws Exception {
        return ((m)L.e(M(url))).get((Object)s).toString();
    }
    
    private static String[] e(final String s, final String s2) throws Exception {
        return M(((e)((m)L.e(s)).get((Object)s2)).M(), null);
    }
    
    private static String[] M(final String s, final String s2) throws Exception {
        final e e;
        final String[] array = new String[(e = (e)L.e(s)).size()];
        int n = 0;
        final Iterator iterator2;
        Iterator<m> iterator = (Iterator<m>)(iterator2 = e.iterator());
        while (iterator.hasNext()) {
            final m next = iterator2.next();
            if (s2 != null && s2.length() != 0) {
                final m m;
                if ((m = next).containsKey((Object)s2)) {
                    array[n] = m.get((Object)s2).toString();
                }
            }
            else {
                array[n] = next.toString();
            }
            ++n;
            iterator = (Iterator<m>)iterator2;
        }
        return array;
    }
    
    private static String M(final URL url) throws Exception {
        final HttpsURLConnection httpsURLConnection = (HttpsURLConnection)url.openConnection();
        if (jG.d != null) {
            httpsURLConnection.setRequestProperty("referer", jG.d);
        }
        final HttpsURLConnection httpsURLConnection2 = httpsURLConnection;
        final String s = "X\fK";
        final String s2 = "'&\u0005 \u00161K\u0006\u000e$\u00146\u00031";
        final HttpsURLConnection httpsURLConnection3 = httpsURLConnection;
        httpsURLConnection3.setRequestProperty(AE.M("%*\b1\u0003+\u0012h2<\u0016 "), "text/plain; charset=UTF-8");
        httpsURLConnection3.setRequestProperty(AE.M(s2), "UTF-8");
        httpsURLConnection2.setRequestMethod(VG.M(s));
        try {
            final HttpsURLConnection httpsURLConnection4 = httpsURLConnection;
            final int responseCode = httpsURLConnection4.getResponseCode();
            final String m = M(httpsURLConnection4.getInputStream());
            if (responseCode != 200) {
                throw new Exception(new StringBuilder().insert(0, AE.M("\u0000\u00147\t7F#\u0014*\u000be?$\b!\u0003=F\u00046\f\\e")).append(m).toString());
            }
            return m;
        }
        catch (Exception ex) {
            final Translate translate;
            if ((translate = (Translate)pg.M().M().M((Class)VC.class)) != null && translate.M()) {
                translate.M(false);
            }
            net.futureclient.client.s.M().M("Disabled Translate due to an error.");
            throw new Exception(new StringBuilder().insert(0, AE.M("\u0000\u00147\t7F#\u0014*\u000be?$\b!\u0003=F\u00046\f\\e")).append(ex.getMessage()).toString());
        }
        finally {
            if (httpsURLConnection != null) {
                httpsURLConnection.disconnect();
            }
        }
    }
    
    public static String M(String s) {
        final StackTraceElement stackTraceElement = new RuntimeException().getStackTrace()[1];
        final String string = new StringBuffer(stackTraceElement.getMethodName()).insert(0, stackTraceElement.getClassName()).toString();
        final int n = string.length() - 1;
        final int n2 = 92;
        final int n3 = 87;
        final int length = (s = s).length();
        final char[] array = new char[length];
        int n4;
        int i = n4 = length - 1;
        final char[] array2 = array;
        final int n5 = n3;
        final int n6 = n2;
        int n7 = n;
        final int n8 = n;
        final String s2 = string;
        while (i >= 0) {
            final char[] array3 = array2;
            final int n9 = n6;
            final String s3 = s;
            final int n10 = n4--;
            array3[n10] = (char)(n9 ^ (s3.charAt(n10) ^ s2.charAt(n7)));
            if (n4 < 0) {
                break;
            }
            final char[] array4 = array2;
            final int n11 = n5;
            final String s4 = s;
            final int n12 = n4;
            final char c = (char)(n11 ^ (s4.charAt(n12) ^ s2.charAt(n7)));
            --n4;
            --n7;
            array4[n12] = c;
            if (n7 < 0) {
                n7 = n8;
            }
            i = n4;
        }
        return new String(array2);
    }
    
    public static void M(final String d) {
        jG.d = d;
    }
    
    public static String M() {
        return jG.k;
    }
    
    private static String M(final InputStream inputStream) throws Exception {
        final StringBuilder sb = new StringBuilder();
        try {
            if (inputStream != null) {
                String line;
                while (null != (line = new BufferedReader(new InputStreamReader(inputStream, "UTF-8")).readLine())) {
                    sb.append(line.replaceAll(AE.M("\ufeba"), ""));
                }
            }
        }
        catch (Exception ex) {
            throw new Exception("[yandex-translator-api] Error reading translation stream.", ex);
        }
        return sb.toString();
    }
    
    public static String M(final URL url, final String s) throws Exception {
        final String[] e = e(M(url), s);
        String string = "";
        final String[] array;
        final int length = (array = e).length;
        int i = 0;
        int n = 0;
        while (i < length) {
            final String s2 = array[n];
            final StringBuilder insert = new StringBuilder().insert(0, string);
            final String s3 = s2;
            ++n;
            string = insert.append(s3).toString();
            i = n;
        }
        return string.trim();
    }
    
    public static void M() throws Exception {
        if (jG.k == null || jG.k.length() < 27) {
            throw new RuntimeException(AE.M("\f(\u0013'\t/\u00019\u00046\f9\u000e#\u001cFhF\u0015\n \u00076\u0003e\u0015 \u0012e\u0012-\u0003e'\u0015/e- \u001fe\u0011,\u0012-F<\t0\u0014e?$\b!\u0003=F\u00046\fF\u000e\u0003<"));
        }
    }
}
