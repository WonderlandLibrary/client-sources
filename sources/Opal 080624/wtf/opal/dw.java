package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class dw extends dn {
  private static d[] F;
  
  private static final long b = on.a(-2592058056421257762L, 1181087795574125211L, MethodHandles.lookup().lookupClass()).a(38916492175878L);
  
  private static final String c;
  
  private static final long d;
  
  public dw(long paramLong) {
    super(c, l, dt.COMBAT);
  }
  
  public boolean b(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast net/minecraft/class_1657
    //   7: astore_2
    //   8: dup
    //   9: iconst_1
    //   10: aaload
    //   11: checkcast java/lang/Long
    //   14: invokevirtual longValue : ()J
    //   17: lstore_3
    //   18: pop
    //   19: lload_3
    //   20: dup2
    //   21: ldc2_w 90743704613986
    //   24: lxor
    //   25: dup2
    //   26: bipush #48
    //   28: lushr
    //   29: l2i
    //   30: istore #5
    //   32: dup2
    //   33: bipush #16
    //   35: lshl
    //   36: bipush #32
    //   38: lushr
    //   39: l2i
    //   40: istore #6
    //   42: dup2
    //   43: bipush #48
    //   45: lshl
    //   46: bipush #48
    //   48: lushr
    //   49: l2i
    //   50: istore #7
    //   52: pop2
    //   53: pop2
    //   54: invokestatic T : ()[Lwtf/opal/d;
    //   57: iconst_0
    //   58: anewarray java/lang/Object
    //   61: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   64: iconst_0
    //   65: anewarray java/lang/Object
    //   68: invokevirtual q : ([Ljava/lang/Object;)Lwtf/opal/ko;
    //   71: iconst_0
    //   72: anewarray java/lang/Object
    //   75: invokevirtual W : ([Ljava/lang/Object;)Ljava/util/Map;
    //   78: aload_2
    //   79: invokeinterface get : (Ljava/lang/Object;)Ljava/lang/Object;
    //   84: checkcast wtf/opal/lv
    //   87: astore #9
    //   89: astore #8
    //   91: iconst_0
    //   92: anewarray java/lang/Object
    //   95: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   98: iconst_0
    //   99: anewarray java/lang/Object
    //   102: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   105: ldc wtf/opal/je
    //   107: iconst_1
    //   108: anewarray java/lang/Object
    //   111: dup_x1
    //   112: swap
    //   113: iconst_0
    //   114: swap
    //   115: aastore
    //   116: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   119: checkcast wtf/opal/je
    //   122: astore #10
    //   124: aload #10
    //   126: iconst_0
    //   127: anewarray java/lang/Object
    //   130: invokevirtual j : ([Ljava/lang/Object;)Lwtf/opal/kt;
    //   133: invokevirtual z : ()Ljava/lang/Object;
    //   136: checkcast java/lang/Double
    //   139: invokevirtual doubleValue : ()D
    //   142: dstore #11
    //   144: aload #10
    //   146: iconst_0
    //   147: anewarray java/lang/Object
    //   150: invokevirtual D : ([Ljava/lang/Object;)Z
    //   153: ifeq -> 177
    //   156: aload_2
    //   157: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   160: invokevirtual method_1488 : ()F
    //   163: invokevirtual method_6055 : (F)F
    //   166: f2d
    //   167: dload #11
    //   169: ddiv
    //   170: goto -> 188
    //   173: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   176: athrow
    //   177: aload_2
    //   178: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   181: invokevirtual method_1488 : ()F
    //   184: invokevirtual method_6055 : (F)F
    //   187: f2d
    //   188: dstore #13
    //   190: aload #8
    //   192: lload_3
    //   193: lconst_0
    //   194: lcmp
    //   195: ifle -> 298
    //   198: ifnonnull -> 290
    //   201: aload_2
    //   202: iload #5
    //   204: i2s
    //   205: iload #6
    //   207: iload #7
    //   209: iconst_4
    //   210: anewarray java/lang/Object
    //   213: dup_x1
    //   214: swap
    //   215: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   218: iconst_3
    //   219: swap
    //   220: aastore
    //   221: dup_x1
    //   222: swap
    //   223: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   226: iconst_2
    //   227: swap
    //   228: aastore
    //   229: dup_x1
    //   230: swap
    //   231: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   234: iconst_1
    //   235: swap
    //   236: aastore
    //   237: dup_x1
    //   238: swap
    //   239: iconst_0
    //   240: swap
    //   241: aastore
    //   242: invokestatic J : ([Ljava/lang/Object;)Z
    //   245: ifeq -> 315
    //   248: goto -> 255
    //   251: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   254: athrow
    //   255: aload #9
    //   257: aload #9
    //   259: iconst_0
    //   260: anewarray java/lang/Object
    //   263: invokevirtual F : ([Ljava/lang/Object;)I
    //   266: iconst_1
    //   267: iadd
    //   268: iconst_1
    //   269: anewarray java/lang/Object
    //   272: dup_x1
    //   273: swap
    //   274: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   277: iconst_0
    //   278: swap
    //   279: aastore
    //   280: invokevirtual z : ([Ljava/lang/Object;)V
    //   283: goto -> 290
    //   286: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   289: athrow
    //   290: lload_3
    //   291: lconst_0
    //   292: lcmp
    //   293: ifle -> 333
    //   296: aload #8
    //   298: ifnull -> 333
    //   301: iconst_4
    //   302: anewarray wtf/opal/d
    //   305: invokestatic p : ([Lwtf/opal/d;)V
    //   308: goto -> 315
    //   311: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   314: athrow
    //   315: aload #9
    //   317: iconst_0
    //   318: iconst_1
    //   319: anewarray java/lang/Object
    //   322: dup_x1
    //   323: swap
    //   324: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   327: iconst_0
    //   328: swap
    //   329: aastore
    //   330: invokevirtual z : ([Ljava/lang/Object;)V
    //   333: aload #9
    //   335: iconst_0
    //   336: anewarray java/lang/Object
    //   339: invokevirtual F : ([Ljava/lang/Object;)I
    //   342: aload #8
    //   344: lload_3
    //   345: lconst_0
    //   346: lcmp
    //   347: ifle -> 380
    //   350: ifnonnull -> 378
    //   353: getstatic wtf/opal/dw.d : J
    //   356: l2i
    //   357: if_icmple -> 397
    //   360: goto -> 367
    //   363: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   366: athrow
    //   367: dload #13
    //   369: dconst_0
    //   370: dcmpl
    //   371: goto -> 378
    //   374: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   377: athrow
    //   378: aload #8
    //   380: ifnonnull -> 394
    //   383: ifle -> 397
    //   386: goto -> 393
    //   389: invokestatic a : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   392: athrow
    //   393: iconst_1
    //   394: goto -> 398
    //   397: iconst_0
    //   398: ireturn
    // Exception table:
    //   from	to	target	type
    //   144	173	173	wtf/opal/x5
    //   190	248	251	wtf/opal/x5
    //   201	283	286	wtf/opal/x5
    //   290	308	311	wtf/opal/x5
    //   333	360	363	wtf/opal/x5
    //   353	371	374	wtf/opal/x5
    //   378	386	389	wtf/opal/x5
  }
  
  public static void m(d[] paramArrayOfd) {
    F = paramArrayOfd;
  }
  
  public static d[] T() {
    return F;
  }
  
  static {
    long l = b ^ 0x1C5C196913E5L;
    m(null);
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
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
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\dw.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */