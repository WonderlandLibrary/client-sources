package exhibition;

import java.lang.reflect.Field;

import exhibition.util.ReflectUtil;
import net.minecraft.client.*;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.util.*;

public class Wrapper
{
	
    public static Minecraft getMinecraft() {
        return Minecraft.getMinecraft();
    }
	   
    public static EntityPlayerSP getPlayer() {
        return getMinecraft().thePlayer;
    }

    public static WorldClient getWorld() {
		      return getMinecraft().theWorld;
    }
		   
    public static NetworkManager getNetManager() {
        return Minecraft.getMinecraft().getNetHandler().getNetworkManager();
    }
    
    public static void printChat(String text) {
        getMinecraft().thePlayer.addChatComponentMessage(new ChatComponentText(text));
    }

     public static void sendChat_NoFilter(String text) {
        getMinecraft().thePlayer.sendQueue.addToSendQueue(new C01PacketChatMessage(text));
    }

     public static void sendChat(String text) {
        getMinecraft().thePlayer.sendChatMessage(text);
    }
     
    public static Timer getTimer(final Minecraft mc) {
        try {
            return (Timer)Client.instance.reflector.getField("timer", "field_71428_T", mc);
        }
        catch (Exception ex) {
            return null;
        }
    }
    
    public static void setLeftClickCounter(final int val) {
        try {
            Client.instance.reflector.setField("leftClickCounter", "field_71429_W", Minecraft.class, Minecraft.getMinecraft(), val, false);
        }
        catch (Exception ex) {}
    }
    
    public static void clickMouse() {
        try {
            Client.instance.reflector.invoke(Minecraft.getMinecraft(), "clickMouse", "func_147116_af", new Class[0], new Object[0]);
        }
        catch (Exception ex) {}
    }
    
    public static double getRenderPosX() {
        try {
            return (Double)Client.instance.reflector.getField("renderPosX", "field_78725_b", Minecraft.getMinecraft().getRenderManager());
        }
        catch (Exception ex) {
            return 0.0;
        }
    }
    
    public static double getRenderPosY() {
        try {
            return (Double) Client.instance.reflector.getField("renderPosY", "field_78726_c", Minecraft.getMinecraft().getRenderManager());
        }
        catch (Exception ex) {
            return 0.0;
        }
    }
    
    public static double getRenderPosZ() {
        try {
            return (Double)Client.instance.reflector.getField("renderPosZ", "field_78723_d", Minecraft.getMinecraft().getRenderManager());
        }
        catch (Exception ex) {
            return 0.0;
        }
    }
}
