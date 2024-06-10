/*   1:    */ package net.minecraft.scoreboard;
/*   2:    */ 
/*   3:    */ import java.util.Collection;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.Map;
/*   6:    */ import net.minecraft.nbt.NBTTagCompound;
/*   7:    */ import net.minecraft.nbt.NBTTagList;
/*   8:    */ import net.minecraft.nbt.NBTTagString;
/*   9:    */ import net.minecraft.world.WorldSavedData;
/*  10:    */ import org.apache.logging.log4j.LogManager;
/*  11:    */ import org.apache.logging.log4j.Logger;
/*  12:    */ 
/*  13:    */ public class ScoreboardSaveData
/*  14:    */   extends WorldSavedData
/*  15:    */ {
/*  16: 14 */   private static final Logger logger = ;
/*  17:    */   private Scoreboard theScoreboard;
/*  18:    */   private NBTTagCompound field_96506_b;
/*  19:    */   private static final String __OBFID = "CL_00000620";
/*  20:    */   
/*  21:    */   public ScoreboardSaveData()
/*  22:    */   {
/*  23: 21 */     this("scoreboard");
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ScoreboardSaveData(String par1Str)
/*  27:    */   {
/*  28: 26 */     super(par1Str);
/*  29:    */   }
/*  30:    */   
/*  31:    */   public void func_96499_a(Scoreboard par1Scoreboard)
/*  32:    */   {
/*  33: 31 */     this.theScoreboard = par1Scoreboard;
/*  34: 33 */     if (this.field_96506_b != null) {
/*  35: 35 */       readFromNBT(this.field_96506_b);
/*  36:    */     }
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void readFromNBT(NBTTagCompound par1NBTTagCompound)
/*  40:    */   {
/*  41: 44 */     if (this.theScoreboard == null)
/*  42:    */     {
/*  43: 46 */       this.field_96506_b = par1NBTTagCompound;
/*  44:    */     }
/*  45:    */     else
/*  46:    */     {
/*  47: 50 */       func_96501_b(par1NBTTagCompound.getTagList("Objectives", 10));
/*  48: 51 */       func_96500_c(par1NBTTagCompound.getTagList("PlayerScores", 10));
/*  49: 53 */       if (par1NBTTagCompound.func_150297_b("DisplaySlots", 10)) {
/*  50: 55 */         func_96504_c(par1NBTTagCompound.getCompoundTag("DisplaySlots"));
/*  51:    */       }
/*  52: 58 */       if (par1NBTTagCompound.func_150297_b("Teams", 9)) {
/*  53: 60 */         func_96498_a(par1NBTTagCompound.getTagList("Teams", 10));
/*  54:    */       }
/*  55:    */     }
/*  56:    */   }
/*  57:    */   
/*  58:    */   protected void func_96498_a(NBTTagList par1NBTTagList)
/*  59:    */   {
/*  60: 67 */     for (int var2 = 0; var2 < par1NBTTagList.tagCount(); var2++)
/*  61:    */     {
/*  62: 69 */       NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
/*  63: 70 */       ScorePlayerTeam var4 = this.theScoreboard.createTeam(var3.getString("Name"));
/*  64: 71 */       var4.setTeamName(var3.getString("DisplayName"));
/*  65: 72 */       var4.setNamePrefix(var3.getString("Prefix"));
/*  66: 73 */       var4.setNameSuffix(var3.getString("Suffix"));
/*  67: 75 */       if (var3.func_150297_b("AllowFriendlyFire", 99)) {
/*  68: 77 */         var4.setAllowFriendlyFire(var3.getBoolean("AllowFriendlyFire"));
/*  69:    */       }
/*  70: 80 */       if (var3.func_150297_b("SeeFriendlyInvisibles", 99)) {
/*  71: 82 */         var4.setSeeFriendlyInvisiblesEnabled(var3.getBoolean("SeeFriendlyInvisibles"));
/*  72:    */       }
/*  73: 85 */       func_96502_a(var4, var3.getTagList("Players", 8));
/*  74:    */     }
/*  75:    */   }
/*  76:    */   
/*  77:    */   protected void func_96502_a(ScorePlayerTeam par1ScorePlayerTeam, NBTTagList par2NBTTagList)
/*  78:    */   {
/*  79: 91 */     for (int var3 = 0; var3 < par2NBTTagList.tagCount(); var3++) {
/*  80: 93 */       this.theScoreboard.func_151392_a(par2NBTTagList.getStringTagAt(var3), par1ScorePlayerTeam.getRegisteredName());
/*  81:    */     }
/*  82:    */   }
/*  83:    */   
/*  84:    */   protected void func_96504_c(NBTTagCompound par1NBTTagCompound)
/*  85:    */   {
/*  86: 99 */     for (int var2 = 0; var2 < 3; var2++) {
/*  87:101 */       if (par1NBTTagCompound.func_150297_b("slot_" + var2, 8))
/*  88:    */       {
/*  89:103 */         String var3 = par1NBTTagCompound.getString("slot_" + var2);
/*  90:104 */         ScoreObjective var4 = this.theScoreboard.getObjective(var3);
/*  91:105 */         this.theScoreboard.func_96530_a(var2, var4);
/*  92:    */       }
/*  93:    */     }
/*  94:    */   }
/*  95:    */   
/*  96:    */   protected void func_96501_b(NBTTagList par1NBTTagList)
/*  97:    */   {
/*  98:112 */     for (int var2 = 0; var2 < par1NBTTagList.tagCount(); var2++)
/*  99:    */     {
/* 100:114 */       NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
/* 101:115 */       IScoreObjectiveCriteria var4 = (IScoreObjectiveCriteria)IScoreObjectiveCriteria.field_96643_a.get(var3.getString("CriteriaName"));
/* 102:116 */       ScoreObjective var5 = this.theScoreboard.addScoreObjective(var3.getString("Name"), var4);
/* 103:117 */       var5.setDisplayName(var3.getString("DisplayName"));
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   protected void func_96500_c(NBTTagList par1NBTTagList)
/* 108:    */   {
/* 109:123 */     for (int var2 = 0; var2 < par1NBTTagList.tagCount(); var2++)
/* 110:    */     {
/* 111:125 */       NBTTagCompound var3 = par1NBTTagList.getCompoundTagAt(var2);
/* 112:126 */       ScoreObjective var4 = this.theScoreboard.getObjective(var3.getString("Objective"));
/* 113:127 */       Score var5 = this.theScoreboard.func_96529_a(var3.getString("Name"), var4);
/* 114:128 */       var5.func_96647_c(var3.getInteger("Score"));
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void writeToNBT(NBTTagCompound par1NBTTagCompound)
/* 119:    */   {
/* 120:137 */     if (this.theScoreboard == null)
/* 121:    */     {
/* 122:139 */       logger.warn("Tried to save scoreboard without having a scoreboard...");
/* 123:    */     }
/* 124:    */     else
/* 125:    */     {
/* 126:143 */       par1NBTTagCompound.setTag("Objectives", func_96505_b());
/* 127:144 */       par1NBTTagCompound.setTag("PlayerScores", func_96503_e());
/* 128:145 */       par1NBTTagCompound.setTag("Teams", func_96496_a());
/* 129:146 */       func_96497_d(par1NBTTagCompound);
/* 130:    */     }
/* 131:    */   }
/* 132:    */   
/* 133:    */   protected NBTTagList func_96496_a()
/* 134:    */   {
/* 135:152 */     NBTTagList var1 = new NBTTagList();
/* 136:153 */     Collection var2 = this.theScoreboard.getTeams();
/* 137:154 */     Iterator var3 = var2.iterator();
/* 138:156 */     while (var3.hasNext())
/* 139:    */     {
/* 140:158 */       ScorePlayerTeam var4 = (ScorePlayerTeam)var3.next();
/* 141:159 */       NBTTagCompound var5 = new NBTTagCompound();
/* 142:160 */       var5.setString("Name", var4.getRegisteredName());
/* 143:161 */       var5.setString("DisplayName", var4.func_96669_c());
/* 144:162 */       var5.setString("Prefix", var4.getColorPrefix());
/* 145:163 */       var5.setString("Suffix", var4.getColorSuffix());
/* 146:164 */       var5.setBoolean("AllowFriendlyFire", var4.getAllowFriendlyFire());
/* 147:165 */       var5.setBoolean("SeeFriendlyInvisibles", var4.func_98297_h());
/* 148:166 */       NBTTagList var6 = new NBTTagList();
/* 149:167 */       Iterator var7 = var4.getMembershipCollection().iterator();
/* 150:169 */       while (var7.hasNext())
/* 151:    */       {
/* 152:171 */         String var8 = (String)var7.next();
/* 153:172 */         var6.appendTag(new NBTTagString(var8));
/* 154:    */       }
/* 155:175 */       var5.setTag("Players", var6);
/* 156:176 */       var1.appendTag(var5);
/* 157:    */     }
/* 158:179 */     return var1;
/* 159:    */   }
/* 160:    */   
/* 161:    */   protected void func_96497_d(NBTTagCompound par1NBTTagCompound)
/* 162:    */   {
/* 163:184 */     NBTTagCompound var2 = new NBTTagCompound();
/* 164:185 */     boolean var3 = false;
/* 165:187 */     for (int var4 = 0; var4 < 3; var4++)
/* 166:    */     {
/* 167:189 */       ScoreObjective var5 = this.theScoreboard.func_96539_a(var4);
/* 168:191 */       if (var5 != null)
/* 169:    */       {
/* 170:193 */         var2.setString("slot_" + var4, var5.getName());
/* 171:194 */         var3 = true;
/* 172:    */       }
/* 173:    */     }
/* 174:198 */     if (var3) {
/* 175:200 */       par1NBTTagCompound.setTag("DisplaySlots", var2);
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   protected NBTTagList func_96505_b()
/* 180:    */   {
/* 181:206 */     NBTTagList var1 = new NBTTagList();
/* 182:207 */     Collection var2 = this.theScoreboard.getScoreObjectives();
/* 183:208 */     Iterator var3 = var2.iterator();
/* 184:210 */     while (var3.hasNext())
/* 185:    */     {
/* 186:212 */       ScoreObjective var4 = (ScoreObjective)var3.next();
/* 187:213 */       NBTTagCompound var5 = new NBTTagCompound();
/* 188:214 */       var5.setString("Name", var4.getName());
/* 189:215 */       var5.setString("CriteriaName", var4.getCriteria().func_96636_a());
/* 190:216 */       var5.setString("DisplayName", var4.getDisplayName());
/* 191:217 */       var1.appendTag(var5);
/* 192:    */     }
/* 193:220 */     return var1;
/* 194:    */   }
/* 195:    */   
/* 196:    */   protected NBTTagList func_96503_e()
/* 197:    */   {
/* 198:225 */     NBTTagList var1 = new NBTTagList();
/* 199:226 */     Collection var2 = this.theScoreboard.func_96528_e();
/* 200:227 */     Iterator var3 = var2.iterator();
/* 201:229 */     while (var3.hasNext())
/* 202:    */     {
/* 203:231 */       Score var4 = (Score)var3.next();
/* 204:232 */       NBTTagCompound var5 = new NBTTagCompound();
/* 205:233 */       var5.setString("Name", var4.getPlayerName());
/* 206:234 */       var5.setString("Objective", var4.func_96645_d().getName());
/* 207:235 */       var5.setInteger("Score", var4.getScorePoints());
/* 208:236 */       var1.appendTag(var5);
/* 209:    */     }
/* 210:239 */     return var1;
/* 211:    */   }
/* 212:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.ScoreboardSaveData
 * JD-Core Version:    0.7.0.1
 */