package fun.ellant.utils.text;

import fun.ellant.utils.render.ColorUtils;
import net.minecraft.util.text.*;

public class GradientUtil {

    public static StringTextComponent gradient(String message, int rgba, int rgbaed) {
        StringTextComponent text = new StringTextComponent("");
        for (int i = 0; i < message.length(); i++) {
            text.append(new StringTextComponent(String.valueOf(message.charAt(i))).setStyle(Style.EMPTY.setColor(new Color(ColorUtils.getColor(i)))));
        }
        return text;
    }

    public static IFormattableTextComponent white(String message) {
        return new StringTextComponent(message).setStyle(Style.EMPTY.setColor(Color.fromHex("#FFFFFF")));
    }


    public static StringTextComponent gradient(String message) {
        StringTextComponent text = new StringTextComponent("");
        for (int i = 0; i < message.length(); ++i) {
            text.append(new StringTextComponent(String.valueOf(message.charAt(i))).setStyle(Style.EMPTY.setColor(new Color(ColorUtils.getColor(i)))));
        }
        return text;
    }
}
