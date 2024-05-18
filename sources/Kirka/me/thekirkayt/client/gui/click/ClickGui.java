/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.gui.click;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import me.thekirkayt.client.gui.click.component.Console;
import me.thekirkayt.client.gui.click.component.window.Handle;
import me.thekirkayt.client.gui.click.component.window.ModuleWindow;
import me.thekirkayt.client.gui.click.component.window.Window;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.client.module.ModuleManager;
import me.thekirkayt.client.option.OptionManager;
import me.thekirkayt.utils.ClientUtils;
import me.thekirkayt.utils.FileUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

public class ClickGui
extends GuiScreen {
    private static final File GUI_DIR = FileUtils.getConfigFile("Gui");
    private static final float SCALE = 2.0f;
    private CopyOnWriteArrayList<ModuleWindow> windows = new CopyOnWriteArrayList();
    private static ClickGui instance;
    private static Console console;
    private boolean binding;

    public static void start() {
        instance = new ClickGui();
    }

    private ClickGui() {
        double x = 20.0;
        double y = 50.0;
        double width = 0.0;
        for (Module.Category category : Module.Category.values()) {
            width = Math.max((double)ClientUtils.clientFont().getStringWidth(category.name()) * 1.1, width);
            for (Module module : ModuleManager.getModules()) {
                if (!module.getCategory().equals((Object)category)) continue;
                width = Math.max(width, (double)ClientUtils.clientFont().getStringWidth(module.getDisplayName()));
            }
            ModuleWindow window = new ModuleWindow(category, x, y, width);
            this.windows.add(window);
            x += 100.0;
        }
        this.load();
        this.save();
        console = new Console();
    }

    public void load() {
        List<String> fileContent = FileUtils.read(GUI_DIR);
        for (String line : fileContent) {
            String[] split = line.split(":");
            String windowName = split[0];
            String windowPosX = split[1];
            String windowPosY = split[2];
            String windowExtended = split[3];
            ModuleWindow window = this.getWindow(windowName);
            int posX = Integer.parseInt(windowPosX);
            int posY = Integer.parseInt(windowPosY);
            Boolean extended = Boolean.parseBoolean(windowExtended);
            window.setStartOffset(new double[]{0.0, 0.0});
            window.setDragging(true);
            if (window.getHandle() != null) {
                window.getHandle().drag(posX, posY, 0);
            }
            window.setDragging(false);
            window.setExtended(extended);
        }
    }

    public void save() {
        ArrayList<String> fileContent = new ArrayList<String>();
        for (ModuleWindow window : this.windows) {
            String windowName = ((Module.Category)((Object)window.getParent())).name();
            String windowPosX = "" + (int)window.getX();
            String windowPosY = "" + (int)window.getY();
            String windowExtended = Boolean.toString(window.isExtended());
            fileContent.add(String.format("%s:%s:%s:%s", windowName, windowPosX, windowPosY, windowExtended));
        }
        FileUtils.write(GUI_DIR, fileContent, true);
    }

    private ModuleWindow getWindow(String name) {
        for (ModuleWindow window : this.windows) {
            if (!((Module.Category)((Object)window.getParent())).name().equalsIgnoreCase(name)) continue;
            return window;
        }
        return null;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        GlStateManager.pushMatrix();
        ScaledResolution scaledRes = new ScaledResolution(this.mc, this.mc.displayWidth, this.mc.displayHeight);
        float scale = (float)scaledRes.getScaleFactor() / (float)Math.pow(scaledRes.getScaleFactor(), 2.0) * 2.0f;
        scale = (float)((double)scale / (1920.0 / (double)Display.getWidth()));
        GlStateManager.scale(scale, scale, scale);
        int realMouseX = (int)Math.round((double)((float)Mouse.getX() / 2.0f) * (1920.0 / (double)Display.getWidth()));
        int realMouseY = (int)Math.round((double)((float)(Display.getHeight() - Mouse.getY()) / 2.0f) * (1920.0 / (double)Display.getWidth()));
        for (Window window : this.windows) {
            window.draw(realMouseX, realMouseY);
        }
        GlStateManager.popMatrix();
        GlStateManager.pushMatrix();
        GlStateManager.scale(1.0f, scale, 1.0f);
        console.draw(realMouseX, realMouseY);
        GlStateManager.popMatrix();
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        int realMouseX = (int)Math.round((double)((float)Mouse.getX() / 2.0f) * (1920.0 / (double)Display.getWidth()));
        int realMouseY = (int)Math.round((double)((float)(Display.getHeight() - Mouse.getY()) / 2.0f) * (1920.0 / (double)Display.getWidth()));
        for (Window window : this.getWindows()) {
            window.click(realMouseX, realMouseY, mouseButton);
        }
        this.save();
    }

    @Override
    protected void mouseClickMove(int mouseX, int mouseY, int clickedMouseButton, long timeSinceLastClick) {
        int realMouseX = (int)Math.round((double)((float)Mouse.getX() / 2.0f) * (1920.0 / (double)Display.getWidth()));
        int realMouseY = (int)Math.round((double)((float)(Display.getHeight() - Mouse.getY()) / 2.0f) * (1920.0 / (double)Display.getWidth()));
        for (Window window : this.windows) {
            if (window.getHandle() == null) continue;
            window.getHandle().drag(realMouseX, realMouseY, clickedMouseButton);
        }
        this.save();
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        int realMouseX = (int)Math.round((double)((float)Mouse.getX() / 2.0f) * (1920.0 / (double)Display.getWidth()));
        int realMouseY = (int)Math.round((double)((float)(Display.getHeight() - Mouse.getY()) / 2.0f) * (1920.0 / (double)Display.getWidth()));
        for (Window window : this.windows) {
            window.release(realMouseX, realMouseY, state);
        }
        this.save();
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (!this.binding && !console.keyType(keyCode, typedChar)) {
            super.keyTyped(typedChar, keyCode);
        }
        for (Window window : this.windows) {
            window.keyPress(keyCode, typedChar);
        }
    }

    @Override
    public void onGuiClosed() {
        ModuleManager.save();
        OptionManager.save();
        super.onGuiClosed();
    }

    public Window getTopWindow(int mouseX, int mouseY) {
        ArrayList<ModuleWindow> hoveringWindows = new ArrayList<ModuleWindow>();
        for (ModuleWindow window : this.windows) {
            if (!window.hovering(mouseX, mouseY) && !window.getHandle().hovering(mouseX, mouseY)) continue;
            hoveringWindows.add(window);
        }
        if (!hoveringWindows.isEmpty()) {
            ModuleWindow lastWindow = (ModuleWindow)hoveringWindows.get(hoveringWindows.size() - 1);
            this.windows.remove(lastWindow);
            this.windows.add(lastWindow);
            return lastWindow;
        }
        return null;
    }

    public static ClickGui getInstance() {
        return instance;
    }

    public CopyOnWriteArrayList<ModuleWindow> getWindows() {
        return this.windows;
    }

    public boolean isBinding() {
        return this.binding;
    }

    public void setBinding(boolean binding) {
        this.binding = binding;
    }
}

