package Squad.Modules.Gui;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventTarget;

import Squad.Squad;
import Squad.Events.EventUpdate;
import Squad.base.Module;
import de.Hero.settings.Setting;
import net.minecraft.client.gui.Gui;

public class ModClickGui extends Module {

	public ModClickGui() {
		super("Gui", Keyboard.KEY_RSHIFT,0xffffffff, Category.Gui);
	}
//
	
	
	  public void setup(){
		  ArrayList<String> options = new ArrayList<>();
	    Squad.setmgr.rSetting(new Setting("Keystrokes", this, false));
	    Squad.instance.setmgr.rSetting(new Setting("GuiSettings", this, "HeroCode", options));
	  }
@EventTarget
public void onEnable(EventUpdate e){
 	mc.displayGuiScreen(Squad.instance.clickgui);
	toggle();

 	
	
}
}
