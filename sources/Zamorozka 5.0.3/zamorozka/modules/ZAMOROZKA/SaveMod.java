package zamorozka.modules.ZAMOROZKA;

import java.io.BufferedReader;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Random;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.network.play.server.SPacketUpdateBossInfo.Operation;
import zamorozka.main.Zamorozka;
import zamorozka.module.Category;
import zamorozka.module.Module;
import zamorozka.ui.FileManager;

public class SaveMod extends Module
{
	  
	  public SaveMod()
	  {
	    super("SaveBind", 0, Category.Zamorozka);
	  }
	  
	public void onEnable() {
			try
			{
				File file = new File(FileManager.Zamorozka.getAbsolutePath(), "cmdbind.txt");
				FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
				DataInputStream in = new DataInputStream(fstream);
				BufferedReader br = new BufferedReader(new InputStreamReader(in));
				String line;
				while((line = br.readLine()) != null)
				{
					String curLine = line.toLowerCase().trim();
					String[] s = curLine.split(":");
					String hack = s[1];
					int id = Keyboard.getKeyIndex(s[0].toUpperCase());
					if (mc.currentScreen instanceof Gui) return;
					
				     if(Keyboard.isKeyDown(id)) {
							mc.player.sendChatMessage(hack);
					}
					
				}
				br.close();
			}catch(Exception err){}

		
	  }
}
