package koks.utilities;

import net.minecraft.entity.player.EntityPlayer;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author avox | lmao | kroko
 * @created on 05.09.2020 : 17:10
 */
public class EntityUtil {

    public String getTeam(EntityPlayer player) {
        Matcher m = Pattern.compile("\u00a7(.).*\u00a7r").matcher(player.getDisplayName().getFormattedText());
        if (m.find()) {
            return m.group(1);
        }
        return "f";
    }

    public boolean isTeam(EntityPlayer e, EntityPlayer e2) {
        return e.getDisplayName().getFormattedText().contains("\u00a7" + getTeam(e))
                && e2.getDisplayName().getFormattedText().contains("\u00a7" + getTeam(e));
    }

}