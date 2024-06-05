package Focus.Beta.API.GUI.clickgui;

import Focus.Beta.UTILS.render.RenderUtil;
import com.google.common.collect.Lists;

import Focus.Beta.IMPL.Module.Type;
import Focus.Beta.IMPL.Module.impl.render.HUD;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;

public class  ClickUi extends GuiScreen {
	public static ArrayList<Focus.Beta.API.GUI.clickgui.window.Window> windows = Lists.newArrayList();
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
		if (windows.isEmpty()) {
			int x = 5;
			Type[] arrmoduleType = Type.values();
			int n = arrmoduleType.length;
			int n2 = 0;
			while (n2 < n) {
				Type c = arrmoduleType[n2];
				windows.add(new Focus.Beta.API.GUI.clickgui.window.Window(c, x, 5));
				x += 105;
				++n2;
			}
		}
	}
	
	  public float smoothTrans(double current, double last){
	        return (float) (current + (last - current) / (Minecraft.debugFPS / 10));
	    }
    @Override
    public void initGui() {
        super.initGui();
        percent = 1.33f;
        lastPercent = 1f;
        percent2 = 1.33f;
        lastPercent2 = 1f;
        outro = 1;
        lastOutro = 1;
    }

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.opacity = this.opacity + 10.0 < 200.0 ? (this.opacity += 10.0) : 200.0;
		GlStateManager.pushMatrix();
		ScaledResolution scaledRes = new ScaledResolution(this.mc);
		float scale = (float) scaledRes.getScaleFactor() / (float) Math.pow(scaledRes.getScaleFactor(), 2.0);
		RenderUtil.blur(0, 0, width, height);
		this.drawGradientRect(0, height - 100, width, height, 0, HUD.color);
		ScaledResolution sr = new ScaledResolution(mc);


        float outro = smoothTrans(this.outro, lastOutro);
        if (mc.currentScreen == null) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
            GlStateManager.scale(outro, outro, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
        }

        percent = smoothTrans(this.percent, lastPercent);
        percent2 = smoothTrans(this.percent2, lastPercent2);


        if (percent > 0.98) {
            GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
            GlStateManager.scale(percent, percent, 0);
            GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
        } else {
            if (percent2 <= 1) {
                GlStateManager.translate(sr.getScaledWidth() / 2, sr.getScaledHeight() / 2, 0);
                GlStateManager.scale(percent2, percent2, 0);
                GlStateManager.translate(-sr.getScaledWidth() / 2, -sr.getScaledHeight() / 2, 0);
            }
        }


        if(percent <= 1.5 && close) {
            percent = smoothTrans(this.percent, 2);
            percent2 = smoothTrans(this.percent2, 2);
        }

        if(percent >= 1.4  &&  close){
            percent = 1.5f;
            closed = true;
            mc.currentScreen = null;
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
		if (mouseX > 40.0 + 170 + 200 + 550F - 5 + 1 && mouseX < 30.0 + 479 + 1 && mouseY > 60.0F + 800 + 15 - 1 && mouseY < 320.0F + 150 + 20 - 1 && mouseButton == 0) {
			System.out.println("te");
		}
		super.mouseClicked(mouseX, mouseY, mouseButton);
	}




	@Override
	protected void keyTyped(char typedChar, int keyCode) throws IOException {
		if(!closed && keyCode == Keyboard.KEY_ESCAPE){
            close = true;
            mc.mouseHelper.grabMouseCursor();
            mc.inGameHasFocus = true;
            return;
        }
        
        if(close) {
            this.mc.displayGuiScreen((GuiScreen) null);
        }

		if (keyCode == 1 && !binding) {
			this.mc.displayGuiScreen(null);
			return;
		}
		windows.forEach(w -> w.key(typedChar, keyCode));
	}


	public void onGuiClosed() {
		this.mc.entityRenderer.func_181022_b();
		this.mc.entityRenderer.isShaderActive();
	}

	public synchronized void sendToFront(Focus.Beta.API.GUI.clickgui.window.Window window) {
		int panelIndex = 0;
		int i = 0;
		while (i < windows.size()) {
			if (windows.get(i) == window) {
				panelIndex = i;
				break;
			}
			++i;
		}
		Focus.Beta.API.GUI.clickgui.window.Window t = windows.get(windows.size() - 1);
		windows.set(windows.size() - 1, windows.get(panelIndex));
		windows.set(panelIndex, t);
	}
}
