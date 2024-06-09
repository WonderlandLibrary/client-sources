package chaos;

import java.io.File;
import java.io.IOException;

import org.lwjgl.opengl.Display;

import chaos.modules.ModuleManager;
import chaos.utils.Logger;
import net.minecraft.client.Minecraft;

public class chaos {
	
	public static chaos instance;
	public static String Client_Name = "Chaos";
	public String Client_Version = "b1.0";
	public String Client_Coder = "Exeptiq";
	public String Client_Prefix = "[Chaos]";
	public Logger logger;
	public static ModuleManager modulemanager;
	
	public File directory;
	
	public void startClient() throws IOException{
		instance = this;
		Display.setTitle(Client_Name + " " + Client_Version);
		System.out.println(Client_Name + Client_Version + " is ready");
		directory = new File(Minecraft.getMinecraft().mcDataDir, "Chaos");
			if(!directory.exists()) {
				directory.mkdir();
			}
			
		logger = new Logger();
		modulemanager = new ModuleManager();
	}
	
}
