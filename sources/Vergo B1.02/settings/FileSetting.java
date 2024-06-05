package xyz.vergoclient.settings;

import java.io.File;
import java.util.ArrayList;

import com.google.gson.annotations.SerializedName;

import xyz.vergoclient.event.Event;
import xyz.vergoclient.event.impl.EventTick;
import xyz.vergoclient.modules.ModuleManager;
import xyz.vergoclient.modules.OnEventInterface;
import net.minecraft.client.Minecraft;

public class FileSetting extends ModeSetting implements OnEventInterface {
	
	@SerializedName(value = "dir")
	public File dir;
	
	public FileSetting(String name, File dirPath) {
		super(name, "");
		ArrayList<String> modes = new ArrayList<>();
		modes.add("None");
		modes.addAll(this.modes);
		this.modes = modes;
		this.dir = dirPath;
		setFileList();
		subscribeToEvents();
	}
	
	public void subscribeToEvents() {
		if (!ModuleManager.eventListeners.contains(this))
			ModuleManager.eventListeners.add(this);
	}
	
	public void unsubscribeFromEvents() {
		if (ModuleManager.eventListeners.contains(this))
			ModuleManager.eventListeners.remove(this);
	}
	
	@Override
	public void onEvent(Event e) {
		
		if (e instanceof EventTick && e.isPre() && Minecraft.getMinecraft().thePlayer.ticksExisted % 100 == 0) {
			try {
				setFileList();
			} catch (Exception e2) {
				
			}
		}
		
	}
	
	public void setFileList() {
		
		String[] files = dir.list();
		
		String oldFile;
		try {
			oldFile = getMode();
		} catch (Exception e) {
			oldFile = "None";
		}
		
		modes.clear();
		modes.add("None");
		
		for (String fileName : files)
			modes.add(fileName);
		
		if (index <= modes.size() - 1)
			// Doesn't trigger a setting change event
			for (String string : modes) {
				if (string.equals(oldFile)) {
					index = modes.indexOf(string);
				}
			}
			// Does trigger a setting change event
//			setMode(oldFile);
		else
			index = 0;
		
		if (!is(getMode()))
			setMode("None");
		
	}
	
	public File getFile() {
		return is("None") ? null : new File(dir, getMode());
	}
	
}
