package net.futureclient.client;

import net.futureclient.client.modules.render.ViewClip;

public class rC extends qA
{
    public final ViewClip k;
    
    public rC(final ViewClip k, final String s) {
        this.k = k;
        super(s);
    }
    
    @Override
    public void M() {
        this.k.distance.M(3.5f);
    }
}
