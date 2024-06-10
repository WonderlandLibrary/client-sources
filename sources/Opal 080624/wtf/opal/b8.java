package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1887;

class b8 extends HashMap<class_1887, String> {
  private static final long a = on.a(-1895597400547114977L, -4088445136335872353L, MethodHandles.lookup().lookupClass()).a(226007547435798L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  b8(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/b8.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: aload_0
    //   7: invokespecial <init> : ()V
    //   10: aload_0
    //   11: getstatic net/minecraft/class_1893.field_9111 : Lnet/minecraft/class_1887;
    //   14: sipush #4238
    //   17: ldc2_w 4174883201225065606
    //   20: lload_1
    //   21: lxor
    //   22: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   27: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   30: pop
    //   31: aload_0
    //   32: getstatic net/minecraft/class_1893.field_9095 : Lnet/minecraft/class_1887;
    //   35: sipush #1468
    //   38: ldc2_w 517489354193899952
    //   41: lload_1
    //   42: lxor
    //   43: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   48: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   51: pop
    //   52: aload_0
    //   53: getstatic net/minecraft/class_1893.field_9129 : Lnet/minecraft/class_1887;
    //   56: sipush #22897
    //   59: ldc2_w 1302886130731306357
    //   62: lload_1
    //   63: lxor
    //   64: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   69: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   72: pop
    //   73: aload_0
    //   74: getstatic net/minecraft/class_1893.field_9107 : Lnet/minecraft/class_1887;
    //   77: sipush #14705
    //   80: ldc2_w 5143948419470503274
    //   83: lload_1
    //   84: lxor
    //   85: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   90: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   93: pop
    //   94: aload_0
    //   95: getstatic net/minecraft/class_1893.field_9096 : Lnet/minecraft/class_1887;
    //   98: sipush #7058
    //   101: ldc2_w 1110551144260439957
    //   104: lload_1
    //   105: lxor
    //   106: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   111: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   114: pop
    //   115: aload_0
    //   116: getstatic net/minecraft/class_1893.field_9127 : Lnet/minecraft/class_1887;
    //   119: sipush #26270
    //   122: ldc2_w 2040312635364831887
    //   125: lload_1
    //   126: lxor
    //   127: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   132: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   135: pop
    //   136: aload_0
    //   137: getstatic net/minecraft/class_1893.field_9105 : Lnet/minecraft/class_1887;
    //   140: sipush #24944
    //   143: ldc2_w 6745724899431052667
    //   146: lload_1
    //   147: lxor
    //   148: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   153: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   156: pop
    //   157: aload_0
    //   158: getstatic net/minecraft/class_1893.field_9097 : Lnet/minecraft/class_1887;
    //   161: sipush #2000
    //   164: ldc2_w 5156201598287844305
    //   167: lload_1
    //   168: lxor
    //   169: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   174: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   177: pop
    //   178: aload_0
    //   179: getstatic net/minecraft/class_1893.field_9128 : Lnet/minecraft/class_1887;
    //   182: sipush #9207
    //   185: ldc2_w 3707909312601995253
    //   188: lload_1
    //   189: lxor
    //   190: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   195: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   198: pop
    //   199: aload_0
    //   200: getstatic net/minecraft/class_1893.field_9122 : Lnet/minecraft/class_1887;
    //   203: sipush #25943
    //   206: ldc2_w 288270411596525933
    //   209: lload_1
    //   210: lxor
    //   211: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   216: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   219: pop
    //   220: aload_0
    //   221: getstatic net/minecraft/class_1893.field_9113 : Lnet/minecraft/class_1887;
    //   224: sipush #4056
    //   227: ldc2_w 574748896011656152
    //   230: lload_1
    //   231: lxor
    //   232: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   237: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   240: pop
    //   241: aload_0
    //   242: getstatic net/minecraft/class_1893.field_23071 : Lnet/minecraft/class_1887;
    //   245: sipush #28957
    //   248: ldc2_w 7427100199023994120
    //   251: lload_1
    //   252: lxor
    //   253: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   258: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   261: pop
    //   262: aload_0
    //   263: getstatic net/minecraft/class_1893.field_38223 : Lnet/minecraft/class_1887;
    //   266: sipush #24070
    //   269: ldc2_w 7279863179098147344
    //   272: lload_1
    //   273: lxor
    //   274: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   279: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   282: pop
    //   283: aload_0
    //   284: getstatic net/minecraft/class_1893.field_9118 : Lnet/minecraft/class_1887;
    //   287: sipush #20263
    //   290: ldc2_w 1026461184507239192
    //   293: lload_1
    //   294: lxor
    //   295: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   300: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   303: pop
    //   304: aload_0
    //   305: getstatic net/minecraft/class_1893.field_9123 : Lnet/minecraft/class_1887;
    //   308: sipush #9647
    //   311: ldc2_w 7801724490856673697
    //   314: lload_1
    //   315: lxor
    //   316: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   321: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   324: pop
    //   325: aload_0
    //   326: getstatic net/minecraft/class_1893.field_9112 : Lnet/minecraft/class_1887;
    //   329: sipush #16535
    //   332: ldc2_w 6112847338220311691
    //   335: lload_1
    //   336: lxor
    //   337: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   342: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   345: pop
    //   346: aload_0
    //   347: getstatic net/minecraft/class_1893.field_9121 : Lnet/minecraft/class_1887;
    //   350: sipush #3281
    //   353: ldc2_w 1461652315564054748
    //   356: lload_1
    //   357: lxor
    //   358: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   363: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   366: pop
    //   367: aload_0
    //   368: getstatic net/minecraft/class_1893.field_9124 : Lnet/minecraft/class_1887;
    //   371: sipush #24200
    //   374: ldc2_w 1395690033602775682
    //   377: lload_1
    //   378: lxor
    //   379: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   384: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   387: pop
    //   388: aload_0
    //   389: getstatic net/minecraft/class_1893.field_9110 : Lnet/minecraft/class_1887;
    //   392: sipush #22955
    //   395: ldc2_w 8547176561338076590
    //   398: lload_1
    //   399: lxor
    //   400: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   405: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   408: pop
    //   409: aload_0
    //   410: getstatic net/minecraft/class_1893.field_9115 : Lnet/minecraft/class_1887;
    //   413: sipush #28953
    //   416: ldc2_w 2939361528705443086
    //   419: lload_1
    //   420: lxor
    //   421: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   426: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   429: pop
    //   430: aload_0
    //   431: getstatic net/minecraft/class_1893.field_9131 : Lnet/minecraft/class_1887;
    //   434: sipush #20260
    //   437: ldc2_w 2695040974122933037
    //   440: lload_1
    //   441: lxor
    //   442: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   447: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   450: pop
    //   451: aload_0
    //   452: getstatic net/minecraft/class_1893.field_9099 : Lnet/minecraft/class_1887;
    //   455: sipush #21451
    //   458: ldc2_w 2766818454748284877
    //   461: lload_1
    //   462: lxor
    //   463: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   468: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   471: pop
    //   472: aload_0
    //   473: getstatic net/minecraft/class_1893.field_9119 : Lnet/minecraft/class_1887;
    //   476: sipush #14506
    //   479: ldc2_w 4174673003510574264
    //   482: lload_1
    //   483: lxor
    //   484: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   489: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   492: pop
    //   493: aload_0
    //   494: getstatic net/minecraft/class_1893.field_9130 : Lnet/minecraft/class_1887;
    //   497: sipush #28215
    //   500: ldc2_w 95848988961503758
    //   503: lload_1
    //   504: lxor
    //   505: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   510: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   513: pop
    //   514: aload_0
    //   515: getstatic net/minecraft/class_1893.field_9103 : Lnet/minecraft/class_1887;
    //   518: sipush #1230
    //   521: ldc2_w 1339657737154498769
    //   524: lload_1
    //   525: lxor
    //   526: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   531: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   534: pop
    //   535: aload_0
    //   536: getstatic net/minecraft/class_1893.field_9116 : Lnet/minecraft/class_1887;
    //   539: sipush #22197
    //   542: ldc2_w 4464649886761992845
    //   545: lload_1
    //   546: lxor
    //   547: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   552: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   555: pop
    //   556: aload_0
    //   557: getstatic net/minecraft/class_1893.field_9126 : Lnet/minecraft/class_1887;
    //   560: sipush #5928
    //   563: ldc2_w 7791154276420584252
    //   566: lload_1
    //   567: lxor
    //   568: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   573: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   576: pop
    //   577: aload_0
    //   578: getstatic net/minecraft/class_1893.field_9125 : Lnet/minecraft/class_1887;
    //   581: sipush #20727
    //   584: ldc2_w 2184961894661276920
    //   587: lload_1
    //   588: lxor
    //   589: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   594: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   597: pop
    //   598: aload_0
    //   599: getstatic net/minecraft/class_1893.field_9114 : Lnet/minecraft/class_1887;
    //   602: sipush #8783
    //   605: ldc2_w 3720760179265451615
    //   608: lload_1
    //   609: lxor
    //   610: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   615: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   618: pop
    //   619: aload_0
    //   620: getstatic net/minecraft/class_1893.field_9100 : Lnet/minecraft/class_1887;
    //   623: sipush #219
    //   626: ldc2_w 6313729704481952998
    //   629: lload_1
    //   630: lxor
    //   631: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   636: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   639: pop
    //   640: aload_0
    //   641: getstatic net/minecraft/class_1893.field_9120 : Lnet/minecraft/class_1887;
    //   644: sipush #4140
    //   647: ldc2_w 1223009952497042479
    //   650: lload_1
    //   651: lxor
    //   652: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   657: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   660: pop
    //   661: aload_0
    //   662: getstatic net/minecraft/class_1893.field_9106 : Lnet/minecraft/class_1887;
    //   665: sipush #9396
    //   668: ldc2_w 7960496286927597704
    //   671: lload_1
    //   672: lxor
    //   673: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   678: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   681: pop
    //   682: aload_0
    //   683: getstatic net/minecraft/class_1893.field_9104 : Lnet/minecraft/class_1887;
    //   686: sipush #19781
    //   689: ldc2_w 9062336535981971798
    //   692: lload_1
    //   693: lxor
    //   694: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   699: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   702: pop
    //   703: aload_0
    //   704: getstatic net/minecraft/class_1893.field_9117 : Lnet/minecraft/class_1887;
    //   707: sipush #17446
    //   710: ldc2_w 411143292544471064
    //   713: lload_1
    //   714: lxor
    //   715: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   720: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   723: pop
    //   724: aload_0
    //   725: getstatic net/minecraft/class_1893.field_9108 : Lnet/minecraft/class_1887;
    //   728: sipush #9264
    //   731: ldc2_w 6783691753318985770
    //   734: lload_1
    //   735: lxor
    //   736: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   741: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   744: pop
    //   745: aload_0
    //   746: getstatic net/minecraft/class_1893.field_9098 : Lnet/minecraft/class_1887;
    //   749: sipush #21933
    //   752: ldc2_w 4670371992932762037
    //   755: lload_1
    //   756: lxor
    //   757: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   762: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   765: pop
    //   766: aload_0
    //   767: getstatic net/minecraft/class_1893.field_9132 : Lnet/minecraft/class_1887;
    //   770: sipush #26130
    //   773: ldc2_w 8634900470865752588
    //   776: lload_1
    //   777: lxor
    //   778: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   783: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   786: pop
    //   787: aload_0
    //   788: getstatic net/minecraft/class_1893.field_9101 : Lnet/minecraft/class_1887;
    //   791: sipush #9142
    //   794: ldc2_w 5710322927371237295
    //   797: lload_1
    //   798: lxor
    //   799: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   804: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   807: pop
    //   808: aload_0
    //   809: getstatic net/minecraft/class_1893.field_9109 : Lnet/minecraft/class_1887;
    //   812: sipush #877
    //   815: ldc2_w 3905625240960735088
    //   818: lload_1
    //   819: lxor
    //   820: <illegal opcode> d : (IJ)Ljava/lang/String;
    //   825: invokevirtual put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   828: pop
    //   829: return
  }
  
  static {
    long l = a ^ 0x4773379088B0L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[39];
    boolean bool = false;
    String str;
    int i = (str = "I=\034C?RË§?\022§²³D\020t\006K÷QW.Äì,\026-ö\020±lí\025æm\037ÌÏ*ñ?U\020Ô\035<tj\026ýVA®\026ñYÄ\020\020~¯§D3tò\030C\nÅ0w,\020\024Ñ\007hÓÀ×Òù\022ÿ^'î#\020\030¼\nGùçQ\017eå\034Wnwû\020ï9°wLk\001;îà Ò¥\017\0206 ºÛEî>à\r\013)1ï>\006\020\017i\017E¨È¼½t×6À¶¼%\020|Æ&F/óßÒP\033Ø¹5\020Î¬ýåÈÙ-\rÚ@Jð\031\020øÖ}(ö\033Ð$s!ª¬I\034\020Ò\024\027m²0\r\"æ|·t\034ø\020/Y\n9Âi\016\\\021z\032I\037v¹\020-y\004giéóµPªJ</n\020q\031¢Ò\036×\035mß\013\\Qø¼û\020ºº\0062½Î®\rQ*òE{?\020;djýD_¢yÉ~M\032Û\020HG¹9ô¥8¸èí#âhº@\025\020ÉÞZtiLt¿e½1ÑT\0205\021?ß ½Â\024j\rÞNÉ2\020â\022DV\031zð8Ø¢\022£ºO\020b×jm\fF8¦HßFâÛ,\020\013b9Kzf/ÏÛßÜm\030w®\020$@û8x½|×\036ÍÂ¬Y¦\020Ò!þ\000+ôÞAâHÆ×é\020ÿ1aQäàÂ\bn\\õ-\035\020\027Åg.ª\037\016Ù*+x,Õ&T\020ÎïÜ°\016\037ò\0139b*H´S\020zÜ¸ª/Ìs\036*ü\034BÈa.\020ö³~7 \001\023ó\006rÑbÀZðR\020\026´%V`?4bÁÓ\033\020é\021|ó\\I±x\027Ç9cF\032¡\020ï\022ZI¼\023^ôXÆ§£\037\020H×°Üp&YA1\034Ãò^\020öüágî¹\006°öb¿\\QZ").length();
    byte b2 = 16;
    byte b = -1;
    while (true);
  }
  
  private static String a(byte[] paramArrayOfbyte) {
    byte b1 = 0;
    int i;
    char[] arrayOfChar = new char[i = paramArrayOfbyte.length];
    for (byte b2 = 0; b2 < i; b2++) {
      int j;
      if ((j = 0xFF & paramArrayOfbyte[b2]) < 192) {
        arrayOfChar[b1++] = (char)j;
      } else if (j < 224) {
        char c = (char)((char)(j & 0x1F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } else if (b2 < i - 2) {
        char c = (char)((char)(j & 0xF) << 12);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F) << 6);
        j = paramArrayOfbyte[++b2];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
  
  private static String a(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x428;
    if (c[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])d.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          d.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/b8", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
      c[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return c[i];
  }
  
  private static Object a(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    String str = a(i, l);
    MethodHandle methodHandle = MethodHandles.constant(String.class, str);
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return str;
  }
  
  private static CallSite a(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
    // Byte code:
    //   0: new java/lang/invoke/MutableCallSite
    //   3: dup
    //   4: aload_2
    //   5: invokespecial <init> : (Ljava/lang/invoke/MethodType;)V
    //   8: astore_3
    //   9: aload_3
    //   10: ldc_w
    //   13: ldc_w [Ljava/lang/Object;
    //   16: aload_2
    //   17: invokevirtual parameterCount : ()I
    //   20: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   23: iconst_0
    //   24: iconst_3
    //   25: anewarray java/lang/Object
    //   28: dup
    //   29: iconst_0
    //   30: aload_0
    //   31: aastore
    //   32: dup
    //   33: iconst_1
    //   34: aload_3
    //   35: aastore
    //   36: dup
    //   37: iconst_2
    //   38: aload_1
    //   39: aastore
    //   40: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   43: aload_2
    //   44: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   47: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   50: goto -> 104
    //   53: astore #4
    //   55: new java/lang/RuntimeException
    //   58: dup
    //   59: new java/lang/StringBuilder
    //   62: dup
    //   63: invokespecial <init> : ()V
    //   66: ldc_w 'wtf/opal/b8'
    //   69: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   72: ldc_w ' : '
    //   75: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   78: aload_1
    //   79: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   82: ldc_w ' : '
    //   85: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   88: aload_2
    //   89: invokevirtual toString : ()Ljava/lang/String;
    //   92: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   95: invokevirtual toString : ()Ljava/lang/String;
    //   98: aload #4
    //   100: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   103: athrow
    //   104: aload_3
    //   105: areturn
    // Exception table:
    //   from	to	target	type
    //   9	50	53	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b8.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */