package wwe;

import java.text.*;
import net.minecraft.client.gui.*;
import wwe.modules.*;
import org.lwjgl.opengl.*;
import wwe.utils.*;
import java.util.*;

public class cRUT
{
    public int YJKE;
    public int tMKv;
    public int qSwY;
    public int Jqfu;
    public boolean KIMB;
    public DecimalFormat ttQQ;
    
    public cRUT() {
        super();
        this.ttQQ = new DecimalFormat("#.#");
    }
    
    public void zeZm(final int n, final int n2) {
        class OEBD implements Comparator<qMIe>
        {
            final cRUT GCHD;
            
            OEBD(final cRUT gchd) {
                this.GCHD = gchd;
                super();
            }
            
            public int CMxe(final qMIe qmIe, final qMIe qmIe2) {
                return qmIe.czUH().compareTo(qmIe2.czUH());
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.CMxe((qMIe)o, (qMIe)o2);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: return         
        //     4: new             Lnet/minecraft/client/gui/ScaledResolution;
        //     7: dup            
        //     8: getstatic       wwe/utils/Wrapper.mc:Lnet/minecraft/client/Minecraft;
        //    11: invokespecial   net/minecraft/client/gui/ScaledResolution.<init>:(Lnet/minecraft/client/Minecraft;)V
        //    14: astore_3       
        //    15: ldc             1
        //    17: aload_3        
        //    18: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaleFactor:()I
        //    21: imul           
        //    22: istore          4
        //    24: iload_2        
        //    25: istore          5
        //    27: iload_1        
        //    28: i2f            
        //    29: iload_2        
        //    30: i2f            
        //    31: iload_1        
        //    32: ldc             60
        //    34: iadd           
        //    35: i2f            
        //    36: iload_2        
        //    37: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //    40: checkcast       [Lwwe/AzzV;
        //    43: arraylength    
        //    44: ldc             12
        //    46: imul           
        //    47: ldc             12
        //    49: isub           
        //    50: iadd           
        //    51: i2f            
        //    52: ldc             -1342177280
        //    54: invokestatic    wwe/gnNa.wQYt:(FFFFI)V
        //    57: iload_1        
        //    58: i2f            
        //    59: iload_2        
        //    60: aload_0        
        //    61: getfield        wwe/cRUT.tMKv:I
        //    64: ldc             12
        //    66: imul           
        //    67: iadd           
        //    68: i2f            
        //    69: iload_1        
        //    70: ldc             60
        //    72: iadd           
        //    73: i2f            
        //    74: iload_2        
        //    75: aload_0        
        //    76: getfield        wwe/cRUT.tMKv:I
        //    79: ldc             12
        //    81: imul           
        //    82: iadd           
        //    83: ldc             12
        //    85: iadd           
        //    86: i2f            
        //    87: getstatic       wwe/vlwv.fomn:Lwwe/flSi;
        //    90: getfield        wwe/flSi.mMom:Lwwe/RCMD;
        //    93: invokevirtual   wwe/RCMD.JqMr:()Ljava/awt/Color;
        //    96: invokevirtual   java/awt/Color.getRGB:()I
        //    99: invokestatic    wwe/gnNa.wQYt:(FFFFI)V
        //   102: invokestatic    org/lwjgl/opengl/GL11.glPushMatrix:()V
        //   105: dconst_1       
        //   106: aload_3        
        //   107: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaleFactor:()I
        //   110: i2d            
        //   111: ddiv           
        //   112: dconst_1       
        //   113: aload_3        
        //   114: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaleFactor:()I
        //   117: i2d            
        //   118: ddiv           
        //   119: dconst_1       
        //   120: invokestatic    org/lwjgl/opengl/GL11.glScaled:(DDD)V
        //   123: iload_1        
        //   124: iload           4
        //   126: imul           
        //   127: iload_2        
        //   128: iload           4
        //   130: imul           
        //   131: iload_1        
        //   132: ldc             60
        //   134: iadd           
        //   135: iload           4
        //   137: imul           
        //   138: iload_2        
        //   139: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //   142: checkcast       [Lwwe/AzzV;
        //   145: arraylength    
        //   146: ldc             12
        //   148: imul           
        //   149: ldc             12
        //   151: isub           
        //   152: iadd           
        //   153: iload           4
        //   155: imul           
        //   156: ldc             1
        //   158: getstatic       wwe/vlwv.fomn:Lwwe/flSi;
        //   161: getfield        wwe/flSi.xeZd:Lwwe/RCMD;
        //   164: invokevirtual   wwe/RCMD.JqMr:()Ljava/awt/Color;
        //   167: invokevirtual   java/awt/Color.getRGB:()I
        //   170: invokestatic    wwe/gnNa.AvjN:(IIIIII)V
        //   173: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //   176: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //   179: checkcast       [Lwwe/AzzV;
        //   182: astore          6
        //   184: aload           6
        //   186: arraylength    
        //   187: istore          7
        //   189: ldc             0
        //   191: istore          8
        //   193: iload           8
        //   195: iload           7
        //   197: if_icmpge       258
        //   200: aload           6
        //   202: iload           8
        //   204: aaload         
        //   205: astore          9
        //   207: aload           9
        //   209: getstatic       wwe/modules/Category.GUI:Lwwe/modules/Category;
        //   212: invokevirtual   wwe/modules/Category.equals:(Ljava/lang/Object;)Z
        //   215: ifne            242
        //   218: aload           9
        //   220: invokevirtual   wwe/modules/Category.name:()Ljava/lang/String;
        //   223: iload_1        
        //   224: ldc             2
        //   226: iadd           
        //   227: i2f            
        //   228: iload           5
        //   230: ldc             2
        //   232: iadd           
        //   233: i2f            
        //   234: ldc             -1
        //   236: invokestatic    wwe/XybC.eTim:(Ljava/lang/String;FFI)V
        //   239: iinc            5, 12
        //   242: iinc            8, 1
        //   245: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //   248: ifeq            193
        //   251: aconst_null    
        //   252: athrow         
        //   253: nop            
        //   254: nop            
        //   255: nop            
        //   256: nop            
        //   257: athrow         
        //   258: aload_0        
        //   259: getfield        wwe/cRUT.YJKE:I
        //   262: ldc             1
        //   264: if_icmpeq       276
        //   267: aload_0        
        //   268: getfield        wwe/cRUT.YJKE:I
        //   271: ldc             2
        //   273: if_icmpne       773
        //   276: new             Ljava/util/ArrayList;
        //   279: dup            
        //   280: invokespecial   java/util/ArrayList.<init>:()V
        //   283: astore          6
        //   285: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //   288: invokevirtual   wwe/eOnJ.xRen:()Ljava/util/ArrayList;
        //   291: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   294: astore          7
        //   296: aload           7
        //   298: invokevirtual   java/util/Iterator.hasNext:()Z
        //   301: ifeq            380
        //   304: aload           7
        //   306: invokevirtual   java/util/Iterator.next:()Ljava/lang/Object;
        //   309: checkcast       Lwwe/qMIe;
        //   312: astore          8
        //   314: aload           8
        //   316: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //   319: checkcast       [Lwwe/AzzV;
        //   322: aload_0        
        //   323: getfield        wwe/cRUT.tMKv:I
        //   326: aaload         
        //   327: ifnull          349
        //   330: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //   333: checkcast       [Lwwe/AzzV;
        //   336: aload_0        
        //   337: getfield        wwe/cRUT.tMKv:I
        //   340: aaload         
        //   341: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //   344: ifeq            358
        //   347: aconst_null    
        //   348: athrow         
        //   349: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //   352: checkcast       [Lwwe/AzzV;
        //   355: ldc             0
        //   357: aaload         
        //   358: invokevirtual   wwe/qMIe.jrFS:(Lwwe/modules/Category;)Z
        //   361: ifeq            372
        //   364: aload           6
        //   366: aload           8
        //   368: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //   371: pop            
        //   372: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //   375: ifeq            296
        //   378: aconst_null    
        //   379: athrow         
        //   380: aload           6
        //   382: new             Lwwe/OEBD;
        //   385: dup            
        //   386: aload_0        
        //   387: invokespecial   wwe/OEBD.<init>:(Lwwe/cRUT;)V
        //   390: invokevirtual   java/util/ArrayList.sort:(Ljava/util/Comparator;)V
        //   393: iload_1        
        //   394: ldc             60
        //   396: iadd           
        //   397: ldc             3
        //   399: iadd           
        //   400: i2f            
        //   401: iload_2        
        //   402: aload_0        
        //   403: getfield        wwe/cRUT.tMKv:I
        //   406: ldc             12
        //   408: imul           
        //   409: iadd           
        //   410: ldc             0
        //   412: isub           
        //   413: i2f            
        //   414: iload_1        
        //   415: ldc             60
        //   417: iadd           
        //   418: ldc             3
        //   420: iadd           
        //   421: ldc             83
        //   423: iadd           
        //   424: ldc             15
        //   426: iadd           
        //   427: i2f            
        //   428: iload_2        
        //   429: aload_0        
        //   430: getfield        wwe/cRUT.tMKv:I
        //   433: ldc             12
        //   435: imul           
        //   436: iadd           
        //   437: aload           6
        //   439: invokevirtual   java/util/ArrayList.size:()I
        //   442: ldc             12
        //   444: imul           
        //   445: iadd           
        //   446: i2f            
        //   447: ldc             -1342177280
        //   449: invokestatic    wwe/gnNa.wQYt:(FFFFI)V
        //   452: iload_1        
        //   453: ldc             60
        //   455: iadd           
        //   456: ldc             3
        //   458: iadd           
        //   459: i2f            
        //   460: iload_2        
        //   461: aload_0        
        //   462: getfield        wwe/cRUT.tMKv:I
        //   465: ldc             12
        //   467: imul           
        //   468: iadd           
        //   469: aload_0        
        //   470: getfield        wwe/cRUT.qSwY:I
        //   473: ldc             12
        //   475: imul           
        //   476: iadd           
        //   477: i2f            
        //   478: iload_1        
        //   479: ldc             60
        //   481: iadd           
        //   482: ldc             3
        //   484: iadd           
        //   485: ldc             83
        //   487: iadd           
        //   488: ldc             15
        //   490: iadd           
        //   491: i2f            
        //   492: iload_2        
        //   493: aload_0        
        //   494: getfield        wwe/cRUT.tMKv:I
        //   497: ldc             12
        //   499: imul           
        //   500: iadd           
        //   501: aload_0        
        //   502: getfield        wwe/cRUT.qSwY:I
        //   505: ldc             12
        //   507: imul           
        //   508: iadd           
        //   509: ldc             12
        //   511: iadd           
        //   512: i2f            
        //   513: getstatic       wwe/vlwv.fomn:Lwwe/flSi;
        //   516: getfield        wwe/flSi.iJuB:Lwwe/RCMD;
        //   519: invokevirtual   wwe/RCMD.JqMr:()Ljava/awt/Color;
        //   522: invokevirtual   java/awt/Color.getRGB:()I
        //   525: invokestatic    wwe/gnNa.wQYt:(FFFFI)V
        //   528: invokestatic    org/lwjgl/opengl/GL11.glPushMatrix:()V
        //   531: dconst_1       
        //   532: aload_3        
        //   533: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaleFactor:()I
        //   536: i2d            
        //   537: ddiv           
        //   538: dconst_1       
        //   539: aload_3        
        //   540: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaleFactor:()I
        //   543: i2d            
        //   544: ddiv           
        //   545: dconst_1       
        //   546: invokestatic    org/lwjgl/opengl/GL11.glScaled:(DDD)V
        //   549: iload_1        
        //   550: ldc             60
        //   552: iadd           
        //   553: ldc             3
        //   555: iadd           
        //   556: iload           4
        //   558: imul           
        //   559: iload_2        
        //   560: aload_0        
        //   561: getfield        wwe/cRUT.tMKv:I
        //   564: ldc             12
        //   566: imul           
        //   567: iadd           
        //   568: ldc             0
        //   570: isub           
        //   571: iload           4
        //   573: imul           
        //   574: iload_1        
        //   575: ldc             60
        //   577: iadd           
        //   578: ldc             3
        //   580: iadd           
        //   581: ldc             83
        //   583: iadd           
        //   584: ldc             15
        //   586: iadd           
        //   587: iload           4
        //   589: imul           
        //   590: iload_2        
        //   591: aload_0        
        //   592: getfield        wwe/cRUT.tMKv:I
        //   595: ldc             12
        //   597: imul           
        //   598: iadd           
        //   599: aload           6
        //   601: invokevirtual   java/util/ArrayList.size:()I
        //   604: ldc             12
        //   606: imul           
        //   607: iadd           
        //   608: iload           4
        //   610: imul           
        //   611: ldc             1
        //   613: getstatic       wwe/vlwv.fomn:Lwwe/flSi;
        //   616: getfield        wwe/flSi.xeZd:Lwwe/RCMD;
        //   619: invokevirtual   wwe/RCMD.JqMr:()Ljava/awt/Color;
        //   622: invokevirtual   java/awt/Color.getRGB:()I
        //   625: invokestatic    wwe/gnNa.AvjN:(IIIIII)V
        //   628: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //   631: iload_2        
        //   632: aload_0        
        //   633: getfield        wwe/cRUT.tMKv:I
        //   636: ldc             12
        //   638: imul           
        //   639: iadd           
        //   640: istore          7
        //   642: aload           6
        //   644: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //   647: astore          8
        //   649: aload           8
        //   651: invokevirtual   java/util/Iterator.hasNext:()Z
        //   654: ifeq            773
        //   657: aload           8
        //   659: invokevirtual   java/util/Iterator.next:()Ljava/lang/Object;
        //   662: checkcast       Lwwe/qMIe;
        //   665: astore          9
        //   667: aload           9
        //   669: invokevirtual   wwe/qMIe.IuiN:()Z
        //   672: ifeq            735
        //   675: aload           9
        //   677: aload           6
        //   679: aload_0        
        //   680: getfield        wwe/cRUT.qSwY:I
        //   683: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   686: if_acmpeq       735
        //   689: iload_1        
        //   690: ldc             60
        //   692: iadd           
        //   693: ldc             3
        //   695: iadd           
        //   696: i2f            
        //   697: iload           7
        //   699: i2f            
        //   700: iload_1        
        //   701: ldc             60
        //   703: iadd           
        //   704: ldc             3
        //   706: iadd           
        //   707: ldc             83
        //   709: iadd           
        //   710: ldc             15
        //   712: iadd           
        //   713: i2f            
        //   714: iload           7
        //   716: ldc             12
        //   718: iadd           
        //   719: i2f            
        //   720: getstatic       wwe/vlwv.fomn:Lwwe/flSi;
        //   723: getfield        wwe/flSi.xeZd:Lwwe/RCMD;
        //   726: invokevirtual   wwe/RCMD.JqMr:()Ljava/awt/Color;
        //   729: invokevirtual   java/awt/Color.getRGB:()I
        //   732: invokestatic    wwe/gnNa.wQYt:(FFFFI)V
        //   735: aload           9
        //   737: invokevirtual   wwe/qMIe.czUH:()Ljava/lang/String;
        //   740: iload_1        
        //   741: ldc             60
        //   743: iadd           
        //   744: ldc             3
        //   746: iadd           
        //   747: ldc             4
        //   749: iadd           
        //   750: i2f            
        //   751: iload           7
        //   753: ldc             2
        //   755: iadd           
        //   756: i2f            
        //   757: ldc             -1
        //   759: invokestatic    wwe/XybC.eTim:(Ljava/lang/String;FFI)V
        //   762: iinc            7, 12
        //   765: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //   768: ifeq            649
        //   771: aconst_null    
        //   772: athrow         
        //   773: aload_0        
        //   774: getfield        wwe/cRUT.YJKE:I
        //   777: ldc             2
        //   779: if_icmpne       1710
        //   782: ldc             100
        //   784: istore          6
        //   786: iload_2        
        //   787: aload_0        
        //   788: getfield        wwe/cRUT.tMKv:I
        //   791: ldc             12
        //   793: imul           
        //   794: iadd           
        //   795: aload_0        
        //   796: getfield        wwe/cRUT.qSwY:I
        //   799: ldc             12
        //   801: imul           
        //   802: iadd           
        //   803: istore          7
        //   805: iload_2        
        //   806: aload_0        
        //   807: getfield        wwe/cRUT.tMKv:I
        //   810: ldc             12
        //   812: imul           
        //   813: iadd           
        //   814: aload_0        
        //   815: getfield        wwe/cRUT.qSwY:I
        //   818: ldc             12
        //   820: imul           
        //   821: iadd           
        //   822: aload_0        
        //   823: aload_0        
        //   824: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //   827: checkcast       [Lwwe/AzzV;
        //   830: aload_0        
        //   831: getfield        wwe/cRUT.tMKv:I
        //   834: aaload         
        //   835: invokevirtual   wwe/cRUT.wHKN:(Lwwe/modules/Category;)Ljava/util/ArrayList;
        //   838: aload_0        
        //   839: getfield        wwe/cRUT.qSwY:I
        //   842: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //   845: checkcast       Lwwe/qMIe;
        //   848: invokevirtual   wwe/cRUT.LtmJ:(Lwwe/qMIe;)Ljava/util/ArrayList;
        //   851: invokevirtual   java/util/ArrayList.size:()I
        //   854: ldc             12
        //   856: imul           
        //   857: iadd           
        //   858: istore          8
        //   860: iload_1        
        //   861: ldc             60
        //   863: iadd           
        //   864: ldc             3
        //   866: iadd           
        //   867: ldc             83
        //   869: iadd           
        //   870: ldc             15
        //   872: iadd           
        //   873: ldc             3
        //   875: iadd           
        //   876: i2f            
        //   877: iload           7
        //   879: i2f            
        //   880: iload_1        
        //   881: ldc             60
        //   883: iadd           
        //   884: ldc             3
        //   886: iadd           
        //   887: ldc             83
        //   889: iadd           
        //   890: ldc             15
        //   892: iadd           
        //   893: iload           6
        //   895: iadd           
        //   896: i2f            
        //   897: iload           8
        //   899: i2f            
        //   900: ldc             -1342177280
        //   902: invokestatic    wwe/gnNa.wQYt:(FFFFI)V
        //   905: iload_1        
        //   906: ldc             60
        //   908: iadd           
        //   909: ldc             3
        //   911: iadd           
        //   912: ldc             83
        //   914: iadd           
        //   915: ldc             15
        //   917: iadd           
        //   918: ldc             3
        //   920: iadd           
        //   921: i2f            
        //   922: iload           7
        //   924: aload_0        
        //   925: getfield        wwe/cRUT.Jqfu:I
        //   928: ldc             12
        //   930: imul           
        //   931: iadd           
        //   932: i2f            
        //   933: iload_1        
        //   934: ldc             60
        //   936: iadd           
        //   937: ldc             3
        //   939: iadd           
        //   940: ldc             83
        //   942: iadd           
        //   943: ldc             15
        //   945: iadd           
        //   946: iload           6
        //   948: iadd           
        //   949: i2f            
        //   950: iload           7
        //   952: aload_0        
        //   953: getfield        wwe/cRUT.Jqfu:I
        //   956: ldc             12
        //   958: imul           
        //   959: iadd           
        //   960: ldc             12
        //   962: iadd           
        //   963: i2f            
        //   964: getstatic       wwe/vlwv.fomn:Lwwe/flSi;
        //   967: getfield        wwe/flSi.UPVq:Lwwe/RCMD;
        //   970: invokevirtual   wwe/RCMD.JqMr:()Ljava/awt/Color;
        //   973: invokevirtual   java/awt/Color.getRGB:()I
        //   976: invokestatic    wwe/gnNa.wQYt:(FFFFI)V
        //   979: invokestatic    org/lwjgl/opengl/GL11.glPushMatrix:()V
        //   982: dconst_1       
        //   983: aload_3        
        //   984: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaleFactor:()I
        //   987: i2d            
        //   988: ddiv           
        //   989: dconst_1       
        //   990: aload_3        
        //   991: invokevirtual   net/minecraft/client/gui/ScaledResolution.getScaleFactor:()I
        //   994: i2d            
        //   995: ddiv           
        //   996: dconst_1       
        //   997: invokestatic    org/lwjgl/opengl/GL11.glScaled:(DDD)V
        //  1000: iload_1        
        //  1001: ldc             60
        //  1003: iadd           
        //  1004: ldc             3
        //  1006: iadd           
        //  1007: ldc             83
        //  1009: iadd           
        //  1010: ldc             15
        //  1012: iadd           
        //  1013: ldc             3
        //  1015: iadd           
        //  1016: iload           4
        //  1018: imul           
        //  1019: iload           7
        //  1021: iload           4
        //  1023: imul           
        //  1024: iload_1        
        //  1025: ldc             60
        //  1027: iadd           
        //  1028: ldc             3
        //  1030: iadd           
        //  1031: ldc             83
        //  1033: iadd           
        //  1034: ldc             15
        //  1036: iadd           
        //  1037: iload           6
        //  1039: iadd           
        //  1040: iload           4
        //  1042: imul           
        //  1043: iload           8
        //  1045: iload           4
        //  1047: imul           
        //  1048: ldc             1
        //  1050: getstatic       wwe/vlwv.fomn:Lwwe/flSi;
        //  1053: getfield        wwe/flSi.qgqp:Lwwe/RCMD;
        //  1056: invokevirtual   wwe/RCMD.JqMr:()Ljava/awt/Color;
        //  1059: invokevirtual   java/awt/Color.getRGB:()I
        //  1062: invokestatic    wwe/gnNa.AvjN:(IIIIII)V
        //  1065: invokestatic    org/lwjgl/opengl/GL11.glPopMatrix:()V
        //  1068: aload_0        
        //  1069: getfield        wwe/cRUT.KIMB:Z
        //  1072: ifeq            1158
        //  1075: iload_1        
        //  1076: ldc             60
        //  1078: iadd           
        //  1079: ldc             3
        //  1081: iadd           
        //  1082: ldc             83
        //  1084: iadd           
        //  1085: ldc             15
        //  1087: iadd           
        //  1088: iload           6
        //  1090: iadd           
        //  1091: ldc             5
        //  1093: iadd           
        //  1094: i2f            
        //  1095: iload           7
        //  1097: aload_0        
        //  1098: getfield        wwe/cRUT.Jqfu:I
        //  1101: ldc             12
        //  1103: imul           
        //  1104: iadd           
        //  1105: ldc             1
        //  1107: iadd           
        //  1108: i2f            
        //  1109: iload_1        
        //  1110: ldc             60
        //  1112: iadd           
        //  1113: ldc             3
        //  1115: iadd           
        //  1116: ldc             83
        //  1118: iadd           
        //  1119: ldc             15
        //  1121: iadd           
        //  1122: iload           6
        //  1124: iadd           
        //  1125: ldc             7
        //  1127: iadd           
        //  1128: i2f            
        //  1129: iload           7
        //  1131: aload_0        
        //  1132: getfield        wwe/cRUT.Jqfu:I
        //  1135: ldc             12
        //  1137: imul           
        //  1138: iadd           
        //  1139: ldc             12
        //  1141: iadd           
        //  1142: i2f            
        //  1143: getstatic       wwe/vlwv.fomn:Lwwe/flSi;
        //  1146: getfield        wwe/flSi.UPVq:Lwwe/RCMD;
        //  1149: invokevirtual   wwe/RCMD.JqMr:()Ljava/awt/Color;
        //  1152: invokevirtual   java/awt/Color.getRGB:()I
        //  1155: invokestatic    wwe/gnNa.wQYt:(FFFFI)V
        //  1158: aload_0        
        //  1159: getfield        wwe/cRUT.KIMB:Z
        //  1162: ifeq            1497
        //  1165: aload_0        
        //  1166: aload_0        
        //  1167: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //  1170: checkcast       [Lwwe/AzzV;
        //  1173: aload_0        
        //  1174: getfield        wwe/cRUT.tMKv:I
        //  1177: aaload         
        //  1178: invokevirtual   wwe/cRUT.wHKN:(Lwwe/modules/Category;)Ljava/util/ArrayList;
        //  1181: aload_0        
        //  1182: getfield        wwe/cRUT.qSwY:I
        //  1185: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1188: checkcast       Lwwe/qMIe;
        //  1191: invokevirtual   wwe/cRUT.LtmJ:(Lwwe/qMIe;)Ljava/util/ArrayList;
        //  1194: aload_0        
        //  1195: getfield        wwe/cRUT.Jqfu:I
        //  1198: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1201: checkcast       Lwwe/IcRI;
        //  1204: invokevirtual   wwe/IcRI.MATs:()Z
        //  1207: ifeq            1497
        //  1210: iload_1        
        //  1211: ldc             60
        //  1213: iadd           
        //  1214: ldc             3
        //  1216: iadd           
        //  1217: ldc             83
        //  1219: iadd           
        //  1220: ldc             15
        //  1222: iadd           
        //  1223: iload           6
        //  1225: iadd           
        //  1226: ldc             11
        //  1228: iadd           
        //  1229: i2f            
        //  1230: iload           7
        //  1232: aload_0        
        //  1233: getfield        wwe/cRUT.Jqfu:I
        //  1236: ldc             12
        //  1238: imul           
        //  1239: iadd           
        //  1240: ldc             1
        //  1242: iadd           
        //  1243: i2f            
        //  1244: iload_1        
        //  1245: ldc             60
        //  1247: iadd           
        //  1248: ldc             3
        //  1250: iadd           
        //  1251: ldc             83
        //  1253: iadd           
        //  1254: ldc             15
        //  1256: iadd           
        //  1257: iload           6
        //  1259: iadd           
        //  1260: ldc             13
        //  1262: iadd           
        //  1263: i2f            
        //  1264: iload           7
        //  1266: aload_0        
        //  1267: getfield        wwe/cRUT.Jqfu:I
        //  1270: ldc             12
        //  1272: imul           
        //  1273: iadd           
        //  1274: ldc             12
        //  1276: iadd           
        //  1277: i2f            
        //  1278: aload_0        
        //  1279: aload_0        
        //  1280: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //  1283: checkcast       [Lwwe/AzzV;
        //  1286: aload_0        
        //  1287: getfield        wwe/cRUT.tMKv:I
        //  1290: aaload         
        //  1291: invokevirtual   wwe/cRUT.wHKN:(Lwwe/modules/Category;)Ljava/util/ArrayList;
        //  1294: aload_0        
        //  1295: getfield        wwe/cRUT.qSwY:I
        //  1298: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1301: checkcast       Lwwe/qMIe;
        //  1304: invokevirtual   wwe/cRUT.LtmJ:(Lwwe/qMIe;)Ljava/util/ArrayList;
        //  1307: aload_0        
        //  1308: getfield        wwe/cRUT.Jqfu:I
        //  1311: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1314: checkcast       Lwwe/utils/NumberValue;
        //  1317: invokevirtual   wwe/utils/NumberValue.OnGi:()Ljava/lang/Object;
        //  1320: checkcast       Ljava/lang/Double;
        //  1323: invokevirtual   java/lang/Double.doubleValue:()D
        //  1326: aload_0        
        //  1327: aload_0        
        //  1328: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //  1331: checkcast       [Lwwe/AzzV;
        //  1334: aload_0        
        //  1335: getfield        wwe/cRUT.tMKv:I
        //  1338: aaload         
        //  1339: invokevirtual   wwe/cRUT.wHKN:(Lwwe/modules/Category;)Ljava/util/ArrayList;
        //  1342: aload_0        
        //  1343: getfield        wwe/cRUT.qSwY:I
        //  1346: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1349: checkcast       Lwwe/qMIe;
        //  1352: invokevirtual   wwe/cRUT.LtmJ:(Lwwe/qMIe;)Ljava/util/ArrayList;
        //  1355: aload_0        
        //  1356: getfield        wwe/cRUT.Jqfu:I
        //  1359: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1362: checkcast       Lwwe/utils/NumberValue;
        //  1365: getfield        wwe/utils/NumberValue.aYiG:D
        //  1368: ldc2_w          0.09
        //  1371: dadd           
        //  1372: dcmpg          
        //  1373: ifgt            1488
        //  1376: aload_0        
        //  1377: aload_0        
        //  1378: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //  1381: checkcast       [Lwwe/AzzV;
        //  1384: aload_0        
        //  1385: getfield        wwe/cRUT.tMKv:I
        //  1388: aaload         
        //  1389: invokevirtual   wwe/cRUT.wHKN:(Lwwe/modules/Category;)Ljava/util/ArrayList;
        //  1392: aload_0        
        //  1393: getfield        wwe/cRUT.qSwY:I
        //  1396: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1399: checkcast       Lwwe/qMIe;
        //  1402: invokevirtual   wwe/cRUT.LtmJ:(Lwwe/qMIe;)Ljava/util/ArrayList;
        //  1405: aload_0        
        //  1406: getfield        wwe/cRUT.Jqfu:I
        //  1409: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1412: checkcast       Lwwe/utils/NumberValue;
        //  1415: invokevirtual   wwe/utils/NumberValue.OnGi:()Ljava/lang/Object;
        //  1418: checkcast       Ljava/lang/Double;
        //  1421: invokevirtual   java/lang/Double.doubleValue:()D
        //  1424: aload_0        
        //  1425: aload_0        
        //  1426: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //  1429: checkcast       [Lwwe/AzzV;
        //  1432: aload_0        
        //  1433: getfield        wwe/cRUT.tMKv:I
        //  1436: aaload         
        //  1437: invokevirtual   wwe/cRUT.wHKN:(Lwwe/modules/Category;)Ljava/util/ArrayList;
        //  1440: aload_0        
        //  1441: getfield        wwe/cRUT.qSwY:I
        //  1444: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1447: checkcast       Lwwe/qMIe;
        //  1450: invokevirtual   wwe/cRUT.LtmJ:(Lwwe/qMIe;)Ljava/util/ArrayList;
        //  1453: aload_0        
        //  1454: getfield        wwe/cRUT.Jqfu:I
        //  1457: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1460: checkcast       Lwwe/utils/NumberValue;
        //  1463: getfield        wwe/utils/NumberValue.LxxX:D
        //  1466: ldc2_w          0.09
        //  1469: dsub           
        //  1470: dcmpl          
        //  1471: iflt            1488
        //  1474: getstatic       java/awt/Color.GREEN:Ljava/awt/Color;
        //  1477: invokevirtual   java/awt/Color.getRGB:()I
        //  1480: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //  1483: ifeq            1494
        //  1486: aconst_null    
        //  1487: athrow         
        //  1488: getstatic       java/awt/Color.red:Ljava/awt/Color;
        //  1491: invokevirtual   java/awt/Color.getRGB:()I
        //  1494: invokestatic    wwe/gnNa.wQYt:(FFFFI)V
        //  1497: iload           7
        //  1499: ldc             2
        //  1501: iadd           
        //  1502: istore          9
        //  1504: aload_0        
        //  1505: aload_0        
        //  1506: invokestatic    wwe/modules/Category.fgCR:()[Lwwe/modules/Category;
        //  1509: checkcast       [Lwwe/AzzV;
        //  1512: aload_0        
        //  1513: getfield        wwe/cRUT.tMKv:I
        //  1516: aaload         
        //  1517: invokevirtual   wwe/cRUT.wHKN:(Lwwe/modules/Category;)Ljava/util/ArrayList;
        //  1520: aload_0        
        //  1521: getfield        wwe/cRUT.qSwY:I
        //  1524: invokevirtual   java/util/ArrayList.get:(I)Ljava/lang/Object;
        //  1527: checkcast       Lwwe/qMIe;
        //  1530: invokevirtual   wwe/cRUT.LtmJ:(Lwwe/qMIe;)Ljava/util/ArrayList;
        //  1533: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //  1536: astore          10
        //  1538: aload           10
        //  1540: invokevirtual   java/util/Iterator.hasNext:()Z
        //  1543: ifeq            1710
        //  1546: aload           10
        //  1548: invokevirtual   java/util/Iterator.next:()Ljava/lang/Object;
        //  1551: checkcast       Lwwe/IcRI;
        //  1554: astore          11
        //  1556: new             Ljava/lang/StringBuilder;
        //  1559: dup            
        //  1560: invokespecial   java/lang/StringBuilder.<init>:()V
        //  1563: aload           11
        //  1565: getfield        wwe/IcRI.VQBN:Ljava/lang/String;
        //  1568: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1571: ldc_w           " : "
        //  1574: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //  1577: aload           11
        //  1579: invokevirtual   wwe/IcRI.wnpo:()Z
        //  1582: ifeq            1604
        //  1585: aload           11
        //  1587: checkcast       Lwwe/utils/BooleanValue;
        //  1590: invokevirtual   wwe/utils/BooleanValue.OnGi:()Ljava/lang/Object;
        //  1593: checkcast       Ljava/io/Serializable;
        //  1596: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //  1599: ifeq            1665
        //  1602: aconst_null    
        //  1603: athrow         
        //  1604: aload           11
        //  1606: invokevirtual   wwe/IcRI.dTln:()Z
        //  1609: ifeq            1631
        //  1612: aload           11
        //  1614: checkcast       Lwwe/utils/ModeValue;
        //  1617: invokevirtual   wwe/utils/ModeValue.OnGi:()Ljava/lang/Object;
        //  1620: checkcast       Ljava/lang/String;
        //  1623: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //  1626: ifeq            1665
        //  1629: aconst_null    
        //  1630: athrow         
        //  1631: aload           11
        //  1633: invokevirtual   wwe/IcRI.MATs:()Z
        //  1636: ifeq            1662
        //  1639: aload_0        
        //  1640: getfield        wwe/cRUT.ttQQ:Ljava/text/DecimalFormat;
        //  1643: aload           11
        //  1645: checkcast       Lwwe/utils/NumberValue;
        //  1648: invokevirtual   wwe/utils/NumberValue.OnGi:()Ljava/lang/Object;
        //  1651: invokevirtual   java/text/DecimalFormat.format:(Ljava/lang/Object;)Ljava/lang/String;
        //  1654: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //  1657: ifeq            1665
        //  1660: aconst_null    
        //  1661: athrow         
        //  1662: ldc_w           ""
        //  1665: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //  1668: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //  1671: iload_1        
        //  1672: ldc             60
        //  1674: iadd           
        //  1675: ldc             3
        //  1677: iadd           
        //  1678: ldc             83
        //  1680: iadd           
        //  1681: ldc             15
        //  1683: iadd           
        //  1684: ldc             3
        //  1686: iadd           
        //  1687: ldc             4
        //  1689: iadd           
        //  1690: i2f            
        //  1691: iload           9
        //  1693: i2f            
        //  1694: ldc             -1
        //  1696: invokestatic    wwe/XybC.eTim:(Ljava/lang/String;FFI)V
        //  1699: iinc            9, 12
        //  1702: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //  1705: ifeq            1538
        //  1708: aconst_null    
        //  1709: athrow         
        //  1710: return         
        //    StackMapTable: 00 19 FF 00 03 00 0D 07 00 02 01 01 00 00 00 00 00 00 00 00 00 01 00 00 00 FF 00 BC 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 3B 01 01 00 00 00 01 00 00 FF 00 30 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 3B 01 01 07 00 86 00 00 01 00 00 FF 00 0A 00 00 00 01 07 00 15 FF 00 04 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 3B 01 01 00 00 00 01 00 00 11 FF 00 13 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 8D 07 00 9E 00 00 00 00 01 00 00 FF 00 34 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 8D 07 00 9E 07 00 A8 00 00 00 01 00 01 07 00 A8 FF 00 08 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 8D 07 00 9E 07 00 A8 00 00 00 01 00 02 07 00 A8 07 00 86 0D FF 00 07 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 8D 07 00 9E 00 00 00 00 01 00 00 FF 01 0C 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 8D 01 07 00 9E 00 00 00 01 00 00 FF 00 55 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 8D 01 07 00 9E 07 00 A8 00 00 01 00 00 FF 00 25 00 0D 07 00 02 01 01 07 00 24 01 01 07 00 04 01 00 00 00 00 01 00 00 FF 01 80 00 0D 07 00 02 01 01 07 00 24 01 01 01 01 01 00 00 00 01 00 00 FF 01 49 00 0D 07 00 02 01 01 07 00 24 01 01 01 01 01 00 00 00 01 00 04 02 02 02 02 FF 00 05 00 0D 07 00 02 01 01 07 00 24 01 01 01 01 01 00 00 00 01 00 05 02 02 02 02 01 02 FF 00 28 00 0D 07 00 02 01 01 07 00 24 01 01 01 01 01 01 07 00 9E 00 01 00 00 FF 00 41 00 0D 07 00 02 01 01 07 00 24 01 01 01 01 01 01 07 00 9E 07 00 E3 01 00 01 07 01 05 5A 07 01 05 5E 07 01 05 FF 00 02 00 0D 07 00 02 01 01 07 00 24 01 01 01 01 01 01 07 00 9E 07 00 E3 01 00 02 07 01 05 07 01 18 FF 00 2C 00 0D 07 00 02 01 01 07 00 24 01 01 00 01 00 00 00 00 01 00 00
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public void MSFO(final int n) {
        if (this.YJKE == 0) {
            if (n == 200) {
                --this.tMKv;
                if (this.tMKv < 0) {
                    this.tMKv = 5;
                }
            }
            if (n == 208) {
                ++this.tMKv;
                if (this.tMKv > 5) {
                    this.tMKv = 0;
                }
            }
            if (n == 205) {
                this.YJKE = 1;
                this.qSwY = 0;
                if (cRUT.\u2000\u200d\u200c) {
                    throw null;
                }
            }
        }
        else if (this.YJKE == 1) {
            if (n == 200) {
                --this.qSwY;
                if (this.qSwY < 0) {
                    this.qSwY = this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).size() - 1;
                }
            }
            if (n == 208) {
                ++this.qSwY;
                if (this.qSwY > this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).size() - 1) {
                    this.qSwY = 0;
                }
            }
            if (n == 205 && this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).size() > 0) {
                this.YJKE = 2;
            }
            if (n == 203) {
                this.YJKE = 0;
            }
            if (n == 28) {
                this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY).GDVp();
                if (cRUT.\u2000\u200d\u200c) {
                    throw null;
                }
            }
        }
        else if (this.YJKE == 2) {
            if (n == 200) {
                if (this.KIMB) {
                    if (this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).wnpo()) {
                        final BooleanValue booleanValue = this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu);
                        boolean b;
                        if (!this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).OnGi()) {
                            b = true;
                            if (cRUT.\u2000\u200d\u200c) {
                                throw null;
                            }
                        }
                        else {
                            b = false;
                        }
                        booleanValue.NdCc(b);
                    }
                    if (this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).dTln()) {
                        this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).NdCc(this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).chKb.get(this.AedC(false)));
                    }
                    if (this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).MATs()) {
                        this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).NdCc(this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).OnGi() + 0.1);
                    }
                    return;
                }
                --this.Jqfu;
                if (this.Jqfu < 0) {
                    this.Jqfu = this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).size() - 1;
                }
            }
            if (n == 208) {
                if (this.KIMB) {
                    if (this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).wnpo()) {
                        final BooleanValue booleanValue2 = this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu);
                        boolean b2;
                        if (!this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).OnGi()) {
                            b2 = true;
                            if (cRUT.\u2000\u200d\u200c) {
                                throw null;
                            }
                        }
                        else {
                            b2 = false;
                        }
                        booleanValue2.NdCc(b2);
                    }
                    if (this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).dTln()) {
                        this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).NdCc(this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).chKb.get(this.AedC(true)));
                    }
                    if (this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).MATs()) {
                        this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).NdCc(this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).OnGi() - 0.1);
                    }
                    return;
                }
                ++this.Jqfu;
                if (this.Jqfu > this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).size() - 1) {
                    this.Jqfu = 0;
                }
            }
            if (n == 205) {
                this.KIMB = true;
            }
            if (n == 203) {
                if (this.KIMB) {
                    this.KIMB = false;
                    return;
                }
                this.YJKE = 1;
                this.Jqfu = 0;
            }
        }
    }
    
    public ArrayList<qMIe> wHKN(final AzzV azzV) {
        class mQzr implements Comparator<qMIe>
        {
            final cRUT jXTN;
            
            mQzr(final cRUT jxtn) {
                this.jXTN = jxtn;
                super();
            }
            
            public int jfbG(final qMIe qmIe, final qMIe qmIe2) {
                return qmIe.czUH().compareTo(qmIe2.czUH());
            }
            
            @Override
            public int compare(final Object o, final Object o2) {
                return this.jfbG((qMIe)o, (qMIe)o2);
            }
        }
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aconst_null    
        //     4: areturn        
        //     5: new             Ljava/util/ArrayList;
        //     8: dup            
        //     9: invokespecial   java/util/ArrayList.<init>:()V
        //    12: astore_2       
        //    13: getstatic       wwe/vlwv.hNxW:Lwwe/eOnJ;
        //    16: invokevirtual   wwe/eOnJ.xRen:()Ljava/util/ArrayList;
        //    19: invokevirtual   java/util/ArrayList.iterator:()Ljava/util/Iterator;
        //    22: astore_3       
        //    23: aload_3        
        //    24: invokevirtual   java/util/Iterator.hasNext:()Z
        //    27: ifeq            68
        //    30: aload_3        
        //    31: invokevirtual   java/util/Iterator.next:()Ljava/lang/Object;
        //    34: checkcast       Lwwe/qMIe;
        //    37: astore          4
        //    39: aload           4
        //    41: aload_1        
        //    42: invokevirtual   wwe/qMIe.jrFS:(Lwwe/modules/Category;)Z
        //    45: ifeq            55
        //    48: aload_2        
        //    49: aload           4
        //    51: invokevirtual   java/util/ArrayList.add:(Ljava/lang/Object;)Z
        //    54: pop            
        //    55: getstatic       wwe/cRUT.\u2000\u200d\u200c:Z
        //    58: ifeq            23
        //    61: aconst_null    
        //    62: athrow         
        //    63: nop            
        //    64: nop            
        //    65: nop            
        //    66: nop            
        //    67: athrow         
        //    68: aload_2        
        //    69: new             Lwwe/mQzr;
        //    72: dup            
        //    73: aload_0        
        //    74: invokespecial   wwe/mQzr.<init>:(Lwwe/cRUT;)V
        //    77: invokevirtual   java/util/ArrayList.sort:(Ljava/util/Comparator;)V
        //    80: aload_2        
        //    81: areturn        
        //    82: nop            
        //    83: nop            
        //    84: nop            
        //    85: nop            
        //    86: athrow         
        //    Signature:
        //  (Lwwe/AzzV;)Ljava/util/ArrayList<Lwwe/qMIe;>;
        //    StackMapTable: 00 07 FF 00 03 00 06 07 00 02 07 00 86 00 00 00 01 00 00 01 FF 00 11 00 06 07 00 02 07 00 86 07 00 8D 07 00 9E 00 01 00 00 FF 00 1F 00 06 07 00 02 07 00 86 07 00 8D 07 00 9E 07 00 A8 01 00 00 FF 00 07 00 00 00 01 07 00 15 FF 00 04 00 06 07 00 02 07 00 86 07 00 8D 07 00 9E 00 01 00 00 FF 00 0D 00 00 00 01 07 00 15
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public ArrayList<IcRI> LtmJ(final qMIe qmIe) {
        final ArrayList<IcRI> list = new ArrayList<IcRI>();
        for (final IcRI icRI : vlwv.tiEw) {
            if (icRI.lJZv.equals(qmIe)) {
                list.add(icRI);
            }
            if (cRUT.\u2000\u200d\u200c) {
                throw null;
            }
        }
        return (ArrayList<IcRI>)list;
    }
    
    public int AedC(final boolean b) {
        if (b) {
            int i = 0;
            while (i < this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).chKb.size()) {
                if (this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).chKb.get(i).equals(((IcRI<Object>)this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu)).OnGi())) {
                    if (i + 1 >= this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).chKb.size()) {
                        return 0;
                    }
                    return i + 1;
                }
                else {
                    ++i;
                    if (cRUT.\u2000\u200d\u200c) {
                        throw null;
                    }
                    continue;
                }
            }
            if (cRUT.\u2000\u200d\u200c) {
                throw null;
            }
        }
        else {
            int j = 0;
            while (j < this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).chKb.size()) {
                if (this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).chKb.get(j).equals(((IcRI<Object>)this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu)).OnGi())) {
                    if (j - 1 < 0) {
                        return this.LtmJ(this.wHKN((Category)((AzzV[])Category.fgCR())[this.tMKv]).get(this.qSwY)).get(this.Jqfu).chKb.size() - 1;
                    }
                    return j - 1;
                }
                else {
                    ++j;
                    if (cRUT.\u2000\u200d\u200c) {
                        throw null;
                    }
                    continue;
                }
            }
        }
        return 0;
    }
}
