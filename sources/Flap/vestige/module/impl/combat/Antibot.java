package vestige.module.impl.combat;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import vestige.Flap;
import vestige.module.Category;
import vestige.module.Module;
import vestige.setting.AbstractSetting;
import vestige.setting.impl.BooleanSetting;
import vestige.setting.impl.IntegerSetting;
import vestige.util.misc.LogUtil;

public class Antibot extends Module {
   private final IntegerSetting ticksExisted = new IntegerSetting("Ticks existed", 30, 0, 100, 5);
   public final BooleanSetting advancedDetection = new BooleanSetting("Advanced detection", true);
   public final BooleanSetting debug = new BooleanSetting("Debug", false);
   private static final HashMap<EntityPlayer, Long> entities = new HashMap();
   private final BooleanSetting entitySpawnDelay = new BooleanSetting("Entity spawn delay", false);
   private final IntegerSetting delay = new IntegerSetting("Delay", 7, 1, 30, 1);
   private final BooleanSetting pitSpawn = new BooleanSetting("Pit spawn", false);
   private final BooleanSetting tablist = new BooleanSetting("Tab list", false);

   public Antibot() {
      super("Antibot", Category.COMBAT);
      this.addSettings(new AbstractSetting[]{this.ticksExisted, this.advancedDetection, this.debug, this.entitySpawnDelay, this.delay, this.pitSpawn, this.tablist});
   }

   public void onClientStarted() {
   }

   public void onUpdate() {
      if (this.entitySpawnDelay.isEnabled() && !entities.isEmpty()) {
         entities.values().removeIf((n) -> {
            return n < System.currentTimeMillis() - (long)(this.delay.getValue() * 1000);
         });
      }

   }

   public boolean onDisable() {
      entities.clear();
      return false;
   }

   public boolean canAttack(EntityLivingBase entity, Module module) {
      if (!this.isEnabled()) {
         return true;
      } else if (entity.ticksExisted < this.ticksExisted.getValue()) {
         if (this.debug.isEnabled() && module == Flap.instance.getModuleManager().getModule(Killaura.class)) {
            LogUtil.addChatMessage("Ticks existed antibot: prevented from hitting: " + entity.ticksExisted);
         }

         return false;
      } else {
         if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer)entity;
            if (this.isBot(player)) {
               if (this.debug.isEnabled() && module == Flap.instance.getModuleManager().getModule(Killaura.class)) {
                  LogUtil.addChatMessage("Advanced antibot: prevented from hitting bot");
               }

               return false;
            }
         }

         return true;
      }
   }

   public boolean isBot(EntityPlayer player) {
      if (!this.isEnabled()) {
         return false;
      } else if (this.entitySpawnDelay.isEnabled() && entities.containsKey(player)) {
         return true;
      } else if (player.isDead) {
         return true;
      } else if (player.getName().isEmpty()) {
         return true;
      } else if (this.tablist.isEnabled() && !this.getTablist().contains(player.getName())) {
         return true;
      } else {
         if (player.maxHurtTime == 0) {
            String unformattedText;
            if (player.getHealth() == 20.0F) {
               unformattedText = player.getDisplayName().getUnformattedText();
               if (unformattedText.length() == 10 && unformattedText.charAt(0) != 167) {
                  return true;
               }

               if (unformattedText.length() == 12 && player.isPlayerSleeping() && unformattedText.charAt(0) == 167) {
                  return true;
               }

               if (unformattedText.length() >= 7 && unformattedText.charAt(2) == '[' && unformattedText.charAt(3) == 'N' && unformattedText.charAt(6) == ']') {
                  return true;
               }

               if (player.getName().contains(" ")) {
                  return true;
               }
            } else if (player.isInvisible()) {
               unformattedText = player.getDisplayName().getUnformattedText();
               if (unformattedText.length() >= 3 && unformattedText.charAt(0) == 167 && unformattedText.charAt(1) == 'c') {
                  return true;
               }
            }
         }

         return false;
      }
   }

   private List<String> getTablist() {
      List<String> tab = new ArrayList();
      Iterator var2 = mc.getNetHandler().getPlayerInfoMap().iterator();

      while(var2.hasNext()) {
         NetworkPlayerInfo networkPlayerInfo = (NetworkPlayerInfo)var2.next();
         if (networkPlayerInfo != null) {
            tab.add(networkPlayerInfo.getGameProfile().getName());
         }
      }

      return tab;
   }
}
