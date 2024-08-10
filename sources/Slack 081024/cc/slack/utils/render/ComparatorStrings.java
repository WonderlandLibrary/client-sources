package cc.slack.utils.render;

import cc.slack.utils.font.Fonts;
import net.minecraft.client.Minecraft;

import java.util.Comparator;

public class ComparatorStrings implements Comparator<String> {

    @Override
    public int compare(String o1, String o2) {
        if (Fonts.poppins18.getStringWidth(o1) <=Fonts.poppins18.getStringWidth(o2)) {
            return 1;
        } else if (Fonts.poppins18.getStringWidth(o1) >= Fonts.poppins18.getStringWidth(o2)) {
            return -1;
        } else {
            return 0;
        }
    }
}
