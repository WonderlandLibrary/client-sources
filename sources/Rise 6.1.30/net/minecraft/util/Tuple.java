package net.minecraft.util;

public class Tuple<A, B> {
    private A a;
    private B b;

    public Tuple(final A aIn, final B bIn) {
        this.a = aIn;
        this.b = bIn;
    }

    /**
     * Get the first Object in the Tuple
     */
    public A getFirst() {
        return this.a;
    }

    /**
     * Get the second Object in the Tuple
     */
    public B getSecond() {
        return this.b;
    }

    public void setFirst(A a) {
        this.a = a;
    }

    public void setSecond(B b) {
        this.b = b;
    }
}
