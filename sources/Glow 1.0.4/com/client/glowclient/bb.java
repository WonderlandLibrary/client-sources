package com.client.glowclient;

import net.minecraft.item.*;

public class Bb
{
    public int L;
    public int A;
    public ItemStack B;
    public int b;
    
    public String D() {
        return this.B.getItem().getItemStackDisplayName(this.B);
    }
    
    public Bb(final ItemStack b, final int l, final int a) {
        super();
        this.B = b;
        this.L = l;
        this.A = a;
    }
    
    private static String M(final ItemStack itemStack, final int n) {
        itemStack.getMaxStackSize();
        return String.format("%d", n);
    }
    
    public String M(final String s, final String s2) {
        final int n = this.A - (this.b + this.L);
        if (this.b != -1 && n > 0) {
            return String.format("§c%s: %s", s2, M(this.B, n));
        }
        return String.format("§a%s", s);
    }
    
    public String M() {
        return String.format("§%c%s§r/%s", (this.L < this.A) ? 'c' : 'a', M(this.B, this.L), M(this.B, this.A));
    }
    
    public Bb(final ItemStack itemStack) {
        final int n = 0;
        this(itemStack, n, n);
    }
}
