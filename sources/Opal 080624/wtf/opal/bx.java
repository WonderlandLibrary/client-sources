package wtf.opal;

import com.google.gson.Gson;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import fr.litarvan.openauth.microsoft.HttpClient;
import fr.litarvan.openauth.microsoft.MicrosoftAuthResult;
import java.io.IOException;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONException;

public final class bx {
  private static final Gson j;
  
  private static MicrosoftAuthResult k;
  
  private static Consumer<MicrosoftAuthResult> U;
  
  private static final long a = on.a(-4438759435827744948L, -951087550828906652L, MethodHandles.lookup().lookupClass()).a(235638002615700L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public static void k(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast [Ljava/lang/String;
    //   7: astore_3
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_1
    //   18: pop
    //   19: getstatic wtf/opal/bx.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 72600626300235
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 93164785325371
    //   37: lxor
    //   38: lstore #6
    //   40: dup2
    //   41: ldc2_w 3371920399722
    //   44: lxor
    //   45: lstore #8
    //   47: pop2
    //   48: invokestatic X : ()[I
    //   51: astore #10
    //   53: aload_3
    //   54: arraylength
    //   55: ifeq -> 1472
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: astore #11
    //   67: new java/util/ArrayList
    //   70: dup
    //   71: invokespecial <init> : ()V
    //   74: astore #12
    //   76: aload_3
    //   77: astore #13
    //   79: aload #13
    //   81: arraylength
    //   82: istore #14
    //   84: iconst_0
    //   85: istore #15
    //   87: iload #15
    //   89: iload #14
    //   91: if_icmpge -> 287
    //   94: aload #13
    //   96: iload #15
    //   98: aaload
    //   99: astore #16
    //   101: aload #10
    //   103: lload_1
    //   104: lconst_0
    //   105: lcmp
    //   106: iflt -> 114
    //   109: ifnonnull -> 315
    //   112: aload #10
    //   114: lload_1
    //   115: lconst_0
    //   116: lcmp
    //   117: iflt -> 284
    //   120: ifnonnull -> 282
    //   123: goto -> 130
    //   126: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   129: athrow
    //   130: aload #12
    //   132: aload #16
    //   134: ldc '\\t'
    //   136: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   139: iconst_5
    //   140: aaload
    //   141: invokevirtual contains : (Ljava/lang/Object;)Z
    //   144: ifne -> 279
    //   147: goto -> 154
    //   150: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   153: athrow
    //   154: aload #16
    //   156: ldc '\\t'
    //   158: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   161: arraylength
    //   162: aload #10
    //   164: ifnonnull -> 278
    //   167: goto -> 174
    //   170: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   173: athrow
    //   174: sipush #24870
    //   177: ldc2_w 2899696729860968432
    //   180: lload_1
    //   181: lxor
    //   182: <illegal opcode> v : (IJ)I
    //   187: if_icmple -> 279
    //   190: goto -> 197
    //   193: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   196: athrow
    //   197: aload #11
    //   199: aload #16
    //   201: ldc '\\t'
    //   203: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   206: iconst_5
    //   207: aaload
    //   208: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   211: ldc '='
    //   213: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   216: aload #16
    //   218: ldc '\\t'
    //   220: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   223: sipush #7876
    //   226: ldc2_w 2157779311036788752
    //   229: lload_1
    //   230: lxor
    //   231: <illegal opcode> v : (IJ)I
    //   236: aaload
    //   237: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   240: sipush #25021
    //   243: ldc2_w 5284781481530676350
    //   246: lload_1
    //   247: lxor
    //   248: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   253: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   256: pop
    //   257: aload #12
    //   259: aload #16
    //   261: ldc '\\t'
    //   263: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   266: iconst_5
    //   267: aaload
    //   268: invokevirtual add : (Ljava/lang/Object;)Z
    //   271: goto -> 278
    //   274: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   277: athrow
    //   278: pop
    //   279: iinc #15, 1
    //   282: aload #10
    //   284: ifnull -> 87
    //   287: new java/lang/StringBuilder
    //   290: dup
    //   291: aload #11
    //   293: iconst_0
    //   294: aload #11
    //   296: invokevirtual length : ()I
    //   299: iconst_2
    //   300: isub
    //   301: invokevirtual substring : (II)Ljava/lang/String;
    //   304: invokespecial <init> : (Ljava/lang/String;)V
    //   307: astore #11
    //   309: lload_1
    //   310: lconst_0
    //   311: lcmp
    //   312: iflt -> 315
    //   315: new java/net/URL
    //   318: dup
    //   319: sipush #26899
    //   322: ldc2_w 8546833538699025569
    //   325: lload_1
    //   326: lxor
    //   327: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   332: invokespecial <init> : (Ljava/lang/String;)V
    //   335: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   338: checkcast javax/net/ssl/HttpsURLConnection
    //   341: astore #13
    //   343: aload #13
    //   345: sipush #8950
    //   348: ldc2_w 3557585032926598985
    //   351: lload_1
    //   352: lxor
    //   353: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   358: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   361: aload #13
    //   363: sipush #6032
    //   366: ldc2_w 425657923540656741
    //   369: lload_1
    //   370: lxor
    //   371: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   376: sipush #17917
    //   379: ldc2_w 5344483048827056217
    //   382: lload_1
    //   383: lxor
    //   384: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   389: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   392: aload #13
    //   394: sipush #21707
    //   397: ldc2_w 2772022009501671776
    //   400: lload_1
    //   401: lxor
    //   402: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   407: sipush #23514
    //   410: ldc2_w 7569202331924262459
    //   413: lload_1
    //   414: lxor
    //   415: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   420: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   423: aload #13
    //   425: sipush #14929
    //   428: ldc2_w 8352479488917147581
    //   431: lload_1
    //   432: lxor
    //   433: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   438: sipush #6006
    //   441: ldc2_w 3114296089444131572
    //   444: lload_1
    //   445: lxor
    //   446: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   451: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   454: aload #13
    //   456: sipush #9287
    //   459: ldc2_w 2744209467863962095
    //   462: lload_1
    //   463: lxor
    //   464: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   469: sipush #7679
    //   472: ldc2_w 6733375293463341087
    //   475: lload_1
    //   476: lxor
    //   477: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   482: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   485: aload #13
    //   487: iconst_0
    //   488: invokevirtual setInstanceFollowRedirects : (Z)V
    //   491: aload #13
    //   493: invokevirtual connect : ()V
    //   496: aload #13
    //   498: sipush #7899
    //   501: ldc2_w 1702814368466119499
    //   504: lload_1
    //   505: lxor
    //   506: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   511: invokevirtual getHeaderField : (Ljava/lang/String;)Ljava/lang/String;
    //   514: ldc ' '
    //   516: sipush #20140
    //   519: ldc2_w 7139592366489565988
    //   522: lload_1
    //   523: lxor
    //   524: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   529: invokevirtual replaceAll : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   532: astore #14
    //   534: new java/net/URL
    //   537: dup
    //   538: aload #14
    //   540: invokespecial <init> : (Ljava/lang/String;)V
    //   543: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   546: checkcast javax/net/ssl/HttpsURLConnection
    //   549: astore #13
    //   551: aload #13
    //   553: sipush #17273
    //   556: ldc2_w 5087390533023856366
    //   559: lload_1
    //   560: lxor
    //   561: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   566: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   569: aload #13
    //   571: sipush #6032
    //   574: ldc2_w 425657923540656741
    //   577: lload_1
    //   578: lxor
    //   579: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   584: sipush #10485
    //   587: ldc2_w 195731570104638816
    //   590: lload_1
    //   591: lxor
    //   592: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   597: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   600: aload #13
    //   602: sipush #15522
    //   605: ldc2_w 5416985527536584967
    //   608: lload_1
    //   609: lxor
    //   610: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   615: sipush #25933
    //   618: ldc2_w 7853181346429653182
    //   621: lload_1
    //   622: lxor
    //   623: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   628: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   631: aload #13
    //   633: sipush #21791
    //   636: ldc2_w 398166932093919374
    //   639: lload_1
    //   640: lxor
    //   641: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   646: sipush #451
    //   649: ldc2_w 3825552433018942545
    //   652: lload_1
    //   653: lxor
    //   654: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   659: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   662: aload #13
    //   664: sipush #18496
    //   667: ldc2_w 8424613906574310841
    //   670: lload_1
    //   671: lxor
    //   672: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   677: aload #11
    //   679: invokevirtual toString : ()Ljava/lang/String;
    //   682: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   685: aload #13
    //   687: sipush #9287
    //   690: ldc2_w 2744209467863962095
    //   693: lload_1
    //   694: lxor
    //   695: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   700: sipush #19534
    //   703: ldc2_w 4186922599415806376
    //   706: lload_1
    //   707: lxor
    //   708: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   713: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   716: aload #13
    //   718: iconst_0
    //   719: invokevirtual setInstanceFollowRedirects : (Z)V
    //   722: aload #13
    //   724: invokevirtual connect : ()V
    //   727: aload #13
    //   729: sipush #26566
    //   732: ldc2_w 3491182344802390599
    //   735: lload_1
    //   736: lxor
    //   737: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   742: invokevirtual getHeaderField : (Ljava/lang/String;)Ljava/lang/String;
    //   745: astore #15
    //   747: new java/net/URL
    //   750: dup
    //   751: aload #15
    //   753: invokespecial <init> : (Ljava/lang/String;)V
    //   756: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   759: checkcast javax/net/ssl/HttpsURLConnection
    //   762: astore #13
    //   764: aload #13
    //   766: sipush #17273
    //   769: ldc2_w 5087390533023856366
    //   772: lload_1
    //   773: lxor
    //   774: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   779: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   782: aload #13
    //   784: sipush #6032
    //   787: ldc2_w 425657923540656741
    //   790: lload_1
    //   791: lxor
    //   792: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   797: sipush #10485
    //   800: ldc2_w 195731570104638816
    //   803: lload_1
    //   804: lxor
    //   805: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   810: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   813: aload #13
    //   815: sipush #15522
    //   818: ldc2_w 5416985527536584967
    //   821: lload_1
    //   822: lxor
    //   823: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   828: sipush #25933
    //   831: ldc2_w 7853181346429653182
    //   834: lload_1
    //   835: lxor
    //   836: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   841: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   844: aload #13
    //   846: sipush #21791
    //   849: ldc2_w 398166932093919374
    //   852: lload_1
    //   853: lxor
    //   854: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   859: sipush #21491
    //   862: ldc2_w 8357094052766475896
    //   865: lload_1
    //   866: lxor
    //   867: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   872: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   875: aload #13
    //   877: sipush #14713
    //   880: ldc2_w 8993514354757078218
    //   883: lload_1
    //   884: lxor
    //   885: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   890: aload #11
    //   892: invokevirtual toString : ()Ljava/lang/String;
    //   895: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   898: aload #13
    //   900: sipush #9287
    //   903: ldc2_w 2744209467863962095
    //   906: lload_1
    //   907: lxor
    //   908: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   913: sipush #19534
    //   916: ldc2_w 4186922599415806376
    //   919: lload_1
    //   920: lxor
    //   921: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   926: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   929: aload #13
    //   931: iconst_0
    //   932: invokevirtual setInstanceFollowRedirects : (Z)V
    //   935: aload #13
    //   937: invokevirtual connect : ()V
    //   940: aload #13
    //   942: sipush #26566
    //   945: ldc2_w 3491182344802390599
    //   948: lload_1
    //   949: lxor
    //   950: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   955: invokevirtual getHeaderField : (Ljava/lang/String;)Ljava/lang/String;
    //   958: astore #16
    //   960: aload #16
    //   962: sipush #11914
    //   965: ldc2_w 5187661356224551722
    //   968: lload_1
    //   969: lxor
    //   970: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   975: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   978: iconst_1
    //   979: aaload
    //   980: astore #17
    //   982: new java/lang/String
    //   985: dup
    //   986: invokestatic getDecoder : ()Ljava/util/Base64$Decoder;
    //   989: aload #17
    //   991: invokevirtual decode : (Ljava/lang/String;)[B
    //   994: getstatic java/nio/charset/StandardCharsets.UTF_8 : Ljava/nio/charset/Charset;
    //   997: invokespecial <init> : ([BLjava/nio/charset/Charset;)V
    //   1000: sipush #4665
    //   1003: ldc2_w 5948731622211430273
    //   1006: lload_1
    //   1007: lxor
    //   1008: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1013: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   1016: iconst_1
    //   1017: aaload
    //   1018: astore #18
    //   1020: aload #18
    //   1022: sipush #13630
    //   1025: ldc2_w 3265220718663663783
    //   1028: lload_1
    //   1029: lxor
    //   1030: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1035: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   1038: iconst_1
    //   1039: aaload
    //   1040: ldc '"'
    //   1042: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   1045: iconst_0
    //   1046: aaload
    //   1047: astore #19
    //   1049: aload #18
    //   1051: sipush #26287
    //   1054: ldc2_w 1805217992408093460
    //   1057: lload_1
    //   1058: lxor
    //   1059: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1064: invokestatic quote : (Ljava/lang/String;)Ljava/lang/String;
    //   1067: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   1070: iconst_1
    //   1071: aaload
    //   1072: ldc '"'
    //   1074: invokevirtual split : (Ljava/lang/String;)[Ljava/lang/String;
    //   1077: iconst_0
    //   1078: aaload
    //   1079: astore #20
    //   1081: aload #20
    //   1083: aload #19
    //   1085: sipush #23635
    //   1088: ldc2_w 4774818740904225174
    //   1091: lload_1
    //   1092: lxor
    //   1093: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1098: dup_x2
    //   1099: pop
    //   1100: ldc ';'
    //   1102: swap
    //   1103: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1108: astore #21
    //   1110: getstatic wtf/opal/bx.j : Lcom/google/gson/Gson;
    //   1113: lload #6
    //   1115: sipush #2450
    //   1118: ldc2_w 950846979581426773
    //   1121: lload_1
    //   1122: lxor
    //   1123: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1128: aload #21
    //   1130: sipush #31366
    //   1133: ldc2_w 7047463998107078468
    //   1136: lload_1
    //   1137: lxor
    //   1138: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1143: swap
    //   1144: sipush #21150
    //   1147: ldc2_w 1689341273421899631
    //   1150: lload_1
    //   1151: lxor
    //   1152: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1157: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1162: iconst_1
    //   1163: iconst_4
    //   1164: anewarray java/lang/Object
    //   1167: dup_x1
    //   1168: swap
    //   1169: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1172: iconst_3
    //   1173: swap
    //   1174: aastore
    //   1175: dup_x1
    //   1176: swap
    //   1177: iconst_2
    //   1178: swap
    //   1179: aastore
    //   1180: dup_x1
    //   1181: swap
    //   1182: iconst_1
    //   1183: swap
    //   1184: aastore
    //   1185: dup_x2
    //   1186: dup_x2
    //   1187: pop
    //   1188: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1191: iconst_0
    //   1192: swap
    //   1193: aastore
    //   1194: invokestatic e : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1197: ldc_w wtf/opal/pd
    //   1200: invokevirtual fromJson : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   1203: checkcast wtf/opal/pd
    //   1206: astore #22
    //   1208: aload #22
    //   1210: aload #10
    //   1212: ifnonnull -> 1299
    //   1215: ifnonnull -> 1242
    //   1218: goto -> 1225
    //   1221: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   1224: athrow
    //   1225: sipush #3857
    //   1228: ldc2_w 9099840712081423004
    //   1231: lload_1
    //   1232: lxor
    //   1233: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1238: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   1241: return
    //   1242: getstatic wtf/opal/bx.j : Lcom/google/gson/Gson;
    //   1245: sipush #26099
    //   1248: ldc2_w 5310459437710790766
    //   1251: lload_1
    //   1252: lxor
    //   1253: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1258: aload #22
    //   1260: getfield L : Ljava/lang/String;
    //   1263: lload #8
    //   1265: dup2_x1
    //   1266: pop2
    //   1267: iconst_3
    //   1268: anewarray java/lang/Object
    //   1271: dup_x1
    //   1272: swap
    //   1273: iconst_2
    //   1274: swap
    //   1275: aastore
    //   1276: dup_x2
    //   1277: dup_x2
    //   1278: pop
    //   1279: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1282: iconst_1
    //   1283: swap
    //   1284: aastore
    //   1285: dup_x1
    //   1286: swap
    //   1287: iconst_0
    //   1288: swap
    //   1289: aastore
    //   1290: invokestatic t : ([Ljava/lang/Object;)Ljava/lang/String;
    //   1293: ldc_w wtf/opal/pf
    //   1296: invokevirtual fromJson : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   1299: checkcast wtf/opal/pf
    //   1302: astore #23
    //   1304: aload #10
    //   1306: ifnonnull -> 1370
    //   1309: aload #23
    //   1311: ifnull -> 1354
    //   1314: goto -> 1321
    //   1317: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   1320: athrow
    //   1321: lload_1
    //   1322: lconst_0
    //   1323: lcmp
    //   1324: iflt -> 1370
    //   1327: aload #23
    //   1329: getfield z : Ljava/lang/String;
    //   1332: aload #10
    //   1334: ifnonnull -> 1367
    //   1337: goto -> 1344
    //   1340: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   1343: athrow
    //   1344: ifnonnull -> 1371
    //   1347: goto -> 1354
    //   1350: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   1353: athrow
    //   1354: sipush #3724
    //   1357: ldc2_w 688837627622079274
    //   1360: lload_1
    //   1361: lxor
    //   1362: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1367: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   1370: return
    //   1371: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1374: checkcast wtf/opal/mixin/MinecraftClientAccessor
    //   1377: astore #24
    //   1379: aload #24
    //   1381: new net/minecraft/class_320
    //   1384: dup
    //   1385: aload #23
    //   1387: getfield C : Ljava/lang/String;
    //   1390: aload #23
    //   1392: getfield z : Ljava/lang/String;
    //   1395: lload #4
    //   1397: dup2_x1
    //   1398: pop2
    //   1399: iconst_2
    //   1400: anewarray java/lang/Object
    //   1403: dup_x1
    //   1404: swap
    //   1405: iconst_1
    //   1406: swap
    //   1407: aastore
    //   1408: dup_x2
    //   1409: dup_x2
    //   1410: pop
    //   1411: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1414: iconst_0
    //   1415: swap
    //   1416: aastore
    //   1417: invokestatic y : ([Ljava/lang/Object;)Ljava/util/UUID;
    //   1420: aload #22
    //   1422: getfield L : Ljava/lang/String;
    //   1425: invokestatic empty : ()Ljava/util/Optional;
    //   1428: invokestatic empty : ()Ljava/util/Optional;
    //   1431: getstatic net/minecraft/class_320$class_321.field_34962 : Lnet/minecraft/class_320$class_321;
    //   1434: invokespecial <init> : (Ljava/lang/String;Ljava/util/UUID;Ljava/lang/String;Ljava/util/Optional;Ljava/util/Optional;Lnet/minecraft/class_320$class_321;)V
    //   1437: invokeinterface setSession : (Lnet/minecraft/class_320;)V
    //   1442: aload #23
    //   1444: getfield C : Ljava/lang/String;
    //   1447: sipush #28597
    //   1450: ldc2_w 231862677509691983
    //   1453: lload_1
    //   1454: lxor
    //   1455: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1460: swap
    //   1461: ldc_w '!'
    //   1464: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1469: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   1472: goto -> 1498
    //   1475: astore #11
    //   1477: sipush #20078
    //   1480: ldc2_w 7532731122734758855
    //   1483: lload_1
    //   1484: lxor
    //   1485: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1490: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   1493: aload #11
    //   1495: invokevirtual printStackTrace : ()V
    //   1498: return
    // Exception table:
    //   from	to	target	type
    //   53	1241	1475	java/lang/Exception
    //   101	123	126	java/lang/Exception
    //   112	147	150	java/lang/Exception
    //   130	167	170	java/lang/Exception
    //   154	190	193	java/lang/Exception
    //   174	271	274	java/lang/Exception
    //   1208	1218	1221	java/lang/Exception
    //   1242	1370	1475	java/lang/Exception
    //   1304	1314	1317	java/lang/Exception
    //   1309	1337	1340	java/lang/Exception
    //   1321	1347	1350	java/lang/Exception
    //   1371	1472	1475	java/lang/Exception
  }
  
  public static MicrosoftAuthResult l(Object[] paramArrayOfObject) throws Exception {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/util/function/Consumer
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/bx.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: invokestatic X : ()[I
    //   28: aload_3
    //   29: putstatic wtf/opal/bx.U : Ljava/util/function/Consumer;
    //   32: astore #4
    //   34: new fr/litarvan/openauth/microsoft/HttpClient
    //   37: dup
    //   38: invokespecial <init> : ()V
    //   41: astore #5
    //   43: sipush #319
    //   46: ldc2_w 8755387280797301908
    //   49: lload_1
    //   50: lxor
    //   51: <illegal opcode> v : (IJ)I
    //   56: sipush #27240
    //   59: ldc2_w 2738798840694282182
    //   62: lload_1
    //   63: lxor
    //   64: <illegal opcode> v : (IJ)I
    //   69: invokestatic nextInt : (II)I
    //   72: istore #6
    //   74: new java/net/InetSocketAddress
    //   77: dup
    //   78: iload #6
    //   80: invokespecial <init> : (I)V
    //   83: iconst_0
    //   84: invokestatic create : (Ljava/net/InetSocketAddress;I)Lcom/sun/net/httpserver/HttpServer;
    //   87: astore #7
    //   89: iload #6
    //   91: sipush #8479
    //   94: ldc2_w 7314398662342444929
    //   97: lload_1
    //   98: lxor
    //   99: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   104: swap
    //   105: sipush #20796
    //   108: ldc2_w 1528059019852871602
    //   111: lload_1
    //   112: lxor
    //   113: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   118: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;ILjava/lang/String;)Ljava/lang/String;
    //   123: astore #8
    //   125: invokestatic method_668 : ()Lnet/minecraft/class_156$class_158;
    //   128: aload #8
    //   130: invokevirtual method_670 : (Ljava/lang/String;)V
    //   133: aload #7
    //   135: ldc_w '/'
    //   138: iload #6
    //   140: aload #5
    //   142: aload #7
    //   144: <illegal opcode> handle : (ILfr/litarvan/openauth/microsoft/HttpClient;Lcom/sun/net/httpserver/HttpServer;)Lcom/sun/net/httpserver/HttpHandler;
    //   149: invokevirtual createContext : (Ljava/lang/String;Lcom/sun/net/httpserver/HttpHandler;)Lcom/sun/net/httpserver/HttpContext;
    //   152: pop
    //   153: aload #7
    //   155: aconst_null
    //   156: invokevirtual setExecutor : (Ljava/util/concurrent/Executor;)V
    //   159: aload #7
    //   161: invokevirtual start : ()V
    //   164: getstatic wtf/opal/bx.k : Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   167: aload #4
    //   169: ifnull -> 186
    //   172: iconst_5
    //   173: anewarray wtf/opal/d
    //   176: invokestatic p : ([Lwtf/opal/d;)V
    //   179: goto -> 186
    //   182: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   185: athrow
    //   186: areturn
    // Exception table:
    //   from	to	target	type
    //   125	179	182	java/lang/Exception
  }
  
  public static String e(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore #4
    //   12: dup
    //   13: iconst_1
    //   14: aaload
    //   15: checkcast java/lang/String
    //   18: astore_3
    //   19: dup
    //   20: iconst_2
    //   21: aaload
    //   22: checkcast java/lang/String
    //   25: astore_2
    //   26: dup
    //   27: iconst_3
    //   28: aaload
    //   29: checkcast java/lang/Boolean
    //   32: invokevirtual booleanValue : ()Z
    //   35: istore_1
    //   36: pop
    //   37: getstatic wtf/opal/bx.a : J
    //   40: lload #4
    //   42: lxor
    //   43: lstore #4
    //   45: invokestatic X : ()[I
    //   48: astore #6
    //   50: new java/net/URL
    //   53: dup
    //   54: aload_3
    //   55: invokespecial <init> : (Ljava/lang/String;)V
    //   58: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   61: checkcast java/net/HttpURLConnection
    //   64: astore #7
    //   66: aload #7
    //   68: sipush #9287
    //   71: ldc2_w 2744201056597435226
    //   74: lload #4
    //   76: lxor
    //   77: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   82: sipush #10997
    //   85: ldc2_w 5800215401046666630
    //   88: lload #4
    //   90: lxor
    //   91: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   96: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   99: aload #7
    //   101: sipush #19997
    //   104: ldc2_w 2228430805289765197
    //   107: lload #4
    //   109: lxor
    //   110: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   115: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   118: aload #7
    //   120: iconst_1
    //   121: invokevirtual setDoOutput : (Z)V
    //   124: aload_2
    //   125: getstatic java/nio/charset/StandardCharsets.UTF_8 : Ljava/nio/charset/Charset;
    //   128: invokevirtual getBytes : (Ljava/nio/charset/Charset;)[B
    //   131: astore #8
    //   133: aload #8
    //   135: arraylength
    //   136: istore #9
    //   138: aload #7
    //   140: iload #9
    //   142: invokevirtual setFixedLengthStreamingMode : (I)V
    //   145: aload #7
    //   147: sipush #32440
    //   150: ldc2_w 5824044779243026849
    //   153: lload #4
    //   155: lxor
    //   156: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   161: iload_1
    //   162: ifeq -> 186
    //   165: sipush #2186
    //   168: ldc2_w 3252822991162789778
    //   171: lload #4
    //   173: lxor
    //   174: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   179: goto -> 200
    //   182: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   185: athrow
    //   186: sipush #17245
    //   189: ldc2_w 7482351476185171044
    //   192: lload #4
    //   194: lxor
    //   195: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   200: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   203: aload #7
    //   205: sipush #6032
    //   208: ldc2_w 425592409906139344
    //   211: lload #4
    //   213: lxor
    //   214: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   219: sipush #2186
    //   222: ldc2_w 3252822991162789778
    //   225: lload #4
    //   227: lxor
    //   228: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   233: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   236: aload #7
    //   238: invokevirtual connect : ()V
    //   241: aload #7
    //   243: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
    //   246: astore #10
    //   248: aload #10
    //   250: aload #8
    //   252: invokevirtual write : ([B)V
    //   255: lload #4
    //   257: lconst_0
    //   258: lcmp
    //   259: ifle -> 277
    //   262: aload #10
    //   264: aload #6
    //   266: ifnonnull -> 274
    //   269: ifnull -> 326
    //   272: aload #10
    //   274: invokevirtual close : ()V
    //   277: goto -> 326
    //   280: astore #11
    //   282: lload #4
    //   284: lconst_0
    //   285: lcmp
    //   286: iflt -> 311
    //   289: aload #10
    //   291: aload #6
    //   293: ifnonnull -> 308
    //   296: ifnull -> 323
    //   299: goto -> 306
    //   302: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   305: athrow
    //   306: aload #10
    //   308: invokevirtual close : ()V
    //   311: goto -> 323
    //   314: astore #12
    //   316: aload #11
    //   318: aload #12
    //   320: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   323: aload #11
    //   325: athrow
    //   326: aload #7
    //   328: invokevirtual getResponseCode : ()I
    //   331: istore #10
    //   333: iload #10
    //   335: sipush #27717
    //   338: ldc2_w 8534545564316692512
    //   341: lload #4
    //   343: lxor
    //   344: <illegal opcode> v : (IJ)I
    //   349: idiv
    //   350: iconst_2
    //   351: lload #4
    //   353: lconst_0
    //   354: lcmp
    //   355: iflt -> 398
    //   358: aload #6
    //   360: ifnonnull -> 398
    //   363: if_icmpeq -> 401
    //   366: goto -> 373
    //   369: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   372: athrow
    //   373: iload #10
    //   375: sipush #32216
    //   378: ldc2_w 5274176388909719996
    //   381: lload #4
    //   383: lxor
    //   384: <illegal opcode> v : (IJ)I
    //   389: idiv
    //   390: iconst_3
    //   391: goto -> 398
    //   394: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   397: athrow
    //   398: if_icmpne -> 413
    //   401: aload #7
    //   403: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   406: goto -> 418
    //   409: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   412: athrow
    //   413: aload #7
    //   415: invokevirtual getErrorStream : ()Ljava/io/InputStream;
    //   418: astore #11
    //   420: aload #6
    //   422: ifnonnull -> 473
    //   425: aload #11
    //   427: ifnonnull -> 475
    //   430: goto -> 437
    //   433: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   436: athrow
    //   437: getstatic java/lang/System.err : Ljava/io/PrintStream;
    //   440: iload #10
    //   442: aload_3
    //   443: sipush #18618
    //   446: ldc2_w 3304846183053725687
    //   449: lload #4
    //   451: lxor
    //   452: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   457: swap
    //   458: <illegal opcode> makeConcatWithConstants : (ILjava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   463: invokevirtual println : (Ljava/lang/String;)V
    //   466: goto -> 473
    //   469: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   472: athrow
    //   473: aconst_null
    //   474: areturn
    //   475: new java/io/BufferedReader
    //   478: dup
    //   479: new java/io/InputStreamReader
    //   482: dup
    //   483: aload #11
    //   485: invokespecial <init> : (Ljava/io/InputStream;)V
    //   488: invokespecial <init> : (Ljava/io/Reader;)V
    //   491: astore #12
    //   493: new java/lang/StringBuilder
    //   496: dup
    //   497: invokespecial <init> : ()V
    //   500: astore #14
    //   502: aload #12
    //   504: invokevirtual readLine : ()Ljava/lang/String;
    //   507: dup
    //   508: astore #13
    //   510: ifnull -> 552
    //   513: aload #14
    //   515: aload #13
    //   517: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   520: lload #4
    //   522: lconst_0
    //   523: lcmp
    //   524: iflt -> 559
    //   527: pop
    //   528: aload #6
    //   530: ifnonnull -> 557
    //   533: aload #6
    //   535: ifnull -> 502
    //   538: lload #4
    //   540: lconst_0
    //   541: lcmp
    //   542: ifle -> 528
    //   545: goto -> 552
    //   548: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   551: athrow
    //   552: aload #12
    //   554: invokevirtual close : ()V
    //   557: aload #14
    //   559: invokevirtual toString : ()Ljava/lang/String;
    //   562: areturn
    //   563: astore #7
    //   565: aload #7
    //   567: invokevirtual printStackTrace : ()V
    //   570: aconst_null
    //   571: areturn
    // Exception table:
    //   from	to	target	type
    //   50	474	563	java/lang/Exception
    //   138	182	182	java/lang/Throwable
    //   248	255	280	java/lang/Throwable
    //   282	299	302	java/lang/Throwable
    //   306	311	314	java/lang/Throwable
    //   333	366	369	java/lang/Throwable
    //   363	391	394	java/lang/Throwable
    //   398	409	409	java/lang/Throwable
    //   420	430	433	java/lang/Throwable
    //   425	466	469	java/lang/Throwable
    //   475	562	563	java/lang/Exception
    //   513	538	548	java/lang/Throwable
  }
  
  private static Map Y(Object[] paramArrayOfObject) throws JSONException {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast org/json/JSONObject
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/bx.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 56761003111600
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 117722739976078
    //   37: lxor
    //   38: lstore #6
    //   40: pop2
    //   41: new java/util/HashMap
    //   44: dup
    //   45: invokespecial <init> : ()V
    //   48: astore #9
    //   50: invokestatic X : ()[I
    //   53: aload_3
    //   54: invokevirtual keys : ()Ljava/util/Iterator;
    //   57: astore #10
    //   59: astore #8
    //   61: aload #10
    //   63: invokeinterface hasNext : ()Z
    //   68: ifeq -> 232
    //   71: aload #10
    //   73: invokeinterface next : ()Ljava/lang/Object;
    //   78: checkcast java/lang/String
    //   81: astore #11
    //   83: aload_3
    //   84: aload #11
    //   86: invokevirtual get : (Ljava/lang/String;)Ljava/lang/Object;
    //   89: astore #12
    //   91: aload #12
    //   93: instanceof org/json/JSONArray
    //   96: aload #8
    //   98: ifnonnull -> 182
    //   101: ifeq -> 152
    //   104: goto -> 111
    //   107: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   110: athrow
    //   111: lload #4
    //   113: aload #12
    //   115: checkcast org/json/JSONArray
    //   118: iconst_2
    //   119: anewarray java/lang/Object
    //   122: dup_x1
    //   123: swap
    //   124: iconst_1
    //   125: swap
    //   126: aastore
    //   127: dup_x2
    //   128: dup_x2
    //   129: pop
    //   130: invokestatic valueOf : (J)Ljava/lang/Long;
    //   133: iconst_0
    //   134: swap
    //   135: aastore
    //   136: invokestatic h : ([Ljava/lang/Object;)Ljava/util/List;
    //   139: astore #12
    //   141: aload #8
    //   143: lload_1
    //   144: lconst_0
    //   145: lcmp
    //   146: iflt -> 160
    //   149: ifnull -> 215
    //   152: lload_1
    //   153: lconst_0
    //   154: lcmp
    //   155: ifle -> 227
    //   158: aload #12
    //   160: aload #8
    //   162: ifnonnull -> 226
    //   165: goto -> 172
    //   168: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   171: athrow
    //   172: instanceof org/json/JSONObject
    //   175: goto -> 182
    //   178: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   181: athrow
    //   182: ifeq -> 215
    //   185: lload #6
    //   187: aload #12
    //   189: checkcast org/json/JSONObject
    //   192: iconst_2
    //   193: anewarray java/lang/Object
    //   196: dup_x1
    //   197: swap
    //   198: iconst_1
    //   199: swap
    //   200: aastore
    //   201: dup_x2
    //   202: dup_x2
    //   203: pop
    //   204: invokestatic valueOf : (J)Ljava/lang/Long;
    //   207: iconst_0
    //   208: swap
    //   209: aastore
    //   210: invokestatic Y : ([Ljava/lang/Object;)Ljava/util/Map;
    //   213: astore #12
    //   215: aload #9
    //   217: aload #11
    //   219: aload #12
    //   221: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   226: pop
    //   227: aload #8
    //   229: ifnull -> 61
    //   232: aload #9
    //   234: lload_1
    //   235: lconst_0
    //   236: lcmp
    //   237: ifle -> 78
    //   240: areturn
    // Exception table:
    //   from	to	target	type
    //   91	104	107	org/json/JSONException
    //   141	165	168	org/json/JSONException
    //   152	175	178	org/json/JSONException
  }
  
  private static List h(Object[] paramArrayOfObject) throws JSONException {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_1
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast org/json/JSONArray
    //   17: astore_3
    //   18: pop
    //   19: getstatic wtf/opal/bx.a : J
    //   22: lload_1
    //   23: lxor
    //   24: lstore_1
    //   25: lload_1
    //   26: dup2
    //   27: ldc2_w 117722739976078
    //   30: lxor
    //   31: lstore #4
    //   33: dup2
    //   34: ldc2_w 56761003111600
    //   37: lxor
    //   38: lstore #6
    //   40: pop2
    //   41: invokestatic X : ()[I
    //   44: new java/util/ArrayList
    //   47: dup
    //   48: invokespecial <init> : ()V
    //   51: astore #9
    //   53: iconst_0
    //   54: istore #10
    //   56: astore #8
    //   58: iload #10
    //   60: aload_3
    //   61: invokevirtual length : ()I
    //   64: if_icmpge -> 217
    //   67: aload_3
    //   68: iload #10
    //   70: invokevirtual get : (I)Ljava/lang/Object;
    //   73: astore #11
    //   75: aload #11
    //   77: instanceof org/json/JSONArray
    //   80: aload #8
    //   82: lload_1
    //   83: lconst_0
    //   84: lcmp
    //   85: iflt -> 156
    //   88: ifnonnull -> 154
    //   91: ifeq -> 142
    //   94: goto -> 101
    //   97: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   100: athrow
    //   101: lload #4
    //   103: aload #11
    //   105: checkcast org/json/JSONArray
    //   108: iconst_2
    //   109: anewarray java/lang/Object
    //   112: dup_x1
    //   113: swap
    //   114: iconst_1
    //   115: swap
    //   116: aastore
    //   117: dup_x2
    //   118: dup_x2
    //   119: pop
    //   120: invokestatic valueOf : (J)Ljava/lang/Long;
    //   123: iconst_0
    //   124: swap
    //   125: aastore
    //   126: invokestatic h : ([Ljava/lang/Object;)Ljava/util/List;
    //   129: astore #11
    //   131: aload #8
    //   133: lload_1
    //   134: lconst_0
    //   135: lcmp
    //   136: iflt -> 144
    //   139: ifnull -> 199
    //   142: aload #11
    //   144: instanceof org/json/JSONObject
    //   147: goto -> 154
    //   150: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   153: athrow
    //   154: aload #8
    //   156: ifnonnull -> 208
    //   159: ifeq -> 199
    //   162: goto -> 169
    //   165: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   168: athrow
    //   169: lload #6
    //   171: aload #11
    //   173: checkcast org/json/JSONObject
    //   176: iconst_2
    //   177: anewarray java/lang/Object
    //   180: dup_x1
    //   181: swap
    //   182: iconst_1
    //   183: swap
    //   184: aastore
    //   185: dup_x2
    //   186: dup_x2
    //   187: pop
    //   188: invokestatic valueOf : (J)Ljava/lang/Long;
    //   191: iconst_0
    //   192: swap
    //   193: aastore
    //   194: invokestatic Y : ([Ljava/lang/Object;)Ljava/util/Map;
    //   197: astore #11
    //   199: aload #9
    //   201: aload #11
    //   203: invokeinterface add : (Ljava/lang/Object;)Z
    //   208: pop
    //   209: iinc #10, 1
    //   212: aload #8
    //   214: ifnull -> 58
    //   217: aload #9
    //   219: lload_1
    //   220: lconst_0
    //   221: lcmp
    //   222: iflt -> 73
    //   225: areturn
    // Exception table:
    //   from	to	target	type
    //   75	94	97	org/json/JSONException
    //   131	147	150	org/json/JSONException
    //   154	162	165	org/json/JSONException
  }
  
  public static String t(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/String
    //   7: astore_1
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_3
    //   18: dup
    //   19: iconst_2
    //   20: aaload
    //   21: checkcast java/lang/String
    //   24: astore_2
    //   25: pop
    //   26: getstatic wtf/opal/bx.a : J
    //   29: lload_3
    //   30: lxor
    //   31: lstore_3
    //   32: invokestatic X : ()[I
    //   35: astore #5
    //   37: new java/net/URL
    //   40: dup
    //   41: aload_1
    //   42: invokespecial <init> : (Ljava/lang/String;)V
    //   45: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   48: checkcast javax/net/ssl/HttpsURLConnection
    //   51: astore #6
    //   53: aload #6
    //   55: sipush #16619
    //   58: ldc2_w 8917581342126264301
    //   61: lload_3
    //   62: lxor
    //   63: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   68: sipush #9816
    //   71: ldc2_w 4137513226237978916
    //   74: lload_3
    //   75: lxor
    //   76: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   81: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   84: aload #6
    //   86: sipush #9283
    //   89: ldc2_w 6480397067873856348
    //   92: lload_3
    //   93: lxor
    //   94: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   99: sipush #2186
    //   102: ldc2_w 3252741238491318211
    //   105: lload_3
    //   106: lxor
    //   107: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   112: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   115: aload #6
    //   117: sipush #17872
    //   120: ldc2_w 7263169506704837271
    //   123: lload_3
    //   124: lxor
    //   125: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   130: aload_2
    //   131: sipush #10467
    //   134: ldc2_w 5102089432888649699
    //   137: lload_3
    //   138: lxor
    //   139: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   144: swap
    //   145: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   150: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   153: aload #6
    //   155: invokevirtual getResponseCode : ()I
    //   158: sipush #29096
    //   161: ldc2_w 3455936485971240345
    //   164: lload_3
    //   165: lxor
    //   166: <illegal opcode> v : (IJ)I
    //   171: if_icmpne -> 258
    //   174: new java/io/BufferedReader
    //   177: dup
    //   178: new java/io/InputStreamReader
    //   181: dup
    //   182: aload #6
    //   184: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   187: invokespecial <init> : (Ljava/io/InputStream;)V
    //   190: invokespecial <init> : (Ljava/io/Reader;)V
    //   193: astore #7
    //   195: new java/lang/StringBuilder
    //   198: dup
    //   199: invokespecial <init> : ()V
    //   202: astore #9
    //   204: aload #7
    //   206: invokevirtual readLine : ()Ljava/lang/String;
    //   209: dup
    //   210: astore #8
    //   212: ifnull -> 252
    //   215: lload_3
    //   216: lconst_0
    //   217: lcmp
    //   218: ifle -> 234
    //   221: aload #9
    //   223: aload #8
    //   225: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   228: aload #5
    //   230: ifnonnull -> 254
    //   233: pop
    //   234: aload #5
    //   236: ifnull -> 204
    //   239: lload_3
    //   240: lconst_0
    //   241: lcmp
    //   242: iflt -> 215
    //   245: goto -> 252
    //   248: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   251: athrow
    //   252: aload #9
    //   254: invokevirtual toString : ()Ljava/lang/String;
    //   257: areturn
    //   258: new java/io/BufferedReader
    //   261: dup
    //   262: new java/io/InputStreamReader
    //   265: dup
    //   266: aload #6
    //   268: invokevirtual getErrorStream : ()Ljava/io/InputStream;
    //   271: invokespecial <init> : (Ljava/io/InputStream;)V
    //   274: invokespecial <init> : (Ljava/io/Reader;)V
    //   277: astore #7
    //   279: new java/lang/StringBuilder
    //   282: dup
    //   283: invokespecial <init> : ()V
    //   286: astore #9
    //   288: aload #7
    //   290: invokevirtual readLine : ()Ljava/lang/String;
    //   293: dup
    //   294: astore #8
    //   296: ifnull -> 336
    //   299: lload_3
    //   300: lconst_0
    //   301: lcmp
    //   302: iflt -> 318
    //   305: aload #9
    //   307: aload #8
    //   309: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   312: aload #5
    //   314: ifnonnull -> 338
    //   317: pop
    //   318: aload #5
    //   320: ifnull -> 288
    //   323: lload_3
    //   324: lconst_0
    //   325: lcmp
    //   326: ifle -> 299
    //   329: goto -> 336
    //   332: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   335: athrow
    //   336: aload #9
    //   338: invokevirtual toString : ()Ljava/lang/String;
    //   341: areturn
    //   342: astore #6
    //   344: aconst_null
    //   345: areturn
    // Exception table:
    //   from	to	target	type
    //   37	257	342	java/lang/Exception
    //   215	239	248	java/lang/Exception
    //   258	341	342	java/lang/Exception
    //   299	323	332	java/lang/Exception
  }
  
  private static UUID y(Object[] paramArrayOfObject) throws JSONException {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: dup
    //   12: iconst_1
    //   13: aaload
    //   14: checkcast java/lang/String
    //   17: astore_1
    //   18: pop
    //   19: getstatic wtf/opal/bx.a : J
    //   22: lload_2
    //   23: lxor
    //   24: lstore_2
    //   25: aload_1
    //   26: sipush #23719
    //   29: ldc2_w 2971511926596357014
    //   32: lload_2
    //   33: lxor
    //   34: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   39: sipush #6561
    //   42: ldc2_w 9120353432865312394
    //   45: lload_2
    //   46: lxor
    //   47: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   52: invokevirtual replaceFirst : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   55: astore #4
    //   57: aload #4
    //   59: invokestatic fromString : (Ljava/lang/String;)Ljava/util/UUID;
    //   62: areturn
    //   63: astore #4
    //   65: new org/json/JSONException
    //   68: dup
    //   69: sipush #4762
    //   72: ldc2_w 1315233415585859050
    //   75: lload_2
    //   76: lxor
    //   77: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   82: invokespecial <init> : (Ljava/lang/String;)V
    //   85: athrow
    // Exception table:
    //   from	to	target	type
    //   25	62	63	java/lang/IllegalArgumentException
  }
  
  private static void lambda$getAccessToken$0(int paramInt, HttpClient paramHttpClient, HttpServer paramHttpServer, HttpExchange paramHttpExchange) throws IOException {
    // Byte code:
    //   0: getstatic wtf/opal/bx.a : J
    //   3: ldc2_w 89780547171520
    //   6: lxor
    //   7: lstore #4
    //   9: lload #4
    //   11: dup2
    //   12: ldc2_w 86338874470655
    //   15: lxor
    //   16: lstore #6
    //   18: dup2
    //   19: ldc2_w 82139220807280
    //   22: lxor
    //   23: lstore #8
    //   25: pop2
    //   26: invokestatic X : ()[I
    //   29: aload_3
    //   30: invokevirtual getRequestURI : ()Ljava/net/URI;
    //   33: invokevirtual toString : ()Ljava/lang/String;
    //   36: astore #11
    //   38: astore #10
    //   40: aload #11
    //   42: aload #11
    //   44: sipush #10779
    //   47: ldc2_w 2306243437310161276
    //   50: lload #4
    //   52: lxor
    //   53: <illegal opcode> v : (IJ)I
    //   58: invokevirtual lastIndexOf : (I)I
    //   61: iconst_1
    //   62: iadd
    //   63: invokevirtual substring : (I)Ljava/lang/String;
    //   66: astore #11
    //   68: new org/json/JSONObject
    //   71: dup
    //   72: invokespecial <init> : ()V
    //   75: astore #12
    //   77: aload #12
    //   79: sipush #30072
    //   82: ldc2_w 4465051972082644307
    //   85: lload #4
    //   87: lxor
    //   88: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   93: sipush #1838
    //   96: ldc2_w 687657359444789020
    //   99: lload #4
    //   101: lxor
    //   102: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   107: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   110: pop
    //   111: aload #12
    //   113: sipush #11702
    //   116: ldc2_w 4521897624696253931
    //   119: lload #4
    //   121: lxor
    //   122: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   127: aload #11
    //   129: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   132: pop
    //   133: aload #12
    //   135: sipush #593
    //   138: ldc2_w 3693895560875164261
    //   141: lload #4
    //   143: lxor
    //   144: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   149: sipush #21040
    //   152: ldc2_w 4690936650886353470
    //   155: lload #4
    //   157: lxor
    //   158: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   163: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   166: pop
    //   167: aload #12
    //   169: sipush #30504
    //   172: ldc2_w 6675571277526185762
    //   175: lload #4
    //   177: lxor
    //   178: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   183: sipush #11523
    //   186: ldc2_w 2049884202256067935
    //   189: lload #4
    //   191: lxor
    //   192: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   197: iconst_1
    //   198: anewarray java/lang/Object
    //   201: dup
    //   202: iconst_0
    //   203: iload_0
    //   204: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   207: aastore
    //   208: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   211: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   214: pop
    //   215: aload #12
    //   217: sipush #25051
    //   220: ldc2_w 5016671520656892304
    //   223: lload #4
    //   225: lxor
    //   226: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   231: sipush #14063
    //   234: ldc2_w 7751510139706078970
    //   237: lload #4
    //   239: lxor
    //   240: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   245: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   248: pop
    //   249: aload_1
    //   250: sipush #605
    //   253: ldc2_w 4780276104956887572
    //   256: lload #4
    //   258: lxor
    //   259: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   264: lload #8
    //   266: aload #12
    //   268: iconst_2
    //   269: anewarray java/lang/Object
    //   272: dup_x1
    //   273: swap
    //   274: iconst_1
    //   275: swap
    //   276: aastore
    //   277: dup_x2
    //   278: dup_x2
    //   279: pop
    //   280: invokestatic valueOf : (J)Ljava/lang/Long;
    //   283: iconst_0
    //   284: swap
    //   285: aastore
    //   286: invokestatic Y : ([Ljava/lang/Object;)Ljava/util/Map;
    //   289: sipush #5461
    //   292: ldc2_w 6004118567136220530
    //   295: lload #4
    //   297: lxor
    //   298: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   303: invokevirtual readPost : (Ljava/lang/String;Ljava/util/Map;Ljava/lang/String;)Ljava/lang/String;
    //   306: astore #13
    //   308: new org/json/JSONObject
    //   311: dup
    //   312: aload #13
    //   314: invokespecial <init> : (Ljava/lang/String;)V
    //   317: sipush #15108
    //   320: ldc2_w 6100927856382278490
    //   323: lload #4
    //   325: lxor
    //   326: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   331: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   334: astore #14
    //   336: new org/json/JSONObject
    //   339: dup
    //   340: invokespecial <init> : ()V
    //   343: astore #15
    //   345: aload #15
    //   347: sipush #165
    //   350: ldc2_w 5053704794129055934
    //   353: lload #4
    //   355: lxor
    //   356: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   361: sipush #28141
    //   364: ldc2_w 3924233787144514981
    //   367: lload #4
    //   369: lxor
    //   370: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   375: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   378: pop
    //   379: aload #15
    //   381: sipush #6092
    //   384: ldc2_w 565459180729751451
    //   387: lload #4
    //   389: lxor
    //   390: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   395: sipush #222
    //   398: ldc2_w 3115762640221159629
    //   401: lload #4
    //   403: lxor
    //   404: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   409: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   412: pop
    //   413: new org/json/JSONObject
    //   416: dup
    //   417: invokespecial <init> : ()V
    //   420: astore #16
    //   422: aload #16
    //   424: sipush #21053
    //   427: ldc2_w 2866342737762488888
    //   430: lload #4
    //   432: lxor
    //   433: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   438: aload #14
    //   440: sipush #17139
    //   443: ldc2_w 2536568327777649353
    //   446: lload #4
    //   448: lxor
    //   449: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   454: swap
    //   455: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   460: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   463: pop
    //   464: aload #16
    //   466: sipush #18015
    //   469: ldc2_w 2740403045567333998
    //   472: lload #4
    //   474: lxor
    //   475: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   480: sipush #8153
    //   483: ldc2_w 2520282838942506960
    //   486: lload #4
    //   488: lxor
    //   489: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   494: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   497: pop
    //   498: aload #16
    //   500: sipush #6529
    //   503: ldc2_w 4431506098201844105
    //   506: lload #4
    //   508: lxor
    //   509: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   514: sipush #1709
    //   517: ldc2_w 7488023530577872528
    //   520: lload #4
    //   522: lxor
    //   523: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   528: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   531: pop
    //   532: aload #15
    //   534: sipush #24171
    //   537: ldc2_w 1938234339835354676
    //   540: lload #4
    //   542: lxor
    //   543: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   548: aload #16
    //   550: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   553: pop
    //   554: new org/json/JSONObject
    //   557: dup
    //   558: aload_1
    //   559: sipush #1807
    //   562: ldc2_w 6596122488678172431
    //   565: lload #4
    //   567: lxor
    //   568: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   573: aload #15
    //   575: sipush #29758
    //   578: ldc2_w 7187554999908709440
    //   581: lload #4
    //   583: lxor
    //   584: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   589: sipush #2186
    //   592: ldc2_w 3252841849969625235
    //   595: lload #4
    //   597: lxor
    //   598: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   603: invokevirtual readPost : (Ljava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   606: invokespecial <init> : (Ljava/lang/String;)V
    //   609: astore #17
    //   611: aload #17
    //   613: sipush #5551
    //   616: ldc2_w 8506943040044750315
    //   619: lload #4
    //   621: lxor
    //   622: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   627: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   630: astore #18
    //   632: aload #17
    //   634: sipush #27365
    //   637: ldc2_w 2188105415837657832
    //   640: lload #4
    //   642: lxor
    //   643: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   648: invokevirtual getJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   651: sipush #7287
    //   654: ldc2_w 8818832230260628589
    //   657: lload #4
    //   659: lxor
    //   660: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   665: invokevirtual getJSONArray : (Ljava/lang/String;)Lorg/json/JSONArray;
    //   668: iconst_0
    //   669: invokevirtual getJSONObject : (I)Lorg/json/JSONObject;
    //   672: sipush #4748
    //   675: ldc2_w 4815618234663686819
    //   678: lload #4
    //   680: lxor
    //   681: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   686: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   689: astore #19
    //   691: new org/json/JSONObject
    //   694: dup
    //   695: invokespecial <init> : ()V
    //   698: astore #20
    //   700: aload #20
    //   702: sipush #8375
    //   705: ldc2_w 7015106047154905223
    //   708: lload #4
    //   710: lxor
    //   711: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   716: sipush #3891
    //   719: ldc2_w 6857425931995697000
    //   722: lload #4
    //   724: lxor
    //   725: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   730: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   733: pop
    //   734: aload #20
    //   736: sipush #16785
    //   739: ldc2_w 1419661421801734549
    //   742: lload #4
    //   744: lxor
    //   745: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   750: sipush #30145
    //   753: ldc2_w 3451784000044021127
    //   756: lload #4
    //   758: lxor
    //   759: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   764: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   767: pop
    //   768: new org/json/JSONObject
    //   771: dup
    //   772: invokespecial <init> : ()V
    //   775: astore #21
    //   777: aload #21
    //   779: sipush #31730
    //   782: ldc2_w 8992341745000856455
    //   785: lload #4
    //   787: lxor
    //   788: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   793: sipush #12363
    //   796: ldc2_w 5161044583283617864
    //   799: lload #4
    //   801: lxor
    //   802: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   807: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   810: pop
    //   811: aload #21
    //   813: sipush #12937
    //   816: ldc2_w 1199805469337875190
    //   819: lload #4
    //   821: lxor
    //   822: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   827: iconst_1
    //   828: anewarray java/lang/String
    //   831: dup
    //   832: iconst_0
    //   833: aload #18
    //   835: aastore
    //   836: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   839: pop
    //   840: aload #20
    //   842: sipush #29996
    //   845: ldc2_w 1321998074900034874
    //   848: lload #4
    //   850: lxor
    //   851: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   856: aload #21
    //   858: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   861: pop
    //   862: new org/json/JSONObject
    //   865: dup
    //   866: aload_1
    //   867: sipush #18083
    //   870: ldc2_w 5855916688897246872
    //   873: lload #4
    //   875: lxor
    //   876: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   881: aload #20
    //   883: sipush #2186
    //   886: ldc2_w 3252841849969625235
    //   889: lload #4
    //   891: lxor
    //   892: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   897: sipush #2186
    //   900: ldc2_w 3252841849969625235
    //   903: lload #4
    //   905: lxor
    //   906: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   911: invokevirtual readPost : (Ljava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   914: invokespecial <init> : (Ljava/lang/String;)V
    //   917: astore #22
    //   919: aload #22
    //   921: sipush #11869
    //   924: ldc2_w 238726020234145311
    //   927: lload #4
    //   929: lxor
    //   930: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   935: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   938: astore #23
    //   940: aload #22
    //   942: sipush #10911
    //   945: ldc2_w 4302407484625119942
    //   948: lload #4
    //   950: lxor
    //   951: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   956: invokevirtual getJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   959: sipush #13359
    //   962: ldc2_w 1684474649051788337
    //   965: lload #4
    //   967: lxor
    //   968: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   973: invokevirtual getJSONArray : (Ljava/lang/String;)Lorg/json/JSONArray;
    //   976: iconst_0
    //   977: invokevirtual getJSONObject : (I)Lorg/json/JSONObject;
    //   980: sipush #12049
    //   983: ldc2_w 4090803930501836635
    //   986: lload #4
    //   988: lxor
    //   989: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   994: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   997: astore #24
    //   999: new org/json/JSONObject
    //   1002: dup
    //   1003: invokespecial <init> : ()V
    //   1006: astore #25
    //   1008: aload #25
    //   1010: sipush #21875
    //   1013: ldc2_w 8604245387899082052
    //   1016: lload #4
    //   1018: lxor
    //   1019: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1024: sipush #6901
    //   1027: ldc2_w 5642747769602333317
    //   1030: lload #4
    //   1032: lxor
    //   1033: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1038: iconst_2
    //   1039: anewarray java/lang/Object
    //   1042: dup
    //   1043: iconst_0
    //   1044: aload #24
    //   1046: aastore
    //   1047: dup
    //   1048: iconst_1
    //   1049: aload #23
    //   1051: aastore
    //   1052: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1055: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   1058: pop
    //   1059: aload #25
    //   1061: sipush #10510
    //   1064: ldc2_w 3099010871866321190
    //   1067: lload #4
    //   1069: lxor
    //   1070: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1075: sipush #29590
    //   1078: ldc2_w 7241289004326171572
    //   1081: lload #4
    //   1083: lxor
    //   1084: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1089: invokevirtual put : (Ljava/lang/String;Ljava/lang/Object;)Lorg/json/JSONObject;
    //   1092: pop
    //   1093: new org/json/JSONObject
    //   1096: dup
    //   1097: aload_1
    //   1098: sipush #17473
    //   1101: ldc2_w 7480783373427901551
    //   1104: lload #4
    //   1106: lxor
    //   1107: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1112: aload #25
    //   1114: sipush #2186
    //   1117: ldc2_w 3252841849969625235
    //   1120: lload #4
    //   1122: lxor
    //   1123: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1128: invokevirtual readPost : (Ljava/lang/String;Lorg/json/JSONObject;Ljava/lang/String;)Ljava/lang/String;
    //   1131: invokespecial <init> : (Ljava/lang/String;)V
    //   1134: astore #26
    //   1136: aload_1
    //   1137: sipush #31451
    //   1140: ldc2_w 2503679387839150809
    //   1143: lload #4
    //   1145: lxor
    //   1146: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1151: aload #26
    //   1153: sipush #29453
    //   1156: ldc2_w 6330159181514978169
    //   1159: lload #4
    //   1161: lxor
    //   1162: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1167: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   1170: ldc_w fr/litarvan/openauth/microsoft/model/response/MinecraftProfile
    //   1173: invokevirtual getJson : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   1176: checkcast fr/litarvan/openauth/microsoft/model/response/MinecraftProfile
    //   1179: astore #27
    //   1181: aload #10
    //   1183: ifnonnull -> 1258
    //   1186: aload #27
    //   1188: ifnonnull -> 1216
    //   1191: goto -> 1198
    //   1194: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   1197: athrow
    //   1198: sipush #17134
    //   1201: ldc2_w 5006028480806257373
    //   1204: lload #4
    //   1206: lxor
    //   1207: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1212: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   1215: return
    //   1216: new fr/litarvan/openauth/microsoft/MicrosoftAuthResult
    //   1219: dup
    //   1220: aload #27
    //   1222: aload #26
    //   1224: sipush #29453
    //   1227: ldc2_w 6330159181514978169
    //   1230: lload #4
    //   1232: lxor
    //   1233: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1238: invokevirtual getString : (Ljava/lang/String;)Ljava/lang/String;
    //   1241: invokespecial <init> : (Lfr/litarvan/openauth/microsoft/model/response/MinecraftProfile;Ljava/lang/String;)V
    //   1244: putstatic wtf/opal/bx.k : Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   1247: getstatic wtf/opal/bx.U : Ljava/util/function/Consumer;
    //   1250: getstatic wtf/opal/bx.k : Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   1253: invokeinterface accept : (Ljava/lang/Object;)V
    //   1258: new net/minecraft/class_320
    //   1261: dup
    //   1262: getstatic wtf/opal/bx.k : Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   1265: invokevirtual getProfile : ()Lfr/litarvan/openauth/microsoft/model/response/MinecraftProfile;
    //   1268: invokevirtual getName : ()Ljava/lang/String;
    //   1271: getstatic wtf/opal/bx.k : Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   1274: invokevirtual getProfile : ()Lfr/litarvan/openauth/microsoft/model/response/MinecraftProfile;
    //   1277: invokevirtual getId : ()Ljava/lang/String;
    //   1280: lload #6
    //   1282: dup2_x1
    //   1283: pop2
    //   1284: iconst_2
    //   1285: anewarray java/lang/Object
    //   1288: dup_x1
    //   1289: swap
    //   1290: iconst_1
    //   1291: swap
    //   1292: aastore
    //   1293: dup_x2
    //   1294: dup_x2
    //   1295: pop
    //   1296: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1299: iconst_0
    //   1300: swap
    //   1301: aastore
    //   1302: invokestatic y : ([Ljava/lang/Object;)Ljava/util/UUID;
    //   1305: getstatic wtf/opal/bx.k : Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   1308: invokevirtual getAccessToken : ()Ljava/lang/String;
    //   1311: invokestatic empty : ()Ljava/util/Optional;
    //   1314: invokestatic empty : ()Ljava/util/Optional;
    //   1317: getstatic net/minecraft/class_320$class_321.field_34962 : Lnet/minecraft/class_320$class_321;
    //   1320: invokespecial <init> : (Ljava/lang/String;Ljava/util/UUID;Ljava/lang/String;Ljava/util/Optional;Ljava/util/Optional;Lnet/minecraft/class_320$class_321;)V
    //   1323: astore #28
    //   1325: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1328: checkcast wtf/opal/mixin/MinecraftClientAccessor
    //   1331: astore #29
    //   1333: aload #29
    //   1335: aload #28
    //   1337: invokeinterface setSession : (Lnet/minecraft/class_320;)V
    //   1342: getstatic wtf/opal/bx.k : Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   1345: invokevirtual getProfile : ()Lfr/litarvan/openauth/microsoft/model/response/MinecraftProfile;
    //   1348: invokevirtual getName : ()Ljava/lang/String;
    //   1351: sipush #7841
    //   1354: ldc2_w 8480510936441642625
    //   1357: lload #4
    //   1359: lxor
    //   1360: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1365: swap
    //   1366: ldc_w '!'
    //   1369: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1374: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   1377: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   1380: invokevirtual method_1548 : ()Lnet/minecraft/class_320;
    //   1383: invokevirtual method_1676 : ()Ljava/lang/String;
    //   1386: sipush #23016
    //   1389: ldc2_w 1804369776415414722
    //   1392: lload #4
    //   1394: lxor
    //   1395: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1400: swap
    //   1401: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   1406: astore #30
    //   1408: aload_3
    //   1409: sipush #5100
    //   1412: ldc2_w 2090616529994614913
    //   1415: lload #4
    //   1417: lxor
    //   1418: <illegal opcode> v : (IJ)I
    //   1423: aload #30
    //   1425: invokevirtual length : ()I
    //   1428: i2l
    //   1429: invokevirtual sendResponseHeaders : (IJ)V
    //   1432: aload_3
    //   1433: invokevirtual getResponseBody : ()Ljava/io/OutputStream;
    //   1436: astore #31
    //   1438: aload #31
    //   1440: aload #30
    //   1442: getstatic java/nio/charset/StandardCharsets.UTF_8 : Ljava/nio/charset/Charset;
    //   1445: invokevirtual getBytes : (Ljava/nio/charset/Charset;)[B
    //   1448: invokevirtual write : ([B)V
    //   1451: aload #31
    //   1453: invokevirtual close : ()V
    //   1456: aload_2
    //   1457: iconst_2
    //   1458: invokevirtual stop : (I)V
    //   1461: aconst_null
    //   1462: putstatic wtf/opal/bx.U : Ljava/util/function/Consumer;
    //   1465: goto -> 1501
    //   1468: astore #12
    //   1470: sipush #15246
    //   1473: aload #12
    //   1475: invokevirtual printStackTrace : ()V
    //   1478: ldc2_w 1976603410066930608
    //   1481: lload #4
    //   1483: lxor
    //   1484: aload_2
    //   1485: iconst_2
    //   1486: invokevirtual stop : (I)V
    //   1489: aconst_null
    //   1490: putstatic wtf/opal/bx.U : Ljava/util/function/Consumer;
    //   1493: <illegal opcode> s : (IJ)Ljava/lang/String;
    //   1498: putstatic wtf/opal/xq.Y : Ljava/lang/String;
    //   1501: return
    // Exception table:
    //   from	to	target	type
    //   68	1215	1468	java/lang/Exception
    //   1181	1191	1194	java/lang/Exception
    //   1216	1465	1468	java/lang/Exception
  }
  
  static {
    long l = a ^ 0x664117144743L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[106];
    boolean bool = false;
    String str;
    int i = (str = "á(|W=0Ö\024PÏÐ\002ý[ö¨4\f·\002ô\022e\027ãLPÜ\t\037~\030êñ\005¯t\031âsåÙÌp\0307ëpU0·Hä\017a&\034Kî$µà?:X«Ë9¿îfqG\032=Á,Z*G%3Í\036hÇøÔG³Ø)ähj¿äiÔ¨£lòâÊxüâ\035¾zÕóØ (H\036á\037õÆáÛ\022B#á9Û|zÑ![ha\034Õ¯\013ª\020H1Æ½5Æ­ÛzÙÊP°¹H»Xz~ÿ¬.YPìß¨Zû\005ÿ8´AeÓ'$ýEj`Ùä§Õë^÷cÞ>7«£zÂVÒã_¥ÛOF·èpF#ÉB\021ëmô MÄ¨ñZª½2¬sï~Dð²Õ\037'bÝÞÃ;Á$GÐxË¯§ÝãÛf\023eéõ¢\027\r\020\022ÿ¿Þ¡GX(värÈIlíGDÏ\020±CÿR\000\"BÑ\022Ç\016ô\007m?nà.m\0067\005\017BzæcðP³Yã~Ò@M¤\003â\021Ku°Ò#\nA\001\n~ªòÔ\034\030gXÐmÝ\035\032î`<ÅXwpÝï¿ø¨Å|7Ôz*»vr\np\005û3?-@\033Î[\027\023¿ã\024k<ÆäÑ}ëâ:\031ãØBë³oÇ¼ÚÐr4ÌÃí\021o\036×KfL~(\001ô[ü* ÓÁbÔîxÕ\030A-h#\027«et$`&$ëñ­Zç\020úåYà(\027¬\036Õ\nÏfÊ(yÐ*Øüù(\025`î\034±%\016x®ÜÚ\fãø\025\033Îf^Ï\027iØaZ¢ø× CpB?ï\037­öãf\t\016­\031G\0369D*ÒëB\023vÅSnï\030ÑÒî,û½g,F\nÿz.\025W)\017_£Kí\020£BæÈå¾ ÜËïVáý\tY(r.Gq\020¦îè½VøaÒ\\^è\022s2\037,Ó×\013JÔì-Wª´f|oÃ¬w\n)\030\032nª{0fÖËXI\t6D#^\026ÎëÙiå\020è:DøñY\fdµµâDÓÃĘµ\027@c\013âAäìßoÒ|géö¥×[ºJÔk·O¯C9\003Rw'¡/6\020¥f=·âMAH¸ÍF,YcNÒÛ·©x:\005ãõáºÙQCVü\017_é(®½¾kª hõ\030N\fÖûè³\000Ójl\036ô~ØlÂcdP%fj\013\021*táÖ\016\021ÐÉw>küªàÉ\fÖä¶sËØ$nÞyªm:t=úêÌ°\025&Sc  R~¥\013uYb\022L4Ùn«q\006 JÈ!Ì¹ð\024/Ò[öülE½gèCÈU{\026cØ}á¸[Êãx\022Áìùv\bÌ#åõÈÌª\017Ò[þ\033OW\020õ[ü­Æz\024%yN5OÌOkÞ!.:¿ò\034ïU!Æ8\027 + ¼[OÂu­.í\\\003Ü¡YO·'ßBn\022À>áóágB\030¤xÞsI G¾2 %F6G\006\006\036mQ¨³\020)dá©\032\"F\033Ú\bjú¤ÚXÞÅÛ÷þÕÿÍ¤ãðf×ÈÐ\002pCÞ\007Ù8zRýý\fî0Ð\022y@\017íÜÃäØ@e.zv«\026\0049.¦8{qèn\034ÄAì<HÈÇò)Jÿ\035Us¿\006Ã\037+D\030C¢ÉÙ-¶_ù§·æÀZà\027p}Xpå{à\000\b&2~/°Z/ÇÁv¸Ó£ê®¯Ev´ì\b$ÊD~î[\026ZíºÑ(#g\037±ÃcEÒ{]Jô\034´c9ÉÅJ\013pY\036ÿ\005ê;ÙüNúÂµÃpà¨\026ç@ô$>ãäò-2ìtý\024C­\bï\026^(©Eø\0337ó\025Å²æ¸åpH\035f\030¨\035üqNHbûê2ôÊÂ¬oÂCð\035fé(<Ub0XC·­SÈ{Ý\024Y+ÏÂaÔO ÑTÆ\033PöøÄ9¹\020è\030/¬9=\002^ÔX¥ç;l5Â^tðDLç\f\031@\000;Àq\021½5#Ë\030Ú®j4\000~`vµE\021öÓ±Ê·ØGÚ\034\bå¥)\020ím0<ø\025Ï´Û\004¯ ¬yÍÐ'¤öv­\020\020À\030}yÄ\0374×Æ90 Þfâ1\nw£©^lÙÞº§í§U¸\003Xv~â=>ª\022(Û¸ÕÈøÎfÛ\034¦ÞöO´.ò±®\027rN\021ã1v;Ìo-ø\020&â\020 ñ\004o?Ô\002\004\023ÿ\bµÍ\ráÐ¼7xW9\032(ÆMã\030\031\001;\036çº=ßªB­.÷©Ëcëth\005é°\030Ü Óº/G¹Ôø¨\005UÔeÐ<iIHècêÉ È°RhßòM\024 \016úm¥\"êECYIÈ|ºK\031Z\032Û¬Á~¡§\\¤ïrwÅ=\030üì×\001S?\004\f\003?é'¯e\027ØÃH±\025sj-\017MÒd2ÜÞq'\003ê]Æa\005M¦oZ'\006ÅÆéï\026î,<4§\024 ã\016õ\035à©9tx>°@iÊO\004ÂWLAÂÄ§ÅºbÂß@C\007\034´áÌ_W¤ÉX®»TîjÙ¨Þ,z¼\026\001$|Z\032cñ³Ò»UF@BòZ\025K´3ù\001\033\036à\024Éàn_\0369 nb\017íw×ÿ\003\023ük%\035ÖY3\013·qêÌÕ\030W;Y\037³ÔÊ\030<Ù\030ò\031á=²ß\017ï\020\031¥b\bÉ?=@ùÊe ïÖs\022Òí¶/ècaÉÚ\0220]±äG{\000¤\037¿ØRióx\t\005v5VÓ\032¹¬\026Ø\031):ÏJ Ó\013Æ?Xq0­Â¬ò°+¹sxic\036\bô\025kAÌ\017¹ìg­\036âþ\003«¬åbïþ8zrH-Jì÷x\"\020¿Iø \f\ní\t\013`¤»¦\020\nÕ¹Ð\020¨Û±`ûCP\023>é\007ÑEÕe\021D\027òq'\005áQ¬©°\\\023Ñõ!x¼t©\027k×;ó¾©\017Ô¼¾¾CòL}¦poëÏyÒ[#Zk\031\017Ìÿ*Ù\031\017\003ÁÈÏ$\020%ê\"\016Ö<è.aK\006jöÊñ(È>ú\016aÔb3äcK_LQ{ÿÍ1ð\033æïÁ>`;tHj´\r\032`d_Ø\021Ö³ó\036öö\020©jÈ\000\023àÀ7öÌpÑ·,Väêã\023ôìTR¨u\f\bmõIi;üÏc­qlvYp\nq]\032FÖçúV÷é×4à<qªäì¹>©=iÌ¡`Ä,/äE@ãfÏ:,o»Ö°²\fóþ\026pO >HjÈö§¢µ'k1Ô\034\f\023ï$¦qÏ9\034E`\\ÓÔïû%ð,\017<([RÈNF@Ú[(äJ8&K#­\006R©q\024\024Çä½¾%å±a\032½ÇÓ\031óB¬\fµÉ®ý¾ÌÒKÓ'ÞOì «ÜMw¡þÈzkÉ\"\030©Ñ=ruõÜ¡\002j\006ßà<iw\020SÕ\035 A\003÷VV=\005¼\016`=½\020©\002gP[Åàá÷[ÕG^\017[o\000Ë\020\003íÛØóû½'OXU§ÂÑ\020\b£À¤?°GD5;â F\tØ(y¦J9ÀK\037»Æ¶héERMfÖjfAQw¢©·\001-º$úVÎºÐZÃT~\026êÇå2j\fö¶ÉeI\",¼õÖT,ñf¡0ÞW`\"rß}\000¾QfîÝðD0·\030YùUv\n`Kb\034¿AÇ{\035KjCyõ:#µ8àå\003oÙ{<âë\034\026³®~Í-Í.Æs@3RJo)ó\035ÈpÛïW\023µ\000,£Ó]Ø¦]&\023¥éX%±+AìÓèWöÚ(%4Lá¯GA\031D{¿lj*\035ïÇxÂ­õ-Ë@\002æîQ'å\030M\007\034J7¥\023ÚÜ¢l×=A½c\023ô[Aì\001\020\030<*zXÊý±üÙûÚñ\rp}\025ác]i}¾r2ìæßÞ)T`P\021$I4l*ÄÍm/`\003EÅºP1iÍÃr¥øïì=æÉN1`_:0ï»\003ùêtÃA1NíCë@£\035ùñ\022ÎÆûgÚ{xö<ÝEÝÎÎ^o\006õÏ\030IÃqéçíæhzx1ÎoëPc\026N½ \031¤OX?\"Z%A\002\037ò\021³w¼v³Ý\020ÇÙém¾ï¸0ß,ô\034²=S]¡ÇS}\013\004ÞYt}|zwè\033'\nð¤Yu^òt\037ÎÜx­è}ÑÅ\033hU!\023s\023\021¥\n¦Å \fÞ¸âBk\025ÌX%ù\fÆ\007]s\002Ã9q\022ê?8>`ÁTóñ]ÖÙû\034æ}| u³{wdîW¨©©¢(ÏÒñ\n²à¥zå ¤N\006T¶×µMÏÙ¾OU[\0057.ÏôÒ¨K\024\007ìÓ@&q\004µ\f¬[Ã²V¬\004\006\023ht-\037´¹6\006\033Üí\013¼ ï«í:\021>2ú.¸ºÚ²\004(>\004ª±\037Ç\016î!B@\007]'è\024>©XYåÏ!à:\024Ý¹íÙ+ _ÇÄ\013.WÄÕ äðí ì{ºùj6Ð¬\nUû(bj«x.E¬3d;ÄÈ)±[{gÚV¹Þ\027Ø\013\016B\037ÆÕáº\033Á-%Mé([=5\nm;\\g8dO¢ÄËî4ÂÀ¹\000A¼S]81âôGà\017%Íç\032 ê·;-ãpÕJ¿²@à·å[Ï+\027u\027E\030z\005*\005õä¤\022ùÚ\035þæY4o\\ÆÔ,|\020~G÷\0317u\003Yp@rÐõ.°âË\024¸sÁwúAð¾Èþ\002¨\025ì$$té\\\fîCÛQ\022¿NóÎ:«\035#n\017þ\005\t3¦«;\032\032]®W\017ëô\006¢\024ùòq±4¢\026¦¼´¬¾Lßã­Ê¾D}\n\036¿ºÇ+nx?ò +°®è?6S$\rðÇñ\031$bE\002\026ár\034F\027\035Ø½D\033V/\024Þ$«è\007M¯Î¬ðÊ«K[_M`Å é\bdÒÊ[Á$#E\027°ÚÀ;o\036Ù6×Øª\022ªä¾)mU\034I÷ôt\024âû\\DÌ×0²§lÿÌ,\005næ\0347ë\036wR{»1Z 6Õg9\001!\017P,Ëº¦ \005ÇkñiÎJ\013¹!ZC¶\rSÈc\001k\006ÍØMÍÁÅ5\n\030\t\003%\003)\001{,®ªÇ4HËÈ\023saT\016\033Y\032*\tÛå/¿7AÍ¹9JìÀ\000Ì3|òV«j#úäZ\006u °çý|«h¶\027ÍÒOÒ¿'ªøÕeg\byzXBÃÛ¹çfÞYÎ!Û´ê¿þü¢ÎÄ)%½{THÁ\013Á}è~°¡}¨\007&s¾>|y¶péÎ\013Ê®¥ÅÐ¾ÏÃ*Mãpzüà\035Û·\016Úzd°[ävý5ç\020Z^\027¨­\034C;\035>¡­þû9û\035Ónd\035^3ð\000ýÿÑÖcp»É\024]+pÃÔ·å\033³å5Y¤}0r-ûTÒ}Eá Àvô§A\004n%\005!\007g\033ÒÞeZt³@1FG\023=Ê+\000ýÆ1­yÈG°\034ÚW;\033û\020M¹\016¿°\n8ÊþNAº\020\033Y\004ñ´R·ZÈBçóØ¿- dPÙ+ïä¼6-Q®\035\026W\025y1\001«ä\033¹¶/\037\032x\007è\030xç¸Â\032láFÿ¥\0233²?¨·ÿi³ï\020\033é_³ñ7=7¦f\021\022Næ(=Ë¶;¾/,ð»\fOJv\0249\017ðjr:Sö2vÈØK»\031&¨ì`ur(û8ÓD}^_\031ÒaE^lÕízîdë0\022.U&X\007Ê3ßC\017åu\007Ü\020\033Ú\\¼\tÉ¬Õ\020\026Cc¥\017x&\030ºP\021¬àå\037k´\016EÔ\\A.\\ ü\"\026\021Çû\032S2u7«\034Uüh\rLj(µ¦®ßï^¼i RXTìáXêC\016ô,.®ÒMPQÛâvÔ\022p¼ß¦ó|\020Õ¶\"\025­\t\035 I?\005¤\007\020ã¤o\"Cggc\bs¤mïç8ý\b3\000Æ \007]\027\034Ás\twÈ[G=8w¸\023\007+BX5}Iþi«Z&ßm¦D\f\rrWù¢\024]ùÊÅ\020\007]8\n\005GC& ¾¯dH²TVå[u8\007YxÔ\006Mo`\037´^_äÇ\024\"èïÒscè¢ÀD\037q7³\027)¤p3Ó\021]°JMfö\r6Áê¶8½Ú$ÀüÿÒû\006½\020V¨M£9câÀÈÁ¨'ö§\020;%­íÊ*G¢(Æ½ºnë)Ò\023í5Ú7{Ö\007ÔÙ[´R9U\021Fâ?z\022)yç\000Ç¶$å%ù&c2\001Ù-Û§c(\016'\027èTÄÖ¬Êë]d1 a\027\024öhZ0BC°[©}>æ6hÄÖu\r,Yö¦´Ú¢Î Gx.í\013çÜ0f¾­\034ô¿0\021.½\002_e \003\034¥Rïû\020&\rH­þ§eaÈ$%\024y Í\007\\¥g\002Ó/\030MÆ4i6ºÂÛ\031\005NùGÁÞ\020\n&HlÍ¢\002q¯»ï\fk\f\020[{@\016ù(¯ÜhCëo\032\003Ñ Í)\030\037ß¦¦á8-6u?s¸07aëH¯µ}Öó$éáÎ\020Ê¦Gt>\022\006ãé$(\004 PâeZÚ\022hz>ûv[ô7XF­vP]^\"\005e'\b!k;\037\002Ì9I|ÀÜ\022-`Þb^0ßù_ÛâxÏð,ñ»Ày'à`4x\036ýÖêJ\016é<Ø88!ù\006¡ åBîAHíd!?è¦9;Ý?87\\\020¤&VöF&¢4\023\016ô~ÚD4;É$³6]ÅÏÔ\026×g\020h\006}Ö.³¸CR\013}ã#~Q(ß§\025\024Õ;\030uÖsÔ\013D#êþÁ÷í«ÞQODÕî¦[¦\003Æ\006Àù÷% ØÏ-Ô´\035`X\f¤ÅÄ¸\020ÄõårÖ$X¿\0171\034\021J¦a\030\"AÙðõv\t¦É¼]eC\027(µ¢¾Ú!KÞpn\004òæ/Ef#±c¦Ã\007r©ß \bIÑ<µöñ\030ÐîåGÑÊë-[¥!¡\020©Í%\023Ç -û¢\031¼¤Çøm­ê»,\026àmF¼åá_Ä:äÚ= Ø¤}fô÷5¥ì]Þ~<\007\036\031hB|At³Ò&4ÑSOE¨sÊP\021E÷^\001>²½ÂVSÌ\033N*N\n)\034¥(þnhtJ*È·8²ãdÖ&òÎ\037¯tîJÛ\024¬.ªÄ«$o[ï\fV\r$ÆÉÕÄ$ÊJh3'ôFÊd¦$Å\027\031\nð/ÄÂ\001R\\¥ Á£u\b\035\037IøuO\026dgFL°ô@x:¹Ø·B»³SâÄðZú\020\013L\033\005\r\023;8®@^¯¼Tägâ²ã5 S\rs`x\tî^n4Îh\030zÛ1¦Ä(×ðúéÄ\tÉÖ{\030ËØqShU²ñi:JpôÆ\033×\017ÌèP\035").length();
    byte b2 = 32;
    byte b = -1;
    while (true);
  }
  
  private static Throwable a(Throwable paramThrowable) {
    return paramThrowable;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x5359;
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
        throw new RuntimeException("wtf/opal/bx", exception);
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
    //   13: ldc [Ljava/lang/Object;
    //   15: aload_2
    //   16: invokevirtual parameterCount : ()I
    //   19: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   22: iconst_0
    //   23: iconst_3
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_0
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: aload_3
    //   34: aastore
    //   35: dup
    //   36: iconst_2
    //   37: aload_1
    //   38: aastore
    //   39: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   42: aload_2
    //   43: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   46: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   49: goto -> 103
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc_w 'wtf/opal/bx'
    //   68: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: ldc_w ' : '
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: aload_1
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: ldc_w ' : '
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_2
    //   88: invokevirtual toString : ()Ljava/lang/String;
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual toString : ()Ljava/lang/String;
    //   97: aload #4
    //   99: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: athrow
    //   103: aload_3
    //   104: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x82B;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = e[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])g.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/bx", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
  }
  
  private static int b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l = ((Long)paramArrayOfObject[1]).longValue();
    int j = b(i, l);
    MethodHandle methodHandle = MethodHandles.constant(int.class, Integer.valueOf(j));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return j;
  }
  
  private static CallSite b(MethodHandles.Lookup paramLookup, String paramString, MethodType paramMethodType) {
    // Byte code:
    //   0: new java/lang/invoke/MutableCallSite
    //   3: dup
    //   4: aload_2
    //   5: invokespecial <init> : (Ljava/lang/invoke/MethodType;)V
    //   8: astore_3
    //   9: aload_3
    //   10: ldc_w
    //   13: ldc [Ljava/lang/Object;
    //   15: aload_2
    //   16: invokevirtual parameterCount : ()I
    //   19: invokevirtual asCollector : (Ljava/lang/Class;I)Ljava/lang/invoke/MethodHandle;
    //   22: iconst_0
    //   23: iconst_3
    //   24: anewarray java/lang/Object
    //   27: dup
    //   28: iconst_0
    //   29: aload_0
    //   30: aastore
    //   31: dup
    //   32: iconst_1
    //   33: aload_3
    //   34: aastore
    //   35: dup
    //   36: iconst_2
    //   37: aload_1
    //   38: aastore
    //   39: invokestatic insertArguments : (Ljava/lang/invoke/MethodHandle;I[Ljava/lang/Object;)Ljava/lang/invoke/MethodHandle;
    //   42: aload_2
    //   43: invokestatic explicitCastArguments : (Ljava/lang/invoke/MethodHandle;Ljava/lang/invoke/MethodType;)Ljava/lang/invoke/MethodHandle;
    //   46: invokevirtual setTarget : (Ljava/lang/invoke/MethodHandle;)V
    //   49: goto -> 103
    //   52: astore #4
    //   54: new java/lang/RuntimeException
    //   57: dup
    //   58: new java/lang/StringBuilder
    //   61: dup
    //   62: invokespecial <init> : ()V
    //   65: ldc_w 'wtf/opal/bx'
    //   68: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   71: ldc_w ' : '
    //   74: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   77: aload_1
    //   78: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   81: ldc_w ' : '
    //   84: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   87: aload_2
    //   88: invokevirtual toString : ()Ljava/lang/String;
    //   91: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   94: invokevirtual toString : ()Ljava/lang/String;
    //   97: aload #4
    //   99: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Throwable;)V
    //   102: athrow
    //   103: aload_3
    //   104: areturn
    // Exception table:
    //   from	to	target	type
    //   9	49	52	java/lang/Exception
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\bx.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */