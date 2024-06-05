package zamorozka.module;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.TreeMap;

import de.Hero.clickgui.Translate;
import de.Hero.settings.Setting;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.SPacketChat;
import zamorozka.event.EventManager;
import zamorozka.main.Zamorozka;
import zamorozka.modules.VISUALLY.HUD;
import zamorozka.notification.NotificationPublisher;
import zamorozka.notification.NotificationType;
import zamorozka.ui.EntityUtils;
import zamorozka.ui.FileManager;

public class Module {
    private final Translate translate = new Translate(0.0F, 0.0F);
	public static boolean hasModes;
	protected int key;
	private Category category;
	private String displayname, suffix;
	protected boolean isEnabled;
	private static boolean toggled;
	private double x;
    private boolean enabled, isHidden;
	private double y;
	private boolean extended;
	public String name, displayName;

	public boolean isExtended() {
		return this.extended;
	}
	
    public String getSuffix() {
        return suffix;
    }
    
    public boolean isHidden() {
        return isHidden;
    }
    public void setHidden(boolean hidden) {
        isHidden = hidden;
    }
	
    public void toggle1() {
        enabled = !enabled;

        if (enabled) {
            EventManager.register(this);
            onEnable();
        } else {
            EventManager.unregister(this);
            onDisable();
        }
    }

    public boolean isEnabled() {
        return enabled;
    }
	
	public boolean isToggled() {
		return this.toggled;
	}
	
    public Translate getTranslate() {
        return translate;
    }

	public boolean nullCheck() {
		return mc.player == null || mc.world == null;
	}

	public static Minecraft mc = Minecraft.getMinecraft();

	public Module(String name, int key, Category category) {
		this.name = name;
		this.key = key;
		this.category = category;
		setup();
	}

	static {
		hasModes = false;
		mc = net.minecraft.client.Minecraft.getMinecraft();
	}

	protected static void sendPacket(final Packet packet) {
		mc.getConnection().sendPacket(packet);
	}

	public void onEnable() {
		Zamorozka.instance.eventManager.register(this);
		/*if(Zamorozka.settingsManager.getSettingByName("ModuleSound").getValBoolean() && ModuleManager.getModule(HUD.class).getState()) {
			Minecraft.player.playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_ON, 1.8F, 1.8F * 0.4F);
		}*/
	}

	public void onDisable() {
		Zamorozka.instance.eventManager.unregister(this);
		/*if(Zamorozka.settingsManager.getSettingByName("ModuleSound").getValBoolean() && ModuleManager.getModule(HUD.class).getState()) {
			Minecraft.player.playSound(SoundEvents.BLOCK_WOOD_BUTTON_CLICK_OFF, 1.8F, 1.8F * 0.4F);
		}*/
	}

	public String getName() {
		return name;
	}

	public Category getCategory() {
		return this.category;
	}

	public boolean getState() {
		return this.isEnabled;
	}

	public int getCategoryColor() {
		switch (category) {
		case COMBAT:
			return new Color(255, 120, 50, 255).getRGB();
		case TRAFFIC:
			return new Color(50, 200, 255, 255).getRGB();
		case PLAYER:
			return new Color(50, 255, 50, 255).getRGB();
		case VISUALLY:
			return new Color(255, 255, 60, 255).getRGB();
		case Exploits:
			return new Color(255, 60, 255, 255).getRGB();
		case Zamorozka:
			return new Color(255, 50, 50, 255).getRGB();
		}
		return -1;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public void onLeftClick() {
	}

	public void toggleModule() {
		setState(!getState());
	}

	public void onToggle() {
	}

	public void onUpdate() {
	}

	public void onRender() {
	}

	public void setup() {
	}

	public final boolean isCategory(Category s) {
		if (s == this.category) {
			return true;
		}
		return false;
	}

	public boolean onSendChatMessage(String s) {
		return true;
	}

	public boolean onRecieveChatMessage(SPacketChat packet) {
		return true;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public static boolean isToggled(String s) {
		return toggled;
	}

	public void toggle() {
		toggled = !toggled;
		onToggle();
		if (toggled) {
			onEnable();
		} else {
			onDisable();
		}
	}

	public boolean setState(boolean state) {
		onToggle();
		if (state) {
			FileManager.saveModules();
			onEnable();
			this.isEnabled = true;
		} else {
			onDisable();
			this.isEnabled = false;
		}
		return state;
	}

	public int getKey() {
		return key;
	}

	public void addNumberOption(String s, double d, double d0, double d1) {
		Zamorozka.settingsManager.rSetting(new Setting(s, this, d, d0, d1, false));
	}

	public void addNumberOption(String s, double d, double d0, double d1, boolean b) {
		Zamorozka.settingsManager.rSetting(new Setting(s, this, d, d0, d1, b));
	}

	public boolean getBool(String s) {
		return Zamorozka.settingsManager.getSettingByName(s).getValBoolean();
	}

	public double getDouble(String s) {
		return Zamorozka.settingsManager.getSettingByName(s).getValDouble();
	}

	protected String getString(String s) {
		return Zamorozka.settingsManager.getSettingByName(s).getValString();
	}

	public void addModes(String[] a) {
		hasModes = true;
		java.util.ArrayList a0 = new java.util.ArrayList();
		int i = a.length;
		int i0 = 0;
		for (; i0 < i; i0 = i0 + 1) {
			a0.add((Object) a[i0]);
		}
		Zamorozka.settingsManager.rSetting(new Setting("Mode", this, a[0], a0));
	}

	public void addBool(String s, boolean b) {
		Zamorozka.settingsManager.rSetting(new Setting(s, this, b));
	}

	protected boolean isMode(String s) {
		return this.getString("Mode").equalsIgnoreCase(s);
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDisplayName() {
		return displayName == null ? name : displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public void updateSettings() {
		// TODO Auto-generated method stub

	}

	public double getX() {
		return this.x;
	}

	public double getY() {
		return this.y;
	}

}