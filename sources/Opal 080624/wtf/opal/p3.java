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

public class p3 {
  private final String F;
  
  private final String I;
  
  private final ks j;
  
  private final long W;
  
  private final String O;
  
  private final long o;
  
  private static final long a = on.a(-4601130691230842485L, 2326856956477258723L, MethodHandles.lookup().lookupClass()).a(120208406874982L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Long[] f;
  
  private static final Map g;
  
  public p3(String paramString1, String paramString2, ks paramks, long paramLong1, String paramString3, long paramLong2) {
    this.F = paramString1;
    this.I = paramString2;
    this.j = paramks;
    this.W = paramLong1;
    this.O = paramString3;
    this.o = paramLong2;
  }
  
  public static p3 R(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_0
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast wtf/opal/kq
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_3
    //   18: dup
    //   19: iconst_2
    //   20: aaload
    //   21: checkcast wtf/opal/ks
    //   24: astore_1
    //   25: pop
    //   26: getstatic wtf/opal/p3.a : J
    //   29: lload_3
    //   30: lxor
    //   31: lstore_3
    //   32: lload_3
    //   33: dup2
    //   34: ldc2_w 73075539647882
    //   37: lxor
    //   38: lstore #5
    //   40: dup2
    //   41: ldc2_w 127811236614912
    //   44: lxor
    //   45: lstore #7
    //   47: pop2
    //   48: invokestatic i : ()[I
    //   51: astore #9
    //   53: new wtf/opal/p3
    //   56: dup
    //   57: aload_2
    //   58: lload #5
    //   60: sipush #31466
    //   63: ldc2_w 361293169145606988
    //   66: lload_3
    //   67: lxor
    //   68: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   73: iconst_2
    //   74: anewarray java/lang/Object
    //   77: dup_x1
    //   78: swap
    //   79: iconst_1
    //   80: swap
    //   81: aastore
    //   82: dup_x2
    //   83: dup_x2
    //   84: pop
    //   85: invokestatic valueOf : (J)Ljava/lang/Long;
    //   88: iconst_0
    //   89: swap
    //   90: aastore
    //   91: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   94: invokevirtual F : ()Ljava/lang/String;
    //   97: aload_2
    //   98: lload #5
    //   100: sipush #2370
    //   103: ldc2_w 7558231110571196654
    //   106: lload_3
    //   107: lxor
    //   108: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   113: iconst_2
    //   114: anewarray java/lang/Object
    //   117: dup_x1
    //   118: swap
    //   119: iconst_1
    //   120: swap
    //   121: aastore
    //   122: dup_x2
    //   123: dup_x2
    //   124: pop
    //   125: invokestatic valueOf : (J)Ljava/lang/Long;
    //   128: iconst_0
    //   129: swap
    //   130: aastore
    //   131: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   134: invokevirtual F : ()Ljava/lang/String;
    //   137: aload_1
    //   138: invokestatic currentTimeMillis : ()J
    //   141: aload_2
    //   142: lload #5
    //   144: sipush #27100
    //   147: ldc2_w 5428080365166059633
    //   150: lload_3
    //   151: lxor
    //   152: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   157: iconst_2
    //   158: anewarray java/lang/Object
    //   161: dup_x1
    //   162: swap
    //   163: iconst_1
    //   164: swap
    //   165: aastore
    //   166: dup_x2
    //   167: dup_x2
    //   168: pop
    //   169: invokestatic valueOf : (J)Ljava/lang/Long;
    //   172: iconst_0
    //   173: swap
    //   174: aastore
    //   175: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   178: lload #7
    //   180: iconst_1
    //   181: anewarray java/lang/Object
    //   184: dup_x2
    //   185: dup_x2
    //   186: pop
    //   187: invokestatic valueOf : (J)Ljava/lang/Long;
    //   190: iconst_0
    //   191: swap
    //   192: aastore
    //   193: invokevirtual J : ([Ljava/lang/Object;)J
    //   196: sipush #16498
    //   199: ldc2_w 8460587719762948125
    //   202: lload_3
    //   203: lxor
    //   204: <illegal opcode> a : (IJ)J
    //   209: lmul
    //   210: ladd
    //   211: aload_2
    //   212: lload #5
    //   214: sipush #27443
    //   217: ldc2_w 2100266030120362648
    //   220: lload_3
    //   221: lxor
    //   222: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   227: iconst_2
    //   228: anewarray java/lang/Object
    //   231: dup_x1
    //   232: swap
    //   233: iconst_1
    //   234: swap
    //   235: aastore
    //   236: dup_x2
    //   237: dup_x2
    //   238: pop
    //   239: invokestatic valueOf : (J)Ljava/lang/Long;
    //   242: iconst_0
    //   243: swap
    //   244: aastore
    //   245: invokevirtual A : ([Ljava/lang/Object;)Lwtf/opal/kv;
    //   248: invokevirtual F : ()Ljava/lang/String;
    //   251: invokestatic currentTimeMillis : ()J
    //   254: sipush #19961
    //   257: ldc2_w 1984130212858674583
    //   260: lload_3
    //   261: lxor
    //   262: <illegal opcode> a : (IJ)J
    //   267: ladd
    //   268: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Lwtf/opal/ks;JLjava/lang/String;J)V
    //   271: invokestatic D : ()[Lwtf/opal/d;
    //   274: ifnull -> 290
    //   277: iconst_1
    //   278: newarray int
    //   280: invokestatic S : ([I)V
    //   283: goto -> 290
    //   286: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   289: athrow
    //   290: areturn
    // Exception table:
    //   from	to	target	type
    //   53	283	286	wtf/opal/x5
  }
  
  public final String k(Object[] paramArrayOfObject) {
    return this.I;
  }
  
  public final String W(Object[] paramArrayOfObject) {
    return this.O;
  }
  
  public final long B(Object[] paramArrayOfObject) {
    return this.o;
  }
  
  public final long C(Object[] paramArrayOfObject) {
    return this.W;
  }
  
  public final String x(Object[] paramArrayOfObject) {
    return this.F;
  }
  
  public final ks v(Object[] paramArrayOfObject) {
    return this.j;
  }
  
  public kq R(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/p3.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 92964874323892
    //   23: lxor
    //   24: lstore #4
    //   26: dup2
    //   27: ldc2_w 76422307776092
    //   30: lxor
    //   31: lstore #6
    //   33: dup2
    //   34: ldc2_w 104715551925880
    //   37: lxor
    //   38: lstore #8
    //   40: pop2
    //   41: new wtf/opal/kq
    //   44: dup
    //   45: lload #8
    //   47: invokespecial <init> : (J)V
    //   50: astore #11
    //   52: aload #11
    //   54: lload #4
    //   56: sipush #4066
    //   59: ldc2_w 8837980411605880137
    //   62: lload_2
    //   63: lxor
    //   64: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   69: aload_0
    //   70: iconst_0
    //   71: anewarray java/lang/Object
    //   74: invokevirtual x : ([Ljava/lang/Object;)Ljava/lang/String;
    //   77: iconst_3
    //   78: anewarray java/lang/Object
    //   81: dup_x1
    //   82: swap
    //   83: iconst_2
    //   84: swap
    //   85: aastore
    //   86: dup_x1
    //   87: swap
    //   88: iconst_1
    //   89: swap
    //   90: aastore
    //   91: dup_x2
    //   92: dup_x2
    //   93: pop
    //   94: invokestatic valueOf : (J)Ljava/lang/Long;
    //   97: iconst_0
    //   98: swap
    //   99: aastore
    //   100: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   103: pop
    //   104: invokestatic i : ()[I
    //   107: aload #11
    //   109: lload #4
    //   111: sipush #12460
    //   114: ldc2_w 5581897833870406146
    //   117: lload_2
    //   118: lxor
    //   119: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   124: aload_0
    //   125: iconst_0
    //   126: anewarray java/lang/Object
    //   129: invokevirtual k : ([Ljava/lang/Object;)Ljava/lang/String;
    //   132: iconst_3
    //   133: anewarray java/lang/Object
    //   136: dup_x1
    //   137: swap
    //   138: iconst_2
    //   139: swap
    //   140: aastore
    //   141: dup_x1
    //   142: swap
    //   143: iconst_1
    //   144: swap
    //   145: aastore
    //   146: dup_x2
    //   147: dup_x2
    //   148: pop
    //   149: invokestatic valueOf : (J)Ljava/lang/Long;
    //   152: iconst_0
    //   153: swap
    //   154: aastore
    //   155: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   158: pop
    //   159: astore #10
    //   161: aload #11
    //   163: lload #4
    //   165: sipush #12430
    //   168: ldc2_w 8359900212153070119
    //   171: lload_2
    //   172: lxor
    //   173: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   178: aload_0
    //   179: iconst_0
    //   180: anewarray java/lang/Object
    //   183: invokevirtual v : ([Ljava/lang/Object;)Lwtf/opal/ks;
    //   186: iconst_0
    //   187: anewarray java/lang/Object
    //   190: invokevirtual U : ([Ljava/lang/Object;)Ljava/lang/String;
    //   193: iconst_3
    //   194: anewarray java/lang/Object
    //   197: dup_x1
    //   198: swap
    //   199: iconst_2
    //   200: swap
    //   201: aastore
    //   202: dup_x1
    //   203: swap
    //   204: iconst_1
    //   205: swap
    //   206: aastore
    //   207: dup_x2
    //   208: dup_x2
    //   209: pop
    //   210: invokestatic valueOf : (J)Ljava/lang/Long;
    //   213: iconst_0
    //   214: swap
    //   215: aastore
    //   216: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   219: pop
    //   220: aload #11
    //   222: lload #4
    //   224: sipush #24561
    //   227: ldc2_w 7258753466752294238
    //   230: lload_2
    //   231: lxor
    //   232: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   237: aload_0
    //   238: iconst_0
    //   239: anewarray java/lang/Object
    //   242: invokevirtual v : ([Ljava/lang/Object;)Lwtf/opal/ks;
    //   245: iconst_0
    //   246: anewarray java/lang/Object
    //   249: invokevirtual u : ([Ljava/lang/Object;)Ljava/lang/String;
    //   252: iconst_3
    //   253: anewarray java/lang/Object
    //   256: dup_x1
    //   257: swap
    //   258: iconst_2
    //   259: swap
    //   260: aastore
    //   261: dup_x1
    //   262: swap
    //   263: iconst_1
    //   264: swap
    //   265: aastore
    //   266: dup_x2
    //   267: dup_x2
    //   268: pop
    //   269: invokestatic valueOf : (J)Ljava/lang/Long;
    //   272: iconst_0
    //   273: swap
    //   274: aastore
    //   275: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   278: pop
    //   279: aload #11
    //   281: lload #4
    //   283: sipush #12339
    //   286: ldc2_w 6253060998393344662
    //   289: lload_2
    //   290: lxor
    //   291: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   296: aload_0
    //   297: iconst_0
    //   298: anewarray java/lang/Object
    //   301: invokevirtual v : ([Ljava/lang/Object;)Lwtf/opal/ks;
    //   304: iconst_0
    //   305: anewarray java/lang/Object
    //   308: invokevirtual e : ([Ljava/lang/Object;)Ljava/lang/String;
    //   311: iconst_3
    //   312: anewarray java/lang/Object
    //   315: dup_x1
    //   316: swap
    //   317: iconst_2
    //   318: swap
    //   319: aastore
    //   320: dup_x1
    //   321: swap
    //   322: iconst_1
    //   323: swap
    //   324: aastore
    //   325: dup_x2
    //   326: dup_x2
    //   327: pop
    //   328: invokestatic valueOf : (J)Ljava/lang/Long;
    //   331: iconst_0
    //   332: swap
    //   333: aastore
    //   334: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   337: pop
    //   338: aload #11
    //   340: lload #6
    //   342: sipush #28548
    //   345: ldc2_w 3287699034190146860
    //   348: lload_2
    //   349: lxor
    //   350: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   355: aload_0
    //   356: iconst_0
    //   357: anewarray java/lang/Object
    //   360: invokevirtual C : ([Ljava/lang/Object;)J
    //   363: iconst_3
    //   364: anewarray java/lang/Object
    //   367: dup_x2
    //   368: dup_x2
    //   369: pop
    //   370: invokestatic valueOf : (J)Ljava/lang/Long;
    //   373: iconst_2
    //   374: swap
    //   375: aastore
    //   376: dup_x1
    //   377: swap
    //   378: iconst_1
    //   379: swap
    //   380: aastore
    //   381: dup_x2
    //   382: dup_x2
    //   383: pop
    //   384: invokestatic valueOf : (J)Ljava/lang/Long;
    //   387: iconst_0
    //   388: swap
    //   389: aastore
    //   390: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   393: pop
    //   394: aload #11
    //   396: lload #4
    //   398: sipush #23697
    //   401: ldc2_w 2860097803631550007
    //   404: lload_2
    //   405: lxor
    //   406: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   411: aload_0
    //   412: iconst_0
    //   413: anewarray java/lang/Object
    //   416: invokevirtual W : ([Ljava/lang/Object;)Ljava/lang/String;
    //   419: iconst_3
    //   420: anewarray java/lang/Object
    //   423: dup_x1
    //   424: swap
    //   425: iconst_2
    //   426: swap
    //   427: aastore
    //   428: dup_x1
    //   429: swap
    //   430: iconst_1
    //   431: swap
    //   432: aastore
    //   433: dup_x2
    //   434: dup_x2
    //   435: pop
    //   436: invokestatic valueOf : (J)Ljava/lang/Long;
    //   439: iconst_0
    //   440: swap
    //   441: aastore
    //   442: invokevirtual I : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   445: pop
    //   446: aload #11
    //   448: lload #6
    //   450: sipush #20587
    //   453: ldc2_w 7769689669470048975
    //   456: lload_2
    //   457: lxor
    //   458: <illegal opcode> l : (IJ)Ljava/lang/String;
    //   463: aload_0
    //   464: iconst_0
    //   465: anewarray java/lang/Object
    //   468: invokevirtual B : ([Ljava/lang/Object;)J
    //   471: iconst_3
    //   472: anewarray java/lang/Object
    //   475: dup_x2
    //   476: dup_x2
    //   477: pop
    //   478: invokestatic valueOf : (J)Ljava/lang/Long;
    //   481: iconst_2
    //   482: swap
    //   483: aastore
    //   484: dup_x1
    //   485: swap
    //   486: iconst_1
    //   487: swap
    //   488: aastore
    //   489: dup_x2
    //   490: dup_x2
    //   491: pop
    //   492: invokestatic valueOf : (J)Ljava/lang/Long;
    //   495: iconst_0
    //   496: swap
    //   497: aastore
    //   498: invokevirtual N : ([Ljava/lang/Object;)Lwtf/opal/kq;
    //   501: pop
    //   502: aload #11
    //   504: aload #10
    //   506: ifnull -> 523
    //   509: iconst_3
    //   510: anewarray wtf/opal/d
    //   513: invokestatic p : ([Lwtf/opal/d;)V
    //   516: goto -> 523
    //   519: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   522: athrow
    //   523: areturn
    // Exception table:
    //   from	to	target	type
    //   161	516	519	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x2FC4B99779BCL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[12];
    boolean bool = false;
    String str;
    int i = (str = "ÚõÝ×yAåÃzFZÑ\023w${U[e*\000N\rð\033ÁBhxÁ\020\bÊ6h_Ò6¼KÐÚü ¹\027\023vÓWåb\náÔÞ\030\030\022õf\025··ØJ\032Æ_DV×\030®±Vûmö\023ÿmZm?à\002\"ÿkÂ]\n\030E\036;\týA8²ìèLªè]u\030ä_n­\020sÜéáá¬CÅRåNÆ]! $+«\\AJé­&\002F\037\034`&&ÜÑ\004ÌiÁ:ÃÞ\025Ù\030GÞ\017\013öx\034ÂjôGJJ\0176\006½°Ö,K \031gWÎ\001CÍrà#Rjëqº¯Ñk±ã\007Ââ!@´\003u¿a\020\032T¦Öì«Í©\022ÐûWGN~").length();
    byte b2 = 32;
    byte b = -1;
    while (true);
  }
  
  private static x5 a(x5 paramx5) {
    return paramx5;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x14B7;
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
        throw new RuntimeException("wtf/opal/p3", exception);
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
    //   65: ldc_w 'wtf/opal/p3'
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
  
  private static long b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6D76;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l1 = e[i];
      byte[] arrayOfByte2 = { (byte)(int)(l1 >>> 56L), (byte)(int)(l1 >>> 48L), (byte)(int)(l1 >>> 40L), (byte)(int)(l1 >>> 32L), (byte)(int)(l1 >>> 24L), (byte)(int)(l1 >>> 16L), (byte)(int)(l1 >>> 8L), (byte)(int)l1 };
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
        throw new RuntimeException("wtf/opal/p3", exception);
      } 
      long l2 = (arrayOfByte3[0] & 0xFFL) << 56L | (arrayOfByte3[1] & 0xFFL) << 48L | (arrayOfByte3[2] & 0xFFL) << 40L | (arrayOfByte3[3] & 0xFFL) << 32L | (arrayOfByte3[4] & 0xFFL) << 24L | (arrayOfByte3[5] & 0xFFL) << 16L | (arrayOfByte3[6] & 0xFFL) << 8L | arrayOfByte3[7] & 0xFFL;
      f[i] = Long.valueOf(l2);
    } 
    return f[i].longValue();
  }
  
  private static long b(MethodHandles.Lookup paramLookup, MutableCallSite paramMutableCallSite, String paramString, Object[] paramArrayOfObject) {
    int i = ((Integer)paramArrayOfObject[0]).intValue();
    long l1 = ((Long)paramArrayOfObject[1]).longValue();
    long l2 = b(i, l1);
    MethodHandle methodHandle = MethodHandles.constant(long.class, Long.valueOf(l2));
    paramMutableCallSite.setTarget(MethodHandles.dropArguments(methodHandle, 0, new Class[] { int.class, long.class }));
    return l2;
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
    //   65: ldc_w 'wtf/opal/p3'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\p3.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */