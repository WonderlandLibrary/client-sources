package net.minecraft.src;

public class CounterInt {
    private final int startValue;
    private int value;

    public CounterInt(int p_i34_1_) {
        this.startValue = p_i34_1_;
        this.value = p_i34_1_;
    }

    public synchronized int nextValue() {
        int i = this.value++;
        return i;
    }

    public synchronized void reset() {
        this.value = this.startValue;
    }

    public int getValue() {
        return this.value;
    }
}
