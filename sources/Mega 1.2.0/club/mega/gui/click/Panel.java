package club.mega.gui.click;

import club.mega.Mega;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.impl.hud.ClickGui;
import club.mega.module.setting.Setting;
import club.mega.util.*;
import net.minecraft.client.Minecraft;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class Panel {

    private double x, y, width, height, dragX, dragY, scrollOffset = 0, current = 0;
    private boolean expand = true, dragging = false;
    private final Category category;
    private final ArrayList<ModuleButton> moduleButtons = new ArrayList<>();

    public Panel(final Category category) {
        this.category = category;
    }

    public final void init() {
        for (final ModuleButton moduleButton : moduleButtons)
        {
            moduleButton.init();
        }
    }

    public final void drawScreen(final int mouseX, final int mouseY) {
        handleScrolling(mouseX, mouseY, x, getY() + 23, width, height - 23);

        if (Mega.INSTANCE.getClickGUI().getCurrent() != 1) {
            height = getRealHeight();
        } else
        height = AnimationUtil.animate(height, getRealHeight(), Mega.INSTANCE.getModuleManager().getModule(ClickGui.class).animationSpeed.getAsDouble());

        if (dragging) {
            x = mouseX - dragX;
            y = mouseY - dragY;
        }

        ModuleButton prevModuleButton = null;
        GL11.glPushMatrix();
        if (Mega.INSTANCE.getClickGUI().getCurrent() == 1) {
            RenderUtil.prepareScissorBox(x, getY() + (Minecraft.getMinecraft().gameSettings.fullScreen ? -34 : 0), width, height + (Minecraft.getMinecraft().gameSettings.fullScreen ? 34 + 23 : 23));
            GL11.glEnable(GL11.GL_SCISSOR_TEST);
        }

        RenderUtil.drawRect(x, getY(), width, 23, ColorUtil.getMainColor());
        RenderUtil.drawRect(x, getY(), 26, 23, new Color(1,1,1,50));
        Mega.INSTANCE.getFontManager().getFont("Roboto bold 25").drawString(category.getName(), x + width - Mega.INSTANCE.getFontManager().getFont("Roboto bold 25").getWidth(category.getName()) - 5, getY() + 5, -1);
        Mega.INSTANCE.getFontManager().getFont("ICON 35").drawString(category.getIcon(), x + 3, getY() + 4, -1);

       for (final ModuleButton moduleButton : moduleButtons)
        {
            if (prevModuleButton != null) {
                if (dragging || getHeight() == getRealHeight()) {
                    moduleButton.setY(prevModuleButton.getFinalHeight());
                } else {
                    if (moduleButton.getY() < prevModuleButton.getFinalHeight())
                        moduleButton.setY(AnimationUtil.animate(moduleButton.getY(), prevModuleButton.getFinalHeight(), Mega.INSTANCE.getModuleManager().getModule(ClickGui.class).animationSpeed.getAsDouble()));
                    if (!prevModuleButton.isExpand() && moduleButton.getY() > prevModuleButton.getY() + prevModuleButton.getHeight())
                        moduleButton.setY(AnimationUtil.animate(moduleButton.getY(), prevModuleButton.getY() + prevModuleButton.getHeight(), Mega.INSTANCE.getModuleManager().getModule(ClickGui.class).animationSpeed.getAsDouble() ));
                }
            }
            else
                moduleButton.setY(getY() + 23);
            moduleButton.setX(x);
            moduleButton.drawButton(mouseX, mouseY);
            prevModuleButton = moduleButton;
        }

        if (!moduleButtons.isEmpty() && height > 0)
            if (moduleButtons.get(0).getModule().isToggled())
                RenderUtil.drawGradientRect(x, getY() + 23, width, 3, new Color(5, 5, 5, 150), new Color(30, 30, 30, 0));
            else
                RenderUtil.drawGradientRect(x, getY() + 23, width, 3, new Color(1, 1, 1), new Color(1, 1, 1,0));

        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();

        if (Mega.INSTANCE.getModuleManager().getModule(ClickGui.class).debug.get()) {
            RenderUtil.drawRect(x, getY() + 23 + height, width, getRealHeight() - height , new Color(0,255, 0, 130));
            RenderUtil.drawRect(x, getY() + 23, width, height, new Color(255, 0, 0, 130));
        }
    }

    public final void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) {
        if (MouseUtil.isInside(mouseX, mouseY, x, getY(), width, 23)) {
            if (mouseButton == 0) {
                dragging = true;
                dragX = mouseX - x;
                dragY = mouseY - y;
            }

        }

        if (expand)
        for (final ModuleButton moduleButton : moduleButtons)
        {
            moduleButton.mouseClicked(mouseX, mouseY, mouseButton);
        }
    }

    public final void mouseReleased(final int mouseX, final int mouseY, final int mouseButton) {
        if (mouseButton == 0)
        dragging = false;

        if (MouseUtil.isInside(mouseX, mouseY, x, y, width, 23) && !dragging) {
            if (mouseButton == 1) {
                expand = !expand;
                if (expand)
                    height = 0;
            }
        }

        if (expand)
        for (final ModuleButton moduleButton : moduleButtons)
        {
            moduleButton.mouseReleased(mouseX, mouseY, mouseButton);
        }
    }

    public final void keyTyped(final char typedChar, final int keyCode) throws IOException
    {
        moduleButtons.forEach(moduleButton -> {
            try {
                moduleButton.keyTyped(typedChar, keyCode);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public final double getX() {
        return x;
    }

    public final void setX(final double x) {
        this.x = x;
    }

    public final double getY() {
        return y - scrollOffset;
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

    public final void addHeight(final double amount) {
        height += amount;
    }

    public final ArrayList<ModuleButton> getModuleButtons() {
        return moduleButtons;
    }

    public final void add(final Module module, final double height) {
        getModuleButtons().add(new ModuleButton(module, this, getWidth(), height));
    }

    private double getRealHeight() {
        double start = 0;
        if (expand)
        for (final ModuleButton moduleButton : moduleButtons)
        {
            start += 20;
            if (moduleButton.isExpand() && Mega.INSTANCE.getClickGUI().getCurrent() == 1)
                for (final Setting setting : moduleButton.getModule().getSettings())
                {
                    if (!setting.isVisible())
                        continue;

                    start += 20;
                }

        }
        return start;
    }

    private void handleScrolling(final int mouseX, final int mouseY, final double x, final double y, final double width, final double height) {
        if (Mouse.hasWheel() && MouseUtil.isInside(mouseX, mouseY, x, y, width, height)) {
            int wheel = Mouse.getDWheel();
            if (wheel < 0) {
                current += 30;
                if (this.current < 0) {
                    this.current = 0;
                }
            } else if (wheel > 0) {
                this.current -= 30;
                if (this.current < 0) {
                    this.current = 0;
                }
            }
        }
        this.scrollOffset = AnimationUtil.animate(scrollOffset, current,  Mega.INSTANCE.getModuleManager().getModule(ClickGui.class).animationSpeed.getAsDouble());
    }

}
