package com.sun.jna.z.a.d;

import com.sun.jna.z.a.e.a.a.a.d.*;
import java.util.concurrent.*;
import com.sun.jna.z.a.e.a.a.a.a.*;

class i implements a
{
    final CopyOnWriteArrayList a;
    final int b;
    final k c;
    
    i(final k a, final CopyOnWriteArrayList a2, final int b) {
        this.c = a;
        this.a = a2;
        this.b = b;
    }
    
    @Override
    public void a(final g a, final int a) {
        new m(this).start();
    }
}
