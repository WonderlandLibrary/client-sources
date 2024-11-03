package vestige.anticheat.impl;

import net.minecraft.entity.player.EntityPlayer;
import vestige.Flap;
import vestige.anticheat.ACPlayer;
import vestige.anticheat.AnticheatCheck;
import vestige.anticheat.PlayerData;
import vestige.module.impl.combat.Antibot;
import vestige.util.misc.LogUtil;

public class BotCheck extends AnticheatCheck {
   private double buffer;

   public BotCheck(ACPlayer player) {
      super("BotCheck", player);
   }

   public void check() {
      PlayerData data = this.player.getData();
      int ticksExisted = this.player.getEntity().ticksExisted;
      boolean lowDistance = mc.thePlayer.getDistanceToEntity(this.player.getEntity()) < 20.0F;
      boolean failedSpeed = ticksExisted >= 15 && data.getDist() > (ticksExisted < 100 && mc.thePlayer.ticksExisted > 500 ? 0.7D : 2.0D) && lowDistance;
      boolean failedFly = ticksExisted >= 15 && !data.isCloseToGround() && data.getCloseToGroundTicks() >= 6 && data.getMotionY() == 0.0D || data.getLastMotionY() == 0.0D && lowDistance;
      if (failedSpeed) {
         EntityPlayer entity = this.player.getEntity();
         if (++this.buffer >= (double)(!entity.isInvisible() && !entity.isInvisibleToPlayer(mc.thePlayer) ? 4 : 2) && !this.player.isBot()) {
            this.player.setBot(true);
            Antibot antibotModule = (Antibot)Flap.instance.getModuleManager().getModule(Antibot.class);
            if (antibotModule.isEnabled() && antibotModule.advancedDetection.isEnabled() && antibotModule.debug.isEnabled()) {
               LogUtil.addChatMessage("Detected bot : " + entity.getGameProfile().getName());
               LogUtil.addChatMessage("Pos Y : " + mc.thePlayer.posY + " | Bot pos Y : " + data.getY());
            }
         }
      } else {
         this.buffer -= 0.015D;
         if (this.buffer < 0.0D) {
            this.buffer = 0.0D;
         }
      }

      if (failedFly) {
      }

   }
}
