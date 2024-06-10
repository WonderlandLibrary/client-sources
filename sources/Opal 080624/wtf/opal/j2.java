package wtf.opal;

import de.jcm.discordgamesdk.LogLevel;
import de.jcm.discordgamesdk.activity.Activity;
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
import org.slf4j.Logger;

public final class j2 extends d {
  private final Logger L;
  
  private Activity b;
  
  private boolean y;
  
  private static String I;
  
  private static final long a = on.a(8790043500106300094L, -2939616363109170648L, MethodHandles.lookup().lookupClass()).a(187795546577909L);
  
  private static final String[] d;
  
  private static final String[] f;
  
  private static final Map g = new HashMap<>(13);
  
  private static final long k;
  
  public j2(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/j2.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 48377573959548
    //   11: lxor
    //   12: lstore_3
    //   13: pop2
    //   14: aload_0
    //   15: sipush #21006
    //   18: ldc2_w 1440173819269508006
    //   21: lload_1
    //   22: lxor
    //   23: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   28: lload_3
    //   29: sipush #16006
    //   32: ldc2_w 2874701757704472362
    //   35: lload_1
    //   36: lxor
    //   37: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   42: getstatic wtf/opal/kn.OTHER : Lwtf/opal/kn;
    //   45: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   48: aload_0
    //   49: invokestatic getLogger : ()Lorg/slf4j/Logger;
    //   52: putfield L : Lorg/slf4j/Logger;
    //   55: return
  }
  
  protected void K(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x7AAA8610478AL;
    long l3 = l1 ^ 0x0L;
    new Object[1];
    k(new Object[] { Long.valueOf(l2) });
    new Object[1];
    super.K(new Object[] { Long.valueOf(l3) });
  }
  
  protected void z(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    long l2 = l1 ^ 0x0L;
    this.y = false;
    new Object[1];
    super.z(new Object[] { Long.valueOf(l2) });
  }
  
  private void k(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/j2.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: invokestatic z : ()Ljava/lang/String;
    //   21: astore #4
    //   23: aload_0
    //   24: aload #4
    //   26: ifnonnull -> 48
    //   29: getfield y : Z
    //   32: ifeq -> 47
    //   35: goto -> 42
    //   38: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   41: athrow
    //   42: return
    //   43: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   46: athrow
    //   47: aload_0
    //   48: iconst_1
    //   49: putfield y : Z
    //   52: new de/jcm/discordgamesdk/CreateParams
    //   55: dup
    //   56: invokespecial <init> : ()V
    //   59: astore #5
    //   61: aload #5
    //   63: getstatic wtf/opal/j2.k : J
    //   66: invokevirtual setClientID : (J)V
    //   69: aload #5
    //   71: iconst_1
    //   72: anewarray de/jcm/discordgamesdk/CreateParams$Flags
    //   75: dup
    //   76: iconst_0
    //   77: getstatic de/jcm/discordgamesdk/CreateParams$Flags.NO_REQUIRE_DISCORD : Lde/jcm/discordgamesdk/CreateParams$Flags;
    //   80: aastore
    //   81: invokevirtual setFlags : ([Lde/jcm/discordgamesdk/CreateParams$Flags;)V
    //   84: new de/jcm/discordgamesdk/Core
    //   87: dup
    //   88: aload #5
    //   90: invokespecial <init> : (Lde/jcm/discordgamesdk/CreateParams;)V
    //   93: astore #6
    //   95: aload #6
    //   97: getstatic de/jcm/discordgamesdk/LogLevel.INFO : Lde/jcm/discordgamesdk/LogLevel;
    //   100: aload_0
    //   101: <illegal opcode> accept : (Lwtf/opal/j2;)Ljava/util/function/BiConsumer;
    //   106: invokevirtual setLogHook : (Lde/jcm/discordgamesdk/LogLevel;Ljava/util/function/BiConsumer;)V
    //   109: aload_0
    //   110: new de/jcm/discordgamesdk/activity/Activity
    //   113: dup
    //   114: invokespecial <init> : ()V
    //   117: putfield b : Lde/jcm/discordgamesdk/activity/Activity;
    //   120: aload_0
    //   121: getfield b : Lde/jcm/discordgamesdk/activity/Activity;
    //   124: invokevirtual timestamps : ()Lde/jcm/discordgamesdk/activity/ActivityTimestamps;
    //   127: invokestatic now : ()Ljava/time/Instant;
    //   130: invokevirtual setStart : (Ljava/time/Instant;)V
    //   133: aload_0
    //   134: getfield b : Lde/jcm/discordgamesdk/activity/Activity;
    //   137: invokevirtual assets : ()Lde/jcm/discordgamesdk/activity/ActivityAssets;
    //   140: sipush #29050
    //   143: ldc2_w 4367334934002778553
    //   146: lload_2
    //   147: lxor
    //   148: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   153: invokevirtual setLargeImage : (Ljava/lang/String;)V
    //   156: aload_0
    //   157: getfield b : Lde/jcm/discordgamesdk/activity/Activity;
    //   160: invokevirtual assets : ()Lde/jcm/discordgamesdk/activity/ActivityAssets;
    //   163: sipush #31610
    //   166: ldc2_w 8813951142876990394
    //   169: lload_2
    //   170: lxor
    //   171: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   176: invokevirtual setLargeText : (Ljava/lang/String;)V
    //   179: aload #6
    //   181: invokevirtual activityManager : ()Lde/jcm/discordgamesdk/ActivityManager;
    //   184: aload_0
    //   185: getfield b : Lde/jcm/discordgamesdk/activity/Activity;
    //   188: invokevirtual updateActivity : (Lde/jcm/discordgamesdk/activity/Activity;)V
    //   191: new wtf/opal/pz
    //   194: dup
    //   195: aload_0
    //   196: sipush #1238
    //   199: ldc2_w 6996575936096515092
    //   202: lload_2
    //   203: lxor
    //   204: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   209: aload #6
    //   211: invokespecial <init> : (Lwtf/opal/j2;Ljava/lang/String;Lde/jcm/discordgamesdk/Core;)V
    //   214: invokevirtual start : ()V
    //   217: goto -> 232
    //   220: astore #5
    //   222: aload #5
    //   224: invokevirtual printStackTrace : ()V
    //   227: aload_0
    //   228: iconst_0
    //   229: putfield y : Z
    //   232: return
    // Exception table:
    //   from	to	target	type
    //   23	35	38	java/lang/Exception
    //   29	43	43	java/lang/Exception
    //   52	217	220	java/lang/Exception
  }
  
  private void lambda$setup$0(LogLevel paramLogLevel, String paramString) {
    // Byte code:
    //   0: getstatic wtf/opal/j2.a : J
    //   3: ldc2_w 77377761407440
    //   6: lxor
    //   7: lstore_3
    //   8: invokestatic z : ()Ljava/lang/String;
    //   11: astore #5
    //   13: aload #5
    //   15: ifnonnull -> 73
    //   18: getstatic wtf/opal/bz.F : [I
    //   21: aload_1
    //   22: invokevirtual ordinal : ()I
    //   25: iaload
    //   26: tableswitch default -> 117, 1 -> 56, 2 -> 78, 3 -> 100
    //   52: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   55: athrow
    //   56: aload_0
    //   57: getfield L : Lorg/slf4j/Logger;
    //   60: aload_2
    //   61: invokeinterface error : (Ljava/lang/String;)V
    //   66: goto -> 73
    //   69: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   72: athrow
    //   73: aload #5
    //   75: ifnull -> 117
    //   78: aload_0
    //   79: getfield L : Lorg/slf4j/Logger;
    //   82: aload_2
    //   83: invokeinterface warn : (Ljava/lang/String;)V
    //   88: aload #5
    //   90: ifnull -> 117
    //   93: goto -> 100
    //   96: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   99: athrow
    //   100: aload_0
    //   101: getfield L : Lorg/slf4j/Logger;
    //   104: aload_2
    //   105: invokeinterface info : (Ljava/lang/String;)V
    //   110: goto -> 117
    //   113: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   116: athrow
    //   117: return
    // Exception table:
    //   from	to	target	type
    //   13	52	52	wtf/opal/x5
    //   18	66	69	wtf/opal/x5
    //   73	93	96	wtf/opal/x5
    //   78	110	113	wtf/opal/x5
  }
  
  public static void G(String paramString) {
    I = paramString;
  }
  
  public static String z() {
    return I;
  }
  
  static {
    G(null);
    long l = a ^ 0x75B3DADF07D2L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "ìo\017\023zª}¥è©ÅºÙBó\020î©\032 ¾II/\034ÀEû\036p|­ß9Õ£W¶\023vAKøÁÜ½©|W<\027Ð«8ß´4\020høÁm4\bæÅ¦I¬Cdl\020Ê&(½æ¯ïÕÖË!\000ó").length();
    byte b2 = 64;
    byte b = -1;
    while (true);
  }
  
  private static Exception a(Exception paramException) {
    return paramException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x4B9;
    if (f[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])g.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/j2", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = d[i].getBytes("ISO-8859-1");
      f[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return f[i];
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
    //   65: ldc_w 'wtf/opal/j2'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j2.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */