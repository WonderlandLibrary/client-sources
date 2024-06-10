package xyz.gucciclient.modules.mods.other;

import xyz.gucciclient.modules.*;
import net.minecraftforge.fml.common.gameevent.*;
import net.minecraft.client.*;
import xyz.gucciclient.utils.*;
import java.lang.reflect.*;
import net.minecraftforge.fml.common.eventhandler.*;

public class FastBridge extends Module
{
    public FastBridge() {
        super(Modules.FastPlace.name(), 0, Category.OTHER);
    }
    
    @SubscribeEvent
    public void onTick(final TickEvent.ClientTickEvent event) throws Exception, Throwable {
        final Field f = Minecraft.class.getDeclaredField("field_71467_ac");
        if (f == null || !f.getName().equalsIgnoreCase("field_71467_ac")) {
            return;
        }
        f.setAccessible(true);
        f.set(Wrapper.getMinecraft(), 0);
    }
}
