package de.tired.module;

import de.tired.api.annotations.ModuleAnnotation;
import de.tired.api.util.misc.FileUtil;
import de.tired.event.EventManager;
import de.tired.interfaces.IHook;
import de.tired.module.impl.list.visual.Notifications;
import de.tired.notification.newnotifications.NotifyManager;
import de.tired.tired.Tired;
import net.minecraft.client.gui.ScaledResolution;

public abstract class Module implements IHook {

	public final String name = getClass().getAnnotation(ModuleAnnotation.class).name();

	public final String clickGUIText = getClass().getAnnotation(ModuleAnnotation.class).clickG();

	public String desc = getClass().getAnnotation(ModuleAnnotation.class).desc();

	public int key = getClass().getAnnotation(ModuleAnnotation.class).key();

	public boolean renderPreview = getClass().getAnnotation(ModuleAnnotation.class).renderPreview();

	public boolean allowRender = true;

	public final ModuleCategory moduleCategory = getClass().getAnnotation(ModuleAnnotation.class).category();

	public final ScaledResolution sr = new ScaledResolution(MC);

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
			this.desc = "";
	}

	public void onRender(int x, int y, int width, int height) {

	}

	public boolean state = false;

	public float posX;

	public abstract void onState();

	public abstract void onUndo();


	public void enableMod() {
		if (!state) {
			doEvent();
			setState(true);
			if (Tired.INSTANCE.moduleManager.findModuleByClass(Notifications.class).isState()) {
				NotifyManager.sendClientMessage("ModuleManager", "Toggled: " + name);
			}
			FileUtil.FILE_UTIL.saveModule();
		}
	}

	public void disableModule() {
		if (Tired.INSTANCE.moduleManager.findModuleByClass(Notifications.class).isState()) {
			NotifyManager.sendClientMessage("ModuleManager", "Disabled: " + name);
		}
		FileUtil.FILE_UTIL.saveModule();
		undoEvent();
		setState(false);
	}

	public void executeMod() {
		if (!state) {
			doEvent();
			setState(true);
			if (Tired.INSTANCE.moduleManager.findModuleByClass(Notifications.class).isState()) {
				NotifyManager.sendClientMessage("ModuleManager", "Toggled: " + name);
			}
			FileUtil.FILE_UTIL.saveModule();
			return;
		}
		if (Tired.INSTANCE.moduleManager.findModuleByClass(Notifications.class).isState()) {
			NotifyManager.sendClientMessage("ModuleManager", "Disabled: " + name);
		}
		FileUtil.FILE_UTIL.saveModule();
		undoEvent();
		setState(false);
	}

	public void unableModule() {
		if (state) {
			undoEvent();
			state = false;
			setState(false);
		}
	}

	public void setKey(int key) {
		this.key = key;
	}

	public String getName() {
		return name;
	}

	public String getNameWithSuffix() {
		return name + " " + desc;
	}

	public int getKey() {
		return key;
	}

	public ModuleCategory getModuleCategory() {
		return moduleCategory;
	}

	public boolean isState() {
		return state;
	}

	public void setState(boolean state) {
		this.state = state;
	}

	public void doEvent() {
		EventManager.register(this);
		if (MC.thePlayer == null) return;
		this.onState();
	}

	public void undoEvent() {
		EventManager.unregister(this);
		if (MC.thePlayer == null) return;
		this.onUndo();
	}

}
