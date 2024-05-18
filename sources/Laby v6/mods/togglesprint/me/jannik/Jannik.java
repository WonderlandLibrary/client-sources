package mods.togglesprint.me.jannik;

import java.io.IOException;

import mods.togglesprint.me.jannik.files.Files;
import mods.togglesprint.me.jannik.module.ModuleManager;
import mods.togglesprint.me.jannik.value.Value;
import mods.togglesprint.me.jannik.value.ValueManager;
import mods.togglesprint.me.jannik.value.values.Values;
//LabyClient src by Exeptiq
public class Jannik {
	
	public static String client_Name = "Vape";
	public static String client_Coder = "Exeptiq";
	public static String client_Version = "13.37";
	
	private static ModuleManager moduleManager;
	private static ValueManager valueManager;
	private static Files files;
	
	public static void startClient() throws IOException {
		moduleManager = new ModuleManager();
		valueManager = new ValueManager();
		files = new Files();		
	}

	public static ModuleManager getModuleManager() {
		return moduleManager;
	}
	
	public static ValueManager getValueManager() {
		return valueManager;
	}
}
