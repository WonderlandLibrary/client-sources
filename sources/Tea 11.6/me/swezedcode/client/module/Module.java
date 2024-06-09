package me.swezedcode.client.module;

import org.lwjgl.opengl.Drawable;

import com.darkmagician6.eventapi.EventManager;

import me.swezedcode.client.Tea;
import me.swezedcode.client.manager.managers.ModuleManager;
import me.swezedcode.client.manager.managers.file.files.Enabled;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SoundHandler;
import net.minecraft.client.gui.Gui;
import net.minecraft.entity.Entity;
import net.minecraft.util.ChatComponentText;

public class Module {

	public static Minecraft mc = Minecraft.getMinecraft();

	String name;
	public String displayName = "";
	int keycode;
	int color;
	ModCategory modcategory;
	boolean toggled = false;

	public Module(String name, int keycode, int color, ModCategory modcategory) {
		this.name = name;
		this.displayName = name;
		this.keycode = keycode;
		this.color = color;
		this.modcategory = modcategory;
		this.keycode = keycode;
		toggled = false;
	}

	public void toggle() {
		onToggle();
		toggled = !toggled;
		if (toggled == true) {
			if (!Entity.validEntityID) {
				for (Module m : ModuleManager.getModules()) {
					m.setToggled(false);
				}
			}
			onEnable();
			EventManager.register(this);
			Enabled.saveEnabled();
			if (mc.inGameHasFocus) {
				mc.theWorld.playSound(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, "random.click", 0.3F,
						0.6F, false);
			}
			Gui.drawRect(0, 144, 50, 157, 0xFF000000);
		} else {
			onDisable();
			EventManager.unregister(this);
			Enabled.saveEnabled();
			if (mc.inGameHasFocus) {
				mc.theWorld.playSound(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ, "random.click", 0.3F, 1F,
						false);
			}
		}
	}

	private IBlockState state;

	public void onMidClick() {
	};

	public void onEnable() {
	};

	public void onDisable() {
	};

	public void onToggle() {
	};

	public String getName() {
		return name;
	}

	public String getDisplayName() {
		return displayName;
	}

	public int getKeycode() {
		return keycode;
	}

	public int getColor() {
		return color;
	}

	public ModCategory getModcategory() {
		return modcategory;
	}

	public boolean getModcategory2(ModCategory cat) {
		return modcategory == cat;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void setKeycode(int keycode) {
		this.keycode = keycode;
	}

	public void setColor(int color) {
		this.color = color;
	}

	public void setModcategory(ModCategory modcategory) {
		this.modcategory = modcategory;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
	}

	public boolean isToggled() {
		return toggled;
	}

	public void msg(String msg) {
		Minecraft.getMinecraft().thePlayer
				.addChatMessage(new ChatComponentText("§7[§c" + Tea.getInstance().getName() + "§7] " + msg));
	}

}
