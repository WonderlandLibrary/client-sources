package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;

public class FlexibleArray<T> implements Iterable<T>
{
    private T[] HorizonCode_Horizon_È;
    
    public FlexibleArray(final T[] array) {
        this.HorizonCode_Horizon_È = array;
    }
    
    public FlexibleArray() {
        this.HorizonCode_Horizon_È = new Object[0];
    }
    
    public void HorizonCode_Horizon_È(final T t) {
        if (t != null) {
            final Object[] array = new Object[this.Â() + 1];
            for (int i = 0; i < array.length; ++i) {
                if (i < this.Â()) {
                    array[i] = this.HorizonCode_Horizon_È(i);
                }
                else {
                    array[i] = t;
                }
            }
            this.HorizonCode_Horizon_È(array);
        }
    }
    
    public void Â(final T t) {
        if (this.Ý(t)) {
            final Object[] array = new Object[this.Â() - 1];
            boolean b = true;
            for (int i = 0; i < this.Â(); ++i) {
                if (b && this.HorizonCode_Horizon_È(i).equals(t)) {
                    b = false;
                }
                else {
                    array[b ? i : (i - 1)] = this.HorizonCode_Horizon_È(i);
                }
            }
            this.HorizonCode_Horizon_È(array);
        }
    }
    
    public boolean Ý(final T t) {
        Object[] var5;
        for (int var4 = (var5 = this.Ý()).length, var6 = 0; var6 < var4; ++var6) {
            final Object entry = var5[var6];
            if (entry.equals(t)) {
                return true;
            }
        }
        return false;
    }
    
    private void HorizonCode_Horizon_È(final T[] array) {
        this.HorizonCode_Horizon_È = array;
    }
    
    public void HorizonCode_Horizon_È() {
        this.HorizonCode_Horizon_È = new Object[0];
    }
    
    public T HorizonCode_Horizon_È(final int index) {
        return this.Ý()[index];
    }
    
    public int Â() {
        return this.Ý().length;
    }
    
    public T[] Ý() {
        return (T[])this.HorizonCode_Horizon_È;
    }
    
    public boolean Ø­áŒŠá() {
        return this.Â() == 0;
    }
    
    @Override
    public Iterator<T> iterator() {
        return (Iterator<T>)new Iterator() {
            private int Â = 0;
            
            @Override
            public boolean hasNext() {
                return this.Â < FlexibleArray.this.Â() && FlexibleArray.this.HorizonCode_Horizon_È(this.Â) != null;
            }
            
            @Override
            public T next() {
                return FlexibleArray.this.HorizonCode_Horizon_È(this.Â++);
            }
            
            @Override
            public void remove() {
                FlexibleArray.this.Â(FlexibleArray.this.HorizonCode_Horizon_È(this.Â));
            }
        };
    }
}
