/*   1:    */ package net.minecraft.stats;
/*   2:    */ 
/*   3:    */ import java.text.DecimalFormat;
/*   4:    */ import java.text.NumberFormat;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Locale;
/*   7:    */ import java.util.Map;
/*   8:    */ import net.minecraft.event.HoverEvent;
/*   9:    */ import net.minecraft.event.HoverEvent.Action;
/*  10:    */ import net.minecraft.scoreboard.IScoreObjectiveCriteria;
/*  11:    */ import net.minecraft.util.ChatComponentText;
/*  12:    */ import net.minecraft.util.ChatStyle;
/*  13:    */ import net.minecraft.util.EnumChatFormatting;
/*  14:    */ import net.minecraft.util.IChatComponent;
/*  15:    */ 
/*  16:    */ public class StatBase
/*  17:    */ {
/*  18:    */   public final String statId;
/*  19:    */   private final IChatComponent statName;
/*  20:    */   public boolean isIndependent;
/*  21:    */   private final IStatType type;
/*  22:    */   private final IScoreObjectiveCriteria field_150957_c;
/*  23:    */   private Class field_150956_d;
/*  24: 23 */   private static NumberFormat numberFormat = NumberFormat.getIntegerInstance(Locale.US);
/*  25: 24 */   public static IStatType simpleStatType = new IStatType()
/*  26:    */   {
/*  27:    */     private static final String __OBFID = "CL_00001473";
/*  28:    */     
/*  29:    */     public String format(int par1)
/*  30:    */     {
/*  31: 29 */       return StatBase.numberFormat.format(par1);
/*  32:    */     }
/*  33:    */   };
/*  34: 32 */   private static DecimalFormat decimalFormat = new DecimalFormat("########0.00");
/*  35: 33 */   public static IStatType timeStatType = new IStatType()
/*  36:    */   {
/*  37:    */     private static final String __OBFID = "CL_00001474";
/*  38:    */     
/*  39:    */     public String format(int par1)
/*  40:    */     {
/*  41: 38 */       double var2 = par1 / 20.0D;
/*  42: 39 */       double var4 = var2 / 60.0D;
/*  43: 40 */       double var6 = var4 / 60.0D;
/*  44: 41 */       double var8 = var6 / 24.0D;
/*  45: 42 */       double var10 = var8 / 365.0D;
/*  46: 43 */       return var2 + " s";
/*  47:    */     }
/*  48:    */   };
/*  49: 46 */   public static IStatType distanceStatType = new IStatType()
/*  50:    */   {
/*  51:    */     private static final String __OBFID = "CL_00001475";
/*  52:    */     
/*  53:    */     public String format(int par1)
/*  54:    */     {
/*  55: 51 */       double var2 = par1 / 100.0D;
/*  56: 52 */       double var4 = var2 / 1000.0D;
/*  57: 53 */       return par1 + " cm";
/*  58:    */     }
/*  59:    */   };
/*  60: 56 */   public static IStatType field_111202_k = new IStatType()
/*  61:    */   {
/*  62:    */     private static final String __OBFID = "CL_00001476";
/*  63:    */     
/*  64:    */     public String format(int par1)
/*  65:    */     {
/*  66: 61 */       return StatBase.decimalFormat.format(par1 * 0.1D);
/*  67:    */     }
/*  68:    */   };
/*  69:    */   private static final String __OBFID = "CL_00001472";
/*  70:    */   
/*  71:    */   public StatBase(String p_i45307_1_, IChatComponent p_i45307_2_, IStatType p_i45307_3_)
/*  72:    */   {
/*  73: 68 */     this.statId = p_i45307_1_;
/*  74: 69 */     this.statName = p_i45307_2_;
/*  75: 70 */     this.type = p_i45307_3_;
/*  76: 71 */     this.field_150957_c = new ObjectiveStat(this);
/*  77: 72 */     IScoreObjectiveCriteria.field_96643_a.put(this.field_150957_c.func_96636_a(), this.field_150957_c);
/*  78:    */   }
/*  79:    */   
/*  80:    */   public StatBase(String p_i45308_1_, IChatComponent p_i45308_2_)
/*  81:    */   {
/*  82: 77 */     this(p_i45308_1_, p_i45308_2_, simpleStatType);
/*  83:    */   }
/*  84:    */   
/*  85:    */   public StatBase initIndependentStat()
/*  86:    */   {
/*  87: 86 */     this.isIndependent = true;
/*  88: 87 */     return this;
/*  89:    */   }
/*  90:    */   
/*  91:    */   public StatBase registerStat()
/*  92:    */   {
/*  93: 95 */     if (StatList.oneShotStats.containsKey(this.statId)) {
/*  94: 97 */       throw new RuntimeException("Duplicate stat id: \"" + ((StatBase)StatList.oneShotStats.get(this.statId)).statName + "\" and \"" + this.statName + "\" at id " + this.statId);
/*  95:    */     }
/*  96:101 */     StatList.allStats.add(this);
/*  97:102 */     StatList.oneShotStats.put(this.statId, this);
/*  98:103 */     return this;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public boolean isAchievement()
/* 102:    */   {
/* 103:112 */     return false;
/* 104:    */   }
/* 105:    */   
/* 106:    */   public String func_75968_a(int par1)
/* 107:    */   {
/* 108:117 */     return this.type.format(par1);
/* 109:    */   }
/* 110:    */   
/* 111:    */   public IChatComponent func_150951_e()
/* 112:    */   {
/* 113:122 */     IChatComponent var1 = this.statName.createCopy();
/* 114:123 */     var1.getChatStyle().setColor(EnumChatFormatting.GRAY);
/* 115:124 */     var1.getChatStyle().setChatHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_ACHIEVEMENT, new ChatComponentText(this.statId)));
/* 116:125 */     return var1;
/* 117:    */   }
/* 118:    */   
/* 119:    */   public IChatComponent func_150955_j()
/* 120:    */   {
/* 121:130 */     IChatComponent var1 = func_150951_e();
/* 122:131 */     IChatComponent var2 = new ChatComponentText("[").appendSibling(var1).appendText("]");
/* 123:132 */     var2.setChatStyle(var1.getChatStyle());
/* 124:133 */     return var2;
/* 125:    */   }
/* 126:    */   
/* 127:    */   public boolean equals(Object par1Obj)
/* 128:    */   {
/* 129:138 */     if (this == par1Obj) {
/* 130:140 */       return true;
/* 131:    */     }
/* 132:142 */     if ((par1Obj != null) && (getClass() == par1Obj.getClass()))
/* 133:    */     {
/* 134:144 */       StatBase var2 = (StatBase)par1Obj;
/* 135:145 */       return this.statId.equals(var2.statId);
/* 136:    */     }
/* 137:149 */     return false;
/* 138:    */   }
/* 139:    */   
/* 140:    */   public int hashCode()
/* 141:    */   {
/* 142:155 */     return this.statId.hashCode();
/* 143:    */   }
/* 144:    */   
/* 145:    */   public String toString()
/* 146:    */   {
/* 147:160 */     return "Stat{id=" + this.statId + ", nameId=" + this.statName + ", awardLocallyOnly=" + this.isIndependent + ", formatter=" + this.type + ", objectiveCriteria=" + this.field_150957_c + '}';
/* 148:    */   }
/* 149:    */   
/* 150:    */   public IScoreObjectiveCriteria func_150952_k()
/* 151:    */   {
/* 152:165 */     return this.field_150957_c;
/* 153:    */   }
/* 154:    */   
/* 155:    */   public Class func_150954_l()
/* 156:    */   {
/* 157:170 */     return this.field_150956_d;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public StatBase func_150953_b(Class p_150953_1_)
/* 161:    */   {
/* 162:175 */     this.field_150956_d = p_150953_1_;
/* 163:176 */     return this;
/* 164:    */   }
/* 165:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.stats.StatBase
 * JD-Core Version:    0.7.0.1
 */