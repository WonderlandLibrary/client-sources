package me.teus.eclipse;

import me.teus.eclipse.utils.managers.CommandManager;
import me.teus.eclipse.events.player.EventChat;
import me.teus.eclipse.utils.managers.ConfigManager;
import me.teus.eclipse.utils.managers.ModuleManager;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.EnumChatFormatting;
import xyz.lemon.event.bus.Listener;
import xyz.lemon.event.bus.Protocol;

public class Client {

    public String CLIENT_NAME = "Eclipse", CLIENT_VERSION = "0.1";

    public static Client instance;
    public Protocol eventProtocol = new Protocol();
    public ConfigManager configManager = new ConfigManager();
    public CommandManager commandManager = new CommandManager();
    public long startupTime = System.currentTimeMillis();

    public void startup(){
        System.out.println("[Eclipse] : Loading...");
        eventProtocol.register(this);
        System.out.println("[Eclipse] : Initializing Managers...");
        CommandManager.initialize();
        ModuleManager.initialize();
        configManager.init();
        configManager.load("default", false);

        System.out.println("[Eclipse] : Successfully Loaded in " + (System.currentTimeMillis() - startupTime) + "ms");
    }

    public void stop(){
        configManager.save("default", false);
    }

    private final Listener<EventChat> event = event -> {
        System.out.println("ANDSSDFJH");
        commandManager.handleChat(event);
    };

    public static Client getInstance(){
        if(instance == null)
            instance = new Client();

        return instance;
    }

    public void addChatMessage(String message) {
        message = EnumChatFormatting.GRAY + "E > " + EnumChatFormatting.RESET + message;
        (Minecraft.getMinecraft()).thePlayer.addChatMessage(new ChatComponentText(message));
    }

}
