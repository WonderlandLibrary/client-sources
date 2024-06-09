package ooo.cpacket.ruby.module.render;

import java.awt.Color;

import me.arithmo.gui.altmanager.RenderingUtil;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import ooo.cpacket.ruby.Ruby;
import ooo.cpacket.ruby.api.event.EventImpl;
import ooo.cpacket.ruby.api.event.IEvent;
import ooo.cpacket.ruby.api.event.events.render.EventGameRender;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.module.render.hud.TabGui;

public class Hud extends Module implements IEvent, Runnable {

	public Hud(String name, int key, Category category) {
		super(name, key, category);
		if (!this.isEnabled())
			this.toggle();
	}

	@Override
	public void run() {
		
	}

	@Override
	public void onEnable() {
		
	}

	@Override
	public void onDisable() {
		this.toggle();
	}
	
	private int getRainbow(int speed, int offset) {
		float hue = (System.currentTimeMillis() + offset) % speed;
		hue /= speed;
		return Color.getHSBColor(hue, 0.7f, 0.7f).getRGB();
	}
	
	@EventImpl
	public void onUpdate(EventGameRender e) {
		ScaledResolution xd = e.sr;
		int y = 0;
		Gui.drawRect(0, 0, 0, 0, -1);
		GlStateManager.pushMatrix();
		GlStateManager.scale(1.5, 1.5, 1.5);
		this.mc.fontRendererObj.drawStringWithShadow("\u00A7o" + Ruby.getRuby.cinfo.getNameF(), 16, 5, -1);
		GlStateManager.popMatrix();
		RenderingUtil.drawCircle(28, 13, 10, 20, -1);
		for (Module m : Ruby.getRuby.getModuleManager().modules) {
			if (!m.isEnabled())
				continue;
			int wheretoplaceXD = xd.getScaledWidth() - mc.fontRendererObj.getStringWidth(m.getSuffixedName()) - 2;
			int boxStart = wheretoplaceXD + mc.fontRendererObj.getStringWidth(m.getSuffixedName()) + 1;
			int boxColorStart = boxStart + 5;
//			Gui.drawRect(boxStart, y + 11, wheretoplaceXD, y, new Color(0,0,0, 0.6f).getRGB());
//			Gui.drawRect(boxColorStart, y + 11, boxColorStart - 6, y, this.getRainbow(6000, y * -15));
			this.mc.fontRendererObj.drawStringWithShadow(m.getSuffixedName(), wheretoplaceXD, y, this.getRainbow(7000, y * -15));
			y += 10;
		}
		TabGui.INSTANCE.render(1, 20);
//		TabGui.INSTANCE.handleKey();
	}
}
