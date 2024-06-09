package com.sun.jna.z.a.e.a.a.a.e.a;

import com.sun.jna.z.a.e.a.a.a.e.*;
import com.sun.jna.z.a.e.a.a.a.a.*;
import org.lwjgl.opengl.*;
import com.sun.jna.z.a.e.a.a.a.f.*;
import java.awt.*;

public class f extends b<p>
{
    private final i e;
    
    f(final i a) {
        super(p.class);
        this.e = a;
        this.b = Color.WHITE;
        this.c = new Color(128, 128, 128, 128);
    }
    
    protected void a(final p a) {
        final int a2 = i.f ? 1 : 0;
        p p = a;
        if (a2 == 0) {
            if (a.k() != null) {
                return;
            }
            p = a;
        }
        final Rectangle a3 = p.j();
        this.a(a, false);
        GL11.glEnable(3042);
        GL11.glDisable(3553);
        GL11.glDisable(2884);
        GL11.glBlendFunc(770, 771);
        a.a(a.l());
        GL11.glBegin(7);
        GL11.glVertex2d(0.0, 0.0);
        GL11.glVertex2d((double)a3.width, 0.0);
        GL11.glVertex2d((double)a3.width, (double)a3.height);
        GL11.glVertex2d(0.0, (double)a3.height);
        GL11.glEnd();
        GL11.glEnable(2884);
        GL11.glEnable(3553);
        GL11.glDisable(3042);
        this.a(a, true);
    }
    
    protected Dimension b(final p a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_1         /* a */
        //     4: invokeinterface com/sun/jna/z/a/e/a/a/a/a/p.b:()[Lcom/sun/jna/z/a/e/a/a/a/a/k;
        //     9: astore_3        /* a */
        //    10: aload_3         /* a */
        //    11: arraylength    
        //    12: anewarray       Ljava/awt/Rectangle;
        //    15: astore          a
        //    17: aload_3         /* a */
        //    18: arraylength    
        //    19: anewarray       [Lcom/sun/jna/z/a/e/a/a/a/c/d;
        //    22: astore          a
        //    24: iconst_0       
        //    25: istore          6
        //    27: istore_2        /* a */
        //    28: iload           a
        //    30: aload_3         /* a */
        //    31: arraylength    
        //    32: if_icmpge       112
        //    35: aload_3         /* a */
        //    36: iload           a
        //    38: aaload         
        //    39: astore          a
        //    41: aload           a
        //    43: invokeinterface com/sun/jna/z/a/e/a/a/a/a/k.a:()Lcom/sun/jna/z/a/e/a/a/a/e/e;
        //    48: aload           a
        //    50: invokeinterface com/sun/jna/z/a/e/a/a/a/e/e.a:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Lcom/sun/jna/z/a/e/a/a/a/e/d;
        //    55: aload           a
        //    57: invokeinterface com/sun/jna/z/a/e/a/a/a/e/d.b:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)Ljava/awt/Dimension;
        //    62: iload_2         /* a */
        //    63: ifne            127
        //    66: astore          a
        //    68: aload           a
        //    70: iload           a
        //    72: new             Ljava/awt/Rectangle;
        //    75: dup            
        //    76: iconst_0       
        //    77: iconst_0       
        //    78: aload           a
        //    80: getfield        java/awt/Dimension.width:I
        //    83: aload           a
        //    85: getfield        java/awt/Dimension.height:I
        //    88: invokespecial   java/awt/Rectangle.<init>:(IIII)V
        //    91: aastore        
        //    92: aload           a
        //    94: iload           a
        //    96: aload_1         /* a */
        //    97: aload           a
        //    99: invokeinterface com/sun/jna/z/a/e/a/a/a/a/p.a:(Lcom/sun/jna/z/a/e/a/a/a/a/k;)[Lcom/sun/jna/z/a/e/a/a/a/c/d;
        //   104: aastore        
        //   105: iinc            a, 1
        //   108: iload_2         /* a */
        //   109: ifeq            28
        //   112: aload_1         /* a */
        //   113: invokeinterface com/sun/jna/z/a/e/a/a/a/a/p.a:()Lcom/sun/jna/z/a/e/a/a/a/c/g;
        //   118: aload           a
        //   120: aload           a
        //   122: invokeinterface com/sun/jna/z/a/e/a/a/a/c/g.a:([Ljava/awt/Rectangle;[[Lcom/sun/jna/z/a/e/a/a/a/c/d;)Ljava/awt/Dimension;
        //   127: areturn        
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
