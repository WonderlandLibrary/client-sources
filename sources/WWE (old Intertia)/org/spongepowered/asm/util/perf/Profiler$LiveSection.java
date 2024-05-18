package org.spongepowered.asm.util.perf;

import java.util.*;

class LiveSection extends Section
{
    private int cursor;
    private long[] times;
    private long start;
    private long time;
    private long markedTime;
    private int count;
    private int markedCount;
    final Profiler this$0;
    
    LiveSection(final Profiler this$0, final String s, final int cursor) {
        this.this$0 = this$0.super(s);
        this.cursor = 0;
        this.times = new long[0];
        this.start = 0L;
        this.cursor = cursor;
    }
    
    @Override
    Section start() {
        this.start = System.currentTimeMillis();
        return this;
    }
    
    @Override
    protected Section stop() {
        if (this.start > 0L) {
            this.time += System.currentTimeMillis() - this.start;
        }
        this.start = 0L;
        ++this.count;
        return this;
    }
    
    @Override
    public Section end() {
        this.stop();
        if (!this.invalidated) {
            this.this$0.end(this);
        }
        return this;
    }
    
    @Override
    void mark() {
        if (this.cursor >= this.times.length) {
            this.times = Arrays.copyOf(this.times, this.cursor + 4);
        }
        this.times[this.cursor] = this.time;
        this.markedTime += this.time;
        this.markedCount += this.count;
        this.time = 0L;
        this.count = 0;
        ++this.cursor;
    }
    
    @Override
    public long getTime() {
        return this.time;
    }
    
    @Override
    public long getTotalTime() {
        return this.time + this.markedTime;
    }
    
    @Override
    public double getSeconds() {
        return this.time * 0.001;
    }
    
    @Override
    public double getTotalSeconds() {
        return (this.time + this.markedTime) * 0.001;
    }
    
    @Override
    public long[] getTimes() {
        final long[] array = new long[this.cursor + 1];
        System.arraycopy(this.times, 0, array, 0, Math.min(this.times.length, this.cursor));
        array[this.cursor] = this.time;
        return array;
    }
    
    @Override
    public int getCount() {
        return this.count;
    }
    
    @Override
    public int getTotalCount() {
        return this.count + this.markedCount;
    }
    
    @Override
    public double getAverageTime() {
        return (this.count > 0) ? (this.time / (double)this.count) : 0.0;
    }
    
    @Override
    public double getTotalAverageTime() {
        return (this.count > 0) ? ((this.time + this.markedTime) / (double)(this.count + this.markedCount)) : 0.0;
    }
}
