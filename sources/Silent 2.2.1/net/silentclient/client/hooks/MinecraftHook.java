package net.silentclient.client.hooks;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;

import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;
import net.silentclient.client.mods.settings.RenderMod;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

public class MinecraftHook {
    
    public static void displayFix(CallbackInfo ci, boolean fullscreen, int displayWidth, int displayHeight) throws LWJGLException {
        Display.setFullscreen(false);
        if (fullscreen) {
            if (Client.getInstance().getModInstances() != null && RenderMod.isBorderlessFullScreen()) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
            } else {
                Display.setFullscreen(true);
                DisplayMode displaymode = Display.getDisplayMode();
                Minecraft.getMinecraft().displayWidth = Math.max(1, displaymode.getWidth());
                Minecraft.getMinecraft().displayHeight = Math.max(1, displaymode.getHeight());
            }
        } else {
            if (Client.getInstance().getModInstances() != null && RenderMod.isBorderlessFullScreen()) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
            } else {
                Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            }
        }

        Display.setResizable(false);
        Display.setResizable(true);

        ci.cancel();
    }
    
    public static void fullScreenFix(boolean fullscreen, int displayWidth, int displayHeight) throws LWJGLException {
        if (Client.getInstance().getModInstances() != null && RenderMod.isBorderlessFullScreen()) {
            if (fullscreen) {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "true");
                Display.setDisplayMode(Display.getDesktopDisplayMode());
                Display.setLocation(0, 0);
                Display.setFullscreen(false);
            } else {
                System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
                Display.setDisplayMode(new DisplayMode(displayWidth, displayHeight));
            }
        } else {
            Display.setFullscreen(fullscreen);
            System.setProperty("org.lwjgl.opengl.Window.undecorated", "false");
        }

        Display.setResizable(false);
        Display.setResizable(true);
    }
}