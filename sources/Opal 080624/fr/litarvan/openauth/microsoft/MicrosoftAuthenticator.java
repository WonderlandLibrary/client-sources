package fr.litarvan.openauth.microsoft;

import fr.litarvan.openauth.microsoft.model.response.MinecraftLoginResponse;
import fr.litarvan.openauth.microsoft.model.response.MinecraftStoreResponse;
import fr.litarvan.openauth.microsoft.model.response.XboxLoginResponse;
import java.lang.invoke.CallSite;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;
import java.lang.invoke.MutableCallSite;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import wtf.opal.on;
import wtf.opal.x5;

public class MicrosoftAuthenticator {
  public static final String MICROSOFT_AUTHORIZATION_ENDPOINT;
  
  public static final String MICROSOFT_TOKEN_ENDPOINT;
  
  public static final String MICROSOFT_REDIRECTION_ENDPOINT;
  
  public static final String XBOX_LIVE_AUTH_HOST;
  
  public static final String XBOX_LIVE_CLIENT_ID;
  
  public static final String XBOX_LIVE_SERVICE_SCOPE;
  
  public static final String XBOX_LIVE_AUTHORIZATION_ENDPOINT;
  
  public static final String XSTS_AUTHORIZATION_ENDPOINT;
  
  public static final String MINECRAFT_AUTH_ENDPOINT;
  
  public static final String CHANGE_NAME_ENDPOINT;
  
  public static final String XBOX_LIVE_AUTH_RELAY;
  
  public static final String MINECRAFT_AUTH_RELAY;
  
  public static final String MINECRAFT_STORE_ENDPOINT;
  
  public static final String MINECRAFT_PROFILE_ENDPOINT;
  
  public static final String MINECRAFT_STORE_IDENTIFIER;
  
  private final HttpClient http = new HttpClient();
  
  private static final long a = on.a(-5306281412325107637L, -8780959494439996029L, MethodHandles.lookup().lookupClass()).a(200536818047534L);
  
  private static final String[] b;
  
  private static final String[] c;
  
  private static final Map d = new HashMap<>(13);
  
  public MicrosoftAuthResult loginWithCredentials(String paramString1, String paramString2) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 67457546262113
    //   6: lxor
    //   7: lstore_3
    //   8: invokestatic E : ()Z
    //   11: invokestatic getDefault : ()Ljava/net/CookieHandler;
    //   14: astore #6
    //   16: new java/net/CookieManager
    //   19: dup
    //   20: aconst_null
    //   21: getstatic java/net/CookiePolicy.ACCEPT_ALL : Ljava/net/CookiePolicy;
    //   24: invokespecial <init> : (Ljava/net/CookieStore;Ljava/net/CookiePolicy;)V
    //   27: invokestatic setDefault : (Ljava/net/CookieHandler;)V
    //   30: new java/util/HashMap
    //   33: dup
    //   34: invokespecial <init> : ()V
    //   37: astore #7
    //   39: aload #7
    //   41: sipush #2609
    //   44: ldc2_w 1169098565525079983
    //   47: lload_3
    //   48: lxor
    //   49: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   54: aload_1
    //   55: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   60: pop
    //   61: istore #5
    //   63: aload #7
    //   65: sipush #4907
    //   68: ldc2_w 2517466774970834579
    //   71: lload_3
    //   72: lxor
    //   73: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   78: aload_1
    //   79: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   84: pop
    //   85: aload #7
    //   87: sipush #31188
    //   90: ldc2_w 1664495056823252053
    //   93: lload_3
    //   94: lxor
    //   95: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   100: aload_2
    //   101: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   106: pop
    //   107: aload_0
    //   108: invokevirtual preAuthRequest : ()Lfr/litarvan/openauth/microsoft/PreAuthData;
    //   111: astore #9
    //   113: aload #7
    //   115: sipush #6875
    //   118: ldc2_w 1588309531265636189
    //   121: lload_3
    //   122: lxor
    //   123: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   128: aload #9
    //   130: invokevirtual getPPFT : ()Ljava/lang/String;
    //   133: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   138: pop
    //   139: aload_0
    //   140: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   143: aload_0
    //   144: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   147: aload #9
    //   149: invokevirtual getUrlPost : ()Ljava/lang/String;
    //   152: aload #7
    //   154: invokevirtual postForm : (Ljava/lang/String;Ljava/util/Map;)Ljava/net/HttpURLConnection;
    //   157: invokevirtual followRedirects : (Ljava/net/HttpURLConnection;)Ljava/net/HttpURLConnection;
    //   160: astore #8
    //   162: aload #6
    //   164: invokestatic setDefault : (Ljava/net/CookieHandler;)V
    //   167: goto -> 180
    //   170: astore #10
    //   172: aload #6
    //   174: invokestatic setDefault : (Ljava/net/CookieHandler;)V
    //   177: aload #10
    //   179: athrow
    //   180: aload_0
    //   181: aload_0
    //   182: aload #8
    //   184: invokevirtual getURL : ()Ljava/net/URL;
    //   187: invokevirtual toString : ()Ljava/lang/String;
    //   190: invokevirtual extractTokens : (Ljava/lang/String;)Lfr/litarvan/openauth/microsoft/AuthTokens;
    //   193: invokevirtual loginWithTokens : (Lfr/litarvan/openauth/microsoft/AuthTokens;)Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   196: iload #5
    //   198: ifeq -> 215
    //   201: iconst_2
    //   202: anewarray wtf/opal/d
    //   205: invokestatic p : ([Lwtf/opal/d;)V
    //   208: goto -> 215
    //   211: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   214: athrow
    //   215: areturn
    //   216: astore #9
    //   218: aload_0
    //   219: sipush #14498
    //   222: ldc2_w 2233400306147865886
    //   225: lload_3
    //   226: lxor
    //   227: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   232: aload_0
    //   233: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   236: aload #8
    //   238: invokevirtual readResponse : (Ljava/net/HttpURLConnection;)Ljava/lang/String;
    //   241: invokevirtual match : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   244: ifnull -> 272
    //   247: new fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   250: dup
    //   251: sipush #27115
    //   254: ldc2_w 9201963431946019953
    //   257: lload_3
    //   258: lxor
    //   259: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   264: invokespecial <init> : (Ljava/lang/String;)V
    //   267: athrow
    //   268: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   271: athrow
    //   272: aload #9
    //   274: athrow
    // Exception table:
    //   from	to	target	type
    //   107	162	170	finally
    //   170	172	170	finally
    //   180	196	216	fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   180	208	211	wtf/opal/x5
    //   218	268	268	fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
  }
  
  public MicrosoftAuthResult loginWithRefreshToken(String paramString) throws MicrosoftAuthenticationException {
    return loginWithRefreshToken(paramString, getLoginParams());
  }
  
  public MicrosoftAuthResult loginWithRefreshToken(String paramString, Map<String, String> paramMap) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 103460301725924
    //   6: lxor
    //   7: lstore_3
    //   8: aload_2
    //   9: sipush #9659
    //   12: ldc2_w 9107354965586009776
    //   15: lload_3
    //   16: lxor
    //   17: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   22: aload_1
    //   23: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   28: pop
    //   29: aload_2
    //   30: sipush #11484
    //   33: ldc2_w 2320608036238219215
    //   36: lload_3
    //   37: lxor
    //   38: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   43: sipush #9659
    //   46: ldc2_w 9107354965586009776
    //   49: lload_3
    //   50: lxor
    //   51: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   56: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   61: pop
    //   62: aload_0
    //   63: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   66: sipush #24542
    //   69: ldc2_w 3568658403274859732
    //   72: lload_3
    //   73: lxor
    //   74: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   79: aload_2
    //   80: ldc fr/litarvan/openauth/microsoft/model/response/MicrosoftRefreshResponse
    //   82: invokevirtual postFormGetJson : (Ljava/lang/String;Ljava/util/Map;Ljava/lang/Class;)Ljava/lang/Object;
    //   85: checkcast fr/litarvan/openauth/microsoft/model/response/MicrosoftRefreshResponse
    //   88: astore #5
    //   90: aload_0
    //   91: new fr/litarvan/openauth/microsoft/AuthTokens
    //   94: dup
    //   95: aload #5
    //   97: invokevirtual getAccessToken : ()Ljava/lang/String;
    //   100: aload #5
    //   102: invokevirtual getRefreshToken : ()Ljava/lang/String;
    //   105: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   108: invokevirtual loginWithTokens : (Lfr/litarvan/openauth/microsoft/AuthTokens;)Lfr/litarvan/openauth/microsoft/MicrosoftAuthResult;
    //   111: areturn
  }
  
  public MicrosoftAuthResult loginWithTokens(AuthTokens paramAuthTokens) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 8683139406718
    //   6: lxor
    //   7: lstore_2
    //   8: aload_0
    //   9: aload_1
    //   10: invokevirtual getAccessToken : ()Ljava/lang/String;
    //   13: invokevirtual xboxLiveLogin : (Ljava/lang/String;)Lfr/litarvan/openauth/microsoft/model/response/XboxLoginResponse;
    //   16: astore #5
    //   18: invokestatic E : ()Z
    //   21: aload_0
    //   22: aload #5
    //   24: invokevirtual getToken : ()Ljava/lang/String;
    //   27: invokevirtual xstsLogin : (Ljava/lang/String;)Lfr/litarvan/openauth/microsoft/model/response/XboxLoginResponse;
    //   30: astore #6
    //   32: aload #6
    //   34: invokevirtual getDisplayClaims : ()Lfr/litarvan/openauth/microsoft/model/response/XboxLoginResponse$XboxLiveLoginResponseClaims;
    //   37: invokevirtual getUsers : ()[Lfr/litarvan/openauth/microsoft/model/response/XboxLoginResponse$XboxLiveUserInfo;
    //   40: iconst_0
    //   41: aaload
    //   42: invokevirtual getUserHash : ()Ljava/lang/String;
    //   45: astore #7
    //   47: istore #4
    //   49: aload_0
    //   50: aload #7
    //   52: aload #6
    //   54: invokevirtual getToken : ()Ljava/lang/String;
    //   57: invokevirtual minecraftLogin : (Ljava/lang/String;Ljava/lang/String;)Lfr/litarvan/openauth/microsoft/model/response/MinecraftLoginResponse;
    //   60: astore #8
    //   62: aload_0
    //   63: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   66: sipush #4501
    //   69: ldc2_w 1479443497415610626
    //   72: lload_2
    //   73: lxor
    //   74: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   79: aload #8
    //   81: invokevirtual getAccessToken : ()Ljava/lang/String;
    //   84: ldc fr/litarvan/openauth/microsoft/model/response/MinecraftStoreResponse
    //   86: invokevirtual getJson : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   89: checkcast fr/litarvan/openauth/microsoft/model/response/MinecraftStoreResponse
    //   92: astore #9
    //   94: aload #9
    //   96: invokevirtual getItems : ()[Lfr/litarvan/openauth/microsoft/model/response/MinecraftStoreResponse$StoreProduct;
    //   99: invokestatic stream : ([Ljava/lang/Object;)Ljava/util/stream/Stream;
    //   102: iload #4
    //   104: ifne -> 180
    //   107: <illegal opcode> test : ()Ljava/util/function/Predicate;
    //   112: invokeinterface noneMatch : (Ljava/util/function/Predicate;)Z
    //   117: ifeq -> 152
    //   120: goto -> 127
    //   123: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   126: athrow
    //   127: new fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   130: dup
    //   131: sipush #1528
    //   134: ldc2_w 4115354104978801992
    //   137: lload_2
    //   138: lxor
    //   139: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   144: invokespecial <init> : (Ljava/lang/String;)V
    //   147: athrow
    //   148: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   151: athrow
    //   152: aload_0
    //   153: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   156: sipush #21123
    //   159: ldc2_w 1183308875382280739
    //   162: lload_2
    //   163: lxor
    //   164: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   169: aload #8
    //   171: invokevirtual getAccessToken : ()Ljava/lang/String;
    //   174: ldc_w fr/litarvan/openauth/microsoft/model/response/MinecraftProfile
    //   177: invokevirtual getJson : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/Class;)Ljava/lang/Object;
    //   180: checkcast fr/litarvan/openauth/microsoft/model/response/MinecraftProfile
    //   183: astore #10
    //   185: new fr/litarvan/openauth/microsoft/MicrosoftAuthResult
    //   188: dup
    //   189: aload #10
    //   191: aload #8
    //   193: invokevirtual getAccessToken : ()Ljava/lang/String;
    //   196: aload_1
    //   197: invokevirtual getRefreshToken : ()Ljava/lang/String;
    //   200: invokespecial <init> : (Lfr/litarvan/openauth/microsoft/model/response/MinecraftProfile;Ljava/lang/String;Ljava/lang/String;)V
    //   203: invokestatic D : ()[Lwtf/opal/d;
    //   206: ifnull -> 233
    //   209: iload #4
    //   211: ifeq -> 229
    //   214: goto -> 221
    //   217: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   220: athrow
    //   221: iconst_0
    //   222: goto -> 230
    //   225: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   228: athrow
    //   229: iconst_1
    //   230: invokestatic m : (Z)V
    //   233: areturn
    // Exception table:
    //   from	to	target	type
    //   94	120	123	fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   107	148	148	fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   185	214	217	fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   209	225	225	fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
  }
  
  protected PreAuthData preAuthRequest() throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 81596545233344
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: invokevirtual getLoginParams : ()Ljava/util/Map;
    //   12: astore_3
    //   13: aload_3
    //   14: sipush #6373
    //   17: ldc2_w 5660048928825897716
    //   20: lload_1
    //   21: lxor
    //   22: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   27: sipush #14715
    //   30: ldc2_w 5307081379464827752
    //   33: lload_1
    //   34: lxor
    //   35: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   40: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   45: pop
    //   46: aload_3
    //   47: sipush #21335
    //   50: ldc2_w 4937181390932162943
    //   53: lload_1
    //   54: lxor
    //   55: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   60: sipush #13247
    //   63: ldc2_w 3474154737836054959
    //   66: lload_1
    //   67: lxor
    //   68: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   73: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   78: pop
    //   79: aload_0
    //   80: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   83: sipush #14879
    //   86: ldc2_w 4754719386372132921
    //   89: lload_1
    //   90: lxor
    //   91: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   96: aload_3
    //   97: invokevirtual getText : (Ljava/lang/String;Ljava/util/Map;)Ljava/lang/String;
    //   100: astore #4
    //   102: aload_0
    //   103: sipush #21286
    //   106: ldc2_w 5570555065259145482
    //   109: lload_1
    //   110: lxor
    //   111: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   116: aload #4
    //   118: invokevirtual match : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   121: astore #5
    //   123: aload_0
    //   124: sipush #28835
    //   127: ldc2_w 2731992715894364842
    //   130: lload_1
    //   131: lxor
    //   132: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   137: aload #4
    //   139: invokevirtual match : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   142: astore #6
    //   144: new fr/litarvan/openauth/microsoft/PreAuthData
    //   147: dup
    //   148: aload #5
    //   150: aload #6
    //   152: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   155: areturn
  }
  
  protected XboxLoginResponse xboxLiveLogin(String paramString) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 72890764463722
    //   6: lxor
    //   7: lstore_2
    //   8: new fr/litarvan/openauth/microsoft/model/request/XboxLiveLoginProperties
    //   11: dup
    //   12: sipush #4709
    //   15: ldc2_w 403427820007491570
    //   18: lload_2
    //   19: lxor
    //   20: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   25: sipush #3897
    //   28: ldc2_w 5128864381403142812
    //   31: lload_2
    //   32: lxor
    //   33: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   38: aload_1
    //   39: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
    //   42: astore #4
    //   44: new fr/litarvan/openauth/microsoft/model/request/XboxLoginRequest
    //   47: dup
    //   48: aload #4
    //   50: sipush #2259
    //   53: ldc2_w 2566406414507842918
    //   56: lload_2
    //   57: lxor
    //   58: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   63: sipush #7190
    //   66: ldc2_w 7784548770086193538
    //   69: lload_2
    //   70: lxor
    //   71: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   76: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V
    //   79: astore #5
    //   81: aload_0
    //   82: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   85: sipush #20684
    //   88: ldc2_w 4488654487590684002
    //   91: lload_2
    //   92: lxor
    //   93: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   98: aload #5
    //   100: ldc fr/litarvan/openauth/microsoft/model/response/XboxLoginResponse
    //   102: invokevirtual postJson : (Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   105: checkcast fr/litarvan/openauth/microsoft/model/response/XboxLoginResponse
    //   108: areturn
  }
  
  protected XboxLoginResponse xstsLogin(String paramString) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 120434409821917
    //   6: lxor
    //   7: lstore_2
    //   8: new fr/litarvan/openauth/microsoft/model/request/XSTSAuthorizationProperties
    //   11: dup
    //   12: sipush #25936
    //   15: ldc2_w 4532186673849116768
    //   18: lload_2
    //   19: lxor
    //   20: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   25: iconst_1
    //   26: anewarray java/lang/String
    //   29: dup
    //   30: iconst_0
    //   31: aload_1
    //   32: aastore
    //   33: invokespecial <init> : (Ljava/lang/String;[Ljava/lang/String;)V
    //   36: astore #4
    //   38: new fr/litarvan/openauth/microsoft/model/request/XboxLoginRequest
    //   41: dup
    //   42: aload #4
    //   44: sipush #25469
    //   47: ldc2_w 863411766324768348
    //   50: lload_2
    //   51: lxor
    //   52: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   57: sipush #19404
    //   60: ldc2_w 6781577226583869155
    //   63: lload_2
    //   64: lxor
    //   65: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   70: invokespecial <init> : (Ljava/lang/Object;Ljava/lang/String;Ljava/lang/String;)V
    //   73: astore #5
    //   75: aload_0
    //   76: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   79: sipush #25455
    //   82: ldc2_w 187565642722946679
    //   85: lload_2
    //   86: lxor
    //   87: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   92: aload #5
    //   94: ldc fr/litarvan/openauth/microsoft/model/response/XboxLoginResponse
    //   96: invokevirtual postJson : (Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   99: checkcast fr/litarvan/openauth/microsoft/model/response/XboxLoginResponse
    //   102: areturn
  }
  
  protected MinecraftLoginResponse minecraftLogin(String paramString1, String paramString2) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 55954807631596
    //   6: lxor
    //   7: lstore_3
    //   8: new fr/litarvan/openauth/microsoft/model/request/MinecraftLoginRequest
    //   11: dup
    //   12: sipush #8286
    //   15: ldc2_w 8735834476829185406
    //   18: lload_3
    //   19: lxor
    //   20: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   25: iconst_2
    //   26: anewarray java/lang/Object
    //   29: dup
    //   30: iconst_0
    //   31: aload_1
    //   32: aastore
    //   33: dup
    //   34: iconst_1
    //   35: aload_2
    //   36: aastore
    //   37: invokestatic format : (Ljava/lang/String;[Ljava/lang/Object;)Ljava/lang/String;
    //   40: invokespecial <init> : (Ljava/lang/String;)V
    //   43: astore #5
    //   45: aload_0
    //   46: getfield http : Lfr/litarvan/openauth/microsoft/HttpClient;
    //   49: sipush #28083
    //   52: ldc2_w 5467888842963507365
    //   55: lload_3
    //   56: lxor
    //   57: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   62: aload #5
    //   64: ldc fr/litarvan/openauth/microsoft/model/response/MinecraftLoginResponse
    //   66: invokevirtual postJson : (Ljava/lang/String;Ljava/lang/Object;Ljava/lang/Class;)Ljava/lang/Object;
    //   69: checkcast fr/litarvan/openauth/microsoft/model/response/MinecraftLoginResponse
    //   72: areturn
  }
  
  protected Map<String, String> getLoginParams() {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 35398341621123
    //   6: lxor
    //   7: lstore_1
    //   8: new java/util/HashMap
    //   11: dup
    //   12: invokespecial <init> : ()V
    //   15: astore_3
    //   16: aload_3
    //   17: sipush #12109
    //   20: ldc2_w 2177998744480858372
    //   23: lload_1
    //   24: lxor
    //   25: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   30: sipush #27340
    //   33: ldc2_w 4574498254046594185
    //   36: lload_1
    //   37: lxor
    //   38: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   43: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   48: pop
    //   49: aload_3
    //   50: sipush #6802
    //   53: ldc2_w 3068517036789757153
    //   56: lload_1
    //   57: lxor
    //   58: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   63: sipush #12224
    //   66: ldc2_w 8221843483137425851
    //   69: lload_1
    //   70: lxor
    //   71: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   76: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   81: pop
    //   82: aload_3
    //   83: sipush #25270
    //   86: ldc2_w 1489560961594920131
    //   89: lload_1
    //   90: lxor
    //   91: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   96: sipush #10899
    //   99: ldc2_w 6705631278252311780
    //   102: lload_1
    //   103: lxor
    //   104: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   109: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   114: pop
    //   115: aload_3
    //   116: sipush #19361
    //   119: ldc2_w 3118615224176944618
    //   122: lload_1
    //   123: lxor
    //   124: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   129: sipush #6649
    //   132: ldc2_w 4032726068135458720
    //   135: lload_1
    //   136: lxor
    //   137: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   142: invokeinterface put : (Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;
    //   147: pop
    //   148: aload_3
    //   149: areturn
  }
  
  protected AuthTokens extractTokens(String paramString) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 71900571818035
    //   6: lxor
    //   7: lstore_2
    //   8: new fr/litarvan/openauth/microsoft/AuthTokens
    //   11: dup
    //   12: aload_0
    //   13: aload_1
    //   14: sipush #17187
    //   17: ldc2_w 2767465604366696669
    //   20: lload_2
    //   21: lxor
    //   22: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   27: invokevirtual extractValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   30: aload_0
    //   31: aload_1
    //   32: sipush #12941
    //   35: ldc2_w 4752448747431326069
    //   38: lload_2
    //   39: lxor
    //   40: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   45: invokevirtual extractValue : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   48: invokespecial <init> : (Ljava/lang/String;Ljava/lang/String;)V
    //   51: areturn
  }
  
  protected String extractValue(String paramString1, String paramString2) throws MicrosoftAuthenticationException {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 127559124373302
    //   6: lxor
    //   7: lstore_3
    //   8: invokestatic E : ()Z
    //   11: aload_0
    //   12: aload_2
    //   13: sipush #14268
    //   16: ldc2_w 811260470559499131
    //   19: lload_3
    //   20: lxor
    //   21: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   26: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   31: aload_1
    //   32: invokevirtual match : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   35: astore #6
    //   37: istore #5
    //   39: aload #6
    //   41: iload #5
    //   43: ifne -> 106
    //   46: ifnonnull -> 88
    //   49: goto -> 56
    //   52: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   55: athrow
    //   56: new fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   59: dup
    //   60: aload_2
    //   61: sipush #10668
    //   64: ldc2_w 6297163098204274040
    //   67: lload_3
    //   68: lxor
    //   69: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   74: swap
    //   75: <illegal opcode> makeConcatWithConstants : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   80: invokespecial <init> : (Ljava/lang/String;)V
    //   83: athrow
    //   84: invokestatic a : (Ljava/lang/Exception;)Ljava/lang/Exception;
    //   87: athrow
    //   88: aload #6
    //   90: sipush #30720
    //   93: ldc2_w 6998908980143500483
    //   96: lload_3
    //   97: lxor
    //   98: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   103: invokestatic decode : (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   106: areturn
    //   107: astore #7
    //   109: new fr/litarvan/openauth/microsoft/MicrosoftAuthenticationException
    //   112: dup
    //   113: aload #7
    //   115: invokespecial <init> : (Ljava/io/IOException;)V
    //   118: athrow
    // Exception table:
    //   from	to	target	type
    //   39	49	52	java/io/UnsupportedEncodingException
    //   46	84	84	java/io/UnsupportedEncodingException
    //   88	106	107	java/io/UnsupportedEncodingException
  }
  
  protected String match(String paramString1, String paramString2) {
    long l = a ^ 0x34E6BE66AF24L;
    Matcher matcher = Pattern.compile(paramString1).matcher(paramString2);
    boolean bool = MicrosoftAuthenticationException.E();
    try {
      if (!bool)
        try {
          if (!matcher.find())
            return null; 
        } catch (x5 x5) {
          throw a(null);
        }  
    } catch (x5 x5) {
      throw a(null);
    } 
    return matcher.group(1);
  }
  
  private static boolean lambda$loginWithTokens$0(MinecraftStoreResponse.StoreProduct paramStoreProduct) {
    // Byte code:
    //   0: getstatic fr/litarvan/openauth/microsoft/MicrosoftAuthenticator.a : J
    //   3: ldc2_w 37792544603118
    //   6: lxor
    //   7: lstore_1
    //   8: aload_0
    //   9: invokevirtual getName : ()Ljava/lang/String;
    //   12: sipush #31182
    //   15: ldc2_w 7994383819565193723
    //   18: lload_1
    //   19: lxor
    //   20: <illegal opcode> q : (IJ)Ljava/lang/String;
    //   25: invokevirtual equals : (Ljava/lang/Object;)Z
    //   28: ireturn
  }
  
  static {
    long l = a ^ 0x4F22D67F6E44L;
    (new byte[8])[0] = (byte)(int)(l >>> 56L);
    for (byte b1 = 1; b1 < 8; b1++)
      (new byte[8])[b1] = (byte)(int)(l << b1 * 8 >>> 56L); 
    Cipher cipher;
    (cipher = Cipher.getInstance("DES/CBC/PKCS5Padding")).init(2, SecretKeyFactory.getInstance("DES").generateSecret(new DESKeySpec(new byte[8])), new IvParameterSpec(new byte[8]));
    String[] arrayOfString = new String[59];
    boolean bool = false;
    String str;
    int i = (str = "øÉ$\\N\002\b\037\021¢ßÐJ¡\0232½web;+ëP§\020® v\0069]èá#ªÐSæ|\020é%¼è,ÇI\017\017»§\003a\020g\026e¤ëæ@LmÒ\bB.\020*\016pÑÁ*,$çæ[T¯HÁªÌm½ÿç\024!\013éæÞl¬ê\025¯-?åÖg\\4Ú×ÿÿy/k^·\020Æhiän\\<\0269MÊF\025¸\032bAN8\rån6!\\\036a¨PÉ6Õµ\000\022$nÉî\004hEÛç5<Ð'U\037bú»\017ggH/\034»W¬ñ\032\003X\033\n,ëÅL(\0070d»iåÇ\t\001¨\f¸\017M÷\f¹1´`\017\t]pªÁ£'ë\017Ü¸û{[/\033ÌÎ®\002?u²A'A'&\004£\005pÅ.\003hûèÚ¹!ÛN¶²?\022Ò$aË6ÍÝ^@¥)\006IÞ#[*@±ë\021\027\003Ð`úÖ a»ÌÜx\036°\032S\037òî\032O\025¤À_Øþ5ó¨ó\033ò¼sßiî\023/\036¡TÏù¨2xõ\033í\022Ç^BìØøüqO¥ÿµ´Xâ\001\000æ³{34\nÞË¼\036\022­Nz\036 Úw¡¶ÜL8¾:2T\037Wý2J¦ Zæ\024~çÕHë\022Ãæt!ÉÈ×#öãÌ¤\006\027»ÄÃÒ.:£·âJW\000Ì9\013ès%ãÅ·o¼ö}¾D\004;\034FÓxNv#F²\030A²ôéÚê\017) ª\005ÑÓ¹uþP\036Õ[ýâZëÈ³ðø¹ä­vÚM\025C¶\031ç´ð\"ý\025\003®eucBÞ9\017\f°¾\\.37S1­yEÆ\f\000Ìß\023k1Î´[\026U¨|¶ºíÿ,\0208ÌêAÛÑû`&îö&\035\031\020Ü5Ìã\021\023Å:ýæÛ³Ùñ \004o·gÜ EI¹«\001F\033@:-6gx¬<¥^ý\022\024~L FX\032Û·k\000õ¥ÊØ¬ü\002¾$<0#o5\024ø¨üÞ_ \024·ÐmðÍ4KZÅDè\032ºî\001Å{´\fXÅC\034.-\020VÿBs§ó2D¤èG£öQ}P°î\033R°FOSÝúÈ_Ã\033®~WO]gòçò\017¤ Ìýg\r¶Aâd¢Èp_\026-Ò½Õ\001÷}÷îÌ~\002\032½§ê\022æT«ñÂÜ?&1HY8ñ¹3ØãÔÍ'£9¶BÁ!õ\026°××E.öTGqé\t½!Ð%\007nâaÜAÃçåö\b\013;u¨H\0206M:NË\034Ñz»ïÎ&b[P\027õqb,\"\tùä/-Ï9'M?\003ãMÁ,n¼Ôm{5ÑÓ¡\tMGçÏ±íòÖ1íÄ\005õîðeì¼\002à<bEË\\\031Ø\024\030Ó\r/¸[\036¹\003zs ób\003\020úEÅ¨ö¿`ÕmË!C\táaÓ§Ç¼`&SÐä9Ú\020xêY]CÎËÉè)ÃxX<`|Ô<û¤#ð®°ý\0000\002?Ã47q\006´¥Ì±Ht}\027\022íýÎ9»×u\013¥ Ê?åa³),¯ú÷º¶g\002G[ëî\ta>Ã=j\n\002)îU[`¤9Å\004`eÃ;U¿#UÙ\fòþ/N9Á?Uî\035°ÄäIZL}£2áY¨ë=@Å^\021&Õ¬dQ¢ÿý!ap`É®8þ\027½+\032ýéù \037h\031\fé\025«VVe_L\"ºï±ïA\004\003¨i.8¤e°/X.dQD\024ã\rW©0\027<\t>òY\003p\002¥Î!}X»pWRd\023Õ\b3V>ôttÆË¿Ùý÷Õ±T,eÏ!\003COzÆÙ¶é~LáÑÁ\b\030\0065]h\020¿ï³Îp5½\002w¾ÉA!1E\004n\000uÞ\007q\025ÈqìÉ\003D*­nö*è\b1ýDýEæ-øÉ½\016>¿k[\0163GíSç×ùP©é\\ýë\007Ê5|ÌñïÜ±\"ä\fHø%IÈ}Ù±ÀM\"\026ùLñÒÞãÎ,Â\021\f\t\0216óPõ\024f-î\031Aî$4Æ¢øGyAgöÝÕ4e1\n0\006lK3(¼¿Q\020û6adê-ìr\rGÇ/¥ê\037k¹ðÙ¸$úÝ@q\035ýÈ©¯tËH\"ø/¾WûÊvBXÁÙðë}\t.eßÏ?\002MZqóh4ÂD Ó\0264ñ*\033«'\033\000}.®Qþ0[Æú-.\030ñ8j!\r\025\020­ýøçjô.§±DFÖìâi\020ß\033¥\032Óð2À\017¬g\003à½×HÂÍI\b#g[6\t)t_¨)\000u&É\001ÖÅÎy46°?±µæâÆ\0031°ØE=k\025Ì¿ïïDÖ\t \024{f=\\ãeï½8`þÝ­óCJ\r_¤a¹#ìvë¹¢æ\016m+d\021U\027/u3äùk\002\\#\ts$Z\tVQ{\003ÖÇÒ0²ýøÕÈcøå²\020°þ\036Ü°ÿ\000<ç[\037}ïÛÿ9ÙVj²0¡Ú¢²SæfgÈè¢0¯\023Ú\034í¦(LâEÊ\0235+ý=½ÕÍLô23jb~[>Zº~\025\032\r\0375àDyÍã­K(ÀÌõais±%\000m\035¦Ö¾²}ºN\031\\ìpuYý5\n#í\007` µx¯4üÀ\006ûê§a]Ìù®\036tî_i¶VúÁkªPx 3\007_¡YMì1\bôù\037¦\033Ü\033ï§¬\017êuÖÍ%ø\022f\020p\024ÈÒ?Í:\027üL]R%ÛxGÅÑ\006£N?¬7x:-8\017îóÚ¬yÁ|p&WÌÜu÷Ëvð_ºöI\024\016­*ó%Ä\007ÌU\005 ú9óL\016uÎ\\Z~_Ü®X\024ë\031 ²-øì9>£vø9>ÂtÊ\005\bíðT·\001Cytm\\=h£y\030èúrc¼\b$-wV7Ý>pñM®JU\020çS{¬g\032p!³m\026\030\".$\fè(k\nùh}ºéIê\bÃ\033\036\tÄ\037Xâs`gÊ¸%Ì\003ððùºn¿Ç\016gí\007ÎèwÕ¸Pô±Wúy¦\021ùçôÁ¶¯¨9ªJKÐ\024J\006F×%@¹\f¹§O¬!{ªêX¹\004ý\017lLÜ°È0N\025<\027}[¤º¼ò?X~%ê¨VY^¹Ã^ÿ±\n'ø\002]©ÔÀõàÙHÉ¦\021Pîù2:¦)\036¹­BMÓé<R¾YÖå¦6<áÂ\013&^Ì\n/Ë6g02ËZÕ}ïpøaA\033î<ôwF+\027ûÑòAþ'P[ýåÖqTôÜU\005ë)\004úñ6µBKNä\021\027.c!ÈgïiÞqè|Òör\000Y8\006,\016Àñ°?ìù\035\026â~\020b8]f ¯Ç¿ÒOöw­]7OÕ\020»`?í®Ùv\030\fKî?å·»\003\020×ÎvÊ¯È_\001©L}Ý(Ã\rÐ+NÄ3\tÕõ\016Ñî?ÆæézÑä\033¯hÖ®÷4©\017ÈA\020ò)xû¡\030ÒòÀbCÊ: Ê7vyDÉç:³vÛø:ÛïéÀ\007;añÊÕµn#\007\017¿ß Ú¦$\006Ëù\006-\b\0207\023:ÉÔUN\016Ô\025Õ:\024w_=püOV7gh\t¾°Bõ!l:}0¢b­Q07ê\022JE÷øq+°î\036¸Ãí\031t\033DZ5fsb´_&á1¢ÚÂ©ñÀn\000Ø¼\"Ò®ÃrÆAñÉ\036}V\031Ö®]~§¿Ör,ÈýS¬ÔxX\031Ø\n\n\034\f\003mãA(¶\034íÓïò}J*{&G]ññN®¨È~uÖH\001\003©\0323î}vÏÌì¾j \027_Wì­×Æ9Ó]É\030.i1ç\026.­\024WI\037¼|=Üm»(÷\013\b-ÿ\\é&I­Ùf-híð\016?Üä\022ßnW±\030¶Ìûê¯D ö7ä\027¹ïÞ¾<±úKJ7I`å\002>\026Ý\037\tFXO\rèõw \022i;Fÿ¿39¸9ìÂ©\0167}uc¤;ÜÉã½£¹XTÉ«Ï\037\037Ë±ÅþUÝÿÐPÛC\006\013\020\004Ë¾¾\024Þé\002\034\fghçvÓQ\026Ö|¨oùëEUÇ¹8mí²>¾Ã\031\002ËD6÷5J§%©EB¿¥TÁeò$\027uã").length();
    byte b2 = 48;
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
    int i = paramInt ^ (int)(paramLong & 0x7FFFL) ^ 0x1041;
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
        throw new RuntimeException("fr/litarvan/openauth/microsoft/MicrosoftAuthenticator", exception);
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
    //   66: ldc_w 'fr/litarvan/openauth/microsoft/MicrosoftAuthenticator'
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


/* Location:              C:\Users\Administrator\Downloads\Opal 080624 ez\Opal.jar!\fr\litarvan\openauth\microsoft\MicrosoftAuthenticator.class
 * Java compiler version: 21 (65.0)
 * JD-Core Version:       1.1.3
 */