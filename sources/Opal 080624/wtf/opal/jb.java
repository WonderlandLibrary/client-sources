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
import net.minecraft.class_1309;
import org.joml.Vector4d;
import oshi.util.tuples.Pair;

public final class jb extends d {
  private final ky<xg> B;
  
  public final ke f;
  
  private final ky<d8> J;
  
  public final k6 z;
  
  public final d2 Q;
  
  private class_1309 m;
  
  private class_1309 E;
  
  private float Z;
  
  private static final long a = on.a(-386092777516122906L, 4021662291970663853L, MethodHandles.lookup().lookupClass()).a(66177644559183L);
  
  private static final String[] b;
  
  private static final String[] d;
  
  private static final Map g = new HashMap<>(13);
  
  private static final long k;
  
  public jb(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/jb.a : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 38564236218683
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 107496177215925
    //   17: lxor
    //   18: dup2
    //   19: bipush #32
    //   21: lushr
    //   22: l2i
    //   23: istore #5
    //   25: dup2
    //   26: bipush #32
    //   28: lshl
    //   29: bipush #40
    //   31: lushr
    //   32: l2i
    //   33: istore #6
    //   35: dup2
    //   36: bipush #56
    //   38: lshl
    //   39: bipush #56
    //   41: lushr
    //   42: l2i
    //   43: istore #7
    //   45: pop2
    //   46: dup2
    //   47: ldc2_w 73589442085163
    //   50: lxor
    //   51: dup2
    //   52: bipush #56
    //   54: lushr
    //   55: l2i
    //   56: istore #8
    //   58: dup2
    //   59: bipush #8
    //   61: lshl
    //   62: bipush #8
    //   64: lushr
    //   65: lstore #9
    //   67: pop2
    //   68: dup2
    //   69: ldc2_w 109994002113095
    //   72: lxor
    //   73: lstore #11
    //   75: dup2
    //   76: ldc2_w 31329190294854
    //   79: lxor
    //   80: dup2
    //   81: bipush #48
    //   83: lushr
    //   84: l2i
    //   85: istore #13
    //   87: dup2
    //   88: bipush #16
    //   90: lshl
    //   91: bipush #48
    //   93: lushr
    //   94: l2i
    //   95: istore #14
    //   97: dup2
    //   98: bipush #32
    //   100: lshl
    //   101: bipush #32
    //   103: lushr
    //   104: l2i
    //   105: istore #15
    //   107: pop2
    //   108: dup2
    //   109: ldc2_w 81876757789458
    //   112: lxor
    //   113: lstore #16
    //   115: dup2
    //   116: ldc2_w 73374515158153
    //   119: lxor
    //   120: lstore #18
    //   122: pop2
    //   123: aload_0
    //   124: sipush #22248
    //   127: ldc2_w 893619653836529499
    //   130: lload_1
    //   131: lxor
    //   132: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   137: lload #16
    //   139: sipush #30419
    //   142: ldc2_w 6142763053323812711
    //   145: lload_1
    //   146: lxor
    //   147: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   152: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   155: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   158: aload_0
    //   159: new wtf/opal/ky
    //   162: dup
    //   163: sipush #23287
    //   166: ldc2_w 309027761138848582
    //   169: lload_1
    //   170: lxor
    //   171: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   176: aload_0
    //   177: getstatic wtf/opal/xg.OPAL : Lwtf/opal/xg;
    //   180: invokespecial <init> : (Ljava/lang/String;Lwtf/opal/d;Ljava/lang/Enum;)V
    //   183: putfield B : Lwtf/opal/ky;
    //   186: aload_0
    //   187: new wtf/opal/ke
    //   190: dup
    //   191: sipush #28668
    //   194: ldc2_w 4776709486594418252
    //   197: lload_1
    //   198: lxor
    //   199: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   204: iconst_1
    //   205: invokespecial <init> : (Ljava/lang/String;Z)V
    //   208: putfield f : Lwtf/opal/ke;
    //   211: aload_0
    //   212: new wtf/opal/ky
    //   215: dup
    //   216: sipush #10459
    //   219: ldc2_w 3371030272505422185
    //   222: lload_1
    //   223: lxor
    //   224: <illegal opcode> b : (IJ)Ljava/lang/String;
    //   229: getstatic wtf/opal/d8.MIDDLE : Lwtf/opal/d8;
    //   232: invokespecial <init> : (Ljava/lang/String;Ljava/lang/Enum;)V
    //   235: putfield J : Lwtf/opal/ky;
    //   238: aload_0
    //   239: new wtf/opal/k6
    //   242: dup
    //   243: iload #8
    //   245: i2b
    //   246: ldc 0.5
    //   248: lload #9
    //   250: ldc 0.5
    //   252: invokespecial <init> : (BFJF)V
    //   255: putfield z : Lwtf/opal/k6;
    //   258: aload_0
    //   259: new wtf/opal/d2
    //   262: dup
    //   263: iload #13
    //   265: i2s
    //   266: getstatic wtf/opal/jb.k : J
    //   269: l2i
    //   270: ldc2_w 0.5
    //   273: iload #14
    //   275: i2c
    //   276: iload #15
    //   278: fconst_1
    //   279: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   282: invokespecial <init> : (SIDCIFLwtf/opal/lp;)V
    //   285: putfield Q : Lwtf/opal/d2;
    //   288: aload_0
    //   289: iconst_4
    //   290: anewarray wtf/opal/k3
    //   293: dup
    //   294: iconst_0
    //   295: aload_0
    //   296: getfield B : Lwtf/opal/ky;
    //   299: aastore
    //   300: dup
    //   301: iconst_1
    //   302: aload_0
    //   303: getfield J : Lwtf/opal/ky;
    //   306: aastore
    //   307: dup
    //   308: iconst_2
    //   309: aload_0
    //   310: getfield f : Lwtf/opal/ke;
    //   313: aastore
    //   314: dup
    //   315: iconst_3
    //   316: aload_0
    //   317: getfield z : Lwtf/opal/k6;
    //   320: aastore
    //   321: lload #11
    //   323: dup2_x1
    //   324: pop2
    //   325: iconst_2
    //   326: anewarray java/lang/Object
    //   329: dup_x1
    //   330: swap
    //   331: iconst_1
    //   332: swap
    //   333: aastore
    //   334: dup_x2
    //   335: dup_x2
    //   336: pop
    //   337: invokestatic valueOf : (J)Ljava/lang/Long;
    //   340: iconst_0
    //   341: swap
    //   342: aastore
    //   343: invokevirtual o : ([Ljava/lang/Object;)V
    //   346: aload_0
    //   347: getfield J : Lwtf/opal/ky;
    //   350: aload_0
    //   351: getfield f : Lwtf/opal/ke;
    //   354: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   359: lload_3
    //   360: dup2_x1
    //   361: pop2
    //   362: iconst_3
    //   363: anewarray java/lang/Object
    //   366: dup_x1
    //   367: swap
    //   368: iconst_2
    //   369: swap
    //   370: aastore
    //   371: dup_x2
    //   372: dup_x2
    //   373: pop
    //   374: invokestatic valueOf : (J)Ljava/lang/Long;
    //   377: iconst_1
    //   378: swap
    //   379: aastore
    //   380: dup_x1
    //   381: swap
    //   382: iconst_0
    //   383: swap
    //   384: aastore
    //   385: invokevirtual C : ([Ljava/lang/Object;)V
    //   388: aload_0
    //   389: aload_0
    //   390: getfield B : Lwtf/opal/ky;
    //   393: iconst_3
    //   394: anewarray wtf/opal/u_
    //   397: dup
    //   398: iconst_0
    //   399: new wtf/opal/pk
    //   402: dup
    //   403: aload_0
    //   404: lload #18
    //   406: invokespecial <init> : (Lwtf/opal/jb;J)V
    //   409: aastore
    //   410: dup
    //   411: iconst_1
    //   412: new wtf/opal/ph
    //   415: dup
    //   416: aload_0
    //   417: invokespecial <init> : (Lwtf/opal/jb;)V
    //   420: aastore
    //   421: dup
    //   422: iconst_2
    //   423: new wtf/opal/px
    //   426: dup
    //   427: iload #5
    //   429: aload_0
    //   430: iload #6
    //   432: iload #7
    //   434: i2b
    //   435: invokespecial <init> : (ILwtf/opal/jb;IB)V
    //   438: aastore
    //   439: iconst_2
    //   440: anewarray java/lang/Object
    //   443: dup_x1
    //   444: swap
    //   445: iconst_1
    //   446: swap
    //   447: aastore
    //   448: dup_x1
    //   449: swap
    //   450: iconst_0
    //   451: swap
    //   452: aastore
    //   453: invokevirtual Y : ([Ljava/lang/Object;)V
    //   456: return
  }
  
  public class_1309 E(Object[] paramArrayOfObject) {
    // Byte code:
    //   0: aload_1
    //   1: dup
    //   2: iconst_0
    //   3: aaload
    //   4: checkcast java/lang/Long
    //   7: invokevirtual longValue : ()J
    //   10: lstore_2
    //   11: pop
    //   12: getstatic wtf/opal/jb.a : J
    //   15: lload_2
    //   16: lxor
    //   17: lstore_2
    //   18: lload_2
    //   19: dup2
    //   20: ldc2_w 66435645653155
    //   23: lxor
    //   24: lstore #4
    //   26: dup2
    //   27: ldc2_w 56992427345321
    //   30: lxor
    //   31: lstore #6
    //   33: pop2
    //   34: invokestatic S : ()Ljava/lang/String;
    //   37: astore #8
    //   39: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   42: getfield field_1755 : Lnet/minecraft/class_437;
    //   45: instanceof wtf/opal/x9
    //   48: ifeq -> 57
    //   51: aconst_null
    //   52: areturn
    //   53: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   56: athrow
    //   57: iconst_0
    //   58: anewarray java/lang/Object
    //   61: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   64: iconst_0
    //   65: anewarray java/lang/Object
    //   68: invokevirtual x : ([Ljava/lang/Object;)Lwtf/opal/x2;
    //   71: ldc wtf/opal/q
    //   73: iconst_1
    //   74: anewarray java/lang/Object
    //   77: dup_x1
    //   78: swap
    //   79: iconst_0
    //   80: swap
    //   81: aastore
    //   82: invokevirtual V : ([Ljava/lang/Object;)Lwtf/opal/d;
    //   85: checkcast wtf/opal/q
    //   88: astore #9
    //   90: aload_0
    //   91: aload #9
    //   93: iconst_0
    //   94: anewarray java/lang/Object
    //   97: invokevirtual U : ([Ljava/lang/Object;)Lnet/minecraft/class_1309;
    //   100: putfield m : Lnet/minecraft/class_1309;
    //   103: aload_0
    //   104: getfield m : Lnet/minecraft/class_1309;
    //   107: aload #8
    //   109: ifnonnull -> 217
    //   112: ifnonnull -> 213
    //   115: goto -> 122
    //   118: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   121: athrow
    //   122: lload_2
    //   123: lconst_0
    //   124: lcmp
    //   125: ifle -> 206
    //   128: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   131: getfield field_1755 : Lnet/minecraft/class_437;
    //   134: instanceof net/minecraft/class_408
    //   137: ifeq -> 175
    //   140: goto -> 147
    //   143: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   146: athrow
    //   147: aload_0
    //   148: lload_2
    //   149: lconst_0
    //   150: lcmp
    //   151: ifle -> 214
    //   154: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   157: getfield field_1724 : Lnet/minecraft/class_746;
    //   160: putfield m : Lnet/minecraft/class_1309;
    //   163: aload #8
    //   165: ifnull -> 213
    //   168: goto -> 175
    //   171: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   174: athrow
    //   175: aload_0
    //   176: getfield Q : Lwtf/opal/d2;
    //   179: lload #4
    //   181: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   184: iconst_2
    //   185: anewarray java/lang/Object
    //   188: dup_x1
    //   189: swap
    //   190: iconst_1
    //   191: swap
    //   192: aastore
    //   193: dup_x2
    //   194: dup_x2
    //   195: pop
    //   196: invokestatic valueOf : (J)Ljava/lang/Long;
    //   199: iconst_0
    //   200: swap
    //   201: aastore
    //   202: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   205: pop
    //   206: goto -> 213
    //   209: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   212: athrow
    //   213: aload_0
    //   214: getfield m : Lnet/minecraft/class_1309;
    //   217: astore #10
    //   219: lload_2
    //   220: lconst_0
    //   221: lcmp
    //   222: iflt -> 280
    //   225: aload #8
    //   227: ifnonnull -> 280
    //   230: aload #10
    //   232: ifnonnull -> 364
    //   235: goto -> 242
    //   238: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   241: athrow
    //   242: aload_0
    //   243: getfield Q : Lwtf/opal/d2;
    //   246: lload #4
    //   248: getstatic wtf/opal/lp.BACKWARDS : Lwtf/opal/lp;
    //   251: iconst_2
    //   252: anewarray java/lang/Object
    //   255: dup_x1
    //   256: swap
    //   257: iconst_1
    //   258: swap
    //   259: aastore
    //   260: dup_x2
    //   261: dup_x2
    //   262: pop
    //   263: invokestatic valueOf : (J)Ljava/lang/Long;
    //   266: iconst_0
    //   267: swap
    //   268: aastore
    //   269: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   272: pop
    //   273: goto -> 280
    //   276: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   279: athrow
    //   280: lload_2
    //   281: lconst_0
    //   282: lcmp
    //   283: ifle -> 359
    //   286: aload_0
    //   287: aload #8
    //   289: ifnonnull -> 348
    //   292: getfield Q : Lwtf/opal/d2;
    //   295: lload #6
    //   297: iconst_1
    //   298: anewarray java/lang/Object
    //   301: dup_x2
    //   302: dup_x2
    //   303: pop
    //   304: invokestatic valueOf : (J)Ljava/lang/Long;
    //   307: iconst_0
    //   308: swap
    //   309: aastore
    //   310: invokevirtual H : ([Ljava/lang/Object;)Z
    //   313: ifeq -> 340
    //   316: goto -> 323
    //   319: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   322: athrow
    //   323: aload_0
    //   324: aconst_null
    //   325: putfield E : Lnet/minecraft/class_1309;
    //   328: aload #8
    //   330: ifnull -> 402
    //   333: goto -> 340
    //   336: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   339: athrow
    //   340: aload_0
    //   341: goto -> 348
    //   344: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   347: athrow
    //   348: getfield E : Lnet/minecraft/class_1309;
    //   351: lload_2
    //   352: lconst_0
    //   353: lcmp
    //   354: ifle -> 404
    //   357: astore #10
    //   359: aload #8
    //   361: ifnull -> 402
    //   364: aload_0
    //   365: getfield Q : Lwtf/opal/d2;
    //   368: lload #4
    //   370: getstatic wtf/opal/lp.FORWARDS : Lwtf/opal/lp;
    //   373: iconst_2
    //   374: anewarray java/lang/Object
    //   377: dup_x1
    //   378: swap
    //   379: iconst_1
    //   380: swap
    //   381: aastore
    //   382: dup_x2
    //   383: dup_x2
    //   384: pop
    //   385: invokestatic valueOf : (J)Ljava/lang/Long;
    //   388: iconst_0
    //   389: swap
    //   390: aastore
    //   391: invokevirtual p : ([Ljava/lang/Object;)Lwtf/opal/dx;
    //   394: pop
    //   395: goto -> 402
    //   398: invokestatic a : (Ljava/lang/MatchException;)Ljava/lang/MatchException;
    //   401: athrow
    //   402: aload #10
    //   404: areturn
    // Exception table:
    //   from	to	target	type
    //   39	53	53	java/lang/MatchException
    //   90	115	118	java/lang/MatchException
    //   112	140	143	java/lang/MatchException
    //   122	168	171	java/lang/MatchException
    //   147	206	209	java/lang/MatchException
    //   219	235	238	java/lang/MatchException
    //   230	273	276	java/lang/MatchException
    //   280	316	319	java/lang/MatchException
    //   292	333	336	java/lang/MatchException
    //   323	341	344	java/lang/MatchException
    //   359	395	398	java/lang/MatchException
  }
  
  public float k(Object[] paramArrayOfObject) {
    return this.Z;
  }
  
  public void x(Object[] paramArrayOfObject) {
    long l1 = ((Long)paramArrayOfObject[0]).longValue();
    l1 = a ^ l1;
    long l2 = l1 ^ 0x1D926402CCBL;
    new Object[1];
    class_1309 class_13091 = E(new Object[] { Long.valueOf(l2) });
    String str = jt.S();
    try {
      if (str == null)
        if (class_13091 == null)
          return;  
    } catch (MatchException matchException) {
      throw a(null);
    } 
    float f = class_13091.method_6063() + class_13091.method_52541();
    try {
      if (l1 > 0L) {
        new Object[3];
        (new Object[3])[2] = Double.valueOf(0.05000000074505806D);
        new Object[3];
        (new Object[3])[1] = Double.valueOf((class_13091.method_6032() + class_13091.method_6067()));
        new Object[3];
        this.Z = Math.min(d6.Y(new Object[] { Double.valueOf(this.Z) }, ).floatValue(), f);
        if (str == null)
          try {
            if (this.m != null) {
            
            } else {
              return;
            } 
          } catch (MatchException matchException) {
            throw a(null);
          }  
      } 
    } catch (MatchException matchException) {
      throw a(null);
    } 
    this.E = this.m;
  }
  
  public String o(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    return ((xg)this.B.z()).toString();
  }
  
  public Pair K(Object[] paramArrayOfObject) {
    long l = ((Long)paramArrayOfObject[0]).longValue();
    Vector4d vector4d = (Vector4d)paramArrayOfObject[1];
    double d1 = ((Double)paramArrayOfObject[2]).doubleValue();
    double d3 = ((Double)paramArrayOfObject[3]).doubleValue();
    l = a ^ l;
    double d4 = vector4d.x;
    double d5 = vector4d.y;
    double d6 = vector4d.z - vector4d.x;
    double d7 = vector4d.w - vector4d.y;
    double d8 = d4 + d6 / 2.0D - d1 / 2.0D;
    double d9 = d5 + d7 / 2.0D - d3 / 2.0D;
    try {
      switch (((d8)this.J.z()).ordinal()) {
        default:
          throw new MatchException(null, null);
        case 0:
        
        case 1:
        
        case 2:
        
        case 3:
          break;
      } 
    } catch (MatchException matchException) {
      throw a(null);
    } 
    return new Pair(Double.valueOf(d4 + d6 - d1 / 4.0D), Double.valueOf(d9));
  }
  
  static {
    long l = a ^ 0x14A85408EE1EL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[5];
    boolean bool = false;
    String str;
    int i = (str = "m4¤¬\t7½s\013ZÖ1WTÁÕ|Î?{@]\022¼h5\020\b\r\036\006l¾B0Â?çCó]4Í\030Õ;¿>a\r²Í·^f\004Ùry[/ßÈÍpÄ").length();
    byte b2 = 32;
    byte b = -1;
    while (true);
  }
  
  private static MatchException a(MatchException paramMatchException) {
    return paramMatchException;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x68CB;
    if (d[i] == null) {
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
        throw new RuntimeException("wtf/opal/jb", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = b[i].getBytes("ISO-8859-1");
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
    //   65: ldc_w 'wtf/opal/jb'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\jb.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */