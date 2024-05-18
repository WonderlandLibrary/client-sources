package HORIZON-6-0-SKIDPROTECTION;

public class FastTrig
{
    private static double Ý(double radians) {
        final double orig = radians;
        radians %= 6.283185307179586;
        if (Math.abs(radians) > 3.141592653589793) {
            radians -= 6.283185307179586;
        }
        if (Math.abs(radians) > 1.5707963267948966) {
            radians = 3.141592653589793 - radians;
        }
        return radians;
    }
    
    public static double HorizonCode_Horizon_È(double radians) {
        radians = Ý(radians);
        if (Math.abs(radians) <= 0.7853981633974483) {
            return Math.sin(radians);
        }
        return Math.cos(1.5707963267948966 - radians);
    }
    
    public static double Â(final double radians) {
        return HorizonCode_Horizon_È(radians + 1.5707963267948966);
    }
}
