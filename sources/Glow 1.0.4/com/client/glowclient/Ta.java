package com.client.glowclient;

import net.minecraft.client.*;

public class ta extends Xa
{
    private final String A;
    private final int B;
    public final Minecraft b;
    
    @Override
    public void mouseReleased(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
    }
    
    @Override
    public void drawEntry(final int n, final int n2, final int n3, final int n4, final int n5, final int n6, final int n7, final boolean b) {
        if (n3 < 0) {
            return;
        }
        Qa.D(this.A, n2, n3 + 1, this.B);
    }
    
    @Override
    public void setSelected(final int n, final int n2, final int n3) {
    }
    
    public ta(final Minecraft b, final String a, final int b2) {
        super();
        this.b = b;
        this.A = a;
        this.B = b2;
    }
    
    public ta(final Minecraft minecraft, final String s) {
        this(minecraft, s, 16777215);
    }
    
    @Override
    public boolean mousePressed(final int n, final int n2, final int n3, final int n4, final int n5, final int n6) {
        return false;
    }
}
