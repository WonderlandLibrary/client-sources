/*
 * Decompiled with CFR 0.143.
 */
package net.minecraft.src;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import javax.imageio.ImageIO;
import net.minecraft.src.Config;
import net.minecraft.src.HttpUtils;
import net.minecraft.src.Json;
import net.minecraft.src.PlayerConfiguration;
import net.minecraft.src.PlayerItemModel;
import net.minecraft.src.PlayerItemParser;
import net.minecraft.util.ResourceLocation;

public class PlayerConfigurationParser {
    private String player = null;
    public static final String CONFIG_ITEMS = "items";
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_ACTIVE = "active";

    public PlayerConfigurationParser(String player) {
        this.player = player;
    }

    public PlayerConfiguration parsePlayerConfiguration(JsonElement je) {
        if (je == null) {
            throw new JsonParseException("JSON object is null, player: " + this.player);
        }
        JsonObject jo = (JsonObject)je;
        PlayerConfiguration pc = new PlayerConfiguration();
        JsonArray items = (JsonArray)jo.get(CONFIG_ITEMS);
        if (items != null) {
            for (int i = 0; i < items.size(); ++i) {
                PlayerItemModel model;
                JsonObject item = (JsonObject)items.get(i);
                boolean active = Json.getBoolean(item, ITEM_ACTIVE, true);
                if (!active) continue;
                String type = Json.getString(item, ITEM_TYPE);
                if (type == null) {
                    Config.warn("Item type is null, player: " + this.player);
                    continue;
                }
                String modelPath = Json.getString(item, "model");
                if (modelPath == null) {
                    modelPath = "items/" + type + "/model.cfg";
                }
                if ((model = this.downloadModel(modelPath)) == null) continue;
                if (!model.isUsePlayerTexture()) {
                    BufferedImage image;
                    String texturePath = Json.getString(item, "texture");
                    if (texturePath == null) {
                        texturePath = "items/" + type + "/users/" + this.player + ".png";
                    }
                    if ((image = this.downloadTextureImage(texturePath)) == null) continue;
                    model.setTextureImage(image);
                    ResourceLocation loc = new ResourceLocation("optifine.net", texturePath);
                    model.setTextureLocation(loc);
                }
                pc.addPlayerItemModel(model);
            }
        }
        return pc;
    }

    private BufferedImage downloadTextureImage(String texturePath) {
        String textureUrl = "http://s.optifine.net/" + texturePath;
        try {
            BufferedImage e = ImageIO.read(new URL(textureUrl));
            return e;
        }
        catch (IOException var4) {
            Config.warn("Error loading item texture " + texturePath + ": " + var4.getClass().getName() + ": " + var4.getMessage());
            return null;
        }
    }

    private PlayerItemModel downloadModel(String modelPath) {
        String modelUrl = "http://s.optifine.net/" + modelPath;
        try {
            byte[] e = HttpUtils.get(modelUrl);
            String jsonStr = new String(e, "ASCII");
            JsonParser jp = new JsonParser();
            JsonObject jo = (JsonObject)jp.parse(jsonStr);
            PlayerItemParser pip = new PlayerItemParser();
            PlayerItemModel pim = PlayerItemParser.parseItemModel(jo);
            return pim;
        }
        catch (Exception var9) {
            Config.warn("Error loading item model " + modelPath + ": " + var9.getClass().getName() + ": " + var9.getMessage());
            return null;
        }
    }
}

