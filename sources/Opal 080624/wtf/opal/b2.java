package wtf.opal;

import java.lang.invoke.MethodHandles;
import javax.crypto.Cipher;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public final class b2 extends Record {
  private final double u;
  
  private final double t;
  
  private final double f;
  
  private final double x;
  
  private static final long a = on.a(1689460801542473914L, 3726669797299118908L, MethodHandles.lookup().lookupClass()).a(246838813923787L);
  
  private static final String b;
  
  public b2(double paramDouble1, double paramDouble2, double paramDouble3, double paramDouble4) {
    this.u = paramDouble1;
    this.t = paramDouble2;
    this.f = paramDouble3;
    this.x = paramDouble4;
  }
  
  public void a() {
    // Byte code:
    //   0: getstatic wtf/opal/b2.a : J
    //   3: ldc2_w 127343268953158
    //   6: lxor
    //   7: lstore_1
    //   8: invokestatic P : ()Z
    //   11: istore_3
    //   12: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   15: getfield field_1687 : Lnet/minecraft/class_638;
    //   18: ifnonnull -> 26
    //   21: return
    //   22: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   25: athrow
    //   26: aload_0
    //   27: getfield u : D
    //   30: aload_0
    //   31: getfield t : D
    //   34: aload_0
    //   35: getfield x : D
    //   38: dadd
    //   39: aload_0
    //   40: getfield f : D
    //   43: invokestatic method_49637 : (DDD)Lnet/minecraft/class_2338;
    //   46: astore #4
    //   48: getstatic wtf/opal/u.V : [I
    //   51: getstatic wtf/opal/ds.J : Lwtf/opal/j6;
    //   54: iconst_0
    //   55: anewarray java/lang/Object
    //   58: invokevirtual r : ([Ljava/lang/Object;)Lwtf/opal/bm;
    //   61: invokevirtual ordinal : ()I
    //   64: iaload
    //   65: lookupswitch default -> 310, 1 -> 92, 2 -> 175
    //   92: getstatic net/minecraft/class_2246.field_10002 : Lnet/minecraft/class_2248;
    //   95: invokevirtual method_9564 : ()Lnet/minecraft/class_2680;
    //   98: astore #5
    //   100: getstatic net/minecraft/class_2246.field_10002 : Lnet/minecraft/class_2248;
    //   103: invokevirtual method_9564 : ()Lnet/minecraft/class_2680;
    //   106: invokevirtual method_26231 : ()Lnet/minecraft/class_2498;
    //   109: astore #6
    //   111: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   114: getfield field_1687 : Lnet/minecraft/class_638;
    //   117: aload_0
    //   118: getfield u : D
    //   121: aload_0
    //   122: getfield t : D
    //   125: aload_0
    //   126: getfield f : D
    //   129: aload #6
    //   131: invokevirtual method_10595 : ()Lnet/minecraft/class_3414;
    //   134: getstatic net/minecraft/class_3419.field_15245 : Lnet/minecraft/class_3419;
    //   137: aload #6
    //   139: invokevirtual method_10597 : ()F
    //   142: fconst_1
    //   143: fadd
    //   144: fconst_2
    //   145: fdiv
    //   146: aload #6
    //   148: invokevirtual method_10599 : ()F
    //   151: ldc 0.8
    //   153: fmul
    //   154: iconst_0
    //   155: invokevirtual method_8486 : (DDDLnet/minecraft/class_3414;Lnet/minecraft/class_3419;FFZ)V
    //   158: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   161: getfield field_1687 : Lnet/minecraft/class_638;
    //   164: aload #4
    //   166: aload #5
    //   168: invokevirtual method_31595 : (Lnet/minecraft/class_2338;Lnet/minecraft/class_2680;)V
    //   171: iload_3
    //   172: ifne -> 310
    //   175: iconst_1
    //   176: putstatic wtf/opal/ds.b : Z
    //   179: getstatic net/minecraft/class_1299.field_6112 : Lnet/minecraft/class_1299;
    //   182: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   185: getfield field_1687 : Lnet/minecraft/class_638;
    //   188: invokevirtual method_5883 : (Lnet/minecraft/class_1937;)Lnet/minecraft/class_1297;
    //   191: checkcast net/minecraft/class_1538
    //   194: astore #7
    //   196: aload #7
    //   198: aload_0
    //   199: getfield u : D
    //   202: aload_0
    //   203: getfield t : D
    //   206: aload_0
    //   207: getfield f : D
    //   210: invokevirtual method_24203 : (DDD)V
    //   213: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   216: getfield field_1687 : Lnet/minecraft/class_638;
    //   219: aload #7
    //   221: invokevirtual method_53875 : (Lnet/minecraft/class_1297;)V
    //   224: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   227: getfield field_1687 : Lnet/minecraft/class_638;
    //   230: aload_0
    //   231: getfield u : D
    //   234: aload_0
    //   235: getfield t : D
    //   238: aload_0
    //   239: getfield f : D
    //   242: getstatic net/minecraft/class_3417.field_14865 : Lnet/minecraft/class_3414;
    //   245: getstatic net/minecraft/class_3419.field_15252 : Lnet/minecraft/class_3419;
    //   248: ldc 0.5
    //   250: fconst_1
    //   251: iconst_0
    //   252: invokevirtual method_8486 : (DDDLnet/minecraft/class_3414;Lnet/minecraft/class_3419;FFZ)V
    //   255: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   258: getfield field_1687 : Lnet/minecraft/class_638;
    //   261: aload_0
    //   262: getfield u : D
    //   265: aload_0
    //   266: getfield t : D
    //   269: aload_0
    //   270: getfield f : D
    //   273: getstatic net/minecraft/class_3417.field_14956 : Lnet/minecraft/class_3414;
    //   276: getstatic net/minecraft/class_3419.field_15252 : Lnet/minecraft/class_3419;
    //   279: fconst_1
    //   280: ldc 0.5
    //   282: getstatic wtf/opal/ds.O : Ljava/util/Random;
    //   285: invokevirtual nextFloat : ()F
    //   288: ldc 0.2
    //   290: fmul
    //   291: fadd
    //   292: iconst_0
    //   293: invokevirtual method_8486 : (DDDLnet/minecraft/class_3414;Lnet/minecraft/class_3419;FFZ)V
    //   296: new wtf/opal/k_
    //   299: dup
    //   300: aload_0
    //   301: getstatic wtf/opal/b2.b : Ljava/lang/String;
    //   304: invokespecial <init> : (Lwtf/opal/b2;Ljava/lang/String;)V
    //   307: invokevirtual start : ()V
    //   310: goto -> 315
    //   313: astore #5
    //   315: return
    // Exception table:
    //   from	to	target	type
    //   12	22	22	java/lang/Exception
    //   48	310	313	java/lang/Exception
  }
  
  public final String toString() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> toString : (Lwtf/opal/b2;)Ljava/lang/String;
    //   6: areturn
  }
  
  public final int hashCode() {
    // Byte code:
    //   0: aload_0
    //   1: <illegal opcode> hashCode : (Lwtf/opal/b2;)I
    //   6: ireturn
  }
  
  public final boolean equals(Object paramObject) {
    // Byte code:
    //   0: aload_0
    //   1: aload_1
    //   2: <illegal opcode> equals : (Lwtf/opal/b2;Ljava/lang/Object;)Z
    //   7: ireturn
  }
  
  public double u() {
    return this.u;
  }
  
  public double T() {
    return this.t;
  }
  
  public double F() {
    return this.f;
  }
  
  public double x() {
    return this.x;
  }
  
  static {
    long l = a ^ 0xE02CEE69C96L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b = 1; b < 8; b++)
      (new byte[8])[b] = (byte)(int)(l << b * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
  }
  
  private static String a(byte[] paramArrayOfbyte) {
    byte b1 = 0;
    int i;
    char[] arrayOfChar = new char[i = paramArrayOfbyte.length];
    for (byte b3 = 0; b3 < i; b3++) {
      int j;
      if ((j = 0xFF & paramArrayOfbyte[b3]) < 192) {
        arrayOfChar[b1++] = (char)j;
      } else if (j < 224) {
        char c = (char)((char)(j & 0x1F) << 6);
        j = paramArrayOfbyte[++b3];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } else if (b3 < i - 2) {
        char c = (char)((char)(j & 0xF) << 12);
        j = paramArrayOfbyte[++b3];
        c = (char)(c | (char)(j & 0x3F) << 6);
        j = paramArrayOfbyte[++b3];
        c = (char)(c | (char)(j & 0x3F));
        arrayOfChar[b1++] = c;
      } 
    } 
    return new String(arrayOfChar, 0, b1);
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\b2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */