package Hydro.module;

import java.util.List;

import com.google.gson.JsonObject;

import Hydro.Client;
import Hydro.ClickGui.settings.Setting;
import Hydro.event.Event;
import Hydro.notification.Color;
import Hydro.notification.NotificationManager;
import Hydro.notification.Type;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumChatFormatting;

public abstract class Module {
	
	private String name;
	public String displayName;
	private int key;
	private Category category;
	public String desc;
	public boolean enabled;
	public boolean visible;
	
	public Minecraft mc = Minecraft.getMinecraft();

	public Module(String name, int key, boolean visible, Category cat, String desc) {
		this.name = name;
		this.displayName = name;
		this.key = key;
		this.visible = visible;
		this.category = cat;
		this.desc = desc;
		enabled = false;
		setup();
	}
	
	public void setDisplayName(String name) {
		this.displayName = this.name + EnumChatFormatting.GRAY + " " + name;
	}
	
	public void saveToJson(JsonObject json) {
		json.addProperty("keyBind", this.getKey());
		json.addProperty("toggled", this.isEnabled());
	}

	public String getName() {
		return name;
	}
	
	public void onUpdate() {}
	public void onEnable() {}
	public void onDisable() {}
	public void setup() {}

	public void toggle() {
		enabled = !enabled;
		
		if(enabled) {
			NotificationManager.getNotificationManager().createNotification("Info", "Enabled " + this.getName(), true, 1500, Type.INFO, Color.GREEN);
			onEnable();
		}
		if(!enabled) {
			NotificationManager.getNotificationManager().createNotification("Info", "Disabled " + this.getName(), true, 1500, Type.INFO, Color.RED);
			onDisable();
		}
	}
	
	public void onEvent(Event e) {
		
	}
	
	public boolean isEnabled() {
		return enabled;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}
	
	public JsonObject save() {
        JsonObject object = new JsonObject();
        object.addProperty("toggled", this.isEnabled());
        object.addProperty("key", getKey());
        List<Setting> properties = Client.instance.settingsManager.getSettingsByMod(this);
        if (properties != null && !properties.isEmpty()) {
            JsonObject propertiesObject = new JsonObject();

            for (Setting property : properties) {
                switch (property.mode.toUpperCase()) {
                    case "CHECK": {
                        propertiesObject.addProperty(property.getName(), property.getValBoolean());
                        break;
                    }
                    case "COMBO": {
                        propertiesObject.addProperty(property.getName(), property.getValString());
                        break;
                    }
                    case "SLIDER": {
                        propertiesObject.addProperty(property.getName(), property.getValDouble());
                        break;
                    }
                }
            }

            object.add("Properties", propertiesObject);
        }
        return object;
    }

    public void load(JsonObject object) {
        if (object.has("toggled"))
            enabled = object.get("toggled").getAsBoolean();

        if (object.has("key"))
            setKey(object.get("key").getAsInt());

        List<Setting> settings = Client.instance.settingsManager.getSettingsByMod(this);

        if (object.has("Properties") && settings != null && !settings.isEmpty()) {
            JsonObject propertiesObject = object.getAsJsonObject("Properties");
            for (Setting property : settings) {
                if (propertiesObject.has(property.getName())) {
                    switch (property.mode.toUpperCase()) {
                        case "CHECK": {
                            property.setValBoolean(propertiesObject.get(property.getName()).getAsBoolean());
                            break;
                        }
                        case "COMBO": {
                            property.setValString(propertiesObject.get(property.getName()).getAsString());
                            break;
                        }
                        case "SLIDER": {
                            property.setValDouble(propertiesObject.get(property.getName()).getAsDouble());
                            break;
                        }
                    }
                }
            }
        }
    }
	
}
