package com.sun.jna.z.a.e.a.a.a.c;

import com.sun.jna.z.a.*;
import java.awt.*;

public class c implements g
{
    @Override
    public void a(final Rectangle a, final Rectangle[] a, final d[][] a) {
        final int c = e.c ? 1 : 0;
        int a2 = 0;
        final int a3 = c;
        final int a4 = a.length;
        int a5 = 0;
        while (a5 < a4) {
            final Rectangle a6 = a[a5];
            Rectangle rectangle2 = null;
            Label_0067: {
                Label_0047: {
                    Rectangle rectangle;
                    try {
                        rectangle = (rectangle2 = a6);
                        if (a3 != 0) {
                            break Label_0067;
                        }
                        if (rectangle == null) {
                            break Label_0047;
                        }
                        break Label_0047;
                    }
                    catch (NullPointerException ex) {
                        throw ex;
                    }
                    try {
                        if (rectangle == null) {
                            throw new NullPointerException();
                        }
                    }
                    catch (NullPointerException ex2) {
                        throw ex2;
                    }
                }
                a6.x = a.x;
                rectangle2 = a6;
            }
            rectangle2.y = a.y + a2;
            a2 += a6.height;
            ++a5;
            if (a3 != 0) {
                break;
            }
        }
        boolean c2 = false;
        Label_0116: {
            Label_0110: {
                try {
                    if (i.g == 0) {
                        return;
                    }
                    final int n = a3;
                    if (n != 0) {
                        break Label_0110;
                    }
                    break Label_0110;
                }
                catch (NullPointerException ex3) {
                    throw ex3;
                }
                try {
                    final int n = a3;
                    if (n != 0) {
                        c2 = false;
                        break Label_0116;
                    }
                }
                catch (NullPointerException ex4) {
                    throw ex4;
                }
            }
            c2 = true;
        }
        e.c = c2;
    }
    
    @Override
    public Dimension a(final Rectangle[] a, final d[][] a) {
        final int c = e.c ? 1 : 0;
        int a2 = 0;
        final int a3 = c;
        final int a4 = a.length;
        int a5 = 0;
        int a7 = 0;
        while (a5 < a4) {
            final Rectangle a6 = a[a5];
            Label_0082: {
                Label_0048: {
                    try {
                        if (a3 != 0) {
                            break Label_0082;
                        }
                        final Rectangle rectangle = a6;
                        if (rectangle == null) {
                            break Label_0048;
                        }
                        break Label_0048;
                    }
                    catch (NullPointerException ex) {
                        throw ex;
                    }
                    try {
                        final Rectangle rectangle = a6;
                        if (rectangle == null) {
                            throw new NullPointerException();
                        }
                    }
                    catch (NullPointerException ex2) {
                        throw ex2;
                    }
                }
                a7 += a6.height;
                a2 = Math.max(a2, a6.width);
                ++a5;
            }
            if (a3 != 0) {
                int a8 = i.g;
                i.g = ++a8;
                break;
            }
        }
        return new Dimension(a2, a7);
    }
}
