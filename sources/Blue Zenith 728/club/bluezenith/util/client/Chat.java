package club.bluezenith.util.client;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import net.minecraft.util.EnumChatFormatting;

import static club.bluezenith.util.MinecraftInstance.mc;
import static net.minecraft.util.IChatComponent.Serializer.jsonToComponent;

public class Chat {

    private final static Gson gson = new GsonBuilder().create();

    public static void xi(String content) {
        final JsonObject object = new JsonObject();
        object.add("text", new JsonPrimitive("§c§l[XI JINPING] §f§l" + content + "!"));
        mc.thePlayer.addChatMessage(jsonToComponent(gson.toJson(object)));
    }

    public static void xi(String content, EnumChatFormatting contentColor) {
        final JsonObject object = new JsonObject();
        object.add("text", new JsonPrimitive("§c§l[XI JINPING] " + contentColor.toString() + "§l" + content + "!"));
        mc.thePlayer.addChatMessage(jsonToComponent(gson.toJson(object)));
    }

    public static void xif(String content, Object... args) {
        final JsonObject object = new JsonObject();
        object.add("text", new JsonPrimitive(String.format("§c§l[XI JINPING] §f§l" + content + "!", args)));
        mc.thePlayer.addChatMessage(jsonToComponent(gson.toJson(object)));
    }

    public static void bz(String content) {
        final JsonObject object = new JsonObject();
        object.add("text", new JsonPrimitive("§9Blue Zenith §7§l\u00bb §f§7" + content));
        mc.thePlayer.addChatMessage(jsonToComponent(gson.toJson(object)));
    }

    public static void bz(String content, EnumChatFormatting contentColor) {
        final JsonObject object = new JsonObject();
        object.add("text", new JsonPrimitive("§9Blue Zenith " + contentColor.toString() + "§l\u00bb §f§7" + content));
        mc.thePlayer.addChatMessage(jsonToComponent(gson.toJson(object)));
    }

    public static void bzf(String content, Object... args) {
        final JsonObject object = new JsonObject();
        object.add("text", new JsonPrimitive(String.format("§9Blue Zenith §7§l\u00bb §f§7" + content, args)));
        mc.thePlayer.addChatMessage(jsonToComponent(gson.toJson(object)));
    }
}
