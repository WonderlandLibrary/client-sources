package com.client.glowclient;

import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class rF extends Module
{
    public rF(final String s, final String s2) {
        super(Category.SERVICE, s, s2);
    }
    
    public rF(final String s, final String s2, final int n) {
        final String s3 = "f^TYDUI";
        super(Category.SERVICE, s, s2);
        this.M(s, n);
        this.c = ValueFactory.M(s, ya.M(s3), "Keybind of the mod", n);
    }
    
    @Override
    public boolean k() {
        return true;
    }
}
