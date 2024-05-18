package com.darkcart.xdolf;

import java.io.File;

import org.lwjgl.input.Keyboard;

import com.darkcart.xdolf.clickgui.XdolfGuiClick;
import com.darkcart.xdolf.fonts.Fonts;
import com.darkcart.xdolf.mods.Hacks;
import com.darkcart.xdolf.util.FileManager;
import com.darkcart.xdolf.util.FriendManager;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.Util.EnumOS;
import net.minecraft.util.text.TextComponentString;

public class Wrapper {
	
	public static Hacks hacks;
	public static FileManager fileManager;
	public static FriendManager friendManager;
	public static XdolfGuiClick clickGui;
	
	public static final Minecraft getMinecraft() {
		return Minecraft.getMinecraft();
	}
	
	public static final EntityPlayerSP getPlayer() {
		return getMinecraft().player;
	}
	
	public static final WorldClient getWorld() {
		return getMinecraft().world;
	}

	public static final File getMinecraftDir() {
		return getMinecraft().mcDataDir;
	}
	
	public static void addChatMessage(String s) {
		getPlayer().addChatMessage(new TextComponentString(Client.wrap(String.format("[%s%s%s] %s", "\247e", Client.CLIENT_NAME, "\247f", s), 100)));
	}
	
	public static float getCooldown()
	{
		return getPlayer().getCooledAttackStrength(0);
	}
	
	public static Hacks getHacks()
	{
		if(hacks == null) hacks = new Hacks();
		
		return hacks;
	}
	
	public static FileManager getFileManager()
	{
		if(fileManager == null) fileManager = new FileManager();
		
		return fileManager;
	}
	
	public static FriendManager getFriends()
	{
		if(friendManager == null) friendManager = new FriendManager();
		
		return friendManager;
	}
	
	public static XdolfGuiClick getClickGui()
	{
		if(clickGui == null) clickGui = new XdolfGuiClick();
		
		return clickGui;
	}
	
	public static void drawCenteredTTFString(String s, int x, int y, int color)
    {
		Fonts.roboto18.drawCenteredString(s, x, y, color, true);
    }
	
    /**
     * gets the working dir (OS specific) for the specific application (which is always minecraft)
     */
    public static File getAppDir(String par0Str)
    {
    	String var1 = System.getProperty("user.home", ".");
        File var2;

        switch (getOs()) {
            case LINUX:
            case SOLARIS:
                var2 = new File(var1, '.' + par0Str + '/');
                break;

            case WINDOWS:
                String var3 = System.getenv("APPDATA");
                
                if (var3 != null) {
                    var2 = new File(var3, "." + par0Str + '/');
                } else {
                    var2 = new File(var1, '.' + par0Str + '/');
                }

                break;

            case OSX:
                var2 = new File(var1, "Library/Application Support/" + par0Str);
                break;

            default:
                var2 = new File(var1, par0Str + '/');
         }

         if (!var2.exists() && !var2.mkdirs()) {
        	 throw new RuntimeException("The working directory could not be created: " + var2);
         } else {
             return var2;
         }
     }
    
    public static EnumOS getOs() {
        String var0 = System.getProperty("os.name").toLowerCase();
        return var0.contains("win") ? EnumOS.WINDOWS : (var0.contains("mac") ? EnumOS.OSX : (var0.contains("solaris") ? EnumOS.SOLARIS : (var0.contains("sunos") ? EnumOS.SOLARIS : (var0.contains("linux") ? EnumOS.LINUX : (var0.contains("unix") ? EnumOS.LINUX : EnumOS.UNKNOWN)))));
    }
    
	public static void onKeyPressed() {
		int key = Keyboard.getEventKey();
		for(Module mod : Hacks.hackList) {
			mod.onKeyPressed(key);
		}
	}
}
