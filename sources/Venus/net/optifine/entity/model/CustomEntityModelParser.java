/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.optifine.entity.model;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.util.ResourceLocation;
import net.optifine.Config;
import net.optifine.config.ConnectedParser;
import net.optifine.entity.model.CustomEntityModel;
import net.optifine.entity.model.CustomEntityRenderer;
import net.optifine.entity.model.CustomModelRenderer;
import net.optifine.entity.model.anim.ModelUpdater;
import net.optifine.entity.model.anim.ModelVariableUpdater;
import net.optifine.player.PlayerItemParser;
import net.optifine.util.Json;

public class CustomEntityModelParser {
    public static final String ENTITY = "entity";
    public static final String TEXTURE = "texture";
    public static final String SHADOW_SIZE = "shadowSize";
    public static final String ITEM_TYPE = "type";
    public static final String ITEM_TEXTURE_SIZE = "textureSize";
    public static final String ITEM_USE_PLAYER_TEXTURE = "usePlayerTexture";
    public static final String ITEM_MODELS = "models";
    public static final String ITEM_ANIMATIONS = "animations";
    public static final String MODEL_ID = "id";
    public static final String MODEL_BASE_ID = "baseId";
    public static final String MODEL_MODEL = "model";
    public static final String MODEL_TYPE = "type";
    public static final String MODEL_PART = "part";
    public static final String MODEL_ATTACH = "attach";
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
    public static final String ENTITY_MODEL = "EntityModel";
    public static final String ENTITY_MODEL_PART = "EntityModelPart";

    public static CustomEntityRenderer parseEntityRender(JsonObject jsonObject, String string) {
        Object object;
        ConnectedParser connectedParser = new ConnectedParser("CustomEntityModels");
        String string2 = connectedParser.parseName(string);
        String string3 = connectedParser.parseBasePath(string);
        String string4 = Json.getString(jsonObject, TEXTURE);
        int[] nArray = Json.parseIntArray(jsonObject.get(ITEM_TEXTURE_SIZE), 2);
        float f = Json.getFloat(jsonObject, SHADOW_SIZE, -1.0f);
        JsonArray jsonArray = (JsonArray)jsonObject.get(ITEM_MODELS);
        CustomEntityModelParser.checkNull(jsonArray, "Missing models");
        HashMap hashMap = new HashMap();
        ArrayList<CustomModelRenderer> arrayList = new ArrayList<CustomModelRenderer>();
        for (int i = 0; i < jsonArray.size(); ++i) {
            object = (JsonObject)jsonArray.get(i);
            CustomEntityModelParser.processBaseId((JsonObject)object, hashMap);
            CustomEntityModelParser.processExternalModel((JsonObject)object, hashMap, string3);
            CustomEntityModelParser.processId((JsonObject)object, hashMap);
            CustomModelRenderer customModelRenderer = CustomEntityModelParser.parseCustomModelRenderer((JsonObject)object, nArray, string3);
            if (customModelRenderer == null) continue;
            arrayList.add(customModelRenderer);
        }
        CustomModelRenderer[] customModelRendererArray = arrayList.toArray(new CustomModelRenderer[arrayList.size()]);
        object = null;
        if (string4 != null) {
            object = CustomEntityModelParser.getResourceLocation(string3, string4, ".png");
        }
        return new CustomEntityRenderer(string2, string3, (ResourceLocation)object, customModelRendererArray, f);
    }

    private static void processBaseId(JsonObject jsonObject, Map map) {
        String string = Json.getString(jsonObject, MODEL_BASE_ID);
        if (string != null) {
            JsonObject jsonObject2 = (JsonObject)map.get(string);
            if (jsonObject2 == null) {
                Config.warn("BaseID not found: " + string);
            } else {
                CustomEntityModelParser.copyJsonElements(jsonObject2, jsonObject);
            }
        }
    }

    private static void processExternalModel(JsonObject jsonObject, Map map, String string) {
        String string2 = Json.getString(jsonObject, MODEL_MODEL);
        if (string2 != null) {
            ResourceLocation resourceLocation = CustomEntityModelParser.getResourceLocation(string, string2, ".jpm");
            try {
                JsonObject jsonObject2 = CustomEntityModelParser.loadJson(resourceLocation);
                if (jsonObject2 == null) {
                    Config.warn("Model not found: " + resourceLocation);
                    return;
                }
                CustomEntityModelParser.copyJsonElements(jsonObject2, jsonObject);
            } catch (IOException iOException) {
                Config.error(iOException.getClass().getName() + ": " + iOException.getMessage());
            } catch (JsonParseException jsonParseException) {
                Config.error(jsonParseException.getClass().getName() + ": " + jsonParseException.getMessage());
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        }
    }

    private static void copyJsonElements(JsonObject jsonObject, JsonObject jsonObject2) {
        for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
            if (entry.getKey().equals(MODEL_ID) || jsonObject2.has(entry.getKey())) continue;
            jsonObject2.add(entry.getKey(), entry.getValue());
        }
    }

    public static ResourceLocation getResourceLocation(String string, String object, String string2) {
        if (!((String)object).endsWith(string2)) {
            object = (String)object + string2;
        }
        if (!((String)object).contains("/")) {
            object = string + "/" + (String)object;
        } else if (((String)object).startsWith("./")) {
            object = string + "/" + ((String)object).substring(2);
        } else if (((String)object).startsWith("~/")) {
            object = "optifine/" + ((String)object).substring(2);
        }
        return new ResourceLocation((String)object);
    }

    private static void processId(JsonObject jsonObject, Map map) {
        String string = Json.getString(jsonObject, MODEL_ID);
        if (string != null) {
            if (string.length() < 1) {
                Config.warn("Empty model ID: " + string);
            } else if (map.containsKey(string)) {
                Config.warn("Duplicate model ID: " + string);
            } else {
                map.put(string, jsonObject);
            }
        }
    }

    public static CustomModelRenderer parseCustomModelRenderer(JsonObject jsonObject, int[] nArray, String string) {
        Object object;
        String string2 = Json.getString(jsonObject, MODEL_PART);
        CustomEntityModelParser.checkNull(string2, "Model part not specified, missing \"replace\" or \"attachTo\".");
        boolean bl = Json.getBoolean(jsonObject, MODEL_ATTACH, false);
        CustomEntityModel customEntityModel = new CustomEntityModel(RenderType::getEntityCutoutNoCull);
        if (nArray != null) {
            customEntityModel.textureWidth = nArray[0];
            customEntityModel.textureHeight = nArray[1];
        }
        ModelUpdater modelUpdater = null;
        JsonArray jsonArray = (JsonArray)jsonObject.get(ITEM_ANIMATIONS);
        if (jsonArray != null) {
            object = new ArrayList();
            for (int i = 0; i < jsonArray.size(); ++i) {
                JsonObject jsonObject2 = (JsonObject)jsonArray.get(i);
                for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                    String string3 = entry.getKey();
                    String string4 = entry.getValue().getAsString();
                    ModelVariableUpdater modelVariableUpdater = new ModelVariableUpdater(string3, string4);
                    object.add(modelVariableUpdater);
                }
            }
            if (object.size() > 0) {
                ModelVariableUpdater[] modelVariableUpdaterArray = object.toArray(new ModelVariableUpdater[object.size()]);
                modelUpdater = new ModelUpdater(modelVariableUpdaterArray);
            }
        }
        object = PlayerItemParser.parseModelRenderer(jsonObject, customEntityModel, nArray, string);
        return new CustomModelRenderer(string2, bl, (ModelRenderer)object, modelUpdater);
    }

    private static void checkNull(Object object, String string) {
        if (object == null) {
            throw new JsonParseException(string);
        }
    }

    public static JsonObject loadJson(ResourceLocation resourceLocation) throws IOException, JsonParseException {
        InputStream inputStream = Config.getResourceStream(resourceLocation);
        if (inputStream == null) {
            return null;
        }
        String string = Config.readInputStream(inputStream, "ASCII");
        inputStream.close();
        JsonParser jsonParser = new JsonParser();
        return (JsonObject)jsonParser.parse(string);
    }
}

