package none.noneClickGui.Panel.Target;

import java.awt.Color;
import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import none.Client;
import none.event.events.EventChat;
import none.module.modules.render.ClientColor;
import none.utils.render.Anima;
import none.utils.render.TTFFontRenderer;

public class TargetPanel {
	public int x, y, w, h;
	
	public boolean isMoving;

    private int dragX;
    private int dragY;
    
    private Anima anima = new Anima();
	
	public ArrayList<TargeterBox> panel;
	public TTFFontRenderer jigsawFont = Client.fm.getFont("JIGR 19");
	
	public TargetPanel() {
		ScaledResolution res = new ScaledResolution(Minecraft.getMinecraft());
		x = 3;
		y = res.getScaledHeight() / 2 + 150;
		panel = new ArrayList<>();
		
		panel.add(new TargeterBox("Player", x, y));
		panel.add(new TargeterBox("Other", x, y));
		panel.add(new TargeterBox("Invisible", x, y));
		panel.add(new TargeterBox("GameType", x, y));
	}
	
	public boolean isHovered(int mouseX, int mouseY) {
		for (TargeterBox box : panel) {
			box.isHovered(mouseX, mouseY);
		}
		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		w = 77;
		h = panel.size() * 15;
		anima.Start();
		anima.setMin(x - (w/2));
		anima.setMax(x + (w/2));
		anima.setLenth(isMoving ? 8 : 2);
		anima.doAnima();
		Gui.drawRect(x - 1, y - 1, x + w + 1, y + h + 1, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		Gui.drawRect(x, y, x + w, y + h, Color.BLACK.getRGB());
		jigsawFont.drawStringWithShadow("Targeter", x + 2, y + 2, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		Gui.drawRect(anima.getAnima() + 1, y + jigsawFont.getHeight("targeter") + 4, anima.getAnima() + w - 1, y + jigsawFont.getHeight("targeter")+ 1 + 4, !ClientColor.rainbow.getObject() ? ClientColor.getColor() : ClientColor.rainbow(100));
		int startY = y + (int)jigsawFont.getHeight("Targeter") + 1 + 7;
		for (TargeterBox box : panel) {
			box.x = x + 2;
			box.y = startY;
			box.drawScreen(mouseX, mouseY, partialTicks);
			startY += box.h + 3;
		}
	}
	
	public void mouseReleased(int button, int x, int y) {
		if (button == 0) {
            isMoving = false;
        }
		for (TargeterBox box : panel) {
			box.mouseReleased(button, x, y);
		}
	}
	
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) return;
        if (button == 0) {
            if (x >= this.x && y >= this.y && x <= this.x + this.w && y <= this.y + this.h) {
                isMoving = true;

                dragX = this.x - x;
                dragY = this.y - y;

//                drag(x, y);
            }
        }
        for (TargeterBox box : panel) {
        	box.mousePressed(button, x, y);
        }
	}
	
	public void mouseMoved(int x, int y) {
		drag(x, y);
    }
	
	private void drag(int mouseX, int mouseY) {
        if (isMoving) {
            this.x = mouseX + dragX;
            this.y = mouseY + dragY;
        }
    }
	
	public void onClose() {
		anima.Stop();
	}
}
