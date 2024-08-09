package src.Wiksi.utils.text;

import src.Wiksi.functions.impl.render.Trails;
import src.Wiksi.utils.render.ColorUtils;

import net.minecraft.util.text.*;

public class GradientUtil {


    public static StringTextComponent gradient(String message) {

        StringTextComponent text = new StringTextComponent("");
        for (int i = 0; i < message.length(); i++) {
            text.append(new StringTextComponent(String.valueOf(message.charAt(i))).setStyle(Style.EMPTY.setColor(new Color(ColorUtils.getColor(i)))));
        }

        return text;

    }

    public static IFormattableTextComponent white(String message) {
        return new StringTextComponent(message).setStyle(Style.EMPTY.setColor(Color.fromHex("#FFFFFF")));
    }

    public static Object gradient(Trails.Point point) {
        return null;
    }

    public static ITextComponent gradient(String ftSchedules, int rgba, int rgba1) {
        return null;
    }
}
