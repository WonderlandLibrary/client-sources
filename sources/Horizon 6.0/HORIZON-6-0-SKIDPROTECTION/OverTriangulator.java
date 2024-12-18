package HORIZON-6-0-SKIDPROTECTION;

public class OverTriangulator implements Triangulator
{
    private float[][] HorizonCode_Horizon_È;
    
    public OverTriangulator(final Triangulator tris) {
        this.HorizonCode_Horizon_È = new float[tris.Ý() * 6 * 3][2];
        int tcount = 0;
        for (int i = 0; i < tris.Ý(); ++i) {
            float cx = 0.0f;
            float cy = 0.0f;
            for (int p = 0; p < 3; ++p) {
                final float[] pt = tris.HorizonCode_Horizon_È(i, p);
                cx += pt[0];
                cy += pt[1];
            }
            cx /= 3.0f;
            cy /= 3.0f;
            for (int p = 0; p < 3; ++p) {
                int n = p + 1;
                if (n > 2) {
                    n = 0;
                }
                final float[] pt2 = tris.HorizonCode_Horizon_È(i, p);
                final float[] pt3 = tris.HorizonCode_Horizon_È(i, n);
                pt2[0] = (pt2[0] + pt3[0]) / 2.0f;
                pt2[1] = (pt2[1] + pt3[1]) / 2.0f;
                this.HorizonCode_Horizon_È[tcount * 3 + 0][0] = cx;
                this.HorizonCode_Horizon_È[tcount * 3 + 0][1] = cy;
                this.HorizonCode_Horizon_È[tcount * 3 + 1][0] = pt2[0];
                this.HorizonCode_Horizon_È[tcount * 3 + 1][1] = pt2[1];
                this.HorizonCode_Horizon_È[tcount * 3 + 2][0] = pt3[0];
                this.HorizonCode_Horizon_È[tcount * 3 + 2][1] = pt3[1];
                ++tcount;
            }
            for (int p = 0; p < 3; ++p) {
                int n = p + 1;
                if (n > 2) {
                    n = 0;
                }
                final float[] pt2 = tris.HorizonCode_Horizon_È(i, p);
                final float[] pt3 = tris.HorizonCode_Horizon_È(i, n);
                pt3[0] = (pt2[0] + pt3[0]) / 2.0f;
                pt3[1] = (pt2[1] + pt3[1]) / 2.0f;
                this.HorizonCode_Horizon_È[tcount * 3 + 0][0] = cx;
                this.HorizonCode_Horizon_È[tcount * 3 + 0][1] = cy;
                this.HorizonCode_Horizon_È[tcount * 3 + 1][0] = pt2[0];
                this.HorizonCode_Horizon_È[tcount * 3 + 1][1] = pt2[1];
                this.HorizonCode_Horizon_È[tcount * 3 + 2][0] = pt3[0];
                this.HorizonCode_Horizon_È[tcount * 3 + 2][1] = pt3[1];
                ++tcount;
            }
        }
    }
    
    @Override
    public void HorizonCode_Horizon_È(final float x, final float y) {
    }
    
    @Override
    public int Ý() {
        return this.HorizonCode_Horizon_È.length / 3;
    }
    
    @Override
    public float[] HorizonCode_Horizon_È(final int tri, final int i) {
        final float[] pt = this.HorizonCode_Horizon_È[tri * 3 + i];
        return new float[] { pt[0], pt[1] };
    }
    
    @Override
    public void Ø­áŒŠá() {
    }
    
    @Override
    public boolean Â() {
        return true;
    }
}
