package com.client.glowclient;

import com.client.glowclient.utils.*;
import net.minecraft.client.gui.*;

public class Kg extends xf
{
    public Kg(final zE ze, final String s, final String s2) {
        super(ze, s, s2);
        this.M = ze.M();
    }
    
    @Override
    public void D(final int n, final int n2, final int n3) {
        this.D(n, n2);
        if (this.A() && n3 == 0) {
            Wrapper.mc.displayGuiScreen((GuiScreen)new jD(iD.M()));
        }
    }
}
