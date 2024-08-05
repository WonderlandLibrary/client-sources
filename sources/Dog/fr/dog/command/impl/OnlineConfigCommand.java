package fr.dog.command.impl;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fr.dog.Dog;
import fr.dog.command.Command;
import fr.dog.property.Property;
import fr.dog.property.impl.BooleanProperty;
import fr.dog.property.impl.ColorProperty;
import fr.dog.property.impl.ModeProperty;
import fr.dog.property.impl.NumberProperty;
import fr.dog.protection.ProtectedLaunch;
import fr.dog.util.packet.RequestUtil;
import fr.dog.util.player.ChatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.HoverEvent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;

import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.io.File;

import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Map;


public class OnlineConfigCommand extends Command {
    private final File configFolder = new File(mc.mcDataDir, "/dog/configs");
    public OnlineConfigCommand() {
        super("onlineconfig", "ocfg");
    }
    @Override
    public void execute(String[] args, String message) {
        String[] words = message.split(" ");
        if (words.length < 2 || words.length > 4) {
            ChatUtil.display("Invalid arguments! Usage: .onlineconfig <upload|list|download> [name]");
            return;
        }
        String action = words[1].toLowerCase();
        String config = words.length == 3 ? words[2] : "default";

        switch (action) {
            case "list" -> listConfigs();
            case "download" ->{
                try {
                    String mashup = new ProtectedLaunch.EncryptionData(getKeyFromString(Dog.getInstance().getKey()), getIvFromString(Dog.getInstance().getVi())).decryptToString(RequestUtil.requestResult.apply("/downloadConfig?tokenid=" + Dog.getInstance().getToken() + "&configid=" + config));
                    read(mashup);
                    ChatUtil.display("Succesfully Loaded Config.");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            case "upload" -> {
                try {
                    String paster = Dog.getInstance().getConfigManager().uploadConfig(config, new File(Dog.getInstance().getConfigManager().getConfig(config).getDirectory(), config + ".json"));


                    StringSelection selection = new StringSelection(paster);
                    Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
                    clipboard.setContents(selection, selection);

                    ChatUtil.display("Config ID is now in your clipboard.");
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
    }
    private void listConfigs() {
        try {
            String mashup = new ProtectedLaunch.EncryptionData(getKeyFromString(Dog.getInstance().getKey()), getIvFromString(Dog.getInstance().getVi())).decryptToString(RequestUtil.requestResult.apply("/getConfigListForClient?tokenid=" + Dog.getInstance().getToken()));
            ChatUtil.display("Listing of all the online configs :");
            for (String s : mashup.split("\n")) {
                String[] configinfos = s.split(":");
                SimpleDateFormat sdf = new SimpleDateFormat("MMM dd,yyyy HH:mm");
                Date resultdate = new Date(Long.valueOf(configinfos[3]));

                IChatComponent ichatcomponent = new ChatComponentText("§f[" + Dog.getInstance().getThemeManager().getCurrentTheme().chatFormatting + "Dog Client§f]§8 > §7" + "> " + configinfos[0] + " by : " + configinfos[2] + " (" + sdf.format(resultdate) + ")");
                ichatcomponent.setChatStyle(ichatcomponent.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, ".ocfg download " + configinfos[1])));
                ichatcomponent.setChatStyle(ichatcomponent.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ChatComponentText("ID : " + configinfos[1] + " - Click To Load"))));
                Minecraft.getMinecraft().thePlayer.addChatMessage(ichatcomponent);

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private static SecretKey getKeyFromString(String key) {
        byte[] decodedKey = Base64.getDecoder().decode(key);
        return new SecretKeySpec(decodedKey, 0, decodedKey.length, "AES");
    }

    private static IvParameterSpec getIvFromString(String iv) {
        byte[] decodedIv = Base64.getDecoder().decode(iv);
        return new IvParameterSpec(decodedIv);
    }
    public void read(String content) {
        JsonObject config = JsonParser.parseString(content).getAsJsonObject();
        assert config != null : "Config is undefined";
        for (Map.Entry<String, JsonElement> entry : config.entrySet()) {
            Dog.getInstance().getModuleManager().getObjects().forEach(module -> {
                if (entry.getKey().equalsIgnoreCase(module.getName())) {
                    JsonObject json = (JsonObject) entry.getValue();
                    module.setEnabled(json.get("enabled").getAsBoolean());
                    JsonObject values = json.get("values").getAsJsonObject();
                    for (Map.Entry<String, JsonElement> value : values.entrySet()) {
                        if (module.getValueByName(value.getKey()) != null) {
                            try {
                                Property<?> property = module.getValueByName(value.getKey());
                                if (property instanceof BooleanProperty booleanProperty)
                                    booleanProperty.setValue(value.getValue().getAsBoolean());
                                if (property instanceof ModeProperty modeProperty)
                                    modeProperty.setIndexValue(value.getValue().getAsInt());
                                if (property instanceof NumberProperty numberProperty)
                                    numberProperty.setValue(value.getValue().getAsFloat());
                                if (property instanceof ColorProperty colorProperty) {
                                    colorProperty.setValue(new Color(value.getValue().getAsInt()));
                                }
                            } catch (Exception ignored) { /* */ }
                        }
                    }
                }
            });
        }
    }
}