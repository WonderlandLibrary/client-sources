package net.minecraft.event;

import com.google.common.collect.Maps;
import java.util.Map;

public class ClickEvent
{
  private final Action action;
  private final String value;
  private static final String __OBFID = "CL_00001260";
  
  public ClickEvent(Action p_i45156_1_, String p_i45156_2_)
  {
    action = p_i45156_1_;
    value = p_i45156_2_;
  }
  



  public Action getAction()
  {
    return action;
  }
  




  public String getValue()
  {
    return value;
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (this == p_equals_1_)
    {
      return true;
    }
    if ((p_equals_1_ != null) && (getClass() == p_equals_1_.getClass()))
    {
      ClickEvent var2 = (ClickEvent)p_equals_1_;
      
      if (action != action)
      {
        return false;
      }
      

      if (value != null)
      {
        if (!value.equals(value))
        {
          return false;
        }
      }
      else if (value != null)
      {
        return false;
      }
      
      return true;
    }
    


    return false;
  }
  

  public String toString()
  {
    return "ClickEvent{action=" + action + ", value='" + value + '\'' + '}';
  }
  
  public int hashCode()
  {
    int var1 = action.hashCode();
    var1 = 31 * var1 + (value != null ? value.hashCode() : 0);
    return var1;
  }
  
  public static enum Action
  {
    OPEN_URL("OPEN_URL", 0, "open_url", true), 
    OPEN_FILE("OPEN_FILE", 1, "open_file", false), 
    RUN_COMMAND("RUN_COMMAND", 2, "run_command", true), 
    TWITCH_USER_INFO("TWITCH_USER_INFO", 3, "twitch_user_info", false), 
    SUGGEST_COMMAND("SUGGEST_COMMAND", 4, "suggest_command", true), 
    CHANGE_PAGE("CHANGE_PAGE", 5, "change_page", true);
    
    private static final Map nameMapping;
    private final boolean allowedInChat;
    private final String canonicalName;
    private static final Action[] $VALUES;
    private static final String __OBFID = "CL_00001261";
    
    private Action(String p_i45155_1_, int p_i45155_2_, String p_i45155_3_, boolean p_i45155_4_)
    {
      canonicalName = p_i45155_3_;
      allowedInChat = p_i45155_4_;
    }
    
    public boolean shouldAllowInChat()
    {
      return allowedInChat;
    }
    
    public String getCanonicalName()
    {
      return canonicalName;
    }
    
    public static Action getValueByCanonicalName(String p_150672_0_)
    {
      return (Action)nameMapping.get(p_150672_0_);
    }
    
    static
    {
      nameMapping = Maps.newHashMap();
      


      $VALUES = new Action[] { OPEN_URL, OPEN_FILE, RUN_COMMAND, TWITCH_USER_INFO, SUGGEST_COMMAND, CHANGE_PAGE };
      























      Action[] var0 = values();
      int var1 = var0.length;
      
      for (int var2 = 0; var2 < var1; var2++)
      {
        Action var3 = var0[var2];
        nameMapping.put(var3.getCanonicalName(), var3);
      }
    }
  }
}
