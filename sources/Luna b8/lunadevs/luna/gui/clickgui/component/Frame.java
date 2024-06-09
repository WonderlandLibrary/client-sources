package lunadevs.luna.gui.clickgui.component;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.opengl.GL11;

import lunadevs.luna.category.Category;
import lunadevs.luna.gui.clickgui.component.components.Button;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.module.Module;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;

public class Frame {

	public ArrayList<Component> components;
	public Category category;
	private boolean open;
	private int width;
	private int y;
	private int x;
	private int barHeight;
	private boolean isDragging;
	public int dragX;
	public int dragY;
    public static Module mod;

	public Frame(Category cat) {
		this.components = new ArrayList<Component>();
		this.category = cat;
		this.width = 88;
		this.x = 5;
		this.y = 5;
		this.barHeight = 13;
		this.dragX = 0;
		this.open = false;
		this.isDragging = false;
		int tY = this.barHeight;
		for(Module mod : ModuleManager.getModules()){
		    if(mod.getCategory().equals(category)) {
                Button modButton = new Button(mod, this, tY);
                this.components.add(modButton);
                tY += 12;
            }
		}
	}
	
	public ArrayList<Component> getComponents() {
		return components;
	}
	
	public void setX(int newX) {
		this.x = newX;
	}
	
	public void setY(int newY) {
		this.y = newY;
	}
	
	public void setDrag(boolean drag) {
		this.isDragging = drag;
	}
	
	public boolean isOpen() {
		return open;
	}
	
	public void setOpen(boolean open) {
		this.open = open;
	}
	 public static double rn = 1.5D;
	private int Color() {
		int cxd = 0;
		cxd = (int)(cxd + rn);
            if (cxd > 50) {
              cxd = 0;
            }
		Color color = new Color(Color.HSBtoRGB((float)(Minecraft.getMinecraft().thePlayer.ticksExisted / 60.0D + Math.sin(cxd / 60.0D * 1.5707963267948966D)) % 1.0F, 0.5882353F, 1.0F));
        int col = new Color(color.getRed(), color.getGreen(), color.getBlue(), 200).getRGB();
		return col;
}
	
	public void renderFrame(FontRenderer fontRenderer) {
		Gui.drawRect(this.x, this.y, this.x + this.width, this.y + this.barHeight, Color());
		GL11.glPushMatrix();
		GL11.glScalef(0.5f,0.5f, 0.5f);
		fontRenderer.drawString(this.category.name(), (this.x + 2) * 2 + 5, (this.y + (int)2.5F) * 2 + 5, 0xFFFFFFFF);
		fontRenderer.drawString(this.open ? "-" : "+", (this.x + this.width - 10) * 2 + 5, (this.y + (int)2.5f) * 2 + 5, -1);//ORIGINAL: -1
		GL11.glPopMatrix();
		if(this.open) {
			if(!this.components.isEmpty()) {
				Gui.drawRect(this.x, this.y + this.barHeight, this.x + 1, this.y + this.barHeight + (12 * components.size()), new Color(0, 200, 20, 150).getRGB());
				Gui.drawRect(this.x, this.y + this.barHeight + (12 * components.size()), this.x + this.width, this.y + this.barHeight + (12 * components.size()) + 1, Color() +new Color(255, 255, 255, 255).getRGB());
				Gui.drawRect(this.x + this.width, this.y + this.barHeight, this.x + this.width - 1, this.y + this.barHeight + (12 * components.size()), new Color(0, 200, 20, 150).getRGB());
				for(Component component : components) {
					component.renderComponent();
				}
			}
		}
	}
	
	public void refresh() {
		int off = this.barHeight;
		for(Component comp : components) {
			comp.setOff(off);
			off += comp.getHeight();
		}
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public void updatePosition(int mouseX, int mouseY) {
		if(this.isDragging) {
			this.setX(mouseX - dragX);
			this.setY(mouseY - dragY);
		}
	}
	
	public boolean isWithinHeader(int x, int y) {
		if(x >= this.x && x <= this.x + this.width && y >= this.y && y <= this.y + this.barHeight) {
			return true;
		}
		return false;
	}
	
}
