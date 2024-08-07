/*     */ package net.minecraft.command;
/*     */ 
/*     */ import java.util.List;
/*     */ import net.minecraft.entity.player.EntityPlayer;
/*     */ import net.minecraft.entity.player.EntityPlayerMP;
/*     */ import net.minecraft.server.MinecraftServer;
/*     */ import net.minecraft.util.BlockPos;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CommandXP
/*     */   extends CommandBase
/*     */ {
/*     */   public String getCommandName() {
/*  15 */     return "xp";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getRequiredPermissionLevel() {
/*  23 */     return 2;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getCommandUsage(ICommandSender sender) {
/*  31 */     return "commands.xp.usage";
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void processCommand(ICommandSender sender, String[] args) throws CommandException {
/*  39 */     if (args.length <= 0)
/*     */     {
/*  41 */       throw new WrongUsageException("commands.xp.usage", new Object[0]);
/*     */     }
/*     */ 
/*     */     
/*  45 */     String s = args[0];
/*  46 */     boolean flag = !(!s.endsWith("l") && !s.endsWith("L"));
/*     */     
/*  48 */     if (flag && s.length() > 1)
/*     */     {
/*  50 */       s = s.substring(0, s.length() - 1);
/*     */     }
/*     */     
/*  53 */     int i = parseInt(s);
/*  54 */     boolean flag1 = (i < 0);
/*     */     
/*  56 */     if (flag1)
/*     */     {
/*  58 */       i *= -1;
/*     */     }
/*     */     
/*  61 */     EntityPlayerMP entityPlayerMP = (args.length > 1) ? getPlayer(sender, args[1]) : getCommandSenderAsPlayer(sender);
/*     */     
/*  63 */     if (flag) {
/*     */       
/*  65 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceLevel);
/*     */       
/*  67 */       if (flag1)
/*     */       {
/*  69 */         entityPlayerMP.addExperienceLevel(-i);
/*  70 */         notifyOperators(sender, this, "commands.xp.success.negative.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*     */       }
/*     */       else
/*     */       {
/*  74 */         entityPlayerMP.addExperienceLevel(i);
/*  75 */         notifyOperators(sender, this, "commands.xp.success.levels", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/*  80 */       sender.setCommandStat(CommandResultStats.Type.QUERY_RESULT, ((EntityPlayer)entityPlayerMP).experienceTotal);
/*     */       
/*  82 */       if (flag1)
/*     */       {
/*  84 */         throw new CommandException("commands.xp.failure.widthdrawXp", new Object[0]);
/*     */       }
/*     */       
/*  87 */       entityPlayerMP.addExperience(i);
/*  88 */       notifyOperators(sender, this, "commands.xp.success", new Object[] { Integer.valueOf(i), entityPlayerMP.getName() });
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public List<String> addTabCompletionOptions(ICommandSender sender, String[] args, BlockPos pos) {
/*  95 */     return (args.length == 2) ? getListOfStringsMatchingLastWord(args, getAllUsernames()) : null;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String[] getAllUsernames() {
/* 100 */     return MinecraftServer.getServer().getAllUsernames();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isUsernameIndex(String[] args, int index) {
/* 108 */     return (index == 1);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\command\CommandXP.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */