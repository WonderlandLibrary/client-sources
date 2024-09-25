package none.noneClickGui.Panel.Target;

import java.awt.Color;

import net.minecraft.client.gui.Gui;
import none.Client;
import none.event.events.EventChat;
import none.module.modules.render.ClientColor;
import none.noneClickGui.Panel.ValuePanel.ValueSlot;
import none.utils.Targeter;
import none.utils.Targeter.GameType;
import none.utils.render.TTFFontRenderer;

public class TargeterBox {
	
	public String title;
	public int x,y,w,h;
	
	public TTFFontRenderer jigsawFont = Client.fm.getFont("JIGR 19");
	
	private boolean toggled;
	
	public TargeterBox(String title, int x, int y) {
		this.title = title;
		this.x = x;
		this.y = y;
		this.w = (int)jigsawFont.getStringWidth(title);
		this.h = (int)jigsawFont.getHeight(title);
		if (title.equalsIgnoreCase("Player")) {
			toggled = Targeter.isPlayer();
		}else if (title.equalsIgnoreCase("Other")) {
			toggled = Targeter.isOther();
		}else if (title.equalsIgnoreCase("Invisible")) {
			toggled = Targeter.isInvisible();
		}
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		String text = title;
		if (title.equalsIgnoreCase("GameType") && Targeter.getGameType() == GameType.Normal) {
			text = title + ":" + "Normal";
			w = (int)jigsawFont.getStringWidth(text);
		}else if (title.equalsIgnoreCase("GameType") && Targeter.getGameType() == GameType.VillagerDef) {
			text = title + ":" + "VillagerDef";
			w = (int)jigsawFont.getStringWidth(text);
		}
		Gui.drawRect(x - 1, y - 1, x + w + 1, y + h + 1, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		Gui.drawRect(x, y, x + w, y + h, toggled ? Color.WHITE.getRGB() : isHovered(mouseX, mouseY) ? Integer.MIN_VALUE : Color.BLACK.getRGB());
		jigsawFont.drawStringWithShadow(text, x, y, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}
	
	public void mouseReleased(int button, int x, int y) {
		
	}
	
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) return;
		if (button == 0) {
			if (title.equalsIgnoreCase("Player")) {
				Targeter.setPlayer(!Targeter.isPlayer());
				toggled = !toggled;
			}else if (title.equalsIgnoreCase("Other")) {
				Targeter.setOther(!Targeter.isOther());
				toggled = !toggled;
			}else if (title.equalsIgnoreCase("Invisible")) {
				Targeter.setInvisible(!Targeter.isInvisible());
				toggled = !toggled;
			}else if (title.equalsIgnoreCase("GameType")) {
				if (Targeter.getGameType() == GameType.Normal) {
					Targeter.setGameType(GameType.VillagerDef);
				}else if (Targeter.getGameType() == GameType.VillagerDef) {
					Targeter.setGameType(GameType.Normal);
				}
			}
		}
	}
	
	public void mouseMoved(int x, int y) {
		
    }
}
