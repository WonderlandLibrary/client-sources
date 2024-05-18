package wwe;

import java.io.*;
import java.net.*;

public class PzCC
{
    public static final int Cimc = 1024;
    private rfyb jzFj;
    private iroq pqmX;
    private String NRtx;
    private String nnur;
    private String SDuq;
    private String Nwqz;
    private long ZsMp;
    private int UxXV;
    private long GxGM;
    private boolean cpbu;
    private Socket OBKu;
    private long jneM;
    private File cKmJ;
    private int YuhY;
    private boolean xpAo;
    private long IAMl;
    private long iiSi;
    
    PzCC(final rfyb jzFj, final iroq pqmX, final String nRtx, final String nnur, final String sDuq, final String nwqz, final String s, final long zsMp, final int uxXV, final long gxGM) {
        super();
        this.nnur = null;
        this.SDuq = null;
        this.OBKu = null;
        this.jneM = (0xF88B02587848D0F3L ^ 0xF88B02587848D0F3L);
        this.cKmJ = null;
        this.YuhY = 0;
        this.IAMl = (0xB602EEC395BFB569L ^ 0xB602EEC395BFB569L);
        this.iiSi = (0xCD07A3EDA5AA0C31L ^ 0xCD07A3EDA5AA0C31L);
        this.jzFj = jzFj;
        this.pqmX = pqmX;
        this.NRtx = nRtx;
        this.nnur = nnur;
        this.SDuq = sDuq;
        this.Nwqz = nwqz;
        this.cKmJ = new File(s);
        this.ZsMp = zsMp;
        this.UxXV = uxXV;
        this.GxGM = gxGM;
        this.cpbu = false;
        this.xpAo = true;
    }
    
    PzCC(final rfyb jzFj, final iroq pqmX, final File cKmJ, final String nRtx, final int yuhY) {
        super();
        this.nnur = null;
        this.SDuq = null;
        this.OBKu = null;
        this.jneM = (0xFBA902EB5A65639FL ^ 0xFBA902EB5A65639FL);
        this.cKmJ = null;
        this.YuhY = 0;
        this.IAMl = (0x72A021F285BD4E01L ^ 0x72A021F285BD4E01L);
        this.iiSi = (0x937714B2152E1D34L ^ 0x937714B2152E1D34L);
        this.jzFj = jzFj;
        this.pqmX = pqmX;
        this.NRtx = nRtx;
        this.cKmJ = cKmJ;
        this.GxGM = cKmJ.length();
        this.YuhY = yuhY;
        this.cpbu = true;
        this.xpAo = false;
    }
    
    public synchronized void EDaU(final File cKmJ, final boolean b) {
        if (!this.cpbu) {
            this.cpbu = true;
            this.cKmJ = cKmJ;
            if (this.Nwqz.equals("SEND") && b) {
                this.jneM = cKmJ.length();
                if (this.jneM == (0x84AA0E3BAEF1CA15L ^ 0x84AA0E3BAEF1CA15L)) {
                    this.JqMb(cKmJ, false);
                    if (PzCC.\u2007\u2000\u200e\u200f) {
                        throw null;
                    }
                }
                else {
                    this.jzFj.oYmD(this.NRtx, "DCC RESUME file.ext " + this.UxXV + " " + this.jneM);
                    this.pqmX.sWUQ(this);
                    if (PzCC.\u2007\u2000\u200e\u200f) {
                        throw null;
                    }
                }
            }
            else {
                this.jneM = cKmJ.length();
                this.JqMb(cKmJ, b);
            }
        }
    }
    
    void JqMb(final File file, final boolean b) {
        class YlQN extends Thread
        {
            final File IMXx;
            final boolean EaJp;
            final PzCC JRJc;
            
            YlQN(final PzCC jrJc, final File imXx, final boolean eaJp) {
                this.JRJc = jrJc;
                this.IMXx = imXx;
                this.EaJp = eaJp;
                super();
            }
            
            @Override
            public void run() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     3: return         
                //     4: aconst_null    
                //     5: astore_1       
                //     6: aconst_null    
                //     7: astore_2       
                //     8: aload_0        
                //     9: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //    12: invokestatic    wwe/PzCC.XXkX:(Lwwe/PzCC;)Lwwe/rfyb;
                //    15: aload_0        
                //    16: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //    19: invokestatic    wwe/PzCC.rDJM:(Lwwe/PzCC;)J
                //    22: invokevirtual   wwe/rfyb.Lodk:(J)[I
                //    25: checkcast       [I
                //    28: astore_3       
                //    29: new             Ljava/lang/StringBuilder;
                //    32: dup            
                //    33: invokespecial   java/lang/StringBuilder.<init>:()V
                //    36: aload_3        
                //    37: ldc             0
                //    39: iaload         
                //    40: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //    43: ldc             "."
                //    45: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //    48: aload_3        
                //    49: ldc             1
                //    51: iaload         
                //    52: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //    55: ldc             "."
                //    57: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //    60: aload_3        
                //    61: ldc             2
                //    63: iaload         
                //    64: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //    67: ldc             "."
                //    69: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //    72: aload_3        
                //    73: ldc             3
                //    75: iaload         
                //    76: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //    79: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //    82: astore          4
                //    84: aload_0        
                //    85: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //    88: new             Ljava/net/Socket;
                //    91: dup            
                //    92: aload           4
                //    94: aload_0        
                //    95: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //    98: invokestatic    wwe/PzCC.qwqA:(Lwwe/PzCC;)I
                //   101: invokespecial   java/net/Socket.<init>:(Ljava/lang/String;I)V
                //   104: invokestatic    wwe/PzCC.YrOU:(Lwwe/PzCC;Ljava/net/Socket;)Ljava/net/Socket;
                //   107: pop            
                //   108: aload_0        
                //   109: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   112: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   115: ldc             30000
                //   117: invokevirtual   java/net/Socket.setSoTimeout:(I)V
                //   120: aload_0        
                //   121: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   124: invokestatic    java/lang/System.currentTimeMillis:()J
                //   127: invokestatic    wwe/PzCC.MyqF:(Lwwe/PzCC;J)J
                //   130: pop2           
                //   131: aload_0        
                //   132: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   135: invokestatic    wwe/PzCC.cRIJ:(Lwwe/PzCC;)Lwwe/iroq;
                //   138: aload_0        
                //   139: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   142: invokevirtual   wwe/iroq.JJDC:(Lwwe/PzCC;)V
                //   145: new             Ljava/io/BufferedInputStream;
                //   148: dup            
                //   149: aload_0        
                //   150: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   153: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   156: invokevirtual   java/net/Socket.getInputStream:()Ljava/io/InputStream;
                //   159: invokespecial   java/io/BufferedInputStream.<init>:(Ljava/io/InputStream;)V
                //   162: astore          5
                //   164: new             Ljava/io/BufferedOutputStream;
                //   167: dup            
                //   168: aload_0        
                //   169: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   172: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   175: invokevirtual   java/net/Socket.getOutputStream:()Ljava/io/OutputStream;
                //   178: invokespecial   java/io/BufferedOutputStream.<init>:(Ljava/io/OutputStream;)V
                //   181: astore          6
                //   183: new             Ljava/io/BufferedOutputStream;
                //   186: dup            
                //   187: new             Ljava/io/FileOutputStream;
                //   190: dup            
                //   191: aload_0        
                //   192: getfield        wwe/YlQN.IMXx:Ljava/io/File;
                //   195: invokevirtual   java/io/File.getCanonicalPath:()Ljava/lang/String;
                //   198: aload_0        
                //   199: getfield        wwe/YlQN.EaJp:Z
                //   202: invokespecial   java/io/FileOutputStream.<init>:(Ljava/lang/String;Z)V
                //   205: invokespecial   java/io/BufferedOutputStream.<init>:(Ljava/io/OutputStream;)V
                //   208: astore_1       
                //   209: ldc             1024
                //   211: newarray        B
                //   213: astore          7
                //   215: ldc             4
                //   217: newarray        B
                //   219: astore          8
                //   221: ldc             0
                //   223: istore          9
                //   225: aload           5
                //   227: aload           7
                //   229: ldc             0
                //   231: aload           7
                //   233: arraylength    
                //   234: invokevirtual   java/io/BufferedInputStream.read:([BII)I
                //   237: dup            
                //   238: istore          9
                //   240: ldc             -1
                //   242: if_icmpeq       430
                //   245: aload_1        
                //   246: aload           7
                //   248: ldc             0
                //   250: iload           9
                //   252: invokevirtual   java/io/BufferedOutputStream.write:([BII)V
                //   255: aload_0        
                //   256: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   259: aload_0        
                //   260: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   263: invokestatic    wwe/PzCC.Usxc:(Lwwe/PzCC;)J
                //   266: iload           9
                //   268: i2l            
                //   269: ladd           
                //   270: invokestatic    wwe/PzCC.ioUv:(Lwwe/PzCC;J)J
                //   273: pop2           
                //   274: aload           8
                //   276: ldc             0
                //   278: aload_0        
                //   279: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   282: invokestatic    wwe/PzCC.Usxc:(Lwwe/PzCC;)J
                //   285: ldc             24
                //   287: lshr           
                //   288: ldc2_w          -1218177997522080238
                //   291: ldc2_w          -2665073605485002411
                //   294: dup2_x2        
                //   295: pop2           
                //   296: pop2           
                //   297: ldc2_w          -2665073605485002326
                //   300: lxor           
                //   301: land           
                //   302: l2i            
                //   303: i2b            
                //   304: bastore        
                //   305: aload           8
                //   307: ldc             1
                //   309: aload_0        
                //   310: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   313: invokestatic    wwe/PzCC.Usxc:(Lwwe/PzCC;)J
                //   316: ldc             16
                //   318: lshr           
                //   319: ldc2_w          3764980722838864478
                //   322: ldc2_w          4540499820776736764
                //   325: dup2_x2        
                //   326: pop2           
                //   327: pop2           
                //   328: ldc2_w          4540499820776736515
                //   331: lxor           
                //   332: land           
                //   333: l2i            
                //   334: i2b            
                //   335: bastore        
                //   336: aload           8
                //   338: ldc             2
                //   340: aload_0        
                //   341: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   344: invokestatic    wwe/PzCC.Usxc:(Lwwe/PzCC;)J
                //   347: ldc             8
                //   349: lshr           
                //   350: ldc2_w          -911533620020289414
                //   353: ldc2_w          -5603788404611082111
                //   356: dup2_x2        
                //   357: pop2           
                //   358: pop2           
                //   359: ldc2_w          -5603788404611082114
                //   362: lxor           
                //   363: land           
                //   364: l2i            
                //   365: i2b            
                //   366: bastore        
                //   367: aload           8
                //   369: ldc             3
                //   371: aload_0        
                //   372: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   375: invokestatic    wwe/PzCC.Usxc:(Lwwe/PzCC;)J
                //   378: ldc             0
                //   380: lshr           
                //   381: ldc2_w          8677053018295738047
                //   384: ldc2_w          3625348693931215519
                //   387: dup2_x2        
                //   388: pop2           
                //   389: pop2           
                //   390: ldc2_w          3625348693931215456
                //   393: lxor           
                //   394: land           
                //   395: l2i            
                //   396: i2b            
                //   397: bastore        
                //   398: aload           6
                //   400: aload           8
                //   402: invokevirtual   java/io/BufferedOutputStream.write:([B)V
                //   405: aload           6
                //   407: invokevirtual   java/io/BufferedOutputStream.flush:()V
                //   410: aload_0        
                //   411: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   414: invokestatic    wwe/PzCC.kZLg:(Lwwe/PzCC;)V
                //   417: getstatic       wwe/YlQN.\u200b\u2005\u2003\u200e\u2005:Z
                //   420: ifeq            225
                //   423: aconst_null    
                //   424: athrow         
                //   425: nop            
                //   426: nop            
                //   427: nop            
                //   428: nop            
                //   429: athrow         
                //   430: aload_1        
                //   431: invokevirtual   java/io/BufferedOutputStream.flush:()V
                //   434: aload_1        
                //   435: invokevirtual   java/io/BufferedOutputStream.close:()V
                //   438: aload_0        
                //   439: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   442: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   445: invokevirtual   java/net/Socket.close:()V
                //   448: getstatic       wwe/YlQN.\u200b\u2005\u2003\u200e\u2005:Z
                //   451: ifeq            533
                //   454: aconst_null    
                //   455: athrow         
                //   456: astore_3       
                //   457: getstatic       wwe/YlQN.\u200b\u2005\u2003\u200e\u2005:Z
                //   460: ifeq            533
                //   463: aconst_null    
                //   464: athrow         
                //   465: astore_3       
                //   466: aload_3        
                //   467: astore_2       
                //   468: aload_1        
                //   469: invokevirtual   java/io/BufferedOutputStream.close:()V
                //   472: aload_0        
                //   473: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   476: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   479: invokevirtual   java/net/Socket.close:()V
                //   482: getstatic       wwe/YlQN.\u200b\u2005\u2003\u200e\u2005:Z
                //   485: ifeq            533
                //   488: aconst_null    
                //   489: athrow         
                //   490: astore_3       
                //   491: getstatic       wwe/YlQN.\u200b\u2005\u2003\u200e\u2005:Z
                //   494: ifeq            533
                //   497: aconst_null    
                //   498: athrow         
                //   499: astore          10
                //   501: aload_1        
                //   502: invokevirtual   java/io/BufferedOutputStream.close:()V
                //   505: aload_0        
                //   506: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   509: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   512: invokevirtual   java/net/Socket.close:()V
                //   515: getstatic       wwe/YlQN.\u200b\u2005\u2003\u200e\u2005:Z
                //   518: ifeq            525
                //   521: aconst_null    
                //   522: athrow         
                //   523: astore          11
                //   525: aload           10
                //   527: athrow         
                //   528: nop            
                //   529: nop            
                //   530: nop            
                //   531: nop            
                //   532: athrow         
                //   533: aload_0        
                //   534: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   537: invokestatic    wwe/PzCC.XXkX:(Lwwe/PzCC;)Lwwe/rfyb;
                //   540: aload_0        
                //   541: getfield        wwe/YlQN.JRJc:Lwwe/PzCC;
                //   544: aload_2        
                //   545: invokevirtual   wwe/rfyb.PmDi:(Lwwe/PzCC;Ljava/lang/Exception;)V
                //   548: return         
                //   549: nop            
                //   550: nop            
                //   551: nop            
                //   552: nop            
                //   553: athrow         
                //    StackMapTable: 00 0E FF 00 03 00 0D 07 00 02 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 FF 00 DC 00 0D 07 00 02 07 00 7A 05 07 00 31 07 00 8D 07 00 71 07 00 7A 07 00 8F 07 00 8F 01 00 00 01 00 00 FF 00 C7 00 00 00 01 07 00 13 FF 00 04 00 0D 07 00 02 07 00 7A 05 07 00 31 07 00 8D 07 00 71 07 00 7A 07 00 8F 07 00 8F 01 00 00 01 00 00 59 07 00 21 FF 00 08 00 0D 07 00 02 07 00 7A 05 00 00 00 00 00 00 00 00 00 01 00 01 07 00 21 FF 00 18 00 0D 07 00 02 07 00 7A 07 00 21 07 00 21 00 00 00 00 00 00 00 00 01 00 01 07 00 21 FF 00 08 00 0D 07 00 02 07 00 7A 07 00 21 00 00 00 00 00 00 00 00 00 01 00 01 07 00 13 FF 00 17 00 0D 07 00 02 07 00 7A 07 00 21 00 00 00 00 00 00 00 07 00 13 00 01 00 01 07 00 21 01 FF 00 02 00 00 00 01 07 00 13 FF 00 04 00 0D 07 00 02 07 00 7A 07 00 21 07 00 CB 00 00 00 00 00 00 00 00 01 00 00 FF 00 0F 00 00 00 01 07 00 13
                //    Exceptions:
                //  Try           Handler
                //  Start  End    Start  End    Type                 
                //  -----  -----  -----  -----  ---------------------
                //  434    448    456    465    Ljava/lang/Exception;
                //  8      425    465    499    Ljava/lang/Exception;
                //  430    434    465    499    Ljava/lang/Exception;
                //  468    482    490    499    Ljava/lang/Exception;
                //  8      425    499    528    Any
                //  430    434    499    528    Any
                //  465    468    499    528    Any
                //  501    515    523    525    Ljava/lang/Exception;
                //  499    501    499    528    Any
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: new             Lwwe/YlQN;
        //     7: dup            
        //     8: aload_0        
        //     9: aload_1        
        //    10: iload_2        
        //    11: invokespecial   wwe/YlQN.<init>:(Lwwe/PzCC;Ljava/io/File;Z)V
        //    14: invokevirtual   wwe/YlQN.start:()V
        //    17: return         
        //    18: nop            
        //    19: nop            
        //    20: nop            
        //    21: athrow         
        //    StackMapTable: 00 03 FC 00 03 01 00 FF 00 0D 00 00 00 01 07 00 27
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    void DoYb(final boolean b) {
        class luXL extends Thread
        {
            final boolean BLCI;
            final PzCC FoCW;
            
            luXL(final PzCC foCW, final boolean blci) {
                this.FoCW = foCW;
                this.BLCI = blci;
                super();
            }
            
            @Override
            public void run() {
                // 
                // This method could not be decompiled.
                // 
                // Original Bytecode:
                // 
                //     3: return         
                //     4: aconst_null    
                //     5: astore_1       
                //     6: aconst_null    
                //     7: astore_2       
                //     8: aconst_null    
                //     9: astore_3       
                //    10: aload_0        
                //    11: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //    14: invokestatic    wwe/PzCC.XXkX:(Lwwe/PzCC;)Lwwe/rfyb;
                //    17: invokevirtual   wwe/rfyb.uWMi:()[I
                //    20: checkcast       [I
                //    23: astore          4
                //    25: aload           4
                //    27: ifnonnull       53
                //    30: new             Ljava/net/ServerSocket;
                //    33: dup            
                //    34: ldc             0
                //    36: invokespecial   java/net/ServerSocket.<init>:(I)V
                //    39: astore_3       
                //    40: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //    43: ifeq            123
                //    46: aconst_null    
                //    47: athrow         
                //    48: nop            
                //    49: nop            
                //    50: nop            
                //    51: nop            
                //    52: athrow         
                //    53: ldc             0
                //    55: istore          5
                //    57: iload           5
                //    59: aload           4
                //    61: arraylength    
                //    62: if_icmpge       104
                //    65: new             Ljava/net/ServerSocket;
                //    68: dup            
                //    69: aload           4
                //    71: iload           5
                //    73: iaload         
                //    74: invokespecial   java/net/ServerSocket.<init>:(I)V
                //    77: astore_3       
                //    78: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //    81: ifeq            104
                //    84: aconst_null    
                //    85: athrow         
                //    86: astore          6
                //    88: iinc            5, 1
                //    91: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //    94: ifeq            57
                //    97: aconst_null    
                //    98: athrow         
                //    99: nop            
                //   100: nop            
                //   101: nop            
                //   102: nop            
                //   103: athrow         
                //   104: aload_3        
                //   105: ifnonnull       123
                //   108: new             Ljava/io/IOException;
                //   111: dup            
                //   112: ldc             "All ports returned by getDccPorts() are in use."
                //   114: invokespecial   java/io/IOException.<init>:(Ljava/lang/String;)V
                //   117: athrow         
                //   118: nop            
                //   119: nop            
                //   120: nop            
                //   121: nop            
                //   122: athrow         
                //   123: aload_3        
                //   124: aload_0        
                //   125: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   128: invokestatic    wwe/PzCC.KkRZ:(Lwwe/PzCC;)I
                //   131: invokevirtual   java/net/ServerSocket.setSoTimeout:(I)V
                //   134: aload_0        
                //   135: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   138: aload_3        
                //   139: invokevirtual   java/net/ServerSocket.getLocalPort:()I
                //   142: invokestatic    wwe/PzCC.sxxV:(Lwwe/PzCC;I)I
                //   145: pop            
                //   146: aload_0        
                //   147: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   150: invokestatic    wwe/PzCC.XXkX:(Lwwe/PzCC;)Lwwe/rfyb;
                //   153: invokevirtual   wwe/rfyb.gKrV:()Ljava/net/InetAddress;
                //   156: astore          5
                //   158: aload           5
                //   160: ifnonnull       175
                //   163: aload_0        
                //   164: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   167: invokestatic    wwe/PzCC.XXkX:(Lwwe/PzCC;)Lwwe/rfyb;
                //   170: invokevirtual   wwe/rfyb.JKrK:()Ljava/net/InetAddress;
                //   173: astore          5
                //   175: aload           5
                //   177: invokevirtual   java/net/InetAddress.getAddress:()[B
                //   180: checkcast       [B
                //   183: astore          6
                //   185: aload_0        
                //   186: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   189: invokestatic    wwe/PzCC.XXkX:(Lwwe/PzCC;)Lwwe/rfyb;
                //   192: aload           6
                //   194: invokevirtual   wwe/rfyb.EavP:([B)J
                //   197: lstore          7
                //   199: aload_0        
                //   200: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   203: invokestatic    wwe/PzCC.beOO:(Lwwe/PzCC;)Ljava/io/File;
                //   206: invokevirtual   java/io/File.getName:()Ljava/lang/String;
                //   209: ldc             32
                //   211: ldc             95
                //   213: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
                //   216: astore          9
                //   218: aload           9
                //   220: ldc             9
                //   222: ldc             95
                //   224: invokevirtual   java/lang/String.replace:(CC)Ljava/lang/String;
                //   227: astore          9
                //   229: aload_0        
                //   230: getfield        wwe/luXL.BLCI:Z
                //   233: ifeq            250
                //   236: aload_0        
                //   237: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   240: invokestatic    wwe/PzCC.cRIJ:(Lwwe/PzCC;)Lwwe/iroq;
                //   243: aload_0        
                //   244: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   247: invokevirtual   wwe/iroq.sWUQ:(Lwwe/PzCC;)V
                //   250: aload_0        
                //   251: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   254: invokestatic    wwe/PzCC.XXkX:(Lwwe/PzCC;)Lwwe/rfyb;
                //   257: aload_0        
                //   258: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   261: invokestatic    wwe/PzCC.foFO:(Lwwe/PzCC;)Ljava/lang/String;
                //   264: new             Ljava/lang/StringBuilder;
                //   267: dup            
                //   268: invokespecial   java/lang/StringBuilder.<init>:()V
                //   271: ldc             "DCC SEND "
                //   273: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   276: aload           9
                //   278: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   281: ldc             " "
                //   283: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   286: lload           7
                //   288: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
                //   291: ldc             " "
                //   293: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   296: aload_0        
                //   297: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   300: invokestatic    wwe/PzCC.qwqA:(Lwwe/PzCC;)I
                //   303: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
                //   306: ldc             " "
                //   308: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
                //   311: aload_0        
                //   312: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   315: invokestatic    wwe/PzCC.beOO:(Lwwe/PzCC;)Ljava/io/File;
                //   318: invokevirtual   java/io/File.length:()J
                //   321: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
                //   324: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
                //   327: invokevirtual   wwe/rfyb.oYmD:(Ljava/lang/String;Ljava/lang/String;)V
                //   330: aload_0        
                //   331: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   334: aload_3        
                //   335: invokevirtual   java/net/ServerSocket.accept:()Ljava/net/Socket;
                //   338: invokestatic    wwe/PzCC.YrOU:(Lwwe/PzCC;Ljava/net/Socket;)Ljava/net/Socket;
                //   341: pop            
                //   342: aload_0        
                //   343: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   346: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   349: ldc             30000
                //   351: invokevirtual   java/net/Socket.setSoTimeout:(I)V
                //   354: aload_0        
                //   355: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   358: invokestatic    java/lang/System.currentTimeMillis:()J
                //   361: invokestatic    wwe/PzCC.MyqF:(Lwwe/PzCC;J)J
                //   364: pop2           
                //   365: aload_0        
                //   366: getfield        wwe/luXL.BLCI:Z
                //   369: ifeq            386
                //   372: aload_0        
                //   373: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   376: invokestatic    wwe/PzCC.cRIJ:(Lwwe/PzCC;)Lwwe/iroq;
                //   379: aload_0        
                //   380: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   383: invokevirtual   wwe/iroq.JJDC:(Lwwe/PzCC;)V
                //   386: aload_3        
                //   387: invokevirtual   java/net/ServerSocket.close:()V
                //   390: new             Ljava/io/BufferedOutputStream;
                //   393: dup            
                //   394: aload_0        
                //   395: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   398: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   401: invokevirtual   java/net/Socket.getOutputStream:()Ljava/io/OutputStream;
                //   404: invokespecial   java/io/BufferedOutputStream.<init>:(Ljava/io/OutputStream;)V
                //   407: astore          10
                //   409: new             Ljava/io/BufferedInputStream;
                //   412: dup            
                //   413: aload_0        
                //   414: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   417: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   420: invokevirtual   java/net/Socket.getInputStream:()Ljava/io/InputStream;
                //   423: invokespecial   java/io/BufferedInputStream.<init>:(Ljava/io/InputStream;)V
                //   426: astore          11
                //   428: new             Ljava/io/BufferedInputStream;
                //   431: dup            
                //   432: new             Ljava/io/FileInputStream;
                //   435: dup            
                //   436: aload_0        
                //   437: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   440: invokestatic    wwe/PzCC.beOO:(Lwwe/PzCC;)Ljava/io/File;
                //   443: invokespecial   java/io/FileInputStream.<init>:(Ljava/io/File;)V
                //   446: invokespecial   java/io/BufferedInputStream.<init>:(Ljava/io/InputStream;)V
                //   449: astore_1       
                //   450: aload_0        
                //   451: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   454: invokestatic    wwe/PzCC.Usxc:(Lwwe/PzCC;)J
                //   457: ldc2_w          -2263744242349570061
                //   460: ldc2_w          5856099891276411079
                //   463: dup2_x2        
                //   464: pop2           
                //   465: pop2           
                //   466: ldc2_w          5856099891276411079
                //   469: lxor           
                //   470: lcmp           
                //   471: ifle            534
                //   474: ldc2_w          -6497152693458097938
                //   477: ldc2_w          -454069136023147796
                //   480: dup2_x2        
                //   481: pop2           
                //   482: pop2           
                //   483: ldc2_w          -454069136023147796
                //   486: lxor           
                //   487: lstore          12
                //   489: lload           12
                //   491: aload_0        
                //   492: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   495: invokestatic    wwe/PzCC.Usxc:(Lwwe/PzCC;)J
                //   498: lcmp           
                //   499: ifge            534
                //   502: lload           12
                //   504: aload_1        
                //   505: aload_0        
                //   506: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   509: invokestatic    wwe/PzCC.Usxc:(Lwwe/PzCC;)J
                //   512: lload           12
                //   514: lsub           
                //   515: invokevirtual   java/io/BufferedInputStream.skip:(J)J
                //   518: ladd           
                //   519: lstore          12
                //   521: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //   524: ifeq            489
                //   527: aconst_null    
                //   528: athrow         
                //   529: nop            
                //   530: nop            
                //   531: nop            
                //   532: nop            
                //   533: athrow         
                //   534: ldc             1024
                //   536: newarray        B
                //   538: astore          12
                //   540: ldc             4
                //   542: newarray        B
                //   544: astore          13
                //   546: ldc             0
                //   548: istore          14
                //   550: aload_1        
                //   551: aload           12
                //   553: ldc             0
                //   555: aload           12
                //   557: arraylength    
                //   558: invokevirtual   java/io/BufferedInputStream.read:([BII)I
                //   561: dup            
                //   562: istore          14
                //   564: ldc             -1
                //   566: if_icmpeq       637
                //   569: aload           10
                //   571: aload           12
                //   573: ldc             0
                //   575: iload           14
                //   577: invokevirtual   java/io/BufferedOutputStream.write:([BII)V
                //   580: aload           10
                //   582: invokevirtual   java/io/BufferedOutputStream.flush:()V
                //   585: aload           11
                //   587: aload           13
                //   589: ldc             0
                //   591: aload           13
                //   593: arraylength    
                //   594: invokevirtual   java/io/BufferedInputStream.read:([BII)I
                //   597: pop            
                //   598: aload_0        
                //   599: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   602: aload_0        
                //   603: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   606: invokestatic    wwe/PzCC.Usxc:(Lwwe/PzCC;)J
                //   609: iload           14
                //   611: i2l            
                //   612: ladd           
                //   613: invokestatic    wwe/PzCC.ioUv:(Lwwe/PzCC;J)J
                //   616: pop2           
                //   617: aload_0        
                //   618: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   621: invokestatic    wwe/PzCC.kZLg:(Lwwe/PzCC;)V
                //   624: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //   627: ifeq            550
                //   630: aconst_null    
                //   631: athrow         
                //   632: nop            
                //   633: nop            
                //   634: nop            
                //   635: nop            
                //   636: athrow         
                //   637: aload_1        
                //   638: invokevirtual   java/io/BufferedInputStream.close:()V
                //   641: aload_0        
                //   642: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   645: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   648: invokevirtual   java/net/Socket.close:()V
                //   651: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //   654: ifeq            736
                //   657: aconst_null    
                //   658: athrow         
                //   659: astore_3       
                //   660: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //   663: ifeq            736
                //   666: aconst_null    
                //   667: athrow         
                //   668: astore_3       
                //   669: aload_3        
                //   670: astore_2       
                //   671: aload_1        
                //   672: invokevirtual   java/io/BufferedInputStream.close:()V
                //   675: aload_0        
                //   676: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   679: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   682: invokevirtual   java/net/Socket.close:()V
                //   685: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //   688: ifeq            736
                //   691: aconst_null    
                //   692: athrow         
                //   693: astore_3       
                //   694: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //   697: ifeq            736
                //   700: aconst_null    
                //   701: athrow         
                //   702: astore          15
                //   704: aload_1        
                //   705: invokevirtual   java/io/BufferedInputStream.close:()V
                //   708: aload_0        
                //   709: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   712: invokestatic    wwe/PzCC.oDup:(Lwwe/PzCC;)Ljava/net/Socket;
                //   715: invokevirtual   java/net/Socket.close:()V
                //   718: getstatic       wwe/luXL.\u2004\u2005\u2000\u200e\u200a:Z
                //   721: ifeq            728
                //   724: aconst_null    
                //   725: athrow         
                //   726: astore          16
                //   728: aload           15
                //   730: athrow         
                //   731: nop            
                //   732: nop            
                //   733: nop            
                //   734: nop            
                //   735: athrow         
                //   736: aload_0        
                //   737: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   740: invokestatic    wwe/PzCC.XXkX:(Lwwe/PzCC;)Lwwe/rfyb;
                //   743: aload_0        
                //   744: getfield        wwe/luXL.FoCW:Lwwe/PzCC;
                //   747: aload_2        
                //   748: invokevirtual   wwe/rfyb.PmDi:(Lwwe/PzCC;Ljava/lang/Exception;)V
                //   751: return         
                //   752: nop            
                //   753: nop            
                //   754: nop            
                //   755: nop            
                //   756: athrow         
                //    StackMapTable: 00 1C FF 00 03 00 12 07 00 02 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 00 FF 00 2B 00 00 00 01 07 00 11 FF 00 04 00 12 07 00 02 05 05 05 07 00 27 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 FF 00 03 00 12 07 00 02 05 05 07 00 29 07 00 27 01 00 00 00 00 00 00 00 00 00 00 00 01 00 00 5C 07 00 1B FF 00 0C 00 00 00 01 07 00 11 FF 00 04 00 12 07 00 02 05 05 07 00 29 07 00 27 01 00 00 00 00 00 00 00 00 00 00 00 01 00 00 FF 00 0D 00 00 00 01 07 00 11 FF 00 04 00 12 07 00 02 05 05 07 00 29 07 00 27 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 FF 00 33 00 12 07 00 02 05 05 07 00 29 07 00 27 07 00 4F 00 00 00 00 00 00 00 00 00 00 00 01 00 00 FF 00 4A 00 11 07 00 02 05 05 07 00 29 07 00 27 07 00 4F 07 00 55 04 07 00 67 00 00 00 00 00 00 00 01 00 00 FB 00 87 FF 00 66 00 10 07 00 02 07 00 C3 05 07 00 29 07 00 27 07 00 4F 07 00 55 04 07 00 67 07 00 BA 07 00 C3 04 00 00 00 01 00 00 FF 00 27 00 00 00 01 07 00 11 FF 00 04 00 11 07 00 02 07 00 C3 05 07 00 29 07 00 27 07 00 4F 07 00 55 04 07 00 67 07 00 BA 07 00 C3 00 00 00 00 00 01 00 00 FF 00 0F 00 11 07 00 02 07 00 C3 05 07 00 29 07 00 27 07 00 4F 07 00 55 04 07 00 67 07 00 BA 07 00 C3 07 00 55 07 00 55 01 00 00 01 00 00 FF 00 51 00 00 00 01 07 00 11 FF 00 04 00 11 07 00 02 07 00 C3 05 07 00 29 07 00 27 07 00 4F 07 00 55 04 07 00 67 07 00 BA 07 00 C3 07 00 55 07 00 55 01 00 00 01 00 00 55 07 00 1B FF 00 08 00 12 07 00 02 07 00 C3 05 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 01 07 00 1B FF 00 18 00 12 07 00 02 07 00 C3 07 00 1B 07 00 1B 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 01 07 00 1B FF 00 08 00 12 07 00 02 07 00 C3 07 00 1B 00 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 01 07 00 11 FF 00 17 00 12 07 00 02 07 00 C3 07 00 1B 00 00 00 00 00 00 00 00 00 00 00 00 07 00 11 00 01 00 01 07 00 1B 01 FF 00 02 00 00 00 01 07 00 11 FF 00 04 00 12 07 00 02 07 00 C3 07 00 1B 07 00 F7 00 00 00 00 00 00 00 00 00 00 00 00 00 01 00 00 FF 00 0F 00 00 00 01 07 00 11
                //    Exceptions:
                //  Try           Handler
                //  Start  End    Start  End    Type                 
                //  -----  -----  -----  -----  ---------------------
                //  65     78     86     99     Ljava/lang/Exception;
                //  637    651    659    668    Ljava/lang/Exception;
                //  8      48     668    702    Ljava/lang/Exception;
                //  53     99     668    702    Ljava/lang/Exception;
                //  104    118    668    702    Ljava/lang/Exception;
                //  123    529    668    702    Ljava/lang/Exception;
                //  534    632    668    702    Ljava/lang/Exception;
                //  671    685    693    702    Ljava/lang/Exception;
                //  8      48     702    731    Any
                //  53     99     702    731    Any
                //  104    118    702    731    Any
                //  123    529    702    731    Any
                //  534    632    702    731    Any
                //  668    671    702    731    Any
                //  704    718    726    728    Ljava/lang/Exception;
                //  702    704    702    731    Any
                // 
                throw new IllegalStateException("An error occurred while decompiling this method.");
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: new             Lwwe/luXL;
        //     7: dup            
        //     8: aload_0        
        //     9: iload_1        
        //    10: invokespecial   wwe/luXL.<init>:(Lwwe/PzCC;Z)V
        //    13: invokevirtual   wwe/luXL.start:()V
        //    16: return         
        //    17: nop            
        //    18: nop            
        //    19: nop            
        //    20: athrow         
        //    StackMapTable: 00 03 FC 00 03 01 00 FF 00 0C 00 00 00 01 07 00 27
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    void iJzE(final long jneM) {
        this.jneM = jneM;
    }
    
    private void gnbN() {
        if (this.IAMl > (0x2F36F587DAD7BF2FL ^ 0x2F36F587DAD7BF2FL)) {
            try {
                Thread.sleep(this.IAMl);
                if (PzCC.\u2007\u2000\u200e\u200f) {
                    throw null;
                }
            }
            catch (InterruptedException ex) {}
        }
    }
    
    public String fpLL() {
        return this.NRtx;
    }
    
    public String Qzqt() {
        return this.nnur;
    }
    
    public String YWFX() {
        return this.SDuq;
    }
    
    public File KIOh() {
        return this.cKmJ;
    }
    
    public int wEOY() {
        return this.UxXV;
    }
    
    public boolean ESZY() {
        return this.xpAo;
    }
    
    public boolean qOJU() {
        boolean b;
        if (!this.ESZY()) {
            b = true;
            if (PzCC.\u2007\u2000\u200e\u200f) {
                throw null;
            }
        }
        else {
            b = false;
        }
        return b;
    }
    
    public void TkxE(final long iaMl) {
        this.IAMl = iaMl;
    }
    
    public long soEj() {
        return this.IAMl;
    }
    
    public long FMfO() {
        return this.GxGM;
    }
    
    public long xfnN() {
        return this.jneM;
    }
    
    public double KZHu() {
        return 100.0 * (this.xfnN() / (double)this.FMfO());
    }
    
    public void cnyH() {
        try {
            this.OBKu.close();
            if (PzCC.\u2007\u2000\u200e\u200f) {
                throw null;
            }
        }
        catch (Exception ex) {}
    }
    
    public long cYkW() {
        final long n = (System.currentTimeMillis() - this.iiSi) / (0xBD7CFC178B3BFE16L ^ 0xBD7CFC178B3BFDFEL);
        if (n <= (0x5037905773DB9BADL ^ 0x5037905773DB9BADL)) {
            return 0xF5F4F277F1E13712L ^ 0xF5F4F277F1E13712L;
        }
        return this.xfnN() / n;
    }
    
    public long iLGr() {
        return this.ZsMp;
    }
    
    static long rDJM(final PzCC pzCC) {
        return pzCC.ZsMp;
    }
    
    static rfyb XXkX(final PzCC pzCC) {
        return pzCC.jzFj;
    }
    
    static Socket YrOU(final PzCC pzCC, final Socket obKu) {
        return pzCC.OBKu = obKu;
    }
    
    static int qwqA(final PzCC pzCC) {
        return pzCC.UxXV;
    }
    
    static Socket oDup(final PzCC pzCC) {
        return pzCC.OBKu;
    }
    
    static long MyqF(final PzCC pzCC, final long iiSi) {
        return pzCC.iiSi = iiSi;
    }
    
    static iroq cRIJ(final PzCC pzCC) {
        return pzCC.pqmX;
    }
    
    static long Usxc(final PzCC pzCC) {
        return pzCC.jneM;
    }
    
    static long ioUv(final PzCC pzCC, final long jneM) {
        return pzCC.jneM = jneM;
    }
    
    static void kZLg(final PzCC pzCC) {
        pzCC.gnbN();
    }
    
    static int KkRZ(final PzCC pzCC) {
        return pzCC.YuhY;
    }
    
    static int sxxV(final PzCC pzCC, final int uxXV) {
        return pzCC.UxXV = uxXV;
    }
    
    static File beOO(final PzCC pzCC) {
        return pzCC.cKmJ;
    }
    
    static String foFO(final PzCC pzCC) {
        return pzCC.NRtx;
    }
}
