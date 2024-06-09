package HORIZON-6-0-SKIDPROTECTION;

public class IntegerCache
{
    private static final int HorizonCode_Horizon_È = 4096;
    private static final Integer[] Â;
    
    static {
        Â = Â(4096);
    }
    
    private static Integer[] Â(final int size) {
        final Integer[] arr = new Integer[size];
        for (int i = 0; i < size; ++i) {
            arr[i] = new Integer(i);
        }
        return arr;
    }
    
    public static Integer HorizonCode_Horizon_È(final int value) {
        return (value >= 0 && value < 4096) ? IntegerCache.Â[value] : new Integer(value);
    }
}
