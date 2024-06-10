package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class uu extends u_<jm> {
  private final kt k;
  
  private final gm<b6> N;
  
  private static final long a = on.a(4689259077327131871L, -651241581081912183L, MethodHandles.lookup().lookupClass()).a(237298467957249L);
  
  private static final String b;
  
  public uu(int paramInt1, short paramShort, int paramInt2, jm paramjm) {
    super(paramjm);
    this.k = new kt(b, this, 0.5D, 0.0D, 9.0D, 0.01D, l2);
    this.N = this::lambda$new$0;
  }
  
  public Enum V(Object[] paramArrayOfObject) {
    return bn.VANILLA;
  }
  
  private void lambda$new$0(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/uu.a : J
    //   3: ldc2_w 49931359427611
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 73575062526948
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 115911301985136
    //   20: lxor
    //   21: lstore #6
    //   23: pop2
    //   24: invokestatic k : ()I
    //   27: istore #8
    //   29: aload_1
    //   30: iconst_0
    //   31: anewarray java/lang/Object
    //   34: invokevirtual W : ([Ljava/lang/Object;)Z
    //   37: iload #8
    //   39: ifeq -> 77
    //   42: ifeq -> 92
    //   45: goto -> 52
    //   48: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   51: athrow
    //   52: lload #4
    //   54: iconst_1
    //   55: anewarray java/lang/Object
    //   58: dup_x2
    //   59: dup_x2
    //   60: pop
    //   61: invokestatic valueOf : (J)Ljava/lang/Long;
    //   64: iconst_0
    //   65: swap
    //   66: aastore
    //   67: invokestatic I : ([Ljava/lang/Object;)Z
    //   70: goto -> 77
    //   73: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   76: athrow
    //   77: iload #8
    //   79: ifeq -> 114
    //   82: ifne -> 93
    //   85: goto -> 92
    //   88: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   91: athrow
    //   92: return
    //   93: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   96: getfield field_1724 : Lnet/minecraft/class_746;
    //   99: iload #8
    //   101: ifeq -> 140
    //   104: invokevirtual method_24828 : ()Z
    //   107: goto -> 114
    //   110: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   113: athrow
    //   114: ifeq -> 133
    //   117: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   120: getfield field_1724 : Lnet/minecraft/class_746;
    //   123: invokevirtual method_6043 : ()V
    //   126: goto -> 133
    //   129: invokestatic b : (Lwtf/opal/x5;)Lwtf/opal/x5;
    //   132: athrow
    //   133: aload_0
    //   134: getfield k : Lwtf/opal/kt;
    //   137: invokevirtual z : ()Ljava/lang/Object;
    //   140: checkcast java/lang/Double
    //   143: invokevirtual doubleValue : ()D
    //   146: lload #6
    //   148: dup2_x2
    //   149: pop2
    //   150: iconst_2
    //   151: anewarray java/lang/Object
    //   154: dup_x2
    //   155: dup_x2
    //   156: pop
    //   157: invokestatic valueOf : (D)Ljava/lang/Double;
    //   160: iconst_1
    //   161: swap
    //   162: aastore
    //   163: dup_x2
    //   164: dup_x2
    //   165: pop
    //   166: invokestatic valueOf : (J)Ljava/lang/Long;
    //   169: iconst_0
    //   170: swap
    //   171: aastore
    //   172: invokestatic k : ([Ljava/lang/Object;)V
    //   175: return
    // Exception table:
    //   from	to	target	type
    //   29	45	48	wtf/opal/x5
    //   42	70	73	wtf/opal/x5
    //   77	85	88	wtf/opal/x5
    //   93	107	110	wtf/opal/x5
    //   114	126	129	wtf/opal/x5
  }
  
  static {
    long l = a ^ 0x7427C1FE7F2CL;
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opa\\uu.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */