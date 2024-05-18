package HORIZON-6-0-SKIDPROTECTION;

import org.lwjgl.opengl.Display;
import org.lwjgl.input.Mouse;

public class MouseHelper
{
    public int HorizonCode_Horizon_È;
    public int Â;
    private static final String Ý = "CL_00000648";
    
    public void HorizonCode_Horizon_È() {
        Mouse.setGrabbed(true);
        this.HorizonCode_Horizon_È = 0;
        this.Â = 0;
    }
    
    public void Â() {
        Mouse.setCursorPosition(Display.getWidth() / 2, Display.getHeight() / 2);
        Mouse.setGrabbed(false);
    }
    
    public void Ý() {
        this.HorizonCode_Horizon_È = Mouse.getDX();
        this.Â = Mouse.getDY();
    }
}
