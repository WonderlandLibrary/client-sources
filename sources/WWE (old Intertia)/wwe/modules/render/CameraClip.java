package wwe.modules.render;

import wwe.*;
import wwe.utils.*;
import wwe.modules.*;

public class CameraClip extends qMIe
{
    public BooleanValue moreDistance;
    public NumberValue cameraDistance;
    
    public CameraClip() {
        super("Camera Clip", "Lets you see though walls in 3rd person", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        this.moreDistance = new BooleanValue("More Distance", this, "More distance in 2/3 person", false);
        this.cameraDistance = new NumberValue("Camera Distance", this, "How much more distance in 2/3 person", 10.0, 0.0, 50.0);
    }
}
