package net.minecraft.util;

import net.minecraft.event.*;
import java.lang.reflect.*;
import com.google.gson.*;

public class ChatStyle
{
    private Boolean underlined;
    private Boolean bold;
    private EnumChatFormatting color;
    private Boolean strikethrough;
    private ChatStyle parentStyle;
    private Boolean obfuscated;
    private ClickEvent chatClickEvent;
    private HoverEvent chatHoverEvent;
    private String insertion;
    private static final ChatStyle rootStyle;
    private Boolean italic;
    private static final String[] I;
    
    static void access$5(final ChatStyle chatStyle, final EnumChatFormatting color) {
        chatStyle.color = color;
    }
    
    public boolean getUnderlined() {
        boolean b;
        if (this.underlined == null) {
            b = this.getParent().getUnderlined();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            b = this.underlined;
        }
        return b;
    }
    
    public ChatStyle setInsertion(final String insertion) {
        this.insertion = insertion;
        return this;
    }
    
    public ChatStyle createShallowCopy() {
        final ChatStyle chatStyle = new ChatStyle();
        chatStyle.bold = this.bold;
        chatStyle.italic = this.italic;
        chatStyle.strikethrough = this.strikethrough;
        chatStyle.underlined = this.underlined;
        chatStyle.obfuscated = this.obfuscated;
        chatStyle.color = this.color;
        chatStyle.chatClickEvent = this.chatClickEvent;
        chatStyle.chatHoverEvent = this.chatHoverEvent;
        chatStyle.parentStyle = this.parentStyle;
        chatStyle.insertion = this.insertion;
        return chatStyle;
    }
    
    public ChatStyle setUnderlined(final Boolean underlined) {
        this.underlined = underlined;
        return this;
    }
    
    public ChatStyle setColor(final EnumChatFormatting color) {
        this.color = color;
        return this;
    }
    
    public boolean isEmpty() {
        if (this.bold == null && this.italic == null && this.strikethrough == null && this.underlined == null && this.obfuscated == null && this.color == null && this.chatClickEvent == null && this.chatHoverEvent == null) {
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    public boolean getObfuscated() {
        boolean b;
        if (this.obfuscated == null) {
            b = this.getParent().getObfuscated();
            "".length();
            if (4 <= 1) {
                throw null;
            }
        }
        else {
            b = this.obfuscated;
        }
        return b;
    }
    
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder(ChatStyle.I[" ".length()]);
        int n;
        if (this.parentStyle != null) {
            n = " ".length();
            "".length();
            if (4 == 2) {
                throw null;
            }
        }
        else {
            n = "".length();
        }
        return sb.append(n != 0).append(ChatStyle.I["  ".length()]).append(this.color).append(ChatStyle.I["   ".length()]).append(this.bold).append(ChatStyle.I[0xC3 ^ 0xC7]).append(this.italic).append(ChatStyle.I[0x2B ^ 0x2E]).append(this.underlined).append(ChatStyle.I[0x4B ^ 0x4D]).append(this.obfuscated).append(ChatStyle.I[0x8F ^ 0x88]).append(this.getChatClickEvent()).append(ChatStyle.I[0xA0 ^ 0xA8]).append(this.getChatHoverEvent()).append(ChatStyle.I[0xCC ^ 0xC5]).append(this.getInsertion()).append((char)(0x65 ^ 0x18)).toString();
    }
    
    public ChatStyle setItalic(final Boolean italic) {
        this.italic = italic;
        return this;
    }
    
    public ChatStyle setChatClickEvent(final ClickEvent chatClickEvent) {
        this.chatClickEvent = chatClickEvent;
        return this;
    }
    
    static void access$0(final ChatStyle chatStyle, final Boolean bold) {
        chatStyle.bold = bold;
    }
    
    public boolean getStrikethrough() {
        boolean b;
        if (this.strikethrough == null) {
            b = this.getParent().getStrikethrough();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        else {
            b = this.strikethrough;
        }
        return b;
    }
    
    static EnumChatFormatting access$14(final ChatStyle chatStyle) {
        return chatStyle.color;
    }
    
    public ChatStyle setChatHoverEvent(final HoverEvent chatHoverEvent) {
        this.chatHoverEvent = chatHoverEvent;
        return this;
    }
    
    static ClickEvent access$16(final ChatStyle chatStyle) {
        return chatStyle.chatClickEvent;
    }
    
    static void access$4(final ChatStyle chatStyle, final Boolean obfuscated) {
        chatStyle.obfuscated = obfuscated;
    }
    
    @Override
    public int hashCode() {
        return (0x2A ^ 0x35) * ((0x61 ^ 0x7E) * ((0x4 ^ 0x1B) * ((0xBA ^ 0xA5) * ((0xDA ^ 0xC5) * ((0x29 ^ 0x36) * ((0x11 ^ 0xE) * ((0x74 ^ 0x6B) * this.color.hashCode() + this.bold.hashCode()) + this.italic.hashCode()) + this.underlined.hashCode()) + this.strikethrough.hashCode()) + this.obfuscated.hashCode()) + this.chatClickEvent.hashCode()) + this.chatHoverEvent.hashCode()) + this.insertion.hashCode();
    }
    
    public String getInsertion() {
        String s;
        if (this.insertion == null) {
            s = this.getParent().getInsertion();
            "".length();
            if (4 < 1) {
                throw null;
            }
        }
        else {
            s = this.insertion;
        }
        return s;
    }
    
    static String access$15(final ChatStyle chatStyle) {
        return chatStyle.insertion;
    }
    
    public boolean getItalic() {
        boolean b;
        if (this.italic == null) {
            b = this.getParent().getItalic();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            b = this.italic;
        }
        return b;
    }
    
    static Boolean access$13(final ChatStyle chatStyle) {
        return chatStyle.obfuscated;
    }
    
    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return " ".length() != 0;
        }
        if (!(o instanceof ChatStyle)) {
            return "".length() != 0;
        }
        final ChatStyle chatStyle = (ChatStyle)o;
        if (this.getBold() == chatStyle.getBold() && this.getColor() == chatStyle.getColor() && this.getItalic() == chatStyle.getItalic() && this.getObfuscated() == chatStyle.getObfuscated() && this.getStrikethrough() == chatStyle.getStrikethrough() && this.getUnderlined() == chatStyle.getUnderlined()) {
            if (this.getChatClickEvent() != null) {
                if (!this.getChatClickEvent().equals(chatStyle.getChatClickEvent())) {
                    "".length();
                    if (4 < 0) {
                        throw null;
                    }
                    return "".length() != 0;
                }
            }
            else if (chatStyle.getChatClickEvent() != null) {
                "".length();
                if (true != true) {
                    throw null;
                }
                return "".length() != 0;
            }
            if (this.getChatHoverEvent() != null) {
                if (!this.getChatHoverEvent().equals(chatStyle.getChatHoverEvent())) {
                    "".length();
                    if (2 <= -1) {
                        throw null;
                    }
                    return "".length() != 0;
                }
            }
            else if (chatStyle.getChatHoverEvent() != null) {
                "".length();
                if (3 != 3) {
                    throw null;
                }
                return "".length() != 0;
            }
            if (this.getInsertion() != null) {
                if (!this.getInsertion().equals(chatStyle.getInsertion())) {
                    return "".length() != 0;
                }
                "".length();
                if (1 <= 0) {
                    throw null;
                }
            }
            else {
                if (chatStyle.getInsertion() != null) {
                    return "".length() != 0;
                }
                "".length();
                if (4 < -1) {
                    throw null;
                }
            }
            return " ".length() != 0;
        }
        return "".length() != 0;
    }
    
    static {
        I();
        rootStyle = new ChatStyle() {
            private static final String[] I;
            
            @Override
            public ChatStyle createDeepCopy() {
                return this;
            }
            
            @Override
            public boolean getItalic() {
                return "".length() != 0;
            }
            
            @Override
            public boolean getObfuscated() {
                return "".length() != 0;
            }
            
            @Override
            public String toString() {
                return ChatStyle$1.I["".length()];
            }
            
            @Override
            public boolean getUnderlined() {
                return "".length() != 0;
            }
            
            @Override
            public ChatStyle setStrikethrough(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public EnumChatFormatting getColor() {
                return null;
            }
            
            @Override
            public boolean getStrikethrough() {
                return "".length() != 0;
            }
            
            @Override
            public ChatStyle setColor(final EnumChatFormatting enumChatFormatting) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public String getFormattingCode() {
                return ChatStyle$1.I[" ".length()];
            }
            
            @Override
            public ClickEvent getChatClickEvent() {
                return null;
            }
            
            @Override
            public HoverEvent getChatHoverEvent() {
                return null;
            }
            
            @Override
            public boolean getBold() {
                return "".length() != 0;
            }
            
            static {
                I();
            }
            
            @Override
            public ChatStyle createShallowCopy() {
                return this;
            }
            
            @Override
            public String getInsertion() {
                return null;
            }
            
            @Override
            public ChatStyle setObfuscated(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setParentStyle(final ChatStyle chatStyle) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setChatHoverEvent(final HoverEvent hoverEvent) {
                throw new UnsupportedOperationException();
            }
            
            private static void I() {
                (I = new String["  ".length()])["".length()] = I("> \u0003\u0019\u0003C\u00065:2", "mTzuf");
                ChatStyle$1.I[" ".length()] = I("", "lNRCm");
            }
            
            @Override
            public ChatStyle setUnderlined(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setBold(final Boolean b) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle setItalic(final Boolean b) {
                throw new UnsupportedOperationException();
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
                    if (2 <= 0) {
                        throw null;
                    }
                }
                return sb.toString();
            }
            
            @Override
            public ChatStyle setChatClickEvent(final ClickEvent clickEvent) {
                throw new UnsupportedOperationException();
            }
        };
    }
    
    static void access$3(final ChatStyle chatStyle, final Boolean strikethrough) {
        chatStyle.strikethrough = strikethrough;
    }
    
    public boolean getBold() {
        boolean b;
        if (this.bold == null) {
            b = this.getParent().getBold();
            "".length();
            if (2 != 2) {
                throw null;
            }
        }
        else {
            b = this.bold;
        }
        return b;
    }
    
    static void access$1(final ChatStyle chatStyle, final Boolean italic) {
        chatStyle.italic = italic;
    }
    
    public EnumChatFormatting getColor() {
        EnumChatFormatting enumChatFormatting;
        if (this.color == null) {
            enumChatFormatting = this.getParent().getColor();
            "".length();
            if (2 < 1) {
                throw null;
            }
        }
        else {
            enumChatFormatting = this.color;
        }
        return enumChatFormatting;
    }
    
    public String getFormattingCode() {
        if (this.isEmpty()) {
            String formattingCode;
            if (this.parentStyle != null) {
                formattingCode = this.parentStyle.getFormattingCode();
                "".length();
                if (2 == 1) {
                    throw null;
                }
            }
            else {
                formattingCode = ChatStyle.I["".length()];
            }
            return formattingCode;
        }
        final StringBuilder sb = new StringBuilder();
        if (this.getColor() != null) {
            sb.append(this.getColor());
        }
        if (this.getBold()) {
            sb.append(EnumChatFormatting.BOLD);
        }
        if (this.getItalic()) {
            sb.append(EnumChatFormatting.ITALIC);
        }
        if (this.getUnderlined()) {
            sb.append(EnumChatFormatting.UNDERLINE);
        }
        if (this.getObfuscated()) {
            sb.append(EnumChatFormatting.OBFUSCATED);
        }
        if (this.getStrikethrough()) {
            sb.append(EnumChatFormatting.STRIKETHROUGH);
        }
        return sb.toString();
    }
    
    static Boolean access$11(final ChatStyle chatStyle) {
        return chatStyle.underlined;
    }
    
    static void access$2(final ChatStyle chatStyle, final Boolean underlined) {
        chatStyle.underlined = underlined;
    }
    
    public ChatStyle setParentStyle(final ChatStyle parentStyle) {
        this.parentStyle = parentStyle;
        return this;
    }
    
    private ChatStyle getParent() {
        ChatStyle chatStyle;
        if (this.parentStyle == null) {
            chatStyle = ChatStyle.rootStyle;
            "".length();
            if (2 < -1) {
                throw null;
            }
        }
        else {
            chatStyle = this.parentStyle;
        }
        return chatStyle;
    }
    
    static void access$7(final ChatStyle chatStyle, final ClickEvent chatClickEvent) {
        chatStyle.chatClickEvent = chatClickEvent;
    }
    
    static void access$8(final ChatStyle chatStyle, final HoverEvent chatHoverEvent) {
        chatStyle.chatHoverEvent = chatHoverEvent;
    }
    
    private static void I() {
        (I = new String[0xAD ^ 0xA7])["".length()] = I("", "jOsTi");
        ChatStyle.I[" ".length()] = I(":$>6'\u00128&)\u0012\b\"\"46T", "iPGZB");
        ChatStyle.I["  ".length()] = I("Da5?/\u00073k", "hAVPC");
        ChatStyle.I["   ".length()] = I("@x\u0017\u001e4\be", "lXuqX");
        ChatStyle.I[0x70 ^ 0x74] = I("UO\u0003\f6\u0015\u0006\tE", "yojxW");
        ChatStyle.I[0x30 ^ 0x35] = I("jO2<=#\u001d+;7#\u000bz", "FoGRY");
        ChatStyle.I[0x29 ^ 0x2F] = I("Kc)-!\u00120%.3\u0002'{", "gCFOG");
        ChatStyle.I[0x81 ^ 0x86] = I("cr-\u001a\",9\u000b\u0000.!&s", "ORNvK");
        ChatStyle.I[0xF ^ 0x7] = I("DK8>8\r\u0019\u0015'+\u0006\u001fm", "hkPQN");
        ChatStyle.I[0x7A ^ 0x73] = I("bb9$4+0$#( \u007f", "NBPJG");
    }
    
    static Boolean access$10(final ChatStyle chatStyle) {
        return chatStyle.italic;
    }
    
    static Boolean access$9(final ChatStyle chatStyle) {
        return chatStyle.bold;
    }
    
    public HoverEvent getChatHoverEvent() {
        HoverEvent hoverEvent;
        if (this.chatHoverEvent == null) {
            hoverEvent = this.getParent().getChatHoverEvent();
            "".length();
            if (4 <= -1) {
                throw null;
            }
        }
        else {
            hoverEvent = this.chatHoverEvent;
        }
        return hoverEvent;
    }
    
    public ChatStyle createDeepCopy() {
        final ChatStyle chatStyle = new ChatStyle();
        chatStyle.setBold(this.getBold());
        chatStyle.setItalic(this.getItalic());
        chatStyle.setStrikethrough(this.getStrikethrough());
        chatStyle.setUnderlined(this.getUnderlined());
        chatStyle.setObfuscated(this.getObfuscated());
        chatStyle.setColor(this.getColor());
        chatStyle.setChatClickEvent(this.getChatClickEvent());
        chatStyle.setChatHoverEvent(this.getChatHoverEvent());
        chatStyle.setInsertion(this.getInsertion());
        return chatStyle;
    }
    
    public ClickEvent getChatClickEvent() {
        ClickEvent clickEvent;
        if (this.chatClickEvent == null) {
            clickEvent = this.getParent().getChatClickEvent();
            "".length();
            if (-1 >= 4) {
                throw null;
            }
        }
        else {
            clickEvent = this.chatClickEvent;
        }
        return clickEvent;
    }
    
    static void access$6(final ChatStyle chatStyle, final String insertion) {
        chatStyle.insertion = insertion;
    }
    
    public ChatStyle setStrikethrough(final Boolean strikethrough) {
        this.strikethrough = strikethrough;
        return this;
    }
    
    public ChatStyle setBold(final Boolean bold) {
        this.bold = bold;
        return this;
    }
    
    static Boolean access$12(final ChatStyle chatStyle) {
        return chatStyle.strikethrough;
    }
    
    static HoverEvent access$17(final ChatStyle chatStyle) {
        return chatStyle.chatHoverEvent;
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
            if (2 == 0) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    public ChatStyle setObfuscated(final Boolean obfuscated) {
        this.obfuscated = obfuscated;
        return this;
    }
    
    public static class Serializer implements JsonDeserializer<ChatStyle>, JsonSerializer<ChatStyle>
    {
        private static final String[] I;
        
        public ChatStyle deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            if (!jsonElement.isJsonObject()) {
                return null;
            }
            final ChatStyle chatStyle = new ChatStyle();
            final JsonObject asJsonObject = jsonElement.getAsJsonObject();
            if (asJsonObject == null) {
                return null;
            }
            if (asJsonObject.has(Serializer.I["".length()])) {
                ChatStyle.access$0(chatStyle, asJsonObject.get(Serializer.I[" ".length()]).getAsBoolean());
            }
            if (asJsonObject.has(Serializer.I["  ".length()])) {
                ChatStyle.access$1(chatStyle, asJsonObject.get(Serializer.I["   ".length()]).getAsBoolean());
            }
            if (asJsonObject.has(Serializer.I[0x2E ^ 0x2A])) {
                ChatStyle.access$2(chatStyle, asJsonObject.get(Serializer.I[0x8D ^ 0x88]).getAsBoolean());
            }
            if (asJsonObject.has(Serializer.I[0x4D ^ 0x4B])) {
                ChatStyle.access$3(chatStyle, asJsonObject.get(Serializer.I[0x35 ^ 0x32]).getAsBoolean());
            }
            if (asJsonObject.has(Serializer.I[0x46 ^ 0x4E])) {
                ChatStyle.access$4(chatStyle, asJsonObject.get(Serializer.I[0xCE ^ 0xC7]).getAsBoolean());
            }
            if (asJsonObject.has(Serializer.I[0x21 ^ 0x2B])) {
                ChatStyle.access$5(chatStyle, (EnumChatFormatting)jsonDeserializationContext.deserialize(asJsonObject.get(Serializer.I[0x21 ^ 0x2A]), (Type)EnumChatFormatting.class));
            }
            if (asJsonObject.has(Serializer.I[0x78 ^ 0x74])) {
                ChatStyle.access$6(chatStyle, asJsonObject.get(Serializer.I[0xB ^ 0x6]).getAsString());
            }
            if (asJsonObject.has(Serializer.I[0xB3 ^ 0xBD])) {
                final JsonObject asJsonObject2 = asJsonObject.getAsJsonObject(Serializer.I[0x99 ^ 0x96]);
                if (asJsonObject2 != null) {
                    final JsonPrimitive asJsonPrimitive = asJsonObject2.getAsJsonPrimitive(Serializer.I[0x7E ^ 0x6E]);
                    ClickEvent.Action valueByCanonicalName;
                    if (asJsonPrimitive == null) {
                        valueByCanonicalName = null;
                        "".length();
                        if (-1 >= 3) {
                            throw null;
                        }
                    }
                    else {
                        valueByCanonicalName = ClickEvent.Action.getValueByCanonicalName(asJsonPrimitive.getAsString());
                    }
                    final ClickEvent.Action action = valueByCanonicalName;
                    final JsonPrimitive asJsonPrimitive2 = asJsonObject2.getAsJsonPrimitive(Serializer.I[0xAD ^ 0xBC]);
                    String asString;
                    if (asJsonPrimitive2 == null) {
                        asString = null;
                        "".length();
                        if (3 <= 1) {
                            throw null;
                        }
                    }
                    else {
                        asString = asJsonPrimitive2.getAsString();
                    }
                    final String s = asString;
                    if (action != null && s != null && action.shouldAllowInChat()) {
                        ChatStyle.access$7(chatStyle, new ClickEvent(action, s));
                    }
                }
            }
            if (asJsonObject.has(Serializer.I[0x6C ^ 0x7E])) {
                final JsonObject asJsonObject3 = asJsonObject.getAsJsonObject(Serializer.I[0x82 ^ 0x91]);
                if (asJsonObject3 != null) {
                    final JsonPrimitive asJsonPrimitive3 = asJsonObject3.getAsJsonPrimitive(Serializer.I[0x16 ^ 0x2]);
                    HoverEvent.Action valueByCanonicalName2;
                    if (asJsonPrimitive3 == null) {
                        valueByCanonicalName2 = null;
                        "".length();
                        if (1 >= 4) {
                            throw null;
                        }
                    }
                    else {
                        valueByCanonicalName2 = HoverEvent.Action.getValueByCanonicalName(asJsonPrimitive3.getAsString());
                    }
                    final HoverEvent.Action action2 = valueByCanonicalName2;
                    final IChatComponent chatComponent = (IChatComponent)jsonDeserializationContext.deserialize(asJsonObject3.get(Serializer.I[0x20 ^ 0x35]), (Type)IChatComponent.class);
                    if (action2 != null && chatComponent != null && action2.shouldAllowInChat()) {
                        ChatStyle.access$8(chatStyle, new HoverEvent(action2, chatComponent));
                    }
                }
            }
            return chatStyle;
        }
        
        static {
            I();
        }
        
        public Object deserialize(final JsonElement jsonElement, final Type type, final JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
            return this.deserialize(jsonElement, type, jsonDeserializationContext);
        }
        
        public JsonElement serialize(final ChatStyle chatStyle, final Type type, final JsonSerializationContext jsonSerializationContext) {
            if (chatStyle.isEmpty()) {
                return null;
            }
            final JsonObject jsonObject = new JsonObject();
            if (ChatStyle.access$9(chatStyle) != null) {
                jsonObject.addProperty(Serializer.I[0x14 ^ 0x2], ChatStyle.access$9(chatStyle));
            }
            if (ChatStyle.access$10(chatStyle) != null) {
                jsonObject.addProperty(Serializer.I[0x31 ^ 0x26], ChatStyle.access$10(chatStyle));
            }
            if (ChatStyle.access$11(chatStyle) != null) {
                jsonObject.addProperty(Serializer.I[0xD ^ 0x15], ChatStyle.access$11(chatStyle));
            }
            if (ChatStyle.access$12(chatStyle) != null) {
                jsonObject.addProperty(Serializer.I[0x5 ^ 0x1C], ChatStyle.access$12(chatStyle));
            }
            if (ChatStyle.access$13(chatStyle) != null) {
                jsonObject.addProperty(Serializer.I[0x99 ^ 0x83], ChatStyle.access$13(chatStyle));
            }
            if (ChatStyle.access$14(chatStyle) != null) {
                jsonObject.add(Serializer.I[0x5B ^ 0x40], jsonSerializationContext.serialize((Object)ChatStyle.access$14(chatStyle)));
            }
            if (ChatStyle.access$15(chatStyle) != null) {
                jsonObject.add(Serializer.I[0x76 ^ 0x6A], jsonSerializationContext.serialize((Object)ChatStyle.access$15(chatStyle)));
            }
            if (ChatStyle.access$16(chatStyle) != null) {
                final JsonObject jsonObject2 = new JsonObject();
                jsonObject2.addProperty(Serializer.I[0x4A ^ 0x57], ChatStyle.access$16(chatStyle).getAction().getCanonicalName());
                jsonObject2.addProperty(Serializer.I[0x4B ^ 0x55], ChatStyle.access$16(chatStyle).getValue());
                jsonObject.add(Serializer.I[0x12 ^ 0xD], (JsonElement)jsonObject2);
            }
            if (ChatStyle.access$17(chatStyle) != null) {
                final JsonObject jsonObject3 = new JsonObject();
                jsonObject3.addProperty(Serializer.I[0x69 ^ 0x49], ChatStyle.access$17(chatStyle).getAction().getCanonicalName());
                jsonObject3.add(Serializer.I[0x1E ^ 0x3F], jsonSerializationContext.serialize((Object)ChatStyle.access$17(chatStyle).getValue()));
                jsonObject.add(Serializer.I[0x6F ^ 0x4D], (JsonElement)jsonObject3);
            }
            return (JsonElement)jsonObject;
        }
        
        public JsonElement serialize(final Object o, final Type type, final JsonSerializationContext jsonSerializationContext) {
            return this.serialize((ChatStyle)o, type, jsonSerializationContext);
        }
        
        private static void I() {
            (I = new String[0x73 ^ 0x50])["".length()] = I("57'0", "WXKTm");
            Serializer.I[" ".length()] = I("5\u000b$.", "WdHJE");
            Serializer.I["  ".length()] = I("\u0004$\u0018+\u0004\u000e", "mPyGm");
            Serializer.I["   ".length()] = I(" #0\u0003#*", "IWQoJ");
            Serializer.I[0x59 ^ 0x5D] = I("\u001b)&$\u0006\u0002.,$\u0010", "nGBAt");
            Serializer.I[0x36 ^ 0x33] = I("#?\u0011 *:8\u001b <", "VQuEX");
            Serializer.I[0xB1 ^ 0xB7] = I("'\u0002\u0019\u0010\u00041\u0002\u0003\u000b\u0000!\u0011\u0003", "Tvkyo");
            Serializer.I[0x7D ^ 0x7A] = I("&&30=0&)+9 5)", "URAYV");
            Serializer.I[0xB6 ^ 0xBE] = I("\u0007\r\"!\u0003\u000b\u000e01\u0014", "hoDTp");
            Serializer.I[0x71 ^ 0x78] = I("\u0017$\u0014;\u0000\u001b'\u0006+\u0017", "xFrNs");
            Serializer.I[0x9C ^ 0x96] = I("\u0019!\u000b!\u0002", "zNgNp");
            Serializer.I[0x57 ^ 0x5C] = I("\u0019\u001b\u0006\b\u001f", "ztjgm");
            Serializer.I[0x61 ^ 0x6D] = I("\u001f\u001a%-\u001d\u0002\u001d9&", "vtVHo");
            Serializer.I[0x4A ^ 0x47] = I(" \u000b\u0017?9=\f\u000b4", "IedZK");
            Serializer.I[0x38 ^ 0x36] = I("!\u0001>-#\u0007\u001b2 <", "BmWNH");
            Serializer.I[0xAC ^ 0xA3] = I("\u0010?\"\u001a\u00066%.\u0017\u0019", "sSKym");
            Serializer.I[0x2E ^ 0x3E] = I("\u0016(\u0007\u00039\u0019", "wKsjV");
            Serializer.I[0x4A ^ 0x5B] = I("\f(%\u000f0", "zIIzU");
            Serializer.I[0x7F ^ 0x6D] = I("<-\u0018\u0000:\u00114\u000b\u000b<", "TBneH");
            Serializer.I[0x6B ^ 0x78] = I("/6!\u0017#\u0002/2\u001c%", "GYWrQ");
            Serializer.I[0x3B ^ 0x2F] = I(")*%$(&", "HIQMG");
            Serializer.I[0xD0 ^ 0xC5] = I("\u001c .\u0005,", "jABpI");
            Serializer.I[0x4E ^ 0x58] = I("\u0000\u001f*\t", "bpFma");
            Serializer.I[0x24 ^ 0x33] = I("\u0019\u0011\u000f!\u0010\u0013", "penMy");
            Serializer.I[0x6F ^ 0x77] = I("-\r<7\u001c4\n67\n", "XcXRn");
            Serializer.I[0x3F ^ 0x26] = I("\u0016 \u0019\u001b=\u0000 \u0003\u00009\u00103\u0003", "eTkrV");
            Serializer.I[0x69 ^ 0x73] = I("\"\b\"\u001b%.\u000b0\u000b2", "MjDnV");
            Serializer.I[0x60 ^ 0x7B] = I("\u001b\u0015;(9", "xzWGK");
            Serializer.I[0xAE ^ 0xB2] = I("(+\u0016*\b5,\n!", "AEeOz");
            Serializer.I[0xBD ^ 0xA0] = I("\n(\f:8\u0005", "kKxSW");
            Serializer.I[0xA6 ^ 0xB8] = I("\u0010%\u0018\u0003)", "fDtvL");
            Serializer.I[0x8A ^ 0x95] = I(".\u0018\b\u0019:\b\u0002\u0004\u0014%", "MtazQ");
            Serializer.I[0xB1 ^ 0x91] = I("\r7\u0017\u0019\u0003\u0002", "lTcpl");
            Serializer.I[0xB8 ^ 0x99] = I("\"4\"<5", "TUNIP");
            Serializer.I[0x4B ^ 0x69] = I("9\"\u000e\t\u001e\u0014;\u001d\u0002\u0018", "QMxll");
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
                if (1 < -1) {
                    throw null;
                }
            }
            return sb.toString();
        }
    }
}
