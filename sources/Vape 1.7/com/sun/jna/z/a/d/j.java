package com.sun.jna.z.a.d;

import md.*;

public class j implements Runnable
{
    public i a;
    
    public j(final i a) {
        this.a = a;
        new n(this).start();
    }
    
    @Override
    public void run() {
        this.a.h();
    }
}
