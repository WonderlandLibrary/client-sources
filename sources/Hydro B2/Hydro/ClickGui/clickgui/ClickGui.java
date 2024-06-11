package Hydro.ClickGui.clickgui;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Hydro.Client;
import Hydro.ClickGui.clickgui.component.Component;
import Hydro.ClickGui.clickgui.component.Frame;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;

public class ClickGui extends GuiScreen {

	public static ArrayList<Frame> frames;
	public static int color = 0xff3389F0;
	
	public ClickGui() {
		this.frames = new ArrayList<Frame>();
		int frameX = 5;
		for(Hydro.module.Category category : Hydro.module.Category.values()) {
			Frame frame = new Frame(category);
			frame.setX(frameX);
			frames.add(frame);
			frameX += frame.getWidth() + 4;
			frame.setOpen(true);
		}
	}
	
	@Override
	public void initGui() {
	}
	
	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		if(Client.instance.settingsManager.getSettingByName("CGuiMode").getValString().equals("Normal")){
			for(Frame frame : reverseArrayList(frames)) {
				frame.renderFrame(this.fontRendererObj);
				frame.updatePosition(mouseX, mouseY);
				for(Component comp : frame.getComponents()) {
					comp.updateComponent(mouseX, mouseY);
				}
			}
		}

		if(Client.instance.settingsManager.getSettingByName("CGuiMode").getValString().equals("Discord")){
			Gui.drawRect(25, 100, 350, 400, 0xff36393F);
			Gui.drawRect(25, 100, 75, 400, 0xff202225);

			for(Frame frame : reverseArrayList(frames)) {
				frame.renderFrame(this.fontRendererObj);
				frame.updatePosition(mouseX, mouseY);
				for(Component comp : frame.getComponents()) {
					comp.updateComponent(mouseX, mouseY);
				}
			}
		}

	}

	public ArrayList<Frame> reverseArrayList(ArrayList<Frame> alist)
	{
		// Arraylist for storing reversed elements
		ArrayList<Frame> revArrayList = new ArrayList<Frame>();
		for (int i = alist.size() - 1; i >= 0; i--) {

			// Append the elements in reverse order
			revArrayList.add(alist.get(i));
		}

		// Return the reversed arraylist
		return revArrayList;
	}
	
	@Override
    protected void mouseClicked(final int mouseX, final int mouseY, final int mouseButton) throws IOException {
		for(Frame frame : frames) {
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 0) {
				frame.setDrag(true);
				frame.dragX = mouseX - frame.getX();
				frame.dragY = mouseY - frame.getY();
			}
			if(frame.isWithinHeader(mouseX, mouseY) && mouseButton == 1) {
				frame.setOpen(!frame.isOpen());
			}
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseClicked(mouseX, mouseY, mouseButton);
					}
				}
			}
		}
	}
	
	@Override
	protected void keyTyped(char typedChar, int keyCode) {
		for(Frame frame : frames) {
			if(frame.isOpen() && keyCode != 1) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.keyTyped(typedChar, keyCode);
					}
				}
			}
		}
		if (keyCode == 1) {
            this.mc.displayGuiScreen(null);
        }
	}

	
	@Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
		for(Frame frame : frames) {
			frame.setDrag(false);
		}
		for(Frame frame : frames) {
			if(frame.isOpen()) {
				if(!frame.getComponents().isEmpty()) {
					for(Component component : frame.getComponents()) {
						component.mouseReleased(mouseX, mouseY, state);
					}
				}
			}
		}
	}
	
	@Override
	public boolean doesGuiPauseGame() {
		return false;
	}
}
