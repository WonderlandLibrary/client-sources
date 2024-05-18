package wwe.modules.render;

import wwe.*;
import wwe.utils.*;
import wwe.modules.*;

public class BloodParticles extends qMIe
{
    public BooleanValue sound;
    
    public BloodParticles() {
        super("Blood Particles", "Makes blood particals when you attack a entity", 0, Category.RENDER);
    }
    
    @Override
    public void HrDr() {
        this.sound = new BooleanValue("Sound", this, "Plays a sound when you hit a entity", true);
    }
}
