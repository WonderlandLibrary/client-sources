package Snipeware.gui.click.components;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import Snipeware.Client;
import Snipeware.gui.click.ClickGui;
import Snipeware.gui.click.Panel;
import Snipeware.gui.click.components.listeners.ComponentListener;
import Snipeware.gui.click.util.ClickUtil;
import Snipeware.module.Module;
import Snipeware.util.other.MathUtils;
import Snipeware.util.other.TimeHelper;
import Snipeware.util.visual.RenderUtil;
import font.FontRenderer;
import net.minecraft.client.gui.VanillaFontRenderer;


/**
 * @author sendQueue <Vinii>
 *
 *         Further info at Vinii.de or github@vinii.de, file created at 11.11.2020. 
 *         Use is only authorized if given credit!
 * 
 */
public class GuiButton implements GuiComponent {
	public static int expandedID = -1;
	private int id;

	private String text;
	private double animated1;	
	
	private ArrayList<ActionListener> clickListeners = new ArrayList<ActionListener>();
	private ArrayList<GuiComponent> guiComponents = new ArrayList<GuiComponent>();

	private int width, textWidth, posX, posY;
	
	private double animatedWidth;
	private boolean done;
	
	/**
	 * 
	 */
	public GuiButton(String text) {
		this.text = text;
		textWidth = (int) Panel.fR.getWidth(text);
		id = ClickGui.compID += 1;
	}

	@Override
	public void render(int posX, int posY, int width, int mouseX, int mouseY, int wheelY) {
		this.posX = posX;
		this.posY = posY;
		this.width = width;

		final int height = getHeight();
		renderGUI(posX, posY, width, height);
		
	}
	

	/**
	 * Renders button for theme Caesium
	 */
	private void renderGUI(int posX, int posY, int width, int height) {
		
		final TimeHelper enableTimer = new TimeHelper();
		
		
		
		Frame frame;
		GuiFrame();
		
		
			animated1 = RenderUtil.animate(posY, animated1, 0.1);
			
		
		animatedWidth = RenderUtil.animate(width, animatedWidth, 0.1);
		
		RenderUtil.drawRect(posX, animated1, width -1, height, Panel.black100);
		
		if (Client.INSTANCE.getModuleManager().getModule(getText()).isEnabled()) {
			RenderUtil.drawRect(posX, animated1, animatedWidth -1, height, Panel.color);
		}
		
		
		final FontRenderer fr = Client.INSTANCE.getFontManager().getFont("Display 17", false);
				
		fr.drawStringWithShadow(getText(), posX + 5, (float)animated1 + 1, Panel.fontColor);
		
		
		RenderUtil.drawRect(posX, animated1, width - 108, height, Panel.black195);
		RenderUtil.drawRect(posX + 107, animated1, width - 108, height, Panel.black195);
		
		
	}

	private void GuiFrame() {
	
		
	}

	@Override
	public void mouseClicked(int mouseX, int mouseY, int mouseButton) {
		if (GuiFrame.dragID == -1 && ClickUtil.isHovered(posX, posY, width, getHeight(), mouseX, mouseY)) {
			if (mouseButton == 1) {
				if (expandedID != id) {
					expandedID = id;
				} else {
					expandedID = -1;
				}
			} else if (mouseButton == 0) {
				for (ActionListener listener : clickListeners) {
					listener.actionPerformed(new ActionEvent(this, id, "click", System.currentTimeMillis(), 0));
				}
			}
		}

		if (expandedID == id) {
			for (GuiComponent component : guiComponents) {
				component.mouseClicked(mouseX, mouseY, mouseButton);
			}
		}
		
	}

	@Override
	public void keyTyped(int keyCode, char typedChar) {
		if (expandedID == id) {
			for (GuiComponent component : guiComponents) {
				component.keyTyped(keyCode, typedChar);
			}
		}

	}

	@Override
	public int getWidth() {
		return 5 + textWidth;
	}

	@Override
	public int getHeight() {
		return Panel.fR.FONT_HEIGHT + 3;
	}
	
	@Override
	public boolean allowScroll() {
		return false;
	}

	public String getText() {
		return text;
	}

	public int getButtonID() {
		return id;
	}

	public ArrayList<GuiComponent> getComponents() {
		return guiComponents;
	}

	public void addClickListener(ActionListener actionlistener) {
		clickListeners.add(actionlistener);
	}

	public void addExtendListener(ComponentListener listener) {
		listener.addComponents();
		guiComponents.addAll(listener.getComponents());
	}

}
