package net.minecraft.client.main.neptune;

import net.minecraft.client.main.neptune.Mod.BoolOption;
import net.minecraft.client.main.neptune.Mod.Mods;
import net.minecraft.client.main.neptune.UI.GuiKeybindMgr;
import net.minecraft.client.main.neptune.UI.Mememan;
import net.minecraft.client.main.neptune.Utils.CmdMgr;
import net.minecraft.client.main.neptune.Utils.FileManager;
import net.minecraft.client.main.neptune.Utils.FriendUtils;

import org.darkstorm.minecraft.gui.theme.simple.SimpleTheme;
import org.darkstorm.minecraft.gui.util.GuiManagerDisplayScreen;
import org.lwjgl.opengl.Display;

public class Neptune {

	private static Neptune theClient = new Neptune();
	public Mods theMods;
	public FriendUtils friendUtils;
	public CmdMgr theCmds;
	private Mememan mememan;
	public FileManager fileManager;
	private GuiKeybindMgr guiKeybindMgr;
	private GuiManagerDisplayScreen guiKeybindDisplay;
	private GuiManagerDisplayScreen guiDisplay;
	public BoolOption legit;

	public static Neptune getWinter() {
		return theClient;
	}

	public Mememan getGuiMgr() {
		if (this.mememan == null) {
			this.mememan = new Mememan();
			this.mememan.setTheme(new SimpleTheme());
			this.getGuiMgr().setup();
		}
		return this.mememan;
	}

	public GuiManagerDisplayScreen getGui() {
		if (this.guiDisplay == null) {
			this.guiDisplay = new GuiManagerDisplayScreen(this.getGuiMgr());
		}
		return this.guiDisplay;
	}

	public GuiKeybindMgr getKeybindGuiMgr() {
		if (this.guiKeybindMgr == null) {
			this.guiKeybindMgr = new GuiKeybindMgr();
			this.guiKeybindMgr.setTheme(new SimpleTheme());
			this.guiKeybindMgr.setup();
		}
		return this.guiKeybindMgr;
	}

	public GuiManagerDisplayScreen getKeybindGui() {
		if (this.guiKeybindDisplay == null) {
			this.guiKeybindDisplay = new GuiManagerDisplayScreen(this.getKeybindGuiMgr());
		}
		return this.guiKeybindDisplay;
	}

	public static String getBuildName() {
		return "Memes";
	}

	public static String getName() {
		return "Memes";
	}

	public void start() {
		theMods = new Mods();
		legit = new BoolOption("Legit Mode");
		friendUtils = new FriendUtils();
		fileManager = new FileManager();
		theCmds = new CmdMgr();
		fileManager.loadFiles();
		Display.setTitle("Minecraft 1.8");
	}
}
