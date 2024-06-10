// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;

import org.lwjgl.opengl.Display;

import me.kaktuswasser.altmanager.AltManager;
import me.kaktuswasser.client.clickgui.ClickGUI;
import me.kaktuswasser.client.clickgui.element.elements.ElementDropdownMenu;
import me.kaktuswasser.client.clickgui.element.elements.ElementModuleButton;
import me.kaktuswasser.client.clickgui.element.elements.ElementValueSlider;
import me.kaktuswasser.client.clickgui.element.elements.ElementValueSliderBox;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.command.CommandManager;
import me.kaktuswasser.client.event.EventManager;
import me.kaktuswasser.client.file.FileManager;
import me.kaktuswasser.client.friend.FriendManager;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.module.ModuleManager;
import me.kaktuswasser.client.notification.NotificationManager;
import me.kaktuswasser.client.tabgui.GuiTabHandler;
import me.kaktuswasser.client.threads.NewestVersion;
import me.kaktuswasser.client.utilities.Logger;
import me.kaktuswasser.client.values.ConstrainedValue;
import me.kaktuswasser.client.values.ModeValue;
import me.kaktuswasser.client.values.Value;
import me.kaktuswasser.client.values.ValueManager;

public class Client
{
	/*
	 *Moddet Icarus/Icarus b17 by KaktusWasser 
	 */
	
    private static EventManager eventManager;
    private static ModuleManager moduleManager;
    private static CommandManager commandManager;
    private static ValueManager valueManager;
    private static FileManager fileManager;
    private static FriendManager friendManager;
    private static NotificationManager notificationManager;
    private static AltManager altManager;
    private static GuiTabHandler guiHandler;
    private static NewestVersion newestVersion;
    private static boolean latestVersion;
    private static String name;
    private static int build;
    private static ClickGUI gui;
    private static File directory;
    
    static {
        Client.eventManager = new EventManager();
        Client.moduleManager = new ModuleManager();
        Client.commandManager = new CommandManager();
        Client.valueManager = new ValueManager();
        Client.fileManager = new FileManager();
        Client.friendManager = new FriendManager();
        Client.notificationManager = new NotificationManager();
        Client.altManager = new AltManager();
        Client.name = "Name";
        Client.build = 1;
        directory = new File(System.getProperty("user.home"), Client.name);
    }
    
    public static void setup() {
    	Display.setTitle(Client.getName() + " b"+ Client.build);
        System.out.println("Welcome to: Better-Icarus/Icarus b17/Client Base\nSrc by KaktusWasser");
        if (!Client.directory.exists()) {
            Client.directory.mkdirs();
        }
        Client.newestVersion = new NewestVersion();
        Client.eventManager.setupListeners();
        Client.commandManager.setupCommands();
        Client.valueManager.setupValues();
        Client.moduleManager.setupModules();
        Client.valueManager.organizeValues();
        Client.commandManager.organizeCommands();
        Client.friendManager.setupFriends();
        Client.eventManager.organizeListeners();
        Client.altManager.setupAlts();
        Client.gui = new ClickGUI();
        Client.guiHandler = new GuiTabHandler();
        Client.fileManager.setupFiles();
        Client.fileManager.loadFiles();
        Client.friendManager.organizeFriends();
        Client.notificationManager.setupNotifications();
        for (final Value value : Client.valueManager.getValues()) {
            if (value.getValue() instanceof Boolean) {
                ElementModuleButton.checkboxes.add(new ElementValueSliderBox(value));
            }
            if (value instanceof ModeValue) {
                ElementModuleButton.dropDowns.add(new ElementDropdownMenu((ModeValue)value));
            }
            if (value instanceof ConstrainedValue) {
                ElementModuleButton.sliders.add(new ElementValueSlider((ConstrainedValue)value));
            }
        }
        Collections.sort(getModuleManager().getModules(), new Comparator<Module>() {
            @Override
            public int compare(final Module mod1, final Module mod2) {
                return mod1.getTag().compareTo(mod2.getTag());
            }
        });
        Collections.sort(Client.commandManager.getCommands(), new Comparator<Command>() {
            @Override
            public int compare(final Command mod1, final Command mod2) {
                return mod1.getCommand().compareTo(mod2.getCommand());
            }
        });
    }
    
    /*--*/
    
    public static EventManager getEventManager() {
        return Client.eventManager;
    }
    
    public static File getDirectory() {
        return Client.directory;
    }
    
    public static ModuleManager getModuleManager() {
        return Client.moduleManager;
    }
    
    public static CommandManager getCommandManager() {
        return Client.commandManager;
    }
    
    public static ValueManager getValueManager() {
        return Client.valueManager;
    }
    
    public static FileManager getFileManager() {
        return Client.fileManager;
    }
    
    public static FriendManager getFriendManager() {
        return Client.friendManager;
    }
    
    public static AltManager getAltManager() {
        return Client.altManager;
    }
    
    public static NotificationManager getNotificationManager() {
        return Client.notificationManager;
    }
    
    public static boolean isLatestVersion() {
        return Client.latestVersion;
    }
    
    public static void setLatestVersion(boolean latestVersion) {
        Client.latestVersion = latestVersion;
    }
    
    public static String getName() {
        return Client.name;
    }
    
    public static int getBuild() {
        return Client.build;
    }
    
    public static ClickGUI getClickGUI() {
        return Client.gui;
    }
    
    public static GuiTabHandler getGuiHandler() {
        return Client.guiHandler;
    }
}
