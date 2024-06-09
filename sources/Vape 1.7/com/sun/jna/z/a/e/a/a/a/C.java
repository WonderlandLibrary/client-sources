package com.sun.jna.z.a.e.a.a.a;

import com.sun.jna.z.a.*;

class C extends Thread
{
    final n a;
    private static final String b;
    
    C(final n a) {
        this.a = a;
    }
    
    @Override
    public void run() {
        final int a = g.c ? 1 : 0;
        try {
            Thread.sleep(2000L);
            this.a.b.b(C.b);
            int a2 = 0;
            while (!h.d.e) {
                try {
                    if (a2 < 240) {
                        ++a2;
                        Thread.sleep(250L);
                        if (a == 0) {
                            continue;
                        }
                    }
                }
                catch (InterruptedException ex) {
                    throw ex;
                }
                break;
            }
            this.a.b.b(C.b);
        }
        catch (InterruptedException a3) {
            a3.printStackTrace();
        }
    }
    
    static {
        final char[] charArray = "Y¬\u0098\u008f\u00e3·\u00e9~¡\u009f\u0082¤\u0097".toCharArray();
        int length;
        int n2;
        final int n = n2 = (length = charArray.length);
        int n3 = 0;
        while (true) {
            Label_0126: {
                if (n > 1) {
                    break Label_0126;
                }
                length = (n2 = n3);
                do {
                    final char c = charArray[n2];
                    char c2 = '\0';
                    switch (n3 % 7) {
                        case 0: {
                            c2 = '\n';
                            break;
                        }
                        case 1: {
                            c2 = '\u00d5';
                            break;
                        }
                        case 2: {
                            c2 = '\u00f6';
                            break;
                        }
                        case 3: {
                            c2 = '\u00ec';
                            break;
                        }
                        case 4: {
                            c2 = '\u00c3';
                            break;
                        }
                        case 5: {
                            c2 = '\u00e4';
                            break;
                        }
                        default: {
                            c2 = '\u008c';
                            break;
                        }
                    }
                    charArray[length] = (char)(c ^ c2);
                    ++n3;
                } while (n == 0);
            }
            if (n <= n3) {
                b = new String(charArray).intern();
                return;
            }
            continue;
        }
    }
}
