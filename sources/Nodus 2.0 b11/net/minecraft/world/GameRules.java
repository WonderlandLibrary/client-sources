/*   1:    */ package net.minecraft.world;
/*   2:    */ 
/*   3:    */ import java.util.Iterator;
/*   4:    */ import java.util.Set;
/*   5:    */ import java.util.TreeMap;
/*   6:    */ import net.minecraft.nbt.NBTTagCompound;
/*   7:    */ 
/*   8:    */ public class GameRules
/*   9:    */ {
/*  10: 10 */   private TreeMap theGameRules = new TreeMap();
/*  11:    */   private static final String __OBFID = "CL_00000136";
/*  12:    */   
/*  13:    */   public GameRules()
/*  14:    */   {
/*  15: 15 */     addGameRule("doFireTick", "true");
/*  16: 16 */     addGameRule("mobGriefing", "true");
/*  17: 17 */     addGameRule("keepInventory", "false");
/*  18: 18 */     addGameRule("doMobSpawning", "true");
/*  19: 19 */     addGameRule("doMobLoot", "true");
/*  20: 20 */     addGameRule("doTileDrops", "true");
/*  21: 21 */     addGameRule("commandBlockOutput", "true");
/*  22: 22 */     addGameRule("naturalRegeneration", "true");
/*  23: 23 */     addGameRule("doDaylightCycle", "true");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public void addGameRule(String par1Str, String par2Str)
/*  27:    */   {
/*  28: 31 */     this.theGameRules.put(par1Str, new Value(par2Str));
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void setOrCreateGameRule(String par1Str, String par2Str)
/*  32:    */   {
/*  33: 36 */     Value var3 = (Value)this.theGameRules.get(par1Str);
/*  34: 38 */     if (var3 != null) {
/*  35: 40 */       var3.setValue(par2Str);
/*  36:    */     } else {
/*  37: 44 */       addGameRule(par1Str, par2Str);
/*  38:    */     }
/*  39:    */   }
/*  40:    */   
/*  41:    */   public String getGameRuleStringValue(String par1Str)
/*  42:    */   {
/*  43: 53 */     Value var2 = (Value)this.theGameRules.get(par1Str);
/*  44: 54 */     return var2 != null ? var2.getGameRuleStringValue() : "";
/*  45:    */   }
/*  46:    */   
/*  47:    */   public boolean getGameRuleBooleanValue(String par1Str)
/*  48:    */   {
/*  49: 62 */     Value var2 = (Value)this.theGameRules.get(par1Str);
/*  50: 63 */     return var2 != null ? var2.getGameRuleBooleanValue() : false;
/*  51:    */   }
/*  52:    */   
/*  53:    */   public NBTTagCompound writeGameRulesToNBT()
/*  54:    */   {
/*  55: 71 */     NBTTagCompound var1 = new NBTTagCompound();
/*  56: 72 */     Iterator var2 = this.theGameRules.keySet().iterator();
/*  57: 74 */     while (var2.hasNext())
/*  58:    */     {
/*  59: 76 */       String var3 = (String)var2.next();
/*  60: 77 */       Value var4 = (Value)this.theGameRules.get(var3);
/*  61: 78 */       var1.setString(var3, var4.getGameRuleStringValue());
/*  62:    */     }
/*  63: 81 */     return var1;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void readGameRulesFromNBT(NBTTagCompound par1NBTTagCompound)
/*  67:    */   {
/*  68: 89 */     Set var2 = par1NBTTagCompound.func_150296_c();
/*  69: 90 */     Iterator var3 = var2.iterator();
/*  70: 92 */     while (var3.hasNext())
/*  71:    */     {
/*  72: 94 */       String var4 = (String)var3.next();
/*  73: 95 */       String var6 = par1NBTTagCompound.getString(var4);
/*  74: 96 */       setOrCreateGameRule(var4, var6);
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public String[] getRules()
/*  79:    */   {
/*  80:105 */     return (String[])this.theGameRules.keySet().toArray(new String[0]);
/*  81:    */   }
/*  82:    */   
/*  83:    */   public boolean hasRule(String par1Str)
/*  84:    */   {
/*  85:113 */     return this.theGameRules.containsKey(par1Str);
/*  86:    */   }
/*  87:    */   
/*  88:    */   static class Value
/*  89:    */   {
/*  90:    */     private String valueString;
/*  91:    */     private boolean valueBoolean;
/*  92:    */     private int valueInteger;
/*  93:    */     private double valueDouble;
/*  94:    */     private static final String __OBFID = "CL_00000137";
/*  95:    */     
/*  96:    */     public Value(String par1Str)
/*  97:    */     {
/*  98:126 */       setValue(par1Str);
/*  99:    */     }
/* 100:    */     
/* 101:    */     public void setValue(String par1Str)
/* 102:    */     {
/* 103:131 */       this.valueString = par1Str;
/* 104:133 */       if (par1Str != null)
/* 105:    */       {
/* 106:135 */         if (par1Str.equals("false"))
/* 107:    */         {
/* 108:137 */           this.valueBoolean = false;
/* 109:138 */           return;
/* 110:    */         }
/* 111:141 */         if (par1Str.equals("true"))
/* 112:    */         {
/* 113:143 */           this.valueBoolean = true;
/* 114:144 */           return;
/* 115:    */         }
/* 116:    */       }
/* 117:148 */       this.valueBoolean = Boolean.parseBoolean(par1Str);
/* 118:    */       try
/* 119:    */       {
/* 120:152 */         this.valueInteger = Integer.parseInt(par1Str);
/* 121:    */       }
/* 122:    */       catch (NumberFormatException localNumberFormatException) {}
/* 123:    */       try
/* 124:    */       {
/* 125:161 */         this.valueDouble = Double.parseDouble(par1Str);
/* 126:    */       }
/* 127:    */       catch (NumberFormatException localNumberFormatException1) {}
/* 128:    */     }
/* 129:    */     
/* 130:    */     public String getGameRuleStringValue()
/* 131:    */     {
/* 132:171 */       return this.valueString;
/* 133:    */     }
/* 134:    */     
/* 135:    */     public boolean getGameRuleBooleanValue()
/* 136:    */     {
/* 137:176 */       return this.valueBoolean;
/* 138:    */     }
/* 139:    */   }
/* 140:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.world.GameRules
 * JD-Core Version:    0.7.0.1
 */