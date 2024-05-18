package HORIZON-6-0-SKIDPROTECTION;

public class Renderer
{
    public static final int HorizonCode_Horizon_È = 1;
    public static final int Â = 2;
    public static final int Ý = 3;
    public static final int Ø­áŒŠá = 4;
    private static SGL Âµá€;
    private static LineStripRenderer Ó;
    
    static {
        Renderer.Âµá€ = new ImmediateModeOGLRenderer();
        Renderer.Ó = new DefaultLineStripRenderer();
    }
    
    public static void HorizonCode_Horizon_È(final int type) {
        switch (type) {
            case 1: {
                HorizonCode_Horizon_È(new ImmediateModeOGLRenderer());
            }
            case 2: {
                HorizonCode_Horizon_È(new VAOGLRenderer());
            }
            default: {
                throw new RuntimeException("Unknown renderer type: " + type);
            }
        }
    }
    
    public static void Â(final int type) {
        switch (type) {
            case 3: {
                HorizonCode_Horizon_È(new DefaultLineStripRenderer());
            }
            case 4: {
                HorizonCode_Horizon_È(new QuadBasedLineStripRenderer());
            }
            default: {
                throw new RuntimeException("Unknown line strip renderer type: " + type);
            }
        }
    }
    
    public static void HorizonCode_Horizon_È(final LineStripRenderer renderer) {
        Renderer.Ó = renderer;
    }
    
    public static void HorizonCode_Horizon_È(final SGL r) {
        Renderer.Âµá€ = r;
    }
    
    public static SGL HorizonCode_Horizon_È() {
        return Renderer.Âµá€;
    }
    
    public static LineStripRenderer Â() {
        return Renderer.Ó;
    }
}
