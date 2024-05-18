package HORIZON-6-0-SKIDPROTECTION;

import java.util.ArrayList;

public class CompactArrayList
{
    private ArrayList HorizonCode_Horizon_È;
    private int Â;
    private float Ý;
    private int Ø­áŒŠá;
    
    public CompactArrayList() {
        this(10, 0.75f);
    }
    
    public CompactArrayList(final int initialCapacity) {
        this(initialCapacity, 0.75f);
    }
    
    public CompactArrayList(final int initialCapacity, final float loadFactor) {
        this.HorizonCode_Horizon_È = null;
        this.Â = 0;
        this.Ý = 1.0f;
        this.Ø­áŒŠá = 0;
        this.HorizonCode_Horizon_È = new ArrayList(initialCapacity);
        this.Â = initialCapacity;
        this.Ý = loadFactor;
    }
    
    public void HorizonCode_Horizon_È(final int index, final Object element) {
        if (element != null) {
            ++this.Ø­áŒŠá;
        }
        this.HorizonCode_Horizon_È.add(index, element);
    }
    
    public boolean HorizonCode_Horizon_È(final Object element) {
        if (element != null) {
            ++this.Ø­áŒŠá;
        }
        return this.HorizonCode_Horizon_È.add(element);
    }
    
    public Object Â(final int index, final Object element) {
        final Object oldElement = this.HorizonCode_Horizon_È.set(index, element);
        if (element != oldElement) {
            if (oldElement == null) {
                ++this.Ø­áŒŠá;
            }
            if (element == null) {
                --this.Ø­áŒŠá;
            }
        }
        return oldElement;
    }
    
    public Object HorizonCode_Horizon_È(final int index) {
        final Object oldElement = this.HorizonCode_Horizon_È.remove(index);
        if (oldElement != null) {
            --this.Ø­áŒŠá;
        }
        return oldElement;
    }
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È.clear();
        this.Ø­áŒŠá = 0;
    }
    
    public void Â() {
        if (this.Ø­áŒŠá <= 0 && this.HorizonCode_Horizon_È.size() <= 0) {
            this.HorizonCode_Horizon_È();
        }
        else if (this.HorizonCode_Horizon_È.size() > this.Â) {
            final float currentLoadFactor = this.Ø­áŒŠá * 1.0f / this.HorizonCode_Horizon_È.size();
            if (currentLoadFactor <= this.Ý) {
                int dstIndex = 0;
                for (int i = 0; i < this.HorizonCode_Horizon_È.size(); ++i) {
                    final Object wr = this.HorizonCode_Horizon_È.get(i);
                    if (wr != null) {
                        if (i != dstIndex) {
                            this.HorizonCode_Horizon_È.set(dstIndex, wr);
                        }
                        ++dstIndex;
                    }
                }
                for (int i = this.HorizonCode_Horizon_È.size() - 1; i >= dstIndex; --i) {
                    this.HorizonCode_Horizon_È.remove(i);
                }
            }
        }
    }
    
    public boolean Â(final Object elem) {
        return this.HorizonCode_Horizon_È.contains(elem);
    }
    
    public Object Â(final int index) {
        return this.HorizonCode_Horizon_È.get(index);
    }
    
    public boolean Ý() {
        return this.HorizonCode_Horizon_È.isEmpty();
    }
    
    public int Ø­áŒŠá() {
        return this.HorizonCode_Horizon_È.size();
    }
    
    public int Âµá€() {
        return this.Ø­áŒŠá;
    }
}
