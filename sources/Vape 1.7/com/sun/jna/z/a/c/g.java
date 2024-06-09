package com.sun.jna.z.a.c;

import cpw.mods.fml.common.eventhandler.*;

public class g implements IEventListener
{
    public void invoke(final Event a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_1         /* a */
        //     4: checkcast       Lnet/minecraftforge/client/event/RenderHandEvent;
        //     7: astore          4
        //     9: istore_2        /* a */
        //    10: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //    13: getfield        com/sun/jna/z/a/h.a:Lcom/sun/jna/z/a/d/e;
        //    16: getfield        com/sun/jna/z/a/d/e.a:Ljava/util/Map;
        //    19: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //    24: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    29: astore          5
        //    31: aload           5
        //    33: invokeinterface java/util/Iterator.hasNext:()Z
        //    38: ifeq            90
        //    41: aload           5
        //    43: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    48: checkcast       Lcom/sun/jna/z/a/d/c;
        //    51: astore          a
        //    53: aload           a
        //    55: iload_2         /* a */
        //    56: ifne            67
        //    59: invokevirtual   com/sun/jna/z/a/d/c.c:()Z
        //    62: ifeq            75
        //    65: aload           a
        //    67: aload           a
        //    69: getfield        net/minecraftforge/client/event/RenderHandEvent.partialTicks:F
        //    72: invokevirtual   com/sun/jna/z/a/d/c.a:(F)V
        //    75: iload_2         /* a */
        //    76: ifeq            31
        //    79: getstatic       com/sun/jna/z/a/i.g:I
        //    82: istore_3        /* a */
        //    83: iinc            a, 1
        //    86: iload_3         /* a */
        //    87: putstatic       com/sun/jna/z/a/i.g:I
        //    90: invokestatic    net/minecraft/client/Minecraft.func_71410_x:()Lnet/minecraft/client/Minecraft;
        //    93: astore          a
        //    95: aload           a
        //    97: getfield        net/minecraft/client/Minecraft.field_71474_y:Lnet/minecraft/client/settings/GameSettings;
        //   100: getfield        net/minecraft/client/settings/GameSettings.field_74336_f:Z
        //   103: istore          a
        //   105: aload           a
        //   107: getfield        net/minecraft/client/Minecraft.field_71474_y:Lnet/minecraft/client/settings/GameSettings;
        //   110: iconst_0       
        //   111: putfield        net/minecraft/client/settings/GameSettings.field_74336_f:Z
        //   114: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //   117: getfield        com/sun/jna/z/a/h.j:Lcom/sun/jna/z/a/f/k;
        //   120: aload           a
        //   122: getfield        net/minecraftforge/client/event/RenderHandEvent.partialTicks:F
        //   125: aload           a
        //   127: getfield        net/minecraftforge/client/event/RenderHandEvent.renderPass:I
        //   130: invokevirtual   com/sun/jna/z/a/f/k.a:(FI)V
        //   133: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //   136: getfield        com/sun/jna/z/a/h.a:Lcom/sun/jna/z/a/d/e;
        //   139: getfield        com/sun/jna/z/a/d/e.a:Ljava/util/Map;
        //   142: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //   147: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //   152: astore          7
        //   154: aload           7
        //   156: invokeinterface java/util/Iterator.hasNext:()Z
        //   161: ifeq            206
        //   164: aload           7
        //   166: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //   171: checkcast       Lcom/sun/jna/z/a/d/c;
        //   174: astore          a
        //   176: iload_2         /* a */
        //   177: ifne            216
        //   180: aload           a
        //   182: iload_2         /* a */
        //   183: ifne            194
        //   186: invokevirtual   com/sun/jna/z/a/d/c.c:()Z
        //   189: ifeq            202
        //   192: aload           a
        //   194: aload           a
        //   196: getfield        net/minecraftforge/client/event/RenderHandEvent.partialTicks:F
        //   199: invokevirtual   com/sun/jna/z/a/d/c.b:(F)V
        //   202: iload_2         /* a */
        //   203: ifeq            154
        //   206: aload           a
        //   208: getfield        net/minecraft/client/Minecraft.field_71474_y:Lnet/minecraft/client/settings/GameSettings;
        //   211: iload           a
        //   213: putfield        net/minecraft/client/settings/GameSettings.field_74336_f:Z
        //   216: return         
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2895)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2445)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
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
        //     at us.deathmarine.luyten.FileSaver.doSaveJarDecompiled(FileSaver.java:191)
        //     at us.deathmarine.luyten.FileSaver.access$300(FileSaver.java:46)
        //     at us.deathmarine.luyten.FileSaver$4.run(FileSaver.java:112)
        //     at java.lang.Thread.run(Unknown Source)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
}
