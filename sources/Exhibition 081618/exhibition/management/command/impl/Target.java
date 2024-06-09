package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.module.impl.combat.Killaura;
import exhibition.util.misc.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;

public class Target extends Command {
   protected final Minecraft mc = Minecraft.getMinecraft();

   public Target(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else {
         if (args.length > 0) {
            String name = args[0];
            if (this.mc.theWorld.getPlayerEntityByName(name) != null) {
               EntityLivingBase vip = this.mc.theWorld.getPlayerEntityByName(name);
               Killaura.vip = vip;
               ChatUtil.printChat("§4[§cE§4]§8 Now targeting " + args[0]);
               return;
            }

            Killaura.vip = null;
            ChatUtil.printChat("§4[§cE§4]§8 No entity with the name \"" + args[0] + "\" currently exists.");
         }

         this.printUsage();
      }
   }

   public String getUsage() {
      return "Target <Target>";
   }
}
