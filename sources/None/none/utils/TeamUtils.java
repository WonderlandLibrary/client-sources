package none.utils;

import net.minecraft.entity.player.EntityPlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by LeakedPvP on 09/27/2017.
 */
public class TeamUtils {

  /*  public static boolean isTeam(final EntityPlayer e, final EntityPlayer e2) {
        // ChatUtil.printChat(e2.getDisplayName().getFormattedText().contains("Â§" + isTeam(e)) + " " + isTeam(e));
        return e.getDisplayName().getFormattedText().contains("Â§" + isTeam(e)) && e2.getDisplayName().getFormattedText().contains("Â§" + isTeam(e));
    }

    private static String isTeam(EntityPlayer player) {
        final Matcher m = Pattern.compile("Â§(.).*Â§r").matcher(player.getDisplayName().getFormattedText());
        if (m.find()) {
            return m.group(1);
        }
        return "f";
    }
     */
	public static boolean isTeam(final EntityPlayer e, final EntityPlayer e2) {
		if(e2.getTeam() != null && e.getTeam() != null){
			String target = e2.getDisplayName().getUnformattedText();
			String player = e.getDisplayName().getUnformattedText();
			if (Character.toLowerCase(target.charAt(0)) == '�' && Character.toLowerCase(player.charAt(0)) == '�') {
				char oneMore = Character.toLowerCase(target.charAt(1));
				char twoMore = Character.toLowerCase(player.charAt(1));
				if(oneMore == twoMore){
					return true;
				}
			}
		}else{
			return true;
		}
		return false;
	}
}
