package me.dinozoid.strife.util.ui;

import me.dinozoid.strife.util.MinecraftUtil;
import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import java.awt.*;


public class WindowedFullscreen extends MinecraftUtil {

    public static boolean apply() {
        mc.fullscreen = !mc.fullscreen;

        boolean grabbed = Mouse.isGrabbed();
        if (grabbed)
            Mouse.setGrabbed(false);
        try {
            DisplayMode displayMode = Display.getDesktopDisplayMode();
            if (mc.fullscreen) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(displayMode);

                Display.setLocation(0, 0);
                Display.setFullscreen(false);
                Display.setResizable(false);
            } else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                displayMode = new DisplayMode(mc.tempDisplayWidth, mc.tempDisplayHeight);
                Display.setDisplayMode(displayMode);
                Display.setResizable(true);
                Dimension dimension = Toolkit.getDefaultToolkit().getScreenSize();
                int x = (int) ((dimension.getWidth() - Display.getWidth()) / 2);
                int y = (int) ((dimension.getHeight() - Display.getHeight()) / 2);
                Display.setLocation(x, y);
            }

            mc.displayWidth = displayMode.getWidth();
            mc.displayHeight = displayMode.getHeight();
            if (mc.currentScreen != null) {
                mc.resize(mc.displayWidth, mc.displayHeight);
            } else {
                mc.updateFramebufferSize();
            }
            mc.updateDisplay();
            Mouse.setCursorPosition((Display.getX() + Display.getWidth()) / 2, (Display.getY() + Display.getHeight()) / 2);
            if (grabbed)
                Mouse.setGrabbed(true);
            return true;
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
        return false;
    }

}
