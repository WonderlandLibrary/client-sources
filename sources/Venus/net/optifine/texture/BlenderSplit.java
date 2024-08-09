/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.texture;

import net.optifine.texture.IBlender;
import net.optifine.util.IntArray;

public class BlenderSplit
implements IBlender {
    private int startHigh;
    private boolean discreteHigh;

    public BlenderSplit(int n, boolean bl) {
        this.startHigh = n;
        this.discreteHigh = bl;
    }

    @Override
    public int blend(int n, int n2, int n3, int n4) {
        boolean bl;
        if (n == n2 && n2 == n3 && n3 == n4) {
            return n;
        }
        boolean bl2 = n < this.startHigh;
        boolean bl3 = n2 < this.startHigh;
        boolean bl4 = n3 < this.startHigh;
        boolean bl5 = bl = n4 < this.startHigh;
        if (bl2 == bl3 && bl3 == bl4 && bl4 == bl) {
            return !bl2 && this.discreteHigh ? n : (n + n2 + n3 + n4) / 4;
        }
        IntArray intArray = new IntArray(4);
        IntArray intArray2 = new IntArray(4);
        this.separate(n, intArray, intArray2);
        this.separate(n2, intArray, intArray2);
        this.separate(n3, intArray, intArray2);
        this.separate(n4, intArray, intArray2);
        if (intArray2.getPosition() > intArray.getPosition()) {
            return this.discreteHigh ? intArray2.get(0) : this.getAverage(intArray2);
        }
        return this.getAverage(intArray);
    }

    private void separate(int n, IntArray intArray, IntArray intArray2) {
        if (n < this.startHigh) {
            intArray.put(n);
        } else {
            intArray2.put(n);
        }
    }

    private int getAverage(IntArray intArray) {
        int n = intArray.getLimit();
        switch (n) {
            case 2: {
                return (intArray.get(0) + intArray.get(1)) / 2;
            }
            case 3: {
                return (intArray.get(0) + intArray.get(1) + intArray.get(2)) / 3;
            }
        }
        int n2 = 0;
        for (int i = 0; i < n; ++i) {
            n2 += intArray.get(i);
        }
        return n2 / n;
    }

    public String toString() {
        return "BlenderSplit: " + this.startHigh + ", " + this.discreteHigh;
    }

    public static void main(String[] stringArray) {
        BlenderSplit blenderSplit = new BlenderSplit(230, true);
        System.out.println("" + blenderSplit);
        int n = blenderSplit.blend(10, 20, 30, 40);
        System.out.println(n + " =? 25");
        int n2 = blenderSplit.blend(10, 20, 30, 230);
        System.out.println(n2 + " =? 20");
        int n3 = blenderSplit.blend(10, 20, 240, 230);
        System.out.println(n3 + " =? 15");
        int n4 = blenderSplit.blend(10, 250, 240, 230);
        System.out.println(n4 + " =? 250");
        int n5 = blenderSplit.blend(245, 250, 240, 230);
        System.out.println(n5 + " =? 245");
        int n6 = blenderSplit.blend(10, 10, 10, 10);
        System.out.println(n6 + " =? 10");
        BlenderSplit blenderSplit2 = new BlenderSplit(65, false);
        System.out.println("" + blenderSplit2);
        int n7 = blenderSplit2.blend(10, 20, 30, 40);
        System.out.println(n7 + " =? 25");
        int n8 = blenderSplit2.blend(10, 20, 30, 70);
        System.out.println(n8 + " =? 20");
        int n9 = blenderSplit2.blend(10, 90, 20, 70);
        System.out.println(n9 + " =? 15");
        int n10 = blenderSplit2.blend(110, 90, 20, 70);
        System.out.println(n10 + " =? 90");
        int n11 = blenderSplit2.blend(110, 90, 130, 70);
        System.out.println(n11 + " =? 100");
    }
}

