package net.minecraft.src;

import java.util.*;

public class CompactArrayList
{
    private ArrayList list;
    private int initialCapacity;
    private float loadFactor;
    private int countValid;
    
    public CompactArrayList() {
        this(10, 0.75f);
    }
    
    public CompactArrayList(final int var1) {
        this(var1, 0.75f);
    }
    
    public CompactArrayList(final int var1, final float var2) {
        this.list = null;
        this.initialCapacity = 0;
        this.loadFactor = 1.0f;
        this.countValid = 0;
        this.list = new ArrayList(var1);
        this.initialCapacity = var1;
        this.loadFactor = var2;
    }
    
    public void add(final int var1, final Object var2) {
        if (var2 != null) {
            ++this.countValid;
        }
        this.list.add(var1, var2);
    }
    
    public boolean add(final Object var1) {
        if (var1 != null) {
            ++this.countValid;
        }
        return this.list.add(var1);
    }
    
    public Object set(final int var1, final Object var2) {
        final Object var3 = this.list.set(var1, var2);
        if (var2 != var3) {
            if (var3 == null) {
                ++this.countValid;
            }
            if (var2 == null) {
                --this.countValid;
            }
        }
        return var3;
    }
    
    public Object remove(final int var1) {
        final Object var2 = this.list.remove(var1);
        if (var2 != null) {
            --this.countValid;
        }
        return var2;
    }
    
    public void clear() {
        this.list.clear();
        this.countValid = 0;
    }
    
    public void compact() {
        if (this.countValid <= 0 && this.list.size() <= 0) {
            this.clear();
        }
        else if (this.list.size() > this.initialCapacity) {
            final float var1 = this.countValid * 1.0f / this.list.size();
            if (var1 <= this.loadFactor) {
                int var2 = 0;
                for (int var3 = 0; var3 < this.list.size(); ++var3) {
                    final Object var4 = this.list.get(var3);
                    if (var4 != null) {
                        if (var3 != var2) {
                            this.list.set(var2, var4);
                        }
                        ++var2;
                    }
                }
                for (int var3 = this.list.size() - 1; var3 >= var2; --var3) {
                    this.list.remove(var3);
                }
            }
        }
    }
    
    public boolean contains(final Object var1) {
        return this.list.contains(var1);
    }
    
    public Object get(final int var1) {
        return this.list.get(var1);
    }
    
    public boolean isEmpty() {
        return this.list.isEmpty();
    }
    
    public int size() {
        return this.list.size();
    }
    
    public int getCountValid() {
        return this.countValid;
    }
}
