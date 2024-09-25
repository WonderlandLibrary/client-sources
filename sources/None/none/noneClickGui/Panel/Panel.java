package none.noneClickGui.Panel;

import java.util.ArrayList;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import none.Client;
import none.module.Category;
import none.module.modules.render.ClientColor;
import none.noneClickGui.NoneClickGui;
import none.utils.render.TTFFontRenderer;

public class Panel {
	private TTFFontRenderer jigsawFont;
	public String text;
	public int x, y, h;
	
	public boolean isMoving;

    private int dragX;
    private int dragY;
    public ArrayList<ModulePanel> Elements = new ArrayList<>();
	
    public Category category;
    
    public int maxW;
    
	public Panel(String text, int x, int y, TTFFontRenderer font, Category category) {
		this.jigsawFont = font;
		this.text = text;
		this.x = x;
		this.y = y;
		this.h = (int) jigsawFont.getHeight(text) + 1;
		this.category = category;
		SetUp();
		maxW = 0;
	}
	
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		if (!this.Elements.isEmpty()) {
			int startY = y + h;
			for (ModulePanel m : this.Elements) {
				if ((int)jigsawFont.getStringWidth(m.mod.getName()) > this.maxW) {
					maxW = (int)jigsawFont.getStringWidth(m.mod.getName());
				}
				m.w = maxW;
				m.x = x;
				m.StrX = m.x + (maxW / 2);
				m.y = startY;
				m.drawScreen(mouseX, mouseY, partialTicks);
				startY += m.h + 4;
			}
		}
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
	
	public Category getCategory() {
		return category;
	}
	
	private void drag(int mouseX, int mouseY) {
        if (isMoving) {
            this.x = mouseX + dragX;
            this.y = mouseY + dragY;
        }
    }
	
	public void mousePressed(int button, int x, int y) {
		if (!isHovered(x, y)) return;
        if (button == 0) {
//            if (x >= this.x && y >= this.y && x <= this.x + this.w && y <= this.y + this.h) {
//                isMoving = true;
//
//                dragX = this.x - x;
//                dragY = this.y - y;
//
////                drag(x, y);
//            }
        }
    }
	
	public boolean isHovered(int mouseX, int mouseY) {
		if (!this.Elements.isEmpty()) {
			for (ModulePanel m : this.Elements) {
				m.isHovered(mouseX, mouseY);
			}
		}
		return false;
//		return mouseX >= x && mouseX <= x + w && mouseY >= y && mouseY <= y + h;
	}
	
	public void mouseMoved(int x, int y) {
        drag(x, y);
    }
	
	public void mouseReleased(int button, int x, int y) {
        if (button == 0) {
            isMoving = false;
        }
    }

	public void SetUp() {
		
	}
}
