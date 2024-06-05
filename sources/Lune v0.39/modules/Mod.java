package me.superskidder.lune.modules;

import java.util.ArrayList;
import java.util.List;

import me.superskidder.lune.Lune;
import me.superskidder.lune.guis.notification.Notification;
import me.superskidder.lune.manager.EventManager;
import me.superskidder.lune.manager.ModuleManager;
import me.superskidder.lune.utils.client.ClientUtils;
import me.superskidder.lune.utils.client.DevUtils;
import me.superskidder.lune.values.Value;
import me.superskidder.lune.values.type.NewMode;
import net.minecraft.client.Minecraft;

public class Mod {
	public String Name;
	public String DisplayName;
	public boolean Stage;
	public ModCategory Type;
	public int key;
	private float xanim, yanim;
	public static Minecraft mc = Minecraft.getMinecraft();
	public List<Value> values = new ArrayList<>();
	public List<NewMode> newModes = new ArrayList<>();
	public float optionAnim = 0;// present
	public float optionAnimNow = 0;// present
	public String description = "";

	public Mod(String name, ModCategory type, String description) {
    	if(Lune.flag < 0) {
    		name = DevUtils.lol(name);
    		description = DevUtils.lol(description);
    	}
		this.Name = name;
		this.Type = type;
		this.xanim = 0;
		this.yanim = 0;
		this.description = description;

	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return description;
	}

	public String getName() {
		return this.Name;
	}

	public String getDisplayName() {
		return this.DisplayName;
	}

	public boolean getState() {
		return this.Stage;
	}

	public ModCategory getType() {
		return this.Type;
	}

	public void setDisplayName(String name) {
		this.DisplayName = name;
	}

	public void setStage(boolean state) {
		for (Mod mod : ModuleManager.fakeModList) {
			if (this.getName().equals(mod.getName()) && state) {
				return;
			}
		}
		this.Stage = state;
		if (state) {
			if (mc.theWorld != null) {
				onEnabled();
			}
			EventManager.register(this);
		} else {
			EventManager.unregister(this);
			if (mc.theWorld != null) {
				onDisable();
			}
		}
		if (mc.theWorld != null) {
			if (this.getState()) {
				ClientUtils.sendClientMessage(this.Name + " Enabled", Notification.Type.SUCCESS);
			} else {
				ClientUtils.sendClientMessage(this.Name + " Disabled", Notification.Type.ERROR);
			}
		}
		// new FileUtils().SaveConfigs();
	}

	public void setStageWithoutNotification(boolean state) {
		for (Mod mod : ModuleManager.fakeModList) {
			if (this.getName().equals(mod.getName()) && state) {
				return;
			}
		}
		this.Stage = state;
		if (state) {
			EventManager.register(this);
			onEnabled();
		} else {
			EventManager.unregister(this);
			onDisable();
		}
	}

	public void setKey(int k) {
		this.key = k;
	}

	public int getKey() {
		return this.key;
	}

	public void toggle() {
		this.setStage(!this.getState());
	}

	public void onEnabled() {

	}

	public void onDisable() {

	}

	public void addValues(Value... values) {
		Value[] v1 = values;
		int vl = values.length;

		for (int i = 0; i < vl; ++i) {
			Value value = v1[i];
			this.values.add(value);
		}
	}

	public void addValues(NewMode... values) {
		NewMode[] v1 = values;
		int vl = values.length;

		for (int i = 0; i < vl; ++i) {
			NewMode value = v1[i];
			this.newModes.add(value);
		}
	}

	public List<Value> getValues() {
		return this.values;
	}

	public List<NewMode> getNewModes() {
		return this.newModes;
	}

	public float getXAnim() {
		return xanim;
	}

	public void setXAnim(float anim) {
		this.xanim = anim;
	}

	public float getYAnim() {
		return yanim;
	}

	public void setYAnim(float anim) {
		this.yanim = anim;
	}
}
