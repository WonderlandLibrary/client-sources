package ClassSub;

import net.minecraftforge.fml.relauncher.*;
import com.google.gson.*;
import net.minecraft.client.*;
import net.minecraft.util.*;

@SideOnly(Side.CLIENT)
public class Class64
{
    public static final String PRIMARY_COLOR = "§7";
    public static final String SECONDARY_COLOR = "§1";
    private static final String PREFIX = "§7[§1Hanabi§7] ";
    
    
    public static void send(final String s) {
        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("text", s);
        Minecraft.getMinecraft().thePlayer.addChatMessage(IChatComponent.Serializer.jsonToComponent(jsonObject.toString()));
    }
    
    public static void success(final String s) {
        info(s);
    }
    
    public static void info(final String s) {
        send("§7[§1Hanabi§7] " + s);
    }
    
    public static void showMessageBox(final String s) {
        new Class98(s);
    }
}
