package wtf.opal;

import com.mojang.authlib.GameProfile;
import dev.jnic.eEZCNM.JNICLoader;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.fabricmc.fabric.api.client.message.v1.ClientReceiveMessageEvents;
import net.fabricmc.fabric.api.client.screen.v1.ScreenEvents;
import net.minecraft.class_2556;
import net.minecraft.class_2561;
import net.minecraft.class_310;
import net.minecraft.class_332;
import net.minecraft.class_437;
import net.minecraft.class_7471;

public final class d1 {
  private static d1 J;
  
  private x2 X;
  
  private uw Q;
  
  private gc B;
  
  private ko z;
  
  private d3 H;
  
  private dm x;
  
  private lo t;
  
  private pa F;
  
  private lj i;
  
  private dc W;
  
  private bu a;
  
  private un u;
  
  private u5 g;
  
  private l2 V;
  
  private xt f;
  
  private lf R;
  
  private boolean E;
  
  private static int D;
  
  private static long b;
  
  private static String[] c;
  
  private static String[] d;
  
  private static Map e;
  
  private static long[] h;
  
  private static Integer[] j;
  
  private static Map k;
  
  public d1(short paramShort, int paramInt, char paramChar) {
    // Byte code:
    //   0: iload_1
    //   1: i2l
    //   2: bipush #48
    //   4: lshl
    //   5: iload_2
    //   6: i2l
    //   7: bipush #32
    //   9: lshl
    //   10: bipush #16
    //   12: lushr
    //   13: lor
    //   14: iload_3
    //   15: i2l
    //   16: bipush #48
    //   18: lshl
    //   19: bipush #48
    //   21: lushr
    //   22: lor
    //   23: getstatic wtf/opal/d1.b : J
    //   26: lxor
    //   27: lstore #4
    //   29: lload #4
    //   31: dup2
    //   32: ldc2_w 121406169622354
    //   35: lxor
    //   36: lstore #6
    //   38: pop2
    //   39: invokestatic V : ()Ljava/lang/String;
    //   42: aload_0
    //   43: invokespecial <init> : ()V
    //   46: aload_0
    //   47: iconst_1
    //   48: putfield E : Z
    //   51: astore #8
    //   53: getstatic wtf/opal/d1.J : Lwtf/opal/d1;
    //   56: aload #8
    //   58: ifnull -> 102
    //   61: ifnull -> 97
    //   64: goto -> 71
    //   67: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   70: athrow
    //   71: new java/lang/IllegalStateException
    //   74: dup
    //   75: sipush #22471
    //   78: ldc2_w 1290199893745421061
    //   81: lload #4
    //   83: lxor
    //   84: <illegal opcode> j : (IJ)Ljava/lang/String;
    //   89: invokespecial <init> : (Ljava/lang/String;)V
    //   92: athrow
    //   93: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   96: athrow
    //   97: aload_0
    //   98: putstatic wtf/opal/d1.J : Lwtf/opal/d1;
    //   101: aload_0
    //   102: lload #6
    //   104: iconst_1
    //   105: anewarray java/lang/Object
    //   108: dup_x2
    //   109: dup_x2
    //   110: pop
    //   111: invokestatic valueOf : (J)Ljava/lang/Long;
    //   114: iconst_0
    //   115: swap
    //   116: aastore
    //   117: invokevirtual Z : ([Ljava/lang/Object;)V
    //   120: iload_2
    //   121: ifle -> 136
    //   124: aload #8
    //   126: ifnonnull -> 143
    //   129: iconst_4
    //   130: anewarray wtf/opal/d
    //   133: invokestatic p : ([Lwtf/opal/d;)V
    //   136: goto -> 143
    //   139: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   142: athrow
    //   143: return
    // Exception table:
    //   from	to	target	type
    //   53	64	67	java/lang/IllegalStateException
    //   61	93	93	java/lang/IllegalStateException
    //   102	136	139	java/lang/IllegalStateException
  }
  
  private native void Z(Object[] paramArrayOfObject);
  
  private void E(Object[] paramArrayOfObject) {
    ScreenEvents.BEFORE_INIT.register(this::lambda$registerFabricEvents$4);
    ClientReceiveMessageEvents.ALLOW_CHAT.register(this::lambda$registerFabricEvents$5);
    ClientReceiveMessageEvents.ALLOW_GAME.register(this::lambda$registerFabricEvents$6);
  }
  
  public static d1 q(Object[] paramArrayOfObject) {
    return J;
  }
  
  public void c(Object[] paramArrayOfObject) {
    this.B.w(new Object[0]);
  }
  
  public boolean L(Object[] paramArrayOfObject) {
    return this.E;
  }
  
  public gc c(Object[] paramArrayOfObject) {
    return this.B;
  }
  
  public dm q(Object[] paramArrayOfObject) {
    return this.x;
  }
  
  public l2 S(Object[] paramArrayOfObject) {
    return this.V;
  }
  
  public x2 x(Object[] paramArrayOfObject) {
    return this.X;
  }
  
  public uw F(Object[] paramArrayOfObject) {
    return this.Q;
  }
  
  public pa z(Object[] paramArrayOfObject) {
    return this.F;
  }
  
  public lj s(Object[] paramArrayOfObject) {
    return this.i;
  }
  
  public u5 i(Object[] paramArrayOfObject) {
    return this.g;
  }
  
  public un l(Object[] paramArrayOfObject) {
    return this.u;
  }
  
  public lo h(Object[] paramArrayOfObject) {
    return this.t;
  }
  
  public xt p(Object[] paramArrayOfObject) {
    return this.f;
  }
  
  public ko q(Object[] paramArrayOfObject) {
    return this.z;
  }
  
  public dc D(Object[] paramArrayOfObject) {
    return this.W;
  }
  
  public bu h(Object[] paramArrayOfObject) {
    return this.a;
  }
  
  public lf C(Object[] paramArrayOfObject) {
    return this.R;
  }
  
  private boolean lambda$registerFabricEvents$6(class_2561 paramclass_2561, boolean paramBoolean) {
    // Byte code:
    //   0: getstatic wtf/opal/d1.b : J
    //   3: ldc2_w 66134531620612
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 39524009156672
    //   13: lxor
    //   14: lstore #5
    //   16: pop2
    //   17: invokestatic V : ()Ljava/lang/String;
    //   20: new wtf/opal/l7
    //   23: dup
    //   24: aload_1
    //   25: iload_2
    //   26: invokespecial <init> : (Lnet/minecraft/class_2561;Z)V
    //   29: astore #8
    //   31: astore #7
    //   33: aload_0
    //   34: getfield x : Lwtf/opal/dm;
    //   37: lload #5
    //   39: aload #8
    //   41: iconst_2
    //   42: anewarray java/lang/Object
    //   45: dup_x1
    //   46: swap
    //   47: iconst_1
    //   48: swap
    //   49: aastore
    //   50: dup_x2
    //   51: dup_x2
    //   52: pop
    //   53: invokestatic valueOf : (J)Ljava/lang/Long;
    //   56: iconst_0
    //   57: swap
    //   58: aastore
    //   59: invokevirtual N : ([Ljava/lang/Object;)V
    //   62: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   65: getfield field_1724 : Lnet/minecraft/class_746;
    //   68: ifnull -> 136
    //   71: aload #8
    //   73: iconst_0
    //   74: anewarray java/lang/Object
    //   77: invokevirtual G : ([Ljava/lang/Object;)Z
    //   80: aload #7
    //   82: ifnull -> 145
    //   85: goto -> 92
    //   88: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   91: athrow
    //   92: iload_2
    //   93: if_icmpeq -> 136
    //   96: goto -> 103
    //   99: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   102: athrow
    //   103: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   106: getfield field_1724 : Lnet/minecraft/class_746;
    //   109: aload #8
    //   111: iconst_0
    //   112: anewarray java/lang/Object
    //   115: invokevirtual T : ([Ljava/lang/Object;)Lnet/minecraft/class_2561;
    //   118: aload #8
    //   120: iconst_0
    //   121: anewarray java/lang/Object
    //   124: invokevirtual G : ([Ljava/lang/Object;)Z
    //   127: invokevirtual method_7353 : (Lnet/minecraft/class_2561;Z)V
    //   130: iconst_0
    //   131: ireturn
    //   132: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   135: athrow
    //   136: aload #8
    //   138: iconst_0
    //   139: anewarray java/lang/Object
    //   142: invokevirtual X : ([Ljava/lang/Object;)Z
    //   145: aload #7
    //   147: ifnull -> 161
    //   150: ifne -> 164
    //   153: goto -> 160
    //   156: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   159: athrow
    //   160: iconst_1
    //   161: goto -> 165
    //   164: iconst_0
    //   165: ireturn
    // Exception table:
    //   from	to	target	type
    //   33	85	88	java/lang/IllegalStateException
    //   71	96	99	java/lang/IllegalStateException
    //   92	132	132	java/lang/IllegalStateException
    //   145	153	156	java/lang/IllegalStateException
  }
  
  private boolean lambda$registerFabricEvents$5(class_2561 paramclass_2561, class_7471 paramclass_7471, GameProfile paramGameProfile, class_2556.class_7602 paramclass_7602, Instant paramInstant) {
    long l1 = b ^ 0x6C68331CCCC8L;
    long l3 = l1 ^ 0x73BC70D9D38CL;
    l7 l7 = new l7(paramclass_2561, false);
    String str = b9.V();
    try {
      (new Object[2])[1] = l7;
      new Object[2];
      this.x.N(new Object[] { Long.valueOf(l3) });
      if (str != null)
        if (!l7.X(new Object[0])) {
        
        } else {
          return false;
        }  
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
  }
  
  private void lambda$registerFabricEvents$4(class_310 paramclass_310, class_437 paramclass_437, int paramInt1, int paramInt2) {
    // Byte code:
    //   0: getstatic wtf/opal/d1.b : J
    //   3: ldc2_w 10699490817823
    //   6: lxor
    //   7: lstore #5
    //   9: lload #5
    //   11: dup2
    //   12: ldc2_w 84497209866490
    //   15: lxor
    //   16: dup2
    //   17: bipush #48
    //   19: lushr
    //   20: l2i
    //   21: istore #7
    //   23: dup2
    //   24: bipush #16
    //   26: lshl
    //   27: bipush #32
    //   29: lushr
    //   30: l2i
    //   31: istore #8
    //   33: dup2
    //   34: bipush #48
    //   36: lshl
    //   37: bipush #48
    //   39: lushr
    //   40: l2i
    //   41: istore #9
    //   43: pop2
    //   44: pop2
    //   45: invokestatic V : ()Ljava/lang/String;
    //   48: astore #10
    //   50: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   53: getfield field_1755 : Lnet/minecraft/class_437;
    //   56: instanceof net/minecraft/class_442
    //   59: aload #10
    //   61: ifnull -> 117
    //   64: ifeq -> 101
    //   67: goto -> 74
    //   70: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   73: athrow
    //   74: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   77: new wtf/opal/pq
    //   80: dup
    //   81: iload #7
    //   83: i2s
    //   84: iload #8
    //   86: iload #9
    //   88: invokespecial <init> : (SII)V
    //   91: invokevirtual method_1507 : (Lnet/minecraft/class_437;)V
    //   94: goto -> 101
    //   97: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   100: athrow
    //   101: aload_2
    //   102: aload #10
    //   104: ifnull -> 154
    //   107: instanceof net/minecraft/class_408
    //   110: goto -> 117
    //   113: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   116: athrow
    //   117: ifeq -> 166
    //   120: aload_2
    //   121: invokestatic afterRender : (Lnet/minecraft/class_437;)Lnet/fabricmc/fabric/api/event/Event;
    //   124: aload_0
    //   125: <illegal opcode> afterRender : (Lwtf/opal/d1;)Lnet/fabricmc/fabric/api/client/screen/v1/ScreenEvents$AfterRender;
    //   130: invokevirtual register : (Ljava/lang/Object;)V
    //   133: aload_2
    //   134: invokestatic afterMouseClick : (Lnet/minecraft/class_437;)Lnet/fabricmc/fabric/api/event/Event;
    //   137: aload_0
    //   138: <illegal opcode> afterMouseClick : (Lwtf/opal/d1;)Lnet/fabricmc/fabric/api/client/screen/v1/ScreenMouseEvents$AfterMouseClick;
    //   143: invokevirtual register : (Ljava/lang/Object;)V
    //   146: aload_2
    //   147: goto -> 154
    //   150: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   153: athrow
    //   154: invokestatic afterMouseRelease : (Lnet/minecraft/class_437;)Lnet/fabricmc/fabric/api/event/Event;
    //   157: aload_0
    //   158: <illegal opcode> afterMouseRelease : (Lwtf/opal/d1;)Lnet/fabricmc/fabric/api/client/screen/v1/ScreenMouseEvents$AfterMouseRelease;
    //   163: invokevirtual register : (Ljava/lang/Object;)V
    //   166: return
    // Exception table:
    //   from	to	target	type
    //   50	67	70	java/lang/IllegalStateException
    //   64	94	97	java/lang/IllegalStateException
    //   101	110	113	java/lang/IllegalStateException
    //   117	147	150	java/lang/IllegalStateException
  }
  
  private void lambda$registerFabricEvents$3(class_437 paramclass_437, double paramDouble1, double paramDouble2, int paramInt) {
    long l1 = b ^ 0x48B3806C454BL;
    long l3 = l1 ^ 0x1683A516AC60L;
    (new Object[2])[1] = Integer.valueOf(paramInt);
    new Object[2];
    this.i.T(new Object[] { Long.valueOf(l3) });
  }
  
  private void lambda$registerFabricEvents$2(class_437 paramclass_437, double paramDouble1, double paramDouble2, int paramInt) {
    long l1 = b ^ 0xB9C1FEFA25L;
    long l3 = l1 ^ 0x70DB520FC299L;
    new Object[4];
    (new Object[4])[3] = Long.valueOf(l3);
    (new Object[4])[2] = Integer.valueOf(paramInt);
    new Object[4];
    (new Object[4])[1] = Double.valueOf(paramDouble2);
    new Object[4];
    this.i.H(new Object[] { Double.valueOf(paramDouble1) });
  }
  
  private void lambda$registerFabricEvents$1(class_437 paramclass_437, class_332 paramclass_332, int paramInt1, int paramInt2, float paramFloat) {
    long l1 = b ^ 0x77D61E5EBB82L;
    long l3 = l1 ^ 0x70108DDA1424L;
    (new Object[3])[2] = Integer.valueOf(paramInt2);
    new Object[3];
    this.i.u(new Object[] { null, Long.valueOf(l3), Integer.valueOf(paramInt1) });
  }
  
  private void lambda$initialize$0() {
    this.B.w(new Object[0]);
  }
  
  public static void N(int paramInt) {
    D = paramInt;
  }
  
  public static int E() {
    return D;
  }
  
  public static int Y() {
    int i = E();
    try {
      if (i == 0)
        return 101; 
    } catch (IllegalStateException illegalStateException) {
      throw a(null);
    } 
    return 0;
  }
  
  private static IllegalStateException a(IllegalStateException paramIllegalStateException) {
    return paramIllegalStateException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4CEA;
    if (d[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])e.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          e.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/d1", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = c[i].getBytes("ISO-8859-1");
      d[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return d[i];
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
    //   66: ldc_w 'wtf/opal/d1'
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
  
  private static int b(int paramInt, long paramLong) {
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1C1B;
    if (j[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = h[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])k.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          k.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/d1", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      j[i] = Integer.valueOf(j);
    } 
    return j[i].intValue();
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
    //   66: ldc_w 'wtf/opal/d1'
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
  
  static {
    JNICLoader.init();
    $jnicLoader();
    $jnicClinit();
  }
}


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\d1.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */