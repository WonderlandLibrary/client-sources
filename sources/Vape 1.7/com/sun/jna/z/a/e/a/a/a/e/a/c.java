package com.sun.jna.z.a.e.a.a.a.e.a;

import com.sun.jna.z.a.e.a.a.a.e.*;
import java.awt.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

public class c extends b<j>
{
    private final i e;
    
    c(final i a) {
        super(j.class);
        this.e = a;
        this.b = Color.WHITE;
        this.c = new Color(128, 128, 128, 192);
    }
    
    protected void a(final j a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: aload_1         /* a */
        //     2: iconst_0       
        //     3: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/c.a:(Lcom/sun/jna/z/a/e/a/a/a/a/k;Z)V
        //     6: aload_1         /* a */
        //     7: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.j:()Ljava/awt/Rectangle;
        //    12: astore          4
        //    14: getstatic       com/sun/jna/z/a/e/a/a/a/e/a/i.f:Z
        //    17: sipush          3042
        //    20: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //    23: istore_2        /* a */
        //    24: sipush          2884
        //    27: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //    30: sipush          3553
        //    33: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //    36: iconst_0       
        //    37: istore          a
        //    39: aload_1         /* a */
        //    40: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()[Ljava/lang/String;
        //    45: astore          6
        //    47: aload           6
        //    49: arraylength    
        //    50: istore          7
        //    52: iconst_0       
        //    53: istore          8
        //    55: iload           8
        //    57: iload           7
        //    59: if_icmpge       110
        //    62: aload           6
        //    64: iload           8
        //    66: aaload         
        //    67: astore          a
        //    69: iload           a
        //    71: aload_0         /* a */
        //    72: getfield        com/sun/jna/z/a/e/a/a/a/e/a/c.e:Lcom/sun/jna/z/a/e/a/a/a/e/a/i;
        //    75: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/i.a:()Lcom/sun/jna/z/a/e/a/a/a/b/a;
        //    78: aload           a
        //    80: invokevirtual   com/sun/jna/z/a/e/a/a/a/b/a.func_78256_a:(Ljava/lang/String;)I
        //    83: invokestatic    java/lang/Math.max:(II)I
        //    86: iload_2         /* a */
        //    87: ifne            111
        //    90: istore          a
        //    92: iinc            8, 1
        //    95: iload_2         /* a */
        //    96: ifeq            55
        //    99: getstatic       com/sun/jna/z/a/i.g:I
        //   102: istore_3        /* a */
        //   103: iinc            a, 1
        //   106: iload_3         /* a */
        //   107: putstatic       com/sun/jna/z/a/i.g:I
        //   110: iconst_0       
        //   111: istore          a
        //   113: aload_1         /* a */
        //   114: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()Z
        //   119: iload_2         /* a */
        //   120: ifne            212
        //   123: ifeq            178
        //   126: aload_1         /* a */
        //   127: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()[Ljava/lang/String;
        //   132: astore          a
        //   134: iconst_0       
        //   135: istore          a
        //   137: iload           a
        //   139: aload           a
        //   141: arraylength    
        //   142: iconst_1       
        //   143: isub           
        //   144: if_icmpge       175
        //   147: iload           a
        //   149: aload_0         /* a */
        //   150: getfield        com/sun/jna/z/a/e/a/a/a/e/a/c.e:Lcom/sun/jna/z/a/e/a/a/a/e/a/i;
        //   153: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/i.a:()Lcom/sun/jna/z/a/e/a/a/a/b/a;
        //   156: getfield        com/sun/jna/z/a/e/a/a/a/b/a.field_78288_b:I
        //   159: iconst_2       
        //   160: iadd           
        //   161: iadd           
        //   162: istore          a
        //   164: iinc            a, 1
        //   167: iload_2         /* a */
        //   168: ifne            241
        //   171: iload_2         /* a */
        //   172: ifeq            137
        //   175: iinc            a, 2
        //   178: aload_1         /* a */
        //   179: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.l:()Ljava/awt/Color;
        //   184: invokestatic    com/sun/jna/z/a/e/a/a/a/f/a.a:(Ljava/awt/Color;)V
        //   187: bipush          7
        //   189: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   192: dconst_0       
        //   193: dconst_0       
        //   194: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   197: aload           a
        //   199: getfield        java/awt/Rectangle.width:I
        //   202: i2d            
        //   203: dconst_0       
        //   204: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   207: aload           a
        //   209: getfield        java/awt/Rectangle.width:I
        //   212: i2d            
        //   213: aload           a
        //   215: getfield        java/awt/Rectangle.height:I
        //   218: iload           a
        //   220: iadd           
        //   221: i2d            
        //   222: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   225: dconst_0       
        //   226: aload           a
        //   228: getfield        java/awt/Rectangle.height:I
        //   231: iload           a
        //   233: iadd           
        //   234: i2d            
        //   235: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   238: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //   241: invokestatic    com/sun/jna/z/a/e/a/a/a/f/a.b:()Ljava/awt/Point;
        //   244: astore          a
        //   246: aload_1         /* a */
        //   247: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.k:()Lcom/sun/jna/z/a/e/a/a/a/a/l;
        //   252: astore          a
        //   254: aload           a
        //   256: ifnull          309
        //   259: aload           a
        //   261: aload           a
        //   263: invokevirtual   java/awt/geom/Point2D.getX:()D
        //   266: aload           a
        //   268: invokeinterface com/sun/jna/z/a/e/a/a/a/a/k.d:()I
        //   273: i2d            
        //   274: dsub           
        //   275: aload           a
        //   277: invokevirtual   java/awt/geom/Point2D.getY:()D
        //   280: aload           a
        //   282: invokeinterface com/sun/jna/z/a/e/a/a/a/a/k.e:()I
        //   287: i2d            
        //   288: dsub           
        //   289: invokevirtual   java/awt/geom/Point2D.setLocation:(DD)V
        //   292: aload           a
        //   294: invokeinterface com/sun/jna/z/a/e/a/a/a/a/k.k:()Lcom/sun/jna/z/a/e/a/a/a/a/l;
        //   299: astore          a
        //   301: iload_2         /* a */
        //   302: ifne            329
        //   305: iload_2         /* a */
        //   306: ifeq            254
        //   309: fconst_0       
        //   310: fconst_0       
        //   311: fconst_0       
        //   312: iconst_0       
        //   313: invokestatic    org/lwjgl/input/Mouse.isButtonDown:(I)Z
        //   316: ifeq            324
        //   319: ldc             0.5
        //   321: goto            326
        //   324: ldc             0.3
        //   326: invokestatic    org/lwjgl/opengl/GL11.glColor4f:(FFFF)V
        //   329: aload           a
        //   331: aload           a
        //   333: invokevirtual   java/awt/Rectangle.contains:(Ljava/awt/geom/Point2D;)Z
        //   336: iload_2         /* a */
        //   337: ifne            401
        //   340: ifeq            395
        //   343: bipush          7
        //   345: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   348: dconst_0       
        //   349: dconst_0       
        //   350: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   353: aload           a
        //   355: getfield        java/awt/Rectangle.width:I
        //   358: i2d            
        //   359: dconst_0       
        //   360: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   363: aload           a
        //   365: getfield        java/awt/Rectangle.width:I
        //   368: i2d            
        //   369: aload           a
        //   371: getfield        java/awt/Rectangle.height:I
        //   374: i2d            
        //   375: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   378: dconst_0       
        //   379: aload           a
        //   381: getfield        java/awt/Rectangle.height:I
        //   384: i2d            
        //   385: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   388: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //   391: iload_2         /* a */
        //   392: ifeq            720
        //   395: aload_1         /* a */
        //   396: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()Z
        //   401: iload_2         /* a */
        //   402: ifne            732
        //   405: ifeq            720
        //   408: aload           a
        //   410: invokevirtual   java/awt/geom/Point2D.getX:()D
        //   413: aload           a
        //   415: getfield        java/awt/Rectangle.x:I
        //   418: i2d            
        //   419: dcmpl          
        //   420: iload_2         /* a */
        //   421: ifne            732
        //   424: iflt            720
        //   427: aload           a
        //   429: invokevirtual   java/awt/geom/Point2D.getX:()D
        //   432: aload           a
        //   434: getfield        java/awt/Rectangle.x:I
        //   437: aload           a
        //   439: getfield        java/awt/Rectangle.width:I
        //   442: iadd           
        //   443: i2d            
        //   444: dcmpg          
        //   445: iload_2         /* a */
        //   446: ifne            732
        //   449: ifgt            720
        //   452: aload_1         /* a */
        //   453: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.g:()I
        //   458: istore          a
        //   460: aload_1         /* a */
        //   461: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()[Ljava/lang/String;
        //   466: astore          a
        //   468: iconst_0       
        //   469: istore          a
        //   471: iload           a
        //   473: aload           a
        //   475: arraylength    
        //   476: if_icmpge       720
        //   479: iload           a
        //   481: aload_1         /* a */
        //   482: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.b:()I
        //   487: iload_2         /* a */
        //   488: ifne            731
        //   491: iload_2         /* a */
        //   492: ifne            513
        //   495: if_icmpne       502
        //   498: iload_2         /* a */
        //   499: ifeq            713
        //   502: aload_0         /* a */
        //   503: getfield        com/sun/jna/z/a/e/a/a/a/e/a/c.e:Lcom/sun/jna/z/a/e/a/a/a/e/a/i;
        //   506: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/i.a:()Lcom/sun/jna/z/a/e/a/a/a/b/a;
        //   509: getfield        com/sun/jna/z/a/e/a/a/a/b/a.field_78288_b:I
        //   512: iconst_2       
        //   513: iadd           
        //   514: istore          a
        //   516: aload_1         /* a */
        //   517: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.b:()I
        //   522: iload_2         /* a */
        //   523: ifne            544
        //   526: ifne            542
        //   529: iload           a
        //   531: iconst_1       
        //   532: iload_2         /* a */
        //   533: ifne            562
        //   536: if_icmpne       551
        //   539: goto            600
        //   542: iload           a
        //   544: iload_2         /* a */
        //   545: ifne            557
        //   548: ifeq            600
        //   551: aload_1         /* a */
        //   552: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.b:()I
        //   557: aload           a
        //   559: arraylength    
        //   560: iconst_1       
        //   561: isub           
        //   562: iload_2         /* a */
        //   563: ifne            597
        //   566: if_icmpne       586
        //   569: iload           a
        //   571: iload_2         /* a */
        //   572: ifne            618
        //   575: aload           a
        //   577: arraylength    
        //   578: iconst_2       
        //   579: isub           
        //   580: if_icmpne       603
        //   583: goto            600
        //   586: iload           a
        //   588: iload_2         /* a */
        //   589: ifne            618
        //   592: aload           a
        //   594: arraylength    
        //   595: iconst_1       
        //   596: isub           
        //   597: if_icmpne       603
        //   600: iinc            a, 1
        //   603: aload           a
        //   605: invokevirtual   java/awt/geom/Point2D.getY:()D
        //   608: aload           a
        //   610: getfield        java/awt/Rectangle.y:I
        //   613: iload           a
        //   615: iadd           
        //   616: i2d            
        //   617: dcmpl          
        //   618: iload_2         /* a */
        //   619: ifne            711
        //   622: iflt            706
        //   625: aload           a
        //   627: invokevirtual   java/awt/geom/Point2D.getY:()D
        //   630: aload           a
        //   632: getfield        java/awt/Rectangle.y:I
        //   635: iload           a
        //   637: iadd           
        //   638: iload           a
        //   640: iadd           
        //   641: i2d            
        //   642: dcmpg          
        //   643: iload_2         /* a */
        //   644: ifne            711
        //   647: ifgt            706
        //   650: bipush          7
        //   652: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   655: dconst_0       
        //   656: iload           a
        //   658: i2d            
        //   659: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   662: dconst_0       
        //   663: iload           a
        //   665: iload           a
        //   667: iadd           
        //   668: i2d            
        //   669: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   672: aload           a
        //   674: getfield        java/awt/Rectangle.width:I
        //   677: i2d            
        //   678: iload           a
        //   680: iload           a
        //   682: iadd           
        //   683: i2d            
        //   684: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   687: aload           a
        //   689: getfield        java/awt/Rectangle.width:I
        //   692: i2d            
        //   693: iload           a
        //   695: i2d            
        //   696: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   699: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //   702: iload_2         /* a */
        //   703: ifeq            720
        //   706: iload           a
        //   708: iload           a
        //   710: iadd           
        //   711: istore          a
        //   713: iinc            a, 1
        //   716: iload_2         /* a */
        //   717: ifeq            471
        //   720: aload_0         /* a */
        //   721: getfield        com/sun/jna/z/a/e/a/a/a/e/a/c.e:Lcom/sun/jna/z/a/e/a/a/a/e/a/i;
        //   724: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/i.a:()Lcom/sun/jna/z/a/e/a/a/a/b/a;
        //   727: getfield        com/sun/jna/z/a/e/a/a/a/b/a.field_78288_b:I
        //   730: iconst_4       
        //   731: iadd           
        //   732: istore          a
        //   734: fconst_0       
        //   735: fconst_0       
        //   736: fconst_0       
        //   737: ldc             0.3
        //   739: invokestatic    org/lwjgl/opengl/GL11.glColor4f:(FFFF)V
        //   742: iconst_4       
        //   743: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   746: aload_1         /* a */
        //   747: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()Z
        //   752: iload_2         /* a */
        //   753: ifne            898
        //   756: ifeq            844
        //   759: iload           a
        //   761: iconst_4       
        //   762: iadd           
        //   763: i2d            
        //   764: iload           a
        //   766: i2d            
        //   767: ldc2_w          2.0
        //   770: ddiv           
        //   771: dadd           
        //   772: iload           a
        //   774: i2d            
        //   775: ldc2_w          3.0
        //   778: ddiv           
        //   779: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   782: iload           a
        //   784: iconst_4       
        //   785: iadd           
        //   786: i2d            
        //   787: iload           a
        //   789: i2d            
        //   790: ldc2_w          3.0
        //   793: ddiv           
        //   794: dadd           
        //   795: ldc2_w          2.0
        //   798: iload           a
        //   800: i2d            
        //   801: dmul           
        //   802: ldc2_w          3.0
        //   805: ddiv           
        //   806: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   809: iload           a
        //   811: iconst_4       
        //   812: iadd           
        //   813: i2d            
        //   814: ldc2_w          2.0
        //   817: iload           a
        //   819: i2d            
        //   820: dmul           
        //   821: ldc2_w          3.0
        //   824: ddiv           
        //   825: dadd           
        //   826: ldc2_w          2.0
        //   829: iload           a
        //   831: i2d            
        //   832: dmul           
        //   833: ldc2_w          3.0
        //   836: ddiv           
        //   837: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   840: iload_2         /* a */
        //   841: ifeq            921
        //   844: iload           a
        //   846: iconst_4       
        //   847: iadd           
        //   848: i2d            
        //   849: iload           a
        //   851: i2d            
        //   852: ldc2_w          3.0
        //   855: ddiv           
        //   856: dadd           
        //   857: iload           a
        //   859: i2d            
        //   860: ldc2_w          3.0
        //   863: ddiv           
        //   864: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   867: iload           a
        //   869: iconst_4       
        //   870: iadd           
        //   871: i2d            
        //   872: ldc2_w          2.0
        //   875: iload           a
        //   877: i2d            
        //   878: dmul           
        //   879: ldc2_w          3.0
        //   882: ddiv           
        //   883: dadd           
        //   884: iload           a
        //   886: i2d            
        //   887: ldc2_w          3.0
        //   890: ddiv           
        //   891: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   894: iload           a
        //   896: iconst_4       
        //   897: iadd           
        //   898: i2d            
        //   899: iload           a
        //   901: i2d            
        //   902: ldc2_w          2.0
        //   905: ddiv           
        //   906: dadd           
        //   907: ldc2_w          2.0
        //   910: iload           a
        //   912: i2d            
        //   913: dmul           
        //   914: ldc2_w          3.0
        //   917: ddiv           
        //   918: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   921: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //   924: fconst_1       
        //   925: invokestatic    org/lwjgl/opengl/GL11.glLineWidth:(F)V
        //   928: fconst_0       
        //   929: fconst_0       
        //   930: fconst_0       
        //   931: fconst_1       
        //   932: invokestatic    org/lwjgl/opengl/GL11.glColor4f:(FFFF)V
        //   935: aload_1         /* a */
        //   936: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()Z
        //   941: iload_2         /* a */
        //   942: ifne            1028
        //   945: ifeq            984
        //   948: iconst_1       
        //   949: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   952: ldc2_w          2.0
        //   955: aload           a
        //   957: getfield        java/awt/Rectangle.height:I
        //   960: i2d            
        //   961: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   964: aload           a
        //   966: getfield        java/awt/Rectangle.width:I
        //   969: iconst_2       
        //   970: isub           
        //   971: i2d            
        //   972: aload           a
        //   974: getfield        java/awt/Rectangle.height:I
        //   977: i2d            
        //   978: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   981: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //   984: iconst_1       
        //   985: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   988: iload           a
        //   990: iconst_4       
        //   991: iadd           
        //   992: i2d            
        //   993: ldc2_w          2.0
        //   996: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //   999: iload           a
        //  1001: iconst_4       
        //  1002: iadd           
        //  1003: i2d            
        //  1004: aload           a
        //  1006: getfield        java/awt/Rectangle.height:I
        //  1009: iconst_2       
        //  1010: isub           
        //  1011: i2d            
        //  1012: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //  1015: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //  1018: iconst_2       
        //  1019: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //  1022: aload_1         /* a */
        //  1023: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()Z
        //  1028: iload_2         /* a */
        //  1029: ifne            1174
        //  1032: ifeq            1120
        //  1035: iload           a
        //  1037: iconst_4       
        //  1038: iadd           
        //  1039: i2d            
        //  1040: iload           a
        //  1042: i2d            
        //  1043: ldc2_w          2.0
        //  1046: ddiv           
        //  1047: dadd           
        //  1048: iload           a
        //  1050: i2d            
        //  1051: ldc2_w          3.0
        //  1054: ddiv           
        //  1055: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //  1058: iload           a
        //  1060: iconst_4       
        //  1061: iadd           
        //  1062: i2d            
        //  1063: iload           a
        //  1065: i2d            
        //  1066: ldc2_w          3.0
        //  1069: ddiv           
        //  1070: dadd           
        //  1071: ldc2_w          2.0
        //  1074: iload           a
        //  1076: i2d            
        //  1077: dmul           
        //  1078: ldc2_w          3.0
        //  1081: ddiv           
        //  1082: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //  1085: iload           a
        //  1087: iconst_4       
        //  1088: iadd           
        //  1089: i2d            
        //  1090: ldc2_w          2.0
        //  1093: iload           a
        //  1095: i2d            
        //  1096: dmul           
        //  1097: ldc2_w          3.0
        //  1100: ddiv           
        //  1101: dadd           
        //  1102: ldc2_w          2.0
        //  1105: iload           a
        //  1107: i2d            
        //  1108: dmul           
        //  1109: ldc2_w          3.0
        //  1112: ddiv           
        //  1113: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //  1116: iload_2         /* a */
        //  1117: ifeq            1197
        //  1120: iload           a
        //  1122: iconst_4       
        //  1123: iadd           
        //  1124: i2d            
        //  1125: iload           a
        //  1127: i2d            
        //  1128: ldc2_w          3.0
        //  1131: ddiv           
        //  1132: dadd           
        //  1133: iload           a
        //  1135: i2d            
        //  1136: ldc2_w          3.0
        //  1139: ddiv           
        //  1140: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //  1143: iload           a
        //  1145: iconst_4       
        //  1146: iadd           
        //  1147: i2d            
        //  1148: ldc2_w          2.0
        //  1151: iload           a
        //  1153: i2d            
        //  1154: dmul           
        //  1155: ldc2_w          3.0
        //  1158: ddiv           
        //  1159: dadd           
        //  1160: iload           a
        //  1162: i2d            
        //  1163: ldc2_w          3.0
        //  1166: ddiv           
        //  1167: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //  1170: iload           a
        //  1172: iconst_4       
        //  1173: iadd           
        //  1174: i2d            
        //  1175: iload           a
        //  1177: i2d            
        //  1178: ldc2_w          2.0
        //  1181: ddiv           
        //  1182: dadd           
        //  1183: ldc2_w          2.0
        //  1186: iload           a
        //  1188: i2d            
        //  1189: dmul           
        //  1190: ldc2_w          3.0
        //  1193: ddiv           
        //  1194: invokestatic    org/lwjgl/opengl/GL11.glVertex2d:(DD)V
        //  1197: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //  1200: sipush          3553
        //  1203: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //  1206: aload_1         /* a */
        //  1207: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.c:()Ljava/lang/String;
        //  1212: astore          a
        //  1214: aload_0         /* a */
        //  1215: getfield        com/sun/jna/z/a/e/a/a/a/e/a/c.e:Lcom/sun/jna/z/a/e/a/a/a/e/a/i;
        //  1218: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/i.a:()Lcom/sun/jna/z/a/e/a/a/a/b/a;
        //  1221: aload           a
        //  1223: iconst_2       
        //  1224: aload           a
        //  1226: getfield        java/awt/Rectangle.height:I
        //  1229: iconst_2       
        //  1230: idiv           
        //  1231: aload_0         /* a */
        //  1232: getfield        com/sun/jna/z/a/e/a/a/a/e/a/c.e:Lcom/sun/jna/z/a/e/a/a/a/e/a/i;
        //  1235: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/i.a:()Lcom/sun/jna/z/a/e/a/a/a/b/a;
        //  1238: getfield        com/sun/jna/z/a/e/a/a/a/b/a.field_78288_b:I
        //  1241: iconst_2       
        //  1242: idiv           
        //  1243: isub           
        //  1244: aload_1         /* a */
        //  1245: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.m:()Ljava/awt/Color;
        //  1250: invokestatic    com/sun/jna/z/a/e/a/a/a/f/a.b:(Ljava/awt/Color;)I
        //  1253: invokevirtual   com/sun/jna/z/a/e/a/a/a/b/a.func_78276_b:(Ljava/lang/String;III)I
        //  1256: pop            
        //  1257: aload_1         /* a */
        //  1258: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()Z
        //  1263: iload_2         /* a */
        //  1264: ifne            1382
        //  1267: ifeq            1373
        //  1270: aload           a
        //  1272: getfield        java/awt/Rectangle.height:I
        //  1275: iconst_2       
        //  1276: iadd           
        //  1277: istore          a
        //  1279: aload_1         /* a */
        //  1280: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.a:()[Ljava/lang/String;
        //  1285: astore          a
        //  1287: iconst_0       
        //  1288: istore          a
        //  1290: iload           a
        //  1292: aload           a
        //  1294: arraylength    
        //  1295: if_icmpge       1373
        //  1298: iload           a
        //  1300: iload_2         /* a */
        //  1301: ifne            1382
        //  1304: aload_1         /* a */
        //  1305: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.b:()I
        //  1310: iload_2         /* a */
        //  1311: ifne            1363
        //  1314: if_icmpne       1321
        //  1317: iload_2         /* a */
        //  1318: ifeq            1366
        //  1321: aload_0         /* a */
        //  1322: getfield        com/sun/jna/z/a/e/a/a/a/e/a/c.e:Lcom/sun/jna/z/a/e/a/a/a/e/a/i;
        //  1325: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/i.a:()Lcom/sun/jna/z/a/e/a/a/a/b/a;
        //  1328: aload           a
        //  1330: iload           a
        //  1332: aaload         
        //  1333: iconst_2       
        //  1334: iload           a
        //  1336: aload_1         /* a */
        //  1337: invokeinterface com/sun/jna/z/a/e/a/a/a/a/j.m:()Ljava/awt/Color;
        //  1342: invokestatic    com/sun/jna/z/a/e/a/a/a/f/a.b:(Ljava/awt/Color;)I
        //  1345: invokevirtual   com/sun/jna/z/a/e/a/a/a/b/a.func_78276_b:(Ljava/lang/String;III)I
        //  1348: pop            
        //  1349: iload           a
        //  1351: aload_0         /* a */
        //  1352: getfield        com/sun/jna/z/a/e/a/a/a/e/a/c.e:Lcom/sun/jna/z/a/e/a/a/a/e/a/i;
        //  1355: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/i.a:()Lcom/sun/jna/z/a/e/a/a/a/b/a;
        //  1358: getfield        com/sun/jna/z/a/e/a/a/a/b/a.field_78288_b:I
        //  1361: iconst_2       
        //  1362: iadd           
        //  1363: iadd           
        //  1364: istore          a
        //  1366: iinc            a, 1
        //  1369: iload_2         /* a */
        //  1370: ifeq            1290
        //  1373: sipush          2884
        //  1376: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //  1379: sipush          3042
        //  1382: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //  1385: aload_0         /* a */
        //  1386: aload_1         /* a */
        //  1387: iconst_1       
        //  1388: invokevirtual   com/sun/jna/z/a/e/a/a/a/e/a/c.a:(Lcom/sun/jna/z/a/e/a/a/a/a/k;Z)V
        //  1391: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    protected Dimension b(final j a) {
        final int a2 = i.f ? 1 : 0;
        final String[] a3 = a.a();
        final int a4 = a3.length;
        int a5 = 0;
        int a7 = 0;
        while (a5 < a4) {
            final String a6 = a3[a5];
            a7 = Math.max(a7, this.e.a().func_78256_a(a6));
            ++a5;
            if (a2 != 0) {
                break;
            }
        }
        return new Dimension(a7 + 8 + this.e.a().field_78288_b, this.e.a().field_78288_b + 4);
    }
    
    protected Rectangle[] c(final j a) {
        a.g();
        final int a2 = i.f ? 1 : 0;
        final int a6 = a.a() ? 1 : 0;
        int a5 = 0;
        if (a2 == 0 && a6 != 0) {
            final String[] a3 = a.a();
            int a4 = 0;
            while (a4 < a3.length) {
                a5 += this.e.a().field_78288_b + 2;
                ++a4;
                if (a2 != 0) {
                    goto Label_0072;
                }
                if (a2 != 0) {
                    break;
                }
            }
            a5 += 2;
            goto Label_0072;
        }
        final Rectangle[] array = new Rectangle[a6];
        array[0] = new Rectangle(0, 0, a.f(), a5);
        return array;
    }
    
    protected void a(final j a, final Point a, final int a) {
        final int a2 = i.f ? 1 : 0;
        int x = a;
        if (a2 == 0) {
            if (a != 0) {
                return;
            }
            x = a.x;
        }
        final int f = a.f();
        int x2 = 0;
        int a7 = 0;
        Label_0105: {
            final int y;
            Label_0096: {
                if (a2 == 0) {
                    if (x <= f) {
                        y = a.y;
                        final int g = a.g();
                        if (a2 != 0) {
                            break Label_0096;
                        }
                        if (y <= g) {
                            final boolean a6 = a.a();
                            if (a2 == 0 && a6) {}
                            a.e(a6);
                            if (a2 == 0) {
                                return;
                            }
                        }
                    }
                    final int n;
                    a7 = (n = (x2 = a.x));
                    if (a2 != 0) {
                        break Label_0105;
                    }
                    a.f();
                }
            }
            if (y > f) {
                return;
            }
            x2 = (a7 = (a.a() ? 1 : 0));
        }
        if (a2 == 0) {
            if (a7 == 0) {
                return;
            }
            x2 = a.g() + 2;
        }
        int a3 = x2;
        final String[] a4 = a.a();
        int a5 = 0;
        while (a5 < a4.length) {
            final int n3;
            final int n2 = n3 = a5;
            int b;
            final int n4 = b = a.b();
            Label_0239: {
                if (a2 == 0) {
                    if (n2 == n4 && a2 == 0) {
                        break Label_0239;
                    }
                    final int y2 = a.y;
                }
                final int y3;
                final int n5;
                Label_0236: {
                    if (a2 == 0) {
                        if (n2 >= n4) {
                            y3 = a.y;
                            n5 = a3 + this.e.a().field_78288_b;
                            if (a2 != 0) {
                                break Label_0236;
                            }
                            if (y3 <= n5) {
                                a.a(a5);
                                a.e(false);
                                if (a2 == 0) {
                                    break;
                                }
                            }
                        }
                        b = this.e.a().field_78288_b + 2;
                    }
                }
                a3 = y3 + n5;
            }
            ++a5;
            if (a2 != 0) {
                break;
            }
        }
    }
}
