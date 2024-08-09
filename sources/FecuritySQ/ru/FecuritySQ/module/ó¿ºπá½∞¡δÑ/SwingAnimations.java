package ru.FecuritySQ.module.визуальные;

import com.mojang.blaze3d.matrix.MatrixStack;
import org.lwjgl.glfw.GLFW;
import ru.FecuritySQ.module.Module;
import ru.FecuritySQ.option.imp.OptionBoolList;
import ru.FecuritySQ.option.imp.OptionBoolean;
import ru.FecuritySQ.option.imp.OptionMode;
import ru.FecuritySQ.option.imp.OptionNumric;

public class SwingAnimations extends Module {
    public OptionNumric leftx = new OptionNumric("Левая рука X", 0F, -3F, 3F, 0.1F);
    public OptionNumric lefty = new OptionNumric("Левая рука Y", 0F, -3F, 3F, 0.1F);
    public OptionNumric leftz = new OptionNumric("Левая рука Z", 0F, -3F, 3F, 0.1F);
    public OptionNumric rightx = new OptionNumric("Правая рука X", 0F, -3F, 3F, 0.1F);
    public OptionNumric righty = new OptionNumric("Правая рука Y", 0F, -3F, 3F, 0.1F);
    public OptionNumric rightz = new OptionNumric("Правая рука Z", 0F, -3F, 3F, 0.1F);
    String[] modes = {"Стандарт", "Тапы", "Блок", "Сворачивание",  "Шлепки", "Сдвиг", "Ломание"};
    public OptionMode mode = new OptionMode("Режим анимации", modes, 5);
    public SwingAnimations() {
        super(Category.Визуальные, GLFW.GLFW_KEY_0);
        addOption(mode);
        addOption(leftx);
        addOption(lefty);
        addOption(leftz);
        addOption(rightx);
        addOption(righty);
        addOption(rightz);
    }
    public void translate(MatrixStack stack, Float x, Float y, Float z, Float sideMultiplier) {
        stack.translate(x * sideMultiplier, y, -z);
    }
}
