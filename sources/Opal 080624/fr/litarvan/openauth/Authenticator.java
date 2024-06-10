package fr.litarvan.openauth;

import fr.litarvan.openauth.model.AuthAgent;
import fr.litarvan.openauth.model.request.AuthRequest;
import fr.litarvan.openauth.model.request.InvalidateRequest;
import fr.litarvan.openauth.model.request.RefreshRequest;
import fr.litarvan.openauth.model.request.SignoutRequest;
import fr.litarvan.openauth.model.request.ValidateRequest;
import fr.litarvan.openauth.model.response.AuthResponse;
import fr.litarvan.openauth.model.response.RefreshResponse;
import java.io.IOException;
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
import wtf.opal.d;
import wtf.opal.on;

public class Authenticator {
  public static final String MOJANG_AUTH_URL;
  
  private String authURL;
  
  private AuthPoints authPoints;
  
  private static final long a = on.a(-609424026943293428L, -650229820595325052L, MethodHandles.lookup().lookupClass()).a(60859402984116L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public Authenticator(String paramString, AuthPoints paramAuthPoints) {
    this.authURL = paramString;
    this.authPoints = paramAuthPoints;
  }
  
  public AuthResponse authenticate(AuthAgent paramAuthAgent, String paramString1, String paramString2, String paramString3) throws AuthenticationException {
    long l = a ^ 0x35CA96F2D74L;
    AuthRequest authRequest = new AuthRequest(paramAuthAgent, paramString1, paramString2, paramString3);
    boolean bool = AuthenticationException.S();
    try {
      if (bool)
        d.p(new d[2]); 
    } catch (AuthenticationException authenticationException) {
      throw a(null);
    } 
    return (AuthResponse)sendRequest(authRequest, AuthResponse.class, this.authPoints.getAuthenticatePoint());
  }
  
  public RefreshResponse refresh(String paramString1, String paramString2) throws AuthenticationException {
    RefreshRequest refreshRequest = new RefreshRequest(paramString1, paramString2);
    return (RefreshResponse)sendRequest(refreshRequest, RefreshResponse.class, this.authPoints.getRefreshPoint());
  }
  
  public void validate(String paramString) throws AuthenticationException {
    ValidateRequest validateRequest = new ValidateRequest(paramString);
    sendRequest(validateRequest, null, this.authPoints.getValidatePoint());
  }
  
  public void signout(String paramString1, String paramString2) throws AuthenticationException {
    SignoutRequest signoutRequest = new SignoutRequest(paramString1, paramString2);
    sendRequest(signoutRequest, null, this.authPoints.getSignoutPoint());
  }
  
  public void invalidate(String paramString1, String paramString2) throws AuthenticationException {
    long l = a ^ 0x2434299EDF48L;
    InvalidateRequest invalidateRequest = new InvalidateRequest(paramString1, paramString2);
    boolean bool = AuthenticationException.P();
    try {
      sendRequest(invalidateRequest, null, this.authPoints.getInvalidatePoint());
      if (d.D() != null) {
        try {
        
        } catch (AuthenticationException authenticationException) {
          throw a(null);
        } 
        AuthenticationException.M(!bool);
      } 
    } catch (AuthenticationException authenticationException) {
      throw a(null);
    } 
  }
  
  private Object sendRequest(Object paramObject, Class<?> paramClass, String paramString) throws AuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/Authenticator.a : J
    //   3: ldc2_w 7233632698468
    //   6: lxor
    //   7: lstore #4
    //   9: invokestatic P : ()Z
    //   12: new com/google/gson/Gson
    //   15: dup
    //   16: invokespecial <init> : ()V
    //   19: astore #7
    //   21: istore #6
    //   23: aload_0
    //   24: aload_0
    //   25: getfield authURL : Ljava/lang/String;
    //   28: aload_3
    //   29: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   34: aload #7
    //   36: aload_1
    //   37: invokevirtual toJson : (Ljava/lang/Object;)Ljava/lang/String;
    //   40: invokevirtual sendPostRequest : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   43: astore #8
    //   45: goto -> 112
    //   48: astore #9
    //   50: new fr/litarvan/openauth/AuthenticationException
    //   53: dup
    //   54: new fr/litarvan/openauth/model/AuthError
    //   57: dup
    //   58: aload #9
    //   60: invokevirtual getClass : ()Ljava/lang/Class;
    //   63: invokevirtual getName : ()Ljava/lang/String;
    //   66: sipush #24758
    //   69: ldc2_w 2629656640339724981
    //   72: lload #4
    //   74: lxor
    //   75: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   80: swap
    //   81: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   86: aload #9
    //   88: invokevirtual getMessage : ()Ljava/lang/String;
    //   91: sipush #8656
    //   94: ldc2_w 6099130206924311517
    //   97: lload #4
    //   99: lxor
    //   100: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   105: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   108: invokespecial <init> : (Lfr/litarvan/openauth/model/AuthError;)V
    //   111: athrow
    //   112: aload_2
    //   113: iload #6
    //   115: ifeq -> 143
    //   118: ifnull -> 144
    //   121: goto -> 128
    //   124: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   127: athrow
    //   128: aload #7
    //   130: aload #8
    //   132: aload_2
    //   133: invokevirtual fromJson : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   136: goto -> 143
    //   139: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   142: athrow
    //   143: areturn
    //   144: aconst_null
    //   145: areturn
    // Exception table:
    //   from	to	target	type
    //   23	45	48	java/io/IOException
    //   112	121	124	java/io/IOException
    //   118	136	139	java/io/IOException
  }
  
  private String sendPostRequest(String paramString1, String paramString2) throws AuthenticationException, IOException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/Authenticator.a : J
    //   3: ldc2_w 18061986513955
    //   6: lxor
    //   7: lstore_3
    //   8: aload_2
    //   9: getstatic java/nio/charset/StandardCharsets.UTF_8 : Ljava/nio/charset/Charset;
    //   12: invokevirtual getBytes : (Ljava/nio/charset/Charset;)[B
    //   15: astore #6
    //   17: invokestatic P : ()Z
    //   20: new java/net/URL
    //   23: dup
    //   24: aload_1
    //   25: invokespecial <init> : (Ljava/lang/String;)V
    //   28: astore #7
    //   30: istore #5
    //   32: aload #7
    //   34: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   37: checkcast java/net/HttpURLConnection
    //   40: astore #8
    //   42: aload #8
    //   44: sipush #21225
    //   47: ldc2_w 7909595955732790439
    //   50: lload_3
    //   51: lxor
    //   52: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   57: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   60: aload #8
    //   62: iconst_1
    //   63: invokevirtual setDoOutput : (Z)V
    //   66: aload #8
    //   68: sipush #15271
    //   71: ldc2_w 8000629320254412270
    //   74: lload_3
    //   75: lxor
    //   76: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   81: sipush #32249
    //   84: ldc2_w 2611572083122672566
    //   87: lload_3
    //   88: lxor
    //   89: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   94: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   97: aload #8
    //   99: sipush #32241
    //   102: ldc2_w 4923888860420019130
    //   105: lload_3
    //   106: lxor
    //   107: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   112: sipush #16576
    //   115: ldc2_w 4078854938666282631
    //   118: lload_3
    //   119: lxor
    //   120: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   125: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   128: aload #8
    //   130: sipush #20099
    //   133: ldc2_w 2083157702913466565
    //   136: lload_3
    //   137: lxor
    //   138: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   143: aload #6
    //   145: arraylength
    //   146: invokestatic valueOf : (I)Ljava/lang/String;
    //   149: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   152: new java/io/DataOutputStream
    //   155: dup
    //   156: aload #8
    //   158: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
    //   161: invokespecial <init> : (Ljava/io/OutputStream;)V
    //   164: astore #9
    //   166: aload #9
    //   168: aload #6
    //   170: iconst_0
    //   171: aload #6
    //   173: arraylength
    //   174: invokevirtual write : ([BII)V
    //   177: aload #9
    //   179: invokevirtual flush : ()V
    //   182: aload #9
    //   184: invokevirtual close : ()V
    //   187: aload #8
    //   189: invokevirtual connect : ()V
    //   192: aload #8
    //   194: invokevirtual getResponseCode : ()I
    //   197: istore #10
    //   199: iload #10
    //   201: sipush #26278
    //   204: ldc2_w 4816900393694690234
    //   207: lload_3
    //   208: lxor
    //   209: <illegal opcode> o : (IJ)I
    //   214: iload #5
    //   216: ifeq -> 255
    //   219: if_icmpne -> 240
    //   222: goto -> 229
    //   225: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   228: athrow
    //   229: aload #8
    //   231: invokevirtual disconnect : ()V
    //   234: aconst_null
    //   235: areturn
    //   236: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   239: athrow
    //   240: iload #10
    //   242: sipush #16357
    //   245: ldc2_w 2779625028360282875
    //   248: lload_3
    //   249: lxor
    //   250: <illegal opcode> o : (IJ)I
    //   255: if_icmpne -> 270
    //   258: aload #8
    //   260: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   263: astore #11
    //   265: iload #5
    //   267: ifne -> 277
    //   270: aload #8
    //   272: invokevirtual getErrorStream : ()Ljava/io/InputStream;
    //   275: astore #11
    //   277: new java/io/BufferedReader
    //   280: dup
    //   281: new java/io/InputStreamReader
    //   284: dup
    //   285: aload #11
    //   287: invokespecial <init> : (Ljava/io/InputStream;)V
    //   290: invokespecial <init> : (Ljava/io/Reader;)V
    //   293: astore #13
    //   295: aload #13
    //   297: invokevirtual readLine : ()Ljava/lang/String;
    //   300: astore #12
    //   302: aload #13
    //   304: invokevirtual close : ()V
    //   307: goto -> 317
    //   310: astore #14
    //   312: aload #14
    //   314: invokevirtual printStackTrace : ()V
    //   317: aload #8
    //   319: invokevirtual disconnect : ()V
    //   322: aload #12
    //   324: ifnull -> 375
    //   327: aload #12
    //   329: ldc_w '﻿'
    //   332: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   335: iload #5
    //   337: ifeq -> 377
    //   340: iload #5
    //   342: ifeq -> 377
    //   345: goto -> 352
    //   348: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   351: athrow
    //   352: ifeq -> 375
    //   355: goto -> 362
    //   358: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   361: athrow
    //   362: aload #12
    //   364: iconst_1
    //   365: invokevirtual substring : (I)Ljava/lang/String;
    //   368: astore #12
    //   370: iload #5
    //   372: ifne -> 322
    //   375: iload #10
    //   377: sipush #6246
    //   380: ldc2_w 8202084299702338937
    //   383: lload_3
    //   384: lxor
    //   385: <illegal opcode> o : (IJ)I
    //   390: if_icmpeq -> 497
    //   393: new com/google/gson/Gson
    //   396: dup
    //   397: invokespecial <init> : ()V
    //   400: astore #14
    //   402: aload #12
    //   404: iload #5
    //   406: ifeq -> 421
    //   409: ifnull -> 477
    //   412: goto -> 419
    //   415: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   418: athrow
    //   419: aload #12
    //   421: ldc_w '{'
    //   424: invokevirtual startsWith : (Ljava/lang/String;)Z
    //   427: ifne -> 477
    //   430: new fr/litarvan/openauth/AuthenticationException
    //   433: dup
    //   434: new fr/litarvan/openauth/model/AuthError
    //   437: dup
    //   438: sipush #1611
    //   441: ldc2_w 3958999259810949123
    //   444: lload_3
    //   445: lxor
    //   446: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   451: aload #12
    //   453: sipush #169
    //   456: ldc2_w 880672422220426981
    //   459: lload_3
    //   460: lxor
    //   461: <illegal opcode> v : (IJ)Ljava/lang/String;
    //   466: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   469: invokespecial <init> : (Lfr/litarvan/openauth/model/AuthError;)V
    //   472: athrow
    //   473: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   476: athrow
    //   477: new fr/litarvan/openauth/AuthenticationException
    //   480: dup
    //   481: aload #14
    //   483: aload #12
    //   485: ldc fr/litarvan/openauth/model/AuthError
    //   487: invokevirtual fromJson : (Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   490: checkcast fr/litarvan/openauth/model/AuthError
    //   493: invokespecial <init> : (Lfr/litarvan/openauth/model/AuthError;)V
    //   496: athrow
    //   497: aload #12
    //   499: areturn
    // Exception table:
    //   from	to	target	type
    //   199	222	225	java/io/IOException
    //   219	236	236	java/io/IOException
    //   302	307	310	java/io/IOException
    //   327	345	348	java/io/IOException
    //   340	355	358	java/io/IOException
    //   402	412	415	java/io/IOException
    //   421	473	473	java/io/IOException
  }
  
  static {
    long l = a ^ 0x2263F1F10A76L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[11];
    boolean bool = false;
    String str;
    int i = (str = "@ßÏ]\000þ?k¥¥ÁHH\0205Wí)#+p`Þáç¬\020=#è{ÁµO=\f\0252Í@¼\002ë\037oJ¬\0377;ñ\021g­axû¬\020ÇÙÄA§À\013!ËD:\005IzgA\021^®NzÎ¼Ûj\031 /g\006\bTþ\027E\0261L\032J\020\007h¤bÏ5){\fpO¿\024»¯\030çú[gÖ£llí¢ïDPó[\023É\005Qu(ö,\032-\004ý<p\bli\025uM\\\020iÇÏ0sbÌî¹©\023ú\033¼²\n ¡\"\n\021®ïêk¾É²j¸1=%Üf\022Ù÷ .b<¡çæ´Ì7 >\rL\001Þ¯$-\000Ù=Ü{¥Qï\f-cåç.UR|Ñ").length();
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x797D;
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
        throw new RuntimeException("fr/litarvan/openauth/Authenticator", exception);
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
    //   66: ldc_w 'fr/litarvan/openauth/Authenticator'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0xA2D;
    if (f[i] == null) {
      byte[] arrayOfByte3;
      byte[] arrayOfByte1 = { (byte)(int)(paramLong >>> 56L), (byte)(int)(paramLong >>> 48L), (byte)(int)(paramLong >>> 40L), (byte)(int)(paramLong >>> 32L), (byte)(int)(paramLong >>> 24L), (byte)(int)(paramLong >>> 16L), (byte)(int)(paramLong >>> 8L), (byte)(int)paramLong };
      long l = e[i];
      byte[] arrayOfByte2 = { (byte)(int)(l >>> 56L), (byte)(int)(l >>> 48L), (byte)(int)(l >>> 40L), (byte)(int)(l >>> 32L), (byte)(int)(l >>> 24L), (byte)(int)(l >>> 16L), (byte)(int)(l >>> 8L), (byte)(int)l };
      Long long_ = Long.valueOf(Thread.currentThread().threadId());
      Object[] arrayOfObject = (Object[])g.get(long_);
      try {
        if (arrayOfObject == null) {
          arrayOfObject = new Object[3];
          arrayOfObject[0] = Cipher.getInstance("DES/CBC/NoPadding");
          arrayOfObject[1] = SecretKeyFactory.getInstance("DES");
          arrayOfObject[2] = new IvParameterSpec(new byte[8]);
          g.put(long_, arrayOfObject);
        } 
        DESKeySpec dESKeySpec = new DESKeySpec(arrayOfByte1);
        SecretKey secretKey = ((SecretKeyFactory)arrayOfObject[1]).generateSecret(dESKeySpec);
        Cipher cipher = (Cipher)arrayOfObject[0];
        cipher.init(2, secretKey, (IvParameterSpec)arrayOfObject[2]);
        arrayOfByte3 = cipher.doFinal(arrayOfByte2);
      } catch (Exception exception) {
        throw new RuntimeException("fr/litarvan/openauth/Authenticator", exception);
      } 
      int j = (arrayOfByte3[4] & 0xFF) << 24 | (arrayOfByte3[5] & 0xFF) << 16 | (arrayOfByte3[6] & 0xFF) << 8 | arrayOfByte3[7] & 0xFF;
      f[i] = Integer.valueOf(j);
    } 
    return f[i].intValue();
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
    //   66: ldc_w 'fr/litarvan/openauth/Authenticator'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\Authenticator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */