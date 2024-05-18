package com.minimap;

import com.minimap.settings.*;
import com.minimap.events.*;
import net.minecraft.client.*;
import com.minimap.interfaces.*;
import com.minimap.gui.*;
import net.minecraft.client.gui.*;
import de.labystudio.modapi.*;
import java.util.zip.*;
import java.io.*;
import java.security.*;
import java.net.*;
import java.util.jar.*;
import java.util.*;

public class XaeroMinimap extends Module
{
    public static XaeroMinimap instance;
    public static final String versionID = "1.8.8_1.7.6";
    public static int newestUpdateID;
    public static ModSettings settings;
    public static String message;
    public static ControlsHandler ch;
    public static Events events;
    public static FMLEvents fmlEvents;
    public static File optionsFile;
    public static File waypointsFile;
    public static Minecraft mc;
    public static Map<String, Map<String, String>> languages;
    public static String previousServer;
    public static boolean allowRadar;
    public static boolean allowPlayerRadar;
    public static boolean allowMiniMap;
    
    public static ModSettings getSettings() {
        return XaeroMinimap.settings;
    }
    
    @Override
    public void onEnable() {
        XaeroMinimap.instance = this;
        InterfaceHandler.loadPresets();
        InterfaceHandler.load();
        try {
            (XaeroMinimap.settings = new ModSettings()).loadSettings();
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        XaeroMinimap.events = new Events();
        XaeroMinimap.fmlEvents = new FMLEvents();
        ModAPI.addSettingsButton("Minimap", new GuiMinimap(getSettings()));
        ModAPI.registerListener(XaeroMinimap.events);
        ModAPI.registerListener(XaeroMinimap.fmlEvents);
        try {
            final List<String> langFiles = new ArrayList<String>();
            final CodeSource src = XaeroMinimap.class.getProtectionDomain().getCodeSource();
            if (src != null) {
                final URL jar = src.getLocation();
                System.out.println(jar.toString());
                final JarInputStream zip = new JarInputStream(jar.openStream());
                JarEntry ze = null;
                while ((ze = zip.getNextJarEntry()) != null) {
                    final String entryName = ze.getName();
                    if (entryName.startsWith("assets/minecraft/xaerobetterpvp/lang")) {
                        if (entryName.equals("assets/minecraft/xaerobetterpvp/lang/")) {
                            continue;
                        }
                        langFiles.add(entryName);
                    }
                }
            }
            File file = null;
            JarFile jar2 = null;
            try {
                file = new File(src.getLocation().toURI());
                jar2 = new JarFile(file);
            }
            catch (Exception ex2) {}
            for (final String langFile : langFiles) {
                final JarEntry entry = jar2.getJarEntry(langFile);
                final BufferedReader input = new BufferedReader(new InputStreamReader(jar2.getInputStream(entry), "UTF-8"));
                final Map<String, String> messages = new HashMap<String, String>();
                final String rawFileName = langFile.substring(langFile.lastIndexOf(47) + 1, langFile.length());
                final String fileName = rawFileName.substring(0, rawFileName.indexOf(46));
                XaeroMinimap.languages.put(fileName, messages);
                String line = null;
                while ((line = input.readLine()) != null) {
                    if (line.split("=").length >= 2) {
                        messages.put(line.split("=")[0], line.split("=")[1]);
                    }
                }
            }
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
    @Override
    public void pluginMessage(final String key, final boolean value) {
        if (key.equals("MINIMAP_RADAR") && !value) {
            XaeroMinimap.allowRadar = false;
        }
        if (key.equals("MINIMAP_PLAYER_RADAR") && !value) {
            XaeroMinimap.allowPlayerRadar = false;
        }
        if (key.equals("MINIMAP") && !value) {
            XaeroMinimap.allowMiniMap = false;
        }
    }
    
    static {
        XaeroMinimap.languages = new HashMap<String, Map<String, String>>();
        XaeroMinimap.previousServer = "";
        XaeroMinimap.allowRadar = true;
        XaeroMinimap.allowPlayerRadar = true;
        XaeroMinimap.allowMiniMap = true;
        XaeroMinimap.message = "";
        XaeroMinimap.optionsFile = new File("./xaerominimap.txt");
        XaeroMinimap.waypointsFile = new File("./xaerowaypoints.txt");
        XaeroMinimap.mc = Minecraft.getMinecraft();
    }
}
