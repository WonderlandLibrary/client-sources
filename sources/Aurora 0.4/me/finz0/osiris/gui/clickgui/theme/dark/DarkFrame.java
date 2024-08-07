package me.finz0.osiris.gui.clickgui.theme.dark;

import me.finz0.osiris.gui.clickgui.base.Component;
import me.finz0.osiris.gui.clickgui.base.ComponentRenderer;
import me.finz0.osiris.gui.clickgui.base.ComponentType;
import me.finz0.osiris.gui.clickgui.elements.Frame;
import me.finz0.osiris.gui.clickgui.theme.Theme;
import me.finz0.osiris.module.modules.gui.ClickGuiModule;
import me.finz0.osiris.util.ColourUtils;
import me.finz0.osiris.util.GLUtils;
import me.finz0.osiris.util.MathUtils;
import me.finz0.osiris.util.OsirisTessellator;
import net.minecraft.client.gui.Gui;

import java.awt.*;

public class DarkFrame extends ComponentRenderer {

    public DarkFrame(Theme theme) {

        super(ComponentType.FRAME, theme);
    }

    @Override
    public void drawComponent(Component component, int mouseX, int mouseY) {

        Frame frame = (Frame) component;
        Dimension dimension = frame.getDimension();
        // GLUtils.glColor(new Color(0, 0, 0));

        if (frame.isMaximized()) {
            isMaximized(frame, dimension, mouseX, mouseY);
        }

        Gui.drawRect(frame.getX(), frame.getY(), frame.getX() + dimension.width, frame.getY() + 15, ClickGuiModule.getColor());
        // GLUtils.glColor(new Color(0, 0, 0));

        if (frame.isMaximizible()) {
            isMaximizible(frame, dimension, mouseX, mouseY);
        }

        //GLUtils.glColor(new Color(0, 0, 0));

        if (frame.isPinnable()) {
            isPinnable(frame, dimension, mouseX, mouseY);
        }
        //GLUtils.glColor(new Color(0, 0, 0));
        theme.fontRenderer.drawStringWithShadow(frame.getText(), frame.getX() + 4, MathUtils.getMiddle(frame.getY(), frame.getY() + 10) - (theme.fontRenderer.FONT_HEIGHT / 10) - 1, ColourUtils.color(1.0f, 1.0f, 1.0f, 1.0f));
        //GLUtils.glColor(new Color(0, 0, 0));

    }

    private void isPinnable(Frame frame, Dimension dimension, int mouseX, int mouseY) {
        Color color;
        if (mouseX >= frame.getX() + dimension.width - 38 && mouseY >= frame.getY() && mouseY <= frame.getY() + 16 && mouseX <= frame.getX() + dimension.width - 16) {
            color = new Color(255, 255, 255, 255);
        } else {
            color = new Color(50, 50, 50, 160);
        }
        //OsirisTessellator.drawRect(frame.getX() + dimension.width - 38, frame.getY(), frame.getX() + dimension.width - 19, frame.getY() + 15, color.hashCode());
        if (frame.isMaximized()) {
            OsirisTessellator.drawHLine(frame.getX(), frame.getX() + dimension.width, frame.getY(), 0x8C808080);
        } else {
            OsirisTessellator.drawHLine(frame.getX(), frame.getX() + dimension.width - 1, frame.getY(), 0x8C808080);
        }
        GLUtils.glColor(255, 255, 255, 255);

        if (!frame.isPinned()) {
            drawPin(MathUtils.getMiddle(frame.getX() + dimension.width - 43, frame.getX() + dimension.width - 30) + 1, frame.getY(), 13, true, new Color(158, 158, 158, 255).hashCode());
            drawPin(MathUtils.getMiddle(frame.getX() + dimension.width - 38, frame.getX() + dimension.width - 30) + 1, frame.getY() + 2, 12, false, new Color(255, 255, 255, 255).hashCode());
        } else {
            drawPin(MathUtils.getMiddle(frame.getX() + dimension.width - 43, frame.getX() + dimension.width - 30) + 1, frame.getY() + 1, 13, true, new Color(255, 255, 255, 255).hashCode());
//            OsirisTessellator.drawFilledCircle(MathUtils.getMiddle(frame.getX() + dimension.width - 38, frame.getX() + dimension.width - 19) + 1, MathUtils.getMiddle(frame.getY(), frame.getY() + 15) + 1, 4, new Color(255, 255, 255, 255).hashCode());
//            OsirisTessellator.drawFilledCircle(MathUtils.getMiddle(frame.getX() + dimension.width - 38, frame.getX() + dimension.width - 19) + 1, MathUtils.getMiddle(frame.getY(), frame.getY() + 15) + 1, 2, new Color(176, 176, 176, 255).hashCode());
        }
    }

    private void isMaximizible(Frame frame, Dimension dimension, int mouseX, int mouseY) {

        Color color;

        if (mouseX >= frame.getX() + dimension.width - 19 && mouseY >= frame.getY() && mouseY <= frame.getY() + 19 && mouseX <= frame.getX() + dimension.width) {
            color = new Color(255, 255, 255, 255);
        } else {
            color = new Color(155, 155, 155, 255);
        }

        theme.fontRenderer.drawStringWithShadow(frame.isMaximized() ? "-" : "+", frame.getX() + dimension.width - 12, frame.getY() + 3, color.getRGB());
    }

    private void isMaximized(Frame frame, Dimension dimension, int mouseX, int mouseY) {
        int mainColor = ClickGuiModule.isLight ? ColourUtils.color(255, 255, 255, 255) : ColourUtils.color(0, 0, 0, 255);
        int mainColorInv = ClickGuiModule.isLight ? ColourUtils.color(0, 0, 0, 255) : ColourUtils.color(255, 255, 255, 255);

        for (Component component : frame.getComponents()) {
            component.setxPos(frame.getX());
        }

        OsirisTessellator.drawRect(frame.getX(), frame.getY() + 1, frame.getX() + dimension.width, frame.getY() + dimension.height, mainColor);
        float height = 5;
        float maxHeight = 0;
        height = dimension.height - 16;

        for (Component component : frame.getComponents()) {
            maxHeight += component.getDimension().height;
        }
        float barHeight = height * (height / maxHeight);
        double y = (frame.getDimension().getHeight() - 16 - barHeight) * ((double) frame.getScrollAmmount() / (double) frame.getMaxScroll());
        y += frame.getY() + 16;
        frame.renderChildren(mouseX, mouseY);

        if (!(barHeight >= height)) {
            OsirisTessellator.drawRect((int) (frame.getX() + dimension.getWidth() - 1), (int) y, (int) (frame.getX() + frame.getDimension().getWidth()), (int) (y + barHeight), ClickGuiModule.getColor());
        }

    }

    @Override
    public void doInteractions(Component component, int mouseX, int mouseY) {
        Frame frame = (Frame) component;
        Dimension area = frame.getDimension();

        if (mouseX >= frame.getX() + area.width - 16 && frame.isMaximizible() && mouseY >= frame.getY() && mouseY <= frame.getY() + 16 && mouseX <= frame.getX() + area.width) {
            frame.setMaximized(!frame.isMaximized());
        }

        if (mouseX >= frame.getX() + area.width - 38 && mouseY >= frame.getY() && mouseY <= frame.getY() + 16 && mouseX <= frame.getX() + area.width - 16) {
            frame.setPinned(!frame.isPinned());
        }
    }


}