package HORIZON-6-0-SKIDPROTECTION;

public class CacheLocal
{
    private int HorizonCode_Horizon_È;
    private int Â;
    private int Ý;
    private int Ø­áŒŠá;
    private int Âµá€;
    private int Ó;
    private int[][][] à;
    private int[] Ø;
    private int áŒŠÆ;
    
    public CacheLocal(final int maxX, final int maxY, final int maxZ) {
        this.HorizonCode_Horizon_È = 18;
        this.Â = 128;
        this.Ý = 18;
        this.Ø­áŒŠá = 0;
        this.Âµá€ = 0;
        this.Ó = 0;
        this.à = null;
        this.Ø = null;
        this.áŒŠÆ = 0;
        this.HorizonCode_Horizon_È = maxX;
        this.Â = maxY;
        this.Ý = maxZ;
        this.à = new int[maxX][maxY][maxZ];
        this.HorizonCode_Horizon_È();
    }
    
    public void HorizonCode_Horizon_È() {
        for (int x = 0; x < this.HorizonCode_Horizon_È; ++x) {
            final int[][] ys = this.à[x];
            for (int y = 0; y < this.Â; ++y) {
                final int[] zs = ys[y];
                for (int z = 0; z < this.Ý; ++z) {
                    zs[z] = -1;
                }
            }
        }
    }
    
    public void HorizonCode_Horizon_È(final int x, final int y, final int z) {
        this.Ø­áŒŠá = x;
        this.Âµá€ = y;
        this.Ó = z;
        this.HorizonCode_Horizon_È();
    }
    
    public int Â(final int x, final int y, final int z) {
        try {
            this.Ø = this.à[x - this.Ø­áŒŠá][y - this.Âµá€];
            this.áŒŠÆ = z - this.Ó;
            return this.Ø[this.áŒŠÆ];
        }
        catch (ArrayIndexOutOfBoundsException var5) {
            var5.printStackTrace();
            return -1;
        }
    }
    
    public void HorizonCode_Horizon_È(final int val) {
        try {
            this.Ø[this.áŒŠÆ] = val;
        }
        catch (Exception var3) {
            var3.printStackTrace();
        }
    }
}
