package com.sun.jna.z.a.c;

import cpw.mods.fml.common.eventhandler.*;

public class f implements IEventListener
{
    public void invoke(final Event a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: checkcast       Lcpw/mods/fml/common/gameevent/TickEvent$PlayerTickEvent;
        //     4: astore_3       
        //     5: getstatic       com/sun/jna/z/a/c/b.b:Z
        //     8: istore_2        /* a */
        //     9: aload_3         /* a */
        //    10: getfield        cpw/mods/fml/common/gameevent/TickEvent$PlayerTickEvent.phase:Lcpw/mods/fml/common/gameevent/TickEvent$Phase;
        //    13: getstatic       cpw/mods/fml/common/gameevent/TickEvent$Phase.START:Lcpw/mods/fml/common/gameevent/TickEvent$Phase;
        //    16: if_acmpeq       20
        //    19: return         
        //    20: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //    23: getfield        com/sun/jna/z/a/h.a:Lcom/sun/jna/z/a/d/e;
        //    26: getfield        com/sun/jna/z/a/d/e.a:Ljava/util/Map;
        //    29: invokeinterface java/util/Map.values:()Ljava/util/Collection;
        //    34: invokeinterface java/util/Collection.iterator:()Ljava/util/Iterator;
        //    39: astore          4
        //    41: aload           4
        //    43: invokeinterface java/util/Iterator.hasNext:()Z
        //    48: ifeq            85
        //    51: aload           4
        //    53: invokeinterface java/util/Iterator.next:()Ljava/lang/Object;
        //    58: checkcast       Lcom/sun/jna/z/a/d/c;
        //    61: astore          a
        //    63: aload           a
        //    65: iload_2         /* a */
        //    66: ifne            77
        //    69: invokevirtual   com/sun/jna/z/a/d/c.c:()Z
        //    72: ifeq            81
        //    75: aload           a
        //    77: aload_3         /* a */
        //    78: invokevirtual   com/sun/jna/z/a/d/c.a:(Lcpw/mods/fml/common/gameevent/TickEvent$PlayerTickEvent;)V
        //    81: iload_2         /* a */
        //    82: ifeq            41
        //    85: return         
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
