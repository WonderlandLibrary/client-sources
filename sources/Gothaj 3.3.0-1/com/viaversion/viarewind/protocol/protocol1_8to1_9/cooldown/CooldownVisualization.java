package com.viaversion.viarewind.protocol.protocol1_8to1_9.cooldown;

import com.viaversion.viarewind.ViaRewind;
import com.viaversion.viarewind.api.ViaRewindConfig;
import com.viaversion.viaversion.api.connection.UserConnection;

public interface CooldownVisualization {
   int MAX_PROGRESS_TEXT_LENGTH = 10;

   void show(double var1) throws Exception;

   void hide() throws Exception;

   static String buildProgressText(String symbol, double cooldown) {
      int green = (int)Math.floor(10.0 * cooldown);
      int grey = 10 - green;
      StringBuilder builder = new StringBuilder("§8");

      while (green-- > 0) {
         builder.append(symbol);
      }

      builder.append("§7");

      while (grey-- > 0) {
         builder.append(symbol);
      }

      return builder.toString();
   }

   public interface Factory {
      CooldownVisualization.Factory DISABLED = user -> new DisabledCooldownVisualization();

      CooldownVisualization create(UserConnection var1);

      static CooldownVisualization.Factory fromConfiguration() {
         try {
            return fromIndicator(ViaRewind.getConfig().getCooldownIndicator());
         } catch (IllegalArgumentException var1) {
            ViaRewind.getPlatform().getLogger().warning("Invalid cooldown-indicator setting");
            return DISABLED;
         }
      }

      static CooldownVisualization.Factory fromIndicator(ViaRewindConfig.CooldownIndicator indicator) {
         switch (indicator) {
            case TITLE:
               return TitleCooldownVisualization::new;
            case BOSS_BAR:
               return BossBarVisualization::new;
            case ACTION_BAR:
               return ActionBarVisualization::new;
            case DISABLED:
               return DISABLED;
            default:
               throw new IllegalArgumentException("Unexpected: " + indicator);
         }
      }
   }
}
