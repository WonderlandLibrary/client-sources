package fr.dog.theme;

import fr.dog.util.render.ColorUtil;
import fr.dog.util.render.RenderUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import net.minecraft.util.EnumChatFormatting;

import java.awt.*;

@Getter
@AllArgsConstructor
public class Theme {
    public String name;
    public EnumChatFormatting chatFormatting;
    public Color color1;
    public Color color2;
    public boolean isRainbow;

    public Color getColor(int time, int offset){
        if (!isRainbow){
            return ColorUtil.getColorFromIndex(time, offset, color1, color2, false);
        } else{
            return ColorUtil.getRainbow(time, .75f, .75f, offset);
        }
    }

    public Color getColor1() {
        if (isRainbow)
            return getColor(3, 0);

        return color1;
    }

    public Color getColor2() {
        if (isRainbow)
            return getColor(3, 250);

        return color2;
    }
}
