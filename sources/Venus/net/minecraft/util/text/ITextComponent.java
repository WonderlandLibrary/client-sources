/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.util.text;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.stream.JsonReader;
import com.mojang.brigadier.Message;
import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import mpp.venusfr.functions.impl.misc.NameProtect;
import net.minecraft.util.EnumTypeAdapterFactory;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Util;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextProperties;
import net.minecraft.util.text.KeybindTextComponent;
import net.minecraft.util.text.NBTTextComponent;
import net.minecraft.util.text.ScoreTextComponent;
import net.minecraft.util.text.SelectorTextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponent;
import net.minecraft.util.text.TranslationTextComponent;

public interface ITextComponent
extends Message,
ITextProperties {
    public Style getStyle();

    public String getUnformattedComponentText();

    @Override
    default public String getString() {
        return ITextProperties.super.getString();
    }

    default public String getStringTruncated(int n) {
        StringBuilder stringBuilder = new StringBuilder();
        this.getComponent(arg_0 -> ITextComponent.lambda$getStringTruncated$0(n, stringBuilder, arg_0));
        return stringBuilder.toString();
    }

    public List<ITextComponent> getSiblings();

    public IFormattableTextComponent copyRaw();

    public IFormattableTextComponent deepCopy();

    public IReorderingProcessor func_241878_f();

    @Override
    default public <T> Optional<T> getComponentWithStyle(ITextProperties.IStyledTextAcceptor<T> iStyledTextAcceptor, Style style) {
        Style style2 = this.getStyle().mergeStyle(style);
        Optional<T> optional = this.func_230534_b_(iStyledTextAcceptor, style2);
        if (optional.isPresent()) {
            return optional;
        }
        for (ITextComponent iTextComponent : this.getSiblings()) {
            Optional<T> optional2 = iTextComponent.getComponentWithStyle(iStyledTextAcceptor, style2);
            if (!optional2.isPresent()) continue;
            return optional2;
        }
        return Optional.empty();
    }

    @Override
    default public <T> Optional<T> getComponent(ITextProperties.ITextAcceptor<T> iTextAcceptor) {
        Optional<T> optional = this.func_230533_b_(iTextAcceptor);
        if (optional.isPresent()) {
            return optional;
        }
        for (ITextComponent iTextComponent : this.getSiblings()) {
            Optional<T> optional2 = iTextComponent.getComponent(iTextAcceptor);
            if (!optional2.isPresent()) continue;
            return optional2;
        }
        return Optional.empty();
    }

    default public <T> Optional<T> func_230534_b_(ITextProperties.IStyledTextAcceptor<T> iStyledTextAcceptor, Style style) {
        return iStyledTextAcceptor.accept(style, NameProtect.getReplaced(this.getUnformattedComponentText()));
    }

    default public <T> Optional<T> func_230533_b_(ITextProperties.ITextAcceptor<T> iTextAcceptor) {
        return iTextAcceptor.accept(NameProtect.getReplaced(this.getUnformattedComponentText()));
    }

    public static ITextComponent getTextComponentOrEmpty(@Nullable String string) {
        return string != null ? new StringTextComponent(string) : StringTextComponent.EMPTY;
    }

    private static Optional lambda$getStringTruncated$0(int n, StringBuilder stringBuilder, String string) {
        int n2 = n - stringBuilder.length();
        if (n2 <= 0) {
            return field_240650_b_;
        }
        stringBuilder.append(string.length() <= n2 ? string : string.substring(0, n2));
        return Optional.empty();
    }

    /*
     * Duplicate member names - consider using --renamedupmembers true
     */
    public static class Serializer
    implements JsonDeserializer<IFormattableTextComponent>,
    JsonSerializer<ITextComponent> {
        private static final Gson GSON = Util.make(Serializer::lambda$static$0);
        private static final Field JSON_READER_POS_FIELD = Util.make(Serializer::lambda$static$1);
        private static final Field JSON_READER_LINESTART_FIELD = Util.make(Serializer::lambda$static$2);

        @Override
        public IFormattableTextComponent deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            Object object;
            TextComponent textComponent;
            if (jsonElement.isJsonPrimitive()) {
                return new StringTextComponent(jsonElement.getAsString());
            }
            if (!jsonElement.isJsonObject()) {
                if (jsonElement.isJsonArray()) {
                    JsonArray jsonArray = jsonElement.getAsJsonArray();
                    IFormattableTextComponent iFormattableTextComponent = null;
                    for (JsonElement jsonElement2 : jsonArray) {
                        IFormattableTextComponent iFormattableTextComponent2 = this.deserialize(jsonElement2, jsonElement2.getClass(), jsonDeserializationContext);
                        if (iFormattableTextComponent == null) {
                            iFormattableTextComponent = iFormattableTextComponent2;
                            continue;
                        }
                        iFormattableTextComponent.append(iFormattableTextComponent2);
                    }
                    return iFormattableTextComponent;
                }
                throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            if (jsonObject.has("text")) {
                textComponent = new StringTextComponent(JSONUtils.getString(jsonObject, "text"));
            } else if (jsonObject.has("translate")) {
                object = JSONUtils.getString(jsonObject, "translate");
                if (jsonObject.has("with")) {
                    JsonArray jsonArray = JSONUtils.getJsonArray(jsonObject, "with");
                    Object[] objectArray = new Object[jsonArray.size()];
                    for (int i = 0; i < objectArray.length; ++i) {
                        StringTextComponent stringTextComponent;
                        objectArray[i] = this.deserialize(jsonArray.get(i), type, jsonDeserializationContext);
                        if (!(objectArray[i] instanceof StringTextComponent) || !(stringTextComponent = (StringTextComponent)objectArray[i]).getStyle().isEmpty() || !stringTextComponent.getSiblings().isEmpty()) continue;
                        objectArray[i] = stringTextComponent.getText();
                    }
                    textComponent = new TranslationTextComponent((String)object, objectArray);
                } else {
                    textComponent = new TranslationTextComponent((String)object);
                }
            } else if (jsonObject.has("score")) {
                object = JSONUtils.getJsonObject(jsonObject, "score");
                if (!((JsonObject)object).has("name") || !((JsonObject)object).has("objective")) {
                    throw new JsonParseException("A score component needs a least a name and an objective");
                }
                textComponent = new ScoreTextComponent(JSONUtils.getString((JsonObject)object, "name"), JSONUtils.getString((JsonObject)object, "objective"));
            } else if (jsonObject.has("selector")) {
                textComponent = new SelectorTextComponent(JSONUtils.getString(jsonObject, "selector"));
            } else if (jsonObject.has("keybind")) {
                textComponent = new KeybindTextComponent(JSONUtils.getString(jsonObject, "keybind"));
            } else {
                if (!jsonObject.has("nbt")) {
                    throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
                }
                object = JSONUtils.getString(jsonObject, "nbt");
                boolean bl = JSONUtils.getBoolean(jsonObject, "interpret", false);
                if (jsonObject.has("block")) {
                    textComponent = new NBTTextComponent.Block((String)object, bl, JSONUtils.getString(jsonObject, "block"));
                } else if (jsonObject.has("entity")) {
                    textComponent = new NBTTextComponent.Entity((String)object, bl, JSONUtils.getString(jsonObject, "entity"));
                } else {
                    if (!jsonObject.has("storage")) {
                        throw new JsonParseException("Don't know how to turn " + jsonElement + " into a Component");
                    }
                    textComponent = new NBTTextComponent.Storage((String)object, bl, new ResourceLocation(JSONUtils.getString(jsonObject, "storage")));
                }
            }
            if (jsonObject.has("extra")) {
                object = JSONUtils.getJsonArray(jsonObject, "extra");
                if (((JsonArray)object).size() <= 0) {
                    throw new JsonParseException("Unexpected empty array of components");
                }
                for (int i = 0; i < ((JsonArray)object).size(); ++i) {
                    textComponent.append(this.deserialize(((JsonArray)object).get(i), type, jsonDeserializationContext));
                }
            }
            textComponent.setStyle((Style)jsonDeserializationContext.deserialize(jsonElement, (Type)((Object)Style.class)));
            return textComponent;
        }

        private void serializeChatStyle(Style style, JsonObject jsonObject, JsonSerializationContext jsonSerializationContext) {
            JsonElement jsonElement = jsonSerializationContext.serialize(style);
            if (jsonElement.isJsonObject()) {
                JsonObject jsonObject2 = (JsonObject)jsonElement;
                for (Map.Entry<String, JsonElement> entry : jsonObject2.entrySet()) {
                    jsonObject.add(entry.getKey(), entry.getValue());
                }
            }
        }

        @Override
        public JsonElement serialize(ITextComponent iTextComponent, Type type, JsonSerializationContext jsonSerializationContext) {
            Object object;
            JsonObject jsonObject = new JsonObject();
            if (!iTextComponent.getStyle().isEmpty()) {
                this.serializeChatStyle(iTextComponent.getStyle(), jsonObject, jsonSerializationContext);
            }
            if (!iTextComponent.getSiblings().isEmpty()) {
                object = new JsonArray();
                for (ITextComponent objectArray : iTextComponent.getSiblings()) {
                    ((JsonArray)object).add(this.serialize(objectArray, (Type)objectArray.getClass(), jsonSerializationContext));
                }
                jsonObject.add("extra", (JsonElement)object);
            }
            if (iTextComponent instanceof StringTextComponent) {
                jsonObject.addProperty("text", ((StringTextComponent)iTextComponent).getText());
            } else if (iTextComponent instanceof TranslationTextComponent) {
                object = (TranslationTextComponent)iTextComponent;
                jsonObject.addProperty("translate", ((TranslationTextComponent)object).getKey());
                if (((TranslationTextComponent)object).getFormatArgs() != null && ((TranslationTextComponent)object).getFormatArgs().length > 0) {
                    var6_6 = new JsonArray();
                    for (Object object2 : ((TranslationTextComponent)object).getFormatArgs()) {
                        if (object2 instanceof ITextComponent) {
                            ((JsonArray)var6_6).add(this.serialize((ITextComponent)object2, (Type)object2.getClass(), jsonSerializationContext));
                            continue;
                        }
                        ((JsonArray)var6_6).add(new JsonPrimitive(String.valueOf(object2)));
                    }
                    jsonObject.add("with", (JsonElement)var6_6);
                }
            } else if (iTextComponent instanceof ScoreTextComponent) {
                object = (ScoreTextComponent)iTextComponent;
                var6_6 = new JsonObject();
                ((JsonObject)var6_6).addProperty("name", ((ScoreTextComponent)object).getName());
                ((JsonObject)var6_6).addProperty("objective", ((ScoreTextComponent)object).getObjective());
                jsonObject.add("score", (JsonElement)var6_6);
            } else if (iTextComponent instanceof SelectorTextComponent) {
                object = (SelectorTextComponent)iTextComponent;
                jsonObject.addProperty("selector", ((SelectorTextComponent)object).getSelector());
            } else if (iTextComponent instanceof KeybindTextComponent) {
                object = (KeybindTextComponent)iTextComponent;
                jsonObject.addProperty("keybind", ((KeybindTextComponent)object).getKeybind());
            } else {
                if (!(iTextComponent instanceof NBTTextComponent)) {
                    throw new IllegalArgumentException("Don't know how to serialize " + iTextComponent + " as a Component");
                }
                object = (NBTTextComponent)iTextComponent;
                jsonObject.addProperty("nbt", ((NBTTextComponent)object).func_218676_i());
                jsonObject.addProperty("interpret", ((NBTTextComponent)object).func_218677_j());
                if (iTextComponent instanceof NBTTextComponent.Block) {
                    var6_6 = (NBTTextComponent.Block)iTextComponent;
                    jsonObject.addProperty("block", ((NBTTextComponent.Block)var6_6).func_218683_k());
                } else if (iTextComponent instanceof NBTTextComponent.Entity) {
                    var6_6 = (NBTTextComponent.Entity)iTextComponent;
                    jsonObject.addProperty("entity", ((NBTTextComponent.Entity)var6_6).func_218687_k());
                } else {
                    if (!(iTextComponent instanceof NBTTextComponent.Storage)) {
                        throw new IllegalArgumentException("Don't know how to serialize " + iTextComponent + " as a Component");
                    }
                    var6_6 = (NBTTextComponent.Storage)iTextComponent;
                    jsonObject.addProperty("storage", ((NBTTextComponent.Storage)var6_6).func_229726_k_().toString());
                }
            }
            return jsonObject;
        }

        public static String toJson(ITextComponent iTextComponent) {
            return GSON.toJson(iTextComponent);
        }

        public static JsonElement toJsonTree(ITextComponent iTextComponent) {
            return GSON.toJsonTree(iTextComponent);
        }

        @Nullable
        public static IFormattableTextComponent getComponentFromJson(String string) {
            return JSONUtils.fromJson(GSON, string, IFormattableTextComponent.class, false);
        }

        @Nullable
        public static IFormattableTextComponent getComponentFromJson(JsonElement jsonElement) {
            return GSON.fromJson(jsonElement, IFormattableTextComponent.class);
        }

        @Nullable
        public static IFormattableTextComponent getComponentFromJsonLenient(String string) {
            return JSONUtils.fromJson(GSON, string, IFormattableTextComponent.class, true);
        }

        public static IFormattableTextComponent getComponentFromReader(com.mojang.brigadier.StringReader stringReader) {
            try {
                JsonReader jsonReader = new JsonReader(new StringReader(stringReader.getRemaining()));
                jsonReader.setLenient(true);
                IFormattableTextComponent iFormattableTextComponent = GSON.getAdapter(IFormattableTextComponent.class).read(jsonReader);
                stringReader.setCursor(stringReader.getCursor() + Serializer.getPos(jsonReader));
                return iFormattableTextComponent;
            } catch (IOException | StackOverflowError throwable) {
                throw new JsonParseException(throwable);
            }
        }

        private static int getPos(JsonReader jsonReader) {
            try {
                return JSON_READER_POS_FIELD.getInt(jsonReader) - JSON_READER_LINESTART_FIELD.getInt(jsonReader) + 1;
            } catch (IllegalAccessException illegalAccessException) {
                throw new IllegalStateException("Couldn't read position of JsonReader", illegalAccessException);
            }
        }

        @Override
        public Object deserialize(JsonElement jsonElement, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }

        @Override
        public JsonElement serialize(Object object, Type type, JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ITextComponent)object, type, jsonSerializationContext);
        }

        private static Field lambda$static$2() {
            try {
                new JsonReader(new StringReader(""));
                Field field = JsonReader.class.getDeclaredField("lineStart");
                field.setAccessible(false);
                return field;
            } catch (NoSuchFieldException noSuchFieldException) {
                throw new IllegalStateException("Couldn't get field 'lineStart' for JsonReader", noSuchFieldException);
            }
        }

        private static Field lambda$static$1() {
            try {
                new JsonReader(new StringReader(""));
                Field field = JsonReader.class.getDeclaredField("pos");
                field.setAccessible(false);
                return field;
            } catch (NoSuchFieldException noSuchFieldException) {
                throw new IllegalStateException("Couldn't get field 'pos' for JsonReader", noSuchFieldException);
            }
        }

        private static Gson lambda$static$0() {
            GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.disableHtmlEscaping();
            gsonBuilder.registerTypeHierarchyAdapter(ITextComponent.class, new Serializer());
            gsonBuilder.registerTypeHierarchyAdapter(Style.class, new Style.Serializer());
            gsonBuilder.registerTypeAdapterFactory(new EnumTypeAdapterFactory());
            return gsonBuilder.create();
        }
    }
}

