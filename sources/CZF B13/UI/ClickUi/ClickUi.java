package gq.vapu.czfclient.UI.ClickUi;

import com.google.common.collect.Lists;
import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.Manager.ModuleManager;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Module.Modules.Render.ClickGui;
import gq.vapu.czfclient.Util.ClientUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.io.IOException;
import java.util.ArrayList;

public class ClickUi extends GuiScreen {
    public static ArrayList<Window> windows = Lists.newArrayList();
    public static boolean binding = false;
    public double opacity = 0.0;
    public int scrollVelocity;

    public ClickUi() {
        if (windows.isEmpty()) {
            int x = 5;
            ModuleType[] arrmoduleType = ModuleType.values();
            int n = arrmoduleType.length;
            int n2 = 0;
            while (n2 < n) {
                ModuleType c = arrmoduleType[n2];
                windows.add(new Window(c, x, 5));
                x += 105;
                ++n2;
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.opacity = this.opacity + 10.0 < 200.0 ? (this.opacity += 10.0) : 200.0;
        Gui.drawRect(0.0, 0.0, Display.getWidth(), Display.getHeight(), ClientUtil.reAlpha(1, 0.3F));// ±³¾°
        ScaledResolution res = new ScaledResolution(this.mc);
        GlStateManager.pushMatrix();
        ScaledResolution scaledRes = new ScaledResolution(this.mc);
        float scale = (float) scaledRes.getScaleFactor() / (float) Math.pow(scaledRes.getScaleFactor(), 2.0);
        windows.forEach(w -> w.render(mouseX, mouseY));
        GlStateManager.popMatrix();
        if (Mouse.hasWheel()) {
            int wheel = Mouse.getDWheel();
            this.scrollVelocity = wheel < 0 ? -120 : (wheel > 0 ? 120 : 0);
        }
        windows.forEach(w -> w.mouseScroll(mouseX, mouseY, this.scrollVelocity));
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        windows.forEach(w -> w.click(mouseX, mouseY, mouseButton));
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @EventHandler
    public void onTick() {
//		this.mc.displayGuiScreen(new ClickUi());

    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (keyCode == 1 && !binding) {
            this.mc.displayGuiScreen(null);
            return;
        }
        windows.forEach(w -> w.key(typedChar, keyCode));
    }

    public void initGui() {
        this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/blur.json"));
        //BlurUtil.blurAll(170, 170);
        super.initGui();
    }

    public void onGuiClosed() {
        //this.mc.entityRenderer.c();
        //BlurUtil.blurAll(0);
        this.mc.entityRenderer.isShaderActive();
        this.mc.entityRenderer.loadShader(new ResourceLocation("shaders/post/fxaa.json"));
        ModuleManager.getModuleByClass(ClickGui.class).setEnabled(false);
    }

    public synchronized void sendToFront(Window window) {
        int panelIndex = 0;
        int i = 0;
        while (i < windows.size()) {
            if (windows.get(i) == window) {
                panelIndex = i;
                break;
            }
            ++i;
        }
        Window t = windows.get(windows.size() - 1);
        windows.set(windows.size() - 1, windows.get(panelIndex));
        windows.set(panelIndex, t);
    }
}
