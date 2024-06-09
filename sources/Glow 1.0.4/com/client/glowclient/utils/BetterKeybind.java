package com.client.glowclient.utils;

import net.minecraftforge.client.settings.*;
import net.minecraft.client.settings.*;
import com.client.glowclient.*;

public class BetterKeybind
{
    private final IKeyConflictContext L;
    private IKeyConflictContext A;
    private int B;
    private final KeyBinding b;
    
    public void M(final boolean b) {
        KeyBinding.setKeyBindState(this.b.getKeyCode(), b);
    }
    
    public void D() {
        ++this.B;
        if (this.A == null) {
            this.A = this.b.getKeyConflictContext();
            this.b.setKeyConflictContext(this.L);
        }
    }
    
    public boolean M() {
        return this.b.getKeyConflictContext() == this.L;
    }
    
    public BetterKeybind(final KeyBinding b) {
        final int b2 = 0;
        final IKeyConflictContext a = null;
        super();
        this.L = (IKeyConflictContext)new SB(this);
        this.A = a;
        this.B = b2;
        this.b = b;
    }
    
    public KeyBinding M() {
        return this.b;
    }
    
    public void M() {
        --this.B;
        if (this.A != null && this.B <= 0) {
            final IKeyConflictContext a = null;
            this.b.setKeyConflictContext(this.A);
            this.A = a;
        }
        if (this.B < 0) {
            this.B = 0;
        }
    }
}
