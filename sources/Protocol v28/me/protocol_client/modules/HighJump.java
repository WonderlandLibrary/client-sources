package me.protocol_client.modules;

import darkmagician6.EventManager;
import me.protocol_client.Wrapper;
import me.protocol_client.module.Category;
import me.protocol_client.module.Module;
import me.protocol_client.thanks_slicky.properties.ClampedValue;

public class HighJump extends Module {

	public HighJump() {
		super("High Jump", "highjump", 0, Category.MOVEMENT, new String[] { "highjump", "jump" });
	}

	public final ClampedValue<Float>	height	= new ClampedValue<>("highjump_amount", 2f, 2f, 10f);

	public void runCmd(String s) {
		try {
			if (s.startsWith("height")) {
				String[] niggers = s.split(" ");
				float lilnigger = Float.parseFloat(niggers[2]);
				this.height.setValue(lilnigger);
				return;
			}
			Wrapper.invalidCommand("High Jump");
			Wrapper.tellPlayer("§7Invalid arguments. -highjump height <amount>");
		} catch (Exception e) {
			Wrapper.invalidCommand("High Jump");
			Wrapper.tellPlayer("§7Invalid arguments. -highjump height <amount>");
		}
	}
}
