package net.minecraft.util;

import java.lang.reflect.*;
import java.util.*;
import com.google.gson.*;

public interface IChatComponent extends Iterable<IChatComponent>
{
    IChatComponent appendText(final String p0);
    
    String getUnformattedText();
    
    List<IChatComponent> getSiblings();
    
    IChatComponent createCopy();
    
    IChatComponent setChatStyle(final ChatStyle p0);
    
    IChatComponent appendSibling(final IChatComponent p0);
    
    ChatStyle getChatStyle();
    
    String getFormattedText();
    
    String getUnformattedTextForChat();
    
    public static class Serializer implements JsonDeserializer<IChatComponent>, JsonSerializer<IChatComponent>
    {
        private static final String[] I;
        private static final Gson GSON;
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        static {
            I();
            final GsonBuilder gsonBuilder = new GsonBuilder();
            gsonBuilder.registerTypeHierarchyAdapter((Class)IChatComponent.class, (Object)new Serializer());
            gsonBuilder.registerTypeHierarchyAdapter((Class)ChatStyle.class, (Object)new ChatStyle.Serializer());
            gsonBuilder.registerTypeAdapterFactory((TypeAdapterFactory)new EnumTypeAdapterFactory());
            GSON = gsonBuilder.create();
        }
        
        public static IChatComponent jsonToComponent(final String s) {
            return (IChatComponent)Serializer.GSON.fromJson(s, (Class)IChatComponent.class);
        }
        
        public static String componentToJson(final IChatComponent chatComponent) {
            return Serializer.GSON.toJson((Object)chatComponent);
        }
        
        private static String I(final String s, final String s2) {
            final StringBuilder sb = new StringBuilder();
            final char[] charArray = s2.toCharArray();
            int length = "".length();
            final char[] charArray2 = s.toCharArray();
            final int length2 = charArray2.length;
            int i = "".length();
            while (i < length2) {
                sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
                ++length;
                ++i;
                "".length();
                if (4 != 4) {
                    throw null;
                }
            }
            return sb.toString();
        }
        
        private static void I() {
            (I = new String[0x85 ^ 0xA6])["".length()] = I("+(;H0O,;\u00003O/:\u0018d\u001b(u\u001b1\u001d)u", "oGUoD");
            Serializer.I[" ".length()] = I("Z9\u001d8\u0015Z1S\u000f\u0015\u0017 \u001c\"\u001f\u0014$", "zPsLz");
            Serializer.I["  ".length()] = I("\u001f\u00046<", "kaNHH");
            Serializer.I["   ".length()] = I("8\u000e!\u001f", "LkYkk");
            Serializer.I[0xB6 ^ 0xB2] = I("\"8\u000f#\u0001:+\u001a(", "VJnMr");
            Serializer.I[0x9 ^ 0xC] = I("&1)\u0002\u0018>\"<\t", "RCHlk");
            Serializer.I[0x87 ^ 0x81] = I("\u001d0\u001f\u0005", "jYkmJ");
            Serializer.I[0x49 ^ 0x4E] = I("\u0001$$=", "vMPUU");
            Serializer.I[0x99 ^ 0x91] = I("#,5\u0004/", "POZvJ");
            Serializer.I[0x1A ^ 0x13] = I("\u0001\u0013=\u0013=", "rpRaX");
            Serializer.I[0xA6 ^ 0xAC] = I("\u001a9,<", "tXAYC");
            Serializer.I[0x5E ^ 0x55] = I(";$)=2 /5=", "TFCXQ");
            Serializer.I[0x8E ^ 0x82] = I("\u0013F\u0019;\u0000 \u0003J;\u0000?\u0016\u00056\n<\u0012J6\n7\u0002\u0019x\u000er\n\u000f9\u001c&F\u000bx\u00013\u000b\u000fx\u000e<\u0002J9\u0001r\t\b2\n1\u0012\u0003.\n", "RfjXo");
            Serializer.I[0x51 ^ 0x5C] = I("\u000b\u000f\u001a\u0010", "enwum");
            Serializer.I[0x20 ^ 0x2E] = I("<\u0013\u001e6\t'\u0018\u00026", "SqtSj");
            Serializer.I[0x69 ^ 0x66] = I("\u0001\u0019\u0003\u0006+", "wxosN");
            Serializer.I[0x12 ^ 0x2] = I("\u0000'=!!", "vFQTD");
            Serializer.I[0xB8 ^ 0xA9] = I(":\u001f\u001f5\u0011=\u0015\u0001", "IzsPr");
            Serializer.I[0xB9 ^ 0xAB] = I("6\u001e\nc\u0012R\u001a\n+\u0011R\u0019\u000b3F\u0006\u001eD0\u0013\u0000\u001fD", "rqdDf");
            Serializer.I[0x6C ^ 0x7F] = I("A\u0018\u00175\bA\u0010Y\u0002\b\f\u0001\u0016/\u0002\u000f\u0005", "aqyAg");
            Serializer.I[0x6 ^ 0x12] = I("\u001a\u0016\u000f\u0011\u0000\u001d\u001c\u0011", "isctc");
            Serializer.I[0x3B ^ 0x2E] = I("1\r6*\u0005", "TuBXd");
            Serializer.I[0x68 ^ 0x7E] = I("+( \u00057", "NPTwV");
            Serializer.I[0x70 ^ 0x67] = I("&\n\u001d\u001f6\u0016\u0007\f\u0002\"S\u0001\u0015\u00172\nD\u0019\u00154\u0012\u001dX\b S\u0007\u0017\n6\u001c\n\u001d\t2\u0000", "sdxgF");
            Serializer.I[0xB ^ 0x13] = I("+\u001d\u00155\u0000", "NeaGa");
            Serializer.I[0xDB ^ 0xC2] = I("\u001c1\u000b\u0000", "hTstr");
            Serializer.I[0x93 ^ 0x89] = I("'5\n\u0001\u0011?&\u001f\n", "SGkob");
            Serializer.I[0x12 ^ 0x9] = I("\u0007\u0018\u0011,", "pqeDl");
            Serializer.I[0x6D ^ 0x71] = I(">+\n,", "PJgIh");
            Serializer.I[0xB7 ^ 0xAA] = I("\t\t\u000e= \u0012\u0002\u0012=", "fkdXC");
            Serializer.I[0xAD ^ 0xB3] = I("2\u0015\u001e'/", "DtrRJ");
            Serializer.I[0x50 ^ 0x4F] = I(":'!\u0003\u000f", "IDNqj");
            Serializer.I[0xB3 ^ 0x93] = I("\n6?H<n2?\u0000?n1>\u0018h:6q\u001c-<00\u0003!4<q", "NYQoH");
            Serializer.I[0x89 ^ 0xA8] = I("u\u000b\u001an*u)\u0006#;:\u0004\f ?", "UjiNK");
            Serializer.I[0x16 ^ 0x34] = I("\u001b\u0011+/!\u001c\u001b5", "htGJB");
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((IChatComponent)o, type, jsonSerializationContext);
        }
        
        private void serializeChatStyle(final ChatStyle chatStyle, final JsonObject jsonObject, final JsonSerializationContext jsonSerializationContext) {
            final JsonElement serialize = jsonSerializationContext.serialize((Object)chatStyle);
            if (serialize.isJsonObject()) {
                final Iterator iterator = ((JsonObject)serialize).entrySet().iterator();
                "".length();
                if (true != true) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final Map.Entry<String, V> entry = iterator.next();
                    jsonObject.add((String)entry.getKey(), (JsonElement)entry.getValue());
                }
            }
        }
        
        public IChatComponent deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (jsonElement.isJsonPrimitive()) {
                return new ChatComponentText(jsonElement.getAsString());
            }
            if (jsonElement.isJsonObject()) {
                final JsonObject asJsonObject = jsonElement.getAsJsonObject();
                ChatComponentStyle chatComponentStyle;
                if (asJsonObject.has(Serializer.I["  ".length()])) {
                    chatComponentStyle = new ChatComponentText(asJsonObject.get(Serializer.I["   ".length()]).getAsString());
                    "".length();
                    if (2 < 2) {
                        throw null;
                    }
                }
                else if (asJsonObject.has(Serializer.I[0x4F ^ 0x4B])) {
                    final String asString = asJsonObject.get(Serializer.I[0xBE ^ 0xBB]).getAsString();
                    if (asJsonObject.has(Serializer.I[0x82 ^ 0x84])) {
                        final JsonArray asJsonArray = asJsonObject.getAsJsonArray(Serializer.I[0x2 ^ 0x5]);
                        final Object[] array = new Object[asJsonArray.size()];
                        int i = "".length();
                        "".length();
                        if (3 != 3) {
                            throw null;
                        }
                        while (i < array.length) {
                            array[i] = this.deserialize(asJsonArray.get(i), type, jsonDeserializationContext);
                            if (array[i] instanceof ChatComponentText) {
                                final ChatComponentText chatComponentText = (ChatComponentText)array[i];
                                if (chatComponentText.getChatStyle().isEmpty() && chatComponentText.getSiblings().isEmpty()) {
                                    array[i] = chatComponentText.getChatComponentText_TextValue();
                                }
                            }
                            ++i;
                        }
                        chatComponentStyle = new ChatComponentTranslation(asString, array);
                        "".length();
                        if (2 <= 0) {
                            throw null;
                        }
                    }
                    else {
                        chatComponentStyle = new ChatComponentTranslation(asString, new Object["".length()]);
                        "".length();
                        if (0 >= 1) {
                            throw null;
                        }
                    }
                }
                else if (asJsonObject.has(Serializer.I[0xCC ^ 0xC4])) {
                    final JsonObject asJsonObject2 = asJsonObject.getAsJsonObject(Serializer.I[0x5B ^ 0x52]);
                    if (!asJsonObject2.has(Serializer.I[0x4C ^ 0x46]) || !asJsonObject2.has(Serializer.I[0x8D ^ 0x86])) {
                        throw new JsonParseException(Serializer.I[0x63 ^ 0x6F]);
                    }
                    chatComponentStyle = new ChatComponentScore(JsonUtils.getString(asJsonObject2, Serializer.I[0x3 ^ 0xE]), JsonUtils.getString(asJsonObject2, Serializer.I[0x56 ^ 0x58]));
                    if (asJsonObject2.has(Serializer.I[0xA1 ^ 0xAE])) {
                        ((ChatComponentScore)chatComponentStyle).setValue(JsonUtils.getString(asJsonObject2, Serializer.I[0x95 ^ 0x85]));
                        "".length();
                        if (2 == 0) {
                            throw null;
                        }
                    }
                }
                else {
                    if (!asJsonObject.has(Serializer.I[0xB9 ^ 0xA8])) {
                        throw new JsonParseException(Serializer.I[0x55 ^ 0x47] + jsonElement.toString() + Serializer.I[0xA9 ^ 0xBA]);
                    }
                    chatComponentStyle = new ChatComponentSelector(JsonUtils.getString(asJsonObject, Serializer.I[0x34 ^ 0x20]));
                }
                if (asJsonObject.has(Serializer.I[0x9E ^ 0x8B])) {
                    final JsonArray asJsonArray2 = asJsonObject.getAsJsonArray(Serializer.I[0x75 ^ 0x63]);
                    if (asJsonArray2.size() <= 0) {
                        throw new JsonParseException(Serializer.I[0x80 ^ 0x97]);
                    }
                    int j = "".length();
                    "".length();
                    if (-1 >= 4) {
                        throw null;
                    }
                    while (j < asJsonArray2.size()) {
                        chatComponentStyle.appendSibling(this.deserialize(asJsonArray2.get(j), type, jsonDeserializationContext));
                        ++j;
                    }
                }
                chatComponentStyle.setChatStyle((ChatStyle)jsonDeserializationContext.deserialize(jsonElement, (Type)ChatStyle.class));
                return chatComponentStyle;
            }
            if (!jsonElement.isJsonArray()) {
                throw new JsonParseException(Serializer.I["".length()] + jsonElement.toString() + Serializer.I[" ".length()]);
            }
            final JsonArray asJsonArray3 = jsonElement.getAsJsonArray();
            IChatComponent chatComponent = null;
            final Iterator iterator = asJsonArray3.iterator();
            "".length();
            if (3 <= 2) {
                throw null;
            }
            while (iterator.hasNext()) {
                final JsonElement jsonElement2 = iterator.next();
                final IChatComponent deserialize = this.deserialize(jsonElement2, (Type)jsonElement2.getClass(), jsonDeserializationContext);
                if (chatComponent == null) {
                    chatComponent = deserialize;
                    "".length();
                    if (2 < -1) {
                        throw null;
                    }
                    continue;
                }
                else {
                    chatComponent.appendSibling(deserialize);
                }
            }
            return chatComponent;
        }
        
        public JsonElement serialize(final IChatComponent chatComponent, final Type type, final JsonSerializationContext jsonSerializationContext) {
            if (chatComponent instanceof ChatComponentText && chatComponent.getChatStyle().isEmpty() && chatComponent.getSiblings().isEmpty()) {
                return (JsonElement)new JsonPrimitive(((ChatComponentText)chatComponent).getChatComponentText_TextValue());
            }
            final JsonObject jsonObject = new JsonObject();
            if (!chatComponent.getChatStyle().isEmpty()) {
                this.serializeChatStyle(chatComponent.getChatStyle(), jsonObject, jsonSerializationContext);
            }
            if (!chatComponent.getSiblings().isEmpty()) {
                final JsonArray jsonArray = new JsonArray();
                final Iterator<IChatComponent> iterator = chatComponent.getSiblings().iterator();
                "".length();
                if (2 == -1) {
                    throw null;
                }
                while (iterator.hasNext()) {
                    final IChatComponent chatComponent2 = iterator.next();
                    jsonArray.add(this.serialize(chatComponent2, chatComponent2.getClass(), jsonSerializationContext));
                }
                jsonObject.add(Serializer.I[0x8 ^ 0x10], (JsonElement)jsonArray);
            }
            if (chatComponent instanceof ChatComponentText) {
                jsonObject.addProperty(Serializer.I[0x3F ^ 0x26], ((ChatComponentText)chatComponent).getChatComponentText_TextValue());
                "".length();
                if (4 <= 3) {
                    throw null;
                }
            }
            else if (chatComponent instanceof ChatComponentTranslation) {
                final ChatComponentTranslation chatComponentTranslation = (ChatComponentTranslation)chatComponent;
                jsonObject.addProperty(Serializer.I[0xB2 ^ 0xA8], chatComponentTranslation.getKey());
                if (chatComponentTranslation.getFormatArgs() != null && chatComponentTranslation.getFormatArgs().length > 0) {
                    final JsonArray jsonArray2 = new JsonArray();
                    final Object[] formatArgs;
                    final int length = (formatArgs = chatComponentTranslation.getFormatArgs()).length;
                    int i = "".length();
                    "".length();
                    if (false) {
                        throw null;
                    }
                    while (i < length) {
                        final Object o = formatArgs[i];
                        if (o instanceof IChatComponent) {
                            jsonArray2.add(this.serialize((IChatComponent)o, ((IChatComponent)o).getClass(), jsonSerializationContext));
                            "".length();
                            if (0 >= 1) {
                                throw null;
                            }
                        }
                        else {
                            jsonArray2.add((JsonElement)new JsonPrimitive(String.valueOf(o)));
                        }
                        ++i;
                    }
                    jsonObject.add(Serializer.I[0x7B ^ 0x60], (JsonElement)jsonArray2);
                    "".length();
                    if (-1 >= 3) {
                        throw null;
                    }
                }
            }
            else if (chatComponent instanceof ChatComponentScore) {
                final ChatComponentScore chatComponentScore = (ChatComponentScore)chatComponent;
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty(Serializer.I[0x34 ^ 0x28], chatComponentScore.getName());
                jsonObject2.addProperty(Serializer.I[0xA7 ^ 0xBA], chatComponentScore.getObjective());
                jsonObject2.addProperty(Serializer.I[0x76 ^ 0x68], chatComponentScore.getUnformattedTextForChat());
                jsonObject.add(Serializer.I[0x23 ^ 0x3C], (JsonElement)jsonObject2);
                "".length();
                if (1 == 2) {
                    throw null;
                }
            }
            else {
                if (!(chatComponent instanceof ChatComponentSelector)) {
                    throw new IllegalArgumentException(Serializer.I[0xA4 ^ 0x84] + chatComponent + Serializer.I[0xE2 ^ 0xC3]);
                }
                jsonObject.addProperty(Serializer.I[0x24 ^ 0x6], ((ChatComponentSelector)chatComponent).getSelector());
            }
            return (JsonElement)jsonObject;
        }
    }
}
