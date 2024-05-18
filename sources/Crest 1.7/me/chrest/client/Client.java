// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client;

import net.minecraft.util.ResourceLocation;
import me.chrest.client.gui.click.ClickGui;
import me.chrest.client.friend.FriendManager;
import me.chrest.client.option.OptionManager;
import me.chrest.client.command.CommandManager;
import me.chrest.client.module.ModuleManager;
import me.chrest.client.gui.ui.virtue.TabGui;
import me.chrest.utils.ClientUtils;
import java.util.Arrays;
import java.util.List;
import me.chrest.client.gui.account.AccountScreen;
import me.chrest.utils.minecraft.FontRenderer;

public final class Client
{
    public static FontRenderer font;
    public static final String NAME = "Atlas";
    public static final double VERSION = 1.6;
    public static AccountScreen accountScreen;
    static List white;
    static List orange;
    static List blue;
    static List green;
    static List yellow;
    static List red;
    static List purple;
    
    static {
        Client.accountScreen = new AccountScreen();
        Client.white = Arrays.asList("AlmightyGabe");
        Client.orange = Arrays.asList("");
        Client.blue = Arrays.asList("Arithmo", "Chrestomathic", "AlexRockzFTW");
        Client.green = Arrays.asList("Polymorphous", "Allesia", "CloseEncounters");
        Client.yellow = Arrays.asList("");
        Client.red = Arrays.asList("IModsPRO3D", "RedstoneRepeater");
        Client.purple = Arrays.asList("GetSchmokedd", "OverCookedJews", "K_Eugine", "AppleScript", "Virb");
    }
    
    public static void start() {
        ClientUtils.loadClientFont();
        TabGui.init();
        me.chrest.client.gui.ui.TabGui.init();
        ModuleManager.start();
        CommandManager.start();
        OptionManager.start();
        FriendManager.start();
        ClickGui.start();
    }
    
    public static boolean isAtlasMember(final String username) {
        return Client.orange.contains(username) || Client.blue.contains(username) || Client.green.contains(username) || Client.yellow.contains(username) || Client.red.contains(username) || Client.purple.contains(username);
    }
    
    public static ResourceLocation getAtlasCape(final String username) {
        String capeColor = "white";
        if (Client.orange.contains(username)) {
            capeColor = "orange";
        }
        if (Client.blue.contains(username)) {
            capeColor = "blue";
        }
        if (Client.green.contains(username)) {
            capeColor = "green";
        }
        if (Client.yellow.contains(username)) {
            capeColor = "yellow";
        }
        if (Client.red.contains(username)) {
            capeColor = "red";
        }
        if (Client.purple.contains(username)) {
            capeColor = "purple";
        }
        final ResourceLocation rl = new ResourceLocation("client/" + capeColor + ".png");
        return rl;
    }
}
