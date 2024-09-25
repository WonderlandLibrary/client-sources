package none.module.modules.render;

import org.lwjgl.input.Keyboard;

import none.Client;
import none.event.Event;
import none.module.Category;
import none.module.Module;
import none.valuesystem.BooleanValue;
import none.valuesystem.ModeValue;
import none.valuesystem.NumberValue;

public class HUDEditer extends Module{
	
	public static BooleanValue ArrayList = new BooleanValue("Show-ArrayList", true);
	public static NumberValue<Integer> ArrayListSpeed = new NumberValue<>("RainBow-Speed", 50, 1, 100);
	public static BooleanValue Hotbar = new BooleanValue("Show-Hotbar", true);
	public static BooleanValue WaterMark = new BooleanValue("Show-WaterMark", true);
	public static BooleanValue Model = new BooleanValue("Show-Model", true);
	
	private static String[] logomode = {"Logo", "Logo2", "Logo3"};
	public static ModeValue logomodes = new ModeValue("Logo", "Logo3", logomode);
	
	private static String[] modes = {"Minecraft", "Roboto18", "Roboto15", "Roboto22", "Sigma", "Jigsaw", "Verdana", "MachineC", "BebasNeue"};
	public static ModeValue fontmode = new ModeValue("Font", "Minecraft", modes);
	
	public static BooleanValue Fasion = new BooleanValue("Fasion", false);
	
	public static BooleanValue ShowHotbar = new BooleanValue("Show-HotBar-BG", true);
	public static BooleanValue MovingHotbar = new BooleanValue("Moving-HotBar", true);
	public static BooleanValue ShowMovingHotbar = new BooleanValue("ShowMoving-HotBar", true);
	
	private static String[] rotmodes = {"NoRot", "Old", "New"};
	public static ModeValue rotations = new ModeValue("Rot", "New", rotmodes);
	
	public HUDEditer() {
		super("HUDEditer", "HUDEditer", Category.RENDER, Keyboard.KEY_NONE);
	}
	
	@Override
	protected void onEnable() {
		super.onEnable();
		this.toggle();
	}
	
	@Override
	protected void onDisable() {
		super.onDisable();
	}

	@Override
	public void onEvent(Event event) {
		
	}
}
