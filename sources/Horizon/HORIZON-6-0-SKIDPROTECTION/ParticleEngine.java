package HORIZON-6-0-SKIDPROTECTION;

import java.util.Random;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.List;

public class ParticleEngine
{
    public static List<Particles> HorizonCode_Horizon_È;
    
    static {
        ParticleEngine.HorizonCode_Horizon_È = new ArrayList<Particles>();
    }
    
    public List<Particles> HorizonCode_Horizon_È() {
        return ParticleEngine.HorizonCode_Horizon_È;
    }
    
    public void Â() {
        for (final Particles p : this.HorizonCode_Horizon_È()) {
            p.HorizonCode_Horizon_È();
        }
    }
    
    public void Ý() {
        final int y = 1;
        final int x = new Random().nextInt(GuiScreen.Çªà¢);
        final int speed = 1;
        final int size = new Random().nextBoolean() ? 1 : 2;
        ParticleEngine.HorizonCode_Horizon_È.add(new Particles(x, y, size, speed));
    }
    
    public void Ø­áŒŠá() {
        for (final Particles p : this.HorizonCode_Horizon_È()) {
            p.Â();
        }
    }
}
