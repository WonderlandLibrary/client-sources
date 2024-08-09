/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.chat;

import com.viaversion.viabackwards.ViaBackwards;
import com.viaversion.viabackwards.api.rewriters.TranslatableRewriter;
import com.viaversion.viabackwards.protocol.protocol1_15_2to1_16.Protocol1_15_2To1_16;
import com.viaversion.viaversion.api.Via;
import com.viaversion.viaversion.libs.gson.JsonElement;
import com.viaversion.viaversion.libs.gson.JsonObject;
import com.viaversion.viaversion.libs.gson.JsonParseException;
import com.viaversion.viaversion.libs.gson.JsonPrimitive;
import com.viaversion.viaversion.libs.kyori.adventure.text.Component;
import com.viaversion.viaversion.protocols.protocol1_13to1_12_2.ChatRewriter;
import com.viaversion.viaversion.protocols.protocol1_16to1_15_2.ClientboundPackets1_16;

public class TranslatableRewriter1_16
extends TranslatableRewriter<ClientboundPackets1_16> {
    private static final ChatColor[] COLORS = new ChatColor[]{new ChatColor("black", 0), new ChatColor("dark_blue", 170), new ChatColor("dark_green", 43520), new ChatColor("dark_aqua", 43690), new ChatColor("dark_red", 0xAA0000), new ChatColor("dark_purple", 0xAA00AA), new ChatColor("gold", 0xFFAA00), new ChatColor("gray", 0xAAAAAA), new ChatColor("dark_gray", 0x555555), new ChatColor("blue", 0x5555FF), new ChatColor("green", 0x55FF55), new ChatColor("aqua", 0x55FFFF), new ChatColor("red", 0xFF5555), new ChatColor("light_purple", 0xFF55FF), new ChatColor("yellow", 0xFFFF55), new ChatColor("white", 0xFFFFFF)};

    public TranslatableRewriter1_16(Protocol1_15_2To1_16 protocol1_15_2To1_16) {
        super(protocol1_15_2To1_16);
    }

    @Override
    public void processText(JsonElement jsonElement) {
        block8: {
            Object object;
            Object object2;
            super.processText(jsonElement);
            if (jsonElement == null || !jsonElement.isJsonObject()) {
                return;
            }
            JsonObject jsonObject = jsonElement.getAsJsonObject();
            JsonPrimitive jsonPrimitive = jsonObject.getAsJsonPrimitive("color");
            if (jsonPrimitive != null && !((String)(object2 = jsonPrimitive.getAsString())).isEmpty() && ((String)object2).charAt(0) == '#') {
                int n = Integer.parseInt(((String)object2).substring(1), 16);
                object = this.getClosestChatColor(n);
                jsonObject.addProperty("color", (String)object);
            }
            if ((object2 = jsonObject.getAsJsonObject("hoverEvent")) == null || !((JsonObject)object2).has("contents")) {
                return;
            }
            try {
                Component component = ChatRewriter.HOVER_GSON_SERIALIZER.deserializeFromTree(jsonObject);
                try {
                    object = (JsonObject)ChatRewriter.HOVER_GSON_SERIALIZER.serializeToTree(component);
                } catch (JsonParseException jsonParseException) {
                    JsonObject jsonObject2 = ((JsonObject)object2).getAsJsonObject("contents");
                    if (jsonObject2.remove("tag") == null) {
                        throw jsonParseException;
                    }
                    component = ChatRewriter.HOVER_GSON_SERIALIZER.deserializeFromTree(jsonObject);
                    object = (JsonObject)ChatRewriter.HOVER_GSON_SERIALIZER.serializeToTree(component);
                }
                JsonObject jsonObject3 = ((JsonObject)object).getAsJsonObject("hoverEvent");
                jsonObject3.remove("contents");
                jsonObject.add("hoverEvent", jsonObject3);
            } catch (Exception exception) {
                if (Via.getConfig().isSuppressConversionWarnings()) break block8;
                ViaBackwards.getPlatform().getLogger().severe("Error converting hover event component: " + jsonObject);
                exception.printStackTrace();
            }
        }
    }

    private String getClosestChatColor(int n) {
        int n2 = n >> 16 & 0xFF;
        int n3 = n >> 8 & 0xFF;
        int n4 = n & 0xFF;
        ChatColor chatColor = null;
        int n5 = 0;
        for (ChatColor chatColor2 : COLORS) {
            if (ChatColor.access$000(chatColor2) == n) {
                return ChatColor.access$100(chatColor2);
            }
            int n6 = (ChatColor.access$200(chatColor2) + n2) / 2;
            int n7 = ChatColor.access$200(chatColor2) - n2;
            int n8 = ChatColor.access$300(chatColor2) - n3;
            int n9 = ChatColor.access$400(chatColor2) - n4;
            int n10 = (2 + (n6 >> 8)) * n7 * n7 + 4 * n8 * n8 + (2 + (255 - n6 >> 8)) * n9 * n9;
            if (chatColor != null && n10 >= n5) continue;
            chatColor = chatColor2;
            n5 = n10;
        }
        return ChatColor.access$100(chatColor);
    }

    private static final class ChatColor {
        private final String colorName;
        private final int rgb;
        private final int r;
        private final int g;
        private final int b;

        ChatColor(String string, int n) {
            this.colorName = string;
            this.rgb = n;
            this.r = n >> 16 & 0xFF;
            this.g = n >> 8 & 0xFF;
            this.b = n & 0xFF;
        }

        static int access$000(ChatColor chatColor) {
            return chatColor.rgb;
        }

        static String access$100(ChatColor chatColor) {
            return chatColor.colorName;
        }

        static int access$200(ChatColor chatColor) {
            return chatColor.r;
        }

        static int access$300(ChatColor chatColor) {
            return chatColor.g;
        }

        static int access$400(ChatColor chatColor) {
            return chatColor.b;
        }
    }
}

