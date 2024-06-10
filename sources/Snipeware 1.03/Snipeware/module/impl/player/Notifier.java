package Snipeware.module.impl.player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import Snipeware.api.objects.Auction;
import Snipeware.api.objects.Bid;
import Snipeware.api.objects.Root;
import Snipeware.api.objects.Root2;
import Snipeware.api.objects.Root3;
import Snipeware.api.objects.Session;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ObjectMapper;

import Snipeware.Client;
import Snipeware.api.annotations.Handler;
import Snipeware.events.packet.EventPacketReceive;
import Snipeware.events.player.EventKeyPress;
import Snipeware.events.player.EventMotionUpdate;
import Snipeware.module.Module;
import Snipeware.util.other.Logger;
import Snipeware.util.other.PlayerUtil;
import Snipeware.util.other.TimeHelper;
import Snipeware.value.impl.BooleanValue;
import Snipeware.value.impl.EnumValue;
import Snipeware.value.impl.NumberValue;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiMainMenu;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.multiplayer.GuiConnecting;
import net.minecraft.network.EnumConnectionState;
import net.minecraft.network.handshake.client.C00Handshake;
import net.minecraft.network.login.client.C00PacketLoginStart;
import net.minecraft.network.play.server.S02PacketChat;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import net.minecraft.util.BlockPos;

public class Notifier extends Module {

	private boolean shouldcall = false;
	private TimeHelper Delay = new TimeHelper();
	private TimeHelper Delay2 = new TimeHelper();
	private Root3 root;
	private int OldValue;
	private int NewValue;
	private boolean exec;

	public Notifier() {
		super("Notifier",0, ModuleCategory.PLAYER);
	}
	
	public void onEnable() {
		Delay.reset();
		Delay2.reset();
		super.onEnable();
	}
	
	public void onDisable() {
		super.onDisable();
	}
	

	//1064ee95-6c9f-4bb0-a281-491f594bbb0c
	
	//UUID c99cc257-4b91-434b-9527-3e503c8ccd2d

	@Handler
	public void onMotionUpdate(final EventMotionUpdate event){
		if(Delay2.isDelayComplete(100)) {
			if(exec == false) {
				OldValue = root.watchdog_total;
				exec = true;
			}
		}

		if(Delay.isDelayComplete(1500)) {
			shouldcall = true;
		}
		if(Delay.isDelayComplete(1550)) {
			shouldcall = false;
			exec = false;
			Delay.reset();
		}
		
			Thread thread = new Thread(new Runnable() {
				 public void run() {
					 if(shouldcall) {
					 try {
						ObjectMapper om = new ObjectMapper();
						String s1 = Caller("https://api.hypixel.net/punishmentstats?key=1064ee95-6c9f-4bb0-a281-491f594bbb0c&uuid=c99cc257-4b91-434b-9527-3e503c8ccd2d");
						root = om.readValue(s1, Root3.class);
						System.out.println(root.watchdog_total);
						
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				 }
			}); 
			thread.start();
	}
	
	
	public static String Caller(String _url) throws Exception {
        URL url = new URL(_url);
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("GET");
        con.setRequestProperty("User-Agent", "Mozilla/5.0");
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while((inputLine = in.readLine()) != null){
            response.append(inputLine);
        }
        in.close();
        
        return response.toString();
        
    }
} 