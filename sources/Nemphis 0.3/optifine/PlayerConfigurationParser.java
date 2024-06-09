/*
 * Decompiled with CFR 0_118.
 * 
 * Could not load the following classes:
 *  com.google.gson.JsonArray
 *  com.google.gson.JsonElement
 *  com.google.gson.JsonObject
 *  com.google.gson.JsonParseException
 *  com.google.gson.JsonParser
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

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public PlayerConfiguration parsePlayerConfiguration(JsonElement je) {
        if (je == null) {
            throw new JsonParseException("JSON object is null, player: " + this.player);
        }
        jo = (JsonObject)je;
        pc = new PlayerConfiguration();
        items = (JsonArray)jo.get("items");
        if (items == null) return pc;
        i = 0;
        while (i < items.size()) {
            item = (JsonObject)items.get(i);
            active = Json.getBoolean(item, "active", true);
            if (!active) ** GOTO lbl29
            type = Json.getString(item, "type");
            if (type != null) ** GOTO lbl16
            Config.warn("Item type is null, player: " + this.player);
            ** GOTO lbl29
lbl16: // 1 sources:
            modelPath = Json.getString(item, "model");
            if (modelPath == null) {
                modelPath = "items/" + type + "/model.cfg";
            }
            if ((model = this.downloadModel(modelPath)) == null) ** GOTO lbl29
            if (model.isUsePlayerTexture()) ** GOTO lbl28
            texturePath = Json.getString(item, "texture");
            if (texturePath == null) {
                texturePath = "items/" + type + "/users/" + this.player + ".png";
            }
            if ((image = this.downloadTextureImage(texturePath)) != null) {
                model.setTextureImage(image);
                loc = new ResourceLocation("optifine.net", texturePath);
                model.setTextureLocation(loc);
lbl28: // 2 sources:
                pc.addPlayerItemModel(model);
            }
lbl29: // 6 sources:
            ++i;
        }
        return pc;
    }

    private BufferedImage downloadTextureImage(String texturePath) {
        String textureUrl = "http://s.optifine.net/" + texturePath;
        try {
            byte[] e = HttpPipeline.get(textureUrl, Minecraft.getMinecraft().getProxy());
            BufferedImage image = ImageIO.read(new ByteArrayInputStream(e));
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
            byte[] e = HttpPipeline.get(modelUrl, Minecraft.getMinecraft().getProxy());
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

