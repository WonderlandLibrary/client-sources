package dev.jnic.eEZCNM;

public final class U extends k {
  private g c;
  
  final H O;
  
  private final r P;
  
  private final v Q = new v(this, (byte)0);
  
  private final v R = new v(this, (byte)0);
  
  public U(g paramg, H paramH, int paramInt1, int paramInt2, int paramInt3) {
    super(paramInt3);
    this.c = paramg;
    this.O = paramH;
    this.P = new r(this, paramInt1, paramInt2);
    c();
  }
  
  public final void c() {
    super.c();
    this.P.c();
    this.Q.c();
    this.R.c();
  }
  
  public final void d() {
    // Byte code:
    //   0: aload_0
    //   1: getfield c : Ldev/jnic/eEZCNM/g;
    //   4: dup
    //   5: astore_1
    //   6: getfield s : I
    //   9: ifle -> 24
    //   12: aload_1
    //   13: dup
    //   14: getfield t : I
    //   17: aload_1
    //   18: getfield s : I
    //   21: invokevirtual a : (II)V
    //   24: aload_0
    //   25: getfield c : Ldev/jnic/eEZCNM/g;
    //   28: dup
    //   29: astore_1
    //   30: getfield p : I
    //   33: aload_1
    //   34: getfield r : I
    //   37: if_icmpge -> 44
    //   40: iconst_1
    //   41: goto -> 45
    //   44: iconst_0
    //   45: ifeq -> 978
    //   48: aload_0
    //   49: getfield c : Ldev/jnic/eEZCNM/g;
    //   52: getfield p : I
    //   55: aload_0
    //   56: getfield v : I
    //   59: iand
    //   60: istore_1
    //   61: aload_0
    //   62: getfield O : Ldev/jnic/eEZCNM/H;
    //   65: aload_0
    //   66: getfield y : [[S
    //   69: aload_0
    //   70: getfield x : Ldev/jnic/eEZCNM/t;
    //   73: getfield S : I
    //   76: aaload
    //   77: iload_1
    //   78: invokevirtual a : ([SI)I
    //   81: ifne -> 430
    //   84: aload_0
    //   85: getfield P : Ldev/jnic/eEZCNM/r;
    //   88: dup
    //   89: dup
    //   90: astore_1
    //   91: getfield T : Ldev/jnic/eEZCNM/U;
    //   94: getfield c : Ldev/jnic/eEZCNM/g;
    //   97: iconst_0
    //   98: invokevirtual b : (I)I
    //   101: aload_1
    //   102: getfield T : Ldev/jnic/eEZCNM/U;
    //   105: getfield c : Ldev/jnic/eEZCNM/g;
    //   108: getfield p : I
    //   111: istore #4
    //   113: istore_2
    //   114: astore_3
    //   115: iload_2
    //   116: bipush #8
    //   118: aload_3
    //   119: getfield L : I
    //   122: isub
    //   123: ishr
    //   124: istore #5
    //   126: iload #4
    //   128: aload_3
    //   129: getfield M : I
    //   132: iand
    //   133: aload_3
    //   134: getfield L : I
    //   137: ishl
    //   138: istore #6
    //   140: iload #5
    //   142: iload #6
    //   144: iadd
    //   145: istore_2
    //   146: aload_1
    //   147: getfield U : [Ldev/jnic/eEZCNM/x;
    //   150: iload_2
    //   151: aaload
    //   152: astore_3
    //   153: iconst_1
    //   154: istore_2
    //   155: aload_3
    //   156: getfield V : Ldev/jnic/eEZCNM/r;
    //   159: getfield T : Ldev/jnic/eEZCNM/U;
    //   162: getfield x : Ldev/jnic/eEZCNM/t;
    //   165: getfield S : I
    //   168: bipush #7
    //   170: if_icmpge -> 177
    //   173: iconst_1
    //   174: goto -> 178
    //   177: iconst_0
    //   178: ifeq -> 214
    //   181: iload_2
    //   182: iconst_1
    //   183: ishl
    //   184: aload_3
    //   185: getfield V : Ldev/jnic/eEZCNM/r;
    //   188: getfield T : Ldev/jnic/eEZCNM/U;
    //   191: getfield O : Ldev/jnic/eEZCNM/H;
    //   194: aload_3
    //   195: getfield N : [S
    //   198: iload_2
    //   199: invokevirtual a : ([SI)I
    //   202: ior
    //   203: dup
    //   204: istore_2
    //   205: sipush #256
    //   208: if_icmplt -> 181
    //   211: goto -> 312
    //   214: aload_3
    //   215: getfield V : Ldev/jnic/eEZCNM/r;
    //   218: getfield T : Ldev/jnic/eEZCNM/U;
    //   221: getfield c : Ldev/jnic/eEZCNM/g;
    //   224: aload_3
    //   225: getfield V : Ldev/jnic/eEZCNM/r;
    //   228: getfield T : Ldev/jnic/eEZCNM/U;
    //   231: getfield w : [I
    //   234: iconst_0
    //   235: iaload
    //   236: invokevirtual b : (I)I
    //   239: istore #4
    //   241: sipush #256
    //   244: istore #5
    //   246: iload #4
    //   248: iconst_1
    //   249: ishl
    //   250: dup
    //   251: istore #4
    //   253: iload #5
    //   255: iand
    //   256: istore #6
    //   258: aload_3
    //   259: getfield V : Ldev/jnic/eEZCNM/r;
    //   262: getfield T : Ldev/jnic/eEZCNM/U;
    //   265: getfield O : Ldev/jnic/eEZCNM/H;
    //   268: aload_3
    //   269: getfield N : [S
    //   272: iload #5
    //   274: iload #6
    //   276: iadd
    //   277: iload_2
    //   278: iadd
    //   279: invokevirtual a : ([SI)I
    //   282: istore #7
    //   284: iload_2
    //   285: iconst_1
    //   286: ishl
    //   287: iload #7
    //   289: ior
    //   290: istore_2
    //   291: iload #5
    //   293: iconst_0
    //   294: iload #7
    //   296: isub
    //   297: iload #6
    //   299: iconst_m1
    //   300: ixor
    //   301: ixor
    //   302: iand
    //   303: istore #5
    //   305: iload_2
    //   306: sipush #256
    //   309: if_icmplt -> 246
    //   312: aload_3
    //   313: getfield V : Ldev/jnic/eEZCNM/r;
    //   316: getfield T : Ldev/jnic/eEZCNM/U;
    //   319: getfield c : Ldev/jnic/eEZCNM/g;
    //   322: iload_2
    //   323: i2b
    //   324: istore_1
    //   325: dup
    //   326: astore_2
    //   327: getfield m : [B
    //   330: aload_2
    //   331: dup
    //   332: getfield p : I
    //   335: dup_x1
    //   336: iconst_1
    //   337: iadd
    //   338: putfield p : I
    //   341: iload_1
    //   342: bastore
    //   343: aload_2
    //   344: getfield q : I
    //   347: aload_2
    //   348: getfield p : I
    //   351: if_icmpge -> 362
    //   354: aload_2
    //   355: dup
    //   356: getfield p : I
    //   359: putfield q : I
    //   362: aload_3
    //   363: getfield V : Ldev/jnic/eEZCNM/r;
    //   366: getfield T : Ldev/jnic/eEZCNM/U;
    //   369: getfield x : Ldev/jnic/eEZCNM/t;
    //   372: dup
    //   373: astore #4
    //   375: getfield S : I
    //   378: iconst_3
    //   379: if_icmpgt -> 391
    //   382: aload #4
    //   384: iconst_0
    //   385: putfield S : I
    //   388: goto -> 24
    //   391: aload #4
    //   393: getfield S : I
    //   396: bipush #9
    //   398: if_icmpgt -> 415
    //   401: aload #4
    //   403: dup
    //   404: getfield S : I
    //   407: iconst_3
    //   408: isub
    //   409: putfield S : I
    //   412: goto -> 24
    //   415: aload #4
    //   417: dup
    //   418: getfield S : I
    //   421: bipush #6
    //   423: isub
    //   424: putfield S : I
    //   427: goto -> 24
    //   430: aload_0
    //   431: getfield O : Ldev/jnic/eEZCNM/H;
    //   434: aload_0
    //   435: getfield z : [S
    //   438: aload_0
    //   439: getfield x : Ldev/jnic/eEZCNM/t;
    //   442: getfield S : I
    //   445: invokevirtual a : ([SI)I
    //   448: ifne -> 742
    //   451: aload_0
    //   452: iload_1
    //   453: istore_2
    //   454: dup
    //   455: astore_1
    //   456: getfield x : Ldev/jnic/eEZCNM/t;
    //   459: dup
    //   460: getfield S : I
    //   463: bipush #7
    //   465: if_icmpge -> 473
    //   468: bipush #7
    //   470: goto -> 475
    //   473: bipush #10
    //   475: putfield S : I
    //   478: aload_1
    //   479: getfield w : [I
    //   482: iconst_3
    //   483: aload_1
    //   484: getfield w : [I
    //   487: iconst_2
    //   488: iaload
    //   489: iastore
    //   490: aload_1
    //   491: getfield w : [I
    //   494: iconst_2
    //   495: aload_1
    //   496: getfield w : [I
    //   499: iconst_1
    //   500: iaload
    //   501: iastore
    //   502: aload_1
    //   503: getfield w : [I
    //   506: iconst_1
    //   507: aload_1
    //   508: getfield w : [I
    //   511: iconst_0
    //   512: iaload
    //   513: iastore
    //   514: aload_1
    //   515: getfield Q : Ldev/jnic/eEZCNM/v;
    //   518: iload_2
    //   519: invokevirtual c : (I)I
    //   522: istore_3
    //   523: aload_1
    //   524: getfield O : Ldev/jnic/eEZCNM/H;
    //   527: aload_1
    //   528: getfield E : [[S
    //   531: iload_3
    //   532: dup
    //   533: istore #5
    //   535: bipush #6
    //   537: if_icmpge -> 547
    //   540: iload #5
    //   542: iconst_2
    //   543: isub
    //   544: goto -> 548
    //   547: iconst_3
    //   548: aaload
    //   549: invokevirtual a : ([S)I
    //   552: dup
    //   553: istore_2
    //   554: iconst_4
    //   555: if_icmpge -> 568
    //   558: aload_1
    //   559: getfield w : [I
    //   562: iconst_0
    //   563: iload_2
    //   564: iastore
    //   565: goto -> 738
    //   568: iload_2
    //   569: iconst_1
    //   570: ishr
    //   571: iconst_1
    //   572: isub
    //   573: istore #4
    //   575: aload_1
    //   576: getfield w : [I
    //   579: iconst_0
    //   580: iconst_2
    //   581: iload_2
    //   582: iconst_1
    //   583: iand
    //   584: ior
    //   585: iload #4
    //   587: ishl
    //   588: iastore
    //   589: iload_2
    //   590: bipush #14
    //   592: if_icmpge -> 622
    //   595: aload_1
    //   596: getfield w : [I
    //   599: iconst_0
    //   600: dup2
    //   601: iaload
    //   602: aload_1
    //   603: getfield O : Ldev/jnic/eEZCNM/H;
    //   606: aload_1
    //   607: getfield F : [[S
    //   610: iload_2
    //   611: iconst_4
    //   612: isub
    //   613: aaload
    //   614: invokevirtual b : ([S)I
    //   617: ior
    //   618: iastore
    //   619: goto -> 738
    //   622: aload_1
    //   623: getfield w : [I
    //   626: iconst_0
    //   627: dup2
    //   628: iaload
    //   629: aload_1
    //   630: getfield O : Ldev/jnic/eEZCNM/H;
    //   633: iload #4
    //   635: iconst_4
    //   636: isub
    //   637: istore #6
    //   639: astore #5
    //   641: iconst_0
    //   642: istore #7
    //   644: aload #5
    //   646: invokevirtual e : ()V
    //   649: aload #5
    //   651: dup
    //   652: getfield W : I
    //   655: iconst_1
    //   656: iushr
    //   657: putfield W : I
    //   660: aload #5
    //   662: getfield X : I
    //   665: aload #5
    //   667: getfield W : I
    //   670: isub
    //   671: bipush #31
    //   673: iushr
    //   674: istore_2
    //   675: aload #5
    //   677: dup
    //   678: getfield X : I
    //   681: aload #5
    //   683: getfield W : I
    //   686: iload_2
    //   687: iconst_1
    //   688: isub
    //   689: iand
    //   690: isub
    //   691: putfield X : I
    //   694: iload #7
    //   696: iconst_1
    //   697: ishl
    //   698: iconst_1
    //   699: iload_2
    //   700: isub
    //   701: ior
    //   702: istore #7
    //   704: iinc #6, -1
    //   707: iload #6
    //   709: ifne -> 644
    //   712: iload #7
    //   714: iconst_4
    //   715: ishl
    //   716: ior
    //   717: iastore
    //   718: aload_1
    //   719: getfield w : [I
    //   722: iconst_0
    //   723: dup2
    //   724: iaload
    //   725: aload_1
    //   726: getfield O : Ldev/jnic/eEZCNM/H;
    //   729: aload_1
    //   730: getfield G : [S
    //   733: invokevirtual b : ([S)I
    //   736: ior
    //   737: iastore
    //   738: iload_3
    //   739: goto -> 960
    //   742: aload_0
    //   743: iload_1
    //   744: istore_2
    //   745: dup
    //   746: astore_1
    //   747: getfield O : Ldev/jnic/eEZCNM/H;
    //   750: aload_1
    //   751: getfield A : [S
    //   754: aload_1
    //   755: getfield x : Ldev/jnic/eEZCNM/t;
    //   758: getfield S : I
    //   761: invokevirtual a : ([SI)I
    //   764: ifne -> 817
    //   767: aload_1
    //   768: getfield O : Ldev/jnic/eEZCNM/H;
    //   771: aload_1
    //   772: getfield D : [[S
    //   775: aload_1
    //   776: getfield x : Ldev/jnic/eEZCNM/t;
    //   779: getfield S : I
    //   782: aaload
    //   783: iload_2
    //   784: invokevirtual a : ([SI)I
    //   787: ifne -> 929
    //   790: aload_1
    //   791: getfield x : Ldev/jnic/eEZCNM/t;
    //   794: dup
    //   795: getfield S : I
    //   798: bipush #7
    //   800: if_icmpge -> 808
    //   803: bipush #9
    //   805: goto -> 810
    //   808: bipush #11
    //   810: putfield S : I
    //   813: iconst_1
    //   814: goto -> 960
    //   817: aload_1
    //   818: getfield O : Ldev/jnic/eEZCNM/H;
    //   821: aload_1
    //   822: getfield B : [S
    //   825: aload_1
    //   826: getfield x : Ldev/jnic/eEZCNM/t;
    //   829: getfield S : I
    //   832: invokevirtual a : ([SI)I
    //   835: ifne -> 848
    //   838: aload_1
    //   839: getfield w : [I
    //   842: iconst_1
    //   843: iaload
    //   844: istore_3
    //   845: goto -> 910
    //   848: aload_1
    //   849: getfield O : Ldev/jnic/eEZCNM/H;
    //   852: aload_1
    //   853: getfield C : [S
    //   856: aload_1
    //   857: getfield x : Ldev/jnic/eEZCNM/t;
    //   860: getfield S : I
    //   863: invokevirtual a : ([SI)I
    //   866: ifne -> 879
    //   869: aload_1
    //   870: getfield w : [I
    //   873: iconst_2
    //   874: iaload
    //   875: istore_3
    //   876: goto -> 898
    //   879: aload_1
    //   880: getfield w : [I
    //   883: iconst_3
    //   884: iaload
    //   885: istore_3
    //   886: aload_1
    //   887: getfield w : [I
    //   890: iconst_3
    //   891: aload_1
    //   892: getfield w : [I
    //   895: iconst_2
    //   896: iaload
    //   897: iastore
    //   898: aload_1
    //   899: getfield w : [I
    //   902: iconst_2
    //   903: aload_1
    //   904: getfield w : [I
    //   907: iconst_1
    //   908: iaload
    //   909: iastore
    //   910: aload_1
    //   911: getfield w : [I
    //   914: iconst_1
    //   915: aload_1
    //   916: getfield w : [I
    //   919: iconst_0
    //   920: iaload
    //   921: iastore
    //   922: aload_1
    //   923: getfield w : [I
    //   926: iconst_0
    //   927: iload_3
    //   928: iastore
    //   929: aload_1
    //   930: getfield x : Ldev/jnic/eEZCNM/t;
    //   933: dup
    //   934: getfield S : I
    //   937: bipush #7
    //   939: if_icmpge -> 947
    //   942: bipush #8
    //   944: goto -> 949
    //   947: bipush #11
    //   949: putfield S : I
    //   952: aload_1
    //   953: getfield R : Ldev/jnic/eEZCNM/v;
    //   956: iload_2
    //   957: invokevirtual c : (I)I
    //   960: istore_1
    //   961: aload_0
    //   962: getfield c : Ldev/jnic/eEZCNM/g;
    //   965: aload_0
    //   966: getfield w : [I
    //   969: iconst_0
    //   970: iaload
    //   971: iload_1
    //   972: invokevirtual a : (II)V
    //   975: goto -> 24
    //   978: aload_0
    //   979: getfield O : Ldev/jnic/eEZCNM/H;
    //   982: invokevirtual e : ()V
    //   985: return
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\dev\jnic\eEZCNM\U.class
 * Java compiler version: 7 (51.0)
 * JD-Core Version:       1.1.3
 */