package com.darkcart.xdolf.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.Module;
import com.darkcart.xdolf.Wrapper;
import com.darkcart.xdolf.altmanager.Alt;
import com.darkcart.xdolf.altmanager.Manager;
import com.darkcart.xdolf.clickgui.XdolfGuiClick;
import com.darkcart.xdolf.clickgui.elements.XdolfWindow;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.mods.world.XRay;

public class FileManager
{
	public File xdolfDir;
	
	public FileManager()
	{
		xdolfDir = new File(Wrapper.getMinecraftDir() + File.separator + "Xdolf");
		if(!xdolfDir.exists())
		{
			xdolfDir.mkdirs();
		}
		loadKeybinds();
		loadGuiSettings();
		loadXrayList();
		loadHacks();
		loadFriends();
		loadMacros();
		loadWaypoints();
	}
	
	public void saveMacros() {
		try {
			File file = new File(xdolfDir.getAbsolutePath(), "macros.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(Macro m : Macro.macroList) {
				int getKey = m.getKey();
				if (getKey <= 114)
				{
					out.write(m.getCommand() + ":" + Keyboard.getKeyName(m.getKey()));
					out.write("\r\n");
				}
			}
			out.close(); 
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadMacros() {
		try {
			File file = new File(xdolfDir.getAbsolutePath(), "macros.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null) {
				String curLine = line.toLowerCase().trim();
				String[] s = curLine.split(":");
				String cmd = s[0];
				int id = Keyboard.getKeyIndex(s[1].toUpperCase());
				Macro m = new Macro(id, cmd);
			}
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
			saveMacros();
		}
	}
	
	public void saveKeybinds() {
		try {
			File file = new File(xdolfDir.getAbsolutePath(), "keys.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(Module mod: Hacks.hackList) {
				int getKey = mod.getKey();
				if (getKey <= 114)
				{
					out.write("key-" + mod.getName().toLowerCase().replace(" ", "") + ":" + Keyboard.getKeyName(getKey));
					out.write("\r\n");
				}
			}
			out.close();
		} catch(Exception e) {
			Wrapper.addChatMessage("Failed to save keybind!");
		}
	}
	
	public void loadKeybinds()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "keys.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				String curLine = line.toLowerCase().trim();
				String[] s = curLine.split(":");
				String hack = s[0];
				int id = Keyboard.getKeyIndex(s[1].toUpperCase());
				for(Module mod: Hacks.hackList)
				{
					if(hack.equalsIgnoreCase("key-" + mod.getName().toLowerCase().replace(" ", "")))
					{
						mod.setKey(id);
					}
				}
			}
			br.close();
		}catch(Exception err)
		{
			err.printStackTrace();
			saveKeybinds();
			System.out.println("[Xdolf] Failed to initialize Xdolf, tell Sgt Pepper or x0XP. " + err.toString());
			err.printStackTrace();
			
			String logString = "FT|CrashLog\r\n[PLAIN]\r\n---Begin plain text---\r\n";
			logString += "Console Log:\r\n";
			logString += "[Xdolf] Failed to initialize Xdolf, tell Sgt Pepper or x0XP. " + err.toString() + "\r\n\r\n";
			for(StackTraceElement ele: err.getStackTrace()) {
				logString += ele.getClassName() + " " + ele.toString() + "\r\n";
			}
			writeCrash(logString);
		}
	}
	
	public void saveAlts()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "alts.txt");
			PrintWriter writer = new PrintWriter(new FileWriter(file));
			for(Alt alt: Manager.altList)
			{
				writer.println(alt.getFileLine());
			}
			writer.close();
		}catch(Exception error)
		{
			error.printStackTrace();
		}
	}
	
	public void loadAlts()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "alts.txt");
			FileInputStream fstream = new FileInputStream(file);
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String rLine;
			while((rLine = br.readLine()) != null)
			{
				String curLine = rLine;
				try
				{
					if(curLine.contains(":") && !curLine.trim().endsWith(":"))
					{
						String[] altInfo = curLine.split(":");
						Alt theAlt = new Alt(altInfo[0], altInfo[1]);
						if(!Manager.altList.contains(theAlt))
						{
							Manager.altList.add(theAlt);
						}
					}else
						if(!curLine.isEmpty() && curLine != null && !curLine.trim().isEmpty())
						{
							Alt theAlt = new Alt(curLine.replace(":", "").trim());
							if(!Manager.altList.contains(theAlt))
							{
								Manager.altList.add(theAlt);
							}
						}
				}catch(Exception error)
				{
					error.printStackTrace();
				}
			}
			br.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveGuiSettings() {
		try {
			File file = new File(xdolfDir, "gui.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(XdolfWindow w : XdolfGuiClick.windowList) {
				out.write(w.getTitle().toLowerCase() + " = " + w.getDragX() + " = " + w.getDragY() + " = " + w.isOpen() + " = " + w.isPinned());
				out.newLine();
			}
			out.close();
		} catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public void loadGuiSettings() {
		try {
			File file = new File(xdolfDir.getAbsolutePath(), "gui.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null) {
				String curLine = line.trim();
				String[] s = curLine.split(" = ");
				for(XdolfWindow w : XdolfGuiClick.windowList) {
					if(w.getTitle().toLowerCase().equalsIgnoreCase(s[0])) {
						w.setDragX(Integer.parseInt(s[1]));
						w.setDragY(Integer.parseInt(s[2]));
						w.setOpen(Boolean.parseBoolean(s[3]));
						w.setPinned(Boolean.parseBoolean(s[4]));
					}
				}
			}
			br.close();
		} catch(Exception e) {
			e.printStackTrace();
			saveGuiSettings();
		}
	}
	
	public void saveXrayList()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "xray.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(Block i: XRay.xrayBlocks)
			{
				String name = i.getLocalizedName().replaceAll("\\s+","_");
				out.write(name + "\r\n");
			}
			out.close();
		}catch(Exception e) {}
	}
	
	public void loadXrayList()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "xray.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				XRay.xrayBlocks.add(Block.getBlockFromName(line));
			}
			br.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			saveXrayList();
		}
	}
	
	public void saveHacks()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "hacks.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(Module mod: Hacks.hackList)
			{
				if(!mod.getName().equals("Spammer") && !mod.getName().equals("Freecam"))
				{
					out.write(mod.getName().toLowerCase().replace(" ", "") + ":" + mod.isEnabled());
					out.write("\r\n");
				}
			}
			out.close();
		}catch(Exception e) {}
	}
	
	public void writeCrash(String alah) {
		try {
			DateFormat format = new SimpleDateFormat("MM_dd_yyyy-HH_mm_ss");
			Date date = new Date();
			File file = new File(xdolfDir.getAbsolutePath(), "crashlog-".concat(format.format(date)).concat(".xen"));
			BufferedWriter outWrite = new BufferedWriter(new FileWriter(file));
			outWrite.write(alah);
			outWrite.close();
		} catch (Exception error) {
			System.out.println("Ohh the irony.");
		}
	}
	
	public void loadHacks()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "hacks.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				String curLine = line.toLowerCase().trim();
				String name = curLine.split(":")[0];
				boolean isOn = Boolean.parseBoolean(curLine.split(":")[1]);
				for(Module mod: Hacks.hackList)
				{
					if(mod.getName().toLowerCase().replace(" ", "").equals(name))
					{
						mod.setState(isOn);
					}
				}
			}
			br.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			saveHacks();
		}
	}
	
	public void saveFriends()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "friends.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(Friend friend: Wrapper.getFriends().friendsList)
			{
				out.write(friend.getName() + ":" + friend.getAlias());
				out.write("\r\n");
			}
			out.close();
		}catch(Exception e) {}
	}
	
	public void loadFriends()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "friends.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				String curLine = line.trim();
				String name = curLine.split(":")[0];
				String alias = curLine.split(":")[1];
				Wrapper.getFriends().addFriend(name, alias);
			}
			br.close();
		}catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	public void saveWaypoints()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "waypoints.txt");
			BufferedWriter out = new BufferedWriter(new FileWriter(file));
			for(Waypoint wayPoint : Waypoint.wayPoints)
			{
				out.write(wayPoint.getName() + ":" + (int)wayPoint.getX() + ":" + (int)wayPoint.getY() + ":" + (int)wayPoint.getZ());
				out.write("\r\n");
			}
			out.close();
		}catch(Exception e) {}
	}
	
	public void loadWaypoints()
	{
		try
		{
			File file = new File(xdolfDir.getAbsolutePath(), "waypoints.txt");
			FileInputStream fstream = new FileInputStream(file.getAbsolutePath());
			DataInputStream in = new DataInputStream(fstream);
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line;
			while((line = br.readLine()) != null)
			{
				String curLine = line.trim();
				String name = curLine.split(":")[0];
				int x = Integer.parseInt(curLine.split(":")[1]);
				int y = Integer.parseInt(curLine.split(":")[2]);
				int z = Integer.parseInt(curLine.split(":")[3]);
				
				Waypoint point = new Waypoint(name, x, y, z, RenderManager.renderPosX, RenderManager.renderPosY, RenderManager.renderPosZ);
			}
			br.close();
		}catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("ERROR WAYPOINTS!");
		}
	}
}
