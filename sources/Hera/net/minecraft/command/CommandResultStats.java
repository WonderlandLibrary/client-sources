/*     */ package net.minecraft.command;
/*     */ 
/*     */ import net.minecraft.entity.Entity;
/*     */ import net.minecraft.nbt.NBTBase;
/*     */ import net.minecraft.nbt.NBTTagCompound;
/*     */ import net.minecraft.scoreboard.Score;
/*     */ import net.minecraft.scoreboard.ScoreObjective;
/*     */ import net.minecraft.scoreboard.Scoreboard;
/*     */ import net.minecraft.util.BlockPos;
/*     */ import net.minecraft.util.IChatComponent;
/*     */ import net.minecraft.util.Vec3;
/*     */ import net.minecraft.world.World;
/*     */ 
/*     */ public class CommandResultStats
/*     */ {
/*  16 */   private static final int NUM_RESULT_TYPES = (Type.values()).length;
/*  17 */   private static final String[] STRING_RESULT_TYPES = new String[NUM_RESULT_TYPES];
/*     */   
/*     */   private String[] field_179675_c;
/*     */   private String[] field_179673_d;
/*     */   
/*     */   public CommandResultStats() {
/*  23 */     this.field_179675_c = STRING_RESULT_TYPES;
/*  24 */     this.field_179673_d = STRING_RESULT_TYPES;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_179672_a(final ICommandSender sender, Type resultTypeIn, int p_179672_3_) {
/*  29 */     String s = this.field_179675_c[resultTypeIn.getTypeID()];
/*     */     
/*  31 */     if (s != null) {
/*     */       String s1;
/*  33 */       ICommandSender icommandsender = new ICommandSender()
/*     */         {
/*     */           public String getName()
/*     */           {
/*  37 */             return sender.getName();
/*     */           }
/*     */           
/*     */           public IChatComponent getDisplayName() {
/*  41 */             return sender.getDisplayName();
/*     */           }
/*     */           
/*     */           public void addChatMessage(IChatComponent component) {
/*  45 */             sender.addChatMessage(component);
/*     */           }
/*     */           
/*     */           public boolean canCommandSenderUseCommand(int permLevel, String commandName) {
/*  49 */             return true;
/*     */           }
/*     */           
/*     */           public BlockPos getPosition() {
/*  53 */             return sender.getPosition();
/*     */           }
/*     */           
/*     */           public Vec3 getPositionVector() {
/*  57 */             return sender.getPositionVector();
/*     */           }
/*     */           
/*     */           public World getEntityWorld() {
/*  61 */             return sender.getEntityWorld();
/*     */           }
/*     */           
/*     */           public Entity getCommandSenderEntity() {
/*  65 */             return sender.getCommandSenderEntity();
/*     */           }
/*     */           
/*     */           public boolean sendCommandFeedback() {
/*  69 */             return sender.sendCommandFeedback();
/*     */           }
/*     */           
/*     */           public void setCommandStat(CommandResultStats.Type type, int amount) {
/*  73 */             sender.setCommandStat(type, amount);
/*     */           }
/*     */         };
/*     */ 
/*     */ 
/*     */       
/*     */       try {
/*  80 */         s1 = CommandBase.getEntityName(icommandsender, s);
/*     */       }
/*  82 */       catch (EntityNotFoundException var11) {
/*     */         return;
/*     */       } 
/*     */ 
/*     */       
/*  87 */       String s2 = this.field_179673_d[resultTypeIn.getTypeID()];
/*     */       
/*  89 */       if (s2 != null) {
/*     */         
/*  91 */         Scoreboard scoreboard = sender.getEntityWorld().getScoreboard();
/*  92 */         ScoreObjective scoreobjective = scoreboard.getObjective(s2);
/*     */         
/*  94 */         if (scoreobjective != null)
/*     */         {
/*  96 */           if (scoreboard.entityHasObjective(s1, scoreobjective)) {
/*     */             
/*  98 */             Score score = scoreboard.getValueFromObjective(s1, scoreobjective);
/*  99 */             score.setScorePoints(p_179672_3_);
/*     */           } 
/*     */         }
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void readStatsFromNBT(NBTTagCompound tagcompound) {
/* 108 */     if (tagcompound.hasKey("CommandStats", 10)) {
/*     */       
/* 110 */       NBTTagCompound nbttagcompound = tagcompound.getCompoundTag("CommandStats"); byte b; int i;
/*     */       Type[] arrayOfType;
/* 112 */       for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */         
/* 114 */         String s = String.valueOf(commandresultstats$type.getTypeName()) + "Name";
/* 115 */         String s1 = String.valueOf(commandresultstats$type.getTypeName()) + "Objective";
/*     */         
/* 117 */         if (nbttagcompound.hasKey(s, 8) && nbttagcompound.hasKey(s1, 8)) {
/*     */           
/* 119 */           String s2 = nbttagcompound.getString(s);
/* 120 */           String s3 = nbttagcompound.getString(s1);
/* 121 */           func_179667_a(this, commandresultstats$type, s2, s3);
/*     */         } 
/*     */         b++; }
/*     */     
/*     */     } 
/*     */   }
/*     */   
/*     */   public void writeStatsToNBT(NBTTagCompound tagcompound) {
/* 129 */     NBTTagCompound nbttagcompound = new NBTTagCompound(); byte b; int i;
/*     */     Type[] arrayOfType;
/* 131 */     for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */       
/* 133 */       String s = this.field_179675_c[commandresultstats$type.getTypeID()];
/* 134 */       String s1 = this.field_179673_d[commandresultstats$type.getTypeID()];
/*     */       
/* 136 */       if (s != null && s1 != null) {
/*     */         
/* 138 */         nbttagcompound.setString(String.valueOf(commandresultstats$type.getTypeName()) + "Name", s);
/* 139 */         nbttagcompound.setString(String.valueOf(commandresultstats$type.getTypeName()) + "Objective", s1);
/*     */       } 
/*     */       b++; }
/*     */     
/* 143 */     if (!nbttagcompound.hasNoTags())
/*     */     {
/* 145 */       tagcompound.setTag("CommandStats", (NBTBase)nbttagcompound);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public static void func_179667_a(CommandResultStats stats, Type resultType, String p_179667_2_, String p_179667_3_) {
/* 151 */     if (p_179667_2_ != null && p_179667_2_.length() != 0 && p_179667_3_ != null && p_179667_3_.length() != 0) {
/*     */       
/* 153 */       if (stats.field_179675_c == STRING_RESULT_TYPES || stats.field_179673_d == STRING_RESULT_TYPES) {
/*     */         
/* 155 */         stats.field_179675_c = new String[NUM_RESULT_TYPES];
/* 156 */         stats.field_179673_d = new String[NUM_RESULT_TYPES];
/*     */       } 
/*     */       
/* 159 */       stats.field_179675_c[resultType.getTypeID()] = p_179667_2_;
/* 160 */       stats.field_179673_d[resultType.getTypeID()] = p_179667_3_;
/*     */     }
/*     */     else {
/*     */       
/* 164 */       func_179669_a(stats, resultType);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private static void func_179669_a(CommandResultStats resultStatsIn, Type resultTypeIn) {
/* 170 */     if (resultStatsIn.field_179675_c != STRING_RESULT_TYPES && resultStatsIn.field_179673_d != STRING_RESULT_TYPES) {
/*     */       
/* 172 */       resultStatsIn.field_179675_c[resultTypeIn.getTypeID()] = null;
/* 173 */       resultStatsIn.field_179673_d[resultTypeIn.getTypeID()] = null;
/* 174 */       boolean flag = true; byte b; int i;
/*     */       Type[] arrayOfType;
/* 176 */       for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */         
/* 178 */         if (resultStatsIn.field_179675_c[commandresultstats$type.getTypeID()] != null && resultStatsIn.field_179673_d[commandresultstats$type.getTypeID()] != null) {
/*     */           
/* 180 */           flag = false;
/*     */           break;
/*     */         } 
/*     */         b++; }
/*     */       
/* 185 */       if (flag) {
/*     */         
/* 187 */         resultStatsIn.field_179675_c = STRING_RESULT_TYPES;
/* 188 */         resultStatsIn.field_179673_d = STRING_RESULT_TYPES;
/*     */       } 
/*     */     } 
/*     */   } public void func_179671_a(CommandResultStats resultStatsIn) {
/*     */     byte b;
/*     */     int i;
/*     */     Type[] arrayOfType;
/* 195 */     for (i = (arrayOfType = Type.values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */       
/* 197 */       func_179667_a(this, commandresultstats$type, resultStatsIn.field_179675_c[commandresultstats$type.getTypeID()], resultStatsIn.field_179673_d[commandresultstats$type.getTypeID()]);
/*     */       b++; }
/*     */   
/*     */   }
/*     */   
/*     */   public enum Type {
/* 203 */     SUCCESS_COUNT(0, "SuccessCount"),
/* 204 */     AFFECTED_BLOCKS(1, "AffectedBlocks"),
/* 205 */     AFFECTED_ENTITIES(2, "AffectedEntities"),
/* 206 */     AFFECTED_ITEMS(3, "AffectedItems"),
/* 207 */     QUERY_RESULT(4, "QueryResult");
/*     */     
/*     */     final int typeID;
/*     */     
/*     */     final String typeName;
/*     */     
/*     */     Type(int id, String name) {
/* 214 */       this.typeID = id;
/* 215 */       this.typeName = name;
/*     */     }
/*     */ 
/*     */     
/*     */     public int getTypeID() {
/* 220 */       return this.typeID;
/*     */     }
/*     */ 
/*     */     
/*     */     public String getTypeName() {
/* 225 */       return this.typeName;
/*     */     }
/*     */ 
/*     */     
/*     */     public static String[] getTypeNames() {
/* 230 */       String[] astring = new String[(values()).length];
/* 231 */       int i = 0; byte b; int j;
/*     */       Type[] arrayOfType;
/* 233 */       for (j = (arrayOfType = values()).length, b = 0; b < j; ) { Type commandresultstats$type = arrayOfType[b];
/*     */         
/* 235 */         astring[i++] = commandresultstats$type.getTypeName();
/*     */         b++; }
/*     */       
/* 238 */       return astring;
/*     */     } public static Type getTypeByName(String name) {
/*     */       byte b;
/*     */       int i;
/*     */       Type[] arrayOfType;
/* 243 */       for (i = (arrayOfType = values()).length, b = 0; b < i; ) { Type commandresultstats$type = arrayOfType[b];
/*     */         
/* 245 */         if (commandresultstats$type.getTypeName().equals(name))
/*     */         {
/* 247 */           return commandresultstats$type;
/*     */         }
/*     */         b++; }
/*     */       
/* 251 */       return null;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\CommandResultStats.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */