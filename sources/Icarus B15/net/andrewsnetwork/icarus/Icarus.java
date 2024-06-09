// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus;

import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collections;
import java.util.Comparator;

import net.andrewsnetwork.altmanager.AltManager;
import net.andrewsnetwork.icarus.clickgui.ClickGUI;
import net.andrewsnetwork.icarus.clickgui.element.elements.ElementDropdownMenu;
import net.andrewsnetwork.icarus.clickgui.element.elements.ElementModuleButton;
import net.andrewsnetwork.icarus.clickgui.element.elements.ElementValueSlider;
import net.andrewsnetwork.icarus.clickgui.element.elements.ElementValueSliderBox;
import net.andrewsnetwork.icarus.command.Command;
import net.andrewsnetwork.icarus.command.CommandManager;
import net.andrewsnetwork.icarus.event.EventManager;
import net.andrewsnetwork.icarus.file.FileManager;
import net.andrewsnetwork.icarus.friend.FriendManager;
import net.andrewsnetwork.icarus.module.Module;
import net.andrewsnetwork.icarus.module.ModuleManager;
import net.andrewsnetwork.icarus.notification.NotificationManager;
import net.andrewsnetwork.icarus.tabgui.GuiTabHandler;
import net.andrewsnetwork.icarus.threads.NewestVersion;
import net.andrewsnetwork.icarus.values.ConstrainedValue;
import net.andrewsnetwork.icarus.values.ModeValue;
import net.andrewsnetwork.icarus.values.Value;
import net.andrewsnetwork.icarus.values.ValueManager;

public class Icarus
{
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
    private static final File directory;
    
    static {
        Icarus.eventManager = new EventManager();
        Icarus.moduleManager = new ModuleManager();
        Icarus.commandManager = new CommandManager();
        Icarus.valueManager = new ValueManager();
        Icarus.fileManager = new FileManager();
        Icarus.friendManager = new FriendManager();
        Icarus.notificationManager = new NotificationManager();
        Icarus.altManager = new AltManager();
        Icarus.name = "Icarus";
        Icarus.build = 15;
        directory = new File(System.getProperty("user.home"), Icarus.name);
    }
    
    public static EventManager getEventManager() {
        return Icarus.eventManager;
    }
    
    public static File getDirectory() {
        return Icarus.directory;
    }
    
    public static ModuleManager getModuleManager() {
        return Icarus.moduleManager;
    }
    
    public static CommandManager getCommandManager() {
        return Icarus.commandManager;
    }
    
    public static ValueManager getValueManager() {
        return Icarus.valueManager;
    }
    
    public static FileManager getFileManager() {
        return Icarus.fileManager;
    }
    
    public static FriendManager getFriendManager() {
        return Icarus.friendManager;
    }
    
    public static AltManager getAltManager() {
        return Icarus.altManager;
    }
    
    public static NotificationManager getNotificationManager() {
        return Icarus.notificationManager;
    }
    
    public static boolean isLatestVersion() {
        return Icarus.latestVersion;
    }
    
    public static void setLatestVersion(final boolean latestVersion) {
        Icarus.latestVersion = latestVersion;
    }
    
    public static String getName() {
        return Icarus.name;
    }
    
    public static int getBuild() {
        return Icarus.build;
    }
    
    public static ClickGUI getClickGUI() {
        return Icarus.gui;
    }
    
    public static void setup() {
        OutputStreamWriter request = new OutputStreamWriter(System.out);
        Label_0031: {
            try {
                request.flush();
            }
            catch (IOException ex) {
                break Label_0031;
            }
            finally {
                request = null;
            }
            request = null;
        }
        System.out.println("Welcome to Icarus!");
        if (!Icarus.directory.exists()) {
            Icarus.directory.mkdirs();
        }
        Icarus.newestVersion = new NewestVersion();
        Icarus.eventManager.setupListeners();
        Icarus.commandManager.setupCommands();
        Icarus.valueManager.setupValues();
        Icarus.moduleManager.setupModules();
        Icarus.valueManager.organizeValues();
        Icarus.commandManager.organizeCommands();
        Icarus.friendManager.setupFriends();
        Icarus.eventManager.organizeListeners();
        Icarus.altManager.setupAlts();
        Icarus.gui = new ClickGUI();
        Icarus.guiHandler = new GuiTabHandler();
        Icarus.fileManager.setupFiles();
        Icarus.fileManager.loadFiles();
        Icarus.friendManager.organizeFriends();
        Icarus.notificationManager.setupNotifications();
        for (final Value value : Icarus.valueManager.getValues()) {
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
        Collections.sort(Icarus.commandManager.getCommands(), new Comparator<Command>() {
            @Override
            public int compare(final Command mod1, final Command mod2) {
                return mod1.getCommand().compareTo(mod2.getCommand());
            }
        });
    }
    
    public static GuiTabHandler getGuiHandler() {
        return Icarus.guiHandler;
    }
}
