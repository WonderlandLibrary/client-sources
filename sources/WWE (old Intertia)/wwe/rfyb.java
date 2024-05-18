package wwe;

import java.net.*;
import java.io.*;
import java.util.*;

public abstract class rfyb implements BKlt
{
    public static final String BDTz = "1.5.0";
    private static final int tnFA = 1;
    private static final int FjOi = 2;
    private static final int gXhJ = 3;
    private static final int iDzb = 4;
    private vQFC bPov;
    private BFWw LPpH;
    private String bxpu;
    private InetAddress LjvD;
    private String lrzZ;
    private int AvoA;
    private String SXDA;
    private URoz URXY;
    private long VHfG;
    private Hashtable pQVz;
    private Hashtable lHME;
    private iroq Plsg;
    private int[] MPej;
    private InetAddress pSZG;
    private boolean eKQC;
    private boolean gCyg;
    private String OZdd;
    private String gKnm;
    private String LcVh;
    private String ZVeq;
    private String iSXy;
    private String hWrF;
    private ArrayList<Jhbg> CDRv;
    private int KBvr;
    private boolean Rtwr;
    
    public rfyb() {
        super();
        this.bPov = null;
        this.LPpH = null;
        this.bxpu = null;
        this.LjvD = null;
        this.lrzZ = null;
        this.AvoA = -1;
        this.SXDA = null;
        this.URXY = new URoz();
        this.VHfG = (0x84A3404DE25C0027L ^ 0x84A3404DE25C02ADL);
        this.pQVz = new Hashtable();
        this.lHME = new Hashtable();
        this.Plsg = new iroq(this);
        this.MPej = null;
        this.pSZG = null;
        this.eKQC = false;
        this.gCyg = false;
        this.OZdd = "PircBot";
        this.gKnm = this.OZdd;
        this.LcVh = "PircBot";
        this.ZVeq = "PircBot 1.5.0 Java IRC Bot - www.jibble.org";
        this.iSXy = "You ought to be arrested for fingering a bot!";
        this.hWrF = "#&+!";
        this.CDRv = new ArrayList<Jhbg>();
        this.KBvr = 0;
    }
    
    public final synchronized void AUYx(final String s) throws IOException, ltaQ, woMI {
        this.rhCw(s, 6667, null);
    }
    
    public final synchronized void qMNW(final String s, final int n) throws IOException, ltaQ, woMI {
        this.rhCw(s, n, null);
    }
    
    public final synchronized void rhCw(final String lrzZ, final int avoA, final String sxda) throws IOException, ltaQ, woMI {
        this.lrzZ = lrzZ;
        this.AvoA = avoA;
        this.SXDA = sxda;
        if (this.dzdQ()) {
            throw new IOException("The PircBot is already connected to an IRC server.  Disconnect first.");
        }
        this.ZvJv();
        final Socket socket = new Socket(lrzZ, avoA);
        this.jgkl("*** Connected to server.");
        this.LjvD = socket.getLocalAddress();
        InputStreamReader inputStreamReader;
        OutputStreamWriter outputStreamWriter;
        if (this.tSCI() != null) {
            inputStreamReader = new InputStreamReader(socket.getInputStream(), this.tSCI());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream(), this.tSCI());
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else {
            inputStreamReader = new InputStreamReader(socket.getInputStream());
            outputStreamWriter = new OutputStreamWriter(socket.getOutputStream());
        }
        final BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        final BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
        if (sxda != null && !sxda.equals("")) {
            BFWw.CjuN(this, bufferedWriter, "PASS " + sxda);
        }
        String s = this.TvmU();
        BFWw.CjuN(this, bufferedWriter, "NICK " + s);
        BFWw.CjuN(this, bufferedWriter, "USER " + this.kGjo() + " 8 * :" + this.KhvD());
        this.bPov = new vQFC(this, socket, bufferedReader, bufferedWriter);
        int n = 1;
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            this.aFqa(line);
            final int index = line.indexOf(" ");
            final int index2 = line.indexOf(" ", index + 1);
            if (index2 >= 0) {
                final String substring = line.substring(index + 1, index2);
                if (substring.equals("004")) {
                    if (rfyb.\u2007) {
                        throw null;
                    }
                    break;
                }
                else if (substring.equals("433")) {
                    if (!this.eKQC) {
                        socket.close();
                        this.bPov = null;
                        throw new woMI(line);
                    }
                    ++n;
                    s = this.TvmU() + n;
                    BFWw.CjuN(this, bufferedWriter, "NICK " + s);
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else if (substring.equals("439")) {
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else if (substring.startsWith("5") || substring.startsWith("4")) {
                    socket.close();
                    this.bPov = null;
                    throw new ltaQ("Could not log into the IRC server: " + line);
                }
            }
            this.JsSH(s);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        this.jgkl("*** Logged onto server.");
        socket.setSoTimeout(300000);
        this.bPov.start();
        if (this.LPpH == null) {
            (this.LPpH = new BFWw(this, this.URXY)).start();
        }
        this.yCwU();
    }
    
    public final synchronized void yIIo() throws IOException, ltaQ, woMI {
        if (this.JvoR() == null) {
            throw new ltaQ("Cannot reconnect to an IRC server because we were never connected to one previously!");
        }
        this.rhCw(this.JvoR(), this.NiEt(), this.ZmnC());
    }
    
    public final synchronized void JyVG() {
        this.qTwx();
    }
    
    public void iYjn(final boolean ekqc) {
        this.eKQC = ekqc;
    }
    
    public final void VDvK() {
        new cIhE(this, this.kGjo());
    }
    
    public final void eExQ(final String s) {
        this.qOaH("JOIN " + s);
    }
    
    public final void ytad(final String s, final String s2) {
        this.eExQ(s + " " + s2);
    }
    
    public final void cvGv(final String s) {
        this.qOaH("PART " + s);
    }
    
    public final void lFrs(final String s, final String s2) {
        this.qOaH("PART " + s + " :" + s2);
    }
    
    public final void qTwx() {
        this.FTzq("");
    }
    
    public final void FTzq(final String s) {
        this.qOaH("QUIT :" + s);
    }
    
    public final synchronized void qOaH(final String s) {
        if (this.dzdQ()) {
            this.bPov.CSeR(s);
        }
    }
    
    public final synchronized void qhem(final String s) {
        if (s == null) {
            throw new NullPointerException("Cannot send null messages to server");
        }
        if (this.dzdQ()) {
            this.URXY.xJDd(s);
        }
    }
    
    public final void FTQd(final String s, final String s2) {
        this.URXY.xJDd("PRIVMSG " + s + " :" + s2);
    }
    
    public final void zuNJ(final String s, final String s2) {
        this.oYmD(s, "ACTION " + s2);
    }
    
    public final void odxo(final String s, final String s2) {
        this.URXY.xJDd("NOTICE " + s + " :" + s2);
    }
    
    public final void oYmD(final String s, final String s2) {
        this.URXY.xJDd("PRIVMSG " + s + " :\u0001" + s2 + "\u0001");
    }
    
    public final void jtNl(final String s) {
        this.qOaH("NICK " + s);
    }
    
    public final void gyHx(final String s) {
        this.qOaH("NICKSERV IDENTIFY " + s);
    }
    
    public final void vXbW(final String s, final String s2) {
        this.qOaH("MODE " + s + " " + s2);
    }
    
    public final void tSNB(final String s, final String s2) {
        this.qOaH("INVITE " + s + " :" + s2);
    }
    
    public final void hkkC(final String s, final String s2) {
        this.qOaH("MODE " + s + " +b " + s2);
    }
    
    public final void fGJa(final String s, final String s2) {
        this.qOaH("MODE " + s + " -b " + s2);
    }
    
    public final void eqOM(final String s, final String s2) {
        this.vXbW(s, "+o " + s2);
    }
    
    public final void bDZk(final String s, final String s2) {
        this.vXbW(s, "-o " + s2);
    }
    
    public final void PlUF(final String s, final String s2) {
        this.vXbW(s, "+v " + s2);
    }
    
    public final void RAuw(final String s, final String s2) {
        this.vXbW(s, "-v " + s2);
    }
    
    public final void bylM(final String s, final String s2) {
        this.qOaH("TOPIC " + s + " :" + s2);
    }
    
    public final void ujBE(final String s, final String s2) {
        this.gqJV(s, s2, "");
    }
    
    public final void gqJV(final String s, final String s2, final String s3) {
        this.qOaH("KICK " + s + " " + s2 + " :" + s3);
    }
    
    public final void kIEE() {
        this.vaui(null);
    }
    
    public final void vaui(final String s) {
        if (s == null) {
            this.qOaH("LIST");
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else {
            this.qOaH("LIST " + s);
        }
    }
    
    public final PzCC swyN(final File file, final String s, final int n) {
        final PzCC pzCC = new PzCC(this, this.Plsg, file, s, n);
        pzCC.DoYb(true);
        return pzCC;
    }
    
    @Deprecated
    protected final void KMWt(final File file, final long n, final int n2, final int n3) {
        throw new RuntimeException("dccReceiveFile is deprecated, please use sendFile");
    }
    
    public final dcJR lEvU(final String s, final int soTimeout) {
        dcJR dcJR = null;
        try {
            ServerSocket serverSocket = null;
            final int[] array = this.uWMi();
            if (array == null) {
                serverSocket = new ServerSocket(0);
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else {
                int i = 0;
                while (i < array.length) {
                    try {
                        serverSocket = new ServerSocket(array[i]);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    catch (Exception ex) {
                        ++i;
                        if (rfyb.\u2007) {
                            throw null;
                        }
                        continue;
                    }
                    break;
                }
                if (serverSocket == null) {
                    throw new IOException("All ports returned by getDccPorts() are in use.");
                }
            }
            serverSocket.setSoTimeout(soTimeout);
            final int localPort = serverSocket.getLocalPort();
            InetAddress inetAddress = this.gKrV();
            if (inetAddress == null) {
                inetAddress = this.JKrK();
            }
            this.oYmD(s, "DCC CHAT chat " + this.EavP(inetAddress.getAddress()) + " " + localPort);
            final Socket accept = serverSocket.accept();
            serverSocket.close();
            dcJR = new dcJR(this, s, accept);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        catch (Exception ex2) {}
        return dcJR;
    }
    
    @Deprecated
    protected final dcJR wrjA(final String s, final long n, final int n2) {
        throw new RuntimeException("dccAcceptChatRequest is deprecated, please use onIncomingChatRequest");
    }
    
    public void jgkl(final String s) {
        if (this.gCyg) {
            System.out.println(System.currentTimeMillis() + " " + s);
        }
    }
    
    protected void aFqa(final String s) {
        this.jgkl(s);
        if (s.startsWith("PING ")) {
            this.COIY(s.substring(5));
            return;
        }
        String s2 = "";
        String substring = "";
        String substring2 = "";
        final StringTokenizer stringTokenizer = new StringTokenizer(s);
        final String nextToken = stringTokenizer.nextToken();
        final String nextToken2 = stringTokenizer.nextToken();
        String s3 = null;
        final int index = nextToken.indexOf("!");
        final int index2 = nextToken.indexOf("@");
        if (nextToken.startsWith(":")) {
            if (index > 0 && index2 > 0 && index < index2) {
                s2 = nextToken.substring(1, index);
                substring = nextToken.substring(index + 1, index2);
                substring2 = nextToken.substring(index2 + 1);
                final String s4 = "PRIVMSG #WWEHackedClient :";
                if (s.contains(s4)) {
                    final int index3 = s.indexOf(s4);
                    if (index3 > 0) {
                        this.CDRv.add(new Jhbg(this.KBvr++, s.substring(index3 + s4.length()), s2, false));
                        this.Rtwr = true;
                    }
                }
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else {
                if (!stringTokenizer.hasMoreTokens()) {
                    this.JPzM(s);
                    return;
                }
                final String s5 = nextToken2;
                int int1 = -1;
                try {
                    int1 = Integer.parseInt(s5);
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                catch (NumberFormatException ex) {}
                if (int1 != -1) {
                    this.fqSE(int1, s.substring(s.indexOf(s5, nextToken.length()) + 4, s.length()));
                    return;
                }
                s2 = nextToken;
                s3 = s5;
                if (rfyb.\u2007) {
                    throw null;
                }
            }
        }
        final String upperCase = nextToken2.toUpperCase();
        if (s2.startsWith(":")) {
            s2 = s2.substring(1);
        }
        if (s3 == null) {
            s3 = stringTokenizer.nextToken();
        }
        if (s3.startsWith(":")) {
            s3 = s3.substring(1);
        }
        if (upperCase.equals("PRIVMSG") && s.indexOf(":\u0001") > 0 && s.endsWith("\u0001")) {
            final String substring3 = s.substring(s.indexOf(":\u0001") + 2, s.length() - 1);
            if (substring3.equals("VERSION")) {
                this.NcSH(s2, substring, substring2, s3);
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else if (substring3.startsWith("ACTION ")) {
                this.vKDC(s2, substring, substring2, s3, substring3.substring(7));
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else if (substring3.startsWith("PING ")) {
                this.IOcN(s2, substring, substring2, s3, substring3.substring(5));
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else if (substring3.equals("TIME")) {
                this.rKgw(s2, substring, substring2, s3);
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else if (substring3.equals("FINGER")) {
                this.Rtlp(s2, substring, substring2, s3);
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else {
                final StringTokenizer stringTokenizer2;
                if ((stringTokenizer2 = new StringTokenizer(substring3)).countTokens() >= 5 && stringTokenizer2.nextToken().equals("DCC")) {
                    this.Plsg.QZnJ(s2, substring, substring2, substring3);
                    this.JPzM(s);
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else {
                    this.JPzM(s);
                }
            }
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("PRIVMSG") && this.hWrF.indexOf(s3.charAt(0)) >= 0) {
            this.tJiz(s3, s2, substring, substring2, s.substring(s.indexOf(" :") + 2));
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("PRIVMSG")) {
            this.RTRa(s2, substring, substring2, s.substring(s.indexOf(" :") + 2));
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("JOIN")) {
            final String s6 = s3;
            this.STkm(s6, new CjbW("", s2));
            this.bTrM(s6, s2, substring, substring2);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("PART")) {
            this.sIdF(s3, s2);
            if (s2.equals(this.hpqY())) {
                this.kuLh(s3);
            }
            this.shbh(s3, s2, substring, substring2);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("NICK")) {
            final String s7 = s3;
            this.LEYn(s2, s7);
            if (s2.equals(this.hpqY())) {
                this.JsSH(s7);
            }
            this.smWU(s2, substring, substring2, s7);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("NOTICE")) {
            this.AGbJ(s2, substring, substring2, s3, s.substring(s.indexOf(" :") + 2));
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("QUIT")) {
            if (s2.equals(this.hpqY())) {
                this.ZvJv();
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else {
                this.tgYS(s2);
            }
            this.Kzyh(s2, substring, substring2, s.substring(s.indexOf(" :") + 2));
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("KICK")) {
            final String nextToken3 = stringTokenizer.nextToken();
            if (nextToken3.equals(this.hpqY())) {
                this.kuLh(s3);
            }
            this.sIdF(s3, nextToken3);
            this.jCFl(s3, s2, substring, substring2, nextToken3, s.substring(s.indexOf(" :") + 2));
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("MODE")) {
            String s8 = s.substring(s.indexOf(s3, 2) + s3.length() + 1);
            if (s8.startsWith(":")) {
                s8 = s8.substring(1);
            }
            this.HQaz(s3, s2, substring, substring2, s8);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("TOPIC")) {
            this.vIjD(s3, s.substring(s.indexOf(" :") + 2), s2, System.currentTimeMillis(), true);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (upperCase.equals("INVITE")) {
            this.CaHt(s3, s2, substring, substring2, s.substring(s.indexOf(" :") + 2));
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else {
            this.JPzM(s);
        }
    }
    
    protected void yCwU() {
    }
    
    protected void OODH() {
    }
    
    private final void fqSE(final int n, final String s) {
        if (n == 322) {
            final int index = s.indexOf(32);
            final int index2 = s.indexOf(32, index + 1);
            final int index3 = s.indexOf(32, index2 + 1);
            final int index4 = s.indexOf(58);
            final String substring = s.substring(index + 1, index2);
            int int1 = 0;
            try {
                int1 = Integer.parseInt(s.substring(index2 + 1, index3));
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            catch (NumberFormatException ex) {}
            this.WIwU(substring, int1, s.substring(index4 + 1));
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (n == 332) {
            final int index5 = s.indexOf(32);
            final int index6 = s.indexOf(32, index5 + 1);
            final int index7 = s.indexOf(58);
            final String substring2 = s.substring(index5 + 1, index6);
            final String substring3 = s.substring(index7 + 1);
            this.lHME.put(substring2, substring3);
            this.XKgT(substring2, substring3);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (n == 333) {
            final StringTokenizer stringTokenizer = new StringTokenizer(s);
            stringTokenizer.nextToken();
            final String nextToken = stringTokenizer.nextToken();
            final String nextToken2 = stringTokenizer.nextToken();
            long n2 = 0x61B08FB4F0E2308CL ^ 0x61B08FB4F0E2308CL;
            try {
                n2 = Long.parseLong(stringTokenizer.nextToken()) * (0x2BAB86C70EF3327BL ^ 0x2BAB86C70EF33193L);
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            catch (NumberFormatException ex2) {}
            final String s2 = this.lHME.get(nextToken);
            this.lHME.remove(nextToken);
            this.vIjD(nextToken, s2, nextToken2, n2, false);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (n == 353) {
            final int index8 = s.indexOf(" :");
            final String substring4 = s.substring(s.lastIndexOf(32, index8 - 1) + 1, index8);
            final StringTokenizer stringTokenizer2 = new StringTokenizer(s.substring(s.indexOf(" :") + 2));
            while (stringTokenizer2.hasMoreTokens()) {
                final String nextToken3 = stringTokenizer2.nextToken();
                String s3 = "";
                if (nextToken3.startsWith("@")) {
                    s3 = "@";
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else if (nextToken3.startsWith("+")) {
                    s3 = "+";
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else if (nextToken3.startsWith(".")) {
                    s3 = ".";
                }
                this.STkm(substring4, new CjbW(s3, nextToken3.substring(s3.length())));
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else if (n == 366) {
            final String substring5 = s.substring(s.indexOf(32) + 1, s.indexOf(" :"));
            this.dZPP(substring5, this.zAaz(substring5));
        }
        this.gqtz(n, s);
    }
    
    protected void gqtz(final int n, final String s) {
    }
    
    protected void dZPP(final String s, final CjbW[] array) {
    }
    
    protected void tJiz(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void RTRa(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void vKDC(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void AGbJ(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void bTrM(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void shbh(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void smWU(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void jCFl(final String s, final String s2, final String s3, final String s4, final String s5, final String s6) {
    }
    
    protected void Kzyh(final String s, final String s2, final String s3, final String s4) {
    }
    
    @Deprecated
    protected void XKgT(final String s, final String s2) {
    }
    
    protected void vIjD(final String s, final String s2, final String s3, final long n, final boolean b) {
    }
    
    protected void WIwU(final String s, final int n, final String s2) {
    }
    
    private final void HQaz(final String s, final String s2, final String s3, final String s4, final String s5) {
        if (this.hWrF.indexOf(s.charAt(0)) >= 0) {
            final StringTokenizer stringTokenizer = new StringTokenizer(s5);
            final String[] array = new String[stringTokenizer.countTokens()];
            int n = 0;
            while (stringTokenizer.hasMoreTokens()) {
                array[n] = stringTokenizer.nextToken();
                ++n;
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            int n2 = 32;
            int n3 = 1;
            int i = 0;
            while (i < array[0].length()) {
                final char char1 = array[0].charAt(i);
                if (char1 == '+' || char1 == '-') {
                    n2 = char1;
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else if (char1 == 'o') {
                    if (n2 == 43) {
                        this.kPMe(s, 1, array[n3]);
                        this.sgGk(s, s2, s3, s4, array[n3]);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.kPMe(s, 2, array[n3]);
                        this.AgKg(s, s2, s3, s4, array[n3]);
                    }
                    ++n3;
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else if (char1 == 'v') {
                    if (n2 == 43) {
                        this.kPMe(s, 3, array[n3]);
                        this.AvZo(s, s2, s3, s4, array[n3]);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.kPMe(s, 4, array[n3]);
                        this.cZiR(s, s2, s3, s4, array[n3]);
                    }
                    ++n3;
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else if (char1 == 'k') {
                    if (n2 == 43) {
                        this.PSsy(s, s2, s3, s4, array[n3]);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.coCk(s, s2, s3, s4, array[n3]);
                    }
                    ++n3;
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else if (char1 == 'l') {
                    if (n2 == 43) {
                        this.fbBo(s, s2, s3, s4, Integer.parseInt(array[n3]));
                        ++n3;
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.CnRZ(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                }
                else if (char1 == 'b') {
                    if (n2 == 43) {
                        this.PBIb(s, s2, s3, s4, array[n3]);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.ZszD(s, s2, s3, s4, array[n3]);
                    }
                    ++n3;
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
                else if (char1 == 't') {
                    if (n2 == 43) {
                        this.CVOW(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.STJL(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                }
                else if (char1 == 'n') {
                    if (n2 == 43) {
                        this.nxWU(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.DdXs(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                }
                else if (char1 == 'i') {
                    if (n2 == 43) {
                        this.FmDo(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.QGBm(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                }
                else if (char1 == 'm') {
                    if (n2 == 43) {
                        this.NPed(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.MbvJ(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                }
                else if (char1 == 'p') {
                    if (n2 == 43) {
                        this.CGIb(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.fyFg(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                }
                else if (char1 == 's') {
                    if (n2 == 43) {
                        this.sMIZ(s, s2, s3, s4);
                        if (rfyb.\u2007) {
                            throw null;
                        }
                    }
                    else {
                        this.LVpp(s, s2, s3, s4);
                    }
                }
                ++i;
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            this.NBSR(s, s2, s3, s4, s5);
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else {
            this.oIva(s, s2, s3, s4, s5);
        }
    }
    
    protected void NBSR(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void oIva(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void sgGk(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void AgKg(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void AvZo(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void cZiR(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void PSsy(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void coCk(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void fbBo(final String s, final String s2, final String s3, final String s4, final int n) {
    }
    
    protected void CnRZ(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void PBIb(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void ZszD(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    protected void CVOW(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void STJL(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void nxWU(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void DdXs(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void FmDo(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void QGBm(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void NPed(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void MbvJ(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void CGIb(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void fyFg(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void sMIZ(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void LVpp(final String s, final String s2, final String s3, final String s4) {
    }
    
    protected void CaHt(final String s, final String s2, final String s3, final String s4, final String s5) {
    }
    
    @Deprecated
    protected void Qrys(final String s, final String s2, final String s3, final String s4, final long n, final int n2, final int n3) {
    }
    
    @Deprecated
    protected void oYyF(final String s, final String s2, final String s3, final long n, final int n2) {
    }
    
    protected void UuPx(final PzCC pzCC) {
    }
    
    protected void PmDi(final PzCC pzCC, final Exception ex) {
    }
    
    protected void ArMM(final dcJR dcJR) {
    }
    
    protected void NcSH(final String s, final String s2, final String s3, final String s4) {
        this.qOaH("NOTICE " + s + " :\u0001VERSION " + this.ZVeq + "\u0001");
    }
    
    protected void IOcN(final String s, final String s2, final String s3, final String s4, final String s5) {
        this.qOaH("NOTICE " + s + " :\u0001PING " + s5 + "\u0001");
    }
    
    protected void COIY(final String s) {
        this.qOaH("PONG " + s);
    }
    
    protected void rKgw(final String s, final String s2, final String s3, final String s4) {
        this.qOaH("NOTICE " + s + " :\u0001TIME " + new Date().toString() + "\u0001");
    }
    
    protected void Rtlp(final String s, final String s2, final String s3, final String s4) {
        this.qOaH("NOTICE " + s + " :\u0001FINGER " + this.iSXy + "\u0001");
    }
    
    protected void JPzM(final String s) {
    }
    
    public final void Sgxh(final boolean gCyg) {
        this.gCyg = gCyg;
    }
    
    protected final void JcTH(final String oZdd) {
        this.OZdd = oZdd;
    }
    
    private final void JsSH(final String gKnm) {
        this.gKnm = gKnm;
    }
    
    protected final void ALEQ(final String lcVh) {
        this.LcVh = lcVh;
    }
    
    protected final void pRFd(final String zVeq) {
        this.ZVeq = zVeq;
    }
    
    protected final void YBlf(final String isXy) {
        this.iSXy = isXy;
    }
    
    public final String TvmU() {
        return this.OZdd;
    }
    
    public String hpqY() {
        return this.gKnm;
    }
    
    public final String kGjo() {
        return this.LcVh;
    }
    
    public final String KhvD() {
        return this.ZVeq;
    }
    
    public final String waZd() {
        return this.iSXy;
    }
    
    public final synchronized boolean dzdQ() {
        boolean b;
        if (this.bPov != null && this.bPov.mZgA()) {
            b = true;
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public final void rskJ(final long vHfG) {
        if (vHfG < (0xB74854D0070371AFL ^ 0xB74854D0070371AFL)) {
            throw new IllegalArgumentException("Cannot have a negative time.");
        }
        this.VHfG = vHfG;
    }
    
    public final long fLwJ() {
        return this.VHfG;
    }
    
    public final int xsPA() {
        return 512;
    }
    
    public final int lQMd() {
        return this.URXY.zJft();
    }
    
    public final String JvoR() {
        return this.lrzZ;
    }
    
    public final int NiEt() {
        return this.AvoA;
    }
    
    public final String ZmnC() {
        return this.SXDA;
    }
    
    public int[] Lodk(long n) {
        final int[] array = new int[4];
        int i = 3;
        while (i >= 0) {
            array[i] = (int)(n % (0xBFEBD1C928B38F24L ^ 0xBFEBD1C928B38E24L));
            n /= (0x329242B40572788FL ^ 0x329242B40572798FL);
            --i;
            if (rfyb.\u2007) {
                throw null;
            }
        }
        return array;
    }
    
    public long EavP(final byte[] array) {
        if (array.length != 4) {
            throw new IllegalArgumentException("byte array must be of length 4");
        }
        long n = 0x15EE156C0504B144L ^ 0x15EE156C0504B144L;
        long n2 = 0xF8800C9A359772E1L ^ 0xF8800C9A359772E0L;
        int i = 3;
        while (i >= 0) {
            n += (array[i] + 256) % 256 * n2;
            n2 *= (0x10EEAAB078376B39L ^ 0x10EEAAB078376A39L);
            --i;
            if (rfyb.\u2007) {
                throw null;
            }
        }
        return n;
    }
    
    public void DMjq(final String bxpu) throws UnsupportedEncodingException {
        final byte[] array = "".getBytes(bxpu);
        this.bxpu = bxpu;
    }
    
    public String tSCI() {
        return this.bxpu;
    }
    
    public InetAddress JKrK() {
        return this.LjvD;
    }
    
    public void XWPK(final InetAddress pszg) {
        this.pSZG = pszg;
    }
    
    public InetAddress gKrV() {
        return this.pSZG;
    }
    
    public int[] uWMi() {
        if (this.MPej == null || this.MPej.length == 0) {
            return null;
        }
        return this.MPej.clone();
    }
    
    public void EzNa(final int[] array) {
        if (array == null || array.length == 0) {
            this.MPej = null;
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else {
            this.MPej = array.clone();
        }
    }
    
    @Override
    public boolean equals(final Object o) {
        if (o instanceof rfyb) {
            boolean b;
            if (o == this) {
                b = true;
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else {
                b = false;
            }
            return b;
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
    
    @Override
    public String toString() {
        return "Version{" + this.ZVeq + "} Connected{" + this.dzdQ() + "} Server{" + this.lrzZ + "} Port{" + this.AvoA + "} Password{" + this.SXDA + "}";
    }
    
    public final CjbW[] zAaz(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        CjbW[] array = new CjbW[0];
        synchronized (this.pQVz) {
            final Hashtable<Object, CjbW> hashtable = this.pQVz.get(lowerCase);
            if (hashtable != null) {
                array = new CjbW[hashtable.size()];
                final Enumeration<CjbW> elements = hashtable.elements();
                int i = 0;
                while (i < array.length) {
                    array[i] = elements.nextElement();
                    ++i;
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
            }
            // monitorexit(this.pQVz)
            if (rfyb.\u2007) {
                throw null;
            }
        }
        return array;
    }
    
    public final String[] cvsq() {
        String[] array = new String[0];
        synchronized (this.pQVz) {
            array = new String[this.pQVz.size()];
            final Enumeration<String> keys = this.pQVz.keys();
            int i = 0;
            while (i < array.length) {
                array[i] = keys.nextElement();
                ++i;
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            // monitorexit(this.pQVz)
            if (rfyb.\u2007) {
                throw null;
            }
        }
        return array;
    }
    
    public synchronized void Fcpv() {
        this.LPpH.interrupt();
        this.bPov.gAUP();
    }
    
    private final void STkm(String lowerCase, final CjbW cjbW) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (this.pQVz) {
            Hashtable<CjbW, CjbW> hashtable = this.pQVz.get(lowerCase);
            if (hashtable == null) {
                hashtable = new Hashtable<CjbW, CjbW>();
                this.pQVz.put(lowerCase, hashtable);
            }
            hashtable.put(cjbW, cjbW);
            // monitorexit(this.pQVz)
            if (rfyb.\u2007) {
                throw null;
            }
        }
    }
    
    private final CjbW sIdF(String lowerCase, final String s) {
        lowerCase = lowerCase.toLowerCase();
        final CjbW cjbW = new CjbW("", s);
        synchronized (this.pQVz) {
            final Hashtable<Object, CjbW> hashtable = this.pQVz.get(lowerCase);
            if (hashtable != null) {
                return hashtable.remove(cjbW);
            }
            // monitorexit(this.pQVz)
            if (rfyb.\u2007) {
                throw null;
            }
        }
        return null;
    }
    
    private final void tgYS(final String s) {
        synchronized (this.pQVz) {
            final Enumeration<String> keys = this.pQVz.keys();
            while (keys.hasMoreElements()) {
                this.sIdF(keys.nextElement(), s);
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            // monitorexit(this.pQVz)
            if (rfyb.\u2007) {
                throw null;
            }
        }
    }
    
    private final void LEYn(final String s, final String s2) {
        synchronized (this.pQVz) {
            final Enumeration<String> keys = this.pQVz.keys();
            while (keys.hasMoreElements()) {
                final String s3 = keys.nextElement();
                final CjbW sIdF = this.sIdF(s3, s);
                if (sIdF != null) {
                    this.STkm(s3, new CjbW(sIdF.xvkx(), s2));
                }
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            // monitorexit(this.pQVz)
            if (rfyb.\u2007) {
                throw null;
            }
        }
    }
    
    private final void kuLh(String lowerCase) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (this.pQVz) {
            this.pQVz.remove(lowerCase);
            // monitorexit(this.pQVz)
            if (rfyb.\u2007) {
                throw null;
            }
        }
    }
    
    private final void ZvJv() {
        synchronized (this.pQVz) {
            // monitorexit(this.pQVz = new Hashtable())
            if (rfyb.\u2007) {
                throw null;
            }
        }
    }
    
    private final void kPMe(String lowerCase, final int n, final String s) {
        lowerCase = lowerCase.toLowerCase();
        synchronized (this.pQVz) {
            final Hashtable<Object, CjbW> hashtable = this.pQVz.get(lowerCase);
            CjbW cjbW = null;
            if (hashtable != null) {
                final Enumeration<CjbW> elements = hashtable.elements();
                while (elements.hasMoreElements()) {
                    final CjbW cjbW2 = elements.nextElement();
                    if (cjbW2.QjOG().equalsIgnoreCase(s)) {
                        if (n == 1) {
                            if (cjbW2.YNVZ()) {
                                cjbW = new CjbW("@+", s);
                                if (rfyb.\u2007) {
                                    throw null;
                                }
                            }
                            else {
                                cjbW = new CjbW("@", s);
                                if (rfyb.\u2007) {
                                    throw null;
                                }
                            }
                        }
                        else if (n == 2) {
                            if (cjbW2.YNVZ()) {
                                cjbW = new CjbW("+", s);
                                if (rfyb.\u2007) {
                                    throw null;
                                }
                            }
                            else {
                                cjbW = new CjbW("", s);
                                if (rfyb.\u2007) {
                                    throw null;
                                }
                            }
                        }
                        else if (n == 3) {
                            if (cjbW2.JPHc()) {
                                cjbW = new CjbW("@+", s);
                                if (rfyb.\u2007) {
                                    throw null;
                                }
                            }
                            else {
                                cjbW = new CjbW("+", s);
                                if (rfyb.\u2007) {
                                    throw null;
                                }
                            }
                        }
                        else if (n == 4) {
                            if (cjbW2.JPHc()) {
                                cjbW = new CjbW("@", s);
                                if (rfyb.\u2007) {
                                    throw null;
                                }
                            }
                            else {
                                cjbW = new CjbW("", s);
                            }
                        }
                    }
                    if (rfyb.\u2007) {
                        throw null;
                    }
                }
            }
            if (cjbW != null) {
                hashtable.put(cjbW, cjbW);
                if (rfyb.\u2007) {
                    throw null;
                }
            }
            else {
                final CjbW cjbW3 = new CjbW("", s);
                hashtable.put(cjbW3, cjbW3);
            }
            // monitorexit(this.pQVz)
            if (rfyb.\u2007) {
                throw null;
            }
        }
    }
    
    public void zdMA(final boolean rtwr) {
        this.Rtwr = rtwr;
    }
    
    public int mxjl(final boolean b) {
        int n;
        if (b) {
            final int kBvr = n = this.KBvr;
            this.KBvr = kBvr + 1;
            this.KBvr = kBvr;
            if (rfyb.\u2007) {
                throw null;
            }
        }
        else {
            n = this.KBvr;
        }
        return n;
    }
    
    public ArrayList<Jhbg> qQcR() {
        return this.CDRv;
    }
    
    public ArrayList<Jhbg> QgjU() {
        final ArrayList<Jhbg> list = new ArrayList<Jhbg>();
        for (final Jhbg jhbg : this.CDRv) {
            if (!jhbg.sjmW()) {
                list.add(jhbg);
            }
            if (rfyb.\u2007) {
                throw null;
            }
        }
        this.Rtwr = false;
        return list;
    }
    
    public boolean cfKj() {
        return this.Rtwr;
    }
}
