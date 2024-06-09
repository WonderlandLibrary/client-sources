package exhibition.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import net.minecraft.entity.player.EntityPlayer;

public class TeamUtils {
   public static boolean isTeam(EntityPlayer e, EntityPlayer e2) {
      return e.getDisplayName().getFormattedText().contains("§" + isTeam(e)) && e2.getDisplayName().getFormattedText().contains("§" + isTeam(e));
   }

   private static String isTeam(EntityPlayer player) {
      Matcher m = Pattern.compile("§(.).*§r").matcher(player.getDisplayName().getFormattedText());
      return m.find() ? m.group(1) : "f";
   }
}
