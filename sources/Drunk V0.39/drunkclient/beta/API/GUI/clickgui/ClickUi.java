/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  org.lwjgl.input.Mouse
 */
package drunkclient.beta.API.GUI.clickgui;

import com.google.common.collect.Lists;
import drunkclient.beta.API.GUI.clickgui.window.Window;
import drunkclient.beta.IMPL.Module.Type;
import java.io.IOException;
import java.util.ArrayList;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.input.Mouse;

public class ClickUi
extends GuiScreen {
    public static ArrayList<Window> windows = Lists.newArrayList();
    public double opacity = 0.0;
    public int scrollVelocity;
    public static boolean binding = false;
    public float lastPercent;
    public float percent;
    public float percent2;
    public float lastPercent2;
    public float outro;
    public float lastOutro;
    private boolean close = false;
    private boolean closed;

    public ClickUi() {
        if (!windows.isEmpty()) return;
        int x = 5;
        Type[] arrmoduleType = Type.values();
        int n = arrmoduleType.length;
        int n2 = 0;
        while (n2 < n) {
            Type c = arrmoduleType[n2];
            windows.add(new Window(c, x, 5));
            x += 105;
            ++n2;
        }
    }

    public float smoothTrans(double current, double last) {
        return (float)(current + (last - current) / (double)(Minecraft.debugFPS / 10));
    }

    @Override
    public void initGui() {
        super.initGui();
        this.percent = 1.33f;
        this.lastPercent = 1.0f;
        this.percent2 = 1.33f;
        this.lastPercent2 = 1.0f;
        this.outro = 1.0f;
        this.lastOutro = 1.0f;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.opacity = this.opacity + 10.0 < 200.0 ? (this.opacity = this.opacity + 10.0) : 200.0;
        GlStateManager.pushMatrix();
        ScaledResolution scaledRes = new ScaledResolution(this.mc);
        float scale = (float)scaledRes.getScaleFactor() / (float)Math.pow(scaledRes.getScaleFactor(), 2.0);
        ScaledResolution sr = new ScaledResolution(this.mc);
        float outro = this.smoothTrans(this.outro, this.lastOutro);
        if (this.mc.currentScreen == null) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0.0f);
            GlStateManager.scale(outro, outro, 0.0f);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0.0f);
        }
        this.percent = this.smoothTrans(this.percent, this.lastPercent);
        this.percent2 = this.smoothTrans(this.percent2, this.lastPercent2);
        if ((double)this.percent > 0.98) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0.0f);
            GlStateManager.scale(this.percent, this.percent, 0.0f);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0.0f);
        } else if (this.percent2 <= 1.0f) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0.0f);
            GlStateManager.scale(this.percent2, this.percent2, 0.0f);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0.0f);
        }
        if ((double)this.percent <= 1.5 && this.close) {
            this.percent = this.smoothTrans(this.percent, 2.0);
            this.percent2 = this.smoothTrans(this.percent2, 2.0);
        }
        if ((double)this.percent >= 1.4 && this.close) {
            this.percent = 1.5f;
            this.closed = true;
            this.mc.currentScreen = null;
        }
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
        if ((double)mouseX > 956.0 && (double)mouseX < 510.0 && (float)mouseY > 874.0f && (float)mouseY < 489.0f && mouseButton == 0) {
            System.out.println("te");
        }
        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
        if (!this.closed && keyCode == 1) {
            this.close = true;
            this.mc.mouseHelper.grabMouseCursor();
            this.mc.inGameHasSwider = true;
            return;
        }
        if (this.close) {
            this.mc.displayGuiScreen(null);
        }
        if (keyCode == 1 && !binding) {
            this.mc.displayGuiScreen(null);
            return;
        }
        windows.forEach(w -> w.key(typedChar, keyCode));
    }

    @Override
    public void onGuiClosed() {
        this.mc.entityRenderer.func_181022_b();
        this.mc.entityRenderer.isShaderActive();
    }

    public synchronized void sendToFront(Window window) {
        int panelIndex = 0;
        for (int i = 0; i < windows.size(); ++i) {
            if (windows.get(i) != window) continue;
            panelIndex = i;
            break;
        }
        Window t = windows.get(windows.size() - 1);
        windows.set(windows.size() - 1, windows.get(panelIndex));
        windows.set(panelIndex, t);
    }
}

