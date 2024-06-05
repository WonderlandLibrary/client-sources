// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.utils.player;

import net.minecraft.entity.player.EntityPlayer;
import java.util.Iterator;
import java.util.Objects;

public class PitUtils
{
    private static int prestige;
    private static final String[] EVENTS;
    
    public static Boolean isInPit() {
        return Objects.requireNonNull(ScoreboardUtils.getScorebardTitle()).replaceAll("§.", "").contains("PIT");
    }
    
    public static Double getGold() {
        for (final String line : ScoreboardUtils.getScoreboardData()) {
            if (line.contains("Gold: §6")) {
                try {
                    return Double.parseDouble(line.split("Gold: §6")[1].replace("§6", "").split("g")[0].replace(",", ""));
                }
                catch (final Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return 0.0;
    }
    
    public static Boolean isNon(final EntityPlayer retard) {
        final String[] keywords = { "diamond", "tier", "archangel", "golden helmet" };
        for (int i = 0; i < 3; ++i) {
            if (retard.getCurrentArmor(i) != null && retard.getCurrentArmor(i).getDisplayName() != null) {
                for (final String keyword : keywords) {
                    if (retard.getCurrentArmor(i).getDisplayName().toLowerCase().contains(keyword)) {
                        return false;
                    }
                }
            }
        }
        return true;
    }
    
    public static String getLevelString() {
        for (final String line : ScoreboardUtils.getScoreboardData()) {
            if (line.contains("Level: ")) {
                return line.split("Level: ")[1];
            }
        }
        return "";
    }
    
    public static int getLevel() {
        String lvlString = getLevelString();
        if (!lvlString.equals("")) {
            lvlString = lvlString.replaceAll("(§.|\\[|])", "");
            try {
                return Integer.parseInt(lvlString);
            }
            catch (final Exception e) {
                e.printStackTrace();
            }
        }
        return 0;
    }
    
    public static int getPrestige() {
        return 0;
    }
    
    public static MajorEvent getCurrentEvent() {
        for (final String line : ScoreboardUtils.getScoreboardData()) {
            for (final String event : PitUtils.EVENTS) {
                if (line.contains(event)) {
                    return MajorEvent.valueOf(line);
                }
            }
        }
        return MajorEvent.NONE;
    }
    
    public void updateInfo() {
    }
    
    static {
        EVENTS = new String[] { "BEAST", "SQUADS", "SPIRE", "PIZZA", "ROBBERY", "TDM" };
    }
    
    public enum MajorEvent
    {
        SQUADS, 
        SPIRE, 
        PIZZA, 
        ROBBERY, 
        TDM, 
        NONE;
    }
}
