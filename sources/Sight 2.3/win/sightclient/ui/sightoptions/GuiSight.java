package win.sightclient.ui.sightoptions;

import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import win.sightclient.ui.DiscordButton;
import win.sightclient.ui.altmanager.gui.GuiAltManager;

public class GuiSight extends GuiScreen {

	@Override
	public void initGui() {
		this.buttonList.add(new GuiButton(0, (this.width / 2) - 102, this.height / 2 - 25, 100, 20, "Animations"));
		this.buttonList.add(new GuiButton(1, (this.width / 2) + 2, this.height / 2 - 25, 100, 20, "Credits"));
		this.buttonList.add(new GuiButton(2, (this.width / 2) + 2, this.height / 2 + 5, 100, 20, "Alt Manager"));
		this.buttonList.add(new GuiButton(3, (this.width / 2) - 102, this.height / 2 + 5, 100, 20, "Search Blocks"));
		
		this.buttonList.add(new DiscordButton(10, 4, this.height - 55));
	}
	
	@Override
    protected void actionPerformed(GuiButton button) throws IOException
    {
		if (button.id == 0) {
			
		} else if (button.id == 1) {
			
		} else if (button.id == 2) {
			mc.displayGuiScreen(new GuiAltManager());
		} else if (button.id == 3) {
			
		}
    }
}
