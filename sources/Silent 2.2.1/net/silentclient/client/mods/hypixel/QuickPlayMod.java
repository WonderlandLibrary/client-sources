package net.silentclient.client.mods.hypixel;

import com.google.common.reflect.TypeToken;
import net.minecraft.client.Minecraft;
import net.silentclient.client.Client;
import net.silentclient.client.event.EventTarget;
import net.silentclient.client.event.impl.ConnectToServerEvent;
import net.silentclient.client.event.impl.KeyEvent;
import net.silentclient.client.gui.quickplay.QuickplayGui;
import net.silentclient.client.mods.Mod;
import net.silentclient.client.mods.ModCategory;
import net.silentclient.client.mods.Setting;
import net.silentclient.client.mods.util.Server;
import org.lwjgl.input.Keyboard;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;

public class QuickPlayMod extends Mod {
    public static ArrayList<QuickplayModeType> hypixelQuickplayModes;
    public static ArrayList<QuickplayModeType> ruhypixelQuickplayModes;
    public static boolean sending = false;
    public static HashMap<Integer, Setting> hashMap;

    public QuickPlayMod() {
        super("Quickplay", ModCategory.MODS, "silentclient/icons/mods/quickplay.png");
    }

    @Override
    public void setup() {
        super.setup();
        this.addKeybindSetting("Open Menu", this, Keyboard.KEY_MINUS);
        initHypixelModes();
        initRuHypixelModes();
        updateHashMap();
    }

    private void initHypixelModes() {
        try {
            Client.logger.info("Initialising Hypixel Quickplay Modes");
            InputStream in = getClass().getResourceAsStream("/assets/minecraft/silentclient/mods/quickplay/modes_hypixel.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer content = new StringBuffer();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }

            Type listType = new TypeToken<ArrayList<QuickplayModeType>>() {
            }.getType();
            hypixelQuickplayModes = Client.getInstance().getGson().fromJson(content.toString(), listType);

            for (QuickplayModeType mode : hypixelQuickplayModes) {
                Client.logger.info(String.format("Initialising Hypixel Quickplay Mode (%s)", mode.name));
                mode.modes.forEach((command) -> {
                    Client.logger.info(String.format("Initialising Hypixel Quickplay Mode (%s) Command (%s)", mode.name, command.name));
                    this.addKeybindSetting("Quickplay Mode&Hypixel&" + command.command, this, -1);
                });
            }
        } catch (Exception err) {
            Client.logger.catching(err);
        }
    }

    public void updateHashMap() {
        HashMap<Integer, Setting> map = new HashMap<>();
        for(Setting setting : Client.getInstance().getSettingsManager().getSettingByMod(this)) {
            if(setting.getName().equals("Open Menu")) {
                map.put(setting.getKeybind(), setting);
                continue;
            }
            String[] args = setting.getName().split("&");
            if (((args[1].equals("Hypixel") && Server.isHypixel()) || (args[1].equals("RuHypixel") && Server.isRuHypixel())) && setting.isKeybind()) {
                map.put(setting.getKeybind(), setting);
            }
        }

        hashMap = map;
    }

    @EventTarget
    public void onServerJoin(ConnectToServerEvent event) {
        updateHashMap();
    }

    @Override
    public void onChangeSettingValue(Setting setting) {
        super.onChangeSettingValue(setting);
        updateHashMap();
    }

    private void initRuHypixelModes() {
        try {
            Client.logger.info("Initialising RuHypixel Quickplay Modes");
            InputStream in = getClass().getResourceAsStream("/assets/minecraft/silentclient/mods/quickplay/modes_ruhypixel.json");
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuffer content = new StringBuffer();
            String inputLine;
            while ((inputLine = reader.readLine()) != null) {
                content.append(inputLine);
            }

            Type listType = new TypeToken<ArrayList<QuickplayModeType>>(){}.getType();
            ruhypixelQuickplayModes = Client.getInstance().getGson().fromJson(content.toString(), listType);

            for(QuickplayModeType mode : ruhypixelQuickplayModes) {
                Client.logger.info(String.format("Initialising RuHypixel Quickplay Mode (%s)", mode.name));
                mode.modes.forEach((command) -> {
                    Client.logger.info(String.format("Initialising RuHypixel Quickplay Mode (%s) Command (%s)", mode.name, command.name));
                    this.addKeybindSetting("Quickplay Mode&RuHypixel&"+command.command, this, -1);
                });
            }
        } catch (Exception err) {
            Client.logger.catching(err);
        }
    }

    public static void runCommand(String command) {
        if(sending) {
            return;
        }
        sending = true;
        (new Thread("QPM: " + command) {
            public void run() {
                Minecraft.getMinecraft().thePlayer.sendChatMessage(command);
                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                sending = false;
            }
        }).start();
    }

    @EventTarget
    public void onClick(KeyEvent event) {
        if(mc.thePlayer != null && mc.theWorld != null && mc.currentScreen == null) {
            Setting setting = hashMap.get(event.getKey());
            if(setting != null) {
                if(setting.getName().equals("Open Menu")) {
                    // Open Menu
                    if(Server.isHypixel() || Server.isRuHypixel()) {
                        mc.displayGuiScreen(new QuickplayGui());
                    }
                } else {
                    if(sending) {
                        return;
                    }
                    String[] args = setting.getName().split("&");
                    runCommand(args[2]);
                }
            }
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();
        updateHashMap();
    }

    @Override
    public void onDisable() {
        super.onDisable();
        updateHashMap();
    }



    public class QuickplayModeType {
        public String name;
        public String icon;
        public ArrayList<QuickplayCommandType> modes;
    }

    public class QuickplayCommandType {
        public String name;
        public String command;
    }
}
