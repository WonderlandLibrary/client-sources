package fr.litarvan.openauth.microsoft;

import com.google.gson.Gson;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import org.json.JSONObject;
import wtf.opal.on;

public class HttpClient {
  public static final String MIME_TYPE_JSON;
  
  public static final String MIME_TYPE_URLENCODED_FORM;
  
  private final Gson gson = new Gson();
  
  private static final long a = on.a(303313517289643582L, -6751895756904308190L, MethodHandles.lookup().lookupClass()).a(252030756933115L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  private static final long[] e;
  
  private static final Integer[] f;
  
  private static final Map g;
  
  public String getText(String paramString, Map<String, String> paramMap) throws MicrosoftAuthenticationException {
    return readResponse(createConnection(paramString + "?" + paramString));
  }
  
  public String readPost(String paramString1, Map<String, Object> paramMap, String paramString2) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 30859474329975
    //   6: lxor
    //   7: lstore #4
    //   9: aload_0
    //   10: aload_0
    //   11: aload_1
    //   12: aload_3
    //   13: sipush #16069
    //   16: ldc2_w 930497093169015633
    //   19: lload #4
    //   21: lxor
    //   22: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   27: aload_0
    //   28: aload_2
    //   29: invokevirtual buildParamsStr : (Ljava/util/Map;)Ljava/lang/String;
    //   32: invokevirtual post : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   35: invokevirtual readResponse : (Ljava/net/HttpURLConnection;)Ljava/lang/String;
    //   38: areturn
  }
  
  public String readPost(String paramString1, Map<String, Object> paramMap, String paramString2, String paramString3) throws MicrosoftAuthenticationException {
    return readJson(post(paramString1, paramString2, paramString3, buildParamsStr(paramMap)));
  }
  
  public String readPost(String paramString1, JSONObject paramJSONObject, String paramString2) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 24429066582802
    //   6: lxor
    //   7: lstore #4
    //   9: aload_0
    //   10: aload_1
    //   11: aload_3
    //   12: sipush #23303
    //   15: ldc2_w 9091339249011463367
    //   18: lload #4
    //   20: lxor
    //   21: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   26: aload_2
    //   27: invokevirtual toString : ()Ljava/lang/String;
    //   30: invokevirtual post : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   33: astore #6
    //   35: aload_0
    //   36: aload #6
    //   38: invokevirtual readJson : (Ljava/net/HttpURLConnection;)Ljava/lang/String;
    //   41: areturn
  }
  
  public String readPost(String paramString1, JSONObject paramJSONObject, String paramString2, String paramString3) throws MicrosoftAuthenticationException {
    HttpURLConnection httpURLConnection = post(paramString1, paramString2, paramString3, paramJSONObject.toString());
    return readJson(httpURLConnection);
  }
  
  public <T> T getJson(String paramString1, String paramString2, Class<T> paramClass) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 62403628809516
    //   6: lxor
    //   7: lstore #4
    //   9: aload_0
    //   10: aload_1
    //   11: invokevirtual createConnection : (Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   14: astore #6
    //   16: aload #6
    //   18: sipush #23579
    //   21: ldc2_w 886630031187236320
    //   24: lload #4
    //   26: lxor
    //   27: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   32: aload_2
    //   33: sipush #13297
    //   36: ldc2_w 1455920851274578492
    //   39: lload #4
    //   41: lxor
    //   42: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   47: swap
    //   48: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   53: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   56: aload #6
    //   58: sipush #28592
    //   61: ldc2_w 5395945580249998918
    //   64: lload #4
    //   66: lxor
    //   67: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   72: sipush #26877
    //   75: ldc2_w 7457461511128834322
    //   78: lload #4
    //   80: lxor
    //   81: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   86: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   89: aload_0
    //   90: aload #6
    //   92: aload_3
    //   93: invokevirtual readJson : (Ljava/net/HttpURLConnection;Ljava/lang/Class;)Ljava/lang/Object;
    //   96: areturn
  }
  
  public HttpURLConnection postForm(String paramString, Map<String, String> paramMap) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 16574665309189
    //   6: lxor
    //   7: lstore_3
    //   8: aload_0
    //   9: aload_1
    //   10: sipush #15870
    //   13: ldc2_w 4321765598773163307
    //   16: lload_3
    //   17: lxor
    //   18: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   23: sipush #23303
    //   26: ldc2_w 9091349027600552912
    //   29: lload_3
    //   30: lxor
    //   31: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   36: aload_0
    //   37: aload_2
    //   38: invokevirtual buildParams : (Ljava/util/Map;)Ljava/lang/String;
    //   41: invokevirtual post : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   44: areturn
  }
  
  public <T> T postJson(String paramString, Object paramObject, Class<T> paramClass) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 122678204190170
    //   6: lxor
    //   7: lstore #4
    //   9: aload_0
    //   10: aload_1
    //   11: sipush #12945
    //   14: ldc2_w 8730754130562370448
    //   17: lload #4
    //   19: lxor
    //   20: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   25: sipush #26877
    //   28: ldc2_w 7457519983939260900
    //   31: lload #4
    //   33: lxor
    //   34: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   39: aload_0
    //   40: getfield gson : Lcom/google/gson/Gson;
    //   43: aload_2
    //   44: invokevirtual toJson : (Ljava/lang/Object;)Ljava/lang/String;
    //   47: invokevirtual post : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   50: astore #6
    //   52: aload_0
    //   53: aload #6
    //   55: aload_3
    //   56: invokevirtual readJson : (Ljava/net/HttpURLConnection;Ljava/lang/Class;)Ljava/lang/Object;
    //   59: areturn
  }
  
  public <T> T postFormGetJson(String paramString, Map<String, String> paramMap, Class<T> paramClass) throws MicrosoftAuthenticationException {
    return readJson(postForm(paramString, paramMap), paramClass);
  }
  
  protected HttpURLConnection post(String paramString1, String paramString2, String paramString3, String paramString4) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 102009223017333
    //   6: lxor
    //   7: lstore #5
    //   9: aload_0
    //   10: aload_1
    //   11: invokevirtual createConnection : (Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   14: astore #7
    //   16: aload #7
    //   18: iconst_1
    //   19: invokevirtual setDoOutput : (Z)V
    //   22: aload #7
    //   24: sipush #13062
    //   27: ldc2_w 5537215934328698041
    //   30: lload #5
    //   32: lxor
    //   33: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   38: aload_2
    //   39: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   42: aload #7
    //   44: sipush #21471
    //   47: ldc2_w 9221714045459862645
    //   50: lload #5
    //   52: lxor
    //   53: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   58: aload_3
    //   59: invokevirtual addRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   62: aload #7
    //   64: sipush #8356
    //   67: ldc2_w 649393122141005576
    //   70: lload #5
    //   72: lxor
    //   73: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   78: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   81: aload #7
    //   83: invokevirtual getOutputStream : ()Ljava/io/OutputStream;
    //   86: aload #4
    //   88: invokevirtual getBytes : ()[B
    //   91: invokevirtual write : ([B)V
    //   94: goto -> 109
    //   97: astore #8
    //   99: new fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   102: dup
    //   103: aload #8
    //   105: invokespecial <init> : (Ljava/io/IOException;)V
    //   108: athrow
    //   109: aload #7
    //   111: areturn
    // Exception table:
    //   from	to	target	type
    //   62	94	97	java/io/IOException
  }
  
  protected HttpURLConnection put(String paramString) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 60259928889377
    //   6: lxor
    //   7: lstore_2
    //   8: aload_0
    //   9: aload_1
    //   10: invokevirtual createConnection : (Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   13: astore #4
    //   15: aload #4
    //   17: iconst_1
    //   18: invokevirtual setDoOutput : (Z)V
    //   21: aload #4
    //   23: sipush #31313
    //   26: ldc2_w 4872044581992961701
    //   29: lload_2
    //   30: lxor
    //   31: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   36: invokevirtual setRequestMethod : (Ljava/lang/String;)V
    //   39: goto -> 54
    //   42: astore #5
    //   44: new fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   47: dup
    //   48: aload #5
    //   50: invokespecial <init> : (Ljava/io/IOException;)V
    //   53: athrow
    //   54: aload #4
    //   56: areturn
    // Exception table:
    //   from	to	target	type
    //   21	39	42	java/io/IOException
  }
  
  protected <T> T readJson(HttpURLConnection paramHttpURLConnection, Class<T> paramClass) throws MicrosoftAuthenticationException {
    return (T)this.gson.fromJson(readResponse(paramHttpURLConnection), paramClass);
  }
  
  protected String readJson(HttpURLConnection paramHttpURLConnection) throws MicrosoftAuthenticationException {
    return readResponse(paramHttpURLConnection);
  }
  
  protected String readResponse(HttpURLConnection paramHttpURLConnection) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 129028352398566
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic E : ()Z
    //   11: aload_1
    //   12: sipush #15987
    //   15: ldc2_w 855631559899962960
    //   18: lload_2
    //   19: lxor
    //   20: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   25: invokevirtual getHeaderField : (Ljava/lang/String;)Ljava/lang/String;
    //   28: astore #5
    //   30: istore #4
    //   32: aload #5
    //   34: iload #4
    //   36: ifne -> 66
    //   39: ifnull -> 67
    //   42: goto -> 49
    //   45: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   48: athrow
    //   49: aload_0
    //   50: aload_0
    //   51: aload #5
    //   53: invokevirtual createConnection : (Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   56: invokevirtual readResponse : (Ljava/net/HttpURLConnection;)Ljava/lang/String;
    //   59: goto -> 66
    //   62: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   65: athrow
    //   66: areturn
    //   67: new java/lang/StringBuilder
    //   70: dup
    //   71: invokespecial <init> : ()V
    //   74: astore #6
    //   76: aload_1
    //   77: invokevirtual getInputStream : ()Ljava/io/InputStream;
    //   80: astore #7
    //   82: aload_0
    //   83: aload_1
    //   84: invokevirtual getURL : ()Ljava/net/URL;
    //   87: invokevirtual checkUrl : (Ljava/net/URL;)Z
    //   90: ifeq -> 259
    //   93: new java/io/ByteArrayOutputStream
    //   96: dup
    //   97: invokespecial <init> : ()V
    //   100: astore #8
    //   102: sipush #6779
    //   105: ldc2_w 4554272776859981320
    //   108: lload_2
    //   109: lxor
    //   110: <illegal opcode> j : (IJ)I
    //   115: newarray byte
    //   117: astore #10
    //   119: aload #7
    //   121: aload #10
    //   123: iconst_0
    //   124: aload #10
    //   126: arraylength
    //   127: invokevirtual read : ([BII)I
    //   130: dup
    //   131: istore #9
    //   133: iconst_m1
    //   134: if_icmpeq -> 164
    //   137: aload #8
    //   139: aload #10
    //   141: iconst_0
    //   142: iload #9
    //   144: invokevirtual write : ([BII)V
    //   147: iload #4
    //   149: ifne -> 259
    //   152: iload #4
    //   154: ifeq -> 119
    //   157: goto -> 164
    //   160: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   163: athrow
    //   164: aload #8
    //   166: sipush #944
    //   169: ldc2_w 251894326319670144
    //   172: lload_2
    //   173: lxor
    //   174: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   179: invokevirtual toString : (Ljava/lang/String;)Ljava/lang/String;
    //   182: sipush #8331
    //   185: ldc2_w 6448928201323135146
    //   188: lload_2
    //   189: lxor
    //   190: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   195: sipush #27639
    //   198: ldc2_w 1384779861400030161
    //   201: lload_2
    //   202: lxor
    //   203: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   208: invokevirtual replaceAll : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   211: sipush #15865
    //   214: ldc2_w 7083764251941330391
    //   217: lload_2
    //   218: lxor
    //   219: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   224: sipush #15790
    //   227: ldc2_w 8146156497149255046
    //   230: lload_2
    //   231: lxor
    //   232: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   237: invokevirtual replaceAll : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   240: getstatic java/nio/charset/StandardCharsets.UTF_8 : Ljava/nio/charset/Charset;
    //   243: invokevirtual getBytes : (Ljava/nio/charset/Charset;)[B
    //   246: astore #11
    //   248: new java/io/ByteArrayInputStream
    //   251: dup
    //   252: aload #11
    //   254: invokespecial <init> : ([B)V
    //   257: astore #7
    //   259: new java/io/BufferedReader
    //   262: dup
    //   263: new java/io/InputStreamReader
    //   266: dup
    //   267: aload #7
    //   269: invokespecial <init> : (Ljava/io/InputStream;)V
    //   272: invokespecial <init> : (Ljava/io/Reader;)V
    //   275: astore #8
    //   277: aload #8
    //   279: invokevirtual readLine : ()Ljava/lang/String;
    //   282: dup
    //   283: astore #9
    //   285: ifnull -> 329
    //   288: aload #6
    //   290: aload #9
    //   292: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   295: sipush #24599
    //   298: ldc2_w 7399941354643319905
    //   301: lload_2
    //   302: lxor
    //   303: <illegal opcode> j : (IJ)I
    //   308: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   311: pop
    //   312: iload #4
    //   314: ifne -> 334
    //   317: iload #4
    //   319: ifeq -> 277
    //   322: goto -> 329
    //   325: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   328: athrow
    //   329: aload #8
    //   331: invokevirtual close : ()V
    //   334: goto -> 359
    //   337: astore #9
    //   339: aload #8
    //   341: invokevirtual close : ()V
    //   344: goto -> 356
    //   347: astore #10
    //   349: aload #9
    //   351: aload #10
    //   353: invokevirtual addSuppressed : (Ljava/lang/Throwable;)V
    //   356: aload #9
    //   358: athrow
    //   359: goto -> 374
    //   362: astore #8
    //   364: new fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   367: dup
    //   368: aload #8
    //   370: invokespecial <init> : (Ljava/io/IOException;)V
    //   373: athrow
    //   374: goto -> 389
    //   377: astore #7
    //   379: new java/lang/RuntimeException
    //   382: dup
    //   383: aload #7
    //   385: invokespecial <init> : (Ljava/lang/Throwable;)V
    //   388: athrow
    //   389: aload #6
    //   391: invokevirtual toString : ()Ljava/lang/String;
    //   394: areturn
    // Exception table:
    //   from	to	target	type
    //   32	42	45	java/lang/Throwable
    //   39	59	62	java/lang/Throwable
    //   76	374	377	java/io/IOException
    //   137	157	160	java/lang/Throwable
    //   259	359	362	java/io/IOException
    //   277	329	337	java/lang/Throwable
    //   288	322	325	java/lang/Throwable
    //   339	344	347	java/lang/Throwable
  }
  
  private boolean checkUrl(URL paramURL) {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 12223162654223
    //   6: lxor
    //   7: lstore_2
    //   8: invokestatic E : ()Z
    //   11: istore #4
    //   13: sipush #30240
    //   16: ldc2_w 2165532689401896171
    //   19: lload_2
    //   20: lxor
    //   21: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   26: aload_1
    //   27: invokevirtual getHost : ()Ljava/lang/String;
    //   30: invokevirtual equals : (Ljava/lang/Object;)Z
    //   33: iload #4
    //   35: ifne -> 117
    //   38: ifeq -> 90
    //   41: goto -> 48
    //   44: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   47: athrow
    //   48: aload_1
    //   49: invokevirtual getPath : ()Ljava/lang/String;
    //   52: sipush #10500
    //   55: ldc2_w 8961775190304490463
    //   58: lload_2
    //   59: lxor
    //   60: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   65: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   68: iload #4
    //   70: ifne -> 679
    //   73: goto -> 80
    //   76: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   79: athrow
    //   80: ifne -> 678
    //   83: goto -> 90
    //   86: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   89: athrow
    //   90: sipush #23705
    //   93: ldc2_w 8164579900158277213
    //   96: lload_2
    //   97: lxor
    //   98: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   103: aload_1
    //   104: invokevirtual getHost : ()Ljava/lang/String;
    //   107: invokevirtual equals : (Ljava/lang/Object;)Z
    //   110: goto -> 117
    //   113: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   116: athrow
    //   117: iload #4
    //   119: ifne -> 201
    //   122: ifeq -> 174
    //   125: goto -> 132
    //   128: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   131: athrow
    //   132: sipush #14090
    //   135: ldc2_w 1429846143340584394
    //   138: lload_2
    //   139: lxor
    //   140: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   145: aload_1
    //   146: invokevirtual getPath : ()Ljava/lang/String;
    //   149: invokevirtual equals : (Ljava/lang/Object;)Z
    //   152: iload #4
    //   154: ifne -> 679
    //   157: goto -> 164
    //   160: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   163: athrow
    //   164: ifne -> 678
    //   167: goto -> 174
    //   170: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   173: athrow
    //   174: sipush #6554
    //   177: ldc2_w 3691825657271726931
    //   180: lload_2
    //   181: lxor
    //   182: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   187: aload_1
    //   188: invokevirtual getHost : ()Ljava/lang/String;
    //   191: invokevirtual equals : (Ljava/lang/Object;)Z
    //   194: goto -> 201
    //   197: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   200: athrow
    //   201: iload #4
    //   203: ifne -> 285
    //   206: ifeq -> 258
    //   209: goto -> 216
    //   212: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   215: athrow
    //   216: sipush #3807
    //   219: ldc2_w 5144035022710547473
    //   222: lload_2
    //   223: lxor
    //   224: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   229: aload_1
    //   230: invokevirtual getPath : ()Ljava/lang/String;
    //   233: invokevirtual equals : (Ljava/lang/Object;)Z
    //   236: iload #4
    //   238: ifne -> 679
    //   241: goto -> 248
    //   244: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   247: athrow
    //   248: ifne -> 678
    //   251: goto -> 258
    //   254: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   257: athrow
    //   258: sipush #24619
    //   261: ldc2_w 1426121882555534056
    //   264: lload_2
    //   265: lxor
    //   266: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   271: aload_1
    //   272: invokevirtual getHost : ()Ljava/lang/String;
    //   275: invokevirtual equals : (Ljava/lang/Object;)Z
    //   278: goto -> 285
    //   281: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   284: athrow
    //   285: iload #4
    //   287: ifne -> 369
    //   290: ifeq -> 342
    //   293: goto -> 300
    //   296: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   299: athrow
    //   300: sipush #26833
    //   303: ldc2_w 8425220528563372557
    //   306: lload_2
    //   307: lxor
    //   308: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   313: aload_1
    //   314: invokevirtual getPath : ()Ljava/lang/String;
    //   317: invokevirtual equals : (Ljava/lang/Object;)Z
    //   320: iload #4
    //   322: ifne -> 679
    //   325: goto -> 332
    //   328: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   331: athrow
    //   332: ifne -> 678
    //   335: goto -> 342
    //   338: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   341: athrow
    //   342: sipush #24619
    //   345: ldc2_w 1426121882555534056
    //   348: lload_2
    //   349: lxor
    //   350: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   355: aload_1
    //   356: invokevirtual getHost : ()Ljava/lang/String;
    //   359: invokevirtual equals : (Ljava/lang/Object;)Z
    //   362: goto -> 369
    //   365: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   368: athrow
    //   369: iload #4
    //   371: ifne -> 453
    //   374: ifeq -> 426
    //   377: goto -> 384
    //   380: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   383: athrow
    //   384: aload_1
    //   385: invokevirtual getPath : ()Ljava/lang/String;
    //   388: sipush #20347
    //   391: ldc2_w 5208859506610562486
    //   394: lload_2
    //   395: lxor
    //   396: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   401: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   404: iload #4
    //   406: ifne -> 679
    //   409: goto -> 416
    //   412: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   415: athrow
    //   416: ifne -> 678
    //   419: goto -> 426
    //   422: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   425: athrow
    //   426: sipush #24619
    //   429: ldc2_w 1426121882555534056
    //   432: lload_2
    //   433: lxor
    //   434: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   439: aload_1
    //   440: invokevirtual getHost : ()Ljava/lang/String;
    //   443: invokevirtual equals : (Ljava/lang/Object;)Z
    //   446: goto -> 453
    //   449: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   452: athrow
    //   453: iload #4
    //   455: ifne -> 537
    //   458: ifeq -> 510
    //   461: goto -> 468
    //   464: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   467: athrow
    //   468: aload_1
    //   469: invokevirtual getPath : ()Ljava/lang/String;
    //   472: sipush #18690
    //   475: ldc2_w 6835412544726197205
    //   478: lload_2
    //   479: lxor
    //   480: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   485: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   488: iload #4
    //   490: ifne -> 679
    //   493: goto -> 500
    //   496: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   499: athrow
    //   500: ifne -> 678
    //   503: goto -> 510
    //   506: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   509: athrow
    //   510: sipush #24619
    //   513: ldc2_w 1426121882555534056
    //   516: lload_2
    //   517: lxor
    //   518: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   523: aload_1
    //   524: invokevirtual getHost : ()Ljava/lang/String;
    //   527: invokevirtual equals : (Ljava/lang/Object;)Z
    //   530: goto -> 537
    //   533: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   536: athrow
    //   537: iload #4
    //   539: ifne -> 621
    //   542: ifeq -> 594
    //   545: goto -> 552
    //   548: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   551: athrow
    //   552: aload_1
    //   553: invokevirtual getPath : ()Ljava/lang/String;
    //   556: sipush #14758
    //   559: ldc2_w 1917356444771292023
    //   562: lload_2
    //   563: lxor
    //   564: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   569: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   572: iload #4
    //   574: ifne -> 679
    //   577: goto -> 584
    //   580: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   583: athrow
    //   584: ifne -> 678
    //   587: goto -> 594
    //   590: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   593: athrow
    //   594: sipush #24619
    //   597: ldc2_w 1426121882555534056
    //   600: lload_2
    //   601: lxor
    //   602: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   607: aload_1
    //   608: invokevirtual getHost : ()Ljava/lang/String;
    //   611: invokevirtual equals : (Ljava/lang/Object;)Z
    //   614: goto -> 621
    //   617: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   620: athrow
    //   621: iload #4
    //   623: ifne -> 663
    //   626: ifeq -> 682
    //   629: goto -> 636
    //   632: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   635: athrow
    //   636: aload_1
    //   637: invokevirtual getPath : ()Ljava/lang/String;
    //   640: sipush #13626
    //   643: ldc2_w 6063967267749109752
    //   646: lload_2
    //   647: lxor
    //   648: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   653: invokevirtual endsWith : (Ljava/lang/String;)Z
    //   656: goto -> 663
    //   659: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   662: athrow
    //   663: iload #4
    //   665: ifne -> 679
    //   668: ifeq -> 682
    //   671: goto -> 678
    //   674: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   677: athrow
    //   678: iconst_1
    //   679: goto -> 683
    //   682: iconst_0
    //   683: ireturn
    // Exception table:
    //   from	to	target	type
    //   13	41	44	java/lang/RuntimeException
    //   38	73	76	java/lang/RuntimeException
    //   48	83	86	java/lang/RuntimeException
    //   80	110	113	java/lang/RuntimeException
    //   117	125	128	java/lang/RuntimeException
    //   122	157	160	java/lang/RuntimeException
    //   132	167	170	java/lang/RuntimeException
    //   164	194	197	java/lang/RuntimeException
    //   201	209	212	java/lang/RuntimeException
    //   206	241	244	java/lang/RuntimeException
    //   216	251	254	java/lang/RuntimeException
    //   248	278	281	java/lang/RuntimeException
    //   285	293	296	java/lang/RuntimeException
    //   290	325	328	java/lang/RuntimeException
    //   300	335	338	java/lang/RuntimeException
    //   332	362	365	java/lang/RuntimeException
    //   369	377	380	java/lang/RuntimeException
    //   374	409	412	java/lang/RuntimeException
    //   384	419	422	java/lang/RuntimeException
    //   416	446	449	java/lang/RuntimeException
    //   453	461	464	java/lang/RuntimeException
    //   458	493	496	java/lang/RuntimeException
    //   468	503	506	java/lang/RuntimeException
    //   500	530	533	java/lang/RuntimeException
    //   537	545	548	java/lang/RuntimeException
    //   542	577	580	java/lang/RuntimeException
    //   552	587	590	java/lang/RuntimeException
    //   584	614	617	java/lang/RuntimeException
    //   621	629	632	java/lang/RuntimeException
    //   626	656	659	java/lang/RuntimeException
    //   663	671	674	java/lang/RuntimeException
  }
  
  protected HttpURLConnection followRedirects(HttpURLConnection paramHttpURLConnection) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 137599459999673
    //   6: lxor
    //   7: lstore_2
    //   8: aload_1
    //   9: sipush #6172
    //   12: ldc2_w 3424056718966887289
    //   15: lload_2
    //   16: lxor
    //   17: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   22: invokevirtual getHeaderField : (Ljava/lang/String;)Ljava/lang/String;
    //   25: astore #4
    //   27: aload #4
    //   29: ifnull -> 43
    //   32: aload_0
    //   33: aload_0
    //   34: aload #4
    //   36: invokevirtual createConnection : (Ljava/lang/String;)Ljava/net/HttpURLConnection;
    //   39: invokevirtual followRedirects : (Ljava/net/HttpURLConnection;)Ljava/net/HttpURLConnection;
    //   42: astore_1
    //   43: aload_1
    //   44: areturn
  }
  
  protected String buildParamsStr(Map<String, Object> paramMap) {
    StringBuilder stringBuilder = new StringBuilder();
    paramMap.forEach(stringBuilder::lambda$buildParamsStr$0);
    System.out.println(stringBuilder.toString());
    return stringBuilder.toString();
  }
  
  protected String buildParams(Map<String, String> paramMap) {
    StringBuilder stringBuilder = new StringBuilder();
    paramMap.forEach(stringBuilder::lambda$buildParams$1);
    return stringBuilder.toString();
  }
  
  protected HttpURLConnection createConnection(String paramString) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 79444365989677
    //   6: lxor
    //   7: lstore_2
    //   8: new java/net/URL
    //   11: dup
    //   12: aload_1
    //   13: invokespecial <init> : (Ljava/lang/String;)V
    //   16: invokevirtual openConnection : ()Ljava/net/URLConnection;
    //   19: checkcast java/net/HttpURLConnection
    //   22: astore #4
    //   24: goto -> 39
    //   27: astore #5
    //   29: new fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   32: dup
    //   33: aload #5
    //   35: invokespecial <init> : (Ljava/io/IOException;)V
    //   38: athrow
    //   39: sipush #30085
    //   42: ldc2_w 6961895054472746617
    //   45: lload_2
    //   46: lxor
    //   47: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   52: astore #5
    //   54: aload #4
    //   56: sipush #7833
    //   59: ldc2_w 8439321363764903250
    //   62: lload_2
    //   63: lxor
    //   64: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   69: sipush #11196
    //   72: ldc2_w 6168403829479819382
    //   75: lload_2
    //   76: lxor
    //   77: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   82: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   85: aload #4
    //   87: sipush #8672
    //   90: ldc2_w 4313535764061045252
    //   93: lload_2
    //   94: lxor
    //   95: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   100: sipush #9971
    //   103: ldc2_w 8580607263486002491
    //   106: lload_2
    //   107: lxor
    //   108: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   113: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   116: aload #4
    //   118: sipush #7160
    //   121: ldc2_w 1141179721017128968
    //   124: lload_2
    //   125: lxor
    //   126: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   131: aload #5
    //   133: invokevirtual setRequestProperty : (Ljava/lang/String;Ljava/lang/String;)V
    //   136: aload #4
    //   138: areturn
    // Exception table:
    //   from	to	target	type
    //   8	24	27	java/io/IOException
  }
  
  private static void lambda$buildParams$1(StringBuilder paramStringBuilder, String paramString1, String paramString2) {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 21035702469659
    //   6: lxor
    //   7: lstore_3
    //   8: invokestatic g : ()Z
    //   11: istore #5
    //   13: aload_0
    //   14: iload #5
    //   16: ifeq -> 98
    //   19: invokevirtual length : ()I
    //   22: ifle -> 57
    //   25: goto -> 32
    //   28: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   31: athrow
    //   32: aload_0
    //   33: sipush #25237
    //   36: ldc2_w 4151600518079053343
    //   39: lload_3
    //   40: lxor
    //   41: <illegal opcode> j : (IJ)I
    //   46: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   49: pop
    //   50: goto -> 57
    //   53: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   56: athrow
    //   57: aload_0
    //   58: aload_1
    //   59: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: sipush #3309
    //   65: ldc2_w 7662643289026302050
    //   68: lload_3
    //   69: lxor
    //   70: <illegal opcode> j : (IJ)I
    //   75: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   78: aload_2
    //   79: sipush #944
    //   82: ldc2_w 251795816958063485
    //   85: lload_3
    //   86: lxor
    //   87: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   92: invokestatic encode : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   95: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   98: pop
    //   99: goto -> 104
    //   102: astore #6
    //   104: return
    // Exception table:
    //   from	to	target	type
    //   13	25	28	java/io/UnsupportedEncodingException
    //   19	50	53	java/io/UnsupportedEncodingException
    //   57	99	102	java/io/UnsupportedEncodingException
  }
  
  private static void lambda$buildParamsStr$0(StringBuilder paramStringBuilder, String paramString, Object paramObject) {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/HttpClient.a : J
    //   3: ldc2_w 101988762382880
    //   6: lxor
    //   7: lstore_3
    //   8: invokestatic g : ()Z
    //   11: istore #5
    //   13: aload_0
    //   14: iload #5
    //   16: ifeq -> 101
    //   19: invokevirtual length : ()I
    //   22: ifle -> 57
    //   25: goto -> 32
    //   28: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   31: athrow
    //   32: aload_0
    //   33: sipush #5399
    //   36: ldc2_w 7537552828528213920
    //   39: lload_3
    //   40: lxor
    //   41: <illegal opcode> j : (IJ)I
    //   46: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   49: pop
    //   50: goto -> 57
    //   53: invokestatic a : (Ljava/lang/Throwable;)Ljava/lang/Throwable;
    //   56: athrow
    //   57: aload_0
    //   58: aload_1
    //   59: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   62: sipush #23070
    //   65: ldc2_w 7399340771446845608
    //   68: lload_3
    //   69: lxor
    //   70: <illegal opcode> j : (IJ)I
    //   75: invokevirtual append : (C)Ljava/lang/StringBuilder;
    //   78: aload_2
    //   79: invokevirtual toString : ()Ljava/lang/String;
    //   82: sipush #944
    //   85: ldc2_w 251850929343022406
    //   88: lload_3
    //   89: lxor
    //   90: <illegal opcode> h : (IJ)Ljava/lang/String;
    //   95: invokestatic encode : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   98: invokevirtual append : (Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   101: pop
    //   102: goto -> 107
    //   105: astore #6
    //   107: return
    // Exception table:
    //   from	to	target	type
    //   13	25	28	java/io/UnsupportedEncodingException
    //   19	50	53	java/io/UnsupportedEncodingException
    //   57	102	105	java/io/UnsupportedEncodingException
  }
  
  static {
    long l = a ^ 0x3D7CC1A2E2DEL;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[39];
    boolean bool = false;
    String str;
    int i = (str = "\007ò\030z Å\013ÖJ8ú§Ã·Wy'#K\007c:eØ\034\016\rÜfE¸1U¥æÂ#;\\dº¿\020tö\000³®ùØ®ké{}»80­\022aDÝöj8âz\004E5Äïc>Ü\016üÕYÂ`]&¿«k]¿t×:Ï²Äå,²\034(á´½â/\017@-ò%Ú=¦!§ÎuúR¡¨¨µuÀè¾ªTÓg' \026ð ¦\003D5Ñ\025¥°)\"G¢=áÚMà\025Ï:.÷ò6_á¦A\020´¬\030n}±FqEýNÓDûayÑsÓl\035¹ ·± ¢þ{iô9}è\003©JÍ>=2ØíÙ\004\035\000ø«kÓÈ ÷\bp06ôè^ö]©ýÈü+¾©Ö<â\005³\\z.#/À#\020\t-áª.{¦\020¸Pxsß\035<GMË6 \022®½9Â?\037fê\006NÈÁ[Ø<é\025õ/~ÏfR,ýê6YU¶ Kíùåäô\fCéK«i\nl\030\020\004<+1~Üðs æÈl>\027è\rå}ÿw\004¼\024aã ï\004\024ËÃ=\026¿H¡æÎ8\020$líú\r¹Søà@$ð\fIÌ|³ßuV<¾8ÄnÑM\030!\001\002qÂ\004`CIå Vwæøn G¡><(f\027þ²F²&\bêdLtBñæÇ\017£ÐÁº+ÁV,¦5Aö\027¼Ø¿º£@ÊÂ\001}Ð©á\017N×gÄÌc0CmIè@K\000¹n¹_H¶ÒCë1è\013P\bH\005Õ#VûÑÛY([yOÆ\030@\n'\017\033tÅ\b¹(-ÚPç\024²¶Ïª½Jû·[oú\031õ2u!ÀÏh=B3´\026<kþ\021¡×N8#Ë[ %t\004ö­Ñ$Fæ×'.\021°MHÝÏ¦D)\027X)ý<}Á\031Õ\017Ùß\021ÒB±ì\t­¼.RÖ{£g T\035F4×,À\001w\007\"3JxbZ\020\031\027èZ\037{ÇUK\t¸¯\020t8$L\003\r@\026\f¡\013n¤e}¸\007ê\035Ié3@NÓÝ\022\b7\021æ¾Ç5ë±\032P9àÖBàIû:$è\013\0243»ºn\020ßðòãÌûû÷Z\\¶[l^ÁÊé³òòß}áÕÍ·,ZB(C4wÔ²\\°êéÆ?aºBMôA\003f\033-|¡3Î³ãûwaYß±áÈë\035·Ø\"[×QvÝµ´Yó7Àñ=õ\f2BÄçe\trÆ«´ö¯ \023Hõ!¶,p\002;PÔU\017¯\021\013Ð S¬\023ÿ^ñ°d£H6<]e @\005§³ændL£©ø£ð\003¨ÚÈ\021\036²OM¤Ã°ó}pÃEZ}\030ÊáRÅ\030wN¼+\0166¼TÛjpÆò`ëLqYdVç=(ôa³è Z\031½5e;;fÞîóo\013M>8¸@.áX@q8\020\025Õ©¡Ã¤\0257ú\006,e\020G\007­´ÛÙK\022ÝÂj=Vê(sq²IÍî\004ÁJóUÔúoêª?¹Iý÷Òÿè\027¯Ù\b\031ÈØvb@T( èÝ«I¨/èn\027ÊÑ$F>$i°\034Ô8ßØ<ãõ¢Q\037m\031ð$µ\020\b\0176öSªbBà_\000ÒIû\0201~¦¡U3þi}­¸( gO¬@.Ú7·ï\\O¾Ë­lA\032­U\001re(¾Ë.5~é^Èy\020ÜµY¤*+^Q\007é\017Sv¬(L[éyíc¡U;Ìö!#IE§\007A\036Yn§_Â(\037\020\017\033Hâ\004âR°Ü\030Ý¢Ò4øÅy\007hIæ$F\004\fßLjê\027¹\030\007*_²\006Ië\035ûáh)\037·á¿/8Cb\020W¤±0(´æû  cë(x\"@òeøÖ)vùß¿¼²lDÙ(Ü{À\036&åCj\030V0¡£Èc×A§mÃ)Æ¯pçºòî9·B¶ÜüÓfWBT'O\020i ©öÔpXþü>3\023Onó(YQî¼\n\b¼SÀÄ#q ôÇ§wÌ.oUm\007§¥|jïOMhi¡\020±Æ¯~ _-Gf¨v&?\016}").length();
    byte b2 = 48;
    byte b = -1;
    while (true);
  }
  
  private static Throwable a(Throwable paramThrowable) {
    return paramThrowable;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x28FF;
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
        throw new RuntimeException("fr/litarvan/openauth/microsoft/HttpClient", exception);
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
    //   66: ldc_w 'fr/litarvan/openauth/microsoft/HttpClient'
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x44A9;
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
        throw new RuntimeException("fr/litarvan/openauth/microsoft/HttpClient", exception);
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
    //   66: ldc_w 'fr/litarvan/openauth/microsoft/HttpClient'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\HttpClient.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */