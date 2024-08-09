package fun.ellant.ui.midnight.component.impl;

import com.mojang.blaze3d.matrix.MatrixStack;
import fun.ellant.functions.api.Function;
import fun.ellant.functions.settings.Setting;
import fun.ellant.functions.settings.impl.*;
import fun.ellant.ui.midnight.ClickGui;
import fun.ellant.utils.client.KeyStorage;
import fun.ellant.utils.math.MathUtil;
import fun.ellant.utils.render.ColorUtils;
import fun.ellant.utils.render.DisplayUtils;
import fun.ellant.utils.render.Scissor;
import net.minecraft.util.math.vector.Vector4f;
import fun.ellant.utils.font.Fonts;


import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static fun.ellant.utils.render.DisplayUtils.reAlphaInt;


public class ModuleComponent extends Component {

    public Function function;

    public List<Component> components = new ArrayList<>();

    public ModuleComponent(Function function) {
        this.function = function;
        for (Setting<?> setting : function.getSettings()) {
            if (setting instanceof BooleanSetting bool) {
                components.add(new BooleanComponent(bool));
            }
            if (setting instanceof SliderSetting slider) {
                components.add(new SliderComponent(slider));
            }
            if (setting instanceof BindSetting bind) {
                components.add(new BindComponent(bind));
            }
            if (setting instanceof ModeSetting mode) {
                components.add(new ModeComponent(mode));
            }
            if (setting instanceof ModeListSetting mode) {
                components.add(new ListComponent(mode));
            }
            if (setting instanceof StringSetting string) {
                components.add(new TextComponent(string));
            }
            if (setting instanceof ColorSetting colorSetting) {
                components.add(new ColorComponent(colorSetting));
            }
        }
    }

    public float animationToggle;
    public static ModuleComponent binding;

    @Override
    public void drawComponent(MatrixStack matrixStack, int mouseX, int mouseY) {

        float totalHeight = 2;
        for (Component component : components) {
            if (component.setting != null && component.setting.visible.get()) {
                totalHeight += component.height;
            }
        }

        float off = 2f;

        components.forEach(c -> {
            c.function = function;
            c.parent = parent;
        });

        animationToggle = MathUtil.lerp(animationToggle, function.isState() ? 1 : 0, 10);
        DisplayUtils.drawShadow(x, y, width, height + totalHeight, 10, new Color(25, 25, 25).getRGB());
        DisplayUtils.drawRoundedRect(x, y, width, height + totalHeight, new Vector4f(3.5f, 3.5f, 3.5f, 3.5f), new Color(17, 18, 21).getRGB());

        DisplayUtils.drawRectW(x + 5, y + 23, width - 10, 0.5f, new Color(26, 28, 33).getRGB());
        Fonts.gilroy[14].drawString(matrixStack, function.getName(), x + 7.5f, y + 9f, new Color(57, 66, 72).getRGB());
        Fonts.gilroy[12].drawString(matrixStack, function.getDesc(), x + 7.5f, y + 18f, new Color(90, 90, 90).getRGB());

        String key = KeyStorage.getKey(function.getBind());

        if (binding == this && key != null) {
            DisplayUtils.drawRoundedRect(x + width - 20 - Fonts.gilroy[14].getWidth(key) + 5, y + 5, 10 + Fonts.gilroy[14].getWidth(key), 10, 2, new Color(17, 18, 21).brighter().getRGB());
            Fonts.gilroy[14].drawCenteredString(matrixStack, key, x + width - 20 - Fonts.gilroy[14].getWidth(key) + 5 + (10 + Fonts.gilroy[14].getWidth(key)) / 2, y + 9, -1);
        }

        int color = ColorUtils.interpolateColor(ColorUtils.IntColor.rgba(26, 29, 33, 255), ColorUtils.IntColor.rgba(74, 166, 218, 255), animationToggle);
        DisplayUtils.drawShadow(x + 5, y + 23 + off, 10, 10, 8, reAlphaInt(color, 50));
        DisplayUtils.drawRoundedRect(x + 5, y + 23 + off, 10, 10, 2f, color);
        Scissor.push();

        Scissor.setFromComponentCoordinates(x + 5, y + 23 + off, 10 * animationToggle, 10);
        Fonts.icons[12].drawString(matrixStack, "A", x + 7, y + 28 + off, -1);
        Scissor.unset();
        Scissor.pop();

        Fonts.gilroy[14].drawString(matrixStack, "Включен", x + 18f, y + 27f + off, -1);

        float offsetY = 0;
        for (Component component : components) {
            if (component.setting != null && component.setting.visible.get()) {
                component.setPosition(x, y + height + offsetY, width, 20);
                component.drawComponent(matrixStack, mouseX, mouseY);
                offsetY += component.height;
            }
        }

    }

    @Override
    public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
        if (MathUtil.isInRegion(mouseX, mouseY, x + 5, y + 20, width - 10, 17) && mouseButton <= 1) {
            function.toggle();
        }

        if (binding == this && mouseButton > 2) {
            function.setBind(-100 + mouseButton);
            binding = null;
        }

        if (MathUtil.isInRegion(mouseX, mouseY, x + 5, y, width - 10, 20)) {
            if (mouseButton == 2) {
                ClickGui.typing = false;
                binding = this;
            }
        }
        components.forEach(component -> component.mouseClicked(mouseX, mouseY, mouseButton));
    }

    @Override
    public void mouseReleased(int mouseX, int mouseY, int mouseButton) {
        components.forEach(component -> component.mouseReleased(mouseX, mouseY, mouseButton));
    }

    @Override
    public void keyTyped(int keyCode, int scanCode, int modifiers) {
        components.forEach(component -> component.keyTyped(keyCode, scanCode, modifiers));
    }

    @Override
    public void charTyped(char codePoint, int modifiers) {
        components.forEach(component -> component.charTyped(codePoint, modifiers));
    }
}
