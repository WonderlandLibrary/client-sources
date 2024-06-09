package ooo.cpacket.lemongui.example;

import java.util.ArrayList;

import org.lwjgl.input.Keyboard;

import ooo.cpacket.lemongui.clickgui.ClickGui;
import ooo.cpacket.lemongui.settings.Setting;
import ooo.cpacket.ruby.Ruby;
import ooo.cpacket.ruby.module.Module;

/**
 *  Made by HeroCode & xTrM_
 *  it's free to use
 *  but you have to credit us
 *
 *  @author HeroCode
 */
public class GuiModule extends Module {

	public ClickGui clickgui;
	
    public GuiModule()
    {
        super("ClickGUI", Keyboard.KEY_RSHIFT, Category.HIDDEN);
        ArrayList<String> options = new ArrayList<>();
    	options.add("DefaultOption");
    	options.add("Option2");
    	options.add("Option3");
    	Ruby.getRuby.setmgr.rSetting(new Setting("OptionSelector", this, "DefaultOption", options));
    	Ruby.getRuby.setmgr.rSetting(new Setting("BooleanOption", this, false));
    	Ruby.getRuby.setmgr.rSetting(new Setting("SliderOptionInt", this, 255, 0, 255, true));
    	Ruby.getRuby.setmgr.rSetting(new Setting("SliderOptionDouble", this, 10, 0, 20, false));
    }
    
    @Override
    public void onEnable()
    {
    	if(this.clickgui == null)
    		this.clickgui = new ClickGui();
    	
    	mc.displayGuiScreen(this.clickgui);
    	toggle();
    }

	@Override
	public void onDisable() {
		
	}
}
