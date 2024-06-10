package wtf.opal;

import de.jcm.discordgamesdk.Core;
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

class pz extends Thread {
  final Core M;
  
  final j2 D;
  
  private static final long a = on.a(-3720494960824056070L, 2703219007445550722L, MethodHandles.lookup().lookupClass()).a(229201757691178L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long e;
  
  pz(j2 paramj2, String paramString, Core paramCore) {
    super(paramString);
  }
  
  public void run() {
    // Byte code:
    //   0: getstatic wtf/opal/pz.a : J
    //   3: ldc2_w 32926616173568
    //   6: lxor
    //   7: lstore_1
    //   8: invokestatic z : ()Ljava/lang/String;
    //   11: astore_3
    //   12: aload_0
    //   13: getfield D : Lwtf/opal/j2;
    //   16: getfield y : Z
    //   19: ifeq -> 247
    //   22: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   25: invokevirtual method_1558 : ()Lnet/minecraft/class_642;
    //   28: astore #4
    //   30: aload_3
    //   31: ifnonnull -> 254
    //   34: aload_3
    //   35: ifnonnull -> 101
    //   38: goto -> 45
    //   41: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   44: athrow
    //   45: aload #4
    //   47: ifnull -> 105
    //   50: goto -> 57
    //   53: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   56: athrow
    //   57: aload_0
    //   58: getfield D : Lwtf/opal/j2;
    //   61: getfield b : Lde/jcm/discordgamesdk/activity/Activity;
    //   64: aload #4
    //   66: getfield field_3761 : Ljava/lang/String;
    //   69: invokevirtual toLowerCase : ()Ljava/lang/String;
    //   72: sipush #6430
    //   75: ldc2_w 6725051690674328329
    //   78: lload_1
    //   79: lxor
    //   80: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   85: swap
    //   86: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   91: invokevirtual setState : (Ljava/lang/String;)V
    //   94: goto -> 101
    //   97: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   100: athrow
    //   101: aload_3
    //   102: ifnull -> 185
    //   105: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   108: invokevirtual method_1542 : ()Z
    //   111: ifeq -> 155
    //   114: goto -> 121
    //   117: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   120: athrow
    //   121: aload_0
    //   122: getfield D : Lwtf/opal/j2;
    //   125: getfield b : Lde/jcm/discordgamesdk/activity/Activity;
    //   128: sipush #31395
    //   131: ldc2_w 8784570608808489142
    //   134: lload_1
    //   135: lxor
    //   136: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   141: invokevirtual setState : (Ljava/lang/String;)V
    //   144: aload_3
    //   145: ifnull -> 185
    //   148: goto -> 155
    //   151: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   154: athrow
    //   155: aload_0
    //   156: getfield D : Lwtf/opal/j2;
    //   159: getfield b : Lde/jcm/discordgamesdk/activity/Activity;
    //   162: sipush #30227
    //   165: ldc2_w 2428972413082369029
    //   168: lload_1
    //   169: lxor
    //   170: <illegal opcode> n : (IJ)Ljava/lang/String;
    //   175: invokevirtual setState : (Ljava/lang/String;)V
    //   178: goto -> 185
    //   181: invokestatic a : (Ljava/lang/IllegalStateException;)Ljava/lang/IllegalStateException;
    //   184: athrow
    //   185: aload_0
    //   186: getfield M : Lde/jcm/discordgamesdk/Core;
    //   189: invokevirtual activityManager : ()Lde/jcm/discordgamesdk/ActivityManager;
    //   192: aload_0
    //   193: getfield D : Lwtf/opal/j2;
    //   196: getfield b : Lde/jcm/discordgamesdk/activity/Activity;
    //   199: invokevirtual updateActivity : (Lde/jcm/discordgamesdk/activity/Activity;)V
    //   202: aload_0
    //   203: getfield M : Lde/jcm/discordgamesdk/Core;
    //   206: invokevirtual runCallbacks : ()V
    //   209: getstatic wtf/opal/pz.e : J
    //   212: invokestatic sleep : (J)V
    //   215: goto -> 12
    //   218: astore #4
    //   220: aload #4
    //   222: invokevirtual printStackTrace : ()V
    //   225: aload_0
    //   226: getfield D : Lwtf/opal/j2;
    //   229: iconst_0
    //   230: putfield y : Z
    //   233: goto -> 247
    //   236: astore #4
    //   238: aload #4
    //   240: invokevirtual printStackTrace : ()V
    //   243: aload_3
    //   244: ifnull -> 12
    //   247: aload_0
    //   248: getfield M : Lde/jcm/discordgamesdk/Core;
    //   251: invokevirtual close : ()V
    //   254: return
    // Exception table:
    //   from	to	target	type
    //   22	215	218	java/lang/IllegalStateException
    //   22	215	236	java/lang/Exception
    //   30	38	41	java/lang/IllegalStateException
    //   34	50	53	java/lang/IllegalStateException
    //   45	94	97	java/lang/IllegalStateException
    //   101	114	117	java/lang/IllegalStateException
    //   105	148	151	java/lang/IllegalStateException
    //   121	178	181	java/lang/IllegalStateException
  }
  
  static {
    long l = a ^ 0x3DB8C63F9406L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[3];
    boolean bool = false;
    String str;
    int i = (str = "]á§QÚw5ÐË~K_EpF\016ÜFTÒÐIDp\022 {\005,GÜnÒ\025ÇLª\016ôìO>nAdîyßÅ çWÛ(9÷ói\\>Äì¯y(ß¥ßDÚï3¼\026").length();
    byte b2 = 32;
    byte b = -1;
    while (true);
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x7247;
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
        throw new RuntimeException("wtf/opal/pz", exception);
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
    //   66: ldc_w 'wtf/opal/pz'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\pz.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */