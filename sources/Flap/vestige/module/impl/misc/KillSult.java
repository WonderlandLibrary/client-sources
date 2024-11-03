package vestige.module.impl.misc;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.play.server.S13PacketDestroyEntities;
import vestige.Flap;
import vestige.event.Listener;
import vestige.event.impl.PacketReceiveEvent;
import vestige.module.Category;
import vestige.module.Module;
import vestige.module.impl.combat.Killaura;
import vestige.setting.impl.ModeSetting;
import vestige.util.util.Killinsuts;

public class KillSult extends Module {
   private final ModeSetting mode = new ModeSetting("Mode", "Mush", new String[]{"Mush", "None"});

   public KillSult() {
      super("KillSult", Category.COMBAT);
   }

   @Listener
   public void receibePacketrs(PacketReceiveEvent event) {
      if (event.getPacket() instanceof S13PacketDestroyEntities) {
         S13PacketDestroyEntities packet = (S13PacketDestroyEntities)event.getPacket();
         int[] var3 = packet.getEntityIDs();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            int id = var3[var5];
            if(((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getTarget() instanceof EntityPlayer){
               if (((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getTarget() != null && id == ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getTarget().getEntityId()) {
                  mc.thePlayer.sendChatMessage(((EntityPlayer) ((Killaura)Flap.instance.getModuleManager().getModule(Killaura.class)).getTarget()).getName() + " " + Killinsuts.getFrasesKillsults());
               }
            }

         }
      }

   }
}
