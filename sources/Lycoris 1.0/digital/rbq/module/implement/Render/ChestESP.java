package digital.rbq.module.implement.Render;

import digital.rbq.module.Category;
import digital.rbq.module.Module;
import digital.rbq.module.value.BooleanValue;
import digital.rbq.module.value.ColorValue;

import java.awt.*;

public class ChestESP extends Module {
	public static ColorValue chestESPColours = new ColorValue("StorageESP", "ChestESP", Color.ORANGE);

	public static BooleanValue Chest = new BooleanValue("StorageESP", "Chest", true);
	public static BooleanValue EnderChest = new BooleanValue("StorageESP", "EnderChest", true);

	public ChestESP() {
		super("StorageESP", Category.Render, false);
	}
}