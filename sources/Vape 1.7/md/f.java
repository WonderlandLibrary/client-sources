package md;

import com.sun.jna.z.a.d.*;
import org.lwjgl.opengl.*;
import net.minecraft.client.entity.*;
import net.minecraft.entity.*;
import java.util.*;

public class f extends c
{
    public static int n;
    public static boolean o;
    public static int p;
    public static int q;
    public static int r;
    public static boolean s;
    public static boolean t;
    public static int u;
    public static boolean v;
    public static boolean w;
    public static int x;
    public static boolean y;
    public static boolean z;
    public static int A;
    public static boolean B;
    public static int C;
    public static boolean D;
    public static boolean E;
    public static boolean F;
    private static final String G;
    
    public f() {
        super(f.G, com.sun.jna.z.a.d.b.Render, -12274966);
    }
    
    @Override
    public void b(final float a) {
        GL11.glPushMatrix();
        final int a2 = f.F ? 1 : 0;
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        this.b().field_71460_t.func_78483_a(0.0);
        while (true) {
            for (final Object a3 : this.b().field_71441_e.field_72996_f) {
                Object o2;
                final Object o = o2 = a3;
                Label_0174: {
                    if (a2 == 0) {
                        final int n = (o instanceof EntityOtherPlayerMP) ? 1 : 0;
                        if (a2 != 0) {
                            GL11.glEnable(n);
                            GL11.glPopMatrix();
                            if (f.n != 0) {
                                f.F = (a2 == 0);
                            }
                            return;
                        }
                        if (n == 0) {
                            break Label_0174;
                        }
                        final Object o3;
                        o2 = (o3 = a3);
                    }
                    if (a2 == 0) {
                        if (o == this.b().field_71439_g) {
                            break Label_0174;
                        }
                        o2 = a3;
                    }
                    final EntityOtherPlayerMP a4 = (EntityOtherPlayerMP)o2;
                    final boolean d = this.d((Entity)a4);
                    f f = null;
                    EntityOtherPlayerMP a6 = null;
                    Label_0163: {
                        if (a2 == 0) {
                            if (d) {
                                this.a((Entity)a4, 0.0f, 1.0f, 0.0f, 1.2f, a);
                                if (a2 == 0) {
                                    break Label_0174;
                                }
                            }
                            f = this;
                            final EntityOtherPlayerMP a5 = a6 = a4;
                            if (a2 != 0) {
                                break Label_0163;
                            }
                            this.c((Entity)a5);
                        }
                        if (d) {
                            this.a((Entity)a4, 0.0f, 0.0f, 1.0f, 1.2f, a);
                            if (a2 == 0) {
                                break Label_0174;
                            }
                        }
                        f = this;
                        a6 = a4;
                    }
                    f.a((Entity)a6, 0.27f, 0.7f, 0.92f, 1.0f, a);
                }
                if (a2 != 0) {
                    break;
                }
            }
            GL11.glColor3d(1.0, 1.0, 1.0);
            this.b().field_71460_t.func_78463_b(0.0);
            continue;
        }
    }
    
    void a(final Entity a, final float a, final float a, final float a, final float a, final float a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     4: aload_1         /* a */
        //     5: getfield        net/minecraft/entity/Entity.field_70165_t:D
        //     8: aload_1         /* a */
        //     9: getfield        net/minecraft/entity/Entity.field_70142_S:D
        //    12: dsub           
        //    13: fload           a
        //    15: f2d            
        //    16: dmul           
        //    17: dadd           
        //    18: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //    21: getfield        com/sun/jna/z/a/h.i:Lcom/sun/jna/z/a/f/i;
        //    24: invokevirtual   com/sun/jna/z/a/f/i.a:()D
        //    27: dsub           
        //    28: dstore          a
        //    30: aload_1         /* a */
        //    31: getfield        net/minecraft/entity/Entity.field_70137_T:D
        //    34: aload_1         /* a */
        //    35: getfield        net/minecraft/entity/Entity.field_70163_u:D
        //    38: aload_1         /* a */
        //    39: getfield        net/minecraft/entity/Entity.field_70137_T:D
        //    42: dsub           
        //    43: fload           a
        //    45: f2d            
        //    46: dmul           
        //    47: dadd           
        //    48: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //    51: getfield        com/sun/jna/z/a/h.i:Lcom/sun/jna/z/a/f/i;
        //    54: invokevirtual   com/sun/jna/z/a/f/i.b:()D
        //    57: dsub           
        //    58: dstore          a
        //    60: aload_1         /* a */
        //    61: getfield        net/minecraft/entity/Entity.field_70136_U:D
        //    64: aload_1         /* a */
        //    65: getfield        net/minecraft/entity/Entity.field_70161_v:D
        //    68: aload_1         /* a */
        //    69: getfield        net/minecraft/entity/Entity.field_70136_U:D
        //    72: dsub           
        //    73: fload           a
        //    75: f2d            
        //    76: dmul           
        //    77: dadd           
        //    78: getstatic       com/sun/jna/z/a/h.d:Lcom/sun/jna/z/a/h;
        //    81: getfield        com/sun/jna/z/a/h.i:Lcom/sun/jna/z/a/f/i;
        //    84: invokevirtual   com/sun/jna/z/a/f/i.c:()D
        //    87: dsub           
        //    88: dstore          13
        //    90: getstatic       md/f.F:Z
        //    93: sipush          770
        //    96: sipush          771
        //    99: invokestatic    org/lwjgl/opengl/GL11.glBlendFunc:(II)V
        //   102: sipush          3042
        //   105: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   108: sipush          770
        //   111: sipush          771
        //   114: invokestatic    org/lwjgl/opengl/GL11.glBlendFunc:(II)V
        //   117: fload_2         /* a */
        //   118: fload_3         /* a */
        //   119: fload           a
        //   121: fconst_1       
        //   122: invokestatic    org/lwjgl/opengl/GL11.glColor4f:(FFFF)V
        //   125: sipush          2896
        //   128: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   131: sipush          2848
        //   134: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   137: fload           a
        //   139: invokestatic    org/lwjgl/opengl/GL11.glLineWidth:(F)V
        //   142: istore          a
        //   144: sipush          3553
        //   147: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   150: iconst_1       
        //   151: invokestatic    org/lwjgl/opengl/GL11.glBegin:(I)V
        //   154: dconst_0       
        //   155: dconst_0       
        //   156: dconst_0       
        //   157: invokestatic    org/lwjgl/opengl/GL11.glVertex3d:(DDD)V
        //   160: dload           a
        //   162: dload           a
        //   164: aload_1         /* a */
        //   165: invokevirtual   net/minecraft/entity/Entity.func_70047_e:()F
        //   168: f2d            
        //   169: dadd           
        //   170: dload           a
        //   172: invokestatic    org/lwjgl/opengl/GL11.glVertex3d:(DDD)V
        //   175: invokestatic    org/lwjgl/opengl/GL11.glEnd:()V
        //   178: sipush          3042
        //   181: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   184: sipush          3553
        //   187: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   190: sipush          2848
        //   193: invokestatic    org/lwjgl/opengl/GL11.glDisable:(I)V
        //   196: sipush          2896
        //   199: invokestatic    org/lwjgl/opengl/GL11.glEnable:(I)V
        //   202: iload           a
        //   204: ifeq            220
        //   207: getstatic       md/f.n:I
        //   210: istore          a
        //   212: iinc            a, 1
        //   215: iload           a
        //   217: putstatic       md/f.n:I
        //   220: return         
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
    
    static {
        final char[] charArray = "\f\u0012\u008c\u000ftv\b".toCharArray();
        int length;
        int n2;
        final int n = n2 = (length = charArray.length);
        int n3 = 0;
        while (true) {
            Label_0120: {
                if (n > 1) {
                    break Label_0120;
                }
                length = (n2 = n3);
                do {
                    final char c = charArray[n2];
                    char c2 = '\0';
                    switch (n3 % 7) {
                        case 0: {
                            c2 = 'X';
                            break;
                        }
                        case 1: {
                            c2 = '`';
                            break;
                        }
                        case 2: {
                            c2 = '\u00ed';
                            break;
                        }
                        case 3: {
                            c2 = 'l';
                            break;
                        }
                        case 4: {
                            c2 = '\u0011';
                            break;
                        }
                        case 5: {
                            c2 = '\u0004';
                            break;
                        }
                        default: {
                            c2 = '{';
                            break;
                        }
                    }
                    charArray[length] = (char)(c ^ c2);
                    ++n3;
                } while (n == 0);
            }
            if (n <= n3) {
                G = new String(charArray).intern();
                return;
            }
            continue;
        }
    }
}
