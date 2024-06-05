package club.mega.gui;

import club.mega.Mega;
import club.mega.event.impl.EventKey;
import club.mega.event.impl.EventRender2D;
import club.mega.module.Category;
import club.mega.module.Module;
import club.mega.module.impl.hud.TabGui;
import club.mega.util.AnimationUtil;
import club.mega.util.ColorUtil;
import club.mega.util.RenderUtil;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import java.awt.*;
import java.util.Arrays;

public final class TabGUI {

    private final double x = 4, y = 25, width = 66, height = 20;
    private double current, current2, target, target2;
    private int index = 0, mIndex = 0;
    private boolean open = false;

    private Category selectedCategory = Category.COMBAT;
    private Module selectedModule;

    public void renderTabGUI(final EventRender2D event) {
        double offset = y;
        current = AnimationUtil.animate(current, target, 1);
        current2 = AnimationUtil.animate(current2, target2, 1);

        if (Mega.INSTANCE.getModuleManager().getModule(TabGui.class).blur.get()) RenderUtil.drawBlurredRect(x, y, width, getHeight(Category.HUD) - 4, new Color(130,130,130));
        for (final Category category : Category.values())
        {
            if (!Mega.INSTANCE.getModuleManager().getModule(TabGui.class).blur.get()) RenderUtil.drawRect(x, offset, width, height,new Color(1,1,1,140));
            RenderUtil.drawRect(x, offset, 3, height,new Color(255, 255, 255, 180));
            if (selectedCategory == category) {
                target = offset;
            }
            Mega.INSTANCE.getFontManager().getFont(selectedCategory == category ? "Roboto bold 23" : "Roboto medium 19").drawString(category.getName(), x + 8, offset + 3, -1);
            offset += height;
        }
        RenderUtil.drawRect(x, current, 3, height, ColorUtil.getMainColor());

        GL11.glPushMatrix();
        RenderUtil.prepareScissorBox(x + width + 3, getHeight(selectedCategory), current2, getModuleHeight(selectedCategory));
        GL11.glEnable(GL11.GL_SCISSOR_TEST);
        offset = getHeight(selectedCategory);
        if (Mega.INSTANCE.getModuleManager().getModule(TabGui.class).blur.get())
        RenderUtil.drawBlurredRect(x + width + 3, offset, width, getModuleHeight(selectedCategory), new Color(130,130,130));
        for (final Module module : Mega.INSTANCE.getModuleManager().getModules(selectedCategory))
        {
            if (!Mega.INSTANCE.getModuleManager().getModule(TabGui.class).blur.get()) RenderUtil.drawRect(x + width + 3, offset, width, 13,new Color(1,1,1,140));
            RenderUtil.drawRect(x + width + 3, offset, 3, 13,new Color(255, 255, 255, 180));
            if (selectedModule == module)
                RenderUtil.drawRect(x + width + 3, offset, 3, 13, ColorUtil.getMainColor());
            Mega.INSTANCE.getFontManager().getFont(selectedModule == module ? "Roboto bold 23" : "Roboto medium 19").drawString(module.getName(), x + width + 11, offset + 1, module.isToggled() ? ColorUtil.getMainColor().getRGB() : -1);
            offset += 13;
        }
        GL11.glDisable(GL11.GL_SCISSOR_TEST);
        GL11.glPopMatrix();
    }

    public void keyTyped(final EventKey event) {
        switch (event.getKey())
        {
            case Keyboard.KEY_UP:
                if (open)
                    mIndex--;
                else
                    index--;
                break;
            case Keyboard.KEY_DOWN:
                if (open)
                    mIndex++;
                else
                    index++;
                break;
            case Keyboard.KEY_LEFT:
            case Keyboard.KEY_BACK:
                close();
                break;
            case Keyboard.KEY_RIGHT:
            case Keyboard.KEY_RETURN:
                if (open)
                    selectedModule.toggle();
                else
                    open();
                break;
        }
        if (index > Category.values().length - 1)
            index = 0;
        if (index < 0)
            index = Category.values().length - 1;
        selectedCategory = Category.values()[index];

        if (mIndex > Mega.INSTANCE.getModuleManager().getModules(selectedCategory).size() - 1)
            mIndex = 0;
        if (mIndex < 0)
            mIndex = Mega.INSTANCE.getModuleManager().getModules(selectedCategory).size() - 1;

        selectedModule = Mega.INSTANCE.getModuleManager().getModules(selectedCategory).get(mIndex);
    }

    private void open() {
        mIndex = 0;
        target2 = width;
        open = true;
    }

    private void close() {
        target2 = 0;
        open = false;
    }

    private double getHeight(final Category category) {
        double offset = y;
        for (int i = 1; i <= Arrays.asList(Category.values()).indexOf(category); i++)
        {
            offset += height;
        }
        return offset;
    }

    private double getModuleHeight(final Category category) {
        double offset = 0;
        for (int i = 1; i <= Mega.INSTANCE.getModuleManager().getModules(category).size(); i++)
        {
            offset += 13;
        }
        return offset;
    }

}
