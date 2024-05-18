package net.minecraft.src;

class ThreadPollServers extends Thread
{
    final ServerData pollServersServerData;
    final GuiSlotServer serverSlotContainer;
    
    ThreadPollServers(final GuiSlotServer par1GuiSlotServer, final ServerData par2ServerData) {
        this.serverSlotContainer = par1GuiSlotServer;
        this.pollServersServerData = par2ServerData;
    }
    
    @Override
    public void run() {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: istore_1        /* var27 */
        //     2: iconst_1       
        //     3: istore_1        /* var27 */
        //     4: aload_0         /* this */
        //     5: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //     8: new             Ljava/lang/StringBuilder;
        //    11: dup            
        //    12: invokespecial   java/lang/StringBuilder.<init>:()V
        //    15: getstatic       net/minecraft/src/EnumChatFormatting.DARK_GRAY:Lnet/minecraft/src/EnumChatFormatting;
        //    18: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //    21: ldc             "Polling.."
        //    23: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //    26: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //    29: putfield        net/minecraft/src/ServerData.serverMOTD:Ljava/lang/String;
        //    32: invokestatic    java/lang/System.nanoTime:()J
        //    35: lstore_2        /* var1 */
        //    36: aload_0         /* this */
        //    37: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //    40: invokestatic    net/minecraft/src/GuiMultiplayer.func_82291_a:(Lnet/minecraft/src/ServerData;)V
        //    43: invokestatic    java/lang/System.nanoTime:()J
        //    46: lstore          var3
        //    48: aload_0         /* this */
        //    49: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //    52: lload           var3
        //    54: lload_2         /* var1 */
        //    55: lsub           
        //    56: ldc2_w          1000000
        //    59: ldiv           
        //    60: putfield        net/minecraft/src/ServerData.pingToServer:J
        //    63: iconst_0       
        //    64: istore_1        /* var27 */
        //    65: iload_1         /* var27 */
        //    66: ifeq            528
        //    69: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //    72: dup            
        //    73: astore          7
        //    75: monitorenter   
        //    76: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //    79: pop            
        //    80: aload           7
        //    82: monitorexit    
        //    83: goto            528
        //    86: aload           7
        //    88: monitorexit    
        //    89: athrow         
        //    90: astore_2        /* var1 */
        //    91: aload_0         /* this */
        //    92: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //    95: ldc2_w          -1
        //    98: putfield        net/minecraft/src/ServerData.pingToServer:J
        //   101: aload_0         /* this */
        //   102: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //   105: new             Ljava/lang/StringBuilder;
        //   108: dup            
        //   109: invokespecial   java/lang/StringBuilder.<init>:()V
        //   112: getstatic       net/minecraft/src/EnumChatFormatting.DARK_RED:Lnet/minecraft/src/EnumChatFormatting;
        //   115: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   118: ldc             "Can't resolve hostname"
        //   120: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   123: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   126: putfield        net/minecraft/src/ServerData.serverMOTD:Ljava/lang/String;
        //   129: iconst_0       
        //   130: istore_1        /* var27 */
        //   131: iload_1         /* var27 */
        //   132: ifeq            512
        //   135: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   138: dup            
        //   139: astore          7
        //   141: monitorenter   
        //   142: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   145: pop            
        //   146: aload           7
        //   148: monitorexit    
        //   149: goto            512
        //   152: aload           7
        //   154: monitorexit    
        //   155: athrow         
        //   156: astore_2        /* var35 */
        //   157: aload_0         /* this */
        //   158: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //   161: ldc2_w          -1
        //   164: putfield        net/minecraft/src/ServerData.pingToServer:J
        //   167: aload_0         /* this */
        //   168: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //   171: new             Ljava/lang/StringBuilder;
        //   174: dup            
        //   175: invokespecial   java/lang/StringBuilder.<init>:()V
        //   178: getstatic       net/minecraft/src/EnumChatFormatting.DARK_RED:Lnet/minecraft/src/EnumChatFormatting;
        //   181: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   184: ldc             "Can't reach server"
        //   186: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   189: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   192: putfield        net/minecraft/src/ServerData.serverMOTD:Ljava/lang/String;
        //   195: iconst_0       
        //   196: istore_1        /* var27 */
        //   197: iload_1         /* var27 */
        //   198: ifeq            448
        //   201: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   204: dup            
        //   205: astore          7
        //   207: monitorenter   
        //   208: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   211: pop            
        //   212: aload           7
        //   214: monitorexit    
        //   215: goto            448
        //   218: aload           7
        //   220: monitorexit    
        //   221: athrow         
        //   222: astore_2        /* var37 */
        //   223: aload_0         /* this */
        //   224: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //   227: ldc2_w          -1
        //   230: putfield        net/minecraft/src/ServerData.pingToServer:J
        //   233: aload_0         /* this */
        //   234: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //   237: new             Ljava/lang/StringBuilder;
        //   240: dup            
        //   241: invokespecial   java/lang/StringBuilder.<init>:()V
        //   244: getstatic       net/minecraft/src/EnumChatFormatting.DARK_RED:Lnet/minecraft/src/EnumChatFormatting;
        //   247: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   250: ldc             "Can't reach server"
        //   252: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   255: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   258: putfield        net/minecraft/src/ServerData.serverMOTD:Ljava/lang/String;
        //   261: iconst_0       
        //   262: istore_1        /* var27 */
        //   263: iload_1         /* var27 */
        //   264: ifeq            464
        //   267: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   270: dup            
        //   271: astore          7
        //   273: monitorenter   
        //   274: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   277: pop            
        //   278: aload           7
        //   280: monitorexit    
        //   281: goto            464
        //   284: aload           7
        //   286: monitorexit    
        //   287: athrow         
        //   288: astore_2        /* var37 */
        //   289: aload_0         /* this */
        //   290: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //   293: ldc2_w          -1
        //   296: putfield        net/minecraft/src/ServerData.pingToServer:J
        //   299: aload_0         /* this */
        //   300: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //   303: new             Ljava/lang/StringBuilder;
        //   306: dup            
        //   307: invokespecial   java/lang/StringBuilder.<init>:()V
        //   310: getstatic       net/minecraft/src/EnumChatFormatting.DARK_RED:Lnet/minecraft/src/EnumChatFormatting;
        //   313: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   316: ldc             "Communication error"
        //   318: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   321: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   324: putfield        net/minecraft/src/ServerData.serverMOTD:Ljava/lang/String;
        //   327: iconst_0       
        //   328: istore_1        /* var27 */
        //   329: iload_1         /* var27 */
        //   330: ifeq            480
        //   333: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   336: dup            
        //   337: astore          7
        //   339: monitorenter   
        //   340: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   343: pop            
        //   344: aload           7
        //   346: monitorexit    
        //   347: goto            480
        //   350: aload           7
        //   352: monitorexit    
        //   353: athrow         
        //   354: astore_2        /* var38 */
        //   355: aload_0         /* this */
        //   356: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //   359: ldc2_w          -1
        //   362: putfield        net/minecraft/src/ServerData.pingToServer:J
        //   365: aload_0         /* this */
        //   366: getfield        net/minecraft/src/ThreadPollServers.pollServersServerData:Lnet/minecraft/src/ServerData;
        //   369: new             Ljava/lang/StringBuilder;
        //   372: dup            
        //   373: ldc             "ERROR: "
        //   375: invokespecial   java/lang/StringBuilder.<init>:(Ljava/lang/String;)V
        //   378: aload_2         /* var39 */
        //   379: invokevirtual   java/lang/Object.getClass:()Ljava/lang/Class;
        //   382: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/Object;)Ljava/lang/StringBuilder;
        //   385: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   388: putfield        net/minecraft/src/ServerData.serverMOTD:Ljava/lang/String;
        //   391: iconst_0       
        //   392: istore_1        /* var27 */
        //   393: iload_1         /* var27 */
        //   394: ifeq            496
        //   397: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   400: dup            
        //   401: astore          7
        //   403: monitorenter   
        //   404: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   407: pop            
        //   408: aload           7
        //   410: monitorexit    
        //   411: goto            496
        //   414: aload           7
        //   416: monitorexit    
        //   417: athrow         
        //   418: astore          6
        //   420: iload_1         /* var27 */
        //   421: ifeq            445
        //   424: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   427: dup            
        //   428: astore          7
        //   430: monitorenter   
        //   431: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   434: pop            
        //   435: aload           7
        //   437: monitorexit    
        //   438: goto            445
        //   441: aload           7
        //   443: monitorexit    
        //   444: athrow         
        //   445: aload           6
        //   447: athrow         
        //   448: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   451: dup            
        //   452: astore_2       
        //   453: monitorenter   
        //   454: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   457: pop            
        //   458: aload_2        
        //   459: monitorexit    
        //   460: return         
        //   461: aload_2        
        //   462: monitorexit    
        //   463: athrow         
        //   464: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   467: dup            
        //   468: astore_2       
        //   469: monitorenter   
        //   470: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   473: pop            
        //   474: aload_2        
        //   475: monitorexit    
        //   476: return         
        //   477: aload_2        
        //   478: monitorexit    
        //   479: athrow         
        //   480: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   483: dup            
        //   484: astore_2       
        //   485: monitorenter   
        //   486: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   489: pop            
        //   490: aload_2        
        //   491: monitorexit    
        //   492: return         
        //   493: aload_2        
        //   494: monitorexit    
        //   495: athrow         
        //   496: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   499: dup            
        //   500: astore_2       
        //   501: monitorenter   
        //   502: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   505: pop            
        //   506: aload_2        
        //   507: monitorexit    
        //   508: return         
        //   509: aload_2        
        //   510: monitorexit    
        //   511: athrow         
        //   512: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   515: dup            
        //   516: astore_2       
        //   517: monitorenter   
        //   518: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   521: pop            
        //   522: aload_2        
        //   523: monitorexit    
        //   524: return         
        //   525: aload_2        
        //   526: monitorexit    
        //   527: athrow         
        //   528: invokestatic    net/minecraft/src/GuiMultiplayer.getLock:()Ljava/lang/Object;
        //   531: dup            
        //   532: astore_2       
        //   533: monitorenter   
        //   534: invokestatic    net/minecraft/src/GuiMultiplayer.decreaseThreadsPending:()I
        //   537: pop            
        //   538: aload_2        
        //   539: monitorexit    
        //   540: goto            546
        //   543: aload_2        
        //   544: monitorexit    
        //   545: athrow         
        //   546: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                             
        //  -----  -----  -----  -----  ---------------------------------
        //  76     83     86     90     Any
        //  86     89     86     90     Any
        //  2      65     90     528    Ljava/net/UnknownHostException;
        //  142    149    152    156    Any
        //  152    155    152    156    Any
        //  2      65     156    464    Ljava/net/SocketTimeoutException;
        //  208    215    218    222    Any
        //  218    221    218    222    Any
        //  2      65     222    480    Ljava/net/ConnectException;
        //  274    281    284    288    Any
        //  284    287    284    288    Any
        //  2      65     288    496    Ljava/io/IOException;
        //  340    347    350    354    Any
        //  350    353    350    354    Any
        //  2      65     354    512    Ljava/lang/Exception;
        //  404    411    414    418    Any
        //  414    417    414    418    Any
        //  2      65     418    448    Any
        //  90     131    418    448    Any
        //  156    197    418    448    Any
        //  222    263    418    448    Any
        //  288    329    418    448    Any
        //  354    393    418    448    Any
        //  431    438    441    445    Any
        //  441    444    441    445    Any
        //  454    460    461    464    Any
        //  461    463    461    464    Any
        //  470    476    477    480    Any
        //  477    479    477    480    Any
        //  486    492    493    496    Any
        //  493    495    493    496    Any
        //  502    508    509    512    Any
        //  509    511    509    512    Any
        //  518    524    525    528    Any
        //  525    527    525    528    Any
        //  534    540    543    546    Any
        //  543    545    543    546    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 296, Size: 296
        //     at java.util.ArrayList.rangeCheck(Unknown Source)
        //     at java.util.ArrayList.get(Unknown Source)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3321)
        //     at com.strobel.decompiler.ast.AstBuilder.convertToAst(AstBuilder.java:3569)
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
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:198)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:51)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:118)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
