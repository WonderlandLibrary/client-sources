package com.sun.jna.z.a.d;

import java.util.*;

class m extends Thread
{
    final i a;
    
    m(final i a) {
        this.a = a;
    }
    
    @Override
    public void run() {
        final int m = c.m;
        final Iterator iterator = this.a.a.iterator();
        final int a = m;
        while (iterator.hasNext()) {
            final com.sun.jna.z.a.f.c a2 = iterator.next();
            final int d = a2.d;
            if (a == 0) {
                if (d == this.a.b) {
                    this.a.a.remove(a2);
                }
            }
            if (a != 0) {
                break;
            }
        }
    }
}
