package Snipeware.gui.click.components;

import java.awt.Color;
import java.util.ArrayList;

import org.lwjgl.input.Mouse;

import Snipeware.Client;
import Snipeware.gui.click.ClickGui;
import Snipeware.gui.click.Panel;
import Snipeware.gui.click.util.ClickUtil;
import Snipeware.util.other.Logger;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.visual.RenderUtil;
import font.FontRenderer;
import net.minecraft.client.gui.VanillaFontRenderer;
import net.minecraft.util.ResourceLocation;

/**
 * @author sendQueue <Vinii>
 *
 *         Further info at Vinii.de or github@vinii.de, file created at
 *         11.11.2020. Use is only authorized if given credit!
 * 
 */
public class GuiFrame implements Frame {
	private ArrayList<GuiButton> buttons = new ArrayList<GuiButton>();

	private boolean isExpaned, isDragging;

	private int id, posX, posY, prevPosX, prevPosY, scrollHeight;
	public static int dragID;
	private double animated1;
	private String title;
	public TimeHelper Timer = new TimeHelper();
	private boolean done = false;
	/**
	 * 
	 */
	public GuiFrame(String title, int posX, int posY, boolean expanded) {
		this.title = title;
		this.posX = posX;
		this.posY = posY;
		this.isExpaned = expanded;
		this.id = ClickGui.compID += 1;
		this.scrollHeight = 0;
	}

	@Override
	public void render(int mouseX, int mouseY) {
		renderGUI(mouseX, mouseY);
	}
	
	

	/**
	 * Handles Caesium theme
	 * 
	 * @param mouseX
	 * @param mouseY
	 */

	
	private void renderGUI(int mouseX, int mouseY) {
		
		
		
		final int color = Panel.color;
		final int fontColor = Panel.fontColor;
		int width = (int) Math.max(Panel.FRAME_WIDTH, Panel.fR.getWidth(title) + 15);
		if (isDragging && Mouse.isButtonDown(0)) {
			posX = mouseX - prevPosX;
			posY = mouseY - prevPosY;
			dragID = id;
		} else {
			isDragging = false;
			dragID = -1;
		}
		for (GuiButton button : buttons) {
			width = Math.max(width, button.getWidth() + 15);
		}
		final FontRenderer fr = Client.INSTANCE.getFontManager().getFont("Display 22", true);
		
		animated1 = RenderUtil.animate(posY, animated1, 0.1);
	
	
		final FontRenderer Image = Client.INSTANCE.getFontManager().getFont("Snipeware 32", false);
		
		RenderUtil.drawRect(posX + 1, animated1 - 6, width - 1, 18, Panel.black195);
	
		//RenderUtil.drawVerticalGradient(posX + 1, posY + 1, width - 1, 11, new Color(126, 204, 251, 200).getRGB(), new Color(90, 185, 235, 120).getRGB());
		fr.drawStringWithShadow(title, posX + 5, (float)animated1 - 1, fontColor);

	
		if(title.contains("VISUALS")) {
			Image.drawCenteredString("C",  posX + width - width / 8 ,   (float)animated1 - 4, -1);
		}else if(title.contains("WORLD")) {
			Image.drawCenteredString("D",  posX + width - width / 8 ,   (float)animated1 - 3.5f, -1);
		}else if(title.contains("PLAYER")) {
			Image.drawCenteredString("B",  posX + width - width / 8 ,   (float)animated1 - 3.5f, -1);
		}else if(title.contains("MOVEMENT")) {
			Image.drawCenteredString("A",  posX + width - width / 8 ,   (float)animated1 - 3.5f, -1);
		}else if(title.contains("COMBAT")) {
			Image.drawCenteredString("E",  posX + width - width / 8 ,   (float)animated1 - 3.5f, -1);
		}
		if (isExpaned) {
			int height = 0;
			final int background = Panel.grey40_240;
			for (GuiButton button : buttons) {
				button.render(posX + 1, posY + height + 12, width, mouseX, mouseY, 0);
				// check if -1
				if (button.getButtonID() == GuiButton.expandedID) {
					ArrayList<GuiComponent> components = button.getComponents();
					if (!components.isEmpty()) {
						int xOffset = 10;
						int yOffset = 0;
						boolean allowScroll = true;
						for (GuiComponent component : components) {
							xOffset = Math.max(xOffset, component.getWidth());
							yOffset += component.getHeight();
							if (!component.allowScroll()) {
								allowScroll = false;
							}
						}
						final int left = posX + width + 2;
						final int right = left + xOffset;
						final int top = posY + height + 12;
						final int bottom = top + yOffset + 1;
						// 8 is the scroll reduction
						int wheelY = Mouse.getDWheel() * -1 / 8;
						if (bottom + scrollHeight < 30) {
							wheelY *= -1;
							scrollHeight += 10;
						}
						if (allowScroll)
							scrollHeight += wheelY;
						
						ClickUtil.drawRect(left + 1, top + 1 + scrollHeight, right, bottom + scrollHeight, Panel.black100);
						
						ClickUtil.drawRect(left + 100, top + 1 + scrollHeight, right, bottom + scrollHeight, Panel.black195);
						
						ClickUtil.drawRect(left, top + 1 + scrollHeight, right - 100, bottom + scrollHeight, Panel.black195);
						
						int height2 = 0;
						for (GuiComponent component : components) {
							component.render(left, top + height2 + 2 + scrollHeight, xOffset, mouseX, mouseY, wheelY);
							height2 += component.getHeight();
						}
					}
				}
				height += button.getHeight();
				
				//RenderUtil.drawHorizontalLine(posX + 1, posX + width - 1, posY + height + 12, color);
			//	RenderUtil.drawRect(posX + 1, animated1 + height + 12, width - 1, 1, Panel.black195);
			}
		}
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		int width = Panel.FRAME_WIDTH;
		if (isExpaned) {

			for (GuiButton button : buttons) {
				// sort for the max needed width
				width = Math.max(width, button.getWidth());
				button.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
		if (ClickUtil.isHovered(posX, posY - 8, width, 19, mouseX, mouseY)) {
			if (mouseButton == 0) {
				prevPosX = mouseX - posX;
				prevPosY = mouseY - posY;
				isDragging = true;
				dragID = id;
			} else if (mouseButton == 1) {
				isExpaned = !isExpaned;
				scrollHeight = 0;
				isDragging = false;
				dragID = -1;
			}
		}
	}

	@Override
	public void keyTyped(int keyCode, char typedChar) {
		if (isExpaned) {
			for (GuiButton button : buttons) {
				button.keyTyped(keyCode, typedChar);
			}
		}
	}

	@Override
	public void initialize() {
		// TODO Auto-generated method stub

	}

	public void addButton(GuiButton button) {
		if (!buttons.contains(button)) {
			buttons.add(button);
		}
	}

	public int getButtonID() {
		return id;
	}

	/**
	 * @return isExpaned
	 */
	public boolean isExpaned() {
		return isExpaned;
	}

	/**
	 * @return the posX
	 */
	public int getPosX() {
		return posX;
	}

	/**
	 * @return the posY
	 */
	public int getPosY() {
		return posY;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

}
