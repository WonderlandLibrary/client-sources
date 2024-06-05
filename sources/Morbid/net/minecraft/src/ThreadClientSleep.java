package net.minecraft.src;

import net.minecraft.client.*;

public class ThreadClientSleep extends Thread
{
    final Minecraft mc;
    
    public ThreadClientSleep(final Minecraft par1Minecraft, final String par2Str) {
        super(par2Str);
        this.mc = par1Minecraft;
    }
    
    @Override
    public void run() {
        while (this.mc.running) {
            try {
                Thread.sleep(2147483647L);
            }
            catch (InterruptedException ex) {}
        }
    }
}
