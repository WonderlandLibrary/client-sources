package none.module.modules.render;

import org.lwjgl.input.Keyboard;

import com.mojang.realmsclient.gui.ChatFormatting;

import none.event.Event;
import none.event.RegisterEvent;
import none.event.events.EventPreMotionUpdate;
import none.event.events.EventTick;
import none.module.Category;
import none.module.Module;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class BlockHitAM extends Module{
	
	private static String[] modes = {"None", "Tap", "Tap2", "Vanilla", "Slide", "Sigma", "Exhibition", "Remix", "Crystalware", "Spin"};
	public static ModeValue abmode = new ModeValue("Block-Modes", "None", modes);
	public static BooleanValue item = new BooleanValue("Item", false);
	public BlockHitAM() {
		super("BlockAnimation", "BlockAnimation", Category.RENDER, Keyboard.KEY_NONE);
	}

	@Override
	@RegisterEvent(events = EventPreMotionUpdate.class)
	public void onEvent(Event event) {
		if (!isEnabled()) return;
		setDisplayName(getName() + ChatFormatting.WHITE + " " + abmode.getSelected());
	}

}
