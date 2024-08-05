package studio.dreamys.module.misc;

import net.minecraft.client.Minecraft;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import studio.dreamys.module.Category;
import studio.dreamys.module.Module;

import java.io.IOException;

public class WindowedFullscreen extends Module {
    public WindowedFullscreen() {
        super("WindowedFullscreen", Category.MISC);
    }

    @Override
    public void onEnable() throws IOException {
        super.onEnable();
        try {
            if (!Minecraft.getMinecraft().isFullScreen()) return;
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
            Display.setDisplayMode(Display.getDesktopDisplayMode());
            Display.setLocation(0, 0);
            Display.setFullscreen(false);
            Display.setResizable(false);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        super.onDisable();
        try {
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
            Display.setDisplayMode(new DisplayMode(Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight));
            Display.setResizable(true);
        } catch (LWJGLException e) {
            e.printStackTrace();
        }
    }
}
