package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class ub extends u_<jm> {
  private final ke P;
  
  private final gm<lz> H;
  
  private static final long a = on.a(4135795749140354284L, -2210136162510046721L, MethodHandles.lookup().lookupClass()).a(120937020199025L);
  
  private static final String b;
  
  public ub(jm paramjm, long paramLong) {
    super(paramjm);
    this.P = new ke(l, b, this, true);
    this.H = this::lambda$new$0;
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return bn.VERUS;
  }
  
  private void lambda$new$0(lz paramlz) {
    // Byte code:
    //   0: getstatic wtf/opal/ub.a : J
    //   3: ldc2_w 83081414966686
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 98692447684432
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 115100516198899
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 73872798646631
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: invokestatic e : ()I
    //   34: istore #10
    //   36: lload #6
    //   38: iconst_1
    //   39: anewarray java/lang/Object
    //   42: dup_x2
    //   43: dup_x2
    //   44: pop
    //   45: invokestatic valueOf : (J)Ljava/lang/Long;
    //   48: iconst_0
    //   49: swap
    //   50: aastore
    //   51: invokestatic I : ([Ljava/lang/Object;)Z
    //   54: iload #10
    //   56: ifne -> 79
    //   59: ifne -> 70
    //   62: goto -> 69
    //   65: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   68: athrow
    //   69: return
    //   70: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   73: getfield field_1724 : Lnet/minecraft/class_746;
    //   76: invokevirtual method_24828 : ()Z
    //   79: iload #10
    //   81: ifne -> 170
    //   84: ifeq -> 157
    //   87: goto -> 94
    //   90: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   93: athrow
    //   94: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   97: getfield field_1724 : Lnet/minecraft/class_746;
    //   100: invokevirtual method_6043 : ()V
    //   103: lload #4
    //   105: iconst_1
    //   106: anewarray java/lang/Object
    //   109: dup_x2
    //   110: dup_x2
    //   111: pop
    //   112: invokestatic valueOf : (J)Ljava/lang/Long;
    //   115: iconst_0
    //   116: swap
    //   117: aastore
    //   118: invokestatic m : ([Ljava/lang/Object;)D
    //   121: lload #8
    //   123: dup2_x2
    //   124: pop2
    //   125: iconst_2
    //   126: anewarray java/lang/Object
    //   129: dup_x2
    //   130: dup_x2
    //   131: pop
    //   132: invokestatic valueOf : (D)Ljava/lang/Double;
    //   135: iconst_1
    //   136: swap
    //   137: aastore
    //   138: dup_x2
    //   139: dup_x2
    //   140: pop
    //   141: invokestatic valueOf : (J)Ljava/lang/Long;
    //   144: iconst_0
    //   145: swap
    //   146: aastore
    //   147: invokestatic k : ([Ljava/lang/Object;)V
    //   150: goto -> 157
    //   153: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   156: athrow
    //   157: aload_0
    //   158: getfield P : Lwtf/opal/ke;
    //   161: invokevirtual z : ()Ljava/lang/Object;
    //   164: checkcast java/lang/Boolean
    //   167: invokevirtual booleanValue : ()Z
    //   170: iload #10
    //   172: ifne -> 201
    //   175: ifeq -> 288
    //   178: goto -> 185
    //   181: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   184: athrow
    //   185: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   188: getfield field_1724 : Lnet/minecraft/class_746;
    //   191: getfield field_5976 : Z
    //   194: goto -> 201
    //   197: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   200: athrow
    //   201: iload #10
    //   203: ifne -> 251
    //   206: ifne -> 288
    //   209: goto -> 216
    //   212: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   215: athrow
    //   216: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   219: getfield field_1724 : Lnet/minecraft/class_746;
    //   222: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   225: invokevirtual method_10214 : ()D
    //   228: iload #10
    //   230: ifne -> 296
    //   233: goto -> 240
    //   236: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   239: athrow
    //   240: ldc2_w 0.33319999363422365
    //   243: dcmpl
    //   244: goto -> 251
    //   247: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   250: athrow
    //   251: ifne -> 288
    //   254: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   257: getfield field_1724 : Lnet/minecraft/class_746;
    //   260: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   263: getfield field_1724 : Lnet/minecraft/class_746;
    //   266: invokevirtual method_18798 : ()Lnet/minecraft/class_243;
    //   269: getstatic net/minecraft/class_2350$class_2351.field_11052 : Lnet/minecraft/class_2350$class_2351;
    //   272: ldc2_w -0.0784000015258789
    //   275: invokevirtual method_38499 : (Lnet/minecraft/class_2350$class_2351;D)Lnet/minecraft/class_243;
    //   278: invokevirtual method_18799 : (Lnet/minecraft/class_243;)V
    //   281: goto -> 288
    //   284: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   287: athrow
    //   288: iconst_0
    //   289: anewarray java/lang/Object
    //   292: invokestatic U : ([Ljava/lang/Object;)F
    //   295: f2d
    //   296: lload #8
    //   298: dup2_x2
    //   299: pop2
    //   300: iconst_2
    //   301: anewarray java/lang/Object
    //   304: dup_x2
    //   305: dup_x2
    //   306: pop
    //   307: invokestatic valueOf : (D)Ljava/lang/Double;
    //   310: iconst_1
    //   311: swap
    //   312: aastore
    //   313: dup_x2
    //   314: dup_x2
    //   315: pop
    //   316: invokestatic valueOf : (J)Ljava/lang/Long;
    //   319: iconst_0
    //   320: swap
    //   321: aastore
    //   322: invokestatic k : ([Ljava/lang/Object;)V
    //   325: return
    // Exception table:
    //   from	to	target	type
    //   36	62	65	wtf/opal/x5
    //   79	87	90	wtf/opal/x5
    //   84	150	153	wtf/opal/x5
    //   170	178	181	wtf/opal/x5
    //   175	194	197	wtf/opal/x5
    //   201	209	212	wtf/opal/x5
    //   206	233	236	wtf/opal/x5
    //   216	244	247	wtf/opal/x5
    //   251	281	284	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x6B8A4186B4B4L;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\ub.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */