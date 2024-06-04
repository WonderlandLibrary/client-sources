package net.minecraft.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import java.lang.reflect.Type;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import net.minecraft.event.HoverEvent;
import net.minecraft.event.HoverEvent.Action;






public class ChatStyle
{
  private ChatStyle parentStyle;
  private EnumChatFormatting color;
  private Boolean bold;
  private Boolean italic;
  private Boolean underlined;
  private Boolean strikethrough;
  private Boolean obfuscated;
  private ClickEvent chatClickEvent;
  private HoverEvent chatHoverEvent;
  private String insertion;
  private static final ChatStyle rootStyle = new ChatStyle()
  {
    private static final String __OBFID = "CL_00001267";
    
    public EnumChatFormatting getColor() {
      return null;
    }
    
    public boolean getBold() {
      return false;
    }
    
    public boolean getItalic() {
      return false;
    }
    
    public boolean getStrikethrough() {
      return false;
    }
    
    public boolean getUnderlined() {
      return false;
    }
    
    public boolean getObfuscated() {
      return false;
    }
    
    public ClickEvent getChatClickEvent() {
      return null;
    }
    
    public HoverEvent getChatHoverEvent() {
      return null;
    }
    
    public String getInsertion() {
      return null;
    }
    
    public ChatStyle setColor(EnumChatFormatting color) {
      throw new UnsupportedOperationException();
    }
    
    public ChatStyle setBold(Boolean p_150227_1_) {
      throw new UnsupportedOperationException();
    }
    
    public ChatStyle setItalic(Boolean italic) {
      throw new UnsupportedOperationException();
    }
    
    public ChatStyle setStrikethrough(Boolean strikethrough) {
      throw new UnsupportedOperationException();
    }
    
    public ChatStyle setUnderlined(Boolean underlined) {
      throw new UnsupportedOperationException();
    }
    
    public ChatStyle setObfuscated(Boolean obfuscated) {
      throw new UnsupportedOperationException();
    }
    
    public ChatStyle setChatClickEvent(ClickEvent event) {
      throw new UnsupportedOperationException();
    }
    
    public ChatStyle setChatHoverEvent(HoverEvent event) {
      throw new UnsupportedOperationException();
    }
    
    public ChatStyle setParentStyle(ChatStyle parent) {
      throw new UnsupportedOperationException();
    }
    
    public String toString() {
      return "Style.ROOT";
    }
    
    public ChatStyle createShallowCopy() {
      return this;
    }
    
    public ChatStyle createDeepCopy() {
      return this;
    }
    
    public String getFormattingCode() {
      return "";
    }
  };
  
  private static final String __OBFID = "CL_00001266";
  
  public ChatStyle() {}
  
  public EnumChatFormatting getColor()
  {
    return color == null ? getParent().getColor() : color;
  }
  



  public boolean getBold()
  {
    return bold == null ? getParent().getBold() : bold.booleanValue();
  }
  



  public boolean getItalic()
  {
    return italic == null ? getParent().getItalic() : italic.booleanValue();
  }
  



  public boolean getStrikethrough()
  {
    return strikethrough == null ? getParent().getStrikethrough() : strikethrough.booleanValue();
  }
  



  public boolean getUnderlined()
  {
    return underlined == null ? getParent().getUnderlined() : underlined.booleanValue();
  }
  



  public boolean getObfuscated()
  {
    return obfuscated == null ? getParent().getObfuscated() : obfuscated.booleanValue();
  }
  



  public boolean isEmpty()
  {
    return (bold == null) && (italic == null) && (strikethrough == null) && (underlined == null) && (obfuscated == null) && (color == null) && (chatClickEvent == null) && (chatHoverEvent == null);
  }
  



  public ClickEvent getChatClickEvent()
  {
    return chatClickEvent == null ? getParent().getChatClickEvent() : chatClickEvent;
  }
  



  public HoverEvent getChatHoverEvent()
  {
    return chatHoverEvent == null ? getParent().getChatHoverEvent() : chatHoverEvent;
  }
  



  public String getInsertion()
  {
    return insertion == null ? getParent().getInsertion() : insertion;
  }
  




  public ChatStyle setColor(EnumChatFormatting color)
  {
    this.color = color;
    return this;
  }
  




  public ChatStyle setBold(Boolean p_150227_1_)
  {
    bold = p_150227_1_;
    return this;
  }
  




  public ChatStyle setItalic(Boolean italic)
  {
    this.italic = italic;
    return this;
  }
  




  public ChatStyle setStrikethrough(Boolean strikethrough)
  {
    this.strikethrough = strikethrough;
    return this;
  }
  




  public ChatStyle setUnderlined(Boolean underlined)
  {
    this.underlined = underlined;
    return this;
  }
  




  public ChatStyle setObfuscated(Boolean obfuscated)
  {
    this.obfuscated = obfuscated;
    return this;
  }
  



  public ChatStyle setChatClickEvent(ClickEvent event)
  {
    chatClickEvent = event;
    return this;
  }
  



  public ChatStyle setChatHoverEvent(HoverEvent event)
  {
    chatHoverEvent = event;
    return this;
  }
  



  public ChatStyle setInsertion(String insertion)
  {
    this.insertion = insertion;
    return this;
  }
  




  public ChatStyle setParentStyle(ChatStyle parent)
  {
    parentStyle = parent;
    return this;
  }
  



  public String getFormattingCode()
  {
    if (isEmpty())
    {
      return parentStyle != null ? parentStyle.getFormattingCode() : "";
    }
    

    StringBuilder var1 = new StringBuilder();
    
    if (getColor() != null)
    {
      var1.append(getColor());
    }
    
    if (getBold())
    {
      var1.append(EnumChatFormatting.BOLD);
    }
    
    if (getItalic())
    {
      var1.append(EnumChatFormatting.ITALIC);
    }
    
    if (getUnderlined())
    {
      var1.append(EnumChatFormatting.UNDERLINE);
    }
    
    if (getObfuscated())
    {
      var1.append(EnumChatFormatting.OBFUSCATED);
    }
    
    if (getStrikethrough())
    {
      var1.append(EnumChatFormatting.STRIKETHROUGH);
    }
    
    return var1.toString();
  }
  




  private ChatStyle getParent()
  {
    return parentStyle == null ? rootStyle : parentStyle;
  }
  
  public String toString()
  {
    return "Style{hasParent=" + (parentStyle != null) + ", color=" + color + ", bold=" + bold + ", italic=" + italic + ", underlined=" + underlined + ", obfuscated=" + obfuscated + ", clickEvent=" + getChatClickEvent() + ", hoverEvent=" + getChatHoverEvent() + ", insertion=" + getInsertion() + '}';
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (this == p_equals_1_)
    {
      return true;
    }
    if (!(p_equals_1_ instanceof ChatStyle))
    {
      return false;
    }
    

    ChatStyle var2 = (ChatStyle)p_equals_1_;
    

    if ((getBold() == var2.getBold()) && (getColor() == var2.getColor()) && (getItalic() == var2.getItalic()) && (getObfuscated() == var2.getObfuscated()) && (getStrikethrough() == var2.getStrikethrough()) && (getUnderlined() == var2.getUnderlined()))
    {


      if (getChatClickEvent() != null ? 
      
        getChatClickEvent().equals(var2.getChatClickEvent()) : 
        



        var2.getChatClickEvent() == null)
      {



        if (getChatHoverEvent() != null ? 
        
          getChatHoverEvent().equals(var2.getChatHoverEvent()) : 
          



          var2.getChatHoverEvent() == null)
        {



          if (getInsertion() != null ? 
          
            getInsertion().equals(var2.getInsertion()) : 
            



            var2.getInsertion() == null)
          {



            boolean var10000 = true;
            return var10000;
          } }
      }
    }
    boolean var10000 = false;
    return var10000;
  }
  

  public int hashCode()
  {
    int var1 = color.hashCode();
    var1 = 31 * var1 + bold.hashCode();
    var1 = 31 * var1 + italic.hashCode();
    var1 = 31 * var1 + underlined.hashCode();
    var1 = 31 * var1 + strikethrough.hashCode();
    var1 = 31 * var1 + obfuscated.hashCode();
    var1 = 31 * var1 + chatClickEvent.hashCode();
    var1 = 31 * var1 + chatHoverEvent.hashCode();
    var1 = 31 * var1 + insertion.hashCode();
    return var1;
  }
  





  public ChatStyle createShallowCopy()
  {
    ChatStyle var1 = new ChatStyle();
    bold = bold;
    italic = italic;
    strikethrough = strikethrough;
    underlined = underlined;
    obfuscated = obfuscated;
    color = color;
    chatClickEvent = chatClickEvent;
    chatHoverEvent = chatHoverEvent;
    parentStyle = parentStyle;
    insertion = insertion;
    return var1;
  }
  




  public ChatStyle createDeepCopy()
  {
    ChatStyle var1 = new ChatStyle();
    var1.setBold(Boolean.valueOf(getBold()));
    var1.setItalic(Boolean.valueOf(getItalic()));
    var1.setStrikethrough(Boolean.valueOf(getStrikethrough()));
    var1.setUnderlined(Boolean.valueOf(getUnderlined()));
    var1.setObfuscated(Boolean.valueOf(getObfuscated()));
    var1.setColor(getColor());
    var1.setChatClickEvent(getChatClickEvent());
    var1.setChatHoverEvent(getChatHoverEvent());
    var1.setInsertion(getInsertion());
    return var1;
  }
  
  public static class Serializer implements JsonDeserializer, JsonSerializer {
    private static final String __OBFID = "CL_00001268";
    
    public Serializer() {}
    
    public ChatStyle deserialize(JsonElement p_deserialize_1_, Type p_deserialize_2_, JsonDeserializationContext p_deserialize_3_) {
      if (p_deserialize_1_.isJsonObject())
      {
        ChatStyle var4 = new ChatStyle();
        JsonObject var5 = p_deserialize_1_.getAsJsonObject();
        
        if (var5 == null)
        {
          return null;
        }
        

        if (var5.has("bold"))
        {
          bold = Boolean.valueOf(var5.get("bold").getAsBoolean());
        }
        
        if (var5.has("italic"))
        {
          italic = Boolean.valueOf(var5.get("italic").getAsBoolean());
        }
        
        if (var5.has("underlined"))
        {
          underlined = Boolean.valueOf(var5.get("underlined").getAsBoolean());
        }
        
        if (var5.has("strikethrough"))
        {
          strikethrough = Boolean.valueOf(var5.get("strikethrough").getAsBoolean());
        }
        
        if (var5.has("obfuscated"))
        {
          obfuscated = Boolean.valueOf(var5.get("obfuscated").getAsBoolean());
        }
        
        if (var5.has("color"))
        {
          color = ((EnumChatFormatting)p_deserialize_3_.deserialize(var5.get("color"), EnumChatFormatting.class));
        }
        
        if (var5.has("insertion"))
        {
          insertion = var5.get("insertion").getAsString();
        }
        



        if (var5.has("clickEvent"))
        {
          JsonObject var6 = var5.getAsJsonObject("clickEvent");
          
          if (var6 != null)
          {
            JsonPrimitive var7 = var6.getAsJsonPrimitive("action");
            ClickEvent.Action var8 = var7 == null ? null : ClickEvent.Action.getValueByCanonicalName(var7.getAsString());
            JsonPrimitive var9 = var6.getAsJsonPrimitive("value");
            String var10 = var9 == null ? null : var9.getAsString();
            
            if ((var8 != null) && (var10 != null) && (var8.shouldAllowInChat()))
            {
              chatClickEvent = new ClickEvent(var8, var10);
            }
          }
        }
        
        if (var5.has("hoverEvent"))
        {
          JsonObject var6 = var5.getAsJsonObject("hoverEvent");
          
          if (var6 != null)
          {
            JsonPrimitive var7 = var6.getAsJsonPrimitive("action");
            HoverEvent.Action var11 = var7 == null ? null : HoverEvent.Action.getValueByCanonicalName(var7.getAsString());
            IChatComponent var12 = (IChatComponent)p_deserialize_3_.deserialize(var6.get("value"), IChatComponent.class);
            
            if ((var11 != null) && (var12 != null) && (var11.shouldAllowInChat()))
            {
              chatHoverEvent = new HoverEvent(var11, var12);
            }
          }
        }
        
        return var4;
      }
      


      return null;
    }
    

    public JsonElement serialize(ChatStyle p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
    {
      if (p_serialize_1_.isEmpty())
      {
        return null;
      }
      

      JsonObject var4 = new JsonObject();
      
      if (bold != null)
      {
        var4.addProperty("bold", bold);
      }
      
      if (italic != null)
      {
        var4.addProperty("italic", italic);
      }
      
      if (underlined != null)
      {
        var4.addProperty("underlined", underlined);
      }
      
      if (strikethrough != null)
      {
        var4.addProperty("strikethrough", strikethrough);
      }
      
      if (obfuscated != null)
      {
        var4.addProperty("obfuscated", obfuscated);
      }
      
      if (color != null)
      {
        var4.add("color", p_serialize_3_.serialize(color));
      }
      
      if (insertion != null)
      {
        var4.add("insertion", p_serialize_3_.serialize(insertion));
      }
      


      if (chatClickEvent != null)
      {
        JsonObject var5 = new JsonObject();
        var5.addProperty("action", chatClickEvent.getAction().getCanonicalName());
        var5.addProperty("value", chatClickEvent.getValue());
        var4.add("clickEvent", var5);
      }
      
      if (chatHoverEvent != null)
      {
        JsonObject var5 = new JsonObject();
        var5.addProperty("action", chatHoverEvent.getAction().getCanonicalName());
        var5.add("value", p_serialize_3_.serialize(chatHoverEvent.getValue()));
        var4.add("hoverEvent", var5);
      }
      
      return var4;
    }
    

    public JsonElement serialize(Object p_serialize_1_, Type p_serialize_2_, JsonSerializationContext p_serialize_3_)
    {
      return serialize((ChatStyle)p_serialize_1_, p_serialize_2_, p_serialize_3_);
    }
  }
}
