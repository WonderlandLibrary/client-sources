package net.futureclient.client;

import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.io.File;
import java.io.Writer;
import java.io.BufferedWriter;
import java.io.Reader;
import java.io.BufferedReader;
import java.io.OutputStreamWriter;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.Date;
import java.util.Enumeration;
import java.io.IOException;
import net.futureclient.loader.injector.Injector;
import net.futureclient.client.utils.Timer;
import org.apache.logging.log4j.Level;
import java.util.StringTokenizer;
import java.util.Hashtable;
import java.net.InetAddress;

public abstract class nH implements J
{
    private boolean lB;
    private t wC;
    private static final int kb = 1;
    private static final int Bb = 3;
    private String Id;
    private long Vc;
    private String qc;
    private InetAddress bC;
    private String Kb;
    private boolean JC;
    private int[] JB;
    private static final int SB = 2;
    private static final int HC = 4;
    private W Rc;
    public static final String kA;
    private String hd;
    private String nd;
    private r QA;
    private InetAddress Ic;
    private String dc;
    private p ec;
    private String xb;
    private Hashtable lc;
    private String oA;
    private int RC;
    private Hashtable D;
    private String k;
    
    public void A(final String s, final String s2, final String s3, final String s4) {
    }
    
    private void A(final String s, final String s2, final String s3, final String s4, final String s5) {
        if (this.xb.indexOf(s.charAt(0)) >= 0) {
            final StringTokenizer stringTokenizer;
            final String[] array = new String[(stringTokenizer = new StringTokenizer(s5)).countTokens()];
            int n = 0;
            StringTokenizer stringTokenizer2 = stringTokenizer;
            while (stringTokenizer2.hasMoreTokens()) {
                final String[] array2 = array;
                final int n2 = n;
                final String nextToken = (stringTokenizer2 = stringTokenizer).nextToken();
                ++n;
                array2[n2] = nextToken;
            }
            int n3 = 32;
            int n4 = 1;
            int i = 0;
            int n5 = 0;
            while (i < array[0].length()) {
                final char char1;
                if ((char1 = array[0].charAt(n5)) == '+' || char1 == '-') {
                    n3 = char1;
                }
                else if (char1 == 'o') {
                    if (n3 == 43) {
                        this.M(s, 1, array[n4]);
                        this.H(s, s2, s3, s4, array[n4]);
                    }
                    else {
                        this.M(s, 2, array[n4]);
                        this.C(s, s2, s3, s4, array[n4]);
                    }
                    ++n4;
                }
                else if (char1 == 'v') {
                    if (n3 == 43) {
                        this.M(s, 3, array[n4]);
                        this.a(s, s2, s3, s4, array[n4]);
                    }
                    else {
                        this.M(s, 4, array[n4]);
                        this.h(s, s2, s3, s4, array[n4]);
                    }
                    ++n4;
                }
                else if (char1 == 'k') {
                    if (n3 == 43) {
                        this.B(s, s2, s3, s4, array[n4]);
                    }
                    else {
                        this.K(s, s2, s3, s4, array[n4]);
                    }
                    ++n4;
                }
                else if (char1 == 'l') {
                    if (n3 == 43) {
                        this.M(s, s2, s3, s4, Integer.parseInt(array[n4++]));
                    }
                    else {
                        this.L(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'b') {
                    if (n3 == 43) {
                        this.m(s, s2, s3, s4, array[n4]);
                    }
                    else {
                        this.b(s, s2, s3, s4, array[n4]);
                    }
                    ++n4;
                }
                else if (char1 == 't') {
                    if (n3 == 43) {
                        this.j(s, s2, s3, s4);
                    }
                    else {
                        this.A(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'n') {
                    if (n3 == 43) {
                        this.H(s, s2, s3, s4);
                    }
                    else {
                        this.i(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'i') {
                    if (n3 == 43) {
                        this.c(s, s2, s3, s4);
                    }
                    else {
                        this.B(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'm') {
                    if (n3 == 43) {
                        this.h(s, s2, s3, s4);
                    }
                    else {
                        this.K(s, s2, s3, s4);
                    }
                }
                else if (char1 == 'p') {
                    if (n3 == 43) {
                        this.k(s, s2, s3, s4);
                    }
                    else {
                        this.C(s, s2, s3, s4);
                    }
                }
                else if (char1 == 's') {
                    if (n3 == 43) {
                        this.l(s, s2, s3, s4);
                    }
                    else {
                        this.b(s, s2, s3, s4);
                    }
                }
                i = ++n5;
            }
            this.g(s, s2, s3, s4, s5);
            return;
        }
        this.i(s, s2, s3, s4, s5);
    }
    
    public void A(final String s) {
        if (this.lB) {
            s.M().M(Level.INFO, System.currentTimeMillis() + "\u0004" + s);
        }
    }
    
    public void A(final String s, final String s2) {
        this.M(s + "\u0004" + s2);
    }
    
    public nH() {
        final String s = "\u001ci2H";
        final String s2 = "=\u0014YHP\u001cY\u0000KOL\u0017\u000f\u001cLQG\u0006Q\u001d\\\u0007A\r\u001eU\u000b\f\t\u0017O\u001aD\u001d]\u001aJ\u000e\u001eRD\u0011K!#";
        final String s3 = "r1K:W0<cV*w\u001d";
        final String s4 = ">Z\u0007Q\u001b[p\b\u001aA;v";
        final String s5 = "r1K:W0<cV*w\u001d";
        final boolean lb = false;
        final boolean jc = false;
        final InetAddress bc = null;
        final int[] jb = null;
        final long vc = 1000L;
        final String nd = null;
        final int rc = -1;
        final String k = null;
        final InetAddress ic = null;
        final String dc = null;
        final t wc = null;
        final W rc2 = null;
        super();
        this.Rc = rc2;
        this.wC = wc;
        this.dc = dc;
        this.Ic = ic;
        this.k = k;
        this.RC = rc;
        this.nd = nd;
        this.QA = new r();
        this.Vc = vc;
        this.D = new Hashtable();
        this.lc = new Hashtable();
        this.ec = new p(this);
        this.JB = jb;
        this.bC = bc;
        this.JC = jc;
        this.lB = lb;
        this.Kb = Timer.M(s5);
        this.oA = this.Kb;
        this.qc = Injector.M(s4);
        this.hd = Timer.M(s3);
        this.Id = Injector.M(s2);
        this.xb = Timer.M(s);
    }
    
    static {
        kA = "r0+\n\u0014";
    }
    
    @Override
    public boolean equals(final Object o) {
        return o instanceof nH && o == this;
    }
    
    @Override
    public String toString() {
        return new StringBuilder().insert(0, Injector.M("?[A\u0017\u001aK;y")).append(this.hd).append("w*_sSS& j{@_").append(this.M()).append(Injector.M("YImV\u0016\u0005A'y")).append(this.k).append("@c\u0013qlP_").append(this.RC).append(Injector.M("RSt\bM@\u0013\u001cV1y")).append(this.nd).append("Y").toString();
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    private void B() {
        synchronized (this.D) {
            this.D = new Hashtable();
        }
    }
    
    public String B() {
        return this.nd;
    }
    
    public void B(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void B(final String s, final String s2) {
        this.a(s, Injector.M("\u000f#\"") + s2);
    }
    
    public void B(final String s, final String s2, final String s3, final String s4) {
    }
    
    public synchronized void B(final String s) throws IOException, X, S {
        this.e(s, 6667, null);
    }
    
    public void C() {
        this.l("");
    }
    
    public void C(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public String C() {
        return this.oA;
    }
    
    public void C(final String s, final String s2) {
        this.QA.e(new StringBuilder().insert(0, "mo\n\u0015SMc\u0004").append(s).append(Injector.M("u8")).append(s2).toString());
    }
    
    private void C(final String oa) {
        this.oA = oa;
    }
    
    public void C(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void D(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void D(final String s, final String s2, final String s3, final String s4, final String s5) {
        this.i(Injector.M("p|0:g\u0010\"") + s + "\u001d\u0007B\u0013WPc\u0004" + s5 + Injector.M("\u0003"));
    }
    
    public void D(final String s, final String s2) {
        this.i("\u0013_Lp\u0004" + s + Injector.M("u8") + s2);
    }
    
    public final void D(final String hd) {
        this.hd = hd;
    }
    
    public void J(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void J(final String s, final String s2) {
        this.i(Injector.M(")<`\u0010\"") + s + ">5F\u0004" + s2);
    }
    
    public void J(final String s) {
        this.i("HO\\TI_X\\<Uyx\r\u0017WX}\u0004" + s);
    }
    
    public void J(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void c(final String s) {
        final String s2 = "4:j\u0012\"";
        this.A(s);
        if (s.startsWith(Injector.M(s2))) {
            this.m(s.substring(5));
            return;
        }
        String s3 = "";
        String substring = "";
        String substring2 = "";
        final StringTokenizer stringTokenizer2;
        final StringTokenizer stringTokenizer = stringTokenizer2 = new StringTokenizer(s);
        final String nextToken = stringTokenizer.nextToken();
        final String nextToken2 = stringTokenizer.nextToken();
        String s4 = null;
        final String s5 = nextToken;
        final int index = s5.indexOf("\u0005");
        final int index2 = s5.indexOf(Injector.M("B"));
        String s7;
        if (s5.startsWith("\u001e")) {
            if (index > 0 && index2 > 0 && index < index2) {
                final String s6 = nextToken;
                s3 = s6.substring(1, index);
                substring = s6.substring(index + 1, index2);
                substring2 = s6.substring(index2 + 1);
                s7 = nextToken2;
            }
            else {
                if (!stringTokenizer2.hasMoreTokens()) {
                    this.j(s);
                    return;
                }
                final String s8 = nextToken2;
                int n = -1;
                int int1;
                try {
                    n = (int1 = Integer.parseInt(s8));
                }
                catch (NumberFormatException ex) {
                    int1 = n;
                }
                if (int1 != -1) {
                    this.e(n, s.substring(s.indexOf(s8, nextToken.length()) + 4, s.length()));
                    return;
                }
                s3 = nextToken;
                s4 = s8;
                s7 = nextToken2;
            }
        }
        else {
            s7 = nextToken2;
        }
        final String upperCase = s7.toUpperCase();
        if (s3.startsWith(Injector.M("8"))) {
            s3 = s3.substring(1);
        }
        if (s4 == null) {
            s4 = stringTokenizer2.nextToken();
        }
        if (s4.startsWith("\u001e")) {
            s4 = s4.substring(1);
        }
        if (upperCase.equals(Injector.M("na-%i\u0006E")) && s.indexOf("\u001e%") > 0 && s.endsWith(Injector.M("\u0003"))) {
            final String substring3;
            if ((substring3 = s.substring(s.indexOf("\u001e%") + 2, s.length() - 1)).equals(Injector.M("hv6 m\u001aL"))) {
                this.m(s3, substring, substring2, s4);
                return;
            }
            if (substring3.startsWith("|\u0000\u0017WQj\u0004")) {
                this.J(s3, substring, substring2, s4, substring3.substring(7));
                return;
            }
            if (substring3.startsWith(Injector.M("4:j\u0012\""))) {
                this.D(s3, substring, substring2, s4, substring3.substring(5));
                return;
            }
            if (substring3.equals("JWia")) {
                this.e(s3, substring, substring2, s4);
                return;
            }
            if (substring3.equals(Injector.M("u-=c\u0010P"))) {
                this.f(s3, substring, substring2, s4);
                return;
            }
            final StringTokenizer stringTokenizer3;
            if ((stringTokenizer3 = new StringTokenizer(substring3)).countTokens() < 5 || !stringTokenizer3.nextToken().equals("Zgg")) {
                this.j(s);
                return;
            }
            if (!this.ec.M(s3, substring, substring2, substring3)) {
                this.j(s);
            }
        }
        else {
            if (upperCase.equals(Injector.M("na-%i\u0006E")) && this.xb.indexOf(s4.charAt(0)) >= 0) {
                this.M(s4, s3, substring, substring2, s.substring(s.indexOf("\u0004\u001e") + 2));
                return;
            }
            if (upperCase.equals(Injector.M("na-%i\u0006E"))) {
                this.D(s3, substring, substring2, s.substring(s.indexOf("\u0004\u001e") + 2));
                return;
            }
            if (upperCase.equals(Injector.M("9k\u001cL"))) {
                final String s9 = s4;
                final String s10 = s3;
                this.M(s9, new Y("", s3));
                this.a(s9, s10, substring, substring2);
                return;
            }
            if (upperCase.equals("N_vp")) {
                final String s11 = s3;
                this.M(s4, s3);
                if (s11.equals(this.C())) {
                    this.b(s4);
                }
                this.M(s4, s3, substring, substring2);
                return;
            }
            if (upperCase.equals(Injector.M("=m\u0016I"))) {
                final String s12 = s4;
                final String s13 = s3;
                this.c(s13, s12);
                if (s13.equals(this.C())) {
                    this.C(s12);
                }
                this.J(s3, substring, substring2, s12);
                return;
            }
            if (upperCase.equals("\r\fJWga")) {
                this.c(s3, substring, substring2, s4, s.substring(s.indexOf(Injector.M("u8")) + 2));
                return;
            }
            if (upperCase.equals("OKmp")) {
                nH nh;
                if (s3.equals(this.C())) {
                    this.B();
                    nh = this;
                }
                else {
                    this.g(s3);
                    nh = this;
                }
                nh.g(s3, substring, substring2, s.substring(s.indexOf(Injector.M("u8")) + 2));
                return;
            }
            if (upperCase.equals("UWgo")) {
                final String nextToken3;
                if ((nextToken3 = stringTokenizer2.nextToken()).equals(this.C())) {
                    this.b(s4);
                }
                this.M(s4, nextToken3);
                this.M(s4, s3, substring, substring2, nextToken3, s.substring(s.indexOf(Injector.M("u8")) + 2));
                return;
            }
            if (upperCase.equals("SQ`a")) {
                final String s14 = s;
                String s15;
                if ((s15 = s14.substring(s14.indexOf(s4, 2) + s4.length() + 1)).startsWith(Injector.M("8"))) {
                    s15 = s15.substring(1);
                }
                this.A(s4, s3, substring, substring2, s15);
                return;
            }
            if (upperCase.equals("\u0017QNmg")) {
                this.M(s4, s.substring(s.indexOf(Injector.M("u8")) + 2), s3, System.currentTimeMillis(), true);
                return;
            }
            if (upperCase.equals("\n\rHWpa")) {
                this.e(s4, s3, substring, substring2, s.substring(s.indexOf(Injector.M("u8")) + 2));
                return;
            }
            this.j(s);
        }
    }
    
    public void c(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void c(final String s, final String s2, final String s3, final String s4) {
    }
    
    private void c(final String s, final String s2) {
        synchronized (this.D) {
            final Enumeration<String> keys = this.D.keys();
            while (keys.hasMoreElements()) {
                final String s3 = keys.nextElement();
                final Y m;
                if ((m = this.M(s3, s)) != null) {
                    this.M(s3, new Y(m.e(), s2));
                }
            }
        }
    }
    
    public void c() {
    }
    
    public void m(final String s) {
        this.i(Injector.M("4<j\u0012\"") + s);
    }
    
    public void m(final String s, final String s2, final String s3, final String s4) {
        this.i(Injector.M("p|0:g\u0010\"") + s + "*&\u001dkx\u0011\u0010WQj\u0004" + this.hd + Injector.M("\u0003"));
    }
    
    public void m(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void m(final String s, final String s2) {
        this.a(s, "3K\u0004" + s2);
    }
    
    public void a(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void a(final String kb) {
        this.Kb = kb;
    }
    
    public void a(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void a(final String s, final String s2) {
        this.i("\u000eQZa\u0004" + s + Injector.M("\"") + s2);
    }
    
    public int b() {
        return this.QA.M();
    }
    
    public String b() {
        return this.Id;
    }
    
    public void b(final String s, final int n, final String s2) {
    }
    
    public void b(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void b(final String s, final String s2) {
        this.M(s, s2, "");
    }
    
    public synchronized void b() {
        this.C();
    }
    
    private void b(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (this.D) {
            this.D.remove(lowerCase);
        }
    }
    
    public void b(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void H(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void H(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void H(final String s, final String s2) {
        this.a(s, Injector.M("\u000f:\"") + s2);
    }
    
    public void H(final String s) {
        if (s == null) {
            this.i(Injector.M("?m\u0006V"));
            return;
        }
        this.i(new StringBuilder().insert(0, "\u000fWMp\u0004").append(s).toString());
    }
    
    public InetAddress e() {
        return this.Ic;
    }
    
    public String e() {
        return this.k;
    }
    
    public int e() {
        return 512;
    }
    
    public synchronized void e(final String s) {
        if (s == null) {
            throw new NullPointerException("|anyxp$ucoe\u000fAb{!mkcll{}oy<hR\u001d0&lhAV");
        }
        if (this.M()) {
            this.QA.e(s);
        }
    }
    
    public void e(final String s, final String s2, final String s3, final String s4) {
        this.i("s\f\u0017W]a\u0004" + s + Injector.M("I\u000420:i\u0010\"") + new Date().toString() + "%");
    }
    
    public synchronized void e() throws IOException, X, S {
        if (this.e() == null) {
            throw new X(Injector.M("2G\u001aM\u0017[SV\f]\\\n\u0010L\u0012RTW\u0017\u000f\u0012JIwa'SW0p\u000eJ\u0001\u0004\u001aJ\n_F\u0017\u001e\f\u001fZII\rM\n\u0018\u0016J\bL\u0003\u0006\u0017L\u0016A\u0016G\u001d[WD\nFQI\u001aFX_\u0001A\u001fW\\\u0011\u0000H,#"));
        }
        this.e(this.e(), this.M(), this.B());
    }
    
    public void e(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void e(final String s, final String s2) {
        this.i("\u0017\fNWg\u0004" + s + Injector.M("u8") + s2);
    }
    
    public synchronized void e(final String k, final int rc, String s) throws IOException, X, S {
        final String nd = s;
        this.k = k;
        this.RC = rc;
        this.nd = nd;
        if (this.M()) {
            throw new IOException(Injector.M("r\u001cFX\u007f\u001aV\n|\\\u0010SM&\"\u0019C\u0001A\u0019K\u0010\u001eP\u000b\u0015B\r\\\u001d[\f\u001f\u001bWXN\u0010\t8t7\u0003\u000bJ\u0001R\fL\u001dD^m\u0018U\u0017L\u0016A\u0016G\u001d\u001eU\r\u0001W!,"));
        }
        this.B();
        final Socket socket = new Socket(k, rc);
        this.A("\u0005\u0005=7\u000e\"hhz|n\u007fn*hs\u001dN&1h{V\n");
        this.Ic = socket.getLocalAddress();
        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamWriter;
        if (this.g() != null) {
            inputStreamReader = new InputStreamReader(socket.getInputStream(), this.g());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), this.g());
        }
        else {
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        }
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        if (s != null && !s.equals("")) {
            net.futureclient.client.t.M(this, bufferedWriter, Injector.M("42w\u0006\"") + s);
        }
        s = this.M();
        net.futureclient.client.t.M(this, bufferedWriter, new StringBuilder().insert(0, "\rW]o\u0004").append(s).toString());
        net.futureclient.client.t.M(this, bufferedWriter, new StringBuilder().insert(0, Injector.M("1 a\u0007\"")).append(this.i()).append("c{>4\u0004\u001e").append(this.h()).toString());
        this.Rc = new W(this, socket, bufferedReader, bufferedWriter);
        int n = 1;
        BufferedReader bufferedReader2 = bufferedReader;
        while (true) {
            String line;
            while ((line = bufferedReader2.readLine()) != null) {
                final String s2 = line;
                this.c(s2);
                final int index = s2.indexOf(Injector.M("\""));
                nH nh2 = null;
                Label_0560: {
                    final int index2;
                    if ((index2 = s2.indexOf("\u0004", index + 1)) >= 0) {
                        final String substring;
                        if ((substring = line.substring(index + 1, index2)).equals(Injector.M("\u0014e6"))) {
                            final nH nh = this;
                            nh.A("\u0005==m\u0001iaxz~:edhs\u001dN&1h{V\n");
                            socket.setSoTimeout(300000);
                            this.Rc.start();
                            if (this.wC == null) {
                                (this.wC = new t(this, this.QA)).start();
                            }
                            this.h();
                            return;
                        }
                        if (substring.equals("*\u0017\u0017")) {
                            if (this.JC) {
                                final StringBuilder sb = new StringBuilder();
                                ++n;
                                s = sb.insert(0, this.M()).append(n).toString();
                                nh2 = this;
                                net.futureclient.client.t.M(this, bufferedWriter, new StringBuilder().insert(0, Injector.M("*:g\u001e\"")).append(s).toString());
                                break Label_0560;
                            }
                            socket.close();
                            this.Rc = null;
                            throw new S(line);
                        }
                        else {
                            if (substring.equals("*\u0017\u001d")) {
                                nh2 = this;
                                break Label_0560;
                            }
                            if (substring.startsWith(Injector.M("7")) || substring.startsWith("\u0010")) {
                                socket.close();
                                this.Rc = null;
                                throw new X(new StringBuilder().insert(0, Injector.M("{\u0017Z\u0012MQH\u001bWXC\u001cCIW]\u0010\u0011\t\u0005N\u0011\u00031}0\u0004\u001a[A\u0012\u0016Vo\"")).append(line).toString());
                            }
                        }
                    }
                    nh2 = this;
                }
                nh2.C(s);
                bufferedReader2 = bufferedReader;
            }
            final nH nh = this;
            continue;
        }
    }
    
    public void e(final boolean jc) {
        this.JC = jc;
    }
    
    private void e(final int n, final String s) {
        nH nh;
        if (n == 322) {
            final int index = s.indexOf(32);
            final int index2 = s.indexOf(32, index + 1);
            final int index3 = s.indexOf(32, index2 + 1);
            final int index4 = s.indexOf(58);
            final String substring = s.substring(index + 1, index2);
            int int1 = 0;
            String s2;
            try {
                int1 = Integer.parseInt(s.substring(index2 + 1, index3));
                s2 = s;
            }
            catch (NumberFormatException ex) {
                s2 = s;
            }
            final String substring2 = s2.substring(index4 + 1);
            nh = this;
            this.b(substring, int1, substring2);
        }
        else if (n == 332) {
            final int index5 = s.indexOf(32);
            final int index6 = s.indexOf(32, index5 + 1);
            final int index7 = s.indexOf(58);
            final String substring3 = s.substring(index5 + 1, index6);
            final String substring4 = s.substring(index7 + 1);
            this.lc.put(substring3, substring4);
            nh = this;
            this.l(substring3, substring4);
        }
        else if (n == 333) {
            final StringTokenizer stringTokenizer;
            (stringTokenizer = new StringTokenizer(s)).nextToken();
            final StringTokenizer stringTokenizer2 = stringTokenizer;
            final String nextToken = stringTokenizer2.nextToken();
            final String nextToken2 = stringTokenizer2.nextToken();
            long n2 = 0L;
            nH nh2;
            try {
                n2 = Long.parseLong(stringTokenizer.nextToken()) * 1000L;
                nh2 = this;
            }
            catch (NumberFormatException ex2) {
                nh2 = this;
            }
            final String s3 = nh2.lc.get(nextToken);
            this.lc.remove(nextToken);
            nh = this;
            this.M(nextToken, s3, nextToken2, n2, false);
        }
        else {
            if (n == 353) {
                final int index8 = s.indexOf("\u0004\u001e");
                final String substring5 = s.substring(s.lastIndexOf(32, index8 - 1) + 1, index8);
                final StringTokenizer stringTokenizer4;
                StringTokenizer stringTokenizer3 = stringTokenizer4 = new StringTokenizer(s.substring(s.indexOf(Injector.M("u8")) + 2));
                while (stringTokenizer3.hasMoreTokens()) {
                    final String nextToken3 = stringTokenizer4.nextToken();
                    String s4 = "";
                    String s5;
                    if (nextToken3.startsWith("d")) {
                        s4 = Injector.M("B");
                        s5 = nextToken3;
                    }
                    else if (nextToken3.startsWith("\u000f")) {
                        s4 = Injector.M(")");
                        s5 = nextToken3;
                    }
                    else {
                        if (nextToken3.startsWith(".")) {
                            s4 = ".";
                        }
                        s5 = nextToken3;
                    }
                    final String substring6 = s5.substring(s4.length());
                    stringTokenizer3 = stringTokenizer4;
                    this.M(substring5, new Y(s4, substring6));
                }
            }
            else if (n == 366) {
                final String substring7 = s.substring(s.indexOf(32) + 1, s.indexOf("\u0004\u001e"));
                this.M(substring7, this.M(substring7));
            }
            nh = this;
        }
        nh.M(n, s);
    }
    
    public final void f(final String qc) {
        this.qc = qc;
    }
    
    public void f(final String s, final String s2, final String s3, final String s4) {
        this.i(Injector.M("p|0:g\u0010\"") + s + "<&<{\n\rY[v\u0004" + this.Id + Injector.M("\u0003"));
    }
    
    public void i(final String s, final String s2, final String s3, final String s4) {
    }
    
    public synchronized void i() {
        this.wC.interrupt();
        this.Rc.M();
    }
    
    public void i(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public String i() {
        return this.qc;
    }
    
    public synchronized void i(final String s) {
        if (this.M()) {
            this.Rc.M(s);
        }
    }
    
    public void i(final String s, final String s2) {
        this.i("t\r\u0015WJa\u0004" + s + Injector.M("u8") + s2);
    }
    
    public void j(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void j(final String s) {
    }
    
    public void l(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void l(final String s, final String s2) {
    }
    
    public void l(final String s) {
        this.i(Injector.M("b1:pu8") + s);
    }
    
    public String g() {
        return this.dc;
    }
    
    private void g(final String s) {
        synchronized (this.D) {
            final Enumeration<String> keys;
            Enumeration<String> enumeration = keys = this.D.keys();
            while (enumeration.hasMoreElements()) {
                final String s2 = keys.nextElement();
                enumeration = keys;
                this.M(s2, s);
            }
        }
    }
    
    public void g(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void g() {
        this.H(null);
    }
    
    public void g(final String s, final String s2) {
        this.QA.e(new StringBuilder().insert(0, "s\f\u0017W]a\u0004").append(s).append(Injector.M("u8")).append(s2).toString());
    }
    
    public void g(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void K(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void K(final String s) {
        this.i("\rW]o\u0004" + s);
    }
    
    public void K(final String s, final String s2) {
        this.M(s, Injector.M("\u007fp0:k\u001b\"") + s2);
    }
    
    public void K(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void M(final long vc) {
        if (vc < 0L) {
            throw new IllegalArgumentException(Injector.M("7B\u0016A\u001cPIVR\u0012\u001b\t\u0010\u0006\u001aF\u001fN\u0007M\u001f[\u0013\u0010\u001aI0,"));
        }
        this.Vc = vc;
    }
    
    public String M() {
        return this.Kb;
    }
    
    public void M() {
        new V(this, this.i());
    }
    
    public String[] M() {
        final String[] array = new String[0];
        synchronized (this.D) {
            final String[] array2 = new String[this.D.size()];
            final Enumeration<String> keys = this.D.keys();
            int i = 0;
            int n = 0;
            while (i < array2.length) {
                final String[] array3 = array2;
                final int n2 = n;
                final String s = keys.nextElement();
                ++n;
                array3[n2] = s;
                i = n;
            }
            return array2;
        }
    }
    
    public void M(final String s, final String s2, final String s3, final String s4, final String s5, final String s6) {
    }
    
    public void M(final String s, final Y[] array) {
    }
    
    public void M(final String s, final String s2, final String s3, final String s4, final long n, final int n2, final int n3) {
    }
    
    public U M(final File file, final String s, final int n) {
        final U u = new U(this, this.ec, file, s, n);
        u.M(true);
        return u;
    }
    
    public void M(final String s, final String s2, final String s3) {
        this.i(Injector.M("/:g\u001e\"") + s + "\u0004" + s2 + Injector.M("u8") + s3);
    }
    
    public x M(final String s, final int soTimeout) {
        x x = null;
        try {
            ServerSocket serverSocket = null;
            ServerSocket serverSocket3 = null;
            Label_0089: {
                final int[] m;
                if ((m = this.M()) != null) {
                    int i = 0;
                    int n = 0;
                Label_0073:
                    while (true) {
                        while (i < m.length) {
                            try {
                                final ServerSocket serverSocket2;
                                serverSocket = (serverSocket2 = new ServerSocket(m[n]));
                                break Label_0073;
                            }
                            catch (Exception ex) {
                                i = ++n;
                                continue;
                            }
                            break;
                            ServerSocket serverSocket2 = null;
                            if (serverSocket2 == null) {
                                throw new IOException("{##2bsnvq0bZKuryr`$d\u007f!fJ[St.\u001ditkl23*kny\u001dT-ckmA\n");
                            }
                            serverSocket3 = serverSocket;
                            break Label_0089;
                        }
                        ServerSocket serverSocket2 = serverSocket;
                        continue Label_0073;
                    }
                }
                serverSocket3 = (serverSocket = new ServerSocket(0));
            }
            serverSocket3.setSoTimeout(soTimeout);
            final int localPort = serverSocket.getLocalPort();
            InetAddress inetAddress;
            if ((inetAddress = this.M()) == null) {
                inetAddress = this.e();
            }
            final byte[] address = inetAddress.getAddress();
            final ServerSocket serverSocket4 = serverSocket;
            this.M(s, Injector.M("0`;\u000f0l(j\u0013\u0007\u001bE!\"") + this.M(address) + "\u0004" + localPort);
            final Socket accept = serverSocket.accept();
            serverSocket4.close();
            x = new x(this, s, accept);
            return x;
        }
        catch (Exception ex2) {
            return x;
        }
    }
    
    public long M() {
        return this.Vc;
    }
    
    public void M(final int n, final String s) {
    }
    
    public void M(final String s, final String s2) {
        this.QA.e(new StringBuilder().insert(0, "mo\n\u0015SMc\u0004").append(s).append(Injector.M("\u0004o\u0003")).append(s2).append("%").toString());
    }
    
    public int M() {
        return this.RC;
    }
    
    public void M(final U u, final Exception ex) {
    }
    
    public void M(final String s) {
        this.i(Injector.M(".<m\u001b\"") + s);
    }
    
    public void M(final String s, final String s2, final String s3, final String s4) {
    }
    
    public int[] M() {
        if (this.JB == null || this.JB.length == 0) {
            return null;
        }
        return this.JB.clone();
    }
    
    private Y M(String lowerCase, final String s) {
        lowerCase = lowerCase.toLowerCase();
        final Y y = new Y("", s);
        synchronized (this.D) {
            final Hashtable<Object, Y> hashtable;
            if ((hashtable = this.D.get(lowerCase)) != null) {
                return hashtable.remove(y);
            }
        }
        return null;
    }
    
    public void M(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public Y[] M(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        Y[] array = new Y[0];
        synchronized (this.D) {
            final Hashtable<Object, Y> hashtable;
            if ((hashtable = this.D.get(lowerCase)) != null) {
                array = new Y[hashtable.size()];
                final Enumeration<Y> elements = hashtable.elements();
                int i = 0;
                int n = 0;
                while (i < array.length) {
                    final Y y = elements.nextElement();
                    final Y[] array2 = array;
                    final int n2 = n;
                    final Y y2 = y;
                    ++n;
                    array2[n2] = y2;
                    i = n;
                }
            }
            return array;
        }
    }
    
    public void M(final boolean lb) {
        this.lB = lb;
    }
    
    public void M(final String s, final String s2, final String s3, final String s4, final int n) {
    }
    
    public int[] M(long n) {
        final int[] array = new int[4];
        int i = 3;
        int n2 = 3;
        while (i >= 0) {
            final int[] array2 = array;
            final int n3 = n2;
            final long n4 = n;
            array2[n3] = (int)(n4 % 256L);
            --n2;
            n = n4 / 256L;
            i = n2;
        }
        return array;
    }
    
    public void M(final U u) {
    }
    
    public void M(final x x) {
    }
    
    public void M(final InetAddress bc) {
        this.bC = bc;
    }
    
    public synchronized void M(final String s, final int n) throws IOException, X, S {
        this.e(s, n, null);
    }
    
    private void M(String lowerCase, final int n, final String s) {
        lowerCase = lowerCase.toLowerCase();
        final Hashtable d = this.D;
        synchronized (d) {
            final Hashtable<Object, Y> hashtable = this.D.get(lowerCase);
            Y y = null;
            if (hashtable != null) {
                final Enumeration<Y> elements = hashtable.elements();
                while (elements.hasMoreElements()) {
                    final Y y2;
                    if ((y2 = elements.nextElement()).M().equalsIgnoreCase(s)) {
                        if (n == 1) {
                            if (y2.M()) {
                                y = new Y("d\u000f", s);
                            }
                            else {
                                y = new Y(Injector.M("B"), s);
                            }
                        }
                        else if (n == 2) {
                            if (y2.M()) {
                                y = new Y("\u000f", s);
                            }
                            else {
                                y = new Y("", s);
                            }
                        }
                        else if (n == 3) {
                            if (y2.e()) {
                                y = new Y(Injector.M("\u0015)"), s);
                            }
                            else {
                                y = new Y("\u000f", s);
                            }
                        }
                        else {
                            if (n != 4) {
                                continue;
                            }
                            if (y2.e()) {
                                y = new Y(Injector.M("B"), s);
                            }
                            else {
                                y = new Y("", s);
                            }
                        }
                    }
                }
            }
            if (y != null) {
                final Hashtable<Object, Y> hashtable2 = hashtable;
                final Y y3 = y;
                hashtable2.put(y3, y3);
            }
            else {
                final Y y4 = new Y("", s);
                final Hashtable<Object, Y> hashtable3 = hashtable;
                final Y y5 = y4;
                hashtable3.put(y5, y5);
            }
        }
    }
    
    public void M(final String s, final String s2, final String s3, final long n, final boolean b) {
    }
    
    private void M(String lowerCase, final Y y) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (this.D) {
            Hashtable<Y, Y> hashtable;
            if ((hashtable = this.D.get(lowerCase)) == null) {
                hashtable = new Hashtable<Y, Y>();
                this.D.put(lowerCase, hashtable);
            }
            hashtable.put(y, y);
        }
    }
    
    public final void M(final File file, final long n, final int n2, final int n3) {
        throw new RuntimeException(Injector.M("K\n]a\u0001\u0018I\u0001I\fx\u0001S\n\u0018\u0011\\^M\u0014V\u0006F\u001bN\u0007A\r\u0012\u0013\u0014\u0012L\u0010U\u0011\u0003\r\\\u0016\u0004\u001a[]\u00005M9g"));
    }
    
    public void M(final String s, final String s2, final String s3, final long n, final int n2) {
    }
    
    public void M(final int[] array) {
        if (array == null || array.length == 0) {
            this.JB = null;
            return;
        }
        this.JB = array.clone();
    }
    
    public long M(final byte[] array) {
        if (array.length != 4) {
            throw new IllegalArgumentException("f}rc!`]]vnm suk?x\u007f*ez<QX-$jv\u0004\u0010");
        }
        long n = 0L;
        long n2 = 1L;
        int i = 3;
        int n3 = 3;
        while (i >= 0) {
            n += (array[n3] + 256) % 256 * n2;
            final long n4 = n2;
            --n3;
            n2 = n4 * 256L;
            i = n3;
        }
        return n;
    }
    
    public final x M(final String s, final long n, final int n2) {
        throw new RuntimeException(Injector.M("\u0010@\u001bn\u0010G\fNG'\u001bE!P5v\u0006A\u000b[IW@D\u001fI\u0018M\f]\tK\n\\T\u000f\u000eE\u0014G\u0007FXZ\u0000AIQ]-\u0010J\u001eK\u001dM\u001fl\u001bE\u001dlV\u0015\u0006A&v"));
    }
    
    public InetAddress M() {
        return this.bC;
    }
    
    public synchronized boolean M() {
        return this.Rc != null && this.Rc.M();
    }
    
    public void L(final String dc) throws UnsupportedEncodingException {
        "".getBytes(dc);
        this.dc = dc;
    }
    
    public void L(final String s, final String s2) {
        this.a(s, "3R\u0004" + s2);
    }
    
    public void L(final String s, final String s2, final String s3, final String s4) {
    }
    
    public void k(final String s) {
        this.i("\u0013_Lp\u0004" + s);
    }
    
    public void k(final String s, final String s2, final String s3, final String s4) {
    }
    
    public String h() {
        return this.hd;
    }
    
    public void h(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    public void h() {
    }
    
    public void h(final String s, final String s2) {
        this.i("\u000eQZa\u0004" + s + Injector.M("S\t7\"") + s2);
    }
    
    public void h(final String s, final String s2, final String s3, final String s4) {
    }
    
    public final void h(final String id) {
        this.Id = id;
    }
}
