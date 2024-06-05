package win.sightclient.script.values;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import win.sightclient.event.Event;
import win.sightclient.script.values.classes.FontRendererMC;
import win.sightclient.script.values.classes.NetHandlerMC;
import win.sightclient.script.values.classes.PlayerMC;
import win.sightclient.script.values.classes.TimerMC;
import win.sightclient.script.values.classes.TimerUtilsMC;
import win.sightclient.script.values.classes.WorldMC;

public class ScriptMC {

	public static ScriptMC instance = new ScriptMC();
	
	public PlayerMC thePlayer = new PlayerMC();
	public TimerMC timer = new TimerMC();
	public WorldMC theWorld = new WorldMC();
	public NetHandlerMC netHandler = new NetHandlerMC();
	public FontRendererMC fontRendererObj = new FontRendererMC();
	
	public void preRun() {
		timer.preRun();
		thePlayer.preRun();
		theWorld.preRun();
	}
	
	public void postRun() {
		timer.postRun();
		thePlayer.postRun();
		theWorld.postRun();
	}
	
	public void sendMessage(String message) {
		StringBuilder sb = new StringBuilder(EnumChatFormatting.GOLD + "[Sight] " + EnumChatFormatting.GRAY);
		if (message != null) {
			String[] args = message.split(" ");
			
			for (String str : args) {
				sb.append(str);
				sb.append(" ");
				sb.append(EnumChatFormatting.GRAY);
			}
		} else {
			sb.append("NULL");
		}
		
		Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage(new ChatComponentText(sb.toString()));
	}
	
	public TimerUtilsMC getTimerUtils() {
		return new TimerUtilsMC();
	}
 	
	public NetHandlerMC getNetHandler() {
		return this.netHandler;
	}
	
	public void drawRect(float drawXPosition, float g, float h, float k, int color) {
		Gui.drawRect(drawXPosition, g, h, k, color);
	}
}
