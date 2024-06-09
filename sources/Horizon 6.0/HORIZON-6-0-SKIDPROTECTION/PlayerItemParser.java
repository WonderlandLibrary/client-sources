package HORIZON-6-0-SKIDPROTECTION;

import java.util.Iterator;
import java.util.Set;
import com.google.gson.JsonElement;
import java.util.Map;
import java.util.ArrayList;
import java.util.HashMap;
import com.google.gson.JsonArray;
import java.awt.Dimension;
import com.google.gson.JsonParseException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class PlayerItemParser
{
    private static JsonParser Šáƒ;
    public static final String HorizonCode_Horizon_È = "type";
    public static final String Â = "textureSize";
    public static final String Ý = "usePlayerTexture";
    public static final String Ø­áŒŠá = "models";
    public static final String Âµá€ = "id";
    public static final String Ó = "baseId";
    public static final String à = "type";
    public static final String Ø = "attachTo";
    public static final String áŒŠÆ = "invertAxis";
    public static final String áˆºÑ¢Õ = "mirrorTexture";
    public static final String ÂµÈ = "translate";
    public static final String á = "rotate";
    public static final String ˆÏ­ = "scale";
    public static final String £á = "boxes";
    public static final String Å = "sprites";
    public static final String £à = "submodel";
    public static final String µà = "submodels";
    public static final String ˆà = "textureOffset";
    public static final String ¥Æ = "coordinates";
    public static final String Ø­à = "sizeAdd";
    public static final String µÕ = "PlayerItem";
    public static final String Æ = "ModelBox";
    
    static {
        PlayerItemParser.Šáƒ = new JsonParser();
    }
    
    public static PlayerItemModel HorizonCode_Horizon_È(final JsonObject obj) {
        final String type = Json.HorizonCode_Horizon_È(obj, "type");
        if (!Config.HorizonCode_Horizon_È(type, (Object)"PlayerItem")) {
            throw new JsonParseException("Unknown model type: " + type);
        }
        final int[] textureSize = Json.Â(obj.get("textureSize"), 2);
        HorizonCode_Horizon_È(textureSize, "Missing texture size");
        final Dimension textureDim = new Dimension(textureSize[0], textureSize[1]);
        final boolean usePlayerTexture = Json.HorizonCode_Horizon_È(obj, "usePlayerTexture", false);
        final JsonArray models = (JsonArray)obj.get("models");
        HorizonCode_Horizon_È(models, "Missing elements");
        final HashMap mapModelJsons = new HashMap();
        final ArrayList listModels = new ArrayList();
        new ArrayList();
        for (int modelRenderers = 0; modelRenderers < models.size(); ++modelRenderers) {
            final JsonObject elem = (JsonObject)models.get(modelRenderers);
            final String baseId = Json.HorizonCode_Horizon_È(elem, "baseId");
            if (baseId != null) {
                final JsonObject id = mapModelJsons.get(baseId);
                if (id == null) {
                    Config.Â("BaseID not found: " + baseId);
                    continue;
                }
                final Set mr = id.entrySet();
                for (final Map.Entry entry : mr) {
                    if (!elem.has((String)entry.getKey())) {
                        elem.add((String)entry.getKey(), (JsonElement)entry.getValue());
                    }
                }
            }
            final String var17 = Json.HorizonCode_Horizon_È(elem, "id");
            if (var17 != null) {
                if (!mapModelJsons.containsKey(var17)) {
                    mapModelJsons.put(var17, elem);
                }
                else {
                    Config.Â("Duplicate model ID: " + var17);
                }
            }
            final PlayerItemRenderer var18 = HorizonCode_Horizon_È(elem, textureDim);
            if (var18 != null) {
                listModels.add(var18);
            }
        }
        final PlayerItemRenderer[] var19 = listModels.toArray(new PlayerItemRenderer[listModels.size()]);
        return new PlayerItemModel(textureDim, usePlayerTexture, var19);
    }
    
    private static void HorizonCode_Horizon_È(final Object obj, final String msg) {
        if (obj == null) {
            throw new JsonParseException(msg);
        }
    }
    
    private static ResourceLocation_1975012498 HorizonCode_Horizon_È(final String texture) {
        final int pos = texture.indexOf(58);
        if (pos < 0) {
            return new ResourceLocation_1975012498(texture);
        }
        final String domain = texture.substring(0, pos);
        final String path = texture.substring(pos + 1);
        return new ResourceLocation_1975012498(domain, path);
    }
    
    private static int Â(final String attachModelStr) {
        if (attachModelStr == null) {
            return 0;
        }
        if (attachModelStr.equals("body")) {
            return 0;
        }
        if (attachModelStr.equals("head")) {
            return 1;
        }
        if (attachModelStr.equals("leftArm")) {
            return 2;
        }
        if (attachModelStr.equals("rightArm")) {
            return 3;
        }
        if (attachModelStr.equals("leftLeg")) {
            return 4;
        }
        if (attachModelStr.equals("rightLeg")) {
            return 5;
        }
        if (attachModelStr.equals("cape")) {
            return 6;
        }
        Config.Â("Unknown attachModel: " + attachModelStr);
        return 0;
    }
    
    private static PlayerItemRenderer HorizonCode_Horizon_È(final JsonObject elem, final Dimension textureDim) {
        final String type = Json.HorizonCode_Horizon_È(elem, "type");
        if (!Config.HorizonCode_Horizon_È(type, (Object)"ModelBox")) {
            Config.Â("Unknown model type: " + type);
            return null;
        }
        final String attachToStr = Json.HorizonCode_Horizon_È(elem, "attachTo");
        final int attachTo = Â(attachToStr);
        final float scale = Json.HorizonCode_Horizon_È(elem, "scale", 1.0f);
        final ModelPlayerItem modelBase = new ModelPlayerItem();
        modelBase.áŒŠÆ = textureDim.width;
        modelBase.áˆºÑ¢Õ = textureDim.height;
        final ModelRenderer mr = HorizonCode_Horizon_È(elem, modelBase);
        final PlayerItemRenderer pir = new PlayerItemRenderer(attachTo, scale, mr);
        return pir;
    }
    
    private static ModelRenderer HorizonCode_Horizon_È(final JsonObject elem, final ModelBase modelBase) {
        final ModelRenderer mr = new ModelRenderer(modelBase);
        final String invertAxis = Json.HorizonCode_Horizon_È(elem, "invertAxis", "").toLowerCase();
        final boolean invertX = invertAxis.contains("x");
        final boolean invertY = invertAxis.contains("y");
        final boolean invertZ = invertAxis.contains("z");
        final float[] translate = Json.HorizonCode_Horizon_È(elem.get("translate"), 3, new float[3]);
        if (invertX) {
            translate[0] = -translate[0];
        }
        if (invertY) {
            translate[1] = -translate[1];
        }
        if (invertZ) {
            translate[2] = -translate[2];
        }
        final float[] rotateAngles = Json.HorizonCode_Horizon_È(elem.get("rotate"), 3, new float[3]);
        for (int mirrorTexture = 0; mirrorTexture < rotateAngles.length; ++mirrorTexture) {
            rotateAngles[mirrorTexture] = rotateAngles[mirrorTexture] / 180.0f * 3.1415927f;
        }
        if (invertX) {
            rotateAngles[0] = -rotateAngles[0];
        }
        if (invertY) {
            rotateAngles[1] = -rotateAngles[1];
        }
        if (invertZ) {
            rotateAngles[2] = -rotateAngles[2];
        }
        mr.HorizonCode_Horizon_È(translate[0], translate[1], translate[2]);
        mr.Ó = rotateAngles[0];
        mr.à = rotateAngles[1];
        mr.Ø = rotateAngles[2];
        final String var19 = Json.HorizonCode_Horizon_È(elem, "mirrorTexture", "").toLowerCase();
        final boolean invertU = var19.contains("u");
        final boolean invertV = var19.contains("v");
        if (invertU) {
            mr.áŒŠÆ = true;
        }
        if (invertV) {
            mr.¥Æ = true;
        }
        final JsonArray boxes = elem.getAsJsonArray("boxes");
        if (boxes != null) {
            for (int sprites = 0; sprites < boxes.size(); ++sprites) {
                final JsonObject submodel = boxes.get(sprites).getAsJsonObject();
                final int[] submodels = Json.Â(submodel.get("textureOffset"), 2);
                if (submodels == null) {
                    throw new JsonParseException("Texture offset not specified");
                }
                final float[] i = Json.HorizonCode_Horizon_È(submodel.get("coordinates"), 6);
                if (i == null) {
                    throw new JsonParseException("Coordinates not specified");
                }
                if (invertX) {
                    i[0] = -i[0] - i[3];
                }
                if (invertY) {
                    i[1] = -i[1] - i[4];
                }
                if (invertZ) {
                    i[2] = -i[2] - i[5];
                }
                final float sm = Json.HorizonCode_Horizon_È(submodel, "sizeAdd", 0.0f);
                mr.HorizonCode_Horizon_È(submodels[0], submodels[1]);
                mr.HorizonCode_Horizon_È(i[0], i[1], i[2], (int)i[3], (int)i[4], (int)i[5], sm);
            }
        }
        final JsonArray var20 = elem.getAsJsonArray("sprites");
        if (var20 != null) {
            for (int var21 = 0; var21 < var20.size(); ++var21) {
                final JsonObject var22 = var20.get(var21).getAsJsonObject();
                final int[] var23 = Json.Â(var22.get("textureOffset"), 2);
                if (var23 == null) {
                    throw new JsonParseException("Texture offset not specified");
                }
                final float[] var24 = Json.HorizonCode_Horizon_È(var22.get("coordinates"), 6);
                if (var24 == null) {
                    throw new JsonParseException("Coordinates not specified");
                }
                if (invertX) {
                    var24[0] = -var24[0] - var24[3];
                }
                if (invertY) {
                    var24[1] = -var24[1] - var24[4];
                }
                if (invertZ) {
                    var24[2] = -var24[2] - var24[5];
                }
                final float subMr = Json.HorizonCode_Horizon_È(var22, "sizeAdd", 0.0f);
                mr.HorizonCode_Horizon_È(var23[0], var23[1]);
                mr.Â(var24[0], var24[1], var24[2], (int)var24[3], (int)var24[4], (int)var24[5], subMr);
            }
        }
        final JsonObject submodel = (JsonObject)elem.get("submodel");
        if (submodel != null) {
            final ModelRenderer var25 = HorizonCode_Horizon_È(submodel, modelBase);
            mr.HorizonCode_Horizon_È(var25);
        }
        final JsonArray var26 = (JsonArray)elem.get("submodels");
        if (var26 != null) {
            for (int var27 = 0; var27 < var26.size(); ++var27) {
                final JsonObject var28 = (JsonObject)var26.get(var27);
                final ModelRenderer var29 = HorizonCode_Horizon_È(var28, modelBase);
                mr.HorizonCode_Horizon_È(var29);
            }
        }
        return mr;
    }
}
