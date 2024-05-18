package HORIZON-6-0-SKIDPROTECTION;

public class Figure
{
    public static final int HorizonCode_Horizon_È = 1;
    public static final int Â = 2;
    public static final int Ý = 3;
    public static final int Ø­áŒŠá = 4;
    public static final int Âµá€ = 5;
    private int Ó;
    private Shape à;
    private NonGeometricData Ø;
    private Transform áŒŠÆ;
    
    public Figure(final int type, final Shape shape, final NonGeometricData data, final Transform transform) {
        this.à = shape;
        this.Ø = data;
        this.Ó = type;
        this.áŒŠÆ = transform;
    }
    
    public Transform HorizonCode_Horizon_È() {
        return this.áŒŠÆ;
    }
    
    public int Â() {
        return this.Ó;
    }
    
    public Shape Ý() {
        return this.à;
    }
    
    public NonGeometricData Ø­áŒŠá() {
        return this.Ø;
    }
}
