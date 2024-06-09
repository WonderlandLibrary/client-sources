package HORIZON-6-0-SKIDPROTECTION;

public class Transform
{
    private float[] HorizonCode_Horizon_È;
    
    public Transform() {
        this.HorizonCode_Horizon_È = new float[] { 1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f };
    }
    
    public Transform(final Transform other) {
        this.HorizonCode_Horizon_È = new float[9];
        for (int i = 0; i < 9; ++i) {
            this.HorizonCode_Horizon_È[i] = other.HorizonCode_Horizon_È[i];
        }
    }
    
    public Transform(final Transform t1, final Transform t2) {
        this(t1);
        this.HorizonCode_Horizon_È(t2);
    }
    
    public Transform(final float[] matrixPosition) {
        if (matrixPosition.length != 6) {
            throw new RuntimeException("The parameter must be a float array of length 6.");
        }
        this.HorizonCode_Horizon_È = new float[] { matrixPosition[0], matrixPosition[1], matrixPosition[2], matrixPosition[3], matrixPosition[4], matrixPosition[5], 0.0f, 0.0f, 1.0f };
    }
    
    public Transform(final float point00, final float point01, final float point02, final float point10, final float point11, final float point12) {
        this.HorizonCode_Horizon_È = new float[] { point00, point01, point02, point10, point11, point12, 0.0f, 0.0f, 1.0f };
    }
    
    public void HorizonCode_Horizon_È(final float[] source, final int sourceOffset, final float[] destination, final int destOffset, final int numberOfPoints) {
        final float[] result = (source == destination) ? new float[numberOfPoints * 2] : destination;
        for (int i = 0; i < numberOfPoints * 2; i += 2) {
            for (int j = 0; j < 6; j += 3) {
                result[i + j / 3] = source[i + sourceOffset] * this.HorizonCode_Horizon_È[j] + source[i + sourceOffset + 1] * this.HorizonCode_Horizon_È[j + 1] + 1.0f * this.HorizonCode_Horizon_È[j + 2];
            }
        }
        if (source == destination) {
            for (int i = 0; i < numberOfPoints * 2; i += 2) {
                destination[i + destOffset] = result[i];
                destination[i + destOffset + 1] = result[i + 1];
            }
        }
    }
    
    public Transform HorizonCode_Horizon_È(final Transform tx) {
        final float[] mp = new float[9];
        final float n00 = this.HorizonCode_Horizon_È[0] * tx.HorizonCode_Horizon_È[0] + this.HorizonCode_Horizon_È[1] * tx.HorizonCode_Horizon_È[3];
        final float n2 = this.HorizonCode_Horizon_È[0] * tx.HorizonCode_Horizon_È[1] + this.HorizonCode_Horizon_È[1] * tx.HorizonCode_Horizon_È[4];
        final float n3 = this.HorizonCode_Horizon_È[0] * tx.HorizonCode_Horizon_È[2] + this.HorizonCode_Horizon_È[1] * tx.HorizonCode_Horizon_È[5] + this.HorizonCode_Horizon_È[2];
        final float n4 = this.HorizonCode_Horizon_È[3] * tx.HorizonCode_Horizon_È[0] + this.HorizonCode_Horizon_È[4] * tx.HorizonCode_Horizon_È[3];
        final float n5 = this.HorizonCode_Horizon_È[3] * tx.HorizonCode_Horizon_È[1] + this.HorizonCode_Horizon_È[4] * tx.HorizonCode_Horizon_È[4];
        final float n6 = this.HorizonCode_Horizon_È[3] * tx.HorizonCode_Horizon_È[2] + this.HorizonCode_Horizon_È[4] * tx.HorizonCode_Horizon_È[5] + this.HorizonCode_Horizon_È[5];
        mp[0] = n00;
        mp[1] = n2;
        mp[2] = n3;
        mp[3] = n4;
        mp[4] = n5;
        mp[5] = n6;
        this.HorizonCode_Horizon_È = mp;
        return this;
    }
    
    @Override
    public String toString() {
        final String result = "Transform[[" + this.HorizonCode_Horizon_È[0] + "," + this.HorizonCode_Horizon_È[1] + "," + this.HorizonCode_Horizon_È[2] + "][" + this.HorizonCode_Horizon_È[3] + "," + this.HorizonCode_Horizon_È[4] + "," + this.HorizonCode_Horizon_È[5] + "][" + this.HorizonCode_Horizon_È[6] + "," + this.HorizonCode_Horizon_È[7] + "," + this.HorizonCode_Horizon_È[8] + "]]";
        return result.toString();
    }
    
    public float[] HorizonCode_Horizon_È() {
        return this.HorizonCode_Horizon_È;
    }
    
    public static Transform HorizonCode_Horizon_È(final float angle) {
        return new Transform((float)FastTrig.Â(angle), -(float)FastTrig.HorizonCode_Horizon_È(angle), 0.0f, (float)FastTrig.HorizonCode_Horizon_È(angle), (float)FastTrig.Â(angle), 0.0f);
    }
    
    public static Transform HorizonCode_Horizon_È(final float angle, final float x, final float y) {
        final Transform temp = HorizonCode_Horizon_È(angle);
        final float sinAngle = temp.HorizonCode_Horizon_È[3];
        final float oneMinusCosAngle = 1.0f - temp.HorizonCode_Horizon_È[4];
        temp.HorizonCode_Horizon_È[2] = x * oneMinusCosAngle + y * sinAngle;
        temp.HorizonCode_Horizon_È[5] = y * oneMinusCosAngle - x * sinAngle;
        return temp;
    }
    
    public static Transform HorizonCode_Horizon_È(final float xOffset, final float yOffset) {
        return new Transform(1.0f, 0.0f, xOffset, 0.0f, 1.0f, yOffset);
    }
    
    public static Transform Â(final float xScale, final float yScale) {
        return new Transform(xScale, 0.0f, 0.0f, 0.0f, yScale, 0.0f);
    }
    
    public Vector2f HorizonCode_Horizon_È(final Vector2f pt) {
        final float[] in = { pt.HorizonCode_Horizon_È, pt.Â };
        final float[] out = new float[2];
        this.HorizonCode_Horizon_È(in, 0, out, 0, 1);
        return new Vector2f(out[0], out[1]);
    }
}
