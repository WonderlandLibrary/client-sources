package ClassSub;

import java.util.*;

public class Class30
{
    
    
    public static String doGet(final String p0) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: nop            
        //     4: athrow         
        //     5: aconst_null    
        //     6: astore_1       
        //     7: aconst_null    
        //     8: astore_2       
        //     9: ldc             ""
        //    11: astore_3       
        //    12: invokestatic    org/apache/http/impl/client/HttpClients.createDefault:()Lorg/apache/http/impl/client/CloseableHttpClient;
        //    15: astore_1       
        //    16: new             Lorg/apache/http/client/methods/HttpGet;
        //    19: dup            
        //    20: aload_0        
        //    21: invokespecial   org/apache/http/client/methods/HttpGet.<init>:(Ljava/lang/String;)V
        //    24: astore          4
        //    26: invokestatic    org/apache/http/client/config/RequestConfig.custom:()Lorg/apache/http/client/config/RequestConfig$Builder;
        //    29: ldc             35000
        //    31: invokevirtual   org/apache/http/client/config/RequestConfig$Builder.setConnectTimeout:(I)Lorg/apache/http/client/config/RequestConfig$Builder;
        //    34: ldc             35000
        //    36: invokevirtual   org/apache/http/client/config/RequestConfig$Builder.setConnectionRequestTimeout:(I)Lorg/apache/http/client/config/RequestConfig$Builder;
        //    39: ldc             60000
        //    41: invokevirtual   org/apache/http/client/config/RequestConfig$Builder.setSocketTimeout:(I)Lorg/apache/http/client/config/RequestConfig$Builder;
        //    44: invokevirtual   org/apache/http/client/config/RequestConfig$Builder.build:()Lorg/apache/http/client/config/RequestConfig;
        //    47: astore          5
        //    49: aload           4
        //    51: aload           5
        //    53: invokevirtual   org/apache/http/client/methods/HttpGet.setConfig:(Lorg/apache/http/client/config/RequestConfig;)V
        //    56: aload           4
        //    58: ldc             "User-Agent"
        //    60: ldc             "Mozilla/5.0 AppIeWebKit"
        //    62: invokevirtual   org/apache/http/client/methods/HttpGet.setHeader:(Ljava/lang/String;Ljava/lang/String;)V
        //    65: aload_1        
        //    66: aload           4
        //    68: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.execute:(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/client/methods/CloseableHttpResponse;
        //    71: astore_2       
        //    72: aload_2        
        //    73: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.getEntity:()Lorg/apache/http/HttpEntity;
        //    78: astore          6
        //    80: aload           6
        //    82: invokestatic    org/apache/http/util/EntityUtils.toString:(Lorg/apache/http/HttpEntity;)Ljava/lang/String;
        //    85: astore_3       
        //    86: aconst_null    
        //    87: aload_2        
        //    88: if_acmpeq       109
        //    91: aload_2        
        //    92: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.close:()V
        //    97: goto            109
        //   100: nop            
        //   101: athrow         
        //   102: astore          4
        //   104: aload           4
        //   106: invokevirtual   java/io/IOException.printStackTrace:()V
        //   109: aconst_null    
        //   110: aload_1        
        //   111: if_acmpeq       301
        //   114: aload_1        
        //   115: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.close:()V
        //   118: goto            301
        //   121: nop            
        //   122: athrow         
        //   123: astore          4
        //   125: aload           4
        //   127: invokevirtual   java/io/IOException.printStackTrace:()V
        //   130: goto            301
        //   133: nop            
        //   134: athrow         
        //   135: astore          4
        //   137: aload           4
        //   139: invokevirtual   org/apache/http/client/ClientProtocolException.printStackTrace:()V
        //   142: aconst_null    
        //   143: aload_2        
        //   144: if_acmpeq       165
        //   147: aload_2        
        //   148: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.close:()V
        //   153: goto            165
        //   156: nop            
        //   157: athrow         
        //   158: astore          4
        //   160: aload           4
        //   162: invokevirtual   java/io/IOException.printStackTrace:()V
        //   165: aconst_null    
        //   166: aload_1        
        //   167: if_acmpeq       301
        //   170: aload_1        
        //   171: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.close:()V
        //   174: goto            301
        //   177: nop            
        //   178: athrow         
        //   179: astore          4
        //   181: aload           4
        //   183: invokevirtual   java/io/IOException.printStackTrace:()V
        //   186: goto            301
        //   189: nop            
        //   190: athrow         
        //   191: astore          4
        //   193: aload           4
        //   195: invokevirtual   java/io/IOException.printStackTrace:()V
        //   198: aconst_null    
        //   199: aload_2        
        //   200: if_acmpeq       221
        //   203: aload_2        
        //   204: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.close:()V
        //   209: goto            221
        //   212: nop            
        //   213: athrow         
        //   214: astore          4
        //   216: aload           4
        //   218: invokevirtual   java/io/IOException.printStackTrace:()V
        //   221: aconst_null    
        //   222: aload_1        
        //   223: if_acmpeq       301
        //   226: aload_1        
        //   227: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.close:()V
        //   230: goto            301
        //   233: nop            
        //   234: athrow         
        //   235: astore          4
        //   237: aload           4
        //   239: invokevirtual   java/io/IOException.printStackTrace:()V
        //   242: goto            301
        //   245: nop            
        //   246: athrow         
        //   247: astore          7
        //   249: aconst_null    
        //   250: aload_2        
        //   251: if_acmpeq       272
        //   254: aload_2        
        //   255: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.close:()V
        //   260: goto            272
        //   263: nop            
        //   264: athrow         
        //   265: astore          8
        //   267: aload           8
        //   269: invokevirtual   java/io/IOException.printStackTrace:()V
        //   272: aconst_null    
        //   273: aload_1        
        //   274: if_acmpeq       293
        //   277: aload_1        
        //   278: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.close:()V
        //   281: goto            293
        //   284: nop            
        //   285: athrow         
        //   286: astore          8
        //   288: aload           8
        //   290: invokevirtual   java/io/IOException.printStackTrace:()V
        //   293: aload           7
        //   295: athrow         
        //   296: nop            
        //   297: nop            
        //   298: nop            
        //   299: nop            
        //   300: athrow         
        //   301: aload_3        
        //   302: areturn        
        //   303: nop            
        //   304: nop            
        //   305: nop            
        //   306: nop            
        //   307: athrow         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                            
        //  -----  -----  -----  -----  ------------------------------------------------
        //  91     97     102    109    Ljava/io/IOException;
        //  114    118    123    133    Ljava/io/IOException;
        //  12     86     135    177    Lorg/apache/http/client/ClientProtocolException;
        //  147    153    158    165    Ljava/io/IOException;
        //  170    174    179    189    Ljava/io/IOException;
        //  12     86     191    233    Ljava/io/IOException;
        //  203    209    214    221    Ljava/io/IOException;
        //  226    230    235    245    Ljava/io/IOException;
        //  12     86     247    296    Any
        //  135    142    247    296    Any
        //  191    198    247    296    Any
        //  254    260    265    272    Ljava/io/IOException;
        //  277    281    286    293    Ljava/io/IOException;
        //  247    249    247    296    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 126, Size: 126
        //     at java.util.ArrayList.rangeCheck(Unknown Source)
        //     at java.util.ArrayList.get(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3321)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3435)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveForgeJarDecompiled(FileSaver.java:228)
        //     at us.deathmarine.luyten.FileSaver.lambda$saveAllForgeDir$0(FileSaver.java:142)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    public static String doPost(final String p0, final Map<String, Object> p1) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: nop            
        //     4: athrow         
        //     5: aconst_null    
        //     6: astore_2       
        //     7: aconst_null    
        //     8: astore_3       
        //     9: ldc             ""
        //    11: astore          4
        //    13: invokestatic    org/apache/http/impl/client/HttpClients.createDefault:()Lorg/apache/http/impl/client/CloseableHttpClient;
        //    16: astore_2       
        //    17: new             Lorg/apache/http/client/methods/HttpPost;
        //    20: dup            
        //    21: aload_0        
        //    22: invokespecial   org/apache/http/client/methods/HttpPost.<init>:(Ljava/lang/String;)V
        //    25: astore          5
        //    27: aload           5
        //    29: ldc             "User-Agent"
        //    31: ldc             "Mozilla/5.0 AppIeWebKit"
        //    33: invokevirtual   org/apache/http/client/methods/HttpPost.setHeader:(Ljava/lang/String;Ljava/lang/String;)V
        //    36: invokestatic    org/apache/http/client/config/RequestConfig.custom:()Lorg/apache/http/client/config/RequestConfig$Builder;
        //    39: ldc             35000
        //    41: invokevirtual   org/apache/http/client/config/RequestConfig$Builder.setConnectTimeout:(I)Lorg/apache/http/client/config/RequestConfig$Builder;
        //    44: ldc             35000
        //    46: invokevirtual   org/apache/http/client/config/RequestConfig$Builder.setConnectionRequestTimeout:(I)Lorg/apache/http/client/config/RequestConfig$Builder;
        //    49: ldc             60000
        //    51: invokevirtual   org/apache/http/client/config/RequestConfig$Builder.setSocketTimeout:(I)Lorg/apache/http/client/config/RequestConfig$Builder;
        //    54: invokevirtual   org/apache/http/client/config/RequestConfig$Builder.build:()Lorg/apache/http/client/config/RequestConfig;
        //    57: astore          6
        //    59: aload           5
        //    61: aload           6
        //    63: invokevirtual   org/apache/http/client/methods/HttpPost.setConfig:(Lorg/apache/http/client/config/RequestConfig;)V
        //    66: aconst_null    
        //    67: aload_1        
        //    68: if_acmpeq       201
        //    71: aload_1        
        //    72: invokeinterface java/util/Map.size:()I
        //    77: ifle            201
        //    80: new             Ljava/util/ArrayList;
        //    83: dup            
        //    84: invokespecial   java/util/ArrayList.<init>:()V
        //    87: astore          7
        //    89: aload_1        
        //    90: invokeinterface java/util/Map.entrySet:()Ljava/util/Set;
        //    95: astore          8
        //    97: aload           8
        //    99: invokeinterface java/util/Set.iterator:()Ljava/util/Iterator;
        //   104: astore          9
        //   106: aload           9
        //   108: invokeinterface java/util/Iterator.hasNext:()Z
        //   113: ifeq            173
        //   116: aload           9
        //   118: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   123: checkcast       Ljava/util/Map$Entry;
        //   126: astore          10
        //   128: aload           7
        //   130: new             Lorg/apache/http/message/BasicNameValuePair;
        //   133: dup            
        //   134: aload           10
        //   136: invokeinterface java/util/Map$Entry.getKey:()Ljava/lang/Object;
        //   141: checkcast       Ljava/lang/String;
        //   144: aload           10
        //   146: invokeinterface java/util/Map$Entry.getValue:()Ljava/lang/Object;
        //   151: invokevirtual   java/lang/Object.toString:()Ljava/lang/String;
        //   154: invokespecial   org/apache/http/message/BasicNameValuePair.<init>:(Ljava/lang/String;Ljava/lang/String;)V
        //   157: invokeinterface java/util/List.add:(Ljava/lang/Object;)Z
        //   162: pop            
        //   163: goto            106
        //   166: nop            
        //   167: nop            
        //   168: nop            
        //   169: nop            
        //   170: nop            
        //   171: nop            
        //   172: athrow         
        //   173: aload           5
        //   175: new             Lorg/apache/http/client/entity/UrlEncodedFormEntity;
        //   178: dup            
        //   179: aload           7
        //   181: ldc             "UTF-8"
        //   183: invokespecial   org/apache/http/client/entity/UrlEncodedFormEntity.<init>:(Ljava/util/List;Ljava/lang/String;)V
        //   186: invokevirtual   org/apache/http/client/methods/HttpPost.setEntity:(Lorg/apache/http/HttpEntity;)V
        //   189: goto            201
        //   192: nop            
        //   193: athrow         
        //   194: astore          10
        //   196: aload           10
        //   198: invokevirtual   java/io/UnsupportedEncodingException.printStackTrace:()V
        //   201: aload_2        
        //   202: aload           5
        //   204: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.execute:(Lorg/apache/http/client/methods/HttpUriRequest;)Lorg/apache/http/client/methods/CloseableHttpResponse;
        //   207: astore_3       
        //   208: aload_3        
        //   209: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.getEntity:()Lorg/apache/http/HttpEntity;
        //   214: astore          7
        //   216: aload           7
        //   218: invokestatic    org/apache/http/util/EntityUtils.toString:(Lorg/apache/http/HttpEntity;)Ljava/lang/String;
        //   221: astore          4
        //   223: aconst_null    
        //   224: aload_3        
        //   225: if_acmpeq       246
        //   228: aload_3        
        //   229: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.close:()V
        //   234: goto            246
        //   237: nop            
        //   238: athrow         
        //   239: astore          7
        //   241: aload           7
        //   243: invokevirtual   java/io/IOException.printStackTrace:()V
        //   246: aconst_null    
        //   247: aload_2        
        //   248: if_acmpeq       438
        //   251: aload_2        
        //   252: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.close:()V
        //   255: goto            438
        //   258: nop            
        //   259: athrow         
        //   260: astore          7
        //   262: aload           7
        //   264: invokevirtual   java/io/IOException.printStackTrace:()V
        //   267: goto            438
        //   270: nop            
        //   271: athrow         
        //   272: astore          7
        //   274: aload           7
        //   276: invokevirtual   org/apache/http/client/ClientProtocolException.printStackTrace:()V
        //   279: aconst_null    
        //   280: aload_3        
        //   281: if_acmpeq       302
        //   284: aload_3        
        //   285: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.close:()V
        //   290: goto            302
        //   293: nop            
        //   294: athrow         
        //   295: astore          7
        //   297: aload           7
        //   299: invokevirtual   java/io/IOException.printStackTrace:()V
        //   302: aconst_null    
        //   303: aload_2        
        //   304: if_acmpeq       438
        //   307: aload_2        
        //   308: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.close:()V
        //   311: goto            438
        //   314: nop            
        //   315: athrow         
        //   316: astore          7
        //   318: aload           7
        //   320: invokevirtual   java/io/IOException.printStackTrace:()V
        //   323: goto            438
        //   326: nop            
        //   327: athrow         
        //   328: astore          7
        //   330: aload           7
        //   332: invokevirtual   java/io/IOException.printStackTrace:()V
        //   335: aconst_null    
        //   336: aload_3        
        //   337: if_acmpeq       358
        //   340: aload_3        
        //   341: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.close:()V
        //   346: goto            358
        //   349: nop            
        //   350: athrow         
        //   351: astore          7
        //   353: aload           7
        //   355: invokevirtual   java/io/IOException.printStackTrace:()V
        //   358: aconst_null    
        //   359: aload_2        
        //   360: if_acmpeq       438
        //   363: aload_2        
        //   364: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.close:()V
        //   367: goto            438
        //   370: nop            
        //   371: athrow         
        //   372: astore          7
        //   374: aload           7
        //   376: invokevirtual   java/io/IOException.printStackTrace:()V
        //   379: goto            438
        //   382: nop            
        //   383: athrow         
        //   384: astore          11
        //   386: aconst_null    
        //   387: aload_3        
        //   388: if_acmpeq       409
        //   391: aload_3        
        //   392: invokeinterface org/apache/http/client/methods/CloseableHttpResponse.close:()V
        //   397: goto            409
        //   400: nop            
        //   401: athrow         
        //   402: astore          12
        //   404: aload           12
        //   406: invokevirtual   java/io/IOException.printStackTrace:()V
        //   409: aconst_null    
        //   410: aload_2        
        //   411: if_acmpeq       430
        //   414: aload_2        
        //   415: invokevirtual   org/apache/http/impl/client/CloseableHttpClient.close:()V
        //   418: goto            430
        //   421: nop            
        //   422: athrow         
        //   423: astore          12
        //   425: aload           12
        //   427: invokevirtual   java/io/IOException.printStackTrace:()V
        //   430: aload           11
        //   432: athrow         
        //   433: nop            
        //   434: nop            
        //   435: nop            
        //   436: nop            
        //   437: athrow         
        //   438: aload           4
        //   440: areturn        
        //   441: nop            
        //   442: nop            
        //   443: nop            
        //   444: nop            
        //   445: athrow         
        //    Signature:
        //  (Ljava/lang/String;Ljava/util/Map<Ljava/lang/String;Ljava/lang/Object;>;)Ljava/lang/String;
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                                            
        //  -----  -----  -----  -----  ------------------------------------------------
        //  173    189    194    201    Ljava/io/UnsupportedEncodingException;
        //  228    234    239    246    Ljava/io/IOException;
        //  251    255    260    270    Ljava/io/IOException;
        //  201    223    272    314    Lorg/apache/http/client/ClientProtocolException;
        //  284    290    295    302    Ljava/io/IOException;
        //  307    311    316    326    Ljava/io/IOException;
        //  201    223    328    370    Ljava/io/IOException;
        //  340    346    351    358    Ljava/io/IOException;
        //  363    367    372    382    Ljava/io/IOException;
        //  201    223    384    433    Any
        //  272    279    384    433    Any
        //  328    335    384    433    Any
        //  391    397    402    409    Ljava/io/IOException;
        //  414    418    423    430    Ljava/io/IOException;
        //  384    386    384    433    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 173, Size: 173
        //     at java.util.ArrayList.rangeCheck(Unknown Source)
        //     at java.util.ArrayList.get(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3321)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3435)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:113)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:210)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:99)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:757)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:655)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:532)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:499)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:141)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:130)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:105)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at us.deathmarine.luyten.FileSaver.doSaveForgeJarDecompiled(FileSaver.java:228)
        //     at us.deathmarine.luyten.FileSaver.lambda$saveAllForgeDir$0(FileSaver.java:142)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
