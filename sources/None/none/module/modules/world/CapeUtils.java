package none.module.modules.world;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.valuesystem.ModeValue;
import none.valuesystem.StringValue;

public class CapeUtils extends Module{
	
	public CapeUtils() {
		super("Cape", "Cape", Category.WORLD);
	}

	public ModeValue mode = new ModeValue("Mode", "None", new String[] {"None", "Optifine", "URL"});
	public StringValue optifinename = new StringValue("Optifine-Name", "Insert Name");
	public StringValue url = new StringValue("Url", "Insert Url");
	
	@Override
	@RegisterEvent(events = EventTick.class)
	public void onEvent(Event event) {
		setDisplayName(getName() + " " + ChatFormatting.WHITE + mode.getSelected());
	}

}
