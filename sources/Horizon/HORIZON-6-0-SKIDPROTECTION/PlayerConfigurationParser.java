package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonParser;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.net.URL;
import java.awt.image.BufferedImage;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonElement;

public class PlayerConfigurationParser
{
    private String Ø­áŒŠá;
    public static final String HorizonCode_Horizon_È = "items";
    public static final String Â = "type";
    public static final String Ý = "active";
    
    public PlayerConfigurationParser(final String player) {
        this.Ø­áŒŠá = null;
        this.Ø­áŒŠá = player;
    }
    
    public PlayerConfiguration HorizonCode_Horizon_È(final JsonElement je) {
        if (je == null) {
            throw new JsonParseException("JSON object is null, player: " + this.Ø­áŒŠá);
        }
        final JsonObject jo = (JsonObject)je;
        final PlayerConfiguration pc = new PlayerConfiguration();
        final JsonArray items = (JsonArray)jo.get("items");
        if (items != null) {
            for (int i = 0; i < items.size(); ++i) {
                final JsonObject item = (JsonObject)items.get(i);
                final boolean active = Json.HorizonCode_Horizon_È(item, "active", true);
                if (active) {
                    final String type = Json.HorizonCode_Horizon_È(item, "type");
                    if (type == null) {
                        Config.Â("Item type is null, player: " + this.Ø­áŒŠá);
                    }
                    else {
                        String modelPath = Json.HorizonCode_Horizon_È(item, "model");
                        if (modelPath == null) {
                            modelPath = "items/" + type + "/model.cfg";
                        }
                        final PlayerItemModel model = this.Â(modelPath);
                        if (model != null) {
                            if (!model.Ø­áŒŠá()) {
                                String texturePath = Json.HorizonCode_Horizon_È(item, "texture");
                                if (texturePath == null) {
                                    texturePath = "items/" + type + "/users/" + this.Ø­áŒŠá + ".png";
                                }
                                final BufferedImage image = this.HorizonCode_Horizon_È(texturePath);
                                if (image == null) {
                                    continue;
                                }
                                model.HorizonCode_Horizon_È(image);
                                final ResourceLocation_1975012498 loc = new ResourceLocation_1975012498("optifine.net", texturePath);
                                model.HorizonCode_Horizon_È(loc);
                            }
                            pc.HorizonCode_Horizon_È(model);
                        }
                    }
                }
            }
        }
        return pc;
    }
    
    private BufferedImage HorizonCode_Horizon_È(final String texturePath) {
        final String textureUrl = "http://s.optifine.net/" + texturePath;
        try {
            final BufferedImage e = ImageIO.read(new URL(textureUrl));
            return e;
        }
        catch (IOException var4) {
            Config.Â("Error loading item texture " + texturePath + ": " + var4.getClass().getName() + ": " + var4.getMessage());
            return null;
        }
    }
    
    private PlayerItemModel Â(final String modelPath) {
        final String modelUrl = "http://s.optifine.net/" + modelPath;
        try {
            final byte[] e = HttpUtils.HorizonCode_Horizon_È(modelUrl);
            final String jsonStr = new String(e, "ASCII");
            final JsonParser jp = new JsonParser();
            final JsonObject jo = (JsonObject)jp.parse(jsonStr);
            final PlayerItemParser pip = new PlayerItemParser();
            final PlayerItemModel pim = PlayerItemParser.HorizonCode_Horizon_È(jo);
            return pim;
        }
        catch (Exception var9) {
            Config.Â("Error loading item model " + modelPath + ": " + var9.getClass().getName() + ": " + var9.getMessage());
            return null;
        }
    }
}
