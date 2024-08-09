package ru.FecuritySQ.module.визуальные;

import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;

public class NoRender extends Module {

    public OptionBoolList objects = new OptionBoolList("Что отключить",
            new OptionBoolean("Плохие эффекты", false),
            new OptionBoolean("Анимация при уроне", false),
            new OptionBoolean("Огонь", true),
            new OptionBoolean("БоссБар", false),
            new OptionBoolean("Скорбоард", false),
            new OptionBoolean("Голограммы", false));
    public NoRender() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
        addOption(objects);
    }

}
