package wwe;

import java.net.*;

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
