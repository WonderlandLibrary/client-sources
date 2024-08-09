package ru.FecuritySQ.module.дисплей;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionColor;
import ru.FecuritySQ.option.imp.OptionMode;
import ru.FecuritySQ.option.imp.OptionNumric;
import ru.FecuritySQ.utils.ColorUtil;

import java.awt.*;

public class ClientOverlay extends Module {

    private static String[] modes = {"Астольфо", "Радуга",  "Свой двойной"};
    public static OptionMode colorMode = new OptionMode("Цвет клиента ", modes, 0);
    public static OptionColor customColor = new OptionColor("Свой цвет #1", new Color(0, 128, 255));
    public static OptionColor customColor2 = new OptionColor("Свой цвет #2", new Color(0, 128, 255));
    public static OptionNumric speed = new OptionNumric("Скорость цвета", 1,1, 10, 1);
    public ClientOverlay() {
        super(Category.Дисплей, GLFW.GLFW_KEY_0);
        addOption(colorMode);
        addOption(customColor);
        addOption(customColor2);
        addOption(speed);
    }

    public static Color getColor(){
        if(colorMode.get().equalsIgnoreCase("Астольфо")){
            return ColorUtil.astolfo(0,  0, 0.6f, 5);
        }
        if(colorMode.get().equalsIgnoreCase("Радуга")){
            return ColorUtil.rainbow(10, 0, 1, 1, 255);
        }

        if (colorMode.get().equalsIgnoreCase("Свой двойной")) {
            return ColorUtil.TwoColorEffect(customColor.get(), customColor2.get(), speed.get());
        }

        return new Color(255, 255, 255);
    }

    public static Color getColor(int offset){
        if(colorMode.get().equalsIgnoreCase("Астольфо")){
            return ColorUtil.astolfo(0,  offset, 0.6f, 5);
        }
        if(colorMode.get().equalsIgnoreCase("Радуга")){
            return ColorUtil.rainbow(10, offset, 1, 1, 1f);
        }
        if (colorMode.get().equalsIgnoreCase("Свой двойной")) {
            return ColorUtil.TwoColorEffect(customColor.get(), customColor2.get(), speed.get());
        }
        return new Color(255, 255, 255);
    }

}
