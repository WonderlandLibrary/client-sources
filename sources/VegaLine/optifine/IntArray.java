/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package optifine;

public class IntArray {
    private int[] array = null;
    private int position = 0;
    private int limit = 0;

    public IntArray(int p_i62_1_) {
        this.array = new int[p_i62_1_];
    }

    public void put(int p_put_1_) {
        this.array[this.position] = p_put_1_;
        ++this.position;
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }

    public void put(int p_put_1_, int p_put_2_) {
        this.array[p_put_1_] = p_put_2_;
        if (this.limit < p_put_1_) {
            this.limit = p_put_1_;
        }
    }

    public void position(int p_position_1_) {
        this.position = p_position_1_;
    }

    public void put(int[] p_put_1_) {
        for (int this.array[this.position] : p_put_1_) {
            ++this.position;
        }
        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }

    public int get(int p_get_1_) {
        return this.array[p_get_1_];
    }

    public int[] getArray() {
        return this.array;
    }

    public void clear() {
        this.position = 0;
        this.limit = 0;
    }

    public int getLimit() {
        return this.limit;
    }

    public int getPosition() {
        return this.position;
    }
}

