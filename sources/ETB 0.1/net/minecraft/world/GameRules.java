package net.minecraft.world;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeMap;
import net.minecraft.nbt.NBTTagCompound;

public class GameRules
{
  private TreeMap theGameRules = new TreeMap();
  
  public GameRules()
  {
    addGameRule("doFireTick", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("mobGriefing", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("keepInventory", "false", ValueType.BOOLEAN_VALUE);
    addGameRule("doMobSpawning", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("doMobLoot", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("doTileDrops", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("commandBlockOutput", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("naturalRegeneration", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("doDaylightCycle", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("logAdminCommands", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("showDeathMessages", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("randomTickSpeed", "3", ValueType.NUMERICAL_VALUE);
    addGameRule("sendCommandFeedback", "true", ValueType.BOOLEAN_VALUE);
    addGameRule("reducedDebugInfo", "false", ValueType.BOOLEAN_VALUE);
  }
  
  public void addGameRule(String key, String value, ValueType type)
  {
    theGameRules.put(key, new Value(value, type));
  }
  
  public void setOrCreateGameRule(String key, String ruleValue)
  {
    Value var3 = (Value)theGameRules.get(key);
    
    if (var3 != null)
    {
      var3.setValue(ruleValue);
    }
    else
    {
      addGameRule(key, ruleValue, ValueType.ANY_VALUE);
    }
  }
  



  public String getGameRuleStringValue(String name)
  {
    Value var2 = (Value)theGameRules.get(name);
    return var2 != null ? var2.getGameRuleStringValue() : "";
  }
  



  public boolean getGameRuleBooleanValue(String name)
  {
    Value var2 = (Value)theGameRules.get(name);
    return var2 != null ? var2.getGameRuleBooleanValue() : false;
  }
  
  public int getInt(String name)
  {
    Value var2 = (Value)theGameRules.get(name);
    return var2 != null ? var2.getInt() : 0;
  }
  



  public NBTTagCompound writeGameRulesToNBT()
  {
    NBTTagCompound var1 = new NBTTagCompound();
    Iterator var2 = theGameRules.keySet().iterator();
    
    while (var2.hasNext())
    {
      String var3 = (String)var2.next();
      Value var4 = (Value)theGameRules.get(var3);
      var1.setString(var3, var4.getGameRuleStringValue());
    }
    
    return var1;
  }
  



  public void readGameRulesFromNBT(NBTTagCompound nbt)
  {
    Set var2 = nbt.getKeySet();
    Iterator var3 = var2.iterator();
    
    while (var3.hasNext())
    {
      String var4 = (String)var3.next();
      String var6 = nbt.getString(var4);
      setOrCreateGameRule(var4, var6);
    }
  }
  



  public String[] getRules()
  {
    return (String[])theGameRules.keySet().toArray(new String[0]);
  }
  



  public boolean hasRule(String name)
  {
    return theGameRules.containsKey(name);
  }
  
  public boolean areSameType(String key, ValueType otherValue)
  {
    Value var3 = (Value)theGameRules.get(key);
    return (var3 != null) && ((var3.getType() == otherValue) || (otherValue == ValueType.ANY_VALUE));
  }
  
  static class Value
  {
    private String valueString;
    private boolean valueBoolean;
    private int valueInteger;
    private double valueDouble;
    private final GameRules.ValueType type;
    private static final String __OBFID = "CL_00000137";
    
    public Value(String value, GameRules.ValueType type)
    {
      this.type = type;
      setValue(value);
    }
    
    public void setValue(String value)
    {
      valueString = value;
      
      if (value != null)
      {
        if (value.equals("false"))
        {
          valueBoolean = false;
          return;
        }
        
        if (value.equals("true"))
        {
          valueBoolean = true;
          return;
        }
      }
      
      valueBoolean = Boolean.parseBoolean(value);
      valueInteger = (valueBoolean ? 1 : 0);
      
      try
      {
        valueInteger = Integer.parseInt(value);
      }
      catch (NumberFormatException localNumberFormatException) {}
      



      try
      {
        valueDouble = Double.parseDouble(value);
      }
      catch (NumberFormatException localNumberFormatException1) {}
    }
    



    public String getGameRuleStringValue()
    {
      return valueString;
    }
    
    public boolean getGameRuleBooleanValue()
    {
      return valueBoolean;
    }
    
    public int getInt()
    {
      return valueInteger;
    }
    
    public GameRules.ValueType getType()
    {
      return type;
    }
  }
  
  public static enum ValueType
  {
    ANY_VALUE("ANY_VALUE", 0, "ANY_VALUE", 0), 
    BOOLEAN_VALUE("BOOLEAN_VALUE", 1, "BOOLEAN_VALUE", 1), 
    NUMERICAL_VALUE("NUMERICAL_VALUE", 2, "NUMERICAL_VALUE", 2);
    private static final ValueType[] $VALUES = { ANY_VALUE, BOOLEAN_VALUE, NUMERICAL_VALUE };
    
    private ValueType(String p_i46394_1_, int p_i46394_2_, String p_i45750_1_, int p_i45750_2_) {}
  }
}
