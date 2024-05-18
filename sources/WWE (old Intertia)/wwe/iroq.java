package wwe;

import java.util.*;

public class iroq
{
    private rfyb HSHM;
    private Vector OSIP;
    
    iroq(final rfyb hshm) {
        super();
        this.OSIP = new Vector();
        this.HSHM = hshm;
    }
    
    boolean QZnJ(final String s, final String s2, final String s3, final String s4) {
        class kUPS extends Thread
        {
            final dcJR PMyx;
            final iroq bklh;
            
            kUPS(final iroq bklh, final dcJR pMyx) {
                this.bklh = bklh;
                this.PMyx = pMyx;
                super();
            }
            
            @Override
            public void run() {
                iroq.nHeb(this.bklh).ArMM(this.PMyx);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: ireturn        
        //     5: new             Ljava/util/StringTokenizer;
        //     8: dup            
        //     9: aload           4
        //    11: invokespecial   java/util/StringTokenizer.<init>:(Ljava/lang/String;)V
        //    14: astore          5
        //    16: aload           5
        //    18: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    21: pop            
        //    22: aload           5
        //    24: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    27: astore          6
        //    29: aload           5
        //    31: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    34: astore          7
        //    36: aload           6
        //    38: ldc             "SEND"
        //    40: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //    43: ifeq            150
        //    46: aload           5
        //    48: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    51: invokestatic    java/lang/Long.parseLong:(Ljava/lang/String;)J
        //    54: lstore          8
        //    56: aload           5
        //    58: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    61: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //    64: istore          10
        //    66: ldc2_w          3201826644190158585
        //    69: ldc2_w          -5007069058711980714
        //    72: dup2_x2        
        //    73: pop2           
        //    74: pop2           
        //    75: ldc2_w          5007069058711980713
        //    78: lxor           
        //    79: lstore          11
        //    81: aload           5
        //    83: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //    86: invokestatic    java/lang/Long.parseLong:(Ljava/lang/String;)J
        //    89: lstore          11
        //    91: getstatic       wwe/iroq.\u2007\u200a:Z
        //    94: ifeq            101
        //    97: aconst_null    
        //    98: athrow         
        //    99: astore          13
        //   101: new             Lwwe/PzCC;
        //   104: dup            
        //   105: aload_0        
        //   106: getfield        wwe/iroq.HSHM:Lwwe/rfyb;
        //   109: aload_0        
        //   110: aload_1        
        //   111: aload_2        
        //   112: aload_3        
        //   113: aload           6
        //   115: aload           7
        //   117: lload           8
        //   119: iload           10
        //   121: lload           11
        //   123: invokespecial   wwe/PzCC.<init>:(Lwwe/rfyb;Lwwe/iroq;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JIJ)V
        //   126: astore          13
        //   128: aload_0        
        //   129: getfield        wwe/iroq.HSHM:Lwwe/rfyb;
        //   132: aload           13
        //   134: invokevirtual   wwe/rfyb.UuPx:(Lwwe/PzCC;)V
        //   137: getstatic       wwe/iroq.\u2007\u200a:Z
        //   140: ifeq            637
        //   143: aconst_null    
        //   144: athrow         
        //   145: nop            
        //   146: nop            
        //   147: nop            
        //   148: nop            
        //   149: athrow         
        //   150: aload           6
        //   152: ldc             "RESUME"
        //   154: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   157: ifeq            368
        //   160: aload           5
        //   162: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   165: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   168: istore          8
        //   170: aload           5
        //   172: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   175: invokestatic    java/lang/Long.parseLong:(Ljava/lang/String;)J
        //   178: lstore          9
        //   180: aconst_null    
        //   181: astore          11
        //   183: aload_0        
        //   184: getfield        wwe/iroq.OSIP:Ljava/util/Vector;
        //   187: dup            
        //   188: astore          12
        //   190: monitorenter   
        //   191: ldc             0
        //   193: istore          13
        //   195: iload           13
        //   197: aload_0        
        //   198: getfield        wwe/iroq.OSIP:Ljava/util/Vector;
        //   201: invokevirtual   java/util/Vector.size:()I
        //   204: if_icmpge       281
        //   207: aload_0        
        //   208: getfield        wwe/iroq.OSIP:Ljava/util/Vector;
        //   211: iload           13
        //   213: invokevirtual   java/util/Vector.elementAt:(I)Ljava/lang/Object;
        //   216: checkcast       Lwwe/PzCC;
        //   219: astore          11
        //   221: aload           11
        //   223: invokevirtual   wwe/PzCC.fpLL:()Ljava/lang/String;
        //   226: aload_1        
        //   227: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   230: ifeq            265
        //   233: aload           11
        //   235: invokevirtual   wwe/PzCC.wEOY:()I
        //   238: iload           8
        //   240: if_icmpne       265
        //   243: aload_0        
        //   244: getfield        wwe/iroq.OSIP:Ljava/util/Vector;
        //   247: iload           13
        //   249: invokevirtual   java/util/Vector.removeElementAt:(I)V
        //   252: getstatic       wwe/iroq.\u2007\u200a:Z
        //   255: ifeq            281
        //   258: aconst_null    
        //   259: athrow         
        //   260: nop            
        //   261: nop            
        //   262: nop            
        //   263: nop            
        //   264: athrow         
        //   265: iinc            13, 1
        //   268: getstatic       wwe/iroq.\u2007\u200a:Z
        //   271: ifeq            195
        //   274: aconst_null    
        //   275: athrow         
        //   276: nop            
        //   277: nop            
        //   278: nop            
        //   279: nop            
        //   280: athrow         
        //   281: aload           12
        //   283: monitorexit    
        //   284: getstatic       wwe/iroq.\u2007\u200a:Z
        //   287: ifeq            305
        //   290: aconst_null    
        //   291: athrow         
        //   292: astore          14
        //   294: aload           12
        //   296: monitorexit    
        //   297: aload           14
        //   299: athrow         
        //   300: nop            
        //   301: nop            
        //   302: nop            
        //   303: nop            
        //   304: athrow         
        //   305: aload           11
        //   307: ifnull          355
        //   310: aload           11
        //   312: lload           9
        //   314: invokevirtual   wwe/PzCC.iJzE:(J)V
        //   317: aload_0        
        //   318: getfield        wwe/iroq.HSHM:Lwwe/rfyb;
        //   321: aload_1        
        //   322: new             Ljava/lang/StringBuilder;
        //   325: dup            
        //   326: invokespecial   java/lang/StringBuilder.<init>:()V
        //   329: ldc             "DCC ACCEPT file.ext "
        //   331: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   334: iload           8
        //   336: invokevirtual   java/lang/StringBuilder.append:(I)Ljava/lang/StringBuilder;
        //   339: ldc             " "
        //   341: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   344: lload           9
        //   346: invokevirtual   java/lang/StringBuilder.append:(J)Ljava/lang/StringBuilder;
        //   349: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   352: invokevirtual   wwe/rfyb.oYmD:(Ljava/lang/String;Ljava/lang/String;)V
        //   355: getstatic       wwe/iroq.\u2007\u200a:Z
        //   358: ifeq            637
        //   361: aconst_null    
        //   362: athrow         
        //   363: nop            
        //   364: nop            
        //   365: nop            
        //   366: nop            
        //   367: athrow         
        //   368: aload           6
        //   370: ldc             "ACCEPT"
        //   372: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   375: ifeq            553
        //   378: aload           5
        //   380: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   383: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   386: istore          8
        //   388: aload           5
        //   390: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   393: invokestatic    java/lang/Long.parseLong:(Ljava/lang/String;)J
        //   396: lstore          9
        //   398: aconst_null    
        //   399: astore          11
        //   401: aload_0        
        //   402: getfield        wwe/iroq.OSIP:Ljava/util/Vector;
        //   405: dup            
        //   406: astore          12
        //   408: monitorenter   
        //   409: ldc             0
        //   411: istore          13
        //   413: iload           13
        //   415: aload_0        
        //   416: getfield        wwe/iroq.OSIP:Ljava/util/Vector;
        //   419: invokevirtual   java/util/Vector.size:()I
        //   422: if_icmpge       499
        //   425: aload_0        
        //   426: getfield        wwe/iroq.OSIP:Ljava/util/Vector;
        //   429: iload           13
        //   431: invokevirtual   java/util/Vector.elementAt:(I)Ljava/lang/Object;
        //   434: checkcast       Lwwe/PzCC;
        //   437: astore          11
        //   439: aload           11
        //   441: invokevirtual   wwe/PzCC.fpLL:()Ljava/lang/String;
        //   444: aload_1        
        //   445: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   448: ifeq            483
        //   451: aload           11
        //   453: invokevirtual   wwe/PzCC.wEOY:()I
        //   456: iload           8
        //   458: if_icmpne       483
        //   461: aload_0        
        //   462: getfield        wwe/iroq.OSIP:Ljava/util/Vector;
        //   465: iload           13
        //   467: invokevirtual   java/util/Vector.removeElementAt:(I)V
        //   470: getstatic       wwe/iroq.\u2007\u200a:Z
        //   473: ifeq            499
        //   476: aconst_null    
        //   477: athrow         
        //   478: nop            
        //   479: nop            
        //   480: nop            
        //   481: nop            
        //   482: athrow         
        //   483: iinc            13, 1
        //   486: getstatic       wwe/iroq.\u2007\u200a:Z
        //   489: ifeq            413
        //   492: aconst_null    
        //   493: athrow         
        //   494: nop            
        //   495: nop            
        //   496: nop            
        //   497: nop            
        //   498: athrow         
        //   499: aload           12
        //   501: monitorexit    
        //   502: getstatic       wwe/iroq.\u2007\u200a:Z
        //   505: ifeq            523
        //   508: aconst_null    
        //   509: athrow         
        //   510: astore          15
        //   512: aload           12
        //   514: monitorexit    
        //   515: aload           15
        //   517: athrow         
        //   518: nop            
        //   519: nop            
        //   520: nop            
        //   521: nop            
        //   522: athrow         
        //   523: aload           11
        //   525: ifnull          540
        //   528: aload           11
        //   530: aload           11
        //   532: invokevirtual   wwe/PzCC.KIOh:()Ljava/io/File;
        //   535: ldc             1
        //   537: invokevirtual   wwe/PzCC.JqMb:(Ljava/io/File;Z)V
        //   540: getstatic       wwe/iroq.\u2007\u200a:Z
        //   543: ifeq            637
        //   546: aconst_null    
        //   547: athrow         
        //   548: nop            
        //   549: nop            
        //   550: nop            
        //   551: nop            
        //   552: athrow         
        //   553: aload           6
        //   555: ldc             "CHAT"
        //   557: invokevirtual   java/lang/String.equals:(Ljava/lang/Object;)Z
        //   560: ifeq            629
        //   563: aload           5
        //   565: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   568: invokestatic    java/lang/Long.parseLong:(Ljava/lang/String;)J
        //   571: lstore          8
        //   573: aload           5
        //   575: invokevirtual   java/util/StringTokenizer.nextToken:()Ljava/lang/String;
        //   578: invokestatic    java/lang/Integer.parseInt:(Ljava/lang/String;)I
        //   581: istore          10
        //   583: new             Lwwe/dcJR;
        //   586: dup            
        //   587: aload_0        
        //   588: getfield        wwe/iroq.HSHM:Lwwe/rfyb;
        //   591: aload_1        
        //   592: aload_2        
        //   593: aload_3        
        //   594: lload           8
        //   596: iload           10
        //   598: invokespecial   wwe/dcJR.<init>:(Lwwe/rfyb;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;JI)V
        //   601: astore          11
        //   603: new             Lwwe/kUPS;
        //   606: dup            
        //   607: aload_0        
        //   608: aload           11
        //   610: invokespecial   wwe/kUPS.<init>:(Lwwe/iroq;Lwwe/dcJR;)V
        //   613: invokevirtual   wwe/kUPS.start:()V
        //   616: getstatic       wwe/iroq.\u2007\u200a:Z
        //   619: ifeq            637
        //   622: aconst_null    
        //   623: athrow         
        //   624: nop            
        //   625: nop            
        //   626: nop            
        //   627: nop            
        //   628: athrow         
        //   629: ldc             0
        //   631: ireturn        
        //   632: nop            
        //   633: nop            
        //   634: nop            
        //   635: nop            
        //   636: athrow         
        //   637: ldc             1
        //   639: ireturn        
        //   640: nop            
        //   641: nop            
        //   642: nop            
        //   643: nop            
        //   644: athrow         
        //    StackMapTable: 00 21 FF 00 03 00 11 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 00 00 00 00 00 00 00 00 00 00 00 01 00 00 01 FF 00 5D 00 0F 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 04 01 04 00 00 00 01 00 01 07 00 1E 01 FF 00 2B 00 00 00 01 07 00 0E FF 00 04 00 11 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 00 00 00 00 00 00 00 00 01 00 00 FF 00 2C 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 01 00 00 01 00 00 FF 00 40 00 00 00 01 07 00 0E FF 00 04 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 01 00 00 01 00 00 FF 00 0A 00 00 00 01 07 00 0E FF 00 04 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 01 00 00 01 00 00 FF 00 0A 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 00 00 00 01 00 01 07 00 0E FF 00 07 00 00 00 01 07 00 0E FF 00 04 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 01 00 00 01 00 00 31 FF 00 07 00 00 00 01 07 00 0E FF 00 04 00 11 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 00 00 00 00 00 00 00 00 01 00 00 FF 00 2C 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 01 00 00 01 00 00 FF 00 40 00 00 00 01 07 00 0E FF 00 04 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 01 00 00 01 00 00 FF 00 0A 00 00 00 01 07 00 0E FF 00 04 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 01 00 00 01 00 00 FF 00 0A 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 00 00 00 01 00 01 07 00 0E FF 00 07 00 00 00 01 07 00 0E FF 00 04 00 10 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 01 04 07 00 47 07 00 15 01 00 00 01 00 00 10 FF 00 07 00 00 00 01 07 00 0E FF 00 04 00 11 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 00 00 00 00 00 00 00 00 01 00 00 FF 00 46 00 00 00 01 07 00 0E FF 00 04 00 11 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 00 00 00 00 00 00 00 00 01 00 00 FF 00 02 00 00 00 01 07 00 0E FF 00 04 00 11 07 00 02 07 00 20 07 00 20 07 00 20 07 00 20 07 00 22 07 00 20 07 00 20 00 00 00 00 00 00 00 00 01 00 00 FF 00 02 00 00 00 01 07 00 0E
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                 
        //  -----  -----  -----  -----  ---------------------
        //  81     91     99     101    Ljava/lang/Exception;
        //  191    260    292    300    Any
        //  265    276    292    300    Any
        //  281    284    292    300    Any
        //  292    297    292    300    Any
        //  409    478    510    518    Any
        //  483    494    510    518    Any
        //  499    502    510    518    Any
        //  510    515    510    518    Any
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    void sWUQ(final PzCC pzCC) {
        synchronized (this.OSIP) {
            this.OSIP.addElement(pzCC);
            // monitorexit(this.OSIP)
            if (iroq.\u2007\u200a) {
                throw null;
            }
        }
    }
    
    void JJDC(final PzCC pzCC) {
        this.OSIP.removeElement(pzCC);
    }
    
    static rfyb nHeb(final iroq iroq) {
        return iroq.HSHM;
    }
}
