/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.NativeImage;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.http.HttpPipeline;
import net.optifine.http.HttpUtils;
import net.optifine.player.PlayerConfiguration;
import net.optifine.player.PlayerItemModel;
import net.optifine.player.PlayerItemParser;
import net.optifine.util.Json;

public class PlayerConfigurationParser {
    private String player = null;
    public static final String CONFIG_ITEMS = "items";
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_ACTIVE = "active";

    public PlayerConfigurationParser(String string) {
        this.player = string;
    }

    public PlayerConfiguration parsePlayerConfiguration(JsonElement jsonElement) {
        if (jsonElement == null) {
            throw new JsonParseException("JSON object is null, player: " + this.player);
        }
        JsonObject jsonObject = (JsonObject)jsonElement;
        PlayerConfiguration playerConfiguration = new PlayerConfiguration();
        JsonArray jsonArray = (JsonArray)jsonObject.get(CONFIG_ITEMS);
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.size(); ++i) {
                PlayerItemModel playerItemModel;
                JsonObject jsonObject2 = (JsonObject)jsonArray.get(i);
                boolean bl = Json.getBoolean(jsonObject2, ITEM_ACTIVE, true);
                if (!bl) continue;
                String string = Json.getString(jsonObject2, ITEM_TYPE);
                if (string == null) {
                    Config.warn("Item type is null, player: " + this.player);
                    continue;
                }
                Object object = Json.getString(jsonObject2, "model");
                if (object == null) {
                    object = "items/" + string + "/model.cfg";
                }
                if ((playerItemModel = this.downloadModel((String)object)) == null) continue;
                if (!playerItemModel.isUsePlayerTexture()) {
                    NativeImage nativeImage;
                    Object object2 = Json.getString(jsonObject2, "texture");
                    if (object2 == null) {
                        object2 = "items/" + string + "/users/" + this.player + ".png";
                    }
                    if ((nativeImage = this.downloadTextureImage((String)object2)) == null) continue;
                    playerItemModel.setTextureImage(nativeImage);
                    ResourceLocation resourceLocation = new ResourceLocation("optifine.net", (String)object2);
                    playerItemModel.setTextureLocation(resourceLocation);
                }
                playerConfiguration.addPlayerItemModel(playerItemModel);
            }
        }
        return playerConfiguration;
    }

    private NativeImage downloadTextureImage(String string) {
        String string2 = HttpUtils.getPlayerItemsUrl() + "/" + string;
        try {
            byte[] byArray = HttpPipeline.get(string2, Minecraft.getInstance().getProxy());
            return NativeImage.read(new ByteArrayInputStream(byArray));
        } catch (IOException iOException) {
            Config.warn("Error loading item texture " + string + ": " + iOException.getClass().getName() + ": " + iOException.getMessage());
            return null;
        }
    }

    private PlayerItemModel downloadModel(String string) {
        String string2 = HttpUtils.getPlayerItemsUrl() + "/" + string;
        try {
            byte[] byArray = HttpPipeline.get(string2, Minecraft.getInstance().getProxy());
            String string3 = new String(byArray, "ASCII");
            JsonParser jsonParser = new JsonParser();
            JsonObject jsonObject = (JsonObject)jsonParser.parse(string3);
            return PlayerItemParser.parseItemModel(jsonObject);
        } catch (Exception exception) {
            Config.warn("Error loading item model " + string + ": " + exception.getClass().getName() + ": " + exception.getMessage());
            return null;
        }
    }
}

