package com.sun.jna.z.a.e.a.a.a;

import com.sun.jna.z.a.e.a.a.a.a.a.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

class x extends g
{
    final com.sun.jna.z.a.e.a.a.a.k o;
    private static final String p;
    
    x(final com.sun.jna.z.a.e.a.a.a.k a, final String a) {
        this.o = a;
        super(a);
    }
    
    @Override
    public void c() {
        final int a = MathHelper.func_76128_c(Minecraft.func_71410_x().field_71439_g.field_70165_t);
        this.b(x.p + String.valueOf(a));
    }
    
    static {
        final char[] charArray = "D\u0015\u00de".toCharArray();
        int length;
        int n2;
        final int n = n2 = (length = charArray.length);
        int n3 = 0;
        while (true) {
            Label_0121: {
                if (n > 1) {
                    break Label_0121;
                }
                length = (n2 = n3);
                do {
                    final char c = charArray[n2];
                    char c2 = '\0';
                    switch (n3 % 7) {
                        case 0: {
                            c2 = '\u001c';
                            break;
                        }
                        case 1: {
                            c2 = '/';
                            break;
                        }
                        case 2: {
                            c2 = '\u00fe';
                            break;
                        }
                        case 3: {
                            c2 = 'X';
                            break;
                        }
                        case 4: {
                            c2 = ' ';
                            break;
                        }
                        case 5: {
                            c2 = '|';
                            break;
                        }
                        default: {
                            c2 = 't';
                            break;
                        }
                    }
                    charArray[length] = (char)(c ^ c2);
                    ++n3;
                } while (n == 0);
            }
            if (n <= n3) {
                p = new String(charArray).intern();
                return;
            }
            continue;
        }
    }
}
