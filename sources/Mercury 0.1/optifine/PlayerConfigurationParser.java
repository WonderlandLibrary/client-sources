/*
 * Decompiled with CFR 0.145.
 */
package optifine;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.Proxy;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import optifine.Config;
import optifine.HttpPipeline;
import optifine.Json;
import optifine.PlayerConfiguration;
import optifine.PlayerItemModel;
import optifine.PlayerItemParser;

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
        PlayerConfiguration pc2 = new PlayerConfiguration();
        JsonArray items = (JsonArray)jo.get(CONFIG_ITEMS);
        if (items != null) {
            for (int i2 = 0; i2 < items.size(); ++i2) {
                PlayerItemModel model;
                JsonObject item = (JsonObject)items.get(i2);
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
                pc2.addPlayerItemModel(model);
            }
        }
        return pc2;
    }

    private BufferedImage downloadTextureImage(String texturePath) {
        String textureUrl = "http://s.optifine.net/" + texturePath;
        try {
            byte[] e2 = HttpPipeline.get(textureUrl, Minecraft.getMinecraft().getProxy());
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(e2));
            return image;
        }
        catch (IOException var5) {
            Config.warn("Error loading item texture " + texturePath + ": " + var5.getClass().getName() + ": " + var5.getMessage());
            return null;
        }
    }

    private PlayerItemModel downloadModel(String modelPath) {
        String modelUrl = "http://s.optifine.net/" + modelPath;
        try {
            byte[] e2 = HttpPipeline.get(modelUrl, Minecraft.getMinecraft().getProxy());
            String jsonStr = new String(e2, "ASCII");
            JsonParser jp2 = new JsonParser();
            JsonObject jo = (JsonObject)jp2.parse(jsonStr);
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

