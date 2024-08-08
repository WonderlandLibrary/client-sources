package net.futureclient.client;

import net.minecraft.client.Minecraft;
import net.futureclient.client.utils.Timer;

public class ND extends XB
{
    public Timer pot;
    
    public ND() {
        super(new String[] { "Pot", "ThrowPot", "Tpot", "UpPot", "SendPot", "Potion" });
        this.pot = new Timer();
    }
    
    public static Minecraft B(final ND nd) {
        return nd.k;
    }
    
    public static Minecraft C(final ND nd) {
        return nd.k;
    }
    
    public static Minecraft b(final ND nd) {
        return nd.k;
    }
    
    public static Minecraft e(final ND nd) {
        return nd.k;
    }
    
    public static Minecraft i(final ND nd) {
        return nd.k;
    }
    
    public static Minecraft M(final ND nd) {
        return nd.k;
    }
    
    @Override
    public String M(final String[] array) {
        pg.M().M().M((n)new oF(this));
        return "Throwing potion.";
    }
    
    @Override
    public String M() {
        return null;
    }
}
