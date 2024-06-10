/*   1:    */ package net.minecraft.scoreboard;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Collection;
/*   5:    */ import java.util.Collections;
/*   6:    */ import java.util.HashMap;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Map;
/*  10:    */ 
/*  11:    */ public class Scoreboard
/*  12:    */ {
/*  13: 14 */   private final Map scoreObjectives = new HashMap();
/*  14: 15 */   private final Map scoreObjectiveCriterias = new HashMap();
/*  15: 16 */   private final Map field_96544_c = new HashMap();
/*  16: 17 */   private final ScoreObjective[] field_96541_d = new ScoreObjective[3];
/*  17: 20 */   private final Map teams = new HashMap();
/*  18: 23 */   private final Map teamMemberships = new HashMap();
/*  19:    */   private static final String __OBFID = "CL_00000619";
/*  20:    */   
/*  21:    */   public ScoreObjective getObjective(String par1Str)
/*  22:    */   {
/*  23: 31 */     return (ScoreObjective)this.scoreObjectives.get(par1Str);
/*  24:    */   }
/*  25:    */   
/*  26:    */   public ScoreObjective addScoreObjective(String par1Str, IScoreObjectiveCriteria par2ScoreObjectiveCriteria)
/*  27:    */   {
/*  28: 36 */     ScoreObjective var3 = getObjective(par1Str);
/*  29: 38 */     if (var3 != null) {
/*  30: 40 */       throw new IllegalArgumentException("An objective with the name '" + par1Str + "' already exists!");
/*  31:    */     }
/*  32: 44 */     var3 = new ScoreObjective(this, par1Str, par2ScoreObjectiveCriteria);
/*  33: 45 */     Object var4 = (List)this.scoreObjectiveCriterias.get(par2ScoreObjectiveCriteria);
/*  34: 47 */     if (var4 == null)
/*  35:    */     {
/*  36: 49 */       var4 = new ArrayList();
/*  37: 50 */       this.scoreObjectiveCriterias.put(par2ScoreObjectiveCriteria, var4);
/*  38:    */     }
/*  39: 53 */     ((List)var4).add(var3);
/*  40: 54 */     this.scoreObjectives.put(par1Str, var3);
/*  41: 55 */     func_96522_a(var3);
/*  42: 56 */     return var3;
/*  43:    */   }
/*  44:    */   
/*  45:    */   public Collection func_96520_a(IScoreObjectiveCriteria par1ScoreObjectiveCriteria)
/*  46:    */   {
/*  47: 62 */     Collection var2 = (Collection)this.scoreObjectiveCriterias.get(par1ScoreObjectiveCriteria);
/*  48: 63 */     return var2 == null ? new ArrayList() : new ArrayList(var2);
/*  49:    */   }
/*  50:    */   
/*  51:    */   public Score func_96529_a(String par1Str, ScoreObjective par2ScoreObjective)
/*  52:    */   {
/*  53: 68 */     Object var3 = (Map)this.field_96544_c.get(par1Str);
/*  54: 70 */     if (var3 == null)
/*  55:    */     {
/*  56: 72 */       var3 = new HashMap();
/*  57: 73 */       this.field_96544_c.put(par1Str, var3);
/*  58:    */     }
/*  59: 76 */     Score var4 = (Score)((Map)var3).get(par2ScoreObjective);
/*  60: 78 */     if (var4 == null)
/*  61:    */     {
/*  62: 80 */       var4 = new Score(this, par2ScoreObjective, par1Str);
/*  63: 81 */       ((Map)var3).put(par2ScoreObjective, var4);
/*  64:    */     }
/*  65: 84 */     return var4;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public Collection func_96534_i(ScoreObjective par1ScoreObjective)
/*  69:    */   {
/*  70: 89 */     ArrayList var2 = new ArrayList();
/*  71: 90 */     Iterator var3 = this.field_96544_c.values().iterator();
/*  72: 92 */     while (var3.hasNext())
/*  73:    */     {
/*  74: 94 */       Map var4 = (Map)var3.next();
/*  75: 95 */       Score var5 = (Score)var4.get(par1ScoreObjective);
/*  76: 97 */       if (var5 != null) {
/*  77: 99 */         var2.add(var5);
/*  78:    */       }
/*  79:    */     }
/*  80:103 */     Collections.sort(var2, Score.field_96658_a);
/*  81:104 */     return var2;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public Collection getScoreObjectives()
/*  85:    */   {
/*  86:109 */     return this.scoreObjectives.values();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public Collection getObjectiveNames()
/*  90:    */   {
/*  91:114 */     return this.field_96544_c.keySet();
/*  92:    */   }
/*  93:    */   
/*  94:    */   public void func_96515_c(String par1Str)
/*  95:    */   {
/*  96:119 */     Map var2 = (Map)this.field_96544_c.remove(par1Str);
/*  97:121 */     if (var2 != null) {
/*  98:123 */       func_96516_a(par1Str);
/*  99:    */     }
/* 100:    */   }
/* 101:    */   
/* 102:    */   public Collection func_96528_e()
/* 103:    */   {
/* 104:129 */     Collection var1 = this.field_96544_c.values();
/* 105:130 */     ArrayList var2 = new ArrayList();
/* 106:131 */     Iterator var3 = var1.iterator();
/* 107:133 */     while (var3.hasNext())
/* 108:    */     {
/* 109:135 */       Map var4 = (Map)var3.next();
/* 110:136 */       var2.addAll(var4.values());
/* 111:    */     }
/* 112:139 */     return var2;
/* 113:    */   }
/* 114:    */   
/* 115:    */   public Map func_96510_d(String par1Str)
/* 116:    */   {
/* 117:144 */     Object var2 = (Map)this.field_96544_c.get(par1Str);
/* 118:146 */     if (var2 == null) {
/* 119:148 */       var2 = new HashMap();
/* 120:    */     }
/* 121:151 */     return (Map)var2;
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void func_96519_k(ScoreObjective par1ScoreObjective)
/* 125:    */   {
/* 126:156 */     this.scoreObjectives.remove(par1ScoreObjective.getName());
/* 127:158 */     for (int var2 = 0; var2 < 3; var2++) {
/* 128:160 */       if (func_96539_a(var2) == par1ScoreObjective) {
/* 129:162 */         func_96530_a(var2, null);
/* 130:    */       }
/* 131:    */     }
/* 132:166 */     List var5 = (List)this.scoreObjectiveCriterias.get(par1ScoreObjective.getCriteria());
/* 133:168 */     if (var5 != null) {
/* 134:170 */       var5.remove(par1ScoreObjective);
/* 135:    */     }
/* 136:173 */     Iterator var3 = this.field_96544_c.values().iterator();
/* 137:175 */     while (var3.hasNext())
/* 138:    */     {
/* 139:177 */       Map var4 = (Map)var3.next();
/* 140:178 */       var4.remove(par1ScoreObjective);
/* 141:    */     }
/* 142:181 */     func_96533_c(par1ScoreObjective);
/* 143:    */   }
/* 144:    */   
/* 145:    */   public void func_96530_a(int par1, ScoreObjective par2ScoreObjective)
/* 146:    */   {
/* 147:186 */     this.field_96541_d[par1] = par2ScoreObjective;
/* 148:    */   }
/* 149:    */   
/* 150:    */   public ScoreObjective func_96539_a(int par1)
/* 151:    */   {
/* 152:191 */     return this.field_96541_d[par1];
/* 153:    */   }
/* 154:    */   
/* 155:    */   public ScorePlayerTeam getTeam(String par1Str)
/* 156:    */   {
/* 157:199 */     return (ScorePlayerTeam)this.teams.get(par1Str);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public ScorePlayerTeam createTeam(String par1Str)
/* 161:    */   {
/* 162:208 */     ScorePlayerTeam var2 = getTeam(par1Str);
/* 163:210 */     if (var2 != null) {
/* 164:212 */       throw new IllegalArgumentException("A team with the name '" + par1Str + "' already exists!");
/* 165:    */     }
/* 166:216 */     var2 = new ScorePlayerTeam(this, par1Str);
/* 167:217 */     this.teams.put(par1Str, var2);
/* 168:218 */     func_96523_a(var2);
/* 169:219 */     return var2;
/* 170:    */   }
/* 171:    */   
/* 172:    */   public void removeTeam(ScorePlayerTeam par1ScorePlayerTeam)
/* 173:    */   {
/* 174:228 */     this.teams.remove(par1ScorePlayerTeam.getRegisteredName());
/* 175:229 */     Iterator var2 = par1ScorePlayerTeam.getMembershipCollection().iterator();
/* 176:231 */     while (var2.hasNext())
/* 177:    */     {
/* 178:233 */       String var3 = (String)var2.next();
/* 179:234 */       this.teamMemberships.remove(var3);
/* 180:    */     }
/* 181:237 */     func_96513_c(par1ScorePlayerTeam);
/* 182:    */   }
/* 183:    */   
/* 184:    */   public boolean func_151392_a(String p_151392_1_, String p_151392_2_)
/* 185:    */   {
/* 186:242 */     if (!this.teams.containsKey(p_151392_2_)) {
/* 187:244 */       return false;
/* 188:    */     }
/* 189:248 */     ScorePlayerTeam var3 = getTeam(p_151392_2_);
/* 190:250 */     if (getPlayersTeam(p_151392_1_) != null) {
/* 191:252 */       func_96524_g(p_151392_1_);
/* 192:    */     }
/* 193:255 */     this.teamMemberships.put(p_151392_1_, var3);
/* 194:256 */     var3.getMembershipCollection().add(p_151392_1_);
/* 195:257 */     return true;
/* 196:    */   }
/* 197:    */   
/* 198:    */   public boolean func_96524_g(String par1Str)
/* 199:    */   {
/* 200:263 */     ScorePlayerTeam var2 = getPlayersTeam(par1Str);
/* 201:265 */     if (var2 != null)
/* 202:    */     {
/* 203:267 */       removePlayerFromTeam(par1Str, var2);
/* 204:268 */       return true;
/* 205:    */     }
/* 206:272 */     return false;
/* 207:    */   }
/* 208:    */   
/* 209:    */   public void removePlayerFromTeam(String par1Str, ScorePlayerTeam par2ScorePlayerTeam)
/* 210:    */   {
/* 211:282 */     if (getPlayersTeam(par1Str) != par2ScorePlayerTeam) {
/* 212:284 */       throw new IllegalStateException("Player is either on another team or not on any team. Cannot remove from team '" + par2ScorePlayerTeam.getRegisteredName() + "'.");
/* 213:    */     }
/* 214:288 */     this.teamMemberships.remove(par1Str);
/* 215:289 */     par2ScorePlayerTeam.getMembershipCollection().remove(par1Str);
/* 216:    */   }
/* 217:    */   
/* 218:    */   public Collection getTeamNames()
/* 219:    */   {
/* 220:298 */     return this.teams.keySet();
/* 221:    */   }
/* 222:    */   
/* 223:    */   public Collection getTeams()
/* 224:    */   {
/* 225:306 */     return this.teams.values();
/* 226:    */   }
/* 227:    */   
/* 228:    */   public ScorePlayerTeam getPlayersTeam(String par1Str)
/* 229:    */   {
/* 230:314 */     return (ScorePlayerTeam)this.teamMemberships.get(par1Str);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public void func_96522_a(ScoreObjective par1ScoreObjective) {}
/* 234:    */   
/* 235:    */   public void func_96532_b(ScoreObjective par1ScoreObjective) {}
/* 236:    */   
/* 237:    */   public void func_96533_c(ScoreObjective par1ScoreObjective) {}
/* 238:    */   
/* 239:    */   public void func_96536_a(Score par1Score) {}
/* 240:    */   
/* 241:    */   public void func_96516_a(String par1Str) {}
/* 242:    */   
/* 243:    */   public void func_96523_a(ScorePlayerTeam par1ScorePlayerTeam) {}
/* 244:    */   
/* 245:    */   public void func_96538_b(ScorePlayerTeam par1ScorePlayerTeam) {}
/* 246:    */   
/* 247:    */   public void func_96513_c(ScorePlayerTeam par1ScorePlayerTeam) {}
/* 248:    */   
/* 249:    */   public static String getObjectiveDisplaySlot(int par0)
/* 250:    */   {
/* 251:338 */     switch (par0)
/* 252:    */     {
/* 253:    */     case 0: 
/* 254:341 */       return "list";
/* 255:    */     case 1: 
/* 256:344 */       return "sidebar";
/* 257:    */     case 2: 
/* 258:347 */       return "belowName";
/* 259:    */     }
/* 260:350 */     return null;
/* 261:    */   }
/* 262:    */   
/* 263:    */   public static int getObjectiveDisplaySlotNumber(String par0Str)
/* 264:    */   {
/* 265:359 */     return par0Str.equalsIgnoreCase("belowName") ? 2 : par0Str.equalsIgnoreCase("sidebar") ? 1 : par0Str.equalsIgnoreCase("list") ? 0 : -1;
/* 266:    */   }
/* 267:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.Scoreboard
 * JD-Core Version:    0.7.0.1
 */