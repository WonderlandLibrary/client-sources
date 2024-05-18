package me.darkmagician6.morbid.mods;

import me.darkmagician6.morbid.mods.base.*;
import me.darkmagician6.morbid.mods.manager.*;
import me.darkmagician6.morbid.*;

public final class FastBow extends ModBase
{
    wk setItem;
    
    public FastBow() {
        super("FastBow", "0", true, ".t fastbow");
        this.setItem = wk.l;
        this.setDescription("Spari velocemente.");
    }
    
    @Override
    public void onRenderHand() {
        if (this.isEnabled()) {
            final Thread fastbowthread = new Thread(new FastBowThread(this), "FastBow Thread");
            fastbowthread.start();
        }
    }
    
    public class FastBowThread implements Runnable
    {
        public FastBow fastbow;
        
        public FastBowThread(final FastBow var1) {
            this.fastbow = var1;
        }
        
        @Override
        public void run() {
            this.fastbow();
            try {
                Thread.sleep(57L);
            }
            catch (InterruptedException var2) {
                var2.printStackTrace();
            }
        }
        
        public void fastbow() {
            Morbid.getManager();
            if (!ModManager.findMod(Freecam.class).isEnabled() && MorbidWrapper.mcObj().g.F && !MorbidWrapper.mcObj().g.S() && !MorbidWrapper.mcObj().g.ae() && !MorbidWrapper.mcObj().g.G() && MorbidWrapper.mcObj().g.aW == 0 && MorbidWrapper.mcObj().g.bX() && MorbidWrapper.mcObj().g.bK.h().b() == FastBow.this.setItem) {
                try {
                    MorbidWrapper.mcObj().r().c(new fr(-1, -1, -1, 255, MorbidWrapper.mcObj().g.bK.h(), 1.0f, 1.0f, 1.0f));
                    Thread.sleep(15L);
                    for (int i = 0; i < 50; ++i) {
                        Thread.sleep(5L);
                        MorbidWrapper.mcObj().r().c(new ee(true));
                    }
                    MorbidWrapper.mcObj().r().c(new el(5, 0, 0, 0, 255));
                    MorbidWrapper.sendPacket((ei)new fr(-1, -1, -1, -1, MorbidWrapper.mcObj().g.bK.h(), 0.0f, 0.0f, 0.0f));
                }
                catch (Exception ex) {}
            }
        }
    }
}
