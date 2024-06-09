package com.sun.jna.z.a.b;

public class b
{
    private static final char[] a;
    private static int[] b;
    private static final String[] c;
    
    public static String a(final byte[] a) {
        // 
        // This method could not be decompiled.
        // 
        // Original Bytecode:
        // 
        //     3: aload_0         /* a */
        //     4: arraylength    
        //     5: istore_2       
        //     6: istore_1        /* a */
        //     7: iload_2         /* a */
        //     8: iconst_2       
        //     9: iadd           
        //    10: iconst_3       
        //    11: idiv           
        //    12: iconst_4       
        //    13: imul           
        //    14: newarray        C
        //    16: astore_3        /* a */
        //    17: iconst_0       
        //    18: istore          a
        //    20: iconst_0       
        //    21: istore          a
        //    23: iload           a
        //    25: iload_2         /* a */
        //    26: if_icmpge       185
        //    29: aload_0         /* a */
        //    30: iload           a
        //    32: iinc            a, 1
        //    35: baload         
        //    36: istore          a
        //    38: iload           a
        //    40: iload_1         /* a */
        //    41: ifne            59
        //    44: iload_2         /* a */
        //    45: iload_1         /* a */
        //    46: ifne            187
        //    49: if_icmpge       62
        //    52: aload_0         /* a */
        //    53: iload           a
        //    55: iinc            a, 1
        //    58: baload         
        //    59: goto            63
        //    62: iconst_0       
        //    63: istore          a
        //    65: iload           a
        //    67: iload_1         /* a */
        //    68: ifne            82
        //    71: iload_2         /* a */
        //    72: if_icmpge       85
        //    75: aload_0         /* a */
        //    76: iload           a
        //    78: iinc            a, 1
        //    81: baload         
        //    82: goto            86
        //    85: iconst_0       
        //    86: istore          a
        //    88: bipush          63
        //    90: istore          a
        //    92: aload_3         /* a */
        //    93: iload           a
        //    95: iinc            a, 1
        //    98: getstatic       com/sun/jna/z/a/b/b.a:[C
        //   101: iload           a
        //   103: iconst_2       
        //   104: ishr           
        //   105: iload           a
        //   107: iand           
        //   108: caload         
        //   109: castore        
        //   110: aload_3         /* a */
        //   111: iload           a
        //   113: iinc            a, 1
        //   116: getstatic       com/sun/jna/z/a/b/b.a:[C
        //   119: iload           a
        //   121: iconst_4       
        //   122: ishl           
        //   123: iload           a
        //   125: sipush          255
        //   128: iand           
        //   129: iconst_4       
        //   130: ishr           
        //   131: ior            
        //   132: iload           a
        //   134: iand           
        //   135: caload         
        //   136: castore        
        //   137: aload_3         /* a */
        //   138: iload           a
        //   140: iinc            a, 1
        //   143: getstatic       com/sun/jna/z/a/b/b.a:[C
        //   146: iload           a
        //   148: iconst_2       
        //   149: ishl           
        //   150: iload           a
        //   152: sipush          255
        //   155: iand           
        //   156: bipush          6
        //   158: ishr           
        //   159: ior            
        //   160: iload           a
        //   162: iand           
        //   163: caload         
        //   164: castore        
        //   165: aload_3         /* a */
        //   166: iload           a
        //   168: iinc            a, 1
        //   171: getstatic       com/sun/jna/z/a/b/b.a:[C
        //   174: iload           a
        //   176: iload           a
        //   178: iand           
        //   179: caload         
        //   180: castore        
        //   181: iload_1         /* a */
        //   182: ifeq            23
        //   185: iload_2         /* a */
        //   186: iconst_3       
        //   187: irem           
        //   188: lookupswitch {
        //                1: 216
        //                2: 225
        //          default: 234
        //        }
        //   216: aload_3         /* a */
        //   217: iinc            a, -1
        //   220: iload           a
        //   222: bipush          61
        //   224: castore        
        //   225: aload_3         /* a */
        //   226: iinc            a, -1
        //   229: iload           a
        //   231: bipush          61
        //   233: castore        
        //   234: new             Ljava/lang/String;
        //   237: dup            
        //   238: aload_3         /* a */
        //   239: invokespecial   java/lang/String.<init>:([C)V
        //   242: areturn        
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
    
    public static byte[] a(final String a) {
        final int a2 = e.e;
        int endsWith;
        final boolean b = (endsWith = (a.endsWith(com.sun.jna.z.a.b.b.c[1]) ? 1 : 0)) != 0;
        Label_0042: {
            if (a2 == 0) {
                if (b) {
                    endsWith = 2;
                    break Label_0042;
                }
                final boolean endsWith2;
                endsWith = ((endsWith2 = a.endsWith("=")) ? 1 : 0);
            }
            if (a2 == 0) {
                if (b) {
                    endsWith = 1;
                }
                else {
                    endsWith = 0;
                }
            }
        }
        final int a3 = endsWith;
        final byte[] a4 = new byte[a.length() * 3 / 4 - a3];
        final int a5 = 255;
        int a6 = 0;
        int a7 = 0;
        byte[] array = null;
        while (a7 < a.length()) {
            final int a8 = com.sun.jna.z.a.b.b.b[a.charAt(a7)];
            final int a9 = com.sun.jna.z.a.b.b.b[a.charAt(a7 + 1)];
            array = a4;
            if (a2 != 0) {
                return array;
            }
            array[a6++] = (byte)((a8 << 2 | a9 >> 4) & a5);
            final int n = a6;
            if (a2 == 0) {
                if (n >= a4.length) {
                    return a4;
                }
                final int n2 = com.sun.jna.z.a.b.b.b[a.charAt(a7 + 2)];
            }
            final int a10 = n;
            a4[a6++] = (byte)((a9 << 4 | a10 >> 2) & a5);
            final int n3 = a6;
            if (a2 == 0) {
                if (n3 >= a4.length) {
                    return a4;
                }
                final int n4 = com.sun.jna.z.a.b.b.b[a.charAt(a7 + 3)];
            }
            final int a11 = n3;
            a4[a6++] = (byte)((a10 << 6 | a11) & a5);
            a7 += 4;
            if (a2 != 0) {
                break;
            }
        }
        return array;
    }
    
    static {
        final String[] a2 = new String[2];
        int a3 = 0;
        final String a5;
        final int a4 = (a5 = "\u001d\u00e7\u0082\u00cc\u00fd\u001c\u00f4\u0014\u00ec\u008b\u00c3\u00f4\u0017\u00fd\u0013\u00f5\u0090\u00da\u00eb\u000e\u00e6\n\u00f2\u0099\u00d1\u00e2;\u00d1?\u00c1¤\u00ee\u00df2\u00da6\u00ce\u00ad\u00e5\u00d65\u00c3-\u00d7²\u00fc\u00cd,\u00c4$\u00dc»¸\u0089h\u0080h\u0090\u00f7¿\u0080c\u0098s\u0002a\u0098").length();
        int a6 = 64;
        int a7 = -1;
        Label_0022: {
            break Label_0022;
            do {
                a6 = a5.charAt(a7);
                ++a7;
                final String s = a5;
                final int n = a7;
                final char[] charArray = s.substring(n, n + a6).toCharArray();
                int length;
                int n3;
                final int n2 = n3 = (length = charArray.length);
                int a8 = 0;
                while (true) {
                    Label_0189: {
                        if (n2 > 1) {
                            break Label_0189;
                        }
                        length = (n3 = a8);
                        do {
                            final char c2 = charArray[n3];
                            char c3 = '\0';
                            switch (a8 % 7) {
                                case 0: {
                                    c3 = '\\';
                                    break;
                                }
                                case 1: {
                                    c3 = '¥';
                                    break;
                                }
                                case 2: {
                                    c3 = '\u00c1';
                                    break;
                                }
                                case 3: {
                                    c3 = '\u0088';
                                    break;
                                }
                                case 4: {
                                    c3 = '¸';
                                    break;
                                }
                                case 5: {
                                    c3 = 'Z';
                                    break;
                                }
                                default: {
                                    c3 = '³';
                                    break;
                                }
                            }
                            charArray[length] = (char)(c2 ^ c3);
                            ++a8;
                        } while (n2 == 0);
                    }
                    if (n2 > a8) {
                        continue;
                    }
                    break;
                }
                a2[a3++] = new String(charArray).intern();
            } while ((a7 += a6) < a4);
        }
        c = a2;
        a = com.sun.jna.z.a.b.b.c[0].toCharArray();
        com.sun.jna.z.a.b.b.b = new int[128];
        for (int a9 = 0; a9 < com.sun.jna.z.a.b.b.a.length; ++a9) {
            com.sun.jna.z.a.b.b.b[com.sun.jna.z.a.b.b.a[a9]] = a9;
        }
    }
}
