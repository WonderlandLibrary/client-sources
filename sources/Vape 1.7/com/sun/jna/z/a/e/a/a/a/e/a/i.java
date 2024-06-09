package com.sun.jna.z.a.e.a.a.a.e.a;

import com.sun.jna.z.a.e.a.a.a.b.*;
import com.sun.jna.z.a.f.*;
import com.sun.jna.z.a.e.a.a.a.e.*;
import net.minecraft.client.gui.*;

public class i extends c
{
    private final a b;
    private final a c;
    private final a d;
    private final a e;
    public static boolean f;
    private static final String g;
    
    public i() {
        this.b = new a(new d(i.g, 22.0f));
        this.c = new a(new d(i.g, 16.0f));
        this.d = new a(new d(i.g, 15.0f));
        this.e = new a(new d(i.g, 14.0f));
        this.a(new com.sun.jna.z.a.e.a.a.a.e.a.d(this));
        this.a(new f(this));
        this.a(new com.sun.jna.z.a.e.a.a.a.e.a.e(this));
        this.a(new com.sun.jna.z.a.e.a.a.a.e.a.a(this));
        this.a(new com.sun.jna.z.a.e.a.a.a.e.a.b(this));
        this.a(new com.sun.jna.z.a.e.a.a.a.e.a.c(this));
        this.a(new h(this));
        this.a(new g(this));
    }
    
    public a a() {
        return this.b;
    }
    
    public FontRenderer b() {
        return this.c;
    }
    
    public FontRenderer c() {
        return this.d;
    }
    
    public FontRenderer d() {
        return this.e;
    }
    
    static {
        final char[] charArray = "a\u0080\u00e2\u0011Q§\u0013".toCharArray();
        int length;
        int n2;
        final int n = n2 = (length = charArray.length);
        int n3 = 0;
        while (true) {
            Label_0123: {
                if (n > 1) {
                    break Label_0123;
                }
                length = (n2 = n3);
                do {
                    final char c = charArray[n2];
                    char c2 = '\0';
                    switch (n3 % 7) {
                        case 0: {
                            c2 = '\"';
                            break;
                        }
                        case 1: {
                            c2 = '\u00e1';
                            break;
                        }
                        case 2: {
                            c2 = '\u008e';
                            break;
                        }
                        case 3: {
                            c2 = 'x';
                            break;
                        }
                        case 4: {
                            c2 = '3';
                            break;
                        }
                        case 5: {
                            c2 = '\u00d5';
                            break;
                        }
                        default: {
                            c2 = 'z';
                            break;
                        }
                    }
                    charArray[length] = (char)(c ^ c2);
                    ++n3;
                } while (n == 0);
            }
            if (n <= n3) {
                g = new String(charArray).intern();
                return;
            }
            continue;
        }
    }
}
