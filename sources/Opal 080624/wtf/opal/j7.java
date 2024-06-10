package wtf.opal;

import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentMap;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import net.minecraft.class_1297;
import net.minecraft.class_1657;
import org.json.JSONObject;

public final class j7 extends d {
  private final ConcurrentMap<String, br> l;
  
  private final kl P;
  
  private boolean s;
  
  private int E;
  
  private final pa D;
  
  private final bu b;
  
  private final gm<uj> a;
  
  private final gm<b6> n;
  
  private final gm<d4> W;
  
  private final gm<lu> Z;
  
  private static final long d = on.a(1385688339069040742L, 1295444468437058403L, MethodHandles.lookup().lookupClass()).a(222111959020316L);
  
  private static final String[] f;
  
  private static final String[] g;
  
  private static final Map k = new HashMap<>(13);
  
  private static final long[] m;
  
  private static final Integer[] o;
  
  private static final Map p;
  
  private static final long q;
  
  public j7(long paramLong) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: lload_1
    //   4: lxor
    //   5: lstore_1
    //   6: lload_1
    //   7: dup2
    //   8: ldc2_w 13525538592482
    //   11: lxor
    //   12: lstore_3
    //   13: dup2
    //   14: ldc2_w 37605568679863
    //   17: lxor
    //   18: lstore #5
    //   20: pop2
    //   21: aload_0
    //   22: sipush #17411
    //   25: ldc2_w 5858398929807504197
    //   28: lload_1
    //   29: lxor
    //   30: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   35: lload #5
    //   37: sipush #968
    //   40: ldc2_w 7275231359992640669
    //   43: lload_1
    //   44: lxor
    //   45: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   50: getstatic wtf/opal/kn.VISUAL : Lwtf/opal/kn;
    //   53: invokespecial <init> : (Ljava/lang/String;JLjava/lang/String;Lwtf/opal/kn;)V
    //   56: aload_0
    //   57: new java/util/concurrent/ConcurrentHashMap
    //   60: dup
    //   61: invokespecial <init> : ()V
    //   64: putfield l : Ljava/util/concurrent/ConcurrentMap;
    //   67: aload_0
    //   68: new wtf/opal/kl
    //   71: dup
    //   72: sipush #6606
    //   75: ldc2_w 6709425783173951119
    //   78: lload_1
    //   79: lxor
    //   80: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   85: ldc ''
    //   87: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   90: putfield P : Lwtf/opal/kl;
    //   93: aload_0
    //   94: iconst_0
    //   95: anewarray java/lang/Object
    //   98: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   101: iconst_0
    //   102: anewarray java/lang/Object
    //   105: invokevirtual z : ([Ljava/lang/Object;)Lwtf/opal/pa;
    //   108: putfield D : Lwtf/opal/pa;
    //   111: aload_0
    //   112: iconst_0
    //   113: anewarray java/lang/Object
    //   116: invokestatic q : ([Ljava/lang/Object;)Lwtf/opal/d1;
    //   119: iconst_0
    //   120: anewarray java/lang/Object
    //   123: invokevirtual h : ([Ljava/lang/Object;)Lwtf/opal/bu;
    //   126: putfield b : Lwtf/opal/bu;
    //   129: aload_0
    //   130: aload_0
    //   131: <illegal opcode> H : (Lwtf/opal/j7;)Lwtf/opal/gm;
    //   136: putfield a : Lwtf/opal/gm;
    //   139: aload_0
    //   140: aload_0
    //   141: <illegal opcode> H : (Lwtf/opal/j7;)Lwtf/opal/gm;
    //   146: putfield n : Lwtf/opal/gm;
    //   149: aload_0
    //   150: aload_0
    //   151: <illegal opcode> H : (Lwtf/opal/j7;)Lwtf/opal/gm;
    //   156: putfield W : Lwtf/opal/gm;
    //   159: aload_0
    //   160: aload_0
    //   161: <illegal opcode> H : (Lwtf/opal/j7;)Lwtf/opal/gm;
    //   166: putfield Z : Lwtf/opal/gm;
    //   169: aload_0
    //   170: iconst_1
    //   171: anewarray wtf/opal/k3
    //   174: dup
    //   175: iconst_0
    //   176: aload_0
    //   177: getfield P : Lwtf/opal/kl;
    //   180: aastore
    //   181: lload_3
    //   182: dup2_x1
    //   183: pop2
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
    //   202: invokevirtual o : ([Ljava/lang/Object;)V
    //   205: return
  }
  
  private CompletableFuture H(Object[] paramArrayOfObject) {
    String str = (String)paramArrayOfObject[0];
    return CompletableFuture.supplyAsync(str::lambda$getRequestJSONAsync$8);
  }
  
  private static JSONObject lambda$getRequestJSONAsync$8(String paramString) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: ldc2_w 135918373145753
    //   6: lxor
    //   7: lstore_1
    //   8: invokestatic S : ()Ljava/lang/String;
    //   11: astore_3
    //   12: aload_0
    //   13: sipush #30968
    //   16: ldc2_w 3508548498018344488
    //   19: lload_1
    //   20: lxor
    //   21: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   26: swap
    //   27: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   32: invokestatic create : (Ljava/lang/String;)Ljava/net/URI;
    //   35: invokevirtual toURL : ()Ljava/net/URL;
    //   38: astore #4
    //   40: aload #4
    //   42: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   45: checkcast java/net/HttpURLConnection
    //   48: astore #5
    //   50: aload #5
    //   52: sipush #20770
    //   55: ldc2_w 4792938740233385976
    //   58: lload_1
    //   59: lxor
    //   60: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   65: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   68: new java/io/BufferedReader
    //   71: dup
    //   72: new java/io/InputStreamReader
    //   75: dup
    //   76: aload #5
    //   78: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   81: invokespecial <init> : (Ljava/io/InputStream;)V
    //   84: invokespecial <init> : (Ljava/io/Reader;)V
    //   87: astore #6
    //   89: new java/lang/StringBuilder
    //   92: dup
    //   93: invokespecial <init> : ()V
    //   96: astore #8
    //   98: aload #6
    //   100: invokevirtual readLine : ()Ljava/lang/String;
    //   103: dup
    //   104: astore #7
    //   106: ifnull -> 132
    //   109: aload #8
    //   111: aload #7
    //   113: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   116: pop
    //   117: aload_3
    //   118: ifnonnull -> 137
    //   121: aload_3
    //   122: ifnull -> 98
    //   125: goto -> 132
    //   128: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   131: athrow
    //   132: aload #6
    //   134: invokevirtual close : ()V
    //   137: new org/json/JSONObject
    //   140: dup
    //   141: aload #8
    //   143: invokevirtual toString : ()Ljava/lang/String;
    //   146: invokespecial <init> : (Ljava/lang/String;)V
    //   149: areturn
    //   150: astore #4
    //   152: aload #4
    //   154: invokevirtual printStackTrace : ()V
    //   157: aconst_null
    //   158: areturn
    // Exception table:
    //   from	to	target	type
    //   12	149	150	java/io/IOException
    //   109	125	128	java/io/IOException
  }
  
  private void lambda$new$7(lu paramlu) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: ldc2_w 81778294632856
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic S : ()Ljava/lang/String;
    //   11: aload_1
    //   12: iconst_0
    //   13: anewarray java/lang/Object
    //   16: invokevirtual g : ([Ljava/lang/Object;)Lnet/minecraft/class_2596;
    //   19: astore #6
    //   21: astore #4
    //   23: aload #6
    //   25: aload #4
    //   27: ifnonnull -> 52
    //   30: instanceof net/minecraft/class_7439
    //   33: ifeq -> 247
    //   36: goto -> 43
    //   39: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   42: athrow
    //   43: aload #6
    //   45: goto -> 52
    //   48: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   51: athrow
    //   52: checkcast net/minecraft/class_7439
    //   55: astore #5
    //   57: aload #5
    //   59: invokevirtual comp_763 : ()Lnet/minecraft/class_2561;
    //   62: invokeinterface getString : ()Ljava/lang/String;
    //   67: sipush #27970
    //   70: ldc2_w 2137191154128696991
    //   73: lload_2
    //   74: lxor
    //   75: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   80: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   83: aload #4
    //   85: ifnonnull -> 131
    //   88: ifeq -> 247
    //   91: goto -> 98
    //   94: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   97: athrow
    //   98: aload #5
    //   100: invokevirtual comp_763 : ()Lnet/minecraft/class_2561;
    //   103: invokeinterface getString : ()Ljava/lang/String;
    //   108: sipush #17538
    //   111: ldc2_w 3634019252016127809
    //   114: lload_2
    //   115: lxor
    //   116: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   121: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   124: goto -> 131
    //   127: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   130: athrow
    //   131: ifeq -> 247
    //   134: aload_1
    //   135: iconst_0
    //   136: anewarray java/lang/Object
    //   139: invokevirtual Z : ([Ljava/lang/Object;)V
    //   142: aload_0
    //   143: aload #5
    //   145: invokevirtual comp_763 : ()Lnet/minecraft/class_2561;
    //   148: invokeinterface getString : ()Ljava/lang/String;
    //   153: sipush #23830
    //   156: ldc2_w 4563044083480266456
    //   159: lload_2
    //   160: lxor
    //   161: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   166: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   169: aload #4
    //   171: ifnonnull -> 224
    //   174: goto -> 181
    //   177: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   180: athrow
    //   181: ifeq -> 243
    //   184: goto -> 191
    //   187: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   190: athrow
    //   191: aload #5
    //   193: invokevirtual comp_763 : ()Lnet/minecraft/class_2561;
    //   196: invokeinterface getString : ()Ljava/lang/String;
    //   201: sipush #14309
    //   204: ldc2_w 3875673317030030397
    //   207: lload_2
    //   208: lxor
    //   209: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   214: invokevirtual contains : (Ljava/lang/CharSequence;)Z
    //   217: goto -> 224
    //   220: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   223: athrow
    //   224: aload #4
    //   226: ifnonnull -> 240
    //   229: ifeq -> 243
    //   232: goto -> 239
    //   235: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   238: athrow
    //   239: iconst_1
    //   240: goto -> 244
    //   243: iconst_0
    //   244: putfield s : Z
    //   247: return
    // Exception table:
    //   from	to	target	type
    //   23	36	39	org/json/JSONException
    //   30	45	48	org/json/JSONException
    //   57	91	94	org/json/JSONException
    //   88	124	127	org/json/JSONException
    //   131	174	177	org/json/JSONException
    //   134	184	187	org/json/JSONException
    //   181	217	220	org/json/JSONException
    //   224	232	235	org/json/JSONException
  }
  
  private void lambda$new$6(d4 paramd4) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: ldc2_w 3374853878739
    //   6: lxor
    //   7: lstore_2
    //   8: aload_0
    //   9: getfield l : Ljava/util/concurrent/ConcurrentMap;
    //   12: invokeinterface clear : ()V
    //   17: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   20: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   23: sipush #15870
    //   26: ldc2_w 2786376487283565695
    //   29: lload_2
    //   30: lxor
    //   31: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   36: invokevirtual method_45729 : (Ljava/lang/String;)V
    //   39: aload_0
    //   40: iconst_0
    //   41: putfield s : Z
    //   44: return
  }
  
  private void lambda$new$5(b6 paramb6) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: ldc2_w 87531723207466
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic S : ()Ljava/lang/String;
    //   11: astore #4
    //   13: aload_1
    //   14: iconst_0
    //   15: anewarray java/lang/Object
    //   18: invokevirtual W : ([Ljava/lang/Object;)Z
    //   21: aload #4
    //   23: ifnonnull -> 47
    //   26: ifeq -> 62
    //   29: goto -> 36
    //   32: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   35: athrow
    //   36: aload_0
    //   37: getfield s : Z
    //   40: goto -> 47
    //   43: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   46: athrow
    //   47: aload #4
    //   49: ifnonnull -> 99
    //   52: ifne -> 63
    //   55: goto -> 62
    //   58: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   61: athrow
    //   62: return
    //   63: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   66: getfield field_1687 : Lnet/minecraft/class_638;
    //   69: invokevirtual method_18112 : ()Ljava/lang/Iterable;
    //   72: aload_0
    //   73: <illegal opcode> accept : (Lwtf/opal/j7;)Ljava/util/function/Consumer;
    //   78: invokeinterface forEach : (Ljava/util/function/Consumer;)V
    //   83: aload_0
    //   84: aload #4
    //   86: ifnonnull -> 103
    //   89: getfield E : I
    //   92: goto -> 99
    //   95: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   98: athrow
    //   99: ifle -> 117
    //   102: aload_0
    //   103: getfield l : Ljava/util/concurrent/ConcurrentMap;
    //   106: aload_0
    //   107: <illegal opcode> accept : (Lwtf/opal/j7;)Ljava/util/function/BiConsumer;
    //   112: invokeinterface forEach : (Ljava/util/function/BiConsumer;)V
    //   117: return
    // Exception table:
    //   from	to	target	type
    //   13	29	32	org/json/JSONException
    //   26	40	43	org/json/JSONException
    //   47	55	58	org/json/JSONException
    //   63	92	95	org/json/JSONException
  }
  
  private void lambda$new$4(String paramString, br parambr) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: ldc2_w 29859570363087
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 126596184555556
    //   13: lxor
    //   14: lstore #5
    //   16: pop2
    //   17: invokestatic S : ()Ljava/lang/String;
    //   20: astore #7
    //   22: aload #7
    //   24: ifnonnull -> 104
    //   27: invokestatic currentTimeMillis : ()J
    //   30: aload_2
    //   31: iconst_0
    //   32: anewarray java/lang/Object
    //   35: invokevirtual F : ([Ljava/lang/Object;)J
    //   38: lsub
    //   39: getstatic wtf/opal/j7.q : J
    //   42: lcmp
    //   43: ifle -> 114
    //   46: goto -> 53
    //   49: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   52: athrow
    //   53: aload_1
    //   54: sipush #15218
    //   57: ldc2_w 7055583930732280803
    //   60: lload_3
    //   61: lxor
    //   62: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   67: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   72: lload #5
    //   74: dup2_x1
    //   75: pop2
    //   76: iconst_2
    //   77: anewarray java/lang/Object
    //   80: dup_x1
    //   81: swap
    //   82: iconst_1
    //   83: swap
    //   84: aastore
    //   85: dup_x2
    //   86: dup_x2
    //   87: pop
    //   88: invokestatic valueOf : (J)Ljava/lang/Long;
    //   91: iconst_0
    //   92: swap
    //   93: aastore
    //   94: invokestatic g : ([Ljava/lang/Object;)V
    //   97: goto -> 104
    //   100: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   103: athrow
    //   104: aload_0
    //   105: dup
    //   106: getfield E : I
    //   109: iconst_1
    //   110: isub
    //   111: putfield E : I
    //   114: return
    // Exception table:
    //   from	to	target	type
    //   22	46	49	org/json/JSONException
    //   27	97	100	org/json/JSONException
  }
  
  private void lambda$new$3(class_1297 paramclass_1297) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: ldc2_w 292610966461
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic S : ()Ljava/lang/String;
    //   11: astore #4
    //   13: aload_1
    //   14: aload #4
    //   16: ifnonnull -> 40
    //   19: instanceof net/minecraft/class_1657
    //   22: ifeq -> 311
    //   25: goto -> 32
    //   28: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   31: athrow
    //   32: aload_1
    //   33: goto -> 40
    //   36: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   39: athrow
    //   40: checkcast net/minecraft/class_1657
    //   43: astore #5
    //   45: aload #5
    //   47: invokevirtual method_5667 : ()Ljava/util/UUID;
    //   50: invokevirtual version : ()I
    //   53: aload #4
    //   55: ifnonnull -> 95
    //   58: iconst_2
    //   59: if_icmpeq -> 277
    //   62: goto -> 69
    //   65: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   68: athrow
    //   69: aload_0
    //   70: getfield l : Ljava/util/concurrent/ConcurrentMap;
    //   73: aload #5
    //   75: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   78: invokeinterface getString : ()Ljava/lang/String;
    //   83: invokeinterface containsKey : (Ljava/lang/Object;)Z
    //   88: goto -> 95
    //   91: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   94: athrow
    //   95: aload #4
    //   97: ifnonnull -> 156
    //   100: ifne -> 277
    //   103: goto -> 110
    //   106: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   109: athrow
    //   110: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   113: aload #4
    //   115: ifnonnull -> 162
    //   118: goto -> 125
    //   121: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   124: athrow
    //   125: getfield field_1724 : Lnet/minecraft/class_746;
    //   128: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   131: invokeinterface getString : ()Ljava/lang/String;
    //   136: aload #5
    //   138: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   141: invokeinterface getString : ()Ljava/lang/String;
    //   146: invokevirtual equals : (Ljava/lang/Object;)Z
    //   149: goto -> 156
    //   152: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   155: athrow
    //   156: ifne -> 277
    //   159: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   162: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   165: aload #5
    //   167: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   170: invokeinterface getString : ()Ljava/lang/String;
    //   175: invokevirtual method_2874 : (Ljava/lang/String;)Lnet/minecraft/class_640;
    //   178: aload #4
    //   180: ifnonnull -> 219
    //   183: ifnull -> 238
    //   186: goto -> 193
    //   189: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   192: athrow
    //   193: getstatic wtf/opal/b9.c : Lnet/minecraft/class_310;
    //   196: invokevirtual method_1562 : ()Lnet/minecraft/class_634;
    //   199: aload #5
    //   201: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   204: invokeinterface getString : ()Ljava/lang/String;
    //   209: invokevirtual method_2874 : (Ljava/lang/String;)Lnet/minecraft/class_640;
    //   212: goto -> 219
    //   215: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   218: athrow
    //   219: invokevirtual method_2959 : ()I
    //   222: iconst_1
    //   223: aload #4
    //   225: ifnonnull -> 274
    //   228: if_icmpne -> 277
    //   231: goto -> 238
    //   234: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   237: athrow
    //   238: aload_0
    //   239: aload #4
    //   241: ifnonnull -> 279
    //   244: goto -> 251
    //   247: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   250: athrow
    //   251: getfield E : I
    //   254: sipush #686
    //   257: ldc2_w 4418352964915040678
    //   260: lload_2
    //   261: lxor
    //   262: <illegal opcode> h : (IJ)I
    //   267: goto -> 274
    //   270: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   273: athrow
    //   274: if_icmplt -> 278
    //   277: return
    //   278: aload_0
    //   279: aload #5
    //   281: invokevirtual method_5667 : ()Ljava/util/UUID;
    //   284: invokevirtual toString : ()Ljava/lang/String;
    //   287: iconst_1
    //   288: anewarray java/lang/Object
    //   291: dup_x1
    //   292: swap
    //   293: iconst_0
    //   294: swap
    //   295: aastore
    //   296: invokevirtual H : ([Ljava/lang/Object;)Ljava/util/concurrent/CompletableFuture;
    //   299: aload_0
    //   300: aload #5
    //   302: <illegal opcode> accept : (Lwtf/opal/j7;Lnet/minecraft/class_1657;)Ljava/util/function/Consumer;
    //   307: invokevirtual thenAccept : (Ljava/util/function/Consumer;)Ljava/util/concurrent/CompletableFuture;
    //   310: pop
    //   311: return
    // Exception table:
    //   from	to	target	type
    //   13	25	28	org/json/JSONException
    //   19	33	36	org/json/JSONException
    //   45	62	65	org/json/JSONException
    //   58	88	91	org/json/JSONException
    //   95	103	106	org/json/JSONException
    //   100	118	121	org/json/JSONException
    //   110	149	152	org/json/JSONException
    //   162	186	189	org/json/JSONException
    //   183	212	215	org/json/JSONException
    //   219	231	234	org/json/JSONException
    //   228	244	247	org/json/JSONException
    //   238	267	270	org/json/JSONException
  }
  
  private void lambda$new$2(class_1657 paramclass_1657, JSONObject paramJSONObject) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: ldc2_w 131126147622527
    //   6: lxor
    //   7: lstore_3
    //   8: lload_3
    //   9: dup2
    //   10: ldc2_w 65105073317366
    //   13: lxor
    //   14: lstore #5
    //   16: pop2
    //   17: invokestatic S : ()Ljava/lang/String;
    //   20: astore #7
    //   22: aload_2
    //   23: aload #7
    //   25: ifnonnull -> 39
    //   28: ifnull -> 294
    //   31: goto -> 38
    //   34: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   37: athrow
    //   38: aload_2
    //   39: sipush #30533
    //   42: ldc2_w 3436819574502308720
    //   45: lload_3
    //   46: lxor
    //   47: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   52: aload #7
    //   54: ifnonnull -> 91
    //   57: invokevirtual isNull : (Ljava/lang/String;)Z
    //   60: ifne -> 294
    //   63: goto -> 70
    //   66: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   69: athrow
    //   70: aload_2
    //   71: sipush #29538
    //   74: ldc2_w 5965329657660587842
    //   77: lload_3
    //   78: lxor
    //   79: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   84: goto -> 91
    //   87: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   90: athrow
    //   91: invokevirtual getJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   94: astore #8
    //   96: aload #8
    //   98: sipush #5400
    //   101: ldc2_w 2070639718808803625
    //   104: lload_3
    //   105: lxor
    //   106: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   111: invokevirtual getJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   114: astore #9
    //   116: aload #9
    //   118: sipush #145
    //   121: ldc2_w 4912071651727330493
    //   124: lload_3
    //   125: lxor
    //   126: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   131: invokevirtual getJSONObject : (Ljava/lang/String;)Lorg/json/JSONObject;
    //   134: astore #10
    //   136: new wtf/opal/br
    //   139: dup
    //   140: aload #10
    //   142: sipush #8679
    //   145: ldc2_w 2462924953675591117
    //   148: lload_3
    //   149: lxor
    //   150: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   155: iconst_0
    //   156: invokevirtual optInt : (Ljava/lang/String;I)I
    //   159: aload #10
    //   161: sipush #17574
    //   164: ldc2_w 3357239549990799502
    //   167: lload_3
    //   168: lxor
    //   169: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   174: iconst_0
    //   175: invokevirtual optInt : (Ljava/lang/String;I)I
    //   178: lload #5
    //   180: dup2_x1
    //   181: pop2
    //   182: aload #10
    //   184: sipush #27899
    //   187: ldc2_w 2063620564174512332
    //   190: lload_3
    //   191: lxor
    //   192: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   197: iconst_0
    //   198: invokevirtual optInt : (Ljava/lang/String;I)I
    //   201: aload #10
    //   203: sipush #23826
    //   206: ldc2_w 1126467756379115817
    //   209: lload_3
    //   210: lxor
    //   211: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   216: iconst_0
    //   217: invokevirtual optInt : (Ljava/lang/String;I)I
    //   220: aload #10
    //   222: sipush #3235
    //   225: ldc2_w 6535304424434745480
    //   228: lload_3
    //   229: lxor
    //   230: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   235: iconst_0
    //   236: invokevirtual optInt : (Ljava/lang/String;I)I
    //   239: aload #10
    //   241: sipush #12305
    //   244: ldc2_w 7306098668930858017
    //   247: lload_3
    //   248: lxor
    //   249: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   254: iconst_0
    //   255: invokevirtual optInt : (Ljava/lang/String;I)I
    //   258: invokespecial <init> : (IJIIIII)V
    //   261: astore #11
    //   263: aload_0
    //   264: getfield l : Ljava/util/concurrent/ConcurrentMap;
    //   267: aload_1
    //   268: invokevirtual method_5477 : ()Lnet/minecraft/class_2561;
    //   271: invokeinterface getString : ()Ljava/lang/String;
    //   276: aload #11
    //   278: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   283: pop
    //   284: aload_0
    //   285: dup
    //   286: getfield E : I
    //   289: iconst_1
    //   290: iadd
    //   291: putfield E : I
    //   294: goto -> 304
    //   297: astore #8
    //   299: aload #8
    //   301: invokevirtual printStackTrace : ()V
    //   304: return
    // Exception table:
    //   from	to	target	type
    //   22	31	34	org/json/JSONException
    //   22	294	297	org/json/JSONException
    //   39	63	66	org/json/JSONException
    //   57	84	87	org/json/JSONException
  }
  
  private void lambda$new$1(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: ldc2_w 74192719000480
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 60282800968000
    //   13: lxor
    //   14: lstore #4
    //   16: pop2
    //   17: aload_0
    //   18: getfield D : Lwtf/opal/pa;
    //   21: aload_0
    //   22: aload_1
    //   23: <illegal opcode> run : (Lwtf/opal/j7;Lwtf/opal/uj;)Ljava/lang/Runnable;
    //   28: lload #4
    //   30: iconst_2
    //   31: anewarray java/lang/Object
    //   34: dup_x2
    //   35: dup_x2
    //   36: pop
    //   37: invokestatic valueOf : (J)Ljava/lang/Long;
    //   40: iconst_1
    //   41: swap
    //   42: aastore
    //   43: dup_x1
    //   44: swap
    //   45: iconst_0
    //   46: swap
    //   47: aastore
    //   48: invokevirtual F : ([Ljava/lang/Object;)V
    //   51: return
  }
  
  private void lambda$new$0(uj paramuj) {
    // Byte code:
    //   0: getstatic wtf/opal/j7.d : J
    //   3: ldc2_w 93457079952500
    //   6: lxor
    //   7: lstore_2
    //   8: lload_2
    //   9: dup2
    //   10: ldc2_w 125230938795677
    //   13: lxor
    //   14: lstore #4
    //   16: dup2
    //   17: ldc2_w 65750120227257
    //   20: lxor
    //   21: lstore #6
    //   23: dup2
    //   24: ldc2_w 101166717246352
    //   27: lxor
    //   28: lstore #8
    //   30: pop2
    //   31: aload_0
    //   32: getfield D : Lwtf/opal/pa;
    //   35: ldc_w 5.0
    //   38: ldc_w 30.0
    //   41: ldc_w 250.0
    //   44: ldc_w 20.0
    //   47: sipush #26729
    //   50: ldc2_w 4600112237771693231
    //   53: lload_2
    //   54: lxor
    //   55: <illegal opcode> h : (IJ)I
    //   60: lload #4
    //   62: bipush #6
    //   64: anewarray java/lang/Object
    //   67: dup_x2
    //   68: dup_x2
    //   69: pop
    //   70: invokestatic valueOf : (J)Ljava/lang/Long;
    //   73: iconst_5
    //   74: swap
    //   75: aastore
    //   76: dup_x1
    //   77: swap
    //   78: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   81: iconst_4
    //   82: swap
    //   83: aastore
    //   84: dup_x1
    //   85: swap
    //   86: invokestatic valueOf : (F)Ljava/lang/Float;
    //   89: iconst_3
    //   90: swap
    //   91: aastore
    //   92: dup_x1
    //   93: swap
    //   94: invokestatic valueOf : (F)Ljava/lang/Float;
    //   97: iconst_2
    //   98: swap
    //   99: aastore
    //   100: dup_x1
    //   101: swap
    //   102: invokestatic valueOf : (F)Ljava/lang/Float;
    //   105: iconst_1
    //   106: swap
    //   107: aastore
    //   108: dup_x1
    //   109: swap
    //   110: invokestatic valueOf : (F)Ljava/lang/Float;
    //   113: iconst_0
    //   114: swap
    //   115: aastore
    //   116: invokevirtual k : ([Ljava/lang/Object;)V
    //   119: aload_0
    //   120: getfield b : Lwtf/opal/bu;
    //   123: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   126: aload_1
    //   127: iconst_0
    //   128: anewarray java/lang/Object
    //   131: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   134: sipush #8276
    //   137: ldc2_w 3359567266933878336
    //   140: lload_2
    //   141: lxor
    //   142: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   147: ldc_w 8.0
    //   150: ldc_w 30.0
    //   153: aload_0
    //   154: getfield b : Lwtf/opal/bu;
    //   157: lload #6
    //   159: sipush #25487
    //   162: ldc2_w 2404413489287130535
    //   165: lload_2
    //   166: lxor
    //   167: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   172: ldc_w 8.0
    //   175: iconst_3
    //   176: anewarray java/lang/Object
    //   179: dup_x1
    //   180: swap
    //   181: invokestatic valueOf : (F)Ljava/lang/Float;
    //   184: iconst_2
    //   185: swap
    //   186: aastore
    //   187: dup_x1
    //   188: swap
    //   189: iconst_1
    //   190: swap
    //   191: aastore
    //   192: dup_x2
    //   193: dup_x2
    //   194: pop
    //   195: invokestatic valueOf : (J)Ljava/lang/Long;
    //   198: iconst_0
    //   199: swap
    //   200: aastore
    //   201: invokevirtual A : ([Ljava/lang/Object;)F
    //   204: fconst_2
    //   205: fdiv
    //   206: fadd
    //   207: lload #8
    //   209: dup2_x1
    //   210: pop2
    //   211: ldc_w 8.0
    //   214: iconst_m1
    //   215: iconst_1
    //   216: bipush #9
    //   218: anewarray java/lang/Object
    //   221: dup_x1
    //   222: swap
    //   223: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   226: bipush #8
    //   228: swap
    //   229: aastore
    //   230: dup_x1
    //   231: swap
    //   232: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   235: bipush #7
    //   237: swap
    //   238: aastore
    //   239: dup_x1
    //   240: swap
    //   241: invokestatic valueOf : (F)Ljava/lang/Float;
    //   244: bipush #6
    //   246: swap
    //   247: aastore
    //   248: dup_x1
    //   249: swap
    //   250: invokestatic valueOf : (F)Ljava/lang/Float;
    //   253: iconst_5
    //   254: swap
    //   255: aastore
    //   256: dup_x2
    //   257: dup_x2
    //   258: pop
    //   259: invokestatic valueOf : (J)Ljava/lang/Long;
    //   262: iconst_4
    //   263: swap
    //   264: aastore
    //   265: dup_x1
    //   266: swap
    //   267: invokestatic valueOf : (F)Ljava/lang/Float;
    //   270: iconst_3
    //   271: swap
    //   272: aastore
    //   273: dup_x1
    //   274: swap
    //   275: iconst_2
    //   276: swap
    //   277: aastore
    //   278: dup_x1
    //   279: swap
    //   280: iconst_1
    //   281: swap
    //   282: aastore
    //   283: dup_x1
    //   284: swap
    //   285: iconst_0
    //   286: swap
    //   287: aastore
    //   288: invokevirtual H : ([Ljava/lang/Object;)V
    //   291: invokestatic S : ()Ljava/lang/String;
    //   294: aload_0
    //   295: getfield b : Lwtf/opal/bu;
    //   298: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   301: aload_1
    //   302: iconst_0
    //   303: anewarray java/lang/Object
    //   306: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   309: sipush #12641
    //   312: ldc2_w 3081325772100452172
    //   315: lload_2
    //   316: lxor
    //   317: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   322: ldc_w 80.0
    //   325: ldc_w 30.0
    //   328: aload_0
    //   329: getfield b : Lwtf/opal/bu;
    //   332: lload #6
    //   334: sipush #18955
    //   337: ldc2_w 3588677216138413093
    //   340: lload_2
    //   341: lxor
    //   342: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   347: ldc_w 8.0
    //   350: iconst_3
    //   351: anewarray java/lang/Object
    //   354: dup_x1
    //   355: swap
    //   356: invokestatic valueOf : (F)Ljava/lang/Float;
    //   359: iconst_2
    //   360: swap
    //   361: aastore
    //   362: dup_x1
    //   363: swap
    //   364: iconst_1
    //   365: swap
    //   366: aastore
    //   367: dup_x2
    //   368: dup_x2
    //   369: pop
    //   370: invokestatic valueOf : (J)Ljava/lang/Long;
    //   373: iconst_0
    //   374: swap
    //   375: aastore
    //   376: invokevirtual A : ([Ljava/lang/Object;)F
    //   379: fconst_2
    //   380: fdiv
    //   381: fadd
    //   382: lload #8
    //   384: dup2_x1
    //   385: pop2
    //   386: ldc_w 8.0
    //   389: iconst_m1
    //   390: iconst_1
    //   391: bipush #9
    //   393: anewarray java/lang/Object
    //   396: dup_x1
    //   397: swap
    //   398: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   401: bipush #8
    //   403: swap
    //   404: aastore
    //   405: dup_x1
    //   406: swap
    //   407: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   410: bipush #7
    //   412: swap
    //   413: aastore
    //   414: dup_x1
    //   415: swap
    //   416: invokestatic valueOf : (F)Ljava/lang/Float;
    //   419: bipush #6
    //   421: swap
    //   422: aastore
    //   423: dup_x1
    //   424: swap
    //   425: invokestatic valueOf : (F)Ljava/lang/Float;
    //   428: iconst_5
    //   429: swap
    //   430: aastore
    //   431: dup_x2
    //   432: dup_x2
    //   433: pop
    //   434: invokestatic valueOf : (J)Ljava/lang/Long;
    //   437: iconst_4
    //   438: swap
    //   439: aastore
    //   440: dup_x1
    //   441: swap
    //   442: invokestatic valueOf : (F)Ljava/lang/Float;
    //   445: iconst_3
    //   446: swap
    //   447: aastore
    //   448: dup_x1
    //   449: swap
    //   450: iconst_2
    //   451: swap
    //   452: aastore
    //   453: dup_x1
    //   454: swap
    //   455: iconst_1
    //   456: swap
    //   457: aastore
    //   458: dup_x1
    //   459: swap
    //   460: iconst_0
    //   461: swap
    //   462: aastore
    //   463: invokevirtual H : ([Ljava/lang/Object;)V
    //   466: aload_0
    //   467: getfield b : Lwtf/opal/bu;
    //   470: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   473: aload_1
    //   474: iconst_0
    //   475: anewarray java/lang/Object
    //   478: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   481: sipush #15756
    //   484: ldc2_w 5213752752940626856
    //   487: lload_2
    //   488: lxor
    //   489: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   494: ldc_w 120.0
    //   497: ldc_w 30.0
    //   500: aload_0
    //   501: getfield b : Lwtf/opal/bu;
    //   504: lload #6
    //   506: sipush #21433
    //   509: ldc2_w 2360689245348912559
    //   512: lload_2
    //   513: lxor
    //   514: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   519: ldc_w 8.0
    //   522: iconst_3
    //   523: anewarray java/lang/Object
    //   526: dup_x1
    //   527: swap
    //   528: invokestatic valueOf : (F)Ljava/lang/Float;
    //   531: iconst_2
    //   532: swap
    //   533: aastore
    //   534: dup_x1
    //   535: swap
    //   536: iconst_1
    //   537: swap
    //   538: aastore
    //   539: dup_x2
    //   540: dup_x2
    //   541: pop
    //   542: invokestatic valueOf : (J)Ljava/lang/Long;
    //   545: iconst_0
    //   546: swap
    //   547: aastore
    //   548: invokevirtual A : ([Ljava/lang/Object;)F
    //   551: fconst_2
    //   552: fdiv
    //   553: fadd
    //   554: lload #8
    //   556: dup2_x1
    //   557: pop2
    //   558: ldc_w 8.0
    //   561: iconst_m1
    //   562: iconst_1
    //   563: bipush #9
    //   565: anewarray java/lang/Object
    //   568: dup_x1
    //   569: swap
    //   570: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   573: bipush #8
    //   575: swap
    //   576: aastore
    //   577: dup_x1
    //   578: swap
    //   579: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   582: bipush #7
    //   584: swap
    //   585: aastore
    //   586: dup_x1
    //   587: swap
    //   588: invokestatic valueOf : (F)Ljava/lang/Float;
    //   591: bipush #6
    //   593: swap
    //   594: aastore
    //   595: dup_x1
    //   596: swap
    //   597: invokestatic valueOf : (F)Ljava/lang/Float;
    //   600: iconst_5
    //   601: swap
    //   602: aastore
    //   603: dup_x2
    //   604: dup_x2
    //   605: pop
    //   606: invokestatic valueOf : (J)Ljava/lang/Long;
    //   609: iconst_4
    //   610: swap
    //   611: aastore
    //   612: dup_x1
    //   613: swap
    //   614: invokestatic valueOf : (F)Ljava/lang/Float;
    //   617: iconst_3
    //   618: swap
    //   619: aastore
    //   620: dup_x1
    //   621: swap
    //   622: iconst_2
    //   623: swap
    //   624: aastore
    //   625: dup_x1
    //   626: swap
    //   627: iconst_1
    //   628: swap
    //   629: aastore
    //   630: dup_x1
    //   631: swap
    //   632: iconst_0
    //   633: swap
    //   634: aastore
    //   635: invokevirtual H : ([Ljava/lang/Object;)V
    //   638: aload_0
    //   639: getfield b : Lwtf/opal/bu;
    //   642: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   645: aload_1
    //   646: iconst_0
    //   647: anewarray java/lang/Object
    //   650: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   653: sipush #14605
    //   656: ldc2_w 1012490470278182692
    //   659: lload_2
    //   660: lxor
    //   661: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   666: ldc_w 160.0
    //   669: ldc_w 30.0
    //   672: aload_0
    //   673: getfield b : Lwtf/opal/bu;
    //   676: lload #6
    //   678: sipush #26842
    //   681: ldc2_w 6553299446622021325
    //   684: lload_2
    //   685: lxor
    //   686: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   691: ldc_w 8.0
    //   694: iconst_3
    //   695: anewarray java/lang/Object
    //   698: dup_x1
    //   699: swap
    //   700: invokestatic valueOf : (F)Ljava/lang/Float;
    //   703: iconst_2
    //   704: swap
    //   705: aastore
    //   706: dup_x1
    //   707: swap
    //   708: iconst_1
    //   709: swap
    //   710: aastore
    //   711: dup_x2
    //   712: dup_x2
    //   713: pop
    //   714: invokestatic valueOf : (J)Ljava/lang/Long;
    //   717: iconst_0
    //   718: swap
    //   719: aastore
    //   720: invokevirtual A : ([Ljava/lang/Object;)F
    //   723: fconst_2
    //   724: fdiv
    //   725: fadd
    //   726: lload #8
    //   728: dup2_x1
    //   729: pop2
    //   730: ldc_w 8.0
    //   733: iconst_m1
    //   734: iconst_1
    //   735: bipush #9
    //   737: anewarray java/lang/Object
    //   740: dup_x1
    //   741: swap
    //   742: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   745: bipush #8
    //   747: swap
    //   748: aastore
    //   749: dup_x1
    //   750: swap
    //   751: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   754: bipush #7
    //   756: swap
    //   757: aastore
    //   758: dup_x1
    //   759: swap
    //   760: invokestatic valueOf : (F)Ljava/lang/Float;
    //   763: bipush #6
    //   765: swap
    //   766: aastore
    //   767: dup_x1
    //   768: swap
    //   769: invokestatic valueOf : (F)Ljava/lang/Float;
    //   772: iconst_5
    //   773: swap
    //   774: aastore
    //   775: dup_x2
    //   776: dup_x2
    //   777: pop
    //   778: invokestatic valueOf : (J)Ljava/lang/Long;
    //   781: iconst_4
    //   782: swap
    //   783: aastore
    //   784: dup_x1
    //   785: swap
    //   786: invokestatic valueOf : (F)Ljava/lang/Float;
    //   789: iconst_3
    //   790: swap
    //   791: aastore
    //   792: dup_x1
    //   793: swap
    //   794: iconst_2
    //   795: swap
    //   796: aastore
    //   797: dup_x1
    //   798: swap
    //   799: iconst_1
    //   800: swap
    //   801: aastore
    //   802: dup_x1
    //   803: swap
    //   804: iconst_0
    //   805: swap
    //   806: aastore
    //   807: invokevirtual H : ([Ljava/lang/Object;)V
    //   810: aload_0
    //   811: getfield b : Lwtf/opal/bu;
    //   814: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   817: aload_1
    //   818: iconst_0
    //   819: anewarray java/lang/Object
    //   822: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   825: sipush #10193
    //   828: ldc2_w 8296104682044205539
    //   831: lload_2
    //   832: lxor
    //   833: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   838: ldc_w 200.0
    //   841: ldc_w 30.0
    //   844: aload_0
    //   845: getfield b : Lwtf/opal/bu;
    //   848: lload #6
    //   850: sipush #29913
    //   853: ldc2_w 8100018565358254828
    //   856: lload_2
    //   857: lxor
    //   858: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   863: ldc_w 8.0
    //   866: iconst_3
    //   867: anewarray java/lang/Object
    //   870: dup_x1
    //   871: swap
    //   872: invokestatic valueOf : (F)Ljava/lang/Float;
    //   875: iconst_2
    //   876: swap
    //   877: aastore
    //   878: dup_x1
    //   879: swap
    //   880: iconst_1
    //   881: swap
    //   882: aastore
    //   883: dup_x2
    //   884: dup_x2
    //   885: pop
    //   886: invokestatic valueOf : (J)Ljava/lang/Long;
    //   889: iconst_0
    //   890: swap
    //   891: aastore
    //   892: invokevirtual A : ([Ljava/lang/Object;)F
    //   895: fconst_2
    //   896: fdiv
    //   897: fadd
    //   898: lload #8
    //   900: dup2_x1
    //   901: pop2
    //   902: ldc_w 8.0
    //   905: iconst_m1
    //   906: iconst_1
    //   907: bipush #9
    //   909: anewarray java/lang/Object
    //   912: dup_x1
    //   913: swap
    //   914: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   917: bipush #8
    //   919: swap
    //   920: aastore
    //   921: dup_x1
    //   922: swap
    //   923: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   926: bipush #7
    //   928: swap
    //   929: aastore
    //   930: dup_x1
    //   931: swap
    //   932: invokestatic valueOf : (F)Ljava/lang/Float;
    //   935: bipush #6
    //   937: swap
    //   938: aastore
    //   939: dup_x1
    //   940: swap
    //   941: invokestatic valueOf : (F)Ljava/lang/Float;
    //   944: iconst_5
    //   945: swap
    //   946: aastore
    //   947: dup_x2
    //   948: dup_x2
    //   949: pop
    //   950: invokestatic valueOf : (J)Ljava/lang/Long;
    //   953: iconst_4
    //   954: swap
    //   955: aastore
    //   956: dup_x1
    //   957: swap
    //   958: invokestatic valueOf : (F)Ljava/lang/Float;
    //   961: iconst_3
    //   962: swap
    //   963: aastore
    //   964: dup_x1
    //   965: swap
    //   966: iconst_2
    //   967: swap
    //   968: aastore
    //   969: dup_x1
    //   970: swap
    //   971: iconst_1
    //   972: swap
    //   973: aastore
    //   974: dup_x1
    //   975: swap
    //   976: iconst_0
    //   977: swap
    //   978: aastore
    //   979: invokevirtual H : ([Ljava/lang/Object;)V
    //   982: astore #10
    //   984: iconst_0
    //   985: istore #11
    //   987: aload_0
    //   988: getfield l : Ljava/util/concurrent/ConcurrentMap;
    //   991: invokeinterface entrySet : ()Ljava/util/Set;
    //   996: invokeinterface iterator : ()Ljava/util/Iterator;
    //   1001: astore #12
    //   1003: aload #12
    //   1005: invokeinterface hasNext : ()Z
    //   1010: ifeq -> 2267
    //   1013: aload #12
    //   1015: invokeinterface next : ()Ljava/lang/Object;
    //   1020: checkcast java/util/Map$Entry
    //   1023: astore #13
    //   1025: aload #13
    //   1027: invokeinterface getValue : ()Ljava/lang/Object;
    //   1032: checkcast wtf/opal/br
    //   1035: iconst_0
    //   1036: anewarray java/lang/Object
    //   1039: invokevirtual A : ([Ljava/lang/Object;)I
    //   1042: istore #14
    //   1044: aload #13
    //   1046: invokeinterface getValue : ()Ljava/lang/Object;
    //   1051: checkcast wtf/opal/br
    //   1054: iconst_0
    //   1055: anewarray java/lang/Object
    //   1058: invokevirtual B : ([Ljava/lang/Object;)I
    //   1061: istore #15
    //   1063: aload #13
    //   1065: invokeinterface getValue : ()Ljava/lang/Object;
    //   1070: checkcast wtf/opal/br
    //   1073: iconst_0
    //   1074: anewarray java/lang/Object
    //   1077: invokevirtual b : ([Ljava/lang/Object;)I
    //   1080: istore #16
    //   1082: aload #13
    //   1084: invokeinterface getValue : ()Ljava/lang/Object;
    //   1089: checkcast wtf/opal/br
    //   1092: iconst_0
    //   1093: anewarray java/lang/Object
    //   1096: invokevirtual J : ([Ljava/lang/Object;)I
    //   1099: istore #17
    //   1101: aload_0
    //   1102: getfield D : Lwtf/opal/pa;
    //   1105: ldc_w 5.0
    //   1108: sipush #21343
    //   1111: ldc2_w 6796581347879987100
    //   1114: lload_2
    //   1115: lxor
    //   1116: <illegal opcode> h : (IJ)I
    //   1121: iload #11
    //   1123: sipush #11656
    //   1126: ldc2_w 1160313078293387592
    //   1129: lload_2
    //   1130: lxor
    //   1131: <illegal opcode> h : (IJ)I
    //   1136: imul
    //   1137: iadd
    //   1138: i2f
    //   1139: ldc_w 250.0
    //   1142: ldc_w 20.0
    //   1145: sipush #12643
    //   1148: ldc2_w 1465266096325510561
    //   1151: lload_2
    //   1152: lxor
    //   1153: <illegal opcode> h : (IJ)I
    //   1158: lload #4
    //   1160: bipush #6
    //   1162: anewarray java/lang/Object
    //   1165: dup_x2
    //   1166: dup_x2
    //   1167: pop
    //   1168: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1171: iconst_5
    //   1172: swap
    //   1173: aastore
    //   1174: dup_x1
    //   1175: swap
    //   1176: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1179: iconst_4
    //   1180: swap
    //   1181: aastore
    //   1182: dup_x1
    //   1183: swap
    //   1184: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1187: iconst_3
    //   1188: swap
    //   1189: aastore
    //   1190: dup_x1
    //   1191: swap
    //   1192: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1195: iconst_2
    //   1196: swap
    //   1197: aastore
    //   1198: dup_x1
    //   1199: swap
    //   1200: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1203: iconst_1
    //   1204: swap
    //   1205: aastore
    //   1206: dup_x1
    //   1207: swap
    //   1208: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1211: iconst_0
    //   1212: swap
    //   1213: aastore
    //   1214: invokevirtual k : ([Ljava/lang/Object;)V
    //   1217: aload_0
    //   1218: getfield b : Lwtf/opal/bu;
    //   1221: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   1224: aload_1
    //   1225: iconst_0
    //   1226: anewarray java/lang/Object
    //   1229: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   1232: aload #13
    //   1234: invokeinterface getKey : ()Ljava/lang/Object;
    //   1239: checkcast java/lang/String
    //   1242: ldc_w 8.0
    //   1245: sipush #7007
    //   1248: ldc2_w 2590729376392736666
    //   1251: lload_2
    //   1252: lxor
    //   1253: <illegal opcode> h : (IJ)I
    //   1258: iload #11
    //   1260: sipush #28969
    //   1263: ldc2_w 4605632245157496301
    //   1266: lload_2
    //   1267: lxor
    //   1268: <illegal opcode> h : (IJ)I
    //   1273: imul
    //   1274: iadd
    //   1275: i2f
    //   1276: aload_0
    //   1277: getfield b : Lwtf/opal/bu;
    //   1280: aload #13
    //   1282: invokeinterface getKey : ()Ljava/lang/Object;
    //   1287: lload #6
    //   1289: dup2_x1
    //   1290: pop2
    //   1291: checkcast java/lang/String
    //   1294: ldc_w 8.0
    //   1297: iconst_3
    //   1298: anewarray java/lang/Object
    //   1301: dup_x1
    //   1302: swap
    //   1303: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1306: iconst_2
    //   1307: swap
    //   1308: aastore
    //   1309: dup_x1
    //   1310: swap
    //   1311: iconst_1
    //   1312: swap
    //   1313: aastore
    //   1314: dup_x2
    //   1315: dup_x2
    //   1316: pop
    //   1317: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1320: iconst_0
    //   1321: swap
    //   1322: aastore
    //   1323: invokevirtual A : ([Ljava/lang/Object;)F
    //   1326: fconst_2
    //   1327: fdiv
    //   1328: fadd
    //   1329: lload #8
    //   1331: dup2_x1
    //   1332: pop2
    //   1333: ldc_w 8.0
    //   1336: iconst_m1
    //   1337: iconst_1
    //   1338: bipush #9
    //   1340: anewarray java/lang/Object
    //   1343: dup_x1
    //   1344: swap
    //   1345: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1348: bipush #8
    //   1350: swap
    //   1351: aastore
    //   1352: dup_x1
    //   1353: swap
    //   1354: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1357: bipush #7
    //   1359: swap
    //   1360: aastore
    //   1361: dup_x1
    //   1362: swap
    //   1363: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1366: bipush #6
    //   1368: swap
    //   1369: aastore
    //   1370: dup_x1
    //   1371: swap
    //   1372: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1375: iconst_5
    //   1376: swap
    //   1377: aastore
    //   1378: dup_x2
    //   1379: dup_x2
    //   1380: pop
    //   1381: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1384: iconst_4
    //   1385: swap
    //   1386: aastore
    //   1387: dup_x1
    //   1388: swap
    //   1389: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1392: iconst_3
    //   1393: swap
    //   1394: aastore
    //   1395: dup_x1
    //   1396: swap
    //   1397: iconst_2
    //   1398: swap
    //   1399: aastore
    //   1400: dup_x1
    //   1401: swap
    //   1402: iconst_1
    //   1403: swap
    //   1404: aastore
    //   1405: dup_x1
    //   1406: swap
    //   1407: iconst_0
    //   1408: swap
    //   1409: aastore
    //   1410: invokevirtual H : ([Ljava/lang/Object;)V
    //   1413: iload #15
    //   1415: ifeq -> 1458
    //   1418: sipush #24912
    //   1421: ldc2_w 4169312387452039029
    //   1424: lload_2
    //   1425: lxor
    //   1426: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   1431: iconst_1
    //   1432: anewarray java/lang/Object
    //   1435: dup
    //   1436: iconst_0
    //   1437: iload #14
    //   1439: i2d
    //   1440: iload #15
    //   1442: i2d
    //   1443: ddiv
    //   1444: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1447: aastore
    //   1448: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1451: goto -> 1471
    //   1454: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1457: athrow
    //   1458: sipush #4738
    //   1461: ldc2_w 2433481937430256817
    //   1464: lload_2
    //   1465: lxor
    //   1466: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   1471: astore #18
    //   1473: aload_0
    //   1474: getfield b : Lwtf/opal/bu;
    //   1477: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   1480: aload_1
    //   1481: iconst_0
    //   1482: anewarray java/lang/Object
    //   1485: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   1488: aload #18
    //   1490: ldc_w 80.0
    //   1493: sipush #7007
    //   1496: ldc2_w 2590729376392736666
    //   1499: lload_2
    //   1500: lxor
    //   1501: <illegal opcode> h : (IJ)I
    //   1506: iload #11
    //   1508: sipush #28969
    //   1511: ldc2_w 4605632245157496301
    //   1514: lload_2
    //   1515: lxor
    //   1516: <illegal opcode> h : (IJ)I
    //   1521: imul
    //   1522: iadd
    //   1523: i2f
    //   1524: aload_0
    //   1525: getfield b : Lwtf/opal/bu;
    //   1528: lload #6
    //   1530: aload #18
    //   1532: ldc_w 8.0
    //   1535: iconst_3
    //   1536: anewarray java/lang/Object
    //   1539: dup_x1
    //   1540: swap
    //   1541: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1544: iconst_2
    //   1545: swap
    //   1546: aastore
    //   1547: dup_x1
    //   1548: swap
    //   1549: iconst_1
    //   1550: swap
    //   1551: aastore
    //   1552: dup_x2
    //   1553: dup_x2
    //   1554: pop
    //   1555: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1558: iconst_0
    //   1559: swap
    //   1560: aastore
    //   1561: invokevirtual A : ([Ljava/lang/Object;)F
    //   1564: fconst_2
    //   1565: fdiv
    //   1566: fadd
    //   1567: lload #8
    //   1569: dup2_x1
    //   1570: pop2
    //   1571: ldc_w 8.0
    //   1574: iconst_m1
    //   1575: iconst_1
    //   1576: bipush #9
    //   1578: anewarray java/lang/Object
    //   1581: dup_x1
    //   1582: swap
    //   1583: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1586: bipush #8
    //   1588: swap
    //   1589: aastore
    //   1590: dup_x1
    //   1591: swap
    //   1592: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1595: bipush #7
    //   1597: swap
    //   1598: aastore
    //   1599: dup_x1
    //   1600: swap
    //   1601: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1604: bipush #6
    //   1606: swap
    //   1607: aastore
    //   1608: dup_x1
    //   1609: swap
    //   1610: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1613: iconst_5
    //   1614: swap
    //   1615: aastore
    //   1616: dup_x2
    //   1617: dup_x2
    //   1618: pop
    //   1619: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1622: iconst_4
    //   1623: swap
    //   1624: aastore
    //   1625: dup_x1
    //   1626: swap
    //   1627: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1630: iconst_3
    //   1631: swap
    //   1632: aastore
    //   1633: dup_x1
    //   1634: swap
    //   1635: iconst_2
    //   1636: swap
    //   1637: aastore
    //   1638: dup_x1
    //   1639: swap
    //   1640: iconst_1
    //   1641: swap
    //   1642: aastore
    //   1643: dup_x1
    //   1644: swap
    //   1645: iconst_0
    //   1646: swap
    //   1647: aastore
    //   1648: invokevirtual H : ([Ljava/lang/Object;)V
    //   1651: iload #17
    //   1653: ifeq -> 1696
    //   1656: sipush #24104
    //   1659: ldc2_w 6326971026553819153
    //   1662: lload_2
    //   1663: lxor
    //   1664: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   1669: iconst_1
    //   1670: anewarray java/lang/Object
    //   1673: dup
    //   1674: iconst_0
    //   1675: iload #16
    //   1677: i2d
    //   1678: iload #17
    //   1680: i2d
    //   1681: ddiv
    //   1682: invokestatic valueOf : (D)Ljava/lang/Double;
    //   1685: aastore
    //   1686: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   1689: goto -> 1709
    //   1692: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   1695: athrow
    //   1696: sipush #16042
    //   1699: ldc2_w 2529754459115925660
    //   1702: lload_2
    //   1703: lxor
    //   1704: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   1709: astore #19
    //   1711: aload_0
    //   1712: getfield b : Lwtf/opal/bu;
    //   1715: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   1718: aload_1
    //   1719: iconst_0
    //   1720: anewarray java/lang/Object
    //   1723: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   1726: aload #19
    //   1728: ldc_w 120.0
    //   1731: sipush #7007
    //   1734: ldc2_w 2590729376392736666
    //   1737: lload_2
    //   1738: lxor
    //   1739: <illegal opcode> h : (IJ)I
    //   1744: iload #11
    //   1746: sipush #28969
    //   1749: ldc2_w 4605632245157496301
    //   1752: lload_2
    //   1753: lxor
    //   1754: <illegal opcode> h : (IJ)I
    //   1759: imul
    //   1760: iadd
    //   1761: i2f
    //   1762: aload_0
    //   1763: getfield b : Lwtf/opal/bu;
    //   1766: lload #6
    //   1768: aload #19
    //   1770: ldc_w 8.0
    //   1773: iconst_3
    //   1774: anewarray java/lang/Object
    //   1777: dup_x1
    //   1778: swap
    //   1779: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1782: iconst_2
    //   1783: swap
    //   1784: aastore
    //   1785: dup_x1
    //   1786: swap
    //   1787: iconst_1
    //   1788: swap
    //   1789: aastore
    //   1790: dup_x2
    //   1791: dup_x2
    //   1792: pop
    //   1793: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1796: iconst_0
    //   1797: swap
    //   1798: aastore
    //   1799: invokevirtual A : ([Ljava/lang/Object;)F
    //   1802: fconst_2
    //   1803: fdiv
    //   1804: fadd
    //   1805: lload #8
    //   1807: dup2_x1
    //   1808: pop2
    //   1809: ldc_w 8.0
    //   1812: iconst_m1
    //   1813: iconst_1
    //   1814: bipush #9
    //   1816: anewarray java/lang/Object
    //   1819: dup_x1
    //   1820: swap
    //   1821: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   1824: bipush #8
    //   1826: swap
    //   1827: aastore
    //   1828: dup_x1
    //   1829: swap
    //   1830: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   1833: bipush #7
    //   1835: swap
    //   1836: aastore
    //   1837: dup_x1
    //   1838: swap
    //   1839: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1842: bipush #6
    //   1844: swap
    //   1845: aastore
    //   1846: dup_x1
    //   1847: swap
    //   1848: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1851: iconst_5
    //   1852: swap
    //   1853: aastore
    //   1854: dup_x2
    //   1855: dup_x2
    //   1856: pop
    //   1857: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1860: iconst_4
    //   1861: swap
    //   1862: aastore
    //   1863: dup_x1
    //   1864: swap
    //   1865: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1868: iconst_3
    //   1869: swap
    //   1870: aastore
    //   1871: dup_x1
    //   1872: swap
    //   1873: iconst_2
    //   1874: swap
    //   1875: aastore
    //   1876: dup_x1
    //   1877: swap
    //   1878: iconst_1
    //   1879: swap
    //   1880: aastore
    //   1881: dup_x1
    //   1882: swap
    //   1883: iconst_0
    //   1884: swap
    //   1885: aastore
    //   1886: invokevirtual H : ([Ljava/lang/Object;)V
    //   1889: iload #14
    //   1891: invokestatic valueOf : (I)Ljava/lang/String;
    //   1894: astore #20
    //   1896: aload_0
    //   1897: getfield b : Lwtf/opal/bu;
    //   1900: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   1903: aload_1
    //   1904: iconst_0
    //   1905: anewarray java/lang/Object
    //   1908: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   1911: aload #20
    //   1913: ldc_w 160.0
    //   1916: sipush #7007
    //   1919: ldc2_w 2590729376392736666
    //   1922: lload_2
    //   1923: lxor
    //   1924: <illegal opcode> h : (IJ)I
    //   1929: iload #11
    //   1931: sipush #28969
    //   1934: ldc2_w 4605632245157496301
    //   1937: lload_2
    //   1938: lxor
    //   1939: <illegal opcode> h : (IJ)I
    //   1944: imul
    //   1945: iadd
    //   1946: i2f
    //   1947: aload_0
    //   1948: getfield b : Lwtf/opal/bu;
    //   1951: lload #6
    //   1953: aload #20
    //   1955: ldc_w 8.0
    //   1958: iconst_3
    //   1959: anewarray java/lang/Object
    //   1962: dup_x1
    //   1963: swap
    //   1964: invokestatic valueOf : (F)Ljava/lang/Float;
    //   1967: iconst_2
    //   1968: swap
    //   1969: aastore
    //   1970: dup_x1
    //   1971: swap
    //   1972: iconst_1
    //   1973: swap
    //   1974: aastore
    //   1975: dup_x2
    //   1976: dup_x2
    //   1977: pop
    //   1978: invokestatic valueOf : (J)Ljava/lang/Long;
    //   1981: iconst_0
    //   1982: swap
    //   1983: aastore
    //   1984: invokevirtual A : ([Ljava/lang/Object;)F
    //   1987: fconst_2
    //   1988: fdiv
    //   1989: fadd
    //   1990: lload #8
    //   1992: dup2_x1
    //   1993: pop2
    //   1994: ldc_w 8.0
    //   1997: iconst_m1
    //   1998: iconst_1
    //   1999: bipush #9
    //   2001: anewarray java/lang/Object
    //   2004: dup_x1
    //   2005: swap
    //   2006: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   2009: bipush #8
    //   2011: swap
    //   2012: aastore
    //   2013: dup_x1
    //   2014: swap
    //   2015: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2018: bipush #7
    //   2020: swap
    //   2021: aastore
    //   2022: dup_x1
    //   2023: swap
    //   2024: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2027: bipush #6
    //   2029: swap
    //   2030: aastore
    //   2031: dup_x1
    //   2032: swap
    //   2033: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2036: iconst_5
    //   2037: swap
    //   2038: aastore
    //   2039: dup_x2
    //   2040: dup_x2
    //   2041: pop
    //   2042: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2045: iconst_4
    //   2046: swap
    //   2047: aastore
    //   2048: dup_x1
    //   2049: swap
    //   2050: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2053: iconst_3
    //   2054: swap
    //   2055: aastore
    //   2056: dup_x1
    //   2057: swap
    //   2058: iconst_2
    //   2059: swap
    //   2060: aastore
    //   2061: dup_x1
    //   2062: swap
    //   2063: iconst_1
    //   2064: swap
    //   2065: aastore
    //   2066: dup_x1
    //   2067: swap
    //   2068: iconst_0
    //   2069: swap
    //   2070: aastore
    //   2071: invokevirtual H : ([Ljava/lang/Object;)V
    //   2074: iload #16
    //   2076: invokestatic valueOf : (I)Ljava/lang/String;
    //   2079: astore #21
    //   2081: aload_0
    //   2082: getfield b : Lwtf/opal/bu;
    //   2085: getstatic wtf/opal/lx.REGULAR : Lwtf/opal/lx;
    //   2088: aload_1
    //   2089: iconst_0
    //   2090: anewarray java/lang/Object
    //   2093: invokevirtual B : ([Ljava/lang/Object;)Lnet/minecraft/class_332;
    //   2096: aload #21
    //   2098: ldc_w 200.0
    //   2101: sipush #7007
    //   2104: ldc2_w 2590729376392736666
    //   2107: lload_2
    //   2108: lxor
    //   2109: <illegal opcode> h : (IJ)I
    //   2114: iload #11
    //   2116: sipush #28969
    //   2119: ldc2_w 4605632245157496301
    //   2122: lload_2
    //   2123: lxor
    //   2124: <illegal opcode> h : (IJ)I
    //   2129: imul
    //   2130: iadd
    //   2131: i2f
    //   2132: aload_0
    //   2133: getfield b : Lwtf/opal/bu;
    //   2136: lload #6
    //   2138: aload #21
    //   2140: ldc_w 8.0
    //   2143: iconst_3
    //   2144: anewarray java/lang/Object
    //   2147: dup_x1
    //   2148: swap
    //   2149: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2152: iconst_2
    //   2153: swap
    //   2154: aastore
    //   2155: dup_x1
    //   2156: swap
    //   2157: iconst_1
    //   2158: swap
    //   2159: aastore
    //   2160: dup_x2
    //   2161: dup_x2
    //   2162: pop
    //   2163: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2166: iconst_0
    //   2167: swap
    //   2168: aastore
    //   2169: invokevirtual A : ([Ljava/lang/Object;)F
    //   2172: fconst_2
    //   2173: fdiv
    //   2174: fadd
    //   2175: lload #8
    //   2177: dup2_x1
    //   2178: pop2
    //   2179: ldc_w 8.0
    //   2182: iconst_m1
    //   2183: iconst_1
    //   2184: bipush #9
    //   2186: anewarray java/lang/Object
    //   2189: dup_x1
    //   2190: swap
    //   2191: invokestatic valueOf : (Z)Ljava/lang/Boolean;
    //   2194: bipush #8
    //   2196: swap
    //   2197: aastore
    //   2198: dup_x1
    //   2199: swap
    //   2200: invokestatic valueOf : (I)Ljava/lang/Integer;
    //   2203: bipush #7
    //   2205: swap
    //   2206: aastore
    //   2207: dup_x1
    //   2208: swap
    //   2209: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2212: bipush #6
    //   2214: swap
    //   2215: aastore
    //   2216: dup_x1
    //   2217: swap
    //   2218: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2221: iconst_5
    //   2222: swap
    //   2223: aastore
    //   2224: dup_x2
    //   2225: dup_x2
    //   2226: pop
    //   2227: invokestatic valueOf : (J)Ljava/lang/Long;
    //   2230: iconst_4
    //   2231: swap
    //   2232: aastore
    //   2233: dup_x1
    //   2234: swap
    //   2235: invokestatic valueOf : (F)Ljava/lang/Float;
    //   2238: iconst_3
    //   2239: swap
    //   2240: aastore
    //   2241: dup_x1
    //   2242: swap
    //   2243: iconst_2
    //   2244: swap
    //   2245: aastore
    //   2246: dup_x1
    //   2247: swap
    //   2248: iconst_1
    //   2249: swap
    //   2250: aastore
    //   2251: dup_x1
    //   2252: swap
    //   2253: iconst_0
    //   2254: swap
    //   2255: aastore
    //   2256: invokevirtual H : ([Ljava/lang/Object;)V
    //   2259: iinc #11, 1
    //   2262: aload #10
    //   2264: ifnull -> 1003
    //   2267: return
    // Exception table:
    //   from	to	target	type
    //   1101	1454	1454	org/json/JSONException
    //   1473	1692	1692	org/json/JSONException
  }
  
  static {
    long l = d ^ 0x1246AD6610A5L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[35];
    boolean bool = false;
    String str;
    int i = (str = "ü?ëØ5\035y!U+¡~C;'\020¯ñ\026Z\024ÊSï\021+ó\017â\020d \030cåÒÎ¼p\tb\020º\\öXb\fÚ6Þ\001ÒvñÆ?\020¾ÇáeèF'&\031s\035r4\r\020iîÓËk#Ý'®Ì.\027\bÙ{ ÔøÃouXþ]î­CRú¿ÖÝ\004ê®-Ü\001g5É¹&Ì ã\026\021\023\025{»\024E\031ØÒÃ»Z\006«áa4~ìEq\fh\020µ6{íQ¤z\bó^\034ÐË\020/ÕKW}\035\025\0257aÀ°ÿó Qa¾\017TÿµÑü©\026$\0026ô\"ïï&{\000Ú|Õ;3\007Å1ßülv/\033\025Ú»\004÷µï:\025WÔk;¼6\001Z¤2+S\fLTà_8\0028$~¼´¥k\024 ©xrâÙw\004ðá_Z1:»r²Ò^Ðbä»Ôg±KYv\032¿.\027\"ïÓñ\0230,¬l\nË»;Ô\030\006©¶­¡\003ry¸[{ï´ô6ï#¬5÷\007×\006\034\020ë\031îî\035ê©ª?ö±{35¥(ùÇÌîÒN8]#q\027ÊuSÔ÷?bWõ!\"óÂ?à''C°Ö¯H¡*\020ßßDÙX\021Ü%Èò\007ýeåbÃ\020\004Ë\030G\017¨õ5\003\022_z*¨¥E\020±6!ß[¸Db!\004í5/\000´\020\001ÐlóU«f¸5ãk} \020»\013\017ÿ¦ eÂÂ\031Â¦.\020·)o\nÞþ\007ÿ\020~\023a*\006\020ûQ\025\r¡D\\XÖW×ü¦e:±(bøå+Îíï]\027dK¡\016\032¼p\0164³-\003«h°74Ù\030cÀ¡6&'Q\034(ú\005ÀáÞbIæ\005\0349¥¬ÁÌx9TD\030Ì\026ðq!ÒÞ´,y\\¼U!º(Y¡º!\022æ\027ü\t\025\0024*&Çxx. \003XLNeÑ=TxÌÔ\031\005ý\001É\020zí¥;±ð-rßüV³-û\020O¤9P\n\017\000;yEa©ª\016 \013ã\016®j©NW?yA¨r äM\035Ü\021\036\002fç\030 e\020ÑÿÇ%Ô±à et_\027y8.(ªp·­ËeÈê¿+ZÊTÕ¹ b\002\013ãÔbúCt.¹c`ø\000?E/\020ñùùî\020AèÉäG¾£\020æ¢¤GS6`ý2µ\001ýní\\\020\033j<¹v\017Z\037jÉIÂ¢\020ë\021\023Ï½ÂB ø&m ¨").length();
    byte b2 = 16;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x6291;
    if (g[i] == null) {
      Object[] arrayOfObject;
      try {
        Long long_ = Long.valueOf(Thread.currentThread().threadId());
        arrayOfObject = (Object[])k.get(long_);
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/PKCS5Padding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          k.put(long_, arrayOfObject);
        } 
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/j7", exception);
      } 
      byte[] arrayOfByte1 = new byte[8];
      arrayOfByte1[0] = (byte)(int)(paramLong >>> 56L);
      for (byte b = 1; b < 8; b++)
        arrayOfByte1[b] = (byte)(int)(paramLong << b * 8 >>> 56L); 
      DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
      SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
      ((Cipher)arrayOfObject[0]).init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
      byte[] arrayOfByte2 = f[i].getBytes("ISO-8859-1");
      g[i] = a(((Cipher)arrayOfObject[0]).doFinal(arrayOfByte2));
    } 
    return g[i];
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
    //   66: ldc_w 'wtf/opal/j7'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1867;
    if (o[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = m[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])p.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          p.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("wtf/opal/j7", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      o[i] = Integer.valueOf(j);
    } 
    return o[i].intValue();
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
    //   66: ldc_w 'wtf/opal/j7'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\wtf\opal\j7.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */