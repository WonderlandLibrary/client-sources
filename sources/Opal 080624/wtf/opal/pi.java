package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class pi extends u_<xw> {
  private int w;
  
  private int K;
  
  private int I;
  
  private final gm<u0> z = this::lambda$new$0;
  
  private static final long a = on.a(2048020385796443899L, -4323462370527439381L, MethodHandles.lookup().lookupClass()).a(154150121728828L);
  
  private static final String b;
  
  private static final long c;
  
  public pi(xw paramxw) {
    super(paramxw);
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return l1.WATCHDOG_LOWHOP_2;
  }
  
  private void lambda$new$0(u0 paramu0) {
    // Byte code:
    //   0: getstatic wtf/opal/pi.a : J
    //   3: ldc2_w 33629565503452
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 100285444588946
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 19266533445733
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic b : ()Z
    //   27: istore #8
    //   29: aload_1
    //   30: iconst_0
    //   31: anewarray java/lang/Object
    //   34: invokevirtual K : ([Ljava/lang/Object;)Z
    //   37: iload #8
    //   39: ifeq -> 82
    //   42: ifne -> 97
    //   45: goto -> 52
    //   48: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: aload_0
    //   53: iconst_0
    //   54: anewarray java/lang/Object
    //   57: invokevirtual y : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   60: checkcast wtf/opal/xw
    //   63: getfield B : Lwtf/opal/ke;
    //   66: invokevirtual z : ()Ljava/lang/Object;
    //   69: checkcast java/lang/Boolean
    //   72: invokevirtual booleanValue : ()Z
    //   75: goto -> 82
    //   78: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   81: athrow
    //   82: iload #8
    //   84: ifeq -> 110
    //   87: ifne -> 98
    //   90: goto -> 97
    //   93: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   96: athrow
    //   97: return
    //   98: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   101: getfield field_1690 : Lnet/minecraft/class_315;
    //   104: getfield field_1903 : Lnet/minecraft/class_304;
    //   107: invokevirtual method_1434 : ()Z
    //   110: iload #8
    //   112: ifeq -> 166
    //   115: ifeq -> 349
    //   118: goto -> 125
    //   121: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   124: athrow
    //   125: aload_0
    //   126: iconst_0
    //   127: putfield w : I
    //   130: aload_0
    //   131: dup
    //   132: getfield K : I
    //   135: iconst_1
    //   136: iadd
    //   137: putfield K : I
    //   140: aload_0
    //   141: dup
    //   142: getfield I : I
    //   145: iconst_1
    //   146: iadd
    //   147: putfield I : I
    //   150: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   153: getfield field_1724 : Lnet/minecraft/class_746;
    //   156: invokevirtual method_24828 : ()Z
    //   159: goto -> 166
    //   162: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   165: athrow
    //   166: iload #8
    //   168: ifeq -> 197
    //   171: ifeq -> 193
    //   174: goto -> 181
    //   177: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   180: athrow
    //   181: aload_0
    //   182: iconst_0
    //   183: putfield I : I
    //   186: goto -> 193
    //   189: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   192: athrow
    //   193: aload_0
    //   194: getfield I : I
    //   197: iload #8
    //   199: ifeq -> 299
    //   202: ifne -> 283
    //   205: goto -> 212
    //   208: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   211: athrow
    //   212: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   215: getfield field_1724 : Lnet/minecraft/class_746;
    //   218: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   221: getfield field_1724 : Lnet/minecraft/class_746;
    //   224: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   227: invokevirtual method_10216 : ()D
    //   230: ldc2_w 0.41999998688697815
    //   233: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   236: getfield field_1724 : Lnet/minecraft/class_746;
    //   239: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   242: invokevirtual method_10215 : ()D
    //   245: invokevirtual method_18800 : (DDD)V
    //   248: lload #6
    //   250: dconst_0
    //   251: iconst_2
    //   252: anewarray java/lang/Object
    //   255: dup_x2
    //   256: dup_x2
    //   257: pop
    //   258: invokestatic valueOf : (D)Ljava/lang/Double;
    //   261: iconst_1
    //   262: swap
    //   263: aastore
    //   264: dup_x2
    //   265: dup_x2
    //   266: pop
    //   267: invokestatic valueOf : (J)Ljava/lang/Long;
    //   270: iconst_0
    //   271: swap
    //   272: aastore
    //   273: invokestatic k : ([Ljava/lang/Object;)V
    //   276: goto -> 283
    //   279: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   282: athrow
    //   283: aload_0
    //   284: iload #8
    //   286: ifeq -> 340
    //   289: getfield I : I
    //   292: goto -> 299
    //   295: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   298: athrow
    //   299: getstatic wtf/opal/pi.c : J
    //   302: l2i
    //   303: if_icmpne -> 361
    //   306: lload #4
    //   308: getstatic wtf/opal/pi.b : Ljava/lang/String;
    //   311: iconst_2
    //   312: anewarray java/lang/Object
    //   315: dup_x1
    //   316: swap
    //   317: iconst_1
    //   318: swap
    //   319: aastore
    //   320: dup_x2
    //   321: dup_x2
    //   322: pop
    //   323: invokestatic valueOf : (J)Ljava/lang/Long;
    //   326: iconst_0
    //   327: swap
    //   328: aastore
    //   329: invokestatic g : ([Ljava/lang/Object;)V
    //   332: aload_0
    //   333: goto -> 340
    //   336: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   339: athrow
    //   340: iconst_m1
    //   341: putfield I : I
    //   344: iload #8
    //   346: ifne -> 361
    //   349: aload_0
    //   350: iconst_0
    //   351: putfield K : I
    //   354: goto -> 361
    //   357: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   360: athrow
    //   361: return
    // Exception table:
    //   from	to	target	type
    //   29	45	48	wtf/opal/x5
    //   42	75	78	wtf/opal/x5
    //   82	90	93	wtf/opal/x5
    //   110	118	121	wtf/opal/x5
    //   115	159	162	wtf/opal/x5
    //   166	174	177	wtf/opal/x5
    //   171	186	189	wtf/opal/x5
    //   197	205	208	wtf/opal/x5
    //   202	276	279	wtf/opal/x5
    //   283	292	295	wtf/opal/x5
    //   299	333	336	wtf/opal/x5
    //   340	354	357	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x48761AB659A9L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static x5 b(x5 paramx5) {
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pi.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */