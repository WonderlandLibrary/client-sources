/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.player;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.Model;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.optifine.Config;
import net.optifine.entity.model.CustomEntityModelParser;
import net.optifine.player.ModelPlayerItem;
import net.optifine.player.PlayerItemModel;
import net.optifine.player.PlayerItemRenderer;
import net.optifine.util.Json;

public class PlayerItemParser {
    private static JsonParser jsonParser = new JsonParser();
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_TEXTURE_SIZE = "textureSize";
    public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
    public static final String ITEM_MODELS = "models";
    public static final String MODEL_ID = "id";
    public static final String MODEL_BASE_ID = "baseId";
    public static final String MODEL_TYPE = "type";
    public static final String MODEL_TEXTURE = "texture";
    public static final String MODEL_TEXTURE_SIZE = "textureSize";
    public static final String MODEL_ATTACH_TO = "attachTo";
    public static final String MODEL_INVERT_AXIS = "invertAxis";
    public static final String MODEL_MIRROR_TEXTURE = "mirrorTexture";
    public static final String MODEL_TRANSLATE = "translate";
    public static final String MODEL_ROTATE = "rotate";
    public static final String MODEL_SCALE = "scale";
    public static final String MODEL_BOXES = "boxes";
    public static final String MODEL_SPRITES = "sprites";
    public static final String MODEL_SUBMODEL = "submodel";
    public static final String MODEL_SUBMODELS = "submodels";
    public static final String BOX_TEXTURE_OFFSET = "textureOffset";
    public static final String BOX_COORDINATES = "coordinates";
    public static final String BOX_SIZE_ADD = "sizeAdd";
    public static final String BOX_UV_DOWN = "uvDown";
    public static final String BOX_UV_UP = "uvUp";
    public static final String BOX_UV_NORTH = "uvNorth";
    public static final String BOX_UV_SOUTH = "uvSouth";
    public static final String BOX_UV_WEST = "uvWest";
    public static final String BOX_UV_EAST = "uvEast";
    public static final String BOX_UV_FRONT = "uvFront";
    public static final String BOX_UV_BACK = "uvBack";
    public static final String BOX_UV_LEFT = "uvLeft";
    public static final String BOX_UV_RIGHT = "uvRight";
    public static final String ITEM_TYPE_MODEL = "PlayerItem";
    public static final String MODEL_TYPE_BOX = "ModelBox";

    private PlayerItemParser() {
    }

    public static PlayerItemModel parseItemModel(JsonObject jsonObject) {
        String string = Json.getString(jsonObject, "type");
        if (!Config.equals(string, ITEM_TYPE_MODEL)) {
            throw new JsonParseException("Unknown model type: " + string);
        }
        int[] nArray = Json.parseIntArray(jsonObject.get("textureSize"), 2);
        PlayerItemParser.checkNull(nArray, "Missing texture size");
        Dimension dimension = new Dimension(nArray[0], nArray[1]);
        boolean bl = Json.getBoolean(jsonObject, ITEM_USE_PLAYER_TEXTURE, false);
        JsonArray jsonArray = (JsonArray)jsonObject.get(ITEM_MODELS);
        PlayerItemParser.checkNull(jsonArray, "Missing elements");
        HashMap<Object, JsonObject> hashMap = new HashMap<Object, JsonObject>();
        ArrayList<Object> arrayList = new ArrayList<Object>();
        new ArrayList();
        for (int i = 0; i < jsonArray.size(); ++i) {
            Object object;
            Object object2;
            JsonObject jsonObject2 = (JsonObject)jsonArray.get(i);
            String string2 = Json.getString(jsonObject2, MODEL_BASE_ID);
            if (string2 != null) {
                object2 = (JsonObject)hashMap.get(string2);
                if (object2 == null) {
                    Config.warn("BaseID not found: " + string2);
                    continue;
                }
                object = ((JsonObject)object2).entrySet().iterator();
                while (object.hasNext()) {
                    Map.Entry entry = (Map.Entry)object.next();
                    if (jsonObject2.has((String)entry.getKey())) continue;
                    jsonObject2.add((String)entry.getKey(), (JsonElement)entry.getValue());
                }
            }
            if ((object2 = Json.getString(jsonObject2, MODEL_ID)) != null) {
                if (!hashMap.containsKey(object2)) {
                    hashMap.put(object2, jsonObject2);
                } else {
                    Config.warn("Duplicate model ID: " + (String)object2);
                }
            }
            if ((object = PlayerItemParser.parseItemRenderer(jsonObject2, dimension)) == null) continue;
            arrayList.add(object);
        }
        PlayerItemRenderer[] playerItemRendererArray = arrayList.toArray(new PlayerItemRenderer[arrayList.size()]);
        return new PlayerItemModel(dimension, bl, playerItemRendererArray);
    }

    private static void checkNull(Object object, String string) {
        if (object == null) {
            throw new JsonParseException(string);
        }
    }

    private static ResourceLocation makeResourceLocation(String string) {
        int n = string.indexOf(58);
        if (n < 0) {
            return new ResourceLocation(string);
        }
        String string2 = string.substring(0, n);
        String string3 = string.substring(n + 1);
        return new ResourceLocation(string2, string3);
    }

    private static int parseAttachModel(String string) {
        if (string == null) {
            return 1;
        }
        if (string.equals("body")) {
            return 1;
        }
        if (string.equals("head")) {
            return 0;
        }
        if (string.equals("leftArm")) {
            return 1;
        }
        if (string.equals("rightArm")) {
            return 0;
        }
        if (string.equals("leftLeg")) {
            return 1;
        }
        if (string.equals("rightLeg")) {
            return 0;
        }
        if (string.equals("cape")) {
            return 1;
        }
        Config.warn("Unknown attachModel: " + string);
        return 1;
    }

    public static PlayerItemRenderer parseItemRenderer(JsonObject jsonObject, Dimension dimension) {
        String string = Json.getString(jsonObject, "type");
        if (!Config.equals(string, MODEL_TYPE_BOX)) {
            Config.warn("Unknown model type: " + string);
            return null;
        }
        String string2 = Json.getString(jsonObject, MODEL_ATTACH_TO);
        int n = PlayerItemParser.parseAttachModel(string2);
        ModelPlayerItem modelPlayerItem = new ModelPlayerItem(RenderType::getEntityCutoutNoCull);
        modelPlayerItem.textureWidth = dimension.width;
        modelPlayerItem.textureHeight = dimension.height;
        ModelRenderer modelRenderer = PlayerItemParser.parseModelRenderer(jsonObject, modelPlayerItem, null, null);
        return new PlayerItemRenderer(n, modelRenderer);
    }

    public static ModelRenderer parseModelRenderer(JsonObject jsonObject, Model model, int[] nArray, String string) {
        JsonObject jsonObject2;
        JsonArray jsonArray;
        float f;
        Object object;
        Object object2;
        Object object3;
        JsonArray jsonArray2;
        int[] nArray2;
        float f2;
        ModelRenderer modelRenderer = new ModelRenderer(model);
        String string2 = Json.getString(jsonObject, MODEL_ID);
        modelRenderer.setId(string2);
        modelRenderer.scaleX = f2 = Json.getFloat(jsonObject, MODEL_SCALE, 1.0f);
        modelRenderer.scaleY = f2;
        modelRenderer.scaleZ = f2;
        String string3 = Json.getString(jsonObject, MODEL_TEXTURE);
        if (string3 != null) {
            modelRenderer.setTextureLocation(CustomEntityModelParser.getResourceLocation(string, string3, ".png"));
        }
        if ((nArray2 = Json.parseIntArray(jsonObject.get("textureSize"), 2)) == null) {
            nArray2 = nArray;
        }
        if (nArray2 != null) {
            modelRenderer.setTextureSize(nArray2[0], nArray2[1]);
        }
        String string4 = Json.getString(jsonObject, MODEL_INVERT_AXIS, "").toLowerCase();
        boolean bl = string4.contains("x");
        boolean bl2 = string4.contains("y");
        boolean bl3 = string4.contains("z");
        float[] fArray = Json.parseFloatArray(jsonObject.get(MODEL_TRANSLATE), 3, new float[3]);
        if (bl) {
            fArray[0] = -fArray[0];
        }
        if (bl2) {
            fArray[1] = -fArray[1];
        }
        if (bl3) {
            fArray[2] = -fArray[2];
        }
        float[] fArray2 = Json.parseFloatArray(jsonObject.get(MODEL_ROTATE), 3, new float[3]);
        for (int i = 0; i < fArray2.length; ++i) {
            fArray2[i] = fArray2[i] / 180.0f * MathHelper.PI;
        }
        if (bl) {
            fArray2[0] = -fArray2[0];
        }
        if (bl2) {
            fArray2[1] = -fArray2[1];
        }
        if (bl3) {
            fArray2[2] = -fArray2[2];
        }
        modelRenderer.setRotationPoint(fArray[0], fArray[1], fArray[2]);
        modelRenderer.rotateAngleX = fArray2[0];
        modelRenderer.rotateAngleY = fArray2[1];
        modelRenderer.rotateAngleZ = fArray2[2];
        String string5 = Json.getString(jsonObject, MODEL_MIRROR_TEXTURE, "").toLowerCase();
        boolean bl4 = string5.contains("u");
        boolean bl5 = string5.contains("v");
        if (bl4) {
            modelRenderer.mirror = true;
        }
        if (bl5) {
            modelRenderer.mirrorV = true;
        }
        if ((jsonArray2 = jsonObject.getAsJsonArray(MODEL_BOXES)) != null) {
            for (int i = 0; i < jsonArray2.size(); ++i) {
                JsonObject jsonObject3 = jsonArray2.get(i).getAsJsonObject();
                object3 = Json.parseIntArray(jsonObject3.get(BOX_TEXTURE_OFFSET), 2);
                object2 = PlayerItemParser.parseFaceUvs(jsonObject3);
                if (object3 == null && object2 == null) {
                    throw new JsonParseException("Texture offset not specified");
                }
                object = Json.parseFloatArray(jsonObject3.get(BOX_COORDINATES), 6);
                if (object == null) {
                    throw new JsonParseException("Coordinates not specified");
                }
                if (bl) {
                    object[0] = -object[0] - object[3];
                }
                if (bl2) {
                    object[1] = -object[1] - object[4];
                }
                if (bl3) {
                    object[2] = -object[2] - object[5];
                }
                f = Json.getFloat(jsonObject3, BOX_SIZE_ADD, 0.0f);
                if (object2 != null) {
                    modelRenderer.addBox((int[][])object2, object[0], object[1], object[2], object[3], object[4], object[5], f);
                    continue;
                }
                modelRenderer.setTextureOffset((int)object3[0], (int)object3[1]);
                modelRenderer.addBox(object[0], object[1], object[2], (float)((int)object[3]), (float)((int)object[4]), (float)((int)object[5]), f);
            }
        }
        if ((jsonArray = jsonObject.getAsJsonArray(MODEL_SPRITES)) != null) {
            for (int i = 0; i < jsonArray.size(); ++i) {
                object3 = jsonArray.get(i).getAsJsonObject();
                object2 = Json.parseIntArray(((JsonObject)object3).get(BOX_TEXTURE_OFFSET), 2);
                if (object2 == null) {
                    throw new JsonParseException("Texture offset not specified");
                }
                object = Json.parseFloatArray(((JsonObject)object3).get(BOX_COORDINATES), 6);
                if (object == null) {
                    throw new JsonParseException("Coordinates not specified");
                }
                if (bl) {
                    object[0] = -object[0] - object[3];
                }
                if (bl2) {
                    object[1] = -object[1] - object[4];
                }
                if (bl3) {
                    object[2] = -object[2] - object[5];
                }
                f = Json.getFloat((JsonObject)object3, BOX_SIZE_ADD, 0.0f);
                modelRenderer.setTextureOffset((int)object2[0], (int)object2[1]);
                modelRenderer.addSprite(object[0], object[1], object[2], (int)object[3], (int)object[4], (int)object[5], f);
            }
        }
        if ((jsonObject2 = (JsonObject)jsonObject.get(MODEL_SUBMODEL)) != null) {
            object3 = PlayerItemParser.parseModelRenderer(jsonObject2, model, nArray2, string);
            modelRenderer.addChild((ModelRenderer)object3);
        }
        if ((object3 = (Object)((JsonArray)jsonObject.get(MODEL_SUBMODELS))) != null) {
            for (int i = 0; i < ((JsonArray)object3).size(); ++i) {
                ModelRenderer modelRenderer2;
                object = (JsonObject)((JsonArray)object3).get(i);
                ModelRenderer modelRenderer3 = PlayerItemParser.parseModelRenderer((JsonObject)object, model, nArray2, string);
                if (modelRenderer3.getId() != null && (modelRenderer2 = modelRenderer.getChild(modelRenderer3.getId())) != null) {
                    Config.warn("Duplicate model ID: " + modelRenderer3.getId());
                }
                modelRenderer.addChild(modelRenderer3);
            }
        }
        return modelRenderer;
    }

    private static int[][] parseFaceUvs(JsonObject jsonObject) {
        int[][] nArrayArray = new int[][]{Json.parseIntArray(jsonObject.get(BOX_UV_DOWN), 4), Json.parseIntArray(jsonObject.get(BOX_UV_UP), 4), Json.parseIntArray(jsonObject.get(BOX_UV_NORTH), 4), Json.parseIntArray(jsonObject.get(BOX_UV_SOUTH), 4), Json.parseIntArray(jsonObject.get(BOX_UV_WEST), 4), Json.parseIntArray(jsonObject.get(BOX_UV_EAST), 4)};
        if (nArrayArray[0] == null) {
            nArrayArray[2] = Json.parseIntArray(jsonObject.get(BOX_UV_FRONT), 4);
        }
        if (nArrayArray[5] == null) {
            nArrayArray[3] = Json.parseIntArray(jsonObject.get(BOX_UV_BACK), 4);
        }
        if (nArrayArray[5] == null) {
            nArrayArray[4] = Json.parseIntArray(jsonObject.get(BOX_UV_LEFT), 4);
        }
        if (nArrayArray[7] == null) {
            nArrayArray[5] = Json.parseIntArray(jsonObject.get(BOX_UV_RIGHT), 4);
        }
        boolean bl = false;
        for (int i = 0; i < nArrayArray.length; ++i) {
            if (nArrayArray[i] == null) continue;
            bl = true;
        }
        return !bl ? (Object)null : nArrayArray;
    }
}

