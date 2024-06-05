package com.ibm.icu.impl;

public class Pair<F, S>
{
    public final F first;
    public final S second;
    
    protected Pair(final F first, final S second) {
        this.first = first;
        this.second = second;
    }
    
    public static <F, S> Pair<F, S> of(final F first, final S second) {
        if (first == null || second == null) {
            throw new IllegalArgumentException("Pair.of requires non null values.");
        }
        return new Pair<F, S>(first, second);
    }
    
    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Pair)) {
            return false;
        }
        final Pair<?, ?> rhs = (Pair<?, ?>)other;
        return this.first.equals(rhs.first) && this.second.equals(rhs.second);
    }
    
    @Override
    public int hashCode() {
        return this.first.hashCode() * 37 + this.second.hashCode();
    }
}
