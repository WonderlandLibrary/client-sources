/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.profiler;

public final class DataPoint
implements Comparable<DataPoint> {
    public final double relTime;
    public final double rootRelTime;
    public final long field_223511_c;
    public final String name;

    public DataPoint(String string, double d, double d2, long l) {
        this.name = string;
        this.relTime = d;
        this.rootRelTime = d2;
        this.field_223511_c = l;
    }

    @Override
    public int compareTo(DataPoint dataPoint) {
        if (dataPoint.relTime < this.relTime) {
            return 1;
        }
        return dataPoint.relTime > this.relTime ? 1 : dataPoint.name.compareTo(this.name);
    }

    public int getTextColor() {
        return (this.name.hashCode() & 0xAAAAAA) + 0x444444;
    }

    @Override
    public int compareTo(Object object) {
        return this.compareTo((DataPoint)object);
    }
}

