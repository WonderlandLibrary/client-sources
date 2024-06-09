package HORIZON-6-0-SKIDPROTECTION;

import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonObject;
import com.google.gson.JsonDeserializationContext;
import java.lang.reflect.Type;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonDeserializer;

public class ChatStyle
{
    private ChatStyle HorizonCode_Horizon_È;
    private EnumChatFormatting Â;
    private Boolean Ý;
    private Boolean Ø­áŒŠá;
    private Boolean Âµá€;
    private Boolean Ó;
    private Boolean à;
    private ClickEvent Ø;
    private HoverEvent áŒŠÆ;
    private String áˆºÑ¢Õ;
    private static final ChatStyle ÂµÈ;
    private static final String á = "CL_00001266";
    
    static {
        ÂµÈ = new ChatStyle() {
            private static final String HorizonCode_Horizon_È = "CL_00001267";
            
            @Override
            public EnumChatFormatting HorizonCode_Horizon_È() {
                return null;
            }
            
            @Override
            public boolean Â() {
                return false;
            }
            
            @Override
            public boolean Ý() {
                return false;
            }
            
            @Override
            public boolean Ø­áŒŠá() {
                return false;
            }
            
            @Override
            public boolean Âµá€() {
                return false;
            }
            
            @Override
            public boolean Ó() {
                return false;
            }
            
            @Override
            public ClickEvent Ø() {
                return null;
            }
            
            @Override
            public HoverEvent áŒŠÆ() {
                return null;
            }
            
            @Override
            public String áˆºÑ¢Õ() {
                return null;
            }
            
            @Override
            public ChatStyle HorizonCode_Horizon_È(final EnumChatFormatting color) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle HorizonCode_Horizon_È(final Boolean p_150227_1_) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle Â(final Boolean italic) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle Ý(final Boolean strikethrough) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle Ø­áŒŠá(final Boolean underlined) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle Âµá€(final Boolean obfuscated) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle HorizonCode_Horizon_È(final ClickEvent event) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle HorizonCode_Horizon_È(final HoverEvent event) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public ChatStyle HorizonCode_Horizon_È(final ChatStyle parent) {
                throw new UnsupportedOperationException();
            }
            
            @Override
            public String toString() {
                return "Style.ROOT";
            }
            
            @Override
            public ChatStyle á() {
                return this;
            }
            
            @Override
            public ChatStyle ˆÏ­() {
                return this;
            }
            
            @Override
            public String ÂµÈ() {
                return "";
            }
        };
    }
    
    public EnumChatFormatting HorizonCode_Horizon_È() {
        return (this.Â == null) ? this.£á().HorizonCode_Horizon_È() : this.Â;
    }
    
    public boolean Â() {
        return (this.Ý == null) ? this.£á().Â() : this.Ý;
    }
    
    public boolean Ý() {
        return (this.Ø­áŒŠá == null) ? this.£á().Ý() : this.Ø­áŒŠá;
    }
    
    public boolean Ø­áŒŠá() {
        return (this.Ó == null) ? this.£á().Ø­áŒŠá() : this.Ó;
    }
    
    public boolean Âµá€() {
        return (this.Âµá€ == null) ? this.£á().Âµá€() : this.Âµá€;
    }
    
    public boolean Ó() {
        return (this.à == null) ? this.£á().Ó() : this.à;
    }
    
    public boolean à() {
        return this.Ý == null && this.Ø­áŒŠá == null && this.Ó == null && this.Âµá€ == null && this.à == null && this.Â == null && this.Ø == null && this.áŒŠÆ == null;
    }
    
    public ClickEvent Ø() {
        return (this.Ø == null) ? this.£á().Ø() : this.Ø;
    }
    
    public HoverEvent áŒŠÆ() {
        return (this.áŒŠÆ == null) ? this.£á().áŒŠÆ() : this.áŒŠÆ;
    }
    
    public String áˆºÑ¢Õ() {
        return (this.áˆºÑ¢Õ == null) ? this.£á().áˆºÑ¢Õ() : this.áˆºÑ¢Õ;
    }
    
    public ChatStyle HorizonCode_Horizon_È(final EnumChatFormatting color) {
        this.Â = color;
        return this;
    }
    
    public ChatStyle HorizonCode_Horizon_È(final Boolean p_150227_1_) {
        this.Ý = p_150227_1_;
        return this;
    }
    
    public ChatStyle Â(final Boolean italic) {
        this.Ø­áŒŠá = italic;
        return this;
    }
    
    public ChatStyle Ý(final Boolean strikethrough) {
        this.Ó = strikethrough;
        return this;
    }
    
    public ChatStyle Ø­áŒŠá(final Boolean underlined) {
        this.Âµá€ = underlined;
        return this;
    }
    
    public ChatStyle Âµá€(final Boolean obfuscated) {
        this.à = obfuscated;
        return this;
    }
    
    public ChatStyle HorizonCode_Horizon_È(final ClickEvent event) {
        this.Ø = event;
        return this;
    }
    
    public ChatStyle HorizonCode_Horizon_È(final HoverEvent event) {
        this.áŒŠÆ = event;
        return this;
    }
    
    public ChatStyle HorizonCode_Horizon_È(final String insertion) {
        this.áˆºÑ¢Õ = insertion;
        return this;
    }
    
    public ChatStyle HorizonCode_Horizon_È(final ChatStyle parent) {
        this.HorizonCode_Horizon_È = parent;
        return this;
    }
    
    public String ÂµÈ() {
        if (this.à()) {
            return (this.HorizonCode_Horizon_È != null) ? this.HorizonCode_Horizon_È.ÂµÈ() : "";
        }
        final StringBuilder var1 = new StringBuilder();
        if (this.HorizonCode_Horizon_È() != null) {
            var1.append(this.HorizonCode_Horizon_È());
        }
        if (this.Â()) {
            var1.append(EnumChatFormatting.ˆà);
        }
        if (this.Ý()) {
            var1.append(EnumChatFormatting.µÕ);
        }
        if (this.Âµá€()) {
            var1.append(EnumChatFormatting.Ø­à);
        }
        if (this.Ó()) {
            var1.append(EnumChatFormatting.µà);
        }
        if (this.Ø­áŒŠá()) {
            var1.append(EnumChatFormatting.¥Æ);
        }
        return var1.toString();
    }
    
    private ChatStyle £á() {
        return (this.HorizonCode_Horizon_È == null) ? ChatStyle.ÂµÈ : this.HorizonCode_Horizon_È;
    }
    
    @Override
    public String toString() {
        return "Style{hasParent=" + (this.HorizonCode_Horizon_È != null) + ", color=" + this.Â + ", bold=" + this.Ý + ", italic=" + this.Ø­áŒŠá + ", underlined=" + this.Âµá€ + ", obfuscated=" + this.à + ", clickEvent=" + this.Ø() + ", hoverEvent=" + this.áŒŠÆ() + ", insertion=" + this.áˆºÑ¢Õ() + '}';
    }
    
    @Override
    public boolean equals(final Object p_equals_1_) {
        if (this == p_equals_1_) {
            return true;
        }
        if (!(p_equals_1_ instanceof ChatStyle)) {
            return false;
        }
        final ChatStyle var2 = (ChatStyle)p_equals_1_;
        if (this.Â() == var2.Â() && this.HorizonCode_Horizon_È() == var2.HorizonCode_Horizon_È() && this.Ý() == var2.Ý() && this.Ó() == var2.Ó() && this.Ø­áŒŠá() == var2.Ø­áŒŠá() && this.Âµá€() == var2.Âµá€()) {
            if (this.Ø() != null) {
                if (!this.Ø().equals(var2.Ø())) {
                    return false;
                }
            }
            else if (var2.Ø() != null) {
                return false;
            }
            if (this.áŒŠÆ() != null) {
                if (!this.áŒŠÆ().equals(var2.áŒŠÆ())) {
                    return false;
                }
            }
            else if (var2.áŒŠÆ() != null) {
                return false;
            }
            if (this.áˆºÑ¢Õ() != null) {
                if (!this.áˆºÑ¢Õ().equals(var2.áˆºÑ¢Õ())) {
                    return false;
                }
            }
            else if (var2.áˆºÑ¢Õ() != null) {
                return false;
            }
            final boolean var3 = true;
            return var3;
        }
        final boolean var3 = false;
        return var3;
    }
    
    @Override
    public int hashCode() {
        int var1 = this.Â.hashCode();
        var1 = 31 * var1 + this.Ý.hashCode();
        var1 = 31 * var1 + this.Ø­áŒŠá.hashCode();
        var1 = 31 * var1 + this.Âµá€.hashCode();
        var1 = 31 * var1 + this.Ó.hashCode();
        var1 = 31 * var1 + this.à.hashCode();
        var1 = 31 * var1 + this.Ø.hashCode();
        var1 = 31 * var1 + this.áŒŠÆ.hashCode();
        var1 = 31 * var1 + this.áˆºÑ¢Õ.hashCode();
        return var1;
    }
    
    public ChatStyle á() {
        final ChatStyle var1 = new ChatStyle();
        var1.Ý = this.Ý;
        var1.Ø­áŒŠá = this.Ø­áŒŠá;
        var1.Ó = this.Ó;
        var1.Âµá€ = this.Âµá€;
        var1.à = this.à;
        var1.Â = this.Â;
        var1.Ø = this.Ø;
        var1.áŒŠÆ = this.áŒŠÆ;
        var1.HorizonCode_Horizon_È = this.HorizonCode_Horizon_È;
        var1.áˆºÑ¢Õ = this.áˆºÑ¢Õ;
        return var1;
    }
    
    public ChatStyle ˆÏ­() {
        final ChatStyle var1 = new ChatStyle();
        var1.HorizonCode_Horizon_È(Boolean.valueOf(this.Â()));
        var1.Â(Boolean.valueOf(this.Ý()));
        var1.Ý(Boolean.valueOf(this.Ø­áŒŠá()));
        var1.Ø­áŒŠá(Boolean.valueOf(this.Âµá€()));
        var1.Âµá€(Boolean.valueOf(this.Ó()));
        var1.HorizonCode_Horizon_È(this.HorizonCode_Horizon_È());
        var1.HorizonCode_Horizon_È(this.Ø());
        var1.HorizonCode_Horizon_È(this.áŒŠÆ());
        var1.HorizonCode_Horizon_È(this.áˆºÑ¢Õ());
        return var1;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final ChatStyle chatStyle, final Boolean ý) {
        chatStyle.Ý = ý;
    }
    
    static /* synthetic */ void Â(final ChatStyle chatStyle, final Boolean ø­áŒŠá) {
        chatStyle.Ø­áŒŠá = ø­áŒŠá;
    }
    
    static /* synthetic */ void Ý(final ChatStyle chatStyle, final Boolean âµá€) {
        chatStyle.Âµá€ = âµá€;
    }
    
    static /* synthetic */ void Ø­áŒŠá(final ChatStyle chatStyle, final Boolean ó) {
        chatStyle.Ó = ó;
    }
    
    static /* synthetic */ void Âµá€(final ChatStyle chatStyle, final Boolean à) {
        chatStyle.à = à;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final ChatStyle chatStyle, final EnumChatFormatting â) {
        chatStyle.Â = â;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final ChatStyle chatStyle, final String áˆºÑ¢Õ) {
        chatStyle.áˆºÑ¢Õ = áˆºÑ¢Õ;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final ChatStyle chatStyle, final ClickEvent ø) {
        chatStyle.Ø = ø;
    }
    
    static /* synthetic */ void HorizonCode_Horizon_È(final ChatStyle chatStyle, final HoverEvent áœšæ) {
        chatStyle.áŒŠÆ = áœšæ;
    }
    
    public static class HorizonCode_Horizon_È implements JsonDeserializer, JsonSerializer
    {
        private static final String HorizonCode_Horizon_È = "CL_00001268";
        
        public ChatStyle HorizonCode_Horizon_È(final JsonElement p_deserialize_1_, final Type p_deserialize_2_, final JsonDeserializationContext p_deserialize_3_) {
            if (!p_deserialize_1_.isJsonObject()) {
                return null;
            }
            final ChatStyle var4 = new ChatStyle();
            final JsonObject var5 = p_deserialize_1_.getAsJsonObject();
            if (var5 == null) {
                return null;
            }
            if (var5.has("bold")) {
                ChatStyle.HorizonCode_Horizon_È(var4, Boolean.valueOf(var5.get("bold").getAsBoolean()));
            }
            if (var5.has("italic")) {
                ChatStyle.Â(var4, var5.get("italic").getAsBoolean());
            }
            if (var5.has("underlined")) {
                ChatStyle.Ý(var4, var5.get("underlined").getAsBoolean());
            }
            if (var5.has("strikethrough")) {
                ChatStyle.Ø­áŒŠá(var4, var5.get("strikethrough").getAsBoolean());
            }
            if (var5.has("obfuscated")) {
                ChatStyle.Âµá€(var4, var5.get("obfuscated").getAsBoolean());
            }
            if (var5.has("color")) {
                ChatStyle.HorizonCode_Horizon_È(var4, (EnumChatFormatting)p_deserialize_3_.deserialize(var5.get("color"), (Type)EnumChatFormatting.class));
            }
            if (var5.has("insertion")) {
                ChatStyle.HorizonCode_Horizon_È(var4, var5.get("insertion").getAsString());
            }
            if (var5.has("clickEvent")) {
                final JsonObject var6 = var5.getAsJsonObject("clickEvent");
                if (var6 != null) {
                    final JsonPrimitive var7 = var6.getAsJsonPrimitive("action");
                    final ClickEvent.HorizonCode_Horizon_È var8 = (var7 == null) ? null : ClickEvent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var7.getAsString());
                    final JsonPrimitive var9 = var6.getAsJsonPrimitive("value");
                    final String var10 = (var9 == null) ? null : var9.getAsString();
                    if (var8 != null && var10 != null && var8.HorizonCode_Horizon_È()) {
                        ChatStyle.HorizonCode_Horizon_È(var4, new ClickEvent(var8, var10));
                    }
                }
            }
            if (var5.has("hoverEvent")) {
                final JsonObject var6 = var5.getAsJsonObject("hoverEvent");
                if (var6 != null) {
                    final JsonPrimitive var7 = var6.getAsJsonPrimitive("action");
                    final HoverEvent.HorizonCode_Horizon_È var11 = (var7 == null) ? null : HoverEvent.HorizonCode_Horizon_È.HorizonCode_Horizon_È(var7.getAsString());
                    final IChatComponent var12 = (IChatComponent)p_deserialize_3_.deserialize(var6.get("value"), (Type)IChatComponent.class);
                    if (var11 != null && var12 != null && var11.HorizonCode_Horizon_È()) {
                        ChatStyle.HorizonCode_Horizon_È(var4, new HoverEvent(var11, var12));
                    }
                }
            }
            return var4;
        }
        
        public JsonElement HorizonCode_Horizon_È(final ChatStyle p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            if (p_serialize_1_.à()) {
                return null;
            }
            final JsonObject var4 = new JsonObject();
            if (p_serialize_1_.Ý != null) {
                var4.addProperty("bold", p_serialize_1_.Ý);
            }
            if (p_serialize_1_.Ø­áŒŠá != null) {
                var4.addProperty("italic", p_serialize_1_.Ø­áŒŠá);
            }
            if (p_serialize_1_.Âµá€ != null) {
                var4.addProperty("underlined", p_serialize_1_.Âµá€);
            }
            if (p_serialize_1_.Ó != null) {
                var4.addProperty("strikethrough", p_serialize_1_.Ó);
            }
            if (p_serialize_1_.à != null) {
                var4.addProperty("obfuscated", p_serialize_1_.à);
            }
            if (p_serialize_1_.Â != null) {
                var4.add("color", p_serialize_3_.serialize((Object)p_serialize_1_.Â));
            }
            if (p_serialize_1_.áˆºÑ¢Õ != null) {
                var4.add("insertion", p_serialize_3_.serialize((Object)p_serialize_1_.áˆºÑ¢Õ));
            }
            if (p_serialize_1_.Ø != null) {
                final JsonObject var5 = new JsonObject();
                var5.addProperty("action", p_serialize_1_.Ø.HorizonCode_Horizon_È().Â());
                var5.addProperty("value", p_serialize_1_.Ø.Â());
                var4.add("clickEvent", (JsonElement)var5);
            }
            if (p_serialize_1_.áŒŠÆ != null) {
                final JsonObject var5 = new JsonObject();
                var5.addProperty("action", p_serialize_1_.áŒŠÆ.HorizonCode_Horizon_È().Â());
                var5.add("value", p_serialize_3_.serialize((Object)p_serialize_1_.áŒŠÆ.Â()));
                var4.add("hoverEvent", (JsonElement)var5);
            }
            return (JsonElement)var4;
        }
        
        public JsonElement serialize(final Object p_serialize_1_, final Type p_serialize_2_, final JsonSerializationContext p_serialize_3_) {
            return this.HorizonCode_Horizon_È((ChatStyle)p_serialize_1_, p_serialize_2_, p_serialize_3_);
        }
    }
}
