package ProxyManager;

import java.io.IOException;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.gui.GuiScreen;

public class ProxyInfo extends GuiScreen {
	int p;
	String ver;
	int ports[];
	GuiScreen LOLXD;
	public ProxyInfo(int p, String ver, int[] ports, GuiScreen LOLXD) {
		this.p = p;
		this.ver = ver;
		this.ports = ports;
		this.LOLXD = LOLXD;
	}
	@Override
	public void drawScreen(int mouseX, int mouseY, float gay) {
		this.fontRendererObj.drawString("Protocol Version: " + ver, 5, 10, -1);
		this.fontRendererObj.drawString("Most used port: " + p, 5, 10, -1);
		this.fontRendererObj.drawString("Other common ports: " + ports.toString(), 5, 20, -1);
		super.drawScreen(mouseX, mouseY, gay);
	}
	
	@Override
	public void keyTyped(char XD, int XDD) throws IOException {
		if (XDD == Keyboard.KEY_ESCAPE) {
			mc.displayGuiScreen(LOLXD);
		}
		super.keyTyped(XD, XDD);
	}
	
	
}
