package lunadevs.luna.module;

import java.util.ArrayList;
import java.util.List;

import com.darkmagician6.eventapi.EventManager;

import lunadevs.luna.category.Category;
import lunadevs.luna.events.UpdateEvent;
import lunadevs.luna.manage.ModuleManager;
import lunadevs.luna.option.Option;
import lunadevs.luna.option.OptionManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.util.StringUtils;

public abstract class ElementManager {
	
	public static Minecraft mc = Minecraft.getMinecraft();
	public String name;
	public int bind;
	public Category category;
	public boolean isEnabled;
	public boolean value;
    public int transition;

	public ElementManager(String name, int bind, Category category, boolean value) {
		this.name = name;
		this.bind = bind;
		this.category = category;
		this.value = value;
	}

    public List<Option> getOptions() {
        final List<Option> optionList = new ArrayList();
        for (final Option option : OptionManager.getOptionList()) {
            if (option.getModule().equals(this)) {
                optionList.add(option);
            }
        }
        return optionList;
    }

	public  void toggle(){
			this.isEnabled = (!this.isEnabled);
		    if (this.isEnabled) {
		      onEnable();
		    }
		    if (!this.isEnabled) {
		      onDisable();
		    }
		    ModuleManager.save();
	}


    public void options() {}

	public void onEnable(){
		EventManager.register(this);
		this.transition = mc.fontRendererObj.getStringWidth(StringUtils.stripControlCodes(this.getName())) - 10;
	}
	public void onDisable(){
		EventManager.unregister(this);
		this.transition = mc.fontRendererObj.getStringWidth(StringUtils.stripControlCodes(this.getName())) + 10;
	}
	
	public void onUpdate(final UpdateEvent event){}
	public void onRender(){}
	public abstract String getValue();
	
	public static boolean isToggled(boolean isEnable)
	  {
	    if (isEnable) {
	      return true;
	    }
	    if (!isEnable) {
	      return false;
	    }
	    return false;
	  }
	
	public boolean onSendChatMessage(String s)
	  {
	    return true;
	  }
	  
	  public boolean onRecieveChatMessage(S02PacketChat packet)
	  {
	    return true;
	  }
	  public final boolean isCategory(Category s) {
			if (s == category)
				return true;
			return false;
		}
	  
	public void setName(String name) {
		this.name = name;
	}
	
	public void setBind(int bind) {
		this.bind = bind;
	}
	
	public void setCategory(Category category) {
		this.category = category;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	
	public String getName() {
		return name;
	}
	
	public int getBind() {
		return bind;
	}
	
	public Category getCategory() {
		return category;
	}
	
	public boolean isEnabled() {
		return isEnabled;
	}
	
	public void setState(boolean flag){
		this.isEnabled = flag;
		if(isEnabled){
			onEnable();
		}else{
			onDisable();
		}
		
	}
	
	public int getTransition() {
        return this.transition;
    }
    
    public void setTransition(final int transition) {
        this.transition = transition;
    }
	
}
