package HORIZON-6-0-SKIDPROTECTION;

public class ControllerDirectionControl extends ControllerControl
{
    public static final HorizonCode_Horizon_È Ó;
    public static final HorizonCode_Horizon_È à;
    public static final HorizonCode_Horizon_È Ø;
    public static final HorizonCode_Horizon_È áŒŠÆ;
    
    static {
        Ó = new HorizonCode_Horizon_È(1);
        à = new HorizonCode_Horizon_È(3);
        Ø = new HorizonCode_Horizon_È(4);
        áŒŠÆ = new HorizonCode_Horizon_È(2);
    }
    
    public ControllerDirectionControl(final int controllerIndex, final HorizonCode_Horizon_È dir) {
        super(controllerIndex, dir.HorizonCode_Horizon_È, 0);
    }
    
    private static class HorizonCode_Horizon_È
    {
        private int HorizonCode_Horizon_È;
        
        public HorizonCode_Horizon_È(final int event) {
            this.HorizonCode_Horizon_È = event;
        }
    }
}
