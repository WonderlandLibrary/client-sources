package xyz.gucciclient.modules.mods.other;

import xyz.gucciclient.modules.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.multiplayer.*;
import net.minecraft.potion.*;
import java.lang.reflect.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class FastBreeeak extends Module
{
    public FastBreeeak() {
        super("FastBreak", 0, Category.OTHER);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) throws Exception, Throwable {
        try {
            final Field f = PlayerControllerMP.class.getDeclaredField("blockHitDelay");
            if (f == null || !f.getName().equalsIgnoreCase("blockHitDelay")) {
                return;
            }
            f.setAccessible(true);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
        this.mc.thePlayer.addPotionEffect(new PotionEffect(3, 9999, 1));
    }
    
    @Override
    public void onDisable() {
        this.mc.thePlayer.removePotionEffect(3);
    }
}
