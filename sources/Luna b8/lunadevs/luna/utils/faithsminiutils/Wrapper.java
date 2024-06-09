package lunadevs.luna.utils.faithsminiutils;

import java.awt.Font;
import java.io.InputStream;

import lunadevs.luna.font.MistFontRenderer;
import lunadevs.luna.main.Luna;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.PlayerControllerMP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.ResourceLocation;

public class Wrapper {

	public static int client_build = 7;
	
	public static Minecraft mc = Minecraft.getMinecraft();
	public static final MistFontRenderer fontRenderer50 = new MistFontRenderer(getFont(70), true, 8);
	public static final MistFontRenderer fontRendererGUI = new MistFontRenderer(getFont(40), true, 8);
	public static final MistFontRenderer fontRendererBOLD = new MistFontRenderer(getFont2(60), true, 8);
	public static final MistFontRenderer fontRenderer = new MistFontRenderer(getFont(36), true, 8);
	public static final MistFontRenderer fontRendererMAIN = new MistFontRenderer(getFont3(250), true, 8);

	private static Font getFont(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Luna/Comfortaa-Bold.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("[Luna]: Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}

	private static Font getFont2(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Luna/Night_Ride.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("[Luna]: Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}

	private static Font getFont3(int size) {
		Font font = null;
		try {
			InputStream is = Minecraft.getMinecraft().getResourceManager()
					.getResource(new ResourceLocation("Luna/theboldfont.ttf")).getInputStream();
			font = Font.createFont(0, is);
			font = font.deriveFont(0, size);
		} catch (Exception ex) {
			ex.printStackTrace();
			System.out.println("[Luna]: Error loading font");
			font = new Font("default", 0, size);
		}
		return font;
	}

	public static String getClientName() {
		return "Luna";
	}
	
	public static int getClientNumber() {
		return 7;
	}
	
	public static String getClientBuild() {
		return "b7";
	}
	
	public static EntityPlayerSP getPlayer() {
		Minecraft.getMinecraft();
		return Minecraft.thePlayer;
	}
	
	public static WorldClient getWorld(){
		Minecraft.getMinecraft();
		return Minecraft.theWorld;
	}
	
	  public static PlayerControllerMP getPlayerController()
	  {
	    return Minecraft.getMinecraft().playerController;
	  }
	  

}
