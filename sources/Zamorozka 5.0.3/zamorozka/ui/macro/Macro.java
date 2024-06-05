package zamorozka.ui.macro;

import net.minecraft.client.Minecraft;

public class Macro {

	int key;
	String value;

	public Macro(final int k, final String v) {
		this.key = k;
		this.value = v;
	}

	public void onMacro() {
		if (Minecraft.getMinecraft().player != null) {
			Minecraft.getMinecraft().player.sendChatMessage(this.value);
		}
	}

	public int getKey() {
		return this.key;
	}

	public String getValue() {
		return this.value;
	}

}
