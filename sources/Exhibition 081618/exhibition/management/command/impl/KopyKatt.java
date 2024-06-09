package exhibition.management.command.impl;

import exhibition.management.command.Command;
import exhibition.util.misc.ChatUtil;
import java.util.Iterator;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

public class KopyKatt extends Command {
   protected final Minecraft mc = Minecraft.getMinecraft();

   public KopyKatt(String[] names, String description) {
      super(names, description);
   }

   public void fire(String[] args) {
      if (args == null) {
         this.printUsage();
      } else {
         if (args.length > 0) {
            Iterator var2 = this.mc.theWorld.getLoadedEntityList().iterator();

            while(var2.hasNext()) {
               Object o = var2.next();
               if (o instanceof EntityPlayer) {
                  EntityPlayer ent = (EntityPlayer)o;
                  if (ent.getName().equalsIgnoreCase(args[0])) {
                     exhibition.module.impl.other.KopyKatt.target = ent;
                     ChatUtil.printChat("§4[§cE§4]§8 " + ent.getName() + " is now being copied. :)");
                     return;
                  }
               }
            }
         }

         this.printUsage();
      }
   }

   public String getUsage() {
      return "Target <Target>";
   }
}
