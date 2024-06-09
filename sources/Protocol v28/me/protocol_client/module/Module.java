package me.protocol_client.module;

import java.util.Random;

import me.protocol_client.Protocol;
import me.protocol_client.Wrapper;
import me.protocol_client.files.allfiles.ToggledMods;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;

public class Module {
	private Category			moduleCategory;
	public String				name;
	public String				alias;
	public int					keyCode;
	private int					color;
	private boolean				toggled;
	protected static Minecraft	mc				= Minecraft.getMinecraft();
	private long				currentMS		= 0L;
	protected long				lastMS			= -1L;
	private boolean				isToggled;
	public String				getDisplayName	= name;
	private String				tag;
	public String[]				cmds;
	public boolean				show			= true;

	public Module(String name, String alias, int keyCode, Category moduleCategory, String[] cmds) {
		this.name = name;
		this.alias = alias;
		this.keyCode = keyCode;
		this.moduleCategory = moduleCategory;
		this.toggled = false;
		this.getDisplayName = name;
		this.cmds = cmds;
	}

	public String getDisplayName() {
		return getDisplayName;
	}

	public void setDisplayName(String displayName) {
		this.getDisplayName = displayName;
	}

	public static Module getModule(Class<? extends Module> modClass) {
		for (Module module : Protocol.getModules()) {
			if (module.getClass().equals(modClass)) {
				return module;
			}
		}
		return null;
	}

	public void toggle() {
		this.toggled = !this.toggled;
		if (this.toggled) {
				final Random ran = new Random();
				final StringBuilder sb = new StringBuilder();
				sb.append("0x");
				while (sb.length() < 10) {
					sb.append(Integer.toHexString(ran.nextInt()));
				}
				sb.setLength(8);
				this.setColor(Integer.decode(sb.toString()));
			onEnable();
		} else {
			onDisable();
		}
		 Wrapper.getPlayer().playSound("random.wood_click", 10f, 1f);
		ToggledMods.bindKey(this, toggled);
	}

	public void onRender() {
	}

	public void onEnable() {
	}

	public void onDisable() {
	}

	public void onToggle() {
	}

	public void onMiddleClick() {
	}

	public void runCmd(String command) {
	}

	public String[] getCmds() {
		return cmds;
	}

	public String getName() {
		return this.name;
	}

	public Boolean shouldShow() {
		return show;
	}

	public void setShowing(boolean show) {
		this.show = show;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getKeyCode() {
		return this.keyCode;
	}

	public String getTag() {
		return tag;
	}

	public void setTag(String tag) {
		this.tag = tag;
	}

	public static Module getModbyName(String name) {
		for (Module mod : Protocol.getModules()) {
			if (mod.getName().equalsIgnoreCase(name)) {
				return mod;
			}
		}
		return null;
	}

	public static Module getModbyAlias(String alias) {
		for (Module mod : Protocol.getModules()) {
			if (mod.getAlias().equalsIgnoreCase(alias)) {
				return mod;
			}
		}
		return null;
	}

	public String getAlias() {
		return this.alias;
	}

	public void setKeyCode(int keycode) {
		this.keyCode = keycode;
	}

	public Category getCategory() {
		return this.moduleCategory;
	}

	public final void setColor(int i) {
		this.color = i;
	}

	public final int getColor() {
		return color;
	}

	public final boolean isColor(int s) {
		if (s == (keyCode))
			return true;
		return false;
	}

	public void setToggled(boolean toggled) {
		this.toggled = toggled;
		if(toggled){
			final Random ran = new Random();
			final StringBuilder sb = new StringBuilder();
			sb.append("0x");
			while (sb.length() < 10) {
				sb.append(Integer.toHexString(ran.nextInt()));
			}
			sb.setLength(8);
			this.setColor(Integer.decode(sb.toString()));
		}
	}

	public boolean isToggled() {
		return this.toggled;
	}

	public final void updateMS() {
		currentMS = System.currentTimeMillis();
	}

	public final void updateLastMS() {
		lastMS = System.currentTimeMillis();
	}

	public final boolean hasTimePassedM(long MS) {
		return currentMS >= lastMS + MS;
	}

	public final boolean hasTimePassedS(float speed) {
		return currentMS >= lastMS + (long) (1000 / speed);
	}

	public final EntityPlayerSP player() {
		return mc.thePlayer;
	}

	public final WorldClient world() {
		return mc.theWorld;
	}

	public static Module getModule(String modName) {
		for (Module module : Protocol.getModules()) {
			if ((module.alias.equalsIgnoreCase(modName)) || (module.name.equalsIgnoreCase(modName))) {
				return module;
			}
		}
		return null;
	}

	public final void setStateSilent(boolean flag) {
		this.isToggled = flag;
		if (isToggled) {
			onEnable();
		} else {
			onDisable();
		}
	}
}
