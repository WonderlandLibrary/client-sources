package net.minecraft.src;

class ThreadConnectToOnlineServer extends Thread
{
    final McoServer field_96597_a;
    final GuiSlotOnlineServerList field_96596_b;
    
    ThreadConnectToOnlineServer(final GuiSlotOnlineServerList par1GuiSlotOnlineServerList, final McoServer par2McoServer) {
        this.field_96596_b = par1GuiSlotOnlineServerList;
        this.field_96597_a = par2McoServer;
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
        //     5: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //     8: getfield        net/minecraft/src/McoServer.field_96411_l:Z
        //    11: ifne            88
        //    14: aload_0         /* this */
        //    15: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //    18: iconst_1       
        //    19: putfield        net/minecraft/src/McoServer.field_96411_l:Z
        //    22: aload_0         /* this */
        //    23: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //    26: ldc2_w          -2
        //    29: putfield        net/minecraft/src/McoServer.field_96412_m:J
        //    32: aload_0         /* this */
        //    33: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //    36: ldc             ""
        //    38: putfield        net/minecraft/src/McoServer.field_96414_k:Ljava/lang/String;
        //    41: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101014_j:()I
        //    44: pop            
        //    45: invokestatic    java/lang/System.nanoTime:()J
        //    48: lstore_2        /* var1 */
        //    49: aload_0         /* this */
        //    50: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96596_b:Lnet/minecraft/src/GuiSlotOnlineServerList;
        //    53: getfield        net/minecraft/src/GuiSlotOnlineServerList.field_96294_a:Lnet/minecraft/src/GuiScreenOnlineServers;
        //    56: aload_0         /* this */
        //    57: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //    60: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101002_a:(Lnet/minecraft/src/GuiScreenOnlineServers;Lnet/minecraft/src/McoServer;)V
        //    63: invokestatic    java/lang/System.nanoTime:()J
        //    66: lstore          var3
        //    68: aload_0         /* this */
        //    69: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //    72: lload           var3
        //    74: lload_2         /* var1 */
        //    75: lsub           
        //    76: ldc2_w          1000000
        //    79: ldiv           
        //    80: putfield        net/minecraft/src/McoServer.field_96412_m:J
        //    83: iconst_0       
        //    84: istore_1        /* var27 */
        //    85: goto            127
        //    88: aload_0         /* this */
        //    89: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //    92: getfield        net/minecraft/src/McoServer.field_102022_m:Z
        //    95: ifeq            125
        //    98: aload_0         /* this */
        //    99: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //   102: iconst_0       
        //   103: putfield        net/minecraft/src/McoServer.field_102022_m:Z
        //   106: aload_0         /* this */
        //   107: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96596_b:Lnet/minecraft/src/GuiSlotOnlineServerList;
        //   110: getfield        net/minecraft/src/GuiSlotOnlineServerList.field_96294_a:Lnet/minecraft/src/GuiScreenOnlineServers;
        //   113: aload_0         /* this */
        //   114: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //   117: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101002_a:(Lnet/minecraft/src/GuiScreenOnlineServers;Lnet/minecraft/src/McoServer;)V
        //   120: iconst_0       
        //   121: istore_1        /* var27 */
        //   122: goto            127
        //   125: iconst_0       
        //   126: istore_1        /* var27 */
        //   127: iload_1         /* var27 */
        //   128: ifeq            452
        //   131: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   134: dup            
        //   135: astore          7
        //   137: monitorenter   
        //   138: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   141: pop            
        //   142: aload           7
        //   144: monitorexit    
        //   145: goto            452
        //   148: aload           7
        //   150: monitorexit    
        //   151: athrow         
        //   152: astore_2        /* var35 */
        //   153: aload_0         /* this */
        //   154: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //   157: ldc2_w          -1
        //   160: putfield        net/minecraft/src/McoServer.field_96412_m:J
        //   163: iconst_0       
        //   164: istore_1        /* var27 */
        //   165: iload_1         /* var27 */
        //   166: ifeq            372
        //   169: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   172: dup            
        //   173: astore          7
        //   175: monitorenter   
        //   176: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   179: pop            
        //   180: aload           7
        //   182: monitorexit    
        //   183: goto            372
        //   186: aload           7
        //   188: monitorexit    
        //   189: athrow         
        //   190: astore_2        /* var36 */
        //   191: aload_0         /* this */
        //   192: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //   195: ldc2_w          -1
        //   198: putfield        net/minecraft/src/McoServer.field_96412_m:J
        //   201: iconst_0       
        //   202: istore_1        /* var27 */
        //   203: iload_1         /* var27 */
        //   204: ifeq            388
        //   207: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   210: dup            
        //   211: astore          7
        //   213: monitorenter   
        //   214: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   217: pop            
        //   218: aload           7
        //   220: monitorexit    
        //   221: goto            388
        //   224: aload           7
        //   226: monitorexit    
        //   227: athrow         
        //   228: astore_2        /* var36 */
        //   229: aload_0         /* this */
        //   230: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //   233: ldc2_w          -1
        //   236: putfield        net/minecraft/src/McoServer.field_96412_m:J
        //   239: iconst_0       
        //   240: istore_1        /* var27 */
        //   241: iload_1         /* var27 */
        //   242: ifeq            404
        //   245: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   248: dup            
        //   249: astore          7
        //   251: monitorenter   
        //   252: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   255: pop            
        //   256: aload           7
        //   258: monitorexit    
        //   259: goto            404
        //   262: aload           7
        //   264: monitorexit    
        //   265: athrow         
        //   266: astore_2        /* var37 */
        //   267: aload_0         /* this */
        //   268: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //   271: ldc2_w          -1
        //   274: putfield        net/minecraft/src/McoServer.field_96412_m:J
        //   277: iconst_0       
        //   278: istore_1        /* var27 */
        //   279: iload_1         /* var27 */
        //   280: ifeq            420
        //   283: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   286: dup            
        //   287: astore          7
        //   289: monitorenter   
        //   290: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   293: pop            
        //   294: aload           7
        //   296: monitorexit    
        //   297: goto            420
        //   300: aload           7
        //   302: monitorexit    
        //   303: athrow         
        //   304: astore_2        /* var38 */
        //   305: aload_0         /* this */
        //   306: getfield        net/minecraft/src/ThreadConnectToOnlineServer.field_96597_a:Lnet/minecraft/src/McoServer;
        //   309: ldc2_w          -1
        //   312: putfield        net/minecraft/src/McoServer.field_96412_m:J
        //   315: iconst_0       
        //   316: istore_1        /* var27 */
        //   317: iload_1         /* var27 */
        //   318: ifeq            436
        //   321: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   324: dup            
        //   325: astore          7
        //   327: monitorenter   
        //   328: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   331: pop            
        //   332: aload           7
        //   334: monitorexit    
        //   335: goto            436
        //   338: aload           7
        //   340: monitorexit    
        //   341: athrow         
        //   342: astore          6
        //   344: iload_1         /* var27 */
        //   345: ifeq            369
        //   348: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   351: dup            
        //   352: astore          7
        //   354: monitorenter   
        //   355: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   358: pop            
        //   359: aload           7
        //   361: monitorexit    
        //   362: goto            369
        //   365: aload           7
        //   367: monitorexit    
        //   368: athrow         
        //   369: aload           6
        //   371: athrow         
        //   372: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   375: dup            
        //   376: astore_2       
        //   377: monitorenter   
        //   378: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   381: pop            
        //   382: aload_2        
        //   383: monitorexit    
        //   384: return         
        //   385: aload_2        
        //   386: monitorexit    
        //   387: athrow         
        //   388: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   391: dup            
        //   392: astore_2       
        //   393: monitorenter   
        //   394: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   397: pop            
        //   398: aload_2        
        //   399: monitorexit    
        //   400: return         
        //   401: aload_2        
        //   402: monitorexit    
        //   403: athrow         
        //   404: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   407: dup            
        //   408: astore_2       
        //   409: monitorenter   
        //   410: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   413: pop            
        //   414: aload_2        
        //   415: monitorexit    
        //   416: return         
        //   417: aload_2        
        //   418: monitorexit    
        //   419: athrow         
        //   420: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   423: dup            
        //   424: astore_2       
        //   425: monitorenter   
        //   426: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   429: pop            
        //   430: aload_2        
        //   431: monitorexit    
        //   432: return         
        //   433: aload_2        
        //   434: monitorexit    
        //   435: athrow         
        //   436: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   439: dup            
        //   440: astore_2       
        //   441: monitorenter   
        //   442: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   445: pop            
        //   446: aload_2        
        //   447: monitorexit    
        //   448: return         
        //   449: aload_2        
        //   450: monitorexit    
        //   451: athrow         
        //   452: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101007_h:()Ljava/lang/Object;
        //   455: dup            
        //   456: astore_2       
        //   457: monitorenter   
        //   458: invokestatic    net/minecraft/src/GuiScreenOnlineServers.func_101013_k:()I
        //   461: pop            
        //   462: aload_2        
        //   463: monitorexit    
        //   464: goto            470
        //   467: aload_2        
        //   468: monitorexit    
        //   469: athrow         
        //   470: return         
        //    Exceptions:
        //  Try           Handler
        //  Start  End    Start  End    Type                             
        //  -----  -----  -----  -----  ---------------------------------
        //  138    145    148    152    Any
        //  148    151    148    152    Any
        //  2      127    152    388    Ljava/net/UnknownHostException;
        //  176    183    186    190    Any
        //  186    189    186    190    Any
        //  2      127    190    404    Ljava/net/SocketTimeoutException;
        //  214    221    224    228    Any
        //  224    227    224    228    Any
        //  2      127    228    420    Ljava/net/ConnectException;
        //  252    259    262    266    Any
        //  262    265    262    266    Any
        //  2      127    266    436    Ljava/io/IOException;
        //  290    297    300    304    Any
        //  300    303    300    304    Any
        //  2      127    304    452    Ljava/lang/Exception;
        //  328    335    338    342    Any
        //  338    341    338    342    Any
        //  2      127    342    372    Any
        //  152    165    342    372    Any
        //  190    203    342    372    Any
        //  228    241    342    372    Any
        //  266    279    342    372    Any
        //  304    317    342    372    Any
        //  355    362    365    369    Any
        //  365    368    365    369    Any
        //  378    384    385    388    Any
        //  385    387    385    388    Any
        //  394    400    401    404    Any
        //  401    403    401    404    Any
        //  410    416    417    420    Any
        //  417    419    417    420    Any
        //  426    432    433    436    Any
        //  433    435    433    436    Any
        //  442    448    449    452    Any
        //  449    451    449    452    Any
        //  458    464    467    470    Any
        //  467    469    467    470    Any
        // 
        // The error that occurred was:
        // 
        // java.lang.IndexOutOfBoundsException: Index: 271, Size: 271
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
