package com.client.glowclient;

import net.minecraft.item.*;
import java.util.*;

public class U implements Comparable<U>
{
    private final ItemStack A;
    private final int B;
    public static final U b;
    
    @Override
    public int compareTo(final Object o) {
        return this.M((U)o);
    }
    
    public boolean M() {
        return ItemStack.EMPTY.equals(this.M());
    }
    
    static {
        b = new U(ItemStack.EMPTY, -1);
    }
    
    public int M(final U u) {
        return Integer.compare(this.M(), u.M());
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(this.A, this.B);
    }
    
    public ItemStack M() {
        return this.A;
    }
    
    public int M() {
        return this.B;
    }
    
    @Override
    public boolean equals(final Object o) {
        return this == o || (o instanceof U && this.M() == ((U)o).M() && this.M().equals(((U)o).M()));
    }
    
    public U(final ItemStack a, final int b) {
        super();
        this.A = a;
        this.B = b;
    }
}
