package rina.turok.bope;

import net.minecraftforge.common.MinecraftForge;
import rina.turok.bope.bopemod.manager.BopeCommandManager;
import rina.turok.bope.bopemod.manager.BopeEventManager;

public class BopeEventRegister {
   public static void register_command_manager(BopeCommandManager manager) {
      MinecraftForge.EVENT_BUS.register(manager);
   }

   public static void register_module_manager(BopeEventManager manager) {
      MinecraftForge.EVENT_BUS.register(manager);
   }
}
