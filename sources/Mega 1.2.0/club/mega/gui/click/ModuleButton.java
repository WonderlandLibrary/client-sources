package club.mega.gui.click;

import club.mega.Mega;
import club.mega.gui.click.components.*;
import club.mega.gui.click.components.TextComponent;
import club.mega.module.Module;
import club.mega.module.setting.Setting;
import club.mega.module.setting.impl.*;
import club.mega.util.*;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ModuleButton {

    private double x, y, width, height;
    private final Module module;
    private final Panel parent;
    private boolean expand = false;
    private final List<Component> components = new ArrayList<>();

    public ModuleButton(final Module module, final Panel parent, double width, double height) {
        this.module = module;
        this.parent = parent;
        this.width = width;
        this.height = height;
    }

    public final void init() {
        components.clear();
    }

    public final void drawButton(final int mouseX, final int mouseY) {
        if (components.isEmpty() && Mega.INSTANCE.getClickGUI().getCurrent() == 1)
        for (final Setting setting : module.getSettings())
        {
            if (setting instanceof TextSetting)
                components.add(new TextComponent((TextSetting) setting, width, height));
            if (setting instanceof ListSetting)
                components.add(new ListComponent((ListSetting) setting, width, height));
            if (setting instanceof BooleanSetting)
                components.add(new BooleanComponent((BooleanSetting) setting, width, height));
            if (setting instanceof NumberSetting)
                components.add(new NumberComponent((NumberSetting) setting, width, height));
        }

        RenderUtil.drawRect(x, y, width, height, module.isToggled() ? ColorUtil.getMainColor() : new Color(30,30,30));
        Mega.INSTANCE.getFontManager().getFont("Arial 21").drawString(module.getName(), x + 5, y + 5, -1);
        if (!module.getSettings().isEmpty())
            Mega.INSTANCE.getFontManager().getFont("Arial 27").drawString(expand ? "-" : "+", x + width - 18, expand ? y + 3 : y + 4, -1);

        Component prevComponent = null;
        for (final Component component : components)
        {
            if (!component.getSetting().isVisible())
                continue;

            component.setX(x);
            if (prevComponent == null)
                component.setY(y + height);
            else
                component.setY(prevComponent.getY() + prevComponent.getHeight());


            component.drawComponent(mouseX, mouseY);
            prevComponent = component;
            RenderUtil.drawGradientRect(x, y + height, width, 3, new Color(1, 1, 1), new Color(30, 30, 30, 0));
            if (expand)
                RenderUtil.drawGradientRect(x, y + height - 3 + getFinalSettingHeight(), width, 3, new Color(30, 30, 30, 0), new Color(1, 1, 1));
        }
    }

    public final void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (expand) {
            for (final Component component : components)
            {
                if (!component.getSetting().isVisible())
                    continue;

                component.mouseClicked(mouseX, mouseY, mouseButton);
            }
        }
    }

    public final void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        if (MouseUtil.isInside(mouseX, mouseY, x, y, width, height)) {
            if (mouseButton == 0)
                module.toggle();
            if (mouseButton == 1 && !module.getSettings().isEmpty())
                expand = !expand;

        }

        if (expand) {
            for (final Component component : components)
            {
                if (!component.getSetting().isVisible())
                    continue;

                component.mouseReleased(mouseX, mouseY, mouseButton);
            }
        }
    }

    public final void keyTyped(final char typedChar, final int keyCode) throws IOException
    {
       components.forEach(component -> {
           try {
               component.keyTyped(typedChar, keyCode);
           } catch (IOException e) {
               e.printStackTrace();
           }
       });
    }

    public final Module getModule() {
        return module;
    }

    public final double getX() {
        return x;
    }

    public final void setX(final double x) {
        this.x = x;
    }

    public final double getY() {
        return y;
    }

    public final void setY(final double y) {
        this.y = y;
    }

    public final double getWidth() {
        return width;
    }

    public final void setWidth(final double width) {
        this.width = width;
    }

    public final double getHeight() {
        return height;
    }

    public final void setHeight(final double height) {
        this.height = height;
    }

    public final boolean isExpand() {
        return expand;
    }

    public final double getFinalHeight() {
        double start = getY() + getHeight();
        if (!module.getSettings().isEmpty() && expand && Mega.INSTANCE.getClickGUI().getCurrent() == 1)
        for (final Component component : components)
        {
            if (!component.getSetting().isVisible())
                continue;

            start += 20;
        }
        return start;
    }

    public final double getFinalSettingHeight() {
        double start = 0;
        if (!module.getSettings().isEmpty() && expand)
            for (final Component component : components)
            {
                if (!component.getSetting().isVisible())
                    continue;

                start += 20;
            }
        return start;
    }

}
