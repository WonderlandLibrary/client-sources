package net.optifine.util;

import lombok.Getter;
import net.minecraft.util.math.MathHelper;

@Getter
public class IntArray {
    private int[] array = null;
    private int position = 0;
    private int limit = 0;

    public IntArray(int size) {
        this.array = new int[size];
    }

    public void put(int x) {
        this.checkPutIndex(this.position);
        this.array[this.position] = x;
        ++this.position;

        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }

    public void put(int pos, int x) {
        this.checkPutIndex(x);
        this.array[pos] = x;

        if (this.limit < pos) {
            this.limit = pos;
        }
    }

    public void position(int pos) {
        this.position = pos;
    }

    public void put(int[] ints) {
        this.checkPutIndex(this.position + ints.length - 1);

        for (int anInt : ints) {
            this.array[this.position] = anInt;
            ++this.position;
        }

        if (this.limit < this.position) {
            this.limit = this.position;
        }
    }

    private void checkPutIndex(int index) {
        if (index >= this.array.length) {
            int i = MathHelper.smallestEncompassingPowerOfTwo(index + 1);
            int[] aint = new int[i];
            System.arraycopy(this.array, 0, aint, 0, this.array.length);
            this.array = aint;
        }
    }

    public int get(int pos) {
        return this.array[pos];
    }

    public void clear() {
        this.position = 0;
        this.limit = 0;
    }

    public int[] toIntArray() {
        int[] aint = new int[this.limit];
        System.arraycopy(this.array, 0, aint, 0, aint.length);
        return aint;
    }

    public String toString() {
        return "position: " + this.position + ", limit: " + this.limit + ", capacity: " + this.array.length;
    }
}
