package com.client.glowclient.modules.render;

import com.client.glowclient.sponge.mixinutils.*;
import com.client.glowclient.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class Xray extends ModuleContainer
{
    private boolean B;
    public static final NumberValue opacity;
    
    @Override
    public void D() {
        HookTranslator.v21 = true;
        ma.M();
    }
    
    @Override
    public void E() {
        HookTranslator.v21 = false;
        ma.M();
    }
    
    public Xray() {
        final boolean b = false;
        super(Category.RENDER, "Xray", false, -1, "Adds transparency to blocks");
        this.B = b;
    }
    
    static {
        opacity = ValueFactory.M("Xray", "Opacity", "Block transparency", 75.0, 1.0, 0.0, 255.0);
    }
}
