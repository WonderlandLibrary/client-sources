package ClassSub;

import net.minecraftforge.fml.common.*;

final class Class13 extends Thread
{
    
    
    @Override
    public void run() {
        try {
            Thread.sleep(3000L);
            FMLCommonHandler.instance().exitJava(0, true);
        }
        catch (InterruptedException ex) {}
    }
}
