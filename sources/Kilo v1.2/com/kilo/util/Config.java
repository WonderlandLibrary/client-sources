package com.kilo.util;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.net.URL;
import java.util.Iterator;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.kilo.Kilo;
import com.kilo.manager.ChatSpamManager;
import com.kilo.manager.DatabaseManager;
import com.kilo.manager.HackFriendManager;
import com.kilo.manager.MacroManager;
import com.kilo.manager.WaypointManager;
import com.kilo.mod.Module;
import com.kilo.mod.ModuleManager;
import com.kilo.mod.ModuleOption;
import com.kilo.mod.ModuleSubOption;
import com.kilo.mod.toolbar.dropdown.Interactable;
import com.kilo.mod.util.FollowHandler;
import com.kilo.mod.util.XrayHandler;
import com.kilo.ui.inter.slotlist.part.HackFriend;
import com.kilo.ui.inter.slotlist.part.Macro;
import com.kilo.ui.inter.slotlist.part.Waypoint;

public class Config {

	private static final File dir = new File("./KiLO");
	private static final File client = new File(dir+"/client");
	private static final File modules = new File(dir+"/modules");
	
	private static final File chatSpam = new File(dir+"/chatspam");
	private static final File follow = new File(dir+"/follow");
	private static final File friends = new File(dir+"/friends");
	private static final File macros = new File(dir+"/macros");
	private static final File waypoints = new File(dir+"/waypoints");
	private static final File xray = new File(dir+"/xray");
	
	public static void saveFiles() {
		if (!dir.exists()) {
			dir.mkdirs();
		}
		saveModules();
		saveChatSpam();
		saveFollow();
		saveHackFriends();
		saveMacros();
		saveWaypoints();
		saveXray();
	}
	
	public static void loadFiles() {
		if (!dir.exists()) {
			saveFiles();
			return;
		}
		loadModules();
		loadChatSpam();
		loadFollow();
		loadHackFriends();
		loadMacros();
		loadWaypoints();
		loadXray();
		
		loadSounds();
	}
	
	public static void saveClient() {
		if (!dir.exists()) {
			dir.mkdirs();
		}
		
		if (Kilo.kilo().client != null) {
			if (!client.exists()) {
				try {
					client.createNewFile();
				} catch (Exception e) {}
			}
			
			JSONObject obj = new JSONObject();
			obj.put("client_id", Kilo.kilo().client.clientID);
			
			Gson gson = new GsonBuilder().setPrettyPrinting().create();
			JsonParser jp = new JsonParser();
			JsonElement je = jp.parse(obj.toJSONString());
			String pretty = gson.toJson(je);
			
			try {
				client.delete();
				client.createNewFile();
				
				FileWriter file = new FileWriter(client.getAbsolutePath());
				file.write(pretty);
				file.flush();
				file.close();
			} catch (Exception exception) {}
		} else {
		}
	}
	
	public static String loadClientID() {
		if (client.exists()) {
			JSONParser parser = new JSONParser();
			
			try {
				Object file = parser.parse(new FileReader(client.getAbsolutePath()));
				JSONObject obj = (JSONObject) file;
				
				return (String) obj.get("client_id");
			} catch (Exception e) {
				return "";
			}
		} else {
			return "";
		}
	}
	
	public static void saveModules() {
		if (!modules.exists()) {
			try {
				modules.createNewFile();
			} catch (Exception exception) {}
		}
		
		JSONObject obj = new JSONObject();
		
		JSONArray moduleList = new JSONArray();
		for(Module m : ModuleManager.list()) {
			JSONObject module = new JSONObject();
			
			module.put("name", m.finder);
			module.put("active", m.active);
			
			JSONArray options = new JSONArray();
			for(ModuleOption mo : m.options) {
				
				if (mo.type == Interactable.TYPE.SETTINGS) {
					continue;
				}
				
				JSONObject option = new JSONObject();

				option.put("name", mo.name);
				option.put("value", mo.value);
				
				if (mo.subOptions == null) {
					options.add(option);
					continue;
				}
				
				JSONArray subOptions = new JSONArray();
				for(ModuleSubOption mso : mo.subOptions) {
					JSONObject subOption = new JSONObject();

					subOption.put("name", mso.name);
					subOption.put("value", mso.value);
					
					subOptions.add(subOption);
				}
				
				option.put("options", subOptions);
				
				options.add(option);
			}
			
			module.put("options", options);
			
			moduleList.add(module);
		}
		
		obj.put("modules", moduleList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(obj.toJSONString());
		String pretty = gson.toJson(je);
		
		try {
			modules.delete();
			modules.createNewFile();
			
			FileWriter file = new FileWriter(modules.getAbsolutePath());
			file.write(pretty);
			file.flush();
			file.close();
		} catch (Exception exception) {}
	}
	
	public static void loadModules() {
		JSONParser parser = new JSONParser();
		
		try {
			Object file = parser.parse(new FileReader(modules.getAbsolutePath()));
			
			JSONObject obj = (JSONObject) file;
			
			JSONArray moduleList = (JSONArray) obj.get("modules");
			Iterator<JSONObject> iter_moduleList = moduleList.iterator();

			while(iter_moduleList.hasNext()) {
				JSONObject module = iter_moduleList.next();
				
				String name = (String) module.get("name");
				boolean active = (Boolean) module.get("active");

				Module m = ModuleManager.get(name);
				if (m == null) {
					continue;
				}
				if (!ModuleManager.isBlackListed(m)) {
					m.active = active;
					if (active) {
						ModuleManager.enable(m);
					} else {
						ModuleManager.disable(m);
					}
				}
				
				JSONArray options = (JSONArray) module.get("options");
				Iterator<JSONObject> iter_options = options.iterator();
				
				while(iter_options.hasNext()) {
					JSONObject option = iter_options.next();
					
					String oName = (String) option.get("name");
					Object oValue = option.get("value");

					ModuleOption mO = null;
					try {
						mO = m.options.get(m.getOption(oName));
					} catch (Exception e) {
						continue;
					}
					
					mO.value = oValue;
					
					JSONArray subOptions = (JSONArray) option.get("options");
					if (subOptions == null) {
						continue;
					}
					Iterator<JSONObject> iter_subOptions = subOptions.iterator();
					
					while(iter_subOptions.hasNext()) {
						JSONObject subOption = iter_subOptions.next();
						
						String soName = (String) subOption.get("name");
						Object soValue = subOption.get("value");
						
						ModuleSubOption mSO = mO.subOptions.get(m.getSubOption(oName, soName));
						mSO.value = soValue;
					}
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			saveModules();
		}
	}
	
	public static void saveHackFriends() {
		if (!friends.exists()) {
			try {
				friends.createNewFile();
			} catch (Exception exception) {}
		}
		
		JSONObject obj = new JSONObject();
		
		JSONArray friendList = new JSONArray();
		
		for(HackFriend f : HackFriendManager.getList()) {
			JSONObject friend = new JSONObject();
			friend.put("name", f.name);
			friend.put("nickname", f.nickname);
			friendList.add(friend);
		}
		obj.put("friends", friendList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(obj.toJSONString());
		String pretty = gson.toJson(je);
		
		try {
			friends.delete();
			friends.createNewFile();
			
			FileWriter file = new FileWriter(friends.getAbsolutePath());
			file.write(pretty);
			file.flush();
			file.close();
		} catch (Exception exception) {}
	}
	
	public static void loadHackFriends() {
		JSONParser parser = new JSONParser();
		
		try {
			Object file = parser.parse(new FileReader(friends.getAbsolutePath()));
			
			JSONObject obj = (JSONObject) file;
			
			JSONArray friendList = (JSONArray) obj.get("friends");
			
			for(Object o : friendList) {
				JSONObject friend = (JSONObject) o; 
				HackFriendManager.addHackFriend(new HackFriend(String.valueOf(friend.get("name")), String.valueOf(friend.get("nickname"))));
			}
		} catch (Exception e) {
			e.printStackTrace();
			saveHackFriends();
		}
	}
	
	public static void saveWaypoints() {
		if (!waypoints.exists()) {
			try {
				waypoints.createNewFile();
			} catch (Exception exception) {}
		}
		
		JSONObject obj = new JSONObject();
		
		JSONArray waypointList = new JSONArray();
		for(Waypoint w : WaypointManager.getList()) {
			JSONObject waypoint = new JSONObject();
			
			waypoint.put("name", w.name);
			waypoint.put("x", w.x);
			waypoint.put("y", w.y);
			waypoint.put("z", w.z);
			waypoint.put("color", w.color);
			
			waypointList.add(waypoint);
		}
		
		obj.put("waypoints", waypointList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(obj.toJSONString());
		String pretty = gson.toJson(je);
		
		try {
			waypoints.delete();
			waypoints.createNewFile();
			
			FileWriter file = new FileWriter(waypoints.getAbsolutePath());
			file.write(pretty);
			file.flush();
			file.close();
		} catch (Exception exception) {}
	}
	
	public static void loadWaypoints() {
		JSONParser parser = new JSONParser();
		
		try {
			Object file = parser.parse(new FileReader(waypoints.getAbsolutePath()));
			
			JSONObject obj = (JSONObject) file;
			
			JSONArray moduleList = (JSONArray) obj.get("waypoints");
			Iterator<JSONObject> iter_moduleList = moduleList.iterator();

			while(iter_moduleList.hasNext()) {
				JSONObject waypoint = iter_moduleList.next();
				
				String name = (String) waypoint.get("name");
				try {
					double x = (Double) waypoint.get("x");
					double y = (Double) waypoint.get("y");
					double z = (Double) waypoint.get("z");
					long color = (Long) waypoint.get("color");

					WaypointManager.addWaypoint(new Waypoint(name, x, y, z, (int)color));
				} catch (Exception e) {
					continue;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			saveWaypoints();
		}
	}
	
	public static void saveXray() {
		if (!xray.exists()) {
			try {
				xray.createNewFile();
			} catch (Exception exception) {}
		}
		
		JSONObject obj = new JSONObject();
		
		JSONArray xrayList = new JSONArray();
		
		for(String s : XrayHandler.blocks.keySet()) {
			if (XrayHandler.blocks.get(s)) {
				xrayList.add(s);
			}
		}
		obj.put("blocks", xrayList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(obj.toJSONString());
		String pretty = gson.toJson(je);
		
		try {
			xray.delete();
			xray.createNewFile();
			
			FileWriter file = new FileWriter(xray.getAbsolutePath());
			file.write(pretty);
			file.flush();
			file.close();
		} catch (Exception exception) {}
	}
	
	public static void loadXray() {
		JSONParser parser = new JSONParser();
		
		try {
			Object file = parser.parse(new FileReader(xray.getAbsolutePath()));
			
			JSONObject obj = (JSONObject) file;
			
			JSONArray xrayList = (JSONArray) obj.get("blocks");
			
			for(Object o : xrayList) {
				XrayHandler.blocks.put(String.valueOf(o), true);
			}
		} catch (Exception e) {
			e.printStackTrace();
			saveXray();
		}
	}
	
	public static void saveChatSpam() {
		if (!chatSpam.exists()) {
			try {
				chatSpam.createNewFile();
			} catch (Exception exception) {}
		}
		
		JSONObject obj = new JSONObject();
		
		JSONArray list = new JSONArray();
		list.addAll(ChatSpamManager.getList());
		obj.put("messages", list);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(obj.toJSONString());
		String pretty = gson.toJson(je);
		
		try {
			chatSpam.delete();
			chatSpam.createNewFile();
			
			FileWriter file = new FileWriter(chatSpam.getAbsolutePath());
			file.write(pretty);
			file.flush();
			file.close();
		} catch (Exception exception) {}
	}
	
	public static void loadChatSpam() {
		JSONParser parser = new JSONParser();
		
		try {
			Object file = parser.parse(new FileReader(chatSpam.getAbsolutePath()));
			
			JSONObject obj = (JSONObject) file;
			
			JSONArray list = (JSONArray) obj.get("messages");
			
			for(Object o : list) {
				ChatSpamManager.addMessage(String.valueOf(o));
			}
		} catch (Exception e) {
			e.printStackTrace();
			saveChatSpam();
		}
	}
	
	public static void saveFollow() {
		if (!follow.exists()) {
			try {
				follow.createNewFile();
			} catch (Exception exception) {}
		}
		
		JSONObject obj = new JSONObject();
		
		obj.put("player", FollowHandler.follow);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(obj.toJSONString());
		String pretty = gson.toJson(je);
		
		try {
			follow.delete();
			follow.createNewFile();
			
			FileWriter file = new FileWriter(follow.getAbsolutePath());
			file.write(pretty);
			file.flush();
			file.close();
		} catch (Exception exception) {}
	}
	
	public static void loadFollow() {
		JSONParser parser = new JSONParser();
		
		try {
			Object file = parser.parse(new FileReader(follow.getAbsolutePath()));
			
			JSONObject obj = (JSONObject) file;
			
			String player = (String) obj.get("player");

			FollowHandler.follow = player;
		} catch (Exception e) {
			e.printStackTrace();
			saveFollow();
		}
	}
	
	public static void loadSounds() {
		if (!Resources.soundNotification.exists()) {
			try {
				Resources.soundNotification.getParentFile().mkdirs();
				Resources.soundNotification.createNewFile();
				
				URL link = new URL(DatabaseManager.notificationSound);
		
				InputStream in = new BufferedInputStream(link.openStream());
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				byte[] buf = new byte[1024];
				int n = 0;
				while (-1 != (n = in.read(buf))) {
					out.write(buf, 0, n);
				}
				out.close();
				in.close();
				byte[] response = out.toByteArray();
		
				FileOutputStream fos = new FileOutputStream(Resources.soundNotification);
				fos.write(response);
				fos.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public static void saveMacros() {
		if (!macros.exists()) {
			try {
				macros.createNewFile();
			} catch (Exception exception) {}
		}
		
		JSONObject obj = new JSONObject();
		
		JSONArray macroList = new JSONArray();
		for(Macro m : MacroManager.getList()) {
			JSONObject macro = new JSONObject();
			
			macro.put("name", m.name);
			macro.put("command", m.command);
			macro.put("keybind", m.keybind);
			
			macroList.add(macro);
		}
		
		obj.put("macros", macroList);
		
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonParser jp = new JsonParser();
		JsonElement je = jp.parse(obj.toJSONString());
		String pretty = gson.toJson(je);
		
		try {
			macros.delete();
			macros.createNewFile();
			
			FileWriter file = new FileWriter(macros.getAbsolutePath());
			file.write(pretty);
			file.flush();
			file.close();
		} catch (Exception exception) {}
	}
	
	public static void loadMacros() {
		JSONParser parser = new JSONParser();
		
		try {
			Object file = parser.parse(new FileReader(macros.getAbsolutePath()));
			
			JSONObject obj = (JSONObject) file;
			
			JSONArray moduleList = (JSONArray) obj.get("macros");
			Iterator<JSONObject> iter_moduleList = moduleList.iterator();

			while(iter_moduleList.hasNext()) {
				JSONObject macro = iter_moduleList.next();
				
				String name = (String) macro.get("name");
				String command = (String) macro.get("command");
				try {
					int keybind = (Integer) macro.get("keybind");

					MacroManager.addMacro(new Macro(name, command, keybind));
				} catch (Exception e) {
					continue;
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
			saveMacros();
		}
	}
}
