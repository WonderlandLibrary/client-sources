package none.module;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import net.minecraft.client.Minecraft;
import none.Client;
import none.event.Event;
import none.event.EventListener;
import none.event.events.EventChat;
import none.notifications.Notification;
import none.notifications.NotificationManager;
import none.notifications.NotificationType;
import none.valuesystem.Value;

public abstract class Module implements EventListener {
	
	protected Minecraft mc = Minecraft.getMinecraft();
	
	private int anim;
	
	public String name;
	public String DisplayName;
	public Category category;
	public int KeyCode;
	
	private boolean enabled;
	private boolean isshowed;
	
	public Module(String name, String displayName, Category category) {
		this(name, displayName, category, Keyboard.KEY_NONE);
	}
	
	public Module(String name, String displayName, Category category, int keyCode) {
		this.name = name;
		DisplayName = displayName;
		this.category = category;
		KeyCode = keyCode;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return DisplayName;
	}

	public void setDisplayName(String displayName) {
		DisplayName = displayName;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public int getKeyCode() {
		return KeyCode;
	}

	public void setKeyCode(int keyCode) {
		KeyCode = keyCode;
	}
	
	public boolean isshowed() {
		return isshowed;
	}
	
	public void setshowed(boolean isshowed) {
		this.isshowed = isshowed;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	public void toggle() {
		if (this.getClass().equals(Checker.class)) return;
		setEnabled(!isEnabled());
		if (enabled == true) {
			onEnable();
		}
		else
		{
			onDisable();
		}
	}
	
	protected void onEnable() {
		Client.instance.eventManager.register(this);
		if (anim >= -1) {
			anim = 0;
			setshowed(true);
		}
		if (this.getClass().equals(Checker.class)) return;
		Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), ChatFormatting.GREEN + " was enabled", 1));
//		EventChat.addchatmessage(ChatFormatting.WHITE + " [" + getName() + "]" + ChatFormatting.GREEN + " Was Enabled");
	}
	
	protected void onDisable() {
		Client.instance.eventManager.unregister(this);
		Client.instance.notification.show(new Notification(NotificationType.INFO, getName(), ChatFormatting.RED + " was disabled", 1));
//		EventChat.addchatmessage(ChatFormatting.WHITE + " [" + getName() + "]" + ChatFormatting.RED + " Was Disabled");
	}
	
	public Value getValue(final String valueName) {
        for(final Field field : getClass().getDeclaredFields()) {
            try{
                field.setAccessible(true);

                final Object o = field.get(this);

                System.out.println(field.getName());

                if(o instanceof Value) {
                    final Value value = (Value) o;

                    if(value.getName().equalsIgnoreCase(valueName))
                        return value;
                }
            }catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    public List<Value> getValues() {
        final List<Value> values = new ArrayList<>();

        for(final Field field : getClass().getDeclaredFields()) {
            try{
                field.setAccessible(true);

                final Object o = field.get(this);

                if(o instanceof Value)
                    values.add((Value) o);
            }catch(IllegalAccessException e) {
                e.printStackTrace();
            }
        }

        return values;
    }
    
    public int getAnim() {
		return anim;
	}
    
    public void setAnim(int anim) {
		this.anim = anim;
	}
    
    public void evc(String message) {
    	EventChat.addchatmessage(message);
    }
    
    public void setState(boolean state) {
    	setEnabled(state);
		if (state == true) {
			onEnable();
		}
		else
		{
			onDisable();
		}
    }
}
