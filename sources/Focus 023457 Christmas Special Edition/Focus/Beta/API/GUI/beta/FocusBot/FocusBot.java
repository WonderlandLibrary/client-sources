package Focus.Beta.API.GUI.beta.FocusBot;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import java.awt.Color;

import Focus.Beta.UTILS.render.RenderUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;

public class FocusBot extends GuiScreen{

    private boolean close = false;
    private boolean closed;
	
	@Override
	public void drawScreen(int x, int y, float partialTicks) {
		ScaledResolution sr = new ScaledResolution(mc);
		RenderUtil.blur(0, 0, sr.getScaledWidth(), sr.getScaledHeight());
		Gui.drawRect(sr.getScaledWidth() - 600, sr.getScaledHeight() - 390, sr.getScaledWidth() - 275, sr.getScaledHeight() - 125, new Color(36, 36, 36).getRGB());
		Gui.drawRect(sr.getScaledWidth() - 685, sr.getScaledHeight() - 390, sr.getScaledWidth() - 604, sr.getScaledHeight() - 125, new Color(36, 36, 36).getRGB());
		super.drawScreen(x, y, partialTicks);
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

	}
	
	@Override
	public void initGui() {
		super.initGui();
	}

}
