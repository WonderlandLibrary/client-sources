package ooo.cpacket.ruby.manager;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.concurrent.CopyOnWriteArrayList;

import org.lwjgl.input.Keyboard;

import com.google.common.collect.Lists;

import net.minecraft.client.Minecraft;
import ooo.cpacket.lemongui.example.GuiModule;
import ooo.cpacket.ruby.module.Module;
import ooo.cpacket.ruby.module.Module.Category;
import ooo.cpacket.ruby.module.attack.AntiBot;
import ooo.cpacket.ruby.module.attack.KillAura;
import ooo.cpacket.ruby.module.attack.Regen;
import ooo.cpacket.ruby.module.attack.TPAura;
import ooo.cpacket.ruby.module.attack.Velocity;
import ooo.cpacket.ruby.module.memes.CakeFucker;
import ooo.cpacket.ruby.module.misc.AntiCheatBreaker;
import ooo.cpacket.ruby.module.misc.GodMode;
import ooo.cpacket.ruby.module.misc.NoFall;
import ooo.cpacket.ruby.module.move.Fly;
import ooo.cpacket.ruby.module.move.HighJump;
import ooo.cpacket.ruby.module.move.InventoryMove;
import ooo.cpacket.ruby.module.move.Jesus;
import ooo.cpacket.ruby.module.move.LongJump;
import ooo.cpacket.ruby.module.move.NoSlow;
import ooo.cpacket.ruby.module.move.Scaffold;
import ooo.cpacket.ruby.module.move.Speed;
import ooo.cpacket.ruby.module.move.Sprint;
import ooo.cpacket.ruby.module.move.Step;
import ooo.cpacket.ruby.module.render.Hud;
import ooo.cpacket.ruby.module.render.Nametags;
import ooo.cpacket.ruby.module.self.Phase;

public class ModuleManager {

	public CopyOnWriteArrayList<Module> modules = Lists.newCopyOnWriteArrayList();

	public void setup() {
		this.modules.add(new Nametags("Nametags", Keyboard.KEY_NONE, Category.VISUAL));
		this.modules.add(new KillAura("KillAura", Keyboard.KEY_R, Category.ATTACK));
		this.modules.add(new TPAura("TPAuraXD", Keyboard.KEY_K, Category.ATTACK));
		this.modules.add(new Regen("FastHeal", Keyboard.KEY_NONE, Category.ATTACK));
		this.modules.add(new Velocity("Velocity", Keyboard.KEY_L, Category.ATTACK));
		this.modules.add(new AntiBot("BotRemover", Keyboard.KEY_NONE, Category.WORLD));
		this.modules.add(new Speed("Speed", Keyboard.KEY_F, Category.MOVE));
		this.modules.add(new Scaffold("AutoPlatform", Keyboard.KEY_V, Category.MOVE));
		this.modules.add(new Sprint("Sprint", Keyboard.KEY_NONE, Category.MOVE));
		this.modules.add(new LongJump("LongJump", Keyboard.KEY_Z, Category.MOVE));
		this.modules.add(new NoSlow("NoSlow", Keyboard.KEY_NONE, Category.MOVE));
		this.modules.add(new Fly("Fly", Keyboard.KEY_G, Category.MOVE));
		this.modules.add(new Step("Step", Keyboard.KEY_APOSTROPHE, Category.MOVE));
		this.modules.add(new HighJump("HighJump", Keyboard.KEY_H, Category.MOVE));
		this.modules.add(new InventoryMove("InventoryMove", Keyboard.KEY_I, Category.MOVE));
		this.modules.add(new NoFall("NoFall", Keyboard.KEY_NONE, Category.MISC));
		this.modules.add(new GodMode("GodMeme", Keyboard.KEY_NONE, Category.MISC));
		this.modules.add(new Phase("Phase", Keyboard.KEY_NONE, Category.SELF));
		this.modules.add(new Jesus("Jesus", Keyboard.KEY_NONE, Category.SELF));
		this.modules.add(new CakeFucker("CakeFucker", Keyboard.KEY_J, Category.MEMES));
		this.modules.add(new AntiCheatBreaker("AntiCheatBreaker", Keyboard.KEY_J, Category.MISC));
		this.modules.add(new GuiModule());
		this.modules.add(new Hud("Hud", Keyboard.KEY_NONE, Category.VISUAL));
		this.rolf();
	}

	public void rolf() {
		modules.sort(new Comparator<Module>() {
			
			@Override
			public int compare(Module arg0, Module arg1) {
				int l1 = Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg0.getSuffixedName());
				int l2 = Minecraft.getMinecraft().fontRendererObj.getStringWidth(arg1.getSuffixedName());
				return l2 - l1;
			}

		});
	}

	public ArrayList<Module> getModulesInCategory(Category categoryIn) {
		ArrayList<Module> mods = new ArrayList<Module>();
		for (Module m : this.modules) {
			if (m.getCategory() == categoryIn)
				mods.add(m);
		}
		return mods;
	}
	
	public Module getModule(Class clazz) {
		for (Module m : this.modules) {
			if (m.getClass() == clazz)
				return m;
		}
		return null;
	}
	
}
