package net.minecraft.client.main.neptune.Mod.Collection.Cmds;

import net.minecraft.client.main.neptune.Neptune;
import net.minecraft.client.main.neptune.Mod.Cmd;
import net.minecraft.client.main.neptune.Mod.Mod;
import net.minecraft.client.main.neptune.Mod.Cmd.Info;
import net.minecraft.client.main.neptune.Utils.ChatUtils;
import net.minecraft.client.main.neptune.Utils.FileManager;

import org.lwjgl.input.Keyboard;

@Info(name = "bind", syntax = { "<mod> <key>" }, help = "Binds a mod to a key")
public class BindCmd extends Cmd {

	private FileManager fileManager;

	public BindCmd() {
		this.fileManager = new FileManager();
	}
	
	@Override
	public void execute(String[] p0) throws Error {
		if (p0.length < 2) {
			this.syntaxError();
		} else {
			Mod mod = Neptune.getWinter().theMods.getMod(p0[0]);
			if (mod != null) {
				int key = Keyboard.getKeyIndex(p0[1].toUpperCase());
				if (key != -1) {
					mod.setBind(key);
					fileManager.saveFiles();
					//ChatUtils.sendMessageToPlayer(
				//			mod.getModName() + " has been set to: " + Keyboard.getKeyName(mod.getBind()));
				} else {
				//	ChatUtils.sendMessageToPlayer("Key not found!");
				}
			} else {
			//	ChatUtils.sendMessageToPlayer("Mod not found! (Hint: Dont include spaces or '.')");
			}
		}
	}

}
