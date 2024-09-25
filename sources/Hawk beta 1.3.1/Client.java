package eze;

import java.util.concurrent.*;
import eze.modules.*;
import eze.ui.*;
import org.lwjgl.opengl.*;
import eze.access.*;
import java.io.*;
import eze.modules.render.*;
import eze.modules.combat.*;
import eze.modules.player.*;
import eze.modules.movement.*;
import eze.events.*;
import eze.events.listeners.*;
import java.util.*;

public class Client
{
    public static String name;
    public static String version;
    public static String mcversion;
    public static String NameInConsole;
    public static CopyOnWriteArrayList<Module> modules;
    public static HUD hud;
    public static boolean IsRegistered;
    
    static {
        Client.name = "Hawk Client";
        Client.version = "beta 1.3";
        Client.mcversion = "Minecraft 1.8";
        Client.NameInConsole = "[Hawk Client] ";
        Client.modules = new CopyOnWriteArrayList<Module>();
        Client.hud = new HUD();
        Client.IsRegistered = false;
    }
    
    public static void startup() {
        System.out.println(String.valueOf(Client.NameInConsole) + "Starting Hawk Client - Minecraft 1.8");
        Display.setTitle(String.valueOf(Client.name) + " " + Client.version + " - " + Client.mcversion);
        try {
            final BufferedReader readfile = new BufferedReader(new FileReader("gie5hg8u4toihu45.hawkclient"));
            String LineContent;
            while ((LineContent = readfile.readLine()) != null) {
                if (LineContent.equals(DisplayOnScreen.CorrectKey)) {
                    Client.IsRegistered = true;
                    System.out.println(String.valueOf(Client.NameInConsole) + "Private version loaded");
                    System.out.println(String.valueOf(Client.NameInConsole) + "Key : " + DisplayOnScreen.CorrectKey);
                }
            }
            readfile.close();
        }
        catch (IOException e) {
            System.out.println(String.valueOf(Client.NameInConsole) + "Normal version loaded");
        }
        Client.modules.add(new Sprint());
        Client.modules.add(new Fullbright());
        Client.modules.add(new Fly());
        Client.modules.add(new Nofall());
        Client.modules.add(new Speed());
        Client.modules.add(new Killaura());
        Client.modules.add(new Fastplace());
        Client.modules.add(new Airjump());
        Client.modules.add(new Timer());
        Client.modules.add(new Antikb());
        Client.modules.add(new TabGUI());
        Client.modules.add(new Aimbot());
        Client.modules.add(new Cheststealer());
        Client.modules.add(new FastEat());
        Client.modules.add(new Noslow());
        Client.modules.add(new BlockAnimation());
        Client.modules.add(new ModulesListOptions());
        Client.modules.add(new Autoarmor());
        Client.modules.add(new Antibot());
        Client.modules.add(new Invmove());
        Client.modules.add(new AutoSetting());
        Client.modules.add(new Longjump());
        Client.modules.add(new Phase());
        Client.modules.add(new ESP());
        Client.modules.add(new Criticals());
        Client.modules.add(new ScaffoldHopeItWorks());
        Client.modules.add(new Safewalk());
    }
    
    public static void onEvent(final Event e) {
        for (final Module m : Client.modules) {
            if (!m.toggled) {
                continue;
            }
            m.onEvent(e);
        }
    }
    
    public static void keyPress(final int key) {
        onEvent(new EventKey(key));
        for (final Module m : Client.modules) {
            if (m.getKey() == key) {
                m.toggle();
            }
        }
    }
    
    public static List<Module> getModulesByCategory(final Module.Category c) {
        final List<Module> modules = new ArrayList<Module>();
        for (final Module m : Client.modules) {
            if (m.category == c) {
                modules.add(m);
            }
        }
        return modules;
    }
}
