package com.wikihacks.listener.tick;

import com.wikihacks.module.ModuleManager;
import com.wikihacks.module.modules.ExampleModule;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;

public class ClientTick {
    @SubscribeEvent
    public static void onTick(TickEvent.ClientTickEvent e) {
        if (ModuleManager.getModuleByName("ExampleModule").isEnabled()) {
            ModuleManager.getModuleByName("ExampleModule").onUpdate();
        }
    }
}
