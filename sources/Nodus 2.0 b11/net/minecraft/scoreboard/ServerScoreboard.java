/*   1:    */ package net.minecraft.scoreboard;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.Arrays;
/*   5:    */ import java.util.Collection;
/*   6:    */ import java.util.HashSet;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.Set;
/*  10:    */ import net.minecraft.entity.player.EntityPlayerMP;
/*  11:    */ import net.minecraft.network.NetHandlerPlayServer;
/*  12:    */ import net.minecraft.network.Packet;
/*  13:    */ import net.minecraft.network.play.server.S3BPacketScoreboardObjective;
/*  14:    */ import net.minecraft.network.play.server.S3CPacketUpdateScore;
/*  15:    */ import net.minecraft.network.play.server.S3DPacketDisplayScoreboard;
/*  16:    */ import net.minecraft.network.play.server.S3EPacketTeams;
/*  17:    */ import net.minecraft.server.MinecraftServer;
/*  18:    */ import net.minecraft.server.management.ServerConfigurationManager;
/*  19:    */ 
/*  20:    */ public class ServerScoreboard
/*  21:    */   extends Scoreboard
/*  22:    */ {
/*  23:    */   private final MinecraftServer scoreboardMCServer;
/*  24: 20 */   private final Set field_96553_b = new HashSet();
/*  25:    */   private ScoreboardSaveData field_96554_c;
/*  26:    */   private static final String __OBFID = "CL_00001424";
/*  27:    */   
/*  28:    */   public ServerScoreboard(MinecraftServer par1MinecraftServer)
/*  29:    */   {
/*  30: 26 */     this.scoreboardMCServer = par1MinecraftServer;
/*  31:    */   }
/*  32:    */   
/*  33:    */   public void func_96536_a(Score par1Score)
/*  34:    */   {
/*  35: 31 */     super.func_96536_a(par1Score);
/*  36: 33 */     if (this.field_96553_b.contains(par1Score.func_96645_d())) {
/*  37: 35 */       this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3CPacketUpdateScore(par1Score, 0));
/*  38:    */     }
/*  39: 38 */     func_96551_b();
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void func_96516_a(String par1Str)
/*  43:    */   {
/*  44: 43 */     super.func_96516_a(par1Str);
/*  45: 44 */     this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3CPacketUpdateScore(par1Str));
/*  46: 45 */     func_96551_b();
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void func_96530_a(int par1, ScoreObjective par2ScoreObjective)
/*  50:    */   {
/*  51: 50 */     ScoreObjective var3 = func_96539_a(par1);
/*  52: 51 */     super.func_96530_a(par1, par2ScoreObjective);
/*  53: 53 */     if ((var3 != par2ScoreObjective) && (var3 != null)) {
/*  54: 55 */       if (func_96552_h(var3) > 0) {
/*  55: 57 */         this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3DPacketDisplayScoreboard(par1, par2ScoreObjective));
/*  56:    */       } else {
/*  57: 61 */         func_96546_g(var3);
/*  58:    */       }
/*  59:    */     }
/*  60: 65 */     if (par2ScoreObjective != null) {
/*  61: 67 */       if (this.field_96553_b.contains(par2ScoreObjective)) {
/*  62: 69 */         this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3DPacketDisplayScoreboard(par1, par2ScoreObjective));
/*  63:    */       } else {
/*  64: 73 */         func_96549_e(par2ScoreObjective);
/*  65:    */       }
/*  66:    */     }
/*  67: 77 */     func_96551_b();
/*  68:    */   }
/*  69:    */   
/*  70:    */   public boolean func_151392_a(String p_151392_1_, String p_151392_2_)
/*  71:    */   {
/*  72: 82 */     if (super.func_151392_a(p_151392_1_, p_151392_2_))
/*  73:    */     {
/*  74: 84 */       ScorePlayerTeam var3 = getTeam(p_151392_2_);
/*  75: 85 */       this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(var3, Arrays.asList(new String[] { p_151392_1_ }), 3));
/*  76: 86 */       func_96551_b();
/*  77: 87 */       return true;
/*  78:    */     }
/*  79: 91 */     return false;
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void removePlayerFromTeam(String par1Str, ScorePlayerTeam par2ScorePlayerTeam)
/*  83:    */   {
/*  84:101 */     super.removePlayerFromTeam(par1Str, par2ScorePlayerTeam);
/*  85:102 */     this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(par2ScorePlayerTeam, Arrays.asList(new String[] { par1Str }), 4));
/*  86:103 */     func_96551_b();
/*  87:    */   }
/*  88:    */   
/*  89:    */   public void func_96522_a(ScoreObjective par1ScoreObjective)
/*  90:    */   {
/*  91:108 */     super.func_96522_a(par1ScoreObjective);
/*  92:109 */     func_96551_b();
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void func_96532_b(ScoreObjective par1ScoreObjective)
/*  96:    */   {
/*  97:114 */     super.func_96532_b(par1ScoreObjective);
/*  98:116 */     if (this.field_96553_b.contains(par1ScoreObjective)) {
/*  99:118 */       this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3BPacketScoreboardObjective(par1ScoreObjective, 2));
/* 100:    */     }
/* 101:121 */     func_96551_b();
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void func_96533_c(ScoreObjective par1ScoreObjective)
/* 105:    */   {
/* 106:126 */     super.func_96533_c(par1ScoreObjective);
/* 107:128 */     if (this.field_96553_b.contains(par1ScoreObjective)) {
/* 108:130 */       func_96546_g(par1ScoreObjective);
/* 109:    */     }
/* 110:133 */     func_96551_b();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public void func_96523_a(ScorePlayerTeam par1ScorePlayerTeam)
/* 114:    */   {
/* 115:138 */     super.func_96523_a(par1ScorePlayerTeam);
/* 116:139 */     this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(par1ScorePlayerTeam, 0));
/* 117:140 */     func_96551_b();
/* 118:    */   }
/* 119:    */   
/* 120:    */   public void func_96538_b(ScorePlayerTeam par1ScorePlayerTeam)
/* 121:    */   {
/* 122:145 */     super.func_96538_b(par1ScorePlayerTeam);
/* 123:146 */     this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(par1ScorePlayerTeam, 2));
/* 124:147 */     func_96551_b();
/* 125:    */   }
/* 126:    */   
/* 127:    */   public void func_96513_c(ScorePlayerTeam par1ScorePlayerTeam)
/* 128:    */   {
/* 129:152 */     super.func_96513_c(par1ScorePlayerTeam);
/* 130:153 */     this.scoreboardMCServer.getConfigurationManager().func_148540_a(new S3EPacketTeams(par1ScorePlayerTeam, 1));
/* 131:154 */     func_96551_b();
/* 132:    */   }
/* 133:    */   
/* 134:    */   public void func_96547_a(ScoreboardSaveData par1ScoreboardSaveData)
/* 135:    */   {
/* 136:159 */     this.field_96554_c = par1ScoreboardSaveData;
/* 137:    */   }
/* 138:    */   
/* 139:    */   protected void func_96551_b()
/* 140:    */   {
/* 141:164 */     if (this.field_96554_c != null) {
/* 142:166 */       this.field_96554_c.markDirty();
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public List func_96550_d(ScoreObjective par1ScoreObjective)
/* 147:    */   {
/* 148:172 */     ArrayList var2 = new ArrayList();
/* 149:173 */     var2.add(new S3BPacketScoreboardObjective(par1ScoreObjective, 0));
/* 150:175 */     for (int var3 = 0; var3 < 3; var3++) {
/* 151:177 */       if (func_96539_a(var3) == par1ScoreObjective) {
/* 152:179 */         var2.add(new S3DPacketDisplayScoreboard(var3, par1ScoreObjective));
/* 153:    */       }
/* 154:    */     }
/* 155:183 */     Iterator var5 = func_96534_i(par1ScoreObjective).iterator();
/* 156:185 */     while (var5.hasNext())
/* 157:    */     {
/* 158:187 */       Score var4 = (Score)var5.next();
/* 159:188 */       var2.add(new S3CPacketUpdateScore(var4, 0));
/* 160:    */     }
/* 161:191 */     return var2;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public void func_96549_e(ScoreObjective par1ScoreObjective)
/* 165:    */   {
/* 166:196 */     List var2 = func_96550_d(par1ScoreObjective);
/* 167:197 */     Iterator var3 = this.scoreboardMCServer.getConfigurationManager().playerEntityList.iterator();
/* 168:    */     Iterator var5;
/* 169:199 */     for (; var3.hasNext(); var5.hasNext())
/* 170:    */     {
/* 171:201 */       EntityPlayerMP var4 = (EntityPlayerMP)var3.next();
/* 172:202 */       var5 = var2.iterator();
/* 173:    */       
/* 174:204 */       continue;
/* 175:    */       
/* 176:206 */       Packet var6 = (Packet)var5.next();
/* 177:207 */       var4.playerNetServerHandler.sendPacket(var6);
/* 178:    */     }
/* 179:211 */     this.field_96553_b.add(par1ScoreObjective);
/* 180:    */   }
/* 181:    */   
/* 182:    */   public List func_96548_f(ScoreObjective par1ScoreObjective)
/* 183:    */   {
/* 184:216 */     ArrayList var2 = new ArrayList();
/* 185:217 */     var2.add(new S3BPacketScoreboardObjective(par1ScoreObjective, 1));
/* 186:219 */     for (int var3 = 0; var3 < 3; var3++) {
/* 187:221 */       if (func_96539_a(var3) == par1ScoreObjective) {
/* 188:223 */         var2.add(new S3DPacketDisplayScoreboard(var3, par1ScoreObjective));
/* 189:    */       }
/* 190:    */     }
/* 191:227 */     return var2;
/* 192:    */   }
/* 193:    */   
/* 194:    */   public void func_96546_g(ScoreObjective par1ScoreObjective)
/* 195:    */   {
/* 196:232 */     List var2 = func_96548_f(par1ScoreObjective);
/* 197:233 */     Iterator var3 = this.scoreboardMCServer.getConfigurationManager().playerEntityList.iterator();
/* 198:    */     Iterator var5;
/* 199:235 */     for (; var3.hasNext(); var5.hasNext())
/* 200:    */     {
/* 201:237 */       EntityPlayerMP var4 = (EntityPlayerMP)var3.next();
/* 202:238 */       var5 = var2.iterator();
/* 203:    */       
/* 204:240 */       continue;
/* 205:    */       
/* 206:242 */       Packet var6 = (Packet)var5.next();
/* 207:243 */       var4.playerNetServerHandler.sendPacket(var6);
/* 208:    */     }
/* 209:247 */     this.field_96553_b.remove(par1ScoreObjective);
/* 210:    */   }
/* 211:    */   
/* 212:    */   public int func_96552_h(ScoreObjective par1ScoreObjective)
/* 213:    */   {
/* 214:252 */     int var2 = 0;
/* 215:254 */     for (int var3 = 0; var3 < 3; var3++) {
/* 216:256 */       if (func_96539_a(var3) == par1ScoreObjective) {
/* 217:258 */         var2++;
/* 218:    */       }
/* 219:    */     }
/* 220:262 */     return var2;
/* 221:    */   }
/* 222:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.scoreboard.ServerScoreboard
 * JD-Core Version:    0.7.0.1
 */